<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%><html>
<%@ page import="cn.com.sinosoft.ims.util.IConstants"%>
<%@ page import="cn.com.sinosoft.saa.model.SaaTask"%>
<head>
<title>服务修改</title>
<%
	String log = (String)request.getAttribute("loginMethods");
	String parentCode = (String)request.getAttribute("parentCode");
%>
<%@ include file="/common/meta_css.jsp"%>
<%@ include file="/common/i18njs.jsp"%>
<%@ include file="/common/meta_js.jsp"%>
<%@ page import="cn.com.sinosoft.ims.util.*"%>


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
	<h2 align="center">修改服务</h2>
</div>
<s:form name="fm" action="/utiISvr/svrModify.do" method="post">
<s:hidden name="position" id="position" value="${position}"></s:hidden>
<s:hidden name="type" id="type" value="${type}"></s:hidden>
	<table class="fix_table">
		<input type="hidden" name="utiISvr.validStatus" value="${utiISvr.validStatus }" />
		<input type="hidden" name="utiISvr.creatorCode" value="${utiISvr.creatorCode}" />
		<input type="hidden" name="utiISvr.createDate" value="${utiISvr.createDate}"  />
		<input type="hidden" name="parentCode" id="parentCode" value="<%=parentCode%>" />		
		<tr>
			<td class="bgc_tt short" >服务代码<font color="red">*</font></td>
			<td class="long" width="70%"><input name="utiISvr.svrCode" value="${utiISvr.svrCode}"
					id="utiISvr.svrCode" class='input_w w_30' maxlength="10" readonly="true"/></td>
					</tr>
		<tr>
			<td class="bgc_tt short">服务名称<font color="red">*</font></td>
			<td class="long"><input name="utiISvr.svrName" value="${utiISvr.svrName}"
					id="utiISvr.svrName" class="input_w w_30 dc-chk" maxlength="30"/><nobr></nobr></td>
		</tr>

		<s:if test="${position=='2' }">
		<tr>
			<td class="bgc_tt short">应用部署机构<font color="red">*</font></td>
			<td class="long">
				<c:set var="checked" value="${companyCode}" />
				<ce:select name="companyCode" id="companyCode" cssClass="input_y w_p90 dc-chk" value="${checked}" list="companyCodeMap"  /></td>
		</tr>
		<tr>
		<td class="bgc_tt short">省集中服务代码<font color="red">*</font></td>
			<td class="long"><input type="text" name="utiISvr.svrCodeInCompany" 
					id="utiISvr.svrCodeInCompany" value="${utiISvr.svrCodeInCompany }" class='input_w w_30 dc-chk' obblur="isEmpty()" maxlength="10" />
			</td>
		</tr>
		</s:if>

		<tr>
			<td class="bgc_tt short">服务IP<font color="red">*</font></td>
			<td class="long"><input name="utiISvr.svrIp" value="${utiISvr.svrIp}"
					id="utiISvr.svrIp" class="input_w w_30 dc-chk" maxlength="15" onblur="checkIp();"/><nobr id="IpMsg"></nobr></td>
					</tr>
		<tr>
			<td class="bgc_tt short">服务端口<font color="red">*</font></td>
			<td class="long"><input name="utiISvr.svrPort" value="${utiISvr.svrPort}"
					id="utiISvr.svrPort" class="input_w w_30 dt-num" maxlength="10" onblur="checkPort()"/><nobr id="portMsg"></nobr></td>
		</tr>
		<tr>
			<td class="bgc_tt short">服务分类<font color="red">*</font></td>
			<td class="long"><ce:select cssClass="input_y w_30 dc-chk"  list="#{'1':'数据库','2':'应用服务器','3':'应用系统'}" 
					name="utiISvr.svrType" value="${utiISvr.svrType}" onblur="checkType();" onchange="checkT();" /></td>

		</tr>
			<tr>	
			<td class="bgc_tt short">服务认证方式<font color="red">*</font></td>
			<td class="long" >
					<input name="svrloginmethod" id="svrloginmethod" class="input_y w_p90 dc-chk" value="<%=log %>" onclick="setMethod()" readonly="true"/>
					<input type="hidden" value="${utiISvr.svrLoginMethod}" name="utiISvr.svrLoginMethod"  maxlength="100"  id="utiISvr.svrLoginMethod" readonly="true" />
			</td>
			</tr>
		<tr>
			<td class="bgc_tt short">有效终止日期</td>
<!--			<td class="long"><input type="text" readonly="true" name="utiISvr.validEndDate" -->
<!--					id="utiISvr.validEndDate" class='input_w w_30' maxlength="20" value="${utiISvr.validEndDate}">-->
<!--				<img src="${ctx}/pages/image/time/date_icon.gif" alt="点击显示时间面板" id="imgBtn1" width="14" height="14" /> -->
<!--				<span class="calender-panel">-->
<!--					<div id="calContainer1" style="position: absolute;"></div>-->
<!--				</span>-->
			<td>
				<input readonly="true" name="utiISvr.validEndDate" id="utiISvr.validEndDate" value="${utiISvr.validEndDate}" class="Wdate" onFocus="WdatePicker()" maxlength="20">
			</td>
		</tr>
		</table>

<!--		<div id="crash_menu">-->
<!--			<h2 align="center">修改服务参数</h2>-->
<!--		</div>-->
		<s:if test="${type=='3'}">
         <table class="fix_table" id="table1" style="display:"  > 
         <tr>
			<td class="bgc_tt short">系统对应工具库</td>
			<td class="long">
				<c:set var="checked" value="${utiISvr.utilitySvrCode}" />
              
				<ce:select name="utiISvr.utilitySvrCode" id="utilitySvrCode" cssClass="input_y w_p60 dc-chk" value="${checked}" list="svrCodeMap"  />（如果没有系统对应工具库，请选择【请选择...】项）</td>
		</tr>      
		<tr>
			<td class="bgc_tt short">是否在该平台中管理权限?</td>
			<td class="long">
				<ce:radio name="utiISvr.manageRightStatus" value="${utiISvr.manageRightStatus }" list="#{'1':'是','0':'否'}"></ce:radio>
			</td>
			</tr>
		<tr>
			<td class="bgc_tt short">是否在该平台中管理菜单?</td>
			<td class="long">
				<ce:radio name="utiISvr.manageMenuStatus" value="${utiISvr.manageMenuStatus }" list="#{'1':'是','0':'否'}"></ce:radio>
			</td>
		</tr>
		<tr>
			<td class="bgc_tt short">是否在该平台中管理账户?</td>
			<td class="long">
				<ce:radio name="utiISvr.manageLoginStatus" value="${utiISvr.manageAccStatus }" list="#{'1':'是','0':'否'}"></ce:radio>
			</td>
			</tr>
		<tr>
			<td class="bgc_tt short">是否使用该平台进行登录管理?</td>
			<td class="long">
				<ce:radio name="utiISvr.manageAccStatus" value="${utiISvr.manageLoginStatus}" list="#{'1':'是','0':'否'}"></ce:radio>
			</td>
		</tr>
<!--		<tr>-->
<!--			<td class="bgc_tt short">是否与账户同步</td>-->
<!--			<td class="long">-->
<!--				<ce:radio name="utiISvr.accSyncStatus" value="${utiISvr.accSyncStatus }" list="#{'1':'同步','0':'不同步'}"></ce:radio>-->
<!--			</td>-->
<!--			<td class="bgc_tt short">是否与账户信息同步</td>-->
<!--			<td class="long">-->
<!--				<ce:radio name="utiISvr.accMsgSyncStatus" value="${utiISvr.accMsgSyncStatus }" list="#{'1':'同步','0':'不同步'}"></ce:radio>-->
<!--			</td>-->
<!--		</tr>-->
		<tr colspan="4">	
			<td class="bgc_tt short">是否使用统一用户账户登录?</td>
			<td colspan="4">
				<ce:radio name="utiISvr.accLoginStatus" value="${utiISvr.accLoginStatus }" list="#{'2':'仅使用统一账号','1':'仅使用原系统账号','0':'统一账号和原系统账号并用'}"></ce:radio>
			</td>
		</tr>
   </table>
		</s:if>
		
		<br>
		<br>
		<br>
		
		
</s:form>
</div>
</div>
<table >
		<tr>
			<td >
				<center>
					<input type="button" name="save" class="button_ty" value="保存" onclick="modify();"/>
					<input type="button" name="cancel" class="button_ty" value="取消" onclick="window.history.back(-1);"/>
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
//initAllSelectUi();
	function modify(){
	//	var tcode = document.getElementById("parentCode").value;
	//	alert(tcode);
	//	if(tcode!='0'){
	//		alert("该服务正在使用中,无法修改!!");
	//		return;
	//	}
		if(YAHOO.quote.data.datacheck('fm')){
			if(fm.document.getElementById("position").value=="2"){
				var code = fm.document.getElementById("companyCode").options[fm.document.getElementById("companyCode").selectedIndex].text;
				if(code=="---请选择---"){
					alert("请正确选择省集中名称!");
					return false;
				}else{
					if(checkLen()){
						fm.action="${ctx}/utiISvr/svrModify.do";
						fm.submit();
						return true;
					}
				}
			}else{
				if(checkLen()){
					fm.action="${ctx}/utiISvr/svrModify.do";
					fm.submit();
					return true;
				}
			}	
		 }else{
			alert("界面输入有误,请核实!");
		}
	}
	function setMethod(){
		window.open("${ctx}/utiISvr/setMethod.do","newwindow","height=300,width=800,top=150,left=250,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no");
	}
	function checkIp(){
		this.ipMsg = "";
		var sip = document.getElementById("utiISvr.svrIp").value;
		Ims.isIP(sip,callBackIp);
	}
	function callBackIp(data){
		if(!data){
			DWRUtil.setValue("IpMsg","服务IP输入错误!");  
		}else{
			DWRUtil.setValue("IpMsg",null);
		}
	}
	function checkPort(){
		this.portMsg = "";
		var port = document.getElementById("utiISvr.svrPort").value;
		Ims.isPort(port,callBackPort);
	}
	function callBackPort(data){
		if(!data){
			DWRUtil.setValue("portMsg","服务端口输入错误,应为1--65535之间");
		}else{
			DWRUtil.setValue("portMsg",null);
		}
	}
		function checkType(){
		this.typeMsg = "";
		var type = document.getElementById("utiISvr.svrType").value;
		Ims.isAType(type,callBackType);
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
	
	//init_calendar("calContainer1","imgBtn1","utiISvr.validEndDate");
</script>