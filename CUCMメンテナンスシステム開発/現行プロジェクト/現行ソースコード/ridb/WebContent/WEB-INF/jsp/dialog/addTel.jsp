<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/taglibs.jsp"/>
<script type="text/javascript">
<!--

	/* 親画面の選択行データ */
	var userAndTelRow;
	var addTelRow;

	/* グリッド */
	var addTelGrid;

	/**
	 * onload処理
	 */
	$(function() {
		
		/* フォーカスセット */
		$("#addTelBranchId").focus();
		
		/* GridUtilの初期化 */
		addTelGrid = $("#addTelGrid").gridutil({
			'resizeHeight' : false,
			'resizeWidth'  : false
		});

		/* 親画面の選択行データをセット */
		userAndTelRow = $('#grid').datagrid('getSelected');

		/* 親画面の電話機データをセット */
		$("#addTelOrgTelId").val(userAndTelRow.telId);

		/* 検索ボタン押下時処理 */
		$('#searchAddTelBtn').click(searchAddTel);

		/* 拠点プルダウン変更 */
		$('#addTelBranchId').change(getSectionAddTelList);

		/* 電話機変更ボタン押下時処理 */
		$("#changeTelBtn").click(function(r){

			/* 更新前処理 */
			if(!beforAddTelEdit()) return false;

			/* コンフィグ */
			$.messager.confirm(t0003, getMessage(c0004,'電話機変更'), function (r) {
				if (!r) return false;
				
				/* 2度押しチェック */
				if(!addTelGrid.clickEventCheck())return false;
				
				/* loading画像表示、ボタンを押下不可にする */
				$("#addTelGrid").datagrid('loading');
				$("#addTelBtnArea").attr("disabled", true);

				$.ajax({
					type : "POST",
					url : "addTel/updateTel",
					data : convertAddTelParam(),
					success : function (data, status, xhr) {
						/* クリック可能処理 */
						addTelGrid.unClickEvent();
						/* loading画像非表示、ボタンを押下可にする */
						$("#addTelBtnArea").attr("disabled", false);

						/* 正常処理 */
						ajaxUpdateSuccessDialog(data, status, xhr,"dialog", "addTelGrid", "grid", "addTelUpdateForm", "addTelMessageError");
					},
					error : function (xhr, status) {
						/* クリック可能処理 */
						addTelGrid.unClickEvent();
						/* loading画像非表示 */
						$("#addTelGrid").datagrid('loaded');

						/* エラー処理 */
						ajaxError(xhr,status);
					}
				});
			});
		});

		/* 電話機追加ボタン押下時処理 */
		$("#telLinkingUserBtn").click(function(r){
			
			/* 更新前処理 */
			if(!beforAddTelEdit()) return false;

			/* コンフィグ */
			$.messager.confirm(t0003, getMessage(c0004,'電話機追加'), function (r) {
				if (!r) return false;
				
				/* 2度押しチェック */
				if(!addTelGrid.clickEventCheck())return false;

				/* loading画像表示、ボタンを押下不可にする */
				$("#addTelGrid").datagrid('loading');
				$("#addTelBtnArea").attr("disabled", true);

				$.ajax({
					type : "POST",
					url : "addTel/telLinkingUser",
					data : convertAddTelParam(),
					success : function (data, status, xhr) {
						/* クリック可能処理 */
						addTelGrid.unClickEvent();
						/* ボタンを押下可にする */
						$("#addTelBtnArea").attr("disabled", false);

						/* 正常処理 */
						ajaxUpdateSuccessDialog(data, status, xhr,"dialog", "addTelGrid", "grid", "addTelUpdateForm", "addTelMessageError");
					},
					error : function (xhr, status) {
						/* クリック可能処理 */
						addTelGrid.unClickEvent();
						/* loading画像非表示 */
						$("#addTelGrid").datagrid('loaded');

						/* エラー処理 */
						ajaxError(xhr,status);
					}
				});
			});
		});

		/* キャンセルボタン押下時処理 */
		$('#addTelCancelBtn').click(function(r){
			$('#dialog').dialog({closed: true});
		});
	});
	
	/**
	 * 更新前処理
	 */
	function beforAddTelEdit(){
		
		addTelRow = $('#addTelGrid').datagrid('getSelected');

		/* 入力チェック */
		if(!addTelRow) {
			$.messager.alert(t0001,getMessage(e0002,'設定する電話機'));
			return false;
		}
		
		return true;
	}

	/**
	 * ユーザー追加ダイアログ：検索処理
	 */
	function searchAddTel(){
		
		/* ページを1行目にする */
		$("#addTelGrid").datagrid('options').pageNumber = 1;

		/* 検索 */
		addTelGrid.search('addTel/search', '#addTelForm');
		return false;
	}

	/**
	 * ユーザー追加で渡すパラメータをセットします。
	 */
	function convertAddTelParam(){
		/* ユーザー追加画面のグリッド値を取得 */
		addTelRow = $('#addTelGrid').datagrid('getSelected');

		/* パラメータをセット */
		var params = "";
		params += 'userId=' + userAndTelRow.userId    + "&";
		params += 'telId='  + addTelRow.telId  + "&";
		params += 'lineId=' + addTelRow.lineId + "&";
		params += 'sectionUserId=' + userAndTelRow.sectionUserId + "&";
		params += 'companyUserId=' + userAndTelRow.companyUserId + "&";
		params += 'orgUserId=' + userAndTelRow.userId + '&';
		params += 'orgTelId='  + userAndTelRow.telId   + '&';
		params += 'orgLineId=' + userAndTelRow.lineId  + '&';
		params += 'orgSectionUserId=' + userAndTelRow.sectionUserId + "&";
		params += 'orgCompanyUserId=' + userAndTelRow.companyUserId + "&";

		/* トークンをセット */
		params += '_RequestVerificationToken=' + $("input[name=_RequestVerificationToken]").val();

		return params;
	}

	/**
	 * 拠点（ユーザー追加ダイアログ）選択時処理
	 */
	 function getSectionAddTelList() {
		/* 店部課の変更（ユーザー追加ダイアログ）※定義はuserAndTelSearchに記載されています。 */
		 getSectionList("#addTelCompanySectionId", "#addTelBranchId");
	}


//-->
</script>
<div id="callAddTelDialog" style="padding:10px 20px;" >
	<%-- Header --%>
	<c:import url="/WEB-INF/jsp/common/dialogHeader.jsp">
		<c:param name="title" value="電話機の検索と選択" />
	</c:import>
	<form id="addTelForm" style="margin:0px 5px 0px 10px">
		<input type="hidden" name="orgTelId" id="addTelOrgTelId">
		<table width="860">
			<colgroup>
				<col width="140px" />
				<col width="260px" />
				<col width="120px" />
				<col width="260px" />
				<col width="80px"  />
				<col width="80px"  />
			</colgroup>
			<tr>
				<td>
					<label for="addTelBranchId">拠点</label>
				</td>
				<td>
					<select id="addTelBranchId" name="addTelBranchId" class="branch_select">
						<c:forEach var="branch" items="${ addTelForm.branchList }" varStatus="status">
							<option value="${ branch.value }"><c:out value="${ branch.label }"/></option>
						</c:forEach>
					</select>
				<td>
					<label for="addTelCompanySectionId" >店部課</label>
				</td>
				<td>
					<select id="addTelCompanySectionId" name="addTelCompanySectionId" class="section_select">
						<c:forEach var="section" items="${ addTelForm.sectionList }" varStatus="status">
							<option value="${ section.value }"><c:out value="${ section.label }"/></option>
						</c:forEach>
					</select>
				</td>
				<td>
					<label for="addTelDirectoryNumber">内線番号</label>
				</td>
				<td>
					<input type="text" name="addTelDirectoryNumber" id="addTelDirectoryNumber" class="directory_number_text" maxlength="9">
				</td>
			</tr>
			<tr>
				<td>
					<label for="telStatusId">ステータス</label>
				</td>
				<td>
					<select id="telStatusId" name="telStatus" class="retain_tel_status_select" >
						<c:forEach var="st" items="${ addTelForm.telStatusList }" varStatus="status">
							<option value="${ st.value }"><c:out value="${ st.label }"/></option>
						</c:forEach>
					</select>
				</td>
				<td>
					<label for="addTelChargeAssociationBranchId">課金先</label>
				</td>
				<td>
					<input type="text" id="addTelChargeAssociationBranchId"
						   name="addTelChargeAssociationBranchId"
						   class="charge_association_branch_text"  maxlength="4">
					<span style="text-align:center;">-</span>
					<input type="text" id="addTelChargeAssociationParentSectionId"
						   name="addTelChargeAssociationParentSectionId"
						   class="charge_association_parent_section_text" maxlength="6">
					<span style="text-align:center;">-</span>
					<input type="text" id="addTelChargeAssociationSectionId"
						   name="addTelChargeAssociationSectionId"
						   class="charge_association_section_text" maxlength="6">
					<div id="addTelChargeAssociationBranchId-error"        class="errorMessage"><span></span></div>
					<div id="addTelChargeAssociationParentSectionId-error" class="errorMessage"><span></span></div>
					<div id="addTelChargeAssociationSectionId-error"       class="errorMessage"><span></span></div>
				</td>
				<td align="right" colspan="2">
					<a id="searchAddTelBtn" class="easyui-linkbutton custom-button" iconCls="icon-search" plain="true" >検索</a>
				</td>
			</tr>
		</table>
	</form>
	<div style="margin:10px 0px 5px 0px;">
		<p class="dialog_notes">紐付けする電話機の行をクリックしてください。</p>
		<div id="addTelMessageError" class="errorMessage" style=""><span style="margin-left:100px;"></span></div>
	</div>
	<table id="addTelGrid" class="easyui-datagrid" style="width:890px;height:315px"
	       data-options="rownumbers:true, singleSelect:true, pagination:true, checkOnSelect:false, selectOnCheck:false">
		<thead>
			<tr>
				<th field="telStatusName"     width="80"    align="left"   sortable="true">ステータス</th>
				<th field="phoneClassNm"      width="80"    align="left"   sortable="true">電話機区分</th>
				<th field="directoryNumber"   width="100"   align="left"   sortable="true">内線番号</th>
				<th field="dialin"            width="100"   align="left"   sortable="true">ダイアルイン</th>
				<th field="telTypeModel"      width="100"   align="left"   sortable="true">電話機種</th>
				<th field="macAddress"        width="100"   align="left"   sortable="true">MACアドレス</th>
				<th field="kanjiUserName"     width="120"   align="left"   sortable="true">利用者名</th>
				<th field="branchTelName"     width="260"   align="left"   sortable="true">拠点</th>
				<th field="sectionTelName"    width="260"   align="left"   sortable="true">所属店部課</th>
				<th field="chargeAssociationPlace" width="140"   align="left"   sortable="true">課金先</th>
			</tr>
		</thead>
	</table>
	<div id="addTelBtnArea" align="right">
		<a id="changeTelBtn"      class="easyui-linkbutton custom-button" iconCls="icon-edit"    plain="true" >電話機変更</a>
		<a id="telLinkingUserBtn" class="easyui-linkbutton custom-button" iconCls="icon-add"     plain="true" >電話機追加</a>
		<a id="addTelCancelBtn"   class="easyui-linkbutton custom-button" iconCls="icon-cancel " plain="true" >キャンセル</a>
	</div>
</div>