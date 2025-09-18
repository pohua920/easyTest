<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
	String path = request.getContextPath();
	String title = "續保通知明細查詢作業";
	String image = path + "/" + "images/";
	String GAMID = "APS044E1";
	String mDescription = "續保通知明細查詢作業";
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
		if("dtlQuery" == type){
		 	$("#mainForm").attr("action","btnDtlQuery.action");
		 	$("#mainForm").submit();				
		}
		
		if("btnDownloadFile" == type){
			doBlockUI(type);
		}
		
		if("btnUpdateCoreRenewData" == type){
			doBlockUI(type);
		}
	}
	
	function doBlockUI(type){
		var token = new Date().getTime();
		$.blockUI({
			//blockUI：設定頁面指定區域顯示執行中文字(如Loading...)並鎖定該區域限制輸入。
			border: 'none',
			padding: '15px',//padding：同時設定四個邊留白的捷徑屬性
			backgroundColor: '#000',//backgroundColor：訊息背景顏色
			color: '#fff',//color：訊息字樣顏色
			'-webkit-border-radius': '10px',//WebKit設定四邊的圓角為10px
			'-moz-border-radius': '10px',//Mozilla設定四邊的圓角為10px
			opacity: .5//opacity：指定表單和其控制項的透明度等級
		});

	    $("#token").val(token);
	    $("#rnYymm").val();
	    $("#batchNo").val();
		$("#mainForm").attr("action",type + ".action");
		$("#mainForm").submit();
		var pollDownload = setInterval(function() {
	        if (document.cookie.indexOf(type + "=" + token) > -1) {
	            $.unblockUI();
	            clearInterval(pollDownload);
	        }
	    }, 1000);
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
<s:url action="btnUpdateFlag?" namespace="/aps/044" var="flagUpdate"/>
<body style="margin: 0; text-align: left">
	<table cellspacing="1" cellpadding="1" width="970px" border="0">
		<tbody>
			<tr>
				<td width="485px">
				<a href="${pageContext.request.contextPath}<%=nameSpace%>/lnkGoQuery.action">
				<img src="${pageContext.request.contextPath}/images/Search_Icon.gif" border="0" width="48" height="26"></a>
				<img src="${pageContext.request.contextPath}/images/Help_Icon.gif" border="0"></td>
				<td align="right" width="485px">PGMID：<%=GAMID%></td>
			</tr>
			<tr>
				<td colspan="2" align="center" width="970px"><h3><%=title%></h3></td>
			</tr>
		</tbody>
	</table>
	<!-- mantis：FIR0657，處理人員：BJ085，需求單編號：FIR0657 住火_配合造價表調整重算外銀續保保額 -->
	<s:form theme="simple" namespace="/aps/044" id="mainForm" name="mainForm">
		<s:hidden name="token" id="token"/>
		<table id="table2" cellspacing="0" cellpadding="0" width="970px">
			<tr>
				<td class="MainTdColor" style="width: 200px" align="center">
					<span id="lbSearch"><b>明細查詢作業</b></span>
				</td>
				<td colspan="3" class="image"></td>
			</tr>
		</table>
		<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px">
			<tr>
				<td width="200px" align="right">批次號碼：</td>
				<td width="285px" align="left">
					<s:property value="batchNo"/>
					<s:hidden name="batchNo" id="batchNo"/>
				</td>
				<td colspan="5"></td>
				<td width="200px" align="right">續保年月：</td>
				<td width="285px" align="left">
					<s:property value="rnYymm"/>
					<s:hidden name="rnYymm" id="rnYymm"/>
				</td>
				<td colspan="5"></td>
			</tr>
			<tr>
				<td width="200px" align="right">業務來源：</td>
				<td width="285px" align="left">
					<s:textfield key="dFilter.businessnature" id="businessnature" />
				</td>
				<td colspan="5"></td>
				<td width="200px" align="right">續保單號：</td>
				<td width="285px" align="left">
					<s:textfield key="dFilter.oldPolicyno" id="oldPolicyno" />
				</td>
				<td colspan="5"></td>
			</tr>
			<tr>
				<td width="200px" align="right">類型：</td>
				<td width="285px" align="left">
					<s:select key="dFilter.rnType" id="rnType" theme="simple" list="#{'':'', '1':'核心續件', '2':'中信件', '3':'外銀件'}" />
				</td>
				<td colspan="5"></td>
				<td width="200px" align="right">新年度保單號：</td>
				<td width="285px" align="left">
					<s:textfield key="dFilter.policyno" id="policyno" />
				</td>
				<td colspan="5"></td>
			</tr>
			<tr>
				<td width="200px" align="right">歸屬單位：</td>
				<td width="285px" align="left">
					<s:textfield key="dFilter.comcode" id="comcode" />
				</td>
				<td colspan="5"></td>
			</tr>
			<tr>
				<td width="200px" align="right">核心續件要保書(1)：</td>
				<td width="285px" align="left">
					<s:select key="dFilter.isRenewal" id="isRenewal" 
					theme="simple" list="#{'':'', 'Y':'已完成續保篩選', 'N':'未完成續保篩選', 'C':'有附加險未重算保費'}" />
				</td>
				<td colspan="5"></td>
				<td width="200px" align="right">附加險(2)：</td>
				<td width="285px" align="left">
					<s:select key="dFilter.isAddIns" id="isAddIns" 
					theme="simple" list="#{'':'', 'Y':'有附加險', 'N':'無附加險'}" />
				</td>
				<td colspan="5"></td>
			</tr>
			<tr>
				<td width="200px" align="right">自動續約(3)：</td>
				<td width="285px" align="left">
					<s:select key="dFilter.isAutoRenew" id="isAutoRenew" 
					theme="simple" list="#{'':'', 'Y':'自動續約', 'N':'非自動續約'}" />
				</td>
				<td colspan="5"></td>
				<td width="200px" align="right">核心續件續期繳費(4)：</td>
				<td width="285px" align="left">
					<s:select key="dFilter.rnPayway" id="rnPayway" 
					theme="simple" list="#{'':'', '2':'信用卡'}" />
				</td>
				<td colspan="5"></td>
			</tr>
			<tr>
				<td width="200px" align="right">核心續件虛擬編號(5)：</td>
				<td width="285px" align="left">
					<s:select key="dFilter.isVirtualCode" id="isVirtualCode" 
					theme="simple" list="#{'':'', 'Y':'有', 'N':'無'}" />
				</td>
				<td colspan="5"></td>
				<td width="200px" align="right">出單狀況(6)：</td>
				<td width="285px" align="left">
					<s:select key="dFilter.isInsured" id="isInsured" 
					theme="simple" list="#{'':'', 'Y':'已出單', 'N':'未出單'}" />
				</td>
				<td colspan="5"></td>
			</tr>
			<tr>
				<td width="200px" align="right">刪除註記：</td>
				<td width="285px" align="left">
					<s:select key="dFilter.deleteFlag" id="deleteFlag" 
					theme="simple" list="#{'':'', 'Y':'Y', 'N':'N'}" />
				</td>
				<td colspan="5"></td>
				<td width="200px" align="right">提示訊息：</td>
				<td width="285px" align="left">
					<s:select key="dFilter.isRemark" id="isRemark" 
					theme="simple" list="#{'':'', 'Y':'Y', 'N':'N'}" />
				</td>
				<td colspan="5"></td>
			</tr>
		</table>
		<!-- mantis：FIR0571，處理人員：BJ085，需求單編號：FIR0571 住火_APS到期通知處理作業-新增印刷廠產檔作業規格 start -->
		<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px">
			<tr>
				<td width="145px" align="right">說明：</td>
				<td width="550px" align="left">
				<span>1.「下載xls」將符合畫面上查詢條件之結果產生xls檔。</span><br>
				<span>2.本年度保額規則如下：</span><br>
				<span>&emsp;類型=核心續件，已做完續保篩選案件，依排程執行時要保書保額；</span><br>
				<span>&emsp;類型=核心續件，尚未做完續保篩選案件，依按下「核心續件資料更新」按鈕時要保書保額；</span><br>
				<span>&emsp;類型=中信件或外銀件，依設定檔設定，若不重算則以排程當下前一年度最新保額。</span><br>
				<span>3.「核心續件資料更新」說明：</span><br>
				<span>&emsp;3-1.類型=核心續件，且(1)=N或C。(即無續保要保書號或有續保要保書號但附加險未重算之要保書。)</span><br>
				<span>&emsp;3-2.會將續保要保書之保額/保費資料重新帶入應續件資料。</span><br>
			</tr>
		</table>
		<!-- mantis：FIR0571，處理人員：BJ085，需求單編號：FIR0571 住火_APS到期通知處理作業-新增印刷廠產檔作業規格 end -->
		<table width="970px" cellpadding="0" cellspacing="0">
			<tr>
				<td align="center">
					<input type="button" value="查詢" onclick="javascript:form_submit('dtlQuery');" />
					<input type="button" value="下載xls" onclick="javascript:form_submit('btnDownloadFile');" />
					<input type="button" value="核心續件資料更新" onclick="javascript:form_submit('btnUpdateCoreRenewData');" />
					<!-- mantis：FIR0571，處理人員：BJ085，需求單編號：FIR0571 住火_APS到期通知處理作業-新增印刷廠產檔作業規格 -->
					<a href="${pageContext.request.contextPath}<%=nameSpace%>/btnQuery.action?goBack=Y"><input type="button" value="回上頁"/></a>
				</td>
			</tr>
		</table>
		<s:if test="dtlDevResults != null && 0 != dtlDevResults.size">
			<table cellspacing="1" cellpadding="1" border="0" width="970px">
				<tr>
					<td>
						<div align="right">
							<custom:ddlChangePage 
							    formId="mainForm"
								name="dPageInfo" 
								nameSpace="/aps/044"
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
			<!--Grid Table-->
			<table border="1" id="gridtable" width="970px" border="0"
				class="main_table">
				<tr align="center">
					<th></th>
					<th>
						續保單號
						<c:url value="/aps/044/lnkDtlSortQuery.action" var="sortURL">
							<c:param name="dFilter.sortBy" value="DTL.OLD_POLICYNO" />
							<c:param name="dFilter.sortType" value="ASC" />
						</c:url>
						<a href='<c:out value="${sortURL}" />'>▲</a>
						<c:url value="/aps/044/lnkDtlSortQuery.action" var="sortURL">
							<c:param name="dFilter.sortBy" value="DTL.OLD_POLICYNO" />
							<c:param name="dFilter.sortType" value="DESC" />
						</c:url>
						<a href='<c:out value="${sortURL}" />'>▼</a>
					</th>
					<th>
						類型
						<c:url value="/aps/044/lnkDtlSortQuery.action" var="sortURL">
							<c:param name="dFilter.sortBy" value="DTL.RN_TYPE" />
							<c:param name="dFilter.sortType" value="ASC" />
						</c:url>
						<a href='<c:out value="${sortURL}" />'>▲</a>
						<c:url value="/aps/044/lnkDtlSortQuery.action" var="sortURL">
							<c:param name="dFilter.sortBy" value="DTL.RN_TYPE" />
							<c:param name="dFilter.sortType" value="DESC" />
						</c:url>
						<a href='<c:out value="${sortURL}" />'>▼</a>
					</th>
					<th>
						業務來源
						<c:url value="/aps/044/lnkDtlSortQuery.action" var="sortURL">
							<c:param name="dFilter.sortBy" value="DTL.BUSINESSNATURE" />
							<c:param name="dFilter.sortType" value="ASC" />
						</c:url>
						<a href='<c:out value="${sortURL}" />'>▲</a>
						<c:url value="/aps/044/lnkDtlSortQuery.action" var="sortURL">
							<c:param name="dFilter.sortBy" value="DTL.BUSINESSNATURE" />
							<c:param name="dFilter.sortType" value="DESC" />
						</c:url>
						<a href='<c:out value="${sortURL}" />'>▼</a>
					</th>
					<th>
						保單到期日
						<c:url value="/aps/044/lnkDtlSortQuery.action" var="sortURL">
							<c:param name="dFilter.sortBy" value="DTL.OLD_ENDDATE" />
							<c:param name="dFilter.sortType" value="ASC" />
						</c:url>
						<a href='<c:out value="${sortURL}" />'>▲</a>
						<c:url value="/aps/044/lnkDtlSortQuery.action" var="sortURL">
							<c:param name="dFilter.sortBy" value="DTL.OLD_ENDDATE" />
							<c:param name="dFilter.sortType" value="DESC" />
						</c:url>
						<a href='<c:out value="${sortURL}" />'>▼</a>
					</th>
					<th>主被保險人</th>
					<th>(1)</th>
					<th>(2)</th>
					<th>(3)</th>
					<th>(4)</th>
					<th>(5)</th>
					<th>(6)</th>
					<th>備註</th>
				</tr>
				<s:iterator var="row" value="dtlDevResults">
					<tr onmouseover="GridOver(this)" onmouseout="GridOut(this)" bgcolor="#EFEFEF">
						<td align="left">
							<c:choose>
								<c:when test="${deleteFlag == 'Y'}"><a href='${flagUpdate}firRenewListDtl.oid=${row.oid}
									&firRenewListDtl.deleteFlag=N'>還原</a>
								</c:when>
								<c:when test="${deleteFlag == 'N'}"><a href='${flagUpdate}firRenewListDtl.oid=${row.oid}
									&firRenewListDtl.deleteFlag=Y'>刪除</a></c:when>
							</c:choose>
						</td>
						<td align="left"><s:property value="oldPolicyno" /></td>
						<td align="left">
							<c:choose>
								<c:when test="${rnType == '1'}">核心續件</c:when>
								<c:when test="${rnType == '2'}">中信件</c:when>
								<c:when test="${rnType == '3'}">外銀續件</c:when>
							</c:choose>
						</td>
						<td align="left"><s:property value="businessnature" /></td>
						<td align="left">
							<fmt:formatDate value='${oldEnddate}' pattern='yyyy/MM/dd' />
						</td>
						<td align="left"><s:property value="insuredname" /></td>
						<td align="left"><s:property value="isRenewal" /></td>
						<td align="left"><s:property value="isAddIns" /></td>
						<td align="left"><s:property value="isAutoRenew" /></td>
						<td align="left"><s:property value="rnPayway" /></td>
						<td align="left"><s:property value="isPrintvirtualcode" /></td>
						<td align="left"><s:property value="isInsured" /></td>
						<td align="left"><s:property value="remark" /></td>
					</tr>
				</s:iterator>
			</table>
		</s:if>
	</s:form>
</body>
</html>