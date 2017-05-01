<!-- 
Project Name  :Abet Application
Module Name   :Program Expected Outcomes to Student Outcomes Mapper
Problem		  :Design a page that lets user define relationship between Program Expected Outcomes and Student Outcomes
Solution      :Design a page that pulls out all the Program Expected Outcomes of particular program and Student Outcomes
			   and Display them in N*M fashion with an input reader at each N*M location.
Description   :This page pulls out all the Program Expected Outcomes of a particular course as well as the Student Outcomes
               and displays them in a N*M fashion in a table. each table cell is provided with the combo box having I,R,A and 
               - as options. user selects from the available options as a relationship for a particular Program Expected 
               Outcomes and Student Outcome. upon updating the page, the mapper or relationship between PEO and SO is also
               updated in data base. 
Libraries Used:Jstl,util
 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.ArrayList,java.util.List" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Program Expected Outcome to Student Outcome Mapper</title>
<link rel="stylesheet" type="text/css" href="Style.css">                     <!-- css code for navigation bar -->
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
table {
    border-collapse: collapse;
    width: 100%;
}

th, td {
    text-align: left;
    padding: 8px;
}




th {
    background-color: #660000;
    color:  #660000;
}
</style>
</head>

<body>
<h1 align="center"><span style="color:#660000">ABET</span>Application</h1>
<form>
																	<!--list having contents of navigation bar  -->
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
<h1>Program Expected Outcome to Student Outcome Mapper</h1>

<c:choose>

<c:when test="${empty pid}">																<!-- checking if user has selected from combo box -->

<form method="get" action="PEOtoSO">														<!-- Attempt to pull the values -->

<select name="pid">																			<!--combo box to display Program id  -->
<c:forEach items="${ProgramIdMap}" var="program">											<!-- accessing the key and value of CourseIdMap from back end if id is empty-->
    <option value="${program.value}">${program.key}</option>
</c:forEach>
</select>
<input type="submit" value="Select" />
</form>
</c:when>
<c:otherwise>

<table border="1">
<tr>
   <td>&nbsp;</td>
<c:forEach items="${SOIdsMap}" var="SOId">													<!--Attempt to pull key value pair of SOIdsMap hash map  -->
<td title="${SODescriptionsMap[SOId.value]}">${SOId.key}</td>								<!-- Creating tool tip for SOId.key  -->
</c:forEach>
</tr>
<c:forEach items="${PEODescriptionsMap}" var="PEODescription">								<!--Attempt to pull key value pair of PEODescriptionMap hash map  -->
<tr>
<td>${PEODescription.value}</td>
<c:forEach items="${SOIdsMap}" var="SOId">

  
  <c:set var="PEOtoSOpair" value="${PEODescription.key}-${SOId.value}" />
  <input form="PEOtoSOform" type="hidden" value="${PEOtoSOMap[PEOtoSOpair]}" name="prevs-${PEODescription.key}_${SOId.value}" />
  <td><select form="PEOtoSOform" name="maps-${PEODescription.key}_${SOId.value}">
    <option value="-">-</option>
    <option value="A" <c:if test="${PEOtoSOMap[PEOtoSOpair] eq 'A'}">selected</c:if> >A</option><!-- checking if previous selected value is A -->
    <option value="I" <c:if test="${PEOtoSOMap[PEOtoSOpair] eq 'I'}">selected</c:if> >I</option><!-- checking if previous selected value is I -->
    <option value="R" <c:if test="${PEOtoSOMap[PEOtoSOpair] eq 'R'}">selected</c:if> >R</option><!-- checking if previous selected value is R -->
  </select></td>
  
</c:forEach>
</tr>
</c:forEach>

</table>

<form method="post" action="PEOtoSO" id="PEOtoSOform">                                           <!-- Attempt to update the page as well as data base -->
  <input type="hidden" name="pid" value="${pid}">
  <input type="submit" value="Update" />
</form>
</c:otherwise>
</c:choose>

</body>
</html>