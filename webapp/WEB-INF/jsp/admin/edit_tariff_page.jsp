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
                                <h3 class="card-text text-center banner-child text-info"><fmt:message key="edit_tariff"/></h3>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-6 mx-auto">
                            <form id="createTariffForm" action="/admin/tariff/edit" method="POST">
                                <input id="tariffId" type="hidden" name="id" value="${tariff.id}">
                                <select id="editTariffProductSelect" class="form-select form-control border-info text-info mb-3" name="productId">
                                    <c:forEach items="${products}" var="product">
                                        <option value="${product.id}" ${product.id == tariff.product.id ? "selected":""}><fmt:message key="${product.name}"/></option>
                                    </c:forEach>
                                </select>
                                <div class="mb-3">
                                    <input id="name" class="form-control border-info text-info" placeholder="<fmt:message key="tariff_name"/>" type="text" name="name" value="${tariff.name}">
                                </div>
                                <div class="mb-3">
                                    <input id="description" class="form-control border-info text-info" placeholder="<fmt:message key="tariff_description"/>" type="text" name="description" value="${tariff.description != null ? tariff.description : ""}">
                                </div>
                                <div class="mb-3">
                                    <input id="cost" class="form-control border-info text-info" placeholder="<fmt:message key="tariff_cost"/>" type="text" name="price" value="${tariff.price}">
                                </div>
                            </form>

                            <div class="col text-center mb-3">
                                <button id="tariffUpdate" class="btn btn-info text-white" type="button"><fmt:message key="edit"/></button>
                            </div>

                            <h5 class="text-info"><fmt:message key="tariff_options"/></h5>
                            <table id="editTariffOptionTable" class="table table-bordered border-info text-center text-info">
                                <c:forEach items="${tariff.options}" var="tariffItem">
                                    <tr>
                                        <td><input class="tariffOption form-control border-info text-info tariff-option" placeholder="<fmt:message key="tariff_option"/>" type="text" value="${tariffItem.item}"></td>
                                        <td><button class="editTariffOption btn border-warning text-warning" attr-id="${tariffItem.id}"><fmt:message key="edit"/></button></td>
                                        <td><button class="deleteTariffOption btn border-danger text-danger" attr-id="${tariffItem.id}"><fmt:message key="delete"/></button></td>
                                    </tr>
                                </c:forEach>
                            </table>

                            <div class="col text-center mb-3">
                                <button id="addNewTariffOptionTable" class="btn btn-info text-white" type="button"><fmt:message key="add_option"/></button>
                            </div>

                            <div id="optionLabel" style="display: none"><fmt:message key="tariff_option"/></div>
                            <div id="createLabel" style="display: none"><fmt:message key="create"/></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%@ include file="/WEB-INF/jspf/footer.jspf" %>
    </body>
</html>