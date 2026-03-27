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
<title>特别约定代码</title>
<%@include file="/common/meta_css.jsp"%>
<%@ include file="/common/i18njs.jsp"%>
</head>
<!--  onkeydown方法禁用BackSpace onload方法光标定位在首个输入框 -->
<body id="all_title" onload="fm.riskCode.focus()" onkeydown="keyDown()">
<div id="wrapper">
<div id="container">

<s:form action="${ctx}/dictionary/updatePrpDriskEngage.do" name="fm" method="post">
<s:hidden name="editType" id="editType" value="${editType}"></s:hidden>
<s:hidden name="prpDriskEngage.flag" id="flag" value="${prpDriskEngage.flag}"></s:hidden>
<s:hidden name="deployCom" id="deployCom" value="${deployCom}"></s:hidden>
	<table width="100%" class="fix_table">
    <tr class="top">
      <s:if test="${editType=='insert' }">
      <div id="crash_menu">
<h2 align="center">增加特别约定代码</h2>
</div>
      </s:if>
      <s:if test="${editType=='update' }">
      <div id="crash_menu">
<h2 align="center">修改特别约定代码</h2>
</div>
      </s:if>
      <s:if test="${editType=='view' }">
      <div id="crash_menu">
<h2 align="center">查看特别约定代码</h2>
</div>
      </s:if>
    </tr>       
    <s:if test="${editType=='view' }">
      <tr>
        <td class="bgc_tt short">产品代码<font color="red">*</font></td>
        <td class="long"><s:textfield name="prpDriskEngage.id.riskCode" 
          id="riskCode" cssClass='input_w w_15 dc-chk' maxlength="30" readonly="true"/></td>
        <td class="bgc_tt short">条款代码<font color="red">*</font></td>
        <td class="long"><s:textfield name="prpDriskEngage.id.clauseCode" 
          id="clauseCode" cssClass='input_w w_15' maxlength="40" readonly="true"/></td>
      </tr>
      <tr>
        <td class="bgc_tt short">特别约定代码<font color="red">*</font></td>    
        <td class="long"><s:textfield name="prpDriskEngage.id.engageCode" 
          id="engageCode" cssClass='input_w w_15 dc-chk dt-nzhs' maxlength="30" readonly="true"/></td>
        <td class="bgc_tt short">旧特约代码</td>
        <td class="long"><s:textfield name="prpDriskEngage.oldEngageCode" 
          id="oldEngageCode" cssClass='input_w w_15' maxlength="30" readonly="true"/></td>  
      </tr>
      <tr>       
        <td class="bgc_tt short">特别约定中文名称</td>
        <td class="long"><s:textfield name="prpDriskEngage.engageCName" 
          id="engageCName" cssClass='input_w w_15' maxlength="40" readonly="true"/></td>       
        <td class="bgc_tt short">特别约定英文名称</td>
        <td class="long"><s:textfield name="prpDriskEngage.engageEName" 
          id="engageEName" cssClass='input_w w_15' maxlength="20" readonly="true"/></td>
      </tr>
      <tr> 
        <td class="bgc_tt short">语种标识</td>
        <td class="long"><s:textfield name="prpDriskEngage.language" 
          id="language" cssClass='input_w w_15' maxlength="20" readonly="true"/></td>
        <td class="bgc_tt short">特别约定描述</td>
        <td class="long"><s:textfield name="prpDriskEngage.engageDesc" 
          id="engageDes" cssClass='input_w w_15 dt-post' maxlength="20" readonly="true"/></td>
      </tr> 
	  <tr> 
        <td class="bgc_tt short">承保是否可改</td>
        <td class="long">
		<s:select name="prpDriskEngage.changeAble"
		list="#@java.util.HashMap@{'1':'可改','0':'不可改'}" disabled="true" />
		</td>
        <td class="bgc_tt short">承保自动带出标识</td>
        <td class="long"><s:textfield name="prpDriskEngage.autoFlag" 
          id="autoFlag" cssClass='input_w w_15' maxlength="20" readonly="true"/></td>
      </tr>
      <tr> 
        <td class="bgc_tt short">特别约定层级</td>
        <td class="long"><s:textfield name="prpDriskEngage.engageLevel" 
          id="engageLevel" cssClass='input_w w_15' maxlength="20" readonly="true"/></td>
       	<td class="bgc_tt short">适用区域主键</td>
        <td class="long"><s:textfield name="prpDriskEngage.areaMappingCode" 
          id="areaMappingCode" cssClass='input_w w_15' maxlength="20" readonly="true"/></td> 
      </tr>
      <tr>
      	<td class="bgc_tt short">适用区域层级</td>
        <td class="long"><s:textfield name="prpDriskEngage.areaLevel" 
          id="areaLevel" cssClass='input_w w_15' maxlength="20" readonly="true"/></td>  
        <td class="bgc_tt short">区域代码</td>
        <td class="long"><s:textfield name="prpDriskEngage.areaCode" 
          id="areaCode" cssClass='input_w w_15' maxlength="20" readonly="true"/></td>       
      </tr>
      <tr>
     	 <td class="bgc_tt short">适用区域名称</td>
        <td class="long"><s:textfield name="prpDriskEngage.areaName" 
          id="areaName" cssClass='input_w w_15' maxlength="255" readonly="true"/></td>
        <td class="bgc_tt short">生效日期</td>
        <td class="long">
		<s:textfield name="prpDriskEngage.validDate"
		id="validDate" cssClass="input_w w_15 "
		value="${prpDriskEngage.validDate}" readonly="true"/>
		</td>           
      </tr>
      <tr>
      	<td class="bgc_tt short">失效日期</td>
        <td class="long">
		<s:textfield name="prpDriskEngage.invalidDate"
		id="invalidDate" cssClass="input_w w_15 "
		value="${prpDriskEngage.invalidDate}" readonly="true"/>
		</td>          
		<td class="bgc_tt short">有效标志</td>
        <td class="long">
		<ct:select name="prpDriskEngage.validInd" value="${prpDriskEngage.validInd }" id="validInd" sysCode="DMS" codeType="ValidStatus" disabled="true"></ct:select>
		</td> 
      </tr> 
    </s:if>
    <s:elseif test="${editType=='update' }">
      <tr>
        <td class="bgc_tt short">产品代码<font color="red">*</font></td>
        <td class="long"><s:textfield name="prpDriskEngage.id.riskCode" 
          id="riskCode" cssClass='input_w w_15 dc-chk dt-nzhs' maxlength="3" readonly="true"/></td>
        <td class="bgc_tt short">条款代码<font color="red">*</font></td>
        <td class="long"><s:textfield name="prpDriskEngage.id.clauseCode" 
          id="clauseCode" cssClass='input_w w_15 dc-chk dt-nzhs' maxlength="6" readonly="true"/></td>
      </tr>
      <tr>
        <td class="bgc_tt short">特别约定代码<font color="red">*</font></td>    
        <td class="long"><s:textfield name="prpDriskEngage.id.engageCode" 
          id="engageCode" cssClass='input_w w_15 dc-chk dt-zzs' maxlength="10" readonly="true"/></td>
        <td class="bgc_tt short">旧特约代码</td>
        <td class="long"><s:textfield name="prpDriskEngage.oldEngageCode" 
          id="oldEngageCode" cssClass='input_w w_15' maxlength="30"/></td>  
      </tr>
      <tr>       
        <td class="bgc_tt short">特别约定中文名称</td>
        <td class="long"><s:textfield name="prpDriskEngage.engageCName" 
          id="engageCName" cssClass='input_w w_15' maxlength="40"/></td>       
        <td class="bgc_tt short">特别约定英文名称</td>
        <td class="long"><s:textfield name="prpDriskEngage.engageEName" 
          id="engageEName" cssClass='input_w w_15' maxlength="20"/></td>
      </tr>
      <tr> 
        <td class="bgc_tt short">语种标识</td>
        <td class="long"><s:textfield name="prpDriskEngage.language" 
          id="language" cssClass='input_w w_15' maxlength="1" /></td>
        <td class="bgc_tt short">特别约定描述</td>
        <td class="long"><s:textfield name="prpDriskEngage.engageDesc" 
          id="engageDes" cssClass='input_w w_15' maxlength="255"/></td>
      </tr> 
	  <tr> 
        <td class="bgc_tt short">承保是否可改</td>
        <td class="long">
		<s:select name="prpDriskEngage.changeAble"
		list="#@java.util.HashMap@{'1':'可改','0':'不可改'}" />
		</td>
        <td class="bgc_tt short">承保自动带出标识</td>
        <td class="long"><s:textfield name="prpDriskEngage.autoFlag" 
          id="autoFlag" cssClass='input_w w_15' maxlength="1"/></td>
      </tr>
      <tr> 
        <td class="bgc_tt short">特别约定层级</td>
        <td class="long"><s:textfield name="prpDriskEngage.engageLevel" 
          id="engageLevel" cssClass='input_w w_15' maxlength="1"/></td>
       	<td class="bgc_tt short">适用区域主键</td>
        <td class="long"><s:textfield name="prpDriskEngage.areaMappingCode" 
          id="areaMappingCode" cssClass='input_w w_15' maxlength="20"/></td> 
      </tr>
      <tr>
      	<td class="bgc_tt short">适用区域层级</td>
        <td class="long"><s:textfield name="prpDriskEngage.areaLevel" 
          id="areaLevel" cssClass='input_w w_15' maxlength="2"/></td>  
        <td class="bgc_tt short">区域代码<font color="red">*</font></td>
        <td class="long"><input name="prpDriskEngage.areaCode" 
          id="comCode" Class='input_y w_15 dc-chk' maxlength="8" VALUE="${prpDriskEngage.areaCode}"
				ondblclick="code_CodeQuery(this, 'ComCode', '0', 'Y','')"
				onkeyup="code_CodeQuery(this, 'ComCode', '0', 'Y','')"
				onchange="code_CodeChange(this, 'ComCode', '0', 'Y','')"/>
		</td>       
      </tr>
      <tr>
      	<td class="bgc_tt short">适用区域名称</td>
        <td class="long"><s:textfield name="prpDriskEngage.areaName" 
          id="areaName" cssClass='input_w w_15' maxlength="255"/></td>
        <td class="bgc_tt short">生效日期</td>
        <td class="long">
		<s:textfield name="prpDriskEngage.validDate"
		id="validDate" cssClass="input_w w_15 "
		value="${prpDriskEngage.validDate}" onfocus="WdatePicker()"/>
		</td>      
      </tr>
      <tr>
      	<td class="bgc_tt short">失效日期</td>
        <td class="long">
		<s:textfield name="prpDriskEngage.invalidDate"
		id="invalidDate" cssClass="input_w w_15 "
		value="${prpDriskEngage.invalidDate}" onfocus="WdatePicker()"/>
		</td>           
		<td class="bgc_tt short">有效标志</td>
        <td class="long">
		<ct:select name="prpDriskEngage.validInd" value="${prpDriskEngage.validInd }" id="validInd" sysCode="DMS" codeType="ValidStatus"></ct:select>
		</td> 
      </tr> 
    </s:elseif>
    
    <s:elseif test="${editType=='insert'}">
       <tr>
        <td class="bgc_tt short">产品代码<font color="red">*</font></td>
        <td class="long"><s:textfield name="prpDriskEngage.id.riskCode" 
          id="riskCode" cssClass='input_w w_15 dc-chk dt-nzhs' maxlength="3"/></td>
        <td class="bgc_tt short">条款代码<font color="red">*</font></td>
        <td class="long"><s:textfield name="prpDriskEngage.id.clauseCode" 
          id="clauseCode" cssClass='input_w w_15 dc-chk dt-nzhs' maxlength="6"/></td>
      </tr>
      <tr>
        <td class="bgc_tt short">特别约定代码<font color="red">*</font></td>    
        <td class="long"><s:textfield name="prpDriskEngage.id.engageCode" 
          id="engageCode" cssClass='input_w w_15 dc-chk dt-zzs' maxlength="10"/></td>
        <td class="bgc_tt short">旧特约代码</td>
        <td class="long"><s:textfield name="prpDriskEngage.oldEngageCode" 
          id="oldEngageCode" cssClass='input_w w_15 dt-nzhs' maxlength="30"/></td>  
      </tr>
      <tr>       
        <td class="bgc_tt short">特别约定中文名称</td>
        <td class="long"><s:textfield name="prpDriskEngage.engageCName" 
          id="engageCName" cssClass='input_w w_15 ' maxlength="40"/></td>       
        <td class="bgc_tt short">特别约定英文名称</td>
        <td class="long"><s:textfield name="prpDriskEngage.engageEName" 
          id="engageEName" cssClass='input_w w_15' maxlength="20"/></td>
      </tr>
      <tr> 
        <td class="bgc_tt short">语种标识</td>
        <td class="long"><s:textfield name="prpDriskEngage.language" 
          id="language" cssClass='input_w w_15 dt-nzhs' maxlength="1"/></td>
        <td class="bgc_tt short">特别约定描述</td>
        <td class="long"><s:textfield name="prpDriskEngage.engageDesc" 
          id="engageDes" cssClass='input_w w_15' maxlength="255"/></td>
      </tr> 
	  <tr> 
        <td class="bgc_tt short">承保是否可改</td>
        <td class="long">
		<s:select name="prpDriskEngage.changeAble"
		list="#@java.util.HashMap@{'1':'可改','0':'不可改'}" />
		</td>
        <td class="bgc_tt short">承保自动带出标识</td>
        <td class="long"><s:textfield name="prpDriskEngage.autoFlag" 
          id="autoFlag" cssClass='input_w w_15' maxlength="1"/></td>
      </tr>
      <tr> 
        <td class="bgc_tt short">特别约定层级</td>
        <td class="long"><s:textfield name="prpDriskEngage.engageLevel" 
          id="engageLevel" cssClass='input_w w_15' maxlength="1"/></td>
       	<td class="bgc_tt short">适用区域主键</td>
        <td class="long"><s:textfield name="prpDriskEngage.areaMappingCode" 
          id="areaMappingCode" cssClass='input_w w_15' maxlength="20"/></td> 
      </tr>
      <tr>
      	<td class="bgc_tt short">适用区域层级</td>
        <td class="long"><s:textfield name="prpDriskEngage.areaLevel" 
          id="areaLevel" cssClass='input_w w_15' maxlength="2"/></td>  
        <td class="bgc_tt short">区域代码<font color="red">*</font></td>
        <td class="long">
        <input  name="prpDriskEngage.areaCode" id="areaCode"
				Class='input_y w_15 dc-chk' maxlength="8"
				ondblclick="code_CodeQuery(this, 'ComCode', '0', 'Y','')"
				onkeyup="code_CodeQuery(this, 'ComCode', '0', 'Y','')"
				onchange="code_CodeChange(this, 'ComCode', '0', 'Y','')" />
		</td>      
      </tr>
      <tr>
      <td class="bgc_tt short">适用区域名称</td>
        <td class="long"><s:textfield name="prpDriskEngage.areaName" 
          id="areaName" cssClass='input_w w_15' maxlength="255"/></td>
        <td class="bgc_tt short">生效日期</td>
        <td class="long">
		<s:textfield name="prpDriskEngage.validDate"
		id="validDate" cssClass="input_w w_15 dt-date"
		value="${prpDriskEngage.validDate}" onfocus="WdatePicker()"/>
		</td>
             
      </tr>
      <tr> 
      	<td class="bgc_tt short">失效日期</td>
        <td class="long">
		<s:textfield name="prpDriskEngage.invalidDate"
		id="invalidDate" cssClass="input_w w_15 dt-date"
		value="${prpDriskEngage.invalidDate}" onfocus="WdatePicker()"/>
		</td>             
		<td class="bgc_tt short">有效标志</td>
        <td class="long">
		<ct:select name="prpDriskEngage.validInd" id="validInd"  sysCode="DMS" value="1" codeType="ValidStatus"></ct:select>
		</td> 
      </tr> 
    </s:elseif>
    
  </table>
  
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr align="center" class="top">
	  <c:if test="${editType=='view' }">
        <td>
        <% if(SyncConstants.ComCode_Head.equals(deployCom)){%>
        <button type="button" value="修改" class="button_ty"
        onclick="prepareUpdate()"><span><em>确定</em></span></button>
<!--        <input type="button" value="修改" class="button_ty"-->
<!--        onclick="prepareUpdate()">-->
        <%}%>
        </td>
      </c:if>
      <c:if test="${editType=='insert' }">
        <td>
        <button type="button" value="" 
        onclick="return addMethod()"><span><em>保存</em></span></button>
<!--        <input type="button" value="保存" class="button_ty"-->
<!--        onclick="return addMethod()">-->
        </td>
      </c:if>
      <c:if test="${editType=='update' }">
        <td>
        <button type="button" value="" 
          onclick="updateMethod()"><span><em>保存</em></span></button>
<!--        <input type="button" value="保存" class="button_ty"-->
<!--          onclick="updateMethod()">-->
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
<script type="text/javascript">

function updateMethod(){
    if(checkForm()){ 
	    fm.action="${ctx}/dictionary/updatePrpDriskEngage.do";
	    fm.submit();  	
    }
}

function addMethod(){
	if(checkForm()){	 
	 if(checkLen()){
			hasSameKey();
		}
	}
}
function prepareUpdate(){//客户需求，查看页面点击修改要进入修改页面。
	var riskCode = document.getElementById("riskCode").value;
	var clauseCode = document.getElementById("clauseCode").value;
	var engageCode = document.getElementById("engageCode").value;
	editRecord("${ctx}/dictionary/prepareUpdatePrpDriskEngage.do?prpDriskEngage.id.riskCode="
		+riskCode
		+ "&prpDriskEngage.id.clauseCode="
	    + clauseCode
	    + "&prpDriskEngage.id.engageCode="
		+ engageCode
		+"&editType=update");
		window.close();
}

function checkForm(){
	return YAHOO.quote.data.datacheck('fm');
}
function hasSameKey(){//多主键校验！
	var key1 = document.getElementById("riskCode").value;
	var key2 = document.getElementById("clauseCode").value;
    var key3 = document.getElementById("engageCode").value;
	var url="${ctx}/dictionary/isSameKeys.do?tableName=PrpDriskEngage&values1=id.riskCode\='"+key1+"'^id.clauseCode\='"+key2+"'^id.engageCode\='"+key3+"'";	
	var handleSuccess = function(o){
		if(o.responseText=="sameKey"){
			alert("该特约代码已存在！");
		}else{
			fm.action="${ctx}/dictionary/insertPrpDriskEngage.do";
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

//function init(){
//	initAllSelectUi();
//}
//YAHOO.util.Event.addListener(window,'load',init);
/*****时间控件******/
//init_calendar("calContainer1","imgBtn1","beginDate","toSecond");
//init_calendar("calContainer2","imgBtn2","endDate","toSecond");
</script>



