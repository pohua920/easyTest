<%--
****************************************************************************
* DESC       ：添加人伤跟踪信息页面
* AUTHOR     ：中科软
* CREATEDATE ： 2005-02-20
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%> 
<%-- 多行输入展现域的模板 --%>
<%--多行输入自定义JavaScript方法域--%>
<%@ include file="/common/taglibs.jsp"%>
    <script language='javascript'>
  //在下面加入本页自定义的JavaScript方法                        
    /*
            插入一条新的personTrace之後的处理（可选方法）
          */

    function afterInsertPersonTrace() {
    	setPrpLpersonTracePersonNo();
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
    	for (var i = 0; i < count; i++) {
    		//alert("看看什么时候运行?count="+count+"  i="+i); 
    		if (count != 1) {
    			fm.prpLpersonTracePersonNo[i].value = i;
    			fm.prpLpersonTraceNewAddFlag[i].value = "new"; //add by liyanjie 2005-12-18
    			//是否新增标志=new,因为已经控制了不能删除原来的.
    		}
    	}
    }	    
    </script> 
      <!--建立显示的录入条，可以收缩显示的-->
<table class="common" cellpadding="5" cellspacing="1">
	<!--表示显示多行的-->
	<tr>
		<td class="common" colspan="4" style="text-align: left;">
			<s:text name="info.personTrack" />
			<%--人伤跟踪信息 --%>
			<br>
			<table cellpadding="5" cellspacing="1" class=common id="PersonTrace_Data" style="display: none">
				<tbody>
					<tr>
						<td class="input" style="width: 3%">
							<div align="center">
								<input class="readonlyNo" readonly name="prpLpersonTracePersonNo" description="序号">
								<input type="hidden" class="readonlyno" name="prpLpersonTraceNewAddFlag">
								<input type="hidden" name="selectSend" value="0">
								<input type="hidden" name="prpLpersonTraceSelectSend" value="0">
							</div>
						</td>
						<td class="subformtitle">
							<table cellpadding="5" cellspacing="1" class="common">
								<tr>
									<td class="title" style="width: 15%">
										<s:text name="claim.name" />
										<%--姓　　名 --%>
									</td>
									<td class="input" style="width: 20%">
										<input name="prpLpersonTracePersonName" class="input" style="width: 70%" maxlength=20 description="人伤跟踪姓名">
										<img src="${ctx }/images/bgMarkMustInput.jpg">
									</td>
									<td class="title" style="width: 15%" align=center>
										<s:text name="db.prpCinsurednature.sex" />
										<%--性　　别 --%>
									</td>
									<td class="input" style="width: 15%">
										<select name="personSex" id="personSex" Style="width: 70%">
											<option value="9" <c:if test="${prpLpersonTrace.personSex=='9' }">selected="selected"</c:if>>
												未说明
											</option>
											<option value="1" <c:if test="${prpLpersonTrace.personSex=='1' }">selected="selected"</c:if>>
												男
											</option>
											<option value="2" <c:if test="${prpLpersonTrace.personSex=='2' }">selected="selected"</c:if>>
												女
											</option>
										</select> <img src="${ctx }/images/bgMarkMustInput.jpg">
									</td>
									<td class="title" style='width: 15%' align=center>
										<s:text name="db.prpCname.age" />
										<%--年　　龄 --%>
									</td>
									<td class="input" style="width: 20%">
										<input name="prpLpersonTracePersonAge" class="input" maxlength=5 description="年龄">
									</td>
								</tr>
								<tr>
									<td class="title" style="width: 15%">
										<s:text name="claim.engagedIndustry" />
										<%--从事行业 --%>
									</td>
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
								<!-- modify by liuwei at 2011-02-15 行业分三级选择 end -->
								<tr>
									<td class="title" style="width: 15%">
										<s:text name="claim.injurArea" />
										<%--受伤部位 --%>
									</td>
									<td class="input" colspan="6">
										<input name="prpLpersonTracePartDesc" class="input" description="受伤部位">
									</td>
								</tr>
								<tr>
									<td class="title" style="width: 15%">
										<s:text name="claim.injuryDescribe" />
										<%--伤情描述 --%>
									</td>
									<td class="input" colspan="6">
										<input name="prpLpersonTraceWoundRemark" class="input" description="伤情描述">
									</td>
								</tr>
								<tr>
									<td class="title" style="width: 15%">
										<s:text name="claim.whetherDoctor" />
										<%--是否自行就医 --%>
									</td>
									<td class="input" colspan="2">
										<select name="motionFlag" Style="width: 45%">
											<option value="0" <c:if test="${prpLpersonTrace.motionFlag=='0' }">selected="selected"</c:if>>
												<s:text name="regist.prpLregist.no" />
												<%--否 --%>
											</option>
											<option value="1" <c:if test="${prpLpersonTrace.motionFlag=='1' }">selected="selected"</c:if>>
												<s:text name="regist.prpLregist.yes" />
												<%--是 --%>
											</option>
										</select>
									</td>
                            		<td class="title" style="width:15%" align=center>就診醫師</td><!-- 就診醫師 -->
	                                <td class="title" style="width:20%" colspan="3">
	                            	    <input name="prpLpersonTraceDoctor" style="width:30%" value="${personTrace.doctor}" class="input" description="就診醫師"/>
									</td>
	                              </tr>
	                              <tr>
									<td class="title" style="width: 15%">
										<s:text name="certainLoss.hospitals" /><%--就诊医院 --%>
									</td>
									<td class="input" colspan="5">
                              			<input name="prpLpersonTraceHospitalCode" style="width:150 px" class="input" description="就診醫院代碼" onkeyup="getHospital(this,'codeCode','0,1');" onblur="isHospital(this,'codeCode');">
                              			<input name="prpLpersonTraceHospital" style="width:300 px" class="input" description="就診醫院" onkeyup="getHospital(this,'codeName','-1,0');">
										<input name="prpLpersonTraceIdentifyNumber" type="hidden">
										<input name="prpLpersonTraceRelatePersonNo" type="hidden">
										<input name="prpLpersonTraceRemark" type="hidden">
										<input name="prpLpersonTraceFlag" type="hidden">
									</td>
								</tr>
							</table>
						</td>
						<td class="input" style='width: 4%'>
							<div align="center">
								<input type=button name="buttonPersonTraceDelete" class="smallbutton" onclick="deleteRow(this,'PersonTrace')" value="-" style="cursor: hand">
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
							<s:text name="db.utiPsele.orderNo" /><%--序号 --%>
						</td>
						<td class="centertitle" style="width: 92%">
							<s:text name="db.utiFile.text" /><%--内容  --%>
						</td>
						<td class="centertitle" style="width: 4%">&nbsp;</td>
					</tr>
				</thead>
				<tfoot>
					<tr>
						<td class="title" colspan=2>
							<s:text name="certainLoss.thirdCarLoss.promptLoss" />
							<%--(按"+"号键增加损失部位信息，按"-"号键删除信息) --%>
							<input type="hidden" name="personCheck" value="1">
							<input type="hidden" name="selectSend" value="0" >           
                            <input type="hidden" name="prpLpersonTraceSelectSend" value="${prpLpersonTrace.selectSend}" >     
						</td>
						<td class="title" colspan=align= "right" style="width: 4%">
							<div align="center">
								<input type="button" value="+" class=smallbutton onclick="insertRow('PersonTrace')" name="buttonPersonTraceInsert" style="cursor: hand">
							</div>
						</td>
					</tr>
				</tfoot>
				<tbody>
					<c:if test="${prpLpersonTrace.personTraceList!=null}">
						<c:forEach items="${prpLpersonTrace.personTraceList}" var="personTrace">
							<tr>
								<td class="input" style="width: 4%">
									<div align="center">
										<input name="prpLpersonTracePersonNo" class="readonlyno" readonly="true" value="${personTrace.id.personNo}">
										<input type="hidden" class="readonlyno" name="prpLpersonTraceNewAddFlag" value="old">
									</div>
								</td>
								<td class="subformtitle">
									<table class="common" cellpadding="5" cellspacing="1">
										<tr>
											<td class="title" style="width: 15%">
												<s:text name="claim.name" /><%--姓　名 --%>
											</td>
											<td class="input" style="width: 20%">
												<input name="prpLpersonTracePersonName" class="input" style="width: 70%" maxlength=20 description="人伤跟踪姓名" value="${personTrace.personName}">
												<img src="${ctx }/images/bgMarkMustInput.jpg">
											</td>
											<td class="title" style="width: 15%" align=center>
												<s:text name="db.prpDcustomer_Idv.sex" /><%--性　　别 --%>
											</td>
											<td class="input" style="width: 15%">
												<select name="personSex" Style="width: 70%">
													<option value="9">
														<s:text name="claim.notSay" /><%--未说明 --%>
													</option>
													<option value="1" <c:if test="${personTrace.personSex=='1' }">selected="selected"</c:if>>
														<s:text name="certainLoss.male" /><%--男 --%>
													</option>
													<option value="2" <c:if test="${personTrace.personSex=='2' }">selected="selected"</c:if>>
														<s:text name="certainLoss.female" /><%--女 --%>
													</option>
												</select> <img src="${ctx }/images/bgMarkMustInput.jpg">
											</td>
											<td class="title" style='width: 15%' align=center>
												<s:text name="db.prpCname.age" /><%--年　　龄 --%>
											</td>
											<td class="input" style="width: 20%">
												<input name="prpLpersonTracePersonAge" class="input" maxlength=5 description="年龄" value="${personTrace.personAge}">
											</td>
										</tr>
										<tr>
											<td class="title" style="width: 15%">
												<s:text name="claim.engagedIndustry" />
												<%--从事行业 --%>
											</td>
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
										<tr>
											<td class="title" style="width: 15%">
												<s:text name="claim.injurArea" />
												<%--受伤部位 --%>
											</td>
											<td class="input" colspan="6">
												<input name="prpLpersonTracePartDesc" class="input" description="受伤部位" value="${personTrace.partDesc}">
											</td>
										</tr>
										<tr>
											<td class="title" style="width: 15%">
												<s:text name="claim.injuryDescribe" /><%--伤情描述 --%>
											</td>
											<td class="input" colspan="6">
												<input name="prpLpersonTraceWoundRemark" class="input" description="伤情描述" value="${personTrace.woundRemark}">
											</td>
										</tr>
										<tr>
											<td class="title" style="width: 15%">
												<s:text name="claim.whetherDoctor" /><%--是否自行就医 --%>
											</td>
											<td class="input" style="width:20%">
												<select name="motionFlag" Style="width: 50%">
													<option value="0" <c:if test="${personTrace.motionFlag=='0' }">selected="selected"</c:if>>
														<s:text name="regist.prpLregist.no" /><%--否 --%>
													</option>
													<option value="1" <c:if test="${personTrace.motionFlag=='1' }">selected="selected"</c:if>>
														<s:text name="regist.prpLregist.yes" /><%--是 --%>
													</option>
												</select>
											</td>
			                                <td class="title" style="width:15%" align=center >就診醫師</td><!-- 就診醫師 -->
			                                <td class="title" style="width:20%" colspan="3">
			                            	    <input name="prpLpersonTraceDoctor" style="width:30%" value="${personTrace.doctor}" class="input" description="就診醫師"/>
			                                </td>
			                              </tr>
			                              <tr>
											<td class="title" style="width: 15%">
												<s:text name="certainLoss.hospitals" /><%--就诊医院 --%>
											</td>
											<td class="input" style="width:15%" colspan="5">
                             	   				<input name="prpLpersonTraceHospitalCode" style="width:150 px" class="input" description="就診醫院代碼" onkeyup="getHospital(this,'codeCode','0,1');" onblur="isHospital(this,'codeCode');" value="${personTrace.hospitalCode }">
	                               				<input name="prpLpersonTraceHospital" style="width:300 px" class="input" description="就診醫院" onkeyup="getHospital(this,'codeName','-1,0');"  value="${personTrace.hospital }">
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
										<input type=button name="buttonPersonTraceDelete" class=smallbutton onclick="deleteRow(this,'PersonTrace')" value="-" style="cursor: hand">
									</div>
								</td>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>
		</td>
	</tr>
</table>
<%--** 醫院名稱下拉显示的隐藏域 *--%>
<div  id="hospitalList" style="background-color: FFFFFF;display: none;cursor:hand;position: absolute;width: 400px;" align="left"></div>