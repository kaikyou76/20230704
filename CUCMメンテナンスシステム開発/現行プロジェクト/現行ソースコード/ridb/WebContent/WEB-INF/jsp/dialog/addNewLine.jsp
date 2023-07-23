<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/taglibs.jsp"/>
<%@ page import="jp.co.netmarks.common.*" %>
<div style="width:1px;height:1px;overflow:hidden;background-color:red;">
	<div id="addNewLineDialog" class="easyui-dialog dialog_width_3" closed="true" modal="true">
		<div style="height:15px;">
			<div id="addNewLineMessageError" class="errorMessage"><span></span></div>
		</div>
		<div  style="margin:10px 0px 5px 0px;">
			<p id="addNewLineLabel" class="dialog_notes">紐付けするラインの情報を入力してください。</p>
		</div>
		<div>
			<form id="addNewLineForm" method="post">
				<table width="280">
					<colgroup>
						<col width="80">
						<col width="190">
					</colgroup>
					<tr>
						<td class="label">
							<label for="newLineIndexId">連番</label>
						</td>
						<td>
							<input type="text" id="newLineIndex" name="newLineIndex" maxlength="2" class="line_index_text" >
						</td>
					</tr>
					<tr>
						<td class="label">
							<label for="newLineDirectoryNumberId">内線番号</label>
						</td>
						<td>
							<input type="text" id="newLineDirectoryNumber" name="newLineDirectoryNumber" maxlength="8"class="directory_number_text" >
						</td>
					</tr>
					<tr id="newDialinArea">
						<td class="label">
							<label for="newLineDialinNumberId">ダイアルイン</label>
						</td>
						<td>
							<input type="text" id="newLineDialinNumber" name="newLineDialinNumber" maxlength="23" class="dialin_numberr_text">
						</td>
					</tr>
					<tr id="newFomeNoArea">
						<td class="label">
							<label for="newFomeNo">FOMA番号</label>
						</td>
						<td>
							<input type="text" id="newFomeNo" name="newFomeNo" maxlength="23" class="dialin_numberr_text">
						</td>
					</tr>
					<tr id="newChargeArea">
						<td class="label">
							<label for="newLineChargeAssociationBranchId">課金先</label>
						</td>
						<td>
							<input type="text" id="newLineChargeAssociationBranchId"
								   name="newLineChargeAssociationBranchId"
								   class="charge_association_branch_text"  maxlength="3">
							<span style="text-align:center;">-</span>
							<input type="text" id="newLineChargeAssociationParentSectionId"
								   name="newLineChargeAssociationParentSectionId"
								   class="charge_association_parent_section_grid_text" maxlength="5">
							<span style="text-align:center;">-</span>
							<input type="text" id="newLineChargeAssociationSectionId"
								   name="newLineChargeAssociationSectionId"
								   class="charge_association_section_grid_text" maxlength="5">
						</td>
					</tr>
<%-- 					<tr>
						<td class="label">
							<label for="newLineVoiceMailFlgId">VM使用</label>
						</td>
						<td style="vertical-align:bottom">
							<input type="checkbox" id="newLineVoiceMailFlgId" name="newLineVoiceMailFlg" value="<%= Constants.VOICE_MAIL_FLG_ON %>" >
						</td>
					</tr> --%>
				</table>
				<div align="right">
					<a id="newLineCreateBtnId" class="easyui-linkbutton custom-button" iconCls="icon-add" plain="true" >新規追加</a>
				</div>
			</form>
		</div>
	</div>
</div>