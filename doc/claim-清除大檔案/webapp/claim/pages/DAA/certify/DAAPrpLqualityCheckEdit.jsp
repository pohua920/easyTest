<%--
****************************************************************************
* DESC       ：显示质量评审内容信息
* AUTHOR     ：中科软
* CREATEDATE ：2013-03-24
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<table class="common" align="center" cellpadding="5" cellspacing="1" style="display: none">
	<tr>
		<td class="common" style="text-align: left;">
			<img style="cursor: hand;" src="/claim/images/butExpandBlue.gif" name="QualityCheckImg" onclick="showPage(this,QualityCheck)">
			<s:text name="certify.workQuality" />
			<%-- 工作质量审核信息 --%>
			<br>
			<table class="common" cellpadding="5" cellspacing="1" id="QualityCheck" style="display:">
				<tbody>
					<!-- 设置零时变量，intTemp记录条数信息，forEach有if判断，不能使用forEach的status -->
					<c:set var="intTemp" value="0" scope="page"></c:set>
					<c:forEach items="${requestScope.qualityCheckList}" var="prpDCodeDto">
						<!--原因：车险要屏蔽调CODECODE为1010的信息-->
						<c:if test="${prpDCodeDto.id.codeCode!='1010'}">
							<c:set var="intTemp" value="${intTemp+1}" scope="page"></c:set>
							<tr <c:if test="${intTemp%2==0}">class="listodd"</c:if> <c:if test="${intTemp%2!=0}">class="listeven"</c:if>>
								<td align="left" style="width: 50%" colspan=3>
									${intTemp }、<${prpDCodeDto.codeCName}/>
									<input type="radio" name="VisitBackQue${intTemp }" value="1">
									<s:text name="certainLoss.thirdCarLoss.yes" />
									<%-- 是 --%>
									<input type="radio" name="VisitBackQue${intTemp }" value="0">
									<s:text name="certainLoss.thirdCarLoss.no" />
									<%-- 否 --%>
									<input type="radio" name="VisitBackQue${intTemp }" value="2">
									<s:text name="certainLoss.thirdCarLoss.uncertainty" />
									<%-- 不确定 --%>
									<input type="hidden" name="txtQuestionCode${intTemp }" value="${prpDCodeDto.id.codeCode}">
									<input type="hidden" name="txtQuestionName${intTemp }" value="${prpDCodeDto.codeCName}">
								</td>
								<td>
									<input type="text" name="txtQuestionRemark${intTemp }" class="common" maxlength="255">
								</td>
							</tr>
						</c:if>
					</c:forEach>
					<input type="hidden" name="txtRecordNum" value="${intTemp}">
					<input type="hidden" name="qualityCheckType" value="certi">
				</tbody>
			</table>
		</td>
	</tr>
</table>
