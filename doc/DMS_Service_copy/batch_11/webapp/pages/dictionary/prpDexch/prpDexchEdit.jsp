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
<title>兑换率</title>
<%@include file="/common/meta_css.jsp"%>
<%@ include file="/common/i18njs.jsp"%>
</head>
<!--  onkeydown方法禁用BackSpace onload方法光标定位在首个输入框 -->
<body id="all_title" onkeydown="keyDown()" onload="fm.exchDate.focus()">
<div id="wrapper">
<div id="container">

<s:form action="${ctx}/dictionary/updatePrpDexch.do" name="fm" method="post">
<s:hidden name="editType" id="editType" value="${editType}"></s:hidden>
<s:hidden name="prpDexch.flag" id="flag" value="${prpDexch.flag}"></s:hidden>
<s:hidden name="deployCom" id="deployCom" value="${deployCom}"></s:hidden>
	<table width="100%" class="fix_table">
    <tr class="top">
      <s:if test="${editType=='insert'}">
      <div id="crash_menu">
<h2 align="center">增加兑换率</h2>
</div>
      </s:if>
      <s:if test="${editType=='update' }">
     <div id="crash_menu">
<h2 align="center">修改兑换率</h2>
</div>
      </s:if>
      <s:if test="${editType=='view' }">
     <div id="crash_menu">
<h2 align="center">查看兑换率</h2>
</div>
      </s:if>
    </tr>
    <s:if test="${editType=='view' }">
      <tr>
        <td class="bgc_tt short">汇率日期</td>
        <td class="long">
		<input name="prpDexch.id.exchDate" type="text" value="${prpDexch.id.exchDate}" 
			 class='Wdate dc-chk dt-date' id="exchDate"  onFocus="" readonly="readonly">
<%--
		<input name="prpDexch.id.exchDate" type="text" value="${prpDexch.id.exchDate}" 
id="exchDate" Class='input_w w_13 dc-chk dt-date' maxlength="20" readonly="readonly">

        <s:textfield name="prpDexch.id.exchDate" id="exchDate" cssClass='input_w w_13 dc-chk dt-date' maxlength="20" readonly="true">
			<s:param name="value"><s:date name="prpDexch.id.exchDate" format="yyyy-MM-dd"/></s:param> 
		</s:textfield>
		<img 
				src="${ctx}/pages/image/time/date_icon.gif" alt="点击显示时间面板"
				id="imgBtn1" width="14" height="14" style="visibility: hidden"/>
			<span class="calender-panel">
				<div id="calContainer1" style="position: absolute;"></div>
			</span>
--%>
		</td>



        <td class="bgc_tt short">基准币别</td>
        <td class="long"><s:textfield name="prpDexch.id.baseCurrency" 
          id="baseCurrency" cssClass='input_w w_15 dc-chk' maxlength="20" readonly="true"/></td>
      </tr>
      <tr>       
        <td class="bgc_tt short">兑换币别</td>
        <td class="long"><s:textfield name="prpDexch.id.exchCurrency" 
          id="exchCurrency" cssClass='input_w w_15 dc-chk' maxlength="20" readonly="true"/></td>       
        <td class="bgc_tt short">基准</td>
        <td class="long"><s:textfield name="prpDexch.base" 
          id="base" cssClass='input_w w_15' maxlength="20" readonly="true"/></td>
      </tr>
      <tr>
        <td class="bgc_tt short">兑换汇率</td>
        <td class="long"><s:textfield name="prpDexch.exchRate" 
          id="exchRate" cssClass='input_w w_15 dt-num' maxlength="20" readonly="true"/></td>       
        <td class="bgc_tt short">买进价</td>
		<td class="long"><s:textfield name="prpDexch.buyPrice" 
          id="buyPrice" cssClass='input_w w_15 dt-num' maxlength="20" readonly="true"/></td> 
      </tr>
      <tr>  
        <td class="bgc_tt short">卖出价</td>
        <td class="long"><s:textfield name="prpDexch.salePrice" 
          id="salePrice" cssClass='input_w w_15 dt-num' maxlength="20" readonly="true"/></td>
        <td class="bgc_tt short">现价</td>
        <td class="long"><s:textfield name="prpDexch.cashPrice" 
          id="cashPrice" cssClass='input_w w_15 dt-num' maxlength="20" readonly="true"/></td>
      </tr>
    </s:if>

    <s:elseif test="${editType=='update' }">
       <tr>
        <td class="bgc_tt short">汇率日期<font color="red">*</font></td>
        <td class="long">
		<input name="prpDexch.id.exchDate" type="text" value="${prpDexch.id.exchDate}" 
			 class='Wdate dc-chk dt-date' id="exchDate"  onFocus="" readonly="readonly">
<%--
		<input name="prpDexch.id.exchDate" type="text" value="${prpDexch.id.exchDate}" 
			id="exchDate" Class='input_w w_13 dc-chk dt-date' maxlength="20" readonly="readonly">

        <s:textfield name="prpDexch.id.exchDate" id="exchDate" cssClass='input_w w_15 dt-date dc-chk' maxlength="20" readonly="true">
			<s:param name="value"><s:date name="prpDexch.id.exchDate" format="yyyy-MM-dd"/></s:param>
		</s:textfield>
			<img
				src="${ctx}/pages/image/time/date_icon.gif" alt="点击显示时间面板"
				id="imgBtn1" width="14" height="14" style="visibility: hidden"/>
			<span class="calender-panel">
				<div id="calContainer1" style="position: absolute;"></div>
			</span>
--%>
		</td>
        <td class="bgc_tt short">基准币别<font color="red">*</font></td>
<%--
		<td>
			<div id="validStatusMapDiv" class="selectui-indiv">
				<div class="selectConfig">
					<div class="codeType">StaticSelect</div>
				</div>
				<ce:select name="prpDexch.id.baseCurrency" id="baseCurrency" cssClass="selectui-input-up input_y w_p90 dc-chk" value="${prpDexch.id.baseCurrency}" list="currencyMap" />
			 </div>
		</td>
--%>
        <td class="long">
        <s:textfield name="prpDexch.id.baseCurrency" 
          id="baseCurrency" cssClass='input_w w_15 dc-chk' maxlength="3" readonly="true"/>
		</td> 
      </tr>
      <tr>
        <td class="bgc_tt short">兑换币别<font color="red">*</font></td>
<%--	
		<td>
			<div id="validStatusMapDiv" class="selectui-indiv">
				<div class="selectConfig">
					<div class="codeType">StaticSelect</div>
				</div>
				<ce:select name="prpDexch.id.exchCurrency" id="exchCurrency" cssClass="selectui-input-up input_y w_p90 dc-chk" value="${prpDexch.id.exchCurrency}" list="currencyMap" disabled="true"/>
			 </div>
		</td>
--%>
        <td class="long"><s:textfield name="prpDexch.id.exchCurrency" 
          id="exchCurrency" cssClass='input_w w_15 dc-chk' maxlength="3" readonly="true"/></td>
        <td class="bgc_tt short">基准</td>
        <td class="long"><s:textfield name="prpDexch.base" 
          id="base" cssClass='input_w w_15 dt-zzs' maxlength="8"/></td>
      </tr>
      <tr>
        <td class="bgc_tt short">兑换汇率</td>
        <td class="long"><s:textfield name="prpDexch.exchRate" 
          id="exchRate" cssClass='input_w w_15 dt-lmoney' maxlength="11"
           onkeypress="return pressDecimal(event);"
             onblur="checkDecimal(this,10,4,'','');"/></td>       
        <td class="bgc_tt short">买进价</td>
		<td class="long"><s:textfield name="prpDexch.buyPrice" 
          id="buyPrice" cssClass='input_w w_15 dt-lmoney' maxlength="11"
          onkeypress="return pressDecimal(event);"
             onblur="checkDecimal(this,10,4,'','');"/></td>
      </tr>
      <tr>
        <td class="bgc_tt short">卖出价</td>
        <td class="long"><s:textfield name="prpDexch.salePrice" 
          id="salePrice" cssClass='input_w w_15 dt-lmoney' maxlength="11"
          onkeypress="return pressDecimal(event);"
             onblur="checkDecimal(this,10,4,'','');"/></td>
        <td class="bgc_tt short">现价</td>
        <td class="long"><input type="text" name="prpDexch.cashPrice" 
          id="cashPrice" Class='input_w w_15 dt-lmoney' maxlength="11" value="${prpDexch.cashPrice}"
          onkeypress="return pressDecimal(event);"
             onblur="checkDecimal(this,10,4,'','');"/></td>       
      </tr>
    </s:elseif>
    
    <s:elseif test="${editType=='insert'}">
        <tr>
        <td class="bgc_tt short">汇率日期<font color="red">*</font></td>
        <td class="long">
		<input name="prpDexch.id.exchDate" type="text" value="${prpDexch.id.exchDate}" 
			 class='Wdate dc-chk dt-date' id="exchDate"  onFocus="WdatePicker()">

<%--
		<input name="prpDexch.id.exchDate" type="text" value="${prpDexch.id.exchDate}" 
			id="exchDate" Class='input_w w_13 dc-chk dt-date' maxlength="20" readonly="readonly">

        <s:textfield name="prpDexch.id.exchDate" id="exchDate" cssClass='input_w w_15 dt-date dc-chk' maxlength="20">
          <s:param name="value"><s:date name="prpDexch.id.exchDate" format="yyyy-MM-dd"/></s:param>
        </s:textfield>

			<img
				src="${ctx}/pages/image/time/date_icon.gif" alt="点击显示时间面板"
				id="imgBtn1" width="14" height="14" /> 
			<span class="calender-panel">
				<div id="calContainer1" style="position: absolute;"></div>
			</span>
--%>
			</td>
        <td class="bgc_tt short">基准币别<font color="red">*</font></td>
		<td class="long">
<%--
			<div id="validStatusMapDiv" class="selectui-indiv">
				<div class="selectConfig">
					<div class="codeType">StaticSelect</div>
				</div>
				<ce:select name="prpDexch.id.baseCurrency" id="baseCurrency" cssClass="selectui-input-up input_y w_p90 dc-chk" value="${prpDexch.id.baseCurrency}" list="currencyMap" />
			 </div>
--%>
			<ct:select name="prpDexch.id.baseCurrency" id="baseCurrency" cssClass="selectui-input-up input_y w_p90 dc-chk" value="${prpDexch.id.baseCurrency}" sysCode="DMS" codeType="Currency"></ct:select>
		</td>
<%--
        <td class="long"><s:textfield name="prpDexch.id.baseCurrency"
          id="baseCurrency" cssClass='input_w w_15 dc-chk' maxlength="3"/></td> 
--%>
      </tr>
      <tr>      
        <td class="bgc_tt short">兑换币别<font color="red">*</font></td>
		<td class="long">
<%--
			<div id="validStatusMapDiv" class="selectui-indiv">
				<div class="selectConfig">
					<div class="codeType">StaticSelect</div>
				</div>
				<ce:select name="prpDexch.id.exchCurrency" id="exchCurrency" cssClass="selectui-input-up input_y w_p90 dc-chk" value="${prpDexch.id.exchCurrency}" list="currencyMap" />
			 </div>
--%>
			<ct:select name="prpDexch.id.exchCurrency" id="exchCurrency" cssClass="selectui-input-up input_y w_p90 dc-chk" value="${prpDexch.id.exchCurrency}" sysCode="IMS" codeType="Currency"></ct:select>
		</td>
<%--
        <td class="long"><s:textfield name="prpDexch.id.exchCurrency" 
          id="exchCurrency" cssClass='input_w w_15 dc-chk' maxlength="3"/></td>  
--%>     
        <td class="bgc_tt short">基准</td>
        <td class="long"><s:textfield name="prpDexch.base" 
          id="base" cssClass='input_w w_15 dt-zzs' maxlength="8"/></td>
      </tr>
      <tr>
        <td class="bgc_tt short">兑换汇率</td>
        <td class="long"><s:textfield name="prpDexch.exchRate" 
          id="exchRate" cssClass='input_w w_15 dt-lmoney' maxlength="11"
          onkeypress="return pressDecimal(event);"
             onblur="checkDecimal(this,10,4,'','');"/></td>       
        <td class="bgc_tt short">买进价</td>
		<td class="long"><s:textfield name="prpDexch.buyPrice" 
          id="buyPrice" cssClass='input_w w_15 dt-lmoney' maxlength="11"
          onkeypress="return pressDecimal(event);"
             onblur="checkDecimal(this,10,4,'','');"/></td> 
      </tr>
      <tr>  
        <td class="bgc_tt short">卖出价</td>
        <td class="long"><s:textfield name="prpDexch.salePrice" 
          id="salePrice" cssClass='input_w w_15 dt-lmoney' maxlength="11"
          onkeypress="return pressDecimal(event);"
             onblur="checkDecimal(this,10,4,'','');"/></td>
        <td class="bgc_tt short">现价</td>
        <td class="long"><input type="text" name="prpDexch.cashPrice" 
          id="cashPrice" Class='input_w w_15 dt-lmoney' maxlength="11"
          onkeypress="return pressDecimal(event);"
             onblur="checkDecimal(this,10,4,'','');"/></td>       
      </tr>
    </s:elseif>
    
  </table>
  
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr align="center" class="top">
	  <c:if test="${editType=='view'}">
        <td>
         <% if(SyncConstants.ComCode_Head.equals(deployCom)){%>
         <button type="button" value="" class="button_ty"
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
          onclick="return updateMethod()"><span><em>保存</em></span></button>
<!--        <input type="button" value="保存" class="button_ty"-->
<!--          onclick="return updateMethod()">-->
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
function checkBase(){
	var base = document.getElementById("base").value;
	if(trim(base) == "" && base != ""){
	alert("基准不能输入空格");	
	return false;
	}
	else
	return true;
}
function updateMethod(){
    if(checkForm()){
        if(checkLen()){
        	if(checkBase()){
        	  fm.action="${ctx}/dictionary/updatePrpDexch.do";
		      fm.submit();
        	}	  
        }
    }
}
function prepareUpdate(){//客户需求，查看页面点击修改要进入修改页面。2009-10-21
	var key1 = document.getElementById("baseCurrency").value;
	var key2 = document.getElementById("exchCurrency").value;
	var key3 = document.getElementById("exchDate").value;
	editRecord("${ctx}/dictionary/prepareUpdatePrpDexch.do?prpDexch.id.baseCurrency="+key1+"&prpDexch.id.exchCurrency="+key2+"&showTime="+key3+"&editType=update");
	window.close();
}
//function addMethod(){
//	if(true){
//		var cc = document.getElementById("exchDate").value;
//		var bc = document.getElementById("baseCurrency").value;
//		var bc = document.getElementById("exchCurrency").value;
//		if(cc==""){
//			alert("汇率日期不能为空！");
//			document.getElementById("exchDate").focus();      
//		}else if(bc==""){
//			alert("基准币别不能为空！");
//			document.getElementById("baseCurrency").focus();      
//		}else if(bc==""){
//			alert("兑换币别不能为空！");
//			document.getElementById("exchCurrency").focus();      
//		}else {
//			if(hasSameKey()){
//				return false;
//			}
//		}
//		
//	}
//}
  function addMethod(){
  var cc = document.getElementById("exchDate").value;
  if(cc==""){
			alert("汇率日期不能为空！");
			document.getElementById("exchDate").focus();      
		}
	if(checkForm()){
		if(checkLen()){
			hasSameKey();
		}
	}
  }

function checkForm(){
return YAHOO.quote.data.datacheck('fm');
}
function hasSameKey(){//多主键校验！
	var key1 = document.getElementById("exchDate").value;
	var key2 = document.getElementById("baseCurrency").value;
	var key3 = document.getElementById("exchCurrency").value;
	var url="${ctx}/dictionary/checkPrpDexchKey.do?prpDexch.id.exchDate="+key1+"&prpDexch.id.baseCurrency="+key2+"&prpDexch.id.exchCurrency="+key3;
	var handleSuccess = function(o){
		if(o.responseText=="sameKey"){
			alert("该兑换率已存在！");
			return true;
		}else{
			fm.action="${ctx}/dictionary/insertPrpDexch.do";
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
//init_calendar("calContainer1","imgBtn1","exchDate","");
//}
//YAHOO.util.Event.addListener(window,'load',init);


/*****时间控件******/
//
//init_calendar("calContainer2","imgBtn2","endDate","toSecond");
</script>



