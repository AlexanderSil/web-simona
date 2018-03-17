var simonaService = angular.module('simonaApp.services', ['ngResource']);

simonaService.factory('UserService', function($resource) {
    // return $resource('api/User/:id', {}, {
    //     authenticate: {
    //         method: 'POST',
    //         params: {'id' : 'authenticate'},
    //         headers : {'Content-Type': 'application/x-www-form-urlencoded'}
    //     }, currentUser: {
    //         method: 'GET',
    //         params: {'id' : 'currentUser'},
    //         headers : {'Content-Type': 'application/x-www-form-urlencoded'}
    //     }
    // });
});