<%@ tag body-content="scriptless" trimDirectiveWhitespaces="true" %>
<%@ attribute name="htmlTitle" type="java.lang.String" rtexprvalue="true"
              required="true" %>
<%@ attribute name="bodyTitle" type="java.lang.String" rtexprvalue="true"
              required="true" %>
<%@ attribute name="headContent" fragment="true" required="false" %>
<%@ attribute name="navigationContent" fragment="true" required="true" %>
<%@ attribute name="authContent" fragment="true" required="true" %>
<%@ include file="/WEB-INF/jsp/base.jspf" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Tokens Service :: <c:out value="${fn:trim(htmlTitle)}" /></title>
        <link rel="stylesheet" href="http://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/2.3.1/css/bootstrap.min.css" />
        <link rel="stylesheet" href="<c:url value="/resource/stylesheet/main.css" />" />

        <jsp:invoke fragment="headContent" />
    </head>
    <body>
        <table id="mainTbl">
            <tbody>
                <tr class="header_row">
                    <td>                        
                        <table class="header_tbl">
                            <tbody>
                                <tr>
                                    <td class="header_left_col">
                                        <a href="mailto:tag4life@yandex.ru">[mailto]</a>
                                    </td> 
                                    <td class="header_cntr_col">
                                        [central]
                                    </td>
                                    <td class="header_right_col">
                                        <jsp:invoke fragment="authContent" />
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </td>
                </tr>
                <tr class="menu_row">                    
                    <td>
                        <table width="100%">
                            <tbody>
                                <tr>
                                    <td class="logo_col">
                                        <a href="/tkn"><h1>[Tag4Life]</h1></a>
                                    </td>                                    
                                    <td>
                                        <table width="100%" class="menu_tbl">
                                            <tbody>
                                                <tr>
                                                    <td>
                                                        <a href="/tkn"><h4>Home</h4></a>
                                                    </td> 
                                                    <td>
                                                        <a href="/tkn"><h4>Shop</h4></a>
                                                    </td>
                                                    <td>
                                                        <a href="/tkn"><h4>About</h4></a>
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </td>
                </tr>
                <tr class="body_row">
                    <td class="body_row_col" align="center">
                        <table id="bodyTable">
                            <tbody>
                                <tr>
                                    <td class="sidebarCell">
                                        <p>[Page menu]</p>
                                        <jsp:invoke fragment="navigationContent" />
                                    </td>
                                    <td class="contentCell" style="height: 500px; vertical-align: top;">
                                        <h2><c:out value="${fn:trim(bodyTitle)}" /></h2>
                                        <jsp:doBody />
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </td>
                </tr>
                <tr class="footer_row">
                    <td>
                        <div class="copyright_block">
                            <p>
                                Copyright &copy;<script>document.write(new Date().getFullYear());</script> - All rights reserved
                            </p>
                        </div>
                    </td>
                </tr>
            </tbody>
        </table>
        <script src="http://code.jquery.com/jquery-1.9.1.js"></script>
        <script src="http://cdnjs.cloudflare.com/ajax/libs/moment.js/2.0.0/moment.min.js"></script>
    </body>
</html>
