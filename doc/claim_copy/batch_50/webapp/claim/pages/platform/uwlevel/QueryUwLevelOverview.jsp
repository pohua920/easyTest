<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="ins.framework.common.Page"%>
<%@ taglib uri="/WEB-INF/tlds/claim-app.tld" prefix="app"%>
<html>
    <head>
        <jsp:include page="/pages/platform/uwcondition/StaticJavascript.jsp" />
        <link href="${ctx}/pages/platform/css/Standard.css" rel="stylesheet" type="text/css">
        <script src="${ctx}/pages/platform/uwlevel/js/uwlevel.js"></script>
    </head>
    <body onload="initPage();">
        <form name="fm" action="${ctx}/processUwLevel.do" method="post">
            <input type="hidden" name="actionType" value="<c:out value="${param.actionType}"/>">
            <table class="common" cellpadding="1" cellspacing="1" align="center">
                <tr>
                    <td colspan="9" align="center" class="top">
                        <s:text name="uwlevel.QueryCondition" /><%-- 核赔人员查询条件 --%>
                    </td>
                </tr>
                <tr>
                    <td width="10%" class="page">
                        <s:text name="uwcondition.TypeAuditing" />：
                    </td>
                    <%-- 审核类型 --%>
                    <td width="10%" class="page">
                        <input name="uwTypeQuery" type="text" class="codecode" style="width: 70px;"
                            value='<c:out value="${LevelQueryDto.uwType}"/>'
                            ondblclick="code_CodeQuery(this,'UwType','0,1','Y','C,Y');"
                            onkeyup="code_CodeQuery(this,'UwType','0,1','Y','C,Y');"
                            onchange="code_CodeChange(this,'UwType','0,1','Y','C,Y');">
                    </td>
                    <td width="13%" class="page">
                        <input name="uwTypeNameQuery" type="text" class="codename"
                            value='<c:out value="${LevelQueryDto.uwTypeName}"/>' readonly>
                    </td>
                    <td width="10%" class="page">
                        <s:text name="uwlevel.NuclearClaims" />：<%-- 核赔员 --%>
                    </td>
                    <td width="10%" class="page">
                        <input name="userCodeQuery" type="text" class="codecode" style="width: 70px;"
                            value='<c:out value="${LevelQueryDto.userCode}"/>'
                            ondblclick="code_CodeQuery(this,'userCode','0,1','Y');"
                            onkeyup="code_CodeQuery(this,'userCode','0,1','Y');"
                            onchange="code_CodeChange(this,'userCode','0,1','Y');">
                    </td>
                    <td width="14%" class="page">
                        <input name="userNameQuery" type="text" class="codename" value='<c:out value="${LevelQueryDto.userName}"/>' readonly>
                    </td>
                    <td width="10%" class="page">
                        <s:text name="uwcondition.AuditDepartment" />：<%-- 审核部门 --%>
                    </td>
                    <td width="10%" class="page">
                        <input name="comCodeQuery" type="text" class="codecode" style="width: 70px;"
                            value='<c:out value="${LevelQueryDto.comCode}"/>'
                            ondblclick="code_CodeQuery(this,'comCode','0,1','Y');"
                            onkeyup="code_CodeQuery(this,'comCode','0,1','Y');"
                            onchange="code_CodeChange(this,'comCode','0,1','Y');">
                    </td>
                    <td width="14%" class="page">
                        <input name="comNameQuery" type="text" class="codename" value='<c:out value="${LevelQueryDto.comName}"/>' readonly>
                    </td>
                </tr>
                <tr>
                    <td width="10%" class="page">
                        <s:text name="uwcondition.Template" />：<%-- 模板 --%>
                    </td>
                    <td width="10%" class="page">
                        <input name="modelNoQuery" type="text" class="codecode" style="width: 70px;"
                            value='<c:out value="${LevelQueryDto.strModelNo}"/>'
                            ondblclick="code_CodeSelect(this,'modelNo','0,1','Y');"
                            onkeyup="code_CodeSelect(this,'modelNo','0,1','Y');"
                            onchange="code_CodeChange(this,'modelNo','0,1','Y');">
                    </td>
                    <td width="14%" class="page">
                        <input name="modelNameQuery" type="text" class="codename"
                            value='<c:out value="${LevelQueryDto.modelName}"/>' readonly>
                    </td>
                    <td width="10%" class="page">
                        <s:text name="archive.level" />：
                    </td>
                    <%-- 级别 --%>
                    <td width="10%" class="page">
                        <input name="nodeNoQuery" type="text" class="codecode" style="width: 70px;"
                            value='<c:out value="${LevelQueryDto.strNodeNo}"/>'
                            ondblclick="dbclickNodeNo(this, 'dbclick');"
                            onkeyup="dbclickNodeNo(this, 'keyup');"
                            onchange="dbclickNodeNo(this, 'change');">
                    </td>
                    <td width="14%" class="page">
                        <input name="nodeNameQuery" type="text" class="codename"
                            value='<c:out value="${LevelQueryDto.nodeName}"/>' readonly>
                    </td>
                    <td width="10%" class="page">
                        <s:text name="regist.prpLregist.riskCodeName" />：<%-- 险种 --%>
                    </td>
                    <td width="10%" class="page">
                        <input name="riskCodeQuery" type="text" class="codecode" style="width: 70px;"
                            value='<c:out value="${LevelQueryDto.riskCode}"/>'
                            ondblclick="code_CodeQuery(this,'riskCode','0,1','Y');"
                            onkeyup="code_CodeQuery(this,'riskCode','0,1','Y');"
                            onchange="code_CodeChange(this,'riskCode','0,1','Y');">
                    </td>
                    <td width="14%" class="page">
                        <input name="riskNameQuery" type="text" class="codename"
                            value='<c:out value="${LevelQueryDto.riskName}"/>' readonly>
                    </td>
                </tr>
            </table>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr align="center">
                    <td>
                        <input type="button" class="button" value="<s:text name='button.query.value'/>" onclick="doQuery();"><%-- 查 询 --%>
                        
                    </td>
                </tr>
            </table>
            <app:claimPlatFromCodeInput />
            &nbsp;
            <% 
              Page pageRecorde = (Page)request.getAttribute("page");
            %>
            <input type="hidden" name="pageNo" value="<%=(pageRecorde==null?0:pageRecorde.getCurrentPageNo())%>">
            <input type="hidden" name="rowsCount" value="<%=(pageRecorde==null?0:pageRecorde.getTotalCount())%>">
            <input type="hidden" name="rowsPerPage" value="<%=(pageRecorde==null?0:pageRecorde.getPageSize())%>">
            <table class="common" cellpadding="1" cellspacing="1" align="center" id=ResultTable>
                <tr>
                    <td colspan="7" align="center" class="top">
                        <s:text name="uwlevel.QueryResults" /><%-- 核赔人员查询结果 --%>
                    </td>
                </tr>
                <tr>
                    <td width="5%" class="top" style="text-align: left">&nbsp;</td>
                    <td width="12%" class="top">
                        <s:text name="uwlevel.NuclearClaims" /><%-- 核赔员 --%>
                    </td>
                    <td width="15%" class="top">
                        <s:text name="uwcondition.AuditDepartment" /><%-- 审核部门 --%>
                    </td>
                    <td width="25%" class="top">
                        <s:text name="regist.prpLregist.riskCodeName" /><%-- 险种 --%>
                    </td>
                    <td width="25%" class="top">
                        <s:text name="uwcondition.Template" /><%-- 模板 --%>
                    </td>
                    <td width="18%" class="top">
                        <s:text name="archive.level" /><%-- 级别 --%>
                    </td>
                    <td width="12%" class="top">
                        <s:text name="uwcondition.TypeAuditing" /><%-- 审核类型 --%>
                    </td>
                </tr>
                    <c:forEach items="${utiUwLevelOverview}" var="iterateDto" varStatus="stat">
                        <input type="hidden" name="utiUwLevelUserCode" value='<c:out value="${iterateDto.userCode}"/>'>
                        <input type="hidden" name="userName" value='<c:out value="${iterateDto.userName}"/>'>
                        <input type="hidden" name="utiUwLevelComCode" value='<c:out value="${iterateDto.comCode}"/>'>
                        <input type="hidden" name="comName" value='<c:out value="${iterateDto.comName}"/>'>
                        <input type="hidden" name="riskCode" value='<c:out value="${iterateDto.riskCode}"/>'>
                        <input type="hidden" name="utiUwLevelModelNo" value='<c:out value="${iterateDto.modelNo}"/>'>
                        <input type="hidden" name="modelName" value='<c:out value="${iterateDto.modelName}"/>'>
                        <input type="hidden" name="utiUwLevelNodeNo" value='<c:out value="${iterateDto.nodeNo}"/>'>
                        <input type="hidden" name="nodeName" value='<c:out value="${iterateDto.nodeName}"/>'>
                        <input type="hidden" name="utiUwLevelUwType" value='<c:out value="${iterateDto.uwType}"/>'>
                        <input type="hidden" name="uwTypeName" value='<c:out value="${iterateDto.uwTypeName}"/>'>
                        <input type="hidden" name="startDate" value='<c:out value="${iterateDto.startDate}"/>'>
                        <input type="hidden" name="endDate" value='<c:out value="${iterateDto.endDate}"/>'>
                        <input type="hidden" name="validStatus" value='<c:out value="${iterateDto.validStatus}"/>'>
                        <tr>
                            <td align="left" class="page">
                                <input type=radio name=checkboxSelect value="${stat.index}">
                            </td>
                            <td align="center" class="page">
                                <c:out value="${iterateDto.userCode}"/> - <c:out value="${iterateDto.userName}"/>
                            </td>
                            <td align="center" class="page">
                                <c:out value="${iterateDto.comCode}"/> - <c:out value="${iterateDto.comName}"/>
                            </td>
                            <td align="center" class="page" width="18%">
                                <c:out value="${iterateDto.riskCode}"/>
                            </td>
                            <td align="center" class="page">
                                <c:out value="${iterateDto.modelNo}"/> - <c:out value="${iterateDto.modelName}"/>
                            </td>
                            <td align="center" class="page">
                                <c:out value="${iterateDto.nodeNo}"/> - <c:out value="${iterateDto.nodeName}"/>
                            </td>
                            <td align="center" class="page">
                                <c:out value="${iterateDto.uwType}"/> - <c:out value="${iterateDto.uwTypeName}"/>
                            </td>
                        </tr>
                    </c:forEach>
                <tr>
                    <td colspan="7" align="center" class="page"><app:navigate objectName="page" display="false"/></td>
                </tr>
                <tr>
                    <td colspan="7" align="center" class="page">
                        <app:command objectName="UwLevel" action="update,delete" path="${ctx}" />
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
