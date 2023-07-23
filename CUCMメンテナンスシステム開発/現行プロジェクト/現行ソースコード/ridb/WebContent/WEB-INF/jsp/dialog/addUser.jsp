<%@page contentType="text/html" pageEncoding="UTF-8" %>

<script type="text/javascript">
<!--

	/* 親画面の選択行データ */
	var userAndTelRow;
	var addUserRow;
	/* グリッド */
	var addUserGrid;

	/* プルダウンのイベント情報のセット */
	$(function() {
		
		/* サブミット */
		$('#addUserForm').submit(searchAddUser);
		
		/* フォーカスセット */
		$("#addUserKanaUserNameId").focus();
		
		/* GridUtilの初期化 */
		addUserGrid = $("#addUserGrid").gridutil({
			'resizeHeight' : false,
			'resizeWidth'  : false
		});

		/* 親画面の選択行データをセット */
		userAndTelRow = $('#grid').datagrid('getSelected');

		/* 親で選択したユーザ情報をセット */
		$("#addUserOrgUserId").val(userAndTelRow.userId);
		$("#addUserOrgCompanyUserId").val(userAndTelRow.companyUserId);
		$("#addUserOrgSectionUserId").val(userAndTelRow.sectionUserId);

		/* 検索ボタン押下時処理 */
		$('#searchAddUserBtn').click(searchAddUser);

		/* 拠点プルダウン変更 */
		$('#addUserBranchId').change(getSectionAddUserList);

		/* ユーザー変更ボタン押下時処理 */
		$("#changeUserBtn").click(function(r){

			/* 更新前処理 */
			if(!beforAddUserEdit()) return false;

			/* コンフィグ */
			$.messager.confirm(t0003, getMessage(c0004,'ユーザー変更'), function (r) {
				if (!r) return false;
				
				/* 2度押しチェック */
				if(!addUserGrid.clickEventCheck())return false;

				/* loading画像表示、ボタンを押下不可にする */
				$("#addUserGrid").datagrid('loading');
				$("#addUserBtnArea").attr("disabled", true);

				$.ajax({
					type : "POST",
					url : "addUser/updateUser",
					data : convertAddUserParam(),
					success : function (data, status, xhr) {
						/* クリック可能処理 */
						addUserGrid.unClickEvent();

						/* loading画像非表示、ボタンを押下可にする */
						$("#addUserBtnArea").attr("disabled", false);

						/* 正常処理 */
						ajaxUpdateSuccessDialog(data, status, xhr,"dialog", "addUserGrid", "grid", "addUserUpdateForm", "addUserMessageError");
					},
					error : function (xhr, status) {
						/* クリック可能処理 */
						addUserGrid.unClickEvent();
						
						/* loading画像非表示 */
						$("#addUserGrid").datagrid('loaded');

						/* エラー処理 */
						ajaxError(xhr,status);
					}
				});
			});
		});

		/* ユーザー追加ボタン押下時処理 */
		$("#userLinkingTelBtn").click(function(r){

			/* 更新前処理 */
			if(!beforAddUserEdit()) return false;
			
			/* コンフィグ */
			$.messager.confirm(t0003, getMessage(c0004,'ユーザー追加'), function (r) {
				if (!r) return false;
				
				/* 2度押しチェック */
				if(!addUserGrid.clickEventCheck())return false;

				/* loading画像表示、ボタンを押下不可にする */
				$("#addUserGrid").datagrid('loading');
				$("#addUserBtnArea").attr("disabled", true);

				$.ajax({
					type : "POST",
					url : "addUser/userLinkingTel",
					data : convertAddUserParam(),
					success : function (data, status, xhr) {
						/* クリック可能処理 */
						addUserGrid.unClickEvent();

						/* ボタンを押下可にする */
						$("#addUserBtnArea").attr("disabled", false);

						/* 正常処理 */
						ajaxUpdateSuccessDialog(data, status, xhr,"dialog", "addUserGrid", "grid", "addUserUpdateForm", "addUserMessageError");
					},
					error : function (xhr, status) {
						/* クリック可能処理 */
						addUserGrid.unClickEvent();

						/* loading画像非表示 */
						$("#addUserGrid").datagrid('loaded');

						/* エラー処理 */
						ajaxError(xhr,status);
					}
				});
			});
		});

		/* キャンセルボタン押下時処理 */
		$('#addUserCancelBtn').click(function(r){
			$('#dialog').dialog({closed: true});
		});

	});
	
	/**
	 * 更新前処理
	 */
	function beforAddUserEdit(){
		
		addUserRow = $('#addUserGrid').datagrid('getSelected');

		/* 選択 */
		if(!addUserRow) {
			$.messager.alert(t0001,getMessage(e0002,'設定するユーザー'));
			return false;
		}
		
		return true;
	}

	/**
	 * ユーザー追加ダイアログ：検索処理
	 */
	function searchAddUser(){

		/* ページを1行目にする */
		$("#addUserGrid").datagrid('options').pageNumber = 1;

		/* 検索 */
		addUserGrid.search('addUser/search', '#addUserForm');
		return false;
	}

	/**
	 * 拠点（ユーザー追加ダイアログ）選択時処理
	 */
	 function getSectionAddUserList() {
		/* 店部課の変更（ユーザー追加ダイアログ）※定義はuserAndTelSearch.jspに記載されています。 */
		 getSectionList("#addUserCompanySectionId", "#addUserBranchId");
	}

	/**
	 * ユーザー追加で渡すパラメータをセットします。
	 */
	function convertAddUserParam(){
		/* ユーザー追加画面のグリッド値を取得 */
		addUserRow = $('#addUserGrid').datagrid('getSelected');

		/* パラメータをセット */
		var params = "";
		params += 'userId=' + addUserRow.userId    + "&";
		params += 'telId='  + userAndTelRow.telId  + "&";
		params += 'lineId=' + userAndTelRow.lineId + "&";
		params += 'sectionUserId=' + addUserRow.sectionId + "&";
		params += 'companyUserId=' + addUserRow.companyId + "&";
		params += 'orgUserId=' + userAndTelRow.userId  + '&';
		params += 'orgTelId='  + userAndTelRow.telId   + '&';
		params += 'orgLineId=' + userAndTelRow.lineId  + '&';
		params += 'orgSectionUserId=' + userAndTelRow.sectionUserId + "&";
		params += 'orgCompanyUserId=' + userAndTelRow.companyUserId + "&";
		/* トークンをセット */
		params += '_RequestVerificationToken=' + $("input[name=_RequestVerificationToken]").val();

		return params;
	}

//-->
</script>
<jsp:directive.include file="/WEB-INF/jsp/common/taglibs.jsp"/>
<div id="callAddUserDialog" style="padding:10px 20px;" >
	<%-- Header --%>
	<c:import url="/WEB-INF/jsp/common/dialogHeader.jsp">
		<c:param name="title" value="ユーザーの検索と選択" />
	</c:import>
	<div>
		<form id="addUserForm" style="margin:0px 5px 0px 10px">
			<%-- 選択元のユーザー情報 --%>
			<input type="hidden" name="orgUserId" id="addUserOrgUserId">
			<input type="hidden" name="orgSectionUserId" id="addUserOrgSectionUserId">
			<input type="hidden" name="orgCompanyUserId" id="addUserOrgCompanyUserId">
			<table width="860">
				<colgroup>
					<col width="140px"  />
					<col width="260px" />
					<col width="120px" />
					<col width="260px" />
					<col width="80px"  />
					<col width="80px" />
				</colgroup>
				<tr>
					<td>
						<label for="addUserKanaUserNameId">ユーザー名(カナ)</label>
					</td>
					<td>
						<input type="text" id="addUserKanaUserNameId" name="addUserKanaUserName" class="kana_user_name_text" maxlength="101">
					</td>
					<td>
						<label for="retainTelStatusId">ステータス</label>
					</td>
					<td>
						<select id="retainTelStatusId" name="retainTelStatus" class="retain_tel_status_select" >
							<c:forEach var="st" items="${ addUserForm.retainTelStatusList }" varStatus="status">
								<option value="${ st.value }"><c:out value="${ st.label }"/></option>
							</c:forEach>
						</select>
					</td>
					<td colspan="2" align="right">
						<a id="searchAddUserBtn" class="easyui-linkbutton custom-button" iconCls="icon-search" plain="true" >検索</a>
					</td>
				</tr>
				<tr>
					<td>
						<label for="addUserBranchId">拠点</label>
					</td>
					<td>
						<select id="addUserBranchId" name="addUserBranchId" class="branch_select">
							<c:forEach var="branch" items="${ addUserForm.branchList }" varStatus="status">
								<option value="${ branch.value }"><c:out value="${ branch.label }"/></option>
							</c:forEach>
						</select>
					</td>
					<td>
						<label for="addUserCompanySectionId" >店部課</label>
					</td>
					<td>
						<select id="addUserCompanySectionId" name="addUserCompanySectionId" class="section_select">
							<c:forEach var="section" items="${ addUserForm.sectionList }" varStatus="status">
								<option value="${ section.value }"><c:out value="${ section.label }"/></option>
							</c:forEach>
						</select>
					</td>
					<td>
						<label for="shozokuka">課名</label>
					</td>
					<td>
						<input type="text" name="addUserAttachSectionName" id="addUserAttachSectionNameId" maxlength="257" class="attach_section_name_text" >
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div  style="margin:10px 0px 5px 0px;">
		<p class="dialog_notes">紐付けするユーザーの行をクリックしてください。</p>
		<div id="addUserMessageError" class="errorMessage" style="display:inline;float:left;"><span style="margin-left:100px;"></span></div>
	</div>
	<table id="addUserGrid" class="easyui-datagrid" style="width:890px;height:315px"
	       data-options="rownumbers:true, singleSelect:true, pagination:true, checkOnSelect:false, selectOnCheck:false">
		<thead>
			<tr>
				<th field="retainTelStatusName" width="100"   align="left"   sortable="true">ステータス</th>
				<th field="userKanjiName"       width="240"   align="left"   sortable="true">利用者名</th>
				<th field="sectionName"         width="260"   align="left"   sortable="true">所属店部課</th>
			</tr>
		</thead>
	</table>
	<div id="addUserBtnArea" align="right">
		<a id="changeUserBtn"      class="easyui-linkbutton custom-button" iconCls="icon-edit"    plain="true" >ユーザー変更</a>
		<a id="userLinkingTelBtn"  class="easyui-linkbutton custom-button" iconCls="icon-add"     plain="true" >ユーザー追加</a>
		<a id="addUserCancelBtn"   class="easyui-linkbutton custom-button" iconCls="icon-cancel " plain="true" >キャンセル</a>
	</div>
</div>