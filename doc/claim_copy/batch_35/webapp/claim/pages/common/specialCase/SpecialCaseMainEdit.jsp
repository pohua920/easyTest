<%@ include file="/common/taglibs.jsp"%>
<input type="hidden" name="configCode" class="common" value="">
<input type="checkbox" name="claimNoBox" style="display: none" class="common" value="">
<input type="hidden" name="ClaimNo" class="common" value="">
<input type="hidden" name="claimNoFlag" class="common" value="">
<input type="hidden" name="swfLogFlowID" class="common" value="${param.swfLogFlowID}">
<input type="hidden" name="swfLogLogNo" class="common" value="${param.swfLogLogNo}">
<input type="hidden" name="nodeStatus" class="common" value="${param.status}">
<table border="0" align="center" cellpadding="5" cellspacing="1" class="common">
	<tr>
		<td class="formtitle" colspan="6">
			<s:text name="specialCase.SpecialProcessing" />
			<!--特殊赔案申请信息处理-->
		</td>
	</tr>
	<tr>
		<td class="left">
			<s:text name="db.prpLclaim.claimNo" />
			：
		</td>
		<c:if test="${prpLclaimList!=null}">
			<td class="right">
				<input type="hidden" name="haveClaimNo" class="common" value="">
				<c:forEach var="prpLclaimTemp" items="${prpLclaimList}" varStatus="prpLclaim_status">
					<input type="radio" name="claimNoBox" title="賠案號碼" value="${prpLclaim_status.count }" onblur="checkPrepay(this);getClaimNo()">${prpLclaimTemp.claimNo}
			<input type="hidden" name="ClaimNo" class="common" value="${prpLclaimTemp.claimNo}">
					<input type="hidden" name="policyNo" value="${prpLclaimTemp.policyNo }">
					<input type="hidden" name="riskCode" value="${param.riskCode}">
					<input type="hidden" name="claimNoFlag" class="common" value="0">
					<input type="hidden" name="configCode" class="common" value="${prpLclaimTemp.configCode}">
				</c:forEach>
			</td>
			<td class="left">
				<s:text name="db.prpCmain.policyNo" />
				：
				<!--特殊赔案申请信息处理-->
			</td>
			<td class="right">
				<c:forEach var="prpLclaimTemp" items="${prpLclaimList}">
			${prpLclaimTemp.policyNo }
			</c:forEach>
			</td>
		</c:if>
		<input type="hidden" name="prpLclaimNo">
		<td class="left">
			<s:text name="db.prpLclaim.registNo" />
			：
			<%--备案号码--%>
		</td>
		<td class="right">
			<input type="text" name="RegistNo" style="width: 100%" class="readonly" title="備案號碼" maxlength="22" readonly="true" value="${prpLclaim.registNo}">
		</td>
	</tr>
	<tr>
		<td class="left">
			<s:text name="specialCase.ClaimsSelection" />
			：
			<!--赔案类型选择-->
		</td>
		<td class="right">
			<input type="hidden" name="prplclaimClaimNo" class="common" value="">
			<select name="specialCaseCaseType" onblur="checkList()">
				<c:if test="${swfLogDto.compeFlag=='1'}">
					<option value="7" <c:if test="${swfLogDto.typeFlag=='7' }">selected="selected"</c:if>>
						<s:text name="specialCase.SalvageFees" />
						<!--预-支付抢救费 -->
					</option>
					<option value="8" <c:if test="${swfLogDto.typeFlag=='8' }">selected="selected"</c:if>>
						<s:text name="specialCase.RescueFee" />
						<!--垫付抢救费-->
					</option>
				</c:if>
				<option value="5" <c:if test="${swfLogDto.typeFlag=='5' }">selected="selected"</c:if>>
					<s:text name="check.advance" />
					<!--预赔-->
				</option>
			</select>
		</td>
		<td class="left">
			<s:text name="claim.applicant" />
			：
			<!--申请人-->
		</td>
		<td class="right">
			<input type="hidden" name="specialCaseDealerCode" title="賠案申請人" class="readonly" value="${swfLogDto.handlerCode }">
			<input type=text name="specialCaseDealerName" title="賠案申請人" class="readonly" value="${swfLogDto.handlerName}">
		</td>
		<td class="left">
			<s:text name="claim.applyTime" />
			：
			<!--申请时间-->
		</td>
		<td class="right">
			<%-- <input type=text name="specialCaseflowInTime" title="申請時間" class="readonly" value="${swfLogDto.flowInTime}">--%>
			<rc:rcDate name="specialCaseflowInTime" title="申請時間" class="readonly" readonly="true" wdatePicker="false" style="width:150px" value="${swfLogDto.flowInTime}" />
		</td>
	</tr>
	<tr>
		<td class="title" colspan="6">
			<s:text name="specialCase.ApplicationReason" />
			：
			<!--申请原因-->
		</td>
	</tr>
	<tr>
		<td class="input" colspan="6" align="center">
			<textarea name='Context' wrap="hard" rows=15 cols=80 class=common>${swfLogDto.titleStr}</textarea>
		</td>
	</tr>
</table>
</tr>
</table>