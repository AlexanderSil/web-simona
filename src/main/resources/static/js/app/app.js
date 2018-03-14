'use strict';
var app = angular.module('my-App', ['ngRoute']);

app.config(function($routeProvider) {
    $routeProvider
        .when("/", {
            // templateUrl : "index"
            template : "<h1>index</h1><p>Tomatoes contain around 95% water.</p>"
        })
        .when("/first", {
            templateUrl : "views/first.html",
            controller : "controller"
        // })
        // .when("/green", {
        //     templateUrl : "green.htm"
        // })
        // .when("/blue", {
        //     templateUrl : "blue.htm"
        //
        })
        .otherwise({
        template : "<h1>None</h1><p>Nothing has been selected</p>"
    });
});
