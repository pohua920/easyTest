<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>员工权限完全复制</title>
<%@include file="/common/meta_css.jsp"%>
<%-- moidfy  update by tongziliang 2011-10-09 reason:修改页面的按钮样式和优化页面样式 --%>
<link rel="stylesheet" href="${ctx}/style/popup/login.css" type="text/css"></link>
</head>
<body id="all_title">
<div id="crash_menu"><h2 align="center">员工权限完全复制</h2></div>
<s:form action="" name="form">
	<table class="fix_table">
		<tr>
			<td class="bgc_tt short" colspan="2" align="center">源员工</td>
			<td class="bgc_tt short" colspan="2" align="center">目标员工</td>
		</tr>
		<tr>
			<td class="bgc_tt short">源员工代码</td>
			<td class="long"><s:textfield 	name="userCodeFrom" id="userCodeFrom" cssClass='input_w w_30' readonly="true" /></td>
			<td class="bgc_tt short">目标员工代码</td>
			<td class="long"><s:textfield  name="userCodeTo" id="userCodeTo" cssClass='input_w w_30' readonly="true" /></td>
		</tr>
		<tr>
			<td class="bgc_tt short">源员工姓名</td>
			<td class="long"><s:textfield id="userNameFrom" cssClass='input_w w_30' readonly="true" /></td>

			<td class="bgc_tt short">目标员工姓名</td>
			<td class="long"><s:textfield id="userNameTo" cssClass='input_w w_30' readonly="true" /></td>
		</tr>			
		<tr>
			<td colspan="4">
			<table width="98%" id="anthTable" class="fix_table">
				<tr class="top">
					<td align="center" valign="middle">源员工选择</td>
					<td align="center" valign="middle">目标员工选择</td>
				</tr>
				<tr>
					<td align="center" valign="middle">
					<select name="userSelectFrom" id="userSelectFrom"
						class="one" size=8 style="width:100%" onChange="user_selectFrom()" >
						<s:iterator value="saaUserList" status="stuts">
							<option
								value="<s:property
								value="%{saaUserList[#stuts.index].userCode}" />" /><s:property
								value="%{saaUserList[#stuts.index].userCode}" />-<s:property
								value="%{saaUserList[#stuts.index].userName}" />
								</option>
						</s:iterator>
					</select>
					</td>
					<td align="center" valign="middle">
					<select name="userSelectTo" id="userSelectTo" class="one" size=8 style="width:100%"  style="color:green"
					onChange="user_select()">
					</select>
					</td>
				</tr>
			</table>
			</td>
		</tr>		
			<tr>
				<td class="bgc_tt short" colspan="2" align="center">
				<button type="button"  value=""
					onclick="submitPowerCopy()"><span><em>确定</em></span></button>
<!--				<input-->
<!--					type="button" class="button_ty" value="确定"-->
<!--					onclick="submitPowerCopy()"></input>-->
					</td>
				<td class="bgc_tt short" colspan="2" align="center">
				<button type="button" value=""
					onclick="javascript:document.forms.form.reset()"><span><em>取消</em></span></button>
<!--				<input-->
<!--					type="button" class="button_ty" value="取消"-->
<!--					onclick="javascript:document.forms.form.reset()"></input>-->
					</td>
			</tr>
		
</table>
</s:form>
</body>
</html>

<%@ include file="/common/meta_js.jsp"%>
<script language="javascript" src="${ctx}/common/dwr/engine.js"></script>
<script language="javascript" src="${ctx}/common/dwr/util.js"></script>
<script language="javascript"
	src="${ctx}/dwr/interface/dwrInvokeDataAction.js"></script>
<script language="javascript"
	src="${ctx}/widgets/yui/autocomplete/autocomplete-min.js"></script>

<script language="javascript" src="${ctx}/common/js/CodeSelect.js"></script>
<script type="text/javascript">
function submitPowerCopy(){
	var userCodeFrom = document.getElementById("userCodeFrom").value;
	var userCodeTo = document.getElementById("userCodeTo").value;
	if(userCodeFrom!=null&&userCodeFrom!=""){
		if(userCodeTo!=null&&userCodeTo!=""){
			if (confirm("权限复制将完全覆盖用户的所有原有权限，确定要复制吗？")){
				// form.target="submitFrame";
	           	form.action="${ctx}/saaUserPower/userPowerCopy.do";
	           	form.submit();
			}
			}else{
			alert("请选择目标员工");
		}
	}else{
		alert("请选择源员工");
	}
}

</script>
<script type="text/javascript">
function user_select(){
	var userSelect = document.getElementById("userSelectTo");
	var userSelectText = userSelect.options[userSelect.selectedIndex].text;
	var userArray = userSelectText.split('-');
	document.getElementById("userCodeTo").value=userArray[0];
	document.getElementById("userNameTo").value=userArray[1];	
}
</script>

<script type="text/javascript">
	var req;
   function user_selectFrom(){
  		document.getElementById("userCodeTo").value="";
   		document.getElementById("userNameTo").value="";       	
        var userSelect = document.getElementById("userSelectFrom");        
		var userSelectText = userSelect.options[userSelect.selectedIndex].text;
		var userArray = userSelectText.split('-');
		document.getElementById("userCodeFrom").value=userArray[0];
		document.getElementById("userNameFrom").value=userArray[1];         
         var url="${ctx}/saaUser/queryUserJSPByUserCode.do?userCode=" + document.getElementById("userCodeFrom").value;
          if(window.XMLHttpRequest){
                req=new XMLHttpRequest();
            }else if(window.ActiveXObject){
                req=new ActiveXObject("Microsoft.XMLHTTP");
            }            
            if(req){
                req.open("GET",url,true);
                req.onreadystatechange=callback;
                req.send(null);
            }
        }             
        function callback(){
            if(req.readyState == 4){	
                if(req.status == 200){	
                    parseMessage();
                }else{
                    alert("Not able to retrieve description"+req.statusText);
                }
            }
        }               
        function parseMessage(){
            var xmlDoc=req.responseXML.documentElement;
            var xSel=xmlDoc.getElementsByTagName('select');
            var select_root=document.getElementById('userSelectTo');
            select_root.options.length=0;            
            
            for(var i=0;i<xSel.length;i++){
                var xValue=xSel[i].childNodes[0].firstChild.nodeValue;
                var xText=xSel[i].childNodes[1].firstChild.nodeValue;
                var option=new Option(xText,xValue);
                try{
                    select_root.add(option);
                }catch(e){
                }
            }  
        }
</script>



