<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tlds/claim-app.tld" prefix="app" %>
<html>
    <head>
        <jsp:include page="/pages/platform/uwcondition/StaticJavascript.jsp" />
        <link rel="stylesheet" type="text/css" href="${ctx}/pages/platform/css/Standard.css">
        <script src="${ctx}/pages/platform/uwcondition/js/uwcondition1.js"></script>
    </head>
    <body onload="initPage();">
        <form name="fm" action="${ctx}/processUwCondition.do" method="post" >
            <input type="hidden" name="actionType" value="<c:out value="${param.actionType}"/>">
            <table class="common" cellpadding="5" cellspacing="1" align="center">
                <tr>
                    <td colspan="6" align="center" class="top"><s:text name="uwcondition.ModifiedConditions"/><%-- 修改双核条件 --%></td>
                </tr>
                <tr>
                    <td width="15%" class="page"><s:text name="uwcondition.TypeAuditing"/>：<%-- 审核类型 --%></td>
                    <td width="15%" class="page">
                        <input name="uwType" type="text" class="codename" value='<c:out value="${conditionDto.uwType}"/>'>
                    </td>
                    <td width="20%" class="page">
                        <input name="uwTypeName" type="text" class="codename" value='<c:out value="${conditionDto.uwTypeName}"/>'>
                    </td>
                    <td width="15%" class="page"><s:text name="uwcondition.AuditDepartment"/>：<%-- 审核部门 --%></td>
                    <td width="15%" class="page">
                        <input name="comCode" type="text" class="codename" value='<c:out value="${conditionDto.comCode}"/>'>
                    </td>
                    <td width="20%" class="page">
                        <input name="comName" type="text" class="codename" value='<c:out value="${conditionDto.comName}"/>'>
                    </td>
                </tr>
                <tr>
                    <td width="15%" class="page"><s:text name="archive.riskClass"/>：<%-- 险类 --%></td>
                    <td width="15%" class="page">
                        <input name="classCode" type="text" class="codename" value='<c:out value="${conditionDto.classCode}"/>'>
                    </td>
                    <td width="20%" class="page">
                        <input name="className" type="text" class="codename" value='<c:out value="${conditionDto.className}"/>'>
                    </td>
                    <td width="15%" class="page">&nbsp;</td>
                    <td width="15%" class="page">&nbsp;</td>
                    <td width="20%" class="page">&nbsp;</td>
                </tr>
                <tr>
                    <td width="15%" class="page"><s:text name="regist.prpLregist.riskCodeName"/>：<%-- 险种 --%></td>
                    <td width="70%" class="page" colspan="4">
                        <input name="riskCode" type="text" class="codecode" 
                            ondblclick="riskCodeByClassCode(this, 'dbclick');" 
                            onkeyup="riskCodeByClassCode(this, 'keyup');" 
                            onchange="riskCodeByClassCode(this, 'change');" 
                            value='<c:out value="${conditionDto.riskCode}"/>'>
                        <img src="${ctx}/pages/platform/images/imgMustInput.gif" />
                        <input name="riskName" type="hidden" class="codename" readonly>
                        <INPUT name="oldRiskCode" type="hidden" value='<c:out value="${conditionDto.riskCode}"/>'>
                    </td>
                    <td width="15%" class="page">&nbsp;</td>
                </tr>
                <tr>
                    <td width="15%" class="page"><s:text name="uwcondition.Template"/>：<%-- 模板 --%></td>
                    <td width="15%" class="page">
                        <input name="modelNo" type="text" class="codename" value='<c:out value="${conditionDto.modelNo}"/>'>
                    </td>
                    <td width="35%" class="page" colspan="2">
                        <input name="modelName" type="text" class="codename" value='<c:out value="${conditionDto.modelName}"/>'>
                    </td>
                    <td width="15%" class="page">&nbsp;</td>
                    <td width="20%" class="page">&nbsp;</td>
                </tr>
            </table>
            &nbsp;
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr align="center">
                    <td>
                        <input type="button" class="button" value="<s:text name='button.next.value'/>" onclick="nextStep();"><%-- 下一步 --%>
                        &nbsp;&nbsp;
                        <input type="button" class="longbutton" value="<s:text name='button.SaveAndReturn.value'/>" onclick="save();"><%-- 保存並返回 --%>
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
             function save(){
                if(trim(fm.riskCode.value) == ""){
                    alert("险种不能为空！");
                    return false;
                }
                if(confirm("確實要儲存嗎？")){
                    fm.action = "/claim/processUwCondition.do?actionType=update";
                    fm.submit();
                }
             }
             function nextStep(){
                if(trim(fm.riskCode.value) == ""){
                    alert("险种不能为空！");
                    return false;
                }
                var oldcode1 = trim(fm.oldRiskCode.value);
                var newcode1 = trim(fm.riskCode.value);
                if(oldcode1 != newcode1){
                    if(confirm("確實要儲存修改並繼續處理嗎？") == false){
                        return false;
                    }
                }
                fm.action = "/claim/processUwCondition.do?actionType=prepareUpdate2";
                fm.submit();
             }
        </script>
    </body>