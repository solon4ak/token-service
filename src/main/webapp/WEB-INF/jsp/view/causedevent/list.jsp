<%--@elvariable id="token" type="ru.tokens.site.entities.Token"--%>
<%--@elvariable id="messageEvents" type="java.util.Collection"--%>
<%--@elvariable id="user" type="ru.tokens.site.entities.User"--%>
<%--@elvariable id="message" type="java.lang.String"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:basic_bs_three_col_tkn htmlTitle="${token.uuidString} :: Message Events" 
                                 bodyTitle="Message Events">

    <jsp:attribute name="authContent">
        <jsp:include page="/WEB-INF/jsp/user.jspf" />
    </jsp:attribute> 

    <jsp:attribute name="rightColumnContent">
        <nav class="nav flex-column">
            <a class="nav-link" href="<c:url value="/token/user/csdevent/add" />">Add</a>   
            <div class="dropdown-divider"></div>
            <a class="nav-link" href="<c:url value="/user/view" />">
                User
            </a>
        </nav>
    </jsp:attribute>

    <jsp:body>        
        <div class="container">
            <c:if test="${message != null}">
                <div class="alert alert-warning alert-dismissible fade show" role="alert">
                    <c:out value="${message}" />
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>                
            </c:if>
            <div class="card my-2">                
                <div class="card-body">
                    <p class="card-text">
                        <small>
                            <c:out value="Token ID: ${token.uuidString}"/>
                        </small>                        
                    </p>
                </div>
            </div>            
            <c:choose>
                <c:when test="${fn:length(messageEvents) > 0}">
                    <c:forEach items="${messageEvents}" var="event">
                        <div class="card my-2">
                            <div class="card-header">
                                Event: <c:url value="${event.dataEntry.subject}" />
                            </div>
                            <div class="card-body">
                                <table class="table card-text">                                
                                    <tbody>
                                        <tr>
                                            <th>
                                                Created
                                            </th>
                                            <td>
                                                <tags:localDate date="${event.createDate}" />
                                            </td>
                                        </tr>
                                        <tr>
                                            <th>
                                                Started
                                            </th>
                                            <td>
                                                <tags:localDate date="${event.startDate}" />
                                            </td>
                                        </tr>
                                        <tr>
                                            <th>
                                                Next checking
                                            </th>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${event.started}">
                                                        <tags:localDate date="${event.nextCheckDate}" />
                                                    </c:when>
                                                    <c:otherwise>
                                                        &ndash;
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th>
                                                Checking interval
                                            </th>
                                            <td>
                                                <c:url value="${event.checkingInterval}" />
                                            </td>
                                        </tr>
                                        <tr>
                                            <th>
                                                Control
                                            </th>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${event.started}">
                                                        <div class="btn-group">
                                                            <button class="btn btn-secondary btn-sm" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                                                Started
                                                            </button>
                                                            <button type="button" class="btn btn-sm btn-secondary dropdown-toggle dropdown-toggle-split" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                                                <span class="sr-only">Toggle Dropdown</span>
                                                            </button>
                                                            <div class="dropdown-menu">
                                                                <a class="dropdown-item" 
                                                                   href="<c:url value="/token/user/csdevent/stop/${event.id}" />">
                                                                    Stop
                                                                </a>                                                        
                                                            </div>
                                                        </div>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <div class="btn-group">
                                                            <button class="btn btn-secondary btn-sm" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                                                Stopped
                                                            </button>
                                                            <button type="button" class="btn btn-sm btn-secondary dropdown-toggle dropdown-toggle-split" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                                                <span class="sr-only">Toggle Dropdown</span>
                                                            </button>
                                                            <div class="dropdown-menu">
                                                                <a class="dropdown-item" 
                                                                   href="<c:url value="/token/user/csdevent/start/${event.id}" />">
                                                                    Start
                                                                </a>                                                        
                                                            </div>
                                                        </div>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th>
                                                Confirmation status
                                            </th>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${event.waitingProlongation && !event.executed}">
                                                        <a href="<c:url value="/token/user/csdevent/confirm/${event.id}" />">Confirm</a>
                                                    </c:when>
                                                    <c:when test="${!event.waitingProlongation && !event.executed}">
                                                        Prolonged
                                                    </c:when>
                                                    <c:when test="${event.executed}">
                                                        Fired at <tags:localDate date="${event.firedDate}" />
                                                    </c:when>
                                                </c:choose>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th>
                                                &nbsp;
                                            </th>
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
                                                           href="<c:url value="/token/user/csdevent/view/${event.id}" />">
                                                            View
                                                        </a>
                                                        <a class="dropdown-item" 
                                                           href="<c:url value="/token/user/csdevent/edit/${event.id}" />">
                                                            Edit
                                                        </a>
                                                        <a class="dropdown-item" 
                                                           href="<c:url value="/token/user/csdevent/delete/${event.id}" />">
                                                            Delete
                                                        </a>
                                                    </div>
                                                </div>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>      
                            </div>
                        </div>
                    </c:forEach>                
                </c:when>
                <c:otherwise>
                    List is empty
                </c:otherwise>            
            </c:choose>
        </div>
        
    </jsp:body>

</template:basic_bs_three_col_tkn>