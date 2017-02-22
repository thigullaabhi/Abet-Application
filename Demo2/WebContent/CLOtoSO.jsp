<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.ArrayList,java.util.List" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Course Learning Objectives to Student Outcomes Mapper</title>
</head>
<body>

<h1>Course Learning Objectives to Student Outcomes Mapper</h1>

<c:choose>

<c:when test="${empty id}">

<form method="get" action="CLOtoSO">

<select name="id">
<c:forEach items="${CourseIdMap}" var="Course">
    <option value="${Course.value}">${Course.key}</option>
</c:forEach>
</select>
<input type="submit" value="Select" />
</form>
</c:when>

<c:otherwise>

<table border="1">
<tr>
   <td>&nbsp;</td>
<c:forEach items="${SOIdsMap}" var="SOId">
<td title="${SODescriptionsMap[SOId.value]}">${SOId.key}</td>
</c:forEach>
</tr>
<c:forEach items="${CLODescriptionsMap}" var="CLODescription">
<tr>
<td>${CLODescription.value}</td>
<c:forEach items="${SOIdsMap}" var="SOId">

  <td>
  
  <select form="CLOtoSOform" name="map-${SOId.value}_${CLODescription.key}">
  <!-- <option value="-">-</option>
  <option value="A">A</option>
  <option value="I">I</option>
  <option value="R">R</option> -->
  
  <c:forEach items="${optionvalues}" var="opval"> 
   <option value="${opval.key}" {opval.key== ${selectedvalue} ? 'selected="selected"' : ''}>${opval.value}</option>
  
  </c:forEach> 
   </select> </td>
  
</c:forEach>
</tr>
</c:forEach>

</table>

<form method="post" action="CLOtoSO" id="CLOtoSOform">
  <input type="hidden" name="id" value="${id}">
  <input type="submit" value="Update" />
</form>
</c:otherwise>
</c:choose>
</body>
</html>