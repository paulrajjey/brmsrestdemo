'use strict';

/* Controllers */

var app = angular.module('ngdemo.controllers', ['chart.js']);



// Clear browser cache (in development mode)
//
// http://stackoverflow.com/questions/14718826/angularjs-disable-partial-caching-on-dev-machine
  app.run(function ($rootScope, $templateCache) {
    $rootScope.$on('$viewContentLoaded', function () {
      $templateCache.removeAll();
    });
  });
  
  
 
   

app.controller('questionareCtrl', function($scope, $http,UserTaskService) {
    $scope.initQuestionare = function () {
	
	// $scope.covplan = { show: false }
	  //var url = 'http://localhost:8280/brms-demo/initQuestionare?'  ;
    	var url = ' http://qachecklistapp-jbosseap.jeyocp.sc.osecloud.com/brms-demo/initQuestionare?'  ;
	  
	  $scope.qaContext = UserTaskService.getUserTaskData(url,$scope); 
	  
	  
    
    }
    $scope.getNaxtQuestion = function (id,qid,qs) {
    	
    	// $scope.covplan = { show: false }
    	// var url = 'http://localhost:8280/brms-demo/nextQuestion?id=' + id  +  '&qaContext.nextQuestion.id=' + qid + '&qaContext.nextQuestion.question=' + qs + '&qaContext.nextQuestion.answer=' + $scope.answer ;

    	var url = ' http://qachecklistapp-jbosseap.jeyocp.sc.osecloud.com/brms-demo/nextQuestion?id=' + id  +  '&qaContext.nextQuestion.id=' + qid + '&qaContext.nextQuestion.question=' + qs + '&qaContext.nextQuestion.answer=' + $scope.answer ;

    	  $scope.qaContext = UserTaskService.getUserTaskData(url,$scope); 
    	  
    	  
        
        }
    
    
   });
	
	
	