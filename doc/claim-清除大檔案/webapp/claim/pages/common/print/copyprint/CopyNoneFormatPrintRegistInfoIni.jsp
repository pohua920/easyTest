<%@ page pageEncoding="GBK" %>
<%--
****************************************************************************
* DESC       ：出险後抄单打印页初始化
* AUTHOR     ： 项目组
* CREATEDATE ：2005-9-15
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>

<%-- 引入bean类部分 --%>
<%@page import="java.text.*"%>
<%@page import="com.sinosoft.claim.ui.control.action.*"%>
<%@page import="com.sinosoft.claim.dto.custom.*"%>
<%@page import="com.sinosoft.claim.dto.domain.*"%>
<%@page import="com.sinosoft.claim.util.*"%>
<%@page import="java.util.*"%>
<%@page import="com.sinosoft.sysframework.common.util.*"%>
<%@page import="com.sinosoft.sysframework.common.datatype.DateTime"%>
<%@page import="com.sinosoft.sysframework.exceptionlog.*"%>
<%@page import="com.sinosoft.utiall.blsvr.BLPrpDkind"%>

<%
	String strRegistno = ""; //报案号
	Date strRegistReportDate = new DateTime(); //报案时间
	String strRregistLinkerName = ""; //联系人
	String strRegistPhoneNumber = ""; //联系电话
	DateTime strRegistDamageStartDate = new DateTime();//出险时间
	String strRegistDamageAddress = "";//出险地点
	String strRegistDamageCode = "";//出险原因
	String strRegistContext = "";//报案内容摘要
	BLPrpDkind blPrpDkind = new BLPrpDkind();
	String strSQL = "";
	Collection collection = null;
	UICodeAction uiCodeAction = new UICodeAction();
	RegistDto registDto = (RegistDto) request.getAttribute("registDto");
	PolicyDto policyDto = (PolicyDto) request.getAttribute("policyDto");
	if (registDto != null && registDto.getPrpLregistDto() != null) {
		strRegistno = registDto.getPrpLregistDto().getRegistNo();
		strRegistReportDate = registDto.getPrpLregistDto().getReportDate();
		strRregistLinkerName = registDto.getPrpLregistDto().getLinkerName();
		strRegistPhoneNumber = registDto.getPrpLregistDto().getPhoneNumber();
		strRegistDamageStartDate = registDto.getPrpLregistDto().getDamageStartDate();
		strRegistDamageAddress = registDto.getPrpLregistDto().getDamageAddress();
		strRegistDamageCode = registDto.getPrpLregistDto().getDamageName();
		Collection prpLregistTextList = registDto.getPrpLregistTextDtoList();
		StringBuffer context = new StringBuffer();
		StringBuffer callCenterInfo = new StringBuffer();
		if (prpLregistTextList != null) {
			Iterator iterator = prpLregistTextList.iterator();
			while (iterator.hasNext()) {
				PrpLregistTextDto prpLregistTextDto = (PrpLregistTextDto) iterator.next();
				if ("1".equals(prpLregistTextDto.getTextType())) {
					context.append("  ");
					context.append(prpLregistTextDto.getContext());
					context.append("\t");
				}
			}
		}
		strRegistContext = context.toString();
	}
%>
