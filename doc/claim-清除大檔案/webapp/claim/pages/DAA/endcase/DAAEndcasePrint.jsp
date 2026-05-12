<%--
****************************************************************************
* DESC       ：打印按钮画面
* AUTHOR     : 中科软
* CREATEDATE ：2013-02-03
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<table class=common cellpadding="5" cellspacing="1">
	<tr>
	<tr class=listtitle>
		<td colspan="4">
			<s:text name="endcase.printDocuments" />
		</td>
		<!--打印案件文档-->
	</tr>
	<tr>
		<td align="center" class="input">
			<input type="button" name=buttonPrint1 class='bigbutton' value="<s:text name='button.reportRecords.value'/>" onclick="return printForm(this,'Regist');">
			<!--保险报案记录-->
		</td>
		<!--<td align="center" class="input">  <input type="button" name=buttonPrint2 class='bigbutton' value="<s:text name='button.policiesEndorsements.value'/>" onclick="return printForm(this,'HistoryFile');">原始保单及批单
    </td>-->
		<!--<td align="center" class="input"><input type="button" name=buttonPrint3 class='bigbutton' value="<s:text name='button.insurancePolicy.value'/>" onclick="return printForm(this,'FileOnRisk');">出险时保单
    </td>-->
		<td align="center" class="input">
			<input type="button" name=buttonPrint4 class='bigbutton' value="<s:text name='button.paymentForm.value'/>" onclick="return printForm(this,'BzPay');">
			<!--预付赔款审批表-->
		</td>
		<td align="center" class="input">
			<input type="button" name=buttonPrint13 class='bigbutton' value="<s:text name='button.accountBook.value'/>" onclick="return relate3(this,'ClaimStatement');">
			<!--赔款计算书-->
		</td>
	</tr>
	<!--
  <tr> 
    <c:if test="${prpLclaim.caseType==2}">
    <td align="center" class="input">  <input type="button" name=buttonPrint5 class='bigbutton' value="<s:text name='button.noticeClaim.value'/> " onclick="return printForm(this,'Cancelnotice');">拒赔通知书
    </td>
    <td align="center" class="input"> <input type="button" name=buttonPrint6 class='bigbutton' value="<s:text name='button.cancelReport.value'/>" onclick="return printForm(this,'Canceltrans');">拒赔案件报告书
    </td>
    </c:if>
    <c:if test="${prpLclaim.caseType!=2}">
     <td align="center" class="input">  <input type="button" name=buttonPrint5 class='bigbutton' value="<s:text name='button.noticeClaim.value'/> " disabled onclick="return printForm(this,'Cancelnotice');">拒赔通知书
    </td>
    <td align="center" class="input"> <input type="button" name=buttonPrint6 class='bigbutton' value="<s:text name='button.cancelReport.value'/>" disabled onclick="return printForm(this,'Canceltrans');">拒赔案件报告书
    </td>
    </c:if>
    
    <td align="center" class="input"><input type="button" name=buttonPrint12 class='bigbutton' value="<s:text name='button.propertyLosses.value'/>" onclick="return printForm(this,'PropList');">财产损失确认书
     <input type="button" name=7 class='bigbutton' value="<s:text name='button.decisionClaims.value'/> " disabled style="display:none" onclick="return printForm(this,'LossSimple');">速决赔案损失确认书
    </td>
    <td align="center" class="input"> <input type="button" name=buttonPrint8 class='bigbutton' value="<s:text name='button.confirmationLoss.value'/>" onclick="return printForm(this,'Loss');">损失情况确认书
    </td>
  </tr>
  <tr> 
    <td align="center" class="input"> <input type="button" name=buttonPrint9 class='bigbutton' value="<s:text name='button.projectsList.value'/>" onclick="return printForm(this,'ComponentList');">零部件更换项目清单
    </td>
    <td align="center" class="input"> <input type="button" name=buttonPrint10 class='bigbutton' value="<s:text name='button.repairProjects.value'/> " onclick="return printForm(this,'RepairList');">修理项目清单
    </td>
    <td align="center" class="input"> <input type="button" name=buttonPrint11 class='bigbutton' value="<s:text name='button.projectsListTable.value'/>" onclick="return printForm(this,'RepairAdd');">修理项目清单附表
    </td>
    <td align="center" class="input"> 
    </td>
  </tr>
  <tr> 
    <td align="center"class="input"> <input type="button" name=buttonPrint13 class='bigbutton' value="<s:text name='button.accountBook.value'/>"onclick="return relate3('ClaimStatement');">赔款计算书
    </td>
    <td align="center"class="input"> <input type="button" name=buttonPrint14 class='bigbutton' value="<s:text name='button.bookReportPage.value'/> " onclick="return relate3('CompensateAdd');">赔款计算书附页
    </td>
    <c:if test="${prpLclaim.caseType==0}">
     <td align="center" class="input"> <input type="button" name=buttonPrint16 class='bigbutton' value="<s:text name='button.noticeCancellation.value'/>" onclick="return printForm(this,'Pressnotice');">注销通知书
    </td>
    </c:if>
    <c:if test="${prpLclaim.caseType!=0}">
     <td align="center" class="input"> <input type="button" name=buttonPrint16 class='bigbutton' value="<s:text name='button.noticeCancellation.value'/>" onclick="return printForm(this,'Pressnotice');">注销通知书
    </td>
    </c:if>
    <td align="center" class="input" > <input type="button" name=buttonPrint16 class='bigbutton' style="display:none" value="<s:text name='button.closingNotice.value'/>" onclick="return printForm(this,'PressnoticeEnd');">结案催告通知书
    </td>
  </tr>
  <tr> 
    <td align="center" class="input"> <input type="button" name=buttonPrint17 class='bigbutton' value="<s:text name='button.closingReport.value'/>" onclick="return printForm(this,'Endcase');">结案报告书
    </td>
    <td align="center" class="input"> <input type="button" name=buttonPrint18 class='bigbutton' value="<s:text name='button.completionReportPage.value'/>" onclick="return printForm(this,'EndcaseAdd');">结案报告书附页
    </td>
    <td align="center"class="input"> <input type="button" name=buttonPrint19 class='bigbutton' value="<s:text name='button.receiveNotice.value'/> " onclick="return relate3('Drawnotice');">领取赔款通知书
    </td>
    <td align="center" class="input"></td>
  </tr> 
  -->
</table>