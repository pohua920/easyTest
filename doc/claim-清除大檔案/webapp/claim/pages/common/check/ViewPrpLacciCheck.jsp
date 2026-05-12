<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/app.tld" prefix="app"%>
<html>
<head>
<title><s:text name="title.checkBeforeEdit.healthTable" /></title>
<%--意健险调查主表--%>
</head>
<link href="/claim/css/Standard.css" rel="stylesheet" type="text/css">
<table width="100%" height="100%" border="0" cellpadding="5" cellspacing="0">
	<tr>
		<td align="center" valign="top">
			<table border="0" cellpadding="5" cellspacing="1" class="common">
				<tr class=listtitle>
					<td colspan="4" align="center">
						<s:text name="check.healthTable" />
					</td>
					<%--意健险调查主表--%>
				</tr>
				<tr class=common>
					<td width="20%">
						<s:text name="db.prpLcheckExt.registNo" />：
					</td>
					<%--报案号码--%>
					<td width="30%">
						<bean:write name="prpLacciCheckDto" property="registNo" />
					</td>
					<td width="20%">
						<s:text name="check.numberInvest" />：
					</td>
					<%--调查次数--%>
					<td width="30%">
						<bean:write name="prpLacciCheckDto" property="times" />
					</td>
				</tr>
				<tr class=common>
					<td width="20%">
						<s:text name="db.prpLacciCheck.checkNo" />：
					</td>
					<%--调查号 --%>
					<td width="30%">
						<bean:write name="prpLacciCheckDto" property="checkNo" />
					</td>
					<td width="20%">
						<s:text name="check.node" />：
					</td>
					<%--发起节点--%>
					<td width="30%">
						<bean:write name="prpLacciCheckDto" property="certiType" />
					</td>
				</tr>
				<tr class=common>
					<td width="20%">
						<s:text name="check.nodeBusinessNo" />：
					</td>
					<%--发起节点的业务号码--%>
					<td width="30%">
						<bean:write name="prpLacciCheckDto" property="certiNo" />
					</td>
					<td width="20%">
						<s:text name="prpLdocCollect.riskCode" />：
					</td>
					<%--险种代码--%>
					<td width="30%">
						<bean:write name="prpLacciCheckDto" property="riskCode" />
					</td>
				</tr>
				<tr class=common>
					<td width="20%">
						<s:text name="db.view_loan.policyNo" />：
					</td>
					<%--保单号码--%>
					<td width="30%">
						<bean:write name="prpLacciCheckDto" property="policyNo" />
					</td>
					<td width="20%">
						<s:text name="check.investigationType" />：
					</td>
					<%--调查类型--%>
					<td width="30%">
						<bean:write name="prpLacciCheckDto" property="checkType" />
					</td>
				</tr>
				<tr class=common>
					<td width="20%">
						<s:text name="certainLoss.prpLacciCheck.prpLacciCheckCheckContext" />
					</td>
					<%--调查内容简要描述：--%>
					<td width="30%">
						<bean:write name="prpLacciCheckDto" property="checkContext" />
					</td>
					<td width="20%">
						<s:text name="certainLoss.prpLacciCheck.prpLacciCheckCheckObject" />:
					</td>
					<%--调查对象：--%>
					<td width="30%">
						<bean:write name="prpLacciCheckDto" property="checkObject" />
					</td>
				</tr>
				<tr class=common>
					<td width="20%">
						<s:text name="check.respondDescrib" />：
					</td>
					<%--调查对象描述--%>
					<td width="30%">
						<bean:write name="prpLacciCheckDto" property="checkObjectDesc" />
					</td>
					<td width="20%">
						<s:text name="certainLoss.prpLacciCheck.prpLacciCheckMethod" />:
					</td>
					<%--调查方式：--%>
					<td width="30%">
						<bean:write name="prpLacciCheckDto" property="checkNature" />
					</td>
				</tr>
				<tr class=common>
					<td width="20%">
						<s:text name="check.investigatStartDate" />：
					</td>
					<%--调查起始日期--%>
					<td width="30%">
						<bean:write name="prpLacciCheckDto" property="checkDate" />
					</td>
					<td width="20%">
						<s:text name="check.investigatStartDate" />：
					</td>
					<%--调查起始日期--%>
					<td width="30%">
						<bean:write name="prpLacciCheckDto" property="checkHour" />
					</td>
				</tr>
				<tr class=common>
					<td width="20%">
						<s:text name="check.investigatEndDate" />：
					</td>
					<%--调查结束日期--%>
					<td width="30%">
						<bean:write name="prpLacciCheckDto" property="checkEndDate" />
					</td>
					<td width="20%">
						<s:text name="check.investigatEndDate" />：
					</td>
					<%--调查结束日期--%>
					<td width="30%">
						<bean:write name="prpLacciCheckDto" property="checkEndHour" />
					</td>
				</tr>
				<tr class=common>
					<td width="20%">
						<s:text name="certainLoss.prpLacciCheck.prpLacciCheckCheckSite" />
					</td>
					<%--调查地点：--%>
					<td width="30%">
						<bean:write name="prpLacciCheckDto" property="checkSite" />
					</td>
					<td width="20%">
						<s:text name="check.causeAccidentCode" />：
					</td>
					<%--事故原因代码--%>
					<td width="30%">
						<bean:write name="prpLacciCheckDto" property="damageCode" />
					</td>
				</tr>
				<tr class=common>
					<td width="20%">
						<s:text name="check.causeAccident" />：
					</td>
					<%--事故原因说明--%>
					<td width="30%">
						<bean:write name="prpLacciCheckDto" property="damageName" />
					</td>
					<td width="20%">
						<s:text name="db.prpLcheck.damageTypeCode" />：
					</td>
					<%--事故类型代码--%>
					<td width="30%">
						<bean:write name="prpLacciCheckDto" property="damageTypeCode" />
					</td>
				</tr>
				<tr class=common>
					<td width="20%">
						<s:text name="db.prpLcheck.damageTypeName" />：
					</td>
					<%--事故类型说明--%>
					<td width="30%">
						<bean:write name="prpLacciCheckDto" property="damageTypeName" />
					</td>
					<td width="20%">
						<s:text name="check.investigatorCode" />：
					</td>
					<%--调查人代码--%>
					<td width="30%">
						<bean:write name="prpLacciCheckDto" property="checkerCode" />
					</td>
				</tr>
				<tr class=common>
					<td width="20%">
						<s:text name="check.reviewPesonlCode" />：
					</td>
					<%--审核人代码--%>
					<td width="30%">
						<bean:write name="prpLacciCheckDto" property="approverCode" />
					</td>
					<td width="20%">
						<s:text name="check.reviewData" />：
					</td>
					<%--审核日期--%>
					<td width="30%">
						<bean:write name="prpLacciCheckDto" property="approverDate" />
					</td>
				</tr>
				<tr class=common>
					<td width="20%">
						<s:text name="check.reviewStatu" />：
					</td>
					<%--审核状态--%>
					<td width="30%">
						<bean:write name="prpLacciCheckDto" property="approverStatus" />
					</td>
					<td width="20%">
						<s:text name="db.prpLcomponent.remark" />：
					</td>
					<%--备注--%>
					<td width="30%">
						<bean:write name="prpLacciCheckDto" property="remark" />
					</td>
				</tr>
			</table>
			&nbsp;
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr align="center">
					<td>
						<a href="#" onclick="javascript:history.back();"><img src="/claim/images/btnBack.gif" width="75" height="20" border="0"></a>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</form>
</body>
</html>
