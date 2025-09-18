<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
String path = request.getContextPath();
String title = "火險保費計算";
String image = path + "/" + "images/";
String GAMID = "TEST002E0";
String mDescription = "火險保費計算";
String nameSpace = "/test/002";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<script language="JavaScript">
	
			
	function form_submit(type){
		 //$("label").html('');
		 if("query" == type){
			 $("#mainForm").attr("action","btnQuery.action");
			 $("#mainForm").submit();
		}
		if("cancel" == type){
			$("#clearForm").submit();
		}		
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

<!-- clear form -->
<s:form theme="simple" action="btnQueryCancel" namespace="/test/002" id="clearForm" name="clearForm"/>
<!--form 開始 -->
<s:form theme="simple" namespace="/test/002" id="mainForm" name="mainForm">
<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px">
	<tbody>
		<tr bgcolor="white">
			<td class="MainTdColor" align="center" width="200px">
				<span id="lbSearch"><b>查詢作業</b></span></td>
			<td colspan="5" class="image"></td>
		</tr>
		<tr>
			<td width="200px" align="right">費率基準日 ：</td>
			<td width="285px" align="left">
				<s:textfield key="calcDate" id="calcDate" maxlength="8" size="8" theme="simple" />YYYYMMDD
			</td>
			<td width="200px" align="right">通路別：</td>
			<td width="285px" align="left">
				<s:textfield key="firPremcalcTmp.channelType" id="channelType" maxlength="2" size="2" theme="simple" />
			</td>			
		</tr>
		<tr>
			<td width="200px" align="right">險種代碼(riskcode)</td>
			<td width="285px" align="left">
				<s:textfield key="firPremcalcTmpdtl.riskcode" id="riskcode" maxlength="8" size="8" theme="simple" value="F02"/>
			</td>
			<td width="200px" align="right">險別代碼(kindcode)</td>
			<td width="285px" align="left">
				<s:textfield key="firPremcalcTmpdtl.kindcode" id="kindcode" maxlength="8" size="8" theme="simple" />
			</td>			
		</tr>
		<tr>
			<td width="200px" align="right">參數類型(paraType)：</td>
			<td width="285px" align="left">
				<s:textfield key="firPremcalcTmpdtl.paraType" id="paraType" maxlength="2" size="2" theme="simple" />
			</td>
			<td width="200px" align="right"></td>
			<td width="285px" align="left">
			</td>			
		</tr>
		<tr>
			<td width="200px" align="right">參數01(para01)：</td>
			<td width="285px" align="left">
				<s:textfield key="firPremcalcTmpdtl.para01" id="para01" maxlength="8" size="8" theme="simple" />
			</td>
			<td width="200px" align="right">參數02(para02)：</td>
			<td width="285px" align="left">
				<s:textfield key="firPremcalcTmpdtl.para02" id="para02" maxlength="8" size="8" theme="simple" />
			</td>			
		</tr>
		<tr>
			<td width="200px" align="right">參數03(para03)：</td>
			<td width="285px" align="left">
				<s:textfield key="firPremcalcTmpdtl.para03" id="para03" maxlength="8" size="8" theme="simple" />
			</td>
			<td width="200px" align="right">參數04(para04)：</td>
			<td width="285px" align="left">
				<s:textfield key="firPremcalcTmpdtl.para04" id="para04" maxlength="8" size="8" theme="simple" />
			</td>			
		</tr>
		<tr>
			<td width="200px" align="right">參數05(para05)：</td>
			<td width="285px" align="left">
				<s:textfield key="firPremcalcTmpdtl.para05" id="para05" maxlength="30" size="30" theme="simple" />
			</td>
			<td width="200px" align="right">參數06(para06)：</td>
			<td width="285px" align="left">
				<s:textfield key="firPremcalcTmpdtl.para06" id="para06" maxlength="30" size="30" theme="simple" />
			</td>			
		</tr>
	</tbody>
</table>
<table width="970px" cellpadding="0" cellspacing="0" >
	<tr>
		<td align="center">
			<input type="button" value="查詢 " onclick="javascript:form_submit('query');"/>&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" value="清除" onclick="javascript:form_submit('cancel');"/>
		</td>
	</tr>
</table>
<table width="970px" cellpadding="0" cellspacing="0" border="0">
	<tr>
		<td width="400px" align="right">純保費：</td>
		<td width="285px" align="left">
			<s:textfield key="firPremcalcTmpdtl.purePremium" id="purePremium" maxlength="12" size="12" theme="simple" /><BR>
		</td>
		<td width="400px" align="right"></td>
		<td width="285px" align="left"></td>	
	</tr>
	<tr>
		<td width="400px" align="right">出單保費：</td>
		<td width="285px" align="left">
			<s:textfield key="firPremcalcTmpdtl.premium" id="premium" maxlength="12" size="12" theme="simple" /><BR>
		</td>
		<td width="400px" align="right"></td>
		<td width="285px" align="left"></td>	
	</tr>
	<tr>
		<td width="400px" align="right">附加費用率：</td>
		<td width="285px" align="left">
			<s:textfield key="firPremcalcTmpdtl.dangerGrade" id="dangerGrade" maxlength="12" size="12" theme="simple" /><BR>
		</td>
		<td width="400px" align="right"></td>
		<td width="285px" align="left"></td>	
	</tr>
	<tr>
		<td width="400px" align="right">出單費率：</td>
		<td width="285px" align="left">
			<s:textfield key="firPremcalcTmpdtl.premRate" id="premRate" maxlength="12" size="12" theme="simple" /><BR>
		</td>
		<td width="400px" align="right"></td>
		<td width="285px" align="left"></td>	
	</tr>
	<tr>
		<td width="400px" align="right">純保費率：</td>
		<td width="285px" align="left">
			<s:textfield key="firPremcalcTmpdtl.purePremRate" id="purePremRate" maxlength="12" size="12" theme="simple" /><BR>
		</td>
		<td width="400px" align="right"></td>
		<td width="285px" align="left"></td>	
	</tr>
	<tr>
		<td width="400px" align="right">火險基本費率：</td>
		<td width="285px" align="left">
			<s:textfield key="firPremcalcTmp.fireBaserate" id="fireBaserate" maxlength="12" size="12" theme="simple" /><BR>
		</td>
		<td width="400px" align="right"></td>
		<td width="285px" align="left"></td>	
	</tr>
	<tr>
		<td width="400px" align="right">火險高樓加費費率：</td>
		<td width="285px" align="left">
			<s:textfield key="firPremcalcTmp.fireHigh" id="fireHigh" maxlength="12" size="12" theme="simple" /><BR>
		</td>
		<td width="400px" align="right"></td>
		<td width="285px" align="left"></td>	
	</tr>
	<tr>
		<td width="400px" align="right">火險營業加費費率：</td>
		<td width="285px" align="left">
			<s:textfield key="firPremcalcTmp.fireOperating" id="fireOperating" maxlength="12" size="12" theme="simple" /><BR>
		</td>
		<td width="400px" align="right"></td>
		<td width="285px" align="left"></td>	
	</tr>
	<tr>
		<td width="400px" align="right">建物等級：</td>
		<td width="285px" align="left">
			<s:textfield key="firPremcalcTmp.fireStructure" id="fireStructure" maxlength="12" size="12" theme="simple" /><BR>
		</td>
		<td width="400px" align="right"></td>
		<td width="285px" align="left"></td>	
	</tr>
	<tr>
		<td width="400px" align="right">回傳結果：</td>
		<td width="285px" align="left">
			<s:textfield key="firPremcalcTmp.returnType" id="returnType" maxlength="1" size="1" theme="simple" /><BR>
		</td>
		<td width="400px" align="right"></td>
		<td width="285px" align="left"></td>	
	</tr>
	<tr>
		<td width="400px" align="right">回傳訊息：</td>
		<td width="285px" align="left">
			<s:textfield key="firPremcalcTmp.returnMsg" id="returnMsg" maxlength="100" size="100" theme="simple" /><BR>
		</td>
		<td width="400px" align="right"></td>
		<td width="285px" align="left"></td>	
	</tr>
</table>

</s:form>
<!-- form結束 -->
</body>
</html>