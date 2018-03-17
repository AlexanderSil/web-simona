simonaApp.controller('MainController', function ($scope, $location, esriLoader) {
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


        "dojo/domReady!"
    ], function($, Map, MapView, GraphicsLayer, MapImageLayer, PopupTemplate,
                Graphic, Point, Polyline, PictureMarkerSymbol, SimpleLineSymbol){

        var map = new Map({
            basemap: "streets"

        });



        var view = new MapView({
            container: "mapDiv",
            map: map,
            zoom: 14,
            center: [36.227969, 49.988633]
        });

        view.then(function () {
            view.popup.dockEnabled = true;
        });

        function getPhoto(url, latitude, longitude) {
            return new Graphic({
                attribute: "photo",
                geometry: new Point({
                    longitude: longitude,
                    latitude: latitude
                }),
                symbol: new PictureMarkerSymbol({
                    width: 25,
                    height: 25,
                    url: url
                }),
                popupTemplate: {
                    title: " Title "
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
                }
            });
        }


        var graphicsLayer = new GraphicsLayer();

        map.add(graphicsLayer);

        graphicsLayer.add(getPhoto("images/pointer.png", 49.988633, 36.227969));
        graphicsLayer.add(getPhoto("images/1.png", 49.988982, 36.222230));
        graphicsLayer.add(getPhoto("images/2.png", 49.991795, 36.223775));
        graphicsLayer.add(getPhoto("images/3.png", 49.993422, 36.235448));
        graphicsLayer.add(getPhoto("images/4.png", 49.985502, 36.224590));
        graphicsLayer.add(getPhoto("images/5.png", 49.985722, 36.227379));
        graphicsLayer.add(getPhoto("images/6.png", 49.992371, 36.230297));
        graphicsLayer.add(getPhoto("images/7.png", 49.992065, 36.229610));

        /**********************
         * Create a point graphic Tracking
         **********************/
            // First create a point geometry
        var pointTrack = new Point({
                longitude: 36.2304,
                latitude: 49.9935
            });
        // Create a symbol for drawing the point
        var textSymbolTracking = {
            type: "text", // autocasts as new TextSymbol()
            color: "#007a0a",
            text: "\ue69b", // esri-icon-tracking (https://developers.arcgis.com/javascript/latest/guide/esri-icon-font/index.html#esri-icon-fonts)
            font: { // autocasts as new Font()
                size: 30,
                family: "CalciteWebCoreIcons"
            }
        };
        // Create a graphic and add the geometry and symbol to it
        var pointGraphicTrack = new Graphic({
            geometry: pointTrack,
            symbol: textSymbolTracking
        });

        // Add the graphics to the view's graphics layer
        view.graphics.add(pointGraphicTrack);
        $scope.mapView = view;

    });

















    $scope.clickfunctionZoom4 = function(){
        $scope.mapView.zoom = 4;
    };
    $scope.clickfunctionZoom14 = function(){
        $scope.mapView.zoom = 14;
    };
});

simonaApp.controller('MonitoringController', function ($scope, $filter, esriLoader) {

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

});

simonaApp.controller('AdminController', function($scope, $filter) {
    $scope.clickfunction = function(){
        var time = $filter('date')(new Date(),'yyyy-MM-dd HH:mm:ss Z');
        $scope.welcome = "Time = " + time;
    }
});

simonaApp.controller('ManagementController', function ($scope, $filter) {
    $scope.clickfunction = function(){
        var time = $filter('date')(new Date(),'yyyy-MM-dd HH:mm:ss Z');
        $scope.welcome = "Time = " + time;
    }
});

simonaApp.controller('LoginController',
    function ($scope, UserService, $rootScope, $location, $cookieStore, USER_ROLES) {
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
});
