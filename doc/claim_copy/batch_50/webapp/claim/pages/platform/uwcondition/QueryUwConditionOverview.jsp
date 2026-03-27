<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="ins.framework.common.Page"%>
<%@ taglib uri="/WEB-INF/tlds/claim-app.tld" prefix="app" %>
<html>
    <head>
        <jsp:include page="/pages/platform/uwcondition/StaticJavascript.jsp" />
        <link href="${ctx}/pages/platform/css/Standard.css" rel="stylesheet" type="text/css">
        <script src="${ctx}/pages/platform/uwcondition/js/uwcondition1.js"></script>
    </head>
    <body onload="initPage();">
        <form name="fm" action="${ctx}/processUwCondition.do" method="post">
            <input type="hidden" name="actionType" value="<c:out value='${param.actionType}'/>">
            <table class="common" cellpadding="1" cellspacing="1" align="center">
                <tr>
                    <td colspan="9" align="center" class="top"><s:text name="uwcondition.query" /><%--查询双核条件 --%></td>
                </tr>
                <tr>
                    <td width="10%" class="page"><s:text name="uwcondition.TypeAuditing" />：<%--审核类型 --%></td>
                    <td width="10%" class="page">
                        <input name="uwTypeQuery" type="text" class="codecode" style="width: 60px;"
                            value='<c:out value="${ConditionQueryDto.uwType}"/>'
                            ondblclick="code_CodeQuery(this,'UwType','0,1','Y','C,Y');"
                            onkeyup="code_CodeQuery(this,'UwType','0,1','Y','C,Y');"
                            onchange="code_CodeChange(this,'UwType','0,1','Y','C,Y');">
                    </td>
                    <td width="13%" class="page">
                        <input name="uwTypeNameQuery" type="text" class="codename"
                            value='<c:out value="${ConditionQueryDto.uwTypeName}"/>' readonly>
                    </td>
                    <td width="10%" class="page"><s:text name="uwcondition.AuditDepartment" />：<%--审核部门 --%></td>
                    <td width="10%" class="page">
                        <input name="comCodeQuery" type="text" class="codecode" style="width: 60px;"
                            value='<c:out value="${ConditionQueryDto.comCode}"/>'
                            ondblclick="code_CodeQuery(this,'comCode','0,1','Y');"
                            onkeyup="code_CodeQuery(this,'comCode','0,1','Y');"
                            onchange="code_CodeChange(this,'comCode','0,1','Y');">
                    </td>
                    <td width="14%" class="page">
                        <input name="comNameQuery" type="text" class="codename"
                            value='<c:out value="${ConditionQueryDto.comName}"/>' readonly>
                    </td>
                    <td width="10%" class="page"><s:text name="uwcondition.InsuranceCategories" />：<%--险种大类 --%></td>
                    <td width="10%" class="page">
                        <input name="riskCategoryCodeQuery" type="text" class="codecode" style="width: 60px;"
                            value='<c:out value="${ConditionQueryDto.riskCategoryCode}"/>'
                            ondblclick="code_CodeQuery(this,'RiskCategory','0,1','Y');"
                            onkeyup="code_CodeQuery(this,'RiskCategory','0,1','Y');"
                            onchange="code_CodeChange(this,'RiskCategory','0,1','Y');">
                    </td>
                    <td width="13%" class="page">
                        <input name="riskCategoryNameQuery" type="text" class="codename" readonly
                            value='<c:out value="${ConditionQueryDto.riskCategoryName}"/>'>
                    </td>
                </tr>
                <tr>
                    <td width="10%" class="page"><s:text name="archive.riskClass" />：<%--险类 --%></td>
                    <td width="10%" class="page">
                        <input name="classCodeQuery" type="text" class="codecode" style="width: 60px;"
                            value='<c:out value="${ConditionQueryDto.classCode}"/>'
                            ondblclick="code_CodeQuery(this,'classCodeByRiskCategory','0,1','Y', fm.riskCategoryCodeQuery.value);"
                            onkeyup="code_CodeQuery(this,'classCodeByRiskCategory','0,1','Y', fm.riskCategoryCodeQuery.value);"
                            onchange="code_CodeChange(this,'classCodeByRiskCategory','0,1','Y', fm.riskCategoryCodeQuery.value);">
                    </td>
                    <td width="13%" class="page">
                        <input name="classNameQuery" type="text" class="codename" readonly
                            value='<c:out value="${ConditionQueryDto.className}"/>'>
                    </td>
                    <td width="10%" class="page"><s:text name="db.prpDdbs.riskCode" />：<%--险种 --%></td>
                    <td width="24%" class="page" colspan="2">
                        <input name="riskCodeQuery" type="text" class="codecode" style="width: 150px;"
                            value='<c:out value="${ConditionQueryDto.riskCode}"/>'
                            ondblclick="code_CodeQuery(this,'riskcodeByClassCode','0,1','Y', addCondition('prpDriskClassCode',fm.classCodeQuery.value));"
                            onkeyup="code_CodeQuery(this,'riskcodeByClassCode','0,1','Y', addCondition('prpDriskClassCode',fm.classCodeQuery.value));"
                            onchange="code_CodeChange(this,'riskcodeByClassCode','0,1','Y', addCondition('prpDriskClassCode',fm.classCodeQuery.value));">
                    </td>
                    <td width="10%" class="page"><s:text name="db.prpDprofit.condition" />：<%--条件描述 --%></td>
                    <td width="23%" class="page" colspan="2">
                        <input name="remarkQuery" type="text" class="common"
                            value='<c:out value="${ConditionQueryDto.remark}"/>'>
                    </td>
                </tr>
            </table>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr align="center">
                    <td>
                        <input type="button" class="button" value="<s:text name="prompt.query" />" onclick="doQuery();">
                    </td>
                </tr>
            </table>
            <app:claimPlatFromCodeInput/>
            &nbsp;
            <% 
              Page pageRecorde = (Page)request.getAttribute("page");
            %>
            <input type="hidden" name="pageNo" value="<%=(pageRecorde==null?0:pageRecorde.getCurrentPageNo())%>">
            <input type="hidden" name="rowsCount" value="<%=(pageRecorde==null?0:pageRecorde.getTotalCount())%>">
            <input type="hidden" name="rowsPerPage" value="<%=(pageRecorde==null?0:pageRecorde.getPageSize())%>">
            <table class="common" cellpadding="1" cellspacing="1" align="center"
                id=ResultTable>
                <tr>
                    <td colspan="6" align="center" class="top"><s:text name="uwcondition.query" /><%--查询双核条件 --%></td>
                </tr>
                <tr>
                    <td width="5%" class="top" style="text-align: left">&nbsp;</td>
                    <td width="12%" class="top"><s:text name="uwcondition.TypeAuditing" /><%--审核类型 --%></td>
                    <td width="15%" class="top"><s:text name="archive.riskClass" /><%--险类--%></td>
                    <td width="25%" class="top"><s:text name="db.prpDdbs.riskCode" /><%--险种--%></td>
                    <td width="25%" class="top"><s:text name="uwcondition.AuditDepartment" /><%--审核部门--%></td>
                    <td width="18%" class="top"><s:text name="workflow.template" /><%--模板--%></td>
                </tr>
                    <c:forEach var="iterateDto" items="${requestScope.conditionList}" varStatus="stat">
                        <input type="hidden" name="uwType" value='<c:out value="${iterateDto.uwType}"/>'>
                        <input type="hidden" name="uwTypeName" value='<c:out value="${iterateDto.uwTypeName}"/>'>
                        <input type="hidden" name="classCode" value='<c:out value="${iterateDto.classCode}"/>'>
                        <input type="hidden" name="className" value='<c:out value="${iterateDto.className}"/>'>
                        <input type="hidden" name="riskCode" value='<c:out value="${iterateDto.riskCode}"/>'>
                        <input type="hidden" name="comCode" value='<c:out value="${iterateDto.comCode}"/>'>
                        <input type="hidden" name="comName" value='<c:out value="${iterateDto.comName}"/>'>
                        <input type="hidden" name="modelNo" value='<c:out value="${iterateDto.modelNo}"/>'>
                        <input type="hidden" name="modelName" value='<c:out value="${iterateDto.modelName}"/>'>
                        <input type="hidden" name="remark" value='<c:out value="${iterateDto.remark}"/>'>
                        <input type="hidden" name="createTime" value='<c:out value="${iterateDto.createTime}"/>'>
                        <input type="hidden" name="validStatus" value='<c:out value="${iterateDto.validStatus}"/>'>
                        <tr>
                            <td align="left" class="page">
                                <input type=radio name=checkboxSelect value="<c:out value="${stat.index}"/>">
                            </td>
                            <td align="center" class="page">
                                <c:out value="${iterateDto.uwType}"/>-<c:out value="${iterateDto.uwTypeName}"/>
                            </td>
                            <td align="center" class="page">
                                <c:out value="${iterateDto.classCode}"/>-<c:out value="${iterateDto.className}"/>
                            </td>
                            <td align="center" class="page" width="18%">
                                <c:out value="${iterateDto.riskCode}"/>
                            </td>
                            <td align="center" class="page">
                                <c:out value="${iterateDto.comCode}"/>-<c:out value="${iterateDto.comName}"/>
                            </td>
                            <td align="center" class="page">
                                <c:out value="${iterateDto.modelNo}"/>-<c:out value="${iterateDto.modelName}"/>
                            </td>
                        </tr>
                    </c:forEach>
                <tr>
                    <td colspan="6" align="center" class="page">
                        <app:navigate objectName="page" display="false"/>
                    </td>
                </tr>
                <tr>
                    <td colspan="6" align="center" class="page">
                        <app:command objectName="UwCondition" action="insert,update,delete" path="${ctx}"/>
                    </td>
                </tr>
            </table>
        </form>
        <script language="javascript">
            function doQuery(){
                fm.actionType.value = "query";
                fm.submit();
            }
            /**
             * 导出指定结果列表对象到EXCEL(只保留数字)
             * @table 结果表的名称
             * @since 2005-12-31
             */
            function exportResultDataToExcel(table){
              //copyObjectToExcel(table);
              errorMessage("本页面不支持导出Excel功能！"); 
            }
        </script>
    </body>
</html>
