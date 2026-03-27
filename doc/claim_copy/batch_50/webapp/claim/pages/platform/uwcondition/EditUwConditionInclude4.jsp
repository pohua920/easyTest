<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<br>
<table class="common" cellpadding="5" cellspacing="1" align="center">
    <tr>
        <td class="top">
            <s:text name="uwcondition.EnumeratedFactors" /> - <c:out value="${conditionDto.nodeName}" /><%-- Ã¶¾ÙÒò×Ó --%>
        </td>
    </tr>
</table>
<table class="common" cellpadding="0" cellspacing="0" align="center">
    <c:if test="${requestScope.enumFactorList != null}">
        <%int i = 0; %>
        <c:forEach items="${requestScope.enumFactorList}" var="factorDto">
            <%if (i % 2 == 0) {%>
            <tr>
                <%}i++;%>
                <td width="50%">
                    <c:if test="${factorDto.enumCodeList != null}">
                        <table class="common" cellpadding="3" cellspacing="1">
                            <tr>
                                <td class="top"><c:out value="${factorDto.factorName}" /></td>
                                <INPUT type="hidden" name="enumfactorFactorCode" value='<c:out value="${factorDto.factorCode}" />'>
                            </tr>
                        </table>
                        <div style="height: 250px; overflow: auto;">
                            <table class="common" cellpadding="2" cellspacing="1" align="top">
                                <c:forEach items="${factorDto.enumCodeList}" var="codeDto">
                                    <tr>
                                        <td class="page">
                                            <input type="checkbox" name="enumfactorCheckbox"
                                                value='<c:out value="${factorDto.factorCode}" />,<c:out value="${codeDto.codeCode}" />'
                                                <c:out value="${codeDto.checked}" /> >
                                            <c:out value="${codeDto.codeCode}" /> - <c:out value="${codeDto.codeName}" />
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </div>
                    </c:if>
                </td>
                <%if (i % 2 == 0) {%>
            </tr>
            <%}%>
        </c:forEach>
        <%if (i % 2 != 0) {%>
            <td width="50%"></td>
        </tr>
        <%}%>
    </c:if>
</table>