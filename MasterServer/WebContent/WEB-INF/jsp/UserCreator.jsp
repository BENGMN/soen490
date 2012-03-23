<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>User Manager</title>
</head>

<body>
	<div>
		<a href="${pageContext.request.contextPath}/controller?command=userlookup&responsetype=jsp">Lookup a User</a> 
		<a href="${pageContext.request.contextPath}/controller?command=usercreator&responsetype=jsp">Create a User</a>
	</div>
	<div>
		<form method="POST"
			action="${pageContext.request.contextPath}/controller?command=createuser&responsetype=jsp"
			name="POST.createUser">
			<table>
				<tr>
					<th colspan="2">Create a new user</th>
				</tr>
				<tr>
					<td>E-mail:</td>
					<td><input type="text" name="email" /></td>
				</tr>
				<tr>
					<td>Password:</td>
					<td><input type="text" name="password" /></td>
				</tr>
				<tr>
					<td>User type:</td>
					<td><input type="radio" name="usertype" value="USER_NORMAL" />Normal
						User<br /> <input type="radio" name="usertype"
						value="USER_ADVERTISER" />Advertiser User</td>
				</tr>
				<tr>
					<td colspan="2"><input type="submit" value="submit" /></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>