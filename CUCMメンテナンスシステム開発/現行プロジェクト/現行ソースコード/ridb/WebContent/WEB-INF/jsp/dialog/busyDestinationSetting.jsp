<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/taglibs.jsp"/>
<%@ page import="jp.co.netmarks.common.*" %>
<script type="text/javascript">
<!--

	/* 親画面の選択行データ */
	var userAndTelRow;
	var busyDestSettingGrid;

	/**
	 * onload処理
	 */
	$(function() {
		
		/* フォーカスセット */
		$("#busyDestinationArray1").focus();

		/* GridUtilの初期化 */
		busyDestSettingGrid = $("#busyDestSettingGrid").gridutil({
			'resizeHeight' : false,
			'resizeWidth'  : false
		});

		/* 親画面の選択行データをセット */
		userAndTelRow = $('#grid').datagrid('getSelected');

		/* 電話機の内線番号をチェック */
		$("#firstDirectoryNumberId").val(userAndTelRow.directoryNumber);

		/* 検索ボタン押下時処理 */
		$('#confirmBusyDestBtn').click(searchBusy);

		/* キャンセルボタン押下時処理 */
		$('#cancelBusyDestBtn').click(function(r){
			$('#dialog').dialog({closed: true});
		});
		
		/* 電話機追加ボタン押下時処理 */
		$("#updateBusyDestBtn").click(function(r){

			/* コンフィグ */
			$.messager.confirm(t0003, getMessage(c0004,'更新（翌日反映）'), function (r) {
				if (!r) return false;

				/* loading画像表示、ボタンを押下不可にする */
				$("#busyDestSettingGrid").datagrid('loading');
				$("#busyDestBtnArea").attr("disabled", true);

				$.ajax({
					type : "POST",
					url : "busyDestination/updateBusyDestination",
					data : convertBusyParam(),
					success : function (data, status, xhr) {

						/* ボタンを押下可にする */
						$("#busyDestBtnArea").attr("disabled", false);

						/* 正常処理 */
						ajaxUpdateSuccessDialog(data, status, xhr,"dialog", "busyDestSettingGrid", "grid", "busyDestinationSettingUpdateForm", "busyDestMessageError");
					},
					error : function (xhr, status) {
						/* loading画像非表示 */
						$("#busyDestSettingGrid").datagrid('loaded');

						/* エラー処理 */
						ajaxError(xhr,status);
					}
				});
			});
		});

	});

	/**
	 * 検索
	 */
	function searchBusy(){
		/* エラーメッセージの初期化 */
		removeError("busyDestinationSettingForm");

		/* 検索処理 */
		busyDestSettingGrid.search('busyDestination/confirm', '#busyDestinationSettingForm',null ,function(data){
			/* エラー処理 */
			showDialogErrorMessageForObject(data['errors'] ,"busyDestMessageError");
		});
		return false;
	}
	
	/**
	 * 更新パラメータコンバート処理
	 */
	function convertBusyParam(){
		/* パラメータをセット */
		var params = "";
		
		$.each($("#busyDestinationSettingForm").get(0).elements, function(){
			params += this.name + '=' + $(this).val() + "&";
		});

		/* トークンをセット */
		params += '_RequestVerificationToken=' + $("input[name=_RequestVerificationToken]").val();

		return params;
	}

//-->
</script>
<div id="callBusyDestDialog" style="padding:10px 20px;" >
	<%-- Header --%>
	<c:import url="/WEB-INF/jsp/common/dialogHeader.jsp">
		<c:param name="title" value="話中転送の設定" />
	</c:import>
	<div style="margin-bottom:5px;">
		<form id="busyDestinationSettingForm" style="margin:0px 5px 0px 10px">
			<%-- 登録元の内線番号 --%>
			<input type="hidden" name="firstDirectoryNumber" id="firstDirectoryNumberId">
			<div  style="margin:10px 0px 5px 0px;">
				<p class="dialog_notes">
					内線番号を入力し、「確認」をクリックしてください。<br/>
					よろしければ「更新」をクリックしてください。
				</p>
				<div id="busyDestMessageError" class="errorMessage" style="display:inline;float:left;"><span style="margin-left:20px;"></span></div>
			</div>
			<table width="770">
				<colgroup>
					<col width="100px">
					<col width="100px">
					<col width="100px">
					<col width="100px">
					<col width="100px">
					<col width="100px">
					<col width="100px">
					<col width="100px">
				</colgroup>
				<c:forEach items="${busyDestinationSettingForm.busyDestinationArray}" var="busy" varStatus="status">
					<c:set var="cnt" value="${cnt + 1}" />
					<c:set var="readonly" value=""/>
					<c:if test="${cnt == 1}">
						<tr>
					</c:if>
					<c:if test="${ status.index == 0 }">
						<c:set var="readonly" value="readonly"/>
					</c:if>
					<td align="right">
						<c:out value="${status.count}" />:
						<input type="text" id="busyDestinationArray${status.index}" name="busyDestinationArray" 
						       	class="busy_destination_short_text" value="${busy}" maxlength="8" ${readonly}>
					</td>
					<c:if test="${cnt == 8 || status.last}">
						</tr>
						<c:set var="cnt" value="0" />
					</c:if>
				</c:forEach>
			</table>
			<div align="right">
				<div id="csvFileDiv" style="display: inline-block;">
					CSVファイル選択：
						<input id="csvFile" name="csvFile" type="file"  maxlength="1" accept="csv"/>
						<a id="csvImportBtn" href="#" class="easyui-linkbutton" iconCls="icon-excel" plain="true">選択したCSVファイルをグリッドに表示</a>
				</div>
				<a id="confirmBusyDestBtn" href="#" class="easyui-linkbutton custom-button" plain="true">確認</a>
			</div>
		</form>
	</div>
	<div>入力設定</div>
	<table id="busyDestSettingGrid" class="easyui-datagrid" style="width:790px;height:200px;"
	       data-options="singleSelect:true, checkOnSelect:false, selectOnCheck:false">
		<thead>
			<tr>
				<th field="forwardIndex"     width="50"   align="center" >転送順</th>
				<th field="directoryNumber"  width="100"  align="left"   >内線番号</th>
				<th field="kanjiUserName"    width="100"  align="left"   >利用者名</th>
				<th field="branchTelName"    width="190"  align="left"   >拠点</th>
				<th field="sectionTelName"   width="190"  align="left"   >所属店部課</th>
				<th field="busyDestination"  width="100"  align="left"   >話中転送先</th>
			</tr>
		</thead>
	</table>
	<div style="margin-top:10px">現状設定</div>
	<table id="busyDestSettingGrid" class="easyui-datagrid" style="width:790px;height:200px;"
	       data-options="singleSelect:true, checkOnSelect:false, selectOnCheck:false">
		<thead>
			<tr>
				<th field="forwardIndex"     width="50"   align="center" >転送順</th>
				<th field="directoryNumber"  width="100"  align="left"   >内線番号</th>
				<th field="kanjiUserName"    width="100"  align="left"   >利用者名</th>
				<th field="branchTelName"    width="190"  align="left"   >拠点</th>
				<th field="sectionTelName"   width="190"  align="left"   >所属店部課</th>
				<th field="busyDestination"  width="100"  align="left"   >話中転送先</th>
			</tr>
		</thead>
	</table>
	<div id="busyDestBtnArea" align="right">
		<a id="updateBusyDestBtn"   class="easyui-linkbutton custom-button" iconCls="icon-ok"     plain="true" >更新</a>
		<a id="cancelBusyDestBtn"   class="easyui-linkbutton custom-button" iconCls="icon-cancel" plain="true" >キャンセル</a>
	</div>
</div>