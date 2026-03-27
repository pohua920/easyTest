<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="cn.com.sinosoft.ims.util.IConstants"%>

<html>
<head>
<title>用户管理</title>
<%@ include file="/common/i18njs.jsp"%>
<%@ include file="/common/meta_css.jsp"%>
</head>
<body id="all_title">

<div id="wrapper">
<div id="container">
<div id="crash_menu">
<h2 align="center">请输入查询条件</h2>
</div>
<s:form name="fm" action="" target="companyTreeRight">
<s:hidden name="flag" id="flag"></s:hidden>
<s:hidden name="utiIUserIdv.creatorCode" id="utiIUserIdv.creatorCode" value="${utiIUserIdv.creatorCode}"></s:hidden>
<s:hidden name="comCode" id="comCode" value="${comCode}"></s:hidden>
	<table class="fix_table">	
		<tr>
			<td class="bgc_tt short">用户代码</td>			
			<td class="long"><input type="text" name="utiIUser.userCode"  class='input_w w_15'></td>			
			<td class="bgc_tt short">用户名称</td>
			<td class="long"><input type="text" name="utiIUser.userName"  class='input_w w_15'></td>
		</tr>		
		<tr>
			<td class="bgc_tt short">归属机构代码</td>
			<td class="long"><input name="utiIUser.comCode"
				id="utiIUser.comcode" class='input_y w_p90'
				ondblclick="code_CodeQuery(this, 'ComCode', '0,1', 'Y','')"
				onkeyup="code_CodeQuery(this, 'ComCode', '0,1', 'Y','')"
				onchange="code_CodeChange(this, 'ComCode', '0,1', 'Y','')"/></td>
			
			<td class="bgc_tt short">归属机构名称</td>
			<td class="long"><input name="comName"
				id="comName" class='input_w w_15'></td>
		</tr>
        <tr>
			<td class="bgc_tt short">用户类型</td>			
			<td class="long"><s:select name="utiIUser.userType" value="${utiIUser.userType}"
					list="#@java.util.HashMap@{'01':'员工用户','02':'业务员用户',
                    '03':'虚拟用户','04':'合作伙伴用户',
                    '06':'企业客户','07':'个人客户',
                    '98':'临时用户','99':'其他用户'}"  /></td>			
			<td class="bgc_tt short">有效标志</td>
			<td class="long"><s:select name="utiIUser.validStatus" value="${utiIUser.validStatus}"
					list="#@java.util.HashMap@{'0':'无效','1':'有效'}"  /></td>
		</tr>		
		<tr align="center">
			<td align="center"  colspan="4">
                <input type="button" class="button_ty" value="查 询" onclick="executeQuery(1,10);">
                <input type='button' class="button_ty" name=buttonInsert value="用户信息维护" onclick="return insertMethod()">
                <input type="button" class="button_ty" value="信息查看" onclick="return viewMethod()" />
                <input type="button" class="button_ty" value="信息更新" onclick="return updateMethod()" />
            </td>
		</tr>
	</table>
</s:form></div>
<div id="content_navigation" class="query" align="center"></div>
<div id="content" class="sort"></div>
<div id="content_navigation" class="query" align="center"></div>
</div>
</body>
</html>
<%@ include file="/common/meta_js.jsp"%>
<script language="javascript" src="${ctx}/common/dwr/engine.js"></script>
<script language="javascript" src="${ctx}/common/dwr/util.js"></script>
<script language="javascript" src="${ctx}/common/js/CodeSelect.js"></script>
<script type='text/javascript' src='/ims/dwr/interface/Ims.js'></script>
<script type="text/javascript">
var contentDataTable;
var contentColumnHeaders; 
YAHOO.namespace("query.container"); 

function init(){
	var comCode_tip = new YAHOO.widget.Tooltip("comCode_tip",{text:"请双击选择机构代码",context:"utiIUser.comCode",zIndex:300});	
	 YAHOO.widget.Column.formatLink = function(elCell, oRecord, oColumn, oData) { 
		 var oId = oRecord.userCode;
		 if(oColumn.key=="serialNo"){
    		  elCell.innerHTML = "<input type=\"checkbox\" name=\"checkboxes\" value="+oId+" onclick=\"onCheck();\"  ></input>";
	     } 
	     //效力状态的显示
	     if(oColumn.key=="validStatus"){
		      	switch(oRecord.validStatus){
		      		case '<%=IConstants.VALIDSTATUS_INVALID %>':elCell.innerHTML="无效";break;
		      		case '<%=IConstants.VALIDSTATUS_VALID %>':elCell.innerHTML="有效";break;
		      	}
		 }
	     //用户类型的显示
	     if(oColumn.key=="userType"){
		      	switch(oRecord.userType){
		      	  case '<%=IConstants.USERTYPE_STUFF %>':elCell.innerHTML="员工用户";break;
	      		  case '<%=IConstants.USERTYPE_SALES %>':elCell.innerHTML="业务员用户";break;
	      		  case '<%=IConstants.USERTYPE_VIRTUAL %>':elCell.innerHTML="虚拟用户";break;
	      		  case '<%=IConstants.USERTYPE_PARTNERS %>':elCell.innerHTML="合作伙伴用户";break;
	      		  case '<%=IConstants.USERTYPE_ENTERPRISE %>':elCell.innerHTML="企业客户";break;
	      		  case '<%=IConstants.USERTYPE_PERSONAL %>':elCell.innerHTML="个人客户";break;
	      		  case '<%=IConstants.USERTYPE_TEMPORARY %>':elCell.innerHTML="临时用户";break;
	      		  case '<%=IConstants.USERTYPE_OTHER %>':elCell.innerHTML="其他用户";break;
		      	}
		 }
	 };
	
		contentColumnHeaders =[
			{key:"userCode",text:"用户代码",width:"40em",sortable:true},
			{key:"userName",text:"用户名称",width:"40em",sortable:true},
			{key:"userType",text:"用户类型",width:"40em",sortable:true,type:"link"},
			{key:"comCode",text:"归属机构代码",width:"50em",sortable:true},
			{key:"validStatus",text:"有效性",width:"20em",sortable:true,type:"link"},
			{key:"serialNo",text:"序号",width:"20em",type:"link",resizeable:true}
			
			]; 
	}
	
	//Query Data
	function executeQuery(pageNo,pageSize){
		
		if(isNaN(parseInt(pageNo))){ 
			pageNo = 1;
		}
		if(isNaN(parseInt(pageSize))){
			pageSize = 10;
		}
		var comCode = document.getElementById("comCode").value;
		var myColumnSet = new YAHOO.widget.ColumnSet(contentColumnHeaders);	
		var myDataSource = new YAHOO.util.DataSource("${ctx}/utiIUser/queryUserList.do");
	    myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
		myDataSource.responseSchema = {
		   resultsList: "data",
		   fields: ["userCode", "userName", "userType" , "comCode" , "validStatus"],
		   totalRecords: "totalRecords"
		};
		myDataSource.subscribe("responseParseEvent",SINOSOFT.util.navigation);	
		myDataSource.connMgr.setForm(fm);
		var initialRequest = "pageSize="+pageSize+"&pageNo="+pageNo;
		var myConfiges ={
			initialRequest:initialRequest,
			paginator:false
		};   
		
			contentDataTable = new YAHOO.widget.DataTable("content",myColumnSet,myDataSource,myConfiges);
			
		
	}
	YAHOO.util.Event.addListener(window,'load',init);
	var flag=true;
	function validate(msg){
		this.userChk=function(code){
	//		alert("svrchk|||"+svrname);
			Ims.isValid(code,callBack); 
	    };
		var callBack=function(data){   
		    if((data=="modify")&&(msg==1)){ 
			    flag = false;
		    }
		    else{
			    flag = true;
			}
		   };  
	}
	function onCheck(){
		var code;
		var codeList = new Array();
		var num = 0;
		var n = 0;
		var checkbox = document.getElementsByName("checkboxes");
		for(var j=0;j<checkbox.length;j++){
			if(checkbox[j].checked){
				num = num + 1;
				codeList[n] = checkbox[j].value;
				n++;
			}
		}
		if(num==0){
		}
		else if((document.getElementById("utiIUser.userType").value)!="01"){
		}
		else{
			code = codeList[0];
			var rc=new validate(num);   
		    rc.userChk(code);
		}
	}
	
	// 信息维护
	function insertMethod(){
		var type = document.getElementById("utiIUser.userType").value;
		var aoCode;
		var codeList = new Array();
		var num = 0;
		var n = 0;
		var checkbox = document.getElementsByName("checkboxes");
		for(var j=0;j<checkbox.length;j++){
			if(checkbox[j].checked){
				num = num + 1;
				codeList[n] = checkbox[j].value;
				n++;
			}
		}
	    if(num == 0){
			alert("请选择一项数据进行添加");
			return;
	    }
	    if(num >1){
			alert("只能选择一项进行添加");
			return;
	    }
	    aoCode = codeList[0];
	//	alert("insert----"+aoCode);
		fm.action="contextRootPath/utiIUser/prepareAddUserIdv.do?userCode="+aoCode;
		var creator = document.getElementById("utiIUserIdv.creatorCode").value;
		if(type=="01"&&(!flag)){
			alert("不能在平台修改员工信息,请在HR系统中修改");
			return;
		}
		fm.submit();
	    }

    //查看
    function viewMethod(){
        var aoCode;
        var checkbox = document.getElementsByName("checkboxes");
        var num = 0;
        for(var j=0;j<checkbox.length;j++){
			if(checkbox[j].checked){
				num = num + 1;
			}
        }
        if(num == 0){
        	alert("请选择一项进行查看");
        }
        else if(num>1){
			alert("只能选择一项进行查看");
        }else{
	        for(var i=0;i<checkbox.length;i++){
				if(checkbox[i].checked){
					aoCode = checkbox[i].value ;
					fm.action="contextRootPath/utiIUser/viewUserIdvOrUnit.do?userCode="+aoCode;
			        fm.submit();
				}
	        }
        return true;
        }
    }

    //同步
    function updateMethod(){
    	var aoCode;
        var checkbox = document.getElementsByName("checkboxes");
        var num = 0;
        for(var j=0;j<checkbox.length;j++){
			if(checkbox[j].checked){
				num = num + 1;
			}
        }
        if(num == 0){
        	alert("请选择一项进行查看");
        }
        else if(num>1){
			alert("只能选择一项进行查看");
        }else{
	        for(var i=0;i<checkbox.length;i++){
				if(checkbox[i].checked){
					aoCode = checkbox[i].value ;
					fm.action="contextRootPath/utiIUser/prepareUserInfoUpdate.do?userCode="+aoCode;
			        fm.submit();
				}
	        }
        return true;
        }
    }
	   YAHOO.util.Event.addListener(window,'load',init);
	</script>