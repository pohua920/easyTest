<%@ page contentType="text/html; charset=GBK" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tlds/claim-app.tld" prefix="app" %>
<html>
<head>
    <jsp:include page="/pages/platform/uwcondition/StaticJavascript.jsp" />
    <link href="${ctx}/pages/platform/css/Standard.css" rel="stylesheet" type="text/css">
    <script src="${ctx}/pages/platform/uwcondition/js/uwcondition1.js"></script>
</head>
<body  onload="initPage();">
<form name="fm" action="${ctx}/processUwCondition.do" method="post">
<input type="hidden" name="actionType" value="<c:out value='${param.actionType}'/>">
<table class="common" cellpadding="5" cellspacing="1" align="center">
        <tr>
            <td colspan="6" align="center" class="top"><s:text name="uwcondition.NewAddConditions"/></td><%-- 新增双核条件 --%>
        </tr>
        <tr>
            <td width="15%" class="page"><s:text name="uwcondition.TypeAuditing"/>：</td> <%-- 审核类型 --%>
            <td width="15%" class="page">
                <input name="uwType" type="text" class="codecode" value="" ondblclick="dbclickUwType(this,'dbclick','C,Y');"
                             onkeyup="dbclickUwType(this,'keyup','C,Y');"
                             onchange="dbclickUwType(this,'change','C,Y');"><img src="${ctx}/pages/platform/images/imgMustInput.gif"/>
            </td>
            <td width="20%" class="page">
                <input name="uwTypeName" type="text" class="codename" readonly>
            </td>
            <td width="15%" class="page"><s:text name="uwcondition.AuditDepartment"/>：</td> <%-- 审核部门 --%>
            <td width="15%" class="page">
                <input name="comCode" type="text" class="codecode" value=""
                       ondblclick="dbclickComCode(this, 'dbclick');"
                       onkeyup="dbclickComCode(this, 'keyup');"
                       onchange="dbclickComCode(this, 'change');"><img src="${ctx}/pages/platform/images/imgMustInput.gif"/>
            </td>
            <td width="20%" class="page">
                <input name="comName" type="text" class="codename" readonly>
            </td>
        </tr>
        <tr>
            <td width="15%" class="page"><s:text name="uwcondition.InsuranceCategories"/>：</td> <%-- 险种大类 --%>
            <td width="15%" class="page">
              <input name="riskCategoryCode" type="text" class="codecode" value=""
                             ondblclick="dbclickRiskCategory(this, 'dbclick');"
                         onkeyup="dbclickRiskCategory(this, 'keyup');"
                         onchange="dbclickRiskCategory(this, 'change');"><img src="${ctx}/pages/platform/images/imgMustInput.gif"/>
            </td>
            <td width="20%" class="page">
                <input name="riskCategoryName" type="text" class="codename" readonly>
            </td>
            <td width="15%" class="page">&nbsp;</td>
            <td width="15%" class="page">&nbsp;</td>
            <td width="20%" class="page">&nbsp;</td>
        </tr>
        <tr>
            <td width="15%" class="page"><s:text name="archive.riskClass"/>：</td> <%-- 险类 --%>
            <td width="15%" class="page">
                <input name="classCode" type="text" class="codecode" value=""
               ondblclick="dbclickClassCode(this, 'dbclick');"
               onkeyup="dbclickClassCode(this, 'keyup');"
                              onchange="dbclickClassCode(this, 'change');"><img src="${ctx}/pages/platform/images/imgMustInput.gif"/>
            </td>
            <td width="20%" class="page">
                <input name="className" type="text" class="codename" readonly>
            </td>
            <td width="15%" class="page">&nbsp;</td>
            <td width="15%" class="page">&nbsp;</td>
            <td width="20%" class="page">&nbsp;</td>
        </tr>
        <tr>
            <td width="15%" class="page"><s:text name="regist.prpLregist.riskCodeName"/>：</td><%-- 险种 --%>
            <td width="70%" class="page" colspan="4">
              <input name="riskCode" type="text" class="codecode" value=""
                             ondblclick="dbclickRiskCode(this, 'dbclick');"
                         onkeyup="dbclickRiskCode(this, 'keyup');"
                         onchange="dbclickRiskCode(this, 'change');"><img src="${ctx}/pages/platform/images/imgMustInput.gif"/>
                <input name="riskName" type="text" class="codename" readonly>
            </td>
            <td width="15%" class="page">&nbsp;</td>
        </tr>
        <tr>
            <td width="15%" class="page"><s:text name="uwcondition.Template"/>：</td><%-- 模板 --%>
            <td width="15%" class="page">
                <input name="modelNo" type="text" class="codecode" value=""
                             ondblclick="dbclickModelNo(this, 'dbclick');"
                             onkeyup="dbclickModelNo(this, 'keyup');"
                             onchange="dbclickModelNo(this, 'change');"><img src="${ctx}/pages/platform/images/imgMustInput.gif"/>
            </td>
            <td width="35%" class="page" colspan="2">
                <input name="modelName" type="text" class="codename" readonly>
            </td>
            <td width="15%" class="page">&nbsp;</td>
            <td width="20%" class="page">&nbsp;</td>
        </tr>
        <%-- <tr>
            <td width="15%" class="page">条件描述：</td>
            <td width="70%" class="page" colspan="4">
              <input name="remark" type="text" class="common" value="" readonly>
            </td>
            <td width="15%" class="page">&nbsp;</td>
        </tr>
        --%>
</table>
&nbsp;
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr align="center">
        <td>
            <input type="button" class="button" value="<s:text name='button.next.value'/>" onclick="nextStep();"><%-- 下一步 --%>
            &nbsp;&nbsp;
            <input type="button" class="button" value="<s:text name='button.return.value'/>" onclick="returnBack();"><%-- 返 回 --%>
        </td>
    </tr>
</table>
<app:claimPlatFromCodeInput/>
</form>
<script language="javascript">
     function submitForm(){
        fm.submit();
     }
     function returnBack(){
        fm.action = "/claim/processUwCondition.do?actionType=queryContinue";
        fm.submit();
     }
</script>
</body>
</html>
