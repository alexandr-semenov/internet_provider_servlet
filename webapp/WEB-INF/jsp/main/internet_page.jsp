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
                <div class="col-sm">
                    <div class="card bg-info text-white banner-height">
                        <div class="card-img-overlay">
                            <div class="align-self-center banner-parent">
                                <h3 class="card-text text-center banner-child"><fmt:message key="internet_page_title"/></h3>
                            </div>
                        </div>
                    </div>
                    <div class="slider">
                        <c:forEach items="${product.tariffs}" var="tariff">
                            <div class="col-sm-4">
                                <div class="card border-info" style="width: 20rem;">
                                    <div class="card-body text-center text-info">
                                        <h5 class="card-title"><fmt:message key="${tariff.name}"/></h5>
                                        <p class="card-text"><fmt:message key="${tariff.description}"/></p>
                                        <ul class="list-group list-group-flush">
                                            <c:forEach items="${tariff.options}" var="item">
                                                <li class="list-group-item"><fmt:message key="${item.item}"/></li>
                                            </c:forEach>
                                        </ul>
                                        <h5 class="card-footer">${tariff.price} <fmt:message key="cost_month"/></h5>
                                        <a href="/subscription/?tariff_id=${tariff.id}" class="btn btn-info text-white"><fmt:message key="order_button"/></a>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <%@ include file="/WEB-INF/jspf/footer.jspf" %>
</body>
</html>