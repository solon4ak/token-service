<%--@elvariable id="certForm" type="ru.tokens.site.controller.BirthCertificateController.BthCertForm"--%>
<%--@elvariable id="token" type="ru.tokens.site.entities.Token"--%>
<%--@elvariable id="user" type="ru.tokens.site.entities.User"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:basic_bs_one_col htmlTitle="Edit Birth Certificate" bodyTitle="Birth certificate edit">

    <jsp:attribute name="authContent">
        <c:choose>
            <c:when test="${sessionScope['ru.tkn.user.principal'] != null}">
                <a class="btn btn-light text-dark" href="<c:url value="/logout" />">
                    Logout
                </a>
            </c:when>
            <c:otherwise>
                <a class="btn btn-light text-dark" href="<c:url value="/login" />">
                    Login
                </a>
            </c:otherwise>
        </c:choose>
    </jsp:attribute>

    <jsp:attribute name="extraNavigationContent">
        <c:if test="${sessionScope['ru.tkn.user.principal'] != null}">
            <a class="p-2 text-dark" href="<c:url value="/user/view" />">User</a>
        </c:if>
    </jsp:attribute>

    <jsp:body>
        <c:out value="Token ID: ${token.uuidString}"/><br />
        <c:out value="User E-mail: ${user.userEmailAddress}"/>
        <hr />
        Birth Date: <wrox:formatDate value="${token.user.birthDate}" />
        <hr />
        <form:form method="post" modelAttribute="certForm">
            <div class="form-group">
                <div class="form-group row">
                    <form:label path="series" class="col-sm-3 col-form-label">
                        Серия
                    </form:label>
                    <div class="col-sm-9">
                        <form:input path="series" type="text" class="form-control" 
                                    placeholder="IV-МР" required="true" />
                    </div>                
                </div>
                <div class="form-group row">
                    <form:label path="number" class="col-sm-3 col-form-label">
                        Номер
                    </form:label>
                    <div class="col-sm-9">
                        <form:input path="number" type="text" class="form-control" 
                                    placeholder="437190" required="true" />
                    </div>                
                </div>
                <div class="form-group row">
                    <form:label path="issueDepartment" class="col-sm-3 col-form-label">
                        Кем выдан
                    </form:label>
                    <div class="col-sm-9">
                        <form:input path="issueDepartment" type="text" class="form-control" 
                                    placeholder="Чертановский отдел ЗАГС Управления ЗАГС Москвы" 
                                    required="true" />
                    </div>                
                </div>
                <div class="form-group row">
                    <form:label path="issueDate" class="col-sm-3 col-form-label">
                        Дата выдачи
                    </form:label>
                    <div class="col-sm-9">
                        <form:input path="issueDate" type="text" class="form-control" 
                                    id="issueDate" aria-describedby="issueDateHelp" 
                                    placeholder="23.11.1997" required="true" />
                        <small id="issueDateHelp" class="form-text text-muted">
                            Формат даты: dd.mm.yyyy (29.01.2001).
                        </small>
                    </div>
                </div>
                <!--Submit-->
                <div class="form-group row">
                    <span class="col-sm-3 col-form-label">
                        &nbsp;
                    </span>
                    <div class="col-sm-9">
                        <button class="btn btn-primary" type="submit">Confirm</button>
                    </div>                
                </div>
            </div>                           
        </form:form>    
    </jsp:body>
</template:basic_bs_one_col>