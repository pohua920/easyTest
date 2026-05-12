<!--
****************************************************************************
* DESC       ：显示核赔意见文字页面
* AUTHOR     : 中科软
* MODIFYLIST ：Name       Date            Reason/Contents
*
****************************************************************************
-->
<%@ include file="/common/taglibs.jsp"%>
<table class="common" width="100%">
    <tr>
        <td class="common" style="text-align: left;">
            <img style="cursor: hand;" src="${ctx}/images/butCollapseBlue.gif" name="HePeiUndwrtTextImg" onclick="showPage(this,HePeiUndwrtText)">
            <s:text name="prepay.reviewComments" /><br><%--核賠意見 --%>
            <table class="common" cellpadding="5" cellspacing="1" id="HePeiUndwrtText" style="display: none">
                <thead>
                    <tr class=listtitle>
                        <td width="5%" align="center" nowrap>
                            <s:text name="regist.prpLregist.serialNo" /><%--序号--%>
                        </td>
                        <td width="20%" align="center" nowrap>
                            <s:text name="compensate.computeBookNum" /><%--计算书号 --%>
                        </td>
                        <td width="25%" align="center" nowrap>
                            <s:text name="pub.approvalPhrase" /><%--审批片语 --%>
                        </td>
                        <td width="50%">
                            <s:text name="pub.approvalOpinion" /><%--审批意见 --%>
                        </td>
                    </tr>
                </thead>
                <tbody>
                    <c:if test="${not empty requestScope.uwNotionList}">
                        <c:forEach items="${requestScope.uwNotionList }" var="uwNotionTemp" varStatus="stat">
                            <c:choose>
                                <c:when test="${stat.index%2==0}"><tr class="listodd"></c:when>
                                <c:otherwise><tr class="listeven"></c:otherwise>
                            </c:choose>
                            <td><c:out value="${stat.count}" /></td>
                            <td><c:out value="${uwNotionTemp.businessNo}" /></td>
                            <td><c:out value="${uwNotionTemp.notion}" /></td>
                            <td><c:out value="${uwNotionTemp.handleText}" /></td>
                        </c:forEach>
                    </c:if>
                </tbody>
            </table>
        </td>
    </tr>
</table>