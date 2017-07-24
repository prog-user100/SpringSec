<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>Prog.kiev.ua</title>
</head>
<body>
<div align="center">

    <c:url value="/admin/edit_user" var="editUrl" />
    <form action="${editUrl}" method="POST">
        ID:<br/><input type="text" name="id" value="${user.id}" readonly/><br/>
        Login:<br/><input type="text" name="login" value="${user.login}" >
        <c:if test="${exists ne null}">
            Such login already exists!
        </c:if><br/>
        Role:<br/><input type="text" name="role" value="${user.role}" ><br/>
        E-mail:<br/><input type="text" name="email" value="${user.email}" ><br/>
        Phone:<br/><input type="text" name="phone" value="${user.phone}"><br/>
        <input type="submit" value="Update" />


    </form>
</div>

</body>
</html>
