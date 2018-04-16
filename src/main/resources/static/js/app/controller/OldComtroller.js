
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
            zoom: 11,
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



        // graphicsLayer.add(getImage("images/pointer.png", 49.988633, 36.227969));
        // graphicsLayer.add(getImage("images/grey_yellow.png", 49.988982, 36.222230));
        // graphicsLayer.add(getImage("images/grey_green.png", 49.991795, 36.223775));
        // graphicsLayer.add(getImage("images/yellow_green.png", 49.993422, 36.235448));
        // graphicsLayer.add(getImage("images/grey_yellow_green.png", 49.985502, 36.224590));
        // graphicsLayer.add(getImage("images/grey.png", 49.985722, 36.227379));

        // graphicsLayer.add(getImage("images/yellow.png", 49.992371, 36.230297));
        // graphicsLayer.add(getImage("images/green.png", 49.992065, 36.229610));





        /***********************************
         *  Event on change zoom.
         ************************************/
        watchUtils.watch(view, "zoom", function (zoom) {
            if (zoom % 1 !== 0) {/** zoom in progress */}
            else {/**  finished zoom */
            $scope.curentZoom = zoom;
                if (6 > zoom | zoom > 21) {
                    if (6 > zoom) {
                        view.zoom = 6;
                    } else {
                        view.zoom = 21;
                    }
                } else {
                    console.log("View extent: xmax- " + view.extent.xmax + "; xmin- " + view.extent.xmin + "; ymax- " + view.extent.ymax + "; ymin- " + view.extent.ymin + "; ");
                    console.log("Zoom is now - " + zoom);
                    aggregating(view.zoom);
                }

            }
        });

        function aggregating(zoom) {
            startAggregating = Date.now();
            // zoom 15 - aggregate; 16 - no (graphicsLayer, baseStations, zoom) 49.992371, 36.230297
            //49.992218 36.2299535
            // console.log("Zoom - " + zoom);
            console.log("Zoom - " + zoom);
            graphicsLayer.removeAll();
            graphicsLayer.add(getImage("images/pointer.png", 49.988633, 36.227969));


            var baseStations = $scope.baseStations;
            console.log("Base Station - " + baseStations.length);
            var imageBaseStations = [];
            if (baseStations != undefined) {
                for (var u = 0; u < baseStations.length; u++) {
                    // console.log("Base Station - " + baseStations[u].id);
                    // console.log("_____________________________-");
                    if (baseStations[u].type == 'green') {
                        imageBaseStations.push(getImage("images/green.png", baseStations[u].latitude, baseStations[u].longitude));
                    }
                    if (baseStations[u].type == 'grey') {
                        imageBaseStations.push(getImage("images/grey.png", baseStations[u].latitude, baseStations[u].longitude));
                    }
                    if (baseStations[u].type == 'yellow') {
                        imageBaseStations.push(getImage("images/yellow.png", baseStations[u].latitude, baseStations[u].longitude));
                    }
                }
                // imageBaseStations.push(getImage("images/yellow.png", 49.988518, 36.226554));//0.005784220345040679
                // imageBaseStations.push(getImage("images/green.png", 49.988072, 36.220787));
                // imageBaseStations.push(getImage("images/grey.png", 49.993258, 36.221779));
                // imageBaseStations.push(getImage("images/yellow.png", 49.988982, 36.222230));
                // imageBaseStations.push(getImage("images/green.png", 49.991795, 36.223775));
                // imageBaseStations.push(getImage("images/yellow.png", 49.993422, 36.235448));
                // imageBaseStations.push(getImage("images/green.png", 49.985502, 36.224590));
                // imageBaseStations.push(getImage("images/grey.png", 49.985722, 36.227379));
                // //
                // imageBaseStations.push(getImage("images/yellow.png", 49.992371, 36.230297));//organ
                // imageBaseStations.push(getImage("images/green.png", 49.992065, 36.229610));//organ


                var displayBaseStations = [];
                var isAggriating = false;
                for (var d = 0; d < imageBaseStations.length; d++) {
                    isAggriating = false;
                    if (displayBaseStations.length == 0) {
                        displayBaseStations.push(imageBaseStations[d]);
                    } else {
                        for (var i = 0; i < displayBaseStations.length; i++) {
                            // console.log("Distance between point - " + displayBaseStations[i].geometry.distance(imageBaseStations[d].geometry));

                            if (displayBaseStations[i].geometry.distance(imageBaseStations[d].geometry) <= 0.000045011109739724245
                                && zoom <= 21) {
                                displayBaseStations[i] = getAggrigatedDisplayBaseStation(displayBaseStations[i], imageBaseStations[d]);
                                isAggriating = true;
                            }
                            if (0.000045011109739724245 < displayBaseStations[i].geometry.distance(imageBaseStations[d].geometry)
                                && displayBaseStations[i].geometry.distance(imageBaseStations[d].geometry) <= 0.00009400531899557338
                                && zoom <= 20) {
                                displayBaseStations[i] = getAggrigatedDisplayBaseStation(displayBaseStations[i], imageBaseStations[d]);
                                isAggriating = true;
                            }
                            if (0.00009400531899557338 < displayBaseStations[i].geometry.distance(imageBaseStations[d].geometry)
                                && displayBaseStations[i].geometry.distance(imageBaseStations[d].geometry) <= 0.0001632482771763881
                                && zoom <= 19) {
                                displayBaseStations[i] = getAggrigatedDisplayBaseStation(displayBaseStations[i], imageBaseStations[d]);
                                isAggriating = true;
                            }
                            if (0.0001632482771763881 < displayBaseStations[i].geometry.distance(imageBaseStations[d].geometry)
                                && displayBaseStations[i].geometry.distance(imageBaseStations[d].geometry) <= 0.0003223491895414472
                                && zoom <= 18) {
                                displayBaseStations[i] = getAggrigatedDisplayBaseStation(displayBaseStations[i], imageBaseStations[d]);
                                isAggriating = true;
                            }
                            if (0.0003223491895414472 < displayBaseStations[i].geometry.distance(imageBaseStations[d].geometry)
                                && displayBaseStations[i].geometry.distance(imageBaseStations[d].geometry) <= 0.0006856566195963213
                                && zoom <= 17) {
                                displayBaseStations[i] = getAggrigatedDisplayBaseStation(displayBaseStations[i], imageBaseStations[d]);
                                isAggriating = true;
                            }
                            if (0.0006856566195963213 < displayBaseStations[i].geometry.distance(imageBaseStations[d].geometry)
                                && displayBaseStations[i].geometry.distance(imageBaseStations[d].geometry) <= 0.000701160466652148
                                && zoom <= 16) {
                                displayBaseStations[i] = getAggrigatedDisplayBaseStation(displayBaseStations[i], imageBaseStations[d]);
                                isAggriating = true;
                            }
                            if (0.000701160466652148 < displayBaseStations[i].geometry.distance(imageBaseStations[d].geometry)
                                && displayBaseStations[i].geometry.distance(imageBaseStations[d].geometry) <=  0.001377618234489109
                                && zoom <= 15) {
                                displayBaseStations[i] = getAggrigatedDisplayBaseStation(displayBaseStations[i], imageBaseStations[d]);
                                isAggriating = true;
                            }
                            if (0.001377618234489109 < displayBaseStations[i].geometry.distance(imageBaseStations[d].geometry)
                                && displayBaseStations[i].geometry.distance(imageBaseStations[d].geometry) <=  0.0028379178987443447
                                && zoom <= 14) {
                                displayBaseStations[i] = getAggrigatedDisplayBaseStation(displayBaseStations[i], imageBaseStations[d]);
                                isAggriating = true;
                            }
                            if (0.0028379178987443447 < displayBaseStations[i].geometry.distance(imageBaseStations[d].geometry)
                                && displayBaseStations[i].geometry.distance(imageBaseStations[d].geometry) <=  0.005784220345040679
                                && zoom <= 13) {
                                displayBaseStations[i] = getAggrigatedDisplayBaseStation(displayBaseStations[i], imageBaseStations[d]);
                                isAggriating = true;
                            }
                            if (0.005784220345040679 < displayBaseStations[i].geometry.distance(imageBaseStations[d].geometry)
                                && displayBaseStations[i].geometry.distance(imageBaseStations[d].geometry) <=  0.011375660772017505
                                && zoom <= 12) {
                                displayBaseStations[i] = getAggrigatedDisplayBaseStation(displayBaseStations[i], imageBaseStations[d]);
                                isAggriating = true;
                            }
                            if (0.011375660772017505 < displayBaseStations[i].geometry.distance(imageBaseStations[d].geometry)
                                && displayBaseStations[i].geometry.distance(imageBaseStations[d].geometry) <=  0.022913646610699744
                                && zoom <= 11) {
                                displayBaseStations[i] = getAggrigatedDisplayBaseStation(displayBaseStations[i], imageBaseStations[d]);
                                isAggriating = true;
                            }
                            if (0.022913646610699744 < displayBaseStations[i].geometry.distance(imageBaseStations[d].geometry)
                                && displayBaseStations[i].geometry.distance(imageBaseStations[d].geometry) <=  0.04542713975147452
                                && zoom <= 10) {
                                displayBaseStations[i] = getAggrigatedDisplayBaseStation(displayBaseStations[i], imageBaseStations[d]);
                                isAggriating = true;
                            }
                            if (0.04542713975147452 < displayBaseStations[i].geometry.distance(imageBaseStations[d].geometry)
                                && displayBaseStations[i].geometry.distance(imageBaseStations[d].geometry) <=  0.0859961892760351
                                && zoom <= 9) {
                                displayBaseStations[i] = getAggrigatedDisplayBaseStation(displayBaseStations[i], imageBaseStations[d]);
                                isAggriating = true;
                            }
                            if (0.0859961892760351 < displayBaseStations[i].geometry.distance(imageBaseStations[d].geometry)
                                && displayBaseStations[i].geometry.distance(imageBaseStations[d].geometry) <=  0.17843863090989773
                                && zoom <= 8) {
                                displayBaseStations[i] = getAggrigatedDisplayBaseStation(displayBaseStations[i], imageBaseStations[d]);
                                isAggriating = true;
                            }
                            if (0.17843863090989773 < displayBaseStations[i].geometry.distance(imageBaseStations[d].geometry)
                                && displayBaseStations[i].geometry.distance(imageBaseStations[d].geometry) <=  0.3684453815479292
                                && zoom <= 7) {
                                displayBaseStations[i] = getAggrigatedDisplayBaseStation(displayBaseStations[i], imageBaseStations[d]);
                                isAggriating = true;
                            }
                            if (displayBaseStations[i].geometry.distance(imageBaseStations[d].geometry) <=  0.7129392669091827
                                && zoom == 6) {
                                displayBaseStations[i] = getAggrigatedDisplayBaseStation(displayBaseStations[i], imageBaseStations[d]);
                                isAggriating = true;
                            }
                        }
                        if (isAggriating == false) {
                            displayBaseStations.push(imageBaseStations[d]);
                        }
                    }
                }
                function getAggrigatedDisplayBaseStation(displayBaseStations, imageBaseStations) {
                    if (displayBaseStations.symbol.url == "images/yellow.png" && imageBaseStations.symbol.url == "images/yellow.png" ) {
                        return getImage("images/yellow.png",
                            (imageBaseStations.geometry.y + displayBaseStations.geometry.y) / 2,
                            (imageBaseStations.geometry.x + displayBaseStations.geometry.x) / 2);
                    } else if (displayBaseStations.symbol.url == "images/yellow.png" && imageBaseStations.symbol.url == "images/green.png" ) {
                        return getImage("images/yellow_green.png",
                            (imageBaseStations.geometry.y + displayBaseStations.geometry.y) / 2,
                            (imageBaseStations.geometry.x + displayBaseStations.geometry.x) / 2);
                    } else if (displayBaseStations.symbol.url == "images/yellow.png" && imageBaseStations.symbol.url == "images/grey.png" ) {
                        return getImage("images/grey_yellow.png",
                            (imageBaseStations.geometry.y + displayBaseStations.geometry.y) / 2,
                            (imageBaseStations.geometry.x + displayBaseStations.geometry.x) / 2);
                    } else if (displayBaseStations.symbol.url == "images/yellow.png" && imageBaseStations.symbol.url == "images/grey_green.png" ) {
                        return getImage("images/grey_yellow_green.png",
                            (imageBaseStations.geometry.y + displayBaseStations.geometry.y) / 2,
                            (imageBaseStations.geometry.x + displayBaseStations.geometry.x) / 2);
                    } else if (displayBaseStations.symbol.url == "images/yellow.png" && imageBaseStations.symbol.url == "images/grey_yellow.png" ) {
                        return getImage("images/grey_yellow.png",
                            (imageBaseStations.geometry.y + displayBaseStations.geometry.y) / 2,
                            (imageBaseStations.geometry.x + displayBaseStations.geometry.x) / 2);
                    } else if (displayBaseStations.symbol.url == "images/yellow.png" && imageBaseStations.symbol.url == "images/grey_yellow_green.png" ) {
                        return getImage("images/grey_yellow_green.png",
                            (imageBaseStations.geometry.y + displayBaseStations.geometry.y) / 2,
                            (imageBaseStations.geometry.x + displayBaseStations.geometry.x) / 2);
                    } else if (displayBaseStations.symbol.url == "images/yellow.png" && imageBaseStations.symbol.url == "images/yellow_green.png" ) {
                        return getImage("images/yellow_green.png",
                            (imageBaseStations.geometry.y + displayBaseStations.geometry.y) / 2,
                            (imageBaseStations.geometry.x + displayBaseStations.geometry.x) / 2);
                    }

                    if (displayBaseStations.symbol.url == "images/green.png" && imageBaseStations.symbol.url == "images/green.png" ) {
                        return getImage("images/green.png",
                            (imageBaseStations.geometry.y + displayBaseStations.geometry.y) / 2,
                            (imageBaseStations.geometry.x + displayBaseStations.geometry.x) / 2);
                    } else if (displayBaseStations.symbol.url == "images/green.png" && imageBaseStations.symbol.url == "images/yellow.png" ) {
                        return getImage("images/yellow_green.png",
                            (imageBaseStations.geometry.y + displayBaseStations.geometry.y) / 2,
                            (imageBaseStations.geometry.x + displayBaseStations.geometry.x) / 2);
                    } else if (displayBaseStations.symbol.url == "images/green.png" && imageBaseStations.symbol.url == "images/grey.png" ) {
                        return getImage("images/grey_green.png",
                            (imageBaseStations.geometry.y + displayBaseStations.geometry.y) / 2,
                            (imageBaseStations.geometry.x + displayBaseStations.geometry.x) / 2);
                    } else if (displayBaseStations.symbol.url == "images/green.png" && imageBaseStations.symbol.url == "images/grey_green.png" ) {
                        return getImage("images/grey_green.png",
                            (imageBaseStations.geometry.y + displayBaseStations.geometry.y) / 2,
                            (imageBaseStations.geometry.x + displayBaseStations.geometry.x) / 2);
                    } else if (displayBaseStations.symbol.url == "images/green.png" && imageBaseStations.symbol.url == "images/grey_yellow.png" ) {
                        return getImage("images/grey_yellow_green.png",
                            (imageBaseStations.geometry.y + displayBaseStations.geometry.y) / 2,
                            (imageBaseStations.geometry.x + displayBaseStations.geometry.x) / 2);
                    } else if (displayBaseStations.symbol.url == "images/green.png" && imageBaseStations.symbol.url == "images/grey_yellow_green.png" ) {
                        return getImage("images/grey_yellow_green.png",
                            (imageBaseStations.geometry.y + displayBaseStations.geometry.y) / 2,
                            (imageBaseStations.geometry.x + displayBaseStations.geometry.x) / 2);
                    } else if (displayBaseStations.symbol.url == "images/green.png" && imageBaseStations.symbol.url == "images/yellow_green.png" ) {
                        return getImage("images/yellow_green.png",
                            (imageBaseStations.geometry.y + displayBaseStations.geometry.y) / 2,
                            (imageBaseStations.geometry.x + displayBaseStations.geometry.x) / 2);
                    }


                    if (displayBaseStations.symbol.url == "images/grey.png" && imageBaseStations.symbol.url == "images/grey.png" ) {
                        return getImage("images/grey.png",
                            (imageBaseStations.geometry.y + displayBaseStations.geometry.y) / 2,
                            (imageBaseStations.geometry.x + displayBaseStations.geometry.x) / 2);
                    } else if (displayBaseStations.symbol.url == "images/grey.png" && imageBaseStations.symbol.url == "images/yellow.png" ) {
                        return getImage("images/grey_yellow.png",
                            (imageBaseStations.geometry.y + displayBaseStations.geometry.y) / 2,
                            (imageBaseStations.geometry.x + displayBaseStations.geometry.x) / 2);
                    } else if (displayBaseStations.symbol.url == "images/grey.png" && imageBaseStations.symbol.url == "images/green.png" ) {
                        return getImage("images/grey_green.png",
                            (imageBaseStations.geometry.y + displayBaseStations.geometry.y) / 2,
                            (imageBaseStations.geometry.x + displayBaseStations.geometry.x) / 2);
                    } else if (displayBaseStations.symbol.url == "images/grey.png" && imageBaseStations.symbol.url == "images/grey_green.png" ) {
                        return getImage("images/grey_green.png",
                            (imageBaseStations.geometry.y + displayBaseStations.geometry.y) / 2,
                            (imageBaseStations.geometry.x + displayBaseStations.geometry.x) / 2);
                    } else if (displayBaseStations.symbol.url == "images/grey.png" && imageBaseStations.symbol.url == "images/grey_yellow.png" ) {
                        return getImage("images/grey_yellow.png",
                            (imageBaseStations.geometry.y + displayBaseStations.geometry.y) / 2,
                            (imageBaseStations.geometry.x + displayBaseStations.geometry.x) / 2);
                    } else if (displayBaseStations.symbol.url == "images/grey.png" && imageBaseStations.symbol.url == "images/grey_yellow_green.png" ) {
                        return getImage("images/grey_yellow_green.png",
                            (imageBaseStations.geometry.y + displayBaseStations.geometry.y) / 2,
                            (imageBaseStations.geometry.x + displayBaseStations.geometry.x) / 2);
                    } else if (displayBaseStations.symbol.url == "images/grey.png" && imageBaseStations.symbol.url == "images/yellow_green.png" ) {
                        return getImage("images/grey_yellow_green.png",
                            (imageBaseStations.geometry.y + displayBaseStations.geometry.y) / 2,
                            (imageBaseStations.geometry.x + displayBaseStations.geometry.x) / 2);
                    }

                    if (displayBaseStations.symbol.url == "images/grey_green.png" && imageBaseStations.symbol.url == "images/yellow_green.png" ) {
                        return getImage("images/grey_yellow_green.png",
                            (imageBaseStations.geometry.y + displayBaseStations.geometry.y) / 2,
                            (imageBaseStations.geometry.x + displayBaseStations.geometry.x) / 2);

                    } else if (displayBaseStations.symbol.url == "images/grey_green.png" && imageBaseStations.symbol.url == "images/grey_yellow_green.png" ) {
                        return getImage("images/grey_yellow_green.png",
                            (imageBaseStations.geometry.y + displayBaseStations.geometry.y) / 2,
                            (imageBaseStations.geometry.x + displayBaseStations.geometry.x) / 2);

                    } else if (displayBaseStations.symbol.url == "images/grey_green.png" && imageBaseStations.symbol.url == "images/grey_yellow.png" ) {
                        return getImage("images/grey_yellow_green.png",
                            (imageBaseStations.geometry.y + displayBaseStations.geometry.y) / 2,
                            (imageBaseStations.geometry.x + displayBaseStations.geometry.x) / 2);

                    } else if (displayBaseStations.symbol.url == "images/grey_green.png" && imageBaseStations.symbol.url == "images/grey_green.png" ) {
                        return getImage("images/grey_green.png",
                            (imageBaseStations.geometry.y + displayBaseStations.geometry.y) / 2,
                            (imageBaseStations.geometry.x + displayBaseStations.geometry.x) / 2);

                    } else if (displayBaseStations.symbol.url == "images/grey_green.png" && imageBaseStations.symbol.url == "images/grey.png" ) {
                        return getImage("images/grey_green.png",
                            (imageBaseStations.geometry.y + displayBaseStations.geometry.y) / 2,
                            (imageBaseStations.geometry.x + displayBaseStations.geometry.x) / 2);

                    } else if (displayBaseStations.symbol.url == "images/grey_green.png" && imageBaseStations.symbol.url == "images/yellow.png" ) {
                        return getImage("images/grey_yellow_green.png",
                            (imageBaseStations.geometry.y + displayBaseStations.geometry.y) / 2,
                            (imageBaseStations.geometry.x + displayBaseStations.geometry.x) / 2);

                    } else if (displayBaseStations.symbol.url == "images/grey_green.png" && imageBaseStations.symbol.url == "images/green.png" ) {
                        return getImage("images/grey_green.png",
                            (imageBaseStations.geometry.y + displayBaseStations.geometry.y) / 2,
                            (imageBaseStations.geometry.x + displayBaseStations.geometry.x) / 2);

                    }

                    if (displayBaseStations.symbol.url == "images/grey_yellow.png" && imageBaseStations.symbol.url == "images/green.png" ) {
                        return getImage("images/grey_yellow_green.png",
                            (imageBaseStations.geometry.y + displayBaseStations.geometry.y) / 2,
                            (imageBaseStations.geometry.x + displayBaseStations.geometry.x) / 2);

                    } else if (displayBaseStations.symbol.url == "images/grey_yellow.png" && imageBaseStations.symbol.url == "images/grey.png" ) {
                        return getImage("images/grey_yellow.png",
                            (imageBaseStations.geometry.y + displayBaseStations.geometry.y) / 2,
                            (imageBaseStations.geometry.x + displayBaseStations.geometry.x) / 2);

                    } else if (displayBaseStations.symbol.url == "images/grey_yellow.png" && imageBaseStations.symbol.url == "images/grey_green.png" ) {
                        return getImage("images/grey_yellow_green.png",
                            (imageBaseStations.geometry.y + displayBaseStations.geometry.y) / 2,
                            (imageBaseStations.geometry.x + displayBaseStations.geometry.x) / 2);

                    } else if (displayBaseStations.symbol.url == "images/grey_yellow.png" && imageBaseStations.symbol.url == "images/grey_yellow.png" ) {
                        return getImage("images/grey_yellow.png",
                            (imageBaseStations.geometry.y + displayBaseStations.geometry.y) / 2,
                            (imageBaseStations.geometry.x + displayBaseStations.geometry.x) / 2);

                    } else if (displayBaseStations.symbol.url == "images/grey_yellow.png" && imageBaseStations.symbol.url == "images/grey_yellow_green.png" ) {
                        return getImage("images/grey_yellow_green.png",
                            (imageBaseStations.geometry.y + displayBaseStations.geometry.y) / 2,
                            (imageBaseStations.geometry.x + displayBaseStations.geometry.x) / 2);

                    } else if (displayBaseStations.symbol.url == "images/grey_yellow.png" && imageBaseStations.symbol.url == "images/yellow.png" ) {
                        return getImage("images/grey_yellow.png",
                            (imageBaseStations.geometry.y + displayBaseStations.geometry.y) / 2,
                            (imageBaseStations.geometry.x + displayBaseStations.geometry.x) / 2);

                    } else if (displayBaseStations.symbol.url == "images/grey_yellow.png" && imageBaseStations.symbol.url == "images/yellow_green.png" ) {
                        return getImage("images/grey_yellow_green.png",
                            (imageBaseStations.geometry.y + displayBaseStations.geometry.y) / 2,
                            (imageBaseStations.geometry.x + displayBaseStations.geometry.x) / 2);

                    }

                    if (displayBaseStations.symbol.url == "images/yellow_green.png" && imageBaseStations.symbol.url == "images/green.png" ) {
                        return getImage("images/yellow_green.png",
                            (imageBaseStations.geometry.y + displayBaseStations.geometry.y) / 2,
                            (imageBaseStations.geometry.x + displayBaseStations.geometry.x) / 2);

                    } else if (displayBaseStations.symbol.url == "images/yellow_green.png" && imageBaseStations.symbol.url == "images/grey.png" ) {
                        return getImage("images/grey_yellow_green.png",
                            (imageBaseStations.geometry.y + displayBaseStations.geometry.y) / 2,
                            (imageBaseStations.geometry.x + displayBaseStations.geometry.x) / 2);

                    } else if (displayBaseStations.symbol.url == "images/yellow_green.png" && imageBaseStations.symbol.url == "images/grey_green.png" ) {
                        return getImage("images/grey_yellow_green.png",
                            (imageBaseStations.geometry.y + displayBaseStations.geometry.y) / 2,
                            (imageBaseStations.geometry.x + displayBaseStations.geometry.x) / 2);

                    } else if (displayBaseStations.symbol.url == "images/yellow_green.png" && imageBaseStations.symbol.url == "images/grey_yellow.png" ) {
                        return getImage("images/grey_yellow_green.png",
                            (imageBaseStations.geometry.y + displayBaseStations.geometry.y) / 2,
                            (imageBaseStations.geometry.x + displayBaseStations.geometry.x) / 2);

                    } else if (displayBaseStations.symbol.url == "images/yellow_green.png" && imageBaseStations.symbol.url == "images/grey_yellow_green.png" ) {
                        return getImage("images/grey_yellow_green.png",
                            (imageBaseStations.geometry.y + displayBaseStations.geometry.y) / 2,
                            (imageBaseStations.geometry.x + displayBaseStations.geometry.x) / 2);

                    } else if (displayBaseStations.symbol.url == "images/yellow_green.png" && imageBaseStations.symbol.url == "images/yellow.png" ) {
                        return getImage("images/yellow_green.png",
                            (imageBaseStations.geometry.y + displayBaseStations.geometry.y) / 2,
                            (imageBaseStations.geometry.x + displayBaseStations.geometry.x) / 2);

                    } else if (displayBaseStations.symbol.url == "images/yellow_green.png" && imageBaseStations.symbol.url == "images/yellow_green.png" ) {
                        return getImage("images/yellow_green.png",
                            (imageBaseStations.geometry.y + displayBaseStations.geometry.y) / 2,
                            (imageBaseStations.geometry.x + displayBaseStations.geometry.x) / 2);

                    }

                    if (displayBaseStations.symbol.url == "images/grey_yellow_green.png" && imageBaseStations.symbol.url == "images/green.png" ) {
                        return getImage("images/grey_yellow_green.png",
                            (imageBaseStations.geometry.y + displayBaseStations.geometry.y) / 2,
                            (imageBaseStations.geometry.x + displayBaseStations.geometry.x) / 2);

                    } else if (displayBaseStations.symbol.url == "images/grey_yellow_green.png" && imageBaseStations.symbol.url == "images/grey.png" ) {
                        return getImage("images/grey_yellow_green.png",
                            (imageBaseStations.geometry.y + displayBaseStations.geometry.y) / 2,
                            (imageBaseStations.geometry.x + displayBaseStations.geometry.x) / 2);

                    } else if (displayBaseStations.symbol.url == "images/grey_yellow_green.png" && imageBaseStations.symbol.url == "images/grey_green.png" ) {
                        return getImage("images/grey_yellow_green.png",
                            (imageBaseStations.geometry.y + displayBaseStations.geometry.y) / 2,
                            (imageBaseStations.geometry.x + displayBaseStations.geometry.x) / 2);

                    } else if (displayBaseStations.symbol.url == "images/grey_yellow_green.png" && imageBaseStations.symbol.url == "images/grey_yellow.png" ) {
                        return getImage("images/grey_yellow_green.png",
                            (imageBaseStations.geometry.y + displayBaseStations.geometry.y) / 2,
                            (imageBaseStations.geometry.x + displayBaseStations.geometry.x) / 2);

                    } else if (displayBaseStations.symbol.url == "images/grey_yellow_green.png" && imageBaseStations.symbol.url == "images/grey_yellow_green.png" ) {
                        return getImage("images/grey_yellow_green.png",
                            (imageBaseStations.geometry.y + displayBaseStations.geometry.y) / 2,
                            (imageBaseStations.geometry.x + displayBaseStations.geometry.x) / 2);

                    } else if (displayBaseStations.symbol.url == "images/grey_yellow_green.png" && imageBaseStations.symbol.url == "images/yellow.png" ) {
                        return getImage("images/grey_yellow_green.png",
                            (imageBaseStations.geometry.y + displayBaseStations.geometry.y) / 2,
                            (imageBaseStations.geometry.x + displayBaseStations.geometry.x) / 2);

                    } else if (displayBaseStations.symbol.url == "images/grey_yellow_green.png" && imageBaseStations.symbol.url == "images/yellow_green.png" ) {
                        return getImage("images/grey_yellow_green.png",
                            (imageBaseStations.geometry.y + displayBaseStations.geometry.y) / 2,
                            (imageBaseStations.geometry.x + displayBaseStations.geometry.x) / 2);

                    }

                }
                graphicsLayer.addMany(displayBaseStations);
            }

            /*********************
             * Get distance between two points (Point.between(Point))
             *
             INSERT INTO base_station VALUES (6, 49.992371, 36.230297, 1);
             INSERT INTO base_station VALUES (7, 49.992065, 36.229610, 3);
             *********************/
            /*
             var point1 = getImage("images/yellow.png", 49.992065, 36.229610);
             var point2 = getImage("images/green.png", 49.992371, 36.230297);

             point1.geometry.distance(point2.geometry)
             */
            endAggregating = Date.now();
            elapsed = (endAggregating - startAggregating) / 1000;
            console.log("Time aggregating - " + elapsed);
        }
        aggregating(view.zoom);
    });

    MonitoringService.getBaseStation()
        .then(function success(response) {
                $scope.baseStations = response.data;
                console.log("Base Station with response.data - " + response.data.length);
                // aggregating(response.data)
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


    /***********************************
     *  Get data from server.
     ************************************/
    MonitoringService.getRegions()
        .then(function success(response) {
                $scope.regions = response.data;
                $scope.mrms = response.data[0].mobileRadioMonitoringStations;
                // $scope.baseStations = response.data[0].mobileRadioMonitoringStations[0].baseStations;
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

    /***********************************
     *  Get data from server.
     ************************************/
    function getBaseStations () {
        MonitoringService.getBaseStation()
            .then(function success(response) {
                    return response.data;
                    // aggregating(response.data)
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
