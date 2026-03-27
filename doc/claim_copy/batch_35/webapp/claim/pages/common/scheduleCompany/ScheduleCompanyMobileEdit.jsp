<%--
****************************************************************************
* DESC       ：调度中心手机信息画面
* AUTHOR     ： Sinosoft 
* CREATEDATE ： 2005-09-01
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

		function afterInsertCompanyMobile() {
			setPrpLregistExtSerialNo();
		}
		
		/*
		        删除本条WarnRegion之後的处理（可选方法）
		      */
		
		function afterDeleteCompanyMobile(field) {
			setPrpLregistExtSerialNo();
		}
		
		/** 
		 * 设置setPrpLregistExtSerialNo
		 */
		
		function setPrpLregistExtSerialNo() {
			var count = getElementCount("prpLscheduleCompanyMobileSerialNo");
			for (var i = 0; i < count; i++) {
		
				if (count != 1) {
					fm.prpLscheduleCompanyMobileSerialNo[i].value = i;
				}
			}
		}
		   //-->      
    </script>    
 <%

          		     Collection collection = (Collection)request.getAttribute("dayList"); 
%>
<table class="common" align="center" width="100%">
	<!--表示显示多行的-->
	<tr>
		<td class="subformtitle" colspan="4">
			<img style="cursor: hand;" src="/claim/images/butExpandBlue.gif" name="CompanyMobileImg" onclick="showPage(this,spanCompanyMobile)">
			<s:text name="scheduleCompany.relationPhoneInformation " />
			<%--联系手机信息 --%>
			<br> <span style="display: none">
				<table class="common" style="display: none" id="CompanyMobile_Data" cellspacing="1" cellpadding="0">
					<tbody>
						<tr>
							<td class="input">
								<input type="hidden" name="prpLscheduleCompanyMobileFlag">
								<input type="text" class="readonly" readonly name="prpLscheduleCompanyMobileSerialNo" description="序号">
							</td>
							<td class="input">
								<!-- mantis： CLM0017，處理人員：Sam，需求單編號：CLM0017，原住名姓名調整作業_車 -->
								<input type="text" class="input" name="prpLscheduleCompanyMobileLinkerName" description="联系人" class="input" maxlength="100" style="width: 90%" value="">
							</td>
							<td class="input">
								<input type="text" name="prpLscheduleCompanyMobileMobileNo" class="input" maxlength="40" style="width: 95%" value="">
							</td>
							<td class="input">
								<input type="text" name="prpLscheduleCompanyMobileRemark" class="input" maxlength="35" style="width: 90%" value="">
							</td>
							<td class="input">
								<select name="onDutyDay" styleClass="three">
									<%
                      Iterator prpLPartCodeNameList = collection.iterator();
                      while (prpLPartCodeNameList.hasNext()){
                      LabelValueBean labelValueBean = (LabelValueBean)prpLPartCodeNameList.next();
                   %>
									<option value="<%= labelValueBean.getValue() %>"><%= labelValueBean.getLabel() %></option>
									<%
                       }
                   %>
								</select>
							</td>
							<td class="input" style='width: 4%' align="center">
								<div>
									<input type=button name="buttonCompanyMobileDelete" onclick="deleteRow(this,'CompanyMobile')" value="-" style="cursor: hand">
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</span> <span id="spanCompanyMobile" style="display:" cellspacing="1" cellpadding="0"> <%-- 多行输入展现域 --%>
				<table class="common" style="width: 100%" id="CompanyMobile">
					<thead>
						<tr>
							<td class="centertitle" style="width: 6%">
								<s:text name="db.prpDrate.serialNo " />
								<%--序号 --%>
							</td>
							<td class="centertitle" style="width: 10%">
								<s:text name="db.prpLregist.linkerName " />
								<%--联系人 --%>
							</td>
							<td class="centertitle" style="width: 30%">
								<s:text name="scheduleCompany.mobilePhone " />
								<%--手机号 --%>
							</td>
							<td class="centertitle" style="width: 40%">
								<s:text name="db.prpDcompany.remark " />
								<%--备注 --%>
							</td>
							<td class="centertitle" style="width: 10%">
								<s:text name="scheduleCompany.onDuty" />
								<%--值班 --%>
							</td>
							<td class="title" style="width: 4%">&nbsp;</td>
						</tr>
					</thead>
					<tfoot>
						<tr>
							<td class="title" colspan=5 style="width: 96%">
								<s:text name="scheduleCompany.query1 " />
								<%--(按"+"号键增加案件补充说明，按"-"号键删除信息) --%>
							</td>
							<td class="title" align="right" style="width: 4%">
								<div align="center">
									<input type="button" value="+" onclick="insertRow('CompanyMobile')" name="buttonDriverInsert" style="cursor: hand">
								</div>
							</td>
						</tr>
					</tfoot>
					</tfoot>
					<tbody>
						<% int indexCompanyMobile=0;%>
						<logic:notEmpty name="scheduleCompanyDto" property="prpLschecduleCompanyMobileDtoList">
							<logic:iterate id="MobileList1" name="scheduleCompanyDto" property="prpLschecduleCompanyMobileDtoList">
								<tr>
									<td class="input">
										<input type="hidden" name="prpLscheduleCompanyMobileFlag" value="">
										<input type="text" name="prpLscheduleCompanyMobileSerialNo" class="readonly" readonly value="<bean:write name='MobileList1' property='serialNo'/>">
									</td>
									<td class="input">
										<!-- mantis： CLM0017，處理人員：Sam，需求單編號：CLM0017，原住名姓名調整作業_車 -->
										<input type="text" class="input" name="prpLscheduleCompanyMobileLinkerName" description="联系人" class="input" maxlength="100" style="width: 90%"
											value="<bean:write name='MobileList1' property='linkerName'/>">
									</td>
									<td class="input">
										<input type="text" name="prpLscheduleCompanyMobileMobileNo" class="input" maxlength="40" style="width: 95%" value="<bean:write name='MobileList1' property='mobileNo'/>">
									</td>
									<td class="input">
										<input type="text" name="prpLscheduleCompanyMobileRemark" class="input" maxlength="35" style="width: 90%" value="<bean:write name='MobileList1' property='remark'/>">
									</td>
									<td class="input">
										<html:select name="MobileList1" property="onDutyDay" styleClass="three">
											<html:options collection="dayList" property="value" labelProperty="label" />
										</html:select>
									<td class="input" style='width: 4%' align="center">
										<div>
											<input type=button name="buttonCompanyMobileDelete" onclick="deleteRow(this,'CompanyMobile')" value="-" style="cursor: hand">
										</div>
									</td>
								</tr>
								<%    indexCompanyMobile++;%>
							</logic:iterate>
						</logic:notEmpty>
					</tbody>
				</table>
			</span>
		</td>
	</tr>
</table>
