<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/undwrt-app.tld" prefix="app"%>
<%@ include file="/common/i18njs.jsp"%>
<%@ include file="CommonStyle.html"%>


<html>
<head>
<title>理賠記錄</title>
<!-- 公用函数 -->
<jsp:include page="/common/meta_css.jsp" />
<jsp:include page="/common/meta_js.jsp" />
<script src="/undwrt/common/js/Common.js"></script>
<script src="/undwrt/common/js/Common_undwrt.js"></script>
<script src="/undwrt/common/js/CommonTaskDeal.js"></script>
<script src="/undwrt/common/BLcommonCommon.js"></script>


<!-- 页面样式 -->
<link rel="stylesheet" type="text/css" href="/undwrt/css/Standard.css">
</head>

<body>
	<form name="fm" action="${ctx}/claimInfo/showClaimInfo.do?actionType=queryContinue">
		<s:hidden name="pageNo" ></s:hidden>
		<s:hidden name="rowsCount" ></s:hidden>
		<s:hidden name="rowsPerPage" ></s:hidden>
		<input type="hidden" name="businessNo" value="${businessNo }"/>
	
		<table class="common" cellpadding="5" cellspacing="1" align="center">
			<tr class="listtitle">
				<td colspan="10">理賠記錄訊息</td>
			</tr>
			
			<tr class=common>
				<td>保單號碼</td>
				<td>被保險人</td>
				<td>要保人</td>
				<td>標的物地址</td>
				<td>承保險種</td>
				<td>發生時間</td>
				<td>損失原因</td>
				<td>预估金额</td>
				<td>損失金额</td>
				<td>赔款金额</td>
			</tr>
			<s:iterator value="listClaimInfoVo" id="claimInfo">
			<tr class=common>
				<td>${policyNo }</td>
				<td>${insuredName1 }</td>
				<td>${insuredName2 }</td>
				<td>${itemAddress }</td>
				<td>${riskName }</td>
				<td>${damagestartdate }</td>
				<td>${damagename }</td>
				<td>${outstanding }</td>
				<td>${sumpaid }</td>
				<td>${sumpaid }</td>
			</tr>
			</s:iterator>
		</table>
		
		<table class=menu align="center">
			<tr>
				<td>
					<app:navigate name="fm" objectName="fm"/>
				</td>
			</tr>
   		</table>
		
		<table class="two">
			<tr>
				<td class="button">
					<input name="close" class="button" type="button" value="關閉" onclick="window.close();"/>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
