 <%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="cn.com.sinosoft.ims.sync.SyncConstants"%>
<%
	String deployCom = (String)session.getAttribute("deployCom");
%>
<html>
<head>
<title>专管专营代码</title>
<%@include file="/common/meta_css.jsp"%>
<%@ include file="/common/i18njs.jsp"%>
<%-- moidfy  update by tongziliang 2011-10-09 reason:修改页面的按钮样式和优化页面样式 --%>
<link rel="stylesheet" href="${ctx}/style/popup/login.css" type="text/css"></link>
</head>
<!--  onkeydown方法禁用BackSpace onload方法光标定位在首个输入框 -->
<body id="all_title" onload="fm.resourceCode.focus()" onkeydown="keyDown()">
<div id="wrapper">
<div id="container">

<s:form action="${ctx}/dictionary/updatePrpDresource.do" name="fm" method="post">
<s:hidden name="editType" id="editType" value="${editType}"></s:hidden>
<s:hidden name="prpDresource.flag" id="flag" value="${prpDresource.flag}"></s:hidden>
<s:hidden name="deployCom" id="deployCom" value="${deployCom}"></s:hidden>
	<table width="100%" class="fix_table">
    <tr class="top">
      <s:if test="${editType=='insert' }">
      <div id="crash_menu">
<h2 align="center">增加专管专营代码</h2>
</div>
      </s:if>
      <s:if test="${editType=='update' }">
      <div id="crash_menu">
<h2 align="center">修改专管专营代码</h2>
</div>
      </s:if>
      <s:if test="${editType=='view' }">
      <div id="crash_menu">
<h2 align="center">查看专管专营代码</h2>
</div>
      </s:if>
    </tr>       
    <s:if test="${editType=='view' }">
      <tr>
        <td class="bgc_tt short">专管专营代码</td>
        <td class="long"><s:textfield name="prpDresource.resourceCode" 
          id="resourceCode" cssClass='input_w w_15 dc-chk' maxlength="30" readonly="true"/></td>
        <td class="bgc_tt short">专管专营名称</td>
        <td class="long"><s:textfield name="prpDresource.resourceName" 
          id="resourceName" cssClass='input_w w_15' maxlength="40" readonly="true"/></td>
      </tr>
      <tr>
        <td class="bgc_tt short">归属机构代码</td>    
        <td class="long"><s:textfield name="prpDresource.comCode" 
          id="comCode" cssClass='input_w w_15 dc-chk dt-nzhs' maxlength="30" readonly="true"/></td>
        <td class="bgc_tt short">合作单位名称</td>
        <td class="long"><s:textfield name="prpDresource.companyName" 
          id="companyName" cssClass='input_w w_15' maxlength="30" readonly="true"/></td>  
      </tr>
      <tr>       
        <td class="bgc_tt short">项目代码</td>
        <td class="long"><s:textfield name="prpDresource.projectCode" 
          id="projectCode" cssClass='input_w w_15' maxlength="40" readonly="true"/></td>       
        <td class="bgc_tt short">渠道代码</td>
        <td class="long"><s:textfield name="prpDresource.agentCode" 
          id="agentCode" cssClass='input_w w_15' maxlength="20" readonly="true"/></td>
      </tr>
      <tr> 
        <td class="bgc_tt short">合作单位地址</td>
        <td class="long"><s:textfield name="prpDresource.companyAddr" 
          id="companyAddr" cssClass='input_w w_15' maxlength="20" readonly="true"/></td>
        <td class="bgc_tt short">邮政编码</td>
        <td class="long"><s:textfield name="prpDresource.postCode" 
          id="postCode" cssClass='input_w w_15 dt-post' maxlength="20" readonly="true"/></td>
      </tr> 
	  <tr> 
        <td class="bgc_tt short">联系人</td>
        <td class="long"><s:textfield name="prpDresource.linkerName" 
          id="linkerName" cssClass='input_w w_15' maxlength="20" readonly="true"/></td>
        <td class="bgc_tt short">电话</td>
        <td class="long"><s:textfield name="prpDresource.phoneNumber" 
          id="phoneNumber" cssClass='input_w w_15' maxlength="20" readonly="true"/></td>
      </tr>
      <tr> 
        <td class="bgc_tt short">传真</td>
        <td class="long"><s:textfield name="prpDresource.faxNumber" 
          id="faxNumber" cssClass='input_w w_15' maxlength="20" readonly="true"/></td>
       	<td class="bgc_tt short">备注</td>
        <td class="long"><s:textfield name="prpDresource.remark" 
          id="remark" cssClass='input_w w_15' maxlength="20" readonly="true"/></td> 
      </tr>
      <tr>
      	<td class="bgc_tt short">专营团队负责人姓名</td>
        <td class="long"><s:textfield name="prpDresource.managerName" 
          id="managerName" cssClass='input_w w_15' maxlength="20" readonly="true"/></td>  
        <td class="bgc_tt short">专营团队负责人办公电话</td>
        <td class="long"><s:textfield name="prpDresource.managerPhone" 
          id="managerPhone" cssClass='input_w w_15' maxlength="20" readonly="true"/></td>       
      </tr>
      <tr>
        <td class="bgc_tt short">专营团队负责人手机</td>
        <td class="long"><s:textfield name="prpDresource.managerMobile" 
          id="managerMobile" cssClass='input_w w_15 dt-mobile' maxlength="20" readonly="true"/></td> 
        <td class="bgc_tt short">专营团队负责人邮件</td>
        <td class="long"><s:textfield name="prpDresource.managerEmail" 
          id="managerEmail" cssClass='input_w w_15 dt-email' maxlength="20" readonly="true"/></td>        
      </tr>
      <tr>         
		<td class="bgc_tt short">有效标志</td>
        <td class="long">
		<ct:select name="prpDresource.validStatus" value="${prpDresource.validStatus}" id="validStatus" sysCode="DMS" codeType="ValidStatus" disabled="true"></ct:select>
		</td>    
      </tr>
    </s:if>
    <s:elseif test="${editType=='update' }">
            <tr>
        <td class="bgc_tt short">专管专营代码</td>
        <td class="long"><s:textfield name="prpDresource.resourceCode" 
          id="resourceCode" cssClass='input_w w_15 dc-chk' maxlength="13" readonly="true"/></td>
        <td class="bgc_tt short">专管专营名称</td>
        <td class="long"><s:textfield name="prpDresource.resourceName" 
          id="resourceName" cssClass='input_w w_15' maxlength="40"/></td>
      </tr>
      <tr>
        <td class="bgc_tt short">归属机构代码</td>
        <td class="long"><input name="prpDresource.comCode" 
          id="comCode" Class='input_y w_15' maxlength="8" VALUE="${prpDresource.comCode}"
				ondblclick="code_CodeQuery(this, 'ComCode', '0', 'Y','')"
				onkeyup="code_CodeQuery(this, 'ComCode', '0', 'Y','')"
				onchange="code_CodeChange(this, 'ComCode', '0', 'Y','')"/>
		</td>
        <td class="bgc_tt short">合作单位名称</td>
        <td class="long"><s:textfield name="prpDresource.companyName" 
          id="companyName" cssClass='input_w w_15' maxlength="30"/></td>  
      </tr>
      <tr>       
        <td class="bgc_tt short">项目代码</td>
        <td class="long"><s:textfield name="prpDresource.projectCode" 
          id="projectCode" cssClass='input_w w_15' maxlength="40"/></td>       
        <td class="bgc_tt short">渠道代码</td>
        <td class="long"><s:textfield name="prpDresource.agentCode" 
          id="agentCode" cssClass='input_w w_15 dt-nzhs' maxlength="12"/></td>
      </tr>
      <tr> 
        <td class="bgc_tt short">合作单位地址</td>
        <td class="long"><s:textfield name="prpDresource.companyAddr" 
          id="companyAddr" cssClass='input_w w_15' maxlength="20"/></td>
        <td class="bgc_tt short">邮政编码</td>
        <td class="long"><s:textfield name="prpDresource.postCode" 
          id="postCode" cssClass='input_w w_15 dt-post' maxlength="20"/></td>
      </tr> 
	  <tr> 
        <td class="bgc_tt short">联系人</td>
        <td class="long"><s:textfield name="prpDresource.linkerName" 
          id="linkerName" cssClass='input_w w_15' maxlength="20"/></td>
        <td class="bgc_tt short">电话</td>
        <td class="long"><s:textfield name="prpDresource.phoneNumber" 
          id="phoneNumber" cssClass='input_w w_15' maxlength="20"/></td>
      </tr>
      <tr> 
        <td class="bgc_tt short">传真</td>
        <td class="long"><s:textfield name="prpDresource.faxNumber" 
          id="faxNumber" cssClass='input_w w_15' maxlength="20"/></td>
       	<td class="bgc_tt short">备注</td>
        <td class="long"><s:textfield name="prpDresource.remark" 
          id="remark" cssClass='input_w w_15' maxlength="20"/></td> 
      </tr>
      <tr>
      	<td class="bgc_tt short">专营团队负责人姓名</td>
        <td class="long"><s:textfield name="prpDresource.managerName" 
          id="managerName" cssClass='input_w w_15' maxlength="20"/></td>  
        <td class="bgc_tt short">专营团队负责人办公电话</td>
        <td class="long"><s:textfield name="prpDresource.managerPhone" 
          id="managerPhone" cssClass='input_w w_15' maxlength="20"/></td>       
      </tr>
      <tr>
        <td class="bgc_tt short">专营团队负责人手机</td>
        <td class="long"><s:textfield name="prpDresource.managerMobile" 
          id="managerMobile" cssClass='input_w w_15 dt-mobile' maxlength="20"/></td> 
        <td class="bgc_tt short">专营团队负责人邮件</td>
        <td class="long"><s:textfield name="prpDresource.managerEmail" 
          id="managerEmail" cssClass='input_w w_15 dt-email' maxlength="20"/></td>        
      </tr>
      <tr>         
		 <td class="bgc_tt short">有效标志</td>
        <td class="long">
		<ct:select name="prpDresource.validStatus" value="${prpDresource.validStatus}" id="validStatus" sysCode="DMS" codeType="ValidStatus"></ct:select>
		</td>    
      </tr>
    </s:elseif>
    
    <s:elseif test="${editType=='insert'}">
             <tr>
        <td class="bgc_tt short">专管专营代码<font color="red">*</font></td>
        <td class="long"><s:textfield name="prpDresource.resourceCode" 
          id="resourceCode" cssClass='input_w w_15 dc-chk dt-zzs' maxlength="13"/></td>
        <td class="bgc_tt short">专管专营名称</td>
        <td class="long"><s:textfield name="prpDresource.resourceName" 
          id="resourceName" cssClass='input_w w_15' maxlength="40"/></td>
      </tr>
      <tr>
        <td class="bgc_tt short">归属机构代码<font color="red">*</font></td>   
		<td class="long">
                <input  name="prpDresource.comCode" id="comCode"
				Class='input_y w_15' maxlength="8"
				ondblclick="code_CodeQuery(this, 'ComCode', '0', 'Y','')"
				onkeyup="code_CodeQuery(this, 'ComCode', '0', 'Y','')"
				onchange="code_CodeChange(this, 'ComCode', '0', 'Y','')" />
			</td>
        <td class="bgc_tt short">合作单位名称</td>
         <td class="long"><s:textfield name="prpDresource.companyName" 
          id="companyName" cssClass='input_w w_15' maxlength="30"/>
          </td>  
      </tr>
      <tr>       
        <td class="bgc_tt short">项目代码</td>
        <td class="long"><s:textfield name="prpDresource.projectCode" 
          id="projectCode" cssClass='input_w w_15' maxlength="40"/></td>       
        <td class="bgc_tt short">渠道代码</td>
        <td class="long"><s:textfield name="prpDresource.agentCode" 
          id="agentCode" cssClass='input_w w_15 dt-nzhs' maxlength="12"/></td>
      </tr>
      <tr> 
        <td class="bgc_tt short">合作单位地址</td>
        <td class="long"><s:textfield name="prpDresource.companyAddr" 
          id="companyAddr" cssClass='input_w w_15' maxlength="20"/></td>
        <td class="bgc_tt short">邮政编码</td>
        <td class="long"><s:textfield name="prpDresource.postCode" 
          id="postCode" cssClass='input_w w_15 dt-post' maxlength="20"/></td>
      </tr> 
	  <tr> 
        <td class="bgc_tt short">联系人</td>
        <td class="long"><s:textfield name="prpDresource.linkerName" 
          id="linkerName" cssClass='input_w w_15' maxlength="20"/></td>
        <td class="bgc_tt short">电话</td>
        <td class="long"><s:textfield name="prpDresource.phoneNumber" 
          id="phoneNumber" cssClass='input_w w_15' maxlength="20"/></td>
      </tr>
      <tr> 
        <td class="bgc_tt short">传真</td>
        <td class="long"><s:textfield name="prpDresource.faxNumber" 
          id="faxNumber" cssClass='input_w w_15' maxlength="20"/></td>
       	<td class="bgc_tt short">备注</td>
        <td class="long"><s:textfield name="prpDresource.remark" 
          id="remark" cssClass='input_w w_15' maxlength="20"/></td> 
      </tr>
      <tr>
      	<td class="bgc_tt short">专营团队负责人姓名</td>
        <td class="long"><s:textfield name="prpDresource.managerName" 
          id="managerName" cssClass='input_w w_15' maxlength="20"/></td>  
        <td class="bgc_tt short">专营团队负责人办公电话</td>
        <td class="long"><s:textfield name="prpDresource.managerPhone" 
          id="managerPhone" cssClass='input_w w_15' maxlength="20"/></td>       
      </tr>
      <tr>
        <td class="bgc_tt short">专营团队负责人手机</td>
        <td class="long"><s:textfield name="prpDresource.managerMobile" 
          id="managerMobile" cssClass='input_w w_15 dt-mobile' maxlength="20"/></td> 
        <td class="bgc_tt short">专营团队负责人邮件</td>
        <td class="long"><s:textfield name="prpDresource.managerEmail" 
          id="managerEmail" cssClass='input_w w_15 dt-email' maxlength="20"/></td>        
      </tr>
      <tr>         
		 <td class="bgc_tt short">有效标志</td>
        <td class="long">
		<ct:select name="prpDresource.validStatus" id="validStatus" sysCode="DMS" value="1" codeType="ValidStatus"></ct:select>
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
function checkResourceCode(){
	var resCode = document.getElementById("resourceCode").value;
	if(trim(resCode).length < 13 || trim(resCode).length > 13){
		alert("专管专营代码必须为13位 ！");
			return false;
		}
		else
			return true;		
}
function updateMethod(){
    if(checkForm()){
      if(checkResourceCode()){   
	    fm.action="${ctx}/dictionary/updatePrpDresource.do";
	    fm.submit();
	    }  	
    }
}

function addMethod(){
	if(checkForm()){
	 if(checkResourceCode()){
	 if(checkLen()){
			hasSameKey();
		}
	 }	
	}
}
function prepareUpdate(){//客户需求，查看页面点击修改要进入修改页面。
	var key1 = document.getElementById("resourceCode").value;
	editRecord("${ctx}/dictionary/prepareUpdatePrpDresource.do?resourceCode="+key1+"&editType=update");
	window.close();
}

function checkForm(){
	return YAHOO.quote.data.datacheck('fm');
}
function hasSameKey(){//多主键校验！
	var key1 = document.getElementById("resourceCode").value;
	var url="${ctx}/dictionary/isSameKey.do?tableName=PrpDresource&values=resourceCode\='"+key1+"'";
	var handleSuccess = function(o){
		if(o.responseText=="sameKey"){
			alert("该专管专营代码已存在！");
		}else{
			fm.action="${ctx}/dictionary/insertPrpDresource.do";
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



