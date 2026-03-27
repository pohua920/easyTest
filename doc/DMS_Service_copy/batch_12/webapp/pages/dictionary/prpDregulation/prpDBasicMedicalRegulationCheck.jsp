<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
	<head>
		<title>基本医疗政策</title>
		<%@ include file="/common/i18njs.jsp"%>
		<%@ include file="/common/meta_css.jsp"%>
		<%@ include file="/common/meta_js.jsp"%>
		<%-- moidfy  update by tongziliang 2011-10-09 reason:修改页面的按钮样式和优化页面样式 --%>
        <link rel="stylesheet" href="${ctx}/style/popup/login.css" type="text/css"></link>
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
<!-- modify by duanfa 20110628新增基本医疗政策 -->
<h2 align="center">基本医疗政策审核</h2>
</div>
<s:form id="fm" name="fm" action="insertPrpDregulation" onsubmit="return checkForm()"
	namespace="/dictionary" method="post">
	
	<table class="fix_table">
		<tr><!-- modify by duanfa 20110628 start -->
		<!-- td class="long" colspan="4"span style="width: 8%" class="bgc_tt short"省： -->
			<td class="long" colspan="4"><span style="width: 23%" class="bgc_tt short">适用范围：</span>
		<!-- modify by duanfa 20110628 end -->
		    <select
				name="prpdRegulation.proviceCode" id="proviceCode"
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
		</td>
		</tr>
		</s:iterator>
					
				</table>
		</s:form><br>
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
							<select id='commentSelect' class="except"  style="width: 86px;" onchange="changeComments(this,'comments')">
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
					<tr><!-- modify by duanfa20110825 单条审批 -->
						<td >
						<button type="button" onclick="passAll(this.form,false)"  value=""><span><em>审核通过</em></span></button>
<!--						<input type="button" onclick="passAll(this.form,false)" class="except button_ty" value="审核通过" />-->
						</td>
						<td >
						<button type="button" onclick="rejectAll(this.form,false)"  value=""><span><em>退回处理</em></span></button>
<!--						<input type="button" onclick="rejectAll(this.form,false)" class="except button_ty" value="退回处理" />-->
						</td>
						<td >
						<button type="button" onclick="history.go(-1)"  value=""><span><em>返回</em></span></button>
<!--						<input type="button" onclick="history.go(-1)" class="except button_ty" value="返回" />-->
						</td>
					</tr>
				</table>
		</form>
		</div>
		</div>
	</body>
</html>

