
<%
	//1.4 理赔打印处理
%>
<tr>
	<%
		// 设置文件夹第二层的前面的那个加号/减号图标
	%>
	<td nowrap>
		<img name="notopimg" ID="14" style="cursor: 'hand';" onmouseup="clickEvent(document.all('14'), '/claim/images/treeFoderclassOpen.gif','/claim/images/treeFoderclass2.gif');"
			src="/claim/images/treeAdd.gif" width="9" height="9">
	</td>
	<%
		// 设置文件夹第二层显示图标和名称
	%>
	<td align=left nowrap onClick="clickEvent(document.all('14'), '/claim/images/treeFoderclassOpen.gif','/claim/images/treeFoderclass2.gif');" style="cursor: 'default'">
		<img name="notopimgII" ID="14II" src="/claim/images/treeFoderclass2.gif" align="absmiddle" width="16" height="16">
	</td>
</tr>
<tr name="notop" ID="14p" STYLE="Display: 'none'">
	<td nowrap></td>
	<td nowrap>
		<%
			// 建立文件夹第三层
		%>
		<table border=0 cellspacing=0 cellpadding=0 class="menu">
			<tr name="notop" ID="140p" STYLE="Display: 'none'">
				<td nowrap></td>
				<%
					// 移到nowrap文件夹第三层
				%>
				<%
					// 1。4。1。机动车辆保险报案记录（代抄单）
				%>
			
			<tr>
				<td nowrap height="22px"></td>
				<td align=left nowrap>
					<font ID="1400font" color="#000000" onClick="maingo('1400','sysMenu','机动车辆保险报案记录（代抄单）','/claim/DAA/print/DAAPrintBeforeEdit.jsp?printType=Regist','1400')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="pub.motorVehicleRecordList" />
							</v:TextBox>
							<%--机动车辆保险报案记录（代抄单）--%>
							<v:Extrusion backdepth='5pt' on='true' />
						</v:rect>
					</font>
				</td>
			</tr>
			<%
				// 1。4。2。出险（盗抢）证明
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="1401font" color="#000000" onClick="maingo('1401','sysMenu','出险（盗抢）证明','/claim/DAA/print/DAAPrintBeforeEdit.jsp?printType=Pilfer','1401')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="pub.dangerCertificate" />
							</v:TextBox>
							<%--出险（盗抢）证明 --%>
							<v:Extrusion backdepth='5pt' on='true' />
						</v:rect>
					</font>
				</td>
			</tr>
			<%
				// 1。4。3。撤销结案任務
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="1402font" color="#000000" onClick="maingo('1402','sysMenu','拒赔通知书','/claim/DAA/print/DAAPrintBeforeEdit.jsp?printType=Cancelnotice','1402')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="menu.claimPrint.cancelNote" />
							</v:TextBox>
							<%--拒赔通知书--%>
							<v:Extrusion backdepth='5pt' on='true' />
						</v:rect>
					</font>
				</td>
			</tr>
			<%
				// 1。4。4。撤销结案任務
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="1403font" color="#000000" onClick="maingo('1403','sysMenu','原始保单及出险前批单','/claim/DAA/print/DAAPrintBeforeEdit.jsp?printType=HistoryFile','1403')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="menu.claimPrint.originalityProposal" />
							</v:TextBox>
							<%--原始保单及出险前批单--%>
							<v:Extrusion backdepth='5pt' on='true' />
						</v:rect>
					</font>
				</td>
			</tr>
			<%
				// 1。4。5。撤销结案任務
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="1404font" color="#000000" onClick="maingo('1404','sysMenu','出险时保单','/claim/DAA/print/DAAPrintBeforeEdit.jsp?printType=FileOnRisk','1404')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="menu.claimPrint.dangerProposal" />
							</v:TextBox>
							<%--出险时保单--%>
							<v:Extrusion backdepth='5pt' on='true' />
						</v:rect>
					</font>
				</td>
			</tr>
			<%
				// 1。4。6。生成归档号
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="1405font" color="#000000" onClick="maingo('1405','sysMenu','拒赔案件报告书','/claim/DAA/print/DAAPrintBeforeEdit.jsp?printType=Canceltrans','1405')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="menu.claimPrint.cancelReport" />
							</v:TextBox>
							<%--拒赔案件报告书--%>
							<v:Extrusion backdepth='5pt' on='true' />
						</v:rect>
					</font>
				</td>
			</tr>
			<%
				// 1。4。7。冲减保额
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="1406font" color="#000000" onClick="maingo('1406','sysMenu',' 损失情况简易确认书','/claim/DAA/print/DAAPrintBeforeEdit.jsp?printType=LossSimple','1406')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="menu.claimPrint.damnifySimAffirm" />
							</v:TextBox>
							<%--损失情况简易确认书--%>
							<v:Extrusion backdepth='5pt' on='true' />
						</v:rect>
					</font>
				</td>
			</tr>
			<%
				// 1。4。8。查询结案任務项
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="1407font" color="#000000" onClick="maingo('1407','sysMenu',' 损失情况确认书','/claim/DAA/print/DAAPrintBeforeEdit.jsp?printType=Loss','1407')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="menu.claimPrint.damnifyAffirm" />
							</v:TextBox>
							<%--损失情况确认书--%>
							<v:Extrusion backdepth='5pt' on='true' />
						</v:rect>
					</font>
				</td>
			</tr>
			<%
				// 1。4。9。 结案任務状态统计项
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="1408font" color="#000000" onClick="maingo('1408','sysMenu','零配件更换项目清单','/claim/DAA/print/DAAPrintBeforeEdit.jsp?printType=ComponentList','1408')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="menu.claimPrint.partChange" />
							</v:TextBox>
							<%--零配件更换项目清单--%>
							<v:Extrusion backdepth='5pt' on='true' />
						</v:rect>
					</font>
				</td>
			</tr>
			<%
				// 1。4。10。 结案任務状态统计项
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="1409font" color="#000000" onClick="maingo('1409','sysMenu','修理项目清单','/claim/DAA/print/DAAPrintBeforeEdit.jsp?printType=RepairList','1409')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="menu.claimPrint.repair" />
							</v:TextBox>
							<%--修理项目清单--%>
							<v:Extrusion backdepth='5pt' on='true' />
						</v:rect>
					</font>
				</td>
			</tr>
			<%
				// 1。4。11。 结案任務状态统计项
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="1410font" color="#000000" onClick="maingo('1410','sysMenu','修理项目清单附表','/claim/DAA/print/DAAPrintBeforeEdit.jsp?printType=RepairAdd','1410')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="menu.claimPrint.repairPlus" />
							</v:TextBox>
							<%--修理项目清单附表--%>
							<v:Extrusion backdepth='5pt' on='true' />
						</v:rect>
					</font>
				</td>
			</tr>
			<%
				// 1。4。12。 结案任務状态统计项
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="1411font" color="#000000" onClick="maingo('1411','sysMenu',' 财产损失确认书','/claim/DAA/print/DAAPrintBeforeEdit.jsp?printType=PropList','1411')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="menu.claimPrint.possession" />
							</v:TextBox>
							<%--财产损失确认书--%>
							<v:Extrusion backdepth='5pt' on='true' />
						</v:rect>
					</font>
				</td>
			</tr>
			<%
				// 1。4。13。 结案任務状态统计项
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="1412font" color="#000000" onClick="maingo('1412','sysMenu',' 赔款计算书','/claim/DAA/print/DAAPrintBeforeEdit.jsp?printType=Compensate','1412')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="menu.claimPrint.indemnityCompensate" />
							</v:TextBox>
							<%--赔款计算书--%>
							<v:Extrusion backdepth='5pt' on='true' />
						</v:rect>
					</font>
				</td>
			</tr>
			<%
				// 1。4。13。 结案任務状态统计项
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="1412font" color="#000000" onClick="maingo('1412','sysMenu','赔款计算书附页','/claim/DAA/print/DAAPrintBeforeEdit.jsp?printType=CompensateAdd','1412')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="menu.claimPrint.indemnityCompensateplus" />
							</v:TextBox>
							<%--赔款计算书附页--%>
							<v:Extrusion backdepth='5pt' on='true' />
						</v:rect>
					</font>
				</td>
			</tr>
			<%
				// 1。4。15。 结案任務状态统计项
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="1414font" color="#000000" onClick="maingo('1414','sysMenu',' 领取赔款通知书','/claim/DAA/print/DAAPrintBeforeEdit.jsp?printType=Drawnotice','1414')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="menu.claimPrint.indemnityNote" />
							</v:TextBox>
							<%--领取赔款通知书--%>
							<v:Extrusion backdepth='5pt' on='true' />
						</v:rect>
					</font>
				</td>
			</tr>
			<%
				// 1。4。16。 结案任務状态统计项
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="1415font" color="#000000" onClick="maingo('1415','sysMenu',' 预付赔款审批表 ','/claim/DAA/print/DAAPrintBeforeEdit.jsp?printType=Prepay','1415')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="menu.claimPrint.preIndemnityCheck" />
							</v:TextBox>
							<%--预付赔款审批表--%>
							<v:Extrusion backdepth='5pt' on='true' />
						</v:rect>
					</font>
				</td>
			</tr>
			<%
				// 1。4。17。 结案任務状态统计项
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="1416font" color="#000000" onClick="maingo('1416','sysMenu','结案催告、註銷通知书','/claim/DAA/print/DAAPrintBeforeEdit.jsp?printType=Pressnotice','1416')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="menu.claimPrint.logNote" />
							</v:TextBox>
							<%--结案催告、注销通知书--%>
							<v:Extrusion backdepth='5pt' on='true' />
						</v:rect>
					</font>
				</td>
			</tr>
			<%
				// 1。4。18。 结案任務状态统计项
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="1417font" color="#000000" onClick="maingo('1417','sysMenu',' 结案报告书','/claim/DAA/print/DAAPrintBeforeEdit.jsp?printType=Endcase','1417')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="menu.claimPrint.finishReport" />
							</v:TextBox>
							<%--结案报告书--%>
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