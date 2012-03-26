<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List"%>
<%@ page import="domain.user.User"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
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
		<br>
		<% List<User> users = (List<User>)request.getAttribute("users"); %>
		<% for(User user : users) {%>
			<div>
				User id: <%=user.getUid() %> <br>
				User email: <%=user.getEmail() %> <br>
				<a href="/MasterServer/controller?command=readuser&userid=<%=user.getUid()%>&responsetype=jsp">View and modify user here.</a> 
				<br> <br>
			</div>
		<%} %>
		<% if(users.size() == 0){ %>
			<p>No Users to browse</p>
		<%} %>
	</body>
</html>