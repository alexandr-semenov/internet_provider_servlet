<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%--Localization--%>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="lang/res"/>
<%----%>

<html>
    <%@ include file="/WEB-INF/jspf/head.jspf" %>
    <body>
        <%@ include file="/WEB-INF/jspf/nav_bar.jspf" %>
        <div class="container">
            <div class="row">
                <div class="col-sm">
                    <div class="card border-info banner-height">
                        <div class="card-img-overlay">
                            <div class="align-self-center banner-parent">
                                <h3 class="card-text text-center banner-child text-info"><fmt:message key="internet_provider"/></h3>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%@ include file="/WEB-INF/jspf/footer.jspf" %>
    </body>
</html>