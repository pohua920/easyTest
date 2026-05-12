<!--
****************************************************************************
* DESC       ：登记权益转让及追偿保存页面
* AUTHOR     ：LiuXing
* CREATEDATE ：2006-07-25
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
-->
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%@include file="/common/i18njs.jsp"%>
<html xmlns:mpc>
<head>
<title>追償訊息頁面</title>
<!-- 页面样式  -->
<link rel="stylesheet" type="text/css" href="${ctx}/css/Standard.css">
<%@ include file="/common/meta_js.jsp"%>
<!-- 标签页样式 -->
<jsp:include page="/behaviors/MpcStyle.jsp" />
<!-- 时间控件 -->
<script src="${ctx}/pages/common/replevy/js/replevyEditNew.js"></script>
<script type="text/javascript">
    //mpc调整
    $(function() {
        initWindow();
        $(window).resize(function() {
            initWindow();
        });
    })
    function initColor(){
    	$("#Replevy").find(":input[name='prpLlossPreSumloss']").css({color:"#FF0000"});
    }
</script>
</head>
<c:set var="oldRegistLastAccessedTime" value="" scope="session" />
<s:if test="#parameters.editType[0]=='SHOW'||#parameters.editType[0]=='DELETE'||#parameters.editType[0]=='UNDWRT'">
    <body class="interface" onload="initPage();initPayNTD();readonlyAllInput();initButton();initColor();oMPC.style.visibility='visible'">
</s:if>
<s:else>
    <body class="interface" onload="initPage();initPayNTD();calSumPaidAll();oMPC.style.visibility='visible'">
</s:else>
<DIV id="mainLayer" class="mainLayer">
    <form name="fm" method="post" action="${ctx}/replevySave.do" autocomplete="off">
    	<s:token></s:token>
        <input type="hidden" name="editType" value="${param.editType}">
        <TABLE id="btnTable" cellpadding="0" cellspacing="0" border="0">
            <TR>
                <td align="left">
                    <!-- //mantis：CLM0082 ，處理人員：BK007  蘇哲，需求單編號：CLM0082.追償作業加入賠案處裡紀錄 -start -->
                    <input type="button" class="bigbutton" name="prpLmessageSave" value="<s:text name='button.claimsProcessingRecords.value' />" onclick="openWinSave('${prpLcompensate.claimNo}','${prpLcompensate.policyNo}','${prpLcompensate.riskCode}','replevy','${prpLcompensate.claimNo}')">
                    <!-- //mantis：CLM0082 ，處理人員：BK007  蘇哲，需求單編號：CLM0082.追償作業加入賠案處裡紀錄 -end -->
                    <%--生成索赔清单 --%>
                    <input type="button" name="buttonCertifyDirect" class="bigbutton" value="<s:text name="button.becomePayList.value" />" onClick="doCertifyDirect('${prpLcompensate.registNo}','replevy')">
                    <%--单证上传 --%>
                    <input type="button" name="eCertify" class="bigbutton" value="<s:text name="button.checkedUpload.value" />"
                        onClick="openCertify('certifyFinishQueryList','prpLcertifyCertifyNo','${prpLcompensate.registNo}','replevy');">
                </td>
            </tr>
        </table>
        <mpc:container ID="oMPC">
            <mpc:page ID="tabMain" TABTITLE="<s:text name="claim.RecoveryRegistMain" />" TABTEXT="<s:text name="claim.RecoveryRegistMain" />">
                <%--追偿基本信息 --%>
                <CENTER>
                    <DIV name="tabMain" class="tabMain">
                        <!---追偿主信息 --->
                        <%@include file="/pages/common/replevy/ReplevyMainHeadEdit.jsp"%>
                        <!---追偿文字 --->
                        <%@include file="/pages/common/replevy/ReplevyText.jsp"%>
                        <!---审批处理意见 --->
                        <%@include file="/pages/common/replevy/ReplevyOpinion.jsp"%>
                    </DIV>
                </CENTER>
            </mpc:page>
            <mpc:page ID="tabMain" TABTITLE="<s:text name="claim.RecoveryInfo" />" TABTEXT="<s:text name="claim.RecoveryInfo" />">
                <%--追偿信息 --%>
                <CENTER>
                    <DIV name="tabMain" class="tabMain">
                        <!---追偿信息 --->
                        <%@include file="/pages/common/replevy/ReplevyMessage.jsp"%>
                        <!---追偿费用 --->
                        <%@include file="/pages/common/replevy/ReplevyCharge.jsp"%>
                        <!-- 支付帳户信息 -->
                        <%@include file="/pages/common/replevy/EditPrpdpaymentaccountPage.jsp"%>
                    </DIV>
                </CENTER>
            </mpc:page>
        </mpc:container>
        <TABLE id="btnCommon" class="common">
            <TR>
                <TD align="center">
                    <!--- 追偿保存 --->
                    <%@include file="/pages/common/replevy/ReplevySave.jsp"%>
                </td>
            </tr>
        </TABLE>
    </form>
</DIV>
</body>
</html>