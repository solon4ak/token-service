<%--@elvariable id="entryForm" type="ru.tokens.site.controller.EntryController.EntryForm"--%>
<%--@elvariable id="token" type="ru.tokens.site.entities.Token"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:basic htmlTitle="Adding Medical Entry" bodyTitle="Add Medical Entry">
    <c:out value="Token ID: ${token.uuidString}"/><br />
    <c:out value="User E-mail: ${token.user.email}"/>
    <hr />
    <form:form method="post" enctype="multipart/form-data" modelAttribute="entryForm">
        <form:label path="subject">Subject</form:label>
        <form:input path="subject"/><br />
        <form:label path="body">Body</form:label>
        <form:textarea path="body" rows="5" cols="30" />
        <hr />
        <b>Attachments:</b><br /><br />
        <table class="data_table">
            <thead>
                <tr>
                    <th>Filename</th>
                    <th>Size</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${entry.attachments}" var="attachment">
                    <tr>
                        <td>
                            <c:out value="${attachment.name}" />
                        </td>
                        <td>
                            <c:out value="${attachment.contentSize}" />
                        </td>
                        <td>
                            <a href="<c:url value="/token/user/med/entry/${entry.id}/${attachment.id}/delete"/>">
                                Delete
                            </a>
                        </td>
                    </tr>                
                </c:forEach>
            </tbody>
        </table>
        <br /><br />
        <label>Up to 5 Mb/file</label>
        <input type="file" name="attachments" multiple="multiple"/><br /><br />
        <input type="submit" value="Submit"/>
    </form:form>
</template:basic>