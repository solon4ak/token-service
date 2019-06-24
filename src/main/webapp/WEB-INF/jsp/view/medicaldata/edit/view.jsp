<%--@elvariable id="token" type="ru.tokens.site.entities.Token"--%>
<%--@elvariable id="user" type="ru.tokens.site.entities.User"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:basic_bs_three_col_tkn htmlTitle="Token-${token.uuidString} :: Medical History"
                                 bodyTitle="Медицинские данные">    

    <jsp:attribute name="rightColumnContent">
        <nav class="nav flex-column">      
            <c:choose>
                <c:when test="${user.medicalHistory == null}">
                    <a class="nav-link" href="<c:url value="/token/user/med/add" />">
                        Добавить
                    </a>
                </c:when>
                <c:otherwise>
                    <a class="nav-link" href="<c:url value="/token/user/med/edit" />">
                        Изменить
                    </a>  
                    <a class="nav-link" href="<c:url value="/token/user/med/entry/list" />">
                        Записи
                    </a> 
                </c:otherwise>
            </c:choose>
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
                <c:when test="${user.medicalHistory == null}">
                    Заполните форму!
                </c:when>
                <c:otherwise>
                    <div class="card my-2 p-3">
                        <table class="table table-borderless">
                            <tbody>
                                <tr>
                                    <th>
                                        Номер полиса ОМС
                                    </th>
                                    <td>
                                        <c:out value="${user.medicalHistory.omsNumber}"/>
                                    </td>
                                </tr>
                                <tr>
                                    <th>
                                        Группа крови
                                    </th>
                                    <td>
                                        <c:out value="${user.medicalHistory.bloodType}"/>
                                    </td>
                                </tr>
                                <tr>
                                    <th>
                                        Донор органов
                                    </th>
                                    <td>
                                        <c:choose>
                                            <c:when test="${user.medicalHistory.organDonor == true}">
                                                Да
                                            </c:when>
                                            <c:otherwise>
                                                Нет
                                            </c:otherwise>
                                        </c:choose> 
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div> 

                    <c:if test="${fn:length(fn:trim(user.medicalHistory.childhoodIllness)) > 0}">
                        <div class="card my-2">
                            <div class="card-header">
                                Перенесенные заболевания в детстве (в том числе детские инфекции)
                            </div>
                            <div class="card-body">
                                <p class="card-text">
                                    <c:out value="${user.medicalHistory.childhoodIllness}"/>
                                </p>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${fn:length(fn:trim(user.medicalHistory.diseases)) > 0}">
                        <div class="card my-2">
                            <div class="card-header">
                                Перенесенные заболевания в течении жизни (в том числе туберкулёз 
                                и контакт с ним, сахарный диабет, болезнь Боткина, венерические 
                                заболевания – гонорея, сифилис, СПИД)
                            </div>
                            <div class="card-body">
                                <p class="card-text">
                                    <c:out value="${user.medicalHistory.diseases}"/>
                                </p>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${fn:length(fn:trim(user.medicalHistory.chronicDiseases)) > 0}">
                        <div class="card my-2">
                            <div class="card-header">
                                Хронические заболевания
                            </div>
                            <div class="card-body">
                                <p class="card-text">
                                    <c:out value="${user.medicalHistory.chronicDiseases}"/>
                                </p>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${fn:length(fn:trim(user.medicalHistory.surgicalOperations)) > 0}">
                        <div class="card my-2">
                            <div class="card-header">
                                Операции
                            </div>
                            <div class="card-body">
                                <p class="card-text">
                                    <c:out value="${user.medicalHistory.surgicalOperations}"/>
                                </p>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${fn:length(fn:trim(user.medicalHistory.injuries)) > 0}">
                        <div class="card my-2">
                            <div class="card-header">
                                Травмы, ранения, контузии
                            </div>
                            <div class="card-body">
                                <p class="card-text">
                                    <c:out value="${user.medicalHistory.injuries}"/>
                                </p>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${fn:length(fn:trim(user.medicalHistory.allergy)) > 0}">
                        <div class="card my-2">
                            <div class="card-header">
                                Аллергические реакции (лекарственные средства, 
                                пищевая аллергия, сезонная аллергия)
                            </div>
                            <div class="card-body">
                                <p class="card-text">
                                    <c:out value="${user.medicalHistory.allergy}"/>
                                </p>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${fn:length(fn:trim(user.medicalHistory.medicine)) > 0}">
                        <div class="card my-2">
                            <div class="card-header">
                                Принимаемые лекарства
                            </div>
                            <div class="card-body">
                                <p class="card-text">
                                    <c:out value="${user.medicalHistory.medicine}"/>
                                </p>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${fn:length(fn:trim(user.medicalHistory.inheritedDiseases)) > 0}">
                        <div class="card my-2">
                            <div class="card-header">
                                Наследственные заболевания
                            </div>
                            <div class="card-body">
                                <p class="card-text">
                                    <c:out value="${user.medicalHistory.inheritedDiseases}"/>
                                </p>
                            </div>
                        </div>
                    </c:if>                    

                    <c:if test="${(user.medicalHistory.numberOfMedicalFormEntries) > 0}">
                        <div class="card my-2">
                            <div class="card-header">
                                Записи: ${user.medicalHistory.numberOfMedicalFormEntries}
                            </div>
                            <div class="card-body">
                                <ul class="card-text list-group list-group-flush">
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
                            </div>
                        </div>
                    </c:if>
                </c:otherwise>
            </c:choose>
        </div>
    </jsp:body>
</template:basic_bs_three_col_tkn>
