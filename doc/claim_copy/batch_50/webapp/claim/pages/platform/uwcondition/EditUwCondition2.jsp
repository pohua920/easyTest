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
    <jsp:include page="/pages/platform/behaviors/MpcStyle.jsp"/>
</HEAD>
<app:claimPlatFromCodeInput/>
<BODY BGCOLOR="#D7E1F6" ONLOAD="oMPC.style.visibility='visible'" style="scroll: no; overflow: hidden;">
<form name="fm" action="" method="POST">
<input type="hidden" name="actionType1" value="<c:out value="${param.actionType}"/>">
<input type="hidden" name="nodeNo" value="<c:out value="${param.nodeNo}"/>">
<input type="hidden" name="comCode" value="<c:out value="${param.comCode}"/>">
    <div id="Layer1" style="position:absolute; width:70px; height:22px; z-index:1; left:550px; top:2px;">
        <table border="0" cellpadding="0" cellspacing="1" class="common">
            <tr><%-- ±£ ¥Ê --%>
                <td><input type="button" name="save" value="<s:text name='button.save.value'/>" class="button" onclick="doUpdate();"></td>
            </tr>
        </table>
    </div>
    <div id="Layer2" style="position:absolute; width:620px; height:450px; z-index:1; left:5px; top:30px;">
        <mpc:container ID="oMPC" STYLE="width:620px; height:450px; visibility:hidden;">
            <mpc:page ID="tab2" TABTITLE="" TABTEXT="»ÀÜT">
                <center>
                    <div style="width:610px; height:445px; overflow:auto;">
                        <jsp:include page="/pages/platform/uwcondition/EditUwConditionInclude2.jsp"/>
                    </div>
                </center>
            </mpc:page>
      </mpc:container>
    </div>
</form>
<script language="javascript">
     function prepareUpdate(actionType, nodeNo){
        fm.action = "/claim/processUwCondition.do?actionType=" + actionType +"&nodeNo=" + nodeNo;
        fm.submit();
     }
     function doUpdate(){
        if(checkUtiUwLevel() == false){
            return;
        }
        if(confirm("¥_åç“™É¶¥ÊÜ·£ø")){
            fm.action = "/claim/processUwCondition.do?actionType=updateUtiUwLevel";
            fm.submit();
        }
     }
</script>
</BODY>
</HTML>