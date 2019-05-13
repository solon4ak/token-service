<%--@elvariable id="token" type="ru.tokens.site.entities.Token"--%>
<%--@elvariable id="messageEvents" type="java.util.Collection"--%>
<%--@elvariable id="user" type="ru.tokens.site.entities.User"--%>
<%--@elvariable id="message" type="java.lang.String"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:basic htmlTitle="List Message Event" bodyTitle="List Message Event">
    <hr />
    <a href="<c:url context="/tkn" value="/token/user/view"/>">&lt;- Back</a>
    <br /><br />
    <c:out value="Token ID: ${token.uuidString}"/><br />
    <c:out value="User E-mail: ${user.userEmailAddress}"/>
    <hr />
    <c:if test="${message != null}">
        <c:out value="${message}" />
        <hr />
    </c:if>
    <c:choose>
        <c:when test="${fn:length(messageEvents) > 0}">
            <table class="data_table">
                <thead>
                    <tr>
                        <th>Subject</th>
                        <th>Created</th>
                        <th>Started</th>
                        <th>Next checking</th>
                        <th>Check Interval</th>
                        <th>Control</th>
                        <th>Confirm user status</th>
                        <th>Edit</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${messageEvents}" var="event">
                        <tr>
                            <td><c:url value="${event.dataEntry.subject}" /></td>
                            <td><tags:localDate date="${event.createDate}" /></td>
                            <td><tags:localDate date="${event.startDate}" /></td>
                            <c:choose>
                                <c:when test="${event.started}">
                                    <td><tags:localDate date="${event.nextCheckDate}" /></td>
                                </c:when>
                                <c:otherwise>
                                    <td>&ndash;</td>
                                </c:otherwise>
                            </c:choose>
                            <td><c:url value="${event.checkingInterval}" /></td>                            
                            <td>
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
                            </td>
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
                            <td>
                                <a href="<c:url value="/token/user/csdevent/view/${event.id}" />">View</a> /                                                                
                                <a href="<c:url value="/token/user/csdevent/edit/${event.id}" />">Edit</a> / 
                                <a href="<c:url value="/token/user/csdevent/delete/${event.id}" />">Delete</a>
                            </td>
                        </tr>
                    </c:forEach>            
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            List is emty
        </c:otherwise>            
    </c:choose>
    <br /><br />
    <a href="<c:url value="/token/user/csdevent/add" />">Add message Event</a>
</template:basic>