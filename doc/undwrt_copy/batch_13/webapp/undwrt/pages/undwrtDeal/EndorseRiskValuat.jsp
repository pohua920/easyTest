<%--

****************************************************************************

* DESC       ：投保单\保单的风险评估子项页面

* Author     : 项目组

* CREATEDATE ：2006-12-07

* MODIFYLIST ：   Name       Date            Reason/Contents

****************************************************************************

--%>



<%-- 引入bean类部分 --%>

<%@page import="com.sinosoft.utility.SysConfig"%>

<%@page import="java.text.*"%>

<%@page import="com.sinosoft.utiall.dbsvr.*"%>

<%@page import="com.sinosoft.utiall.blsvr.*"%>

<%@page import="com.sinosoft.prpall.blsvr.cb.*"%>

<%@page import="com.sinosoft.prpall.blsvr.tb.*"%>

<%@page import="com.sinosoft.prpall.schema.*"%>

<%@page import="com.sinosoft.utility.SysConfig"%>

<%@page import="com.sinosoft.utility.string.Str"%>

<%@page import="com.sinosoft.utiall.schema.*"%>


<%
	  BLPrpDriskValuat	blPrpDRiskValuat = new BLPrpDriskValuat();
	  BLPrpPhead blPrpPhead = new BLPrpPhead();
	  BLPrpPriskValuat blPrpPriskValuat = new BLPrpPriskValuat();	
	  BLPrpCPriskValuat blPrpCPriskValuat = new BLPrpCPriskValuat();		
	  
    String businessNo = request.getParameter("iBusinessNo");
		String strQuery = "riskcode='"+riskCode+"'";
		String policyNo = null;
		blPrpDRiskValuat.query(strQuery);	
		int iCount = blPrpDRiskValuat.getSize();
		String iWherehead = "EndorseNo='"+businessNo+"'";
		blPrpPhead.query(iWherehead);
		policyNo = blPrpPhead.getArr(0).getPolicyNo();
		String iWhere = "EndorseNo='"+businessNo+"'";
		blPrpPriskValuat.query(iWhere);	
		String iWherep = "PolicyNo='"+policyNo+"'";	
		blPrpCPriskValuat.query(iWherep);		
%>

      <table class="common" width="100%" border=0 >

    <tr class=mline>

      <td class="common" colspan="4" style="text-align:left">

        <img style="cursor:hand;" name="RiskValuatImg"  src="/undwrt/common/images/butCollapse.gif"

          name="RiskValuatImg" onclick="showPage(this,RiskValuat);">

       <s:text name="undwrt.EndorseRiskValuat.riskAccident"/>
        
				</td>
          <table id="RiskValuat" border="0" style="display:none">
          	<%
          		for(int i = 0; i < iCount;i++){
          	%>
              <tr class=common>
                <input type="hidden" name="RiskValuat_Flag">
                <input type="hidden" name="RiskValuatCode">     
                <td><input type="title" style="width:150px" class="readonly"
                	 readonly name="RiskValuatName" value="<%=blPrpDRiskValuat.getArr(i).getRiskValuatCName()%>">
                </td>
                <td>
                  <input class="readonly" readonly style="width:600px" name="RiskValuatValue">
                </td>
              </tr>
            <%}%>
            
          </table>
      </td>

    </tr>



  <%-- 初始化 --%>

<script language="javascript">
	
				<%for(int j = 0 ; j < blPrpCPriskValuat.getSize();j++)
				{
					for(int j2=0;j2<blPrpPriskValuat.getSize();j2++)
					{
						if(blPrpCPriskValuat.getArr(j).getRiskValuatCode().equals(blPrpPriskValuat.getArr(j2).getRiskValuatCode()))
						{
				%>
						fm.RiskValuatValue[<%=j%>].title="<%=blPrpPriskValuat.getArr(j2).getRiskValuatValue()%>";
				<%
					   }
					 }
				%>
	  		fm.RiskValuatValue[<%=j%>].value="<%=blPrpCPriskValuat.getArr(j).getRiskValuatValue()%>";
	  	<%}%>
</script>


