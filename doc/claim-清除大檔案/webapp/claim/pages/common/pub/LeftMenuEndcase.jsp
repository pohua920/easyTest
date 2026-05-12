
<%
	//1.10 结案任务处理
%>
<tr>
	<%
		// 设置文件夹第二层的前面的那个加号/减号图标
	%>
	<td nowrap>
		<img name="notopimg" ID="20" style="cursor: 'hand';" onmouseup="clickEvent(document.all('20'), '/claim/images/treeFoderclassOpen.gif','/claim/images/treeFoderclass2.gif');"
			src="/claim/images/treeAdd.gif" width="9" height="9">
	</td>
	<%
		// 设置文件夹第二层显示图标和名称
	%>
	<td align=left nowrap onClick="clickEvent(document.all('20'), '/claim/images/treeFoderclassOpen.gif','/claim/images/treeFoderclass2.gif');" style="cursor: 'default'">
		<img name="notopimgII" ID="20II" src="/claim/images/treeFoderclass2.gif" align="absmiddle" width="16" height="16"> <font ID="20font" color="#000000"><s:text name="pub.finalTaskProcess" /></font>
		<%--结案任務处理--%>
	</td>
</tr>
<tr name="notop" ID="20p" STYLE="Display: 'none'">
	<td nowrap></td>
	<td nowrap>
		<%
			// 建立文件夹第三层
		%>
		<table border=0 cellspacing=0 cellpadding=0 class="menu">
			<tr name="notop" ID="200p" STYLE="Display: 'none'">
				<td nowrap></td>
				<%
					// 移到nowrap文件夹第三层
				%>
				<%
					// 1。10。1。处理结案登记项
				%>
				<%
					//是否有结案读权限
					checkCode = AppConfig.get("sysconst.CHECKCODE_WRITE");
					taskCode = AppConfig.get("sysconst.TASKCODE_LPPA");
					checkPower = false;
					checkPower = uiPowerAction.checkPower(userCode, riskCode, taskCode, checkCode);
					if (checkPower) {
				%>
				<!--                
        <tr>                                                                                                                                                                                                                    
          <td nowrap height="22px"></td>                                                                                                                                           
          <td align=left nowrap>  
            <font ID="2000font" color="#000000" onClick="maingo('2000','sysMenu','处理结案登记','/claim/common/endcase/EndcaseBeforeEdit.jsp','2000')"  style="cursor:'hand'" >                                                                                                                                                                                                     
              <v:line style="POSITION: absolute" from = "0,0" to = "0,16">
			     <v:stroke dashstyle ="ShortDot"></v:stroke>
			  </v:line>
			  <v:line style="POSITION: absolute" from = "0,10" to = "10,10">
			     <v:stroke dashstyle ="ShortDot"></v:stroke>
			  </v:line>
			  
			  <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'> 
				     <v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset = '15pt,0pt,0pt,-10pt'>处理结案登记</v:TextBox>
				   <v:Extrusion backdepth='5pt' on='true'/>
			  </v:rect>
            </font>
          </td>                                                                                                                                                                                                                
        </tr>                                                                                                                                                                                                                  
         -->
				<%
					// 1。10。9。待处理结案任務
				%>
			
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="2009font" color="#000000" onClick="maingo('2009','sysMenu','待处理结案任務','/claim/wfLogQuery.do?nodeType=endca&status=0','2009')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="menu.finishCase.todoTask " />
							</v:TextBox>
							<%--待处理结案任務--%>
							<v:Extrusion backdepth='5pt' on='true' />
						</v:rect>
					</font>
				</td>
			</tr>
			<%
				// 1。10。5。撤销结案任務
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="2004font" color="#000000" onClick="maingo('2004','sysMenu','删除结案任務','workAffair/document/unprocessed_docs.jsp','2004')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="pub.deleteFinalTask" />
							</v:TextBox>
							<%--删除结案任務--%>
							<v:Extrusion backdepth='5pt' on='true' />
						</v:rect>
					</font>
				</td>
			</tr>
			<%
				// 1。10。6。生成归档号
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="2005font" color="#000000" onClick="maingo('2005','sysMenu','產生归档号','/claim/common/endcase/CaseNoBeforeEdit.jsp','2005')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="endcase.generateArchive" />
							</v:TextBox>
							<%--生成归档号--%>
							<v:Extrusion backdepth='5pt' on='true' />
						</v:rect>
					</font>
				</td>
			</tr>
			<%
				// 1。10。7。冲减保额
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="2006font" color="#000000" onClick="maingo('2006','sysMenu',' 冲减保额','/claim/common/endcase/EndorBeforeInput.jsp','2006')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="menu.finishCase.amountTask" />
							</v:TextBox>
							<%--冲减保额--%>
							<v:Extrusion backdepth='5pt' on='true' />
						</v:rect>
					</font>
				</td>
			</tr>
			<%
				}
			%>
			<%
				//是否有结案读权限
				checkCode = AppConfig.get("sysconst.CHECKCODE_READ");
				taskCode = AppConfig.get("sysconst.TASKCODE_LPPA");
				checkPower = false;
				checkPower = uiPowerAction.checkPower(userCode, riskCode, taskCode, checkCode);
				if (checkPower) {
			%>
			<%
				// 1。10。9。 结案任務状态统计项
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="2008font" color="#000000" onClick="maingo('2008','sysMenu',' 结案任務状态统计','/claim/common/claimstatus/ClaimStatusStatEdit.jsp?nodeType=endca','2008')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="menu.finishCase.computTask" />
							</v:TextBox>
							<%--结案任務状态统计--%>
							<v:Extrusion backdepth='5pt' on='true' />
						</v:rect>
					</font>
				</td>
			</tr>
			<%
				// 1。10。8。查询结案任務项
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="2007font" color="#000000" onClick="maingo('2007','sysMenu',' 结案查询','/claim/common/endcase/EndcaseQueryEdit.jsp','2007')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="pub.querySettlement" />
							</v:TextBox>
							<%--结案查询--%>
							<v:Extrusion backdepth='5pt' on='true' />
						</v:rect>
					</font>
				</td>
			</tr>
			<%
				}
			%>
		</table>
		<%
			//完成第三级文件夹
		%>
	</td>
</tr>