<%@ page contentType="text/html; charset=GBK" %>
<%@ include file="/common/taglibs.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<br>
<span style="display:none">
    <table class="common" style="display:none" id="User_Data" cellspacing="1" cellpadding="2">
        <tbody>
            <tr>
                <td class="page" width="21%">
                    <input name="newComCode" type="text" class="codecode" value="" style="width:60px;"
                                 ondblclick="code_CodeSelect(this,'comCode','0,1','Y');"
                           onchange="code_CodeChange(this,'comCode','0,1','Y');">
                    <input name="comName" type="text" class="codename" value="" readonly style="width:43">
                </td>
                <td class="page" width="18%">
                    <input name="userCode" type="text" class="codecode" value="" style="width:55;"
                                 ondblclick="code_CodeSelect(this,'userCodeByComCode','0,1','Y', fm.newComCode[fm.newComCode.length-1].value, 'func()');"
                                 onchange="code_CodeChange(this,'userCodeByComCode','0,1','Y', fm.newComCode[fm.newComCode.length-1].value, 'func()');">
                    <input name="userName" type="text" class="codename" value="" readonly style="width:30">
                </td>
                <td class="page" width="21%">
                    <input name="underComCode" type="text" class="codecode" value="" style="width:60px;"
                                 ondblclick="code_CodeSelect(this,'underComCodeByComCode','0,1','Y','<c:out value="${param.comCode}"/>');"
                           onchange="code_CodeChange(this,'underComCode','0,1','Y');">
                    <input name="comName" type="text" class="codename" value="" readonly style="width:38">
                </td>

                <td class="page" width="25%">
                    <input name="startDate" type="text" class="common" value="" style="width:70px;" maxLength="10">
                    <s:text name="prompt.to"/><%-- 至--%>
                    <input name="endDate" type="text" class="common" value="" style="width:70px;" maxLength="10">
                    <INPUT type="hidden" name="flag" value="0">
                </td>
                <td class="page" width="10%">　</td>
                <td class="page" width="5%">
                    <input type=button name="btnDel2" class="smallbutton"
                                 onclick="deleteRow(this,'User');" value="-" style="cursor: hand">
                </td>
            </tr>
        </tbody>
    </table>
</span>
<table border="0" cellpadding="2" cellspacing="1" class="common" width="90%">
    <input type="hidden" name="utiuwlevelStartDate" value='<c:out value="${requestScope.utiuwlevelStartDate}"/>'>
    <input type="hidden" name="utiuwlevelEndDate"   value='<c:out value="${requestScope.utiuwlevelEndDate}"/>'>
    <tr> 
        <td colspan="6" class="top"><s:text name="check.personnel"/> - <c:out value="${conditionDto.nodeName}"/></td><%-- 人员 --%>
    </tr>
    <tr> 
        <td class="top" width="20%"><s:text name="uwcondition.PersonnelDepartment"/></td><%-- 人员所属部门 --%>
        <td class="top" width="16%"><s:text name="check.personnel"/></td> <%-- 人员 --%>
        <td class="top" width="19%"><s:text name="uwcondition.AuditDepartment"/></td> <%-- 审核部门 --%>
        <td class="top" width="30%"><s:text name="uwcondition.StartingEndingPeriod="/></td> <%--任职起止期  --%>
        <td class="top" width="10%"><s:text name="uwcondition.StaffPermissions"/></td><%-- 人员权限 --%>
        <td class="top" width="5%"></td>
    </tr>
</table>
<table id="User" border="0" cellpadding="2" cellspacing="1" class="common" width="90%">
    <thead></thead>
    <tfoot>
        <tr>
            <td class="page" width="20%">　</td>
            <td class="page" width="10%">　</td>
            <td class="page" width="10%">　</td>
            <td class="page" width="45%">　</td>
            <td class="page" width="10%">　</td>
            <td class="page" width="5%">
                <input type="button" value="+" class="smallbutton" onclick="insertRow('User');" name="btnAdd2" style="cursor: hand">
            </td>
        </tr>
    </tfoot>
    <c:forEach items="${requestScope.utiUwLevelUserList}" var="utiUwLevelDto" varStatus="stat">
        <tr>
            <td width="22%" class="page">
                <input name="newComCode" type="text" class="codecode" style="width:60px;"
                       value='<c:out value="${utiUwLevelDto.userComCode}" />'
                             ondblclick="code_CodeSelect(this,'comCode','0,1','Y');"
                             onchange="code_CodeChange(this,'comCode','0,1','Y');">
                <input name="userComName" type="text" class="codename" readonly style="width:38;"
                       value='<c:out value="${utiUwLevelDto.userComName}" />'>
            </td>
            <td width="19%" class="page">
                <input name="userCode" type="text" class="codecode" style="width:55px;"
                       value='<c:out value="${utiUwLevelDto.userCode}" />'
                             ondblclick="code_CodeSelect(this,'userCodeByComCode','0,1','Y', fm.newComCode[fm.newComCode.length-1].value, 'func()');"
                             onchange="code_CodeChange(this,'userCodeByComCode','0,1','Y', fm.newComCode[fm.newComCode.length-1].value, 'func()');">
                <input name="userName" type="text" class="codename" readonly style="width:33;"
                       value='<c:out value="${utiUwLevelDto.userName}" />'>
            </td>
            <td class="page" width="21%">
                    <input name="underComCode" type="text" class="codecode" value='<c:out value="${utiUwLevelDto.comCode}" />'
                    style="width:60px;" ondblclick="code_CodeSelect(this,'underComCodeByComCode','0,1','Y','<c:out value="${param.comCode}"/>');"
                           onchange="code_CodeChange(this,'comCode','0,1','Y','<c:out value="${param.comCode}"/>');">
                    <input name="comName" type="text" class="codename" readonly style="width:38"
                           value='<c:out value="${utiUwLevelDto.comName}" />'>
            </td>
            <td width="23%" class="page">
                <input name="startDate" type="text" class="common" style="width:70px;" maxLength="10"
                       value='<c:out value="${utiUwLevelDto.startDate}" />'>
                <s:text name="prompt.to"/><%-- 至--%>
                <input name="endDate" type="text" class="common" style="width:70px;" maxLength="10"
                       value='<c:out value="${utiUwLevelDto.endDate}" />'>
                <INPUT type="hidden" name="flag" value='<c:out value="${utiUwLevelDto.flag}" />'>
            </td>
            <td width="10%" class="page">
                <img src="${ctx}/pages/platform/images/btnModifyMenu.gif" style="cursor:hand;" border="0"
                     onclick="func2('<c:out value="${stat.index}" />');">&nbsp;
                <logic:equal name="utiUwLevelDto" property="flag" value="1">
                    <img src="${ctx}/pages/platform/images/btnDeleteMenu.gif" style="cursor:hand;" border="0"
                       onclick="func3('<c:out value="${stat.index}" />');">
                </logic:equal>
            </td>
            <td width="5%" class="page" align="center">
                <input type=button name="btnDel2" class="smallbutton"
                             onclick="deleteRow(this,'User');" value="-" style="cursor: hand">
            </td>
        </tr>
    </c:forEach>
</table>
<script language="javascript">
    function func()
    {
        if(fm.userCode[fm.userCode.length-1].value != "")
        {
            fm.startDate[fm.startDate.length-1].value = fm.utiuwlevelStartDate.value;
            fm.endDate[fm.endDate.length-1].value = fm.utiuwlevelEndDate.value;
        }
    }
    function func2(x)
    {
        var actionType1 = fm.actionType1.value;
        fm.action = "/claim/processUwUserCondition.do?actionType=prepareUpdate&index=" + x +
                      "&actionType1=" + actionType1;
        fm.submit();
    }
    function func3(x)
    {
        var userName = fm.userName[x+1].value;
        var actionType1 = fm.actionType1.value;
        if(confirm("確實要刪除 " + userName + " 的權限嗎？"))
        {
            fm.action = "/claim/processUwUserCondition.do?actionType=delete&index=" + x +
                          "&actionType1=" + actionType1;
            fm.submit();
        }
    }
</script>