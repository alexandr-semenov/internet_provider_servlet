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
                            <h3 class="card-text text-center banner-child text-info"><fmt:message key="login"/></h3>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-4 mx-auto">
                <form id="login-form" action="/login">
                    <div class="mb-3">
                        <input id="username" class="form-control border-info text-info" placeholder="<fmt:message key="username_placeholder"/>" type="text" name="username" value="">
                    </div>

                    <div class="mb-3">
                        <input id="password" class="form-control border-info text-info" placeholder="<fmt:message key="password_placeholder"/>" type="password" name="password" value="">
                    </div>
                    <div class="col text-center">
                        <button id="login" class="btn btn-info text-white" type="button"><fmt:message key="send_button"/></button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <%@ include file="/WEB-INF/jspf/footer.jspf" %>
</body>
</html>
