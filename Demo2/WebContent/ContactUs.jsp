<!--  
Project Name  :Abet Application
Module Name   :About
Description   :This page provides the functionality of the web application and details of the developers. 
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
<!-- css code for navigation bar  -->
<style>
 body{
	font-family: sans-serif;
	text-align: center;
	background-size: 100%;
	background-repeat: no-repeat; 
	
	
	
}
 #nav{
	 width: 1600px;
	height: 125px;
	background:#ccc; 
	opacity: 1; 
}
#wrapper{
	height: 50px;
	width: 1600px;
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
<body>
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
</form>
<p align="justify"><span style="color:#660000">ABET</span> is an accrediting body.  As part of the accreditation process, an 
institution needs so show how the learning objectives in the courses it 
offers for a particular program map to the student learning objectives 
which the ABET endorses.  This is to say that ABET wants students in an 
accredited program to learn certain things and this mapping illustrates 
that it would be a possibility.</p>

<p align="justify">It is also important that the material covered in a course match up with 
specific course learning objectives.  For this reason, it is helpful to 
create a course syllabus which contains the course learning objectives 
and how they map to ABET's student learning objectives, the topics 
covered in the course, textbook used and so forth.  Generating course 
syllabi is a common task and it would be helpful to automate the process.</p>
<h5 align="left">Supervised By</h5><h6 align="left">Eickholt, Jesse L,phd</h6><h6 align="left">Assistant Professor Department of Computer Science</h6><h6 align="left">Central Michigan University</h6>
<h5 align="left">Developed By</h5><h6 align="left">Abhilash Reddy Thigulla</h6>
</body>
</html>