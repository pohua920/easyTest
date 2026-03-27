<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@include file="/common/taglibs.jsp"%>
<script src="${ctx}/pages/common/account/js/paymentAccount.js"></script>
<script type="text/javascript">
function payObjectInfoOwnerShip(field) { //修改支付方式
    var $ownerShip = $(field);
    var $prpLpayObjectInfo = $ownerShip.parents("tr[name='PrpLpayObjectInfo']");
    if ($ownerShip.val() == "B") { //汇款
        $prpLpayObjectInfo.find("span[name='spanCutBack']").hide(); //隐藏禁背
        $prpLpayObjectInfo.find("tr[name='bankInfo']").show(); //开放银行帳户录入
    } else if ($ownerShip.val() == "Q"){
        $prpLpayObjectInfo.find("tr[name='bankInfo']").hide(); //关闭银行帳户录入
        $prpLpayObjectInfo.find("span[name='spanCutBack']").show(); //显示禁背
    }
 }
/**
 * 賠付代號改变时
 * 如果賠付代號(賠案)值為3，費用代碼類型自動設為 6（費用類型：健保局）
 */

function setPrpLpayObjectInfoPaycodeType(field) {
    if (field.value == "3") { //健保局
        $("#PrpLpayObject").find("tr[name='PrpLpayObjectInfo']").each(function () {
            //赔案代号为健保局时，设置所有的費用代碼類为健保局
            var $paymentKind = $(this).find(":input[name='prpLpayObjectInfoPaymentKind']");
            $paymentKind.val("6");
        });
    }
}

function afterInsertPrpLpayObject(PrpLpayObject,pageCode,btnField,csFieldName,psFieldName){
    //当前赔付代号为健保局时，需设置費用類型为健保局
    if ($(":input[name='prpLpayObjectInfoPaycodeType']").val() == "3") {
        $(PrpLpayObject).find(":input[name='prpLpayObjectInfoPaymentKind']").val("6");
    }
}
/***
 * 删除赔付对象，标的赔付和人员伤亡赔付的赔付对象讯息需要重新处理
 */
function afterDeletePrpLpayObject(deletObject,btnField,pageCode,csFieldName){
    var deletePayObjectSerialNo = parseInt($(deletObject).find("input[name='"+csFieldName+"']").val());
    $(":input[name$='PayObjectSerialNo']").each(function(){ //处理所有的赔付对象序号
         if($.trim(this.value)!=""){
              var serialNoStrArry = this.value.split(";");
              var newSerialNoStr = "";
              for(var i = 0; i < serialNoStrArry.length; i++){
                  var temp = serialNoStrArry[i].split(":");
                  if(parseInt(temp[0]) < parseInt(deletePayObjectSerialNo) ){//序号小的直接拿
                      newSerialNoStr += serialNoStrArry[i]+ ";";
                  } else if (parseInt(temp[0]) > parseInt(deletePayObjectSerialNo)){
                      newSerialNoStr += (parseInt(temp[0]) - 1) + ":" + temp[1] + ";"; //序号大的，减1再拿
                  }
              }
              if(newSerialNoStr !=""){
                  newSerialNoStr = newSerialNoStr.substring(0, newSerialNoStr.length - 1);
              }
              this.value = newSerialNoStr;
         }
     });
}
/***
 * 删除赔付讯息后，根据被删除元素的赔付对象序号，重新计算本条记录涉及的收取对象的金额
 * @param serialNoStr 追偿收入讯息的Jquery对象
 */
function calPayAmount(serialNoStr) {
    if($.trim(serialNoStr)!=""){
        var serialNoStrArray = serialNoStr.split(";");
        var $payAmount = $("#PrpLpayObject").find("input[name='prpLpayObjectInfoPayAmount']");
        for(var i = 0;i < serialNoStrArray.length; i++){
            var temp = serialNoStrArray[i].split(":");
            if(parseFloat(temp[1]) != 0){
                var f = $payAmount.get(parseInt(temp[0])-1);
                f.value = Math.round(parseFloat(f.value) - parseFloat(temp[1]));
            }
        }
    }
}
</script>
<table class="common" style="width: 100%" id="PrpLpayObject_Data" style="display:none">
    <tbody>
        <tr name="PrpLpayObjectInfo">
            <td class="subformtitle" style="width: 96%">
                <table class="common" style="width: 100%">
                    <tr>
                        <td class="input" colspan="6">
                            <b><s:text name="compensate.paymentObject" />&nbsp;<input type="text" name="prpLpayObjectInfoSerialNo" value="" readonly="readonly" class="readonly">
                        </td>
                    </tr>
                    <tr>
                        <td class="title" style="width: 15%">
                            <s:text name="common.compensate.claimPayment" />：<%--赔款支付方式--%>
                        </td>
                        <td class="input" style="width: 18%">
                            <select name="prpLpayObjectInfoOwnerShip" style="width: 50%" onchange="payObjectInfoOwnerShip(this);">
                                <option value="B" selected="selected"><s:text name="compensate.remittance" /></option><!-- 汇款 -->
                                <option value="Q"><s:text name="compensate.agentInfo.cheque" /></option><!-- 支票 -->
                            </select>
                        </td>
                        <td class="title" style="width: 15%">
                            <s:text name="common.compensate.payAmount" />：<%--理賠金額 --%>
                        </td>
                        <td class="input" style="width: 18%">
                            <input name="prpLpayObjectInfoPayAmount" class="readonly" readonly value="0" title="<s:text name='common.compensate.payAmount'/>" style="width: 80px;" />
                        </td>
                        <td class="title" style="width: 15%">支付幣別：</td>
                        <td class="input" style="width: 18%">
                            <select name="prpLpayObjectInfoAccountCurrency" class="common" style="width: 160px" onchange="getPrpLprpLpayObjectExchRate(this);">
                                <c:forEach items="${requestScope.prpLpayObjectInfoCurrencyList}" var="tempMap">
                                    <option value="${tempMap.key}" <c:if test="${tempMap.key==requestScope.LOCAL_CURRENCY}">selected="selected"</c:if>><c:out value="${tempMap.key}" /> - <c:out value="${tempMap.value}" /></option>
                                </c:forEach>
                            </select>
                            <input type="hidden" name="prpLpayObjectInfoCurrency" value="${LOCAL_CURRENCY }">
                            <input type="hidden" name="prpLpayObjectInfoExchRate" value="1">
                        </td>
                    </tr>
                    <tr>
                        <td class="title" style="width: 15%">
                            <s:text name="compensate.paymentObject" />：<%--赔付对象--%>
                        </td>
                        <td class="input" style="width: 18%">
                            <input name="prpLpayObjectInfoOwnerName" class="input" maxlength="120" value="${requestScope.prpLpayObjectInfo.ownerName}">
                            <img src="${ctx}/images/bgMarkMustInput.jpg">
                        </td>
                        <td class="title" style="width: 15%">賠付類型：</td>
                        <td class="input" style="width: 18%">
                            <s:select name="prpLpayObjectInfoPaymentKind" list="#request.paymentKindList" listKey="key" listValue="value" headerKey="" headerValue=""></s:select>
                            <img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
                        </td>
                        <td class="title" style="width: 15%">
                            <s:text name="common.compensate.payeePhone" />：<%--受款人電話--%>
                        </td>
                        <td class="input" style="width: 18%">
                            <input name="prpLpayObjectInfoBeneficiaryPhone" class="input" value="" />
                            <img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" name="BeneficiaryPhoneIMG" />
                        </td>
                    </tr>
                    <tr>
                        <td class="title" style="width: 15%">證件類型：</td>
                        <td class="input" style="width: 18%">
                            <s:select name="prpLpayObjectInfoCertificateCode" listKey="key" listValue="value" list="#request.prpdpaymentaccountCertificateTypeList" />
                        </td>
                        <td class="title" style="width: 15%">證件號碼：</td>
                        <td class="input" style="width: 18%">
                            <input name="prpLpayObjectInfoUniformNo" class="input" value="" />
                        </td>
                        <td class="title" style="width: 15%">
                            <span name="spanCutBack" style="display: none;"><s:text name="common.compensate.cutBack" />：</span>
                            <%--禁背--%>
                        </td>
                        <td class="input" style="width: 18%">
                            <span name="spanCutBack" style="display: none"><s:select name="prpLpayObjectInfoCutBack" list="#{'0':'否','1':'是'}" listKey="key" listValue="value" value="1" /></span>
                        </td>
                    </tr>
                    <tr name="bankInfo">
                        <td class="title" style="width: 15%">
                            <s:text name="common.compensate.bankCode" />：<%--总行代号--%>
                        </td>
                        <td class="input" style="width: 18%">
                            <input name="prpLpayObjectInfoBankCode" value="" readOnly="readonly" class="readonly" />
                        </td>
                        <td class="title" style="width: 15%">
                            <s:text name="common.compensate.bankName" />：<%--总行名称--%>
                        </td>
                        
                        <td class="input" style="width: 18%">
                            <input name="prpLpayObjectInfoBankName" value="" readOnly="readonly" class="readonly">
                        </td>
                        <td class="title" style="width: 15%">
                            <s:text name="common.compensate.accountCode" />：<%--汇款账号--%>
                        </td>
                        <td class="input" style="width: 18%">
                            <input name="prpLpayObjectInfoAccountCode" value="" readOnly="readonly" class="readonly">
                        </td>
                    </tr>
                    <tr name="bankInfo">
                        <td class="title" style="width: 15%">
                            <s:text name="common.compensate.customBankCode" />：<%--分行代号--%>
                        </td>
                        <td class="input" style="width: 18%">
                            <input name="prpLpayObjectInfoCustomBankCode" value="" readOnly="readonly" class="readonly">
                        </td>
                        <td class="title" style="width: 15%">
                            <s:text name="common.compensate.customBankName" />：<%--分行名称--%>
                        </td>
                        <td class="input" style="width: 18%">
                            <input name="prpLpayObjectInfoCustomBankName" value="" readOnly="readonly" class="readonly">
                        </td>
                        <td class="title" style="width: 33%" colspan="2" align="center">
                            <!-- 录入赔款支付帳户信息 -->
                            <input class='bigbutton' type='button' name='buttonAccCompensate' style="width: 180px;" value='<s:text name="button.inputPaymentInformation.value" />' onclick="queryUserCompensate(this);">
                        </td>
                    </tr>
                    <tr name="AreaInfo">
                    <!-- mantis：CLM0145，處理人員：DP0713，需求單編號：CLM0145，.新核心-理算任務處理賠付對象郵遞區號長度檢核 -->
                    <!--  \webapp\claim\pages\commonLiab\compensate\EditPrpdpaymentaccountPage.jsp -->
                        <td class="title" style="width: 15%">
                            <s:text name="common.compensate.areaCode" />：<%--邮递区号--%>
                        </td>
                        <td class="input" style="width: 18%">
                       		<!-- mantis：CLM0145，處理人員：DP0713，需求單編號：CLM0145，.新核心-理算任務處理賠付對象郵遞區號長度檢核 -->
                            <input name="prpLpayObjectInfoAreaCode" class="input" maxlength="3">
                            <img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
                        </td>
                        <td class="title" style="width: 15%">
                            <s:text name="common.compensate.courierAddress" />：<%--邮递地址--%>
                        </td>
                        <td class="input" style="width: 50%" colspan="3">
                            <input name="prpLpayObjectInfoCourierAddress" class="input">
                            <img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
                        </td>
                    </tr>
                    <tr name="PayDate" style="display: none">
                        <td class="title" style="width: 15%">
                            <s:text name="common.compensate.paymentDate" />：<%--付款日期--%>
                        </td>
                        <td class="input" style="width: 18%">
                            <rc:rcDate name="prpLpayObjectInfoPayDate" class="input" />
                        </td>
                        <td class="title" style="width: 15%">
                            <s:text name="regist.mobilePhones" />：<%--行动电话--%>
                        </td>
                        <td class="input" style="width: 18%">
                            <input name="prpLpayObjectInfoMobilePhoneNo" class="input">
                        </td>
                        <td class="title" style="width: 15%"></td>
                        <td class="input" style="width: 18%"></td>
                    </tr>
                </table>
            </td>
            <td class="input" style="width: 4%;">
                <div>
                    <input type=button name="buttonPayAccountInfoDelete" class="smallbutton" onclick="deleteRow(this,'PrpLpayObject','prpLpayObjectInfoSerialNo');" value="-" style="cursor: hand">
                </div>
            </td>
        </tr>
    </tbody>
</table>
<table class="common" align="center" style="width: 100%">
    <tr>
        <td class="common">
            <img style="cursor: hand;" src="${ctx}/images/butCollapseBlue.gif" name="ChargeImg" onclick="showPage(this,spanPayAccountInfo);">
            <b><s:text name="common.compensate.paymentinfo" /></b><%--賠款給付對象資訊--%> 
            <s:set var="prpLpayObjectInfoPaycodeType" value="" scope="page" />
            <c:if test="${not empty requestScope.prpLpayObjectInfo.prpLpayObjectInfoList}">
                <s:set var="prpLpayObjectInfoPaycodeType" value="#request.prpLpayObjectInfo.prpLpayObjectInfoList.get(0).paycodeType" scope="page" />
            </c:if>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <%--赔付代号（赔案）--%>
            <s:text name="common.compensate.payoutCode" />
            <select name="prpLpayObjectInfoPaycodeType" style="width: 100px" onchange="setPrpLpayObjectInfoPaycodeType(this);">
                <option value="1" <s:if test="#attr.prpLpayObjectInfoPaycodeType==1">selected="selected"</s:if>><s:text name="common.compensate.pei" /><%--一般赔案--%></option>
                <option value="2" <s:if test="#attr.prpLpayObjectInfoPaycodeType==2">selected="selected"</s:if>><s:text name="common.compensate.interbank" /><%--同业--%></option>
                <option value="3" <s:if test="#attr.prpLpayObjectInfoPaycodeType==3">selected="selected"</s:if>><s:text name="common.compensate.NHI" /><%--健保局--%></option>
            </select>
            <span id="spanPayAccountInfo" style="display: none">
                <table class="common" align="center" cellspacing="1" cellpadding="0" id="PrpLpayObject">
                    <thead>
                        <tr>
                            <td class="centertitle" colspan=2>
                                <s:text name="common.compensate.paymentObjectinfo" /><%--赔付对象讯息--%>
                            </td>
                        </tr>
                    </thead>
                    <tfoot>
                        <tr>
                            <td class="title" style="width: 96%">
                                <s:text name="common.compensate.addmessage1" /><%--（按“+”号键的增加赔付对象的讯息，按“ - ”号键删除赔付对象讯息）--%>
                            </td>
                            <td class="title" align="right" style="width: 4%">
                                <div align="center">
                                    <input type="button" value="+" class=smallbutton onclick="insertRow('PrpLpayObject',this,'prpLpayObjectInfoSerialNo');" name="buttonPayAccountInfoInsert" style="cursor: hand">
                                </div>
                            </td>
                        </tr>
                    </tfoot>
                    <tbody>
                        <c:forEach var="prpLpayObject" items="${requestScope.prpLpayObjectInfo.prpLpayObjectInfoList}" varStatus="stat">
                            <tr name="PrpLpayObjectInfo">
                                <td class="subformtitle" style="width: 96%">
                                    <table class="common" style="width: 100%">
                                        <tr>
                                            <td class="input" colspan="6">
                                                <b><s:text name="compensate.paymentObject" />&nbsp;<input type="text" name="prpLpayObjectInfoSerialNo" value="<c:out value="${prpLpayObject.id.serialNo}"/>" readonly="readonly" class="readonly">
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="title" style="width: 15%">
                                                <s:text name='common.compensate.claimPayment' />：<%--赔款支付方式--%>
                                            </td>
                                            <td class="input" style="width: 18%">
                                                <select name="prpLpayObjectInfoOwnerShip" style="width: 50%" onchange="payObjectInfoOwnerShip(this);">
                                                    <option value="B" <c:if test="${prpLpayObject.ownerShip=='B'}"><c:out value="selected"/></c:if>><s:text name="compensate.remittance" /></option>
                                                    <!-- 汇款 -->
                                                    <option value="Q" <c:if test="${prpLpayObject.ownerShip=='Q'}"><c:out value="selected"/></c:if>><s:text name="compensate.agentInfo.cheque" /></option>
                                                    <!-- 支票 -->
                                                </select>
                                            </td>
                                            <td class="title" style="width: 15%">
                                                <s:text name="common.compensate.payAmount" />：<%--理赔金额--%>
                                            </td>
                                            <td class="input" style="width: 18%">
                                                <input name="prpLpayObjectInfoPayAmount" class="readonly" readonly value="<fmt:formatNumber value="${prpLpayObject.payAmount}" pattern="#.##"/>" 
                                                    title="<s:text name="common.compensate.payAmount"/>" style="width: 80px;" />
                                            </td>
                                            <td class="title" style="width: 15%">支付幣別：</td>
                                            <td class="input" style="width: 18%">
                                                <select name="prpLpayObjectInfoAccountCurrency" class="common" style="width: 160px" onchange="getPrpLprpLpayObjectExchRate(this);">
                                                    <c:forEach items="${requestScope.prpLpayObjectInfoCurrencyList}" var="tempMap" >
                                                        <option value="${tempMap.key}" <c:if test="${tempMap.key==prpLpayObject.currency}">selected="selected"</c:if>><c:out value="${tempMap.key}" /> - <c:out value="${tempMap.value}" /></option>
                                                    </c:forEach>
                                                </select>
                           						<input type="hidden" name="prpLpayObjectInfoCurrency" value="${prpLpayObject.currency }">
                           						<input type="hidden" name="prpLpayObjectInfoExchRate" value="${prpLpayObject.exchRate }">
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="title" style="width: 15%">
                                                <s:text name="compensate.paymentObject" />：<%--赔付对象--%>
                                            </td>
                                            <td class="input" style="width: 18%">
                                                <input name="prpLpayObjectInfoOwnerName" class="input" maxlength="120" value="${prpLpayObject.ownerName}">
                                                <img src="${ctx}/images/bgMarkMustInput.jpg">
                                            </td>
                                            <td class="title" style="width: 15%">賠付類型：</td>
                                            <td class="input" style="width: 18%">
                                                <c:set var="tempSelectedValue" value='${prpLpayObject.paymentKind}' />
                                                <s:select name="prpLpayObjectInfoPaymentKind" list="#request.paymentKindList" listKey="key" listValue="value" value="#attr.tempSelectedValue"></s:select>
                                                <img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
                                            </td>
                                            <td class="title" style="width: 15%">
                                                <s:text name="common.compensate.payeePhone" />：<%--受款人电话--%>
                                            </td>
                                            <td class="input" style="width: 18%">
                                                <input name="prpLpayObjectInfoBeneficiaryPhone" class="input" value="${prpLpayObject.beneficiaryPhone}" />
                                                <img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" name="BeneficiaryPhoneIMG" />
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="input" style="width: 15%">證件類型：</td>
                                            <td class="input" style="width: 18%">
                                                <c:set var="tempCertificateCode" value='${prpLpayObject.certificateCode}' />
                                                <s:select name="prpLpayObjectInfoCertificateCode" value="#attr.tempCertificateCode" listKey="key" listValue="value" list="#request.prpdpaymentaccountCertificateTypeList" />
                                            </td>
                                            <td class="title" style="width: 15%">證件號碼：</td>
                                            <td class="input" style="width: 18%">
                                                <input name="prpLpayObjectInfoUniformNo" class="input" value="${prpLpayObject.uniformNo}" />
                                            </td>
                                            <td class="title" style="width: 15%">
                                                <span name="spanCutBack" <c:if test="${prpLpayObject.ownerShip!='Q'}">style="display:none;"</c:if>><s:text name="common.compensate.cutBack" />：<%--禁背--%></span>
                                            </td>
                                            <td class="input" style="width: 18%">
                                                <span name="spanCutBack" <c:if test="${prpLpayObject.ownerShip!='Q'}">style="display:none;"</c:if>>
                                                    <select name="prpLpayObjectInfoCutBack" >
                                                        <option value="0" <c:if test="${prpLpayObject.cutBack=='0'}"> selected</c:if>><s:text name="regist.prpLregist.no" /></option>
                                                        <%--否--%>
                                                        <option value="1" <c:if test="${prpLpayObject.cutBack=='1'}"> selected</c:if>><s:text name="regist.prpLregist.yes" /></option>
                                                        <%--是--%>
                                                    </select>
                                                </span>
                                            </td>
                                        </tr>
                                        <tr name="bankInfo" <c:if test="${prpLpayObject.ownerShip!='B'}">style="display:none;"</c:if>>
                                            <td class="title" style="width: 15%">
                                                <s:text name="common.compensate.bankCode" />：<%--总行代号--%>
                                            </td>
                                            <td class="input" style="width: 18%">
                                                <input name="prpLpayObjectInfoBankCode" readOnly="readonly" class="readonly" value="${prpLpayObject.bankCode}" />
                                            </td>
                                            <td class="title" style="width: 15%">
                                                <s:text name="common.compensate.bankName" />：<%--总行名称--%>
                                            </td>
                                            <td class="input" style="width: 18%">
                                                <input name="prpLpayObjectInfoBankName" readOnly="readonly" class="readonly" value="${prpLpayObject.bankName}">
                                            </td>
                                            <td class="title" style="width: 15%">
                                                <s:text name="common.compensate.accountCode" />：<%--汇款账号--%>
                                            </td>
                                            <td class="input" style="width: 18%">
                                                <input name="prpLpayObjectInfoAccountCode" readOnly="readonly" class="readonly" value="${prpLpayObject.accountCode}">
                                            </td>
                                        </tr>
                                        <tr name="bankInfo" <c:if test="${prpLpayObject.ownerShip!='B'}">style="display:none;"</c:if>>
                                            <td class="title" style="width: 15%">
                                                <s:text name="common.compensate.customBankCode" />：<%--分行代号--%>
                                            </td>
                                            <td class="input" style="width: 18%">
                                                <input name="prpLpayObjectInfoCustomBankCode" readOnly="readonly" class="readonly" value="${prpLpayObject.customBankCode}">
                                            </td>
                                            <td class="title" style="width: 15%">
                                                <s:text name="common.compensate.customBankName" />：<%--分行名称--%>
                                            </td>
                                            <td class="input" style="width: 18%">
                                                <input name="prpLpayObjectInfoCustomBankName" readOnly="readonly" class="readonly" value="${prpLpayObject.customBankName}">
                                            </td>
                                            <td class="title" style="width: 33%" colspan="2" align="center">
                                                <!-- 录入赔款支付帳户信息 -->
                                                <input class='bigbutton' type='button' name='buttonAccCompensate' style="width: 180px;" value='<s:text name="button.inputPaymentInformation.value" />' onclick="queryUserCompensate(this);">
                                            </td>
                                        </tr>
                                        <tr name="AreaInfo" <c:if test="${prpLpayObject.ownerShip=='C'}">style="display:none"</c:if>>
                                            <!-- mantis：CLM0145，處理人員：DP0713，需求單編號：CLM0145，.新核心-理算任務處理賠付對象郵遞區號長度檢核 -->
                                            <!--  \webapp\claim\pages\commonLiab\compensate\EditPrpdpaymentaccountPage.jsp 2-->
                                            <td class="title" style="width: 15%">
                                                <s:text name="common.compensate.areaCode" />：<%--邮递区号--%>
                                            </td>
                                            <td class="input" style="width: 18%">
                                            	<!-- mantis：CLM0145，處理人員：DP0713，需求單編號：CLM0145，.新核心-理算任務處理賠付對象郵遞區號長度檢核 -->
                                                <input name="prpLpayObjectInfoAreaCode" class="input" value="${prpLpayObject.areaCode}" maxlength="3"/>
                                                <img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
                                            </td>
                                            <td class="title" style="width: 15%">
                                                <s:text name="common.compensate.courierAddress" />：<%--邮递地址--%>
                                            </td>
                                            <td class="input" style="width: 50%" colspan="3">
                                                <input name="prpLpayObjectInfoCourierAddress" class="input" value="${prpLpayObject.courierAddress}">
                                                <img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
                                            </td>
                                        </tr>
                                        <tr name="PayDate" <c:if test="${prpLpayObject.ownerShip!='C'}">style="display:none"</c:if>>
                                            <td class="title" style="width: 15%">
                                                <s:text name="common.compensate.paymentDate" />：<%--付款日期--%>
                                            </td>
                                            <td class="input" style="width: 18%">
                                                <rc:rcDate name="prpLpayObjectInfoPayDate" class="input" value="${prpLpayObject.payDate}" />
                                            </td>
                                            <td class="title" style="width: 15%">
                                                <s:text name="regist.mobilePhones" />：<%--行动电话--%>
                                            </td>
                                            
                                            <td class="input" style="width: 18%">
                                                <input name="prpLpayObjectInfoMobilePhoneNo" class="input" value="${prpLpayObject.mobilePhoneNo}">
                                            </td>
                                            <td class="title" style="width: 15%"></td>
                                            <td class="input" style="width: 18%"></td>
                                        </tr>
                                    </table>
                                </td>
                                <td class="input" style="width: 4%;">
                                    <div>
                                        <input type=button name="buttonPayAccountInfoDelete" class="smallbutton" onclick="deleteRow(this,'PrpLpayObject','prpLpayObjectInfoSerialNo');" value="-" style="cursor: hand">
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </span>
        </td>
    </tr>
</table>
<div id="divPayObjectinfo" style='width: 300; display: none; position: absolute; background-color: FFFFFF;' class="common" align="left">
    <ul id="uLprpLPayObjectinfo" style='list-style-type: none; padding-left: 0; margin-left: 0'></ul>
    <ul align="center" style='list-style-type: none; padding-left: 0; margin-left: 0;'>
        <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" class="button" name="closePrpObjectinfoSerialNo" onclick="hideSubPage(this,'divPayObjectinfo')"
                value="<s:text name='button.close.value' />" /></li>
    </ul>
</div>