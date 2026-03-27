<%--
****************************************************************************
* DESC       ：在页面中增加“巨灾一级代码”、巨灾二级代码”录入域
* AUTHOR     ：中科软
* CREATEDATE ：2013-01-17
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<table class="common" align="center" cellpadding="5" cellspacing="1">
	<tr>
		<td class="common" style="text-align: left" colspan=4>
			<img style="cursor: hand;" src="${ctx }/images/butCollapseBlue.gif" name="LtextImg" onclick="showPage(this,Lloss)">
			<s:text name="regist.prpLregist.catastrophe" />
			<br>
			<table class="common" cellpadding="5" cellspacing="1" id="Lloss" style="display: none">
				<tbody>
					<tr>
						<td class="title" style="width: 10%">
							<s:text name="regist.prpLregist.catastropheCode1" />：
							<%-- 巨災類型 --%>
						</td>
						<td class="input" style="width: 20%">
							<input name="prpCatastropheCode1" class="codecode" style="width: 98%" maxlength=5 value="${prpLregist.catastropheCode1}" ondblclick="code_CodeSelect(this, 'CatastropheCode','0,1','Y');"
								onchange="code_CodeSelect(this, 'CatastropheCode','0,1','Y');" onkeyup="code_CodeSelect(this, 'CatastropheCode','0,1','Y');">
						</td>
						<td class="title" style="width: 10%">
							<s:text name="regist.prpLregist.catastropheName1" />：
							<%-- 巨災名稱 --%>
						</td>
						<td class="input" style="width: 20%">
							<input name="prpCatastropheName1" class="codecode" style="width: 98%" maxlength=30 value="${prpLregist.catastropheName1}">
						</td>
						<td class="title" style="width: 10%">
							<s:text name="regist.prpLregist.catastropheCode" />：
							<%-- 巨災代碼 --%>
						</td>
						<td class="input" style="width: 20%">
							<input name="prpCatastropheName2" class="input" style="width: 98% maxlength=30" value="${prpLregist.catastropheName2}">
						</td>
					</tr>
				</tbody>
			</table>
		</td>
	</tr>
</table>