<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/taglibs.jsp"/>
<script type="text/javascript">
<!--

	/**
	 * onload処理
	 */
	$(function() {
		
		/* エラーメッセージを取得 */
		var errorMessage = "${separatelyReferenceForm.message}";
		
		/* エラーがあった場合 */
		if(errorMessage != null && errorMessage != ""){
			$('#dialog').dialog({closed: true});
			$.messager.alert(t0001,errorMessage);
		}else {
			/* キャンセルボタン押下時処理 */
			$('#separatelyCancelBtn').click(function(r){
				$('#dialog').dialog({closed: true});
			});
		}
		

	});
//-->
</script>
<div id="callAddLineDialog" style="padding:10px 20px;">
	<div>
		<table style="margin:0 auto;" >
			<colgroup>
				<col width="120">
				<col width="150">
			</colgroup>
			<tr>
				<td style="font-weight:bold;">
					全転送設定状況
				</td>
				<td style="font-weight:bold;">
					<c:choose>
						<c:when test="${ not empty separatelyReferenceForm.fwdAllDestination }">
							<c:out value="${separatelyReferenceForm.fwdAllDestination}" />
						</c:when>
						<c:otherwise>
							未設定
						</c:otherwise>
					</c:choose>
				</td>
		    </tr>
		</table>
	</div>
	<div style="margin-top:20px;" align="right">
		<a id="separatelyCancelBtn"   class="easyui-linkbutton custom-button" iconCls="icon-cancel " plain="true" >閉じる</a>
	</div>
</div>