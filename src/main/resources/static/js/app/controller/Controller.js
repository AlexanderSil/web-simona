
simonaApp.controller('MainController', ['$scope', '$http', '$location', 'esriLoader', 'MonitoringService', function ($scope, $http, $location, esriLoader, MonitoringService) {
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
            var contentInfo = "";
            if (info == null) {

            } else {
                contentInfo = "<p class='popupTemplateContentGrey'><b>{MARRIEDRATE} "+ info[0] +" </b></p>" +
                "<p class='popupTemplateContentGreen'><b>{MARRIEDRATE} "+ info[1] +" </b></p>" +
                "<p class='popupTemplateContentYellow'><b>{MARRIEDRATE} "+ info[2] +" </b></p>"
            }

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
                    content: contentInfo
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
        var graphicsLayer = new GraphicsLayer();/*** Add graphic layer*/
        map.add(graphicsLayer);

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
                    // console.log("Right top. Latitude - " + rightTopLatitude + " ; Longtitude - " + rightTopLongtitude);
                    // console.log("Left Bottom. Latitude - " + leftBottomLatitude + " ; Longtitude - " + leftBottomLongtitude);
                    // console.log("View extent (when change represent part): xmax- " + view.extent.xmax + "; xmin- " + view.extent.xmin + "; ymax- " + view.extent.ymax + "; ymin- " + view.extent.ymin + "; ");
                    // console.log("Right top. XY max coordinate - " + webMercatorUtils.xyToLngLat(view.extent.xmax, view.extent.ymax));
                    // console.log("Left Bottom. XY min coordinate - " + webMercatorUtils.xyToLngLat(view.extent.xmin, view.extent.ymin));
                    // rightTopLatitude = webMercatorUtils.xyToLngLat($scope.view.extent.xmax, $scope.view.extent.ymax)[1];
                    // rightTopLongtitude = webMercatorUtils.xyToLngLat($scope.view.extent.xmax, $scope.view.extent.ymax)[0];
                    // leftBottomLatitude = webMercatorUtils.xyToLngLat($scope.view.extent.xmin, $scope.view.extent.ymin)[1];
                    // leftBottomLongtitude = webMercatorUtils.xyToLngLat($scope.view.extent.xmin, $scope.view.extent.ymin)[0];
                    //
                    // getBaseStations(rightTopLatitude, rightTopLongtitude, leftBottomLatitude, leftBottomLongtitude, $scope.view.zoom);
                    $scope.sendRequest();

                }
            }
        });

        showMonitoringObjects = function showMonitoringObjects(baseStations) {
            graphicsLayer.removeAll();
            console.log("baseStations.length - " + baseStations.length);
            for (var u = 0; u < baseStations.length; u++) {
                graphicsLayer.add(createGraphicObject("images/" + baseStations[u].imageName,
                    baseStations[u].latitude,
                    baseStations[u].longitude,
                    baseStations[u].info));
            }
        };

        getRegionsWithMonitoringStations();

        $scope.webMercatorUtils = webMercatorUtils;

    });




    /***********************************
     * Send request to server and after show gets base stations
     ************************************/
    $scope.sendRequest = function () {
        console.log("Zoom - " + $scope.view.zoom);

        rightTopLatitude = $scope.webMercatorUtils.xyToLngLat($scope.view.extent.xmax, $scope.view.extent.ymax)[1];
        rightTopLongtitude = $scope.webMercatorUtils.xyToLngLat($scope.view.extent.xmax, $scope.view.extent.ymax)[0];
        leftBottomLatitude = $scope.webMercatorUtils.xyToLngLat($scope.view.extent.xmin, $scope.view.extent.ymin)[1];
        leftBottomLongtitude = $scope.webMercatorUtils.xyToLngLat($scope.view.extent.xmin, $scope.view.extent.ymin)[0];

        getBaseStations(rightTopLatitude, rightTopLongtitude, leftBottomLatitude, leftBottomLongtitude, $scope.view.zoom, $scope.selectedObject.regionIds, $scope.selectedObject.mrmsIds);
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
                        for (var m = 0; m < $scope.regions[i].mobileRadioMonitoringStations.length; m++) {

                            var index = $scope.selectedObject.mrmsIds.indexOf($scope.regions[i].mobileRadioMonitoringStations[m].name);
                            if (index > -1) {
                                $scope.selectedObject.mrmsIds.splice(index, 1);
                            }

                            document.getElementById($scope.regions[i].mobileRadioMonitoringStations[m].name).checked = false;
                        }
                    }
                } else if ((document.getElementById($scope.regions[i].id).checked)) {
                        $scope.selectedObject.regionIds.push($scope.regions[i].id);
                }
            }
            console.log("Selected Regions ID's - " + $scope.selectedObject.regionIds + " || Selected MRMS name - " + $scope.selectedObject.mrmsIds);
        }
        if (isMRMS) {
            $scope.selectedObject.mrmsIds = [];
            for (var i = 0; i < $scope.regions.length; i++) {
                for (var j = 0; j < $scope.selectedObject.regionIds.length; j++) {
                    if ($scope.regions[i].id == $scope.selectedObject.regionIds[j]) {
                        for (var m = 0; m < $scope.regions[i].mobileRadioMonitoringStations.length; m++) {
                            if ($scope.regions[i].mobileRadioMonitoringStations[m].name == item.mrms.name) {
                                if ((document.getElementById($scope.regions[i].mobileRadioMonitoringStations[m].name).checked) == false) {
                                    $scope.selectedObject.mrmsIds.push($scope.regions[i].mobileRadioMonitoringStations[m].name);
                                }
                            } else {
                                if ((document.getElementById($scope.regions[i].mobileRadioMonitoringStations[m].name).checked)) {
                                    $scope.selectedObject.mrmsIds.push($scope.regions[i].mobileRadioMonitoringStations[m].name);
                                }
                            }
                        }
                    }
                }
            }
            console.log("Selected Regions ID's - " + $scope.selectedObject.regionIds + " || Selected MRMS name - " + $scope.selectedObject.mrmsIds);
        }
        $scope.sendRequest();
    };

    /***********************************
     * Function Get data (BaseStations) from server.
     ************************************/
    function getBaseStations (rightTopLatitude, rightTopLongtitude, leftBottomLatitude, leftBottomLongtitude, zoom,
                regionIds, mrmsIds) {
        MonitoringService.getBaseStation(rightTopLatitude, rightTopLongtitude, leftBottomLatitude, leftBottomLongtitude, zoom, regionIds, mrmsIds)
            .then(function success(response) {
                    showMonitoringObjects(response.data);
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
                    $scope.mobileRadioMonitoringStations = response.data[0].mobileRadioMonitoringStations;
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



}]);

// simonaApp.controller('MonitoringController', function ($scope, $filter, esriLoader) {
//
    // esriLoader.require(["esri/Map", "esri/views/MapView", "dojo/domReady!"], function(Map, MapView){
    //
    //     var map = new Map({
    //         basemap: "streets"
    //     });
    //
    //     var view = new MapView({
    //         container: "mapDiv",
    //         map: map,
    //         zoom: 14,
    //         center: [36.2304, 49.9935]
    //     });
    // });
//
// });
//
// simonaApp.controller('AdminController', function($scope, $filter) {
//     $scope.clickfunction = function(){
//         var time = $filter('date')(new Date(),'yyyy-MM-dd HH:mm:ss Z');
//         $scope.welcome = "Time = " + time;
//     }
// });
//
// simonaApp.controller('ManagementController', function ($scope, $filter) {
//     $scope.clickfunction = function(){
//         var time = $filter('date')(new Date(),'yyyy-MM-dd HH:mm:ss Z');
//         $scope.welcome = "Time = " + time;
//     }
// });
//
// simonaApp.controller('LoginController',
//     function ($scope, UserService, $rootScope, $location, $cookieStore, USER_ROLES) {
/*
    $scope.rmReadonly = function() {
        $("#username").attr("readonly", false);
    };

    $scope.login = function () {
        if ($rootScope.user === undefined) {
            // checks the inputs to add the data to the model in case of autofill
            $('#loginForm').checkAndTriggerAutoFillEvent();

            UserService.authenticate("username=" + $('#loginForm').get('0')[0].value + "&password=" + $('#loginForm').get('0')[1].value,
                function (tokenObject) {
                    var authToken = tokenObject.token;
                    $rootScope.authToken = authToken;
                    $rootScope.logoutFlag = true;
                    $cookieStore.put('authToken', authToken);
                    var user = tokenObject.authUser;
                    $rootScope.user = user;
                    if ($rootScope.hasRole(USER_ROLES.SUPER_ADMIN)) {
                        $location.path("/management");
                        $rootScope.logoutFlag = false;
                    }
                    else if ($rootScope.hasRole(USER_ROLES.USER)) {
                        $location.path("/");
                        $rootScope.logoutFlag = false;
                    }
                    else {
                        $location.path("/");
                        $rootScope.logoutFlag = false;
                    }
                    $rootScope.$broadcast('$login');
                },
                function (error) {
                    var key = error.data.exceptionClassName;
                    var errorString = $rootScope.dictionary[key];
                    if (error.status == 401) {
                        $rootScope.errorL =  $rootScope.dictionary['org.springframework.security.authentication.BadCredentialsException'];
                    } if (error.status == 409) {
                        $rootScope.errorL =  $rootScope.dictionary['com.siliconnile.eshtapay.operators.exception.LoginsExceededException'];
                    }
                }
            );

        } else {
            $location.path("/");
        }
    };
 */
// });
