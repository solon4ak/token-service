<%--@elvariable id="entryForm" type="ru.tokens.site.controller.EntryController.EntryForm"--%>
<%--@elvariable id="token" type="ru.tokens.site.entities.Token"--%>
<%--@elvariable id="user" type="ru.tokens.site.entities.User"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:basic_bs_three_col_tkn htmlTitle="Token-${token.uuidString} :: Editing Medical Entry" 
                                 bodyTitle="Изменить запись">

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
            <a class="nav-link" href="<c:url value="/token/user/med/entry/list" />">
                Записи
            </a>
            <div class="dropdown-divider"></div>
            <a class="nav-link" href="<c:url value="/user/view" />">
                Пользователь
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
                            Прикрепленный файлы
                        </form:label> 
                        <c:if test="${entry.numberOfAttachments > 0}">
                            <table class="table table-sm">
                                <thead>
                                    <tr>
                                        <th>Название</th>
                                        <th>Размер</th>     
                                        <th>Действия</th>
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
                                                <a href="<c:url 
                                                       value="/token/user/med/entry/${entry.id}/${attachment.id}/delete" />">
                                                    Удалить
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
                            До 5 Mb/файл
                        </small>
                    </div>
                    <div class="form-group row">
                        <button class="btn btn-primary" type="submit">Подтвердить</button>                
                    </div>
                </form:form>
            </div>
        </div>
    </jsp:body>

</template:basic_bs_three_col_tkn>