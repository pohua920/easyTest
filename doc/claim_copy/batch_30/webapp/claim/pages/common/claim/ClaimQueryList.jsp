<%--
****************************************************************************
* DESC       ：立案查询结果显示页面
* AUTHOR     ：lijiyuan
* CREATEDATE ：2004-03-01
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
							zhangshi		20130201			修改*操作符为=*将like '%%'改为左%右匹配查询
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app"%>
<%@ page import="com.sinosoft.claim.dto.domain.PrpLclaimDto"%>
<%@ page import="com.sinosoft.sysframework.common.datatype.DateTime"%>
<%
	//原因：向页面中增加一个打印按钮
%>
<script src="/claim/common/js/showpage.js"> </script>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<script language="javascript">
   <%--案件状态标志处理--%>
  function submitForm()
  {
    if((fm.InsuredNameSign.value=="="&&fm.InsuredName.value.length>0)
  	 ||(fm.LicenseNoSign.value=="="&&fm.LicenseNo.value.length>0)
  	 ||(fm.RegistNoSign.value=="="&&fm.RegistNo.value.length>0)
  	 ||(fm.PolicyNoSign.value=="="&&fm.PolicyNo.value.length>0)
  	 ||(fm.ClaimNoSign.value=="="&&fm.ClaimNo.value.length>0)){
  	 //输入了一个条件，可以查
  	 }else if((fm.RegistNoSign.value=="=*"&&fm.RegistNo.value.length>8)
  	        ||(fm.PolicyNoSign.value=="=*"&&fm.PolicyNo.value.length>8)
  	        ||(fm.ClaimNoSign.value=="=*"&&fm.ClaimNo.value.length>8)){
  	 	if("D"==getClassCodeType(fm.RegistNo.value.substr(1,2))
  	 	 ||"D"==getClassCodeType(fm.PolicyNo.value.substr(1,2))
  	 	 ||"D"==getClassCodeType(fm.ClaimNo.value.substr(1,2))){
  	 		alert("车险必须精确查询！");
  	 		return false;
  	 	}else{
  	 		//非车险可以前9位模糊查询
  	 	}  		
  	}else{
  		alert("车险必须输入立案号、报案号、保单号、车牌号、被保险人其中一项精确查询！\n 非车险可以用立案号、报案号或者保单号的前9位进行模糊查询！");
  		return false;
  	}
    var ref="";

    for(i=0;i<fm.status.length;i++){

      if(fm.status[i].checked==true){
        ref = ref+fm.status[i].value+",";
      }
    }

    fm.caseFlag.value = ref;
    fm.searchFlag.value="true";
    fm.pageNo.value="1";//查询後页面设为1
    fm.submit();//提交
  }
  //-->
    function document.onkeydown() 
    { 
    if(event.keyCode==13) 
    { 
      document.getElementById("button").click(); 
      return false; 
    } 
    }  
  </script>
<html:html locale="true">
<head>
<app:css />
<title><s:text name="title.claimBeforeEdit.queryClaim" /></title>
<html:base />
</head>
<body onload="initPage();document.onkeydown();">
	<form name="fm" action="/claim/ClaimQuery.do" method="post" onSubmit="return validateForm(this);">
		<table border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td colspan=4 class="formtitle">
					<s:text name="title.claimBeforeEdit.titleName" />
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="db.prpLclaim.claimNo" />：
				</td>
				<td class='input'>
					<select class=tag name="ClaimNoSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="ClaimNo" class="query">
				</td>
				<td class='title'>
					<s:text name="db.prpLclaim.policyNo" />：
				</td>
				<td class='input'>
					<select class=tag name="PolicyNoSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="PolicyNo" class="query">
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="db.prpLclaim.registNo" />：
				</td>
				<%--备案号码--%>
				<td class='input'>
					<select class=tag name="RegistNoSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="RegistNo" class="query">
				</td>
				<td class='title'>
					<s:text name="db.prpLregist.licenseNo" />：
				</td>
				<td class='input'>
					<select class=tag name="LicenseNoSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="LicenseNo" class="query">
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="db.prpLclaimStatus.operatedate" />：
				</td>
				<%--操作时间--%>
				<td class='input'>
					<select class=tag name="OperateDateSign">
						<option value="=">=&nbsp;</option>
						<option value=">">&gt;&nbsp;</option>
						<option value="<">&lt;&nbsp;</option>
						<option value=">=">&gt;=</option>
						<option value="<=">&lt;=</option>
					</select>
					<input type=text name="OperateDate" class="query" style="width: 40%">
					<img style='cursor: hand' src="/claim/images/bgcalendar.gif"
						onClick="TogglePopupCalendarWindow('document.fm.OperateDate', '<%=(new DateTime(DateTime.current(),
						DateTime.YEAR_TO_DAY).getYear()) - 15%>', '<%=(new DateTime(DateTime.current(),
						DateTime.YEAR_TO_DAY).getYear()) + 2%>')">
				</td>
				<%--报案查询增加被保险人查询条件--%>
				<td class='title'>
					<s:text name="db.prpLCMain.insuredName" />:
				</td>
				<%--被保险人名称--%>
				<td class='input'>
					<select class=tag name="InsuredNameSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="InsuredName" class="query">
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="certainLoss.prpLscheduleMainWF.caseState" />
				</td>
				<%--案件状态：--%>
				<td colspan="3" class='input'>
					<input type="hidden" name="caseFlag">
					<!--<input type="checkbox" name="status" value="1">未处理-->
					<input type="checkbox" name="status" value="2">
					<s:text name="common.status.intreating" />
					<%--正处理--%>
					<%--<input type="checkbox" name="status" value="3">已处理--%>
					<input type="checkbox" name="status" value="4">
					<s:text name="common.status.submited" />
					<%--已提交--%>
					<input type="checkbox" name="status" value="6">
					<s:text name="claim.logOut" />
					<%--注销--%>
					<input type="checkbox" name="status" value="7">
					<s:text name="claim.rejectClaim" />
					<%--拒赔--%>
					<input type="checkbox" name="status" value="8">
					<s:text name="claim.endCase" />
					<%--结案--%>
			</tr>
			<tr>
				<td class="title" style="color: red" colspan="4">
					<s:text name="prompt.schedule.query1" />
					<br>
					<%--"="符号，必须精确查询。--%>
					<s:text name="prompt.schedule.query2" />
					<br>
					<%--"=*"符号，前匹配後模糊的查询。--%>
					<s:text name="prompt.schedule.query3" />
					<br>
					<%--车险必须输入报案号、车牌号、被保险人其中一项精确查询！--%>
					<s:text name="prompt.schedule.query4" />
					<%--非车险可以用报案号的前9位进行模糊查询！--%>
				</td>
			</tr>
			<tr>
				<td class='button' colspan="4">
					<input type=button id="button" class='button' value="<s:text name='button.query.value' />" onClick="submitForm();">
					<input type="hidden" name="searchFlag" value="">
					<input type="hidden" name="pageFlag">
					<input type="hidden" name="editType2" value="SHOW">
				</td>
			</tr>
		</table>
		<logic:empty name="type">
		</logic:empty>
		<table class="common" cellpadding="5" cellspacing="1">
			<tr>
				<!-- <table class="common" cellpadding="5" cellspacing="1" >-->
				<td colspan=5 class="formtitle">
					<s:text name="claim.fileInfoQuery" />
				</td>
			</tr>
			<%--立案查询结果信息--%>
			<tr>
				<td class="centertitle">
					<s:text name="certainLoss.prpLscheduleMainWF.caseState" />
				</td>
				<%--案件状态--%>
				<td class="centertitle">
					<s:text name="db.prpLclaim.claimNo" />
				</td>
				<td class="centertitle">
					<s:text name="prpLbpmMain.mainNo" />
				</td>
				<%--报案号--%>
				<td class="centertitle">
					<s:text name="db.prpLlawsuit.operatorCode" />
				</td>
				<%--操作员--%>
				<td class="centertitle">
					<s:text name="db.prpLclaimStatus.operatedate" />
				</td>
				<%--操作时间--%>
			</tr>
			<%
				int index = 0;
			%>
			<logic:notEmpty name="prpLclaimDto" property="claimList">
				<logic:iterate id="prpLclaim1" name="prpLclaimDto" property="claimList">
					<%
						if (index % 2 == 0)
										out.print("<tr class=listodd>");
									else
										out.print("<tr class=listeven>");
					%>
					<tr class=common>
						<td align="center">
							<logic:equal name="prpLclaim1" property="caseType" value="">
								<logic:equal name="prpLclaim1" property="status" value='1'>
									<s:text name="common.status.untreated" />
									<%--未处理--%>
								</logic:equal>
								<logic:equal name="prpLclaim1" property="status" value='2'>
									<s:text name="common.status.intreating" />
									<%--正处理--%>
								</logic:equal>
								<logic:equal name="prpLclaim1" property="status" value='3'>
									<s:text name="common.status.treated" />
									<%--已处理--%>
								</logic:equal>
								<logic:equal name="prpLclaim1" property="status" value='4'>
									<s:text name="common.status.submited" />
									<%--已提交--%>
								</logic:equal>
								<logic:equal name="prpLclaim1" property="status" value='5'>
									<s:text name="common.status.revoked" />
									<%--已撤消--%>
								</logic:equal>
							</logic:equal>
							<logic:equal name="prpLclaim1" property="caseType" value="0">
								<s:text name="common.status.cancled" />
								<%--已注销--%>
							</logic:equal>
							<logic:equal name="prpLclaim1" property="caseType" value="1">
								<s:text name="claim.rejectClaims" />
								<%--已拒赔--%>
							</logic:equal>
							<logic:equal name="prpLclaim1" property="caseType" value="2">
								<s:text name="claim.case" />
								<%--已结案--%>
							</logic:equal>
						</td>
						<td align="center">
							<a href="/claim/claimFinishQueryList.do?prpLclaimClaimNo=<bean:write name='prpLclaim1' property='claimNo'/>&editType=SHOW&riskCode=<bean:write name="prpLclaim1" property="riskCode"/>"><bean:write
									name="prpLclaim1" property="claimNo" /></a>
						</td>
						<td align="center">
							<bean:write name="prpLclaim1" property="registNo" />
						</td>
						<td align="center">
							<bean:write name="prpLclaim1" property="operatorCode" />
						</td>
						<td align="center">
							<bean:write name="prpLclaim1" property="operateDate" />
						</td>
					</tr>
					<%
						index++;
					%>
				</logic:iterate>
			</logic:notEmpty>
			<tr class="listtail">
				<td colspan="5">
					<table width="100%" class="common" align="center" cellpadding="0" cellspacing="0">
						<tr>
							<bean:define id="pageview" name="prpLclaimDto" property="turnPageDto" />
							<%
								PrpLclaimDto prpLclaimDto = (PrpLclaimDto) request
											.getAttribute("prpLclaimDto");
									int curPage = prpLclaimDto.getTurnPageDto().getPageNo();
							%>
							<%@include file="/common/pub/TurnOverPage.jsp"%>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		</tr>
		</table>
		<input type="hidden" name="editType" value="<%=request.getParameter("editType")%>">
	</form>
</body>
</html:html>