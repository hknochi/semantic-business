<%@page import="com.google.common.base.Joiner"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set value="<%=request.getContextPath()%>" var="url" />
<html>
<head>
<title>Home</title>
</head>
<body>
	<div class="row">
		<div class="col-md-3"></div>
		<div class="col-md=6">
			<form role="form" method="get" action="/search">
				<div class="form-group">
					<div class="input-group">
						<input type="text" class="form-control" name="q"
							placeholder="Enter search text here ..."> <span
							class="input-group-btn">
							<button class="btn btn-default" type="submit">Search!</button>
						</span>
					</div>
					<!-- /input-group -->
				</div>
			</form>
		</div>
		<div class="col-md-3"></div>
	</div>
	<c:if test="${not empty results}">
		<h2 class="sub-header">Results</h2>
		<div class="table-responsive">
			<table class="table table-striped">
				<thead>
					<tr>
						<th>#</th>
						<th>icon</th>
						<th>name</th>
						<th>address</th>
						<th>action</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${results.results}" var="result"
						varStatus="index">
						<tr>
							<td>${page*fn:length(results.results)+index.count}</td>
							<td><img src="${result.icon}" width="30" /></td>
							<td>${result.name}<br />
								<p class="text-muted">
									<c:forEach items="${result.types}" var="type" varStatus="typeIndex">${type}
									<c:if test="${typeIndex.count < fn:length(result.types)}">,</c:if>
									</c:forEach>
								</p></td>
							<td>${result.address}</td>
							<td>action</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<ul class="pager">
				<li class="previous"><a href=""
					onclick="history.back(); return false;">&larr; Older</a></li>
				<li class="next"><a
					href="${url}/next?t=${results.nextPageToken}&p=${page+1}">next
						&rarr;</a></li>
			</ul>
		</div>
	</c:if>
</body>
</html>
