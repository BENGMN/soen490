<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ page
	import="domain.user.User,domain.user.UserType,java.math.BigInteger"%>

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
	width: 30%;
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

.noindent {
	text-indent: 0px;
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
		<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.1/jquery.min.js"></script>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>User Manager</title>
	</head>
	
	<body>
		<div>
		   <a href="/MasterServer/controller?command=browseusers">Browse Users</a>
		   <a href="/MasterServer/lookupuser.html">Lookup a User</a>
		   <a href="/MasterServer/createuser.html">Create a new User</a>
			
		</div>
		<div>
			<% User user = (User)request.getAttribute("user"); %>
			<form method="POST"
				action="${pageContext.request.contextPath}/controller?command=updateuser&responsetype=jsp"
				name="POST.updateuser">
				<input id="version" type="hidden" name="version" value="<%=user.getVersion() %>" />
				<input id="userid" type="hidden" name="userid" value="<%=user.getUid() %>" />
				<br />
				<table>
					<tr>
						<th colspan="2">Update Parameters</th>
					</tr>
					<tr>
						<td>E-mail:</td>
						<td><input type="text" name="email" value='<%= user.getEmail() %>' /></td>
					</tr>
					<tr>
						<td>Password:</td>
						<td><input type="password" name="password" value = '<%= user.getPassword() %>'/></td>
					</tr>
					<tr>
						<td>User type:</td>
						<td class="noindent" >
							<input type="radio" name="usertype" value="USER_NORMAL" <% if(UserType.convertEnum(user.getType()) == 0) out.print("checked"); %> />Normal User
							<br /> 
							<input type="radio" name="usertype" value="USER_ADVERTISER" <% if(UserType.convertEnum(user.getType()) == 1) out.print("checked"); %> />Advertiser User
						</td>
					</tr>
				</table>
				<input type="submit" value="Update User" />
			</form>			
		</div>
		
		<div>
			<script type="text/javascript">
				function deleteUser() {
					var userid = $('#userid').val();
					var version = $('#version').val();
					$.ajax({
						type: "DELETE",
						url: "/MasterServer/controller?command=deleteuser&userid="+ userid + "&responsetype=jsp&version=" + version,
						success: function(data) {
							window.location = "/MasterServer/controller?command=browseusers";
						}
					});
				}
			</script>
			
			<button onClick="deleteUser()">Delete User</button>
		</div>
	</body>
</html>