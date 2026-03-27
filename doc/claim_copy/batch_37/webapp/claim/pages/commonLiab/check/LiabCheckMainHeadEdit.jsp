<%--
****************************************************************************
* DESC       :添加主信息子块界面页面
* AUTHOR     :中科软
* MODIFYLIST :   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ include file="/common/taglibs.jsp"%>
<%--系统内从prpdcompany带出，系统外则从Prplexternalagency中取值--%>
<script language="javascript">
 function selectHandleCodeByUnitType(field,eventType,coordinate,queryType){
	var unitType = fm.unitType.value;
         
	if(eventType == "dbclick" || eventType == "keyup")
	{
		if(unitType==1){
    		code_CodeSelect(field,'prpdcompany',coordinate,'Y',queryType);
       	}else{
    		code_CodeSelect(field,'prpdCustomerUnit',coordinate,'Y',queryType);
    	} 
	}
	else if(eventType == "change")
	{
		if(unitType==1){
	    	code_CodeChange(field,'prpdcompany',coordinate,'Y',queryType);
    	}else{
    		code_CodeChange(field,'prpdCustomerUnit',coordinate,'Y',queryType);
    	} 
	}
 }
     
      function clearHandleUnitCode(){
       fm.prpLcheckHandleUnitCode.value = ""; 
       fm.prpLcheckHandleUnitName.value = "";
       if(fm.unitType.value == "1"){
           fm.freightHeresyCheck.style.display="none";
       }else{
    	   fm.freightHeresyCheck.style.display="";
       }
      }
</script>
<table  class=subtable cellpadding="0" cellspacing="1">
  <tr>
	<td>
	  <table  class=common cellpadding="1" cellspacing="1">
      <tr>
        <td class="left">
          <input type="hidden" name="referKind">
          <input type="hidden" name="prpLcheckRiskCode"  value="${prpLcheck.riskCode}">
          <input type="hidden" name="riskCode"  value="${prpLcheck.riskCode}">
          <input type="hidden" name="riskcode"  value="${prpLcheck.riskCode}">
          <input type="hidden" name="prpLcheckFlag"  value="${prpLcheck.flag}">
          <input type="hidden" name="prpLcheckReferSerialNo" value="${prpLcheck.id.referSerialNo}">
          <input type="hidden" name="prpLcheckInsureCarFlag" value="${prpLcheck.insureCarFlag}">
          <input type="hidden" name="swfLogFlowID" value="${param.swfLogFlowID}">
          <input type="hidden" name="swfLogLogNo" value="${param.swfLogLogNo}">
          <input type="hidden" name="swfLogActorId" value="<c:out value='${param.actorId}'/>">
          <input type="hidden" name="swfLogProcessId" value="<c:out value='${param.processId}'/>">          <input type="hidden" name="registno" value="${prpLcheck.id.registNo}">
          <input type="hidden" name="policyno" value="${prpLcheck.policyNo}">
          <input type="hidden" name="coreURL" value="${core_URL}">
          <input type=hidden name="prpLregistComCode" title="<s:text name='Ownership.institution'/>" class="ReadOnly" ReadOnly style="width:120px" value="${prpLregist.comCode}"/>
           <s:text name="certainLoss.prpLacciCheck.riskCName"/>:<%--险种名称 --%>
        </td> 
        <td class="right">${riskCName}</td>
        <td class="left"><s:text name="certainLoss.prpLacciCheck.prpLacciCheckRegistNo"/></td>  <%--报案号 --%>
        <td class="right">
        	<input type=text name="prpLcheckRegistNo" class="readonly" readonly="true" value="${prpLcheck.id.registNo}">
        </td>
        <td class="left"><s:text name="certainLoss.prpLacciCheck.prpLcheckClaimNo"/></td>  <%--赔案号--%>
        <td class="right">
        	<input type="text" name="prpLcheckClaimNo" class="readonly" readonly="true" value="${prpLcheck.claimNo}">
        </td>
      </tr>
      </table>
      </td>
      </tr>
      </table>
      <br>
      <table  class=subtable cellpadding="0" cellspacing="1">
  <tr>
	<td>
	  <table  class=common cellpadding="1" cellspacing="1">
      <tr>
      	<td class="left"><s:text name="certainLoss.prpLacciCheck.prpLacciCheckPolicyNo"/></td><%--保单号--%>
      	<td class="right">
      		<input type="text" name="prpLcheckPolicyNo" class="readonly" readonly="true" value="${prpLcheck.policyNo}">
      	</td>
      	<td class="left" colspan="4">
      		<input type="image" name="btRelate" src="/claim/images/butRelate.gif" onclick="relate(fm.prpLcheckPolicyNo.value);return false;">
          	<input type="hidden" name="damageDate" value="${prpLcheck.damageStartDate} ">
          	<input type=button class="bigbutton"  name="policyBackWard" value="<s:text name='button.dangerPolicyInfo.value' />" onclick="backWardPolicy(fm.coreURL.value,fm.prpLcheckPolicyNo.value,fm.prpLcheckRiskCode.value,fm.damageDate.value);"> <%--出险时保单信息--%>
      	</td>
      </tr>
      <tr>
      	<td class="left"><s:text name="general.insuredName"></s:text></td><%-- 被保险人 --%>
      	<td class="right">
      		<input type="text" name="insuredName" class="readonly" readonly="true"  value="${prpLcheck.insuredName}">
      	</td>
      	<td class="left"></td>
      	<td class="right"></td>
      	<td class="left"></td>
      	<td class="right"></td>
      </tr>
      </table>
      </td>
      </tr>
      </table>
      <br>
      <table  class=subtable cellpadding="0" cellspacing="1">
  <tr>
	<td>
	  <table  class=common cellpadding="1" cellspacing="1">
      <tr>
      	<td class="left"><s:text name="certainLoss.prpLcheck.prpLcheckDamageStartDate" /></td><%--出险时间--%>
      	<td class="right">
      		<rc:rcDate name="prpLcheckDamageStartDate" style="width:100" value="${prpLcheck.damageStartDate}"/>${prpLcheck.damageStartHour} <s:text name ="regist.prpLregist.hour"/>${prpLcheck.damageStartMinute} <s:text name ="regist.prpLregist.minute"/>
      	</td>
      	<td class="left"><s:text name="certainLoss.prpLcheck.prpLcheckDamageCase" /></td><%--出险原因--%>
      	<td class="right">
      		<input name="prpLcheckDamageCode" class="codecode" style="width:34%" maxlength=3 description="<s:text name='certainLoss.prpLcheck.prpLcheckDamageCase' />" value="${prpLcheck.damageCode}"
             ondblclick="code_CodeSelect(this, 'DamageCode','0,1','Y','Y',fm.riskCode.value);"
             onkeyup= "code_CodeSelect(this, 'DamageCode','0,1','Y','Y',fm.riskCode.value);">
          <input name="prpLcheckDamageName" class="codename"  style="width:46%" maxlength=20 description="<s:text name='certainLoss.prpLcheck.prpLcheckDamageCase' />" value="${prpLcheck.damageName}"
             ondblclick="code_CodeSelect(this, 'DamageCode','-1,0','Y','Y',fm.riskCode.value);"
             onkeyup= "code_CodeSelect(this, 'DamageCode','-1,0','Y','Y',fm.riskCode.value);">
          <img src="/claim/images/bgMarkMustInput.jpg">
      	</td>
      	<td class="left"><s:text name="certainLoss.prpLcheck.prpLcheckDamageTimes" />:</td><%--已出险次数--%>
      	<td class="right">
      		<%@include file="/pages/commonLiab/regist/LiabExistRegist.jsp"%>
      	</td>
      </tr>
      <tr>
      	<td class="left"></td>
      	<td class="right"></td>
      	<td class="left"></td>
      	<td class="right"></td>
      	<td class="left"></td>
      	<td class="right"></td>
      </tr>
      <tr>
      	<td class="left"><s:text name="certainLoss.prpLcheck.prpLcheckDamageAddress" /></td><%--出险地点--%>
      	<td class="right"colspan="5">
      	<select name="countryFlag" style="width:100px" onchange="countryFlag_change(this.options[this.selectedIndex].value)">
        <option value="0"><s:text name="commonAcci.claim.domestic" /></option><%--国内--%>
        <option value="1"><s:text name="commonAcci.claim.abroad" /></option><%--国外--%>
        </select>
        <input type=text class="codecode" name="countryCode" style="display:none"/>
        <input type=text class="codecode" name="countryCName" style="display:none" title="<s:text name='common.select.country'/>" style="width:120px"
        	ondblclick = "code_CodeSelect(this, 'CountryCode','-1,0','Y','N');"
            onkeyup = "code_CodeSelect(this, 'CountryCode','-1,0','Y','N');"
            onchange = "code_CodeSelect(this, 'CountryCode','-1,0','Y','N');"/>
        <input type=hidden class="codecode" name="provinceCode" style="display:none"/>
        <input type=hidden class="codecode" name="provinceName" title="<s:text name='common.select.province'/>" style="width:120px"
        	ondblclick = "code_CodeSelect(this, 'utiAdminProvice','-1,0','Y','N');"
            onkeyup = "code_CodeSelect(this, 'utiAdminProvice','-1,0','Y','N');"
            onchange = "code_CodeSelect(this, 'utiAdminProvice','-1,0','Y','N');"/>
        <input type=text class="codecode" name="prpLcheckAddressCode" style="width: 40px;" title="<s:text name='regist.prpLregist.areaCode'/>" value="${prpLcheck.addressCode}"
			ondblclick="code_CodeSelect(this, 'PostCode','0,1','Y','Y');" onkeyup="code_CodeSelect(this, 'PostCode','0,1','Y','Y');"
			onchange="code_CodeSelect(this, 'PostCode','0,1','Y','Y');" > 
		<input type=text class="codecode" name="prpLcheckAddressName" title="<s:text name='db.prpLclaim.damageAreaName'/>" style="width: 110px" value="${prpLcheck.addressName}"
			ondblclick="code_CodeSelect(this, 'PostCode','-1,0','Y','N');" onkeyup="code_CodeSelect(this, 'PostCode','-1,0','Y','N');"
			onchange="code_CodeSelect(this, 'PostCode','-1,0','Y','N');">
      		<input type="text" name="prpLcheckDamageAddress" onchange="changeLDamageAddress()" style="width:50%" class="input" value="${prpLcheck.damageAddress}"
      		onclick = "showProvinceCity(this,'countryCName','prpLcheckAddressName');" selectValue="${prpLcheck.addressName}">
      	</td>
      </tr>
      <tr>
      	<td class="title" ><s:text name='check.PoliceUnit'/>:</td><%--宪警单位--%>
        <td class="input">
          <input name='prpLcheckPoliceUnit' class='input' maxlength=20 style="width:140px" description="宪警单位" value="${prpLcheck.policeUnit}">
        </td>
      	<td class="left"></td>
      	<td class="right"></td>
      	<td class="left"></td>
      	<td class="right"></td>
      </tr>
      </table>
      </td>
      </tr>
      </table>
      <br>
      <table  class=subtable cellpadding="0" cellspacing="1">
  <tr>
	<td>
	  <table  class=common cellpadding="1" cellspacing="1">
      <tr>
      	<td class="left"><s:text name="certainLoss.prpLcheck.checkType" /></td><%--查勘类型--%>
      	<td class="right">
			<s:select list="#request.checkTypeList" name="checkType" listKey="key" listValue="value" value="#request.prpLcheck.checkType" />
		</td>
      	<td class="left"><s:text name="prpLcheck.checkDate" /></td><%--查勘日期--%>
      	<td class="right">
      		<rc:rcDate  name="prpLcheckCheckDate" style="width:100" class="input" value="${prpLcheck.checkDate}"/>
          <img src="/claim/images/bgMarkMustInput.jpg">
      	</td>
      	<td class="left"><s:text name="certainLoss.prpLcheck.prpLcheckCheckKind" /></td><%--查勘性质--%>
      	<td class="right">
          <s:select list="#request.checkNatures" listKey="newCodeCode" listValue="codeCName" name="checkNature" value="#request.prpLcheck.checkNature"></s:select>
      	</td>
      </tr>
      <tr>
      	<td class="left"><s:text name="certainLoss.prpLcheck.prpLcheckChecker1" /></td><%--查勘人 1--%>
      	<td class="right">
      		<input name='prpLcheckChecker1' class='input' maxlength=20 description="<s:text name='certainLoss.person1'/>" value="${prpLcheck.checker1}">
          <img src="/claim/images/bgMarkMustInput.jpg">
      	</td>
      	<td class="left"><s:text name="certainLoss.prpLcheck.prpLcheckChecker2" /></td><%--查勘人 2--%>
      	<td class="right">
      		<input name='prpLcheckChecker2' class='input' maxlength=20 description="<s:text name='certainLoss.person2'/>" value="${prpLcheck.checker2}">
      	</td>
      	<td class="left"></td>
      	<td class="right"></td>
      </tr>
      <tr>
      	<td class="left"><s:text name="prpLcheck.checkArea" /></td><%--查勘地点--%>
      	<td class="right" colspan="5">
      		<input type="text" name="prpLcheckCheckSite" class="input" style="width:95%" value="${prpLcheck.checkSite}">
          <img src="/claim/images/bgMarkMustInput.jpg">
      	</td>
      </tr>
      <tr>
      	<td class="left"><s:text name="certainLoss.prpLcheck.prpLcheckCheckCom" /></td><%--查勘处理单位--%>
      	<td class="right">
      		<select name='unitType' onchange="clearHandleUnitCode();">
            <option value="1"><s:text name="check.inSystem" /></option><%--系统内--%>
            <option value="0"><s:text name="check.outSystem" /></option><%--系统外--%>
           </select>     
           </td>
      	
      	<td class="left">
      	<c:if test="${not empty prpLcheck.handleUnitCode}">
      		<input name="prpLcheckHandleUnitCode" class="codecode" maxlength=20 description="<s:text name='certainLoss.prpLscheduleMainWF.Unitcode'/>" value="${prpLcheck.handleUnitCode}"
             		ondblclick="selectHandleCodeByUnitType(this,'dbclick','0,1','Y');"
					onkeyup="selectHandleCodeByUnitType(this,'keyup','0,1','Y');">
      	</c:if>
      	<c:if test="${empty prpLcheck.handleUnitCode}">
      		<input name="prpLcheckHandleUnitCode" class="codecode" maxlength=20 description="<s:text name='certainLoss.prpLscheduleMainWF.Unitcode'/>" value="${sessionScope.user.comCode}" 
              		ondblclick="selectHandleCodeByUnitType(this,'dbclick','0,1','Y');"
					onkeyup="selectHandleCodeByUnitType(this,'keyup','0,1','Y');">
      	</c:if>
      	</td>
      	<td class="right">
      	<c:if test="${not empty prpLcheck.handleUnitCode}">
      		<input name="prpLcheckHandleUnitName" class="readonly" maxlength=60 description="<s:text name='certainLoss.prpLscheduleMainWF.Unit'/>" value="${prpLcheck.handleUnit}" readonly>
      	</c:if>
      	<c:if test="${empty prpLcheck.handleUnitCode}">
      		<input name="prpLcheckHandleUnitName" class="readonly" maxlength=60 description="<s:text name='certainLoss.prpLscheduleMainWF.Unit'/>" value="${sessionScope.user.comName}" readonly>
      	</c:if>
      	</td>
      	<td class="left">
      	<c:if test="${prpLcheck.unitType!='0'}">
      		<input type=button class='button' value="<s:text name='button.attorney.value' />" name="freightHeresyCheck" style="display:none"  onclick="heresyCheck()"/><%--委托书--%>
      	</c:if>
		<c:if test="${ prpLcheck.unitType=='0'}">
			<input type=button class='button' value="<s:text name='button.attorney.value' />" name="freightHeresyCheck" onclick="heresyCheck()"/>  <%--委托书--%>
		</c:if>
		</td>
      	<td class="right"></td>
      	 <input type=hidden name='prpLcheckCheckUnitName'  style="width:93%" maxlength=30 description="<s:text name='certainLoss.prpLscheduleMainWF.Unit'/>" value="${prpLcheck.checkUnitName}">
      </tr>
      </table>
      </td>
      </tr>
      </table>
      <br>
