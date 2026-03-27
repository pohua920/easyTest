<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tlds/claim-app.tld" prefix="app"%>
<html>
    <head>
        <jsp:include page="/pages/platform/uwcondition/StaticJavascript.jsp" />
        <link href="${ctx}/pages/platform/css/Standard.css" rel="stylesheet" type="text/css">
        <script src="${ctx}/pages/platform/uwlevel/js/uwlevel.js"></script>
    </head>
    <body onload="initPage();">
        <form name="fm" action="${ctx}/processUwLevel.do" method="post">
            <input type="hidden" name="actionType" value="<c:out value="${param.actionType}"/>">
            <table class="common" cellpadding="5" cellspacing="1" align="center">
                <tr>
                    <td colspan="6" align="center" class="top">
                        <s:text name="uwlevel.AddConditions" /><%-- 新增人员条件 --%>
                    </td>
                </tr>
                <tr>
                    <td width="15%" class="page">
                        <s:text name="uwcondition.TypeAuditing" />：<%-- 审核类型 --%>
                    </td>
                    <td width="15%" class="page">
                        <input name="uwType" type="text" class="codecode" value=""
                            ondblclick="dbclickUwType(this,'dbclick','C,Y');"
                            onkeyup="dbclickUwType(this,'keyup','C,Y');"
                            onchange="dbclickUwType(this,'change','C,Y');">
                        <img src="${ctx}/pages/platform/images/imgMustInput.gif" />
                    </td>
                    <td width="20%" class="page">
                        <input name="uwTypeName" type="text" class="codename" readonly>
                    </td>
                    <td width="15%" class="page">
                        <s:text name="uwcondition.AuditDepartment" />：<%-- 审核部门 --%>
                    </td>
                    <td width="15%" class="page">
                        <input name="comCode" type="text" class="codecode" value=""
                            ondblclick="dbclickComCode(this, 'dbclick');"
                            onkeyup="dbclickComCode(this, 'keyup');"
                            onchange="dbclickComCode(this, 'change');">
                        <img src="${ctx}/pages/platform/images/imgMustInput.gif" />
                    </td>
                    <td width="20%" class="page">
                        <input name="comName" type="text" class="codename" readonly>
                    </td>
                </tr>
                <tr>
                    <td width="15%" class="page">
                        <s:text name="uwcondition.Template" />：<%-- 模板 --%>
                    </td>
                    <td width="15%" class="page">
                        <input name="modelNo" type="text" class="codecode" value=""
                            ondblclick="code_CodeSelect(this,'modelNo','0,1','Y');"
                            onkeyup="code_CodeSelect(this,'modelNo','0,1','Y');"
                            onchange="code_CodeChange(this,'modelNo','0,1','Y');">
                        <img src="${ctx}/pages/platform/images/imgMustInput.gif" />
                    </td>
                    <td width="35%" class="page" colspan="2">
                        <input name="modelName" type="text" class="codename" readonly>
                    </td>
                    <td width="15%" class="page"></td>
                    <td width="20%" class="page"></td>
                </tr>
            </table>
            &nbsp;
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr align="center">
                    <td>
                        <input type="button" class="button" value="<s:text name='button.next.value'/>" onclick="nextStep();"><%--  下一步--%>
                        &nbsp;&nbsp;
                        <input type="button" class="button" value="<s:text name='button.return.value'/>" onclick="window.history.back();"><%-- 返 回 --%>
                    </td>
                </tr>
            </table>
            <app:claimPlatFromCodeInput />
        </form>
        <script language="javascript">
         function submitForm(){
            fm.submit();
            }
        </script>
    </body>
</html>
