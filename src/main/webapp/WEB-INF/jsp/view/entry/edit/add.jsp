<%--@elvariable id="entryForm" type="ru.tokens.site.controller.EntryController.EntryForm"--%>
<%--@elvariable id="token" type="ru.tokens.site.entities.Token"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:basic_bs_three_col_tkn htmlTitle="Token-${token.uuidString} :: Adding Medical Entry" 
                                 bodyTitle="Add Medical Entry">

    <jsp:attribute name="extraHeadContent">
        <script src="https://cloud.tinymce.com/5/tinymce.min.js?apiKey=i6rk63ssg8q0xzbsnyjrkxichdz2rjlup6i7drwsa82i6i1w"></script>        
        <script>
            tinymce.init({
                selector: '#addProductDescription'
            });
        </script>
    </jsp:attribute>

    <jsp:attribute name="authContent">
        <jsp:include page="/WEB-INF/jsp/user.jspf" />
    </jsp:attribute>

    <jsp:attribute name="rightColumnContent">
        <nav class="nav flex-column">
            <a class="nav-link" href="<c:url value="/token/user/med/entry/list" />">
                Entries
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
                <form:form method="post" enctype="multipart/form-data" modelAttribute="entryForm">
                    
                    <div class="form-group row">
                        <form:label path="subject">
                            Название записи
                        </form:label> 
                        <form:input path="subject" type="text" class="form-control bg-light text-dark" 
                                    placeholder="Обращение к терапевту" id="subject" />                    
                    </div>
                    <div class="form-group row">
                        <form:label path="body">
                            Содержание записи
                        </form:label>
                        <form:textarea path="body" class="form-control bg-light text-dark" 
                                       id="addProductDescription" rows="3" />  
                    </div>
                    <div class="form-group row">
                        <form:label path="attachments">
                            Прикрепленные файлы
                        </form:label> 
                        <input type="file"  name="attachments" multiple="multiple"
                               class="form-control-file" 
                               aria-describedby="fileHelp" id="attachments" />
                        <small id="fileHelp" class="form-text text-muted">
                            Up to 5 Mb/file
                        </small>
                    </div>
                    <div class="form-group row">
                        <button class="btn btn-primary" type="submit">Submit</button>                
                    </div>
                </form:form>
            </div>
        </div>
    </jsp:body>

</template:basic_bs_three_col_tkn>