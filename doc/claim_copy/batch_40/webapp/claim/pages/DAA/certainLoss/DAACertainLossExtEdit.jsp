
<%--
****************************************************************************
* DESC       ：添加定核损补充说明页面
* AUTHOR     ：理赔组
* CREATEDATE ： 2013-02-20
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<!--建立显示的录入条，可以收缩显示的-->
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="com.sinosoft.sysframework.common.datatype.DateTime"%>
<script language='javascript'>
	//<!--
	//在下面加入本页自定义的JavaScript方法
	/*
	  插入一条新的之後的处理（可选方法）
	 */

	function afterInsertCertainLossExt() {
		setPrpLverifyLossExtSerialNo();
	}

	/*
	  删除本条WarnRegion之後的处理（可选方法）
	 */

	function afterDeleteCertainLossExt(field) {
		setPrpLverifyLossExtSerialNo();
	}

	/**
	 * 设置setPrpLverifyLossExtSerialNo
	 */

	function setPrpLverifyLossExtSerialNo() {
		var count = getElementCount("prpLverifyLossExtSerialNo");
		for ( var i = 0; i < count; i++) {
			//alert("看看什么时候运行?count="+count+"  i="+i);
			if (count != 1) {
				fm.prpLverifyLossExtSerialNo[i].value = i;
			}
		}
	}
//-->
</script>
<input type="hidden" name="status" value="${param.status}" />
<input type="hidden" name="prpLverifyLossExtRegistNo" value="${requestScope.prpLverifyLossExt.id.registNo}">
<input type="hidden" name="prpLverifyLossExtRiskCode" value="${requestScope.prpLverifyLossExt.riskCode}">
<table class="common" cellspacing="1" cellpadding="5">
	<!--表示显示多行的-->
	<tr>
		<td class="common" colspan="4" style="text-align: left;">
			<!--
        <img style="cursor:hand;" src="/claim/images/butCollapseBlue.gif"
             name="CertainLossExtImg" onclick="showPage(this,spanCertainLossExt)">
             理赔联系记录<br>-->
			<img style="cursor: hand;" src="/claim/images/butExpandBlue.gif" name="CertainLossExtImg" onclick="showPage(this,spanCertainLossExt)">
			<s:text name="certainLoss.lossDetails" />
			<br>
			<!--定核损意见详细信息-->
			<span style="display: none">
				<table class="common" style="display: none" id="CertainLossExt_Data" cellspacing="1" cellpadding="5">
					<tbody>
						<tr>
							<td class="input" style="width: 6%">
								<input type="hidden" name="prpLverifyLossExtFlag">
								<input type="text" class="readonly" readonly name="prpLverifyLossExtSerialNo" description="序号">
							</td>
							<td class="input" style="width: 18%">
								<rc:rcDate style="width:40%" name="prpLverifyLossExtInputDate" class="readonly" readonly="true" wdatePicker="false" defaultValue="0"/>
								<input type="text" name="prpLverifyLossExtInputHour" class="readonly" readonly style="width: 47%" value="">
							</td>
							<td class="input" style="width: 12%">
								<c:if test="${param.editType!='SHOW'}">
									<input type="hidden" name="prpLverifyLossExtOperatorCode" value="${sessionScope.user.userCode}">
									<input type="text" name="" class="readonly" readonly style="width: 100%" value="${sessionScope.user.userName}">
								</c:if>
							</td>
							<td class="input" style="width: 15%">
								<input type="text" name="prpLverifyLossExtTitle" class="input" style="width: 100%" maxlength='20'>
							</td>
							<td class="input" style="width: 45%">
								<input type="text" name="prpLverifyLossExtContext" class="input" style="width: 100%" maxlength='125'>
							</td>
							<td class="input" style='width: 4%' align="center">
								<div>
									<input type=button name="buttonCertainLossExtDelete" class=smallbutton onclick="deleteRow(this,'CertainLossExt')" value="-" style="cursor: hand">
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</span> <span id="spanCertainLossExt" style="" cellspacing="1" cellpadding="0"> <%-- 多行输入展现域 --%>
				<table class="common" id="CertainLossExt" cellspacing="1" cellpadding="5">
					<thead>
						<tr>
							<td class="centertitle" style="width: 6%">
								<s:text name="db.prpLdriver.serialNo" />
							</td>
							<!--序号-->
							<td class="centertitle" style="width: 18%">
								<s:text name="currentTime" />
							</td>
							<!--时间-->
							<td class="centertitle" style="width: 12%">
								<s:text name="db.utiTtyRecord.userName" />
							</td>
							<!--操作员-->
							<td class="centertitle" style="width: 15%">
								<s:text name="certainLoss.views" />
							</td>
							<!--意见-->
							<td class="centertitle" style="width: 45%">
								<s:text name="db.prpLregistText.context" />
							</td>
							<!--内容-->
							<%--              <td class="centertitle" style="width:4%" >&nbsp;</td>--%>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${requestScope.prpLverifyLossExt.verifyLossExtList}" varStatus="stat" var="prpLverifyLossExt">
							<tr>
								<td class="input" style="width: 6%">
									<input type="hidden" name="prpLverifyLossExtFlag" value="">
									<input type="text" name="prpLverifyLossExtSerialNo" class="readonly" readonly value="${prpLverifyLossExt.id.serialNo}">
								</td>
								<td class="input" style="width: 18%">
									<%-- <input  type="text" name="prpLverifyLossExtInputDate" class="readonly" readonly style="width:45%" value="<fmt:formatDate value="${prpLverifyLossExt.inputDate}" pattern="yyyy-MM-dd"/>">&nbsp;--%>
									<rc:rcDate style="width:40%" name="prpLverifyLossExtInputDate" class="readonly" readonly="true" wdatePicker="false" value="${prpLverifyLossExt.inputDate}" />
									<input type="text" name="prpLverifyLossExtInputHour" class="readonly" readonly style="width: 47%" value="${prpLverifyLossExt.inputHour}">
								</td>
								<td class="input" style="width: 12%">
									<input type="text" name="prpLverifyLossExtOperatorName" class="readonly" readonly style="width: 100%" value="${prpLverifyLossExt.operatorCodeName}">
									<input type="hidden" name="prpLverifyLossExtOperatorCode" class="readonly" readonly style="width: 100%" value="${prpLverifyLossExt.operatorCode}">
								</td>
								<c:choose>
									<c:when test="${(param.status=='0'||param.status=='2'||param.status=='3') && stat.last}">
										<td class="input" style="width: 15%">
											<input type="text" name="prpLverifyLossExtTitle" class="input" style="width: 100%" value="${prpLverifyLossExt.title }">
										</td>
										<td class="input" style="width: 45%">
											<input type="text" name="prpLverifyLossExtContext" class="input" style="width: 100%" value="${prpLverifyLossExt.context }">
										</td>
									</c:when>
									<c:otherwise>
										<td class="input" style="width: 15%">
											<input type="text" name="prpLverifyLossExtTitle" class="readonly" readonly style="width: 100%" value="${prpLverifyLossExt.title }">
										</td>
										<td class="input" style="width: 45%">
											<input type="text" name="prpLverifyLossExtContext" class="readonly" readonly style="width: 100%" value="${prpLverifyLossExt.context }">
										</td>
									</c:otherwise>
								</c:choose>
								<%--                <td class="input" style='width:4%'  align="center"></td>--%>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</span>
		</td>
	</tr>
</table>
