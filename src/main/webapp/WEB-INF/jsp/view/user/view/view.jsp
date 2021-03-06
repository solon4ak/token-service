<%--@elvariable id="token" type="ru.tokens.site.entities.Token"--%>
<%--@elvariable id="age" type="java.lang.Integer"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:loggedOut htmlTitle="${token.uuidString}"
                    bodyTitle="Token: ${token.uuidString}">
    Activated: <wrox:formatDate value="${token.activatedDate}" />
    <hr />
    <table class="data_table">
        <tbody>
            <tr>
                <td>
                    <b>Token owner</b>
                </td>
                <td>
                    <c:out value="${user.lastName}, 
                           ${user.firstName} ${user.middleName} " />
                </td>
            </tr>
            <tr>
                <td>
                    Birth date:
                </td>
                <td>
                    <tags:localDate date="${user.birthDate}" />
                </td>
            </tr>
            <tr>
                <td>
                    Age:
                </td>
                <td>
                    <c:out value="${age}" />
                </td>
            </tr>
        </tbody>        
    </table>
    <hr />
    <c:if test="${user.image != null}">
        <a href="<c:url value="/token/image">
               <c:param name="userId" value="${user.userId}" />
        </c:url>">
            <img src="<c:url value="/token/thumb">
                 <c:param name="userId" value="${user.userId}" />
            </c:url>" 
                 alt="${user.lastName}, ${user.firstName}"/>
        </a>        
    </c:if>    
    <c:choose>
        <c:when test="${age > 14 && token.user.passport != null}">
            <hr />
            <h5>Пасспорт:</h5>
            <c:choose>
                <c:when test="${user.passport == null}">
                    There is no passport binded to the token.                 
                </c:when>
                <c:otherwise>
                    <table class="data_table">
                        <!--                <thead>
                                            <tr>
                                                <th>Property</th>
                                                <th>Data</th>
                                            </tr>
                                        </thead>-->
                        <tbody>
                            <tr>
                                <td>Серия:</td>
                                <td><c:out value="${user.passport.series}" /></td>
                            </tr>
                            <tr>
                                <td>Номер:</td>
                                <td><c:out value="${user.passport.number}" /></td>
                            </tr>
                            <tr>
                                <td>Кем выдан:</td>
                                <td><c:out value="${user.passport.issueDepartment}" /></td>
                            </tr>
                            <tr>
                                <td>Код подразделения:</td>
                                <td><c:out value="${user.passport.issueDepartmentCode}" /></td>
                            </tr>
                            <tr>
                                <td>Дата выдачи:</td>
                                <td><wrox:formatDate value="${user.passport.issueDate}" /></td>
                            </tr>
                        </tbody>
                    </table>
                </c:otherwise>
            </c:choose>
        </c:when>
        <c:otherwise>
            <hr />
            <h5>Birth Certificate:</h5>
            <c:choose>
                <c:when test="${user.birthCertificate == null}">
                    There is no birth certificate binded to the token.                             
                </c:when>
                <c:otherwise>
                    <table class="data_table">
                        <!--                <thead>
                                            <tr>
                                                <th>Property</th>
                                                <th>Data</th>
                                            </tr>
                                        </thead>-->
                        <tbody>
                            <tr>
                                <td>Серия:</td>
                                <td><c:out value="${user.birthCertificate.series}" /></td>
                            </tr>
                            <tr>
                                <td>Номер:</td>
                                <td><c:out value="${user.birthCertificate.number}" /></td>
                            </tr>
                            <tr>
                                <td>Кем выдано:</td>
                                <td><c:out value="${user.birthCertificate.issueDepartment}" /></td>
                            </tr>
                            <tr>
                                <td>Дата выдачи:</td>
                                <td><wrox:formatDate value="${user.birthCertificate.issueDate}" /></td>
                            </tr>
                        </tbody>
                    </table>            
                </c:otherwise>
            </c:choose>
        </c:otherwise>
    </c:choose>
    <hr />
    <h5>Address:</h5>
    <c:choose>
        <c:when test="${user.tokenAddress == null}">
            There is no address binded to the token!                            
        </c:when>
        <c:otherwise>
            <table class="data_table">
                <!--                <thead>
                                    <tr>
                                        <th>Property</th>
                                        <th>Data</th>
                                    </tr>
                                </thead>-->
                <tbody>
                    <tr>
                        <td>Country:</td>
                        <td><c:out value="${user.tokenAddress.country}" /></td>
                    </tr>
                    <tr>
                        <td>Region:</td>
                        <td><c:out value="${user.tokenAddress.region}" /></td>
                    </tr>
                    <tr>
                        <td>City:</td>
                        <td><c:out value="${user.tokenAddress.city}" /></td>
                    </tr>
                    <tr>
                        <td>Street:</td>
                        <td><c:out value="${user.tokenAddress.street}" /></td>
                    </tr>
                    <tr>
                        <td>Building:</td>
                        <td><c:out value="${user.tokenAddress.building}" /></td>
                    </tr>
                    <tr>
                        <td>Apartment:</td>
                        <td><c:out value="${user.tokenAddress.apartment}" /></td>
                    </tr>
                </tbody>
            </table>
            <br />
        </c:otherwise>
    </c:choose>
    <hr />

    <h5>Contacts:</h5>
    <c:if test="${fn:length(user.contacts) == 0}">
        There is no contacts binded to the token!
    </c:if>
    <table class="data_table">
        <c:if test="${fn:length(user.contacts) > 0}">
            <thead>
                <!-- here should go some titles... -->
                <tr>                
                    <th>First name</th>
                    <th>Last Name</th>
                    <th>Phone</th>
                    <th>E-mail</th>
                </tr>
            </thead>
        </c:if>
        <tbody>
            <c:forEach items="${user.contacts}" var="contact">
                <tr>                
                    <td>
                        <c:out value="${contact.firstName}" />
                    </td>
                    <td>
                        <c:out value="${contact.lastName}" />
                    </td>
                    <td>
                        <c:out value="${contact.phoneNumber}" />
                    </td>
                    <td>
                        <c:out value="${contact.email}" />
                    </td>                    
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <hr />
    <h5>Medical History:</h5>
    <c:choose>
        <c:when test="${user.medicalHistory == null}">
            There is no medical history binded to the token<br /><br />                        
        </c:when>
        <c:otherwise>
            <table class="data_table">
                <!--                <thead>
                                    <tr>
                                        <th>Описание</th>
                                        <th>Данные</th>
                                    </tr>
                                </thead>-->
                <tbody>
                    <tr>
                        <td>Номер полиса ОМС</td>
                        <td>
                            <c:out value="${user.medicalHistory.omsNumber}"/>
                        </td>
                    </tr>
                    <tr>
                        <td>Группа крови</td>
                        <td>
                            <c:out value="${user.medicalHistory.bloodType}"/>
                        </td>
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
            <hr />
            <c:if test="${(user.medicalHistory.numberOfMedicalFormEntries) > 0}">
                <b>Entries: ${token.user.medicalHistory.numberOfMedicalFormEntries}</b><br /><br />
                <ol>
                    <c:forEach items="${user.medicalHistory.medicalFormEntries}" var="entry">
                        <li>
                            <a href="<c:url value="/token/${token.tokenId}/${token.uuidString}/med/entry/view/${entry.id}" />">
                                <c:out value="${entry.subject}" />                               
                            </a>                            
                        </li>
                    </c:forEach>
                </ol>
            </c:if>            
        </c:otherwise>
    </c:choose> 
</template:loggedOut>
