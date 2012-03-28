<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="application.ServerParameters,domain.serverparameter.ServerParameter"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script type="text/javascript">
	function onKeyInvalid(obj) {
		obj.value = obj.value.replace(/[^0-9]/gi,"");
	}
</script>
<style type="text/css">
h1 {
	text-align: center;
}

table {
	border-collapse: collapse;
	width: 100%;
}

table,td,th {
	border: 1px solid black;
}

th {
	height: 40px;
	text-align: center;
}

td {
	text-indent: 10px;
}

input.text {
	width: 87%;
	test-alight: right;
}

input.submit {
	position: absolute;
	right: 10px;
}
</style>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Server Configuration Utility</title>
</head>
<body>

	<form method="POST"
		action="${pageContext.request.contextPath}/controller?command=updateserverparameters&source=client"
		name="POST.updateServerConfiguration">
		<h1>Server Configuration Utility
			${pageContext.request.contextPath}</h1>
		<table border="1">
			<tr>
				<th>Parameter Name</th>
				<th>Parameter Description</th>
				<th>Parameter Value</th>
			</tr>

			<tr>
				<%
					ServerParameters params = (ServerParameters) request
							.getAttribute("serverParameters");
					ServerParameter param = params.get("minMessageSizeBytes");
				%>
			
			<tr>
				<td><%=param.getParamName()%></td>
				<td><%=param.getDescription()%></td>
				<td><input class='text' type='text'
					name='<%=param.getParamName()%>' value='<%=param.getValue()%>'
					onkeyup='onKeyInvalid(this)' /></td>
			</tr>
			<%
				param = params.get("maxMessageSizeBytes");
			%>
			<tr>
				<td><%=param.getParamName()%></td>
				<td><%=param.getDescription()%></td>
				<td><input class='text' type='text'
					name='<%=param.getParamName()%>' value='<%=param.getValue()%>'
					onkeyup='onKeyInvalid(this)' /></td>
			</tr>
			<%
				param = params.get("advertiserMessageLifeDays");
			%>
			<tr>
				<td><%=param.getParamName()%></td>
				<td><%=param.getDescription()%></td>
				<td><input class='text' type='text'
					name='<%=param.getParamName()%>' value='<%=param.getValue()%>'
					onkeyup='onKeyInvalid(this)' /></td>
			</tr>
			<%
				param = params.get("minEmailLength");
			%>
			<tr>
				<td><%=param.getParamName()%></td>
				<td><%=param.getDescription()%></td>
				<td><input class='text' type='text'
					name='<%=param.getParamName()%>' value='<%=param.getValue()%>'
					onkeyup='onKeyInvalid(this)' /></td>
			</tr>
			<%
				param = params.get("maxEmailLength");
			%>
			<tr>
				<td><%=param.getParamName()%></td>
				<td><%=param.getDescription()%></td>
				<td><input class='text' type='text'
					name='<%=param.getParamName()%>' value='<%=param.getValue()%>'
					onkeyup='onKeyInvalid(this)' /></td>
			</tr>
			<%
				param = params.get("minPasswordLength");
			%>
			<tr>
				<td><%=param.getParamName()%></td>
				<td><%=param.getDescription()%></td>
				<td><input class='text' type='text'
					name='<%=param.getParamName()%>' value='<%=param.getValue()%>'
					onkeyup='onKeyInvalid(this)' /></td>
			</tr>
			<%
				param = params.get("maxPasswordLength");
			%>
			<tr>
				<td><%=param.getParamName()%></td>
				<td><%=param.getDescription()%></td>
				<td><input class='text' type='text'
					name='<%=param.getParamName()%>' value='<%=param.getValue()%>'
					onkeyup='onKeyInvalid(this)' /></td>
			</tr>
			<%
				param = params.get("speedThreshold");
			%>
			<tr>
				<td><%=param.getParamName()%></td>
				<td><%=param.getDescription()%></td>
				<td><input class='text' type='text'
					name='<%=param.getParamName()%>' value='<%=param.getValue()%>'
					onkeyup='onKeyInvalid(this)' /></td>
			</tr>
			<%
				param = params.get("defaultRadiusMeters");
			%>
			<tr>
				<td><%=param.getParamName()%></td>
				<td><%=param.getDescription()%></td>
				<td><input class='text' type='text'
					name='<%=param.getParamName()%>' value='<%=param.getValue()%>'
					onkeyup='onKeyInvalid(this)' /></td>
			</tr>
						<%
				param = params.get("minMessages");
			%>
			<tr>
				<td><%=param.getParamName()%></td>
				<td><%=param.getDescription()%></td>
				<td><input class='text' type='text'
					name='<%=param.getParamName()%>' value='<%=param.getValue()%>'
					onkeyup='onKeyInvalid(this)' /></td>
			</tr>
			<%
				param = params.get("maxMessages");
			%>
			<tr>
				<td><%=param.getParamName()%></td>
				<td><%=param.getDescription()%></td>
				<td><input class='text' type='text'
					name='<%=param.getParamName()%>' value='<%=param.getValue()%>'
					onkeyup='onKeyInvalid(this)' /></td>
			</tr>
			<%
				param = params.get("daysOfGrace");
			%>
			<tr>
				<td><%=param.getParamName()%></td>
				<td><%=param.getDescription()%></td>
				<td><input class='text' type='text'
					name='<%=param.getParamName()%>' value='<%=param.getValue()%>'
					onkeyup='onKeyInvalid(this)' /></td>
			</tr>
			<%
				param = params.get("purgeMonitorInitialDelay");
			%>
			<tr>
				<td><%=param.getParamName()%></td>
				<td><%=param.getDescription()%></td>
				<td><input class='text' type='text'
					name='<%=param.getParamName()%>' value='<%=param.getValue()%>'
					onkeyup='onKeyInvalid(this)' /></td>
			</tr>
			<%
				param = params.get("purgeMonitorDelay");
			%>
			<tr>
				<td><%=param.getParamName()%></td>
				<td><%=param.getDescription()%></td>
				<td><input class='text' type='text'
					name='<%=param.getParamName()%>' value='<%=param.getValue()%>'
					onkeyup='onKeyInvalid(this)' /></td>
			</tr>
		</table>
		<p>
			<input class='submit' type="submit" value="Update" />
		</p>

	</form>
</body>
</html>