<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="cn.com.sinosoft.ims.sync.SyncConstants"%>
<%
	String deployCom = (String)session.getAttribute("deployCom");
%>
<%@ include file="/common/taglibs.jsp"%>
<%@include file="/common/meta_css.jsp"%>
<%@ include file="/common/i18njs.jsp"%>
<%@ include file="/common/meta_js.jsp"%>
<%-- moidfy  update by tongziliang 2011-10-09 reason:修改页面的按钮样式和优化页面样式 --%>
<link rel="stylesheet" href="${ctx}/style/popup/login.css" type="text/css"></link>
<html>
	<head>
		<title>申请代码</title>
	</head>
	<!--  onkeydown方法禁用BackSpace onload方法光标定位在首个输入框 -->
	<body id="all_title" onkeydown="keyDown()" onload="fm.comCode.focus()">	
		<div id="wrapper">
			<div id="container">
				<s:form name="fm" action="${ctx}/dictionary/updatePrpDcompanyTrace.do" method="post">
					<s:hidden name="editType" id="editType" value="${editType}"></s:hidden>
					<s:hidden name="deployCom" id="deployCom" value="${deployCom}"></s:hidden>
					<s:hidden name="prpDcompanyTrace.currentStatus" value="${prpDcompanyTrace.currentStatus}"></s:hidden>
					<s:hidden name="prpDcompanyTrace.applicantType" value="${prpDcompanyTrace.applicantType}"></s:hidden>
					<table width="100%" class="fix_table">
						<tr class="top">
							<s:if test="${editType=='update' }">
								<div id="crash_menu">
									<h2 align="center">
										修改申请代码
									</h2>
								</div>
							</s:if>
							<s:if test="${editType=='view' }">
								<div id="crash_menu">
									<h2 align="center">
										查看申请代码
									</h2>
								</div>
							</s:if>
						</tr>
						<s:if test="${editType=='update' }">
							<tr>
								<td class="bgc_tt short">
									机构代码
									<font color="red">*</font>
								</td>
								<td class="long">
									<s:textfield name="prpDcompanyTrace.comCode" id="comCode"
										cssClass='input_w w_30 dc-chk dt-nzhs' readonly='true'/>
								</td>
								<td class="bgc_tt short">
									机构名称
									<font color="red">*</font>
								</td>
								<td class="long">
									<s:textfield name="prpDcompanyTrace.comCName" id="comCName"
										cssClass="input_w w_30 dc-chk " />
								</td>
							</tr>
							<tr>
								<td class="bgc_tt short">
									机构英文名
								</td>
								<td class="long">
									<s:textfield name="prpDcompanyTrace.comEName" id="comEName"
										cssClass='input_w w_30' maxlength="80" />
								</td>
								<td class="bgc_tt short">
									机构地址								
								</td>
								<td class="long">
									<s:textfield name="prpDcompanyTrace.addressCName"
										id="addressCName" cssClass='input_w w_30'
										maxlength=""  />
								</td>
							</tr>
							<tr>
								<td class="bgc_tt short">
									英文地址
								</td>
								<td class="long">
									<s:textfield name="prpDcompanyTrace.addressEName"
										id="addressEName" cssClass='input_w w_30' maxlength="80"
										/>
								</td>
								<td class="bgc_tt short">
									邮编
								</td>
								<td class="long">
									<s:textfield name="prpDcompanyTrace.postCode" id="postCode"
										cssClass='input_w w_30 dt-num' maxlength="6"  />
								</td>
							</tr>
							<tr>
								<td class="bgc_tt short">
									电话
								</td>
								<td class="long">
									<s:textfield name="prpDcompanyTrace.phoneNumber"
										id="phoneNumber" cssClass='input_w w_30' maxlength="20"
										 />
								</td>
								<td class="bgc_tt short">
									传真
								</td>
								<td class="long">
									<s:textfield name="prpDcompanyTrace.faxNumber" id="faxNumber"
										cssClass='input_w w_30' maxlength="20"  />
								</td>
							</tr>
							<tr>
								<td class="bgc_tt short">
									上级机构代码
								</td>
								<td class="long">
									<s:textfield name="prpDcompanyTrace.upperComCode"
										id="uppercomcode" cssClass='input_w w_30' maxlength="20"
										readonly="true" />
								</td>
								<td class="bgc_tt short">
									归属保险公司名称
								</td>
								<td class="long">
									<s:textfield name="prpDcompanyTrace.insurerName"
										id="insurerName" cssClass='input_w w_30' maxlength="40"
										 />
								</td>
							</tr>
							<tr>
								<td class="bgc_tt short">
									机构类型
								</td>
								<td class="long">
									<s:select name="prpDcompanyTrace.comType" id="comType"
										list="#@java.util.HashMap@{'0':'出单','1':'归属','2':'收付'}" />
								</td>
								<td class="bgc_tt short">
									经理
								</td>
								<td class="long">
									<s:textfield name="prpDcompanyTrace.manager" id="manager"
										cssClass='input_w w_30' maxlength="20" />
								</td>
							</tr>
							<tr>
								<td class="bgc_tt short">
									会计
								</td>
								<td class="long">
									<s:textfield name="prpDcompanyTrace.accountant" id="accountant"
										cssClass='input_w w_30' maxlength="20"  />
								</td>
								<td class="bgc_tt short">
									最新机构代码
								</td>
								<td class="long">
									<s:textfield name="prpDcompanyTrace.newComCode" id="newComCode"
										cssClass='input_w w_30' maxlength="20"  />
								</td>
							</tr>
							<tr>
								<td class="bgc_tt short">
									帐户归属机构代码
								</td>
								<td class="long">
									<s:textfield name="prpDcompanyTrace.acntUnit" id="acntUnit"
										cssClass='input_w w_30' maxlength="20" />
								</td>
								<td class="bgc_tt short">
									专项代码
								</td>
								<td class="long">
									<s:textfield name="prpDcompanyTrace.articleCode"
										id="articleCode" cssClass='input_w w_30' maxlength="20"
										 />
								</td>
							</tr>
							<tr>
								<td class="bgc_tt short">
									有效标志
								</td>
								<td class="long"">
									<ct:select name="prpDcompanyTrace.validStatus"
										value="${prpDcompanyTrace.validStatus}" sysCode="IMS"
										codeType="ValidStatus"></ct:select>
								</td>
								<td class="bgc_tt short">
									机构性质
								</td>
								<td class="long">
									<s:textfield name="prpDcompanyTrace.comFlag" id="comFlag"
										cssClass='input_w w_30' maxlength="2"  />
								</td>
							</tr>
							<tr>
								<td class="bgc_tt short">
									标志字段第一位
								</td>
								<td class="long">
									<s:select name="flag1" id="flag1"
										list="#@java.util.HashMap@{' ':'其它','1':'清算中心的机构代码','9':'北分金锁单证接口'}" />
								</td>
								<td class="bgc_tt short"
									title="如果为9: 传入收付费的ksdm=ComCode 其它：ksdm = Makecom ；如果为 8 保费分摊判断公司代码等于8取comcode[1,8]，其他取comcode[1,6]">
									标志字段第二位
								</td>
								<td class="long">
									<s:textfield name="flag2" id="flag2" cssClass='input_w w_30'
										maxlength="1"/>
								</td>
							</tr>
							<tr>
								<td class="bgc_tt short" title="标志字段第三位">
									是否分公司
								</td>
								<td class="long">
									<s:select name="prpDcompanyTrace.branchType" id="branchType"
										list="#@java.util.HashMap@{'2':' 区县','3':'科室','4':'地市公司','5':'省公司','7':'直辖市/计划单列市','8':'网点'}" />
								</td>
								<td class="bgc_tt short" title="标志字段第四位">
									是否基层核算单位
								</td>
								<td class="long">
									<s:select name="prpDcompanyTrace.centerFlag" id="centerFlag"
										list="#@java.util.HashMap@{'0':' 否','1':'是'}" />
								</td>
							</tr>
							<tr>
								<td class="bgc_tt short">
									序号
									<font color="red">*</font>
								</td>
								<td class="long">
									<s:textfield name="prpDcompanyTrace.serialNo" id="serialNo"
										cssClass='input_w w_30 dc-chk dt-nzhs' readonly="true"
										maxlength="" />
								</td>
								<td class="bgc_tt short">
									申请人
									<font color="red">*</font>
								</td>
								<td class="long">
									<s:textfield name="prpDcompanyTrace.applicantMen"
										id="applicantMen" cssClass='input_w w_30 dc-chk dt-nzhs'
										 maxlength="" readonly="true"/>
								</td>
							</tr>
							<tr>
								<td class="bgc_tt short">
									审核状态
									<font color="red">*</font>
								</td>
								<td class="long">
								<c:if test="${prpDcompanyTrace.currentStatus==0}">
									<s:textfield value="初始化"
										id="currentStatus" readonly="true"
										cssClass='input_w w_30 dc-chk' maxlength="" />
								</c:if>
								<c:if test="${prpDcompanyTrace.currentStatus==1}">
									<s:textfield value="待审核"
										id="currentStatus" readonly="true"
										cssClass='input_w w_30 dc-chk' maxlength="" />
								</c:if>
								<c:if test="${prpDcompanyTrace.currentStatus==8}">
									<s:textfield value="审核通过"
										id="currentStatus" readonly="true"
										cssClass='input_w w_30 dc-chk' maxlength="" />
								</c:if>
								<c:if test="${prpDcompanyTrace.currentStatus==9}">
									<s:textfield value="审核未通过"
										id="currentStatus" readonly="true"
										cssClass='input_w w_30 dc-chk' maxlength="" />
								</c:if>
								</td>
								<td class="bgc_tt short">
									申请类型
									<font color="red">*</font>
								</td>
								<td class="long">
								<c:if test="${prpDcompanyTrace.applicantType==1}">
									<s:textfield value="新增" readonly="true"
										id="applicantType"
										cssClass='input_w w_30 dc-chk' maxlength=""
										/>
								</c:if>
								<c:if test="${prpDcompanyTrace.applicantType==2}">
									<s:textfield value="修改" readonly="true"
										id="applicantType"
										cssClass='input_w w_30 dc-chk' maxlength=""
										/>
								</c:if>
								<c:if test="${prpDcompanyTrace.applicantType==3}">
									<s:textfield value="注销/启用" readonly="true"
										id="applicantType"
										cssClass='input_w w_30 dc-chk' maxlength=""
										/>
								</c:if>
								</td>
							</tr>
							<tr>
								<td class="bgc_tt short">
									申请时间
								</td>
								<td class="long">
									<s:textfield name="prpDcompanyTrace.applicantDate"
										id="applicantDate" cssClass="input_w w_30 "
										value="${prpDcompanyTrace.applicantDate}" readonly="true"/>
								</td>
								<td class="bgc_tt short">
									最后修改时间							
								</td>
								<td class="long">
									<s:textfield name="prpDcompanyTrace.updateDate" id="updateDate"
										cssClass="input_w w_30 " 
										value="${prpDcompanyTrace.updateDate}" readonly="true"/>
								</td>
							</tr>
							<tr>
								<td class="bgc_tt short">申请描述<font color="red">*</font></td>
								<td class="long" colspan="3">
									<s:textarea name="prpDcompanyTrace.applicantDesc" id="applicantDesc"
								cols="40" rows="3"></s:textarea></td>
							</tr>
							<tr>
								<td class="bgc_tt short" >审核意见</td>
								<td class="long" colspan="3">
									<s:textarea name="prpDcompanyTrace.auditSuggest" cols="45" rows="4" 
										id="auditSuggest" readonly="true"></s:textarea>
								</td>
							</tr>
							<tr>
								<td class="bgc_tt short">
									备注
								</td>
								<td class="long" colspan="3"> 
									<s:textarea name="prpDcompanyTrace.remark" id="remark"
										cols="40" rows="3"></s:textarea>
							    </td>
							</tr>
						</s:if>
						<s:elseif test="${editType=='view'}">
							<tr>
								<td class="bgc_tt short">
									机构代码
									<font color="red">*</font>
								</td>
								<td class="long">
									<s:textfield name="prpDcompanyTrace.comCode" id="comCode"
										cssClass='input_w w_30 dc-chk dt-nzhs' readonly="true"/>
								</td>
								<td class="bgc_tt short">
									机构名称
									<font color="red">*</font>
								</td>
								<td class="long">
									<s:textfield name="prpDcompanyTrace.comCName" id="comCName"
										cssClass="input_w w_30 dc-chk dt-nzhs" readonly="true" />
								</td>
							</tr>
							<tr>
								<td class="bgc_tt short">
									机构英文名
								</td>
								<td class="long">
									<s:textfield name="prpDcompanyTrace.comEName" id="comEName"
										cssClass='input_w w_30' maxlength="80" readonly="true"/>
								</td>
								<td class="bgc_tt short">
									机构地址								
								</td>
								<td class="long">
									<s:textfield name="prpDcompanyTrace.addressCName"
										id="addressCName" cssClass='input_w w_30'
										maxlength="" readonly="true" />
								</td>
							</tr>
							<tr>
								<td class="bgc_tt short">
									英文地址
								</td>
								<td class="long">
									<s:textfield name="prpDcompanyTrace.addressEName"
										id="addressEName" cssClass='input_w w_30' maxlength="80" readonly="true"
										/>
								</td>
								<td class="bgc_tt short">
									邮编
								</td>
								<td class="long">
									<s:textfield name="prpDcompanyTrace.postCode" id="postCode"
										cssClass='input_w w_30 dt-num' maxlength="20" readonly="true" />
								</td>
							</tr>
							<tr>
								<td class="bgc_tt short">
									电话
								</td>
								<td class="long">
									<s:textfield name="prpDcompanyTrace.phoneNumber"
										id="phoneNumber" cssClass='input_w w_30' maxlength="20" readonly="true"
										 />
								</td>
								<td class="bgc_tt short">
									传真
								</td>
								<td class="long">
									<s:textfield name="prpDcompanyTrace.faxNumber" id="faxNumber"
										cssClass='input_w w_30' maxlength="20"  readonly="true"/>
								</td>
							</tr>
							<tr>
								<td class="bgc_tt short">
									上级机构代码
								</td>
								<td class="long">
									<s:textfield name="prpDcompanyTrace.upperComCode"
										id="uppercomcode" cssClass='input_w w_30' maxlength="20"
										readonly="true" />
								</td>
								<td class="bgc_tt short">
									归属保险公司名称
								</td>
								<td class="long">
									<s:textfield name="prpDcompanyTrace.insurerName"
										id="insurerName" cssClass='input_w w_30' maxlength="40" readonly="true"
										 />
								</td>
							</tr>
							<tr>
								<td class="bgc_tt short">
									机构类型
								</td>
								<td class="long">
									<s:select name="prpDcompanyTrace.comType" id="comType"
										list="#@java.util.HashMap@{'0':'出单','1':'归属','2':'收付'}" disabled="true"/>
								</td>
								<td class="bgc_tt short">
									经理
								</td>
								<td class="long">
									<s:textfield name="prpDcompanyTrace.manager" id="manager"
										cssClass='input_w w_30' maxlength="20" readonly="true" />
								</td>
							</tr>
							<tr>
								<td class="bgc_tt short">
									会计
								</td>
								<td class="long">
									<s:textfield name="prpDcompanyTrace.accountant" id="accountant"
										cssClass='input_w w_30' maxlength="20"  readonly="true"/>
								</td>
								<td class="bgc_tt short">
									最新机构代码
								</td>
								<td class="long">
									<s:textfield name="prpDcompanyTrace.newComCode" id="newComCode"
										cssClass='input_w w_30' maxlength="20" readonly="true" />
								</td>
							</tr>
							<tr>
								<td class="bgc_tt short">
									帐户归属机构代码
								</td>
								<td class="long">
									<s:textfield name="prpDcompanyTrace.acntUnit" id="acntUnit"
										cssClass='input_w w_30' maxlength="20" readonly="true" />
								</td>
								<td class="bgc_tt short">
									专项代码
								</td>
								<td class="long">
									<s:textfield name="prpDcompanyTrace.articleCode"
										id="articleCode" cssClass='input_w w_30' maxlength="20" readonly="true"
										 />
								</td>
							</tr>
							<tr>
								<td class="bgc_tt short">
									有效标志
								</td>
								<td class="long"">
									<ct:select name="prpDcompanyTrace.validStatus"
										value="${prpDcompanyTrace.validStatus}" sysCode="IMS"
										codeType="ValidStatus" disabled='true'></ct:select>
								</td>
								<td class="bgc_tt short"> 
									机构性质
								</td>
								<td class="long">
									<s:textfield name="prpDcompanyTrace.comFlag" id="comFlag"
										cssClass='input_w w_30' maxlength="2"  readonly="true"/>
								</td>
							</tr>
							<tr>
								<td class="bgc_tt short">
									标志字段第一位
								</td>
								<td class="long">
									<s:select name="flag1" id="flag1"
										list="#@java.util.HashMap@{' ':'其它','1':'清算中心的机构代码','9':'北分金锁单证接口'}" disabled='true'/>
								</td>
								<td class="bgc_tt short"
									title="如果为9: 传入收付费的ksdm=ComCode 其它：ksdm = Makecom ；如果为 8 保费分摊判断公司代码等于8取comcode[1,8]，其他取comcode[1,6]">
									标志字段第二位
								</td>
								<td class="long">
									<s:textfield name="flag2" id="flag2" cssClass='input_w w_30'
										maxlength="1"  readonly="true"/>
								</td>
							</tr>
							<tr>
								<td class="bgc_tt short" title="标志字段第三位">
									是否分公司
								</td>
								<td class="long">
									<s:select name="prpDcompanyTrace.branchType" id="branchType"
										list="#@java.util.HashMap@{'2':' 区县','3':'科室','4':'地市公司','5':'省公司','7':'直辖市/计划单列市','8':'网点'}" disabled='true'/>
								</td>
								<td class="bgc_tt short" title="标志字段第四位">
									是否基层核算单位
								</td>
								<td class="long">
									<s:select name="prpDcompanyTrace.centerFlag" id="centerFlag"
										list="#@java.util.HashMap@{'0':' 否','1':'是'}" disabled='true'/>
								</td>
							</tr>
							<tr>
								<td class="bgc_tt short">
									序号
									<font color="red">*</font>
								</td>
								<td class="long">
									<s:textfield name="prpDcompanyTrace.serialNo" id="serialNo"
										cssClass='input_w w_30 dc-chk dt-nzhs' readonly="true"
										maxlength=""/>
								</td>
								<td class="bgc_tt short">
									申请人
									<font color="red">*</font>
								</td>
								<td class="long">
									<s:textfield name="prpDcompanyTrace.applicantMen"
										id="applicantMen" cssClass='input_w w_30 dc-chk dt-nzhs'
										 maxlength="" readonly="true"/>
								</td>
							</tr>
							<tr>
								<td class="bgc_tt short">
									审核状态
									<font color="red">*</font>
								</td>
								<td class="long">
								<c:if test="${prpDcompanyTrace.currentStatus==0}">
									<s:textfield value="初始化"
										id="currentStatus" readonly="true"
										cssClass='input_w w_30 dc-chk dt-nzhs' maxlength="" />
								</c:if>
								<c:if test="${prpDcompanyTrace.currentStatus==1}">
									<s:textfield value="待审核"
										id="currentStatus" readonly="true"
										cssClass='input_w w_30 dc-chk dt-nzhs' maxlength="" />
								</c:if>
								<c:if test="${prpDcompanyTrace.currentStatus==8}">
									<s:textfield value="审核通过"
										id="currentStatus" readonly="true"
										cssClass='input_w w_30 dc-chk dt-nzhs' maxlength="" />
								</c:if>
								<c:if test="${prpDcompanyTrace.currentStatus==9}">
									<s:textfield value="审核未通过"
										id="currentStatus" readonly="true"
										cssClass='input_w w_30 dc-chk dt-nzhs' maxlength="" />
								</c:if>
								</td>
								<td class="bgc_tt short">
									申请类型
									<font color="red">*</font>
								</td>
								<td class="long">
								<c:if test="${prpDcompanyTrace.applicantType==1}">
									<s:textfield value="新增" readonly="true"
										id="applicantType"
										cssClass='input_w w_30 dc-chk dt-nzhs' maxlength=""
										/>
								</c:if>
								<c:if test="${prpDcompanyTrace.applicantType==2}">
									<s:textfield value="修改" readonly="true"
										id="applicantType"
										cssClass='input_w w_30 dc-chk dt-nzhs' maxlength=""
										/>
								</c:if>
								<c:if test="${prpDcompanyTrace.applicantType==3}">
									<s:textfield value="注销/启用" readonly="true"
										id="applicantType"
										cssClass='input_w w_30 dc-chk dt-nzhs' maxlength=""
										/>
								</c:if>
								</td>
							</tr>
							<tr>
								<td class="bgc_tt short">
									申请时间
								</td>
								<td class="long">
									<s:textfield name="prpDcompanyTrace.applicantDate"
										id="applicantDate" cssClass="input_w w_30 "
										value="${prpDcompanyTrace.applicantDate}" readonly="true"/>
								</td>
								<td class="bgc_tt short">
									最后修改时间							
								</td>
								<td class="long">
									<s:textfield name="prpDcompanyTrace.updateDate" id="updateDate"
										cssClass="input_w w_30 " 
										value="${prpDcompanyTrace.updateDate}" readonly="true"/>
								</td>
							</tr>
							<tr>
								<td class="bgc_tt short">申请描述<font color="red">*</font></td>
								<td class="long" colspan="3">
									<s:textarea name="prpDcompanyTrace.applicantDesc" id="applicantDesc"
								cols="40" rows="3" readonly="true"></s:textarea></td>
							</tr>
								<tr>
								<td class="bgc_tt short" >审核意见</td>
								<td class="long" colspan="3">
									<s:textarea name="prpDcompanyTrace.auditSuggest" cols="45" rows="4" 
										id="auditSuggest" readonly="true"></s:textarea>
								</td>
							</tr>
							<tr>
								<td class="bgc_tt short">
									备注
								</td>
								<td class="long" colspan="3"> 
									<s:textarea name="prpDcompanyTrace.remark" id="remark"
										cols="40" rows="3" readonly="true"></s:textarea>
							    </td>
							</tr>
						</s:elseif>
					</table>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr align="center" class="top">
							<c:if test="${editType=='update' }">
								<td>
								<button type="button" value="" 
										onclick="updateMethod()"><span><em>保存</em></span></button>
<!--									<input type="button" value="保存" class="button_ty"-->
<!--										onclick="updateMethod()">-->
								</td>
							</c:if>
							<c:if test="${editType=='update' }">
								<td>
								<button  type="button" value="" 
										onclick="updateAndAuditMethod()"><span><em>保存并提交</em></span></button>
<!--									<input type="button" value="保存并提交" class="button_ty"-->
<!--										onclick="updateAndAuditMethod()">-->
								</td>
							</c:if>
						</tr>
					</table>
				</s:form>
			</div>
		</div>
	</body>
</html>
<script language="javascript" src="${ctx}/common/js/newCalendar.js"></script>
		<script language="javascript" src="${ctx}/common/dwr/engine.js"></script>
		<script language="javascript" src="${ctx}/common/dwr/util.js"></script>
		<script language="javascript"
			src="${ctx}/widgets/yui/autocomplete/autocomplete-min.js"></script>
		<script language="javascript" src="${ctx}/common/js/CodeSelect.js"></script>
		<script language="javascript"
			src="${ctx}/common/js/StaticJavascript.jsp"></script>
		<script type="text/javascript">
	function updateMethod(){
	var applicantDesc = document.getElementById("applicantDesc").innerHTML;
	if(checkForm()){
		if(checkLen()){
		if(trim(applicantDesc) == "" || applicantDesc == null){
			alert("请输入申请描述！");
			return false;
		}else{
			var i = 0;
			for(var j=0;j<applicantDesc.length;j++){
				 if(applicantDesc.charAt(j)<='\255') {   
					 i++;
				  }else {   
					  i=i+2;
				 }	
			}
			if(i > 255){
				alert("申请描述过长，请检查！");
				return false;
			}
			}
		fm.action="${ctx}/dictionary/updatePrpDcompanyTrace.do?flagf=2";
		fm.submit();
		 }
	}
}
	function updateAndAuditMethod(){
	var applicantDesc = document.getElementById("applicantDesc").innerHTML;
	if(checkForm()){
	 	if(checkLen()){
	 	if(trim(applicantDesc) == "" || applicantDesc == null){
			alert("请输入申请描述！");
			return false;
		}else{
			var i = 0;
			for(var j=0;j<applicantDesc.length;j++){
				 if(applicantDesc.charAt(j)<='\255') {   
					 i++;
				  }else {   
					  i=i+2;
				 }	
			}
			if(i > 255){
				alert("申请描述过长，请检查！");
				return false;
			}
			}
		fm.action="${ctx}/dictionary/updatePrpDcompanyTrace.do?flagf=1";
		fm.submit();
		}		
	}
}
	function checkForm(){
	return YAHOO.quote.data.datacheck('fm');
	}
	function keyDown(){
            // 禁止使用backspace键
            if(window.event.keyCode == 8){
             event.keyCode = 0; 
       		 event.cancelBubble = true; 
             return false; 
            }
        }
</script>


