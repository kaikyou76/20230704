<%@page import="jp.co.netmarks.common.Constants"%>
<%@page import="org.eclipse.jdt.internal.compiler.impl.Constant"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/taglibs.jsp"/>
<script type="text/javascript">
	<!--
	
		/**
		 * onload処理
		 */
		$(function() {
			$('#helpBtn').click(function() {
				/* ダイアログ表示処理 */
				$('#helpDialog').dialog({
				    title  : 'ヘルプ',
				    width  : 680,
				    height : 570,
				    closed : false,
				    cache  : false,
				    modal  : true,
				    href   : "${ pageContext.request.contextPath }/html/help.html",
				    buttons: [{
				            text:'閉じる', iconCls:"icon-cancel" ,handler: function(){
				            	$('#helpDialog').dialog({closed: true});
				            }
			        }],
				});
			});
		});
	
	//-->
</script>	

<div id="header" class="container clearfix" style="min-width:920px;height: 60px;" >
	<div class="grid20F first">
		<a class="logo" href="${ pageContext.request.contextPath }/userAndTel">SMBC</a>
	</div>
	<div class="grid40F title" style="text-align:right;">
		<c:if test="${ not empty param.title }"><c:out value="${ param.title }" /></c:if>
	</div>
	<c:if test="${ not empty param.infoDisplay && param.infoDisplay }">
		<div class="grid40 user-info">
			<sec:authorize access="isAuthenticated()">
	<!-- 			ログインID：[
					<sec:authentication property="details.loginId" />
				] -->
				ログインユーザ名：[
					<sec:authentication property="details.kanjiUserName" />
				]
				権限：[
					<sec:authorize access="hasRole('ROLE_ADMIN')"><%= Constants.ROLE_ADMIN_NAME %></sec:authorize>
					<sec:authorize ifNotGranted="ROLE_ADMIN">
						<sec:authorize access="hasRole('ROLE_CHANGE')"><%= Constants.ROLE_CHANGE_NAME %></sec:authorize>
					</sec:authorize>
					<sec:authorize ifNotGranted="ROLE_CHANGE"><%= Constants.ROLE_USER_NAME %></sec:authorize>
				]
				｜ <a id="helpBtn" target="_blank" class="easyui-linkbutton" iconCls="icon-help" plain="true"></a>
				｜ <a href="${ pageContext.request.contextPath }/logout">ログアウト</a>
			</sec:authorize>
		</div>
		<br>
		<div class="grid30F first">
			<div style="margin-left:23px;font-weight:bold;">CUCMメンテナンスシステム</div>
		</div>
		<div id="global-menu" class="grid70F" style="text-align:right;">
			<sec:authorize access="isAuthenticated()">
				<sec:authorize access="hasRole('ROLE_USER')">
					<a href="${ pageContext.request.contextPath }/userAndTel">ユーザーと電話機の一覧 </a>
				</sec:authorize>
				<sec:authorize access="hasRole('ROLE_CHANGE')">
					&nbsp;&nbsp;&nbsp;<a href="${ pageContext.request.contextPath }/telLumpEdit">電話機一括登録 </a>
					&nbsp;&nbsp;&nbsp;<a href="${ pageContext.request.contextPath }/authManagement">権限管理</a>
					&nbsp;&nbsp;&nbsp;<a href="${ pageContext.request.contextPath }/checkConsistency">整合性チェック</a>
					&nbsp;&nbsp;&nbsp;<a href="${ pageContext.request.contextPath }/maintenance">メンテナンス</a>
					&nbsp;&nbsp;&nbsp;<a href="${ pageContext.request.contextPath }/logManagement">ログ管理</a>
				</sec:authorize>
				<sec:authorize access="hasRole('ROLE_USER')">
					&nbsp;&nbsp;&nbsp;<a href="${ pageContext.request.contextPath }/changePassword">パスワード変更</a>
				</sec:authorize>
			</sec:authorize>
		</div>
	</c:if>
	<c:if test="${ empty param.infoDisplay}">
	<div class="grid40 user-info" style="text-align:right;">
		<a href="${ pageContext.request.contextPath }/logout">ログアウト</a>
	</div>
	</c:if>
</div>
<div id="helpDialog"></div>