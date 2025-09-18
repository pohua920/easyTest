<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String title = "富邦續保資料處理作業";
	String image = path + "/" + "images/";
	String GAMID = "APS034E2";
	String mDescription = "富邦續保資料處理作業";
	String nameSpace = "/aps/034";
%>
<!-- mantis：FIR0455，處理人員：BJ085，需求單編號：FIR0455 住火-APS富邦續件處理作業 -->
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
		if("query" == type){
			 $("#mainForm").attr("action","btnQuery.action");
			 $("#mainForm").submit();
		}
	}
	
	function showMsg(msg){
		alert(msg);
	}
	
	var msg = "<%=request.getAttribute("message")%>";
	if('' != msg && 'null' != msg){
		showMsg(msg);
	}
	
	function confirmGenFile(filetype){
		if("diff" == filetype){
			if(!confirm("請確認是否產生差異明細檔？")){
				return false;
			}
			if(!checkDiffData() && !confirm("查無資料，將產生空檔，是否繼續？")){
				return false;
			}
		}
		if("reject" == filetype){
			if(!confirm("請確認是否產生剔退檔並傳送至富邦SFTP？(若無剔退資料則會產生空檔。)")){
				return false;
			}
		}
		if("bigPolicy" == filetype){
			if(!compareData("大保單","請確認是否產生大保單並傳送至富邦SFTP？")){
				return false;
			}
		}
		if("renewal" == filetype){
			if(!compareData("續保明細表","請確認是否產生續保明細表？")){
				return false;
			}
		}
		if("fixData" == filetype){
			if(!checkFixData()){
				return false;
			}
		}
		if("debitNotice" == filetype){
			if(!compareData("扣款通知書","請確認是否產生扣款通知書？")){
				return false;
			}
		}
		
		$.blockUI({
			border: 'none',
			padding: '15px',
			backgroundColor: '#000',
			color: '#fff',
			'-webkit-border-radius': '10px',
			'-moz-border-radius': '10px',
			opacity: .5
		});
	}
	
	function compareData(filetype,msg){
		//取出ajax xml相對路徑
		var contextPath = '<%=request.getScheme()%>' + '://<%=request.getServerName()+":"+request.getServerPort()+request.getContextPath()%>';
		var path = ''; 
		
		var batchNo = $("#batchNo").val();
		var fixNrec = $("#fixNrec").val();
		var sfNrec = $("#sfNrec").val();
	   	path = contextPath + '/aps/ajax012/compareDataCount.action';
	   	var check = true;
		
		//執行ajax
		$.ajax({
			url : path,
			type: 'POST',
			data: {batchNo:batchNo, fixNrec:fixNrec, sfNrec:sfNrec},
			dataType: 'json',
			timeout: 10000,
			async: false,
			cache: false,
			error: function (data, status, e){
				alert("比對核心出單筆數失敗。");
				check = false;
			},
			success: function (data, status){
				if(!data.compare){//出單筆數、鎖定筆數扣除預計剔退筆數不符合，回傳bseq(不一定有值)
					alert("鎖定筆數扣除預計剔退筆數為"+data.bprec+"筆，核心已出單"+data.prec+"筆，資料筆數不相符，無法產生"+filetype+"；差異資料如下(序號-要保書號)："+data.bseq);
					check = false;
				}else if(!confirm(msg)){
					check = false;
				}
			}
		});
		return check;
	}
	
	function checkDiffData(){
		//取出ajax xml相對路徑
		var contextPath = '<%=request.getScheme()%>' + '://<%=request.getServerName()+":"+request.getServerPort()+request.getContextPath()%>';
		var path = ''; 
		
		var batchNo = $("#batchNo").val();
	   	path = contextPath + '/aps/ajax012/ckeckDiffData.action';
	   	var check = true;
		
		//執行ajax
		$.ajax({
			url : path,
			type: 'POST',
			data: {batchNo:batchNo},
			dataType: 'json',
			timeout: 10000,
			async: false,
			cache: false,
			error: function (data, status, e){
				alert("查詢差異檔資料失敗。");
				check = false;
			},
			success: function (data, status){
				if(!data.haveData){
					check = false;
				}
			}
		});
		return check;
	}
	
	function checkFixData(){
		//取出ajax xml相對路徑
		var contextPath = '<%=request.getScheme()%>' + '://<%=request.getServerName()+":"+request.getServerPort()+request.getContextPath()%>';
		var path = ''; 
		
		var batchNo = $("#batchNo").val();
	   	path = contextPath + '/aps/ajax012/countFixData.action';
	   	var check = true;
		
		//執行ajax
		$.ajax({
			url : path,
			type: 'POST',
			data: {batchNo:batchNo},
			dataType: 'json',
			timeout: 10000,
			async: false,
			cache: false,
			error: function (data, status, e){
				alert("查詢鎖定資料筆數失敗。");
				check = false;
			},
			success: function (data, status){
				if(!data.haveFixData){
					alert("查無需鎖定之資料。");
					check = false;
				}else if(!confirm("請確認是否要進行整批鎖定("+data.fixCount+")，鎖定後將無法再調整要保資料，包含剔退註記也無法調整。")){
					check = false;
				}
			}
		});
		return check;
	}
	
</script>
</head>

<s:url action="lnkGoUpdate?" namespace="/aps/034" var="goUpdate"/>
<s:url action="btnDownloadFile?" namespace="/aps/034" var="downloadFile"/>
<s:url action="btnUploadBigPolicyFile?" namespace="/aps/034" var="uploadUtf8File"/>
<body style="margin:0;text-align:left">
<table cellspacing="1" cellpadding="1" width="970px" border="0">
	<tr>
		<td width="485px">
			<img src="${pageContext.request.contextPath}/images/Help_Icon.gif" border="0" width="49" height="26">
		</td>
		<td align="right" width="485px">PGMID：<%=GAMID%></td>
	</tr>
	<tr>
		<td align="center" colSpan="2"><h3><%=title%></h3></td>
	</tr>
</table>
<!-- form 開始 -->
<s:form theme="simple" namespace="/aps/034" id="mainForm" name="mainForm">	
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px">
		<tr bgcolor="white">
			<td class="MainTdColor" width="200px" align="center">
				<span id="lbSearch"><b>檔案產生、下載作業</b></span>
			</td>
			<td colspan="2" class="image"></td>
		</tr>
	</table>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
		<tr>
			<td width="150px" align="right">批次號碼：</td>
			<td width="200px" align="left">
				<s:property value="fbDownloadVo.batchNo"/>
			</td>
			<td width="150px" align="right">預計筆數：</td>
			<td width="200px" align="left">
				<s:property value="fbDownloadVo.dataqtyT"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">成功筆數：</td>
			<td width="200px" align="left">
				<s:property value="fbDownloadVo.dataqtyS"/>
			</td>
			<td width="150px" align="right">失敗筆數：</td>
			<td width="200px" align="left">
				<s:property value="fbDownloadVo.dataqtyF"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">鎖定筆數：</td>
			<td width="200px" align="left">
				<s:property value="fbDownloadVo.fixNrec"/>
			</td>
			<td width="150px" align="right">預計剔退筆數：</td>
			<td width="200px" align="left">
				<s:property value="fbDownloadVo.sfNrec"/>
			</td>
		</tr>
	</table>
	<span>======================================================================產生差異明細檔=====================================================================</span>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
		<tr>
			<td width="150px" align="right">處理說明：</td>
			<td width="550px" align="left">
				<span>每M-1月12日前，當「成功筆數」>1筆時，可下載差異明細檔xlsx。</span><br>
				<span>請注意，差異檔於每月11日應續件資料接收後即比對完成，此處僅將比對結果產生成差異明細檔xlsx，</span><br>
				<span>無論是否有使用介面修改資料，都不會重新產生比對結果。</span>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">產生差異檔：</td>
			<td width="550px" align="left">
				<s:if test="fbDownloadVo.dataqtyS > 0">
					<a href="${pageContext.request.contextPath}<%=nameSpace%>/btnGenFile.action?fbDownloadVo.batchNo=${fbDownloadVo.batchNo}&fileType=diffFile"
					onclick="return confirmGenFile('diff');"><input type="button" value="產生" id="diffFile" /></a>
				</s:if>
				<s:else>
					<input type="button" value="產生" id="diffFile" disabled="disabled"/>
				</s:else>
			</td>
		</tr>
	</table>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
		<tr>
			<td width="150px" align="right">產生人員：</td>
			<td width="200px" align="left">
				<s:property value="fbDownloadVo.diffUser"/>
			</td>
			<td width="150px" align="right">產生時間：</td>
			<td width="200px" align="left">
				<fmt:formatDate value='${fbDownloadVo.diffTime}' pattern='yyyy/MM/dd HH:mm:ss'/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">產生結果：</td>
			<td width="200px" align="left">
				<s:property value="fbDownloadVo.diffMemo"/>
			</td>
			<td width="150px" align="right">檔案下載：</td>
			<td width="200px" align="left">
				<a href='${downloadFile}bno=${fbDownloadVo.diffBno}'><input type="button" value="下載"></a>
			</td>
		</tr>
	</table>
	<span>=========================================================================整批鎖定========================================================================</span>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
		<tr>
			<td width="150px" align="right">處理說明：</td>
			<td width="550px" align="left">
				<span>每M-1月15日前，確定本批次所有續保資料無誤後利用本功能將整批應續件狀態改為鎖定。</span><br>
				<span>鎖定後由新核心系統每天三次排程(09:00,13:00,17:00)自動轉入承保系統生成續保要保書。</span><br>
				<span>請注意，鎖定後資料無法再進行修正，也無法調整是否要「剔退」。</span>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">整批鎖定：</td>
			<td width="550px" align="left">
				<s:if test="fbDownloadVo.dataqtyS > fbDownloadVo.fixNrec">
					<a href="${pageContext.request.contextPath}<%=nameSpace%>/btnGenFile.action?fbDownloadVo.batchNo=${fbDownloadVo.batchNo}&fileType=fixData" 
					onclick="return confirmGenFile('fixData');"><input type="button" value="執行" id="fixData" /></a>
				</s:if>
				<s:else>
					<input type="button" value="執行" id="" disabled="disabled"/>
				</s:else>
			</td>
		</tr>
	</table>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
		<tr>
			<td width="150px" align="right">鎖定人員：</td>
			<td width="200px" align="left">
				<s:property value="fbDownloadVo.fixUser"/>
			</td>
			<td width="150px" align="right">鎖定時間：</td>
			<td width="200px" align="left">
				<fmt:formatDate value='${fbDownloadVo.fixDate}' pattern='yyyy/MM/dd HH:mm:ss'/>
			</td>
		</tr>
	</table>
	<span>========================================================================產生剔退檔=======================================================================</span>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
		<tr>
			<td width="150px" align="right">處理說明：</td>
			<td width="550px" align="left">
				<span>每M-1月15日前手動產生，須在整批資料均為「已鎖定」的狀態下才能產生剔退檔。</span><br>
				<span>按下「產生」後若執行成功，系統會自動將剔退檔(FBINSF)傳送至富邦SFTP。</span>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">產生剔退檔：</td>
			<td width="550px" align="left">
				<s:if test='fbDownloadVo.sfMemo == "OK" || fbDownloadVo.fixNrec == 0 || fbDownloadVo.fixNrec == null'>
					<input type="button" value="產生" id="" disabled="disabled"/>
				</s:if>
				<s:else>
					<a href="${pageContext.request.contextPath}<%=nameSpace%>/btnGenFile.action?fbDownloadVo.batchNo=${fbDownloadVo.batchNo}&fileType=rejectFile" 
					onclick="return confirmGenFile('reject');"><input type="button" value="產生" id="rejectFile" /></a>
				</s:else>
			</td>
		</tr>
	</table>	
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
		<tr>
			<td width="150px" align="right">產生人員：</td>
			<td width="200px" align="left">
				<s:property value="fbDownloadVo.sfUser"/>
			</td>
			<td width="150px" align="right">產生時間：</td>
			<td width="200px" align="left">
				<fmt:formatDate value='${fbDownloadVo.sfTime}' pattern='yyyy/MM/dd HH:mm:ss'/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">產生結果：</td>
			<td width="200px" align="left">
				<s:property value="fbDownloadVo.sfMemo"/>
			</td>
			<td width="150px" align="right">檔案下載：</td>
			<td width="200px" align="left">
				<a href='${downloadFile}bno=${fbDownloadVo.sfBno}'><input type="button" value="下載"></a>
			</td>
		</tr>
	</table>
	<span>========================================================================產生大保單=======================================================================</span>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
		<tr>
			<td width="150px" align="right">處理說明：</td>
			<td width="550px" align="left">
				<span>每M-1月25日前手動產生，每M月20日前手動傳送至富邦SFTP。</span><br>
				<span>系統取得本批號未剔退之資料比對核心系統保單資料，取得最新保單資料，將已出單之保單產生大保單檔。</span><br>
				<span>按下「產生」後若執行成功，系統會自動將大保單(FUGGX4-big5檔案)傳送至富邦SFTP(A0001102路徑下)。</span><br>
				<span>按下「上傳」後，系統會將大保單(FUGGX4-UTF_8檔案)傳送至富邦SFTP(A0001178路徑下)。</span>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">產生大保單：</td>
			<td width="550px" align="left">
				<s:if test='fbDownloadVo.bpMemo == "OK" || fbDownloadVo.fixNrec == 0 || fbDownloadVo.fixNrec == null'>
					<input type="button" value="產生" id="" disabled="disabled"/>
				</s:if>
				<s:else>
					<a href="${pageContext.request.contextPath}<%=nameSpace%>/btnGenFile.action?fbDownloadVo.batchNo=${fbDownloadVo.batchNo}&fileType=bigPolicy"
					onclick="return confirmGenFile('bigPolicy');"><input type="button" value="產生" id="bigPolicy" /></a>
				</s:else>
			</td>
		</tr>
	</table>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
		<tr>
			<td width="150px" align="right">產生人員：</td>
			<td width="200px" align="left">
				<s:property value="fbDownloadVo.bpUser"/>
			</td>
			<td width="150px" align="right">產生時間：</td>
			<td width="200px" align="left">
				<fmt:formatDate value='${fbDownloadVo.bpTime}' pattern='yyyy/MM/dd HH:mm:ss'/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">產生結果：</td>
			<td width="200px" align="left">
				<s:property value="fbDownloadVo.bpMemo"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">上傳UTF_8檔案：</td>
			<td width="200px" align="left">
				<s:if test='fbDownloadVo.bpUploadMemo == "OK"'>
					<input type="button" value="上傳" id="" disabled="disabled"/>
				</s:if>
				<s:elseif test='fbDownloadVo.bpMemo == "OK"'>
					<a href='${uploadUtf8File}bno=${fbDownloadVo.bpBno}U&fbDownloadVo.batchNo=${fbDownloadVo.batchNo}&fileType=bpUpload'>
					<input type="button" value="上傳"></a>
				</s:elseif>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">上傳人員：</td>
			<td width="200px" align="left">
				<s:property value="fbDownloadVo.bpUploadUser"/>
			</td>
			<td width="150px" align="right">上傳時間：</td>
			<td width="200px" align="left">
				<fmt:formatDate value='${fbDownloadVo.bpUploadTime}' pattern='yyyy/MM/dd HH:mm:ss'/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">上傳結果：</td>
			<td width="200px" align="left">
				<s:property value="fbDownloadVo.bpUploadMemo"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">檔案下載：</td>
			<td width="200px" align="left">
				<a href='${downloadFile}bno=${fbDownloadVo.bpBno}U'><input type="button" value="下載UTF8檔"></a>
				<a href='${downloadFile}bno=${fbDownloadVo.bpBno}B'><input type="button" value="下載big5檔"></a>
			</td>
		</tr>
	</table>
	<span>=======================================================================產生續期保費扣款通知書=============================================================</span>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
		<tr>
			<td width="150px" align="right">處理說明：</td>
			<td width="550px" align="left">
				<span>每M-1月28日前手動產生。</span><br>
				<span>系統取得本批號未剔退之資料比對核心系統保單資料，取得要保書資料，將已出單之保單產生續期保費扣款通知書。</span><br>
				<span>按下「產生」後若執行成功，請下載檔案後自行列印並寄送保戶。</span>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">產生通知書：</td>
			<td width="550px" align="left">
				<s:if test='fbDownloadVo.fixNrec == 0 || fbDownloadVo.fixNrec == null'>
					<input type="button" value="產生" id="" disabled="disabled"/>
				</s:if>
				<s:else>
					<a href="${pageContext.request.contextPath}<%=nameSpace%>/btnGenFile.action?fbDownloadVo.batchNo=${fbDownloadVo.batchNo}&fileType=debitNotice"
					onclick="return confirmGenFile('debitNotice');"><input type="button" value="產生" id="debitNotice" /></a>
				</s:else>
			</td>
		</tr>
	</table>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
		<tr>
			<td width="150px" align="right">產生人員：</td>
			<td width="200px" align="left">
				<s:property value="fbDownloadVo.paynoUser"/>
			</td>
			<td width="150px" align="right">產生時間：</td>
			<td width="200px" align="left">
				<fmt:formatDate value='${fbDownloadVo.paynoTime}' pattern='yyyy/MM/dd HH:mm:ss'/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">產生結果：</td>
			<td width="200px" align="left">
				<s:property value="fbDownloadVo.paynoMemo"/>
			</td>
			<td width="150px" align="right">檔案下載：</td>
			<td width="200px" align="left">
				<a href='${downloadFile}bno=${fbDownloadVo.paynoBno}'><input type="button" value="下載"></a>
			</td>
		</tr>
	</table>
	<span>===========================================================================產生續保明細表================================================================</span>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
		<tr>
			<td width="150px" align="right">處理說明：</td>
			<td width="550px" align="left">
				<span>每M月15日前手動產生。</span><br>
				<span>系統取得本批號未剔退之資料比對核心系統保單資料，取得最新保單資料，將已出單之保單產生續保明細表。</span><br>
				<span>產生完成後寄送或MAIL予富邦作服部。</span>
			</td>
		</tr>
	</table>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
		<tr>
			<td width="150px" align="right">產生續保明細表：</td>
			<td width="200px" align="left">
				<s:if test='fbDownloadVo.fixNrec == 0 || fbDownloadVo.fixNrec == null'>
					<input type="button" value="產生" id="" disabled="disabled"/>
				</s:if>
				<s:else>
 					<a href="${pageContext.request.contextPath}<%=nameSpace%>/btnGenFile.action?fbDownloadVo.batchNo=${fbDownloadVo.batchNo}&fileType=renewListFile"
					onclick="return confirmGenFile('renewal')"><input type="button" value="產生" id="renewListFile" /></a>
				</s:else>
			</td>
			<td width="150px" align="right">檔案下載：</td>
			<td width="200px" align="left">
				簽單聲明書(空白)
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">產生人員：</td>
			<td width="200px" align="left">
				<s:property value="fbDownloadVo.rnlistUser"/>
			</td>
			<td width="150px" align="right">產生時間：</td>
			<td width="200px" align="left">
				<fmt:formatDate value='${fbDownloadVo.rnlistTime}' pattern='yyyy/MM/dd HH:mm:ss'/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">產生結果：</td>
			<td width="200px" align="left">
				<s:property value="fbDownloadVo.rnlistMemo"/>
			</td>
			<td width="150px" align="right">檔案下載：</td>
			<td width="200px" align="left">
				<a href='${downloadFile}bno=${fbDownloadVo.rnlistBno}'><input type="button" value="續保明細表"></a>
			</td>
		</tr>
	</table>
	<table width="970px" cellpadding="0" cellspacing="0" >
		<tr>
			<td align="center">
				<a href="${pageContext.request.contextPath}<%=nameSpace%>/btnQuery.action?goBack=Y">
		<input type="button" value="回上頁"/></a>
			</td>
		</tr>
	</table>
</s:form>
	<s:hidden key="fbDownloadVo.batchNo" id="batchNo"/>
	<s:hidden key="fbDownloadVo.sfNrec" id="sfNrec"/>
	<s:hidden key="fbDownloadVo.fixNrec" id="fixNrec"/>
</body>
</html>