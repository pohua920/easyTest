<%--
****************************************************************************
* DESC       ：显示付款说明页面
* AUTHOR     ：中科软
* MODIFYLIST ：Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ include file="/common/taglibs.jsp"%>
<c:set var="displayFlag" value="none" />
<c:if test="${requestScope.riskType=='Y'}"><%-- 水险放开屏蔽 --%>
    <c:set var="displayFlag" value="block" />
</c:if>
<table class="common" align="center" style="display: block">
    <tr>
        <td class="common" style="text-align: left;">
            <img style="cursor: hand;" src="${ctx }/images/butCollapseBlue.gif" name="PayTextImg" onclick="showPage(this,PayText)">
            <s:text name="compensate.payInformation" /><!-- 付款说明 --><br>
            <table class="common" align="center" id="PayText" style="display: none">
                <tbody>
                    <tr>
                        <td class="input" style="text-align: center;" colspan="0">
                            <textarea style="wrap: hard" rows="15" cols="80" name="prpLctextContextPayTextInnerHTML">${prpLctextPayText.context}</textarea>
                        </td>
                    </tr>
                </tbody>
            </table>
        </td>
    </tr>
</table>