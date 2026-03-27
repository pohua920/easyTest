<%--
****************************************************************************
* DESC       ：添加人伤跟踪信息页面
* AUTHOR     ：理赔组
* CREATEDATE ： 2013-03-20
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%-- 多行输入展现域的模板 --%>
<%--多行输入自定义JavaScript方法域--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<script language='javascript'>
	//在下面加入本页自定义的JavaScript方法                        
	/*
	插入一条新的personTrace之後的处理（可选方法）
	 */
	function afterInsertPersonTrace() {
		setPrpLpersonTracePersonNo();
	}
	function check_person() {

		if (fm.personcount.value == "0"
				&& fm.prpLpersonTraceReferKind.value == "") {
			fm.buttonPersonTraceInsert.disabled = true;
		}

	}
	/*
	  删除本条PersonTrace之後的处理（可选方法）
	 */
	function afterDeletePersonTrace(field) {
		setPrpLpersonTracePersonNo();
	}

	/**
	 * 设置setPrpLpersonTracePersonNo
	 */
	function setPrpLpersonTracePersonNo() {
		var count = getElementCount("prpLpersonTracePersonNo");
		for ( var i = 0; i < count; i++) {
			//alert("看看什么时候运行?count="+count+"  i="+i); 
			if (count != 1) {
				fm.prpLpersonTracePersonNo[i].value = i;
				fm.prpLpersonTraceNewAddFlag[i].value = "new";
				//是否是新增的人损标志=new,因为已经控制了不能删除原来的.
			}
		}
	}
</script>
<!--建立显示的录入条，可以收缩显示的-->
<table class="common" align="center" style="width: 100%">
	<!--表示显示多行的-->
	<tr>
		<td class="subformtitle" style="text-align: left" colspan="4">
			<img style="cursor: hand;" src="/claim/images/butCollapseBlue.gif" name="PersonTraceImg" onclick="showPage(this,spanClaimpersonTrace)">
			<s:text name="info.personTrack" />
			<br>
			<%--人伤跟踪信息 --%>
			<table width="100%" cellpadding="0" cellspacing="1" class="common" id="PersonTrace_Data" style="display: none">
				<tbody>
					<tr>
						<td class="input" style="width: 3%">
							<div align="center">
								<input class="readonlyNo" readonly name="prpLpersonTracePersonNo" description="序号">
								<input type="hidden" class="readonlyno" name="prpLpersonTraceNewAddFlag">
								<input type="hidden" name="prpLpersonTraceSelectSend" value="0">
								<!--是否是新增的人伤损失 -->
							</div>
						</td>
						<td class="subformtitle">
							<table cellpadding="0" cellspacing="1" class="common">
								<tr>
									<td class="title" style="width: 10%">
										<s:text name="claim.name" />：
									</td>
									<%--姓名 --%>
									<td class="input" style='width: 18%'>
										<!-- mantis： CLM0017，處理人員：Sam，需求單編號：CLM0017，原住名姓名調整作業_車 -->
										<input name="prpLpersonTracePersonName" class="input" style="width: 70%" maxlength="100" description="人伤跟踪姓名">
										<img src="/claim/images/bgMarkMustInput.jpg">
									</td>
									<td class="title" style="width: 6%">
										<s:text name="db.prpLpersonloss.sex" />：
									</td>
									<%--性别 --%>
									<td class="input" style="width: 12%">
										<%--
											//reason:人伤性别默认为未说明
										--%>
										<s:select name="personSex" list="#{'1':'男','2':'女','9':'未说明'}" listKey="key" listValue="value" value="prpLpersonTrace.personSex">
										</s:select>
										<img src="/claim/images/bgMarkMustInput.jpg">
									</td>
									<td class="title" style='width: 5%'>
										<s:text name="db.prpLpersonloss.age" />：
									</td>
									<%--年龄 --%>
									<td class="input" style='width: 5%'>
										<input name="prpLpersonTracePersonAge" class="input" maxlength=5 description="年龄">
									</td>
								</tr>
								<tr>
									<td class="title" style="width: 15%">
										<s:text name="claim.engagedIndustry" />：
									</td>
									<%-- 从事行业 --%>
									<td class="input" colspan="5" align="left">
										<input type="hidden" name="prpLpersonTraceJobCode1">
										<input type="text" name="prpLpersonTraceJobName1" class="codename" style="width: 100px" ondblclick="code_CodeSelect(this,'BusinessSource1','-1,0','Y');"
											onkeyup="code_CodeSelect(this,'BusinessSource1','-1,0','Y');" onchange="code_CodeChange(this,'BusinessSource1','-1,0','Y');">
										<input type="hidden" name="prpLpersonTraceJobCode2">
										<input type="text" name="prpLpersonTraceJobName2" class="codename" style="width: 100px" ondblclick="code_CodeSelect(this,'BusinessSource2','-1,0','Y');"
											onkeyup="code_CodeSelect(this,'BusinessSource2','-1,0','Y');" onchange="code_CodeChange(this,'BusinessSource2','-1,0','Y');">
										<input type="hidden" name="prpLpersonTraceJobCode">
										<input type="text" name="prpLpersonTraceJobName" class="codename" style="width: 100px" ondblclick="code_CodeSelect(this,'BusinessSource','-1,0','Y','N');"
											onchange="code_CodeChange(this,'BusinessSource','-1,0','Y','N');" onkeyup="code_CodeSelect(this,'BusinessSource','-1,0','Y','N');">
									</td>
								</tr>
								<tr>
									<td class="title">
										<s:text name="claim.involRisk" />：
									</td>
									<%--涉及险种 --%>
									<td class="input" colspan="3">
										<s:select name="prpLpersonTraceReferKind" Style="width:50%" list="#request.referKindList" listKey="kindCode" listValue="kindName" value="prpLpersonTrace.prpLpersonTraceReferKind"
											headerKey=" " headerValue=" "></s:select>
									</td>
									<td class="title" style="width: 10%">
										<s:text name="regist.prpLregist.casualtiesType" />：
									</td><%--伤亡类型 --%>
									<td class="input" style='width: 32%' >
										<s:select name="prpLpersonTraceFlag" listKey="key" listValue="value" list="#request.casualtiesList" />
									</td>
								</tr>
								<tr>
									<td class="title">
										<s:text name="claim.injurArea" />：
									</td>
									<%--受伤部位 --%>
									<td class="input" colspan="5">
										<input name="prpLpersonTracePartDesc" class="input" description="受伤部位">
									</td>
								</tr>
								<tr>
									<td class="title">
										<s:text name="claim.injuryDescribe" />：
									</td>
									<%--伤情描述 --%>
									<td class="input" colspan="5">
										<input name="prpLpersonTraceWoundRemark" class="input" description="伤情描述">
									</td>
								</tr>
								<tr>
									<td class="title">
										<s:text name="claim.whetherDoctor" />：
									</td>
									<%--是否自行就医 --%>
									<td class="input" colspan="3">
										<%--<s:select name="motionFlag" list="#{'0':'否','1':'是'}" listKey="key" listValue="value" value="prpLpersonTrace.motionFlag">
 										</s:select> --%>
										<select name="motionFlag">
											<option value="0" <c:if test="${prpLpersonTrace.motionFlag=='0' }">selected="selected"</c:if>>否</option>
											<option value="1" <c:if test="${prpLpersonTrace.motionFlag=='1' }">selected="selected"</c:if>>是</option>
										</select>
									</td>
									<td class="title">
										<s:text name="certainLoss.hospitals" />：
									</td>
									<%--就诊医院 --%>
									<td class="input">
										<input name="prpLpersonTraceHospital" class="input" description="就诊医院">
										<input name="prpLpersonTraceIdentifyNumber" type="hidden">
										<input name="prpLpersonTraceRelatePersonNo" type="hidden">
										<input name="prpLpersonTraceJobCode" type="hidden">
										<input name="prpLpersonTraceRemark" type="hidden">
									</td>
								</tr>
							</table>
						</td>
						<td class="input" style='width: 4%'>
							<div align="center">
								<input type=button name="buttonPersonTraceDelete" onclick="deleteRow(this,'PersonTrace')" value="-" style="cursor: hand">
							</div>
						</td>
					</tr>
				</tbody>
			</table>
			<span id="spanClaimpersonTrace" style="display: none"> <%-- 多行输入展现域 --%>
				<table class="sub" id="PersonTrace" align="center" cellspacing="1" cellpadding="0">
					<thead>
						<tr>
							<td class="title" style="width: 4%">
								<s:text name="regist.prpLregist.serialNo" />
							</td>
							<%--序号 --%>
							<td class="title" style="width: 96%" colspan=2>
								<p align="right"></p>
							</td>
						</tr>
					</thead>
					<tfoot>
						<tr>
							<td class="title" colspan=2 style="width: 96%">
								<s:text name="prompt.schedule.addRename5" />
							</td>
							<%--(按"+"号键增加人伤跟踪信息，按"-"号键删除信息)--%>
							<td class="title" align="right" style="width: 4%">
								<div align="center">
									<input type="button" value="+" onclick="insertRow('PersonTrace')" name="buttonPersonTraceInsert" style="cursor: hand">
									<!--test-->
								</div>
							</td>
						</tr>
					</tfoot>
					<tbody id="readonlyPersonTraceTable">
						<c:set var="index" value="0"></c:set>
						<c:if test="${prpLpersonTrace.personTraceList!=null}">
							<c:forEach var="personTrace" items="${prpLpersonTrace.personTraceList}">
								<c:choose>
									<c:when test="${index %2== 0}">
										<tr class=oddrow>
									</c:when>
									<c:otherwise>
										<tr class=oddrow>
									</c:otherwise>
								</c:choose>
								<td class="input" style="width: 4%">
									<div align="center">
										<input name="prpLpersonTracePersonNo" class="readonlyno" readonly="true" value="${personTrace.id.personNo}">
										<input type="hidden" class="readonlyno" name="prpLpersonTraceNewAddFlag" value="old">
										<input type="hidden" name="prpLpersonTraceSelectSend" value="${prpLpersonTrace.selectSend}">
										<!--是否是新增的人伤标志 -->
									</div>
								</td>
								<td class="subformtitle">
									<table cellpadding="0" cellspacing="1" class="common">
										<tr>
											<td class="title" style="width: 10%">
												<s:text name="claim.name" />：
											</td>
											<%--姓名 --%>
											<td class="input" style='width: 18%'>
												<!-- mantis： CLM0017，處理人員：Sam，需求單編號：CLM0017，原住名姓名調整作業_車 -->
												<input name="prpLpersonTracePersonName" class="input" style="width: 70%" maxlength="100" description="人伤跟踪姓名" value="${personTrace.personName}">
												<img src="/claim/images/bgMarkMustInput.jpg">
											</td>
											<td class="title" style="width: 6%">性别：</td>
											<td class="input" style="width: 12%">
												<select name="personSex">
													<c:forEach var="personSex" items="${requestScope.driverSexs}">
														<option value="${personSex.id.codeCode}" <c:if test="${personTrace.personSex==personSex.id.codeCode}">selected="selected"</c:if>>
															<c:out value="${personSex.codeCName}" />
														</option>
													</c:forEach>
												</select> <img src="/claim/images/bgMarkMustInput.jpg">
											</td>
											<td class="title" style='width: 5%'>
												<s:text name="db.prpLpersonloss.age" />：
											</td>
											<%--年龄 --%>
											<td class="input" style='width: 5%'>
												<input name="prpLpersonTracePersonAge" class="input" maxlength=5 description="年龄" value="${personTrace.personAge}">
											</td>
										</tr>
										<tr>
											<td class="title" style="width: 15%">
												<s:text name="claim.engagedIndustry" />：
											</td>
											<%-- 从事行业 --%>
											<td class="input" colspan="5" align="left">
												<input type="hidden" name="prpLpersonTraceJobCode1" value="${personTrace.jobCode1}">
												<input type="text" name="prpLpersonTraceJobName1" value="${personTrace.jobName1}" class="codename" style="width: 100px" ondblclick="code_CodeSelect(this,'BusinessSource1','-1,0','Y');"
													onkeyup="code_CodeSelect(this,'BusinessSource1','-1,0','Y');" onchange="code_CodeChange(this,'BusinessSource1','-1,0','Y');">
												<input type="hidden" name="prpLpersonTraceJobCode2" value="${personTrace.jobCode2}">
												<input type="text" name="prpLpersonTraceJobName2" value="${personTrace.jobName2}" class="codename" style="width: 100px" ondblclick="code_CodeSelect(this,'BusinessSource2','-1,0','Y');"
													onkeyup="code_CodeSelect(this,'BusinessSource2','-1,0','Y');" onchange="code_CodeChange(this,'BusinessSource2','-1,0','Y');">
												<input type="hidden" name="prpLpersonTraceJobCode" value="${personTrace.jobCode}">
												<input type="text" name="prpLpersonTraceJobName" value="${personTrace.jobName}" class="codename" style="width: 100px" ondblclick="code_CodeSelect(this,'BusinessSource','-1,0','Y','N');"
													onchange="code_CodeChange(this,'BusinessSource','-1,0','Y','N');" onkeyup="code_CodeSelect(this,'BusinessSource','-1,0','Y','N');">
											</td>
										</tr>
										<tr>
											<td class="title">
												<s:text name="claim.involRisk" />：
											</td>
											<%--涉及险种 --%>
											<td class="input" colspan="3">
												<select name="prpLpersonTraceReferKind" Style="width: 50%">
													<c:if test="${fn:length(referKindList)<1}">
														<option value=" "></option>
													</c:if>
													<c:forEach var="referKind" items="${requestScope.referKindList}">
														<option value="${referKind.kindCode}" <c:if test="${personTrace.prpLpersonTraceReferKind==referKind.kindCode}">selected="selected"</c:if>>
															<c:out value="${referKind.kindName}" />
														</option>
													</c:forEach>
												</select>
											</td>
											<td class="title" style="width: 10%">
												<s:text name="regist.prpLregist.casualtiesType" />：
											</td><%-- 伤亡类型 --%>
											<td class="input" style='width: 32%'>
												<c:set var="flag" value="${personTrace.flag}" />
												<s:select name="prpLpersonTraceFlag" value="#attr.flag" listKey="key" listValue="value" list="#request.casualtiesList" />
											</td>
										</tr>
										<tr>
											<td class="title">
												<s:text name="claim.injurArea" />：
											</td>
											<%--受伤部位 --%>
											<td class="input" colspan="5">
												<input name="prpLpersonTracePartDesc" class="input" description="受伤部位" value="${personTrace.partDesc}">
											</td>
										</tr>
										<tr>
											<td class="title">
												<s:text name="claim.injuryDescribe" />：
											</td>
											<%--伤情描述 --%>
											<td class="input" colspan="5">
												<input name="prpLpersonTraceWoundRemark" class="input" description="伤情描述" value="${personTrace.woundRemark}">
											</td>
										</tr>
										<tr>
											<td class="title">
												<s:text name="claim.whetherDoctor" />：
											</td>
											<%--是否自行就医 --%>
											<td class="input" colspan="3">
												<select name="motionFlag" Style="width: 50%">
													<option value="0" <c:if test="${personTrace.motionFlag=='0'}">selected="selected"</c:if>>否</option>
													<option value="1" <c:if test="${personTrace.motionFlag=='1'}">selected="selected"</c:if>>是</option>
												</select>
											</td>
											<td class="title">
												<s:text name="certainLoss.hospitals" />：
											</td>
											<%--就诊医院 --%>
											<td class="input">
												<input name="prpLpersonTraceHospital" class="input" description="就诊医院" value="${personTrace.hospital}">
												<input name="prpLpersonTraceIdentifyNumber" type="hidden" value="${personTrace.identifyNumber}">
												<input name="prpLpersonTraceRelatePersonNo" type="hidden" value="${personTrace.relatePersonNo}">
												<input name="prpLpersonTraceJobCode" type="hidden" value="${personTrace.jobCode}">
												<input name="prpLpersonTraceRemark" type="hidden" value="${personTrace.remark}">
											</td>
										</tr>
									</table>
								</td>
								<td class="title" style="width: 4%">
									<div align="center">
										<input type=button name="buttonPersonTraceDelete" onclick="deleteRow(this,'PersonTrace')" value="-" disabled style="cursor: hand">
									</div>
								</td>
								</tr>
								<c:set var="index" value="${index+1}" scope="page"></c:set>
							</c:forEach>
						</c:if>
					</tbody>
				</table>
			</span>
		</td>
	</tr>
	<input type="hidden" name=personcount value="${index}">
	<input type="hidden" name=referKindListCount value="${referKindListCount }" />
</table>
