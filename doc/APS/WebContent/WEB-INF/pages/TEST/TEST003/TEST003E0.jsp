<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
String path = request.getContextPath();
String title = "輔助平台資料重導測試";
String image = path + "/" + "images/";
String GAMID = "TEST003E0";
String mDescription = "火險保費計算";
String nameSpace = "/test/003";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<script language="JavaScript">
	
			
	function form_submit(type){
		
		if("query" == type){
			
			//<!-- mantis：OTH0078，處理人員：BJ085，需求單編號：OTH0078 更名相關-APS、CWP、SYS系統中有台壽保產險、tlg、網域名稱更為新名稱 -->
			var testData = '{"address":"許昌街17號18樓之1","arbtyInsOrNot":"Y","birthDate":"19750627","bizKind":"02","brandModel":"07000000","capacityLimit":"5.0","capacityUnit":"C","carriageType":"C","checkValue":"","city":"台北市","collectionType":"1","compBeginDate":"2019110112","compEndDate":"2020110112","compInsOrNot":"Y","compPremium":"1149","compRepName":"代表負責人姓名","compTypeNo":"12","cylinder":"2359.0","district":"中正區","email":"bi086@ctbcins.com","engineNo":"4J12RE01069","feeGrade":"1","gender":"M","handledID":"AJ124","handledName":"經手人姓名","idType":"1","insuredCarType":"22","insuredCardNo":"AB12345678","makeYearMonth":"201501","mobile":"0912345678","orgGrade":"1","orgIssueDate":"20150101","ownerId":"A123456789","ownerName":"被保險人姓名ABC","paperOrNot":"Y","phone":"0212345678","plateNo":"AGV-7897","producerName":"業務員姓名","producerRegNo":"A12C456789","proposalDate":"20191031153020","siteNo":"13135641","stsSelNo":"181109C8526024","tradingCompCode":"J12051-349129-******","tradingCompName":"大旺保險代理人有限公司","tradingCompUnitedNo":"13135641","txNo":"182019101400001","violateExtraFee":"3600","violateTimes":"2","zipCode":"10001"}';
			alert("testData = " + testData);
			//var testData = '12345';
			//var testData = '"address":"許昌街17號18樓之1"';
			var path = $('#serverUrl').val();
			window.location.href = path + "?dataStr=" + testData ;
		}
		if("cancel" == type){
			$("#clearForm").submit();
		}		
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

<!-- clear form -->
<s:form theme="simple" action="btnQueryCancel" namespace="/test/003" id="clearForm" name="clearForm"/>
<!--form 開始 -->
<s:form theme="simple" namespace="/test/003" id="mainForm" name="mainForm">
<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px">
	<tbody>
		<tr bgcolor="white">
			<td class="MainTdColor" align="center" width="200px">
				<span id="lbSearch"><b>查詢作業</b></span></td>
			<td colspan="5" class="image"></td>
		</tr>
		<tr>
			<td width="200px" align="right">轉址連結：</td>
			<td width="300px" align="left">
				<s:textfield key="serverUrl" id="serverUrl" maxlength="100" size="100" theme="simple" value="http://192.168.190.32:8980/tripinsptmWeb/action/crTradevan/getData.action" />
			</td>
		</tr>
		
	</tbody>
</table>
<table width="970px" cellpadding="0" cellspacing="0" >
	<tr>
		<td align="center">
			<input type="button" value="送出 " onclick="javascript:form_submit('query');"/>&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" value="清除" onclick="javascript:form_submit('cancel');"/>
		</td>
	</tr>
</table>
</s:form>
<!-- form結束 -->
</body>
</html>