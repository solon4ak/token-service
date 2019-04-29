<%--@elvariable id="token" type="ru.tokens.site.entities.Token"--%>
<%--@elvariable id="user" type="ru.tokens.site.entities.User"--%>
<%--@elvariable id="age" type="java.lang.Integer"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:loggedOut htmlTitle="${token.uuidString}"
                    bodyTitle="Token: ${token.uuidString}">
    Activated: <wrox:formatDate value="${token.activatedDate}" />
    <hr />
    <table class="data_table">
        <tbody>
            <tr>
                <td>
                    <b>Token owner</b>
                </td>
                <td>
                    <c:out value="${user.lastName}, 
                           ${user.firstName} ${user.middleName} " />
                </td>
            </tr>
            <tr>
                <td>
                    Birth date:
                </td>
                <td>
                    <tags:localDate date="${user.birthDate}" />
                </td>
            </tr>
            <tr>
                <td>
                    Age:
                </td>
                <td>
                    <c:out value="${age}" />
                </td>
            </tr>
        </tbody>        
    </table>
    <hr />
    <c:if test="${user.image != null}">
        <a href="<c:url value="/token/image">
               <c:param name="userId" value="${user.userId}" />
        </c:url>">
            <img src="<c:url value="/token/thumb">
                 <c:param name="userId" value="${user.userId}" />
            </c:url>" 
                 alt="${user.lastName}, ${user.firstName}"/>
        </a> 
        <hr />
    </c:if> 
    <h5>Address:</h5>    
    <c:choose>
        <c:when test="${user.tokenAddress == null}">
            There is no address binded to the token!                            
        </c:when>
        <c:otherwise>
            <table class="data_table">
                <!--                <thead>
                                    <tr>
                                        <th>Property</th>
                                        <th>Data</th>
                                    </tr>
                                </thead>-->
                <tbody>
                    <tr>
                        <td>Country:</td>
                        <td><c:out value="${user.tokenAddress.country}" /></td>
                    </tr>
                    <tr>
                        <td>Region:</td>
                        <td><c:out value="${user.tokenAddress.region}" /></td>
                    </tr>
                    <tr>
                        <td>City:</td>
                        <td><c:out value="${user.tokenAddress.city}" /></td>
                    </tr>
                    <tr>
                        <td>Street:</td>
                        <td><c:out value="${user.tokenAddress.street}" /></td>
                    </tr>
                    <tr>
                        <td>Building:</td>
                        <td><c:out value="${user.tokenAddress.building}" /></td>
                    </tr>
                    <tr>
                        <td>Apartment:</td>
                        <td><c:out value="${user.tokenAddress.apartment}" /></td>
                    </tr>
                </tbody>
            </table>
            <br />
        </c:otherwise>
    </c:choose>
    <hr />

    <h5>Contacts:</h5>
    <c:if test="${fn:length(user.contacts) == 0}">
        There is no contacts binded to the token!
    </c:if>
    <table class="data_table">
        <c:if test="${fn:length(user.contacts) > 0}">
            <thead>
                <!-- here should go some titles... -->
                <tr>                
                    <th>First name</th>
                    <th>Last Name</th>
                    <th>Phone</th>
                    <th>E-mail</th>
                </tr>
            </thead>
        </c:if>
        <tbody>
            <c:forEach items="${token.user.contacts}" var="contact">
                <tr>                
                    <td>
                        <c:out value="${contact.firstName}" />
                    </td>
                    <td>
                        <c:out value="${contact.lastName}" />
                    </td>
                    <td>
                        <c:out value="${contact.phoneNumber}" />
                    </td>
                    <td>
                        <c:out value="${contact.email}" />
                    </td>                    
                </tr>
            </c:forEach>
        </tbody>
    </table>     
</template:loggedOut>
