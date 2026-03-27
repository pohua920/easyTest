
<%@page import="ins.framework.common.DateTime"%>
<%--
****************************************************************************
* DESC       ：添加案件补充说明页面
* AUTHOR     ：理赔组
* CREATEDATE ： 2004-12-07
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<!--建立显示的录入条，可以收缩显示的-->
<script language='javascript'>
<!--
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
		for ( var i = 0; i < count; i++) {
			//alert("看看什么时候运行?count="+count+"  i="+i); 
			if (count != 1) {
				fm.prpLregistExtSerialNo[i].value = i;
			}
		}
	}
//-->
</script>
<input type="hidden" name="prpLregistExtRegistNo" value="${prpLregistExt.id.registNo}">
<input type="hidden" name="prpLregistExtRiskCode" value="${prpLregistExt.riskCode}">
<table class="common" align="center" width="100%">
	<!--表示显示多行的-->
	<tr style="display: none">
		<td class="subformtitle" colspan="4">
			<div align="left">
				<img style="cursor: hand;" src="${ctx }/images/butCollapseBlue.gif" name="RegistExtImg" onclick="showPage(this,spanRegistExt)">
				<s:text name="title.linkRecord.claimRelateRecord " />
				<br>
				<%--理赔联系记录 --%>
			</div>
			<span style="display: none">
				<table class="common" style="display: none" id="RegistExt_Data" cellspacing="1" cellpadding="0">
					<tbody>
						<tr class=common>
							<td>
								<input type="hidden" name="prpLregistExtFlag">
								<input type="text" class="readonly" readonly name="prpLregistExtSerialNo" description="<s:text name='certainLoss.prpLscheduleMainWF.number'/>"><%--序号--%>
							</td>
							<%
								String time1 = new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_DAY).toString();
								String time2 = new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_SECOND).getHour() + "时" + new DateTime(DateTime.current().toString(), DateTime.YEAR_TO_SECOND).getSecond() + "分";
							%>
							<td>
								<input type="text" name="prpLregistExtInputDate" class="readonly" readonly style="width: 45%" value="<%=time1%>">
								<input type="text" name="prpLregistExtInputHour" class="readonly" readonly style="width: 45%" value="<%=time2%>">
							</td>
							<td>
								<input type="text" name="prpLregistExtOperatorCode" class="readonly" readonly style="width: 90%" value="${user.userCode}">
							</td>
							<td>
								<input type="text" name="prpLregistExtContext" class="input" style="width: 100%">
							</td>
							<td style='width: 4%' align="center">
								<div>
									<input type=button name="buttonRegistExtDelete" class="smallbutton" onclick="deleteRow(this,'RegistExt')" value="-" style="cursor: hand">
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</span> <span id="spanRegistExt" style="display: none" cellspacing="1" cellpadding="0"> <%-- 多行输入展现域 --%>
				<table class="common" style="width: 100%" id="RegistExt" cellpadding="5" cellspacing="1">
					<thead>
						<tr>
							<td class="centertitle" style="width: 6%">
								<s:text name="db.prpDrate.serialNo" />
								<%--序号 --%>
							</td>
							<td class="centertitle" style="width: 20%">
								<s:text name="currentTime" />
								<%--时间 --%>
							</td>
							<td class="centertitle" style="width: 10%">
								<s:text name="db.utiTtyRecord.userName" />
								<%--操作员 --%>
							</td>
							<td class="centertitle" style="width: 60%">
								<s:text name="db.utiFile.text" />
								<%--内容 --%>
							</td>
							<td class="title" style="width: 4%">&nbsp;</td>
						</tr>
					</thead>
					<tfoot>
						<tr>
							<td class="title" colspan=4 style="width: 96%">
								<s:text name="regist.query2" />
								<%--(按"+"号键增加理赔联系记录信息，按"-"号键删除信息) --%>
							</td>
							<td class="title" align="right" style="width: 4%">
								<div align="center">
									<s:if test="#request.editType=='SHOW'||#request.editType=='DELETE'"></s:if>
									<input type="button" value="+" class="smallbutton" name="buttonDriverInsert">
									<s:else>
										<input type="button" value="+" class="smallbutton" onclick="insertRow('RegistExt')" name="buttonDriverInsert" style="cursor: hand">
									</s:else>
								</div>
							</td>
						</tr>
					</tfoot>
					<!--   </tfoot> -->
					<tbody>
						<c:if test="!empty prpLregistExt.registExtList">
							<c:forEach items="prpLregistExt.registExtList" var="registExt1" varStatus="registExtStatus">
								<tr class=common>
									<td>
										<input type="hidden" name="prpLregistExtFlag" value="">
										<input type="text" name="prpLregistExtSerialNo" class="readonly" readonly value="${registExt1.id.serialNo }">
									</td>
									<td>
										<input type="text" name="prpLregistExtInputDate" class="readonly" readonly style="width: 45%" value="${registExt1.inputDate}">
										<input type="text" name="prpLregistExtInputHour" class="readonly" readonly style="width: 45%" value="${registExt1.inputHour}">
									</td>
									<td>
										<input type="text" name="prpLregistExtOperatorCode" class="readonly" readonly style="width: 90%" value="${registExt1.operatorCode}">
									</td>
									<td>
										<input type="text" name="prpLregistExtContext" class="readonly" readonly style="width: 100%" value="${registExt1.context}">
									</td>
									<td style='width: 4%' align="center">
										<div>
											<s:if test="#request.editType=='SHOW'||#request.editType=='DELETE'">
												<input disabled type=button name="buttonRegistExtDelete" class="smallbutton" value="-">
											</s:if>
											<s:else>
												<input type=button name="buttonRegistExtDelete" class="smallbutton" onclick="deleteRow(this,'RegistExt')" value="-" style="cursor: hand">
											</s:else>
										</div>
									</td>
								</tr>
							</c:forEach>
						</c:if>
					</tbody>
				</table>
			</span>
		</td>
	</tr>
</table>
