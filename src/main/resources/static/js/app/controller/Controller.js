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
        "esri/Map",
        "esri/views/MapView",
        "esri/layers/MapImageLayer",

        "esri/PopupTemplate",
        "esri/Graphic",
        "esri/geometry/Point",


        "dojo/domReady!"
    ], function(Map, MapView, MapImageLayer, PopupTemplate, Graphic, Point){

        var map = new Map({
            basemap: "streets",

        });
        /**********************
         * Create a point graphic
         **********************/
            // First create a point geometry
        var point = new Point({
                longitude: 36.2304,
                latitude: 49.9935
            });

        // Create a symbol for drawing the point
        var textSymbol = {
            type: "text", // autocasts as new TextSymbol()
            color: "#7A003C",
            text: "\ue61d", // esri-icon-map-pin
            font: { // autocasts as new Font()
                size: 30,
                family: "CalciteWebCoreIcons"
            }
        };

        // Create a graphic and add the geometry and symbol to it
        var pointGraphic = new Graphic({
            geometry: point,
            symbol: textSymbol
        });


        var view = new MapView({
            container: "mapDiv",
            map: map,
            zoom: 14,
            center: [36.2304, 49.9935]
        });
        // Add the graphics to the view's graphics layer
        view.graphics.add(pointGraphic);




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
