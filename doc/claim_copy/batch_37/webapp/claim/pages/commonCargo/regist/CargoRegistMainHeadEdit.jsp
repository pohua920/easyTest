<%--
****************************************************************************
* DESC       ：添加主信息子块界面页面
* AUTHOR     ： 中科软
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ include file="/common/taglibs.jsp"%>
<s:if test="#request.prpLregist.startHour==0">
	<s:set var="startHour" value="<s:text name='modifySumClaim.comeEffect'/>" scope="page"></s:set><%-- 零时起至 --%>
</s:if>
<s:elseif test="#request.prpLregist.startHour==12">
	<s:set var="startHour" value="<s:text name='regist.from'/>" scope="page"></s:set><%--十二时起至  --%>
</s:elseif>
<s:elseif test="#request.prpLregist.startHour==24">
	<s:set var="startHour" value="<s:text name='regist.start'/>" scope="page"></s:set> <%-- 二十四时起 --%>
</s:elseif>
<s:else>
	<s:set var="startHour" value="" scope="page"></s:set>
</s:else>
<s:if test="#request.prpLregist.endHour==0">
	<s:set var="endHour" value="<s:text name='regist.until'/>" scope="page"></s:set> <%--零时止  --%>
</s:if>
<s:elseif test="#request.prpLregist.endHour==12">
	<s:set var="endHour" value="<s:text name='regist.end'/>" scope="page"></s:set><%-- 十二时止 --%>
</s:elseif>
<s:elseif test="#request.prpLregist.endHour==24">
	<s:set var="endHour" value="<s:text name='modifySumClaim.hourEnd'/>" scope="page"></s:set>  <%--二十四时止  --%>
</s:elseif>
<s:else>
	<s:set var="endHour" value="" scope="page"></s:set>
</s:else>
    <c:if test="${editTypeOther!='SHOWTASK'}">
    <table border="0" align="center" cellpadding="0" cellspacing="0" class="common">
    <tr>
         <input type=hidden name=nodeType value="regist">
     <c:if test="${editType=='ADD'}">
       <td><input type="button" name="messageSave" class="bigbutton" value="<s:text name='button.claimsProcessingRecords.value'/>" onclick="openWinSave('${prpLregist.registNo}','${prpLregist.policyNo}','${prpLregist.riskCode}','regis','')"></td>  <%--赔案处理记录  --%>
    </c:if>
     <c:if test="${editType!='ADD'}">
       <td><input type="button" disabled name="messageSave" class="bigbutton" value="<s:text name='button.claimsProcessingRecords.value'/>" onclick="openWinSave('${prpLregist.registNo}','${prpLregist.policyNo}','${prpLregist.riskCode}','regis','')"></td>  <%-- 赔案处理记录 --%>
    </c:if>
       <td width="70%" align="right"><font color="#666666"><s:text name="scheduleObject.note1"/>　“<font color="#FF0000">*</font>”<s:text name="scheduleObject.note2"/>，“<img src="${ctx}/images/bgDoubleClick2.gif" width="13" height="13" align="absbottom">”<%-- 注：  --%><%-- 为必选项 --%>
     <s:text name="scheduleObject.note3"/> 。</font></td>   <%-- 为双击选择项 --%>
    </tr>
    </table>
    </c:if>
    <table  border="0" align="center" cellpadding="5" cellspacing="1"  class="common">
     <tr class=listtitle><td colspan="4"><s:text name="regist.registration"/> <%-- 货运报案登记 --%>
    	<c:if test="${prpLregist.cancelDate!=null&&prpLregist.cancelDate!=''}">
          <s:text name="prompt.regist.noteEliminate"/> <%-- (已注消) --%>
       </c:if>
     </td>
     </tr>
      <tr>
        <td class="title" colspan="2"><s:text name="regist.registration"/> <%--  货运报案登记--%>
          <input type="hidden" name="prpLregistLFlag" value="L">
          <input type="hidden" name="prpLregistRiskCode"  value="${prpLregist.riskCode}">
          <input type="hidden" name="prpLregistClassCode" value="${prpLregist.classCode}">
          <input type="hidden" name="prpLregistLanguage" value="${prpLregist.language}">
          <input type="hidden" name="language"  title="语种" value="${prpLregist.language}">
          <input type="hidden" name="prpLregistLicenseNo" value="${prpLregist.licenseNo}">
          <input type="hidden" name="prpLregistLicenseColorCode" value="${prpLregist.licenseColorCode}">
          <input type="hidden" name="prpLregistCarKindCode" value="${prpLregist.carKindCode}">
          <input type="hidden" name="prpLregistModelCode" value="${prpLregist.modelCode}">
          <input type="hidden" name="prpLregistEngineNo" value="${prpLregist.engineNo}">
          <input type="hidden" name="prpLregistFrameNo" value="${prpLregist.frameNo}">
          <input type="hidden" name="prpLregistRunDistance" value="${prpLregist.runDistance}">
          <input type="hidden" name="prpLregistUseYears" value="${prpLregist.useYears}">
          <input type="hidden" name="prpLregistBrandName" value="${prpLregist.brandName}">
          <input type="hidden" name="prpLregistTypeForDriver" value="Regist">
          <input type="hidden" name='prpLregistEditType'value="${prpLregist.editType}">
          <input type="hidden" name='prpLregistDrivingLicenseNo'>
          <input type="hidden" name='prpLregistDrivingName' >
          <input type="hidden" name='prpLregistDrivingSex'>
          <input type="hidden" name='prpLregistDrivingIdentifyNumber' >
          <input type="hidden" name='prpLregistDrivingAge'>
          <input type="hidden" name='prpLregistDrivingOccupation'>
          <input type="hidden" name='prpLregistDrivingOccupationName'>
          <input type="hidden" name='prpLregistDrivingEducation'>
          <input type="hidden" name='prpLregistDrivingEducationName'>
          <input type="hidden" name='prpLregistDrivingUnitAddress'>
          <input type="hidden" name='prpLregistDrivingReceiveLicenseDate'>
          <input type="hidden" name='prpLregistDrivingCarType'>
          <input type="hidden" name='prpLregistDrivingAwardLicenseOrgan'>
          <input type="hidden" name="prpLregistLossQuantity" value="${prpLregist.lossQuantity}">
          <input type="hidden" name="prpLregistRunDistance" value="${prpLregist.runDistance}">
          <input type="hidden" name='riskcode' value="${prpLregist.riskCode}">
          <input type="hidden" name='policyno' value="${prpLregist.policyNo}">
          <input type="hidden" name='registno' value="${prpLregist.registNo}">
          <input type="hidden" name="prpLregistOthFlag" value="${prpLregist.othFlag}">  
          <input type="hidden" name="underWriteEndDate" value="${prpLregist.underWriteEndDate}">  
          <input type="hidden" name="coreURL" value="${core_URL }">
          <input type="hidden" name='prpLregistPayFee' value="${prpLregist.payFlag}">
   	      <input type="hidden" name='delinquentfeeCase' value="${delinquentfeeCase}">          
          <!--<input type="hidden" name="PerilCount" value="${prpLregistDto1.perilCount}">-->
          <input type="hidden" name="RecentCount" value="${prpLregistDto1.recentCount}">
          <input type="hidden" name="RegistViewLimitDay" value="${registViewLimitDay }">
          <input type="hidden" name="prpLregistFlowInTime" value="${prpLregist.flowInTime}">
          <input type="hidden" name="prpLregistSignDate" value="${prpLregist.signDate}">
          <input type="hidden" name="endorType" value="${endorType}">
          <input type="hidden" name="originalRequestURITemp" value="${sessionScope.originalRequestURITemp}">
      </td>
        <td class="title"><s:text name="query.xianzhongName"/>:</td> <%-- 险种名称 --%>
        <td class="title">${riskCName }</td>
      </tr>
      <tr>
        <%--加入报案出险延期天数 --%>
          <s:if test="#attr.configValue!=null&&#attr.configValue!=''">
          <input type="hidden" name='configValue' value="${configValue}">
          </s:if>
          <s:else>
          <input type="hidden" name='configValue' value="99999">
          </s:else>
         <s:if test="#attr.shareHolderFlag!=null&&#attr.shareHolderFlag!=''">
          <input type="hidden" name="shareHolderFlag" value="${shareHolderFlag}">
         </s:if>
         <s:else>
          <input type="hidden" name="shareHolderFlag" value="0">
         </s:else>
        <td class="title" style="width:15%"><s:text name="db.prpLregist.registNo" />:</td>
        <td class="input" style="width:35%" >
          <input type=text name="prpLregistRegistNo" class="readonly" readonly="true" value="${prpLregist.registNo}">
        </td>
        <td class="title" style="width:15%" style="valign:bottom" ><s:text name="db.prpLregist.policyNo" />:</td>
        <td class="input" style="width:35%" style="valign:middle">
          <input type=text name="prpLregistPolicyNo" class="readonly" readonly="true" style="width:140px" value="${prpLregist.policyNo}">
          <input type="image" name="btRelate" src="${ctx}/images/butRelate.gif" align="middle" width="54" height="17" border="0" onclick="relate(fm.prpLregistPolicyNo.value);return false;">
        </td>
      </tr>
      <tr>
        <td class="title"><s:text name="db.prpLregist.insuredCode" />:</td>
        <td class="input" >
        <span id=insuredCode>${prpLregist.insuredCode}</span>
          <!--</a>-->
        </td>
        <td class="title"><s:text name="certainLoss.prpLcheck.prpLcheckDamageTimes"/>:</td><%-- 已出险次数 --%>
        <td class="input">
         <%-- 出险信息画面 --%>
           <%@include file="/pages/common/regist/ExistRegist.jsp"%>
        </td>
      </tr>
      <tr>
        <td class="title" id="InsuredNameID"><s:text name="db.prpLregist.insuredName" />:</td>
        <td class="input" colspan="1">
          <input type=hidden name="prpLregistInsuredCode" title='<s:text name="db.prpCmain.insuredCode"/>' class="readonly" readonly="true" value="${prpLregist.insuredCode}">
          <input type=text name="prpLregistInsuredName" title="<s:text name="db.prpCmain.insuredName"/>"
           class="codecode"
           style="width:60%" 
           ondblclick="getCinsured(this);"
           onkeyup="getCinsured(this);" onchange="getCinsured(this);"
           value="${prpLregist.insuredName}">
        </td>
        <td class="title"><s:text name="db.prpCmain_cargo.conveyance"/>:</td> <%-- 装载运输工具 --%>
        <td class="input"><input  name="prpLregistCargoName" class="readonly" readonly="true" value="<%=(String)request.getAttribute("bLNo")%>"></td>
      </tr>
      <tr>
        <td class="title"><s:text name="db.prpCcargoDetail.startDate"/></td> <%-- 起运日期 --%>
        <td class="input" colspan=1>
          <rc:rcDate name="prpLregistStartDate" title="<s:text name='db.prpCmain.startDate'/>" class="readonly" style="width:80px" readonly="true" wdatePicker="false" value="${prpLregist.startDate}"/>${startHour }
          <input type=hidden name="prpLregistEndDate"   title="<s:text name='db.prpCmain.endDate'/>" class="readonly" style="width:80px" readonly="true"   value="${prpLregist.endDate}">${endHour }
        </td>
        <td class="input" colspan=3>
          <input type="hidden" name="damageDate" value="${damageDate }">
          <input type=button class="bigbutton"  name="policyBackWard" value="<s:text name='button.dangerPolicyInfo.value'/>" onclick="backWardPolicy(fm.coreURL.value,fm.prpLregistPolicyNo.value,fm.prpLregistRiskCode.value,fm.prpLregistDamageStartDate.value,fm.prpLregistComCode.value);"> <%-- 出险时保单信息 --%>
         </td>
      </tr>
      <tr>
          <td class="title"><s:text name="db.prpCcargoDetail.startSiteName"/></td>  <%-- 起运地 --%>
          <td class="input"><input type='text' style="width:120px"  class="readonly" readonly="true"  value="${prpCmain_cargo.startSiteName}"/></td>
          <td class="title"><s:text name="db.prpCcargoDetail.endSiteName"/></td> <%-- 目的地 --%>
          <td class="input"><input type='text' style="width:120px" class="readonly" readonly="true"  value="${prpCmain_cargo.endSiteName}" /></td>
      </tr>
       <tr>
          <td class="title"><s:text name="db.prpLclaimagent.conveyance"/></td>   <%-- 运输方式 --%>
          <td class="input"><input type='text' style="width:120px" class="readonly" readonly="true"  value="${prpCmain_cargo.conveyance}"/></td>
          <td class="title"><s:text name="db.prpLassure.voyage"/></td> <%-- 航次 --%>
          <td class="input"><input type='text' style="width:120px" class="readonly" readonly="true"  value="${prpCmain_cargo.voyageNo}"/></td>
      </tr>
      <tr>
        <td class="title"><s:text name="db.prpLregist.reportorName" />:</td>
        <td class="input">
          <!-- mantis： CLM0017，處理人員：Sam，需求單編號：CLM0017，原住名姓名調整作業_車 -->
          <input type=text name="prpLregistReportorName" title='<s:text name="db.prpLregist.reportorName" />' class="input"  maxlength="100" style="width:120px"  value="${prpLregist.reportorName}"
           onchange='changeLxr();'>
        </td>
        <td class="title"><s:text name="prpLregist.reportorNumber"/>：</td> <%-- 报案人电话 --%>
        <td class="input">
          <input type=text name="prpLregistReportorPhoneNumber" class="input" style="width:120px" maxlength="12" value="${prpLregist.reportorPhoneNumber}">
        </td>
      </tr>
      <tr>
        <td class="title"><s:text name="prpLregist.reportHour"/>:</td> <%-- 报案时间 --%>
        <td class="input">
          <rc:rcDate name="prpLregistReportDate" style="width:100px" value="${prpLregist.reportDate}" /><s:text name="regist.prpLregist.date"/>
          <input name="prpLregistReportHour" class="input" maxlength="2" style="width:20px"  class="readonly" readonly="true"   value="${prpLregist.reportHour}"><s:text name="regist.prpLregist.hour"/><%-- 时 --%>
          <input name="prpLregistReportMinute" class="input" maxlength="2" style="width:20px"   class="readonly" readonly="true" value="${prpLregist.reportMinute}"><s:text name="regist.prpLregist.minute"/> <%-- 分 --%>
          <img src="${ctx}/images/bgMarkMustInput.jpg">
        </td>
        <td class="title"></td>
        <td class="input">
        </td>
      </tr>
      <tr >
        <td class="title"><s:text name="db.prpLregist.reportType" />:</td>
        <td class="input">
        <s:select name="reportType" list="#request.reportTypes" id="reportType" listKey="id.codeCode" listValue="codeCName" value="#request.prpLregist.reportType" styleClass="three"  style="width:120px"></s:select>
        </td>
        <td class="title"><s:text name="regist.reportDate" />:</td>
        <td class="input">
           <rc:rcDate name="prpLregistInputDate"  title="<s:text name='db.prpLreclaim.inputDate'/>" class="readonly" style="width:80px" readonly="true" value="${prpLregist.inputDate}"/>
        </td>
      </tr>
      <tr>
        <td class="title"><s:text name="db.prpLregist.linkerName" />:</td>
        <td class="input">
          <input type=text name="prpLregistLinkerName" title="<s:text name="db.prpDagent.linkerName" />" class="input" style="width:120px" value="${prpLregist.linkerName}"/>
          <img src="${ctx}/images/bgMarkMustInput.jpg">
        </td>
        <td class="title"><s:text name="db.prpLregist.phoneNumber" />:</td>
        <td class="input">
          <input type=text name="prpLregistPhoneNumber" title="<s:text name="db.prpLregist.phoneNumber " />" class="input" style="width:120px" value="${prpLregist.phoneNumber}">
          <img src="${ctx}/images/bgMarkMustInput.jpg">
        </td>
      </tr>
      <tr>
        <td class="title">
			<s:text name="db.prpLregist.linkerAddress" />
			:</td>
        <td class="input" colspan=3>
          <input type=text name="prpLregistLinkerAddress" title="<s:text name="certainLoss.prpLcheck.Address" />" class="input"  style="width:92%"  value="${prpLregist.linkerAddress}">
         
        </td>
      </tr>
      <%@ include file="/pages/commonCargo/regist/CargoRegistLinkmanEdit.jsp" %>
      <tr>
        <td class="title"><s:text name="certainLoss.prpLcheck.prpLcheckDamageStartDate"/>:</td> <%-- 出险时间 --%>
        <td class="input">
          <rc:rcDate name="prpLregistDamageStartDate" title="<s:text name='regist.prpLregist.damageTime'/>"   style="width:100px" value="${prpLregist.damageStartDate}"/><s:text name='regist.prpLregist.date'/> <%-- 日 --%>
             <input type=text name="prpLregistDamageStartHour" title="<s:text name="db.prpLregist.damageHour" />" class="input" maxlength="2" style="width:20px" value="${prpLregist.damageStartHour}"><s:text name="regist.prpLregist.hour"/><%-- 时 --%>
             <input type=text name="prpLregistDamageStartMinute" title="<s:text name="db.prpLregist.damageMinute" />" class="input" maxlength="2" style="width:20px" value="${prpLregist.damageStartMinute}"><s:text name="regist.prpLregist.minute"/> <%-- 分 --%>
          <img src="${ctx}/images/bgMarkMustInput.jpg">
        </td>
        <td class="title"><s:text name="db.prpLregist.damageCode" />:&nbsp;
        </td>
        <td class="input">
           <input type=hidden class="codecode" name="prpLregistDamageCode"  style="width:40px" title="<s:text name="db.prpLregist.damageCode" />" value="${prpLregist.damageCode}"
             ondblclick="code_CodeSelect(this, 'DamageCode','0,1','Y','Y',fm.riskcode.value);"
             onkeyup= "code_CodeSelect(this, 'DamageCode','0,1','Y','Y',fm.riskcode.value);"
             onchange= "code_CodeChange(this, 'DamageCode','0,1','Y','Y',fm.riskCode.value);">
           <input type=text class="codecode" name="prpLregistDamageName"  title="<s:text name="db.prpLregist.damageCode" />" style="width:120px" value="${prpLregist.damageName}"
             ondblclick="code_CodeSelect(this, 'DamageCode','-1,0','Y','N',fm.riskcode.value);"
             onkeyup= "code_CodeSelect(this, 'DamageCode','-1,0','Y','N',fm.riskcode.value);"
             onchange= "code_CodeChange(this, 'DamageCode','-1,0','Y','N',fm.riskcode.value);">
          <img src="${ctx}/images/bgMarkMustInput.jpg">
        </td>
      </tr>

      <tr>
        <td class="title"><s:text name="db.prpLregist.areaPostCode" />:</td>
        <td class="input" colspan='3'>
          <input type=text name="prpLregistAddressCode"  maxlength="3"   title="<s:text name="db.prpLregist.damageAreaPostCode"/>" class="input"  style="width:80px" value="${prpLregist.addressCode}">
        </td>
      </tr>
      <tr>
      <!--原因：出险地默认为目的地，从货运险保单信息--->
        <td class="title"><s:text name="db.prpLregist.damageAddress" />:</td>
        <td class="input" colspan='3'>
          <select name="countryFlag" style="width:100px" onchange="countryFlag_change(this.options[this.selectedIndex].value)">
            <option value="0"><s:text name="commonAcci.claim.domestic"/></option> <%-- 国内 --%>
            <option value="1"><s:text name="commonAcci.claim.abroad"/></option> <%-- 国外 --%>
          </select>
          <input type=text class="codecode" name="foreignCountryCode" style="display:none"/>
          <input type=text class="codecode" name="foreignCountryName" style="display:none" title="<s:text name="common.select.country"/>" style="width:120px"
              ondblclick = "code_CodeSelect(this, 'foreignCountryCode','-1,0','Y','N');clearPortCode();"
              onkeyup = "code_CodeSelect(this, 'foreignCountryCode','-1,0','Y','N');clearPortCode();"
              onchange = "code_CodeSelect(this, 'foreignCountryCode','-1,0','Y','N');clearPortCode();"/>
          <input type=text class="codecode" name="portCode" style="display:none"/>
          <input type=text class="codecode" name="portCName" title="<s:text name="common.select.port"/>" style="width:120px"
        	  ondblclick = "code_CodeSelect(this, 'portCode','-1,0','Y','N');"
              onkeyup = "code_CodeSelect(this, 'portCode','-1,0','Y','N');"
              onchange = "code_CodeSelect(this, 'portCode','-1,0','Y','N');"/>
          <input type=text name="prpLregistDamageAddress" title="<s:text name="db.prpLregist.damageAddress"/>" Class="input" style="width:350px" value="${prpLregist.damageAddress}" 
              onclick = "showPort(this);"/>
          <img src="${ctx}/images/bgMarkMustInput.jpg">
        </td>
      </tr>
      <tr>
        <td class="title"><s:text name="regist.prpLregist.currency"/>:</td><%--  币别--%>
        <td class="input" colspan=3>
        <input type="text" name="prpLregistEstiCurrency" value="${prpLregist.estiCurrency}" class="readonly" readonly style="width:30%" title="<s:text name="db.prpLcomponent.currency"/>"
         >
        <input type=text name="prpLregistEstiCurrencyName" class="readonly" readonly style="width:60%" title="<s:text name="db.prpLcomponent.currency"/>"  value="${prpLregist.estiCurrencyName}">
			<img src="${ctx}/images/bgMarkMustInput.jpg">
        </td>
      </tr>
      <!-- reason:隐藏：报损费用  -->
      <tr>
        <td class="title"><%--<s:text name="db.prpLregist.estimateLoss" />--%><s:text name="certainLoss.prpLscheduleMainWF.LossSum"/></td> <%-- 报损金额 --%>
        <td class="input" colspan=3>
        <input type=text name="prpLregistEstimateLoss" title="<s:text name="certainLoss.prpLscheduleMainWF.prpLregistEstimateLoss"/>" Class="input" style="width:80px" value="<fmt:formatNumber value='${prpLregist.estimateLoss}' pattern='#'/>" onblur="checkLength(this);">
        <input type=hidden  name="prpLregistEstimateFee" title="<s:text name="common.report.charge"/>" Class="input" style="width:80px" value="<fmt:formatNumber value='${prpLregist.estimateFee}' pattern='#'/>">
      </tr>
