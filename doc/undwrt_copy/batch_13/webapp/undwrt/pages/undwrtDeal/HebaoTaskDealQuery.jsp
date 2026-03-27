<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/i18njs.jsp"%>
<%@ taglib prefix="rc" uri="http://util.one.sinosoft.com/RCDate"%>

<%@ page import="java.util.*"%>
<%@ page import="com.sinosoft.undwrt.common.vo.RiskCategoryCodeVo"%>
<%@page import="com.sinosoft.undwrt.common.vo.NodeListVo"%>
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
	<jsp:include page="/common/meta_css.jsp" />
	<jsp:include page="/common/meta_js.jsp" />
    <!-- 公用函数 -->
	<script src="/undwrt/common/js/WfLogQuery.js"></script>
	<script src="/undwrt/common/js/My97DatePicker/WdatePicker.js"></script>
	</head>
	<body >
		<form name="fm" method="post" action="/undwrt/undwrtDeal/hebaoTaskDeal.do?actionType=query">
			<input type="hidden" name="handType" value="<s:property value='handType'/>"/>
			<input type="hidden"  name="editType" value="<s:property value='editType'/>"/>
			<input type="hidden"  name="BusinessTypeFlag" value="<s:property value='BusinessTypeFlag'/>"/><!-- 非车询价单添加 -->
			
			<table class="common" cellpadding="5" cellspacing="1" align="center">
				<tr class=listtitle>
					<td colspan="4">
				  		<%--核保任务查询 --%>
				  		<s:text name="undwrt.HebaoTaskDealQuery.undwrtTaskQuery"/>
				    </td>
				</tr>
				<tr>
					<td class="title4">
						<%--险种大类 --%>
						<s:text name="undwrt.HebaoTaskDealQuery.riskBigType"/>：
					</td>
					<td class="input4">
						<input type="hidden" name="riskCategoryTag" value="=">
						<s:select cssClass="common" name="riskCategory" list="prpDcode_riskCategoryList" 
							listKey="id.codeCode" listValue="id.codeCName"
							onchange="buildRiskCodeSelect(fm.riskCategory, fm.riskCode);changeField(fm.riskCategory.value, '11');">
						</s:select>
					</td>
					<td class="title4">
						<%--险种 --%>
						<s:text name="undwrt.HebaoTaskDealQuery.risk"/>：
					</td>
					<td class="input4">
						<input type="hidden" name="riskCodeTag" value="=">
						<select class="common" name="riskCode" size="12" multiple></select>
					</td>
				</tr>
				<tr>
				    <td class="title4">
				    	<s:if test='actionType=="prepareQuery"'>
				    	<%--业务号 --%>
				    	<s:text name="undwrt.HebaoTaskDealQuery.businessNo"/>：
				    	<input type="hidden" name="operateType" value="proposal">
				    	</s:if>
				    	<s:else>
				    	 <%--业务号 --%>
				    	 <s:text name="undwrt.CommonDealContentQta.quoteNo"/>：
				    	 <input type="hidden" name="operateType" value="quote">
				    	</s:else>
				    </td>
				    <td class="input4">
				      	<select class="tag" name="businessNoTag">
				      		<%@include file="CommonStringOption.jsp"%>
				      	</select>
					  	<input class=query type="text" name="businessNo" MaxLength="25" onkeypress="return isInteger(this)">
					</td>
					<td class="title4">
						<%--合同号 --%>
						<s:text name="agentManage.agreementNo"/>：
					</td>
					<td class="input4">
						
						<select class="tag" name="contractNoTag">
							<%@include file="CommonStringOption.jsp"%>
						</select>
						<input class=query type="text" name="contractNo" MaxLength="21" onkeypress="">
					</td>
				</tr>
				<tr>
					<td class="title4">
							<%--机构代码 --%>
							<s:text name="undwrt.HebaoTaskDealQuery.organizationCode"/>：
					</td>
					<td class="input4">
						<select class="tag" name="comCodeTag">
							<%@include file="CommonStringOption.jsp"%>
						</select>
						<input class=query type="text" name="comCode" MaxLength="10">
					</td>
					<td class="title4">
							<%--是否包含下级 --%>
							<s:text name="undwrt.HebaoTaskDealQuery.whetherIncludeSubordinate"/>：
					</td>
					<td class="input4">
						<input type="hidden" name="underling" value="N">
						<input type="hidden" name="underlingCheck" value="" onclick="underlingValue();">
						
						<select name="selectUnderling">	
							<s:iterator value="nodeList">
								<option value="<s:property value="nodeNo"/>">
									<s:property value="nodeName"/>
								</option>
							</s:iterator>
						</select>
					</td>
				</tr>
				<tr>
					<td class="title4">
				        <%--保单号 --%>
				  		<s:text name="undwrt.HebaoTaskDealQuery.policyNo"/>：
				    </td>
					<td class="input4">
				 		<select class="tag" name="policynoTag">
				    		<%@include file="CommonStringOption.jsp"%>
				   		</select>
						<input class=query type="text" name="policyno" MaxLength="25" onkeypress="return isInteger(this)">
					</td>
					<!-- add by wangcan 2015/11/26 增加出单员查询条件 -->
					<td class="title4">
				        <%--出單員 --%>
				  		<s:text name="undwrt.pages.undwrtDeal.issuingStaff"/>：
				    </td>
				   <%--  <td class="input4">
				 		<select class="tag" name="singleMemberTag">
				    		<%@include file="CommonStringOption.jsp"%>
				   		</select>
						<input class=query type="text" name="singleMember" MaxLength="25" onkeypress="return isInteger(this)">
					</td> --%>
					<!-- add by wangcan 2015/12/16 出单员查询，查询内容为出单员代码 -->
					 <td class="input4">
				 		<select class="tag" name="singleCodeTag">
				    		<%@include file="CommonStringOption.jsp"%>
				   		</select>
						<input class=query type="text" name="singleCode" MaxLength="25" onkeypress="return isInteger(this)">
					</td>
				</tr>
				<tr>
					 <td class="title4">
					 		<%--状态 --%>
							<s:text name="undwrt.HebaoTaskDealQuery.status"/>：
					 </td>
					 <td class="input4" colspan="3">
						<input type="checkbox" name="nodeStatus" value="1" checked >
							<%--待处理 --%>
							<s:text name="undwrt.HebaoTaskDealQuery.waitDeal"/>&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="checkbox" name="nodeStatus" value="2" checked >
							<%--正在处理 --%>
							<s:text name="undwrt.HebaoTaskDealQuery.playingDeal"/>&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="checkbox" name="nodeStatus" value="4" >
							<%--已处理流转 --%>
							<s:text name="undwrt.HebaoTaskDealQuery.alreadyDealFlow"/>&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="checkbox" name="nodeStatus" value="0" >
							<%--已处理完毕 --%>
							<s:text name="undwrt.HebaoTaskDealQuery.alreadyFinish"/>
					 </td>
				</tr>
				<tr>
					<td class="title4">
						<%--提交时间--%>
					    <s:text name="undwrt.HebaoTaskDealQuery.submitTime"/>：
					</td>
					<td class="input4">
						<input type="hidden" name="flowInTime1" value="${startDate}">
						<input class=small type="text" name="flowInTime1Rc" value="<rc:rcDate value="${startDateRc}" format="yyyy-MM-dd"/>"  
							onFocus="WdatePicker({dateFmt:'yyy-MM-dd'})" onchange="getToRcDateValue(fm,this)">
						<%--至 --%>
						<s:text name="undwrt.HebaoTaskDealQuery.to"/>
						<input type="hidden" name="flowInTime2" value="${todayDate}">
					    <input class=small type="text" name="flowInTime2Rc" value="<rc:rcDate value="${todayDateRc}" format="yyyy-MM-dd"/>"  
					    	onFocus="WdatePicker({dateFmt:'yyy-MM-dd'})" onchange="getToRcDateValue(fm,this)">
					</td>
					<td class="input4" colspan="2"></td>
				</tr>
				<tr id="licenseNoId" style="display:none">
					<td class="title4">
						<%--车牌号 --%>
					    <s:text name="undwrt.HebaoTaskDealQuery.carNo"/>：
					</td>
					<td class="input4">
						<select class="tag" name="licenseNoTag">
							<%@include file="CommonStringOption.jsp"%>
						</select>
						<input class=query type="text" name="licenseNo">
					</td>
					<td class="input4" colspan="2"></td>
				</tr>
				<tr id="identifyId" style="display:none">
					<td class="title4">
						<%--证件类型 --%>
						<s:text name="undwrt.HebaoTaskDealQuery.papersType"/>：
					</td>
					<td class="input4">
						<input type="hidden" name="identifyTypeTag" value="=">
					</td>
					<td class="title4">
						<%--证件号 --%>
						<s:text name="undwrt.HebaoTaskDealQuery.papersNo"/>：
					</td>
					<td class="input4">
						<select class="tag" name="identifyNumberTag">
							<%@include file="CommonStringOption.jsp"%>
						</select>
						<input class=query type="text" name="identifyNumber">
					</td>
				</tr>
				<tr id="contractId" style="display:none">
					<td class="title4">
						<%--是否为预约协议项下挂的保单 --%>
						<s:text name="undwrt.HebaoTaskDealQuery.whetherIsOrderAgreementDownPolicy"/>：
					</td>
					<td class="input4">
						<input type="hidden" name="relateContractNoYesNoTag" value="=">
						<input type="checkbox" name="relateContractNoYesNo" value="Yes">
							<%--是 --%>
							<s:text name="undwrt.HebaoTaskDealQuery.yes"/>&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="checkbox" name="relateContractNoYesNo" value="No">
							<%--否 --%>
							<s:text name="undwrt.HebaoTaskDealQuery.no"/>
					</td>
					<td class="title4">
						<%--预约协议号 --%>
						<s:text name="undwrt.HebaoTaskDealQuery.orderAgreementNo"/>：
					</td>
					<td class="input4">
						<select class="tag" name="relateContractNoTag">
							<%@include file="CommonStringOption.jsp"%>
						</select>
						<input class=query type="text" name="relateContractNo">
					</td>
				</tr>
			</table>
				
			<table class="two">
				<tr>
					<td align="center">
						<Input class="button" name="buttonSubmit" type="button" value="<s:text name='prompt.query'/>" onclick="validateForm();" >
		   　　 		</td>
			  	</tr>
			</table>
		</form>
		
		<script type="text/javascript">
			var riskCodeCount = <%=riskCodeCollection.size()%>;
			var riskCodes = new Array();
			<%for(int i=0; i<riskCodeCollection.size(); i++){%>
				riskCodes[<%=i%>] = new Array("<%=riskCategory[i]%>", "<%=riskCode[i]%>", "<%=riskName[i]%>");
			<%}%>
			
			function underlingValue()
			{
				if(fm.underling.value == "Y"){
					fm.underling.value = "N";
				} else if(fm.underling.value == "N"){
					fm.underling.value = "Y";
				}
			}
			/*
			mantis： CAR0123，處理人員：Sam，需求單編號：CAR0123--- start
			延續原CAR0107議題,新增關聯單卡控條件
			*/
			window.onload = function() {
				var rBusinessNo = "<s:property value='#session.relevUndwrtBusiNo'/>";
				if(rBusinessNo != null && rBusinessNo != ""){
					fm.businessNo.value = rBusinessNo;
					validateForm();
				}
			};
			/* mantis： CAR0123，處理人員：Sam，需求單編號：CAR0123 --- end */
		</script>
	</body>
</html>