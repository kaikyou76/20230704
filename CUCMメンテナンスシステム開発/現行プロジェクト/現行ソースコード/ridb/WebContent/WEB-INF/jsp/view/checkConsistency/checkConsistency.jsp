<%@page contentType="text/html" pageEncoding="UTF-8" errorPage="/jsp/error" %>
<jsp:directive.include file="/WEB-INF/jsp/common/taglibs.jsp"/>
<%@ page import="jp.co.netmarks.common.*" %>
<!doctype html>
<html>
<head>
	<c:import url="/WEB-INF/jsp/common/htmlHeader.jsp"/>
	<script type="text/javascript" src="${ pageContext.request.contextPath }/js/jquery.gridutil.js"></script>
	<script type="text/javascript" src="${ pageContext.request.contextPath }/js/datagrid-groupview.js"></script>
	<script type="text/javascript">
	<!--
		/* Grid用 */
		var cucmGrid;
		var officeLinkGrid;
		/* 詳細確認用データ */
		var detailData;
		/* 詳細情報区分 */
		var telInfo             = "<%= Constants.INCONSISTENCY_TEL_INFO %>";
		var telLineInfo         = "<%= Constants.INCONSISTENCY_TEL_LINE_INFO %>";
		var lineInfo            = "<%= Constants.INCONSISTENCY_LINE_INFO %>";
		var officeLinkInfo      = "<%= Constants.INCONSISTENCY_OFFICE_LINK_INFO %>";
		/* 詳細情報区分ラベル */
		var telInfoLabel        = "<%= Constants.INCONSISTENCY_TEL_INFO_LABEL %>";
		var telLineInfoLabel    = "<%= Constants.INCONSISTENCY_TEL_LINE_INFO_LABEL %>";
		var lineInfoLabel       = "<%= Constants.INCONSISTENCY_LINE_INFO_LABEL %>";
		var officeLinkInfoLabel = "<%= Constants.INCONSISTENCY_OFFICE_LINK_INFO_LABEL %>";

		/**
		 * onload処理
		 */
		$(function() {

			/* 初期フォーカス */
			$("#" + "${ cucmGridForm.focus }").focus();

			/* Grid用ライブラリを設定 */
			cucmGrid = $("#cucmGrid").gridutil({
				'syncWidthPanel': '.gridPanel',
				'resizeHeight' : false
			});
			officeLinkGrid = $("#officeLinkGrid").gridutil({
				'syncWidthPanel': '.gridPanel',
				'resizeHeight' : false
			});

			/* 検索ボタン押下時イベントを設定 */
			$('#checkConsistencySearchForm').submit(search);
			$('#searchBtn').click(search);

			/* OKボタン押下時処理 */
			$('#cucmOKBtn').click(function(r){
				$('#checkDetailCucmDialog').dialog({closed: true});
			});
			$('#officeLinkOKBtn').click(function(r){
				$('#checkDetailOfficeLinkDialog').dialog({closed: true});
			});

			/* Grid:CUCM詳細確認ボタンイベント */
			$("#cucmShowDetailBtn").click(function() {

				/* 詳細確認対象を選択しているかチェックする */
				var row = $('#cucmGrid').datagrid('getSelected');
				if(!row) {
					$.messager.alert(t0001,getMessage(e0002,'詳細確認する電話機'));
					return false;
				}

				/* データ整形処理（CUCM詳細確認用） */
				changeDataCucmFormat(row);

				/* 詳細確認用Grid設定 */
				$("#checkCucmDetailGrid").datagrid({
					data:detailData,
					view:groupview,
					groupField:'div',
					groupFormatter:function(value,rows){
						switch (value){
						case telInfo    :return telInfoLabel;
						case telLineInfo:return telLineInfoLabel;
						case lineInfo   :return lineInfoLabel;
						default:;
						}
					}
				});

				/* ダイアログ表示処理 */
				$('#checkDetailCucmDialog').dialog('open').dialog('setTitle','CUCM詳細');
			});

			/* Grid:オフィスリンク詳細確認ボタンイベント */
			$("#officeLinkShowDetailBtn").click(function() {

				/* 詳細確認対象を選択しているかチェックする */
				var row = $('#officeLinkGrid').datagrid('getSelected');
				if(!row) {
					$.messager.alert(t0001,getMessage(e0002,'詳細確認するオフィスリンク'));
					return false;
				}

				/* データ整形処理（オフィスリンク詳細確認用） */
				changeDataOfficeLinkFormat(row);

				/* 詳細確認用Grid設定 */
				$("#checkOfficeLinkDetailGrid").datagrid({
					data:detailData,
					view:groupview,
					groupField:'div',
					groupFormatter:function(value,rows){
						switch (value){
						case officeLinkInfo    :return officeLinkInfoLabel;
						default:;
						}
					}
				});

				/* ダイアログ表示処理 */
				$('#checkDetailOfficeLinkDialog').dialog('open').dialog('setTitle','オフィスリンク詳細');
			});

			/* CUCM情報取込 */
			$('#fetchCucmBtn').click(function() {

				/* CHECKBOXチェック */
				if ($("#grid").datagrid('getChecked').length == 0) {
					$.messager.alert(t0001,getMessage(e0002,'更新対象データ'));
					return false;
				}

				/* 選択 */
				$.messager.confirm(t0003, getMessage(c0004,'CUCM情報取込'), function (r) {
					if (!r) return false;

					removeError('#gridForm');
					grid.update('checkConsistency/fetchCucmData', '#gridForm', function (data) {

						if (data["checkConsistencyListUpdateForm"] && data["checkConsistencyListUpdateForm"].message) {
							$.messager.alert(t0002,data["checkConsistencyListUpdateForm"].message);
							/* 再検索 */
							$("#grid").datagrid('reload');
						}
					}, false, function (data) {
						/* エラー処理 */
						if (data['errors']) {
							$.each(data['errors'], function () {
								var indexes = [this.field.lastIndexOf('['), this.field.lastIndexOf(']')];
								var formName = this.field.slice(0, indexes[0]);
								var index = this.field.slice(indexes[0] + 1, indexes[1]);

								var message = this.defaultMessage;
								$("#" + formName + " .row").eq(index).parents("tr")
								.addClass('validateColor-invalid')
								.hover(
									function () {
										$('#message-error').show().find("span").html(message);
									},
									function () {
										$('#message-error').hide();
									}
								);
							});
						}
					});
				});
			});

			// データ検索実施
			search();
		});

		/**
		 * 検索処理
		 */
		function search() {

			/* ページを1行目にする */
			$("#cucmGrid").datagrid('options').pageNumber = 1;
			$("#officeLinkGrid").datagrid('options').pageNumber = 1;

			/* 検索 */
			cucmGrid.search('checkConsistency/searchInconsistancyCucm', '#cucmGridForm');
			/* officeLinkGrid.search('checkConsistency/searchInconsistancyOfficeLink', '#officeLinkGridForm'); */
			officeLinkGrid.search('checkConsistency/searchInconsistancyCucm', '#officeLinkGridForm');
			return false;
		}

		/**
		 * 入力エラーにてエラー属性を設定した項目を初期化します。
		 */
		function removeError(formName){
			/* 画面を通常に戻す */
			$(formName).find(".datagrid-row").each(function(i){
				$(this).removeClass("validateColor-invalid")
					   .unbind("mouseenter").unbind("mouseleave");
			});
		}

		/**
		 * データ整形処理（CUCM詳細確認用）
		 */
		 function changeDataCucmFormat(row) {
			 detailData = [
					  /** {"div":telInfo    , "subject":"Description"               , "app":row.appDescription        , "cucm":row.cucmDescription} */
					  {"div":telInfo    , "subject":"Calling Search Space Name"   , "app":row.appCssName            , "cucm":row.cucmCssName}
					 ,{"div":telInfo    , "subject":"Location Name"               , "app":row.appLocationName       , "cucm":row.cucmLocationName}
					 ,{"div":telInfo    , "subject":"Device Pool Name"            , "app":row.appDevicePoolName     , "cucm":row.cucmDevicePoolName}
					 ,{"div":telInfo    , "subject":"Phone Templete Name"         , "app":row.appPhoneButtonName    , "cucm":row.cucmPhoneButtonName}
					 ,{"div":telInfo    , "subject":"Addon Module Name 1"         , "app":row.appAddonModule1       , "cucm":row.cucmAddonModule1}
					 ,{"div":telInfo    , "subject":"Addon Module Name 2"         , "app":row.appAddonModule2       , "cucm":row.cucmAddonModule2}
					 ,{"div":telLineInfo, "subject":"No"                          , "app":row.appIndex              , "cucm":row.cucmIndex}
					 ,{"div":telLineInfo, "subject":"External Phone Number Mask"  , "app":row.appExternalPhoneNumber, "cucm":row.cucmExternalPhoneNumber}
					 ,{"div":telLineInfo, "subject":"Line Text Label"             , "app":row.appLineTextLabel      , "cucm":row.cucmLineTextLabel}
					 ,{"div":telLineInfo, "subject":"Ring Setting Name"           , "app":row.appRingSetting        , "cucm":row.cucmRingSetting}
					 ,{"div":lineInfo   , "subject":"Fwd ALL CSS"                 , "app":row.appFwdAllCss          , "cucm":row.cucmFwdAllCss}
					 ,{"div":lineInfo   , "subject":"Fwd Busy Destination"        , "app":row.appFwdBusyDestination , "cucm":row.cucmFwdBusyDestination}
					 ,{"div":lineInfo   , "subject":"Fwd Busy CSS"                , "app":row.appFwdBusyCss         , "cucm":row.cucmFwdBusyCss}
					 ,{"div":lineInfo   , "subject":"Fwd Noans Destination"       , "app":row.appFwdNoansDestination, "cucm":row.cucmFwdNoansDestination}
					 ,{"div":lineInfo   , "subject":"Fwd Noans CSS"               , "app":row.appFwdNoansCss        , "cucm":row.cucmFwdNoansCss}
					 ,{"div":lineInfo   , "subject":"Call Pickup Group Name"      , "app":row.appPickupGroup        , "cucm":row.cucmPickupGroup}
					 ,{"div":lineInfo   , "subject":"Voice Mail Profile"          , "app":row.appUseVmFlg           , "cucm":row.cucmUseVmFlg}
			 ];
		}

		/**
		 * データ整形処理（オフィスリンク詳細確認用）
		 */
		 function changeDataOfficeLinkFormat(row) {
			 /**
			 detailData = [
					  {"div":officeLinkInfo    , "subject":"FOMA Number"          , "app":row.appFomaNumber        , "officeLink":row.officeLinkFomaNumber}
					 ,{"div":officeLinkInfo    , "subject":"SIP ID"               , "app":row.appSIPID             , "officeLink":row.officeSIPID}
					 ,{"div":officeLinkInfo    , "subject":"SIP Password"         , "app":row.appSIPPassword       , "officeLink":row.officeSIPPassword}
					 ,{"div":officeLinkInfo    , "subject":"Web Cuscom User Name" , "app":row.appWebCuscomUserName , "officeLink":row.officeLinkWebCuscomUserName}
					 ,{"div":officeLinkInfo    , "subject":"Web Cuscom Password"  , "app":row.appWebCuscomPassword , "officeLink":row.officeLinkWebCuscomPassword}
			 ];
			 */
			 detailData = [
				  {"div":officeLinkInfo    , "subject":"FOMA Number"          , "app":row.appDescription    , "officeLink":row.cucmDescription}
				 ,{"div":officeLinkInfo    , "subject":"SIP ID"               , "app":row.appDescription    , "officeLink":row.cucmDescription}
				 ,{"div":officeLinkInfo    , "subject":"SIP Password"         , "app":row.appDescription    , "officeLink":row.cucmDescription}
				 ,{"div":officeLinkInfo    , "subject":"Web Cuscom User Name" , "app":row.appDevicePoolName , "officeLink":row.cucmDevicePoolName}
				 ,{"div":officeLinkInfo    , "subject":"Web Cuscom Password"  , "app":row.appDevicePoolName , "officeLink":row.cucmDevicePoolName}
		 ];
		}



		/** ################### cell styler #################### **/
		/**
		 * エラー用表示切替（連携アプリ：MACアドレス）
		 */
		function changeErrorAppTel(value,row,index) {
			switch (row.inconsistencyDiv) {
				case '0':return 'color:#FF3300';
				case '1':return '';
				case '2':return 'color:#FF3300';
				case '3':return 'color:#FF3300';
				case '4':return '';
				default:return '';
			}
		}
		/**
		 * エラー用表示切替（連携アプリ：内線番号）
		 */
		function changeErrorAppLine(value,row,index) {
			switch (row.inconsistencyDiv) {
				case '0':return 'color:#FF3300';
				case '1':return '';
				case '2':return 'color:#FF3300';
				case '3':return '';
				case '4':return 'color:#FF3300';
				default:return '';
			}
		}
		/**
		 * エラー用表示切替（CUCM：MACアドレス）
		 */
		function changeErrorCucmTel(value,row,index) {
			switch (row.inconsistencyDiv) {
				case '0':return '';
				case '1':return 'color:#FF3300';
				case '2':return 'color:#FF3300';
				case '3':return 'color:#FF3300';
				case '4':return '';
				default:return '';
			}
		}
		/**
		 * エラー用表示切替（CUCM：内線番号）
		 */
		function changeErrorCucmLine(value,row,index) {
			switch (row.inconsistencyDiv) {
				case '0':return '';
				case '1':return 'color:#FF3300';
				case '2':return 'color:#FF3300';
				case '3':return '';
				case '4':return 'color:#FF3300';
				default:return '';
			}
		}
		/**
		 * 詳細確認カラム（項目）
		 */
		function subjectStyler(value,row,index) {
			return 'background-color:#D8D8D8;font-weight:bold;';
		}
		/**
		 * 詳細確認カラム（連携アプリ）
		 */
		function appStyler(value,row,index) {
			if(value != row.cucm){
				return 'background-color:#FA5858;';
			} else {
				return 'background-color:#BCF5A9;';
			}

		}
		/**
		 * 詳細確認カラム（CUCM）
		 */
		function cucmStyler(value,row,index) {
			if(value != row.app){
				return 'background-color:#FA5858;';
			}else {
				return 'background-color:#A9F5F2;';
			}
		}
		/**
		 * 詳細確認カラム（オフィスリンク）
		 */
		function officeLinkStyler(value,row,index) {
			if(value != row.app){
				return 'background-color:#FA5858;';
			}else {
				return 'background-color:#A9F5F2;';
			}
		}

		/**
		 * formatter:MACアドレス(連携アプリ)
		 * ※行ごとエラー表示するために使用
		 */
		function formatAppMacAddress(value,row,index){
			return "<label class='row'>" + row.appMacAddress + "</label>";
		}
	-->
	</script>
</head>
<body>
<%-- Header --%>
<c:import url="/WEB-INF/jsp/common/header.jsp">
	<c:param name="title" value="整合性チェック" />
	<c:param name="infoDisplay" value="true" />
</c:import>

<div id="contents" class="container" style="min-width:955px">
	<%--
	<div id="searchCondition" title="検索条件" iconCls="icon-search" class="easyui-panel" style="background:#fafafa;padding:5px;" collapsible="true">
		<form id="checkConsistencySearchForm">

			<input type="submit" style="top: -1000px; left: 0px; margin-left:-1000px;position: absolute;"/>
			<table class="narrow_table" >
				<colgroup>
					<col width="20">
					<col width="60" style="padding:0px;">	<!-- 1 -->
					<col width="105">	<!-- 2 -->
					<col width="100">	<!-- 3 -->
					<col width="695">	<!-- 4 -->
				</colgroup>
				<tr>
					<td></td>
					<td class="label">
						<label for="clusterId">クラスタ</label>
					</td>
					<td>
						<select id="clusterId" name="clusterId" class="cluster_select">
							<c:forEach var="cluster" items="${ checkConsistencySearchForm.clusterList }" varStatus="status">
								<option value="${ cluster.value }"><c:out value="${ cluster.label }"/></option>
							</c:forEach>
						</select>
					</td>
					<td>
						<a id="searchBtn" class="easyui-linkbutton custom-button" iconCls="icon-search" plain="true" >検索</a>
					</td>
					<td></td>

				</tr>
			</table>
		</form>
	</div>
	<div class="grid first" style="text-align:center;margin:10px 10px 5px 0px;min-width:955px;">
		<input type="submit" style="position: absolute; top: 0px; left: 0px; visibility: hidden;" />
	</div>
	<div class="grid first" style="text-align:right;margin:10px 10px 5px 0px;min-width:955px;">
		<span>CUCM情報取得日：${ checkConsistencySearchForm.cucmUpdateTime }</span>
	</div>
	--%>
	<div id="cucmList" class="easyui-panel gridPanel" title="CUCM整合性チェック一覧" collapsible="true" style="background:#fafafa;" >
		<form id="cucmGridForm" name="cucmGridForm">
		<input type="hidden" name="_RequestVerificationToken"  />
			<table id="cucmGrid" class="easyui-datagrid"  toolbar="#toolbarCucm"  collapsible="true"  fitColumns="true" pagination="true"
				   rownumbers="true"  pageSize="50" singleSelect="true" checkOnSelect="false" selectOnCheck="false">
				<thead>
					<tr>
						<th width="400" colspan="2">連携アプリ</th>
						<th width="400" colspan="2">CUCM</th>
					</tr>
					<tr>
						<th field="appMacAddress"       width="200" align="center" sortable="true" data-options="formatter:formatAppMacAddress" styler="changeErrorAppTel" >MACアドレス</th>
						<th field="appDirectoryNumber"  width="200" align="center" sortable="true" styler="changeErrorAppLine">内線番号</th>
						<th field="cucmMacAddress"      width="200" align="center" sortable="true" styler="changeErrorCucmTel">MACアドレス</th>
						<th field="cucmDirectoryNumber" width="200" align="center" sortable="true" styler="changeErrorCucmLine">内線番号</th>
					</tr>
				</thead>
			</table>
			<div id="toolbarCucm">
				<a id="cucmShowDetailBtn" href="javascript:void(0)" class="easyui-linkbutton custom-button" plain="true">詳細確認</a>
				<%--<a id="fetchCucmBtn" href="javascript:void(0)" class="easyui-linkbutton custom-button" plain="true">CUCM情報取込</a>--%>
				<div id="message-error"  class="errorMessage" style="display:inline;"><span style="margin-left:20px;"></span></div>
			</div>
		</form>
	</div>
	<div class="panel-bottom"></div>
	<div id="officeLinkList" class="easyui-panel gridPanel" title="オフィスリンク整合性チェック一覧" collapsible="true" style="background:#fafafa;" >
		<form id="officeLinkGridForm" name="officeLinkGridForm">
			<table id="officeLinkGrid" class="easyui-datagrid"  toolbar="#toolbarOfficeLink"  collapsible="true"  fitColumns="true" pagination="true"
				   rownumbers="true"  pageSize="50" singleSelect="true" checkOnSelect="false" selectOnCheck="false">
				<thead>
					<tr>
						<th width="400">連携アプリ</th>
						<th width="400">オフィスリンク</th>
					</tr>
					<tr>
						<th field="appDirectoryNumber"  width="400" align="center" sortable="true" styler="changeErrorAppLine">内線番号</th>
						<th field="cucmDirectoryNumber" width="400" align="center" sortable="true" styler="changeErrorCucmLine">内線番号</th>
					</tr>
				</thead>
			</table>
			<div id="toolbarOfficeLink">
				<a id="officeLinkShowDetailBtn" href="javascript:void(0)" class="easyui-linkbutton custom-button" plain="true">詳細確認</a>
				<div id="message-error"  class="errorMessage" style="display:inline;"><span style="margin-left:20px;"></span></div>
			</div>
		</form>
	</div>
	<br>
</div>
<%-- dialog --%>
<c:import url="/WEB-INF/jsp/dialog/checkConsistencyCucmDetail.jsp"/>
<c:import url="/WEB-INF/jsp/dialog/checkConsistencyOfficeLinkDetail.jsp"/>
</body>
</html>
