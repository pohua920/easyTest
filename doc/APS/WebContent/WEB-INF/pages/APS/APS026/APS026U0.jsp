<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
String path = request.getContextPath();
String title = "要被保人通訊資料比對確認作業";
String image = path + "/" + "images/";
String GAMID = "APS026U0";
String mDescription = "要被保人通訊資料比對確認作業";
String nameSpace = "/aps/026";
%>
<!-- mantis：OTH0106，處理人員：BJ085，需求單編號：OTH0106 要被保人通訊資料比對確認作業 -->
<!--mantis：OTH0184，處理人員：DP0706，需求單編號：APS要被保人通訊地址比對作業增加資料來源(XCHG)-->
<!-- 整頁修改-->
<!-- prpcinsuredContactChk0:原business prpcinsuredContactChk0New:新分流來源xchg -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

<script language="JavaScript">
	$(document).ready(function(){
		//validate
		$("#mainForm").validate({
			isShowLabel:false,
			isAlertLocalMsg:false,
			rules: {
				"prpcinsuredContactChk0.remark":{
				"required":true
				}
			},
			messages: {
				"prpcinsuredContactChk0.remark":{
				"required":"備註欄位必填"
				}
			}
		});
		
		if( $("#isPass").val() == "Y" ){
			$("#passBtn").css("display", "none");
		}
		
	});
	
	function form_submit(type){
		if("pass" === type && confirm("確認通過審核?")){
			 $("#isPass").val("Y");
			 $("#mainForm").attr("action","btnUpdate.action");
			 $("#mainForm").submit();
		}else if("unPass" === type && confirm("確認不通過審核?")){
			$("#isPass").val("N") 
			$("#mainForm").attr("action","btnUpdate.action");
			$("#mainForm").submit();
		}
		
		if("clear" == type){
			if(confirm("請確認是否放棄儲存並返回查詢頁面？")){
				$("#clearForm").submit();
			}
			return false;
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
<s:url action="default" namespace="/aps/026" var="goQuery"/>
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
<s:form theme="simple" action="default" namespace="/aps/026" id="clearForm" name="clearForm"/>
<!--form 開始 -->
<s:form theme="simple" namespace="/aps/026" id="mainForm" name="mainForm">
<s:if test="'prpins' == source">
	<s:hidden name="prpcinsuredContactChk0.no" id="no"/>
	<s:hidden name="prpcinsuredContactChk0.isPass" id="isPass"/>
</s:if>
<s:elseif test="'xchg' == source">
	<s:hidden name="prpcinsuredContactChk0New.no" id="no"/>
	<s:hidden name="prpcinsuredContactChk0New.isPass" id="isPass"/>
</s:elseif>
<s:hidden name="source" id="source" />
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
		<tbody>
			<tr bgcolor="white">
				<td class="MainTdColor" align="center" width="200px">
					<span id="lbSearch"><b>修改作業</b></span></td>
				<td colspan="5" class="image"></td>
			</tr>
		</tbody>
	</table>
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
		<tbody>
			<tr>
				<td width="120px" align="right">報價/要保日期：</td>
				<td width="285px" align="left">
					<s:if test="'prpins' == source">
						<s:property value="prpcinsuredContactChk0.inputdate"/>
					</s:if>
					<s:elseif test="'xchg' == source">
						<s:property value="prpcinsuredContactChk0New.inputdate"/>
					</s:elseif>
					
				</td>
				<td width="285px" align="left"></td>			
				<td width="285px" align="left"></td>			
			</tr>
			<tr>
				<td width="120px" align="right">系統別：</td>
				<td width="285px" align="left">
					<c:out value="${'prpins' == source ? prpcinsuredContactChk0.appcode : prpcinsuredContactChk0New.appcode}"/>
				</td>
			</tr>
			<tr>
				<td width="120px" align="right">出單單位：</td>
				<td width="285px" align="left">
					<c:out value="${'prpins' == source ? prpcinsuredContactChk0.com : prpcinsuredContactChk0New.com}"/>
				</td>
			</tr>
			<tr>
				<td width="120px" align="right">報價號/要保號：</td>
				<td width="285px" align="left">
					<c:out value="${'prpins' == source ? prpcinsuredContactChk0.businessno : prpcinsuredContactChk0New.businessno}"/>
				</td>
			</tr>
		</tbody>
	</table>
	<table class="main_table" id="table3" cellpadding="0" cellspacing="0" width="970px" >
		<tbody>
			<tr align="center">
				<th width="280px">項目關係人</th>
				<th width="400px">業務員/保經代/分支機構</th>
				<th align="center">是否相同</th>
			</tr>
		</tbody>
	</table>                                                                  
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
		<tbody>
			<tr>
				<td width="120px" align="right">類型：</td>
				<td width="285px" align="left">
					<s:if test="'prpins' == source">
						<c:choose>
							<c:when test="${prpcinsuredContactChk0.insuredflag == '1'}">被</c:when>
							<c:when test="${prpcinsuredContactChk0.insuredflag == '2'}">要</c:when>
							<c:otherwise></c:otherwise>
						</c:choose>
					</s:if>
					<s:elseif test="'xchg' == source">
						<c:choose>
							<c:when test="${prpcinsuredContactChk0New.insuredflag == '1'}">被</c:when>
							<c:when test="${prpcinsuredContactChk0New.insuredflag == '2'}">要</c:when>
							<c:otherwise></c:otherwise>
						</c:choose>
					</s:elseif>
					
				</td>			
				<td width="285px" align="left">
					<s:if test="'prpins' == source">
						<c:choose>
						<c:when test="${prpcinsuredContactChk0.contacttype == '1'}">1-業務員</c:when>
						<c:when test="${prpcinsuredContactChk0.contacttype == '9'}">9-公司營業處所</c:when>
						<c:when test="${prpcinsuredContactChk0.contacttype == '2' ||prpcinsuredContactChk0.contacttype == '4'}">2、4-保經代總分支機構</c:when>
						<c:otherwise></c:otherwise>
						</c:choose>
					</s:if>
					<s:elseif test="'xchg' == source">
						<c:choose>
							<c:when test="${prpcinsuredContactChk0New.contacttype == '1'}">1-業務員</c:when>
							<c:when test="${prpcinsuredContactChk0New.contacttype == '9'}">9-公司營業處所</c:when>
							<c:when test="${prpcinsuredContactChk0New.contacttype == '2' ||prpcinsuredContactChk0New.contacttype == '4'}">2、4-保經代總分支機構</c:when>
							<c:otherwise></c:otherwise>
						</c:choose>
					</s:elseif>
					
				</td>			
			</tr>
			<tr>
				<td width="120px" align="right">關係人ID：</td>
				<td width="285px" align="left">
					<c:out value="${'prpins' == source ? prpcinsuredContactChk0.identifynumber : prpcinsuredContactChk0New.identifynumber}"/>
				</td>			
				<td width="285px" align="left">
					<c:out value="${'prpins' == source ? prpcinsuredContactChk0.contactcode : prpcinsuredContactChk0New.contactcode}"/>
				</td>			
			</tr>
			<tr>
				<td width="120px" align="right">關係人姓名：</td>
				<td width="285px" align="left">
					<c:out value="${'prpins' == source ? prpcinsuredContactChk0.insuredname : prpcinsuredContactChk0New.insuredname}"/>
				</td>			
				<td width="285px" align="left">
					<c:out value="${'prpins' == source ? prpcinsuredContactChk0.contactname : prpcinsuredContactChk0New.contactname}"/>
				</td>			
			</tr>
			<tr>
				<td width="120px" align="right">地址型態：</td>
				<td width="285px" align="left">
					<s:if test="'prpins' == source">
						<c:choose>
							<c:when test="${prpcinsuredContactChk0.aType == '00'}">00-通訊地址</c:when>
							<c:when test="${prpcinsuredContactChk0.aType == '10'}">10-戶籍地址</c:when>
							<c:otherwise></c:otherwise>
						</c:choose>
					</s:if>
					<s:elseif test="'xchg' == source">
						<c:choose>
							<c:when test="${prpcinsuredContactChk0New.aType == '00'}">00-通訊地址</c:when>
							<c:when test="${prpcinsuredContactChk0New.aType == '10'}">10-戶籍地址</c:when>
							<c:otherwise></c:otherwise>
						</c:choose>
					</s:elseif>
				</td>			
			</tr>
			<tr class="MainTdColor">
				<td width="120px" align="right">地址：</td>
				<td width="285px" align="left">
					<c:out value="${'prpins' == source ? prpcinsuredContactChk0.a1 : prpcinsuredContactChk0New.a1}"/>
				</td>			
				<td width="285px" align="left">
					<c:out value="${'prpins' == source ? prpcinsuredContactChk0.a2 : prpcinsuredContactChk0New.a2}"/>
				</td>
				<td width="285px" align="center" style="color:red">
					<c:out value="${'prpins' == source ? prpcinsuredContactChk0.c1 : prpcinsuredContactChk0New.c1}"/>
				</td>			
			</tr>
			<tr class="MainTdColor">
				<td width="120px" align="right">手機：</td>
				<td width="285px" align="left">
					<c:out value="${'prpins' == source ? prpcinsuredContactChk0.m1 : prpcinsuredContactChk0New.m1}"/>
				</td>			
				<td width="285px" align="left">
					<c:out value="${'prpins' == source ? prpcinsuredContactChk0.m2 : prpcinsuredContactChk0New.m2}"/>
				</td>	
				<td width="285px" align="center" style="color:red">
					<c:out value="${'prpins' == source ? prpcinsuredContactChk0.c2 : prpcinsuredContactChk0New.c2}"/>
				</td>			
			</tr>
			<tr class="MainTdColor">
				<td width="120px" align="right">聯絡電話：</td>
				<td width="285px" align="left">
					<c:out value="${'prpins' == source ? prpcinsuredContactChk0.p1 : prpcinsuredContactChk0New.p1}"/>
				</td>			
				<td width="285px" align="left">
					<c:out value="${'prpins' == source ? prpcinsuredContactChk0.p2 : prpcinsuredContactChk0New.p2}"/>
				</td>
				<td width="285px" align="center" style="color:red">
					<c:out value="${'prpins' == source ? prpcinsuredContactChk0.c3 : prpcinsuredContactChk0New.c3}"/>
				</td>			
			</tr>
			<tr class="MainTdColor">
				<td width="120px" align="right">Email：</td>
				<td width="285px" align="left">
					<c:out value="${'prpins' == source ? prpcinsuredContactChk0.e1 : prpcinsuredContactChk0New.e1}"/>
				</td>			
				<td width="285px" align="left">
					<c:out value="${'prpins' == source ? prpcinsuredContactChk0.e2 : prpcinsuredContactChk0New.e2}"/>
				</td>			
				<td width="285px" align="center" style="color:red">
					<c:out value="${'prpins' == source ? prpcinsuredContactChk0.c4 : prpcinsuredContactChk0New.c4}"/>
				</td>			
			</tr>
			<tr>
				<td width="120px" align="right">是否通過：</td>
				<td width="285px" align="left">
					<c:out value="${'prpins' == source ? prpcinsuredContactChk0.isPass : prpcinsuredContactChk0New.isPass}"/>
				</td>			
			</tr>
			<tr>
				<td width="120px" align="right">處理備註：</td>
				<td width="285px" align="left">
					<s:if test="'prpins' == source">
						<s:textarea key="prpcinsuredContactChk0.remark" id="remark" maxlength="150" style="width: 250px; height: 60px;"/>
					</s:if>
					<s:elseif test="'xchg' == source">
						<s:textarea key="prpcinsuredContactChk0New.remark" id="remark" maxlength="150" style="width: 250px; height: 60px;"/>
					</s:elseif>
					
				</td>			
			</tr>
			<tr>
				<td width="120px" align="right">建檔人員：</td>
				<td width="285px" align="left">
					<c:out value="${'prpins' == source ? prpcinsuredContactChk0.icreate : prpcinsuredContactChk0New.icreate}"/>
				</td>			
			</tr>
			<tr>
				<td width="120px" align="right">建檔日期：</td>
				<td width="285px" align="left">
					<fmt:formatDate value="${'prpins' == source ? prpcinsuredContactChk0.dcreate : prpcinsuredContactChk0New.dcreate}" pattern='yyyy/MM/dd HH:mm:ss'/>
				</td>			
			</tr>
			<tr>
				<td width="120px" align="right">最後異動人員：</td>
				<td width="285px" align="left">
					<c:out value="${'prpins' == source ? prpcinsuredContactChk0.iupdate : prpcinsuredContactChk0New.iupdate}"/>
				</td>			
			</tr>
			<tr>
				<td width="120px" align="right">最後建檔日期：</td>
				<td width="285px" align="left">
					<fmt:formatDate value="${'prpins' == source ? prpcinsuredContactChk0.dupdate : prpcinsuredContactChk0New.dupdate}" pattern='yyyy/MM/dd HH:mm:ss'/>
				</td>			
			</tr>
			
		</tbody>
	</table>
	<table width="970px" cellpadding="0" cellspacing="0" >
		<tr>
			<td align="center">
				<input value="通過" type="button" id="passBtn" onclick="javascript:form_submit('pass');"/>
				<input value="不通過" type="button" id="unpassBtn" onclick="javascript:form_submit('unPass');"/>
				<input value="取消" type="button" onclick="javascript:form_submit('clear');"/>
			</td>
		</tr>
	</table>
</s:form>
<!-- form結束 -->
</body>
</html>