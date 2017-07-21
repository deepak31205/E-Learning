'use strict';

angular.module('main', [
    'ngMaterial',
    'ui.router',
    'ngSanitize',
    'ui.bootstrap'
])
    .run(function ($rootScope,Config) {
    	
    })
    .config(function ($stateProvider, $urlRouterProvider) {
        $urlRouterProvider.otherwise('/');
        $stateProvider
            .state('home', {
                url: '/',
                templateUrl: 'main/templates/home.html',
                controller: 'HomeCtrl'
            })
    });
