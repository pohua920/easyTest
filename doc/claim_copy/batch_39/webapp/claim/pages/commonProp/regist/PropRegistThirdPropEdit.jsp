<%--
****************************************************************************
* DESC       ：财产损失部位信息页面
* AUTHOR     ：中科软
* MODIFYLIST ：Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
   <!--建立显示的录入条，可以收缩显示的-->
   <%@ include file="/common/taglibs.jsp"%>
    <script language='javascript'>
  //在下面加入本页自定义的JavaScript方法
    function afterInsertThirdProp(ThirdPropObject){
 	   $(ThirdPropObject).find(":input[name='prpLthirdPropNewAddFlag']").val("new");
     }
    //以下打开代码页面
    function openLossItemCodeWin(PageCode, Field) {
    	var index = parseInt(getElementOrder(Field)) - 1;
    	var partCode = fm.txtCodeType.value;
    	var pageUrl = "/claim/pages/DAA/regist/DAARegistGetCompCode.jsp?strIndex=" + index + "&partCode=" + partCode;
    	window.open(pageUrl, "openLossItemCodeWin", "resizable=0,scrollbars,dependent,alwaysRaised,width=230,height=450");
    }
    </script>
<table class="common" cellspacing="1" cellpadding="4">
	<!--表示显示多行的-->
	<tr>
		<td class="common" colspan="4" style="text-align: left;">
			<s:text name="schedule.damagePropertyInfo" />
			<%-- 財產損失訊息 --%>
			<br>
			<table class="common" style="display: none" id="PROPERTYLOSS_Data" cellspacing="1" cellpadding="5">
				<tbody>
					<tr>
						<td class="input" style="width: 10%">
							<input type="hidden" name="prpLthirdPropFlag">
							<input name="prpLthirdPropItemNo" class="readonly" readonly style="width: 75%" maxlength=3 value="1">
							<input type="hidden" class="readonlyno" name="prpLthirdPropNewAddFlag">
							<!--是否新增标志 -->
							<input type="hidden" name="prpLthirdPropLicenseNo" class="common" maxlength=20 style="width: 90%">
						</td>
						<td class="input" style="width: 35%">
							<input name="prpLthirdLossItemName" class="input" style="width: 95%">
							<input type="hidden" name="prpLthirdLossItemCode">
						</td>
						<td class="input" style="width: 50%">
							<textarea name="prpLthirdPropLossDesc" class="input" style="width: 95%" rows="2" ></textarea>
						</td>
						<c:if test="${prpLnodeType=='check'}">
							<td class="input" style="width: 10%;display: none">
								<%--headerKey不要去掉那个空格，去掉空格后，会后台报空指针异常 --%>
								<s:select style="width:100%;" name="prpLthirdPropKindCode" list="#request.prpLcheckPropItemKindList" listKey="kindCode" listValue="kindName" headerKey=" " headerValue=""></s:select>
							</td>
							<td class="input" style="width: 10%;display: none">
								<input name="prpLthirdPropLossFee" class="input" style="width: 100%; align: right">
							</td>
						</c:if>
						<td class="input" style='width: 5%' colspan="2" align="center">
							<div>
								<input type=button name="buttonThirdPropDelete" class=smallbutton onclick="deleteRow(this,'PROPERTYLOSS','prpLthirdPropItemNo')" value="-" style="cursor: hand">
							</div>
						</td>
					</tr>
				</tbody>
			</table>
			<%-- 多行输入展现域 --%>
			<table class="common" id="PROPERTYLOSS" cellspacing="1" cellpadding="4">
				<thead>
					<tr>
						<td class="centertitle" style="width: 10%">
							<s:text name="certainLoss.thirdCarLoss.prpLcheckDamageItemNo" />
						</td>
						<%-- 损失项目序号 --%>
						<td class="centertitle" style="width: 35%">
							<s:text name="certainLoss.thirdCarLoss.lossName" />
						</td>
						<%-- 损失名称 --%>
						<td class="centertitle" style="width: 50%">
							<s:text name="certainLoss.thirdCarLoss.prpLcheckDamageDetail" />
						</td>
						<%-- 损失程度描述 --%>
						<c:set var="strThirdPropRows" value="4" />
						<td class="centertitle" style="width: 5%">操作</td>
					</tr>
				</thead>
				<tfoot>
					<tr>
						<td class="title" colspan="${strThirdPropRows-1}">
							<s:text name="certainLoss.thirdCarLoss.promptLoss" />
							<input type="hidden" name="thirdPropCheck" value="0">
							<input type="hidden" name="selectSend" value="0">
							<input type="hidden" name="prpLthirdPropSelectSend" value="${prpLthirdProp.selectSend}">
						</td>
						<td class="title" align="right" style="width: 2%">
							<div align="center">
								<input type="button" class=smallbutton value="+" class=smallbutton onclick="insertRow('PROPERTYLOSS',this,'prpLthirdPropItemNo')" name="buttonThirdPropInsert" style="cursor: hand">
							</div>
						</td>
					</tr>
				</tfoot>
				<tbody>
					<c:if test="${not empty requestScope.prpLthirdProp.thirdPropList}">
						<c:forEach var="ThirdPropdtox" items="${requestScope.prpLthirdProp.thirdPropList}" varStatus="status">
							<tr>
								<td class="input" style="width: 10%">
									<input type="hidden" name="prpLthirdPropFlag" value="<c:out value='${ThirdPropdtox.flag}'/>">
									<input name="prpLthirdPropItemNo" class="readonly" readonly style="width: 75%" maxlength=3 value="<c:out value='${ThirdPropdtox.id.serialNo}'/>">
									<input type="hidden" class="readonlyno" name="prpLthirdPropNewAddFlag" value="old">
									<input type="hidden" name="prpLthirdPropLicenseNo" class="common" style="width: 90%" maxlength="1" value="<c:out value='${ThirdPropdtox.licenseNo}'/>">
								</td>
								<td class="input" style="width: 35%">
									<input name="prpLthirdLossItemName" class="input" style="width: 95%" value="<c:out value='${ThirdPropdtox.lossItemName}'/>">
									<input type="hidden" name="prpLthirdLossItemCode" value="<c:out value='${ThirdPropdtox.lossItemCode}'/>">
								</td>
								<td class="input"  style="width: 50%">
									<textarea name="prpLthirdPropLossDesc" class="input" style="width: 95%" rows="2" ><c:out value='${ThirdPropdtox.lossItemDesc}'/></textarea>
								</td>
								<!--Reason:赔款费用模块合到财产损失信息中-->
								<c:if test="${prpLnodeType=='check'}">
									<td class="input" style="width: 30%;display: none">
										<s:select style="width:100%;" name="prpLthirdPropKindCode" list="#request.prpLcheckPropItemKindList" listKey="kindCode" listValue="kindName"
											value="#request.prpLpropList.get(#attr.status.index).kindCode" headerKey=" " headerValue=""></s:select>
									</td>
									<td class="input" style="width: 20%;display: none">
										<input name="prpLthirdPropLossFee" class="input" style="width: 100%; align: right" value="<fmt:formatNumber value="${requestScope.prpLpropList[status.index].sumLoss}" pattern="#"/>">
									</td>
								</c:if>
								<td class="input" style='width: 5%' align="center">
									<div>
										<input type=button name="buttonThirdPropDelete" class=smallbutton onclick="deleteRow(this,'PROPERTYLOSS','prpLthirdPropItemNo')" value="-" style="cursor: hand">
									</div>
								</td>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
				<!--其它损失代码定为5-->
				<input type="hidden" name="txtCodeType" value="5">
			</table>
		</td>
	</tr>
</table>