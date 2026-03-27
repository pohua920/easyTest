<%--
****************************************************************************
* DESC       :添加主信息子块界面页面
* AUTHOR     : 中科软
* MODIFYLIST :   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%> 
<%@ include file="/common/taglibs.jsp"%>
    <table  border="0" align="center" cellpadding="4" cellspacing="1"  class="title" width="100%">
      <tr>
        <td class="title" colspan="2">
          <input type="hidden" name="referKind">
          <input type="hidden" name="prpLcheckRiskCode"  value="${prpLcheck.riskCode}">
          <input type="hidden" name="riskCode"  value="${prpLcheck.riskCode}">
          <input type="hidden" name="prpLcheckFlag"  value="${prpLcheck.flag}">
          <input type="hidden" name="prpLcheckReferSerialNo" value="${prpLcheck.id.referSerialNo}">
          <input type="hidden" name="prpLcheckInsureCarFlag" value="">
          <input type="hidden" name="swfLogFlowID" value="${param.swfLogFlowID}">
          <input type="hidden" name="swfLogLogNo" value="${param.swfLogLogNo}">
          <input type="hidden" name="swfLogActorId" value="<c:out value='${param.actorId}'/>">
          <input type="hidden" name="swfLogProcessId" value="<c:out value='${param.processId}'/>">
          <input type="hidden" name="policyno" value="${prpLcheck.policyNo}">
          <input type="hidden" name="registno" value="${prpLcheck.id.registNo}">
          <input type="hidden" name="coreURL" value="${core_URL }">
          <input type=hidden name="prpLregistComCode" title="归属机构" class="ReadOnly" ReadOnly style="width:120px" value="${prpLregist.comCode}"/>
        </td>
        <td class="title"><s:text name="certainLoss.prpLacciCheck.riskCName"/>:</td><%--险种名称 --%>
        <td class="title">${riskCName}</td>
      </tr>
      
      <tr>
        <td class="title"  style="valign:bottom" ><s:text name="certainLoss.prpLacciCheck.prpLacciCheckRegistNo"/></td><%--报案号 --%>
        <td class="input"  style="valign:middle">
          <input type=text name="prpLcheckRegistNo" class="readonly" readonly="true" style="width:200px" value="${prpLcheck.id.registNo}">
        </td> 
        <td class="title" ><s:text name="certainLoss.prpLacciCheck.prpLcheckClaimNo"/></td><%--赔案号--%>
        <td class="input"  >
          <input type="text" name="prpLcheckClaimNo" class="readonly" readonly="true" value="${prpLcheck.claimNo}">
        </td>
      </tr>
        
      <tr>
        <td class="title" ><s:text name="certainLoss.prpLacciCheck.prpLacciCheckPolicyNo"/></td><%--保单号--%>
        <td class="input" style="width:55%" colspan="3">
          <input type="text" name="prpLcheckPolicyNo" class="readonly" readonly="true" style="width:200px" value="${prpLcheck.policyNo}"><input type="image" name="btRelate" src="/claim/images/butRelate.gif" align="middle" width="54" height="17" border="0" onclick="relate(fm.prpLcheckPolicyNo.value);return false;">
          <img src="/claim/images/bgMarkMustInput.jpg">
          <input type="hidden" name="damageDate" value="${prpLcheck.damageStartDate}"/>
          <input type=button class="bigbutton"  name="policyBackWard" value="出险时保单信息" onclick="backWardPolicy(fm.coreURL.value,fm.prpLcheckPolicyNo.value,fm.prpLcheckRiskCode.value,fm.damageDate.value,fm.prpLregistComCode.value);">
        </td>
      </tr>
      <tr>
        <td class="title" ><s:text name="certainLoss.prpLcheck.checkType" /></td><%--查勘类型--%>
        <td class="input"  >
        	<s:select list="#request.shipCheckTypeList" name="checkType" listKey="key" listValue="value" value="#request.prpLcheck.checkType" />
        </td>
         <td class="title" ><s:text name="prpLcheck.checkDate" />:</td>   <%--查勘日期--%>
        <td class="input"  >
          <rc:rcDate name="prpLcheckCheckDate" style="width:140px" class="input" value="${prpLcheck.checkDate}" /><img src="/claim/images/bgMarkMustInput.jpg">
        </td>
      </tr>
     <tr>
        <td class="title" ><s:text name="certainLoss.prpLcheck.prpLcheckDamageStartDate" /></td><%--出险时间:--%>
        <td class="input" >
        <rc:rcDate name="prpLcheckDamageStartDate" style="width:100" value="${prpLcheck.damageStartDate}"/>${prpLcheck.damageStartHour} <s:text name ="regist.prpLregist.hour"/>${prpLcheck.damageStartMinute} <s:text name ="regist.prpLregist.minute"/>
        </td>
        <td class="left">共保狀態:</td>
		<td class="right">
			<input class="input" name="prpLcheckCoinsFlag" value="${prpLcheck.coinsFlag}">
		</td>
	</tr>
	<tr>
        <td class="title" ><s:text name="certainLoss.prpLcheck.prpLcheckDamageAddress" /></td><%--出险地点:--%>
        <td class="input"  colspan="3">
        <select name="countryFlag" style="width:100px" onchange="countryFlag_change(this.options[this.selectedIndex].value)">
        <option value="0"><s:text name="commonAcci.claim.domestic" /></option><%--国内--%>
        <option value="1"><s:text name="commonAcci.claim.abroad" /></option> <%--国外--%>
        </select>
        <input type=text class="codecode" name="foreignCountryCode" style="display:none"/>
        <input type=text class="codecode" name="foreignCountryName" style="display:none" title="选择国家名" style="width:120px"
            ondblclick = "code_CodeSelect(this, 'foreignCountryCode','-1,0','Y','N');clearPortCode();"
            onkeyup = "code_CodeSelect(this, 'foreignCountryCode','-1,0','Y','N');clearPortCode();"
            onchange = "code_CodeSelect(this, 'foreignCountryCode','-1,0','Y','N');clearPortCode();"/>
        <input type=text class="codecode" name="prpLcheckAddressCode" style="width: 40px;" title="<s:text name='regist.prpLregist.areaCode'/>" value="${prpLcheck.addressCode}"
			ondblclick="code_CodeSelect(this, 'PostCode','0,1','Y','Y');" onkeyup="code_CodeSelect(this, 'PostCode','0,1','Y','Y');"
			onchange="code_CodeSelect(this, 'PostCode','0,1','Y','Y');" > 
		<input type=text class="codecode" name="prpLcheckAddressName" title="<s:text name='db.prpLclaim.damageAreaName'/>" style="width: 110px" value="${prpLcheck.addressName}"
			ondblclick="code_CodeSelect(this, 'PostCode','-1,0','Y','N');" onkeyup="code_CodeSelect(this, 'PostCode','-1,0','Y','N');"
			onchange="code_CodeSelect(this, 'PostCode','-1,0','Y','N');">
        <input type="text" name="prpLcheckDamageAddress" class="input"  style="width:240px" value="${prpLcheck.damageAddress}"
            onclick = "showProvinceCity(this,'foreignCountryName','prpLcheckAddressName');" selectValue="${prpLcheck.addressName}">
        <img src="/claim/images/bgMarkMustInput.jpg">
        </td>
      </tr>
      
      <tr>
        <td class="title" ><s:text name="prpLcheck.checkArea" />:</td><%--查勘地点--%>
        <td class="input">
          <input type="text" name="prpLcheckCheckSite" class="input" style="width:350px" value="${prpLcheck.checkSite}">
        </td>
        <td class="title" ><s:text name='check.PoliceUnit'/>:</td><%--宪警单位--%>
        <td class="input">
          <input name='prpLcheckPoliceUnit' class='input' maxlength=20 style="width:140px" description="宪警单位" value="${prpLcheck.policeUnit}">
        </td>
      </tr>
      <tr>
        <td class="title" ><s:text name="certainLoss.prpLcheck.prpLcheckChecker1" /></td> <%--查勘人 1--%>
        <td class="input"  >
          <input name='prpLcheckChecker1' class='input' maxlength=20 style="width:140px" description="查勘人1" value="${prpLcheck.checker1}">
          <img src="/claim/images/bgMarkMustInput.jpg">
        </select>
        </td>
        <td class="title"  style="valign:bottom" ><s:text name="certainLoss.prpLcheck.prpLcheckChecker2" /></td><%--查勘人 2--%>
        <td class="input"  style="valign:middle">
          <input name='prpLcheckChecker2' class='input' maxlength=20 style="width:140px" description="查勘人2" value="${prpLcheck.checker2}">
        </td>
      </tr>
      <tr>
        <td class="left">
      		<s:text name="prpLcheck.damageCode" />:<%--出险原因--%>
      	</td>
      	<td class="right">
      		<select name= "theMain">
               <option value="9000"><s:text name="check.all" /></option>   <%--所有--%>
               <option value="9500"><s:text name="check.naturalCategory" /></option> <%--自然灾害类--%>
               <option value="9600"><s:text name="check.accidenTypes" /></option> <%--意外事故类--%>
               <option value="9700"><s:text name="check.otherClasses" /></option>    <%--其它类--%>
           </select>
      	</td>
      <td class="right">
		<input name="prpLcheckDamageCode" class="codecode" style="width:34%"
			maxlength=3 description="出险原因"
			value="${prpLcheck.damageCode}"
			  ondblclick="code_CodeSelect(this, 'DamageCode','0,1','Y','Y',fm.prpLcheckRiskCode.value);"
        onkeyup= "code_CodeSelect(this, 'DamageCode','0,1','Y','Y',fm.prpLcheckRiskCode.value);"
        onchange = "code_CodeSelect(this, 'DamageCode','0,1','Y','Y',fm.prpLcheckRiskCode.value);">
		<input name="prpLcheckDamageName" class="codename" style="width:46%"
			maxlength=20 description="出险原因"
			value="${prpLcheck.damageName}"
			 ondblclick="code_CodeSelect(this, 'DamageCode','-1,0','Y','N',fm.prpLcheckRiskCode.value);"
             onkeyup= "code_CodeSelect(this, 'DamageCode','-1,0','Y','N',fm.prpLcheckRiskCode.value);"
             onchange = "code_CodeSelect(this, 'DamageCode','-1,0','Y','N',fm.prpLcheckRiskCode.value);">
		<img src="/claim/images/bgMarkMustInput.jpg">
		</td>
      </tr>
      <tr>
      <td class='title' id='tdTitleCheckUnitName'><s:text name="certainLoss.prpLcheck.prpLcheckCheckCom" /></td><%--查勘处理单位--%>
      <td class='input' colspan='3'>
        <input name='prpLcheckCheckUnitName' class='input' style="width:93%" maxlength=30 description="查勘处理单位" value="${prpLcheck.checkUnitName}">
      </td>
      </tr>