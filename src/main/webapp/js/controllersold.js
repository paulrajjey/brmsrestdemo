'use strict';

/* Controllers */

var app = angular.module('ngdemo.controllers', []);


// Clear browser cache (in development mode)
//
// http://stackoverflow.com/questions/14718826/angularjs-disable-partial-caching-on-dev-machine
  app.run(function ($rootScope, $templateCache) {
    $rootScope.$on('$viewContentLoaded', function () {
      $templateCache.removeAll();
    });
  });
  
 app.controller('SlackController', function($scope,$http) {
	 
	
	   $scope.allSlack = function () {
		   var slurl = 'http://localhost:8280/credit-dispute/slackMessages';
		   
		   $http.get(slurl).success(function(result) {  
			   $scope.messages  = result;
			  
	    })
	    
	  };
	  
	  
	  
	  $scope.getSlack = function () {
		   var slurl = 'http://localhost:8280/credit-dispute/slackMessages';
		   
		   $http.get(slurl).success(function(result) {  
			   $scope.messages  = result;
			  
	    })
	  /*  $interval($http.get(slurl).success(function(result) {  
			   $scope.messages  = result;
			  
	    }) , 1000);*/
	  };
	 
	 // $scope.getSlack();
  });

app.controller('AdminProcCtrl', function($scope, $http,UserTaskService) {
    $scope.CreateProcess = function () {
	
	  $scope.tskState = { show: true }
	  var url = 'http://localhost:8280/credit-dispute/newCase?' + 'accountName=' + $scope.accountName + '&merchantName=' + $scope.merchantName + '&transactionDate=' + $scope.transactionDate + '&transactionAmount=' + $scope.transactionAmount + '&areyoudisbutefullamount=' + $scope.areyoudisbutefullamount + '&disputeAmount=' + $scope.disputeAmount + '&disputeReason=' + $scope.disputeReason + '&smsMobile=' + $scope.smsMobile + '&contactPhone=' + $scope.contactPhone + '&emailId=' + $scope.emailId ;
	  
	  $scope.procStat = UserTaskService.getUserTaskData(url,$scope); 
         
		 if($scope.procStat)
	       $scope.procCreate = {show: true};
    
    }
   });
   
	app.controller('caseCtrl', function($scope,$interval,$http,$window,caseService) {
		
		//
		$scope.openChildWindow = function (id) {
			
			 var url1 = 'http://localhost:8280/credit-dispute/image/?id=' +id;
			  //  $scope.htmlurl ='test2';
			   $http.post(url1).success(function(result) {  
				 // $scope.image = $sce.trustAsResourceUrl(result.data);
		       $scope.htmlurl = result;
		       var left = screen.width / 2 - 150, top = screen.height / 2 - 200
				$window.open('http://localhost:8280/credit-dispute/'+result, '', "top=" + top + ",left=" + left + ",width=800,height=1000")
		       
		    }) 
			
			};
			
			$scope.sendSlack = function () {
				  var slsurl = 'http://localhost:8280/credit-dispute/sendSlackmessage/?' + "slakcMessage=" + $scope.slakcMessage;
				  $http.post(slsurl).success(function(result) {  
					  $scope.slakcMessage  = "";
					  
					  var slurl = 'http://localhost:8280/credit-dispute/slackMessages';
					  $http.get(slurl).success(function(result) {  
						  $scope.messages  = result;
					       
					    }) 
				       
				    }) 
				   
				  };
				  
				  
		 $scope.getSlack = function () {
					  var slurl = 'http://localhost:8280/credit-dispute/slackMessages';
					  $http.get(slurl).success(function(result) {  
						  $scope.messages  = result;
					       
					    }) 
					  };		  
			
		$scope.getAllcases = function () {
			$scope.caseList = { show: true }
			$scope.caseList = true
			$scope.caseDet=false 
			$scope.messageList={ show: true } 
	  var url = 'http://localhost:8280/credit-dispute/cases';
	  $scope.caseinstances = caseService.caseinstances(url);

    };
    $scope.caseWork = function(id) {
    	
 	   var url = 'http://localhost:8280/credit-dispute/caseDetail/?id=' +id;
       // $scope.tastdetId = taskid;
 	  $scope.htmlurl ='test';
 	   $scope.processId = id;
 	  $scope.caseDetail= [];
 	  $scope.caseDet =  { show: true }
 	  $scope.caseList = false
 	   $http.post(url).then(function (res) {
          $scope.caseDetail = res.data;  
 	    })
 	    
 	  /* $scope.getSlack = function () {
 		  var slurl = 'http://localhost:8280/credit-dispute/slackMessages';
 		  $http.get(slurl).success(function(result) {  
 				 // $scope.image = $sce.trustAsResourceUrl(result.data);
 			  $scope.messages  = result;
 		       
 		    }) 
 		  };*/
 	  
     }
    
    //update 
    
    $scope.updateProcess = function (id) {
    	
  	  var urlup = 'http://localhost:8280/credit-dispute/update/?id=' + id + '&caseDetail.accountName=' + $scope.caseDetail.accountName + '&caseDetail.merchantName=' + $scope.caseDetail.merchantName + '&caseDetail.transactionDate=' + $scope.caseDetail.transactionDate + '&caseDetail.transactionAmount=' + $scope.caseDetail.transactionAmount + '&caseDetail.areyoudisbutefullamount=' + $scope.caseDetail.areyoudisbutefullamount + '&caseDetail.disputeAmount=' + $scope.caseDetail.disputeAmount + '&caseDetail.disputeReason=' + $scope.caseDetail.disputeReason + '&caseDetail.smsMobile=' + $scope.caseDetail.smsMobile + '&caseDetail.contactPhone=' + $scope.caseDetail.contactPhone + '&caseDetail.emailId=' + $scope.caseDetail.emailId ;
  	  
  	  $http.post(urlup).success(function(result) {  
		 // $scope.image = $sce.trustAsResourceUrl(result.data);
  		 $scope.procUpdate = {show: true};
  		 
      
  	  }) 
      
      }
    //update
    
    //
    $scope.getImage = function(id) {
    // var auth ' $base64.encode("bpmsAdmin:jboss123$"), headers = {"Authorization": "Basic " + auth};
	    var url1 = 'http://localhost:8280/credit-dispute/image/?id=' +id;
	  //  $scope.htmlurl ='test2';
	   $http.post(url1).success(function(result) {  
		 // $scope.image = $sce.trustAsResourceUrl(result.data);
       $scope.htmlurl = result;
       
    }) 
    }
    //
    
    $scope.sendSignal= function(id) {
  	   var url = 'http://localhost:8280/credit-dispute/signal/?id=' +$scope.processId  +  '&signalName=' + $scope.signalName + '&signalData=' + $scope.signalData;
      
  	  $scope.caseDet =  { show: true }
  	   $http.post(url).then(function (result) {
  		   
  		 var ret = result;
       })
       
       var urlc = 'http://localhost:8280/credit-dispute/caseDetail/?id=' +id;
  	  	$http.post(urlc).then(function (result) {
		   
  	  	$scope.caseDetail = res.data;  
       })
      }
     //creare new acc
   
    $scope.newAccount= function(id) {
 	   var url = 'http://localhost:8280/case-management/createNewAccount/?id=' +$scope.processId  +  '&newAccountType=' + $scope.newAccountType + '&minamount=' + $scope.minamount;
       // $scope.tastdetId = taskid;
 	  // $scope.processId = id;
 	  $scope.caseDet =  { show: true }
 	   $http.post(url).then(function (res) {
          $scope.caseDetail = res.data;  
 	    
      
      })
     }
     //end create new account
     //close account
      $scope.closeAccount= function(type) {
 	   var url = 'http://localhost:8280/case-management/closeAccount/?id=' +$scope.processId  +  '&type=' + type;
       // $scope.tastdetId = taskid;
 	  // $scope.processId = id;
 	  $scope.caseDet =  { show: true }
 	   $http.post(url).then(function (res) {
          $scope.caseDetail = res.data;  
 	    
      
      })
     }
     //end close account
     //suspend accond
     //close account
      $scope.suspendAccount= function(type) {
 	   var url = 'http://localhost:8280/case-management/suspendAccount/?id=' +$scope.processId  +  '&type=' + type;
       // $scope.tastdetId = taskid;
 	  // $scope.processId = id;
 	  $scope.caseDet =  { show: true }
 	   $http.post(url).then(function (res) {
          $scope.caseDetail = res.data;  
 	    
      
      })
     }
     //end close account
     //suspend end
     //Start adhoc task
     
     $scope.startAdhocTask = function(taskname) {
 	   var url = 'http://localhost:8280/case-management/startAdhocTask/?id=' +$scope.processId  +  '&taskname=' + taskname;
       
 	  $scope.caseDet =  { show: true }
 	   $http.post(url).then(function (res) {
          $scope.caseDetail = res.data;  
 	    
      
      })
     }
     
     //end adhoctask
   //new dyn task
      
     $scope.newDynamicTask = function() {
 	   var url = 'http://localhost:8280/case-management/addDynamicTask/?id=' +$scope.processId  +  '&taskName=' + $scope.taskName +  '&taskType=' + $scope.taskType +  '&taskUser=' + $scope.taskUser +  '&taskGroup=' + $scope.taskGroup +  '&taskData=' + $scope.taskData;
       
 	  $scope.caseDet =  { show: true }
 	   $http.post(url).then(function (res) {
          $scope.caseDetail = res.data;  
 	    
      
      })
     }
   //end new dyn task
     //creare new user
   
    $scope.newUser= function(id) {
 	   var url = 'http://localhost:8280/case-management/addNewUser/?id=' +$scope.processId  +  '&userId=' + $scope.userId ;
       // $scope.tastdetId = taskid;
 	  // $scope.processId = id;
 	  $scope.caseDet =  { show: true }
 	   $http.post(url).then(function (res) {
          $scope.caseDetail = res.data;  
 	  
 		
      
      })
     }
     //end new user
      //creare new user
   
    $scope.newGroup= function(id) {
 	   var url = 'http://localhost:8280/case-management/addNewRole/?id=' +$scope.processId  +  '&groupId=' + $scope.groupId ;
      
 	  $scope.caseDet =  { show: true }
 	   $http.post(url).then(function (res) {
          $scope.caseDetail = res.data;  
 	   
 		
      
      })
     }
     //end new user
     
     
     // $scope.caseinstance();
	  });
	
   app.controller('UserTaskCtrl', function($scope,$http,UserTaskService) {
    $scope.doSearchTasks = function () {
	   $scope.tskState = { show: true }
	  var url = '/jbpmngwebexample/rest/json/tasks/pending?user=' +$scope.user;
	  $scope.taskresults = UserTaskService.getUserTaskData(url);
     }
 
// app.controller('UserTaskCtrl', function($scope,$http,UserTaskService) {
    $scope.getTasks = function () {
	   $scope.tskState = { show: true }
	  var url = 'http://localhost:8280/case-management/tasks' ;
	  $scope.tasks = UserTaskService.getUserTaskData(url);
     }
   
     $scope.taskWork = function (id) {
	  // $scope.taskDet = { show: true }
	  var url = 'http://localhost:8280/case-management/taskDetail/?id=' + id ;
	  //$scope.task = UserTaskService.getUserTaskData(url);
	  $http.post(url).then(function (res) {
		  $scope.task = res.data;  
		     $scope.taskDet = { show: true }
		     if($scope.taskDet.status == 'Ready'){
		    	 $scope.claim = true;
		     };
		    
		     if($scope.taskDet.status == 'Reserved'){
		    	 $scope.start = true;
		     };
			 
			 
	     
	     })
     }
   
	 $scope.CompleteTask = function(id,status) {
		 $scope.taskDet = { show: true }
		 var url = 'http://localhost:8280/case-management/completeTask/?id=' + id +'&status=' +status ;
	   // var url = '/jbpmngwebexample/rest/json/tasks/compleTask?user=' + $scope.user +'&taskId=' +$scope.tastdetId  +'&priority=' +priority +'&modelNumber=' +modelNumber +'&quantity=' +quantity;
		 $scope.task = UserTaskService.getUserTaskData(url);
		
	 }
	 
	$scope.taskWork1 = function(processId,taskid) {
	   var url = '/jbpmngwebexample/rest/json/tasks/processparams?processId=' +processId;
       $scope.tastdetId = taskid;
	    $scope.processId = processId;
	   
	   $http.post(url).then(function (res) {
         $scope.taskdetldata = res.data;  
	     $scope.taskDet = { show: true }
		 $scope.quantity = res.data.quantity;
		 $scope.modelNumber = res.data.modelNumber;
		 $scope.priority = res.data.priority;
		 
     
     })
    }
	   
	
   });

	