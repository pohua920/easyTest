<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
String path = request.getContextPath();
String title = "難字案件維護";
String image = path + "/" + "images/";
String GAMID = "APS015U0";
String mDescription = "難字案件維護";
String nameSpace = "/aps/015";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<style>
h4 {
   width: 100%; 
   text-align: center; 
   border-bottom: 1px solid #000; 
   line-height: 0.1em;
   margin: 10px 0 20px; 
} 

h4 span { 
    background:#efefef;; 
    padding:0 10px; 
}
</style>
<script language="JavaScript">
	$(document).ready(function(){

	});
			
	function form_submit(type){
		 //$("label").html('');
		if("update" == type){
			if(validateForm(type)){
				$("#mainForm").attr("action","btnUpdate.action");
				$("#mainForm").submit();
			}
		}
		/*mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理start*/
		if("clear" == type){
			if(confirm("請確認是否放棄修改並返回查詢頁面？")){
				$("#clearForm").submit();
			}
			return false;
		}
		/*mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理end*/
	}
	
	function validateForm(type){
		if("update" == type){
			var errorMsg = "";
			var dataType = $("#datatype").val();
			var ownerId = $("#ownerid").val();
			var theName = $("#thename").val();
			if(ownerId.length <= 0){
				errorMsg += "資料內容為必填欄位;";
			}
			if(dataType == "1" && theName.length <= 0){
				errorMsg += "資料型態：身份證字號，請輸入姓名;";
			}
			if(dataType == "2" && theName.length > 0){
				errorMsg += "資料型態：保單號碼，不需輸入姓名;";
			}
			if(errorMsg.length > 0){
				alert(errorMsg);
				return false;
			}
		}
		return true;
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
<s:url action="default" namespace="/aps/015" var="goQuery"/>
<body style="margin:0;text-align:left">
<table cellspacing="1" cellpadding="1" width="970px" border="0">
	<tbody>
		   <tr>
			  <td width="485px">			      
				  <img src="${pageContext.request.contextPath}/images/Help_Icon.gif" border="0">
				  <a href='${goQuery}'><img src="${pageContext.request.contextPath}/images/Search_Icon.gif" border="0"></a>
			  </td>
			  <td align="right" width="485px">PGMID：<%=GAMID%></td>
		   </tr>
		   <tr>
			   <td colspan="2" align="center" width="970px"><h3><%=title%></h3></td>
		   </tr>
	</tbody>		
</table>

<!-- clear form -->
<!-- clear form -->
<s:form theme="simple" action="lnkGoQuery" namespace="/aps/015" id="clearForm" name="clearForm"/>
<!--form 開始 -->
<s:form theme="simple" namespace="/aps/015" id="mainForm" name="mainForm">
<s:hidden name="firCtbcRewNoshowword.oid" id="oid"/>
<s:hidden name="firCtbcRewNoshowword.datatype" id="datatype"/>
<!-- mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理 -->
<s:hidden name="oriOwnerid" id="oriOwnerid" value="%{firCtbcRewNoshowword.ownerid}"/>
<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
	<tbody>
		<tr bgcolor="white">
			<td class="MainTdColor" align="center" width="200px">
				<span id="lbSearch"><b>修改作業</b></span></td>
			<td colspan="3" class="image"></td>
		</tr>
		<tr>
			<td width="200px" align="right">資料型態：</td>
			<td width="285px" align="left">
				<c:if test="${firCtbcRewNoshowword.datatype == '1'}">身份證字號</c:if>
				<c:if test="${firCtbcRewNoshowword.datatype == '2'}">保單號碼</c:if>
			</td>
			<td width="200px" align="right"></td>
			<td width="285px" align="left"></td>			
		</tr>
		<tr>
			<td width="200px" align="right">資料內容：</td>
			<td width="285px" align="left">
				<s:textfield key="firCtbcRewNoshowword.ownerid" id="ownerid" theme="simple" />
			</td>
			<td width="200px" align="right">姓名：</td>
			<td width="285px" align="left">
				<s:textfield key="firCtbcRewNoshowword.thename" id="thename" theme="simple" />
			</td>			
		</tr>
		<!-- mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理 start -->
		<tr>
			<td width="200px" align="right">建立人員：</td>
			<td width="285px" align="left">
				<s:property value="firCtbcRewNoshowword.icreate"/>
			</td>
			<td width="200px" align="right">建立時間：</td>
			<td width="285px" align="left">
				<fmt:formatDate value='${firCtbcRewNoshowword.dcreate}' pattern='yyyy/MM/dd HH:mm:ss'/>
			</td>
		</tr>
		<tr>
			<td width="200px" align="right">修改人員：</td>
			<td width="285px" align="left">
				<s:property value="firCtbcRewNoshowword.iupdate"/>
			</td>
			<td width="200px" align="right">修改時間：</td>
			<td width="285px" align="left">
				<fmt:formatDate value='${firCtbcRewNoshowword.dupdate}' pattern='yyyy/MM/dd HH:mm:ss'/>
			</td>
		</tr>
		<!-- mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理 end -->
	</tbody>
</table>
<table width="970px" cellpadding="0" cellspacing="0" >
	<tr>
		<td align="center">
			<input type="button" value="修改" onclick="javascript:form_submit('update');"/>
			<input type="button" value="取消" onclick="javascript:form_submit('clear');"/>
		</td>
	</tr>
</table>
</s:form>
<!-- form結束 -->
</body>
</html>