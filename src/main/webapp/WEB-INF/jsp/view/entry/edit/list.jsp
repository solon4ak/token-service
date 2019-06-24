<%--@elvariable id="token" type="ru.tokens.site.entities.Token"--%>
<%--@elvariable id="user" type="ru.tokens.site.entities.User"--%>
<%--@elvariable id="entries" type="java.util.Collection"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:basic_bs_three_col_tkn htmlTitle="Token-${token.uuidString} :: Medical History Entries"
                                 bodyTitle="Список записей">    

    <jsp:attribute name="rightColumnContent">
        <nav class="nav flex-column">
            <a class="nav-link" href="<c:url value="/token/user/med/entry/create" />">
                Новая запись
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
            <c:choose>
                <c:when test="${(user.medicalHistory.numberOfMedicalFormEntries) > 0}">
                    <h4>Записи: ${user.medicalHistory.numberOfMedicalFormEntries}</h5>
                        <ul class="list-group list-group-flush">
                            <c:forEach items="${user.medicalHistory.medicalFormEntries}" var="entry">
                                <li class="list-group-item d-flex justify-content-between align-items-center">
                                    <a href="<c:url context="/tkn" 
                                           value="/token/user/med/entry/${entry.id}/view" />">
                                       <c:out value="${entry.subject}" />                               
                                    </a>  
                                    <span class="badge badge-primary badge-pill">
                                        Вложения:&nbsp;<c:out value="${entry.numberOfAttachments}" />
                                    </span>
                                </li>
                            </c:forEach>
                        </ul>
                    </c:when>
                    <c:otherwise>
                        <p>[информация о том, что должно быть на странице]</p>
                        <p>Дополнений к мед истории пока нет.</p>
                    </c:otherwise>                        
                </c:choose>
        </div>        
    </jsp:body>

</template:basic_bs_three_col_tkn>
