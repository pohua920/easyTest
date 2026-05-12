<%--
****************************************************************************
* DESC       ：机动车辆垫付赔款收据
* AUTHOR     ：liqiang
* CREATEDATE ：2006-11-13
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<META http-equiv=Content-Type content="text/html; charset=gb2312">
<META content="MSHTML 6.00.2800.1106" name=GENERATOR>
<OBJECT ID="DS_Printer" border=0 CLASSID="CLSID:24DDA709-7162-4CAD-8575-5DB572479D32"> </object>
<%@page import="java.util.*"%>
<%@page import="com.sinosoft.claim.dto.domain.PrpLagentDto"%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app"%>
<script language="javascript">
	function jsPrintPage() {
		//printPage()
		divButton.style.display = "none"
		//纸张大小
		DS_Printer.SetPaperSize(40000, 60000);

		//上边距
		DS_Printer.SetTopMargin(2000);//1/100 mm

		//下边距
		DS_Printer.SetBottomMargin(2000);

		//左边距
		DS_Printer.SetLeftMargin(2000);

		//右边距
		DS_Printer.SetRightMargin(1900);

		window.print();
	}
</script>
<%-- 初始化 --%>
<%
	PrpLagentDto prpLagentDto = (PrpLagentDto) request.getAttribute("prpLagentDto");
	if (prpLagentDto == null)
		prpLagentDto = new PrpLagentDto();
	String NullComName = "";
	if (prpLagentDto.getNullComName().length() > 10) {
		NullComName = prpLagentDto.getNullComName().substring(0, 9) + "<br>" + prpLagentDto.getNullComName().substring(9, prpLagentDto.getNullComName().length());
	} else {
		NullComName = prpLagentDto.getNullComName();
	}
%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<span id="PolicyNo" style="LEFT: 60px; POSITION: absolute; TOP: 35px"><font size=1><%=prpLagentDto.getNullReportNo()%></font></span>
<span id="LicenseNo" style="LEFT: 330px; POSITION: absolute; TOP: 12px"><font size=1><%=prpLagentDto.getNullCarMark()%></font></span>
<span id="InsuredName" style="LEFT: 510px; POSITION: absolute; TOP: 12px"><font size=1><%=prpLagentDto.getNullInsured()%></font></span>
<span id="ClaimNo" style="LEFT: 400px; POSITION: absolute; TOP: 55px"><font size=1><%=prpLagentDto.getAdvanceNo()%></font></span>
<span id="CompensateNo" style="LEFT: 490px; POSITION: absolute; TOP: 85px"></span>
<span id="CompensateNo" style="LEFT: 160px; POSITION: absolute; TOP: 225px"><font size=1>财险</font></span>
<span id="ComName" style="LEFT: 340px; POSITION: absolute; TOP: 215px"><font size=1> <%=NullComName%></font></span>
<span id="AgentPay" style="LEFT: 140px; POSITION: absolute; TOP: 260px"><font size=1><%=prpLagentDto.getSettleMentAmount()%></font></span>
<span id="InsuredName" style="LEFT: 190px; POSITION: absolute; TOP: 330px"><font size=1><%=prpLagentDto.getFullInsured()%></font></span>
<span id="PolicyNo" style="LEFT: 370px; POSITION: absolute; TOP: 320px"><font size=1><%=prpLagentDto.getFullPolicyCode()%></font></span>
<span id="spbutton" style="WIDTH: 700px; LEFT: 40px; POSITION: absolute; TOP: 500px; font-family: 宋体; font-size: 11pt;">
	<table id='divButton' cellpadding="0" cellspacing="0" width="80%" style="display:">
		<tr>
			<td class=button align="center"><input class="button" type="button" name="buttonPrint" value=" 列 印 " onclick="jsPrintPage();"></td>
			<td class=button align="center"><input class="button" type="button" name="buttonClose" value=" 关 闭 " onclick="javascript:window.close();"></td>
		</tr>
	</table>
</span>