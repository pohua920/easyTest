
<%
	//1。9流程管理
%>
<tr>
	<td nowrap>
		<img name="notopimg" ID="30" style="cursor: 'hand';" onmouseup="clickEvent(document.all('30'), '/claim/images/treeFoderclassOpen.gif','/claim/images/treeFoderclass2.gif');"
			src="/claim/images/treeAdd.gif" width="9" height="9">
	</td>
	<td align=left nowrap onClick="clickEvent(document.all('30'), '/claim/images/treeFoderclassOpen.gif','/claim/images/treeFoderclass2.gif');" style="cursor: 'default'">
		<img name="notopimgII" ID="30II" src="/claim/images/treeFoderclass2.gif" align="absmiddle" width="16" height="16"> <font ID="30font" color="#000000"><s:text name="pub.workflowManage" /></font>
		<%--工作流管理--%>
	</td>
</tr>
<tr name="notop" ID="30p" STYLE="Display: 'none'">
	<td nowrap></td>
	<td nowrap>
		<%
			//开始第二级文件夹
		%>
		<%
			// 建立文件夹第二层
		%>
		<table border=0 cellspacing=0 cellpadding=2 class="menu">
			<%
				//是否有工作流管理执行权限
				checkCode = AppConfig.get("sysconst.CHECKCODE_WRITE");
				taskCode = AppConfig.get("sysconst.TASKCODE_SYS");
				checkPower = false;
				checkPower = uiPowerAction.checkPower(userCode, riskCode, taskCode, checkCode);
				if (checkPower) {
			%>
			<tr>
				<%
					// 设置文件夹第二层的前面的那个加号/减号图标
				%>
				<td nowrap>
					<img name="notopimg" ID="31" style="cursor: 'hand';" onmouseup="clickEvent(document.all('31'), '/claim/images/treeFoderclassOpen.gif','/claim/images/treeFoderclass2.gif');"
						src="/claim/images/treeAdd.gif" width="9" height="9">
				</td>
				<%
					// 设置文件夹第二层显示图标和名称
				%>
				<td align=left nowrap onClick="clickEvent(document.all('31'), '/claim/images/treeFoderclassOpen.gif','/claim/images/treeFoderclass2.gif');" style="cursor: 'default'">
					<img name="notopimgII" ID="31II" src="/claim/images/treeFoderclass2.gif" align="absmiddle" width="16" height="16"> <font ID="31font" color="#000000"><s:text name="pub.templateManage" /></font>
					<%--模板管理--%>
				</td>
			</tr>
			<%
				}
			%>
			<tr name="notop" ID="31p" STYLE="Display: 'none'">
				<td nowrap></td>
				<td nowrap>
					<%
						// 建立文件夹第三层
					%>
					<table border=0 cellspacing=0 cellpadding=0 class="menu">
						<tr name="notop" ID="310p" STYLE="Display: 'none'">
							<td nowrap></td>
							<%
								// 移到nowrap文件夹第三层
							%>
							<%
								// 1。1。1。模板维护
							%>
						
						<tr>
							<td nowrap height="22px"></td>
							<td align=left nowrap>
								<font ID="3101font" color="#000000" onClick="maingo('3101','sysMenu','模板维护','/claim/swfModelQuery.do','3101')" style="cursor: 'hand'"> <v:line style="POSITION: absolute" from="0,0"
										to="0,16">
										<v:stroke dashstyle="ShortDot"></v:stroke>
									</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
										<v:stroke dashstyle="ShortDot"></v:stroke>
									</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
										<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
											<s:text name="pub.templateMaintenan" />
											<%--模板维护--%>
										</v:TextBox>
										<v:Extrusion backdepth='5pt' on='true' />
									</v:rect>
								</font>
							</td>
						</tr>
						<%
							// 创建模板
						%>
						<tr>
							<td nowrap></td>
							<td align=left nowrap>
								<font ID="3102font" color="#000000" onClick="maingo('3102','sysMenu','创建模板','/claim/swfModelBeforeEdit.do?editType=ADD','3102')" style="cursor: 'hand'"> <v:line
										style="POSITION: absolute" from="0,0" to="0,16">
										<v:stroke dashstyle="ShortDot"></v:stroke>
									</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
										<v:stroke dashstyle="ShortDot"></v:stroke>
									</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
										<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
											<s:text name="pub.createTemplate" />
											<%--创建模板--%>
										</v:TextBox>
										<v:Extrusion backdepth='5pt' on='true' />
									</v:rect>
								</font>
							</td>
						</tr>
						<%
							// 1。1。2。批量模板分配
						%>
						<tr>
							<td nowrap></td>
							<td align=left nowrap>
								<font ID="3103font" color="#000000" onClick="maingo('3103','sysMenu','模板批量分配','/claim/swfModelUseBeforeEdit.do?editType=ADD','3103')" style="cursor: 'hand'"> <v:line
										style="POSITION: absolute" from="0,0" to="0,16">
										<v:stroke dashstyle="ShortDot"></v:stroke>
									</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
										<v:stroke dashstyle="ShortDot"></v:stroke>
									</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
										<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
											<s:text name="pub.templateDistribut" />
											<%--模板批量分配--%>
										</v:TextBox>
										<v:Extrusion backdepth='5pt' on='true' />
									</v:rect>
								</font>
							</td>
						</tr>
						<%
							// 1。1。2。模板分配维护
						%>
						<tr>
							<td nowrap></td>
							<td align=left nowrap>
								<font ID="3104font" color="#000000" onClick="maingo('3104','sysMenu','模板分配维护','/claim/swfModelUseBeforeEdit.do?editType=EDIT','3104')" style="cursor: 'hand'"> <v:line
										style="POSITION: absolute" from="0,0" to="0,16">
										<v:stroke dashstyle="ShortDot"></v:stroke>
									</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
										<v:stroke dashstyle="ShortDot"></v:stroke>
									</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
										<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
											<s:text name="pub.templateAllocatMainten" />
											<%--模板分配维护--%>
										</v:TextBox>
										<v:Extrusion backdepth='5pt' on='true' />
									</v:rect>
								</font>
							</td>
						</tr>
						<%
							// 1。1。3。路径条件维护
						%>
						<tr>
							<td nowrap></td>
							<td align=left nowrap>
								<font ID="3105font" color="#000000" onClick="maingo('3105','sysMenu','路径条件维护','/claim/swfModelCondStep1Query.do','3105')" style="cursor: 'hand'"> <v:line style="POSITION: absolute"
										from="0,0" to="0,10">
										<v:stroke dashstyle="ShortDot"></v:stroke>
									</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
										<v:stroke dashstyle="ShortDot"></v:stroke>
									</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
										<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
											<s:text name="menu.workFlow.caseMaintenance" />
											<%--路径条件维护--%>
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
			<%
				//是否有工作流管理执行权限
				checkCode = AppConfig.get("sysconst.CHECKCODE_READ");
				taskCode = AppConfig.get("sysconst.TASKCODE_SYS");
				checkPower = false;
				checkPower = uiPowerAction.checkPower(userCode, riskCode, taskCode, checkCode);
				if (checkPower) {
			%>
			<tr>
				<%
					// 设置文件夹第二层的前面的那个加号/减号图标
				%>
				<td nowrap>
					<img name="notopimg" ID="32" style="cursor: 'hand';" onmouseup="clickEvent(document.all('32'), '/claim/images/treeFoderclassOpen.gif','/claim/images/treeFoderclass2.gif');"
						src="/claim/images/treeAdd.gif" width="9" height="9">
				</td>
				<%
					// 设置文件夹第二层显示图标和名称
				%>
				<td align=left nowrap onClick="clickEvent(document.all('32'), '/claim/images/treeFoderclassOpen.gif','/claim/images/treeFoderclass2.gif');" style="cursor: 'default'">
					<img name="notopimgII" ID="32II" src="/claim/images/treeFoderclass2.gif" align="absmiddle" width="16" height="16"> <font ID="32font" color="#000000"><s:text name="pub.processManage" /></font>
					<%--流程管理--%>
				</td>
			</tr>
			<%
				}
			%>
			<tr name="notop" ID="32p" STYLE="Display: 'none'">
				<td nowrap></td>
				<td nowrap>
					<%
						// 建立文件夹第三层
					%>
					<table border=0 cellspacing=0 cellpadding=0 class="menu">
						<tr name="notop" ID="320p" STYLE="Display: 'none'">
							<%
								// 1。1。1。流程查询
							%>
						
						<tr>
							<td nowrap height="22px"></td>
							<td align=left nowrap>
								<font ID="3201font" color="#000000" onClick="maingo('3201','sysMenu','流程查询','/claim/workflow/flow/WorkFlowFlowBeforeQuery.jsp','3201')" style="cursor: 'hand'"> <v:line
										style="POSITION: absolute" from="0,0" to="0,10">
										<v:stroke dashstyle="ShortDot"></v:stroke>
									</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
										<v:stroke dashstyle="ShortDot"></v:stroke>
									</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
										<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
											<s:text name="pub.processQuery" />
											<%--流程查询--%>
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
