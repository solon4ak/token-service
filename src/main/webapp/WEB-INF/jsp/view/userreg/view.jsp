<%--@elvariable id="user" type="ru.tokens.site.entities.User"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:basic_bs_two_col htmlTitle="User page for ${user.lastName}, ${user.firstName}"
                           bodyTitle="${user.lastName}, ${user.firstName}">

    <jsp:attribute name="authContent">
        <jsp:include page="/WEB-INF/jsp/user.jspf" />
    </jsp:attribute>

    <jsp:attribute name="leftColumnContent">
        <nav class="nav flex-column">
            <a class="nav-link" href="<c:url value="/user/edit" />">
                Edit user
            </a>
            <c:choose>
                <c:when test="${user.postAddress == null}">
                    <a class="nav-link" href="<c:url value="/token/user/postaddress/add" />">
                        Add post address
                    </a>
                </c:when>
                <c:otherwise>
                    <a class="nav-link" href="<c:url value="/token/user/postaddress/view" />">
                        Post address
                    </a>
                </c:otherwise>
            </c:choose>            
        </nav>
    </jsp:attribute>

    <jsp:body>
        <h4>User</h4> 
        <table class="table">
            <tbody>  
                <tr>
                    <td>Registered</td>
                    <td><wrox:formatDate value="${user.registered}" /></td>
                </tr>
                <tr>
                    <td>First name</td>
                    <td><c:out value="${user.firstName}" /></td>
                </tr>
                <tr>
                    <td>Middle name</td>
                    <td><c:out value="${user.middleName}" /></td>
                </tr>
                <tr>
                    <td>Last name</td>
                    <td><c:out value="${user.lastName}" /></td>
                </tr>
                <tr>
                    <td>Birth date</td>
                    <td><tags:localDate date="${user.birthDate}" /></td>
                </tr>
                <tr>
                    <td>E-mail</td>
                    <td><c:out value="${user.userEmailAddress}" /></td>
                </tr>
                <tr>
                    <td>Phone number</td>
                    <td><c:out value="${user.phoneNumber}" /></td>
                </tr>
            </tbody>
        </table>            
    </jsp:body>

</template:basic_bs_two_col>
