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
                                <h3 class="card-text text-center banner-child text-primary"><fmt:message key="cabinet_title"/></h3>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-6 mx-auto">
                            <h4 class="text-primary"><fmt:message key="subscription"/></h4>
                            <table class="table table-bordered border-primary text-center text-primary">
                                <thead>
                                <tr>
                                    <td>#</td>
                                    <td><fmt:message key="tariff_name_title"/></td>
                                    <td><fmt:message key="tariff_description_title"/></td>
                                    <td><fmt:message key="tariff_price_title"/></td>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${user.subscription.tariffs}" var="tariff">
                                    <tr>
                                        <td>${tariff.id}</td>
                                        <td><fmt:message key="${tariff.name}"/></td>
                                        <td><fmt:message key="${tariff.description != null ? tariff.description : '-'}"/></td>
                                        <td>${tariff.price}</td>
                                    </tr>
                                </c:forEach>
                                <tr>
                                    <td colspan="3" style="text-align: left;"><fmt:message key="total"/></td>
                                    <td>${user.subscription.price}</td>
                                </tr>
                                </tbody>
                            </table>

                            <h4 class="text-primary"><fmt:message key="subscription_status"/></h4>
                            <table class="table table-bordered border-primary text-center text-primary">
                                <tbody>
                                <tr>
                                    <td valign="middle"><fmt:message key="status"/></td>
                                    <td valign="middle" class="${user.subscription.status == 'NOT_ACTIVE' ? 'text-danger' : 'text-success' }"><fmt:message key="${user.subscription.status}"/></td>
                                </tr>
                                <tr>
                                    <td><fmt:message key="amount_to_be_paid"/></td>
                                    <td>${user.subscription.price - user.account.amount}</td>
                                </tr>
                                </tbody>
                            </table>

                            <h4 class="text-primary"><fmt:message key="balance"/></h4>
                            <table class="table table-bordered border-primary text-center text-primary">
                                <thead>
                                <tr>
                                    <td><fmt:message key="current_balance"/></td>
                                    <td>${user.account.amount}</td>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td></td>
                                    <td><a href="/cabinet/deposit" class="btn btn-primary" type="button"><fmt:message key="deposit"/></a></td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%@ include file="/WEB-INF/jspf/footer.jspf" %>
    </body>
</html>