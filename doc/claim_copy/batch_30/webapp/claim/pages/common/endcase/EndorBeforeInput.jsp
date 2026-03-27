<!--
****************************************************************************
* DESC       ：冲减保额前输入赔案号页面
* AUTHOR     ：理赔组
* CREATEDATE ：2004-11-26
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
-->
<%@ page contentType="text/html; charset=GBK" %>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title><s:text name="menu.finishCase.amountTask" /></title>
<!-- 冲减保额 -->
<!-- 页面样式  -->
<link rel="stylesheet" type="text/css" href="${ctx }/css/Standard.css">
<script language='javascript'>
    /**
     @author 中科软
     @description 校验窗体方法
     @param       无
     @return      boolean,合法返回true,不合法返回false
    */
    function checkForm()
		{
			if(isEmpty(fm.ClaimNo))
			{
				fm.ClaimNo.focus();
				errorMessage("赔案号不能为空!");
				return false;
			}
			else if(trim(fm.ClaimNo.value).length!=22)
			{
				fm.ClaimNo.focus();
				errorMessage("赔案号应为22位长!");
				return false;
			}

			if(!isEmpty(fm.CompensateNo))
			{
			  if(trim(fm.CompensateNo.value).length!=22)
			  {
  				fm.CompensateNo.focus();
  				errorMessage("赔款计算书号应为22位长!");
  				return false;
			  }
			}

			return true;
		}

    /**
     @author 中科软
     @description 提交窗体方法
     @param       无
     @return      无
     @see         checkForm
    */
    function submitForm()
    {
    /*
      if(checkForm()==true)
	  	{
        fm.submit();
      }
      */
       fm.submit();
    }

    function resetForm()
    {
      fm.reset();
    }
  </script>
</head>
<body class="interface">
	<form name="fm" action="${ctx }/endor.do" method="post" onsubmit="return validateForm(this);">
		<table border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td colspan=4 class="formtitle">
					<s:text name="endcase.inputClaimNumber2" />
				</td>
			</tr>
			<!-- 输入赔案号（冲减保额） -->
			<tr>
				<td class="title">
					<s:text name="check.claimNum" />：
				</td>
				<!-- 赔案号 -->
				<td class="input">
					<input type=text name="ClaimNo" class="common" maxlength='21'>
					<img src="${ctx }/images/bgMarkMustInput.jpg">
				</td>
				<td class=title>
					<s:text name="db.prpLcfee.compensateNo" />：
				</td>
				<!-- 赔款计算书号 -->
				<td class="input">
					<input type=text class="common" type='text' name='CompensateNo' maxlength='30'>
				</td>
			</tr>
			<tr>
				<td colspan=2 class=button>
					<input type='button' value='<s:text name="button.next.value" />' class="button" onclick="return submitForm();">
					<!-- 下一步 -->
				</td>
				<td colspan=2 class=button>
					<input type='button' value='<s:text name="button.reset.value" />' class="button" onclick="return resetForm();">
					<!-- 重  置 -->
				</td>
			</tr>
		</table>
		<input type="hidden" name="editType" value="ADD">
	</form>
</body>
</html>