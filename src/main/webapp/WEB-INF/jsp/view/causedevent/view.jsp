<%--@elvariable id="event" type="ru.tokens.site.entities.MessageEvent"--%>
<%--@elvariable id="token" type="ru.tokens.site.entities.Token"--%>
<%--@elvariable id="user" type="ru.tokens.site.entities.User"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:basic_bs_three_col_tkn htmlTitle="${user.token.uuidString} :: View Message Event" 
                                 bodyTitle="Event #${event.id}: ${event.dataEntry.subject}">

    <jsp:attribute name="authContent">
        <jsp:include page="/WEB-INF/jsp/user.jspf" />
    </jsp:attribute> 

    <jsp:attribute name="rightColumnContent">
        <nav class="nav flex-column">
            <a class="nav-link" href="<c:url value="/token/user/csdevent/list" />">
                List
            </a>
            <a class="nav-link" href="<c:url value="/token/user/csdevent/edit/${event.id}" />">
                Edit
            </a>
            <a class="nav-link" href="<c:url value="/token/user/csdevent/delete/${event.id}" />">
                Delete
            </a>
            <a class="nav-link" href="<c:url value="/token/user/csdevent/add" />">
                Add
            </a>
            <div class="dropdown-divider"></div>
            <a class="nav-link" href="<c:url value="/user/view" />">
                User
            </a>
        </nav>
    </jsp:attribute>

    <jsp:body>
        <div class="container">
            <jsp:include page="/WEB-INF/jsp/token_id.jspf" />
            <c:if test="${event.startDate != null}">
                <div class="card my-2">                
                    <div class="card-body">
                        <p class="card-text">
                            <small>            
                                Created: <tags:localDate date="${event.createDate}" />
                            </small>
                        </p>
                    </div>
                </div>
            </c:if>

            <div class="card my-2">
                <div class="card-header">
                    Status
                </div>
                <div class="card-body">
                    <p class="card-text">
                        <c:choose>
                            <c:when test="${event.started}">
                                <b>Started</b> /
                                <a href="<c:url value="/token/user/csdevent/stop/${event.id}" />">Stop</a>
                            </c:when>
                            <c:otherwise>
                                <a href="<c:url value="/token/user/csdevent/start/${event.id}" />">Start</a> /
                                <b>Stopped</b>
                            </c:otherwise>
                        </c:choose>
                    </p>
                </div>
            </div>

            <div class="card my-2">
                <div class="card-header">
                    Subject
                </div>
                <div class="card-body">
                    <p class="card-text">
                        <c:out value="${event.dataEntry.subject}" />
                    </p>
                </div>
            </div>
            <div class="card my-2">
                <div class="card-header">
                    Content
                </div>
                <div class="card-body">
                    <p class="card-text">
                        <c:out value="${event.dataEntry.body}" escapeXml="false" />
                    </p>
                </div>
            </div>

            <c:if test="${event.dataEntry.numberOfAttachments > 0}">
                <div class="card my-2">
                    <div class="card-header">
                        Attachments: <c:out value="${event.dataEntry.numberOfAttachments}" />
                    </div>
                    <div class="card-body">
                        <table class="table card-text">
                            <thead>
                                <tr>
                                    <th>Filename</th>
                                    <th>Size</th>                    
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${event.dataEntry.attachments}" var="attachment">
                                    <tr>
                                        <td>
                                            <c:out value="${attachment.name}" />
                                        </td>
                                        <td>
                                            <c:out value="${attachment.contentSize}" />
                                        </td>                        
                                    </tr>                
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </c:if>  

            <div class="card my-2">
                <div class="card-header">
                    Contacts
                </div>
                <div class="card-body">
                    <table class="table card-text">
                        <thead>
                            <tr>
                                <th>Name</th>
                                <th>Email</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${event.contacts}" var="contact">
                                <tr>
                                    <td>
                                        <c:out value="${contact.lastName}" />&nbsp;
                                        <c:out value="${contact.firstName}" />
                                    </td>
                                    <td><c:out value="${contact.email}" /></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>      
                </div>
            </div>

            <div class="card my-2">
                <div class="card-header">
                    Sending checking email interval
                </div>
                <div class="card-body">
                    <p class="card-text">
                        <c:out value="${event.checkingInterval}" />
                    </p>
                </div>
            </div>
        </div>        
    </jsp:body>
</template:basic_bs_three_col_tkn>