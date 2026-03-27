<%-- ***************************************************************************
mantis：CLM0241，處理人員： DP0713 ，需求單編號：強制任意批次核賠功能新增(view-content)
****************************************************************************
--%>
<%@ include file="/common/taglibs.jsp"%>
<script language='javascript'>
	function exportToExcel(){
		var oldAction = fm.action;
		fm.action="/claim/heapToExcel.do?editType=heapToExcel";
	    fm.submit();
	    fm.action=oldAction;
	}
</script>
<tr class=common>
	<td colspan="4">
		<input type=hidden name="hiBusinessNo" value="${wfLog.businessNo}">
		<input type=hidden name="hiBusinessType" value="${wfLog.businessType}">
		<input type="hidden" name="riskUnitFlag" value="1">
		<%-- 是否需要拆分危险单位标志1为允许--%>
		<input type="hidden" name="hiRiskLevel" value="">
		<input type="hidden" name="hiRetCurrency" value="">
		<input type="hidden" name="hiRetentionValue" value="">
		<input type="hidden" name="hiDangerItemKind" value="">
		<input type="hidden" name="hiDangerFlag" value="">
		<input type="hidden" name="hiRiskLevelDesc" value="">
		<input type="hidden" name="includeAccident" value="Y">
		<span id="spanInfo">
			<%--投保单信息 --%>
			<table width=100%>
				<tr>
					<td width="100%">
						<table cellpadding="5" cellspacing="1" class="common" align="center" style="width: 100%">
							<%-- 保单信息 --%>
							<c:if test="${requestScope.prpCmain!=null}">
								<tr class=listtitle>
									<td colspan="4">
										<s:text name="undwrt.PolicyPaymentInformation" />
										<%-- 保单摘要和赔付摘要信息 --%>
									</td>
								</tr>
								<tr>
									<td class=title4>
										<s:text name="db.prpDdbs.riskCode" />：<%--险种 --%>
									</td>
									<td class=input4>${requestScope.prpCmain.riskCode}</td>
									<td class=title4>
										總賠付件數：
									</td>
									<td class=input4>${requestScope.sumThisPaidCount} 件</td>
								</tr>
								<tr>
									<td class=title4>
										<s:text name="certainLoss.prpLcheck.riskCName" />
										<%-- 险种名称 --%>
									</td>
									<td class=input4>${requestScope.riskCodeName}</td>
									<td class=title4>
										總賠付金額：
									</td>
									<td class=input4><fmt:formatNumber value='${requestScope.sumThisPaidAmount}' pattern='#,###.##'/> 元</td>
								</tr>
								<tr>
									<td class=title4>
										賠款速度:
									</td>
									<td class=input4>
										<select class="common" id="prpLcompensateSpeedFlag" name="prpLcompensateSpeedFlag" onchange=""  >
											<option value="Y">Y-速賠件</option>
											<option value="N">N-正常件</option>
										</select>
									</td>
									<td class=title4>
										本次核賠案件清單下載：
									</td>
								    <TD class="title4" width=20%>
								    	<input type=button id="button" name="urgentCaseButton" class='' value="導出爲Excel" onClick="exportToExcel();" style="color: #000000;background-image:url(${ctx}/images/BgLongButton.gif);text-align: center;height: 24px;width: 150px;border: none;"
								    	<c:if test="${requestScope.content!=''}">disabled</c:if> >
								    </TD>
								</tr>
								
								
								<tr>
									<td class="input4" colspan="4">
										<font color='red'>
											<marquee behavior=alternate scrollamount=2>
												${requestScope.content}
											</marquee>
										</font>
									</td>
								</tr>
								
								<c:set var="sumLoss" value="0.00" scope="page" />
								
								
							</c:if>
						</table>
					</td>
				</tr>
				<%-- 原始标的信息 DEL--%>
			</table>
		</span>
	</td>
</tr>
