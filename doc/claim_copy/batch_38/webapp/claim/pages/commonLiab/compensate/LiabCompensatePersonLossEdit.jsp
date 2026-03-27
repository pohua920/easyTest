<%--
****************************************************************************
* DESC       ：添加人员赔款费用信息页面
* AUTHOR     ：中科软
* MODIFYLIST ：Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@include file="/common/taglibs.jsp"%>
<script type="text/javascript">
/***
 * 根据出生日期计算年龄
 */
function updatePersonLossAge(field){
    var age = 0;
    if(field.realValue != "" && field.realValue != null){
        var birthday = new Date(field.realValue.replace("-","/"));
        var now = new Date();//获得系统当前时间
        age = now.getFullYear()-birthday.getFullYear();
        var nextDate = getNextYearFullDate(field.realValue,age);
        var temp = compareFullDate(nextDate,convertFullDateToString(now));
        if(temp > 0 ){
            age -= 1;
        }
        if(age < 0 ){
            age = 0;
        }
    }else{
        age = "";
    }
    var index = $("input[name='"+field.name+"']").index(field);
    $("input[name='prpLpersonLossAge']").get(index).value = age;
}
/** 设置险别的保险金额  */
function setPersonLossAmount(field){
	var $personObject = $(field).parents("tr[name='prpLpersonLossObject']");
	var kindCode = $personObject.find(":input[name='prpLpersonLossKindCode']").val();
	if(kindCode == ""){
		return;
	}
	var $amount = $personObject.find(":input[name='prpLpersonLossAmount']");
	for(var i=0;i<damageKind.length;i++){
		if(damageKind[i]==kindCode){
			$amount.val(damageItemAmount[i]);
			break;
		}
	}
	
}
</script>
<script type="text/javascript">
	$(function(){
		$(":input[name='prpLpersonLossKindName'] , :input[name='prpLpersonLossLiabDetailName'] , :input[name='prpLpersonLossInjuryName'] , :input[name='prpLpersonLossInjuryItemName'] , :input[name='prpLpersonLossPayObjectSerialNo']").bind("mouseover",function(){
			$(this).prop("title",$(this).val());
		});
	})
</script>
<!--建立显示的录入条，可以收缩显示的-->
<span style="display: none">
    <table class="common" style="display: none" id="PersonFeeLoss_Data" cellspacing="1" cellpadding="0">
        <tbody>
            <tr name="prpLpersonLossObject">
                <td class="input" style="width: 12%">
                    <input type="hidden" name="serialNo" >
                    <input type="hidden" name="personLossSerialNo" >
                    <input type="text" name="prpLpersonLossKindCode" class="codecode" style="width: 40px" onblur="clearPrpLpersonLoss(this);" 
                        ondblclick="code_CodeSelect(this, 'PolicyKindCode','0,1,2','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);setPersonLossAmount(this);" 
                        onchange="code_CodeSelect(this, 'PolicyKindCode','0,1,2','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);setPersonLossAmount(this);" 
                        onkeyup="code_CodeSelect(this, 'PolicyKindCode','0,1,2','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);setPersonLossAmount(this);"/>
                    <input type="text" name=prpLpersonLossKindName class="codename" style="width: 80px" onblur="clearPrpLpersonLoss(this);" 
                        ondblclick="code_CodeSelect(this, 'PolicyKindCode','-1,0,1','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);setPersonLossAmount(this);" 
                        onkeyup="code_CodeSelect(this, 'PolicyKindCode','-1,0,1','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);setPersonLossAmount(this);" 
                        onchange="code_CodeSelect(this, 'PolicyKindCode','-1,0,1','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);setPersonLossAmount(this);">
                    <input type="hidden" name="prpLpersonLossItemKindNo">
                </td>
                <td class="input" style="width: 12%">
                    <input name="prpLpersonLossLiabDetailCode" class="codecode" style="width: 30px" 
                        ondblclick="code_CodeSelect(this,'PersonFeeTypeFlag','0,1,2','Y','Y',fm.prpLcompensateRiskCode.value);"
                        onchange="code_CodeChange(this, 'PersonFeeTypeFlag','0,1,2','Y','Y',fm.prpLcompensateRiskCode.value);"
                        onkeyup="code_CodeSelect(this,'PersonFeeTypeFlag','0,1,2','Y','Y',fm.prpLcompensateRiskCode.value);" readonly="readonly" >
                    <input name="prpLpersonLossLiabDetailName" class="codename" style="width: 65px"
                        ondblclick="code_CodeSelect(this,'PersonFeeTypeFlag','-1,0,1','Y','N',fm.prpLcompensateRiskCode.value);"
                        onchange="code_CodeChange(this, 'PersonFeeTypeFlag','-1,0,1','Y','N',fm.prpLcompensateRiskCode.value);"
                        onkeyup="code_CodeSelect(this,'PersonFeeTypeFlag','-1,0,1','Y','N',fm.prpLcompensateRiskCode.value);" readonly="readonly" >
                    <input name="medicDeathFlag" type="hidden" title="人傷費用類別類型">
                </td>
                <td class="input" style="width: 10%">
                    <input type="text" name="prpLpersonLossInjuryCode" ondblclick="code_CodeSelect(this,'InjuryCode','0,1','Y','Y',fm.prpLcompensateRiskCode.value);clearInjuryItemCode(this);" class="codecode" style="width: 30px" readonly="readonly">
                    <input type="text" name="prpLpersonLossInjuryName" ondblclick="code_CodeSelect(this,'InjuryCode','-1,0','Y','N',fm.prpLcompensateRiskCode.value);clearInjuryItemCode(this);" class="codename" style="width: 50px" readonly="readonly">
                </td>
                <td class="input" style="width: 10%">
                    <input type="text" name="prpLpersonLossInjuryItemCode" ondblclick="code_CodeSelect(this,'InjuryItemCode','0,1','Y','Y',fm.prpLcompensateRiskCode.value);" class="codecode" style="width: 30px" readonly="readonly">
                    <input type="text" name="prpLpersonLossInjuryItemName" ondblclick="code_CodeSelect(this,'InjuryItemCode','-1,0','Y','N',fm.prpLcompensateRiskCode.value);" class="codename" style="width: 50px" readonly="readonly">
                    <input type="hidden" name="prpLpersonLossClaimRate" value="100">
                </td>
                <td class="input" style="width: 8%">
                    <input name="prpLpersonLossSumDefPay" class="input" style="width: 75px" value="0" title="核定賠償" onchange="calRealpayForPerson(this);">
                </td>
                <td class="input" style="width: 8%">
                    <input name="prpLpersonLossDeductible" class="input" style="width: 70px" value="0" title="自負額" onchange="calRealpayForPerson(this);">
                </td>
                <td class="input" style="width: 8%">
                    <input name="prpLpersonLossSumRealPay" class="common" style="width: 75px" readonly="readonly" value="0" >
                </td>
                <td class="input" style="width: 5%">
                    <select name="prpLpersonLossCurrency" class="input" style="width: 50px" onchange="getPrpLpersonLossExchRate(this);">
                        <c:forEach items="${requestScope.prpLpayObjectInfoCurrencyList}" var="tempMap">
                            <option value="${tempMap.key}" <c:if test="${tempMap.key==requestScope.LOCAL_CURRENCY}">selected="selected"</c:if>><c:out value="${tempMap.key}"/></option>
                        </c:forEach>
                    </select>
                </td>
                <td class="input" style="width: 5%">
                    <input name="prpLpersonLossExchRate" value="1" onchange="calRealpayForPerson(this);"  class="input" style="width: 70px" readonly="readonly">
                </td>
                <td class="input" style="width: 8%">
                    <input name="prpLpersonLossSumRealPayNTD" class="common" readonly="readonly" style="width: 75px" value="0">
                </td>
                <td class="input" style="width: 7%">
                    <input name="prpLpersonLossPayObjectSerialNo" class="common" readonly="readonly" style="width: 60px" onclick="setPayObjectSerialNo(this);">
                    <input type="hidden" name="prpLpersonLossUnitAmount">
                    <input type="hidden" name="prpLpersonLossAmount" value="0">
                    <input type="hidden" name="prpLpersonLossLossQuantity">
                    <input type="hidden" name="prpLpersonLossFamilyName">
                    <input type="hidden" name="prpLpersonLossDeductibleRate" value="0">
                    <input type="hidden" name="prpLpersonLossFamilyNo">
                    <input type="hidden" name="prpLpersonLossLiabCode">
                    <input type="hidden" name="prpLpersonLossLiabName">
                    <input type="hidden" name="prpLpersonLossJobCode">
                    <input type="hidden" name="prpLpersonLossJobName">
                    <input type="hidden" name="prpLpersonLossItemAddress">
                    <input type="hidden" name="prpLpersonLossUnit">
                    <input type="hidden" name="prpLpersonLossCurrency2" value="<%=ConstantCodes.LOCAL_CURRENCY%>">
                    <input type="hidden" name="prpLpersonLossCurrency1" value="<%=ConstantCodes.LOCAL_CURRENCY%>">
                    <input type="hidden" name="prpLpersonLossItemValue">
                    <input type="hidden" name="prpLpersonLossSumRest" value="0">
                    <input type="hidden" name="prpLpersonLossCurrency4" value="<%=ConstantCodes.LOCAL_CURRENCY%>">
                    <input type="hidden" name="prpLpersonLossFlag">
                    <input type="hidden" name="prpLpersonLossCurrency3" value="<%=ConstantCodes.LOCAL_CURRENCY%>">
                </td>
                <td class="input" style="width: 4%" align="ceter">
                    <s:select name="prpLpersonLossReservedEstimate" list="#attr.reservedEstimateList" ></s:select>
                </td>
                <td class="input" style="width: 3%">
                    <div align="center">
                        <input type=button name="buttonPersonFeeLossDelete" class="smallbutton" onclick="deleteRow(this,'PersonFeeLoss','serialNo','personLossSerialNo')" value="-" readonly style="cursor: hand">
                    </div>
                </td>
            </tr>
        </tbody>
    </table>
    <table class="common" style="display: none" id="Person_Data" cellspacing="1" cellpadding="0">
        <tbody>
            <tr name="personObject">
                <td class="input" style="width: 4%">
                    <div align="center">
                        <input type="text" class="readonly" readonly name="prpLpersonLossSerialNo" style="width: 25px">
                        <input type="hidden" name="prpLpersonLossPersonNo">
                        <input type="hidden" name="prpLpersonLossDangerNo" value="1" onClick="viewDangerUnitPersonLoss(this);">
                    </div>
                </td>
                <td class="subformtitle" style="width: 92%">
                    <table cellpadding="0" cellspacing="1" class="common">
                        <tbody>
                            <tr>
                                <td class="title" style="width: 18%">姓名：</td>
                                <td class="input" style="width: 32%">
                                    <input class="input" style="width: 160px" name="prpLpersonLossPersonName" maxlength=20 description="<s:text name='db.prpLperson.personName' />">
                                    <img src="/claim/images/bgMarkMustInput.jpg">
                                </td>
                                <td class="title" style="width: 18%">
                                    <s:text name="db.prpCCarDriver.sex" />：<%-- 性别 --%>
                                </td>
                                <td class="input" style="width: 32%">
                                    <select name="prpLpersonLossSex" class="input" style="width: 50px">
                                        <option value="1"><s:text name="certainLoss.male" /><%--男 --%></option>
                                        <option value="2"><s:text name="certainLoss.female" /><%--女  --%></option>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td class="title" style="width: 18%" >出生日期：</td>
                                <td class="input" style="width: 32%" >
                                    <rc:rcDate class="input" style="width: 110px" name="prpLpersonLossBirthday" onchange="updatePersonLossAge(this);" title="出生年份" wdatePicker="true" />
                                    <img src="${ctx}/images/bgMarkMustInput.jpg">
                                </td>
                                <td class="title" style="width: 18%"><s:text name="db.prpLpersonloss.age" />：<%-- 年龄 --%></td>
                                <td class="input" style="width: 32%">
                                    <input type="text" class="input" name="prpLpersonLossAge" style="width: 50px" maxlength="3" description="<s:text name='db.prpLpersonloss.age'/>">
                                </td>
                            </tr>
                            <tr>
                                <td class="title" style="width: 18%" >證件類型：</td>
                                <td class="input" style="width: 32%" >
                                    <s:select name="prpLpersonLossCertificateCode" value="01" listKey="key" listValue="value" list="#request.prpdpaymentaccountCertificateTypeList" cssClass="input" onchange="resetSumRealPay(this);"/>
                                    <img src="${ctx}/images/bgMarkMustInput.jpg">
                                </td>
                                <td class="title" style="width: 18%" >是否以健保身份就診：</td>
                                <td class="input" style="width: 32%" >
                                    <select name="prpLpersonLossMedicalCode" class="input">
                                        <option value="Y" selected="selected">是</option>
                                        <option value="N">否</option>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td class="title" style="width: 18%" >證件號碼：</td>
                                <td class="input" style="width: 32%" >
                                    <input type="text" name="prpLpersonLossIdentifyNumber" class="input" style="width: 160px" onchange="resetSumRealPay(this);"/>
                                </td>
                                <td class="title" style="width: 18%" >受害人電話：</td>
                                <td class="input" style="width: 32%" >
                                    <input type="text" name="prpLpersonLossMobilePhone" class="input" style="width: 160px"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="title" style="width: 18%" >醫院名稱：</td>
                                <td class="input" style="width: 32%">
                                    <input class="input" name="prpLpersonLossHospitalCode" onkeyup="getHospital(this,'codeCode','0,1')" onblur="isHospital(this,'codeCode');" style="width: 100px" title="醫院代碼" >
                                    <input class="input" name="prpLpersonLossHospitalName" onkeyup="getHospital(this,'codeName','-1,0')" style="width: 180px" title="醫院名稱" >
                                </td>
                                <td class="title" style="width: 18%" >醫師姓名：</td>
                                <td class="input" style="width: 32%" >
                                    <input class="input" name="prpLpersonLossDoctor" style="width: 110px" title="醫師姓名">
                                </td>
                            </tr>
                            <tr>
                                <td class="title" style="width: 18%" >傷亡情形：</td>
                                <td class="input" style="width: 32%" >
                                    <s:select name="prpLpersonLossCasualties" listKey="key" listValue="value" list="#request.casualtiesList" cssClass="input" cssStyle="width: 110px" onchange="resetSumRealPay(this);"/>
                                </td>
                                <td class="title" style="width: 18%" >肇事責任比率：</td>
                                <td class="input" style="width: 32%" >
                                    <input type="text" class="input" name="prpLpersonLossIndemnityDutyRate" style="width: 110px" title="肇事責任比率" value="0" onfocus="cacheData(this);" onchange="validatePercent(this,0,100);">%
                                    <img src="${ctx}/images/bgMarkMustInput.jpg">
                                </td>
                            </tr>
                            <tr>
                                <td class="title" style="width: 18%" >憲警單位：</td>
                                <td class="input" style="width: 32%" >
                                    <input type="text" name="PoliceUnits" class="readonly" readonly="readonly"/>
                                </td>
                                <td class="title" style="width: 18%" ></td>
                                <td class="input" style="width: 32%" ></td>
                            </tr>
                            <tr>
                                <td class="title" style="width: 18%" >補充保費：</td>
                                <td class="input" style="width: 32%" >
                                    <input type="text" name="prpLpersonLossAddPremium" value="0" class="input" style="width: 120px">
                                </td>
                                <td class="title" style="width: 18%" >賠付金額合計：</td>
                                <td class="input" style="width: 32%" >
                                    <input type="text" name="prpLpersonLossSumRealPay1NTD" class="common" style="width: 120px" readonly="readonly" value="0"/>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="4">
                                    <span id="spanPersonFeeLoss">
                                        <%-- 多行输入展现域 --%>
                                        <table id="PersonFeeLoss" name="PersonFeeLoss" class="common" align="center" cellspacing="1" cellpadding="0">
                                            <thead>
                                                <tr>
                                                    <td class="subformtitle" colspan="13">賠款費用訊息</td>
                                                </tr>
                                                <tr>
                                                    <td class="centertitle" style="width: 12%">險別名稱</td>
                                                    <td class="centertitle" style="width: 12%">費用類型</td>
                                                    <td class="centertitle" style="width: 10%">殘廢項目</td>
                                                    <td class="centertitle" style="width: 10%">殘廢程度</td>
                                                    <td class="centertitle" style="width: 8%">核定賠償</td>
                                                    <td class="centertitle" style="width: 8%">自負額</td>
                                                    <td class="centertitle" style="width: 8%">賠償金額</td>
                                                    <td class="centertitle" style="width: 5%">幣別</td>
                                                    <td class="centertitle" style="width: 5%">匯率</td>
                                                    <td class="centertitle" style="width: 8%">賠償金額（NTD）</td>
                                                    <td class="centertitle" style="width: 7%">賠付對象訊息</td>
                                                    <td class="centertitle" style="width: 4%">保留預估</td>
                                                    <td class="centertitle" style="width: 3%">&nbsp;</td>
                                                </tr>
                                            </thead>
                                            <tfoot>
                                                <tr>
                                                    <td class="titlesubsub" colspan="12" style="width: 97%"></td>
                                                    <td class="title" align="right" style="width: 3%">
                                                        <div align="center">
                                                            <input type="button" value="+" class="smallbutton" onclick="insertRow('PersonFeeLoss',this,'serialNo','personLossSerialNo');" name="buttonPersonFeeLossInsert" readonly style="cursor: hand">
                                                        </div>
                                                    </td>
                                                </tr>
                                            </tfoot>
                                            <tbody>
                                            </tbody>
                                        </table>
                                    </span>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </td>
                <td class="input" style="width: 4%">
                    <div align="center">
                        <input type=button name="buttonPersonDelete" class="smallbutton" onclick="deleteRow(this,'Person','prpLpersonLossSerialNo')" value="-" style="cursor: hand">
                    </div>
                </td>
            </tr>
        </tbody>
    </table>
</span>
<table class="common" align="center">
    <!--表示显示多行的-->
    <tr>
        <td class="common" colspan="4">
            <img style="cursor: hand;" src="/claim/images/butCollapseBlue.gif" name="PersonImg" onclick="showPage(this,spanPerson)">
            <b><s:text name="人員傷亡賠付訊息"/></b><br>
            <span id="spanPerson" style="display: none"> <%-- 多行输入展现域 --%>
                <table id="Person" class="common" align="center" cellspacing="1" cellpadding="0">
                    <thead>
                        <tr>
                            <td class="centertitle" style="width: 4%">
                                <s:text name="db.prpLcheckExt.serialNo" /><%-- 序号 --%>
                            </td>
                            <td class="centertitle" style="width: 96%" colspan=2>
                                <s:text name="db.prpLregistText.context" /><%-- 内容 --%>
                            </td>
                        </tr>
                    </thead>
                    <tfoot>
                        <tr>
                            <td class="title" colspan=2 style="width: 96%">(按"+"號鍵增加人員傷亡賠付訊息，按"-"號鍵刪除訊息)</td>
                            <td class="title" align="right" style="width: 4%">
                                <div align="center">
                                    <input type="button" value="+" onclick="insertRow('Person',this,'prpLpersonLossSerialNo')" class="smallbutton" name="buttonPersonInsert" style="cursor: hand">
                                </div>
                            </td>
                        </tr>
                    </tfoot>
                    <tbody>
                        <c:set var="personNo" value="0" scope="page"/>
                        <c:set var="personSerialNo" value="1" scope="page"/>
                        <c:forEach var="tempPerson" items="${requestScope.prpLpersonLoss.prpLpersonLossList}">
                            <c:if test="${tempPerson.personNo - pageScope.personNo != 0}">
                                <tr name="personObject">
                                    <td class="input" style="width: 4%">
                                        <div align="center">
                                            <input type="text" class="readonly" readonly name="prpLpersonLossSerialNo" value="${pageScope.personSerialNo}" style="width: 25px">
                                            <input type="hidden" name="prpLpersonLossPersonNo" value="${tempPerson.personNo}">
                                            <input type="hidden" name="prpLpersonLossDangerNo" value="${tempPerson.dangerNo}" onClick="viewDangerUnitPersonLoss(this);">
                                        </div>
                                    </td>
                                    <td class="subformtitle" style="width: 92%">
                                        <table cellpadding="0" cellspacing="1" class="common">
                                            <tbody>
                                                <tr>
                                                    <td class="title" style="width: 18%">姓名：</td>
                                                    <td class="input" style="width: 32%">
                                                        <input class="input" style="width: 160px" name="prpLpersonLossPersonName"  maxlength=20 description="<s:text name='db.prpLperson.personName' />" value="${tempPerson.personName}">
                                                        <img src="/claim/images/bgMarkMustInput.jpg">
                                                    </td>
                                                    <td class="title" style="width: 18%">
                                                        <s:text name="db.prpCCarDriver.sex" />：<%-- 性别 --%>
                                                    </td>
                                                    <td class="input" style="width: 32%">
                                                        <select name="prpLpersonLossSex" class="input" style="width: 50px">
                                                            <option value="1" <c:if test="${fn:trim(tempPerson.sex)=='1'}"><c:out value="selected"/></c:if>><s:text name="certainLoss.male" /><%-- 男 --%></option>
                                                            <option value="2" <c:if test="${fn:trim(tempPerson.sex)=='2'}"><c:out value="selected"/></c:if>><s:text name="certainLoss.female" /></option>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td class="title" style="width: 18%" >出生日期：</td>
                                                    <td class="input" style="width: 32%" >
                                                        <rc:rcDate class="input" style="width: 110px" name="prpLpersonLossBirthday" onchange="updatePersonLossAge(this);" title="出生年份" wdatePicker="true" value="${tempPerson.birthday}"/>
                                                        <img src="${ctx}/images/bgMarkMustInput.jpg">
                                                    </td>
                                                    <td class="title" style="width: 18%"><s:text name="db.prpLpersonloss.age" />：<%-- 年龄 --%></td>
                                                    <td class="input" style="width: 32%">
                                                        <input type="text" class="input" name="prpLpersonLossAge" style="width: 50px" maxlength="3" description="<s:text name='db.prpLpersonloss.age'/>" value="${tempPerson.age}">
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td class="title" style="width: 18%" >證件類型：</td>
                                                    <td class="input" style="width: 32%" >
                                                        <c:set var="tempSelectedValue" value="${tempPerson.certificateCode}" />
                                                        <s:select name="prpLpersonLossCertificateCode" value="#attr.tempSelectedValue" listKey="key" listValue="value" list="#request.prpdpaymentaccountCertificateTypeList" cssClass="input" onchange="resetSumRealPay(this);"/>
                                                        <img src="${ctx}/images/bgMarkMustInput.jpg">
                                                    </td>
                                                    <td class="title" style="width: 18%" >是否以健保身份就診：</td>
                                                    <td class="input" style="width: 32%" >
                                                        <select name="prpLpersonLossMedicalCode" class="input">
                                                            <option value="Y" <c:if test="${tempPerson.medicalCode=='Y'}"><c:out value="selected"/></c:if>>是</option>
                                                            <option value="N" <c:if test="${tempPerson.medicalCode=='N'}"><c:out value="selected"/></c:if>>否</option>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td class="title" style="width: 18%" >證件號碼：</td>
                                                    <td class="input" style="width: 32%" >
                                                        <input type="text" name="prpLpersonLossIdentifyNumber" class="input" value="${tempPerson.identifyNumber}" style="width: 160px" onchange="resetSumRealPay(this);"/>
                                                        <img src="${ctx}/images/bgMarkMustInput.jpg">
                                                    </td>
                                                    <td class="title" style="width: 18%" >受害人電話：</td>
                                                    <td class="input" style="width: 32%" >
                                                        <input type="text" name="prpLpersonLossMobilePhone" class="input" value="${tempPerson.mobilePhone}" style="width: 160px"/>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td class="title" style="width: 18%" >醫院名稱：</td>
                                                    <td class="input" style="width: 32%">
                                                        <input class="input" name="prpLpersonHospitalHospitalCode" onkeyup="getHospital(this,'codeCode','0,1')" onblur="isHospital(this,'codeCode');" style="width: 100px" title="醫院代碼" value="${tempPerson.hospitalCode}">
                                                        <input class="input" name="prpLpersonHospitalHospitalName" onkeyup="getHospital(this,'codeName','-1,0')" style="width: 180px" title="醫院名稱" value="${tempPerson.hospitalName}">
                                                    </td>
                                                    <td class="title" style="width: 18%" >醫師姓名：</td>
                                                    <td class="input" style="width: 32%" >
                                                        <input class="input" name="prpLpersonLossDoctor" style="width: 110px" title="醫師姓名" value="${tempPerson.doctor}" >
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td class="title" style="width: 18%" >傷亡情形：</td>
                                                    <td class="input" style="width: 32%" >
                                                        <c:set var="tempSelectedValue" value="${tempPerson.casualties}" />
                                                        <s:select name="prpLpersonLossCasualties" listKey="key" listValue="value" list="#request.casualtiesList" cssClass="input" cssStyle="width: 110px" value="#attr.tempSelectedValue" onchange="resetSumRealPay(this);"/>
                                                    </td>
                                                    <td class="title" style="width: 18%" >肇事責任比率：</td>
                                                    <td class="input" style="width: 32%" >
                                                        <input type="text" class="input" name="prpLpersonLossIndemnityDutyRate" style="width: 110px" title="肇事責任比率" onfocus="cacheData(this);" onchange="validatePercent(this,0,100);" value="<fmt:formatNumber value='${tempPerson.indemnityDutyRate}'  maxFractionDigits='2'/>" >%
                                                        <img src="${ctx}/images/bgMarkMustInput.jpg">
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td class="title" style="width: 18%" >憲警單位：</td>
                                                    <td class="input" style="width: 32%" >
                                                        <input type="text" name="PoliceUnits" class="readonly" readonly="readonly"/>
                                                    </td>
                                                    <td class="title" style="width: 18%" ></td>
                                                    <td class="input" style="width: 32%" ></td>
                                                </tr>
                                                <tr>
                                                    <td class="title" style="width: 18%" >補充保費：</td>
                                                    <td class="input" style="width: 32%" >
                                                        <input type="text" name="prpLpersonLossAddPremium" value="<fmt:formatNumber value='${tempPerson.addPremium}' pattern='#'/>" class="input" style="width: 120px">
                                                    </td>
                                                    <td class="title" style="width: 18%" >賠付金額合計：</td>
                                                    <td class="input" style="width: 32%" >
                                                        <input type="text" name="prpLpersonLossSumRealPay1NTD" class="common" value="0" style="width: 120px" readonly="readonly"/>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td colspan="4">
                                                        <span id="spanPersonFeeLoss">
                                                            <%-- 多行输入展现域 --%>
                                                            <table id="PersonFeeLoss" name="PersonFeeLoss" class="common" align="center" cellspacing="1" cellpadding="0">
                                                                <thead>
                                                                    <tr>
                                                                        <td class="subformtitle" colspan="13">賠款費用訊息</td>
                                                                    </tr>
                                                                    <tr>
                                                                        <td class="centertitle" style="width: 12%">險別名稱</td>
                                                                        <td class="centertitle" style="width: 12%">費用類型</td>
                                                                        <td class="centertitle" style="width: 10%">殘廢項目</td>
                                                                        <td class="centertitle" style="width: 10%">殘廢程度</td>
                                                                        <td class="centertitle" style="width: 8%">核定賠償</td>
                                                                        <td class="centertitle" style="width: 8%">自負額</td>
                                                                        <td class="centertitle" style="width: 8%">賠償金額</td>
                                                                        <td class="centertitle" style="width: 5%">幣別</td>
                                                                        <td class="centertitle" style="width: 5%">匯率</td>
                                                                        <td class="centertitle" style="width: 8%">賠償金額（NTD）</td>
                                                                        <td class="centertitle" style="width: 7%">賠付對象訊息</td>
                                                                        <td class="centertitle" style="width: 4%">保留預估</td>
                                                                        <td class="centertitle" style="width: 3%">&nbsp;</td>
                                                                    </tr>
                                                                </thead>
                                                                <tfoot>
                                                                    <tr>
                                                                        <td class="titlesubsub" colspan="12" style="width: 97%"></td>
                                                                        <td class="title" align="right" style="width: 3%">
                                                                            <div align="center">
                                                                                <input type="button" value="+" class="smallbutton" onclick="insertRow('PersonFeeLoss',this,'serialNo','personLossSerialNo');" name="buttonPersonFeeLossInsert" readonly style="cursor: hand">
                                                                            </div>
                                                                        </td>
                                                                    </tr>
                                                                </tfoot>
                                                                <tbody>
                                                                    <c:set var="tempSerial" value="0" scope="page"/>
                                                                    <c:forEach items="${requestScope.prpLpersonLoss.prpLpersonLossList}" var="tempPrpLpersonLosss">
                                                                        <c:if test="${tempPrpLpersonLosss.personNo == tempPerson.personNo}">
                                                                            <c:set var="tempSerial" value="${tempSerial + 1}" />
                                                                            <tr name="prpLpersonLossObject">
                                                                                <td class="input" style="width: 12%">
                                                                                    <input type="hidden" name="serialNo" value="${tempSerial}" >
                                                                                    <input type="hidden" name="personLossSerialNo" value="${tempPerson.personNo}"><%-- 归属父类的序号 --%>
                                                                                    <input type="text" name="prpLpersonLossKindCode" class="codecode" value="${tempPrpLpersonLosss.kindCode}" style="width: 40px" onblur="clearPrpLpersonLoss(this);" 
                                                                                        ondblclick="code_CodeSelect(this, 'PolicyKindCode','0,1,2','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);setPersonLossAmount(this);" 
                                                                                        onchange="code_CodeSelect(this, 'PolicyKindCode','0,1,2','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);setPersonLossAmount(this);" 
                                                                                        onkeyup="code_CodeSelect(this, 'PolicyKindCode','0,1,2','Y','Y',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);setPersonLossAmount(this);"/>
                                                                                    <input type="text" name=prpLpersonLossKindName class="codename" style="width: 80px" value="${tempPrpLpersonLosss.kindName}" style="width: 100px" onblur="clearPrpLpersonLoss(this);" 
                                                                                        ondblclick="code_CodeSelect(this, 'PolicyKindCode','-1,0,1','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);setPersonLossAmount(this);" 
                                                                                        onkeyup="code_CodeSelect(this, 'PolicyKindCode','-1,0,1','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);setPersonLossAmount(this);" 
                                                                                        onchange="code_CodeSelect(this, 'PolicyKindCode','-1,0,1','Y','N',fm.policyno.value+'|'+fm.damageStartDate.value+'|'+fm.damageStartHour.value);setPersonLossAmount(this);" >
                                                                                    <input type="hidden" name="prpLpersonLossItemKindNo" value="${tempPrpLpersonLosss.itemKindNo}">
                                                                                </td>
                                                                                <td class="input" style="width: 12%">
                                                                                    <input name="prpLpersonLossLiabDetailCode" class="codecode" style="width: 30px" value="<c:out value='${tempPrpLpersonLosss.liabDetailCode}' />"
                                                                                        ondblclick="code_CodeSelect(this,'PersonFeeTypeFlag','0,1,2','Y','Y',fm.prpLcompensateRiskCode.value);"
                                                                                        onchange="code_CodeChange(this, 'PersonFeeTypeFlag','0,1,2','Y','Y',fm.prpLcompensateRiskCode.value);"
                                                                                        onkeyup="code_CodeSelect(this,'PersonFeeTypeFlag','0,1,2','Y','Y',fm.prpLcompensateRiskCode.value);" readonly="readonly">
                                                                                    <input name="prpLpersonLossLiabDetailName" class="codename" style="width: 65px" value="<c:out value='${tempPrpLpersonLosss.liabDetailName}' />"
                                                                                        ondblclick="code_CodeSelect(this,'PersonFeeTypeFlag','-1,0,1','Y','N',fm.prpLcompensateRiskCode.value);"
                                                                                        onchange="code_CodeChange(this, 'PersonFeeTypeFlag','-1,0,1','Y','N',fm.prpLcompensateRiskCode.value);"
                                                                                        onkeyup="code_CodeSelect(this,'PersonFeeTypeFlag','-1,0,1','Y','N',fm.prpLcompensateRiskCode.value);" readonly="readonly">
                                                                                    <input name="medicDeathFlag" type="hidden" title="人傷費用類別類型" value="<c:out value='${tempPrpLpersonLosss.feeCategory}' />">
                                                                                </td>
                                                                                <td class="input" style="width: 10%">
                                                                                    <input type="text" name="prpLpersonLossInjuryCode" ondblclick="code_CodeSelect(this,'InjuryCode','0,1','Y','Y',fm.prpLcompensateRiskCode.value);clearInjuryItemCode(this);" class="codecode" style="width: 30px" readonly="readonly" value="${tempPrpLpersonLosss.injuryCode}">
                                                                                    <input type="text" name="prpLpersonLossInjuryName" ondblclick="code_CodeSelect(this,'InjuryCode','-1,0','Y','N',fm.prpLcompensateRiskCode.value);clearInjuryItemCode(this);" class="codename" style="width: 50px" readonly="readonly" value="${tempPrpLpersonLosss.injuryName}">
                                                                                </td>
                                                                                <td class="input" style="width: 10%">
                                                                                    <input type="text" name="prpLpersonLossInjuryItemCode" ondblclick="code_CodeSelect(this,'InjuryItemCode','0,1','Y','Y',fm.prpLcompensateRiskCode.value);" class="codecode" style="width: 30px" readonly="readonly" value="${tempPrpLpersonLosss.injuryItemCode}">
                                                                                    <input type="text" name="prpLpersonLossInjuryItemName" ondblclick="code_CodeSelect(this,'InjuryItemCode','-1,0','Y','N',fm.prpLcompensateRiskCode.value);" class="codename" style="width: 50px" readonly="readonly" value="${tempPrpLpersonLosss.injuryItemName}">
                                                                                    <input type="hidden" name="prpLpersonLossClaimRate" value="${tempPrpLpersonLosss.claimRate}">
                                                                                </td>
                                                                                <td class="input" style="width: 8%">
                                                                                    <input name="prpLpersonLossSumDefPay" class="input" style="width: 75px" title="核定賠償" value="<fmt:formatNumber value='${tempPrpLpersonLosss.sumDefPay}' pattern='#0.##'/>" onchange="calRealpayForPerson(this);">
                                                                                </td>
                                                                                <td class="input" style="width: 8%">
                                                                                    <input name="prpLpersonLossDeductible" class="input" style="width: 70px" title="自負額" value="<fmt:formatNumber value='${tempPrpLpersonLosss.deductible}' pattern='#0.##'/>" onchange="calRealpayForPerson(this);">
                                                                                </td>
                                                                                <td class="input" style="width: 8%">
                                                                                    <input name="prpLpersonLossSumRealPay" class="common" style="width: 75px" readonly="readonly" value="<fmt:formatNumber value='${tempPrpLpersonLosss.sumRealPay}' pattern='#0.##'/>">
                                                                                </td>
                                                                                <td class="input" style="width: 5%">
                                                                                    <select name="prpLpersonLossCurrency" class="input" style="width: 50px" onchange="getPrpLpersonLossExchRate(this);">
                                                                                        <c:forEach items="${requestScope.prpLpayObjectInfoCurrencyList}" var="tempMap">
                                                                                            <option value="${tempMap.key}" <c:if test="${tempMap.key==tempPrpLpersonLosss.currency}">selected="selected"</c:if>><c:out value="${tempMap.key}"/></option>
                                                                                        </c:forEach>
                                                                                    </select>
                                                                                </td>
                                                                                <td class="input" style="width: 5%">
                                                                                    <input name="prpLpersonLossExchRate" value="${tempPrpLpersonLosss.exchRate}" onchange="calRealpayForPerson(this);" class="input" style="width: 70px" readonly="readonly">
                                                                                </td>
                                                                                <td class="input" style="width: 8%">
                                                                                    <input name="prpLpersonLossSumRealPayNTD" class="common" readonly="readonly" style="width: 75px">
                                                                                </td>
                                                                                <td class="input" style="width: 7%">
                                                                                    <input name="prpLpersonLossPayObjectSerialNo" class="common" readonly="readonly" style="width: 60px" onclick="setPayObjectSerialNo(this);" value="${tempPrpLpersonLosss.payObjectSerialNo}">
                                                                                    <input type="hidden" name="prpLpersonLossUnitAmount" value="${tempPrpLpersonLosss.unitAmount}">
                                                                                    <input type="hidden" name="prpLpersonLossAmount" value="${tempPrpLpersonLosss.amount}">
                                                                                    <input type="hidden" name="prpLpersonLossLossQuantity" value="${tempPrpLpersonLosss.lossQuantity}">
                                                                                    <input type="hidden" name="prpLpersonLossFamilyName" value="${tempPrpLpersonLosss.familyName}">
                                                                                    <input type="hidden" name="prpLpersonLossDeductibleRate" value="${tempPrpLpersonLosss.deductiblerate}">
                                                                                    <input type="hidden" name="prpLpersonLossFamilyNo" value="${tempPrpLpersonLosss.familyNo}">
                                                                                    <input type="hidden" name="prpLpersonLossLiabCode" value="${tempPrpLpersonLosss.liabCode}">
                                                                                    <input type="hidden" name="prpLpersonLossLiabName" value="${tempPrpLpersonLosss.liabName}">
                                                                                    <input type="hidden" name="prpLpersonLossJobCode" value="${tempPrpLpersonLosss.jobCode}">
                                                                                    <input type="hidden" name="prpLpersonLossJobName" value="${tempPrpLpersonLosss.jobName}">
                                                                                    <input type="hidden" name="prpLpersonLossItemAddress" value="${tempPrpLpersonLosss.itemAddress}">
                                                                                    <input type="hidden" name="prpLpersonLossUnit" value="${tempPrpLpersonLosss.unit}">
                                                                                    <input type="hidden" name="prpLpersonLossCurrency2" value="${tempPrpLpersonLosss.currency2}">
                                                                                    <input type="hidden" name="prpLpersonLossCurrency1" value="${tempPrpLpersonLosss.currency1}">
                                                                                    <input type="hidden" name="prpLpersonLossItemValue" value="${tempPrpLpersonLosss.itemValue}">
                                                                                    <input type="hidden" name="prpLpersonLossSumRest" value="${tempPrpLpersonLosss.sumRest}">
                                                                                    <input type="hidden" name="prpLpersonLossCurrency4" value="${tempPrpLpersonLosss.currency4}">
                                                                                    <input type="hidden" name="prpLpersonLossFlag" value="${tempPrpLpersonLosss.flag}">
                                                                                    <input type="hidden" name="prpLpersonLossCurrency3" value="${tempPrpLpersonLosss.currency3}">
                                                                                </td>
                                                                                <td class="input" style="width: 4%" align="ceter">
                                                                                    <s:select name="prpLpersonLossReservedEstimate" list="#attr.reservedEstimateList" value="#attr.tempPrpLpersonLosss.reservedEstimate"></s:select>
                                                                                </td>
                                                                                <td class="input" style="width: 3%">
                                                                                    <div align="center">
                                                                                        <input type=button name="buttonPersonFeeLossDelete" class="smallbutton" onclick="deleteRow(this,'PersonFeeLoss','serialNo','personLossSerialNo')" value="-" readonly style="cursor: hand">
                                                                                    </div>
                                                                                </td>
                                                                            </tr>
                                                                        </c:if>
                                                                    </c:forEach>
                                                                </tbody>
                                                            </table>
                                                        </span>
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </td>
                                    <td class="input" style="width: 4%">
                                        <div align="center">
                                            <input type=button name="buttonPersonDelete" class="smallbutton" onclick="deleteRow(this,'Person','prpLpersonLossSerialNo')" value="-" style="cursor: hand">
                                        </div>
                                    </td>
                                </tr>
                                <c:set var="personNo" value="${tempPerson.personNo}" />
                                <c:set var="personSerialNo" value="${pageScope.personSerialNo + 1}" />
                            </c:if>
                        </c:forEach>
                    </tbody>
                </table>
            </span>
        </td>
    </tr>
</table>
<div id="hospitalList" style="background-color: FFFFFF; display: none; cursor: hand; position: absolute; width: 400px;" align="left"></div>