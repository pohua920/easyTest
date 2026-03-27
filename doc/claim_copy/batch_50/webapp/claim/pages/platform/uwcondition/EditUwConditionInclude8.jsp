<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<br>
<!------------------------------------------------------------------------------------------------------------>
<table class="newcommon" cellpadding="5" cellspacing="0" align="center">
    <tr>
        <td class="top">
            <s:text name="uwcondition.CombinationFactor"/> - <c:out value="${UtiUwConditionDto.modelName}" /> - <c:out value="${UtiUwConditionDto.riskCode}" /> - <c:out value="${UtiUwConditionDto.comName}" />(<c:out value="${UtiUwConditionDto.comCode}" />)<%-- 组合因子 --%>
        </td>
    </tr>
</table>
<c:if test="${requestScope.comboFactorList != null}">
<%int tableId=0;%>
<c:forEach items="${requestScope.comboFactorLis}" var="utiUwFactorDto">
    <span style="display:none">
        <%int col1 = 0, col2 = 0;int nodeCount = 0;%>
        <c:forEach items="${utiUwFactorDto.utiUwComboFactorList}"><%col1++; col2++;%></c:forEach>
        <c:forEach items="${requestScope.swfNodeList}"><%nodeCount++;%></c:forEach>
        <%
            int tdWidth = 0 ;
            int codeWidth = 0 ;
            int nameWidth = 0 ;
            int nodeWidth = 0 ;
            if(col1==1){
                tdWidth = 17;
                codeWidth = 50;
                nameWidth = 37;
                nodeWidth = (int)(72/nodeCount);
            }else{
                tdWidth = (int)((28)/col1);
                codeWidth = 60;
                nameWidth = 95;
                nodeWidth = (int)(61/nodeCount);
            }
        %>
        <table class="newcommon" style="display:none" id="Combo<%=tableId%>_Data" cellspacing="1" cellpadding="1">
            <tbody>
                <tr>
                    <td class="page" width="8%;">
                        <p align="center">
                        <select name="utiUwConditionFactorCodeValue"  onchange="isCheck(this,<%=col1%>);">   
                          <option value="Value"><s:text name="uwcondition.Details"/></option><%-- 详细 --%>
                          <option value="OtherValue" ><s:text name="certainLoss.other"/></option><%-- 其它 --%>
                        </select>
                        </p>
                    </td>
                    <c:forEach items="${utiUwFactorDto.utiUwComboFactorList}" var="comboFactorDto">
                        <td class="page" width="<%=tdWidth%>%;">
                            <INPUT type="hidden" name="combofactorFactorCode" value='<c:out value="${utiUwFactorDto.factorCode}" />'>
                            <INPUT type="hidden" name="combofactorCodeType" value='<c:out value="${utiUwFactorDto.factorCode}" />,<c:out value="${comboFactorDto.codeType}" />'>
                            <input type="text" name="combofactorCodeCode" class="codecode" style="width:<%=codeWidth%>%;" value=""
                              ondblclick="code_CodeSelect(this,'<c:out value="${comboFactorDto.codeType}" />','0,1','Y','<c:out value="${UtiUwConditionDto.riskCode}" />');" style="width:84">
                            <input type="text" name="typeName" class="codename" value="" style="width:<%=nameWidth%>%;" readonly>
                        </td>
                    </c:forEach>
                <c:forEach items="${requestScope.swfNodeList}" var="swfNodeDto">
                    <td class="page" width="<%=nodeWidth%>%">
                        <input type="text" class="common" name="combofactorFactorValue">
                          <input type="hidden" name="comboNodeNo" value="<c:out value='${swfNodeDto.nodeNo}'/>" >    
                          <INPUT type="hidden" name="combofactorFactorName" value="<c:out value='${utiUwFactorDto.factorName}'/>" >
                        <INPUT type="hidden" name="combofactorFactorAttr" value="<c:out value='${utiUwFactorDto.factorAttr}'/>" >
                    </td>
                </c:forEach>
                    <td class="page" width="3%">
                        <input type=button class="smallbutton" name="btnDelCombo<%=tableId%>" onclick="deleteRow(this,'Combo<%=tableId%>');" value="-" style="cursor: hand">
                    </td>
                </tr>
            </tbody>
                <INPUT type="hidden" name="combofactorFactorCols" value="<c:out value='${utiUwFactorDto.factorCode}'/>,<%=col1%>">
        </table>
    </span>
    <table id="Combo<%=tableId%>" border="0" cellpadding="1" cellspacing="1" class="newcommon">
        <thead>
            <tr>
                <td class="top" colspan="<%=col1+nodeCount+4%>"><c:out value='${utiUwFactorDto.factorName}'/></td>
            </tr>
            <tr>
                <td class="top" width="8%"></td>
            <c:forEach items="${utiUwFactorDto.utiUwComboFactorList}" var="comboFactorDto">
                <td class="top" width="<%=tdWidth%>%;"><c:out value='${comboFactorDto.typeName}'/></td>
            </c:forEach>
            <c:forEach items="${requestScope.swfNodeList}" var="swfNodeDto">
                <td class="top" width="<%=nodeWidth%>%;"><c:out value='${swfNodeDto.nodeName}'/></td>
            </c:forEach>
                <td class="top" width="3%"></td>
            </tr>
        </thead>
        <tfoot>
            <tr>
                <td class="page" width="8%"></td>
                <%for(;col2>0;col2--){%><td class="page" width="<%=tdWidth%>%;">&nbsp;</td><%}%>
                <%for(;nodeCount>0;nodeCount--){%><td class="page" width="<%=nodeWidth%>%;">&nbsp;</td><%}%>
                <%--<td class="page" width="5%">&nbsp;</td>--%>
                <td class="page" width="3%">
                    <input type="button" value="+" class="smallbutton" onclick="insertRow('Combo<%=tableId%>');" style="cursor: hand">
                </td>
            </tr>
        </tfoot>
        <c:forEach items="${utiUwFactorDto.comboRowList}" var="comboRowDto">
                <tr>
                    <td class="page" width="13%;">
                        <p align="center">
                            <select name="utiUwConditionFactorCodeValue" class="three" onchange="isCheck(this,<%=col1%>);">
                              <option value="Value" <c:if test="${comboRowDto.utiUwConditionFactorCodeValue!='OtherValue'}"> selected </c:if> ><s:text name="uwcondition.Details"/></option><%-- 详细 --%>
                              <option value="OtherValue" <c:if test="${comboRowDto.utiUwConditionFactorCodeValue=='OtherValue'}"> selected </c:if> ><s:text name="certainLoss.other"/></option><%-- 其它 --%>
                            </select>
                        </p>
                    </td>
                <c:forEach items="${comboRowDto.cellList}" var="cellDto">
                    <td class="page" width="<%=tdWidth%>%;">                
                        <INPUT type="hidden" name="combofactorFactorCode" value='<c:out value="${utiUwFactorDto.factorCode}"/>'>
                        <INPUT type="hidden" name="combofactorCodeType" value='<c:out value="${utiUwFactorDto.factorCode}"/>,<c:out value="${comboFactorDto.codeType}"/>'>
                        <input type="text" name="combofactorCodeCode" class="codecode" style="width:<%=codeWidth%>%;" value='<c:out value="${cellDto.codeCode}"/>'
                        ondblclick="code_CodeSelect(this,'<c:out value="${cellDto.codeType}"/>','0,1','Y','<c:out value="${UtiUwConditionDto.riskCode}"/>');" 
                         <c:if test="${comboRowDto.utiUwConditionFactorCodeValue!='OtherValue'}"> style="display:none;" </c:if> >
                        <input name="typeName" type="text" class="codename" readonly style="width:<%=nameWidth%>%;" value='<c:out value="${cellDto.codeName}"/>' >
                    </td>
                </c:forEach>
                <c:forEach items="${requestScope.swfNodeList}" var="swfNodeDto" >
                        <% boolean isValueNodeNo = false;%>
                    <td class="page" width="<%=nodeWidth%>%">
                    <c:if test="${comboRowDto.valueList != null}">
                        <c:forEach items="${comboRowDto.valueList}" var="utiUwConditionDto">
                           <c:if test="${utiUwConditionDto.nodeNo == swfNodeDto.nodeNo}">
                                  <input type="text" name="combofactorFactorValue" class="common" value='<c:out value="${utiUwConditionDto.factorValue}"/>'>
                                  <%isValueNodeNo = true;%>
                           </c:if>
                        </c:forEach>
                              <%if(isValueNodeNo == false){%>
                                      <input type="text" class="common" name="combofactorFactorValue">
                              <%}%>
                    </c:if>
                        <input type="hidden" name="comboNodeNo" value="<c:out value="${swfNodeDto.nodeNo}"/>" >
                          <INPUT type="hidden" name="combofactorFactorName" value='<c:out value="${utiUwFactorDto.factorName}"/>'>
                        <INPUT type="hidden" name="combofactorFactorAttr" value='<c:out value="${utiUwFactorDto.factorAttr}"/>'>                
                    </td>
                </c:forEach>
                <td class="page" width="3%">
                    <input type="button" class="smallbutton" name="btnDelCombo<%=tableId%>" onclick="deleteRow(this,'Combo<%=tableId%>');" value="-" style="cursor: hand">
                </td>
            </tr>
        </c:forEach>
    </table>
    &nbsp;
<%tableId++;%>
</c:forEach>
</c:if>
<script>
    function isCheck(index,combCount){
           recentDeletedRowNo = parseInt(getElementOrder(index));
           var codeValue = fm.utiUwConditionFactorCodeValue[recentDeletedRowNo-1].value;
        var equalCodeValue = "OtherValue";
        var arithmetic = recentDeletedRowNo*combCount-combCount;
        if(codeValue == equalCodeValue){
            for(var i = 0 ; i < combCount ; i++){
                fm.combofactorCodeCode[arithmetic + i].value = "";
                fm.typeName[arithmetic + i].value = "";
                fm.combofactorCodeCode[arithmetic + i].id=arithmetic + i;
                fm.combofactorCodeCode[arithmetic + i].style.display="none";
                //fm.combofactorCodeCode[arithmetic + i].readonly = "readonly";
            }
        }else{
            for(var i = 0 ; i < combCount ; i++){
                fm.combofactorCodeCode[arithmetic + i].style.display="block";
            }
        }
    }
</script>