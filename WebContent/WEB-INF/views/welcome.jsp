<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Welcome</title>
</head>
<body>
	<!--  come on, guys! let's do this!! -->
	<form action="/SpringJDBC/add" method="post">
		Customer ID <input type="text" name="customerID"> <br>
		Company Name <input type="text" name="companyName"> <br>
		Contact Name <input type="text" name="contactName"> <br>

		<input type="submit" value="SUBMIT">
	</form>
	
	<table>
		<c:forEach items="${message}" var="list">
			<tr>
				<td><c:out value="${list}"></c:out></td>
			</tr>

		</c:forEach>
	</table>

</body>
</html>