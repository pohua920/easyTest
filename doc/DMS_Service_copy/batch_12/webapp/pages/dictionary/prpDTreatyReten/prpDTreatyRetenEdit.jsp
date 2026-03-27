<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="cn.com.sinosoft.ims.sync.SyncConstants"%>
<%
	String deployCom = (String)session.getAttribute("deployCom");
%>
<%-- moidfy  update by tongziliang 2011-10-09 reason:修改页面的按钮样式和优化页面样式 --%>
<link rel="stylesheet" href="${ctx}/style/popup/login.css" type="text/css"></link>
<html>
<head>
<title>自留额计划</title>
<%@include file="/common/meta_css.jsp"%>
<%@ include file="/common/i18njs.jsp"%>

</head>
<!--  onkeydown方法禁用BackSpace onload方法光标定位在首个输入框 -->
<body id="all_title" onkeydown="keyDown()" onload="fm.uwYear.focus()">
<div id="wrapper">
<div id="container">
<s:form name="fm" action="${ctx}/dictionary/insertPrpDTreatyReten.do" method="post">
<s:hidden name="editType" id="editType" value="${editType}"></s:hidden>
<s:hidden name="deployCom" id="deployCom" value="${deployCom}"></s:hidden>
<!--  
<s:hidden name="prpDtreatyReten.flag" id="flag" value="${prpDtreatyReten.flag}"></s:hidden>
-->
<table width="100%" class="fix_table">
<tr class="top">
	<s:if test="${editType=='insert' }">
		<div id="crash_menu">
			<h2 align="center">增加自留额计划</h2>
		</div>
	</s:if>
	<s:if test="${editType=='update' }">
		<div id="crash_menu">
			<h2 align="center">修改自留额计划</h2>
		</div>
	</s:if>
	<s:if test="${editType=='view' }">
		<div id="crash_menu">
			<h2 align="center">查看自留额计划</h2>
		</div>
	</s:if>
</tr>       
<s:if test="${editType=='insert' }">
	<tr class="top">
	<td class="bgc_tt short">业务年度<font color="red">*</font></td>
	<td class="long">
		<s:textfield name="prpDtreatyReten.id.uwYear" id="uwYear" cssClass="input_w w_15 dc-chk dt-plusnum" maxlength="4"/>
	</td>
	<td class="bgc_tt short">序号<font color="red">*</font></td>
	<td class="long">
		<s:textfield name="prpDtreatyReten.id.serialNo" id="serialNo" cssClass="input_w w_15 dc-chk dt-plusnum" maxlength="4" />
	</td> 	
	</tr>
	<tr>
	<td class="bgc_tt short">险类代码<font color="red">*</font></td>
	<td class="long">
	
		<ct:select name="prpDtreatyReten.id.classCode" id="classCode" 
			cssClass="selectui-input-up input_y w_p90 dc-chk dt-nzhs" sysCode="DMS" 
			value="${prpDtreatyReten.id.classCName}" codeType="PrpDclass">
		</ct:select>
	</td>
	<td class="bgc_tt short">险种代码<font color="red">*</font></td>
	<td class="long">
		<s:textfield name="prpDtreatyReten.id.riskCode" id="riskCode" cssClass="input_w w_15 dc-chk dt-nzhs" maxlength="40" />
	</td>
	     
	</tr>
	<tr>
	<td class="bgc_tt short">风险类别<font color="red">*</font></td>
	<td class="long">
		<s:textfield name="prpDtreatyReten.riskClass" id="riskClass" cssClass="input_w w_15 dc-chk dt-nzhs" maxlength="20" />
	</td>
	<td class="bgc_tt short">风险类别描述</td>
	<td class="long">
		<s:textfield name="prpDtreatyReten.riskClassDesc" id="riskClassDesc" cssClass="input_w w_15" maxlength="127" />
	</td>     
	</tr>
	<tr>       
	<td class="bgc_tt short">风险等级<font color="red">*</font></td>
	<td class="long">
		<s:textfield name="prpDtreatyReten.riskLevel" id="riskLevel" cssClass="input_w w_15 dc-chk dt-nzhs" maxlength="4" />
	</td>  
	<td class="bgc_tt short">风险等级描述</td>
	<td class="long">
		<s:textfield name="prpDtreatyReten.riskLevelDesc" id="riskLevelDesc" cssClass="input_w w_15" maxlength="127" />
	</td>   
	</tr>
	<tr>
	<td class="bgc_tt short">行业类型/销售区域（司法管辖）/产品性质</td>
	<td class="long">
		<s:textfield name="prpDtreatyReten.business" id="business" cssClass="input_w w_15" maxlength="10" />
	</td>
	<td class="bgc_tt short">行业类型描述</td>
	<td class="long">
		<s:textfield name="prpDtreatyReten.businessDesc" id="businessDesc" cssClass="input_w w_15" maxlength="127" />
	</td>  
	</tr>
	<tr>
	<td class="bgc_tt short">业务质量上限</td>
	<td class="long">
		<s:textfield name="prpDtreatyReten.upperLimit" id="upperLimit" cssClass="input_w w_15" maxlength="30" />
	</td>
	<td class="bgc_tt short">业务质量下限</td>
	<td class="long">
		<s:textfield name="prpDtreatyReten.lowerLimit" id="lowerLimit" cssClass="input_w w_15" maxlength="30" />
	</td>  
	</tr>
			
	<tr>
	<td class="bgc_tt short">评分级别<font color="red">*</font></td>
	<td class="long">
		<s:textfield name="prpDtreatyReten.grade" id="grade" cssClass="input_w w_15 dc-chk dt-nzhs" maxlength="3" />
	</td>
	<td class="bgc_tt short">币别</td>
	<td class="long">
		<s:textfield name="prpDtreatyReten.currency" id="currency" cssClass="input_w w_15 dt-currentcy" maxlength="3" />
	</td>  
	</tr>
	<tr>
	<td class="bgc_tt short">自留额</td>
	<td class="long">
		<s:textfield name="prpDtreatyReten.retentionValue" id="retentionValue" cssClass="input_w w_15 dt-retentionValue" maxlength="15" />
	</td>
	<td class="bgc_tt short">自留比例(%)</td>
	<td class="long">
		<s:textfield name="prpDtreatyReten.retentionRate" id="retentionRate" cssClass="input_w w_15 dt-retentionRate" maxlength="10" />
	</td>  
	</tr>
	<tr>
	<td class="bgc_tt short">起始日期</td>
	<td class="long">
		<s:textfield name="prpDtreatyReten.startDate" id="startDate" value="${prpDtreatyReten.startDate}" 
				cssClass="input_w w_30 Wdate" onfocus="WdatePicker()" />
	</td>
	<td class="bgc_tt short">终止日期</td>
	<td class="long">
		<s:textfield name="prpDtreatyReten.endDate" id="endDate" value="${prpDtreatyReten.endDate}" 
				cssClass="input_w w_30 Wdate" onfocus="WdatePicker()"/>
	</td>  
	</tr>
	<tr>
	<td class="bgc_tt short">合同限额</td>
	<td class="long">
		<s:textfield name="prpDtreatyReten.limitValue" id="limitValue" cssClass="input_w w_15 dt-retentionValue" maxlength="15" />
	</td>
	<td class="bgc_tt short">业务标志</td>
	<td>
		<s:select name="prpDtreatyReten.flag" id="flag" disabled="false"
          	list="#@java.util.HashMap@{'0':'商业分出自留信息','3':'协议分出自留信息'}"/>
    </td>
	</tr>
	<tr>
	<td class="bgc_tt short">备注</td>
	<td class="long">
		<s:textfield name="prpDtreatyReten.remarks" id="remarks" cssClass="input_w w_15" maxlength="30" />
	</td> 
	<td class="bgc_tt short"></td>
	<td class="long">
		<s:hidden name="prpDtreatyReten.retenFlag" id="retenFlag" value="0"/>
	</td>   
	</tr>
</s:if>
<s:elseif test="${editType=='update' }">
<tr class="top">
	<td class="bgc_tt short">业务年度<font color="red">*</font></td>
	<td class="long">
		<s:textfield name="prpDtreatyReten.id.uwYear" id="uwYear" readonly="true" 
			cssClass="input_w w_15 dc-chk dt-plusnum" maxlength="4"/>
	</td>
	<td class="bgc_tt short">序号<font color="red">*</font></td>
	<td class="long">
		<s:textfield name="prpDtreatyReten.id.serialNo" id="serialNo" readonly="true"
			cssClass="input_w w_15 dc-chk dt-plusnum" maxlength="40" />
	</td>  
	</tr>
	<tr>
	<td class="bgc_tt short">险类代码<font color="red">*</font></td> 
	<td class="long">
		<s:textfield name="prpDtreatyReten.id.classCode" id="classCode" value="${prpDtreatyReten.id.classCode}"
			cssClass="input_w w_15" readonly="true">
		</s:textfield>
	</td>
	<td class="bgc_tt short">险种代码<font color="red">*</font></td>
	<td class="long">
		<s:textfield name="prpDtreatyReten.id.riskCode" id="riskCode" readonly="true" 
			cssClass="input_w w_15 dc-chk dt-nzhs" maxlength="4" />
	</td>
	</tr>
	<tr>
	<td class="bgc_tt short">风险类别<font color="red">*</font></td>
	<td class="long">
		<s:textfield name="prpDtreatyReten.riskClass" id="riskClass" cssClass="input_w w_15 dc-chk dt-nzhs" maxlength="20" />
	</td>
	<td class="bgc_tt short">风险类别描述</td>
	<td class="long">
		<s:textfield name="prpDtreatyReten.riskClassDesc" id="riskClassDesc" cssClass="input_w w_15" maxlength="127" />
	</td>     
	</tr>
	<tr>       
	<td class="bgc_tt short">风险等级<font color="red">*</font></td>
	<td class="long">
		<s:textfield name="prpDtreatyReten.riskLevel" id="riskLevel" cssClass="input_w w_15 dc-chk dt-nzhs" maxlength="4" />
	</td>  
	<td class="bgc_tt short">风险等级描述</td>
	<td class="long">
		<s:textfield name="prpDtreatyReten.riskLevelDesc" id="riskLevelDesc" cssClass="input_w w_15" maxlength="127" />
	</td>   
	</tr>
	<tr>
	<td class="bgc_tt short">行业类型/销售区域（司法管辖）/产品性质</td>
	<td class="long">
		<s:textfield name="prpDtreatyReten.business" id="business" cssClass="input_w w_15" maxlength="10" />
	</td>
	<td class="bgc_tt short">行业类型描述</td>
	<td class="long">
		<s:textfield name="prpDtreatyReten.businessDesc" id="businessDesc" cssClass="input_w w_15" maxlength="127" />
	</td>  
	</tr>
	<tr>
	<td class="bgc_tt short">业务质量上限</td>
	<td class="long">
		<s:textfield name="prpDtreatyReten.upperLimit" id="upperLimit" cssClass="input_w w_15" maxlength="30" />
	</td>
	<td class="bgc_tt short">业务质量下限</td>
	<td class="long">
		<s:textfield name="prpDtreatyReten.lowerLimit" id="lowerLimit" cssClass="input_w w_15" maxlength="30" />
	</td>  
	</tr>
			
	<tr>
	<td class="bgc_tt short">评分级别<font color="red">*</font></td>
	<td class="long">
		<s:textfield name="prpDtreatyReten.grade" id="grade" cssClass="input_w w_15 dc-chk dt-nzhs" maxlength="3" />
	</td>
	<td class="bgc_tt short">币别</td>
	<td class="long">
		<s:textfield name="prpDtreatyReten.currency" id="currency" cssClass="input_w w_15 dt-currentcy" maxlength="3" />
	</td>  
	</tr>
	<tr>
	<td class="bgc_tt short">自留额</td>
	<td class="long">
		<s:textfield name="prpDtreatyReten.retentionValue" id="retentionValue" cssClass="input_w w_15 dt-retentionValue" maxlength="15" />
	</td>
	<td class="bgc_tt short">自留比例(%)</td>
	<td class="long">
		<s:textfield name="prpDtreatyReten.retentionRate" id="retentionRate" cssClass="input_w w_15 dt-retentionRate" maxlength="10" />
	</td>  
	</tr>
	<tr>
	<td class="bgc_tt short">起始日期</td>
	<td class="long">
		<s:textfield name="prpDtreatyReten.startDate" id="startDate" value="${prpDtreatyReten.startDate}" 
				cssClass="input_w w_30 Wdate" onfocus="WdatePicker()" />
	</td>
	<td class="bgc_tt short">终止日期</td>
	<td class="long">
		<s:textfield name="prpDtreatyReten.endDate" id="endDate" value="${prpDtreatyReten.endDate}" 
				cssClass="input_w w_30 Wdate" onfocus="WdatePicker()"/>
	</td>  
	</tr>
	<tr>
	<td class="bgc_tt short">合同限额</td>
	<td class="long">
		<s:textfield name="prpDtreatyReten.limitValue" id="limitValue" cssClass="input_w w_15 dt-retentionValue" maxlength="15" />
	</td>
	<td class="bgc_tt short">业务标志</td>
	<td>
		<s:select name="prpDtreatyReten.flag" id="flag" disabled="false"
          	list="#@java.util.HashMap@{'0':'商业分出自留信息','3':'协议分出自留信息'}"/>
    </td>
	</tr>
	<tr>
	<td class="bgc_tt short">备注</td>
	<td class="long">
		<s:textfield name="prpDtreatyReten.remarks" id="remarks" cssClass="input_w w_15" maxlength="30" />
	</td>
	<td class="long">
		<s:hidden name="prpDtreatyReten.retenFlag" id="retenFlag" value="0"/>
	</td>
	 
</tr>       
</s:elseif>
<s:elseif test="${editType=='view'}">
<tr class="top">
	<td class="bgc_tt short">业务年度<font color="red">*</font></td>
	<td class="long">
		<s:textfield name="prpDtreatyReten.id.uwYear" id="uwYear" readonly="true" cssClass="input_w w_15" maxlength="4"/>
	</td>
	<td class="bgc_tt short">序号<font color="red">*</font></td>
	<td class="long">
		<s:textfield name="prpDtreatyReten.id.serialNo" id="serialNo" readonly="true" disabled="true"
			cssClass="input_w w_15" maxlength="4" />
	</td>   
	</tr>
	<tr>
	<td class="bgc_tt short">险类代码<font color="red">*</font></td>
	<td class="long">
		<s:textfield name="prpDtreatyReten.id.classCode" id="classCode" value="${prpDtreatyReten.id.classCode}"
			cssClass="input_w w_15" disabled="true">
		</s:textfield>
	</td>	
	<td class="bgc_tt short">险种代码<font color="red">*</font></td>
	<td class="long">
		<s:textfield name="prpDtreatyReten.id.riskCode" id="riskCode" readonly="true" disabled="true"
			cssClass="" maxlength="40" />
	</td>
	   
	</tr>
	<tr>
	<td class="bgc_tt short">风险类别<font color="red">*</font></td>
	<td class="long">
		<s:textfield name="prpDtreatyReten.riskClass" id="riskClass" readonly="true" disabled="true"
			cssClass="input_w w_15 dc-chk dt-nzhs" maxlength="20" />
	</td>
	<td class="bgc_tt short">风险类别描述</td>
	<td class="long">
		<s:textfield name="prpDtreatyReten.riskClassDesc" id="riskClassDesc" readonly="true" disabled="true"
			cssClass="input_w w_15" maxlength="127" />
	</td>     
	</tr>
	<tr>       
	<td class="bgc_tt short">风险等级<font color="red">*</font></td>
	<td class="long">
		<s:textfield name="prpDtreatyReten.riskLevel" id="riskLevel" readonly="true" disabled="true"
			cssClass="input_w w_15 dc-chk dt-nzhs" maxlength="4" />
	</td>  
	<td class="bgc_tt short">风险等级描述</td>
	<td class="long">
		<s:textfield name="prpDtreatyReten.riskLevelDesc" id="riskLevelDesc" readonly="true" disabled="true"
			cssClass="input_w w_15" maxlength="127" />
	</td>   
	</tr>
	<tr>
	<td class="bgc_tt short">行业类型/销售区域（司法管辖）/产品性质</td>
	<td class="long">
		<s:textfield name="prpDtreatyReten.business" id="business" readonly="true" disabled="true"
			cssClass="input_w w_15" maxlength="10" />
	</td>
	<td class="bgc_tt short">行业类型描述</td>
	<td class="long">
		<s:textfield name="prpDtreatyReten.businessDesc" id="businessDesc" readonly="true" disabled="true"
			cssClass="input_w w_15" maxlength="127" />
	</td>  
	</tr>
	<tr>
	<td class="bgc_tt short">业务质量上限</td>
	<td class="long">
		<s:textfield name="prpDtreatyReten.upperLimit" id="upperLimit" readonly="true" disabled="true"
			cssClass="input_w w_15" maxlength="30" />
	</td>
	<td class="bgc_tt short">业务质量下限</td>
	<td class="long">
		<s:textfield name="prpDtreatyReten.lowerLimit" id="lowerLimit" readonly="true" disabled="true"
			cssClass="input_w w_15" maxlength="30" />
	</td>  
	</tr>
			
	<tr>
	<td class="bgc_tt short">评分级别<font color="red">*</font></td>
	<td class="long">
		<s:textfield name="prpDtreatyReten.grade" id="grade" readonly="true" disabled="true"
			cssClass="input_w w_15 dc-chk dt-nzhs" maxlength="3" />
	</td>
	<td class="bgc_tt short">币别</td>
	<td class="long">
		<s:textfield name="prpDtreatyReten.currency" id="currency" readonly="true" disabled="true"
			cssClass="input_w w_15" maxlength="3" />
	</td>  
	</tr>
	<tr>
	<td class="bgc_tt short">自留额</td>
	<td class="long">
		<s:textfield name="prpDtreatyReten.retentionValue" id="retentionValue" readonly="true" disabled="true"
			cssClass="input_w w_15" maxlength="30" />
	</td>
	<td class="bgc_tt short">自留比例(%)</td>
	<td class="long">
		<s:textfield name="prpDtreatyReten.retentionRate" id="retentionRate" readonly="true" disabled="true"
			cssClass="input_w w_15 dt-num" maxlength="30" />
	</td>  
	</tr>
	<tr>
	<td class="bgc_tt short">起始日期</td>
	<td class="long">
		<s:textfield name="prpDtreatyReten.startDate" id="startDate" readonly="true" disabled="true"
				value="${prpDtreatyReten.startDate}" 
				cssClass="input_w w_30 Wdate" onfocus="WdatePicker()" />
	</td>
	<td class="bgc_tt short">终止日期</td>
	<td class="long">
		<s:textfield name="prpDtreatyReten.endDate" id="endDate" readonly="true" disabled="true"
				value="${prpDtreatyReten.endDate}" 
				cssClass="input_w w_30 Wdate" onfocus="WdatePicker()"/>
	</td>  
	</tr>
	<tr>
	<td class="bgc_tt short">合同限额</td>
	<td class="long">
		<s:textfield name="prpDtreatyReten.limitValue" id="limitValue" readonly="true" disabled="true"
			cssClass="input_w w_15 dt-zhs" maxlength="30" />
	</td>
	<td class="bgc_tt short">业务标志</td>
	<td>
		<s:select name="prpDtreatyReten.flag" id="flag" disabled="true"
          	list="#@java.util.HashMap@{'0':'商业分出自留信息','3':'协议分出自留信息'}"/>
    </td>
	</tr>
	<tr>
	<td class="bgc_tt short">备注</td>
	<td class="long">
		<s:textfield name="prpDtreatyReten.remarks" id="remarks" readonly="true" disabled="true"
			cssClass="input_w w_15" maxlength="30" />
	</td>
	<td class="long">
		<s:hidden name="prpDtreatyReten.retenFlag" id="retenFlag" value="0"/>
	</td>
</tr>
</s:elseif>
</table> 
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr align="center" class="top">
		<c:if test="${editType=='view' }">
			<td>
			<% if(SyncConstants.ComCode_Head.equals(deployCom)){%>
			<button type="button" value=""  onclick="prepareUpdate()"><span><em>修改</em></span></button>
<!--			<input type="button" value="修改" class="button_ty" onclick="prepareUpdate()">-->
			<%}%>
			</td>
		</c:if>
		<c:if test="${editType=='insert' }">
			<td>
			<button type="button" value=""  onclick="return addMethod()"><span><em>保存</em></span></button>
<!--			<input type="button" value="保存" class="button_ty" onclick="return addMethod()">-->
			</td>
		</c:if>
		<c:if test="${editType=='update' }">
			<td>
			<button type="button" value=""  onclick="updateMethod()"><span><em>保存</em></span></button>
<!--			<input type="button" value="保存" class="button_ty" onclick="updateMethod()">-->
			</td>
		</c:if>
	</tr>
</table>
</s:form>
</div>
</div>
</body>
</html>
<%@ include file="/common/meta_js.jsp"%>
<script language="javascript" src="${ctx}/common/js/newCalendar.js"></script>
<script language="javascript" src="${ctx}/common/dwr/engine.js"></script>
<script language="javascript" src="${ctx}/common/dwr/util.js"></script>
<script language="javascript" src="${ctx}/widgets/yui/autocomplete/autocomplete-min.js"></script>
<script language="javascript" src="${ctx}/common/js/CodeSelect.js"></script>
<script language="javascript" src="${ctx}/common/js/StaticJavascript.jsp"></script>
<script type="text/javascript">
    
	function updateMethod(){
	if(YAHOO.quote.data.datacheck('fm') ){   
      if(checkLen()){
	    fm.action="${ctx}/dictionary/updatePrpDTreatyReten.do";
	    fm.submit();
	   	 }	
    	}
	}
	
	function addMethod(){
		var flag = document.getElementById("flag");
		if(YAHOO.quote.data.datacheck('fm') ){
			hasSameKey();
		}else{
			 alert("界面输入有误，请核实！");
		}
	}
	function prepareUpdate(){//客户需求，查看页面点击修改要进入修改页面。2009-10-21
		var uwYear    = document.getElementById("uwYear").value;
		var classCode = document.getElementById("classCode").value;
		var riskCode  = document.getElementById("riskCode").value;
		var serialNo  = document.getElementById("serialNo").value;
		editRecord("${ctx}/dictionary/prepareUpdatePrpDTreatyReten.do?prpDtreatyReten.id.uwYear="+uwYear+"&prpDtreatyReten.id.classCode="+classCode+"&prpDtreatyReten.id.riskCode="+riskCode+"&prpDtreatyReten.id.serialNo="+serialNo+"&editType=update");
		window.close();
	}
	function hasSameKey(){//多主键校验！
	var key1 = document.getElementById("uwYear").value;
	var key2 = document.getElementById("classCode").value;
    var key3 = document.getElementById("riskCode").value;
	var key4 = document.getElementById("serialNo").value;
	var url="${ctx}/dictionary/isSameKeys.do?tableName=PrpDtreatyReten&values1=id.uwYear\='"+key1+"'^id.classCode\='"+key2+"'^id.riskCode\='"+key3+"'^id.serialNo\="+key4+"";
	var handleSuccess = function(o){
		if(o.responseText=="sameKey"){
			alert("该新增代码已存在！");
		}else{
			fm.action="${ctx}/dictionary/insertPrpDTreatyReten.do";
			fm.submit();		
		}
	};
	var handleFailure = function(o){
		if(o.responseText !== undefined){
			var msg = i18n.errors.updatefail+"!\n"+ o.status +" " + o.statusText;
			alert(msg);
			return true;
		}
	};	
	var callback =
	{
	  success:handleSuccess,
	  failure:handleFailure
	};
	var req = YAHOO.util.Connect.asyncRequest('POST', url, callback, "");
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




