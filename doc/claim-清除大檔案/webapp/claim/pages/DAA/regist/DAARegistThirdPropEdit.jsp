<%--
****************************************************************************
* DESC       ：财产损失部位信息页面
* AUTHOR     ：理赔项目组
* CREATEDATE ：2013-03-04
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
   <!--建立显示的录入条，可以收缩显示的-->
   <%@ include file="/common/taglibs.jsp"%>
    <script language='javascript'>
  //在下面加入本页自定义的JavaScript方法
    /*
            插入一条新的之後的处理（可选方法）
          */
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
<table class="common" cellspacing="1" cellpadding="5">
	<!--表示显示多行的-->
	<tr>
		<td>
			<table class="common" style="display: none" id="ThirdProp_Data" cellspacing="1" cellpadding="5">
				<tbody>
					<tr>
						<td class="input" style="width: 12%">
							<input type="hidden" name="prpLthirdPropFlag">
							<input name="prpLthirdPropItemNo" class="readonly" readonly style="width: 75%" maxlength=3 value="1">
							<input type="hidden" class="readonlyno" name="prpLthirdPropNewAddFlag">
							<!--是否新增标志 -->
						</td>
						<input type="hidden" name="prpLthirdPropLicenseNo" class="common" maxlength=20 style="width: 90%">
						<td class="input" style="width: 10%">
							<input name="prpLthirdLossItemName" class="input" style="width: 90%">
							<input type="hidden" name="prpLthirdLossItemCode">
						</td>
						<td class="input" style="width: 22%">
							<input name="prpLthirdPropLossDesc" class="input" style="width: 90%">
						</td>
						<!--Reason:赔款费用模块合到财产损失信息中-->
						<c:if test="${prpLnodeType=='check'}">
							<%
                 String prpLnodeType = (String)request.getAttribute("prpLnodeType");
                 %>
							<td class="input" style="width: 10%">
								<%--headerKey不要去掉那个空格，去掉空格后，会后台报空指针异常 --%>
								<s:select style="width:100%;" name="prpLthirdPropKindCode" list="#request.prpLcheckPropItemKindList" listKey="kindCode" listValue="kindName" headerKey=" " headerValue=""></s:select>
							</td>
							<td class="input" style="width: 10%">
								<input name="prpLthirdPropLossFee" class="input" style="width: 100%; align: right">
							</td>
						</c:if>
						<td class="input" style='width: 4%' colspan="2" align="center">
							<div>
								<input type=button name="buttonThirdPropDelete" class=smallbutton onclick="deleteRow(this,'ThirdProp','prpLthirdPropItemNo')" value="-" style="cursor: hand">
							</div>
						</td>
					</tr>
				</tbody>
			</table>
			<%-- 多行输入展现域 --%>
			<table class="common" id="ThirdProp" cellspacing="1" cellpadding="5">
				<thead>
					<tr>
						<td class="centertitle" style="width: 12%">
							<s:text name="certainLoss.thirdCarLoss.prpLcheckDamageItemNo" />
						</td>
						<%-- 损失项目序号 --%>
						<td class="centertitle" style="width: 10%">
							<s:text name="certainLoss.thirdCarLoss.lossName" />
						</td>
						<%-- 损失名称 --%>
						<td class="centertitle" style="width: 22%">
							<s:text name="certainLoss.thirdCarLoss.prpLcheckDamageDetail" />
						</td>
						<%-- 损失程度描述 --%>
						<!--Reason:赔款费用模块合到财产损失信息中-->
						<c:set var="strThirdPropRows" value="4" />
						<c:if test="${prpLnodeType=='check'}">
							<td class="centertitle" style="width: 10%">
								<s:text name="db.prpDkind.kindCode" />
							</td>
							<%-- 险别代码 --%>
							<td class="centertitle" style="width: 20%">
								<s:text name="certainLoss.thirdCarLoss.LossFee" />
							</td>
							<%-- 损失金额 --%>
							<c:set var="strThirdPropRows" value="6" />
						</c:if>
						<td class="centertitle" style="width: 4%">操作</td>
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
								<input type="button" class=smallbutton value="+" class=smallbutton onclick="insertRowTableNew('ThirdProp','ThirdProp_Data',this,'prpLthirdPropItemNo')" name="buttonThirdPropInsert" style="cursor: hand">
							</div>
						</td>
					</tr>
				</tfoot>
				<tbody>
					<c:if test="${not empty requestScope.prpLthirdProp.thirdPropList}">
						<c:forEach var="ThirdPropdtox" items="${requestScope.prpLthirdProp.thirdPropList}" varStatus="status">
							<tr>
								<input type="hidden" name="prpLthirdPropFlag" value="<c:out value='${ThirdPropdtox.flag}'/>">
								<td class="input" style="width: 5%">
									<input name="prpLthirdPropItemNo" class="readonly" readonly style="width: 75%" maxlength=3 value="<c:out value='${ThirdPropdtox.id.serialNo}'/>">
									<input type="hidden" class="readonlyno" name="prpLthirdPropNewAddFlag" value="old">
								</td>
								<input type="hidden" name="prpLthirdPropLicenseNo" class="common" style="width: 90%" maxlength="1" value="<c:out value='${ThirdPropdtox.licenseNo}'/>">
								<td class="input" style="width: 10%">
									<input name="prpLthirdLossItemName" class="input" style="width: 90%" value="<c:out value='${ThirdPropdtox.lossItemName}'/>">
									<input type="hidden" name="prpLthirdLossItemCode" value="<c:out value='${ThirdPropdtox.lossItemCode}'/>">
								</td>
								<td class="input" style="width: 10%">
									<input name="prpLthirdPropLossDesc" class="input" style="width: 90%" value="<c:out value='${ThirdPropdtox.lossItemDesc}'/>">
								</td>
								<!--Reason:赔款费用模块合到财产损失信息中-->
								<c:if test="${prpLnodeType=='check'}">
									<td class="input" style="width: 30%">
										<s:select style="width:100%;" name="prpLthirdPropKindCode" list="#request.prpLcheckPropItemKindList" listKey="kindCode" listValue="kindName"
											value="#request.prpLpropList.get(#attr.status.index).kindCode" headerKey=" " headerValue=""></s:select>
									</td>
									<td class="input" style="width: 20%">
										<input name="prpLthirdPropLossFee" class="input" style="width: 100%; align: right" value="<fmt:formatNumber value="${requestScope.prpLpropList[status.index].sumLoss}" pattern="#"/>">
									</td>
								</c:if>
								<td class="input" style='width: 2%' align="center">
									<div>
										<input type=button name="buttonThirdPropDelete" class=smallbutton onclick="deleteRow(this,'ThirdProp','prpLthirdPropItemNo')" value="-" style="cursor: hand">
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