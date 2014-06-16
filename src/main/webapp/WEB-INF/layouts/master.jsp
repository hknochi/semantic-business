<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<gae:isDevelopment>
	<c:set var="bootstrapCss"
		value="//netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.css" />
	<c:set var="bootstrapJs"
		value="//netdna.bootstrapcdn.com/bootstrap/3.0.2/js/bootstrap.js" />
	<c:set var="jqueryJs" value="http://code.jquery.com/jquery-1.10.2.js" />
</gae:isDevelopment>
<gae:isProduction>
	<c:set var="bootstrapCss"
		value="//netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.min.css" />
	<c:set var="bootstrapJs"
		value="//netdna.bootstrapcdn.com/bootstrap/3.0.2/js/bootstrap.min.js" />
	<c:set var="jqueryJs"
		value="http://code.jquery.com/jquery-1.10.2.min.js" />
</gae:isProduction>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta name="description" content="" />
<meta name="author" content="HKnochi" />
<title><decorator:title /></title>
<decorator:head />

<%@ include file="/WEB-INF/views/includes/style.jsp"%>
</head>
<body>
	<div id="navigation">
		<%@ include file="/WEB-INF/views/includes/navigation.jsp"%>
	</div>
	<div id="content">
		<decorator:body />
	</div>
	<div id="footer">
		<%@ include file="/WEB-INF/views/includes/footer.jsp"%>
	</div>
	<div id="javascript">
		<%@ include file="/WEB-INF/views/includes/script.jsp"%>
	</div>
</body>
</html>
