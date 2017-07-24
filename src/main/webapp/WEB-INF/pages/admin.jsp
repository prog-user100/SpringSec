<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>Prog.kiev.ua</title>
</head>
<body>
<div align="center">
    <h1>Secret page for admins only!</h1>

    <c:url value="/logout" var="logoutUrl" />
    <p>Click to logout: <a href="${logoutUrl}">LOGOUT</a></p>
    <br/>

    <c:if test="${msg ne null}"><h4>${msg}</h4></c:if>
    <br/>

    <table>
        <tr>
            <th>ID</th>
            <th>LOGIN</th>
            <th>ROLE</th>
            <th>EMAIL</th>
            <th>PHONE</th>
            <th>Update/Delete</th>
            <th>Comments</th>
        </tr>
        <c:forEach items="${usersList}" var="user">
            <tr>
                <td>${user.id}</td>
                <td>${user.login}</td>
                <td>${user.role}</td>
                <td>${user.email}</td>
                <td>${user.phone}</td>
                <td>
                    <a href="/admin/get_user/${user.id}">Update</a> | <a href="/admin/delete_user/${user.id}">Delete</a>

                </td>
                <td>
                    <c:if test="${id eq user.id}" >It's you</c:if>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
