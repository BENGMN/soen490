<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.util.HashMap,java.util.Iterator,java.util.Map" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Server Configuration Utility</title>
</head>
<body>
	<form method="POST" action='ConfigurationController' name="POST.updateServerConfiguration">
		<table border="1">
			<tr>
			<%
			    HashMap<String,Double> params = (HashMap<String,Double>)request.getAttribute("serverConfiguration");
			
				Iterator it = params.entrySet().iterator();
			    while (it.hasNext()) {
			        Map.Entry pairs = (Map.Entry)it.next();
			        out.println("<tr> \n <td>" + pairs.getKey() + "</td> \n <input type='text' name='" + pairs.getKey() +"' value='" + pairs.getValue() + "'/></td>\n");
			    }
			%>
			</tr>
		</table>
		<p><input type="submit" name="Update Server Configuration" value="ServerConfiguration" /></p>
	</form>
</body>
</html>