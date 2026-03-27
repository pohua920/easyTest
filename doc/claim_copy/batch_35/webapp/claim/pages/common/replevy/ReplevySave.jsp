<%@ include file="/common/taglibs.jsp"%>
<table border="0" cellspacing="0" cellpadding="0">
<c:choose>
    <c:when test="${param.editType=='addQuery'||param.editType=='editQuery'||param.editType=='ADD'||param.editType=='EDIT'}">
        <tr align="center">
            <td class=button align="center">
                <input type=button name=buttonSave class='button' value="<s:text name="undwrt.Submit" />" onclick="return saveForm();" />
                &nbsp;&nbsp;<%--提交 --%>
            </td>
            <td class=button align="center">
                <input type=button name=buttonCancel class='button' value="<s:text name="prompt.cancel" />" onclick="history.go(-1);" />
                &nbsp;&nbsp;<%--取消 --%>
            </td>
        </tr>
    </c:when>
    <c:when test="${param.editType=='UNDWRT'}">
        <tr align="center">
            <td class=button align="center">
                <input type=button name="buttonUndwrt" class='button' value="<s:text name="button.checkPass.value" />" onclick="return undwrt();" />
                &nbsp;&nbsp;<%--审核通过 --%>
            </td>
        <c:if test="${not empty requestScope.swfLog}">
            <td class=button align="center">
                <input type=button name="buttonSubmit" class='button' value="提交上級" onclick="return submitSuperior();" />
                &nbsp;&nbsp;
            </td>        
        </c:if>
            <td class=button align="center">
                <%/** 兼容無審核流程的老數據  **/%>
                <c:choose>
                   <c:when test="${empty requestScope.swfLog}">
                      <input type=button name="buttonBack" class='button' value="<s:text name="uwcondition.backModified" />" onclick="return withdrawal();" />
                   </c:when>
                   <c:otherwise>
                      <input type=button name="buttonBack" class='button' value="<s:text name="uwcondition.backModified" />" onclick="return submitJunior();" />
                   </c:otherwise>
                </c:choose>
                &nbsp;&nbsp;
                <%--打回修改--%>
            </td>
        <c:if test="${not empty requestScope.swfLog}">
            <td class=button align="center">
                <input type=button name=buttonGiveup class='button' value="<s:text name='button.giveUpTask.value' />" onclick="taskGiveup();">
                &nbsp;&nbsp;
            </td>        
        </c:if>
            <td class=button align="center">
                <input type=button name="buttonCancel" class='button' value="<s:text name="prompt.cancel" />" onclick="history.go(-1);" />
                &nbsp;&nbsp;
                <%--取消--%>
            </td>
        </tr>
    </c:when>
    <c:otherwise>
        <tr align="center">
            <td>&nbsp;</td>
            <td class=button align="center">
                <input type=button name=buttonSave class='button' value="<s:text name="prompt.back" />" onclick="history.go(-1);" />
                &nbsp;&nbsp;
                <%--返回 --%>
            </td>
        </tr>
    </c:otherwise>
</c:choose>
</table>