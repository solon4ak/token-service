<%--@elvariable id="token" type="ru.tokens.site.entities.Token"--%>
<%--@elvariable id="age" type="java.lang.Integer"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:basic_bs_one_col_simple htmlTitle="${token.uuidString}"
                                  bodyTitle="Token: ${token.uuidString}">
    <div class="border rounded mb-3 bg-light text-dark">
        <div class="p-2">
            <h5 class="ml-4">Owner</h5>
            <hr />
            <div class="container">
                <div class="row justify-content-lg-center py-2">
                    <div class="col-md-6">
                        <c:if test="${user.image != null}">
                            <a href="<c:url value='/token/image'>
                                   <c:param name='userId' value='${user.userId}' />
                               </c:url>" data-fancybox="images">
                                <img src="<c:url value="/token/thumb">
                                         <c:param name="userId" value="${user.userId}" />
                                     </c:url>" 
                                     class="img-thumbnail" 
                                     alt="${user.lastName}, ${user.firstName}">
                            </a> 
                        </c:if>
                    </div>
                    <div class="col-md-6">
                        <table class="table table-sm table-borderless">
                            <tbody>
                                <tr>
                                    <th>
                                        Token owner
                                    </th>
                                    <td>
                                        <c:out value="${user.lastName}, 
                                               ${user.firstName} ${user.middleName} " />
                                    </td>
                                </tr>
                                <tr>
                                    <th>
                                        Birth date
                                    </th>
                                    <td>
                                        <tags:localDate date="${user.birthDate}" />
                                    </td>
                                </tr>
                                <tr>
                                    <th>
                                        Age
                                    </th>
                                    <td>
                                        <c:out value="${age}" />
                                    </td>
                                </tr>
                            </tbody>        
                        </table>
                    </div>
                </div>
            </div> 
        </div>
    </div>
    <div class="border rounded mb-3 bg-light text-dark">
        <div class="p-2">
            <c:choose>
                <c:when test="${age > 14 && token.user.passport != null}">
                    <h5 class="pl-4">Пасспорт</h5>
                    <c:choose>
                        <c:when test="${user.passport == null}">
                            There is no passport binded to the token.                 
                        </c:when>
                        <c:otherwise>
                            <table class="table">
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
                    <h5 class="pl-4">Birth Certificate</h5>
                    <c:choose>
                        <c:when test="${user.birthCertificate == null}">
                            There is no birth certificate binded to the token.                             
                        </c:when>
                        <c:otherwise>
                            <table class="table">
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
        </div>
    </div>

    <div class="border rounded mb-3 bg-light text-dark">
        <div class="p-2">
            <h5 class="pl-4">Address</h5>    
            <c:choose>
                <c:when test="${user.tokenAddress == null}">
                    There is no address binded to the token!                            
                </c:when>
                <c:otherwise>
                    <table class="table">
                        <tbody>
                            <tr>
                                <th>Country</th>
                                <td><c:out value="${user.tokenAddress.country}" /></td>
                            </tr>
                            <tr>
                                <th>Region</th>
                                <td><c:out value="${user.tokenAddress.region}" /></td>
                            </tr>
                            <tr>
                                <th>City</th>
                                <td><c:out value="${user.tokenAddress.city}" /></td>
                            </tr>
                            <tr>
                                <th>Street</th>
                                <td><c:out value="${user.tokenAddress.street}" /></td>
                            </tr>
                            <tr>
                                <th>Building</th>
                                <td><c:out value="${user.tokenAddress.building}" /></td>
                            </tr>
                            <tr>
                                <th>Apartment</th>
                                <td><c:out value="${user.tokenAddress.apartment}" /></td>
                            </tr>
                        </tbody>
                    </table>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
    <div class="border rounded mb-3 bg-light text-dark">
        <div class="p-2">
            <h5 class="pl-4">Contacts</h5>
            <c:if test="${fn:length(user.contacts) == 0}">
                There is no contacts binded to the token!
            </c:if>
            <table class="table">
                <c:if test="${fn:length(user.contacts) > 0}">
                    <thead>
                        <!-- here should go some titles... -->
                        <tr>                
                            <th>First name</th>
                            <th>Last Name</th>
                            <th>Phone</th>
                        </tr>
                    </thead>
                </c:if>
                <tbody>
                    <c:forEach items="${token.user.contacts}" var="contact">
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
                        </tr>
                    </c:forEach>
                </tbody>
            </table>   
        </div>            
    </div>
    <div class="border rounded mb-3 bg-light text-dark">
        <div class="p-2">
            <h5 class="pl-4">Medical History</h5>
            
            <div class="card my-2 p-3">
                <table class="table-borderless">
                    <tbody>
                        <tr>
                            <th>Номер полиса ОМС</th>
                            <td><c:out value="${user.medicalHistory.omsNumber}"/></td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div class="card my-2 p-3">
                <table class="table-borderless">
                    <tbody>
                        <tr>
                            <th>Группа крови</th>
                            <td><c:out value="${user.medicalHistory.bloodType}"/></td>
                        </tr>
                    </tbody>
                </table>                
            </div>
            <div class="card my-2 p-3">
                <table class="table-borderless">
                    <tbody>
                        <tr>
                            <th>Донор органов</th>
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
                <div class="card-header font-weight-bolder">
                    Перенесенные заболевания в детстве (в том числе детские инфекции)
                </div>
                <div class="card-body">
                    <p class="card-text lead">
                        <c:out value="${user.medicalHistory.childhoodIllness}"/>
                    </p>
                </div>
            </div>
            <div class="card my-2">
                <div class="card-header font-weight-bolder">
                    Перенесенные заболевания в течении жизни (в том числе туберкулёз 
                    и контакт с ним, сахарный диабет, болезнь Боткина, венерические 
                    заболевания – гонорея, сифилис, СПИД)
                </div>
                <div class="card-body">
                    <p class="card-text lead">
                        <c:out value="${user.medicalHistory.diseases}"/>
                    </p>
                </div>
            </div>
            <div class="card my-2">
                <div class="card-header font-weight-bolder">
                    Хронические заболевания
                </div>
                <div class="card-body">
                    <p class="card-text lead">
                        <c:out value="${user.medicalHistory.chronicDiseases}"/>
                    </p>
                </div>
            </div>
            <div class="card my-2">
                <div class="card-header font-weight-bolder">
                    Операции
                </div>
                <div class="card-body">
                    <p class="card-text lead">
                        <c:out value="${user.medicalHistory.surgicalOperations}"/>
                    </p>
                </div>
            </div>
            <div class="card my-2">
                <div class="card-header font-weight-bolder">
                    Травмы, ранения, контузии
                </div>
                <div class="card-body">
                    <p class="card-text lead">
                        <c:out value="${user.medicalHistory.injuries}"/>
                    </p>
                </div>
            </div>
            <div class="card my-2">
                <div class="card-header font-weight-bolder">
                    Аллергические реакции (лекарственные средства, 
                    пищевая аллергия, сезонная аллергия)
                </div>
                <div class="card-body">
                    <p class="card-text lead">
                        <c:out value="${user.medicalHistory.allergy}"/>
                    </p>
                </div>
            </div>
            <div class="card my-2">
                <div class="card-header font-weight-bolder">
                    Принимаемые лекарства
                </div>
                <div class="card-body">
                    <p class="card-text lead">
                        <c:out value="${user.medicalHistory.medicine}"/>
                    </p>
                </div>
            </div>
            <div class="card my-2">
                <div class="card-header font-weight-bolder">
                    Наследственные заболевания
                </div>
                <div class="card-body">
                    <p class="card-text lead">
                        <c:out value="${user.medicalHistory.inheritedDiseases}"/>
                    </p>
                </div>
            </div>
            <div class="card my-2">
                <c:if test="${(user.medicalHistory.numberOfMedicalFormEntries) > 0}">
                    <div class="card-header font-weight-bolder">
                        Entries: ${user.medicalHistory.numberOfMedicalFormEntries}
                    </div>
                    <div class="card-body">
                        <ul class="card-text list-group list-group-flush">
                            <c:forEach items="${user.medicalHistory.medicalFormEntries}" var="entry">
                                <li class="list-group-item">
                                    <a href="<c:url value="/token/${token.tokenId}/${token.uuidString}/med/entry/view/${entry.id}" />">
                                        <c:out value="${entry.subject}" />                               
                                    </a>                            
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                </c:if>
            </div>
        </div>
    </div>
</template:basic_bs_one_col_simple>
