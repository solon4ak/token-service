<%--@elvariable id="medicalForm" type="ru.tokens.site.controller.MedHistFormController.MedicalForm"--%>
<%--@elvariable id="token" type="ru.tokens.site.entities.Token"--%>
<%--@elvariable id="user" type="ru.tokens.site.entities.User"--%>
<%--@elvariable id="bloodVariants" type="java.util.List"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:basic_bs_three_col_tkn htmlTitle="Adding medical data" bodyTitle="Add medical data">

    <jsp:attribute name="rightColumnContent">
        <nav class="nav flex-column">     
            <a class="nav-link" href="<c:url value="/user/view" />">
                User
            </a>
        </nav>
    </jsp:attribute>

    <jsp:body>
        <div class="container">
            <jsp:include page="/WEB-INF/jsp/token_id.jspf" />

            <form:form method="post" modelAttribute="medicalForm">
                <div class="form-group mx-3">
                    <div class="form-group row mt-1 p-2">
                        <form:label path="omsNumber">
                            Номер полиса ОМС
                        </form:label> 
                        <form:input path="omsNumber" type="text" class="form-control bg-light text-dark" 
                                    placeholder="1234 5678 9012 3456" 
                                    aria-describedby="omsHelp" id="omsNumber" />
                        <small id="omsHelp" class="form-text text-muted">
                            16 digits!
                        </small>
                    </div>
                    <div class="form-group row border-top mt-1 p-2">
                        <form:label path="bloodType">Группа крови</form:label>
                        <form:select path="bloodType" class="form-control bg-light text-dark" id="bloodType">
                            <form:option value="Нет данных" label="--- Select ---" />
                            <form:options items="${bloodVariants}" />
                        </form:select>
                    </div>
                    <div class="form-group row border-top mt-1 p-2">
                        <div class="col-sm-4">Донор органов</div>
                        <div class="col-sm-8">
                            <div class="form-check">
                                <form:checkbox path="isOrganDonor" class="form-check-input bg-light text-dark" id="isOrganDonor"/>
                                <label class="form-check-label" for="isOrganDonor">
                                    Да
                                </label>
                            </div>
                        </div>
                    </div>                
                    <div class="form-group row border-top mt-1 p-2">
                        <form:label path="childhoodIllness">
                            Перенесенные заболевания в детстве (в том числе детские инфекции)
                        </form:label>
                        <form:textarea path="childhoodIllness" class="form-control bg-light text-dark" 
                                       id="childhoodIllness" rows="3" />
                    </div>
                    <div class="form-group row border-top mt-1 p-2">
                        <form:label path="diseases">
                            Перенесенные заболевания в течении жизни (в том числе туберкулёз 
                            и контакт с ним, сахарный диабет, болезнь Боткина, венерические 
                            заболевания – гонорея, сифилис, СПИД)
                        </form:label>
                        <form:textarea path="diseases" class="form-control bg-light text-dark" 
                                       id="diseases" rows="3" />
                    </div>
                    <div class="form-group row border-top mt-1 p-2">
                        <form:label path="chronicDiseases">
                            Хронические заболевания
                        </form:label>
                        <form:textarea path="chronicDiseases" class="form-control bg-light text-dark" 
                                       id="chronicDiseases" rows="3" />
                    </div>
                    <div class="form-group row border-top mt-1 p-2">
                        <form:label path="surgicalOperations">
                            Операции
                        </form:label>
                        <form:textarea path="surgicalOperations" class="form-control bg-light text-dark" 
                                       id="surgicalOperations" rows="3" />
                    </div>
                    <div class="form-group row border-top mt-1 p-2">
                        <form:label path="injuries">
                            Травмы, ранения, контузии
                        </form:label>
                        <form:textarea path="injuries" class="form-control bg-light text-dark" 
                                       id="injuries" rows="3" />
                    </div>
                    <div class="form-group row border-top mt-1 p-2">
                        <form:label path="allergy">
                            Аллергические реакции (лекарственные средства, 
                            пищевая аллергия, сезонная аллергия)
                        </form:label>
                        <form:textarea path="allergy" class="form-control bg-light text-dark" 
                                       id="allergy" rows="3" />
                    </div>
                    <div class="form-group row border-top mt-1 p-2">
                        <form:label path="medicine">
                            Принимаемые лекарства
                        </form:label>
                        <form:textarea path="medicine" class="form-control bg-light text-dark" 
                                       id="medicine" rows="3" />
                    </div>
                    <div class="form-group row border-top mt-1 p-2">
                        <form:label path="inheritedDiseases">
                            Наследственные заболевания
                        </form:label>
                        <form:textarea path="inheritedDiseases" class="form-control bg-light text-dark" 
                                       id="inheritedDiseases" rows="3" />
                    </div>
                    <div class="form-group row border-top p-4">
                        <button class="btn btn-primary" type="submit">Submit</button>                
                    </div>
                </div>            
            </form:form>
        </div>
    </jsp:body>
</template:basic_bs_three_col_tkn>
