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


      <table class="common" width="100%" border=0 >

    <tr class=mline>

      <td class="common" colspan="4" style="text-align:left">

        <img style="cursor:hand;" name="RiskValuatImg"  src="/undwrt/common/images/butCollapse.gif"

          name="RiskValuatImg" onclick="showPage(this,RiskValuat);">

        <s:text name='undwrt.CommonRiskValuat.riskAccess'/>
        
				</td>
          <table id="RiskValuat" border="0" style="display:none">
            <s:if test='blPrpTriskValuat != null'>
               <s:iterator value="blPrpTriskValuat" id="blPrpTriskValuat" status="index">
	              <tr class=common>
	                <input type="hidden" name="RiskValuat_Flag">
	                <input type="hidden" name="RiskValuatCode">     
	                <td><input type="title" style="width:150px" class="readonly"
	                	 readonly name="RiskValuatName" value="<s:property value="#blPrpDRiskValuat.riskValuatCName"/>">
	                </td>
	                <td>
	                  <input class="readonly" readonly style="width:600px" name="RiskValuatValue" value="<s:property value="#blPrpDRiskValuat.riskValuatValue"/>">
	                </td>
	              </tr>
               </s:iterator>
            </s:if>
            
          </table>
      </td>

    </tr>



  <%-- 初始化 --%>




