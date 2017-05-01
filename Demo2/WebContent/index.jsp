<!--  
Project Name  :Abet Application
Module Name   :Home
Problem       :Design a Home page through which all the contents of an application can be accessed.
Solution      :Implement a navigation bar through which all the contents of the application is accessed
Description   :This is the home page for the application and is provided for background video for new
Libraries Used:Jstl
-->

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ABET Application</title>
<link rel="stylesheet" type="text/css" href="Style.css"> <!-- css code for navigation bar -->
 <style>
 body{
	font-family: sans-serif;
	text-align: center;
	background-size: 100%;
	background-repeat: no-repeat; 
	
	
	
}
 #nav{
	 width: 100%;
	height: 125px;
	background:#ccc; 
	opacity: 1; 
}
#wrapper{
	height: 50px;
	width: 100%;
	background: #660000;
	opacity: 1;
	
}
#wrapper ul li{
	list-style: none;
	display: inline;
	font-weight: bold;
	padding: 10px;
	opacity: 1;
}
#wrapper a:hover{
color:black

}
#wrapper a{
	color: white;
	text-decoration: underline;
	
}

</style>
</head>
<body background="${pageContext.request.contextPath}/Images/main.png">				<!-- back ground image acting as a splash screen -->
<h1 align="center"><span style="color:#660000">ABET</span>Application</h1>


<form>
 																					<!-- list of contents in navigation bar -->

<div id=nav><br>                                                   
	<div id=wrapper>
	<ul>
	<li><a href="index.jsp">Home</a></li>                                  
	<li><a href="CLOtoSO.jsp">LearningObjectives maps StudentOutcomes</a></li>
	<li><a href="PEOtoSO.jsp">ProgramExpectedOutcome maps StudentOutcomes</a></li>
	<li><a href="SyllabusGenerator.jsp">Syllabus Generator</a></li>
	<li><a href="ContactUs.jsp">About</a></li>
	</ul>
	</div>

</div>

<video width="100%" height="100%" controls="controls" loop="loop" preload="auto" autoplay="true">  <!--back ground video for bulletins -->
<source src="${pageContext.request.contextPath}/Images/movfile.mp4"  type="video/mp4" /> </video>
</form>
</body>
</html>