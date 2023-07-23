<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="jp.co.netmarks.common.Constants"%>
<jsp:directive.include file="/WEB-INF/jsp/common/taglibs.jsp"/>
<%-- ファビコン --%>
<link rel="shortcut icon" href="${ pageContext.request.contextPath }/images/favicon.ico">

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="content-language" content="ja">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="-1">
<title>CUCMメンテナンスシステム<c:if test="${ not empty title }"> - <c:out value="${ title }" /></c:if></title>
<link type="text/css" rel="stylesheet" href="${ pageContext.request.contextPath }/css/reset.css"/>
<link type="text/css" rel="stylesheet" href="${ pageContext.request.contextPath }/css/978grid-fluid.css"/>
<link type="text/css" rel="stylesheet" href="${ pageContext.request.contextPath }/lib/jquery-easyui/themes/metro-blue/easyui.css"/>
<link type="text/css" rel="stylesheet" href="${ pageContext.request.contextPath }/lib/jquery-easyui/themes/icon.css"/>
<link type="text/css" rel="stylesheet" href="${ pageContext.request.contextPath }/css/default.css"/>
<link type="text/css" rel="stylesheet" href="${ pageContext.request.contextPath }/css/input.css"/>
<script type="text/javascript" src="${ pageContext.request.contextPath }/js/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="${ pageContext.request.contextPath }/lib/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ pageContext.request.contextPath }/lib/jquery-easyui/locale/easyui-lang-ja.js"></script>
<script type="text/javascript" src="${ pageContext.request.contextPath }/js/common.js"></script>
<script type="text/javascript" src="${ pageContext.request.contextPath }/js/message.js"></script>
<script type="text/javascript">
<!--
//-->
</script>