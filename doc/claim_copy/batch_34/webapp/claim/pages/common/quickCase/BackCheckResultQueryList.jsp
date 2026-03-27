<%--
****************************************************************************
* DESC       ：简易赔案信息查询结果
* AUTHOR     ：claim
* CREATEDATE ：2007-06-22
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
							zhangshi		20080512				修改模糊查询为右模糊查询
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app"%>
<%@ page import="com.sinosoft.claim.dto.domain.PrpLquickCaseDto"%>
<html:html locale="true">
<head>
<app:css />
<title><s:text name="title.claimBeforeEdit.queryClaim" /></title>
<script src="/claim/common/js/showpage.js">
	
</script>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<SCRIPT LANGUAGE="VBScript">   
    function showMessage(str)   
    showMessage=msgbox(str,3)   
    //是 6   
    //否 7   
    //取消 2   
    end   function   
  </SCRIPT>
<script language="javascript">
<!--
	/**
	 *@description 入口转一般赔案的操作
	 *@param       报案号
	 *@return      通过返回true,否则返回false
	 */
	function submitForm() {
		var ref = "";
		for (i = 0; i < fm.checkFlag.length; i++) {
			if (fm.checkFlag[i].checked == true) {
				ref = ref + fm.checkFlag[i].value + ",";
			}
		}
		fm.searchFlag.value = "true";
		//fm.pageNo.value="1";//查询後页面设为1
		fm.caseFlag.value = ref;
		fm.submit();//提交
	}

	function changeToComm(registNo, quickCaseStatus, fieldObject) {
		var strMsg = "";
		var strAction = "/claim/quickCaseChangeToCommon.do?registNo="
				+ registNo + "&quickCaseStatus=" + quickCaseStatus
				+ "&callType=LIST";
		//判断输入的报案号
		if (registNo == "") {
			alert("没有得到报案号,请重新操作。");
			return false;
		}
		//判断输入的状态
		if (quickCaseStatus == "") {
			alert("没有得到简易赔案的状态,请重新操作。");
			return false;
		}
		//提示是否转赔案？
		if (quickCaseStatus == "01") {
			strMsg = "确定要将报案号为'" + registNo + "'的简易赔案转为一般赔案吗?";
			//执行back的操作
			if (confirm(strMsg)) {

				return true;
			}
		}

		//提示是否转赔案？
		if (quickCaseStatus == "02") {
			strMsg = "要将报案号为'" + registNo
					+ "'的简易赔案转为一般赔案到查勘处吗？选'是'转到查勘，选'否'转到定损", strMsg = strMsg
					+ "~r~n选'取消'返回不做操作!";
			strMsg = strMsg + "若转到查勘，则不保留定损和理算資料 "
			strMsg = strMsg + "若转到定损，则不保留理算資料 "
			var blreturn = showMessage(strMsg);
			//取消操作
			if (blreturn == '2') {
				return false;
			}
			//转移到查勘的操作
			if (blreturn == '6') {
				fm.nodeType.value = 'check';
				return true;
			}
			//转移到定损的操作
			if (blreturn == '7') {
				fm.nodeType.value = 'certa';
				return true;
			}
		}
		return false;
	}
//-->
</script>
<html:base />
</head>
<body onload="initPage();">
	<form name="fm" action="/claim/backCheckQueryEdit.do" method="post" onSubmit="return validateForm(this);">
		<table border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td colspan=4 class="formtitle">
					<s:text name="quickCase.simpleClaimInfoQuery" />
				</td>
			</tr>
			<%--查询简易赔案信息--%>
			<tr>
				<td class='title'>
					<s:text name="db.prpLclaimApprov.registNo" />
					：
				</td>
				<%--报案号--%>
				<td class='input'>
					<select class=tag name="RegistNoSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="RegistNo" class="query">
				</td>
				<td class='title'>
					<s:text name="db.view_larrearage.policyNo" />
					：
				</td>
				<%--保单号--%>
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
					<s:text name="db.prpLclaimStatus.operatedate" />
					:
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
					<input type=text name="OperateDate" class="query">
					<img align="absmiddle" style='cursor: hand' src="/claim/images/bgcalendar.gif"
						onClick="TogglePopupCalendarWindow('document.fm.OperateDate', '<%=(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY).getYear() - 15)%>', '<%=(new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY).getYear() + 2)%>')">
				</td>
				<td class='title'></td>
				<td class='input'></td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="quickCase.simpleStatu" />
					:
				</td>
				<%--简易赔案状态--%>
				<td class='input' colspan="3">
					<input type=checkbox name="checkFlag" value='0'>
					<s:text name="common.status.untreated" />
					<%--未处理--%>
					<input type=checkbox name="checkFlag" value='1'>
					<s:text name="common.status.submitedReview" />
					<%--复查通过--%>
					<input type=checkbox name="checkFlag" value='2'>
					<s:text name="common.status.unsubmitedReview" />
					<%--复查未通过--%>
					<input type=checkbox name="checkFlag" value='3'>
					<s:text name="common.status.intreatingReview" />
					<%--复堪处理--%>
				</td>
			</tr>
			<tr>
				<input name="caseFlag" type="hidden">
				<td class='button' colspan="4">
					<input type=button class='button' value="<s:text name='button.query.value' />" onClick="submitForm();">
					<input name="searchFlag" type="hidden" id="searchFlag">
				</td>
			</tr>
			<tr>
				<td class="title" style="color: red" colspan="4">
					<s:text name="prompt.schedule.query1" />
					<%--"="符号，必须精确查询。--%>
					<br>
					<s:text name="prompt.schedule.query2" />
					<%--"=*"符号，前匹配後模糊的查询。--%>
				</td>
			</tr>
		</table>
		<table class="common" cellpadding="6" cellspacing="1">
			<tr>
				<td colspan=7 class="formtitle">
					<s:text name="quickCase.simpleClaimInfoQueryResult" />
				</td>
			</tr>
			<%--简易赔案信息查询结果--%>
			<tr>
				<td class="centertitle">
					<s:text name="db.prpLclaimApprov.registNo" />
				</td>
				<%--报案号--%>
				<td class="centertitle">
					<s:text name="db.view_larrearage.policyNo" />
				</td>
				<%--保单号--%>
				<td class="centertitle">
					<s:text name="quickCase.reviewOperator" />
				</td>
				<%--复查操作员--%>
				<td class="centertitle">
					<s:text name="quickCase.reviewStartTime" />
				</td>
				<%--复查开始时间--%>
				<td class="centertitle">
					<s:text name="quickCase.reviewState" />
				</td>
				<%--复查状态--%>
				<td class="centertitle">
					<s:text name="certify.operate" />
				</td>
				<%--操作--%>
			</tr>
			<%
				int index = 0;
			%>
			<%
				String strEditType = "";//编辑类型
			%>
			<logic:notEmpty name="prpLBackCheckDto" property="prpLBackCheckDtoList">
				<logic:iterate id="backCheckDto1" name="prpLBackCheckDto" property="prpLBackCheckDtoList">
					<tr class=common>
						<td align="center">
							<a
								href="/claim/quickCaseFinishQueryList.do?nodeType=quickCase&editType=SHOW&registNo=<bean:write name='backCheckDto1' property='registNo'/>&policyNo=<bean:write name="backCheckDto1" property="policyNo"/>&riskCode=<bean:write name="backCheckDto1" property="riskCode"/>">
								<bean:write name="backCheckDto1" property="registNo" />
							</a>
						</td>
						<td align="center">
							<bean:write name="backCheckDto1" property="policyNo" />
						</td>
						<td align="center">
							<bean:write name="backCheckDto1" property="backCheckCode" />
						</td>
						<td align="center">
							<bean:write name="backCheckDto1" property="startTime" />
						</td>
						<td align="center">
							<logic:equal name="backCheckDto1" property="backCheckType" value='0'>
								<s:text name="common.status.untreated" />
								<%--未处理--%>
							</logic:equal>
							<logic:equal name="backCheckDto1" property="backCheckType" value='1'>
								<s:text name="common.status.submitedReview" />
								<%--复查通过--%>
							</logic:equal>
							<logic:equal name="backCheckDto1" property="backCheckType" value='2'>
								<s:text name="common.status.unsubmitedReview" />
								<%--复查未通过--%>
							</logic:equal>
							<logic:equal name="backCheckDto1" property="backCheckType" value='3'>
								<s:text name="common.status.intreatingReview" />
								<%--复堪处理--%>
							</logic:equal>
						</td>
						<td align="center"></td>
					</tr>
					<%
						index++;
					%>
				</logic:iterate>
			</logic:notEmpty>
			<input type="hidden" name="nodeType" value="">
		</table>
	</form>
</body>
</html:html>