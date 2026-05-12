<%--
****************************************************************************
* DESC	   ：查勘调度标的处理页面
* AUTHOR	 ：中科软
* CREATEDATE ：2013-01-22
* MODIFYLIST ：   Name	   Date			Reason/Contents
****************************************************************************
--%>
<%@ include file="/common/taglibs.jsp"%>
<c:set var="haveCheck1disable" value="" />
<%--判断是否已经调度过了 --%>
<c:set var="haveCheck1" value="" />
<%--判断是否选过了 --%>
<c:set var="ifreadonly1" value="" />
<%--默认不是只读的 --%>
<c:set var="getBackDisp1" value="" />
<c:if test="${prpLscheduleMainWF.scheduleFlag =='1' }">
	<c:set var="haveCheck1disable" value="disabled" />
	<%--判断是否已经调度过了 --%>
	<c:set var="haveCheck1" value="checked" />
	<%--判断是否选过了 --%>
	<c:if test="${saveType1 != 'GETBACKEDIT' }">
		<c:set var="ifreadonly1" value="readonly" />
		<%--默认不是只读的 --%>
	</c:if>
	<c:if test="${saveType1 == 'GETBACKEDIT' }">
		<c:set var="getBackDisp1" value="(可改派)" />
	</c:if>
</c:if>
<table class=common cellpadding="5" cellspacing="1">
	<tr>
		<td>
			<input type="hidden" value="查勘調度${strtitleTemp}處理" />
		</td>
	</tr>
	<tr>
		<td class="title" style="width: 12%" rowspan="5">
			<div align="center">
				<%-- 标的 --%>
				<input type="checkbox" name="checkScheduleCheckYesNo" class="readonly" style="width: 20px" value="ON" <c:out value="${haveCheck1}"/> <c:out value="${haveCheck1disable}"/>>
				&nbsp;
				<s:text name="compensate.underly" />
				:${prpLscheduleMainWF.licenseNo}
				<c:out value="${getBackDisp1}" />
			</div>
			<input type="hidden" name="checkSelectSend" value="0">
			<input type="hidden" name="prpLscheduleMainWFLicenseNo" class="readonly" readonly style="width: 98%" maxlength=20 description="號牌號碼" value="${prpLscheduleMainWF.licenseNo}">
		</td>
		<td class="title" style="width: 11%">
			<%-- 查勘处理单位 --%>
			<s:text name="certainLoss.prpLscheduleMainWF.prpLscheduleMainWFScheduleObject" />
		</td>
		<td class="input">
			<input type=text class="codecode" name="prpLscheduleMainWFScheduleObjectID" num=-1 style="width: 20%" title="具體單位" value="${prpLscheduleMainWF.scheduleObjectID}" <c:out value="${ifreadonly1}"/>
				<c:if test="${ifreadonly1 != 'readonly'}">
					ondblclick="dbclickComCodeByProvinceCode(this,'dbclick','0,1','Y','${provinceCode}','Check');"
					onkeyup= "dbclickComCodeByProvinceCode(this,'keyup','0,1','Y','${provinceCode}','Check');"
					onchange= "dbclickComCodeByProvinceCode(this,'change','0,1','Y','${provinceCode}','Check');"
				   </c:if>>
			<input type=text class="codecode" name="prpLscheduleMainWFScheduleObjectName" title="具體單位" style="width: 50%" value="${prpLscheduleMainWF.scheduleObjectName}" <c:out value="${ifreadonly1}"/>
				<c:if test="${ifreadonly1 != 'readonly' }">
					ondblclick="dbclickComCodeByProvinceCode(this,'dbclick','-1,0','N','${provinceCode}');"
					onkeyup= "dbclickComCodeByProvinceCode(this,'keyup','-1,0','N','${provinceCode}');"
					onchange= "dbclickComCodeByProvinceCode(this,'change','-1,0','N','${provinceCode}');"  
				   </c:if>>
			<img src="/claim/images/bgMarkMustInput.jpg">
		</td>
	</tr>
	<tr>
		<td class="title" style="width: 11%">
			<%-- 查勘地址 --%>
			<s:text name="certainLoss.prpLscheduleMainWF.prpLscheduleMainWFScheduleAddress" />
		</td>
		<td class="input" colspan=3>
			<input class="input" name="prpLscheduleMainWFCheckSite" style="width: 72%" value="${prpLscheduleMainWF.checkSite}" <c:out value="${ifreadonly1}"/>>
			<img src="/claim/images/bgMarkMustInput.jpg">
		</td>
	</tr>
	<tr>
		<td class="title" style="width: 11%">
			<%-- 查勘要点提示 --%>
			<s:text name="certainLoss.prpLscheduleMainWF.prpLscheduleMainWFCheckInfo" />
		</td>
		<td class="input" colspan=3>
			<input class="input" name="prpLscheduleMainWFCheckInfo" style="width: 72%" maxlength="255" value="${prpLscheduleMainWF.checkInfo}" <c:out value="${ifreadonly1}"/>>
			<input type="hidden" name="prpLscheduleMainWFFlag" value="${prpLscheduleMainWF.flag}">
			<input type="hidden" name="prpLscheduleMainWFScheduleFlag" value="${prpLscheduleMainWF.scheduleFlag}">
			<input type="hidden" name="checkCommiItemFlag" value="${prpLscheduleMainWF.commiItemFlag}">
			<input type="hidden" name="prpLscheduleMainWFCheckClaimComCode" value="${prpLscheduleMainWF.claimComCode}">
			<input type="hidden" name="prpLdrivername" value="${param.prpLdriverName}">
			<input type="hidden" name="prpLdriverLicenseNo" value="${prpLregist.licenseNo}">
			<input type="button" name="btnCheckText" class=bigbutton value="<s:text name='button.generatSurvey.value' />" onclick="return generateCheckText();">
		</td>
		<%--生成查勘要点提示 --%>
	</tr>
	<%--选择下一个节点用的--%>
	<tr>
		<td class="title" style="width: 11%">
			<%-- 查勘人员 --%>
			<s:text name="certainLoss.prpLscheduleMainWF.Handler" />
		</td>
		<td class="input" colspan=3>
			<input type=text name="nextHandlerCode1" class="codecode" style="width: 20%" maxlength="10" title="操作員" value="${prpLscheduleMainWF.nextHandlerCode}" <c:out value="${ifreadonly1}"/>
				<c:if test="${ifreadonly1 != 'readonly' }">
						  onblur="queryByUserCode(this,'${saveType1 }');" 
						  ondblclick="dbclickCheckPerson(this,'dbclick','0,1','Y');" 
						  onkeyup= "dbclickCheckPerson(this,'keyup','0,1','Y');"  
						  onchange= "dbclickCheckPerson(this,'change','0,1','Y');" 
						 </c:if>>
			<input type=text name="nextHandlerName1" class="codecode" style="width: 50%" title="操作員" value="${prpLscheduleMainWF.nextHandlerName}" <c:out value="${ifreadonly1}"/>
				<c:if test="${ifreadonly1 != 'readonly' }">
						  ondblclick="dbclickCheckPerson(this,'dbclick','-1,0','N');" 
				 		  onblur="queryByUserCode(this,'${saveType1 }');" 
						  onkeyup= "dbclickCheckPerson(this,'keyup','-1,0','N');" 
						  onchange= "dbclickCheckPerson(this,'change','-1,0','N');"  
						 </c:if>>
		</td>
	</tr>
</table>
<c:if test="${saveType1 eq 'GETBACKEDIT'}">
	<input type="hidden" name=maxrow value="0">
</c:if>