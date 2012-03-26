<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ page
	import="domain.user.User,domain.user.UserType,java.math.BigInteger"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.1/jquery.min.js"></script>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>User Manager</title>
	</head>
	
	<body>
		<div>
			<a href="${pageContext.request.contextPath}/controller?command=browseusers">Browse Users</a> 
			<a href="${pageContext.request.contextPath}/createuser.html">Create a User</a>
		    <a href="${pageContext.request.contextPath}/lookupuser.html">Lookup a User</a>
			
		</div>
		<div>
			<% User user = (User)request.getAttribute("user"); %>
			<form method="POST"
				action="${pageContext.request.contextPath}/controller?command=updateuser&responsetype=jsp"
				name="POST.updateuser">
				<input id="version" type="hidden" name="version" value="<%=user.getVersion() %>" />
				<input id="userid" type="hidden" name="userid" value="<%=user.getUid() %>" />
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
						<td>
							<input type="radio" name="usertype" value="USER_NORMAL" checked='<%= Boolean.toString(UserType.convertEnum(user.getType()) == 0) %>' />Normal User
							<br /> 
							<input type="radio" name="usertype" value="USER_ADVERTISER"  checked='<%=  Boolean.toString(UserType.convertEnum(user.getType()) == 1) %>' />Advertiser User
						</td>
					</tr>
					
					<tr>
						<td colspan="2"><input type="submit" value="Update User" /></td>
					</tr>
				</table>
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