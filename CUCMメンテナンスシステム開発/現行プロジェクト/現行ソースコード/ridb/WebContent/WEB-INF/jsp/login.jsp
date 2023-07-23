<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/jsp/common/taglibs.jsp"/>
<!doctype html>
<html>
<head>
	<c:import url="/WEB-INF/jsp/common/htmlHeader.jsp"/>
	<script type="text/javascript">
		$(function() {
			$("#${ loginForm.focus }").focus();

			/* ログインイベント */
			$('#loginBtn').click(function(){
				 $('#loginFrm').submit();
			});
		});
	</script>
</head>
<body>

<%-- Header --%>
<c:import url="/WEB-INF/jsp/common/header.jsp">
	<c:param name="infoDisplay" value="false" />
</c:import>
<div id="contents" class="container">
	<div class="grid30F">&nbsp;</div>
	<div class="grid60F" style="margin-top:30px;">
		<div id="loginPanel" class="easyui-panel" title="認証" style="width:450px;padding:30px;">
			<form id="loginFrm" name="loginFrm" method="post" action="login" novalidate="true">
				<input type="submit" style="margin-left:-1000px;" />
				<table style="margin:0 auto;">
					<colgroup>
						<col width="100px">
						<col width="200px">
					</colgroup>
					<tr>
						<td class="label">
							<label for="deptno" >ユーザー名</label>
						</td>
						<td>
							<form:input path="loginForm.username" id="username" class="login_id" maxlength="50" />
							<div><form:errors path="loginForm.username" cssClass="errorMessage" /></div>
						</td>
					</tr>
					<tr>
						<td class="label">
							<label for="deptno" >パスワード</label>
						</td>
						<td>
							<form:password path="loginForm.password" id="password" class="password_text" maxlength="50" />
							<div><form:errors path="loginForm.password" cssClass="errorMessage" /></div>
						</td>
					</tr>
				</table>
				<div style="text-align:center;margin:20px 0 20px;"><form:errors path="loginForm.message" cssClass="errorMessage" /></div>

				<div style="text-align:right;margin:7px 15px 0px 0px;">
					<a id="loginBtn" class="easyui-linkbutton" >ログイン</a>
				</div>
			</form>
		</div>
	</div>
</div>
</body>
</html>