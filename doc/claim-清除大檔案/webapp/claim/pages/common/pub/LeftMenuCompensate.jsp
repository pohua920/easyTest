
<%
	//1.7 ¨º¦Ì?a¨¨???¡ä|¨¤¨ª
%>
<tr>
	<%
		// ¨¦¨¨?????t?D¦Ì¨²?t2?¦Ì??¡ã??¦Ì??????¨®o?/??o?¨ª?¡À¨º
	%>
	<td nowrap>
		<img name="notopimg" ID="17" style="cursor: 'hand';" onmouseup="clickEvent(document.all('17'), '/claim/images/treeFoderclassOpen.gif','/claim/images/treeFoderclass2.gif');"
			src="/claim/images/treeAdd.gif" width="9" height="9">
	</td>
	<%
		// ¨¦¨¨?????t?D¦Ì¨²?t2???¨º?¨ª?¡À¨ºo¨ª??3?
	%>
	<td align=left nowrap onClick="clickEvent(document.all('17'), '/claim/images/treeFoderclassOpen.gif','/claim/images/treeFoderclass2.gif');" style="cursor: 'default'">
		<img name="notopimgII" ID="17II" src="/claim/images/treeFoderclass2.gif" align="absmiddle" width="16" height="16"> <font ID="17font" color="#000000"><s:text
				name="pub.realCompensateTask" /></font>
		<%--¨º¦Ì?a¨¨???¡ä|¨¤¨ª--%>
	</td>
</tr>
<tr name="notop" ID="17p" STYLE="Display: 'none'">
	<td nowrap></td>
	<td nowrap>
		<%
			// ?¡§¨¢¡é???t?D¦Ì¨²¨¨y2?
		%>
		<table border=0 cellspacing=0 cellpadding=0 class="menu">
			<tr name="notop" ID="170p" STYLE="Display: 'none'">
				<td nowrap></td>
				<%
					// ¨°?¦Ì?nowrap???t?D¦Ì¨²¨¨y2?
				%>
				<%
					// 1?¡ê7?¡ê1?¡ê¡ä|¨¤¨ª¨º¦Ì?a¦Ì?????
				%>
				<!--
                             <tr >                                                                                                                                                                                                                    
                                   <td nowrap height="22px"></td>                                                                                                                                           
                                   <td align=left nowrap>                                                                                                                                                                                              
                                      
                                      <font ID="1700font" color="#000000" onClick="maingo('1700','sysMenu','¡ä|¨¤¨ª¨º¦Ì?a¦Ì???','/claim/common/compensate/compensateBeforeEdit.jsp','1700')"  style="cursor:'hand'" >                                                                                                                                                                                                     
                                          <v:line style="POSITION: absolute" from = "0,0" to = "0,16">
										     <v:stroke dashstyle ="ShortDot"></v:stroke>
										  </v:line>
										  <v:line style="POSITION: absolute" from = "0,10" to = "10,10">
										     <v:stroke dashstyle ="ShortDot"></v:stroke>
										  </v:line>
										  
										  <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'> 
											     <v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset = '15pt,0pt,0pt,-10pt'>¡ä|¨¤¨ª¨º¦Ì?a¦Ì???</v:TextBox>
											   <v:Extrusion backdepth='5pt' on='true'/>
										  </v:rect>
    				                  </font>
                                   </td>                                                                                                                                                                                                                
                              </tr>                                                                                                                                                                                                                  
                               -->
				<%
					//¨º?¡¤?¨®D¨º¦Ì?a?¡äDD¨¨¡§?T
					checkCode = AppConfig.get("sysconst.CHECKCODE_WRITE");
					taskCode = AppConfig.get("sysconst.TASKCODE_LPPA");
					checkPower = false;
					checkPower = uiPowerAction.checkPower(userCode, riskCode, taskCode, checkCode);
					if (checkPower) {
				%>
				<%
					// 1?¡ê7?¡ê9?¡ê¡äy¡ä|¨¤¨ª¨º¦Ì?a¨¨???
				%>
			
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="1701font" color="#000000" onClick="maingo('1701','sysMenu','¡äy¡ä|¨¤¨ª¨º¦Ì?a¨¨???','/claim/wfLogQuery.do?nodeType=compe&status=0','1701')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="menu.realIndemnity.todoTask" />
							</v:TextBox>
							<%--¡äy¡ä|¨¤¨ª¨º¦Ì?a¨¨???--%>
							<v:Extrusion backdepth='5pt' on='true' />
						</v:rect>
					</font>
				</td>
			</tr>
			<%
				// 1?¡ê7?¡ê2?¡ê?y?¨²¡ä|¨¤¨ª¨º¦Ì?a¨¨?????
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="1702font" color="#000000" onClick="maingo('1702','sysMenu','?y?¨²¡ä|¨¤¨ª¨º¦Ì?a¨¨???','/claim/wfLogQuery.do?nodeType=compp&status=2','1702')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="menu.realIndemnity.doingTask" />
							</v:TextBox>
							<%--?y?¨²¡ä|¨¤¨ª¨º¦Ì?a¨¨???--%>
							<v:Extrusion backdepth='5pt' on='true' />
						</v:rect>
					</font>
				</td>
			</tr>
			<!--                  
                              <%// 1?¡ê7?¡ê3?¡ê¨°?¨ª¨º3¨¦¨º¦Ì?a¨¨?????%>                                                                                                                                                                                                                             
                              <tr>                                                                                                                                                                                                                    
                                 <td nowrap></td>                                                                                                                                           
                                 <td align=left nowrap>                                                                                                                                                                                              
                                     <font ID="1702font" color="#000000" onClick="maingo('1702','sysMenu','¨°?¨ª¨º3¨¦¨º¦Ì?a¨¨???','/claim/claimStatusQuery.do?status=3&nodeType=compe','1702')"  style="cursor:'hand'" > 
                                          <v:line style="POSITION: absolute" from = "0,0" to = "0,16">
										     <v:stroke dashstyle ="ShortDot"></v:stroke>
										  </v:line>
										  <v:line style="POSITION: absolute" from = "0,10" to = "10,10">
										     <v:stroke dashstyle ="ShortDot"></v:stroke>
										  </v:line>
										  
										  <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'> 
											   <v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset = '15pt,0pt,0pt,-10pt'>
											   ¨°?¨ª¨º3¨¦¨º¦Ì?a¨¨???
											   </v:TextBox>
											   <v:Extrusion backdepth='5pt' on='true'/>
										  </v:rect>
                                     </font>                                                                                                                                                                                                     
                                 </td>                                                                                                                                                                                                                
                              </tr>       
                -->
			<%
				// 1?¡ê7?¡ê4?¡ê¨°?¨¬¨¢??¨º¦Ì?a¨¨?????
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="1703font" color="#000000" onClick="maingo('1703','sysMenu','¨°?¨¬¨¢??¨º¦Ì?a¨¨???','/claim/wfLogQuery.do?nodeType=compp&status=4','1703')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="menu.realIndemnity.submitTask" />
								<%--¨°?¨¬¨¢??¨º¦Ì?a¨¨???--%>
							</v:TextBox>
							<v:Extrusion backdepth='5pt' on='true' />
						</v:rect>
					</font>
				</td>
			</tr>
			<%
				// 1?¡ê7?¡ê5?¡ê¨¦?3y¨º¦Ì?a¨¨???
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="1704font" color="#000000" onClick="maingo('1704','sysMenu',' ¨¦?3y¨º¦Ì?a¨¨???','workAffair/document/unprocessed_docs.jsp','1704')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="pub.deleteCompensateTask" />
								<%--¨¦?3y¨º¦Ì?a¨¨???--%>
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
				//¨º?¡¤?¨®D¨º¦Ì?a?¨¢¨¨¡§?T
				checkCode = AppConfig.get("sysconst.CHECKCODE_READ");
				taskCode = AppConfig.get("sysconst.TASKCODE_LPPA");
				checkPower = false;
				checkPower = uiPowerAction.checkPower(userCode, riskCode, taskCode, checkCode);
				if (checkPower) {
			%>
			<%
				// 1?¡ê7?¡ê9?¡ê ¨º¦Ì?a¨¨???¡Á¡ä¨¬?¨ª3????
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="1705font" color="#000000" onClick="maingo('1705','sysMenu',' ¨º¦Ì?a¨¨???¡Á¡ä¨¬?¨ª3??','/claim/common/claimstatus/ClaimStatusStatEdit.jsp?nodeType=compe','1705')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="pub.realLoseTaskState" />
								<%--¨º¦Ì?a¨¨???¡Á¡ä¨¬?¨ª3??--%>
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
				//¨º?¡¤?¨®D¨º¦Ì?a?¨¢¨¨¡§?T
				checkCode = AppConfig.get("sysconst.CHECKCODE_CHECK");
				taskCode = AppConfig.get("sysconst.TASKCODE_LPPA");
				checkPower = false;
				checkPower = uiPowerAction.checkPower(userCode, riskCode, taskCode, checkCode);
				if (checkPower) {
			%>
			<%
				// 1?¡ê7?¡ê6?¡ê¨º¦Ì?a¨¨????¡äo?
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="1706font" color="#000000" onClick="maingo('1706','sysMenu','¨º¦Ì?a¨¨????2o?','/claim/common/compensate/CompensateApproveQueryEdit.jsp','1706')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="pub.realCompensateReview" />
								<%--¨º¦Ì?a¨¨????¡äo?--%>
							</v:TextBox>
							<v:Extrusion backdepth='5pt' on='true' />
						</v:rect>
					</font>
				</td>
			</tr>
			<%
				// 1?¡ê7?¡ê7?¡ê¨º?1¡è¨¬¨¢??o??a¨¨?????
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="1707font" color="#000000" onClick="maingo('1707','sysMenu',' ¨º?1¡è¨¬¨¢??o??a','workAffair/document/unprocessed_docs.jsp','1707')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="pub.ManualSubmission" />
								<%--¨º?1¡è¨¬¨¢??o??a--%>
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
				//¨º?¡¤?¨®D¨º¦Ì?a?¨¢¨¨¡§?T
				checkCode = AppConfig.get("sysconst.CHECKCODE_READ");
				taskCode = AppConfig.get("sysconst.TASKCODE_LPPA");
				checkPower = false;
				checkPower = uiPowerAction.checkPower(userCode, riskCode, taskCode, checkCode);
				if (checkPower) {
			%>
			<%
				// 1?¡ê7?¡ê8?¡ê2¨¦?¡¥¨º¦Ì?a¨¨?????
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="1708font" color="#000000" onClick="maingo('1708','sysMenu',' ¨º¦Ì?a2¨¦?¡¥','/claim/common/compensate/CompensateQueryEdit.jsp','1708')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="pub.realLoseQuery" />
								<%--¨º¦Ì?a2¨¦?¡¥--%>
							</v:TextBox>
							<v:Extrusion backdepth='5pt' on='true' />
						</v:rect>
					</font>
				</td>
			</tr>
			</tr>
			<%
				// 1?¡ê7?¡ê9?¡ê¨º¦Ì?ao??a¨ª¡§1y1|?¨¹¡ê¡§??¨®?¨¬?2a¨º?¡ê?
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="1709font" color="#000000" onClick="maingo('1709','sysMenu',' ¨º¦Ì?ao??a¨ª¡§1y1|?¨¹¡ê¡§??¨®?¨¬?2a¨º?¡ê?','/claim/common/compensate/CompensateApproveTest.jsp','1709')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="pub.compensate realLoseFunction" />
								<%-- ¨º¦Ì?ao??a¨ª¡§1y1|?¨¹¡ê¡§??¨®?¨¬?2a¨º?¡ê?--%>
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
			//¨ª¨º3¨¦¦Ì¨²¨¨y?????t?D
		%>
	</td>
</tr>
