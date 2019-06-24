<%--@elvariable id="token" type="ru.tokens.site.entities.Token"--%>
<%--@elvariable id="user" type="ru.tokens.site.entities.User"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:basic_bs_three_col_tkn htmlTitle="${token.uuidString}"
                                 bodyTitle="Жетон: ${token.uuidString}">

    <jsp:attribute name="rightColumnContent">
        <nav class="nav flex-column">
            <a class="nav-link" href="<c:url value="/user/view" />">
                Пользователь
            </a>
        </nav>
    </jsp:attribute>

    <jsp:body>
        <div class="container">
            <h5>ID жетона: <c:out value="${token.uuidString}"/></h5>
            <c:choose>
                <c:when test="${user.image == null}">
                    <div class="row pt-3 border-top">                        
                        <div class="col-md-6">
                            <table class="table table-sm table-borderless">
                                <tbody>
                                    <tr>
                                        <td class="h6">
                                            Активирован 
                                        </td>
                                        <td>
                                            <wrox:formatDate value="${token.activatedDate}" />
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="h6">
                                            Владелец 
                                        </td>
                                        <td>
                                            <c:out value="${user.lastName}, 
                                                   ${user.firstName} ${user.middleName} " />
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="h6">
                                            Дата рождения
                                        </td>
                                        <td>
                                            <tags:localDate date="${user.birthDate}" />
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="h6">
                                            E-mail 
                                        </td>
                                        <td>
                                            <c:out value="${user.userEmailAddress}"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="h6">
                                            Телефон 
                                        </td>
                                        <td>
                                            <c:out value="${user.phoneNumber}"/>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>                    
                        </div>   
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="row pt-3 border-top">
                        <div class="col-md-6 border-right">
                            <img class="img-thumbnail" src="<c:url value="/token/user/image/thumbnail" />" 
                                 alt="${user.lastName}, ${user.firstName}"/>
                        </div>
                        <div class="col-md-6">
                            <table class="table table-sm table-borderless">
                                <tbody>
                                    <tr>
                                        <td class="h6">
                                            Активирован 
                                        </td>
                                        <td>
                                            <wrox:formatDate value="${token.activatedDate}" />
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="h6">
                                            Владелец 
                                        </td>
                                        <td>
                                            <c:out value="${user.lastName}, 
                                                   ${user.firstName} ${user.middleName} " />
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="h6">
                                            Дата рождения
                                        </td>
                                        <td>
                                            <tags:localDate date="${user.birthDate}" />
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="h6">
                                            E-mail 
                                        </td>
                                        <td>
                                            <c:out value="${user.userEmailAddress}"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="h6">
                                            Телефон 
                                        </td>
                                        <td>
                                            <c:out value="${user.phoneNumber}"/>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>                    
                        </div>   
                    </div>
                </c:otherwise>
            </c:choose>        

            <hr />        
            <h6>Как это выглядит</h6>
            <a href="<c:url value="/token/${token.tokenId}/${token.uuidString}">
                   <c:param name="showMH" value="false" />              
               </c:url>" target="_blank">
                Адрес и контакты
            </a> / 
            <a href="<c:url value="/token/${token.tokenId}/${token.uuidString}">
                   <c:param name="showMH" value="true" />              
               </c:url>" target="_blank">
                Полная информация
            </a>
        </div>
    </jsp:body>
</template:basic_bs_three_col_tkn>
