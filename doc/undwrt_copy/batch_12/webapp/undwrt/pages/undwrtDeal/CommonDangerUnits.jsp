<!--***************************************************************************
* Description: 拆分危险单位页面
****************************************************************************-->
<%@ include file="/common/taglibs.jsp"%>

<%@page import="com.sinosoft.sysframework.reference.DBManager"%>
<%@page import="com.sinosoft.platform.ui.control.action.UIPrpDriskConfigAction"%>
<%@page import="com.sinosoft.platform.dto.domain.PrpDriskConfigDto"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Collection"%>
<%@page import="com.sinosoft.claim.dto.domain.PrpLchargeDto"%>
<%@page import="com.sinosoft.common.schema.model.PrpTmain"%>
<%@page import="com.sinosoft.prpall.dto.domain.*"%>
<%@page import="com.sinosoft.prpall.dto.domain.PrpCmainDto"%>
<%@page import="com.sinosoft.undwrt.common.vo.CommonDangerItemInfoVo"%>
<%@page import="com.sinosoft.undwrt.common.vo.CommonAmountAndPremiumVo"%>
<%@page import="com.sinosoft.utility.UtiPower"%>
<%@page import="com.sinosoft.utility.SysConfig"%>
<%@page import="com.sinosoft.platform.dto.domain.PrpDuserDto"%>
<%@page import="com.sinosoft.sysframework.common.Constants"%>
<%@page import="com.sinosoft.platform.ui.control.action.UIPowerAction"%>
<%@page import="java.text.*"%>
<%@page import="java.util.*"%>
<%@page import="com.sinosoft.utility.string.*"%>
<%@page import="com.sinosoft.utility.string.ChgDate"%>
<%@page import="com.sinosoft.undwrt.pub.InternationalizationUtil"%>
<%@page import="com.sinosoft.prpall.ui.model.UtiPrintPageFindByConditionsCommand"%>
<%@page import="com.sinosoft.dimension.*"%>
<%@page import="com.sinosoft.utiall.blsvr.BLPrpDconfigCode"%>
<%@page import="com.sinosoft.reins.in.bl.facade.BLPrpReinsVerifyFacade"%>
<%@page import="com.sinosoft.reins.in.dto.domain.PrpReinsVerifyDto"%>
<%@page import="com.sinosoft.platform.bl.facade.BLPrpDpreauditConfigFacade"%>
<%@page import="com.sinosoft.undwrt.common.model.*"%>

<%
		//是否续保业务标记zhutq
		boolean isRenewal = (Boolean) request.getAttribute("isRenewal");
		String oldPolicyNo = "";
		if (isRenewal) {
			oldPolicyNo = (String) request.getAttribute("oldPolicyNo");
		}

		java.text.DecimalFormat decimalFormat1 = new java.text.DecimalFormat(
				"#,##0.00");
		java.text.DecimalFormat decimalFormat = new java.text.DecimalFormat(
				"0.00");
		java.text.DecimalFormat gradeFormat = new java.text.DecimalFormat(
				"0.000");
		String riskUnitFlag = ""; //是否拆分危险单位标识
		String requiredReins = ""; //是否强制分保试算
		String reinsOfflineFlag = ""; //是否离线计算
		String strsumAmount = "";
		String strsumPremium = "";
		boolean allowSpecial = false; //是否允许录入特殊因子
		boolean allowSpecial_user = false; //操作员是否有录入特殊因子的权限
		String businessNo = request.getParameter("iBusinessNo");
		String businessType = request.getParameter("iBusinessType");
		boolean blnIsPreaudit = false;
		boolean blnSplitDangerUnit = false;
		String comCode = (String) session.getAttribute("myComCode");
		String userCode = (String) session.getValue("myUserCode");
		String strGoalInsuredFlag = "0";
		String strComCode = "";
		double profitRate = 0.00; //优惠
		double disRate = 0.00; //手续费
		String color = ""; //用于高亮显示超过15%的手续费
		String strLanguage = ""; //取语言种类
		String strRiskCodeCI = ""; //交强险险种代码
		String strBusinessNoCI = ""; //关联交强险投保单号
		PrpTmain prpTmainSubDto = new PrpTmain();
		UIPrpDriskConfigAction uiPrpDriskConfigAction = new UIPrpDriskConfigAction();
		PrpDriskConfigDto prpDriskConfigDto = new PrpDriskConfigDto();
		prpDriskConfigDto = uiPrpDriskConfigAction.queryRiskConfig(comCode,
				riskCode, "RISK_UNIT_FLAG");
		BLPrpDconfigCode blPrpDconfigCode = new BLPrpDconfigCode();
		//add by zhaoning20091109 begin Reason:除了预处理岗，其他双核级别都具有拆分危险单位的操作权限
		BLPrpDpreauditConfigFacade blPrpDpreauditConfigFacade = new BLPrpDpreauditConfigFacade();
		blnIsPreaudit = blPrpDpreauditConfigFacade.getIsPreaudit(
				businessNo, businessType, request.getParameter("iModelNo"),
				request.getParameter("iNodeNo"));
		blnSplitDangerUnit = blPrpDpreauditConfigFacade
				.getAllowSplitDangerUnit(businessNo, businessType);
		//add by zhaoning20091109 end
		ChgDate nowDate = new ChgDate();
		String strNotifyPath = "";

		if (prpDriskConfigDto != null
				&& prpDriskConfigDto.getConfigValue().equals("1")
				&& comCode.substring(0, 2).equals("00")) {
			riskUnitFlag = "1";
		} else {
			riskUnitFlag = "0";
		}
		//add by zhaoning20091109 begin Reason:除了预处理岗，其他双核级别都具有拆分危险单位的操作权限
		if (prpDriskConfigDto != null && !blnIsPreaudit
				&& blnSplitDangerUnit) {
			riskUnitFlag = "1";
		}
		//add by zhaoning20091109 end
		InternationalizationUtil internal = new InternationalizationUtil();
		System.out.println(internal
				.getText("undwrt.pages.undwrtDeal.splitRiskUnit")
				+ riskUnitFlag);
		prpDriskConfigDto = uiPrpDriskConfigAction.queryRiskConfig(comCode,
				riskCode, "REQUIRED_REINS");
		if (prpDriskConfigDto != null
				&& prpDriskConfigDto.getConfigValue().equals("1")) {
			requiredReins = "1";
		} else {
			requiredReins = "0";
		}
		//特殊因子录入开关
		prpDriskConfigDto = uiPrpDriskConfigAction.queryRiskConfig(comCode,
				riskCode, "ALLOW_SPECIALPREMIUM_POLICY");
		if (prpDriskConfigDto != null
				&& prpDriskConfigDto.getConfigValue().equals("1")) {
			allowSpecial = true;
		} else {
			allowSpecial = false;
		}
		//承保录入特殊因子权限校验
		//add xuning gpic 10061016
		double FACLEVEL = Double.parseDouble(SysConfig
				.getProperty("FACLEVEL"));
		PrpDuserDto user = (PrpDuserDto) (session.getAttribute("user"));
		allowSpecial_user = UIPowerAction.checkPowerReturn(user,
				"prpall.policy.middlecost");
		//modify by zhulei 20060419 end 权限改造

		//是否显示特殊因子项
		String strDisplay = "none"; //默认不显示
		//只有特殊因子开关打开并且该人员具有承保中间成本的写权限时才允许显示特殊因子修改项。
		if ((allowSpecial) && (allowSpecial_user)) {
			strDisplay = "";
		}
		/* modify by xiaojian 20060304_1 begin reason：非“非联共保”不允许修改特殊因子 */
		String className = "";
		String coinsFlag = "";
		/* modify by xiaojian 20060304_1 end */
		//add begin by zhaijq 0060414 2799纯意外险不允许输入PML值
		String includeAccident = "Y";
		if (riskCode.equals("2799")) {
			includeAccident = "N";
			Collection prpItemKindList = (Collection) request
					.getAttribute("ItemKind");
			if (prpItemKindList != null && prpItemKindList.size() > 0) {
				Iterator iterator = prpItemKindList.iterator();
				while (iterator.hasNext()) {
					CommonDangerItemInfoVo commonDangerItemInfoDto = (CommonDangerItemInfoVo) iterator
							.next();
					if (!commonDangerItemInfoDto.getKindCode()
							.substring(0, 1).equals("2")) {
						includeAccident = "Y";
						break;
					}
				}
			}
		}
		//add end by zhaijq 20060414
	%>
<script language='javascript'>
    function Print(Flag)
    {
      var strURL;
      var strTypeFlag;
      var strBizNo = "<%=businessNo%>";
      if(strBizNo=="")
      {
        errorMessage("<s:text name='undwrt.pages.undwrtDeal.CommonDangerUnits.alert'/>");
        return false;
      }
      var prpallIP = fm.PrpallIp.value;
      //保单打印
      if (Flag=="11")  //保单抄件正本打印
      {
      	  strURL = prpallIP+"/prpall/<%=riskCode%>/tb/UIProposal<%=riskCode%>NoneFormatPrint.jsp?BizNo="+strBizNo+"&EDITTYPE=MASTER&NotEdit=0";
      } 
      if (Flag=="61"){
        strURL = prpallIP+"/prpall/commonship/pub/UIEditReportPrint.jsp?BizNo=" + strBizNo + "&BizType=0";
      }
      if (Flag=="62"){
        strURL = prpallIP+"/prpall/commonship/pub/UIEditReportPrint.jsp?BizNo=" + strBizNo + "&BizType=0&PrintType=1";
      }    //aded by LanNing end 20070813 意外险团体清单打印
    
    strURL += "&FlagForPrint=" + Flag;
    printWindow(strURL,"<s:text name='undwrt.pages.undwrtDeal.print'/>");
 	
 	}
 	
 	//显示打印窗口

function printWindow(strURL,strWindowName)

{

  var pageWidth=screen.availWidth-10;

  var pageHeight=screen.availHeight-30;

  if (pageWidth<100 )

    pageWidth = 100;



  if (pageHeight<100 )

    pageHeight = 100;



  var newWindow = window.open(strURL,strWindowName,'width='+pageWidth+',height='+pageHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1.resizable=1,status=0');

  newWindow.focus();

  return newWindow;

}
//zhutq 点击查看上张保单赔案信息按钮
function checkLastPolicyClaimInfoList(){
 	var strURL = "/undwrt/common/LastPolicyClaimInfoList.jsp?oldPolicyNo=<%=oldPolicyNo%>&pageNo=1";    
  	window.open(strURL,'<s:text name="undwrt.pages.undwrtDeal.CommonDangerUnits.open"/>','top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}
</script>

<tr class=common>		
	<td colspan="4">
		<%--查看业务详细信息ip --%>
		<input type="hidden" name="PrpallIp" value="${iPrpallIp }">
		<input type="hidden" name="reinsIP" value="${reinsIP }">
		<input type="hidden" name="hiBusinessNo" value="<%=businessNo%>">
		<input type="hidden" name="hiBusinessType" value="<%=businessType%>">
		<%--是否需要拆分危险单位标志1为允许--%>
		<input type="hidden" name="riskUnitFlag" value="<%=riskUnitFlag%>">
		<%--是否强制分保试算--%>
		<input type="hidden" name="requiredReins" value="<%=requiredReins%>">
		<input type="hidden" name="hiRiskLevel" value="">
		<input type="hidden" name="hiRetCurrency" value="">
		<input type="hidden" name="hiRetentionValue" value="">
		<input type="hidden" name="hiDangerItemKind" value="">
		<input type="hidden" name="hiDangerFlag" value="">
		<input type="hidden" name="hiRiskLevelDesc" value="">
		<input type="hidden" name="includeAccident" value="<%=includeAccident%>">
		
	<%
		String strClassCode = "";
		//提交分入确认
		String businessFlag = "";
		String verifyFlag = "0";
		//免导团单查看详细信息时，调用自己的页面
		String strPolicySort = "";
		if (request.getAttribute("PrpTmainDto") != null) {
			//增加千分位
			PrpTmain prpMainDto = (PrpTmain) request
					.getAttribute("PrpTmainDto");
			strClassCode = prpMainDto.getClassCode();
			strsumAmount = prpMainDto.getSumAmount() + "";
			strsumAmount = decimalFormat1.format(Double.parseDouble(ChgData
					.chgStrZero(strsumAmount)));
			strsumPremium = prpMainDto.getSumPremium() + "";
			strsumPremium = decimalFormat1.format(Double
					.parseDouble(ChgData.chgStrZero(strsumPremium)));

			//免导团单查看详细信息时，调用自己的页面
			strPolicySort = prpMainDto.getPolicySort();

			strComCode = prpMainDto.getComCode();
			strClassCode = prpMainDto.getClassCode();
			if(prpMainDto.getDiscount()!=null)
			{
			profitRate = prpMainDto.getDiscount().doubleValue() * 100;
			}
			if(prpMainDto.getDisRate()!=null)
			{
			disRate = prpMainDto.getDisRate().doubleValue();
			}
			if (disRate > 15) {
				color = "red";
			}
			businessFlag = prpMainDto.getBusinessflag();
			strLanguage = prpMainDto.getLanguage();
		} else if (request.getAttribute("PrpCmainDto") != null) {
			//增加千分位
			PrpCmainDto prpMainDto = (PrpCmainDto) request
					.getAttribute("PrpCmainDto");
			strClassCode = prpMainDto.getClassCode();
			strsumAmount = prpMainDto.getSumAmount() + "";
			strsumAmount = decimalFormat1.format(Double.parseDouble(ChgData
					.chgStrZero(strsumAmount)));
			strsumPremium = prpMainDto.getSumPremium() + "";
			strsumPremium = decimalFormat1.format(Double
					.parseDouble(ChgData.chgStrZero(strsumPremium)));

			//免导团单查看详细信息时，调用自己的页面
			strPolicySort = prpMainDto.getPolicySort();
			strComCode = prpMainDto.getComCode();
			strClassCode = prpMainDto.getClassCode();
			profitRate = prpMainDto.getDiscount() * 100;
			disRate = prpMainDto.getDisRate();
			if (disRate > 15) {
				color = "red";
			}
			businessFlag = prpMainDto.getBusinessFlag();
			strLanguage = prpMainDto.getLanguage();
		}
		if (request.getAttribute("PrpTmainSubDto") != null) {
			prpTmainSubDto = (PrpTmain) request
					.getAttribute("PrpTmainSubDto");
			strRiskCodeCI = prpTmainSubDto.getRiskCode();
			strBusinessNoCI = prpTmainSubDto.getProposalNo();
		}
		if (prpTmainSubDto != null) {
	%> <input type="hidden" name="riskCodeCI"
		value=<%=strRiskCodeCI%>> <input type="hidden"
		name="businessNoCI" value=<%=strBusinessNoCI%>> <%
 	}
 %> <input type="hidden" name="businessFlag"
		value=<%=businessFlag%>> <!-- 分入业务临分意向前要先再保分入确认完成  --> <%
 	if(businessFlag.equals("1")){
 	 BLPrpReinsVerifyFacade blPrpReinsVerifyFacade =new BLPrpReinsVerifyFacade();
 	 PrpReinsVerifyDto   prpReinsVerifyDto     = new PrpReinsVerifyDto();
 	 prpReinsVerifyDto = blPrpReinsVerifyFacade.findByPrimaryKey(businessNo);
 	 if(prpReinsVerifyDto !=null && !prpReinsVerifyDto.getReinsState().equals("1")){
 	 verifyFlag = "1";
 	 }else if(prpReinsVerifyDto==null){
 	 verifyFlag = "2";
 	 }else{
 	 verifyFlag= "0";
 	 }
 	 }
 %> <input type="hidden" name="verifyFlag" value="<%=verifyFlag%>">
	<span id="spanInfo">

	<table width=100%>
		<!--投保单信息-->
		<tr>
		<td width="100%">
			<table cellpadding="5" cellspacing="1" class="common" align="center" style="width: 100%">
				<s:if test="#request.PrpTmainDto != null">
					<tr class=listtitle>
						<td colspan="4">
							<%--投保单摘要信息 --%>
							<s:text name="undwrt.pages.undwrtDeal.insureBillModelMessages"/>
						</td>
					</tr>
					<tr>
						<td class=title4>
							<%--险种 --%>
							<s:text name="riskName"/>：
						</td>
						<td class=input4>
						<s:if test="#request.PrpTmainDto.rationCode == 'OP'">
						<s:property value="#request.PrpTmainDto.rationCode" />
						</s:if>
						<s:else>
					     <s:property value="#request.PrpTmainDto.riskCode" />
						</s:else>
						</td>
						<td class=title4>
							<%--归属机构 --%>
							<s:text name="comOfRemoteOrg"/>：
						</td>
						<td class=input4>
							<s:property value="#request.PrpTmainDto.comCode" />
						</td>
					</tr>
					<tr>
						<td class=title4>
							<%--险种名称 --%>
							<s:text name="undwrt.pages.undwrtDeal.riskcName"/>：
						</td>
						<td class=input4><s:property value="riskCName" /></td>
						<td class=title4>
							<%--归属机构名称 --%>
							<s:text name="undwrt.pages.undwrtDeal.comOfRemoteOrgName"/>：
						</td>
						<td class=input4>
							<%=request.getAttribute("comCName")%>
						</td>
					</tr>
					<tr class="mline">
						<td class=title4>
							<%--投保单号 --%>
							<s:text name="undwrt.pages.undwrtDeal.insureBillNo"/>：
						</td>
						<td class=input4>
							<s:property value="#request.PrpTmainDto.proposalNo" />
							<input type="hidden" name="proposalNo" description="<s:text name='undwrt.pages.undwrtDeal.insureBillNo'/>"
								value="<s:property value="#request.PrpTmainDto.proposalNo"/>">
						</td>
						<s:if test='"A01"!=iRiskCode && "B01"!=iRiskCode'>
							<%-- <td class=title4>
								被保險人近三年是否有賠案
								<s:text name="undwrt.pages.undwrtDeal.lastThreeYearsClaims"/>： 
								<input type="hidden"
										name="riskCode" description="<s:text name='undwrt.pages.undwrtDeal.riskCode'/>"
										value="<s:property value="#request.PrpTmainDto.riskCode"/>">
								<input type="hidden" name="hiClassCode" description="<s:text name='undwrt.CommonDangerUnits.riskTypeCode'/>"
										value="<s:property value="#request.PrpTmainDto.classCode"/>">
							</td>
							<s:if test='existClaim'>
								<td class=input4>
									<font color='red'>
									有
										<s:text name="undwrt.have"/>
									</font>&nbsp; 
									<Input type="button" class="longbutton" name="claimInfo"
										value="<s:text name='undwrt.pages.undwrtDeal.lastThreeYearsClaimsInfo'/>" style="color: red"
										onclick="checkLastPolicyClaimInfoList();">
								</td>
							</s:if>
							<s:else>
								<td class=input4><font color='red'>
									无
									<s:text name="undwrt.not"/></font>
								</td>
							</s:else> --%>
							
							<%-- <s:if test="%{businessType == 'T'}"> --%>
							<%
								if("T".equals(businessType)){
							%>
								<td class=title4>
									<input type="hidden" name="riskCode" description="<s:text name='undwrt.pages.undwrtDeal.riskCode'/>"
											value="<s:property value="#request.PrpTmainDto.riskCode"/>">
									<input type="hidden" name="hiClassCode" description="<s:text name='undwrt.CommonDangerUnits.riskTypeCode'/>"
											value="<s:property value="#request.PrpTmainDto.classCode"/>">
									<s:text name="undwrt.CommonDangerUnits.insuredIsClaim"/>：
								</td>
								<td class=input4>${isClaim }</td>	
							<%
								}else{
							%>
								<td class=title4 colspan="2">
									<input type="hidden" name="riskCode" description="<s:text name='undwrt.pages.undwrtDeal.riskCode'/>"
											value="<s:property value="#request.PrpTmainDto.riskCode"/>">
									<input type="hidden" name="hiClassCode" description="<s:text name='undwrt.CommonDangerUnits.riskTypeCode'/>"
											value="<s:property value="#request.PrpTmainDto.classCode"/>">
								</td>
							<%
								}
							%>
						</s:if>
						<s:else>
							<td class=title4>
								<%--上一保单是否有赔案 --%>
								<s:text name="undwrt.pages.undwrtDeal.insureBillIfClaims"/>： 
								<input type="hidden" name="riskCode" description="<s:text name='undwrt.pages.undwrtDeal.riskCode'/>"
										value="<s:property value="#request.PrpTmainDto.riskCode"/>">
								<input type="hidden" name="hiClassCode" description="<s:text name='undwrt.CommonDangerUnits.riskTypeCode'/>"
										value="<s:property value="#request.PrpTmainDto.classCode"/>">
							</td>
							<s:if test='Renewal'>
								<td class=input4>
									<font color='red'>
									<%--有 --%>
										<s:text name="undwrt.have"/>
									</font>&nbsp; 
									<Input type="button" class="longbutton" name="claimInfo"
										value="<s:text name='undwrt.CommonDangerUnits.prePolicyPayInfo'/>" style="color: red"
										onclick="checkLastPolicyClaimInfoList();">
								</td>
							</s:if>
							<s:else>
								<td class=input4><font color='red'>
									<%--无 --%>
									<s:text name="undwrt.not"/></font>
								</td>
							</s:else>	
						</s:else>
					</tr>
					<tr>
						<%
							if (strClassCode.equals("26") || strClassCode.equals("27")) {
						%>
						<td class=title4>
							<%--投保人名称 --%>
							<s:text name="undwrt.pages.undwrtDeal.applicantName"/>：
						</td>
						<td class=input4>
							<s:property value="#request.PrpTmainDto.appliName" />
						</td>

						<%
							} else {
						%>
						<td class=title4>
							<%--被保险人名称 --%>
							<s:text name="undwrt.pages.undwrtDeal.insuredName"/>：
						</td>
						<td class=input4>
							<s:property value="#request.PrpTmainDto.insuredName" />
						</td>
						<%
							}
						%>
						<s:if test='"MC"==iRiskCode'>
						<td class=title4>
							<%--水险的TB险别没有起航日期20140715 --%>
							<s:if test='"TB"!=rationCode'>
									<%--起运日期 --%>
									<s:text name="undwrt.pages.undwrtDeal.startDate"/>11：
							</s:if>
						</td>
						<td class=input4>
							<s:if test='"TB"!=rationCode'>
								<rc:rcDate value = "${PrpTmainDto.startDate}" format="yyyy-MM-dd"/><s:text name="prompt.day"/>22
							</s:if>
						</td>
							
						</s:if>
						<s:else>
						<td class=title4>
							<%--保险期间 --%>
							<s:text name="undwrt.pages.undwrtDeal.insurePeriod"/>：
						</td>
						<td class=input4><rc:rcDate value = "${PrpTmainDto.startDate}" format="yyyy-MM-dd"/>&nbsp;3333&nbsp;<s:text name="prompt.day"/>${PrpTmainDto.startHour}
							<s:text name="prompt.hour"/><s:text name="prompt.start"/><s:text name="prompt.to"/>&nbsp;<rc:rcDate value = "${PrpTmainDto.endDate}" format="yyyy-MM-dd"/>
							<s:text name="prompt.day"/>${PrpTmainDto.endHour}<s:text name="prompt.hour"/>
						</td>
						</s:else>
					</tr>
					<tr>
						<td class=title4>
							<%--总保险金额 --%>
							<s:text name="undwrt.pages.undwrtDeal.insuranceSumMoney"/>：
						</td>
						<td class=input4>
							<s:property value="#request.PrpTmainDto.currency" />&nbsp; 
							<input type="text" class=readonly readonly="readonly" name="sumAmount"
								value="<fmt:formatNumber value="${PrpTmainDto.sumAmount}" pattern="#,##0.00"/>">
						</td>
						<td class=title4>
							<%--总保险费 --%>
							<s:text name="undwrt.pages.undwrtDeal.sumPremium"/>：
						</td>
						<td class=input4><s:property value="#request.PrpTmainDto.currency" />&nbsp; 
							<input type="hidden" name="TemCurrency"
								value="<s:property value="#request.PrpTmainDto.currency"/>" /> <input
								type="text" class=readonly  readonly="readonly" name="sumAmount"
								value="<fmt:formatNumber value="${PrpTmainDto.sumPremium}" pattern="#,##0.00"/>">
						</td>
					</tr>

					<%-- add by xiaojian 20051204_1 begin reason：投保（保）单核保时可见、可修改特殊因子 --%>
					<%
						PrpTmain prpTmainDto = (PrpTmain) request
									.getAttribute("PrpTmainDto");
							CommonAmountAndPremiumVo commonAmountAndPremiumDto = (CommonAmountAndPremiumVo) request
									.getAttribute("AmountAndPremiumDto");
							//是否应该“配置项和人员权限都满足”才显示特殊因子
							double dblDisRate1 = 0;
							double dblPremium1 = 0;
							double dblDisFee1 = 0;
							if(prpTmainDto.getDisRate1()!=null)
							{
							dblDisRate1 = prpTmainDto.getDisRate1().doubleValue();
							}
							dblPremium1 = commonAmountAndPremiumDto.getPremium();
							//四舍五入的问题
							dblDisFee1 = dblPremium1 * dblDisRate1 / 100;

							//管理费比例 begin
							String strManageFeeDisplay = "none"; //管理费比例默认不显示
							boolean allowManageFee = false; //条件1：是否允许录入管理费比例
							boolean allowManageFee_user = false; //条件2：操作员是否有录入管理费比例的权限
							boolean allowManageFee_Flag = false; //条件3：业务录入时是否允许管理费比例
							//管理费比例录入开关
							prpDriskConfigDto = uiPrpDriskConfigAction.queryRiskConfig(
									comCode, riskCode, "SWITCH_MANAGEFEERATE");
							if (prpDriskConfigDto != null
									&& prpDriskConfigDto.getConfigValue().equals("1")) {
								allowManageFee = true;
							} else {
								allowManageFee = false;
							}
							//人员管理费比例操作权限
							allowManageFee_user = UIPowerAction.checkPowerReturn(user,
									"prpall.policy.managefeerate");
							PrpTexpenseDto prpTexpenseDto = (PrpTexpenseDto) request
									.getAttribute("PrpTexpenseDto");
							double dbManageFeeRate = 0;
							double dbManageFee = 0;
							if (prpTexpenseDto != null) {
								dbManageFeeRate = prpTexpenseDto.getManageFeeRate();
								dbManageFee = dblPremium1 * dbManageFeeRate / 100;
								if (prpTexpenseDto.getFlag().length() >= 2) {
									//Flag第二位为“2”或“3”，管理费标志：true
									if (prpTexpenseDto.getFlag().substring(1, 2)
											.equals("2")
											|| prpTexpenseDto.getFlag().substring(1, 2)
													.equals("3")) {
										allowManageFee_Flag = true;
										strDisplay = "none"; //关闭特殊因子
									}
									//Flag第二位为“1”并且管理费开关为“开”，管理费标志：true
									if (prpTexpenseDto.getFlag().substring(1, 2)
											.equals("1")
											&& allowManageFee) {
										allowManageFee_Flag = true;
										strDisplay = "none"; //关闭特殊因子
									}
								}
								strDisplay = "none"; //关闭特殊因子
							}
							//zhulei：管理费比例显示，三个条件，prpDriskConfig配置放开，当前登陆人员有权，当前业务录入时允许管理费比例；
							if (allowManageFee && allowManageFee_user
									&& allowManageFee_Flag) {
								strManageFeeDisplay = "";
							}
							//管理费比例 end

							//非“非联共保”不允许修改特殊因子 
							coinsFlag = prpTmainDto.getCoinsFlag();
							if (!coinsFlag.equals("0"))
								className = "\"readonly\" readonly";
							else if (editType.equals("query"))
								className = "\"readonly\" readonly";
							else
								className = "\"free\"";
					%>

					<tr style="display:<%=strDisplay%>">
						<td class="title4">
							<%--特殊因子 --%>
							<s:text name="undwrt.pages.undwrtDeal.specificFactor"/>：
						</td>
						<td class="input4">
							<input type="hidden" name="DisRate1Old"
								value="<s:property value="#request.PrpTmainDto.disRate1"/>">
							<input type="text" name="DisRate1" class=<%=className%>
								value="<fmt:formatNumber value="${PrpTmainDto.disRate1}" pattern="0.0000"/>"
								onblur="changeDisFee1(this)">
						</td>
						<td class="title4">
							<%--特殊因子金额 --%>
							<s:text name="undwrt.pages.undwrtDeal.specificFactorMoney"/>：
						</td>
						<td class="input4">
							<input type="hidden" name="Premium1"
								value="<s:property value="dblPremium1" />"> <input
								type="text" name="DisFee1" class="readonly" readonly
								value="<s:property value="dblDisFee1" />">
						</td>
					</tr>
					<!-- 管理费比例 begin -->
					<s:if test="prpTexpenseDto != null">
						<tr style="display:<%=strManageFeeDisplay%>">
							<td class="title4">
								<%--管理费比例 --%>
								<s:text name="undwrt.pages.undwrtDeal.administrativeFeeProportion"/>：
							</td>
							<td class="input4">
								<input type="hidden"
									name="ManageFeeRateOld"
									value="<s:property value="prpTexpenseVo.manageFeeRate"/>">
								<input type="text" name="ManageFeeRate" class=<%=className%>
									value="<s:property value="prpTexpenseVo.manageFeeRate"/>"
									onblur="changeManageFeeRate(this)">
							</td>
							<td class="title4">
								<%--管理费金额 --%>
								<s:text name="undwrt.pages.undwrtDeal.administrativeFeeMoney"/>：
							</td>
							<td class="input4">
								<input type="hidden" name="Premium2"
									value="<s:property value="dblPremium1" />">
							    <input type="text" name="ManageFee" class="readonly" readonly
									value="<s:property value="dbManageFee" />">
							</td>
						</tr>
					</s:if>
					<!-- 管理费比例 end -->

					<%-- 增加是否目标客户  begin--%>
					<tr style="display: none;">
						<td class="title4">
							<%--是否目标客户 --%>
							<s:text name="undwrt.pages.undwrtDeal.ifTargetCustomer"/>：
						</td>
						<%
							if (prpTmainDto.getOthFlag().length() >= 15)
									strGoalInsuredFlag = prpTmainDto.getOthFlag().substring(14,
											15);
								if (strGoalInsuredFlag.equals("1"))
									strGoalInsuredFlag = "<s:text name='undwrt.yes'/>";
								else
									strGoalInsuredFlag = "<s:text name='undwrt.no'/>";
						%>
						<td class="input4">
							<%=strGoalInsuredFlag%>
						</td>
						<td class="title4"></td>
						<td class="input4">
							<input type="hidden" name="policyType"
								value="<s:property value="#request.PrpTmainDto.policyType"/>">
						</td>
					</tr>
					<%-- 增加是否目标客户 end --%>

					<!-- 净费比例（仅车险显示）begin-->
					<s:if test='#request.PrpTmainDto.classCode == "A" || #request.PrpTmainDto.classCode == "B"'>
						<td class="title4">
							<%--手续费比例 --%>
							<s:text name="undwrt.pages.undwrtDeal.factorageRatio"/>(%)：
						</td>
						<td class=input4 style="color:'<%=color%>'"><%=disRate%></td>
						<td class="title4">
							<%--优惠比例 --%>
							<s:text name="undwrt.pages.undwrtDeal.privilegeRatio"/>(%)：
						</td>
						<td class=input4><%=profitRate%></td>
					</s:if>
					<s:else>
						<td class="title4"></td>
						<td class="input4"></td>
						<td class="title4"></td>
						<td class="input4"></td>
					</s:else>
					<!-- 净费比例（仅车险显示）end -->

				</s:if>

				<!-- 保单信息 -->
				<s:if test="#request.prpCmainDto != null">
					<tr class=listtitle>
						<s:if
							test='handType == "22" && (businessType == "C" ||businessType = "Y")'>
							<td colspan="4">
								<%--保单摘要和赔付摘要信息 --%>
								<s:text name="undwrt.pages.undwrtDeal.insuranceModelMessages"/>
							</td>
						</s:if>
						<s:else>
							<td colspan="4">
								<%--保单摘要信息 --%>
								<s:text name="undwrt.pages.undwrtDeal.insuranceMessages"/>
							</td>
						</s:else>
					</tr>
					<tr>
						<td class=title4>
							<%--险种 --%>
							<s:text name="riskName"/>
						</td>
						<td class=input4>
							<s:property value="#request.prpCmainDto.riskCode" />
						</td>
						<td class=title4>
							<%--归属机构 --%>
							<s:text name="comOfRemoteOrg"/>：
						</td>
						<td class=input4>
							<s:property value="#request.prpCmainDto.comCode" />
						</td>
					</tr>
					<tr>
						<td class=title4>
							<%--险种名称 --%>
							<s:text name="undwrt.pages.undwrtDeal.riskcName"/>：
						</td>
						<td class=input4>
							<%=request.getAttribute("riskCName")%>
						</td>
						<td class=title4>
							<%--归属机构名称 --%>
							<s:text name="undwrt.pages.undwrtDeal.comOfRemoteOrgName"/>：
						</td>
						<td class=input4>
							<%=request.getAttribute("comCName")%>
						</td>
					</tr>
					<tr>
						<td class=title4>
							<%--保单号 --%>
							<s:text name="policyManage.policyNo"/>：
						</td>
						<td class=input4>
							<s:property value="prpCmainDto.policyNo" />
						</td>
						<td class=title4>
							<input type="hidden" name="riskCode"
								description="<s:text name="undwrt.pages.undwrtDeal.riskCode"/>"
								value="<s:property value="#request.prpCmainDto.riskCode"/>">
						</td>
						<input type="hidden" name="hiClassCode" description="<s:text name='undwrt.pages.undwrtDeal.insuranceCategoryCode'/>"
							value="<s:property value="#request.prpCmainDto.classCode"/>">
						<td class=input4>&nbsp;</td>
					</tr>
					<tr>
						<%
							if (strClassCode.equals("26") || strClassCode.equals("27")) {
						%>
						<td class=title4>
							<%--投保人名称 --%>
							<s:text name="undwrt.pages.undwrtDeal.applicantName"/>：
						</td>
						<td class=input4>
							<s:property value="#request.prpCmainDto.appliName" />
						</td>
						<%
							} else {
						%>
						<td class=title4>
							<%--被保险人名称 --%>
							<s:text name="undwrt.HebaoTaskDealQueryResult.insuredName"/>：
						</td>
						<td class=input4>
						 	<s:property value="#request.prpCmainDto.insuredName" />
						</td>
						<%
							}
						%>
						<s:if test='"MC"==iRiskCode'>
						<td class=title4>
						 <s:if test='"TB"!=rationCode'>
							<%--起运日期 --%>
							<s:text name="undwrt.pages.undwrtDeal.startDate"/>44：
						 </s:if>
						</td>
						</s:if>
						<s:else>
						<td class=title4>
						<%--保险期间 --%>
						<s:text name="undwrt.pages.undwrtDeal.insurePeriod"/>：
						</td>
						</s:else>
						<td class=input4>
							<s:property value="#rpCmainVo.startDate" />55&nbsp;<s:text name="prompt.to"/>&nbsp;77&nbsp;<s:property
								value="prpCmainVo.endDate" />
						</td>
					</tr>
					<tr>
						<td class=title4>
							<%--总保险金额 --%>
							<s:text name="undwrt.pages.undwrtDeal.insuranceSumMoney"/>：
						</td>
						<td class=input4>
							<s:property value="#prpCmainDto.currency" />&nbsp;
							<s:property value="prpCmainVo.sumAmount" />
						</td>
						<td class=title4>
							<%--总保险费 --%>
							<s:text name="undwrt.pages.undwrtDeal.sumPremium"/>：
						</td>
						<td class=input4>
							<s:property value="#prpCmainDto.currency" />&nbsp;
							<s:property value="prpCmainVo.sumPremium" />
						</td>
					</tr>
					<%-- add by xiaojian 20051204_2 begin reason：投保（保）单核保时可见、可修改特殊因子 --%>
					<%
						if (!businessType.equals("C") && !businessType.equals("Y")) {
								PrpCmainDto prpCmainDto = (PrpCmainDto) request
										.getAttribute("PrpCmainDto");
								CommonAmountAndPremiumVo commonAmountAndPremiumDto = (CommonAmountAndPremiumVo) request
										.getAttribute("AmountAndPremiumDto");
								//xiaojian_leave：是否应该“配置项和人员权限都满足”才显示特殊因子
								double dblDisRate1 = 0;
								double dblPremium1 = 0;
								double dblDisFee1 = 0;

								dblDisRate1 = prpCmainDto.getDisRate1();
								dblPremium1 = commonAmountAndPremiumDto.getPremium();
								//xiaojian_leave：四舍五入的问题
								dblDisFee1 = dblPremium1 * dblDisRate1 / 100;

								/* modify by xiaojian 20060304_3 begin reason：非“非联共保”不允许修改特殊因子 */
								coinsFlag = prpCmainDto.getCoinsFlag();
								if (!coinsFlag.equals("0"))
									className = "\"readonly\" readonly";
								else if (editType.equals("query"))
									className = "\"readonly\" readonly";
								else
									className = "\"free\"";
								/* modify by xiaojian 20060304_3 end */
					%>
					<tr style="display:<%=strDisplay%>">
						<td class="title4">
							<%--特殊因子 --%>
							<s:text name="undwrt.pages.undwrtDeal.specificFactor"/>：
						</td>
						<td class="input4">
							<input type="hidden" name="DisRate1Old"
								value="<s:property value="#prpCmainDto.disRate1"/>"> 
								<input
								type="text" name="DisRate1" class=<%=className%>
								value="<s:property value="#prpCmainDto.disRate1"/>"
								onblur="changeDisFee1(this)">
						</td>
						<td class="title4">
							<%--特殊因子金额 --%>
							<s:text name="undwrt.pages.undwrtDeal.specificFactorMoney"/>：
						</td>
						<td class="input4">
							<input type="hidden" name="Premium1"
								value="<s:property value="#dblPremium1"/>"> 
							<input type="text" name="DisFee1" class="readonly" readonly
								value="<s:property value="#dblDisFee1"/>">
							</td>
					</tr>
					<%-- add by xiaojian 20051204_2 end --%>

					<%-- add by xiaojian 20051112_2 begin reason：增加是否目标客户 --%>
					<tr>
						<td class="title4">
						<%--是否目标客户 --%>
						<s:text name="undwrt.pages.undwrtDeal.ifTargetCustomer"/>：
						</td>
						<%
							if (prpCmainDto.getOthFlag().length() >= 15)
										strGoalInsuredFlag = prpCmainDto.getOthFlag()
												.substring(14, 15);
									if (strGoalInsuredFlag.equals("1"))
										strGoalInsuredFlag = "<s:text name='undwrt.yes'/>";
									else
										strGoalInsuredFlag = "<s:text name='undwrt.no'/>";
						%>
						<td class="input4"><%=strGoalInsuredFlag%></td>
						<td class="title4"></td>
						<td class="input4"><input type="hidden" name="policyType"
							value="<s:property value="#prpCmainDto.policyType"/>"></td>
					</tr>
					<%-- add by xiaojian 20051112_2 end --%>
					<%
						}
							/* modify by xiaojian 20051229 end */

							if (handType.equals("22")
									&& (businessType.equals("C") || businessType
											.equals("Y"))) {
								ArrayList chargeList = (ArrayList) request
										.getAttribute("prplchargeList");
								Iterator theCharge = chargeList.iterator();
								PrpLchargeDto prpLchargeDto = new PrpLchargeDto();
								int lines = 1;
								while (theCharge.hasNext()) {
									prpLchargeDto = (PrpLchargeDto) theCharge.next();
									if (lines % 2 != 0) {
										out.println("<tr>");
									}
									double sumCharge = prpLchargeDto.getChargeAmount();
									double sumRealPay = prpLchargeDto.getSumRealPay();
									double charge = sumCharge;
									out.println("<td  class='title4'><b>"
											+ prpLchargeDto.getChargeName() + "</b></td>");
									out.println("<td  class='input4'><b>" + charge
											+ "</b></td>");
									if (lines % 2 == 0) {
										out.println("</tr>");
									}
									lines++;
								}
								if (lines % 2 == 0) {
									out.println("<td class=title4></td><td class=input4></td>");
									out.println("</tr>");
								}
					%>
					<tr>
						<td class="title4">
							<b>
								<%--标的损失 --%>
								<s:text name="undwrt.pages.undwrtDeal.objectLoss"/>：
							</b>
						</td>
						<td class="input4">
							<b>
								<s:property value="#policyAbstractInfoDto.sumLoss" />
							</b>
						</td>
						<td class="title4">
							<b>
								<%--合计 --%>
								<s:text name="undwrt.pages.undwrtDeal.amount"/>：
							</b>
							</td>
						<td class="input4">
							<b>
								<s:property value="#policyAbstractInfoDto.sumPaid" />
							</b>
						</td>
					</tr>
					<%
						}
					%>
				</s:if>
			</table>
			</td>
		</tr>

		<tr>
			<td>
			<%
				if (!handType.equals("22")) {
			%> <%
 	blPrpDconfigCode.getFunNameOrFunType(strComCode, riskCode,
 				"Notify", nowDate.getCurrentTime("yyyy-MM-dd"));
 		if (blPrpDconfigCode.getSize() > 0) {
 			strNotifyPath = blPrpDconfigCode.getArr(0).getFunName();
 %> 		<input type="hidden" name="NotifyPath"
				description="<s:text name='undwrt.pages.undwrtDeal.informPath'/>" value="<%=strNotifyPath%>">
			<%--告知单信息 --%> 
			<Input name="NotifyBtn" class="button" type="button" value="<s:text name='undwrt.pages.undwrtDeal.informPath'/>"
				onclick="showNotifyInfo()"> 
	<%
 	}
 %> 
 			<%--保单类型 --%>
			<input type="hidden" name="PolicySort" description="<s:text name='undwrt.pages.undwrtDeal.insurancePolicyType'/>"
				value="<%=strPolicySort%>">
			<%--详细信息 --%>
			<Input name="butDetail" class="button" type="button" value="<s:text name='undwrt.pages.undwrtDeal.detailedInformation'/>"
				onclick="showBusinessInfo('<%=strComCode%>')"> <%
 	if (!("".equals(prpTmainSubDto.getRiskCode()) || prpTmainSubDto
 				.getRiskCode() == null)) {
 %> 
 			<%--关联投保单信息 --%>
 			<Input type="button" class="longbutton"
				value="<s:text name='undwrt.pages.undwrtDeal.relevanceInsureBillMessages'/>" onclick="showBusinessCIInfo()">
 <%
 	}
 	}
 	//add by zhangruifeng end 20070416
 %> 
 			<s:if test='historyProposal==true'>
 				<%--历史承保信息 --%>
				<input type="button" class=longbutton value="<s:text name='undwrt.pages.undwrtDeal.historyUnderwriteMessages'/>"
					name="BusinessTotalInfo"
					onclick="showBusinessTotalInfo('<s:property value="iBusinessNo"/>');">
			</s:if> 
			<s:if test='historyLoss==true'>
				<%--历史赔付信息 --%>
				<input type="button" class=longbutton value="<s:text name='undwrt.pages.undwrtDeal.historyPayMessages'/>"
					name="HistoryLossInfo"
					onclick="showHistoryLossInfo('<s:property value="iBusinessNo"/>');">
			</s:if> 
				<input type="hidden" name="typeTreeXML" value="${typeTreeXML}">
	            <input type="hidden" name="remoteUrl" value="${remoteUrl }">
	            <input type="hidden" name="paramString" value="${paramString}">
	            <input type="hidden" name="remoteUrl1" value="${remoteUrl1 }">
	            <%--影像资料 --%>
                <input name="buttonMessage1" class="longbutton" type="button" value="<s:text name='undwrt.prompt.videoFiles'/>"
                        onclick="queryImage()">
                <input name="buttonMessage2" class="longbutton" type="button" value="影像资料上傳"
                        onclick="uploadImage('<%=userCode%>','<%=comCode%>','<%=businessNo%>')">     
			<s:if test='handType == "22"'>
				<%--保单信息 --%>
				<Input type="button" name="PolicyNoInfo" class="longbutton"
					value="<s:text name='undwrt.pages.undwrtDeal.insurancePolicyMessages'/>" onclick="showPolicyInfo();">
			</s:if>
		  <s:if test="handType == 11 && iNodeStatus != 4 && iNodeStatus != 0">
		  		<%--备注记录 --%>
		  		<s:if test="existMessage">
				<Input name="buttonMessage1" class="button" type="button"
					value="<s:text name='undwrt.pages.undwrtDeal.remarkRecord'/>" style="color:yellow" onclick="openWinQuery();">
				</s:if>
				<s:else>
				<Input name="buttonMessage1" class="button" type="button"
					value="<s:text name='undwrt.pages.undwrtDeal.remarkRecord'/>" onclick="openWinQuery();">
				</s:else>
			</s:if> 
			<s:if test='"A01"!=iRiskCode && "B01"!=iRiskCode'>
				<%--理赔记录 --%>
				<Input name="claimInfo" class="button" type="button" value="<s:text name='undwrt.pages.undwrtDeal.payConditionRecord'/>" 
					onclick="openClaimInfo();">
			</s:if> 
			<input type="hidden" name="language" description="<s:text name='undwrt.pages.undwrtDeal.languageType'/>"
				value="<%=strLanguage%>"> <!-- added by yanglibo 20090812 begin reason：历年承保理赔信息 -->
			<%
				blPrpDconfigCode.getFunNameOrFunType(strComCode, riskCode,
						"BISumPaid", nowDate.getCurrentTime("yyyy-MM-dd"));
				if (blPrpDconfigCode.getSize() > 0) {
			%>
			<%--历年承保、理赔信息查询 --%>
			 <Input name="butTPolicyClaimInfo" class="longbutton"
				type="button" value="<s:text name='undwrt.pages.undwrtDeal.messagesQuery'/>" onclick="viewPolicyClaimInfo()">
			<%
				}
			%> 
			</td>
		</tr>

		<!-- 原始标的信息 -->
		<tr>
			<td width="100%">
			<table cellpadding="5" cellspacing="1" class="common" align="center"
				style="width: 100%">
				<%
					if (strClassCode.startsWith("26") || strClassCode.startsWith("27")) {
				%>
				<tr class=listtitle>
					<%-- 			
					<td colspan="7">
						<%--原始标的信息 
						<s:text name="undwrt.pages.undwrtDeal.originalObjectMessages"/>
					</td>
						--%>
				</tr>
				<tr class=common>
					<td>
						<%--条款名称 --%>
						<s:text name="undwrt.pages.undwrtDeal.clauseName"/>
					</td>
					<td>
						<%--险种责任 --%>
						<s:text name="undwrt.pages.undwrtDeal.riskcResponsibility"/>
					</td>
					<%
						if (!riskCode.equals("2727")) {
					%>
					<td>
						<%--折扣 --%>
						<s:text name="undwrt.pages.undwrtDeal.discount"/>
					</td>

					<%
						}
							if (riskCode.equals("2703") || riskCode.equals("2708")) {
					%>
					<td>
						<%--份数 --%>
						<s:text name="undwrt.pages.undwrtDeal.fraction"/>
					</td>

					<%
						}
							if (!riskCode.equals("2727")) {
					%>
					<td>
						<%--人数 --%>
						<s:text name="undwrt.pages.undwrtDeal.numberPeople"/>
					</td>
					<%
						}
					%>
					<td>
						<%--保额 --%>
						<s:text name="undwrt.pages.undwrtDeal.coverage"/>
					</td>					
					<td>
					<div style="display:none">
						<%--保费--%> 
						<s:text name="undwrt.pages.undwrtDeal.premium"/>
						</div>&nbsp;
					</td>				
				</tr>
				<s:if test="#request.ItemKind != null">
					<s:iterator status="index" id="itemKind" value="#request.ItemKind">

						<tr class=common>
							<td><input class="formtitle1" readonly
								value="<s:property value="#itemKind.kindName"/>">
							</td>
							<td><input class="formtitle1" readonly
								value="<s:property value="#itemKind.itemDetailName"/>">
							</td>
							<s:if test='"2727" !=iRiskCode'>
								<td>
									<input class="formtitle1" readonly
										value="<s:property value="#itemKind.discount"/>">
								</td>
							</s:if>
							<s:if test='"2703" ==iRiskCode || "2708" ==iRiskCode'>

								<td>
									<input class="formtitle1" readonly
										value="<s:property value="#itemKind.value"/>">
								</td>
							</s:if>
							<s:if test='"2727" !=iRiskCode'>

								<td>
									<input class="formtitle1" readonly
										value="<s:property value="#itemKind.quantity"/>">
								</td>
							</s:if>
							<%-- modify by yanglibo 20080826 begin 改为千分位 --%>
							<td>
								<input class="formtitle1" readonly
									value="<s:property value="#itemKind.amount"/>">
							</td>
							<td>
								<input class="formtitle1" readonly
									value="<s:property value="#itemKind.premium"/>">
							</td>
							<%-- modify by yanglibo 20080826 end 改为千分位 --%>
						</tr>
					</s:iterator>
				</s:if>
				<%
					} else {
				%>
				<tr class=listtitle>
					<%--
					<td colspan="10">
						原始标的信息
						<s:text name="undwrt.pages.undwrtDeal.originalObjectMessages"/>
					</td>
					 --%>
				</tr>
				<tr class=common>
					<td>
						<%--序号 --%>
						<s:text name="undwrt.pages.undwrtDeal.serialNo"/>
					</td>
					<td>
						<%--险别 --%>
						<s:text name="undwrt.pages.undwrtDeal.Risk"/>
					</td>
					<s:if
						test='!(iRiskCode == "0101" || iRiskCode == "0102" ||iRiskCode == "0104" || iRiskCode == "0110" ||iRiskCode == "0111" ||iRiskCode =="0112")'>

						<td>
							<%--标的项目 --%>
							<s:text name="undwrt.pages.undwrtDeal.objectItem"/>
						</td>
					</s:if>
					<%
						if (strClassCode.equals("09") || strClassCode.equals("10")) {
					%>

					<td>
						<%--被保险货物名称 --%>
						<s:text name="undwrt.pages.undwrtDeal.subjectInsuranceName"/>
					</td>
					<%
						} else {
					%>

					<td>
						<%--标的名称 --%>
						<s:text name="undwrt.pages.undwrtDeal.objectName"/>
					</td>
					<%
						}
					%>
					<%--水险没有邮编区号20140702 --%>
					<s:if test='"MC"!=iRiskCode'>
					<td>
						<%--邮编 --%>
						<s:text name="undwrt.pages.undwrtDeal.postcode"/>
					</td>
					<td>
						<%--标的地址 --%>
						<s:text name="undwrt.pages.undwrtDeal.objectAddress"/>
					</td>
					</s:if>
					<td>
						<%--币别 --%>
						<s:text name="undwrt.pages.undwrtDeal.Currency"/>
					</td>

					<%
						if (strClassCode.startsWith("15")) {
					%>
					<td>
						<%--累计责任限额 --%>
						<s:text name="undwrt.pages.undwrtDeal.accumulativeDutyLimit"/>
					</td>
					<td>
						<%--每次事故责任限额 --%>
						<s:text name="undwrt.pages.undwrtDeal.oneAccumulativeDutyLimit"/>
					</td>
					<%
						} else {
					%>
					<td>
						<%--保额 --%>
						<s:text name="undwrt.pages.undwrtDeal.coverage"/>
					</td>
					<%
						}
					%>
					<%-- <td>
					   <div style="display:none">
						保费
						<s:text name="undwrt.pages.undwrtDeal.premium"/>
						</div>&nbsp;
					</td> --%>
					
				</tr>
				<s:if test="#request.ItemKind != null">
					<s:iterator value="#request.ItemKind" status="index" id="itemKind">
					<!--mantis： EGN0110_0610，處理人員：DP0706，EGN0110_新增CM機械綜合險START-->
					<s:if test="(#itemKind.riskCode != 'CM') || (#itemKind.riskCode == 'CM' && #itemKind.kindCode != 'CM001')">
						<tr class=common>
							<td>
								<input class="formtitle1" name="itemKindNo" readonly
									value="<s:property value="#itemKind.itemKindNo"/>">
							</td>
							<td>
								<input class="formtitle1" readonly value="<s:property value="#itemKind.kindName"/>">
								 <input type=hidden value="<s:property value="#itemKind.kindCode"/>">
							</td>
							<s:if test='!(iRiskCode=="0101" 
								|| iRiskCode=="0102" 
								|| iRiskCode=="0104" 
								|| iRiskCode=="0110" 
								|| iRiskCode=="0111" 
								|| iRiskCode=="0112")'>
								<td>
									<input class="formtitle1" name="" readonly
										value="<s:property value="#itemKind.itemCode"/>">
								</td>
							</s:if>
							<s:else>
								<input type="hidden" class="formtitle1" name="" readonly
									value="<s:property value="#itemKind.itemCode"/>">
							</s:else>
							<td>
								<input class="formtitle1" name="" readonly
									value="<s:property value="#itemKind.itemDetailName"/>">
							</td>
							<%--水险没有邮编区号20140702 --%>
							<s:if test='"MC"!=iRiskCode'>
							<td>
								<input class="formtitle1" name="" readonly
									value="<s:property value="#itemKind.addressCode"/>">
							</td>
							<td>
								<input class="formtitle1" name="" readonly
									value="<s:property value="#itemKind.addressName"/>">
							</td>
							</s:if>
							<td>
								<input class="formtitle1" name="iCurrency" readonly
									value="<s:property value="#itemKind.currency"/>">
							</td>
							<%--modify by yanglibo begin 20080918 15险类特殊处理--%>
							<%
								if (strClassCode.startsWith("15")) {
							%>
							<td>
								<input class="formtitle1" name="iAmount" readonly
									value="<s:property value="#itemKind.amount"/>">
							</td>
							<%--modify by douzongxing begin 20081126 每次事故赔偿限额--%>
							<td>
								<input class="formtitle1" name="limitFee" readonly
									value="<s:property value="#itemKind.limitFee"/>">
							</td>
							<%--modify by douzongxing end 20081126 每次事故赔偿限额--%>
							<%
								}else {
							%>
								<!-- add by wangcan 2015/11/24 如果为MC，附加条款保额栏位显示为空 -->
						        <s:if test='"MC"==iRiskCode'>
						        	<s:if test='"2"==#itemKind.flag'>
						          	  <td><input class="formtitle1" name="iAmount" readonly value="" /> </td>
						      		</s:if>
						       		<s:else>	
		        						<td>
											<input class="formtitle1" name="iAmount" readonly
												value="<fmt:formatNumber value="${itemKind.amount}" pattern="#,##0.00"/>">
										</td>
									</s:else>
								</s:if>
								<s:else>
									<td>
										<input class="formtitle1" name="iAmount" readonly
											value="<fmt:formatNumber value="${itemKind.amount}" pattern="#,##0.00"/>">
									</td>
								</s:else>
							<%
								}
							%>
							<%--modify by yanglibo end
							<td>
								<input class="formtitle1" name="iPremium" readonly
									value="<fmt:formatNumber value="${itemKind.premium}" pattern="#,##0.00"/>">
								<input type="hidden" name="calculateFlag"
									value="<s:property value="#itemKind.calculateFlag"/>">
						   </td>
						   --%>
						</tr>
					</s:if><!--mantis： EGN0110_0610，處理人員：DP0706，EGN0110_新增CM機械綜合險END-->
					</s:iterator>
				</s:if>
				<%
					}
				%>
			</table>
			</td>
		</tr>

		<!-- 划分风险评估信息Start-->
		<s:if test='handType =="11"'>
			<tr>
				<td>
				<%--风险评估信息 --%>
				<IMG name="butDanger" class="button" type="button"
					alt="<s:text name='undwrt.pages.undwrtDeal.riskAssessMessages'/>" src="/undwrt/common/images/butCollapse.gif"
					onclick="showPage(this,dangerInfo)"> <s:text name="undwrt.pages.undwrtDeal.riskAssessMessages"/><br>

				<span id="dangerInfo">
				<table width="100%">
					<tr>
						<td>
						<span style="display: none">
						<table class="common" style="display: none" id="DangerUnit_Data"
							cellspacing="1" cellpadding="0">
							<tbody>
								<td>
								<table class="common" style="width: 100%" cellspacing="1"
									cellpadding="0">
									<tr class=common>
										<td></td>
										<td width='4%'>
											<%--序号--%>
											<s:text name="undwrt.pages.undwrtDeal.serialNo"/>
										</td>
										<td width='25%' colspan='2'>
											<%--描述 --%>
											<s:text name="undwrt.pages.undwrtDeal.describe"/>
										</td>
										<td width='15%'>
											<%--地址 --%>
											<s:text name="undwrt.pages.undwrtDeal.address"/>
										</td>
										<td width='7%'>
											<%--币别 --%>
											<s:text name="undwrt.pages.undwrtDeal.Currency"/>
										</td>
										<td width='15%'>
											<%--保额 --%>
											<s:text name="undwrt.pages.undwrtDeal.coverage"/>
										</td>
										<td width='13%'>
											<div style="display:none">
											<%--保费 --%>
											<s:text name="undwrt.pages.undwrtDeal.premium"/>
											</div>&nbsp;
										</td>
										<td width='10%'>
											<%--占比 --%>
											<s:text name="undwrt.pages.undwrtDeal.dutyCycle"/>
										</td>
										<td width='10%'>
											<%--子信息 --%>
											<s:text name="undwrt.pages.undwrtDeal.sonMessages"/>
										</td>
										<td width='1%'>*</td>
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
										<td rowspan="3">
											<input class="free" readonly
												name="dangerNo" description="<s:text name='undwrt.pages.undwrtDeal.serialNo'/>">
										</td>
										<td colspan='2'>
											<input type="hidden"
												name="dangerCoinsFlag" value=""> 
											<input type="hidden"
												name="dangerShareHolderFlag" value="">
											 <input
												type="hidden" name="dangerBusinessFlag" value=""> 
											<input
												type="hidden" name="dangerBusinessNature" value=""> 
											<input
												type="hidden" name="dangerChannelType" value=""> 
											<input
												type="hidden" name="dangerCartypeCode" value="">
											 <input
												type="hidden" name="dangerExchRateCNY" value="">
											 <input
												class="free" readonly name="dangerDesc" description="<s:text name='undwrt.pages.undwrtDeal.describe'/>">
										</td>
										<td>
											<input class="free" readonly name="dangerAddress"
												description="<s:text name='undwrt.pages.undwrtDeal.address'/>">
										</td>
										<td>
											<input class="free" readonly name="currency"
												description="<s:text name='undwrt.pages.undwrtDeal.Currency'/>">
										</td>
										<td>
											<input class="free" readonly name="amount"
												description="<s:text name='undwrt.pages.undwrtDeal.coverage'/>">
										</td>
										<td>
											<input class="free" readonly name="premium"
												description="<s:text name='undwrt.pages.undwrtDeal.premium'/>">
										</td>
										<td align="center">
											<input class="free" name="dangerShare"
												readonly description="<s:text name='undwrt.pages.undwrtDeal.dutyCycle'/>">
										</td>
										<td rowspan="3">
										<div><!--modify by yanglibo 20090512 begin reason：非车险权限岗位调整，增加核保初审岗-->
											<%--详细信息 --%>
											<input type=button class=button name="buttonShowItem"
												onclick="return showDangerItem(this,'DangerUnit','0');"
												value="<s:text name='undwrt.pages.undwrtDeal.detailedInformation'/>" style="cursor: hand"
												<%="4".equals(request.getParameter("iNodeNo")) ? "disabled": ""%>>
										<!--modify by yanglibo 20090512 end reason：非车险权限岗位调整，增加核保初审岗-->
										</div>
										<input type=hidden name="hiDangerNo">
										 <input type=hidden name="isSavaDangerUnit"> <%--单条危险单位保存是否保存标志位--%>
										</td>
										<td rowspan="3" style='width: 1%' align="center">
											<div>
												<input type=button name="buttonDelete"
													class=smallbutton
													onclick="deleteTdangerInfo(this,'DangerUnit');" value="-"
													style="cursor: hand"
													<%=editType.equals("query") ? "disabled" : ""%>>
										 	</div>
										</td>
									</tr>

									<tr class=common>
										<td width='15%'>
											<%--险种名称 --%>
											<s:text name="undwrt.pages.undwrtDeal.riskcName"/>
										</td>
										<td width='10%'>
											<%--风险等级 --%>
											<s:text name="undwrt.pages.undwrtDeal.riskGrade"/>
										</td>
										<td width='15%'>
											<%--风险名称 --%>
											<s:text name="undwrt.pages.undwrtDeal.riskName"/>
										</td>
										<%
											if (strClassCode.equals("27") && includeAccident.equals("Y")) {
										%>
										<td width='5%'>
											<%--意健险PML值 --%>
											<s:text name="undwrt.pages.undwrtDeal.accidentHealthInsurancePMLvalue"/>
										</td>
										<td width='15%'>
											<%--自留额 --%>
											<s:text name="undwrt.pages.undwrtDeal.retention"/>
										</td>
										<td width='15%'>
											<%--除外责任/申报业务 --%>
											<s:text name="undwrt.pages.undwrtDeal.exceptResponsibility"/>/<s:text name="undwrt.pages.undwrtDeal.declareBusiness"/>
										</td>
										<%
											} else {
										%>
										<td width='5%'>
											<%--币别 --%>
											<s:text name="undwrt.pages.undwrtDeal.Currency"/>
										</td>
										<td width='15%'>
											<%--自留额 --%>
											<s:text name="undwrt.pages.undwrtDeal.retention"/>
										</td>
										<td width='15%'>
											<%--除外责任/申报业务 --%>
											<s:text name="undwrt.pages.undwrtDeal.exceptResponsibility"/>/<s:text name="undwrt.pages.undwrtDeal.declareBusiness"/>
										</td>
										<%
											}
										%>
										<td width='10%'>
											<%--进合约 --%>
											<s:text name="undwrt.pages.undwrtDeal.intoContract"/>
										</td>
									</tr>
									<tr class=common>
										<td width='15%'>
											<input type="hidden" name="eRiskCode"
												description="<s:text name='undwrt.pages.undwrtDeal.riskCode'/>"> 
											<input class="free" readonly
												name="riskName" description="<s:text name='undwrt.pages.undwrtDeal.riskcName'/>">
										</td>
										<td width='10%'>
											<input class="free" readonly
												name="riskLevel" description="<s:text name='undwrt.pages.undwrtDeal.riskGrade'/>">
										</td>
										<td width='15%'>
											<input class="free" readonly
												name="riskLevelDesc" description="<s:text name='undwrt.pages.undwrtDeal.riskGradeDescribe'/>">
										</td>
										<%
											if (strClassCode.equals("27") && includeAccident.equals("Y")) {
										%>
										<td width='5%'>
											<input type="test" style="width: 50%"
												name="speCurrency" description="PML<s:text name='undwrt.pages.undwrtDeal.currency'/>" value=""> 
											<input class="free" style="width: 50%" name="speValue"
												description="PML<s:text name='undwrt.value'/>" value="">
										</td>
										<td width='15%'>
											<input class="free" readonly
												name="retCurrency" style="width: 20%" description="<s:text name='undwrt.pages.undwrtDeal.retentionCurrency'/>">
											<input class="free" readonly name="retentionValue"
												style="width: 70%" description="<s:text name='undwrt.pages.undwrtDeal.retentionMoney'/>">
										</td>
										<td width='15%'>
											<input type="hidden" class="free"
												name="dangerItemKind" description="<s:text name="undwrt.pages.undwrtDeal.exceptResponsibility"/>">
											<input name="dangerItemKindName" class="free" readonly>
										</td>
										<%
											} else {
										%>
										<td width='5%'>
											<input class="free" readonly
												name="retCurrency" description="<s:text name='undwrt.pages.undwrtDeal.retentionCurrency'/>">
										</td>
										<td width='15%'>
											<input class="free" readonly
												name="retentionValue" description="<s:text name='undwrt.pages.undwrtDeal.retentionMoney'/>">
										</td>
										<td width='15%'>
										  	<input type="hidden" class="free"
												name="dangerItemKind" description="<s:text name="undwrt.pages.undwrtDeal.exceptResponsibility"/>">
										 	<input name="dangerItemKindName" class="free" readonly>
										</td>
										<%
											}
										%>
										<td width='10%'>
											<input type="checkbox" align="center"
												name="dangerFlag" description="<s:text name='undwrt.pages.undwrtDeal.intoContract'/>">
										</td>
									</tr>
								</table>
								</td>
							</tbody>
						</table>
						</span> 
						<span id="spanDangerUnit" style="display: " cellspacing="1" cellpadding="0">
						<table class="common" cellpadding="5" cellspacing="1" align="center" id="DangerUnit">
							<thead>
								<tr class=listtitle>
									<td>
										<%--划分风险评估信息 --%>
										<s:text name="undwrt.pages.undwrtDeal.divideRiskAssessMessages"/>
									</td>
								</tr>
							</thead>
							<tbody>
								<s:if test="#request.DangerDetail != null">
									<s:iterator value="#request.DangerDetail" status="index"
										id="dangerDetail">
										<tr>
											<td>
											<table class="common" style="width: 100%" cellspacing="1"
												cellpadding="0">
												<tr class=common>
													<td></td>
													<td width="4%">
														<%--序号 --%>
														<s:text name="undwrt.pages.undwrtDeal.serialNo"/>
													</td>
													<%
														if (strClassCode.equals("27")
																			&& includeAccident.equals("Y")) {
													%>
													<td width="25%" colspan="2">
														<%--描述 --%>
														<s:text name="undwrt.pages.undwrtDeal.describe"/>
													</td>
													<%
														} else {
													%>
													<td width="25%" colspan="2">
														<%--描述 --%>
														<s:text name="undwrt.pages.undwrtDeal.describe"/>
													</td>
													<%
														}
													%>
													<td width="15%">
														<%--地址 --%>
														<s:text name="undwrt.pages.undwrtDeal.address"/>
													</td>
													<td width="7%">
														<%--币别 --%>
														<s:text name="undwrt.pages.undwrtDeal.currency"/>
													</td>
													<td width="15%">
														<%--保额 --%>
														<s:text name="undwrt.pages.undwrtDeal.coverage"/>
													</td>			
													<!--  									
													<td width="13%">
														<div style="display:none">
														<%--保费 --%>
														<s:text name="undwrt.pages.undwrtDeal.premium"/>
														</div>&nbsp;
													</td>
													-->
													<td width="10%" colspan="2">
														<%--占比 --%>
														<s:text name="undwrt.pages.undwrtDeal.dutyCycle"/>
													</td>
													<td width="10%">
														<%--子信息 --%>
														<s:text name="undwrt.pages.undwrtDeal.sonMessages"/>
													</td>
													<td width="1%">*</td>
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
													<td rowspan="3"><input class="free" readonly
														name="dangerNo" description="<s:text name='undwrt.pages.undwrtDeal.serialNo'/>"
														value="<s:property value="#dangerDetail.dangerNo"/>"></td>
													
													<td colspan="2">
														<input type="hidden"
															name="dangerCoinsFlag"
															value="<s:property value="#dangerDetail.coinsFlag"/>">
														<input type="hidden" name="dangerShareHolderFlag"
															value="<s:property value="#dangerDetail.shareHolderFlag"/>">
														<input type="hidden" name="dangerBusinessFlag"
															value="<s:property value="#dangerDetail.businessFlag"/>">
														<input class="free" readonly name="dangerDesc"
															description="<s:text name='undwrt.pages.undwrtDeal.describe'/>"
															value="<s:property value="#dangerDetail.dangerDesc"/>">
														<input type="hidden" name="dangerBusinessFlag" value="">
														<input type="hidden" name="dangerBusinessNature"
															value="<s:property value="#dangerDetail.businessNature"/>">
														<input type="hidden" name="dangerChannelType"
															value="<s:property value="#dangerDetail.channelType"/>">
														<input type="hidden" name="dangerCartypeCode"
															value="<s:property value="#dangerDetail.cartypeCode"/>">
														<input type="hidden" name="dangerExchRateCNY"
															value="<s:property value="#dangerDetail.exchRateCNY"/>">
													</td>
													<td>
														<input class="free" readonly name="dangerAddress"
															description="<s:text name='undwrt.pages.undwrtDeal.address'/>"
															value="<s:property value="#dangerDetail.addressName"/>">
													</td>
													<td>
														<input class="free" readonly name="currency"
															description="<s:text name='undwrt.pages.undwrtDeal.currency'/>"
															value="<s:property value="#dangerDetail.currency"/>">
													</td>
													<%-- modify by yanglibo 20080826 begin 改为千分位 20080828  去掉千分位--%>
													<td>
														<input class="free" readonly name="amount"
															description="<s:text name='undwrt.pages.undwrtDeal.coverage'/>"
															value="<fmt:formatNumber value="${amount}" pattern="0.00"/>"
															onblur="checkNumber(this)">
													</td>
													<!-- 
													<td>
														<input class="free" readonly name="premium"
															description="<s:text name='undwrt.pages.undwrtDeal.premium'/>"
															value="<fmt:formatNumber value="${premium}" pattern="0.00"/>"
															onblur="checkNumber(this)">
															
													</td>
													 -->
													 <input class="free" readonly name="premium"
															description="<s:text name='undwrt.pages.undwrtDeal.premium'/>"
															value="<fmt:formatNumber value="${premium}" pattern="0.00"/>"
															onblur="checkNumber(this)" style="display:none;">
													<%-- modify by yanglibo 20080826 begin 改为千分位 --%>
													<td align="center" colspan="2">
														<input class="free"
															name="dangerShare" readonly description="<s:text name='undwrt.pages.undwrtDeal.dutyCycle'/>"
															value="<fmt:formatNumber value="${dangerShare}" pattern="0.00"/>"
															onblur="checkNumber(this)">
													</td>
													<td rowspan="3">
														<div>
															<s:if test='"4" != "iNodeStatus" && "0" !="iNodeStatus"'>
																<input type=button class=button name="buttonShowItem"
																	onclick="return showDangerItem(this,'DangerUnit','0');"
																	value="<s:text name='undwrt.pages.undwrtDeal.detailedInformation'/>" 
																	style="cursor: hand"/>
															</s:if>
														</div>
														<input type=hidden name="hiDangerNo" value="<s:property value="#dangerDetail.dangerNo"/>">
														<input type=hidden name="isSavaDangerUnit" value="Y" />
													</td>
													<td rowspan="3" style='width: 1%' align="center">
													<div>
														<input type=button name="buttonDelete"
															class=smallbutton
															onclick="deleteTdangerInfo(this,'DangerUnit');" value="-"
															style="cursor: hand"
														<s:if test='editType=="query"'>disabled</s:if>>
													</div>
													</td>
												</tr>

												<tr class=common>
													<td width='15%'>
														<%--险种名称 --%>
														<s:text name="undwrt.pages.undwrtDeal.riskcName"/>
													</td>
										            <td width='10%'>
											            <%--风险等级 --%>
											            <s:text name="undwrt.pages.undwrtDeal.riskGrade"/>
										            </td>
										            <td width='15%'>
											            <%--风险名称 --%>
											            <s:text name="undwrt.pages.undwrtDeal.riskName"/>
										            </td>
													<%
														if (strClassCode.equals("27")
																			&& includeAccident.equals("Y")) {
													%>
													<td width='5%'>
													<%--意健险PML值 --%>
													<s:text name="undwrt.pages.undwrtDeal.accidentHealthInsurancePMLvalue"/>
													</td>
													<td width='15%'>
													<%--自留额 --%>
													<s:text name="undwrt.pages.undwrtDeal.retention"/>
													</td>
													<td width='15%'>
													<%--除外责任/申报业务 --%>
													<s:text name="undwrt.pages.undwrtDeal.exceptResponsibility"/>/<s:text name="undwrt.pages.undwrtDeal.declareBusiness"/>
													</td>
													<%
														} else {
													%>
													<td width='5%'>
														<%--币别 --%>
														<s:text name="undwrt.pages.undwrtDeal.Currency"/>
													</td>
													<td width='15%'>
														<%--自留额 --%>
														<s:text name="undwrt.pages.undwrtDeal.retention"/>
													</td>
													<td width='15%'>
														<%--除外责任/申报业务 --%>
														<s:text name="undwrt.pages.undwrtDeal.exceptResponsibility"/>/<s:text name="undwrt.pages.undwrtDeal.declareBusiness"/>
													</td>
													<%
														}
													%>
													<td width='10%'>
														<%--进合约 --%>
														<s:text name="undwrt.pages.undwrtDeal.intoContract"/>
													</td>
												</tr>
												<tr class=common>
													<td>
														<input type="hidden" name="eRiskCode"
															description="<s:text name='undwrt.pages.undwrtDeal.riskcName'/>"
															value="<s:property value="#dangerDetail.riskCode"/>">
														<input class="free" readonly name="riskName"
															description="<s:text name='undwrt.pages.undwrtDeal.riskcName'/>"
															value="<s:property value="#dangerDetail.riskName"/>">
													</td>
													<td>
														<input class="free" readonly name="riskLevel"
															description="<s:text name='undwrt.pages.undwrtDeal.riskGrade'/>"
															value="<s:property value="#dangerDetail.riskLevel"/>">
													</td>
													<td>
														<input class="free" readonly name="riskLevelDesc"
															description="<s:text name='undwrt.pages.undwrtDeal.riskGradeDescribe'/>"
															value="<s:property value="#dangerDetail.riskLevelDesc"/>">
												   </td>
													<%
														if (strClassCode.equals("27")
																			&& includeAccident.equals("Y")) {
													%>

													<td>
														<input class="free" readonly name="speCurrency"
															style="" description="PML<s:text name='undwrt.pages.undwrtDeal.currency'/>"
															value="<s:property value="#dangerDetail.speCurrency"/>">
														<input class="free" readonly name="speValue"
															style="width: 50%" description="PML<s:text name='undwrt.value'/>"
															value="<fmt:formatNumber value="${speValue}" pattern="0.00"/>">
													</td>
													<td>
														<input class="free" readonly name="retCurrency"
															style="width: 20%" description="<s:text name='undwrt.pages.undwrtDeal.retentionCurrency'/>"
															value="<s:property value="#dangerDetail.retCurrency"/>">
														<input class="free" readonly name="retentionValue"
															style="width: 70%" description="<s:text name='undwrt.pages.undwrtDeal.retentionMoney'/>"
															value="<fmt:formatNumber value="${retentionValue}" pattern="0.00"/>">
												  </td>
													<td>
														<input class="free" type="hidden"
															name="dangerItemKind" description="<s:text name='undwrt.pages.undwrtDeal.exceptResponsibility'/>"
															value="<s:property value="#dangerDetail.itemKind"/>">
														<input type="text" name="dangerItemKindName" class="free"
															value="<s:property value="#dangerDetail.itemKindDesc"/>">
												  	</td>
													<%
														} else {
													%>
													<td>
													<input class="free" readonly name="retCurrency"
														description="<s:text name='undwrt.pages.undwrtDeal.retentionCurrency'/>"
														value="<s:property value="#dangerDetail.retCurrency"/>">
													</td>
													<td>
														<input class="free" readonly name="retentionValue"
															description="<s:text name='undwrt.pages.undwrtDeal.retentionMoney'/>"
															value="<fmt:formatNumber value="${retentionValue}" pattern="0.00"/>">
													</td>
													<td>
														<input class="free" type="hidden"
															name="dangerItemKind" description="<s:text name='undwrt.pages.undwrtDeal.exceptResponsibility'/>"
															value="<s:property value="#dangerDetail.itemKind"/>">
														<input type="text" name="dangerItemKindName" class="free"
															value="<s:property value="#dangerDetail.itemKindDesc"/>">
													</td>
													<%
														}
													%>
													<td>
														<%--进合约 --%>
														<input type="checkbox" align="center"
															name="dangerFlag" description="<s:text name='undwrt.pages.undwrtDeal.intoContract'/>"
														<s:if test="#dangerDetail.flag ==10">checked</s:if>
														<s:if test="#dangerDetail.flag ==11">checked</s:if>
															value="<s:property value="#dangerDetail.flag"/>"
															onclick="return false;">
												  </td>
												</tr>
											</table>
											</td>
										</tr>
									</s:iterator>
								</s:if>
							</tbody>
							<tfoot>
							<!-- 商火危险单位临分功能开发20140307 -->
								<%
									if (!riskUnitFlag.equals("") && riskUnitFlag.equals("1")) {
								%>
								<tr>
									<td>
									<table width="100%">
										<tr>
											<td>
												<%--按"+"号键增加危险单位信息，按"-"号键删除信息 --%>
												(<s:text name="undwrt.pages.undwrtDeal.CommonDangerUnits1"/>,<font color=red><s:text name="undwrt.pages.undwrtDeal.CommonDangerUnits2"/>)</font>
											</td>
											
											<td>
												<div align="center"><input type="button" value="+"
													class=smallbutton
													onclick="insertRow('DangerUnit'); return showDangerItem(this,'DangerUnit','NewDangerNo');"
													name="buttonInsert" style="cursor: hand"
													<%=editType.equals("query") ? "disabled" : ""%>>
												</div>
											</td>
										</tr>
									</table>
									</td>
								</tr>
								<%
									}
								%>
							</tfoot>
						</table>
						</span></td>
					</tr>
				</table>
				</span>
				</td>
			</tr>




			<tr width=100%>
				<td>
				<table width=100% border="0">
					<tr>
						<s:if test='editType =="query"'>
							<td class=button width="33%">
								<%--风险评估信息 --%>
								<input type="button"
									class="longbutton" name="allEvaluate"
									description="<s:text name='undwrt.pages.undwrtDeal.assessRiskMessages'/>"
									value="<s:text name='undwrt.pages.undwrtDeal.assessRiskMessages'/>"
									onclick="viewDangerRiskInfo2(this)">
							</td>
						</s:if>
						<td class=button width="33%">
								<s:if test='editType !="query"'>
								<%--分保试算 --%>
									<Input name="ReinsTrial" type="button" class=button
										value="<s:text name='undwrt.pages.undwrtDeal.reinsuranceTrial'/>"
										onclick="simulateReinsByDanger()">
								<%if(businessFlag.equals("1")){%>
								<%--提交分入确认 --%>
									<Input name="ReinsTrial" type="button" class=longbutton
										value="<s:text name='undwrt.pages.undwrtDeal.submitPointsAffirm'/>"
										onclick="reinsVerify()">
								<%}%>
								<s:if test="#request.AmountAndPremiumDto != null">
									<input type="hidden" name="tolAmount"
										value="<s:property value="#request.AmountAndPremiumDto.amount"/>">
									<input type="hidden" name="tolPremium"
										value="<s:property value="#request.AmountAndPremiumDto.premium" />">
								<s:if test="dangerDetail != null">
										<input type="hidden" name="tolRetentionValue"
											value="<s:property value="#dangerDetail.retentionValue" />">
								</s:if>
							</s:if>
							</s:if>
						 	<s:else>
						 	<%--分保信息 --%>
								<Input name="ReinsTrial" type="button" class=button
									value="<s:text name='undwrt.pages.undwrtDeal.reinsuranceMessages'/>"
									onclick="showSimulateReins()">
							</s:else>
						</td>
						<td class=button width="33%">
							<s:if test='iNodeStatus != "4" && iNodeStatus != "0"'>
								<Input name="butSubmitReins" class="button" type="button" alt="<s:text name='undwrt.pages.undwrtDeal.partIntention'/>"
									value="<s:text name='undwrt.pages.undwrtDeal.partIntention'/>"
									onclick="submitReins()" 
									<%=((editType.equals("query")) || !verifyFlag.equals("0")) ? "disabled" : ""%>/>
							</s:if> 
							<s:if test='iNodeStatus == "4" || iNodeStatus == "0"'>
									<input type="button" class="longbutton"
										value="<s:text name='undwrt.pages.undwrtDeal.backContinueDispose'/>"
										onclick="history.back();">
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
		<!-- 核保时的相关危险单位信息结束 -->


	</table>


	<!--  开始处理核赔时的危险单位
  	//该险种要求拆分危险单位和分摊试算add by qinyongli 2005-8-23
  	--> <s:if test='handType =="22"'>
		<s:if test='riskUnitFlag != "" && riskUnitFlag == "1"'>
			<table class="common" style="width: 99%" cellpadding="5"
				cellspacing="1" align="center" id="HepeiDangerUnit">
				<tr class=listtitle>
					<td colspan="15">
					<%--危险单位分摊信息 --%>
					<s:text name="undwrt.pages.undwrtDeal.dangerComShareMessages"/>
					</td>
				</tr>
				<tr class=common>
					<td>
						<%--序号 --%>
						<s:text name="undwrt.pages.undwrtDeal.serialNo"/>
					</td>
					<td>
						<%--描述--%>
						<s:text name="undwrt.pages.undwrtDeal.describe"/>
					</td>
					<td>
						<%--地址 --%>
						<s:text name="undwrt.pages.undwrtDeal.address"/>
					</td>
					<td>
						<%--币别 --%>
						<s:text name="undwrt.pages.undwrtDeal.currency"/>
					</td>
					<td>
						<%--估损金额 --%>
						<s:text name="undwrt.pages.undwrtDeal.appraisalDamage"/>
					</td>
					<td>
						<%--已决赔款 --%>
						<s:text name="undwrt.pages.undwrtDeal.settledClaim"/>
					</td>
					<td>
						<%--占比 --%>
						<s:text name="undwrt.pages.undwrtDeal.dutyCycle"/>%
					</td>
				</tr>

				<s:if test="dangerDetail != null">
					<s:iterator value="dangerDetail" status="statu" id="dangerDetail">
						<tr class=common>
							<td><input class="formtitle1" readonly name="dangerNo"
								title="<s:text name='undwrt.pages.undwrtDeal.serialNo'/>" value="<s:property value="#dangerDetail.dangerno"/>"></td>
							<td><input class="formtitle1" readonly name="dangerDesc"
								title="<s:text name='undwrt.pages.undwrtDeal.describe'/>"
								value="<s:property value="#dangerDetail.dangerdesc"/>"></td>
							<td><input class="formtitle1" readonly name="dangerAddress"
								title="<s:text name='undwrt.pages.undwrtDeal.address'/>"
								value="<s:property value="#dangerDetail.addressname"/>"></td>
							<td><input class="formtitle1" readonly name="currency"
								title="<s:text name='undwrt.pages.undwrtDeal.currency'/>" value="<s:property value="#dangerDetail.currency"/>"></td>
							<td><input class="formtitle1" readonly name="dangerShare"
								title="<s:text name='undwrt.pages.undwrtDeal.dutyCycle'/>"
								value="<s:property value="#dangerDetail.dangershare"/>"
								onblur="checkNumber(this)"></td>
						</tr>
					</s:iterator>
				</s:if>
				<tr>
					<td>
						<%--分摊试算 --%>
						<input class="button" type="button" value="<s:text name='undwrt.pages.undwrtDeal.reinsTrial'/>"
							onclick="simulateReinsHepei()">
					</td>
				</tr>
			</table>
		</s:if>

		<%
			//不要求拆分危险单位,但是要求分摊试算
				if (!requiredReins.equals("") && requiredReins.equals("1")
						&& riskUnitFlag.equals("0")) {
		%>
		<table>
			<tr align="left">
				<td>
				<%--分摊试算 --%>
				<input class="button" type="button" value="<s:text name='undwrt.pages.undwrtDeal.reinsTrial'/>"
					onclick="simulateReinsHepei()">
				</td>
			</tr>
		</table>
		<%
			}
				//核赔危险单位处理完毕
		%>
	</s:if> </span></td>
</tr>