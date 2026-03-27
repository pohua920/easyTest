<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/i18njs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><s:text name="prompt.messages.sendInfor"/></title>
		<jsp:include page="/common/meta_css.jsp" />
		<jsp:include page="/common/meta_js.jsp" />
	    <!--Í¨ÓÃº¯Êý-->
	    <script src="/undwrt/common/js/Common.js">--</script>
	    <script src="/undwrt/common/js/Common_undwrt.js"></script>
	    <script src="/undwrt/common/js/CommonTaskDeal.js"></script>
	    <script src="/undwrt/common/js/My97DatePicker/WdatePicker.js"></script>
</head>
<body>
	<form name ="fm" method="post">
		<table class="common">
			<tr class=listtitle>
        		<%-- --%>
          		<td colspan="3" align="center">
          		<s:text name="prompt.messages.sendInfor"></s:text>
          		</td>
          	</tr>
			<tr>
				<td width="400px;"><s:text name="undwrt.CommonDealContent.operatorCome"/> <s:text name="undwrt.CommonDealContent.operatorCode"/> <s:text name="undwrt.CommonDealContent.operatorName"/> <s:text name="undwrt.CommonDealContent.operatorNodeNo"/>:</td>
				<td width="400px;">
                   <select name="operatorCode">
                   			<option value=""></option>
                           <s:iterator value="sendTaskVoList" id="sendTask">
                            <option value="<s:property value="#sendTask.comCode"/>*<s:property value="#sendTask.userCode"/>"><s:property value="#sendTask.comCode"/> <s:property value="#sendTask.userCode"/> <s:property value="#sendTask.userName"/> <s:property value="#sendTask.nodeNo"/></option>
                           </s:iterator>
                    </select>
                </td>
 			</tr>
 		</table>
 			<table class="common">
				<tr>
				<td class="button">
				<input class=button name="button1" type="button" value="<s:text name='prompt.ok'/>"  onclick="sendTaskTwo();"/></td>
				<td class="button">
				<input class=button name="button2" type="reset" value="<s:text name='prompt.reset'/>" /></td>
				<td class="button">
          		<%-- <input class="button" name="button3" type="button" value="<s:text name='undwrt.close'/>" onclick="window.close();"></td>
				--%>
				</tr>
			</table>
	</form>
</body>
</html>