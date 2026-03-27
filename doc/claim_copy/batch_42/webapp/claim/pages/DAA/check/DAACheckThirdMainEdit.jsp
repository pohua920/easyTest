<%--
****************************************************************************
* DESC       ：添加如果是三者的主信息子块界面页面
* AUTHOR     ： lixiang
* CREATEDATE ： 2004-12-23 
* MODIFYLIST ：   Name       Date            Reason/Contents
*               wuxiaodong  20050907       增加代码选择的onchange事件，同时支持名称与代码的相互选择
*          ------------------------------------------------------
****************************************************************************
--%>
<table border="0" align="center" cellpadding="0" cellspacing="0" class="common">
	<tr>
		<td width="30%">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="12">
						<img src="/claim/images/bgBarLeft.gif" width="12" height="19">
					</td>
					<td class="formtitle">
						<s:text name="checkPage.edit" />
					</td>
					<%-- 查勘登记 --%>
					<td width="11">
						<img src="/claim/images/bgBarRight.gif" width="11" height="19">
					</td>
				</tr>
			</table>
		</td>
		<td>
			<input type="button" name="prpLmessageSave" class="bigbutton" value="<s:text name='button.disMessage.value' />"
				onclick="openWinSave(fm.prpLcheckRegistNo.value,fm.prpLcheckPolicyNo.value,fm.prpLcheckRiskCode.value,'check','');">
		</td>
		<%-- 讨论留言 --%>
		<td>
			<input type="button" name="prpLmessageView" class="bigbutton" value="<s:text name='button.viewMessage.value' />" onClick="openWinQuery('registNo',fm.prpLcheckRegistNo.value);">
		</td>
		<%-- 查看留言 --%>
		<td>
			<input type="button" name="eCertify" class="bigbutton" value="<s:text name='button.electronicDocuments.value' />"
				onClick="openCertify('certifyFinishQueryList','prpLcertifyCertifyNo','<bean:write name='prpLcheckDto' property='registNo'/>');">
		</td>
		<%-- 电子单证 --%>
		<%--
       <td><input type="button" name="prpLmessageSave" class="bigbutton" value="讨论留言" onclick="openWinSave()"></td>
       <td><input type="button" name="prpLmessageView" value="查看留言" onClick="openWinQuery()"></td>
       <td><input type="button" name="eCertify" value="电子单证" onClick="openCertify()"></td> 
       --%>
		<td width="70%" align="right">
			<font color="#666666"> <s:text name="prompt.check.note" /> <%-- 注 --%>：“<font color="#FF0000">*</font>” <s:text name="prompt.check.fieldWill" /> <%-- 为必选项 --%>，“<img
				src="/claim/images/bgDoubleClick2.gif" width="13" height="13" align="absbottom">” <s:text name="prompt.check.doubleClick" /> <%-- 为双击选择项 --%>。
			</font>
		</td>
	</tr>
</table>
<table border="0" align="center" cellpadding="4" cellspacing="1" bgcolor="#2D8EE1" class="title">
	<tr>
		<td class="title" colspan="4">
			<s:text name="checkPage.edit" />
			<%-- 查勘登记 --%>
			<input type="hidden" name="referKind">
			<input type="hidden" name="prpLcheckRiskCode" value="<bean:write name='prpLcheckDto' property='riskCode'/>">
			<input type="hidden" name="prpLcheckCheckUnitName" value="<bean:write name='prpLcheckDto' property='checkUnitName'/>">
			<input type="hidden" name="prpLcheckFlag" value="<bean:write name='prpLcheckDto' property='flag'/>">
			<input type="hidden" name="riskcode" value="<bean:write name='prpLcheckDto' property='riskCode' filter='true' />">
			<input type="hidden" name="policyno" value="<bean:write name='prpLcheckDto' property='policyNo' filter='true' />">
		</td>
	</tr>
	<tr>
		<td class="title" style="width: 15%">
			<s:text name="check.claimNum" />
			:
		</td>
		<%-- 赔案号 --%>
		<td class="input" style="width: 35%">
			<input type="text" name="prpLcheckClaimNo" class="readonly" readonly="true" value="<bean:write name='prpLcheckDto' property='claimNo'/>">
		</td>
		<td class="title" style="width: 15%" style="valign:bottom">
			<s:text name="prpLregist.registNo" />
			:
		</td>
		<%-- 报案号 --%>
		<td class="input" style="width: 35%" style="valign:middle">
			<input type=text name="prpLcheckRegistNo" class="readonly" readonly="true" style="width: 140px" value="<bean:write name='prpLcheckDto' property='registNo'/>">
		</td>
	</tr>
	<tr>
		<td class="title" style="width: 15%">
			<s:text name="db.view_larrearage.policyNo" />
			：
		</td>
		<%-- 保单号 --%>
		<td class="input" style="width: 55%" colspan="3">
			<input type="text" name="prpLcheckPolicyNo" class="readonly" readonly="true" style="width: 140px" value="<bean:write name='prpLcheckDto' property='policyNo'/>">
			<input type="image" name="btRelate" src="/claim/images/butRelate.gif" align="middle" width="54" height="17" border="0" onclick="relate(fm.prpLcheckPolicyNo.value);return false;">
			<img src="/claim/images/bgMarkMustInput.jpg">
		</td>
	</tr>
	<tr>
		<td class="title" style="width: 15%">
			<s:text name="db.prpLCItemCar.clauseType" />
			：
		</td>
		<%-- 条款类别 --%>
		<td class="input" style="width: 85%" colspan="3">
			<input type="text" name="prpLcheckClauseName" class="readonly" readonly="true" style="width: 440px" value="<bean:write name='prpLcheckDto' property='clauseName'/>">
		</td>
	</tr>
	<tr>
		<td class="title" style="width: 15%">
			<s:text name="certainLoss.prpLcheck.prpLcheckCheckType" />
			：
		</td>
		<%-- 查勘类型 --%>
		<td class="input" style="width: 35%">
			<html:select name="prpLcheckDto" property="checkType">
				<html:option value="L">
					<s:text name="check.mentHereunde" />
				</html:option>
				<%-- 查勘 --%>
				<html:option value="D">
					<s:text name="check.generSurvey" />
				</html:option>
				<%-- 代查勘 --%>
				<html:option value="B">
					<s:text name="check.bySurvey" />
				</html:option>
				<%-- 被查勘 --%>
			</html:select>
		</td>
		<td class="title" style="width: 15%" style="valign:bottom">
			<s:text name="certainLoss.prpLcheck.prpLcheckCheckKind" />
			：
		</td>
		<%-- 查勘性质 --%>
		<td class="input" style="width: 35%" style="valign:middle">
			<html:select name="prpLcheckDto" property="checkNature" styleClass="three">
				<html:options collection="checkNatures" property="codeCode" labelProperty="codeCName" />
			</html:select>
		</td>
	</tr>
	<tr>
		<td class="title" style="width: 15%" style="valign:bottom">
			<s:text name="prpLcheck.claimType" />
			：
		</td>
		<%-- 赔案类别 --%>
		<td class="input" style="width: 35%" style="valign:middle">
			<html:select name="prpLcheckDto" property="claimType" styleClass="three">
				<html:options collection="caseCodes" property="codeCode" labelProperty="codeCName" />
			</html:select>
		</td>
		<td class="title" style="width: 15%" style="valign:bottom">
			<s:text name="regist.prpLregist.damageAddressType" />
			：
		</td>
		<%-- 出险地点分类 --%>
		<td class="input" style="width: 35%" style="valign:middle">
			<html:select name="prpLcheckDto" property="damageAddressType" styleClass="three">
				<html:options collection="damageAddresss" property="codeCode" labelProperty="codeCName" />
			</html:select>
		</td>
	</tr>
	<tr>
		<td class="title" style="width: 15%">
			<s:text name="certainLoss.prpLcheck.prpLcheckCheckDate" />
			：
		</td>
		<%-- 查勘日期 --%>
		<td class="input" style="width: 35%">
			<input type="text" name="prpLcheckCheckDate" class="input" style="width: 140px" value="<bean:write name='prpLcheckDto' property='checkDate'/>">
			<img src="/claim/images/bgMarkMustInput.jpg">
		</td>
		<td class="title" style="width: 15%">
			<s:text name="certainLoss.prpLcheck.prpLcheckDamageTimes" />
			:
		</td>
		<%-- 已出险次数 --%>
		<td class="input" style="width: 35%">
			<%-- 出险信息画面 --%>
			<%@include file="/DAA/regist/DAAExistRegist.jsp"%>
		</td>
	</tr>
	<tr>
		<td class="title" style="width: 15%">
			<s:text name="prpLcheck.checkArea" />
			：
		</td>
		<%-- 查勘地点 --%>
		<td class="input" style="width: 35%" colspan="3">
			<input type="text" name="prpLcheckCheckSite" class="input" style="width: 440px" value="<bean:write name='prpLcheckDto' property='checkSite'/>">
			<img src="/claim/images/bgMarkMustInput.jpg">
		</td>
	</tr>
	<tr>
		<td class="title" style="width: 15%">
			<s:text name="certainLoss.prpLcheck.prpLcheckDamageStartDate" />
			：
		</td>
		<%-- 出险时间 --%>
		<td class="input" style="width: 35%" colspan="3">
			<input type="text" name="prpLcheckDamageStartDate" class="readonly" readonly="true" maxlength="10" style="width: 250px"
				value="<bean:write name='prpLcheckDto' property='damageStartDate' filter='true' /> 日 <bean:write name='prpLcheckDto' property='damageStartHour' filter='true' /> 時 <bean:write name='prpLcheckDto' property='damageStartMinute' filter='true' /> 分">
		</td>
	</tr>
	<tr>
		<td class="title" style="width: 15%">
			<s:text name="certainLoss.prpLcheck.prpLcheckDamageCase" />
			：
		</td>
		<%-- 出险原因 --%>
		<td class="input" style="width: 35%">
			<input name="prpLcheckDamageCode" class="codecode" style="width: 40px" maxlength=3 description="出险原因" value="<bean:write name='prpLcheckDto' property='damageCode'/>"
				ondblclick="code_CodeSelect(this, 'DamageCode');" onchange="code_CodeChange(this, 'DamageCode');" onkeyup="code_CodeSelect(this, 'DamageCode');">
			<input name="prpLcheckDamageName" class="codename" style="width: 110px" maxlength=20 description="出险原因" value="<bean:write name='prpLcheckDto' property='damageName'/>"
				ondblclick="code_CodeSelect(this, 'DamageCode','-1','name','none','post');" onchange="code_CodeChange(this, 'DamageCode','-1','name','none','post');"
				onkeyup="code_CodeSelect(this, 'DamageCode','-1','name','none','post');">
			<img src="/claim/images/bgMarkMustInput.jpg">
		</td>
		<td class="title" style="width: 15%" style="valign:bottom">
			<s:text name="certainLoss.prpLcheck.prpLcheckDamageTypeCase" />
			：
		</td>
		<%-- 事故原因 --%>
		<td class="input" style="width: 35%" style="valign:middle">
			<input name="prpLcheckDamageTypeCode" class="codecode" style="width: 40px" maxlength=3 description="事故原因" value="<bean:write name='prpLcheckDto' property='damageTypeCode'/>"
				ondblclick="code_CodeSelect(this, 'DamageTypeCode');" onchange="code_CodeChange(this, 'DamageTypeCode');" onkeyup="code_CodeSelect(this, 'DamageTypeCode');">
			<input name="prpLcheckDamageTypeName" class="codename" style="width: 110px" maxlength=20 description="事故原因" value="<bean:write name='prpLcheckDto' property='damageTypeName'/>"
				ondblclick="code_CodeSelect(this, 'DamageTypeCode','-1','name','none','post');" onchange="code_CodeChange(this, 'DamageTypeCode','-1','name','none','post');"
				onkeyup="code_CodeSelect(this, 'DamageTypeCode','-1','name','none','post');">
			<img src="/claim/images/bgMarkMustInput.jpg">
		</td>
	</tr>
	<tr>
		<td class="title" style="width: 15%">
			<s:text name="certainLoss.prpLcheck.prpLcheckDamageArea" />
			：
		</td>
		<%-- 出险区域 --%>
		<td class="input" style="width: 35%">
			<input name="prpLcheckDamageAreaCode" class="codecode" style="width: 40px" description="出险網域" value="<bean:write name='prpLcheckDto' property='damageAreaCode'/>"
				ondblclick="code_CodeSelect(this, 'DamageAreaCode');" onchange="code_CodeChange(this, 'DamageAreaCode');" onkeyup="code_CodeSelect(this, 'DamageAreaCode');">
			<input name="prpLcheckDamageAreaName" class="codename" style="width: 110px" description="出险網域" value="<bean:write name='prpLcheckDto' property='damageAreaName'/>"
				ondblclick="code_CodeSelect(this, 'DamageAreaCode','-1','name','none','post');" onchange="code_CodeChange(this, 'DamageAreaCode','-1','name','none','post');"
				onkeyup="code_CodeSelect(this, 'DamageAreaCode','-1','name','none','post');">
			<img src="/claim/images/bgMarkMustInput.jpg">
		</td>
		<td class="title" style="width: 15%">
			<s:text name="certainLoss.prpLcheck.firstSiteFlag" />
			：
		</td>
		<%-- 是否第一现场 --%>
		<td class="input" style="width: 35%">
			<html:radio name="prpLcheckDto" property="firstSiteFlag" value="0" />
			<s:text name="certainLoss.thirdCarLoss.no" />
			<%-- 否 --%>
			<html:radio name="prpLcheckDto" property="firstSiteFlag" value="1" />
			<s:text name="certainLoss.thirdCarLoss.yes" />
			<%-- 是 --%>
		</td>
	</tr>
	<tr>
		<td class="title" style="width: 15%" rowspan="6">
			<s:text name="db.prpLcheck.referKind" />
			：
		</td>
		<%-- 事故所涉及险种 --%>
		<td class="input" style="width: 35%">
			<input type="checkbox" name="prpLcheckReferKind" value="A">
			<s:text name="check.carLossDanger" />
		</td>
		<%-- 车辆损失险 --%>
		<td class="input" style="width: 15%" style="valign:bottom">
			<input type="checkbox" name="prpLcheckReferKind" value="B">
			<s:text name="check.thirdPartyIns" />
		</td>
		<%-- 第三者责任险 --%>
		<td class="input" style="width: 35%" style="valign:middle">
			<input type="checkbox" name="prpLcheckReferKind" value="D1">
			<s:text name="check.liabilityPers" />
		</td>
		<%-- 车上人员责任险 --%>
	</tr>
	<tr>
		<td class="input" style="width: 35%">
			<input type="checkbox" name="prpLcheckReferKind" value="D2">
			<s:text name="check.vehiLiability" />
			<%--车上货物责任险--%>
		</td>
		<td class="input" style="width: 15%" style="valign:bottom">
			<input type="checkbox" name="prpLcheckReferKind" value="E">
			<s:text name="check.fireExplosDanger" />
			<%--火灾、爆炸、自燃损失险--%>
		</td>
		<td class="input" style="width: 35%" style="valign:middle">
			<input type="checkbox" name="prpLcheckReferKind" value="F">
			<s:text name="check.glassAloneDanger" />
			<%-- 玻璃单独破碎险 --%>
		</td>
	</tr>
	<tr>
		<td class="input" style="width: 35%">
			<input type="checkbox" name="prpLcheckReferKind" value="G">
			<s:text name="check.pilfer" />
			<%-- 盗抢险 --%>
		</td>
		<td class="input" style="width: 15%" style="valign:bottom">
			<input type="checkbox" name="prpLcheckReferKind" value="J">
			<s:text name="check.bailoutTerms" />
			<%-- 救助特约条款 --%>
		</td>
		<td class="input" style="width: 35%" style="valign:middle">&nbsp</td>
	</tr>
	<tr>
		<td class="input" style="width: 35%">
			<input type="checkbox" name="prpLcheckReferKind" value="K1">
			<s:text name="check.lifUnloadDanger" />
			<%-- 起重、装卸、挖掘车辆损失险 --%>
		</td>
		<td class="input" style="width: 15%" style="valign:bottom">
			<input type="checkbox" name="prpLcheckReferKind" value="K2">
			<s:text name="check.specialVehicleFix" />
			<%-- 特种车辆固定设备、仪器损坏险 --%>
		</td>
		<td class="input" style="width: 35%" style="valign:middle">
			<input type="checkbox" name="prpLcheckReferKind" value="L">
			<s:text name="check.bodyScratchesDanger" />
			<%-- 车身划痕损失险 --%>
		</td>
	</tr>
	<tr>
		<td class="input" style="width: 35%">
			<input type="checkbox" name="prpLcheckReferKind" value="M">
			<s:text name="check.bodyScratches" />
			<%-- 不计免赔特约条款 --%>
		</td>
		<td class="input" style="width: 15%" style="valign:bottom">
			<input type="checkbox" name="prpLcheckReferKind" value="T">
			<s:text name="check.trafficLossDanger" />
			<%-- 车辆停驶损失险 --%>
		</td>
		<td class="input" style="width: 35%" style="valign:middle">
			<input type="checkbox" name="prpLcheckReferKind" value="W">
			<s:text name="check.withoutFaultIns" />
			<%-- 无过失责任险 --%>
		</td>
	</tr>
	<tr>
		<td class="input" style="width: 35%">
			<input type="checkbox" name="prpLcheckReferKind" value="Z">
			<s:text name="check.spontCombLoss" />
		</td>
		<%-- 自燃损失险 --%>
		<td class="input" style="width: 15%" style="valign:bottom">&nbsp;</td>
		<td class="input" style="width: 35%" style="valign:middle">
			<img src="/claim/images/bgMarkMustInput.jpg">
		</td>
	</tr>
	<tr>
		<td class="title" style="width: 15%">
			<s:text name="certainLoss.prpLcheck.indemnityDuty" />
			：
		</td>
		<%-- 事故责任 --%>
		<td class="input" style="width: 35%">
			<html:select name="prpLcheckDto" property="indemnityDuty" styleClass="three">
				<html:options collection="indemnityDutys" property="codeCode" labelProperty="codeCName" />
			</html:select>
		</td>
		<td class="title" style="width: 15%" style="valign:bottom">
			<s:text name="db.prpLcheck.claimFlag" />
			：
		</td>
		<%-- 是否属於保险责任 --%>
		<td class="input" style="width: 35%" style="valign:middle">
			<html:radio name="prpLcheckDto" property="claimFlag" value="1" />
			<s:text name="certainLoss.thirdCarLoss.yes" />
			<%-- 是 --%>
			<html:radio name="prpLcheckDto" property="claimFlag" value="2" />
			<s:text name="check.no" />
			<%-- 不是 --%>
			<html:radio name="prpLcheckDto" property="claimFlag" value="3" />
			<s:text name="check.unableDet" />
			<%-- 无法确定 --%>
		</td>
	</tr>
	<tr>
		<td class="title" style="width: 15%">
			<s:text name="certainLoss.prpLcheck.prpLcheckChecker1" />
			：
		</td>
		<%-- 查勘人 1 --%>
		<td class="input" style="width: 35%">
			<input name='prpLcheckChecker1' class='input' maxlength=20 style="width: 140px" description="查勘人1" value="<bean:write name='prpLcheckDto' property='checker1'/>">
			<img src="/claim/images/bgMarkMustInput.jpg"> </select>
		</td>
		<td class="title" style="width: 15%" style="valign:bottom">
			<s:text name="certainLoss.prpLcheck.prpLcheckChecker2" />
			：
		</td>
		<%-- 查勘人 2 --%>
		<td class="input" style="width: 35%" style="valign:middle">
			<input name='prpLcheckChecker2' class='input' maxlength=20 style="width: 140px" description="查勘人2" value="<bean:write name='prpLcheckDto' property='checker2'/>">
		</td>
	</tr>
	<tr>
		<td class="title" style="width: 15%">
			<s:text name="check.proDepartment" />
			：
		</td>
		<%-- 处理部门 --%>
		<td class="input" style="width: 85%" colspan="3">
			<input type="input" name="prpLcheckHandleUnit" class="codecode" style="width: 40%" description="处理部门代码" value="<bean:write name='prpLcheckDto' property='handleUnit'/>"
				ondblclick="code_CodeSelect(this, 'ComCode');" onchange="code_CodeChange(this, 'ComCode');" onkeyup="code_CodeSelect(this, 'ComCode');">
			<input name='prpLcheckHandleUnitName' class='codename' maxlength=60 style="width: 45%" description="处理部门" value="<bean:write name='prpLcheckDto' property='handleUnitName'/>"
				ondblclick="code_CodeSelect(this, 'ComCode','-1','name','none','post');" onchange="code_CodeChange(this, 'ComCode','-1','name','none','post');"
				onkeyup="code_CodeSelect(this, 'ComCode','-1','name','none','post');">
			<img src="/claim/images/bgDoubleClick1.gif" width="13" height="13" align="absmiddle">
		</td>
	</tr>
	<tr>
		<td class="title" style="width: 15%">
			<s:text name="db.prpLregist.remark " />
			：
		</td>
		<%-- 备注 --%>
		<td class="input" style="width: 85%" colspan="3">
			<input name='prpLcheckRemark' class='input' maxlength=80 style="width: 440px" value="<bean:write name='prpLcheckDto' property='remark'/>">
		</td>
	</tr>
</table>