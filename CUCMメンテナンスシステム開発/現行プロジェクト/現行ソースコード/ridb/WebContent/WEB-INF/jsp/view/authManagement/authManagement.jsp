<%@page contentType="text/html" pageEncoding="UTF-8" errorPage="/jsp/error" %>
<jsp:directive.include file="/WEB-INF/jsp/common/taglibs.jsp"/>
<%@ page import="jp.co.netmarks.common.*" %>
<!doctype html>
<html>
<head>
	<c:import url="/WEB-INF/jsp/common/htmlHeader.jsp"/>
	<script type="text/javascript" src="${ pageContext.request.contextPath }/js/jquery.gridutil.js"></script>
	<script type="text/javascript">
	<!--
		/* プルダウンラベル用 */
		var ALL_LABEL         = "<%= Constants.ALL_LABEL %>";

		/* Grid用 */
		var targetGrid;
		var grid;

		/**
		 * onload処理
		 */
		$(function() {

			/* 初期フォーカス */
			$("#" + "${ authManagementForm.focus }").focus();

			/* Grid用ライブラリを設定 */
			targetGrid = $("#authManagementTargetGrid").gridutil({
					'syncWidthPanel': '#searchCondition',
					'resizeHeight' : false,
					'editables'    : {
						'authId'  : '#authManagementTargetGridForm select[name=authId]'
					}
			});

			grid = $("#authManagementGrid").gridutil({
				'syncWidthPanel': '.gridPanel',
				'resizeHeight' : false
			});

			/* 検索ボタン押下時イベントを設定 */
			$('#authManagementForm').submit(search);
			$('#searchBtn').click(search);

			/* 拠点プルダウンイベント */
			$('#branchUserId').change(getSectionUserList);

			/* 管理権限を更新 */
			$('#updateAuthBtn').click(function() {

				/* 更新対象チェック */
				if ($("#authManagementTargetGrid").datagrid('getRows').length == 0) {
					$.messager.alert(t0001,getMessage(e0002,'更新対象データ'));
					return false;
				}

				/* 選択 */
				$.messager.confirm(t0003, getMessage(c0004,'権限更新'), function (r) {
					if (!r) return false;

					targetGrid.update('authManagement/authManagementUpdate', '#authManagementTargetGridForm', function (data) {
						if (data["authManagementListUpdateForm"] && data["authManagementListUpdateForm"].message) {
							$.messager.alert(t0002,data["authManagementListUpdateForm"].message);
						/* 再検索 */
						reload();
						}
					});
				});
			});

			/* チェックした社員を削除 */
			$('#deleteAuthBtn').click(function() {

				/* CHECKBOXチェック */
				if ($("#authManagementGrid").datagrid('getChecked').length == 0) {
					$.messager.alert(t0001,getMessage(e0002,'削除対象データ'));
					return false;
				}

				/* 選択 */
				$.messager.confirm(t0003, getMessage(c0004,'権限削除'), function (r) {
					if (!r) return false;

					grid.update('authManagement/authManagementDelete', '#authManagementGridForm', function (data) {
						if (data["authManagementListUpdateForm"] && data["authManagementListUpdateForm"].message) {
							$.messager.alert(t0002,data["authManagementListUpdateForm"].message);
							/* 再検索 */
							reload();
						}
					});
				});
			});

			/* 選択した社員のアカウントロックを解除 */
			$('#unlockAccountBtn').click(function() {

				/* 選択データチェック */
				var row = $('#authManagementGrid').datagrid('getSelected');
				if(!row) {
					$.messager.alert(t0001,getMessage(e0002,'解除対象データ'));
					return false;
				} else if(row.account_lock == '0'){
					$.messager.alert(t0001,getMessage(e0002,'電話機'));
					return false;
				}

				/* 選択 */
				$.messager.prompt(t0003, getMessage(c0005,'アカウントロック解除'), function (r) {
					if (!r) return false;

					grid.update('authManagement/authManagementDelete', '#authManagementGridForm', function (data) {
						if (data["authManagementListUpdateForm"] && data["authManagementListUpdateForm"].message) {
							$.messager.alert(t0002,data["authManagementListUpdateForm"].message);
							/* 再検索 */
							reload();
						}
					});
				});
			});
		});

		/**
		 * 検索処理
		 */
		function search() {

			/* ページを1行目にする */
			$("#authManagementGrid").datagrid('options').pageNumber = 1;
			$("#authManagementTargetGrid").datagrid('options').pageNumber = 1;

			/* 管理権限付与者一覧検索 */
			grid.search('authManagement/authManagementSearch', '#authManagementForm');

			/* 管理権限付与対象者一覧検索 */
			targetGrid.search('authManagement/authManagementTargetSearch', '#authManagementForm');

			return false;
		}

		/**
		 * 再検索処理
		 */
		function reload() {
			$("#authManagementGrid").datagrid("reload");
			$("#authManagementTargetGrid").datagrid("reload");
		}

		/**
		 * 拠点（ユーザー）選択時処理
		 */
		function getSectionUserList() {
			/* 店部課（ユーザー）の変更 */
			getSectionList("#sectionUserId" ,"#branchUserId");
		}

		/**
		 * 拠点プルダウン変更処理
		 * 概要：拠点プルダウン変更時に、該当の店部課の選択値を変更します。
		 */
		function getSectionList(targetId, eventId){
			$.ajax({
				type : "POST",
				url : "authManagement/getAuthSectionList?id=" + $(eventId).val(),
				success : function (data, status, xhr) {
					 if (data["data"]) {
						 /* プルダウンの入れ替え */
						 if(data["data"][0]){
							 changeSelector(targetId,data["data"]);
						 } else {
							 changeSelector(targetId,[{'label':ALL_LABEL,'value':''}]);
						 }

					 } else {
						/* timeout */
						$(window).attr("location","login");
				     }
				},
				error: function () {
					$(window).attr("location","jsp/error");
				}
			});
		}

		/**
		 * formatter:処理リスト
		 */
		function formatManagement(value,row,index){

			var jsonList = ${ authManagementForm.managementListJson };

	 		//jsonList = [{'label':ALL_LABEL,'value':''}];

			return $.gridutil.formatSelectTag(jsonList,
					{ name :'authId' , 'class' : 'authId_select' }, value);
		}
	-->

	</script>
</head>
<body>
<%-- Header --%>
<c:import url="/WEB-INF/jsp/common/header.jsp">
	<c:param name="title" value="権限管理" />
	<c:param name="infoDisplay" value="true" />
</c:import>
<div id="contents" class="container" style="min-width:955px">
	<div id="searchCondition" class="easyui-panel searchCondition" title="検索条件" iconCls="icon-search" collapsible="true">
		<form id="authManagementForm">

			<input type="submit" style="top: -1000px; left: 0px; margin-left:-1000px;position: absolute;"/>
			<table class="narrow_table">
				<colgroup>
					<col width="20">
					<col width="35" style="padding:0px;">	<!-- 1 -->
					<col width="270">	<!-- 2 -->
					<col width="70">	<!-- 3 -->
					<col width="270">	<!-- 4 -->
					<col width="160">	<!-- 5 -->
				</colgroup>
				<tr>
					<td></td>
					<td>
						<label for="branchUserId" >拠点</label>
					</td>
					<td>
						<select id="branchUserId" name="branchUserId" class="branch_select">
							<c:forEach var="branch" items="${ authManagementForm.branchUserList }" varStatus="status">
								<option value="${ branch.value }"><c:out value="${ branch.label }"/></option>
							</c:forEach>
						</select>
					</td>
					<td>
						<label for="sectionUserId" >所属店部課</label>
					</td>
					<td>
						<select id="sectionUserId" name="sectionUserId" class="section_select">
							<c:forEach var="section" items="${ authManagementForm.sectionUserList }" varStatus="status">
								<option value="${ section.value }"><c:out value="${ section.label }"/></option>
							</c:forEach>
						</select>
					</td>
					<td>
						<a id="searchBtn" class="easyui-linkbutton custom-button" iconCls="icon-search" plain="true" >検索</a>
					</td>
				</tr>
			</table>

		</form>
	</div>
	<div class="panel-bottom"></div>
	<div id="userList" class="easyui-panel gridPanel" title="管理者権限登録予定対象者" collapsible="true"
	 style="background:#fafafa;min-height:235px;min-width:950px" >
		<form id="authManagementTargetGridForm" name="gridForm">
			<%-- トークン --%>
			<input type="hidden" name="_RequestVerificationToken"  />
			<table id="authManagementTargetGrid" class="easyui-datagrid " style="height:230px;margin-right:5px;" toolbar="#toolbarAuth"
				   rownumbers="true"  pagination="true"  striped="true" singleSelect="true" checkOnSelect="true" selectOnCheck="false">
				<thead>
					<tr>
						<th field="authId"           width="150" align="left"  sortable="true" data-options="formatter:formatManagement">処理</th>
						<th field="employeeId"       width="150" align="left"  sortable="true">社員コード</th>
						<th field="kanjiUserName"    width="200" align="left"  sortable="true">利用者名</th>
						<th field="branchUserName"   width="375" align="left"  sortable="true">拠点</th>
						<th field="sectionUserName"  width="375" align="left"  sortable="true">所属店部課</th>
					</tr>
				</thead>
			</table>
			<div id="toolbarAuth">
				<a id="updateAuthBtn" href="javascript:void(0)" class="easyui-linkbutton custom-button" plain="true">管理権限を更新</a>&nbsp;&nbsp;&nbsp;&nbsp;
			</div>
		</form>
	</div>
	<div class="panel-bottom"></div>
	<div id="authUserList" class="easyui-panel gridPanel" title="管理者権限登録完了対象者" collapsible="true"
	 style="background:#fafafa;min-height:235px;min-width:950px" >
		<form id="authManagementGridForm" name="gridForm">
			<table id="authManagementGrid" class="easyui-datagrid " style="height:230px" toolbar="#toolbarDel"
				   rownumbers="true"  pagination="true"  striped="true" singleSelect="false" checkOnSelect="true" selectOnCheck="true">
				<thead>
					<tr>
						<th field="ck"               width="20"  align="center"  checkbox="true" ></th>
						<th field="employeeId"       width="150" align="left"    sortable="true">社員コード</th>
						<th field="kanjiUserName"    width="200" align="left"    sortable="true">利用者名</th>
						<th field="authName"         width="100" align="left"    sortable="true">権限</th>
						<th field="lockStateName"    width="100" align="left"    sortable="true">ロック状態</th>
						<th field="branchUserName"   width="375" align="left"    sortable="true">拠点</th>
						<th field="sectionUserName"  width="375" align="left"    sortable="true">所属店部課</th>
					</tr>
				</thead>
			</table>
			<div id="toolbarDel">
				<a id="deleteAuthBtn" href="javascript:void(0)" class="easyui-linkbutton custom-button" plain="true">管理権限を削除</a>
				<a id="unlockAccountBtn" href="javascript:void(0)" class="easyui-linkbutton custom-button" plain="true">アカウントロック解除</a>
			</div>
		</form>
	</div>
</div>
</body>
</html>