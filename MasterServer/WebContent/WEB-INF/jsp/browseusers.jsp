<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List"%>
<%@ page import="domain.user.User"%>
<%@ page import="domain.user.UserType"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<style type="text/css">
a {
	padding-right:20px;
}

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
	width: 92%;
	test-alight: right;
}

input.submit {
	position: absolute;
	right: 10px;
}
</style>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Browse Users</title>
	</head>
	<body>
		<div>
		   <a href="/MasterServer/controller?command=browseusers">Browse Users</a>
		   <a href="/MasterServer/lookupuser.html">Lookup a User</a>
		   <a href="/MasterServer/createuser.html">Create a new User</a>
		</div>
		<h1>Browse Users</h1>
		<table border="1">
			<tr>
				<th>User Id</th>
				<th>User Email</th>
				<th>User Type</th>
				<th>Edit</th>
			</tr>

		<% List<User> users = (List<User>)request.getAttribute("users"); %>
		<% for(User user : users) {%>
			<tr>
				<td><%=user.getUid() %> </td>
				<td><%=user.getEmail() %> </td>
				<%if(UserType.convertEnum(user.getType()) == 0){%>
					<td>Normal </td>
				<%} %>
				<%if(UserType.convertEnum(user.getType()) == 1){ %>
					<td>Advertiser </td>
				<%} %>
				<td><a href="/MasterServer/controller?command=readuser&userid=<%=user.getUid()%>&responsetype=jsp">Edit User</a></td> 
			</tr>
			
		<%} %>
		</table>
		<% if(users.size() == 0){ %>
			<p>No Users to browse</p>
		<%} %>
	</body>
</html>