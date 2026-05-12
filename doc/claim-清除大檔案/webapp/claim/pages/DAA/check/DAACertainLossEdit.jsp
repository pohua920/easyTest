
<script src="/claim/DAA/certainLoss/js/DAACertainLossRepairComponentEdit.js"></script>
<table class="common" align="center" width="100%">
	<tr>
		<td class="subformtitle" style="text-align: left;">
			<img style="cursor: hand;" src="/claim/images/butCollapseBlue.gif" name="CertainLossInsertImg" onclick="showPage(this,CertainLossInsert)">
			<s:text name="check.entryInfor" />
			<%-- 录入定损信息 --%>
			<br>
			<table class="common" align="center" id="CertainLossInsert" style="display: none">
				<tr>
					<td><%!int indexCertainLoss = 0;
						int repairFeeNo = 0;
						int componentNo = 0;
						String flag = null;%>
						<%
							flag = request.getParameter("flag");
							//System.out.println("----------定损核损的标志---11111s----" + flag);
						%>
						<%-- 定损主表的画面 --%>
						<%@include file="/DAA/check/DAACertainLossMainEdit.jsp"%>
						<%-- 1.定损环节过程的修理/换件清单页面 --%>
						<%@include file="/DAA/certainLoss/DAACertainLossRepairComponentEdit.jsp"%></td>
				</tr>
			</table>
		</td>
	</tr>
</table>