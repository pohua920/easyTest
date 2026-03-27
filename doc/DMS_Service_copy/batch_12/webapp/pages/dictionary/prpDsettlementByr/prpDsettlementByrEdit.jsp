<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="cn.com.sinosoft.ims.sync.SyncConstants"%>
<%
	String deployCom = (String)session.getAttribute("deployCom");
%>
<html>
<head>
<title>国管局项目一级预算单位</title>
<%@include file="/common/meta_css.jsp"%>
<%@ include file="/common/i18njs.jsp"%>

</head>
<%-- moidfy  update by tongziliang 2011-10-09 reason:修改页面的按钮样式和优化页面样式 --%>
<link rel="stylesheet" href="${ctx}/style/popup/login.css" type="text/css"></link>
<!--  onkeydown方法禁用BackSpace onload方法光标定位在首个输入框 -->
<body id="all_title" onkeydown="keyDown()" onload="fm.buyerUnitCode.focus()">
<div id="wrapper">
<div id="container">
<s:form name="fm" action="" method="post">
<s:hidden name="editType" id="editType" value="${editType}"></s:hidden>
<s:hidden name="deployCom" id="deployCom" value="${deployCom}"></s:hidden>
<!--  
<s:hidden name="prpDtreatyReten.flag" id="flag" value="${prpDtreatyReten.flag}"></s:hidden>
-->
<table width="100%" class="fix_table">
<tr class="top">
	<s:if test="${editType=='insert' }">
		<div id="crash_menu">
			<h2 align="center">增加国管局项目一级预算单位</h2>
		</div>
	</s:if>
	<s:if test="${editType=='update' }">
		<div id="crash_menu">
			<h2 align="center">修改国管局项目一级预算单位</h2>
		</div>
	</s:if>
	<s:if test="${editType=='view' }">
		<div id="crash_menu">
			<h2 align="center">查看国管局项目一级预算单位</h2>
		</div>
	</s:if>
</tr>       
<s:if test="${editType=='insert' }">
	<tr class="top">
		<td class="bgc_tt short">预算单位代码<font color="red">*</font></td>
		<td class="long">
			<s:textfield name="prpDsettlementByr.buyerUnitCode" id="buyerUnitCode" cssClass="input_w w_15 dc-chk dt-nzhs" maxlength="40"/>
		</td>
		<td class="bgc_tt short">预算单位名称<font color="red">*</font></td>
		<td class="long">
			<s:textfield name="prpDsettlementByr.buyerUnitName" id="buyerUnitName" cssClass="input_w w_15 dc-chk" maxlength="40" />
		</td> 	
	</tr>
	<tr>
		<td class="bgc_tt short">预算单位地址</td>
		<td class="long">
			<s:textfield name="prpDsettlementByr.buyerUnitAddress" id="buyerUnitAddress" cssClass="input_w w_15" maxlength="120" />
		</td>
		<td class="bgc_tt short">效力状态<font color="red">*</font></td>
		<td class="long">
			<ct:select name="prpDsettlementByr.validStatus" id="validStatus" value="1" 
			sysCode="DMS" codeType="ValidStatus"></ct:select>
		</td>
	</tr>
	<tr>
		<td class="bgc_tt short">标志字段</td>
		<td class="long">
			<s:textfield name="prpDsettlementByr.flag" id="flag" cssClass="input_w w_15" maxlength="5" />
		</td>
	</tr>
</s:if>
<s:elseif test="${editType=='update' }">
	<tr class="top">
		<td class="bgc_tt short">预算单位代码<font color="red">*</font></td>
		<td class="long">
			<s:textfield name="prpDsettlementByr.buyerUnitCode" id="buyerUnitCode" readonly="true"
				cssClass="input_w w_15" maxlength="40"/>
		</td>
		<td class="bgc_tt short">预算单位名称<font color="red">*</font></td>
		<td class="long">
			<s:textfield name="prpDsettlementByr.buyerUnitName" id="buyerUnitName" 
				cssClass="input_w w_15" maxlength="40" readonly="true"/>
		</td> 	
	</tr>
	<tr>
		<td class="bgc_tt short">预算单位地址</td>
		<td class="long">
			<s:textfield name="prpDsettlementByr.buyerUnitAddress" id="buyerUnitAddress" cssClass="input_w w_15" maxlength="120" />
		</td>
		<td class="bgc_tt short">效力状态<font color="red">*</font></td>
		<td class="long">
			<ct:select name="prpDsettlementByr.validStatus" id="validStatus" value="${prpDsettlementByr.validStatus}" 
			sysCode="DMS" codeType="ValidStatus"></ct:select>
		</td>
	</tr>
	<tr>
		<td class="bgc_tt short">标志字段</td>
		<td class="long">
			<s:textfield name="prpDsettlementByr.flag" id="flag" cssClass="input_w w_15" maxlength="5" />
		</td>
	</tr>
</s:elseif>
<s:elseif test="${editType=='view'}">
	<tr class="top">
		<td class="bgc_tt short">预算单位代码<font color="red">*</font></td>
		<td class="long">
			<s:textfield name="prpDsettlementByr.buyerUnitCode" id="buyerUnitCode" 
				cssClass="input_w w_15" readonly="true"/>
		</td>
		<td class="bgc_tt short">预算单位名称<font color="red">*</font></td>
		<td class="long">
			<s:textfield name="prpDsettlementByr.buyerUnitName" id="buyerUnitName" 
				cssClass="input_w w_15" readonly="true"/>
		</td> 	
	</tr>
	<tr>
		<td class="bgc_tt short">预算单位地址</td>
		<td class="long">
			<s:textfield name="prpDsettlementByr.buyerUnitAddress" id="buyerUnitAddress" 
				cssClass="input_w w_15" readonly="true"/>
		</td>
		<td class="bgc_tt short">效力状态<font color="red">*</font></td>
		<td class="long">
			<ct:select name="prpDsettlementByr.validStatus" id="validStatus" value="${prpDsettlementByr.validStatus}" 
			sysCode="DMS" codeType="ValidStatus" disabled="true"></ct:select>
		</td>
	</tr>
	<tr>
		<td class="bgc_tt short">标志字段</td>
		<td class="long">
			<s:textfield name="prpDsettlementByr.flag" id="flag" 
				cssClass="input_w w_15 dc-chk dt-nzhs" readonly="true"/>
		</td>
	</tr>
</s:elseif>
</table> 
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr align="center" class="top">
		<c:if test="${editType=='view' }">
			<td>
			<% if(SyncConstants.ComCode_Head.equals(deployCom)){%>
			<button type="button" value="" onclick="prepareUpdate()"><span><em>修改</em></span></button>
<!--			<input type="button" value="修改" class="button_ty" onclick="prepareUpdate()">-->
			<%}%>
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
		fm.action="${ctx}/dictionary/updatePrpDsettlementByr.do";
		fm.submit();
	}
	function addMethod(){
		if(YAHOO.quote.data.datacheck('fm') ){
			hasSameKey();
		}else{
			 alert("界面输入有误，请核实！");
		}
	}
	function prepareUpdate(){//客户需求，查看页面点击修改要进入修改页面。2009-10-21
		var buyerUnitCode    = document.getElementById("buyerUnitCode").value;
		editRecord("${ctx}/dictionary/prepareUpdatePrpDsettlementByr.do?prpDsettlementByr.buyerUnitCode="+buyerUnitCode+"&editType=update");
		window.close();
	}  

	function hasSameKey(){//多主键校验！
		var key1 = document.getElementById("buyerUnitCode").value;
		var url="${ctx}/dictionary/isSameKey.do?tableName=PrpDsettlementByr&values=buyerUnitCode\='"+key1+"'";
		var handleSuccess = function(o){
			if(o.responseText=="sameKey"){
				alert("该预算单位代码已存在！");
			}else{
				fm.action="${ctx}/dictionary/insertPrpdsettlementByr.do";
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




