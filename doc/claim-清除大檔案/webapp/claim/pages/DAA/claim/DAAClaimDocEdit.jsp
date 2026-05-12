<%--
****************************************************************************
* DESC       ：添加单证页面
* AUTHOR     ：     zhangpeng
* CREATEDATE ： 2004-04-15
* MODIFYLIST ：   Name       Date            Reason/Contents
*               wuxiaodong  20050907       增加代码选择的onchange事件，同时支持名称与代码的相互选择
*          ------------------------------------------------------
*              lixiang       2004-06-17
****************************************************************************
--%>
<!--建立显示的录入条，可以收缩显示的-->
<table class="common" cellpadding="5" cellspacing="1">
	<!--表示显示多行的-->
	<tr>
		<td class="common" colspan="4">
			<img style="cursor: hand;" src="/claim/images/butCollapseBlue.gif" name="DocImg" onclick="showPage(this,spanDoc)">
			<s:text name="claim.addDocument" />
			<br>
			<%-- 添加单证 --%>
			<span style="display: none">
				<table class="common" style="display: none" id="Doc_Data" cellspacing="1" cellpadding="5">
					<tbody>
						<tr>
							<td class="input">
								<input name="prpLdocDocCode" class="codecode" style="width: 100%" maxlength=3 ondblclick="code_CodeSelect(this, 'DocCode');" onchange="code_CodeChange(this, 'DocCode');"
									onkeyup="code_CodeSelect(this, 'DocCode');">
							</td>
							<td class="input">
								<input name="prpLdocDocName" class="codename" ondblclick="code_CodeSelect(this, 'DocCode','-1','name','none','post');" onchange="code_CodeChange(this, 'DocCode','-1','name','none','post');"
									onkeyup="code_CodeSelect(this, 'DocCode','-1','name','none','post');">
							</td>
							<td class="input">
								<input name="prpLdocDocCount" class="common" style="width: 100%">
							</td>
							<td class="input" colspan="2">
								<input name="prpLdocSignInDate" class="common" style="width: 100%" description="签收日期">
							</td>
							<td class="input" style='width: 4%' colspan="2" align="center">
								<div>
									<input type=button name="buttonDocDelete" class="smallbutton" onclick="deleteRow(this,'Doc')" value="-" style="cursor: hand">
									<input type="hidden" name="prpLdocFlag">
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</span> <span id="spanDoc" style="display: none"> <%-- 多行输入展现域 --%>
				<table class="common" cellpadding="5" cellspacing="1" id="Doc">
					<thead>
						<tr>
							<td class="centertitle" style="width: 20%">
								<s:text name="db.prpLdoc.docCode" />
							</td>
							<%-- 单证代码 --%>
							<td class="centertitle" style="width: 40%">
								<s:text name="db.prpLdoc.docName" />
							</td>
							<%-- 单证名称 --%>
							<td class="centertitle" style="width: 10%">
								<s:text name="db.prpLdoc.docCount" />
							</td>
							<%-- 单证份数 --%>
							<td class="centertitle" style="width: 20%">
								<s:text name="db.prpLdoc.signInDate" />
							</td>
							<%--  签收日期 --%>
							<td class="centertitle" style="width: 10%">&nbsp;</td>
						</tr>
					</thead>
					<tfoot>
						<tr>
							<td class="title" align="right" colspan=5 style="width: 10%">
								<div align="center">
									<input type="button" value="+" onclick="insertRow('Doc')" class=smallbutton name="buttonDriverInsert" style="cursor: hand">
								</div>
							</td>
						</tr>
					</tfoot>
					</tfoot>
					<tbody>
						<%
							int index1 = 0;
						%>
						<logic:iterate id="Doc" name="prpLdocDto" property="docList">
							<%
								if (index1 % 2 == 0)
										out.print("<tr class=oddrow>");
									else
										out.print("<tr class=oddrow>");
							%>
							<td class="input">
								<input name="prpLdocDocCode" class="codecode" style="width: 100%" maxlength=3 value="<bean:write name='Doc' property='docCode'/>" ondblclick="code_CodeSelect(this, 'DocCode');"
									onchange="code_CodeChange(this, 'DocCode');" onkeyup="code_CodeSelect(this, 'DocCode');">
							</td>
							<td class="input">
								<input name="prpLdocDocName" class="codename" value="<bean:write name='Doc' property='docName'/>" ondblclick="code_CodeSelect(this, 'DocCode','-1','name','none','post');"
									onchange="code_CodeChange(this, 'DocCode','-1','name','none','post');" onkeyup="code_CodeSelect(this, 'DocCode','-1','name','none','post');">
							</td>
							<td class="input">
								<input name="prpLdocDocCount" class="common" style="width: 100%" value="<bean:write name='Doc' property='docCount'/>">
							</td>
							<td class="input" colspan="2">
								<input name="prpLdocSignInDate" class="common" style="width: 100%" description="签收日期" value="<bean:write name='Doc' property='signInDate'/>">
							</td>
							<td class="input" style='width: 4%' colspan="2" align="center">
								<div>
									<input type=button name="buttonDocDelete" class=smallbutton onclick="deleteRow(this,'Doc')" value="-" style="cursor: hand">
									<input type="hidden" name="prpLdocFlag">
								</div>
							</td>
							</tr>
							<%
								index1++;
							%>
						</logic:iterate>
					</tbody>
				</table>
			</span>
		</td>
	</tr>
</table>