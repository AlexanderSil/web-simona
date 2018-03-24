
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

        "dojo/domReady!"
    ], function($, Map, MapView, GraphicsLayer, MapImageLayer, PopupTemplate, Graphic, Point, Polyline,
                PictureMarkerSymbol, SimpleLineSymbol, ScaleBar, watchUtils){

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
            zoom: 14,
            center: [36.227969, 49.988633]
        });

        /********************
         * Add Scale bar
         *******************/
        var scaleBar = new ScaleBar({
            view: view,
            unit: "metric"
        });
        view.ui.add(scaleBar, {position: "bottom-right"});

        /********************
         * Move zoom buttons top-right
         *******************/
        view.ui.move("zoom", "top-right");


        view.then(function () {
            view.popup.dockEnabled = true;
        });

        /*********************
         * Create Graphic object on the map.
         *********************/
        function getImage(url, latitude, longitude) {
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
                popupTemplate: { // autocasts as new PopupTemplate()
                    title: "Title",
                    content: "255 3 05701 00201"
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



        graphicsLayer.add(getImage("images/pointer.png", 49.988633, 36.227969));
        graphicsLayer.add(getImage("images/1.png", 49.988982, 36.222230));
        graphicsLayer.add(getImage("images/2.png", 49.991795, 36.223775));
        graphicsLayer.add(getImage("images/3.png", 49.993422, 36.235448));
        graphicsLayer.add(getImage("images/4.png", 49.985502, 36.224590));
        graphicsLayer.add(getImage("images/5.png", 49.985722, 36.227379));
        graphicsLayer.add(getImage("images/6.png", 49.992371, 36.230297));
        graphicsLayer.add(getImage("images/7.png", 49.992065, 36.229610));



        /*********************
         * Get distance between two points (Point.between(Point))
         *********************/
        var point1 = getImage("images/7.png", 49.992065, 36.229610);
        var point2 = getImage("images/6.png", 49.992371, 36.230297);
        point1.geometry.distance(point2.geometry)


        /***********************************
         *  Event on change zoom.
         ************************************/
        watchUtils.watch(view, "zoom", function (zoom) {
            if (zoom % 1 !== 0) {/** zoom in progress */}
            else {/**  finished zoom */
                console.log("Zoom is now - " + zoom);
            }
        });
    });



    /***********************************
     *  Get data from server.
     ************************************/
    MonitoringService.getRegions()
        .then(function success(response) {
                $scope.regions = response.data;
                $scope.mrms = response.data[0].mobileRadioMonitoringStations;
                $scope.baseStations = response.data[0].mobileRadioMonitoringStations[0].baseStations;
                // console.log("MonitoringService.getRegions data - " + JSON.parse(response.data));
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

}]);

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
