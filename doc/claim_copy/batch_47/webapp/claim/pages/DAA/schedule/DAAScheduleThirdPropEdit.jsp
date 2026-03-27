<%--
****************************************************************************
* DESC       ：财产损失部位信息页面
* AUTHOR     ：理赔项目组
* CREATEDATE ：2013-03-12
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@page import="com.sinosoft.claim.schema.model.*"%>
<%@page import="com.sinosoft.claim.dto.domain.*"%>
<%@page import="com.sinosoft.claim.dto.custom.*"%>
<%@page import="java.util.*"%>
<%@ include file="/common/taglibs.jsp"%>
<!--建立显示的录入条，可以收缩显示的-->
<script language='javascript'>
	//在下面加入本页自定义的JavaScript方法
	/*
		  插入一条新的之後的处理（可选方法） 
		 */
	
	function afterInsertThirdProp() {
		setPrpLThirdPropSerialNo();
	}
	
	/*
		  删除本条WarnRegion之後的处理（可选方法）
		 */
	
	function afterDeleteThirdProp(field) {
		setPrpLThirdPropSerialNo();
	}
	
	/** 
	 * 设置setPrpLThirdPropSerialNo
	 */
	
	function setPrpLThirdPropSerialNo() {
		var count = getElementCount("prpLthirdPropItemNo");
		for (var i = 0; i < count; i++) {
			if (count != 1) {
				fm.prpLthirdPropItemNo[i].value = i;
				fm.prpLthirdPropNewAddFlag[i].value = "new";
				//是否是新增的财损标志=new,因为已经控制了不能删除原来的.
			}
		}
	}
	
	//以下打开代码页面
	
	function openLossItemCodeWin(PageCode, Field) {
		var index = parseInt(getElementOrder(Field)) - 1;
		var partCode = fm.txtCodeType.value;
		var pageUrl = "/claim/pages/DAA/regist/DAARegistGetCompCode.jsp?strIndex=" + index + "&partCode=" + partCode;
		window
			.open(pageUrl, "openLossItemCodeWin",
				"resizable=0,scrollbars,dependent,alwaysRaised,width=230,height=450");
	}
</script>
<table class="common" align="center" width="100%">
	<!--表示显示多行的-->
	<tr>
		<td class="subformtitle" style="text-align: left" colspan="4">
			<img style="cursor: hand;" src="/claim/images/butCollapseBlue.gif" name="ThirdPropImg" onclick="showPage(this,spanThirdProp)">
			<s:text name="schedule.damagePropertyInfo" />
			<br>
			<%--财产损失信息--%>
			<span style="display: none">
				<table class="common" style="display: none" id="ThirdProp_Data" cellspacing="1" cellpadding="0">
					<tbody>
						<tr>
							<td class="input" style="width: 10%">
								<input type="hidden" name="prpLthirdPropFlag">
								<input name="prpLthirdPropItemNo" class="readonly" readonly style="width: 75%" maxlength=3 value="1">
								<input type="hidden" class="readonlyno" name="prpLthirdPropNewAddFlag">
							</td>
							<td class="input" style="width: 15%; display: none">
								<input name="prpLthirdPropLicenseNo" class="common" style="width: 90%">
							</td>
							<td class="input" style="width: 15%">
								<input name="prpLthirdLossItemName" class="common" style="width: 90%">
								<input type="hidden" name="prpLthirdLossItemCode">
							</td>
							<td class="input" style="width: 21%">
								<input name="prpLthirdPropLossDesc" class="input" style="width: 90%">
							</td>
							<td class="input" style="display: none">
								<s:select name="prpLthirdPropKindCode" list="#request.referKindList" listKey="kindCode" listValue="kindName" headerKey=" " headerValue=" "></s:select>
							</td>
							<td class="input" style="width: 20%">
								<input name="prpLthirdPropLossFee" class="input" style="width: 100%; align: right">
							</td>
							<td class="input" style='width: 4%' align="center">
								<div>
									<input type=button name="buttonThirdPropDelete" onclick="deleteRow(this,'ThirdProp')" value="-" style="cursor: hand">
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</span> <span id="spanThirdProp" style="display: none" cellspacing="1" cellpadding="0"> <%-- 多行输入展现域 --%>
				<table class="common" style="width: 100%" id="ThirdProp">
					<thead>
						<tr>
							<td class="centertitle" style="width: 10%">
								<s:text name="certainLoss.thirdCarLoss.prpLcheckDamageItemNo" />
							</td>
							<%--损失项目序号--%>
							<td class="centertitle" style="width: 15%; display: none">
								<s:text name="certainLoss.thirdCarLoss.prpLthirdCarLicenseNo" />
							</td>
							<%--车牌号--%>
							<td class="centertitle" style="width: 15%">
								<s:text name="certainLoss.thirdCarLoss.lossName" />
							</td>
							<%--损失名称--%>
							<td class="centertitle" style="width: 21%">
								<s:text name="certainLoss.thirdCarLoss.prpLcheckDamageDetail" />
							</td>
							<%--损失程度描述--%>
							<c:set var="strThirdPropRows" value="'4'"></c:set>
							<td class="centertitle" style="width: 10%; display: none">
								<s:text name="regist.prpLregist.kindCode" />
							</td>
							<%--险别代码--%>
							<td class="centertitle" style="width: 20%">
								<s:text name="db.prpLcheckLoss.lossFee" />
							</td>
							<%--损失金额--%>
							<td class="title" style="width: 4%">&nbsp;</td>
						</tr>
					</thead>
					<tfoot>
						<tr>
							<td class="title" colspan=${strThirdPropRows } style="width: 96%"><s:text name="certainLoss.thirdCarLoss.promptLoss" /></td>
							<%--(按"+"号键增加损失部位信息，按"-"号键删除信息)--%>
							<td class="title" align="right" style="width: 4%">
								<div align="center">
									<input type="button" value="+" onclick="insertRow('ThirdProp')" name="buttonDriverInsert" style="cursor: hand">
								</div>
							</td>
						</tr>
					</tfoot>
					</tfoot>
					<tbody id="readonlyThirdPropTable">
						<%
							pageContext.setAttribute("prpLcheckLossNew", new com.sinosoft.claim.schema.model.PrpLcheckLoss());
						%>
						<c:if test="${prpLthirdProp.thirdPropList!=null}">
							<c:set var="intCheckLossIndex" value="0" />
							<c:forEach var="ThirdPropdtox" items="${prpLthirdProp.thirdPropList}">
								<c:set var="prpLcheckLoss2" value="${pageScope.PrpLcheckLossNew}" />
								<c:if test="${not empty requestScope.prpLcheckLoss.prpLcheckLossList}">
									<c:if test="${fn:length(requestScope.prpLcheckLoss.prpLcheckLossList) > intCheckLossIndex}">
										<c:set var="prpLcheckLoss2" value="${requestScope.prpLcheckLoss.prpLcheckLossList[intCheckLossIndex]}" />
										<c:set var="intCheckLossIndex" value="${intCheckLossIndex+1}" />
									</c:if>
								</c:if>
								<tr>
									<input type="hidden" name="prpLthirdPropFlag" value="${ThirdPropdtox.flag}">
									<td class="input" style="width: 10%">
										<input name="prpLthirdPropItemNo" class="readonly" readonly style="width: 75%" maxlength=3 value="${ThirdPropdtox.id.serialNo}">
										<input type="hidden" class="readonlyno" name="prpLthirdPropNewAddFlag" value="old">
									</td>
									<td class="input" style="width: 15%; display: none">
										<input name="prpLthirdPropLicenseNo" class="common" style="width: 90%" value="${ThirdPropdtox.licenseNo}">
									</td>
									<td class="input" style="width: 15%">
										<input name="prpLthirdLossItemName" class="codename" style="width: 90%" value="${ThirdPropdtox.lossItemName}">
										<input type="hidden" name="prpLthirdLossItemCode" value="${ThirdPropdtox.lossItemCode}">
									</td>
									<td class="input" style="width: 21%">
										<input name="prpLthirdPropLossDesc" class="input" style="width: 90%" value="${ThirdPropdtox.lossItemDesc}">
									</td>
									<!--Reason:赔款费用模块合到财产损失信息中-->
									<td class="input" style="display: none">
										<select name="prpLthirdPropKindCode" styleClass="three" style='width: 100px'>
											<c:if test="${fn:length(prpLcheckItemKindList)<1}">
												<option value=" "></option>
											</c:if>
											<c:forEach var="prpCitemKindDto" items="${prpLcheckItemKindList}">
												<option value="${prpCitemKindDto.kindCode}" <c:if test="${prpCitemKindDto.kindCode==prpLcheckLoss2.kindCode}"> selected </c:if>>${prpCitemKindDto.kindName}</option>
											</c:forEach>
										</select>
									</td>
									<td class="input" style="width: 20%">
										<input name="prpLthirdPropLossFee" class="input" style="width: 100%; align: right" <fmt:formatNumber value="${prpLcheckLoss2.lossFee}" pattern="#"/>>
									</td>
									<td class="input" style='width: 4%' align="center">
										<div>
											<input type=button name="buttonThirdPropDelete" onclick="deleteRow(this,'ThirdProp')" value="-" disabled style="cursor: hand">
										</div>
									</td>
								</tr>
							</c:forEach>
						</c:if>
					</tbody>
					<!--其它损失代码定为5-->
					<input type="hidden" name="txtCodeType" value="5">
				</table>
			</span>
		</td>
	</tr>
</table>
