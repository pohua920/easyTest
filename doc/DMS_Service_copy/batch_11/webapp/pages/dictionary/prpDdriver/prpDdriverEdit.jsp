<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%-- moidfy  update by tongziliang 2011-10-09 reason:修改页面的按钮样式和优化页面样式 --%>
<link rel="stylesheet" href="${ctx}/style/popup/login.css" type="text/css"></link>
<html>
<head>
<title>机动车险司机代码</title>
<%@include file="/common/meta_css.jsp"%>
<%@ include file="/common/i18njs.jsp"%>
</head>
<!--  onkeydown方法禁用BackSpace onload方法光标定位在首个输入框 -->
<body id="all_title" onkeydown="keyDown()" onload="fm.drivingLicenseNo.focus()">
<div id="wrapper">
<div id="container">
<s:form action="${ctx}/dictionary/updatePrpDdriver.do" name="fm" method="post">
<s:hidden name="editType" id="editType" value="${editType}"></s:hidden>
<s:hidden name="prpDdriver.flag" id="flag" value="${prpDdriver.flag}"></s:hidden>
	<table width="100%" class="fix_table">
    <tr class="top">
      <s:if test="${editType=='insert' }">
        <div id="crash_menu">
			<h2 align="center">增加司机代码</h2>
		</div>
      </s:if>
      <s:if test="${editType=='update' }">
        <div id="crash_menu">
				<h2 align="center">修改司机代码</h2>
		</div>
      </s:if>
      <s:if test="${editType=='view' }">
        <div id="crash_menu">
				<h2 align="center">查看司机代码</h2>
		</div>
      </s:if>
    </tr>
    <s:if test="${editType=='view' }">
      <tr>
        <td class="bgc_tt short">驾驶证号码</td>
        <td class="long"><s:textfield name="prpDdriver.drivingLicenseNo" 
          id="drivingLicenseNo" cssClass='input_w w_15' maxlength="20" readonly="true"/></td>
        <td class="bgc_tt short">姓名</td>
        <td class="long"><s:textfield name="prpDdriver.driverName" 
          id="driverName" cssClass='input_w w_15' maxlength="20" readonly="true"/></td> 
	    </tr>
		<tr>
        <td class="bgc_tt short">性别</td>
		<td class="long">
<!--
		<s:select name="prpDdriver.DriverSex" 
         	 list="#@java.util.HashMap@{'1':'男','0':'女'}" disabled="true" />
-->
		<ct:select sysCode="IMS" name="prpDdriver.driverSex" value="${prpDdriver.driverSex}" disabled="true" codeType="SexCode"></ct:select>
		</td>
        <td class="bgc_tt short">出生日期</td>
       <td class="long" nowrap="nowrap">
		<input type="text" name="prpDdriver.birthday" value="${prpDdriver.birthday}"
			 class='input_w w_15 Wdate' id="birthday"  onFocus="" readonly="readonly">
<%--	
		<s:textfield name="prpDdriver.birthday" 
          id="birthday" cssClass='input_w w_15 dt-date' maxlength="20" readonly="true">
          	<s:param name="value"><s:date name="prpDdriver.birthday" format="yyyy-MM-dd"/></s:param>
          </s:textfield>
			<img
				src="${ctx}/pages/image/time/date_icon.gif" alt="点击显示时间面板"
				id="imgBtn1" width="14" height="14" style="visibility: hidden"/> 
			<span class="calender-panel">
				<div id="calContainer1" style="position: absolute;"></div>
			</span>
--%>
			</td>
	    </tr>
		<tr>
        <td class="bgc_tt short">身份证号</td>
        <td class="long"><s:textfield name="prpDdriver.identifyNumber" 
          id="identifyNumber" cssClass='input_w w_20 dt-id' maxlength="20" readonly="true"/></td>
        <td class="bgc_tt short">单位或住址</td>
		<td class="long"><s:textfield name="prpDdriver.driverAddress" 
          	id="driverAddress" cssClass='input_w w_15' maxlength="120" readonly="true"/></td>
	    </tr>
		<tr>   
        <td class="bgc_tt short">初次领证日期</td>
         <td class="long" nowrap="nowrap">

		<input type="text" name="prpDdriver.receiveLicenseDate" value="${prpDdriver.receiveLicenseDate}"
			 class='input_w w_15 Wdate' id="receiveLicenseDate"  onFocus="" readonly="readonly">
<%--         
         <s:textfield name="prpDdriver.receiveLicenseDate" 
          id="receiveLicenseDate" cssClass='input_w w_15 dt-date' maxlength="20" readonly="true">
          	<s:param name="value"><s:date name="prpDdriver.receiveLicenseDate" format="yyyy-MM-dd"/></s:param>
          </s:textfield>
			<img
				src="${ctx}/pages/image/time/date_icon.gif" alt="点击显示时间面板"
				id="imgBtn2" width="14" height="14" style="visibility: hidden"/> 
			<span class="calender-panel">
				<div id="calContainer2" style="position: absolute;"></div>
			</span>
--%>			
			</td>   
        <td class="bgc_tt short">领证机关</td>
        <td class="long"><s:textfield name="prpDdriver.awardLicenseOrgan" 
          id="awardLicenseOrgan" cssClass='input_w w_15' maxlength="20" readonly="true"/></td>  
	    </tr>
		<tr>  
 		<td class="bgc_tt short">准驾车型</td>
 		<td class="long">
<!--
 		<s:textfield name="prpDdriver.drivingCarType" 
          id="drivingCarType" cssClass='input_w w_15' maxlength="20" readonly="true"/>
-->
		<ct:select sysCode="IMS" value="${prpDdriver.drivingCarType}" name="prpDdriver.drivingCarType" id="drivingCarType" cssClass='input_w w_15' codeType="CarType" disabled="true"></ct:select>
		</td>
 		<td class="bgc_tt short"></td>
 		<td class="long"></td>        
      </tr>
    </s:if>
    <s:elseif test="${editType=='update' }">
            <tr>
        <td class="bgc_tt short">驾驶证号码 <font color="red">*</font></td>
        <td class="long"><s:textfield name="prpDdriver.drivingLicenseNo" 
          id="drivingLicenseNo" cssClass='input_w w_15 dc-chk dt-nzhs' maxlength="20" readonly="true"/></td>
        <td class="bgc_tt short">姓名 <font color="red">*</font></td>
        <td class="long"><s:textfield name="prpDdriver.driverName" 
          id="driverName" cssClass='input_w w_15 dc-chk' maxlength="20"/></td> 
	    </tr>
		<tr>
        <td class="bgc_tt short">性别</td>
		<td class="long">
<!--
		<s:select name="prpDdriver.DriverSex" 
         	 list="#@java.util.HashMap@{'1':'男','0':'女'}"/>
-->
		<ct:select sysCode="IMS" name="prpDdriver.driverSex" value="${prpDdriver.driverSex}" codeType="SexCode"></ct:select>
		</td>
        <td class="bgc_tt short">出生日期</td>
        <td class="long" nowrap="nowrap">
		<input type="text" name="prpDdriver.birthday" value="${prpDdriver.birthday}"
			 class='input_w w_15 Wdate' id="birthday"  onFocus="WdatePicker()">
<%--
       <input type="text" name="prpDdriver.birthday" value="${prpDdriver.birthday}"
          id="birthday" Class='input_w w_15 dt-date' maxlength="20" readonly="readonly">

		 <s:textfield name="prpDdriver.birthday" 
          id="birthday" cssClass='input_w w_15 dt-date' maxlength="20" readonly="true">
          	<s:param name="value"><s:date name="prpDdriver.birthday" format="yyyy-MM-dd"/></s:param>
          </s:textfield>

			<img
				src="${ctx}/pages/image/time/date_icon.gif" alt="点击显示时间面板"
				id="imgBtn1" width="14" height="14" /> 
			<span class="calender-panel">
				<div id="calContainer1" style="position: absolute;"></div>
			</span>
--%>			
			</td>
	    </tr>
		<tr>
        <td class="bgc_tt short">身份证号</td>
        <td class="long"><s:textfield name="prpDdriver.identifyNumber" 
          id="identifyNumber" cssClass='input_w w_20 dt-id' maxlength="20"/></td>
        <td class="bgc_tt short">单位或住址</td>
		<td class="long"><s:textfield name="prpDdriver.driverAddress" 
          	id="driverAddress" cssClass='input_w w_15' maxlength="120"/></td>  
	    </tr>
		<tr> 
        <td class="bgc_tt short">初次领证日期</td>
        <td class="long" nowrap="nowrap">
		<input type="text" name="prpDdriver.receiveLicenseDate" value="${prpDdriver.receiveLicenseDate}"
			 class='input_w w_15 Wdate' id="receiveLicenseDate"  onFocus="WdatePicker()">
<%-- 
		<input type="text" name="prpDdriver.receiveLicenseDate" value="${prpDdriver.receiveLicenseDate}" 
          id="receiveLicenseDate" Class='input_w w_15 dt-date' maxlength="20" readonly="readonly">
       
        <s:textfield name="prpDdriver.receiveLicenseDate" 
          id="receiveLicenseDate" cssClass='input_w w_15 dt-date' maxlength="20" readonly="true">
          	<s:param name="value"><s:date name="prpDdriver.receiveLicenseDate" format="yyyy-MM-dd"/></s:param>
          </s:textfield>

			<img
				src="${ctx}/pages/image/time/date_icon.gif" alt="点击显示时间面板"
				id="imgBtn2" width="14" height="14" /> 
			<span class="calender-panel">
				<div id="calContainer2" style="position: absolute;"></div>
			</span> 
--%>			
			 </td>     
        <td class="bgc_tt short">领证机关</td>
        <td class="long"><s:textfield name="prpDdriver.awardLicenseOrgan" 
          id="awardLicenseOrgan" cssClass='input_w w_15' maxlength="20"/></td> 
	    </tr>
		<tr>   
 		<td class="bgc_tt short">准驾车型</td>
 		<td class="long">
<!-- 		
 		<s:textfield name="prpDdriver.drivingCarType" 
          id="drivingCarType" cssClass='input_w w_15' maxlength="10"/>
-->
		<ct:select sysCode="IMS" value="${prpDdriver.drivingCarType}" name="prpDdriver.drivingCarType" id="drivingCarType" cssClass='input_w w_15' codeType="CarType"></ct:select>
		</td> 
 		<td class="bgc_tt short"></td>
 		<td class="long"></td>       
      </tr>
    </s:elseif>
    
    <s:elseif test="${editType=='insert'}">
       <tr>
        <td class="bgc_tt short">驾驶证号码<font color="red">*</font></td>
        <td class="long"><s:textfield name="prpDdriver.drivingLicenseNo" 
          id="drivingLicenseNo" cssClass='input_w w_15 dc-chk dt-nzhs' maxlength="20"/></td>
        <td class="bgc_tt short">姓名<font color="red">*</font></td>
        <td class="long"><s:textfield name="prpDdriver.driverName" 
          id="driverName" cssClass='input_w w_15 dc-chk' maxlength="20"/></td>
	    </tr>
		<tr>       
        <td class="bgc_tt short">性别</td>
		<td class="long">
<!--
		<s:select name="prpDdriver.DriverSex" 
         	 list="#@java.util.HashMap@{'1':'男','0':'女'}"/>
-->
		<ct:select sysCode="IMS" name="prpDdriver.driverSex" value="${prpDdriver.driverSex}" codeType="SexCode"></ct:select>
		</td>
        <td class="bgc_tt short">出生日期</td>
        <td class="long" nowrap="nowrap">
		<input type="text" name="prpDdriver.birthday" value="${prpDdriver.birthday}"
			 class='input_w w_15 Wdate' id="birthday"  onFocus="WdatePicker()">
<%--
        <input type="text" name="prpDdriver.birthday" 
          id="birthday" Class='input_w w_15 dt-date' maxlength="20" readonly="readonly">

        <s:textfield name="prpDdriver.birthday" 
          id="birthday" cssClass='input_w w_15 dt-date' maxlength="20" readonly="true">
          	<s:param name="value"><s:date name="prpDdriver.birthday" format="yyyy-MM-dd"/></s:param>
          </s:textfield>

			<img
				src="${ctx}/pages/image/time/date_icon.gif" alt="点击显示时间面板"
				id="imgBtn1" width="14" height="14" /> 
			<span class="calender-panel">
				<div id="calContainer1" style="position: absolute;"></div>
			</span>
--%>
			</td>
	    </tr>
		<tr>
        <td class="bgc_tt short">身份证号</td>
        <td class="long"><s:textfield name="prpDdriver.identifyNumber" 
          id="identifyNumber" cssClass='input_w w_20 dt-id' maxlength="20"/></td>
        <td class="bgc_tt short">单位或住址</td>
		<td class="long"><s:textfield name="prpDdriver.driverAddress" 
          	id="driverAddress" cssClass='input_w w_15' maxlength="120"/></td> 
	    </tr>
		<tr>
        <td class="bgc_tt short">初次领证日期</td>
        <td class="long" nowrap="nowrap">
		<input type="text" name="prpDdriver.receiveLicenseDate" value="${prpDdriver.receiveLicenseDate}"
			 class='input_w w_15 Wdate' id="receiveLicenseDate"  onFocus="WdatePicker()">
<%--
        <input type="text" name="prpDdriver.receiveLicenseDate" 
          id="receiveLicenseDate" Class='input_w w_15 dt-date' maxlength="20" readonly="readonly">
        <s:textfield name="prpDdriver.receiveLicenseDate" 
          id="receiveLicenseDate" cssClass='input_w w_15 dt-date' maxlength="20" readonly="true">
          	<s:param name="value"><s:date name="prpDdriver.receiveLicenseDate" format="yyyy-MM-dd"/></s:param>
          </s:textfield>  
			<img
				src="${ctx}/pages/image/time/date_icon.gif" alt="点击显示时间面板"
				id="imgBtn2" width="14" height="14" /> 
			<span class="calender-panel">
				<div id="calContainer2" style="position: absolute;"></div>
			</span>
--%>
			 </td>   
        <td class="bgc_tt short">领证机关</td>
        <td class="long"><s:textfield name="prpDdriver.awardLicenseOrgan" 
          id="awardLicenseOrgan" cssClass='input_w w_15' maxlength="20"/></td> 
	    </tr>
		<tr>   
 		<td class="bgc_tt short">准驾车型</td>
 		<td class="long">
<!-- 		
 		<s:textfield name="prpDdriver.drivingCarType" 
          id="drivingCarType" cssClass='input_w w_15' maxlength="10" />
-->
		<ct:select sysCode="IMS" value="${prpDdriver.drivingCarType}" name="prpDdriver.drivingCarType" id="drivingCarType" cssClass='input_w w_15' codeType="CarType"></ct:select>
	</td>
 		<td class="bgc_tt short"></td>
 		<td class="long"></td>        
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
		 fm.action="${ctx}/dictionary/updatePrpDdriver.do";
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
	var key1 = document.getElementById("drivingLicenseNo").value;
	editRecord("${ctx}/dictionary/prepareUpdatePrpDdriver.do?drivingLicenseNo="+key1+"&editType=update");
	window.close();
}

function checkForm(){
	return YAHOO.quote.data.datacheck('fm');
}
function hasSameKey(){//多主键校验！
	var key1 = document.getElementById("drivingLicenseNo").value;
	var url="${ctx}/dictionary/isSameKey.do?tableName=PrpDdriver&values=drivingLicenseNo\='"+key1+"'";
	var handleSuccess = function(o){
		if(o.responseText=="sameKey"){
			alert("该司机代码已存在！");
		}else{
			fm.action="${ctx}/dictionary/insertPrpDdriver.do";
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
//	init_calendar("calContainer1","imgBtn1","birthday","");
//	init_calendar("calContainer2","imgBtn2","receiveLicenseDate","");
//}
//YAHOO.util.Event.addListener(window,'load',init);

</script>



