<%--@elvariable id="token" type="ru.tokens.site.entities.Token"--%>
<%--@elvariable id="user" type="ru.tokens.site.entities.User"--%>
<%--@elvariable id="entries" type="java.util.Collection"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:basic_bs_three_col_tkn htmlTitle="Token-${token.uuidString} :: Medical History Entries"
                                 bodyTitle="Medical History Entries">    

    <jsp:attribute name="authContent">
        <jsp:include page="/WEB-INF/jsp/user.jspf" />
    </jsp:attribute>

    <jsp:attribute name="rightColumnContent">
        <nav class="nav flex-column">
            <a class="nav-link" href="<c:url value="/token/user/med/entry/create" />">
                Add entry
            </a>
            <div class="dropdown-divider"></div>
            <a class="nav-link" href="<c:url value="/user/view" />">
                User
            </a>
        </nav>
    </jsp:attribute>

    <jsp:body>
        <div class="container">              
            <c:choose>
                <c:when test="${(user.medicalHistory.numberOfMedicalFormEntries) > 0}">
                    <h4>Entries: ${user.medicalHistory.numberOfMedicalFormEntries}</h5>
                        <ul class="list-group list-group-flush">
                            <c:forEach items="${user.medicalHistory.medicalFormEntries}" var="entry">
                                <li class="list-group-item">
                                    <a href="<c:url context="/tkn" 
                                           value="/token/user/med/entry/${entry.id}/view" />">
                                       <c:out value="${entry.subject}" />                               
                                    </a>                            
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
