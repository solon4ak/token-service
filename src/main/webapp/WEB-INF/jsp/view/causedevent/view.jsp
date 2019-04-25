<%--@elvariable id="event" type="ru.tokens.site.entities.MessageEvent"--%>
<%--@elvariable id="token" type="ru.tokens.site.entities.Token"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:basic htmlTitle="View Message Event" bodyTitle="Event #${event.id}: ${event.dataEntry.subject}">
    <c:out value="Token ID: ${token.uuidString}"/><br />
    <c:out value="User E-mail: ${token.user.email}"/>
    <hr />
    <a href="<c:url context="/tkn" value="/token/user/csdevent/list" />">
        <- Back
    </a><br /><br />
    Created: <tags:localDate date="${event.startDate}" /><br /><br />
    <c:out value="${event.dataEntry.body}" /><br /><br />
    
    <c:if test="${event.dataEntry.numberOfAttachments > 0}">
        <b>Attachments: <c:out value="${event.dataEntry.numberOfAttachments}" /></b><br /><br />
        
        <table class="data_table">
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
    </c:if>    
    <hr />
    <b>Contacts:</b><br /><br />
    <table class="data_table">
        <thead>
            <tr>
                <th>Last Name</th>
                <th>First Name</th>
                <th>Email</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${event.contacts}" var="contact">
                <tr>
                    <td><c:out value="${contact.lastName}" /></td>
                    <td><c:out value="${contact.firstName}" /></td>
                    <td><c:out value="${contact.email}" /></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>      
    <hr />
    <b>Checking email sending interval:</b>&nbsp;<c:out value="${event.checkingInterval}" />
        <hr />        
        <a href="<c:url context="/tkn" value="/token/user/csdevent/edit/${event.id}" />">
            Edit
        </a>/
        <a href="<c:url context="/tkn" 
               value="/token/user/csdevent/delete/${event.id}" />">
            Delete
        </a><br /><br />
        <a href="<c:url context="/tkn" value="/token/user/csdevent/add" />">
            Add entry
        </a>   
</template:basic>