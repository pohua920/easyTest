<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>机构代码</title>
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
		fm.action="${ctx}/dictionary/updatePrpDcodeCom.do";
		fm.submit();
	}
	function addMethod(){
		if(YAHOO.quote.data.datacheck('fm') ){
			fm.action="${ctx}/dictionary/insertPrpDcodeCom.do";
			fm.submit();
		}else{
			 alert("界面输入有误，请核实！");
		}
	}
	function prepareUpdate(){//客户需求，查看页面点击修改要进入修改页面。2009-10-21
		var comCode    = document.getElementById("comCode").value;
		var codeType    = document.getElementById("codeType").value;
		var codeCode    = document.getElementById("codeCode").value;
		editRecord("${ctx}/dictionary/prepareUpdatePrpDcodeCom.do?prpDcodeCom.id.comCode="+comCode+"&prpDcodeCom.id.codeType="+codeType+"&prpDcodeCom.id.codeCode="+codeCode+"&editType=update");
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
<body id="all_title" onkeydown="keyDown()" onload="fm.comCode.focus()">
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
			<h2 align="center">增加机构代码</h2>
		</div>
	</s:if>
	<s:if test="${editType=='update' }">
		<div id="crash_menu">
			<h2 align="center">修改机构代码</h2>
		</div>
	</s:if>
	<s:if test="${editType=='view' }">
		<div id="crash_menu">
			<h2 align="center">查看机构代码</h2>
		</div>
	</s:if>
</tr>       
<s:if test="${editType=='insert' }">
	<tr>
		<td class="bgc_tt short">机构代码<font color="red">*</font></td>			
		<td class="long">
			<s:textfield name="prpDcodeCom.id.comCode" id="comCode" cssClass="input_w w_15 dc-chk dt-nzhs" maxlength="" />
		</td>
		<td class="bgc_tt short">代码类型<font color="red">*</font></td>
		<td class="long">
			<s:textfield name="prpDcodeCom.id.codeType" id="codeType" cssClass="input_w w_15 dc-chk dt-nzhs" maxlength="30" />
		</td>
	</tr> 
	<tr>
		<td class="bgc_tt short">业务代码<font color="red">*</font></td>			
		<td class="long">
			<s:textfield name="prpDcodeCom.id.codeCode" id="codeCode" cssClass="input_w w_15 dc-chk dt-nzhs" maxlength="" />
		</td>
		<td class="bgc_tt short">业务代码中文含义</td>
		<td class="long">
			<s:textfield name="prpDcodeCom.codeCName" id="codeCName" cssClass="input_w w_15" maxlength="30" />
		</td>
	</tr>
	<tr>
		<td class="bgc_tt short">业务代码英文含义</td>			
		<td class="long">
			<s:textfield name="prpDcodeCom.codeEName" id="codeEName" cssClass="input_w w_15" maxlength="" />
		</td>
		<td class="bgc_tt short">新的业务代码</td>
		<td class="long">
			<s:textfield name="prpDcodeCom.newCodeCode" id="newCodeCode" cssClass="input_w w_15" maxlength="30" />
		</td>
	</tr>
	<tr>
		<td class="bgc_tt short">有效状态</td>			
		<td class="long">
			<s:textfield name="prpDcodeCom.validStatus" id="validStatus" cssClass="input_w w_15" maxlength="" />
		</td>
		<td class="bgc_tt short">标志字段</td>
		<td class="long">
			<s:textfield name="prpDcodeCom.flag" id="flag" cssClass="input_w w_15" maxlength="30" />
		</td>
	</tr>
	<tr>
		<td class="bgc_tt short">代码值</td>			
		<td class="long">
			<s:textfield name="prpDcodeCom.codeValue" id="codeValue" cssClass="input_w w_15" maxlength="" />
		</td>
	</tr>  
</s:if>
<s:elseif test="${editType=='update' }">
	<tr>
		<td class="bgc_tt short">机构代码</td>			
		<td class="long">
			<s:textfield name="prpDcodeCom.id.comCode" id="comCode" cssClass="input_w w_15" readonly="true"/>
		</td>
		<td class="bgc_tt short">代码类型</td>
		<td class="long">
			<s:textfield name="prpDcodeCom.id.codeType" id="codeType" cssClass="input_w w_15" readonly="true"/>
		</td>
	</tr> 
	<tr>
		<td class="bgc_tt short">业务代码</td>			
		<td class="long">
			<s:textfield name="prpDcodeCom.id.codeCode" id="codeCode" cssClass="input_w w_15" readonly="true"/>
		</td>
		<td class="bgc_tt short">业务代码中文含义</td>
		<td class="long">
			<s:textfield name="prpDcodeCom.codeCName" id="codeCName" cssClass="input_w w_15" maxlength="30"/>
		</td>
	</tr>
	<tr>
		<td class="bgc_tt short">业务代码英文含义</td>			
		<td class="long">
			<s:textfield name="prpDcodeCom.codeEName" id="codeEName" cssClass="input_w w_15" maxlength="20" />
		</td>
		<td class="bgc_tt short">新的业务代码</td>
		<td class="long">
			<s:textfield name="prpDcodeCom.newCodeCode" id="newCodeCode" cssClass="input_w w_15" maxlength="30" />
		</td>
	</tr>
	<tr>
		<td class="bgc_tt short">有效状态</td>			
		<td class="long">
			<s:textfield name="prpDcodeCom.validStatus" id="validStatus" cssClass="input_w w_15" maxlength="10" />
		</td>
		<td class="bgc_tt short">标志字段</td>
		<td class="long">
			<s:textfield name="prpDcodeCom.flag" id="flag" cssClass="input_w w_15" maxlength="30" />
		</td>
	</tr>
	<tr>
		<td class="bgc_tt short">代码值</td>			
		<td class="long">
			<s:textfield name="prpDcodeCom。codeValue" id="codeValue" cssClass="input_w w_15" maxlength="10" />
		</td>
	</tr>	
</s:elseif>
<s:elseif test="${editType=='view'}">
	<tr>
		<td class="bgc_tt short">机构代码</td>			
		<td class="long">
			<s:textfield name="prpDcodeCom.id.comCode" id="comCode" cssClass="input_w w_15" 
				disabled="true" readonly="true"/>
		</td>
		<td class="bgc_tt short">代码类型</td>
		<td class="long">
			<s:textfield name="prpDcodeCom.id.codeType" id="codeType" cssClass="input_w w_15" 
				disabled="true" readonly="true"/>
		</td>
	</tr> 
	<tr>
		<td class="bgc_tt short">业务代码</td>			
		<td class="long">
			<s:textfield name="prpDcodeCom.id.codeCode" id="codeCode" cssClass="input_w w_15"  
				disabled="true" readonly="true"/>
		</td>
		<td class="bgc_tt short">业务代码中文含义</td>
		<td class="long">
			<s:textfield name="prpDcodeCom.codeCName" id="codeCName" cssClass="input_w w_15" 
				disabled="true" readonly="true"/>
		</td>
	</tr>
	<tr>
		<td class="bgc_tt short">业务代码英文含义</td>			
		<td class="long">
			<s:textfield name="prpDcodeCom.codeEName" id="codeEName" cssClass="input_w w_15" 
				disabled="true" readonly="true"/>
		</td>
		<td class="bgc_tt short">新的业务代码</td>
		<td class="long">
			<s:textfield name="prpDcodeCom.newCodeCode" id="newCodeCode" cssClass="input_w w_15" 
				disabled="true" readonly="true"/>
		</td>
	</tr>
	<tr>
		<td class="bgc_tt short">有效状态</td>			
		<td class="long">
			<s:textfield name="prpDcodeCom.validStatus" id="validStatus" cssClass="input_w w_15" 
				disabled="true" readonly="true"/>
		</td>
		<td class="bgc_tt short">标志字段</td>
		<td class="long">
			<s:textfield name="prpDcodeCom.flag" id="flag" cssClass="input_w w_15" 
				readonly="true" disabled="true"/>
		</td>
	</tr>
	<tr>
		<td class="bgc_tt short">代码值</td>			
		<td class="long">
			<s:textfield name="prpDcodeCom.codeValue" id="codeValue" cssClass="input_w w_15" 
				disabled="true" readonly="true" />
		</td>
	</tr>	
</s:elseif>
</table> 
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr align="center" class="top">
		<c:if test="${editType=='view' }">
			<td>
			<button type="button" value=""  onclick="prepareUpdate()"><span><em>修改</em></span></button>
<!--			<input type="button" value="修改" class="button_ty" onclick="prepareUpdate()">-->
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


