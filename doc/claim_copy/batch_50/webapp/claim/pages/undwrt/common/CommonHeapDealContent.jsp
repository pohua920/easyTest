<!--***************************************************************************
mantis：CLM0241，處理人員： DP0713 ，需求單編號：強制任意批次核賠功能新增(view)
****************************************************************************-->
<%@ page contentType="text/html; charset=GBK"%>
<!-- 滚动条样式定义 -->
<%@ include file="/pages/undwrt/common/CommonStyle.html"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta_js.jsp"%>
<c:set var="handTitle" value="核賠" scope="page" />
<c:choose>
	<c:when test="${param.EditType=='deal'}">
		<c:set var="editTitle" value="處理" scope="page" />
	</c:when>
	<c:otherwise>
		<c:set var="editTitle" value="查詢" scope="page" />
	</c:otherwise>
</c:choose>
<html>
<head>
<title>核賠${pageScope.handTitle}<s:text name="title.undwrtBeforeEdit.Task" /></title>
<%-- 任務 --%>
<link href="${ctx }/pages/undwrt/css/KMessageBox.css" rel="stylesheet" rev="stylesheet" type="text/css" />
<!--通用函数-->
<script type="text/javascript" src="${ctx }/pages/undwrt/common/js/prototype.js"></script>
<script type="text/javascript" src="${ctx }/pages/undwrt/common/js/KMessageBox.js"></script>
<!--通用任務处理函数-->
<script src="${ctx }/pages/undwrt/common/js/CommonTaskDeal.js"></script>
<script src="${ctx }/pages/undwrt/common/js/WfLogQuery.js"></script>
<script type="text/javascript">
</script>
</head>
<link rel="stylesheet" type="text/css" href="${ctx}/pages/undwrt/css/Standard.css">
<body onload="">
    <form name="fm" method="post">
    	<c:if test="${param.EditType=='deal'}">
    		<s:token></s:token>
    	</c:if>
        <!--隐含域，数据提交-->
        <input type="hidden" name="DealType">
        <input type="hidden" name="EditType" value="${param.EditType}">
	        <input type="hidden" name="riskCode" value='${requestScope.riskCode}'>
	        <input type="hidden" name="payCodeType" value='${requestScope.payCodeType}'>
	        <input type="hidden" name="uniformNo" value='${requestScope.uniformNo}'>
	        <input type="hidden" name="flowInTime1" value='${requestScope.flowInTime1}'>
	        <input type="hidden" name="flowInTime2" value='${requestScope.flowInTime2}'>
	        <input type="hidden" name="choseNodeStatus" value='${requestScope.choseNodeStatus}'>
	        <input type="hidden" name="paymentKind" value='${requestScope.paymentKind}'>
        <input type="hidden" name="selectNodeNo" value='${swfPath.endNodeNo}'>
        <input type="hidden" name="selectNodeName" value='${swfPath.endNodeName}'>
        <input type="hidden" name="wfLogFlowIdArray" value='${requestScope.wfLogFlowIdArray}'>
		<input type="hidden" name="compNoAry" value="${requestScope.compNoAry}">
		<input type="hidden" name="content" value="${requestScope.content}">
		<input type="hidden" name="contentHid" value="${requestScope.contentHid}">
        <!-- 增加核心地址 -->
        <input type="hidden" name="prpallUrl" value="${prpallUrl}">

        <table class="common" cellpadding="5" cellspacing="1" align="center">
            <tr class=listtitle>
                <td colspan="4">
                    ${pageScope.editTitle}${pageScope.handTitle}<s:text name="task" /><%--處理/查詢 核賠任務 --%>
                </td>
                <%-- 任務 --%>
            </tr>
            <tr>
                <c:if test="${param.HandType=='22'}">
                    <input type="hidden" name="ClaimNo" value='${prpLclaim.claimNo}'>
                    <input type="hidden" name="RegistNo" value='${prpLclaim.registNo}'>
                    <input type="hidden" name="PolicyNo" value='${prpLclaim.policyNo}'>
                </c:if>
                <c:if test="${param.HandType=='22'&& wfLog.riskCategory=='D'}">
                    <input type="button" class="longbutton" name="claimInfo" value="<s:text name='button.AccidentInformation.value'/>" <%-- 历次出险信息 --%>
                         onclick="buttonOnClick('perilInfoShow','${prpLclaim.policyNo}','${prpLclaim.registNo}');">
                </c:if>
            </tr>
            <br>
            <tr>
                <td class=title4>
                    <s:text name="check.proDepartment" />：<%-- 处理部门 --%>
                </td>
                <td class=input4>
                    <input readonly class=readonly type="text" name="DeptCode" value="${deptName}">
                </td>
                <td class=title4><s:text name="undwrt.SubmissionTime" />：<%-- 提交时间 --%></td>
                <td class=title4>
                    ${systemTime}
                </td>
                <input type="hidden" name="DefaultFlag" value="1">
            </tr>
            <%@include file="/pages/undwrt/common/CommonHeapDangerUnits.jsp"%><%-- 保單摘要和賠付摘要訊息  --%>
            <tr class="listtitle">
                <td colspan="9">
                    <s:text name="undwrt.ApprovalInformation" /><%-- 审批信息 --%>
                </td>
            </tr>
            <tr>
                <td class=title4>
                    <s:text name="undwrt.SignedComments" /><%-- 签署审批意见 --%>
                </td>
                
                <td class=input4><%--textarea 不要换行，否则内容里面会有空格 --%>
                    <textarea class=big wrap="soft" name="HandleText"  ><c:forEach items="${requestScope.notionContent}" var="content">${content.handleText }</c:forEach></textarea>
                </td>
                <td class=title4>
                    <s:text name="undwrt.ApprovalPhrases" /><%-- 审批片语 --%>
                </td>
                <td class=input4>
                    <select class=common name="notion" onchange="changeNotion1(this)" >
                        <option value="">-----<s:text name="undwrt.PleaseSelect" />-----</option>
                    <c:forEach items="${requestScope.notionCode}" var="notion_Code">
                        <option value="${notion_Code.codeCName}">${notion_Code.codeCName}</option>
                    </c:forEach>
                    </select>
                </td>
            </tr>
        </table>
        <table class=two>
            <tr>
                <td class="button"><%-- 审核通过 --%>
                   	<Input type="button" class="button" name="passBtn" value="<s:text name='button.checkPass.value'/>" onclick="submitHeapPass(this);"
                   		<c:if test="${requestScope.content!=''}">disabled</c:if> >
                </td>
                <td class="button"><%-- 放棄任務 --%>
                    <input type="button" class="button" name="submitJunior" value="放棄任務" onclick="undoHeapTask(this);">
                </td>
            </tr>
        </table>
    </form>
</body>
</html>
