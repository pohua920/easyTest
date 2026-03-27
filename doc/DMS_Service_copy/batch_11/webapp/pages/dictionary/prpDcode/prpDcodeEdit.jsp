<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="cn.com.sinosoft.ims.sync.SyncConstants"%>
<%
	String deployCom = (String)session.getAttribute("deployCom");
%>
<html>
<head>
<title>代码管理</title>
<%@ include file="/common/meta_css.jsp"%>
<%@ include file="/common/i18njs.jsp"%>
<%-- moidfy  update by tongziliang 2011-10-09 reason:修改页面的按钮样式和优化页面样式 --%>
<link rel="stylesheet" href="${ctx}/style/popup/login.css" type="text/css"></link>
</head>
<!--  onkeydown方法禁用BackSpace  -->
<body id="all_title" onkeydown="keyDown()">
<div id="wrapper">
<div id="container"><s:form
	action="${ctx}/dictionary/updatePrpDcode.do" name="fm" method="post">
	<s:hidden name="editType" id="editType" value="${editType}"></s:hidden>
	<s:hidden name="prpDcode.flag" id="flag" value="${prpDcode.flag}"></s:hidden>
	<s:hidden name="deployCom" id="deployCom" value="${deployCom}"></s:hidden>
	<table width="100%" class="fix_table">
		<tr class="top">
			<s:if test="${editType=='insert' }">
				<div id="crash_menu">
				<h2 align="center">增加代码</h2>
				</div>
			</s:if>
			<s:if test="${editType=='update' }">
				<div id="crash_menu">
				<h2 align="center">修改代码</h2>
				</div>
			</s:if>
			<s:if test="${editType=='view' }">
				<div id="crash_menu">
				<h2 align="center">查看代码</h2>
				</div>
			</s:if>
		</tr>
		<s:if test="${editType=='view' }">
			<tr>
				<td class="bgc_tt short">代码类型</td>
				<td class="long"><s:textfield name="prpDcode.id.codeType"
					id="codeType" cssClass='input_w w_15' maxlength="20"
					readonly="true" /></td>
				<td class="bgc_tt short">代码</td>
				<td class="long"><s:textfield name="prpDcode.id.codeCode"
					id="codeCode" cssClass='input_w w_15' maxlength="20"
					readonly="true" /></td>
			</tr>
			<tr>
				<td class="bgc_tt short">代码中文名</td>
				<td class="long"><s:textfield name="prpDcode.codeCName"
					id="codeCName" cssClass='input_w w_15' maxlength="20"
					readonly="true" /></td>
				<td class="bgc_tt short">代码英文名</td>
				<td class="long"><s:textfield name="prpDcode.codeEName"
					id="codeEName" cssClass='input_w w_15' maxlength="20"
					readonly="true" /></td>
			</tr>
			<tr>
				<td class="bgc_tt short">老代码类型</td>
				<td class="long"><s:textfield name="prpDcode.oldCodeType"
					id="oldCodeType" cssClass='input_w w_15' maxlength="20"
					readonly="true" /></td>
				<td class="bgc_tt short">老业务代码</td>
				<td class="long"><s:textfield name="prpDcode.oldCodeCode"
					id="oldCodeCode" cssClass='input_w w_15' maxlength="20"
					readonly="true" /></td>
			</tr>
			<tr>
				<td class="bgc_tt short">新业务代码</td>
				<td class="long"><s:textfield name="prpDcode.newCodeCode"
					id="newCodeCode" cssClass='input_w w_15' maxlength="20"
					readonly="true" /></td>
				<td class="bgc_tt short">有效标志</td>
				<td class="long"><ct:select name="prpDcode.validStatus"
					value="${prpDcode.validStatus}" id="validStatus" sysCode="DMS"
					codeType="ValidStatus" disabled="true"></ct:select></td>
			</tr>
		</s:if>
		<s:elseif test="${editType=='update' }">
			<tr>
				<td class="bgc_tt short">代码类型<font color="red">*</font></td>
				<td class="long"><s:textfield name="prpDcode.id.codeType"
					id="codeType" cssClass='input_w w_15 dc-chk' maxlength="20"
					readonly="true"
					/></td>
				<td class="bgc_tt short">代码<font color="red">*</font></td>
				<td class="long"><s:textfield name="prpDcode.id.codeCode"
					id="codeCode" cssClass='input_w w_15 dc-chk dt-nzhs' maxlength="30" onchange="initNewCodeCode()"
					readonly="true"/></td>
			</tr>
			<tr>
				<td class="bgc_tt short">代码中文名<font color="red">*</font></td>
				<td class="long"><s:textfield name="prpDcode.codeCName"
					id="codeCName" cssClass='input_w w_15 dc-chk' maxlength="100" /></td>
				<td class="bgc_tt short">代码英文名</td>
				<td class="long"><s:textfield name="prpDcode.codeEName"
					id="codeEName" cssClass='input_w w_15' maxlength="100" /></td>
			</tr>
			<tr>
				<td class="bgc_tt short">老代码类型</td>
				<td class="long"><s:textfield name="prpDcode.oldCodeType"
					id="oldCodeType" cssClass='input_w w_15' maxlength="20" /></td>
				<td class="bgc_tt short">老业务代码</td>
				<td class="long"><s:textfield name="prpDcode.oldCodeCode"
					id="oldCodeCode" cssClass='input_w w_15' maxlength="20" /></td>
			</tr>
			<tr>
				<td class="bgc_tt short">新业务代码<font color="red">*</font></td>
				<td class="long"><s:textfield name="prpDcode.newCodeCode"
					id="newCodeCode" cssClass='input_w w_15 dc-chk' maxlength="15" readonly="true"/></td>
				<td class="bgc_tt short">有效标志<font color="red">*</font></td>
				<td class="long"><ct:select name="prpDcode.validStatus"
					value="${prpDcode.validStatus}" id="validStatus" sysCode="DMS"
					codeType="ValidStatus"></ct:select> <s:hidden
					name="prpDcode.validStatus" id="validStatus"
					value="${prpDcode.validStatus}"></s:hidden></td>
			</tr>
		</s:elseif>

		<s:elseif test="${editType=='insert'}">
			<tr>
				<td class="bgc_tt short">代码类型<font color="red">*</font></td>
				<td class="long"><s:textfield name="prpDcode.id.codeType"
					id="codeType" cssClass='input_w w_15 dc-chk' maxlength="20"
					readonly="true"/></td>
				<td class="bgc_tt short">代码<font color="red">*</font></td>
				<td class="long"><s:textfield name="prpDcode.id.codeCode"
					id="codeCode" cssClass='input_w w_15 dc-chk  dt-nzhs' onchange="initNewCodeCode()"
					maxLength="30" /></td>
			</tr>
			<tr>
				<td class="bgc_tt short">代码<font color='red'>*</font></td>
				<td class="long"><s:textfield name="prpDcode.id.codeCode"
				<td class="bgc_tt short">代码中文名<font color="red">*</font></td>
				<td class="long"><s:textfield name="prpDcode.codeCName"
					id="codeCName" cssClass='input_w w_15 dc-chk' maxlength="100" /></td>
				<td class="bgc_tt short">代码英文名</td>
				<td class="long"><s:textfield name="prpDcode.codeEName"
					id="codeEName" cssClass='input_w w_15' maxlength="100" /></td>
			</tr>
			<tr>
				<td class="bgc_tt short">老代码类型</td>
				<td class="long"><s:textfield name="prpDcode.oldCodeType"
					id="oldCodeType" cssClass='input_w w_15' maxlength="20" /></td>
				<td class="bgc_tt short">老业务代码</td>
				<td class="long"><s:textfield name="prpDcode.oldCodeCode"
					id="oldCodeCode" cssClass='input_w w_15' maxlength="20" /></td>
			</tr>
			<tr>
				<td class="bgc_tt short">新业务代码<font color="red">*</font></td>
				<td class="long"><s:textfield name="prpDcode.newCodeCode"
					id="newCodeCode" cssClass='input_w w_15 dc-chk' maxlength="15" /></td>
				<td class="bgc_tt short">有效标志<font color="red">*</font></td>
				<td class="long"><ct:select name="prpDcode.validStatus"
					id="validStatus" sysCode="DMS" codeType="ValidStatus" value="1">
					</ct:select> <s:hidden name="prpDcode.validStatus"
					id="validStatus" value="1"></s:hidden></td>
			<tr>
				<td class="bgc_tt short">请选择</td>
				<td class="long">
					<input type="radio" name="radio1" value="1" size="7" onclick="withComCode(this)" checked>总颁代码
					<input type="radio" name="radio1" value="2"	size="7" onclick="withComCode(this)">省颁代码
				</td>
				<td class="bgc_tt short"><div id="comCodeLable" style="display:none;">省颁代码归属机构<font color="red">*</font></div></td>
				<td class="long"><div id='comCode' style="display:none;">
					<!-- <s:textfield name="prpDnewCodeCom.id.comCode" cssClass='input_w w_15' maxlength="8" /> 
					-->
                <s:textfield  name="prpDnewCodeCom.id.comCode" 
				cssClass='input_y w_15' maxlength="8"
				ondblclick="code_CodeQuery(this, 'ComCode', '0', 'Y','')"
				onkeyup="code_CodeQuery(this, 'ComCode', '0', 'Y','')"
				onchange="code_CodeChange(this, 'ComCode', '0', 'Y','')" />
					</div>
				</td>
			</tr>
		</s:elseif>
	</table>

	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr align="center" class="top">
			<c:if test="${editType=='view' }">
				<td>
				 <% if(SyncConstants.ComCode_Head.equals(deployCom)){%>
				 <button type="button" value=""
					onclick="prepareUpdate()"><span><em>修改</em></span></button>
<!--				<input type="button" value="修改" class="button_ty"-->
<!--					onclick="prepareUpdate()">-->
				 <%}%>	
				</td>
			</c:if>
			<c:if test="${editType=='insert' }">
				<td>
				<button type="button" value="" 
					onclick="return addMethod()"><span><em>保存</em></span></button>
<!--				<input type="button" value="保存" class="button_ty"-->
<!--					onclick="return addMethod()">-->
					</td>
			</c:if>
			<c:if test="${editType=='update' }">
				<td>
				<button type="button" value="" 
					onclick="updateMethod()"><span><em>保存</em></span></button>
<!--				<input type="button" value="保存" class="button_ty"-->
<!--					onclick="updateMethod()">-->
					</td>
			</c:if>
		</tr>
	</table>
</s:form></div>
</div>
</body>
</html>
<%@ include file="/common/meta_js.jsp"%>
<script language="javascript" src="${ctx}/common/js/newCalendar.js"></script>
<script language="javascript" src="${ctx}/common/dwr/engine.js"></script>
<script language="javascript" src="${ctx}/common/dwr/util.js"></script>
<script language="javascript" src="${ctx}/widgets/yui/autocomplete/autocomplete-min.js"></script>
<script language="javascript" src="${ctx}/common/js/CodeSelect.js"></script>
<script type="text/javascript"><!--
function choose(){
	var radio1 = document.getElementById("radio1").vaule
	return true;
}
function updateMethod(){
    if(checkForm()){
    	if(checkLen()){
	    fm.action="${ctx}/dictionary/updatePrpDcode.do";	   
	    fm.submit();
	    }
    }
}

function addMethod(){
	var codecomtype =document.getElementsByName("radio1");
	var flag = false;
	var comcode = document.getElementById('prpDnewCodeCom.id.comCode').value;
	for(i=0;i<codecomtype.length;i++){
		if(codecomtype[i].checked==true){
			flag = true;
			if(codecomtype[i].value=="2" && comcode==""){
				alert("必须填写省颁代码归属机构！");
				return false;
			} else if(codecomtype[i].value=="2" && comcode.length!=8){
				alert("机构代码不足八位，请检查！");
				return false;
			}
		}
	}
	if(!flag){
		alert("请选择统颁代码或者省颁代码！");
		return false;
	}
	if(checkForm()){
		if(checkLen()){
			hasSameKey();
		}
	}	
}

function prepareUpdate(){//客户需求，查看页面点击修改要进入修改页面。2009-10-21
	var key1 = document.getElementById("codeCode").value;
	var key2 = document.getElementById("codeType").value;
	editRecord("${ctx}/dictionary/prepareUpdatePrpDcode.do?prpDcode.id.codeType="+key2+"&prpDcode.id.codeCode="+key1+"&editType=update");
	window.close();
}

function checkForm(){
	return YAHOO.quote.data.datacheck('fm');
}
function hasSameKey(){//多主键校验！
	var key1 = document.getElementById("codeCode").value;
	var key2 = document.getElementById("codeType").value;
	var url="${ctx}/dictionary/isSameKey.do?tableName=PrpDnewCode&values=id.codeCode\='"+key1+"'#id.codeType\='"+key2+"'";
	var handleSuccess = function(o){
		if(o.responseText=="sameKey"){		
			alert("该通用代码已存在！");
		}else{
			fm.action="${ctx}/dictionary/insertPrpDcode.do";
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

function initNewCodeCode(){
	var newCodeCode = document.getElementById("newCodeCode");
	var codeCode = document.getElementById("codeCode");
	if(newCodeCode.value == null || newCodeCode.value == "") {
		newCodeCode.value = codeCode.value;
	}
}

function withComCode(field){	
	var comcode = document.getElementById("comCode");	
	var lable = document.getElementById("comCodeLable");	
	if(field.value==1){  		
	    comcode.style.display ='none';
		lable.style.display = 'none';
	} 
	else {
		comcode.style.display='';
		lable.style.display='';
	}		
}
	function keyDown(){
            // 禁止使用backspace键
            if(window.event.keyCode == 8){
             event.keyCode = 0; 
       		 event.cancelBubble = true; 
             return false; 
            }
        }
                      
//function init(){
//	initAllSelectUi();
//}
//YAHOO.util.Event.addListener(window,'load',init);
/*****时间控件******/
//init_calendar("calContainer1","imgBtn1","beginDate","toSecond");
//init_calendar("calContainer2","imgBtn2","endDate","toSecond");
</script>


