<%@ include file="/common/taglibs.jsp"%>
<table class="common" align="center" width="100%" <c:if test="${pageScope.registerFlag||pageScope.negotiationFlag}">style="display: none"</c:if> >
    <tr>
        <td class="subformtitle" style="text-align: left">
            <img style="cursor: hand;" src="${ctx}/images/butCollapseBlue.gif" name="LtextImg" onclick="showPage(this,Ltext)">
            <s:text name="replevy.recoverWords" />
            <%--×·³¥ÎÄ×Ö --%>
            <br>
            <table class="common" align="center" id="Ltext" style="display: none">
                <tbody>
                    <tr>
                        <td class="title" style="text-align: center;">
                            <c:choose>
                                <c:when test="${param.editType=='SHOW'||((param.editType=='EDIT'||param.editType=='UNDWRT')&&!pageScope.negotiationFlag)}">
                                    <textarea name="prpLrtextContext" wrap="hard" rows=10 cols=80 style="width: 800px">${prpLctext.context}</textarea>
                                </c:when>
                                <c:otherwise>
                                    <textarea name="prpLrtextContext" wrap="hard" rows=10 cols=80 style="width: 800px"></textarea>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </tbody>
            </table>
        </td>
    </tr>
</table>
