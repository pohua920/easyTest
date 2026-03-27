<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
    <head>
        <title></title>
        <link href="${ctx}/pages/platform/css/Standard.css" rel="stylesheet" type="text/css">
        <link rel="stylesheet" type="text/css" href="${ctx}/pages/platform/behaviors/2k3OlBar.css">
    </head>
    <body style="margin: 0px; scroll: no; overflow: hidden;">
        <script language="javascript">
            function disableButton(field){
                fm.nodeIndex.value = field.value;
                var nodeIndex = parseInt(fm.nodeIndex.value);
                if(parseInt(fm.nodeNo[nodeIndex].value) == 1){
                    fm.n2.disabled = "true";
                } else {
                    fm.n2.disabled = null;
                }
            }
        </script>
        <form name="fm" action="" method="POST">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td style="width: 145px;">
                        <div style="border: solid 1px #000080; width: 340px; height: 458px; overflow: auto;">
                            <table class="common" cellpadding="3" cellspacing="1" align="center">
                                <tr>
                                    <td class="top">
                                        <strong><s:text name="archive.level" /></strong>
                                    </td>
                                    <%--级别--%>
                                </tr>
                                <INPUT type="hidden" name="nodeIndex" value="-10">
                                <c:forEach items="${swfNodeList}" var="nodeDto" varStatus="stat">
                                    <tr>
                                        <td class="page">
                                            <input type="radio" name="radioSelect" value="${stat.index}" onclick="disableButton(this);">
                                            <c:out value="${nodeDto.nodeName}"/> <INPUT type="hidden" name="nodeNo" value='<c:out value="${nodeDto.nodeNo}"/>'>
                                        </td>
                                    </tr>
                                </c:forEach>
                                <tr>
                                    <td class="page">
                                        <center>
                                            <input type="button" name="n2" value="人员" class="button3" onclick="prepareUpdate('insertUwLevel');">
                                        </center>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="top">
                                        <strong><s:text name="regist.prpLregist.registMain" /></strong><%--基本信息--%>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="page">
                                        <s:text name="uwcondition.TypeAuditing" />：<c:out value="${utiUwLevelDto.uwTypeName}" /><%--审核类型--%>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="page">
                                        <s:text name="uwcondition.AuditDepartment" />：<br /><%--审核部门--%>
                                        <c:out value="${utiUwLevelDto.comCode}" /> - <c:out value="${utiUwLevelDto.comName}" />
                                    </td>
                                    <INPUT type="hidden" name="comCode" value='<c:out value="${utiUwLevelDto.comCode}"/>'>
                                    
                                </tr>
                                <tr>
                                    <td class="page">
                                        <s:text name="regist.prpLregist.riskCodeName" />：<br />
                                        <textarea class="readonlya" name="riskCode"><c:out value="${utiUwLevelDto.riskCode}" /></textarea>
                                    </td>
                                    <%--险种--%>
                                </tr>
                                <tr>
                                    <td class="page">
                                        <%--模板--%><s:text name="uwcondition.Template" />：<br /> <c:out value="${utiUwLevelDto.modelName}" />
                                        <INPUT type="hidden" name="modelNo" value='<c:out value="${utiUwLevelDto.modelNo}"/>'>
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </td>
                    <td rowspan="2">
                        <div style="border: solid 1px #000080; width: 430px; height: 485px;">
                            <iframe src='${ctx}/pages/platform/uwlevel/InsertUwLevelRight.jsp' id="LevelRight" name="LevelRight" width="230px;" height="485px;"></iframe>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td style="width: 145px;">
                        <div style="height: 30px; width: 140px;">
                            <center>
                                <table>
                                    <tr>
                                        <td>
                                            <input type="button" name="btnBack" value="<s:text name='button.returnToPage.value'/>" class="longbutton" onclick="backOverview();">
                                        </td>
                                        <%--返回到新增页面--%>
                                    </tr>
                                </table>
                            </center>
                        </div>
                    </td>
                </tr>
            </table>
        </form>
        <script language="javascript">
            function prepareUpdate(actionType){
                var nodeIndex = parseInt(fm.nodeIndex.value);
                if(nodeIndex != -10){
                    var nodeNo = fm.nodeNo[nodeIndex].value;
                    var modelNo = fm.modelNo.value;
                    document.LevelRight.prepareUpdate(actionType,modelNo,nodeNo);
                }
            }
            function backOverview(){
                    fm.action = "/claim/processUwLevel.do?actionType=prepareInsert";
                    fm.submit();
            }
        </script>
    </body>
</html>