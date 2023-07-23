<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/taglibs.jsp"/>
<%@ page import="jp.co.netmarks.common.*" %>
<div style="width:1px;height:1px;overflow:hidden;background-color:red;">

	<div id="checkDetailCucmDialog" class="easyui-dialog dialog_width_7" closed="true" modal="true">
		<%--<div  style="margin:10px 0px 5px 0px;">
			<p class="dialog_notes_2">CUCM情報取得日：${ checkConsistencySearchForm.cucmUpdateTime }</p>
		</div>--%>
		<table id="checkCucmDetailGrid" class="easyui-datagrid" style="width:700px;height:535px" rownumbers="false"
		       singleSelect="true" pagination="false" fitColumns="true" checkOnSelect="false" >
			<thead>
				<tr>
					<th field="subject"    width="200"  align="center"  styler="subjectStyler">項目</th>
					<th field="app"      width="240"  align="center"  styler="appStyler">連携アプリ</th>
					<th field="cucm"     width="240"  align="center"  styler="cucmStyler">CUCM</th>
				</tr>
			</thead>
		</table>
		<div id="addUserBtnArea" align="right">
			<a id="cucmOKBtn"   class="easyui-linkbutton custom-button" iconCls="icon-ok" plain="true" >OK</a>
		</div>
	</div>
</div>