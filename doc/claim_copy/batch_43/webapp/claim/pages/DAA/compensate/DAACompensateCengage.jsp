<%@	page contentType="text/html; charset=GBK" language="java"%>
<%--
****************************************************************************
* DESC       ：特别约定显示画面
* AUTHOR     ：中科软
* CREATEDATE ： 2013-01-11
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ include file="/common/taglibs.jsp"%>
<script language="javascript">
	//按钮单击事件，用於条款的显示
	function buttonOnClick1(fieldObject) {
		var intIndex = parseInt(fieldObject.num);
		var spanId = 'span_Engage_Context';
		if (isNaN(fm.button_Engage_Open_Context.length)) {
		} else {//多行
			spanId = 'span_Engage_Context' + "[" + intIndex + "]";
		}
		showSubPage2(spanId);
	}
	//显示输入框
	//leftMove 默认值0，坐标左移leftMove
	function showSubPage2(spanID, leftMove) {
		var intLeftMove = (leftMove == null ? 0 : leftMove);
		var span = eval(spanID);
		var strTemp = span.id;
		var ex = window.event.clientX + document.body.scrollLeft; //得到事件的坐标x
		var ey = window.event.clientY + document.body.scrollTop; //得到事件的坐标y

		ex = ex - 520;

		if (ex < 0) {
			ex = 0;
		}
		ex = ex - intLeftMove;

		span.style.left = ex;
		span.style.top = ey;
		span.style.display = '';
	}
</script>
<input type="hidden" name="passDay" value="${passDay}">
<!--建立显示的录入条，可以收缩显示的-->
<table class="common" align="center" width="100%">
	<tr class=mline>
		<td class="common" colspan="4" style="text-align: left">
			<img style="cursor: hand;" src="${ctx }/images/butCollapseBlue.gif" name="EngageImg" onclick="showPage(this,spanEngage)">
			<%--特别约定 --%>
			<s:text name="certainLoss.prpLcheck.specialize" />
			<br> <span style="display: none">
				<table id="Engage_Data">
					<tbody>
					</tbody>
				</table>
			</span> <span id="spanEngage" style="display: none">
				<table class=common cellpadding="5" cellspacing="1" id="Engage">
					<thead>
						<tr>
							<td class="centertitle">
								<%-- 序号--%>
								<s:text name="certainLoss.prpLcheck.prpLcheckNumber" />
							</td>
							<td class="centertitle">
								<%-- 特约代码 --%>
								<s:text name="certainLoss.prpLcheck.specializeCode" />
							</td>
							<td class="centertitle" colspan="3">
								<%-- 特约名称 --%>
								<s:text name="certainLoss.prpLcheck.specializeName" />
							</td>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${requestScope.prpCengage.prpCengageList}" var="prpCengageTemp" varStatus="prpCengageTempStatus">
							<tr>
								<td class="centertitle">${pageScope.prpCengageTemp.id.serialNo}</td>
								<td class="centertitle">${pageScope.prpCengageTemp.clauseCode}</td>
								<td class="centertitle">${pageScope.prpCengageTemp.clauses}</td>
								<td class="centertitle">
									<input type=button ACCESSKEY="." num=${pageScope.prpCengageTempStatus.index } value='...' name='button_Engage_Open_Context' onclick="buttonOnClick1(this);">
									<span id="span_Engage_Context" style='width: 520; display: none; position: absolute; background-color: FFFFFF;'>
										<table class="common">
											<tr>
												<td class="prompttitle" colspan="6">
													<%-- 特别约定详细信息 --%>
													<s:text name="certainLoss.prpLcheck.prpLcheckspecializeInfo" />
												</td>
											</tr>
											<tr>
												<td class="prompt" colspan="6">${pageScope.prpCengageTemp.context}</td>
											</tr>
											<tr>
												<td colspan=6 class="common">
													<input type=button class=button name='button_Engage_Close_Context' value="<s:text name='button.close.value' />" ACCESSKEY="O" onclick="hideSubPage(this,'span_Engage_Context')">
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
