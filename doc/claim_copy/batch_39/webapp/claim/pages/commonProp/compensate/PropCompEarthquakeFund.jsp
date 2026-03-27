<%--
****************************************************************************
* DESC       ：地震基金讯息
* AUTHOR     ：理赔组
* CREATEDATE ：2004-12-06
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<span style="display: none;">
	<table class="common" cellpadding="4" cellspacing="1" id="EarthquakeFund_Data">
		<tbody>
			<tr class="listeven" name="TrEarthquakeFund">
				<td align="center" style="width: 20%;">
					<input type="text" name="prpLearthquakeFundCompanyCode" class="input">
				</td>
				<td align="center" style="width: 20%;">
					<input type="text" name="prpLearthquakeFundComCode" class="input">
				</td>
				<td align="center" style="width: 15%;">
					<input type="text" name="prpLearthquakeFundPolicyNo" class="input">
				</td>
				<td align="center" style="width: 15%;">
					<input type="text" name="prpLearthquakeFundClaimNo" class="input">
				</td>
				<td align="center" style="width: 10%;">
					<input type="text" name="prpLearthquakeFundTimes" class="input">
				</td>
				<td align="center" style="width: 15%;">
					<input type="text" name="prpLearthquakeFundAddressNo" class="input">
				</td>
				<td align="center" style="width: 5%;">
					<input type=button name="buttonEarthquakeFundDelete" class="smallbutton" onclick="deleteRow(this,'EarthquakeFund');" value="-" style="cursor: hand">
				</td>
			</tr>
		</tbody>
	</table>
</span>
<table class="common" cellpadding="4" cellspacing="1" id="EarthquakeFund">
	<thead>
		<tr>
			<td colspan="7" class="formtitle">
				地震基金跨簽單
			</td>
		</tr>
		<tr>
			<td align="center" class="left" style="width: 20%;">
				地震基金編號
			</td>
			<td align="center"  class="right" style="width: 20%;">
				<input type="text" name="prpLearthquakeFundEarthquakeFundNo" class="input" value="${prpLearthquakeFund.earthquakeFundNo }">
			</td>
			<td align="center" class="left" style="width: 15%;">
				出險日期
			</td>
			<td align="center" colspan="4" class="right" style="width: 45%;">
				<rc:rcDate name="prpLearthquakeFundDamageStartDate" value="${prpLearthquakeFund.damageStartDate }" style="width:30%;" />日
				<input type="text" name="prpLearthquakeFundDamageStartHour" value="${prpLearthquakeFund.damageStartHour }" class="input" style="width:10%;">時
				<input type="text" name="prpLearthquakeFundDamageStartMinute" value="${prpLearthquakeFund.damageStartMinute }"  class="input" style="width:10%;">分
			</td>
		</tr>
		<tr>
			<td class="centertitle" style="width: 20%;">
				公司代號
			</td>
			<td class="centertitle" style="width: 20%;">
				公司機構代號
			</td>
			<td class="centertitle" style="width: 15%;">
				保單號碼
			</td>
			<td class="centertitle" style="width: 15%;">
				賠案號碼
			</td>
			<td class="centertitle" style="width: 10%;">
				賠次
			</td>
			<td class="centertitle" style="width: 15%;">
				地址序號
			</td>
			<td class="centertitle" style="width: 5%;">
				操作
			</td>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="earthquakeFund" items="${prpLearthquakeFund.prpLearthquakeFundList}">
			<tr class="listeven" name="TrEarthquakeFund">
				<td align="center" style="width: 20%;">
					<input type="text" name="prpLearthquakeFundCompanyCode" value="${earthquakeFund.companyCode }" class="input">
				</td>
				<td align="center" style="width: 20%;">
					<input type="text" name="prpLearthquakeFundComCode" value="${earthquakeFund.comCode }" class="input">
				</td>
				<td align="center" style="width: 15%;">
					<input type="text" name="prpLearthquakeFundPolicyNo" value="${earthquakeFund.policyNo }" class="input">
				</td>
				<td align="center" style="width: 15%;">
					<input type="text" name="prpLearthquakeFundClaimNo" value="${earthquakeFund.claimNo }" class="input">
				</td>
				<td align="center" style="width: 10%;">
					<input type="text" name="prpLearthquakeFundTimes" value="${earthquakeFund.times }" class="input">
				</td>
				<td align="center" style="width: 15%;">
					<input type="text" name="prpLearthquakeFundAddressNo" value="${earthquakeFund.addressNo }" class="input">
				</td>
				<td align="center" style="width: 5%;">
					<input type=button name="buttonEarthquakeFundDelete" class="smallbutton" onclick="deleteRow(this,'EarthquakeFund');" value="-" style="cursor: hand">
				</td>
			</tr>
		</c:forEach>
	</tbody>
	<tfoot>
		<tr  class="listeven">
			<td align="left" colspan="6">
				(按"+"號鍵增加地震基金訊息，按"-"號鍵刪除訊息)
			</td>
			<td align="right">
				<div align="center">
					<input type="button" value="+" onclick="insertRow('EarthquakeFund');" class="smallbutton" name="buttonEarthquakeFundInsert" style="cursor: hand">
				</div>
			</td>
		</tr>
	</tfoot>
</table>
