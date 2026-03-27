<%@ include file="/common/taglibs.jsp"%>
<table class="common" cellpadding="5" cellspacing="1">
	<tr>
		<td colspan=6 class="formtitle">
			<s:text name="specialCase.UnusualClaimInformation" />
		</td>
	</tr>
	<!--已有特殊赔案信息-->
	<tr>
		<td class="centertitle">
			<s:text name="regist.prpLregist.serialNo" />
		</td>
		<!--序号-->
		<td class="centertitle">
			<s:text name="regist.prpLregist.status" />
		</td>
		<!--状态-->
		<td class="centertitle">
			<s:text name="certify.type" />
		</td>
		<!--类型-->
		<td class="centertitle">
			<s:text name="specialCase.BusinessNumber" />
		</td>
		<!--业务号-->
		<td class="centertitle">
			<s:text name="specialCase.SpecialClaims" />
		</td>
		<!--特殊赔案号-->
		<td class="centertitle">
			<s:text name="db.prpCmain.policyNo" />
		</td>
		<!--保单号-->
	</tr>
	<s:set var="swfLogDto_count" value="0" scope="page" />
	<c:if test="${swfLogDto.swfLogList!=null}">
		<s:set var="swfLogDto_count" value="#attr.swfLogDto.swfLogList.size()" scope="page" />
		<c:forEach var="swfLogTemp" items="${swfLogDto.swfLogList}" varStatus="swfLogTemp_status">
			<c:if test="${swfLogTemp_status.index%2==0}">
				<tr class="listodd">
			</c:if>
			<c:if test="${swfLogTemp_status.index%2!=0}">
				<tr class="listeven">
			</c:if>
			<td align="center">${swfLogTemp_status.count }</td>
			<td align="center">
				<c:if test="${swfLogTemp.nodeStatus=='0'}">
					<s:text name="common.status.untreated" />
				</c:if>
				<!--未处理-->
				<c:if test="${swfLogTemp.nodeStatus=='2'}">
					<s:text name="common.status.intreating" />
				</c:if>
				<%--正处理  --%>
				<c:if test="${swfLogTemp.nodeStatus=='4'}">
					<s:text name="common.status.submited" />
				</c:if>
				<%--已提交  --%>
			</td>
			<td align="center">
				<c:if test="${swfLogTemp.typeFlag=='3'}">
					<s:text name="specialCase.Accommodation" />
				</c:if>
				<%-- 通融 --%>
				<c:if test="${swfLogTemp.typeFlag=='4'}">
					<s:text name="specialCase.Repay" />
				</c:if>
				<%-- 预付 --%>
				<c:if test="${swfLogTemp.typeFlag=='5'}">
					<s:text name="check.advance" />
				</c:if>
				<!--预赔-->
				<c:if test="${swfLogTemp.typeFlag=='7'}">
					<s:text name="specialCase.SalvageFees" />
				</c:if>
				<!--预-支付抢救费-->
				<c:if test="${swfLogTemp.typeFlag=='8'}">
					<s:text name="specialCase.RescueFee" />
				</c:if>
				<!--垫付抢救费-->
			</td>
			<td align="center">${swfLogTemp.keyIn}</td>
			<td align="center">${swfLogTemp.keyOut}</td>
			<td align="center">${swfLogTemp.policyNo}</td>
			</tr>
		</c:forEach>
	</c:if>
	<tr class="listtail">
		<td colspan="6">
			<s:text name="certainLoss.totalInquiries" />
			${swfLogDto_count }
			<s:text name="certainLoss.meetRecord" />
			<%-- 共查询出 --%>
			<%-- 条满足条件的记录 --%>
		</td>
	</tr>
</table>