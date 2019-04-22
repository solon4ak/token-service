<%--@elvariable id="eventForm" type="ru.tokens.site.controller.CausedEventController.EventForm"--%>
<%--@elvariable id="token" type="ru.tokens.site.entities.Token"--%>
<%--@elvariable id="emailIntervals" type="java.util.LinkedList"--%>
<%--@elvariable id="contacts" type="java.util.LinkedList"--%>
<%--@elvariable id="eventForm" type="ru.tokens.site.controller.MessageEventController.EventForm"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:basic htmlTitle="Adding Message Event" bodyTitle="Add Message Event">
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
            <br /> 
            <a href="<c:url context="/tkn" value="/token/user/contact/add" />">Add contact</a>
        </fieldset>
        <fieldset>
            <legend>Attachments</legend>
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
