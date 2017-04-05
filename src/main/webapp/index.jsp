<!DOCTYPE html>
<html ng-app="ngdemo">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">


    <title>Angularjs Demo</title>
  
  
	<link href="Content/bootstrap.min.css" rel="stylesheet" media="screen">
    <link href="Content/customerManagementStyles.css" rel="stylesheet" />
<style>
	.hoverme:hover
	{
	background-color:#F8F9F9;
	}
	.clicked
	{
	background-color:#FBEEE6;
	}
	</style>
	 
    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->
  </head>
 
  <body>
 

 <!--  <div class="container-fluid" ng-controller="caseCtrl" ng-init="getSlack()" /> -->
<div class="container-fluid"  />   <div class="row">
         <div class="col-md-9 ">
      <!-- Static navbar -->
      <div class="navbar navbar-default">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" >Red Hat BRMS  </a>
          <!-- <a class="navbar-brand" href="#">Red Hat BRMS - Medicare Eligibility </a> -->
        </div>
       
      </div>
   
	
	
      <!-- Main component for a primary marketing message or call to action -->
	  <div class="row">
		
			<div class="col-md-3 panel-group" id="accordion">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h4 class="panel-title">
						<a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion" href="#/questionare">Checklist Questionare</a>
							<!-- a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion" href="#panel1">Credit Dispute Process</a> -->
						</h4>
					</div>
					<div id="panel1" class="panel-collapse collapse">
						<div class="panel-body">
							<li><a href="#/questionare">Checklist Questionare</a></li>
						    
							
						</div>
					</div>
				</div>
				
				
				
				
				
				
			</div>
		
        <!-- <canvas id="line" class="chart chart-line" chart-data="data" chart-labels="labels" 
                    chart-legend="true" chart-series="series" chart-click="onClick"></canvas>  -->
		<div ng-view></div>
		
			</div>
			<!--  -->
			 </div>
 	<div class="col-md-3 ">
 	
 	<div ng-controller="caseCtrl">
 		
 		<div class="panel panel-default">
 		
 			<div class="panel-heading">
						<h4 class="panel-title"> Social Media
						<!-- <img src="images/slack1.png" HEIGHT="30" WIDTH="60" BORDER="0" alt="Smiley face" />  -->
						<img src="images/twitter.png" HEIGHT="30" WIDTH="60" BORDER="0" alt="Description" ng-click="getSlack()" />
						<!-- <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion" href="#panel4"> </a>-->
						</h4>
						
			</div>
			<div id="panel4" class="panel-collapse collapse.in">
			
			
					<div class="panel-body">
					
					<!-- <a class="twitter-timeline" data-lang="en" data-theme="light" data-link-color="#2B7BB9" href="https://twitter.com/jeypaulraj">Tweets by jeypaulraj</a> <script async src="//platform.twitter.com/widgets.js" charset="utf-8"></script> -->
					<p>
					<a href="https://twitter.com/jpaulraj5" class="twitter-follow-button" data-show-screen-name="false" data-show-count="false">Follow @jpaulraj5</a><script async src="//platform.twitter.com/widgets.js" charset="utf-8"></script>			  		
					</p>
				
					</div>		
			</div>
		<div id="panel4" class="panel-collapse collapse.in">	
			<div class="panel-body">	
			<p>
				<a class="twitter-follow-button" href="https://twitter.com/jpaulraj5" data-size="large"> Follow @TwitterDev</a>
				</p>	
					</div>		
			</div>
		</div>
	
				
			</div>
	</div>
	
			</div>			
    </div> <!-- /container -->
   <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css" />
  	
		  <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="//code.jquery.com/jquery.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="Scripts/bootstrap.min.js"></script>
   
    <!-- 
    <script src="Scripts/chart.bundle.js"></script>
    <script src="Scripts/chart.bundle.min.js"></script>
     -->

   

		<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.0.8/angular.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.0.8/angular-resource.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.0/angular-animate.js"></script>
 		<script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.2.1/angular-sanitize.js"></script>	
		<script src="//angular-ui.github.io/bootstrap/ui-bootstrap-tpls-1.2.5.js"></script>
		 <script src="Scripts/Chart.min.js"></script>
    	<script src="Scripts/angular-chart.min.js"></script>
	<!-- <script src="//ajax.googleapis.com/ajax/libs/angularjs/1.5.0-beta.2/angular-sanitize.js"></script>-->
	    
	 
     <script src="js/app.js"></script>
    <script src="js/controllers.js"></script>
    <script src="js/services.js"></script>

	
  </body>
</html>

