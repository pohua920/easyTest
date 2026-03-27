<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="cn.com.sinosoft.ims.sync.SyncConstants"%>
<%
	String deployCom = (String)session.getAttribute("deployCom");
%>
<html>
<head>
<title>港口代码</title>
<%@include file="/common/meta_css.jsp"%>
<%@ include file="/common/i18njs.jsp"%>
<%-- moidfy  update by tongziliang 2011-10-09 reason:修改页面的按钮样式和优化页面样式 --%>
<link rel="stylesheet" href="${ctx}/style/popup/login.css" type="text/css"></link>
</head>
<!--  onkeydown方法禁用BackSpace onload方法光标定位在首个输入框 -->
<body id="all_title" onkeydown="keyDown()" onload="fm.portCode.focus()">
<div id="wrapper">
<div id="container">

<s:form action="${ctx}/dictionary/updatePrpDport.do" name="fm" method="post">
<s:hidden name="editType" id="editType" value="${editType}"></s:hidden>
<s:hidden name="prpDport.flag" id="flag" value="${prpDport.flag}"></s:hidden>
<s:hidden name="deployCom" id="deployCom" value="${deployCom}"></s:hidden>
	<table width="100%" class="fix_table">
    <tr class="top">
      <s:if test="${editType=='insert' }">
      <div id="crash_menu">
<h2 align="center">增加港口代码</h2>
</div>
      </s:if>
      <s:if test="${editType=='update' }">
      <div id="crash_menu">
<h2 align="center">修改港口代码</h2>
</div>
      </s:if>
      <s:if test="${editType=='view' }">
      <div id="crash_menu">
<h2 align="center">查看港口代码</h2>
</div>
      </s:if>
    </tr>       
    <s:if test="${editType=='view' }">
      <tr>
        <td class="bgc_tt short">港口代码</td>
        <td class="long"><s:textfield name="prpDport.portCode" 
          id="portCode" cssClass='input_w w_15' maxlength="20" readonly="true"/></td>
        <td class="bgc_tt short">港口中文名</td>
        <td class="long"><s:textfield name="prpDport.portCName" 
          id="portCName" cssClass='input_w w_15' maxlength="40" readonly="true"/></td>
      </tr>
      <tr>       
        <td class="bgc_tt short">港口英文名</td>
        <td class="long"><s:textfield name="prpDport.portEName" 
          id="portEName" cssClass='input_w w_15' maxlength="40" readonly="true"/></td>       
        <td class="bgc_tt short">国别代码</td>
        <td class="long"><s:textfield name="prpDport.countryCode" 
          id="countryCode" cssClass='input_w w_15' maxlength="20" readonly="true"/></td>
      </tr>
      <tr>
        <td class="bgc_tt short">国家中文名</td>
        <td class="long"><s:textfield name="prpDport.countryCName" 
          id="countryCName" cssClass='input_w w_15' maxlength="30" readonly="true"/></td>       
        <td class="bgc_tt short">国家英文名</td>
        <td class="long"><s:textfield name="prpDport.countryEName" 
          id="countryEName" cssClass='input_w w_15' maxlength="30" readonly="true"/></td>  
      </tr>
<%--
	  <tr>
        <td class="bgc_tt short">生效日期 </td>
        <td class="long"><input type="text" name="prpDport.validDate" value="${prpDport.validDate}"
			 class='input_w w_30 Wdate' id="validDate"  onFocus="WdatePicker()" readonly="readonly"></td>
        <td class="bgc_tt short">失效日期</td>
        <td class="long"><input type="text" name="prpDport.invalidDate" value="${prpDport.invalidDate}"
			 class='input_w w_30 Wdate' id="invalidDate"  onFocus="WdatePicker()" readonly="readonly"></td>
        </tr>
--%>
      <tr> 
        <td class="bgc_tt short">新港口代码</td>
        <td class="long"><s:textfield name="prpDport.newPortCode" 
          id="newPortCode" cssClass='input_w w_15' maxlength="20" readonly="true"/></td>
         <td class="bgc_tt short">有效标志</td>
        <td class="long">
<%--
        <s:select name="prpDport.ValidStatus" id="validStatus"
          list="#@java.util.HashMap@{'1':'有效','0':'无效'}" disabled="true" />
--%> 
		<ct:select name="prpDport.validStatus" value="${prpDport.validStatus}" id="validStatus" sysCode="IMS" codeType="ValidStatus" disabled="true"></ct:select>
		</td>
      </tr>
    </s:if>
    <s:elseif test="${editType=='update' }">
           <tr>
        <td class="bgc_tt short">港口代码<font color="red">*</font></td>
        <td class="long"><s:textfield name="prpDport.portCode" 
          id="portCode" cssClass='input_w w_15 dc-chk' maxlength="8" readonly="true"/></td>
        <td class="bgc_tt short">港口中文名</td>
        <td class="long"><s:textfield name="prpDport.portCName" 
          id="portCName" cssClass='input_w w_15' maxlength="40"/></td>   
      </tr>
      <tr>    
        <td class="bgc_tt short">港口英文名</td>
        <td class="long"><s:textfield name="prpDport.portEName" 
          id="portEName" cssClass='input_w w_15' maxlength="40"/></td>       
        <td class="bgc_tt short">国别代码</td>
        <td class="long"><s:textfield name="prpDport.countryCode" 
          id="countryCode" cssClass='input_w w_15' maxlength="6"/></td>
      </tr>
      <tr>
        <td class="bgc_tt short">国家中文名</td>
        <td class="long"><s:textfield name="prpDport.countryCName" 
          id="countryCName" cssClass='input_w w_15' maxlength="30"/></td>       
        <td class="bgc_tt short">国家英文名</td>
        <td class="long"><s:textfield name="prpDport.countryEName" 
          id="countryEName" cssClass='input_w w_15' maxlength="30"/></td>   
      </tr>
<%--
	  <tr>
        <td class="bgc_tt short">生效日期 </td>
        <td class="long"><input type="text" name="prpDport.validDate" value="${prpDport.validDate}"
			 class='input_w w_30 Wdate' id="validDate"  onFocus="WdatePicker()"></td>
        <td class="bgc_tt short">失效日期</td>
        <td class="long"><input type="text" name="prpDport.invalidDate" value="${prpDport.invalidDate}"
			 class='input_w w_30 Wdate' id="invalidDate"  onFocus="WdatePicker()"></td>
        </tr>
--%>
      <tr>
        <td class="bgc_tt short">新港口代码<font color="red">*</font></td>
        <td class="long"><s:textfield name="prpDport.newPortCode" 
          id="newPortCode" cssClass='input_w w_15  dc-chk' maxlength="8"/></td>
         <td class="bgc_tt short">有效标志<font color="red">*</font></td>
        <td class="long">
<%--
        <s:select name="prpDport.ValidStatus" id="validStatus"
          list="#@java.util.HashMap@{'1':'有效','0':'无效'}"/>
--%>
		<ct:select name="prpDport.validStatus" value="${prpDport.validStatus}" id="validStatus" sysCode="IMS" codeType="ValidStatus"></ct:select>
		<s:hidden name="prpDport.validStatus" id="validStatus" value="${prpDport.validStatus}"></s:hidden>
		</td>
      </tr>
    </s:elseif>
    
    <s:elseif test="${editType=='insert'}">
        <tr>
        <td class="bgc_tt short">港口代码<font color="red">*</font></td>
        <td class="long"><s:textfield name="prpDport.portCode" 
          id="portCode" cssClass='input_w w_15 dc-chk dt-nzhs' maxlength="8"/></td>
        <td class="bgc_tt short">港口中文名</td>
        <td class="long"><s:textfield name="prpDport.portCName" 
          id="portCName" cssClass='input_w w_15' maxlength="40"/></td> 
      </tr>
      <tr>      
        <td class="bgc_tt short">港口英文名</td>
        <td class="long"><s:textfield name="prpDport.portEName" 
          id="portEName" cssClass='input_w w_15' maxlength="40"/></td>       
        <td class="bgc_tt short">国别代码</td>
        <td class="long"><s:textfield name="prpDport.countryCode" 
          id="countryCode" cssClass='input_w w_15' maxlength="6"/></td>
      </tr>
      <tr>
        <td class="bgc_tt short">国家中文名</td>
        <td class="long"><s:textfield name="prpDport.countryCName" 
          id="countryCName" cssClass='input_w w_15' maxlength="30"/></td>       
        <td class="bgc_tt short">国家英文名</td>
        <td class="long"><s:textfield name="prpDport.countryEName" 
          id="countryEName" cssClass='input_w w_15' maxlength="30"/></td> 
      </tr>
<%--
	  <tr>
        <td class="bgc_tt short">生效日期 </td>
        <td class="long"><input type="text" name="prpDport.validDate" value="${prpDport.validDate}"
			 class='input_w w_30 Wdate' id="validDate"  onFocus="WdatePicker()"></td>
        <td class="bgc_tt short">失效日期</td>
        <td class="long"><input type="text" name="prpDport.invalidDate" value="${prpDport.invalidDate}"
			 class='input_w w_30 Wdate' id="invalidDate"  onFocus="WdatePicker()"></td>
        </tr>
--%>
      <tr>  
        <td class="bgc_tt short">新港口代码<font color="red">*</font></td>
        <td class="long"><s:textfield name="prpDport.newPortCode" 
          id="newPortCode" cssClass='input_w w_15 dc-chk dt-nzhs' maxlength="8"/></td>
         <td class="bgc_tt short">有效标志<font color="red">*</font></td>
        <td class="long">
<%--
        <s:select name="prpDport.ValidStatus" id="validStatus"
          list="#@java.util.HashMap@{'1':'有效','0':'无效'}"/>
--%>
		<ct:select name="prpDport.validStatus" id="validStatus" sysCode="IMS" codeType="ValidStatus" value="1"></ct:select>
		<s:hidden name="prpDport.validStatus" id="validStatus" value="1"></s:hidden>
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
<script language="javascript" src="${ctx}/widgets/yui/autocomplete/autocomplete-min.js"></script>
<script language="javascript" src="${ctx}/common/js/CodeSelect.js"></script>
<script type="text/javascript">
function updateMethod(){
    if(checkForm()){
    	if(checkLen()){
	    fm.action="${ctx}/dictionary/updatePrpDport.do";
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
	var key1 = document.getElementById("portCode").value;
	editRecord("${ctx}/dictionary/prepareUpdatePrpDport.do?portCode="+key1+"&editType=update");
	window.close();
}

function checkForm(){
	return YAHOO.quote.data.datacheck('fm');
}
function hasSameKey(){//多主键校验！
	var key1 = document.getElementById("portCode").value;
	var url="${ctx}/dictionary/isSameKey.do?tableName=PrpDport&values=portCode\='"+key1+"'";
	var handleSuccess = function(o){
		if(o.responseText=="sameKey"){
			alert("该港口代码已存在！");
		}else{
			fm.action="${ctx}/dictionary/insertPrpDport.do";
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



