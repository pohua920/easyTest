<%--
****************************************************************************
* DESC       ：添加主信息子块界面页面[ 结案 ]
* AUTHOR     ： 中科软
* CREATEDATE ： 2013-02-03
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<table class="common" cellpadding="5" cellspacing="1">
	<tr>
		<td colspan=5 class="formtitle">
			<s:text name="compensate.adjustmentInformation" />
		</td>
	</tr>
	<!-- 理算信息 -->
	<tr>
		<td class="centertitle">
			<s:text name="db.prpLclaimStatus.status" />
		</td>
		<!-- 案件状态 -->
		<td class="centertitle">
			<s:text name="db.prpLcfee.compensateNo" />
		</td>
		<!-- 赔款计算书号 -->
		<td class="centertitle">
			<s:text name="check.claimNum" />
		</td>
		<!-- 赔案号 -->
		<td class="centertitle">
			<s:text name="prompt.queRegist.PolicyNo" />
		</td>
		<!-- 保单号 -->
		<td class="centertitle">
			<s:text name="compensate.sumPayMoney" />
		</td>
		<!-- 总赔付金额(TWD) -->
	</tr>
	<c:forEach items="${prpLcompensate.compensateList}" var="prpLcompensate" varStatus="indexCompensate">
		<c:if test="${indexCompensate.index %2== 0}">
			<tr class=listodd>
		</c:if>
		<c:if test="${indexCompensate.index %2!= 0}">
			<tr class=listeven>
		</c:if>
		<tr class=common>
			<td align="center">
				<c:if test="${prpLcompensate.underWriteFlag==0}">
					<s:text name="compensate.staging" />
				</c:if>
				<!-- 暂存 -->
				<c:if test="${prpLcompensate.underWriteFlag==1}">
					<s:text name="compensate.hepeiPass" />
				</c:if>
				<!-- 核赔通过 -->
				<c:if test="${prpLcompensate.underWriteFlag==2}">
					<s:text name="compensate.hepeiNoPass" />
				</c:if>
				<!-- 核赔不通过 -->
				<c:if test="${prpLcompensate.underWriteFlag==3}">
					<s:text name="compensate.withoutHePei" />
				</c:if>
				<!-- 无需核赔 -->
				<c:if test="${prpLcompensate.underWriteFlag==9}">
					<s:text name="compensate.stayHePei" />
				</c:if>
				<!-- 待核赔 -->
			</td>
			<td align="center">
				<a href="/claim/compensate/compensateFinishQueryList.do?prpLcompensateCompensateNo=${prpLcompensate.compensateNo}&nodeType=compp&editType=SHOW&riskCode=${prpLcompensate.riskCode}">
					${prpLcompensate.compensateNo}</a>
			</td>
			<td align="center">${prpLcompensate.claimNo}</td>
			<td align="center">${prpLcompensate.policyNo}</td>
			<td align="center">
				<fmt:formatNumber value='${prpLcompensate.sumPaid}' pattern='#' />
			</td>
		</tr>
	</c:forEach>
	<tr class="listtail">
		<td colspan="5">
			<s:text name="compensate.common1" />
			<!--共有-->
			<c:out value='${fn:length(prpLcompensate.compensateList)}' />
			<s:text name="compensate.common2" />
			<!-- 条满足条件的记录 -->
		</td>
	</tr>
</table>
</tr>
</table>
