<%--
****************************************************************************
* DESC       ：调阅申请操作页面
* AUTHOR     ：liuwei
* CREATEDATE ：2010-12-31
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK" %>
<%@ include file="/common/taglibs.jsp"%>
<%@include file="/common/i18njs.jsp"%>
<%@include file="/common/meta_css.jsp"%>
<%@include file="/common/meta_js.jsp"%>

<html:html locale="true">
<head>
    <title><s:text name="archive.readTheApplication"/></title><!-- 调阅申请 -->
    <%-- 页面样式  --%>
    <link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
    <script type="text/javascript">
    function submitForm() {
    	var obj = fm.remark.value;
    	var length = obj.length;
    	if (length > 200) {
    		alert("说明栏位不能超过200个字！");
    		return;
    	}
    	fm.buttonSave.disabled = "disabled";
    	fm.submit();
    }
    </script>
</head>

<body class="interface" onload="initPage();">
    <form name=fm action="/claim/archive/archiveApply.do" method="post" onsubmit="return validateForm(this);">
        <table class="common" cellpadding="5" cellspacing="1">
  	        <tr>
  	  		    <td class=formtitle colspan="4"><s:text name="archive.readTheApplication"/></td><!-- 调阅申请 -->
  	        </tr>
            <tr>
                <td class="title"><s:text name="check.claimNum"/>:</td><!-- 赔案号  -->
                <td class="input">
                    <input name="claimNo" class="readonly" readonly value="${prpLDocArchive.claimNo}">
                </td>
                <td class="title"><s:text name="prompt.queRegist.PolicyNo"/></td><!-- 保单号： -->
                <td class="input">
                    <input name="policyNo" class="readonly" readonly value="${prpLDocArchive.policyNo}">
                </td>
            </tr>
            <tr>
                <td class="title"><s:text name="db.prpCmain.insuredName"/>:</td><!-- 被保险人名称： -->
                <td class="input">
                    <input name="insuredName" class="readonly" readonly value="${prpLDocArchive.insuredName}">
                </td>
                <td class="title"><s:text name="db.prpLclaim.endCaseDate"/></td><!-- 结案日期： -->
                <td class="input">
                    <%-- <input name="endCaseDate" class="readonly" readonly value="${prpLDocArchive.endCaseDate}">--%>
                    <rc:rcDate name="endCaseDate" class="readonly" readonly="true" wdatePicker="false"  style="width:80px" value="${prpLDocArchive.endCaseDate}" /> 
                </td>
            </tr>
            <tr>
                <td class="title"><s:text name="compensate.compel.paymentAmount"/></td><!-- 赔款金额： -->
                <td class="input"> 
                    <input name="sumDutyPaid" class="readonly" readonly value="${prpLDocArchive.sumDutyPaid}">
                </td>
                <td class="title"><s:text name="archive.applyReadReason"/>：</td><!-- 申请调阅事由 -->
                <td class="input">
                    <select name="applyReason" style="width: 80%">
                        <option value="1" selected="selected"><s:text name="archive.reopenClaim"/></option><!-- 重开赔案 -->
                        <option value="2"><s:text name="archive.copyClaimDocuments"/></option><!-- 查阅赔案内容 -->
                        <option value="3"><s:text name="archive.copyClaimDocumentsFiles"/></option><!-- 复印赔案文件 -->
                        <option value="4"><s:text name="check.other"/></option><!-- 其它 -->
                    </select>
                </td>
            </tr>
            <tr>
                <td class="title"><s:text name="archive.estimatedReturnTime"/></td><!-- 预计归还时间： -->
                <td class="input" colspan="3">
                    <input type="radio" name="estimatePeriod" value="1" checked="checked"><s:text name="archive.oneWeek"/><!-- 一周 -->
                    <input type="radio" name="estimatePeriod" value="2"><s:text name="archive.oneMonth"/><!-- 一月 -->
                    <input type="radio" name="estimatePeriod" value="3"><s:text name="archive.oneQuarter"/><!-- 一季 -->
                </td>
            </tr>
            <tr>
                <td class="title" colspan=4><s:text name="certify.instructe"/>:</td><!-- 说明： -->
            </tr>
            <tr>
                <td class="input" colspan="4" align="center">
                    <textarea name="remark" wrap="hard" rows="15" cols="80" class="common"></textarea>
                </td>
            </tr>
        </table>
        <table class="common" align="center">
            <tr>
                <td class="button" >
                    <input type="submit" name="buttonSave" value=" <s:text name="button.apply.value"/>" class="button" onclick="submitForm();"><!-- 申 请  -->
                </td>
                <td class="button" >
                    <input type="reset" name="buttonCancel" value=" <s:text name="button.cancel.value"/>" class="button"><!-- 取 消  -->
                </td>
            </tr>
        </table>
        <!-- 隐藏域 -->
        <input type="hidden" name="editType" value="applySave">
        <input type="hidden" name="serialNo" value="${serialNo}">
        <input type="hidden" name="status" value="${prpLDocArchive.status}">
        <input type="hidden" name="sumDutyPaid" value="${prpLDocArchive.sumDutyPaid}">
    </form>
</body>
<!--这个函数是调动所能用到的通用js的过程，一般包括最常用的js的函数声明都在meta_js.jsp中-->
</html:html>
