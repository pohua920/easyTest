<%--
**************************************************************************
* DESC       ：实赔录入/修改页面
* AUTHOR     ：理赔组
* CREATEDATE ：2013-02-18
* MODIFYLIST ：   Name       Date            Reason/Contents
**************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ page import="com.sinosoft.claim.schema.model.*"%>
<%@ page import="java.util.*"%>
<%@ page import="com.sinosoft.claim.dto.custom.UserDto"%>
<%@ page import="ins.framework.utils.DataUtils"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta_js.jsp"%>
<html xmlns:mpc>
<head>
<!--对title处理-->
<title><s:text name="title.compensateBeforeEdit.editCompensate" /></title>
<%-- 页面样式  --%>
<link rel="stylesheet" type="text/css" href="${ctx}/css/Standard.css">
<%-- 标签页样式 --%>
<jsp:include page="/behaviors/MpcStyle.jsp" />
<script type="text/javascript">
	var CarKindCode = "${requestScope.CarKindCode}";
	var CarKindCodeArray = CarKindCode.split(",");
</script>
<script src="${ctx}/pages/DAA/compensate/js/DAACompensateEdit.js"></script>
<script src="${ctx}/pages/DAA/compensate/js/DAAPersonLossEdit.js"></script>
<script src="${ctx}/pages/DAA/compensate/js/DAAlLossEdit.js"></script>
<script src="${ctx}/pages/DAA/compensate/js/DAACompensateEditDwr.js"></script>
<script src="${ctx}/pages/DAA/compensate/js/autoHospital.js"></script>
<script src="${ctx}/pages/DAA/compensate/js/DAACompensateEditNew.js"></script>
<script src="${ctx}/pages/DAA/compensate/js/DAACompensateCertainLoss.js"></script>
<!--这个函数是调动所能用到的通用js的过程，一般包括最常用的js的函数声明都在meta_js.jsp中-->
<script language="JavaScript">
  javascript:window.history.forward(1);
/**
  *@description 初始化回访问询信息
  *@param       无
  *@return      通过返回true,否则返回false
  */
  function initSet1(){
	  <%List<PrpLqualityCheck> prpLqualityCheckList = (List<PrpLqualityCheck>) request.getAttribute("prpLqualityCheckList");
			if (prpLqualityCheckList != null && prpLqualityCheckList.size() > 0) {
				for (int i = 0; i < prpLqualityCheckList.size(); i++) {
					PrpLqualityCheck prpLqualityCheck = (PrpLqualityCheck) prpLqualityCheckList.get(i);
					if ("0".equals(prpLqualityCheck.getCheckResult())) {%>
	            	fm.VisitBackQue<%=i + 1%>[1].checked=true;
	  		<%} else if ("1".equals(prpLqualityCheck.getCheckResult())) {%>
				fm.VisitBackQue<%=i + 1%>[0].checked=true;
	  		<%} else if ("2".equals(prpLqualityCheck.getCheckResult())) {%>
	            fm.VisitBackQue<%=i + 1%>[2].checked=true;
	  		<%}%>
	            fm.txtQuestionRemark<%=i + 1%>.value='<%=DataUtils.dbNullToEmpty(prpLqualityCheck.getCheckRemark())%>';
			<%}
			}%>
	    return true;
	    }
  </SCRIPT>
<script type="text/javascript">
		//mpc调整
		$(function(){
		     initWindow();
	         $(window).resize(function(){
				initWindow();
	         });
		})
   </script>
	<script type="text/javascript">
		$(document).ready(function(){
				//医院代码查询不用禁用按钮
			   $(":input[name!='prpLpersonLossHospitalCode'][name!='prpLpersonLossHospitalName']").filter(":enabled").ajaxStart(function(){
			     $(this).attr("disabled",true);//请求开始禁用按钮
			   }).ajaxComplete(function(){
			     $(this).attr("disabled",false);//请求完成恢复按钮
			   });
		});
	</script>
</head>
<%
	//防止重复提交
	session.setAttribute("oldCompensateLastAccessedTime", "");
	String editType = request.getParameter("editType");
	UserDto user = (UserDto) session.getAttribute("user");
%>
<c:choose>
	<c:when test="${param.editType=='SHOW'}">
		<body class="interface" onload="initPage();initSet();initSet1();readonlyAllInput();oMPC.style.visibility='visible';">
	</c:when>
	<c:when test="${param.editType=='EDIT'}">
		<body class="interface" onload="initPage();initSet();initSet1();oMPC.style.visibility='visible';">
	</c:when>
	<c:otherwise>
		<body class="interface" onload="initPage();initSet();initSet1();oMPC.style.visibility='visible';">
	</c:otherwise>
</c:choose>
<DIV id="mainLayer" class="mainLayer">
	<c:choose>
		<c:when test="${param.editType=='DELETE'}">
			<form name=fm action="${ctx}/compensate/compensateDelete.do" method="post" onsubmit="return validateForm(this);" autocomplete="off">
		</c:when>
		<c:otherwise>
			<form name=fm action="${ctx}/compensate/compensateSave.do" method="post" onsubmit="return validateForm(this);" autocomplete="off">
		</c:otherwise>
	</c:choose>
	<input type="hidden" name="nodeType" value="compe">
	<input type="hidden" name="editType" value="${editType}">
	<c:if test="${editType == 'ADD' || editType == 'EDIT'}">
		<s:token></s:token>
	</c:if>
	<TABLE id="btnTable" cellpadding="0" cellspacing="0" border="0">
		<TR>
			<td align="left">
				<input type="hidden" class="bigbutton" name="ManyCar" value="<s:text name='button.manyCalculations.value' />" title="多車互碰理賠計算" onclick="showManyCar()">
				<%-- 多车互碰理赔计算 --%>
				<input type="button" class="bigbutton" name="prpLmessageSave" value="<s:text name='button.claimsProcessingRecords.value' />"
					onclick="openWinSave(fm.prpLcompensateClaimNo.value,fm.prpLcompensatePolicyNo.value,fm.prpLcompensateRiskCode.value,'compe',fm.prpLcompensateClaimNo.value)">
				<%-- 赔案处理记录 --%>
				<input type="button" name="eCertify" class="button" value="<s:text name='button.electronicDocuments.value' />"
					onClick="openCertify('certifyFinishQueryList','prpLcertifyCertifyNo',fm.prpLregistExtRegistNo.value,'compe');">
				<%-- 电子单证 --%>
				<c:if test="${param.editType == 'SHOW'}">
					<c:if test="${sessionScope.user.userCode==requestScope.prpLcompensate.handlerCode}">
						<input type="hidden" name="assessor" class="bigbutton" value="<s:text name='button.assessTeacher.value' />" onClick="openAssessor(fm.prpLcompensateClaimNo.value);">
						<%-- 公估师评估 --%>
					</c:if>
				</c:if>
			</td>
		</tr>
	</table>
	<mpc:container ID="oMPC" style="width:830px;height:520px;">
		<mpc:page ID="tabMain" TABTITLE="<s:text name="regist.prpLregist.registMain" />" TABTEXT="<s:text name="regist.prpLregist.registMain" />">
			<%--基本信息--%>
			<CENTER>
				<DIV name="tabMain" class="tabMain">
					<%-- 1.理算主信息--%>
					<%@include file="/pages/DAA/compensate/DAACompensateMainHeadEdit.jsp"%>
					<%-- 2.特别约定 --%>
					<%--<jsp:include page="/pages/DAA/compensate/DAACompensateCengage.jsp" />--%>
					<%--如果是案终赔付，增加结案报告--%>
					<jsp:include page="/pages/DAA/compensate/DAAEndCaseCompensateTextEdit.jsp" />
					<%-- 2.单证主信息 --%>
					<%--理算任务处理，屏蔽“工作质量审核信息 ”栏，－刘国安确认--%>
					<jsp:include page="/pages/DAA/compensate/DAAPrpLqualityCheckEdit.jsp" />
					<%-- 4.报案信息补充说明 --%>
					<jsp:include page="/pages/DAA/regist/DAARegistExtEdit.jsp" />
					<%-- 核赔意见 --%>
					<jsp:include page="/pages/common/pub/UndwrtTextEdit.jsp" />
				</DIV>
			</CENTER>
		</mpc:page>
		<mpc:page ID="tabMain" TABTITLE="<s:text name="button.PayoutInformation.value" /> " TABTEXT="<s:text name="button.PayoutInformation.value" /> ">
			<%--赔付信息--%>
			<CENTER>
				<DIV name="tabMain" class="tabMain">
					<%-- 免赔条件的设置 --%>
					<jsp:include page="/pages/DAA/compensate/DAACompensateDeductCondEdit.jsp" />
					<%-- 3.赔付标的信息 --%>
					<%@include file="/pages/DAA/compensate/DAACompensateLlossEdit.jsp"%>
					<%-- 4.赔付人员信息 --%>
					<%@include file="/pages/DAA/compensate/DAACompensatePersonLossEdit.jsp"%>
					<%-- 5.不计免赔率信息 --%>
					<jsp:include page="/pages/DAA/compensate/DAACompensateExceptDeductibleRateEdit.jsp" />
					<%-- 6.赔款费用 --%>
					<%@include file="/pages/DAA/compensate/DAACompensateChargeEdit.jsp"%>
					<%-- 賠付對象信息 --%>
					<%@include file="/pages/DAA/compensate/DAACompensatePayObject.jsp"%>
					<%-- 7.报案主信息 --%>
					<%@include file="/pages/DAA/compensate/DAACompensateMainTailEdit.jsp"%>
					<%-- 9.車體險訊息 --%>
					<jsp:include page="/pages/DAA/compensate/DAACompensateCarInsurance.jsp" />
					<%-- 8.理算报告 --%>
					<jsp:include page="/pages/DAA/compensate/DAACompensateTextEdit.jsp" />
				</DIV>
			</CENTER>
		</mpc:page>
		<c:if test="${certainLossFlag}">
			<mpc:page ID="tabMain" TABTITLE="<s:text name="certainLoss.vehicleInfo" />" TABTEXT="<s:text name="certainLoss.vehicleInfo" />">
				<CENTER>
					<DIV name="tabMain" class="tabMain">
						<%-- 5.定损讯息  --%>
						<jsp:include page="/pages/DAA/compensate/DAACompensateRepairComponentEdit.jsp" />
					</DIV>
				</CENTER>
			</mpc:page>
		</c:if>
		<mpc:page ID="tabMain" TABTITLE="<s:text name="claim.dangerousUnitInfo" />" TABTEXT="<s:text name="claim.dangerousUnitInfo" />">
			<%--危险单位信息--%>
			<CENTER>
				<DIV name="tabMain" class="tabMain">
					<%-- 5.指定危险单位信息 --%>
					<jsp:include page="/pages/common/claim/ClaimRiskUnit.jsp" />
				</DIV>
			</CENTER>
		</mpc:page>
	</mpc:container>
	<%-- 保存通用按钮页面 --%>
	<TABLE id="btnCommon" class="common">
		<TR>
			<TD align="center"><%@include file="/pages/DAA/compensate/DAACompensateSave.jsp"%></TD>
		</TR>
	</TABLE>
	<c:choose>
		<c:when test="${not empty requestScope.chiefFlag}">
			<input type="hidden" name="chiefflag" value="${requestScope.chiefFlag}">
			<c:if test="${requestScope.chiefFlag == '1'}">
				<jsp:include page="/pages/common/compensate/CompensateCoinsEditFrame.jsp" />
			</c:if>
		</c:when>
		<c:otherwise>
			<input type="hidden" name="chiefflag" value="0">
		</c:otherwise>
	</c:choose>
	</form>
</DIV>
</body>
</html>
