<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<HTML xmlns:mpc>
    <HEAD>
        <TITLE></TITLE>
        <jsp:include page="/pages/platform/uwcondition/StaticJavascript.jsp" />
        <link href="${ctx}/pages/platform/css/Standard.css" rel="stylesheet" type="text/css">
        <script src="${ctx}/pages/platform/uwcondition/js/uwcondition1.js"></script>
        <script src="${ctx}/pages/platform/uwcondition/js/uwcondition2.js"></script>
        <jsp:include page="/pages/platform/behaviors/MpcStyle.jsp" />
    </HEAD>
    <app:claimPlatFromCodeInput/>
    <BODY BGCOLOR="#D7E1F6" ONLOAD="oMPC.style.visibility='visible'" style="scroll: no; overflow: hidden;">
        <form name="fm" action="" method="POST">
            <input type="hidden" name="actionType1" value="<c:out value="${param.actionType}"/>">
            <input type="hidden" name="nodeNo" value="<c:out value="${param.nodeNo}"/>">
            <div id="Layer1" style="position: absolute; width: 70px; height: 22px; z-index: 1; left: 550px; top: 2px;">
                <table border="0" cellpadding="0" cellspacing="1" class="common">
                    <tr>
                        <td><%-- 保 存 --%>
                            <input type="button" name="save" value="<s:text name='button.save.value'/>" class="button" onclick="doUpdate();">
                        </td>
                    </tr>
                </table>
            </div>
            <div id="Layer2" style="position: absolute; width: 620px; height: 450px; z-index: 1; left: 5px; top: 30px;">
                <mpc:container ID="oMPC" STYLE="width:620px; height:450px; visibility:hidden;">
                    <c:if test="${requestScope.simpleCount == '1'}">
                        <mpc:page ID="tab3" TABTITLE="" TABTEXT="簡單因子">
                            <center>
                                <div style="width: 610px; height: 445px; overflow: auto;">
                                    <jsp:include page="/pages/platform/uwcondition/EditUwConditionInclude3.jsp" />
                                </div>
                            </center>
                        </mpc:page>
                    </c:if>
                    <c:if test="${requestScope.enumCount == '1'}">
                        <mpc:page ID="tab5" TABTITLE="" TABTEXT="枚舉因子">
                            <center>
                                <div style="width: 610px; height: 445px; overflow: auto;">
                                    <jsp:include page="/pages/platform/uwcondition/EditUwConditionInclude4.jsp" />
                                </div>
                            </center>
                        </mpc:page>
                    </c:if>
                    <c:if test="${requestScope.comboCount == '1'}">
                        <mpc:page ID="tab4" TABTITLE="" TABTEXT="組合因子">
                            <center>
                                <div style="width: 610px; height: 445px; overflow: auto;">
                                    <jsp:include page="/pages/platform/uwcondition/EditUwConditionInclude8.jsp" />
                                </div>
                            </center>
                        </mpc:page>
                    </c:if>
                </mpc:container>
            </div>
        </form>
        <script language="javascript">
         function prepareUpdate(actionType, nodeNo){
                fm.action = "/claim/processUwCondition.do?actionType=" + actionType +"&nodeNo=" + nodeNo;
                fm.submit();
         }     
        </script>
    </BODY>
</HTML>