<%--
****************************************************************************
* DESC       ：机动车辆保险结案催告、注销通知书打印初始化
* AUTHOR     ：zhulianyu
* CREATEDATE ：2005-11-14
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>

<%-- 引入bean类部分 --%>
<%@page import="java.text.*"%> 
<%@ page import="java.util.Date"%>
<%@page import="com.sinosoft.claim.ui.control.action.*"%>
<%@page import="com.sinosoft.claim.dto.custom.*"%>
<%@page import="com.sinosoft.claim.dto.domain.*"%> 
<%@page import="com.sinosoft.claim.util.*"%> 
<%@page import="com.sinosoft.sysframework.common.util.*"%> 
<%@page import="com.sinosoft.sysframework.common.datatype.DateTime"%> 
<%@page import="com.sinosoft.sysframework.exceptionlog.*"%> 

<%
 	//变量声明部分
 	String strClaimNo = request.getParameter("ClaimNo"); //赔案号
 	String strRegistNo = ""; //报案号
 	String strPolicyNo = ""; //保单号
 	String strInsuredDate = ""; //保险期间
 	String strDamageStartDate = ""; //出险时间
 	String strDamageStartHour = ""; //出险小时
 	String strInsureCarFlag = ""; //是否为本保单车辆
 	double dblSumAmount1 = 0; //保险金额    
 	double dblSumAmount2 = 0; //责任限额    
 	String strRiskCode = ""; //险种代码     
 	String strKindCode = ""; //险别代码     
 	String strBLName = ""; //运输工具名称
 	String strCalculateFlag = ""; //是否记入保额
 	String strStartSiteName = ""; //货物运输起始地址
 	String strEndSiteName = ""; //货物运输终止地址
 	String strCaseNo = ""; //归档号
 	String strLossName = ""; //保险标的
 	String strUserCode = ((UserDto) request.getSession().getAttribute("user")).getUserCode();
 	String strMessage = "";
 	String strDamageName = ""; //出险原因

 	String comCName = ""; //保险公司中文名称
 	String addressCName = ""; //保险公司地址
 	String postCode = ""; //保险公司邮编
 	String phoneNumber = ""; //保险公司电话
 	String manager = ""; //保险公司经理
 	String faxNumber = ""; //保险公司传真

 	//代码翻译变量
 	String strCode = "";
 	String strName = "";
 	boolean isChinese = true; //中文标志 

 	//对象定义部分                                             
 	PrpDcompanyDto prpDcompanyDto = null; //打印此通知书user所在的公司                                          
 	PrpCitemKindDto prpItemKindDto = null; //保单的ItemKindDto对象
 	PrpLregistDto prpLregistDto = null; //RegistDto对象
 	PrpLclaimDto prpLclaimDto = null; //ClaimDto对象
 	PrpCmainCargoDto prpCmainCargoDto = null; //货运险保单信息对象

 	int intClaimCount = 0; //ClaimDto对象的记录数
 	int intItemKindCount = 0; //ItemKindDto对象的记录数
 	int intThirdPartyCount = 0; //ThirdPartyDto对象的记录数

 	UICompanyAction uiCompanyAction = new UICompanyAction();
 	prpDcompanyDto = uiCompanyAction.findByPrimaryKey(strUserCode); //得到保险公司的对象
 	if (prpDcompanyDto != null) {
 		comCName = prpDcompanyDto.getComCName(); //保险公司中文名称
 		addressCName = prpDcompanyDto.getAddressCName(); //保险公司地址
 		postCode = prpDcompanyDto.getPostCode(); //保险公司邮编
 		phoneNumber = prpDcompanyDto.getPhoneNumber(); //保险公司电话
 		manager = prpDcompanyDto.getManager(); //保险公司经理（联系人）
 		faxNumber = prpDcompanyDto.getFaxNumber(); //保险公司传真
 	}

 	int index = 0;
 	//得到ClaimDto,RegistDto,CertainLossDto,PolicyDto对象
 	ClaimDto claimDto = (ClaimDto) request.getAttribute("claimDto");
 	PolicyDto policyDto = (PolicyDto) request.getAttribute("policyDto");
 	RegistDto registDto = (RegistDto) request.getAttribute("registDto");
 	//得到表Dto对象
 	prpCmainCargoDto = policyDto.getPrpCmainCargoDto();

 	UICodeAction uiCodeAction = new UICodeAction();
 	//得到prpLclaimDto对象
 	prpLclaimDto = claimDto.getPrpLclaimDto();
 	strCaseNo = prpLclaimDto.getCaseNo();
 	//System.out.println("prpLregistDto对象");
 	//得到保单号  
 	strPolicyNo = prpLclaimDto.getPolicyNo();
 	if (policyDto.getPrpCmainCargoDto() != null) {
 		strStartSiteName = prpCmainCargoDto.getStartSiteName();
 		strEndSiteName = prpCmainCargoDto.getEndSiteName();
 		strBLName = prpCmainCargoDto.getBLName();
 	}
 	if (strStartSiteName.equals(""))
 		strStartSiteName = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
 	if (strEndSiteName.equals(""))
 		strEndSiteName = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
 	strDamageStartHour = prpLclaimDto.getDamageStartHour();
 	if (strDamageStartHour.equals("")) {
 		strDamageStartHour = "0";
 	} else {
 		strDamageStartHour = prpLclaimDto.getDamageStartHour().substring(0, 2);
 	}

 	//得到prpLregistDto对象
 	prpLregistDto = registDto.getPrpLregistDto();
 	strRegistNo = prpLregistDto.getRegistNo();
 	strDamageName = prpLregistDto.getDamageName();
 	strLossName = prpLregistDto.getLossName();//受损标的
 	//System.out.println("prpLregistDto对象");
 %>

<script language="javascript">
function loadForm() {
	//*****立案信息表PrpLregist*****
	tdCaseNo.innerHTML = '归档号:' + '<%=strCaseNo%>';
	tdPolicyNo.innerHTML = '<%=StringConvert.encode(prpLclaimDto.getPolicyNo())%>';
	tdRegistNo.innerHTML = '报案号:' + '<%=strRegistNo%>';
	tdInsuredName.innerHTML = '<%=StringConvert.encode(prpLclaimDto.getInsuredName())%>';
	tdDamageCause.innerHTML = '出险原因:' + '<%=StringConvert.encode(prpLregistDto.getDamageName())%>';
	tdLossName.innerHTML = '<%=strLossName%>'; <%
	if ((prpLclaimDto.getStartDate() + "").length() > 0)
		strInsuredDate = "自 " + prpLclaimDto.getStartDate().getYear() + "年" + prpLclaimDto.getStartDate().getMonth() + "月" + prpLclaimDto.getStartDate().getDate() + "日起";
	if ((prpLclaimDto.getEndDate() + "").length() > 0)

		strInsuredDate = strInsuredDate + "至 " + prpLclaimDto.getEndDate().getYear() + "年" + prpLclaimDto.getEndDate().getMonth() + "月" + prpLclaimDto.getEndDate().getDate() + "日止"; %>
		tdInsuredDate.innerHTML = '<%=strInsuredDate%>';
	tdBLName.innerHTML = '<%=strBLName%>';
	tdSiteName.innerHTML = '从' + '<%=strStartSiteName%>' + '起至' + '<%=strEndSiteName%>' + '止'; <% //出险险种
	strCode = "";
	strName = "";
	strCode = StringConvert.encode(prpLclaimDto.getRiskCode());
	strName = uiCodeAction.translateRiskCode(strCode, isChinese); %>
	//tdRiskName.innerHTML = '<%=strName%>';
	tdTitleName.innerHTML = '<%=strName%>' + '拒赔/註銷案件通知书'; <% strDamageStartDate = prpLclaimDto.getDamageStartDate().getYear() + " 年 " + prpLclaimDto.getDamageStartDate().getMonth() + " 月 " + prpLclaimDto.getDamageStartDate().getDate() + " 日" + prpLclaimDto.getDamageStartHour().substring(0, 2) + "&nbsp;时"; %>
		tdDamageStartDate.innerHTML = '<%=strDamageStartDate%>';
	tdDamageAddress.innerHTML = '<%=StringConvert.encode(prpLclaimDto.getDamageAddress())%>';

	<% //*****标的子险信息PrpTitemKind*****
	dblSumAmount1 = 0;
	dblSumAmount2 = 0;

	if (policyDto.getPrpCitemKindDtoList() != null) {
		for (index = 0; index < intItemKindCount; index++) {
			prpItemKindDto = (PrpCitemKindDto) policyDto.getPrpCitemKindDtoList().get(index);
			strCalculateFlag = prpItemKindDto.getCalculateFlag();
			if (strCalculateFlag.equals("Y")) {
				dblSumAmount1 += prpItemKindDto.getAmount();
			}
			if (strCalculateFlag.equals("N")) {
				dblSumAmount2 += prpItemKindDto.getAmount();
			}
		}
	} %>
		tdSumAmount1.innerHTML = '<%=new DecimalFormat("#,##0.00").format(dblSumAmount1)%>';
	//tdSumAmount2.innerHTML = '<%=new DecimalFormat("#,##0.00").format(dblSumAmount2)%>';    
}
</script>
 