<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
			    for(int i = 0; i < )
			    if (rand < .1) {
			        out.println("you win!");
			    } else {
			        out.println("try again");
			    }
			%>
				<td>Maximum user password length</td>
				<td><input type="text" name="MaxPasswordLength" /></td>
			</tr>
		</table>
		<p><input type="submit" name="Update Server Configuration" value="ServerConfiguration" /></p>
	</form>
</body>
</html>