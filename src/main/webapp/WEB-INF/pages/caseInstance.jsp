<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>

</head>
<body>

<h3>Account holder information</h3>
	<!-- <h1>Process definition details</h1>

	<table>
		<tr>
			<td><b>Process Instance Id</b></td>
			<td>${processInstance.id}</td>
		</tr>
		<tr>
			<td><b>Process Name</b></td>
			<td>${processInstance.processName}</td>
		</tr>
		<tr>
			<td><b>Process Version</b></td>
			<td>${processInstance.processVersion}</td>
		</tr>
		<tr>
			<td><b>Process Description</b></td>
			<td>${processInstance.processInstanceDescription}</td>
		</tr>
		<tr>
			<td><b>State</b></td>
			<td>
				<c:choose>
					<c:when test="${processInstance.state == 1}">Active</c:when>
					<c:when test="${processInstance.state == 2}">Completed</c:when>
					<c:when test="${processInstance.state == 3}">Aborted</c:when>
					<c:otherwise>Unknown</c:otherwise>
				</c:choose>
			</td>
		</tr>
		<tr>
			<td><b>Deployment id</b></td>
			<td>${processInstance.deploymentId}</td>
		</tr>
		<tr>
			<td><b>Date</b></td>
			<td>${processInstance.dataTimeStamp}</td>
		</tr>
	</table>
	<h4>Process Variables</h4>
	<hr />

	<table style="width: 600px" class="reference">
		<tbody>
			<tr>
				<th>Name</th>
				<th>Value</th>
			</tr>
			<c:forEach var="variable" items="${variables}">
				<tr>
					<td><c:out value="${variable.variableId}" /></td>
					<td><c:out value="${variable.newValue}" /></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	  -->
	  <table style="width: 600px" class="reference">
		<tbody>
		<tr>
			<td><c:out value="Customer Name" /></td>
			<td><input type="text" value="${customer.name}" /></td>
		</tr>
		
		<tr>
			<td><c:out value="Address" /></td>
			<td><input type="text" value="${customer.address}" /></td>
		</tr>
		<tr>
			<td><c:out value="city" /></td>
			<td><input type="text" value="${customer.city}" /></td>
		</tr>
		<tr>
			<td><c:out value="state" /></td>
			<td><input type="text" value="${customer.state}" /></td>
		</tr>
		<tr>
			<td><c:out value="zip" /></td>
			<td><input type="text" value="${customer.zip}" /></td>
		</tr>
		
		<tr>
			<td><c:out value="Account type" /></td>
			<td><input type="text" value="${account.type}" /></td>
		</tr>
		
		<tr>
			<td><c:out value="amount" /></td>
			<td><input type="text" value="${account.amount}" /></td>
		</tr>
		
			<!-- <tr>
				<th>Name</th>
				<th>Value</th>
			</tr>
			<c:forEach var="entry" items="${processDefinition.processVariables}">
				<tr>
					<td><c:out value="${entry.key}" /> (<c:out value="${entry.value}" />)</td>
					<td><input type="text" name="${entry.key}" /></td>
				</tr>
			</c:forEach>
			-->
		</tbody>
	</table>
	<c:if test="${processInstance.state == 1}">

	<c:if test="${not empty adhocFragments}">
			<div style="margin: 10px;">
				<h3>Action items</h3>
				<hr />
				<table style="width: 600px" class="reference">
					<tbody>
						
						<c:forEach var="activity" items="${adhocFragments}"
							varStatus="loopCounter">
							<tr>
								<td><c:out value="${loopCounter.count}" /></td>
								<td><a href="start?activityName=${activity}&id=${processInstance.id}"><c:out value="start - ${activity}" /></a></td>
								
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
	</c:if>
	<c:if test="${not empty adhocFragments}">
			<div style="margin: 10px;">
				<h3>Action items in progress</h3>
				<hr />
				<table style="width: 600px" class="reference">
					<tbody>
						
						<c:forEach var="activeactivity" items="${adhocactiveFragments}"
							varStatus="loopCounter">
							<tr>
								<td><c:out value="${loopCounter.count}" /></td>
								<td><c:out value="${activeactivity}" /></a></td>
								
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
	</c:if>
	
		<h3>Add new action item </h4>
		
		<form method="post" action="addNewTask">
			<input type="hidden" name="deploymentId" value="${processInstance.deploymentId}" /> 
			<input type="hidden" name="id" value="${processInstance.id}" /> 
			Task Name:<input type="text" name="name" /><br/>
			Task Type:<select name="taskType" >
                
                <option value="HumanTask">HumanTask</option>
                <option value="SubProcess">SubProcess</option>
                
            </select>
            <br/>
   			Assign to User: <input type="text" name="user" /><br/>
   			Assign to group: <input type="text" name="role" /><br/>
			Task Data:<input type="text" name="data" /><br/> 
			<!-- <select name="select2" size="3" multiple="multiple" tabindex="1">
			 <option value="11">eleven</option>
        	 <option value="12">twelve</option>
        	 <option value="13">thirette</option>
        	 <option value="14">fourteen</option>
        	 <option value="15">fifteen</option>
        	 <option value="16">sixteen</option>
        	 <option value="17">seventeen</option>
        	 <option value="18">eighteen</option>
        	 <option value="19">nineteen</option>
        	 <option value="20">twenty</option>
      </select>
			<hr /> -->
			<input type="submit" name="Add Task" value="submit" />
		</form>
		
		<c:if test="${not empty roles}">
			<div style="margin: 10px;">
				<h3>Available Roles</h3>
				<hr />
				<table style="width: 600px" class="reference">
					<tbody>
						
						<c:forEach var="roleactivity" items="${roles}"
							varStatus="loopCounter">
							<tr>
								<td><c:out value="${loopCounter.count}" /></td>
								<td><c:out value="${roleactivity}" /></a></td>
								
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			
			
	</c:if>
	<h3>Add New Role </h3>
	<form method="post" action="addRole">
	<input type="hidden" name="deploymentId" value="${processInstance.deploymentId}" /> 
			<input type="hidden" name="id" value="${processInstance.id}" /> 
			Role Id: <input type="text" name="role" /><br/>
			<input type="submit" name="Add Role" value="submit" />
			</form>
	<c:if test="${not empty users}">
			<div style="margin: 10px;">
				<h3>Available Users</h3>
				<hr />
				<table style="width: 600px" class="reference">
					<tbody>
						
						<c:forEach var="usersactivity" items="${users}"
							varStatus="loopCounter">
							<tr>
								<td><c:out value="${loopCounter.count}" /></td>
								<td><c:out value="${usersactivity}" /></a></td>
								
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
	</c:if>
	<h3> Add new User </h3>
	<form method="post" action="addUser">
	<input type="hidden" name="deploymentId" value="${processInstance.deploymentId}" /> 
			<input type="hidden" name="id" value="${processInstance.id}" /> 
			User Id: <input type="text" name="user" /><br/>
			<input type="submit" name="Add User" value="submit" />
			</form>
		<!-- <h4>Abort process instance</h4>
		<form method="post" action="abort">
			<input type="hidden" name="deploymentId" value="${processInstance.deploymentId}" /> 
			<input type="hidden" name="id" value="${processInstance.id}" /> 
				
			<hr />
			<input type="submit" name="start" value="Abort process instance" />
		</form>
		<h4>Signal process instance</h4>
		<form method="post" action="signal">
			<b>Signal:</b> <input type="text" name="signal" /><br/>
			<b>Event:</b> <input type="text" name="data" /><br/> 
			<input type="hidden" name="deploymentId" value="${processInstance.deploymentId}" /> 
			<input type="hidden" name="id" value="${processInstance.id}" /> 
			<hr />
			<input type="submit" name="start" value="Signal process instance" />
		</form>
		-->
	</c:if>
	
</body>

</html>