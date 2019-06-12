<%--@elvariable id="token" type="ru.tokens.site.entities.Token"--%>
<%--@elvariable id="user" type="ru.tokens.site.entities.User"--%>
<%--@elvariable id="contacts" type="java.util.Collection"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:basic_bs_three_col_tkn htmlTitle="${token.uuidString} :: Contacts"
                                 bodyTitle="Contacts">

    <jsp:attribute name="rightColumnContent">
        <nav class="nav flex-column">   
            <a class="nav-link" href="<c:url value="/token/user/contact/add" />">Add</a>
            <div class="dropdown-divider"></div>
            <a class="nav-link" href="<c:url value="/user/view" />">
                User
            </a>
        </nav>
    </jsp:attribute>

    <jsp:body>
        <div class="container">
            <jsp:include page="/WEB-INF/jsp/token_id.jspf" />
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
                                <th>Name</th>
                                <th>Phone</th>
                                <th>E-mail</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${user.contacts}" var="contact">
                                <tr>                
                                    <td>
                                        <c:out value="${contact.firstName}" />&nbsp;
                                        <c:out value="${contact.lastName}" />
                                    </td>
                                    <td>
                                        <c:out value="${contact.phoneNumber}" />
                                    </td>
                                    <td>
                                        <c:out value="${contact.email}" />
                                    </td>
                                    <td>
                                        <div class="btn-group">
                                            <button class="btn btn-secondary btn-sm" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                                Action
                                            </button>
                                            <button type="button" class="btn btn-sm btn-secondary dropdown-toggle dropdown-toggle-split" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                                <span class="sr-only">Toggle Dropdown</span>
                                            </button>
                                            <div class="dropdown-menu">
                                                <a class="dropdown-item" 
                                                   href="<c:url value="/token/user/contact/edit/${contact.contactId}" />">
                                                    Edit
                                                </a>
                                                <a class="dropdown-item" 
                                                   href="<c:url value="/token/user/contact/delete/${contact.contactId}" />">
                                                    Delete
                                                </a>
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:otherwise>            
            </c:choose> 
        </div>
    </jsp:body>
</template:basic_bs_three_col_tkn>
