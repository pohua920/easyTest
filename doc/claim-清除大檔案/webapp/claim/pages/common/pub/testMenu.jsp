<%@ page import="com.sinosoft.claim.dto.domain.PrpLmenuDto"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.Iterator"%>
<%
	PrpLmenuDto prpLmenuDto = (PrpLmenuDto) session.getAttribute("prpLmenuDto");
	Collection menuLevelList1 = null; //一级菜单
	Collection menuLevelList2 = null; //二级菜单
	Collection menuLevelList3 = null; //三级菜单

	//modify by mengdongdong 2006-03-04 三级菜单分别获取 
	if (prpLmenuDto != null) {
		menuLevelList1 = prpLmenuDto.getMenuLevel1();
		menuLevelList2 = prpLmenuDto.getMenuLevel2();
		menuLevelList3 = prpLmenuDto.getMenuLevel3();
	}

	PrpLmenuDto prpLmenuDtoTemp1 = null;
	PrpLmenuDto prpLmenuDtoTemp2 = null;
	PrpLmenuDto prpLmenuDtoTemp3 = null;
	if (menuLevelList1 != null) {
		for (Iterator iter = menuLevelList1.iterator(); iter.hasNext();) {
			prpLmenuDtoTemp1 = (PrpLmenuDto) iter.next();
			if (prpLmenuDtoTemp1.getFuncLevel().trim().equals("1")) {
				checkPower = false;
				checkPower = uiPowerInterface.checkPower(userCode, prpLmenuDtoTemp1.getTaskCode(), prpLmenuDtoTemp1.getCheckCode(), riskCode);
				if (checkPower) {
%>
<!-- 设置文件夹第一层-->
<table border=0 cellspacing=0 cellpadding=2 class="menu">
	<tr>
		<td nowrap>
			<span name="notopimg" ID="<%=prpLmenuDtoTemp1.getFuncID()%>" />
		</td>
		<td align=left nowrap
			onClick="clickEvent(document.all('<%=prpLmenuDtoTemp1.getFuncID()%>'), '/claim/images/<%=prpLmenuDtoTemp1.getImageExpand()%>','/claim/images/<%=prpLmenuDtoTemp1.getImageCollapse()%>');"
			style="cursor: 'default'">
			<img src="/claim/images/cpArrowFunction.gif"> <font ID="<%=prpLmenuDtoTemp1.getFuncID()%>font" color="#000000" value="<%=prpLmenuDtoTemp1.getFuncCName()%>"><%=prpLmenuDtoTemp1.getFuncCName()%></font>
		</td>
	</tr>
	<tr name="notop" ID="<%=prpLmenuDtoTemp1.getFuncID()%>p" STYLE="Display: 'none'">
		<td nowrap></td>
		<td nowrap>
			<%
				//开始第二级文件夹
			%>
			<%
				// 建立文件夹第二层
			%>
			<table border=0 cellspacing=0 cellpadding=5>
				<%
					//是否有工作流管理执行权限
									checkCode = prpLmenuDtoTemp1.getTaskCode();
									taskCode = prpLmenuDtoTemp1.getCheckCode();
									checkPower = false;
									for (Iterator iter2 = menuLevelList2.iterator(); iter2.hasNext();) {
										prpLmenuDtoTemp2 = (PrpLmenuDto) iter2.next();
										if (prpLmenuDtoTemp2.getParentID().equals(prpLmenuDtoTemp1.getFuncID())) {
											checkPower = uiPowerInterface.checkPower(userCode, prpLmenuDtoTemp2.getTaskCode(), prpLmenuDtoTemp2.getCheckCode(), riskCode);
											if (checkPower) {
				%>
				<tr class="menu2">
					<%
						// 设置文件夹第二层的前面的那个加号/减号图标
					%>
					<td class="menu2" nowrap>
						<span name="notopimg" ID="<%=prpLmenuDtoTemp2.getFuncID()%>" />
					</td>
					<%
						// 设置文件夹第二层显示图标和名称
					%>
					<td align=left nowrap class="menu2"
						onClick="clickEvent(document.all('<%=prpLmenuDtoTemp2.getFuncID()%>'), '/claim/images/<%=prpLmenuDtoTemp2.getImageExpand()%>','/claim/images/<%=prpLmenuDtoTemp2.getImageCollapse()%>');"
						style="cursor: 'default'">
						<img src="/claim/images/ImgVisaMenu_2.gif">
						<%
							String fontColor = "#3B6492";
														//流程查询菜单及核赔任务查询菜单颜色要改变
														if ("1s".equals(prpLmenuDtoTemp2.getFuncID()) || "1m".equals(prpLmenuDtoTemp2.getFuncID())) {
															fontColor = "#400040";
														}
						%>
						<font ID="<%=prpLmenuDtoTemp2.getFuncID()%>font" color="<%=fontColor%>" value="<%=prpLmenuDtoTemp2.getFuncCName()%>"><%=prpLmenuDtoTemp2.getFuncCName()%></font>
					</td>
				</tr>
				<tr name="notop" ID="<%=prpLmenuDtoTemp2.getFuncID()%>p" STYLE="Display: 'none'">
					<td nowrap></td>
					<td nowrap>
						<!--  建立文件夹第三层-->
						<table border=0 cellspacing=0 cellpadding=0 class="menu">
							<tr name="notop" ID="<%=prpLmenuDtoTemp2.getFuncID()%>0p" STYLE="Display: 'none'">
								<td nowrap></td>
								<%
									for (Iterator iter3 = menuLevelList3.iterator(); iter3.hasNext();) {
																	prpLmenuDtoTemp3 = (PrpLmenuDto) iter3.next();
																	if (prpLmenuDtoTemp3.getParentID().equals(prpLmenuDtoTemp2.getFuncID())) {
																		checkPower = false;
																		checkPower = uiPowerInterface.checkPower(userCode, prpLmenuDtoTemp3.getTaskCode(), prpLmenuDtoTemp3.getCheckCode(), riskCode);
																		if (checkPower) {
								%>
							
							<tr>
								<td nowrap height="22px"></td>
								<!-- // 设置文件夹第三层显示图标和名称-->
								<td align=left nowrap class="menu3"
									onClick="maingo_new('<%=prpLmenuDtoTemp3.getFuncID()%>','sysMenu','<%=prpLmenuDtoTemp3.getFuncCName()%>','<%=prpLmenuDtoTemp3.getParam()%>','<%=prpLmenuDtoTemp3.getFuncID()%>','<%=prpLmenuDtoTemp2.getFuncID()%>','<%=prpLmenuDtoTemp1.getFuncID()%>')">
									<img src="/claim/images/imgIcon-3.gif"> <font ID="<%=prpLmenuDtoTemp3.getFuncID()%>font" color="#6C90DA" value="<%=prpLmenuDtoTemp3.getFuncCName()%>"><%=prpLmenuDtoTemp3.getFuncCName()%></font>
								</td>
							</tr>
							<%
								} //结束第三级的checkPower
																} //结束第三级的if
															} //结束第三级的for
							%>
						</table>
						<!-- 完成第三级文件夹 -->
					</td>
				</tr>
				<%
					}
										}
									}//第二层
				%>
			</table>
		</td>
	</tr>
	<%
		}//第一级菜单checkpower
				}//第一级菜单
			}
		}
	%>
</table>
