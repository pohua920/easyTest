
<tr>
	<%
		// ¨¦¨¨?????t?D¦Ì¨²?t2?¦Ì??¡ã??¦Ì??????¨®o?/??o?¨ª?¡À¨º
	%>
	<td nowrap>
		<img name="notopimg" ID="23" style="cursor: 'hand';" onmouseup="clickEvent(document.all('23'), '/claim/images/treeFoderclassOpen.gif','/claim/images/treeFoderclass2.gif');"
			src="/claim/images/treeAdd.gif" width="9" height="9">
	</td>
	<%
		// ¨¦¨¨?????t?D¦Ì¨²?t2???¨º?¨ª?¡À¨ºo¨ª??3?
	%>
	<td align=left nowrap onClick="clickEvent(document.all('23'), '/claim/images/treeFoderclassOpen.gif','/claim/images/treeFoderclass2.gif');" style="cursor: 'default'">
		<img name="notopimgII" ID="23II" src="/claim/images/treeFoderclass2.gif" align="absmiddle" width="16" height="16"> <font ID="23font" color="#000000"><s:text name="pub.nuclearDamage" /></font>
		<%--o??e¨¨???¡ä|¨¤¨ª--%>
	</td>
</tr>
<tr name="notop" ID="23p" STYLE="Display: 'none'">
	<td nowrap></td>
	<td nowrap>
		<%
			// ?¡§¨¢¡é???t?D¦Ì¨²¨¨y2?
		%>
		<table border=0 cellspacing=0 cellpadding=0 class="menu">
			<tr name="notop" ID="230p" STYLE="Display: 'none'">
				<td nowrap></td>
				<%
					// ¨°?¦Ì?nowrap???t?D¦Ì¨²¨¨y2?
				%>
				<%
					// 1?¡ê13?¡ê1?¡ê¡ä|¨¤¨ªo??e¦Ì?????
				%>
				<%
					//¨º?¡¤?¨®Do??e?¡äDD¨¨¡§?T
					checkCode = AppConfig.get("sysconst.CHECKCODE_WRITE");
					taskCode = AppConfig.get("sysconst.TASKCODE_LPLA");
					checkPower = false;
					checkPower = uiPowerAction.checkPower(userCode, riskCode, taskCode, checkCode);
					if (checkPower) {
				%>
				<!-- 
                             <tr >                                                                                                                                                                                                                    
                                   <td nowrap height="22px"></td>                                                                                                                                           
                                   <td align=left nowrap> 
                                      <font ID="2300font" color="#000000" onClick="maingo('2300','sysMenu','¡ä|¨¤¨ªo??e¦Ì???','/claim/common/verifyLoss/verifyLossBeforeEdit.jsp','2300')"  style="cursor:'hand'" >                                                                                                                                                                                                     
                                          <v:line style="POSITION: absolute" from = "0,0" to = "0,16">
                                             <v:stroke dashstyle ="ShortDot"></v:stroke>
										  </v:line>
										  <v:line style="POSITION: absolute" from = "0,10" to = "10,10">
										     <v:stroke dashstyle ="ShortDot"></v:stroke>
										  </v:line>
										  
										  <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'> 
											   <v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset = '15pt,0pt,0pt,-10pt'>
											   ¡ä|¨¤¨ªo??e¦Ì???
											   </v:TextBox>
											   <v:Extrusion backdepth='5pt' on='true'/>
										  </v:rect>
                                      </font>
                                   </td>                                                                                                                                                                                                                
                              </tr>  
                       -->
				<%
					// 1?¡ê13?¡ê9?¡ê¡äy¡ä|¨¤¨ªo??e¨¨???
				%>
			
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="2308font" color="#000000" onClick="maingo('2308','sysMenu','¡äy¡ä|¨¤¨ªo??e¨¨???','/claim/wfLogQuery.do?nodeType=verif&status=0','2308')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="menu.checkClaim.todoTask" />
								<%--¡äy¡ä|¨¤¨ªo??e¨¨???--%>
							</v:TextBox>
							<v:Extrusion backdepth='5pt' on='true' />
						</v:rect>
					</font>
				</td>
			</tr>
			<%
				// 1?¡ê13?¡ê2?¡ê?y?¨²¡ä|¨¤¨ªo??e¨¨?????
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="2301font" color="#000000" onClick="maingo('2301','sysMenu','?y?¨²¡ä|¨¤¨ªo??e¨¨???','/claim/wfLogQuery.do?nodeType=verif&status=2','2301')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="menu.checkClaim.doingTask" />
								<%--?y?¨²¡ä|¨¤¨ªo??e¨¨???--%>
							</v:TextBox>
							<v:Extrusion backdepth='5pt' on='true' />
						</v:rect>
					</font>
				</td>
			</tr>
			</tr>
			<%
				// 1?¡ê13?¡ê3?¡ê¨°?¨ª¨º3¨¦o??e¨¨?????
			%>
			<!--
                              <tr>                                                                                                                                                                                                                    
                                 <td nowrap></td>                                                                                                                                           
                                 <td align=left nowrap>                                                                                                                                                                                              
                                     
                                     <font ID="2302font" color="#000000" onClick="maingo('2302','sysMenu','¨°?¨ª¨º3¨¦o??e¨¨???','/claim/claimStatusQuery.do?status=3&nodeType=verif','2302')"  style="cursor:'hand'" > 
                                          <v:line style="POSITION: absolute" from = "0,0" to = "0,16">
                                             <v:stroke dashstyle ="ShortDot"></v:stroke>
										  </v:line>
										  <v:line style="POSITION: absolute" from = "0,10" to = "10,10">
										     <v:stroke dashstyle ="ShortDot"></v:stroke>
										  </v:line>
										  
										  <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'> 
											   <v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset = '15pt,0pt,0pt,-10pt'>
											   ¨°?¨ª¨º3¨¦o??e¨¨???
											   </v:TextBox>
											   <v:Extrusion backdepth='5pt' on='true'/>
										  </v:rect>
                                     </font>                                                                                                                                                                                                     
                                 </td>                                                                                                                                                                                                                
                              </tr>       
  -->
			<%
				// 1?¡ê13?¡ê4?¡ê¨°?¨¬¨¢??o??e¨¨?????
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="2303font" color="#000000" onClick="maingo('2303','sysMenu','¨°?¨¬¨¢??o??e¨¨???','/claim/wfLogQuery.do?nodeType=verif&status=4','2303')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="menu.checkClaim.submitTask" />
								<%--¨°?¨¬¨¢??o??e¨¨???--%>
							</v:TextBox>
							<v:Extrusion backdepth='5pt' on='true' />
						</v:rect>
					</font>
				</td>
			</tr>
			<%
				// 1?¡ê13?¡ê5?¡ê3¡¤?¨²o??e¨¨???
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="2304font" color="#000000" onClick="maingo('2304','sysMenu','¨¦?3yo??e¨¨???','workAffair/document/unprocessed_docs.jsp','2304')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="pub.deleteNuclearDamage" />
								<%--¨¦?3yo??e¨¨???--%>
							</v:TextBox>
							<v:Extrusion backdepth='5pt' on='true' />
						</v:rect>
					</font>
				</td>
			</tr>
			<%
				// 1?¡ê13?¡ê10?¡ê¡Á¡é?¨²/?¨¹?a
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="2310font" color="#000000" onClick="maingo('2310','sysMenu',' ¨¦¨º???]?N/?¨¹?a','/claim/wfLogQuery.do?status=-1&nodeType=verif','2310')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="pub.applyCancellationReject" />
								<%--¨¦¨º??¡Á¡é?¨²/?¨¹?a   --%>
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
				//¨º?¡¤?¨®D¨¢¡é¡ã??¨¢¨¨¡§?T
				checkCode = AppConfig.get("sysconst.CHECKCODE_READ");
				taskCode = AppConfig.get("sysconst.TASKCODE_LPLA");
				checkPower = false;
				checkPower = uiPowerAction.checkPower(userCode, riskCode, taskCode, checkCode);
				if (checkPower) {
			%>
			<%
				// 1?¡ê13?¡ê6?¡ê o??e¨¨???¡Á¡ä¨¬?¨ª3????
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="2305font" color="#000000" onClick="maingo('2305','sysMenu',' o??e¨¨???¡Á¡ä¨¬?¨ª3??','/claim/common/claimstatus/ClaimStatusStatEdit.jsp?nodeType=verif','2305')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="menu.checkClaim.computTask" />
								<%--o??e¨¨???¡Á¡ä¨¬?¨ª3??--%>
							</v:TextBox>
							<v:Extrusion backdepth='5pt' on='true' />
						</v:rect>
					</font>
				</td>
			</tr>
			<%
				// 1?¡ê13?¡ê8?¡ê2¨¦?¡¥o??e¨¨?????
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="2307font" color="#000000" onClick="maingo('2307','sysMenu',' o??e2¨¦?¡¥','/claim/common/verifyLoss/VerifyLossQueryEdit.jsp','2307')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="pub.nuclearDamageQuery" />
								<%--o??e2¨¦?¡¥--%>
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