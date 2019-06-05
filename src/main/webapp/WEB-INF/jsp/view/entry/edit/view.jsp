<%--@elvariable id="entry" type="ru.tokens.site.entities.MedicalFormEntry"--%>
<%--@elvariable id="token" type="ru.tokens.site.entities.Token"--%>
<%--@elvariable id="user" type="ru.tokens.site.entities.User"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:basic_bs_three_col_tkn htmlTitle="Token-${token.uuidString} :: View Medical Entry" 
                                 bodyTitle="Entry #${entry.id} : ${entry.subject}">

    <jsp:attribute name="authContent">
        <jsp:include page="/WEB-INF/jsp/user.jspf" />
    </jsp:attribute>

    <jsp:attribute name="rightColumnContent">
        <nav class="nav flex-column">
            <a class="nav-link" href="<c:url value="/token/user/med/entry/list" />">
                Entries
            </a>
            <a class="nav-link" href="<c:url value="/token/user/med/entry/create" />">
                Add entry
            </a>
            <a class="nav-link" href="<c:url context="/tkn" 
                   value="/token/user/med/entry/${entry.id}/edit" />">
                Edit
            </a>
            <a class="nav-link" href="<c:url context="/tkn" 
                   value="/token/user/med/entry/${entry.id}/delete" />">
                Delete
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
            <div class="card my-2">                
                <div class="card-body">                    
                    <p class="card-text">
                        <small>
                            Created: <wrox:formatDate value="${entry.dateCreated}" type="both"
                                             timeStyle="long" dateStyle="full" />                
                        </small>
                    </p>
                </div>
            </div>
            <div class="card my-2">
                <div class="card-header">
                    Subject
                </div>
                <div class="card-body">
                    <p class="card-text">
                        <c:out value="${entry.subject}"/>
                    </p>
                </div>
            </div>
            <div class="card my-2">
                <div class="card-header">
                    Content
                </div>
                <div class="card-body">
                    <p class="card-text">
                        <c:out value="${entry.body}" escapeXml="false"/>
                    </p>
                </div>
            </div>
            <c:if test="${entry.numberOfAttachments > 0}">
                <div class="card my-2">
                    <div class="card-header">
                        Attachments
                    </div>
                    <div class="card-body">
                        <table class="card-text table">
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
                    </div>
                </div>
            </c:if> 
        </div>
    </jsp:body>
</template:basic_bs_three_col_tkn>