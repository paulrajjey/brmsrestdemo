'use strict';

// Declare app level module which depends on filters, and services
var app = angular.module('ngdemo', [ 'ngdemo.services', 'ngdemo.controllers']);

    app.config(['$routeProvider', function ($routeProvider) {
    	$routeProvider.when('/questionare', {templateUrl: 'partials/questionare.html', controller: 'questionareCtrl'});
		$routeProvider.otherwise({redirectTo: '/questionare'});
		
    }]);
