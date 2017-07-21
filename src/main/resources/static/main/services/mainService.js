angular.module('main')
    .service('MainService', function ($http, Config) {
        var baseURL = Config.ENV.REST_URL+"/course";

        this.getAllCourses = function (pageNo, size) {
            var url = baseURL + "/getAllCourses";
            url = url+"?page="+pageNo+"&size="+size;
            return $http.get(url).then(function (response) {
                return response;
            }).catch(function () {
                return null;
            });
        };
        
        this.getRole = function (){
        	var url = baseURL + "/getUserRole";
        	return $http.get(url).then(function (response) {
                return response.data;
            }).catch(function () {
                return null;
            });
        }
        
        this.getTotalCourses = function (){
        	var url = baseURL + "/getTotalCourses";
        	return $http.get(url).then(function (response){
        		return response;
            }).catch(function () {
                return 0;
        	});
        };
        
        this.getCourseDetails = function (id){
        	var url = baseURL + "/getCourseById?id=" + id;
        	return $http.get(url).then(function (response){
        		return response;
            }).catch(function () {
                return null;
        	});
        }
        
        this.deleteCourse = function (id){
        	var url = baseURL + "/delete?id=" + id;
        	return $http.get(url).then(function (response){
        		return response;
            }).catch(function () {
                return null;
        	});
        }

        this.save = function (jsonReq) {
        	if(jsonReq.isNew){
        		var url = baseURL + "/add";
        	} else {
        		var url = baseURL + "/update";
        	}
        	console.log(url);
            var json = angular.toJson(jsonReq);
            return $http.post(url, json).then(function (response) {
                return response;
            });
        };
    });