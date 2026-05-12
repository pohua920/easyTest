<%--
****************************************************************************
* DESC       ：录入报案前查询保单号条件果面
* AUTHOR     ： 理赔组
* CREATEDATE ： 2013-03-01
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@page import="com.sinosoft.claim.common.ConstantCodes"%>
<%@ include file="/common/taglibs.jsp"%>
<%@page import="com.sinosoft.utiall.blsvr.BLPrpDclass"%>
<%@page import="com.sinosoft.utiall.blsvr.BLPrpDrisk"%>
<%@page import="com.sinosoft.sysframework.common.datatype.*"%>
<%@page import="java.util.*"%>
<html>
<head>
<title><s:text name="title.registBeforeEdit.queryPolicy" /></title>
<%-- 公用函数 --%>
<%@include file="/common/meta_css.jsp"%>
<script src="/claim/common/js/Common.js"></script>
<%@include file="/common/meta_js.jsp"%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<script language="javascript">
    function submitForm(field) {
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
          }CLM9001 不送 END
        */
        if(trim(strDamageDate)==""){
           alert("出險日期不能爲空");
           return false;
        }
        if(trim(strDamageHour)==""){
           alert("出險小時不能爲空");
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
        field.disabled = true;
        fm.submit();//提交
    }
    $(document).ready(function(){
    	setClassCode("","");
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
    	$(document).keydown(function (e) {
		    var e = e?e:window.event;
		    var code = e.keyCode?e.keyCode:e.which;
		    if (code == 13) {
		        $("#button").click();
		    }
		})
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
 </script>
</head>
<body onload="initPage();">
    <form name="fm" action="/claim/regist/registBeforeQuery.do" method="post" onsubmit="return validateForm(this);">
    	<input type="hidden" name="editType" value="RegistBeforeQuery" />
    	<input type="hidden" name="searchFlag" value= "true">
    	<input type="hidden" name="nodeType" value="regis">
        <table width="100%" border="0" align="center" cellpadding="4" cellspacing="1" class="common">
            <tr>
                <td colspan="4" class="formtitle">
                    <s:text name="title.registBeforeEdit.queryPolicy" /><%-- 查詢保單訊息 --%>
                </td>
            </tr>
            <tr>
                <td class='title'><s:text name="db.prpLregist.policyNo" />：<%-- 保单号码 --%></td>
                <td class='input'>
                    <select class=tag name="PolicyNoSign">
                        <option value="=">=</option>
                    </select>
                    <input type=text name="PolicyNo" class="query" >
                </td>
                <td class='title'><s:text name="db.prpLregist.licenseNo" />：<%-- 牌照號碼 --%></td>
                <td class='input'>
                    <select class=tag name="LicenseNoSign">
                        <option value="=">=</option>
                    </select>
                    <input type=text name="LicenseNo" class="query">
                </td>
            </tr>
            <tr>
                <td class='title'><s:text name="db.prpLregist.insuredName" />：<%-- 被保险人 --%></td>
                <td class='input'>
                    <select class=tag name="InsuredNameSign">
                        <option value="=">=</option>
                        <option value="=*">=*</option>
                        <option value="*">*</option>
                    </select>
                    <input type=text name="InsuredName" class="query">
                </td>
                <td class='title'><s:text name="db.prplcheck.identifyNumber" />：<%-- 身份证号--%></td>    
                <td class='input'>
                    <select class=tag name="InsuredIdentifyNumberSign">
                        <option value="=">=</option>
                    </select>
                    <input type=text name="InsuredIdentifyNumber" class="query">
                </td>
                <input type="hidden" name="IDCardFlag" value="Flag">
            </tr>
            <tr>
                <td class='title'><s:text name="db.prpLregist.appliName" />：<%-- 要保人名称--%></td>
                <td class='input'>
                    <select class=tag name="AppliNameSign">
                        <option value="=">=&nbsp;</option>
                    </select>
                    <input type=text name="AppliName" class="query">
                </td>
                <td class='title'><s:text name="db.prpLregist.appliNameCode" />ID：<%-- 要保人身份证号--%></td>
                <td class='input'>
                    <select class=tag name="AppliIdentifyNumberSign">
                        <option value="=">=</option>
                    </select>
                    <input type=text name="AppliIdentifyNumber" class="query">
                </td>
            </tr>
            <tr>
                <td class='title'><s:text name="定作人" />：<%-- 定作人--%></td>    
                <td class='input'>
                    <select class=tag name="HirerSign">
                        <option value="=">=</option>
                    </select>
                    <input type=text name="Hirer" class="query">
                </td>
                <td class='title'><s:text name="施工處所" />：<%-- 施工處所 --%></td>
                <td class='input'>
                    <select class=tag name="ConstructAddressSign">
                        <option value="=">=</option>
                        <option value="=*">=*</option>
                        <option value="*">*</option>
                    </select>
                    <input type=text name="ConstructAddress" class="query">
                </td>
            </tr>
            <tr>
                <td class='title'><s:text name="db.prpLregist.startDate" />：<%-- 保险起期 --%></td>
                <td class='input'>
                    <select class=tag name="StartDateSign">
                        <option value=">=">&gt;=</option>
                        <option value="=">=&nbsp;</option>
                        <option value=">">&gt;&nbsp;</option>
                        <option value="<">&lt;&nbsp;</option>
                        <option value="<=">&lt;=</option>
                    </select>
                    <rc:rcDate name="StartDate" defaultValue="-1" style="width: 60%"/>
                </td>
                <td class='title'><s:text name="db.prpLregist.endDate" />：<%-- 保险止期 --%></td>
                <td class='input'>
                    <select class=tag name="EndDateSign">
                        <option value="<=">&lt;=</option>
                        <option value="=">=&nbsp;</option>
                        <option value=">">&gt;&nbsp;</option>
                        <option value="<">&lt;&nbsp;</option>
                        <option value=">=">&gt;=</option>
                    </select>
                    <rc:rcDate name="EndDate" defaultValue="2" style="width: 60%"/>
                </td>
            </tr>
            <tr>
                <td class='title'><s:text name="db.prpLregist.damageDate" />：<%-- 出险日期 --%></td>
                <td class='input'>
                    <select class=tag name="DamageDateSign">
                        <option value="=">=&nbsp;</option>
                    </select>
                    <%--mantis： CLM0198，處理人員：CD078，需求單編號：CLM0198 新核心-備案登記出險日期預設為空 Start--%>
<%--                     <rc:rcDate name="DamageDate" defaultValue="0" style="width: 60%"/> --%>
                    <rc:rcDate name="DamageDate" defaultValue="" style="width: 60%"/>
                </td>
                <td class='title'><s:text name="db.prpLregist.damageHour" />：<%-- 出险小时--%></td>
                <td class='input'>
                    <select class=tag name="DamageHourSign">
                        <option value="=">=&nbsp;</option>
                    </select>
                    <%
                    	Calendar cal = Calendar.getInstance();
                    	int d = cal.get(Calendar.HOUR_OF_DAY);
                    %>
<%--                     <input type=text name="DamageHour" class="query" value="<%=(d < 10 ? "0":"")+d%>"> --%>
					<%--mantis： CLM0207，處理人員：DP0713，需求單編號：新核心-備案登記處理出險小時欄位增加長度檢核 --%>
                    <input type=text name="DamageHour" class="query" value="" maxlength="2">
					<%--mantis： CLM0198，處理人員：CD078，需求單編號：CLM0198 新核心-備案登記出險日期預設為空 End--%>
                </td>
            </tr>
            <tr>
                <td class='title'><s:text name="db.prpCmain.visaCodeBI" />：<%-- 任意保險卡號    --%></td>
                <td class='input'>
                	<select class=tag name="visaCodeBISign">
                        <option value="=">=&nbsp;</option>
                        <option value="=*">=*</option>
                    </select>
                    <input type=text name="visaCodeBI" class="query">
                </td>
                <td class='title'><s:text name="db.prpLregist.sequenceNo" />：<%-- 流水号（台帳保单） --%></td>
                <td class='input'>
                    <select class=tag name="sequenceNoSign">
                        <option value="=">=&nbsp;</option>
                        <option value="=*">=*</option>
                    </select>
                    <input type=text name="sequenceNo" class="query">
                </td>
            </tr>
            <tr>
                <td class='title'><s:text name="compensate.dubang.project" /><s:text name="user.address" />：<%-- 標的物地址   --%></td>
                <td class='input' colspan="3">
                	<select class=tag name="addressDetailInfoSign" style="width: 70px">
                        <option value="*">*&nbsp;</option>
                    </select>
                    <input type=text name="addressDetailInfo" class="query">
                </td>
            </tr>
            <tr>
                <td class='title' align="left" ><s:text name="db.prpLregist.riskCode" />：<%-- 险种代码--%></td>
                <td class='input' colspan="3" align="left">
                    <input type="text" readonly="readonly" name="RiskCode" class="readonly" style="width: 70px" >
                    <input type="hidden" name="RiskCategory">
                    <input type="hidden" name="ClassCode">
                    <select name="ClassCodeSelect" class="common" style="width: 250px"></select>
                    <select name="RiskCodeSelect" class="common" style="width: 300px"></select>
                </td>
            </tr>
            </tr>
            <tr>
                <td class="title" style="color: red" colspan="4">
                    <s:text name="prompt.schedule.query1" /><%--"="符号，必须精确查询。--%><br>
                    <s:text name="prompt.schedule.query2" /><%--"=*"符号，前匹配後模糊的查询。--%><br>
                    <s:text name="regist.query1" /><%--"*"符号，全模糊的查询。被保险人采用全模糊查询时，险种代码不能为空！ --%>
                </td>
            </tr>
        </table>
        <table width=100%>
            <tr>
                <td class='button' colspan="4">
                    <input id="button" type="button" class='button' value="<s:text name='button.query.value' />" onClick="submitForm(this);">
                </td>
            </tr>
        </table>
    </form>
</body>
</html>
