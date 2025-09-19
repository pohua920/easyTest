<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
String path = request.getContextPath();
String title = "火險保額計算";
String image = path + "/" + "images/";
String GAMID = "TEST001E0";
String mDescription = "火險保額計算";
String nameSpace = "/test/001";
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
<s:form theme="simple" action="btnQueryCancel" namespace="/test/001" id="clearForm" name="clearForm"/>
<!--form 開始 -->
<s:form theme="simple" namespace="/test/001" id="mainForm" name="mainForm">
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
			<td width="200px" align="right">郵遞區號3碼：</td>
			<td width="285px" align="left">
				<s:textfield key="firPremcalcTmp.postcode" id="postcode" maxlength="3" size="3" theme="simple" />
			</td>
			<td width="200px" align="right">建物結構：</td>
			<td width="285px" align="left">
				<s:textfield key="firPremcalcTmp.wallno" id="wallno" maxlength="2" size="2" theme="simple" />
			</td>			
		</tr>
		<tr>
			<td width="200px" align="right">屋頂別：</td>
			<td width="285px" align="left">
				<s:textfield key="firPremcalcTmp.roofno" id="roofno" maxlength="2" size="2" theme="simple" />
			</td>
			<td width="200px" align="right">總樓層數：</td>
			<td width="285px" align="left">
				<s:textfield key="firPremcalcTmp.sumfloors" id="sumfloors" maxlength="2" size="2" theme="simple" />
			</td>			
		</tr>
		<tr>
			<td width="200px" align="right">坪數：</td>
			<td width="285px" align="left">
				<s:textfield key="firPremcalcTmp.buildarea" id="buildarea" maxlength="8" size="8" theme="simple" />
			</td>
			<td width="200px" align="right">每坪裝潢費：</td>
			<td width="285px" align="left">
				<s:textfield key="firPremcalcTmp.decorFee" id="decorFee" maxlength="8" size="8" theme="simple" />
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
		<td width="200px" align="right">地震保額：</td>
		<td width="285px" align="left">
			<s:textfield key="firPremcalcTmp.eqAmt" id="eqAmt" maxlength="12" size="12" theme="simple" /><BR>
		</td>
		<td width="200px" align="right"></td>
		<td width="285px" align="left"></td>	
	</tr>
	<tr>
		<td width="200px" align="right">火險建議保額：</td>
		<td width="285px" align="left">
			<s:textfield key="firPremcalcTmp.fsAmt" id="fsAmt" maxlength="12" size="12" theme="simple" /><BR>
		</td>
		<td width="200px" align="right"></td>
		<td width="285px" align="left"></td>	
	</tr>
	<tr>
		<td width="200px" align="right">火險保額上限：</td>
		<td width="285px" align="left">
			<s:textfield key="firPremcalcTmp.fsMaxAmt" id="fsMaxAmt" maxlength="12" size="12" theme="simple" /><BR>
		</td>
		<td width="200px" align="right"></td>
		<td width="285px" align="left"></td>	
	</tr>
	<tr>
		<td width="200px" align="right">回傳結果：</td>
		<td width="285px" align="left">
			<s:textfield key="firPremcalcTmp.returnType" id="returnType" maxlength="1" size="1" theme="simple" /><BR>
		</td>
		<td width="200px" align="right"></td>
		<td width="285px" align="left"></td>	
	</tr>
	<tr>
		<td width="200px" align="right">回傳訊息：</td>
		<td width="285px" align="left">
			<s:textfield key="firPremcalcTmp.returnMsg" id="returnMsg" maxlength="100" size="100" theme="simple" /><BR>
		</td>
		<td width="200px" align="right"></td>
		<td width="285px" align="left"></td>	
	</tr>
</table>

</s:form>
<!-- form結束 -->
</body>
</html>