<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%><html>
<head>
<title>服务增加</title>
<%@include file="/common/meta_css.jsp"%>
<%@include file="/common/i18njs.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<script type="text/javascript" src="${ctx}/common/js/sinosoft.js"></script>
<script type="text/javascript" src="${ctx}/common/js/MulLine.js"></script>
<script type="text/javascript" src="${ctx}/common/js/Common.js"></script>
<script type="text/javascript" src="${ctx }/common/dtree/dtree.js"></script>
<script language="javascript" src="${ctx}/common/js/CodeSelect.js"></script>
<script type="text/javascript">
	var svrloginmethod_tip = new YAHOO.widget.Tooltip("svrloginmethod_tip",{text:"请单击选择认证方式",context:"svrloginmethod",zIndex:300});	
</script>
</head>
<body id="all_title">
<div id="wrapper">
<div id="container">
<div id="crash_menu">
<h2 align="center">增加服务</h2>
</div>
<s:form name="fm" action="/utiISvr/svrInsert.do" method="post">
<s:hidden name="position" id="position" value="${position}"></s:hidden>
<s:hidden name="userCode" id="userCode" value="${userCode}"></s:hidden>
<s:hidden name="sType" id="sType" value="${sType}"></s:hidden>
	<table class="fix_table">
		<input type="hidden" name="utiISvr.creatorCode"
			value="${utiISvr.creatorCode}" />
		<tr>
			<td class="bgc_tt short">服务代码<font color="red">*</font></td>
			<td class="long"><input type="text" name="utiISvr.svrCode"
				value="${utiISvr.svrCode}" id="utiISvr.svrCode"
				class="input_w w_30 dc-chk" maxlength="10" onblur="onChecked();" /><nobr id="svrMsg"></nobr></td>
				
		</tr>
		<tr>
			<td class="bgc_tt short">服务名称<font color="red">*</font></td>
			<td class="long"><input type="text" name="utiISvr.svrName"
				value="${utiISvr.svrName}" id="utiISvr.svrName"
				class="input_w w_30 dc-chk" maxlength="30" /></td>
		</tr>
		<s:if test="${position=='2' }">
		<tr>
			<td class="bgc_tt short">应用部署机构<font color="red">*</font></td>
			<td class="long">
				<c:set var="checked" value="0" />
				<ce:select name="companyCode" id="companyCode" cssClass="input_y w_p90 dc-chk" value="${checked}" list="companyCodeMap"  /></td>
			</tr>
		<tr>
			<td class="bgc_tt short">省集中服务代码<font color="red">*</font></td>
			<td class="long"><input type="text"
				name="utiISvr.svrCodeInCompany"  maxlength="10"  id="utiISvr.svrCodeInCompany"
				value="${utiISvr.svrCodeInCompany }" class='input_w w_30 dc-chk'
				maxlength="20"></td>
		</tr>

		</s:if>
		<tr>
			<td class="bgc_tt short">服务IP<font color="red">*</font></td>
			<td class="long"><input type="text" name="utiISvr.svrIp"
				value="${utiISvr.svrIp}" id="utiISvr.svrIp"
				class="input_w w_30 dc-chk" maxlength="15" onblur="checkIp();" /><nobr id="ipMsg"></nobr></td>
			</tr>
		<tr>
			<td class="bgc_tt short">服务端口<font color="red">*</font></td>
			<td class="long"><input type="text" name="utiISvr.svrPort"
				value="${utiISvr.svrPort}" id="utiISvr.svrPort"
				class="input_w w_30 dc-chk dt-num" maxlength="20"
				onblur="checkPort();" /><nobr id="portMsg"></nobr></td>
		</tr>
		<tr>
			<td class="bgc_tt short">服务分类<font color="red">*</font></td>
			<td class="long"><c:set var="checked" value="*" /> <ce:select
				list="#{'*':'请选择','1':'数据库','2':'应用服务器','3':'应用系统'}"
				name="utiISvr.svrType" id="utiISvr.svrType"  cssClass="input_y w_30"  value="${checked}" onblur="checkType();" onchange="checkT();"/><nobr id="typeMsg"></nobr></td>
		</tr>
		<tr>
			<td class="bgc_tt short">服务认证方式<font color="red">*</font></td>
			<td class="long"><input name="svrloginmethod"
				id="svrloginmethod" class="input_y w_p90 dc-chk" value=""
				onclick="setMethod()" readonly="true" /> <input type="hidden"
				value="" name="utiISvr.svrLoginMethod" maxlength="100"  
				id="utiISvr.svrLoginMethod" readonly="true" /></td>
		</tr>
		<tr>
			<td class="bgc_tt short">有效终止日期</td>
			<td class="long">
<!--				<input readonly="true"-->
<!--				name="utiISvr.validEndDate" id="utiISvr.validEndDate"-->
<!--				class='input_w w_p30' maxlength="20"> <img-->
<!--				src="${ctx}/pages/image/time/date_icon.gif" alt="点击显示时间面板"-->
<!--				id="imgBtn1" width="16" height="16" /> <span class="calender-panel">-->
<!--			<div id="calContainer1" style="position: absolute;"></div>-->
<!--			</span>-->
			<input readonly="true" name="utiISvr.validEndDate" id="utiISvr.validEndDate" class="Wdate" onFocus="WdatePicker()" maxlength="20">
			</td>
		</tr>
		<tr>
			<td class="bgc_tt short">是否有效?</td>
			<td class="long"><c:set var="checked" value="1" /><ce:radio name="utiISvr.validStatus"
				value="${checked}" list="#{'1':'是','0':'否'}"></ce:radio>
			</td>
		</tr>
		
	
	</table>

<!--	<div id="crash_menu">-->
<!--	<h2 align="center">服务参数设置</h2>-->
<!--	</div>-->
	<table class="fix_table" id="table1" style="display:none" disabled="true">
          <tr>
			<td class="bgc_tt short">系统对应工具库</td>
			<td class="long">
				<c:set var="checked" value="0" />
				<ce:select name="utiISvr.utilitySvrCode" id="utilitySvrCode" cssClass="input_y w_p60 dc-chk" value="${checked}" list="svrCodeMap"  />
                               （如果没有系统对应工具库，请选择【请选择...】项）</td>
			</tr>
		<tr>
			<td class="bgc_tt short">是否在该平台中管理权限?</td>
			<td class="long"><c:set var="checked" value="1" /><ce:radio name="utiISvr.manageRightStatus"
				value="${checked }" list="#{'1':'是','0':'否'}"></ce:radio>
			</td>
		</tr>
		<tr>
			<td class="bgc_tt short">是否在该平台中管理菜单?</td>
			<td class="long"><c:set var="checked" value="1" /><ce:radio name="utiISvr.manageMenuStatus"
				value="${checked}" list="#{'1':'是','0':'否'}"></ce:radio>
			</td>
		</tr>
		<tr>
			<td class="bgc_tt short">是否在该平台中管理账户?</td>
			<td class="long"><c:set var="checked" value="1" /><ce:radio name="utiISvr.manageAccStatus"
				value="${checked }" list="#{'1':'是','0':'否'}"></ce:radio>
			</td>
			</tr>
		<tr>
			<td class="bgc_tt short">是否使用该平台进行登录管理?</td>
			<td class="long"><c:set var="checked" value="1" /><ce:radio name="utiISvr.manageLoginStatus"
				value="${checked }" list="#{'1':'是','0':'否'}" ></ce:radio>
			</td>
		</tr>
<!--		<tr>-->
<!--			<td class="bgc_tt short">是否与账户同步</td>-->
<!--			<td class="long"><c:set var="checked" value="1" /><ce:radio name="utiISvr.accSyncStatus" value="${checked }" list="#{'1':'同步','0':'不同步'}"></ce:radio></td>-->
<!--			<td class="bgc_tt short">是否与账户信息同步</td>-->
<!--			<td class="long"><c:set var="checked" value="1" /><ce:radio name="utiISvr.accMsgSyncStatus" value="${checked }" list="#{'1':'同步','0':'不同步'}"></ce:radio></td>-->
<!--		</tr>-->
		<tr colspan='4'>
			<td class="bgc_tt short">是否使用统一用户账户登录?</td>
			<td colspan='4'>
				<c:set var="checked" value="2" />
					<ce:radio name="utiISvr.accLoginStatus" value="${checked}" list="#{'2':'仅使用统一账号','1':'仅使用原系统账号','0':'统一账号和原系统账号并用'}">
					</ce:radio>
			</td>
		</tr>
	</table>
	<br>
	<br>
	<br>
	
</s:form></div>
</div>
<table>
	<tr>
		<td >
		<center>
			<input type="button" name="save" class="button_ty" value="保存" onclick="return insert();" /> 
			<input type="button" name="cancel" class="button_ty" value="取消" onclick="return back();"/>
		</center>
		</td>
	</tr>
	</table>
</body>
</html>
<%@ include file="/common/meta_js.jsp"%>
<script language="javascript" src="${ctx}/common/dwr/engine.js"></script>
<script language="javascript" src="${ctx}/common/dwr/util.js"></script>
<script type='text/javascript' src='/ims/dwr/interface/Ims.js'></script>
<script type="text/javascript">
	var flog = true;
	var svcode = "no";
	var svip = "no";
	var svport = "no";
	var svtype = "no";
	function insert() {
		/*	var code = document.getElementById("utiISvr.svrCode").value;
			var name = document.getElementById("utiISvr.svrName").value;
			var method = document.getElementById("utiISvr.svrLoginMethod").value;
			if(code==null||code==""){
				alert("服务代码不能为空");return;
			}
			if(name==null||name==""){
				alert("服务名称不能为空");return;
			}
			if(method==null||method==""){
				alert("服务认证方式不能为空");return;
			}
		 */
		if (YAHOO.quote.data.datacheck('fm')) {
			if(fm.document.getElementById("position").value=="2"){
				var code = fm.document.getElementById("companyCode").options[fm.document.getElementById("companyCode").selectedIndex].text;
				if(code=="---请选择---"){
					alert("请正确选择省集中名称!!!");
					return false;
				}
			}
			if (svcode == "yes" && svip == "yes" && svport == "yes" && svtype=="yes") {
				if (confirm("服务增加后服务代码无法更改，是否确定为此服务代码？")) {
					if(checkLen()){
	//				    fm.save.disabled=true;
						fm.action = "${ctx}/utiISvr/svrInsert.do";
						fm.submit();
						return true;
					}
				} else {
					alert("操作已取消");
				}
			} else {
				alert("界面输入有误，请核实!");
			}
		} else {
			alert("界面输入有误，请核实！");
		}
	}
	function validate() {
		this.flg = true;
		this.svrMsg = "";
		this.svrChk = function(svrname) {
			//		alert("svrchk|||"+svrname);
			Ims.isExist(svrname, callBack);
		};
		var callBack = function(data) {
			if (!data) {
				DWRUtil.setValue("svrMsg", "该服务代码已经存在!");
				this.flg = false;
				svcode = "no";
			} else {
				DWRUtil.setValue("svrMsg", null);
				this.flg = true;
				svcode = "yes";
			}
		};
	}
	function onChecked() {
		//	alert("onblur");
		var rc = new validate();
		rc.svrChk(document.getElementById("utiISvr.svrCode").value);
		if (rc.flg) {
			flog = true;
			return true;
		} else {
			var errors = rc.svrMsg;
			DWRUtil.setValues(errors);
			flog = false;
			return false;
			document.getElementById("utiISvr.svrCode").value = "";
		}
	}
	/*function checkUsed(){
		var asvrcode = document.getElementById("utiISvr.svrcode").value;
		fm.action="${ctx}/utiISvr/checkUsed.do";
		fm.submit();
		return true;
	}
	 */
	function back() {
		fm.action = "${ctx}/utiISvr/prepareQuerySvr.do";
		fm.submit();
		return true;
	}
	function checkIp() {
		this.ipMsg = "";
		var sip = document.getElementById("utiISvr.svrIp").value;
		Ims.isIP(sip, callBackIp);
	}
	function callBackIp(data) {
		if (!data) {
			DWRUtil.setValue("ipMsg", "服务IP输入错误!");
			svip = "no";
		} else {
			DWRUtil.setValue("ipMsg", null);
			svip = "yes";
		}
	}
	function checkPort() {
		this.portMsg = "";
		var port = document.getElementById("utiISvr.svrPort").value;
		Ims.isPort(port, callBackPort);
	}
	function callBackPort(data) {
		if (!data) {
			DWRUtil.setValue("portMsg", "服务端口输入错误,应为1--65535之间");
			svport = "no";
		} else {
			DWRUtil.setValue("portMsg", null);
			svport = "yes";
		}
	}
	function checkType(){
		this.typeMsg = "";
		var type = document.getElementById("utiISvr.svrType").value;
		Ims.isAType(type,callBackType);
	}
	function callBackType(data){
		if(!data){
			DWRUtil.setValue("typeMsg", "请选择服务类型");
			svtype = "no";
		}else{
			DWRUtil.setValue("typeMsg",null);
			svtype = "yes";
		}
	}
	function checkT(){
		var type = document.getElementById("utiISvr.svrType").value;
		if(type=="3"){
			document.getElementById("table1").style.display="";
			document.getElementById("table1").disabled=false;
		}else{
			document.getElementById("table1").style.display="none";
			document.getElementById("table1").disabled=true;
		}
	}
	
	function setMethod() {
		window
				.open(
						"${ctx}/utiISvr/setMethod.do",
						"newwindow",
						"height=300,width=800,top=150,left=250,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no");
	}
	function aa(){
		alert(document.getElementById("companyCode").value);
	}

	//init_calendar("calContainer1", "imgBtn1", "utiISvr.validEndDate");
</script>