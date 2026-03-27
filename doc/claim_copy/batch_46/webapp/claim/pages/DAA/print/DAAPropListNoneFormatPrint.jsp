<%--
****************************************************************************
* DESC       ：机动车辆保险财产损失确认书列印页面
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
<%@page import="com.sinosoft.claim.common.ConstantCodes"%> 

<%
 	//变量声明部分
 	String strClaimNo = request.getParameter("ClaimNo"); //赔案号
 	String strPolicyNo = "";
 	String strInsureCarFlag = ""; //是否是本保单车辆
 	String strKindCode = ""; //险别
 	double dblAmount = 0; //责任限额
 	double dblSumLoss = 0; //价格
 	double dblSumDefLoss = 0; //损失金额
 	String strApproverName = ""; //复核人
 	String strHandlerName = ""; //经办人
 	String strMessage = "";

 	//代码翻译变量
 	String strCode = "";
 	String strName = "";
 	boolean isChinese = true; //中文标志

 	//对象定义部分  
 	PrpLclaimDto prpLclaimDto = null; //ClaimDto对象
 	PrpLthirdPartyDto prpLthirdPartyDto = null; //ThirdPartyDto对象
 	PrpLpropDto prpLpropDto = null; //propDto对象
 	PrpCitemKindDto prpItemKindDto = null; //保单的ItemKindDto对象

 	int intClaimCount = 0; //ClaimDto对象的记录数
 	int intPropCount = 0; //PropDto对象的记录数
 	int intThirdPartyCount = 0; //ThirdPartyDto对象的记录数
 	int intItemKindCount = 0; //ItemKindDto对象的记录数

 	int index = 0;

 	//得到ClaimDto,RegistDto,CertainLossDto,PolicyDto对象
 	ClaimDto claimDto = (ClaimDto) request.getAttribute("claimDto");
 	PolicyDto policyDto = (PolicyDto) request.getAttribute("policyDto");
 	RegistDto registDto = (RegistDto) request.getAttribute("registDto");
 	CheckDto checkDto = (CheckDto) request.getAttribute("checkDto");
 	CertainLossDto certainLossDto = (CertainLossDto) request
 			.getAttribute("certainLossDto");

 	UICodeAction uiCodeAction = new UICodeAction();
 	//得到保单号
 	strPolicyNo = registDto.getPrpLregistDto().getPolicyNo();

 	//得到blPrpCitemCar对象
 	if (policyDto.getPrpCitemKindDtoList() != null) {
 		intItemKindCount = policyDto.getPrpCitemKindDtoList().size();
 	}

 	//得到prpLclaimDto对象
 	PrpLregistDto prplRegistDto = registDto.getPrpLregistDto();
 	PrpLclaimDto prplClaimDto = claimDto.getPrpLclaimDto();

 	//得到blPrpLthirdParty对象的记录数 
 	if (registDto.getPrpLthirdPartyDtoList() != null) {
 		intThirdPartyCount = registDto.getPrpLthirdPartyDtoList()
 				.size();
 	}

 	//得到blPrpLprop对象的记录数  
 	if (certainLossDto.getPrpLpropDtoList() != null) {
 		intPropCount = certainLossDto.getPrpLpropDtoList().size();
 	}
 %> 

<script language="javascript">
function loadForm() {
	//*****理赔车辆信息PrpLthirdParty*****
	tdPolicyNo.innerHTML = '保险单号：' + '<%=strPolicyNo%>';
	tdCertainLoss.innerHTML = '定损单编号：';
	tdRegistNo.innerHTML = '报案编号：' + '<%=StringConvert.encode(prplRegistDto.getRegistNo())%>';
	tdDamageKind.innerHTML = '出险险别：' + '<%=StringConvert.encode(prplClaimDto.getDamageKind())%>'; 
	<%  
	  if(registDto.getPrpLthirdPartyDtoList()!=null){
	    for(index=0;index<intThirdPartyCount;index++)
	    {
	      prpLthirdPartyDto = (PrpLthirdPartyDto)registDto.getPrpLthirdPartyDtoList().get(index);
	      strInsureCarFlag     = prpLthirdPartyDto.getInsureCarFlag();
	      if( strInsureCarFlag.equals("1") ) 
	      { 
	        //条款类别
	        strCode = "";
	        strName = "";
	        strCode = StringConvert.encode(prpLthirdPartyDto.getClauseType());
	        strName = uiCodeAction.translateCodeCode("ClauseType",strCode,isChinese);
%>        
        tdBrandName.innerHTML = '厂牌型号：' + '<%=StringConvert.encode(prpLthirdPartyDto.getBrandName())%>';
        tdLicenseNo.innerHTML = '号牌号码：' + '<%=StringConvert.encode(prpLthirdPartyDto.getLicenseNo())%>';
<%        
        //承保公司
        strCode = "";
        strName = "";
        strCode = StringConvert.encode(prpLthirdPartyDto.getInsureComCode());
        
        strName = uiCodeAction.translateComCode(strCode,isChinese);
        
%>        
        tdComName.innerHTML   = '承保公司：' + '<%=strName%>';
        tdAmount.innerHTML = '责任限额：';
<%  				}
				}
			}

			//*****标的子险信息PrpTitemKind*****
			dblAmount = 0;

			if (policyDto.getPrpCitemKindDtoList() != null) {
				for (index = 0; index < intItemKindCount; index++) {
					prpItemKindDto = (PrpCitemKindDto) policyDto
							.getPrpCitemKindDtoList().get(index);
					strKindCode = prpItemKindDto.getKindCode();

					if (strKindCode.equals(ConstantCodes.KINDCODE_D_B)
							|| strKindCode.equals(ConstantCodes.KINDCODE_D_D2)) {
						dblAmount += prpItemKindDto.getAmount();
					}
				}
			}%>
		tdAmount.innerHTML = '责任限额：' + '<%=new DecimalFormat("#,##0.00").format(dblAmount)%>';
}
</script>
<html>
<link rel="stylesheet" type="text/css" href="/claim/DAA/print/StandardPrint.css">
<body bgcolor="#FFFFFF" onload="loadForm();">
	<!-- 标题部分 -->
	<table width="92%" align="center" cellspacing="0" cellpadding="0" border="0">
		<tr height="40">
			<td colspan="2" height="40" align=top align=center style="font-family: 宋体; font-size: 14pt;">
				<B>机动车辆保险财产损失确认书<B>
			</td>
		</tr>
		<tr height="25">
			<td width="60%" align=left id="tdRegistNo" style="font-family: 宋体; font-size: 10pt;">报案编号：</td>
			<td width="40%" align=left id="tdCertainLoss" style="font-family: 宋体; font-size: 10pt;">定损单编号：</td>
		</tr>
	</table>
	<!-- 主体部分 -->
	<table border=1 width="92%" align="center" cellspacing="0" cellpadding="2" style="border-collapse: collapse" bordercolor="#111111" style="font-family:宋体; font-size:10pt;">
		<tr height="25">
			<td colspan=2 width="40%" id="tdPolicyNo">保险单号：</td>
			<td colspan=3 width="30%" id="tdBrandName">厂牌型号：</td>
			<td colspan=3 width="30%" id="tdComName">承保公司：</td>
		</tr>
		<tr height="25">
			<td colspan=2 width="40%" id="tdLicenseNo">号牌号码：</td>
			<td colspan=3 width="30%" id="tdAmount">责任限额：</td>
			<td colspan=3 width="30%" id="tdDamageKind">出险险别：</td>
		</tr>
		<tr height="25" align="center">
			<td width="5%">序号</td>
			<td width="30%">损失项目名称</td>
			<td colspan=2 width="20%">损坏情况</td>
			<td colspan=2 width="20%">价 格</td>
			<td colspan=2 width="20%">
				保险公司<br>核定损失金额
			</td>
		</tr>
		<%
			int intPropCountTmp = 0; //列印的财产损失信息记录数

			//财产损失信息
			if (intPropCount > 28) //项数超过指定列印范围
			{
				intPropCountTmp = 28;
			} else {
				intPropCountTmp = intPropCount;
			}

			if (certainLossDto.getPrpLpropDtoList() != null) {
				for (index = 0; index < intPropCountTmp; index++) {
					prpLpropDto = (PrpLpropDto) certainLossDto
							.getPrpLpropDtoList().get(index);

					dblSumLoss += prpLpropDto.getSumLoss();
					dblSumDefLoss += prpLpropDto.getSumDefLoss();
		%>
		<tr height="25">
			<td align="center">
				<%=index + 1%>
			</td>
			<td>
				<%=StringConvert.encode(prpLpropDto.getFeeTypeName())%>
			</td>
			<td colspan=2>
				<%=StringConvert.encode(prpLpropDto.getRemark())%>
			</td>
			<%
				if (prpLpropDto.getSumLoss() == 0) {
			%>
			<td colspan=2>&nbsp;</td>
			<%
				} else {
			%>
			<td colspan=2>
				<%=new DecimalFormat("#,##0.00")
								.format(prpLpropDto.getSumLoss())%>
			</td>
			<%
				}
						if (prpLpropDto.getSumDefLoss() == 0) {
			%>
			<td colspan=2>&nbsp;</td>
			<%
				} else {
			%>
			<td colspan=2>
				<%=new DecimalFormat("#,##0.00")
								.format(prpLpropDto.getSumDefLoss())%>
			</td>
			<%
				}
			%>
		</tr>
		<%
			}
			}

			for (index = 0; index < 28 - intPropCountTmp; index++) {
		%>
		<tr height="25">
			<td align="center"><%=intPropCount + index + 1%></td>
			<td>&nbsp;</td>
			<td colspan=2>&nbsp;</td>
			<td colspan=2>&nbsp;</td>
			<td colspan=2>&nbsp;</td>
		</tr>
		<%
			}
		%>
		<tr height="25">
			<td colspan="4" align="center">合&nbsp;&nbsp;&nbsp;&nbsp;计</td>
			<td colspan=2><%=DataUtils.zeroToEmpty(dblSumLoss)%></td>
			<td colspan=2><%=DataUtils.zeroToEmpty(dblSumDefLoss)%></td>
		</tr>
		<tr>
			<td colspan=2>
				<table width="100%" height="100%" align="center" cellspacing="0" cellpadding="0" border="0" style="font-family: 宋体; font-size: 10pt;">
					<tr height="50">
						<td width="33%" align="left" valign="top">&nbsp;保险公司（签章）：</td>
					</tr>
					<tr height="25">
						<td width="33%" align="right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;日&nbsp;&nbsp;&nbsp;</td>
					</tr>
				</table>
			</td>
			<td colspan=3>
				<table width="100%" height="100%" align="center" cellspacing="0" cellpadding="0" border="0" style="font-family: 宋体; font-size: 10pt;">
					<tr height="50">
						<td width="33%" align="left" valign="top">&nbsp;被保险人（签章）：</td>
					</tr>
					<tr height="25">
						<td width="33%" align="right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;日&nbsp;&nbsp;&nbsp;</td>
					</tr>
				</table>
			</td>
			<td colspan=3>
				<table width="100%" height="100%" align="center" cellspacing="0" cellpadding="0" border="0" style="font-family: 宋体; font-size: 10pt;">
					<tr height="50">
						<td width="33%">&nbsp;</td>
					</tr>
					<tr height="25">
						<td width="33%" align="right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;日&nbsp;&nbsp;&nbsp;</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<!-- 结尾部分 -->
	<table width="92%" align="center" cellspacing="0" cellpadding="2" border="0" style="font-family: 宋体; font-size: 10pt;">
		<tr align="left" height="25">
			<td width="50%" id="tdSanctioner">核赔人：</td>
			<td width="570%" id="tdHandlerName">经办人：</td>
		</tr>
		<table>
			<script language='javascript'>
				function printPage() {
					//add print liudaoping 2013-04-15
					//alert("【列印】功能屬於客制化需求，暫未開發，請知悉！");
					return false;
					tbButton.style.display = "none";
					window.print();
				}
			</script>
			<!-- 按钮部分 -->
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
