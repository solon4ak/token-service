<%--@elvariable id="user" type="ru.tokens.site.entities.User"--%>
<%--@elvariable id="address" type="ru.tokens.site.entities.Address"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:basic_bs_two_col htmlTitle="User: ${user.lastName}, ${user.firstName} post address"
                           bodyTitle="Post address">

    <jsp:attribute name="authContent">
        <c:choose>
            <c:when test="${sessionScope['ru.tkn.user.principal'] != null}">
                <a class="btn btn-light text-dark" href="<c:url value="/logout" />">
                    Logout
                </a>
            </c:when>
            <c:otherwise>
                <a class="btn btn-light text-dark" href="<c:url value="/login" />">
                    Login
                </a>
            </c:otherwise>
        </c:choose>
    </jsp:attribute>

    <jsp:attribute name="extraNavigationContent">
        <c:if test="${sessionScope['ru.tkn.user.principal'] != null}">
            <a class="p-2 text-dark" href="<c:url value="/user/view" />">User</a>
        </c:if>
    </jsp:attribute>

    <jsp:attribute name="leftColumnContent">
        <nav class="nav flex-column">
            <a class="nav-link" href="<c:url value="/token/user/postaddress/edit" />">
                Edit
            </a>            
        </nav>
    </jsp:attribute>

    <jsp:body>            
        <h4>Post Address</h4>
        <table class="table">
            <tbody>
                <tr>
                    <td>Zip code</td>
                    <td><c:out value="${address.zipCode}" /></td>
                </tr>
                <tr>
                    <td>Country</td>
                    <td><c:out value="${address.country}" /></td>
                </tr>
                <tr>
                    <td>Region</td>
                    <td><c:out value="${address.region}" /></td>
                </tr>
                <tr>
                    <td>City</td>
                    <td><c:out value="${address.city}" /></td>
                </tr>
                <tr>
                    <td>Street</td>
                    <td><c:out value="${address.street}" /></td>
                </tr>
                <tr>
                    <td>Building</td>
                    <td><c:out value="${address.building}" /></td>
                </tr>
                <tr>
                    <td>Apartment</td>
                    <td><c:out value="${address.apartment}" /></td>
                </tr>
            </tbody>
        </table>
    </jsp:body>

</template:basic_bs_two_col>
