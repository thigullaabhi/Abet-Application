<!--
Project Name  :Abet Application
Module Name   :Syllabus Generator
Problem       :Design a page that lets the user to access the syllabus of a particular course by its selection.
Solution      :Design a jsp page that lets the user to select from the list of available courses using a list box or combo- 
			   box and display the all the details of a particular subject which are pulled from the database.
Description   :This page is an interface for the user to learn about the details of the subjects such as Instructor Name,
			   Topics Covered, Text Book Prescribed. the user is also provided with the file explorer in order to access, 
			   edit and use the course materials.
Libraries Used:jstl     
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
<div id=nav><br>                                                      <!--list having contents of navigation bar  -->
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
	<h1>ABET Syllabus Generator</h1>
	<c:choose>

		<c:when test="${empty id}">									

		<form method="get" action="SyllabusGenerator">				<!-- Attempt to pull the values -->

			<select name="id">										<!--combo box to display course id  -->
			<c:forEach items="${CourseIdMap}" var="Cours">			<!-- accessing the key and value of CourseIdMap from back end if id is empty-->
				    <option value="${Cours.value}">${Cours.key}</option>
			</c:forEach>
			</select>
			<input type="submit" value="Select" />
		</form>
		</c:when>

		<c:otherwise>												<!-- if id is not empty Instructor,textbook and topics covered are displayed on the page  -->
        
			<table>
				<tr>
					<c:forEach items="${Instructor}" var="Ins">
					<td><label>InstructorsName:</label></td><td>${Ins.value}</td>
					</c:forEach>
				</tr>
				<tr>
				    <c:forEach items="${TextBook}" var="Text">
					<td align="right"><label >TextBook:</label></td><td>${Text.value}</td>
					</c:forEach>
				</tr>
				<tr>
					<c:forEach items="${TopicsCovered}" var="Topics">
					<td align="right"><label>TopicsCovered:</label></td><td>${Topics.value}</td>
					</c:forEach>
				</tr>
			</table>
		</c:otherwise>
	</c:choose>
	<form method="get" action="PDFGenerator">								<!-- form to access the PDF Generator code -->
		<input type="submit" value="GeneratePDF"/>
	</form>
<!-- <form method="post" enctype="multipart/form-data" action="SyllabusGenerator">
<input type="file" name="file" />
<input type="submit" value="upload">
</form> -->
		
		<a href="FileExplorer.jsp">File Explorer</a>								<!-- anchor for accessing file explorer  -->
</body>
</html>