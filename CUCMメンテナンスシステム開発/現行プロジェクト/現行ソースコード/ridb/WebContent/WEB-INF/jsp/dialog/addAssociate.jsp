<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/taglibs.jsp"/>
<script type="text/javascript">
<!--

	var selectedRow;
	/**
	 * onload処理
	 */
	$(function() {
		selectedRow = $('#grid').datagrid('getSelected');
		/* 選択拠点コードセット */
		$('#associateBranchId').text(selectedRow.branchId);
	});

	/* 紐付作成ボタン押下時処理 */
	$("#insertAssociate").click(function() {

		/* コンフィグ */
		$.messager.confirm(t0003, getMessage(c0004,'拠点-店部課の紐付を作成'), function (r) {
			if (!r) return false;

			/* 2度押しチェック */
			if(!grid.clickEventCheck())return false;


			/* ボタンを押下不可にする */
			$("#insertAssociate").attr("disabled", true);

			var branchId = $('#associateBranchId').text();
			var branchName = selectedRow.branchName;
			var clusterId = selectedRow.clusterId;
			var companyId = $('#companyId').val();
			var sectionId = $('#sectionId').val();

			$.ajax({
				type : "POST",
				url : "addAssociate/addNewAssociate",
				data : convertDialogParam(branchId,branchName,clusterId,companyId,sectionId),
				success : function (data, status, xhr) {

					/* クリック可能処理 */
					grid.unClickEvent();

					/* loading画像非表示、ボタンを押下可にする */
					$("#insertAssociate").attr("disabled", false);

					/* 画面を通常に戻す */
					removeError('addAssociateForm');

					/* 正常処理 */
					ajaxUpdateSuccessDialog(data, status, xhr,"dialog",
							null, "grid", "addAssociateForm", "associateMessageError");

					/* 拠点プルダウン再取得 */
					getBranchList();
				},
				error : function (xhr, status) {
					/* loading画像非表示 */
					$("#grid").datagrid('loaded');

					/* エラー処理 */
					ajaxError(xhr,status);

					/* 拠点プルダウン再取得 */
					getBranchList();
				}
			});
		});
	});
//-->
</script>
<%@ page import="jp.co.netmarks.common.*" %>
<div id="addAssociateDialog"  style="padding:10px 20px" >
	<div id="dialogHeader" class="">
		<div  style="margin:10px 0px 5px 0px;">
		<p style="font-weight:bold">紐付を行う店部課情報を入力してください</p>
		</div>
	</div>
	<div style="height:15px;">
		<div id="associateMessageError" class="errorMessage"><span></span></div>
	</div>
	<div>
	<form id="addAssociateForm" method="post">
		<table width="280">
			<colgroup>
				<col width="80">
				<col width="190">
			</colgroup>
			<tr>
				<td class="label" align="center">
					<label for="associateBranchId">拠点コード</label>
				</td>
				<td>
					<label id="associateBranchId"  class="branch_id_text" ></label>
				</td>
			</tr>
			<tr>
				<td class="label" align="center">
					<label for="companyId">会社コード</label>
				</td>
				<td>
					<input type="text" id="companyId" maxlength="3" class="company_id_text" value="">
				</td>
			</tr>
			<tr>
				<td class="label" align="center">
					<label for="sectionId">店部課コード</label>
				</td>
				<td>
					<input type="text" id="sectionId" maxlength="5" class="section_id_text" value="">
				</td>
			</tr>
		</table>
	</form>
	</div>
	<div align="right" style="margin:10px 0px 0px 0px">
		<a id="insertAssociate" href="#" class="easyui-linkbutton  custom-button" iconCls="icon-add" plain="true">紐付作成</a>
	</div>
</div>