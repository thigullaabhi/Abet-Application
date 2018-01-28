<!-- 
Project Name  :Abet Application
Module Name   :Course Learning Objectives to Student Outcomes Mapper
Problem		  :Design a page that lets user define relationship between Course Learning Objectives and Student Outcomes
Solution      :Design a page that pulls out all the course learning objectives of particular course and Student Outcomes
			   and Display them in N*M fashion with an input reader at each N*M location.
Description   :This page pulls out all the Course Learning Objectives of a particular course as well as the Student Outcomes
               and displays them in a N*M fashion in a table. each table cell is provided with the combo box having I,R,A and 
               - as options. user selects from the available options as a relationship for a particular Course Learning
               Objective and Student Outcome. upon updating the page, the mapper or relationship between CLO and SO is also
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
<title>Course Learning Objectives to Student Outcomes Mapper</title>
<link rel="stylesheet" type="text/css" href="Style.css"> 				<!-- css code for navigation bar -->
</head>
<body>
<h1 align="center"><span style="color:#660000">ABET</span>Application</h1>
<form>
<div id=nav><br>															<!--list having contents of navigation bar  -->
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
<h1>Course Learning Objectives to Student Outcomes Mapper</h1>

<c:choose>

<c:when test="${empty id}">												  <!-- checking if user has selected from combo box-->

<form method="get" action="CLOtoSO">									  <!-- Attempt to pull the values -->	  

<select name="id">													      <!--combo box to display course id  -->
<c:forEach items="${CourseIdMap}" var="Course">							  <!-- accessing the key and value of CourseIdMap from back end if id is empty-->
    <option value="${Course.value}">${Course.key}</option>
</c:forEach>
</select>
<input type="submit" value="Select" />
</form>
</c:when>

<c:otherwise>															  <!--  if user has selected from combo box mapper is displayed-->

<table border="1">
<tr>
   <td>&nbsp;</td>
<c:forEach items="${SOIdsMap}" var="SOId">								  <!--Attempt to pull key value pair of SoIdsMap hash map  -->
<td title="${SODescriptionsMap[SOId.value]}">${SOId.key}</td>			  <!-- Creating tool tip for SOId.key  -->
</c:forEach>
</tr>
<c:forEach items="${CLODescriptionsMap}" var="CLODescription">			  <!--Attempt to pull key value pair of CLODescriptionMap hash map  -->
<tr>
<td>${CLODescription.value}</td>
<c:forEach items="${SOIdsMap}" var="SOId">

  
  <c:set var="CLOtoSOpair" value="${CLODescription.key}-${SOId.value}" />
  <input form="CLOtoSOform" type="hidden" value="${CLOtoSOMap[CLOtoSOpair]}" name="prev-${CLODescription.key}_${SOId.value}" /> <!-- Attempt to pull previously selected value -->
  <td><select form="CLOtoSOform" name="map-${CLODescription.key}_${SOId.value}">
    <option value="-">-</option>
    <option value="A" <c:if test="${CLOtoSOMap[CLOtoSOpair] eq 'A'}">selected</c:if> >A</option><!-- checking if previous selected value is A -->
    <option value="I" <c:if test="${CLOtoSOMap[CLOtoSOpair] eq 'I'}">selected</c:if> >I</option><!-- checking if previous selected value is I -->
    <option value="R" <c:if test="${CLOtoSOMap[CLOtoSOpair] eq 'R'}">selected</c:if> >R</option><!-- checking if previous selected value is R -->
  </select></td>
  
</c:forEach>
</tr>
</c:forEach>

</table>

<form method="post" action="CLOtoSO" id="CLOtoSOform">                  <!-- Attempt to update the page as well as data base -->
  <input type="hidden" name="id" value="${id}">
  <input type="submit" value="Update" />
</form>
</c:otherwise>
</c:choose>
</body>
</html>
