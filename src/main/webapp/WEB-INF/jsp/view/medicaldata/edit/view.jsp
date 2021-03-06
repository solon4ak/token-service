<%--@elvariable id="token" type="ru.tokens.site.entities.Token"--%>
<%--@elvariable id="user" type="ru.tokens.site.entities.User"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:basic htmlTitle="Token-${token.uuidString} :: Medical History"
                bodyTitle="Medical History">    
    <c:out value="Token ID: ${token.uuidString}"/><br />
    <c:out value="User E-mail: ${user.userEmailAddress}"/>
    <hr />
    View my token page: 
    <a href="<c:url value="/token/${token.tokenId}/${token.uuidString}">
           <c:param name="showMH" value="false" />              
       </c:url>" target="_blank">
        Address
    </a> / 
    <a href="<c:url value="/token/${token.tokenId}/${token.uuidString}">
           <c:param name="showMH" value="true" />              
       </c:url>" target="_blank">
        Full
    </a>
    <hr />
    <a href="<c:url value="/token/user/view"/>"><- Back</a><br /><br />
    <table class="data_table">
        <thead>
            <tr>
                <th>Описание</th>
                <th>Данные</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>Номер полиса ОМС</td>
                <td>
                    <c:out value="${user.medicalHistory.omsNumber}"/>
                </td>
            </tr>
            <tr>
                <td>Группа крови</td>
                <td><c:out value="${user.medicalHistory.bloodType}"/></td>
            </tr>
            <tr>
                <td>Разрешение на извлечение органов (донор органов)</td>
                <td>
                    <c:choose>
                        <c:when test="${user.medicalHistory.organDonor == true}">
                            Разрешаю
                        </c:when>
                        <c:otherwise>
                            Не разрешаю
                        </c:otherwise>
                    </c:choose>                            
                </td>
            </tr>
            <tr>
                <td>
                    Перенесенные заболевания в детстве <br />(в том числе детские инфекции)
                </td>
                <td>
                    <c:out value="${user.medicalHistory.childhoodIllness}"/>
                </td>
            </tr>
            <tr>
                <td>
                    Перенесенные заболевания в течении жизни <br />(в том числе туберкулёз 
                    и контакт с ним, <br />сахарный диабет, болезнь Боткина, <br />венерические 
                    заболевания – гонорея, сифилис, СПИД)
                </td>
                <td>
                    <c:out value="${user.medicalHistory.diseases}"/>
                </td>
            </tr>
            <tr>
                <td>
                    Хронические заболевания
                </td>
                <td>
                    <c:out value="${user.medicalHistory.chronicDiseases}"/>
                </td>
            </tr>
            <tr>
                <td>
                    Операции
                </td>
                <td>
                    <c:out value="${user.medicalHistory.surgicalOperations}"/>
                </td>
            </tr>
            <tr>
                <td>
                    Травмы, ранения, контузии
                </td>
                <td>
                    <c:out value="${user.medicalHistory.injuries}"/>
                </td>
            </tr>
            <tr>
                <td>
                    Аллергические реакции <br />(лекарственные средства, 
                    пищевая аллергия, <br />сезонная аллергия)
                </td>
                <td>
                    <c:out value="${user.medicalHistory.allergy}"/>
                </td>
            </tr>
            <tr>
                <td>
                    Принимаемые лекарства
                </td>
                <td>
                    <c:out value="${user.medicalHistory.medicine}"/>
                </td>
            </tr>
            <tr>
                <td>
                    Наследственные заболевания
                </td>
                <td>
                    <c:out value="${user.medicalHistory.inheritedDiseases}"/>
                </td>
            </tr>
        </tbody>
    </table> 
    <br />
    <a href="<c:url value="/token/user/med/edit" />">Edit</a>
    <hr />
    <c:if test="${(user.medicalHistory.numberOfMedicalFormEntries) > 0}">
        <b>Entries: ${user.medicalHistory.numberOfMedicalFormEntries}</b><br /><br />
        <ol>
            <c:forEach items="${user.medicalHistory.medicalFormEntries}" var="entry">
                <li>
                    <a href="<c:url context="/tkn" 
                           value="/token/user/med/entry/${entry.id}/view" />">
                       <c:out value="${entry.subject}" />                               
                    </a>                            
                </li>
            </c:forEach>
        </ol>
    </c:if>
    <a href="<c:url value="/token/user/med/entry/create" />">Add entry</a>

</template:basic>
