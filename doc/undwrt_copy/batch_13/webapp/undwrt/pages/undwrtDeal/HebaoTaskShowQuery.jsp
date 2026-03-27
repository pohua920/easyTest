<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/i18njs.jsp"%>
<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="java.util.*"%>
<%@ page import="com.sinosoft.undwrt.common.vo.RiskCategoryCodeVo"%>
<%@ taglib uri="/WEB-INF/undwrt-app.tld" prefix="app"%>
<!-- 滚动条样式定义 -->
<%@ include file="CommonStyle.html"%>

<html>
  <head>
  	<jsp:include page="/common/meta_css.jsp" />
	<jsp:include page="/common/meta_js.jsp" />
<%
	List riskCodeCollection = (List)request.getAttribute("riskCodeCollection");
	String[] riskCategory = new String[riskCodeCollection.size()];
	String[] riskCode = new String[riskCodeCollection.size()];
	String[] riskName = new String[riskCodeCollection.size()];
	RiskCategoryCodeVo riskCategoryCodeDto = null;
	for(int i=0; i<riskCodeCollection.size(); i++)
	{
		riskCategoryCodeDto = (RiskCategoryCodeVo)riskCodeCollection.get(i);
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
		<form name="fm" method="post" action="/undwrt/hebaoTaskDeal.do?actionType=query">
			<input type="hidden" name="HandType" value="11">
			<input type="hidden" name="EditType" value='<s:property="HandType">'>
			<table class="common" cellpadding="5" cellspacing="1" align="center">
				<tr class=listtitle>
				  <td  colspan="4"><s:text name="undwrt.HebaoTaskShowQuery.undwrtTaskQuery"/></td>
				</tr>
				<tr>
					<td class="title4"><s:text name="undwrt.HebaoTaskShowQuery.riskBigType"/>：</td>
					<td class="input4">
						<input type="hidden" name="riskCategoryTag" value="=">
						<select class="common" name="riskCategory" 
								onchange="buildRiskCodeSelect(fm.riskCategory, fm.riskCode);changeField(fm.riskCategory.value, '11');">
							<option value=""><s:text name="undwrt.HebaoTaskShowQuery.fullRisk"/></option>
							<option value="D"><s:text name="undwrt.HebaoTaskShowQuery.carRisk"/></option>
							<option value="Y"><s:text name="undwrt.HebaoTaskShowQuery.waterRisk"/></option>
							<option value="Q"><s:text name="undwrt.HebaoTaskShowQuery.unWaterRisk"/></option>
							<option value="E"><s:text name="undwrt.HebaoTaskShowQuery.accidentHealth"/></option>
						</select>
					</td>
					<td class="title4"><s:text name="undwrt.HebaoTaskShowQuery.risk"/>：</td>
					<td class="input4">
						<input type="hidden" name="riskCodeTag" value="=">
						<select class="common" name="riskCode" size="12" multiple>
						</select>
					</td>
				</tr>
				<tr>
				  <td class="title4"><s:text name="undwrt.HebaoTaskShowQuery.businessNo"/>：</td>
				  <td class="input4">
				    <select class="tag" name="businessNoTag"><%@include file="CommonStringOption.jsp"%></select>
					  <input class=query type="text" name="businessNo" MaxLength="25" onkeypress="return isInteger(this)">
					</td>
					<td class="title4"><s:text name="undwrt.HebaoTaskShowQuery.contractNo"/>：</td>
					<td class="input4">
						<select class="tag" name="contractNoTag"><%@include file="CommonStringOption.jsp"%></select>
						<input class=query type="text" name="contractNo" MaxLength="21" onkeypress="">
					</td>
				</tr>
				<tr>
					<td class="title4"><s:text name="undwrt.HebaoTaskShowQuery.organizationCode"/>：</td>
					<td class="input4">
						<select class="tag" name="comCodeTag"><%@include file="CommonStringOption.jsp"%></select>
						<input class=query type="text" name="comCode" MaxLength="10">
					</td>
					<td class="title4"><s:text name="undwrt.HebaoTaskShowQuery.whetherIncludeLowerLevel"/>：</td>
					<td class="input4">
						<s:hidden name="underling" value="N"></s:hidden>
						<input type="checkbox" name="underlingCheck" value="" onclick="underlingValue();"><s:text name="undwrt.HebaoTaskShowQuery.include"/>
					</td>
				</tr>
				<tr>
					<td class="title4"><s:text name="undwrt.HebaoTaskShowQuery.status"/>：</td>
					<td class="input4" colspan="3">
						<input type="checkbox" name="nodeStatus" value="1" checked onclick="checkNodeStatus('1');"><s:text name="undwrt.HebaoTaskShowQuery.waitDeal"/>&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="checkbox" name="nodeStatus" value="2" checked
						onclick="checkNodeStatus('2');"><s:text name="undwrt.HebaoTaskShowQuery.playingDeal"/>&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="checkbox" name="nodeStatus" value="3" checked onclick="checkNodeStatus('3');"><s:text name="undwrt.HebaoTaskShowQuery.alreadyDealNoFlow"/>&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="checkbox" name="nodeStatus" value="4" onclick="checkNodeStatus('4');"><s:text name="undwrt.HebaoTaskShowQuery.alreadyDealFlow"/>&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="checkbox" name="nodeStatus" value="0" 
						onclick="checkNodeStatus('0');"><s:text name="undwrt.HebaoTaskShowQuery.alreadyDealFinish"/>
					</td>
				</tr>
				<tr>
					<td class="title4"><s:text name="undwrt.HebaoTaskShowQuery.submitTime"/>：</td>
					<td class="input4">
						<input type="hidden" name="flowInTime1Tag" value=">=">
						<input class=small type="text" name="flowInTime1" MaxLength="10"
							    onblur="validateFlowInTime();" value='<s:property="startDate">'>
						<s:text name="undwrt.HebaoTaskShowQuery.to"/>
						<input type="hidden" name="flowInTime2Tag" value="<=">
						<input class=small type="text" name="flowInTime2" MaxLength="10"
							    onblur="validateFlowInTime();" value='<s:property="todayDate">'>
					</td>
					<td class="title4"></td>
					<td class="input4"></td>
				</tr>
				<tr id="licenseNoId" style="display:none">
					<td class="title4"><s:text name="undwrt.HebaoTaskShowQuery.carNo"/>：</td>
					<td class="input4">
						<select class="tag" name="licenseNoTag"><%@include file="CommonStringOption.jsp"%></select>
						<input class=query type="text" name="licenseNo">
					</td>
					<td class="title4"></td>
					<td class="input4"></td>
				</tr>
				<tr id="identifyId" style="display:none">
					<td class="title4"><s:text name="undwrt.HebaoTaskShowQuery.paperType"/>：</td>
					<td class="input4">
						<input type="hidden" name="identifyTypeTag" value="=">
							
						<!-- 应该没改好,参照下面注释 -->					
						<s:select cssClass="common" list="identifyTypeList"  name="wfLogDto" listKey="identifyType" listValue="value" label="label">
						</s:select>						
					
					<!-- <html:select name="wfLogDto" property="identifyType" styleClass="common">
							<html:options collection="identifyTypeList" property="value" labelProperty="label"/>
						</html:select>
					-->
					</td>
					<td class="title4"><s:text name="undwrt.HebaoTaskShowQuery.paperNo"/>：</td>
					<td class="input4">
						<select class="tag" name="identifyNumberTag"><%@include file="CommonStringOption.jsp"%></select>
						<input class=query type="text" name="identifyNumber">
					</td>
				</tr>
				<tr id="contractId" style="display:none">
					<td class="title4"><s:text name="undwrt.HebaoTaskShowQuery.whetherOrderSchemeDownPolicy"/>：</td>
					<td class="input4">
						<input type="hidden" name="relateContractNoYesNoTag" value="=">
						<input type="checkbox" name="relateContractNoYesNo" value="Yes"><s:text name="undwrt.HebaoTaskShowQuery.yes"/>&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="checkbox" name="relateContractNoYesNo" value="No"><s:text name="undwrt.HebaoTaskShowQuery.no"/>
					</td>
					<td class="title4"><s:text name="undwrt.HebaoTaskShowQuery.orderSchemeNo"/>：</td>
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
					<Input class="button" name="buttonSubmit" type="button" value="<s:text name='prompt.query'/>" onclick="validateForm();" >
		   　　 </td>
			  </tr>
			</table>
		</form>
	</body>
</html>
<script language="javascript">
	var riskCodeCount = <%=riskCodeCollection.size()%>;
	var riskCodes = new Array();
	<%for(int i=0; i<riskCodeCollection.size(); i++){%>
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
</script>