<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<!-- mantis：FIR0181， 中信新件流程-排程查詢作業(含產生查詢及回饋查詢) 
新增「轉檔異常資料修正」功能-->
<%
String path = request.getContextPath();
String title = "中信新件轉入-轉檔異常資料修正作業";
String image = path + "/" + "images/";
String GAMID = "APS002U1";
String mDescription = "中信新件轉入-轉檔異常資料修正作業";
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
		if("btnEditUnusualData" == type){
			 $("#mainForm").attr("action","btnEditUnusualData.action");
			 if(validate()){
				 $("#mainForm").submit();
			 }
		}
		
		if("goBackU0" == type){
			var redirectUrl = "/APS/aps/002/lnkGoDetail.action?aps002DetailVo.batchNo=" + $("#batchNo").val() 
				+ "&aps002DetailVo.batchSeq=" + $("#batchSeq").val()
				+ "&aps002DetailVo.fkOrderSeq=" + $("#fkOrderSeq").val()
				+ "&aps002DetailVo.coreComcode=" + $("#coreComcode").val();

			window.location.href = redirectUrl;
		}
	}
	
	function showMsg(msg){
		alert(msg);
	}
	
	var msg = "<%=request.getAttribute("message")%>";
	if('' != msg && 'null' != msg){
		showMsg(msg);
	}
	
	function validate(){
		var message = "";
		var check = true;
		var commCenterCode = $("#commCenterCode").val();
		if(commCenterCode === null || commCenterCode === '') {
			message += "請輸入代收區號;"
			check = false;
		}
		var handler1code = $("#handler1code").val();
		if(handler1code === null || handler1code === '') {
			message += "請輸入服務人員;"
			check = false;
		}
		if(message.length > 0){
			alert(message);
		}
		return check;
	}
	
</script>
</head>
<s:url action="lnkGoDetail?" namespace="/aps/002" var="goDetail"/>
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
<s:hidden name="aps002DetailVo.batchNo" id="batchNo"/>
<s:hidden name="aps002DetailVo.batchSeq" id="batchSeq"/>
<s:hidden name="aps002DetailVo.fkOrderSeq" id="fkOrderSeq"/>
<s:hidden name="aps002DetailVo.coreComcode" id="coreComcode"/>
<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
	<tbody>
		<tr bgcolor="white">
			<td class="MainTdColor" align="center" width="200px">
				<span id="lbSearch"><b>修改作業</b></span></td>
			<td colspan="5" class="image"></td>
		</tr>
		<tr>
			<td width="200px" align="right">批次號碼：</td>
			<td width="285px" align="left"><s:property value="aps002DetailVo.batchNo"/></td>
			<td width="200px" align="right">序號：</td>
			<td width="285px" align="left"><s:property value="aps002DetailVo.batchSeq"/></td>			
		</tr>
		<tr>
			<td width="200px" align="right">受理編號：</td>
			<td width="285px" align="left"><s:property value="aps002DetailVo.fkOrderSeq"/></td>
			<td width="200px" align="right">&nbsp;&nbsp;</td>
			<td width="285px" align="left">&nbsp;&nbsp;</td>			
		</tr>
		<tr>
			<td width="200px" align="right">代收區號：</td>
			<td width="285px" align="left"><s:textfield key="aps002DetailVo.commCenterCode" id="commCenterCode" theme="simple" /></td>
			<td width="200px" align="right">服務人員：</td>
			<td width="285px" align="left"><s:textfield key="aps002DetailVo.handler1code" id="handler1code" theme="simple" /></td>
		</tr>	
	</tbody>
</table>
<table width="970px" cellpadding="0" cellspacing="0" >
	<tr>
		<td align="center">
			<input type="button" value="取消" onclick="javascript:form_submit('goBackU0');"/>
			<input type="button" value="修正資料重新轉入" onclick="javascript:form_submit('btnEditUnusualData');"/>
		</td>
	</tr>
</table>
</s:form>
<!-- form結束 -->
</body>
</html>