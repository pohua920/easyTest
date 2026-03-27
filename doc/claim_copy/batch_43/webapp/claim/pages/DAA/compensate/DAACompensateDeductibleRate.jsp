<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app"%>
<%@ page import="java.util.Vector"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.sinosoft.claim.dto.domain.*"%>
<html>
<head>
<script language='javascript'>
	function deductibleRate() {

		var deductibleRateCount = window.opener.fm.all("prpLlossDtoKindCode").length;

		var kindCodeCount = parseInt(fm.kindCodeCount.value, 10);
		//var j=0;

		//修改赔付标的信息已有的责任免赔率及绝对免赔率
		for ( var i = 0; i < deductibleRateCount; i++) {
			for ( var j = 1; j < (kindCodeCount + 1); j++) {

				if (window.opener.fm.prpLlossDtoKindCode[i].value == fm.KindCode[j].value) {
					window.opener.fm.prpLlossDtoDutyDeductibleRate[i].value = fm.DutyDeductibleRate[j].value;
					window.opener.fm.prpLlossDtoDeductibleRate[i].value = fm.DeductibleRate[j].value;
					window.opener.fm.prpLlossDtoDeductibleRate[i].onchange();
					window.opener.fm.prpLlossDtoDutyDeductibleRate[i]
							.onchange();
				}
			}
		}

		//修改点击加号产生的赔付标的信息责任免赔率及绝对免赔率
		var deductibleCount = window.opener.fm.all("prpLlossDtoKindCodeShow").length;
		for ( var i = 0; i < deductibleCount; i++) {
			for ( var j = 1; j < (kindCodeCount + 1); j++) {
				if (window.opener.fm.prpLlossDtoKindCodeShow[i].value == fm.KindCode[j].value) {
					window.opener.fm.DutyDeductibleRate[i].value = fm.DutyDeductibleRate[j].value;
					window.opener.fm.DeductibleRate[i].value = fm.DeductibleRate[j].value;
				}
			}
		}

		//修改赔付人员信息已有的免赔率
		var personCount = window.opener.fm.all('prpLpersonLossKindCode').length;
		for ( var i = 0; i < personCount; i++) {
			for ( var j = 1; j < (kindCodeCount + 1); j++) {
				if (window.opener.fm.prpLpersonLossKindCode[i].value == fm.KindCode[j].value) {
					window.opener.fm.prpLpersonLossDutyDeductibleRate[i].value = fm.DutyDeductibleRate[j].value;
					window.opener.fm.prpLpersonLossDeductibleRate[i].value = fm.DeductibleRate[j].value;
					window.opener.fm.prpLpersonLossDutyDeductibleRate[i]
							.onchange();
					window.opener.fm.prpLpersonLossDeductibleRate[i].onchange();
				}
			}
		}

		//修改费用赔款信息已有的免赔率 （目前只修改车损险的施救费，只有车损险施救费需要计算）
		var chargeCount = window.opener.fm.all('prpLchargeKindCode').length;
		for ( var i = 0; i < chargeCount; i++) {
			for ( var j = 1; j < (kindCodeCount + 1); j++) {
				if ('A' == fm.KindCode[j].value
						&& window.opener.fm.prpLchargeKindCode[i].value == 'A'
						&& window.opener.fm.prpLchargeChargeCode[i].value == '03') {
					window.opener.fm.prpLchargeChargeReport[i].onchange();
					window.opener.fm.prpLchargeChargeAmount[i].onchange();
				}
			}
		}

		//改变叶面隐藏的域
		var kindCode2Count = window.opener.fm.all('kindCode2').length;
		for ( var i = 0; i < kindCode2Count; i++) {
			for ( var j = 1; j < (kindCodeCount + 1); j++) {
				if (window.opener.fm.kindCode2[i].value == fm.KindCode[j].value) {
					window.opener.fm.absolutDeductRate[i].value = fm.DeductibleRate[j].value;

				}
			}
		}

		//add by miaowenjun for 修改页面隐藏的免赔率条件
		var deductCondCount = parseInt(fm.DeductCondLength.value, 10);
		for ( var i = 2; i < deductCondCount + 2; i++) {
			window.opener.fm.deductName[i].value = fm.deductName[i - 2].value;
			window.opener.fm.deductCondition[i].value = fm.deductCondition[i - 2].value;
			window.opener.fm.Times[i].value = fm.Times[i - 2].value;
		}

		window.close();
	}
</script>
</head>
<body>
	<form name=fm>
		<table>
			<td>
				<input type="text" name="KindCode" value="1">
				<input type="text" name="DutyDeductibleRate" value="1">
				<input type="text" name="DeductibleRate" value="1">
			</td>
			<%
				PrpLcompensateDto prpLcompensateDto = (PrpLcompensateDto) request.getAttribute("prpLcompensateDto");
				List prpLdeductCondDtoList = new ArrayList();
				prpLdeductCondDtoList = prpLcompensateDto.getPrpLdeductCondDtoList();
				if (prpLdeductCondDtoList != null
						&& prpLdeductCondDtoList.size() != 0) {
					for (int i = 0; i < prpLdeductCondDtoList.size(); i++) {
			%>
			<input type="hidden" name="deductName" value="<%=((PrpLdeductCondDto) prpLdeductCondDtoList.get(i))
							.getDeductCondName()%>">
			<input type="hidden" name="deductCondition" value="<%=((PrpLdeductCondDto) prpLdeductCondDtoList.get(i))
							.getDeductCondCode()%>">
			<input type="hidden" name="Times" value="<%=String
							.valueOf(((PrpLdeductCondDto) prpLdeductCondDtoList
									.get(i)).getTimes())%>">
			<%
				}
			%>
			<input type="text" name="DeductCondLength" value="<%=prpLdeductCondDtoList.size()%>">
			<%
				}
				Vector it = (Vector) request.getAttribute("it");
				if (it != null && it.size() > 0) {
					for (int i = 0; i < it.size() / 3; i++) {
						String kindCode = (String) it.elementAt(i * 3);
						//System.out.println("=== kindCode ========" +kindCode);
						double dutyDeductibleRate = Double.parseDouble((String) it
								.elementAt(i * 3 + 1));
						double deductibleRate = Double.parseDouble((String) it
								.elementAt(i * 3 + 2));
			%>
			<tr>
				<td>
					<input type="text" name="KindCode" value="<%=kindCode%>">
					<input type="text" name="DutyDeductibleRate" value="<%=dutyDeductibleRate%>">
					<input type="text" name="DeductibleRate" value="<%=deductibleRate%>">
				</td>
			</tr>
			<%
				}
				} else {
			%>
			<input type="text" name="KindCode" value="">
			<%
				}
			%>
			<input type="text" name="kindCodeCount" value="<%=it.size() / 3%>">
		</table>
	</form>
</body>
<script language='javascript'>
	deductibleRate();
</script>
</html>
