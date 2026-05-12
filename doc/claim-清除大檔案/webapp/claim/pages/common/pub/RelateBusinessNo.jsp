<%@ page contentType="text/html; charset=GBK"%>
<%@ page import="com.sinosoft.sysframework.reference.AppConfig"%>
<%@ include file="/common/taglibs.jsp"%>
<%@include file="/common/meta_css.jsp"%>
<html>
<head>
	<title>保單關聯訊息</title>
	<script src="/claim/common/js/showpage.js"> </script>
	<script language="javascript">
		//显示保单
		function showPolicy(strPolicyNo,strRiskCode,strComCode,strDamageDate){
			  var vURL = '/claim/pages/common/pub/PolicyShowCenter.jsp?BIZTYPE=POLICY&SHOWTYPE=SHOW&BizNo=' + strPolicyNo + '&RiskCode=' + strRiskCode + '&damageDate=' + strDamageDate;
			  document.location.href = vURL;
		}

		//显示保险卡保单
		function showPolicyCard(strPolicyNo){
			  var vURL = fm.coreURL.value  + "card/tbcbpg/UIPrPoEnCardShow.jsp?BIZTYPE=POLICY&BizNo=" + strPolicyNo + "&PolicyType=01&SHOWTYPE=SHOW";
		    document.location.href = vURL;
		}
		
		//显示批单信息
		function showPrpHeadInfo(strBizNo,strRiskCode){
		    var vURL = '<%=AppConfig.get("sysconst.Core_URL")%>' + 'endorse/browseEndorseForClaim.do?applyNo='+strBizNo+'&systemCode=claim';
		    document.location.href = vURL;
		}
		
		//显示报案信息
		function showRegistInfo(strRegistNo,strRiskCode){
		    var vURL = '<%=request.getContextPath()%>/registFinishQueryList.do?prpLregistRegistNo=' + strRegistNo + '&riskCode=' + strRiskCode + '&editType=SHOW&ifclose=true';
		    document.location.href = vURL;
		}
		
		//显示流程图信息
		function showWorkFlower(strWorkFlowId,registNo){
		    var vURL = '${ctx}/workflow/swfFlowBeforeQuery.do?swfLogFlowID=' + strWorkFlowId +'&ifclose=true' + '&registNo=' + registNo;
		    document.location.href = vURL;
		}
		//为避免js报错，写一个空方法
		function showRcDateTime(rcField,format){
			
		}
	</script>	
</head>
        <!-- 增加核心地址 -->
        <%
        String prpallUrl = AppConfig.get("sysconst.Core_URL");
        %>
        <input type="hidden" name="prpallUrl" value="<%=prpallUrl%>">
		<s:if test="#request.prpCmain.startHour==0">
			<s:set var="startHour" value="%{getText('modifySumClaim.comeEffect')}" scope="page"></s:set>
			<%-- 零时起至 --%>
		</s:if>
		<s:elseif test="#request.prpCmain.startHour==12">
			<s:set var="startHour" value="%{getText('regist.from')}" scope="page"></s:set>
			<%--十二时起至 --%>
		</s:elseif>
		<s:elseif test="#request.prpCmain.startHour==24">
			<s:set var="startHour" value="%{getText('regist.start')}" scope="page"></s:set>
			<%-- 二十四时起 --%>
		</s:elseif>
		<s:else>
			<s:set var="startHour" value="" scope="page"></s:set>
		</s:else>
		<s:if test="#request.prpCmain.endHour==0">
			<s:set var="endHour" value="%{getText('regist.until')}" scope="page"></s:set>
			<%--零时止  --%>
		</s:if>
		<s:elseif test="#request.prpCmain.endHour==12">
			<s:set var="endHour" value="%{getText('regist.end')}" scope="page"></s:set>
			<%-- 十二时止 --%>
		</s:elseif>
		<s:elseif test="#request.prpCmain.endHour==24">
			<s:set var="endHour" value="%{getText('modifySumClaim.hourEnd')}" scope="page"></s:set>
			<%--二十四时止  --%>
		</s:elseif>
		<s:else>
			<s:set var="endHour" value="" scope="page"></s:set>
		</s:else>
<body>
	<form name="fm" action="/claim/registBeforeQuery.do" method="post">
		<table class="common" cellpadding="4" cellspacing="1">
			<input type="hidden" name="coreURL" value="<%=AppConfig.get("sysconst.Core_URL")%>">
			<tr>
				<td colspan="2" class="formtitle">保單訊息</td>
			</tr>
			<tr>
				<td class="title" align="center" width="10%">保單號碼</td>
				<td class="input" align="center" width="90%">
				<c:if test="${prpCmain.policySort != 'I'}">
					<a href="javascript:showPolicy('${prpCmain.policyNo}','${prpCmain.riskCode}','${prpCmain.comCode}','${strDamageDate}')" >
				</c:if>
				<c:if test="${prpCmain.policySort == 'I'}">
					<a href="javascript:showPolicyCard('${prpCmain.policyNo}')" >
				</c:if>
						${prpCmain.policyNo}
					</a>
				</td>
			</tr>
			<tr>
				<td class="title" align="center">保險期限</td>
				<td class="input" align="center">
					<%-- ${prpCmain.startDate} --%>
					<rc:rcDate name="startDate" class="readonly" readonly="true" wdatePicker="false"  style="width:85px" value="${prpCmain.startDate}" />  ${startHour}
					<%-- ${prpCmain.endDate} --%>
					<rc:rcDate name="endDate" class="readonly" readonly="true" wdatePicker="false"  style="width:85px" value="${prpCmain.endDate}" />
					 ${endHour}
				</td>
			</tr>
			<tr>
				<td class="title" align="center">被保險人</td>
				<td class="input" align="center">
					${prpCmain.insuredName}
				</td>
			</tr>
		</table>
		<table class="common" cellpadding="3" cellspacing="1">
			<tr>
				<td colspan="5" class="formtitle">批單訊息</td>
			</tr>
			<tr>
				<td class="centertitle">序號</td>
				<td class="centertitle">批單號碼</td>
				<td class="centertitle">收件日期</td>
				<td class="centertitle">生效日期</td>
				<td class="centertitle">核批完成日期</td>
			</tr>
			<c:if test="${headList == '[]'}">
				<tr class=listeven>
					<td align="center" colspan="5">無批單	</td>
				</tr>
			</c:if>
			<c:if test="${headList != null}">
			<c:set var="index" value="0"></c:set>
			<c:forEach var="head" items="${headList}" >
				<tr class=listeven>
					<td align="center">${index+1}</td>
					<td align="center">
						<a href="javascript:showPrpHeadInfo('${head.endorseNo}','${head.riskCode}')" >${head.endorseNo}</a>
					</td>
					<td align="center">
						<rc:rcDate name="endorDate" class="readonly" readonly="true" wdatePicker="false" style="width:85px" value="${head.endorDate}"/>
					</td>
					<td align="center">
						<rc:rcDate name="validDate" class="readonly" readonly="true" wdatePicker="false" style="width:85px" value="${head.validDate}"/>
					</td>
					<td align="center">
						<%-- ${head.underWriteEndDate}--%>
						<rc:rcDate name="underWriteEndDate" class="readonly" readonly="true" wdatePicker="false"  style="width:85px" value="${head.underWriteEndDate}" /> 
					</td>
				</tr>
				<c:set var="index" value="${index+1}"></c:set>
			</c:forEach>
			</c:if>
		</table>
		<table class="common" cellpadding="4" cellspacing="1">
			<tr>
				<!-- mantis：CLM0231，處理人員：DP0713，需求單編號：新核心-傷害險高保額新商品檢核  -->
				<td colspan="${prpCmain.riskCode == 'PA'?6:5}" class="formtitle">理賠訊息</td>
			</tr>
			<tr>
				<td class="centertitle">序號	</td>
				<td class="centertitle">備案號碼</td>
				<!-- mantis：CLM0231，處理人員：DP0713，需求單編號：新核心-傷害險高保額新商品檢核 START -->
				<c:if test="${prpCmain.riskCode == 'PA'}">
					<td class="centertitle">立案號碼</td>
				</c:if>
				<!-- mantis：CLM0231，處理人員：DP0713，需求單編號：新核心-傷害險高保額新商品檢核END -->
				<td class="centertitle">出險時間</td>
				<td class="centertitle">賠付金額</td>
				<td class="centertitle">流程圖</td>
			</tr>
			<c:if test="${registList == '[]'}">
				<tr class=listeven>
					<td align="center" colspan="5">無理賠訊息</td>
				</tr>
			</c:if>
			<c:if test="${registList != '[]'}">
				<c:set var="index2" value="0"></c:set>
				<c:forEach var="regist" items="${registList}" >
					<tr class=listeven>
						<td align="center">${index2+1}</td>
						<td align="center">
							<a href="javascript:showRegistInfo('${regist.registNo}','${regist.riskCode}')" >
								${regist.registNo}
							</a>
						</td>
						<!-- mantis：CLM0231，處理人員：DP0713，需求單編號：新核心-傷害險高保額新商品檢核 START -->
						<c:if test="${prpCmain.riskCode == 'PA'}">
							<td align="center">
								<a href="javascript:showRegistInfo('${regist.registNo}','${regist.riskCode}')" >${claimNo}</a>
							</td>
						</c:if>
						<!-- mantis：CLM0231，處理人員：DP0713，需求單編號：新核心-傷害險高保額新商品檢核END -->
						<td align="center">
							<%--${regist.damageStartDate} --%>
							<rc:rcDate name="damageStartDate" class="readonly" readonly="true" wdatePicker="false"  style="width:80px" value="${regist.damageStartDate}" /> 
						</td>
						<td align="center">
						<fmt:formatNumber value="${regist.compensateFeeDto.sumPaid}" pattern="#" />
						</td>
						<td align="center">
							<a href="javascript:showWorkFlower('${regist.workFlowId}','${regist.registNo}')" >
								查看
							</a>
						</td>
					</tr>
					<c:set var="index2" value="${index2+1}"></c:set>
				</c:forEach>
				</c:if>
		</table>
	</form>
</body>
</html>
