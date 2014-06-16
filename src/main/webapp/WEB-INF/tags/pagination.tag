<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ attribute name="maxPages" type="java.lang.Integer" required="true"
	rtexprvalue="true" description="The maximum number of pages available"%>
<%@ attribute name="page" type="java.lang.Integer" required="false"
	rtexprvalue="true"
	description="The current page (not required, defaults to 1)"%>
<%@ attribute name="size" type="java.lang.Integer" required="false"
	rtexprvalue="true"
	description="The number of records per page (not required, defaults to 5)"%>

<c:if test="${empty page || page lt 1}">
	<c:set var="page" value="1" />
</c:if>

<c:if test="${empty maxPages || maxPages lt 1}">
	<c:set var="maxPages" value="1" />
</c:if>

<c:if test="${empty size || size lt 1}">
	<c:set var="size" value="5" />
</c:if>

<spring:message code="list_size" var="list_size" htmlEscape="false" />
<c:out value="${list_size} " />
<c:forEach var="i" begin="5" end="25" step="5">
	<c:choose>
		<c:when test="${size eq i}">
			<c:out value="${i}" />
		</c:when>
		<c:otherwise>
			<spring:url value="" var="sizeUrl">
				<spring:param name="page.page" value="1" />
				<spring:param name="page.size" value="${i}" />
			</spring:url>
			<a href="${sizeUrl}">${i}</a>
		</c:otherwise>
	</c:choose>
	<c:out value=" " />
</c:forEach>
<%-- <c:out value="| " /> --%>

<div class="paging">
		<c:if test="${page ne 1  && page-1 ne 1 }">
			<spring:url value="" var="first">
				<spring:param name="page.page" value="1" />
				<spring:param name="page.size" value="${size}" />
				<spring:param name="query" value="${query}" />
			</spring:url>
			<spring:url value="/resources/images/resultset_first.png"
				var="first_image_url" />
			<spring:message code="list_first" var="first_label"
				htmlEscape="false" />
			<a href="${first}" title="${fn:escapeXml(first_label)}">&laquo;</a>
		</c:if>
		<c:if test="${page gt 1 }">
			<spring:url value="" var="previous">
				<spring:param name="page.page" value="${page - 1}" />
				<spring:param name="page.size" value="${size}" />
			</spring:url>
			<spring:url value="/resources/images/resultset_previous.png"
				var="previous_image_url" />
			<spring:message code="list_previous" var="previous_label"
				htmlEscape="false" />
			<a href="${previous}" title="${fn:escapeXml(previous_label)}">${page
				- 1}</a>

		</c:if>
		<c:out value=" " />
		<%-- 	<spring:message code="list_page" arguments="${page},${maxPages}" --%>
		<!-- 		argumentSeparator="," /> -->

		<span class='current'>${page}</span>
		<c:out value=" " />
		<c:if test="${page lt maxPages}">
			<spring:url value="" var="next">
				<spring:param name="page.page" value="${page + 1}" />
				<spring:param name="page.size" value="${size}" />
			</spring:url>
			<spring:url value="/resources/images/resultset_next.png"
				var="next_image_url" />
			<spring:message code="list_next" var="next_label" htmlEscape="false" />
			<a href="${next}" title="${fn:escapeXml(next_label)}">${page + 1}</a>
		</c:if>
		<c:if test="${page ne maxPages && page+1 ne maxPages}">
			<spring:url value="" var="last">
				<spring:param name="page.page" value="${maxPages}" />
				<spring:param name="page.size" value="${size}" />
			</spring:url>
			<spring:url value="/resources/images/resultset_last.png"
				var="last_image_url" />
			<spring:message code="list_last" var="last_label" htmlEscape="false" />
			<a href="${last}" title="${fn:escapeXml(last_label)}">&raquo;</a>
		</c:if>
</div>
