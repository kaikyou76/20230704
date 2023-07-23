<%@page import="com.sun.corba.se.impl.orbutil.closure.Constant"%>
<%@ page contentType="text/html"  pageEncoding="UTF-8" errorPage="/jsp/error" %>
<jsp:directive.include file="/WEB-INF/jsp/common/taglibs.jsp"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="jp.co.netmarks.common.*" %>
<!doctype html>
<html>
<head>
	<c:import url="/WEB-INF/jsp/common/htmlHeader.jsp"/>
	<script type="text/javascript" src="${ pageContext.request.contextPath }/js/jquery.gridutil.js"></script>
	<script type="text/javascript" src="${ pageContext.request.contextPath }/js/jquery.upload-1.0.2.js"></script>
	<script type="text/javascript">
	<!--
		/* JSONデータ（プルダウン） */
		var dynamicSectionTel;			// 店部課リスト（動的）
		var dynamicPhoneButtonTemplete;	// PhoneButtonTemplete

		/* グリッド */
		var grid;

		/**
		 * onload処理
		 */
		$(function() {

			/* 初期フォーカス */
			$("#" + "${ telLumpEditForm.focus }").focus();

			/* Grid用ライブラリを設定 */
			grid = $("#grid").gridutil({
					'editables'     : {
						'branchTelId'                      : '#gridForm select[name=branchTelId]',						// 拠点（電話機）
						'companySectionTelId'              : '#gridForm select[name=companySectionTelId]',				// 店部課（電話機）
						'telTypeModel'                     : '#gridForm select[name=telTypeModel]',						// 電話機
						'phoneButtonTemplete'              : '#gridForm select[name=phoneButtonTemplete]',				// PhoneButtonTemplete
						'macAddress'                       : '#gridForm input[name=macAddress]',							// MACアドレス
						'directoryNumber'                  : '#gridForm input[name=directoryNumber]',					// 内線番号
						'loggerData'                       : '#gridForm select[name=loggerData]',						// 通話録音
						'dialinNumber'                     : '#gridForm input[name=dialinNumber]',						// ダイアルイン
						'chargeAssociationBranchId'        : '#gridForm input[name=chargeAssociationBranchId]',			// 課金先（拠点）
						'chargeAssociationParentSectionId' : '#gridForm input[name=chargeAssociationParentSectionId]',	// 課金先（親店部課）
						'chargeAssociationSectionId'       : '#gridForm input[name=chargeAssociationSectionId]'			// 課金先（子店部課）
					}
			});

			/* 選択したCSVファイルをグリッドに表示ボタン押下時イベントを設定 */
			$('#csvImportBtn').click(csvRead);


			/* 一括登録 */
			$('#lumpEditBtn').click(function() {
				/* 選択 */
				$.messager.confirm(t0003, getMessage(c0004,'一括登録'), function (r) {

					if (!r) return false;

					$("#grid").datagrid('getData').rows[0].branchTelId = $(".branch_select")[0].value;

					grid.update('telLumpEdit/lumpEdit', '#gridForm',function (data) {
						if (data["telLumpEditListForm"] && data["telLumpEditListForm"].message) {
							$.messager.alert(t0002,data["telLumpEditListForm"].message);
						}
					});
				});
			});
		});

		/**
		 * 初期表示処理
		 */
		window.onload = function() {
			/* 電話機登録行設定(規定値：50行)*/
			setTimeout(function() {
				grid.search('telLumpEdit/setDefaultRows','#gridForm');
			}, 300);
		}

		/**
		 * CSVをグリッドに表示
		 */
		function csvRead() {

			/* 取込みファイルを選択しているかチェックする */
			if(!$("#csvFile")[0].value) {
				$.messager.alert(t0001,getMessage(e0002,'取込むCSVファイル'));
				return false;
			}

			/* CSVファイル取込み*/
			$("#csvFile").upload('telLumpEdit/setCsvValue', function(res) {
				if(res["errorMessage"]) {
					$.messager.alert(t0001,res["errorMessage"]);
				}

				/* 取込みデータGrid表示*/
				grid.search('telLumpEdit/setDefaultRows','#gridForm', function(data){

					/* 取込みデータをGrid表示データに合わせる*/
					for(var i in data.rows){
						if($(".branch_select")[i].selectedIndex == 0) {
							data.rows[i].branchTelId = "";
						}
						if($(".section_select")[i].selectedIndex == 0) {
							data.rows[i].companySectionTelId = "";
						}
						if($(".tel_type_model_select")[i].selectedIndex == 0) {
							data.rows[i].telTypeModel = "";
						}
						if($(".phone_button_templete_select")[i].selectedIndex == 0) {
							data.rows[i].phoneButtonTemplete = "";
						}
					};

				});
		    },'json');

			return false;
		}

		/**
		 * formatter:MACアドレス
		 */
		function formatMacAddress(value,row,index){
			var disFlg = false;
			/* 電話機がCTI portの場合 */
			var telTypeModel = row.telTypeModel;
			if(telTypeModel == '<%= Constants.TEL_TYPE_MODEL_CTI_PORT %>'){
				disFlg = true;
			}
			
			return $.gridutil.formatInput({ name :'macAddress' , value : value , 'class' : 'mac_address_text' , maxlength : '12', disabled : disFlg});
		}

		/**
		 * formatter:内線番号
		 */
		function formatDirectoryNumber(value,row,index){
			return $.gridutil.formatInput({ name :'directoryNumber' , value : value , 'class' : 'directory_number_text' , maxlength : '8'});
		}

		/**
		 * formatter:ダイアルイン
		 */
		function formatDialinNumber(value,row,index){
			/* 電話機がCTI portの場合 */
			var disFlg = false;
			var telTypeModel = row.telTypeModel;
			if(telTypeModel == '<%= Constants.TEL_TYPE_MODEL_CTI_PORT %>'){
				jsonList = [{'label':'--','value':''}];
				disFlg = true;
			}
			
			return $.gridutil.formatInput({ name :'dialinNumber' , value : value , 'class' : 'dialin_numberr_text' , maxlength : '24', disabled : disFlg});
		}

		/**
		 * formatter:備考
		 */
		function formatRemarks(value,row,index){
			return $.gridutil.formatInput({ name :'remarks' , value : value , 'class' : 'tel_remarks_text' , maxlength : '256'});
		}

		/**
		 * formatter:課金先
		 */
		function formatCharge(value,row,index){
			/* 電話機がCTI portの場合 */
			var disFlg = false;
			var telTypeModel = row.telTypeModel;
			if(telTypeModel == '<%= Constants.TEL_TYPE_MODEL_CTI_PORT %>'){
				jsonList = [{'label':'--','value':''}];
				disFlg = true;
			}
			
			var tag =   $.gridutil.formatInput({ name  : 'chargeAssociationBranchId'       , 'class' : 'charge_association_branch_grid_text',
											value : row.chargeAssociationBranchId ,        maxlength : '3', disabled : disFlg})
					   + $.gridutil.formatInput({ name  : 'chargeAssociationParentSectionId', 'class' : 'charge_association_parent_section_grid_text' ,
					   						value : row.chargeAssociationParentSectionId , maxlength : '5', disabled : disFlg})
					   + $.gridutil.formatInput({ name  : 'chargeAssociationSectionId'      , 'class' : 'charge_association_section_grid_text' ,
						   					value : row.chargeAssociationSectionId ,       maxlength : '5', disabled : disFlg});

			return  tag;
		}


		/**
		 * formatter:拠点リスト
		 */
		function formatBranchTel(value,row, index){

			return $.gridutil.formatSelectTag(${ telLumpEditForm.branchListJson },
					{ name :'branchTelId' , 'class' : 'branch_select' } , value);
		}

		/**
		 * formatter:店部課リスト（動的）
		 */
		function formatSectionTel(value, row, index) {
			 dynamicSectionTel = ${ telLumpEditForm.sectionListJson };
			 var jsonList = dynamicSectionTel[row.branchTelId];
			if (jsonList == undefined) {
				 jsonList = [{'label':'--','value':''}];
			}
			return $.gridutil.formatSelectTag(jsonList,
					{ name :'companySectionTelId' , 'class' : 'section_select'} , value, row.branchTelId);
		}

		/**
		 * formatter:電話機リスト
		 */
		function formatTelTypeModel(value, row, index) {
			return $.gridutil.formatSelectTag(${ telLumpEditForm.telTypeModelListJson },
					{ name :'telTypeModel' , 'class' : 'tel_type_model_select' } , value);
		}

		/**
		 * formatter:電話機区分 TODO:削除予定
		 */
		function formatPhoneClass(value,row,index){
			var disFlg = false;
			var jsonList = [
					{'label':'IP電話','value':'0'},
					{'label':'固定併用','value':'2'},
				];

			/* 電話機がCTI portの場合 */
			var telTypeModel = row.telTypeModel;
			if(telTypeModel == '<%= Constants.TEL_TYPE_MODEL_CTI_PORT %>'){
				jsonList = [{'label':'FMC端末','value':'1'}];
			}
			
			return $.gridutil.formatSelectTag(jsonList, { name :'phoneClass' , 'class' : 'phone_class_select' , disabled : disFlg } , value);
		}

		/**
		 * formatter:PhoneButtonTempleteリスト（動的）
		 */
		function formatPhoneButtonTemplete(value, row, index) {
			dynamicPhoneButtonTemplete = ${ telLumpEditForm.phoneButtonTempleteListJson };
			var jsonList = dynamicPhoneButtonTemplete[row.telTypeModel];
			if (jsonList == undefined) {
				jsonList = [{'label':'--','value':''}];
			}
			/* 電話機がCTI portの場合 */
			var disFlg = false;
			var telTypeModel = row.telTypeModel;
			if(telTypeModel == '<%= Constants.TEL_TYPE_MODEL_CTI_PORT %>'){
				jsonList = [{'label':'--','value':''}];
				disFlg = true;
			}
				
			return $.gridutil.formatSelectTag(jsonList,
					{ name :'phoneButtonTemplete' , 'class' : 'phone_button_templete_select', disabled : disFlg } , value ,row.telTypeModel);
		}

		/**
		 * formatter:通話録音リスト
		 */
		function formatLoggerData(value,row,index){
			var jsonList = ${ telLumpEditForm.loggerDataListJson };
			/* 電話機がCTI portの場合 */
			var disFlg = false;
			var telTypeModel = row.telTypeModel;
			if(telTypeModel == '<%= Constants.TEL_TYPE_MODEL_CTI_PORT %>'){
				jsonList = [{'label':'--','value':''}];
				disFlg = true;
			}
			
			return $.gridutil.formatSelectTag(jsonList,
					{ name :'loggerData' , 'class' : 'logger_data_select', disabled : disFlg }, value);
		}
	-->
	</script>
</head>
<body>
<%-- Header --%>
<c:import url="/WEB-INF/jsp/common/header.jsp">
	<c:param name="title" value="電話機一括登録" />
	<c:param name="infoDisplay" value="true" />
</c:import>

<div id="contents" class="container" style="min-width:978px">
	<br>
	<%-- グリッド --%>
	<form id="gridForm" name="gridForm">
		<!-- 一覧 -->
		<input type="hidden" name="_RequestVerificationToken"  />
		<table id="grid" class="easyui-datagrid " style="height:500px" toolbar="#toolbar"
			   rownumbers="true"  pagination="false"  remoteSort="false" striped="true" singleSelect="true" checkOnSelect="false" selectOnCheck="false">
			<thead>
				<tr>
					<th field="branchTelId"            width="260"  align="left" sortable="false" data-options="formatter:formatBranchTel">拠点</th>
					<th field="companySectionTelId"    width="260"  align="left" sortable="false" data-options="formatter:formatSectionTel">所属店部課</th>
					<th field="telTypeModel"           width="110"  align="left" sortable="false" data-options="formatter:formatTelTypeModel" >電話機</th>
					<th field="phoneButtonTemplete"    width="160"  align="left" sortable="false" data-options="formatter:formatPhoneButtonTemplete" >PhoneButtonTemplate</th>
					<th field="macAddress"             width="110"  align="left" sortable="false" data-options="formatter:formatMacAddress">MAC ADDRESS</th>
					<th field="directoryNumber"        width="100"  align="left" sortable="false" data-options="formatter:formatDirectoryNumber" >内線番号</th>
					<th field="loggerData"             width="70"   align="left" sortable="false" data-options="formatter:formatLoggerData">通録</th>
					<th field="dialinNumber"           width="100"  align="left" sortable="false" data-options="formatter:formatDialinNumber">ダイアルイン</th>
					<th field="charge"                 width="170"  align="left" sortable="false" data-options="formatter:formatCharge">課金先</th>
					<th field="remarks"                width="160"  align="left" sortable="false" data-options="formatter:formatRemarks">備考（電話機の設置場所）</th>
				</tr>
			</thead>
		</table>
	</form>

		<div id="toolbar">
			<div id="csvFileDiv">
				CSVファイル選択：
					<input id="csvFile" name="csvFile" type="file"  maxlength="1" accept="csv"/>
					<a id="csvImportBtn" href="#" class="easyui-linkbutton" iconCls="icon-excel" plain="true">選択したCSVファイルをグリッドに表示</a>
			</div>

			</br>
			<a id="lumpEditBtn" href="#" class="easyui-linkbutton custom-button" plain="true">一括登録</a>
			<div id="message-error"           class="errorMessage" style="display:inline;"><span style="margin-left:100px;"></span></div>
		</div>
</div>

</body>
</html>