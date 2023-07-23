<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/taglibs.jsp"/>
<script type="text/javascript">
<!--
/* 親画面の選択行データ */

	var selectedRow;
	/**
	 * onload処理
	 */
	$(function() {
		selectedRow = $('#grid').datagrid('getSelected');
		/* 選択拠点コードセット */
		$('#updateBranchId').text(selectedRow.branchId);
	});

	/* 拠点変更ボタン押下時処理 */
	$("#updateBranch").click(function() {

		/* コンフィグ */
		$.messager.confirm(t0003, getMessage(c0004,'拠点情報を変更'), function (r) {
			if (!r) return false;

			/* 2度押しチェック */
			if(!grid.clickEventCheck())return false;

			/* ボタンを押下不可にする */
			$("#updateBranch").attr("disabled", true);

			var branchId = $('#updateBranchId').text();
			var branchName = $('#updateBranchName').val();
			var clusterId = $('#updateClusterId').val();


			$.ajax({
				type : "POST",
				url : "updateBranch/updateBranchInfo",
				data : convertDialogParam(branchId,branchName,clusterId,INPUT_DATA_BRANCH_COMPANY_ID,INPUT_DATA_SECTION_ID),
				success : function (data, status, xhr) {

					/* クリック可能処理 */
					grid.unClickEvent();

					/* loading画像非表示、ボタンを押下可にする */
					$("#updateBranch").attr("disabled", false);

					/* 画面を通常に戻す */
					removeError('updateBranchForm');

					/* 正常処理 */
					ajaxUpdateSuccessDialog(data, status, xhr,"dialog",
							null, "grid", "updateBranchForm", "updateBranchMessageError");

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
<div id="updateBranchDialog"  style="padding:10px 20px" >
	<div id="dialogHeader" class="">
		<div  style="margin:10px 0px 5px 0px;">
		<p style="font-weight:bold">更新する拠点情報を入力してください。</p>
		</div>
	</div>
	<div style="height:15px;">
		<div id="updateBranchMessageError" class="errorMessage"><span></span></div>
	</div>
	<div>
	<form id="updateBranchForm" method="post">
		<table width="280">
			<colgroup>
				<col width="80">
				<col width="190">
			</colgroup>
			<tr>
				<td class="label" align="center">
					<label for="updateBranchId">拠点コード</label>
				</td>
				<td>
					<label id="updateBranchId"  class="" ></label>
				</td>
			</tr>
			<tr>
				<td class="label" align="center">
					<label for="updateBranchName">拠点名</label>
				</td>
				<td>
					<input type="text" id="updateBranchName"   class="branch_name_text" >
				</td>
			</tr>
			<%-- 
			<tr>
				<td class="label" align="center">
					<label for="updateClusterId">クラスタ</label>
				</td>
				<td>
					<select id="updateClusterId" name="clusterId"  class="cluster_select">
						<c:forEach var="cluster" items="${ updateBranchForm.clusterList }" varStatus="status">
							<option value="${ cluster.value }"><c:out value="${ cluster.label }"/></option>
						</c:forEach>
					</select>
				</td>
			</tr>
			 --%>
		</table>
	</form>
	</div>
	<div align="right" style="margin:10px 0px 0px 0px">
		<a id="updateBranch" href="#" class="easyui-linkbutton  custom-button" iconCls="icon-reload" plain="true">更新</a>
	</div>
</div>

