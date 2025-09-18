<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String title = "臺銀續保資料處理作業-產檔作業";
	String image = path + "/" + "images/";
	String GAMID = "APS055E2";
	String mDescription = "臺銀續保資料處理作業";
	String nameSpace = "/aps/055";
%>
<!-- mantis：FIR0621，處理人員：BJ085，需求單編號：FIR0621 住火_臺銀續保作業_續件資料處理作業 -->
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
		if("RE" == filetype){
			if(!confirm("請確認是否產生臺銀RE檔並傳送至臺銀SFTP？")){
				return false;
			}
		}
		if("EN" == filetype){
			if(!confirm("請確認是否產生到期通知檔案？")){
				return false;
			}
		}
		if("RNPRO" == filetype){
			if(!confirm("請確認是否產生續保要保書相關檔案？")){
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
</script>
</head>

<s:url action="btnDownloadFile?" namespace="/aps/055" var="downloadFile"/>
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
<s:form theme="simple" namespace="/aps/055" id="mainForm" name="mainForm">	
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
				<s:property value="botDownloadVo.batchNo"/>
			</td>
			<td width="150px" align="right">續保年月：</td>
			<td width="200px" align="left">
				<s:property value="botDownloadVo.rnYyyymm"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">預計筆數：</td>
			<td width="200px" align="left">
				<s:property value="botDownloadVo.dataqtyT"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">成功筆數：</td>
			<td width="200px" align="left">
				<s:property value="botDownloadVo.dataqtyS"/>
			</td>
			<td width="150px" align="right">失敗筆數：</td>
			<td width="200px" align="left">
				<s:property value="botDownloadVo.dataqtyF"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">住火筆數：</td>
			<td width="200px" align="left">
				<s:property value="botDownloadVo.f02Nrec"/>
			</td>
			<td width="150px" align="right">商火筆數：</td>
			<td width="200px" align="left">
				<s:property value="botDownloadVo.f01Nrec"/>
			</td>
		</tr>
	</table>
	<span>======================================================================通路部產檔=====================================================================</span>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
		<tr>
			<td width="150px" align="right">處理說明：</td>
			<td width="550px" align="left">
				<span>資料確認無誤後，可進行檔案產生，包含「續保要保書+書面分析報告+代理投保通知」、「臺銀RE檔」。</span><br>
				<span>臺銀RE執行「產生」功能後，會自動拋送至臺銀SFTP。</span><br>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">產生檔案：</td>
			<td width="550px" align="left">
				<s:if test="botDownloadVo.dataqtyS > 0">
					<a href="${pageContext.request.contextPath}<%=nameSpace%>/btnGenFile.action?batchNo=${botDownloadVo.batchNo}&fileType=reFile"
					onclick="return confirmGenFile('RE');"><input type="button" value="產生臺銀RE檔並傳送SFTP" id="reFile" /></a>
				</s:if>
				<s:else>
					<input type="button" value="產生臺銀RE檔並傳送SFTP" id="reFile" disabled="disabled"/>
				</s:else>
			</td>
		</tr>
	</table>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
		<tr>
			<td width="150px" align="right">產生人員：</td>
			<td width="200px" align="left">
				<s:property value="botDownloadVo.reUser"/>
			</td>
			<td width="150px" align="right">產生時間：</td>
			<td width="200px" align="left">
				<fmt:formatDate value='${botDownloadVo.reTime}' pattern='yyyy/MM/dd HH:mm:ss'/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">產生結果：</td>
			<td width="200px" align="left">
				<s:property value="botDownloadVo.reMemo"/>
			</td>
			<td width="150px" align="right">檔案下載：</td>
			<td width="200px" align="left">
				<a href='${downloadFile}bno=${botDownloadVo.reBno}'><input type="button" value="下載"></a>
			</td>
		</tr>
	</table>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
		<tr>
			<td width="150px" align="right">產生檔案：</td>
			<td width="550px" align="left">
				<s:if test="botDownloadVo.dataqtyS > 0">
					<a href="${pageContext.request.contextPath}<%=nameSpace%>/btnGenFile.action?batchNo=${botDownloadVo.batchNo}&fileType=rnproFile&rnYyyymm=${botDownloadVo.rnYyyymm}"
					onclick="return confirmGenFile('RNPRO');"><input type="button" value="產生續保要保書、書面分析報告、代理投保通知" id="rnproFile" /></a>
				</s:if>
				<s:else>
					<input type="button" value="產生續保要保書、書面分析報告、代理投保通知" id="rnproFile" disabled="disabled"/>
				</s:else>
			</td>
		</tr>
	</table>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
		<tr>
			<td width="150px" align="right">產生人員：</td>
			<td width="200px" align="left">
				<s:property value="botDownloadVo.rnproUser"/>
			</td>
			<td width="150px" align="right">產生時間：</td>
			<td width="200px" align="left">
				<fmt:formatDate value='${botDownloadVo.rnproTime}' pattern='yyyy/MM/dd HH:mm:ss'/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">產生結果：</td>
			<td width="200px" align="left">
				<s:property value="botDownloadVo.rnproMemo"/>
			</td>
			<td width="150px" align="right">檔案下載：</td>
			<td width="200px" align="left">
				<a href='${downloadFile}bno=${botDownloadVo.rnproBno}'><input type="button" value="下載"></a>
			</td>
		</tr>
	</table>
	<span>=========================================================================商品部產檔========================================================================</span>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
		<tr>
			<td width="150px" align="right">處理說明：</td>
			<td width="550px" align="left">
				<span>手動產生檔案後需自行下載後上傳至印刷廠系統。</span><br>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">產生檔案：</td>
			<td width="550px" align="left">
				<s:if test="botDownloadVo.dataqtyS > 0">
					<a href="${pageContext.request.contextPath}<%=nameSpace%>/btnGenFile.action?batchNo=${botDownloadVo.batchNo}&fileType=enFile"
					onclick="return confirmGenFile('EN');"><input type="button" value="產生到期通知" id="enFile" /></a>
				</s:if>
				<s:else>
					<input type="button" value="產生到期通知" id="enFile" disabled="disabled"/>
				</s:else>
			</td>
		</tr>
	</table>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
		<tr>
			<td width="150px" align="right">產生人員：</td>
			<td width="200px" align="left">
				<s:property value="botDownloadVo.enUser"/>
			</td>
			<td width="150px" align="right">產生時間：</td>
			<td width="200px" align="left">
				<fmt:formatDate value='${botDownloadVo.enTime}' pattern='yyyy/MM/dd HH:mm:ss'/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">產生結果：</td>
			<td width="200px" align="left">
				<s:property value="botDownloadVo.enMemo"/>
			</td>
			<td width="150px" align="right">檔案下載：</td>
			<td width="200px" align="left">
				<a href='${downloadFile}bno=${botDownloadVo.enBno}'><input type="button" value="下載"></a>
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
</body>
</html>