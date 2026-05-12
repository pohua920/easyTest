<%@ include file="/common/taglibs.jsp"%>
<script language='javascript'>
	//在下面加入本页自定义的JavaScript方法                        
	/*
	插入一条新的personTrace之後的处理（可选方法）
	 */
	function afterInsertPersonTrace() {
		setPrpLpersonTracePersonNo();
	}

    //mantis：CLM0277 ，處理人員： DP0713 ，需求單編號：理算任務串聯區塊鏈API3.10同業確認+API3.5建立理賠單 (立案頁面進入 判斷人傷資料移除)START
	//mantis：CLM0209，處理人員：DP0713，需求單編號：新核心-立案節點同步備案人傷訊息更新區塊鏈資料 START
	$( window ).on( "load", function() {
		var nodeTypeValue = null!=fm.nodeType&&undefined!=fm.nodeType?fm.nodeType.value:null;
        var registType = null!=fm.registType&&undefined!=fm.registType?fm.registType.value:null;
        var riskCode = null!=fm.riskCode&&undefined!=fm.riskCode?fm.riskCode.value:null;
      	//alert("nodeTypeValue:"+nodeTypeValue+"/riskCode:"+riskCode+"/registType="+registType);
		if(null!=nodeTypeValue && nodeTypeValue!='compe'){//這頁原本只存在立案與備案 不存在理算 後來 0277 放入理算，移除0181，人傷檢查放在提交不是載入時候提醒
	        prpLthirdPartyLicenseNoRebulid();
	        buildSelectLicenseno();
	        $( 'input[name="prpLthirdPartyLicenseNo"]' ).on( "blur", function() {
	            prpLthirdPartyLicenseNoRebulid();
	            buildSelectLicenseno();
	        });
		}
        /*
        //mantis：CLM0181，處理人員：DP0713，需求單編號：新核心-案件備案WS 3.10查詢及記錄留存作業 START
        try{
	        var riskCode = null!=fm.riskCode&&undefined!=fm.riskCode?fm.riskCode.value:null;
	        //alert("riskCode:"+riskCode+"/registType="+registType);
	        if(null!=riskCode && null!=registType && (riskCode == "B01" && registType == "2")){//B01 且為關聯單 檢查人傷資料 缺資料移除按鈕不給儲存
	        	var countPrpLpersonTraceIdNumber = 0;
				$('input[name="prpLpersonTraceIdNumber"]').each(function(index){
					if(index!=0){			
						if($(this).val()!=""){
							countPrpLpersonTraceIdNumber++;
						}
					}
				});
				var pass = true;
				if(countPrpLpersonTraceIdNumber==0){
					//alert("請確認受損訊息內【人傷跟蹤訊息】頁簽之受害人身分證號、乘坐狀況、乘坐牌照號碼皆為必填。");
					pass = false;
				}
				for (var i = 1; i < fm.prpLpersonTraceIdNumber.length; i++) {
					if (fm.prpLpersonTraceIdNumber[i].value == '') {
						//alert("請確認受損訊息內【人傷跟蹤訊息】頁簽之受害人身分證號、乘坐狀況、乘坐牌照號碼皆為必填。");
						pass = false;
					}
				}
				for (var i = 1; i < fm.prpLpersonTraceLicenseno.length; i++) {
					if (fm.prpLpersonTraceLicenseno[i].value == '') {
						//alert("請確認受損訊息內【人傷跟蹤訊息】頁簽之受害人身分證號、乘坐狀況、乘坐牌照號碼皆為必填。");
						pass = false;
					}
				}
				for (var i = 1; i < fm.prpLpersonTraceApplicantBirthday.length; i++) {
					if (fm.prpLpersonTraceApplicantBirthday[i].value == '') {
						//alert("prpLpersonTraceApplicantBirthday皆為必填。");
						pass = false;
					}
				}
				if(!pass){
		            $('input[name="buttonSaveFinishSubmitSimple"]').each(function() { 
		            	if(this.value == "簡易賠案"){
		    	        	$(this).hide();
		            	}
		            });
					alert("遺漏人傷資料，請先移至\"備案修改\"補完人傷資訊，再返回\"立案\"修正!");
				}
	        }
        }catch(e){}
    	//mantis：CLM0181，處理人員：DP0713，需求單編號：新核心-案件備案WS 3.10查詢及記錄留存作業 END
        */
    	//mantis：CLM0277 ，處理人員： DP0713 ，需求單編號：理算任務串聯區塊鏈API3.10同業確認+API3.5建立理賠單 END
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
	
	function changeDef(){
		fm.defValue.value = 'Y';
	}
	//mantis：CLM0209，處理人員：DP0713，需求單編號：新核心-立案節點同步備案人傷訊息更新區塊鏈資料 END
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
			}
		}
	}
	//mantis：CLM0181，處理人員：DP0713，需求單編號：新核心-案件備案WS 3.10查詢及記錄留存作業 START
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
<table class="common" cellpadding="5" cellspacing="1">
	<!--表示显示多行的-->
	<tr>
		<td class="common" colspan="4" style="text-align: left;">
			<img style="cursor: hand;" src="${ctx }/images/butCollapseBlue.gif" name="PersonTraceImg" onclick="showPage(this,spanClaimpersonTrace)">
			<s:text name="info.personTrack" />
			<%-- 人伤跟踪信息 --%>
			<br>
			<table cellpadding="5" cellspacing="1" class=common id="PersonTrace_Data" style="display: none">
				<tbody>
					<tr>
						<td class="input" style="width: 3%">
							<div align="center">
								<input class="readonly" readonly="readonly" style="width: 50%" name="prpLpersonTracePersonNo" description="序号">
								<input type="hidden" name="selectSend" value="0">
								<input type="hidden" name="prpLpersonTraceSelectSend" value="0">
								<!-- mantis：CLM0209，處理人員：DP0713，需求單編號：新核心-立案節點同步備案人傷訊息更新區塊鏈資料 -->
								<input type="hidden" name="defValue" value="N">
								<!-- mantis：CLM0181，處理人員：DP0713，需求單編號：新核心-案件備案WS 3.10查詢及記錄留存作業 START-->
								<input type="hidden" name="registType" value="${registType}">
								<input type="hidden" name="riskCode" value="${riskCode}">
								<!-- mantis：CLM0181，處理人員：DP0713，需求單編號：新核心-案件備案WS 3.10查詢及記錄留存作業 END-->
							</div>
						</td>
						<td class="common">
							<table cellpadding="5" cellspacing="1" class="common">
								<tr>
									<td class="title" style="width: 15%">
										<s:text name="claim.name" />：
									</td>
									<%-- 姓　　名 --%>
									<td class="input" style="width: 20%">
										<input name="prpLpersonTracePersonName" class="input" style="width: 70%" maxlength=20 description="人伤跟踪姓名">
									</td>
									<td class="title" style="width: 15%" align=center>
										<s:text name="db.prpLpersonloss.sex" />：
									</td>
									<%-- 性　　别 --%>
									<td class="input" style="width: 15%">
										<%-- 人伤性别默认为未说明  --%>
										<select name="personSex" id="personSex" style="width: 50%">
											<option <c:if test="${prpLpersonTrace.personSex == '9'}">selected="selected"</c:if> value="9">
												<s:text name="claim.notSay" />
												<%-- 未说明 --%>
											</option>
											<option value="1" <c:if test="${prpLpersonTrace.personSex == '1'}">selected="selected"</c:if>>
												<s:text name="certainLoss.male" />
												<%-- 男 --%>
											</option>
											<option value="2" <c:if test="${prpLpersonTrace.personSex == '2'}">selected="selected"</c:if>>
												<s:text name="certainLoss.female" />
												<%-- 女 --%>
											</option>
										</select> <img src="${ctx }/images/bgMarkMustInput.jpg">
									</td>
									<td class="title" style='width: 15%'>
										<s:text name="db.prpLpersonloss.age" />：
									</td>
									<%-- 年　　龄 --%>
									<td class="input" style="width: 20%">
										<input name="prpLpersonTracePersonAge" class="input" maxlength=5 description="年龄">
									</td>
								</tr>
								<!-- mantis：CLM0209，處理人員：DP0713，需求單編號：新核心-立案節點同步備案人傷訊息更新區塊鏈資料 START -->
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
									<td class="title" style='width: 15%' align='left'>
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
								<!-- mantis：CLM0181，處理人員：DP0713，需求單編號：新核心-案件備案WS 3.10查詢及記錄留存作業 START-->
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
								<!-- mantis：CLM0181，處理人員：DP0713，需求單編號：新核心-案件備案WS 3.10查詢及記錄留存作業 END-->
								<!-- mantis：CLM0209，處理人員：DP0713，需求單編號：新核心-立案節點同步備案人傷訊息更新區塊鏈資料 END -->
								<%-- 行业分三级选择  --%>
								<tr>
									<td class="title" style="width: 15%">
										<s:text name="claim.engagedIndustry" />：
									</td>
									<%-- 从事行业 --%>
									<td class="input" colspan="6" align="left">
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
								<%-- 行业分三级选择 --%>
								<tr>
									<td class="title" style="width: 15%">
										<s:text name="claim.involRisk" />：
									</td>
									<%-- 涉及险种 --%>
									<td class="input" colspan="2" align=left>
										<c:set var="referKind" value="${personTrace.referKind}" />
										<s:select name="prpLpersonTraceReferKind" id="prpLpersonTraceReferKind" list="#request.referKindList" listKey="kindCode" listValue="kindName" value="#attr.referKind" style="width:50%"
											headerKey=" " headerValue=" " />
									</td>
									<td class="title" align="center">
										<s:text name="regist.prpLregist.casualtiesType" />：
									</td>
									<%-- 伤亡类型 --%>
									<td class="input" colspan="2">
										<s:select name="prpLpersonTraceFlag" listKey="key" listValue="value" list="#request.casualtiesList" />
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
										<select name="motionFlag" id="motionFlag">
											<option <c:if test="${prpLpersonTrace.motionFlag == '0'}">selected="selected"</c:if> value="0">
												<s:text name="certainLoss.prpLscheduleMainWF.no" />
												<%-- 否 --%>
											</option>
											<option <c:if test="${prpLpersonTrace.motionFlag == '1'}">selected="selected"</c:if> value="1">
												<s:text name="certainLoss.prpLscheduleMainWF.yes" />
												<%-- 是 --%>
											</option>
										</select>
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
			<span id="spanClaimpersonTrace" style="display: none"> <%-- 多行输入展现域 --%>
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
							<td class="centertitle" style="width: 4%"></td>
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
									<input type="button" value="+" class=smallbutton onclick="insertRow('PersonTrace')" name="buttonPersonTraceInsert" style="cursor: hand">
								</div>
							</td>
						</tr>
					</tfoot>
					<tbody>
						<c:forEach var="personTrace" items="${requestScope.prpLpersonTrace.personTraceList}" varStatus="stat">
							<c:choose>
								<c:when test="${stat.index%2==0}">
									<tr class=oddrow>
								</c:when>
								<c:otherwise>
									<tr class=oddrow>
								</c:otherwise>
							</c:choose>
							<td class="input" style="width: 4%">
								<div align="center">
									<input name="prpLpersonTracePersonNo" class="readonlyno" readonly="true" value="${personTrace.id.personNo}">
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
											<input name="prpLpersonTracePersonName" class="input" style="width: 70%" maxlength=20 description="人伤跟踪姓名" value="${personTrace.personName}">
										</td>
										<td class="title" style="width: 15%" align=center><s:text name="db.prpLpersonloss.sex" />：</td>
										<td class="input" style="width: 15%">
											<select style="width: 50%" name="personSex">
												<c:forEach var="personSex" items="${requestScope.driverSexs}">
													<option value="${personSex.id.codeCode}" <c:if test="${personTrace.personSex==personSex.id.codeCode}">selected="selected"</c:if>>
														<c:out value="${personSex.codeCName}" />
													</option>
												</c:forEach>
											</select><img src="/claim/images/bgMarkMustInput.jpg">
										</td>
										<td class="title" style='width: 15%'>
											<s:text name="db.prpLpersonloss.age" />：
										</td>
										<%-- 年　　龄 --%>
										<td class="input" style='width: 20%'>
											<input name="prpLpersonTracePersonAge" class="input" maxlength=5 description="年龄" value="${personTrace.personAge}">
										</td>
									</tr>
									<!-- mantis：CLM0209，處理人員：DP0713，需求單編號：新核心-立案節點同步備案人傷訊息更新區塊鏈資料 START -->
									<tr>
										<td class="title" style="width: 15%">
											受害人身分證號：
										</td>
										<%-- 受害人身分證 --%>
										<td class="input" style="width: 20%">
											<input name="prpLpersonTraceIdNumber" class="input" style="width: 70%" maxlength="10" description=""
											value="${personTrace.idNumber}" onblur="checkLicenseNo(this)" onchange="changeDef()"/>
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
										<td class="title" style='width: 15%' align="left">
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
									<!-- mantis：CLM0209，處理人員：DP0713，需求單編號：新核心-立案節點同步備案人傷訊息更新區塊鏈資料 END -->
									<!-- mantis：CLM0181，處理人員：DP0713，需求單編號：新核心-案件備案WS 3.10查詢及記錄留存作業 START -->
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
									<%-- 行业分三级选择 --%>
									<tr>
										<td class="title" style="width: 15%">
											<s:text name="claim.engagedIndustry" />：
										</td>
										<%-- 从事行业 --%>
										<td class="input" colspan="6" align="left">
											<input type="hidden" name="prpLpersonTraceJobCode1" value="${personTrace.jobCode1}">
											<input type="text" name="prpLpersonTraceJobName1" class="codename" style="width: 100px" value="${personTrace.jobName1}" ondblclick="code_CodeSelect(this,'BusinessSource1','-1,0','Y');"
												onkeyup="code_CodeSelect(this,'BusinessSource1','-1,0','Y');" onchange="code_CodeChange(this,'BusinessSource1','-1,0','Y');">
											<input type="hidden" name="prpLpersonTraceJobCode2" value="${personTrace.jobCode2}">
											<input type="text" name="prpLpersonTraceJobName2" class="codename" style="width: 100px" value="${personTrace.jobName2}" ondblclick="code_CodeSelect(this,'BusinessSource2','-1,0','Y');"
												onkeyup="code_CodeSelect(this,'BusinessSource2','-1,0','Y');" onchange="code_CodeChange(this,'BusinessSource2','-1,0','Y');">
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
											<c:set var="referKind" value="${personTrace.referKind}" />
											<s:select name="prpLpersonTraceReferKind" id="prpLpersonTraceReferKind" list="#request.referKindList" listKey="kindCode" listValue="kindName" style="width:50%" value="#attr.referKind"
												headerKey=" " headerValue=" " />
										</td>
										<td class="title" align="center">
											<s:text name="regist.prpLregist.casualtiesType" />：
										</td>
									<%-- 伤亡类型 --%>
										<td class="input" colspan="2">
											<c:set var="flag" value="${personTrace.flag}" />
											<s:select name="prpLpersonTraceFlag" value="#attr.flag" listKey="key" listValue="value" list="#request.casualtiesList" />
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
											<select name="motionFlag" id="motionFlag">
												<option <c:if test="${personTrace.motionFlag == '0'}">selected="selected"</c:if> value="0">
													<s:text name="certainLoss.prpLscheduleMainWF.no" />
													<%-- 否 --%>
												</option>
												<option <c:if test="${personTrace.motionFlag == '1'}">selected="selected"</c:if> value="1">
													<s:text name="certainLoss.prpLscheduleMainWF.yes" />
													<%-- 是 --%>
												</option>
											</select>
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
					</tbody>
				</table>
			</span>
		</td>
	</tr>
</table>