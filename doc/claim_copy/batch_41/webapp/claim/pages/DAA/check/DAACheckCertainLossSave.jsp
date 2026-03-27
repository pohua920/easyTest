<%--
****************************************************************************
* DESC       ：通用按钮画面
* AUTHOR     : 中科软
* CREATEDATE ：2004-10-11
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%-- 保存按钮 --%>
<table id="buttonArea1" cellpadding="0" cellspacing="0" width="80%" style="display:" id="tablebutton">
	<!--在不同状态下，按钮的数量是不同的，-->
	<%-- (1)立案登记 " 暂存" " 取消" "已完成","已完成並提交"
  (2)暂存  
  (5) "返回"                  
  --%>
	<tr>
		<%-- 隐藏所按的保存按钮是哪个的标志--%>
		<td></td>
	</tr>
	<tr>
		<logic:notEqual name="prpLcheckDto" property="status" value="4">
			<td class=button style="width: 20%" align="center">
				<!--保存按钮-->
				<input type="button" name=buttonCertainLossSave class='button' value="<s:text name='button.save.value' />" onclick="return saveCertainLossForm(this);">
			</td>
		</logic:notEqual>
		<td class=button style="width: 33%" align="center">
			<!--返回查勘-->
			<input type=button name=buttonCertainReturn class='bigbutton' value="<s:text name='button.returnSurvey.value' />"
				onclick="backSubPage('span_certa');backSubPage('span_check1');backSubPage('span_check2');">
		</td>
		<%-- 返回查勘 --%>
	</tr>
</table>
