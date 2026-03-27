<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>渠道代码</title>
<%@include file="/common/meta_css.jsp"%>
<%@ include file="/common/i18njs.jsp"%>
</head>
<!--  onkeydown方法禁用BackSpace onload方法光标定位在首个输入框 -->
<body id="all_title" onkeydown="keyDown()" onload="fm.agentCode.focus()">
<div id="wrapper">
<div id="container">

<s:form action="${ctx}/dictionary/updatePrpDagent.do" name="fm" method="post">
<s:hidden name="editType" id="editType" value="${editType}"></s:hidden>
<s:hidden name="prpDagent.flag" id="flag" value="${prpDagent.flag}"></s:hidden>
	<table width="100%" class="fix_table">
    <tr class="top">
      <s:if test="${editType=='insert' }">
      <div id="crash_menu">
		<h2 align="center">增加渠道代码</h2>
	  </div>
      </s:if>
      <s:if test="${editType=='update' }">
      <div id="crash_menu">
		<h2 align="center">修改渠道代码</h2>
		</div>
      </s:if>
      <s:if test="${editType=='view' }">
     <div id="crash_menu">
		<h2 align="center">查看渠道代码</h2>
	</div>
      </s:if>
    </tr>       
    <s:if test="${editType=='view'}">
      <tr>
        <td class="bgc_tt short">渠道代码</td>
        <td class="long"><s:textfield name="prpDagent.agentCode" 
          id="agentCode" cssClass='input_w w_30' maxlength="12" readonly="true"/></td>
        <td class="bgc_tt short">渠道名称</td>
        <td class="long"><s:textfield name="prpDagent.agentName" 
          id="agentName" cssClass='input_w w_30' maxlength="120" readonly="true"/></td>
      </tr>
      <tr>
        <td class="bgc_tt short">渠道地址</td>
        <td class="long"><s:textfield name="prpDagent.addressName" 
          id="addressName" cssClass='input_w w_30' maxlength="60" readonly="true"/></td>
        <td class="bgc_tt short">渠道类型</td>
        <td class="long"><s:textfield name="prpDagent.agentType" 
          id="agentType" cssClass='input_w w_30' maxlength="3" readonly="true"/></td>
      </tr>
      <tr>
        <td class="bgc_tt short">邮政编码</td>
        <td class="long"><s:textfield name="prpDagent.postCode" 
          id="postCode" cssClass='input_w w_30' maxlength="6" readonly="true"/></td>       
        <td class="bgc_tt short">许可证号</td>
         <td class="long"><s:textfield name="prpDagent.permitNo" 
          id="permitNo" cssClass='input_w w_30' maxlength="22" readonly="true"/></td>
      </tr>
      <tr>   
        <td class="bgc_tt short">联系人</td>
        <td class="long"><s:textfield name="prpDagent.linkerName" 
          id="linkerName" cssClass='input_w w_30' maxlength="20" readonly="true"/></td>
        <td class="bgc_tt short">电话</td>
        <td class="long"><s:textfield name="prpDagent.phoneNumber" 
          id="phoneNumber" cssClass='input_w w_30' maxlength="30" readonly="true"/></td> 
      </tr>
      <tr>      
        <td class="bgc_tt short">传真</td>
        <td class="long"><s:textfield name="prpDagent.faxNumber" 
          id="faxNumber" cssClass='input_w w_30' maxlength="20" readonly="true"/></td>       
        <td class="bgc_tt short">合同期 </td>
		<td class="long" nowrap="nowrap">
		
		<input type="text" name="prpDagent.bargainDate" value="${prpDagent.bargainDate}"
			 class='input_w w_30 Wdate' id="bargainDate"  onFocus="" readonly="readonly">
<%--			 
	 		<input type="text" name="prpDagent.bargainDate" value="${prpDagent.bargainDate}"
	          id="bargainDate" Class='input_w w_30 dt-date' maxlength="20" readonly="true">

        <s:textfield name="prpDagent.bargainDate" id="bargainDate" cssClass='input_w w_30' maxlength="20" readonly="true">
          	<s:param name="value"><s:date name="prpDagent.bargainDate" format="yyyy-MM-dd"/></s:param>
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
        <td class="bgc_tt short">归属机构代码</td>
        <td class="long"><s:textfield name="prpDagent.comCode" 
          id="comCode" cssClass='input_w w_30' maxlength="8" readonly="true"/></td>
        <td class="bgc_tt short">上级代理人代码</td>
        <td class="long"><s:textfield name="prpDagent.upperAgentCode" 
          id="upperAgentCode" cssClass='input_w w_30' maxlength="12" readonly="true"/></td> 
      </tr>
      <tr>
        <td class="bgc_tt short">新的代理人代码 </td>
        <td class="long"><s:textfield name="prpDagent.newAgentCode" 
          id="newAgentCode" cssClass='input_w w_30' maxlength="12" readonly="true"/></td>
        <td class="bgc_tt short">是否允许下级机构使用</td>
        <td class="long"><s:select name="prpDagent.agentNature" 
          list="#@java.util.HashMap@{'1':'是','0':'否'}" disabled="true" /></td>  
        </tr>
<%--
      <tr>
        <td class="bgc_tt short">生效日期 </td>
        <td class="long"><input type="text" name="prpDagent.validDate" value="${prpDagent.validDate}"
			 class='input_w w_30 Wdate' id="validDate"  onFocus="WdatePicker()" readonly="readonly"></td>
        <td class="bgc_tt short">失效日期</td>
        <td class="long"><input type="text" name="prpDagent.invalidDate" value="${prpDagent.invalidDate}"
			 class='input_w w_30 Wdate' id="invalidDate"  onFocus="WdatePicker()" readonly="readonly"></td>
        </tr>
--%>
      <tr>
        <td class="bgc_tt short">有效标志</td>
        <td class="long">
<!--
        <s:select name="prpDagent.ValidStatus" id="validStatus"
          list="#@java.util.HashMap@{'1':'有效','0':'无效'}" disabled="true" />
-->
		<ct:select name="prpDagent.validStatus" value="${prpDagent.validStatus}" id="validStatus" sysCode="IMS" codeType="ValidStatus" disabled="true"></ct:select>
		</td>  
        <td class="bgc_tt short">专项代码</td>
         <td class="long"><s:textfield name="prpDagent.articleCode" 
          id="articleCode" cssClass='input_w w_30' maxlength="8" readonly="true"/></td>
      </tr>
    </s:if>

    <s:elseif test="${editType=='update' }">
      <tr>
        <td class="bgc_tt short">渠道代码<font color="red">*</font></td>
        <td class="long"><s:textfield name="prpDagent.agentCode" 
          id="agentCode" cssClass='input_w w_30 dc-chk dt-nzhs' maxlength="12" readonly="true"/></td>
        <td class="bgc_tt short">渠道名称</td>
        <td class="long"><s:textfield name="prpDagent.agentName" 
          id="agentName" cssClass='input_w w_30' maxlength="120"/></td>   
      </tr>
      <tr>
        <td class="bgc_tt short">渠道地址</td>
        <td class="long"><s:textfield name="prpDagent.addressName" 
          id="addressName" cssClass='input_w w_30' maxlength="60"/></td>       
        <td class="bgc_tt short">渠道类型</td>
        <td class="long"><s:textfield name="prpDagent.agentType" 
          id="agentType" cssClass='input_w w_30' maxlength="3"/></td>
      </tr>
      <tr>
        <td class="bgc_tt short">邮政编码</td>
        <td class="long"><s:textfield name="prpDagent.postCode" 
          id="postCode" cssClass='input_w w_30' maxlength="6"/></td>       
        <td class="bgc_tt short">许可证号</td>
         <td class="long"><s:textfield name="prpDagent.permitNo" 
          id="permitNo" cssClass='input_w w_30' maxlength="22"/></td> 
      </tr>
      <tr>  
        <td class="bgc_tt short">联系人</td>
        <td class="long"><s:textfield name="prpDagent.linkerName" 
          id="linkerName" cssClass='input_w w_30' maxlength="20"/></td>
        <td class="bgc_tt short">电话</td>
        <td class="long"><s:textfield name="prpDagent.phoneNumber" 
          id="phoneNumber" cssClass='input_w w_30' maxlength="30"/></td>
      </tr>
      <tr>       
        <td class="bgc_tt short">传真</td>
        <td class="long"><s:textfield name="prpDagent.faxNumber" 
          id="faxNumber" cssClass='input_w w_30' maxlength="20"/></td>       
        <td class="bgc_tt short">合同期 </td>
        <td class="long" nowrap="nowrap">
		<input type="text" name="prpDagent.bargainDate" value="${prpDagent.bargainDate}"
			 class='input_w w_30 Wdate' id="bargainDate"  onFocus="WdatePicker()">
<%--
        <input type="text" name="prpDagent.bargainDate" value="${prpDagent.bargainDate}"
          id="bargainDate" Class='input_w w_30 dt-date' maxlength="20" readonly="true">

        <s:textfield name="prpDagent.bargainDate" 
          id="bargainDate" cssClass='input_w w_30 dt-date' maxlength="20" readonly="true">
          	<s:param name="value"><s:date name="prpDagent.bargainDate" format="yyyy-MM-dd"/></s:param>
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
        <td class="bgc_tt short">归属机构代码</td>
		<td class="long">
                <input  name="prpDagent.comCode" id="comCode"
				Class='input_y w_30' maxlength="8" VALUE="${prpDagent.comCode}"
				ondblclick="code_CodeQuery(this, 'ComCode', '0', 'Y','')"
				onkeyup="code_CodeQuery(this, 'ComCode', '0', 'Y','')"
				onchange="code_CodeChange(this, 'ComCode', '0', 'Y','')" />
			</td>          
        <td class="bgc_tt short">上级代理人代码</td>
        <td class="long"><s:textfield name="prpDagent.upperAgentCode" 
          id="upperAgentCode" cssClass='input_w w_30' maxlength="12"/></td> 
      </tr>
      <tr>      
        <td class="bgc_tt short">新的代理人代码 <font color="red">*</font></td>
        <td class="long"><s:textfield name="prpDagent.newAgentCode" 
          id="newAgentCode" cssClass='input_w w_30 dc-chk' maxlength="12"/></td>
        <td class="bgc_tt short">是否允许下级机构使用</td>
        <td class="long"><s:select name="prpDagent.agentNature" 
          list="#@java.util.HashMap@{'1':'是','0':'否'}"/></td> 
      </tr>
<%--
      <tr>
        <td class="bgc_tt short">生效日期 </td>
        <td class="long"><input type="text" name="prpDagent.validDate" value="${prpDagent.validDate}"
			 class='input_w w_30 Wdate' id="validDate"  onFocus="WdatePicker()"></td>
        <td class="bgc_tt short">失效日期</td>
        <td class="long"><input type="text" name="prpDagent.invalidDate" value="${prpDagent.invalidDate}"
			 class='input_w w_30 Wdate' id="invalidDate"  onFocus="WdatePicker()"></td>
        </tr>
--%>
      <tr>   
        <td class="bgc_tt short">有效标志<font color="red">*</font></td>
        <td class="long">
<!--
        <s:select name="prpDagent.ValidStatus" id="validStatus"
          list="#@java.util.HashMap@{'1':'有效','0':'无效'}"/>
-->
		<ct:select name="prpDagent.validStatus" id="validStatus" sysCode="IMS" codeType="ValidStatus" value="${prpDagent.validStatus}"></ct:select>
		<s:hidden name="prpDagent.validStatus" id="validStatus" value="${prpDagent.validStatus}"></s:hidden>
	</td>  
        <td class="bgc_tt short">专项代码</td>
         <td class="long"><s:textfield name="prpDagent.articleCode" 
          id="articleCode" cssClass='input_w w_30' maxlength="8"/></td>
      </tr>
    </s:elseif>
    
    <s:elseif test="${editType=='insert'}">
       <tr>
        <td class="bgc_tt short">渠道代码<font color="red">*</font></td>
        <td class="long"><s:textfield name="prpDagent.agentCode" 
          id="agentCode" cssClass='input_w w_30 dc-chk dt-nzhs' maxlength="12"/></td>
        <td class="bgc_tt short">渠道名称</td>
        <td class="long"><s:textfield name="prpDagent.agentName" 
          id="agentName" cssClass='input_w w_30' maxlength="120"/></td>
      </tr>
      <tr>    
        <td class="bgc_tt short">渠道地址</td>
        <td class="long"><s:textfield name="prpDagent.addressName" 
          id="addressName" cssClass='input_w w_30' maxlength="60"/></td>       
        <td class="bgc_tt short">渠道类型</td>
        <td class="long"><s:textfield name="prpDagent.agentType" 
          id="agentType" cssClass='input_w w_30' maxlength="3"/></td>
      </tr>
      <tr>
        <td class="bgc_tt short">邮政编码</td>
        <td class="long"><s:textfield name="prpDagent.postCode" 
          id="postCode" cssClass='input_w w_30' maxlength="6"/></td>       
        <td class="bgc_tt short">许可证号</td>
         <td class="long"><s:textfield name="prpDagent.permitNo" 
          id="permitNo" cssClass='input_w w_30' maxlength="22"/></td> 
      </tr>
      <tr>  
        <td class="bgc_tt short">联系人</td>
        <td class="long"><s:textfield name="prpDagent.linkerName" 
          id="linkerName" cssClass='input_w w_30' maxlength="20"/></td>
        <td class="bgc_tt short">电话</td>
        <td class="long"><s:textfield name="prpDagent.phoneNumber" 
          id="phoneNumber" cssClass='input_w w_30' maxlength="30"/></td>
      </tr>
      <tr>       
        <td class="bgc_tt short">传真</td>
        <td class="long"><s:textfield name="prpDagent.faxNumber" 
          id="faxNumber" cssClass='input_w w_30' maxlength="20"/></td>       
        <td class="bgc_tt short">合同期 </td>
        <td class="long" nowrap="nowrap">
		<input type="text" name="prpDagent.bargainDate" value="${prpDagent.bargainDate}"
			 class='input_w w_30 Wdate' id="bargainDate"  onFocus="WdatePicker()">
<%-- 
		<input type="text" name="prpDagent.bargainDate" value="${prpDagent.bargainDate}"
	          id="bargainDate" Class='input_w w_30 dt-date' maxlength="20" readonly="true">
   
        <s:textfield name="prpDagent.bargainDate" 
          id="bargainDate" cssClass='input_w w_30 dt-date' maxlength="20" readonly="true">
          	<s:param name="value"><s:date name="prpDagent.bargainDate" format="yyyy-MM-dd"/></s:param>
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
        <td class="bgc_tt short">归属机构代码</td>
		<td class="long">
                <input  name="prpDagent.comCode" id="comCode"
				Class='input_y w_30' maxlength="8"
				ondblclick="code_CodeQuery(this, 'ComCode', '0', 'Y','')"
				onkeyup="code_CodeQuery(this, 'ComCode', '0,1', 'Y','')"
				onchange="code_CodeChange(this, 'ComCode', '0,1', 'Y','')" />
			</td>       
        <td class="bgc_tt short">上级代理人代码</td>
        <td class="long"><s:textfield name="prpDagent.upperAgentCode" 
          id="upperAgentCode" cssClass='input_w w_30' maxlength="12"/></td> 
      </tr>
      <tr>      
        <td class="bgc_tt short">新的代理人代码 <font color="red">*</font></td>
        <td class="long"><s:textfield name="prpDagent.newAgentCode" 
          id="newAgentCode" cssClass='input_w w_30 dc-chk' maxlength="12"/></td>
        <td class="bgc_tt short">是否允许下级机构使用</td>
        <td class="long"><s:select name="prpDagent.agentNature" 
          list="#@java.util.HashMap@{'1':'是','0':'否'}"/></td> 
      </tr>
<%--
      <tr>
        <td class="bgc_tt short">生效日期 </td>
        <td class="long"><input type="text" name="prpDagent.validDate" value="${prpDagent.validDate}"
			 class='input_w w_30 Wdate' id="validDate"  onFocus="WdatePicker()"></td>
        <td class="bgc_tt short">失效日期</td>
        <td class="long"><input type="text" name="prpDagent.invalidDate" value="${prpDagent.invalidDate}"
			 class='input_w w_30 Wdate' id="invalidDate"  onFocus="WdatePicker()"></td>
        </tr>
--%>
      <tr>   
        <td class="bgc_tt short">有效标志<font color="red">*</font></td>
        <td class="long">
<!--
        <s:select name="prpDagent.ValidStatus" id="validStatus"
          list="#@java.util.HashMap@{'1':'有效','0':'无效'}"/>
-->
		<ct:select name="prpDagent.validStatus" id="validStatus" sysCode="IMS" codeType="ValidStatus" value="1"></ct:select>
		<s:hidden name="prpDagent.validStatus" id="validStatus" value="1"></s:hidden>
        <td class="bgc_tt short">专项代码</td>
         <td class="long"><s:textfield name="prpDagent.articleCode" 
          id="articleCode" cssClass='input_w w_30' maxlength="8"/></td>
      </tr>
    </s:elseif>
  </table>
  <%--
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr align="center" class="top">
	  <c:if test="${editType=='view' }">
        <td><input type="button" value="修改" class="button_ty"
        onclick="prepareUpdate()"></td>
      </c:if>
      <c:if test="${editType=='insert' }">
        <td><input type="button" value="保存" class="button_ty"
        onclick="return addMethod()"></td>
      </c:if>
      <c:if test="${editType=='update' }">
        <td><input type="button" value="保存" class="button_ty"
          onclick="updateMethod()"></td>
      </c:if>
    </tr>
  </table>
  --%>
</s:form>
		</div>
		</div>

</body>
</html>

<%@ include file="/common/meta_js.jsp"%>
<script language="javascript" src="${ctx}/common/dwr/engine.js"></script>
<script language="javascript" src="${ctx}/common/dwr/util.js"></script>
<script language="javascript" src="${ctx}/common/js/CodeSelect.js"></script>
<script language="javascript" src="${ctx}/common/js/newCalendar.js"></script>
<script language="javascript"
	src="${ctx}/widgets/yui/autocomplete/autocomplete-min.js"></script>

<script type="text/javascript">
function updateMethod(){
    if(checkForm()){
    	if(checkLen()){
		    fm.action="${ctx}/dictionary/updatePrpDagent.do";
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
	var key1 = document.getElementById("agentCode").value;
	editRecord("${ctx}/dictionary/prepareUpdatePrpDagent.do?agentCode="+key1+"&editType=update");
	window.close();
}
function checkForm(){
	return YAHOO.quote.data.datacheck('fm');
}

function hasSameKey(){//多主键校验！
	var key1 = document.getElementById("agentCode").value;
	var url="${ctx}/dictionary/isSameKey.do?tableName=PrpDagent&values=agentCode\='"+key1+"'";
	var handleSuccess = function(o){
		if(o.responseText=="sameKey"){
			alert("该渠道代码已存在！");
		}else{
			fm.action="${ctx}/dictionary/insertPrpDagent.do";
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
//alert("1");

//alert("2");
//}
//YAHOO.util.Event.addListener(window,'load',init);
/*****时间控件******/
//init_calendar("calContainer1","imgBtn1","bargainDate","");
//init_calendar("calContainer2","imgBtn2","endDate","toSecond");
</script>
