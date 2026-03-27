<%--
****************************************************************************
* DESC       ：回访信息录入/修改页面
* AUTHOR     ：理赔项目组
* CREATEDATE ：2005-03-24
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK" %>
<%@ include file="/common/taglibs.jsp"%>
<html>
  <head>
   <title><s:text name="title.dangerUnitBeforeEdit.RiskUnitInformation"/></title><%-- 查看危险单位子信息 --%>
<SCRIPT LANGUAGE="JavaScript">
	function selectDangerUnit(){
		var fm = DangerUnitForm;
		var openerIndex = DangerUnitForm.openerIndex.value;
	  	var pageType    = DangerUnitForm.pageType.value;
	  	var dangerNo = "1";
	  	var checkedFlag = false;
	  	var UnitDangerNo = document.getElementsByName("UnitDangerNo");
		for( i = 1;i<UnitDangerNo.length; i++){
			if(DangerUnitForm.checkDanger[i].checked == true){	
				dangerNo = DangerUnitForm.UnitDangerNo[i].value;
				checkedFlag = true;
				break;
			}
		}
		if(UnitDangerNo.length<2){
			checkedFlag = true;
		}
		if(pageType=="ClaimLoss"){
			window.opener.fm.prpLclaimLossDangerNo[openerIndex].value=dangerNo;
		}else if(pageType =="CompensateLloss"){
			window.opener.fm.prpLlossDtoDangerNo[openerIndex].value=dangerNo;
		}else if(pageType == "PersonLoss"){
			window.opener.fm.prpLpersonLossDangerNo[openerIndex].value=dangerNo;
		}else if(pageType == "CompensateCharge"){
			window.opener.fm.prpLchargeDangerNo[openerIndex].value=dangerNo;
		}
		if(checkedFlag){
			window.close();
			return;
		}
		alert("请选择危险单位号");
	}
</SCRIPT>
    <link rel="stylesheet" type="text/css" href="${ctx }/css/Standard.css">
  </head>

<body>
<form name="DangerUnitForm" >
	 <input type="hidden" name="checkDanger" >
	 <input type="hidden" name="UnitDangerNo" >
	 <input type= "hidden" name="openerIndex" value="${openerIndex }">
	 <input type= "hidden" name="pageType" value="${pageType}">
	<table class="common" cellpadding="5" cellspacing="1" align="center" id="PrpUnitInfo">
    <c:if test="${dangerUnitList!=null}">
    	<c:forEach var="cDangerDto" items="${dangerUnitList}">
        <tr class=listtitle>
        	<td colspan="8" ><s:text name="claim.dangerousUnitInfo"/></td><%-- 危险单位信息 --%>
   			</tr>
   			<tr class=listtitle>
   				<td width='5%'><s:text name="regist.prpLregist.check"/></td><%-- 选择 --%>
          <td width='5%'><s:text name="dangerUnit.RiskNumber"/></td><%-- 危险单位号 --%>
          <td width='20%'><s:text name="user.address"/></td> <%-- 地址 --%>
          <td width='20%'><s:text name="dangerUnit.RiskDescription"/></td><%-- 风险描述 --%>
          <td width='5%'><s:text name="dangerUnit.Share"/></td><%-- 占比 --%>
          <td width='10%'><s:text name="regist.prpLregist.currency"/></td><%-- 币别 --%>
          <td width='10%'><s:text name="db.prpCcargoDetail.sumAmount"/></td><%-- 保额 --%>
          <td width='10%'><s:text name="db.prpCcargoDetail.sumPremium"/></td><%-- 保费 --%>
        </tr>
    	<tr class="common">
	    	 <td>
	    	 	 <input type="radio" name="checkDanger" 
	    	 	 value="${cDangerDto.dangerNo}"></td>
	    	 <td>
	    	 	  <input type="text" class="formtitle1"  name="UnitDangerNo" 
	    	 	  value="${cDangerDto.dangerNo}"></td>
        <td> 
        	<input type="text" class="formtitle1"  name="UnitAddressName" 
        	value="${cDangerDto.addressName}"></td>
        <td>
        	<input type="text" class="formtitle1"  name="UnitRiskLevelDesc" 
        	value="${cDangerDto.dangerDesc}"></td>
        <td>
        	<input type="text" class="formtitle1"  name="UnitDangerShare" 
        	value="${cDangerDto.dangerShare}"></td>
        <td>
        	<input type="text" class="formtitle1"  name="UnitCurrency" 
        	value="${cDangerDto.currency}"></td>
        <td>
        	<input type="text" class="formtitle1"  name="UnitAmount" 
        	value='<fmt:formatNumber value="${cDangerDto.amount}" pattern="#"/>'></td>
        <td>
        	<input type="text" class="formtitle1"  name="UnitPremium" 
        	value='<fmt:formatNumber value="${cDangerDto.premium}" pattern="#"/>'></td>
      </tr>
	<tr>
	<td colspan = '8'><s:text name="dangerUnit.OriginalInformation"/>:</td></tr><%-- 原始标的信息 --%>
  <tr class=listtitle>
   				<td></td>
          <%--<td>序号</td>--%>
          <td ><s:text name="dangerUnit.RiskClassification"/></td><%-- 险别归类 --%>
          <td ><s:text name="regist.prpLregist.itemName"/></td><%-- 标的名称 --%>
          <td><s:text name="title.prepayBeforeEdit.editPrepay"/><s:text name="regist.prpLregist.currency"/></td><%-- 币别 --%>
          <td><s:text name="title.prepayBeforeEdit.editPrepay"/><s:text name="db.prpCcargoDetail.sumAmount"/></td><%-- 保额 --%>
          <td><s:text name="title.prepayBeforeEdit.editPrepay"/><s:text name="db.prpCcargoDetail.sumPremium"/></td><%-- 保费 --%>
          <td colspan="2"></td>
        </tr>
      <c:forEach items="${cDangerDto.dangerItemList}" var="prpCdangerItemDto">
	       <tr class="common">
	       	<td></td>
	    	 <%--<td>
	    	 	  <input type="text" class="formtitle1"  name="serialNo" 
	    	 	  value="${prpCdangerItemDto.serialNo}"></td>--%>
        <td> 
        	<input type="text" class="formtitle1"  name="riskCode" 
        	value="${prpCdangerItemDto.kindName}"></td>
        <td>
        	<input type="text" class="formtitle1"  name="itemDetailName" 
        	value="${prpCdangerItemDto.itemDetailName}"></td>
        <td>
        	<input type="text" class="formtitle1"  name="currency" 
        	value="${prpCdangerItemDto.currency}"></td>
        <td>
        	<input type="text" class="formtitle1"  name="amount" 
        	value='<fmt:formatNumber value="${prpCdangerItemDto.amount}" pattern="#"/>'></td>
        <td>
        	<input type="text" class="formtitle1"  name="premium" 
        	value='<fmt:formatNumber value="${prpCdangerItemDto.premium}" pattern="#"/>'>
        </td>
        <td colspan="2"></td>
      </tr>
     </c:forEach>
   	</c:forEach>
    </c:if>
  </td>
  </tr>
   <tr class="common">
        <td colspan='10'>
        	<input type="button" name="submit" value="<s:text name='button.determine.value'/>" class='button' onclick = "selectDangerUnit();" ></td><%-- 确 定 --%>
    </tr>
  </table>  
</body>
</html>
