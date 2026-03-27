<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>版本号管理</title>
<%@include file="/common/meta_css.jsp"%>
<%@ include file="/common/i18njs.jsp"%>
</head>
<!--  onkeydown方法禁用BackSpace onload方法光标定位在首个输入框 -->
<body id="all_title" onkeydown="keyDown()" onload="fm.projectVersion.focus()"> 
<div id="wrapper">
<div id="container">

<s:form action="${ctx}/dictionary/queryPrpVersion.do" name="fm" method="post">
<s:hidden name="editType" id="editType" value="${editType}"></s:hidden>
<s:hidden name="deployCom" id="deployCom" value="${deployCom}"></s:hidden>
	<table width="100%" class="fix_table">
    <tr class="top">
      <s:if test="${editType=='view' }">
      <div id="crash_menu">
		<h2 align="center">查看项目版本号</h2>
		</div>
      </s:if>
    </tr>       
    <s:if test="${editType=='view' }">
      <tr>
        <td class="bgc_tt short">项目版本号</td>
        <td class="long"><s:textfield name="prpVersion.id.projectVersion" 
          id="projectVersion" cssClass='input_w w_15' maxlength="30" readonly="true"/></td>
        <td class="bgc_tt short">系统代码</td>
        <td class="long"><s:textfield name="prpVersion.id.productId" 
          id="productId" cssClass='input_w w_15' maxlength="40" readonly="true"/></td>
      </tr>
      <tr>
        <td class="bgc_tt short">变更次数</td>
        <td class="long"><s:textfield name="prpVersion.times" 
          id="times" cssClass='input_w w_15' maxlength="30" readonly="true"/></td>       
        <td class="bgc_tt short">项目名称</td>
        <td class="long"><s:textfield name="prpVersion.projectName" 
          id="projectName" cssClass='input_w w_15' maxlength="30" readonly="true"/></td>  
      </tr>
      <tr>       
        <td class="bgc_tt short">升级前版本号</td>
        <td class="long"><s:textfield name="prpVersion.primaryVersion" 
          id="primaryVersion" cssClass='input_w w_15' maxlength="40" readonly="true"/></td>       
        <td class="bgc_tt short">最后修改时间</td>
        <td class="long"><s:textfield name="prpVersion.updateDate" 
          id="updateDate" cssClass='input_w w_15' maxlength="20" readonly="true"/></td>
      </tr>
      <tr> 
        <td class="bgc_tt short">新用户名称</td>
        <td class="long"><s:textfield name="prpVersion.userName" 
          id="userName" cssClass='input_w w_15' maxlength="20" readonly="true"/></td>
        <td class="bgc_tt short">单位名称</td>
        <td class="long"><s:textfield name="prpVersion.company" 
          id="company" cssClass='input_w w_15' maxlength="20" readonly="true"/></td>
      </tr> 
	  <tr> 
        <td class="bgc_tt short">扩展字段1</td>
        <td class="long"><s:textfield name="prpVersion.flag1" 
          id="flag1" cssClass='input_w w_15' maxlength="20" readonly="true"/></td>
        <td class="bgc_tt short">扩展字段2</td>
        <td class="long"><s:textfield name="prpVersion.flag2" 
          id="flag2" cssClass='input_w w_15' maxlength="20" readonly="true"/></td>
      </tr>
      <tr> 
        <td class="bgc_tt short">扩展字段3</td>
        <td class="long"><s:textfield name="prpVersion.flag3" 
          id="flag3" cssClass='input_w w_15' maxlength="20" readonly="true"/></td>
       	<td class="bgc_tt short">扩展字段4</td>
        <td class="long"><s:textfield name="prpVersion.flag4" 
          id="flag4" cssClass='input_w w_15' maxlength="20" readonly="true"/></td> 
      </tr>
      <tr>
      	<td class="bgc_tt short">扩展字段5</td>
        <td class="long"><s:textfield name="prpVersion.flag5" 
          id="flag5" cssClass='input_w w_15' maxlength="20" readonly="true"/></td>      
      </tr>      
    </s:if>
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
function keyDown(){
            // 禁止使用backspace键
            if(window.event.keyCode == 8){
             event.keyCode = 0; 
       		 event.cancelBubble = true; 
             return false; 
            }
        }
</script>


