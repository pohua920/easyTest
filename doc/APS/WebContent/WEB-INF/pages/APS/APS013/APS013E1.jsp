<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
	String path = request.getContextPath();
	String title = "傷害險通報查詢作業";
	String image = path + "/" + "images/";
	String GAMID = "APS013E1";
	String mDescription = "傷害險通報查詢作業";
	String nameSpace = "/aps/013";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- 
mantis：OTH0093，處理人員：BJ085，需求單編號：OTH0093 傷害險通報查詢、重送介面  start
-->
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
		if("resend" == type && confirm("確認要重送此筆資料?")==true && checkData() && compareDate() && checkNum()){
			$('#cmptype').removeAttr('disabled');
			$("#mainForm").attr("action","btnResend.action");
			$("#mainForm").submit();
		}
	}
	
	function ajaxAction(action,oid,announceCase){
		//取出ajax xml相對路徑
		var contextPath = '<%=request.getScheme()%>' + '://<%=request.getServerName()+":"+request.getServerPort()+request.getContextPath()%>';
		var path = ''; 

		if(action == 'findCwpAnnounceData'){
	    	path = contextPath + '/aps/ajax004/findCwpAnnounceData.action?oid='+oid+'&announceCase='+announceCase;
		}
		//執行ajax
		$.ajax({
			url : path,
			type: 'POST',
			data: {	},
			dataType: 'json',
			timeout: 10000,
			async: false,
			cache: false,
			error: function (data, status, e){
				ajaxError(data, status);
			},
			success: function (data, status){
				ajaxSuccess(action, data);
			}
		});
	}
	//ajax成功時會回來的method
		function ajaxSuccess(action, data){
		//ajax回傳的data存在，將ajax回傳的data設定至頁面上
		if(data.cwpAnnounceData != null){
			$('#moreDtlDiv').css('display','block');
			$('#askname').val(data.cwpAnnounceData.askname);//要保人姓名
			$('#askidno').val(data.cwpAnnounceData.askidno);//要保人身分證
			$('#askbirdate').val(data.cwpAnnounceData.askbirdate);//要保人生日
			$('#asktype').val(data.cwpAnnounceData.asktype);//"要保人與被保險人關係
			$('#name').val(data.cwpAnnounceData.name);//被保人姓名
			$('#idno').val(data.cwpAnnounceData.idno);//被保人身分證
			$('#birdate').val(data.cwpAnnounceData.birdate);//被保人生日
			$('#sex').val(data.cwpAnnounceData.sex);//被保人性別
			$('#filldate').val(data.cwpAnnounceData.filldate);//要保書填寫日期/要保日期
			$('#broktype').val(data.cwpAnnounceData.broktype);//保經代類別
			$('#dcreate').val(data.cwpAnnounceData.dcreate);//建立時間
			$('#sendtime').text(data.cwpAnnounceData.sendtime);//傳送時間
			$('#itema').val(data.cwpAnnounceData.itema);//給付項目(身故)
			$('#itemb').val(data.cwpAnnounceData.itemb);//給付項目(完全失能或高級失能)
			$('#itemc').val(data.cwpAnnounceData.itemc);//給付項目(失能扶助金)
			$('#itemd').val(data.cwpAnnounceData.itemd);//給付項目(特定事故保險金)
			$('#iteme').val(data.cwpAnnounceData.iteme);//給付項目(初次罹患)
			$('#itemf').val(data.cwpAnnounceData.itemf);//給付項目(醫療限額)
			$('#itemg').val(data.cwpAnnounceData.itemg);//給付項目(醫療限額自負額)
			$('#itemh').val(data.cwpAnnounceData.itemh);//給付項目(日額)
			$('#itemi').val(data.cwpAnnounceData.itemi);//給付項目(住院手術)
			$('#itemj').val(data.cwpAnnounceData.itemj);//給付項目(門診手術)
			$('#itemk').val(data.cwpAnnounceData.itemk);//給付項目(門診)
			$('#iteml').val(data.cwpAnnounceData.iteml);//給付項目(重大疾病(含特定傷病))
			$('#itemm').val(data.cwpAnnounceData.itemm);//給付項目(重大燒燙傷)
			$('#itemn').val(data.cwpAnnounceData.itemn);//給付項目(癌症療養)
			$('#itemo').val(data.cwpAnnounceData.itemo);//給付項目(出院療養)
			$('#itemp').val(data.cwpAnnounceData.itemp);//給付項目(喪失工作能力)
			$('#itemq').val(data.cwpAnnounceData.itemq);//給付項目(喪葬費用)
			$('#itemr').val(data.cwpAnnounceData.itemr);//給付項目(銜接原醫療限額之自負額)
			$('#items').val(data.cwpAnnounceData.items);//給付項目(分期給付)
			$('#valdate').val(data.cwpAnnounceData.valdate);//契約生效日期
			$('#valtime').val(data.cwpAnnounceData.valtime);//契約生效日期時分
			$('#ovrdate').val(data.cwpAnnounceData.ovrdate);//契約滿期日期
			$('#ovrtime').val(data.cwpAnnounceData.ovrtime);//契約滿期日期時分
			$('#prm').val(data.cwpAnnounceData.prm);//保費
			$('#bamttype').val(data.cwpAnnounceData.bamttype);//保費繳別
			$('#prmyears').val(data.cwpAnnounceData.prmyears);//保費繳費年期
			$('#con').val(data.cwpAnnounceData.con);//保單狀況
			$('#condate').val(data.cwpAnnounceData.condate);//保單狀況生效日期
			$('#contime').val(data.cwpAnnounceData.contime);//保單狀況生效日期時分
			$('#sourceRemark').val(data.cwpAnnounceData.sourceRemark);//資料來源
			$('#rtncode').text(data.cwpAnnounceData.rtncode);//回應碼
			$('#rtnmsg').text(data.cwpAnnounceData.rtnmsg);//回應訊息
			$('#checkno').text(data.cwpAnnounceData.checkno);//批次號
			$('#keyidno').val(data.cwpAnnounceData.keyidno);//被保險人身份證字號
			$('#dataserno').val(data.cwpAnnounceData.dataserno);//資料序號，被保險人身分證字號為key值分組，從1開始
			$('#cmptype').val(data.cwpAnnounceData.cmptype);//產壽險別
			$('#cmpno').val(data.cwpAnnounceData.cmpno);//公司代號
			$('#insnom').val(data.cwpAnnounceData.insnom);//主約保單號碼
			$('#insno').val(data.cwpAnnounceData.insno);//保單號碼
			$('#origin').val(data.cwpAnnounceData.origin);//來源別
			$('#channel').val(data.cwpAnnounceData.channel);//銷售通路別
			$('#prdcode').val(data.cwpAnnounceData.prdcode);//商品代碼
			$('#insclass').val(data.cwpAnnounceData.insclass);//保單分類
			$('#inskind').val(data.cwpAnnounceData.inskind);//險種分類
			$('#insitem').val(data.cwpAnnounceData.insitem);//險種
			$('#paytype').val(data.cwpAnnounceData.paytype);//公、自費件
			$('#resendSourceOid').val(data.cwpAnnounceData.oid);//原始oid
		}else{//ajax回傳的data不存在
			alert("無回傳資料!!!");
		}
	}

	//ajax發生錯誤時會呼叫的method
	function ajaxError(data, status){
		alert("無資料");
	}
	
	function checkData(){
		var regexDate = /^0([0-9]\d{2}(0+[1-9]|1[012])(0+[1-9]|[12][0-9]|3[01]))*$/;
		var dateArray = ['condate','askbirdate','birdate','filldate','valdate','ovrdate'];
		var dateNameArray = ['保單狀況生效日','要保人出生日期','被保險人出生日期','要保書填寫日期/要保日期','契約生效日期','契約滿期日期'];
		var dateMsg = "";
		for(var i=0; i<dateArray.length; i++){
			if($("[id='"+dateArray[i]+"']").val()!="" && !regexDate.test($("[id='"+dateArray[i]+"']").val())){
				if(dateMsg == ""){
					dateMsg += dateNameArray[i];
				}else{
					dateMsg += "、"+dateNameArray[i];	
				}
			}
		}
		var regexTime = /^(20|21|22|23|[0-1]\d)([0-5]\d)$/;
		var timeArray = ['contime','valtime','ovrtime'];
		var timeNameArray = ['保單狀況生效日四碼時分','契約生效日期四碼時分','契約滿期日期四碼時分'];
		var timeMsg = "";
		for(var i=0; i<timeArray.length; i++){
			if($("[id='"+timeArray[i]+"']").val()!="" && !regexTime.test($("[id='"+timeArray[i]+"']").val())){
				if(timeMsg != "" || dateMsg != ""){
					timeMsg += "、" + timeNameArray[i];
				}else{
					timeMsg += timeNameArray[i];	
				}
			}
		}
		if($('#resendReason').val() == ""){
			alert("請輸入重送原因!")
			return false;
		}
		if(dateMsg != "" || timeMsg != ""){
			alert(dateMsg + timeMsg + "不符合日期時間格式!");
			return false;
		}
		return true;
	}
	
	function compareDate(){
		var ovrdate = $('#ovrdate').val();
		var valdate = $('#valdate').val();
		var askbirdate = $('#askbirdate').val();
		var birdate = $('#birdate').val();
		var filldate = $('#filldate').val();
		if(ovrdate!="" && valdate!="" && valdate > ovrdate){
			alert("契約生效日不得大於契約滿期日!");
			return false;
		}
		if(filldate!="" && valdate!="" && filldate > valdate){
			alert("要保書填寫日期/要保日期不得大於契約生效日、契約滿期日!");
			return false;
		}
		if(birdate!="" && filldate!="" && birdate > filldate){
			alert("被保險人生日不得大於契約生效日、契約滿期日、要保書填寫日期/要保日期!");
			return false;
		}
		if(askbirdate!="" && filldate!="" && askbirdate > filldate){
			alert("要保險人生日不得大於契約生效日、契約滿期日、要保書填寫日期/要保日期!");
			return false;
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
		
		for(var i=0; i<numArray.length; i++){
			if($("[id='"+numArray[i]+"']").val()!="" && !regex.test($("[id='"+numArray[i]+"']").val())){
				alert("給付項目輸入必須為數字!");
				return false;
			}
		}
		if($('#prmyears').val()!="" && !regex.test($('#prmyears').val())){
			alert("繳費年期輸入必須為數字!");
			return false;
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
				<span id="lbSearch"><b>查詢作業</b></span></td>
			<td colspan="3" class="image"></td>
		</tr>
	</table>

<!--MainGrid Table-->
<table border="1" id="gridtable" width="970px" border="0" class="main_table">
	<tr align="center">
		<th>被保人身分證字號</th>
		<th>被保人姓名</th> 
		<th>保單號碼</th>
		<th>傳送時間</th>
		<th>批次號</th>
	</tr>
	<tr onmouseover="GridOver(this)" onmouseout="GridOut(this)" bgcolor="#EFEFEF">
		<td align="center"><!-- 被保人身分證字號 -->
			<s:property value="cwpAnnounceVo.idno"/>
		</td>
		<td align="center"><!-- 被保人姓名 -->
			<s:property value="cwpAnnounceVo.name"/>
		</td>
		<td align="center"><!-- 保單號碼 -->
			<s:property value="cwpAnnounceVo.insno"/>
		</td>
		<td align="center"><!-- 傳送時間 -->
			<fmt:formatDate value="${cwpAnnounceVo.sendtime}" pattern="yyyy/MM/dd HH:mm:ss"/>
		</td>
		<td align="center"><!-- 批次號 -->
			<s:property value="cwpAnnounceVo.checkno"/>
		</td>
	</tr>
</table>
<!--DetailGrid Table-->
<s:if test="dtlDevResults != null && 0 != dtlDevResults.size">
<table cellspacing="1" cellpadding="1" border="0" width="970px">
	<tr>
		<td>
		<div align="right">
			<custom:ddlChangePage 
			    formId="mainForm"
				name="dPageInfo" 
				nameSpace="/aps/013"
				currentPage="${dPageInfo.currentPage}" 
				pageSize="${dPageInfo.pageSize}"   
				selectOnChange="ddlDtlPageSizeChanged" 
				textOnChange="txtDtlChangePageIndex"  
				rowCount="${dPageInfo.rowCount}"
				pageCount="${dPageInfo.pageCount}"/>
		</div>
		</td>
	</tr>
</table>
<table border="1" id="gridtable" width="970px" border="0" class="main_table">
	<tr align="center">
		<th>序號</th>
		<th>要保險人姓名</th> 
		<th>要保人身份證字號</th>
		<th>來源別</th>
		<th>銷售通路別</th>
		<th>商品代碼</th>
		<th>保單分類</th>
		<th>險種分類</th>
		<th>險種</th>
		<th>公、自費件</th>
		<th>回應碼</th>
		<th>重送時間</th>
		<th></th>
		<th>資料重送</th>
	</tr>
	<s:iterator var="row" value="dtlDevResults">
	<tr onmouseover="GridOver(this)" onmouseout="GridOut(this)" bgcolor="#EFEFEF">
		<td align="center"><!-- 序號 -->
			<s:property value="dataserno"/>
		</td>
		<td align="center"><!-- 要保險人姓名  -->
			<s:property value="askname"/>
		</td>
		<td align="center"><!-- 要保險人身份證字號 -->
			<s:property value="askidno"/>
		</td>
		<td align="center"><!-- 來源別 -->
			<c:if test="${origin == '0'}">無</c:if>
			<c:if test="${origin == '1'}">OIU保單</c:if>
		</td>
		<td align="center"><!-- 銷售通路別 -->
			<c:if test="${channel == '1'}">網路投保</c:if>
			<c:if test="${channel == '2'}">業務員</c:if>
			<c:if test="${channel == '3'}">保經、保代</c:if>
			<c:if test="${channel == '4'}">電話行銷</c:if>
			<c:if test="${channel == '5'}">機場櫃檯</c:if>
		</td>
		<td align="center"><!-- 商品代碼  -->
			<s:property value="prdcode"/>
		</td>
		<td align="center"><!-- 保單分類  -->
			<c:if test="${insclass == '1' }">個人</c:if>
			<c:if test="${insclass == '2' }">團體</c:if>
		</td>
		<td align="center"><!-- 險種分類  -->
			<c:if test="${inskind == '1' }">人壽保險</c:if>
			<c:if test="${inskind == '2' }">傷害保險</c:if>
			<c:if test="${inskind == '3' }">健康保險</c:if>
			<c:if test="${inskind == '4' }">年金保險</c:if>
		</td>
		<td align="center"><!-- 險種  -->
			<c:if test="${insitem == '01' }">一般</c:if>
			<c:if test="${insitem == '02' }">特定</c:if>
			<c:if test="${insitem == '03' }">投資型</c:if>
			<c:if test="${insitem == '04' }">日額型</c:if>
			<c:if test="${insitem == '05' }">實支實付型</c:if>
			<c:if test="${insitem == '06' }">日額或實支實付擇一</c:if>
			<c:if test="${insitem == '07' }">手術型</c:if>
			<c:if test="${insitem == '08' }">重大疾病</c:if>
			<c:if test="${insitem == '09' }">帳戶型</c:if>
			<c:if test="${insitem == '10' }">長期看護型</c:if>
			<c:if test="${insitem == '11' }">喪失工作能力</c:if>
			<c:if test="${insitem == '12' }">防癌</c:if>
			<c:if test="${insitem == '13' }">旅行平安</c:if>
			<c:if test="${insitem == '14' }">微型</c:if>
			<c:if test="${insitem == '15' }">微型實支實付型</c:if>
			<c:if test="${insitem == '16' }">小額終老保險</c:if>
			<c:if test="${insitem == '17' }">失能扶助保險</c:if>
			<c:if test="${insitem == '18' }">登山綜合保險</c:if>
			<c:if test="${insitem == '19' }">定期壽險</c:if>
			<c:if test="${insitem == '99' }">意外身故</c:if>
			<!-- 2024/08/02 傷害險通報新增21險種 -->
			<c:if test="${insitem == '21' }">一年期</c:if>
		</td>
		<td align="center"><!-- 公、自費件  -->
			<c:if test="${paytype == '0' }">無</c:if>
			<c:if test="${paytype == '1' }">公費</c:if>
			<c:if test="${paytype == '2' }">自費</c:if>
		</td>
		<td align="center"><!-- 回應碼  -->
			<s:property value="rtncode"/>
		</td>
		<td align="center"><!-- 重送時間  -->
			<fmt:formatDate value="${resendModifytime}" pattern="yyyy/MM/dd HH:mm:ss"/>
		</td>
		<td align="center">
			<a href="" onclick="ajaxAction('findCwpAnnounceData','${row.oid}','${announceCase}');resentBlock('detail');return false">給付項目明細</a>
		</td>
		<td align="center">
			<c:if test="${resendModifytime == null && rtncode != '00'}">
				<a href="" onclick="ajaxAction('findCwpAnnounceData','${row.oid}','${announceCase}');resentBlock('update');return false">修改</a>
			</c:if>
		</td>
	</tr>
	<s:hidden key="cwpAnnounceVo.oid" id="oid"/>
	<s:hidden key="announceCase" id="announceCase"/>
	</s:iterator>
</table>
</s:if>

<!-- form 開始 -->
<s:form theme="simple" namespace="/aps/013" id="mainForm" name="mainForm">
<div id="moreDtlDiv" style="display:none">
<br/>
要被保人、所有給付項目明細資料
<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
	<tr>
		<td width="150px" align="right">資料序號：</td>
		<td width="200px" align="left">
			<s:textfield key="cwpAnnounceVo.dataserno" id="dataserno"/>
		</td>
		<td width="150px" align="right">批次號：</td>
		<td width="200px" align="left">
			<span id="checkno"></span>
		</td>
	</tr>
	<tr>
		<td width="150px" align="right">主約保單號碼：</td>
		<td width="200px" align="left">
			<s:textfield key="cwpAnnounceVo.insnom" id="insnom" maxLength="20" />
		</td>	
		<td width="150px" align="right">保單號碼：</td>
		<td width="200px" align="left">
			<s:textfield key="cwpAnnounceVo.insno" id="insno" maxLength="20"/>
		</td>
	</tr>
	<tr>
		<td width="150px" align="right">公司代號：</td>
		<td width="200px" align="left">
			<s:textfield key="cwpAnnounceVo.cmpno" id="cmpno" />
		</td>
		<td width="150px" align="right">產壽險別：</td>
		<td width="200px" align="left">
			<s:select list="cmptypeMap"	key="cwpAnnounceVo.cmptype" id="cmptype" /> 
		</td>
	</tr>
	<tr>
		<td width="150px" align="right">要保人姓名：</td>
		<td width="200px" align="left">
			<s:textfield key="cwpAnnounceVo.askname" id="askname" maxLength="10"/>
		</td>
		<td width="150px" align="right">要保人身分證號碼：</td>
		<td width="200px" align="left">
			<s:textfield key="cwpAnnounceVo.askidno" id="askidno" maxLength="20" size="20"/>
		</td>
	</tr>
	<tr>
		<td width="150px" align="right">要保人出生日期：</td>
		<td width="200px" align="left">
			<s:textfield key="cwpAnnounceVo.askbirdate" id="askbirdate" maxLength="8" size="8" />
		</td>
		<td width="150px" align="right">要保人與被保險人關係：</td>
		<td width="200px" align="left">
			<s:select list="asktypeMap"	key="cwpAnnounceVo.asktype" id="asktype" />
		</td>
	</tr>
	<tr>
		<td width="150px" align="right">被保險人姓名：</td>
		<td width="200px" align="left">
			<s:textfield key="cwpAnnounceVo.name" id="name" maxLength="10" size="10"/>
		</td>
		<td width="150px" align="right">被保險人身分證字號：</td>
		<td width="200px" align="left">
			<s:textfield key="cwpAnnounceVo.idno" id="idno" />
		</td>
	</tr>
	<tr>
		<td width="150px" align="right">被保險人身份證字號：</td>
		<td width="200px" align="left">
			<s:textfield key="cwpAnnounceVo.keyidno" id="keyidno" />
		</td>
	</tr>
	<tr>
		<td width="150px" align="right">被保人性別：</td>
		<td width="200px" align="left">
			<s:select list="sexMap" key="cwpAnnounceVo.sex" id="sex" />
		</td>
		<td width="150px" align="right">被保人出生日期：</td>
		<td width="200px" align="left">
			<s:textfield key="cwpAnnounceVo.birdate" id="birdate" maxLength="8" size="8"/>
		</td>
	</tr>
	<tr>
		<td width="150px" align="right">要保書填寫日期/要保日期：</td>
		<td width="200px" align="left">
			<s:textfield key="cwpAnnounceVo.filldate" id="filldate" maxLength="8" size="8" />
		</td>
		<td width="150px" align="right">保經代類別：</td>
		<td width="200px" align="left">
			<s:select list="broktypeMap" key="cwpAnnounceVo.broktype" id="broktype" />
		</td>
	</tr>
	<tr>
		<td width="150px" align="right">建立時間(由各中介轉進時間)：</td>
		<td width="200px" align="left">
			<s:textfield key="cwpAnnounceVo.dcreate" id="dcreate" />
		</td>
		<td width="150px" align="right">傳送時間(送公會時間)：</td>
		<td width="200px" align="left">
			<span id="sendtime"></span>
		</td>
	</tr>
	<tr>	
		<td width="150px" align="right">契約生效日期：</td>
		<td width="200px" align="left">
			<s:textfield key="cwpAnnounceVo.valdate" id="valdate" maxLength="8" size="8"/>
			四碼時分：<s:textfield key="cwpAnnounceVo.valtime" id="valtime" maxLength="4" size="4"/>
		</td>
		<td width="150px" align="right">契約滿期日期：</td>
		<td width="200px" align="left">
			<s:textfield key="cwpAnnounceVo.ovrdate" id="ovrdate" maxLength="8" size="8"/>
			四碼時分：<s:textfield key="cwpAnnounceVo.ovrtime" id="ovrtime" maxLength="4" size="4"/>
		</td>
	</tr>
	<tr>
		<td width="150px" align="right">保費：</td>
		<td width="200px" align="left">
			<s:textfield key="cwpAnnounceVo.prm" id="prm" maxLength="10" size="10"/>
		</td>
		<td width="150px" align="right">保費繳別：</td>
		<td width="200px" align="left">
			<s:select list="bamttypeMap" key="cwpAnnounceVo.bamttype" id="bamttype" />
		</td>
	</tr>
	<tr>
		<td width="150px" align="right">保費繳費年期：</td>
		<td width="200px" align="left">
			<s:textfield key="cwpAnnounceVo.prmyears" id="prmyears" maxLength="3" size="3"/>
		</td>
		<td width="150px" align="right">保單狀況生效日期：</td>
		<td width="200px" align="left">
			<s:textfield key="cwpAnnounceVo.condate" id="condate" maxLength="8" size="8"/>
			四碼時分：<s:textfield key="cwpAnnounceVo.contime" id="contime" maxLength="4" size="4"/>
		</td>
	</tr>
	<tr>
		<td width="150px" align="right">資料來源：</td>
		<td width="200px" align="left">
			<s:textfield key="cwpAnnounceVo.sourceRemark" id="sourceRemark" />
		</td>
		<td width="150px" align="right">來源別：</td>
		<td width="200px" align="left">
			<s:select list="originMap"	key="cwpAnnounceVo.origin" id="origin" />
		</td>
	</tr>	
	<tr>
		<td width="150px" align="right">銷售通路別：</td>
		<td width="200px" align="left">
			<s:select list="channelMap"	key="cwpAnnounceVo.channel" id="channel" />
		</td>
		<td width="150px" align="right">商品代碼：</td>
		<td width="200px" align="left">
			<s:textfield key="cwpAnnounceVo.prdcode" id="prdcode" maxLength="20" size="20"/>
		</td>
	</tr>
	<tr>
		<td width="150px" align="right">保單分類：</td>
		<td width="200px" align="left">
			<s:select list="insclassMap" key="cwpAnnounceVo.insclass" id="insclass" />
		</td>
		<td width="150px" align="right">險種分類：</td>
		<td width="200px" align="left">
			<s:select list="inskindMap" key="cwpAnnounceVo.inskind"	id="inskind" />
		</td>
	</tr>
	<tr>
		<td width="150px" align="right">險種：</td>
		<td width="200px" align="left">
			<s:select list="insitemMap" key="cwpAnnounceVo.insitem" id="insitem" />
		</td>
		<td width="150px" align="right">公、自費件：</td>
		<td width="200px" align="left">
			<s:select list="paytypeMap" key="cwpAnnounceVo.paytype" id="paytype" />
		</td>
	</tr>
	<!-- 給付項目區塊 start -->
	<tr>
		<td width="150px" align="right">給付項目(身故)：</td>
		<td width="200px" align="left">
			<s:textfield key="cwpAnnounceVo.itema" id="itema" maxLength="10" size="10"/>
		</td>
		<td width="150px" align="right">給付項目(完全失能或最高級失能)：</td>
		<td width="200px" align="left">
			<s:textfield key="cwpAnnounceVo.itemb" id="itemb" maxLength="10" size="10"/>
		</td>
	</tr>
	<tr>
		<td width="150px" align="right">給付項目(失能扶助金)：</td>
		<td width="200px" align="left">
			<s:textfield key="cwpAnnounceVo.itemc" id="itemc" maxLength="10" size="10"/>
		</td>
		<td width="150px" align="right">給付項目(特定事故保險金)：</td>
		<td width="200px" align="left">
			<s:textfield key="cwpAnnounceVo.itemd" id="itemd" maxLength="10" size="10"/>
		</td>
	</tr>
	<tr>
		<td width="150px" align="right">給付項目(初次罹患)：</td>
		<td width="200px" align="left">
			<s:textfield key="cwpAnnounceVo.iteme" id="iteme" maxLength="10" size="10"/>
		</td>
		<td width="150px" align="right">給付項目(醫療限額)：</td>
		<td width="200px" align="left">
			<s:textfield key="cwpAnnounceVo.itemf" id="itemf" maxLength="10" size="10"/>
		</td>
	</tr>
	<tr>
		<td width="150px" align="right">給付項目(醫療限額自負額)：</td>
		<td width="200px" align="left">
			<s:textfield key="cwpAnnounceVo.itemg" id="itemg" maxLength="10" size="10"/>
		</td>
		<td width="150px" align="right">給付項目(日額)：</td>
		<td width="200px" align="left">
			<s:textfield key="cwpAnnounceVo.itemh" id="itemh" maxLength="10" size="10"/>
		</td>
	</tr>
	<tr>
		<td width="150px" align="right">給付項目(住院手術)：</td>
		<td width="200px" align="left">
			<s:textfield key="cwpAnnounceVo.itemi" id="itemi" maxLength="10" size="10"/>
		</td>
		<td width="150px" align="right">給付項目(門診手術)：</td>
		<td width="200px" align="left">
			<s:textfield key="cwpAnnounceVo.itemj" id="itemj" maxLength="10" size="10"/>
		</td>
	</tr>
	<tr>
		<td width="150px" align="right">給付項目(門診)：</td>
		<td width="200px" align="left">
			<s:textfield key="cwpAnnounceVo.itemk" id="itemk" maxLength="10" size="10"/>
		</td>
		<td width="150px" align="right">給付項目(重大疾病(含特定傷病))：</td>
		<td width="200px" align="left">
			<s:textfield key="cwpAnnounceVo.iteml" id="iteml" maxLength="10" size="10"/>
		</td>
	</tr>
	<tr>
		<td width="150px" align="right">給付項目(重大燒燙傷)：</td>
		<td width="200px" align="left">
			<s:textfield key="cwpAnnounceVo.itemm" id="itemm" maxLength="10" size="10"/>
		</td>
		<td width="150px" align="right">給付項目(癌症療養)：</td>
		<td width="200px" align="left">
			<s:textfield key="cwpAnnounceVo.itemn" id="itemn" maxLength="10" size="10"/>
		</td>
	</tr>
	<tr>
		<td width="150px" align="right">給付項目(出院療養)：</td>
		<td width="200px" align="left">
			<s:textfield key="cwpAnnounceVo.itemo" id="itemo" maxLength="10" size="10"/>
		</td>
		<td width="150px" align="right">給付項目(喪失工作能力)：</td>
		<td width="200px" align="left">
			<s:textfield key="cwpAnnounceVo.itemp" id="itemp" maxLength="10" size="10"/>
		</td>
	</tr>
	<tr>
		<td width="150px" align="right">給付項目(喪葬費用)：</td>
		<td width="200px" align="left">
			<s:textfield key="cwpAnnounceVo.itemq" id="itemq" maxLength="10" size="10"/>
		</td>
		<td width="150px" align="right">給付項目(銜接原醫療限額之自負額)：</td>
		<td width="200px" align="left">
			<s:textfield key="cwpAnnounceVo.itemr" id="itemr" maxLength="10" size="10"/>
		</td>
	</tr>
	<tr>
		<td width="150px" align="right">給付項目(分期給付)：</td>
		<td width="200px" align="left">
			<s:textfield key="cwpAnnounceVo.items" id="items" maxLength="10" size="10"/>
		</td>
	</tr>
	<!-- 給付項目區塊 end -->
</table>
<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px">
	<tr>
		<td width="135px" align="right">保單狀況：</td>
		<td width="300px" align="left">
			<s:select key="cwpAnnounceVo.con" id="con" list="conMap"/>
		</td>
	</tr>	
	<tr> 
		<td width="130px" align="right">回應碼：</td>
		<td width="300px" align="left">
			<span id="rtncode"></span>
		</td>
	</tr>
	<tr>
 		<td width="130px" align="right">回應訊息：</td>
		<td width="300px" align="left">
			<span id="rtnmsg"></span>
		</td>
	</tr>
</table>
<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px">
	<tr id="resendTr" style="display:none">
 		<td width="205px" align="right">重送原因：</td>
		<td width="300px" align="left">
			<s:textfield key="cwpAnnounceVo.resendReason" id="resendReason" maxLength="200" size="120"/>
		</td>
	</tr>
</table>
	<s:hidden id="resendSourceOid" key="cwpAnnounceVo.resendSourceOid"/>
<br/>
<table width="970px">
	<tr>
		<td align="center">
			<input value="重送" type="button" onclick="javascript:form_submit('resend');" id="resendButton" style="display:none">
	    </td>
	</tr>
</table>
</div>
</s:form>
</body>
</html>