<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app" %>
<%@page import="com.sinosoft.sysframework.common.datatype.*"%>
<%@page import="com.sinosoft.claim.dto.domain.PrpLagentDto"%>
<%
	PrpLagentDto prpLagentDto1 = (PrpLagentDto) request
			.getAttribute("prpLagentDto1");
	PrpLagentDto prpLagentDto = (PrpLagentDto) request
			.getAttribute("prpLagentDto");
%>
<html locale="true">
<head>
</head>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<app:claimCodeInput />
<body onload="initPage();">
	<form name=fm action="/claim/advance.do" method="post" onsubmit="">
		<input type="hidden" name="full_report_no1" value="">
		<input type="hidden" name="full_car_mark1" value="">
		<input type="hidden" name="full_vehicle_type1" value="">
		<input type="hidden" name="Underwriteflag" value="0">
		<input type="hidden" name="claimType" value="1">
		<input type="hidden" name="flagAgent" value="2">
		<table width="100%" border="0" align="center" cellpadding="4" cellspacing="1" class="common">
			<tr>
				<td colspan="4" class="formtitle">
					<s:text name="compensate.agentInfo.payInfoEntry" />
				</td>
				<%-- 垫付信息輸入界面 --%>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="compensate.agentInfo.paymCode" />
				</td>
				<%-- 垫付赔案编码 --%>
				<td class='input'>
					<input type=text name="advance_no" style="color: '#9B009B'" class="query" readonly>
				</td>
				<td class='title'>
					<s:text name="compensate.agentInfo.withoutAccounNnum" />
				</td>
				<%-- 无责方报案号 --%>
				<td class='input'>
					<input type=text name="null_report_no" class="query">
					<img name=buttonDistribute src="/claim/images/butDeal.gif" border="0" hspace="5" alt="手写輸入"> <img src="/claim/images/bgMarkMustInput.jpg">
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="compensate.agentInfo.noResponCompanyNum" />
				</td>
				<%-- 无责方公司号码 --%>
				<td class='input'>
					<input type=text style="width: 60%" name="null_company" class="codecode" ondblclick="code_CodeSelect(this, 'NullCompanyCode','0,1','Y');"
						onkeyup="code_CodeSelect(this, 'NullCompanyCode','0,1','Y');" onchange="code_CodeSelect(this, 'NullCompanyCode','0,1','Y');">
					<img name=buttonDistribute src="/claim/images/butDeal.gif" border="0" hspace="5" alt="手写輸入"> <img src="/claim/images/bgMarkMustInput.jpg">
				</td>
				<td class='title'>
					<s:text name="compensate.agentInfo.noResponCompanyName" />
				</td>
				<%-- 无责方公司名称 --%>
				<td class='input'>
					<input type=text style="width: 60%" name="null_com_name" class="codecode" ondblclick="code_CodeSelect(this, 'NullCompanyCode','-1,0','Y','N');"
						onkeyup="code_CodeSelect(this, 'NullCompanyCode','-1,0','Y','N');" onchange="code_CodeSelect(this, 'NullCompanyCode','-1,0','Y','N');">
					<img name=buttonDistribute src="/claim/images/butDeal.gif" border="0" hspace="5" alt="手写輸入"> <img src="/claim/images/bgMarkMustInput.jpg">
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="compensate.agentInfo.noResponClaimNo" />
				</td>
				<%-- 无责方赔案号 --%>
				<td class='input'>
					<input type=text name="null_claim_code" class="query">
					<img name=buttonDistribute src="/claim/images/butDeal.gif" border="0" hspace="5" alt="手写輸入">
				</td>
				<td class='title'>
					<s:text name="compensate.agentInfo.noResponPolityNo" />
				</td>
				<%-- 无责方保单号 --%>
				<td class='input'>
					<input type=text name="null_policy_code" class="query">
					<img name=buttonDistribute src="/claim/images/butDeal.gif" border="0" hspace="5" alt="手写輸入">
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="compensate.agentInfo.noResponInsurConfirm" />
				</td>
				<%-- 无责方投保确认码 --%>
				<td class='input'>
					<input type=text name="null_confirm_sequence_no" class="query">
					<img name=buttonDistribute src="/claim/images/butDeal.gif" border="0" hspace="5" alt="手写輸入">
				</td>
				<td class='title'>
					<s:text name="compensate.agentInfo.noResponInsur" />
				</td>
				<%-- 无责方被保险人 --%>
				<td class='input'>
					<input type=text name="null_insured" class="query">
					<img name=buttonDistribute src="/claim/images/butDeal.gif" border="0" hspace="5" alt="手写輸入"> <img src="/claim/images/bgMarkMustInput.jpg">
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="compensate.agentInfo.noResponLicenseNum" />
				</td>
				<%-- 无责方车牌号码 --%>
				<td class='input'>
					<input type=text name="null_car_mark" class="query">
					<img name=buttonDistribute src="/claim/images/butDeal.gif" border="0" hspace="5" alt="手写輸入"> <img src="/claim/images/bgMarkMustInput.jpg">
				</td>
				<td class='title'>
					<s:text name="compensate.agentInfo.noResponLicenseType" />
				</td>
				<%-- 无责方号牌种类 --%>
				<td class='input'>
					<input type=text style="width: 60%" name="null_vehicle_type" class="codecode" ondblclick="code_CodeSelect(this, 'LicenseKindCode','0,1','Y');"
						onkeyup="code_CodeSelect(this, 'LicenseKindCode','0,1','Y');" onchange="code_CodeSelect(this, 'LicenseKindCode','0,1','Y');">
					<input type=hidden name="null_car_mark_name" class="codecode">
					<img name=buttonDistribute src="/claim/images/butDeal.gif" border="0" hspace="5" alt="手写輸入"> <img src="/claim/images/bgMarkMustInput.jpg">
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="compensate.agentInfo.noResponDriverName" />
				</td>
				<%-- 无责方驾驶员名称 --%>
				<td class='input'>
					<input type=text name="null_driver_name" class="query">
					<img name=buttonDistribute src="/claim/images/butDeal.gif" border="0" hspace="5" alt="手写輸入">
				</td>
				<td class='title'>
					<s:text name="compensate.agentInfo.noResponDriverCateNo" />
				</td>
				</td>
				<%-- 无责方驾驶员证件号码 --%>
				<td class='input'>
					<input type=text name="null_driver_code" class="query">
					<img name=buttonDistribute src="/claim/images/butDeal.gif" border="0" hspace="5" alt="手写輸入">
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="compensate.agentInfo.fullResponAmount" />
				</td>
				<%-- 全责方定损金额 --%>
				<td class='input'>
					<input type=text name="estimated_amount" class="query">
					<img src="/claim/images/bgMarkMustInput.jpg">
				</td>
				<td class='title'>
					<s:text name="compensate.agentInfo.noResponPayable" />
				</td>
				<%-- 无责方赔偿金额 --%>
				<td class='input'>
					<input type=text name="settlement_amount" class="query">
					<img src="/claim/images/bgMarkMustInput.jpg">
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="compensate.agentInfo.fullResponPlateNo" />
				</td>
				<%-- 全责方号牌号码 --%>
				<td class='input'>
					<input type=text name="full_car_mark" class="query">
				</td>
				<td class='title'>
					<s:text name="compensate.agentInfo.fullResponPlateType" />
				</td>
				<%-- 全责方号牌种类 --%>
				<td class='input'>
					<input type=text name="full_vehicle_type" class="query">
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="compensate.agentInfo.termPayment" />
				</td>
				<%-- 付款方式 --%>
				<td class='input'>
					<select style="font-size: 11pt; border: #009966 1px solid; background-color: #ffffff; width: 60%; color: #000000" name="pay_mode">
						<option value="1">
							<s:text name="compensate.agentInfo.cash" />
						</option>
						<%-- 现金 --%>
						<option value="2">
							<s:text name="compensate.agentInfo.cheque" />
						</option>
						<%-- 支票 --%>
						<option value="3">
							<s:text name="compensate.agentInfo.transfer" />
						</option>
						<%-- 转帐 --%>
						<option value="9">
							<s:text name="regist.prpLregist.other" />
						</option>
						<%-- 其他 --%>
					</select> <img name=buttonDistribute src="/claim/images/butDeal.gif" border="0" hspace="5" alt="手写輸入"> <img src="/claim/images/bgMarkMustInput.jpg">
				</td>
				<td class='title'>
					<s:text name="compensate.agentInfo.fullResponReportNo" />
				</td>
				<%-- 全责方报案号 --%>
				<td class='input'>
					<input type=text name="full_report_no" class="query" readonly>
					<img src="/claim/images/bgMarkMustInput.jpg">
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="compensate.agentInfo.fullResponClaimNo" />
				</td>
				<%-- 全责方赔案号 --%>
				<td class='input'>
					<input type=text name="full_claim_code" class="query" readonly>
					<img src="/claim/images/bgMarkMustInput.jpg">
				</td>
			</tr>
		</table>
		<table width=100%>
			<tr>
				&nbsp
				<s:text name="compensate.agentInfo.solelyOpinion" />
				<%-- 全责方意见 --%>
			</tr>
			<tr>
				<td class='input' width=90%>
					<textarea style="wrap: hard" rows="4" name="full_comments"></textarea>
				</td>
			</tr>
		</table>
		<table width=100% id="buttonArea">
			<tr>
				<td class='button' width=30% align="center">
					<input id="button" type=button class='button' value="<s:text name='button.automatValue.value' />" onClick="fuzhi25();">
				</td>
				<%-- 自动带入值 --%>
				<td class='button' width=30% align="center">
					<input id="button" type=button class='button' value="<s:text name='button.clear.value' />" onClick="clearIt();">
				</td>
				<%-- 清  空 --%>
				<td class='button' width=30% align="center">
					<input id="button" type=button class='button' value="<s:text name='button.save.value' />" onClick="javascript:if(fm.full_report_no.value==''){alert('全责方报案号不能为空！');return false;}submit();">
				</td>
			</tr>
		</table>
	</form>
</body>
<img name=buttonDistribute src="/claim/images/butDeal.gif" border="0" hspace="5" alt="手写輸入">&nbsp&nbsp
<s:text name="compensate.agentInfo.behalfNeedInput" />
<%-- 代表需要手写輸入 --%>
<img src="/claim/images/bgMarkMustInput.jpg">&nbsp&nbsp
<s:text name="compensate.agentInfo.behalfInfoUpload" />
<%-- 代表在上传平台信息时必须有值 --%>
<script language="javascript">
<%if (prpLagentDto != null) {
				prpLagentDto1 = prpLagentDto;%>
    fm.advance_no.value = '<%=prpLagentDto.getAdvanceNo()%>';  
    fm.null_report_no.value = '<%=prpLagentDto.getNullReportNo()%>'; 
    fm.null_company.value = '<%=prpLagentDto.getNullCompany()%>';
    fm.null_com_name.value = '<%=prpLagentDto.getNullComName()%>';
    fm.null_claim_code.value = '<%=prpLagentDto.getNullClaimCode()%>';
    fm.null_policy_code.value = '<%=prpLagentDto.getNullPolicyCode()%>
	';
	fm.null_confirm_sequence_no.value = '
<%=prpLagentDto.getNullConfirmSequenceNo()%>';
    fm.null_insured.value = '<%=prpLagentDto.getNullInsured()%>';
    fm.null_car_mark.value = '<%=prpLagentDto.getNullCarMark()%>';
    fm.null_vehicle_type.value = '<%=prpLagentDto.getNullVihecleType()%>';
    fm.null_driver_name.value = '<%=prpLagentDto.getNullDriverName()%>';
    fm.null_driver_code.value = '<%=prpLagentDto.getNullDriverCode()%>';
    fm.estimated_amount.value = '<%=prpLagentDto.getEstimatedAmount()%>';
    fm.settlement_amount.value = '<%=prpLagentDto.getSettleMentAmount()%>';
    fm.full_car_mark.value = '<%=prpLagentDto.getFullCarMark()%>';
    fm.full_vehicle_type.value = '<%=prpLagentDto.getFullVihecleType()%>';
    fm.pay_mode.value = '<%=prpLagentDto.getPayMode()%>';
    fm.full_report_no.value = '<%=prpLagentDto.getFullReportNo()%>';
    fm.full_claim_code.value = '<%=prpLagentDto.getFullClaimCode()%>';
    fm.full_comments.value = '<%=prpLagentDto.getFullComment()%>';
    var caseStatus = '<%=prpLagentDto.getCaseStatus()%>';
    if(caseStatus!='00'&&caseStatus!='10'&&caseStatus!='')
    {
       readonlyAllInput();
       disabledAllButton('buttonArea');
    }
 <%}%>
 <%if (prpLagentDto1 != null) {%>
    fm.full_report_no1.value = '<%=prpLagentDto1.getFullReportNo()%>';
    fm.full_car_mark1.value = '<%=prpLagentDto1.getFullCarMark()%>';        
    fm.full_vehicle_type1.value = '<%=prpLagentDto1.getFullVihecleType()%>';
<%}%>
 function fuzhi25()
 {
    var licenseNo = '';
    if(window.opener.fm.prpLcarLossInsureCarFlag)
    {
       for(var index=1;index<window.opener.fm.prpLcarLossInsureCarFlag.length;index++)
       {
          if('1'==window.opener.fm.prpLcarLossInsureCarFlag[index].value)
          {
             licenseNo = window.opener.fm.prpLcarLossLossItemName[index].value;
             break;
          }
       }
    }
    if(window.opener.fm.prpLlossDtoSerialNo)//商业险正常流程理算环节获取定损及赔偿金额
    {
	    for(var index=1;index<window.opener.fm.prpLlossDtoSerialNo.length;index++)
	    {
	      if('A'==window.opener.fm.prpLlossDtoKindCode[index].value||(ConstantCodes.KINDCODE_D_BZ==window.opener.fm.prpLlossDtoKindCode[index].value && window.opener.fm.licenseNo[index].value==licenseNo))
	      {
		      fm.estimated_amount.value =window.opener.fm.prpLlossDtoSumLoss[index].value;
		      if(parseFloat(fm.estimated_amount.value)<=400)fm.settlement_amount.value = fm.estimated_amount.value;
		      else 
		      fm.settlement_amount.value = 400;
		      break;
	      }
	    }
    }
    else if(window.opener.fm.propSerialNo)//交强险正常流程理算环节获取定损及赔偿金额
    {
      for(var index=1;index<window.opener.fm.propSerialNo.length;index++)
	    {
	      if(window.opener.fm.prpLcompensateLicenseNo.value==window.opener.fm.propLicenseNo[index].value)
	      {
		      fm.estimated_amount.value =window.opener.fm.propSumLoss[index].value;
		      if(parseFloat(fm.estimated_amount.value)<=400)fm.settlement_amount.value = fm.estimated_amount.value;
		      else 
		      fm.settlement_amount.value = 400;
		      break;
	      }
	    }
    }
    if(window.opener.fm.prpLcompensateClaimNo)//正常流程理算环节获取赔案号
    {
       fm.full_claim_code.value = window.opener.fm.prpLcompensateClaimNo.value;
    }
    else if(window.opener.fm.prpLclaimNo)//简易赔案环节获取赔案号
    {
       fm.full_claim_code.value = window.opener.fm.prpLclaimNo.value;
       
       if(fm.full_claim_code.value == '')
       {
          fm.full_claim_code.value = window.opener.fm.compelPrpLclaimNo.value;
       }
    }
    fm.full_report_no.value = fm.full_report_no1.value;
    fm.full_car_mark.value = fm.full_car_mark1.value;
    fm.full_vehicle_type.value = fm.full_vehicle_type1.value;
 }
 function clearIt()
 {
    fm.advance_no.value = '';  
    fm.null_report_no.value = ''; 
    fm.null_company.value = '';
    fm.null_com_name.value = '';
    fm.null_claim_code.value = '';
    fm.null_policy_code.value = '';
    fm.null_confirm_sequence_no.value = '';
    fm.null_insured.value = '';
    fm.null_car_mark.value = '';
    fm.null_vehicle_type.value = '';
    fm.null_driver_name.value = '';
    fm.null_driver_code.value = '';
    fm.estimated_amount.value = '';
    fm.settlement_amount.value = '';
    fm.full_car_mark.value = '';
    fm.full_vehicle_type.value = '';
    fm.pay_mode.value = '';
    fm.full_report_no.value = '';
    fm.full_claim_code.value = '';
    fm.full_comments.value = '';
 }
</script>
</html>
