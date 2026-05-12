<%@page import="com.sinosoft.claim.dto.domain.*"%>
<%@page import="com.sinosoft.claim.dto.custom.*"%>
<%
	
%>
<img style="cursor: hand;" src="/claim/images/butCollapseBlue.gif" name="CommerceImg" onclick="showPage(this,spanCommerce);">
<s:text name="compensate.dubang.compInsurParagA" />
<br>
<%-- 商业三者综合保险(A款) --%>
<span id="spanCommerce" style="">
	<table class="common" id="PersonLossCareFee" cellspacing="1" cellpadding="0">
		<thead>
			<tr>
				<td class="centertitle" style="width: 9%" align="center">
					<s:text name="compensate.dubang.project" />
				</td>
				<%-- 项目 --%>
				<td class="centertitle" style="width: 8%" align="center">
					<s:text name="compensate.dubang.totalLoss" />
				</td>
				<%-- 总损失 --%>
				<td class="centertitle" style="width: 9%" align="center">
					<s:text name="compensate.dubang.strongThree" />
				</td>
				<%-- 强三限额 --%>
				<td class="centertitle" style="width: 9%" align="center">
					<s:text name="compensate.dubang.superStrongThree" />
				</td>
				<%-- 超强三金额 --%>
				<td class="centertitle" style="width: 10%" align="center">
					<s:text name="compensate.dubang.accidentLiabProport" />
				</td>
				<%-- 事故责任比例 --%>
				<td class="centertitle" style="width: 9%" align="center">
					<s:text name="compensate.compel.compensateShall" />
				</td>
				<%-- 核定赔偿金 --%>
				<td class="centertitle" style="width: 9%" align="center">
					<s:text name="db.prpLmedicine.indemnityLimit" />
				</td>
				<%-- 赔偿限额 --%>
				<td class="centertitle" style="width: 9%" align="center">
					<s:text name="compensate.dubang.accidFranch" />
				</td>
				<%-- 事故免赔率 --%>
				<td class="centertitle" style="width: 9%" align="center">
					<s:text name="compensate.AbsFranchise" />
				</td>
				<%-- 绝对免赔率 --%>
				<td class="centertitle" style="width: 9%" align="center">
					<s:text name="db.prpLpersonloss.sumRealpay" />
				</td>
				<%-- 实赔金额 --%>
				<td class="centertitle" style="width: 10%" align="center">
					<s:text name="compensate.dubang.totalSumRealpay" />
				</td>
				<%-- 总实赔金额 --%>
			</tr>
		</thead>
		<tbody>
			<tr>
				<%
					String[] titles = { "wealth", "medical", "diedeformity" };
					int row = 11, column = 3;
					String Kindcode = "A";
					if (Kindcode.equals("A")) {

						for (int j = 0; j < column; j++) {
				%>
			
			<tr>
				<%
					for (int i = 0; i < row; i++) {
				%>
				<%
					if ((i > 3) && j > 0)
									continue;
								if ((i > 3)) {
				%>
				<td class="input" rowspan="3" style="width: 9%" align="center">
					<input type="text" name="<%=titles[j]%>" class="readonly" align="center" readonly style="width: 100%">
					<%
						} else {
					%>
				
				<td class="input" style="width: 9%" align="center">
					<input type="text" name="<%=titles[j]%>" class="readonly" align="center" readonly style="width: 100%">
					<%
						}
					%>
				</td>
				<%
					}
				%>
			</tr>
			<%
				}
			%>
			<%
				} else {

					for (int j = 0; j < column; j++) {
			%>
			<tr>
				<%
					for (int i = 0; i < row; i++) {
				%>
				<%
					if ((i == 5 || i == 10) && j > 0)
									continue;
								if ((i == 5 || i == 10)) {
				%>
				<td class="input" rowspan="3" style="width: 9%" align="center">
					<input type="text" name="<%=titles[j]%>" class="readonly" align="center" readonly style="width: 100%">
					<%
						} else {
					%>
				
				<td class="input" style="width: 9%" align="center">
					<input type="text" name="<%=titles[j]%>" class="readonly" align="center" readonly style="width: 100%">
					<%
						}
					%>
				</td>
				<%
					}
				%>
			</tr>
			<%
				}
			%>
			<%
				}
			%>
		</tbody>
	</table>
</span>
