<%--@elvariable id="token" type="ru.tokens.site.entities.Token"--%>
<%--@elvariable id="messageEvents" type="java.util.Collection"--%>
<%--@elvariable id="user" type="ru.tokens.site.entities.User"--%>
<%--@elvariable id="message" type="java.lang.String"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:basic_bs_three_col_tkn htmlTitle="${token.uuidString} :: Message Events" 
                                 bodyTitle="Список сообщений">

    <jsp:attribute name="rightColumnContent">
        <nav class="nav flex-column">
            <a class="nav-link" href="<c:url value="/token/user/csdevent/add" />">Добавить</a>   
            <div class="dropdown-divider"></div>
            <a class="nav-link" href="<c:url value="/user/view" />">
                Пользователь
            </a>
        </nav>
    </jsp:attribute>

    <jsp:body>        
        <div class="container">
            <jsp:include page="/WEB-INF/jsp/token_id.jspf" />
            <c:if test="${message != null}">
                <div class="alert alert-warning alert-dismissible fade show" role="alert">
                    <c:out value="${message}" />
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>                
            </c:if>
                        
            <c:choose>
                <c:when test="${fn:length(messageEvents) > 0}">
                    <c:forEach items="${messageEvents}" var="event">
                        <div class="card my-2">
                            <div class="card-header">
                                Сообщение: <c:url value="${event.dataEntry.subject}" />
                            </div>
                            <div class="card-body">
                                <table class="table card-text">                                
                                    <tbody>
                                        <tr>
                                            <th>
                                                Создано
                                            </th>
                                            <td>
                                                <tags:localDate date="${event.createDate}" />
                                            </td>
                                        </tr>
                                        <tr>
                                            <th>
                                                Запущено
                                            </th>
                                            <td>
                                                <tags:localDate date="${event.startDate}" />
                                            </td>
                                        </tr>
                                        <tr>
                                            <th>
                                                Следующая проверка
                                            </th>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${event.started}">
                                                        <tags:localDate date="${event.nextCheckDate}" />
                                                    </c:when>
                                                    <c:otherwise>
                                                        &ndash;
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th>
                                                Интервал проверок
                                            </th>
                                            <td>
                                                <c:url value="${event.checkingInterval}" />
                                            </td>
                                        </tr>
                                        <tr>
                                            <th>
                                                Управление
                                            </th>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${event.started}">
                                                        <div class="btn-group">
                                                            <button class="btn btn-secondary btn-sm" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                                                Запущено
                                                            </button>
                                                            <button type="button" class="btn btn-sm btn-secondary dropdown-toggle dropdown-toggle-split" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                                                <span class="sr-only">Toggle Dropdown</span>
                                                            </button>
                                                            <div class="dropdown-menu">
                                                                <a class="dropdown-item" 
                                                                   href="<c:url value="/token/user/csdevent/stop/${event.id}" />">
                                                                    Остановить
                                                                </a>                                                        
                                                            </div>
                                                        </div>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <div class="btn-group">
                                                            <button class="btn btn-secondary btn-sm" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                                                Остановлено
                                                            </button>
                                                            <button type="button" class="btn btn-sm btn-secondary dropdown-toggle dropdown-toggle-split" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                                                <span class="sr-only">Toggle Dropdown</span>
                                                            </button>
                                                            <div class="dropdown-menu">
                                                                <a class="dropdown-item" 
                                                                   href="<c:url value="/token/user/csdevent/start/${event.id}" />">
                                                                    Запустить
                                                                </a>                                                        
                                                            </div>
                                                        </div>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th>
                                                Статус подтверждения
                                            </th>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${event.waitingProlongation && !event.executed}">
                                                        <a href="<c:url value="/token/user/csdevent/confirm/${event.id}" />">Подтвердить</a>
                                                    </c:when>
                                                    <c:when test="${!event.waitingProlongation && !event.executed}">
                                                        Продлено
                                                    </c:when>
                                                    <c:when test="${event.executed}">
                                                        Реализовано <tags:localDate date="${event.firedDate}" />
                                                    </c:when>
                                                </c:choose>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th>
                                                &nbsp;
                                            </th>
                                            <td>
                                                <div class="btn-group">
                                                    <button class="btn btn-secondary btn-sm" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                                        Действия
                                                    </button>
                                                    <button type="button" class="btn btn-sm btn-secondary dropdown-toggle dropdown-toggle-split" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                                        <span class="sr-only">Toggle Dropdown</span>
                                                    </button>
                                                    <div class="dropdown-menu">
                                                        <a class="dropdown-item" 
                                                           href="<c:url value="/token/user/csdevent/view/${event.id}" />">
                                                            Смотреть
                                                        </a>
                                                        <a class="dropdown-item" 
                                                           href="<c:url value="/token/user/csdevent/edit/${event.id}" />">
                                                            Изменить
                                                        </a>
                                                        <a class="dropdown-item" 
                                                           href="<c:url value="/token/user/csdevent/delete/${event.id}" />">
                                                            Удалить
                                                        </a>
                                                    </div>
                                                </div>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>      
                            </div>
                        </div>
                    </c:forEach>                
                </c:when>
                <c:otherwise>
                    Сообщений нет.
                </c:otherwise>            
            </c:choose>
        </div>
        
    </jsp:body>

</template:basic_bs_three_col_tkn>