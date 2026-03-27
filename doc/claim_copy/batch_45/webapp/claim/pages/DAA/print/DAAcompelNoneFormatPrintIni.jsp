<%--
****************************************************************************
* DESC       ：事故责任强制保险拒赔通知书打印
* AUTHOR     ：zhouliu
* CREATEDATE ：2005-12-08
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
<%@page import="java.util.*"%>
<%
	//变量声明部分
	String strPolicyNo = ""; //保单号
	String strInsuredName = ""; //被保险人
	String strLicenseNo = "";//被保险人牌照号码
	String strDamageAddress = "";//出险地点
	DateTime damageDate = null;//出险时间
	Date curdate = new Date();

	//对象申明部分
	PrpLregistDto prpLregistDto = null; //RegistDto对象

	//得到指定对象
	RegistDto registDto = (RegistDto) request.getAttribute("registDto");

	//得到prpLregistDto对象
	prpLregistDto = registDto.getPrpLregistDto();

	//给参数赋值
	strPolicyNo = prpLregistDto.getPolicyNo();
	strInsuredName = prpLregistDto.getInsuredName();
	strLicenseNo = prpLregistDto.getLicenseNo();
	strDamageAddress = prpLregistDto.getDamageAddress();
	damageDate = prpLregistDto.getDamageStartDate();
%>