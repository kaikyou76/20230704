<%@page contentType="text/html" pageEncoding="UTF-8" errorPage="/jsp/error" %>
<jsp:directive.include file="/WEB-INF/jsp/common/taglibs.jsp"/>
<%@ page import="jp.co.netmarks.common.*" %>
<!doctype html>
<html>
<head>
	<c:import url="/WEB-INF/jsp/common/htmlHeader.jsp"/>
	<script type="text/javascript" src="${ pageContext.request.contextPath }/js/jquery.gridutil.js"></script>
	<script type="text/javascript" src="${ pageContext.request.contextPath }/js/jquery.upload-1.0.2.js"></script>
	<script type="text/javascript">
	<!--
		/* 親画面の選択行データ */
		var selectedRow;
		/* グリッド */
		var grid;
		/* ダミーデータ */
		var INPUT_DATA_STRING_NUMBER      = <%= Constants.INPUT_DATA_STRING_NUMBER %>
		var INPUT_DATA_BRANCH_COMPANY_ID  = <%= Constants.INPUT_DATA_BRANCH_COMPANY_ID %>
		var INPUT_DATA_SECTION_ID         = <%= Constants.INPUT_DATA_SECTION_ID %>

		/**
		 * onload処理
		 */
		$(function() {

			/* 初期フォーカス */
			$("#" + "${ maintenanceSearchForm.focus }").focus();

			/* Grid用ライブラリを設定 */
			grid = $("#grid").gridutil({
				'syncWidthPanel': '.searchCondition',
				'resizeHeight' : false
			});


			/* 検索ボタン押下時イベントを設定 */
			$('#searchBtn').click(search);

			/* CUCMマスタパラメータの取得ボタン押下時イベントを設定 */
			$('#getCucmParamBtn').click(getCucmParam);

			/* 双方向チェック用データの取得ボタン押下時イベントを設定 */
			$('#getCheckDataBtn').click(getCheckData);

			/* 取込ボタン押下時イベントを設定 */
			$('#csvFetchBtn').click(csvFetch);

			/* データ移行ボタン押下時イベントを設定 */
			$('#dataShiftBtnId').click(dataShift);
			
			/* 拠点プルダウンイベント */
			$('#branchUserId').change(getSectionUserList);

			/* 拠点追加ボタン押下時処理 */
			$("#addBranchBtn").click(function() {
				/* ダイアログ表示処理 */
				$('#dialog').dialog({
				    title  : '拠点追加',
				    width  : 330,
				    height : 250,
				    closed : false,
				    cache  : false,
				    modal  : true,
				    href   : 'addBranch/index'
				});
			});

			/* 拠点変更ボタン押下時処理 */
			$("#updateBranchBtn").click(function() {
				selectedRow = $('#grid').datagrid('getSelected');

				/* 選択 */
				if(!selectedRow) {
					$.messager.alert(t0001,getMessage(e0002,'更新する拠点'));
					return false;
				}

				/* ダイアログ表示処理 */
				$('#dialog').dialog({
				    title  : '拠点変更',
				    width  : 330,
				    height : 250,
				    closed : false,
				    cache  : false,
				    modal  : true,
				    href   : 'updateBranch/index'
				});
			});

			/* 拠点を削除ボタン押下時処理 */
			$("#deleteBranchBtn").click(function() {
				selectedRow = $('#grid').datagrid('getSelected');

				/* 選択 */
				if(!selectedRow) {
					$.messager.alert(t0001,getMessage(e0002,'削除する拠点'));
					return false;
				}

				/* コンフィグ */
				$.messager.confirm(t0003, getMessage(c0004,'拠点削除'), function (r) {
					if (!r) return false;

					/* 2度押しチェック */
					if(!grid.clickEventCheck())return false;


					/* loading画像表示*/
					$("#grid").datagrid('loading');

					$.ajax({
						type : "POST",
						url : "maintenance/deleteBranch",
						data : convertGridParam(selectedRow),
						success : function (data, status, xhr) {

							/* クリック可能処理 */
							grid.unClickEvent();

							/* loading画像非表示 */
							$("#grid").datagrid('loaded');

							/* エラーメッセージが含まれる場合 */
							if (data['errors']) {
								if (data['errors']['defaultMessage'] != '') {
									$.messager.alert(t0001,data['errors'][0]['defaultMessage']);
								}
							} else {
								/* グリッドをリロードする */
								$("#grid").datagrid('reload');
								$.messager.alert(t0002,data["maintenanceUpdateForm"].message);
								/* 拠点プルダウン再取得 */
								getBranchList();
							}

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

			/* 拠点-店部課紐付き作成ボタン押下時処理 */
			$("#addAssociateBtn").click(function() {
				selectedRow = $('#grid').datagrid('getSelected');

				/* 選択 */
				if(!selectedRow) {
					$.messager.alert(t0001,getMessage(e0002,'紐付を作成する拠点'));
					return false;
				}

				/* ダイアログ表示処理 */
				$('#dialog').dialog({
				    title  : '拠点-店部課紐付作成',
				    width  : 330,
				    height : 250,
				    closed : false,
				    cache  : false,
				    modal  : true,
				    href   : 'addAssociate/index'
				});

			});

			/* 拠点-店部課紐付き削除ボタン押下時処理 */
			$("#deleteAssociateBtn").click(function() {
				selectedRow = $('#grid').datagrid('getSelected');

				/* 選択 */
				if(!selectedRow) {
					$.messager.alert(t0001,getMessage(e0002,'紐付を削除する拠点'));
					return false;
				}

				/* 選択行の店部課コード */
				if(!selectedRow.sectionId) {
					$.messager.alert(t0001,getMessage(e0003,'店部課'));
					return false;
				}

				/* コンフィグ */
				$.messager.confirm(t0003, getMessage(c0004,'拠点-店部課の紐付を削除'), function (r) {
					if (!r) return false;

					/* 2度押しチェック */
					if(!grid.clickEventCheck())return false;

					/* loading画像表示*/
					$("#grid").datagrid('loading');

					$.ajax({
						type : "POST",
						url : "maintenance/deleteAssociate",
						data : convertGridParam(selectedRow),
						success : function (data, status, xhr) {

							/* クリック可能処理 */
							grid.unClickEvent();

							/* loading画像非表示 */
							$("#grid").datagrid('loaded');

							/* エラーメッセージが含まれる場合 */
							if (data['errors']) {
								if (data['errors']['defaultMessage'] != '') {
									$.messager.alert(t0001,data['errors'][0]['defaultMessage']);
								}
							} else {
								/* グリッドをリロードする */
								$("#grid").datagrid('reload');
								$.messager.alert(t0002,data["maintenanceUpdateForm"].message);
							}

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



		});

		/**
		 * 検索処理
		 */
		function search() {

			/* ページを1行目にする */
			$("#grid").datagrid('options').pageNumber = 1;

			/* 検索 */
			grid.search('maintenance/search', '#SearchForm');
			return false;
		}

		/**
		 * 取込
		 */
		function csvFetch() {

			/* 取込みファイルを選択しているかチェックする */
			if(!$("#csvFile")[0].value) {
				$.messager.alert(t0001,getMessage(e0002,'取込むCSVファイル'));
				return false;
			}

			/* 2度押しチェック */
			if(!grid.clickEventCheck())return false;

			var param = 'tableName=' + $("#tableName").val();

			/* CSVファイル取込み*/
			$("#csvFile").upload('maintenance/fetchTableData',param, function(res) {
					/* クリック可能処理 */
					grid.unClickEvent();

					if(res["errorMessage"]) {
						$.messager.alert(t0001,res["errorMessage"]);
					} else {
						$.messager.alert(t0002,res["sucsessMessage"]);
					}
			},'json');
			return false;
		}

		/**
		 * CUCMマスタパラメータ取得処理
		 */
		 function getCucmParam() {
			 /* 選択 */
			$.messager.confirm(t0003, getMessage(c0004,'CUCMマスタパラメータを取得'), function (r) {
				if (!r) return false;

				/* 2度押しチェック */
				if(!grid.clickEventCheck())return false;

				$.ajax({
					type : "POST",
					url : "maintenance/getCucmParam",
					data : "",
					success : function (data, status, xhr) {

						/* クリック可能処理 */
						grid.unClickEvent();

						$.messager.alert(t0002,data["message"]);
					},
					error : function (xhr, status) {

						/* エラー処理 */
						ajaxError(xhr,status);
					}
				});
			});
		}

		/**
		* 双方向チェック用データ取得処理
		*/
		function getCheckData() {
			 /* 選択 */
			$.messager.confirm(t0003, getMessage(c0004,'双方向チェック用データを取得'), function (r) {
				if (!r) return false;

				/* 2度押しチェック */
				if(!grid.clickEventCheck())return false;

				$.ajax({
					type : "POST",
					url : "maintenance/getCheckData",
					data : "",
					success : function (data, status, xhr) {

						/* クリック可能処理 */
						grid.unClickEvent();

						$.messager.alert(t0002,data["message"]);
					},
					error : function (xhr, status) {

						/* エラー処理 */
						ajaxError(xhr,status);
					}
				});
			});
		}

		/**
		 * 拠点プルダウン再取得処理
		 */
		function getBranchList(){
			$.ajax({
				type : "POST",
				url : "maintenance/getBranchList",
				success : function (data, status, xhr) {
					 if (data["data"]) {
						 /* プルダウンの入れ替え */
						 changeSelector("#searchBranchId",data["data"]);

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
		 * 拠点（ユーザー）選択時処理
		 */
		function getSectionUserList() {
			/* 店部課（ユーザー）の変更 */
			getSectionList("#sectionUserId" ,"#branchUserId");
		}

		/**
		 * 拠点プルダウン変更処理
		 * 概要：拠点プルダウン変更時に、該当の店部課の選択値を変更します。
		 */
		function getSectionList(targetId, eventId){
			$.ajax({
				type : "POST",
				url : "authManagement/getAuthSectionList?id=" + $(eventId).val(),
				success : function (data, status, xhr) {
					 if (data["data"]) {
						 /* プルダウンの入れ替え */
						 if(data["data"][0]){
							 changeSelector(targetId,data["data"]);
						 } else {
							 changeSelector(targetId,[{'label':ALL_LABEL,'value':''}]);
						 }

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
		 * データ移行
		 */
		function dataShift() {

			/* 取込みファイルを選択しているかチェックする */
			if(!$("#csvDataShiftFileId")[0].value) {
				$.messager.alert(t0001,getMessage(e0002,'移行するファイル'));
				return false;
			}

			/* CSVファイル取込み*/
			$.messager.confirm(t0003, getMessage(c0004,'データ移行'), function (r) {
				if (!r) return false;

				/* 2度押しチェック */
				if(!grid.clickEventCheck())return false;

				$("#csvDataShiftFileId").upload('maintenance/dataShift',function(res) {
					/* クリック可能処理 */
					grid.unClickEvent();

					if(res){
						if(res["errorMessage"]) {
							$.messager.alert(t0001,res["errorMessage"]);
						} else{
							$.messager.alert(t0002,res["successMessage"]);
						}
					}
				},'json');
			});
		}

		/**
		 * ダイアログから渡すパラメータをセットします。
		 */
		function convertDialogParam(branchId,branchName,clusterId,companyId,sectionId){

			/* パラメータをセット */
			var params = "";
			params += 'branchId='  + branchId    + "&";
			params += 'branchName='+ branchName  + "&";
			params += 'clusterId=' + clusterId   + "&";
			params += 'companyId=' + companyId   + '&';
			params += 'sectionId=' + sectionId;

			return params;
		}

		/**
		 * Gridから渡すパラメータをセットします。
		 */
		function convertGridParam(selectedRow){
			var companyId;
			var sectionId;

			if(selectedRow.companyId) {
				companyId = selectedRow.companyId;
			} else {
				companyId = INPUT_DATA_BRANCH_COMPANY_ID
			}

			if(selectedRow.sectionId) {
				sectionId = selectedRow.sectionId;
			} else {
				sectionId = INPUT_DATA_SECTION_ID
			}


			/* パラメータをセット */
			var params = "";
			params += 'branchId='  + selectedRow.branchId    + "&";
			params += 'branchName='+ selectedRow.branchName  + "&";
			params += 'clusterId=' + selectedRow.clusterId   + "&";
			params += 'companyId=' + companyId   + '&';
			params += 'sectionId=' + sectionId;

			return params;
		}

		/**
		 * エラー属性の初期化(ラベル用)
		 */
		function removeErrLabel(elementId){
			$('#' + elementId).removeClass("validateColor-invalid")
			   .unbind("mouseenter").unbind("mouseleave");
		}


	-->
	</script>
</head>
<body>

<%-- Header --%>
<c:import url="/WEB-INF/jsp/common/header.jsp">
	<c:param name="title" value="メンテナンス" />
	<c:param name="infoDisplay" value="true" />
</c:import>
<div id="contents" class="container" style="min-width:955px;">
	<div id="searchCondition" class="easyui-panel searchCondition" title="検索条件" iconCls="icon-search" collapsible="true"
		 style="background:#fafafa;padding:5px;" >
		<form id="SearchForm">

			<input type="submit" style="position: absolute; top: 0px; left: 0px; visibility: hidden;" />
			<table class="narrow_table">
				<colgroup>
					<col width="20">
					<col width="35" style="padding:0px;">	<!-- 1 -->
					<col width="270">	<!-- 2 -->
					<col width="100">	<!-- 3 -->
					<col width="530">	<!-- 4 -->
				</colgroup>
				<tr>
					<td></td>
					<td>
						<label for="branchId" >拠点</label>
					</td>
					<td>
						<select id="searchBranchId" name="branchId" class="branch_select">
							<c:forEach var="branch" items="${ maintenanceSearchForm.branchList }" varStatus="status">
								<option value="${ branch.value }"><c:out value="${ branch.label }"/></option>
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
	<div class="panel-bottom"></div>
	<form id="gridForm">
		<!-- 一覧 -->
		<table id="grid" class="easyui-datagrid " style="height:350px" toolbar="#toolbar"
			   rownumbers="true"  pagination="true" pageSize="50" striped="true" singleSelect="true" checkOnSelect="false" selectOnCheck="false">
			<thead>
				<tr>
					<th field="branchId"       width="80"  align="center" sortable="true">拠点コード</th>
					<th field="branchName"     width="260" align="left"   sortable="true" >拠点名</th>
					<!-- <th field="clusterName"    width="80"  align="center" sortable="true">クラスタ</th> -->
					<th field="companyId"      width="80"  align="center" sortable="true">会社コード</th>
					<th field="sectionId"      width="80"  align="center" sortable="true">店部課コード</th>
					<th field="sectionName"    width="260" align="left"   sortable="true">店部課名</th>
				</tr>
			</thead>
		</table>
		<div id="toolbar">
			<a id="addBranchBtn"       href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true">拠点追加</a>
			<a id="updateBranchBtn"    href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true">拠点変更</a>
			<a id="deleteBranchBtn"    href="#" class="easyui-linkbutton" iconCls="icon-no" plain="true">拠点を削除</a>
			<a id="addAssociateBtn"    href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true">拠点-店部課紐付作成</a>
			<a id="deleteAssociateBtn" href="#" class="easyui-linkbutton" iconCls="icon-no" plain="true">拠点-店部課紐付削除</a>
			<div id="maintenanceMessageError" class="errorMessage" style="display:inline;float:right;"><span style="margin-left:100px;"></span></div>
		</div>
	</form>
	<div class="panel-bottom"></div>
	<%-- バッチ起動 --%>
	<div id="excuteBatch" class="easyui-panel searchCondition" title="除外データ取込"
		 style="background:#fafafa;padding:5px;" collapsible="true" >
		 <%-- 
		<form id="excuteBatchForm">
			<a id="getCucmParamBtn" href="javascript:void(0)" class="easyui-linkbutton custom-button" plain="true">CUCMマスタパラメータの取得</a>
			<a id="getCheckDataBtn" href="javascript:void(0)" class="easyui-linkbutton custom-button" plain="true">双方向チェック用データの取得</a>
		</form>
		 --%>

		<div id="csvFileDiv">
			<label for="tableName">テーブル選択</label>
			<select id="tableName" name="tableName" style="width: 160px;">
				<c:forEach var="table" items="${ maintenanceSearchForm.tableList }" varStatus="status">
					<option value="${ table.value }"><c:out value="${ table.label }"/></option>
				</c:forEach>
			</select>
			ファイル選択：
			<input id="csvFile" name="csvFile" type="file"  maxlength="1" accept="csv"/>
			<a id="csvFetchBtn" href="#" class="easyui-linkbutton" iconCls="icon-excel" plain="true">取込</a>
		</div>
	</div>
	<div class="panel-bottom"></div>
	<%-- データ移行 --%>
	<div id="dataShift" class="easyui-panel searchCondition" title="連携用ファイル取込" style="background:#fafafa;padding:5px;" collapsible="true" >
		<div id="csvDataShiftFileDivId" style="float: left;">
			<span>ファイル選択：</span>
			<input id="csvDataShiftFileId" name="csvDataShiftFile" type="file" accept="text/plain"/>
			<a id="dataShiftBtnId" href="javascript:void(0)" class="easyui-linkbutton custom-button" iconCls="icon-excel" plain="true" style="margin-left: 10px;">取込</a>
		</div>
	</div>
	<div class="panel-bottom"></div>
	<%-- データエクスポート --%>
	<div id="dataShift" class="easyui-panel searchCondition" title="連携用ファイル出力" style="background:#fafafa;padding:5px;" collapsible="true" >
		<form id="outputForm">

			<input type="submit" style="position: absolute; top: 0px; left: 0px; visibility: hidden;" />
			<table class="narrow_table">
				<colgroup>
					<col width="190">	<!-- 1 -->
					<col width="60">	<!-- 2 -->
					<col width="270">	<!-- 3 -->
					<col width="60">	<!-- 4 -->
					<col width="270">	<!-- 5 -->
					<col width="100">	<!-- 6 -->
				</colgroup>
				<tr>
					<td>
						<label class="table_lbl">CUCM、オフィスリンク連携用：</label>
					</td>
					<td>
						<label for="selectTarget" >出力範囲</label>
					</td>
					<td>
						<select id="selectTarget" name="selectTarget" class="branch_select">
							<option value="1"><c:out value="更新対象のみ"/></option>
							<option value="2"><c:out value="全件対象"/></option>
						</select>
					</td>
					<td colspan="2">
						<a id="dataShiftBtnId" class="easyui-linkbutton custom-button" plain="true" >ファイル出力</a>
					</td>
				</tr>
				<tr>
					<td>
						<label class="table_lbl">課金一覧：</label>
					</td>
					<td>
						<label for="branchChargeId" >拠点</label>
					</td>
					<td>
						<select id="branchChargeId" name="branchChargeId" class="branch_select">
							<c:forEach var="branch" items="${ maintenanceSearchForm.branchList }" varStatus="status">
								<option value="${ branch.value }"><c:out value="${ branch.label }"/></option>
							</c:forEach>
						</select>
					</td>
					<td>
						<label for="sectionChargeId" >店部課</label>
					</td>
					<td>
						<select id="sectionChargeId" name="sectionChargeId" class="section_select">
							<c:forEach var="section" items="${ outputForm.sectionChargeList }" varStatus="status">
								<option value="${ section.value }"><c:out value="${ section.label }"/></option>
							</c:forEach>
						</select>
					</td>
					<td>
						<a id="dataShiftBtnId" class="easyui-linkbutton custom-button" plain="true" >課金一覧出力</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div class="panel-bottom"></div>
	<%-- ユーザー設定 --%>
	<div id="dataShift" class="easyui-panel searchCondition" title="ユーザー設定" style="background:#fafafa;padding:5px;" collapsible="true" >
		<div id="csvDataShiftFileDivId" style="float: left;">
			<span>パスワード有効期限（日数）：</span>
			<input id="csvDataShiftFileId" name="csvDataShiftFile">
			<span>パスワード世代数：</span>
			<input id="csvDataShiftFileId" name="csvDataShiftFile">
			<span>認証失敗回数：</span>
			<input id="csvDataShiftFileId" name="csvDataShiftFile">
			<a id="dataShiftBtnId" href="javascript:void(0)" class="easyui-linkbutton custom-button" plain="true" style="margin-left: 10px;">更新</a>
		</div>
	</div>
	<div class="panel-bottom"></div>
	<%-- 共用名マスタ設定 --%>
	<div id="dataShift" class="easyui-panel searchCondition" title="共用名マスタ設定" style="background:#fafafa;padding:5px;" collapsible="true" >
		<div id="csvDataShiftFileDivId" style="float: left;">
			<a id="dataShiftBtnId" href="javascript:void(0)" class="easyui-linkbutton custom-button" plain="true" style="margin-left: 10px;">マスタ出力</a>
			<span>ファイル選択：</span>
			<input id="csvDataShiftFileId" name="csvDataShiftFile" type="file" accept="text/plain"/>
			<a id="dataShiftBtnId" href="javascript:void(0)" class="easyui-linkbutton custom-button" iconCls="icon-excel" plain="true" style="margin-left: 10px;">取込</a>
		</div>
	</div>
	<br/>
	<br/>
</div>
<%-- dialog --%>
<div id="dialog"></div>
</body>
</html>