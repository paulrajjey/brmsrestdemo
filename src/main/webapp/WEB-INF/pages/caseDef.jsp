<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
<body>
	<h2>Customer on boarding - Opening new account</h2>

	<!--  <table>
		<tr>
			<td><b>Process Id</b></td>
			<td>${processDefinition.id}</td>
		</tr>
		<tr>
			<td><b>Process Name</b></td>
			<td>${processDefinition.name}</td>
		</tr>
		<tr>
			<td><b>Process Version</b></td>
			<td>${processDefinition.version}</td>
		</tr>
		<tr>
			<td><b>Package</b></td>
			<td>${processDefinition.packageName}</td>
		</tr>
		<tr>
			<td><b>Type</b></td>
			<td>${processDefinition.type}</td>
		</tr>
	</table>
	<h4>Process Variables</h4>
	<hr />-->
	<hr />
	<form method="post" action="new">
	<input type="hidden" name="deploymentId" value="${processDefinition.deploymentId}" />
	<input type="hidden" name="processId" value="${processDefinition.id}" />
	<table style="width: 600px" class="reference">
		<tbody>
		<tr>
			<td><c:out value="Customer Name" /></td>
			<td><input type="text" name="name"  /></td>
		</tr>
		
		<tr>
			<td><c:out value="Address" /></td>
			<td><input  type="text" name= "address"  /></td>
		</tr>
		<tr>
			<td><c:out value="city" /></td>
			<td><input  type="text" name="city"  /></td>
		</tr>
		<tr>
			<td><c:out value="state" /></td>
			<td><input type="text" name="state"   /></td>
		</tr>
		<tr>
			<td><c:out value="zip" /></td>
			<td><input type="text" name="customer.zip"  /></td>
		</tr>
		<!-- 
			<tr>
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
	<TABLE id="account">
        <TR>
                <TD>Account Type</TD>
                <TD>Minimum Balance</TD>
               
        </TR>
        <TR>
                <TD><INPUT type="text" name="accounts[0].type" /></TD>
                <TD><INPUT type="text" name="accounts[0].amount" /></TD>
                <TD><input type="button" value="Add More" onclick="addRow('account')" /> </TD>
                
        </TR>
</TABLE>
	
	<input type="submit" name="start" value="submit"/>
	</form>
</body>
<SCRIPT lang="javascript">
        function addRow(tableID) 
        {
                var table = document.getElementById(tableID);

                var rowCount = table.rows.length;
                var row = table.insertRow(rowCount);
                var counts = rowCount - 1;

                var cell1 = row.insertCell(0);
                var name = document.createElement("input");
                name.type = "text";
                name.name = "accounts[" + counts + "].name";
                cell1.appendChild(name);

                var cell2 = row.insertCell(1);
                var amount = document.createElement("input");
                amount.type = "text";
                amount.name = "accounts[" + counts + "].amount";
                cell2.appendChild(amount);

               

        }
</SCRIPT>
</html>