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
<s:hidden name="comCode" id="comCode" value="${comCode}"></s:hidden>
	<table class="fix_table">	
		<tr>
			<td class="bgc_tt short" >用户代码</td>			
			<td class="long" ><input type="text" name="utiIUser.userCode"  class='input_w w_30'></td>			
		</tr>	
		<tr>
			<td class="bgc_tt short" >用户名称</td>
			<td class="long" ><input type="text" name="utiIUser.userName"  class='input_w w_30'></td>
		</tr>		
		<tr>
			<td class="bgc_tt short" >归属机构</td>
			<td class="long" >
				<div id="validStatusMapDiv" class="selectui-indiv">
			        <div class="selectConfig">
			        <div class="codeType">StaticSelect</div>
			        </div>
			        <c:set var="checked" value="${comCode}" />
			        <ce:select name="utiIUser.comCode" id="utiIUser.comCode" cssClass="selectui-input-up input_w w_60" value="${checked}" list="comCodeMap" />
			    </div>
			    <div>
			    	<c:set var="checked" value="1" ></c:set>
			    	<ce:radio name="company" value="${checked}" list="#{'1':'仅当前公司','0':'包含子公司'}" />
			    </div>
		     </td>
	    </tr>	
	    <input type="hidden" value="" name="test">
        <tr>
			<td class="bgc_tt short">用户类型</td>	
            <td class="long" >
					    <div id="userTypeDiv" class="selectui-indiv">
					        <div class="selectConfig">
					        <div class="codeType">StaticSelect</div>
					        </div>
					        <c:set var="checked" value="0" />
					        <ce:select name="utiIUser.userType" id="userType" cssClass="selectui-input input_w w_15	"  value="${checked}" list="userTypeMap" />
					    </div>
			</td>		
		</tr>
		<tr>
			<td class="bgc_tt short">有效标志</td>
            <td class="long">
					    <div id="validStatusDiv" class="selectui-indiv">
					        <div class="selectConfig">
					        <div class="codeType">StaticSelect</div>
					        </div>
					        <c:set var="checked" value="" />
					        <ce:select name="utiIUser.validStatus" id="validStatus" cssClass="selectui-input input_w w_15"  value="${checked}" 
					        list="#{'':'所有','0':'注销','1':'激活','2':'未设置'}" />
					    </div>
			</td>
			
		</tr>		
		<tr align="center">
			<td align="center"  colspan="4">
                <input type="button" class="button_ty" value="查  询" onclick="executeQuery(1,10);">
                <input type="button" class="button_ty" name=buttonInsert value="增  加" onclick="return insertMethod()">
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
<script type='text/javascript' src='/ims/dwr/interface/Ims.js'></script>
<script language="javascript" src="${ctx}/common/js/CodeSelect.js"></script>
<script type="text/javascript">
	var contentDataTable;
	var contentColumnHeaders; 
	YAHOO.namespace("query.container"); 

	
	
	function init(){
		//var comCode_tip = new YAHOO.widget.Tooltip("comCode_tip",{text:"请双击选择机构代码",context:"utiIUser.comCode",zIndex:300});	
		 YAHOO.widget.Column.formatLink = function(elCell, oRecord, oColumn, oData) { 
			  var oId = oRecord.userCode;
			  var type= oRecord.userType;
			  var validStatus = oRecord.validStatus;
			  var auditStatus = oRecord.auditStatus;
			  if(oColumn.key=="userCode"){
				  elCell.innerHTML = "<a href=\"#\" onclick=\"viewMethod('"+oId+"');\">"+oId+"</a>";
		      }
		      if(oColumn.key=="userEdit"){
			      if(auditStatus == "1"){
			    	  elCell.innerHTML = "<a href=\"#\" onclick=\"modifyMethod('"+oId+"','"+type+"');\">修改</a>";
				   }else if(auditStatus == "0" && validStatus == "1"){
					   elCell.innerHTML = "<a href=\"#\" onclick=\"modifyMethod('"+oId+"','"+type+"');\">修改</a><a href=\"#\" onclick=\"replyApply('"+oId+"');\">提交申请</a>";
				   }else if(auditStatus == "0" && validStatus == "0"){
					   elCell.innerHTML = "<a href=\"#\" onclick=\"viewMethod('"+oId+"');\">查看</a>";
				}
		      }
		      if(oColumn.key=="userAdjust"){
			      if(auditStatus == "1"){
			    	  	elCell.innerHTML = "<a href=\"#\" onclick=\"adjustMethod('"+oId+"','"+type+"');\">机构调整</a>";
				  }else{
		    	  	elCell.innerHTML = "";
				  }
		      }
		     // if(oColumn.key=="userRoaming"){
		    //	  elCell.innerHTML = "<a href=\"#\" onclick=\"roamingMethod('"+oId+"','"+type+"');\">漫游</a>";
		     // }
		    //  if(oColumn.key=="userRoamBack"){
		    //	  elCell.innerHTML = "<a href=\"#\" onclick=\"roamBackMethod('"+oId+"','"+type+"');\">收回</a>";
		    //  }
		      if(oColumn.key=="validStatus"){
			      if(auditStatus == "1"){
			    	  if(validStatus == "1"){
				    	  elCell.innerHTML = "<a href=\"#\" onclick=\"validMethod('"+oId+"','"+type+"');\">注销</a>";
					  }else{
						  elCell.innerHTML = "<a href=\"#\" onclick=\"validMethod('"+oId+"','"+type+"');\">激活</a>";
					  }
				   }else{
					   elCell.innerHTML = "未设置";
					}
			      
		    	 
		      }
		      if(oColumn.key=="userUpdate"){
			      if(auditStatus == "1"){
			    	  elCell.innerHTML = "<a href=\"#\" onclick=\"updateMethod('"+oId+"','"+type+"');\">将账户信息更新到用户</a>";
				   }else{
					   elCell.innerHTML = "";
					}
		    	 
		      }
		     
			  
		
		     //用户类型的显示
		     if(oColumn.key=="userType"){
			      	switch(oRecord.userType){
			      	  case '<%=IConstants.USERTYPE_STUFF %>':elCell.innerHTML="员工用户";break;
		      		  case '<%=IConstants.USERTYPE_SALES %>':elCell.innerHTML="业务员用户";break;
		      		  case '<%=IConstants.USERTYPE_VIRTUAL %>':elCell.innerHTML="虚拟用户";break;
		      		  case '<%=IConstants.USERTYPE_PARTNERS %>':elCell.innerHTML="合作伙伴用户";break;
		      		  case '<%=IConstants.USERTYPE_ENTERPRISE %>':elCell.innerHTML="企业用户";break;
		      		  case '<%=IConstants.USERTYPE_PERSONAL %>':elCell.innerHTML="个人用户";break;
		      		  case '<%=IConstants.USERTYPE_TEMPORARY %>':elCell.innerHTML="临时用户";break;
		      		  case '<%=IConstants.USERTYPE_OTHER %>':elCell.innerHTML="其他用户";break;
			      	}
			 }
		 };
		
		contentColumnHeaders =[
			{key:"userCode",text:"用户代码",width:"25em",type:"link",sortable:true},
			{key:"userName",text:"用户名称",width:"30em",sortable:true},
			{key:"userType",text:"用户类型",width:"20em",sortable:true,type:"link"},
			{key:"comCode",text:"归属机构代码",width:"20em",sortable:true},
			//{key:"validStatus",text:"有效性",width:"15em",sortable:true,type:"link"},
			{key:"userEdit",text:"操作",width:"8em",type:"link",resizeable:true},
			{key:"userAdjust",text:"操作",width:"12em",type:"link",resizeable:true},
			//{key:"userRoaming",text:"漫游",width:"8em",type:"link",resizeable:true},
			//{key:"userRoamBack",text:"收回",width:"8em",type:"link",resizeable:true},
			{key:"validStatus",text:"操作",width:"8em",type:"link",resizeable:true},
			{key:"userUpdate",text:"操作",width:"18em",type:"link",resizeable:true}
			
			
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
		var comcode = document.getElementById("comcode").value;
		var myColumnSet = new YAHOO.widget.ColumnSet(contentColumnHeaders);	
		var myDataSource = new YAHOO.util.DataSource("${ctx}/utiIUser/queryUserList.do");
	    myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
		myDataSource.responseSchema = {
		   resultsList: "data",
		   fields: ["userCode", "userName", "userType" , "comCode" ,"validStatus","auditStatus"],
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
	// 增加
	function insertMethod(){
        fm.action="prepareSelectUserType.do?editType=insert";
        fm.target="page";
        fm.submit();
        return true;
    }
	//修改
    function modifyMethod(oId,userType){  
    	
        if(userType=="01"){
			alert("不能在平台修改员工信息，只能在HR中修改！");
			return false;
		}if(userType=="02"){
			alert("不能在平台修改业务员用户信息，只能在HR中修改！");
			return false;
		}if(userType=="04"){
			alert("不能在平台修改合作伙伴用户信息，只能在HR中修改！");
			return false;
		}if(userType=="01"){
			alert("不能在平台修改企业客户信息，只能在HR中修改！");
			return false;
		}if(userType=="07"){
			alert("不能在平台修改个人客户信息，只能在HR中修改！");
			return false;
		}else{
        	fm.action="contextRootPath/utiIUser/prepareUpdateUser.do?editType=update&userCode="+oId;
			fm.target="page";
			fm.submit();

			return true;
        }
    }

    //用户机构调整
    function adjustMethod(oId,userType){
    	if(userType=="01"){
 			alert("不能在平台修改员工信息，只能在HR中修改！");
 			return false;
 		}if(userType=="02"){
			alert("不能在平台修改业务员用户信息，只能在HR中修改！");
			return false;
		}if(userType=="04"){
			alert("不能在平台修改合作伙伴用户信息，只能在HR中修改！");
			return false;
		}if(userType=="01"){
			alert("不能在平台修改企业客户信息，只能在HR中修改！");
			return false;
		}if(userType=="07"){
			alert("不能在平台修改个人客户信息，只能在HR中修改！");
			return false;
		}else{
         	fm.action="contextRootPath/utiIUser/prepareAdjustOrg.do?userCode="+oId;
 			fm.target="page";
 			fm.submit();

 			return true;
         }
    }

    //漫游
  
    function roamingMethod(oId,userType){ 
    	
        if(userType=="01"){
			alert("不能在平台对用户进行漫游操作，只能在HR中进行！");
			return false;
		}if(userType=="02"){
			alert("不能在平台对业务员用户进行漫游操作，只能在HR中修改！");
			return false;
		}if(userType=="04"){
			alert("不能在平台对合作伙伴用户进行漫游操作，只能在HR中修改！");
			return false;
		}if(userType=="01"){
			alert("不能在平台对企业客户进行漫游操作，只能在HR中修改！");
			return false;
		}if(userType=="07"){
			alert("不能在平台对个人客户进行漫游操作，只能在HR中修改！");
			return false;
		}else{
	        
			fm.action="contextRootPath/utiIUser/preRoaming.do?userCode="+oId;
 			fm.target="page";
 			fm.submit();

            return true;
        }
    }
    //收回
    function roamBackMethod(oId,userType){
    	if(userType=="01"){
			alert("不能在平台对用户进行收回操作，只能在HR中进行！");
			return;
		}if(userType=="02"){
			alert("不能在平台修改业务员用户信息，只能在HR中修改！");
			return false;
		}if(userType=="04"){
			alert("不能在平台修改合作伙伴用户信息，只能在HR中修改！");
			return false;
		}if(userType=="01"){
			alert("不能在平台修改企业客户信息，只能在HR中修改！");
			return false;
		}if(userType=="07"){
			alert("不能在平台修改个人客户信息，只能在HR中修改！");
			return false;
		}else{
	        
			var args = new SINOSOFT.util.QueryString( contentDataTable.initialRequest );
			var pageSize = parseInt(args["pageSize"],10);
			var pageNo = parseInt(args["pageNo"],10);
			executeQuery(pageNo,pageSize);
			var url = "contextRootPath/utiIUser/updateRoaming.do?userCode="+oId;
			var req = YAHOO.util.Connect.asyncRequest('POST', url, "");
				
           return true;
        }
    }

    //注销/激活
    function validMethod(oId,userType){
    	
    	if(userType=="01"){
			alert("不能在平台对用户进行注销/激活操作，只能在HR中进行！");
			return false;
		}if(userType=="02"){
			alert("不能在平台对业务员用户进行注销/激活操作，只能在HR中修改！");
			return false;
		}if(userType=="04"){
			alert("不能在平台对合作伙伴用户进行注销/激活操作，只能在HR中修改！");
			return false;
		}if(userType=="01"){
			alert("不能在平台对企业客户进行注销/激活操作，只能在HR中修改！");
			return false;
		}if(userType=="07"){
			alert("不能在平台对个人客户进行注销/激活操作，只能在HR中修改！");
			return false;
		}else if(confirm("确定要对所选数据进行操作？")){
			var handleSuccess = function(o){
				var args = new SINOSOFT.util.QueryString( contentDataTable.initialRequest );
				var pageSize = parseInt(args["pageSize"],10);
				var pageNo = parseInt(args["pageNo"],10);
				executeQuery(pageNo,pageSize);
			};
			var callback =
			{
			  success:handleSuccess
			};
			var url = "contextRootPath/utiIUser/updateValidStatus.do?userCode="+oId;
			var req = YAHOO.util.Connect.asyncRequest('POST', url, callback,"");
		}else{
			alert("操作已取消");
		}    
    }

    //查看
    function viewMethod(oId){
			fm.action="contextRootPath/utiIUser/prepareUpdateUser.do?editType=view&userCode="+oId;
			fm.submit();
				
    }
  //同步
    function updateMethod(oId,userType){
    	 if(userType=="01"){
 			alert("不能在平台对用户信息进行更新操作，只能在HR中进行！");
 			return false;
 		}if(userType=="02"){
			alert("不能在平台对业务员用户进行更新操作，只能在HR中修改！");
			return false;
		}if(userType=="04"){
			alert("不能在平台对合作伙伴进行更新操作，只能在HR中修改！");
			return false;
		}if(userType=="01"){
			alert("不能在平台对企业客户进行更新操作，只能在HR中修改！");
			return false;
		}if(userType=="07"){
			alert("不能在平台对个人客户进行更新操作，只能在HR中修改！");
			return false;
		}else{
 	 		Ims.hasAccount(oId,callBackUpdate);
			
 		} 
    }
    function callBackUpdate(data){
		if(data=="noAccount"){
			alert("该用户不包含账户");
		}else{
			fm.action="contextRootPath/utiIUser/prepareUserInfoUpdate.do?userCode="+data;
		    fm.submit();
	        return true;
		}
    }

	function replyApply(userCode){
		if(confirm("确定要对所选数据进行操作？")){
			var handleSuccess = function(o){
				alert('申请已提交，请等待审核！');
				var args = new SINOSOFT.util.QueryString( contentDataTable.initialRequest );
				var pageSize = parseInt(args["pageSize"],10);
				var pageNo = parseInt(args["pageNo"],10);
				executeQuery(pageNo,pageSize);
			};
			var callback =
			{
			  success:handleSuccess
			};
			var url = "contextRootPath/utiIUser/replyApply.do?userCode="+userCode;
			var req = YAHOO.util.Connect.asyncRequest('POST', url, callback,"");
		}else{
			alert("操作已取消");
		}    
	}
    
    YAHOO.util.Event.addListener(window,'load',init);
	</script>