<%--@elvariable id="registrationFailed" type="java.lang.Boolean"--%>
<%--@elvariable id="tokenRegistrationForm" type="ru.tokens.site.controller.TokenRegistrationController.TokenRegistrationForm"--%>
<template:basic_bs_two_col htmlTitle="Token registration" bodyTitle="Token registration">

    <jsp:attribute name="authContent">
        <jsp:include page="/WEB-INF/jsp/user.jspf" />
    </jsp:attribute> 
    
    <jsp:attribute name="leftColumnContent">
        <nav class="nav flex-column">
            <a class="nav-link" href="<c:url value="/user/view" />">
                User
            </a>
            <a class="nav-link" href="<c:url value="/token/user/postaddress/view" />">
                Post address
            </a>
            <a class="nav-link disabled" href="<c:url value="/token/user/view" />">
                Token
            </a> 
        </nav>
    </jsp:attribute>
    

    <jsp:body>
        <c:if test="${registrationFailed}">
            <div class="alert alert-warning alert-dismissible fade show" role="alert">
                The User ID or Activation Code you entered are not correct. Please try again.
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
        </c:if>
        <form:form method="post" modelAttribute="tokenRegistrationForm">
            <div class="form-group">
                <div class="form-group row">
                    <form:label path="uuid" class="col-sm-3 col-form-label">
                        Token Id
                    </form:label>
                    <div class="col-sm-9">
                        <form:input path="uuid" type="text" class="form-control" 
                                    aria-describedby="tknHelp" 
                                    placeholder="57Kkq2mjBVU1ql3tVH2u75Iuq3QQL10y4JoT" required="true" />
                        <small id="tknHelp" class="form-text text-muted">
                            36 characters!
                        </small>
                    </div>                
                </div>
                <div class="form-group row">
                    <form:label path="activationCode" class="col-sm-3 col-form-label">
                        Activation Code
                    </form:label>
                    <div class="col-sm-9">
                        <form:input path="activationCode" type="text" class="form-control" 
                                    aria-describedby="acHelp" 
                                    placeholder="Ztg50L@M@x8@" required="true" />
                        <small id="acHelp" class="form-text text-muted">
                            12 characters!
                        </small>
                    </div>                
                </div>
                <!-- Submit -->
                <div class="form-group row">
                    <span class="col-sm-3 col-form-label">
                        &nbsp;
                    </span>
                    <div class="col-sm-9">
                        <button class="btn btn-primary" type="submit">Register</button>
                    </div>                
                </div>
            </div>            
        </form:form>
    </jsp:body>

</template:basic_bs_two_col>
