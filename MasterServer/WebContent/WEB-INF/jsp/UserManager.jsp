<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ page
	import="domain.user.User,domain.user.UserType,java.math.BigInteger"%>
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
			action="${pageContext.request.contextPath}/controller?command=updateuser&responsetype=jsp"
			name="POST.updateuser">
			<table>
				<tr>
					<th colspan="2">Update Parameters</th>
				</tr>
				<tr>
					<td>E-mail:</td>
					<td><input type="text" name="email" value='<%= ((User)request.getAttribute("user")).getEmail() %>' /></td>
				</tr>
				<tr>
					<td>Password:</td>
					<td><input type="text" name="password" value = '<%= ((User)request.getAttribute("user")).getPassword() %>'/></td>
				</tr>
				<tr>
					<td>User type:</td>
					<td>
						<input type="radio" name="usertype" value="USER_NORMAL" checked='<%= Boolean.toString(UserType.convertEnum(((User)request.getAttribute("user")).getType()) == 0) %>' />Normal User
						<br /> 
						<input type="radio" name="usertype" value="USER_ADVERTISER"  checked='<%=  Boolean.toString(UserType.convertEnum(((User)request.getAttribute("user")).getType()) == 1) %>' />Advertiser User
					</td>
				</tr>
				<tr>
					<td colspan="2"><input type="submit" value="Update User" /></td>
				</tr>
			</table>
		</form>
		<form method="POST"
			action="${pageContext.request.contextPath}/controller?command=deleteuser&userid=<%= ((User)request.getAttribute("user")).getUid().toString() %>&version=<%= Integer.toString(((User)request.getAttribute("user")).getVersion()) %>&responsetype=jsp"
			name="POST.deleteuser">
			<input type="submit" value="Delete User" />
		</form>
		
	</div>
</body>
</html>