<%@ page contentType="text/html; charset=GBK" %>
<%@ include file="/common/taglibs.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<br>
<table class="newcommon" cellpadding="5" cellspacing="0" align="center">
    <tr>
        <td class="top">
            简单因子  - <c:out value="${UtiUwConditionDto.modelName}"/> - <c:out value="${UtiUwConditionDto.riskCode}"/> - <c:out value="${UtiUwConditionDto.comName}"/>(<c:out value="${UtiUwConditionDto.comCode}"/>)
        </td>
    </tr>
</table>
<%int col1 = 0 ; int col2 = 0 ;%>
<c:forEach items="${requestScope.swfNodeList}">
    <%col1++; col2++;%>
</c:forEach>

<table class="newcommon" cellpadding="5" cellspacing="1">
    <tr>
        <td class="top" width="14%">因子名称</td>
        <%int tdWidth = (100-20)/col1;%>
        <c:forEach items="${requestScope.swfNodeList}" var="swfNodeDto">
            <td class="top" width="<%=tdWidth%>%"><c:out value="${swfNodeDto.nodeName}"/></td>
        </c:forEach>
        <td class="top" width="6%">提示</td>
    </tr>
    <%int k=0;%>
    <c:forEach var="factorDto" items="${requestScope.singleFactorList}" varStatus="stat">
        <tr>
            <td class="page" width="14%"><c:out value="${factorDto.factorName}"/></td>
                 <c:forEach items="${requestScope.swfNodeList}" var="swfNodeDto">
                     <%boolean isValueNodeNo = false;%>
                    <td class="page" width="<%=tdWidth%>%">
                        <c:if test="${factorDto.nodeValueList != null}">
                             <c:forEach items="${factorDto.nodeValueList}" var="utiUwConditionDto">
                                 <c:if test="${utiUwConditionDto.nodeNo == swfNodeDto.nodeNo}">
                                    <input type="text" name="simpleFactorValue" class="common" value='<c:out value="${utiUwConditionDto.factorValue}"/>'>
                                      <%isValueNodeNo = true;%>
                                 </c:if>
                             </c:forEach>
                             <input type="hidden" name="nodeNo" value="<c:out value='${swfNodeDto.nodeNo}'/>" >    
                             <INPUT type="hidden" name="simFactorCode" value="<c:out value='${factorDto.factorCode}'/>">
                               <%if(isValueNodeNo == false){%>
                                      <input type="text" name="simpleFactorValue" class="common" value="">
                               <%}%>
                        </c:if>
                        <INPUT type="hidden" name="simpleFactorCode" value="<c:out value='${factorDto.factorCode}'/>">
                        <INPUT type="hidden" name="simpleFactorName" value="<c:out value='${factorDto.factorName}'/>">
                        <INPUT type="hidden" name="simpleFactorAttr" value="<c:out value='${factorDto.factorAttr}'/>">
                        <INPUT type="hidden" name="simpleFactorValueDefault" value="<c:out value='${factorDto.exampleValue}'/>">
                    </td>
                </c:forEach>
            <td class="page" width="6%">
                <center>
                   <a onclick='openTipWindow(<%=k%>);' href="javascript: void(<%=k%>);">提示</a>
                </center>
                <%k++;%>
            </td>
        </tr>
    </c:forEach>
</table>
<jsp:include page="/pages/platform/uwcondition/FactorTipJsInclude.jsp"/>