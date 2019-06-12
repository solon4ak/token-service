<%--@elvariable id="wrongEmail" type="java.lang.Boolean"--%>
<%--@elvariable id="message" type="java.lang.String"--%>
<%--@elvariable id="passwordResetForm" type="ru.tokens.site.controller.PasswordResetController.PasswordResetForm"--%>
<template:basic_bs_two_col htmlTitle="Password reset" bodyTitle="Password reset">

    <jsp:body>
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
                <div class="form-group row">
                    <form:label path="email" class="col-md-4 col-form-label">
                        E-mail
                    </form:label>
                    <div class="col-md-8">
                        <form:input path="email" type="email" class="form-control" 
                                    placeholder="Enter email" required="true" />                    
                    </div>                
                </div>
                <div class="form-group row">
                    <span class="col-md-4 col-form-label">
                        &nbsp;
                    </span>
                    <div class="col-md-8">
                        <button class="btn btn-primary" type="submit">Confirm</button>
                    </div>                
                </div>
            </div>
        </form:form>
    </jsp:body>
</template:basic_bs_two_col>
