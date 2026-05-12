<%--
****************************************************************************
* DESC       ：理赔审核书打印
* AUTHOR     ：caopeng
* CREATEDATE ：2005-12-14
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=gb2312"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%-- 初始化 --%>
<%@include file="AcciCompensateAuditBookNoneFormatPrintIni.jsp"%>
<html>
<head>
<title>理赔计算书列印</title>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<meta http-equiv="pragma" CONTENT="no-cache">
<meta http-equiv="Cache-Control" CONTENT="no-cache,   must-revalidate">
</head>
<body onLoad="loadForm();">
	<table width="92%" align="center" cellspacing="0" cellpadding="0" border="0" style="font-family: 宋体; font-size: 10pt;">
		<tr>
			<td width="100%" height="40" align=center style="font-family: 宋体; font-size: 14pt;">
				<%
					//<img src="/claim/images/LOGO.jpg"/>
				%>
			</td>
		</tr>
		<tr>
			<td height="20" align=center style="font-family: 宋体; font-size: 14pt;">
				<B>意健险理赔赔款计算书<B>
			</td>
		</tr>
		<!--delete by zhangyingrui start at 20060831-->
		<!--<tr>
          <td height="20" align=center style="font-family:宋体; font-size:10pt;"><div align="left"> 赔案号 : <span id="spClaimNo"></span></div></td>
        </tr>-->
		<!--delete by zhangyingrui start at 20060831-->
		<tr>
			<td height="15" align=center style="font-family: 宋体; font-size: 10pt;">
				<div align="left">
					计算书号 :
					<%=strCompensateNo%></span>
				</div>
			</td>
		</tr>
	</table>
	<table width="92%" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#111111" style="border-collapse: collapse; font-family: 宋体 font-size : 10pt;">
		<tr>
			<td colspan="9" height="20">
				赔案号 : <span id="spClaimNo"><%=strClaimNo%></span>
			</td>
		</tr>
		<tr style="font-family: '宋体'; font-size: 10pt">
			<td width="2%" height="20" rowspan="4" align="center" valign="middle">事故情况</td>
			<!--<td height="25" colspan="8">          
    	<table width="100%"  border="0" cellspacing="0" cellpadding="0" style=" font-family:'宋体';font-size:10pt;">
        <tr>
          <td height="20">事故类型： </td>
        </tr>
        <tr>
          <td height="20"><span id="spDamageTypeName">□意外身故 □意外残疾 □重疾 □意外医疗 □疾病医疗 </span></td>
        </tr>
      </table>
      
    </td>-->
			<td width="17%" height="20" align="center" valign="middle">事故类型</td>
			<td height="20" colspan="3" align="center" valign="middle">
				<span id="spDamageTypeName"><%=strDamageTypeName%></span>&nbsp;
			</td>
			<td height="20" colspan="2" align="center" valign="middle">案件类型</td>
			<td height="20" colspan="3" align="center" valign="middle">
				<span id="strClaimTypeName"><%=strClaimTypeName%></span>&nbsp;
			</td>
		</tr>
		<tr style="font-family: '宋体'; font-size: 10pt">
			<td width="17%" height="20" align="center" valign="middle">事故者姓名</td>
			<td width="14%" height="20" align="center" valign="middle">
				<span id="spAcciName"><%=strAcciName%>&nbsp;</span>
			</td>
			<td width="7%" height="20" align="center" valign="middle">
				<div align="center">性别</div>
			</td>
			<td width="6%" height="20" align="center" valign="middle">
				<span id="spAcciSex"><%=strAcciSex%></span>
			</td>
			<td width="9%" height="20" align="center" valign="middle">年龄</td>
			<td width="13%" height="20" align="center" valign="middle">
				<span id="spAcciAge"><%=strAcciAge%>&nbsp;</span>
			</td>
			<td width="13%" height="20" align="center" valign="middle">
				<div align="center">身份證字號</div>
			</td>
			<td width="21%" height="20" align="center" valign="middle">
				<span id="spAcciIDCardNo"><%=strAcciIDCardNo%></span>
				<div align="center"></div>
			</td>
		</tr>
		<tr style="font-family: '宋体'; font-size: 10pt">
			<td width="17%" height="20" align="center" valign="middle">事故时间</td>
			<td height="20" colspan="3" align="center" valign="middle">
				<span id="spAcciDate"><%=strAcciDate%></span>&nbsp;
			</td>
			<td height="20" align="center" valign="middle">残疾鉴定时间</td>
			<td height="20" colspan="3" align="center" valign="middle">&nbsp;</td>
		</tr>
		<tr style="font-family: '宋体'; font-size: 10pt">
			<td height="25" colspan="8" align="center" valign="middle">
				<table width="100%" border="0" cellspacing="0" cellpadding="0" style="font-family: '宋体'; font-size: 10pt">
					<tr>
						<td height="20">
							<div align="left">事故原因、经过及事故者现状：</div>
						</td>
					</tr>
					<tr>
						<td height="20">
							<span id="spAcciDamageDesc"><%="&nbsp;&nbsp;事故原因：" + strDamageName + "&nbsp;&nbsp;" + strRegistTextContext%></span>&nbsp;
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr style="font-family: '宋体'; font-size: 10pt">
			<td width="2%" height="20" rowspan="4" align="center" valign="middle">
				<p>保单信息</p>
			</td>
			<td width="17%" height="20" align="center" valign="middle">保单号码</td>
			<td height="20" colspan="3" align="center" valign="middle">
				<span id="spPolicyNo"><%=strPolicyNo%></span>&nbsp;
			</td>
			<td height="20" colspan="1" align="center" valign="middle">保险期间</td>
			<td height="20" colspan="3" align="center" valign="middle">
				<span id="spInsuredDate"><%=strInsuredDate%></span>
			</td>
		</tr>
		<tr>
			<td height="20" colspan="8" align="left" valign="middle">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;歷史賠付紀錄：</td>
		</tr>
		<tr style="font-family: '宋体'; font-size: 10pt">
			<td width="17%" height="20" align="center" valign="middle">赔案号</td>
			<td height="20" colspan="3" align="center" valign="middle">
				<span id="spClaimNo"><%=strClaimNo%></span>&nbsp;
			</td>
			<td height="20" colspan="1" align="center" valign="middle">事故时间</td>
			<td height="20" colspan="3" align="center" valign="middle">
				<span id="spAcciDate1"><%=strAcciDate%></span>
			</td>
		</tr>
		<tr style="font-family: '宋体'; font-size: 10pt">
			<td width="17%" height="20" align="center" valign="middle">理赔决定 /给付金额</td>
			<td height="20" colspan="3" align="center" valign="middle">&nbsp;</td>
			<td height="20" colspan="1" align="center" valign="middle">事故原因</td>
			<td height="20" colspan="3" align="center" valign="middle">
				<span id="spAcciDamageName"><%=strDamageName%></span>
			</td>
		</tr>
		<tr>
			<td colspan="9">
				<table width="100%" border="0" cellspacing="0" cellpadding="0" style="font-family: '宋体'; font-size: 10pt">
					<tr>
						<td height="20">核赔结论、依据：</td>
					</tr>
					<tr>
						<td height="20" style='word-break: break-all'>
							&nbsp;&nbsp;&nbsp;&nbsp;<%=strContext%></td>
					</tr>
				</table>
			</td>
		</tr>
		<tr style="font-family: '宋体'; font-size: 10pt">
			<td width="2%" height="25" rowspan="1" align="center" valign="middle">
				<p>理算</p>
			</td>
			<td height="20" colspan="1" align="center" valign="middle">计算公式：</td>
			<td colspan="7">
				<table width="100%" border="0" cellspacing="0" cellpadding="0" style="font-family: '宋体'; font-size: 10pt">
					<tr>
						<td height="60" style='word-break: break-all'>
							&nbsp;&nbsp;&nbsp;&nbsp;<%=tempContext%></td>
					</tr>
				</table>
			</td>
			<td style="display: none" id="tdContext" colspan="7" height="60" align="left" valign="top"></td>
		</tr>
		<%
			//<tr style=" font-family:'宋体';font-size:10pt ">
			//<td height="20" colspan="2"><div align="center"> 给付项目 </div></td>
			//<td height="20" colspan="2"><div align="center"> 给付金额 </div></td>
			//<td height="20" colspan="2"><div align="center"> 不合理项目 </div></td>
			//<td height="20" colspan="2"><div align="center"> 不合理金额 </div></td>
			//</tr>
			//<tr style=" font-family:'宋体';font-size:10pt ">
			//  <td height="20" colspan="2"> <div align="center">意外身故 </div></td>
			//  <td height="20" colspan="2"><div align="center">&nbsp;=sumrealpay1&nbsp;</div></td>
			//  <td height="20" colspan="2"><div align="center">自费金额</div></td>
			// <td height="20" colspan="2"><div align="center">&nbsp;=sumrest&nbsp;</div></td>
			// </tr>
			// <tr style=" font-family:'宋体';font-size:10pt ">
			//   <td height="20" colspan="2"><div align="center"> 意外残疾</div></td>
			//   <td height="20" colspan="2"><div align="center">&nbsp;=sumrealpay2&nbsp;</div></td>
			//   <td height="20" colspan="2"> <div align="center">&nbsp;&nbsp;</div></td>
			//   <td height="20" colspan="2"><div align="center">&nbsp;&nbsp;</div></td>
			// </tr>
			// <tr style=" font-family:'宋体';font-size:10pt ">
			//   <td height="20" colspan="2"><div align="center">意外医疗（门急诊）</div></td>
			//   <td height="20" colspan="2"><div align="center">&nbsp;=sumrealpay3&nbsp;</div></td>
			//   <td height="20" colspan="2"><div align="center">&nbsp;</div></td>
			//   <td height="20" colspan="2"><div align="center">&nbsp;&nbsp;</div></td>
			// </tr>
			// <tr style=" font-family:'宋体';font-size:10pt ">
			//   <td height="20" colspan="2"><div align="center">意外医疗（住院） </div></td>
			//   <td height="20" colspan="2"><div align="center">&nbsp;=sumrealpay4&nbsp;</div></td>
			//   <td height="20" colspan="2"><div align="center">&nbsp;&nbsp;</div></td>
			//   <td height="20" colspan="2"><div align="center">&nbsp;&nbsp;</div></td>
			// </tr>
			// <tr style=" font-family:'宋体';font-size:10pt ">
			//   <td height="20" colspan="2"><div align="center"> 住院补贴 </div></td>
			//   <td height="20" colspan="2"><div align="center">&nbsp;=sumrealpay6&nbsp;</div></td>
			//   <td height="20" colspan="2"><div align="center">&nbsp;&nbsp;</div></td>
			//   <td height="20" colspan="2"><div align="center">&nbsp;&nbsp;</div></td>
			// </tr>
			// <tr style=" font-family:'宋体';font-size:10pt ">
			//   <td height="20" colspan="2"><div align="center"> 合计</div></td>
			//   <td height="20" colspan="2"><div align="center">&nbsp;=sumPaid1</div></td>
			//   <td height="20" colspan="2"><div align="center">合计 </div></td>
			//   <td height="20" colspan="2"><div align="center">&nbsp;=sumrest</td>
			//</tr>
		%>
		<%--<tr style=" font-family:'宋体';font-size:10pt ">
    <td height="20" colspan="2"><div align="center"> 给付项目 </div></td>
    <td height="20" colspan="2"><div align="center"> 给付金额 </div></td>
    <td height="20" colspan="2"><div align="center"> 不合理项目 </div></td>
    <td height="20" colspan="2"><div align="center"> 不合理金额 </div></td>
  </tr>--%>
		<tr style="font-family: '宋体'; font-size: 10pt">
			<td height="20" colspan="9" align="center" valign="middle">
				<div align="left">
					&nbsp;&nbsp;实际给付金额：&nbsp;&nbsp;<%=strCSumDutyPaid%>￥:
					<%=strSumDutyPaid%>元
				</div>
			</td>
		</tr>
		<tr style="font-family: '宋体'; font-size: 10pt">
			<td height="20" colspan="9" align="center" valign="middle">
				<div align="left">
					&nbsp;&nbsp;查勘费用：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=strCChargeAmoutcheck%>￥:
					<%=strChargeAmoutcheck%>元
				</div>
			</td>
		</tr>
		<tr style="font-family: '宋体'; font-size: 10pt">
			<td height="20" colspan="9" align="center" valign="middle">
				<div align="left">
					&nbsp;&nbsp;预付赔款：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;￥:
					<%=strSumprepaid%>元
				</div>
			</td>
		</tr>
		<tr style="font-family: '宋体'; font-size: 10pt">
			<td height="20" colspan="9" align="center" valign="middle">
				<div align="left">
					&nbsp;&nbsp;结案总金额：&nbsp;&nbsp;&nbsp;&nbsp;<%=strCSumDutyPaid%>￥:
					<%=strSumDutyPaid%>元
				</div>
			</td>
		</tr>
		<!-- <tr style=" font-family:'宋体';font-size:10pt ">
    <td height="20" colspan="9">      <div align="center"> 分公司审核意见 </div></td>
  </tr>
  <tr style=" font-family:'宋体';font-size:10pt ">
    <td height="20" colspan="3"><table width="100%"  border="0" cellspacing="0" cellpadding="0" style=" font-family:'宋体';font-size:10pt ">
      <tr>
        <td height="20"> &nbsp;&nbsp;经办意见： </td>
      </tr>
      <tr>
        <td height="20">&nbsp;</td>
      </tr>
      <tr>
        <td height="20"> &nbsp;&nbsp;经办人： </td>
      </tr>
      <tr>
        <td height="20"> 
          <div align="center">&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;月&nbsp;&nbsp;日&nbsp; </div></td>
      </tr>
    </table></td>
    <td height="20" colspan="4"><table width="100%"  border="0" cellspacing="0" cellpadding="0" style=" font-family:'宋体';font-size:10pt ">
      <tr>
        <td height="20"> &nbsp;&nbsp; 审核意见：</td>
      </tr>
      <tr>
        <td height="20">&nbsp;</td>
      </tr>
      <tr>
        <td height="20"> &nbsp;&nbsp; 审核人 ： </td>
      </tr>
      <tr>
        <td height="20">          
          <div align="center">&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;月&nbsp;&nbsp;日&nbsp; &nbsp; </div></td>
      </tr>
    </table></td>
    <td height="20" colspan="2"><table width="100%"  border="0" cellspacing="0" cellpadding="0" style=" font-family:'宋体';font-size:10pt ">
      <tr>
        <td height="20"> &nbsp; 签批意见： </td>
      </tr>
      <tr>
        <td height="20">&nbsp;</td>
      </tr>
      <tr>
        <td height="20"> &nbsp;&nbsp;签批人

： </td>
      </tr>
      <tr>
        <td height="20">          
          <div align="center">&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;月&nbsp;&nbsp;日&nbsp; </div></td>
      </tr>
    </table></td>
  </tr> -->
		<tr style="font-family: '宋体'; font-size: 10pt">
			<td height="10" colspan="9">
				<div align="center">审核意见</div>
			</td>
		</tr>
		<tr>
			<td height="20" colspan="3">
				<table width="100%" border="0" cellspacing="0" cellpadding="0" style="font-family: '宋体'; font-size: 10pt">
					<tr>
						<td height="20">&nbsp;&nbsp;经办意见：</td>
					</tr>
					<tr>
						<td height="20">&nbsp;</td>
					</tr>
					<tr>
						<td height="20">&nbsp;&nbsp;经办人：</td>
					</tr>
					<tr>
						<td height="20">
							<div align="center">&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;月&nbsp;&nbsp;日&nbsp;</div>
						</td>
					</tr>
				</table>
			</td>
			<td height="20" colspan="4">
				<table width="100%" border="0" cellspacing="0" cellpadding="0" style="font-family: '宋体'; font-size: 10pt">
					<tr>
						<td height="20">&nbsp;&nbsp; 审核意见：</td>
					</tr>
					<tr>
						<td height="20">&nbsp;</td>
					</tr>
					<tr>
						<td height="20">&nbsp;&nbsp; 审核人 ：</td>
					</tr>
					<tr>
						<td height="20">
							<div align="center">&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;月&nbsp;&nbsp;日&nbsp; &nbsp;</div>
						</td>
					</tr>
				</table>
			</td>
			<td height="20" colspan="2">
				<table width="100%" border="0" cellspacing="0" cellpadding="0" style="font-family: '宋体'; font-size: 10pt">
					<tr>
						<td height="20">&nbsp; 签批意见：</td>
					</tr>
					<tr>
						<td height="20">&nbsp;</td>
					</tr>
					<tr>
						<td height="20">&nbsp;&nbsp;签批人 ：</td>
					</tr>
					<tr>
						<td height="20">
							<div align="center">&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;月&nbsp;&nbsp;日&nbsp;</div>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<!--include打印按钮-->
	<%--modify by liuwei at 2011-03-04 start--%>
	<%-- <jsp:include page="/common/print/PrintButton.jsp" /> --%>
	<jsp:include page="/common/print/CompensatePrintButton.jsp" />
	<%--modify by liuwei at 2011-03-04 end--%>
</body>
</html>
