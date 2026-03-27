<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
    <head>
        <jsp:include page="/pages/platform/uwcondition/StaticJavascript.jsp"/>
        <link href="${ctx}/pages/platform/css/Standard.css" rel="stylesheet" type="text/css">
        <script src="${ctx}/pages/platform/system/uwfactor/js/uwfactor.js"></script>
    </head>
    <body onload="initPage();">
        <form name="fm" method="post" action="${ctx}/processUwFactor.do">
            <input type="hidden" name="actionType" value="<c:out value="${param.actionType}"/>">
            <input type="hidden" name="uwType" value='<c:out value="${factorDto.uwType}"/>'>
            <input type="hidden" name="classCode" value='<c:out value="${factorDto.classCode}"/>'>
            <input type="hidden" name="factorCode" value='<c:out value="${factorDto.factorCode}"/>'>
            <input type="hidden" name="riskCategoryCode" value='<c:out value="${factorDto.riskCategoryCode}"/>'>
            <input type="hidden" name="factorName" value='<c:out value="${factorDto.factorName}"/>'>
            <input type="hidden" name="factorAttr" value='<c:out value="${factorDto.factorAttr}"/>'>
            <input type="hidden" name="multiSelectFlag" value='<c:out value="${factorDto.multiSelectFlag}"/>'>
            <input type="hidden" name="isCodeFlag" value='<c:out value="${factorDto.isCodeFlag}"/>'>
            <input type="hidden" name="operator" value='<c:out value="${factorDto.operator}"/>'>
            <table class="common" cellpadding="5" cellspacing="1" align="center">
                <tr>
                    <td colspan="6" align="center" class="top"><strong><s:text name="uwcondition.DoubleFactor"/></strong></td>
                </tr>
                <tr>
                    <td width="15%" class="page"><s:text name="uwcondition.TypeAuditing"/>：<%-- 审核类型 --%></td>
                    <td width="20%" class="page">
                        <c:out value="${factorDto.uwType}" /> - <c:out value="${factorDto.uwTypeName}" />
                    </td>
                    <td width="15%" class="page">&nbsp;</td>
                    <td width="15%" class="page">&nbsp;</td>
                    <td width="20%" class="page">&nbsp;</td>
                    <td width="15%" class="page">&nbsp;</td>
                </tr>
                <tr>
                    <td width="15%" class="page"><s:text name="uwcondition.InsuranceCategories"/>：<%-- 险种大类 --%></td>
                    <td width="20%" class="page">
                        <c:out value="${factorDto.riskCategoryCode}" /> - <c:out value="${factorDto.riskCategoryName}" />
                    </td>
                    <td width="15%" class="page">&nbsp;</td>
                    <td width="15%" class="page"><s:text name="archive.riskClass"/>：<%-- 险类 --%></td>
                    <td width="20%" class="page">
                        <c:out value="${factorDto.classCode}" /> - <c:out value="${factorDto.className}" />
                    </td>
                    <td width="15%" class="page">&nbsp;</td>
                </tr>
                <tr>
                    <td width="15%" class="page"><s:text name="uwcondition.NameFactor"/>：<%-- 因子名称 --%></td>
                    <td width="20%" class="page"><c:out value="${factorDto.factorName}" /></td>
                    <td width="15%" class="page">&nbsp;</td>
                    <td width="15%" class="page"><s:text name="uwcondition.FactorCode"/>：<%-- 因子代码 --%></td>
                    <td width="20%" class="page"><c:out value="${factorDto.factorCode}" /></td>
                    <td width="15%" class="page">&nbsp;</td>
                </tr>
                <tr>
                    <td width="15%" class="page"><s:text name="uwcondition.FactorProperty"/>：<%-- 因子属性 --%></td>
                    <td width="20%" class="page">
                        <c:out value="${factorDto.factorAttr}" /> - <c:out value="${factorDto.factorAttrName}" />
                    </td>
                    <td width="15%" class="page">&nbsp;</td>
                    <td width="15%" class="page"><s:text name="uwcondition.AssignmentExample"/>：<%-- 赋值示例 --%></td>
                    <td width="20%" class="page">
                        <input name="exampleValue" type="text" class="common" value='<c:out value="${factorDto.exampleValue}"/>'>
                        <img src="${ctx}/pages/platform/images/imgMustInput.gif" />
                    </td>
                    <td width="15%" class="page">&nbsp;</td>
                </tr>
                <tr>
                    <td width="15%" class="page"><s:text name="uwcondition.FactorType"/>：<%-- 因子类型 --%></td>
                    <td width="20%" class="page">
                        <c:out value="${factorDto.multiSelectFlag}" /> - <c:out value="${factorDto.multiSelectName}" />
                    </td>
                    <td width="15%" class="page">&nbsp;</td>
                    <td width="15%" class="page"><s:text name="referlaw.validity"/>：<%-- 是否有效 --%></td>
                    <td width="20%" class="page">
                        <select name="validStatus" class="one">
                           <option value="1" <c:if test="${factorDto.validStatus=='1'}"> selected </c:if> >1-有效</option>
                           <option value="0" <c:if test="${factorDto.validStatus=='0'}"> selected </c:if> >0-註銷</option>
                        </select>
                    </td>
                    <td width="15%" class="page">&nbsp;</td>
                </tr>
                <tr>
                    <td width="15%" class="page"><s:text name="uwcondition.AssignmentTips"/>：<%-- 赋值提示 --%></td>
                    <td width="70%" class="page" colspan="4">
                        <input name="valueDesc" type="text" class="common" value='<c:out value="${factorDto.valueDesc}"/>'>
                    </td>
                    <td width="15%" class="page">&nbsp;</td>
                </tr>
                <tr>
                    <td width="15%" class="page"><s:text name="db.prpLcomponent.remark"/>：<%-- 备注 --%></td>
                    <td width="70%" class="page" colspan="4">
                        <input name="remark" type="text" class="common" value='<c:out value="${factorDto.remark}"/>'>
                    </td>
                    <td width="15%" class="page">&nbsp;</td>
                </tr>
            </table>
            <c:if test="${factorDto.multiSelectFlag == 'C'}">
                <span style="display: none">
                    <table class="common" style="display: none" id="Combo_Data" cellspacing="1" cellpadding="2">
                        <tbody>
                            <tr>
                                <td class="page" width="15%"><s:text name="uwcondition.ConditionNumber"/>：<%--风险条件序号  --%></td>
                                <td class="page" width="10%">
                                    <input name="serialNo" type="text" class="common" value="">
                                </td>
                                <td class="page" width="15%"><s:text name="uwcondition.RiskCode"/>：<%-- 风险条件代码 --%></td>
                                <td class="page" width="20%">
                                    <input name="codeType" type="text" class="codecode" value=""
                                        ondblclick="code_CodeSelect(this,'prpDtype','0,1','Y');"
                                        onkeyup="code_CodeSelect(this,'prpDtype','0,1','Y');"
                                        onchange="code_CodeChange(this,'prpDtype','0,1','Y');">
                                </td>
                                <td class="page" width="15%"><s:text name="uwcondition.RiskName"/>：<%-- 风险条件名称 --%></td>
                                <td class="page" width="20%">
                                    <input name="typeName" type="text" class="common" value="">
                                </td>
                                <td class="page" width="5%">
                                    <input type=button name="btnDelCombo" class="smallbutton"
                                        onclick="deleteRow(this,'Combo');" value="-" style="cursor: hand">
                                </td>
                            </tr>
                        </tbody>
                    </table> 
                </span>&nbsp;
                <table id="Combo" border="0" cellpadding="2" cellspacing="1" class="common">
                    <thead></thead>
                    <tfoot>
                        <tr>
                            <td class="page" width="15%">&nbsp;</td>
                            <td class="page" width="10%">&nbsp;</td>
                            <td class="page" width="15%">&nbsp;</td>
                            <td class="page" width="20%">&nbsp;</td>
                            <td class="page" width="15%">&nbsp;</td>
                            <td class="page" width="20%">&nbsp;</td>
                            <td class="page" width="5%">
                                <input type="button" value="+" class="smallbutton"
                                    onclick="insertRow('Combo');" name="btnAddCombo" style="cursor: hand">
                            </td>
                        </tr>
                    </tfoot>
                    <c:forEach items="${factorDto.utiUwComboFactorList}" var="comboFactorDto">
                        <tr>
                            <td class="page" width="15%"><s:text name="uwcondition.ConditionNumber"/>：<%--风险条件序号  --%></td>
                            <td class="page" width="10%">
                                <input name="serialNo" type="text" class="common" value='<c:out value="${comboFactorDto.serialNo}"/>'>
                            </td>
                            <td class="page" width="15%"><s:text name="uwcondition.RiskCode"/>：<%-- 风险条件代码 --%></td>
                            <td class="page" width="20%">
                                <input name="codeType" type="text" class="codecode"
                                    ondblclick="code_CodeSelect(this,'prpDtype','0,1','Y');"
                                    onkeyup="code_CodeSelect(this,'prpDtype','0,1','Y');"
                                    onchange="code_CodeChange(this,'prpDtype','0,1','Y');"
                                    value='<c:out value="${comboFactorDto.codeType}"/>'>
                            </td>
                            <td class="page" width="15%"><s:text name="uwcondition.RiskName"/>：<%-- 风险条件名称 --%></td>
                            <td class="page" width="20%">
                                <input name="typeName" type="text" class="common" value='<c:out value="${comboFactorDto.typeName}"/>'>
                            </td>
                            <td class="page" width="5%">
                                <input type="button" name="btnDelCombo" class="smallbutton"
                                    onclick="deleteRow(this,'Combo');" value="-" style="cursor: hand">
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:if>
            &nbsp;
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr align="center">
                    <td>
                        <input type="button" class="button" value="<s:text name='button.save.value'/>" onclick="submitForm();"><%-- 保  存 --%>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <input type="button" class="button" value="<s:text name='button.return.value'/>" onclick="returnBack();"><%-- 返  回 --%>
                    </td>
                </tr>
            </table>
            <app:claimPlatFromCodeInput/>
        </form>
        <script language="javascript">
           function submitForm(){
              if(trim(fm.exampleValue.value) == ""){
                  alert("系統預設值不能为空！");
                  return;
               }
              if(confirm("確實要儲存嗎？")){
                  fm.submit();
              }
           }
           function returnBack(){
                fm.action = "/claim/processUwFactor.do?actionType=queryContinue";
                fm.submit();
           }
       </script>
    </body>
</html>
