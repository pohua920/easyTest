<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="rc" uri="http://util.one.sinosoft.com/RCDate"%>
<%@ taglib uri="/WEB-INF/undwrt-app.tld" prefix="app"%>
<%@ page import="com.sinosoft.sysframework.reference.AppConfig"%>

<html>
<head>
<link rel="stylesheet" type="text/css" href="/undwrt/css/Standard1.css">
<script type='text/javascript' src="/undwrt/widgets/yui/yahoo-dom-event/yahoo-dom-event.js"></script>
<script type='text/javascript' src="/undwrt/widgets/yui/yahoo/yahoo-min.js"></script>
<script type='text/javascript' src="/undwrt/widgets/yui/yahoo/yahoo.js"></script>
<script type='text/javascript' src="/undwrt/widgets/yui/utilities/utilities.js"></script>
<script type='text/javascript' src="/undwrt/widgets/yui2/treeview/treeview-min.js"></script>
<script type='text/javascript' src="/undwrt/widgets/yui2/json/json-min.js"></script>
	<script src="/undwrt/common/js/WfLogQuery.js"></script>
	<script src="/undwrt/common/js/My97DatePicker/WdatePicker.js"></script>
		<jsp:include page="/common/meta_css.jsp" />
	 <script language=Javascript>
    function mySubmit()
    {
        if(document.getElementById("flag").value!=null&&document.getElementById("flag").value=="3"){
        	fm.action="/undwrt/common/blackMaintenance.do?type=updateList";
        }else{
        	if(document.getElementById("identifyNumber").value==null&&document.getElementById("identifyNumber").value==""){
        		alert("證件號碼不能為空");
        	}  
        }
        fm.submit(); //提交
     }
    function myQuery(){
    	fm.action="/undwrt/common/blackMaintenance.do?type=query";
    	fm.submit();
    }
    function changeCode(filed){
       document.getElementById("blackListCode").value=filed.value;
    }
    function updateBlackList(param){
    	fm.action="/undwrt/common/blackMaintenance.do?param="+param+"&type=update";
    	fm.submit();
    }
    function deleteBlackList(param){
    	fm.action="/undwrt/common/blackMaintenance.do?param="+param+"&type=delete";
    	fm.submit();
    }
    function checkInsuredType(param){
		if(param=='1'){
			document.getElementsByName("identifyType")[0].children[0].selected=true;
		}else{
			document.getElementsByName("identifyType")[0].children[3].selected=true;

		}
    }
    function queryBankInfoInput(field1,field,valueField){
    	debugger;
       	//获取银行代号前三位
       	var BankCode = document.getElementById(field1).value;
       	//alert("BankCode::::"+BankCode);
       	if(!BankCode){		
       		errorMessage("银行前三位代碼不能為空");/*"银行前三位代码不能为空！"*/
       		return;
       }
       	//获取银行代号后四位
       	var BankCodeDetail = document.getElementById(field.id).value;
       	//alert("BankCodeDetail::::"+BankCodeDetail);
       	if(!BankCodeDetail){
       		errorMessage(getResourceValue("銀行后四位代碼不能為空"));/*银行后四位代码不能为空！*/
       		return;
       }
       	//拼接银行全代码
       	var LastBankCode = BankCode+BankCodeDetail;
       	//alert("LastBankCode::::"+LastBankCode);
       	//調用查詢函數，通過银行代码查询银行名称
       	 var callback ={
       			 success:function(res){
       				 debugger;
       	    	 var prpDBankInfolist = [];
	       	    	prpDBankInfolist =YAHOO.lang.JSON.parse(res.responseText);
	       	    	 //給銀行名稱欄位賦值
	       	    	if(prpDBankInfolist.msg!=""&&prpDBankInfolist.msg!=null){
		       	      	 var BankNameField = document.getElementById(valueField);
		       	    	 BankNameField.value = prpDBankInfolist.msg;
					}
       	  
       	         },
       		     failure:function(res){
       			 errorMessage(getResourceValue("輸入的銀行代碼不存在，請檢查輸入"));//"輸入的銀行代碼不存在，請檢查輸入！"
       			 //errorMessage("輸入的銀行代碼不存在，請檢查輸入！");
       		     } 				 			
       		};
       		var transaction = YAHOO.util.Connect.asyncRequest('POST','/undwrt/common/blackMaintenance.do?LastBankCode='+LastBankCode+"&type=queryCode", callback, null);
       }
    function getBlackList(param){
    	url="/undwrt/common/blackMaintenance.do?param="+param+"&type=list";
    	window.open(url);
    }
    function  showSpan(){
    	document.getElementById("span_ItemKind-I-Context_[0]").style.display="";
    }
    function closeItemKind_Context(){
    	document.getElementById("span_ItemKind-I-Context_[0]").style.display="none"
    }
  </script>
</head>
<body class="interface">
<form action="/undwrt/common/blackMaintenance.do?type=save" method=post name=fm>
<table class="fix_table" cellpadding="5" cellspacing="1" id="customerIdv"  
	style="display: block">
	  	<tr>
	  	
	  	<input  type="hidden"  id="flag" name="flag" value="<%=request.getParameter("flag") %>" >
		<td class="title">客戶類型 ：<span style="color: red;">*</span></td>
		<td class="input" >
	        <INPUT type=radio name="insuredType" <%
			 if("1".equals(request.getAttribute("only"))){
			 %>disabled=true <%}%> value="1" onclick="checkInsuredType('1')" checked >個人 
	        <INPUT type=radio <%
			 if("1".equals(request.getAttribute("only"))){
			 %>disabled=true <%}%> <%
			 if("2".equals(request.getAttribute("insuredType"))&&"3".equals(request.getAttribute("flag"))){
			 %>checked <%}%>  name="insuredType" value="2" onclick="checkInsuredType('2')"  >法人   	
      </td>
		</tr>
		<tr>
		<td class="title">證件類型 ：<span style="color: red;">*</span></td>
		<td class="input" >
		<select class=common name="identifyType" <%
			 if("1".equals(request.getAttribute("only"))){
			 %>disabled=true <%}%> onchange="" > 
		<option  <%
			 if("01".equals(request.getAttribute("identifyType"))&&"3".equals(request.getAttribute("flag"))){
			 %>selected <%}%> value='01'>身份證</option>
		<option <%
			 if("04".equals(request.getAttribute("identifyType"))&&"3".equals(request.getAttribute("flag"))){
			 %>selected <%}%> value='04'>護照</option>
		<option <%
			 if("05".equals(request.getAttribute("identifyType"))&&"3".equals(request.getAttribute("flag"))){
			 %>selected <%}%>value='05'>居留證</option>
		<option <%
			 if("60".equals(request.getAttribute("identifyType"))&&"3".equals(request.getAttribute("flag"))){
			 %>selected <%}%> value='60'>統一編號</option>
		</select>
		</td>
		<td class="title">證件號碼：<span style="color: red;">*</span></td>
		<td class="input" colspan="3"><input type="text" class="common"
			id="identifyNumber"
			name="identifyNumber" <%
			 if("1".equals(request.getAttribute("only"))){
			 %>readonly=true <%}%> value="<%
			 if(request.getAttribute("identifyNumber")!=null&&"3".equals(request.getAttribute("flag"))){
			 %><%=request.getAttribute("identifyNumber") %><%}%>"
			codeType="" <%
			 if(request.getAttribute("identifyNumber")!=null&&"3".equals(request.getAttribute("flag"))){
			 %> readonly="true"<%}%> title="" onchange="changeCode(this)" maxlength="18"/> 
			<input type="hidden"
			id="blackListType" name="blackListType"
			cssClass="input_common" codeType="" value="E" readonly="true" title=""/>
			<input type="hidden"   id="blackListCode" name="blackListCode" 
			value="<%
			 if(request.getAttribute("blackListCode")!=null&&"3".equals(request.getAttribute("flag"))){
			 %><%=request.getAttribute("blackListCode") %><%}%>"   />
		</td>
		<td>
		<input class="button" <%
			 if("1".equals(request.getAttribute("only"))){
			 %>disabled=true <%}%> type="button" alt="客戶查詢 " value="客戶查詢" onclick="myQuery()">
		
		</td>
	</tr>
	<tr>
		<td class="title">客戶中文名稱 ：</td>
		<td class="input"><input type="text" class="common"
			name="insuredName" <%
			 if("1".equals(request.getAttribute("only"))){
			 %>readonly=true <%}%> id="insuredName" cssClass="input_common" codeType=""
			value="<%
			 if(request.getAttribute("insuredName")!=null&&"3".equals(request.getAttribute("flag"))){
			 %><%=request.getAttribute("insuredName") %><%}%>" title="" maxlength="58"  />
			</td>
	
	</tr>
	<tr>
        	<td width="13%" class="title">客戶英文名稱：</td>
          <td width="10%" class="input">
             <input type="text" class="common" name="insuredEName" <%
			 if("1".equals(request.getAttribute("only"))){
			 %>readonly=true <%}%> maxlength="120"
                  id="insuredEName" value="<%
			 if(request.getAttribute("insuredEName")!=null&&"3".equals(request.getAttribute("flag"))){
			 %><%=request.getAttribute("insuredEName") %><%}%>"></td>
          <td width="13%" class="title">電子信箱 ：</td>
          <td width="10%" class="input">
            <input type="text" <%
			 if("1".equals(request.getAttribute("only"))){
			 %>readonly=true <%}%> class="common" value="<%
			 if(request.getAttribute("email")!=null&&"3".equals(request.getAttribute("flag"))){
			 %><%=request.getAttribute("email") %><%}%>" name="email" id="email" maxlength="30" >
          </td>
            
       
        </tr>
        
        <tr>
        <td width="13%" class="title">電話：</td>
          <td width="20%" class="input">
            <input type="text" class="common" <%
			 if("1".equals(request.getAttribute("only"))){
			 %>readonly=true <%}%>
            value="<%
			 if(request.getAttribute("mobile")!=null&&"3".equals(request.getAttribute("flag"))){
			 %><%=request.getAttribute("mobile") %><%}%>" name="mobile" maxlength="12" id="mobile"></td>  
          <td width="13%" class="title">行動電話：</td>
          <td width="20%" class="input">
            <input type="text" class="common" <%
			 if("1".equals(request.getAttribute("only"))){
			 %>readonly=true <%}%> value="<%
			 if(request.getAttribute("phoneNumber")!=null&&"3".equals(request.getAttribute("flag"))){
			 %><%=request.getAttribute("phoneNumber") %><%}%>" name="phoneNumber" id="phoneNumber" maxlength="15" onblur="checkNumFormInput(this)"></td>
            <tr>
             <td width="14%" class="title">地址英文名稱：</td>
          <td width="10%" class="input"> 
             <input type="text" class="common" <%
			 if("1".equals(request.getAttribute("only"))){
			 %>readonly=true <%}%> value="<%
			 if(request.getAttribute("addressEName")!=null&&"3".equals(request.getAttribute("flag"))){
			 %><%=request.getAttribute("addressEName") %><%}%>" name="addressEName" id="addressEName" maxlength="255">
           </td>
          <td width="14%" class="title">通信地址：</td>
          <td width="20%" class="input">
             <input type="text" class="common" <%
			 if("1".equals(request.getAttribute("only"))){
			 %>readonly=true <%}%> value="<%
			 if(request.getAttribute("linkAddress")!=null&&"3".equals(request.getAttribute("flag"))){
			 %><%=request.getAttribute("linkAddress") %><%}%>" id="linkAddress" name="linkAddress" maxlength="255" onblur="checkLength(this)" onfocus="setDefaultAddressCName();"></td>
        </tr>
        <tr>
         <td width="13%" class="title">郵遞區號 ：</td>
          <td width="20%" class="input">
            <input type="text" class="codeselect_code" <%
			 if("1".equals(request.getAttribute("only"))){
			 %>readonly=true <%}%> value="<%
			 if(request.getAttribute("postCode")!=null&&"3".equals(request.getAttribute("flag"))){
			 %><%=request.getAttribute("postCode") %><%}%>" id="postCode" name="ppostCode" maxlength=6>
            </td>
         <td width="13%" class="left3">地址中文名稱：</td>
          <td width="20%" colspan="3" class="input">
            <input type="text" class="common" <%
			 if("1".equals(request.getAttribute("only"))){
			 %>readonly=true <%}%> value="<%
			 if(request.getAttribute("addressCName")!=null&&"3".equals(request.getAttribute("flag"))){
			 %><%=request.getAttribute("addressCName") %><%}%>" name="addressCName" maxlength="255" id="addressCName">
            
           </td>         
        </tr>
        <tr>
                  
          <td width="13%" class="title">性别：</td>
          <td width="20%" colspan="5" class="input">
			  <input type="radio" <%
			 if("1".equals(request.getAttribute("only"))){
			 %>disabled=true <%}%>  name="sex" <%
			 if(("1".equals(request.getAttribute("sex"))||request.getParameter("sex")==null)&&"3".equals(request.getAttribute("flag"))){
			 %>checked <%}%> value="1" checked >男 
    	      <input type="radio" <%
			 if("1".equals(request.getAttribute("only"))){
			 %>disabled=true <%}%>  name="sex"  <%
			 if(("2".equals(request.getAttribute("sex")))&&"3".equals(request.getAttribute("flag"))){
			 %>checked <%}%> value="2" >女</td>
        </tr>
        <tr>
        	 <td class="title">			   
			    扣款银行：			   
		   </td><!--  -->
				<td class="input" colspan="2" style="width: 500px;">
				       <input type="text" id="bankCode1" name="bankCode1"  <%
			 if("1".equals(request.getAttribute("only"))){
			 %>readonly=true <%}%>
						required="true" description="扣款银行代码 " <%
			 if("1".equals(request.getAttribute("only"))){
			 %>readonly=true <%}%> value="<%
			 if(request.getAttribute("bankCode1")!=null&&"3".equals(request.getAttribute("flag"))){
			 %><%=request.getAttribute("bankCode1") %><%}%>" style="width: 50px;" class="input_common"	
						/> 
						<input type="text" id="bankCode2" name="bankCode2"   <%
			 if("1".equals(request.getAttribute("only"))){
			 %>readonly=true <%}%>
						required="true" description="扣款银行代码 " value="<%
			 if(request.getAttribute("bankCode2")!=null&&"3".equals(request.getAttribute("flag"))){
			 %><%=request.getAttribute("bankCode2") %><%}%>" onblur="queryBankInfoInput('bankCode1',this,'bank');"  style="width: 60px;" class="input_common"
						/>
					<input type="text" id="bank" name="bank" <%
			 if("1".equals(request.getAttribute("only"))){
			 %>readonly=true <%}%> value="<%
			 if(request.getAttribute("bank")!=null&&"3".equals(request.getAttribute("flag"))){
			 %><%=request.getAttribute("bank") %><%}%>" class="input_common" 
						required="true" description="扣款银行 " style="width: 250px;"
						/>
				</td>
				</tr>
				<tr>
				<td class="title" >扣款賬號：</td>
				<td class="input" colspan="2">
				<input type="text" id="account" style="width: 250px;" <%
			 if("1".equals(request.getAttribute("only"))){
			 %>readonly=true <%}%>
				value="<%
			 if(request.getAttribute("account")!=null&&"3".equals(request.getAttribute("flag"))){
			 %><%=request.getAttribute("account") %><%}%>" name="account" class="input_common" description="扣款账号" maxlength="60"/> 
				</td>		
        </tr>
        <tr>
        	  <td class="title">管制原因：<span style="color: red;">*</span></td>
			   <td class="input">
			   
			   	<span><em></em><input class="button" type="button"  value="......" onclick="showSpan()"></em></span>
				      </button>
                				<span id="span_ItemKind-I-Context_[0]" style='width: 100px; display: none; position: absolute; background-color: #d1fbf3;'>
								<table class="sub" style="border: #94d8e4 1px solid">
								<tr>
								<td class="title"><textarea  id="insuredIdvNote" <%
			 if("1".equals(request.getAttribute("only"))){
			 %>readonly=true <%}%> name="insuredIdvNote" class="input_common" 
					required="true" value="<%
			 if(request.getAttribute("insuredIdvNote")!=null&&"3".equals(request.getAttribute("flag"))){
			 %><%=request.getAttribute("insuredIdvNote") %><%}%>"   style="width: 250px;" maxlength="50" rows="7" 
								cols="60" maxLength="255">
								<%
			 if(request.getAttribute("insuredIdvNote")!=null&&"3".equals(request.getAttribute("flag"))){
			 %><%=request.getAttribute("insuredIdvNote") %><%}%>
										</textarea></td>
								</tr>
								<tr>
								<td colspan="2" align="center">
								<button name="button_LadingNo_Close_Context" onclick="closeItemKind_Context();">
								<span><em><s:text name="確定"></s:text><!-- 确 定 --></em></span>
								</button>
								</td>
								</tr>
								</table>
				            </span>
			   </td>
			   	<td class="title">適用險類：</td>
				<td class="input" >
			<select class=common <%
			 if("1".equals(request.getAttribute("only"))){
			 %>disabled=true <%}%> name="riskCode" > 
			<option <%
			 if("E".equals(request.getAttribute("riskCode"))&&"3".equals(request.getAttribute("flag"))){
			 %>selected <%}%> value='E'>工程險</option>
			<option <%
			 if("C1".equals(request.getAttribute("riskCode"))&&"3".equals(request.getAttribute("flag"))){
			 %>selected <%}%> value='C1'>傷害險</option>
			</select>
			</td>
        </tr>
        <tr>
        		<td class="title4">
						建檔日期：
					</td>
					<td class="input4">
						<input class=small type="text" name="inputDate" <%
			 if("1".equals(request.getAttribute("only"))){
			 %>readonly=true <%}%> id="inputDate" value="<rc:rcDate value="${inputDateRC}" format="yyyy-MM-dd"/>"  
							onFocus="WdatePicker({dateFmt:'yyy-MM-dd'})" >
					</td>
        </tr>
        <tr>
           <td colspan="6" align="center"> 
			<input class="button" type="button" alt="儲存 " value="儲存"  <%
			 if("1".equals(request.getAttribute("only"))){
			 %>disabled=true <%}%> onclick="mySubmit()">
 			<input class="button" type="reset" alt="清除"  <%
			 if("1".equals(request.getAttribute("only"))){
			 %>disabled=true <%}%> value="清除" >
           </td>
           
        </tr>
</table>
<!-- 动作设置结束 --> <!-- 查询结果区域开始 -->
<%
String flag=request.getParameter("flag");

if(!"1".equals(flag)&&!"3".equals(flag))
 {
%>
<div id="hiddenInput" >
	 <table class="common" cellpadding="5" cellspacing="1" align="center">
	    <tr class="listtitle">
	    <td colspan="8">
	    	 <%--核保任务查询结果 --%>
	   		 <b>黑名單查詢結果</b>
	    </td>
	</tr>
	<tr class=listtitle>
		<td> 
		   客戶編號
		</td>
		<td>
		 客戶名稱
		</td>
		<td>
		 建檔日期
		</td>
		<td>
		  建檔人員
		</td>
		<td>
			刪除人員
		</td>
		<td>
			刪除日期
		</td>
		<td>
			修改
		</td>
		<td>
			刪除
		</td>
	</tr>
	   <s:if test="blackMaintenanceList != null">
	   <s:iterator value="blackMaintenanceList" status="statu" id="blackList">
	   <tr class=common>
	    <td> 
		 <a class="check" href="#" onclick="getBlackList('<s:property value="#blackList.identifyNumber"/>')" ><s:property value="#blackList.identifyNumber"/></a>
		</td>
		<td>
		 <s:property value="#blackList.insuredName"/>
		</td>
		<td>
		 <rc:rcDate name = "#blackList.inputDate" format="yyyy-MM-dd"/>
		</td>
		<td>
		 <s:property value="#blackList.operatorCode"/>
		</td>
		<td>
		 <s:property value="#blackList.cheatMeans"/>
		</td>
		<td>
		 <rc:rcDate name = "#blackList.cheatDate" format="yyyy-MM-dd"/>
		</td>
		<td>
		<s:if test="#blackList.cheatMeans==null">
			<a class="check" href="#" onclick="updateBlackList('<s:property value="#blackList.identifyNumber"/>')" >修改</a>
		</s:if>
		<s:else>
		<a class="check" href="#" onclick="" >修改</a>
		</s:else>
		</td>
		<td>
		<s:if test="#blackList.cheatMeans==null">
			<a class="check" href="#" onclick="deleteBlackList('<s:property value="#blackList.identifyNumber"/>')" >刪除</a>
	   </s:if>
	   <s:else>
	   		<a class="check" href="#" onclick="" >刪除</a>
	   </s:else>
		</td>
	   </tr>
	   </s:iterator>
	   </s:if>
	 </table>
	    <table class=menu align="center">
		<tr>
			<td>
				<app:navigate name="fm" objectName="fm"/>
			</td>
		</tr>
		</table>
</div>
<%
}
%>
</form>
</body>
</html>