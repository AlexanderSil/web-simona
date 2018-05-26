var simonaService = angular.module('simonaApp.services', ['ngResource']);

simonaService.service('MonitoringService', [ '$http', function($http) {


    /*******************************************************************************************************************
     * Control Points - (Base Stations)
     *******************************************************************************************************************/
    this.getBaseStationFromDB = function getRegions(zoom, mrmsIds, rightTopLatitude, rightTopLongtitude, leftBottomLatitude, leftBottomLongtitude) {
        return $http({
            method : 'GET',
            url : "base/stations",
            params: {
                zoom: zoom,
                mrmsIds: mrmsIds,
                rightTopLatitude: rightTopLatitude,
                rightTopLongtitude: rightTopLongtitude,
                leftBottomLatitude: leftBottomLatitude,
                leftBottomLongtitude: leftBottomLongtitude
            }
        });
    };
    this.getActualControlPoints = function getRegions(zoom, mrmsIds, rightTopLatitude, rightTopLongtitude, leftBottomLatitude, leftBottomLongtitude) {
        return $http({
            method : 'GET',
            url : "base/stations/actual",
            params: {
                zoom: zoom,
                mrmsIds: mrmsIds,
                rightTopLatitude: rightTopLatitude,
                rightTopLongtitude: rightTopLongtitude,
                leftBottomLatitude: leftBottomLatitude,
                leftBottomLongtitude: leftBottomLongtitude
            }
        });
    };
    this.updateControlPointStatus = function updatePostControlPointStatus(zoom, mrmsIds, updateObject, rightTopLatitude, rightTopLongitude, leftBottomLatitude, leftBottomLongitude) {
        return $http({
            method : 'GET',
            url : "updatePostControlPointStatus",
            params: {
                rightTopLatitude: rightTopLatitude,
                rightTopLongitude: rightTopLongitude,
                leftBottomLatitude: leftBottomLatitude,
                leftBottomLongitude: leftBottomLongitude,
                zoom: zoom,
                mrmsIds: mrmsIds,
                points: updateObject.points,
                postID: updateObject.postID,
                type: updateObject.type,
                packetID: updateObject.packetID
            }
        });
    };
    this.updateControlPointDetect = function updateControlPointDetect(zoom, mrmsIds, updateObject, rightTopLatitude, rightTopLongitude, leftBottomLatitude, leftBottomLongitude) {
        return $http({
            method : 'GET',
            url : "updateControlPointDetect",
            params: {
                rightTopLatitude: rightTopLatitude,
                rightTopLongitude: rightTopLongitude,
                leftBottomLatitude: leftBottomLatitude,
                leftBottomLongitude: leftBottomLongitude,
                zoom: zoom,
                mrmsIds: mrmsIds,
                points: updateObject.points,
                postID: updateObject.postID,
                type: updateObject.type,
                packetID: updateObject.packetID
            }
        });
    };
    // conn.send("{\"points\":[19452],\"postID\":28,\"type\":\"CONTROL_POINT_DETECT\",\"packetID\":573}");
    // conn.send("{\"points\":[{\"postStatus\":\"DETECT\",\"pointID\":5931,\"status\":\"MEASUREMENT\"}],\"postID\":28,\"type\":\"POST_CONTROL_POINT_STATUS\",\"packetID\":577}");


    /*******************************************************************************************************************
     * Posts
     *******************************************************************************************************************/
    this.getPostsFromDB = function getPostList(zoom, mrmsIds) {
        return $http({
            method : 'GET',
            url : "posts",
            params: {
                zoom: zoom,
                mrmsIds: mrmsIds
            }
        });
    };
    this.getActualPostList = function getPostList(zoom, mrmsIds) {
        return $http({
            method : 'GET',
            url : "posts/actual",
            params: {
                zoom: zoom,
                mrmsIds: mrmsIds
            }
        });
    };
    this.updatePostLocation = function updatePostLocation(zoom, mrmsIds, updateObject) {
        return $http({
            method : 'GET',
            url : "/posts/update",
            params: {
                zoom: zoom,
                mrmsIds: mrmsIds,
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




    /*******************************************************************************************************************
     * Regions Menu Info
     *******************************************************************************************************************/
    this.getRegions = function getRegions() {
        return $http({
            method : 'GET',
            url : "regions"
        });
    };
    this.actualRegionsInfo = function getRegions(mrmsIds) {
        return $http({
            method : 'GET',
            url : "region/actual/info",
            params: {
                mrmsIds: mrmsIds
            }
        });
    };
    // this.getBaseStation = function getRegions() {
    //     return $http({
    //         method : 'GET',
    //         url : "api/base"
    //     });
    // };




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
    this.updateUnidentifiedCount = function update(updatedObject) {
        return $http({
            method : 'GET',
            url : "updateUnidentifiedCount",
            params: {
                unidentified: updatedObject.data
            }
        });
    };
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