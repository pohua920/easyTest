<%--   
****************************************************************************
* DESC       ： 获得自负额
* AUTHOR     ：理赔组
* CREATEDATE ： 2013-03-31
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>
<%-- 引入bean类部分 --%>
<%@page import="com.sinosoft.claim.common.service.facade.PolicyService"%>
<%@page import="com.sinosoft.claim.common.vo.PolicyDto"%>
<%@page import="ins.framework.common.ServiceFactory"%>
<%@page import="com.sinosoft.claim.schema.model.PrpCitemKind"%>
<%@page import="java.util.*"%>
<script>
<%                                                              
	String strKindCode = request.getParameter("KindCode");
	String strRiskCode = request.getParameter("RiskCode");
	String strPolicyNo = request.getParameter("PolicyNo");
	String index = request.getParameter("Index");
	PolicyService policyService = (PolicyService) ServiceFactory.getService("policyService");
	String strCond = "";
	double dblValue = 0;
	try {
		PolicyDto policyDto = policyService.findByPrimaryKey(strPolicyNo);
		List<PrpCitemKind> list = policyDto.getPrpCitemKindList();
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				PrpCitemKind prpCitemKindTemp = list.get(i);
				if (prpCitemKindTemp.getKindCode().equals(strKindCode)) {
					dblValue = prpCitemKindTemp.getValue();
	
				}
			}
		}
	
	} catch (Exception e) {
		e.printStackTrace();
		out.println("window.status='没有查询到自负额';");
	}
%> 
    var tempFrame = parent.document.frames("fraInterface");
    tempFrame.fm.target="interface"; 
    tempFrame.fm.prpLlossDtoSumRest[<%= index %>].value  = "<%=dblValue%>";      
    tempFrame.fm.prpLlossDtoSumRealPay[<%= index %>].value  = "<%=dblValue*(-1)%>";
	tempFrame.calFund();
</script>
