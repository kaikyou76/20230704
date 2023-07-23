<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/taglibs.jsp"/>
<script type="text/javascript">
<!--

/* 新規追加ボタン押下時処理 */
$("#insertBranch").click(function() {

	/* コンフィグ */
	$.messager.confirm(t0003, getMessage(c0004,'新規拠点を追加'), function (r) {
		if (!r) return false;

		/* 2度押しチェック */
		if(!grid.clickEventCheck())return false;

		/* ボタンを押下不可にする */
		$("#insertBranch").attr("disabled", true);

		var branchId = $('#branchId').val();
		var branchName = $('#branchName').val();
		var clusterId = $('#addClusterId').val();

		$.ajax({
			type : "POST",
			url : "addBranch/addNewBranch",
			data : convertDialogParam(branchId,branchName,clusterId,INPUT_DATA_BRANCH_COMPANY_ID,INPUT_DATA_SECTION_ID),
			success : function (data, status, xhr) {

				/* クリック可能処理 */
				grid.unClickEvent();

				/* loading画像非表示、ボタンを押下可にする */
				$("#insertBranch").attr("disabled", false);

				/* 画面を通常に戻す */
				removeError('addBranchForm');

				/* 正常処理 */
				ajaxUpdateSuccessDialog(data, status, xhr,"dialog",
						null, "grid", "addBranchForm", "addBranchMessageError");

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
<div id="addBranchDialog" class="" style="padding:10px 20px" >
	<div id="dialogHeader" class="">
		<div  style="margin:10px 0px 5px 0px;">
		<p style="font-weight:bold">新規追加する拠点情報を入力してください。</p>
		</div>
	</div>
	<div style="height:15px;">
		<div id="addBranchMessageError" class="errorMessage"><span></span></div>
	</div>
	<div>
	<form id="addBranchForm" method="post">
		<table width="280">
			<colgroup>
				<col width="80">
				<col width="190">
			</colgroup>
			<tr>
				<td class="label" align="center">
					<label for="branchId">拠点コード</label>
				</td>
				<td>
					<%-- <input type="text" id="addBranchId"  name="branchId"  maxlength="5" class="branch_id_text" >--%>
					
					<form:input path="addBranchForm.branchId" id="branchId" class="branch_id_text" maxlength="5" />
					<div>
						<span id="branchIdError"></span>
					</div>
				</td>
			</tr>
			<tr>
				<td class="label" align="center">
					<label for="branchName">拠点名</label>
				</td>
				<td>
					<input type="text" id="branchName" name="branchName"  class="branch_name_text" >
				</td>
			</tr>
			<%-- 
			<tr>
				<td class="label" align="center">
					<label for="addClusterId">クラスタ</label>
				</td>
				<td>
					<select id="addClusterId" name="clusterId"  class="cluster_select" >
						<c:forEach var="cluster" items="${ addBranchForm.clusterList }" varStatus="status">
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
		<a id="insertBranch" href="#" class="easyui-linkbutton custom-button" iconCls="icon-add" plain="true">新規追加</a>
	</div>
</div>