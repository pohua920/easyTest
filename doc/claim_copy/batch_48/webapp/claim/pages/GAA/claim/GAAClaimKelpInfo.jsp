<%--
****************************************************************************
* DESC       ：在页面中增加“巨灾一级代码”、巨灾二级代码”录入域
* AUTHOR     ：理赔组
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<table class="common" align="center" style="width: 100%">
	<tr>
		<td class="subformtitle" style="text-align: left" colspan=4>
			<img style="cursor: hand;" src="/claim/images/butCollapseBlue.gif" name="LtextImg" onclick="showPage(this,Lloss)">
			<s:text name="regist.prpLregist.catastrophe" />
			<%--巨灾信息  --%>
			<br>
			<table class="common" align="center" id="Lloss" style="display: none">
				<tbody>
					<tr>
						<td class="title" style="width: 25%">
							<s:text name="claim.catastropLevelCode" />
							：
						</td>
						<%-- 巨灾一级代码 --%>
						<td class="input" style="width: 25%">
							<input name="prpCatastropheCode1" class="codecode" style="width: 98%" maxlength=5 description="巨灾一级代码" value="${prpLclaimDto.catastropheCode1}" ondblclick="code_CodeSelect(this, 'DamageCode');"
								onkeyup="code_CodeSelect(this, 'DamageCode');">
						</td>
						<td class="title" width="25%">
							<s:text name="undwrt.CatNames" />
						</td>
						<%-- 巨灾名称 --%>
						<td class="input" style="width: 25%">
							<input name="prpCatastropheName1" class="codename" style="width: 98%" maxlength=30 description="巨灾名称" value="${prpLclaimDto.catastropheName1}"
								ondblclick="code_CodeSelect(this, 'DamageCode','-1','always','none','post');" onkeyup="code_CodeSelect(this, 'DamageCode','-1','always','none','post');">
						</td>
					</tr>
					<tr>
						<td class="title">
							<s:text name="claim.catastropLevelCode2" />
						</td>
						<%-- 巨灾二级代码 --%>
						<td class="input">
							<input type="text" name="prpCatastropheCode2" maxlength=5 class="input" style="width: 98%"></input>
						</td>
						<td class="title">
							<s:text name="undwrt.CatNames" />
						</td>
						<%-- 巨灾名称 --%>
						<td class="input">
							<input type="text" name="prpCatastropheName2" maxlength=30 class="input" style="width: 98%"></input>
						</td>
					</tr>
				</tbody>
			</table>
		</td>
	</tr>
</table>