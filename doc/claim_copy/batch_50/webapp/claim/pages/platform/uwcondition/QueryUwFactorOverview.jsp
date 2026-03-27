<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tlds/claim-app.tld" prefix="app" %>
<%@ page import="ins.framework.common.Page"%>
<html>
    <head>
        <jsp:include page="/pages/platform/uwcondition/StaticJavascript.jsp" />
        <link href="${ctx}/pages/platform/css/Standard.css" rel="stylesheet" type="text/css">
    </head>
    <body onload="initPage();">
        <form name="fm" method="post" action="${ctx}/processUwFactor.do">
            <input type="hidden" name="actionType" value="<c:out value="${param.actionType}"/>">
            <table class="common" cellpadding="1" cellspacing="1" align="center">
                <tr>
                    <td colspan="9" align="center" class="top"><s:text name="uwcondition.queryFactor" /><%--查询双核因子 --%></td>
                </tr>
                <tr>
                    <td width="10%" class="page"><s:text name="uwcondition.TypeAuditing" /><%--审核类型 --%>：</td>
                    <td width="10%" class="page">
                        <input name="uwTypeQuery" type="text" class="codecode" style="width: 70px;"
                            value='<c:out value="${FactorQueryDto.uwType}"/>'
                            ondblclick="code_CodeSelect(this,'UwType','0,1','Y','C,Y');"
                            onkeyup="code_CodeSelect(this,'UwType','0,1','Y','C,Y');"
                            onchange="code_CodeChange(this,'UwType','0,1','Y','C,Y');">
                    </td>
                    <td width="13%" class="page">
                        <input name="uwTypeNameQuery" type="text" class="codename"
                            value='<c:out value="${FactorQueryDto.uwTypeName}"/>' readonly>
                    </td>
                    <td width="10%" class="page"><s:text name="uwcondition.InsuranceCategories" />：<%--险种大类 --%></td>
                    <td width="10%" class="page">
                        <input name="riskCategoryCodeQuery" type="text" class="codecode" style="width: 70px;"
                            value='<c:out value="${FactorQueryDto.riskCategoryCode}"/>'
                            ondblclick="code_CodeSelect(this,'riskCategoryByClassCode','0,1','Y', fm.classCodeQuery.value);"
                            onkeyup="code_CodeSelect(this,'riskCategoryByClassCode','0,1','Y', fm.classCodeQuery.value);"
                            onchange="code_CodeChange(this,'riskCategoryByClassCode','0,1','Y', fm.classCodeQuery.value);">
                    </td>
                    <td width="14%" class="page">
                        <input name="riskCategoryNameQuery" type="text" class="codename" readonly value='<c:out value="${FactorQueryDto.riskCategoryName}"/>'>
                    </td>
                    <td width="10%" class="page"><s:text name="archive.riskClass" />：<%--险类 --%></td>
                    <td width="10%" class="page">
                        <input name="classCodeQuery" type="text" class="codecode" style="width: 70px;"
                            value='<c:out value="${FactorQueryDto.classCode}"/>'
                            ondblclick="code_CodeSelect(this,'classCodeByRiskCategory','0,1','Y', fm.riskCategoryCodeQuery.value);"
                            onkeyup="code_CodeSelect(this,'classCodeByRiskCategory','0,1','Y', fm.riskCategoryCodeQuery.value);"
                            onchange="code_CodeChange(this,'classCodeByRiskCategory','0,1','Y', fm.riskCategoryCodeQuery.value);">
                    </td>
                    <td width="13%" class="page">
                        <input name="classNameQuery" type="text" class="codename" readonly value='<c:out value="${FactorQueryDto.className}"/>'>
                    </td>
                </tr>
                <tr>
                    <td width="10%" class="page"><s:text name="uwcondition.NameFactor" />：<%--因子名称 --%></td>
                    <td width="10%" class="page">
                        <input name="factorNameQuery" type="text" class="common" style="width: 70px;" value='<c:out value="${FactorQueryDto.factorName}"/>'>
                    </td>
                    <td width="13%" class="page">&nbsp;</td>
                    <td width="10%" class="page"><s:text name="uwcondition.FactorCode" />：<%--因子代码 --%></td>
                    <td width="10%" class="page">
                        <input name="factorCodeQuery" type="text" class="common" style="width: 70px;" value='<c:out value="${FactorQueryDto.factorCode}"/>'>
                    </td>
                    <td width="14%" class="page">&nbsp;</td>
                    <td width="10%" class="page"><s:text name="referlaw.validity" />：<%--是否有效 --%></td>
                    <td width="10%" class="page">
                        <select name="validStatus" >
                            <option value="" >&nbsp;&nbsp;全部</option>
                            <option value="1" <c:if test="${FactorQueryDto.validStatus=='1'}"> selected </c:if> >1-有效</option>
                            <option value="0" <c:if test="${FactorQueryDto.validStatus=='0'}"> selected </c:if> >0-註銷</option>
                        </select>
                    </td>
                    <td width="13%" class="page">&nbsp;</td>
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
                    <td colspan="9" class="top"><s:text name="uwcondition.queryFactor" /><%--查询双核因子 --%></td>
                </tr>
                <tr>
                    <td class="top" style="text-align: left">&nbsp;</td>
                    <td class="top"><s:text name="uwcondition.NameFactor" /></td><%--因子名称--%>
                    <td class="top"><s:text name="uwcondition.FactorCode" /></td><%--因子代码--%>
                    <td class="top"><s:text name="uwcondition.TypeAuditing" /></td><%--审核类型--%>
                    <td class="top"><s:text name="uwcondition.InsuranceCategories" /></td><%--险种大类--%>
                    <td class="top"><s:text name="archive.riskClass" /></td><%--险类--%>
                    <td class="top"><s:text name="uwcondition.valueType" /></td><%--取值类型--%>
                    <td class="top"><s:text name="uwcondition.FactorType" /></td><%--因子类型--%>
                    <td class="top"><s:text name="referlaw.validity" /></td><%--是否有效--%>
                </tr>
                <c:forEach items="${requestScope.uwFactorOverview}" var="uwFactorDto" varStatus="stat">
                        <input type="hidden" name="uwType" value='<c:out value="${uwFactorDto.uwType}"/>'>
                        <input type="hidden" name="factorCode" value='<c:out value="${uwFactorDto.factorCode}"/>'>
                        <input type="hidden" name="classCode" value='<c:out value="${uwFactorDto.classCode}"/>'>
                        <tr>
                            <td class="page">
                                <input type=radio name=checkboxSelect value="<c:out value="${stat.index}"/>">
                            </td>
                            <td class="page"><c:out value="${uwFactorDto.factorName}" /></td>
                            <td class="page"><c:out value="${uwFactorDto.factorCode}" /></td>
                            <td class="page">
                                <c:out value="${uwFactorDto.uwType}" /> - <c:out value="${uwFactorDto.uwTypeName}" />
                            </td>
                            <td class="page">
                                <c:out value="${uwFactorDto.riskCategoryCode}" /> - <c:out value="${uwFactorDto.riskCategoryName}" />
                            </td>
                            <td class="page">
                                <c:out value="${uwFactorDto.classCode}" /> - <c:out value="${uwFactorDto.className}" />
                            </td>
                            <td class="page">
                                <c:out value="${uwFactorDto.factorAttr}" /> - <c:out value="${uwFactorDto.factorAttrName}" />
                            </td>
                            <td class="page">
                                <c:out value="${uwFactorDto.multiSelectFlag}" /> - <c:out value="${uwFactorDto.multiSelectName}" />
                            </td>
                            <td class="page">
                                <c:out value="${uwFactorDto.validStatus}" /> - <c:out value="${uwFactorDto.validStatusName}" />
                            </td>
                        </tr>
                </c:forEach>
                <tr>
                    <td colspan="9" align="center" class="page">
                        <app:navigate objectName="page" display="true"/>
                    </td>
                </tr>
                <tr>
                    <td colspan="9" align="center" class="page">
                        <app:command objectName="UwFactor" action="update" path="${ctx}" />
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
