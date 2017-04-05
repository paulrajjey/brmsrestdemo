'use strict';
/* Services */
var services = angular.module('ngdemo.services', ['ngResource']);
//get the JSON data using the REST API call

services.factory('UserTaskService', function($http){
     return {   
        getUserTaskData: function(url) {		
            return $http.post(url).then(function(result) {	
                    return result.data;
                   //return "http://localhost:8280/case-management/";
          })
        }
		}; 
		});
		
services.factory('caseService', function($http){
     return {   
        caseinstances: function(url) {		
            return $http.post(url).then(function(result) {	
                   return result.data;
                   //return "http://localhost:8280/case-management/";
          })
        }
		}; 		
		 
	
});


