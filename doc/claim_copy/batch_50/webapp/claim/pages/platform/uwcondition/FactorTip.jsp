<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
    <head>
        <jsp:include page="/pages/platform/uwcondition/StaticJavascript.jsp" />
        <link href="${ctx}/pages/platform/css/Standard.css" rel="stylesheet" type="text/css">
    </head>
    <body onload="initPage();">
        <form name="fm" action="${ctx}/processUwFactor.do" method="post">
            <table class="common" cellpadding="5" cellspacing="1" align="center">
                <tr>
                    <td colspan="4" align="center" class="top">
                        <strong><s:text name="prompt.uwcondition.Tips" /></strong><%-- 双核因子赋值提示 --%>
                    </td>
                </tr>
                <tr>
                    <td width="15%" class="page"><s:text name="uwcondition.NameFactor" />：</td>
                    <%-- 因子名称 --%>
                    <td width="35%" class="page"><c:out value="${factorDto.factorName}"/></td>
                    <td width="15%" class="page"><s:text name="uwcondition.FactorProperty" />：</td>
                    <%-- 因子属性 --%>
                    <td width="35%" class="page">
                        <c:out value="${factorDto.factorAttr}"/> - <c:out value="${factorDto.factorAttrName}"/>
                    </td>
                </tr>
                <tr>
                    <td width="15%" class="page"><s:text name="uwcondition.AssignmentTips" />：<%-- 赋值提示 --%></td>
                    <td width="85%" class="page" colspan="3"><c:out value="${factorDto.valueDesc}"/></td>
                </tr>
                <tr>
                    <td width="15%" class="page"><s:text name="uwcondition.AssignmentExample" />：<%-- 赋值示例 --%></td>
                    <td width="35%" class="page"><c:out value="${factorDto.exampleValue}"/></td>
                    <td width="15%" class="page">&nbsp;</td>
                    <td width="35%" class="page">&nbsp;</td>
                </tr>
            </table>
        </form>
    </body>
</html>
