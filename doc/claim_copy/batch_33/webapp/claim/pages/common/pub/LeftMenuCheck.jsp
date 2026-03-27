
<%
	//1.8 查勘任務处理
%>
<tr>
	<%
		// 设置文件夹第二层的前面的那个加号/减号图标
	%>
	<td nowrap>
		<img name="notopimg" ID="18" style="cursor: 'hand';" onmouseup="clickEvent(document.all('18'), '/claim/images/treeFoderclassOpen.gif','/claim/images/treeFoderclass2.gif');"
			src="/claim/images/treeAdd.gif" width="9" height="9">
	</td>
	<%
		// 设置文件夹第二层显示图标和名称
	%>
	<td align=left nowrap onClick="clickEvent(document.all('18'), '/claim/images/treeFoderclassOpen.gif','/claim/images/treeFoderclass2.gif');" style="cursor: 'default'">
		<img name="notopimgII" ID="18II" src="/claim/images/treeFoderclass2.gif" align="absmiddle" width="16" height="16"> <font ID="18font" color="#000000"><s:text
				name="pub.surveyTaskProcessing" /></font>
		<%--查勘任務处理--%>
	</td>
</tr>
<tr name="notop" ID="18p" STYLE="Display: 'none'">
	<td nowrap></td>
	<td nowrap>
		<%
			// 建立文件夹第三层
		%>
		<table border=0 cellspacing=0 cellpadding=0 class="menu">
			<%
				//是否有查勘执行权限
				checkCode = AppConfig.get("sysconst.CHECKCODE_WRITE");
				taskCode = AppConfig.get("sysconst.TASKCODE_LPCK");
				checkPower = false;
				checkPower = uiPowerAction.checkPower(userCode, riskCode, taskCode, checkCode);
				if (checkPower) {
			%>
			<tr name="notop" ID="180p" STYLE="Display: 'none'">
				<td nowrap></td>
				<%
					// 1。8。6。处理查勘登记项
				%>
				<%
					// 移到nowrap文件夹第三层
				%>
				<%
					/*                
																												                               <tr >
																												                                  <td nowrap height="22px"></td>
																												                                  <td align=left nowrap>
																												                                     <font ID="1805font" color="#000000" onClick="maingo('1805','sysMenu','处理调度任務','/claim/checkScheduleList.do?editType=DAALIST','1805')"  style="cursor:'hand'" >
																												                                         <v:line style="POSITION: absolute" from = "0,0" to = "0,16">
																												                                            <v:stroke dashstyle ="ShortDot"></v:stroke>
																																					  </v:line>
																																					  <v:line style="POSITION: absolute" from = "0,10" to = "10,10">
																																					     <v:stroke dashstyle ="ShortDot"></v:stroke>
																																					  </v:line>
																																					  
																																					  <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'> 
																																						   <v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset = '15pt,0pt,0pt,-10pt'>
																																						   处理调度任務
																																						   </v:TextBox>
																																						   <v:Extrusion backdepth='5pt' on='true'/>
																																					  </v:rect>
																												   				                          </font>
																												                                  </td>
																												                             </tr>
						 */
				%>
				<%
					// 1。8。1。处理查勘登记项
				%>
				<!--
                             <tr >
                                   <td nowrap height="22px"></td>
                                   <td align=left nowrap>
                                      <font ID="1800font" color="#000000" onClick="maingo('1800','sysMenu','处理查勘登记','/claim/common/check/checkBeforeEdit.jsp','1800')"  style="cursor:'hand'" >
                                          <v:line style="POSITION: absolute" from = "0,0" to = "0,16">
                                             <v:stroke dashstyle ="ShortDot"></v:stroke>
										  </v:line>
										  <v:line style="POSITION: absolute" from = "0,10" to = "10,10">
										     <v:stroke dashstyle ="ShortDot"></v:stroke>
										  </v:line>
										  
										  <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'> 
											   <v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset = '15pt,0pt,0pt,-10pt'>
											   处理查勘登记
											   </v:TextBox>
											   <v:Extrusion backdepth='5pt' on='true'/>
										  </v:rect>
    				                          </font>
                                   </td>
                              </tr>
                              -->
				<%
					// 1。8。2。待处理查勘任務
				%>
			
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="1809font" color="#000000" onClick="maingo('1809','sysMenu','待处理查勘任務','/claim/wfLogQuery.do?nodeType=check&status=0','1809')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="menu.survey.todoTask" />
								<%--待处理查勘任務--%>
							</v:TextBox>
							<v:Extrusion backdepth='5pt' on='true' />
						</v:rect>
					</font>
				</td>
			</tr>
			<%
				// 1。8。3。正在处理查勘任務项
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="1801font" color="#000000" onClick="maingo('1801','sysMenu','正在处理查勘任務','/claim/wfLogQuery.do?status=2&nodeType=check','1801')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="menu.survey.doingTask" />
								<%--正在处理查勘任務--%>
							</v:TextBox>
							<v:Extrusion backdepth='5pt' on='true' />
						</v:rect>
					</font>
				</td>
			</tr>
			<%
				// 1。8。4。已提交查勘任務项
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="1803font" color="#000000" onClick="maingo('1803','sysMenu','已提交查勘任務','/claim/wfLogQuery.do?status=4&nodeType=check','1803')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="menu.survey.submitTask" />
								<%--已提交查勘任務--%>
							</v:TextBox>
							<v:Extrusion backdepth='5pt' on='true' />
						</v:rect>
					</font>
				</td>
			</tr>
			<%
				// 1。8。5。查勘删除
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="1804font" color="#000000" onClick="maingo('1804','sysMenu',' 查勘删除','/claim/common/check/CheckDeleteQueryEdit.jsp','1804')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="pub.surveyDelete" />
								<%--查勘删除--%>
							</v:TextBox>
							<v:Extrusion backdepth='5pt' on='true' />
						</v:rect>
					</font>
				</td>
			</tr>
			<%
				// 1。8。10。注销/拒赔
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="1810font" color="#000000" onClick="maingo('1810','sysMenu',' 申请註銷/拒赔','/claim/wfLogQuery.do?status=-1&nodeType=check','1810')" style="cursor: 'hand'"> <v:line
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
				//是否有查勘执行权限
				checkCode = AppConfig.get("sysconst.CHECKCODE_READ");
				taskCode = AppConfig.get("sysconst.TASKCODE_LPCK");
				checkPower = false;
				checkPower = uiPowerAction.checkPower(userCode, riskCode, taskCode, checkCode);
				if (checkPower) {
			%>
			<%
				// 1。8。6。 查勘任務状态统计项
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="1806font" color="#000000" onClick="maingo('1806','sysMenu',' 查勘任務状态统计','/claim/common/claimstatus/ClaimStatusStatEdit.jsp?nodeType=check','1806')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="menu.survey.computTask" />
								<%--查勘任務状态统计--%>
							</v:TextBox>
							<v:Extrusion backdepth='5pt' on='true' />
						</v:rect>
					</font>
				</td>
			</tr>
			<%
				// 1。8。8。查询查勘任務项
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="1807font" color="#000000" onClick="maingo('1807','sysMenu',' 查勘查询','/claim/common/check/CheckQueryEdit.jsp','1807')" style="cursor: 'hand'"> <v:line style="POSITION: absolute"
							from="0,0" to="0,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="pub.surveyQuery2" />
								<%--查勘查询--%>
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
				//是否有查勘执行权限
				checkCode = AppConfig.get("sysconst.CHECKCODE_WRITE");
				taskCode = AppConfig.get("sysconst.TASKCODE_LPCK");
				checkPower = false;
				checkPower = uiPowerAction.checkPower(userCode, riskCode, taskCode, checkCode);
				if (checkPower) {
			%>
			<%
				// 1。8。9。查勘图片上传管理
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="1808font" color="#000000" onClick="maingo('1808','sysMenu',' 查勘图片上传管理','/claim/common/check/CheckCertifyQueryEdit.jsp','1808')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="pub.surveyImagManagement" />
								<%--查勘图片上传管理--%>
							</v:TextBox>
							<v:Extrusion backdepth='5pt' on='true' />
						</v:rect>
					</font>
				</td>
			</tr>
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
