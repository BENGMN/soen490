<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>User Manager</title>
</head>

<body>
<form method="POST"
		action="${pageContext.request.contextPath}/controller?command=createUser"
		name="POST.createUser">
<table>
<tr><th colspan="2">Create a new user</th></tr>
<tr><td>E-mail:</td><td><input type="text" id="email" /></td></tr>
<tr><td>Password:</td><td><input type="text" id="password" /></td></tr>
<tr><td>User type:</td><td><input type="radio" name="type" value="USER_NORMAL" />Normal User<br />
						   <input type="radio" name="type" value="USER_ADVERTISER" />Advertiser User</td>
</tr>
<tr><td colspan="2"><input type="submit" value="submit" /></td></tr>
</table>
</form>
</body>
</html>