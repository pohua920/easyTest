<%--
****************************************************************************
* DESC       ：添加费用赔款信息页面
* AUTHOR     ：中科软
* CREATEDATE ： 2013-02-20
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<!--建立显示的录入条，可以收缩显示的-->
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<table class="common" align="center">
	<!--表示显示多行的-->
	<tr>
		<td class="common" colspan="4" style="text-align: left">
			<img style="cursor: hand;" src="${ctx }/images/butCollapseBlue.gif" name="CarInsuranceImg" onclick="showPage(this,spanCarInsurance);">
			<b>車體險訊息</b>
			<br>
			<%-- 费用赔款信息 --%>
			<span style="display: none">
				<table class="common" cellspacing="1" cellpadding="0" id="CarInsurance_Data">
					<tbody>
						<tr name="CarInsuranceObject">
							<td class="input" style="width: 5%">
								<input type="text" name="prpLcarInsuranceSerialNo" class="readonly" readonly="readonly" value="0"/>
							</td>
							<td class="input" style="width: 12%">
								<select name="prpLcarInsuranceWrittenEstimate" style="width:100%;">
									<option value="N" >N-否</option>
									<option value="Y" selected="selected" >Y-是</option>
								</select>
							</td>
							<!-- #083 第三次修改 需求变更 -->
							<td class="input" style="width: 10%">
								<select name="prpLcarInsuranceCertificateType" style="width:100%;">
									<option value="1" selected="selected">發票</option>
									<option value="0" >非發票</option>
								</select>
							</td>
							<td class="input" style="width: 10%"><input type="text" name="prpLcarInsuranceDeductibleInvoice" class="input" maxlength="20" /></td>
							<!-- #083 第三次修改 需求变更
							<td class="input" style="width: 10%"><input type="text" name="prpLcarInsuranceUniformNo" class="input"/></td>
							 -->
							 
							<td class="input" style="width: 30%">
								<select name="prpLcarInsuranceCollisionCount" style="width:100%;">
									<c:forEach items="${collisionCountList}" var="collisionCount">
										<option value="${collisionCount.key }" title="${collisionCount.value }">${collisionCount.value }</option>
									</c:forEach>
								</select>
							</td>
							<td class="input" style="width: 10%"><input type="text" name="prpLcarInsuranceRepairUniformNo" class="input"/></td>
							<td class="input" style="width: 18%">
								<input type="text" name="prpLcarInsuranceHandlerCode" 
									ondblclick="code_CodeSelect(this, 'handerCode','0,1','Y','Y','RISKCODE|'+fm.riskcode.value);" 
									onkeyup="code_CodeSelect(this, 'handerCode','0,1','Y','Y','RISKCODE|'+fm.riskcode.value);" 
									onchange="code_CodeSelect(this, 'handerCode','0,1','Y','Y','RISKCODE|'+fm.riskcode.value);" 
									class="codecode" style="width: 30%;" value="${sessionScope.user.userCode}"/>
								<input type="text" name="prpLcarInsuranceHandlerName" 
									ondblclick="code_CodeSelect(this, 'handerCode','-1,0','Y','N','RISKCODE|'+fm.riskcode.value);" 
									onkeyup="code_CodeSelect(this, 'handerCode','-1,0','Y','N','RISKCODE|'+fm.riskcode.value);" 
									onchange="code_CodeSelect(this, 'handerCode','-1,0','Y','N','RISKCODE|'+fm.riskcode.value);" 
									class="codename" style="width: 65%;" value="${sessionScope.user.userName}"/>
							</td>
							<td class="input" style="width: 5%">
								<div align="center">
									<input type=button name="CarInsuranceDelete" class="smallbutton" onclick="deleteRow(this,'CarInsurance','prpLcarInsuranceSerialNo');disCarInsuranceInsert();" value="-" style="cursor: hand">
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</span>
			<span id="spanCarInsurance" style="display: none" cellspacing="1" cellpadding="0"> <%-- 多行输入展现域 --%>
				<table class="common" cellpadding="5" cellspacing="1" id="CarInsurance">
					<thead>
						<tr>
							<td class="centertitle" style="width: 5%">
								序號
							</td>
							<td class="centertitle" style="width: 12%">
								車體險估價單有無當事人簽屬
							</td>
							<!-- #083 第三次修改 需求变更 增加憑證類型 -->
							<td class="centertitle" style="width: 10%">憑證類型</td>
							<td class="centertitle" style="width: 10%">自負額發票號</td>
							<!-- #083 第三次修改 需求变更
							<td class="centertitle" style="width: 10%">開立者統編</td>
							 -->
							<td class="centertitle" style="width: 30%">
								單一車輛自行碰撞事故統計代碼
							</td>
							<td class="centertitle" style="width: 10%">修理廠統一編號/負責人身份證字號</td>
							<td class="centertitle" style="width: 18%">理賠經辦人員</td>
							<td class="centertitle" style="width: 5%">
								<s:text name="certify.operate" /><%-- 操作 --%>
							</td>
						</tr>
					</thead>
					<tfoot>
						<tr id="inserttr" <c:if test="${not empty prpLcarInsurance.prpLcarInsuranceList}">style="display: none"</c:if> >
							<td class="title" colspan="7" >
								(按"+"號鍵增加車體險訊息，按"-"號鍵刪除訊息) 
							</td>
							<%-- (按"+"号键增加费用赔款信息，按"-"号键删除信息) --%>
							<td class="title" align="right" style="width: 5%">
								<div align="center">
									<input type="button" value="+" class=smallbutton onclick="insertRow('CarInsurance',this,'prpLcarInsuranceSerialNo');disCarInsuranceInsert();" name="CarInsuranceInsert" style="cursor: hand">
								</div>
							</td>
						</tr>
					</tfoot>
					<tbody>
						<c:forEach items="${prpLcarInsurance.prpLcarInsuranceList}" var="prpLcarInsuranceTemp">
							<tr name="CarInsuranceObject">
								<td class="input" style="width: 5%">
									<input type="text" name="prpLcarInsuranceSerialNo" value="${prpLcarInsuranceTemp.id.serialNo}" class="readonly" readonly="readonly"/>
									<input type="hidden" name="prpLcarInsuranceCompensateNo" value="${prpLcarInsuranceTemp.id.compensateNo}">
								</td>
								<td class="input" style="width: 12%">
									<select name="prpLcarInsuranceWrittenEstimate" style="width:100%;">
										<option value="N" <c:if test="${prpLcarInsuranceTemp.writtenEstimate == 'N'}">selected="selected"</c:if> >N-否</option>
										<option value="Y" <c:if test="${prpLcarInsuranceTemp.writtenEstimate == 'Y'}">selected="selected"</c:if> >Y-是</option>
									</select>
								</td>
								<!-- #083 第三次修改 需求变更 -->
								<td class="input" style="width: 10%">
									<select name="prpLcarInsuranceCertificateType" style="width:100%;">
										<option value="1" <c:if test="${prpLcarInsuranceTemp.certificateType == '1'}">selected="selected"</c:if> >發票</option>
										<option value="0" <c:if test="${prpLcarInsuranceTemp.certificateType == '0'}">selected="selected"</c:if> >非發票</option>
									</select>
								</td>
								<td class="input" style="width: 10%"><input type="text" name="prpLcarInsuranceDeductibleInvoice" value="${prpLcarInsuranceTemp.deductibleInvoice }" class="input" maxlength="20"/></td>
								<!-- #083 第三次修改 需求变更
								<td class="input" style="width: 10%"><input type="text" name="prpLcarInsuranceUniformNo" value="" class="input"/></td>
								 -->
								 <td class="input" style="width: 30%">
									<select name="prpLcarInsuranceCollisionCount" style="width:100%;">
										<c:forEach items="${collisionCountList}" var="collisionCount">
											<option value="${collisionCount.key }" title="${collisionCount.value }" <c:if test="${prpLcarInsuranceTemp.collisionCount==collisionCount.key }">selected</c:if> >${collisionCount.value }</option>
										</c:forEach>
									</select>
								</td>
								<td class="input" style="width: 10%"><input type="text" name="prpLcarInsuranceRepairUniformNo" value="${prpLcarInsuranceTemp.repairUniformNo }" class="input"/></td>
								<td class="input" style="width: 18%">
									<input type="text" name="prpLcarInsuranceHandlerCode" value="${prpLcarInsuranceTemp.handlerCode }" 
										ondblclick="code_CodeSelect(this, 'handerCode','0,1','Y','Y','RISKCODE|'+fm.riskcode.value);" 
										onkeyup="code_CodeSelect(this, 'handerCode','0,1','Y','Y','RISKCODE|'+fm.riskcode.value);" 
										onchange="code_CodeSelect(this, 'handerCode','0,1','Y','Y','RISKCODE|'+fm.riskcode.value);" 
										class="codecode" style="width: 30%;"/>
									<input type="text" name="prpLcarInsuranceHandlerName" value="${prpLcarInsuranceTemp.handlerName }" 
										ondblclick="code_CodeSelect(this, 'handerCode','-1,0','Y','N','RISKCODE|'+fm.riskcode.value);" 
										onkeyup="code_CodeSelect(this, 'handerCode','-1,0','Y','N','RISKCODE|'+fm.riskcode.value);" 
										onchange="code_CodeSelect(this, 'handerCode','-1,0','Y','N','RISKCODE|'+fm.riskcode.value);" 
										class="codename" style="width: 65%;"/>
								</td>
								<td class="input" style="width: 5%">
									<div align="center">
										<input type=button name="CarInsuranceDelete" class="smallbutton" onclick="deleteRow(this,'CarInsurance','prpLcarInsuranceSerialNo');disCarInsuranceInsert();" value="-" style="cursor: hand">
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
<script type="text/javascript">
	function disCarInsuranceInsert(){
		if($("#CarInsurance").find("tr[name='CarInsuranceObject']").length > 0){
			$("#inserttr").hide();
		} else {
			$("#inserttr").show();
		}
	}
</script>
<script type="text/javascript">
//	mantis：CLM0074 ，處理人員：BK007 蘇哲，需求單編號：CLM0074.理賠系統-車體險訊息[自負額發票] -start
// 	var $prpLcarInsuranceCompensateNo = $("#CarInsurance").find(":input[name='prpLcarInsuranceCompensateNo']");
// 	if($prpLcarInsuranceCompensateNo.length > 0 ){
// 		var compensateNo = $(":input[name='prpLcompensateCompensateNo']").val();
// 		var carInsuranceCompensateNo = $prpLcarInsuranceCompensateNo.first().val();
// 		//一筆賠案只需錄一筆車體險訊息
// 		if( compensateNo != carInsuranceCompensateNo ){//
// 			$("#CarInsurance").find(":input:enabled").prop("disabled",true);
// 		}
// 	}
//	mantis：CLM0074 ，處理人員：BK007 蘇哲，需求單編號：CLM0074.理賠系統-車體險訊息[自負額發票] -end
</script>
<script type="text/javascript">
	$(function(){
		$(":input[name='prpLcarInsuranceCollisionCount'],:input[name='prpLpersonLossProsecutorsOffice']").live("mouseover",function(){
			$(this).prop("title",$(this).children(":selected").text());
		});
	})
</script>