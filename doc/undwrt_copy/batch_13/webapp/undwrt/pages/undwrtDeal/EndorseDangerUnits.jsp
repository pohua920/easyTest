<!--***************************************************************************
* Description: 批单拆分危险单位页面
* Author     : LongYin
* CreateDate : 2005-5-23 20:30
* UpdateLog  ：Name       Date            Reason/Contents
*              huzhenyu   20071106        车险保费变化为负数时提交核批给出是否已经实收的提示
*               yanglibo  20080826        增加千分位控制
*               yanglibo     20080828        去掉风险评估信息里的千分位
*              zhangfan   20080902        传入批单中的comcode
*             yanglibo  20080918           15险类批改标的特殊处理
****************************************************************************-->
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/i18njs.jsp"%>
<%@page import="com.sinosoft.sysframework.reference.DBManager"%>
<%@page import="java.util.Collection"%>
<%@page import="com.sinosoft.platform.ui.control.action.UIPrpDriskConfigAction"%>
<%@page import="com.sinosoft.platform.dto.domain.PrpDriskConfigDto"%>
<%@page import="com.sinosoft.common.schema.model.PrpCmain"%>
<%@page import="com.sinosoft.common.schema.model.PrpPhead"%>

<%@page import="com.sinosoft.sysframework.common.Constants"%>
<%@page import="com.sinosoft.platform.dto.domain.PrpDuserDto"%>
<%@page import="com.sinosoft.platform.ui.control.action.UIPowerAction"%>
<%@page import="com.sinosoft.prpall.dto.domain.*"%>
<%@ page import="java.text.*"%>
<%@ page import="java.util.*"%>
<%@page import="com.sinosoft.undwrt.common.vo.CommonDangerItemInfoVo"%>
<%@page import="com.sinosoft.undwrt.pub.InternationalizationUtil"%>
<%@page import="com.sinosoft.prpall.dbsvr.pg.*"%>
<%@page import="com.sinosoft.prpall.blsvr.pg.*"%>
<%@page import="com.sinosoft.prpall.schema.*"%>
<%@page import="com.sinosoft.utility.error.*"%>
<%@page import="com.sinosoft.sff.blsvr.*"%>
<%@page import="com.sinosoft.dimension.*"%>
<%@page import="com.sinosoft.utility.string.ChgDate"%>
<%@page import="com.sinosoft.utiall.blsvr.BLPrpDconfigCode"%>
<%@page import="com.sinosoft.common.schema.model.PrpCPmain"%>
<%@page import="com.sinosoft.platform.bl.facade.BLPrpDpreauditConfigFacade"%>
<%@ taglib prefix="rc" uri="http://util.one.sinosoft.com/RCDate"%>

<%
	//add by zhulei 20060430 金额四舍五入显示
	java.text.DecimalFormat decimalFormat= new java.text.DecimalFormat("0.00");
  	java.text.DecimalFormat gradeFormat= new java.text.DecimalFormat("0.000");
  	String riskUnitFlag = ""; //是否拆分危险单位标识
  	String requiredReins  = ""; //是否强制分保试算
  	String reinsOfflineFlag  = ""; //是否离线计算
  	String riskCode = (String)session.getAttribute("riskCode");
  	String businessNo = request.getParameter("iBusinessNo");
  	String iPrpallIp     = (String)request.getAttribute("iPrpallIp");//查看业务详细信息ip
  	String businessType = request.getParameter("iBusinessType");
  	ArrayList ItemKind=(ArrayList)request.getAttribute("ItemKind");
  	boolean blnIsPreaudit = false;
  	boolean blnSplitDangerUnit = false;
  	String comCode =   (String) session.getAttribute("myComCode");
  	String strComCode = "";  
  	UIPrpDriskConfigAction uiPrpDriskConfigAction  = new UIPrpDriskConfigAction();
  	PrpCmain prpCmainDto = (PrpCmain)request.getAttribute("PrpCmainDto");
  	PrpDriskConfigDto prpDriskConfigDto = new PrpDriskConfigDto();
  	BLPrpDconfigCode blPrpDconfigCode = new BLPrpDconfigCode();
  	ChgDate nowDate = new ChgDate();
  	
  	//add by zhaoning20091109 begin Reason:除了预处理岗，其他双核级别都具有拆分危险单位的操作权限
  	BLPrpDpreauditConfigFacade blPrpDpreauditConfigFacade = new BLPrpDpreauditConfigFacade();
  	blnIsPreaudit = blPrpDpreauditConfigFacade.getIsPreaudit(businessNo,businessType,request.getParameter("iModelNo"),request.getParameter("iNodeNo"));
  	blnSplitDangerUnit = blPrpDpreauditConfigFacade.getAllowSplitDangerUnit(businessNo,businessType);
  	//add by zhaoning20091109 end
  	prpDriskConfigDto = uiPrpDriskConfigAction.queryRiskConfig(comCode,riskCode,"RISK_UNIT_FLAG");
  	if( prpDriskConfigDto!=null && prpDriskConfigDto.getConfigValue().equals("1") && comCode.substring(0,2).equals("00"))
    {
       riskUnitFlag = "1";
    } else {   
       riskUnitFlag = "0";
   	}
	InternationalizationUtil internal = new InternationalizationUtil();
	System.out.println(internal.getText("undwrt.pages.undwrtDeal.splitRiskUnit")+ riskUnitFlag);
	
   	//add by zhaoning20091109 begin Reason:除了预处理岗，其他双核级别都具有拆分危险单位的操作权限
   	if(prpDriskConfigDto!=null && !blnIsPreaudit && blnSplitDangerUnit)
   	{
     	riskUnitFlag = "1";
   	}
   	//add by zhaoning20091109 end
   
   	prpDriskConfigDto = uiPrpDriskConfigAction.queryRiskConfig(comCode,riskCode,"REQUIRED_REINS");
	if(	prpDriskConfigDto!=null && prpDriskConfigDto.getConfigValue().equals("1"))
	{
	     requiredReins = "1";//系统要求强制分保试算
	} else {   //System.out.println("系统没有要求强制分保试算");
		requiredReins = "0";//系统不要求强制分保试算
	}
	 
	//add begin by zhaijq 0060414 2799纯意外险不允许输入PML值
   	String  includeAccident = "Y";
   	if (riskCode.equals("2799"))
   	{
		includeAccident = "Y";
      	Collection prpItemKindList = (Collection)request.getAttribute("ItemKind"); 
      	if (prpItemKindList != null && prpItemKindList.size()>0)
      	{
   			Iterator iterator = prpItemKindList.iterator();
          	while(iterator.hasNext())
          	{
             	CommonDangerItemInfoVo commonDangerItemInfoDto = (CommonDangerItemInfoVo)iterator.next();
             	if (!commonDangerItemInfoDto.getKindCode().substring(0,1).equals("2"))
             	{
                 	includeAccident = "Y";
                 	break;
             	}
          	}
      	}
	}
%>

	<input type=hidden name="PrpallIp" value="<%=iPrpallIp%>">
	<input type="hidden" name="hiBusinessNo"     value="<s:property value="iBusinessNo"/>">
	<input type="hidden" name="hiBusinessType"   value="<s:property value="iBusinessType"/>">
	<input type="hidden" name="riskUnitFlag"     value=<%=riskUnitFlag%>><!--是否需要拆分危险单位标志1为允许-->
	<input type="hidden" name="requiredReins"    value="<%=requiredReins%>"> <!--是否强制分保试算-->
	<input type="hidden" name="hiRiskLevel"      value="">
	<input type="hidden" name="hiRetCurrency"    value="">
	<input type="hidden" name="hiRetentionValue" value="">
	<input type="hidden" name="hiDangerItemKind" value="">
	<input type="hidden" name="hiDangerFlag"     value="">
	<input type="hidden" name="hiRiskLevelDesc"  value="">
	<input type="hidden" name="includeAccident"  value="<%=includeAccident%>">
	<input type="hidden" name="reinsIP" value="${reinsIP }">
	<%
		String strClassCode = prpCmainDto.getClassCode();
        String businessFlag= prpCmainDto.getBusinessFlag();
        strComCode=prpCmainDto.getComCode();
      	//add by zhouhui 20090722 begin 免导团单查看详细信息时，调用自己的页面
        String strPolicySort= prpCmainDto.getPolicySort();
       	//add by zhouhui 20090722 end 免导团单查看详细信息时，调用自己的页面
       	
        if(businessType.equals("E") && (strClassCode.equals("A") ||strClassCode.equals("B")))
        {
   			BLPrpJplanFee blPrpJplanFee = new BLPrpJplanFee();
          	BLPrpPmain blPrpPmain = new BLPrpPmain();
          	String sql = " RealPayRefFee!=0 AND PayRefReason = 'R10'  AND CertiType='P' " + 
                       " AND CertiNo='" + prpCmainDto.getPolicyNo() + "'";
          	blPrpJplanFee.query(sql);
          	int planFeeCount = blPrpJplanFee.getSize();
          	blPrpPmain.getData(businessNo);
         	strComCode=blPrpPmain.getArr(0).getComCode();
         	System.err.println("strComCode====P===="+strComCode);
	%>
     		<input type="hidden" name="hiplanFeeCount" value="${planFeeCount}">
      		<input type="hidden" name="hichgPremium" value="<%=blPrpPmain.getArr(0).getChgPremium()%>">
	<%
        }
	%>

		<table class="common" cellpadding="5" cellspacing="1" align="center" border="0" width=100%>
	 	<!-- 保单信息 -->
	  	<s:if test='#request.PrpCmainDto!=null'>
	   		<tr class=listtitle>
	      		<td colspan="10"  >
		            <%--保单摘要信息 --%>
		            <s:text name="undwrt.EndorseDangerUnits.policySummaryInfo"/></td>
	    	</tr>
	    	
	     	<tr>
	      		<td class=title4>
		            <%--险  种 --%>
		            <s:text name="undwrt.EndorseDangerUnits.risk"/>:</td>
	            <td class=input4>
	            	<s:property value="#request.PrpCmainDto.riskCode"/></td>
	            <td  class=title4>
	            	<%--归属机构 --%>
	            	<s:text name="undwrt.EndorseDangerUnits.belongOrganization"/>:</td>
	            <td class=input4>
	            	<s:property value="#request.PrpCmainDto.comCode"/></td>
	   		</tr>
	   		
	   		<tr>
	       		<td class=title4>
		            <%--险种名称 --%>
		            <s:text name="undwrt.EndorseDangerUnits.riskName"/>:</td>
	            <td class=input4>
	            	<s:property value="riskCName"/></td>
	            <td class=title4>
	            	<%--归属机构名称 --%>
	            	<s:text name="undwrt.EndorseDangerUnits.belongOrganizationName"/></td>
	            <td class=input4>
	            	<%=request.getAttribute("comCName")%></td>
	   		</tr>
	   		
	   		<tr>
	       		<td  class=title4>
	            	<%--保单号 --%>
	            	<s:text name="undwrt.EndorseDangerUnits.policyNo"/>:</td>
	            <td class=input4>
	             	<s:property value="#request.PrpCmainDto.policyNo"/></td>
	            <s:if test='prpCmainDto.classCode!="A" && prpCmainDto.classCode!="B" 
	            	&& (prpCmainDto.riskCode!="9997") && (prpCmainDto.riskCode!="9998") && (prpCmainDto.riskCode!="9999")'>
	    		<td class=title4 style="display:none;">
	    			<%--保单分级 --%>
	    			<font color="red"><s:text name="undwrt.EndorseDangerUnits.policyLevel"/>：</font>
		            <input type="hidden" name="riskCode" description="<s:text name='undwrt.pages.undwrtDeal.riskCode'/>" 
		            	value="<s:property value="#request.PrpCmainDto.riskCode"/>"/>
		            <input type="hidden" name="hiClassCode" description="<s:text name='undwrt.pages.undwrtDeal.riskCode'/>" 
		            	value="<s:property value="#request.PrpCmainDto.classCode"/>"/>
		            <input type="hidden" name="policyType" value="<s:property value="ManualGradeCode"/>"/>
		            <input type="hidden" name="coinsFlag" value="<s:property value="#request.PrpCmainDto.coinsFlag"/>"/>
	            </td>
	            <td class=input4 style="display:none;">
		            <font color="red"><s:property value="ManualGradeCode"/></font>
		            <input type="hidden" name="hiManualGradeCode" description="<s:text name='undwrt.notice.originalPolicyOutline'/>" 
		            	value="<s:property value="ManualGradeCode"/>">
		            <%--保单号 --%>
		            <input type="hidden" name="hiPolicyNo" description="<s:text name='undwrt.CommonDangerUnits.PolicyNo'/>" 
		            	value="<s:property value="#request.PrpCmainDto.policyNo"/>">
		            <%--保单批单对应的投保单号 --%>
		            <input type="hidden" name="hiProposalNo" description="<s:text name='policyManage.policyApprovalBillRelateThrowPolicyNo'/>" 
		            	value="<s:property value="#request.PrpCmainDto.proposalNo"/>">
	            </td>	
	    	    </s:if>
	    	    <s:else>
	    		<td class=title4>
		            <input type="hidden" name="riskCode" description="<s:text name='undwrt.pages.undwrtDeal.riskCode'/>" 
		            	value="<s:property value="#request.PrpCmainDto.riskCode"/>">
		            <input type="hidden" name="hiClassCode" description="<s:text name='undwrt.pages.undwrtDeal.riskCode'/>" 
		            	value="<s:property value="#request.PrpCmainDto.classCode"/>">
		            <input type="hidden" name="policyType" value="<s:property value="#request.PrpCmainDto.policyType"/>">
		            <input type="hidden" name="coinsFlag" value="<s:property value="#request.PrpCmainDto.coinsFlag"/>">
	            </td>
	            <td class=input4>
	            	<%--保单号 --%>
		            <input type="hidden" name="hiPolicyNo" description="<s:text name='undwrt.CommonDangerUnits.PolicyNo'/>" 
		            	value="<s:property value="#request.PrpCmainDto.policyNo"/>">
		            <%--保单号对应的投保单号 --%>
		            <input type="hidden" name="hiProposalNo" description="<s:text name='policyManage.policyApprovalBillRelateThrowPolicyNo'/>" 
		            	value="<s:property value="#request.PrpCmainDto.proposalNo"/>">
	            </td>
	    		</s:else>
	   		</tr>
	   		
	     	<tr>
	      	<%
	      		if(strClassCode.equals("26") || strClassCode.equals("27")){
	   		%>
	      		<td class=title4>
	        		<%--投保人名称 --%>
	             	<s:text name="undwrt.EndorseDangerUnits.policyerName"/>:</td>
	            <td class=input4>
	            	<s:property value="#request.PrpCmainDto.appliName"/></td>
	       	<% } else { %>
	            <td class=title4>
	            	<%--被保险人名称 --%>
	            	<s:text name="undwrt.EndorseDangerUnits.insuredName"/>:</td>
	            <td class=input4>
	            	<s:property value="#request.PrpCmainDto.insuredName"/></td>
	      	<% } %>          
	     	<s:if test='"MC"==iRiskCode'>
	        	<td class=title4>
	        	   <%--水险的TB险别没有起航日期20140715 --%>
	        		<s:if test='"TB"!=rationCode'>
	            		<%--起运日期 --%>
	            		<s:text name="undwrt.pages.undwrtDeal.startDate"/>:
	            	</s:if>
	            </td>
	            <td class=input4>
	            	<s:if test='"TB"!=rationCode'>
	            		<rc:rcDate value = "${PrpCmainDto.startDate}" format="yyyy-MM-dd"/><s:text name="prompt.day"/>
	            	</s:if>
	            </td>
	       	</s:if>
	      	<s:else>
	       		<td class=title4>
	            	<%--保险期间 --%>
	            	<s:text name="undwrt.EndorseDangerUnits.insureDuration"/>:</td>
	            <td class=input4>
		            <rc:rcDate value="${PrpCmainDto.startDate}" format="yyyy-MM-dd"/>&nbsp;<s:text name="prompt.day"/>${PrpCmainDto.startHour}
		            <s:text name="prompt.hour"/><s:text name="prompt.start"/><s:text name="undwrt.EndorseDangerUnits.to"/>&nbsp;
		            <rc:rcDate value = "${PrpCmainDto.endDate}" format="yyyy-MM-dd"/>
		            <s:text name="prompt.day"/>${PrpCmainDto.endHour}<s:text name="prompt.hour"/>
		            </td>
	     	</s:else>         
			</tr>
			<s:if test="${PrpCmainDto.classCode ==  'F'}">
				<tr>
		       		<td class=title4>
		            	<%--总保险金额 --%>
		            	<s:text name="undwrt.EndorseDangerUnits.totalInsureAmount.PQ"/>:</td>
		            <td class=input4>
			            <s:property value="#request.PrpCmainDto.currency"/>&nbsp;
			            <input type="text" class=readonly name="sumAmount" value="<fmt:formatNumber value="${PrpCmainDto.sumAmount}" pattern="#,##0.00"/>">
		            </td>
		            <td class=title4>
		            	<%--总保险费 --%>
		            	<s:text name="undwrt.EndorseDangerUnits.totalInsureFee.PQ"/>:</td>
		            <td class=input4>
			            <s:property value="#request.PrpCmainDto.currency"/>&nbsp;
			            <input type="text" class=readonly name="sumAmount" value="<fmt:formatNumber value="${PrpCmainDto.sumPremium}" pattern="#,##0.00"/>">
		            </td>
	      		</tr>
	      		<tr>
		       		<td class=title4>
		            	<%--总保险金额 --%>
		            	<s:text name="undwrt.EndorseDangerUnits.totalInsureAmount.PH"/>:</td>
		            <td class=input4>
			            <s:property value="#request.PrpCmainDto.currency"/>&nbsp;
			            <input type="text" class=readonly name="sumAmount" value="<fmt:formatNumber value="${PrpCPmainDto.sumAmount}" pattern="#,##0.00"/>">
		            </td>
		            <td class=title4>
		            	<%--总保险费 --%>
		            	<s:text name="undwrt.EndorseDangerUnits.totalInsureFee.PH"/>:</td>
		            <td class=input4>
			            <s:property value="#request.PrpCmainDto.currency"/>&nbsp;
			            <input type="text" class=readonly name="sumAmount" value="<fmt:formatNumber value="${PrpCPmainDto.sumPremium}" pattern="#,##0.00"/>">
		            </td>
	      		</tr>
			</s:if>
			<s:else>
		      	<tr>
		       		<td class=title4>
		            	<%--总保险金额 --%>
		            	<s:text name="undwrt.EndorseDangerUnits.totalInsureAmount"/>:</td>
		            <td class=input4>
			            <s:property value="#request.PrpCmainDto.currency"/>&nbsp;
			            <input type="text" class=readonly name="sumAmount" value="<fmt:formatNumber value="${PrpCmainDto.sumAmount}" pattern="#,##0.00"/>">
		            </td>
		            <td class=title4>
		            	<%--总保险费 --%>
		            	<s:text name="undwrt.EndorseDangerUnits.totalInsureFee"/>:</td>
		            <td class=input4>
			            <s:property value="#request.PrpCmainDto.currency"/>&nbsp;
			            <input type="text" class=readonly name="sumAmount" value="<fmt:formatNumber value="${PrpCmainDto.sumPremium}" pattern="#,##0.00"/>">
		            </td>
		      	</tr>
	      	</s:else>
		<%
		//add by zhulei 20060425 管理费比例 begin
	  	PrpDuserDto user = (PrpDuserDto) (session.getAttribute("user"));
	  	double dblPremium1 = prpCmainDto.getSumPremium().doubleValue();
	  	String strManageFeeDisplay = "none";  //管理费比例默认不显示
	  	boolean allowManageFee = false;       //条件1：是否允许录入管理费比例
	  	boolean allowManageFee_user = false;  //条件2：操作员是否有录入管理费比例的权限
	  	boolean allowManageFee_Flag = false;  //条件3：业务录入时是否允许管理费比例
	  	
	  	//管理费比例录入开关
	  	prpDriskConfigDto = uiPrpDriskConfigAction.queryRiskConfig(comCode,riskCode,"SWITCH_MANAGEFEERATE");
	  	if( prpDriskConfigDto!=null && prpDriskConfigDto.getConfigValue().equals("1"))
	  	{
	    	allowManageFee = true;
	  	} else {
	   		allowManageFee = false;
	  	}
	  	
	  	//人员管理费比例操作权限
	  	allowManageFee_user = UIPowerAction.checkPowerReturn(user,"prpall.policy.managefeerate");
	  	PrpCPexpenseDto prpCPexpenseDto = (PrpCPexpenseDto)request.getAttribute("PrpCPexpenseDto");
	  	double dbManageFeeRate = 0;
	  	double dbManageFee = 0;
	  	if(prpCPexpenseDto!=null)
	  	{
	    	dbManageFeeRate = prpCPexpenseDto.getManageFeeRate();
	    	dbManageFee = dblPremium1*dbManageFeeRate/100;
	    	if(prpCPexpenseDto.getFlag().length()>=2)
	    	{
	      		//Flag第二位为“2”或“3”，管理费标志：true
		    	if(prpCPexpenseDto.getFlag().substring(1,2).equals("2") || prpCPexpenseDto.getFlag().substring(1,2).equals("3"))
		    	{
		      		allowManageFee_Flag = true;
		    	}
		    	//Flag第二位为“1”并且管理费开关为“开”，管理费标志：true
		    	if(prpCPexpenseDto.getFlag().substring(1,2).equals("1") && allowManageFee)
		    	{
		      		allowManageFee_Flag = true;
		    	}
	    	}
	  	}
	  	
	  	//zhulei：管理费比例显示，三个条件，prpDriskConfig配置放开，当前登陆人员有权，当前业务录入时允许管理费比例；
	  	if(allowManageFee && allowManageFee_user && allowManageFee_Flag)
	  	{
	    	strManageFeeDisplay = "";
	  	}
	  	
	  	//管理费比例是否允许修改控制，批改时，仅批改类型中包含“管理费比例批改”（EndorseType＝59）时，允许双核修改管理费
	  	String strManageFeeAble = "readonly";
	  	String strManageFeeClass = "readonly";
	  	PrpPhead prpPheadDto = (PrpPhead)(request.getAttribute("PrpPheadDto"));
	  	if(prpPheadDto!=null && prpPheadDto.getEndorType().indexOf("59")>0)
	  	{
	    	strManageFeeAble = "";
	    	strManageFeeClass = "common";
	  	}
	  	String endorseNo = prpPheadDto.getEndorseNo();
	  	//add by zhulei 20060424 管理费比例 end
		%>
	          
			<!-- add by zhulei 20060423 管理费比例 begin -->
	    	<s:if test='PrpCPexpenseDto!=null'>
	     	<tr style="display:${strManageFeeDisplay}">
	       		<td class="title4">
	          		<%--管理费比例 --%>
	           		<s:text name="undwrt.EndorseDangerUnits.managerScale"/>：</td>
	           	<td class="input4">
		       		<input type="hidden" name="ManageFeeRateOld" value="<s:property value="#request.PrpCmainDto.manageFeeRate"/>" >
		        	<input type="text" name="ManageFeeRate"  ${strManageFeeAble} value="<s:property value="#request.PrpCmainDto.manageFeeRate"/>"
		          		onblur="changeManageFeeRate(this)"/></td>
	          	<td class="title4">
	           		<%--管理费金额 --%>
	            	<s:text name="undwrt.EndorseDangerUnits.managerAmount"/>：</td>
	         	<td class="input4">
		       		<input type="hidden" name="Premium2" value="<%=decimalFormat.format(dblPremium1)%>">
		        	<input type="text" name="ManageFee" class="readonly" readonly value="<%=decimalFormat.format(dbManageFee)%>"/></td>
	     	</tr>
	    	</s:if>
	    	<!-- add by zhulei 20060423 管理费比例 end -->
	  	</s:if>
	  	</table>
	
	<%
		//对象定义部分
		BLPrpPtext     blPrpPtext     = null;   //批文对象
		PrpPtextSchema prpPtextSchema = null;   //批单的PrpPtextSchema对象
		if((businessNo == null )||(businessNo.trim().length() == 0))
		{
			throw new UserException(-98,-1014,"UIPtextShow.jsp");
		}
		blPrpPtext = new BLPrpPtext();
		blPrpPtext.query(" EndorseNo = '" + businessNo +"' ORDER BY LineNo",0);
	%>
	
		<table class="common" cellpadding="5" cellspacing="1" align="center" border="0" width=100%>
		<%
		//意健险特殊处理 add by luyang 2005-8-31 19:53
		if(strClassCode.startsWith("26") || strClassCode.startsWith("27")){
		%>
		<tr class=listtitle>
       		<td colspan="10" >
	       		<%--原始标的信息 --%>
	            <s:text name="undwrt.EndorseDangerUnits.originalObjInfo"/></td>
 		</tr>
 		
 		<tr class=common>
   			<td>
	   			<%--条款名称 --%>
	            <s:text name="undwrt.EndorseDangerUnits.itemName"/></td>
            <td>
	            <%--险种责任 --%>
	            <s:text name="undwrt.EndorseDangerUnits.riskDuty"/></td>

		<%
		if(!riskCode.equals("2727")){
		%> 
      		<td>
         		<%--折扣 --%>
            	<s:text name="undwrt.EndorseDangerUnits.rebate"/></td>
    	<%
		}
		if(riskCode.equals("2703") || riskCode.equals("2708") ){
		%>
     		<td>
            	<%--份数 --%>
            	<s:text name="undwrt.EndorseDangerUnits.copies"/></td>
		<%
		}
		if(!riskCode.equals("2727")){
		%>   
    		<td>
            	<%--人数--%>
            	<s:text name="undwrt.EndorseDangerUnits.peopleCount"/></td>
   		<%
 		}
		%>
            <td>
            	<%--保额 --%>
            	<s:text name="undwrt.EndorseDangerUnits.policyAmount"/></td>
            <td>
            	<%--保费 --%>
            	<s:text name="undwrt.EndorseDangerUnits.policyFee"/></td>
   		<%
		if(!riskCode.equals("2727")){
		%>   
			<td>
				<%--人数变化 --%>
				<s:text name="undwrt.EndorseDangerUnits.peopleCountChange"/></td>
   		<%
		}
		%>
			<td>
				<%--保额变化 --%>
				<s:text name="undwrt.EndorseDangerUnits.policyAmountChange"/></td>
			<td>
				<%--保费变化 --%>
				<s:text name="undwrt.EndorseDangerUnits.policyFeeChange"/></td> 
		</tr>
					 
		<s:if test='#request.ItemKind!=null'>
		<s:iterator id="ItemKind" status="statu" value="#request.ItemKind">
		<tr class=common>
   			<td><input class="formtitle1"  readonly  value="<s:property value="#ItemKind.kindName"/>"/></td>
            <td><input class="formtitle1"  readonly  value="<s:property value="#ItemKind.itemDetailName"/>"/></td>
      		<%
			if(!riskCode.equals("2727")){
			%>  
            <td>
            	<input class="formtitle1"  readonly  value="<s:property value="#ItemKind.discount"/>"/></td>
			<%
			}
			if(riskCode.equals("2703") || riskCode.equals("2708") ){
			%>     
            <td>
            	<input class="formtitle1"  readonly  value="<s:property value="#ItemKind.value"/>"/></td> 
			<%
			}
			if(!riskCode.equals("2727")){
			%>
            <td>
            	<input class="formtitle1"  readonly  value="<fmt:formatNumber value="${quantity}" pattern="0"/>"/></td>
 			<%
			}
			%>
            <td>
            	<input class="formtitle1"  readonly  value="<fmt:formatNumber value="${amount}" pattern="#,##0.00"/>"/></td>
            <td>
            	<input class="formtitle1"  readonly  value="<fmt:formatNumber value="${premium}" pattern="#,##0.00"/>"/></td>
           
			<%
				if(!riskCode.equals("2727")){
			%>  
            <td>
            	<input class="formtitle1"  readonly  value="<fmt:formatNumber value="${chgQuantity}" pattern="0.00"/>"/></td>
			<%
			}
			%>    
            <td>
            	<input class="formtitle1"  readonly  value="<fmt:formatNumber value="${chgAmount}" pattern="0.00"/>"/></td>
            <td>
            	<input class="formtitle1"  readonly  value="<fmt:formatNumber value="${chgPremium}" pattern="#,##0.00"/>"/></td>
		</tr>
		</s:iterator>
		</s:if>

		<% }else{ %>
		<tr class=listtitle>
   			<td colspan="13" >
        		<%--批改标的信息 --%>
            	<s:text name="undwrt.EndorseDangerUnits.correctObjInfo"/>
            </td>
   		</tr>
   		
     	<tr class=common>
     		<td >
         		<%--序号 --%>
            	<s:text name="undwrt.EndorseDangerUnits.serialNo"/></td>
            <td >
            	<%--险别 --%>
            	<s:text name="undwrt.EndorseDangerUnits.kind"/></td>
            <%
       		if(!(riskCode.equals("0101") ||riskCode.equals("0102") ||riskCode.equals("0104") ||
          		riskCode.equals("0110") ||riskCode.equals("0111") ||riskCode.equals("0112"))){
   			%>
            <td >
            	<%--标的项目 --%>
            	<s:text name="undwrt.EndorseDangerUnits.objProject"/></td>
   			<%
        	}if(strClassCode.equals("09") || strClassCode.equals("10")){
            %>
            <td>
            	<%--被保险货物名称 --%>
            	<s:text name="undwrt.EndorseDangerUnits.insuredObjName"/></td>
        	<%
            }else{
            %>
            <td>
            	<%--标的名称 --%>
            	<s:text name="undwrt.EndorseDangerUnits.objName"/></td>
         	<%
            }
            %>
            <%--水险没有邮编区号20140702 --%>
			<s:if test='"MC"!=iRiskCode'>
            <td >
            	<%--邮编 --%>
            	<s:text name="undwrt.EndorseDangerUnits.postcode"/>
            </td>
            <td >
            	<%--标的地址 --%>
            	<s:text name="undwrt.EndorseDangerUnits.objAddress"/>
            </td>
            </s:if>
            <td >
            	<%--币别 --%>
            	<s:text name="undwrt.EndorseDangerUnits.currency"/></td>
     		<%
            if(strClassCode.equals("15")){
            %>
            <td>
	            <%--累计责任限额 --%>
	            <s:text name="undwrt.EndorseDangerUnits.totalDutyLimitAmout"/></td>
            <td>
	            <%--变化累计责任限额 --%>
	            <s:text name="undwrt.EndorseDangerUnits.changeTotalDutyLimitAmout"/></td>
            <td>
	            <%--每次事故责任限额 --%>
	            <s:text name="undwrt.EndorseDangerUnits.perEventDutyLimitAmout"/></td>
            <td>
	            <%--变化每次事故责任限额 --%>
	            <s:text name="undwrt.EndorseDangerUnits.changePerEventDutyLimitAmout"/></td>
         	<%
           	}else{
    		%>
            <td >
	            <%--原始保额 --%>
	            <s:text name="undwrt.EndorseDangerUnits.originalProtectAmount"/></td>
            <%-- <td>
            	<div style="display:none">
	           	变化保额
	            <s:text name="undwrt.EndorseDangerUnits.changeProtectAmount"/>
	            </div>&nbsp;
	     	</td> --%>
        	<%
            }
            %>
            <%-- <td >
            	<div style="display:none">
	            	原始保费
	            	<s:text name="undwrt.EndorseDangerUnits.originalProtectFee"/>
	            </div>
            </td>
            <td>
            	<div style="display:none">
	           		变化保费
	            	<s:text name="undwrt.EndorseDangerUnits.changeProtectFee"/>
	            </div>&nbsp;
        	</td> --%>
		</tr>
		
		<s:if test='#request.ItemKind!=null'>
		<s:iterator id="ItemKind" status="statu" value="#request.ItemKind">
		<!--mantis： EGN0110_0610，處理人員：DP0706，EGN0110_新增CM機械綜合險START-->
		<s:if test="(#ItemKind.riskCode != 'CM') || (#ItemKind.riskCode == 'CM' && #ItemKind.kindCode != 'CM001')">
   		<tr class=common>
      		<td>
         		<input class="formtitle1"  name="itemKindNo" value="<s:property value="#ItemKind.itemKindNo"/>"/></td>
            <td>
	       		<input class="formtitle1"  value="<s:property value="#ItemKind.kindName"/>"/>
	        	<input type=hidden value="<s:property value="#ItemKind.kindCode"/>"/></td>
     		<%
       		if(!(riskCode.equals("0101") ||riskCode.equals("0102") ||riskCode.equals("0104") ||
                 riskCode.equals("0110") ||riskCode.equals("0111") ||riskCode.equals("0112"))){
            %>
            <td>
           		<input class="formtitle1" name=""  value="<s:property value="#ItemKind.itemCode"/>"/>
            </td>
           	<%} else {%>
           	<td>
            	<input  type="hidden" class="formtitle1" name=""  value="<s:property value="#ItemKind.itemCode"/>"/>
            </td>
           	<%} %>
         	<td>
           		<input class="formtitle1" name=""  value="<s:property value="#ItemKind.itemDetailName"/>"/></td>
            <%--水险没有邮编区号20140702 --%>
			<s:if test='"MC"!=iRiskCode'>
            <td>
            	<input class="formtitle1" name=""  readonly value="<s:property value="#ItemKind.addressCode"/>"/>
            </td>
            <td>
            	<input class="formtitle1" name=""  readonly value="<s:property value="#ItemKind.addressName"/>"/>
            </td>
            </s:if>
            <td >
            	<input class="formtitle1" name="iCurrency" value="<s:property value="#ItemKind.currency"/>"/></td>
            <%
            if(strClassCode.equals("15")){
            %>
         	<td>
            	<input class="formtitle1" name="iAmount" value="<s:property value="#ItemKind.limit03Fee"/>"/></td>
            <td>
            	<input class="formtitle1" name="ichgAmount" value="<s:property value="#ItemKind.chgLimit03Fee"/>"/></td>
            <td>
            	<input class="formtitle1" name="iAmount1" value="<s:property value="#ItemKind.limitFee"/>"/></td>
            <td>
          		<input class="formtitle1" name="ichgAmount1" value="<s:property value="#ItemKind.chgLimitFee"/>"/></td>
      		<%
            } else {
            %>
            <td>
            	<input type="hidden" name="calculateFlag" value="<s:property value="#ItemKind.calculateFlag"/>"/>
            	<input class="formtitle1" name="iAmount" value="<fmt:formatNumber value="${amount}" pattern="#,##0.00"/>"/></td>
            <td style="display: none">
            	<input class="formtitle1" name="ichgAmount" value="<fmt:formatNumber value="${chgAmount}" pattern="#,##0.00"/>"/></td>
            <%
            }
            %>
         	<td style="display: none">
       			<input class="formtitle1" name="iPremium" value="<fmt:formatNumber value="${premium}" pattern="#,##0.00"/>"/></td>
        	<td style="display: none">
         		<input class="formtitle1" name="ichgPremium" value="<fmt:formatNumber value="${chgPremium}" pattern="#,##0.00"/>"/></td>
		</tr>
		</s:if><!--mantis： EGN0110_0610，處理人員：DP0706，EGN0110_新增CM機械綜合險END-->
        </s:iterator>
        </s:if>
		<%
		}
		%>              
		</table>
      
		<table class="common" cellpadding="5" cellspacing="1" align="center" border="0" width=100%>
			<tr>
				<td class=input4>
		       		<s:if test='handType=="22"'>
					<input type="hidden" name="ClaimNo"  value='<s:property value="ClaimNo"/>'>
				  	<input type="hidden" name="RegistNo" value='<s:property value="RegistNo"/>'>
				  	<input type="hidden" name="PolicyNo" value='<s:property value="policyNo"/>'>
				  	<%--理赔信息 --%>
				  	<Input type="button" class="button" name="claimInfo" value="<s:text name='undwrt.pages.undwrtDeal.payMessages'/>" onclick="viewClaimInfo();">
		        	</s:if>
		        	<%--added by zhouhui begin 20090722 免导团单调用自己的保单详细信息页面--%>
		         	<%--保单类型 --%>
		       		<input type="hidden" name="PolicySort" description="<s:text name='undwrt.pages.undwrtDeal.insurancePolicyType'/>" value="${strPolicySort}">
		         	<%--added by zhouhui end 20090722 免导团单调用自己的保单详细信息页面--%>
		        	<%--详细信息 --%>
					<Input name="butDetail" class="button" type="button" alt="<s:text name='undwrt.pages.undwrtDeal.detailedInformation'/>" 
						value="<s:text name='undwrt.pages.undwrtDeal.detailedInformation'/>" onclick="showBusinessInfo('${strComCode}')">          	
					<s:if test='historyProposal=="true"'>
						<%--历史承保信息 --%>               
		           		<input type="button" class=longbutton  value="<s:text name='undwrt.pages.undwrtDeal.historyUnderwriteMessages'/>" name="BusinessTotalInfo" 
		           			onclick="showBusinessTotalInfo('<s:property value="iBusinessNo"/>');">
					</s:if>
					<s:if test='historyLoss=="true"'> 
						<%--历史赔付信息 --%>              
		        		<input type="button" value="<s:text name='undwrt.pages.undwrtDeal.historyPayMessages'/>" class=longbutton name="HistoryLossInfo" 
		        			onclick="showHistoryLossInfo('<s:property value="iBusinessNo"/>');">
					</s:if>   	
		        	<input type="hidden" name="typeTreeXML" value="${typeTreeXML}">
			    	<input type="hidden" name="remoteUrl" value="${remoteUrl }">
			    	<input type="hidden" name="paramString" value="${paramString }">	
					<s:if test=' iNodeStatus!="4" && iNodeStatus !="0" '>
				  		<%--影像资料--%>
		          		<Input name="buttonMessage1" type="hidden" class="longbutton" type="button" value="<s:text name='undwrt.prompt.videoFiles'/>" onclick="queryImage();">
					</s:if>
				
					<s:if test='handType=="22"'>
						<%--保单信息 --%>
				  		<Input type="button" name="PolicyNoInfo" class="longbutton" value="<s:text name='undwrt.pages.undwrtDeal.insurancePolicyMessages'/>"
				   			onclick="showPolicyInfo();">
					</s:if>
					<s:if test='handType=="11" && iNodeStatus !="4" && iNodeStatus !="0" '>
						<%--备注记录 --%>
						<s:if test="existMessage">
				    	<Input name="buttonMessage1" class="button" type ="hidden" type="button"  style="color:yellow" value="<s:text name='undwrt.pages.undwrtDeal.remarkRecord'/>" 
				    		onclick="openWinQuery();">
				    	</s:if>
							<s:else>
						<Input name="buttonMessage1" class="button" type ="hidden" type="button" value="<s:text name='undwrt.pages.undwrtDeal.remarkRecord'/>" 
				    		onclick="openWinQuery();">
				    	</s:else>
				  		<!-- added by yanglibo 20090812 begin reason：历年承保理赔信息 -->
		               	<%
		        			blPrpDconfigCode.getFunNameOrFunType(strComCode,riskCode,"BISumPaid",nowDate.getCurrentTime("yyyy-MM-dd"));
		             		if(blPrpDconfigCode.getSize()>0) {
		                %>
		            	<%--历年承保、理赔信息查询 --%>
		            	<Input name="butEPolicyClaimInfo" class="longbutton" type="button" value="<s:text name='undwrt.pages.undwrtDeal.messagesQuery'/>" 
		            		onclick="viewPolicyClaimInfo();">
		                <%
		                  	}
		                %> 
		       		</s:if>
				  	<!-- added by yanglibo 20090812 end reason：历年承保理赔信息 -->
				  	<s:elseif test='handType=="22"'>
						<%--理赔情况记录 --%>
					  	<Input name="buttonMessage1" class="longbutton" type="button" value="<s:text name='undwrt.pages.undwrtDeal.payConditionRecord'/>" 
					  		onclick="openWinQuery();">
					</s:elseif>
				</td>
			</tr>
		</table>
      	
      	<table class="common" cellpadding="5" cellspacing="1" align="center" border="0" width=100%>
			<tr>
				<td>
					<%--批文信息 --%>
					<IMG name="butDanger" class="button" type="button" alt="<s:text name='undwrt.showApprovalInfo'/>" 
						src="/undwrt/common/images/butCollapse.gif" onclick="showPage(this,PtextInfo)">
			      	<s:text name="undwrt.EndorseDangerUnits.approvalInfo"/>
					<span id="PtextInfo" style="display:none">
				    	<table>      
				   			<tr>
				     			<td class="input4">
				        			<pre><%=(blPrpPtext.getEndorseText())%></pre>
				      			</td>
				     		</tr>
				  		</table>
					</span>
				</td>
			</tr>
		</table>
		
		<table class="common" cellpadding="5" cellspacing="1" align="center" border="0" width=100%>
		<s:if test='handType==11'>
			<tr>
				<td>
		      		<%--风险评估信息 --%>
		     		<IMG name="butDanger" class="button" type="button" alt="<s:text name='undwrt.pages.undwrtDeal.riskAssessMessages'/>" 
		      			src="/undwrt/common/images/butCollapse.gif" onclick="showPage(this,dangerInfo);">
		      		<s:text name="undwrt.EndorseDangerUnits.riskAccessInfo0"/>
		      		
		      		<span id="dangerInfo" >
		     		<table width="100%">
		        	<tr class=common>
		        		<td>
		           			<input type="hidden" name="hiRiskLevel"      value="">
				           	<input type="hidden" name="hiRetCurrency"    value="">
				           	<input type="hidden" name="hiRetentionValue" value="">
				           	<input type="hidden" name="hiDangerItemKind" value="">
				           	<input type="hidden" name="hiDangerFlag"     value="">
				           	
		         			<span style="display:none">
		          			<table class="common" cellpadding="5" cellspacing="1" align="center" id="DangerUnit_Data" style="display:none" style="width: 100%">
		          			<tbody>
		          				<tr class=common>
		            				<td>
		             				<table class="common" style="width:100%" cellspacing="1" cellpadding="1">
		             				<tr class=common>
		             				<td width='1%'></td>
		             					<td width='5%'>
			             					<%--序号 --%>
			             					<s:text name="undwrt.EndorseDangerUnits.serialNo"/></td>
					 					<td width='8%'>
						 					<%--风险等级 --%>
						 					<s:text name="undwrt.EndorseDangerUnits.riskLevel"/></td>
					 					<td width='12%'>
						 					<%--风险名称 --%>
						 					<s:text name="undwrt.EndorseDangerUnits.riskName"/></td>
					 					<td width='5%' colspan="2">
						 					<%--自留额 --%>
						 					<s:text name="undwrt.EndorseDangerUnits.autoRemainAmount"/></td>
					 					<% if (strClassCode.equals("27") && includeAccident.equals("Y")) {  %>
					 					<td width='5%' colspan='2'>
						 					<%--意健险PML值 --%>
						 					<s:text name="undwrt.EndorseDangerUnits.accidentHealthPMLValue"/></td>
					 					<% } %>
					 					<td width='5%' colspan="2">
						 					<%--币别 --%>
						 					<s:text name="undwrt.EndorseDangerUnits.currency"/></td>
					 					<td width='8%' colspan="2">
						 					<%--原始保额 --%>
						 					<s:text name="undwrt.EndorseDangerUnits.originalProtectAmount"/></td>
					 					<td width='8%' style="display:none">
						 					<%--变化保额 --%>
						 					<s:text name="undwrt.EndorseDangerUnits.changeProtectAmount"/></td>
					 					<td width='8%' style="display:none">
						 					<%--原始保费 --%>
						 					<s:text name="undwrt.EndorseDangerUnits.originalProtectFee"/></td>
					 					<td width='8%' style="display:none">
						 					<%--变化保费--%>
						 					<s:text name="undwrt.EndorseDangerUnits.changeProtectFee"/></td>
					 					<td width='5%'>
						 					<%--占比 --%>
						 					<s:text name="undwrt.EndorseDangerUnits.occupyScale"/>%</td>
					 					<td width='10%'>
						 					<%--子信息--%>
						 					<s:text name="undwrt.EndorseDangerUnits.sonInfo"/></td>
		    						    <td width='5%'>*</td>
									</tr>
									
									<tr class=common>
										<!-- 商火需求在危险单位前加入单选框，被选中的危险单位可以进行临分20140306 -->
										<td rowspan="3">
											<s:if test='"F01"==iRiskCode'>
												<s:if test="#dangerDetail.hasEnquiry">
	    									 		<input name="facing" type="checkbox" checked onclick="ChangeToValue(this,facing)">
	    									 		<input type="hidden" name="whetherFacing" value="1">
	    									 	</s:if>
	    										<s:else>
	    									 		<input name="facing" type="checkbox"  onclick="ChangeToValue(this,facing)">
	    											<input type="hidden" name="whetherFacing" value="0">
	    									 	</s:else>
	    									</s:if>
										</td>
										<td  rowspan ="3">
											<input class="free" readonly name="dangerNo" readonly description="<s:text name='undwrt.CommonDangerUnits.serialNo'/>"/></td>
										<td>
											<input class="free"  name="riskLevel" description="<s:text name='undwrt.CommonDangerUnits.dangerLevel'/>"/></td>
										<td>
											<input class="free" readonly name="riskLevelDesc" description="<s:text name='undwrt.CommonDangerUnits.dangerName'/>"/></td>
										<td colspan="2">
			    							<input type='hidden' readonly name="retCurrency" description="<s:text name='undwrt.CommonDangerUnits.autoAmountCurrency'/>"/>
											<input class="free"  readonly  name="retentionValue" description="<s:text name='undwrt.pages.undwrtDeal.retentionMoney'/>"/></td>
										<% if (strClassCode.equals("27") && includeAccident.equals("Y")) {  %>
										<td>
											<input class="free"  name="speCurrency" description="<s:text name='undwrt.CommonDangerUnits.PMLCurrencyKind'/>" value=""/></td>
										<td>
											<input class="free"  name="speValue" description="<s:text name='undwrt.CommonDangerUnits.PMLValue'/>" value="" format="0.00"/></td>
										<% } %>
										<td colspan="2">
											<input class="free" readonly name="currency" description="<s:text name='undwrt.CommonDealContentQta.currency'/>"/></td>					 
										<td colspan="2">
											<input class="free"  readonly name="amount" description="<s:text name='undwrt.CommonDealContentQta.insureAmout'/>" 
												onblur="checkNumber(this)"/></td>
										<td style="display: none">
											<input class="free" name="chgAmount" description="<s:text name='undwrt.CommonReinsSimulate.changePolicyAmount'/>" 
												onblur="checkNumber(this)"/></td>
										<td style="display: none">
											<input class="free"  readonly name="premium" description="<s:text name='undwrt.EndorseDangerUnits.policyFee'/>" 
												onblur="checkNumber(this)"/></td>
										<td style="display: none">
											<input class="free" name="chgPremium" description="<s:text name='undwrt.EndorseDangerUnits.changeProtectFee'/>" 
												onblur="checkNumber(this)"/></td>
			  						  	<td>
			  						  		<input class="free"  readonly name="dangerShare" description="<s:text name='undwrt.EndorseDangerUnits.occupyScale'/>" 
			  						  			onblur="checkNumber(this)"/></td>
			  						  	<td rowspan ="3">
											<%--详细信息 --%>
											<input type=button name="buttonShowItem" class=button 
												onclick="showEndorseDangerItem(this,'DangerUnit','0');" 
												value="<s:text name='undwrt.pages.undwrtDeal.detailedInformation'/>" 
												style="cursor: hand" <%= "4".equals(request.getParameter("iNodeNo"))?"disabled":""%>/>
		    						    	<input type=hidden name="isSavaDangerUnit" value="N"/></td>
										<td  rowspan ="3">
											<input type=button name="buttonDelete" class=smallbutton 
												onclick="deletePdangerInfo(this,'DangerUnit');" value="-" style="cursor: hand"/></td>
									</tr>
									
		    						<tr class=common>
		    							<td>
		    						   		<%--险种名称 --%>
		    						    	<s:text name="undwrt.EndorseDangerUnits.riskName"/></td>
							           	<td>
							           		<%--描述 --%>
							            	<s:text name="undwrt.EndorseDangerUnits.describe"/></td>
							         	<td colspan="3">
							           		<%--地址 --%>
							             	<s:text name="undwrt.EndorseDangerUnits.address"/></td>
							          	<td colspan="3">
							          		<%--除外责任/申报业务 --%>
							           		<s:text name="undwrt.EndorseDangerUnits.exDutyApplyBusiness"/></td>
		                       			<td>
		                       				<%--进合约 --%>
		                       				<s:text name="undwrt.EndorseDangerUnits.intoContract"/></td>		
				                 	</tr>
				                 	
		    						<tr class=common>   
		    							<td>
		    								<input type="hidden" name="eRiskCode"  description="<s:text name='undwrt.pages.undwrtDeal.riskCode'/>"/>
		    						    	<input class="free" name="riskName" description="<s:text name='undwrt.CommonDangerUnits.riskName'/>"/>
		    						 	</td>           
		    						  	<td>
		    						    	<input class="free" name="dangerDesc" description="<s:text name='undwrt.page.riskUnitDescribe'/>"/>
		    						    </td>
										<td colspan="3">
											<input class="free" name="dangerAddress" description="<s:text name='undwrt.page.addressDescribe'/>">
										</td>
			  							<td colspan="3">
											<input class="free" type="hidden" name="dangerItemKind" description="<s:text name='undwrt.pages.undwrtDeal.exceptResponsibility'/>"/>
											<input class="free" name="dangerItemKindName" readonly>
			  						  	</td>
		    							<td>
			    							<input type="checkbox" align="center" name="dangerItemFlag" 
			    								description="<s:text name='undwrt.pages.undwrtDeal.intoContract'/>" value="">
			    							<input type="hidden" name="hiDangerItemFlag">
											<input type="hidden" name="dangerBusinessNature" >
											<input type="hidden" name="dangerChannelType" >
										 	<input type="hidden" name="dangerCartypeCode"  >
											<input type="hidden" name="dangerExchRateCNY" >  
		    							</td>  
									</tr>	  
				   					</table>
				   					</td>
				   				</tr>
		       				</tbody>
		      				</table>
		      				</span>
		      				
							<span  id="spanDangerUnit" style="display:" cellspacing="1" cellpadding="0">
		  					<table class="common" cellpadding="5" cellspacing="1" align="center" id="DangerUnit" style="width: 100%">
					   		<thead>
								<tr class=listtitle>
									<td>
										<%--批单风险评估信息 --%>
										<s:text name="undwrt.EndorseDangerUnits.approvalBillRiskAccessInfo"/>
									</td>
							  	</tr>	  
							</thead>
							
			   				<tfoot>
			   				<%
									if (!riskUnitFlag.equals("") && riskUnitFlag.equals("1")) {
							%>
		       					<tr class=common>
			        				<td align=left>
					        			<%--按"+"号键增加危险单位信息，按"-"号键删除信息 --%>
					        			<s:text name="undwrt.EndorseDangerUnits.pressAddMarkEnhanceDangerUnitInfoOrElseSo"/>
				         				<div align="right">
				          					<input type="button" class=smallbutton value="+" 
				          						onclick="insertRow('DangerUnit');return showEndorseDangerItem(this,'DangerUnit','NewDangerNo');" 
				          						name="buttonInsert" style="cursor: hand">
				         				</div>
			       					</td>
		       					</tr>
		       					<%
									}
								%>
		    				</tfoot>
		    				
		      				<tbody>
		      				<s:if test='#request.DangerDetail!=null'>
		      				<s:iterator id="DangerDetail" status="statu" value="#request.DangerDetail">
					        <tr class=common>
					     		<td>
					     		<table class="common" style="width:100%" cellspacing="1" cellpadding="1">
					         		<tr class=common>
					         		<td width='1%'></td>
						             	<td width='5%'>
						             		<%--序号 --%>
						             		<s:text name="undwrt.EndorseDangerUnits.serialNo"/></td>
									 	<td width='8%'>
									 		<%--风险等级 --%>
									 		<s:text name="undwrt.EndorseDangerUnits.riskLeve1"/></td>
									 	<td width='12%'>
									 		<%--风险名称 --%>
									 		<s:text name="undwrt.EndorseDangerUnits.riskName"/></td>
									 	<td width='5%' colspan="2">
									 		<%--自留额 --%>
									 		<s:text name="undwrt.EndorseDangerUnits.autoRemainAmount"/></td>
									 	<% if (strClassCode.equals("27") && includeAccident.equals("Y")) {  %>
									 	<td width='5%' colspan='2'>
									 		<%--意健险PML值 --%>
									 		<s:text name="undwrt.EndorseDangerUnits.accidentHealthPMLValue"/></td>
									 	<% } %>
									 	<td width='5%' colspan="2">
									 		<%--币别 --%>
									 		<s:text name="undwrt.EndorseDangerUnits.currency"/></td>
									 	<td width='8%' colspan="2">
									 		<%--原始保额 --%>
									 		<s:text name="undwrt.EndorseDangerUnits.originalProtectAmount"/></td>
									 	<td width='8%' style="display:none">
									 		<%--变化保额 --%>
									 		<s:text name="undwrt.EndorseDangerUnits.changeProtectAmount"/></td>
									 	<td width='8%' style="display:none">
									 		<s:text name="undwrt.EndorseDangerUnits.originalProtectFee"/></td>
									  	<td width='8%' style="display:none">
									  		<%--变化保费--%>
									  		<s:text name="undwrt.EndorseDangerUnits.changeProtectFee"/></td>
									 	<td width='5%'>
									 		<%--占比 --%>
									 		<s:text name="undwrt.EndorseDangerUnits.occupyScale"/>%</td>
									 	<td width='10%'>
									 		<%--子信息--%>
									 		<s:text name="undwrt.EndorseDangerUnits.sonInfo"/></td>
						       			<td width='5%'>*</td>
									</tr>
									
									<tr class=common>
										<!-- 商火需求在危险单位前加入单选框，被选中的危险单位可以进行临分20140306 -->
										<td rowspan="3">
											<s:if test='"F01"==iRiskCode'>
												<s:if test="#dangerDetail.hasEnquiry">
	    									 		<input name="facing" type="checkbox" checked onclick="ChangeToValue(this,facing)">
	    									 		<input type="hidden" name="whetherFacing" value="1">
	    									 	</s:if>
	    										<s:else>
	    											<input name="facing" type="checkbox"  onclick="ChangeToValue(this,facing)">
	    									 		<input type="hidden" name="whetherFacing" value="0">
	    									 	</s:else>
	    									</s:if>
										</td>
										<td rowspan ="3">
											<input class="free" readonly name="dangerNo" description="<s:text name='undwrt.pages.undwrtDeal.serialNo'/>" 
												value="<s:property value="#DangerDetail.dangerNo"/>">
										</td> 
									  	<td>
									  		<input class="free"  name="riskLevel" description="<s:text name='undwrt.CommonDangerUnits.dangerLevel'/>" 
									  			value="<s:property value="#DangerDetail.riskLevel"/>"></td>
									  	<td>
									  		<input class="free"   readonly name="riskLevelDesc" description="<s:text name='undwrt.pages.undwrtDeal.riskGradeDescribe'/>" 
									  			value="<s:property value="#DangerDetail.riskLevelDesc"/>"></td>
									  	<td colspan="2">
											<input type="hidden" name="dangerBusinessNature" value="<s:property value="#DangerDetail.businessNature"/>">
								            <input type="hidden" name="dangerChannelType" value="<s:property value="#DangerDetail.channelType"/>">
								            <input type="hidden" name="dangerCartypeCode" value="<s:property value="#DangerDetail.cartypeCode"/>">
								            <input type="hidden" name="dangerExchRateCNY" value="<s:property value="#DangerDetail.exchRateCNY"/>">		  
											<input class="free" type='hidden'  readonly name="retCurrency" 
												description="<s:text name='undwrt.CommonDangerUnits.autoAmountCurrency'/>"  
												value="<s:property value="#DangerDetail.retCurrency"/>">
											<input class="free" width="80%"readonly  name="retentionValue"  description="<s:text name='undwrt.pages.undwrtDeal.retentionMoney'/>" 
												value="<fmt:formatNumber value="${retentionValue}" pattern="0.00"/>"/></td>
								     	<% if (strClassCode.equals("27") && includeAccident.equals("Y")) {  %>
									  	<td>
									  		<input class="free"  name="speCurrency" description="<s:text name='undwrt.CommonDangerUnits.PMLCurrencyKind'/>" 
									  			value="<s:property value="#DangerDetail.speCurrency"/>"></td>
									  	<td>
									  		<input class="free"  name="speValue" description="<s:text name='undwrt.CommonDangerUnits.PMLValue'/>" 
									  			value="<fmt:formatNumber value="${speValue}" pattern="0.00"/>"/></td>
									  	<% } %>
									  	<td colspan="2">
									  		<input class="free" readonly name="currency" description="<s:text name='undwrt.CommonDealContentQta.currency'/>" 
									  			value="<s:property value="#DangerDetail.currency"/>"/></td>
									  	<%--//modify by yanglibo 20080826 begin 增加千分位控制 20080828 去掉千分位--%>
									  	<td colspan="2">
									  		<input class="free" readonly name="amount"  description="<s:text name=''/>" 
									  			value="<fmt:formatNumber value="${amount}" pattern="0.00"/>" 
									  			onblur="checkNumber(this)"/></td>
									  	<td style="display: none">
									  		<input class="free" readonly name="chgAmount"  description="<s:text name='undwrt.CommonReinsSimulate.changePolicyAmount'/>" 
									  			value="<fmt:formatNumber value="${chgAmount}" pattern="0.00"/>"/></td>
									  	<td style="display: none">
									  		<input class="free" readonly name="premium" readonly description="<s:text name='undwrt.EndorseDangerUnits.policyFee'/>" 
									  			value="<fmt:formatNumber value="${premium}" pattern="0.00"/>" onblur="checkNumber(this)"/></td>
								      	<td style="display: none">		    		
								    		<input class="free" readonly name="chgPremium" readonly description="<s:text name='undwrt.EndorseDangerUnits.changeProtectFee'/>" 
								    			value="<fmt:formatNumber value="${chgPremium}" pattern="0.00"/>"/></td>
						         		<%--//modify by yanglibo 20080826 end 增加千分位控制--%>
						        		<td>
						        			<input class="free" readonly name="dangerShare" description="<s:text name='undwrt.EndorseDangerUnits.occupyScale'/>" 
						        				value="<s:property value="#DangerDetail.dangerShare"/>" 
						        				onblur="checkNumber(this)"/></td>
						        		<td rowspan ="3">
						        		<s:if test='iNodeStatus !="4" && iNodeStatus !="0" '>
									    	<input type=button name="buttonShowItem" class=button 
									    		onclick="showEndorseDangerItem(this,'DangerUnit');" 
									    		value="<s:text name='undwrt.pages.undwrtDeal.detailedInformation'/>" 
									    		style="cursor: hand"/>
						          			<input type=hidden name="isSavaDangerUnit" value="N"/>
						        		</s:if></td>
										<td rowspan ="3">
						      				<input type=button name="buttonDelete" class=smallbutton 
						      					onclick="deletePdangerInfo(this,'DangerUnit');" value="-" style="cursor: hand">
										</td>	
									</tr>
							
							    	<tr class=common>
										<td>
											<%--险种名称 --%>
									       	<s:text name="undwrt.EndorseDangerUnits.riskName"/></td>
										<td >
											<%--描   述 --%>
										   	<s:text name="undwrt.EndorseDangerUnits.describe"/></td>
										<td colspan="4">
										   	<%--地  址 --%>
										   	<s:text name="undwrt.EndorseDangerUnits.address"/></td>
										<td colspan="2">
											<%--除外责任/申报业务--%>
										   	<s:text name="undwrt.EndorseDangerUnits.exDutyApplyBusiness"/></td>
										<td>
									   		<%--进合约 --%>
									        <s:text name="undwrt.EndorseDangerUnits.intoContract"/></td>		
									</tr>
									
									<tr class=common>
										<td>
								    		<input type="hidden" name="eRiskCode"  description="<s:text name='undwrt.pages.undwrtDeal.riskCode'/>" 
						         				value="<s:property value="#DangerDetail.riskCode"/>"/>
						         			<input class="free" name="riskName" value="<s:property value="#DangerDetail.riskName"/>" 
						         				description="<s:text name='undwrt.pages.undwrtDeal.riskcName'/>"/></td>
									    <td>
											<input class="free" name="dangerDesc" description="<s:text name='undwrt.page.riskUnitDescribe'/>" 
												value="<s:property value="#DangerDetail.dangerDesc"/>"/></td>
									  	<td colspan="4">
									  		<input class="free" name="dangerAddress" description="<s:text name='undwrt.ShowDangerItem.address1'/>" 
									  			value="<s:property value="#DangerDetail.addressName"/>"/></td>
									  	<td colspan="2">
									  		<input class="free" type="hidden" name="dangerItemKind" description="<s:text name='undwrt.ShowDangerItem.exDuty'/>"
										 			value="<s:property value="#DangerDetail.itemKind"/>"/>
											<input type="test" name="dangerItemKindName" readonly class="free" 	
													value="<s:property value="#DangerDetail.itemKindDesc"/>"/></td> 
									  	<td>
										    <input type="checkbox" name="dangerItemFlag" description="<s:text name='undwrt.CommonDangerUnits.enterConract'/>" 
										    	<s:if test="#DangerDetail.flag ==10">checked</s:if>
										    	<s:if test="#DangerDetail.flag ==11">checked</s:if>
										       	value="<s:property value="#DangerDetail.flag"/>">
										    <input type="hidden" name="hiDangerItemFlag" value="00"/></td> 
									</tr>
							 	</table>
								</td>
							</tr>
		        			</s:iterator>
		        			</s:if>
		     				</tbody>
		   					</table>
		   					</span>
		    			</td>
		     		</tr>
		    		</table>
		   			</span>
		  		</td>
			</tr>
			
			<tr width=100%>
				<td>
					<table width=100% border="0">
			    		<tr>
					 		<s:if test='editType=="query"'>
				            <td class=button width="33%">
				           		<%--风险评估信息 --%>
				                <input type="button" class="longbutton" name="allEvaluate" description="<s:text name='undwrt.CommonDangerUnits.riskAssessInfo'/>"
				                 	value="<s:text name='undwrt.CommonDangerUnits.riskAssessInfo'/>" onclick="showEvaluateRiskInfo(this)">
				            </td>
				          	</s:if>
		            		<td class=button width="33%">
		            			<%--分批试算 --%>
		            	 		<Input name="ReinsTrial" type="button" class=button 
		            	 			value="<s:text name='undwrt.CommonDangerUnits.divideBatchTestAccount'/>" 
		            	 			onclick="endorseSimulateReinsByDanger()"  <s:if test='editType=="query"'>disabled</s:if>/>
		             			<s:if test="${AmountAndPremiumDto != null}">
						  			<input type="hidden" name="tolAmount" value="<s:property value="${AmountAndPremiumDto.amount }"/>">
			              			<input type="hidden" name="tolPremium" value="<s:property value="${AmountAndPremiumDto.premium }"/>">
		             			</s:if>
			        			<input type="hidden" name="endorNo" value="${businessNo}">
			        			<input type="hidden" name="policyNo" description="<s:text name='undwrt.CommonDangerUnits.PolicyNo'/>" 
			        				value="<s:property value="#request.PrpCmainDto.policyNo"/>">
			        			<s:if test='businessFlag=="1"'>
					       			<%--提交分入确认 --%>
					       			<Input name="ReinsTrial" type="button" class=longbutton value="<s:text name='undwrt.pages.undwrtDeal.submitPointsAffirm'/>" 
					       				onclick="reinsPolicyVerify(endorNo)">
								</s:if>
		           			</td>
			       			<td class=button width="34%">
			       			<s:if test='iNodeStatus != "4" && iNodeStatus != "0" '>
				       			<%--临分意向 --%>
			            		<Input name="butSubmitReins" class="button" type="button" alt="<s:text name='undwrt.pages.undwrtDeal.partIntention'/>" 
			            			value="<s:text name='undwrt.pages.undwrtDeal.partIntention'/>" onclick="submitReins()" 
			            			<s:if test='editType=="query"'>disabled</s:if>/>
		           			</s:if>
		           			<s:if test='iNodeStatus=="4" || iNodeStatus=="0"'>
		           				<%--返回继续处理 --%>
		           				<input type="button" class="longbutton" value="<s:text name='undwrt.pages.undwrtDeal.backContinueDispose'/>" 
		           					onclick="history.back(-1);">            	
		        			</s:if>
		           			</td>
		           			<td class=button width="33%">
								<s:if test='sameRiskFlag == "1"'>
									<Input name="butSubmitReins" class="longbutton" type="button" alt="<s:text name='undwrt.pages.undwrtDeal.partIntention'/>"
										value="<s:text name='undwrt.pages.undwrtDeal.sameRiskFlag'/>"
										onclick="similarRiskInfo('<s:property value="iBusinessNo" />','<s:property value="iBusinessType" />');" />
								</s:if> 
					 		</td>
		         		</tr>
		        	</table>
			   	</td>
			</tr>
		</s:if>
	</table>
