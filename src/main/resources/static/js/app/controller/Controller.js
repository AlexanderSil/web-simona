
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
        };
        var graphicsLayer = new GraphicsLayer();/*** Add graphic layer for Control Points*/
        // $scope.graphicsLayerTest = graphicsLayer;
        map.add(graphicsLayer);
        var graphicsLayerPosts = new GraphicsLayer();/*** Add graphic layer for Posts*/
        // $scope.graphicsLayerPostsTest = graphicsLayerPosts;
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

                    $scope.getActualPostsAndBaseStations();

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

        showNewPosts = function showPosts(posts) {
            graphicsLayerPosts.removeAll();
            // console.log("baseStations.length - " + baseStations.length);
            for (var u = 0; u < posts.length; u++) {
                if (posts[u].imageName != null && posts[u].lastPostTraces.latitude != null
                    && posts[u].lastPostTraces.longitude != null && posts[u].info != null) {
                                $scope.post = createGraphicObject("images/" + posts[u].imageName,
                                    posts[u].lastPostTraces.latitude,
                                    posts[u].lastPostTraces.longitude,
                                    posts[u].info);
                }
                graphicsLayerPosts.add($scope.post);
            }
        };

        removePosts = function removePosts() {
            graphicsLayerPosts.remove($scope.post);
        };

        getRegionsWithMonitoringStations();

        $scope.webMercatorUtils = webMercatorUtils;

    });




    /***********************************
     * Send request to server and after show actual base stations and posts
     ************************************/
    $scope.getActualPostsAndBaseStations = function () {
        // console.log("Zoom - " + $scope.view.zoom);

        rightTopLatitude = $scope.webMercatorUtils.xyToLngLat($scope.view.extent.xmax, $scope.view.extent.ymax)[1];
        rightTopLongtitude = $scope.webMercatorUtils.xyToLngLat($scope.view.extent.xmax, $scope.view.extent.ymax)[0];
        leftBottomLatitude = $scope.webMercatorUtils.xyToLngLat($scope.view.extent.xmin, $scope.view.extent.ymin)[1];
        leftBottomLongtitude = $scope.webMercatorUtils.xyToLngLat($scope.view.extent.xmin, $scope.view.extent.ymin)[0];

        getActualBaseStations($scope.view.zoom, $scope.selectedObject.mrmsIds, rightTopLatitude, rightTopLongtitude, leftBottomLatitude, leftBottomLongtitude);
        getActualPosts($scope.view.zoom, $scope.selectedObject.mrmsIds);
    };


    /***********************************
     * Load Posts and Control Points from data base.
     ************************************/
    $scope.loadDataFromDB = function () {
        MonitoringService.getPostsFromDB($scope.view.zoom, $scope.selectedObject.mrmsIds)
            .then(function success(response) {
                    showNewPosts(response.data);

                    rightTopLatitude = $scope.webMercatorUtils.xyToLngLat($scope.view.extent.xmax, $scope.view.extent.ymax)[1];
                    rightTopLongtitude = $scope.webMercatorUtils.xyToLngLat($scope.view.extent.xmax, $scope.view.extent.ymax)[0];
                    leftBottomLatitude = $scope.webMercatorUtils.xyToLngLat($scope.view.extent.xmin, $scope.view.extent.ymin)[1];
                    leftBottomLongtitude = $scope.webMercatorUtils.xyToLngLat($scope.view.extent.xmin, $scope.view.extent.ymin)[0];
                    MonitoringService.getBaseStationFromDB($scope.view.zoom, $scope.selectedObject.mrmsIds, rightTopLatitude, rightTopLongtitude, leftBottomLatitude, leftBottomLongtitude)
                        .then(function success(response) {
                                showMonitoringObjects(response.data);
                            },
                            function error(response) {
                                $scope.message = '';
                                if (response.status === 404) {
                                    console.log("BaseStation not found in DB!");
                                }
                                else {
                                    console.log("Error getting BaseStation from DB!");
                                }
                            });

                },
                function error(response) {
                    $scope.message = '';
                    if (response.status === 404) {
                        console.log("Posts not found in DB!");
                    }
                    else {
                        console.log("Error getting Posts from DB!");
                    }
                });

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
                        for (var m = 0; m < $scope.regions[i].postDTOTemps.length; m++) {

                            var index = $scope.selectedObject.mrmsIds.indexOf($scope.regions[i].postDTOTemps[m].name);
                            if (index > -1) {
                                $scope.selectedObject.mrmsIds.splice(index, 1);
                            }

                            document.getElementById($scope.regions[i].postDTOTemps[m].id).checked = false;
                        }
                    }
                } else if ((document.getElementById($scope.regions[i].id).checked)) {
                        $scope.selectedObject.regionIds.push($scope.regions[i].id);
                }
            }

        }

        if (isMRMS) {
            $scope.selectedObject.mrmsIds = [];
            for (var i = 0; i < $scope.regions.length; i++) {
                for (var j = 0; j < $scope.selectedObject.regionIds.length; j++) {
                    if ($scope.regions[i].id == $scope.selectedObject.regionIds[j]) {
                        for (var m = 0; m < $scope.regions[i].postDTOTemps.length; m++) {
                            if ($scope.regions[i].postDTOTemps[m].id == item.mrms.id) {
                                if ((document.getElementById($scope.regions[i].postDTOTemps[m].id).checked) == false) {
                                    $scope.selectedObject.mrmsIds.push($scope.regions[i].postDTOTemps[m].id);
                                }
                            } else {
                                if ((document.getElementById($scope.regions[i].postDTOTemps[m].id).checked)) {
                                    $scope.selectedObject.mrmsIds.push($scope.regions[i].postDTOTemps[m].id);
                                }
                            }
                        }
                    }
                }
            }
            $scope.loadDataFromDB();//get data from data base.
            if ($scope.selectedObject.mrmsIds.length != 0) {
                subscribePostServerSymonaWebSocket();
                sendToSocket({func: "SUBSC_CONTROL_POINT"});//subscribe to all control points
            } else {
                getRegionsWithMonitoringStations();
            }

        }
        // console.log("Selected Regions ID's - " + $scope.selectedObject.regionIds + " || Selected MRMS name - " + $scope.selectedObject.mrmsIds);
    };


    /***********************************
     * Function Get data (BaseStations) from server.
     ************************************/
    function getActualBaseStations (zoom, mrmsIds, rightTopLatitude, rightTopLongtitude, leftBottomLatitude, leftBottomLongtitude) {
        MonitoringService.getActualControlPoints(zoom, mrmsIds, rightTopLatitude, rightTopLongtitude, leftBottomLatitude, leftBottomLongtitude)
            .then(function success(response) {
                    if ($scope.view.zoom === zoom) {
                        showMonitoringObjects(response.data);
                    } else {
                        //recursion for getting actual Control Points for zoom.
                        getActualBaseStations(
                            Math.trunc($scope.view.zoom),
                            $scope.selectedObject.mrmsIds,
                            $scope.webMercatorUtils.xyToLngLat($scope.view.extent.xmax, $scope.view.extent.ymax)[1],
                            $scope.webMercatorUtils.xyToLngLat($scope.view.extent.xmax, $scope.view.extent.ymax)[0],
                            $scope.webMercatorUtils.xyToLngLat($scope.view.extent.xmin, $scope.view.extent.ymin)[1],
                            $scope.webMercatorUtils.xyToLngLat($scope.view.extent.xmin, $scope.view.extent.ymin)[0]
                            );
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
    function getActualPosts (zoom, mrmsIds) {
        MonitoringService.getActualPostList(zoom, mrmsIds)
            .then(function success(response) {
                    showNewPosts(response.data);
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
     *  Get data (Region's - MRMSs - BaseStations) from DB.
     ************************************/
    function getRegionsWithMonitoringStations() {

        $scope.regions = null;

        MonitoringService.getRegions()
            .then(function success(response) {
                    $scope.regions = response.data;
                },
                function error (response) {
                    $scope.message = '';
                    if (response.status === 404){
                        console.log("Regions or posts not found!");
                    }
                    else {
                        console.log("Error getting regions and posts info!");
                    }
                });
    }

        window.setInterval(function(){
            if ($scope.selectedObject.mrmsIds.length > 0) {
                getActualBaseStations(
                    Math.trunc($scope.view.zoom),
                    $scope.selectedObject.mrmsIds,
                    $scope.webMercatorUtils.xyToLngLat($scope.view.extent.xmax, $scope.view.extent.ymax)[1],
                    $scope.webMercatorUtils.xyToLngLat($scope.view.extent.xmax, $scope.view.extent.ymax)[0],
                    $scope.webMercatorUtils.xyToLngLat($scope.view.extent.xmin, $scope.view.extent.ymin)[1],
                    $scope.webMercatorUtils.xyToLngLat($scope.view.extent.xmin, $scope.view.extent.ymin)[0])
                getActualPostsInfo();//update info in posts after update control points.
                getActualPosts($scope.view.zoom, $scope.selectedObject.mrmsIds);
            }

        }, 5000);

    /***********************************
     *  Update Posts Info.
     ************************************/
    function getActualPostsInfo() {
        MonitoringService.actualRegionsInfo()
            .then(function success(response) {
                    angular.forEach($scope.regions[0].postDTOTemps[0].rserviceDTOs, function(rserviceDTO){
                        if (response.data[0].postDTOTemps != null) {
                            angular.forEach(response.data[0].postDTOTemps[0].rserviceDTOs, function(newRserviceDTO){
                                if (rserviceDTO.name == newRserviceDTO.name) {
                                    rserviceDTO.detectedcount = newRserviceDTO.detectedcount;
                                    rserviceDTO.measuredcount = newRserviceDTO.measuredcount;
                                }
                            });
                        }
                    });
                },
                function error (response) {
                    $scope.message = '';
                    if (response.status === 404){
                        console.log("Status - " + response.status + ". Updated info for posts.");
                    }
                    else {
                        console.log("Error updated info posts!");
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
                                $scope.regions[0].postDTOTemps[0].iconName = "greenCar.png";
                            }
                            if (updatedObject.status === "OFFLINE") {
                                $scope.regions[0].postDTOTemps[0].iconName = "blackCar.png";
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
                MonitoringService.updatePostLocation($scope.view.zoom, $scope.selectedObject.mrmsIds, updatedObject)
                    .then(function success(response) {
                            if (response.data.length > 0) {
                                showNewPosts(response.data);//todo change to showNewPosts
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
                MonitoringService.updateControlPointStatus($scope.view.zoom, $scope.selectedObject.mrmsIds, updatedObject, rightTopLatitude, rightTopLongtitude, leftBottomLatitude, leftBottomLongtitude)
                    .then(function success(response) {
                            showMonitoringObjects(response.data);
                            // getRegionsWithMonitoringStations();//update info in posts after update control points.
                            getActualPostsInfo();//update info in posts after update control points.
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
            var res = new WebSocket('ws://192.168.1.88:10102/');
            // var res = new WebSocket('ws://0.0.0.0:10102/');
            // var res = new WebSocket('ws://127.0.0.1:10102/');
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
    }]);