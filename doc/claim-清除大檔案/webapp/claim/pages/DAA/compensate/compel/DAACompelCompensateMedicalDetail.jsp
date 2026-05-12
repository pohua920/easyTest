<%@ page contentType="text/html; charset=GBK"%>
<html>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta_js.jsp"%>
<head>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<script language="javascript" src="${ctx}/pages/DAA/compensate/compel/js/DAACompelPrpLcompelMedical.js"></script>
<title>強制險醫療給付費用明細</title>
<style type="text/css">
	table tbody tr td {
		line-height: 25px;
		width: 60px;
	}
	TD.centertitle {
		background: #cefaf2 url(../images/formtitle_1.gif) top repeat-x;
		border: 1px solid #4fbcb9;
		font-size: 11pt;
		font-weight: normal;
		text-align: center;
		color: #106466;
		height: 24px;
	}
	
</style>
<script type="text/javascript">
	$(function(){
		$(":input[name^='Fee']").on("change",function(){
			setSumFee(this.name);//獲取當前變動的錄入域名
			var $tr = $(this).closest("tr[name='prpLcompelMedicalObject']");
			setFeeA($tr[0]);// 統計單張收據醫療費用之和
			setSumFeeA01234();
		})
		setSumAll();
		$(":input").ajaxStart(function(){
			$(this).prop("disabled" , true);
		 }).ajaxComplete(function(){
			$(this).prop("disabled" , false);
		 });
	})
	function checkDate(filed){
		 var nowDate = new Date();
		if(nowDate<new Date(filed.value)){
			alert("'就診日期（起日 ）'不能大於當前日期");
			filed.value="";
			return false;
		}
	}

</script>
</head>
<c:choose>
	<c:when test="${param.actionType=='SHOW'}">
		<body style="overflow: auto;padding: 5px;" onload="initPage();readonlyAllInput();">
	</c:when>
	<c:when test="${param.actionType=='EDIT' || param.actionType=='ADD' || param.actionType=='AMEND'}">
		<body style="overflow: auto;padding: 5px;" onload="initPage();">
	</c:when>
	<c:otherwise>
		<body style="overflow: auto;padding: 5px;" onload="readonlyAllInput();">
	</c:otherwise>
</c:choose>
	<form name="fm" method="post" autocomplete="off" >
		<span style="display: none">
			<table class="common" style="display: none" id="PrpLcompelMedical_Data" cellspacing="1" cellpadding="0">
				<tbody>
						<tr class="content" bgcolor="#F7F7F7" name="prpLcompelMedicalObject">
							<td class="title" ><input type="checkbox" name="cbx"><input type="text" name="SerialNo" class="readonly" readonly="readonly" style="width: 80px;" value=""></td>
							<!-- mantis： CLM0046 ，處理人員：BK007 蘇哲，需求單編號：CLM0046強制險醫療明細提醒同一身分證號與日期重複時有提醒與之前重複的賠次 -->
							<td class="title" ><input type="text" class="Wdate" name="StartDate" onblur="checkDate(this)" onchange="verifyDate(this)" onfocus="WdatePicker({dateFmt: 'yyyy/MM/dd'})"/></td>
							<td class="title" ><input type="text" class="input" style="width: 60px;" name="FeeA01" value=""></td>
							<td class="title" ><input type="text" class="input" style="width: 60px;" name="FeeA021" value=""></td>
							<td class="title" ><input type="text" class="input" style="width: 60px;" name="FeeA022" value=""></td>
							<td class="title" ><input type="text" class="input" style="width: 60px;" name="FeeA023" value=""></td>
							<td class="title" ><input type="text" class="input" style="width: 60px;" name="FeeA024" value=""></td>
							<td class="title" ><input type="text" class="input" style="width: 60px;" name="FeeA025" value=""></td>
							<td class="title" ><input type="text" class="input" style="width: 60px;" name="FeeA026" value=""></td>
							<td class="title" ><input type="text" class="input" style="width: 60px;" name="FeeA029a" value=""></td>
							<td class="title" ><input type="text" class="input" style="width: 60px;" name="FeeA029b" value=""></td>
							<td class="title" ><input type="text" class="input" style="width: 60px;" name="FeeA029c" value=""></td>
							<td class="title" ><input type="text" class="input" style="width: 60px;" name="FeeA029z" value=""></td>
							<td class="title" ><input type="text" class="input" style="width: 60px;" name="FeeA03" value=""></td>
							<td class="title" ><input type="text" class="input" style="width: 60px;" name="FeeA04" value=""></td>
							<td class="title" ><input type="text" readonly="readonly" class="readonly" style="width: 60px;" name="FeeA" value=""></td>
							<td class="title" >
								<select name="healthHospitalize" class='common' style="width: 110">
									<option value="Y">Y</option>
									<option value="N">N</option>
								</select>
							</td>
<!-- 
* mantis： CLM0046 ，處理人員：BK007 蘇哲，需求單編號：CLM0046強制險醫療明細提醒同一身分證號與日期重複時有提醒與之前重複的賠次-start
* 處理過程：
*  PrpLcompensateService 新增 verifyPrpLcompelMedical 方法，提供 verifyMedicalDetail Ajax 使用，驗證同一身分證號與日期重複資訊
*  DAACompelPrpLcompelMedical.js 提供前台驗證，有輸入時提醒與送出驗證及提醒
*  ClaimPrintAction 列印功能新增備註頁面(prpLcompelMedicalComment.jrxml)及調整 項目渲染時的提示 (prpLcompelMedical_subreport0.jrxml) 
-->
							<c:if test="${param.actionType=='EDIT' || param.actionType=='ADD'}">
								<td class="title" ><input type="text" readonly="readonly" class="readonly" style="width: 60px;" name="vMsg" value=""></td>
							</c:if>
<!--
mantis： CLM0046 ，處理人員：BK007 蘇哲，需求單編號：CLM0046強制險醫療明細提醒同一身分證號與日期重複時有提醒與之前重複的賠次 -end
  --> 
						<tr/>
				</tbody>
			</table>
		</span>
		<div align="left" style="margin: 5px 0 5px 0;">
			<input type="hidden" id="compensateNo" name="compensateNo" value="${param.compensateNo}">
			<input type="hidden" id="personNo" name="personNo" value="${requestScope.prpLcompelMedical.personNo}">
			<input type="hidden" name="claimNo" value="${param.claimNo}">
			<input type="hidden" name="personName" value="${requestScope.prpLcompelMedical.personName}">
			<input type="hidden" id="identifyNumber" name="identifyNumber" value="${requestScope.prpLcompelMedical.id.identifyNumber}">
			<input type="hidden" id="claimReceiveDate" name="claimReceiveDate" value="${requestScope.reportDate}">
			<input type="hidden" id="endCaseDate" name="endCaseDate" value="${requestScope.underWriteEndDate}">
			<input type="hidden" name="prpLpayObjectInfoPaycodeType" value="${requestScope.prpLpayObjectInfoPaycodeType}">
			<input type="hidden" name="damageDate" value="${requestScope.damageDate}">
			<div align="left" style="width: 100%;">
				案號：<span style="width: 150px;">${param.claimNo}</span>
				姓名：<span style="width: 120px;">${requestScope.prpLcompelMedical.personName}</span>
				受害人身份證：<span style="width: 120px;">${requestScope.prpLcompelMedical.id.identifyNumber}</span>
				結案時間：<span style="width: 120px;">${requestScope.underWriteEndDate}</span>
			</div>
			<c:if test="${param.actionType=='EDIT' || param.actionType=='ADD'}">
				<div style="width: 100%;color: red;" align="left">注意：費用收據資料與“計算書號碼”和“受害人身分證號碼”綁定，如“受害人身分證號碼”有異動，需重新錄入收據資料！</div>
			</c:if>
		</div>
		<table align="center" cellspacing="1" cellpadding="0" style="width: 100%">
			<thead>
				<tr class="tableHead">
					<td class="centertitle" rowspan="4"><div style="width: 80px ;">醫療費用收據編號</div></td>
					<td class="centertitle" colspan="1" rowspan="4">就診日期(起日)</td>
					<td class="centertitle" colspan="13">核付費用</td>
					<td class="centertitle" colspan="2">&nbsp;</td>
<!-- 
* mantis： CLM0046 ，處理人員：BK007 蘇哲，需求單編號：CLM0046強制險醫療明細提醒同一身分證號與日期重複時有提醒與之前重複的賠次-start
* 處理過程：
*  PrpLcompensateService 新增 verifyPrpLcompelMedical 方法，提供 verifyMedicalDetail Ajax 使用，驗證同一身分證號與日期重複資訊
*  DAACompelPrpLcompelMedical.js 提供前台驗證，有輸入時提醒與送出驗證及提醒
*  ClaimPrintAction 列印功能新增備註頁面(prpLcompelMedicalComment.jrxml)及調整 項目渲染時的提示 (prpLcompelMedical_subreport0.jrxml) 
-->
					<c:if test="${param.actionType=='EDIT' || param.actionType=='ADD'}">
						<td class="centertitle" colspan="2">&nbsp;</td>
					</c:if>
<!--
mantis： CLM0046 ，處理人員：BK007 蘇哲，需求單編號：CLM0046強制險醫療明細提醒同一身分證號與日期重複時有提醒與之前重複的賠次 -end
  -->
				</tr >
				<tr class="tableHead">
					<!-- <td class="centertitle" rowspan="3">起日</td>
					<td class="centertitle" rowspan="3">迄日</td> -->
					<td class="centertitle" rowspan="2">A01</td>
					<td class="centertitle" colspan="10">A02</td>
					<td class="centertitle" rowspan="2">A03</td>
					<td class="centertitle" rowspan="2">A04</td>
					<td class="centertitle" rowspan="2">A00</td>
					<td class="centertitle" rowspan="2">&nbsp;</td>
<!-- 
* mantis： CLM0046 ，處理人員：BK007 蘇哲，需求單編號：CLM0046強制險醫療明細提醒同一身分證號與日期重複時有提醒與之前重複的賠次-start
* 處理過程：
*  PrpLcompensateService 新增 verifyPrpLcompelMedical 方法，提供 verifyMedicalDetail Ajax 使用，驗證同一身分證號與日期重複資訊
*  DAACompelPrpLcompelMedical.js 提供前台驗證，有輸入時提醒與送出驗證及提醒
*  ClaimPrintAction 列印功能新增備註頁面(prpLcompelMedicalComment.jrxml)及調整 項目渲染時的提示 (prpLcompelMedical_subreport0.jrxml) 
-->
					<c:if test="${param.actionType=='EDIT' || param.actionType=='ADD'}">
						<td class="centertitle" rowspan="2">&nbsp;</td>
					</c:if>
<!--
mantis： CLM0046 ，處理人員：BK007 蘇哲，需求單編號：CLM0046強制險醫療明細提醒同一身分證號與日期重複時有提醒與之前重複的賠次 -end
  --> 
				</tr>
				<tr class="tableHead">
					<td class="centertitle" >1</td>
					<td class="centertitle" >2</td>
					<td class="centertitle" >3</td>
					<td class="centertitle" >4</td>
					<td class="centertitle" >5</td>
					<td class="centertitle" >6</td>
					<td class="centertitle" >9A</td>
					<td class="centertitle" >9B</td>
					<td class="centertitle" >9C</td>
					<td class="centertitle" >&nbsp;</td>
				</tr >
				<tr class="tableHead">
					<td class="centertitle" ><div style="width: 20px">急救費用</div></td>
					<td class="centertitle" ><div style="width: 60px ;">自行負擔之病房費差額</div></td>
					<td class="centertitle" ><div style="width: 20px ;">膳食費</div></td>
					<td class="centertitle" ><div style="width: 60px ;">自行負擔之義肢器材及裝置費用</div></td>
					<td class="centertitle" ><div style="width: 60px ;">義齒器材及裝置費用</div></td>
					<td class="centertitle" ><div style="width: 60px ;">義眼器材及裝置費用</div></td>
					<td class="centertitle" ><div style="width: 60px ;">其他必要之醫療器材</div></td>
					<td class="centertitle" ><div style="width: 20px">部分負擔</div></td>
					<td class="centertitle" ><div style="width: 20px">掛號費</div></td>
					<td class="centertitle" ><div style="width: 20px">診斷證明書</div></td>
					<td class="centertitle" ><div style="width: 60px ;">依健保緊急自墊醫療費用核退辦法核付診療費用</div></td>
					<td class="centertitle" ><div style="width: 20px ;">接送費用</div></td>
					<td class="centertitle" ><div style="width: 20px ;">看護費用</div></td>
					<td class="centertitle" ><div style="width: 20px">合計</div></td>
					<td class="centertitle" ><div style="width: 20px">是否以健保身份就醫(Y/N)</div></td>
<!-- 
* mantis： CLM0046 ，處理人員：BK007 蘇哲，需求單編號：CLM0046強制險醫療明細提醒同一身分證號與日期重複時有提醒與之前重複的賠次-start
* 處理過程：
*  PrpLcompensateService 新增 verifyPrpLcompelMedical 方法，提供 verifyMedicalDetail Ajax 使用，驗證同一身分證號與日期重複資訊
*  DAACompelPrpLcompelMedical.js 提供前台驗證，有輸入時提醒與送出驗證及提醒
*  ClaimPrintAction 列印功能新增備註頁面(prpLcompelMedicalComment.jrxml)及調整 項目渲染時的提示 (prpLcompelMedical_subreport0.jrxml) 
-->
					<c:if test="${param.actionType=='EDIT' || param.actionType=='ADD'}">
						<!-- mantis：CLM0071 ，處理人員：BK007 蘇哲，需求單編號：CLM0071.車險理算節點修正 -->
						<td class="centertitle" ><div style="width: 20px">是否重複就醫確認(Y/N)</div></td>
					</c:if>
<!--
mantis： CLM0046 ，處理人員：BK007 蘇哲，需求單編號：CLM0046強制險醫療明細提醒同一身分證號與日期重複時有提醒與之前重複的賠次 -end
  -->
				</tr>
				<tr class="content" bgcolor="#F7F7F7">
					<td class="title"><div style="width: 60px ;">上次簽結</div></td>
					<td class="title"><input type="text" readonly="readonly" class="readonly" style="width: 95px" value="${requestScope.lastPrpLcompelMedical.startDate}" id="lastStartDateDisPlay" name="lastStartDateDisPlay"/></td>
					<td class="title"><input type="text" readonly="readonly" class="readonly" style="width: 95px" value="${requestScope.lastPrpLcompelMedical.a01}" id="lastSumFeeA01" name="lastSumFeeA01"/></td>
				    <td class="title"><input type="text" readonly="readonly" class="readonly" style="width: 95px" value="${requestScope.lastPrpLcompelMedical.a021}" id="lastSumFeeA021" name="lastSumFeeA021"/></td>
					<td class="title"><input type="text" readonly="readonly" class="readonly" style="width: 95px" value="${requestScope.lastPrpLcompelMedical.a022}" id="lastSumFeeA022" name="lastSumFeeA022"/></td>
					<td class="title"><input type="text" readonly="readonly" class="readonly" style="width: 95px" value="${requestScope.lastPrpLcompelMedical.a023}" id="lastSumFeeA023" name="lastSumFeeA023"/></td>
					<td class="title"><input type="text" readonly="readonly" class="readonly" style="width: 95px" value="${requestScope.lastPrpLcompelMedical.a024}" id="lastSumFeeA024" name="lastSumFeeA024"/></td>
					<td class="title"><input type="text" readonly="readonly" class="readonly" style="width: 95px" value="${requestScope.lastPrpLcompelMedical.a025}" id="lastSumFeeA025" name="lastSumFeeA025"/></td>
					<td class="title"><input type="text" readonly="readonly" class="readonly" style="width: 95px" value="${requestScope.lastPrpLcompelMedical.a026}" id="lastSumFeeA026" name="lastSumFeeA026"/></td>
				    <td class="title"><input type="text" readonly="readonly" class="readonly" style="width: 95px" value="${requestScope.lastPrpLcompelMedical.a029a}" id="lastSumFeeA029a" name="lastSumFeeA029a"/></td>
					<td class="title"><input type="text" readonly="readonly" class="readonly" style="width: 95px" value="${requestScope.lastPrpLcompelMedical.a029b}" id="lastSumFeeA029b" name="lastSumFeeA029b"/></td>
					<td class="title"><input type="text" readonly="readonly" class="readonly" style="width: 95px" value="${requestScope.lastPrpLcompelMedical.a029c}" id="lastSumFeeA029c" name="lastSumFeeA029c"/></td>
					<td class="title"><input type="text" readonly="readonly" class="readonly" style="width: 95px" value="${requestScope.lastPrpLcompelMedical.a029z}" id="lastSumFeeA029z" name="lastSumFeeA029z"/></td>
					<td class="title"><input type="text" readonly="readonly" class="readonly" style="width: 95px" value="${requestScope.lastPrpLcompelMedical.a03}" id="lastSumFeeA03" name="lastSumFeeA03"/></td>
					<td class="title"><input type="text" readonly="readonly" class="readonly" style="width: 95px" value="${requestScope.lastPrpLcompelMedical.a04}" id="lastSumFeeA04" name="lastSumFeeA04"/></td>
					<td class="title" ><input type="text" readonly="readonly" class="readonly" style="width: 60px;" id="SumFeeA01234" value="" name="SumFeeA01234"></td>
					<td class="title" ><input type="text" readonly="readonly" class="readonly" style="width: 60px;" id="healthHospitalize" value="" name="lastHealthHospitalize"></td>
				<tr/>
				<tr class="content" bgcolor="#F7F7F7" >
					<td class="title" >總計</td>
					<td class="title" ><input type="text" readonly="readonly" class="readonly" style="width: 95px" id="StartDateDisPlay" name="StartDateDisPlay"/></td>
					<td class="title" ><input type="text" readonly="readonly" class="readonly" style="width: 60px;" id="SumFeeA01" value="" name="SumFeeA01"></td>
					<td class="title" ><input type="text" readonly="readonly" class="readonly" style="width: 60px;" id="SumFeeA021" value="" name="SumFeeA021"></td>
					<td class="title" ><input type="text" readonly="readonly" class="readonly" style="width: 60px;" id="SumFeeA022" value="" name="SumFeeA022"></td>
					<td class="title" ><input type="text" readonly="readonly" class="readonly" style="width: 60px;" id="SumFeeA023" value="" name="SumFeeA023"></td>
					<td class="title" ><input type="text" readonly="readonly" class="readonly" style="width: 60px;" id="SumFeeA024" value="" name="SumFeeA024"></td>
					<td class="title" ><input type="text" readonly="readonly" class="readonly" style="width: 60px;" id="SumFeeA025" value="" name="SumFeeA025"></td>
					<td class="title" ><input type="text" readonly="readonly" class="readonly" style="width: 60px;" id="SumFeeA026" value="" name="SumFeeA026"></td>
					<td class="title" ><input type="text" readonly="readonly" class="readonly" style="width: 60px;" id="SumFeeA029a" value="" name="SumFeeA029a"></td>
					<td class="title" ><input type="text" readonly="readonly" class="readonly" style="width: 60px;" id="SumFeeA029b" value="" name="SumFeeA029b"></td>
					<td class="title" ><input type="text" readonly="readonly" class="readonly" style="width: 60px;" id="SumFeeA029c" value="" name="SumFeeA029c"></td>
					<td class="title" ><input type="text" readonly="readonly" class="readonly" style="width: 60px;" id="SumFeeA029z" value="" name="SumFeeA029z"></td>
					<td class="title" ><input type="text" readonly="readonly" class="readonly" style="width: 60px;" id="SumFeeA03" value="" name="SumFeeA03"></td>
					<td class="title" ><input type="text" readonly="readonly" class="readonly" style="width: 60px;" id="SumFeeA04" value="" name="SumFeeA04"></td>
					<td class="title" ><input type="text" readonly="readonly" class="readonly" style="width: 60px;" id="SumFeeA" value="" name="SumFeeA"></td>
				    <td class="title" ><input type="text" readonly="readonly" class="readonly" style="width: 60px;" id="healthHospitalize" value="" name="sumHealthHospitalize"></td>
					
				<tr/>
				<tr class="content" bgcolor="#F7F7F7">
					<td class="title" colspan="10">&nbsp;</td>
					<td class="title" colspan="4" align="center"><input type="text" readonly="readonly" class="readonly" style="width: 60px;" id="SumFeeA029" value=""></td>
					<td class="title" colspan="5">&nbsp;</td>
				<tr/>
			</thead>
			<tbody id="PrpLcompelMedical">
				<c:forEach items="${requestScope.prpLcompelMedicalList}" var="prpLcompelMedical">
					<tr class="content" bgcolor="#F7F7F7" name="prpLcompelMedicalObject">
						<td class="title" ><input type="checkbox" name="cbx"><input type="text" name="SerialNo" class="readonly" readonly="readonly" style="width: 80px;" value="${prpLcompelMedical.id.serialNo}"></td>
						<!-- mantis： CLM0046 ，處理人員：BK007 蘇哲，需求單編號：CLM0046強制險醫療明細提醒同一身分證號與日期重複時有提醒與之前重複的賠次 -->
						<td class="title" ><input type="text" class="Wdate" name="StartDate"  value="<fmt:formatDate value='${prpLcompelMedical.startDate}' pattern='yyyy/MM/dd'/>" onchange="verifyDate(this)" onfocus="WdatePicker({dateFmt: 'yyyy/MM/dd' })" onblur="checkDate(this)"/></td>
						<td class="title" ><input type="text" class="input" style="width: 60px;" name="FeeA01" value="<fmt:formatNumber value="${prpLcompelMedical.a01}" pattern="#"/>" ></td>
						<td class="title" ><input type="text" class="input" style="width: 60px;" name="FeeA021" value="<fmt:formatNumber value="${prpLcompelMedical.a021}" pattern="#"/>" ></td>
						<td class="title" ><input type="text" class="input" style="width: 60px;" name="FeeA022" value="<fmt:formatNumber value="${prpLcompelMedical.a022}" pattern="#"/>" ></td>
						<td class="title" ><input type="text" class="input" style="width: 60px;" name="FeeA023" value="<fmt:formatNumber value="${prpLcompelMedical.a023}" pattern="#"/>" ></td>
						<td class="title" ><input type="text" class="input" style="width: 60px;" name="FeeA024" value="<fmt:formatNumber value="${prpLcompelMedical.a024}" pattern="#"/>" ></td>
						<td class="title" ><input type="text" class="input" style="width: 60px;" name="FeeA025" value="<fmt:formatNumber value="${prpLcompelMedical.a025}" pattern="#"/>" ></td>
						<td class="title" ><input type="text" class="input" style="width: 60px;" name="FeeA026" value="<fmt:formatNumber value="${prpLcompelMedical.a026}" pattern="#"/>" ></td>
						<td class="title" ><input type="text" class="input" style="width: 60px;" name="FeeA029a" value="<fmt:formatNumber value="${prpLcompelMedical.a029a}" pattern="#"/>" ></td>
						<td class="title" ><input type="text" class="input" style="width: 60px;" name="FeeA029b" value="<fmt:formatNumber value="${prpLcompelMedical.a029b}" pattern="#"/>" ></td>
						<td class="title" ><input type="text" class="input" style="width: 60px;" name="FeeA029c" value="<fmt:formatNumber value="${prpLcompelMedical.a029c}" pattern="#"/>" ></td>
						<td class="title" ><input type="text" class="input" style="width: 60px;" name="FeeA029z" value="<fmt:formatNumber value="${prpLcompelMedical.a029z}" pattern="#"/>" ></td>
						<td class="title" ><input type="text" class="input" style="width: 60px;" name="FeeA03" value="<fmt:formatNumber value="${prpLcompelMedical.a03}" pattern="#"/>" ></td>
						<td class="title" ><input type="text" class="input" style="width: 60px;" name="FeeA04" value="<fmt:formatNumber value="${prpLcompelMedical.a04}" pattern="#"/>" ></td>
						<td class="title" ><input type="text" readonly="readonly" class="readonly" style="width: 60px;" name="FeeA" value=""></td>
						<td class="title" >
								<select name="healthHospitalize" id="healthHospitalize" class='common' style="width: 110" >
									<option value="Y" <c:if test="${prpLcompelMedical.healthHospitalize=='Y'}"> selected="selected"</c:if>>
										Y	
									</option>
									<option value="N" <c:if test="${prpLcompelMedical.healthHospitalize=='N'}"> selected="selected"</c:if>>
										N
									</option>
								</select>
						</td>
<!-- 
* mantis： CLM0046 ，處理人員：BK007 蘇哲，需求單編號：CLM0046強制險醫療明細提醒同一身分證號與日期重複時有提醒與之前重複的賠次-start
* 處理過程：
*  PrpLcompensateService 新增 verifyPrpLcompelMedical 方法，提供 verifyMedicalDetail Ajax 使用，驗證同一身分證號與日期重複資訊
*  DAACompelPrpLcompelMedical.js 提供前台驗證，有輸入時提醒與送出驗證及提醒
*  ClaimPrintAction 列印功能新增備註頁面(prpLcompelMedicalComment.jrxml)及調整 項目渲染時的提示 (prpLcompelMedical_subreport0.jrxml) 
-->
						<c:if test="${param.actionType=='EDIT' || param.actionType=='ADD'}">
							<td class="title" ><input type="text" readonly="readonly" class="readonly" style="width: 60px;" name="vMsg" value=""></td>
						</c:if> 
<!--
mantis： CLM0046 ，處理人員：BK007 蘇哲，需求單編號：CLM0046強制險醫療明細提醒同一身分證號與日期重複時有提醒與之前重複的賠次 -end
  -->
					<tr/>
				</c:forEach>
			</tbody>
			<tfoot>
				<tr>
					<td class="title" align="center" colspan="20">
						<c:if test="${param.actionType=='EDIT' || param.actionType=='ADD' || param.actionType=='AMEND'}">
							&nbsp;<input type="button" value="增加" class="button" onclick="insertRow('PrpLcompelMedical',this,'SerialNo');" name="buttonInsert" style="cursor: hand">
							&nbsp;<input type="button" value="刪除" class="button" onclick="deletePrpLcompelMedical();" name="buttonDelete" style="cursor: hand">
							<c:choose>
								<c:when test="${param.actionType=='AMEND'}">
									&nbsp;<input type="button" value="暂存" class="button" onclick="savePrpLcompelMedical('${param.actionType}','2')" name="buttonDelete" style="cursor: hand">
									&nbsp;<input type="button" value="提交" class="button" onclick="savePrpLcompelMedical('${param.actionType}','4')" name="buttonDelete" style="cursor: hand">
								</c:when>
								<c:otherwise>
									&nbsp;<input type="button" value="保存" class="button" onclick="savePrpLcompelMedical('${param.actionType}','2')" name="buttonDelete" style="cursor: hand">
								</c:otherwise>
							</c:choose>
						</c:if>
						&nbsp;<input type="button" value="關閉" class="button" onclick="window.close();" name="buttonDelete" style="cursor: hand">
<!-- 
* mantis： CLM0046 ，處理人員：BK007 蘇哲，需求單編號：CLM0046強制險醫療明細提醒同一身分證號與日期重複時有提醒與之前重複的賠次-start
* 處理過程：
*  PrpLcompensateService 新增 verifyPrpLcompelMedical 方法，提供 verifyMedicalDetail Ajax 使用，驗證同一身分證號與日期重複資訊
*  DAACompelPrpLcompelMedical.js 提供前台驗證，有輸入時提醒與送出驗證及提醒
*  ClaimPrintAction 列印功能新增備註頁面(prpLcompelMedicalComment.jrxml)及調整 項目渲染時的提示 (prpLcompelMedical_subreport0.jrxml) 
-->
						<c:if test="${param.actionType!='EDIT' && param.actionType!='ADD' && param.actionType!='AMEND'}">
							&nbsp;<input type="button" value="列印" class="button" onclick="printPrpLcompelMedical();" name="buttonDelete" style="cursor: hand">
						</c:if>
<!--
mantis： CLM0046 ，處理人員：BK007 蘇哲，需求單編號：CLM0046強制險醫療明細提醒同一身分證號與日期重複時有提醒與之前重複的賠次 -end
  -->
					</td>
				</tr>
			</tfoot>
		</table>
	</form>
</body>
</html>
