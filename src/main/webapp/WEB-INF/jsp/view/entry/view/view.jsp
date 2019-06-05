<%--@elvariable id="entry" type="ru.tokens.site.entities.MedicalFormEntry"--%>
<%--@elvariable id="token" type="ru.tokens.site.entities.Token"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:basic_bs_one_col_simple htmlTitle="View Medical Entry" 
                                  bodyTitle="Entry #${entry.id} : ${entry.subject}">
    <jsp:body>
        <div class="container">
            <a class="btn btn-info" href="<c:url value="/token/${token.tokenId}/${token.uuidString}">
                   <c:param name="showMH" value="true" />
               </c:url>">
                Back
            </a><br /><br />
            <div class="border rounded mb-3 bg-light text-dark">
                <div class="p-2">
                    <h5 class="m-2 pl-4">
                        <c:out value="${entry.subject}" />
                    </h5>
                    <div class="row justify-content-lg-center py-2">
                        <div class="col-md-11 border-top p-2">
                            <small>
                                Created: <wrox:formatDate value="${entry.dateCreated}" type="both"
                                                 timeStyle="long" dateStyle="full" />
                            </small>
                        </div>                            
                    </div>
                    <div class="row justify-content-lg-center py-2">
                        <div class="col-md-11 border-top p-2">
                            <c:out value="${entry.body}" escapeXml="false" />
                        </div>                            
                    </div> 

                    <c:if test="${entry.numberOfAttachments > 0}">
                        <div class="row justify-content-lg-center">
                            <div class="col-md-11">
                                <table class="table">
                                    <thead>
                                        <tr>
                                            <th>Filename</th>
                                            <th>Size</th>                    
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${entry.attachments}" var="attachment">
                                            <tr>
                                                <td>
                                                    <a href="<c:url context="/tkn" 
                                                           value="/token/${token.tokenId}/${token.uuidString}/med/entry/view/${entry.id}/attachment/${attachment.name}" />">
                                                       <c:out value="${attachment.name}" />
                                                    </a>
                                                </td>
                                                <td>
                                                    <c:out value="${attachment.contentSize}" />
                                                </td>                        
                                            </tr>                
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>                            
                        </div> 
                    </c:if> 
                </div>
            </div>
        </div>
    </jsp:body>


</template:basic_bs_one_col_simple>