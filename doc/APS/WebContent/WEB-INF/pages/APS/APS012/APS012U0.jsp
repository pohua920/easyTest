<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/customTagLib" prefix="custom"%>
<%
String path = request.getContextPath();
String title = "中信代理投保通知處理";
String image = path + "/" + "images/";
String GAMID = "APS012U0";
String mDescription = "中信代理投保通知處理";
String nameSpace = "/aps/012";
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
		//mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理
		if("update" == type && checkData()){
			 $("#mainForm").attr("action","btnUpdateFix180.action");
			 $("#mainForm").submit();
		}
		
		if("lnkGoEditO180Fix" == type){
			 $("#mainForm").attr("action","lnkGoEditO180Fix.action");
			 $("#mainForm").submit();
		}
	}
	
	function redirect(){
		$("#oid").val($("#batchOid").val());
		$("#voO180filename").val($("#o180filename").val());
		form_submit("lnkGoEditO180Fix");
	}
	
	/*mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理 start*/
	function checkData(){
		var premium = parseInt($("#premium").val());
		var premium1 = parseInt($("#premium1").val());
		if(premium + premium1 != $("#totalpremiums").val()){
			alert("總保費 需等於 火險保費 + 地震險保費");
			return false;
		}
		return true;
	}
	/*mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理 end*/
	
	function showMsg(msg){
		alert(msg);
	}
	
	var msg = "<%=request.getAttribute("message")%>";
	if('' != msg && 'null' != msg){
		showMsg(msg);
	}
	
</script>
</head>
<s:url action="default" namespace="/aps/012" var="goQuery"/>
<body style="margin:0;text-align:left">
<table cellspacing="1" cellpadding="1" width="970px" border="0">
	<tbody>
		   <tr>
			  <td width="485px">			      
				  <img src="${pageContext.request.contextPath}/images/Help_Icon.gif" border="0">
				  <a href="javascript:void(0)" onclick="redirect()"><img src="${pageContext.request.contextPath}/images/Search_Icon.gif" border="0"></a>
			  </td>
			  <td align="right" width="485px">PGMID：<%=GAMID%></td>
		   </tr>
		   <tr>
			   <td colspan="2" align="center" width="970px"><h3><%=title%></h3></td>
		   </tr>
	</tbody>		
</table>

<!-- clear form -->
<s:form theme="simple" action="btnQueryCancel" namespace="/aps/012" id="clearForm" name="clearForm"/>
<!--form 開始 -->
<s:form theme="simple" namespace="/aps/012" id="mainForm" name="mainForm">
<s:hidden name="editFirCtbcRewFix180.batchOid" id="batchOid"/>
<s:hidden name="editFirCtbcRewFix180.o180filename" id="o180filename"/>
<s:hidden name="editFirCtbcRewFix180.linenum" id="linenum"/>
<s:hidden name="aps012DetailVo.oid" id="oid"/>
<s:hidden name="aps012DetailVo.o180filename" id="voO180filename"/>
<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
	<tbody>
		<tr bgcolor="white">
			<td class="MainTdColor" align="center" width="200px">
				<span id="lbSearch"><b>修改作業</b></span></td>
			<td colspan="3" class="image"></td>
		</tr>
		<tr>
			<td width="200px" align="right">檔案名稱：</td>
			<td width="285px" align="left">
				<s:property value="editFirCtbcRewFix180.o180filename" />
			</td>
			<td width="200px" align="right">批次號碼：</td>
			<td width="285px" align="left">
				<s:property value="editFirCtbcRewFix180.batchOid" />
			</td>			
		</tr>
		<tr>
			<td align="right">行號：</td>
			<td align="left">
				<s:property value="editFirCtbcRewFix180.linenum" />
			</td>
			<td align="right">保單號碼：</td>
			<td align="left">
				<s:property value="editFirCtbcRewFix180.policynumber" />
			</td>	
		</tr>
		<tr>
			<td align="right">保單起日：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.startdate" id="startdate" theme="simple" />
			</td>
			<td align="right">保單迄日：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.enddate" id="enddate" theme="simple" />
			</td>	
		</tr>
		<tr>
			<td align="right">通訊地址：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.mailingaddress" id="mailingaddress" theme="simple" />
			</td>
			<td align="right">不動產所在地：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.address" id="address" theme="simple" />
			</td>	
		</tr>
		<tr>
			<td align="right">火險保額：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.amount" id="amount" theme="simple" />
			</td>
			<td align="right">地震保額：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.amount1" id="amount1" theme="simple" />
			</td>	
		</tr>
		<!-- mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理 start-->
		<tr>
			<td align="right">火險保費：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.premium" id="premium" theme="simple" />
			</td>
			<td align="right">地震保費：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.premium1" id="premium1" theme="simple" />
			</td>	
		</tr>
		<tr>
			<td align="right">總保費：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.totalpremiums" id="totalpremiums" theme="simple" />
			</td>
		</tr>
		<!-- mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理 end-->
		<tr>
			<td align="right">構造：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.material" id="material" theme="simple" />
			</td>
			<td align="right">屋頂：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.roof" id="roof" theme="simple" />
			</td>	
		</tr>
		<tr>
			<td align="right">樓層：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.sumfloors" id="sumfloors" theme="simple" />
			</td>
			<td align="right">建築等級：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.buildinglevelcode" id="buildinglevelcode" theme="simple" />
			</td>	
		</tr>
		<tr>
			<td align="right">建造年份：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.buildyears" id="buildyears" theme="simple" />
			</td>
			<td align="right">不動產坪數：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.buildingsarea" id="buildingsarea" theme="simple" />
			</td>	
		</tr>
		<tr>
			<td align="right">出單費率：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.rates" id="rates" theme="simple" />
			</td>
			<td align="right">自動續保：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.autorenew" id="autorenew" theme="simple" />
			</td>	
		</tr>
		<tr>
			<td align="right">區域中心代碼：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.branchcode" id="branchcode" theme="simple" />
			</td>
			<td align="right"></td>
			<td align="left"></td>	
		</tr>
		<tr>
			<td align="right">要保人1ID：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.identifynumber" id="identifynumber" theme="simple" />
			</td>
			<td align="right">要保人1姓名：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.insuredname" id="insuredname" theme="simple" />
			</td>	
		</tr>
		<tr>
			<td align="right">要保人1生日/設立日：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.birthday" id="birthday" theme="simple" />
			</td>
			<td align="right">要保人1國籍/註冊地中文：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.nationality" id="nationality" theme="simple" />
			</td>	
		</tr>
		<tr>
			<td align="right">要保人1職業別代碼：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.occupationcode" id="occupationcode" theme="simple" />
			</td>
			<td align="right">要保人1職業別名稱：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.occupationname" id="occupationname" theme="simple" />
			</td>	
		</tr>
		<tr>
			<td align="right">要保人1電話：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.phonenumber" id="phonenumber" theme="simple" />
			</td>
			<td align="right"></td>
			<td align="left"></td>	
		</tr>
		<tr>
			<td align="right">要保人2ID：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.identifynumber2" id="identifynumber2" theme="simple" />
			</td>
			<td align="right">要保人2姓名：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.insuredname2" id="insuredname2" theme="simple" />
			</td>	
		</tr>
		<tr>
			<td align="right">要保人2生日/設立日：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.birthday2" id="birthday2" theme="simple" />
			</td>
			<td align="right">要保人2國籍/註冊地中文：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.nationality2" id="nationality2" theme="simple" />
			</td>	
		</tr>
		<tr>
			<td align="right">要保人2職業別代碼：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.occupationcode2" id="occupationcode2" theme="simple" />
			</td>
			<td align="right">要保人2職業別名稱：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.occupationname2" id="occupationname2" theme="simple" />
			</td>	
		</tr>
		<tr>
			<td align="right">要保人2電話：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.phonenumber2" id="phonenumber2" theme="simple" />
			</td>
			<td align="right"></td>
			<td align="left"></td>	
		</tr>
		<tr>
			<td align="right">要保人3ID：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.identifynumber3" id="identifynumber3" theme="simple" />
			</td>
			<td align="right">要保人3姓名：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.insuredname3" id="insuredname3" theme="simple" />
			</td>	
		</tr>
		<tr>
			<td align="right">要保人3生日/設立日：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.birthday3" id="birthday3" theme="simple" />
			</td>
			<td align="right">要保人3國籍/註冊地中文：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.nationality3" id="nationality3" theme="simple" />
			</td>	
		</tr>
		<tr>
			<td align="right">要保人3職業別代碼：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.occupationcode3" id="occupationcode3" theme="simple" />
			</td>
			<td align="right">要保人3職業別名稱：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.occupationname3" id="occupationname3" theme="simple" />
			</td>	
		</tr>
		<tr>
			<td align="right">要保人3電話：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.phonenumber3" id="phonenumber3" theme="simple" />
			</td>
			<td align="right"></td>
			<td align="left"></td>	
		</tr>
		<tr>
			<td align="right">要保人4ID：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.identifynumber4" id="identifynumber4" theme="simple" />
			</td>
			<td align="right">要保人4姓名：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.insuredname4" id="insuredname4" theme="simple" />
			</td>	
		</tr>
		<tr>
			<td align="right">要保人4生日/設立日：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.birthday4" id="birthday4" theme="simple" />
			</td>
			<td align="right">要保人4國籍/註冊地中文：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.nationality4" id="nationality4" theme="simple" />
			</td>	
		</tr>
		<tr>
			<td align="right">要保人4職業別代碼：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.occupationcode4" id="occupationcode4" theme="simple" />
			</td>
			<td align="right">要保人4職業別名稱：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.occupationname4" id="occupationname4" theme="simple" />
			</td>	
		</tr>
		<tr>
			<td align="right">要保人4電話：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.phonenumber4" id="phonenumber4" theme="simple" />
			</td>
			<td align="right"></td>
			<td align="left"></td>	
		</tr>
		<tr>
			<td align="right">要保人5ID：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.identifynumber5" id="identifynumber5" theme="simple" />
			</td>
			<td align="right">要保人5姓名：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.insuredname5" id="insuredname5" theme="simple" />
			</td>	
		</tr>
		<tr>
			<td align="right">要保人5生日/設立日：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.birthday5" id="birthday5" theme="simple" />
			</td>
			<td align="right">要保人5國籍/註冊地中文：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.nationality5" id="nationality5" theme="simple" />
			</td>	
		</tr>
		<tr>
			<td align="right">要保人5職業別代碼：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.occupationcode5" id="occupationcode5" theme="simple" />
			</td>
			<td align="right">要保人5職業別名稱：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.occupationname5" id="occupationname5" theme="simple" />
			</td>	
		</tr>
		<tr>
			<td align="right">要保人5電話：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.phonenumber5" id="phonenumber5" theme="simple" />
			</td>
			<td align="right"></td>
			<td align="left"></td>	
		</tr>
		<tr>
			<td align="right">要保人6ID：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.identifynumber6" id="identifynumber6" theme="simple" />
			</td>
			<td align="right">要保人6姓名：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.insuredname6" id="insuredname6" theme="simple" />
			</td>	
		</tr>
		<tr>
			<td align="right">要保人6生日/設立日：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.birthday6" id="birthday6" theme="simple" />
			</td>
			<td align="right">要保人6國籍/註冊地中文：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.nationality6" id="nationality6" theme="simple" />
			</td>	
		</tr>
		<tr>
			<td align="right">要保人6職業別代碼：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.occupationcode6" id="occupationcode6" theme="simple" />
			</td>
			<td align="right">要保人6職業別名稱：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.occupationname6" id="occupationname6" theme="simple" />
			</td>	
		</tr>
		<tr>
			<td align="right">要保人6電話：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.phonenumber6" id="phonenumber6" theme="simple" />
			</td>
			<td align="right"></td>
			<td align="left"></td>	
		</tr>
		<tr>
			<td align="right">要保人7ID：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.identifynumber7" id="identifynumber7" theme="simple" />
			</td>
			<td align="right">要保人7姓名：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.insuredname7" id="insuredname7" theme="simple" />
			</td>	
		</tr>
		<tr>
			<td align="right">要保人7生日/設立日：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.birthday7" id="birthday7" theme="simple" />
			</td>
			<td align="right">要保人7國籍/註冊地中文：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.nationality7" id="nationality7" theme="simple" />
			</td>	
		</tr>
		<tr>
			<td align="right">要保人7職業別代碼：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.occupationcode7" id="occupationcode7" theme="simple" />
			</td>
			<td align="right">要保人7職業別名稱：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.occupationname7" id="occupationname7" theme="simple" />
			</td>	
		</tr>
		<tr>
			<td align="right">要保人7電話：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.phonenumber7" id="phonenumber7" theme="simple" />
			</td>
			<td align="right"></td>
			<td align="left"></td>	
		</tr>
		<tr>
			<td align="right">被保人1ID：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.holderid" id="holderid" theme="simple" />
			</td>
			<td align="right">被保人1姓名：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.ownername" id="ownername" theme="simple" />
			</td>	
		</tr>
		<tr>
			<td align="right">被保人1生日/設立日：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.ownerbirthday" id="ownerbirthday" theme="simple" />
			</td>
			<td align="right">被保人1國籍/註冊地中文：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.ownernationality" id="ownernationality" theme="simple" />
			</td>	
		</tr>
		<tr>
			<td align="right">被保人1職業別代碼：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.owneroccupationcode" id="owneroccupationcode" theme="simple" />
			</td>
			<td align="right">被保人1職業別名稱：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.owneroccupationname" id="owneroccupationname" theme="simple" />
			</td>	
		</tr>
		<tr>
			<td align="right">被保人1電話：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.ownerphonenumber" id="ownerphonenumber" theme="simple" />
			</td>
			<td align="right"></td>
			<td align="left"></td>	
		</tr>
		<tr>
			<td align="right">被保人2ID：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.holderid2" id="holderid2" theme="simple" />
			</td>
			<td align="right">被保人2姓名：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.ownername2" id="ownername2" theme="simple" />
			</td>	
		</tr>
		<tr>
			<td align="right">被保人2生日/設立日：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.ownerbirthday2" id="ownerbirthday2" theme="simple" />
			</td>
			<td align="right">被保人2國籍/註冊地中文：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.ownernationality2" id="ownernationality2" theme="simple" />
			</td>	
		</tr>
		<tr>
			<td align="right">被保人2職業別代碼：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.owneroccupationcode2" id="owneroccupationcode2" theme="simple" />
			</td>
			<td align="right">被保人2職業別名稱：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.owneroccupationname2" id="owneroccupationname2" theme="simple" />
			</td>	
		</tr>
		<tr>
			<td align="right">被保人2電話：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.ownerphonenumber2" id="ownerphonenumber2" theme="simple" />
			</td>
			<td align="right"></td>
			<td align="left"></td>	
		</tr>
		<tr>
			<td align="right">被保人3ID：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.holderid3" id="holderid3" theme="simple" />
			</td>
			<td align="right">被保人3姓名：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.ownername3" id="ownername3" theme="simple" />
			</td>	
		</tr>
		<tr>
			<td align="right">被保人3生日/設立日：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.ownerbirthday3" id="ownerbirthday3" theme="simple" />
			</td>
			<td align="right">被保人3國籍/註冊地中文：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.ownernationality3" id="ownernationality3" theme="simple" />
			</td>	
		</tr>
		<tr>
			<td align="right">被保人3職業別代碼：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.owneroccupationcode3" id="owneroccupationcode3" theme="simple" />
			</td>
			<td align="right">被保人3職業別名稱：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.owneroccupationname3" id="owneroccupationname3" theme="simple" />
			</td>	
		</tr>
		<tr>
			<td align="right">被保人3電話：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.ownerphonenumber3" id="ownerphonenumber3" theme="simple" />
			</td>
			<td align="right"></td>
			<td align="left"></td>	
		</tr>
		<tr>
			<td align="right">被保人4ID：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.holderid4" id="holderid4" theme="simple" />
			</td>
			<td align="right">被保人4姓名：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.ownername4" id="ownername4" theme="simple" />
			</td>	
		</tr>
		<tr>
			<td align="right">被保人4生日/設立日：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.ownerbirthday4" id="ownerbirthday4" theme="simple" />
			</td>
			<td align="right">被保人4國籍/註冊地中文：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.ownernationality4" id="ownernationality4" theme="simple" />
			</td>	
		</tr>
		<tr>
			<td align="right">被保人4職業別代碼：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.owneroccupationcode4" id="owneroccupationcode4" theme="simple" />
			</td>
			<td align="right">被保人4職業別名稱：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.owneroccupationname4" id="owneroccupationname4" theme="simple" />
			</td>	
		</tr>
		<tr>
			<td align="right">被保人4電話：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.ownerphonenumber4" id="ownerphonenumber4" theme="simple" />
			</td>
			<td align="right"></td>
			<td align="left"></td>	
		</tr>
		<tr>
			<td align="right">被保人5ID：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.holderid5" id="holderid5" theme="simple" />
			</td>
			<td align="right">被保人5姓名：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.ownername5" id="ownername5" theme="simple" />
			</td>	
		</tr>
		<tr>
			<td align="right">被保人5生日/設立日：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.ownerbirthday5" id="ownerbirthday5" theme="simple" />
			</td>
			<td align="right">被保人5國籍/註冊地中文：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.ownernationality5" id="ownernationality5" theme="simple" />
			</td>	
		</tr>
		<tr>
			<td align="right">被保人5職業別代碼：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.owneroccupationcode5" id="owneroccupationcode5" theme="simple" />
			</td>
			<td align="right">被保人5職業別名稱：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.owneroccupationname5" id="owneroccupationname5" theme="simple" />
			</td>	
		</tr>
		<tr>
			<td align="right">被保人5電話：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.ownerphonenumber5" id="ownerphonenumber5" theme="simple" />
			</td>
			<td align="right"></td>
			<td align="left"></td>	
		</tr>
		<tr>
			<td align="right">被保人6ID：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.holderid6" id="holderid6" theme="simple" />
			</td>
			<td align="right">被保人6姓名：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.ownername6" id="ownername6" theme="simple" />
			</td>	
		</tr>
		<tr>
			<td align="right">被保人6生日/設立日：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.ownerbirthday6" id="ownerbirthday6" theme="simple" />
			</td>
			<td align="right">被保人6國籍/註冊地中文：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.ownernationality6" id="ownernationality6" theme="simple" />
			</td>	
		</tr>
		<tr>
			<td align="right">被保人6職業別代碼：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.owneroccupationcode6" id="owneroccupationcode6" theme="simple" />
			</td>
			<td align="right">被保人6職業別名稱：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.owneroccupationname6" id="owneroccupationname6" theme="simple" />
			</td>	
		</tr>
		<tr>
			<td align="right">被保人6電話：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.ownerphonenumber6" id="ownerphonenumber6" theme="simple" />
			</td>
			<td align="right"></td>
			<td align="left"></td>	
		</tr>
		<tr>
			<td align="right">被保人7ID：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.holderid7" id="holderid7" theme="simple" />
			</td>
			<td align="right">被保人7姓名：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.ownername7" id="ownername7" theme="simple" />
			</td>	
		</tr>
		<tr>
			<td align="right">被保人7生日/設立日：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.ownerbirthday7" id="ownerbirthday7" theme="simple" />
			</td>
			<td align="right">被保人7國籍/註冊地中文：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.ownernationality7" id="ownernationality7" theme="simple" />
			</td>	
		</tr>
		<tr>
			<td align="right">被保人7職業別代碼：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.owneroccupationcode7" id="owneroccupationcode7" theme="simple" />
			</td>
			<td align="right">被保人7職業別名稱：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.owneroccupationname7" id="owneroccupationname7" theme="simple" />
			</td>	
		</tr>
		<tr>
			<td align="right">被保人7電話：</td>
			<td align="left">
				<s:textfield key="editFirCtbcRewFix180.ownerphonenumber7" id="ownerphonenumber7" theme="simple" />
			</td>
			<td align="right"></td>
			<td align="left"></td>	
		</tr>
	</tbody>
</table>
<table width="970px" cellpadding="0" cellspacing="0" >
	<tr>
		<td align="center">
			<input type="button" value="更新" onclick="javascript:form_submit('update');"/>
		</td>
	</tr>
</table>
</s:form>
<!-- form結束 -->
</body>
</html>