<%--@elvariable id="eventForm" type="ru.tokens.site.controller.CausedEventController.EventForm"--%>
<%--@elvariable id="token" type="ru.tokens.site.entities.Token"--%>
<%--@elvariable id="emailIntervals" type="java.util.LinkedList"--%>
<%--@elvariable id="contacts" type="java.util.LinkedList"--%>
<%--@elvariable id="attachments" type="java.util.Collection"--%>
<%--@elvariable id="event" type="ru.tokens.site.controller.MessageEvent"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:basic htmlTitle="Adding Medical Entry" bodyTitle="Add Medical Entry">
    <c:out value="Token ID: ${token.uuidString}"/><br />
    <c:out value="User E-mail: ${token.user.email}"/>
    <hr />
    <a href="<c:url context="/tkn" value="/token/user/view"/>">&lt;- Back</a>
    <form:form method="post" enctype="multipart/form-data" modelAttribute="eventForm">
        <fieldset>
            <legend>Message</legend>
            <form:label path="subject">Subject</form:label>
            <form:input path="subject" />
            <form:label path="body">Message</form:label>
            <form:textarea path="body" rows="5" cols="100" />
        </fieldset>        
        <fieldset>
            <legend>To whom</legend>
            <table class="data_table">
                <thead>
                    <tr>
                        <th>Last Name</th>
                        <th>First Name</th>
                        <th>Email</th>
                        <th>Check</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${contacts}" var="contact">
                        <tr>
                            <td><c:out value="${contact.lastName}" /></td>
                            <td><c:out value="${contact.firstName}" /></td>
                            <td><c:out value="${contact.email}" /></td>
                            <td><form:checkbox path="emailContacts" value="${contact.contactId}"/></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </fieldset>
        <fieldset>
            <legend>Attachments</legend>            
            <c:if test="${fn:length(attachments) > 0}">
                <table class="data_table">
                    <thead>
                        <tr>
                            <th>Filename</th>
                            <th>Size</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${attachments}" var="attachment">
                            <tr>
                                <td>
                                    <c:out value="${attachment.name}" />
                                </td>
                                <td>
                                    <c:out value="${attachment.contentSize}" />
                                </td>
                                <td>
                                    <a href="<c:url context="/tkn" value="/token/user/csdevent/${event.id}/${attachment.id}/delete"/>">
                                        Delete
                                    </a>
                                </td>
                            </tr>                
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
            <br />
            <label>Up to 5 Mb/file</label>
            <input type="file" name="attachments" multiple="multiple"/>
        </fieldset>
        <fieldset>
            <legend>Checking email sending interval</legend>
            <form:select path="emailSendingInterval">
                <form:options items="${emailIntervals}" />
            </form:select>
        </fieldset>
        <hr />
        <input type="submit" value="Submit"/>
    </form:form>
</template:basic>