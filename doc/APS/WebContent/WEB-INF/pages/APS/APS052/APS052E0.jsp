<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String title = "傷害暨健康險批次排程執行作業";
	String image = path + "/" + "images/";
	String GAMID = "APS052E0";
	String mDescription = "傷害暨健康險批次排程執行作業";
	String nameSpace = "/aps/052";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- /** mantis：HAS0215，處理人員：CD094，需求單編號：HAS0215 TA雄獅回饋檔*/ -->
<!-- /** mantis：OTH0161，處理人員：DP0714，錠嵂保經全險種回饋檔 (全檔案覆蓋)*/ -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<script language="JavaScript">

	$(document).ready(function(){
		//加上小日曆
		$('#tiiUndate').datepicker({dateFormat:"yyyy/mm/dd"});
	});
	
	function form_submit(type){
		if ("btnExecuteHasLionBackFile" == type) {
			$("#mainForm").attr("action","/APS/aps/052/btnExecuteHasLionBackFile.action");
		} else if ("btnExecuteHasLawBackFile" == type) {
			$("#mainForm").attr("action","/APS/aps/052/btnExecuteHasLawBackFile.action");
		} else if ("btnExecuteHasFetBackFile" == type) {//mantis：HAS0284，處理人員：DP0706，需求單編號：HAS0284_遠傳優化需求-比對同要保人但生日或姓名不同
			$("#mainForm").attr("action","/APS/aps/052/btnExecuteHasFetInsuredFile.action");
		}
		$("#mainForm").submit();
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
<body style="margin:0;text-align:left">
<table cellspacing="1" cellpadding="1" width="970px" border="0">
	<tbody>
		   <tr>
			  <td width="485px">			      
				  <img src="${pageContext.request.contextPath}/images/Help_Icon.gif" border="0">
			  </td>
			  <td align="right" width="485px">PGMID：<%=GAMID%></td>
		   </tr>
		   <tr>
			   <td colspan="2" align="center" width="970px"><h3><%=title%></h3></td>
		   </tr>
	</tbody>		
</table>
<s:form theme="simple" namespace="/aps/004" id="mainForm" name="mainForm">
<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
	<tbody>
		<tr bgcolor="white">
			<td class="MainTdColor" align="center" width="200px">
				<span id="lbSearch"><b>手動執行排程作業</b></span></td>
			<td colspan="5" class="image"></td>
		</tr>
	</tbody>
</table>
<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px">
	<tbody>
		<tr>
			<td align="left">
			1.雄獅回饋檔產生作業(未輸入批次號則完整執行，若有輸入批次號則依據批次號重新產生TXT拋送到雄獅SFTP)：
			<input type="button" value="執行" onclick="javascript:form_submit('btnExecuteHasLionBackFile');"/>
			</br>資料時間(YYYYMMDD)：<s:textfield key="filter.dataDate" id="dataDate" theme="simple" />
			(若無輸入執行時間則自動帶入系統時間)
			</br>回饋檔類型：<s:select key="filter.backFileType" id="backFileType" list="#{'1':'受理檔','2':'保批檔','3':'銷帳檔','4':'佣金檔','5':'理賠檔'}"/>
			&emsp;批次號碼：<s:textfield key="filter.backFileBatchNo" id="backFileBatchNo" theme="simple" />
			</td>
		</tr>
		<tr>
			<td align="left">
			2.錠嵂保經全險種回饋檔產生作業(未輸入批次號則完整執行，若有輸入批次號則依據批次號重新產生ZIP拋送到錠嵂保經SFTP)：
			<input type="button" value="執行" onclick="javascript:form_submit('btnExecuteHasLawBackFile');"/>
			<br>資料時間(YYYYMMDD)：<s:textfield key="filter.dataDate2" theme="simple" />(若無輸入執行時間則自動帶入系統時間)
			<br>批次號碼：<s:textfield key="filter.backFileBatchNo2" theme="simple" />
			</td>
		</tr>
		<!--mantis：HAS0284，處理人員：DP0706，需求單編號：HAS0284_遠傳優化需求-比對同要保人但生日或姓名不同 START-->
		<tr>
			<td align="left">
			3.遠傳旅平險姓名生日不一致比對排程(未輸入批次號則完整執行，若有輸入批次號則依據批次號重新產生excel並寄送mail)：
			<input type="button" value="執行" onclick="javascript:form_submit('btnExecuteHasFetBackFile');"/>
			<br>資料時間(YYYYMMDD)：<s:textfield key="filter.dataDate3" theme="simple" />(若無輸入執行時間則自動帶入系統時間，如選擇每月資料則以輸入的年月做執行)
			<br>回饋檔類型：<s:select key="filter.backFileType2" id="backFileType2" list="#{'1':'每日','2':'每月'}"/>
			&emsp;批次號碼：<s:textfield key="filter.backFileBatchNo3" id="backFileBatchNo" theme="simple" />
			</td>
		</tr>
		<!--mantis：HAS0284，處理人員：DP0706，需求單編號：HAS0284_遠傳優化需求-比對同要保人但生日或姓名不同END-->
	</tbody>
</table>
</s:form>
</body>
</html>