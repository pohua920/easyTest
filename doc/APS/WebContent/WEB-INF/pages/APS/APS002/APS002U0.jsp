<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
String path = request.getContextPath();
String title = "中信新件轉入查詢作業";
String image = path + "/" + "images/";
String GAMID = "APS002U0";
String mDescription = "中信新件轉入查詢作業";
String nameSpace = "/aps/002";
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
		 //$("label").html('');
		// mantis：FIR0181， 中信新件流程-排程查詢作業(含產生查詢及回饋查詢) start
		if("goEditUnusualData" == type){
			 $("#mainForm").attr("action","lnkGoEditUnusualData.action");
			 $("#mainForm").submit();
		}
		// mantis：FIR0181， 中信新件流程-排程查詢作業(含產生查詢及回饋查詢) end
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
<s:url action="lnkDownloadPDF?" namespace="/aps/002" var="downloadPDF"/>
<s:url action="lnkDownloadProve?" namespace="/aps/002" var="downloadProve"/>
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

<!--form 開始 -->
<s:form theme="simple" namespace="/aps/002" id="mainForm" name="mainForm">
<!-- mantis：FIR0181， 中信新件流程-排程查詢作業(含產生查詢及回饋查詢) start-->
<s:hidden name="aps002DetailVo.batchNo" />
<s:hidden name="aps002DetailVo.batchSeq" />
<s:hidden name="aps002DetailVo.fkOrderSeq" />
<s:hidden name="aps002DetailVo.coreComcode" />
<!-- mantis：FIR0181， 中信新件流程-排程查詢作業(含產生查詢及回饋查詢) end-->
<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
	<tbody>
		<tr bgcolor="white">
			<td class="MainTdColor" align="center" width="200px">
				<span id="lbSearch"><b>查詢作業</b></span></td>
			<td colspan="5" class="image"></td>
		</tr>
	</tbody>
</table>
<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
	<!-- mantis：FIR0181， 中信新件流程-排程查詢作業(含產生查詢及回饋查詢) start-->
	<tbody>
		<tr>
			<td width="200"  align="right">批次號碼-序號：</td>
			<td width="285" align="left"><s:property value="aps002DetailVo.batchNo"/>-<s:property value="aps002DetailVo.batchSeq"/></td>
			<td width="200"  align="right">受理編號：</td>
			<td style="width: 285px;" align="left">
				<s:property value="aps002DetailVo.fkOrderSeq"/>
			</td>			
		</tr>
		<tr>
			<td  align="right">下單狀態：</td>
			<td  align="left">
				<c:if test="${aps002DetailVo.fkStatus == '01'}">下單完成</c:if>
				<c:if test="${aps002DetailVo.fkStatus == '02'}">暫存</c:if>
				<c:if test="${aps002DetailVo.fkStatus == '09'}">取消</c:if>
				<!-- mantis：OTH0078，處理人員：BJ085，需求單編號：OTH0078 更名相關-APS、CWP、SYS系統中有台壽保產險、tlg、網域名稱更為新名稱 -->
				<c:if test="${aps002DetailVo.fkStatus == 'XX'}">中國信託產險檢核異常</c:if>
			</td>
			<td  align="right">受理編號狀態：</td>
			<td  align="left">
				<c:if test="${aps002DetailVo.orderSeqStatus == '0'}">未處理</c:if>
				<c:if test="${aps002DetailVo.orderSeqStatus == '1'}">資料驗證失敗</c:if>
				<c:if test="${aps002DetailVo.orderSeqStatus == '2'}">寫入中信下單檔成功</c:if>
				<c:if test="${aps002DetailVo.orderSeqStatus == '3'}">轉核心暫存檔成功</c:if>
				<c:if test="${aps002DetailVo.orderSeqStatus == '4'}">轉核心暫存檔失敗</c:if>
				<c:if test="${aps002DetailVo.orderSeqStatus == '5'}">轉核心要保檔成功</c:if>
				<c:if test="${aps002DetailVo.orderSeqStatus == '6'}">轉核心要保檔失敗</c:if>
				<c:if test="${aps002DetailVo.orderSeqStatus == 'A'}">出單完成產生投保證明</c:if>
				<c:if test="${aps002DetailVo.orderSeqStatus == 'B'}">產生回饋檔成功</c:if>
				<c:if test="${aps002DetailVo.orderSeqStatus == 'C'}">產生回饋檔失敗</c:if>
				<c:if test="${aps002DetailVo.orderSeqStatus == 'D'}">中信暫存件不處理</c:if>
				<c:if test="${aps002DetailVo.orderSeqStatus == 'E'}">中信取消件不處理</c:if>
				<c:if test="${aps002DetailVo.orderSeqStatus == 'F'}">未簽署件不處理</c:if>
				<c:if test="${aps002DetailVo.orderSeqStatus == 'G'}">內部程式異常</c:if>
				<c:if test="${aps002DetailVo.orderSeqStatus == 'H'}">產生投保證明失敗-SP</c:if>
				<c:if test="${aps002DetailVo.orderSeqStatus == 'I'}">產生投保證明失敗-PDF</c:if>
			</td>			
		</tr>
		<tr>
			<td  align="right">送件類別：</td>
			<td  align="left">
				<c:if test="${aps002DetailVo.sendType == '01'}">正本送件</c:if>
				<c:if test="${aps002DetailVo.sendType == '02'}">傳真送件</c:if>
				<c:if test="${aps002DetailVo.sendType == '03'}">續保叫單</c:if>
			</td>
			<td  align="right">保單號碼：</td>
			<td  align="left"><s:property value="aps002DetailVo.policyno"/></td>			
		</tr>
		<tr>
			<td  align="right">核心轉檔執行狀態：</td>
			<td  align="left">
				<c:if test="${aps002DetailVo.prpinsBatchStatus == '0'}">未處理</c:if>
				<c:if test="${aps002DetailVo.prpinsBatchStatus == '1'}">執行中</c:if>
				<c:if test="${aps002DetailVo.prpinsBatchStatus == '2'}">完成</c:if>
			</td>
			<td  align="right">核心轉檔異常訊息：</td>
			<td  style="width: 285px;display: inline-block;word-wrap:break-word;" align="left">
				<s:property value="aps002DetailVo.prpinsBatchErrmsg"/>
			</td>			
		</tr>
		<tr>
			<td  align="right">轉核心暫存時間：</td>
			<td  align="left">
				<c:if test="${not empty aps002DetailVo.transTmpTime}">
					<s:date name="aps002DetailVo.transTmpTime" format="yyyy/MM/dd HH:mm:ss" nice="false"/>
				</c:if> 
			</td>
			<td  align="right">轉核心要保時間：</td>
			<td  align="left">
				<c:if test="${not empty aps002DetailVo.transPpsTime}">
					<s:date name="aps002DetailVo.transPpsTime" format="yyyy/MM/dd HH:mm:ss" nice="false"/>
				</c:if> 
			</td>			
		</tr>	
		<tr>
			<td  align="right">複保險查詢結果：</td>
			<td  align="left">
				<c:if test="${aps002DetailVo.dquakeStatus == 'S'}">正常</c:if>
				<c:if test="${aps002DetailVo.dquakeStatus == 'D'}">複保險</c:if>
				<c:if test="${aps002DetailVo.dquakeStatus == 'E'}">地震基金服務異常</c:if>
			</td>
			<td  align="right">複保險序號：</td>
			<td  align="left">
				<s:property value="aps002DetailVo.dquakeNo"/>
			</td>			
		</tr>	
		<tr>
			<td  align="right">保額計算服務序號：</td>
			<td  align="left">
				<s:property value="aps002DetailVo.oidFirPremcalcTmp"/>
			</td>
			<td  align="right">　</td>
			<td  align="left">　</td>			
		</tr>	
		<tr>
			<td  align="right">火險保額檢核結果：</td>
			<td  align="left">
				<c:if test="${aps002DetailVo.famtStatus == '1'}">足額</c:if>
				<c:if test="${aps002DetailVo.famtStatus == '2'}">不足額</c:if>
				<c:if test="${aps002DetailVo.famtStatus == '3'}">超額</c:if>
			</td>
			<td  align="right">火險建議保額：</td>
			<td  align="left">
				<fmt:formatNumber type = "number"  maxFractionDigits = "3" value = "${aps002DetailVo.wsFirAmt}" />
			</td>			
		</tr>	
		<tr>
			<td  align="right">地震保額檢核結果：</td>
			<td  align="left">
				<c:if test="${aps002DetailVo.qamtStatus == '1'}">足額</c:if>
				<c:if test="${aps002DetailVo.qamtStatus == '2'}">不足額</c:if>
				<c:if test="${aps002DetailVo.qamtStatus == '3'}">超額</c:if>
			</td>
			<td  align="right">地震保額：</td>
			<td  align="left">
				<fmt:formatNumber type = "number"  maxFractionDigits = "3" value = "${aps002DetailVo.wsQuakeAmt}" />
			</td>			
		</tr>	
		<tr>
			<td  align="right">地址檢核結果：</td>
			<td  align="left">
				<c:if test="${aps002DetailVo.addrStatus  == 'S'}">通過</c:if>
				<c:if test="${aps002DetailVo.addrStatus == 'F'}">不通過</c:if>
				<c:if test="${aps002DetailVo.addrStatus == 'E'}">異常</c:if>
			</td>
			<td  align="right">地址檢核明細：</td>
			<td  align="left">
				<s:property value="aps002DetailVo.addrDetail"/>
			</td>			
		</tr>	
		<tr>
			<td  align="right">是否應有簽署文件(TIFF)：</td>
			<td  align="left">
				<s:property value="aps002DetailVo.isTiff"/>
			</td>
			<td  align="right">簽署文件檔名：</td>
			<td  align="left">
				<a href='${downloadPDF}aps002DetailVo.batchNo=${aps002DetailVo.batchNo}&aps002DetailVo.batchSeq=${aps002DetailVo.batchSeq}&aps002DetailVo.fkOrderSeq=${aps002DetailVo.fkOrderSeq}&aps002DetailVo.filenameTiff=${aps002DetailVo.filenameTiff}'><s:property value="aps002DetailVo.filenameTiff"/></a>
			</td>			
		</tr>	
		<tr>
			<td  align="right">資料檢核異常訊息：</td>
			<td  align="left">
				<s:property value="aps002DetailVo.checkErrMsg"/>
			</td>
			<td  align="right">　</td>
			<td  align="left">　</td>			
		</tr>	
		<tr>
			<td  align="right">資料檢核提示訊息：</td>
			<td  align="left">
				<s:property value="aps002DetailVo.checkWarnMsg"/>
			</td>
			<td  align="right">　</td>
			<td  align="left">　</td>			
		</tr>	
		<tr>
			<td  align="right">投保證明產生時間：</td>
			<td  align="left">
				<c:if test="${not empty aps002DetailVo.printCtfTime}">
					<s:date name="aps002DetailVo.printCtfTime" format="yyyy/MM/dd HH:mm:ss" nice="false"/>
				</c:if> 
			</td>
			<td  align="right">投保證明PDF檔：</td>
			<td  align="left">
				<c:if test="${not empty aps002DetailVo.ctfOid}">
					<a href='${downloadProve}aps002DetailVo.fkOrderSeq=${aps002DetailVo.fkOrderSeq}'>預覽</a>
				</c:if>
			</td>			
		</tr>	
		<tr>
			<td  align="right">核心系統撤單人員：</td>
			<td  align="left">
				<s:property value="aps002DetailVo.coreCancelUser"/>
			</td>
			<td  align="right">核心系統撤單時間：</td>
			<td  align="left">
				<c:if test="${not empty aps002DetailVo.coreCancelTime}">
					<s:date name="aps002DetailVo.coreCancelTime" format="yyyy/MM/dd HH:mm:ss" nice="false"/>
				</c:if> 
			</td>			
		</tr>	
		<tr>
			<td  align="right">回饋檔產生時間：</td>
			<td  align="left">
				<c:if test="${not empty aps002DetailVo.bkTime}">
					<s:date name="aps002DetailVo.bkTime" format="yyyy/MM/dd HH:mm:ss" nice="false"/>
				</c:if> 
			</td>
			<td  align="right">回饋檔檔名：</td>
			<td  align="left">
				<s:property value="aps002DetailVo.bkFilename"/>
			</td>			
		</tr>	
		<tr>
			<td  align="right">回饋檔內容：</td>
			<td  align="left">
				<c:if test="${aps002DetailVo.bkType == '01'}">已核保</c:if>
				<c:if test="${aps002DetailVo.bkType == '02'}">不核保</c:if>
			</td>
			<td  align="right">代收區號：</td>
			<td  align="left"><s:property value="aps002DetailVo.commCenterCode"/></td>			
		</tr>	
		<tr>
			<td  align="right">要保人姓名：</td>
			<td  align="left"><s:property value="aps002DetailVo.appliname"/></td>
			<td  align="right">被保險人姓名：</td>
			<td  align="left"><s:property value="aps002DetailVo.insuredname"/></td>			
		</tr>	
		<tr>
			<td  align="right">服務人員：</td>
			<td  align="left"><s:property value="aps002DetailVo.handler1code"/></td>
			<td  align="right">歸屬單位：</td>
			<td  align="left"><s:property value="aps002DetailVo.comcode"/></td>			
		</tr>
		<tr>
			<td  align="right">登錄證號：</td>
			<td  align="left"><s:property value="aps002DetailVo.handleridentifynumber"/></td>
			<td  align="right">業務員姓名：</td>
			<td  align="left"><s:property value="aps002DetailVo.handlername"/></td>			
		</tr>
		<!-- mantis：FIR0181， 中信新件流程-排程查詢作業(含產生查詢及回饋查詢) end-->
	</tbody>
</table>
<table width="970px" cellpadding="0" cellspacing="0" >
	<tr>
		<td align="center">
			<!-- mantis：FIR0181， 中信新件流程-排程查詢作業(含產生查詢及回饋查詢)
				 新增「轉檔異常資料修正」功能 start-->
			<c:if test="${aps002DetailVo.orderSeqStatus == '6'}">
    				<input type="button" value="轉檔異常資料修正" onclick="javascript:form_submit('goEditUnusualData');"/>
  			</c:if>
			<input type="button" value="回上頁" onclick="javascript:history.back();"/>
			<!-- mantis：FIR0181， 中信新件流程-排程查詢作業(含產生查詢及回饋查詢) end-->
		</td>
	</tr>
</table>
</s:form>
<!-- form結束 -->
</body>
</html>