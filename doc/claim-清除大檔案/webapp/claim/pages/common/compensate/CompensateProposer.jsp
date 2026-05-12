<%--
****************************************************************************
* DESC       ：添加驾驶员信息页面
* AUTHOR     ：weishixin
* CREATEDATE ： 2004-03-03
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ include file="/common/taglibs.jsp"%>
<script language='javascript'>
	function afterInsertProposer() {
		setPrpLdriverSerialNo();

		var count = getElementCount("proposerIdentifyNo");
		for ( var i = 0; i < count; i++) {
			if (count != 1) {
				fm.proposerIdentifyNo[i].maxLength = 22;
			}
		}

		setButtonProposerInsertStatus();
	}

	/*
	  删除本条Proposer之後的处理（可选方法）
	 */

	function afterDeleteProposer(field) {
		setPrpLdriverSerialNo();
		setButtonProposerInsertStatus();
	}

	/**
	 * 设置setPrpLdriverSerialNo
	 */

	function setPrpLdriverSerialNo() {
		var count = getElementCount("prpLacciPersonSerialNo");
		for ( var i = 0; i < count; i++) {
			if (count != 1) {
				fm.prpLacciPersonSerialNo[i].value = i;
			}
		}
	}

	//modify by liuyanmei modify 20051119 reason:itest bug 976要求可以输入多条记录
	/**
	 * 只允许有一个驾驶员
	 */
	function setButtonProposerInsertStatus() {
		var count = getElementCount("proposerName");
		if (count <= 1) {
			fm.buttonProposerInsert.disabled = false;
		} else {
			fm.buttonProposerInsert.disabled = true;
		}
	}
</script>
<table class="common" align="center" width="100%" >
             <c:if test="${prpDexch.baseCurrency!=null&&prpDexch.baseCurrency!=LOCAL_CURRENCY}">
              <tr>
                <td class="title" colspan=3 style="color:red"><s:text name="compensate.signSingleCurrencyCase"/>:</td><!-- 此案件签单币别为 -->
                <td colspan=4 class="common">
                 <input type=text name="BaseCurrency2" class="readonly" readonly  style="color:red" value="${prpDexch.baseCurrency }">
                </td>
                <td class="title" colspan=3 style="color:red"><s:text name="compensate.currentExchangeRate"/>:</td><!-- 当前兑换率为 -->
                <td colspan=4 class="common">
                 <input type=text name="ExchRate2" class="readonly" readonly  style="color:red" value="${prpDexch.exchRate }">
                </td>
              </tr>
              </c:if>
       	<c:if test="${coinsFlag!=null&&(coinsFlag=='1'||coinsFlag=='2'||coinsFlag=='3')}">
              <tr>
                <td class="title" colspan=14 style="color:red"><s:text name="compensate.allLine"/></td><!-- ***共保业务，录入损失时请录入总损失；录入费用时请录入我司分摊的费用 -->
              </tr>
              </c:if>
</table>
<!--建立显示的录入条，可以收缩显示的-->
<table class="common" align="center" width="100%"  style="display: none">
   <!--表示显示多行的-->
  <tr>
    <td class="common" ><img style="cursor:hand;" src="${ctx }/images/butCollapseBlue.gif" name="prpLacciPersonImg" onclick="showPage(this,spanClaimProposer)"> <s:text name="compensate.payInformation"/>索赔申请人信息<br>

     <table cellpadding="5" cellspacing="1" class="common"  id="Proposer_Data" style="display:none">
       <tbody>
        <tr>
          <td style="width:5%" class=common>
             <div align="left">
               <input class="readonlyNo" readonly name="prpLacciPersonSerialNo" description="序号">
             </div>
          </td>
          <td  class=common colspan=5 style="width:91%">
            <table  cellpadding="2" cellspacing="1" class="common">
              <tr>
                 <!-----索赔人信息start----------------->
                 <td class="input" style='width:11%'>
                   <!-- mantis： CLM0017，處理人員：Sam，需求單編號：CLM0017，原住名姓名調整作業_車 -->
                   <input name="proposerName" class="input" style="width:70%" maxlength="100" description="索赔申请人姓名"><img src="${ctx }/images/bgMarkMustInput.jpg">  
                 </td>
                 <td class="input" style='width:25%' align="center">
                   <input name="proposerIdentifyNumber" class="input" style="width:70%" maxlength=100 description="索赔申请人身份證字號"> <img src="${ctx }/images/bgMarkMustInput.jpg">
                 </td>
                 <td class="input" style="width:20%" align="center">
                   <select name="relationCode" >
                              <option value="1" ><s:text name="compensate.insuredHimself"/></option><!-- 被保险人本人 -->
		                      <option value="2" ><s:text name="compensate.appointBeneficiary"/></option><!-- 指定受益人 -->
		                      <option value="3" ><s:text name="compensate.insuredHeir"/></option><!-- 被保险人之继承人 -->
		                      <option value="4" ><s:text name="compensate.insuredCare"/></option><!-- 被保险人之监护人 -->
		                      <option value="5" ><s:text name="compensate.principal"/></option><!-- 委托人 -->
		            </select> <img src="${ctx }/images/bgMarkMustInput.jpg">
		        </td>
                <td class="input" style='width:15%' align="center">
                  <input name="proposerPhone" class="common" style="width:95%" maxlength=20 description="联系电话">
                </td>
                <td class="input" style='width:20%' align="center">
                  <input name="proposerAddress" class="common" style="width:95%" maxlength=100 description="通信地址">
                  <input type=hidden name="personFamilyNo" value="${prpLclaim.familyNo }" class="input" />
                  <input type=hidden name="claimNo" value="${prpLclaim.claimNo }" class="input" />
                  <input type=hidden name="proposerPolicyNo" value="${prpLclaim.policyNo }" class="input" />
                  
                </td>
              </tr>
            </table>
          </td>
          <%-----索赔人信息end-
          <!-- <td class="input" style='width:4%'>
          <div align="right">
            <input type=button name="buttonProposerDelete"  class=smallbutton onclick="deleteRow(this,'Proposer')" value="-" style="cursor: hand">
          </div>
          </td> --%>
       </tr>
     </tbody>
  </table>
  <span  id="spanClaimProposer" style="display:none">
  <%-- 多行输入展现域 --%>
    <table class=common id="Proposer" cellpadding="5" cellspacing="1">
            <thead>
              <tr class=listtitle>
                <td  style="width:5%"><s:text name="db.prpDrate.serialNo"/></td><!-- 序号 -->
                <td  style="width:10%"><s:text name="claim.name"/></td><!-- 姓名 -->
                <td  style="width:25%"><s:text name="db.prpDcustomer_Idv.identifyNumber"/></td><!-- 身份证号码 -->
                <td  style="width:20%"><s:text name="compensate.relationshipAccident"/></td><!-- 与事故者关系 -->
                <td  style="width:15%"><s:text name="db.prpLregist.phoneNumber"/></td><!-- 联系电话 -->
                <td  style="width:21%"><s:text name="db.prpDcustomer_Unit.postAddress"/></td><!-- 通信地址 -->
              </tr>
            </thead>
         <tbody>
<c:forEach var="prpLacciPersonTemp" items="${prpLacciPerson.prpLacciPersonList}" varStatus="prpLacciPerson_status">
	<c:if test="${prpLacciPerson_status.index%2==0}">
		<tr class="oddrow">
	</c:if>
	<c:if test="${prpLacciPerson_status.index%2!=0}">
		<tr class="oddrow">
	</c:if>
              <td class="input" style="width:4%">
                <div align="center">
                  <input  name="prpLacciPersonSerialNo"  class="readonlyno" readonly="true" value="${prpLacciPersonTemp.id.serialNo }">
                </div>
              </td>
              <td class="common"  colspan=5>
                <table  cellpadding="5" cellspacing="1" class="common">
                  <tr>
                     <td class="input" style='width:10%'>
                        <!-- mantis： CLM0017，處理人員：Sam，需求單編號：CLM0017，原住名姓名調整作業_車 -->
                        <input name="proposerName"  class="input" style="width:70%" maxlength="100" value="${prpLacciPersonTemp.acciName}" title="索賠人姓名"><img src="${ctx }/images/bgMarkMustInput.jpg">
                     </td>
                     <td class="input" style='width:25%'>
                        <input name="proposerIdentifyNumber"  class="input" style="width:70%" maxlength=20 value="${prpLacciPersonTemp.identifyNumber}" title="身份證字號"><img src="${ctx }/images/bgMarkMustInput.jpg">
                     </td>
                     <td class="input" style="width:20%" >
                      <select name='relationCode'>
	                       <option value="1" <c:if test="${prpLacciPersonTemp.relationCode=='1'}">selected="selected"</c:if>><s:text name="compensate.insuredHimself"/></option><!-- 被保险人本人 -->
	                      <option value="2" <c:if test="${prpLacciPersonTemp.relationCode=='2'}">selected="selected"</c:if>><s:text name="compensate.appointBeneficiary"/></option><!-- 指定受益人 -->
	                      <option value="3" <c:if test="${prpLacciPersonTemp.relationCode=='3'}">selected="selected"</c:if>><s:text name="compensate.insuredHeir"/></option><!-- 被保险人之继承人 -->
	                      <option value="4" <c:if test="${prpLacciPersonTemp.relationCode=='4'}">selected="selected"</c:if>><s:text name="compensate.insuredCare"/></option><!-- 被保险人之监护人 -->
	                      <option value="5" <c:if test="${prpLacciPersonTemp.relationCode=='5'}">selected="selected"</c:if>><s:text name="compensate.principal"/></option><!-- 委托人 -->
                     </select>
                  <input type=hidden name="claimNo" value="${prpLacciPersonTemp.id.certiNo }" class="input" />  
                    </td>
                    <td class="input" style='width:15%'>
                        <input name="proposerPhone"  class="input" style="width:95%" maxlength=20 value="${prpLacciPersonTemp.phone }" title="索賠人電話">
                    </td>
                    <td class="input" style="width:20%">
                        <input name="proposerAddress" class="input"  style="width:80%" maxlength=100 value="${prpLacciPersonTemp.address}" title="索賠人地址">
                    </td>
                  </tr>
                </table> 
              </td>
             </tr>  
             </c:forEach> 
          </tbody>
        </table>
      </td>
    </tr>
  </table>
</span>
</table>

