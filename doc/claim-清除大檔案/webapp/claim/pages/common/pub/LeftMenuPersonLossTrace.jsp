
<tr>
	<%
		// 设置文件夹第二层的前面的那个加号/减号图标
	%>
	<td nowrap>
		<img name="notopimg" ID="24" style="cursor: 'hand';" onmouseup="clickEvent(document.all('24'), '/claim/images/treeFoderclassOpen.gif','/claim/images/treeFoderclass2.gif');"
			src="/claim/images/treeAdd.gif" width="9" height="9">
	</td>
	<%
		// 设置文件夹第二层显示图标和名称
	%>
	<td align=left nowrap onClick="clickEvent(document.all('24'), '/claim/images/treeFoderclassOpen.gif','/claim/images/treeFoderclass2.gif');" style="cursor: 'default'">
		<img name="notopimgII" ID="24II" src="/claim/images/treeFoderclass2.gif" align="absmiddle" width="16" height="16"> <font ID="24font" color="#000000"><s:text
				name="pub.peopleTaskProcess" /></font>
		<%--人伤跟踪任務处理--%>
	</td>
</tr>
<tr name="notop" ID="24p" STYLE="Display: 'none'">
	<td nowrap></td>
	<td nowrap>
		<%
			// 建立文件夹第三层
		%>
		<table border=0 cellspacing=0 cellpadding=0 class="menu">
			<tr name="notop" ID="240p" STYLE="Display: 'none'">
				<td nowrap></td>
				<%
					// 移到nowrap文件夹第三层
				%>
				<%
					// 1。12。1。处理人伤跟踪登记项
				%>
				<%
					//是否有人伤跟踪管理执行权限
					checkCode = AppConfig.get("sysconst.CHECKCODE_WRITE");
					taskCode = AppConfig.get("sysconst.TASKCODE_LPLA");
					checkPower = false;
					checkPower = uiPowerAction.checkPower(userCode, riskCode, taskCode, checkCode);
					if (checkPower) {
				%>
				<%
					/*         
																																																 <tr>                                                                                                                                                                                                                      
																																																 <td nowrap height="22px"></td>                                                                                                                                           
																																																 <td align=left nowrap>                                                                                                                                                                                               
																																																
																																																 <font ID="2400font" color="#000000" onClick="maingo('2400','sysMenu','处理人伤跟踪登记','/claim/common/certainLoss/certainLossBeforeEdit.jsp','2400')"  style="cursor:'hand'" >                                                                                                                                                                                                     
																																																 <v:line style="POSITION: absolute" from = "0,0" to = "0,16">
																																																 <v:stroke dashstyle ="ShortDot"></v:stroke>
																																																 </v:line>
																																																 <v:line style="POSITION: absolute" from = "0,10" to = "10,10">
																																																 <v:stroke dashstyle ="ShortDot"></v:stroke> 
																																																 </v:line>
																																																
																																																 <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'> 
																																																 <v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset = '15pt,0pt,0pt,-10pt'>
																																																 处理人伤跟踪登记
																																																 </v:TextBox>
																																																 <v:Extrusion backdepth='5pt' on='true'/>
																																																 </v:rect>
																																																 </font>
																																																 </td>                                                                                                                                                                                                                
																																																 </tr>  
						 */
				%>
				<%
					// 1。12。9。待处理人伤跟踪任務
				%>
			
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="2408font" color="#000000" onClick="maingo('2408','sysMenu','待处理人伤跟踪任務','/claim/wfLogQuery.do?nodeType=wound&status=0','2408')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="pub.woundedPendTasks" />
								<%--待处理人伤跟踪任務--%>
							</v:TextBox>
							<v:Extrusion backdepth='5pt' on='true' />
						</v:rect>
					</font>
				</td>
			</tr>
			<%
				// 1。12。2。正在处理人伤跟踪任務项
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="2401font" color="#000000" onClick="maingo('2401','sysMenu','正在处理人伤跟踪任務','/claim/wfLogQuery.do?nodeType=wound&status=2','2401')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="pub.dealingPeopleTasks" />
								<%--正在处理人伤跟踪任務--%>
							</v:TextBox>
							<v:Extrusion backdepth='5pt' on='true' />
						</v:rect>
					</font>
				</td>
			</tr>
			</tr>
			<%
				// 1。12。3。已提交人伤跟踪任務项
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="2402font" color="#000000" onClick="maingo('2402','sysMenu','已提交人伤跟踪任務','/claim/wfLogQuery.do?nodeType=wound&status=4','2402')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="pub.submittedPeopleTasks" />
								<%--已提交人伤跟踪任務--%>
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
                                      
                                     <font ID="2403font" color="#000000" onClick="maingo('2403','sysMenu','核损回退任務项','/claim/claimStatusQuery.do?status=5&nodeType=certa','2403')"  style="cursor:'hand'" > 
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
				// 1。12。5。撤销人伤跟踪任務
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="2404font" color="#000000" onClick="maingo('2404','sysMenu','删除人伤跟踪任務','workAffair/document/unprocessed_docs.jsp','2404')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="pub.deletePeopleTasks" />
								<%--删除人伤跟踪任務--%>
							</v:TextBox>
							<v:Extrusion backdepth='5pt' on='true' />
						</v:rect>
					</font>
				</td>
			</tr>
			<%
				// 1。12。10。注销/拒赔
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="2410font" color="#000000" onClick="maingo('2410','sysMenu',' 申请註銷/拒赔','/claim/wfLogQuery.do?status=-1&nodeType=wound','2410')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="pub.applyCancellationReject" />
								<%-- 申请注销/拒赔--%>
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
				//是否有人伤跟踪查询权限
				checkCode = AppConfig.get("sysconst.CHECKCODE_READ");
				taskCode = AppConfig.get("sysconst.TASKCODE_LPLA");
				checkPower = false;
				checkPower = uiPowerAction.checkPower(userCode, riskCode, taskCode, checkCode);
				if (checkPower) {
			%>
			<%
				// 1。12。6。人伤跟踪任務状态统计
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="2405font" color="#000000" onClick="maingo('2405','sysMenu',' 人伤跟踪任務状态统计','/claim/common/claimstatus/ClaimStatusStatEdit.jsp?nodeType=certa','2405')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="pub.peopleTaskStateStatistic" />
								<%--人伤跟踪任務状态统计--%>
							</v:TextBox>
							<v:Extrusion backdepth='5pt' on='true' />
						</v:rect>
					</font>
				</td>
			</tr>
			<%
				// 1。12。8。查询人伤跟踪任務项
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="2407font" color="#000000" onClick="maingo('2407','sysMenu',' 人伤跟踪查询','/claim/common/certainLoss/CertainLossQueryEdit.jsp','2407')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="pub.peopleFollowQuery" />
								<%--人伤跟踪查询--%>
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
				//是否有人伤跟踪执行权限
				checkCode = AppConfig.get("sysconst.CHECKCODE_WRITE");
				taskCode = AppConfig.get("sysconst.TASKCODE_LPLA");
				checkPower = false;
				checkPower = uiPowerAction.checkPower(userCode, riskCode, taskCode, checkCode);
				if (checkPower) {
			%>
			<%
				// 1。12。9。人伤跟踪图片上传管理
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="2408font" color="#000000" onClick="maingo('2408','sysMenu',' 人伤跟踪图片上传管理','/claim/common/certainLoss/CertainLossCertifyQueryEdit.jsp','2408')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="pub.peopleHurtManagement" />
								<%--人伤跟踪图片上传管理--%>
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
<tr>
	<%
		// 设置文件夹第二层的前面的那个加号/减号图标
	%>
	<td nowrap>
		<img name="notopimg" ID="25" style="cursor: 'hand';" onmouseup="clickEvent(document.all('25'), '/claim/images/treeFoderclassOpen.gif','/claim/images/treeFoderclass2.gif');"
			src="/claim/images/treeAdd.gif" width="9" height="9">
	</td>
	<%
		// 设置文件夹第二层显示图标和名称
	%>
	<td align=left nowrap onClick="clickEvent(document.all('25'), '/claim/images/treeFoderclassOpen.gif','/claim/images/treeFoderclass2.gif');" style="cursor: 'default'">
		<img name="notopimgII" ID="25II" src="/claim/images/treeFoderclass2.gif" align="absmiddle" width="16" height="16"> <font ID="25font" color="#000000"><s:text name="pub.peopleHurtDamage" /></font>
		<%--人伤核损任務处理--%>
	</td>
</tr>
<tr name="notop" ID="25p" STYLE="Display: 'none'">
	<td nowrap></td>
	<td nowrap>
		<%
			// 建立文件夹第三层
		%>
		<table border=0 cellspacing=0 cellpadding=0 class="menu">
			<tr name="notop" ID="250p" STYLE="Display: 'none'">
				<td nowrap></td>
				<%
					// 移到nowrap文件夹第三层
				%>
				<%
					// 1。12。1。处理人伤核损登记项
				%>
				<%
					//是否有人伤核损管理执行权限
					checkCode = AppConfig.get("sysconst.CHECKCODE_WRITE");
					taskCode = AppConfig.get("sysconst.TASKCODE_LPLA");
					checkPower = false;
					checkPower = uiPowerAction.checkPower(userCode, riskCode, taskCode, checkCode);
					if (checkPower) {
				%>
			
			<tr>
				<td nowrap height="22px"></td>
				<td align=left nowrap>
					<font ID="2500font" color="#000000" onClick="maingo('2500','sysMenu','处理人伤核损登记','/claim/common/certainLoss/certainLossBeforeEdit.jsp','2500')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="pub.dealingPeopleRegistration" />
								<%--处理人伤核损登记--%>
							</v:TextBox>
							<v:Extrusion backdepth='5pt' on='true' />
						</v:rect>
					</font>
				</td>
			</tr>
			<%
				// 1。12。9。待处理人伤核损任務
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="2508font" color="#000000" onClick="maingo('2508','sysMenu','待处理人伤核损任務','/claim/wfLogQuery.do?nodeType=veriw&status=0','2508')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="pub.woundedNuclearTasks" />
								<%--待处理人伤核损任務--%>
							</v:TextBox>
							<v:Extrusion backdepth='5pt' on='true' />
						</v:rect>
					</font>
				</td>
			</tr>
			<%
				// 1。12。2。正在处理人伤核损任務项
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="2501font" color="#000000" onClick="maingo('2501','sysMenu','正在处理人伤核损任務','/claim/wfLogQuery.do?nodeType=veriw&status=2','2501')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="pub.woundingNuclearTasks" />
								<%--正在处理人伤核损任務--%>
							</v:TextBox>
							<v:Extrusion backdepth='5pt' on='true' />
						</v:rect>
					</font>
				</td>
			</tr>
			</tr>
			<%
				// 1。12。3。已提交人伤核损任務项
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="2502font" color="#000000" onClick="maingo('2502','sysMenu','已提交人伤核损任務','/claim/wfLogQuery.do?nodeType=veriw&status=4','2502')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="pub.submitedNuclearTasks" />
								<%--已提交人伤核损任務--%>
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
                                      
                                     <font ID="2503font" color="#000000" onClick="maingo('2503','sysMenu','核损回退任務项','/claim/claimStatusQuery.do?status=5&nodeType=certa','2503')"  style="cursor:'hand'" > 
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
				// 1。12。5。撤销人伤核损任務
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="2504font" color="#000000" onClick="maingo('2504','sysMenu','删除人伤核损任務','workAffair/document/unprocessed_docs.jsp','2504')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="pub.delNuclearTasks" />
								<%--删除人伤核损任務--%>
							</v:TextBox>
							<v:Extrusion backdepth='5pt' on='true' />
						</v:rect>
					</font>
				</td>
			</tr>
			<%
				// 1。12。11。注销/拒赔
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="2510font" color="#000000" onClick="maingo('2510','sysMenu',' 申请註銷/拒赔','/claim/wfLogQuery.do?status=-1&nodeType=certa','2510')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="pub.applyCancellationReject" />
								<%-- 申请注销/拒赔--%>
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
				//是否有人伤核损查询权限
				checkCode = AppConfig.get("sysconst.CHECKCODE_READ");
				taskCode = AppConfig.get("sysconst.TASKCODE_LPLA");
				checkPower = false;
				checkPower = uiPowerAction.checkPower(userCode, riskCode, taskCode, checkCode);
				if (checkPower) {
			%>
			<%
				// 1。12。6。人伤核损任務状态统计
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="2505font" color="#000000" onClick="maingo('2505','sysMenu',' 人伤核损任務状态统计','/claim/common/claimstatus/ClaimStatusStatEdit.jsp?nodeType=certa','2505')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="pub.peopleHurtTaskStatistics" />
								<%--人伤核损任務状态统计--%>
							</v:TextBox>
							<v:Extrusion backdepth='5pt' on='true' />
						</v:rect>
					</font>
				</td>
			</tr>
			<%
				// 1。12。8。查询人伤核损任務项
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="2507font" color="#000000" onClick="maingo('2507','sysMenu',' 人伤核损查询','/claim/common/certainLoss/CertainLossQueryEdit.jsp','2507')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="pub.peopleHurtQuery" />
								<%--人伤核损查询--%>
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
				//是否有人伤核损执行权限
				checkCode = AppConfig.get("sysconst.CHECKCODE_WRITE");
				taskCode = AppConfig.get("sysconst.TASKCODE_LPLA");
				checkPower = false;
				checkPower = uiPowerAction.checkPower(userCode, riskCode, taskCode, checkCode);
				if (checkPower) {
			%>
			<%
				// 1。12。9。人伤核损图片上传管理
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="2509font" color="#000000" onClick="maingo('2509','sysMenu',' 人伤核损图片上传管理','/claim/common/certainLoss/CertainLossCertifyQueryEdit.jsp','2509')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="pub.peopleHurtManage" />
								<%--人伤核损图片上传管理--%>
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
