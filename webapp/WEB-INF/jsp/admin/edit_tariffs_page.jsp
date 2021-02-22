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
                                    <h3 class="card-text text-center banner-child text-info"><fmt:message key="edit_tariffs"/></h3>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-6 mx-auto">
                                <h5 class="text-info"><fmt:message key="service"/></h5>
                                <select id="editTariffProductSelect" class="form-select form-control border-info text-info mb-3">
                                    <c:forEach items="${products}" var="product">
                                    <option value="${product.id}"><fmt:message key="${product.name}"/></option>
                                    </c:forEach>
                                </select>

                                <h5 class="text-info"><fmt:message key="tariffs"/></h5>
                                <table id="editTariffTables" class="table table-bordered border-info text-center text-info">
                                    <tbody>

                                    </tbody>
                                </table>
                                <div id="editLabel" style="display: none"><fmt:message key="edit"/></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <%@ include file="/WEB-INF/jspf/footer.jspf" %>
            <script type="text/javascript" src="${request.contextPath}/js/edit-tariff.js"></script>
        </body>
</html>