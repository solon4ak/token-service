<%--@elvariable id="eventForm" type="ru.tokens.site.controller.CausedEventController.EventForm"--%>
<%--@elvariable id="token" type="ru.tokens.site.entities.Token"--%>
<%--@elvariable id="emailIntervals" type="java.util.LinkedList"--%>
<%--@elvariable id="contacts" type="java.util.LinkedList"--%>
<%--@elvariable id="attachments" type="java.util.Collection"--%>
<%--@elvariable id="event" type="ru.tokens.site.controller.MessageEvent"--%>
<%--@elvariable id="user" type="ru.tokens.site.entities.User"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:basic_bs_three_col_tkn htmlTitle="${user.token.uuidString} :: Editing Medical Entry" 
                                 bodyTitle="Editing Medical Entry">

    <jsp:attribute name="extraHeadContent">
        <script src="https://cloud.tinymce.com/5/tinymce.min.js?apiKey=i6rk63ssg8q0xzbsnyjrkxichdz2rjlup6i7drwsa82i6i1w"></script>        
        <script>
            tinymce.init({
                selector: '#addProductDescription'
            });
        </script>
    </jsp:attribute>

    <jsp:attribute name="rightColumnContent">
        <nav class="nav flex-column">
            <a class="nav-link" href="<c:url value="/token/user/csdevent/list" />">
                List
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
            <div class="form-group">
                <form:form method="post" enctype="multipart/form-data" modelAttribute="eventForm">
                    <div class="form-group row">
                        <form:label class="h5" path="subject">
                            Тема сообщения
                        </form:label> 
                        <form:input path="subject" type="text" class="form-control bg-light text-dark" 
                                    id="subject" />                    
                    </div>
                    <hr />
                    <div class="form-group row">
                        <form:label class="h5" path="body">
                            Содержание сообщения
                        </form:label>
                        <form:textarea path="body" class="form-control bg-light text-dark" 
                                       id="addProductDescription" rows="3" placeholder="Когда ты получишь это письмо..." />  
                    </div>
                    <hr />
                    <div class="form-group row">
                        <label class="h5" for="contacts">
                            Адресаты
                        </label>
                        <table class="table">
                            <thead>
                                <tr>
                                    <th>Name</th>
                                    <th>Email</th>
                                    <th>Check</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${contacts}" var="contact">
                                    <tr>
                                        <td>
                                            <c:out value="${contact.lastName}" />
                                            &nbsp;<c:out value="${contact.firstName}" />
                                        </td>
                                        <td>
                                            <c:out value="${contact.email}" />
                                        </td>
                                        <td>
                                            <form:checkbox class="form-check-input bg-light text-dark" 
                                                           path="emailContacts" value="${contact.contactId}"/>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>                       
                    </div>                    
                    <hr />
                    <div class="form-group row">
                        <form:label class="h5 pb-2" path="attachments">
                            Прикрепленные файлы
                        </form:label> 
                        <c:if test="${event.dataEntry.numberOfAttachments > 0}">
                            <table class="table table-sm">
                                <thead>
                                    <tr>
                                        <th>Filename</th>
                                        <th>Size</th> 
                                        <th>Action</th>                       
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
                                            <td>
                                                <a href="<c:url value="/token/user/csdevent/${event.id}/${attachment.id}/delete" />">
                                                    Delete
                                                </a>
                                            </td>
                                        </tr>                
                                    </c:forEach>
                                </tbody>
                            </table>
                        </c:if>  
                        <input type="file"  name="attachments" multiple="multiple"
                               class="form-control-file" 
                               aria-describedby="fileHelp" id="attachments" />
                        <small id="fileHelp" class="form-text text-muted">
                            Up to 5 Mb/file
                        </small>
                    </div>
                    <hr />
                    <div class="form-group row">
                        <form:label class="h5" for="emailInterval" path="emailSendingInterval">Sending checking email interval</form:label>
                        <form:select path="emailSendingInterval" class="form-control bg-light text-dark" id="emailInterval">
                            <form:options items="${emailIntervals}" />
                        </form:select>
                    </div>
                    <hr />
                    <div class="form-group row">
                        <button class="btn btn-primary" type="submit">Submit</button>                
                    </div>
                </form:form>
            </div>
        </div>
    </jsp:body>


</template:basic_bs_three_col_tkn>