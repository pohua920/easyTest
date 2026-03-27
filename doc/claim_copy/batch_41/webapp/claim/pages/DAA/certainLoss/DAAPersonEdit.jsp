
<%--
****************************************************************************
* DESC       ：定损登记录入/修改页面
* AUTHOR     ：理赔组
* CREATEDATE ：2004-07-13
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>
<%@ page import="com.sinosoft.sysframework.reference.AppConfig"%>
<%@ page import="java.util.*"%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<script language="JavaScript">
	javascript:window.history.forward(1);
</script>
<html xmlns:mpc>
<head>
<!--对title处理-->
<title><s:text name="title.certainLossBeforeEdit.editCertainLoss" /></title>
<!--定损登记-->
<%@include file="/common/i18njs.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<%-- 页面样式  --%>
<link rel="stylesheet" type="text/css" href="${ctx}/css/Standard.css">
<%-- 标签页样式 --%>
<jsp:include page="/behaviors/MpcStyle.jsp" />
<script src="${ctx}/pages/DAA/certainLoss/js/DAACertainLossEdit.js"></script>
<script src="${ctx}/pages/DAA/certainLoss/js/DAACertainLossPersonEdit.js"></script>
<script>
	//mpc调整
	$(function(){
	    	initWindowNoBtn();
	     $(window).resize(function(){
			initWindowNoBtn();
	     });
	})
	/**
     *@description 设置伤情信息表的初始值
     *@param       无
     *@return      通过返回true,否则返回false
     */
     function loadPrpLpersonWound() { 
         var prpLpersonPersonNo = document.getElementsByName("prpLpersonPersonNo");
         var index = -1;
         var woundCodeCheck;
         <c:forEach items="${prpLpersonWound.woundList}" var="prpLpersonWoundTemp">
         	index = -1;
         	for(var i=0;i<prpLpersonPersonNo.length;i++){
             	if(prpLpersonPersonNo[i].value=="${prpLpersonWoundTemp.id.personNo}"){
             		index = i;
             		break;
                }
            }
            if(index>-1){
            	woundCodeCheck = document.getElementsByName("woundCodeCheck${prpLpersonWoundTemp.woundCode}")[index];
            	woundCodeCheck.checked=true;
            	woundCodeCheck = document.getElementsByName("woundCodeCheck${prpLpersonWoundTemp.woundCode}Txt")[index];
            	woundCodeCheck.value="1";
            }
     	</c:forEach>
    	return true;
    }
  </script>
</head>
<c:choose>
	<c:when test="${param.editType == 'SHOW'}">
		<body onload="initPage();initSet();readonlyAllInput();disabledAllButton('buttonArea');loadPrpLpersonWound();">
	</c:when>
	<c:otherwise>
		<body onload="initPage();initSet();loadPrpLpersonWound();">
	</c:otherwise>
</c:choose>
<c:set var="oldCertainLossLastAccessedTime" value="" scope="session"/>
<DIV id="mainLayer" class="mainLayerNoBtn">
	<form name="fm" action="${ctx}/certainLoss/certainLossSave.do" method="post" onsubmit="return validateForm(this);">
		<c:if test="${param.editType == 'ADD' || param.editType == 'EDIT'}">
			<s:token />
		</c:if>
		<input type="hidden" name="nodeType" value="wound">
		<input type="hidden" name="editType" value="${editType}">
		<input type="hidden" name="riskcode" value="${requestScope.prpLregist.riskCode}">
		<input type="hidden" name="policyno" value="${requestScope.prpLregist.policyNo}">
		<input type="hidden" name="prpLcertainLossPayFee" value="${requestScope.payFee}" />
		<input type="hidden" name="lossTypeFlag" value="${param.lossTypeFlag}">
		<mpc:container ID="oMPC">
			<mpc:page ID="tabMain" TABTITLE="<s:text name="regist.prpLregist.registMain" />" TABTEXT="<s:text name="regist.prpLregist.registMain" />">
				<%--基本信息--%>
				<CENTER>
					<DIV name="tabMain" class="tabMain">
						<%-- 定损主表的画面 --%>
						<%@include file="/pages/DAA/certainLoss/DAACertainLossMainEdit.jsp"%>
						<c:if test="${requestScope.prpLverifyLoss.id.nodeType =='wound' && param.status =='3'}">
							<table border="0" align="center" cellpadding="5" cellspacing="1" class="common">
								<tr>
									<td class="title2">
										<s:text name="certainLoss.rollbackCauses" />:
									</td>
									<!--回退的原因-->
									<td class="input2">
										<input name="prpLverifyLossVeriwReturnReason" class="readonly" readonly value="${requestScope.prpLverifyLoss.veriwReturnReason}">
									</td>
								</tr>
							</table>
						</c:if>
						<%-- 打印定损清单、损失确认书 及检验定损报告 --%>
						<%--<%@include file="/pages/DAA/certainLoss/DAACertainLossPrint.jsp"%>--%>
					</DIV>
				</CENTER>
			</mpc:page>
			<mpc:page ID="tabMain" TABTITLE="<s:text name="certainLoss.inventory" />" TABTEXT="<s:text name="certainLoss.inventory" />">
				<%--人员伤亡清单--%>
				<CENTER>
					<DIV name="tabMain" class="tabMain">
						<%-- 1.人员伤亡清单页面 --%>
						<%@include file="/pages/DAA/certainLoss/DAACertainLossPersonEdit.jsp"%>
					</DIV>
				</CENTER>
			</mpc:page>
		</mpc:container>
		<TABLE id="btnCommon" class="common">
			<TR>
				<TD align="center">
					<%-- 保存通用按钮 --%>
					<%@include file="/pages/DAA/certainLoss/DAACertainLossSave.jsp"%>
				</td>
			</TR>
		</TABLE>
	</form>
</DIV>
</body>
</html>
