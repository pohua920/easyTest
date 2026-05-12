<%@ page contentType="text/html; charset=GBK" %>
<%@ include file="/common/taglibs.jsp"%>
<html>
  <head>
    <%@include file="/common/i18njs.jsp"%>
    <%@include file="/common/meta_js.jsp"%>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
    <title><s:text name="title.archive.notEntityDataQueryResult"/></title><!-- 未归档实体资料查询结果 -->
    <!--通用函数-->
    <script src="${ctx}/claim/pages/common/archive/js/ArchiveEdit.js"></script>
	<script type="text/javascript">
	function prepareBatchSubmit() {
		var obj = document.getElementsByName("checkboxSelect");
		var objYN = false;
		if (obj != null) {;
			for (var i = 0; i < obj.length; i++) {
				if (obj[i].checked == true) {
					objYN = true;
				}
				break;
			}
		}
		if (objYN) {
			fm.action = "/claim/batchArchiveDeal.do";
			fm.method = "post";
			fm.submit();
		} else {
			alert("请选择记录！");
		}
	}
	</script>
  </head>
<body>
	<form name="fm" action="/claim/archiveBefore.do" method="post">
		<input type="hidden" name="editType" value="archive">
		<table class="common" cellpadding="5" cellspacing="1" align="center">
			<tr class="listtitle">
				<td colspan="8">
					<b><s:text name="title.archive.notEntityDataQueryResult" /></b>
				</td>
				<!-- 未归档实体资料查询结果 -->
			</tr>
			<tr class="listtitle">
				<td>
					<input type="checkbox" name="selectButton" onclick="boundCheckBox(this, fm.checkboxSelect);">
				</td>
				<td>
					<s:text name="check.claimNum" />
				</td>
				<!-- 赔案号 -->
				<td>
					<s:text name="prompt.queRegist.RegistNo" />
				</td>
				<!-- 报案号 -->
				<td>
					<s:text name="prompt.queRegist.PolicyNo" />
				</td>
				<!-- 保单号 -->
				<td>
					<s:text name="db.prpCmain.insuredName" />
				</td>
				<!-- 被保险人名称 -->
				<td>
					<s:text name="db.prpLclaim.endCaseDate" />
				</td>
				<!-- 结案日期 -->
				<td>
					<s:text name="compensate.compel.paymentAmount" />
				</td>
				<!-- 赔款金额 -->
				<td>
					<s:text name="db.prpDshortrate.validStatus" />
				</td>
				<!-- 状态 -->
			</tr>
			<logic:notEmpty name="prpLDocArchiveDto" property="archiveList">
				<logic:iterate id="archiveList1" name="prpLDocArchiveDto" property="archiveList">
					<tr class=common>
						<td>
							<input type="checkbox" name="checkboxSelect" value="<bean:write name="archiveList1" property="claimNo"/>">
						</td>
						<td>
							<bean:write name="archiveList1" property="claimNo" />
						</td>
						<td>
							<bean:write name="archiveList1" property="registNo" />
						</td>
						<td>
							<bean:write name="archiveList1" property="policyNo" />
						</td>
						<td>
							<bean:write name="archiveList1" property="insuredName" />
						</td>
						<td>
							<bean:write name="archiveList1" property="endCaseDate" />
						</td>
						<td>
							<bean:write name="archiveList1" property="sumDutyPaid" />
						</td>
						<td>
							<s:text name="archive.undocumented" />
							<!-- 未归档 -->
						</td>
					</tr>
				</logic:iterate>
			</logic:notEmpty>
			<tr>
				<td colspan="8">
					<table width="100%" class="common" align="center" cellpadding="0" cellspacing="0">
						<tr>
							<bean:define id="pageview" name="prpLDocArchiveDto" property="turnPageDto" />
							<%
								PrpLDocArchiveDto prpDocArchiveDto = (PrpLDocArchiveDto) request.getAttribute("prpLDocArchiveDto");
								int curPage = prpDocArchiveDto.getTurnPageDto().getPageNo();
							%>
							<%@include file="/common/pub/TurnOverPage.jsp"%>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		&nbsp;
		<table class="two">
			<tr>
				<td align="center">
					<input type="button" class="button" name="btn3" value="<s:text name="button.theArchive.value"/>" onclick="prepareBatchSubmit();">
					<!-- 归档 -->
				</td>
			</tr>
		</table>
	</form>
</body>
</html>