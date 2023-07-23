<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/taglibs.jsp"/>
<%@ page import="jp.co.netmarks.common.*" %>
<%@ page import="java.util.Properties" %>
<script type="text/javascript">
<!--

	/* 親画面の選択行データ */
	var userAndTelRow;
	var addLineRow;
	/* フラグ */
	var COM_FLG_OFF = <%= Constants.COM_FLG_OFF %>
	/* ダミーデータ */
	var INPUT_DATA_STRING_NUMBER      = <%= Constants.INPUT_DATA_STRING_NUMBER %>;
	var INPUT_DATA_DIRECTORY_NUMBER   = <%= Constants.INPUT_DATA_DIRECTORY_NUMBER %>;
	var INPUT_DATA_CHARGE_BRANCH_ID   = <%= Constants.INPUT_DATA_CHARGE_BRANCH_ID %>;
	var INPUT_DATA_CHARGE_SECTION_ID  = <%= Constants.INPUT_DATA_CHARGE_SECTION_ID %>;
	/* 子画面のタイトル */
	var addNewLine = "紐付けするラインの情報を入力してください。";
	var addNewFmc = "紐付けするFMCの情報を入力してください。";

	/* グリッド */
	var addLineGrid;

	/**
	 * onload処理
	 */
	$(function() {

		/* フォーカスセット */
		$("#addLineDirectoryNumberId").focus();
		
		/* GridUtilの初期化 */
		addLineGrid = $("#addLineGrid").gridutil({
			'resizeHeight' : false,
			'resizeWidth'  : false
		});

		/* 親画面の選択行データをセット */
		userAndTelRow = $('#grid').datagrid('getSelected');

		/* 親画面の電話機データをセット */
		$("#addLineOrgTelId").val(userAndTelRow.telId);

		/* 検索ボタン押下時処理 */
		$('#searchAddLineBtn').click(searchAddLine);

		/* キャンセルボタン押下時処理 */
		$('#addLineCancelBtn').click(function(r){
			$('#dialog').dialog({closed: true});
		});

		/* ライン変更ボタン押下時処理 */
		$("#changeLineBtn").click(function(r){

			/* 更新前処理 */
			if(!beforAddLineEdit()) return false;

			/* コンフィグ */
			$.messager.confirm(t0003, getMessage(c0004,'ライン変更'), function (r) {
				if (!r) return false;

				/* 2度押しチェック */
				if(!addLineGrid.clickEventCheck())return false;

				/* loading画像表示、ボタンを押下不可にする */
				$("#addLineGrid").datagrid('loading');
				$("#addLineBtnArea").attr("disabled", true);
				$("#addNewLineBtnArea").attr("disabled", true);

				$.ajax({
					type : "POST",
					url : "addLine/updateLine",
					data : convertAddLineParam(),
					success : function (data, status, xhr) {
						/* クリック可能処理 */
						addLineGrid.unClickEvent();

						/* loading画像非表示、ボタンを押下可にする */
						$("#addLineBtnArea").attr("disabled", false);
						$("#addNewLineBtnArea").attr("disabled", false);
						
						/* エラーメッセージの初期化 */
						removeError("addLineBtnArea");
						$("#addLineMessageError").hide();

						/* 正常処理 */
						ajaxUpdateSuccessDialog(data, status, xhr,"dialog", "addLineGrid", "grid", "addLineUpdateForm", "addLineMessageError");
					},
					error : function (xhr, status) {
						/* クリック可能処理 */
						addLineGrid.unClickEvent();
						
						/* loading画像非表示 */
						$("#addLineGrid").datagrid('loaded');

						/* エラー処理 */
						ajaxError(xhr,status);
					}
				});
			});
		});

		/* ライン追加ボタン押下時処理 */
		$("#lineLinkingTelBtn").click(function(r){
			/* 更新前処理 */
			if(!beforAddLineEdit()) return false;	

			/* コンフィグ */
			$.messager.confirm(t0003, getMessage(c0004,'ライン追加'), function (r) {
				if (!r) return false;
				
				/* 2度押しチェック */
				if(!addLineGrid.clickEventCheck())return false;

				/* loading画像表示、ボタンを押下不可にする */
				$("#addLineGrid").datagrid('loading');
				$("#addLineBtnArea").attr("disabled", true);
				$("#addNewLineBtnArea").attr("disabled", true);
				
				$.ajax({
					type : "POST",
					url : "addLine/lineLinkingTel",
					data : convertAddLineParam(),
					success : function (data, status, xhr) {
						/* クリック可能処理 */
						addLineGrid.unClickEvent();

						/* loading画像非表示、ボタンを押下可にする */
						$("#addLineBtnArea").attr("disabled", false);
						$("#addNewLineBtnArea").attr("disabled", false);
						
						/* エラーメッセージの初期化 */
						removeError("addLineBtnArea");
						$("#addLineMessageError").hide();

						/* 正常処理 */
						ajaxUpdateSuccessDialog(data, status, xhr,"dialog", "addLineGrid", "grid", "addLineUpdateForm", "addLineMessageError");
					},
					error : function (xhr, status) {
						/* クリック可能処理 */
						addLineGrid.unClickEvent();
						
						/* loading画像非表示 */
						$("#addLineGrid").datagrid('loaded');

						/* エラー処理 */
						ajaxError(xhr,status);
					}
				});
			});
		});

		/* 新規ライン追加ボタン押下時処理 */
		$("#addNewLineBtnId").click(function() {
			/* ダイアログ表示処理 */
			$('#addNewLineDialog').dialog('open').dialog('setTitle','新規ライン情報の入力');
			/* 値のクリア */
			$("#addNewLineForm").form('clear');
			/* タイトルの更新 */
			$("#addNewLineLabel").text(addNewLine);
			/* ラベルの活性制御 */
			$("#newLineIndex").prop('disabled', false);
			$("#newLineDirectoryNumber").prop('disabled', false);
			/* エリア表示制御 */
			$("#newDialinArea").show();
			$("#newFomeNoArea").hide();
			$("#newChargeArea").show();
			/* エラー属性の初期化 */
			removeError('addNewLineForm');
		});

		/* 新規FMC追加ボタン押下時処理 */
		$("#addNewFmcBtnId").click(function() {
			/* ダイアログ表示処理 */
			$('#addNewLineDialog').dialog('open').dialog('setTitle','新規FMC情報の入力');
			/* 値のクリア */
			$("#addNewLineForm").form('clear');
			/* タイトルの更新 */
			$("#addNewLineLabel").text(addNewFmc);
			/* ラベルの活性制御 */
			$("#newLineIndex").prop('disabled', true);
			$("#newLineDirectoryNumber").prop('disabled', true);
			$("#newLineIndex").val('1');
			$("#newLineDirectoryNumber").val(userAndTelRow.directoryNumber);
			/* エリア表示制御 */
			$("#newDialinArea").hide();
			$("#newFomeNoArea").show();
			$("#newChargeArea").hide();
			/* エラー属性の初期化 */
			removeError('addNewLineForm');
		});

		/** #################### 新規ライン追加 開始 #################### **/

		/* 新規ライン追加ボタン押下時処理 */
		$("#newLineCreateBtnId").click(function() {

			/* コンフィグ */
			$.messager.confirm(t0003, getMessage(c0004,'新規ライン作成'), function (r) {
				if (!r) return false;
				
				/* 2度押しチェック */
				if(!addLineGrid.clickEventCheck())return false;

				/* loading画像表示、ボタンを押下不可にする */
				$("#newLineCreateBtnId").attr("disabled", true);

				$.ajax({
					type : "POST",
					url : "addLine/addNewLine",
					data : convertNewLineParam(),
					success : function (data, status, xhr) {

						/* クリック可能処理 */
						addLineGrid.unClickEvent();
						
						/* loading画像非表示、ボタンを押下可にする */
						$("#newLineCreateBtnId").attr("disabled", false);

						/* 画面を通常に戻す */
						removeError('addNewLineForm');

						if(!data['errors']){
							/* ダイアログを閉じる */
							$("#addNewLineDialog").dialog({closed: true});
						}

						/* 正常処理 */
						ajaxUpdateSuccessDialog(data, status, xhr,"dialog",
								null, "grid", "addLineUpdateForm", "addNewLineMessageError");
					},
					error : function (xhr, status) {
						
						/* クリック可能処理 */
						addLineGrid.unClickEvent();
						
						/* loading画像非表示 */
						$("#addLineGrid").datagrid('loaded');

						/* エラー処理 */
						ajaxError(xhr,status);
					}
				});
			});
		});

		/** #################### 新規ライン追加 終了 #################### **/

	});
	
	/**
	 * 更新前処理
	 */
	function beforAddLineEdit(){
		
		addLineRow = $('#addLineGrid').datagrid('getSelected');

		/* 選択 */
		if(!addLineRow) {
			$.messager.alert(t0001, getMessage(e0002,'設定する電話番号'));
			return false;
		}
		
		return true;
	}

	/**
	 * ユーザー追加ダイアログ：検索処理
	 */
	function searchAddLine(){
		/* ページを1行目にする */
		$("#addLineGrid").datagrid('options').pageNumber = 1;
		
		/* 検索 */
		addLineGrid.search('addLine/search', '#addLineForm');
		return false;
	}

	/**
	 * ライン追加で渡すパラメータをセットします。
	 */
	function convertAddLineParam(){
		/* ライン追加画面のグリッド値を取得 */
		addLineRow = $('#addLineGrid').datagrid('getSelected');

		/* ライン番号の先頭0を削除する */
		if($('#lineIndex').val() != "" && !$('#lineIndex').val().match(/[^0-9]+/))
				$('#lineIndex').val(Number($('#lineIndex').val()));

		/* パラメータをセット */
		var params = "";
		params += 'userId=' + userAndTelRow.userId    + "&";
		params += 'telId='  + userAndTelRow.telId  + "&";
		params += 'lineId=' + addLineRow.lineId + "&";
		params += 'lineIndex=' + $('#lineIndex').val() + '&';
		params += 'orgUserId=' + userAndTelRow.userId + '&';
		params += 'orgTelId='  + userAndTelRow.telId   + '&';
		params += 'orgLineId=' + userAndTelRow.lineId  + '&';
		params += 'orgLineIndex=' + userAndTelRow.lineIndex + '&';
		params += 'branchTelId='  + userAndTelRow.orgBranchTelId + '&'; 

		/* 入力チェック回避用 */
		params += 'newLineIndex=' + INPUT_DATA_STRING_NUMBER + '&';
		params += 'newLineDirectoryNumber=' + INPUT_DATA_DIRECTORY_NUMBER + '&';
		params += 'newLineChargeAssociationBranchId=' + INPUT_DATA_CHARGE_BRANCH_ID + '&';
		params += 'newLineChargeAssociationParentSectionId=' + INPUT_DATA_CHARGE_SECTION_ID + '&';
		params += 'newLineChargeAssociationSectionId=' + INPUT_DATA_CHARGE_SECTION_ID + '&';
		/* トークンをセット */
		params += '_RequestVerificationToken=' + $("input[name=_RequestVerificationToken]").val();

		return params;
	}

	/**
	 * 新規ラインで渡すパラメータをセットします。
	 */
	function convertNewLineParam(){

		/* ライン番号の先頭0を削除する */
		if($('#newLineIndex').val() != "" && !$('#newLineIndex').val().match(/[^0-9]+/))
				$('#newLineIndex').val(Number($('#newLineIndex').val()));

		/* パラメータをセット */
		var params = "";
		params += 'userId=' + userAndTelRow.userId + "&";
		params += 'telId='  + userAndTelRow.telId  + "&";
		params += 'lineId=' + "0" + "&";
		params += 'lineIndex=' + INPUT_DATA_STRING_NUMBER + '&';
		params += 'branchTelId='  + userAndTelRow.orgBranchTelId + '&'; 
		params += 'newLineIndex=' + $('#newLineIndex').val() + '&';
		params += 'newLineDirectoryNumber=' + $('#newLineDirectoryNumber').val() + '&';
		params += 'newLineDialinNumber=' + $('#newLineDialinNumber').val() + '&';
		params += 'newLineChargeAssociationBranchId='  + $('#newLineChargeAssociationBranchId').val() + '&';
		params += 'newLineChargeAssociationParentSectionId=' + $('#newLineChargeAssociationParentSectionId').val() + '&';
		params += 'newLineChargeAssociationSectionId=' + $('#newLineChargeAssociationSectionId').val() + '&';

		var voiceMailFlg = $('#newLineVoiceMailFlgId').val();
		if(!$('#newLineVoiceMailFlgId').attr("checked")) voiceMailFlg = COM_FLG_OFF;
		params += 'newLineVoiceMailFlg=' + voiceMailFlg + '&';

		params += 'orgUserId=' + userAndTelRow.userId + '&';
		params += 'orgTelId='  + userAndTelRow.telId   + '&';
		params += 'orgLineId=' + userAndTelRow.lineId  + '&';
		params += 'orgLineIndex=' + userAndTelRow.lineIndex + '&';


		/* トークンをセット */
		params += '_RequestVerificationToken=' + $("input[name=_RequestVerificationToken]").val();

		return params;
	}


//-->
</script>
<div id="callAddLineDialog" style="padding:10px 20px;" >
	<%-- Header --%>
	<c:import url="/WEB-INF/jsp/common/dialogHeader.jsp">
		<c:param name="title" value="ラインの検索と選択" />
	</c:import>
	<div>
		<form id="addLineForm" style="margin:0px 5px 0px 10px" onsubmit="return false;">
			<input type="hidden" name="orgLineId" id="addLineOrgTelId">
			<div  style="margin:10px 0px 5px 0px;">
				<p class="dialog_notes">電話番号を追加する連番を指定してください。</p>
			</div>
			<table width="860">
				<colgroup>
					<col width="80px">
					<col width="130px">
					<col width="650px">
				</colgroup>
				<tr>
					<td class="label" align="left">
						<label for="addLineDirectoryNumberId">内線番号</label>
					</td>
					<td>
						<input type="text" id="addLineDirectoryNumberId" name="addLineDirectoryNumber" class="directory_number_text" maxlength="9">
					</td>
					<td >
						<a id="searchAddLineBtn" class="easyui-linkbutton custom-button" iconCls="icon-search" plain="true" >検索</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div  style="margin:10px 0px 5px 0px;">
		<p class="dialog_notes">紐付けする電話番号の行をクリックしてください。</p>
		<div id="addLineMessageError" class="errorMessage" style="display:inline;float:left;"><span style="margin-left:100px;"></span></div>
	</div>

	<table id="addLineGrid" class="easyui-datagrid" style="width:890px;height:315px"
	       data-options="rownumbers:true, singleSelect:true, pagination:true, checkOnSelect:false, selectOnCheck:false">
		<thead>
			<tr>
				<th field="directoryNumber"        width="100" align="left" sortable="true">内線番号</th>
				<th field="fomeNo"                 width="100" align="left" sortable="true">FOMA番号</th>
				<th field="busyDestination"        width="100" align="left" sortable="true">話中転送先</th>
				<th field="noansDestination"       width="100" align="left" sortable="true">不応答転送先</th>
				<th field="pickupGroupName"        width="140" align="left" sortable="true">PickUpGroup</th>
				<th field="chargeAssociationPlace" width="140" align="left" sortable="true">課金先</th>
				<th field="voiceMailProfileNm"     width="140" align="left" sortable="true">VMプロファイル名</th>
			</tr>
		</thead>
	</table>
	<div id="addNewLineBtnArea">
		<a id="addNewLineBtnId" class="easyui-linkbutton custom-button" iconCls="icon-add" plain="true"style="float:left" >新規ライン追加</a>
		<a id="addNewFmcBtnId"  class="easyui-linkbutton custom-button" iconCls="icon-add" plain="true"style="float:left" >新規FMC追加</a>
	</div>
	<div id="addLineBtnArea" align="right">
		<label for="indextxtL">ライン</label>
		<input type="text" id="lineIndex" name="lineIndex" maxlength="2" class="line_index_text">
		<a id="changeLineBtn"      class="easyui-linkbutton custom-button" iconCls="icon-edit"    plain="true" >ライン変更</a>
		<a id="lineLinkingTelBtn"  class="easyui-linkbutton custom-button" iconCls="icon-add"     plain="true" >ライン追加</a>
		<a id="addLineCancelBtn"   class="easyui-linkbutton custom-button" iconCls="icon-cancel " plain="true" >キャンセル</a>
	</div>
</div>
<%-- dialog --%>
<c:import url="/WEB-INF/jsp/dialog/addNewLine.jsp"/>