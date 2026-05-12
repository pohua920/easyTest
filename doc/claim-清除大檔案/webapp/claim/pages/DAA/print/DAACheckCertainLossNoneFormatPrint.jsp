<%--
****************************************************************************
* DESC       ：机动车辆保险检验定损报告打印页面
* AUTHOR     ：wangli
* CREATEDATE ：2005-03-30
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%-- 初始化 --%>
<%@include file="DAACheckCertainLossNoneFormatPrintIni.jsp"%>
<html>
<head>
<title><s:text name="title.printBeforeEdit.vehicleReportPrint" /></title>
<%-- 机动车辆保险检验定损报告打印 --%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
</head>
<body bgcolor="#FFFFFF" onload="loadForm();">
	<!-- 标题部分 -->
	<table width="92%" align="center" cellspacing="0" cellpadding="0" border="0">
		<tr>
			<td colspan="2" height="40" align=top align=center style="text-align: center; font-family: 宋体; font-size: 14pt;">
				<B><s:text name="print.vehicleReportPrint" /><B> <%-- 机动车辆保险检验定损报告 --%>
			</td>
		</tr>
		<%--
      <tr>
        <td align=left id="tdCompany" width="50%" style="font-family:宋体; font-size:10pt;">
          填报单位（签章）：
        </td>
        <td align=right id="tdClaimNo" width="50%" style="font-family:宋体; font-size:10pt;">
          立案编号：
        </td>
      </tr>
      --%>
	</table>
	<table width="92%" align="center" cellspacing="0" cellpadding="0" border="0">
		<tr>
			<td colspan=2 align="left" height="28" width="10%">
				<s:text name="print.respect" />:
				<%-- 致尊敬的 --%>
				<input type=text name="prplInsuredName" readonly="true" style="width: 60px" value="<%=StringConvert.encode(prpLclaimDto.getInsuredName())%>">
			</td>
			<%-- <td height="28" width="35%" id="tdInsuredName">&nbsp;</td> --%>
		</tr>
		<br> &nbsp;&nbsp;
		<tr>
			<td colspan=2 align="left" height="28" width="10%">
				<s:text name="print.youAt" />
				<%--您在 --%>
				<input type=text name="prplInsuredAddress" style="width: 100px" value="">
				<s:text name="print.insurMotorVehicle" />
				<%--投保的机动车辆 --%>
				<input type=text name="prplInsuredAddress" style="width: 100px" value="">
				<s:text name="print.yu" />
				<%-- 於 --%>
				<input type=text name="prplAccidentDate" style="width: 60px" value="">
				<s:text name="print.at" />
				<%-- 在 --%>
				<input type=text name="prplAccidentAddress" style="width: 100px" value="">
				<s:text name="print.insAccidentReport" />
				<%-- 发生的保险事故，我保险公司接到您报案後，委托 --%>
				<input type=text name="prplCommission" style="width: 100px" value="">
				<s:text name="print.dangerAccidentReport" />
				就您的出险事故进行处理，为充分保障您的权益，请您认真审阅此报告。
			</td>
		</tr>
	</table>
	<!-- 主体部分 -->
	<table border=1 width="92%" align="center" cellspacing="0" cellpadding="2" style="border-collapse: collapse" bordercolor="#111111" style="font-family:宋体; font-size:10pt;">
		<tr>
			<td colspan=5 align="center" height="28" width="100%">
				<B> <s:text name="prompt.print.firstPart" /></B>
				<%-- 第一部分：保单承保情况及出险查勘摘要 --%>
			</td>
		<tr>
		<tr>
			<td colspan=2 align="center" height="28" width="10%">
				<s:text name="db.prpLregist.insuredName" />
			</td>
			<%-- 被保险人 --%>
			<td height="28" width="35%" id="tdInsuredName">&nbsp;</td>
			<td align="center" height="28" width="10%">
				<s:text name="print.securityNo" />
			</td>
			<%-- 保险号码 --%>
			<td height="28" width="45%" id="tdPolicyNo">&nbsp;</td>
		</tr>
		<tr>
			<td colspan=2 align="center" height="25">
				<s:text name="db.prpLinvestigate.licenseNo" />
			</td>
			<%--牌照号码 --%>
			<td height="28" id="tdLicenseNo">&nbsp;</td>
			<td align="center" height="28">
				<s:text name="regist.prpLregist.brandName" />
			</td>
			<%--厂牌车型 --%>
			<td height="28" id="tdBrandName">&nbsp;</td>
		</tr>
		<tr>
			<td colspan=2 align="center" height="25">
				<s:text name="print.frameNum" />
			</td>
			<%--车架号码 --%>
			<td height="28" id="tdLicenseNo">&nbsp;</td>
			<td align="center" height="28">
				<s:text name="print.useNature" />
			</td>
			<%--使用性质 --%>
			<td height="28" id="tdBrandName">&nbsp;</td>
		</tr>
		<tr>
			<td colspan=2 align="center" height="25">
				<s:text name="print.bearRisk" />
			</td>
			<%--承担险种 --%>
			<td colspan=3 align="left" height="28">
				<input type="checkbox" name="thirdperson">
				<s:text name="check.thirdPartyIns" />
				&nbsp;&nbsp;&nbsp;&nbsp;
				<%--第三者责任险 --%>
				<input type="checkbox" name="others">
				<s:text name="print.aloneDissilDanger" />
				<%--前後挡风玻璃单独爆裂险 --%>
			</td>
		</tr>
		<tr>
			<td colspan=2 align="center" height="28">
				<s:text name="regist.prpLregist.insuranceTime" />
			</td>
			<%--保险期间 --%>
			<td colspan=3 height="28" id="tdInsuredDate">&nbsp;</td>
		</tr>
		<tr>
			<td colspan=2 align="center" height="28">
				<s:text name="certainLoss.prpLcheck.prpLcheckDamageStartDate" />
			</td>
			<%-- 出险时间--%>
			<td height="28" id="tdDamageStartDate">&nbsp;</td>
			<td align="center" height="28">
				<s:text name="certainLoss.prpLcheck.prpLcheckDamageAddress" />
			</td>
			<%--出险地点 --%>
			<td height="28" id="tdDamageAddress">&nbsp;</td>
		</tr>
		<tr>
			<td colspan=2 align="center" height="28">
				<s:text name="print.insLiability" />
			</td>
			<%--保险责任 --%>
			<td colspan=3 align="left" height="28">
				<input type="checkbox" name="belongto">
				<s:text name="print.belong" />
				&nbsp;&nbsp;&nbsp;&nbsp;
				<%--属於 --%>
				<input type="checkbox" name="notbelongto">
				<s:text name="print.noBelong" />
				<%--不属於 --%>
			</td>
		</tr>
	</table>
	<table width="92%" align="center" cellspacing="0" cellpadding="0" border="0">
		<tr>
			<td colspan=5 align="center" height="28" width="100%">
				<B><s:text name="prompt.print.secondPart" /></B>
				<%-->第二部分：到目前为止我们已做的工作 --%>
			</td>
		</tr>
		<br>
		<tr>
			<td colspan=5 align="left" height="28">
				<p>
					<input type="checkbox" name="secondpart1">
					<s:text name="prompt.print.infoMassage1" />
					<br>
					<%--我保险公司已接受了您的报案，並已将相关报案信息记录在案。 --%>
					<br>
					<input type="checkbox" name="secondpart2">
					<s:text name="prompt.print.infoMassage2" />
					<br>
					<%--我们已经对出险现场进行了查勘，並已向您出具了“现场查勘报告”。 --%>
					<br>
					<input type="checkbox" name="secondpart3">
					<s:text name="prompt.print.infoMassage3" />
					<%--我们已经到事故承修厂（ --%>
					<input type=text name="prplCarryFactory" style="width: 100px" value="">
					<s:text name="prompt.print.right" />
					<%-- ）对（--%>
					<input type="checkbox" name="targetcar">
					<s:text name="print.markCar" />
					<%--标的车 --%>
					<input type="checkbox" name="thirdcar">
					<s:text name="prompt.print.thirdCar" />
					<br>
					<%--三者车）进行了查勘，並对损失情况进行了核实。--%>
					<br> &nbsp;&nbsp;
					<s:text name="prompt.print.targetVehicle" />
					<%--标的车损失情况：材料费 --%>
					<input type=text name="prplMaterialCost" style="width: 60px" value="">
					<s:text name="prompt.print.yuanPartners" />
					<%--元；工时费 --%>
					<input type=text name="prplWorkCost" style="width: 60px" value="">
					<s:text name="prompt.print.totalAmount" />
					<%--元 合计金额 --%>
					<input type=text name="prplToltalCost" style="width: 60px" value="">
					<s:text name="print.yuan" />
					。
					<%-- 元 --%>
				<p>
					<s:text name="prompt.print.threeDamage" />
					<%--三者车损失情况：材料费--%>
					<input type=text name="prplMaterialCost2" style="width: 60px" value="">
					<s:text name="prompt.print.yuanPartners" />
					<%--元；工时费 --%>
					<input type=text name="prplWorkCost2" style="width: 60px" value="">
					<s:text name="prompt.print.yuanTotalAmount" />
					<%--元 合计金额--%>
					<input type=text name="prplToltalCost2" style="width: 60px" value="">
					<s:text name="print.yuan" />
					。
					<%-- 元 --%>
					<br> <br>
					<input type="checkbox" name="secondpart3">
					<s:text name="prompt.print.sceneAccident" />
					<%--我们已经到事故现场（--%>
					<input type=text name="prplAccidentLocal" style="width: 100px" value="">
					<s:text name="prompt.print.tatolAccident" />
					<%--）对本次事故造成的第三者财物损失进行了查勘，並对损失情况进行了核实。核定第三者财物损失共计--%>
					<input type=text name="prplTotalCost3" style="width: 60px" value="">
					<s:text name="print.yuan" />
					。
					<%-- 元 --%>
					<br> <br>
					<input type="checkbox" name="secondpart4">
					<s:text name="prompt.print.save" />
					<%--我们已经到医院（ --%>
					<input type=text name="prplHospital" style="width: 100px" value="">
					<s:text name="prompt.print.injuredAccident" />
					<%--）对本次事故的伤者（--%>
					<input type=text name="prplInjuredPerson" style="width: 60px" value="">
					<s:text name="prompt.print.medicalSituate" />
					<%--）的医疗情况进行了查勘，並初步估计伤者医疗费用总计 --%>
					<input type=text name="prplTotalCost4" style="width: 60px" value="">
					<s:text name="print.yuan" />
					。
					<%-- 元 --%>
					<br> <br>
				<p>
					<s:text name="prompt.print.infoMassage4" />
				</p>
				<%--为了使您清楚了解本次事故的损失情况，便於您顺利地向我保险公司提出索赔， 请您阅读查勘定损报告正文及相关附件，做好相关部门的结案手续， --%>
				<p>
					<s:text name="prompt.print.infoMassage5" />
					<%--並按索赔须知的指引，收集整理所需的材料，递交保险公司，以获得合理赔偿。 --%>
				</p>
				<p>
					<br>
					<s:text name="prompt.print.infoMassage6" />
				</p>
				<%--如果您在处理本次事故的过程中有任何疑问，请您随时致电您的客户经理或定损人，与之沟通。%>
      </td>         
      </tr>
     </table>
      <br> <br><p>
   <table width="92%">
   <tr> 
    <td width="92%" colspan=5 align="center"> 
      <p> &nbsp;&nbsp; &nbsp;  <s:text name="db.prpLregist.linkerName" /><%--联系人 --%>
				<input type=text name="prplLinkerName" style="width: 60px" value="">
				&nbsp; &nbsp;
				<s:text name="certainLoss.prpLscheduleMainWF.prpLscheduleMainWFPhoneNumber" />
				<input type=text name="prplLinkerPhone" style="width: 60px" value="">
				<%--联系电话 --%>
				&nbsp; &nbsp;
				<s:text name="db.prpDcustomer_Idv.faxNumber" />
				<%--传真--%>
				<input type=text name="prplLinkerFax" style="width: 60px" value="">
				</p>
			</td>
		</tr>
	</table>
	<table width="92%" align="center" cellspacing="0" cellpadding="0" border="0">
		<tr>
			<td colspan=5 align="center" width="100%">
				<B><s:text name="print.thirdReportText" /></B>
				<%--第三部分：查勘定损报告正文 --%>
			</td>
		</tr>
		<br>
		<tr>
			<td align="left">
				<B> <s:text name="print.accidentAfter" /></B>
				<%--事故经过 --%>
			<td>
		</tr>
		<br>
		<br>
		<tr>
			<td colspan="4" align="center">
				<textarea name="accidentcourse" cols="70" rows="5"></textarea>
			</td>
		</tr>
		<tr>
			<td align="left" height="28">
				<B><s:text name="print.surveySituate" /></B>
				<%--查勘情况 --%>
			<td>
		</tr>
		<br>
		<br>
		<tr>
			<td colspan="4" align="center">
				<textarea name="accidentcourse" cols="70" rows="5"></textarea>
			</td>
		</tr>
		<tr>
			<td align="left" height="28">
				<B> <s:text name="print.damage" />
				</B>
				<%--损失情况 --%>
			</td>
		</tr>
		<br>
		<br>
		<tr>
			<td align="left" width="10%">
				<s:text name="print.markDamage" />:
			</td>
			<%--标的车损失情况 --%>
			<td colspan="3" align="left" width="90%">
				<textarea name="targetcarContext" cols="60" rows="5"></textarea>
			</td>
		</tr>
		<br>
		<br>
		<tr>
			<td align="left" width="10%">
				<s:text name="print.threeCarDamage" />:
			</td>
			<%--三者车损失情况 --%>
			<td colspan="3" align="left" width="90%">
				<textarea name="thirdcarContext" cols="60" rows="5"></textarea>
			</td>
		</tr>
		<br>
		<br>
		<tr>
			<td align="left" width="10%">
				<s:text name="print.thirdPartySituation" />:
			</td>
			<%--第三者物损情况 --%>
			<td colspan="3" align="left" width="90%">
				<textarea name="thirdlosscarContext" cols="60" rows="5"></textarea>
			</td>
		</tr>
		<br>
		<br>
		<tr>
			<td align="left" width="10%">
				<s:text name="print.casualty" />:
			</td>
			<%-- 人员伤亡情况 --%>
			<td colspan="3" align="left" width="90%">
				<textarea name="personlosscarContext" cols="60" rows="5"></textarea>
			</td>
		</tr>
		<br>
		<tr>
			<td align="left" height="28">
				<B> <s:text name="print.responsiIdentify" /></B>
				<%--责任认定 --%>
			<td>
		</tr>
		<br>
		<br>&nbsp;&nbsp;
		<tr>
			<td colspan="4" align="left" height="28">
				<s:text name="print.accordInsuCompany" />
				<%--我保险公司根据（ --%>
				<input type="checkbox" name="check">
				<s:text name="print.siteSurvey" />
				<%--现场查勘 --%>
				<input type="checkbox" name="describe">
				<s:text name="print.negativeMark" />
				<%--司机叙述 ）情况，认定标的车负（ --%>
				<input type="radio" name="full">
				<s:text name="print.allResponsib" />
				<%--全部责任 --%>
				<input type="radio" name="subordination">
				<s:text name="print.secondaryResponsib" />
				<%--次要责任 --%>
				<input type="radio" name="noresponsiblity">
				<s:text name="print.noResponsib" />
				<%--无责任 --%>
				<input type="radio" name="nodefinitude">
				<s:text name="print.responsibNotClear" />
				<%--不明确责任 ）。（ --%>
				<input type="radio" name="yes">
				<s:text name="print.submitted" />
				<%--已报 --%>
				<input type="radio" name="no">
				<s:text name="print.rafficPolice" />
				<%--未报 ）交警处理。 --%>
			</td>
		</tr>
		<tr>
			<td align="left" height="28">
				<B> <s:text name="db.prpLcomponent.remark" /></B>
				<%--备注 --%>
			<td>
		</tr>
		<tr>
			<td colspan="4" align="center">
				<textarea name="remark" cols="70" rows="5"></textarea>
			</td>
		</tr>
		<tr>
			<td align="left" height="28">
				&nbsp;&nbsp;
				<s:text name="print.bestWishe" />:
			<td>
				<%--顺祝 --%>
		</tr>
		<br>
		<tr>
			<td align="left" height="28">
				<s:text name="print.shangQi" />
			<td>
				<%--商祺！ --%>
		</tr>
		<br>
		<br>
		<p>
		<tr>
			<td align="left" height="28">
				<s:text name="print.insurAssessDiv" />:
				<%--保险公估师 --%>
			</td>
			<td height="28">
				<input type=text name="prplInsuranceEngineer" style="width: 60px" value="">
			</td>
			<td align="center" height="28">
				<s:text name="print.reviewer" />：
				<%--审核人 --%>
			</td>
		</tr>
		<br>
		<br>
		<tr>
			<td colspan="4" align="right">
				<input type=text name="prplCompanyName" style="width: 100px" value="">
				<s:text name="print.insuranceAssess" />
				<%--保险公估有限公司 --%>
			</td>
		</tr>
		<br>
		<br>
		<br>
		<tr>
			<td colspan="4" align="right">
				<s:text name="print.year" />
				<%-- 年 --%>
				&nbsp;&nbsp;
				<s:text name="print.month" />
				<%-- 月 --%>
				&nbsp;&nbsp;
				<s:text name="regist.prpLregist.date" />
				<%-- 日 --%>
			</td>
		</tr>
	</table>
	<%-- include打印按钮 --%>
	<jsp:include page="/common/print/PrintButton.jsp" />
</body>
</html>
