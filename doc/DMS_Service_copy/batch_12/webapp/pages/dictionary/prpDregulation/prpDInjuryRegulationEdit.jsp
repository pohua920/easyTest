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
<h2>新增工伤条例</h2>
</div>
<s:form id="fm" name="fm" action="insertPrpDregulation" onsubmit="return checkForm()"
	namespace="/dictionary" method="post">
	<input type="hidden" name="prpdRegulation.regulationType" value="I"/>
	<input type="hidden" id="editType" name="editType" value="${editType }"/>
	<input type="hidden" name="prpdRegulation.regulationCode"  value="${prpdRegulation.regulationCode }" >
	<table class="fix_table">
		<tr>	<!-- modify by duanfa 20110823 添加必录标志-->
			<td class="bgc_tt short"><font color="red">*</font>适用范围：</td>
			<td colspan="3">
			<select
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
			</td><!--modify by duanfa 20110825 start 输入校验  -->
			<td class="bgc_tt short"><font color="red">*</font>实施时间</td>
			<!-- modify by duanfa20111009 start 去掉字段改变后就调用checkInput -->
			<td class="long"><input name="prpdRegulation.validDate" value="${prpdRegulation.validDate }"
				id="validDate" Class="input_w w_15 dt-date dc-chk " onfocus="WdatePicker()" /></td>
		</tr>
		<tr>
			<td class="bgc_tt short"><font color="red">*</font>文号：</td>
			<!-- modify by duanfa20110929 -->
			<td class="long"><input id="fileCode" name="prpdRegulation.fileCode" maxlength="10" value="${prpdRegulation.fileCode }"
				id="uwYear" class='input_w w_15 dc-chk' ></td>
			<td class="bgc_tt short"><font color="red">*</font>文件名称：</td>
			<td class="long"><input name="prpdRegulation.fileName" maxlength="15" value="${prpdRegulation.fileName }"
				id="uwYear" class='input_w w_15 dc-chk' ></td>
			<td class="bgc_tt short">工伤发生率：</td>
			<td class="long"><input name="prpdRegulation.jobInjuryRate" value="${prpdRegulation.jobInjuryRate }"
				id="uwYear" onblur="checkNumber(this)" class='input_w w_15 '
				maxlength="6"></td>
		</tr>
		<tr>
		
			<td class="bgc_tt short"><font color="red">*</font>投保人归属行业类别：</td>
			<td class="long">
			<!-- modify by duanfa 20110729 start 改为下拉框  -->
			<!--input name="prpdRegulation.indusCategory" value="${prpdRegulation.indusCategory }"
				id="uwYear" class='input_w w_15 dc-chk dt-nzhs'-->
				<ce:select name="prpdRegulation.indusCategory" id="indusCategory" cssClass="input_w w_15 dc-chk dt-nzhs"  value="${checked}" 
				      list="#@java.util.HashMap@{'1':'一类行业','2':'二类行业','3':'三类行业'}" />
			<!-- modify by duanfa 20110729 end 改为下拉框  -->
			</td>
			<td class="bgc_tt short"><font color="red">*</font>工伤鉴定周期：</td>
			<!-- modify by duanfa20110928 数字校验 -->
			<td class="long"><input name="prpdRegulation.identifyPeriod" value="${prpdRegulation.identifyPeriod }"
				id="uwYear" class='input_w w_15 dc-chk dt-num' ></td>
			<td class="bgc_tt short"><font color="red">*</font>效力状态</td>
			<!-- modify by duanfa20111009 end 去掉字段改变后就调用checkInput -->
			<td class="long"><select name="prpdRegulation.validStatus" value="${prpdRegulation.validStatus }">
				<option value="1">有效</option>
				<option value="0">无效</option>
			</select></td>
		</tr>
		<!--modify by duanfa 20110921 影像地址  -->
		<tr>
			<td class="bgc_tt short">影像地址：</td>
			<td class="long" colspan="3" ><input id="prpdRegulation.imagePath" name="prpdRegulation.imagePath" type="text" value="${prpdRegulation.imagePath }" style="width: 500px;"/></td>
				<td class="table_bgc_tt" ></td>
				<td class="table_bgc_tt"></td>
		</tr>
	</table>
	
	<br/><!--modify by duanfa20110825 调整样式-->
	<table id="injuryDefine_table" style="width: 70%;" class="fix_table">
		<tr id='injuryDefine_tr_head' align="center" class="sort">
			<th>界定描述</th>
			<th>操作</th>
		</tr>
			<s:iterator value="prpdRegulation.prpdInjuryDefines" id="injuryDefine" status="status">
				<tr id='injuryDefine_tr_<s:property value="#status.index" />' >
					<td>
						<input type="hidden"  name='prpdInjuryDefines[<s:property value="#status.index" />].injuryDefineCode' value='<s:property value="#injuryDefine.injuryDefineCode" />'/>
						<input class='table_long' name='prpdInjuryDefines[<s:property value="#status.index" />].defineDesc' value='<s:property value="#injuryDefine.defineDesc" />'>
					</td>
					<td>
					<!-- modify by duanfa20111024 查看时不可操作删除 -->
					<s:if test="${editType!='view'}">
					<button type='button' value=''  onclick='deleteTrById("injuryDefine_tr_<s:property value="#status.index" />");refreshTagName("prpdInjuryDefines","injuryDefine_table")'><span><em>删除</em></span></button>
					</s:if>
<!--					<input type='button' value='删除' class='button_ty' onclick='deleteTrById("injuryDefine_tr_<s:property value="#status.index" />");refreshTagName("prpdInjuryDefines","injuryDefine_table")'/>-->
					</td>
				</tr>
			</s:iterator>
			<s:if test="${editType!='view'}">
				<tr id='injuryDefine_tr_last'>
					<td colspan="2" align="right">（按"增加"键增加信息，按"删除"键删除信息）
					<button type="button" 
						value="" onclick="addInjuryDefine('injuryDefine_tr_last')"><span><em>增 加</em></span></button>
<!--					<input type="button" class="button_ty"-->
<!--						value="增 加" onclick="addInjuryDefine('injuryDefine_tr_last')" />-->
						</td>
				</tr>
			</s:if>
	</table>
	<br/>
	<table id="injuryRate_table" class="fix_table">
		<tr class="sort">
			<th >一级费率档次</th>
			<th >二级费率档次</th>
			<th >三级费率档次</th>
			<th >操作</th>
		</tr>
		<s:iterator value="prpdRegulation.prpdInjuryRates" id="prpdInjuryRate" status="status">
			<tr id='injuryRate_tr_<s:property value="#status.index" />'  align="center">
				<td><input type='hidden' name='prpdInjuryRates[${status.index}].injuryRateCode' value="${prpdInjuryRate.injuryRateCode}" />
					<ct:select name='prpdInjuryRates[${status.index}].firstGrade'  value="${prpdInjuryRate.firstGrade}" sysCode="DMS" codeType="injuryRate" ></ct:select>
				</td>
				<td>
					<ct:select name='prpdInjuryRates[${status.index}].secondGrade'  value="${prpdInjuryRate.secondGrade}" sysCode="DMS" codeType="injuryRate" ></ct:select>
				</td>
				<td>
					<ct:select name='prpdInjuryRates[${status.index}].thirdGrade'  value="${prpdInjuryRate.thirdGrade}" sysCode="DMS" codeType="injuryRate" ></ct:select>
				</td>
				<td>
				<!-- modify by duanfa20111024 查看时不可操作删除 -->
				<s:if test="${editType!='view'}">
				<button name='submit' type='button' value='' onclick=deleteTrById('injuryRate_tr_${status.index}');refreshTagName('prpdInjuryRates','injuryRate_table') ><span><em>删除</em></span></button>
				</s:if>
<!--				<input name='submit' type='button' value='删除' class='button_ty' onclick=deleteTrById('injuryRate_tr_${status.index}');refreshTagName('prpdInjuryRates','injuryRate_table') ></input>-->
				</td>
			</tr>
		</s:iterator>
		<s:if test="${editType!='view'}">
			<tr id="injuryRate_tr_last">
				<td colspan="4" align="right">（按"增加"键增加信息，按"删除"键删除信息）
				<button type="button"  onclick="addInjuryRate('injuryRate_tr_last')" value=""><span><em>增 加</em></span></button>
<!--				<input type="button" class="button_ty" onclick="addInjuryRate('injuryRate_tr_last')" value="增 加"/>-->
				
				</td>
			</tr>
		</s:if>
	</table>
	<br/>
	<table id="injuryDutie_table"  class="fix_table">
		<tr class="sort">
			<th>责任类型</th>
			<th >给付类型</th>
			<th >伤残类别</th>
			<th >伤残等级</th>
			<th >给付计算基数类型</th>
			<th >支付单位类别</th>
			<th >支付单位</th>
			<th >操作</th>
		</tr>
		
			<s:iterator value="prpdRegulation.prpdInjuryDuties" id="injuryDutie" status="status">
				<tr id='injuryDutie_tr_<s:property value="#status.index" />'  align="center">
				<td><select name='prpdInjuryDuties[<s:property value="#status.index" />].dutyType' value='<s:property value="#injuryDutie.dutyType" />'> <option value='1'>主要责任</option></select></td>
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
					<td>
					<!-- modify by duanfa20111024 查看时不可操作删除 -->
					<s:if test="${editType!='view'}">
					<button name='submit' type='button' value='' style='width: 50px;'  onclick=deleteTrById('injuryDutie_tr_${status.index}');refreshTagName('prpdInjuryDuties','injuryDutie_table') ><span><em>删除</em></span></button>
					</s:if>
<!--					<input name='submit' type='button' value='删除' style='width: 50px;' class='button_ty' onclick=deleteTrById('injuryDutie_tr_${status.index}');refreshTagName('prpdInjuryDuties','injuryDutie_table') ></input>-->
					</td>
				</tr>
			</s:iterator>
		<s:if test="${editType!='view'}">
			<tr id='injuryDutie_tr_last'>
				<td colspan="8" align="right">（按"增加"键增加信息，按"删除"键删除信息）
				<button type="button" 
					value="" onclick="addInjuryDutie('injuryDutie_tr_last')"><span><em>增 加</em></span></button>
<!--				<input type="button" class="button_ty"-->
<!--					value="增 加" onclick="addInjuryDutie('injuryDutie_tr_last')"  />-->
					</td>
			</tr>
		</s:if>
	</table>
	<s:if test="${editType!='view'}">
	<!-- modify by duanfa20110823 start  -->
	<button style="margin-left: 500px;"  type="submit"  id="Sub_button" value=""><span><em>提 交</em></span></button>
<!--	<input style="margin-left: 500px;" class="button_ty" type="submit"  id="Sub_button" value="提 交" />-->
	</s:if>
	<button  type="button" onclick="colseWin()" value=""><span><em>返 回</em></span></button>
<!--		<input class="button_ty except" type="button" onclick="colseWin()" value="返 回" />-->
		<!-- modify by duanfa20110823 end  -->
</s:form></div>
</div>
<!-- add by duanfa 20110804 隐藏标签 -->
<div style="display: none">
<ct:select id='WoundLevelSelect' sysCode='DMS' codeType='WoundLevel' ></ct:select>
<ct:select id='paymentTypeSelect' sysCode='DMS' codeType='paymentType' ></ct:select>
<ct:select id='injuryRateSelect' sysCode='DMS' codeType='injuryRate' ></ct:select>
<ct:select id='disabiCategorySelect' sysCode='DMS' codeType='disabiCategory' ></ct:select>
<ct:select id='injuryCountTypeSelect' sysCode='DMS' codeType='injuryCountType' ></ct:select>
<ct:select id='payScaleTypeSelect' sysCode='DMS' codeType='payScaleType' ></ct:select>
<ct:select id='disabiLevelSelect' sysCode='DMS' codeType='disabiLevel' ></ct:select>

</div>
</body>
</html>

