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
		<p>Operation failed to complete </p>		
	</div>
</body>
</html>