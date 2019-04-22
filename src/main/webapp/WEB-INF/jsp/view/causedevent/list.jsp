<%--@elvariable id="token" type="ru.tokens.site.entities.Token"--%>
<%--@elvariable id="messageEvents" type="java.util.Collection"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:basic htmlTitle="List Message Event" bodyTitle="List Message Event">
    <hr />
    <a href="<c:url context="/tkn" value="/token/user/view"/>">&lt;- Back</a>

    <c:out value="Token ID: ${token.uuidString}"/><br />
    <c:out value="User E-mail: ${token.user.email}"/>
    <hr />
    <c:choose>
        <c:when test="${fn:length(messageEvents) > 0}">
            <table class="data_table">
                <thead>
                    <tr>
                        <th>Created</th>
                        <th>Subject</th>
                        <th>Check Interval</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${messageEvents}" var="event">
                        <tr>
                            <td><tags:localDate date="${event.startDate}" /></td>
                            <td><c:url value="${event.dataEntry.subject}" /></td>
                            <td><c:url value="${event.checkingInterval}" /></td>
                            <td><a href="<c:url context="/tkn" value="/token/user/csdevent/edit/${event.id}" />">Edit</a> / 
                                <a href="<c:url context="/tkn" value="/token/user/csdevent/delete/${event.id}" />">Delete</a>
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
    <a href="<c:url context="/tkn" value="/token/user/csdevent/add" />">Add message Event</a>
</template:basic>