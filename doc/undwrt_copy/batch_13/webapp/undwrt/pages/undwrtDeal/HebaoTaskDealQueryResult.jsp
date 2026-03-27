<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/i18njs.jsp"%>
<%@ taglib uri="/WEB-INF/undwrt-app.tld" prefix="app"%>
<%@ page import="com.sinosoft.sysframework.reference.AppConfig"%>
<%@ taglib prefix="rc" uri="http://util.one.sinosoft.com/RCDate"%>
<%@page import="java.util.*"%>
<%@page import="java.lang.*"%>
<html>	
		<head>
		<!--通用任务处理函数-->
		<script src="/undwrt/common/js/CommonTaskDeal.js"></script>
		<script src="/undwrt/common/js/WfLogQuery.js"></script>
		<script src="/undwrt/common/js/My97DatePicker/WdatePicker.js"></script>
		<jsp:include page="/common/meta_css.jsp" />
		<jsp:include page="/common/meta_js.jsp" />
		<!-- 需求150 回調函數導入   -->
		<script language="javascript" src="/undwrt/e3/tree/yui/build/yahoo-dom-event/yahoo-dom-event.js"></script>
		<script type='text/javascript' src="/undwrt/widgets/yui/connection/connection-min.js"></script>
		<script type='text/javascript' src="/undwrt/widgets/yui2/json/json-min.js"></script>
		<script type='text/javascript' src="/undwrt/widgets/yui/element/element-beta-min.js"></script>
		<!-- mantis： CAR0369，處理人員：BI086，需求單編號：CAR0369:核心車險地址正規化作業 START -->
		<script type='text/javascript' src="/undwrt/common/dwr/engine-min.js"></script> 
		<script type='text/javascript' src="/undwrt/common/dwr/util-min.js"></script> 
		<script type='text/javascript' src="/undwrt/common/dwr/engine.js"></script>
		<script type='text/javascript' src="/undwrt/common/dwr/util.js"></script>
		<script type="text/javascript" src="/undwrt/common/js/json2.js" ></script>
		<script type='text/javascript' src='/undwrt/dwr/interface/DwrUtilService.js'></script>
		<!-- mantis： CAR0369，處理人員：BI086，需求單編號：CAR0369:核心車險地址正規化作業 END -->
        <!-- //add by xuhuilign -->
        <%
        	String valueType  = (String)session.getAttribute("valueType");
        %>
        <!-- add by xuhuiling -->
        </head>
  <body >

  <form name="fm" action="${ctx}/undwrtDeal/hebaoTaskDeal.do?actionType=queryContinue">
	<s:hidden name="pageNo" ></s:hidden>
	<s:hidden name="rowsCount" ></s:hidden>
	<s:hidden name="rowsPerPage" ></s:hidden>
	<input type="hidden" name="actionType" value="queryContinue"/>
	<input type="hidden" name="riskCategory" value='<s:property value='riskCategory'/>'>
	  	<%
  	String[] nodeStatus=(String[])session.getAttribute("nodeStatusList");
  	for(int i=0;i<nodeStatus.length;i++){
  	%>
  	<input type="hidden" name="nodeStatus" value="<%=nodeStatus[i]%>" checked>
  	<%
  }  	
  	%>
	<input type="hidden" name="HandType" value="11">
	<input type="hidden" name="handType" value="11">
	<input type="hidden" name="EditType" value="deal">
	<input type="hidden" name="operateType" value="<s:property value="operateType" />">
		&nbsp;&nbsp;<font color="#D82626"><s:text name="undwrt.HebaoTaskDealQueryResult.hitContractNoBatchDownSendAlterDeal"/>
  <table class="common" cellpadding="5" cellspacing="1" align="center">
    <tr>
    	<td align="left" colspan="11">
    		<font color="red">
    		<s:if test="BatchSubmitSuperior != null">
    		   <s:property value="BatchSubmitSuperior" />
    		</s:if>
<!--     		<logic:present name="BatchSubmitSuperior"><bean:write name="BatchSubmitSuperior"/></logic:present> -->
    		</font>
    	</td>
    </tr>
    <tr class="listtitle">
	    <td colspan="20">
	    	 <%--核保任务查询结果 --%>
	   		 <b><s:text name="undwrt.HebaoTaskDealQueryResult.undwrtTaskQueryResult"/></b>
	    </td>
	</tr>
	<tr class=listtitle>
		<td>
		    <s:if test='EditType != "query"'>
    		   	<input type="checkbox" name="selectButton" value="v" 
					   onpropertychange="boundCheckBox(this, fm.checkboxSelect);">
    		</s:if>
    	    <s:if test='EditType == "query"'>
    		   	<input type="checkbox" name="selectButton" value="v" disabled>
    		</s:if>
		</td>
		<td>
			<%--业务号 --%>
			<s:text name="undwrt.HebaoTaskDealQueryResult.businessNo"/>
		</td>
		<!-- delete by wangcan 2015/11/26 去掉协议号 -->
		<%-- <td>
			合同号
			<s:text name="undwrt.HebaoTaskDealQueryResult.contractNo"/>
		</td> --%>
		<td>
			<s:property value="showColumnName" />
		</td>
			<td>
			<%--投保人名称 --%>
			<s:text name="undwrt.HebaoTaskDealQueryResult.appliName"/>
		</td>
		<td>
			<%--被保险人名称 --%>
			<s:text name="undwrt.HebaoTaskDealQueryResult.insuredName"/>
		</td>
		<td>
			<%--险种 --%>
			<s:text name="undwrt.HebaoTaskDealQueryResult.risk"/>
		</td>
		<!-- add by wangcan 2015/11/26 增加保费 -->
		<td>
			<%--保費 --%>
			<s:text name="undwrt.EndorseDangerUnits.policyFee"/>
		</td>
		<td>
			<%--归属机构 --%>
			<s:text name="undwrt.HebaoTaskDealQueryResult.belongOrganization"/>
		</td>
		<td>
			<%--提交时间 --%>
			<s:text name="undwrt.HebaoTaskDealQueryResult.submitTime"/>
		</td>
		<td>
			<%--级别 --%>
			<s:text name="undwrt.HebaoTaskDealQueryResult.level"/>
		</td>
		<td>
			<%--状态 --%>
			<s:text name="undwrt.HebaoTaskDealQueryResult.status"/>
		</td>
		<td>
			<%--提交人 --%>
			<s:text name="undwrt.HebaoTaskDealQueryResult.submiter"/>
		</td>
		<td>
			<%--出单员 --%>
			<s:text name="undwrt.HebaoTaskDealQueryResult.outBiller"/>
		</td>
		<td>
			<%--服務人員 --%>
			<s:text name="undwrt.HebaoTaskDealQueryResult.handler1Name"/>
		</td>
		<!-- add by xuhuiling 需求150 20160818 begin -->
		<td>
			<%--拒限保--%>
			<s:text name="undwrt.refuseLimiteInsurance"/>
		</td>
		<td>
			<%--名單檢測 --%>
			<s:text name="undwrt.listDetection"/>
		</td>
		<td>
			<%--風險評級 --%>
			<s:text name="undwrt.riskRating"/>
		</td>
		<td>
			<%--作業狀態 --%>
			<s:text name="undwrt.workStatus"/>
		</td>
		<td>
			<%--查看風險 --%>
			<s:text name="undwrt.button.queryWorkStatus"/>
		</td>
		<!-- add by xuhuiling 需求150 20160818 end -->
		<!--mantis： CAR0369，處理人員：BI086，需求單編號：CAR0369:核心車險地址正規化作業 start -->
		<td nowrap="nowrap">
			<s:text name="undwrt.address.verify"/>
		</td>
		<!--mantis： CAR0369，處理人員：BI086，需求單編號：CAR0369:核心車險地址正規化作業 end -->
		<!-- add by songxin 需求：增加繳費資料輸入 start -->
		<td>
		    <s:text name="繳費資料輸入"></s:text>
		</td>
		<!-- add by songxin 需求：增加繳費資料輸入 start -->
	</tr>

   <s:if test="undwrtTaskList != null">
	   <s:iterator value="undwrtTaskList" status="statu" id="WflogList">
	      <tr class=common>
	        <td>
				<s:if test='EditType != "query"'>
					<input type="checkbox" name="checkboxSelect" value=<s:property value='#statu.index'/> onclick="checkRelBusiness(<s:property value='#statu.index'/>);">
				</s:if>
				<s:if test='EditType == "query"'>
					<input type="checkbox" name="checkboxSelect" value=<s:property value='#statu.index'/> disabled>
				</s:if>
			</td>
			<td>
	          <a class="check" href="#" onclick="checkTask(<s:property value='#statu.index'/>);">
				<s:property value="#WflogList.businessNo"/>
				
			  </a>
			 
	        </td>
	        <!-- delete by wangcan 2015/11/26 去掉协议号 -->
			<%-- <%
			  //批量核保开关
			  if(AppConfig.get("sysconst.BATCHTASK")!=null && AppConfig.get("sysconst.BATCHTASK").equals("1")){
			%>        
			<td>
				<a class="check" href="#" onclick="checkBatchTask(<s:property value='#statu.index'/>)" ><s:property value="#WflogList.contractNo"/></a>
			</td>
			<%}else{%>
			<td>
				<s:property value="#WflogList.contractNo"/>
			</td> 
			<%}%> --%>
			<td>
				<s:if test='riskCategory == "D"'>
				    <s:property value="#WflogList.licenseNo"/>
				</s:if>
				<s:if test='riskCategory == "Y"'>
				    <s:property value="#WflogList.relateContractNo"/>
				</s:if>
	            <s:if test='riskCategory == "E"'>
					<s:property value="#WflogList.identifyNumber"/>
				</s:if>	
	            <s:if test='riskCategory == "Q"'>
	                &nbsp;
	            </s:if>
	            <s:if test='riskCategory == ""'>
	                &nbsp;
	            </s:if>
			</td>
			<td>
	        	<s:property value="#WflogList.appliName"/>
	        </td>
	        <td>
	        	<s:property value="#WflogList.insuredName"/>
	        </td>
	        <td>
	        	<s:property value="#WflogList.riskCode"/>
	        </td>
	        <!-- add by wangcan 2015/11/26 增加保费 -->
	        <td> 
	        	<s:property value="#WflogList.premium"/>
	        </td>
	        <td>
	        	<s:property value="#WflogList.comCode"/>
	        </td>
	        <td>
					 <input type="hidden" name="flowInTime" value="#WflogList.flowInTime">
					 <!-- // add by wangcan2015/11/26  暂时增加空行，升级时重新替换为当前的值（原来显示有问题） -->
					  <rc:rcDate name = "#WflogList.flowInTime" format="yyyy-MM-dd"/>
							   				           	
	        </td>
	        <td>
	        	<s:property value="#WflogList.nodeName"/>
	        </td>
	   <!--  added by wangjun20130116 --> 
	        <td>
	        <s:if test="#WflogList.nodeStatus== 1">
                                           <s:text name="undwrt.HebaoTaskDealQueryResult.waitDeal"/>
            </s:if>
            <s:if test="#WflogList.nodeStatus == 2">
                                           <s:text name="undwrt.HebaoTaskDealQueryResult.playingDeal"/>
            </s:if>
            <s:if test="#WflogList.nodeStatus == 3">
                                           <s:text name="undwrt.HebaoTaskDealQueryResult.alreadyDeadyNoSubmit"/>
            </s:if>
            <s:if test="#WflogList.nodeStatus == 4">
                                           <s:text name="undwrt.HebaoTaskDealQuery.alreadyDealFlow"/>
            </s:if>
            <s:if test="#WflogList.nodeStatus == 0">
                                           <s:text name="undwrt.HebaoTaskDealQuery.alreadyFinish"/>
            </s:if>
	        </td>
	        <td><s:property value="#WflogList.operatorName"/></td>
			<!-- 增加出单员 -->
			<td><s:property value="#WflogList.singleMember"/></td>
			<!-- 增加服務人員 -->
			<td><s:property value="#WflogList.handler1Name"/></td>
			<!-- add by xuhuiling 需求150 20160818 begin -->
			
			<!-- 拒限保 -->
			<td>
			   <s:if test="#WflogList.refuseLimiteInsurance=='00'"><s:text name="undwrt.notInTarget"/></s:if>
			   <s:if test="#WflogList.refuseLimiteInsurance=='01'"><s:text name="undwrt.InTargetNotSelected"/></s:if>
			   <s:if test="#WflogList.refuseLimiteInsurance=='02'"><s:text name="undwrt.InTargetAndTrue"/></s:if>
			   <s:if test="#WflogList.refuseLimiteInsurance=='03'"><s:text name="undwrt.InTargetAndFalse"/></s:if>
            </td>
			<!-- 名單檢測 -->
			<td>
			    <s:if test="#WflogList.listDetection=='01'"><s:text name="undwrt.notInTarget"/></s:if>
			    <s:if test="#WflogList.listDetection=='02'"><s:text name="undwrt.InTargetNotSelected"/></s:if>
			    <s:if test="#WflogList.listDetection=='03'"><s:text name="undwrt.InTargetAndSelected"/></s:if>
			</td>
			<!-- 風險評級 -->
			<td><s:if test="#WflogList.riskRating=='00'"><s:text name="undwrt.riskRating.highNotDeal"/></s:if>
			    <s:if test="#WflogList.riskRating=='01'"><s:text name="undwrt.riskRating.highAndDeal"/></s:if>
			    <s:if test="#WflogList.riskRating=='02'"><s:text name="undwrt.riskRating.middleNotDeal"/></s:if>
			    <s:if test="#WflogList.riskRating=='03'"><s:text name="undwrt.riskRating.middleAndDeal"/></s:if>
			    <s:if test="#WflogList.riskRating=='04'"><s:text name="undwrt.riskRating.lowerRisk"/></s:if>
			</td>
			<!-- 作業狀態 -->
			<td><s:if test="#WflogList.workStatus== '00'"> <s:text name="undwrt.workStatus.notExcute"/></s:if>
			    <s:if test="#WflogList.workStatus== '01'"> <s:text name="undwrt.workStatus.waitLaterQuery"/></s:if>
			    <s:if test="#WflogList.workStatus== '02'"> <s:text name="undwrt.workStatus.querying"/></s:if>
			    <s:if test="#WflogList.workStatus== '03'"> <s:text name="undwrt.workStatus.reciveAnswerRefuse"/></s:if>
			    <s:if test="#WflogList.workStatus== '04'"> <s:text name="undwrt.workStatus.reciveAnswerAngree"/></s:if>
			    <s:if test="#WflogList.workStatus == '05'"><s:text name="undwrt.workStatus.queryException"/></s:if>
			    <s:if test="#WflogList.workStatus == '06'"><s:text name="undwrt.workStatus.queryTimeOut"/></s:if>
			    <s:if test="#WflogList.workStatus == '07'"><s:text name="undwrt.workStatus.humanDeal"/></s:if>
			    <s:if test="#WflogList.workStatus == '08'"><s:text name="undwrt.workStatus.humanFinsh"/></s:if>
			</td>
			<!-- 查看風險 -->
			<td>
			   <s:if test="#WflogList.workStatus == '03'||#WflogList.workStatus == '04'||#WflogList.workStatus == '00'||#WflogList.nodeStatus == 4||#WflogList.nodeStatus == 0">
			               <input name="viewRisk" class="button" type="button" value="查看295" disabled="disabled"/>
			   </s:if>
			   
			   <s:if test="#WflogList.workStatus != '03' && #WflogList.workStatus != '04' && #WflogList.workStatus != '00' && #WflogList.nodeStatus != 4 && #WflogList.nodeStatus != 0">
			                <input name="viewRisk"  class="button" type="button" value="查看299" onclick="checkPrompt('<s:property value="#WflogList.businessNo"/>','<s:property value="#WflogList.businessType"/>',this);"/>
			   </s:if>
			</td>
			<!-- add by xuhuiling 需求150 20160818 end -->
			<!--mantis： CAR0369，處理人員：BI086，需求單編號：CAR0369:核心車險地址正規化作業 start -->
			<td id="addressFormatTd-<s:property value='#statu.index'/>" >
				<s:if test="${(businessType == 'T' || businessType == 'B')}">
					<s:if test="#WflogList.normastatus == 0">
						<s:text name="undwrt.address.format.status.0"/>
	            	</s:if>
	            	<s:if test="#WflogList.normastatus == 1">
						<s:text name="undwrt.address.format.status.1"/>
	            	</s:if>
					<s:if test="#WflogList.normastatus == 2">
			 			<s:text name="undwrt.address.format.status.2"/>
	            	</s:if>
	            	<s:if test="#WflogList.normastatus == 3">
						<s:text name="undwrt.address.format.status.3"/>
	            	</s:if>
	            	<s:if test="#WflogList.normastatus == 4">
						<s:text name="undwrt.address.format.status.4"/>
	            	</s:if>
					<s:if test="#WflogList.normastatus == 5">
						<s:text name="undwrt.address.format.status.5"/>
	            	</s:if>
					<s:if test="#WflogList.normastatus == 6">
						<s:text name="undwrt.address.format.status.6"/>
	            	</s:if>
					<s:if test="#WflogList.normastatus == 7">
						<s:text name="undwrt.address.format.status.7"/>
	            	</s:if>
				</s:if>

			</td>
			<!--mantis： CAR0369，處理人員：BI086，需求單編號：CAR0369:核心車險地址正規化作業 end -->
			<!-- add by songxin 需求：增加繳費資料輸入 start -->
			<td>
				<s:if test="${(businessType == 'T' || businessType == 'E') && (riskCode == 'A01' || riskCode == 'B01')}">
			   		<s:if test="${((underwriteflag == '5' || underwriteflag == '6') && jfeeflag=='1' && realpayrefflag =='0')||
			   		     (((underwriteflag == '5' || underwriteflag == '6') && jfeeflag=='1' && realpayrefflag =='0')&&(businessType == 'T' && superpay !=1))}">
			   			<input name = "forPayRef" class ="button" type ="button" value="輸入"  onclick="checkPayRef('<s:property value="#WflogList.businessNo"/>','<s:property value="#WflogList.businessType"/>',this);"/>
					</s:if>
				</s:if>	
			</td>
			<!-- add by songxin 需求：增加繳費資料輸入 end -->
	      </tr>
		  <!--隐含域-->
	       	<span style="display:none">
				<input name="BusinessNo"   value="<s:property value="#WflogList.businessNo"/>">
				<input name="BusinessType" value="<s:property value="#WflogList.businessType"/>">
				<input name="ComCode"      value="<s:property value="#WflogList.comCode"/>">
				<input name="ContractNo" value="<s:property value="#WflogList.contractNo"/>">
				<input name="FlowID"     value="<s:property value="#WflogList.id.flowId"/>">
				<input name="PackageID"  value="<s:property value="#WflogList.packageId"/>">
				<input name="LogNo"      value="<s:property value="#WflogList.id.logNo"/>">
				<input name="ModelNo"    value="<s:property value="#WflogList.modelNo"/>">
				<input name="NodeNo"     value="<s:property value="#WflogList.nodeNo"/>">
				<input name="FlowStatus" value="<s:property value="#WflogList.flowStatus"/>">
				<input name="DeptCode"   value="<s:property value="#WflogList.deptCode"/>">
				<input name="FlowInTime" value="<s:property value="#WflogList.flowInTime"/>">
				<input name="NodeStatus" value="<s:property value="#WflogList.nodeStatus"/>	">		
				<input name="RiskCode"   value="<s:property value="#WflogList.riskCode"/>">
				<input name="ClassCode"  value="<s:property value="#WflogList.classCode"/>">
				<input name="NodeName" value="<s:property value="#WflogList.nodeName"/>">
				<input name="WorkStatus" value="<s:property value="#WflogList.workStatus"/>">
				<input name="underwriteflag" value="<s:property value="#WflogList.underwriteflag"/>">
				<input name="jfeeflag" value="<s:property value="#WflogList.jfeeflag"/>">
				<input name="realpayrefflag" value="<s:property value="#WflogList.realpayrefflag"/>">
				<input name="Superpay" value="<s:property value="#WflogList.superpay"/>">
				<input name="ValueType" value="<s:property value="<%= valueType %>"/>">
				<!-- mantis： CAR0369，處理人員：BI086，需求單編號：CAR0369:核心車險地址正規化作業 START -->
				<input name="normastatus" value="<s:property value="#WflogList.normastatus"/>">
				<!-- add by songxin 需求：增加繳費資料輸入 end -->
			</span>
	
	   </s:iterator>
   </s:if>
		<!--控制数组有效的隐含域-->
		<span style="display:none">
	 		<input name="iBusinessNo">
	 		<input name="iBusinessType">
			<input name="iComCode">
	 		<input name="iContractNo">
			<input name="iFlowID">
			<input name="iPackageID">
			<input name="iModelNo">
			<input name="iNodeNo">
			<input name="iFlowStatus">
			<input name="iDeptCode">
			<input name="iFlowInTime">
			<input name="iNodeStatus">
			<input name="iLogNo">
			<input name="iRiskCode">
			<input name="iClassCode">
			<input name="iNodeName">
			<!-- mantis： CAR0369，處理人員：BI086，需求單編號：CAR0369:核心車險地址正規化作業 START -->
			<input name="iNormastatus">
			<input name="iNormastatusTxt">
			<!-- mantis： CAR0369，處理人員：BI086，需求單編號：CAR0369:核心車險地址正規化作業 END -->
		</span> 	 
   </table>
   <table class=menu align="center">
		<tr>
			<td>
				<app:navigate name="fm" objectName="fm"/>
			</td>
		</tr>
		<tr>
		<td align="center">
		<Input name="butCancelForm" class="button" type="button" alt="<s:text name='undwrt.pages.undwrtDeal.issuedModify'/>" value="<s:text name='undwrt.pages.undwrtDeal.issuedModify'/>" onclick="return Directlyissued();" >
		</td>
		</tr>
   </table>
   &nbsp;
  </form>
  </body>
  <!--mantis： CAR0123，處理人員： David ，需求單編號： CAR0123 新增關聯單卡控條件及一併處理關聯強制險-->
  <script type="text/javascript">
	window.onload = function() {
		var rBusinessNo = "<s:property value='#session.relevUndwrtBusiNo'/>";
		if(rBusinessNo != null && rBusinessNo != ""){
			checkTask(0);
		}
	};
  </script>
</html>