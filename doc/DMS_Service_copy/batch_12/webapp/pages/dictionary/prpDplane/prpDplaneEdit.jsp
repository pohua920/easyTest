<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>飞机代码</title>
<%@include file="/common/meta_css.jsp"%>
<%@ include file="/common/i18njs.jsp"%>
<%-- moidfy  update by tongziliang 2011-10-09 reason:修改页面的按钮样式和优化页面样式 --%>
<link rel="stylesheet" href="${ctx}/style/popup/login.css" type="text/css"></link>
</head>
<!--  onkeydown方法禁用BackSpace onload方法光标定位在首个输入框 -->
<body id="all_title" onkeydown="keyDown()" onload="fm.licenceNo.focus()">
<div id="wrapper">
<div id="container">

<s:form action="${ctx}/dictionary/updatePrpDplane.do" name="fm" method="post">
<s:hidden name="editType" id="editType" value="${editType}"></s:hidden>
<s:hidden name="prpDplane.flag" id="flag" value="${prpDplane.flag}"></s:hidden>
	<table width="100%" class="fix_table">
    <tr class="top">
      <s:if test="${editType=='insert' }">
      <div id="crash_menu">
<h2 align="center">增加飞机代码</h2>
</div>
      </s:if>
      <s:if test="${editType=='update' }">
      <div id="crash_menu">
<h2 align="center">修改飞机代码</h2>
</div>
      </s:if>
      <s:if test="${editType=='view' }">
      <div id="crash_menu">
<h2 align="center">查看飞机代码</h2>
</div>
      </s:if>
    </tr>       
    <s:if test="${editType=='view' }">
      <tr>
        <td class="bgc_tt short">注册号</td>
        <td class="long"><s:textfield name="prpDplane.licenceNo" 
          id="licenceNo" cssClass='input_w w_15' maxlength="30" readonly="true"/></td>
        <td class="bgc_tt short">飞机种类</td>
         <td class="long">
         <s:select name="prpDplane.planeType" id="planeType" disabled="true"
          	list="#@java.util.HashMap@{'1':'宽体机','2':'窄体机','3':'混合机'}"/></td>  
      </tr>
      <tr>    
        <td class="bgc_tt short">机型</td>
        <td class="long"><s:textfield name="prpDplane.model" 
          id="model" cssClass='input_w w_15' maxlength="60" readonly="true"/></td>       
        <td class="bgc_tt short">出厂号</td>
        <td class="long"><s:textfield name="prpDplane.factoryNo" 
          id="factoryNo" cssClass='input_w w_15' maxlength="30" readonly="true"/></td>
      </tr>
      <tr>
        <td class="bgc_tt short">出厂日期</td>
        <td class="long" nowrap="nowrap">
        <input type="text" name="prpDplane.factoryDate" value="${prpDplane.factoryDate}"
			 class='input_w w_15 Wdate' id="factoryDate"  onFocus="" readonly="readonly">
<%--       
        <s:textfield name="prpDplane.factoryDate" 
          id="factoryDate" cssClass='input_w w_15 dt-date' maxlength="20" readonly="true">
          	<s:param name="value"><s:date name="prpDplane.factoryDate" format="yyyy-MM-dd"/></s:param>
          </s:textfield>
			<img
				src="${ctx}/pages/image/time/date_icon.gif" alt="点击显示时间面板"
				id="imgBtn1" width="14" height="14" style="visibility: hidden"/> 
			<span class="calender-panel">
				<div id="calContainer1" style="position: absolute;"></div>
			</span>
 --%>			
			</td>    
        <td class="bgc_tt short">制造年份</td>
		<td class="long" nowrap="nowrap">
		<input type="text" name="prpDplane.makeYear" value="${prpDplane.makeYear}"
			 class='input_w w_15 Wdate' id="makeYear"  onFocus="" readonly="readonly">
<%--
		<s:textfield name="prpDplane.makeYear" 
          id="makeYear" cssClass='input_w w_15 dt-date' maxlength="20" readonly="true">
          	<s:param name="value"><s:date name="prpDplane.makeYear" format="yyyy-MM-dd"/></s:param>
          </s:textfield>
			<img
				src="${ctx}/pages/image/time/date_icon.gif" alt="点击显示时间面板"
				id="imgBtn2" width="14" height="14" style="visibility: hidden"/> 
			<span class="calender-panel">
				<div id="calContainer2" style="position: absolute;"></div>
			</span>
--%>
			</td>
      </tr>
      <tr>
        <td class="bgc_tt short">航空公司中文名</td>
        <td class="long"><s:textfield name="prpDplane.airlineCname" 
          id="airlineCname" cssClass='input_w w_15' maxlength="120" readonly="true"/></td>
        <td class="bgc_tt short">航空公司英文名</td>
        <td class="long"><s:textfield name="prpDplane.airlineEname" 
          id="airlineEname" cssClass='input_w w_15' maxlength="120" readonly="true"/></td>  
      </tr>
      <tr>     
        <td class="bgc_tt short">飞行范围</td>
        <td class="long"><s:textfield name="prpDplane.range" 
          id="range" cssClass='input_w w_15' maxlength="20" readonly="true"/></td>       
        <td class="bgc_tt short">座位数</td>
        <td class="long"><s:textfield name="prpDplane.seatCount" 
          id="seatCount" cssClass='input_w w_15' maxlength="5" readonly="true"/></td>
      </tr>
      <tr>
        <td class="bgc_tt short">购/租情况</td>
		<td class="long"><s:select name="prpDplane.loanStaus" 
          list="#@java.util.HashMap@{'L':'租','P':'购'}" disabled="true" /></td>
        <td class="bgc_tt short">飞机用途</td>
        <td class="long"><s:select name="prpDplane.planeUsage" 
          list="#@java.util.HashMap@{'1':'CARGO','2':'PASSENGER','3':'BOTH'}" disabled="true" /></td>
      </tr>
      <tr>
        <td class="bgc_tt short">美元投保金额</td>
        <td class="long"><s:textfield name="prpDplane.usdAmount" 
          id="usdAmount" cssClass='input_w w_15' maxlength="20" readonly="true"/></td>
        <td class="bgc_tt short">日元投保金额</td>
        <td class="long"><s:textfield name="prpDplane.jpyAmount" 
          id="jpyAmount" cssClass='input_w w_15' maxlength="20" readonly="true"/></td>  
      </tr>
	      <tr>
        <td class="bgc_tt short">生效日期 </td>
        <td class="long"><input type="text" name="prpDplane.validDate" value="${prpDplane.validDate}"
			 class='input_w w_30 Wdate' id="validDate"  onFocus="WdatePicker()" readonly="readonly"></td>
        <td class="bgc_tt short">失效日期</td>
        <td class="long"><input type="text" name="prpDplane.invalidDate" value="${prpDplane.invalidDate}"
			 class='input_w w_30 Wdate' id="invalidDate"  onFocus="WdatePicker()" readonly="readonly"></td>
        </tr>
      <tr>
       <td class="bgc_tt short">有效标志</td>
        <td class="long">
<!--
        <s:select name="prpDplane.ValidStatus" id="validStatus"
          list="#@java.util.HashMap@{'1':'有效','0':'无效'}" disabled="true" />
-->
		<ct:select name="prpDplane.validStatus" value="${prpDplane.validStatus}" id="validStatus" sysCode="IMS" codeType="ValidStatus" disabled="true"></ct:select>
		</td>
        <td class="bgc_tt short">备注</td>
		<td class="long"><s:textfield name="prpDplane.remark" 
          id="remark" cssClass='input_w w_15' maxlength="40" readonly="true"/></td>
      </tr>
    </s:if>

    <s:elseif test="${editType=='update' }">
          <tr>
        <td class="bgc_tt short">注册号<font color="red">*</font></td>
        <td class="long"><s:textfield name="prpDplane.licenceNo" 
          id="licenceNo" cssClass='input_w w_15 dc-chk dt-nzhs' maxlength="30" readonly="true"/></td>
        <td class="bgc_tt short">飞机种类<font color="red">*</font></td>
        <td class="long"><s:select name="prpDplane.planeType" id="planeType"
          		list="#@java.util.HashMap@{'1':'宽体机','2':'窄体机','3':'混合机'}"/>
		</td>

      </tr>
      <tr>
        <td class="bgc_tt short">机型</td>
        <td class="long"><s:textfield name="prpDplane.model" 
          id="model" cssClass='input_w w_15' maxlength="60"/></td>       
        <td class="bgc_tt short">出厂号</td>
        <td class="long"><s:textfield name="prpDplane.factoryNo" 
          id="factoryNo" cssClass='input_w w_15' maxlength="30"/></td>
      </tr>
      <tr>
        <td class="bgc_tt short">出厂日期</td>
        <td class="long" nowrap="nowrap">

       <input type="text" name="prpDplane.factoryDate" value="${prpDplane.factoryDate}"
			 class='input_w w_15 Wdate' id="factoryDate"  onFocus="WdatePicker()">
<%--
        <s:textfield name="prpDplane.factoryDate" 
          id="factoryDate" cssClass='input_w w_15 dt-date' maxlength="20" readonly="true">
          	<s:param name="value"><s:date name="prpDplane.factoryDate" format="yyyy-MM-dd"/></s:param>
          </s:textfield>
			<img
				src="${ctx}/pages/image/time/date_icon.gif" alt="点击显示时间面板"
				id="imgBtn1" width="14" height="14" /> 
			<span class="calender-panel">
				<div id="calContainer1" style="position: absolute;"></div>
			</span>
--%>			
			</td>
        <td class="bgc_tt short">制造年份</td>
		<td class="long" nowrap="nowrap">
		 <input type="text" name="prpDplane.makeYear" value="${prpDplane.makeYear}"
			 class='input_w w_15 Wdate' id="makeYear"  onFocus="WdatePicker()">
<%--		
		<s:textfield name="prpDplane.makeYear" 
          id="makeYear" cssClass='input_w w_15 dt-date' maxlength="20" readonly="true">
          	<s:param name="value"><s:date name="prpDplane.makeYear" format="yyyy-MM-dd"/></s:param>
          </s:textfield>
			<img
				src="${ctx}/pages/image/time/date_icon.gif" alt="点击显示时间面板"
				id="imgBtn2" width="14" height="14"/> 
			<span class="calender-panel">
				<div id="calContainer2" style="position: absolute;"></div>
			</span>
--%>			
			</td>
      </tr>
      <tr>
        <td class="bgc_tt short">航空公司中文名</td>
        <td class="long"><s:textfield name="prpDplane.airlineCname" 
          id="airlineCname" cssClass='input_w w_15' maxlength="120"/></td>
        <td class="bgc_tt short">航空公司英文名</td>
        <td class="long"><s:textfield name="prpDplane.airlineEname" 
          id="airlineEname" cssClass='input_w w_15' maxlength="120"/></td>
      </tr>
      <tr>       
        <td class="bgc_tt short">飞行范围</td>
        <td class="long"><s:textfield name="prpDplane.range" 
          id="range" cssClass='input_w w_15' maxlength="6"/></td>       
        <td class="bgc_tt short">座位数</td>
        <td class="long"><s:textfield name="prpDplane.seatCount" 
          id="seatCount" cssClass='input_w w_15 dt-num' maxlength="5"/></td>
      </tr>
      <tr>
        <td class="bgc_tt short">购/租情况</td>
		<td class="long"><s:select name="prpDplane.loanStaus" 
          list="#@java.util.HashMap@{'L':'租','P':'购'}"/></td>
        <td class="bgc_tt short">飞机用途</td>
       <td class="long"><s:select name="prpDplane.planeUsage" 
          list="#@java.util.HashMap@{'1':'CARGO','2':'PASSENGER','3':'BOTH'}" /></td> 
      </tr>
      <tr>
        <td class="bgc_tt short">美元投保金额</td>
        <td class="long"><s:textfield name="prpDplane.usdAmount" 
          id="usdAmount" cssClass='input_w w_15 dt-num'
            onkeypress="return pressDecimal(event);"
             onblur="checkDecimal(this,14,2,'','');"/></td>
        <td class="bgc_tt short">日元投保金额</td>
        <td class="long"><s:textfield name="prpDplane.jpyAmount" 
          id="jpyAmount" cssClass='input_w w_15 dt-num' 
          onkeypress="return pressDecimal(event);"
             onblur="checkDecimal(this,14,2,'','');"/></td>
      </tr>
	      <tr>
        <td class="bgc_tt short">生效日期 </td>
        <td class="long"><input type="text" name="prpDplane.validDate" value="${prpDplane.validDate}"
			 class='input_w w_30 Wdate' id="validDate"  onFocus="WdatePicker()"></td>
        <td class="bgc_tt short">失效日期</td>
        <td class="long"><input type="text" name="prpDplane.invalidDate" value="${prpDplane.invalidDate}"
			 class='input_w w_30 Wdate' id="invalidDate"  onFocus="WdatePicker()"></td>
        </tr>
      <tr>       
       <td class="bgc_tt short">有效标志</td>
        <td class="long">
<!--
        <s:select name="prpDplane.ValidStatus" id="validStatus"
          list="#@java.util.HashMap@{'1':'有效','0':'无效'}"/>
-->
		<ct:select name="prpDplane.validStatus" value="${prpDplane.validStatus}" id="validStatus" sysCode="IMS" codeType="ValidStatus" disabled="true"></ct:select>
		<s:hidden name="prpDplane.validStatus" id="validStatus" value="${prpDplane.validStatus}"></s:hidden>
		</td>
        <td class="bgc_tt short">备注</td>
		<td class="long"><s:textfield name="prpDplane.remark" 
          id="remark" cssClass='input_w w_15' maxlength="40"/></td>
      </tr>
    </s:elseif>
    
    <s:elseif test="${editType=='insert'}">
       <tr>
        <td class="bgc_tt short">注册号<font color="red">*</font></td>
        <td class="long"><s:textfield name="prpDplane.licenceNo" 
          id="licenceNo" cssClass='input_w w_15 dc-chk dt-nzhs' maxlength="30"/></td>
        <td class="bgc_tt short">飞机种类<font color="red">*</font></td>
         <td class="long"><s:select name="prpDplane.planeType" id="planeType"
          		list="#@java.util.HashMap@{'1':'宽体机','2':'窄体机','3':'混合机'}"/></td>
      </tr>
      <tr>      
        <td class="bgc_tt short">机型</td>
        <td class="long"><s:textfield name="prpDplane.model" 
          id="model" cssClass='input_w w_15' maxlength="60"/></td>       
        <td class="bgc_tt short">出厂号</td>
        <td class="long"><s:textfield name="prpDplane.factoryNo" 
          id="factoryNo" cssClass='input_w w_15' maxlength="30"/></td>
      </tr>
      <tr>
        <td class="bgc_tt short">出厂日期</td>
 		<td class="long" nowrap="nowrap">
		<input type="text" name="prpDplane.factoryDate" value="${prpDplane.factoryDate}"
			 class='input_w w_15 Wdate' id="factoryDate"  onFocus="WdatePicker()">
<%--
       <s:textfield name="prpDplane.factoryDate" 
          id="factoryDate" cssClass='input_w w_15 dt-date' maxlength="20" readonly="true">
          		<s:param name="value"><s:date name="prpDplane.factoryDate" format="yyyy-MM-dd"/></s:param>
          </s:textfield>
			<img
				src="${ctx}/pages/image/time/date_icon.gif" alt="点击显示时间面板"
				id="imgBtn1" width="14" height="14" /> 
			<span class="calender-panel">
				<div id="calContainer1" style="position: absolute;"></div>
			</span>  
--%>			
			  </td>
        <td class="bgc_tt short">制造年份</td>
		<td class="long" nowrap="nowrap">
		<input type="text" name="prpDplane.makeYear" value="${prpDplane.makeYear}"
			 class='input_w w_15 Wdate' id="makeYear"  onFocus="WdatePicker()">
<%--		
		<s:textfield name="prpDplane.makeYear" 
          id="makeYear" cssClass='input_w w_15 dt-date' maxlength="20" readonly="true">
          	<s:param name="value"><s:date name="prpDplane.makeYear" format="yyyy-MM-dd"/></s:param>
          </s:textfield>
			<img
				src="${ctx}/pages/image/time/date_icon.gif" alt="点击显示时间面板"
				id="imgBtn2" width="14" height="14" /> 
			<span class="calender-panel">
				<div id="calContainer2" style="position: absolute;"></div>
			</span>
--%>			
			</td>
      </tr>
      <tr>
        <td class="bgc_tt short">航空公司中文名</td>
        <td class="long"><s:textfield name="prpDplane.airlineCname" 
          id="airlineCname" cssClass='input_w w_15' maxlength="120"/></td>
        <td class="bgc_tt short">航空公司英文名</td>
        <td class="long"><s:textfield name="prpDplane.airlineEname" 
          id="airlineEname" cssClass='input_w w_15' maxlength="120"/></td>  
      </tr>
      <tr>     
        <td class="bgc_tt short">飞行范围</td>
        <td class="long"><s:textfield name="prpDplane.range" 
          id="range" cssClass='input_w w_15' maxlength="6"/></td>       
        <td class="bgc_tt short">座位数</td>
        <td class="long"><s:textfield name="prpDplane.seatCount" 
          id="seatCount" cssClass='input_w w_15 dt-num' maxlength="5"/></td>
      </tr>
      <tr>
        <td class="bgc_tt short">购/租情况</td>
		<td class="long"><s:select name="prpDplane.loanStaus" 
          list="#@java.util.HashMap@{'L':'租','P':'购'}"/></td>
        <td class="bgc_tt short">飞机用途</td>
       <td class="long"><s:select name="prpDplane.planeUsage" 
          list="#@java.util.HashMap@{'1':'CARGO','2':'PASSENGER','3':'BOTH'}"/></td>
      </tr>
      <tr>
        <td class="bgc_tt short">美元投保金额</td>
        <td class="long"><s:textfield name="prpDplane.usdAmount" 
          id="usdAmount" cssClass='input_w w_15 dt-num' 
          onkeypress="return pressDecimal(event);"
             onblur="checkDecimal(this,14,2,'','');"/></td>
        <td class="bgc_tt short">日元投保金额</td>
        <td class="long"><s:textfield name="prpDplane.jpyAmount" 
          id="jpyAmount" cssClass='input_w w_15 dt-num' 
          onkeypress="return pressDecimal(event);"
             onblur="checkDecimal(this,14,2,'','');"/></td>
      </tr>
	      <tr>
        <td class="bgc_tt short">生效日期 </td>
        <td class="long"><input type="text" name="prpDplane.validDate" value="${prpDplane.validDate}"
			 class='input_w w_30 Wdate' id="validDate"  onFocus="WdatePicker()"></td>
        <td class="bgc_tt short">失效日期</td>
        <td class="long"><input type="text" name="prpDplane.invalidDate" value="${prpDplane.invalidDate}"
			 class='input_w w_30 Wdate' id="invalidDate"  onFocus="WdatePicker()"></td>
        </tr>
      <tr>
       <td class="bgc_tt short">有效标志</td>
        <td class="long">
<!--
        <s:select name="prpDplane.validStatus" id="validStatus"
          list="#@java.util.HashMap@{'1':'有效','0':'无效'}"/>
-->
		<ct:select name="prpDplane.validStatus" id="validStatus" sysCode="IMS" codeType="ValidStatus" value="1" disabled="true"></ct:select>
		<s:hidden name="prpDplane.validStatus" id="validStatus" value="1"></s:hidden>
		</td>
        <td class="bgc_tt short">备注</td>
		<td class="long"><s:textfield name="prpDplane.remark" 
          id="remark" cssClass='input_w w_15' maxlength="40"/></td>
      </tr>
    </s:elseif>
    
  </table>
  
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr align="center" class="top">
	  <c:if test="${editType=='view' }">
        <td>
        <button type="button" value="" 
        onclick="prepareUpdate()"><span><em>修改</em></span></button>
<!--        <input type="button" value="修改" class="button_ty"-->
<!--        onclick="prepareUpdate()">-->
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
	    fm.action="${ctx}/dictionary/updatePrpDplane.do";
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
	var key1 = document.getElementById("licenceNo").value;
	editRecord("${ctx}/dictionary/prepareUpdatePrpDplane.do?licenceNo="+key1+"&editType=update");
	window.close();
}

function checkForm(){
	return YAHOO.quote.data.datacheck('fm');
}

function hasSameKey(){//多主键校验！
	var key1 = document.getElementById("licenceNo").value;
	var url="${ctx}/dictionary/isSameKey.do?tableName=PrpDplane&values=licenceNo\='"+key1+"'";
	var handleSuccess = function(o){
		if(o.responseText=="sameKey"){
			alert("该飞机代码已存在！");
		}else{
			fm.action="${ctx}/dictionary/insertPrpDplane.do";
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
/*****时间控件******/
//init_calendar("calContainer1","imgBtn1","factoryDate","");
//init_calendar("calContainer2","imgBtn2","makeYear","");
//}
//YAHOO.util.Event.addListener(window,'load',init);
</script>
