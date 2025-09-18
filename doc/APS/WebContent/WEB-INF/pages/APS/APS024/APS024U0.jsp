<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
String path = request.getContextPath();
String title = "火險地址維護作業";
String image = path + "/" + "images/";
String GAMID = "APS024U0";
String mDescription = "火險地址維護作業";
String nameSpace = "/aps/024";
%>
<!-- mantis：FIR0357，處理人員：BJ085，需求單編號：FIR0357 火險地址維護作業 -->
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
				"firAddrCkdata.oriAddress":{
				"required":true
				},
				"firAddrCkdata.stdAddress":{
				"required":true
				},
				"firAddrCkdata.addrStructure":{
				"required":true,
				"checkStructure":""
				},
				"firAddrCkdata.addrSumfloors":{
				"required":true,
				"checkSumfloors":""
				/*mantis：FIR609，處理人員：BJ085，需求單編號：FIR609 地址維護作業調整-FIR0521住火標的物地址正規化-FIR0357地址維護作業start*/
				},
				"firAddrCkdata.buildyears":{
				"required":true,
				"checkBuildyears":""
				/*mantis：FIR609，處理人員：BJ085，需求單編號：FIR609 地址維護作業調整-FIR0521住火標的物地址正規化-FIR0357地址維護作業end*/
				}
			},
			messages: {
				"firAddrCkdata.oriAddress":{
				"required":"請輸入原始地址"
				},
				"firAddrCkdata.stdAddress":{
				"required":"請輸入轉換後地址"
				},
				"firAddrCkdata.addrStructure":{
				"required":"請輸入建築等級",
				"checkStructure":"僅能輸入正整數且不可為0"
				},
				"firAddrCkdata.addrSumfloors":{
				"required":"請輸入總樓層數!",
				"checkSumfloors":"僅能輸入正整數且不可為0"
				/*mantis：FIR609，處理人員：BJ085，需求單編號：FIR609 地址維護作業調整-FIR0521住火標的物地址正規化-FIR0357地址維護作業start*/
				},
				"firAddrCkdata.buildyears":{
				"required":"請輸入建築年度!",
				"checkBuildyears":"僅能輸入正整數且不可為0"
				/*mantis：FIR609，處理人員：BJ085，需求單編號：FIR609 地址維護作業調整-FIR0521住火標的物地址正規化-FIR0357地址維護作業end*/
				}
			}
		});
	});
	
	$.validator.addMethod("checkStructure", function(value,element,param) { 
		return checkNum($("#addrStructure").val());						
	},"");
	$.validator.addMethod("checkSumfloors", function(value,element,param) { 
		return checkNum($("#addrSumfloors").val());						
	},"");
	/*mantis：FIR609，處理人員：BJ085，需求單編號：FIR609 地址維護作業調整-FIR0521住火標的物地址正規化-FIR0357地址維護作業 start*/
	$.validator.addMethod("checkBuildyears", function(value,element,param) { 
		return checkNum($("#buildyears").val());						
	},"");
	/*mantis：FIR609，處理人員：BJ085，需求單編號：FIR609 地址維護作業調整-FIR0521住火標的物地址正規化-FIR0357地址維護作業 end*/	
	function checkNum(num){
		var regex = /^\d+$/;
		if(!regex.test(num) || num == 0 ){
			return false;
		}
		return true;
	}
	
	function form_submit(type){
		if("update" == type){
			/* mantis：FIR0521，處理人員：CC009，需求單編號：FIR0521 火險地址維護作業增加處理正規化功能 start */
			if (!$("#mainForm").valid()) {
				alert("請確認必填欄位是否輸入。");
				$.unblockUI();
				return;
			}
			if($('#formattedResult').val() != 'Y' && $('#formattedResult').val() != 'U'){
				if(confirm("本筆資料地址正規化結果不通過，本次若有調整正規化相關欄位，請確認「正規化結果」是否已改成「U人工修正」。"
						+ "若未修改，選擇「取消」回到原畫面。若已修改，請選擇「確定」進行存檔。")){
					$("#mainForm").attr("action","btnUpdate.action");
				 	$("#mainForm").submit();
				}
			}else {
				$("#mainForm").attr("action","btnUpdate.action");
			 	$("#mainForm").submit();
			}
			/* mantis：FIR0521，處理人員：CC009，需求單編號：FIR0521 火險地址維護作業增加處理正規化功能 end */
		}
		
		if("clear" == type){
			if(confirm("請確認是否放棄存檔並返回查詢頁面？")){
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
<s:url action="default" namespace="/aps/024" var="goQuery"/>
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
<s:form theme="simple" action="default" namespace="/aps/024" id="clearForm" name="clearForm"/>
<!--form 開始 -->
<s:form theme="simple" namespace="/aps/024" id="mainForm" name="mainForm">
	<!-- mantis：FIR0521，處理人員：CC009，需求單編號：FIR0521 火險地址維護作業增加處理正規化功能 -->
	<s:hidden key="filter.queryType" id="queryType" />
	<table class="MainBodyColor" id="table3" cellpadding="0" cellspacing="0" width="970px" >
		<tbody>
			<tr bgcolor="white">
				<td class="MainTdColor" align="center" width="200px">
					<span id="lbSearch"><b>修改作業</b></span></td>
				<td colspan="5" class="image"></td>
			</tr>
			<tr>
				<td width="120px" align="right">序號：</td>
				<td width="285px" align="left">
					<s:property value="firAddrCkdata.oid"/>
					<s:hidden key="firAddrCkdata.oid" id="oid"></s:hidden>
				</td>
			</tr>
			<tr>
				<td width="120px" align="right">保單號碼：</td>
				<td width="285px" align="left">
					<s:property value="firAddrCkdata.policyno"/>
				</td>			
				<td width="120px" align="right">簽單日期：</td>
				<td width="285px" align="left">
					<s:property value="firAddrCkdata.underwriteenddate"/>
				</td>			
			</tr>
			<tr>
				<td width="120px" align="right">批單號碼：</td>
				<td width="285px" align="left">
					<s:property value="firAddrCkdata.endorseno"/>
				</td>			
				<td width="120px" align="right">地址序號：</td>
				<td width="285px" align="left">
					<s:property value="firAddrCkdata.addrNo"/>
				</td>			
			</tr>
			<tr>
				<td width="120px" align="right"><font color="#FF0000">*</font>原始地址：</td>
				<td width="285px" align="left">
					<s:textfield key="firAddrCkdata.oriAddress" id="oriAddress" theme="simple" size="80"/>
				</td>			
			</tr>
			<tr>
				<td width="120px" align="right"><font color="#FF0000">*</font>轉換後地址：</td>
				<td width="285px" align="left">
					<s:textfield key="firAddrCkdata.stdAddress" id="stdAddress" theme="simple" size="80"/>
				</td>			
			</tr>
			<tr>
				<td width="120px" align="right"><font color="#FF0000">*</font>建築等級：</td>
				<td width="285px" align="left">
					<s:textfield key="firAddrCkdata.addrStructure" id="addrStructure" size="5" maxlength="5" theme="simple" />
				</td>			
				<td width="120px" align="right"><font color="#FF0000">*</font>總樓層數：</td>
				<td width="285px" align="left">
					<s:textfield key="firAddrCkdata.addrSumfloors" id="addrSumfloors" size="5" maxlength="5" theme="simple" />
				</td>			
			</tr>
			<!-- mantis：FIR609，處理人員：BJ085，需求單編號：FIR609 地址維護作業調整-FIR0521住火標的物地址正規化-FIR0357地址維護作業 start -->
			<tr>
				<td width="120px" align="right">外牆：</td>
				<td width="285px" align="left">
					<s:textfield key="firAddrCkdata.wallmaterial" id="wallmaterial" size="5" maxlength="5" theme="simple" />
				</td>			
				<td width="120px" align="right">屋頂：</td>
				<td width="285px" align="left">
					<s:textfield key="firAddrCkdata.roofmaterial" id="roofmaterial" size="5" maxlength="5" theme="simple" />
				</td>			
			</tr>
			<tr>
				<td width="120px" align="right"><font color="#FF0000">*</font>建築年度：</td>
				<td width="285px" align="left">
					<s:textfield key="firAddrCkdata.buildyears" id="buildyears" size="5" maxlength="5" theme="simple" />
				</td>			
			</tr>
			<!-- mantis：FIR609，處理人員：BJ085，需求單編號：FIR609 地址維護作業調整-FIR0521住火標的物地址正規化-FIR0357地址維護作業 end -->
			<tr>
				<td width="120px" align="right"><font color="#FF0000">*</font>生效註記：</td>
				<td width="285px" align="left">
					<s:select key="firAddrCkdata.validFlag" id="validFlag" theme="simple" list="#{'0':'無效', '1':'有效'}"/>
				</td>			
			</tr>
			<tr>
				<td width="120px" align="right">備註：</td>
				<td width="285px" align="left">
					<s:textfield key="firAddrCkdata.remark" id="remark" theme="simple" size="80"/>
				</td>			
			</tr>
			<!-- mantis：FIR0521，處理人員：CC009，需求單編號：FIR0521 火險地址維護作業增加處理正規化功能 start -->
			<tr>
				<td width="120px" align="right">正規化狀態碼：</td>
				<td width="285px" align="left">
					<s:property value="firAddrCkdata.formattedCode"/>
				</td>			
				<td width="120px" align="right">正規化結果：</td>
				<td width="285px" align="left">
				<s:if test='"Y" == firAddrCkdata.formattedResult'>
					<s:select key="firAddrCkdata.formattedResult" id="formattedResult" theme="simple" list="#{'Y':'成功'}" disabled="true"/>
				</s:if>
				<s:else>
					<s:select key="firAddrCkdata.formattedResult" id="formattedResult" theme="simple" list="#{'N':'失敗', 'F':'服務無回應', 'U':'人工修正'}"/>
				</s:else>
				</td>			
			</tr>
			<tr>
				<td width="120px" align="right">正規化訊息：</td>
				<td width="285px" align="left">
					<s:property value="firAddrCkdata.formattedMsg"/>
				</td>			
			</tr>
			<s:if test='"Y" == firAddrCkdata.formattedResult'>
			<tr>
				<td width="120px" align="right">正規化地址：</td>
				<td width="285px" align="left" colspan="6">
					<!-- mantis：FIR609，處理人員：BJ085，需求單編號：FIR609 地址維護作業調整-FIR0521住火標的物地址正規化-FIR0357地址維護作業 start -->
					<s:textfield key="firAddrCkdata.formattedZip" id="formattedZip" theme="simple" size="10" readonly="true" cssClass="txtLabel"/>
					<s:textfield key="firAddrCkdata.formattedAddr" id="formattedAddr" theme="simple" size="100" readonly="true" cssClass="txtLabel"/>
					<!-- mantis：FIR609，處理人員：BJ085，需求單編號：FIR609 地址維護作業調整-FIR0521住火標的物地址正規化-FIR0357地址維護作業 end -->
				</td>			
			</tr>
			<tr>
				<td width="120px" align="right">縣市：</td>
				<td width="285px" align="left">
					<!-- mantis：FIR609，處理人員：BJ085，需求單編號：FIR609 地址維護作業調整-FIR0521住火標的物地址正規化-FIR0357地址維護作業 -->
					<s:textfield key="firAddrCkdata.formattedCity" id="formattedCity" theme="simple" readonly="true" cssClass="txtLabel"/>
				</td>			
				<td width="120px" align="right">鄉鎮區：</td>
				<td width="285px" align="left">
					<!-- mantis：FIR609，處理人員：BJ085，需求單編號：FIR609 地址維護作業調整-FIR0521住火標的物地址正規化-FIR0357地址維護作業 -->
					<s:textfield key="firAddrCkdata.formattedDistrict" id="formattedDistrict" theme="simple" readonly="true" cssClass="txtLabel"/>
				</td>			
			</tr>
			<tr>
				<td width="120px" align="right">村里鄰：</td>
				<td width="285px" align="left">
					<!-- mantis：FIR609，處理人員：BJ085，需求單編號：FIR609 地址維護作業調整-FIR0521住火標的物地址正規化-FIR0357地址維護作業 -->
					<s:textfield key="firAddrCkdata.formattedNeighborhood" id="formattedNeighborhood" theme="simple" readonly="true" cssClass="txtLabel"/>
				</td>			
				<td width="120px" align="right">街路段：</td>
				<td width="285px" align="left">
					<!-- mantis：FIR609，處理人員：BJ085，需求單編號：FIR609 地址維護作業調整-FIR0521住火標的物地址正規化-FIR0357地址維護作業 -->
					<s:textfield key="firAddrCkdata.formattedRoad" id="formattedRoad" theme="simple" readonly="true" cssClass="txtLabel"/>
				</td>			
			</tr>
			<tr>
				<td width="120px" align="right">巷：</td>
				<td width="285px" align="left">
					<!-- mantis：FIR609，處理人員：BJ085，需求單編號：FIR609 地址維護作業調整-FIR0521住火標的物地址正規化-FIR0357地址維護作業 -->
					<s:textfield key="firAddrCkdata.formattedLane" id="formattedLane" theme="simple" readonly="true" cssClass="txtLabel"/>
				</td>			
				<td width="120px" align="right">弄：</td>
				<td width="285px" align="left">
					<!-- mantis：FIR609，處理人員：BJ085，需求單編號：FIR609 地址維護作業調整-FIR0521住火標的物地址正規化-FIR0357地址維護作業 -->
					<s:textfield key="firAddrCkdata.formattedLane" id="formattedLane" theme="simple" readonly="true" cssClass="txtLabel"/>
				</td>			
			</tr>
			<tr>
				<td width="120px" align="right">衖：</td>
				<td width="285px" align="left">
					<!-- mantis：FIR609，處理人員：BJ085，需求單編號：FIR609 地址維護作業調整-FIR0521住火標的物地址正規化-FIR0357地址維護作業 -->
					<s:textfield key="firAddrCkdata.formattedAlley2" id="formattedAlley2" theme="simple" readonly="true" cssClass="txtLabel"/>
				</td>			
				<td width="120px" align="right">號：</td>
				<td width="285px" align="left">
					<!-- mantis：FIR609，處理人員：BJ085，需求單編號：FIR609 地址維護作業調整-FIR0521住火標的物地址正規化-FIR0357地址維護作業 -->
					<s:textfield key="firAddrCkdata.formattedNo" id="formattedNo" theme="simple" readonly="true" cssClass="txtLabel"/>
				</td>			
			</tr>
			<tr>
				<td width="120px" align="right">樓：</td>
				<td width="285px" align="left">
					<!-- mantis：FIR609，處理人員：BJ085，需求單編號：FIR609 地址維護作業調整-FIR0521住火標的物地址正規化-FIR0357地址維護作業 -->
					<s:textfield key="firAddrCkdata.formattedFloor" id="formattedFloor" theme="simple" readonly="true" cssClass="txtLabel"/>
				</td>			
				<td width="120px" align="right">其他：</td>
				<td width="285px" align="left">
					<!-- mantis：FIR609，處理人員：BJ085，需求單編號：FIR609 地址維護作業調整-FIR0521住火標的物地址正規化-FIR0357地址維護作業 -->
					<s:textfield key="firAddrCkdata.formattedOther" id="formattedOther" theme="simple" readonly="true" cssClass="txtLabel"/>
				</td>			
			</tr>
			</s:if>
			<s:else>
			<tr>
				<td width="120px" align="right">正規化地址：</td>
				<td width="285px" align="left" colspan="6">
					<s:textfield key="firAddrCkdata.formattedZip" id="formattedZip" theme="simple" size="10"/>
					<s:textfield key="firAddrCkdata.formattedAddr" id="formattedAddr" theme="simple" size="100"/>
				</td>			
			</tr>
			<tr>
				<td width="120px" align="right">縣市：</td>
				<td width="285px" align="left">
					<s:textfield key="firAddrCkdata.formattedCity" id="formattedCity" theme="simple"/>
				</td>			
				<td width="120px" align="right">鄉鎮區：</td>
				<td width="285px" align="left">
					<s:textfield key="firAddrCkdata.formattedDistrict" id="formattedDistrict" theme="simple"/>
				</td>			
			</tr>
			<tr>
				<td width="120px" align="right">村里鄰：</td>
				<td width="285px" align="left">
					<s:textfield key="firAddrCkdata.formattedNeighborhood" id="formattedNeighborhood" theme="simple"/>
				</td>			
				<td width="120px" align="right">街路段：</td>
				<td width="285px" align="left">
					<s:textfield key="firAddrCkdata.formattedRoad" id="formattedRoad" theme="simple"/>
				</td>			
			</tr>
			<tr>
				<td width="120px" align="right">巷：</td>
				<td width="285px" align="left">
					<s:textfield key="firAddrCkdata.formattedLane" id="formattedLane" theme="simple"/>
				</td>			
				<td width="120px" align="right">弄：</td>
				<td width="285px" align="left">
					<s:textfield key="firAddrCkdata.formattedAlley1" id="formattedAlley1" theme="simple"/>
				</td>			
			</tr>
			<tr>
				<td width="120px" align="right">衖：</td>
				<td width="285px" align="left">
					<s:textfield key="firAddrCkdata.formattedAlley2" id="formattedAlley2" theme="simple"/>
				</td>			
				<td width="120px" align="right">號：</td>
				<td width="285px" align="left">
					<s:textfield key="firAddrCkdata.formattedNo" id="formattedNo" theme="simple"/>
				</td>			
			</tr>
			<tr>
				<td width="120px" align="right">樓：</td>
				<td width="285px" align="left">
					<s:textfield key="firAddrCkdata.formattedFloor" id="formattedFloor" theme="simple"/>
				</td>			
				<td width="120px" align="right">其他：</td>
				<td width="285px" align="left">
					<s:textfield key="firAddrCkdata.formattedOther" id="formattedOther" theme="simple"/>
				</td>			
			</tr>
			</s:else>
			<!-- mantis：FIR0521，處理人員：CC009，需求單編號：FIR0521 火險地址維護作業增加處理正規化功能 start -->
			<tr>
				<td width="120px" align="right">建檔人員：</td>
				<td width="285px" align="left">
					<s:property value="firAddrCkdata.icreate"/>
				</td>			
				<td width="120px" align="right">建檔日期：</td>
				<td width="285px" align="left">
					<fmt:formatDate value='${firAddrCkdata.dcreate}' pattern='yyyy/MM/dd HH:mm:ss'/>
				</td>			
			</tr>
			<tr>
				<td width="120px" align="right">最後異動人員：</td>
				<td width="285px" align="left">
					<s:property value="firAddrCkdata.iupdate"/>
				</td>			
				<td width="120px" align="right">最後異動日期：</td>
				<td width="285px" align="left">
					<fmt:formatDate value='${firAddrCkdata.dupdate}' pattern='yyyy/MM/dd HH:mm:ss'/>
				</td>			
			</tr>
		</tbody>
	</table>
	<table width="970px" cellpadding="0" cellspacing="0" >
		<tr>
			<td align="center">
				<input value="存檔" type="button" onclick="javascript:form_submit('update');"/>
				<input value="取消" type="button" onclick="javascript:form_submit('clear');"/>
			</td>
		</tr>
	</table>
</s:form>
<!-- form結束 -->
</body>
</html>