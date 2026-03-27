<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="cn.com.sinosoft.ims.sync.SyncConstants"%>
<%
	String deployCom = (String)session.getAttribute("deployCom");
%>
<html>
<head>
<title>代码类型</title>
<%@include file="/common/meta_css.jsp"%>
<%@ include file="/common/i18njs.jsp"%>
<%-- moidfy  update by tongziliang 2011-10-09 reason:修改页面的按钮样式和优化页面样式 --%>
<link rel="stylesheet" href="${ctx}/style/popup/login.css" type="text/css"></link>
</head>
<!--  onkeydown方法禁用BackSpace onload方法光标定位在首个输入框 -->
<body id="all_title" onkeydown="keyDown()" onload="fm.codeType.focus()"> 
<div id="wrapper">
<div id="container">

<s:form action="${ctx}/dictionary/updatePrpDtype.do" name="fm" method="post">
<s:hidden name="editType" id="editType" value="${editType}"></s:hidden>
<s:hidden name="prpDtype.flag" id="flag" value="${prpDtype.flag}"></s:hidden>
<s:hidden name="deployCom" id="deployCom" value="${deployCom}"></s:hidden>
	<table width="100%" class="fix_table">
    <tr class="top">
      <s:if test="${editType=='insert' }">
        <td colspan="6" align="center"><strong></strong></td>
      <div id="crash_menu">
<h2 align="center">增加代码</h2>
</div>
      </s:if>
      <s:if test="${editType=='update' }">
        <td colspan="6" align="center"><strong></strong></td>
      <div id="crash_menu">
<h2 align="center">修改代码</h2>
</div>
      </s:if>
      <s:if test="${editType=='view' }">
        <td colspan="6" align="center"><strong></strong></td>
      <div id="crash_menu">
<h2 align="center">查看代码</h2>
</div>
      </s:if>
    </tr>       
    <s:if test="${editType=='view' }">
      <tr>
        <td class="bgc_tt short">代码类型</td>
        <td class="long"><s:textfield name="prpDtype.codeType" 
          id="codeType" cssClass='input_w w_15' maxlength="20" readonly="true"/></td>
        <td class="bgc_tt short">代码类型名称</td>
        <td class="long"><s:textfield name="prpDtype.codeTypeDesc" 
          id="codeTypeDesc" cssClass='input_w w_15' maxlength="50" readonly="true"/></td> 
      </tr>
<%--
	  <tr>
        <td class="bgc_tt short">生效日期 </td>
        <td class="long"><input type="text" name="prpDtype.validDate" value="${prpDtype.validDate}"
			 class='input_w w_30 Wdate' id="validDate"  onFocus="WdatePicker()" readonly="readonly"></td>
        <td class="bgc_tt short">失效日期</td>
        <td class="long"><input type="text" name="prpDtype.invalidDate" value="${prpDtype.invalidDate}"
			 class='input_w w_30 Wdate' id="invalidDate"  onFocus="WdatePicker()" readonly="readonly"></td>
        </tr>
--%>
      <tr>      
        <td class="bgc_tt short">新的代码类型</td>
        <td class="long"><s:textfield name="prpDtype.newCodeType" 
          id="newCodeType" cssClass='input_w w_15' maxlength="20" readonly="true"/></td>       
       	<td class="bgc_tt short">有效标志</td>
        <td class="long">
<!--
        <s:select name="prpDtype.ValidStatus"  id="validStatus"
          list="#@java.util.HashMap@{'1':'有效','0':'无效'}" disabled="true" />
-->
		<ct:select name="prpDtype.validStatus" value="${prpDtype.validStatus}" id="validStatus" sysCode="IMS" codeType="ValidStatus" disabled="true"></ct:select>
		</td>  
      </tr>
    </s:if>

    <s:elseif test="${editType=='update' }">
         <tr>
        <td class="bgc_tt short">代码类型<font color="red">*</font></td>
        <td class="long"><s:textfield name="prpDtype.codeType" 
          id="codeType" cssClass='input_w w_15 dc-chk dt-nzhs' maxlength="20" readonly="true"/></td>
        <td class="bgc_tt short">代码类型名称<font color="red">*</font></td>
        <td class="long"><s:textfield name="prpDtype.codeTypeDesc" 
          id="codeTypeDesc" cssClass='input_w w_15 dc-chk' maxlength="50"/></td>   
      </tr>
<%--
	  <tr>
        <td class="bgc_tt short">生效日期 </td>
        <td class="long"><input type="text" name="prpDtype.validDate" value="${prpDtype.validDate}"
			 class='input_w w_30 Wdate' id="validDate"  onFocus="WdatePicker()"></td>
        <td class="bgc_tt short">失效日期</td>
        <td class="long"><input type="text" name="prpDtype.invalidDate" value="${prpDtype.invalidDate}"
			 class='input_w w_30 Wdate' id="invalidDate"  onFocus="WdatePicker()"></td>
        </tr>
--%>

      <tr>    
        <td class="bgc_tt short">新的代码类型<font color="red">*</font></td>
        <td class="long"><s:textfield name="prpDtype.newCodeType" 
          id="newCodeType" cssClass='input_w w_15 dc-chk' maxlength="20"/></td>       
       <td class="bgc_tt short">有效标志<font color="red">*</font></td>
        <td class="long">
<!--
        <s:select name="prpDtype.ValidStatus"  id="validStatus"
          list="#@java.util.HashMap@{'1':'有效','0':'无效'}"/>
-->
		<ct:select name="prpDtype.validStatus" value="${prpDtype.validStatus}"  id="validStatus" sysCode="IMS" codeType="ValidStatus" ></ct:select>
		<s:hidden name="prpDtype.validStatus" id="validStatus" value="${prpDtype.validStatus}"></s:hidden>
		</td>
      </tr>
    </s:elseif>
    
    <s:elseif test="${editType=='insert'}">
        <tr>
        <td class="bgc_tt short">代码类型<font color="red">*</font></td>
        <td class="long"><s:textfield name="prpDtype.codeType" 
          id="codeType" cssClass='input_w w_15 dc-chk dt-nzhs' maxlength="20"/></td>
        <td class="bgc_tt short">代码类型名称<font color="red">*</font></td>
        <td class="long"><s:textfield name="prpDtype.codeTypeDesc" 
          id="codeTypeDesc" cssClass='input_w w_15 dc-chk' maxlength="50"/></td>  
      </tr>
<%--
	  <tr>
        <td class="bgc_tt short">生效日期 </td>
        <td class="long"><input type="text" name="prpDtype.validDate" value="${prpDtype.validDate}"
			 class='input_w w_30 Wdate' id="validDate"  onFocus="WdatePicker()"></td>
        <td class="bgc_tt short">失效日期</td>
        <td class="long"><input type="text" name="prpDtype.invalidDate" value="${prpDtype.invalidDate}"
			 class='input_w w_30 Wdate' id="invalidDate"  onFocus="WdatePicker()"></td>
        </tr>
--%>
      <tr>     
        <td class="bgc_tt short">新的代码类型<font color="red">*</font></td>
        <td class="long"><s:textfield name="prpDtype.newCodeType" 
          id="newCodeType" cssClass='input_w w_15 dc-chk' maxlength="20"/></td>       
       <td class="bgc_tt short">有效标志<font color="red">*</font></td>
        <td class="long">
<!--
        <s:select name="prpDtype.ValidStatus"  id="validStatus"
          list="#@java.util.HashMap@{'1':'有效','0':'无效'}"/>
-->
		<ct:select name="prpDtype.validStatus"  id="validStatus" sysCode="IMS" codeType="ValidStatus" value="1"></ct:select>
		<s:hidden name="prpDtype.validStatus" id="validStatus" value="1"></s:hidden>
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
<script language="javascript"
	src="${ctx}/widgets/yui/autocomplete/autocomplete-min.js"></script>

<script language="javascript" src="${ctx}/common/js/CodeSelect.js"></script>
<script type="text/javascript">
function updateMethod(){
    if(checkForm()){
	     if(checkLen()){
		    fm.action="${ctx}/dictionary/updatePrpDtype.do";
		    fm.submit();
	    }
    }
}

function addMethod(){
	if(checkForm()){
		 if(checkLen()){
			hasSameKey();
		}
	}
}
function prepareUpdate(){//客户需求，查看页面点击修改要进入修改页面。2009-10-21
	var key1 = document.getElementById("codeType").value;
	editRecord("${ctx}/dictionary/prepareUpdatePrpDtype.do?chkbox="+key1+"&editType=update");
	window.close();
}

function checkForm(){
	return YAHOO.quote.data.datacheck('fm');
}
function hasSameKey(){//多主键校验！
	var key1 = document.getElementById("codeType").value;
	var url="${ctx}/dictionary/isSameKey.do?tableName=PrpDtype&values=codeType\='"+key1+"'";
	var handleSuccess = function(o){
		if(o.responseText=="sameKey"){
			alert("该代码类型已存在！");
		}else{
			fm.action="${ctx}/dictionary/insertPrpDtype.do";
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



