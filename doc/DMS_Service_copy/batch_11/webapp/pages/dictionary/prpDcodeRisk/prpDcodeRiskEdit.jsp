<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>险类代码</title>
<%@include file="/common/meta_css.jsp"%>
<%@ include file="/common/i18njs.jsp"%>
<%@ include file="/common/meta_js.jsp"%>
<%-- moidfy  update by tongziliang 2011-10-09 reason:修改页面的按钮样式和优化页面样式 --%>
<link rel="stylesheet" href="${ctx}/style/popup/login.css" type="text/css"></link>
<script language="javascript" src="${ctx}/common/js/newCalendar.js"></script>
<script language="javascript" src="${ctx}/common/dwr/engine.js"></script>
<script language="javascript" src="${ctx}/common/dwr/util.js"></script>
<script language="javascript" src="${ctx}/widgets/yui/autocomplete/autocomplete-min.js"></script>
<script language="javascript" src="${ctx}/common/js/CodeSelect.js"></script>
<script language="javascript" src="${ctx}/common/js/StaticJavascript.jsp"></script>
<script type="text/javascript">
	function updateMethod(){
		fm.action="${ctx}/dictionary/updatePrpDcodeRisk.do";
		fm.submit();
	}
	function addMethod(){
		if(YAHOO.quote.data.datacheck('fm') ){
			fm.action="${ctx}/dictionary/insertPrpDcodeRisk.do";
			fm.submit();
		}else{
			 alert("界面输入有误，请核实！");
		}
	}
	function prepareUpdate(){//客户需求，查看页面点击修改要进入修改页面。2009-10-21
		var riskCode    = document.getElementById("riskCode").value;
		editRecord("${ctx}/dictionary/prepareUpdatePrpDcodeRisk.do?prpDcodeRisk.id.riskCode="+riskCode+"&editType=update");
		window.close();
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
</head>
<!--  onkeydown方法禁用BackSpace onload方法光标定位在首个输入框 -->
<body id="all_title" onkeydown="keyDown()" onload="fm.riskCode.focus()">
<div id="wrapper">
<div id="container">
<s:form name="fm" action="${ctx}/dictionary/insertPrpDcodeRisk.do" method="post">
<s:hidden name="editType" id="editType" value="${editType}"></s:hidden>
<!--  
<s:hidden name="prpDtreatyReten.flag" id="flag" value="${prpDtreatyReten.flag}"></s:hidden>
-->
<table width="100%" class="fix_table">
<tr class="top">
	<s:if test="${editType=='insert' }">
		<div id="crash_menu">
			<h2 align="center">增加险类代码</h2>
		</div>
	</s:if>
	<s:if test="${editType=='update' }">
		<div id="crash_menu">
			<h2 align="center">修改险类代码</h2>
		</div>
	</s:if>
	<s:if test="${editType=='view' }">
		<div id="crash_menu">
			<h2 align="center">查看险类代码</h2>
		</div>
	</s:if>
</tr>       
<s:if test="${editType=='insert' }">
	<tr>
		<td class="bgc_tt short">险种代码<font color="red">*</font></td>			
		<td class="long">
			<s:textfield name="prpDcodeRisk.id.riskCode" id="riskCode" cssClass="input_w w_15 dc-chk dt-nzhs" maxlength="" />
		</td>
		<td class="bgc_tt short">代码类型</td>
		<td class="long">
			<s:textfield name="prpDcodeRisk.id.codeType" id="codeType" cssClass="input_w w_15" maxlength="30" />
		</td>
	</tr>  
	<tr>
		<td class="bgc_tt short">业务代码</td>			
		<td class="long">
			<s:textfield name="prpDcodeRisk.id.codeCode" id="codeCode" cssClass='input_w w_15' maxlength="" />
		</td>
	</tr>
</s:if>
<s:elseif test="${editType=='update' }">
	<tr>
		<td class="bgc_tt short">险种代码</td>			
		<td class="long">
			<s:textfield name="prpDcodeRisk.id.riskCode" id="riskCode" cssClass="input_w w_15" readonly="true" maxlength="" />
		</td>
		<td class="bgc_tt short">代码类型</td>
		<td class="long">
			<s:textfield name="prpDcodeRisk.id.codeType" id="codeType" cssClass="input_w w_15" maxlength="30" />
		</td>
	</tr>  
	<tr>
		<td class="bgc_tt short">业务代码</td>			
		<td class="long">
			<s:textfield name="prpDcodeRisk.id.codeCode" id="codeCode" cssClass='input_w w_15' maxlength="" />
		</td>
	</tr>
</s:elseif>
<s:elseif test="${editType=='view'}">
	<tr>
		<td class="bgc_tt short">险种代码<font color="red">*</font></td>			
		<td class="long">
			<s:textfield name="prpDcodeRisk.id.riskCode" id="riskCode" cssClass="input_w w_15 dc-chk dt-nzhs" 
				readonly="true" disabled="true" maxlength="" />
		</td>
		<td class="bgc_tt short">代码类型</td>
		<td class="long">
			<s:textfield name="prpDcodeRisk.id.codeType" id="codeType" cssClass="input_w w_15" 
				readonly="true" disabled="true" maxlength="30" />
		</td>
	</tr>  
	<tr>
		<td class="bgc_tt short">业务代码</td>			
		<td class="long">
			<s:textfield name="prpDcodeRisk.id.codeCode" id="codeCode" cssClass='input_w w_15'
				readonly="true" disabled="true"  maxlength="" />
		</td>
	</tr>
</s:elseif>
</table> 
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr align="center" class="top">
		<c:if test="${editType=='view' }">
			<td>
			<button type="button" value="" onclick="prepareUpdate()"><span><em>修改</em></span></button>
<!--			<input type="button" value="修改" class="button_ty" onclick="prepareUpdate()">-->
			</td>
		</c:if>
		<c:if test="${editType=='insert' }">
			<td>
			<button type="button" value="" onclick="return addMethod()"><span><em>保存</em></span></button>
<!--			<input type="button" value="保存" class="button_ty" onclick="return addMethod()">-->
			</td>
		</c:if>
		<c:if test="${editType=='update' }">
			<td>
			<buttontype="button" value="" onclick="updateMethod()"><span><em>保存</em></span></button>
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


