
<%
	//4。1流程统计
%>
<tr>
	<td nowrap>
		<img name="notopimg" ID="40" style="cursor: 'hand';" onmouseup="clickEvent(document.all('40'), '/claim/images/treeFoderclassOpen.gif','/claim/images/treeFoderclass2.gif');"
			src="/claim/images/treeAdd.gif" width="9" height="9">
	</td>
	<td align=left nowrap onClick="clickEvent(document.all('40'), '/claim/images/treeFoderclassOpen.gif','/claim/images/treeFoderclass2.gif');" style="cursor: 'default'">
		<img name="notopimgII" ID="40II" src="/claim/images/treeFoderclass2.gif" align="absmiddle" width="16" height="16"> <font ID="40font" color="#000000"><s:text name="pub.queryStatistic" /></font>
		<%--查询统计--%>
	</td>
</tr>
<tr name="notop" ID="40p" STYLE="Display: 'none'">
	<td nowrap></td>
	<td nowrap>
		<%
			//开始第二级文件夹
		%>
		<%
			// 建立文件夹第二层
		%>
		<table border=0 cellspacing=0 cellpadding=2 class="menu">
			<tr>
				<%
					// 设置文件夹第二层的前面的那个加号/减号图标
				%>
				<td nowrap>
					<img name="notopimg" ID="41" style="cursor: 'hand';" onmouseup="clickEvent(document.all('41'), '/claim/images/treeFoderclassOpen.gif','/claim/images/treeFoderclass2.gif');"
						src="/claim/images/treeAdd.gif" width="9" height="9">
				</td>
				<%
					// 设置文件夹第二层显示图标和名称
				%>
				<td align=left nowrap onClick="clickEvent(document.all('41'), '/claim/images/treeFoderclassOpen.gif','/claim/images/treeFoderclass2.gif');" style="cursor: 'default'">
					<img name="notopimgII" ID="41II" src="/claim/images/treeFoderclass2.gif" align="absmiddle" width="16" height="16"> <font ID="41font" color="#000000"><s:text
							name="pub.workflowStatistic" /></font>
					<%--工作流统计--%>
				</td>
			</tr>
			<tr name="notop" ID="41p" STYLE="Display: 'none'">
				<td nowrap></td>
				<td nowrap>
					<%
						// 建立文件夹第三层
					%>
					<table border=0 cellspacing=0 cellpadding=0 class="menu">
						<tr name="notop" ID="410p" STYLE="Display: 'none'">
							<td nowrap></td>
							<%
								// 移到nowrap文件夹第三层
							%>
							<%
								// 1。1。1。节点状态统计
							%>
						
						<tr>
							<td nowrap height="22px"></td>
							<td align=left nowrap>
								<font ID="4101font" color="#000000" onClick="maingo('4101','sysMenu','节点状态数量统计','/claim/workflow/stat/WorkFlowNodeStatBeforeEdit.jsp','4101')" style="cursor: 'hand'"> <v:line
										style="POSITION: absolute" from="0,0" to="0,16">
										<v:stroke dashstyle="ShortDot"></v:stroke>
									</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
										<v:stroke dashstyle="ShortDot"></v:stroke>
									</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
										<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
											<s:text name="pub.nodeQuantityStatistic" />
											<%--节点状态数量统计--%>
										</v:TextBox>
										<v:Extrusion backdepth='5pt' on='true' />
									</v:rect>
								</font>
							</td>
						</tr>
						<%
							// 节点用户状态统计
						%>
						<tr>
							<td nowrap></td>
							<td align=left nowrap>
								<font ID="4102font" color="#000000" onClick="maingo('4102','sysMenu','节点用户状态统计','/claim/workflow/stat/WorkFlowNodeUserStatBeforeEdit.jsp','4102')" style="cursor: 'hand'"> <v:line
										style="POSITION: absolute" from="0,0" to="0,16">
										<v:stroke dashstyle="ShortDot"></v:stroke>
									</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
										<v:stroke dashstyle="ShortDot"></v:stroke>
									</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
										<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
											<s:text name="pub.nodeUserStatistic" />
											<%--节点用户状态统计--%>
										</v:TextBox>
										<v:Extrusion backdepth='5pt' on='true' />
									</v:rect>
								</font>
							</td>
						</tr>
						<%
							// 超时节点查询
						%>
						<tr>
							<td nowrap></td>
							<td align=left nowrap>
								<font ID="4103font" color="#000000" onClick="maingo('4103','sysMenu','超时节点查询','/claim/workflow/stat/WorkFlowNodeTimeOutBeforeEdit.jsp','4103')" style="cursor: 'hand'"> <v:line
										style="POSITION: absolute" from="0,0" to="0,10">
										<v:stroke dashstyle="ShortDot"></v:stroke>
									</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
										<v:stroke dashstyle="ShortDot"></v:stroke>
									</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
										<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
											<s:text name="pub.timeoutQuery" />
											<%--超时节点查询--%>
										</v:TextBox>
										<v:Extrusion backdepth='5pt' on='true' />
									</v:rect>
								</font>
							</td>
						</tr>
					</table>
					<%
						//完成第三级文件夹
					%>
				</td>
			</tr>
			<tr>
				<%
					// 设置文件夹第二层的前面的那个加号/减号图标
				%>
				<td nowrap>
					<img name="notopimg" ID="42" style="cursor: 'hand';" onmouseup="clickEvent(document.all('42'), '/claim/images/treeFoderclassOpen.gif','/claim/images/treeFoderclass2.gif');"
						src="/claim/images/treeAdd.gif" width="9" height="9">
				</td>
				<%
					// 设置文件夹第二层显示图标和名称
				%>
				<td align=left nowrap onClick="clickEvent(document.all('42'), '/claim/images/treeFoderclassOpen.gif','/claim/images/treeFoderclass2.gif');" style="cursor: 'default'">
					<img name="notopimgII" ID="42II" src="/claim/images/treeFoderclass2.gif" align="absmiddle" width="16" height="16"> <font ID="42font" color="#000000"><s:text
							name="pub.comprehenQuery" /></font>
					<%--综合查询--%>
				</td>
			</tr>
			<tr name="notop" ID="42p" STYLE="Display: 'none'">
				<td nowrap></td>
				<td nowrap>
					<%
						// 建立文件夹第三层
					%>
					<table border=0 cellspacing=0 cellpadding=0 class="menu">
						<tr name="notop" ID="420p" STYLE="Display: 'none'">
							<%
								// 1。1。1。未决赔款查询
							%>
						
						<tr>
							<td nowrap height="22px"></td>
							<td align=left nowrap>
								<font ID="4201font" color="#000000" onClick="maingo('4201','sysMenu','未决赔款查询','/claim/common/query/NoEndCaseSumQueryBeforeEdit.jsp','4201')" style="cursor: 'hand'"> <v:line
										style="POSITION: absolute" from="0,0" to="0,16">
										<v:stroke dashstyle="ShortDot"></v:stroke>
									</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
										<v:stroke dashstyle="ShortDot"></v:stroke>
									</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
										<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
											<s:text name="pub.outstandClaimQuerie" />
											<%--未决赔款查询--%>
										</v:TextBox>
										<v:Extrusion backdepth='5pt' on='true' />
									</v:rect>
								</font>
							</td>
						</tr>
						<%
							// 1。1。2。已决赔款查询
						%>
						<tr>
							<td nowrap height="22px"></td>
							<td align=left nowrap>
								<font ID="4202font" color="#000000" onClick="maingo('4202','sysMenu','已决赔款查询','/claim/common/query/EndCaseSumQueryBeforeEdit.jsp','4202')" style="cursor: 'hand'"> <v:line
										style="POSITION: absolute" from="0,0" to="0,10">
										<v:stroke dashstyle="ShortDot"></v:stroke>
									</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
										<v:stroke dashstyle="ShortDot"></v:stroke>
									</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
										<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
											<s:text name="pub.determinedQuerie" />
											<%--已决赔款查询--%>
										</v:TextBox>
										<v:Extrusion backdepth='5pt' on='true' />
									</v:rect>
								</font>
							</td>
						</tr>
						</tr>
					</table>
					<%
						//完成第三级文件夹
					%>
				</td>
			</tr>
		</table>
	</td>
</tr>
