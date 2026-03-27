
<%
	//1.3 调度任務处理
%>
<tr>
	<%
		// 设置文件夹第二层的前面的那个加号/减号图标
	%>
	<td nowrap>
		<img name="notopimg" ID="13" style="cursor: 'hand';" onmouseup="clickEvent(document.all('13'), '/claim/images/treeFoderclassOpen.gif','/claim/images/treeFoderclass2.gif');"
			src="/claim/images/treeAdd.gif" width="9" height="9">
	</td>
	<%
		// 设置文件夹第二层显示图标和名称
	%>
	<td align=left nowrap onClick="clickEvent(document.all('13'), '/claim/images/treeFoderclassOpen.gif','/claim/images/treeFoderclass2.gif');" style="cursor: 'default'">
		<img name="notopimgII" ID="13II" src="/claim/images/treeFoderclass2.gif" align="absmiddle" width="16" height="16"> <font ID="13font" color="#000000"><s:text name="pub.schedulTaskDeal" /></font>
		<%--调度任務处理--%>
	</td>
</tr>
<tr name="notop" ID="13p" STYLE="Display: 'none'">
	<td nowrap></td>
	<td nowrap>
		<%
			// 建立文件夹第三层
		%>
		<table border=0 cellspacing=0 cellpadding=0 class="menu">
			<%
				//是否有调度写权限
				checkCode = AppConfig.get("sysconst.CHECKCODE_WRITE");
				taskCode = AppConfig.get("sysconst.TASKCODE_LPCK");
				checkPower = false;
				checkPower = uiPowerAction.checkPower(userCode, riskCode, taskCode, checkCode);
				if (checkPower) {
			%>
			<tr name="notop" ID="130p" STYLE="Display: 'none'">
				<td nowrap></td>
				<%
					// 移到nowrap文件夹第三层
				%>
				<%
					// 1。3。1。处理调度登记项
				%>
			
			<tr>
				<td nowrap height="22px"></td>
				<td align=left nowrap>
					<font ID="1300font" color="#000000" onClick="maingo('1300','sysMenu','待处理调度登记','/claim/scheduleRegistNew.do','1300')" style="cursor: 'hand'"> <v:line style="POSITION: absolute" from="0,0"
							to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text name="pub.schedulProcessed" />
								<%--待处理调度登记--%>
							</v:TextBox>
							<v:Extrusion backdepth='5pt' on='true' />
						</v:rect>
					</font>
				</td>
			</tr>
			<%
				// 1。3。4。已提交调度任務项
			%>
			<tr>
				<td nowrap></td>
				<td align=left nowrap>
					<font ID="1303font" color="#000000" onClick="maingo('1303','sysMenu','已提交调度任務','/claim/wfLogQuery.do?status=4&nodeType=sched','1303')" style="cursor: 'hand'"> <v:line
							style="POSITION: absolute" from="0,0" to="0,16">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:line style="POSITION: absolute" from="0,10" to="10,10">
							<v:stroke dashstyle="ShortDot"></v:stroke>
						</v:line> <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'>
							<v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset='15pt,0pt,0pt,-10pt'>
								<s:text
									name="menu.attemper.submitTask /><%--已提交调度任務--%>
											   </v:TextBox>
											   <v:Extrusion backdepth='5pt' on='true'/>
										  </v:rect>
                                     </font>
                                 </td>
                              </tr>



                               <%// 1。3。9。调度取回任務%>
                              <tr>
                                 <td nowrap></td>
                                 <td align=left nowrap>
                                     <font ID="1308font" color="#000000" onClick="maingo('1308','sysMenu',' 调度取回任務','/claim/common/schedule/ScheduleGetBackQueryEdit.jsp','1308')"  style="cursor:'hand'" >
                                          <v:line style="POSITION: absolute" from = "0,0" to = "0,16">
                                             <v:stroke dashstyle ="ShortDot"></v:stroke>
										  </v:line>
										  <v:line style="POSITION: absolute" from = "0,10" to = "10,10">
										     <v:stroke dashstyle ="ShortDot"></v:stroke>
										  </v:line>
										  
										  <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'> 
											   <v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset = '15pt,0pt,0pt,-10pt'>
											   <s:text name="pub.taskSchedulRetrieve" /><%--调度取回任務--%>
											   </v:TextBox>
											   <v:Extrusion backdepth='5pt' on='true'/>
										  </v:rect>
                                     </font>
                                 </td>
                              </tr>


                              <%// 1。3。5。删除调度任務%>
                              <tr>
                                 <td nowrap></td>
                                 <td align=left nowrap>
                                     <font ID="1304font" color="#000000" onClick="maingo('1304','sysMenu',' 删除调度任務','/claim/common/schedule/ScheduleCancelQueryEdit.jsp','1304')"  style="cursor:'hand'" >
                                          <v:line style="POSITION: absolute" from = "0,0" to = "0,16">
                                             <v:stroke dashstyle ="ShortDot"></v:stroke>
										  </v:line>
										  <v:line style="POSITION: absolute" from = "0,10" to = "10,10">
										     <v:stroke dashstyle ="ShortDot"></v:stroke>
										  </v:line>
										  
										  <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'> 
											   <v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset = '15pt,0pt,0pt,-10pt'>
											   <s:text name="pub.deleteSchedulRetrieve" /><%--删除调度任務--%>
											   </v:TextBox>
											   <v:Extrusion backdepth='5pt' on='true'/>
										  </v:rect>
                                     </font>
                                 </td>
                              </tr>
              <%}%>
            <%//是否有调度读权限
				checkCode = AppConfig.get("sysconst.CHECKCODE_READ");
				taskCode = AppConfig.get("sysconst.TASKCODE_LPCK");
				checkPower = false;
				checkPower = uiPowerAction.checkPower(userCode, riskCode, taskCode, checkCode);
				if (checkPower) {%>   
			          <%// 1。3。6。查勘调度处理情况查询%>
                              <tr>
                                 <td nowrap></td>
                                 <td align=left nowrap>
                                     <font ID="1305font" color="#000000" onClick="maingo('1305','sysMenu','查勘情况查询','/claim/common/check/CheckScheduleQueryEdit.jsp','1305')"  style="cursor:'hand'" >
                                          <v:line style="POSITION: absolute" from = "0,0" to = "0,16">
                                             <v:stroke dashstyle ="ShortDot"></v:stroke>
										  </v:line>
										  <v:line style="POSITION: absolute" from = "0,10" to = "10,10">
										     <v:stroke dashstyle ="ShortDot"></v:stroke>
										  </v:line>
										  
										  <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'> 
											   <v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset = '15pt,0pt,0pt,-10pt'>
											   <s:text name="pub.surveyQuery1" /><%--查勘情况查询--%>
											   </v:TextBox>
											   <v:Extrusion backdepth='5pt' on='true'/>
										  </v:rect>
                                     </font>
                                 </td>
                              </tr>              
                              <%// 1。3。8。查询调度任務项%>
                              <tr>
                                 <td nowrap></td>
                                 <td align=left nowrap>
                                     <font ID="1307font" color="#000000" onClick="maingo('1307','sysMenu',' 调度查询','/claim/common/schedule/ScheduleQueryEdit.jsp','1307')"  style="cursor:'hand'" >
                                          <v:line style="POSITION: absolute" from = "0,0" to = "0,16">
                                             <v:stroke dashstyle ="ShortDot"></v:stroke>
										  </v:line>
										  <v:line style="POSITION: absolute" from = "0,10" to = "10,10">
										     <v:stroke dashstyle ="ShortDot"></v:stroke>
										  </v:line>
										  
										  <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'> 
											   <v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset = '15pt,0pt,0pt,-10pt'>
											   <s:text name="pub.schedulingQuery" /><%--调度查询--%>
											   </v:TextBox>
											   <v:Extrusion backdepth='5pt' on='true'/>
										  </v:rect>
                                     </font>
                                 </td>
                              </tr>
                             
                              <%// 1。3。7。调度任務状态统计%>
                              <tr>
                                 <td nowrap></td>
                                 <td align=left nowrap>
                                     <font ID="1306font" color="#000000" onClick="maingo('1306','sysMenu',' 调度任務状态统计','/claim/common/claimstatus/ClaimStatusStatEdit.jsp?nodeType=sched','1306')"  style="cursor:'hand'" >
                                         <v:line style="POSITION: absolute" from = "0,0" to = "0,10">
                                             <v:stroke dashstyle ="ShortDot"></v:stroke>
										  </v:line>
										  <v:line style="POSITION: absolute" from = "0,10" to = "10,10">
										     <v:stroke dashstyle ="ShortDot"></v:stroke>
										  </v:line>
										  
										  <v:rect style='position:relative;top:0;left:10;width:10;height:10;z-index:8;' fillcolor='#FFCCFF' strokeColor='blue'> 
											   <v:TextBox style='MARGIN-TOP: 7.406pt; FONT-SIZE: 11pt; LEFT:auto; MARGIN-LEFT: 4.562pt; WIDTH: 83.875pt; TOP:auto; HEIGHT: 25.687pt' inset = '15pt,0pt,0pt,-10pt'>
											   <s:text name="menu.attemper.computTask" /><%--调度任務状态统计--%>
											   </v:TextBox>
											   <v:Extrusion backdepth='5pt' on='true'/>
										  </v:rect>
                                     </font>
                                 </td>
                              </tr>

                           </tr>
              <%}%>
                       </table>
                  <%//完成第三级文件夹%>
                 </td>

               </tr>   