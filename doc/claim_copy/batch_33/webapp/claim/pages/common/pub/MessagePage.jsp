<%--
 *************************************************************************
 * 程序名称: MessagePage.jsp
 * 程序功能: 信息反馈页面
 * 最近更新人: weishixin
 * 最近更新日期: 2004-03-29
 ****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ page import="com.sinosoft.claim.dto.custom.*"%>
<%@page import="com.sinosoft.claim.ui.control.action.UICodeAction"%>
<%@page import="com.sinosoft.claim.common.ConstantCodes"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title><s:text name="db.prpLregist.registNo" /></title>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
</head>
<body class="interface">
	<!--原因：在提交报案信息时要提示是否需要抄单列印-->
	<script language='javascript'>
   function submitprint() {
		strUrl = "/claim/ClaimPrint.do?printType=QuickCase&RegistNo='<%=request.getParameter("registNo")%>'";
		printWindow1(strUrl, "列印1");
	}

	function printWindow1(strURL, strWindowName) {
		//add print liudaoping 2013-04-15
		//alert("【列印】功能屬於客制化需求，暫未開發，請知悉！");
		return false;
		var pageWidth = screen.availWidth - 10;
		var pageHeight = screen.availHeight - 30;
		if (pageWidth < 100)
			pageWidth = 100;

		if (pageHeight < 100)
			pageHeight = 100;

		var newWindow = window.open(strURL, strWindowName, 'width=' + pageWidth + ',height=' + pageHeight + ',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1.resizable=1,status=0');
		newWindow.focus();
		return newWindow;
	} <%
	if (request.getAttribute("com.sinosoft.registno") != null) { %>
		//显示列印窗口

		function printWindow(strURL, strWindowName) {
			//add print liudaoping 2013-04-15
			//alert("【列印】功能屬於客制化需求，暫未開發，請知悉！");
			return false;
			var pageWidth = screen.availWidth - 10;
			var pageHeight = screen.availHeight - 30;

			if (pageWidth < 100)
				pageWidth = 100;

			if (pageHeight < 100)
				pageHeight = 100;

			var newWindow = window.open(strURL, strWindowName, 'width=' + pageWidth + ',height=' + pageHeight + ',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1.resizable=1,status=0');
			newWindow.focus();
			return newWindow;
		}

		if (confirm("是否进行承保理赔信息列印")) { <%
			//reason:此处报错误
			String riskCode1 = (String) request.getAttribute("riskCode");
			UICodeAction uicodeAction = new UICodeAction();
			String strRiskType = uicodeAction.translateRiskCodetoRiskType(riskCode1);
			if ("D".equals(strRiskType)) { %>
					strUrl = "/claim/ClaimPrint.do?printType=Regist&RegistNo=<%= request.getAttribute("com.sinosoft.registno ") %> "; <%
			} else { %>
					strUrl = "/claim/ClaimPrint.do?printType=CopyPrint&RegistNo=<%= request.getAttribute("com.sinosoft.registno ") %> "; <%
			} %>
				printWindow(strUrl, "列印1");
		} <%
	} %> <%
	if (request.getAttribute("RegistNo1") != null) { %>
		if (confirm("是否进行无责垫付收据列印")) {
			function printWindow2(strURL, strWindowName) {
				//add print liudaoping 2013-04-15
				//alert("【列印】功能屬於客制化需求，暫未開發，請知悉！");
				return false;
				var pageWidth = screen.availWidth - 10;
				var pageHeight = screen.availHeight - 30;

				if (pageWidth < 100)
					pageWidth = 100;

				if (pageHeight < 100)
					pageHeight = 100;

				var newWindow = window.open(strURL, strWindowName, 'width=' + pageWidth + ',height=' + pageHeight + ',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1.resizable=1,status=0');
				newWindow.focus();
				return newWindow;
			}
			strUrl = "/claim/ClaimPrint.do?printType=AgentType&RegistNo=<%=request.getAttribute("RegistNo1")%>";
			printWindow2(strUrl, "列印2");
		} <%
	} %> <%
	//再保中的现金赔款、共同理赔处理: 需要给予提示. 2005-9-28
	if (request.getAttribute("com.sinosoft.flag") != null && !"".equals(request.getAttribute("com.sinosoft.flag").toString())) { %>
			alert('<%= request.getAttribute("com.sinosoft.flag")%>'); <%
	} %> <%

	//再保中的临分理赔处理: 需要给予提示. 
	if (request.getAttribute("com.sinosoft.reinsFlag") != null && !"".equals(request.getAttribute("com.sinosoft.reinsFlag").toString())) { %>
			alert('<%= request.getAttribute("com.sinosoft.reinsFlag")%>'); <%
	}
	%>

	<%
		String riskcode = (String) request.getAttribute("riskCode");
	if (riskcode == null) {
		riskcode = "0000";
	}
	Object obj = request.getAttribute("compensateNo");
	String compensateNo = "";
	if (obj != null) {
		compensateNo = (String) obj;
	}

	Object obj1 = request.getAttribute("compPrintType");
	String compPrintType = "";
	if (obj1 != null) {
		compPrintType = (String) obj1;
	}

	String riskcode2 = riskcode;
	String registNo = "";
	String businessNo = "";
	String policyNo = "";
	String swfLogFlowID = (String) request.getAttribute("swfLogFlowID");
	String handleDept = (String) request.getAttribute("handleDept");
	if (handleDept == null || "".equals(handleDept)) {
		handleDept = "0000";
	}
	//报案後直接调度
	if ("D".equals(ConstantCodes.carClassMap.get(riskcode2)) && registNo != null) { //目前，只有车险需要调度
		//以下为需要的几个参数
		registNo = (String) request.getAttribute("registNo");
		String prpLscheduleMainWFSurveyNo = "0"; //以前程序中写死为0，这里也写死，具体含义不清楚。
		String scheduleType = "sched"; //调度类型，目前应该就一个调度
		swfLogFlowID = (String) request.getAttribute("swfLogFlowID");
		String swfLogLogNo = "2"; //工作流表中的节点顺序，调度是第二个节点
		String status = "0"; //报案後，状态都为0；
		//riskCode，上面已经获取了
		String editType = "ADD"; //编辑类型，报案後，直接进肯定是ADD
		String nodeType = "sched"; //节点类型，调度
		businessNo = registNo; //业务号。报案号
		String keyIn = registNo; //流入业务号，和报案相同
		policyNo = (String) request.getAttribute("policyNo"); //保单号码
		String modelNo = ""; //模版号，车险模版为1
		String nodeNo = ""; //节点号，到调度後，节点号为2

	}

	// 查勘後直接立案 
	String logno = (String) request.getAttribute("logno");
	String checkRiskCode = (String) request.getAttribute("riskCode");
	String modelNo = "";
	String nodeNo = "";
	if (checkRiskCode != null) {
		if (("2".equals(logno))) {
			registNo = (String) request.getAttribute("registNo");
			swfLogFlowID = (String) request.getAttribute("swfLogFlowID");
			policyNo = (String) request.getAttribute("policyNo"); //保单号码
			businessNo = registNo;
			modelNo = (String) request.getAttribute("modelNo");
			nodeNo = (String) request.getAttribute("nodeNo");
		}
	}
	%>

	function submitSched() {
		//增加命名空间，在action中配置了命名空间
		fm.action = "/claim/schedule/scheduleDealRegist.do?prpLscheduleMainWFRegistNo=<%=registNo%>&prpLscheduleMainWFSurveyNo=0&scheduleType=sched&handleDept=<%=handleDept%>&&endflag=0&commiFlag=0&swfLogFlowID=<%=swfLogFlowID%>&swfLogLogNo=3&status=0&riskCode=<%=riskcode%>&editType=ADD&nodeType=sched&businessNo=<%=businessNo%>&keyIn=<%=businessNo%>&policyNo=<%=policyNo%>&modelNo=1&nodeNo=2";
		fm.buttonSave.disabled = true;

		fm.submit();
	}
	// 查勘後直接立案 

	function submitcheck() {
		fm.action = "/claim/claimBeforeEdit.do?RegistNo=<%=registNo%>&swfLogFlowID=<%=swfLogFlowID%>&swfLogLogNo=<%=logno%>&status=0&riskCode=<%=checkRiskCode%>&editType=ADD&nodeType=claim&businessNo=<%=businessNo%>&keyIn=<%=businessNo%>&policyNo=<%=policyNo%>&modelNo=<%=modelNo %>&nodeNo=<%=nodeNo %>&dfFlag=";
		fm.buttonSave.disabled = true;
		fm.submit();
	}

	/**
	 * @author 中科软
	 * @param obj
	 */

	function copyMessage(obj) {
		var message = obj;
		message.select();
		document.execCommand("Copy");
		alert("复制成功！");
	}

	function CompSubmitPrint() {
		//alert("【列印】功能屬於客制化需求，暫未開發，請知悉！");
		return false;
		var compensateNo = document.getElementById('compensateNo').value;
		if (compensateNo != null && compensateNo != "") {
			var riskCode = document.getElementById('riskCode').value;
			var riskCode2 = document.getElementById('riskCode2').value;
			if ("D" == getClassCodeType(riskCode2)) {
				window.location.href = "/claim/ClaimPrint.do?printType=Compensate&CompensateNo=" + compensateNo;
				//window.location.href="/claim/DAA/print/DAACompensateNoneFormatPrint.jsp?printType=Compensate&CompensateNo="+compensateNo;
			} else if (riskCode2 == '03' || riskCode2 == '23' || riskCode2 == '01' || riskCode2 == '07') {
				window.location.href = "/claim/ClaimPrint.do?printType=PropCompensate&CompensateNo=" + compensateNo;
			} else if (riskCode2 == '15') {
				window.location.href = "/claim/ClaimPrint.do?printType=LiabCompensate&CompensateNo=" + compensateNo;
			} else if (riskCode2 == '27') {
				window.location.href = "/claim/ClaimPrint.do?printType=CompensateAuditBook&CompensateNo=" + compensateNo;
			}
		}

	}
</script>
	<form name="fm" action="" method="post">
		<table cellpadding="4" cellspacing="1" bgcolor="#2D8EE1" align="center">
			<tr>
				<td class=formtitle colspan="2">
					<s:text name="prompt.system.title" />
				</td>
			</tr>
			<tr>
				<td class="common">
					<img src='/claim/images/bgClaimSuccess.gif'>
				</td>
				<td class="common">
					<%--<logic:messagesPresent message="true">
       	<html:messages id="message" message="true">
         <span id="success"><c:out value="${message}"/><c:out value="${prpLregistDto.registNo}"/></span><br>
		--%>
					<s:actionmessage />
					<br>
					<%--</html:messages>
		</logic:messagesPresent>--%>
					<c:if test="${user.userMessage != null}">
     	 ${user.userMessage}
     	 </c:if>
				</td>
			</tr>
			<%
      		if(("D".equals(ConstantCodes.carClassMap.get(riskcode2))|| "23".equals(riskcode2) || "03".equals(riskcode2) || "01".equals(riskcode2)
      		|| "07".equals(riskcode2) || "15".equals(riskcode2) || "27".equals(riskcode2)) && "compPrintType".equalsIgnoreCase(compPrintType)){
      	 %>
			<tr>
				<td class=common colspan="2" align="center">
					<input type="button" name=buttonSave class='button' value="<s:text name='button.printAccount.value' />" onclick="CompSubmitPrint()">
					<%--列印计算书--%>
					<input type="hidden" name="riskCode" value="<%=riskcode %>" />
					<input type="hidden" name="riskCode2" value="<%=riskcode2 %>" />
					<input type="hidden" name="compensateNo" value="<%=compensateNo %>" />
				</td>
			</tr>
			<%} %>
			<% 
     //报案後直接调度
     String saveType = request.getParameter("buttonSaveType"); // 保存类型
    if("D".equals(ConstantCodes.carClassMap.get(riskcode2))&& "2".equals(saveType)== false){//目前，只有车险需要调度
       if(swfLogFlowID!=null){
      
     %>
			<tr>
				<td class=common colspan="2" align="center">
					<!---<div align="center"><a href="/claim/scheduleDealRegist.do?prpLscheduleMainWFRegistNo=<%=registNo%>&prpLscheduleMainWFSurveyNo=0&scheduleType=sched&handleDept=<%=handleDept%>&&endflag=0&commiFlag=0&swfLogFlowID=<%=swfLogFlowID%>&swfLogLogNo=3&status=0&riskCode=<%=riskcode%>&editType=ADD&nodeType=sched&businessNo=<%=businessNo%>&keyIn=<%=businessNo%>&policyNo=<%=policyNo%>&modelNo=1&nodeNo=2" ><img src='/claim/images/ImgVisaMenu_2.gif' border="0">&nbsp处理调度任務</a></div>--->
					<input type="button" name=buttonSave class='button' value="<s:text name='button.scheduling.value' />" onclick="submitSched()">
					<%--调度--%>
				</td>
			</tr>
			<%
         
         }
     } 
    %>
			<% 
			// 查勘後直接立案 
    
    if(checkRiskCode!=null){
    if("2".equals(logno)){
    	
    	%>
			<tr>
				<td class=common colspan="2" align="center">
					<input type="button" name=buttonSave class='button' value="<s:text name='button.record.value' />" onclick="submitcheck()">
					<%--立案--%>
				</td>
			</tr>
			<%
    	
    	}}	
     %>
			<%
    String flag ="";
    flag  = (String)request.getAttribute("flag");
    if(flag!=null){
    if("quickcasePrint".equals(flag))
    
       {
       if("submit".equals(request.getParameter("saveType"))){
       
       %>
			<tr>
				<td class=common colspan="2" align="center">
					<input type="bigbutton" name=buttonSave class='button' value="<s:text name='button.simplePrint.value' />" onclick="submitprint()">
					<%--简易赔案列印--%>
				</td>
			</tr>
			<%}}}%>
			<%
           if(request.getAttribute("sendMessage")!=null && (request.getAttribute("sendMessage").toString()).trim()!=""){
       %>
			<tr>
				<td class=common colspan="2" align="center" width="400px">
					<textarea rows="12" cols="600" name="message" id="message" readonly="true"><%=request.getAttribute("sendMessage")%></textarea>
				</td>
			</tr>
			<tr>
				<td class=common colspan="2" align="center">
					<input type="button" onclick="copyMessage(document.getElementById('message'))" value="<s:text name='button.copy.value' />" />
					<%--复 制--%>
				</td>
			</tr>
			<%
           }
       %>
		</table>
	</form>
	<%
  UserDto user = (UserDto) session.getAttribute("user");
  user.setUserMessage(""); 
%>
</body>
</html>
