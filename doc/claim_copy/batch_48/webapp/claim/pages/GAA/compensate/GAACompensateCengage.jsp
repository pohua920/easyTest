<%--
****************************************************************************
* DESC       ：特别约定显示画面
* AUTHOR     ：理赔组
* CREATEDATE ： 2004-05-20
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<!--建立显示的录入条，可以收缩显示的-->
<%@ include file="/common/taglibs.jsp"%>
<table class="common" align="center" width="100%">
	<tr class=mline>
		<td class="subformtitle" colspan="4" style="text-align: left">
			<img style="cursor: hand;" src="/claim/images/butCollapseBlue.gif" name="EngageImg" onclick="showPage(this,spanEngage)">
			<s:text name="commonAcci.compensate.specificallyAgreed" />
			<br>
			<%---特别约定  --%>
			<span id="spanEngage" style="display: none">
				<table class="sub" style='width: 100%' id="Engage">
					<thead>
						<tr>
							<td class="centertitle">
								<s:text name="regist.prpLregist.serialNo" />
							</td>
							<%---序号  --%>
							<td class="centertitle">
								<s:text name="certainLoss.prpLcheck.specializeCode" />
							</td>
							<%--- 特约代码 --%>
							<td class="centertitle" colspan="3">
								<s:text name="certainLoss.prpLcheck.specializeName" />
							</td>
							<%--- 特约名称 --%>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${prpCengage.prpCengageList}" var="prpCengageTemp" varStatus="prpCengageTempStatus">
							<tr class=oddrow>
								<td class="centertitle">${pageScope.prpCengageTempStatus.count }</td>
								<td class="centertitle">${prpCengageTemp.clauseCode }</td>
								<td class="centertitle">${prpCengageTemp.clauses }</td>
								<td class="centertitle">
									<input type=button ACCESSKEY="." num=${pageScope.prpCengageTempStatus.index } value='...' class="smallbutton" name='button_Engage_Open_Context' onclick="buttonOnClick1(this);">
									<span id="span_Engage_Context" style='width: 520; display: none; position: absolute; background-color: FFFFFF;'>
										<table class=sub>
											<tr>
												<td class="prompt" colspan="6">${prpCengageTemp.context }</td>
											</tr>
											<tr>
												<td colspan=2 align=center>
													<%---确定  --%>
													<input type=button name='button_Engage_Close_Context' class="button" value='(${pageScope.prpCengageTempStatus.count })<s:text name="button.determine.value"/>'  ACCESSKEY="O" onclick="hideSubPage(this, 'span_Engage_Context');">
												</td>
											</tr>
										</table>
									</span>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				
				</table>
			</span>
		</td>
	</tr>
</table>
