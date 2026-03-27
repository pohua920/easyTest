<%@ page contentType="text/html; charset=GBK" %>
<%@ include file="/common/taglibs.jsp"%>
<br>
<span style="display:none">
    <table class="common" style="display:none" id="User_Data" cellspacing="1" cellpadding="2">
        <tbody>
            <tr>
                <td class="page" width="14%">
                    <input name="newComCode" type="text" class="codecode" value="" 
                                 ondblclick="code_CodeSelect(this,'comCode','0,1','Y');"
                           onchange="code_CodeChange(this,'comCode','0,1','Y');">
                    <input name="userComName" type="text" class="codename" value="" readonly >
                </td>
                <td class="page" width="13%">
                    <input name="userCode" type="text" class="codecode" value="" 
                                 ondblclick="code_CodeSelect(this,'userCodeByComCode','0,1','Y', fm.newComCode[fm.newComCode.length-1].value, 'func()');"
                                 onchange="code_CodeChange(this,'userCodeByComCode','0,1','Y', fm.newComCode[fm.newComCode.length-1].value, 'func()');">
                    <input name="userName" type="text" class="codename" value="" readonly >
                </td>
                <td class="page" width="13%">
                    <input name="underComCode" type="text" class="codecode" value="" 
                                 ondblclick="code_CodeSelect(this,'underComCodeByComCode','0,1','Y','<c:out value="${UtiUwLevelDto.comCode}"/>');"
                           onchange="code_CodeChange(this,'underComCode','0,1','Y');">
                    <input name="comName" type="text" class="codename" value="" readonly >
                </td>
                <td class="page" width="14%">
                    <input name="riskCode" type="text" class="codecode" value="" 
                                ondblclick="code_CodeQuery(this,'riskCodeByModelNo','0,1','Y','<c:out value="${UtiUwLevelDto.modelNo}"/>');"
                                 onkeyup="code_CodeQuery(this,'riskCodeByModelNo');"
                                 onchange="code_CodeChange(this,'riskCodeByModelNo');">
                    <input name="riskName" type="text" class="codename" value="" readonly >
                </td>
                <td class="page" width="31%">
                    <input name="startDate" type="text" class="common" value="<c:out value="${utiuwlevelStartDate}"/>" style="width:70px;" maxLength="10">
                <s:text name="prompt.to" /><%--至--%>
                    <input name="endDate" type="text" class="common" value="<c:out value="${utiuwlevelEndDate}"/>" style="width:70px;" maxLength="10">
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
<table border="0" cellpadding="2" cellspacing="1" class="newcommon" width="90%">
    <input type="hidden" name="utiuwlevelStartDate" value='<c:out value="${utiuwlevelStartDate}"/>'>
    <input type="hidden" name="utiuwlevelEndDate"   value='<c:out value="${utiuwlevelEndDate}"/>'>
    <tr> 
        <td colspan="7" class="top"></td><s:text name="check.personnel" /> - <c:out value="${uwLevelDto.nodeName}"/></td><%--人员--%>
    </tr>
    <tr> 
        <td class="top" width="14%"><s:text name="uwlevel.personnelDepartment" /></td><%--人员所属部门--%>
        <td class="top" width="13%"><s:text name="check.personnel" /></td><%--人员--%>
        <td class="top" width="13%"><s:text name="uwusercondition.auditDepartment" /></td><%--审核部门--%>
        <td class="top" width="14%"><s:text name="regist.prpLregist.riskCodeName" /></td><%--险种--%>
        <td class="top" width="31%"><s:text name="uwlevel.startStopPhase" /></td><%--任职起止期--%>
        <td class="top" width="10%"><s:text name="uwusercondition.personnelAccess" /></td><%--人员权限--%>
        <td class="top" width="5%"></td>
    </tr>
</table>
<table id="User" border="0" cellpadding="2" cellspacing="1" class="newcommon" width="90%">
    <thead></thead>
    <tfoot>
        <tr>
            <td class="page" width="14%">　</td>
            <td class="page" width="13%">　</td>
            <td class="page" width="13%">　</td>
            <td class="page" width="14%">　</td>
            <td class="page" width="31%">　</td>
            <td class="page" width="10%">　</td>
            <td class="page" width="5%">
                <input type="button" value="+" class="smallbutton" onclick="insertRow('User');"  name="btnAdd2" style="cursor: hand">
            </td>
        </tr>
    </tfoot>
    <c:forEach items="${utiUwLevelUserList}" var="utiUwLevelDto" varStatus="stat">
        <tr>
            <td width="14%" class="page">
                <input name="newComCode" type="text" class="codecode" 
                       value='<c:out value="${utiUwLevelDto.userComCode}"/>'
                             ondblclick="code_CodeSelect(this,'comCode','0,1','Y');"
                             onchange="code_CodeChange(this,'comCode','0,1','Y');">
                <input name="userComName" type="text" class="codename" readonly 
                       value='<c:out value="${utiUwLevelDto.userComName}"/>'>
            </td>
            <td width="13%" class="page">
                <input name="userCode" type="text" class="codecode" 
                       value='<c:out value="${utiUwLevelDto.userCode}"/>'
                             ondblclick="code_CodeSelect(this,'userCodeByComCode','0,1','Y', fm.newComCode[fm.newComCode.length-1].value, 'func()');"
                             onchange="code_CodeChange(this,'userCodeByComCode','0,1','Y', fm.newComCode[fm.newComCode.length-1].value, 'func()');">
                <input name="userName" type="text" class="codename" readonly 
                       value='<c:out value="${utiUwLevelDto.userName}"/>'>
            </td>
            <td class="page" width="13%">
                    <input name="underComCode" type="text" class="codecode" value='<c:out value="${utiUwLevelDto.comCode}"/>'
                    ondblclick="code_CodeSelect(this,'underComCodeByComCode','0,1','Y','<c:out value="${utiUwLevelDto.comCode}"/>');"
                           onchange="code_CodeChange(this,'comCode','0,1','Y','<c:out value="${utiUwLevelDto.comCode}"/>');">
                    <input name="comName" type="text" class="codename" readonly 
                           value='<c:out value="${utiUwLevelDto.comName}"/>'>
            </td>
            <td class="page" width="14%">
                    <input name="riskCode" type="text" class="codecode" value="<c:out value="${utiUwLevelDto.riskCode}"/>" 
                                ondblclick="code_CodeQuery(this,'riskCodeByModelNo','0,1','Y','<c:out value="${utiUwLevelDto.modelNo}"/>');"
                                 onkeyup="code_CodeQuery(this,'riskCodeByModelNo','0,1','Y','<c:out value="${utiUwLevelDto.modelNo}"/>);"
                                 onchange="code_CodeChange(this,'riskCodeByModelNo','0,1','Y','<c:out value="${utiUwLevelDto.modelNo}"/>);" style="width:78px;">
                    <input name="riskName" type="text" class="codename" readonly 
                           value='<c:out value="${utiUwLevelDto.riskName}"/>' style="width:78px;">
            </td>
            <td width="31%" class="page">
                <input name="startDate" type="text" class="common" style="width:70px;" maxLength="10"
                       value='<c:out value="${utiUwLevelDto.startDate}"/>'>
                至
                <input name="endDate" type="text" class="common" style="width:70px;" maxLength="10"
                       value='<c:out value="${utiUwLevelDto.endDate}"/>'>
                <INPUT type="hidden" name="flag" value='<c:out value="${utiUwLevelDto.flag}"/>'>
            </td>
            <td width="10%" class="page">
                <img src="${ctx}/pages/platform/images/btnModifyMenu.gif" style="cursor:hand;" border="0"
                     onclick="func2(<c:out value="${stat.index}"/>);">&nbsp;
                <c:if test="${utiUwLevelDto.flag=='1'}">
                    <img src="${ctx}/pages/platform/images/btnDeleteMenu.gif" style="cursor:hand;" border="0" onclick="func3(<c:out value="${stat.index}"/>);">
                </c:if>
            </td>
            <td width="5%" class="page" align="center">
                <input type=button name="btnDel2" class="smallbutton" onclick="deleteRow(this,'User');" value="-" style="cursor: hand">
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
    <%--function func2(x)
    {
        var actionType1 = fm.actionType1.value;
        fm.action = "/claim/processUwUserCondition.do?actionType=prepareUpdate&index=" + x +
                      "&actionType1=" + actionType1;
        fm.submit();
    }
    --%>
    function func2(x)
    {
        var actionType1 = fm.actionType1.value;
        fm.action = "/claim/processUwUserCondition.do?actionType=beforeUpdate&index=" + x;
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