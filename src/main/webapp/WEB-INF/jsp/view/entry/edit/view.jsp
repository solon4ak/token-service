<%--@elvariable id="entry" type="ru.tokens.site.entities.MedicalFormEntry"--%>
<%--@elvariable id="token" type="ru.tokens.site.entities.Token"--%>
<%--@elvariable id="user" type="ru.tokens.site.entities.User"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:basic htmlTitle="View Medical Entry" bodyTitle="Entry #${entry.id} : ${entry.subject}">
    <c:out value="Token ID: ${token.uuidString}"/><br />
    <c:out value="User E-mail: ${user.userEmailAddress}"/>
    <hr />
    <a href="<c:url context="/tkn" value="/token/user/med/view" />">
        <- Back
    </a><br /><br />
    Created: <wrox:formatDate value="${entry.dateCreated}" type="both"
                     timeStyle="long" dateStyle="full" /><br /><br />
    <c:out value="${entry.body}" /><br /><br />
    <c:if test="${entry.numberOfAttachments > 0}">
        <b>Attachments:</b><br /><br />
        <table class="data_table">
            <thead>
                <tr>
                    <th>Filename</th>
                    <th>Size</th>                    
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${entry.attachments}" var="attachment">
                    <tr>
                        <td>
                            <a href="<c:url context="/tkn" 
                                   value="/token/${token.tokenId}/${token.uuidString}/med/entry/view/${entry.id}/attachment/${attachment.name}" />">
                               <c:out value="${attachment.name}" />
                            </a>
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
    <a href="<c:url context="/tkn" 
               value="/token/user/med/entry/${entry.id}/edit" />">
            Edit
        </a>/
        <a href="<c:url context="/tkn" 
               value="/token/user/med/entry/${entry.id}/delete" />">
            Delete
        </a><br /><br />
        <a href="<c:url context="/tkn" value="/token/user/med/entry/create" />">
            Add entry
        </a>   
</template:basic>