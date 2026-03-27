
<script src="/claim/DAA/certainLoss/js/DAACertainLossRepairComponentEdit.js"></script>
<table class="common" cellpadding="5" cellspacing="1">
	<tr>
		<td class="common" style="text-align: left;">
			<img style="cursor: hand;" src="/claim/images/butCollapseBlue.gif" name="CheckInsertImg" onclick="showPage(this,CheckInsert)">
			<s:text name="certainLoss.inputInformation" />
			<!--录入查勘信息-->
			<br>
			<table class="common" cellpadding="5" cellspacing="1" id="CheckInsert" style="display: none">
				<tr class=common>
					<td>
						<%-- 1.查勘/代查勘主信息 --%>
						<%@include file="/DAA/certainLoss/DAACheckMainEdit.jsp"%>
						<%-- 2.查勘/代查勘扩展 --%>
						<%@include file="/DAA/check/DAACheckExtEdit.jsp"%>
						<%-- 3.涉案车辆 --%>
						<%@include file="/DAA/claim/DAAClaimThirdPartyEdit.jsp"%>
						<%--  财产损失部位信息 --%>
						<%@include file="/DAA/regist/DAARegistThirdPropEdit.jsp"%>
						<%-- 4.损失金额信息 --%>
						<%@include file="/DAA/check/DAACheckLossEdit.jsp"%>
						<%-- 4.1 人伤跟踪信息 --%>
						<%@include file="/DAA/claim/DAAClaimPersonTraceEdit.jsp"%>
						<%-- 5.驾驶员信息 --%>
						<%@include file="/DAA/claim/DAAClaimDriverEdit.jsp"%>
						<%-- 6.查勘备注信息 --%>
						<%@include file="/DAA/regist/DAARegistTextEdit.jsp"%>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>