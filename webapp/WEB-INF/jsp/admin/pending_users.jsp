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
        <div class="row admin">
            <div class="col-sm">
                <div class="card border-info banner-height">
                    <div class="card-img-overlay">
                        <div class="align-self-center banner-parent">
                            <h3 class="card-text text-center banner-child text-info"><fmt:message key="pending_users_title"/></h3>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-6 mx-auto">
                        <table class="table table-bordered border-info text-center text-info">
                            <thead>
                            <tr>
                                <td>#</td>
                                <td><fmt:message key="username"/></td>
                                <td><fmt:message key="status"/></td>
                                <td></td>
                            </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${users}" var="user">
                                    <tr>
                                        <td>${user.id}</td>
                                        <td>${user.username}</td>
                                        <td>${user.active}</td>
                                        <td>
                                            <button class="activate btn btn-info text-white" attr-user="${user.id}">Activate
                                            </button>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                        </table>

                        <nav>
                            <ul class="pagination pagination-sm">
                                <c:if test="${totalPages > 0}">
                                    <c:forEach items="${pageNumbers}" var="pageNumber">
                                        <li class="${pageNumber == currentPage ? 'page-item active' : 'page-item'}">
                                            <a class="page-link text-info" href="/admin/pending-users?page=${pageNumber}&size=${size}">${pageNumber}</a>
                                        </li>
                                    </c:forEach>
                                </c:if>
                            </ul>
                        </nav>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <%@ include file="/WEB-INF/jspf/footer.jspf" %>
</body>
</html>
