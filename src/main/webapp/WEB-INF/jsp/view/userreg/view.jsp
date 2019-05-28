<%--@elvariable id="user" type="ru.tokens.site.entities.User"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:basic htmlTitle="User page for ${user.lastName}, ${user.firstName}"
                bodyTitle="User: ${user.lastName}, ${user.firstName}">
    <jsp:attribute name="extraNavigationContent">
        <a href="<c:url value="/user/edit" />">Edit user</a><br /><br />
        <c:choose>
            <c:when test="${user.postAddress == null}">
                <a href="<c:url value="/token/user/postaddress/add" />">Add post address</a><br /><br />
            </c:when>
            <c:otherwise>
                <a href="<c:url value="/token/user/postaddress/edit" />">Edit post address</a><br /><br />
            </c:otherwise>
        </c:choose>
        <c:choose>
            <c:when test="${user.token == null}">
                <a href="<c:url value="/token/register" />">Register your Token</a>
            </c:when>
            <c:otherwise>
                <a href="<c:url value="/token/user/view" />">User Token Page</a>
            </c:otherwise>
        </c:choose>
    </jsp:attribute>

    <jsp:body>
        Registered: <wrox:formatDate value="${user.registered}" />
        <hr />
        <h4>User:</h4>
        <table class="data_table">
            <thead>
                <tr>
                    <th>Property</th>
                    <th>Data</th>
                </tr>
            </thead>        
            <tbody>            
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
        
        <c:if test="${user.postAddress != null}">
            <hr />
            <h4>Post Address:</h4>
            <table class="data_table">
                <thead>
                    <tr>
                        <th>Property</th>
                        <th>Data</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>Zip code:</td>
                        <td><c:out value="${user.postAddress.zipCode}" /></td>
                    </tr>
                    <tr>
                        <td>Country:</td>
                        <td><c:out value="${user.postAddress.country}" /></td>
                    </tr>
                    <tr>
                        <td>Region:</td>
                        <td><c:out value="${user.postAddress.region}" /></td>
                    </tr>
                    <tr>
                        <td>City:</td>
                        <td><c:out value="${user.postAddress.city}" /></td>
                    </tr>
                    <tr>
                        <td>Street:</td>
                        <td><c:out value="${user.postAddress.street}" /></td>
                    </tr>
                    <tr>
                        <td>Building:</td>
                        <td><c:out value="${user.postAddress.building}" /></td>
                    </tr>
                    <tr>
                        <td>Apartment:</td>
                        <td><c:out value="${user.postAddress.apartment}" /></td>
                    </tr>
                </tbody>
            </table>
        </c:if>
    </jsp:body>

</template:basic>
