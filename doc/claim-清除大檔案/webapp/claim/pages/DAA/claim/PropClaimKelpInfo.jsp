<%--
****************************************************************************
* DESC       ：在页面中增加“巨灾一级代码”、巨灾二级代码”录入域
* AUTHOR     ：中科软
* CREATEDATE ：2013-01-11
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------

****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<table class="common" align="center" style="width: 100%">
	<tr>
		<td class="common" style="text-align: left" colspan=4>
			<img style="cursor: hand;" src="/claim/images/butCollapseBlue.gif" name="LtextImg" onclick="showPage(this,Lloss)">
			<s:text name="regist.prpLregist.catastrophe" />
			<%-- 巨灾信息 --%>
			<br> <span id="Lloss" style="display: none">
				<table class="common" cellpadding="5" cellspacing="1">
					<tbody>
						<tr>
							<td class="title" style="width: 10%">
								<s:text name="regist.prpLregist.catastropheCode1" />：
							</td>
							<%-- 巨灾类型 --%>
							<td class="input" style="width: 15%">
								<input id="prpCatastropheCode1" name="prpCatastropheCode1" class="codecode" style="width: 98%" maxlength=5 description="出险原因" value="${prpLclaim.catastropheCode1}"
									ondblclick="code_CodeSelect(this, 'CatastropheCode','0,1','Y','Y');" onchange="code_CodeChange(this, 'CatastropheCode','0,1','Y','Y');"
									onkeyup="code_CodeSelect(this, 'CatastropheCode','0,1','Y','Y');">
							</td>
							<td class="title" style="width: 10%">
								<s:text name="regist.prpLregist.catastropheName1" />：
							</td>
							<%-- 巨灾名称 --%>
							<td class="input" style="width: 15%">
								<input id="prpCatastropheName1" name="prpCatastropheName1" class="codecode" style="width: 98%" maxlength=30 description="出险原因" value="${prpLclaim.catastropheName1}"
									ondblclick="code_CodeSelect(this, 'CatastropheCode','-1,0','Y','N');" onchange="code_CodeChange(this, 'CatastropheCode','-1,0','Y','N');"
									onkeyup="code_CodeSelect(this, 'CatastropheCode','-1,0','Y','N');">
							</td>
							<td class="title" style="width: 10%">
								<s:text name="regist.prpLregist.catastropheCode" />：<%-- 巨災代碼 --%>
							</td>
							<td class="input" style="width: 15%">
								<input id="prpCatastropheName2" name="prpCatastropheName2" class="common" style="width: 98%" maxlength=30 description="巨災代號" value="${prpLclaim.catastropheName2}">
								<%-- 巨災代號 --%>
							</td>
						</tr>
					</tbody>
				</table>
			</span>
		</td>
	</tr>
</table>