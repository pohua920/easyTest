<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<html>

<head>
	<title>核保系統</title>
	<jsp:include page="/common/meta_css.jsp" />
	<jsp:include page="/common/meta_js.jsp" />
	<%@ include file="/common/i18njs.jsp"%>
	<link href="/undwrt/css/style.css" rel="stylesheet" type="text/css">
</head>
<body class="body_12" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<form id="fm" name="fm" action="${ctx}/common/login.do" method="post">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" >
			<tr>
				<td align="center">
					<div class="main_12">
						<div class="logo_12">
							<img src="${ctx}/images/logo.png" height="63px" width="412px"/>
						</div>
						
						<div class="cont_12">
							<div class="cont_bg_12">
								<div class="input_txt_12">
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td width="30%" height="40" align="right">員工代號：</td>
											<td align="center">
												<input id="userCode" name="userCode" type="text" class="input1" maxlength="10" 
											    	onBlur="reloadComCodeList()" />	
											    <input type="hidden" id="actionType" name="actionType" /> 
												<input type="hidden" id="systemCode" name="systemCode"  value="undwrt" />
	                               			</td>
										</tr>
										<tr>
											<td height="40" align="right">密&nbsp;&nbsp;碼：</td>
											<td align="center">
											 	<!--  OTH0145，處理人員：DP0706，需求單編號：OTH0145- prpins、undwrt、sales及pms登入改為AD驗證   -->
												<input id="password" name="password" type="password" class="input1"
													onBlur="reloadComCodeList()"/>
											</td>
										</tr>
										<tr>
											<td height="40" align="right">單位代號：</td>
											<%-- 
											<td align="center">
			                                    <input type="text" class="input1" name="comCode" maxlength="10" 
			                   					ondblclick="code_CodeSelect(this,'comCodeByUserCode','0','Y',addCondition('userCode',fm.userCode.value),'');"
			                   					onkeyup="code_CodeSelect(this,'comCodeByUserCode','0','Y',addCondition('userCode',fm.userCode.value),'');">
			                                </td>
			                                 --%>
											<td align="center">
												<select id="comCode" name="comCode" class="input1" onFocus="reloadComCodeList()"></select>
											</td>
										</tr>
										<tr>
											<td align="right" height="50" colspan="2" >
												<input name="imageField" type="image" src="${ctx}/images/main_butom.gif" width="199" height="24" border="0" 
													onClick="return submitForm()" />
											</td>
										</tr>
									</table>
								</div>
							</div>
						</div>
						
						<div class="foot_12">技術支援 中科軟科技股份有限公司</div>
					</div>
				</td>
			</tr>
		</table>
		<script type="text/javascript">
				var oldUserCode = "";
				function reloadComCodeList() {
					window.status = new Date();
					if (trim(fm.userCode.value) == oldUserCode) {
						return;
					}
					removeAllComCode();
					if (trim(fm.password.value) == "") {
						return;
					}
					oldUserCode = trim(fm.userCode.value);
					if (trim(fm.userCode.value).length > 0 && trim(fm.userCode.value).length < 11) {
						var url = "${ctx}/common/processCodeInput.do";
						var data = $("#fm").serializeArray();
						$.getJSON(url, data, processComCodeStateChange);						
					}
				}
				function processComCodeStateChange(json) {
					var message = json.message;
					var startIndex = json.startIndex;
					var recordsReturned = json.totalRecords;
					var options = '';
					if(message != "success"){
						alert(message);
					}else{
						$("#comCode").empty();
						for(startIndex; startIndex<recordsReturned; startIndex++){
							options +=  "<option value='" + json.data[startIndex].comCode + "'>"+ json.data[startIndex].comCode + "-" + json.data[startIndex].comName +"</option>";
						}
						$('#comCode').html(options);
					}
				}

				function removeAllComCode() {
					$("#comCode").empty();
				}

				var intPageWidth = screen.availWidth;
				var intPageHeight = screen.availHeight;
				var ua = window.navigator.userAgent;
				var msie = ua.indexOf("MSIE ");
				window.name = 'MainWindow';
				window.resizeTo(intPageWidth, intPageHeight);
				window.focus();
				/**
				 * 是否是IE6
				 * @since 2004-12-07
				 * @return 是返回ture，否则返回false
				 */
				function isIE6() {
					if (navigator.appVersion.indexOf("MSIE 6") > -1) {
						return true;
					} else {
						return false;
					}
				}
				function submitForm() {
					if (validateForm(fm) == false) {
						return false;
					}
					if (fm.password.value == "") {
						alert("請輸入密碼！");//请输入密码!

						setFocus(fm.password);
						return false;
					}
					if (fm.comCode.value == "") {
						alert("請選擇登錄機構。");//请选择登录机构。
						return false;
					}
					fm.actionType.value = "login";
				}
		</script>
	</form>
</body>
</html>