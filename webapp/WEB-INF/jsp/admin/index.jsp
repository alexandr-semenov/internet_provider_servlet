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
                            <h3 class="card-text text-center banner-child text-info"><fmt:message key="admin"/></h3>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-6 mx-auto">
                        <h4 class="text-info"><fmt:message key="admin_options"/></h4>
                        <table class="table table-bordered border-info text-center text-info">
                            <thead>
                            <tr>
                                <td valign="middle"><fmt:message key="activate_new_users"/></td>
                                <td><a href="/admin/pending-users" class="btn btn-info text-white" type="button"><fmt:message key="activate"/></a></td>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td valign="middle"><fmt:message key="edit_tariffs"/></td>
                                <td><a href="/admin/tariffs/edit" class="btn btn-info text-white" type="button"><fmt:message key="edit"/></a></td>
                            </tr>
                            <tr>
                                <td valign="middle"><fmt:message key="create_tariff"/></td>
                                <td><a href="/admin/tariff/create" class="btn btn-info text-white" type="button"><fmt:message key="create"/></a></td>
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