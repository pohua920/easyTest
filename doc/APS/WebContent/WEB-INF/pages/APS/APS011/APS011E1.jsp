<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%@ page import="com.tlg.prpins.entity.MetaAmlResultDetail" %>
<%@ page import="java.util.*"%>
<%
	String path = request.getContextPath();
	String title = "AML洗錢手動登錄查詢作業-掃描結果明細";
	String image = path + "/" + "images/";
	String GAMID = "APS011E1";
	String mDescription = "AML洗錢手動登錄查詢作業-掃描結果明細";
	String nameSpace = "/aps/011";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- 
mantis：OTH0087，處理人員：BJ085，需求單編號：OTH0087 AML手動登錄
-->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<style type="text/css">
	.ui-dialog .ui-dialog-buttonpane { 
    text-align: center;
	}
	.ui-dialog .ui-dialog-buttonpane .ui-dialog-buttonset { 
	    float: none;
	}
</style>
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

		var pageCount = $("#pageCount").val();
		var currentPage = $("#currentPage").val();		
		var next = parseInt(currentPage) + 1;
		var pre = parseInt(currentPage) - 1;
		if(pre > 0){
			$("#pageCountDiv").append('<a id="pre" onClick="ajaxAction('+"'queryMetaAmlResultDetail'"+','+"'pre'"+')"><u>上一頁</u></a>');	
		}
		else{
			$("#pageCountDiv").append('上一頁');
		}
		if (next <= pageCount) {
			$("#pageCountDiv").append('<a id="next" onClick="ajaxAction('+"'queryMetaAmlResultDetail'"+','+"'next'"+')"><u>下一頁</u></a>');
		}
		else{
			$("#pageCountDiv").append('下一頁');
		}
	});

	//ajax-----------------------------------------------------------------------------------------------
	function ajaxAction(action,type){
		//取出ajax xml相對路徑
		var contextPath = '<%=request.getScheme()%>' + '://<%=request.getServerName()+":"+request.getServerPort()+request.getContextPath()%>';
		var path = ''; 
		if(action == 'queryMetaAmlResultDetail'){

			var oidResult = $("#oidResult").val();
			var currentPage = $("#currentPage").val();
			var pageSize = $("#pageSize").val();
			var startRow = $("#startRow").val();
			var endRow = $("#endRow").val();
			var pageCount = $("#pageCount").val();
			if(type == 'pre'){
				currentPage = parseInt(currentPage)-1;
				startRow = parseInt(startRow) - parseInt(pageSize);
				endRow = parseInt(endRow) - parseInt(pageSize);
			}else if((type == 'next')){
				currentPage = parseInt(currentPage)+1;
				startRow = parseInt(startRow) + parseInt(pageSize);
				endRow = parseInt(endRow) + parseInt(pageSize);
			}
			var next = parseInt(currentPage) + 1;
			var pre = parseInt(currentPage) - 1;
			$("#rowPage").text("第"+currentPage+"頁");
			$("#currentPage").val(currentPage);
			var next = parseInt(currentPage) + 1;
			var pre = parseInt(currentPage) - 1;
			$("#pageCountDiv").empty();
			if(pre > 0){
				$("#pageCountDiv").append('<a id="pre" onClick="ajaxAction('+"'queryMetaAmlResultDetail'"+','+"'pre'"+')"><u>上一頁</u></a>');	
			}else{
				$("#pageCountDiv").append('上一頁');
			}
			if (next <= pageCount) {
				$("#pageCountDiv").append('<a id="next" onClick="ajaxAction('+"'queryMetaAmlResultDetail'"+','+"'next'"+')"><u>下一頁</u></a>');
			}else{
				$("#pageCountDiv").append('下一頁');
			}
			
			path = contextPath + '/aps/ajax001/queryMetaAmlResultDetail.action';
		}
		//執行ajax
		$.ajax({
			url : path,
			type: 'POST',
			data: {oid:oidResult,currentPage:currentPage,pageSize:pageSize,startRow:startRow,endRow:endRow},
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
		if(data.isExist == 'true'){
			$(".guirTd").remove();
			if(action == 'queryMetaAmlResultDetail'){
				var list = data.metaAmlResultDetailList;
				for (var i=0; i<list.length; i++){
					$("#gridtable2:eq()").append(
					"<tr onmouseover='GridOver(this)' onmouseout='GridOut(this)' bgcolor='#EFEFEF'>"+
					"<td align='center' class='guirTd'>"+ list[i].screenSeq +"</td>"+
					"<td align='center' class='guirTd'>"+ showWlfScreenResult(list[i].wlfScreenResult) +"</td>"+
					"<td align='center' class='guirTd'>"+ showWlfScreenList(list[i].wlfScreenList) +"</td>"+
					"<td align='center' class='guirTd'>"+ list[i].crrScreenEntryid +"</td>"+
					"<td align='center' class='guirTd'>"+ showCrrCrrResult(list[i].crrCrrResult) +"</td>"+
					"<td align='center' class='guirTd'>"+ list[i].createtime +"</td>"+
					"</tr>"
					)
				}
			}
		}else{//ajax回傳的data不存在
			if(action == 'queryMetaAmlResultDetail'){
				$("#gridtable2:eq()").remove();
			}
		}
	}
	function showWlfScreenList (data){
		var wlfScreenList="";
		if(data.indexOf("1") != -1) wlfScreenList += "制裁名單(SAN);"
		if(data.indexOf("2") != -1) wlfScreenList += "其他官方名單(OOL);"
		if(data.indexOf("3") != -1) wlfScreenList += "其他禁令黑名單(OEL);"
		if(data.indexOf("4") != -1) wlfScreenList += "制裁所有權調查(SOR);"
		if(data.indexOf("5") != -1) wlfScreenList += "負面新聞對象-需特別關注的人士(SIP);"
		if(data.indexOf("6") != -1) wlfScreenList += "負面新聞對象-需特別關注的人士-次級犯罪閾值(SIP LT);"
		if(data.indexOf("7") != -1) wlfScreenList += "特殊利益對象-高國家風險(ECR);"
		if(data.indexOf("8") != -1) wlfScreenList += "政治公眾人物(PEP);"
		if(data.indexOf("9") != -1) wlfScreenList += "政治公眾人物親屬或具密切關係人員(RCA);"
		if(data.indexOf("10") != -1) wlfScreenList += "國有企業;"
		if(data.indexOf("20") != -1) wlfScreenList += "未分類黑名單;"
		if(data.indexOf("90") != -1) wlfScreenList += "自建名單;"
		return wlfScreenList;
	}

	function showWlfScreenResult (data){
		switch (data) {
			case "0":
			　return "無評級";
			case "1":
			　return "超低";
			case "2":
			　return "極低";
			case "3":
			　return "低";
			case "4":
			　return "微低";
			case "5":
			　return "中";
			case "6":
			　return "微高";
			case "7":
			　return "高";
			case "8":
			　return "極高";
			case "9":
			　return "超高";
			default:
			　return "";
		}
	}

	function showCrrCrrResult (data){
		switch (data) {
			case "0":
			　return "未命中";
			case "1":
			　return "疑似命中";
			case "2":
			　return "命中白名單";
			case "10":
			　return "已完成案件(確認未命中)";
			case "11":
			　return "已完成案件(確認命中)";
			default:
			　return "";
		}
	}

	//ajax發生錯誤時會呼叫的method
	function ajaxError(data, status){
		alert('無資料');
	}

	function showMsg(msg){
		alert(msg);
	}

	var msg = "<%=request.getAttribute("message")%>";
	if('' != msg && 'null' != msg){
		showMsg(msg);
	}

	$(function() {
		$("#dialog").dialog({
			autoOpen: false,
			height : 550, 
			width : 1000,
		});
			$("#opener").click(function() {
			$("#dialog").dialog("open");
			$("#autoOpen").val(true);
		});
	});
	
	function resendMask(type) {
		if(!confirm("是否確定"+type+"此筆資料?")){
			return false;
		}
		$.blockUI({
			border: 'none',
			padding: '15px',//padding：同時設定四個邊留白的捷徑屬性
			backgroundColor: '#000',//backgroundColor：訊息背景顏色
			color: '#fff',//color：訊息字樣顏色
			'-webkit-border-radius': '10px',//WebKit設定四邊的圓角為10px
			'-moz-border-radius': '10px',//Mozilla設定四邊的圓角為10px
			opacity: .5//opacity：指定表單和其控制項的透明度等級
		});
	};

</script>
</head>
<s:hidden key="filter.autoOpen" id="autoOpen" value="false"/>
<body style="margin:0;text-align:left">
<table cellspacing="1" cellpadding="1" width="970px" border="0">
	<tr>
		<td width="485px">
			<img src="${pageContext.request.contextPath}/images/Help_Icon.gif" border="0" width="49" height="26">
			<a href="${pageContext.request.contextPath}<%=nameSpace%>/lnkGoQuery.action"><img src="${pageContext.request.contextPath}/images/Search_Icon.gif" border="0" width="48" height="26"></a>
		</td>
		<td align="right" width="485px">PGMID：<%=GAMID%></td>
	</tr>
	<tr>
		<td align="center" colSpan="2"><h3><%=title%></h3></td>
	</tr>
</table>

<!-- form 開始 -->
<s:form theme="simple" namespace="/aps/011" id="mainForm" name="mainForm">
	<table id="table2"  cellspacing="0" cellpadding="0" width="970px">
		<tr>
			<td class="MainTdColor" style="width:200px" align="center">
				<span id="lbSearch"><b>查詢作業</b></span></td>
			<td colspan="3" class="image"></td>
		</tr>
	</table>
	<table border="1" id="gridtable" width="970px" border="0" class="main_table">
	<tr align="center">
		<th>險別</th>
		<th>險種代碼</th>
		<th>業務號</th>
		<th>強+任(強制險要保號)</th>
		<th>系統代號</th>
		<th>登入ID</th>
		<th>登入姓名</th>
		<th>作業類型</th>
		<th>公司代號</th>
		<th>業務來源</th>
		<th>商品風險等級</th>
		<th>保費</th>
		<th>建立時間</th>
	</tr>
	<tr>
		<td align="center"><!-- 險別 -->
			<s:property value="amlQueryObjMain.classCode"/>
			<c:if test="${amlQueryObjMain.classCode == 'A'}">任意車險</c:if>
			<c:if test="${amlQueryObjMain.classCode == 'B'}">強制車險</c:if>
			<c:if test="${amlQueryObjMain.classCode == 'C'}">責任險</c:if>
			<c:if test="${amlQueryObjMain.classCode == 'C1'}">傷害暨健康險</c:if>
			<c:if test="${amlQueryObjMain.classCode == 'E'}">工程險</c:if>
			<c:if test="${amlQueryObjMain.classCode == 'F'}">火險</c:if>			
			<c:if test="${amlQueryObjMain.classCode == 'M'}">水險</c:if>			
		</td>
		<td align="center"><!-- 險種代碼 -->
			<s:property value="amlQueryObjMain.riskCode"/>
		</td>
		<td align="center"><!-- 業務號 -->
			<s:property value="amlQueryObjMain.businessNo"/>
		</td>
		<td align="center"><!-- 強+任(強制險要保號) -->
			<s:property value="amlQueryObjMain.extraBusinessNo"/>
		</td>
		<td align="center"><!-- 系統代號 -->
			<s:property value="amlQueryObjMain.appCode"/>
		</td>
		<td align="center"><!-- 登入ID -->
			<s:property value="amlQueryObjMain.userId"/>
		</td>
		<td align="center"><!-- 登入姓名 -->
			<s:property value="amlQueryObjMain.userName"/>
		</td>
		<td align="center"><!-- 作業類型 -->
			<c:if test="${amlQueryObjMain.type == 'Q'}">報價</c:if>
			<c:if test="${amlQueryObjMain.type == 'T'}">要保</c:if>
			<c:if test="${amlQueryObjMain.type == 'E'}">批改</c:if>
			<c:if test="${amlQueryObjMain.type == 'C'}">理賠</c:if>
		</td>
		<td align="center"><!-- 公司代號 -->
			<s:property value="amlQueryObjMain.comCode"/>
		</td>
		<td align="center"><!-- 業務來源 -->
			<c:if test="${amlQueryObjMain.channelType == '10'}">業務員</c:if>
			<c:if test="${amlQueryObjMain.channelType == '20'}">保險經紀人</c:if>
			<c:if test="${amlQueryObjMain.channelType == '30'}">保險代理人</c:if>
			<c:if test="${amlQueryObjMain.channelType == '40'}">直接業務</c:if>			
		</td>
		<td align="center"><!-- 商品風險等級 -->
			<c:if test="${amlQueryObjMain.comLevel == 'H'}">高風險</c:if>
			<c:if test="${amlQueryObjMain.comLevel == 'M'}">中風險</c:if>
			<c:if test="${amlQueryObjMain.comLevel == 'L'}">低風險</c:if>		
		</td>
		
		<td align="center"><!-- 保費 -->
			<s:property value="amlQueryObjMain.prem"/>
		</td>
		<td align="center"><!-- 建立時間 -->
			<fmt:formatDate value='${amlQueryObjMain.createtime}' pattern='yyyy/MM/dd HH:mm:ss'/>
		</td>
	</tr>
</table>
<s:hidden key="amlQueryObjMain.oid" id="oid"/>
<br/>
<s:if test="queryDtlDevResults != null && 0 != queryDtlDevResults.size">
<table cellspacing="1" cellpadding="1" border="0" width="970px">
	<tr>
		<td>
		<div align="right">
			<custom:ddlChangePage 
			    formId="mainForm"
				name="ePageInfo" 
				nameSpace="/aps/011"
				currentPage="${ePageInfo.currentPage}" 
				pageSize="${ePageInfo.pageSize}"   
				selectOnChange="ddlDtlPageSizeChanged" 
				textOnChange="txtDtlChangePageIndex"  
				rowCount="${ePageInfo.rowCount}"
				pageCount="${ePageInfo.pageCount}"/>
		</div>
		</td>
	</tr>
</table>
<!--Grid Table-->
<table border="1" id="gridtable" width="970px" border="0" class="main_table">
	<tr align="center">
		<th>序號</th>
		<th>身分證字號/統編</th>
		<th>身分別</th>
		<th>身分類型</th>
		<th>狀態</th>
		<th>姓名</th>
		<th>英文姓名</th>
		<th>性別</th>
		<th>生日</th>
		<th>國籍代碼</th>
		<th>是否為高風險職業</th>
		<th>公司成立日期</th>
		<th>上市櫃公司</th>
		<th>建立時間</th>
	</tr>
	<s:iterator var="row" value="queryDtlDevResults">
	<tr onmouseover="GridOver(this)" onmouseout="GridOut(this)" bgcolor="#EFEFEF">
		<td align="center"><s:property value="serialNo"/></td>
		<td align="center"><s:property value="id"/></td>
		<td align="center">
			<c:if test="${insuredType == '1'}">自然人</c:if>
			<c:if test="${insuredType == '2'}">法人</c:if>
		</td>
		<td align="center">
			<c:if test="${insuredFlag == '0'}">其他</c:if>
			<c:if test="${insuredFlag == '1'}">被保人</c:if>
			<c:if test="${insuredFlag == '2'}">要保人</c:if>
			<c:if test="${insuredFlag == '3'}">銀行</c:if>
			<c:if test="${insuredFlag == '4'}">船名</c:if>
			<c:if test="${insuredFlag == '5'}">飛機</c:if>
			<c:if test="${insuredFlag == '6'}">國家</c:if>
			<c:if test="${insuredFlag == '7'}">收貨人</c:if>
			<c:if test="${insuredFlag == '8'}">理賠-賠付對象</c:if>
			<c:if test="${insuredFlag == '9'}">受益人</c:if>
		</td>
		<td align="center"><s:property value="status"/></td>
		<td align="center"><s:property value="name"/></td>
		<td align="center"><s:property value="enName"/></td>
		<td align="center">
			<c:if test="${gender == 'M'}">男</c:if>
			<c:if test="${gender == 'F'}">女</c:if>
		</td>
		<td align="center"><s:property value="birthday"/></td>
		<td align="center"><s:property value="nationCode"/></td>
		<td align="center">
			<c:if test="${dangerOccupation == 'Y'}">是</c:if>
			<c:if test="${dangerOccupation == 'N'}">否</c:if>
		</td>
		<td align="center"><s:property value="estDate"/></td>
		<td align="center">
			<c:if test="${listedCabinetCompany == 'Y'}">是</c:if>
			<c:if test="${listedCabinetCompany == 'N'}">否</c:if>
		</td>
		<td align="center"><fmt:formatDate value='${createtime}' pattern='yyyy/MM/dd HH:mm:ss'/></td>
	</tr>
	</s:iterator>
</table>
</s:if>
<br/>

<!--------------------------------------------- dialog ---------------------------------------------->
<div id="dialog" title="AML回傳結果">
<table id="table2"  cellspacing="0" cellpadding="0" width="970px">
		<tr>
			<td class="MainTdColor" style="width:200px" align="center">
				<span id="lbSearch"><b>查詢作業</b></span></td>
			<td colspan="3" class="image"></td>
		</tr>
	</table>
	<table border="1" id="gridtable" width="970px" border="0" class="main_table">
	<tr align="center">
		<th>險別</th>
		<th>險種代碼</th>
		<th>業務號</th>
		<th>強+任(強制險要保號)</th>
		<th>系統代號</th>
		<th>登入ID</th>
		<th>登入姓名</th>
		<th>作業類型</th>
		<th>公司代號</th>
		<th>業務來源</th>
		<th>商品風險等級</th>
		<th>保費</th>
		<th>建立時間</th>
	</tr>
	<tr>
		<td align="center"><!-- 險別 -->
			<s:property value="amlQueryObjMain.classCode"/>
			<c:if test="${amlQueryObjMain.classCode == 'A'}">任意車險</c:if>
			<c:if test="${amlQueryObjMain.classCode == 'B'}">強制車險</c:if>
			<c:if test="${amlQueryObjMain.classCode == 'C'}">責任險</c:if>
			<c:if test="${amlQueryObjMain.classCode == 'C1'}">傷害暨健康險</c:if>
			<c:if test="${amlQueryObjMain.classCode == 'E'}">工程險</c:if>
			<c:if test="${amlQueryObjMain.classCode == 'F'}">火險</c:if>			
			<c:if test="${amlQueryObjMain.classCode == 'M'}">水險</c:if>			
		</td>
		<td align="center"><!-- 險種代碼 -->
			<s:property value="amlQueryObjMain.riskCode"/>
		</td>
		<td align="center"><!-- 業務號 -->
			<s:property value="amlQueryObjMain.businessNo"/>
		</td>
		<td align="center"><!-- 強+任(強制險要保號) -->
			<s:property value="amlQueryObjMain.extraBusinessNo"/>
		</td>
		<td align="center"><!-- 系統代號 -->
			<s:property value="amlQueryObjMain.appCode"/>
		</td>
		<td align="center"><!-- 登入ID -->
			<s:property value="amlQueryObjMain.userId"/>
		</td>
		<td align="center"><!-- 登入姓名 -->
			<s:property value="amlQueryObjMain.userName"/>
		</td>
		<td align="center"><!-- 作業類型 -->
			<c:if test="${amlQueryObjMain.type == 'Q'}">報價</c:if>
			<c:if test="${amlQueryObjMain.type == 'T'}">要保</c:if>
			<c:if test="${amlQueryObjMain.type == 'E'}">批改</c:if>
			<c:if test="${amlQueryObjMain.type == 'C'}">理賠</c:if>
		</td>
		<td align="center"><!-- 公司代號 -->
			<s:property value="amlQueryObjMain.comCode"/>
		</td>
		<td align="center"><!-- 業務來源 -->
			<c:if test="${amlQueryObjMain.channelType == '10'}">業務員</c:if>
			<c:if test="${amlQueryObjMain.channelType == '20'}">保險經紀人</c:if>
			<c:if test="${amlQueryObjMain.channelType == '30'}">保險代理人</c:if>
			<c:if test="${amlQueryObjMain.channelType == '40'}">直接業務</c:if>			
		</td>
		<td align="center"><!-- 商品風險等級 -->
			<c:if test="${amlQueryObjMain.comLevel == 'H'}">高風險</c:if>
			<c:if test="${amlQueryObjMain.comLevel == 'M'}">中風險</c:if>
			<c:if test="${amlQueryObjMain.comLevel == 'L'}">低風險</c:if>		
		</td>
		
		<td align="center"><!-- 保費 -->
			<s:property value="amlQueryObjMain.prem"/>
		</td>
		<td align="center"><!-- 建立時間 -->
			<fmt:formatDate value='${amlQueryObjMain.createtime}' pattern='yyyy/MM/dd HH:mm:ss'/>
		</td>
	</tr>
</table>

<s:if test="metaAmlResultMain != null">
<p align="center">AML回傳結果</p>
<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
	<tr>
		<td width="110px" align="right">狀態：</td>
		<td width="285px" align="left">
			<c:if test="${metaAmlResultMain.screenCode == '0'}">成功</c:if>
			<c:if test="${metaAmlResultMain.screenCode == '1'}">格式錯誤</c:if>
			<c:if test="${metaAmlResultMain.screenCode == '2'}">系統錯誤</c:if>
			<c:if test="${metaAmlResultMain.screenCode == '3'}">Unikey重覆</c:if>
			<c:if test="${metaAmlResultMain.screenCode == '4'}">無此資料</c:if>
			<c:if test="${metaAmlResultMain.screenCode == '5'}">該客戶有未結案件</c:if>
		</td>
		<td width="110px" align="right">錯誤原因說明：</td>
		<td width="285px" align="left">
			<s:property value="metaAmlResultMain.screenMessage"/>
		</td>
	</tr>
	<tr>
		<td width="110px" align="right">險別：</td>
		<td width="285px" align="left">
			<c:if test="${metaAmlResultMain.classCode == 'A'}">任意車險</c:if>
			<c:if test="${metaAmlResultMain.classCode == 'B'}">強制車險</c:if>
			<c:if test="${metaAmlResultMain.classCode == 'C'}">責任險</c:if>
			<c:if test="${metaAmlResultMain.classCode == 'C1'}">傷害暨健康險</c:if>
			<c:if test="${metaAmlResultMain.classCode == 'E'}">工程險</c:if>
			<c:if test="${metaAmlResultMain.classCode == 'F'}">火險</c:if>			
			<c:if test="${metaAmlResultMain.classCode == 'M'}">水險</c:if>
		</td>
		<td width="110px" align="right">險種：</td>
		<td width="285px" align="left">
			<s:property value="metaAmlResultMain.riskCode"/>
		</td>
	</tr>
	<tr>
		<td width="110px" align="right">業務號：</td>
		<td width="285px" align="left">
			<s:property value="metaAmlResultMain.businessNo"/>
		</td>
		<td width="110px" align="right">作業類型：</td>
		<td width="285px" align="left">
			<c:if test="${metaAmlResultMain.type == 'Q'}">報價</c:if>
			<c:if test="${metaAmlResultMain.type == 'T'}">要保</c:if>
			<c:if test="${metaAmlResultMain.type == 'E'}">批改</c:if>
			<c:if test="${metaAmlResultMain.type == 'C'}">理賠</c:if>
		</td>
	</tr>
	<tr>
		<td width="110px" align="right">公司代號：</td>
		<td width="285px" align="left">
			<s:property value="metaAmlResultMain.comCode"/>
		</td>
		<td width="110px" align="right">系統代號：</td>
		<td width="285px" align="left">
			<s:property value="metaAmlResultMain.appCode"/>
		</td>
	</tr>
	<tr>
		<td width="110px" align="right">進件號碼：</td>
		<td width="285px" align="left">
			<s:property value="metaAmlResultMain.wlfScreenEntryid"/>
		</td>
		<td width="110px" align="right">掃描時間：</td>
		<td width="285px" align="left">
			<s:property value="metaAmlResultMain.wlfScreenDate"/>
		</td>
	</tr>
	<tr>
		<td width="110px" align="right">起案ID：</td>
		<td width="285px" align="left">
			<s:property value="metaAmlResultMain.wlfCaseId"/>
		</td>
		<td width="110px" align="right">工作狀態：</td>
		<td width="285px" align="left">
			<c:if test="${metaAmlResultMain.workStatus == '00'}">不執行</c:if>
			<c:if test="${metaAmlResultMain.workStatus == '01'}">待再查詢</c:if>
			<c:if test="${metaAmlResultMain.workStatus == '02'}">查詢中</c:if>
			<c:if test="${metaAmlResultMain.workStatus == '03'}">收到回覆拒保</c:if>
			<c:if test="${metaAmlResultMain.workStatus == '04'}">收到回覆可承保</c:if>
			<c:if test="${metaAmlResultMain.workStatus == '05'}">查詢異常</c:if>
			<c:if test="${metaAmlResultMain.workStatus == '06'}">查詢超時</c:if>
			<c:if test="${metaAmlResultMain.workStatus == '07'}">人工審核註記</c:if>
			<c:if test="${metaAmlResultMain.workStatus == '08'}">人工審核完成</c:if>
		</td>
	</tr>
	<tr>
		<td width="110px" align="right">案件目前處理單位：</td>
		<td width="285px" align="left">
			<s:property value="metaAmlResultMain.wlfCaseAffiliate"/>
		</td>
		<td width="110px" align="right">案件目前處理使用者：</td>
		<td width="285px" align="left">
			<s:property value="metaAmlResultMain.wlfCaseUser"/>
		</td>
	</tr>
	<tr>
		<td width="110px" align="right">案件調查進度：</td>
		<td width="285px" align="left">
			<c:if test="${metaAmlResultMain.wlfCaseStatus == 'L2'}">第 2 層</c:if>
			<c:if test="${metaAmlResultMain.wlfCaseStatus == 'L3'}">第 3 層</c:if>
			<c:if test="${metaAmlResultMain.wlfCaseStatus == 'C'}">案件結案</c:if>
		</td>
		<td width="110px" align="right">案件確認結果：</td>
		<td width="285px" align="left">
			<c:if test="${metaAmlResultMain.wlfCaseResult == '1'}">案件調查中</c:if>
			<c:if test="${metaAmlResultMain.wlfCaseResult == '2'}">可交易/放行交易</c:if>
			<c:if test="${metaAmlResultMain.wlfCaseResult == '10'}">拒絕交易</c:if>
			<c:if test="${metaAmlResultMain.wlfCaseResult == '11'}">取消交易</c:if>
			<c:if test="${metaAmlResultMain.wlfCaseResult == '12'}">不配合審查</c:if>
		</td>
	</tr>
	<tr>
		<td width="110px" align="right">制裁狀態：</td>
		<td width="285px" align="left">
			<c:if test="${metaAmlResultMain.refuseLimitInsurance == '00'}">拒限保未命中</c:if>
			<c:if test="${metaAmlResultMain.refuseLimitInsurance == '01'}">拒限保命中未判定</c:if>
			<c:if test="${metaAmlResultMain.refuseLimitInsurance == '02'}">拒限保命中已判定True</c:if>
			<c:if test="${metaAmlResultMain.refuseLimitInsurance == '03'}">拒限保命中已判定False</c:if>
		</td>
		<td width="110px" align="right">保單風險：</td>
		<td width="285px" align="left">
			<c:if test="${metaAmlResultMain.riskRating == '00' }">高風險未處理</c:if>
			<c:if test="${metaAmlResultMain.riskRating == '01' }">高風險已處理</c:if>
			<c:if test="${metaAmlResultMain.riskRating == '02' }">中風險未處理</c:if>
			<c:if test="${metaAmlResultMain.riskRating == '03' }">中風險已處理</c:if>
			<c:if test="${metaAmlResultMain.riskRating == '04' }">低風險</c:if>
		</td>
	</tr>
	<tr>
		<td width="110px" align="right">命中狀態：</td>
		<td width="285px" align="left">
			<c:if test="${metaAmlResultMain.listDetection == '01' }">名單檢測未命中</c:if>
			<c:if test="${metaAmlResultMain.listDetection == '02' }">名單檢測命中未判定</c:if>
			<c:if test="${metaAmlResultMain.listDetection == '03' }">名單檢測命中已判定</c:if>
		</td>
		<td width="110px" align="right">建立時間：</td>
		<td width="300px" align="left">
			<fmt:formatDate value='${metaAmlResultMain.createtime}' pattern='yyyy/MM/dd HH:mm:ss'/>
		</td>
	
	</tr>
	<s:hidden key="metaAmlResultMain.oid" id="oidResult" />
</table>
</s:if>
<s:elseif test="metaAmlResultMain == null">
	<p align="center">查無AML回傳結果</p>
</s:elseif>

<s:if test="resultDtlDevResults != null && 0 != resultDtlDevResults.size">
<p align="center">回傳結果明細</p>
<table>
	<tr>
		<td>總共<c:out value="${rPageInfo.rowCount}"/>筆</td> 
		<td><p id="rowPage">第${rPageInfo.currentPage}頁</p></td>
	</tr>
</table>


<div id="pageCountDiv"></div>
	<s:hidden value="%{rPageInfo.currentPage}" id="currentPage"/>
	<s:hidden value="%{rPageInfo.pageSize}" id="pageSize"/>
	<s:hidden value="%{rPageInfo.startRow}" id="startRow"/>
	<s:hidden value="%{rPageInfo.endRow}" id="endRow"/>
	<s:hidden value="%{rPageInfo.pageCount}" id="pageCount"/>
	<s:hidden value="%{rPageInfo.rowCount}" id="rowCount"/>
	<s:hidden value="" id="prePage" name="prePage"/>
	<s:hidden value="" id="nextPage"/>

	<table border="1" id="gridtable2" width="970px" border="0" class="main_table">
		<tr align="center">
			<th>掃描序列號</th>
			<th>檢核結果</th>
			<th>命中類型 </th>
			<th>進件號碼</th>
			<th>評分風險等級</th>
			<th>建立時間</th>
		</tr>
	</table>
</s:if>
<s:elseif test="resultDtlDevResults.size == 0 && metaAmlResultMain != null">
	<p align="center">查無回傳結果明細</p>
</s:elseif>

<table width="970px">
	<tr>
		<td align="center">
			<s:if test="metaAmlResultMain.workStatus =='05' || metaAmlResultMain.workStatus =='06'">
				<a href="${pageContext.request.contextPath}<%=nameSpace%>/btnResend.action?mainOid=${amlQueryObjMain.oid}&workStatus=${metaAmlResultMain.workStatus}"
				onclick="return resendMask('重送');">
				<input type="button" value="重送資料"/></a>
				<a href="${pageContext.request.contextPath}<%=nameSpace%>/lnkGoEdit.action?mainOid=${amlQueryObjMain.oid}"
				onclick="">
				<input type="button" value="修改"/></a>
			</s:if>
			<s:elseif test="metaAmlResultMain.workStatus =='01' || metaAmlResultMain.workStatus =='02'">
				<a href="${pageContext.request.contextPath}<%=nameSpace%>/btnResend.action?mainOid=${amlQueryObjMain.oid}&workStatus=${metaAmlResultMain.workStatus}"
				onclick="return resendMask('重新查詢');">
				<input type="button" value="查詢最新結果"/></a>
			</s:elseif>
		</td>
	</tr>
</table>

</div>
</s:form>
<table width="970px" cellpadding="0" cellspacing="0" >
	<tr>
		<td align="center">
			<input type="button" onclick="ajaxAction('queryMetaAmlResultDetail','')" value="AML回傳結果" id="opener"/>
		</td>
	</tr>
</table>
</body>
</html>