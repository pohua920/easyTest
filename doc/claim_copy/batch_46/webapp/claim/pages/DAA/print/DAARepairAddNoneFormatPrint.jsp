<%--
****************************************************************************
* DESC       ：机动车辆保险修理项目附页
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
	//System.out.println("===================修理附表====");
	//变量声明部分
	String printType = request.getParameter("printType");
	String licenseNo = request.getParameter("strLicenseNo");
	String strInsuredName = "";
	String strRegistNo = "";

	String engineNo = "";
	String frameNo = "";
	String brandName = "";

	//变量声明部分
	String strClaimNo = request.getParameter("ClaimNo"); //赔案号
	String strInsureCarFlag = ""; //是否是本保单车辆
	double dblManHour = 0; //工时
	double dblManHourFee = 0; //工时费
	double manHourFee = 0; //工时费
	double dblMaterialFee = 0; //材料费 
	String strSanctioner = ""; //核准人
	String strApproverName = ""; //复核人
	String strOperatorName = ""; //操作人
	String strMessage = "";

	//代码翻译变量
	String strCode = "";
	String strName = "";
	boolean isChinese = true; //中文标志

	//对象定义部分  
	PrpLclaimDto prpLclaimDto = null; //ClaimDto对象
	PrpLthirdPartyDto prpLthirdPartyDto = null; //ThirdPartyDto对象
	PrpLrepairFeeDto prpLrepairFeeDto = null; //RepairFeeDto对象

	int intClaimCount = 0; //ClaimDto对象的记录数
	int intRepairFeeCount = 0; //RepairFeeDto对象的记录数
	int intThirdPartyCount = 0; //ThirdPartyDto对象的记录数

	int index = 0;

	//System.out.println("===================修理附表=2===");
	//得到ClaimDto,RegistDto,CertainLossDto,PolicyDto对象
	/*  ClaimDto claimDto = (ClaimDto)request.getAttribute("claimDto");  */

	PolicyDto policyDto = (PolicyDto) request.getAttribute("policyDto");
	RegistDto registDto = (RegistDto) request.getAttribute("registDto");
	CheckDto checkDto = (CheckDto) request.getAttribute("checkDto");
	CertainLossDto certainLossDto = (CertainLossDto) request
			.getAttribute("certainLossDto");
	//System.out.println("===================修理附表=21===");   
	UICodeAction uiCodeAction = new UICodeAction();
	//得到保单号
	String strPolicyNo = policyDto.getPrpCmainDto().getPolicyNo();
	/* prpLclaimDto = claimDto.getPrpLclaimDto();*/
	strInsuredName = policyDto.getPrpCmainDto().getInsuredName();
	strRegistNo = certainLossDto.getPrpLthirdPartyDto().getRegistNo();
	//System.out.println("===================修理附表=22===");
	//得到blPrpLthirdParty对象的记录数 
	if (certainLossDto.getPrpLthirdPartyDtoList() != null) {
		intThirdPartyCount = certainLossDto.getPrpLthirdPartyDtoList()
				.size();
		//System.out.println("===================修理附表=23===");
	}
	//System.out.println("===================修理附表=233===" +certainLossDto.getPrpLrepairFeeDtoList().size());
	//得到blPrpLrepairFee对象的记录数
	if (certainLossDto.getPrpLrepairFeeDtoList() != null) {
		intRepairFeeCount = certainLossDto.getPrpLrepairFeeDtoList()
				.size();
		//System.out.println("===================修理附表=24===");
	}
	//System.out.println("===================修理附表=25===" +certainLossDto.getPrpLthirdPartyDtoList().size());
	if (intRepairFeeCount < 24) {
		strMessage = "抱歉!修理项目未超过24项,不需列印清单附页,请您列印清单。";
		//System.out.println(strMessage );
%>
    <jsp:include page="/common/pub/UIErrorPage.jsp">
      <jsp:param name="Picture" value="F" />
      <jsp:param name="Content" value="<%=strMessage%>" />
    </jsp:include>
<%
	return;
	}
	//System.out.println("lijiyuan12");
%>  

<script language="javascript">
function loadForm() { <%//*****立案信息表PrpLclaim*****
			//承保公司
			strCode = "";
			strName = "";
			strCode = StringConvert.encode(policyDto.getPrpCmainDto()
					.getComCode());
			strName = uiCodeAction.translateComCode(strCode, isChinese);%> <%if (certainLossDto.getPrpLthirdPartyDtoList() != null) {
				for (index = 0; index < intThirdPartyCount; index++) {
					prpLthirdPartyDto = (PrpLthirdPartyDto) certainLossDto
							.getPrpLthirdPartyDtoList().get(index);
					strInsureCarFlag = prpLthirdPartyDto.getInsureCarFlag();
					//System.out.println("===================修理附表=4==3="+prpLthirdPartyDto.getLicenseNo()); 
					if (prpLthirdPartyDto.getLicenseNo().equals(licenseNo)) {
						engineNo = prpLthirdPartyDto.getEngineNo();
						frameNo = prpLthirdPartyDto.getFrameNo();
						brandName = prpLthirdPartyDto.getBrandName();
					}

					if (strInsureCarFlag.equals("1")) {
						//条款类别
						strCode = "";
						strName = "";
						strCode = StringConvert.encode(prpLthirdPartyDto
								.getClauseType());
						strName = uiCodeAction.translateCodeCode("ClauseType",
								strCode, isChinese);%>

				<%//System.out.println("===================修理附表=4===");
					}
				}
			}%>
}
</script>
<html>
<head>
<title>机动车辆保险车辆损失情况确认书修理项目清单列印</title>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<link rel="stylesheet" type="text/css" href="/claim/DAA/print/StandardPrint.css">
</head>
<body bgcolor="#FFFFFF" onload="loadForm();">
	<!-- 标题部分 -->
	<table width="92%" align="center" cellspacing="0" cellpadding="0" border="0">
		<tr>
			<td colspan="2" height="40" align="center" style="text-align: center; font-family: 宋体; font-size: 14pt;">
				<span><img src="/claim/images/LOGO.jpg" /></span>
			</td>
		</tr>
		<tr>
			<td colspan="2" height="40" align=center style="text-align: center; font-family: 宋体; font-size: 14pt;">
				<strong><B>机动车辆保险定损报告明细（修理-附页）<B> </b></b></strong>
			</td>
		</tr>
	</table>
	<table width="92%" align="center" cellspacing="0" cellpadding="0" border="0">
		<tr height=30>
			<td width="30%" align=left id="insuredName" style="font-family: 宋体; font-size: 10pt;">
				被保险人：<%=strInsuredName%>
			</td>
			<td width="40%" align=left id="registNo" style="font-family: 宋体; font-size: 10pt;">
				报案号：<%=strRegistNo%>
			</td>
			<td width="30%" align=right id="pageCount" style="font-family: 宋体; font-size: 10pt;">共&nbsp;&nbsp;&nbsp;&nbsp;页&nbsp;第&nbsp&nbsp;&nbsp;&nbsp页</td>
		</tr>
	</table>
	<table border=1 width="92%" align="center" cellspacing="0" cellpadding="0" border="0" style="border-collapse: collapse" bordercolor="#111111" style="font-family:宋体; font-size:10pt;">
		<tr height="25" border=1>
			<td width="15%" align=center id="tdLicenseNo" style="font-family: 宋体; font-size: 10pt;">号牌号码:</td>
			<td width="35%" align=center><%=licenseNo%></td>
			<td width="15%" align=center id="tdPolicyNo" style="font-family: 宋体; font-size: 10pt;">保险单号:</td>
			<td width="35%" align=center><%=strPolicyNo%></td>
		</tr>
		<tr height="25">
			<td width="15%" align=center id="" style="font-family: 宋体; font-size: 10pt;">发动机号:</td>
			<td width="35%" align=center><%=engineNo%></td>
			<td width="15%" align=center id="" style="font-family: 宋体; font-size: 10pt;">车架号:</td>
			<td width="35%" align=center><%=frameNo%>
			</td>
		</tr>
	</table>
	<!-- 主体部分 -->
	<table border=1 width="92%" align="center" cellspacing="0" cellpadding="2" style="border-collapse: collapse" bordercolor="#111111" style="font-family:宋体; font-size:10pt;">
		<tr height="25" align="center">
			<td width="5%">序号</td>
			<td width="20%">修理项目名称</td>
			<td width="13%">工时</td>
			<td width="16%">工时费</td>
			<td width="18%">材料费</td>
			<!--<td width="18%">核损金额</td>-->
			<td width="20%">备 注</td>
		</tr>
		<%
			if (certainLossDto.getPrpLrepairFeeDtoList() != null) {
				for (index = 24; index < intRepairFeeCount; index++) {
					prpLrepairFeeDto = (PrpLrepairFeeDto) certainLossDto
							.getPrpLrepairFeeDtoList().get(index);
					//System.out.println("1111111111111=="+prpLrepairFeeDto.getManHour());
					//System.out.println("3333333333==="+prpLrepairFeeDto.getManHourUnitPrice());
					if (prpLrepairFeeDto.getVeriManHour() == 0
							&& prpLrepairFeeDto.getVeriManUnitPrice() == 0) {
						manHourFee = prpLrepairFeeDto.getManHour()
								* prpLrepairFeeDto.getManHourUnitPrice();
						dblManHour += prpLrepairFeeDto.getManHour();
						dblManHourFee += prpLrepairFeeDto.getManHour()
								* prpLrepairFeeDto.getManHourUnitPrice();
						dblMaterialFee += prpLrepairFeeDto.getMaterialFee();
					} else {
						manHourFee = prpLrepairFeeDto.getVeriManHour()
								* prpLrepairFeeDto.getVeriManUnitPrice();
						dblManHour += prpLrepairFeeDto.getVeriManHour();
						dblManHourFee += prpLrepairFeeDto.getVeriManHour()
								* prpLrepairFeeDto.getVeriManUnitPrice();
						dblMaterialFee += prpLrepairFeeDto.getVeriMaterialFee();
					}

					//dblManHour     += Double.parseDouble(DataUtils.nullToZero(prpLrepairFeeDto.getManHour()));

					//dblManHourFee  += Double.parseDouble(DataUtils.nullToZero(prpLrepairFeeDto.getManHourFee()));

					//dblMaterialFee += Double.parseDouble(DataUtils.nullToZero(prpLrepairFeeDto.getMaterialFee()));
					//核准人
					strCode = "";
					strCode = StringConvert.encode(prpLrepairFeeDto
							.getSanctioner());
					strSanctioner = uiCodeAction.translateUserCode(strCode,
							isChinese);
					//复核人
					strCode = "";
					strCode = StringConvert.encode(prpLrepairFeeDto
							.getApproverCode());
					strApproverName = uiCodeAction.translateUserCode(strCode,
							isChinese);
					//操作员
					strCode = "";
					strCode = StringConvert.encode(prpLrepairFeeDto
							.getOperatorCode());
					strOperatorName = uiCodeAction.translateUserCode(strCode,
							isChinese);
		%>
		<tr height="25">
			<td align="center">
				<%=index + 1%>
			</td>
			<td>
				<%=StringConvert.encode(prpLrepairFeeDto
							.getCompName())%>
			</td>
			<%
				//if( prpLrepairFeeDto.getManHour()==0 )
						if (prpLrepairFeeDto.getManHour() == 0) {
			%>
			<td>&nbsp;</td>
			<%
				} else {
			%>
			<td align="center">
				<%
					if (prpLrepairFeeDto.getVeriManHour() == 0
										&& prpLrepairFeeDto.getVeriManUnitPrice() == 0) {
				%>
				<%=new DecimalFormat("#,##0.00")
									.format(prpLrepairFeeDto.getManHour())%>
				<%
					} else {
				%>
				<%=new DecimalFormat("#,##0.00")
									.format(prpLrepairFeeDto.getVeriManHour())%>
				<%
					}
				%>
			</td>
			<%
				}

						//System.out.println("manHourFee==="+manHourFee);

						if (manHourFee == 0) { //System.out.println("manHourFee==="+manHourFee);
			%>
			<td>&nbsp;</td>
			<%
				} else {
			%>
			<td align="center">
				<%=new DecimalFormat("#,##0.00")
								.format(manHourFee)%>
			</td>
			<%
				}

						if (prpLrepairFeeDto.getMaterialFee() == 0) {
			%>
			<td>&nbsp;</td>
			<%
				} else {
			%>
			<td align="center">
				<%
					if (prpLrepairFeeDto.getVeriManHour() == 0
										&& prpLrepairFeeDto.getVeriManUnitPrice() == 0) {
				%>
				<%=new DecimalFormat("#,##0.00")
									.format(prpLrepairFeeDto.getMaterialFee())%>
				<%
					} else {
				%>
				<%=new DecimalFormat("#,##0.00")
									.format(prpLrepairFeeDto
											.getVeriMaterialFee())%>
				<%
					}
				%>
				<!-- <%=new DecimalFormat("#,##0.00")
								.format(prpLrepairFeeDto.getMaterialFee())%> -->
			</td>
			<%
				}
			%>
			<td>&nbsp;</td>
		</tr>
		<%
			}
			}
		%>
		<tr height="25">
			<td colspan="2" align="center">小 计</td>
			<td align="center"><%=new DecimalFormat("#,##0.00").format(dblManHour)%></td>
			<td>
				<table width="100%" style="font-family: 宋体; font-size: 10pt;">
					<tr>
						<td width="60%" align="right"><%=new DecimalFormat("#,##0.00").format(dblManHourFee)%></td>
						<td width="40%">&nbsp;</td>
					</tr>
				</table>
			</td>
			<%
				if (dblMaterialFee == 0) {
			%><td>&nbsp;</td>
			<%
				} else {
			%>
			<td>
				<table width="100%" style="font-family: 宋体; font-size: 10pt;">
					<tr>
						<td width="60%" align="right"><%=new DecimalFormat("#,##0.00").format(dblMaterialFee)%></td>
						<td width="40%">&nbsp;</td>
					</tr>
				</table>
			</td>
			<%
				}
			%>
			<td>&nbsp;</td>
		</tr>
	</table>
	<!-- 结尾部分 -->
	<table width="92%" align="center" cellspacing="0" cellpadding="2" border="0" style="border-collapse: collapse" bordercolor="#111111" style="font-family:宋体; font-size:10pt;">
		<tr height=25>
			<td width="50%" id="tdSanctioner">
				查勘定损人：<%=strSanctioner%></td>
			<td width="50%" id="tdApproverName">
				核价人：<%=strApproverName%></td>
			<!-- <td width="37%" id="tdOperatorName">定损制表人：<%=strOperatorName%></td>-->
		</tr>
	</table>
	<script language='javascript'>
      function printPage()
      {
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
