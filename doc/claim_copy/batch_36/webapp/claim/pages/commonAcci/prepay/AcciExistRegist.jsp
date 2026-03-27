<!--
****************************************************************************
* DESC       ：出险信息画面
* AUTHOR     ： Sinosoft 
* CREATEDATE ： 2004-12-09
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
-->
<%@ include file="/common/taglibs.jsp"%>
<SCRIPT LANGUAGE="JavaScript">
	//reason:在报案登记画面中，已出险次数的历次出险事故的清单中,可以点击报案号关联到相关案件信息  
	/**
	 *@description 弹出关联报案信息页面
	 *@param       无
	 *@return      通过返回true,否则返回false
	 */
	 function showRegist(registNo) {

		    var linkURL = "${ctx}/registFinishQueryList.do?prpLregistRegistNo=" + registNo + "&editType=SHOW";
		    var newWindow = window
		        .open(
		            linkURL,
		            "NewWindow",
		            "width=640,height=500,top=0,left=0,toolbar=yes,location=no,directories=no,menubar=no,scrollbars=yes,resizable=yes,status=no");
		}

		function showPicture(registNo) {
		    var linkURL = "${ctx}/DAA/certify/DAACertifyViewFile.jsp?businessNo=" + registNo + "&display=all";
		    var newWindow = window
		        .open(
		            linkURL,
		            "NewWindow",
		            "width=640,height=500,top=0,left=0,toolbar=yes,location=no,directories=no,menubar=no,scrollbars=yes,resizable=yes,status=no");
		}
</SCRIPT>
<input type=text name="PerilCount" class="readonly" readonly="true" style="width: 50%; text-align ='center'; color: '#9B009B'" value="${prpLregist1.perilCount}">
<input title="點選此處可獲得已出險相關訊息" type=button ACCESSKEY="." value='...' name='button_Peril_Open_Context' onclick="buttonOnClick('span_Peril_Context');">
<span id="span_Peril_Context" style='width: 800; display: none; position: absolute; background-color: FFFFFF;'>
	<table class="prompt" style="width: 400">
		<tr class="prompt">
			<td class="prompttitle">
				<s:text name="db.prpLmedicine.serialNo" />
			</td>
			<%--序号--%>
			<td class="prompttitle">
				<s:text name="prpLregist.registNo" />
			</td>
			<%--报案号--%>
			<!--原因：在界面上增加一些信息-->
			<td class="prompttitle">
				<s:text name="check.claimNum" />
			</td>
			<%--赔案号--%>
			<td class="prompttitle">
				<s:text name="pub.lossAssessAmount" />
			</td>
			<%--估损金额CNY--%>
			<td class="prompttitle">
				<s:text name="pub.compensatAmount" />
			</td>
			<%--赔付金额CNY--%>
			<td class="prompttitle">
				<s:text name="certainLoss.prpLcheck.prpLcheckDamageStartDate" />
			</td>
			<td class="prompttitle">
				<s:text name="certainLoss.prpLcheck.prpLcheckDamageAddress" />
			</td>
			<%--出险地点--%>
			<td class="prompttitle">
				<s:text name="certainLoss.prpLcheck.prpLcheckDamageCase" />
			</td>
			<%--出险原因--%>
			<td class="prompttitle">
				<s:text name="certainLoss.thirdCarLoss.prpLchecDemagePart" />
			</td>
			<%--损失部位--%>
			<td class="prompttitle">
				<s:text name="regist.prpLregist.casePhoto" />
			</td>
			<%--案件照片--%>
			<td class="prompttitle">
				<s:text name="db.prpLregist.phoneNumber" />
			</td>
			<%--联系电话--%>
			<td class="prompttitle">
				<s:text name="db.prpDshortrate.validStatus" />
			</td>
			<%--状态--%>
		</tr>
		<!-- 插入出险次数详细信息-->
		<c:if test="${registClaimDtoList!=null}">
			<c:forEach var="registClaimDto" items="${registClaimDtoList}" varStatus="registClaimDto_status">
				<tr>
					<td class="prompt">${registClaimDto_status.count }</td>
					<!--resson:在报案登记画面中，已出险次数的历次出险事故的清单中,可以点击报案号关联到相关案件信息-->
					<td class="prompt">
						<a href="javascript:showRegist('${registClaimDto.registNo}">${registClaimDto.registNo}</a>
					</td>
					<!--原因：在界面上增加一些信息-->
					<td class="prompt">${registClaimDto.claimNo}</td>
					<td class="prompt">${registClaimDto.sumClaim}</td>
					<td class="prompt">${registClaimDto.sumPaidShow}</td>
					<td class="prompt">${registClaimDto.damageStartDate}</td>
					<td class="prompt">${registClaimDto.damageAreaName}</td>
					<td class="prompt">${registClaimDto.damageName}</td>
					<td class="prompt">${registClaimDto.compName}</td>
					<td class="prompt">
						<a href="javascript:showPicture('${registClaimDto.registNo}')">${registClaimDto.registNo}</a>
					</td>
					<td class="prompt">${registClaimDto.phoneNumber}</td>
					<td class="prompt">${registClaimDto.status}</td>
				</tr>
			</c:forEach>
		</c:if>
		<tr>
			<td colspan=14 class="prompttitle">
				<input type=button name='button_Peril_Close_Context' value="<s:text name='button.close.value' />" ACCESSKEY="O" onclick="hideSubPage(this,'span_Peril_Context')">
			</td>
		</tr>
	</table>
</span>
