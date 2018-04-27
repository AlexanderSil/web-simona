var simonaService = angular.module('simonaApp.services', ['ngResource']);

simonaService.service('MonitoringService', [ '$http', function($http) {

    this.getRegions = function getRegions() {
        return $http({
            method : 'GET',
            url : "regions"
        });
    };
    // this.getBaseStation = function getRegions() {
    //     return $http({
    //         method : 'GET',
    //         url : "api/base"
    //     });
    // };
    this.getBaseStation = function getRegions(rightTopLatitude, rightTopLongtitude, leftBottomLatitude, leftBottomLongtitude, zoom, regionIds, mrmsIds) {
        return $http({
            method : 'GET',
            url : "points",
            params: {
                rightTopLatitude: rightTopLatitude,
                rightTopLongtitude: rightTopLongtitude,
                leftBottomLatitude: leftBottomLatitude,
                leftBottomLongtitude: leftBottomLongtitude,
                zoom: zoom,
                regionIds: regionIds,
                mrmsIds: mrmsIds
            }
        });
    };
    this.updatePostStatus = function update(updatedObject) {
        return $http({
            method : 'GET',
            url : "updatePostStatus",
            params: {
                status: updatedObject.status,
                postID: updatedObject.postID,
                type: updatedObject.type,
                packetID: updatedObject.packetID
            }
        });
    };
    this.updatePostLocation = function updatePostLocation(rightTopLatitude, rightTopLongitude, leftBottomLatitude, leftBottomLongitude, zoom, updateObject) {
        return $http({
            method : 'GET',
            url : "updatePostLocation",
            params: {
                rightTopLatitude: rightTopLatitude,
                rightTopLongitude: rightTopLongitude,
                leftBottomLatitude: leftBottomLatitude,
                leftBottomLongitude: leftBottomLongitude,
                zoom: zoom,
                coordLat: updateObject.coord.lat,
                coordLon: updateObject.coord.lon,
                speed: updateObject.speed,
                direction: updateObject.direction,
                postID: updateObject.postID,
                type: updateObject.type,
                packetID: updateObject.packetID
            }
        });
    };
    this.updatePostControlPointStatus = function updatePostControlPointStatus(rightTopLatitude, rightTopLongitude, leftBottomLatitude, leftBottomLongitude, zoom, updateObject) {
        return $http({
            method : 'GET',
            url : "updatePostControlPointStatus",
            params: {
                rightTopLatitude: rightTopLatitude,
                rightTopLongitude: rightTopLongitude,
                leftBottomLatitude: leftBottomLatitude,
                leftBottomLongitude: leftBottomLongitude,
                zoom: zoom,
                points: updateObject.points,
                postID: updateObject.postID,
                type: updateObject.type,
                packetID: updateObject.packetID
            }
        });
    }
} ]);

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
    });
// simonaService.factory('MonitoringService', function($resource) {
//     return $resource('api/regions', {}, {
//         getRegions: {
//             method: 'GET',
//             headers : {'Content-Type': 'application/x-www-form-urlencoded'}
//         }
//     });
// });

/*
var MonitoringService = function ($http) {
    return {
        getRegions: function () {
            return $http({
                method: 'GET',
                url: 'api/regions'
            })
        }
    }
};
simonaService.factory('MonitoringService', MonitoringService);
*/


/*
simonaService.factory('MonitoringService', function ($http) {
    var MonitoringService = {
        getAll: function() {
            var promise = $http({
                url: 'api/regions',
                method: 'GET'
            }).then(function(response){
                $scope.regions = response.data;
                console.log(response);
                return response.data;
            });
            return promise;
        },
        getRegions: function () {
            var promise =$http({
                url: "api/regions",
                method: "GET"
            }).then(function successCallback(response) {
                // this callback will be called asynchronously
                // when the response is available
                $scope.data = response.data;
                console.log(response);
            }, function errorCallback(response) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                $scope.error = response.statusText;
            });
            return promise;
        }
    };
    return MonitoringService;

});
*/