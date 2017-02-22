<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ABET Application</title>
</head>
<body>
<h1>ABET Syllabus Generator</h1>
<c:choose>

<c:when test="${empty id}">

<form method="get" action="SyllabusGenerator">

<select name="id">
<c:forEach items="${CourseIdMap}" var="Cours">
    <option value="${Cours.value}">${Cours.key}</option>
</c:forEach>
</select>
<input type="submit" value="Select" />
</form>
</c:when>

<c:otherwise>

<table>
<tr>
<c:forEach items="${Instructor}" var="Ins">
<td><label>InstructorsName:</label></td><td>${Ins.value}</td>
</c:forEach>
</tr>
<tr>
<td><label>TextBook</label></td><td></td>
</tr>
<tr>
<c:forEach items="${TopicsCovered}" var="Topics">
<td><label>TopicsCovered:</label></td><td>${Topics.value}</td>
</c:forEach>
</tr>
</table>
</c:otherwise>
</c:choose>
</body>
</html>