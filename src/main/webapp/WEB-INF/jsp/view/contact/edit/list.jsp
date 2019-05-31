<%--@elvariable id="token" type="ru.tokens.site.entities.Token"--%>
<%--@elvariable id="user" type="ru.tokens.site.entities.User"--%>
<%--@elvariable id="contacts" type="java.util.Collection"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:basic_bs_one_col htmlTitle="${token.uuidString} :: Contacts"
                           bodyTitle="Contacts">

    <jsp:attribute name="authContent">
        <jsp:include page="/WEB-INF/jsp/user.jspf" />
    </jsp:attribute> 

    <jsp:body>
        Token ID: <c:out value="${token.uuidString}"/><br />
        User E-mail: <c:out value="${user.userEmailAddress}"/>
        <hr />
        <h4>Contacts</h4>
        <c:choose>
            <c:when test="${contacts == null || fn:length(contacts) == 0}">
                <p>There is no contacts binded to the token!</p>                
            </c:when>
            <c:otherwise>
                <table class="table">   
                    <thead>
                        <!-- here should go some titles... -->
                        <tr>                
                            <th>First name</th>
                            <th>Last Name</th>
                            <th>Phone</th>
                            <th>E-mail</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${user.contacts}" var="contact">
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
                                <td>
                                    <a class="btn btn-primary btn-sm" 
                                       href="<c:url value="/token/user/contact/edit/${contact.contactId}" />">
                                        Edit
                                    </a>
                                    <a class="btn btn-primary btn-sm" 
                                       href="<c:url value="/token/user/contact/delete/${contact.contactId}" />">
                                        Delete
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>            
        </c:choose>    
        <hr />
        <p>
            <a class="btn btn-primary" href="<c:url value="/token/user/contact/add" />">Add contact</a>
        </p>
    </jsp:body>
</template:basic_bs_one_col>
