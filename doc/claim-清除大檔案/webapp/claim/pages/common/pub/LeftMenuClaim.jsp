
<%
	//开始第二级文件夹
%>
<%
	//1。2立案任務处理
%>
<tr>
	<td nowrap>
		<img name="notopimg" ID="11" style="cursor: 'hand';" onmouseup="clickEvent(document.all('11'), '/claim/images/treeFoderclassOpen.gif','/claim/images/treeFoderclass2.gif');"
			src="/claim/images/treeAdd.gif" width="9" height="9">
	</td>
	<td align=left nowrap onClick="clickEvent(document.all('11'), '/claim/images/treeFoderclassOpen.gif','/claim/images/treeFoderclass2.gif');" style="cursor: 'default'">
		<img name="notopimgII" ID="11II" src="/claim/images/treeFoderclass2.gif" align="absmiddle" width="16" height="16"> <font ID="11font" color="#000000"><s:text
				name="pub.iInitiateProcessing" /></font>
		<%--立案任務处理--%>
	</td>
</tr>
<tr name="notop" ID="11p" STYLE="Display: 'none'">
	<td nowrap></td>
	<td nowrap>
		<%
			//建立第三级文件夹
		%>
		<table border=0 cellspacing=0 cellpadding=0 class="menu">
			<%
				// 1。2。1。处理立案登记
			%>
			<!--
           <tr>
               <td nowrap></td> 
               <td align=left nowrap>

                  <font ID="110font" color="#000000" onClick="maingo('110','sysMenu',' 处理立案登记','/claim/common/claim/claimBeforeEdit.jsp','110')"  style="cursor:'hand'" >
			       <v:line style="POSITION: absolute" from = "0,0" to = "0,16">
			          <v:stroke dashstyle ="ShortDot"></v:stroke>
			       </v:line>
			       <v:line style="POSITION: absolute" from = "0,10" to = "10,10">
			          <v:stroke dashstyle ="ShortDot"></v:stroke>
			       </v:line>
			       
			       <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'> 
				       <v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset = '15pt,0pt,0pt,-10pt'>处理立案登记</v:TextBox>
					   <v:Extrusion backdepth='5pt' on='true'/>
			       </v:rect>
                  </font>
               </td>
          </tr>
          -->
			<%
				// 1。2。9。待处理立案任務
			%>
			<%
				//是否有立案执行权限
				checkCode = AppConfig.get("sysconst.CHECKCODE_WRITE");
				taskCode = AppConfig.get("sysconst.TASKCODE_LPLA");
				checkPower = false;
				checkPower = uiPowerAction.checkPower(userCode, riskCode, taskCode, checkCode);
				if (checkPower) {
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="110font" color="#000000" onClick="maingo('110','sysMenu','待处理立案任務','/claim/wfLogQuery.do?nodeType=claim&status=0','110')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="menu.claim.todoTask" />
							</v:TextBox>
							<%--待处理立案任務--%>
							<v:Extrusion backdepth='5pt' on='true' />
						</v:rect>
					</font>
				</td>
			</tr>
			<%
				// 1。2。2。正在处理立案任務
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="111font" color="#000000" onClick="maingo('111','sysMenu','正在处理立案任務','/claim/wfLogQuery.do?nodeType=claim&status=2','111')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="menu.claim.doingTask" />
							</v:TextBox>
							<%--正在处理立案任務--%>
							<v:Extrusion backdepth='5pt' on='true' />
						</v:rect>
					</font>
				</td>
			</tr>
			<%
				// 1。2。4。已提交立案任務
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="113font" color="#000000" onClick="maingo('113','sysMenu','已提交立案任務','/claim/wfLogQuery.do?nodeType=claim&status=4','113')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="menu.claim.submitTask" />
							</v:TextBox>
							<%--已提交立案任務--%>
							<v:Extrusion backdepth='5pt' on='true' />
						</v:rect>
					</font>
				</td>
			</tr>
			<%
				// 1。2。8。注销/拒赔
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="119font" color="#000000" onClick="maingo('119','sysMenu',' 申请註銷/拒赔','/claim/wfLogQuery.do?status=-1&nodeType=claim','119')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="pub.applyCancellationReject" />
								<%--申请注销/拒赔--%>
							</v:TextBox>
							<v:Extrusion backdepth='5pt' on='true' />
						</v:rect>
					</font>
				</td>
			</tr>
			<%
				// 1。2。5。注销/拒赔立案任務
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="114font" color="#000000" onClick="maingo('114','sysMenu','註銷/拒赔立案任務','/claim/common/claim/ClaimBeforeCancel.jsp','114')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="pub.cancellationClaimsTask" />
							</v:TextBox>
							<%--注销/拒赔立案任務--%>
							<v:Extrusion backdepth='5pt' on='true' />
						</v:rect>
					</font>
				</td>
			</tr>
			<%
				// 1。2。9。撤消立案任務
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="118font" color="#000000" onClick="maingo('118','sysMenu','删除立案任務','/claim/common/claim/ClaimDeleteQueryEdit.jsp','118')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="pub.delIInitiateTakes" />
							</v:TextBox>
							<%--删除立案任務--%>
							<v:Extrusion backdepth='5pt' on='true' />
						</v:rect>
					</font>
				</td>
			</tr>
			<%
				}
			%>
			<%
				// 1。2。6。立案任務状态统计
			%>
			<%
				//是否有立案读权限
				checkCode = AppConfig.get("sysconst.CHECKCODE_READ");
				taskCode = AppConfig.get("sysconst.TASKCODE_LPLA");
				checkPower = false;
				checkPower = uiPowerAction.checkPower(userCode, riskCode, taskCode, checkCode);
				if (checkPower) {
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="115font" color="#000000" onClick="maingo('115','sysMenu','立案任務状态统计','/claim/common/claimstatus/ClaimStatusStatEdit.jsp?nodeType=claim','115')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="menu.claim.computTask" />
							</v:TextBox>
							<%--立案任務状态统计--%>
							<v:Extrusion backdepth='5pt' on='true' />
						</v:rect>
					</font>
				</td>
			</tr>
			<%
				// 1。2。7。查询立案任務
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="116font" color="#000000" onClick="maingo('116','sysMenu','查询立案任務','/claim/common/claim/ClaimQueryEdit.jsp','116')" style="cursor: 'hand'"> <v:line style="POSITION: absolute"
							from="0,0" to="0,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="menu.claim.queryTask" />
							</v:TextBox>
							<%--查询立案任務--%>
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