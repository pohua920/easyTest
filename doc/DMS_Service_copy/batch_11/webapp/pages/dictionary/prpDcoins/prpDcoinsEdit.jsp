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
<title>共保体代码</title>
<%@include file="/common/meta_css.jsp"%>
<%@ include file="/common/i18njs.jsp"%>

</head>
<!--  onkeydown方法禁用BackSpace onload方法光标定位在首个输入框 -->
<body id="all_title" onkeydown="keyDown()" onload="fm.comCode.focus()">
<div id="wrapper">
<div id="container">
<s:form name="fm" action="${ctx}/dictionary/insertPrpDcoins.do" method="post">
<s:hidden name="editType" id="editType" value="${editType}"></s:hidden>
<s:hidden name="deployCom" id="deployCom" value="${deployCom}"></s:hidden>
<!--  
<s:hidden name="prpDtreatyReten.flag" id="flag" value="${prpDtreatyReten.flag}"></s:hidden>
-->
<table width="100%" class="fix_table">
<tr class="top">
	<s:if test="${editType=='insert' }">
		<div id="crash_menu">
			<h2 align="center">增加共保体代码</h2>
		</div>
	</s:if>
	<s:if test="${editType=='update' }">
		<div id="crash_menu">
			<h2 align="center">修改共保体代码</h2>
		</div>
	</s:if>
	<s:if test="${editType=='view' }">
		<div id="crash_menu">
			<h2 align="center">查看共保体代码</h2>
		</div>
	</s:if>
</tr>       
<s:if test="${editType=='insert' }">
	<tr>
		<td class="bgc_tt short">单位代码<font color="red">*</font></td>			
		<td class="long">
                <input  name="prpDcoins.id.comCode" id="comCode"
				Class='input_y w_15' maxlength="8"
				ondblclick="code_CodeQuery(this, 'ComCode', '0', 'Y','')"
				onkeyup="code_CodeQuery(this, 'ComCode', '0', 'Y','')"
				onchange="code_CodeChange(this, 'ComCode', '0', 'Y','')" />
		</td> 
		<td class="bgc_tt short">险种代码<font color="red">*</font></td>
		<td class="long">
			<s:textfield name="prpDcoins.id.riskCode" id="riskCode" cssClass="input_w w_15 dc-chk dt-nzhs" maxlength="3" />
		</td>
	</tr>
	<tr>
		<td class="bgc_tt short">批次<font color="red">*</font></td>			
		<td class="long">
		<s:textfield name="prpDcoins.id.period" id="period" cssClass='input_w w_15 dc-chk dt-plusnum' maxlength="4" />
		</td>
		<td class="bgc_tt short">共保身份<font color="red">*</font></td>
		<td class="long">
			<s:select name="prpDcoins.coinsType" id="coinsType" cssClass="input_w w_15 dc-chk" 
			list="#@java.util.HashMap@{'1':'我方','2':'系统内他方','3':'系统外他方'}" /> 
		</td>
	</tr>
	<tr>
		<td class="bgc_tt short">共保体单位代码<font color="red">*</font></td>			
		<td class="long">
		<s:textfield name="prpDcoins.id.coinsComCode" id="coinsComCode" cssClass='input_w w_15 dc-chk dt-nzhs' maxlength="20" />
		</td>
		<td class="bgc_tt short">共保体单位名称<font color="red">*</font></td>
		<td class="long">
			<s:textfield name="prpDcoins.coinsComName" id="coinsComName" cssClass="input_w w_15 dc-chk" maxlength="30" />
		</td>
	</tr>
	<tr>
		<td class="bgc_tt short">共保比例<font color="red">*</font></td>			
		<td class="long">
		<s:textfield name="prpDcoins.coinsRate" id="coinsRate" cssClass='input_w w_15 dc-chk dt-mrate' maxlength="9" />
		</td>
		<td class="bgc_tt short">启用日期<font color="red">*</font></td>
		<td class="long">
			<s:textfield name="prpDcoins.validDate" id="validDate" cssClass="input_w w_15 Wdate dc-chk" value="${prpDcoins.validDate}"
			             onfocus="WdatePicker()" />
		</td>
	</tr>
	<tr>
		<td class="bgc_tt short">有效状态<font color="red">*</font></td>			
		<td class="long">
			<ct:select name="prpDcoins.validStatus" id="validStatus" value="1"
			sysCode="DMS" codeType="ValidStatus"></ct:select>
		</td>
		<td class="bgc_tt short">标志字段</td>
		<td class="long">
			<s:textfield name="prpDcoins.flag" id="flag" cssClass="input_w w_15" maxlength="30" />
		</td>
	</tr>              
	
</s:if>
<s:elseif test="${editType=='update' }">
	<tr>
		<td class="bgc_tt short">单位代码<font color="red">*</font></td>			
		<td class="long">
		<s:textfield name="prpDcoins.id.comCode" id="comCode" cssClass='input_w w_15 dc-chk dt-nzhs' readonly="true" maxlength="" />
		</td>
		<td class="bgc_tt short">险种代码<font color="red">*</font></td>
		<td class="long">
			<s:textfield name="prpDcoins.id.riskCode" id="riskCode" cssClass="input_w w_15 dc-chk dt-nzhs" readonly="true" maxlength="3" />
		</td>
	</tr>
	<tr>
		<td class="bgc_tt short">批次<font color="red">*</font></td>			
		<td class="long">
		<s:textfield name="prpDcoins.id.period" id="period" cssClass='input_w w_15 dc-chk dt-plusnum' readonly="true" maxlength="4" />
		</td>
		<td class="bgc_tt short">共保体单位代码<font color="red">*</font></td>			
		<td class="long">
		<s:textfield name="prpDcoins.id.coinsComCode" id="coinsComCode" cssClass='input_w w_15 dc-chk' readonly="true" maxlength="20" />
		</td>
		
	</tr>
	<tr>
		<td class="bgc_tt short">共保身份<font color="red">*</font></td>
		<td class="long">
			<s:select name="prpDcoins.coinsType" id="coinsType" cssClass="input_w w_15 dc-chk" 
			list="#@java.util.HashMap@{'1':'我方','2':'系统内他方','3':'系统外他方'}" /> 
		</td>
		<td class="bgc_tt short">共保体单位名称<font color="red">*</font></td>
		<td class="long">
			<s:textfield name="prpDcoins.coinsComName" id="coinsComName" cssClass="input_w w_15 dc-chk" maxlength="30" />
		</td>
	</tr>
	<tr>
		<td class="bgc_tt short">共保比例<font color="red">*</font></td>			
		<td class="long">
		<s:textfield name="prpDcoins.coinsRate" id="coinsRate" cssClass='input_w w_15 dc-chk dt-mrate' maxlength="9" />
		</td>
		<td class="bgc_tt short">启用日期<font color="red">*</font></td>
		<td class="long">
			<s:textfield name="prpDcoins.validDate" id="validDate" cssClass="input_w w_15 Wdate dc-chk" value="${prpDcoins.validDate}"
			             onfocus="WdatePicker()" />
		</td>
	</tr>
	<tr>
		<td class="bgc_tt short">有效状态<font color="red">*</font></td>			
		<td class="long">
			<ct:select name="prpDcoins.validStatus" id="validStatus" value="${prpDcoins.validStatus}"
			sysCode="DMS" codeType="ValidStatus"></ct:select>
		</td>
		<td class="bgc_tt short">标志字段</td>
		<td class="long">
			<s:textfield name="prpDcoins.flag" id="flag" cssClass="input_w w_15" maxlength="30" />
		</td>
	</tr>                                      	
</s:elseif>
<s:elseif test="${editType=='view'}">
	<tr>
		<td class="bgc_tt short">单位代码<font color="red">*</font></td>			
		<td class="long">
		<s:textfield name="prpDcoins.id.comCode" id="comCode" cssClass='input_w w_15' 
			maxlength="" readonly="true"/>
		</td>
		<td class="bgc_tt short">险种代码<font color="red">*</font></td>
		<td class="long">
			<s:textfield name="prpDcoins.id.riskCode" id="riskCode" cssClass="input_w w_15" 
				maxlength="" readonly="true"/>
		</td>
	</tr>
	<tr>
		<td class="bgc_tt short">批次<font color="red">*</font></td>			
		<td class="long">
		<s:textfield name="prpDcoins.id.period" id="period" cssClass='input_w w_15' 
			maxlength="4" readonly="true"/>
		</td>
		<td class="bgc_tt short">共保身份<font color="red">*</font></td>
		<td class="long">
			<s:textfield name="prpDcoins.coinsType" id="coinsType" cssClass="input_w w_15" 
				maxlength="" readonly="true"/>
		</td>
	</tr>
	<tr>
		<td class="bgc_tt short">共保体单位代码<font color="red">*</font></td>			
		<td class="long">
		<s:textfield name="prpDcoins.id.coinsComCode" id="coinsComCode" cssClass='input_w w_15' 
			maxlength="" readonly="true"/>
		</td>
		<td class="bgc_tt short">共保体单位名称<font color="red">*</font></td>
		<td class="long">
			<s:textfield name="prpDcoins.coinsComName" id="coinsComName" cssClass="input_w w_15" 
				maxlength="30" readonly="true"/>
		</td>
	</tr>
	<tr>
		<td class="bgc_tt short">共保比例<font color="red">*</font></td>			
		<td class="long">
		<s:textfield name="prpDcoins.coinsRate" id="coinsRate" cssClass='input_w w_15' 
			maxlength="" readonly="true"/>
		</td>
		<td class="bgc_tt short">启用日期<font color="red">*</font></td>
		<td class="long">
			<s:textfield name="prpDcoins.validDate" id="validDate" cssClass="input_w w_15 Wdate" value="${prpDcoins.validDate}"
			             onfocus="WdatePicker()" readonly="true" disabled="true"/>
		</td>
	</tr>
	<tr>
		<td class="bgc_tt short">有效状态<font color="red">*</font></td>			
		<td class="long">
			<ct:select name="prpDcoins.validStatus" id="validStatus" disabled="true" value="${prpDcoins.validStatus}"
			sysCode="DMS" codeType="ValidStatus"></ct:select>
		</td>
		<td class="bgc_tt short">标志字段</td>
		<td class="long">
			<s:textfield name="prpDcoins.flag" id="flag" cssClass="input_w w_15" maxlength="30" readonly="true"/>
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
		<c:if test="${editType=='insert' }"><td>
		<button type="button" value=""  onclick="return addMethod()"><span><em>保存</em></span></button>
<!--			<input type="button" value="保存" class="button_ty" onclick="return addMethod()">-->
			</td>
		</c:if>
		<c:if test="${editType=='update' }"><td>
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
			fm.action="${ctx}/dictionary/updatePrpDcoins.do";
			fm.submit();
		} else {
			 alert("界面输入有误，请核实！");
		}			
	}
	function addMethod(){
		if(YAHOO.quote.data.datacheck('fm') ){
			fm.action="${ctx}/dictionary/insertPrpDcoins.do";
			fm.submit();
		}else{
			 alert("界面输入有误，请核实！");
		}
	}
	function prepareUpdate(){//客户需求，查看页面点击修改要进入修改页面。2009-10-21
		var comCode         = document.getElementById("comCode").value;
		var riskCode        = document.getElementById("riskCode").value;
		var period          = document.getElementById("period").value;
		var coinsComCode    = document.getElementById("coinsComCode").value;
		editRecord("${ctx}/dictionary/prepareUpdatePrpDcoins.do?prpDcoins.id.comCode="
		+comCode
		+ "&prpDcoins.id.riskCode="
	    + riskCode
	    + "&prpDcoins.id.period="
		+ period
		+ "&prpDcoins.id.coinsComCode="
		+ coinsComCode
		+"&editType=update");
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


