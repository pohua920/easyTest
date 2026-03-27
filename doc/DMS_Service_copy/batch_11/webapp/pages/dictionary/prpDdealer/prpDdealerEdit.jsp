<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="cn.com.sinosoft.ims.sync.SyncConstants"%>
<%-- moidfy  update by tongziliang 2011-10-09 reason:修改页面的按钮样式和优化页面样式 --%>
<link rel="stylesheet" href="${ctx}/style/popup/login.css" type="text/css"></link>
<%
	String deployCom = (String)session.getAttribute("deployCom");
%>
<html>
<head>
<title>汽车经销商代码</title>
<%@include file="/common/meta_css.jsp"%>
<%@ include file="/common/i18njs.jsp"%>
</head>
<!--  onkeydown方法禁用BackSpace onload方法光标定位在首个输入框 -->
<body id="all_title" onkeydown="keyDown()" onload="fm.dealerCode.focus()">
<div id="wrapper">
<div id="container">
<s:form action="${ctx}/dictionary/updatePrpDdealer.do" name="fm" method="post">
<s:hidden name="editType" id="editType" value="${editType}"></s:hidden>
<s:hidden name="prpDdealer.flag" id="flag" value="${prpDdealer.flag}"></s:hidden>
<s:hidden name="deployCom" id="deployCom" value="${deployCom}"></s:hidden>
	<table width="100%" class="fix_table">
    <tr class="top">
      <s:if test="${editType=='insert' }">
        <div id="crash_menu">
			<h2 align="center">增加经销商</h2>
		</div>
      </s:if>
      <s:if test="${editType=='update' }">
        <div id="crash_menu">
			<h2 align="center">修改经销商</h2>
		</div>
      </s:if>
      <s:if test="${editType=='view' }">
        <div id="crash_menu">
			<h2 align="center">查看经销商</h2>
		</div>
      </s:if>
    </tr>       
    <s:if test="${editType=='view' }">
      <tr>
        <td class="bgc_tt short">经销商代码</td>
        <td class="long"><s:textfield name="prpDdealer.dealerCode" 
          id="dealerCode" cssClass='input_w w_15' maxlength="20" readonly="true"/></td>
        <td class="bgc_tt short">经销商名称</td>
        <td class="long"><s:textfield name="prpDdealer.dealerName" 
          id="dealerName" cssClass='input_w w_15' maxlength="120" readonly="true"/></td>  
      </tr>
      <tr>     
        <td class="bgc_tt short">客户代码</td>
        <td class="long"><s:textfield name="prpDdealer.customerCode" 
          id="customerCode" cssClass='input_w w_15' maxlength="20" readonly="true"/></td>       
        <td class="bgc_tt short">机构地址</td>
        <td class="long"><s:textfield name="prpDdealer.addressName" 
          id="addressName" cssClass='input_w w_15' maxlength="20" readonly="true"/></td>
      </tr>
      <tr>
        <td class="bgc_tt short">邮政编码</td>
        <td class="long"><s:textfield name="prpDdealer.postCode" 
          id="postCode" cssClass='input_w w_15' maxlength="20" readonly="true"/></td>       
        <td class="bgc_tt short">经销商类型</td>
        <td class="long"><s:select name="prpDdealer.dealerType" id="dealerType"
          list="#@java.util.HashMap@{'1':'银行','0':'汽车金融公司'}" disabled="true" /></td>
      </tr>
      <tr>
        <td class="bgc_tt short">注册资本</td>
        <td class="long"><s:textfield name="prpDdealer.capital" 
          id="capital" cssClass='input_w w_15' maxlength="20" readonly="true"/></td>
        <td class="bgc_tt short">经销商等级</td>
        <td class="long"><s:textfield name="prpDdealer.dealerGrade" 
          id="dealerGrade" cssClass='input_w w_15' maxlength="20" readonly="true"/></td> 
      </tr>
      <tr>      
        <td class="bgc_tt short">联系人</td>
        <td class="long"><s:textfield name="prpDdealer.linkerName" 
          id="linkerName" cssClass='input_w w_15' maxlength="60" readonly="true"/></td>       
        <td class="bgc_tt short">电话</td>
        <td class="long"><s:textfield name="prpDdealer.phoneNumber" 
          id="phoneNumber" cssClass='input_w w_15' maxlength="20" readonly="true"/></td>
      </tr>
      <tr>
        <td class="bgc_tt short">传真</td>
        <td class="long"><s:textfield name="prpDdealer.faxNumber" 
          id="faxNumber" cssClass='input_w w_15' maxlength="20" readonly="true"/></td>       
        <td class="bgc_tt short">逾期率</td>
        <td class="long"><s:textfield name="prpDdealer.arrearageRate" 
          id="arrearageRate" cssClass='input_w w_15' maxlength="20" readonly="true"/></td> 
      </tr>
      <tr>      
        <td class="bgc_tt short">逾期系数</td>
        <td class="long"><s:textfield name="prpDdealer.arrearageCoff" 
          id="arrearageCoff" cssClass='input_w w_15' maxlength="20" readonly="true"/></td>
        <td class="bgc_tt short">归属机构代码</td>
        <td class="long"><s:textfield name="prpDdealer.comCode" 
          id="comCode" cssClass='input_w w_15' maxlength="20" readonly="true"/></td> 
      </tr>
<%--
      <tr>
        <td class="bgc_tt short">生效日期 </td>
        <td class="long"><input type="text" name="prpDdealer.validDate" value="${prpDdealer.validDate}"
			 class='input_w w_30 Wdate' id="validDate"  onFocus="WdatePicker()" readonly="readonly"></td>
        <td class="bgc_tt short">失效日期</td>
        <td class="long"><input type="text" name="prpDdealer.invalidDate" value="${prpDdealer.invalidDate}"
			 class='input_w w_30 Wdate' id="invalidDate"  onFocus="WdatePicker()" readonly="readonly"></td>
        </tr>
--%>
      <tr>      
         <td class="bgc_tt short">有效标志</td>
        <td class="long">
<%--
        <s:select name="prpDdealer.ValidStatus" id="validStatus"
          list="#@java.util.HashMap@{'1':'有效','0':'无效'}" disabled="true" />
--%>
		<ct:select name="prpDdealer.validStatus" value="${prpDdealer.validStatus}" id="validStatus" sysCode="IMS" codeType="ValidStatus" disabled="true"></ct:select>
		</td>
	   <td class="bgc_tt short">经销车型</td>
        <td class="long"><s:textfield name="prpDdealer.carType" 
          id="carType" cssClass='input_w w_15' maxlength="200" readonly="true"/></td> 
      </tr>
    </s:if>

    <s:elseif test="${editType=='update' }">
       <tr>
        <td class="bgc_tt short">经销商代码<font color="red">*</font></td>
        <td class="long"><s:textfield name="prpDdealer.dealerCode" 
          id="dealerCode" cssClass='input_w w_15 dc-chk dt-nzhs' maxlength="16" readonly="true"/></td>
        <td class="bgc_tt short">经销商名称<font color="red">*</font></td>
        <td class="long"><s:textfield name="prpDdealer.dealerName" 
          id="dealerName" cssClass='input_w w_15 dc-chk' maxlength="120"/></td>
      </tr>
      <tr>        
        <td class="bgc_tt short">客户代码<font color="red">*</font></td>
        <td class="long"><s:textfield name="prpDdealer.customerCode" 
          id="customerCode" cssClass='input_w w_15 dc-chk dt-nzhs' maxlength="16"/></td>       
        <td class="bgc_tt short">机构地址</td>
        <td class="long"><s:textfield name="prpDdealer.addressName" 
          id="addressName" cssClass='input_w w_15' maxlength="60"/></td>
      </tr>
      <tr> 
        <td class="bgc_tt short">邮政编码</td>
        <td class="long"><s:textfield name="prpDdealer.postCode" 
          id="postCode" cssClass='input_w w_15 dt-post' maxlength="6"/></td>       
        <td class="bgc_tt short">经销商类型<font color="red">*</font></td>
        <td class="long"><s:select name="prpDdealer.dealerType" id="dealerType"
          list="#@java.util.HashMap@{'1':'银行','0':'汽车金融公司'}"/></td>
      </tr>
      <tr> 
        <td class="bgc_tt short">注册资本</td>
        <td class="long"><s:textfield name="prpDdealer.capital" 
          id="capital" cssClass='input_w w_15 dt-num' 
          onkeypress="return pressDecimal(event);"
             onblur="checkDecimal(this,14,2,'','');"/></td>
        <td class="bgc_tt short">经销商等级</td>
        <td class="long"><s:textfield name="prpDdealer.dealerGrade" 
          id="dealerGrade" cssClass='input_w w_15' maxlength="2"/></td> 
      </tr>
      <tr>       
        <td class="bgc_tt short">联系人</td>
        <td class="long"><s:textfield name="prpDdealer.linkerName" 
          id="linkerName" cssClass='input_w w_15' maxlength="60"/></td>
        <td class="bgc_tt short">电话</td>
        <td class="long"><s:textfield name="prpDdealer.phoneNumber" 
          id="phoneNumber" cssClass='input_w w_15 dt-mobile' maxlength="30"/></td>
      </tr>
      <tr> 
        <td class="bgc_tt short">传真</td>
        <td class="long"><s:textfield name="prpDdealer.faxNumber" 
          id="faxNumber" cssClass='input_w w_15 dt-num' maxlength="20"/></td>       
        <td class="bgc_tt short">逾期率</td>
        <td class="long"><s:textfield name="prpDdealer.arrearageRate" 
          id="arrearageRate" cssClass='input_w w_15 dt-num' 
          onkeypress="return pressDecimal(event);"
             onblur="checkDecimal(this,14,2,'','');"/></td>  
      </tr>
      <tr>      
        <td class="bgc_tt short">逾期系数</td>
        <td class="long"><s:textfield name="prpDdealer.arrearageCoff" 
          id="arrearageCoff" cssClass='input_w w_15 dt-num' 
          onkeypress="return pressDecimal(event);"
             onblur="checkDecimal(this,14,2,'','');"/></td>
        <td class="bgc_tt short">归属机构代码<font color="red">*</font></td>
		<td class="long"><input name="prpDdealer.comCode" 
          id="comCode" Class='input_y w_15' maxlength="8" VALUE="${prpDdealer.comCode}"
				ondblclick="code_CodeQuery(this, 'ComCode', '0', 'Y','')"
				onkeyup="code_CodeQuery(this, 'ComCode', '0', 'Y','')"
				onchange="code_CodeChange(this, 'ComCode', '0', 'Y','')"/>
		</td>
      </tr>
<%--
      <tr>
        <td class="bgc_tt short">生效日期 </td>
        <td class="long"><input type="text" name="prpDdealer.validDate" value="${prpDdealer.validDate}"
			 class='input_w w_30 Wdate' id="validDate"  onFocus="WdatePicker()"></td>
        <td class="bgc_tt short">失效日期</td>
        <td class="long"><input type="text" name="prpDdealer.invalidDate" value="${prpDdealer.invalidDate}"
			 class='input_w w_30 Wdate' id="invalidDate"  onFocus="WdatePicker()"></td>
        </tr>
--%>
      <tr>    
         <td class="bgc_tt short">有效标志<font color="red">*</font></td>
        <td class="long">
<%--
        <s:select name="prpDdealer.ValidStatus" id="validStatus"
          list="#@java.util.HashMap@{'1':'有效','0':'无效'}"/>
--%>
		<ct:select name="prpDdealer.validStatus" value="${prpDdealer.validStatus}" id="validStatus" sysCode="IMS" codeType="ValidStatus"></ct:select>
		<s:hidden name="prpDdealer.validStatus" id="validStatus" value="${prpDdealer.validStatus}"></s:hidden>
		</td>
          <td class="bgc_tt short">经销车型</td>
        <td class="long"><s:textfield name="prpDdealer.carType" 
          id="carType" cssClass='input_w w_15' maxlength="200"/></td> 
      </tr>
    </s:elseif>
    
    <s:elseif test="${editType=='insert'}">
       <tr>
        <td class="bgc_tt short">经销商代码<font color="red">*</font></td>
        <td class="long"><s:textfield name="prpDdealer.dealerCode" 
          id="dealerCode" cssClass='input_w w_15 dc-chk dt-nzhs' maxlength="16"/></td>
        <td class="bgc_tt short">经销商名称<font color="red">*</font></td>
        <td class="long"><s:textfield name="prpDdealer.dealerName" 
          id="dealerName" cssClass='input_w w_15 dc-chk' maxlength="120"/></td>
      </tr>
      <tr>          
        <td class="bgc_tt short">客户代码<font color="red">*</font></td>
        <td class="long"><s:textfield name="prpDdealer.customerCode" 
          id="customerCode" cssClass='input_w w_15 dc-chk dt-nzhs' maxlength="16"/></td>       
        <td class="bgc_tt short">机构地址</td>
        <td class="long"><s:textfield name="prpDdealer.addressName" 
          id="addressName" cssClass='input_w w_15' maxlength="60"/></td>
      </tr>
      <tr>   
        <td class="bgc_tt short">邮政编码</td>
        <td class="long"><s:textfield name="prpDdealer.postCode" 
          id="postCode" cssClass='input_w w_15 dt-post' maxlength="6"/></td>       
        <td class="bgc_tt short">经销商类型<font color="red">*</font></td>
        <td class="long"><s:select name="prpDdealer.dealerType" id="dealerType"
          list="#@java.util.HashMap@{'1':'银行','0':'汽车金融公司'}"/></td>
      </tr>
      <tr>   
        <td class="bgc_tt short">注册资本</td>
        <td class="long"><s:textfield name="prpDdealer.capital" 
          id="capital" cssClass='input_w w_15 dt-num' 
          onkeypress="return pressDecimal(event);"
             onblur="checkDecimal(this,14,2,'','');"/></td>
        <td class="bgc_tt short">经销商等级</td>
        <td class="long"><s:textfield name="prpDdealer.dealerGrade" 
          id="dealerGrade" cssClass='input_w w_15' maxlength="2"/></td>  
      </tr>
      <tr>        
        <td class="bgc_tt short">联系人</td>
        <td class="long"><s:textfield name="prpDdealer.linkerName" 
          id="linkerName" cssClass='input_w w_15' maxlength="60"/></td>       
        <td class="bgc_tt short">电话</td>
        <td class="long"><s:textfield name="prpDdealer.phoneNumber" 
          id="phoneNumber" cssClass='input_w w_15 dt-mobile' maxlength="30"/></td>
      </tr>
      <tr>   
        <td class="bgc_tt short">传真</td>
        <td class="long"><s:textfield name="prpDdealer.faxNumber" 
          id="faxNumber" cssClass='input_w w_15 dt-num' maxlength="20"/></td>       
        <td class="bgc_tt short">逾期率</td>
        <td class="long"><s:textfield name="prpDdealer.arrearageRate" 
          id="arrearageRate" cssClass='input_w w_15 dt-num' 
          onkeypress="return pressDecimal(event);"
             onblur="checkDecimal(this,14,2,'','');"/></td>   
      </tr>
      <tr>       
        <td class="bgc_tt short">逾期系数</td>
        <td class="long"><s:textfield name="prpDdealer.arrearageCoff" 
          id="arrearageCoff" cssClass='input_w w_15 dt-num' 
          onkeypress="return pressDecimal(event);"
             onblur="checkDecimal(this,14,2,'','');"/></td>
        <td class="bgc_tt short">归属机构代码<font color="red">*</font></td>
		<td class="long">
                <input  name="prpDdealer.comCode" id="comCode"
				Class='input_y w_15' maxlength="8"
				ondblclick="code_CodeQuery(this, 'ComCode', '0', 'Y','')"
				onkeyup="code_CodeQuery(this, 'ComCode', '0', 'Y','')"
				onchange="code_CodeChange(this, 'ComCode', '0', 'Y','')" />
		</td> 
      </tr>
<%--
      <tr>
        <td class="bgc_tt short">生效日期 </td>
        <td class="long"><input type="text" name="prpDdealer.validDate" value="${prpDdealer.validDate}"
			 class='input_w w_30 Wdate' id="validDate"  onFocus="WdatePicker()"></td>
        <td class="bgc_tt short">失效日期</td>
        <td class="long"><input type="text" name="prpDdealer.invalidDate" value="${prpDdealer.invalidDate}"
			 class='input_w w_30 Wdate' id="invalidDate"  onFocus="WdatePicker()"></td>
        </tr>
--%>
      <tr>
         <td class="bgc_tt short">有效标志<font color="red">*</font></td>
        <td class="long">
<%--
        	<s:select name="prpDdealer.ValidStatus" id="validStatus"
          	list="#@java.util.HashMap@{'1':'有效','0':'无效'}"/>
--%>
		<ct:select name="prpDdealer.validStatus" id="validStatus" sysCode="IMS" codeType="ValidStatus" value="1"></ct:select>
		<s:hidden name="prpDdealer.validStatus" id="validStatus" value="1"></s:hidden>
        </td>
          <td class="bgc_tt short">经销车型</td>
        <td class="long"><s:textfield name="prpDdealer.carType" 
          id="carType" cssClass='input_w w_15' maxlength="200"/></td> 
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
           
    	 fm.action="${ctx}/dictionary/updatePrpDdealer.do";
		    fm.submit();
        }
    	
    }
}
function prepareUpdate(){//客户需求，查看页面点击修改要进入修改页面。2009-10-21
	var key1 = document.getElementById("dealerCode").value;
	editRecord("${ctx}/dictionary/prepareUpdatePrpDdealer.do?dealerCode="+key1+"&editType=update");
		
	window.close();
}

function addMethod(){
	if(checkForm()){
	var comCode1 = document.getElementById("comCode").value;
	var validStatus1 = document.getElementById("validStatus").value;
		if(comCode1==""||validStatus1==""){
			alert("请输入完整信息！");
			return;
		}
		if(checkLen()){
			hasSameKey();
		}
	}
}


function checkForm(){
	return YAHOO.quote.data.datacheck('fm');
}
function hasSameKey(){//多主键校验！
	var key1 = document.getElementById("dealerCode").value;
	var url="${ctx}/dictionary/isSameKey.do?tableName=PrpDdealer&values=dealerCode\='"+key1+"'";
	var handleSuccess = function(o){
		if(o.responseText=="sameKey"){
			alert("该汽车经销商代码已存在！");
		}else{
			fm.action="${ctx}/dictionary/insertPrpDdealer.do";
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



