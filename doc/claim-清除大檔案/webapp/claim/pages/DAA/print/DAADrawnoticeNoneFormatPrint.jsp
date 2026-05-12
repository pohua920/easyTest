<%--
****************************************************************************
* DESC       ：机动车辆保险领取赔款通知书列印页面
* AUTHOR     ：理赔组
* CREATEDATE ：2004-11-16
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK" %>

<%-- 引入bean类部分 --%>
<%@page import="java.text.*"%>
<%@page import="com.sinosoft.claim.ui.control.action.*"%>
<%@page import="com.sinosoft.claim.dto.custom.*"%>
<%@page import="com.sinosoft.claim.dto.domain.*"%> 
<%@page import="com.sinosoft.claim.util.*"%> 
<%@page import="com.sinosoft.sysframework.common.util.*"%> 
<%@page import="com.sinosoft.sysframework.common.datatype.DateTime"%> 
<%@page import="com.sinosoft.sysframework.exceptionlog.*"%>

<%
	//变量声明部分
	//  String strCaseNo         = request.getParameter("CaseNo"); //归档号
	String strCompensateNo = request.getParameter("CompensateNo"); //归档号
	String strCaseNo = ""; //归档号
	String strClaimNo = ""; //赔案号
	String strPolicyNo = ""; //保单号
	String strInsuredName = ""; //被保险人
	String strInsureCarFlag = ""; //是否为本保单车辆 
	String strLicenseNo = ""; //牌照号码
	String strDamageStartDate = ""; //出险时间
	String strDamageAddress = ""; //出险地点
	String strDamageName = ""; //出险原因
	String strInputDate = ""; //日期
	String strPhoneNumber = ""; //联系电话
	String strHandlerName = ""; //联系人
	String strAddressCName = ""; //领款地址
	String strContext = "";
	String strMessage = "";

	//代码翻译变量
	String strCode = "";
	String strName = "";
	boolean isChinese = true; //中文标志

	int intCompensateCount = 0;
	int intThirdPartyCount = 0;
	int index = 0;
	//得到ClaimDto,RegistDto,CertainLossDto,PolicyDto对象
	CompensateDto compensateDto = (CompensateDto) request
			.getAttribute("compensateDto");
	ClaimDto claimDto = (ClaimDto) request.getAttribute("claimDto");
	PolicyDto policyDto = (PolicyDto) request.getAttribute("policyDto");
	PrpLcompensateDto prpLcompensateDto = compensateDto
			.getPrpLcompensateDto();
	strCaseNo = prpLcompensateDto.getCaseNo();
	strClaimNo = prpLcompensateDto.getClaimNo();
	strPolicyNo = prpLcompensateDto.getPolicyNo();

	UICodeAction uiCodeAction = new UICodeAction();
	//得到PrpLclaimDto对象 
	PrpLclaimDto prpLclaimDto = claimDto.getPrpLclaimDto();

	strInsuredName = prpLclaimDto.getInsuredName();
	DateTime damageStartDateTemp = prpLclaimDto.getDamageStartDate();
	strDamageStartDate = damageStartDateTemp.getYear() + " 年 "
			+ damageStartDateTemp.getMonth() + " 月 "
			+ damageStartDateTemp.getDate() + " 日 ";
	strDamageAddress = prpLclaimDto.getDamageAddress();
	strDamageName = prpLclaimDto.getDamageName();

	strCode = "";
	strName = "";
	strCode = prpLclaimDto.getMakeCom();
	//dbPrpDcompany.getInfo(strCode);
	UICompanyAction uiCompanyAction = new UICompanyAction();
	PrpDcompanyDto prpDcompanyDto = uiCompanyAction
			.findByPrimaryKey(strCode);
	strPhoneNumber = prpDcompanyDto.getPhoneNumber();
	strAddressCName = prpDcompanyDto.getAddressCName();

	//得到PrpLthirdParty对象的记录数
	if (claimDto.getPrpLthirdPartyDtoList() != null) {
		intThirdPartyCount = claimDto.getPrpLthirdPartyDtoList().size();
	}
	if (claimDto.getPrpLthirdPartyDtoList() != null) {
		for (index = 0; index < intThirdPartyCount; index++) {
			strInsureCarFlag = ((PrpLthirdPartyDto) claimDto
					.getPrpLthirdPartyDtoList().get(index))
					.getInsureCarFlag();

			if (strInsureCarFlag.equals("1")) {
				strLicenseNo = ((PrpLthirdPartyDto) claimDto
						.getPrpLthirdPartyDtoList().get(index))
						.getLicenseNo();
			}
		}
	}

	strContext = "<br>&nbsp;&nbsp;&nbsp;&nbsp;您投保的车辆（牌照号码："
			+ strLicenseNo + "&nbsp;&nbsp;），於<ins> "
			+ strDamageStartDate + "</ins>在<ins> " + strDamageAddress
			+ "&nbsp;&nbsp;" + "</ins>地段，<br><br>因 <ins>&nbsp;"
			+ strDamageName
			+ "&nbsp;</ins>出险，根据保险条款的有关规定，该起赔案本公司已处理结案，请" + ""
			+ "前来办理领取赔款手续。";

	//*****投保单基本信息表PrpCmain*****
	PrpCmainDto prpCmainDto = policyDto.getPrpCmainDto();

	strCode = "";
	strCode = prpCmainDto.getHandlerCode();
	strHandlerName = uiCodeAction.translateUserCode(strCode, isChinese);

	//日期
	strInputDate = DateTime.current().getYear() + " 年 "
			+ DateTime.current().getMonth() + " 月 "
			+ DateTime.current().getDate() + " 日";
%>
<html>
<link rel="stylesheet" type="text/css" href="/claim/DAA/print/StandardPrint.css">
<body bgcolor="#FFFFFF">
	<!-- 标题部分 -->
	<table width="92%" align="center" cellspacing="0" cellpadding="0" border="0">
		<tr>
			<td colspan="2" height="40" align=top align=center style="font-family: 宋体; font-size: 14pt;">
				<B>机动车辆保险领取赔款通知书<B>
			</td>
		</tr>
		<tr>
			<td align=left width="50%" style="font-family: 宋体; font-size: 10pt;">
				<br>被保险人：<%=strInsuredName%></td>
			<!--        <td align=right width="50%" style="font-family:宋体; font-size:10pt;">
          <br>赔款计算书号：<%=strCompensateNo%>
        </td>        
-->
			<td align=right id="tdCaseNo" width="30%" style="font-family: 宋体; font-size: 10pt;">
				<br>归档号：<%=strCaseNo%></td>
		</tr>
	</table>
	<!-- 主体部分 -->
	<table border="0" width="92%" align="center" cellspacing="0" cellpadding="0" style="border-collapse: collapse" bordercolor="#111111" style="font-family:宋体; font-size:10pt;">
		<tr>
			<td colspan="3" align="center">
				<hr noshade>
			</td>
		</tr>
		<tr>
			<td colspan="3" align="left" valign="top" height="80"><%=strContext%></td>
		</tr>
		<tr>
			<td colspan="3" align="left" height="28">&nbsp;&nbsp;&nbsp;&nbsp;谢谢您的合作，欢迎继续到本公司投保。</td>
		</tr>
		<tr>
			<td colspan="2" align="left" width="50%" height="28">&nbsp;</td>
			<td align="left" width="50%" height="28">保险公司（签章）：</td>
		</tr>
		<tr>
			<td colspan="2" align="left" height="30">&nbsp;</td>
			<td align="left" height="28">
				日期：<%=strInputDate%><br>
			</td>
		</tr>
		<tr>
			<td width="15%" height="28">&nbsp;</td>
			<td align="left" height="28">
				联系电话：<%=strPhoneNumber%></td>
			<td width="50%" height="28">&nbsp;</td>
		</tr>
		<tr>
			<td width="15%" height="28">&nbsp;</td>
			<td align="left" height="28">
				联 系 人：<%=strHandlerName%></td>
			<td width="50%" height="28">&nbsp;</td>
		</tr>
		<tr>
			<td width="15%" height="28">&nbsp;</td>
			<td align="left" height="28">
				领款地址：<%=strAddressCName%></td>
			<td width="50%" height="28">&nbsp;</td>
		</tr>
		<tr>
			<td colspan="3" align="center" height="28">
				<hr noshade>
			</td>
		</tr>
	</table>
	<!-- 结尾部分 -->
	<table border="0" width="92%" align="center" cellspacing="0" cellpadding="0" style="border-collapse: collapse" bordercolor="#111111" style="font-family:宋体; font-size:10pt;">
		<tr>
			<td height="20">
				<br> 注意事项：领取赔款时请您携带一下单证、材料：<br> <br> &nbsp;&nbsp;&nbsp;&nbsp;1、被保险人为法人或其他组织的，应提供单位财务专用章或公章。<br> <br> &nbsp;&nbsp;&nbsp;&nbsp;2、被保险人为自然人的，应提供被保险人身份证明（原件）。<br> <br>
				&nbsp;&nbsp;&nbsp;&nbsp;3、代他人领取赔款的，需提供被保险人签署的《领取赔款授权书》和领取赔款人身份证明（原件）。
			</td>
		</tr>
	</table>
	<br>
	<br>
	<script language='javascript'>
		function printPage() {
			//add print liudaoping 2013-04-15
			//alert("【列印】功能屬於客制化需求，暫未開發，請知悉！");
			return false;
			tbButton.style.display = "none";
			window.print();
		}
	</script>
	<%-- include列印按钮 --%>
	<!--<jsp:include page="/common/print/PrintButton.jsp" />     -->
	<table id="tbButton" cellpadding="0" cellspacing="0" width="80%" style="display:">
		<tr>
			<td class=button style="width: 50%" align="center">
				<input type=button name=buttonPrint value=" 列 印 " class="button" onclick="return printPage()">
			</td>
			<td class=button style="width: 50%" align="center">
				<input type=button name=buttonClose value=" 关 闭 " class="button" onclick="javascript:window.close()">
			</td>
		</tr>
	</table>
</body>
</html>
