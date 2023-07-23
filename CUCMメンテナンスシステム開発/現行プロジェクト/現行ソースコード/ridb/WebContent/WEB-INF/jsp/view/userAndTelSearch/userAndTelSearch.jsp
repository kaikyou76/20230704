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

		/* ボイスメールフラグ */
		var VOICE_MAIL_FLG_ON     =  "<%= Constants.VOICE_MAIL_FLG_ON %>";
		var VOICE_MAIL_FLG_OFF    =  "<%= Constants.VOICE_MAIL_FLG_OFF %>";
		/* TEL_DIR_DATA */
		var TEL_DIR_DATA_ON       =  "<%= Constants.TEL_DIR_DATA_ON %>";
		/* プルダウンラベル用 */
		var ALL_LABEL             = "<%= Constants.ALL_LABEL %>";
		/* 電話も出る */
		var MODULE_TEL_TYPE_MODEL = "<%= Constants.MODULE_TEL_TYPE_MODEL %>";
		/* ライン番号：１ */
		var LINE_INDEX_MAIN       = "<%= Constants.LINE_INDEX_MAIN %>";
		/* 共用電話 */
		var ENABLED_SHARED_USE_PRIVATE = "<%= Constants.ENABLED_SHARED_USE_PRIVATE %>";	// 個人電話
		var ENABLED_SHARED_USE_SHARE   = "<%= Constants.ENABLED_SHARED_USE_SHARE %>";		// 共用電話

		/* JSONデータ（プルダウン） */
		var dynamicSectionTel;			// 店部課リスト（動的）
		var dynamicPickupGroup;			// ピックアップグループ
		var dynamicCss;					// CSS
		var dynamicPhoneButtonTemplete;	// 電話テンプレート
		var dynamicAddonModule;			// 拡張モジュール
		var dynamicParentSection = ${ userAndTelSearchForm.parentSectionListJson };	// 親店部課

		/* グリッド */
		var grid;
		
		/* 画面描画が終わってからリサイズする */
		window.onload = function(){
			/* Grid用ライブラリを設定 */
			grid = $("#grid").gridutil({
					  'syncWidthPanel': '#searchCondition'
					<sec:authorize access="hasRole('ROLE_ADMIN')">
						, 'editables'     : {
							/*'phoneClass'                       : '#gridForm select[name=phoneClass]',						// 電話機区分*/
							'dialinNumber'                     : '#gridForm input[name=dialinNumber]',						// ダイアルイン
							'pickupGroupName'                  : '#gridForm select[name=pickupGroupName]',					// ピックアップグループ
							'voiceMailFlg'                     : '#gridForm select[name=voiceMailProfile]',					// VMプロファイル
							'busyDestination'                  : '#gridForm input[name=busyDestination]',					// 話中転送先
							'callingSearchSpaceName'           : '#gridForm select[name=callingSearchSpaceName]',			// callingSearchSpaceName
							'chargeAssociationBranchId'        : '#gridForm input[name=chargeAssociationBranchId]',			// 課金先（拠点）
							'chargeAssociationParentSectionId' : '#gridForm input[name=chargeAssociationParentSectionId]',	// 課金先（親店部課）
							'chargeAssociationSectionId'       : '#gridForm input[name=chargeAssociationSectionId]',		// 課金先（子店部課
							'chargeRemarks'                    : '#gridForm input[name=chargeRemarks]',						// 備考（電話機）
							'loggerData'                       : '#gridForm select[name=loggerData]',						// 通話録音
							/* 'telDirData'                       : '#gridForm input[name=telDirData]',						// 電話帳 */
							'branchTelId'                      : '#gridForm select[name=branchTelId]',						// 拠点（電話機）
							'companySectionTelId'              : '#gridForm select[name=companySectionTelId]',				// 店部課（電話機）
							'telLineRemarks'                   : '#gridForm input[name=telLineRemarks]',					// 備考（電話機/内線番号）
							'lineTextLabel'                    : '#gridForm input[name=lineTextLabel]',						// lineTextLabel
							'noansDestination'                 : '#gridForm input[name=noansDestination]',					// 不応答転送
							'externalPhoneNumberMask'          : '#gridForm input[name=externalPhoneNumberMask]',			// ExternalPhoneNumber
							'phoneButtonTemplete'              : '#gridForm select[name=phoneButtonTemplete]',				// 電話機種
							'addonModuleName1'                 : '#gridForm select[name=addonModuleName1]',					// 設定モジュール１
							'addonModuleName2'                 : '#gridForm select[name=addonModuleName2]',					// 設定モジュール２
							'ringSettingName'                  : '#gridForm select[name=ringSettingName]',					// 鳴動設定
							'telRemarks'                       : '#gridForm input[name=telRemarks]',						// 備考（電話機の設置場所）
							'transformationMask'               : '#gridForm input[name=transformationMask]',				// 発呼側トランスフォーメーションマスク
							'gwRepletionSpecialNo'             : '#gridForm input[name=gwRepletionSpecialNo]'				// GW補足特番
						}
					</sec:authorize>
			});
		}

		/**
		 * onload処理
		 */
		$(function() {
			
			/* フォーカスセット */
			$("#branchUserId").focus();
			
			/* 検索ボタン押下時イベントを設定 */
			$('#userAndTelSearchForm').submit(search);
			$('#searchBtn').click(search);

			/* 拠点プルダウンイベント */
			$('#branchUserId').change(getSectionUserList);
			$('#branchTelId').change(getSectionTelList);

			/* 店部課（電話機）プルダウンイベント */
			$('#sectionTelId').change(getPickupGroupList);

			/* 電話機プルダウンイベント */
			$('#telTypeModelId').change(getPhoneButtonTempleteList);

			/* 個別反映 */
			$('#separatelyReflectionBtnId').click(function() {

				/* 共通チェック */
				if(!checkGridUpdEvent("grid"))return false;

				/* CHECKBOXチェック */	
				if ($("#grid").datagrid('getChecked').length == 0) {
					$.messager.alert(t0001 ,getMessage(e0002,'更新対象データ'));
					return false;
				}

				/* 選択 */
				$.messager.confirm(t0003, getMessage(c0004,'個別反映'), function (r) {
					if (!r) return false;
					grid.update('userAndTel/separatelyReflection', '#gridForm', function (data) {
						if (data["userAndTelListUpdateForm"] && data["userAndTelListUpdateForm"].message) {
							$.messager.alert(t0002 ,data["userAndTelListUpdateForm"].message);
						}
					});
				});
			});

			/* 更新（翌日反映） */
			$('#nextDayReflectionBtnId').click(function() {
				/* 共通チェック */
				if(!checkGridUpdEvent("grid"))return false;

				/* 選択 */
				$.messager.confirm(t0003, getMessage(c0004,'更新（翌日反映）'), function (r) {
					if (!r) return false;
					$("#grid").datagrid('checkAll');
					grid.update('userAndTel/nextDayReflection', '#gridForm', function (data) {
						if (data["userAndTelListUpdateForm"] && data["userAndTelListUpdateForm"].message) {
							$.messager.alert(t0002 ,data["userAndTelListUpdateForm"].message);
						}
					}, true , function(data){
						$("#grid").datagrid('uncheckAll');
					});
				});
			});

			/* 話中転送先確認 */
			$('#busyDestinationBtnId').click(function(){

				/* 共通チェック */
				if(!checkGridUpdEvent("grid"))return false;

				/* 選択 */
				$.messager.confirm(t0003, getMessage(c0004,'話中転送先確認'), function (r) {
					if (!r) return false;
					$("#grid").datagrid('checkAll');
					grid.update('userAndTel/busyDestinationCheck', '#gridForm', busyDesMessage, false, busyDesMessage);
				});
			});
			
			/* 検索結果をエクスポート */
			$("#tableDataExportBtnId").click(function(){
				/* 共通チェック */
				if(!checkGridUpdEvent("grid"))return false;
				/* 選択 */
				$.messager.confirm(t0003, getMessage(c0004,'検索結果をエクスポート'), function (r) {
					if (!r) return false;
					/* loading画像表示 */
					$("#grid").datagrid('loading');

					$.ajax({
						type : "POST",
						url : "userAndTel/tableDataExportCsv",
						data : $('#grid').datagrid('getData').userAndTelSearchForm,
						success : function (data, status, xhr) {
							/* loading画像非表示 */
							$("#grid").datagrid('loaded');
							$.messager.alert(t0002 ,i0004);
						},
						error : function (xhr, status) {
	
							/* エラー処理 */
							ajaxError(xhr,status);
						}
					});
				});
			});

			/* 一覧をエクスポート */
			$("#searchResultExportBtnId").click(function(){

				/* 共通チェック */
				if(!checkGridUpdEvent("grid"))return false;
				$.messager.confirm(t0003, getMessage(c0004,'検索結果をエクスポート'), function (r) {
					if (!r) return false;
					/* loading画像表示 */
					$("#grid").datagrid('loading');
					$.ajax({
						type : "POST",
						url : "userAndTel/searchResultExportCsv",
						data : $('#grid').datagrid('getData').userAndTelSearchForm,
						success : function (data, status, xhr) {
							/* loading画像非表示 */
							$("#grid").datagrid('loaded');
							$.messager.alert(t0002 ,i0004);
						},
						error : function (xhr, status) {
							/* エラー処理 */
							ajaxError(xhr,status);
						}
					});
				});
			});

			/* 個別参照 */
			$("#separatelyReferenceBtn").click(function() {

				/* 共通チェック */
				if(!checkGridUpdEvent("grid"))return false;

				/* 追加対象を選択しているかチェックする */
				var row = $('#grid').datagrid('getSelected');
				if(!row) {
					$.messager.alert(t0001,getMessage(e0002,'設定する電話機'));
					return false;
				} else if(!row.telId){
					$.messager.alert(t0001,getMessage(e0003,'電話機'));
					return false;
				}

				/* ダイアログ表示処理 */
				$('#dialog').dialog({
					title  : '個別参照',
					width  : 280,
					height : 150,
					closed : false,
					cache  : false,
					modal  : true,
					href   : 'separatelyReference/index?directoryNumber=' + encodeURIComponent(row.directoryNumber) + "&clusterId=" + row.clusterId
				});

//				$('#dialog').dialog('refresh', 'separatelyReference/index?directoryNumber=' + encodeURIComponent(row.directoryNumber) + "&clusterId=" + row.clusterId);
			});


			/* ユーザー追加ボタンイベント */
			$("#addUserBtn").click(function() {

				/* 共通チェック */
				if(!checkGridUpdEvent("grid"))return false;

				/* 追加対象を選択しているかチェックする */
				var row = $('#grid').datagrid('getSelected');
				if(!row) {
					$.messager.alert(t0001,getMessage(e0002,'設定する電話機'));
					return false;
				} else if(!row.telId){
					$.messager.alert(t0001,getMessage(e0003,'電話機'));
					return false;
				} else if(row.sharedUse == ENABLED_SHARED_USE_SHARE){
					$.messager.alert(t0001,getMessage(e0009,new Array('共用電話機','ユーザー追加')));
					return false;
				}

				/* ダイアログ表示処理 */
				$('#dialog').dialog({
				    title  : 'ユーザーの検索と選択',
				    width  : 950,
				    height : 570,
				    closed : false,
				    cache  : false,
				    modal  : true,
				    href   : 'addUser/index'
				});
				
//				$('#dialog').dialog('refresh', 'addUser/index');
			});

			/* 電話機追加ボタンイベント */
			$("#addTelBtn").click(function() {

				/* 共通チェック */
				if(!checkGridUpdEvent("grid"))return false;

				/* 追加対象を選択しているかチェックする */
				var row = $('#grid').datagrid('getSelected');
				if(!row) {
					$.messager.alert(t0001,getMessage(e0002,'設定するユーザー'));
					return false;
				} else if(!row.userId){
					$.messager.alert(t0001,getMessage(e0003,'ユーザー'));
					return false;
				} else if(row.sharedUse == ENABLED_SHARED_USE_SHARE){
					$.messager.alert(t0001,getMessage(e0009,new Array('共用電話機','電話機追加')));
					return false;
				}

				/* ダイアログ表示処理 */
				$('#dialog').dialog({
				    title  : '電話機の検索と選択',
				    width  : 950,
				    height : 570,
				    closed : false,
				    cache  : false,
				    modal  : true,
				    href   : 'addTel/index'
				});

//				$('#dialog').dialog('refresh', 'addTel/index');
			});

			/* ライン追加ボタンイベント */
			$("#addLineBtn").click(function() {

				/* 共通チェック */
				if(!checkGridUpdEvent("grid"))return false;

				/* 追加対象を選択しているかチェックする */
				var row = $('#grid').datagrid('getSelected');
				if(!row) {
					$.messager.alert(t0001,getMessage(e0002,'設定する電話機'));
					return false;
				} else if(!row.telId){
					$.messager.alert(t0001,getMessage(e0003,'電話機'));
					return false;
				}

				/* ダイアログ表示処理 */
				$('#dialog').dialog({
				    title  : 'ラインの検索と選択',
				    width  : 950,
				    height : 570,
				    closed : false,
				    cache  : false,
				    modal  : true,
				    href   : 'addLine/index'
				});
//				$('#dialog').dialog('refresh', 'addLine/index');
			});

			/* 話中転送先ボタンイベント */
			$("#busyDestinationSettiogBtn").click(function(){

				/* 共通チェック */
				if(!checkGridUpdEvent("grid"))return false;

				/* 追加対象を選択しているかチェックする */
				var row = $('#grid').datagrid('getSelected');
				if(!row) {
					$.messager.alert(t0001,getMessage(e0002,'設定する電話機'));
					return false;
				} else if(!row.telId){
					$.messager.alert(t0001,getMessage(e0003,'電話機'));
					return false;
				}

				/* ダイアログ表示処理 */
				$('#dialog').dialog({
				    title  : '話中転送先の設定',
				    width  : 850,
				    height : 815,
				    closed : false,
				    cache  : false,
				    modal  : true,
				    href   : 'busyDestination/index?directoryNumber=' + encodeURIComponent(row.directoryNumber)
				});

//				$('#dialog').dialog('refresh', 'busyDestination/index?directoryNumber=' + encodeURIComponent(row.directoryNumber));

			});

			/* 返却ボタンイベント */
			$("#returnTelBtn").click(function() {

				/* 共通チェック */
				if(!checkGridUpdEvent("grid"))return false;

				/* 追加対象を選択しているかチェックする */
				var row = $('#grid').datagrid('getSelected');
				if(!row) {
					$.messager.alert(t0001,getMessage(e0002,'設定する電話機'));
					return false;
				} else if(!row.telId){
					$.messager.alert(t0001,getMessage(e0003,'電話機'));
					return false;
				}

				/* 選択 */
				$.messager.confirm(t0003, getMessage(c0004,'返却'), function (r) {
					if (!r) return false;
					/* チェックボックスにチェック */
					checkSelctRow();

					/* 更新処理 */
					grid.update('userAndTel/returnTelBtn', '#gridForm', function (data) {
						if (data["userAndTelListUpdateForm"] && data["userAndTelListUpdateForm"].message) {
							$.messager.alert(t0001,data["userAndTelListUpdateForm"].message);
						}
					}, true , function(data){
						$("#grid").datagrid('uncheckAll');
					});
				});
			});

			/* 電話機削除ボタン押下時処理 */
			$("#deleteTelBtn").click(function() {

				/* 共通チェック */
				if(!checkGridUpdEvent("grid"))return false;

				/* 追加対象を選択しているかチェックする */
				var row = $('#grid').datagrid('getSelected');
				if(!row) {
					$.messager.alert(t0001,getMessage(e0002,'設定する電話機'));
					return false;
				} else if(row.userId){
					$.messager.alert(t0001,getMessage(e0005,'ユーザー'));
					return false;
				}

				/* 選択 */
				$.messager.confirm(t0003, getMessage(c0004,'電話機削除'), function (r) {
					if (!r) return false;
					/* チェックボックスにチェック */
					checkSelctRow();

					/* 更新処理 */
					grid.update('userAndTel/deleteTel', '#gridForm', function (data) {
						if (data["userAndTelListUpdateForm"] && data["userAndTelListUpdateForm"].message) {
							$.messager.alert(t0001,data["userAndTelListUpdateForm"].message);
						}
					}, true , function(data){
						$("#grid").datagrid('uncheckAll');
					});
				});
			});

			/* ライン削除ボタンイベント */
			$("#deleteLineBtn").click(function() {

				/* 共通チェック */
				if(!checkGridUpdEvent("grid"))return false;

				/* 追加対象を選択しているかチェックする */
				var row = $('#grid').datagrid('getSelected');
				if(!row) {
					$.messager.alert(t0001,getMessage(e0002,'設定する電話機'));
					return false;
				} else if(!row.telId){
					$.messager.alert(t0001,getMessage(e0003,'電話機'));
					return false;
				}

				/* 選択 */
				$.messager.confirm(t0003, getMessage(c0004,'ライン削除'), function (r) {
					if (!r) return false;
					/* チェックボックスにチェック */
					checkSelctRow();

					/* 更新処理 */
					grid.update('userAndTel/deleteLine', '#gridForm', function (data) {
						if (data["userAndTelListUpdateForm"] && data["userAndTelListUpdateForm"].message) {
							$.messager.alert(t0002,data["userAndTelListUpdateForm"].message);
						}
					}, true , function(data){
						$("#grid").datagrid('uncheckAll');
					});
				});
			});

			/* 部内在庫ボタンイベント */
			$("#insideStockBtn").click(function() {

				/* 共通チェック */
				if(!checkGridUpdEvent("grid"))return false;

				/* 追加対象を選択しているかチェックする */
				var row = $('#grid').datagrid('getSelected');
				if(!row) {
					$.messager.alert(t0001,getMessage(e0002,'設定する電話機'));
					return false;
				} else if(!row.telId){
					$.messager.alert(t0001,getMessage(e0003,'電話機'));
					return false;
				} else if(!row.userId){
					$.messager.alert(t0001,getMessage(e0003,'ユーザー'));
					return false;
				}

				/* 選択 */
				$.messager.confirm(t0003, getMessage(c0004,'部内在庫'), function (r) {
					if (!r) return false;
					/* チェックボックスにチェック */
					checkSelctRow();

					/* 更新処理 */
					grid.update('userAndTel/insideStock', '#gridForm', function (data) {
						if (data["userAndTelListUpdateForm"] && data["userAndTelListUpdateForm"].message) {
							$.messager.alert(t0002,data["userAndTelListUpdateForm"].message);
						}
					}, true , function(data){
						$("#grid").datagrid('uncheckAll');
					});
				});
			});

			/* 共用電話化ボタンイベント */
			$("#shareTelBtn").click(function(){
				/* 共通チェック */
				if(!checkGridUpdEvent("grid"))return false;

				/* 追加対象を選択しているかチェックする */
				var row = $('#grid').datagrid('getSelected');
				if(!row) {
					$.messager.alert(t0001,getMessage(e0002,'設定する電話機'));
					return false;
				} else if(!row.telId){
					$.messager.alert(t0001,getMessage(e0003,'電話機'));
					return false;
				} else if(row.sharedUse == ENABLED_SHARED_USE_SHARE){
					$.messager.alert(t0001,getMessage(e0008,'共用電話'));
					return false;
				}

	/* ダイアログ表示処理 */
				$('#dialog').dialog({
				    title  : '電話機の共用化',
				    width  : 450,
				    height : 230,
				    closed : false,
				    cache  : false,
				    modal  : true,
				    href   : 'sharedTel/index'
				});
//				$('#dialog').dialog('refresh', 'sharedTel/index');
			});

			/* 共用名変更ボタンイベント */
			$("#shareTelNameUpdateBtn").click(function(){
				/* 共通チェック */
				if(!checkGridUpdEvent("grid"))return false;

				/* 追加対象を選択しているかチェックする */
				var row = $('#grid').datagrid('getSelected');
				if(!row) {
					$.messager.alert(t0001,getMessage(e0002,'設定する電話機'));
					return false;
				} else if(!row.telId){
					$.messager.alert(t0001,getMessage(e0003,'電話機'));
					return false;
				} else if(!row.sharedUse || row.sharedUse == ENABLED_SHARED_USE_PRIVATE){
					$.messager.alert(t0001,getMessage(e0002,'共用電話'));
					return false;
				}

				/* ダイアログ表示処理 */
				$('#dialog').dialog({
				    title  : '共用名の変更',
				    width  : 450,
				    height : 220,
				    closed : false,
				    cache  : false,
				    modal  : true,
				    href   : 'sharedTel/index'
				});
//				$('#dialog').dialog('refresh', 'sharedTel/index');
			});

			/* 即時反映ボタンイベント */
			$("#cucmReflectionBtn").click(function() {

				/* 選択 */
				$.messager.confirm(t0003, getMessage(c0004,'即時反映'), function (r) {
					if (!r) return false;

					/* 更新処理 */
					grid.update('userAndTel/cucmReflection', '#gridForm', function (data) {
							$.messager.alert(t0002,data["message"]);
					});
				});
			});
			
		});

		/**
		 * 検索処理
		 */
		function search() {

			/* ページを1行目にする */
			$("#grid").datagrid('options').pageNumber = 1;

			/* 検索 */
			grid.search('userAndTel/search', '#userAndTelSearchForm');
			return false;
		}
		
		/* 
		 * 選択行のチェックボックスにチェックを設定する 
		 */
		function checkSelctRow(){
			/* チェックを全て消す */
			$("#grid").datagrid('uncheckAll');
			/* 選択行にチェックする */
			$("#grid").datagrid('checkRow',grid.getRowIndex());
		}
		
		/**
		 * 話中転送確認用：メッセージ出力
		 */
		function busyDesMessage(data) {
			$.messager.alert(t0003,data["userAndTelListUpdateForm"].message);
			
			/* チェックを全て消す */
			$("#grid").datagrid('uncheckAll');
		}

		/**
		 * 拠点（ユーザー）選択時処理
		 */
		function getSectionUserList() {
			/* 店部課（ユーザー）の変更 */
			getSectionList("#sectionUserId" ,"#branchUserId");
		}

		/**
		 * 拠点（電話機）選択時処理
		 */
		function getSectionTelList() {
			/* 店部課（電話機）の変更 */
			getSectionList("#sectionTelId", "#branchTelId");
			/* ピックアップグループの初期化 */
			changeSelector("#pickupGroupNameId", [{'label':ALL_LABEL,'value':''}]);
		}

		/**
		 * 拠点プルダウン変更処理
		 * 概要：拠点プルダウン変更時に、該当の店部課の選択値を変更します。
		 */
		function getSectionList(targetId, eventId){
			$.ajax({
				type : "POST",
				url : "ajax/getSectionList?id=" + $(eventId).val() + "&frontFlg=true",
				success : function (data, status, xhr) {
					 if (data["data"]) {
						 /* プルダウンの入れ替え */
						 changeSelector(targetId,data["data"]);

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
		 * ピックアップリストを取得
		 */
		function getPickupGroupList() {
			$.ajax({
				type : "POST",
				url : "ajax/getPickupGroupList?branchId=" + $("select[name='branchTelId']").val() +
					   "&sectionCompanyId=" + $("#sectionTelId").val() ,
				success : function (data, status, xhr) {
					 if (data["data"]) {

						 /* プルダウンの入れ替え */
						 changeSelector('#pickupGroupNameId',data["data"]);

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
		 * PhoneButtonTempleteリストを取得
		 */
		function getPhoneButtonTempleteList() {
			$.ajax({
				type : "POST",
				url : "ajax/getPhoneButtonTempleteList?name=" + $("#telTypeModelId").val() ,
				success : function (data, status, xhr) {
					 if (data["data"]) {

						 /* プルダウンの入れ替え */
						 changeSelector('#phoneButtonTempleteId',data["data"]);

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
		 * cell背景色の変更イベントを設定
		 */
		$.gridutil.cellStyle = function (value,row,index) {
			switch (row.telUsagesStatus) {
				case '<%= Constants.TEL_USAGES_DEFAULT %>'       : return 'background-color:rgb(255,255,127)';	// 黄
				case '<%= Constants.TEL_USAGES_COMMON_USER %>'   : return 'background-color:rgb(192,192,192)';	// 灰
				case '<%= Constants.TEL_USAGES_SHARED_LINE %>'   : return 'background-color:rgb(127,255,127)';	// 緑
				case '<%= Constants.TEL_USAGES_MULTI_DEVICE %>'  : return 'background-color:rgb(127,191,255)';	// 青
				case '<%= Constants.TEL_USAGES_MULTI_LINE %>'    : return 'background-color:rgb(255,207,167)';	// 茶
				case '<%= Constants.TEL_USAGES_SHARED_TEL %>'    : return 'background-color:rgb(127,255,255)';	// 水
				case '<%= Constants.TEL_USAGES_NOT_LINK_TEL %>'  : return 'background-color:rgb(213,182,255)';	// 紫
				case '<%= Constants.TEL_USAGES_NOT_LINK_USER %>' : return 'background-color:rgb(255,182,255)';	// 桃
				case '<%= Constants.TEL_USAGES_MANY %>'          : return 'background-color:rgb(255, 255, 255)';// 白
				default                              : return 'background-color:rgb(255, 255, 221)';
			}
		}

		/**
		 * formatter:電話機区分 TODO:削除予定
		 */
		function formatPhoneClass(value,row,index){
			var disFlg = false;
			var jsonList = [
					{'label':'IP電話','value':'1'},
					{'label':'FMC端末','value':'2'},
					{'label':'固定併用','value':'3'},
				];

			/* 電話機と紐付いていない場合 */
			if(!row.telId || row.telId == 0 || !adminBoolean()){
				disFlg = true;
				jsonList = [{'label':ALL_LABEL,'value':''}];
			}
			
			return $.gridutil.formatSelectTag(jsonList, { name :'phoneClass' , 'class' : 'phone_class_select' , disabled : disFlg } , value);
		}

		/**
		 * formatter:ダイアルイン
		 */
		function formatDialinNumber(value,row,index){
			var disFlg = false;
			if(!row.telId || row.telId == 0 || !adminBoolean())disFlg = true;

			return $.gridutil.formatInput({ name :'dialinNumber' , value : value , 'class' : 'dialin_numberr_text' , maxlength : '24' , disabled : disFlg});
		}
		
		/**
		 * formatter:話中転送先
		 */
		function formatBusyDestination(value,row,index){
			var disFlg = false;
			if(!row.telId || row.telId == 0 || !adminBoolean()) disFlg = true;
			return $.gridutil.formatInput({ name :'busyDestination' , value : value , 'class' : 'busy_destination_text' , maxlength : '8'  , disabled : disFlg} );
		}

/**
		 * formatter:課金先
		 */
		function formatCharge(value,row,index){
			var disFlg = false;
			if(!row.telId || row.telId == 0 || !adminBoolean()) disFlg = true;
			 var tag =   $.gridutil.formatInput({ name  : 'chargeAssociationBranchId'       , 'class' : 'charge_association_branch_grid_text',
				 								  value : row.chargeAssociationBranchId ,        maxlength : '3' , disabled : disFlg})
					   + $.gridutil.formatInput({ name  : 'chargeAssociationParentSectionId', 'class' : 'charge_association_parent_section_grid_text' ,
						   						  value : row.chargeAssociationParentSectionId , maxlength : '5' , disabled : disFlg})
					   + $.gridutil.formatInput({ name  : 'chargeAssociationSectionId'      , 'class' : 'charge_association_section_grid_text' ,
						   						  value : row.chargeAssociationSectionId ,       maxlength : '5' , disabled : disFlg});

			return  tag;
		}

		/**
		 * formatter:備考（課金先）テキスト
		 */
		function formatChargeRemarks(value,row,index){
			var disFlg = false;

			if(!row.telId || row.telId == 0 || !adminBoolean())disFlg = true;
			return $.gridutil.formatInput({ name : 'chargeRemarks' , 'class' : 'charge_remarks_text' , value : value , maxlength : '256' ,disabled : disFlg, });
		}

		/**
		 * formatter:備考（電話機/内線電話）テキスト
		 */
		function formatTelLineRemarks(value,row,index){
			var disFlg = false;
			if(!row.telId || row.telId == 0 || !adminBoolean()) disFlg = true;
			return $.gridutil.formatInput({ name : 'telLineRemarks' , 'class' : 'tel_line_remarks_text' , value : value , maxlength : '256' ,disabled : disFlg});
		}

		/**
		 * formatter:LineTextLabelテキスト
		 */
		function formatLineTextLabel(value,row,index){
			var disFlg = false;
			if(!row.telId || row.telId == 0 || !adminBoolean()) disFlg = true;
			return $.gridutil.formatInput({ name : 'lineTextLabel' , 'class' : 'line_text_Label_text' , value : value , maxlength : '30' , disabled : disFlg});
		}

		/**
		 * formatter:不応答転送テキスト
		 */
		function formatNoansDestination(value,row,index){
			var disFlg = false;
			if(!row.telId || row.telId == 0 || !adminBoolean()) disFlg = true;
			return $.gridutil.formatInput({ name : 'noansDestination' , 'class' : 'noans_destination_text' , value : value , maxlength : '50' , disabled : disFlg});
		}

		/**
		 * formatter:ExternalPhoneNumberテキスト
		 */
		function formatExternalPhoneNumber(value,row,index){
			var disFlg = false;
			if(!row.telId || row.telId == 0 || !adminBoolean()) disFlg = true;
			return $.gridutil.formatInput({ name : 'externalPhoneNumberMask' , 'class' : 'external_phone_number_mask_text' , value : value , maxlength : '24' , disabled : disFlg});
		}

		/**
		 * formatter:vmProfileリスト
		 */
		function formatVoiceMailProfile(value,row,index){
			var disFlg = false;
			if(!row.telId || row.telId == 0  || !adminBoolean() || 
			   (row.lineIndex != LINE_INDEX_MAIN && (!row.orgVoiceMailFlg || row.orgVoiceMailFlg == VOICE_MAIL_FLG_OFF)) ) disFlg = true;

			/* return $.gridutil.formatCheckBox({name : 'voiceMailFlg' , value : value , disabled : disFlg}); */

			dynamicPhoneButtonTemplete = ${ userAndTelSearchForm.phoneButtonTempleteListJson };
			var jsonList = dynamicPhoneButtonTemplete[row.telTypeModel];
			if (jsonList == undefined) {
				 jsonList = [{'label':ALL_LABEL,'value':''}];
			}
			return $.gridutil.formatSelectTag(jsonList,
					{ name :'voiceMailProfile' , 'class' : 'voice_mail_profile_select' , disabled : disFlg} , value, row.telTypeModel);
		}

		/**
		 * formatter:電話帳チェックボックス
		 */
		function formatTelDirData(value,row,index){
			var disFlg = false;
			if(!row.telId || row.telId == 0 || !row.userId || row.userId == 0 || !adminBoolean()) disFlg = true;

			return $.gridutil.formatCheckBox({name : 'telDirData' , value : value , disabled : disFlg});
		}

		/**
		 * formatter:通話録音リスト
		 */
		function formatLoggerData(value,row,index){
			var disFlg = false;
			var jsonList = ${ userAndTelSearchForm.loggerDataListJson };

			/* 電話機と紐付いていない場合 */
			if(!row.telId || row.telId == 0 || !adminBoolean()){
				disFlg = true;
				jsonList = [{'label':ALL_LABEL,'value':''}];
			}

			return $.gridutil.formatSelectTag(jsonList,
					{ name :'loggerData' , 'class' : 'logger_data_select' , disabled : disFlg}, value);
		}

		/**
		 * formatter:拠点リスト
		 */
		function formaBranchTel(value,row, index){
			var disFlg = false;
			var dynamicKey;
			var jsonList = ${ userAndTelSearchForm.branchListJson };
			/* 電話機と紐付いていない場合 */
			if(!row.telId || row.telId == 0 || !adminBoolean()){
				disFlg = true;
				jsonList = [{'label':ALL_LABEL,'value':''}];
				dynamicKey = 'disabled';
			}

			return $.gridutil.formatSelectTag(jsonList,
					{ name :'branchTelId' , 'class' : 'branch_select' , disabled : disFlg} , value, dynamicKey);
		}

		/**
		 * formatter:店部課リスト（動的）
		 */
		function formatSectionTel(value, row, index) {
			var disFlg = false;
			if(!row.telId || row.telId == 0 || !adminBoolean()) disFlg = true;

			dynamicSectionTel = ${ userAndTelSearchForm.sectionListJson };
			var jsonList = dynamicSectionTel[row.branchTelId];
			if (jsonList == undefined) {
				jsonList = [{'label':ALL_LABEL,'value':''}];
			}
			return $.gridutil.formatSelectTag(jsonList,
					{ name :'companySectionTelId' , 'class' : 'section_select' , disabled : disFlg} , value, row.branchTelId);
		}

		/**
		 * formatter:ピックアップグループリスト（動的）
		 */
		function formatPickupGroup(value, row, index) {
			var dynamicKey;
			var disFlg = false;
			if(!row.telId || row.telId == 0 || !adminBoolean()) disFlg = true;

			dynamicPickupGroup = ${ userAndTelSearchForm.pickupGroupListJson };
			var sectionTelId = row.companySectionTelId.split(",");
			var jsonList = dynamicPickupGroup[dynamicKey];
			
			if (dynamicPickupGroup[row.branchTelId + "_" + sectionTelId[1]]  != undefined) {
				/* 選択した店部課と拠点の紐付きでピックアップグループが存在した場合 */
				dynamicKey = row.branchTelId + "_" + sectionTelId[1];
			} else {
				/* 選択した店部課と拠点の紐付きでピックアップグループが存在しなかった場合 */
				/* 選択した店部課の親店部課と拠点でピックアップグループを取得する */
				if(dynamicParentSection[row.companySectionTelId]){
					sectionTelId = dynamicParentSection[row.companySectionTelId].split(",");
					if(dynamicPickupGroup[row.branchTelId + "_" + sectionTelId[1]]  != undefined){
						/* 存在した場合 */
						dynamicKey = row.branchTelId + "_" + sectionTelId[1];
					}
				}
			}
			
			if (dynamicKey) {
				jsonList = dynamicPickupGroup[dynamicKey];
			} else {
		 		jsonList = [{'label':ALL_LABEL,'value':''}];
			}
			
			return $.gridutil.formatSelectTag(jsonList,
					{ name :'pickupGroupName' , 'class' : 'pickup_group_select' , disabled : disFlg} , value, dynamicKey);
		}

		/**
		 * formatter:コーリングサーチスペースリスト（動的）
		 */
		function formatCallingSearchSpace(value, row , index) {
			var disFlg = false;
			if(!row.telId || row.telId == 0 || !adminBoolean()) disFlg = true;

			dynamicCss = ${ userAndTelSearchForm.callingSearchSpaceListJson };

			var dynamicKey;
			if (dynamicCss[row.branchTelId + "_" + row.companySectionTelId] != undefined){
				/* 拠点＿店部課 */
				dynamicKey = row.branchTelId + "_" + row.companySectionTelId;
		 	} else if (dynamicCss[row.branchTelId + "_" + row.branchTelId] != undefined){
		 		/* 拠点＿拠点 */
		 		dynamicKey = row.branchTelId + "_" + row.branchTelId;
		 	}

			var jsonList = "";
			if (dynamicKey) {
				jsonList = dynamicCss[dynamicKey];
			} else {
		 		jsonList = [{'label':ALL_LABEL,'value':''}];
			}

			return $.gridutil.formatSelectTag(jsonList,
					{ name :'callingSearchSpaceName' , 'class' : 'calling_search_space_select' , disabled : disFlg} , value, dynamicKey);
		}

/**
		 * formatter:電話機リスト（動的）
		 */
		function formatPhoneButtonTemplete(value, row, index) {
			var disFlg = false;
			if(!row.telId || row.telId == 0 || !adminBoolean()) disFlg = true;

			dynamicPhoneButtonTemplete = ${ userAndTelSearchForm.phoneButtonTempleteListJson };
			var jsonList = dynamicPhoneButtonTemplete[row.telTypeModel];
			if (jsonList == undefined) {
				 jsonList = [{'label':ALL_LABEL,'value':''}];
			}
			return $.gridutil.formatSelectTag(jsonList,
					{ name :'phoneButtonTemplete' , 'class' : 'phone_button_templete_select' , disabled : disFlg} , value, row.telTypeModel);
		}

		/**
		 * formatter:拡張モジュール１
		 */
		function formatAddonModuleName1(value, row , index){

			return getAddonModuleList(value, row, 'addonModuleName1');
		}

		/**
		 * formatter:拡張モジュール２
		 */
		function formatAddonModuleName2(value, row , index){

			return getAddonModuleList(value, row, 'addonModuleName2');
		}

		/**
		 * 拡張モジュール取得
		 */
		function getAddonModuleList(value, row, selectName){
			var disFlg = false;
			var dynamicKey = "0";

			if(!row.telId || row.telId == 0 || !adminBoolean()) disFlg = true;

			dynamicAddonModule = ${ userAndTelSearchForm.addonModuleListJson };
			var jsonList = dynamicAddonModule[row.telTypeModel];
			if (jsonList == undefined) {
				 jsonList = [{'label':ALL_LABEL,'value':''}];
			 }


			return $.gridutil.formatSelectTag(jsonList, { name :selectName , 'class' : 'addon_module_name_select' , disabled : disFlg}, value, row.telTypeModel);
		}

		/**
		 * 鳴動設定
		 */
		function formatRingSettingname(value, row , index){
			var disFlg = false;
			var jsonList = ${ userAndTelSearchForm.ringSettingListJson };
			if(!row.telId || row.telId == 0 || !adminBoolean()){
				disFlg = true;
				jsonList = [{'label':ALL_LABEL,'value':''}];
			}
			return $.gridutil.formatSelectTag(jsonList,
					{ name :'ringSettingName' , 'class' : 'ring_setting_name_select', disabled : disFlg}, value);
		}

		/**
		 * formatter:備考（電話機の設置場所）テキスト
		 */
		function formatTelRemarks(value,row,index){
			var disFlg = false;
			if(!row.telId || row.telId == 0 || row.lineIndex != LINE_INDEX_MAIN || !adminBoolean()) disFlg = true;
			return $.gridutil.formatInput({ name : 'telRemarks' , 'class' : 'tel_remarks_text' , value : value, maxlength : '256', disabled : disFlg});
		}

		/**
		 * formatter:発呼側トランスフォーメーションマスクテキスト
		 */
		function formatTransformationMask(value,row,index){
			var disFlg = false;
			if(!row.telId || row.phoneClass == '1' || !adminBoolean()) disFlg = true;
			return $.gridutil.formatInput({ name : 'transformationMask' , 'class' : 'transformation_mask_text' , value : value , maxlength : '10' , disabled : disFlg});
		}

		/**
		 * formatter:GW補足特番テキスト
		 */
		function formatGwRepletionSpecialNo(value,row,index){
			var disFlg = false;
			if(!row.telId || row.phoneClass == '1' || !adminBoolean()) disFlg = true;
			return $.gridutil.formatInput({ name : 'gwRepletionSpecialNo' , 'class' : 'gw_repletion_special_no_text' , value : value , maxlength : '3' , disabled : disFlg});
		}

		
		/**
		 * 権限によってBooleanを返します。
		 * 管理者,変更：true 参照：false
		 */
		function adminBoolean(){
			var admin = true;
			<sec:authorize ifNotGranted="ROLE_CHANGE">
				admin = false;
			</sec:authorize>
			return admin;
		}

	 -->
	</script>
</head>
<body>

<%-- Header --%>
<c:import url="/WEB-INF/jsp/common/header.jsp">
	<c:param name="title" value="ユーザーと電話機の一覧" />
	<c:param name="infoDisplay" value="true" />
</c:import>

<div id="contents" class="container" style="min-width:955px">

	<div id="searchCondition" class="easyui-panel searchCondition" title="検索条件" iconCls="icon-search" collapsible="true">
		<form id="userAndTelSearchForm">
			<input type="submit" style="top: -1000px; left: 0px; margin-left:-1000px;position: absolute;"/>
			<table style="margin:0 auto;padding:0" class="narrow_table">
				<colgroup>
					<col width="105">	<!-- 1 -->
					<col width="160">	<!-- 2 -->
					<col width="80">	<!-- 3 -->
					<col width="100">	<!-- 4 -->
					<col width="55">	<!-- 5_1 -->
					<col width="65">	<!-- 5_2 -->
					<col width="70">	<!-- 6_1 -->
					<col width="40">	<!-- 6_2 -->
					<col width="80">	<!-- 7 -->
					<col width="90">	<!-- 8 -->
					<col width="130">	<!-- 9 -->
					<col width="130">	<!-- 10 -->
				</colgroup>
				<tr>
					<td colspan="1">
						<label for="branchUserId" >拠点（ユーザー）</label>
					</td>
					<td colspan="3">
						<select id="branchUserId" name="branchUserId" class="branch_select">
							<c:forEach var="branch" items="${ userAndTelSearchForm.branchUserList }" varStatus="status">
								<option value="${ branch.value }"><c:out value="${ branch.label }"/></option>
							</c:forEach>
						</select>
					</td>
					<td colspan="2">
						<label for="sectionUserId" >店部課（ユーザー）</label>
					</td>
					<td  colspan="4">
 						<select id="sectionUserId" name="sectionUserId" class="section_select">
							<c:forEach var="section" items="${ userAndTelSearchForm.sectionUaerList }" varStatus="status">
								<option value="${ section.value }"><c:out value="${ section.label }"/></option>
							</c:forEach>
						</select>
					</td>
					<td>
						<label for="attachSectionNameId" >所属課（営業店のみ）</label>
					</td>
					<td>
						<input type="text" id="attachSectionNameId" name="attachSectionName" class="attach_section_name_text" maxlength="257">
					</td>
				</tr>
				<tr>
					<td>
						<label for="branchTelId" >拠点（電話機）</label>
					</td>
					<td colspan="3">
						<select id="branchTelId" name="branchTelId" class="branch_select">
							<c:forEach var="branch" items="${ userAndTelSearchForm.branchTelList }" varStatus="status">
								<option value="${ branch.value }"><c:out value="${ branch.label }"/></option>
							</c:forEach>
						</select>
					</td>
					<td colspan="2">
						<label for="sectionTelId" >店部課（電話機）</label>
					</td>
					<td  colspan="4">
 						<select id="sectionTelId" name="sectionTelId" class="section_select">
							<c:forEach var="section" items="${ userAndTelSearchForm.sectionTelList }" varStatus="status">
								<option value="${ section.value }"><c:out value="${ section.label }"/></option>
							</c:forEach>
						</select>
					</td>
					<td>
						<label for="macAddressId" >MACアドレス</label>
					</td>
					<td>
						<input type="text" id="macAddressId" name="macAddress" class="mac_address_text" maxlength="13">
					</td>
				</tr>
				<tr>
					<td>
						<label for="cucmReflectionDivId" >反映状況</label>
					</td>
					<td>
						<select id="cucmReflectionDivId" name="cucmReflectionDiv" class="cucm_reflection_div_selct">
							<c:forEach var="reflection" items="${ userAndTelSearchForm.cucmReflectionList }" varStatus="status">
								<option value="${ reflection.value }"><c:out value="${ reflection.label }"/></option>
							</c:forEach>
						</select>
					</td>
					<td>
						<label for="kanaUserNameId" >ユーザー名</label>
					</td>
					<td>
 						<input type="text" id="kanaUserNameId" name="kanaUserName" class="kana_user_name_text" maxlength="101">
					</td>
					<td colspan="2">
						<label for="directoryNumberId" >内線番号</label>
					</td>
					<td colspan="2">
						<input type="text" id="directoryNumberId" name="directoryNumber" class="directory_number_text" maxlength="9">
					</td>
					<td>
						<label for="di	alinNumberId" >ダイアルイン</label>
					</td>
					<td>
						<input type="text" id="dialinNumberId" name="dialinNumber" class="dialin_numberr_text" maxlength="24">
						<div id="dialinNumberId-error" class="errorMessage"><span></span></div>
					</td>
					<%-- 
					<td>
						<label for="clusterId" >クラスタ</label>
					</td>
					<td>
						<select id="clusterId" name="clusterId" class="cluster_select">
							<c:forEach var="clus" items="${ userAndTelSearchForm.clusterList }" varStatus="status">
								<option value="${ clus.value }"><c:out value="${ clus.label }"/></option>
							</c:forEach>
						</select>
					</td>
					--%>
					<td>
						<label for="phoneTypeId" >電話機区分</label>
					</td>
					<td>
						<select id="phoneTypeId" name="phoneTypeId" class="phone_type_id_select">
							<option><c:out value="--"/></option>
							<option value="1"><c:out value="IP電話"/></option>
							<option value="2"><c:out value="FMC端末"/></option>
						</select>
					</td>
				</tr>
			    <tr>
					<td>
						<label for="telTypeModelId" >電話機</label>
					</td>
					<td>
						<select id="telTypeModelId" name="telTypeModel" class="tel_type_model_select">
							<c:forEach var="tel" items="${ userAndTelSearchForm.telTypeModelList }" varStatus="status">
								<option value="${ tel.value }"><c:out value="${ tel.label }"/></option>
							</c:forEach>
						</select>
					</td>
					<td>
						<label for="phoneButtonTempleteId" class="letter_size">PhoneButton<br/>Templete</label>
					</td>
					<td>
						<select id="phoneButtonTempleteId" name="phoneButtonTemplete" class="phone_button_templete_search_select">
							<c:forEach var="phone" items="${ userAndTelSearchForm.phoneButtonTempleteList }" varStatus="status">
								<option value="${ phone.value }"><c:out value="${ phone.label }"/></option>
							</c:forEach>
						</select>
					</td>
					<td>
						<label for="addonModuleFlgId" >拡張モジュール</label>
					</td>
					<td>
						<input type="checkbox" id="addonModuleFlgId" name="addonModuleFlg" value="<%= Constants.VOICE_MAIL_FLG_ON %>">
					</td>
					<td>
						<label for="voiceMailFlgId" class="letter_size">Voice&nbsp;Mail<br/>Profile</label>
					</td>
					<td>
						<input type="checkbox" id="voiceMailFlgId" name="voiceMailFlg" value="<%= Constants.VOICE_MAIL_FLG_ON %>" >
					</td>
					<td>
						<label for="busyDestinationId" >話中転送先</label>
					</td>
					<td>
						<input type="text" id="busyDestinationId" name="busyDestination" class="busy_destination_text" maxlength="9">
					</td>
					<td>
						<label for="callingSearchSpaceNameId" class="letter_size" >CSS</label>
					</td>
					<td>
						<input type="text" id="callingSearchSpaceNameId" name="callingSearchSpaceName" class="calling_search_space_name_text" maxlength="257">
					</td>
				</tr>
				<tr>
					<td>
						<label for="chargeAssociationBranchId" >課金先</label>
					</td>
					<td>
						<input type="text" id="chargeAssociationBranchId"        name="chargeAssociationBranchId"        class="charge_association_branch_text"  maxlength="4">
						<span style="text-align:center">-</span>
						<input type="text" id="chargeAssociationParentSectionId" name="chargeAssociationParentSectionId" class="charge_association_parent_section_text" maxlength="6">
						<span style="text-align:center">-</span>
						<input type="text" id="chargeAssociationSectionId"       name="chargeAssociationSectionId"       class="charge_association_section_text" maxlength="6">
					</td>
					<td>
						<label for="loggerDataId" >通話録音</label>
					</td>
					<td>
 						<select id="loggerDataId" name="loggerData" class="logger_data_select">
							<c:forEach var="logger" items="${ userAndTelSearchForm.loggerDataList }" varStatus="status">
								<option value="${ logger.value }"><c:out value="${ logger.label }"/></option>
							</c:forEach>
						</select>
					</td>
					<td colspan="2">
						<label for="pickupGroupNameId" class="letter_size">PickupGroup</label>
					</td>
					<td colspan="2">
 						<select id="pickupGroupNameId" name="pickupGroupName" class="pickup_group_select">
							<c:forEach var="pickup" items="${ userAndTelSearchForm.pickupGropuList }" varStatus="status">
								<option value="${ pickup.value }"><c:out value="${ pickup.label }"/></option>
							</c:forEach>
						</select>
					</td>
					<td>
						<label for="noansDestinationId" >不応答転送</label>
					</td>
					<td>
						<input type="text" id="noansDestinationId" name="noansDestination" class="noans_destination_text" maxlength="50">
					</td>
					<td>
						<label for="lineTextLabelId" >Line&nbsp;Text&nbsp;Label</label>
					</td>
					<td>
						<input type="text" id="lineTextLabelId" name="lineTextLabel" class="line_text_Label_text" maxlength="31">
					</td>
				</tr>
			</table>
			<div class="grid first" style="text-align:right;">
				<a id="searchBtn" class="easyui-linkbutton custom-button" iconCls="icon-search" plain="true" >検索</a>
			</div>
		</form>
	</div>
	<div class="panel-bottom"></div>
	<%-- グリッド --%>
	<form id="gridForm" name="gridForm">
		<%-- トークン --%>
		<input type="hidden" name="_RequestVerificationToken"  />
		<%-- ユーザー追加用ユーザー --%>
		<table id="grid" class="easyui-datagrid"  toolbar="#toolbar"
			   rownumbers="true" singleSelect="true" pagination="true" pageSize="50" checkOnSelect="false" selectOnCheck="false">
			   <thead frozen="true">
					<tr>
						<sec:authorize access="hasRole('ROLE_CHANGE')">
							<th field="ck"          	width="20"  align="center" checkbox="true" data-options="styler:$.gridutil.cellStyleChanged"></th>
						</sec:authorize>
						<th field="statusName"      width="80"  align="center" sortable="true" data-options="styler:$.gridutil.cellStyle">ｽﾃｰﾀｽ</th>
						<th field="directoryNumber" width="100" align="center" sortable="true" data-options="styler:$.gridutil.cellStyle">内線番号</th>
						<th field="phoneClassNm"      width="80"  align="left" sortable="true">電話機区分</th>
					</tr>
				</thead>
				<thead>
					<tr>
						<th field="dialinNumber"            width="100"  align="center" sortable="true" data-options="formatter:formatDialinNumber">ダイアルイン</th>
						<th field="lineIndex"               width="30"   align="right"  sortable="true">番号</th>
						<th field="kanjiUserName"           width="100"  align="left"   sortable="true">ユーザー名</th>
						<th field="sectionUserName"         width="260"  align="left"   sortable="true">所属店部課（ユーザー）</th>
						<th field="pickupGroupName"         width="100"  align="center" sortable="true" data-options="formatter:formatPickupGroup">Pickup&nbsp;Group</th>
						<th field="voiceMailProfile"        width="120"  align="center" sortable="true" data-options="formatter:formatVoiceMailProfile">VMプロファイル</th>
						<th field="busyDestination"         width="100"  align="center" sortable="true" data-options="formatter:formatBusyDestination">話中転送先</th>
						<th field="charge"                  width="170"  align="center" sortable="true" data-options="formatter:formatCharge">課金先</th>
						<th field="chargeRemarks"           width="160"  align="center" sortable="true" data-options="formatter:formatChargeRemarks">備考（課金先）</th>
						<th field="loggerData"              width="70"   align="center" sortable="true" data-options="formatter:formatLoggerData">通話録音</th>
						<%-- <th field="telDirData"              width="50"   align="center" sortable="true" data-options="formatter:formatTelDirData">電話帳</th> --%>
						<th field="telTypeModel"            width="100"  align="left"   sortable="true">電話機</th>
						<th field="macAddress"              width="110"  align="left"   sortable="true">MAC&nbsp;ADDRESS</th>
						<th field="phoneButtonTemplete"     width="160"  align="center" sortable="true" data-options="formatter:formatPhoneButtonTemplete">PhoneButtonTemplate</th>
						<th field="branchTelId"             width="260"  align="center" sortable="true" data-options="formatter:formaBranchTel">拠点（電話機）</th>
						<th field="companySectionTelId"     width="260"  align="center" sortable="true" data-options="formatter:formatSectionTel">所属店部課（電話機）</th>
						<th field="callingSearchSpaceName"  width="210"  align="center" sortable="true" data-options="formatter:formatCallingSearchSpace">CSS</th>
						<th field="addonModuleName1"        width="260"  align="center" sortable="true" data-options="formatter:formatAddonModuleName1">拡張モジュール1設定</th>
						<th field="addonModuleName2"        width="260"  align="center" sortable="true" data-options="formatter:formatAddonModuleName2">拡張モジュール2設定</th>
						<th field="ringSettingName"         width="145"  align="center" sortable="true" data-options="formatter:formatRingSettingname">鳴動設定</th>
						<th field="telLineRemarks"          width="160"  align="center" sortable="true" data-options="formatter:formatTelLineRemarks">備考（電話機/内線番号）</th>
						<th field="lineTextLabel"           width="110"  align="center" sortable="true" data-options="formatter:formatLineTextLabel">Line&nbsp;Text&nbsp;Label</th>
						<th field="noansDestination"        width="100"  align="center" sortable="true" data-options="formatter:formatNoansDestination">不応答転送</th>
						<th field="externalPhoneNumberMask" width="165"  align="center" sortable="true" data-options="formatter:formatExternalPhoneNumber">External&nbsp;Phone&nbsp;Number</th>
						<th field="telRemarks"              width="160"  align="center" sortable="true" data-options="formatter:formatTelRemarks">備考（電話機の設置場所）</th>
						<th field="transformationMask"      width="220"  align="center" sortable="true" data-options="formatter:formatTransformationMask">発呼側トランスフォーメーションマスク</th>
						<th field="gwRepletionSpecialNo"    width="100"  align="center" sortable="true" data-options="formatter:formatGwRepletionSpecialNo">GW補足特番</th>
						<th field="allCss"                  width="210"  align="left"   sortable="true">全転送</th>
						<th field="representativePickup"    width="120"  align="left"   sortable="true">代表ピックアップ</th>
						<th field="fomaNo"                  width="110"  align="left"   sortable="true">FOMA番号</th>
					</tr>
				</thead>
		</table>
		<div id="toolbar" style="padding:0">
			<sec:authorize access="hasRole('ROLE_CHANGE')">
				<%-- <a id="separatelyReflectionBtnId" class="easyui-linkbutton custom-button"        plain="true" style="margin-right:0px;">個別反映</a> --%>
				<a id="nextDayReflectionBtnId"    class="easyui-linkbutton custom-button"              plain="true" style="margin-right:0px;margin-left: 5px;padding: 0px 5px 0px 0px;">更新</a>
				<a id="cucmReflectionBtn"         class="easyui-linkbutton" iconCls="icon-send_orange" plain="true" style="position:margin-right:0px;">即時反映</a>
				<a id="busyDestinationBtnId"      class="easyui-linkbutton" iconCls="icon-check"       plain="true" style="margin-right:0px;">話中転送先確認</a>
				<a id="tableDataExportBtnId"      class="easyui-linkbutton" iconCls="icon-excel"       plain="true" style="margin-right:0px;">検索結果をエクスポート</a>
				<a id="searchResultExportBtnId"   class="easyui-linkbutton" iconCls="icon-excel"       plain="true" style="margin-right:0px;">一覧をエクスポート</a>
				<div id="message-error"           class="errorMessage" style="display:inline;"><span style="padding-left:5px;"></span></div>
				<br/>
			</sec:authorize>
				<%-- <a id="separatelyReferenceBtn"    class="easyui-linkbutton" iconCls="icon-tip"    plain="true" >個別参照</a> --%>
			<sec:authorize access="hasRole('ROLE_CHANGE')">
				<a id="addUserBtn"                class="easyui-linkbutton" iconCls="icon-add"    plain="true" >ユーザー追加</a>
				<a id="addTelBtn"                 class="easyui-linkbutton" iconCls="icon-add"    plain="true" >電話機追加</a>
				<a id="addLineBtn"                class="easyui-linkbutton" iconCls="icon-add"    plain="true" >ライン追加</a>
				<a id="busyDestinationSettiogBtn" class="easyui-linkbutton" iconCls="icon-setup"  plain="true" >話中転送設定</a>
				<a id="returnTelBtn"              class="easyui-linkbutton" iconCls="icon-undo"   plain="true" >返却</a>
				<a id="deleteTelBtn"              class="easyui-linkbutton" iconCls="icon-no"     plain="true" >電話機削除</a>
				<a id="deleteLineBtn"             class="easyui-linkbutton" iconCls="icon-no"     plain="true" >ライン削除</a>
				<a id="insideStockBtn"            class="easyui-linkbutton" iconCls="icon-redo"   plain="true" >部内在庫化</a>
				<a id="shareTelBtn"               class="easyui-linkbutton" iconCls="icon-reload" plain="true" >共用電話化</a>
				<a id="shareTelNameUpdateBtn"     class="easyui-linkbutton" iconCls="icon-pencil" plain="true" >共用名変更</a>
				<a id="addRepresentativeBtn"      class="easyui-linkbutton" iconCls="icon-add"    plain="true" >代表ピックアップ化</a>
				<a id="deleteRepresentativeBtn"   class="easyui-linkbutton" iconCls="icon-no"     plain="true" >代表ピックアップ解除</a>
			</sec:authorize>
		</div>
	</form>
</div>
<%-- dialog --%>
<div id="dialog"></div>

</body>
</html>


		