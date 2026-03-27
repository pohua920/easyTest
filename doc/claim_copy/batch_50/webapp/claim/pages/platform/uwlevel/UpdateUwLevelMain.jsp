<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
    <head>
        <title></title>
        <link href="${ctx}/pages/platform/css/Standard.css" rel="stylesheet" type="text/css">
        <link rel="stylesheet" type="text/css" href="${ctx}/pages/platform/behaviors/2k3OlBar.css">
    </head>
    <form name="fm" action="" method="POST">
        <input type="hidden" name="actionType1" value="<c:out value="${param.actionType}"/>">
        <body style="margin: 0px; scroll: no; overflow: hidden;">
            <table border="0" cellpadding="2" cellspacing="1" class="newcommon"
                width="90%">
                <tr>
                    <td colspan="7" class="top">
                        <s:text name="check.personnel" /> - <c:out value="${utiUwLevelDto.nodeName}" />
                    </td>
                    <%-- 人员 --%>
                </tr>
                <tr>
                    <td class="top" width="8%"></td>
                    <td class="top" width="15%"><s:text name="check.personnel" /></td>
                    <%-- 人员 --%>
                    <td class="top" width="15%"><s:text name="uwcondition.AuditDepartment"/></td>
                    <%-- 审核部门 --%>
                    <td class="top" width="13%"><s:text name="regist.prpLregist.riskCodeName"/></td>
                    <%-- 险种 --%>
                    <td class="top" width="23%"><s:text name="uwcondition.StartingEndingPeriod"/></td><%--任职起止期  --%>
                    <td class="top" width="8%"><s:text name="uwcondition.StaffPermissions"/></td>
                    <%-- 人员权限 --%>
                    <td class="top" width="18%"><s:text name="form.save"/></td>
                    <%-- 保存 --%>
                </tr>
            </table>
            <table id="User" border="0" cellpadding="2" cellspacing="1"
                class="newcommon" width="90%">
                <thead></thead>
                <tfoot>
                    <tr>
                        <td class="page" width="8%"></td>
                        <td class="page" width="15%"></td>
                        <td class="page" width="15%"></td>
                        <td class="page" width="13%"></td>
                        <td class="page" width="23%"></td>
                        <td class="page" width="8%"></td>
                        <td class="page" width="18%"></td>
                    </tr>
                </tfoot>
                <%int x = 0;%>
                <tr>
                    <td width="8%" class="page">
                        <select name="validStatus">
                            <option value="0" <c:if test="${utiUwLevelDto.validStatus=='0'}"> selected </c:if> ><s:text name="common.status.invalid" /><%-- 无效 --%></option>
                            <option value="1" <c:if test="${utiUwLevelDto.validStatus=='1'}"> selected </c:if> ><s:text name="common.status.effective" /><%-- 有效 --%></option>
                        </select>
                    </td>
                    <td width="15%" class="page">
                        <input name="userCode" type="text" class="codecode" readonly style="width: 55%;"
                            value='<c:out value="${utiUwLevelDto.userCode}"/>'>
                        <input name="userName" type="text" class="codename" readonly style="width: 38%;"
                            value='<c:out value="${utiUwLevelDto.userName}"/>'>
                    </td>
                    <td class="page" width="15%">
                        <input name="comCode" type="text" class="codecode" readonly style="width: 55%;"
                            value='<c:out value="${utiUwLevelDto.comCode}"/>'>
                        <input name="comName" type="text" class="codename" readonly style="width: 38%;"
                            value='<c:out value="${utiUwLevelDto.comName}"/>'>
                    </td>
                    <td class="page" width="13%">
                        <input name="riskCode" type="text" class="codecode" style="width: 82px;"
                            value="<c:out value="${utiUwLevelDto.riskCode}"/>" >
                    </td>
                    <td width="23%" class="page">
                        <input name="startDate" type="text" class="common" style="width: 70px;" maxLength="10" value='<c:out value="${utiUwLevelDto.startDate}"/>'>
                        <s:text name="prompt.to" /><%-- 至 --%>
                        <input name="endDate" type="text" class="common" style="width: 70px;" maxLength="10" value='<c:out value="${utiUwLevelDto.endDate}"/>'>
                        <INPUT type="hidden" name="flag" value='<c:out value="${utiUwLevelDto.flag}"/>'>
                    </td>
                    <td width="8%" class="page">
                        <img src="${ctx}/pages/platform/images/btnModifyMenu.gif" style="cursor: hand;" border="0" onclick="func2(<%=x%>);">&nbsp;
                        <c:if test="${utiUwLevelDto.flag=='1'}">
                            <img src="${ctx}/pages/platform/images/btnDeleteMenu.gif"
                                style="cursor: hand;" border="0" onclick="func3(<%=x%>);">
                        </c:if>
                    </td>
                    <td width="18%" class="page">
                        <input type="button" value="<s:text name='button.SaveAndReturn.value'/>" class="button2" onclick="saveUpdate();">
                        <%-- 保存並返回 --%>
                        <!-- 
                        <input type="button" value="<s:text name='button.next.value'/>" class="button3" onclick="nextStep();">
                         -->
                        <%-- 下一步 此功能有问题啊，屏蔽了先。chenjie--%>
                    </td>
                </tr>
                <INPUT type="hidden" name="uwType" value='<c:out value="${utiUwLevelDto.uwType}"/>'>
                <INPUT type="hidden" name="modelNo" value='<c:out value="${utiUwLevelDto.modelNo}"/>'>
                <INPUT type="hidden" name="nodeNo" value='<c:out value="${utiUwLevelDto.nodeNo}"/>'>
                <INPUT type="hidden" name="flag" value='<c:out value="${utiUwLevelDto.flag}"/>'>
                <INPUT type="hidden" name="classCode" value='<c:out value="${utiUwLevelDto.classCode}"/>'>
                <%x++;%>
            </table>
    </form>
    <script language="javascript">
        function func2(x){
            var actionType1 = fm.actionType1.value;
            fm.action = "/claim/processUwUserCondition.do?actionType=beforeUpdate&index=" + x;
            fm.submit();
        }
        function func3(x){
            var userName = fm.userName[x+1].value;
            var actionType1 = fm.actionType1.value;
            if(confirm("確實要刪除 " + userName + " 的權限嗎？")){
                fm.action = "/claim/processUwUserCondition.do?actionType=delete&index=" + x +
                              "&actionType1=" + actionType1;
                fm.submit();
            }
        }
        function saveUpdate(){
            if(confirm("確實要儲存並返回嗎？")){
                fm.action = "/claim/processUwLevel.do?actionType=update";
                fm.submit();
            }
        }
        function nextStep(){
            if(confirm("確實要儲存繼續修改嗎？")){
                fm.action = "/claim/processUwLevel.do?actionType=updateContinue";
                fm.submit();
            }
        }
    </script>
    </body>
</html>