<%--@elvariable id="wrongEmail" type="java.lang.Boolean"--%>
<%--@elvariable id="message" type="java.lang.String"--%>
<%--@elvariable id="passwordResetForm" type="ru.tokens.site.controller.PasswordResetController.PasswordResetForm"--%>
<template:basic_bs_one_col htmlTitle="Password reset" bodyTitle="Сброс пароля">

    <jsp:body>
        <div class="container  justify-content-center">
            <c:if test="${wrongEmail}">
                <div class="alert alert-warning alert-dismissible fade show" role="alert">
                    <c:out value="${message}" />
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </c:if>
            <form:form method="post" modelAttribute="passwordResetForm">
                <div class="form-group">
                    <div class="form-group">
                        <div>
                            <form:input path="email" type="email" class="form-control" 
                                        placeholder="Enter email" required="true" />                    
                        </div>                
                    </div>
                    <div class="form-group">                        
                        <div>
                            <button class="btn btn-primary" type="submit">Отправить</button>
                        </div>                
                    </div>
                </div>
            </form:form>
        </div>

    </jsp:body>
</template:basic_bs_one_col>
