<%--
****************************************************************************
* DESC	   ：輸入报案前查询保单号码结果面
* AUTHOR	 ：理赔组
* CREATEDATE ：2004-12-06
* MODIFYLIST ：   id	   Date			Reason/Contents
*		  ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@page import="com.sinosoft.claim.common.ConstantCodes"%>
<%@ include file="/common/taglibs.jsp"%>
<%@page import="com.sinosoft.utiall.blsvr.BLPrpDclass"%>
<%@page import="com.sinosoft.utiall.blsvr.BLPrpDrisk"%>
<%@ page import="com.sinosoft.sysframework.common.datatype.*"%>
<html locale="true">
<head>
<title><s:text name="title.registBeforeEdit.queryPolicyList" /></title>
<%@include file="/common/meta_css.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<script language="javascript">
	function submitForm(field) {
		$(document).blur();
		var strPolicyNo = $(":input[name='PolicyNo']").val();
		var strInsuredName = $(":input[name='InsuredName']").val();
		var strInsuredIdentifyNumber = $(":input[name='InsuredIdentifyNumber']").val();
		var strAppliName = $(":input[name='AppliName']").val();
		var strAppliIdentifyNumber = $(":input[name='AppliIdentifyNumber']").val();	
		var strLicenseNo = $(":input[name='LicenseNo']").val();
		var strDamageDate = $(":input[name='DamageDate']").val();
		var strDamageHour = $(":input[name='DamageHour']").val();
		var strRiskCode = $(":input[name='RiskCode']").val();
		var strHirer = $(":input[name='Hirer']").val();
		var strConstructAddress = $(":input[name='ConstructAddress']").val();
		var visaCodeBI = $(":input[name='visaCodeBI']").val();
		var addressDetailInfo = $(":input[name='addressDetailInfo']").val();
		/*CLM9001 不送 START
		if((trim(strPolicyNo).length>0) ||(trim(strInsuredName).length>0)
			||(trim(strAppliName).length>0)||(trim(strInsuredIdentifyNumber).length>0)
			||(trim(strAppliName).length>0)||(trim(strAppliIdentifyNumber).length>0)
			||(trim(strHirer).length>0)||(trim(strConstructAddress).length>0)
			||(trim(visaCodeBI).length>0)||(trim(addressDetailInfo).length>0)
			||((trim(strLicenseNo).length>0)&& "D"==fm.RiskCategory.value && fm.RiskCode.value != "")){
		}else{
			alert("車險的險種可以只輸入車牌號進行查詢！\n其他險種必須輸入保單號碼、被保險人名稱、被保險人ID、要保人或者要保人ID其中一個！");
			return false;
		}CLM9001 不送 END*/
		if(trim(strDamageDate)==""){
			alert("出險日期不能爲空");
			return false;
		}
		if(trim(strDamageHour)==""){
			alert("出險小時不能爲空");
			return false;
		}
		var re = /((0|1)\d{1})|(2[0-3]{1})/g;
		if(!re.test(strDamageHour)){
			alert("出險小時錄入不正確");
			return false;
		}
		//被保险人全模糊
		if(trim(strInsuredName).length > 0 && fm.InsuredNameSign.value == "*" && trim(strRiskCode).length==0){
			if(trim(strPolicyNo).length==0  && trim(strInsuredIdentifyNumber).length==0
				&& trim(strAppliName).length==0 && trim(strAppliIdentifyNumber).length==0
				&& trim(strRiskCode).length==0 ){
				alert("被保險人全模糊查詢且沒有輸入其他條件，必須輸入險種！");
				return false;
			}
		}
		fm.searchFlag.value="true";//表示查询而非翻页
		field.disabled = true;
		fm.submit();//提交
	}

	function document.onkeydown() { 
		if(event.keyCode==13) { 
			document.getElementById("button").click(); 
			return false; 
		}
	}
	function otherFlag(e) {
		var $registData = $(e).closest("tr[name='registData']");
		var riskCode = $registData.find(":input[name='riskCode']").val();
		var licenseNo = $registData.find(":input[name='licenseNo']").val();
		var endorType = $registData.find(":input[name='endorType']").val();
		var startDate = $registData.find(":input[name='startDate']").val();
		var startHour = $registData.find(":input[name='startHour']").val();
		var endDate = $registData.find(":input[name='endDate']").val();
		var endHour = $registData.find(":input[name='endHour']").val();
		var damageDate = $registData.find(":input[name='damageDate']").val();
		var damageHour = $registData.find(":input[name='damageHour']").val();
		var sstartDate = new Date(startDate.replace(/-/g,"/"));
		sstartDate.setHours(parseInt(startHour),0,0);
		var sendDate = new Date(endDate.replace(/-/g,"/"));
		sendDate.setHours(parseInt(endHour),0,0);
		var sdamageDate = new Date(damageDate.replace(/-/g,"/"));
		sdamageDate.setHours(parseInt(damageHour),0,0);
		var checkflag = true;
		var msg = "";
		if(endorType == "21" || endorType == "98" || endorType == "19"){//全單退保
			msg = endorType == "21" ? "該保單已全單退保！" : (endorType == "98" ? "該保單已全額退保！" : "該保單已註銷保！");
			var validDate = $registData.find(":input[name='validDate']").val();
			var validHour = $registData.find(":input[name='validHour']").val();
			var svalidDate = new Date(validDate.replace(/-/g,"/"));
			svalidDate.setHours(parseInt(validHour==""?"0":validHour),0,0);
			if(svalidDate <= sstartDate){
				alert(msg + "不允許備案！");
				return false;
			} else {
				if( (riskCode == "A01" || riskCode == "B01") && licenseNo == ""){
					alert("出險時保單無車號，請通知出單單位進行車牌號批改作業！");
					return false;
				}
				if(!(sdamageDate > sstartDate && sdamageDate < svalidDate && sdamageDate < sendDate )){
					msg += "於"+getMGDate(validDate) + "日" + validHour + "時生效！";
					msg += "\r\n當前出險日期在保單有效期範圍內！";
					msg += "\r\n是否繼續？";
					return confirm(msg);
				}
			}
		}
		if( (riskCode == "A01" || riskCode == "B01") && licenseNo == ""){
			alert("出險時保單無車號，請通知出單單位進行車牌號批改作業！");
			return false;
		}
		//mantis： CLM0023 ，處理人員： David ，需求單編號： CLM0023  受理即時檢核人員
		if (checkIsPolicyHandlers($('#policyNo').val())) {
			alert("保單服務及業務人員不可備案！") ;
			return false ;
		}
		if(!(sdamageDate >= sstartDate && sdamageDate < sendDate )){
			msg = "當前出險日期不在保單有效期範圍內！";
			msg += "\r\n是否繼續？";
			return confirm(msg);;
		}
		var currDate = new Date();
		if(currDate > sendDate){
			msg = "保單已滿期，請慎重處理！";
			alert(msg);
		}
/*		if (otherFlag.length>2 && otherFlag.substring(2,3)== "2") {
			alert("此保單已滿期退保，請慎重處理。");
		}
		if (otherFlag.length>3 && otherFlag.substring(3,4)== "1") {
			alert("此保單已被註銷，不能備案");
		}
		if (otherFlag.length>4 && otherFlag.substring(4,5)== "1") {
			alert("此保單已遺失，請慎重處理。");
		}
		if (otherFlag.length>5 && otherFlag.substring(5,6)== "1") {
			alert("此保單已終止合約，請慎重處理。");
		} */
		return true;
	}
	$(function(){
		setClassCode("${param.ClassCode}","${param.RiskCode}");
		$(":input[name='ClassCodeSelect']").bind("change",function(){
			var tempValue = $(this).val();
			$(":input[name='ClassCode']").val(tempValue);
			$(":input[name='RiskCode']").val("");
			if(tempValue==""){
				$(":input[name='RiskCodeSelect']").empty();
				$(":input[name='RiskCategory']").val("");
			}else{
				setRiskCode(tempValue,"");
			}
		});
		$(":input[name='RiskCodeSelect']").bind("change",function(){
			$(":input[name='RiskCode']").val($(this).val());
		});
	});
	function setClassCode(classCode,riskCode){
		$.ajax({
			type:"POST",
			url:"${ctx}/getClassCode.do",
			dataType: "html",
			success:function(htmlStr){
				var $ClassCodeSelect = $(":input[name='ClassCodeSelect']");
				$ClassCodeSelect.append(htmlStr);
				if(classCode != ''){
					$ClassCodeSelect.val(classCode);
				}
				if(classCode != ''){
					setRiskCode(classCode,riskCode);
				}
			}
		})
	}
	function setRiskCode(classCode,riskCode){
		$.ajax({
			type:"POST",
			url:"${ctx}/getRiskCode.do",
			data:"classCode="+classCode,
			dataType: "json",
			success:function(d){
				var $RiskCodeSelect = $(":input[name='RiskCodeSelect']");
				$RiskCodeSelect.empty().append(d.htmlStr);
				$(":input[name='RiskCategory']").val(d.riskCategory);
				if(riskCode != ''){
					$RiskCodeSelect.val(riskCode);
				}
			}
		})
	}
	//mantis： CLM0023 ，處理人員： David ，需求單編號： CLM0023  受理即時檢核人員
	function checkIsPolicyHandlers(policyNo) {
		var result ;
		$.ajax({
			type:"POST",
			async: false,
			timeout: 3000,
			url:"${ctx}/checkIsPolicyHandlers.do",
			data:"policyNo="+policyNo,
			dataType: "html",
			success:function(htmlStr){
				result = (htmlStr=="true" ? true : false ) ;
			}
		}) ;
		return result ;
	}
</script>
</head>
<body>
	<form name="fm" action="/claim/registBeforeQuery.do" method="post">
		<input type="hidden" name="editType" value="RegistBeforeQuery" />
		<input type="hidden" name="searchFlag" value="">
		<input type="hidden" name="nodeType" value="regis">
		<table width="100%" border="0" align="center" cellpadding="4" cellspacing="1" class="common">
			<tr>
				<td colspan="4" class="formtitle">
					<s:text name="title.registBeforeEdit.queryPolicy" />
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="db.prpLregist.policyNo" />：
					<%-- 保单号码 --%>
				</td>
				<td class='input'>
					<select class=tag name="PolicyNoSign">
						<option value="=">=</option>
					</select>
					<input type=text name="PolicyNo" class="query" value="<c:out value="${param.PolicyNo}"/>">
				</td>
				<td class='title'>
					<s:text name="db.prpLregist.licenseNo" />：
					<%-- 牌照號碼 --%>
				</td>
				<td class='input'>
					<select class=tag name="LicenseNoSign">
						<option value="=">=</option>
					</select>
					<input type=text name="LicenseNo" class="query" value="<c:out value="${param.LicenseNo}"/>">
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="db.prpLregist.insuredName" />：
					<%-- 被保险人 --%>
				</td>
				<td class='input'>
					<select class=tag name="InsuredNameSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
						<option value="*">*</option>
					</select>
					<input type=text name="InsuredName" class="query" value="<c:out value="${param.InsuredName}"/>">
				</td>
				<td class='title'>
					<s:text name="db.prpCmain.insured" />ID：
					<%-- 身份证号--%>
				</td>
				<td class='input'>
					<select class=tag name="InsuredIdentifyNumberSign">
						<option value="=">=</option>
					</select>
					<input type=text name="InsuredIdentifyNumber" class="query" value="<c:out value="${param.InsuredIdentifyNumber}"/>">
				</td>
				<input type="hidden" name="IDCardFlag" value="Flag">
			</tr>
			<tr>
				<td class='title'>
					<s:text name="db.prpLregist.appliName" />：
					<%-- 要保人名称--%>
				</td>
				<td class='input'>
					<select class=tag name="AppliNameSign">
						<option value="=">=&nbsp;</option>
					</select>
					<input type=text name="AppliName" class="query" value="<c:out value="${param.AppliName}"/>">
				</td>
				<td class='title'>
					<s:text name="db.prpLregist.appliNameCode" />ID：
					<%-- 要保人身份证号--%>
				</td>
				<td class='input'>
					<select class=tag name="AppliIdentifyNumberSign">
						<option value="=">=</option>
					</select>
					<input type=text name="AppliIdentifyNumber" class="query" value="<c:out value="${param.AppliIdentifyNumber}"/>">
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="定作人" />：
					<%-- 定作人--%>
				</td>
				<td class='input'>
					<select class=tag name="HirerSign">
						<option value="=">=</option>
					</select>
					<input type=text name="Hirer" class="query" value="<c:out value="${param.Hirer}"/>">
				</td>
				<td class='title'>
					<s:text name="施工處所" />：
					<%-- 施工處所 --%>
				</td>
				<td class='input'>
					<select class=tag name="ConstructAddressSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
						<option value="*">*</option>
					</select>
					<input type=text name="ConstructAddress" class="query" value="<c:out value="${param.ConstructAddress}"/>">
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="db.prpLregist.startDate" />：
					<%-- 保险起期 --%>
				</td>
				<td class='input'>
					<select class=tag name="StartDateSign">
						<option value=">=">&gt;=</option>
						<option value="=">=&nbsp;</option>
						<option value=">">&gt;&nbsp;</option>
						<option value="<">&lt;&nbsp;</option>
						<option value="<=">&lt;=</option>
					</select>
					<%--mantis： CLM0198，處理人員：CD078，需求單編號：CLM0198 新核心-備案登記出險日期預設為空 --%>
					<rc:rcDate name="StartDate" defaultValue="-1" value="${param.StartDate}" style="width: 60%" />
				</td>
				<td class='title'>
					<s:text name="db.prpLregist.endDate" />：
					<%-- 保险止期 --%>
				</td>
				<td class='input'>
					<select class=tag name="EndDateSign">
						<option value="<=">&lt;=</option>
						<option value="=">=&nbsp;</option>
						<option value=">">&gt;&nbsp;</option>
						<option value="<">&lt;&nbsp;</option>
						<option value=">=">&gt;=</option>
					</select>
					<%--mantis： CLM0198，處理人員：CD078，需求單編號：CLM0198 新核心-備案登記出險日期預設為空 --%>
					<rc:rcDate name="EndDate" defaultValue="2" value="${param.EndDate}" style="width: 60%" />
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="db.prpLregist.damageDate" />：
					<%-- 出险日期 --%>
				</td>
				<td class='input'>
					<select class=tag name="DamageDateSign">
						<option value="=">=&nbsp;</option>
					</select>
					<%--mantis： CLM0198，處理人員：CD078，需求單編號：CLM0198 新核心-備案登記出險日期預設為空 --%>
					<rc:rcDate name="DamageDate" defaultValue="0" value="${param.DamageDate}" style="width: 60%"/>
				</td>
				<td class='title'>
					<s:text name="db.prpLregist.damageHour" />：
					<%-- 出险小时--%>
				</td>
				<td class='input'>
					<select class=tag name="DamageHourSign">
						<option value="=">=&nbsp;</option>
					</select>
					<%--mantis： CLM0207，處理人員：DP0713，需求單編號：新核心-備案登記處理出險小時欄位增加長度檢核 --%>
					<input type=text name="DamageHour" class="query" value="<c:out value="${param.DamageHour}"/>" maxlength="2">
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="db.prpCmain.visaCodeBI" />：
					<%-- 任意保險卡號	--%>
				</td>
				<td class='input'>
					<select class=tag name="visaCodeBISign">
						<option value="=">=&nbsp;</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="visaCodeBI" class="query" value="<c:out value='${param.visaCodeBI}'/>">
				</td>
				<td class='title'>
					<s:text name="db.prpLregist.sequenceNo" />：
					<%-- 流水号（台帳保单） --%>
				</td>
				<td class='input'>
					<select class=tag name="sequenceNoSign">
						<option value="=">=&nbsp;</option>
						<option value="=*">=*</option>
					</select>
					<input type=text name="sequenceNo" class="query" value="<c:out value="${param.sequenceNo}"/>">
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="compensate.dubang.project" />
					<s:text name="user.address" />：
					<%-- 標的物地址   --%>
				</td>
				<td class='input' colspan="3">
					<select class=tag name="addressDetailInfoSign" style="width: 70px">
						<option value="*">*&nbsp;</option>
					</select>
					<input type=text name="addressDetailInfo" class="query" value="<c:out value='${param.addressDetailInfo}'/>" />
				</td>
			</tr>
			<tr>
				<td class='title' align="left">
					<s:text name="db.prpLregist.riskCode" />：
					<%-- 险种代码--%>
				</td>
				<td class='input' colspan="3" align="left">
					<input type="text" readonly="readonly" name="RiskCode" class="readonly" style="width: 70px" value="${param.RiskCode}">
					<input type="hidden" name="RiskCategory" value="${param.RiskCategory}">
					<input type="hidden" name="ClassCode" value="${param.ClassCode}">
					<select name="ClassCodeSelect" class="common" style="width: 250px"></select>
					<select name="RiskCodeSelect" class="common" style="width: 300px"></select>
				</td>
			</tr>
			</tr>
			<tr>
				<td class="title" style="color: red; display: none" colspan="4">
					<s:text name="prompt.schedule.query1" />
					<%--"="符号，必须精确查询。--%>
					<br>
					<s:text name="prompt.schedule.query2" />
					<%--"=*"符号，前匹配後模糊的查询。--%>
					<br>
					<s:text name="regist.query1" />
					<%--"*"符号，全模糊的查询。被保险人采用全模糊查询时，险种代码不能为空！ --%>
				</td>
			</tr>
		</table>
		<script type="text/javascript">
			<%/** 匹配符的默认值 */%>
			$(":input[name='PolicyNoSign']").val("${param.PolicyNoSign}");
			$(":input[name='LicenseNoSign']").val("${param.LicenseNoSign}");
			$(":input[name='InsuredNameSign']").val("${param.InsuredNameSign}");
			$(":input[name='InsuredIdentifyNumberSign']").val("${param.InsuredIdentifyNumberSign}");
			$(":input[name='AppliNameSign']").val("${param.AppliNameSign}");
			$(":input[name='AppliIdentifyNumberSign']").val("${param.AppliIdentifyNumberSign}");
			$(":input[name='StartDateSign']").val("${param.StartDateSign}");
			$(":input[name='EndDateSign']").val("${param.EndDateSign}");
			$(":input[name='DamageDateSign']").val("${param.DamageDateSign}");
			$(":input[name='DamageHourSign']").val("${param.DamageHourSign}");
			$(":input[name='sequenceNoSign']").val("${param.sequenceNoSign}");
			$(":input[name='visaCodeBISign']").val("${param.visaCodeBISign}");
		</script>
		<table width=100%>
			<tr>
				<td height="23" colspan="4" class='button'>
					<input id="button" type=button class='button' value="<s:text name='button.query.value' />" onClick="submitForm(this);">
				</td>
			</tr>
		</table>
		<table class="common" cellpadding="4" cellspacing="1">
			<tr>
				<td colspan="7" class="formtitle">
					<s:text name="title.registBeforeEdit.queryPolicyList" />
				</td>
			</tr>
			<tr>
				<td class="centertitle">
					<s:text name="db.prpLregist.policyNo" />
				</td>
				<td class="centertitle">
					<s:text name="db.prpLregist.insuredName" />
				</td>
				<td class="centertitle">
					<s:text name="db.prpLregist.licenseNo" />
				</td>
				<td class="centertitle">
					<s:text name="db.prpLregist.brandName" />
				</td>
				<td class="centertitle">
					<s:text name="db.prpLregist.startDate" />
				</td>
				<td class="centertitle">
					<s:text name="db.prpLregist.endDate" />
				</td>
				<td class="centertitle">
					<s:text name="regist.prpLregist.riskCodeName" />
				</td>
			</tr>
			<c:forEach var="prpCmain" items="${requestScope.prpCmainList}" varStatus="status">
				<c:choose>
					<c:when test="${status.index%2==0}">
						<tr class="listodd" name="registData">
					</c:when>
					<c:otherwise>
						<tr class="listeven" name="registData">
					</c:otherwise>
				</c:choose>
				<td align="center">
					<a target="fraInterface" href="${ctx}/registBeforeEdit.do?prpCmainPolicyNo=${prpCmain.policyNo}&editType=ADD&damageDate=${prpCmain.damageDate}&damageHour=${prpCmain.damageHour}&insuredCode=${prpCmain.insuredCode}&insuredName=${prpCmain.insuredName}"
						onClick="return otherFlag(this)">
						${prpCmain.policyNo}
					</a>
					<input type="hidden" name="colorFlag" value="${prpCmain.colorFlag}">
					<input type="hidden" name="endorType" value="${prpCmain.endorType}">
					<input type="hidden" name="validDate" value="${prpCmain.validDate}">
					<input type="hidden" name="validHour" value="${prpCmain.validHour}">
					<input type="hidden" name="startDate" value="<fmt:formatDate value="${prpCmain.startDate}" pattern="yyyy-MM-dd"/>">
					<input type="hidden" name="startHour" value="${prpCmain.startHour}">
					<input type="hidden" name="endDate" value="<fmt:formatDate value="${prpCmain.endDate}" pattern="yyyy-MM-dd"/>">
					<input type="hidden" name="endHour" value="${prpCmain.endHour}">
					<input type="hidden" name="othFlag" value="${prpCmain.othFlag}">
					<input type="hidden" name="damageDate" value="${prpCmain.damageDate}">
					<input type="hidden" name="damageHour" value="${prpCmain.damageHour}">
					<input type="hidden" name="riskCode" value="${prpCmain.riskCode}">
					<!-- mantis： CLM0023 ，處理人員： David ，需求單編號： CLM0023  受理即時檢核人員 -->
					<input type="hidden" name="policyNo" id="policyNo" value="${prpCmain.policyNo}">
				</td>
				<td align="center">${prpCmain.insuredName}</td>
				<td align="center">${prpCmain.licenseNo}<input type="hidden" name="licenseNo" value="${prpCmain.licenseNo}"></td>
				<td align="center">${prpCmain.brandName}</td>
				<td align="center" class="mgdate"><fmt:formatDate value="${prpCmain.startDate}" pattern="yyyy-MM-dd"/></td>
				<td align="center" class="mgdate"><fmt:formatDate value="${prpCmain.endDate}" pattern="yyyy-MM-dd"/></td>
				<td align="center">${prpCmain.riskCName}</td>
			</tr>
			</c:forEach>
			<c:choose>
				<c:when test="${empty requestScope.prpCmainList}">
					<tr>
						<td colspan="7" align="center">對不起，沒有找到滿足條件的保單！</td>
					</tr>
				</c:when>
				<c:otherwise>
					<tr class="listtail">
						<td colspan="7" align="center">
							<%@include file="/pages/common/pub/TurnPage.jsp"%>
						</td>
					</tr>
				</c:otherwise>
			</c:choose>
		</table>
	</form>
</body>
<script type="text/javascript">
	/** 西元日期轉民國  */
	$("td").filter(".mgdate").text(function(index,text){
		return getMGDate(text);
	});
	/** 註銷和過期保單標記紅色 */
	$(":input[name='colorFlag'][value='1']").each(function(){
		var $registData = $(this).closest("tr[name='registData']");
		$registData.children("td").find("a").andSelf().wrapInner("<font color='red'/>");
	});
</script>
</html>