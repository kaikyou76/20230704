<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/taglibs.jsp"/>
<%@ page import="jp.co.netmarks.common.*" %>
<div style="width:1px;height:1px;overflow:hidden;background-color:red;">

	<div id="checkDetailOfficeLinkDialog" class="easyui-dialog dialog_width_7" closed="true" modal="true">
		<%--<div  style="margin:10px 0px 5px 0px;">
			<p class="dialog_notes_2">CUCM情報取得日：${ checkConsistencySearchForm.cucmUpdateTime }</p>
		</div>--%>
		<table id="checkOfficeLinkDetailGrid" class="easyui-datagrid" style="width:700px;height:180px" rownumbers="false"
		       singleSelect="true" pagination="false" fitColumns="true" checkOnSelect="false" >
			<thead>
				<tr>
					<th field="subject"  width="200"  align="center"  styler="subjectStyler">項目</th>
					<th field="app"        width="240"  align="center"  styler="appStyler">連携アプリ</th>
					<th field="officeLink" width="240"  align="center"  styler="officeLinkStyler">オフィスリンク</th>
				</tr>
			</thead>
		</table>
		<div id="addUserBtnArea" align="right">
			<a id="officeLinkOKBtn"   class="easyui-linkbutton custom-button" iconCls="icon-ok" plain="true" >OK</a>
		</div>
	</div>
</div>