<%--
****************************************************************************
* DESC       ：货运险查勘报告清单打印
* AUTHOR     ：wangwei
* CREATEDATE ：2005-5-22
* MODIFYLIST ：   id       Date            Reason/Contents
    建议打印设置：上
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ page import="java.text.*"%>
<%-- 初始化 --%>
<html>
<head>
<title>货运险检验报告清单列印</title>
<link rel="stylesheet" type="text/css" href="Standard.css">
</head>
<body bgcolor="#FFFFFF">
	<!-- 标题部分 -->
	<logic:present name="uiFreightCheckReportPrintDto">
		<table width="100%" align="center" cellspacing="0" cellpadding="0" border="0">
			<tr>
				<td colspan="2" height="40" align="center">
					<Img src="/claim/images/LOGO.jpg" />
				</td>
			</tr>
			<tr>
				<td colspan="2" height="40" align=top align=center style="text-align: center; font-family: 宋体; font-size: 14pt;">
					<B>货物运输保险检验报告<B>
				</td>
			</tr>
			<tr>
				<td colspan="2" height="40" align=top align=center style="text-align: center; font-family: 宋体; font-size: 10pt;">
					<b>CARGO TRANSPORTATION INSURANCE SURVEY REPORT</b>
				</td>
			</tr>
			<tr>
				<td height="15" align=left width="50%">
					<font style="font-family: 宋体; font-size: 10pt;">1、立案号(Claim No.)：<u><bean:write name="uiFreightCheckReportPrintDto" property="claimNo" /></u></font>
				</td>
				<td height="15" align=left width="50%">
					<font style="font-family: 宋体; font-size: 10pt;">2、保单号(Policy No.)：<u><bean:write name="uiFreightCheckReportPrintDto" property="policyNo" /></u></font>
				</td>
			</tr>
			<tr>
				<td height="15" align=left width="50%">
					<font style="font-family: 宋体; font-size: 10pt;">3、保险险别(Coverage)：<u></u></font>
				</td>
				<td height="15" align=left width="50%">
					<font style="font-family: 宋体; font-size: 10pt;">4、保险金额(Sum Insured)：<bean:write name="uiFreightCheckReportPrintDto" format="#000.00" property="currency" /><u><bean:write
								name="uiFreightCheckReportPrintDto" format="#000.00" property="sumAmount" /></u></font>
				</td>
			</tr>
			<tr>
				<td height="15" align=left width="50%">
					<font style="font-family: 宋体; font-size: 10pt;">5、被保险人名称(Name of Insured)：<u><bean:write name="uiFreightCheckReportPrintDto" property="insuredName" /></u></font>
				</td>
			</tr>
			<tr>
				<td height="15" align=left colspan=2>
					<font style="font-family: 宋体; font-size: 10pt;">6、提单/运单/货票号码(B/L No./AWB No.)：<u><bean:write name="uiFreightCheckReportPrintDto" property="ladingNo" /></u></font>
				</td>
			</tr>
			<tr>
				<td height="15" align=left colspan=2>
					<font style="font-family: 宋体; font-size: 10pt;">7、装载运输工具(Per Conveyance)：<u><bean:write name="uiFreightCheckReportPrintDto" property="blName" /></u></font>
				</td>
			</tr>
			<tr>
				<td height="15" align=left colspan=2>
					<font style="font-family: 宋体; font-size: 10pt;">8、保险货物项目(Description of Goods)：<u><bean:write name="uiFreightCheckReportPrintDto" property="displayName" /></u></font>
				</td>
			</tr>
			<tr>
				<td height="15" align=left colspan=2>
					<font style="font-family: 宋体; font-size: 10pt;">9、包装及数量(Packing & Quantity)：<u></u></font>
				</td>
			</tr>
			<tr>
				<td height="15" align=left colspan=4>
					<font style="font-family: 宋体; font-size: 10pt;">10、起运日期(Date of Commencement)：<u><bean:write name="uiFreightCheckReportPrintDto" property="sailStartDate" /></u></font>
				</td>
			</tr>
			<tr>
				<td height="15" align=left colspan=2>
					<font style="font-family: 宋体; font-size: 10pt;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;自(From)&nbsp;<u><bean:write name="uiFreightCheckReportPrintDto"
								property="startSiteName" /></u>&nbsp;经(Via)&nbsp;<u><bean:write name="uiFreightCheckReportPrintDto" property="viaSiteName" /></u>&nbsp;至(To)&nbsp;<u><bean:write
								name="uiFreightCheckReportPrintDto" property="endSiteName" /></u></font>
				</td>
			</tr>
			<tr>
				<td height="15" align=left colspan="4">
					<font style="font-family: 宋体; font-size: 10pt;">11、出险日期及时间(Time & Date of Loss)：<u><bean:write name="uiFreightCheckReportPrintDto" property="damageStartDate" /></u></font>
				</td>
			</tr>
			<tr>
				<td height="15" align=left colspan="2">
					<font style="font-family: 宋体; font-size: 10pt;">12、出险地点(Location of Damage)：<u><bean:write name="uiFreightCheckReportPrintDto" property="damageSite" /></u></font>
				</td>
			</tr>
			<tr>
				<td height="15" align=left colspan="2">
					<font style="font-family: 宋体; font-size: 10pt;">13、出险原因(Reason of Damage)：<u><bean:write name="uiFreightCheckReportPrintDto" property="damageName" /></u></font>
				</td>
			</tr>
			<tr>
				<td height="15" align=left colspan="2">
					<font style="font-family: 宋体; font-size: 10pt;">14、申请检验日期(Date of Application for Survey)：<u><bean:write name="uiFreightCheckReportPrintDto" property="appliCheckDate" /></u></font>
				</td>
			</tr>
			<tr>
				<td height="15" align=left colspan=2>
					<font style="font-family: 宋体; font-size: 10pt;">&nbsp;&nbsp;&nbsp;&nbsp;延误原因，如有延误(Reason of Delay If Delayed)<u></u></font>
				</td>
			</tr>
			<tr>
				<td height="15" align=left width="50%">
					<font style="font-family: 宋体; font-size: 10pt;">15、申请检验人(Applicant for Survey)：<u></u></font>
				</td>
				<td height="15" align=left width="50%">
					<font style="font-family: 宋体; font-size: 10pt;">16、检验日期(Date of Survey)：<u><bean:write name="uiFreightCheckReportPrintDto" property="checkDate" /></u></font>
				</td>
			</tr>
			<tr>
				<td height="100%" align=left colspan="2">
					<font style="font-family: 宋体; font-size: 10pt;">17、检验地点及相关人员(Place of Survey & Related Parties)：<u><bean:write name="uiFreightCheckReportPrintDto" property="checkSite" /></u>&nbsp;&nbsp;<u><bean:write
								name="uiFreightCheckReportPrintDto" property="checker1" /></u>&nbsp;&nbsp;<u><bean:write name="uiFreightCheckReportPrintDto" property="checker1" /></u></font>
				</td>
			</tr>
			<tr>
				<td height="15" align=left width="50%" colspan=2>
					<font style="font-family: 宋体; font-size: 10pt;">18、出险情况描述(Situation of Loss)：</font>
				</td>
			</tr>
			<tr>
				<td height="15" align=left width="50%" colspan=2>
					<font style="font-family: 宋体; font-size: 10pt;">&nbsp;</font>
				</td>
			</tr>
			<tr>
				<td height="15" align=left width="50%" colspan=2>
					<font style="font-family: 宋体; font-size: 10pt;">&nbsp;</font>
				</td>
			</tr>
			<tr>
				<td height="15" align=left width="50%">
					<font style="font-family: 宋体; font-size: 10pt;">19、现场查勘情况、责任分析及处理意见：</font>
				</td>
			</tr>
			<tr>
				<td height="15" align=left colspan="2">
					<font style="font-family: 宋体; font-size: 10pt;">&nbsp;(Survey of Claim & Liability Analysis & Suggestion to the Settlement)</font>
				</td>
			</tr>
			<tr>
				<td height="15" align=left width="50%" colspan=2>
					<font style="font-family: 宋体; font-size: 10pt;">&nbsp;&nbsp;&nbsp;&nbsp;<bean:write name="uiFreightCheckReportPrintDto" property="context" /></font>
				</td>
			</tr>
			<tr>
				<td height="15" align=left width="50%" colspan=2>
					<font style="font-family: 宋体; font-size: 10pt;">&nbsp;&nbsp;</font>
				</td>
			</tr>
			<tr>
				<td height="15" align=left width="50%" colspan=2>
					<font style="font-family: 宋体; font-size: 10pt;">&nbsp;&nbsp;</font>
				</td>
			</tr>
			<tr>
				<td height="15" align=left width="50%">
					<font style="font-family: 宋体; font-size: 10pt;">20、估计损失金额(Estimated Loss)：</font>
				</td>
			</tr>
			<tr>
				<td height="15" align=left width="50%" colspan=2>
					<font style="font-family: 宋体; font-size: 10pt;">&nbsp;&nbsp;&nbsp;&nbsp;<bean:write name="uiFreightCheckReportPrintDto" property="estimateLoss" /></font>
				</td>
			</tr>
			<tr>
				<td height="15" align=left width="50%">
					<font style="font-family: 宋体; font-size: 10pt;">21、备注(Remarks)：</font>
				</td>
			</tr>
			<tr>
				<td height="15" align=left width="50%" colspan=2>
					<font style="font-family: 宋体; font-size: 10pt;">&nbsp;&nbsp;&nbsp;&nbsp;<bean:write name="uiFreightCheckReportPrintDto" property="remark" /></font>
				</td>
			</tr>
			<tr>
				<td height="15" align=left width="50%" colspan=2>
					<font style="font-family: 宋体; font-size: 10pt;">22、检验报告日期(Date of Survey Report)：<u>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</u></font>&nbsp;
				</td>
			</tr>
			<tr>
				<td height="15" align=left width="50%">
					<font style="font-family: 宋体; font-size: 10pt;">23、检验费(Survey Fee)：<u>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </u></font>
				</td>
				<td width="50%">
					<font style="font-family: 宋体; font-size: 10pt;">24、检验人(Surveyor)： <u><bean:write name="uiFreightCheckReportPrintDto" property="checker1" /></u><u><bean:write
								name="uiFreightCheckReportPrintDto" property="checker2" /></u></font>
				</td>
			</tr>
			<tr>
				<td height="15" align=left width="50%" colspan=2>
					<font style="font-family: 宋体; font-size: 10pt;">25、检验人签名(Sign of Surveyor)：<u>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</u></font> &nbsp;
				</td>
			</tr>
		</table>
		<jsp:include page="/common/print/PrintButton.jsp" />
	</logic:present>
</body>
</html>