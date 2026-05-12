
<tr>
	<%
		// ¨¦¨¨?????t?D¦Ì¨²?t2?¦Ì??¡ã??¦Ì??????¨®o?/??o?¨ª?¡À¨º
	%>
	<td nowrap>
		<img name="notopimg" ID="27" style="cursor: 'hand';" onmouseup="clickEvent(document.all('27'), '/claim/images/treeFoderclassOpen.gif','/claim/images/treeFoderclass2.gif');"
			src="/claim/images/treeAdd.gif" width="9" height="9">
	</td>
	<%
		// ¨¦¨¨?????t?D¦Ì¨²?t2???¨º?¨ª?¡À¨ºo¨ª??3?
	%>
	<td align=left nowrap onClick="clickEvent(document.all('27'), '/claim/images/treeFoderclassOpen.gif','/claim/images/treeFoderclass2.gif');" style="cursor: 'default'">
		<img name="notopimgII" ID="27II" src="/claim/images/treeFoderclass2.gif" align="absmiddle" width="16" height="16"> <font ID="27font" color="#000000"><s:text name="pub.specialClaimTask" /></font>
		<%--¨¬?¨ºa?a¡ã?¨¨???¡ä|¨¤¨ª--%>
	</td>
</tr>
<tr name="notop" ID="27p" STYLE="Display: 'none'">
	<td nowrap></td>
	<td nowrap>
		<%
			// ?¡§¨¢¡é???t?D¦Ì¨²¨¨y2?
		%>
		<table border=0 cellspacing=0 cellpadding=0 class="menu">
			<%
				//¨º?¡¤?¨®D¨¬?¨ºa?a¡ã?D¡ä¨¨¡§?T 
				checkCode = AppConfig.get("sysconst.CHECKCODE_WRITE");
				taskCode = AppConfig.get("sysconst.TASKCODE_LPPA");
				checkPower = false;
				checkPower = uiPowerAction.checkPower(userCode, riskCode, taskCode, checkCode);
				if (checkPower) {
			%>
			<tr name="notop" ID="270p" STYLE="Display: 'none'">
				<td nowrap></td>
				<%
					// ¨°?¦Ì?nowrap???t?D¦Ì¨²¨¨y2?
				%>
				<%
					// 1?¡ê6?¡ê1?¡ê¡ä|¨¤¨ª¨¬?¨ºa?a¡ã?¦Ì?????
				%>
			
			<tr>
				<td nowrap height="22px"></td>
				<td align=left nowrap>
					<font ID="2700font" color="#000000" onClick="maingo('2700','sysMenu','¡ä|¨¤¨ª¨¬?¨ºa?a¡ã?¦Ì???','/claim/common/specialCase/SpecialCaseBeforeQueryEdit.jsp','2700')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="pub.handleSpecialClaim" />
								<%--¡ä|¨¤¨ª¨¬?¨ºa?a¡ã?¦Ì???--%>
							</v:TextBox>
							<v:Extrusion backdepth='5pt' on='true' />
						</v:rect>
					</font>
				</td>
			</tr>
			<%
				// 1?¡ê6?¡ê9?¡ê¡äy¡ä|¨¤¨ª¨¬?¨ºa?a¡ã?¨¨???
			%>
			<!--                                                                                                                                                                                                                     
                              <tr>                                                                                                                                                                                                                    
                                 <td nowrap></td>                                                                                                                                           
                                 <td align=left nowrap>                                                                                                                                                                                              
                                    
                                    <font ID="2708font" color="#000000" onClick="maingo('2708','sysMenu','¡äy¡ä|¨¤¨ª¨¬?¨ºa?a¡ã?¨¨???','/claim/wfLogQuery.do?nodeType=prepa&status=0','2708')"  style="cursor:'hand'" >
                                          <v:line style="POSITION: absolute" from = "0,0" to = "0,16">
										     <v:stroke dashstyle ="ShortDot"></v:stroke>
										  </v:line>
										  <v:line style="POSITION: absolute" from = "0,10" to = "10,10">
										     <v:stroke dashstyle ="ShortDot"></v:stroke>
										  </v:line>
										  
										  <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'> 
											   <v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset = '15pt,0pt,0pt,-10pt'>
											   ¡äy¡ä|¨¤¨ª¨¬?¨ºa?a¡ã?¨¨???
											   </v:TextBox>
											   <v:Extrusion backdepth='5pt' on='true'/>
										  </v:rect>
                                    </font>                                                                                                                                                                                                     
                                 </td>                                                                                                                                                                                                                
                              </tr> 
                                                                                                                                                                                                                                    
                           </tr>                                                                                                                                                                                                                     
                                     -->
			<%
				// 1?¡ê6?¡ê2?¡ê?y?¨²¡ä|¨¤¨ª¨¬?¨ºa?a¡ã?¨¨?????
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="2701font" color="#000000" onClick="maingo('2701','sysMenu','?y?¨²¡ä|¨¤¨ª¨¬?¨ºa?a¡ã?¨¨???','/claim/wfLogQuery.do?nodeType=speci&status=2','2701')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="pub.specialClaimTasks" />
								<%--?y?¨²¡ä|¨¤¨ª¨¬?¨ºa?a¡ã?¨¨???--%>
							</v:TextBox>
							<v:Extrusion backdepth='5pt' on='true' />
						</v:rect>
					</font>
				</td>
			</tr>
			</tr>
			<!--              
                                                                                                                                                                                                                                
                             <%// 1?¡ê6?¡ê3?¡ê¨°?¨ª¨º3¨¦¨¬?¨ºa?a¡ã?¨¨?????%>                                                                                                                                                                                                                             
                              <tr>                                                                                                                                                                                                                    
                                 <td nowrap></td>                                                                                                                                           
                                 <td align=left nowrap>                                                                                                                                                                                              
                                     
                                     <font ID="2702font" color="#000000" onClick="maingo('2702','sysMenu','¨°?¨ª¨º3¨¦¨¬?¨ºa?a¡ã?¨¨???','/claim/claimStatusQuery.do?status=3&nodeType=prepa','2702')"  style="cursor:'hand'" > 
                                         <v:line style="POSITION: absolute" from = "0,0" to = "0,16">
										     <v:stroke dashstyle ="ShortDot"></v:stroke>
										  </v:line>
										  <v:line style="POSITION: absolute" from = "0,10" to = "10,10">
										     <v:stroke dashstyle ="ShortDot"></v:stroke>
										  </v:line>
										  
										  <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'> 
											   <v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset = '15pt,0pt,0pt,-10pt'>
											   ¨°?¨ª¨º3¨¦¨¬?¨ºa?a¡ã?¨¨???
											   </v:TextBox>
											   <v:Extrusion backdepth='5pt' on='true'/>
										  </v:rect>
                                     </font>                                                                                                                                                                                                     
                                 </td>                                                                                                                                                                                                                
                              </tr>       
                            -->
			<%
				// 1?¡ê6?¡ê4?¡ê¨°?¨¬¨¢??¨¬?¨ºa?a¡ã?¨¨?????
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="2703font" color="#000000" onClick="maingo('2703','sysMenu','¨°?¨¬¨¢??¨¬?¨ºa?a¡ã?¨¨???','/claim/wfLogQuery.do?nodeType=speci&status=4','2703')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="pub.submitSpecialClaimTasks" />
								<%--¨°?¨¬¨¢??¨¬?¨ºa?a¡ã?¨¨???--%>
							</v:TextBox>
							<v:Extrusion backdepth='5pt' on='true' />
						</v:rect>
					</font>
				</td>
			</tr>
			<%
				// 1?¡ê6?¡ê5?¡ê3¡¤?¨²¨¬?¨ºa?a¡ã?¨¨???
			%>
			<!--                                                                                                                                                                                                                           
                              <tr>                                                                                                                                                                                                                    
                                 <td nowrap></td>                                                                                                                                           
                                 <td align=left nowrap>                                                                                                                                                                                              
                                
                                     <font ID="2704font" color="#000000" onClick="maingo('2704','sysMenu','3¡¤?¨²¨¬?¨ºa?a¡ã?¨¨???','workAffair/document/unprocessed_docs.jsp','2704')"  style="cursor:'hand'" > 
                                          <v:line style="POSITION: absolute" from = "0,0" to = "0,16">
										     <v:stroke dashstyle ="ShortDot"></v:stroke>
										  </v:line>
										  <v:line style="POSITION: absolute" from = "0,10" to = "10,10">
										     <v:stroke dashstyle ="ShortDot"></v:stroke>
										  </v:line>
										  
										  <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'> 
											   <v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset = '15pt,0pt,0pt,-10pt'>
											   3¡¤?¨²¨¬?¨ºa?a¡ã?¨¨???
											   </v:TextBox>
											   <v:Extrusion backdepth='5pt' on='true'/>
										  </v:rect>
                                     </font>                                                                                                                                                                                                     
                                 </td>                                                                                                                                                                                                                
                              </tr> 
             <%}%>     
                     -->
			<%
				//¨º?¡¤?¨®D¨¬?¨ºa?a¡ã??¨¢¨¨¡§?T
				checkCode = AppConfig.get("sysconst.CHECKCODE_CHECK");
				taskCode = AppConfig.get("sysconst.TASKCODE_LPPA");
				checkPower = false;
				checkPower = uiPowerAction.checkPower(userCode, riskCode, taskCode, checkCode);
				if (checkPower) {
			%>
			<%
				// 1?¡ê6?¡ê6?¡ê¨¬?¨ºa?a¡ã?¨¨????¡äo?
			%>
			<!--                                                                                                                                                                                                                        
                              <tr>
                                 <td nowrap></td>                                                                                                                                           
                                 <td align=left nowrap>                                                                                                                                                                                              
                                    
                                     <font ID="2705font" color="#000000" onClick="maingo('2705','sysMenu','¨¬?¨ºa?a¡ã?¨¨????2o?','/claim/common/specialCase/specialCaseApproveQueryEdit.jsp','2705')"  style="cursor:'hand'" > 
                                         <v:line style="POSITION: absolute" from = "0,0" to = "0,16">
										     <v:stroke dashstyle ="ShortDot"></v:stroke>
										  </v:line>
										  <v:line style="POSITION: absolute" from = "0,10" to = "10,10">
										     <v:stroke dashstyle ="ShortDot"></v:stroke>
										  </v:line>
										  
										  <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'> 
											   <v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset = '15pt,0pt,0pt,-10pt'>
											   ¨¬?¨ºa?a¡ã?¨¨????¡äo?
											   </v:TextBox>
											   <v:Extrusion backdepth='5pt' on='true'/>
										  </v:rect>
                                     </font>                                                                                                                                                                                                     
                                 </td>                                                                                                                                                                                                                
                              </tr>                                                                                                                                                                                                                
                                      -->
			<%
				// 1?¡ê6?¡ê7?¡ê¨º?1¡è¨¬¨¢??o??a??
			%>
			<!--                                                                                                                                                                                                                          
                              <tr>
                                 <td nowrap></td>                                                                                                                                           
                                 <td align=left nowrap>                                                                                                                                                                                              
                                     
                                     <font ID="2706font" color="#000000" onClick="maingo('2706','sysMenu',' ¨º?1¡è¨¬¨¢??o??a','workAffair/document/unprocessed_docs.jsp','2706')"  style="cursor:'hand'" > 
                                         <v:line style="POSITION: absolute" from = "0,0" to = "0,16">
										     <v:stroke dashstyle ="ShortDot"></v:stroke>
										  </v:line>
										  <v:line style="POSITION: absolute" from = "0,10" to = "10,10">
										     <v:stroke dashstyle ="ShortDot"></v:stroke>
										  </v:line>
										  
										  <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'> 
											   <v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset = '15pt,0pt,0pt,-10pt'>
											   ¨º?1¡è¨¬¨¢??o??a
											   </v:TextBox>
											   <v:Extrusion backdepth='5pt' on='true'/>
										  </v:rect>
                                     </font>                                                                                                                                                                                                     
                                 </td>                                                                                                                                                                                                                
                              </tr> 
                              -->
			<%
				}
			%>
			<%
				//¨º?¡¤?¨®D¨¬?¨ºa?a¡ã??¨¢¨¨¡§?T
				checkCode = AppConfig.get("sysconst.CHECKCODE_READ");
				taskCode = AppConfig.get("sysconst.TASKCODE_LPPA");
				checkPower = false;
				checkPower = uiPowerAction.checkPower(userCode, riskCode, taskCode, checkCode);
				if (checkPower) {
			%>
			<%
				// 1?¡ê7?¡ê9?¡ê ¨¬?¨ºa?a¡ã?¨¨???¡Á¡ä¨¬?¨ª3????
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="2708font" color="#000000" onClick="maingo('2708','sysMenu',' ¨¬?¨ºa?a¡ã?¨¨???¡Á¡ä¨¬?¨ª3??','/claim/common/claimstatus/ClaimStatusStatEdit.jsp?nodeType=speci','2708')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="pub.specialClaimStatistics" />
								<%--¨¬?¨ºa?a¡ã?¨¨???¡Á¡ä¨¬?¨ª3??--%>
							</v:TextBox>
							<v:Extrusion backdepth='5pt' on='true' />
						</v:rect>
					</font>
				</td>
			</tr>
			<%
				// 1?¡ê6?¡ê8?¡ê2¨¦?¡¥¨¬?¨ºa?a¡ã?¨¨?????
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="2707font" color="#000000" onClick="maingo('2707','sysMenu',' ¨¬?¨ºa?a¡ã?2¨¦?¡¥','/claim/common/specialCase/SpecialCaseQueryEdit.jsp','2707')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="pub.speciaClaimQuery" />
								<%--¨¬?¨ºa?a¡ã?2¨¦?¡¥--%>
							</v:TextBox>
							<v:Extrusion backdepth='5pt' on='true' />
						</v:rect>
					</font>
				</td>
			</tr>
			<%
				// 1?¡ê6?¡ê8?¡ê???¨®¨¬?¨ºa?a¡ã?o??a¨ª¡§1y1|?¨¹¡ê¡§??¨®?¨¬?2a¨º?¡ê?
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="2708font" color="#000000" onClick="maingo('2708','sysMenu',' ¨¬?¨ºa?a¡ã?o??a¨ª¡§1y1|?¨¹¡ê¡§??¨®?¨¬?2a¨º?¡ê?','/claim/common/specialCase/SpecialCaseApproveTest.jsp','2708')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="pub.specialClaimFunction" />
								<%--¨¬?¨ºa?a¡ã?o??a¨ª¡§1y1|?¨¹¡ê¡§??¨®?¨¬?2a¨º?¡ê?--%>
							</v:TextBox>
							<v:Extrusion backdepth='5pt' on='true' />
						</v:rect>
					</font>
				</td>
			</tr>
			<%
				// 1?¡ê6?¡ê8?¡ê???¨®?¡è?ao??a¨ª¡§1y1|?¨¹¡ê¡§??¨®?¨¬?2a¨º?¡ê?
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="1608font" color="#000000" onClick="maingo('1608','sysMenu',' ?¡è?ao??a¨ª¡§1y1|?¨¹¡ê¡§??¨®?¨¬?2a¨º?¡ê?','/claim/common/prepay/PrepayApproveTest.jsp','1608')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="pub.compensate ClaimFunction" />
								<%--?¡è?ao??a¨ª¡§1y1|?¨¹¡ê¡§??¨®?¨¬?2a¨º?¡ê?--%>
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
			//¨ª¨º3¨¦¦Ì¨²¨¨y?????t?D
		%>
	</td>
</tr>
