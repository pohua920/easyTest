<%--
****************************************************************************
* DESC	   :车辆调度标底信息页面
* AUTHOR	 :中科软
* CREATEDATE : 2013-01-22
* MODIFYLIST :   Name	   Date			Reason/Contents
*		  ------------------------------------------------------
****************************************************************************
--%>
<%@ include file="/common/taglibs.jsp"%>
<%-- 多行输入展现域的模板 --%>
<%--多行输入自定义JavaScript方法域--%>
<!--建立显示的录入条，不可以收缩显示的-->
<input type=text readonly class="readonly" name="prpLscheduleItemFinishSchedule" value="${param.finishSubmit}">
<%-- 多行输入展现域 --%>
<table class=common cellpadding="0" cellspacing="1">
	<tr>
		<td>
			<input type="hidden" value="定损调度任務${strtitleTemp}处理" />
		</td>
	</tr>
	<tbody>
		<%--
			int index = 0;
			int rowspan = 7; //作为序号合並使用的
			int personrowspan = 7;
			String checkValue = "";
			String saveType = "getBackers";
			String haveCheck = ""; //判断是否已经选择调度
			String haveCheckdisable = ""; //判断是否已经调度过了。
			String itemDis = ""; //显示标的名
			String ifreadonly = ""; //默认不是只读的
			String checkTypeDisplay = "style='dipslay:none'"; //默认没有查勘现场等的显示
			String lossItemCode = request.getParameter("lossItemCode");// 只有在editType=GETBACK的时候，决定哪一个才可以修改的
			//判断人员选择的
			String selectPersonFunction = "CertaHanderCode";
			String getBackDisp = ""; //设置改派显示的
			String strLossItemCode = ""; //车辆标的序号
		--%>
		<%--思路：如果是改派，只显示选择的那一行，不显示其他的调度信息--%>
		<%--
			PrpLscheduleItem prpLscheduleItem1 = (PrpLscheduleItem) request.getAttribute("prpLscheduleItem");
		--%>
		<c:set var="index" value="0" scope="page"/>
		<c:forEach var="scheduleItem" items="${prpLscheduleItem.scheduleItemList}" varStatus="indexScheduleItem">
			<!-- 插入涉案车辆内容-->
			<%--
				haveCheckdisable = ""; //默认没有被调度过
					haveCheck = ""; //默认没有选择调度
					itemDis = "";//默认什么都不显示
					ifreadonly = ""; //默认不是只读的
					getBackDisp = ""; //改派的显示
					strLossItemCode = "";
			--%>
			<c:set var="getBackDisp" value="" scope="page"/>
			<c:set var="haveCheck" value="" scope="page"/>
			<c:set var="haveCheckdisable" value="" scope="page"/>
			<c:set var="ifreadonly" value="" scope="page"/>
			<tr>
			<%--
				PrpLscheduleItem prpLscheduleItem = (PrpLscheduleItem) ((ArrayList) prpLscheduleItem1.getScheduleItemList()).get(index);
					if (!"GETBACKEDIT".equals(saveType1) || ("GETBACKEDIT".equals(saveType1) && (prpLscheduleItem.getId().getItemNo() + "").equals(lossItemCode))) {
						//判断人员选择用哪一个
						selectPersonFunction = "CertaHanderCode";
						if (prpLscheduleItem.getId().getItemNo() == 0)
							selectPersonFunction = "WoundHanderCode";
						if (prpLscheduleItem.getId().getItemNo() == -1)
							selectPersonFunction = "PropHanderCode";
			--%>
			<c:if test="${'GETBACKEDIT'!=saveType1||('GETBACKEDIT'==saveType1&&scheduleItem.nextNodeNo==param.nodeType&&scheduleItem.id.itemNo==param.lossItemCode)}">
				<input name="prpLscheduleItemOperatorCode" type="hidden" description="操作员" value="${scheduleItem.operatorCode}">
				<input name="prpLscheduleItemInputDate" type="hidden" description="操作时间" value="${scheduleItem.inputDate}">
				
				<c:if test="${scheduleItem.nextNodeNo == 'propc'}">
					<td class="title" style="width: 20%" rowspan=8>
				</c:if>
				<c:if test="${scheduleItem.nextNodeNo != 'propc'}">
					<td class="title" style="width: 20%" rowspan="7">
				</c:if>
				<div align="center">
					<c:if test="${scheduleItem.selectSend == 1}">
						<c:set var="haveCheck" value="checked" scope="page"/>
					</c:if>
					<c:if test="${scheduleItem.surveyTimes >= 1}">
						<c:set var="haveCheckdisable" value="disabled" scope="page"/>
						<c:set var="ifreadonly" value="readonly" scope="page"/>
					</c:if>
					<%--如果是改派，则可以进行修改操作，但是只能指定的那行做修改的--%>
					<c:if test="${prpLscheduleMainWF.saveType == 'GETBACKEDIT'}">
						<c:if test="${scheduleItem.id.itemNo == param.lossItemCode}">
							<c:set var="ifreadonly" value="" />
							<c:set var="getBackDisp" value="(可改派)" />
						</c:if>
					</c:if>
					<input type="checkbox" class="readonly" name="checkYesNo" style="width: 20px" value="ON" ${haveCheck } ${haveCheckdisable }>
					&nbsp;
					<c:set var="itemDis" value="" scope="page"/>
					<c:choose>
						<c:when test="${scheduleItem.nextNodeNo == 'certa'}">
							<c:if test="${scheduleItem.id.itemNo == '1'}">
								<c:set var="itemDis" value="標的車(${scheduleItem.id.itemNo}):" scope="page"/>
							</c:if>
							<c:if test="${scheduleItem.id.itemNo > '1'}">
								<c:set var="itemDis" value="三者車(${scheduleItem.id.itemNo}):" scope="page"/>
							</c:if>
						</c:when>
						<c:when test="${scheduleItem.nextNodeNo == 'wound'}">
							<c:set var="itemDis" value="人傷:" scope="page"/>
						</c:when>
						<c:otherwise>
							<c:set var="itemDis" value="" scope="page"/>
						</c:otherwise>
					</c:choose>
					${itemDis }${scheduleItem.licenseNo}${getBackDisp }
					<br>
					</td>
					<input type="hidden" class="readonlyno" readonly name="prpLscheduleItemItemNo" value="${scheduleItem.id.itemNo}">
					<input name="prpLscheduleItemLicenseNo" type="hidden" description="號牌號碼" value="${scheduleItem.licenseNo}">
				</div>
				</td>
				<td class="title" style="width: 11%">
					<%-- 定损处理单位 --%>
					<s:text name="certainLoss.prpLscheduleMainWF.prpLscheduleItemScheduleObjectName" />
				</td>
				<td class="input" colspan=6>
					<input type=text class="codecode" name="prpLscheduleItemScheduleObjectID" num="${index }" id="${index }" style="width: 20%" title="具體單位" value="${scheduleItem.scheduleObjectID}"
						<c:if test="${ifreadonly != 'readonly' }">
						  ondblclick="dbclickComCodeByProvinceCode(this,'dbclick','0,1','Y','${provinceCode}','CertainLoss','${prpLscheduleMainWF.saveType}');"
						  onkeyup= "dbclickComCodeByProvinceCode(this,'keyup','0,1','Y','${provinceCode}','CertainLoss','${prpLscheduleMainWF.saveType}');"
						  onchange="dbclickComCodeByProvinceCode(this,'change','0,1','Y','${provinceCode}','CertainLoss','${prpLscheduleMainWF.saveType}');"
						</c:if>
						<c:out value="${ifreadonly}"/>>
					<input type=text style="width: 69%" class="codecode" name="prpLscheduleItemScheduleObjectName" id="${index }" title="具體單位" style="width:50%" value="${scheduleItem.scheduleObjectName}"
						<c:if test="${ifreadonly != 'readonly' }">
						  ondblclick="dbclickComCodeByProvinceCode(this,'dbclick','-1,0','Y','${provinceCode}','CertainLoss','${prpLscheduleMainWF.saveType}');"
						  onkeyup= "dbclickComCodeByProvinceCode(this,'keyup','-1,0','Y','${provinceCode}','CertainLoss','${prpLscheduleMainWF.saveType}');"
						  onchange="dbclickComCodeByProvinceCode(this,'change','-1,0','Y','${provinceCode}','CertainLoss','${prpLscheduleMainWF.saveType}');"
						 </c:if>
						<c:out value="${ifreadonly}"/>>
					<img src="/claim/images/bgMarkMustInput.jpg">
			</tr>
			<c:set var="checkTypeDisplay" value="" />
			<c:if test="${scheduleItem.nextNodeNo=='certa'}">
				<%--//默认没有查勘现场等的显示 --%>
				<c:set var="checkTypeDisplay" value="style='display:none'" />
			</c:if>
			<tr <c:out value="${checkTypeDisplay}"/>>
				<input type=hidden class="readonlyno" readonly name="prpLscheduleItemInsureCarFlag" value="${scheduleItem.insureCarFlag}">
				<c:if test="${scheduleItem.nextNodeNo=='certa'}">
					<input type=hidden name="prpLscheduleItemCommendRepairFactoryName" value="${scheduleItem.commendRepairFactoryName}">
					<c:if test="${scheduleItem.surveyTimes == 0 }">
						<select name="surveyType" style="display: none">
							<option value="1" ${scheduleItem.surveyType=="1"?selected:""}>
								<s:text name="certainLoss.prpLscheduleMainWF.firstLocale" />
							</option>
							<%-- 第一现场 --%>
							<option value="0" ${scheduleItem.surveyType=="0"?selected:""}>
								<s:text name="certainLoss.prpLscheduleMainWF.NfirstLocale" />
							</option>
							<%-- 非第一现场 --%>
						</select>
					</c:if>
					<c:if test="${scheduleItem.surveyTimes != 0 }">
						<select name="surveyType" style="display: none">
							<c:if test="${scheduleItem.surveyType =='1'}">
								<option value="1" ${scheduleItem.surveyType=="1"?selected:""}>
									<s:text name="certainLoss.prpLscheduleMainWF.firstLocale" />
								</option>
								<%-- 第一现场 --%>
							</c:if>
							<c:if test="${scheduleItem.surveyType =='0'}">
								<option value="0" ${scheduleItem.surveyType=="0"?selected:""}>
									<s:text name="certainLoss.prpLscheduleMainWF.NfirstLocale" />
								</option>
								<%-- 非第一现场 --%>
							</c:if>
						</select>
					</c:if>
					<td class="title" style="width: 19%">
						<%-- 是否为标的车辆 --%>
						<s:text name="certainLoss.prpLscheduleMainWF.carRemark" />
					</td>
					<td class="input" style="width: 10%" colspan="3">
						<c:if test="${scheduleItem.insureCarFlag == 1}">
							<%-- 是 --%>
							<s:text name="certainLoss.prpLscheduleMainWF.yes" />
						</c:if>
						<c:if test="${scheduleItem.insureCarFlag != 1}">
							<%--否 --%>
							<s:text name="certainLoss.prpLscheduleMainWF.no" />
						</c:if>
					</td>
				</c:if>
				<c:if test="${scheduleItem.nextNodeNo=='wound'}">
					<td class="title">
						<%--就医医院名称--%>
						<s:text name="certainLoss.prpLscheduleMainWF.hospitalName" />
					</td>
					<td class="input" colspan=5>
						<input class="input" <c:out value="${ifreadonly}"/> name="prpLscheduleItemCheckSite" style="width: 90%" value="${scheduleItem.checkSite}">
					</td>
				</c:if>
				<c:if test="${scheduleItem.nextNodeNo!='certa'}">
					<input type="hidden" name="surveyType" value="${scheduleItem.surveyType}">
				</c:if>
			</tr>
			<!--Reason:在定损调度页面中增加修理厂报损金额、修理厂联系电话、是否紧急标志位 -->
			<c:if test="${scheduleItem.nextNodeNo=='wound'}">
				<tr>
					<td class="title" style="width: 11%">
						<%--联系人名称--%>
						<s:text name="certainLoss.prpLscheduleMainWF.linkPersonName" />
					</td>
					<td class="input" style="width: 15%">
						<input class="input" <c:out value="${ifreadonly}"/> name="prpLscheduleItemCommendRepairFactoryName" value="${scheduleItem.commendRepairFactoryName}">
					</td>
					<td class="input" style="width: 8%">
						<%--联系人电话--%>
						<s:text name="certainLoss.prpLscheduleMainWF.linkPersonTel" />
					</td>
					<td class="input" style="width: 15%">
						<input style="width: 73%" class="input" <c:out value="${ifreadonly}"/> name="prpLscheduleItemFactoryPhone" value="${scheduleItem.factoryPhone}">
					</td>
				</tr>
				<tr>
					<td class="title" style="width: 11%">
						<%--报损金额--%>
						<s:text name="certainLoss.prpLscheduleMainWF.LossSum" />
					</td>
					<td class="input" colspan=4>
						<input class="input" <c:out value="${ifreadonly}"/> name="prpLscheduleItemFactoryEstimateLoss" value="<fmt:formatNumber value='${scheduleItem.factoryEstimateLoss}' pattern='#'/>">
					</td>
				</tr>
			</c:if>
			<c:if test="${scheduleItem.nextNodeNo=='propc'}">
				<tr>
					<td class="title">
						<%--定损地址--%>
						<s:text name="certainLoss.prpLscheduleMainWF.lossAddress" />
					</td>
					<td class="input" colspan=5>
						<input class="input" <c:out value="${ifreadonly}"/> name="prpLscheduleItemCheckSite" style="width: 72%" value="${scheduleItem.checkSite}">
						<img src="/claim/images/bgMarkMustInput.jpg">
					</td>
				</tr>
				<tr>
					<td class="title" style="width: 11%">
						<%--联系人名称--%>
						<s:text name="certainLoss.prpLscheduleMainWF.linkPersonName" />
					</td>
					<td class="input" style="width: 15%">
						<input class="input" <c:out value="${ifreadonly}"/> name="prpLscheduleItemCommendRepairFactoryName" value="${scheduleItem.commendRepairFactoryName}">
					</td>
					<td class="input" style="width: 8%">
						<%--联系人电话--%>
						<s:text name="certainLoss.prpLscheduleMainWF.linkPersonTel" />
					</td>
					<td class="input" style="width: 15%">
						<input style="width: 73%" class="input" <c:out value="${ifreadonly}"/> name="prpLscheduleItemFactoryPhone" value="${scheduleItem.factoryPhone}">
					</td>
				</tr>
				<tr>
					<td class="title" style="width: 11%">
						<%--报损金额--%>
						<s:text name="certainLoss.prpLscheduleMainWF.LossSum" />
					</td>
					<td class="input" colspan=4>
						<input class="input" <c:out value="${ifreadonly}"/> name="prpLscheduleItemFactoryEstimateLoss" value="<fmt:formatNumber value='${scheduleItem.factoryEstimateLoss}' pattern='#'/>">
					</td>
				</tr>
			</c:if>
			<c:if test="${scheduleItem.nextNodeNo=='certa'}">
				<tr>
					<td class="title">
						<%--定损地址--%>
						<s:text name="certainLoss.prpLscheduleMainWF.lossAddress" />
					</td>
					<td class="input" colspan=5>
						<input class="input" <c:out value="${ifreadonly}"/> name="prpLscheduleItemCheckSite" style="width: 90%" value="${scheduleItem.checkSite}">
						<img src="/claim/images/bgMarkMustInput.jpg">
					</td>
				</tr>
				<tr>
					<td class="title" style="width: 11%">
						<%--报损金额--%>
						<s:text name="certainLoss.prpLscheduleMainWF.LossSum" />
					</td>
					<td class="input" style="width: 15%">
						<input class="input" <c:out value="${ifreadonly}"/> name="prpLscheduleItemFactoryEstimateLoss" value="<fmt:formatNumber value='${scheduleItem.factoryEstimateLoss}' pattern='#'/>">
					</td>
					<td class="input" style="width: 8%">
						<%--修理厂联系电话--%>
						<s:text name="certainLoss.prpLscheduleMainWF.FactoryPhone" />
					</td>
					<td class="input" style="width: 15%">
						<input style="width: 73%" class="input" <c:out value="${ifreadonly}"/> name="prpLscheduleItemFactoryPhone" value="${scheduleItem.factoryPhone}">
					</td>
				</tr>
			</c:if>
			<tr>
				<td class="title">
					<%--案件状态:--%>
					<s:text name="certainLoss.prpLscheduleMainWF.caseState" />
				</td>
				<td class="input" colspan="5">
					<s:select name="exigenceGree" value="#attr.scheduleItem.exigenceGree" listKey="key" listValue="value" list="#request.exigenceGreeList" />
				</td>
			</tr>
			<tr>
				<td class="title">
					<%--定损要点提示:--%>
					<s:text name="certainLoss.prpLscheduleMainWF.cue" />
				</td>
				<td class="input" colspan=6>
					<input class="input" <c:out value="${ifreadonly}"/> name="prpLscheduleItemResultInfo" style="width: 90%" value="${scheduleItem.resultInfo}">
					<input type=hidden name="prpLCheckSelectSend" value="0">
					<input type=hidden name="prpLscheduleItemSelectSend" value="${scheduleItem.selectSend}">
					<!-- 每个定损项目有单独的双代标志,单独的调度中心代码-->
					<input type=hidden name="prpLscheduleItemCommiItemFlag" value="${scheduleItem.commiItemFlag}">
					<input type=hidden name="prpLscheduleItemClaimComCode" value="${scheduleItem.claimComCode}">
					<input type=hidden name="prpLscheduleItemSurveyTimes" value="${scheduleItem.surveyTimes}">
					<input type=hidden name="prpLscheduleItemBookFlag" value="${scheduleItem.bookFlag}">
					<input type=hidden name="prpLscheduleItemScheduleType" value="${scheduleItem.scheduleType}">
					<input type=hidden name="prpLscheduleItemFlag" value="${scheduleItem.flag}">
				</td>
			</tr>
			<tr>
				<td class="title" style="width: 11%">
					<%--定损人员:--%>
					<s:text name="certainLoss.prpLscheduleMainWF.lossPerson" />
				</td>
				<td class="input" colspan=6 Style="">
					<input type="hidden" name="nextNodeNo" value="${scheduleItem.nextNodeNo}">
					<input type=text name="nextHandlerCode" num="${index }" id="${index }" class="codecode" style="width: 20%" maxlength="10" title="操作員" value="${scheduleItem.nextHandlerCode}"
						<c:if test="${ifreadonly != 'readonly' }">
						  ondblclick="dbclickCertainLoss(this,'dbclick','0,1','Y',this);" 
						  onblur="queryByUserCode(this,'${prpLscheduleMainWF.saveType}');" 
						  onkeyup= "dbclickCertainLoss(this,'keyup','0,1','Y',this);" 
						  onchange= "dbclickCertainLoss(this,'change','0,1','Y',this);" 
						 </c:if>
						<c:out value="${ifreadonly}"/>>
					<input type=text name="nextHandlerName" num="${index }" id="${index }" class="codecode" style="width: 69%" title="操作員" value="${scheduleItem.nextHandlerName}"
						<c:if test="${ifreadonly != 'readonly' }"> 
						  ondblclick="dbclickCertainLoss(this,'dbclick','-1,0','N',this);" 
						  onblur="queryByUserCode(this,'${prpLscheduleMainWF.saveType}');" 
						  onkeyup= "dbclickCertainLoss(this,'keyup','-1,0','N',this);" 
						  onchange= "dbclickCertainLoss(this,'change','-1,0','N',this);" 
						 </c:if>
						<c:out value="${ifreadonly}"/>>
				</td>
			</tr>
			<tr>
				<td colspan="7">
					<hr size="2" color="#065498">
				</td>
			</tr>
			<input type="hidden" name="scheduleLossItemCode" value="${param.lossItemCode }">
			<input type=hidden name="prpLscheduleItemScheduleID" value="${scheduleItem.id.scheduleID}">
		</c:if>
			<c:set var="index" value="${index+1}" scope="page"/>
		</c:forEach>
	</tbody>
	<c:if test="${'GETBACKEDIT' eq saveType1}">
		<c:set var="index" value="1" scope="page"/>
	</c:if>
	<input type=hidden name=maxrow value="${index }">
	<input type=hidden name="scheduleType" value="">
	<input type=hidden name="selectLossItemCodeMain" value="0">
</table>
