<%--
****************************************************************************
* DESC       ：机动车辆回勘记录打印打印页面
* AUTHOR     ：zhaozhuo
* CREATEDATE ：2005-04-13
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app"%>
<html xmlns:v="urn:schemas-microsoft-com:vml" xmlns:o="urn:schemas-microsoft-com:office:office" xmlns="http://www.w3.org/TR/REC-html40">
<head>
<title><s:text name="title.printBeforeEdit.motorVehicleRecord" /></title>
<%-- 机动车辆回勘记录打印 --%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<script LANGUAGE="JavaScript">
<!--
	/**
	 *@description 设值页面的一些初始化信息
	 *@param       无
	 *@return      通过返回true,否则返回false
	 */
	function initSet() {
		return true;
	}
//-->
</script>
</head>
<body bgcolor="#FFFFFF" onload="initSet();">
	<form name="fm"></form>
	<p height="40" align=top align=center style="text-align: center; font-family: 宋体; font-size: 14pt;">
		<B> <s:text name="print.motorVehicle" />
		</b>
	</p>
	<%-- 机动车辆回勘记录 --%>
	<table border="1" cellspacing="0" style="border-collapse: collapse" bordercolor="#111111" width="100%" height="660">
		<tr>
			<td width="50%" colspan="2" height="50"><s:text name="db.prpLregist.insuredName" /> <%-- 被保险人 --%>： <font width="50%" colspan="2" height="50" id="tdInsuredName">&nbsp;</font></td>
			<td width="50%" height="50"><s:text name="print.canBackDate" /> <%-- 回勘时间 --%>：&nbsp;&nbsp;&nbsp; <s:text name="print.year" /> <%-- 年 --%>&nbsp;&nbsp;&nbsp; <s:text name="print.month" /> <%-- 月 --%>&nbsp;&nbsp;&nbsp;
				<s:text name="regist.prpLregist.date" /> <%-- 日 --%></td>
		</tr>
		<tr>
			<td width="25%" height="50"><s:text name="certainLoss.thirdCarLoss.prpLthirdCarLicenseNo" />：<%-- 车牌号 --%> <font width="25%" height="50" id="tdLicenseNo">&nbsp;</td>
			<td width="25%" height="50"><s:text name="print.models" /> <%-- 车型 --%> <font width="25%" height="50" id="tdBrandName">&nbsp;</td>
			<td width="50%" height="50"><s:text name="print.markCar" />&nbsp;&nbsp; <%-- 标的车 --%> <input type="checkbox" name="C1" value="ON">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<s:text name="print.thirdParty" />&nbsp;&nbsp; <input type="checkbox" name="C1" value="ON"></td>
			<%-- 第三者车 --%>
		</tr>
		<tr>
			<td width="100%" colspan="3" height="261">
				<p>
					<s:text name="print.backCaseShall" />：
				</p> <%-- 回勘情况 --%>
				<p>
					<s:text name="print.replaceProject" />
					<%-- A：有无按合同约定配件更换项目更换 --%>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<s:text name="print.all" />
					<%-- 全部 --%>
					&nbsp;&nbsp; <input type="checkbox" name="C1" value="ON">&nbsp;&nbsp;
					<s:text name="print.part" />
					<%-- 部分 --%>
					&nbsp;&nbsp; <input type="checkbox" name="C1" value="ON">
				</p>
				<p>
					<s:text name="print.replaceDamagedPart" />
					<%-- B: 有无按合同约定更换损坏配件 --%>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<s:text name="print.factory" />
					<%-- 正厂 --%>
					&nbsp;&nbsp; <input type="checkbox" name="C1" value="ON">&nbsp;&nbsp;
					<s:text name="print.provisContract" />
					<%-- 合同规定 --%>
					&nbsp;&nbsp; <input type="checkbox" name="C1" value="ON">&nbsp;&nbsp;
					<s:text name="certainLoss.thirdCarLoss.dutyOther" />
					<%-- 其他 --%>
					&nbsp;&nbsp; <input type="checkbox" name="C1" value="ON">
				</p>
				<p>
					<s:text name="print.maintProjectMainten" />
					<%-- C:&nbsp; 有无按合同约定维修项目维修 --%>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<s:text name="print.all" />
					<%-- 全部 --%>
					&nbsp;&nbsp; <input type="checkbox" name="C1" value="ON">&nbsp;&nbsp;
					<s:text name="print.part" />
					<%-- 部分 --%>
					&nbsp;&nbsp; <input type="checkbox" name="C1" value="ON">
				</p>
				<p>
					<s:text name="print.accessAmount" />
				</p> <%-- D:&nbsp; 未按合同约定更换的配件名称及金额 --%>
				<p>
					<s:text name="print.accessAmountProject" />
				</p> <%-- E:&nbsp; 未按合同约定维修的项目名称及金额 --%>
				<p>
			</td>
		</tr>
		<tr>
			<td width="100%" colspan="3" height="156">
				<p>
					<s:text name="print.nucleaProcessOpinion" />：
				</p> <%-- 核损人员处理意见 --%>
				<p></p>
				<p>
					<s:text name="print.personnShall" />：
				</p> <%-- 回勘人员签字 --%>
				<p style="text-align: right">
					<s:text name="print.year" />
					<%-- 年 --%>
					&nbsp;&nbsp;&nbsp;
					<s:text name="print.month" />
					<%-- 月 --%>
					&nbsp;&nbsp;&nbsp;
					<s:text name="regist.prpLregist.date" />
					<%-- 日 --%>
			</td>
		</tr>
		<tr>
			<td width="100%" colspan="3" height="156">
				<p>
					<s:text name="print.factoryConfirm" />：
				</p> <%-- 承修厂确认意见 --%>
				<p></p>
				<p>
					<s:text name="print.signature" />：
				</p> <%-- 签章 --%>
				<p style="text-align: right">
					<s:text name="print.year" />
					<%-- 年 --%>
					&nbsp;&nbsp;&nbsp;
					<s:text name="print.month" />
					<%-- 月 --%>
					&nbsp;&nbsp;&nbsp;
					<s:text name="regist.prpLregist.date" />
					<%-- 日 --%>
			</td>
		</tr>
	</table>
	<p>
		<s:text name="prompt.print.accordArchive" />
	</p>
	<%-- 本记录表内部流转，作为理算依据归档。 --%>
	<%-- include打印按钮 --%>
	<jsp:include page="/common/print/PrintButton.jsp" />
	</form>
</body>
</b>
</html>