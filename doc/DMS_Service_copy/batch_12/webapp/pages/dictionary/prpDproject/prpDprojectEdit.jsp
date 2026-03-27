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
<title>项目代码</title>
<%@include file="/common/meta_css.jsp"%>
<%@ include file="/common/i18njs.jsp"%>
</head>
<!--  onkeydown方法禁用BackSpace onload方法光标定位在首个输入框 -->
<body id="all_title" onload="fm.projectCode.focus()" onkeydown="keyDown()">
<div id="wrapper">
<div id="container">

<s:form action="${ctx}/dictionary/updatePrpDproject.do" name="fm" method="post">
<s:hidden name="editType" id="editType" value="${editType}"></s:hidden>
<s:hidden name="deployCom" id="deployCom" value="${deployCom}"></s:hidden>
	<table width="100%" class="fix_table">
    <tr class="top">
      <s:if test="${editType=='insert' }">
      <div id="crash_menu">
<h2 align="center">增加项目代码</h2>
</div>
      </s:if>
      <s:if test="${editType=='update' }">
      <div id="crash_menu">
<h2 align="center">修改项目代码</h2>
</div>
      </s:if>
      <s:if test="${editType=='view' }">
      <div id="crash_menu">
<h2 align="center">查看项目代码</h2>
</div>
      </s:if>
    </tr>       
    <s:if test="${editType=='view' }">
      <tr>
        <td class="bgc_tt short">项目代码<font color="red">*</font></td>
        <td class="long"><s:textfield name="prpDproject.projectCode" 
          id="projectCode" cssClass='input_w w_15 dc-chk dt-nzhs' maxlength="12" readonly="true"/></td>
        <td class="bgc_tt short">项目简体中文名称<font color="red">*</font></td>
        <td class="long"><s:textfield name="prpDproject.projectCName" 
          id="prpDproject" cssClass='input_w w_15 dc-chk' maxlength="40" readonly="true"/></td>
      </tr>
      <tr>
        <td class="bgc_tt short">项目繁体中文名称</td>    
        <td class="long"><s:textfield name="prpDproject.projectTName" 
          id="projectTName" cssClass='input_w w_15 dc-chk dt-nzhs' maxlength="30" readonly="true"/></td>
        <td class="bgc_tt short">项目英文名称</td>
        <td class="long"><s:textfield name="prpDproject.projectEName" 
          id="projectEName" cssClass='input_w w_15' maxlength="30" readonly="true"/></td>  
      </tr>
      <tr>       
        <td class="bgc_tt short">创建人<font color="red">*</font></td>
        <td class="long"><s:textfield name="prpDproject.creatorCode" 
          id="creatorCode" cssClass='input_w w_15 dc-chk' maxlength="40" readonly="true"/></td>       
        <td class="bgc_tt short">创建时间<font color="red">*</font></td>
        <td>
        <s:textfield name="prpDproject.createTime"
		id="validDate" cssClass="input_w w_15 dc-chk"
		value="${prpDproject.createTime}" readonly="true"/>
		</td>
      </tr>
      <tr> 
        <td class="bgc_tt short">最后修改人</td>
        <td class="long"><s:textfield name="prpDproject.updaterCode" 
          id="updaterCode" cssClass='input_w w_15' maxlength="20" readonly="true"/></td>
        <td class="bgc_tt short">最后修改时间</td>
        <td>
        <s:textfield name="prpDproject.updateTime"
		id="validDate" cssClass="input_w w_15 "
		value="${prpDproject.updateTime}" readonly="true"/>
		</td>
      </tr> 
	  <tr> 
        <td class="bgc_tt short">生效日期<font color="red">*</font></td>
        <td>
        <s:textfield name="prpDproject.validDate"
		id="validDate" cssClass="input_w w_15 dc-chk"
		value="${prpDproject.validDate}" readonly="true"/>
		</td>
		<td class="bgc_tt short">审核标志</td>
        <td class="long"><s:textfield name="prpDproject.auditFlag" 
          id="auditFlag" cssClass='input_w w_15' maxlength="20" readonly="true"/></td>
      </tr>
      <tr> 
       	<td class="bgc_tt short">归属机构</td>
        <td class="long"><s:textfield name="prpDproject.comCode" 
          id="comCode" cssClass='input_w w_15' maxlength="20" readonly="true"/></td> 
        <td class="bgc_tt short">备注</td>
        <td class="long"><s:textfield name="prpDproject.remark" 
          id="remark" cssClass='input_w w_15' maxlength="255" readonly="true"/></td>  
      </tr>
      <tr>	
        <td class="bgc_tt short">预留字段1</td>
        <td class="long"><s:textfield name="prpDproject.tcol1" 
          id="tcol1" cssClass='input_w w_15' maxlength="20" readonly="true"/></td> 
        <td class="bgc_tt short">预留字段2</td>
        <td class="long"><s:textfield name="prpDproject.tcol2" 
          id="tcol2" cssClass='input_w w_15' maxlength="20" readonly="true"/></td>         
      </tr>
      <tr>     
        <td class="bgc_tt short">预留字段3</td>
        <td class="long"><s:textfield name="prpDproject.tcol3" 
          id="tcol3" cssClass='input_w w_15' maxlength="20" readonly="true"/></td>
        <td class="bgc_tt short">有效标志</td>
        <td class="long">
		<ct:select name="prpDproject.validInd" value="${prpDproject.validInd}" id="validInd" sysCode="DMS" codeType="ValidStatus" disabled="true"></ct:select>
		</td>         
      </tr>
      <tr>        
		<td class="bgc_tt short">标志字段</td>
        <td class="long"><s:textfield name="prpDproject.flag" 
          id="flag" cssClass='input_w w_15' maxlength="20" readonly="true"/></td>   
      </tr>
    </s:if>
    <s:elseif test="${editType=='update' }">
        <tr>
        <td class="bgc_tt short">项目代码<font color="red">*</font></td>
        <td class="long"><s:textfield name="prpDproject.projectCode" 
          id="projectCode" cssClass='input_w w_15 dc-chk dt-nzhs' maxlength="12" readonly="true"/></td>
        <td class="bgc_tt short">项目简体中文名称<font color="red">*</font></td>
        <td class="long"><s:textfield name="prpDproject.projectCName" 
          id="prpDproject" cssClass='input_w w_15 dc-chk' maxlength="40"/></td>
      </tr>
      <tr>
        <td class="bgc_tt short">项目繁体中文名称</td>    
        <td class="long"><s:textfield name="prpDproject.projectTName" 
          id="projectTName" cssClass='input_w w_15' maxlength="30"/></td>
        <td class="bgc_tt short">项目英文名称</td>
        <td class="long"><s:textfield name="prpDproject.projectEName" 
          id="projectEName" cssClass='input_w w_15' maxlength="30"/></td>  
      </tr>
      <tr>       
        <td class="bgc_tt short">创建人<font color="red">*</font></td>
        <td class="long"><s:textfield name="prpDproject.creatorCode" 
          id="creatorCode" cssClass='input_w w_15 dc-chk' maxlength="40"/></td>       
        <td class="bgc_tt short">创建时间<font color="red">*</font></td>
        <td>
        <s:textfield name="prpDproject.createTime"
		id="validDate" cssClass="input_w w_15 dc-chk"
		value="${prpDproject.createTime}" onfocus="WdatePicker()"/>
		</td>
      </tr>
      <tr> 
        <td class="bgc_tt short">最后修改人</td>
        <td class="long"><s:textfield name="prpDproject.updaterCode" 
          id="updaterCode" cssClass='input_w w_15' maxlength="20"/></td>
        <td class="bgc_tt short">最后修改时间</td>
        <td>
        <s:textfield name="prpDproject.updateTime"
		id="validDate" cssClass="input_w w_15 "
		value="${prpDproject.updateTime}" onfocus="WdatePicker()"/>
		</td>
      </tr> 
	  <tr> 
        <td class="bgc_tt short">生效日期<font color="red">*</font></td>
        <td>
        <s:textfield name="prpDproject.validDate"
		id="validDate" cssClass="input_w w_15 dc-chk"
		value="${prpDproject.validDate}" onfocus="WdatePicker()"/>
		</td>
		<td class="bgc_tt short">审核标志</td>
        <td class="long"><s:textfield name="prpDproject.auditFlag" 
          id="auditFlag" cssClass='input_w w_15' maxlength="1"/></td>
      </tr>
      <tr> 
       	<td class="bgc_tt short">归属机构<font color="red">*</font></td>
        <td class="long"><input name="prpDproject.comCode" 
          id="comCode" Class='input_y w_15 dc-chk' maxlength="8" VALUE="${prpDproject.comCode}"
				ondblclick="code_CodeQuery(this, 'ComCode', '0', 'Y','')"
				onkeyup="code_CodeQuery(this, 'ComCode', '0', 'Y','')"
				onchange="code_CodeChange(this, 'ComCode', '0', 'Y','')"/>
		</td>
		<td class="bgc_tt short">备注</td>
        <td class="long"><s:textfield name="prpDproject.remark" 
          id="remark" cssClass='input_w w_15' maxlength="255"/></td>
      </tr>
      <tr>  
        <td class="bgc_tt short">预留字段1</td>
        <td class="long"><s:textfield name="prpDproject.tcol1" 
          id="tcol1" cssClass='input_w w_15' maxlength="20"/></td> 
        <td class="bgc_tt short">预留字段2</td>
        <td class="long"><s:textfield name="prpDproject.tcol2" 
          id="tcol2" cssClass='input_w w_15' maxlength="20"/></td>       
      </tr>
      <tr>       
        <td class="bgc_tt short">预留字段3</td>
        <td class="long"><s:textfield name="prpDproject.tcol3" 
          id="tcol3" cssClass='input_w w_15' maxlength="20"/></td>
        <td class="bgc_tt short">有效标志</td>
        <td class="long">
		<ct:select name="prpDproject.validInd" value="${prpDproject.validInd}" id="validInd" sysCode="DMS" codeType="ValidStatus"></ct:select>
		</td>          
      </tr>
      <tr>         
		<td class="bgc_tt short">标志字段</td>
        <td class="long"><s:textfield name="prpDproject.flag" 
          id="flag" cssClass='input_w w_15' maxlength="20"/></td>   
      </tr>
    </s:elseif>
    
    <s:elseif test="${editType=='insert'}">
             <tr>
        <td class="bgc_tt short">项目代码<font color="red">*</font></td>
        <td class="long"><s:textfield name="prpDproject.projectCode" 
          id="projectCode" cssClass='input_w w_15 dc-chk dt-nzhs' maxlength="12"/></td>
        <td class="bgc_tt short">项目简体中文名称<font color="red">*</font></td>
        <td class="long"><s:textfield name="prpDproject.projectCName" 
          id="prpDproject" cssClass='input_w w_15 dc-chk' maxlength="40"/></td>
      </tr>
      <tr>
        <td class="bgc_tt short">项目繁体中文名称</td>    
        <td class="long"><s:textfield name="prpDproject.projectTName" 
          id="projectTName" cssClass='input_w w_15' maxlength="30"/></td>
        <td class="bgc_tt short">项目英文名称</td>
        <td class="long"><s:textfield name="prpDproject.projectEName" 
          id="projectEName" cssClass='input_w w_15' maxlength="30"/></td>  
      </tr>
      <tr>       
        <td class="bgc_tt short">创建人<font color="red">*</font></td>
        <td class="long"><s:textfield name="prpDproject.creatorCode" 
          id="creatorCode" cssClass='input_w w_15 dc-chk' maxlength="50"/></td>       
        <td class="bgc_tt short">创建时间<font color="red">*</font></td>
        <td>
        <s:textfield name="prpDproject.createTime"
		id="validDate" cssClass="input_w w_15 dc-chk"
		value="${prpDproject.createTime}" onfocus="WdatePicker()"/>
		</td>
      </tr>
      <tr> 
        <td class="bgc_tt short">最后修改人</td>
        <td class="long"><s:textfield name="prpDproject.updaterCode" 
          id="updaterCode" cssClass='input_w w_15' maxlength="50"/></td>
        <td class="bgc_tt short">最后修改时间</td>
        <td>
        <s:textfield name="prpDproject.updateTime"
		id="validDate" cssClass="input_w w_15 "
		value="${prpDproject.updateTime}" onfocus="WdatePicker()"/>
		</td>
      </tr> 
	  <tr> 
        <td class="bgc_tt short">生效日期<font color="red">*</font></td>
        <td>
        <s:textfield name="prpDproject.validDate"
		id="validDate" cssClass="input_w w_15 dc-chk"
		value="${prpDproject.validDate}" onfocus="WdatePicker()"/>
		</td>
        <td class="bgc_tt short">审核标志</td>
        <td class="long"><s:textfield name="prpDproject.auditFlag" 
          id="auditFlag" cssClass='input_w w_15' maxlength="1"/></td>
      </tr>
      <tr> 
       	<td class="bgc_tt short">归属机构<font color="red">*</font></td>
		<td class="long">
                <input  name="prpDproject.comCode" id="comCode"
				Class='input_y w_15 dc-chk' maxlength="8"
				ondblclick="code_CodeQuery(this, 'ComCode', '0', 'Y','')"
				onkeyup="code_CodeQuery(this, 'ComCode', '0,1', 'Y','')"
				onchange="code_CodeChange(this, 'ComCode', '0,1', 'Y','')" />
		</td>
		<td class="bgc_tt short">备注</td>
        <td class="long"><s:textfield name="prpDproject.remark" 
          id="remark" cssClass='input_w w_15' maxlength="255"/></td>  
      </tr>
      <tr>   	
        <td class="bgc_tt short">预留字段1</td>
        <td class="long"><s:textfield name="prpDproject.tcol1" 
          id="tcol1" cssClass='input_w w_15' maxlength="20"/></td>
        <td class="bgc_tt short">预留字段2</td>
        <td class="long"><s:textfield name="prpDproject.tcol2" 
          id="tcol2" cssClass='input_w w_15' maxlength="20"/></td>         
      </tr>
      <tr>    
        <td class="bgc_tt short">预留字段3</td>
        <td class="long"><s:textfield name="prpDproject.tcol3" 
          id="tcol3" cssClass='input_w w_15' maxlength="20"/></td>
        <td class="bgc_tt short">有效标志</td>
        <td class="long">
		<ct:select name="prpDproject.validInd" id="validInd" sysCode="DMS" value="1" codeType="ValidStatus"></ct:select>
		</td>          
      </tr>
      <tr>         	
		<td class="bgc_tt short">标志字段</td>
        <td class="long"><s:textfield name="prpDproject.flag" 
          id="flag" cssClass='input_w w_15' maxlength="20"/></td>   
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
	    fm.action="${ctx}/dictionary/updatePrpDproject.do";
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
	var key1 = document.getElementById("projectCode").value;
	editRecord("${ctx}/dictionary/prepareUpdatePrpDproject.do?projectCode="+key1+"&editType=update");
	window.close();
}

function checkForm(){
	return YAHOO.quote.data.datacheck('fm');
}
function hasSameKey(){//多主键校验！
	var key1 = document.getElementById("projectCode").value;
	var url="${ctx}/dictionary/isSameKey.do?tableName=PrpDproject&values=projectCode\='"+key1+"'";
	var handleSuccess = function(o){
		if(o.responseText=="sameKey"){
			alert("该项目代码已存在！");
		}else{
			fm.action="${ctx}/dictionary/insertPrpDproject.do";
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



