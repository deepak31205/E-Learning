angular.module('main')
    .controller('HomeCtrl', function ($scope, $rootScope, $window, $mdDialog, $state, MainService, Config) {

        $scope.initialize = function () {
            $scope.pageNo = 1;
            $scope.size = 5;
            $scope.showCourses($scope.pageNo-1, $scope.size);
            $scope.totalPages();
            $scope.getRole();
            $scope.view = "main";
            $scope.maxSize = 5;
        };
        
        $scope.getRole = function() {
        	MainService.getRole().then(function (response) {
        		$scope.userRole = response.data;
        	});
        }
 
        $scope.showCourses = function (pg, size) {
        	MainService.getAllCourses(pg, size).then(function (response) {
               if (response.data.response_code !== 400) {
            	   $scope.courses = response.data;
               } else {
                   $scope.showDialog('Error', response.data.message);
               }
        	});
        };
        
        $scope.totalPages = function (){
        	MainService.getTotalCourses().then(function(response){
        		$scope.totalPage = Math.ceil(response.data/$scope.size); 
        		console.log($scope.totalPage);
        		$scope.total = response.data;
        	})
        };
        
        $scope.setNew = function(size) {
        	$scope.size = size;
        	$scope.showCourses(0, size);
        	$scope.totalPage = Math.ceil($scope.total/size); 
        };
        
        $scope.totalArray = function (){
        	return new Array($scope.total);
        };
        
        $scope.loadView = function (id, type) {
        	$scope.view = "details";
        	$scope.mode = type;
        	if(id == 'new'){
        		$scope.course = {};
        		$scope.course.graphic = {};
        		$scope.course.isNew = true;
        	} else {
        		MainService.getCourseDetails(id).then(function(response){
            		$scope.course = response.data;
            	});
        	}
        };
        
        $scope.deleteCourse = function (id) {
        	MainService.deleteCourse(id).then(function(response){
        		if(response.data.response_code == 200){
        			$scope.showDialog('Success', response.data.message);
        			$scope.showCourses($scope.pageNo-1, $scope.size);
        			$scope.totalPages();
        		} else {
        			$scope.showDialog('Error', response.data.message);
        		}
        	});
        	
        };
        
        $scope.showDialog = function (title, message) {
            var alert = $mdDialog.alert({
                title: title,
                textContent: message,
                ok: 'Close'
            });
            $mdDialog.show(alert).finally(function () {
                alert = undefined;
            });
        };
        
        $scope.setPage = function (page) {
        	$scope.pageNo = page;
        	$scope.showCourses($scope.pageNo-1, $scope.size);
        };
        
        $scope.saveCourse = function (course) {
        	var isValid = $scope.validate(course);
        	if(isValid){
        		MainService.save(course).then(function(response){
        			if(response.data.response_code == 200){
            			$scope.showDialog('Success', response.data.message);
            			$scope.view = "main";
            			$scope.showCourses($scope.pageNo-1, $scope.size);
            			$scope.totalPages();
            		} else {
            			$scope.showDialog('Error', response.data.message);
            		}
        		});
        	} else {
        		return ;
        	}
        };
        
        $scope.validate = function (course) {
        	$scope.error = "";
        	var regex1 = /^[a-zA-Z0-9' '.-]+$/;
        	var regex2 = /^[a-zA-Z]+$/;
        	var regex3 = /^[}{][)(><]+$/;
        	if(Number.isNaN(course) || course.time<1){
        		$scope.error = $scope.error + "\nPlease enter a valid numeric value for time" 
        	}
        	if(regex1.test(course.title) == false){
        		$scope.error = $scope.error + "\nPlease enter a valid title"
        	}
        	if(regex2.test(course.skill) == false){
        		$scope.error = $scope.error + "\nPlease enter a valid skill"
        	}
        	if(regex2.test(course.language) == false){
        		$scope.error = $scope.error + "\nPlease enter a valid skill"
        	}
        	if(regex1.test(course.graphic.alt) == false){
        		$scope.error = $scope.error + "\nPlease enter a valid Image name"
        	}
        	if(regex3.test(course.graphic.src) == true){
        		$scope.error = $scope.error + "\nPlease enter a valid Image Url"
        	}
        	
        	if($scope.error != ""){
        		return false;
        	}else{
        		return true;
        	}
        }

        $scope.initialize();
    });
