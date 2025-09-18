<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
	String path = request.getContextPath();
	String title = "傷害險通報補送作業";
	String image = path + "/" + "images/";
	String GAMID = "APS013A0";
	String mDescription = "補送作業";
	String nameSpace = "/aps/013";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<script language="JavaScript">
	$(document).ready(function(){
		<c:if test="${not empty errorMsg}">
			var msgg = "有錯誤\n";
			<c:forEach items="${errorMsg}" var="entry">
				$('input[id^=<c:out value="${entry.key}" /> ]').css('background-color','yellow');
				<c:if test="${entry.value != ''}">
					msgg = msgg + '<c:out value="${entry.value}" />' + "\n";
				</c:if>
			</c:forEach>
			alert(msgg);
		</c:if>
	});

	function form_submit(type){
		if("resend" == type && confirm("確認要重送此筆資料?")==true && checkNull() && checkData() && compareDate() && checkNum()){
			$("#mainForm").attr("action","btnReSendLia.action");
			$("#mainForm").submit();
		}
	}
	
	function checkNull(){
		var dataAry = ["con","insnom","insno","cmpno","cmptype","askname","askidno","asktype","name","idno","sex","birdate","filldate","broktype","valdate","valtime",
		               "ovrdate","ovrtime","prm","bamttype","prmyears","condate","contime","sourceRemark","origin","channel","insclass","inskind","insitem","paytype","itema",
		               "itemb","itemc","itemd","iteme","itemf","itemg","itemh","itemi","itemj","itemk","iteml","itemm","itemn","itemo","itemp","itemq","itemr","items"];
		var nameAry = ["保單狀況","主約保單號碼","保單號碼","公司代號","產壽險別","要保人姓名","要保人身分證號碼","要保人與被保險人關係","被保險人姓名","被保險人身分證字號","被保人性別","被保人出生日期","要保書填寫日期/要保日期","保經代類別","契約生效日期","契約生效四碼時分","契約滿期日期","契約滿期四碼時分","保費",
		               "保費繳別","保費繳費年期","保單狀況生效日期","保單狀況四碼時分","資料來源","來源別","銷售通路別","保單分類","險種分類","險種","公、自費件","給付項目(身故)","給付項目(完全失能或最高級失能)","給付項目(失能扶助金)","給付項目(特定事故保險金)","給付項目(初次罹患)","給付項目(醫療限額)","給付項目(醫療限額自負額)","給付項目(日額)",
		               "給付項目(住院手術)","給付項目(門診手術)","給付項目(門診)","給付項目(重大疾病(含特定傷病))","給付項目(重大燒燙傷)","給付項目(癌症療養)","給付項目(出院療養)","給付項目(喪失工作能力)","給付項目(喪葬費用)","給付項目(銜接原醫療限額之自負額)","給付項目(分期給付)"];
		var msg = "";
		
		var announceCase = $("#announceCase").val();
		
		for(var row = 0; row < $('[id=tableCount]').length ; row++){
			for(var i=0; i<dataAry.length; i++){
				if(announceCase == "UNDWRT" && (dataAry[i] == "filldate" || dataAry[i] == "broktype")){
					continue;
				}
				
				var obj = $("#dtlDevResults" + "\\["+row+"\\]\\." + dataAry[i]);
				obj.css('background-color','WHITE');
				if(obj.val() == "" ||obj.val() == null){
					obj.css('background-color','yellow');
					msg += "第" + (row + 1) + "筆資料的" + nameAry[i] + "不可為空值\r\n";
				}
			}
		}
		if(msg != ""){
			alert(msg);
			return false;
		}
		return true;
	}
	
	function checkData(){
		var regexDate = /^0([0-9]\d{2}(0+[1-9]|1[012])(0+[1-9]|[12][0-9]|3[01]))*$/;
		var dateArray = ['condate','askbirdate','birdate','filldate','valdate','ovrdate'];
		var dateNameArray = ['保單狀況生效日','要保人出生日期','被保險人出生日期','要保書填寫日期/要保日期','契約生效日期','契約滿期日期'];
		var dateMsg = "";
		
	    for(var row = 0; row < $('[id=tableCount]').length ; row++){
			for(var i=0; i<dateArray.length; i++){
				var objVal = $("#dtlDevResults" + "\\["+row+"\\]\\." + dateArray[i]).val();
				//alert(dateArray[i] + "=" + objVal);
				if(objVal != "" && !regexDate.test(objVal)){
					if(dateMsg == ""){
						dateMsg += "第" + (row + 1) + "筆資料的" + dateNameArray[i];
					}else{
						dateMsg += "、" + "第" + (row + 1) + "筆資料的" + dateNameArray[i];	
					}
				}
			}
	    }
		var regexTime = /^(20|21|22|23|[0-1]\d)([0-5]\d)$/;
		var timeArray = ['contime','valtime','ovrtime'];
		var timeNameArray = ['保單狀況生效日四碼時分','契約生效日期四碼時分','契約滿期日期四碼時分'];
		var timeMsg = "";
		for(var row = 0; row < $('[id=tableCount]').length ; row++){
			for(var i=0; i<timeArray.length; i++){
				var objVal = $("#dtlDevResults" + "\\["+row+"\\]\\." + timeArray[i]).val();
				//alert(timeArray[i] + "=" + objVal);
				if(objVal !="" && !regexTime.test(objVal)){
					if(timeMsg != "" || dateMsg != ""){
						timeMsg += "、" + "第" + (row + 1) + "筆資料的" + timeNameArray[i];
					}else{
						timeMsg += "第" + (row + 1) + "筆資料的" + timeNameArray[i];	
					}
				}
			}
		}
		if(dateMsg != "" || timeMsg != ""){
			alert(dateMsg + timeMsg + "不符合日期時間格式!");
			return false;
		}
		return true;
	}
	
	function compareDate(){
		
		for(var row = 0; row < $('[id=tableCount]').length ; row++){
			var ovrdate = $("#dtlDevResults" + "\\["+row+"\\]\\.ovrdate").val();
			var valdate = $("#dtlDevResults" + "\\["+row+"\\]\\.valdate").val();
			var askbirdate = $("#dtlDevResults" + "\\["+row+"\\]\\.askbirdate").val();
			var birdate = $("#dtlDevResults" + "\\["+row+"\\]\\.birdate").val();
			var filldate = $("#dtlDevResults" + "\\["+row+"\\]\\.filldate").val();

			if(ovrdate!="" && valdate!="" && valdate > ovrdate){
				alert("第" + (row + 1) + "筆資料-契約生效日不得大於契約滿期日!");
				return false;
			}
			if(filldate!="" && valdate!="" && filldate > valdate){
				alert("第" + (row + 1) + "筆資料-要保書填寫日期/要保日期不得大於契約生效日、契約滿期日!");
				return false;
			}
			if(birdate!="" && filldate!="" && birdate > filldate){
				alert("第" + (row + 1) + "筆資料-被保險人生日不得大於契約生效日、契約滿期日、要保書填寫日期/要保日期!");
				return false;
			}
			//if(askbirdate!="" && filldate!="" && askbirdate > filldate){
			//	alert("第" + (row + 1) + "筆資料-要保險人生日不得大於契約生效日、契約滿期日、要保書填寫日期/要保日期!");
			//	return false;
			//}
		}
		return true;
	}

	function resentBlock(type){
		if(type == "detail"){
			$('#moreDtlDiv input').attr("class","txtLabel").attr("readonly",true);
			$('#moreDtlDiv select').attr("class","txtLabel").attr("disabled",true);
			$("#resendButton").css("display", "none");
			$("#resendTr").css("display", "none");
		}else if(type == "update"){
			$('#moreDtlDiv input').removeAttr('readonly').removeClass();
			$('#moreDtlDiv select').removeAttr('disabled').removeClass();
			$("#resendButton").css("display", "block");
			$("#resendTr").css("display", "block");
			$("#dataserno").attr("readonly",true).attr("class","txtLabel");
			$("#cmpno").attr("readonly",true).attr("class","txtLabel");
			$("#cmptype").attr("disabled",true).attr("class","txtLabel");
			$("#idno").attr("readonly",true).attr("class","txtLabel");
			$("#keyidno").attr("readonly",true).attr("class","txtLabel");
			$("#dcreate").attr("readonly",true).attr("class","txtLabel");
			$("#sourceRemark").attr("readonly",true).attr("class","txtLabel");
		}
	}
	
	function checkNum(){
		var regex = /^\d+$/;
		var numArray = ['itema','itemb','itemc','itemd','iteme','itemf','itemg','itemh','itemi','itemj','itemk',
			'iteml','itemm','itemn','itemo','itemp','itemq','itemr','items'];
		
		for(var row = 0; row < $('[id=tableCount]').length ; row++){
			for(var i=0; i<numArray.length; i++){
				var objVal = $("#dtlDevResults" + "\\["+row+"\\]\\." + numArray[i]).val();
				if(objVal != "" && !regex.test(objVal)){
					alert("第" + (row + 1) + "筆資料-給付項目輸入" + numArray[i] + "必須為數字!");
					return false;
				}
			}
			var prmyearsVal = $("#dtlDevResults" + "\\["+row+"\\]\\.prmyears").val();
			if(prmyearsVal !="" && !regex.test(prmyearsVal)){
				alert("繳費年期輸入必須為數字!");
				return false;
			}
		}
		return true;
	}

	function showMsg(msg){
		alert(msg);
	}
	
	var msg = "<%=request.getAttribute("message")%>";
	if('' != msg && 'null' != msg){
		showMsg(msg);
	}
</script>
</head>
<s:url action="lnkGoMoreDetail?" namespace="/aps/013" var="moreDtlQuery"/>
<body style="margin:0;text-align:left">
<table cellspacing="1" cellpadding="1" width="970px" border="0">
	<tr>
		<td width="485px">
			<img src="${pageContext.request.contextPath}/images/Help_Icon.gif" border="0" width="49" height="26">
			<a href="${pageContext.request.contextPath}<%=nameSpace%>/lnkGoQuery.action">
			<img src="${pageContext.request.contextPath}/images/Search_Icon.gif" border="0" width="48" height="26"></a>
		</td>
		<td align="right" width="485px">PGMID：<%=GAMID%></td>
	</tr>
	<tr>
		<td align="center" colSpan="2"><h3><%=title%></h3></td>
	</tr>
</table>

	<table id="table2"  cellspacing="0" cellpadding="0" width="970px">
		<tr>
			<td class="MainTdColor" style="width:200px" align="center">
				<span id="lbSearch"><b>補送作業</b></span></td>
			<td colspan="3" class="image"></td>
		</tr>
	</table>
<!-- form 開始 -->
<s:form theme="simple" namespace="/aps/013" id="mainForm" name="mainForm">
	<s:hidden name="announceCase" id="announceCase"/>
	<s:if test="sendResults != null && 0 != sendResults.size">
		本次補送結果如下：
		<table border="1" id="gridtable" width="970px" border="0" class="main_table">
			<tr align="center">
				<th width="60px">資料序號</th>
				<th>傳送時間</th>
				<th>回傳代號</th>
				<th>訊息內容</th>
			</tr>
			<s:iterator var="row" value="sendResults" status="st">
				<tr onmouseover="GridOver(this)" onmouseout="GridOut(this)" bgcolor="#EFEFEF">
					<td align="center"><!-- 資料序號 -->
						<s:property value="dataserno"/>
					</td>
					<td align="center"><!-- 傳送時間 -->
						<s:property value="sendtime"/>
					</td>
					<td align="center"><!-- 回傳代號 -->
						<s:property value="rtncode"/>
					</td>
					<td align="center"><!-- 訊息內容 -->
						<s:property value="rtnmsg"/>
					</td>
				</tr>
			</s:iterator>
		</table>
		<br>
	</s:if>
	
	<!--DetailGrid Table-->
	<s:if test="announceCase == 'RCV'">
		收件通報補送內容：
	</s:if>
	<s:if test="announceCase == 'UNDWRT'">
		承保通報補送內容：
	</s:if>	
	<s:if test="dtlDevResults != null && 0 != dtlDevResults.size">
	
		<s:iterator var="row" value="dtlDevResults" status="st">
		
		<s:hidden name="tableCount" id="tableCount" value="%{#st.count}" />
		<table class="MainBodyColor" id="dataTable" cellpadding="0" cellspacing="0" width="970px" >
		<tr>
			<td width="200px" align="right">資料序號：</td>
			<td width="200px" align="left">
				<s:textfield key="%{'dtlDevResults['+#st.index+'].dataserno'}" id="%{'dtlDevResults['+#st.index+'].dataserno'}"	size="5" maxlength="5" value="%{#st.count}"/>
			</td>
			<td width="200px" align="right">批次號：</td>
			<td width="200px" align="left">
				<span id="checkno"></span>
			</td>
		</tr>
		<tr>
			<td width="135px" align="right">保單狀況：</td>
			<td align="left" colspan="4">
				<s:select key="%{'dtlDevResults['+#st.index+'].con'}" id="%{'dtlDevResults['+#st.index+'].con'}" list="conMap"/>
			</td>
		</tr>	
		<tr>
			<td width="200px" align="right">主約保單號碼-insnom：</td>
			<td width="200px" align="left">
				<s:textfield key="%{'dtlDevResults['+#st.index+'].insnom'}"  id="%{'dtlDevResults['+#st.index+'].insnom'}" maxLength="20" />
			</td>	
			<td width="200px" align="right">保單號碼-insno：</td>
			<td width="200px" align="left">
				<s:textfield key="%{'dtlDevResults['+#st.index+'].insno'}" id="%{'dtlDevResults['+#st.index+'].insno'}" maxLength="20"/>
			</td>
		</tr>
		<tr>
			<td width="200px" align="right">公司代號-cmpno：</td>
			<td width="200px" align="left">
				<s:textfield key="%{'dtlDevResults['+#st.index+'].cmpno'}" id="%{'dtlDevResults['+#st.index+'].cmpno'}" />
			</td>
			<td width="200px" align="right">產壽險別-cmptype：</td>
			<td width="200px" align="left">
				<s:select list="cmptypeMap"	key="%{'dtlDevResults['+#st.index+'].cmptype'}" id="%{'dtlDevResults['+#st.index+'].cmptype'}" /> 
			</td>
		</tr>
		<tr>
			<td width="200px" align="right">要保人姓名-askname：</td>
			<td width="200px" align="left">
				<s:textfield key="%{'dtlDevResults['+#st.index+'].askname'}" id="%{'dtlDevResults['+#st.index+'].askname'}" maxLength="10"/>
			</td>
			<td width="200px" align="right">要保人身分證號碼-askidno：</td>
			<td width="200px" align="left">
				<s:textfield key="%{'dtlDevResults['+#st.index+'].askidno'}" id="%{'dtlDevResults['+#st.index+'].askidno'}" maxLength="20" size="20"/>
			</td>
		</tr>
		<tr>
			<td width="200px" align="right">要保人出生日期-askbirdate：</td>
			<td width="200px" align="left">
				<s:textfield key="%{'dtlDevResults['+#st.index+'].askbirdate'}" id="%{'dtlDevResults['+#st.index+'].askbirdate'}" maxLength="8" size="8" />
			</td>
			<td width="200px" align="right">要保人與被保險人關係-asktype：</td>
			<td width="200px" align="left">
				<s:select list="asktypeMap"	key="%{'dtlDevResults['+#st.index+'].asktype'}" id="%{'dtlDevResults['+#st.index+'].asktype'}" />
			</td>
		</tr>
		<tr>
			<td width="200px" align="right">被保險人姓名-name：</td>
			<td width="200px" align="left">
				<s:textfield key="%{'dtlDevResults['+#st.index+'].name'}" id="%{'dtlDevResults['+#st.index+'].name'}" maxLength="10" size="10"/>
			</td>
			<td width="200px" align="right">被保險人身分證字號-idno：</td>
			<td width="200px" align="left">
				<s:textfield key="%{'dtlDevResults['+#st.index+'].idno'}" id="%{'dtlDevResults['+#st.index+'].idno'}" />
			</td>
		</tr>
		<!-- 
		<tr>
			<td width="200px" align="right">被保險人身份證字號-keyidno：</td>
			<td width="200px" align="left">
				<s:textfield key="%{'dtlDevResults['+#st.index+'].keyidno'}" id="%{'dtlDevResults['+#st.index+'].keyidno'}" />
			</td>
		</tr>
		 -->
		<tr>
			<td width="200px" align="right">被保人性別-sex：</td>
			<td width="200px" align="left">
				<s:select list="sexMap" key="%{'dtlDevResults['+#st.index+'].sex'}" id="%{'dtlDevResults['+#st.index+'].sex'}" />
			</td>
			<td width="200px" align="right">被保人出生日期-birdate：</td>
			<td width="200px" align="left">
				<s:textfield key="%{'dtlDevResults['+#st.index+'].birdate'}" id="%{'dtlDevResults['+#st.index+'].birdate'}" maxLength="8" size="8"/>
			</td>
		</tr>
		<tr>
			<td width="200px" align="right">要保書填寫日期/要保日期-filldate：</td>
			<td width="200px" align="left">
				<s:textfield key="%{'dtlDevResults['+#st.index+'].filldate'}" id="%{'dtlDevResults['+#st.index+'].filldate'}" maxLength="8" size="8" />
			</td>
			<td width="200px" align="right">保經代類別-broktype：</td>
			<td width="200px" align="left">
				<s:select list="broktypeMap" key="%{'dtlDevResults['+#st.index+'].broktype'}" id="%{'dtlDevResults['+#st.index+'].broktype'}" />
			</td>
		</tr>
		<!-- 
		<tr>
			<td width="200px" align="right">建立時間-dcreate(由各中介轉進時間)：</td>
			<td width="200px" align="left">
				<s:textfield key="%{'dtlDevResults['+#st.index+'].dcreate'}" id="%{'dtlDevResults['+#st.index+'].dcreate'}" />
			</td>
			<td width="200px" align="right">傳送時間(送公會時間)：</td>
			<td width="200px" align="left">
				<span id="sendtime"></span>
			</td>
		</tr>
		 -->
		<tr>	
			<td width="200px" align="right">契約生效日期-valdate：</td>
			<td width="200px" align="left">
				<s:textfield key="%{'dtlDevResults['+#st.index+'].valdate'}" id="%{'dtlDevResults['+#st.index+'].valdate'}" maxLength="8" size="8"/>
				四碼時分-valtime：<s:textfield key="%{'dtlDevResults['+#st.index+'].valtime'}" id="%{'dtlDevResults['+#st.index+'].valtime'}" maxLength="4" size="4"/>
			</td>
			<td width="200px" align="right">契約滿期日期-ovrdate：</td>
			<td width="200px" align="left">
				<s:textfield key="%{'dtlDevResults['+#st.index+'].ovrdate'}" id="%{'dtlDevResults['+#st.index+'].ovrdate'}" maxLength="8" size="8"/>
				四碼時分-ovrtime：<s:textfield key="%{'dtlDevResults['+#st.index+'].ovrtime'}" id="%{'dtlDevResults['+#st.index+'].ovrtime'}" maxLength="4" size="4"/>
			</td>
		</tr>
		<tr>
			<td width="200px" align="right">保費-prm：</td>
			<td width="200px" align="left">
				<s:textfield key="%{'dtlDevResults['+#st.index+'].prm'}" id="%{'dtlDevResults['+#st.index+'].prm'}" maxLength="10" size="10"/>
			</td>
			<td width="200px" align="right">保費繳別-bamttype：</td>
			<td width="200px" align="left">
				<s:select list="bamttypeMap" key="%{'dtlDevResults['+#st.index+'].bamttype'}" id="%{'dtlDevResults['+#st.index+'].bamttype'}" />
			</td>
		</tr>
		<tr>
			<td width="200px" align="right">保費繳費年期-prmyears：</td>
			<td width="200px" align="left">
				<s:textfield key="%{'dtlDevResults['+#st.index+'].prmyears'}" id="%{'dtlDevResults['+#st.index+'].prmyears'}" maxLength="3" size="3"/>
			</td>
			<td width="200px" align="right">保單狀況生效日期-condate：</td>
			<td width="200px" align="left">
				<s:textfield key="%{'dtlDevResults['+#st.index+'].condate'}" id="%{'dtlDevResults['+#st.index+'].condate'}" maxLength="8" size="8"/>
				四碼時分-contime：<s:textfield key="%{'dtlDevResults['+#st.index+'].contime'}" id="%{'dtlDevResults['+#st.index+'].contime'}" maxLength="4" size="4"/>
			</td>
		</tr>
		<tr>
			<td width="200px" align="right">資料來源-sourceRemark：</td>
			<td width="200px" align="left">
				<s:textfield key="%{'dtlDevResults['+#st.index+'].sourceRemark'}" id="%{'dtlDevResults['+#st.index+'].sourceRemark'}" />
			</td>
			<td width="200px" align="right">來源別-origin：</td>
			<td width="200px" align="left">
				<s:select list="originMap"	key="%{'dtlDevResults['+#st.index+'].origin'}" id="%{'dtlDevResults['+#st.index+'].origin'}" />
			</td>
		</tr>	
		<tr>
			<td width="200px" align="right">銷售通路別-channel：</td>
			<td width="200px" align="left">
				<s:select list="channelMap"	key="%{'dtlDevResults['+#st.index+'].channel'}" id="%{'dtlDevResults['+#st.index+'].channel'}" />
			</td>
			<td width="200px" align="right">商品代碼-prdcode：</td>
			<td width="200px" align="left">
				<s:textfield key="%{'dtlDevResults['+#st.index+'].prdcode'}" id="%{'dtlDevResults['+#st.index+'].prdcode'}" maxLength="20" size="20"/>
			</td>
		</tr>
		<tr>
			<td width="200px" align="right">保單分類-insclass：</td>
			<td width="200px" align="left">
				<s:select list="insclassMap" key="%{'dtlDevResults['+#st.index+'].insclass'}" id="%{'dtlDevResults['+#st.index+'].insclass'}" />
			</td>
			<td width="200px" align="right">險種分類-inskind：</td>
			<td width="200px" align="left">
				<s:select list="inskindMap" key="%{'dtlDevResults['+#st.index+'].inskind'}" id="%{'dtlDevResults['+#st.index+'].inskind'}" />
			</td>
		</tr>
		<tr>
			<td width="200px" align="right">險種-insitem：</td>
			<td width="200px" align="left">
				<s:select list="insitemMap" key="%{'dtlDevResults['+#st.index+'].insitem'}" id="%{'dtlDevResults['+#st.index+'].insitem'}" />
			</td>
			<td width="200px" align="right">公、自費件-paytype：</td>
			<td width="200px" align="left">
				<s:select list="paytypeMap" key="%{'dtlDevResults['+#st.index+'].paytype'}" id="%{'dtlDevResults['+#st.index+'].paytype'}" />
			</td>
		</tr>
		<!-- 給付項目區塊 start -->
		<tr>
			<td width="200px" align="right">給付項目(身故)-itema：</td>
			<td width="200px" align="left">
				<s:textfield key="%{'dtlDevResults['+#st.index+'].itema'}" id="%{'dtlDevResults['+#st.index+'].itema'}" maxLength="10" size="10"/>
			</td>
			<td width="200px" align="right">給付項目(完全失能或最高級失能)-itemb：</td>
			<td width="200px" align="left">
				<s:textfield key="%{'dtlDevResults['+#st.index+'].itemb'}" id="%{'dtlDevResults['+#st.index+'].itemb'}" maxLength="10" size="10"/>
			</td>
		</tr>
		<tr>
			<td width="200px" align="right">給付項目(失能扶助金)-itemc：</td>
			<td width="200px" align="left">
				<s:textfield key="%{'dtlDevResults['+#st.index+'].itemc'}" id="%{'dtlDevResults['+#st.index+'].itemc'}" maxLength="10" size="10"/>
			</td>
			<td width="200px" align="right">給付項目(特定事故保險金)-itemd：</td>
			<td width="200px" align="left">
				<s:textfield key="%{'dtlDevResults['+#st.index+'].itemd'}" id="%{'dtlDevResults['+#st.index+'].itemd'}" maxLength="10" size="10"/>
			</td>
		</tr>
		<tr>
			<td width="200px" align="right">給付項目(初次罹患)-iteme：</td>
			<td width="200px" align="left">
				<s:textfield key="%{'dtlDevResults['+#st.index+'].iteme'}" id="%{'dtlDevResults['+#st.index+'].iteme'}" maxLength="10" size="10"/>
			</td>
			<td width="200px" align="right">給付項目(醫療限額)-itemf：</td>
			<td width="200px" align="left">
				<s:textfield key="%{'dtlDevResults['+#st.index+'].itemf'}" id="%{'dtlDevResults['+#st.index+'].itemf'}" maxLength="10" size="10"/>
			</td>
		</tr>
		<tr>
			<td width="200px" align="right">給付項目(醫療限額自負額)-itemg：</td>
			<td width="200px" align="left">
				<s:textfield key="%{'dtlDevResults['+#st.index+'].itemg'}" id="%{'dtlDevResults['+#st.index+'].itemg'}" maxLength="10" size="10"/>
			</td>
			<td width="200px" align="right">給付項目(日額)-itemh：</td>
			<td width="200px" align="left">
				<s:textfield key="%{'dtlDevResults['+#st.index+'].itemh'}" id="%{'dtlDevResults['+#st.index+'].itemh'}" maxLength="10" size="10"/>
			</td>
		</tr>
		<tr>
			<td width="200px" align="right">給付項目(住院手術)-itemi：</td>
			<td width="200px" align="left">
				<s:textfield key="%{'dtlDevResults['+#st.index+'].itemi'}" id="%{'dtlDevResults['+#st.index+'].itemi'}" maxLength="10" size="10"/>
			</td>
			<td width="200px" align="right">給付項目(門診手術)-itemj：</td>
			<td width="200px" align="left">
				<s:textfield key="%{'dtlDevResults['+#st.index+'].itemj'}" id="%{'dtlDevResults['+#st.index+'].itemj'}" maxLength="10" size="10"/>
			</td>
		</tr>
		<tr>
			<td width="200px" align="right">給付項目(門診)-itemk：</td>
			<td width="200px" align="left">
				<s:textfield key="%{'dtlDevResults['+#st.index+'].itemk'}" id="%{'dtlDevResults['+#st.index+'].itemk'}" maxLength="10" size="10"/>
			</td>
			<td width="200px" align="right">給付項目(重大疾病(含特定傷病))-iteml：</td>
			<td width="200px" align="left">
				<s:textfield key="%{'dtlDevResults['+#st.index+'].iteml'}" id="%{'dtlDevResults['+#st.index+'].iteml'}" maxLength="10" size="10"/>
			</td>
		</tr>
		<tr>
			<td width="200px" align="right">給付項目(重大燒燙傷)-itemm：</td>
			<td width="200px" align="left">
				<s:textfield key="%{'dtlDevResults['+#st.index+'].itemm'}" id="%{'dtlDevResults['+#st.index+'].itemm'}" maxLength="10" size="10"/>
			</td>
			<td width="200px" align="right">給付項目(癌症療養)-itemn：</td>
			<td width="200px" align="left">
				<s:textfield key="%{'dtlDevResults['+#st.index+'].itemn'}" id="%{'dtlDevResults['+#st.index+'].itemn'}" maxLength="10" size="10"/>
			</td>
		</tr>
		<tr>
			<td width="200px" align="right">給付項目(出院療養)-itemo：</td>
			<td width="200px" align="left">
				<s:textfield key="%{'dtlDevResults['+#st.index+'].itemo'}" id="%{'dtlDevResults['+#st.index+'].itemo'}" maxLength="10" size="10"/>
			</td>
			<td width="200px" align="right">給付項目(喪失工作能力)-itemp：</td>
			<td width="200px" align="left">
				<s:textfield key="%{'dtlDevResults['+#st.index+'].itemp'}" id="%{'dtlDevResults['+#st.index+'].itemp'}" maxLength="10" size="10"/>
			</td>
		</tr>
		<tr>
			<td width="200px" align="right">給付項目(喪葬費用)-itemq：</td>
			<td width="200px" align="left">
				<s:textfield key="%{'dtlDevResults['+#st.index+'].itemq'}" id="%{'dtlDevResults['+#st.index+'].itemq'}" maxLength="10" size="10"/>
			</td>
			<td width="200px" align="right">給付項目(銜接原醫療限額之自負額)-itemr：</td>
			<td width="200px" align="left">
				<s:textfield key="%{'dtlDevResults['+#st.index+'].itemr'}" id="%{'dtlDevResults['+#st.index+'].itemr'}" maxLength="10" size="10"/>
			</td>
		</tr>
		<tr>
			<td width="200px" align="right">給付項目(分期給付)-items：</td>
			<td width="200px" align="left">
				<s:textfield key="%{'dtlDevResults['+#st.index+'].items'}" id="%{'dtlDevResults['+#st.index+'].items'}" maxLength="10" size="10"/>
			</td>
		</tr>
		</table>
		<!-- 給付項目區塊 end -->
		<br>
		</s:iterator>
	</s:if>
	<br/>
	<table width="970px">
		<tr>
			<td align="center">
				<input value="重送" type="button" onclick="javascript:form_submit('resend');">
	    	</td>
		</tr>
	</table>
</s:form>
</body>
</html>