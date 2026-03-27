<%--

****************************************************************************

* DESC       ：非车险报案录入画面关联抄单打印

* AUTHOR     ： wangli

* CREATEDATE ： 2005-05-28   

* MODIFYLIST ：   Name       Date            Reason/Contents

*          ------------------------------------------------------

****************************************************************************

--%>
<%@ page contentType="text/html; charset=GBK"%>
<script language="javascript">
	/**

	 * printType 调用打印表类型

	 */

	//打印提交按钮
	function submitRegistForm(printType) {

		var registNo = fm.prpLregistRegistNo.value;

		// modify by liping start
		//if(registNo==''){

		//alert("无报案号，无法打印");

		//return false;

		// }
		// modify by liping end
		// alert(registNo);

		//strUrl = "/claim/ClaimPrint.do?printType="+fm.PrintType.value+"&CompensateNo=" + fm.BizNo.value; 

		var strUrl = "/claim/ClaimPrint.do?printType=" + printType
				+ "&RegistNo=" + registNo;

		printWindow(strUrl, "列印1");

	}

	//显示打印窗口

	function printWindow(strURL, strWindowName)

	{
		//add print liudaoping 2013-04-15
		//alert("【列印】功能屬於客制化需求，暫未開發，請知悉！");
		return false;
		var pageWidth = screen.availWidth - 10;

		var pageHeight = screen.availHeight - 30;

		if (pageWidth < 100)

			pageWidth = 100;

		if (pageHeight < 100)

			pageHeight = 100;

		var newWindow = window
				.open(
						strURL,
						strWindowName,
						'width='
								+ pageWidth
								+ ',height='
								+ pageHeight
								+ ',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1.resizable=1,status=0');

		newWindow.focus();

		return newWindow;

	}
</script>
<table border="0" align="center" cellpadding="5" cellspacing="1" class="common">
	<tr>
		<td class="common" align="center">
			<input type=button name=registPrint value="<s:text name="regist.modifyState.registPrint"/>" class='bigbutton' onclick="submitRegistForm('CopyPrint');"><%--抄单列印 --%>
		</td>
	</tr>
</table>
