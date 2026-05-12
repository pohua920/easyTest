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
	//mantis：CLM0181，處理人員：DP0713，需求單編號：新核心-案件備案WS 3.10查詢及記錄留存作業 START
	$( window ).on( "load", function() {
        prpLthirdPartyLicenseNoRebulid();
        buildSelectLicenseno();
        $( 'input[name="prpLthirdPartyLicenseNo"]' ).on( "blur", function() {
            prpLthirdPartyLicenseNoRebulid();
            buildSelectLicenseno();
        });
    });
	
	var prpLthirdPartyLicenseNoArray = [];
	//輸入的車牌收集
	function prpLthirdPartyLicenseNoRebulid(){
		var tempStr = "";
        $('input[name^="prpLthirdPartyLicenseNo"]').each(function() {
        	tempStr+=$(this).val()+",";
        });
        prpLthirdPartyLicenseNoArray = tempStr.substring(0,tempStr.length-1).split(',');
        
	}
	//建置受害人車牌選單
	function buildSelectLicenseno(){
        $('div[name="selectLicenseno"]').each(function() {
        	var prpLpersonTraceLicenseno = $(this).find('input[name="prpLpersonTraceLicenseno"]').val();//hidden obj
        	//現有車牌組合至OPTION
            $.each($(this).find('select[name="optionLicenseno"]'), function(index, value) {
                $(this).empty();
                buildOptionLicenseno($(this),prpLpersonTraceLicenseno);
            });
        });
	}
	//選項建置
	function buildOptionLicenseno(obj,value){
		$.each(prpLthirdPartyLicenseNoArray, function(i, option){
			$(obj).append("<option value='" + prpLthirdPartyLicenseNoArray[i] + "'>" + prpLthirdPartyLicenseNoArray[i] + "</option>");
		});
		//原值回寫
		$(obj).val(value);
	}
	//選擇的參數寫入HIDE
	function setOptionLicenseno(field){
		$(field).parent().find('input[name="prpLpersonTraceLicenseno"]').val(field.value);
	}
	
	function checkLicenseNo(field) {
		var  prpLpersonIdNumber= $(field);
		var identifyNumber = prpLpersonIdNumber.val();
		var sex = $(field).parents("table").find(":input[name='personSex']");
		if(identifyNumber != "" ){
			if (!checkIdentifyNumber(identifyNumber, sex[0].value)) {
				alert("請輸入正確的身份證號");
				return false;
			}
		}
	}
	/**
	 * 根据出生日期计算年龄
	 */
	function updatePersonTraceApplicantBirthday(field){
	    var age = 0;
	    if(field.realValue != "" && field.realValue != null){
	        var birthday = new Date(field.realValue.replace("-","/"));
	        var now = new Date();//获得系统当前时间
	        age = now.getFullYear()-birthday.getFullYear();
	        var nextDate = getNextYearFullDate(field.realValue,age);
	        var temp = compareFullDate(nextDate,convertFullDateToString(now));
	        if(temp > 0 ){
	            age -= 1;
	        }
	        if(age < 0 ){
	            age = 0;
	        }
	    }else{
	        age = "";
	    }
	    var index = $(":input[name='"+field.name+"']").index(field);
	    $(":input[name='prpLpersonTracePersonAge']").get(index).value = age;
	}
	//mantis：CLM0181，處理人員：DP0713，需求單編號：新核心-案件備案WS 3.10查詢及記錄留存作業 END
</script>
<!--建立显示的录入条，可以收缩显示的-->
<table class="subtable" cellspacing="1" cellpadding="0">
	<tr>
		<td >
			<div style="background-color: #ffffff">
				<table class="common" cellpadding="1" cellspacing="1">
					<tr>
						<td class="left">
							<s:text name="claim.hirdPartyDied" />：
							<%-- 第三者亡人数 --%>
						</td>
						<td class="right">
							<input type=text name="prpLregistPersonDeathB" class="input" value="${prpLregist.personDeathB}">
						</td>
						<td class="left">
							<s:text name="claim.thirdInjuryNum" />：
							<%-- 第三者伤人数 --%>
						</td>
						<td class="right">
							<input type=text name="prpLregistPersonInjureB" class="input" value="${prpLregist.personInjureB}">
						</td>
						<td class="left" style="display: none">
							<s:text name="claim.threePeopleAid" />
						</td>
						<%-- 三者人是否急救 --%>
						<td class="right" style="display: none">
							<c:if test="${prpLregist.lextValue2 == '0'}">
								<s:text name="certainLoss.prpLscheduleMainWF.no" />
								<%-- 否 --%>
							</c:if>
							<c:if test="${prpLregist.lextValue2 == '1'}">
								<s:text name="certainLoss.prpLscheduleMainWF.yes" />
								<%-- 是 --%>
							</c:if>
						</td>
						<td class="right"></td>
					</tr>
					<tr>
						<td class="left">
							<s:text name="claim.deathTollPerson" />：
							<%-- 车上人员亡人数 --%>
						</td>
						<td class="right">
							<input type=text name="prpLregistPersonDeathD1" class="input" value="${prpLregist.personDeathD1}">
						</td>
						<td class="left">
							<s:text name="claim.casualtyTollPerson" />：
							<%-- 车上人员伤人数 --%>
						</td>
						<td class="right">
							<input type=text name="prpLregistPersonInjureD1" class="input" value="${prpLregist.personInjureD1}">
						</td>
						<td class="left" style="display: none">
							<s:text name="claim.whetherCarEmerg" />
						</td>
						<%-- 车上人是否急救 --%>
						<td class="right" style="display: none">
							<c:if test="${prpLregist.lextValue1 == '0'}">
								<s:text name="certainLoss.prpLscheduleMainWF.no" />
								<%-- 否 --%>
							</c:if>
							<c:if test="${prpLregist.lextValue1 == '1'}">
								<s:text name="certainLoss.prpLscheduleMainWF.yes" />
								<%-- 是 --%>
							</c:if>
						</td>
						<td class="right"></td>
					</tr>
				</table>
			</div>
		</td>
	</tr>
</table>
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
								<!-- mantis：CLM0181，處理人員：DP0713，需求單編號：新核心-案件備案WS 3.10查詢及記錄留存作業 START -->
								<tr>
									<td class="title" style="width: 15%">
										受害人身分證號：
									</td>
									<%-- 受害人身分證 --%>
									<td class="input" style="width: 20%">
										<input name="prpLpersonTraceIdNumber" class="input" style="width: 70%" maxlength="10" description=""
										/>
										<img src="/claim/images/bgMarkMustInput.jpg">
									</td>
									<td class="title" style="width: 15%" align=center>
										受害人乘坐狀況：
									</td>
									<%-- 受害人乘坐狀況--%>
									<td class="input" style="width: 15%">
										<s:select 
											list="#{'1':'本車上乘客','3':'車外人員'
													,'4':'對方車上乘客','5':'對方車上駕駛'
													,'6':'本車上駕駛'}" name="rideSituation" Style="width:40%" />
										<img src="/claim/images/bgMarkMustInput.jpg">
									</td>
									<td class="title" style='width: 15%' align=center>
										受害人乘坐車輛牌照號碼：
									</td>
									<%-- 受害人乘坐車輛牌照號碼 --%>
									<td class="input" style="width: 20%">
										<div name="selectLicenseno" >
											<s:select list="#{'':''}" name="optionLicenseno" Style="width:40%" onclick="setOptionLicenseno(this)"/>
											<input type="hidden" name="prpLpersonTraceLicenseno">
											<img src="/claim/images/bgMarkMustInput.jpg">
										</div>
									</td>
								</tr>
								<tr>
									<td class="title" style="width: 15%">
										受害人身分證號類別：
									</td>
									<%-- 受害人身分證號類別 --%>
									<td class="input" style="width: 20%">
										<s:select 
											list="#{'ID_NUMBER':'身分證字號','ARC_NUMBER':'居留證號'
													,'PASSPORT_NUM':'護照號碼'}" name="prpLpersonTraceIdNumberType" Style="width:40%" />
									</td>
									<td class="title" style="width: 15%" align=center>
										受害人出生年月日：
									</td>
									<%-- 受害人出生年月日--%>
									<td class="input" style="width: 15%">
										<ad:date class='common' style="width:85%" name="prpLpersonTraceApplicantBirthday" title="出生年份" wdatePicker="true" onblur="updatePersonTraceApplicantBirthday(this)"/>
										<img src="${ctx }/images/bgMarkMustInput.jpg">
									</td>
									<td class="title" style='width: 15%' align=center>
										
									</td>
									<%--  --%>
									<td class="input" style="width: 20%">
									</td>
								</tr>
								<!-- mantis：CLM0181，處理人員：DP0713，需求單編號：新核心-案件備案WS 3.10查詢及記錄留存作業 END -->
								<tr>
									<td class="title" style="width: 15%">
										<s:text name="claim.engagedIndustry" />：
									</td>
									<%-- 从事行业 --%>
									<td class="input" colspan="6" align="left">
										<input type="hidden" name="prpLpersonTraceJobCode1">
										<input type="text" name="prpLpersonTraceJobName1" class="codename" style="width: 100px" ondblclick="code_CodeSelect(this,'BusinessSource1','-1,0','Y','N');"
											onkeyup="code_CodeSelect(this,'BusinessSource1','-1,0','Y','N');" onchange="code_CodeChange(this,'BusinessSource1','-1,0','Y','N');">
										<input type="hidden" name="prpLpersonTraceJobCode2">
										<input type="text" name="prpLpersonTraceJobName2" class="codename" style="width: 100px" ondblclick="code_CodeSelect(this,'BusinessSource2','-1,0','Y','N');"
											onkeyup="code_CodeSelect(this,'BusinessSource2','-1,0','Y','N');" onchange="code_CodeChange(this,'BusinessSource2','-1,0','Y','N');">
										<input type="hidden" name="prpLpersonTraceJobCode">
										<input type="text" name="prpLpersonTraceJobName" class="codename" style="width: 100px" ondblclick="code_CodeSelect(this,'BusinessSource','-1,0','Y','N');"
											onchange="code_CodeChange(this,'BusinessSource','-1,0','Y','N');" onkeyup="code_CodeSelect(this,'BusinessSource','-1,0','Y','N');">
									</td>
								</tr>
								<tr>
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
									<td class="title" align=center>
										<s:text name="certainLoss.hospitals" />：
									</td>
									<%-- 就诊医院 --%>
									<td class="input" colspan="2">
										<input name="prpLpersonTraceHospital" class="input" description="就诊医院">
										<input name="prpLpersonTraceIdentifyNumber" type="hidden">
										<input name="prpLpersonTraceRelatePersonNo" type="hidden">
										<input name="prpLpersonTraceRemark" type="hidden">
										<!-- <input name="prpLpersonTraceFlag"  type="hidden"> -->
									</td>
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
									<!-- mantis：CLM0181，處理人員：DP0713，需求單編號：新核心-案件備案WS 3.10查詢及記錄留存作業 START -->
									<tr>
										<td class="title" style="width: 15%">
											受害人身分證號：
										</td>
										<%-- 受害人身分證 --%>
										<td class="input" style="width: 20%">
											<input name="prpLpersonTraceIdNumber" class="input" style="width: 70%" maxlength="10" description=""
											value="${personTrace.idNumber}" onblur="checkLicenseNo(this)"/>
											<img src="/claim/images/bgMarkMustInput.jpg">
										</td>
										<td class="title" style="width: 15%" align=center>
											受害人乘坐狀況：
										</td>
										<%-- 受害人乘坐狀況--%>
										<td class="input" style="width: 15%">
											<select name="rideSituation" Style="width: 40%">
												<option value="1" <c:if test="${personTrace.rideSituation=='1'}"> selected="selected"</c:if>>本車上乘客</option>
												<option value="3" <c:if test="${personTrace.rideSituation=='3'}"> selected="selected"</c:if>>車外人員</option>
												<option value="4" <c:if test="${personTrace.rideSituation=='4'}"> selected="selected"</c:if>>對方車上乘客</option>
												<option value="5" <c:if test="${personTrace.rideSituation=='5'}"> selected="selected"</c:if>>對方車上駕駛</option>
												<option value="6" <c:if test="${personTrace.rideSituation=='6'}"> selected="selected"</c:if>>本車上駕駛</option>
											</select>
											<img src="/claim/images/bgMarkMustInput.jpg">
										</td>
										<td class="title" style='width: 15%' align=center>
											受害人乘坐車輛牌照號碼：
										</td>
										<%-- 受害人乘坐車輛牌照號碼 --%>
										<td class="input" style="width: 20%">
											<div name="selectLicenseno" >
												<s:select list="#{'':''}" name="optionLicenseno" Style="width:40%" onclick="setOptionLicenseno(this)"/>
												<input type="hidden" name="prpLpersonTraceLicenseno" value="${personTrace.licenseno}">
												<img src="/claim/images/bgMarkMustInput.jpg">
											</div>
										</td>
									</tr>
									<tr>
										<td class="title" style="width: 15%">
											受害人身分證號類別：
										</td>
										<%-- 受害人身分證號類別 --%>
										<td class="input" style="width: 20%">
											<select name="prpLpersonTraceIdNumberType" Style="width: 40%" value="${personTrace.idNumberType}">
												<option value="ID_NUMBER" <c:if test="${personTrace.idNumberType=='ID_NUMBER'}"> selected="selected"</c:if>>身分證字號</option>
												<option value="ARC_NUMBER" <c:if test="${personTrace.idNumberType=='ARC_NUMBER'}"> selected="selected"</c:if>>居留證號</option>
												<option value="PASSPORT_NUM" <c:if test="${personTrace.idNumberType=='PASSPORT_NUM'}"> selected="selected"</c:if>>護照號碼</option>
											</select>
										</td>
										<td class="title" style="width: 15%" align=center>
											受害人出生年月日：
										</td>
										<%-- 受害人出生年月日--%>
										<td class="input" style="width: 15%">
											<ad:date class='common' style="width:85%" name="prpLpersonTraceApplicantBirthday" title="出生年份" wdatePicker="true" onblur="updatePersonTraceApplicantBirthday(this)"
											value="${personTrace.applicantBirthday}"/>
											<img src="${ctx }/images/bgMarkMustInput.jpg">
										</td>
										<td class="title" style='width: 15%' align=center>
											
										</td>
										<%--  --%>
										<td class="input" style="width: 20%">
										</td>
									</tr>
									<!-- mantis：CLM0181，處理人員：DP0713，需求單編號：新核心-案件備案WS 3.10查詢及記錄留存作業 END -->
									<tr>
										<td class="title" style="width: 15%">
											<s:text name="claim.engagedIndustry" />：
										</td>
										<%-- 从事行业 --%>
										<td class="input" colspan="6" align="left">
											<input type="hidden" name="prpLpersonTraceJobCode1" value="${personTrace.jobCode1}">
											<input type="text" name="prpLpersonTraceJobName1" class="codename" style="width: 100px" value="${personTrace.jobName1}" ondblclick="code_CodeSelect(this,'BusinessSource1','-1,0','Y','N');"
												onkeyup="code_CodeSelect(this,'BusinessSource1','-1,0','Y','N');" onchange="code_CodeChange(this,'BusinessSource1','-1,0','Y','N');">
											<input type="hidden" name="prpLpersonTraceJobCode2" value="${personTrace.jobCode2}">
											<input type="text" name="prpLpersonTraceJobName2" class="codename" style="width: 100px" value="${personTrace.jobName2}" ondblclick="code_CodeSelect(this,'BusinessSource2','-1,0','Y','N');"
												onkeyup="code_CodeSelect(this,'BusinessSource2','-1,0','Y','N');" onchange="code_CodeChange(this,'BusinessSource2','-1,0','Y','N');">
											<input type="hidden" name="prpLpersonTraceJobCode" value="${personTrace.jobCode}">
											<input type="text" name="prpLpersonTraceJobName" class="codename" style="width: 100px" value="${personTrace.jobName}" ondblclick="code_CodeSelect(this,'BusinessSource','-1,0','Y','N');"
												onchange="code_CodeChange(this,'BusinessSource','-1,0','Y','N');" onkeyup="code_CodeSelect(this,'BusinessSource','-1,0','Y','N');">
										</td>
									</tr>
									<tr>
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
											<s:text name="certainLoss.hospitals" />：
										</td>
										<%-- 就诊医院 --%>
										<td class="input" colspan="2">
											<input name="prpLpersonTraceHospital" class="input" description="就诊医院" value="${personTrace.hospital}">
											<input name="prpLpersonTraceIdentifyNumber" type="hidden" value="${personTrace.identifyNumber}">
											<input name="prpLpersonTraceRelatePersonNo" type="hidden" value="${personTrace.relatePersonNo}">
											<input name="prpLpersonTraceRemark" type="hidden" value="${personTrace.remark}">
											<!-- <input name="prpLpersonTraceFlag"  type="hidden" value="${personTrace.flag}"> -->
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