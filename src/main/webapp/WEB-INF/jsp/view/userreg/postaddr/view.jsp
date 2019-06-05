<%--@elvariable id="user" type="ru.tokens.site.entities.User"--%>
<%--@elvariable id="address" type="ru.tokens.site.entities.Address"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:basic_bs_three_col htmlTitle="User: ${user.lastName}, ${user.firstName} post address"
                             bodyTitle="Post address">

    <jsp:attribute name="authContent">
        <jsp:include page="/WEB-INF/jsp/user.jspf" />
    </jsp:attribute>

    <jsp:attribute name="rightColumnContent">
        <nav class="nav flex-column">
            <c:choose>
                <c:when test="${user.postAddress == null}">
                    <a class="nav-link" href="<c:url value="/token/user/postaddress/add" />">
                        Add
                    </a>
                </c:when>
                <c:otherwise>
                    <a class="nav-link" href="<c:url value="/token/user/postaddress/edit" />">
                        Edit
                    </a>
                </c:otherwise>
            </c:choose>
        </nav>
    </jsp:attribute>

    <jsp:attribute name="leftColumnContent">
        <nav class="nav flex-column">
            <a class="nav-link" href="<c:url value="/user/view" />">
                User
            </a>
            <a class="nav-link disabled" href="<c:url value="/token/user/postaddress/view" />">
                Post address
            </a>
            <a class="nav-link" href="<c:url value="/token/user/view" />">
                Token
            </a> 
        </nav>
    </jsp:attribute>

    <jsp:body>   
        <c:choose>
            <c:when test="${user.postAddress == null}">
                Post address wasn't added!
            </c:when>
            <c:otherwise>
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
            </c:otherwise>
        </c:choose>        
    </jsp:body>

</template:basic_bs_three_col>
