<%@ page language="java" contentType="text/html"%>
<!DOCTYPE html>
<html>
<head>
<title>Dictionary site</title>
<link rel="stylesheet" href="Site.css" type="text/css" />
</head>

<% 
	// Check for username
	String welcome_msg = "";
	Object name = session.getAttribute("fullname");
	if (name != null) {
		welcome_msg = "Hello " + name.toString() + ", welcome to the site!";
	}
	
	String dictionaryTable = "";
	Object dictionary = session.getAttribute("dictionary");
	if (dictionary != null) {
		dictionaryTable = dictionary.toString();
	}
%>

<body>
	<% if (name != null) { %>
	
		<div class="splash">
			<h2>Welcome!</h2>
			<p><%=welcome_msg%></p>
			<p>You can use the tools below to search and edit the dictionary's words. You can also use the buttons to view the whole dictionary organized by word or definition.
		</div>
		
		<div class="tools">
			<h2>Tools</h2>
			<form method=POST action="WordServlet">
				<input type="submit" name="display" value="Show all words">
				<input type="submit" name="dump" value="Show all definitions">
				<table style="margin-left:auto; margin-right:auto">
					<tr>
						<td>
							<input type="text" name="word" id="word">
						</td>
						<td>
	  			  			<label for="word" style="font-weight:bold"> Word </label>
	  			  		</td>
	  			  	</tr>
					<tr>
						<td>
    						<input type="text" name="sound" id="sound">
    					</td>
    					<td>
    						<label for="sound" style="font-weight:bold"> Pronunciation </label>
    					</td>
   	 				</tr>
					<tr>
						<td>
		   		 			<select name="class">
		   		 				<option value="select" selected>Select...</option>
			   					<option value="adjective">Adjective</option>
		   						<option value="noun">Noun</option>
		 			  			<option value="intransitive verb">Intransitive Verb</option>
		 		  				<option value="transitive verb">Transitive Verb</option>
				 		  	</select>
				 		</td>
				 		<td>
							<label for="class" style="font-weight:bold"> Part of Speech </label>
			   			</td>
				 	</tr>
				</table>
				<input type="submit" name="select" value="Search word">
				<input type="submit" name="insert" value="Insert word">
				<input type="submit" name="update" value="Update word">
				<input type="submit" name="delete" value="Delete word">
			</form>	
		</div>
		
		<%-- if (session.getAttribute("dictionaryEmpty") != null) { 
			if ((boolean) session.getAttribute("dictionaryEmpty") == false) { --%>
				<%--@ include file="Dictionary.out.xml" --%>
		<%-- } } --%>
		
		<% if (dictionary != null) { %>
			<%=dictionaryTable%>
		<% } %>
	<% } else { %>
	 	<a href="Login.jsp">Please log in to view the site.</a>
	 <% } %>
</body>
</html>