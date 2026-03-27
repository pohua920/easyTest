<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<%-- moidfy  update by tongziliang 2011-10-09 reason:修改页面的按钮样式和优化页面样式 --%>
<link rel="stylesheet" href="${ctx}/style/popup/login.css" type="text/css"></link>
	<head>
		<title>基本医疗政策</title>
		<%@ include file="/common/i18njs.jsp"%>
		<%@ include file="/common/meta_css.jsp"%>
		<%@ include file="/common/meta_js.jsp"%>
		<script language="javascript" src="${ctx}/common/dwr/engine.js"></script>
		<script language="javascript" src="${ctx}/common/dwr/util.js"></script>
		<script language="javascript" src="${ctx}/common/js/CodeSelect.js"></script>
		<script type="text/javascript"
			src="${ctx}/pages/dictionary/prpDregulation/prpDregulation.js"></script>
		<script type="text/javascript"
			src="${ctx}/pages/dictionary/prpDregulation/prpDBasicMedicalRegulationEdit.js"></script>
	</head>
<body id="all_title" >
<div id="wrapper">
<div id="container">
<div id="crash_menu">
<h2>新增基本医疗政策</h2>
</div>
<s:form id="fm" name="fm" action="insertPrpDregulation" onsubmit="return checkForm()"
	namespace="/dictionary" method="post">
	<input type="hidden" name="prpdRegulation.regulationType" value="B"/>
	<input type="hidden" id="editType" name="editType" value="${editType }"/>
	<input type="hidden" name="prpdRegulation.regulationCode"  value="${prpdRegulation.regulationCode }" >
	<table class="fix_table">
		<tr>
		<!-- modify by duanfa 20110823 添加必录标志-->
			<td class="long" colspan="4"><span style="width: 23%" class="bgc_tt short"><font color="red">*</font>适用范围：</span>
		<select	name="prpdRegulation.proviceCode" id="proviceCode"
				onchange="changeCitycode(this)" style="width: 150px;">
				${proinvceResult }
			</select>
			<!-- modify by duanfa20110628 -->
				 <span id="cityCodeSlectSpanId"><select
					name="prpdRegulation.cityCode"
					onchange="changeCountycode(this)" style="width: 150px;">
					${cityResult }
				</select> </span> 
				<!-- modify by duanfa20110628 -->
				<span id="countyCodeSlectSpanId"><select
					name="prpdRegulation.countyCode"
					style="width: 150px;">
					${countyResult }
				</select></span>
			</td><!--modify by duanfa 20110825 输入校验  -->
			<td class="bgc_tt short"><font color="red">*</font>实施时间</td>
			<!-- modify by duanfa20111009 start 去掉字段改变后就调用checkInput -->
			<td class="long"><input name="prpdRegulation.validDate" value="${prpdRegulation.validDate }"
				id="validDate" Class="input_w w_15 dt-date dc-chk" onfocus="WdatePicker()" /></td>
		</tr>
		<tr>
			<td class="bgc_tt short"><font color="red">*</font>文号：</td>
			<!-- modify by duanfa20110928 -->
			<td class="long"><input id="fileCode" maxlength="10" name="prpdRegulation.fileCode" value="${prpdRegulation.fileCode }"
				id="uwYear" class='input_w w_15 dc-chk' maxlength="15" ></td>
			<td class="bgc_tt short"><font color="red">*</font>文件名称：</td>
			<td class="long"><input name="prpdRegulation.fileName" maxlength="15"  value="${prpdRegulation.fileName }"
				id="uwYear" class='input_w w_15 dc-chk' ></td>
			<!-- modify by duanfa20111009 end 去掉字段改变后就调用checkInput -->
			<td class="bgc_tt short"><font color="red">*</font>有效状态：</td>
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
	<br>
	<table id="prpdBasicMedicals_table">
		<tr>
			<td class="table_bgc_tt table_normal" >保险责任</td>
			<td class="table_bgc_tt table_normal" >投保类型</td>
			<td class="table_bgc_tt table_normal" >人员类别</td>
			<td class="table_bgc_tt table_normal" >基本医疗缴费标准</td>
			<td class="table_bgc_tt table_normal" >补充医疗缴费标准</td>
			<td class="table_bgc_tt table_normal" >医院级别</td>
			<td class="table_bgc_tt table_normal" >起付线</td>
			<td class="table_bgc_tt table_normal" >基本医疗最高限额</td>
			<td class="table_bgc_tt table_normal" >高额段最高限额</td>
			<td class="table_bgc_tt table_normal" >基本医疗支付比例</td>
			<td class="table_bgc_tt table_normal" >补充医疗赔付比例</td>
			<td class="table_bgc_tt table_normal" >高额及以上段赔付比例</td>
			<td class="table_bgc_tt table_normal" >操&nbsp;&nbsp;&nbsp;作</td>
		</tr>
			<s:iterator value="prpdRegulation.prpdBasicMedicals" id="prpdBasicMedical" status="status">
				<tr id='basicMedical_tr_<s:property value="#status.index" />' >
					<s:if test="${prpdBasicMedical.itemKind}==other">
						<td>
						<ct:select onchange="otherChoose(this)"  name='remove' value='other' sysCode="DMS" codeType="MedicalItemKind" ></ct:select>
						<input name='prpdBasicMedicals[${status.index}].itemKind' value="${prpdBasicMedical.itemKind}" ></td>
					</s:if>
					<s:else>
						<td>
						<ct:select onchange="otherChoose(this)"  name='prpdBasicMedicals[${status.index}].itemKind'  value="${prpdBasicMedical.itemKind}" sysCode="DMS" codeType="MedicalItemKind" ></ct:select>
						</td>
					</s:else>
					
					<s:if test="${prpdBasicMedical.insureType}==other">
						<td>
						<ct:select onchange="otherChoose(this)"  name='remove' value='other' sysCode="DMS" codeType="MedicalInsureType" ></ct:select>
						<input name='prpdBasicMedicals[${status.index}].insureType' value="${prpdBasicMedical.insureType}" ></td>
					</s:if>
					<s:else>
						<td>
						<ct:select onchange="otherChoose(this)"  name='prpdBasicMedicals[${status.index}].insureType'  value="${prpdBasicMedical.insureType}" sysCode="DMS" codeType="MedicalInsureType" ></ct:select>
						</td>
					</s:else>
					
					<s:if test="${prpdBasicMedical.personCategory}==other">
						<td>
							<ct:select onchange="otherChoose(this)" name='remove' value='other' sysCode="DMS" codeType="MedicalPersCategory" ></ct:select>
						<input name='prpdBasicMedicals[${status.index}].personCategory' value="${prpdBasicMedical.personCategory}" ></td>
					</s:if>
					<s:else>
						<td>
							<ct:select onchange="otherChoose(this)"  name='prpdBasicMedicals[${status.index}].personCategory'  value="${prpdBasicMedical.personCategory}" sysCode="DMS" codeType="MedicalPersCategory" ></ct:select>
						</td>
					</s:else>
					
					<td>
						<input type='hidden' name='prpdBasicMedicals[${status.index}].basicMedicalCode' value="${prpdBasicMedical.basicMedicalCode}" />						
						<input  name='prpdBasicMedicals[${status.index}].baseStandard' value="${prpdBasicMedical.baseStandard}"  >
					</td>
					<td><input  name='prpdBasicMedicals[${status.index}].busiStandard' value="${prpdBasicMedical.busiStandard}"  ></td>
					<td>
					<ct:select name='prpdBasicMedicals[${status.index}].hospitalLevel'  value="${prpdBasicMedical.hospitalLevel}" sysCode="DMS" codeType="hospitalType" ></ct:select>
					</td>
					<td><input  name='prpdBasicMedicals[${status.index}].payLine' value="${prpdBasicMedical.payLine}"  ></td>
					<td><input  name='prpdBasicMedicals[${status.index}].baseLimit' value="${prpdBasicMedical.baseLimit}"  ></td>
					<td><input  name='prpdBasicMedicals[${status.index}].highSegLimit' value="${prpdBasicMedical.highSegLimit}"  ></td>
					<td><input  name='prpdBasicMedicals[${status.index}].basePayScale' value="${prpdBasicMedical.basePayScale}"  ></td>
					<td><input  name='prpdBasicMedicals[${status.index}].addedPayScale' value="${prpdBasicMedical.addedPayScale}"  ></td>
					<td><input  name='prpdBasicMedicals[${status.index}].highSegPayScale' value="${prpdBasicMedical.highSegPayScale}"  ></td>
					<td>
					<!-- modify by duanfa20111024 查看时不可操作删除 -->
					<s:if test="${editType!='view'}">
					<button name='submit' type='button' style='width: 50px;' value='' onclick=deleteTrById('basicMedical_tr_${status.index}');refreshTagName('prpdBasicMedicals','prpdBasicMedicals_table') ><span><em>删除</em></span></button>
					</s:if>
<!--					<input name='submit' type='button' style='width: 50px;' value='删除' class='button_ty' onclick=deleteTrById('basicMedical_tr_${status.index}');refreshTagName('prpdBasicMedicals','prpdBasicMedicals_table') >-->
<!--		</input>-->
		</td>
		</tr>
		</s:iterator>
		<s:if test="${editType!='view'}">
			<tr id='prpdBasicMedicals_tr_last'>
				<td colspan="8">
					（按"增加"键增加信息，按"删除"键删除信息）
					<button type="button"  value=""
						onclick="addBasicMedical('prpdBasicMedicals_tr_last')"><span><em>增加</em></span></button>
<!--					<input type="button" class="button_ty" value="增加"-->
<!--						onclick="addBasicMedical('prpdBasicMedicals_tr_last')" />-->
				</td>
			</tr>
		</s:if>
		</table>
		<s:if test="${editType!='view'}">
		<!-- modify by duanfa20110823 start -->
		<button style="margin-left: 500px;"  type="submit" id="Sub_button" value=""><span><em>保存</em></span></button>
<!--		<input style="margin-left: 500px;" class="button_ty" type="submit" id="Sub_button" value="保存" />-->
		</s:if>
		<button  type="button" onclick="colseWin()" value="" ><span><em>返回</em></span></button>
<!--		<input class="button_ty except" type="button" onclick="colseWin()" value="返回" />-->
		<!-- modify by duanfa20110823 end -->
		</s:form>
		</div>
		</div>
<!--modify  by duanfa 20110805 start 基础数据从数据库取的隐藏标签 -->
<div style="display: none">
<ct:select id='MedicalItemKindSelect' sysCode='DMS' codeType='MedicalItemKind' ></ct:select>
<ct:select id='MedicalInsureTypeKindSelect' sysCode='DMS' codeType='MedicalInsureType' ></ct:select>
<ct:select id='MedicalPersCategorySelect' sysCode='DMS' codeType='MedicalPersCategory' ></ct:select>
<ct:select id='hospitalTypeSelect' sysCode='DMS' codeType='hospitalType' ></ct:select>

</div>
	</body>
</html>

