<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
String path = request.getContextPath();
String title = "AML洗錢 web service測試";
String image = path + "/" + "images/";
String GAMID = "TEST005E0";
String mDescription = "AML洗錢 web service測試";
String nameSpace = "/test/005";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<script language="JavaScript">
	
			
	function form_submit(type){
		
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
	<s:form theme="simple" action="btnQueryCancel" namespace="/test/005" id="clearForm" name="clearForm"/>
<!--form 開始 -->
<s:form theme="simple" namespace="/test/005" id="mainForm" name="mainForm">
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px">
		<tbody>
			<tr bgcolor="white">
				<td class="MainTdColor" align="center" width="200px">
					<span id="lbSearch"><b>測試作業</b></span></td>
				<td colspan="5" class="image"></td>
			</tr>
			<tr>
				<td width="200px" align="right">業務號(報價單號、要保號、批單號)：</td>
				<td width="300px" align="left">
					<s:textfield key="%{'amlInsuredListVo.businessNo'}" id="%{'amlInsuredListVo.businessNo'}" size="20" maxlength="20" />
				</td>
			</tr>
			<tr>
				<td width="200px" align="right">程式代號-NEWIMS、B2B、B2C：</td>
				<td width="300px" align="left">
					<s:textfield key="%{'amlInsuredListVo.appCode'}" id="%{'amlInsuredListVo.appCode'}" size="20" maxlength="20" />
				</td>
			</tr>
			<tr>
				<td width="200px" align="right">險類代碼：</td>
				<td width="300px" align="left">
					<s:select list="#{'A':'A 車險-任意險', 'B':'B 車險-強制險', 'C1':'C1 傷害', 'M':'M 水險', 'F':'F 火險', 'E':'E 工程險'}" key="%{'amlInsuredListVo.classCode'}" id="%{'amlInsuredListVo.classCode'}" />
				</td>
			</tr>
			<tr>
				<td width="200px" align="right">公司代號：</td>
				<td width="300px" align="left">
					<s:textfield key="%{'amlInsuredListVo.comCode'}" id="%{'amlInsuredListVo.comCode'}" size="2" maxlength="2" />
				</td>
			</tr>
			<tr>
				<td width="200px" align="right">作業類型：</td>
				<td width="300px" align="left">
					<s:select list="#{'':'','Q':'Q-報價', 'T':'T-要保', 'E':'E-批改'}" key="%{'amlInsuredListVo.type'}" id="%{'amlInsuredListVo.type'}" />
				</td>
			</tr>
			<tr>
				<td width="200px" align="right">通路類型：</td>
				<td width="300px" align="left">
					<s:select list="#{'10':'10-業務員', '20':'20-保險經紀人', '30':'30-保險代理人', '40':'40-直接業務'}" key="%{'amlInsuredListVo.channelType'}" id="%{'amlInsuredListVo.channelType'}" />
				</td>
			</tr>
			<tr>
				<td width="200px" align="right">商品風險等級：</td>
				<td width="300px" align="left">
					<s:select list="#{'H':'H-高', 'M':'M-中', 'L':'L-低'}" key="%{'amlInsuredListVo.comLevel'}" id="%{'amlInsuredListVo.comLevel'}"  />
				</td>
			</tr>
			<tr>
				<td width="200px" align="right">保費：</td>
				<td width="300px" align="left">
					<s:textfield key="%{'amlInsuredListVo.prem'}" id="%{'amlInsuredListVo.prem'}" size="15" maxlength="15" />
				</td>
			</tr>
		</tbody>
	</table>
	<fieldset>
	<legend>第一組要保人/關係人資料</legend>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px">
		<tbody>
			<tr>
				<td width="200px" align="right">序號：</td>
				<td width="300px" align="left">
					<s:textfield key="%{'amlInsuredList[0].serialNo'}" id="%{'amlInsuredList[0].serialNo'}" size="2" maxlength="2" value="1"/>
				</td>
			</tr>
			<tr>
				<td width="200px" align="right">身分別：</td>
				<td width="300px" align="left">
					<s:select list="#{'1':'1 - 自然人', '2':'2 - 法人'}" key="%{'amlInsuredList[0].insuredType'}" id="%{'amlInsuredList[0].insuredType'}" />
				</td>
			</tr>
			<tr>
				<td width="200px" align="right">身分類型：</td>
				<td width="300px" align="left">
					<s:select list="#{'2':'2 - 要保人', '1':'1 - 被保人', '9':'9 - 受益人'}" key="%{'amlInsuredList[0].insuredFlag'}" id="%{'amlInsuredList[0].insuredFlag'}" />
				</td>
			</tr>
			<tr>
				<td width="200px" align="right">身分證字號/統編：</td>
				<td width="300px" align="left">
					<s:textfield key="%{'amlInsuredList[0].id'}" id="%{'amlInsuredList[0].id'}" size="10" maxlength="10" />
				</td>
			</tr>
			<tr>
				<td width="200px" align="right">姓名：</td>
				<td width="300px" align="left">
					<s:textfield key="%{'amlInsuredList[0].name'}" id="%{'amlInsuredList[0].name'}" size="50" maxlength="50" />
				</td>
			</tr>
			<tr>
				<td width="200px" align="right">英文姓名：</td>
				<td width="300px" align="left">
					<s:textfield key="%{'amlInsuredList[0].enName'}" id="%{'amlInsuredList[0].enName'}" size="50" maxlength="50" />
				</td>
			</tr>
			<tr>
				<td width="200px" align="right">性別，法人不需填：</td>
				<td width="300px" align="left">
					<s:select list="#{'':'' , 'M':'M - 男', 'F':' F - 女'}" key="%{'amlInsuredList[0].gender'}" id="%{'amlInsuredList[0].gender'}" />
				</td>
			</tr>
			<tr>
				<td width="200px" align="right">生日yyyy-MM-dd 法人不需填：</td>
				<td width="300px" align="left">
					<s:textfield key="%{'amlInsuredList[0].birthday'}" id="%{'amlInsuredList[0].birthday'}" size="10" maxlength="10" />
				</td>
			</tr>
			<tr>
				<td width="200px" align="right">國籍代碼，例如 TW：</td>
				<td width="300px" align="left">
					<s:textfield key="%{'amlInsuredList[0].nationCode'}" id="%{'amlInsuredList[0].nationCode'}" size="10" maxlength="10" />
				</td>
			</tr>
			<tr>
				<td width="200px" align="right">高風險職業 ：</td>
				<td width="300px" align="left">
					<s:select list="#{'':'' , 'Y':'Y - 是', 'N':'N - 否'}" key="%{'amlInsuredList[0].dangerOccupation'}" id="%{'amlInsuredList[0].dangerOccupation'}" size="1" maxlength="1" />
				</td>
			</tr>
			<tr>
				<td width="200px" align="right">公司成立日期，自然人不用填：</td>
				<td width="300px" align="left">
					<s:textfield key="%{'amlInsuredList[0].estDate'}" id="%{'amlInsuredList[0].estDate'}" size="10" maxlength="10" />
				</td>
			</tr>
			<tr>
				<td width="200px" align="right">上市櫃公司，自然人不用填：</td>
				<td width="300px" align="left">
					<s:select list="#{'':'' , 'Y':'Y - 是', 'N':'N - 否'}" key="%{'amlInsuredList[0].listedCabinetCompany'}" id="%{'amlInsuredList[0].listedCabinetCompany'}" size="1" maxlength="1" />
				</td>
			</tr>
		</tbody>
	</table>
	</fieldset>
	<fieldset>
	<legend>第二組要保人/關係人資料</legend>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px">
		<tbody>
			<tr>
				<td width="200px" align="right">序號：</td>
				<td width="300px" align="left">
					<s:textfield key="%{'amlInsuredList[1].serialNo'}" id="%{'amlInsuredList[1].serialNo'}" size="2" maxlength="2" value="2"/>
				</td>
			</tr>
			<tr>
				<td width="200px" align="right">身分別：</td>
				<td width="300px" align="left">
					<s:select list="#{'1':'1 - 自然人', '2':'2 - 法人'}" key="%{'amlInsuredList[1].insuredType'}" id="%{'amlInsuredList[1].insuredType'}" />
				</td>
			</tr>
			<tr>
				<td width="200px" align="right">身分類型：</td>
				<td width="300px" align="left">
					<s:select list="#{'2':'2 - 要保人', '1':'1 - 被保人', '9':'9 - 受益人'}" key="%{'amlInsuredList[1].insuredFlag'}" id="%{'amlInsuredList[1].insuredFlag'}" />
				</td>
			</tr>
			<tr>
				<td width="200px" align="right">身分證字號/統編：</td>
				<td width="300px" align="left">
					<s:textfield key="%{'amlInsuredList[1].id'}" id="%{'amlInsuredList[1].id'}" size="10" maxlength="10" />
				</td>
			</tr>
			<tr>
				<td width="200px" align="right">姓名：</td>
				<td width="300px" align="left">
					<s:textfield key="%{'amlInsuredList[1].name'}" id="%{'amlInsuredList[1].name'}" size="50" maxlength="50" />
				</td>
			</tr>
			<tr>
				<td width="200px" align="right">英文姓名：</td>
				<td width="300px" align="left">
					<s:textfield key="%{'amlInsuredList[1].enName'}" id="%{'amlInsuredList[1].enName'}" size="50" maxlength="50" />
				</td>
			</tr>
			<tr>
				<td width="200px" align="right">性別，法人不需填：</td>
				<td width="300px" align="left">
					<s:select list="#{'':'' , 'M':'M - 男', 'F':' F - 女'}" key="%{'amlInsuredList[1].gender'}" id="%{'amlInsuredList[1].gender'}" />
				</td>
			</tr>
			<tr>
				<td width="200px" align="right">生日yyyy-MM-dd 法人不需填：</td>
				<td width="300px" align="left">
					<s:textfield key="%{'amlInsuredList[1].birthday'}" id="%{'amlInsuredList[1].birthday'}" size="10" maxlength="10" />
				</td>
			</tr>
			<tr>
				<td width="200px" align="right">國籍代碼，例如 TW：</td>
				<td width="300px" align="left">
					<s:textfield key="%{'amlInsuredList[1].nationCode'}" id="%{'amlInsuredList[1].nationCode'}" size="10" maxlength="10" />
				</td>
			</tr>
			<tr>
				<td width="200px" align="right">高風險職業：</td>
				<td width="300px" align="left">
					<s:select list="#{'':'' , 'Y':'Y - 是', 'N':'N - 否'}" key="%{'amlInsuredList[1].dangerOccupation'}" id="%{'amlInsuredList[1].dangerOccupation'}" size="1" maxlength="1" />
				</td>
			</tr>
			<tr>
				<td width="200px" align="right">公司成立日期，自然人不用填：</td>
				<td width="300px" align="left">
					<s:textfield key="%{'amlInsuredList[1].estDate'}" id="%{'amlInsuredList[1].estDate'}" size="10" maxlength="10" />
				</td>
			</tr>
			<tr>
				<td width="200px" align="right">上市櫃公司，自然人不用填：</td>
				<td width="300px" align="left">
					<s:select list="#{'':'' , 'Y':'Y - 是', 'N':'N - 否'}" key="%{'amlInsuredList[1].listedCabinetCompany'}" id="%{'amlInsuredList[1].listedCabinetCompany'}" size="1" maxlength="1" />
				</td>
			</tr>
		</tbody>
	</table>
	</fieldset>
	<fieldset>
	<legend>第三組要保人/關係人資料</legend>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px">
		<tbody>
			<tr>
				<td width="200px" align="right">序號：</td>
				<td width="300px" align="left">
					<s:textfield key="%{'amlInsuredList[2].serialNo'}" id="%{'amlInsuredList[2].serialNo'}" size="2" maxlength="2" value="3"/>
				</td>
			</tr>
			<tr>
				<td width="200px" align="right">身分別：</td>
				<td width="300px" align="left">
					<s:select list="#{'1':'1 - 自然人', '2':'2 - 法人'}" key="%{'amlInsuredList[2].insuredType'}" id="%{'amlInsuredList[2].insuredType'}" />
				</td>
			</tr>
			<tr>
				<td width="200px" align="right">身分類型：</td>
				<td width="300px" align="left">
					<s:select list="#{'2':'2 - 要保人', '1':'1 - 被保人', '9':'9 - 受益人'}" key="%{'amlInsuredList[2].insuredFlag'}" id="%{'amlInsuredList[2].insuredFlag'}" />
				</td>
			</tr>
			<tr>
				<td width="200px" align="right">身分證字號/統編：</td>
				<td width="300px" align="left">
					<s:textfield key="%{'amlInsuredList[2].id'}" id="%{'amlInsuredList[2].id'}" size="10" maxlength="10" />
				</td>
			</tr>
			<tr>
				<td width="200px" align="right">姓名：</td>
				<td width="300px" align="left">
					<s:textfield key="%{'amlInsuredList[2].name'}" id="%{'amlInsuredList[2].name'}" size="50" maxlength="50" />
				</td>
			</tr>
			<tr>
				<td width="200px" align="right">英文姓名：</td>
				<td width="300px" align="left">
					<s:textfield key="%{'amlInsuredList[2].enName'}" id="%{'amlInsuredList[2].enName'}" size="50" maxlength="50" />
				</td>
			</tr>
			<tr>
				<td width="200px" align="right">性別，法人不需填：</td>
				<td width="300px" align="left">
					<s:select list="#{'':'' , 'M':'M - 男', 'F':' F - 女'}" key="%{'amlInsuredList[2].gender'}" id="%{'amlInsuredList[2].gender'}" />
				</td>
			</tr>
			<tr>
				<td width="200px" align="right">生日yyyy-MM-dd 法人不需填：</td>
				<td width="300px" align="left">
					<s:textfield key="%{'amlInsuredList[2].birthday'}" id="%{'amlInsuredList[2].birthday'}" size="10" maxlength="10" />
				</td>
			</tr>
			<tr>
				<td width="200px" align="right">國籍代碼，例如 TW：</td>
				<td width="300px" align="left">
					<s:textfield key="%{'amlInsuredList[2].nationCode'}" id="%{'amlInsuredList[2].nationCode'}" size="10" maxlength="10" />
				</td>
			</tr>
			<tr>
				<td width="200px" align="right">高風險職業：</td>
				<td width="300px" align="left">
					<s:select list="#{'':'' , 'Y':'Y - 是', 'N':'N - 否'}" key="%{'amlInsuredList[2].dangerOccupation'}" id="%{'amlInsuredList[2].dangerOccupation'}" />
				</td>
			</tr>
			<tr>
				<td width="200px" align="right">公司成立日期，自然人不用填：</td>
				<td width="300px" align="left">
					<s:textfield key="%{'amlInsuredList[2].estDate'}" id="%{'amlInsuredList[2].estDate'}" size="10" maxlength="10" />
				</td>
			</tr>
			<tr>
				<td width="200px" align="right">上市櫃公司，自然人不用填：</td>
				<td width="300px" align="left">
					<s:select list="#{'':'' , 'Y':'Y - 是', 'N':'N - 否'}" key="%{'amlInsuredList[2].listedCabinetCompany'}" id="%{'amlInsuredList[2].listedCabinetCompany'}"/>
				</td>
			</tr>
		</tbody>
	</table>
	</fieldset>
	<fieldset>
	<legend>第四組要保人/關係人資料</legend>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px">
		<tbody>
			<tr>
				<td width="200px" align="right">序號：</td>
				<td width="300px" align="left">
					<s:textfield key="%{'amlInsuredList[3].serialNo'}" id="%{'amlInsuredList[3].serialNo'}" size="2" maxlength="2" value="4"/>
				</td>
			</tr>
			<tr>
				<td width="200px" align="right">身分別-1.自然人 2.法人：</td>
				<td width="300px" align="left">
					<s:select list="#{'1':'1 - 自然人', '2':'2 - 法人'}" key="%{'amlInsuredList[3].insuredType'}" id="%{'amlInsuredList[3].insuredType'}" />
				</td>
			</tr>
			<tr>
				<td width="200px" align="right">身分類型：</td>
				<td width="300px" align="left">
					<s:select list="#{'2':'2 - 要保人', '1':'1 - 被保人', '9':'9 - 受益人'}" key="%{'amlInsuredList[3].insuredFlag'}" id="%{'amlInsuredList[3].insuredFlag'}" />
				</td>
			</tr>
			<tr>
				<td width="200px" align="right">身分證字號/統編：</td>
				<td width="300px" align="left">
					<s:textfield key="%{'amlInsuredList[3].id'}" id="%{'amlInsuredList[3].id'}" size="10" maxlength="10" />
				</td>
			</tr>
			<tr>
				<td width="200px" align="right">姓名：</td>
				<td width="300px" align="left">
					<s:textfield key="%{'amlInsuredList[3].name'}" id="%{'amlInsuredList[3].name'}" size="50" maxlength="50" />
				</td>
			</tr>
			<tr>
				<td width="200px" align="right">英文姓名：</td>
				<td width="300px" align="left">
					<s:textfield key="%{'amlInsuredList[3].enName'}" id="%{'amlInsuredList[3].enName'}" size="50" maxlength="50" />
				</td>
			</tr>
			<tr>
				<td width="200px" align="right">性別，法人不需填：</td>
				<td width="300px" align="left">
					<s:select list="#{'':'' , 'M':'M - 男', 'F':' F - 女'}" key="%{'amlInsuredList[3].gender'}" id="%{'amlInsuredList[3].gender'}" />
				</td>
			</tr>
			<tr>
				<td width="200px" align="right">生日yyyy-MM-dd 法人不需填：</td>
				<td width="300px" align="left">
					<s:textfield key="%{'amlInsuredList[3].birthday'}" id="%{'amlInsuredList[3].birthday'}" size="10" maxlength="10" />
				</td>
			</tr>
			<tr>
				<td width="200px" align="right">國籍代碼，例如 TW：</td>
				<td width="300px" align="left">
					<s:textfield key="%{'amlInsuredList[3].nationCode'}" id="%{'amlInsuredList[3].nationCode'}" size="10" maxlength="10" />
				</td>
			</tr>
			<tr>
				<td width="200px" align="right">高風險職業 ：</td>
				<td width="300px" align="left">
					<s:select list="#{'':'' , 'Y':'Y - 是', 'N':'N - 否'}" key="%{'amlInsuredList[3].dangerOccupation'}" id="%{'amlInsuredList[3].dangerOccupation'}" />
				</td>
			</tr>
			<tr>
				<td width="200px" align="right">公司成立日期，自然人不用填：</td>
				<td width="300px" align="left">
					<s:textfield key="%{'amlInsuredList[3].estDate'}" id="%{'amlInsuredList[3].estDate'}" size="10" maxlength="10" />
				</td>
			</tr>
			<tr>
				<td width="200px" align="right">上市櫃公司，自然人不用填：</td>
				<td width="300px" align="left">
					<s:select list="#{'':'' , 'Y':'Y - 是', 'N':'N - 否'}" key="%{'amlInsuredList[3].listedCabinetCompany'}" id="%{'amlInsuredList[3].listedCabinetCompany'}"/>
				</td>
			</tr>
		</tbody>
	</table>
	</fieldset>
	<fieldset>
	<legend>第四組要保人/關係人資料</legend>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px">
		<tbody>
			<tr>
				<td width="200px" align="right">序號：</td>
				<td width="300px" align="left">
					<s:textfield key="%{'amlInsuredList[4].serialNo'}" id="%{'amlInsuredList[4].serialNo'}" size="2" maxlength="2" value="5"/>
				</td>
			</tr>
			<tr>
				<td width="200px" align="right">身分別：</td>
				<td width="300px" align="left">
					<s:select list="#{'1':'1 - 自然人', '2':'2 - 法人'}" key="%{'amlInsuredList[4].insuredType'}" id="%{'amlInsuredList[4].insuredType'}" />
				</td>
			</tr>
			<tr>
				<td width="200px" align="right">身分類型：</td>
				<td width="300px" align="left">
					<s:select list="#{'2':'2 - 要保人', '1':'1 - 被保人', '9':'9 - 受益人'}" key="%{'amlInsuredList[4].insuredFlag'}" id="%{'amlInsuredList[4].insuredFlag'}" />
				</td>
			</tr>
			<tr>
				<td width="200px" align="right">身分證字號/統編：</td>
				<td width="300px" align="left">
					<s:textfield key="%{'amlInsuredList[4].id'}" id="%{'amlInsuredList[4].id'}" size="10" maxlength="10" />
				</td>
			</tr>
			<tr>
				<td width="200px" align="right">姓名：</td>
				<td width="300px" align="left">
					<s:textfield key="%{'amlInsuredList[4].name'}" id="%{'amlInsuredList[4].name'}" size="50" maxlength="50" />
				</td>
			</tr>
			<tr>
				<td width="200px" align="right">英文姓名：</td>
				<td width="300px" align="left">
					<s:textfield key="%{'amlInsuredList[4].enName'}" id="%{'amlInsuredList[4].enName'}" size="50" maxlength="50" />
				</td>
			</tr>
			<tr>
				<td width="200px" align="right">性別，法人不需填：</td>
				<td width="300px" align="left">
					<s:select list="#{'':'' , 'M':'M - 男', 'F':' F - 女'}" key="%{'amlInsuredList[4].gender'}" id="%{'amlInsuredList[4].gender'}" />
				</td>
			</tr>
			<tr>
				<td width="200px" align="right">生日yyyy-MM-dd 法人不需填：</td>
				<td width="300px" align="left">
					<s:textfield key="%{'amlInsuredList[4].birthday'}" id="%{'amlInsuredList[4].birthday'}" size="10" maxlength="10" />
				</td>
			</tr>
			<tr>
				<td width="200px" align="right">國籍代碼，例如 TW：</td>
				<td width="300px" align="left">
					<s:textfield key="%{'amlInsuredList[4].nationCode'}" id="%{'amlInsuredList[4].nationCode'}" size="10" maxlength="10" />
				</td>
			</tr>
			<tr>
				<td width="200px" align="right">高風險職業 Y/N：</td>
				<td width="300px" align="left">
					<s:select list="#{'':'' , 'Y':'Y - 是', 'N':'N - 否'}" key="%{'amlInsuredList[4].dangerOccupation'}" id="%{'amlInsuredList[4].dangerOccupation'}" />
				</td>
			</tr>
			<tr>
				<td width="200px" align="right">公司成立日期，自然人不用填：</td>
				<td width="300px" align="left">
					<s:textfield key="%{'amlInsuredList[4].estDate'}" id="%{'amlInsuredList[4].estDate'}" size="10" maxlength="10" />
				</td>
			</tr>
			<tr>
				<td width="200px" align="right">上市櫃公司，自然人不用填：</td>
				<td width="300px" align="left">
					<s:select list="#{'':'' , 'Y':'Y - 是', 'N':'N - 否'}" key="%{'amlInsuredList[4].listedCabinetCompany'}" id="%{'amlInsuredList[4].listedCabinetCompany'}"/>
				</td>
			</tr>
		</tbody>
	</table>
	</fieldset>
	<p>
	<table width="970px" cellpadding="0" cellspacing="0" >
		<tr>
			<td align="center">
				<input type="button" value="送出 " onclick="javascript:form_submit('query');"/>&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" value="清除" onclick="javascript:form_submit('cancel');"/>
			</td>
		</tr>
	</table>
	<p>
	<s:if test="amlResponseVo != null">
		<table cellspacing="0" rules="all" border="1" id="tbGroup" style="width:970px;border-collapse:collapse" bordercolor="#000066">
			<tr align="Center" class="gridheader">
				<td bgcolor="#87CEFA" width="55">業務號</td>
				<td bgcolor="#87CEFA" width="180">作業狀態</td>
				<td bgcolor="#87CEFA" width="180">拒限保</td>		
				<td bgcolor="#87CEFA" width="180">名單檢測</td>	
				<td bgcolor="#87CEFA" width="191">風險評級</td>	
			</tr>
			<tr onmouseover="GridOver(this)" onmouseout="GridOut(this)">
				<td align="left" bgcolor="#EFEFEF" width="76">
					<s:property value="amlResponseVo.bussinessNo" />
				</td>
				<td align="left" bgcolor="#EFEFEF" width="76">
					<s:property value="amlResponseVo.workStatus" />
				</td>
				<td align="left" bgcolor="#EFEFEF" width="76">
					<s:property value="amlResponseVo.refuseLimiteInsurance" />
				</td>
				<td align="left" bgcolor="#EFEFEF" width="76">
					<s:property value="amlResponseVo.listDetection" />
				</td>
				<td align="left" bgcolor="#EFEFEF" width="76">
					<s:property value="amlResponseVo.riskRating" />
				</td>
			</tr>
		</table>
	</s:if>
	<p>
	<s:if test="amlResponseVo != null">
		<table cellspacing="0" rules="all" border="1" id="tbGroup" style="width:970px;border-collapse:collapse" bordercolor="#000066">
			<tr align="Center" class="gridheader">
				<td bgcolor="#87CEFA" width="55">序號</td>
				<td bgcolor="#87CEFA" width="55">編號</td>
				<td bgcolor="#87CEFA" width="55">身分證字號/統編</td>
				<td bgcolor="#87CEFA" width="55">作業狀態</td>
				<td bgcolor="#87CEFA" width="55">拒限保</td>		
				<td bgcolor="#87CEFA" width="55">名單檢測</td>	
				<td bgcolor="#87CEFA" width="55">風險評級</td>
				<td bgcolor="#87CEFA" width="55">返回碼retCode</td>
				<td bgcolor="#87CEFA" width="55">最後判定結果decType</td>
				<td bgcolor="#87CEFA" width="55">最後命中狀態decState</td>
				<td bgcolor="#87CEFA" width="55">參考風險等級level</td>
				<td bgcolor="#87CEFA" width="55">風險審查狀態review</td>
			</tr>
			<s:iterator id="map" value="amlResponseVo.detailList" var="map">
			<tr onmouseover="GridOver(this)" onmouseout="GridOut(this)">
				<td align="left" bgcolor="#EFEFEF" width="76">
					<s:property value="#map.serialNo" />
				</td>
				<td align="left" bgcolor="#EFEFEF" width="76">
					<s:property value="#map.cio" />
				</td>
				<td align="left" bgcolor="#EFEFEF" width="76">
					<s:property value="#map.id" />
				</td>
				<td align="left" bgcolor="#EFEFEF" width="76">
					<s:property value="#map.workStatus" />
				</td>
				<td align="left" bgcolor="#EFEFEF" width="76">
					<s:property value="#map.refuseLimiteInsurance" />
				</td>
				<td align="left" bgcolor="#EFEFEF" width="76">
					<s:property value="#map.listDetection" />
				</td>
				<td align="left" bgcolor="#EFEFEF" width="76">
					<s:property value="#map.riskRating" />
				</td>
				<td align="left" bgcolor="#EFEFEF" width="76">
					<s:property value="#map.retCode" />
				</td>
				<td align="left" bgcolor="#EFEFEF" width="76">
					<s:property value="#map.decType" />
				</td>
				<td align="left" bgcolor="#EFEFEF" width="76">
					<s:property value="#map.decState" />
				</td>
				<td align="left" bgcolor="#EFEFEF" width="76">
					<s:property value="#map.level" />
				</td>
				<td align="left" bgcolor="#EFEFEF" width="76">
					<s:property value="#map.review" />
				</td>
			</tr>
			</s:iterator>
		</table>	
	</s:if>
		欄位值說明：
		<table cellspacing="0" rules="all" border="1" id="tbGroup" style="width:970px;border-collapse:collapse" bordercolor="#000066">
			<tr>
				<td>
					<table cellspacing="0" rules="all" border="1" id="tbGroup" style="width:970px;border-collapse:collapse" bordercolor="#000066">
						<tr>
							<td colspan="2">作業狀態</td>
						</tr>
						<tr>
							<td>00</td><td>不執行</td>
						</tr>
						<tr>
							<td>01</td><td>待再查詢</td>
						</tr>
						<tr>
							<td>02</td><td>查詢中</td>
						</tr>
						<tr>
							<td>03</td><td>收到回覆拒保</td>
						</tr>
						<tr>
							<td>04</td><td>收到回覆可承保</td>
						</tr>
						<tr>
							<td>05</td><td>查詢異常</td>
						</tr>
						<tr>
							<td>06</td><td>查詢超時</td>
						</tr>
						<tr>
							<td>07</td><td>人工審核註記</td>
						</tr>
						<tr>
							<td>08</td><td>人工審核完成
d>
						</tr>
					</table>			
					<table cellspacing="0" rules="all" border="1" id="tbGroup" style="width:970px;border-collapse:collapse" bordercolor="#000066">
						<tr>
							<td colspan="2">拒限保</td>
						</tr>
						<tr>
							<td>00</td><td>拒限保未命中</td>
						</tr>
						<tr>
							<td>01</td><td>拒限保命中未判定</td>
						</tr>
						<tr>
							<td>02</td><td>拒限保命中已判定True</td>
						</tr>
						<tr>
							<td>03</td><td>拒限保命中已判定False</td>
						</tr>
					</table>
					<table cellspacing="0" rules="all" border="1" id="tbGroup" style="width:970px;border-collapse:collapse" bordercolor="#000066">
						<tr>
							<td colspan="2">名單檢測</td>
						</tr>
						<tr>
							<td>01</td><td>名單檢測未命中</td>
						</tr>
						<tr>
							<td>02</td><td>名單檢測命中未判定</td>
						</tr>
						<tr>
							<td>03</td><td>名單檢測命中已判定</td>
						</tr>
					</table>
					<table cellspacing="0" rules="all" border="1" id="tbGroup" style="width:970px;border-collapse:collapse" bordercolor="#000066">
						<tr>
							<td colspan="2">風險評級</td>
						</tr>
						<tr>
							<td>00</td><td>高風險未處理</td>
						</tr>
						<tr>
							<td>01</td><td>高風險已處理</td>
						</tr>
						<tr>
							<td>02</td><td>中風險未處理</td>
						</tr>
						<tr>
							<td>03</td><td>中風險已處理</td>
						</tr>
						<tr>
							<td>04</td><td>低風險</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	<p>
</s:form>
</body>
</html>