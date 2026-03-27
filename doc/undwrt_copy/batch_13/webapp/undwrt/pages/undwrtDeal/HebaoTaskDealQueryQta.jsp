<%@ page contentType="text/html; charset=GBK" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/i18njs.jsp"%>
<%@ taglib prefix="rc" uri="http://util.one.sinosoft.com/RCDate"%>

<%@ page import="java.util.*"%>
<%@ page import="com.sinosoft.undwrt.common.vo.RiskCategoryCodeVo"%>
<%@ page import="com.sinosoft.undwrt.common.vo.NodeListVo"%>

<!-- 滚动条样式定义 -->
<%@ include file="CommonStyle.html"%>

<html>
  <head>
<%
	List riskCodeCollection = (List)request.getAttribute("riskCategoryList");
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
    <jsp:include page="/common/meta_css.jsp" />
	<jsp:include page="/common/meta_js.jsp" />
    <script src="/undwrt/common/js/WfLogQuery.js"></script>
    <script src="/undwrt/common/js/My97DatePicker/WdatePicker.js"></script>

	</head>
	<body >
		<form name="fm" method="post" action="/undwrt/undwrtDeal/hebaoTaskDeal.do?actionType=queryQta">
			<input type="hidden" name="handType" value="<s:property value='handType'/>"/>
			<input type="hidden" value="<s:property value='handType'/>"/>
			<input type="hidden" name="editType" value="<s:property value='editType'/>"/>
			<table class="common" cellpadding="5" cellspacing="1" align="center">
				<tr class=listtitle>
				  <td  colspan="4"><s:text name="undwrt.HebaoTaskDealQueryQta.auditTaskQuery"/></td>
				</tr>
				<tr style="display:none">
					<td class="title4"><s:text name="undwrt.HebaoTaskDealQueryQta.riskBigType"/>：</td>
					<td class="input4">
						<input type="hidden" name="riskCategoryTag" value="=">
						<select class="common" name="riskCategory" 
								onchange="buildRiskCodeSelect(fm.riskCategory, fm.riskCode);changeField(fm.riskCategory.value, '11');">
							<option value=""><s:text name="undwrt.HebaoTaskDealQueryQta.fullRisk"/></option>
							<option value="D" selected><s:text name="undwrt.HebaoTaskDealQueryQta.carRisk"/></option>
							<option value="Y"><s:text name="undwrt.HebaoTaskDealQueryQta.warterRisk"/></option>
							<option value="Q"><s:text name="undwrt.HebaoTaskDealQueryQta.unWarterRisk"/></option>
							<option value="E"><s:text name="undwrt.HebaoTaskDealQueryQta.accidentHealthy"/></option>
						</select>
					</td>
					<td class="title4"><s:text name="undwrt.HebaoTaskDealQueryQta.risk"/>：</td>
					<td class="input4">
						<input type="hidden" name="riskCodeTag" value="=">
						<select class="common" name="riskCode" size="12" multiple>
						</select>
					</td>
				</tr>
				<tr>
				  	<td class="title4"><s:text name="undwrt.HebaoTaskDealQueryQta.businessNo"/>：</td>
				  	<td class="input4">
				    	<select class="tag" name="businessNoTag">
				    		<%@include file="CommonStringOption.jsp"%>
				    	</select>
					  	<input class=query type="text" name="businessNo" MaxLength="25" 
					  		onkeypress="return isInteger(this)">
					</td>
					<td class="title4"><s:text name="undwrt.HebaoTaskDealQueryQta.organizationCode"/>：</td>
					<td class="input4">
						<select class="tag" name="comCodeTag"><%@include file="CommonStringOption.jsp"%></select>
						<input class=query type="text" name="comCode" MaxLength="10">
					</td>
				</tr>
				<tr style="display:none">
					<td class="title4"><s:text name="undwrt.HebaoTaskDealQueryQta.contractNo"/>：</td>
					<td class="input4">
						<select class="tag" name="contractNoTag"><%@include file="CommonStringOption.jsp"%></select>
						<input class=query type="text" name="contractNo" MaxLength="21" onkeypress="">
					</td>
					<td class="title4"><s:text name="undwrt.HebaoTaskDealQueryQta.whetherIncludeSubordinate"/>：</td>
					<td class="input4">
					</td>
				</tr>
				<tr>
					<td class="title4"><s:text name="undwrt.HebaoTaskDealQueryQta.status"/>：</td>
					<td class="input4" colspan="3">
						<input type="checkbox" name="nodeStatus" value="9" checked ><s:text name="undwrt.HebaoTaskDealQueryQta.waitAudit"/>&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="checkbox" name="nodeStatus" value="1,3" ><s:text name="undwrt.HebaoTaskDealQueryQta.auditPass"/>
					</td>
				</tr>
				<tr>
					<td class="title4"><s:text name="undwrt.HebaoTaskDealQueryQta.submitTime"/>：</td>
					<td class="input4">
						<input type="hidden" name="flowInTime1Tag" value=">=">
						<input type="hidden" name="flowInTime1" value="${startDate}">
						<input class=small type="text" name="flowInTime1Rc" MaxLength="10"
							    value="<rc:rcDate value="${startDateRc}" format="yyyy-MM-dd"/>"  onFocus="WdatePicker({dateFmt:'yyy-MM-dd'})"
							    onchange="getToRcDateValue(fm,this)"
							    >
							  <%--至 --%>  
							 <s:text name="undwrt.HebaoTaskDealQueryQta.to"/>
						<input type="hidden" name="flowInTime2Tag" value="<=">
						<input type="hidden" name="flowInTime2" value="${todayDate}">
					    <input class=small type="text" name="flowInTime2Rc" MaxLength="10"
							    value="<rc:rcDate value="${todayDateRc}" format="yyyy-MM-dd"/>"  onFocus="WdatePicker({dateFmt:'yyy-MM-dd'})"
							    onchange="getToRcDateValue(fm,this)"
							    >
					</td>
					<td class="title4"></td>
					<td class="input4"></td>
				</tr>
				<tr id="licenseNoId" style="display:none">
					<td class="title4"><s:text name="undwrt.HebaoTaskDealQueryQta.carNo"/>：</td>
					<td class="input4">
						<select class="tag" name="licenseNoTag"><%@include file="CommonStringOption.jsp"%></select>
						<input class=query type="text" name="licenseNo">
					</td>
					<td class="title4"></td>
					<td class="input4"></td>
				</tr>
				<tr id="identifyId" style="display:none">
					<td class="title4"><s:text name="undwrt.HebaoTaskDealQueryQta.papersType"/>：</td>
					<td class="input4">
						<input type="hidden" name="identifyTypeTag" value="=">
					</td>
					<td class="title4"><s:text name="undwrt.HebaoTaskDealQueryQta.papersNo"/>：</td>
					<td class="input4">
						<select class="tag" name="identifyNumberTag"><%@include file="CommonStringOption.jsp"%></select>
						<input class=query type="text" name="identifyNumber">
					</td>
				</tr>
				<tr id="contractId" style="display:none">
					<td class="title4"><s:text name="undwrt.HebaoTaskDealQueryQta.whetherIsOrderAgreementDownPolicy"/>：</td>
					<td class="input4">
						<input type="hidden" name="relateContractNoYesNoTag" value="=">
						<input type="checkbox" name="relateContractNoYesNo" value="Yes"><s:text name="undwrt.HebaoTaskDealQueryQta.yes"/>&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="checkbox" name="relateContractNoYesNo" value="No"><s:text name="undwrt.HebaoTaskDealQueryQta.no"/>
					</td>
					<td class="title4"><s:text name="undwrt.HebaoTaskDealQueryQta.orderAgreementNo"/>：</td>
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
</html>