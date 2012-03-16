<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="application.ServerParameters,domain.serverparameter.ServerParameter" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script>
function onKeyInvalid(obj) {
	if(!/^\d+\.?\d*$/.test(obj.value))
		obj.value = "";
}
</script>
<style type="text/css">
h1
{
text-align:center;
}
table
{
border-collapse:collapse;
width:100%;
}
table, td, th
{
border:1px solid black;
}
th
{
height:40px;
text-align:center;
}
td
{
text-indent:10px;
}
input.text
{
width:95%;
test-alight:right;
}
input.submit
{
position:absolute;
right:10px;
}
</style>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Server Configuration Utility</title>
</head>
<body>

	<form method="POST" action="${pageContext.request.contextPath}/controller?command=updateserverparameters&source=client" name="POST.updateServerConfiguration">
		<h1>Server Configuration Utility ${pageContext.request.contextPath}</h1>
		<table border="1">
			<tr>
				<th>Parameter Name</th>
				<th>Parameter Description</th>
				<th>Parameter Value</th>
			</tr>
		
			<tr>
			<%
			    ServerParameters params = (ServerParameters)request.getAttribute("serverParameters");

		    	ServerParameter minMessageSizeBytes = params.get("minMessageSizeBytes");
				out.println("<tr> \n");
			    out.println("	<td>" + minMessageSizeBytes.getParamName() + "</td> \n");
			    out.println("	<td>" + minMessageSizeBytes.getDescription() + "</td> \n");
			    out.println("	<td><input class='text' type='text' name='" + minMessageSizeBytes.getParamName() +"' value='" + minMessageSizeBytes.getValue() + "' onkeyup='onKeyInvalid(this)' /></td>");
			    out.println("</tr> \n");

		    	ServerParameter maxMessageSizeBytes = params.get("maxMessageSizeBytes");
				out.println("<tr> \n");
			    out.println("	<td>" + maxMessageSizeBytes.getParamName() + "</td> \n");
			    out.println("	<td>" + maxMessageSizeBytes.getDescription() + "</td> \n");
			    out.println("	<td><input class='text' type='text' name='" + maxMessageSizeBytes.getParamName() +"' value='" + maxMessageSizeBytes.getValue() + "' onkeyup='onKeyInvalid(this)' /></td>");
			    out.println("</tr> \n");
			    
			    ServerParameter messageLifeDays = params.get("messageLifeDays");
				out.println("<tr> \n");
			    out.println("	<td>" + messageLifeDays.getParamName() + "</td> \n");
			    out.println("	<td>" + messageLifeDays.getDescription() + "</td> \n");
			    out.println("	<td><input class='text' type='text' name='" + messageLifeDays.getParamName() +"' value='" + messageLifeDays.getValue() + "' onkeyup='onKeyInvalid(this)' /></td>");
			    out.println("</tr> \n");

			    
		    	ServerParameter advertiserMessageLifeDays = params.get("advertiserMessageLifeDays");
				out.println("<tr> \n");
			    out.println("	<td>" + advertiserMessageLifeDays.getParamName() + "</td> \n");
			    out.println("	<td>" + advertiserMessageLifeDays.getDescription() + "</td> \n");
			    out.println("	<td><input class='text' type='text' name='" + advertiserMessageLifeDays.getParamName() +"' value='" + advertiserMessageLifeDays.getValue() + "' onkeyup='onKeyInvalid(this)' /></td>");
			    out.println("</tr> \n");
			    
		    	ServerParameter minEmailLength = params.get("minEmailLength");
				out.println("<tr> \n");
			    out.println("	<td>" + minEmailLength.getParamName() + "</td> \n");
			    out.println("	<td>" + minEmailLength.getDescription() + "</td> \n");
			    out.println("	<td><input class='text' type='text' name='" + minEmailLength.getParamName() +"' value='" + minEmailLength.getValue() + "' onkeyup='onKeyInvalid(this)' /></td>");
			    out.println("</tr> \n");

			    
		    	ServerParameter maxEmailLength = params.get("maxEmailLength");
				out.println("<tr> \n");
			    out.println("	<td>" + maxEmailLength.getParamName() + "</td> \n");
			    out.println("	<td>" + maxEmailLength.getDescription() + "</td> \n");
			    out.println("	<td><input class='text' type='text' name='" + maxEmailLength.getParamName() +"' value='" + maxEmailLength.getValue() + "' onkeyup='onKeyInvalid(this)' /></td>");
			    out.println("</tr> \n");

		    	ServerParameter minPasswordLength = params.get("minPasswordLength");
				out.println("<tr> \n");
			    out.println("	<td>" + minPasswordLength.getParamName() + "</td> \n");
			    out.println("	<td>" + minPasswordLength.getDescription() + "</td> \n");
			    out.println("	<td><input class='text' type='text' name='" + minPasswordLength.getParamName() +"' value='" + minPasswordLength.getValue() + "' onkeyup='onKeyInvalid(this)' /></td>");
			    out.println("</tr> \n");

		    	ServerParameter maxPasswordLength = params.get("maxPasswordLength");
				out.println("<tr> \n");
			    out.println("	<td>" + maxPasswordLength.getParamName() + "</td> \n");
			    out.println("	<td>" + maxPasswordLength.getDescription() + "</td> \n");
			    out.println("	<td><input class='text' type='text' name='" + maxPasswordLength.getParamName() +"' value='" + maxPasswordLength.getValue() + "' onkeyup='onKeyInvalid(this)' /></td>");
			    out.println("</tr> \n");

		    	ServerParameter speedThreshold = params.get("speedThreshold");
				out.println("<tr> \n");
			    out.println("	<td>" + speedThreshold.getParamName() + "</td> \n");
			    out.println("	<td>" + speedThreshold.getDescription() + "</td> \n");
			    out.println("	<td><input class='text' type='text' name='" + speedThreshold.getParamName() +"' value='" + speedThreshold.getValue() + "' onkeyup='onKeyInvalid(this)' /></td>");
			    out.println("</tr> \n");

		    	ServerParameter defaultMessageRadiusMeters = params.get("defaultMessageRadiusMeters");
				out.println("<tr> \n");
			    out.println("	<td>" + defaultMessageRadiusMeters.getParamName() + "</td> \n");
			    out.println("	<td>" + defaultMessageRadiusMeters.getDescription() + "</td> \n");
			    out.println("	<td><input class='text' type='text' name='" + defaultMessageRadiusMeters.getParamName() +"' value='" + defaultMessageRadiusMeters.getValue() + "' onkeyup='onKeyInvalid(this)' /></td>");
			    out.println("</tr> \n");

			%>
			</tr>
		</table>
		<p><input class='submit' type="submit" value="Update"/></p>

	</form>
</body>
</html>