<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String title = "續保通知檔案產生作業";
	String image = path + "/" + "images/";
	String GAMID = "APS044E2";
	String mDescription = "續保通知檔案產生作業";
	String nameSpace = "/aps/044";
%>
<!-- mantis：FIR0571，處理人員：BJ085，需求單編號：FIR0571 住火_APS到期通知處理作業 -->
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
		if("ctbc" == filetype){
			if(!confirm("是否產生中信到期通知檔案？")){
				return false;
			}
		}
		if("ncore" == filetype){
			if(!confirm("是否產生核心續件到期通知檔案？")){
				return false;
			}
		}
		//mantis： FIR0685 ，處理人員： CF048，住火_APS續保通知檔案產生作業_原產生核心續件到期通知-無附加險_新增I40503勤業通路產檔格式 start
		if("tsca" == filetype){
			if(!confirm("是否產生勤業(台中二信)到期通知檔案？")){
				return false;
			}
		}
		//mantis： FIR0685 ，處理人員： CF048，住火_APS續保通知檔案產生作業_原產生核心續件到期通知-無附加險_新增I40503勤業通路產檔格式 end
		if("ncorea" == filetype){
			if(!confirm("是否產生核心續件-附加險到期通知檔案？")){
				return false;
			}
		}
		if("bop" == filetype){
			if(!confirm("是否產生板信到期通知檔案？")){
				return false;
			}
		}
		if("fb" == filetype){
			if(!confirm("是否產生富邦到期通知檔案？")){
				return false;
			}
		}
		// mantis：FIR0638，處理人員：DP0714，住火_APS到期續保要保書處理 -- start
		if("frnpro" == filetype){
			if(!confirm("是否產生續保要保書供廠商套印檔案？")){
				return false;
			}
		}
		// mantis：FIR0638，處理人員：DP0714，住火_APS到期續保要保書處理 -- end
		
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

<s:url action="btnDownloadFile2?" namespace="/aps/044" var="downloadFile"/>
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
<s:form theme="simple" namespace="/aps/044" id="mainForm" name="mainForm">	
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
				<s:property value="firRenewList.batchNo"/>
			</td>
			<td width="150px" align="right">筆數：</td>
			<td width="200px" align="left">
				<s:property value="firRenewList.dataqty"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">續保年月：</td>
			<td width="200px" align="left">
				<s:property value="firRenewList.rnYymm"/>
			</td>
		</tr>
	</table>
	<span>===================================================================產生核心續件到期通知-無附加險==================================================================</span>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
		<tr>
			<td width="150px" align="right">處理說明：</td>
			<td width="550px" align="left">
				<span>取得「類型=核心續件」且無附加險之資料，產生印刷廠TXT。請自行「下載」檔案後上傳至印刷廠系統。</span><br>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">產生檔案：</td>
			<td width="550px" align="left">
				<a href="${pageContext.request.contextPath}<%=nameSpace%>/btnGenFile.action?firRenewList.batchNo=${firRenewList.batchNo}&fileType=ncore"
				onclick="return confirmGenFile('ncore');"><input type="button" value="產生" id="ncoreFile" /></a>
			</td>
		</tr>
	</table>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
		<tr>
			<td width="150px" align="right">產生人員：</td>
			<td width="200px" align="left">
				<s:property value="firRenewList.ncoreUser"/>
			</td>
			<td width="150px" align="right">產生時間：</td>
			<td width="200px" align="left">
				<fmt:formatDate value='${firRenewList.ncoreTime}' pattern='yyyy/MM/dd HH:mm:ss'/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">產生結果：</td>
			<td width="200px" align="left">
				<s:property value="firRenewList.ncoreMemo"/>
			</td>
			<td width="150px" align="right">檔案下載：</td>
			<td width="200px" align="left">
				<c:if test="${firRenewList.ncoreMemo == 'OK'}">
					<a href='${downloadFile}bno=${firRenewList.ncoreBno}'><input type="button" value="下載"></a>
				</c:if>
			</td>
		</tr>
	</table>
	<span>===================================================================產生核心續件到期通知-有附加險==================================================================</span>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
		<tr>
			<td width="150px" align="right">處理說明：</td>
			<td width="550px" align="left">
				<span>取得「類型=核心續件」且有附加險之資料，產生WORD合併列印之EXCEL檔。</span><br>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">產生檔案：</td>
			<td width="550px" align="left">
				<a href="${pageContext.request.contextPath}<%=nameSpace%>/btnGenFile.action?firRenewList.batchNo=${firRenewList.batchNo}&fileType=ncorea"
				onclick="return confirmGenFile('ncorea');"><input type="button" value="產生" id="ncoreaFile" /></a>
			</td>
		</tr>
	</table>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
		<tr>
			<td width="150px" align="right">產生人員：</td>
			<td width="200px" align="left">
				<s:property value="firRenewList.ncoreaUser"/>
			</td>
			<td width="150px" align="right">產生時間：</td>
			<td width="200px" align="left">
				<fmt:formatDate value='${firRenewList.ncoreaTime}' pattern='yyyy/MM/dd HH:mm:ss'/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">產生結果：</td>
			<td width="200px" align="left">
				<s:property value="firRenewList.ncoreaMemo"/>
			</td>
			<td width="150px" align="right">檔案下載：</td>
			<td width="200px" align="left">
				<c:if test="${firRenewList.ncoreaMemo == 'OK'}">
					<a href='${downloadFile}bno=${firRenewList.ncoreaBno}'><input type="button" value="下載"></a>
				</c:if>
			</td>
		</tr>
	</table>
	<span>========================================================================產生中信到期通知=========================================================================</span>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
		<tr>
			<td width="150px" align="right">處理說明：</td>
			<td width="550px" align="left">
				<span>取得「類型=中信件」之資料，產生印刷廠TXT。請自行「下載」檔案後上傳至印刷廠系統。</span><br>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">產生檔案：</td>
			<td width="550px" align="left">
				<a href="${pageContext.request.contextPath}<%=nameSpace%>/btnGenFile.action?firRenewList.batchNo=${firRenewList.batchNo}&fileType=ctbc"
				onclick="return confirmGenFile('ctbc');"><input type="button" value="產生" id="ctbcFile" /></a>
			</td>
		</tr>
	</table>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
		<tr>
			<td width="150px" align="right">產生人員：</td>
			<td width="200px" align="left">
				<s:property value="firRenewList.i99050User"/>
			</td>
			<td width="150px" align="right">產生時間：</td>
			<td width="200px" align="left">
				<fmt:formatDate value='${firRenewList.i99050Time}' pattern='yyyy/MM/dd HH:mm:ss'/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">產生結果：</td>
			<td width="200px" align="left">
				<s:property value="firRenewList.i99050Memo"/>
			</td>
			<td width="150px" align="right">檔案下載：</td>
			<td width="200px" align="left">
				<c:if test="${firRenewList.i99050Memo == 'OK'}">
					<a href='${downloadFile}bno=${firRenewList.i99050Bno}'><input type="button" value="下載"></a>
				</c:if>
			</td>
		</tr>
	</table>
	<span>========================================================================產生板信件到期通知=========================================================================</span>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
		<tr>
			<td width="150px" align="right">處理說明：</td>
			<td width="550px" align="left">
				<span>取得「類型=外銀件且業務來源=I99065」之資料，產生印刷廠TXT。請自行「下載」檔案後上傳至印刷廠系統。</span><br>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">產生檔案：</td>
			<td width="550px" align="left">
				<a href="${pageContext.request.contextPath}<%=nameSpace%>/btnGenFile.action?firRenewList.batchNo=${firRenewList.batchNo}&fileType=bop"
				onclick="return confirmGenFile('bop');"><input type="button" value="產生" id="bopFile" /></a>
			</td>
		</tr>
	</table>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
		<tr>
			<td width="150px" align="right">產生人員：</td>
			<td width="200px" align="left">
				<s:property value="firRenewList.i99065User"/>
			</td>
			<td width="150px" align="right">產生時間：</td>
			<td width="200px" align="left">
				<fmt:formatDate value='${firRenewList.i99065Time}' pattern='yyyy/MM/dd HH:mm:ss'/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">產生結果：</td>
			<td width="200px" align="left">
				<s:property value="firRenewList.i99065Memo"/>
			</td>
			<td width="150px" align="right">檔案下載：</td>
			<td width="200px" align="left">
				<c:if test="${firRenewList.i99065Memo == 'OK'}">
					<a href='${downloadFile}bno=${firRenewList.i99065Bno}'><input type="button" value="下載"></a>
				</c:if>
			</td>
		</tr>
	</table>
	<span>========================================================================產生富邦件到期通知=========================================================================</span>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
		<tr>
			<td width="150px" align="right">處理說明：</td>
			<td width="550px" align="left">
				<span>取得「類型=外銀件且業務來源=I00107」之資料，產生印刷廠TXT。請自行「下載」檔案後上傳至印刷廠系統。</span><br>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">產生檔案：</td>
			<td width="550px" align="left">
				<a href="${pageContext.request.contextPath}<%=nameSpace%>/btnGenFile.action?firRenewList.batchNo=${firRenewList.batchNo}&fileType=fb"
				onclick="return confirmGenFile('fb');"><input type="button" value="產生" id="fbFile" /></a>
			</td>
		</tr>
	</table>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
		<tr>
			<td width="150px" align="right">產生人員：</td>
			<td width="200px" align="left">
				<s:property value="firRenewList.i00107User"/>
			</td>
			<td width="150px" align="right">產生時間：</td>
			<td width="200px" align="left">
				<fmt:formatDate value='${firRenewList.i00107Time}' pattern='yyyy/MM/dd HH:mm:ss'/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">產生結果：</td>
			<td width="200px" align="left">
				<s:property value="firRenewList.i00107Memo"/>
			</td>
			<td width="150px" align="right">檔案下載：</td>
			<td width="200px" align="left">
				<c:if test="${firRenewList.i00107Memo == 'OK'}">
					<a href='${downloadFile}bno=${firRenewList.i00107Bno}'><input type="button" value="下載"></a>
				</c:if>
			</td>
		</tr>
	</table>
    <!-- mantis：FIR0638，處理人員：DP0714，住火_APS到期續保要保書處理 start -->
	<span>========================================================================續保要保書供廠商套印=========================================================================</span>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
		<tr>
			<td width="150px" align="right">處理說明：</td>
			<td width="550px" align="left">
				<span>產生印刷廠TXT。請自行「下載」檔案後上傳至印刷廠系統。</span><br>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">產生檔案：</td>
			<td width="550px" align="left">
				<a href="${pageContext.request.contextPath}<%=nameSpace%>/btnGenFile.action?firRenewList.batchNo=${firRenewList.batchNo}&fileType=frnpro&firRenewList.rnYymm=${firRenewList.rnYymm}"
				onclick="return confirmGenFile('frnpro');"><input type="button" value="產生" id="fbFile" /></a>
			</td>
		</tr>
	</table>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
		<tr>
			<td width="150px" align="right">產生人員：</td>
			<td width="200px" align="left">
				<s:property value="firRenewList.frnproUser"/>
			</td>
			<td width="150px" align="right">產生時間：</td>
			<td width="200px" align="left">
				<fmt:formatDate value='${firRenewList.frnproTime}' pattern='yyyy/MM/dd HH:mm:ss'/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">產生結果：</td>
			<td width="200px" align="left">
				<s:property value="firRenewList.frnproMemo"/>
			</td>
			<td width="150px" align="right">檔案下載：</td>
			<td width="200px" align="left">
				<c:if test="${firRenewList.frnproMemo == 'OK'}">
					<a href='${downloadFile}bno=${firRenewList.frnproBno}'><input type="button" value="下載"></a>
				</c:if>
			</td>
		</tr>
	</table>
    <!-- mantis：FIR0638，處理人員：DP0714，住火_APS到期續保要保書處理 end -->
    <!-- mantis： FIR0685，處理人員： CF048，住火_APS續保通知檔案產生作業_原產生核心續件到期通知-無附加險_新增I40503勤業通路產檔格式 start -->
 	<span>===================================================================產生勤業(台中二信)到期通知==================================================================</span>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
		<tr>
			<td width="150px" align="right">處理說明：</td>
			<td width="550px" align="left">
				<span>取得「類型=核心續件 且業務來源=I40503」且無附加險之資料，產生印刷廠TXT。請自行「下載」檔案後上傳至印刷廠系統。</span><br>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">產生檔案：</td>
			<td width="550px" align="left">
				<a href="${pageContext.request.contextPath}<%=nameSpace%>/btnGenFile.action?firRenewList.batchNo=${firRenewList.batchNo}&fileType=tsca"
				onclick="return confirmGenFile('tsca');"><input type="button" value="產生" id="tscaFile" /></a>
			</td>
		</tr>
	</table>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
		<tr>
			<td width="150px" align="right">產生人員：</td>
			<td width="200px" align="left">
				<s:property value="firRenewList.i40503User"/>
			</td>
			<td width="150px" align="right">產生時間：</td>
			<td width="200px" align="left">
				<fmt:formatDate value='${firRenewList.i40503Time}' pattern='yyyy/MM/dd HH:mm:ss'/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">產生結果：</td>
			<td width="200px" align="left">
				<s:property value="firRenewList.i40503Memo"/>
			</td>
			<td width="150px" align="right">檔案下載：</td>
			<td width="200px" align="left">
				<c:if test="${firRenewList.ncoreMemo == 'OK'}">
					<a href='${downloadFile}bno=${firRenewList.i40503Bno}'><input type="button" value="下載"></a>
				</c:if>
			</td>
		</tr>
	</table>   
    <!-- mantis： FIR0685，處理人員： CF048，住火_APS續保通知檔案產生作業_原產生核心續件到期通知-無附加險_新增I40503勤業通路產檔格式 end -->
	<span>=========================================================================產生台銀件到期通知=======================================================================</span>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
		<tr>
			<td width="150px" align="right">處理說明：</td>
			<td width="550px" align="left">
				<span>取得「類型=外銀件且業務來源=I99004」之資料，產生印刷廠TXT。請自行「下載」檔案後上傳至印刷廠系統。</span><br>
				<span>註:此功能待台銀續件由AS400回新核心後再開發。</span>
			</td>
		</tr>
	</table>
	<span>=========================================================================產生聯邦件到期通知=======================================================================</span>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
		<tr>
			<td width="150px" align="right">處理說明：</td>
			<td width="550px" align="left">
				<span>取得「類型=外銀件且業務來源=I99080」之資料，產生印刷廠TXT。請自行「下載」檔案後上傳至印刷廠系統。</span><br>
				<span>註:此功能待聯邦續件由AS400回新核心後再開發</span>
			</td>
		</tr>
	</table>
	<span>=========================================================================產生元大件到期通知=======================================================================</span>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
		<tr>
			<td width="150px" align="right">處理說明：</td>
			<td width="550px" align="left">
				<span>取得「類型=外銀件且業務來源=I00006」之資料，產生印刷廠TXT。請自行「下載」檔案後上傳至印刷廠系統。</span><br>
				<span>註:此功能待元大續件由AS400回新核心後再開發</span>
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