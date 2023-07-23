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
		/* Grid用 */
		var logWebGrid;
		var logBatchGrid;
		var exportWebGrid;
		var exportBatchBkGrid;
		var exportExCoopGrid;
		var exportLineListGrid;

		/**
		 * onload処理
		 */
		$(function() {

			/* 初期フォーカス */
			$("#" + "${ logManagementSearchForm.focus }").focus();

			/* Grid用ライブラリを設定 */
			logWebGrid          = $("#logWebGrid").gridutil({
				'resizeHeight'  : false,
				'syncWidthPanel': '.searchCondition'});
			logBatchGrid        = $("#logBatchGrid").gridutil({
				'resizeHeight'  : false,
				'syncWidthPanel': '.gridPanel'});
			exportWebGrid       = $("#webGrid").gridutil({
				'resizeHeight'  : false});
			exportBatchBkGrid   = $("#bkGrid").gridutil({
				'resizeHeight'  : false});
			exportExCoopGrid    = $("#exCoopGrid").gridutil({
				'resizeHeight'  : false});
			exportLineListGrid  = $("#lineListGrid").gridutil({
				'resizeHeight'  : false});

			/* フォルダのリストを取得ボタン押下時イベントを設定 */
			$('#getLogWebBtn').click(getFileList);
			$('#getLogBatchBtn').click(getFileList);
			$('#getWebBtn').click(getFileList);
			$('#getBkBtn').click(getFileList);
			$('#getExCoopBtn').click(getFileList);
			$('#getLineListBtn').click(getFileList);

			$('input[type="text"]').keypress(function(e){
				   if ((e.which && e.which == 13) || (e.keyCode && e.keyCode == 13)) {
				     return false;
				   }else{
				     return true;
				   }
		    });

		});

		/**
		 * ファイルリスト取得処理
		 */
		 function getFileList(){

			var dirDiv = this.parentNode.dirDiv.value;
			var searchFormId = this.parentNode.id;
			var grid;

			/* 表示するGridを設定 */
			switch(dirDiv){
				case "<%= Constants.DIR_LOG_WEB %>"              :
					$("#logWebGrid").datagrid('options').pageNumber = 1;
					grid = logWebGrid;
					break;
				case "<%= Constants.DIR_LOG_BATCH %>"            :
					$("#logBatchGrid").datagrid('options').pageNumber = 1;
					grid = logBatchGrid;
					break;
				case "<%= Constants.DIR_EXPORT_WEB %>"           :
					$("#webGrid").datagrid('options').pageNumber = 1;
					grid = exportWebGrid;
					break;
				case "<%= Constants.DIR_EXPORT_BATCH_BK %>"      :
					$("#bkGrid").datagrid('options').pageNumber = 1;
					grid = exportBatchBkGrid;
					break;
				case "<%= Constants.DIR_EXPORT_BATCH_EXCOOP %>"  :
					$("#exCoopGrid").datagrid('options').pageNumber = 1;
					grid = exportExCoopGrid;
					break;
				case "<%= Constants.DIR_EXPORT_BATCH_LINELIST %>":
					$("#lineListGrid").datagrid('options').pageNumber = 1;
					grid = exportLineListGrid;
					break;
				default:grid = null;break;
			}

			if(grid){
				/* ファイルリスト取得 */
				grid.search('logManagement/getFileList', '#' + searchFormId,function(r){
					 $('.LogLink').click(downloadFile);
				 });
			}
			 return false;
		}

		 /**
		 * ファイルダウンロード処理
		 */
		function downloadFile(){
			$('#hdnFileName')[0].value = this.innerHTML;
			$('#hdnDirDiv')[0].value = this.name;

	 		$('#downloadForm').submit();
		}

		function formatterDlNm(value, row){
			return "<a href='javascript:void(0);' name='"+ row.dirDiv +"' class='LogLink'>" + value + "</a>";
		}
	-->

	</script>
</head>
<body>
<%-- Header --%>
<c:import url="/WEB-INF/jsp/common/header.jsp">
	<c:param name="title" value="ログ管理" />
	<c:param name="infoDisplay" value="true" />
</c:import>
<div id="contents" class="container" style="min-width:955px">
	<form id="downloadForm" action="logManagement/downloadFile" method="post" >
		<input type="hidden" id="hdnFileName" name="hdnFileName"/>
		<input type="hidden" id="hdnDirDiv" name="hdnDirDiv"/>
	</form>

	<div class="easyui-panel searchCondition" style="background:#fafafa;padding:5px;" >
		<form id="logWebSearchForm">

			<input type="submit" style="position: absolute; top: 0px; left: 0px; visibility: hidden;" />
			更新日 : <input id="txtLogWeb" name="updateDate" type="date" class="file_update_date_text"/>
			<input type="hidden"  name="dirDiv" value="<%= Constants.DIR_LOG_WEB %>"/>
			～ <input id="txtLogWeb" name="updateDate" type="text" class="file_update_date_text"/>
			<input type="hidden"  name="dirDiv" value="<%= Constants.DIR_LOG_WEB %>"/>
			<a id="getLogWebBtn" href="javascript:void(0)" class="easyui-linkbutton custom-button" iconCls="icon-mini-refresh" plain="true" >フォルダのリストを取得</a>
			<a id="getLogWebBtn" href="javascript:void(0)" class="easyui-linkbutton custom-button" plain="true" style="float: right;" >ダウンロード</a>
		</form>
	</div>
	<div class="panel-bottom"></div>
	<div id="logWebList" class="easyui-panel gridPanel" title="Webログ" collapsible="true" data-collapsed="true"
	 style="background:#fafafa;padding:5px;" >
		<form id="logWebGridForm" name="gridForm" >
			<table id="logWebGrid" class="easyui-datagrid " style="height:310px;min-width:978px;"
				   rownumbers="true"  pagination=true  striped="true" singleSelect="true" checkOnSelect="true" selectOnCheck="true">
				<thead>
					<tr>
						<th field="ck"         width="20"  align="center" checkbox="true" ></th>
						<th field="fileName"   width="500" align="left"   sortable="true" data-options="formatter:formatterDlNm">ファイル名</th>
						<th field="fileSize"   width="100" align="left"   sortable="true">サイズ(bytes)</th>
						<th field="updateDate" width="200" align="left"   sortable="true">更新日</th>
					</tr>
				</thead>
			</table>
		</form>
	</div>
	<br><br>
	<div  class="easyui-panel searchCondition" style="background:#fafafa;padding:5px;" >
		<form id="logBatchSearchForm">
			<%-- ページング関連を初期化 --%>
			<input type="hidden" name="start" value="1" />
			<input type="hidden" name="end" value="50" />
			<input type="hidden" name="page" value="1" />
			<input type="hidden" name="rows" value="1" />
			<input type="hidden" name="sort" value="" />
			<input type="hidden" name="order" value="" />
			<input type="submit" style="position: absolute; top: 0px; left: 0px; visibility: hidden;" />
			更新日 : <input id="txtLogBatch" name="updateDate" type="text" class="file_update_date_text"/>
			<input type="hidden"  name="dirDiv" value="<%= Constants.DIR_LOG_BATCH %>"/>
			～ <input id="txtLogWeb" name="updateDate" type="text" class="file_update_date_text"/>
			<input type="hidden"  name="dirDiv" value="<%= Constants.DIR_LOG_WEB %>"/>
			<a id="getLogBatchBtn" href="javascript:void(0)" class="easyui-linkbutton custom-button" iconCls="icon-mini-refresh" plain="true" >フォルダのリストを取得</a>
			<a id="getLogWebBtn" href="javascript:void(0)" class="easyui-linkbutton custom-button" plain="true" style="float: right;" >ダウンロード</a>
		</form>
	</div>
	<div class="panel-bottom"></div>
	<div id="logBatchList" class="easyui-panel gridPanel" title="バッチログ" collapsible="true"
	 style="background:#fafafa;padding:5px;" >
		<form id="logBatchGridForm" name="gridForm">
			<table id="logBatchGrid" class="easyui-datagrid " style="height:310px;min-width:978px;"
				   rownumbers="true"  pagination="true" striped="true" singleSelect="true" checkOnSelect="true" selectOnCheck="true">
				<thead>
					<tr>
						<th field="ck"         width="20"  align="center" checkbox="true" ></th>
						<th field="fileName"   width="500" align="left"   sortable="true" data-options="formatter:formatterDlNm">ファイル名</th>
						<th field="fileSize"   width="100" align="left"   sortable="true">サイズ(bytes)</th>
						<th field="updateDate" width="200" align="left"   sortable="true">更新日</th>
					</tr>
				</thead>
			</table>
		</form>
	</div>

	<br><br>

	<div class="easyui-panel searchCondition" style="background:#fafafa;padding:5px;" >
		<form id="webSearchForm">
			<%-- ページング関連を初期化 --%>
			<input type="hidden" name="start" value="1" />
			<input type="hidden" name="end" value="50" />
			<input type="hidden" name="page" value="1" />
			<input type="hidden" name="rows" value="1" />
			<input type="hidden" name="sort" value="" />
			<input type="hidden" name="order" value="" />
			<input type="submit" style="position: absolute; top: 0px; left: 0px; visibility: hidden;" />
			更新日 : <input id="txtWeb" name="updateDate" type="text" class="file_update_date_text"/>
			<input type="hidden"  name="dirDiv" value="<%= Constants.DIR_EXPORT_WEB %>"/>
			～ <input id="txtLogWeb" name="updateDate" type="text" class="file_update_date_text"/>
			<input type="hidden"  name="dirDiv" value="<%= Constants.DIR_LOG_WEB %>"/>
			<a id="getWebBtn" href="javascript:void(0)" class="easyui-linkbutton custom-button" iconCls="icon-mini-refresh" plain="true" >フォルダのリストを取得</a>
			<a id="getLogWebBtn" href="javascript:void(0)" class="easyui-linkbutton custom-button" plain="true" style="float: right;" >ダウンロード</a>
		</form>
	</div>
	<div class="panel-bottom"></div>
	<div id="webList" class="easyui-panel gridPanel" title="Webエクスポート" collapsible="true"
	 style="background:#fafafa;padding:5px;" >
		<form id="webGridForm" name="gridForm">
			<table id="webGrid" class="easyui-datagrid " style="height:310px;min-width:978px;"
				   rownumbers="true"  pagination="true"   striped="true" singleSelect="true" checkOnSelect="true" selectOnCheck="true">
				<thead>
					<tr>
						<th field="ck"         width="20"  align="center" checkbox="true" ></th>
						<th field="fileName"   width="500" align="left"   sortable="true" data-options="formatter:formatterDlNm">ファイル名</th>
						<th field="fileSize"   width="100" align="left"   sortable="true">サイズ(bytes)</th>
						<th field="updateDate" width="200" align="left"   sortable="true">更新日</th>
					</tr>
				</thead>
			</table>
		</form>
	</div>

	<br><br>

	<div class="easyui-panel searchCondition" style="background:#fafafa;padding:5px;" >
		<form id="bkSearchForm">
			<%-- ページング関連を初期化 --%>
			<input type="hidden" name="start" value="1" />
			<input type="hidden" name="end" value="50" />
			<input type="hidden" name="page" value="1" />
			<input type="hidden" name="rows" value="1" />
			<input type="hidden" name="sort" value="" />
			<input type="hidden" name="order" value="" />
			<input type="submit" style="position: absolute; top: 0px; left: 0px; visibility: hidden;" />
			更新日 : <input id="txtBk" name="updateDate" type="text" class="file_update_date_text"/>
			<input type="hidden"  name="dirDiv" value="<%= Constants.DIR_EXPORT_BATCH_BK %>"/>
			～ <input id="txtLogWeb" name="updateDate" type="text" class="file_update_date_text"/>
			<input type="hidden"  name="dirDiv" value="<%= Constants.DIR_LOG_WEB %>"/>
			<a id="getBkBtn" href="javascript:void(0)" class="easyui-linkbutton custom-button" iconCls="icon-mini-refresh" plain="true" >フォルダのリストを取得</a>
			<a id="getLogWebBtn" href="javascript:void(0)" class="easyui-linkbutton custom-button" plain="true" style="float: right;" >ダウンロード</a>
		</form>
	</div>
	<div class="panel-bottom"></div>
	<div id="BkList" class="easyui-panel gridPanel" title="バッチエクスポート - バックアップ" collapsible="true"
	 style="background:#fafafa;padding:5px;" >
		<form id="BkGridForm" name="gridForm">
			<table id="bkGrid" class="easyui-datagrid " style="height:310px;min-width:978px;"
				   rownumbers="true"  pagination="true"  striped="true" singleSelect="true" checkOnSelect="true" selectOnCheck="true">
				<thead>
					<tr>
						<th field="ck"         width="20"  align="center" checkbox="true" ></th>
						<th field="fileName"   width="500" align="left"   sortable="true" data-options="formatter:formatterDlNm">ファイル名</th>
						<th field="fileSize"   width="100" align="left"   sortable="true">サイズ(bytes)</th>
						<th field="updateDate" width="200" align="left"   sortable="true">更新日</th>
					</tr>
				</thead>
			</table>
		</form>
	</div>

	<br><br>

	<div class="easyui-panel searchCondition" style="background:#fafafa;padding:5px;" >
		<form id="exCoopSearchForm">
			<%-- ページング関連を初期化 --%>
			<input type="hidden" name="start" value="1" />
			<input type="hidden" name="end" value="50" />
			<input type="hidden" name="page" value="1" />
			<input type="hidden" name="rows" value="1" />
			<input type="hidden" name="sort" value="" />
			<input type="hidden" name="order" value="" />
			<input type="submit" style="position: absolute; top: 0px; left: 0px; visibility: hidden;" />
			更新日 : <input id="txtExCoop" name="updateDate" type="text" class="file_update_date_text"/>
			<input type="hidden"  name="dirDiv" value="<%= Constants.DIR_EXPORT_BATCH_EXCOOP %>"/>
			～ <input id="txtLogWeb" name="updateDate" type="text" class="file_update_date_text"/>
			<input type="hidden"  name="dirDiv" value="<%= Constants.DIR_LOG_WEB %>"/>
			<a id="getExCoopBtn" href="javascript:void(0)" class="easyui-linkbutton custom-button" iconCls="icon-mini-refresh" plain="true" >フォルダのリストを取得</a>
			<a id="getLogWebBtn" href="javascript:void(0)" class="easyui-linkbutton custom-button" plain="true" style="float: right;" >ダウンロード</a>
		</form>
	</div>
	<div class="panel-bottom"></div>
	<div id="exCoopList" class="easyui-panel gridPanel" title="バッチエクスポート - 外部連携" collapsible="true"
	 style="background:#fafafa;padding:5px;" >
		<form id="exCoopGridForm" name="gridForm">
			<table id="exCoopGrid" class="easyui-datagrid " style="height:310px;min-width:978px;"
				   rownumbers="true"  pagination="true"   striped="true" singleSelect="true" checkOnSelect="true" selectOnCheck="true">
				<thead>
					<tr>
						<th field="ck"         width="20"  align="center" checkbox="true" ></th>
						<th field="fileName"   width="500" align="left"   sortable="true" data-options="formatter:formatterDlNm">ファイル名</th>
						<th field="fileSize"   width="100" align="left"   sortable="true">サイズ(bytes)</th>
						<th field="updateDate" width="200" align="left"   sortable="true">更新日</th>
					</tr>
				</thead>
			</table>
		</form>
	</div>

	<br><br>

	<div  class="easyui-panel searchCondition" style="background:#fafafa;padding:5px;" >
		<form id="lineListSearchForm">
			<%-- ページング関連を初期化 --%>
			<input type="hidden" name="start" value="1" />
			<input type="hidden" name="end" value="50" />
			<input type="hidden" name="page" value="1" />
			<input type="hidden" name="rows" value="1" />
			<input type="hidden" name="sort" value="" />
			<input type="hidden" name="order" value="" />
			<input type="submit" style="position: absolute; top: 0px; left: 0px; visibility: hidden;" />
			更新日 : <input id="txtLineList" name="updateDate" type="text" class="file_update_date_text"/>
			<input type="hidden"  name="dirDiv" value="<%= Constants.DIR_EXPORT_BATCH_LINELIST %>"/>
			～ <input id="txtLogWeb" name="updateDate" type="text" class="file_update_date_text"/>
			<input type="hidden"  name="dirDiv" value="<%= Constants.DIR_LOG_WEB %>"/>
			<a id="getLineListBtn" href="javascript:void(0)" class="easyui-linkbutton custom-button" iconCls="icon-mini-refresh" plain="true" >フォルダのリストを取得</a>
			<a id="getLogWebBtn" href="javascript:void(0)" class="easyui-linkbutton custom-button" plain="true" style="float: right;" >ダウンロード</a>
		</form>
	</div>
	<div class="panel-bottom"></div>
	<div id="lineList" class="easyui-panel gridPanel" title="バッチエクスポート - 回線一覧" collapsible="true"
	 style="background:#fafafa;padding:5px;" >
		<form id="lineListGridForm" name="gridForm">
			<table id="lineListGrid" class="easyui-datagrid " style="height:310px;min-width:978px;"
				   rownumbers="true"  pagination="true"  striped="true" singleSelect="true" checkOnSelect="true" selectOnCheck="true">
				<thead>
					<tr>
						<th field="ck"         width="20"  align="center" checkbox="true" ></th>
						<th field="fileName"   width="500" align="left"   sortable="true" data-options="formatter:formatterDlNm">ファイル名</th>
						<th field="fileSize"   width="100" align="left"   sortable="true">サイズ(bytes)</th>
						<th field="updateDate" width="200" align="left"   sortable="true">更新日</th>
					</tr>
				</thead>
			</table>
		</form>
	</div>

	<br><br>

</div>
</body>
</html>

