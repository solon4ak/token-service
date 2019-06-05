<%--@elvariable id="passportForm" type="ru.tokens.site.controller.PassportController.PassportForm"--%>
<%--@elvariable id="token" type="ru.tokens.site.entities.Token"--%>
<%--@elvariable id="user" type="ru.tokens.site.entities.User"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:basic_bs_three_col_tkn htmlTitle="Edit Passport" bodyTitle="Edit Passport">

    <jsp:attribute name="authContent">
        <jsp:include page="/WEB-INF/jsp/user.jspf" />
    </jsp:attribute> 
    
    <jsp:attribute name="rightColumnContent">
        <nav class="nav flex-column">
            <a class="nav-link" href="<c:url value="/user/view" />">
                User
            </a>
        </nav>
    </jsp:attribute>

    <jsp:body>
        <c:out value="Token ID: ${token.uuidString}"/><br />
        <c:out value="User E-mail: ${user.userEmailAddress}"/>
        <hr />
        Birth Date: <wrox:formatDate value="${token.user.birthDate}" />
        <hr />
        <form:form method="post" modelAttribute="passportForm">
            <div class="form-group">
                <div class="form-group row">
                    <form:label path="ser" class="col-sm-3 col-form-label">
                        Серия
                    </form:label>
                    <div class="col-sm-9">
                        <form:input path="ser" type="text" class="form-control" 
                                    placeholder="4524" required="true" />
                    </div>                
                </div>
                <div class="form-group row">
                    <form:label path="num" class="col-sm-3 col-form-label">
                        Номер паспорта
                    </form:label>
                    <div class="col-sm-9">
                        <form:input path="num" type="text" class="form-control" 
                                    placeholder="437190" required="true" />
                    </div>                
                </div>
                <div class="form-group row">
                    <form:label path="issueDep" class="col-sm-3 col-form-label">
                        Кем выдан
                    </form:label>
                    <div class="col-sm-9">
                        <form:input path="issueDep" type="text" class="form-control" 
                                    placeholder="Отделом УФМС России по гор. Москве по району Митино" 
                                    required="true" />
                    </div>                
                </div>
                <div class="form-group row">
                    <form:label path="issueDepCode" class="col-sm-3 col-form-label">
                        Код подразделения
                    </form:label>
                    <div class="col-sm-9">
                        <form:input path="issueDepCode" type="text" class="form-control" 
                                    placeholder="770-093" 
                                    required="true" />
                    </div>                
                </div>
                <div class="form-group row">
                    <form:label path="dated" class="col-sm-3 col-form-label">
                        Дата выдачи
                    </form:label>
                    <div class="col-sm-9">
                        <form:input path="dated" type="text" class="form-control" 
                                    id="dated" aria-describedby="issueDateHelp" 
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

</template:basic_bs_three_col_tkn>