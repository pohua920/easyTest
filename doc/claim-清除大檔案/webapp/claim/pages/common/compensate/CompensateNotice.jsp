<%--
****************************************************************************
* DESC       ：发送再保摊赔通知函
* AUTHOR     ：中科软
* MODIFYLIST ：Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ include file="/common/taglibs.jsp"%>
<table class="common" align="center">
    <tr>
        <td class="common" style="text-align: left">
            &nbsp;&nbsp;<input type="checkbox" name="prpLcompensateInformReinsFlag" value="1" <c:if test="${prpLcompensate.informReinsFlag == '1'}">checked="checked"</c:if> >通知再保
        </td>
    </tr>
</table>