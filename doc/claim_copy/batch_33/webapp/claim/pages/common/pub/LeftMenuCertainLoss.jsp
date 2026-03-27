
<tr>
	<%
		// 设置文件夹第二层的前面的那个加号/减号图标
	%>
	<td nowrap>
		<img name="notopimg" ID="22" style="cursor: 'hand';" onmouseup="clickEvent(document.all('22'), '/claim/images/treeFoderclassOpen.gif','/claim/images/treeFoderclass2.gif');"
			src="/claim/images/treeAdd.gif" width="9" height="9">
	</td>
	<%
		// 设置文件夹第二层显示图标和名称
	%>
	<td align=left nowrap onClick="clickEvent(document.all('22'), '/claim/images/treeFoderclassOpen.gif','/claim/images/treeFoderclass2.gif');" style="cursor: 'default'">
		<img name="notopimgII" ID="22II" src="/claim/images/treeFoderclass2.gif" align="absmiddle" width="16" height="16"> <font ID="22font" color="#000000"><s:text
				name="pub.feeTaskProcessing" /></font>
		<%--定损任務处理--%>
	</td>
</tr>
<tr name="notop" ID="22p" STYLE="Display: 'none'">
	<td nowrap></td>
	<td nowrap>
		<%
			// 建立文件夹第三层
		%>
		<table border=0 cellspacing=0 cellpadding=0 class="menu">
			<tr name="notop" ID="220p" STYLE="Display: 'none'">
				<td nowrap></td>
				<%
					// 移到nowrap文件夹第三层
				%>
				<%
					// 1。12。1。处理定损登记项
				%>
				<%
					//是否有定损管理执行权限
					checkCode = AppConfig.get("sysconst.CHECKCODE_WRITE");
					taskCode = AppConfig.get("sysconst.TASKCODE_LPLA");
					checkPower = false;
					checkPower = uiPowerAction.checkPower(userCode, riskCode, taskCode, checkCode);
					if (checkPower) {
				%>
			
			<tr>
				<td nowrap height="22px"></td>
				<td align=left nowrap>
					<font ID="2200font" color="#000000" onClick="maingo('2200','sysMenu','处理定损登记','/claim/common/certainLoss/certainLossBeforeEdit.jsp','2200')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="pub.handlingFeeRegister" />
								<%--处理定损登记--%>
							</v:TextBox>
							<v:Extrusion backdepth='5pt' on='true' />
						</v:rect>
					</font>
				</td>
			</tr>
			<%
				// 1。12。9。待处理定损任務
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="2208font" color="#000000" onClick="maingo('2208','sysMenu','待处理定损任務','/claim/wfLogQuery.do?nodeType=certa&status=0','2208')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="pub.documentFeeProcessed" />
								<%--待处理定损任務--%>
							</v:TextBox>
							<v:Extrusion backdepth='5pt' on='true' />
						</v:rect>
					</font>
				</td>
			</tr>
			<%
				// 1。12。2。正在处理定损任務项
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="2201font" color="#000000" onClick="maingo('2201','sysMenu','正在处理定损任務','/claim/wfLogQuery.do?nodeType=certa&status=2','2201')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="pub.processingFeeProcessed" />
								<%--正在处理定损任務--%>
							</v:TextBox>
							<v:Extrusion backdepth='5pt' on='true' />
						</v:rect>
					</font>
				</td>
			</tr>
			</tr>
			<%
				// 1。12。3。已提交定损任務项
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="2202font" color="#000000" onClick="maingo('2202','sysMenu','已提交定损任務','/claim/wfLogQuery.do?nodeType=certa&status=4','2202')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="pub.submittedFeeProcessed" />
								<%--已提交定损任務--%>
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
                                      
                                     <font ID="2203font" color="#000000" onClick="maingo('2203','sysMenu','核损回退任務项','/claim/claimStatusQuery.do?status=5&nodeType=certa','2203')"  style="cursor:'hand'" > 
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
				// 1。12。5。撤销定损任務
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="2204font" color="#000000" onClick="maingo('2204','sysMenu','删除定损任務','workAffair/document/unprocessed_docs.jsp','2204')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="pub.deleteFeeProcessed" />
								<%--删除定损任務--%>
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
					<font ID="2210font" color="#000000" onClick="maingo('2210','sysMenu',' 申请註銷/拒赔','/claim/wfLogQuery.do?status=-1&nodeType=certa','2210')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text
									name="pub.applyCancellationReject /><%-- 申请注销/拒赔--%>
											   </v:TextBox>
											   <v:Extrusion backdepth='5pt' on='true'/>
										  </v:rect>
                                     </font>
                                 </td>
                              </tr>
          
                 <%}%>
            <%//是否有定损查询权限
				checkCode = AppConfig.get("sysconst.CHECKCODE_READ");
				taskCode = AppConfig.get("sysconst.TASKCODE_LPLA");
				checkPower = false;
				checkPower = uiPowerAction.checkPower(userCode, riskCode, taskCode, checkCode);
				if (checkPower) {%>  
                              <%// 1。12。6。定损任務状态统计%>
                              <tr>
                                 <td nowrap></td>
                                 <td align=left nowrap>
                                     <font ID="2205font" color="#000000" onClick="maingo('2205','sysMenu',' 定损任務状态统计','/claim/common/claimstatus/ClaimStatusStatEdit.jsp?nodeType=certa','2205')"  style="cursor:'hand'" >
                                          <v:line style="POSITION: absolute" from = "0,0" to = "0,16">
                                             <v:stroke dashstyle ="ShortDot"></v:stroke>
										  </v:line>
										  <v:line style="POSITION: absolute" from = "0,10" to = "10,10">
										     <v:stroke dashstyle ="ShortDot"></v:stroke>
										  </v:line>
										  
										  <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'> 
											   <v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset = '15pt,0pt,0pt,-10pt'>
											   <s:text name="menu.determinant.computTask" /><%--定损任務状态统计--%>
											   </v:TextBox>
											   <v:Extrusion backdepth='5pt' on='true'/>
										  </v:rect>
                                     </font>
                                 </td>
                              </tr>                                                     
                              <%// 1。12。8。查询定损任務项%>                                                                                                                                                                                                                            
                              <tr>
                                 <td nowrap></td>                                                                                                                                           
                                 <td align=left nowrap>                                                                                                                                                                                              
                                     
                                     <font ID="2207font" color="#000000" onClick="maingo('2207','sysMenu',' 定损查询','/claim/common/certainLoss/CertainLossQueryEdit.jsp','2207')"  style="cursor:'hand'" > 
                                         <v:line style="POSITION: absolute" from = "0,0" to = "0,10">
                                             <v:stroke dashstyle ="ShortDot"></v:stroke>
										  </v:line>
										  <v:line style="POSITION: absolute" from = "0,10" to = "10,10">
										     <v:stroke dashstyle ="ShortDot"></v:stroke>
										  </v:line>
										  
										  <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'> 
											   <v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset = '15pt,0pt,0pt,-10pt'>
											   <s:text name="pub.feeQuerie" /><%--定损查询--%>
											   </v:TextBox>
											   <v:Extrusion backdepth='5pt' on='true'/>
										  </v:rect>
                                     </font>                                                                                                                                                                                                     
                                 </td>                                                                                                                                                                                                                
                              </tr> 
                   <%}%>   
             <%//是否有定损执行权限
				checkCode = AppConfig.get("sysconst.CHECKCODE_WRITE");
				taskCode = AppConfig.get("sysconst.TASKCODE_LPLA");
				checkPower = false;
				checkPower = uiPowerAction.checkPower(userCode, riskCode, taskCode, checkCode);
				if (checkPower) {%>                                                     
                              <%// 1。12。9。定损图片上传管理%>                                                                                                                                                                                                                            
                              <tr>
                                 <td nowrap></td>                                                                                                                                           
                                 <td align=left nowrap>                                                                                                                                                                                              
                                     
                                     <font ID="2208font" color="#000000" onClick="maingo('2208','sysMenu',' 定损图片上传管理','/claim/common/certainLoss/CertainLossCertifyQueryEdit.jsp','2208')"  style="cursor:'hand'" > 
                                         <v:line style="POSITION: absolute" from = "0,0" to = "0,10">
                                             <v:stroke dashstyle ="ShortDot"></v:stroke>
										  </v:line>
										  <v:line style="POSITION: absolute" from = "0,10" to = "10,10">
										     <v:stroke dashstyle ="ShortDot"></v:stroke>
										  </v:line>
										  
										  <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'> 
											   <v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset = '15pt,0pt,0pt,-10pt'>
											   <s:text name="pub.managementFeeUpload" /><%--定损图片上传管理--%>
											   </v:TextBox>
											   <v:Extrusion backdepth='5pt' on='true'/>
										  </v:rect>
                                     </font>                                                                                                                                                                                                      
                                 </td>                                                                                                                                                                                                                
                              </tr>  
                  <%}%>                                                                                                                                                                                                                   
                       </table>  
                  <%//完成第三级文件夹%>                                                                                                                                                                                                                 
                 </td>
                                                                                                                                                                                                                                
               </tr>