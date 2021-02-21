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
                    <div class="card border-primary banner-height">
                        <div class="card-img-overlay">
                            <div class="align-self-center banner-parent">
                                <h3 class="card-text text-center banner-child text-primary"><fmt:message key="deposit_page"/></h3>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-4 mx-auto">
                            <h4 class="text-primary"><fmt:message key="make_deposit"/></h4>
                            <form id="deposit-form" action="/cabinet/deposit">
                                <div class="mb-3">
                                    <input id="amount" class="form-control border-primary text-primary" placeholder="<fmt:message key="amount_placeholder"/>" type="text" name="amount" value="">
                                </div>
                                <div class="col text-center">
                                    <button id="deposit" class="btn btn-primary text-white" type="button"><fmt:message key="deposit_button"/></button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%@ include file="/WEB-INF/jspf/footer.jspf" %>
    </body>
</html>
