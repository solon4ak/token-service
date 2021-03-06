<%--@elvariable id="medicalForm" type="ru.tokens.site.controller.MedHistFormController.MedicalForm"--%>
<%--@elvariable id="token" type="ru.tokens.site.entities.Token"--%>
<%--@elvariable id="user" type="ru.tokens.site.entities.User"--%>
<%--@elvariable id="bloodVariants" type="java.util.List"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:basic htmlTitle="Editing medical data" bodyTitle="Edit medical data">
    <c:out value="Token ID: ${token.uuidString}"/><br />
    <c:out value="User E-mail: ${user.userEmailAddress}"/>
    <hr />
    <form:form method="post" modelAttribute="medicalForm">
        <fieldset>
            <legend>Edit medical data</legend>
            <table border="1" cellpadding="10">
                <thead>
                    <tr>
                        <th>Описание</th>
                        <th>Данные</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td><form:label path="omsNumber">Номер полиса ОМС</form:label></td>
                        <td><form:input path="omsNumber"/></td>
                    </tr>
                    <tr>
                        <td><form:label path="bloodType">Группа крови</form:label></td>
                            <td>
                            <form:select path="bloodType">
                                <form:option value="Нет данных" label="--- Select ---" />
                                <form:options items="${bloodVariants}" />
                            </form:select>
                        </td>
                    </tr>
                    <tr>
                        <td><form:label path="isOrganDonor">Разрешение на извлечение органов (донор органов)</form:label></td>
                        <td>Разрешаю &nbsp;<form:checkbox path="isOrganDonor"/></td>
                    </tr>
                    <tr>
                        <td>
                            <form:label path="childhoodIllness">
                                Перенесенные заболевания в детстве <br />(в том числе детские инфекции)
                            </form:label>
                        </td>
                        <td>
                            <form:textarea path="childhoodIllness" rows="5" cols="30" />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <form:label path="diseases">
                                Перенесенные заболевания в течении жизни <br />(в том числе туберкулёз 
                                и контакт с ним, <br />сахарный диабет, болезнь Боткина, <br />венерические 
                                заболевания – гонорея, сифилис, СПИД)
                            </form:label>
                        </td>
                        <td>
                            <form:textarea path="diseases" rows="5" cols="70" />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <form:label path="chronicDiseases">
                                Хронические заболевания
                            </form:label>
                        </td>
                        <td>
                            <form:textarea path="chronicDiseases" rows="5" cols="70" />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <form:label path="surgicalOperations">
                                Операции
                            </form:label>
                        </td>
                        <td>
                            <form:textarea path="surgicalOperations" rows="5" cols="70" />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <form:label path="injuries">
                                Травмы, ранения, контузии
                            </form:label>
                        </td>
                        <td>
                            <form:textarea path="injuries" rows="5" cols="70" />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <form:label path="allergy">
                                Аллергические реакции <br />(лекарственные средства, 
                                пищевая аллергия, <br />сезонная аллергия)
                            </form:label>
                        </td>
                        <td>
                            <form:textarea path="allergy" rows="5" cols="70" />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <form:label path="medicine">
                                Принимаемые лекарства
                            </form:label>
                        </td>
                        <td>
                            <form:textarea path="medicine" rows="5" cols="70" />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <form:label path="inheritedDiseases">
                                Наследственные заболевания
                            </form:label>
                        </td>
                        <td>
                            <form:textarea path="inheritedDiseases" rows="5" cols="70" />
                        </td>
                    </tr>
                </tbody>
            </table> 
        </fieldset>

        <hr />
        <input type="submit" value="Submit"/>
    </form:form>
</template:basic>