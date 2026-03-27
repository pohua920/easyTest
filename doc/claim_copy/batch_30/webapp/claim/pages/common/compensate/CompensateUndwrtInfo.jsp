<%--
****************************************************************************
* DESC       ：核赔退回理算信息页面
* AUTHOR     ：中科软
* MODIFYLIST ：Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ include file="/common/taglibs.jsp"%>
<table class="common" width="100%" align="center">
    <tr class=mline>
        <td class="common" style="text-align: left;">
            <img style="cursor: hand;" src="${ctx }/images/butCollapseBlue.gif" name="RegistTextImg" onclick="showPage(this,CompensateUndwrtInfo)">
            <s:text name="compensate.hepeiReturnReason" /><!-- 核赔退回原因 --><br>
            <table class="common" align="left" id="CompensateUndwrtInfo" style="display: none" cellspacing="1" width="100%">
                <tbody>
                    <c:forEach var="swfNotion" items="${requestScope.swfNotion.swfNotionList}" varStatus="indexSwfNotion">
                        <tr>
                            <td>
                                <c:out value="${swfNotion.handleText}" />
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </td>
    </tr>
</table>