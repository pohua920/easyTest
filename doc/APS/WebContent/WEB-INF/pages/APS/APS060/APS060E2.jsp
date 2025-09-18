<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String title = "元大續保資料處理作業-產檔作業";
	String image = path + "/" + "images/";
	String GAMID = "APS060E2";
	String mDescription = "元大續保資料處理作業";
	String nameSpace = "/aps/060";
%>
<!-- mantis：FIR0668，處理人員：DP0706，需求單編號：FIR0668_住火_元大續保作業_續件資料處理作業 -->
<!-- FIR0690、FIR0693	 CF048住火	住火_APS_元大續保作業Ph2_N+1_產生保單副本&N+1產生出單明細檔-->
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
		if("RN" == filetype){
			if(!confirm("請確認是否產生元大續保明細檔？")){
				return false;
			}
		}
		if("EN" == filetype){
			if(!confirm("請確認是否產生元大到期通知？")){
				return false;
			}
		}
		if("PO" == filetype){
			if(!confirm("請確認是否產生元大出單明細檔？")){
				return false;
			}
		}
		if("CO" == filetype){
			if(!confirm("請確認是否產生元大保單副本檔？")){
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

<s:url action="btnDownloadFile?" namespace="/aps/060" var="downloadFile"/>
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
<s:form theme="simple" namespace="/aps/060" id="mainForm" name="mainForm">	
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
				<s:property value="ycbDownloadVo.batchNo"/>
			</td>
			<td width="150px" align="right">續保年月：</td>
			<td width="200px" align="left">
				<s:property value="ycbDownloadVo.rnYyyymm"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">預計筆數：</td>
			<td width="200px" align="left">
				<s:property value="ycbDownloadVo.dataqtyT"/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">成功筆數：</td>
			<td width="200px" align="left">
				<s:property value="ycbDownloadVo.dataqtyS"/>
			</td>
			<td width="150px" align="right">失敗筆數：</td>
			<td width="200px" align="left">
				<s:property value="ycbDownloadVo.dataqtyF"/>
			</td>
		</tr>
		
	</table>
	<span>======================================================================通路部產檔=====================================================================</span>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
		<tr>
			<td width="150px" align="right">處理說明：</td>
			<td width="550px" align="left">
				<span>續保資料產生後，可進行檔案產生，包含「提供元大續保明細檔」。</span><br>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">產生檔案：</td>
			<td width="550px" align="left">
				<s:if test="ycbDownloadVo.dataqtyS > 0">
					<a href="${pageContext.request.contextPath}<%=nameSpace%>/btnGenFile.action?batchNo=${ycbDownloadVo.batchNo}&fileType=rnFile"
					onclick="return confirmGenFile('RN');"><input type="button" value="產生提供元大續保明細檔" id="rnFile" /></a>
				</s:if>
				<s:else>
					<input type="button" value="產生提供元大續保明細檔" id="rnFile" disabled="disabled"/>
				</s:else>
			</td>
		</tr>
	</table>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
		<tr>
			<td width="150px" align="right">產生人員：</td>
			<td width="200px" align="left">
				<s:property value="ycbDownloadVo.rnUser"/>
			</td>
			<td width="150px" align="right">產生時間：</td>
			<td width="200px" align="left">
				<fmt:formatDate value='${ycbDownloadVo.rnTime}' pattern='yyyy/MM/dd HH:mm:ss'/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">產生結果：</td>
			<td width="200px" align="left">
				<s:property value="ycbDownloadVo.rnMemo"/>
			</td>
			<td width="150px" align="right">檔案下載：</td>
			<td width="200px" align="left">
				<a href='${downloadFile}bno=${ycbDownloadVo.rnBno}'><input type="button" value="下載"></a>
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
				<s:if test="ycbDownloadVo.dataqtyS > 0">
					<a href="${pageContext.request.contextPath}<%=nameSpace%>/btnGenFile.action?batchNo=${ycbDownloadVo.batchNo}&fileType=enFile"
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
				<s:property value="ycbDownloadVo.enUser"/>
			</td>
			<td width="150px" align="right">產生時間：</td>
			<td width="200px" align="left">
				<fmt:formatDate value='${ycbDownloadVo.enTime}' pattern='yyyy/MM/dd HH:mm:ss'/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">產生結果：</td>
			<td width="200px" align="left">
				<s:property value="ycbDownloadVo.enMemo"/>
			</td>
			<td width="150px" align="right">檔案下載：</td>
			<td width="200px" align="left">
				<a href='${downloadFile}bno=${ycbDownloadVo.enBno}'><input type="button" value="下載"></a>
			</td>
		</tr>
	</table>
	<span>=========================================================================通路部產檔-出單明細檔========================================================================</span>
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
				<s:if test="ycbDownloadVo.dataqtyS > 0">
					<a href="${pageContext.request.contextPath}<%=nameSpace%>/btnGenFile.action?batchNo=${ycbDownloadVo.batchNo}&fileType=poFile"
					onclick="return confirmGenFile('PO');"><input type="button" value="產生出單明細檔" id="enFile" /></a>
				</s:if>
				<s:else>
					<input type="button" value="產生出單明細檔" id="poFile" disabled="disabled"/>
				</s:else>
			</td>
		</tr>
	</table>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
		<tr>
			<td width="150px" align="right">產生人員：</td>
			<td width="200px" align="left">
				<s:property value="ycbDownloadVo.poUser"/>
			</td>
			<td width="150px" align="right">產生時間：</td>
			<td width="200px" align="left">
				<fmt:formatDate value='${ycbDownloadVo.poTime}' pattern='yyyy/MM/dd HH:mm:ss'/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">產生結果：</td>
			<td width="200px" align="left">
				<s:property value="ycbDownloadVo.poMemo"/>
			</td>
			<td width="150px" align="right">檔案下載：</td>
			<td width="200px" align="left">
				<a href='${downloadFile}bno=${ycbDownloadVo.poBno}'><input type="button" value="下載"></a>
			</td>
		</tr>
	</table>
	<span>=========================================================================商品部產檔-保單副本檔========================================================================</span>
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
				<s:if test="ycbDownloadVo.dataqtyS > 0">
					<a href="${pageContext.request.contextPath}<%=nameSpace%>/btnGenFile.action?batchNo=${ycbDownloadVo.batchNo}&fileType=coFile"
					onclick="return confirmGenFile('CO');"><input type="button" value="產生保單副本檔" id="coFile" /></a>
				</s:if>
				<s:else>
					<input type="button" value="產生保單副本檔" id="coFile" disabled="disabled"/>
				</s:else>
			</td>
		</tr>
	</table>
		<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
		<tr>
			<td width="150px" align="right">產生人員：</td>
			<td width="200px" align="left">
				<s:property value="ycbDownloadVo.coUser"/>
			</td>
			<td width="150px" align="right">產生時間：</td>
			<td width="200px" align="left">
				<fmt:formatDate value='${ycbDownloadVo.coTime}' pattern='yyyy/MM/dd HH:mm:ss'/>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">產生結果：</td>
			<td width="200px" align="left">
				<s:property value="ycbDownloadVo.coMemo"/>
			</td>
			<td width="150px" align="right">檔案下載：</td>
			<td width="200px" align="left">
				<a href='${downloadFile}bno=${ycbDownloadVo.coBno}'><input type="button" value="下載"></a>
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