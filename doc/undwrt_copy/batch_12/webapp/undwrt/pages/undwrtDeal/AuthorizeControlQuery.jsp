<!--***************************************************************************
* Description: 授权查询页面
* Author     : yishengcheng
* CreateDate : 2011-11-02

****************************************************************************-->
<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="java.util.*"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/i18njs.jsp"%>
<%@ page import="com.sinosoft.undwrt.common.vo.RiskCategoryCodeVo"%>
<%@ page import="com.sinosoft.undwrt.common.vo.NodeListVo"%>
<%@ taglib uri="/WEB-INF/undwrt-app.tld" prefix="app"%>
<!-- 滚动条样式定义 -->
<%@ include file="CommonStyle.html"%>

<html>
  <head>
	<jsp:include page="/common/meta_css.jsp" />
	<jsp:include page="/common/meta_js.jsp" />
<%
	List riskCodeCollection = (List) request.getAttribute("riskCategoryList");
	//added by LanNing end 20080407 核保修改查看下级
	String[] riskCategory = new String[riskCodeCollection.size()];
	String[] riskCode = new String[riskCodeCollection.size()];
	String[] riskName = new String[riskCodeCollection.size()];
	RiskCategoryCodeVo riskCategoryCodeDto = null;
	for (int i = 0; i < riskCodeCollection.size(); i++) {
		riskCategoryCodeDto = (RiskCategoryCodeVo) riskCodeCollection.get(i);
		riskCategory[i] = riskCategoryCodeDto.getRiskCategory();
		riskCode[i] = riskCategoryCodeDto.getRiskCode();
		riskName[i] = riskCategoryCodeDto.getRiskName();
	}
%>
    <!-- 公用函数 -->
    <script src="/undwrt/common/js/Common.js"></script>
	<script src="/undwrt/common/js/WfLogQuery.js"></script>
	</head>
	<body >
		<form name="fm" method="post" action="/undwrt/undwrtDeal/hebaoTaskDeal.do?actionType=queryAuthorizeControl">
			<input type="hidden" name="handType" value="11">
			<input type="hidden" name="editType" value="<s:property value="editType"/>">
			<table class="common" cellpadding="5" cellspacing="1" align="center">
				<tr class=listtitle>
				  <td  colspan="4"><s:text name="undwrt.pages.undwrtDeal.AuthorizeControlQuery"/></td>
				</tr>
				<tr>
					<td class="title4"><s:text name="undwrt.pages.undwrtDeal.classCodeCName"/>：</td>
					<td class="input4">
						<input type="hidden" name="riskCategoryTag" value="=">
						<select class="common" name="riskCategory" 
								onchange="buildRiskCodeSelect(fm.riskCategory, fm.riskCode);changeField(fm.riskCategory.value, '11');">
							<option value=""><s:text name="undwrt.pages.undwrtDeal.riskName"/></option>
							<option value="D"><s:text name="undwrt.pages.undwrtDeal.carInsurance"/></option>
							<option value="Y"><s:text name="undwrt.pages.undwrtDeal.marineRisk"/></option>
							<option value="Q"><s:text name="undwrt.pages.undwrtDeal.noMarineRisk"/></option>
							<option value="E"><s:text name="undwrt.pages.undwrtDeal.accidentHealthInsurance"/></option>
						</select>
					</td>
					<td class="title4"><s:text name="riskName"/>：</td>
					<td class="input4">
						<input type="hidden" name="riskCodeTag" value="=">
						<select class="common" name="riskCode" size="12" multiple>
						</select>
					</td>
				</tr>
				<tr>
				  <td class="title4"><s:text name="undwrt.pages.undwrtDeal.certiNo"/>：</td>
				  <td class="input4">
				    <select class="tag" name="businessNoTag"><%@include file="CommonStringOption.jsp"%></select>
					  <input class=query type="text" name="businessNo" MaxLength="25" onkeypress="return isInteger(this)">
					</td>
					<td class="title4"><s:text name="undwrt.pages.undwrtDeal.contractNo"/>：</td>
					<td class="input4">
						<select class="tag" name="contractNoTag"><%@include file="CommonStringOption.jsp"%></select>
						<input class=query type="text" name="contractNo" MaxLength="21" onkeypress="">
					</td>
				</tr>
				<tr>
					<td class="title4"><s:text name="comCode"/>：</td>
					<td class="input4">
						<select class="tag" name="comCodeTag"><%@include file="CommonStringOption.jsp"%></select>
						<input class=query type="text" name="comCode" MaxLength="10">
					</td>
					<td class="title4"><s:text name="undwrt.pages.undwrtDeal.whetherSubordinate"/>：</td>
					<td class="input4">
						<input type="hidden" name="underling" value="N">
						<input type="hidden" name="underlingCheck" value="" onclick="underlingValue();">								
						<select name="selectUnderling">	
							<s:iterator value="nodeList">
								<option value="<s:property value="nodeNo"/>"><s:property value="nodeName"/></option>
							</s:iterator>
						</select>
					</td>
				</tr>
				
				<!-- add by fushixing start -->
				<tr>
				  <td class="title4"><s:text name="policyManage.policyNo"/>：</td>
				  <td class="input4">
				    <select class="tag" name="policynoTag"><%@include file="CommonStringOption.jsp"%></select>
					  <input class=query type="text" name="policyno" MaxLength="25" onkeypress="return isInteger(this)">
				</tr>
				<tr>
					<td class="title4"><s:text name="undwrt.pages.undwrtDeal.submitTime"/>：</td>
					<td class="input4">
						<input type="hidden" name="flowInTime1Tag" value=">=">
						<input class=small type="text" name="flowInTime1" MaxLength="10"
							    onblur="validateFlowInTime();" value="<s:property value="startDate"/>">
						<s:text name="prompt.to"/>
						<input type="hidden" name="flowInTime2Tag" value="<=">
						<input class=small type="text" name="flowInTime2" MaxLength="10"
							    onblur="validateFlowInTime();" value="<s:property value="todayDate"/>">
					</td>
					<td class="title4"></td>
					<td class="input4"></td>
				</tr>
				<tr id="licenseNoId" style="display:none">
					<td class="title4"><s:text name="undwrt.pages.undwrtDeal.licenseNo"/>：</td>
					<td class="input4">
						<select class="tag" name="licenseNoTag"><%@include file="CommonStringOption.jsp"%></select>
						<input class=query type="text" name="licenseNo">
					</td>
					<td class="title4"></td>
					<td class="input4"></td>
				</tr>
				<tr id="identifyId" style="display:none">
					<td class="title4"><s:text name="undwrt.pages.undwrtDeal.papersType"/>：</td>
					<td class="input4">
						<input type="hidden" name="identifyTypeTag" value="=">
						
						<s:select list="identifyTypeList" name="wfLogDto" listKey="identifyType" listValue="value" label="label" cssClass="common">
                        </s:select>
						
						<!-- <html:select name="wfLogDto" property="identifyType" styleClass="common">
							<html:options collection="identifyTypeList" property="value" labelProperty="label"/>
						</html:select>
						 -->
					</td>
					<td class="title4"><s:text name="undwrt.pages.undwrtDeal.papersNo"/>：</td>
					<td class="input4">
						<select class="tag" name="identifyNumberTag"><%@include file="CommonStringOption.jsp"%></select>
						<input class=query type="text" name="identifyNumber">
					</td>
				</tr>
				<tr id="contractId" style="display:none">
					<td class="title4"><s:text name="undwrt.pages.undwrtDeal.whetherUnderPolicy"/>：</td>
					<td class="input4">
						<input type="hidden" name="relateContractNoYesNoTag" value="=">
						<input type="checkbox" name="relateContractNoYesNo" value="Yes"><s:text name="undwrt.yes"/>&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="checkbox" name="relateContractNoYesNo" value="No"><s:text name="undwrt.no"/>
					</td>
					<td class="title4"><s:text name="undwrt.pages.undwrtDeal.contractNo"/>：</td>
					<td class="input4">
						<select class="tag" name="relateContractNoTag"><%@include file="CommonStringOption.jsp"%></select>
						<input class=query type="text" name="relateContractNo">
					</td>
				</tr>
			</table>
			&nbsp;
			<table class=two>
			  <tr>
				<td align=center>
					<Input class="button" name="buttonSubmit" type="button" value="<s:text name='prompt.query'/>" onclick="validateForm1();" >
		   　　 </td>
			  </tr>
			</table>
		</form>
			<script language="javascript">
			var riskCodeCount = <%=riskCodeCollection.size()%>;
			var riskCodes = new Array(riskCodeCount);
			<%for (int i = 0; i < riskCodeCollection.size(); i++) {%>
				riskCodes[<%=i%>] = new Array("<%=riskCategory[i]%>", "<%=riskCode[i]%>", "<%=riskName[i]%>");
			<%}%>
	function underlingValue()
				{
						if(fm.underling.value == "Y")
					  {
							fm.underling.value = "N";
						}
						else if(fm.underling.value == "N")
					  {
							fm.underling.value = "Y";
						}
				}
				function validateForm1()
				{   
				if(fm.editType.value == "deal"||fm.editType.value == "queryStats")
				{
					//if(nodeStatusObj.item(0).checked == false && nodeStatusObj.item(1).checked == false &&
					//   nodeStatusObj.item(2).checked == false && nodeStatusObj.item(3).checked == false &&
					//   nodeStatusObj.item(4).checked == false && nodeStatusObj.item(5).checked == false)
					fm.buttonSubmit.disabled = true;
					fm.submit();
				}else{
					alert("<s:text name='undwrt.pages.undwrtDeal.noTssk'/>");
				}
			}
</script>		
	</body>
</html>

