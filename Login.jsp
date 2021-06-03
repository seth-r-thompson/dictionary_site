<%@ page language="java" contentType="text/html"%>
<!DOCTYPE html>
<html>
<head>
<title>Dictionary site login</title>
<link rel="stylesheet" href="Site.css" type="text/css" />
</head>
<%
	// Check for error message
	String error_msg = "";
	Object error = session.getAttribute("error");
	if (error != null) {
		error_msg = error.toString();
	}
%>
<body>
	<div class="splash">
		<h2>Welcome!</h2>
		Welcome to a dictionary site for CSE 640. The site hosts a simple dictionary of fake words. To enter, please login.
	</div>
	
	<% if (error != null) { %>
		<h2>Error!</h2>
		<p class="error"><%=error_msg%></p>
	<% } %>
		
	<h2>Login</h2>
		<form method=GET action="LoginServlet">
			<table style="margin-left:auto; margin-right:auto">
				<tr>
					<td>
						<input type="text" name="user_id" id="user_id">
					</td>
					<td>
						<label for="user_id" style="font-weight:bold"> User ID </label>
					</td>
				</tr>
				<tr>
					<td>
						<input type="password" name="password" id="password">
					</td>
					<td>
						<label for="password" style="font-weight:bold"> Password </label>
					</td>
				</tr>
			</table>
			<input type="submit" value="Login">
		</form>	
</body>
</html>