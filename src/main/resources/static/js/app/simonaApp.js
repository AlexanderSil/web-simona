var simonaApp = angular.module('simonaApp', ['ngRoute', 'esri.map', 'simonaApp.services'])
    .config(['$routeProvider', function ($routeProvider) {


        // $routeProvider.when("/monitoring", {
            // templateUrl: 'partials/monitoring.html',
            // controller: 'MainController',
            // controllerAs: 'mainController',
            // resolve: MonitoringController.resolve,
            // title: "Monitoring page"
            // accessControl: {
            //     authorizedRoles: [USER_ROLES.ALL]
            // }
        // })
            // .when("/monitoring", {
            // templateUrl: 'partials/monitoring.html',
            // controller: 'MonitoringController',
            // controllerAs: 'monitoringController',
            // resolve: MonitoringController.resolve,
            // title: "Monitoring page"
            // accessControl: {
            //     authorizedRoles: [USER_ROLES.ALL]
            // }
        // })
        //     .when('/login', {
        //     templateUrl: 'partials/login.html',
        //     controller: 'LoginController',
//				title: "page.title.login",
//             title: "Login"
            // accessControl: {
            //     authorizedRoles: [USER_ROLES.ALL]
            // }
        // }).when('/management', {
        //     templateUrl: 'partials/management.html',
        //     controller: 'ManagementController',
        //     controllerAs: 'managementController',
        //     title: 'Management page'
        //     resolve: ManagementController.resolve
        //     accessControl: {
        //         authorizedRoles: [USER_ROLES.SUPER_ADMIN]
        //     }
        // })
        //     .otherwise({redirectTo: "/monitoring"
        // });


}]);

