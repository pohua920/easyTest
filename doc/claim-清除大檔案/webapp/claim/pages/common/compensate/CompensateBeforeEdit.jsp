<%--
****************************************************************************
* DESC       ：录入实赔前输入赔案号页面
* AUTHOR     ： 理赔组
* CREATEDATE ： 2013-09-10
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK" %>
<%
  //根据险别显示免赔条件
  String strRiskCode = request.getParameter("riskCode");
  String strDisplay  = "";
  if (strRiskCode!=null&&strRiskCode.equals("DAS"))
    strDisplay = "none";
%>
<script language='javascript'>
	function loadForm()
	{
		fm.ATimes.style.display = "none";
		fm.BTimes.style.display = "none";
		fm.VisaNum.style.display = "none";
	}
</script>
<html:html locale="true">
<head>
  <title><s:text name="title.compensateBeforeEdit.titleName"/></title><!-- 查询实赔信息 -->
  <%-- 页面样式  --%>
  <link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
  
    <script language='javascript'>
      /**
       @author      任轶群
       @description 校验
       @param       无
       @return      boolean,合法返回true,不合法返回false
      */
      function checkForm()
      {
      	if(isEmptyField(fm.ClaimNo))
      	{
      		fm.ClaimNo.focus();
      		errorMessage("赔案号不能为空!");
      		return false;
      	}
      	/*
      	else if(trim(fm.ClaimNo.value).length!=22)
      	{
      		fm.ClaimNo.focus();
      		errorMessage("赔案号应为22位长!");
      		return false;
      	}
      	*/
      	if(fm.APaidTimes.checked == true)
      	{
      		if(isEmptyField(fm.ATimes))
      		{
      			fm.ATimes.focus();
      			errorMessage("赔款次数不能为空!");
      			return false;
      		}
      		if(fm.ATimes.value=='0')
      		{
      			fm.ATimes.focus();
      			errorMessage("赔款次数不能为0!");
      			return false;
      		}
      	}

      	if(fm.BPaidTimes.checked == true)
      	{
      		if(isEmptyField(fm.BTimes))
      		{
      			fm.BTimes.focus();
      			errorMessage("赔款次数不能为空!");
      			return false;
      		}
      		if(fm.BTimes.value=='0')
      		{
      			fm.BTimes.focus();
      			errorMessage("赔款次数不能为0!");
      			return false;
      		}
      	}

      	if(fm.NoVisaNum.checked == true)
      	{
      		if(isEmptyField(fm.VisaNum))
      		{
      			fm.VisaNum.focus();
      			errorMessage("单证个数不能为空!");
      			return false;
      		}
      		if(fm.VisaNum.value=='0')
      		{
      			fm.VisaNum.focus();
      			errorMessage("单证个数不能为0!");
      			return false;
      		}
      	}
      	return true;
      }

      /**
       @author      任轶群
       @description 获得免赔条件方法
       @param       无
       @return      无
      */
      function getDeductibleTerm()
      {
        var strIsSingle    = "0";
        var strIsFindThirt = "0";
        var strIsSafeLoad  = "0";
        var strIsLoadAccid = "0";
        var strIsNoDriver  = "0";
        var strAPaidTimes  = "0";
        var strBPaidTimes  = "0";
        var strNoVisaNum   = "0";
        var strIsNoKey     = "0";
        var strIsGlassCrash= "0";

        if( fm.IsSingle.checked == true )
        {strIsSingle = "1";}
        if( fm.IsFindThirt.checked == true )
        {strIsFindThirt = "1";}
        if( fm.IsSafeLoad.checked == true )
        {strIsSafeLoad = "1";}
        if( fm.IsLoadAccid.checked == true )
        {strIsLoadAccid = "1";}
        if( fm.IsNoDriver.checked == true )
        {strIsNoDriver = "1";}
        if( fm.APaidTimes.checked == true )//Modify By sunhao 2004-09-15
        {strAPaidTimes = fm.ATimes.value;}
        if( fm.BPaidTimes.checked == true )//Modify By sunhao 2004-09-15
        {strBPaidTimes = fm.BTimes.value;}
        if( fm.NoVisaNum.checked == true )
        {strNoVisaNum = fm.VisaNum.value;}
        if( fm.IsNoKey.checked == true )
        {strIsNoKey = "1";}
        if( fm.IsGlassCrash.checked == true )
        {strIsGlassCrash = "1";}

        fm.DeductibleTerm.value = strIsSingle + strIsFindThirt + strIsSafeLoad + strIsLoadAccid
                                + strIsNoDriver + strAPaidTimes + strBPaidTimes + strNoVisaNum + strIsNoKey + strIsGlassCrash;

      }


      /**
       @author      任轶群
       @description 提交方法
       @param       无
       @return      无
      */
      function submitForm()
      {

        if(!checkForm())
        {
          return false;
        }
        getDeductibleTerm();
        fm.submit();
      }

      //Modify By sunhao begin 2004-09-15
			function displayAPaidTimes()
			{
				if( fm.ATimes.style.display == "")
				{
					fm.ATimes.style.display = "none";
				}
			  else
				{
					fm.ATimes.style.display = "";
				}

			}

			function displayBPaidTimes()
			{
				if( fm.BTimes.style.display == "")
				{
					fm.BTimes.style.display = "none";
				}
			  else
				{
					fm.BTimes.style.display = "";
				}

			}
			//Modify By sunhao end 2004-09-15

			function displayNoVisaNum()
			{
				if( fm.VisaNum.style.display == "" )
				{
					fm.VisaNum.style.display = "none";
				}
				else
				{
					fm.VisaNum.style.display = "";
				}
			}
    </script>
</head>

<body  onload="initPage();loadForm();">
<form name="fm" action="/claim/compensateBeforeEdit.do"  method="post" onsubmit="return validateForm(this);">

    <table  border="0" align="center" cellpadding="5" cellspacing="1"  class="common">
    <tr><td colspan=2 class="formtitle"><s:text name="compensate.enterClaimNumber"/></td></tr><!-- 输入赔案号（赔款计算书） -->
      <tr>
        <td class='title2' ><s:text name="check.claimNum"/>:</td><!-- 赔案号 -->
        <td class='input2' >
          <input type=text name="ClaimNo" class="readonly" readonly value="<%= request.getParameter("ClaimNo") %>">
          <input type="hidden" name="swfLogFlowID" value="<%= request.getParameter("swfLogFlowID") %>">
          <input type="hidden" name="swfLogLogNo" value="<%= request.getParameter("swfLogLogNo") %>">
          <input type="hidden" name="status" value="<%= request.getParameter("status") %>">
          <input type="hidden" name="riskCode" value="<%= request.getParameter("riskCode") %>">
          <input type="hidden" name="editType" value="<%= request.getParameter("editType") %>">
          <input type="hidden" name="nodeType" value="<%= request.getParameter("nodeType") %>">
          <input type="hidden" name="nodeType" value="<%= request.getParameter("nodeType") %>">
          <input type="hidden" name="businessNo" value="<%= request.getParameter("businessNo") %>">
          <input type="hidden" name="caseType" value="<%= request.getParameter("caseType") %>">

        </td>
      </tr>
         <tr style="display:'<%=strDisplay%>'">
          <td class='title2'><s:text name="compensate.franchiseConditions"/>：</td><!-- 免赔条件 -->
          <td class='input2'>
            <input type="checkbox" name="IsSingle"    value="IsSingle"><s:text name="compensate.unilateralAccidentAccident"/><br><!-- 单方肇事事故 -->
            <input type="checkbox" name="IsFindThirt" value="IsFindThirt"><s:text name="compensate.unableFindThirdParty"/><br><!-- 无法找到第三方 -->
            <input type="checkbox" name="IsSafeLoad"  value="IsSafeLoad"><s:text name="compensate.violationProvisionsSafeLoad"/><br><!-- 违反安全装载规定 -->
            <input type="checkbox" name="IsLoadAccid" value="IsLoadAccid"><s:text name="compensate.violationProvisionsSafeLoadAccident"/><br><!-- 因违反安全装载规定导致保险事故发生 -->
            <input type="checkbox" name="IsNoDriver"  value="IsNoDriver"><s:text name="compensate.nonConventionalDriversAccident"/><br><!-- 非约定驾驶人员使用保险车辆发生保险事故的 -->
            <input type="checkbox" name="APaidTimes"   value="APaidTimes" onclick="displayAPaidTimes()"><s:text name="compensate.sameInsuranceCoverageOfDanger"/><!-- 同一保险年度内车损险(A)出险次数 --><input class="common" type='text' name='ATimes' maxlength='1' style="width:30"><br>
            <input type="checkbox" name="BPaidTimes"   value="BPaidTimes" onclick="displayBPaidTimes()"><s:text name="compensate.sameInsuranceAnnualThirdParty"/><!-- 同一保险年度内第三者责任险(B)出险次数 --><input class="common" type='text' name='BTimes' maxlength='1' style="width:30"><br>
            <input type="checkbox" name="NoVisaNum"   value="NoVisaNum" onclick="displayNoVisaNum()"><s:text name="compensate.lackRelevantDocumentNumber"/><!-- 缺少相关单证个数(盗抢险) --><input class="common" type='text' name='VisaNum' maxlength='1' style="width:30"><br>
            <input type="checkbox" name="IsNoKey"     value="IsNoKey"><s:text name="compensate.lackCarKeys"/><br><!-- 缺少车钥匙(盗抢险) -->
            <input type="checkbox" name="IsGlassCrash" value="IsGlassCrash"><s:text name="claim.glassAloneBro"/><br><!-- 玻璃单独破碎 -->
            <input class="common" type='hidden' name='DeductibleTerm'>
          </td>
        </tr>

      <tr>
        <td class='button' colspan="2"  style="align:center">
          <input type="button" class='button' value="<s:text name="button.next.value"/>" onclick="submitForm();"><!-- 下一步 -->
        </td>
      </tr>
    </table>


  </form>

      <%--modify by wangli add start 20050417 --%>
            <%-- 赔案号对应赔款计算书列表 --%>
      <%@include file="/common/compensate/CompensateMainBeforeEdit.jsp" %>
      <%--modify by wangli add end 20050417 --%>

</body>
</html:html>



