<%--
****************************************************************************
* DESC       ：机动车辆保险赔款通知书打印页面初始化
* AUTHOR     ：caopeng
* CREATEDATE ：2005-12-09
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>

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
 	String strCompensateNo = request.getParameter("CompensateNo"); //赔款计算书号
 	String strClaimNo = ""; //赔案号
 	String strRegistNo = ""; //报案号
 	String strPolicyNo = ""; //保单号
 	String strSumPaid = ""; //
 	String strCSumPaid = ""; //
 	double dblSumPaid = 0; //
 	String strChargeCode = "";
 	String strInsuredName = ""; //被保险人
 	String strYear = "";
 	String strMonth = "";
 	String strDate = "";
 	String strComCode = ""; //业务归属机构代码 
 	String strComCName = ""; //业务归属机构中文名称
 	//代码翻译变量
 	String strCode = "";
 	String strName = "";
 	boolean isChinese = true; //中文标志

 	//对象定义部分

 	PrpLclaimDto prpLclaimDto = null; //ClaimDto对象
 	PrpLcompensateDto prpLcompensateDto = null; //CompensateDto对象
 	PrpCmainDto prpMainDto = null; //CMainDto对象

 	int index = 0;

 	//得到ClaimDto,RegistDto,CertainLossDto,PolicyDto对象
 	ClaimDto claimDto = (ClaimDto) request.getAttribute("claimDto");
 	PolicyDto policyDto = (PolicyDto) request.getAttribute("policyDto");
 	CompensateDto compensateDto = (CompensateDto) request.getAttribute("compensateDto");

 	UICodeAction uiCodeAction = new UICodeAction();
 	//得到prpLcompensateDto 对象
 	prpLcompensateDto = compensateDto.getPrpLcompensateDto();

 	//得到prpLclaimDto对象
 	prpLclaimDto = claimDto.getPrpLclaimDto();
 	if (policyDto != null) {
 		prpMainDto = policyDto.getPrpCmainDto();
 		if (prpMainDto != null) {
 			strComCode = StringConvert.encode(prpMainDto.getComCode());
 			UICompanyAction uiCompanyAction = new UICompanyAction();
 			PrpDcompanyDto prpDcompanyDto = uiCompanyAction.findByPrimaryKey(strComCode);
 			if (prpDcompanyDto != null)
 				strComCName = prpDcompanyDto.getComCName();
 		}
 	}

 	java.util.Date date = new java.util.Date();
 	strYear = String.valueOf(date.getYear() + 1900);
 	strMonth = String.valueOf(date.getMonth() + 1);
 	strDate = String.valueOf(date.getDate());
 	//得到保单号
 	strPolicyNo = prpLclaimDto.getPolicyNo();

 	strInsuredName = StringConvert.encode(prpLclaimDto.getInsuredName());
 	if (prpLcompensateDto != null) {
 		dblSumPaid = prpLcompensateDto.getSumDutyPaid();
 		strClaimNo = prpLcompensateDto.getClaimNo();
 	}
 	strSumPaid = new DecimalFormat("#,##0.00").format(dblSumPaid);
 	strCSumPaid = MoneyUtils.toChinese(dblSumPaid, prpLcompensateDto.getCurrency());
 %> 



  