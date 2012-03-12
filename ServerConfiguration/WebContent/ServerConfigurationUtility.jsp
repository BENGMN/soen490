<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Server Configuration Utility</title>
</head>
<body>
	<form method="GET" action='ConfigurationController' name="ServerConfiguration">
		<table border="1">
			<tr>
				<td>Minimum message size</td>
				<td><input type="text" name="MinMessageSize" /></td>
			</tr>
			<tr>
				<td>Maximum Message Size</td>
				<td><input type="text" name="MaxMessageSize" /></td>
			</tr>
			<tr>
				<td>Regular message lifetime</td>
				<td><input type="text" name="RegMessageLife" /></td>
			</tr>
			<tr>
				<td>Advertiser message lifetime</td>
				<td><input type="text" name="AdvMessageLife" /></td>
			</tr>
			<tr>
				<td>Minimum user password length</td>
				<td><input type="text" name="MinPasswordLength" /></td>
			</tr>
			<tr>
				<td>Maximum user password length</td>
				<td><input type="text" name="MaxPasswordLength" /></td>
			</tr>
		</table>
		
		<p><input type="submit" name="Update Server Configuration" value="ServerConfiguration" /></p>
	</form>
</body>
</html>