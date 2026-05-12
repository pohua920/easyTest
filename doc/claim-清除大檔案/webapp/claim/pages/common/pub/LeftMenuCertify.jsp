
<%
	//1.11 单证收集任務处理
%>
<tr>
	<%
		// 设置文件夹第二层的前面的那个加号/减号图标
	%>
	<td nowrap>
		<img name="notopimg" ID="21" style="cursor: 'hand';" onmouseup="clickEvent(document.all('21'), '/claim/images/treeFoderclassOpen.gif','/claim/images/treeFoderclass2.gif');"
			src="/claim/images/treeAdd.gif" width="9" height="9">
	</td>
	<%
		// 设置文件夹第二层显示图标和名称
	%>
	<td align=left nowrap onClick="clickEvent(document.all('21'), '/claim/images/treeFoderclassOpen.gif','/claim/images/treeFoderclass2.gif');" style="cursor: 'default'">
		<img name="notopimgII" ID="21II" src="/claim/images/treeFoderclass2.gif" align="absmiddle" width="16" height="16"> <font ID="21font" color="#000000"><s:text
				name="pub.documentCollectionTask" /></font>
		<%--单证收集任務处理--%>
	</td>
</tr>
<tr name="notop" ID="21p" STYLE="Display: 'none'">
	<td nowrap></td>
	<td nowrap>
		<%
			// 建立文件夹第三层
		%>
		<table border=0 cellspacing=0 cellpadding=0 class="menu">
			<%
				//是否有单证执行权限
				checkCode = AppConfig.get("sysconst.CHECKCODE_WRITE");
				taskCode = AppConfig.get("sysconst.TASKCODE_LPLA");
				checkPower = false;
				checkPower = uiPowerAction.checkPower(userCode, riskCode, taskCode, checkCode);
				if (checkPower) {
			%>
			<tr name="notop" ID="210p" STYLE="Display: 'none'">
				<td nowrap></td>
				<%
					// 移到nowrap文件夹第三层
				%>
				<%
					// 1。11。1。处理单证登记项
				%>
				<!--
                             <tr >                                                                                                                                                                                                                    
                                   <td nowrap height="22px"></td>                                                                                                                                           
                                   <td align=left nowrap>                                                                                                                                                                                              
                                      
                                      <font ID="2100font" color="#000000" onClick="maingo('2100','sysMenu','处理单证登记','/claim/common/certify/CertifyBeforeEdit.jsp','2100')"  style="cursor:'hand'" >                                                                                                                                                                                                     
                                           <v:line style="POSITION: absolute" from = "0,0" to = "0,16">
										     <v:stroke dashstyle ="ShortDot"></v:stroke>
										  </v:line>
										  <v:line style="POSITION: absolute" from = "0,10" to = "10,10">
										     <v:stroke dashstyle ="ShortDot"></v:stroke>
										  </v:line>
										  
										  <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'> 
											   <v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset = '15pt,0pt,0pt,-10pt'>
											   处理单证登记
											   </v:TextBox>
											   <v:Extrusion backdepth='5pt' on='true'/>
										  </v:rect>
    				                          </font>
                                   </td>                                                                                                                                                                                                                
                              </tr>                                                                                                                                                                                                                  
                              -->
				<%
					// 1。11。9。待处理单证任務
				%>
			
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="2109font" color="#000000" onClick="maingo('2109','sysMenu','待处理单证任務','/claim/wfLogQuery.do?nodeType=certi&status=0','2109')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="pub.documentProcessed" />
								<%--待处理单证任務--%>
							</v:TextBox>
							<v:Extrusion backdepth='5pt' on='true' />
						</v:rect>
					</font>
				</td>
			</tr>
			</tr>
			<%
				// 1。11。2。正在处理单证任務项
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="2101font" color="#000000" onClick="maingo('2101','sysMenu','正在处理单证任務','/claim/wfLogQuery.do?nodeType=certi&status=2','2101')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="pub.taskProcessingDocument" />
								<%--正在处理单证任務--%>
							</v:TextBox>
							<v:Extrusion backdepth='5pt' on='true' />
						</v:rect>
					</font>
				</td>
			</tr>
			</tr>
			<%
				// 1。11。3。已完成单证任務项
			%>
			<%
				/*                             <tr>                                                                                                                                                                                                                    
																									 <td nowrap></td>                                                                                                                                           
																									 <td align=left nowrap>                                                                                                                                                                                              
																									
																									 <font ID="2102font" color="#000000" onClick="maingo('2102','sysMenu','已完成单证任務','/claim/claimStatusQuery.do?status=3&nodeType=certi','2102')"  style="cursor:'hand'" > 
																									 <v:line style="POSITION: absolute" from = "0,0" to = "0,16">
																									 <v:stroke dashstyle ="ShortDot"></v:stroke>
																									 </v:line>
																									 <v:line style="POSITION: absolute" from = "0,10" to = "10,10">
																									 <v:stroke dashstyle ="ShortDot"></v:stroke>
																									 </v:line>
																									
																									 <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'> 
																									 <v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset = '15pt,0pt,0pt,-10pt'>
																									 已完成单证任務
																									 </v:TextBox>
																									 <v:Extrusion backdepth='5pt' on='true'/>
																									 </v:rect>
																									 </font>                                                                                                                                                                                                     
																									 </td>                                                                                                                                                                                                                
																									 </tr>       
					 */
			%>
			<%
				// 1。11。4。已提交单证任務项
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="2103font" color="#000000" onClick="maingo('2103','sysMenu','已提交单证任務','/claim/wfLogQuery.do?nodeType=certi&status=4','2103')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="pub.taskSubmittedDocument" />
								<%--已提交单证任務--%>
							</v:TextBox>
							<v:Extrusion backdepth='5pt' on='true' />
						</v:rect>
					</font>
				</td>
			</tr>
			<%
				// 1。11。5。撤销单证任務
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="2104font" color="#000000" onClick="maingo('2104','sysMenu','删除单证任務','workAffair/document/unprocessed_docs.jsp','2104')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="pub.deleteDocumentTask" />
								<%--删除单证任務--%>
							</v:TextBox>
							<v:Extrusion backdepth='5pt' on='true' />
						</v:rect>
					</font>
				</td>
			</tr>
			<%
				// 1。11。10。注销/拒赔
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="2110font" color="#000000" onClick="maingo('2110','sysMenu',' 申请註銷/拒赔','/claim/wfLogQuery.do?status=-1&nodeType=certi','2110')" style="cursor: 'hand'"> <v:line
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
				//是否有单证读权限
				checkCode = AppConfig.get("sysconst.CHECKCODE_READ");
				taskCode = AppConfig.get("sysconst.TASKCODE_LPLA");
				checkPower = false;
				checkPower = uiPowerAction.checkPower(userCode, riskCode, taskCode, checkCode);
				if (checkPower) {
			%>
			<%
				// 1。11。6。 单证任務状态统计项
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="2105font" color="#000000" onClick="maingo('2105','sysMenu',' 单证任務状态统计','/claim/common/claimstatus/ClaimStatusStatEdit.jsp?nodeType=certi','2105')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="pub.documentTaskState" />
								<%--单证任務状态统计--%>
							</v:TextBox>
							<v:Extrusion backdepth='5pt' on='true' />
						</v:rect>
					</font>
				</td>
			</tr>
			<%
				// 1。11。8。查询单证任務项
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="2107font" color="#000000" onClick="maingo('2107','sysMenu',' 单证查询','/claim/common/certify/CertifyQueryEdit.jsp','2107')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="pub.documentQuery" />
								<%--单证查询--%>
							</v:TextBox>
							<v:Extrusion backdepth='5pt' on='true' />
						</v:rect>
					</font>
				</td>
			</tr>
			<%
				// 1。11。9。待处理任務
			%>
			<!--
                          <tr>
                            <td nowrap></td>
                            <td align=left nowrap>
                              <font ID="2108font" color="#000000" onClick="maingo('2108','sysMenu','待处理任務','/claim/certifyQuery.do?editType=WaitToDo','2108')"  style="cursor:'hand'" >
                                          <v:line style="POSITION: absolute" from = "0,0" to = "0,10">
                                          <v:stroke dashstyle ="ShortDot"></v:stroke>
										  </v:line>
										  <v:line style="POSITION: absolute" from = "0,10" to = "10,10">
										     <v:stroke dashstyle ="ShortDot"></v:stroke>
										  </v:line>
										  
										  <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'> 
											   <v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset = '15pt,0pt,0pt,-10pt'>
											   待处理任務
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
		</table>
		<%
			//完成第三级文件夹
		%>
	</td>
</tr>
