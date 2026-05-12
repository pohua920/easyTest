<%@ include file="/common/taglibs.jsp"%>
<script src="${ctx}/pages/common/account/js/paymentAccount.js"></script>
<script>
function replevyOwnerShip_change(field) {
    var $ownerShip = $(field);
    var $prpLpayObjectInfo = $ownerShip.parents("tr[name='PrpLpayObjectInfo']");
    if ($ownerShip.val() == "B") { //汇款
        $prpLpayObjectInfo.find("span[name='spanCutBack']").hide(); //隐藏禁背
        //$prpLpayObjectInfo.find("tr[name='bankInfo']").hide(); //开放银行帳户輸入
        //$prpLpayObjectInfo.find("tr[name='AreaInfo']").show(); //隐藏邮递信息
    } else if ($ownerShip.val() == "Q") { //支票
        $prpLpayObjectInfo.find("span[name='spanCutBack']").show(); //显示禁背
        //$prpLpayObjectInfo.find("tr[name='bankInfo']").hide(); //隐藏银行帳户輸入
        //$prpLpayObjectInfo.find("tr[name='AreaInfo']").show(); //隐藏邮递信息
    } else if ($ownerShip.val() == "C") { //现金
        $prpLpayObjectInfo.find("span[name='spanCutBack']").hide(); //隐藏禁背项
        //$prpLpayObjectInfo.find("tr[name='bankInfo']").hide(); //隐藏银行支付账户讯息
        //$prpLpayObjectInfo.find("tr[name='AreaInfo']").hide(); //隐藏邮递信息
    }
}
/***
 * 设置payObjectIndex显示的序号
 */
function afterInsertPrpLpayObject(obj,pageCode,btnField,csFieldName){
    setObjectIndex()
}

/***
 * 1，更新显示序号
 * 2，赔付对象删除后，调整预追偿的对每个收付对象的金额调整
 */
function afterDeletePrpLpayObjectInfo(deletObject,btnField,pageCode,csFieldName){
    setObjectIndex();//更新显示序号
    var deletePayObjectSerialNo = parseInt($(deletObject).find("input[name='"+csFieldName+"']").val());
    $("#PrpLloss").find("input[name='prpLlossPayObjectSerialNo']").each(function(){
         if($.trim(this.value)!=""){
              var serialNoStrArry = this.value.split(";");
              var newSerialNoStr = "";
              for(var i = 0; i < serialNoStrArry.length; i++){
                  var temp = serialNoStrArry[i].split(":");
                  if(parseInt(temp[0]) < parseInt(deletePayObjectSerialNo) ){//序号小的直接拿
                      newSerialNoStr += serialNoStrArry[i]+ ";";
                  } else if (parseInt(temp[0]) > parseInt(deletePayObjectSerialNo)){
                      newSerialNoStr += (parseInt(temp[0]) - 1) + ":" + temp[1] + ";";
                  }
              }
              if(newSerialNoStr !=""){
                  newSerialNoStr = newSerialNoStr.substring(0, newSerialNoStr.length - 1);
              }
              this.value = newSerialNoStr;
         }
     });
}

function setObjectIndex(){
    $("#PrpLpayObject").find(":input[name='prpLpayObjectInfoSerialNo']").each(function(){
        $(this).closest("td").find("span[name='payObjectIndex']").html(this.value);
    });
}


</script>
<c:if test="${prpLcompensate!=null}">
    <table class="common" align="center" style="width: 100%" id="tablePrpLpayObject">
        <tr>
            <td class="common">
                <img style="cursor: hand;" src="${ctx}/images/butExpandBlue.gif" name="ChargeImg" onclick="showPage(this,spanPayPersonInfo);">
                <b>追償對象訊息</b><br>
                <span style="display: none">
                    <table class="common" style="width: 100%" id="PrpLpayObject_Data">
                        <tbody>
                            <tr name="PrpLpayObjectInfo">
                                <td class="subformtitle" style="width: 96%">
                                    <table class="common" style="width: 100%">
                                        <tr>
                                            <td class="title" colspan="6">
                                                <b>追償對象&nbsp;<span name="payObjectIndex"> </span>：
                                                </b>
                                                <input type="hidden" name="prpLpayObjectInfoSerialNo" value="">
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="title" style="width: 12%">追償款收取方式：</td>
                                            <td class="input" style="width: 20%">
                                                <select name="prpLpayObjectInfoOwnerShip" onchange="replevyOwnerShip_change(this);">
                                                    <option value="B" selected="selected"><s:text name="compensate.remittance" /><%--汇款 --%></option>
                                                    <option value="C"><s:text name="compensate.agentInfo.cash" /><!-- 现金 --></option>
                                                    <option value="Q"><s:text name="compensate.agentInfo.cheque" /><%--支票 --%></option>
                                                </select>
                                            </td>
                                            <td class="title" style="width: 12%">追償金額：</td>
                                            <td class="input" style="width: 20%">
                                                <input type="text" name="prpLpayObjectInfoPayAmount" readonly class="readonly" value="0" onfocus="cacheData(this);" onchange="validateMoney(this);">
                                            </td>
                                            <td class="title" style="width: 12%">幣別：</td>
                                            <td class="input" style="width: 20%">
                                            	 <c:choose>
				                                    <c:when test="${pageScope.registerFlag||pageScope.negotiationFlag}">
				                                        <input class="readonly" type="text" style="width: 70px" name="prpLpayObjectInfoAccountCurrency" value="${requestScope.LOCAL_CURRENCY}">
				                                    </c:when>
				                                    <c:otherwise>
				                                        <select name="prpLpayObjectInfoAccountCurrency" class="input" style="width: 150px" onchange="getPrpLpayObjectInfoExchRate(this);">
				                                            <c:forEach items="${requestScope.prpLpayObjectInfoCurrencyList}" var="tempMap">
				                                                <option value="${tempMap.key}" <c:if test="${tempMap.key==requestScope.LOCAL_CURRENCY}">selected="selected"</c:if>><c:out value="${tempMap.key}"/>-<c:out value="${tempMap.value}"/></option>
				                                            </c:forEach>
				                                       	</select>
				                                  	</c:otherwise>
				                                </c:choose>
				                                 <input type="hidden" name="prpLpayObjectInfoCurrency" value="${requestScope.LOCAL_CURRENCY }">
				                                 <input type="hidden" name="prpLpayObjectInfoExchRate" value="1">
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="title" style="width: 12%">追償對象：</td>
                                            <td class="input" style="width: 20%">
                                                <!-- mantis： CLM0017，處理人員：Sam，需求單編號：CLM0017，原住名姓名調整作業_車 -->
                                                <input type="text" name="prpLpayObjectInfoOwnerName" class="input" maxlength="100">
                                                <img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
                                            </td>
                                            <td class="title" style="width: 12%">證件類型：</td>
                                            <td class="input" style="width: 20%">
                                                <s:select name="prpLpayObjectInfoCertificateCode" listKey="key" listValue="value" headerKey=" " headerValue=" " list="#request.prpdpaymentaccountCertificateTypeList" />
                                            </td>
                                            <td class="title" style="width: 12%">負責人：</td>
                                            <td class="input" style="width: 20%">
                                            	 <input type="text" name="prpLpayObjectInfoManager" class="input" maxlength="120">
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="title" style="width: 12%">證件號碼：</td>
                                            <td class="input" style="width: 20%">
                                                <input type="text" name="prpLpayObjectInfoUniformNo" class="input" maxlength="20">
                                            </td>
                                            <td class="title" style="width: 12%">受款人電話：</td>
                                            <td class="input" style="width: 20%">
                                                <input type="text" name="prpLpayObjectInfoBeneficiaryPhone" class="input" maxlength="20">
                                            </td>
                                            <td class="title" style="width: 12%" >
                                                <span name="spanCutBack" style="display:none;"><s:text name="common.compensate.cutBack" />：<%--禁背--%></span>
                                            </td>
                                            <td class="input" style="width: 20%">
                                                <span name="spanCutBack" style="display:none;">
                                                    <select name="prpLpayObjectInfoCutBack" >
                                                        <option value="0" ><s:text name="regist.prpLregist.no" /></option>
                                                        <option value="1" selected="selected"><s:text name="regist.prpLregist.yes" /></option>
                                                    </select>
                                                </span>
                                            </td>
                                        </tr>
                                        <tr name="bankInfo" style="display:none;">
                                            <td class="title" style="width: 12%">總行代號：</td>
                                            <%--總行代號 --%>
                                            <td class="input" style="width: 20%">
                                                <input type="text" readOnly="readonly" class="readonly" name="prpLpayObjectInfoBankCode" />
                                            </td>
                                            <td class="title" style="width: 12%">總行名稱：</td>
                                            <%--總行名稱 --%>
                                            <td class="input" style="width: 20%">
                                                <input type="text" readOnly="readonly" class="readonly" name="prpLpayObjectInfoBankName" value="" />
                                            </td>
                                            <td class="title" style="width: 12%">匯款帳號：</td>
                                            <%--匯款帳號 --%>
                                            <td class="input" style="width: 20%">
                                                <input type="text" name="prpLpayObjectInfoAccountCode" readOnly="readonly" class="readonly" value="" />
                                            </td>
                                        </tr>
                                        <tr name="bankInfo" style="display:none;">
                                            <td class="title" style="width: 12%">分行代號：</td>
                                            <td class="input" style="width: 20%">
                                                <input type="text" name="prpLpayObjectInfoCustomBankCode" readOnly="readonly" class="readonly">
                                            </td>
                                            <td class="title" style="width: 12%">分行名稱：</td>
                                            <td class="input" style="width: 20%">
                                                <input type="text" name="prpLpayObjectInfoCustomBankName" readOnly="readonly" class="readonly">
                                            </td>
                                            <td class="title" style="width: 32%" colspan="2" align="center">
                                                <input class='bigbutton' type='button' name='buttonAccCompensate' style="width: 180px;" value='<s:text name="button.inputPaymentInformation.value" />' onclick="queryUserCompensate(this);">
                                            </td>
                                        </tr>
                                        <tr name="AreaInfo" >
                                        <!-- mantis：CLM0145，處理人員：DP0713，需求單編號：CLM0145，.新核心-理算任務處理賠付對象郵遞區號長度檢核 -->
                                        <!-- \webapp\claim\pages\common\replevy\EditPrpdpaymentaccountPage.jsp -->
                                            <td class="title" style="width: 12%">郵遞區號：</td>
                                            <td class="input" style="width: 20%">
                                            	<!-- mantis：CLM0145，處理人員：DP0713，需求單編號：CLM0145，.新核心-理算任務處理賠付對象郵遞區號長度檢核 -->
                                                <input type="text" name="prpLpayObjectInfoAreaCode" class="input" maxlength="3">
                                            </td>
                                            <td class="title" style="width: 12%">郵遞地址：</td>
                                            <td class="input" style="width: 52%" colspan="3">
                                                <input type="text" name="prpLpayObjectInfoCourierAddress" class="input" maxlength="50" style="width: 95%">
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                                <td class="input" style='width: 4%' align="right">
                                    <div>
                                        <input type=button name="buttonPayPersonInfoDelete" class="smallbutton" onclick="deleteRow(this,'PrpLpayObject','prpLpayObjectInfoSerialNo');" value="-" style="cursor: hand">
                                    </div>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </span> 
                <span id="spanPayPersonInfo" style="display:">
                    <table class="common" style="width: 100%" id="PrpLpayObject">
                        <thead>
                            <tr>
                                <td class="centertitle" colspan=2>追償對象訊息</td>
                            </tr>
                        </thead>
                        <tbody>
                            <c:if test="${prpLpayObjectInfo != null}">
                                <c:forEach var="prpLpayObjectInfo" items="${prpLpayObjectInfo.prpLpayObjectInfoList}">
                                    <tr name="PrpLpayObjectInfo">
                                        <td class="subformtitle" style="width: 96%">
                                            <table class="common" style="width: 100%">
                                                <tr>
                                                    <td class="title" colspan="6">
                                                        <b>追償對象&nbsp;<span name="payObjectIndex"><c:out value="${prpLpayObjectInfo.id.serialNo}" /></span>：
                                                        </b>
                                                        <input type="hidden" name="prpLpayObjectInfoSerialNo" value="<c:out value="${prpLpayObjectInfo.id.serialNo}" />">
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td class="title" style="width: 12%">追償款收取方式：</td>
                                                    <td class="input" style="width: 20%">
                                                        <select name="prpLpayObjectInfoOwnerShip" onchange="replevyOwnerShip_change(this);">
                                                            <option value="B" <c:if test="${prpLpayObjectInfo.ownerShip=='B'}">selected</c:if>>
                                                                <s:text name="compensate.remittance" /><%--汇款 --%>
                                                            </option>
                                                            <option value="C" <c:if test="${prpLpayObjectInfo.ownerShip=='C'}">selected</c:if>>
                                                                <s:text name="compensate.agentInfo.cash" /><!-- 现金 -->
                                                            </option>
                                                            <option value="Q" <c:if test="${prpLpayObjectInfo.ownerShip=='Q'}">selected</c:if>>
                                                                <s:text name="compensate.agentInfo.cheque" /><%--支票 --%>
                                                            </option>
                                                        </select>
                                                    </td>
                                                    <td class="title" style="width: 12%">追償金額：</td>
                                                    <td class="input" style="width: 20%">
                                                        <input type="text" name="prpLpayObjectInfoPayAmount" readonly class="readonly" value="<fmt:formatNumber value="${prpLpayObjectInfo.payAmount}" pattern="#.##"/>" onfocus="cacheData(this);" onchange="validateMoney(this);">
                                                    </td>
                                                    <td class="title" style="width: 12%">幣別：</td>
                                                    <td class="input" style="width: 20%">
                                                    	<c:choose>
						                                    <c:when test="${pageScope.registerFlag||pageScope.negotiationFlag}">
						                                        <input class="readonly" type="text" style="width: 170px" name="prpLpayObjectInfoAccountCurrency" value="${requestScope.LOCAL_CURRENCY}">
						                                    </c:when>
						                                    <c:otherwise>
						                                        <select name="prpLpayObjectInfoAccountCurrency" class="input" style="width: 150px" onchange="getPrpLpayObjectInfoExchRate(this);">
						                                            <c:forEach items="${requestScope.prpLpayObjectInfoCurrencyList}" var="tempMap">
						                                                <option value="${tempMap.key}" <c:if test="${tempMap.key==prpLpayObjectInfo.accountCurrency}">selected="selected"</c:if>><c:out value="${tempMap.key}"/>-<c:out value="${tempMap.value}"/></option>
						                                            </c:forEach>
						                                       	</select>
						                                  	</c:otherwise>
						                                </c:choose>
						                                <input type="hidden" name="prpLpayObjectInfoCurrency" value="${prpLpayObjectInfo.currency }">
						                                <input type="hidden" name="prpLpayObjectInfoExchRate" value="${prpLpayObjectInfo.exchRate }">
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td class="title" style="width: 12%">追償對象：</td>
                                                    <td class="input" style="width: 20%">
                                                        <!-- mantis： CLM0017，處理人員：Sam，需求單編號：CLM0017，原住名姓名調整作業_車 -->
                                                        <input type="text" name="prpLpayObjectInfoOwnerName" class="input" maxlength="100" value="${prpLpayObjectInfo.ownerName}">
                                                        <img src="${ctx}/images/bgMarkMustInput.jpg" complete="complete" />
                                                    </td>
                                                    <td class="title" style="width: 12%">證件類型：</td>
                                                    <td class="input" style="width: 20%">
                                                        <c:set var="tempCertificateCode" value="${prpLpayObjectInfo.certificateCode }" />
                                                        <s:select name="prpLpayObjectInfoCertificateCode" headerKey=" " headerValue=" " value="#attr.tempCertificateCode" listKey="key" listValue="value"
                                                            list="#request.prpdpaymentaccountCertificateTypeList" />
                                                    </td>
                                                    <td class="title" style="width: 12%">負責人：</td>
                                                    <td class="input" style="width: 20%">
                                                    	<input type="text" name="prpLpayObjectInfoManager" class="input" maxlength="120" value="${prpLpayObjectInfo.repLevyManager}" >
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td class="title" style="width: 12%">證件號碼：</td>
                                                    <td class="input" style="width: 20%">
                                                        <input type="text" name="prpLpayObjectInfoUniformNo" class="input" maxlength="20" value="${prpLpayObjectInfo.uniformNo}">
                                                    </td>
                                                    <td class="title" style="width: 12%">受款人電話：</td>
                                                    <td class="input" style="width: 20%">
                                                        <input type="text" name="prpLpayObjectInfoBeneficiaryPhone" class="input" maxlength="20" value="${prpLpayObjectInfo.beneficiaryPhone}">
                                                    </td>
                                                    <td class="title" style="width: 12%" >
                                                        <span name="spanCutBack" <c:if test="${prpLpayObject.ownerShip!='Q'}">style="display:none;"</c:if>><s:text name="common.compensate.cutBack" />：<%--禁背--%></span>
                                                    </td>
                                                    <td class="input" style="width: 20%" >
                                                        <span name="spanCutBack" <c:if test="${prpLpayObject.ownerShip!='Q'}">style="display:none;"</c:if>>
                                                            <select name="prpLpayObjectInfoCutBack" class='common' style="width: 50%">
                                                                <option value="0" <c:if test="${prpLpayObject.cutBack=='0'}"> selected</c:if>><s:text name="regist.prpLregist.no" /></option>
                                                                <%--否--%>
                                                                <option value="1" <c:if test="${prpLpayObject.cutBack=='1'}"> selected</c:if>><s:text name="regist.prpLregist.yes" /></option>
                                                                <%--是--%>
                                                            </select>
                                                        </span>
                                                    </td>
                                                </tr>
                                                <tr name="bankInfo" style="display:none;">
                                                    <td class="title" style="width: 12%">總行代號：</td>
                                                    <td class="input" style="width: 20%">
                                                        <input type="text" readOnly="readonly" class="readonly" name="prpLpayObjectInfoBankCode" value="${prpLpayObjectInfo.bankCode}">
                                                    </td>
                                                    <td class="title" style="width: 12%">總行名稱：</td>
                                                    <td class="input" style="width: 20%">
                                                        <input type="text" readOnly="readonly" class="readonly" name="prpLpayObjectInfoBankName" value="${prpLpayObjectInfo.bankName}">
                                                    </td>
                                                    <td class="title" style="width: 12%">匯款帳號：</td>
                                                    <td class="input" style="width: 20%">
                                                        <input type=text name="prpLpayObjectInfoAccountCode" class="readonly" readOnly="readonly" value="${prpLpayObjectInfo.accountCode}">
                                                    </td>
                                                </tr>
                                                <tr name="bankInfo" style="display:none;">
                                                    <td class="title" style="width: 12%">分行代號：</td>
                                                    <td class="input" style="width: 20%">
                                                        <input type="text" name="prpLpayObjectInfoCustomBankCode" readOnly="readonly" class="readonly" value="${prpLpayObjectInfo.customBankCode}">
                                                    </td>
                                                    <td class="title" style="width: 12%">分行名稱：</td>
                                                    <td class="input" style="width: 20%">
                                                        <input type="text" name="prpLpayObjectInfoCustomBankName" readOnly="readonly" class="readonly" value="${prpLpayObjectInfo.customBankName}">
                                                    </td>
                                                    <td class="title" style="width: 32%" colspan="2" align="center">
                                                        <input class='bigbutton' type='button' name='buttonAccCompensate' style="width: 180px;" value='<s:text name="button.inputPaymentInformation.value" />' onclick="queryUserCompensate(this);">
                                                    </td>
                                                </tr>
                                                <tr name="AreaInfo" >
                                                <!-- mantis：CLM0145，處理人員：DP0713，需求單編號：CLM0145，.新核心-理算任務處理賠付對象郵遞區號長度檢核 -->
                                                <!-- \webapp\claim\pages\common\replevy\EditPrpdpaymentaccountPage.jsp 2-->
                                                    <td class="title" style="width: 12%">郵遞區號：</td>
                                                    <td class="input" style="width: 20%">
                                                    	<!-- mantis：CLM0145，處理人員：DP0713，需求單編號：CLM0145，.新核心-理算任務處理賠付對象郵遞區號長度檢核 -->
                                                        <input type="text" name="prpLpayObjectInfoAreaCode" class="input" maxlength="3" value="${prpLpayObjectInfo.areaCode}">
                                                    </td>
                                                    <td class="title" style="width: 12%">郵遞地址：</td>
                                                    <td class="input" style="width: 52%" colspan="3">
                                                        <input type="text" name="prpLpayObjectInfoCourierAddress" style="width: 95%" class="input" maxlength="50" value="${prpLpayObjectInfo.courierAddress}">
                                                    </td>
                                                </tr>
                                            </table>
                                        </td>
                                        <td class="input" style='width: 4%' align="right">
                                            <div>
                                                <input type=button name="buttonPayPersonInfoDelete" class="smallbutton" onclick="deleteRow(this,'PrpLpayObject','prpLpayObjectInfoSerialNo');" value="-" style="cursor: hand">
                                            </div>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:if>
                        </tbody>
                        <tfoot>
                            <tr>
                                <td class="title" style="width: 96%">
                                    <s:text name="prompt.replvy.addRemove" />
                                    <%--（按“+”号键的增加收取对象的讯息，按“ - ”号键删除讯息）--%>
                                    <c:if test="${coinsFlag=='1'}">
                                        <font color='red' style="display: none">&nbsp&nbsp<s:text name="replevy.query3" />
                                            <%--本案涉及联、共保，请輸入总的追偿费用，系统会自动计算出我方金额 --%></font>
                                    </c:if>
                                </td>
                                <td class="title" align="right" style="width: 4%">
                                    <div align="right">
                                        <input type="button" value="+" class=smallbutton onclick="insertRow('PrpLpayObject',this,'prpLpayObjectInfoSerialNo');" name="buttonPayPersonInfoInsert" style="cursor: hand">
                                    </div>
                                </td>
                            </tr>
                        </tfoot>
                    </table>
                </span>
            </td>
        </tr>
    </table>
</c:if>
<div id="divPayObjectinfo" style='width: 300px; display: none; position: absolute; background-color: #FFFFFF;' class="common" align="left">
    <ul id="uLprpLPayObjectinfo" style='list-style-type: none; padding-left: 0; margin-left: 0'></ul>
    <ul align="center" style='list-style-type: none; padding-left: 0; margin-left: 0;'>
        <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" class="button" name="closePrpObjectinfoSerialNo" onclick="hideSubPage(this,'divPayObjectinfo')" value="<s:text name='button.close.value' />" /></li>
    </ul>
</div>