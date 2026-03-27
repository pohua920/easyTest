
<tr>
	<%
		// 设置文件夹第二层的前面的那个加号/减号图标
	%>
	<td nowrap>
		<img name="notopimg" ID="26" style="cursor: 'hand';" onmouseup="clickEvent(document.all('26'), '/claim/images/treeFoderclassOpen.gif','/claim/images/treeFoderclass2.gif');"
			src="/claim/images/treeAdd.gif" width="9" height="9">
	</td>
	<%
		// 设置文件夹第二层显示图标和名称
	%>
	<td align=left nowrap onClick="clickEvent(document.all('26'), '/claim/images/treeFoderclassOpen.gif','/claim/images/treeFoderclass2.gif');" style="cursor: 'default'">
		<img name="notopimgII" ID="26II" src="/claim/images/treeFoderclass2.gif" align="absmiddle" width="16" height="16"> <font ID="26font" color="#000000"><s:text
				name="pub.cancellatClaimProcessing" /></font>
		<%--注销/拒赔处理--%>
	</td>
</tr>
<tr name="notop" ID="26p" STYLE="Display: 'none'">
	<td nowrap></td>
	<td nowrap>
		<%
			// 建立文件夹第三层
		%>
		<table border=0 cellspacing=0 cellpadding=0 class="menu">
			<tr name="notop" ID="260p" STYLE="Display: 'none'">
				<td nowrap></td>
				<%
					// 移到nowrap文件夹第三层
				%>
				<%
					// 1。12。1。处理注销/拒赔登记项
				%>
				<%
					//是否有注销/拒赔管理执行权限
					checkCode = AppConfig.get("sysconst.CHECKCODE_WRITE");
					taskCode = AppConfig.get("sysconst.TASKCODE_LPLA");
					checkPower = false;
					checkPower = uiPowerAction.checkPower(userCode, riskCode, taskCode, checkCode);
					if (checkPower) {
				%>
				<%
					// 1。12。9。待处理注销/拒赔任務
				%>
			
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="2608font" color="#000000" onClick="maingo('2608','sysMenu','待处理註銷/拒赔任務','/claim/wfLogQuery.do?nodeType=cance&status=0','2608')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="pub.pendingCancellationClaims" />
								<%--待处理注销/拒赔任務--%>
							</v:TextBox>
							<v:Extrusion backdepth='5pt' on='true' />
						</v:rect>
					</font>
				</td>
			</tr>
			<%
				// 1。12。3。已提交注销/拒赔任務项
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="2602font" color="#000000" onClick="maingo('2602','sysMenu','已提交註銷/拒赔任務','/claim/wfLogQuery.do?nodeType=cance&status=4','2602')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="pub.submitCancellationClaims" />
								<%--已提交注销/拒赔任務--%>
							</v:TextBox>
							<v:Extrusion backdepth='5pt' on='true' />
						</v:rect>
					</font>
				</td>
			</tr>
			<!-- 
                                      
                             <%// 1。12。4。核损退回任務项%>                                                                                                                                                                                                                            
                              <tr>                                                                                                                                                                                                                    
                                 <td nowrap></td>                                                                                                                                           
                                 <td align=left nowrap>                                                                                                                                                                                              
                                      
                                     <font ID="2603font" color="#000000" onClick="maingo('2603','sysMenu','核损回退任務项','/claim/claimStatusQuery.do?status=5&nodeType=certa','2603')"  style="cursor:'hand'" > 
                                         <v:line style="POSITION: absolute" from = "0,0" to = "0,16">
                                             <v:stroke dashstyle ="ShortDot"></v:stroke>
										  </v:line>
										  <v:line style="POSITION: absolute" from = "0,10" to = "10,10">
										     <v:stroke dashstyle ="ShortDot"></v:stroke>
										  </v:line>
										  
										  <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'> 
											   <v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset = '15pt,0pt,0pt,-10pt'>
											   核损回退任務项
											   </v:TextBox>
											   <v:Extrusion backdepth='5pt' on='true'/>
										  </v:rect>
                                     </font>                                                                                                                                                                                                     
                                 </td>                                                                                                                                                                                                                
                              </tr> 
                              
    -->
			<%
				// 1。12。5。撤销注销/拒赔任務
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="2604font" color="#000000" onClick="maingo('2604','sysMenu','删除註銷/拒赔任務','workAffair/document/unprocessed_docs.jsp','2604')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="pub.deleteCancellationClaims" />
								<%-- 删除注销/拒赔任務--%>
							</v:TextBox>
							<v:Extrusion backdepth='5pt' on='true' />
						</v:rect>
					</font>
				</td>
			</tr>
			<%
				}
			%>
			<%
				//是否有注销/拒赔查询权限
				checkCode = AppConfig.get("sysconst.CHECKCODE_READ");
				taskCode = AppConfig.get("sysconst.TASKCODE_LPLA");
				checkPower = false;
				checkPower = uiPowerAction.checkPower(userCode, riskCode, taskCode, checkCode);
				if (checkPower) {
			%>
			<%
				// 1。12。6。注销/拒赔任務状态统计
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="2605font" color="#000000" onClick="maingo('2605','sysMenu',' 註銷/拒赔任務状态统计','/claim/common/claimstatus/ClaimStatusStatEdit.jsp?nodeType=cance','2605')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="pub.cancellationRejectingStatistic" />
								<%--注销/拒赔任務状态统计--%>
							</v:TextBox>
							<v:Extrusion backdepth='5pt' on='true' />
						</v:rect>
					</font>
				</td>
			</tr>
			<%
				// 1。12。8。查询注销/拒赔任務项
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="2607font" color="#000000" onClick="maingo('2607','sysMenu',' 註銷/拒赔查询','/claim/common/certainLoss/CertainLossQueryEdit.jsp','2607')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="pub.cancellationClaimsQuerie" />
								<%--注销/拒赔查询 --%>
							</v:TextBox>
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
