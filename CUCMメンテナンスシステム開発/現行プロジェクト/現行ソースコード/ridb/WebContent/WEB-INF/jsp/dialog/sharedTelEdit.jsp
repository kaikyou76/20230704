<%@page import="jp.co.netmarks.common.Constants"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/taglibs.jsp"/>
<script type="text/javascript">
<!--
	/* 新規・更新判定フラグ :true:新規 false:更新 */
	var userAndTelRow = $('#grid').datagrid('getSelected');
	var newEditFlg = (userAndTelRow.sharedUse == <%= Constants.ENABLED_SHARED_USE_PRIVATE %>) ? true : false;

	/* 注意書き */
	var notesNewEdit = "この電話を共用化します。<br>既存ユーザとの紐付は解除されます。";
	var notesUpdate = "この電話の共用名を変更します。";

	/* ボタン名 */
	var btnNewEdit = "共用化";
	var btnUpdate = "変更";

	/**
	 * onload処理
	 */
	$(function() {
		
		/* 2度押しチェック用フラグ */
		updateFlg = "0";

		/* 共用名プルダウンイベント */
		sharedNmDisabled();
		$('#sharedId').change(sharedNmDisabled);

		/* 注書きの設定 */
		var notes = newEditFlg ? notesNewEdit : notesUpdate;
		$("#sharedTelNotes").html(notes);
		/* ボタン名の設定 */
		var btnNm = newEditFlg ? btnNewEdit : btnUpdate;
		$('#sharedTelEditBtn').text(btnNm);
		
		/* フォーカスセット */
		$("#sharedUserName").focus();

		/* 共用ボタン押下 */
		$('#sharedTelEditBtn').click(function(r){

			var url = newEditFlg ? "sharedTel/sharedTelEdit"  : "sharedTel/sharedTelUpdate" ;
			
			/* コンフィグ */
			$.messager.confirm('', getMessage(c0004,'共用化'), function (r) {
				if (!r) return false;
				
				/* 2度押しチェック */
				if(!updateCheck()) return false;

				/* ボタンを押下不可にする */
				$("#sharedTelBtnArea").attr("disabled", true);

				
				$.ajax({
					type : "POST",
					url  : url,
					data : convertSharedTelParam(),
					success : function (data, status, xhr) {
						
						/* 2度押しチェック用フラグ */
						updateFlg = "0";
						
						/* ボタンを押下不可にする */
						$("#sharedTelBtnArea").attr("disabled", false);


						/* 正常処理 */
						ajaxUpdateSuccessDialog(data, status, xhr,"dialog", null, "grid", "sharedTelUpdateForm", "sharedTelMessageError");
					},
					error : function (xhr, status) {
						
						/* 2度押しチェック用フラグ */
						updateFlg = "0";
						
						/* ボタンを押下不可にする */
						$("#sharedTelBtnArea").attr("disabled", false);
						
						/* loading画像非表示 */
						$("#addTelGrid").datagrid('loaded');

						/* エラー処理 */
						ajaxError(xhr,status);
					}
				});
			});
		});

		/**
		 * ユーザー追加で渡すパラメータをセットします。
		 */
		function convertSharedTelParam(){
			/* パラメータをセット */
			var params = "";
			params += 'userId='         + userAndTelRow.userId        + "&";
			params += 'telId='          + userAndTelRow.telId         + "&";
			params += 'lineId='         + userAndTelRow.lineId        + "&";
			params += 'lineIndex='      + userAndTelRow.lineIndex     + '&';
			params += 'sectionUserId='  + userAndTelRow.sectionUserId + '&';
			params += 'companyUserId='  + userAndTelRow.companyUserId + '&';
			params += 'sharedUse='      + userAndTelRow.sharedUse     + '&';
			params += 'sharedId='       + $('#sharedId').val()        + '&';
			params += 'sharedNm='       + $('#sharedNm').val()        + '&';
			params += 'remarks='        + $('#remarks').val()         + '&';


			/* トークンをセット */
			params += '_RequestVerificationToken=' + $("input[name=_RequestVerificationToken]").val();

			return params;
		}

		/* キャンセルボタン押下時処理 */
		$('#sharedTelCancelBtn').click(function(r){
			$('#dialog').dialog({closed: true});
		});
		
		/**
		 * 拠点名選択時処理
		 */
		function sharedNmDisabled() {
			var disFlg = true;
			
			// その他選択時、共用名入力欄を活性化する
			if ($("#sharedId").val() == 99) {
				disFlg = false;
			}

			$("#sharedNm").prop('disabled', disFlg);
		}

	});

//-->
</script>
<div id="callSaredTelDialog" style="padding:10px 10px;" >
	<div id="sharedTelNotes"></div>
	<div style="height:15px;">
		<div id="sharedTelMessageError" class="errorMessage" style=""><span style="margin-left:100px;"></span></div>
	</div>
	<form id="sharedTelForm" method="post" onsubmit="return false;">
		<table class="narrow_table">
			<colgroup>
				<col width="80">	<!-- 1 -->
				<col width="320">	<!-- 2 -->
			</colgroup>
			<tr>
				<td>
					<label >共用名：</label>
				</td>
				<td>
					<select id="sharedId" name="sharedId" class="shared_id_select">
						<option value="1"><c:out value="会議室"/></option>
						<option value="99"><c:out value="その他"/></option>
					</select>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>
					<input type="text" id="sharedNm" name="sharedNm" class="shared_nm_text"  maxlength="40">
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<label>備考（電話機の設置場所）：</label>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>
					<input type="text" id="remarks" name="remarks" class="shared_nm_text"  maxlength="256">
				</td>
			</tr>
		</table>
	</form>
	<div id="sharedTelBtnArea" align="right">
		<a id="sharedTelEditBtn"   class="easyui-linkbutton custom-button" iconCls="icon-ok"      plain="true" >共用化</a>
		<a id="sharedTelCancelBtn" class="easyui-linkbutton custom-button" iconCls="icon-cancel"  plain="true" >キャンセル</a>
	</div>
</div>