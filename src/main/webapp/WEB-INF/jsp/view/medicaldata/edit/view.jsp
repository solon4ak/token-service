<%--@elvariable id="token" type="ru.tokens.site.entities.Token"--%>
<%--@elvariable id="user" type="ru.tokens.site.entities.User"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:basic_bs_three_col_tkn htmlTitle="Token-${token.uuidString} :: Medical History"
                                 bodyTitle="Medical History">    

    <jsp:attribute name="authContent">
        <jsp:include page="/WEB-INF/jsp/user.jspf" />
    </jsp:attribute>

    <jsp:attribute name="rightColumnContent">
        <nav class="nav flex-column">      
            <c:choose>
                <c:when test="${user.medicalHistory == null}">
                    <a class="nav-link" href="<c:url value="/token/user/med/add" />">
                        Add
                    </a>
                </c:when>
                <c:otherwise>
                    <a class="nav-link" href="<c:url value="/token/user/med/edit" />">
                        Edit
                    </a>  
                    <a class="nav-link" href="<c:url value="/token/user/med/entry/list" />">
                        Entries
                    </a> 
                </c:otherwise>
            </c:choose>
            <div class="dropdown-divider"></div>
            <a class="nav-link" href="<c:url value="/user/view" />">
                User
            </a>
        </nav>
    </jsp:attribute>

    <jsp:body>
        <div class="container"> 
            <div class="card my-2">                
                <div class="card-body">
                    <p class="card-text"><c:out value="Token ID: ${token.uuidString}"/><br />
                        <c:out value="User E-mail: ${user.userEmailAddress}"/></p>
                </div>
            </div>
            <c:choose>
                <c:when test="${user.medicalHistory == null}">
                    Empty! Please add your med data!
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

                    <c:if test="${(user.medicalHistory.numberOfMedicalFormEntries) > 0}">
                        <div class="card my-2">
                            <div class="card-header">
                                Entries: ${user.medicalHistory.numberOfMedicalFormEntries}
                            </div>
                            <div class="card-body">
                                <ul class="card-text list-group list-group-flush">
                                    <c:forEach items="${user.medicalHistory.medicalFormEntries}" var="entry">
                                        <li class="list-group-item">
                                            <a href="<c:url context="/tkn" 
                                                   value="/token/user/med/entry/${entry.id}/view" />">
                                               <c:out value="${entry.subject}" />                               
                                            </a>                            
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
