<%@	page contentType="text/html; charset=GBK"	language="java"	%>
<%@ include file="/common/taglibs.jsp"%>
<%@page import="com.sinosoft.sysframework.common.datatype.DateTime"%>
<script language='javascript'>
//在下面加入本页自定义的JavaScript方法
/*
        插入一条新的之後的处理（可选方法）
      */

function afterInsertRegistExt() {
	setPrpLregistExtSerialNo();
}

/*
        删除本条WarnRegion之後的处理（可选方法）
      */

function afterDeleteRegistExt(field) {
	setPrpLregistExtSerialNo();
}

/**
 * 设置setPrpLregistExtSerialNo
 */

function setPrpLregistExtSerialNo() {
	var count = getElementCount("prpLregistExtSerialNo");
	for (var i = 0; i < count; i++) {
		//alert("看看什么时候运行?count="+count+"  i="+i);
		if (count != 1) {
			fm.prpLregistExtSerialNo[i].value = i;
		}
	}
}

function relateCallCenter() {
	var registNo = fm.prpLregistRegistNo.value;
	var newWindow = window.open("/claim/registCallCenterServiceList.do?registNo=" + registNo, "NewWindow", "width=640,height=300,top=0,left=0,toolbar=no,location=no,directories=no,menubar=no,scrollbars=yes,resizable=yes,status=no");
}
</script>
<input type="hidden" id="prpLregistExtRegistNo" name="prpLregistExtRegistNo" value="${requestScope.prpLregistExt.id.registNo}">
<input type="hidden" id="prpLregistExtRiskCode" name="prpLregistExtRiskCode" value="${requestScope.prpLregistExt.riskCode}">
<table class=subtable cellpadding="0" cellspacing="1">
	<tr style="display: none">
		<td>
			<table class="common" align="center" width="100%">
				<!--表示显示多行的-->
				<tr>
					<td class="common" colspan="4" style="text-align: left;">
						<%--  备　　注 --%>
						<s:text name="certainLoss.prpLscheduleMainWF.remark" />
						<br>
						<table class="common" style="display: none" id="RegistExt_Data" cellspacing="1" cellpadding="5">
							<tbody>
								<tr>
									<td class="input" style="width: 10%">
										<input type="hidden" name="prpLregistExtFlag">
										<input type="text" class="readonly" readonly name="prpLregistExtSerialNo" title="序號">
									</td>
									<%
					                    String time1 = new DateTime(DateTime.current().toString(),DateTime.YEAR_TO_DAY).toString();
					                    String time2 = new DateTime(DateTime.current().toString(),DateTime.YEAR_TO_SECOND).getHour()+"时"+new DateTime(DateTime.current().toString(),DateTime.YEAR_TO_SECOND).getSecond()+ "分";
					                %>
									<td class="input" style="width: 20%">
										<input type="text" name="prpLregistExtInputDate" class="readonly" readonly style="width: 45%" value="<%=time1%>">
										<input type="text" name="prpLregistExtInputHour" class="readonly" readonly style="width: 45%" value="<%=time2%>">
									</td>
									<td class="input" style="width: 10%">
										<c:if test="${param.editType != 'SHOW'}">
											<input type="text" name="prpLregistExtOperatorCode" class="readonly" readonly style="width: 90%" value="${sessionScope.user.userCode}">
										</c:if>
									</td>
									<td class="input" style="width: 55%">
										<input type="text" name="prpLregistExtContext" class="input" style="width: 100%">
									</td>
									<td class="input" style='width: 4%' align="center">
										<div>
											<input type=button name="buttonRegistExtDelete" class=smallbutton onclick="deleteRow(this,'RegistExt')" value="-" style="cursor: hand">
										</div>
									</td>
								</tr>
							</tbody>
						</table>
						<%-- 多行输入展现域 --%>
						<table class="common" id="RegistExt" cellspacing="1" cellpadding="5">
							<thead>
								<tr>
									<td class="centertitle" style="width: 10%">
										<%--  序号 --%>
										<s:text name="certainLoss.prpLscheduleMainWF.number" />
									</td>
									<td class="centertitle" style="width: 20%">
										<%--  时间 --%>
										<s:text name="certainLoss.prpLscheduleMainWF.time" />
									</td>
									<td class="centertitle" style="width: 10%">
										<%--  操作员 --%>
										<s:text name="certainLoss.prpLscheduleMainWF.oprater" />
									</td>
									<td class="centertitle" style="width: 55%">
										<%--  内容--%>
										<s:text name="certainLoss.prpLscheduleMainWF.content" />
									</td>
									<td class="centertitle" style="width: 4%">&nbsp;</td>
								</tr>
							</thead>
							<tfoot>
								<tr>
									<td class="title" colspan=4>
										<s:text name="certainLoss.prpLscheduleMainWF.prompt" />
									</td>
									<td class="title" align="right" style="width: 4%">
										<div align="center">
											<c:choose>
												<c:when test="${param.editType == 'SHOW' || param.editType == 'DELETE'}">
													<input type="button" disabled value="+" class=smallbutton name="buttonRegistExtInsert">
												</c:when>
												<c:otherwise>
													<input type="button" value="+" class=smallbutton onclick="insertRow('RegistExt')" name="buttonRegistExtInsert" style="cursor: hand">
												</c:otherwise>
											</c:choose>
										</div>
									</td>
								</tr>
							</tfoot>
							<tbody>
								<c:forEach var="registExt" items="${requestScope.prpLregistExt.registExtList}" varStatus="indexRegistExt">
									<tr>
										<td class="input" style="width: 10%">
											<input type="hidden" name="prpLregistExtFlag" value="">
											<input type="text" id="prpLregistExtSerialNo" name="prpLregistExtSerialNo" class="readonly" readonly value="${pageScope.registExt.id.serialNo}">
										</td>
										<td class="input" style="width: 20%">
											<input type="text" id="prpLregistExtInputDate" name="prpLregistExtInputDate" class="readonly" readonly style="width: 45%" value="${pageScope.registExt.inputDate}">
											<input type="text" id="prpLregistExtInputHour" name="prpLregistExtInputHour" class="readonly" readonly style="width: 45%" value="${pageScope.registExt.inputHour}">
										</td>
										<td class="input" style="width: 10%">
											<input type="text" id="prpLregistExtOperatorCode" name="prpLregistExtOperatorCode" class="readonly" readonly style="width: 90%" value="${pageScope.registExt.operatorCode}">
										</td>
										<td class="input" style="width: 55%">
											<input type="text" id="prpLregistExtContext" name="prpLregistExtContext" class="readonly" readonly style="width: 100%" value="${pageScope.registExt.context}">
										</td>
										<td class="input" style='width: 4%' align="center">
											<div>
												<c:choose>
													<c:when test="${param.editType == 'SHOW' || param.editType == 'DELETE'}">
														<input type=button disabled name="buttonRegistExtDelete" class="smallbutton" value="-">
													</c:when>
													<c:otherwise>
														<input type=button name="buttonRegistExtDelete" class="smallbutton" onclick="deleteRow(this,'RegistExt')" value="-" style="cursor: hand">
													</c:otherwise>
												</c:choose>
											</div>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
