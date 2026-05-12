<%--
****************************************************************************
* DESC       ：
* AUTHOR     ：lijiyuan
* CREATEDATE ：2006-03-09
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
*               
****************************************************************************
--%>
<%@ include file="/common/taglibs.jsp"%>
<table class="common" cellpadding="5" cellspacing="1" id="Lloss" style="">
	<tr>
		<td class="title" style="width: 20%">
			<s:text name="certainLoss.nuclearDamage" />：
		</td>
		<!--核损意见-->
		<td class="input" style="width: 80%" colspan='5'>
			<c:choose>
				<c:when test="${param.nodeType =='verif'}">
					<s:select name="verifyOpinion" list="#request.verifyOpinionList" id="labelValueBean" listKey="key" listValue="value" value="#request.prpLverifyLoss.verifyOpinion" style='width:160px'
						onclick="change();" onchange="change();">
					</s:select>
				</c:when>
				<c:otherwise>
					<input name="verifyOpinion" type='hidden' value="${requestScope.prpLverifyLoss.verifyOpinion}">
					<input name="prpLverifyLossVerifyRemark" type='hidden' value="${requestScope.prpLverifyLoss.verifyRemark}">
					<c:choose>
						<c:when test="${requestScope.prpLverifyLoss.verifyOpinion == '01'}">
							<input name="verifyOpinionName" class="readOnly" readOnly value="<s:text name='certainLoss.agreeLoss'/>">
							<!--同意定损-->
						</c:when>
						<c:when test="${requestScope.prpLverifyLoss.verifyOpinion == '02'}">
							<input name="verifyOpinionName" class="readOnly" readOnly value="<s:text name='certainLoss.priceObjections'/>">
							<!--价格异议-->
						</c:when>
						<c:when test="${requestScope.prpLverifyLoss.verifyOpinion == '03'}">
							<input name="verifyOpinionName" class="readOnly" readOnly value="<s:text name='certainLoss.notSufficient'/>">
							<!--信息不充分-->
						</c:when>
						<c:when test="${requestScope.prpLverifyLoss.verifyOpinion == '04'}">
							<input name="verifyOpinionName" class="readOnly" readOnly value="<s:text name='certainLoss.produceReports '/>">
							<!--出具检验报告-->
						</c:when>
						<c:when test="${requestScope.prpLverifyLoss.verifyOpinion == '99'}">
							<input name="verifyOpinionName" class="readOnly" readOnly value="<s:text name='certainLoss.other'/>">
							<!--其它-->
						</c:when>
					</c:choose>
				</c:otherwise>
			</c:choose>
		</td>
	</tr>
</table>
