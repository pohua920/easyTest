<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tlds/claim-app.tld" prefix="app" %>
<HTML xmlns:mpc>
    <HEAD>
        <TITLE></TITLE>
        <jsp:include page="/pages/platform/uwcondition/StaticJavascript.jsp" />
        <link href="${ctx}/pages/platform/css/Standard.css" rel="stylesheet" type="text/css">
        <script src="${ctx}/pages/platform/uwcondition/js/uwcondition1.js"></script>
        <script src="${ctx}/pages/platform/uwcondition/js/uwcondition2.js"></script>
        <jsp:include page="/pages/platform/behaviors/MpcStyle.jsp" />
    </HEAD>
    <BODY BGCOLOR="#D7E1F6" ONLOAD="oMPC.style.visibility='visible'" style="scroll: no; overflow: hidden;">
        <form name="fm" action="" method="POST">
            <% int comboCount = 0;%>
            <c:forEach items="${requestScope.comboFactorList}" var="utiUwFactorDto">
                 <c:forEach items="${utiUwFactorDto.utiUwComboFactorList}" >
                     <%comboCount++;%>
                 </c:forEach>
            </c:forEach>
            <input type="hidden" name="actionType" value="<c:out value='${param.actionType}'/>">
            <div id="Layer1" style="position: absolute; width: 180px; height: 22px; z-index: 1; left: 550px; top: 2px;">
                <table border="0" cellpadding="0" cellspacing="1" class="newcommon">
                    <tr>
                        <td width="14%">
                            <input type="button" name="save" value="儲存" class="button" onclick="doInsert(<%=comboCount%>);">
                        </td>
                        <td>
                            <input type="button" name="btnBack" value="返回到结果列表" class="longbutton" onclick="backOverview();">
                        </td>
                    </tr>
                </table>
            </div>
            <div id="Layer2"
                style="position: absolute; width: 620px; height: 450px; z-index: 1; left: 5px; top: 30px;">
                <mpc:container ID="oMPC" STYLE="width:770px; height:450px; visibility:hidden;">
                    <c:if test="${requestScope.simpleCount=='1'}">
                        <mpc:page ID="tab3" TABTITLE="" TABTEXT="簡單因子"
                            STYLE="width:780px;">
                            <center>
                                <div style="width: 760px; height: 445px; overflow: auto;">
                                    <jsp:include page="/pages/platform/uwcondition/EditUwConditionInclude3.jsp" />
                                </div>
                            </center>
                        </mpc:page>
                    </c:if>
                    <c:if test="${requestScope.enumCount=='1'}">
                        <mpc:page ID="tab5" TABTITLE="" TABTEXT="枚舉因子"
                            STYLE="width:780px;">
                            <center>
                                <div style="width: 760px; height: 445px; overflow: auto;">
                                    <jsp:include page="/pages/platform/uwcondition/EditUwConditionInclude4.jsp" />
                                </div>
                            </center>
                        </mpc:page>
                    </c:if>
                    <c:if test="${requestScope.comboCount=='1'}">
                        <mpc:page ID="tab4" TABTITLE="" TABTEXT="組合因子"
                            STYLE="width:780px;">
                            <center>
                                <div style="width: 760px; height: 445px; overflow: auto;">
                                    <jsp:include page="/pages/platform/uwcondition/EditUwConditionInclude8.jsp" />
                                </div>
                            </center>
                        </mpc:page>
                    </c:if>
                </mpc:container>
            </div>
            <app:claimPlatFromCodeInput/>
        </form>
        <script language="javascript">
    function prepareUpdate(actionType, nodeNo, comCode){
        fm.action = "/claim/processUwCondition.do?actionType=" + actionType +"&nodeNo=" + nodeNo+"&comCode="+comCode;
        fm.submit();
    }
     function doInsert(comboCount){
            if(checkOtherValue()==false){
                return;
            }
            if(checkComboIsNull(comboCount)==false){
                return;
            }
            if(checkSimpleFactorValue() == false){
                return;
            }
            if(checkComboFactorValue() == false){
                return;
            }
            if(confirm("確實要儲存嗎？")){
                fm.action = "/claim/processUwCondition.do?actionType=insertUtiUwCondition";
                fm.submit();
            }
     }
    function backOverview(){
        if(confirm("確實要返回嗎？")){
            fm.action = "/claim/processUwCondition.do?actionType=queryContinue";
            fm.submit();
        }
    }
</script>
    </BODY>
</HTML>