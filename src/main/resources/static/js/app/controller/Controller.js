
simonaApp.controller('MainController', ['$scope', '$http', '$location', 'esriLoader', 'MonitoringService', '$stomp',
    function ($scope, $http, $location, esriLoader, MonitoringService, $stomp) {
    // $scope.currentPath = $location.path();
    // $scope.getClass = function(path) {
    //     if ($location.path() == path) {
    //         return "active";
    //     } else {
    //         return "";
    //     }
    // }
    esriLoader.require([
        "jquery",
        "esri/Map",
        "esri/views/MapView",
        "esri/layers/GraphicsLayer",
        "esri/layers/MapImageLayer",
        "esri/PopupTemplate",
        "esri/Graphic",
        "esri/geometry/Point",
        "esri/geometry/Polyline",
        "esri/symbols/PictureMarkerSymbol",
        "esri/symbols/SimpleLineSymbol",
        "esri/widgets/ScaleBar",
        "esri/core/watchUtils",
        "esri/geometry/support/webMercatorUtils",

        "dojo/domReady!"
    ], function($, Map, MapView, GraphicsLayer, MapImageLayer, PopupTemplate, Graphic, Point, Polyline,
                PictureMarkerSymbol, SimpleLineSymbol, ScaleBar, watchUtils, webMercatorUtils){

        /********************
         * Create Map and MapView - general element ArcGis(ESRI)
         * https://developers.arcgis.com/javascript/latest/api-reference/esri-views-MapView.html
         *******************/
        var map = new Map({
            basemap: "streets"
        });
        var view = new MapView({
            container: "mapDiv",
            map: map,
            zoom: 11,
            center: [36.227969, 49.988633]
        });
        $scope.view = view;

        /********************
         * Add Scale bar
         *******************/
        var scaleBar = new ScaleBar({
            view: $scope.view,
            unit: "metric"
        });
        $scope.view.ui.add(scaleBar, {position: "bottom-right"});

        /********************
         * Move zoom buttons top-right
         *******************/
        $scope.view.ui.move("zoom", "top-right");


        $scope.view.then(function () {
            $scope.view.popup.dockEnabled = true;
        });

        /***********************************
         * Create Graphic object on the map.
         ***********************************/
        function createGraphicObject(url, latitude, longitude, info) {
            return new Graphic({
                attribute: "text",
                geometry: new Point({
                    longitude: longitude,
                    latitude: latitude
                }),
                symbol: new PictureMarkerSymbol({
                    width: 25,
                    height: 25,
                    url: url
                }),
                popupTemplate: { // autocasts as new PopupTemplate()
                    // title: "Title",
                    content: info
                }
                // popupTemplate: {
                //     title: " Title "
                    // content: [{
                    //     type: "media",
                    //     mediaInfos: [{
                    //         type: "image",
                            // value: {
                            //     sourceURL: url
                            // },
                        //     caption: "255 3 05701 00201"
                        // }]
                    // }]
                // }
            });
        }
        var graphicsLayer = new GraphicsLayer();/*** Add graphic layer for Control Points*/
        map.add(graphicsLayer);
        var graphicsLayerPosts = new GraphicsLayer();/*** Add graphic layer for Posts*/
        map.add(graphicsLayerPosts);

        /***********************************
         *  Event on change zoom.
         ************************************/
        watchUtils.watch($scope.view, "zoom", function (zoom) {});

        /***********************************
         * Event when change represented part of map.
         *
         * X minimum coordinate = Left
         * Y minimum coordinate = Bott
         *
         * X maximum coordinate = Right
         * Y maximum coordinate =  Top
         *
         *
         ************************************/
        watchUtils.watch($scope.view, "stationary", function () {
            if ($scope.view.stationary) {
                if (6 > view.zoom | view.zoom > 21) {
                    if (6 > view.zoom) {
                        $scope.view.zoom = 6;
                    } else {
                        $scope.view.zoom = 21;
                    }
                } else {

                    console.log("Zoom - " + view.zoom);

                    $scope.sendRequest();

                }
            }
        });

        showMonitoringObjects = function showMonitoringObjects(baseStations) {
            graphicsLayer.removeAll();
            // console.log("baseStations.length - " + baseStations.length);
            for (var u = 0; u < baseStations.length; u++) {
                graphicsLayer.add(createGraphicObject("images/" + baseStations[u].imageName,
                    baseStations[u].latitude,
                    baseStations[u].longitude,
                    baseStations[u].info));
            }
        };

        showPosts = function showPosts(posts) {
            graphicsLayerPosts.removeAll();
            // console.log("baseStations.length - " + baseStations.length);
            for (var u = 0; u < posts.length; u++) {
                graphicsLayerPosts.add(createGraphicObject("images/" + posts[u].imageName,
                    posts[u].latitude,
                    posts[u].longitude,
                    posts[u].info));
            }
        };

        getRegionsWithMonitoringStations();
        // getPosts();

        $scope.webMercatorUtils = webMercatorUtils;

    });




    /***********************************
     * Send request to server and after show gets base stations
     ************************************/
    $scope.sendRequest = function () {
        // console.log("Zoom - " + $scope.view.zoom);

        rightTopLatitude = $scope.webMercatorUtils.xyToLngLat($scope.view.extent.xmax, $scope.view.extent.ymax)[1];
        rightTopLongtitude = $scope.webMercatorUtils.xyToLngLat($scope.view.extent.xmax, $scope.view.extent.ymax)[0];
        leftBottomLatitude = $scope.webMercatorUtils.xyToLngLat($scope.view.extent.xmin, $scope.view.extent.ymin)[1];
        leftBottomLongtitude = $scope.webMercatorUtils.xyToLngLat($scope.view.extent.xmin, $scope.view.extent.ymin)[0];
        subscribePostServerSymonaWebSocket();
        getBaseStations(rightTopLatitude, rightTopLongtitude, leftBottomLatitude, leftBottomLongtitude, $scope.view.zoom, $scope.selectedObject.regionIds, $scope.selectedObject.mrmsIds);
        getPosts(rightTopLatitude, rightTopLongtitude, leftBottomLatitude, leftBottomLongtitude, $scope.view.zoom, $scope.selectedObject.regionIds, $scope.selectedObject.mrmsIds);
    };

    /***********************************
     * List Selected Object
     ************************************/
    $scope.selectedObject = {
        regionIds: [],
        mrmsIds: []
    };

    /***********************************
     * Generate List Selected Object
     ************************************/
    $scope.selectObject = function selectObject(item, isRegion, isMRMS) {

        if (isRegion) {
            $scope.selectedObject.regionIds = [];
            for (var i = 0; i < $scope.regions.length; i++) {
                if ($scope.regions[i].id == item.region.id) {
                    if ((document.getElementById($scope.regions[i].id).checked) == false) {
                        $scope.selectedObject.regionIds.push($scope.regions[i].id);
                    } else {
                        for (var m = 0; m < $scope.regions[i].postDTOs.length; m++) {

                            var index = $scope.selectedObject.mrmsIds.indexOf($scope.regions[i].postDTOs[m].name);
                            if (index > -1) {
                                $scope.selectedObject.mrmsIds.splice(index, 1);
                            }

                            document.getElementById($scope.regions[i].postDTOs[m].id).checked = false;
                        }
                    }
                } else if ((document.getElementById($scope.regions[i].id).checked)) {
                        $scope.selectedObject.regionIds.push($scope.regions[i].id);
                }
            }
            sendToSocket({func: "SUBSC_CONTROL_POINT"});//subscribe to all control points
        }

        if (isMRMS) {
            $scope.selectedObject.mrmsIds = [];
            for (var i = 0; i < $scope.regions.length; i++) {
                for (var j = 0; j < $scope.selectedObject.regionIds.length; j++) {
                    if ($scope.regions[i].id == $scope.selectedObject.regionIds[j]) {
                        for (var m = 0; m < $scope.regions[i].postDTOs.length; m++) {
                            if ($scope.regions[i].postDTOs[m].id == item.mrms.id) {
                                if ((document.getElementById($scope.regions[i].postDTOs[m].id).checked) == false) {
                                    $scope.selectedObject.mrmsIds.push($scope.regions[i].postDTOs[m].id);
                                }
                            } else {
                                if ((document.getElementById($scope.regions[i].postDTOs[m].id).checked)) {
                                    $scope.selectedObject.mrmsIds.push($scope.regions[i].postDTOs[m].id);
                                }
                            }
                        }
                    }
                }
            }
            $scope.sendRequest();
        }
        // console.log("Selected Regions ID's - " + $scope.selectedObject.regionIds + " || Selected MRMS name - " + $scope.selectedObject.mrmsIds);
    };

    function subscribePostServerSymonaWebSocket() {
        var data = {
            func: "SUBSC_POST",
            from: {
                lat: $scope.webMercatorUtils.xyToLngLat($scope.view.extent.xmin, $scope.view.extent.ymin)[1],
                lon: $scope.webMercatorUtils.xyToLngLat($scope.view.extent.xmin, $scope.view.extent.ymin)[0]
            },
            to: {
                lat: $scope.webMercatorUtils.xyToLngLat($scope.view.extent.xmax, $scope.view.extent.ymax)[1],
                lon: $scope.webMercatorUtils.xyToLngLat($scope.view.extent.xmax, $scope.view.extent.ymax)[0]
            },
        };
        if ($scope.selectedObject.mrmsIds.length > 0) {
            data["posts"] = $scope.selectedObject.mrmsIds.map(String);
        }
        sendToSocket(data);//Subscribe to WebSocket Symona server. SUBSC_POST
    }

    /***********************************
     * Function Get data (BaseStations) from server.
     ************************************/
    function getBaseStations (rightTopLatitude, rightTopLongtitude, leftBottomLatitude, leftBottomLongtitude, zoom,
                regionIds, mrmsIds) {
        MonitoringService.getBaseStation(rightTopLatitude, rightTopLongtitude, leftBottomLatitude, leftBottomLongtitude, zoom, regionIds, mrmsIds)
            .then(function success(response) {
                    if ($scope.view.zoom === zoom) {
                        showMonitoringObjects(response.data);
                    } else {
                        //recursion for getting actual Control Points for zoom.
                        getBaseStations($scope.webMercatorUtils.xyToLngLat($scope.view.extent.xmax, $scope.view.extent.ymax)[1],
                            $scope.webMercatorUtils.xyToLngLat($scope.view.extent.xmax, $scope.view.extent.ymax)[0],
                            $scope.webMercatorUtils.xyToLngLat($scope.view.extent.xmin, $scope.view.extent.ymin)[1],
                            $scope.webMercatorUtils.xyToLngLat($scope.view.extent.xmin, $scope.view.extent.ymin)[0],
                            Math.trunc($scope.view.zoom));
                        console.log("Recursion!");
                    }
                },
                function error(response) {
                    $scope.message = '';
                    if (response.status === 404) {
                        console.log("BaseStation not found!");
                    }
                    else {
                        console.log("Error getting BaseStation!");
                    }
                });
    }

    /***********************************
     * Function Get data (Posts) from server.
     ************************************/
    function getPosts (rightTopLatitude, rightTopLongtitude, leftBottomLatitude, leftBottomLongtitude, zoom,
                regionIds, mrmsIds) {
        MonitoringService.getPostList(rightTopLatitude, rightTopLongtitude, leftBottomLatitude, leftBottomLongtitude, zoom, regionIds, mrmsIds)
            .then(function success(response) {
                    showPosts(response.data);
                },
                function error(response) {
                    $scope.message = '';
                    if (response.status === 404) {
                        console.log("BaseStation not found!");
                    }
                    else {
                        console.log("Error getting BaseStation!");
                    }
                });
    }

    /***********************************
     *  Get data (Region's - MRMSs - BaseStations) from server.
     ************************************/
    function getRegionsWithMonitoringStations () {
        MonitoringService.getRegions()
            .then(function success(response) {
                    $scope.regions = response.data;
                    $scope.mobileRadioMonitoringStations = response.data[0].postDTOs;

                    var total = 0;
                    angular.forEach(response.data[0].postDTOs[0].rserviceDTOs, function(rserviceDTO){
                        total = rserviceDTO.count + total;
                    });
                $scope.totalRservice = total;
                },
                function error (response) {
                    $scope.message = '';
                    if (response.status === 404){
                        console.log("Regions not found!");
                    }
                    else {
                        console.log("Error getting regions!");
                    }
                });
    }

        // Stomp websocket client
        // // $scope.myres = [];
        //
        // $stomp.connect('http://localhost:8080/monitoring-websocket', {})
        //     .then(function (frame) {
        //         var subscription = $stomp.subscribe('/topic/monitoring',
        //             function (payload, headers, res) {
        //                 // $scope.myresTest = payload;
        //                 // $scope.$apply($scope.myresTest);
        //             });
        //
        //         $stomp.send('/app/monitoring', '');
        //     });

        /*********************************************************************************************************
         *  Update Monitoring Object.
         **********************************************************************************************************/
        function updateMonitoringObject (object) {
            rightTopLatitude = $scope.webMercatorUtils.xyToLngLat($scope.view.extent.xmax, $scope.view.extent.ymax)[1];
            rightTopLongtitude = $scope.webMercatorUtils.xyToLngLat($scope.view.extent.xmax, $scope.view.extent.ymax)[0];
            leftBottomLatitude = $scope.webMercatorUtils.xyToLngLat($scope.view.extent.xmin, $scope.view.extent.ymin)[1];
            leftBottomLongtitude = $scope.webMercatorUtils.xyToLngLat($scope.view.extent.xmin, $scope.view.extent.ymin)[0];

            var updatedObject = JSON.parse(object);
            if (updatedObject.type === "POST_STATUS") {
                MonitoringService.updatePostStatus(updatedObject)
                    .then(function success(response) {
                            //todo update post status for one post.
                            if (updatedObject.status === "ONLINE") {
                                $scope.regions[0].postDTOs[0].iconName = "backCar.png";
                            }
                            if (updatedObject.status === "OFFLINE") {
                                $scope.regions[0].postDTOs[0].iconName = "greenCar.png";
                            }
                        },
                        function error(response) {
                            $scope.message = '';
                            if (response.status === 404) {
                                console.log("404 - Post status update.");
                            }
                            else {
                                console.log("Error Post status update.");
                            }
                        });
            } else if (updatedObject.type === "POST_LOCATION") {
                MonitoringService.updatePostLocation(rightTopLatitude, rightTopLongtitude, leftBottomLatitude, leftBottomLongtitude, $scope.view.zoom, updatedObject)
                    .then(function success(response) {
                            if (response.data.length > 0) {
                                showPosts(response.data);
                            }
                        },
                        function error(response) {
                            $scope.message = '';
                            if (response.status === 404) {
                                console.log("404 Post Location update.");
                            }
                            else {
                                console.log("Error Post Location update.");
                            }
                        });
            } else if (updatedObject.type === "POST_CONTROL_POINT_STATUS") {
                MonitoringService.updatePostControlPointStatus(rightTopLatitude, rightTopLongtitude, leftBottomLatitude, leftBottomLongtitude, $scope.view.zoom, updatedObject)
                    .then(function success(response) {
                            showMonitoringObjects(response.data);
                        },
                        function error(response) {
                            $scope.message = '';
                            if (response.status === 404) {
                                console.log("404 Post Control Point Status update.");
                            }
                            else {
                                console.log("Error Post Control Point Status update.");
                            }
                        });
            } else {
                console.log("Response Server Symona have not type(POST_CONTROL_POINT_STATUS, POST_LOCATION, POST_STATUS).");
            }

        }

        /***********************************************************************************************************
         * Client Websocket for Symona server.
         **********************************************************************************************************/
        var socket;
        var createSocket = function() {
            // var res = new WebSocket('ws://192.168.1.88:10102/');
            var res = new WebSocket('ws://0.0.0.0:10102/');
            res.onopen = function() {
                console.log("Connect Websocket to Symona server.");
            };

            res.onclose = function(AEvent) {
                console.log("Close Websocket to Symona server.");
                socket = null;
            };

            res.onmessage = function(AEvent) {
                updateMonitoringObject(AEvent.data);
                console.log("Websocket to Symona server response - " + AEvent.data);
                // console.log("Websocket to Symona server TYPE response - " + JSON.parse(AEvent.data).type);
            };

            res.onerror = function(error) {
                console.log("Error Websocket to Symona server. Target URL - \"" + error.target.url + "\". Message - " + error.message);
            };

            return res;
        };

        socket = createSocket();

        var closeWebSocket = function() {
            socket.close();
            socket = null;
        };

        var sendToSocket = function(data) {
            if (typeof data === "object") {
                data = JSON.stringify(data);
            }
            if (socket != null && socket.readyState == 1) {
                socket.send(data);
            }
        };
    }]);