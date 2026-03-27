<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/i18njs.jsp"%>
<%@ include file="/common/meta_css.jsp"%>
<%@ include file="/common/meta_js.jsp"%>
<%-- moidfy  update by tongziliang 2011-10-09 reason:修改页面的按钮样式和优化页面样式 --%>
<link rel="stylesheet" href="${ctx}/style/popup/login.css" type="text/css"></link>
<html>
<head>
<title>工伤管理条例</title>
<script language="javascript" src="${ctx}/common/dwr/engine.js"></script>
<script language="javascript" src="${ctx}/common/dwr/util.js"></script>
<script language="javascript" src="${ctx}/common/js/CodeSelect.js"></script>
<script type="text/javascript"
	src="${ctx}/pages/dictionary/prpDregulation/prpDregulation.js"></script>
<script type="text/javascript"
	src="${ctx}/pages/dictionary/prpDregulation/prpDInjuryRegulationEdit.js"></script>
</head>
<body id="all_title">
<div id="wrapper">
<div id="container">
<div id="crash_menu">
<h2 align="center">工伤条例审核</h2>
</div>
<s:form id="fm" name="fm" action="insertPrpDregulation" onsubmit="return checkForm()"
	namespace="/dictionary" method="post">
	
	<table class="fix_table">
		<tr>
			<td class="long" colspan="4"><span style="width: 23%" class="bgc_tt short">适用范围：</span><select
				name="prpdRegulation.proviceCode" id="proviceCode"
				onchange="changeCitycode(this)" style="width: 150px;">
				${proinvceResult }
			</select>
				    <span id="cityCodeSlectSpanId"><select
					name="prpdRegulation.cityCode"
					onchange="changeCountycode(this)" style="width: 150px;">
					${cityResult }
				</select> </span> 
				<span id="countyCodeSlectSpanId"><select
					name="prpdRegulation.countyCode"
					style="width: 150px;">
					${countyResult }
				</select></span>
			</td>
			<td class="bgc_tt short"><font color="red">*</font>实施时间</td>
			<td class="long"><input name="prpdRegulation.validDate" value="${prpdRegulation.validDate }"
				id="validDate" Class="input_w w_15 dt-date dc-chk dt-nzhs Wdate" onfocus="WdatePicker()" /></td>
		</tr>
		<tr>
			<td class="bgc_tt short"><font color="red">*</font>文号：</td>
			<td class="long"><input id="fileCode" name="prpdRegulation.fileCode" value="${prpdRegulation.fileCode }"
				id="uwYear" class='input_w w_15 dc-chk dt-nzhs'></td>
			<td class="bgc_tt short"><font color="red">*</font>文件名称：</td>
			<td class="long"><input name="prpdRegulation.fileName" value="${prpdRegulation.fileName }"
				id="uwYear" class='input_w w_15 dc-chk dt-nzhs'></td>
			<td class="bgc_tt short">工伤发生率：</td>
			<td class="long"><input name="prpdRegulation.jobInjuryRate" value="${prpdRegulation.jobInjuryRate }"
				id="uwYear" onblur="checkNumber(this)" class='input_w w_15 '
				maxlength="6"></td>
		</tr>
		<tr>
		
			<td class="bgc_tt short"><font color="red">*</font>投保人归属行业类别：</td>
			<td class="long">
			<!-- modify by duanfa 20110815 改为下拉框 -->
			<!--  input name="prpdRegulation.indusCategory" value="${prpdRegulation.indusCategory }"
				id="uwYear" class='input_w w_15 dc-chk dt-nzhs'-->
			<ce:select name="prpdRegulation.indusCategory" id="indusCategory" cssClass="input_w w_15 dc-chk dt-nzhs"  value="${prpdRegulation.indusCategory }" 
			      list="#@java.util.HashMap@{'1':'一类行业','2':'二类行业','3':'三类行业'}" />
			</td>
			<td class="bgc_tt short"><font color="red">*</font>工伤鉴定周期：</td>
			<td class="long"><input name="prpdRegulation.identifyPeriod" value="${prpdRegulation.identifyPeriod }"
				id="uwYear" class='input_w w_15 dc-chk dt-nzhs'></td>
			<td class="bgc_tt short"><font color="red">*</font>效力状态</td>
			<td class="long"><select name="prpdRegulation.validStatus" value="${prpdRegulation.validStatus }">
				<option value="1">有效</option>
				<option value="0">无效</option>
			</select></td>
		</tr><!--modify by duanfa 20110921 影像地址  -->
		<tr>
			<td class="bgc_tt short">影像地址：</td>
			<td class="long" colspan="3" ><input id="prpdRegulation.imagePath" name="prpdRegulation.imagePath" type="text" value="${prpdRegulation.imagePath }" style="width: 500px;"/></td>
				<td class="table_bgc_tt" ></td>
				<td class="table_bgc_tt"></td>
		</tr>
	</table>
	
	<br/>
	<table id="injuryDefine_table" style="width: 70%;">
		<tr id='injuryDefine_tr_head' align="center">
			<td class="table_bgc_tt table_long" >界定描述</td>
		</tr>
			<s:iterator value="prpdRegulation.prpdInjuryDefines" id="injuryDefine" status="status">
				<tr id='injuryDefine_tr_<s:property value="#status.index" />' >
					<td>
						<input type="hidden"  name='prpdInjuryDefines[<s:property value="#status.index" />].injuryDefineCode' value='<s:property value="#injuryDefine.injuryDefineCode" />'/>
						<input class='table_long' name='prpdInjuryDefines[<s:property value="#status.index" />].defineDesc' value='<s:property value="#injuryDefine.defineDesc" />'>
					</td>
				</tr>
			</s:iterator>
	</table>
	<br/>
	<table id="injuryRate_table" >
		<tr align="center">
			<td class="table_bgc_tt table_normal" align="center">一级费率档次</td>
			<td class="table_bgc_tt table_normal" align="left">二级费率档次</td>
			<td class="table_bgc_tt table_normal" align="center">三级费率档次</td>
		</tr>
		<s:iterator value="prpdRegulation.prpdInjuryRates" id="prpdInjuryRate" status="status">
			<tr id='injuryRate_tr_<s:property value="#status.index" />' >
				<td><input type='hidden' name='prpdInjuryRates[${status.index}].injuryRateCode' value="${prpdInjuryRate.injuryRateCode}" />
					<ct:select name='prpdInjuryRates[${status.index}].firstGrade'  value="${prpdInjuryRate.firstGrade}" sysCode="DMS" codeType="injuryRate" ></ct:select>
				</td>
				<td>
					<ct:select name='prpdInjuryRates[${status.index}].secondGrade'  value="${prpdInjuryRate.secondGrade}" sysCode="DMS" codeType="injuryRate" ></ct:select>
				</td>
				<td>
					<ct:select name='prpdInjuryRates[${status.index}].thirdGrade'  value="${prpdInjuryRate.thirdGrade}" sysCode="DMS" codeType="injuryRate" ></ct:select>
				</td>
			</tr>
		</s:iterator>
	</table>
	<br/>
	<table id="injuryDutie_table" >
		<tr>
			<td class="table_bgc_tt table_normal">责任类型</td>
			<td class="table_bgc_tt table_normal" >给付类型</td>
			<td class="table_bgc_tt table_normal" >伤残类别</td>
			<td class="table_bgc_tt table_normal" >伤残等级</td>
			<td class="table_bgc_tt table_normal" >给付计算基数类型</td>
			<td class="table_bgc_tt table_normal" >支付单位类别</td>
			<td class="table_bgc_tt table_normal" >支付单位</td>
		</tr>
		
		<s:iterator value="prpdRegulation.prpdInjuryDuties" id="injuryDutie" status="status">
				<tr id='injuryDutie_tr_<s:property value="#status.index" />' >
				<td><select name='prpdInjuryDuties[<s:property value="#status.index" />].dutyType' value='<s:property value="#injuryDutie.dutyType" />'> <option value='a'>主要责任</option></select></td>
					<td>
						<input type='hidden' name='prpdInjuryDuties[${status.index}].injuryDutyCode' value="${injuryDutie.injuryDutyCode}" />						
						<ct:select name='prpdInjuryDuties[${status.index}].paymentType' value="${injuryDutie.paymentType}" sysCode="DMS" codeType="paymentType" ></ct:select>
					</td>
					<td>
						<ct:select   onchange='changeDisabiLevel(this,${status.index})' name='prpdInjuryDuties[${status.index}].disabiCategory' value="${injuryDutie.disabiCategory}" sysCode="DMS" codeType="disabiCategory" ></ct:select>
					</td>
					<td>
						<s:if test="${injuryDutie.disabiCategory}==1">
							<ct:select id='disabiLevel_${status.index}' name='prpdInjuryDuties[${status.index}].disabiLevel' value="${injuryDutie.disabiLevel}" sysCode="DMS" codeType="WoundLevel" ></ct:select>
						</s:if>
						<s:if test="${injuryDutie.disabiCategory}==2">
							<ct:select id='disabiLevel_${status.index}' name='prpdInjuryDuties[${status.index}].disabiLevel' value="${injuryDutie.disabiLevel}" sysCode="DMS" codeType="disabiLevel" ></ct:select>
						</s:if>
					</td>
					<td>
							<ct:select name='prpdInjuryDuties[${status.index}].countType' value="${injuryDutie.countType}" sysCode="DMS" codeType="injuryCountType" ></ct:select>
					</td>
					<td>
							<ct:select name='prpdInjuryDuties[${status.index}].payScaleType' value="${injuryDutie.payScaleType}" sysCode="DMS" codeType="payScaleType" ></ct:select>
					</td>
					<td><input name='prpdInjuryDuties[${status.index}].payScale' value="${injuryDutie.payScale}"  /></td>
				</tr>	
				<!-- add by duanfa20110806 上次误删 -->
				</s:iterator>			
	</table>
</s:form>
<br>
<br>
<br>
<form action="checkPassRegulation.do" method="post">
<input type="hidden" id="editType" name="editType" value="${editType }"/>
<input type="hidden" class="except" name="regulationCode"  value="${prpdRegulation.regulationCode }" >
	<table width="60%" style=" margin-left:15%">
					<tr>
						<td valign="top">
							审核片语：
						</td>
						<td valign="top">
							<select id='commentSelect' class="except" style="width: 86px;" onchange="changeComments(this,'comments')">
								<option value=''>
									请选择
								</option>
								<option value='通过'>
									通过
								</option>
								<option value='退回处理'>
									退回处理
								</option>
							</select>
						</td>
						<td valign="top">
							审核意见：
						</td>
						<td style="width: 60%">
							<textarea id="comments" name="comments" rows="8" cols="50" ></textarea>
						</td>
					</tr>
					<tr>
						<!-- modify by duanfa20110825 单条审批 -->
						<td >
						<button type="button" onclick="passAll(this.form,false)"  value=""><span><em>审核通过</em></span></button>
<!--						<input type="button" onclick="passAll(this.form,false)" class="except button_ty" value="审核通过" />-->
						</td>
						<td >
						<button type="button" onclick="rejectAll(this.form,false)" value=""><span><em>退回处理</em></span></button>
<!--						<input type="button" onclick="rejectAll(this.form,false)" class="except button_ty" value="退回处理" />-->
						</td>
						<td >
						<button type="button" onclick="history.go(-1)"  value=""><span><em>返  回</em></span></button>
<!--						<input type="button" onclick="history.go(-1)" class="except button_ty" value="返  回" />-->
						</td>
					</tr>
				</table>
				
</form>
</div>
</div>
</body>
</html>

