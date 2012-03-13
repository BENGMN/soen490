<%@ page language="java" contentType="text/html; charset=UTF-8"
<<<<<<< HEAD
	pageEncoding="UTF-8"%>
<%@ page import="java.util.HashMap,java.util.Iterator,java.util.Map"%>
=======
    pageEncoding="UTF-8"%>
    <%@ page import="java.util.HashMap,java.util.Iterator,java.util.Map" %>
>>>>>>> c069e9331614e7a32c13377593be6152c82b1682
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Server Configuration Utility</title>
</head>
<body>
<<<<<<< HEAD
	<form method="POST" action="?command=updateserverparameters">
		<table border="1">
			<tr><td>Parameter Name</td>
			
				<%
					HashMap<String, Double> params = (HashMap<String, Double>) request.getAttribute("serverConfiguration");

					Iterator it = params.entrySet().iterator();
					while (it.hasNext()) {
						Map.Entry pairs = (Map.Entry) it.next();
						out.println("<tr> \n <td>" + pairs.getKey()
								+ "</td> \n <input type='text' name='" + pairs.getKey()
								+ "' value='" + pairs.getValue() + "'/></td>\n");
					}
				%> 
			</tr>
		</table>
		<p>
			<input type="submit" name="Update Server Configuration"
				value="ServerConfiguration" />
		</p>
=======
	<form method="POST" action='FrontController' name="POST.updateServerConfiguration">
		<p>Server Configuration Utility</p>
		<table border="1">
			<tr>
			<%
			    HashMap<String,Double> params = (HashMap<String,Double>)request.getAttribute("serverConfiguration");
			
				Iterator it = params.entrySet().iterator();
			    while (it.hasNext()) {
			        Map.Entry pairs = (Map.Entry)it.next();
			        out.println("<tr> \n");
			        out.println("	<td>" + pairs.getKey() + "</td> \n");
			        out.println("	<td><input type='text' name='" + pairs.getKey() +"' value='" + pairs.getValue() + "'/></td>");
			        out.println("</tr> \n");
			    }
			%>
			</tr>
		</table>
		<p><input type="submit" name="Update Server Configuration" value="Update" /></p>
>>>>>>> c069e9331614e7a32c13377593be6152c82b1682
	</form>
</body>
</html>