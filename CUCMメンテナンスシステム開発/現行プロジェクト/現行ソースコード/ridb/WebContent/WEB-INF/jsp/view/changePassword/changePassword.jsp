changePassword.jsp
<%@ page contentType="text/html"  pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/taglibs.jsp"/>
<!doctype html>
<html>
<head>
	<c:import url="/WEB-INF/jsp/common/htmlHeader.jsp"/>
	<script type="text/javascript">
	<!--
		/* 画面遷移リンク非表示フラグ */
		var menuHide = false;

		$(function() {

			/* 初期フォーカス */
			$("#" + "${ changePasswordForm.focus }").focus();

			/* パスワード有効期限切れの場合、メッセージ表示 */
			if("${ changePasswordForm.message }"){
				$.messager.alert(t0001,"${ changePasswordForm.message }");
				menuHide = true;
			}

			/* パスワード有効期限切れの場合、遷移リンク非表示 */
			if(menuHide){
				$("#global-menu").toggle();
			}

			/* パスワード変更イベント */
			$('#changePasswordBtn').click(function(){
				$.messager.confirm(t0003, getMessage(c0004,'パスワード変更'), function (r) {
					if (!r) return false;
					$("div.errorMessage").hide();

					$("#changePasswordBtn").attr("disabled", true);

					$.ajax({
						type : "POST",
						url : "changePassword/updatePassword",
						data : convertParam(),
						success : function (data, status, xhr) {

							/* ボタンを押下可にする */
							$("#changePasswordBtn").attr("disabled", false);

							/* 画面を通常に戻す */
							removeError('passwordFrm');

							var errorFiledId = 'changePasswordMessageError';

							/* フォーカス設定 */
							$("#" + data['changePasswordForm'].focus).focus();

							/* エラーメッセージが含まれる場合 */
							if (data['errors']) {
								if (data['errors']['defaultMessage'] != '') {
									if(errorFiledId){
										if (data['errors'][0]['objectName'] == '') {
											showDialogErrorMessage(data['errors'], errorFiledId);
										} else {
											showDialogErrorMessageForObject(data['errors'],errorFiledId);
										}
									} else {
										$.messager.alert(t0001,data['errors'][0]['defaultMessage']);
									}
								}
							} else {

								/* 値のクリア */
								$("#passwordFrm").form('clear');
								/* パスワード有効期限切れだった場合 */
								if(menuHide){
									/* 遷移リンク再表示 */
									$("#global-menu").toggle();

									/* メッセージ表示 */
									$.messager.show({
											title:t0002
											,msg:data['changePasswordForm'].message + i0007
											,showType:null
											,style:{
												right:'',
												bottom:''
											}});

									/* 自動遷移 */
									setTimeout(function(){
										window.location.href = "${ pageContext.request.contextPath }/userAndTel";
									}, 3000);
								} else {
									/* メッセージ表示 */
									$.messager.alert(t0002,data['changePasswordForm'].message);
								}
							}
						},
						error : function (xhr, status) {
							/* エラー処理 */
							ajaxError(xhr,status);
						}
					});
				});
			});
		});

		/**
		 * パスワード変更で渡すパラメータをセットします。
		 */
		function convertParam(){

			/* パラメータをセット */
			var params = "";
			params += 'currentPassword=' + $('#currentPassword').val() + '&';
			params += 'newPassword=' + $('#newPassword').val() + '&';
			params += 'newPasswordConf=' + $('#newPasswordConf').val();

			return params;
		}
	-->
	</script>
</head>
<body>
<%-- Header --%>
<c:import url="/WEB-INF/jsp/common/header.jsp">
	<c:param name="title" value="パスワード変更" />
	<c:param name="infoDisplay" value="true" />
</c:import>

<%-- 入力エリア --%>
<div id="contents" class="container" >
	<div class="grid30F">&nbsp;</div>
	<div class="grid60F" style="margin-top:30px;">
		<div id="passwordPanel" class="easyui-panel" title="パスワードを入力後、ボタンを押下してください" style="background:#fafafa;width:510px;padding:30px;">
		<div style="height:15px;text-align:center;">
			<div id="changePasswordMessageError" class="errorMessage"><span></span></div>
		</div>
			<form id="passwordFrm"  name="passwordFrm" method="post" >
				<table style="margin:3 auto;height:200px;width:100%">
					<colgroup>
						<col width="170">
						<col width="200">
					</colgroup>
					<tr><td></td></tr>
					<tr>
						<td class="label">
							<label for="currentPassword">現在のパスワード：</label>
						</td>
						<td>
							<input id="currentPassword" name="currentPassword"  class="password_text"  type="password">
						</td>
					</tr>
					<tr>
						<td class="label">
							<label for="newPassword">新しいパスワード：</label>
						</td>
						<td>
							<input id="newPassword" name="newPassword"  class="new_password_text"  type="password">
						</td>
					</tr>
					<tr>
						<td class="label">
							<label for="newPasswordConf">新しいパスワード(確認用)：</label>
						</td>
						<td>
							<input id="newPasswordConf" name="newPasswordConf" class="new_password_conf_text"  type="password">
						</td>
					</tr>
					<tr>
						<td colspan="2" style="text-align:center">
							<a id="changePasswordBtn" href="javascript:void(0)" class="easyui-linkbutton custom-button"  iconCls="icon-edit"  plain="true">パスワードを変更</a>
						</td>
					</tr>
					<tr><td></td></tr>
				</table>
			</form>
		</div>
	</div>
</div>
</body>
</html>