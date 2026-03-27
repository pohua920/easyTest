<%--
****************************************************************************
* DESC       ：添加人伤跟踪信息页面
* AUTHOR     ：中科软
* CREATEDATE ： 2013-01-17
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%--多行输入自定义JavaScript方法域--%>
<script language='javascript'>
	function afterInsertPersonTrace(PersonTraceObject){
		$(PersonTraceObject).find(":input[name='prpLpersonTraceNewAddFlag']").val("new");
	}
</script>
<!--建立显示的录入条，可以收缩显示的-->
<table class="common" cellpadding="5" cellspacing="1">
	<!--表示显示多行的-->
	<tr>
		<td class="common" colspan="4" style="text-align: left;">
			<s:text name="info.personTrack" />
			<br>
			<%-- 人伤跟踪信息 --%>
			<table cellpadding="5" cellspacing="1" class=common id="PersonTrace_Data" style="display: none">
				<tbody>
					<tr>
						<td class="input" style="width: 3%">
							<div align="center">
								<input class="readonly" readonly="readonly" style="width: 30%" name="prpLpersonTracePersonNo" description="序号">
								<input type="hidden" class="readonlyno" name="prpLpersonTraceNewAddFlag">
								<input type="hidden" name="selectSend" value="0">
								<input type="hidden" name="prpLpersonTraceSelectSend" value="0">
								<!--是否车辆标志 -->
							</div>
						</td>
						<td class="subformtitle">
							<table cellpadding="5" cellspacing="1" class="common">
								<tr>
									<td class="title" style="width: 15%">
										<s:text name="claim.name" />：
									</td>
									<%-- 姓　　名 --%>
									<td class="input" style="width: 20%">
										<!-- mantis： CLM0017，處理人員：Sam，需求單編號：CLM0017，原住名姓名調整作業_車 -->
										<input name="prpLpersonTracePersonName" class="input" style="width: 70%" maxlength="100" description="人伤跟踪姓名">
										<img src="/claim/images/bgMarkMustInput.jpg">
									</td>
									<td class="title" style="width: 15%" align=center>
										<s:text name="db.prpLpersonloss.sex" />：
									</td>
									<%-- 性　　别 --%>
									<td class="input" style="width: 15%">
										<s:select list="#{'1':'男','2':'女','9':'未說明'}" name="personSex" Style="width:40%" />
										<img src="/claim/images/bgMarkMustInput.jpg">
									</td>
									<td class="title" style='width: 15%' align=center>
										<s:text name="db.prpLpersonloss.age" />：
									</td>
									<%-- 年　　龄 --%>
									<td class="input" style="width: 20%">
										<input name="prpLpersonTracePersonAge" class="input" maxlength=5 description="年龄">
									</td>
								</tr>
								<tr>
									<td class="title" style="width: 15%">
										<s:text name="claim.engagedIndustry" />：
									</td>
									<%-- 从事行业 --%>
									<td class="input" colspan="6" align="left">
										<input type="hidden" name="prpLpersonTraceJobCode1">
										<input type="text" name="prpLpersonTraceJobName1" class="codename" style="width: 100px" ondblclick="code_CodeSelect(this,'BusinessSource1','-1,0','Y','N',fm.riskcode.value);"
											onkeyup="code_CodeSelect(this,'BusinessSource1','-1,0','Y','N',fm.riskcode.value);" onchange="code_CodeChange(this,'BusinessSource1','-1,0','Y','N',fm.riskcode.value);">
										<input type="hidden" name="prpLpersonTraceJobCode2">
										<input type="text" name="prpLpersonTraceJobName2" class="codename" style="width: 100px" ondblclick="code_CodeSelect(this,'BusinessSource2','-1,0','Y','N',fm.riskcode.value);"
											onkeyup="code_CodeSelect(this,'BusinessSource2','-1,0','Y','N',fm.riskcode.value);" onchange="code_CodeChange(this,'BusinessSource2','-1,0','Y','N',fm.riskcode.value);">
										<input type="hidden" name="prpLpersonTraceJobCode">
										<input type="text" name="prpLpersonTraceJobName" class="codename" style="width: 100px" ondblclick="code_CodeSelect(this,'BusinessSource','-1,0','Y','N',fm.riskcode.value);"
											onchange="code_CodeChange(this,'BusinessSource','-1,0','Y','N',fm.riskcode.value);" onkeyup="code_CodeSelect(this,'BusinessSource','-1,0','Y','N',fm.riskcode.value);">
									</td>
								</tr>
								<tr style="display: none">
									<td class="title" style="width: 15%">
										<s:text name="claim.involRisk" />：
									</td>
									<td class="input" colspan="2" align=left>
										<%--headerKey不要去掉那个空格，去掉空格后，会后台报空指针异常 --%>
										<s:select list="#request.referKindList" name="prpLpersonTraceReferKind" listKey="kindCode" listValue="kindName" headerKey=" " headerValue="" />
									</td>
									<td class="title" style="width: 10%" align=center>
										<s:text name="regist.prpLregist.casualtiesType" />：
									</td>
									<%-- 伤亡类型 --%>
									<td class="input" colspan="2" align=left>
										<s:select list="#{'1':'1.醫療','2':'2.失能','3':'3.死亡'}" name="prpLpersonTraceFlag" listKey="key" listValue="value"></s:select>
									</td>
								</tr>
								<tr>
									<td class="title" style="width: 15%">
										<s:text name="claim.injurArea" />：
									</td>
									<%-- 受伤部位 --%>
									<td class="input" colspan="6">
										<input name="prpLpersonTracePartDesc" class="input" description="受伤部位">
									</td>
								</tr>
								<tr>
									<td class="title" style="width: 15%">
										<s:text name="claim.injuryDescribe" />：
									</td>
									<%-- 伤情描述 --%>
									<td class="input" colspan="6">
										<input name="prpLpersonTraceWoundRemark" class="input" description="伤情描述">
									</td>
								</tr>
								<tr>
									<td class="title" style="width: 15%">
										<s:text name="claim.whetherDoctor" />：
									</td>
									<%-- 是否自行就医 --%>
									<td class="input" colspan="2">
										<s:select list="#{'0':'否','1':'是'}" name="motionFlag"/>
									</td>
									<td class="title" style="width: 15%">
										<s:text name="certainLoss.hospitals" /><%--就诊医院 --%>
									</td>
									<td class="input" colspan="5">
										<input type="text" name="prpLpersonTraceHospitalCode" style="display: none;" class="input" description="就診醫院代碼">
										<input type="text" name="prpLpersonTraceHospital" style="width:300 px" class="input" description="就診醫院" >
										<input name="prpLpersonTraceIdentifyNumber" type="hidden">
										<input name="prpLpersonTraceRelatePersonNo" type="hidden">
										<input name="prpLpersonTraceRemark" type="hidden">
										<input name="prpLpersonTraceFlag" type="hidden">
									</td>
									<%-- 就诊医院 --%>
								</tr>
							</table>
						</td>
						<td class="input" style='width: 4%'>
							<div align="center">
								<input type=button name="buttonPersonTraceDelete" class="smallbutton" onclick="deleteRow(this,'PersonTrace','prpLpersonTracePersonNo')" value="-" style="cursor: hand">
							</div>
						</td>
					</tr>
				</tbody>
			</table>
			<%-- 多行输入展现域 --%>
			<table class="sub" id="PersonTrace" align="center" cellspacing="1" cellpadding="0">
				<thead>
					<tr>
						<td class="centertitle" style="width: 4%">
							<s:text name="regist.prpLregist.serialNo" />
						</td>
						<%-- 序号 --%>
						<td class="centertitle" style="width: 92%">
							<s:text name="db.prpLregistText.context" />
							<%-- 内容 --%>
						</td>
						<td class="centertitle" style="width: 4%">操作</td>
					</tr>
				</thead>
				<tfoot>
					<tr>
						<td class="title" colspan=2>
							<s:text name="prompt.schedule.addRename5" />
							<%-- (按"+"号键增加损失部位信息，按"-"号键删除信息) --%>
							<input type="hidden" name="personCheck" value="1">
						</td>
						<td class="title" colspan=align= "right" style="width: 4%">
							<div align="center">
								<input type="button" value="+" class=smallbutton onclick="insertRow('PersonTrace',this,'prpLpersonTracePersonNo')" name="buttonPersonTraceInsert" style="cursor: hand">
							</div>
						</td>
					</tr>
				</tfoot>
				<tbody>
					<c:forEach var="personTrace" items="${prpLpersonTrace.personTraceList}" varStatus="status">
						<tr class=oddrow>
							<td class="input" style="width: 4%">
								<div align="center">
									<input name="prpLpersonTracePersonNo" class="readonlyno" readonly="true" value="${personTrace.id.personNo}">
									<input type="hidden" class="readonlyno" name="prpLpersonTraceNewAddFlag" value="old">
									<input type="hidden" name="selectSend" value="0">
									<input type="hidden" name="prpLpersonTraceSelectSend" value="${personTrace.selectSend}">
								</div>
							</td>
							<td class="subformtitle">
								<table class="common" cellpadding="5" cellspacing="1">
									<tr>
										<td class="title" style="width: 15%">
											<s:text name="claim.name" />：
										</td>
										<%-- 姓　　名 --%>
										<td class="input" style='width: 20%'>
											<!-- mantis： CLM0017，處理人員：Sam，需求單編號：CLM0017，原住名姓名調整作業_車 -->
											<input name="prpLpersonTracePersonName" class="input" style="width: 70%" maxlength="100" description="人伤跟踪姓名" value="${personTrace.personName}">
											<img src="/claim/images/bgMarkMustInput.jpg">
										</td>
										<td class="title" style="width: 15%" align=center>
											<s:text name="db.prpLpersonloss.sex" />：
										</td>
										<%-- 性　　别 --%>
										<td class="input" style="width: 15%">
											<select name="personSex" Style="width: 40%">
												<option value="1" <c:if test="${personTrace.personSex=='1'}"> selected="selected"</c:if>>男</option>
												<option value="2" <c:if test="${personTrace.personSex=='2'}"> selected="selected"</c:if>>女</option>
												<option value="9" <c:if test="${personTrace.personSex=='9'}"> selected="selected"</c:if>>未說明</option>
											</select> <img src="/claim/images/bgMarkMustInput.jpg">
										</td>
										<td class="title" style='width: 15%'>
											<s:text name="db.prpLpersonloss.age" />：
										</td>
										<%-- 年　　龄 --%>
										<td class="input" style='width: 20%'>
											<input name="prpLpersonTracePersonAge" class="input" maxlength=5 description="年龄" value="${personTrace.personAge}">
										</td>
									</tr>
									<tr>
										<td class="title" style="width: 15%">
											<s:text name="claim.engagedIndustry" />：
										</td>
										<%-- 从事行业 --%>
										<td class="input" colspan="6" align="left">
											<input type="hidden" name="prpLpersonTraceJobCode1" value="${personTrace.jobCode1}">
											<input type="text" name="prpLpersonTraceJobName1" class="codename" style="width: 100px" value="${personTrace.jobName1}" ondblclick="code_CodeSelect(this,'BusinessSource1','-1,0','Y','N',fm.riskcode.value);"
												onkeyup="code_CodeSelect(this,'BusinessSource1','-1,0','Y','N',fm.riskcode.value);" onchange="code_CodeChange(this,'BusinessSource1','-1,0','Y','N',fm.riskcode.value);">
											<input type="hidden" name="prpLpersonTraceJobCode2" value="${personTrace.jobCode2}">
											<input type="text" name="prpLpersonTraceJobName2" class="codename" style="width: 100px" value="${personTrace.jobName2}" ondblclick="code_CodeSelect(this,'BusinessSource2','-1,0','Y','N',fm.riskcode.value);"
												onkeyup="code_CodeSelect(this,'BusinessSource2','-1,0','Y','N',fm.riskcode.value);" onchange="code_CodeChange(this,'BusinessSource2','-1,0','Y','N',fm.riskcode.value);">
											<input type="hidden" name="prpLpersonTraceJobCode" value="${personTrace.jobCode}">
											<input type="text" name="prpLpersonTraceJobName" class="codename" style="width: 100px" value="${personTrace.jobName}" ondblclick="code_CodeSelect(this,'BusinessSource','-1,0','Y','N',fm.riskcode.value);"
												onchange="code_CodeChange(this,'BusinessSource','-1,0','Y','N',fm.riskcode.value);" onkeyup="code_CodeSelect(this,'BusinessSource','-1,0','Y','N',fm.riskcode.value);">
										</td>
									</tr>
									<tr style="display: none">
										<td class="title" style="width: 15%">
											<s:text name="claim.involRisk" />：
										</td>
										<%-- 涉及险种 --%>
										<td class="input" colspan="2" align=left>
											<select name="prpLpersonTraceReferKind">
												<option value=" "></option>
												<c:forEach items="${requestScope.referKindList}" var="referKind">
													<option value="${referKind.kindCode}" <c:if test="${personTrace.prpLpersonTraceReferKind==referKind.kindCode}"> selected="selected" </c:if>>
														<c:out value="${referKind.kindName}" />
													</option>
												</c:forEach>
											</select>
										</td>
										<td class="title" style="width: 10%" align=center>
											<s:text name="regist.prpLregist.casualtiesType" />：
										</td>
										<td class="input" colspan="2" align=left>
											<c:set var="tempSelectedValue" value="${personTrace.flag}" />
											<s:select name="prpLpersonTraceFlag" value="#attr.tempSelectedValue" listKey="key" listValue="value" list="#request.casualtiesList" />
										</td>
									</tr>
									<tr>
										<td class="title" style="width: 15%">
											<s:text name="claim.injurArea" />：
										</td>
										<%-- 受伤部位 --%>
										<td class="input" colspan="6">
											<input name="prpLpersonTracePartDesc" class="input" description="受伤部位" value="${personTrace.partDesc}">
										</td>
									</tr>
									<tr>
										<td class="title" style="width: 15%">
											<s:text name="claim.injuryDescribe" />：
										</td>
										<%-- 伤情描述 --%>
										<td class="input" colspan="6">
											<input name="prpLpersonTraceWoundRemark" class="input" description="伤情描述" value="${personTrace.woundRemark}">
										</td>
									</tr>
									<tr>
										<td class="title" style="width: 15%">
											<s:text name="claim.whetherDoctor" />：
										</td>
										<%-- 是否自行就医 --%>
										<td class="input" colspan="2">
											<c:set var="tempSelectedValue" value="${personTrace.motionFlag}" />
											<s:select name="motionFlag" value="#attr.tempSelectedValue" listKey="key" listValue="value" list="#request.motionFlagList"/>
										</td>
										<td class="title" align=center>
											
										</td>
										<%-- 就诊医院 --%>
										<td class="input" colspan="2">
											
										</td>
									</tr>
									<tr>
										<td class="title" style="width: 15%">
											<s:text name="certainLoss.hospitals" /><%--就诊医院 --%>
										</td>
										<td class="input" style="width:15%" colspan="5">
											<input type="text" name="prpLpersonTraceHospitalCode" style="display: none;" class="input" description="就診醫院代碼" value="${personTrace.hospitalCode }">
											<input type="text" name="prpLpersonTraceHospital" style="width:300 px" class="input" description="就診醫院" value="${personTrace.hospital }">
											<input name="prpLpersonTraceIdentifyNumber" type="hidden" value="${personTrace.identifyNumber}">
											<input name="prpLpersonTraceRelatePersonNo" type="hidden" value="${personTrace.relatePersonNo}">
											<input name="prpLpersonTraceRemark" type="hidden" value="${personTrace.remark}">
											<input name="prpLpersonTraceFlag" type="hidden" value="${personTrace.flag}">
										</td>
									</tr>
								</table>
							</td>
							<td class="title" style="width: 4%">
								<div align="center">
									<input type=button name="buttonPersonTraceDelete" class=smallbutton onclick="deleteRow(this,'PersonTrace','prpLpersonTracePersonNo')" value="-" style="cursor: hand">
								</div>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</td>
	</tr>
</table>
<%--** 醫院名稱下拉显示的隐藏域 *--%>
<div  id="hospitalList" style="background-color: FFFFFF;display: none;cursor:hand;position: absolute;width: 400px;" align="left"></div>