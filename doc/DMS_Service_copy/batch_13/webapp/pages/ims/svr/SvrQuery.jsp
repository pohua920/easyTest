<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="cn.com.sinosoft.ims.util.IConstants"%>
<html>
<head>
<title>服务管理</title>
<%@ include file="/common/i18njs.jsp"%>
<%@ include file="/common/meta_css.jsp"%>
<%@ page import="cn.com.sinosoft.ims.util.*"%>
</head>
<body id="all_title">

<div id="container">
<div id="crash_menu">
<h2 align="center">查询条件页面</h2>
</div>
<s:form name="fm" action="querySvrList" namespace="/utiISvr" method="post" >
	<s:hidden name="utiISvr.flag" id="utiISvr.flag"></s:hidden>
	<table class="fix_table">
		<tr>
			<td class="bgc_tt short">服务代码</td>
			<td class="long"><input type="text" name="utiISvr.svrCode" id="utiISvr.svrCode" class='input_w w_30'></td>

			<td class="bgc_tt short">服务名称</td>
			<td class="long"><input type="text" name="utiISvr.svrName" id="utiISvr.svrName" class='input_w w_30'></td>

			<td class="bgc_tt short">有效性</td>
			<td class="long">
				<c:set var="checked" value="" />
				<ce:select name="utiISvr.validStatus" id="utiISvr.validStatus" value="${checked}" list="#{'':'所有','1':'有效','0':'无效'}"/></td>
		</tr>
			</table>
		
</s:form></div>
	<table>
		<tr align="center">
			<td ><input type="button" class="button_ty" value="查 询" onclick="executeQuery(1,10);" />
			<input type="button" class="button_ty" value="增 加" onclick="prepareInsertMethod();" /></td>
		</tr>
	</table>
<div id="content_navigation" class="query" align="center"></div>
<div id="content" class="sort"></div>
<div id="content_navigation" class="query" align="center"></div>

</body>
</html>
<%@ include file="/common/meta_js.jsp"%>
<script language="javascript" src="${ctx}/common/dwr/engine.js"></script>
<script language="javascript" src="${ctx}/common/dwr/util.js"></script>
<script language="javascript" src="${ctx}/common/js/CodeSelect.js"></script>
<script type='text/javascript' src="/ims/dwr/interface/ims.js"></script>
<script type='text/javascript' src="${ctx}/pages/ims/svr/SvrView.js"></script>
<script type='text/javascript' src="${ctx}/pages/ims/svr/SvrAdd.js"></script>
<script type='text/javascript' src="${ctx}/pages/ims/svr/SvrModify.js"></script>
<script type="text/javascript">
	var contentDataTable;
	var contentColumnHeaders; 
	YAHOO.namespace("query.container"); 
	var code;
	var hasSpace = false;
	function init(){
		 YAHOO.widget.Column.formatLink = function(elCell, oRecord, oColumn, oData) {
			 var oCode = oRecord.svrCode;
			 var oSvrloginmethod = oRecord.svrLoginMethod;
			 var oupdatercode = oRecord.updaterCode;
			 var validStatus = oRecord.validStatus;
			 var type = oRecord.svrType;
			 var oposition = oRecord.position;
/*			  if(oColumn.key=="serialNo"){
		    		  elCell.innerHTML = "<input type=\"checkbox\" name=\"checkboxes\" value="+oCode+" />";
			  } 
*/
			  if(oColumn.key=="modify"){
				  elCell.innerHTML = "<a href=\"#\" onclick=\"editRecord('"+oCode+"','"+type+"','"+oposition+"')\">修改</a>";
			  }
			  if(oColumn.key=="svrCode"){
					  elCell.innerHTML = "<a href=\"#\" onclick=\"svrView('"+oCode+"','"+type+"','"+oposition+"')\">"+oCode+"</a>";
  			  }
			  if(oColumn.key=="logOrOut"){
				  if(validStatus == 1){
					  elCell.innerHTML = "<a href=\"#\" onclick=\"logOutOrIn('"+oCode+"');\">注销</a>";
				  }else {
					  elCell.innerHTML = "<a href=\"#\" onclick=\"logOutOrIn('"+oCode+"');\">启动</a>";
				  }
			  }
			  if(oColumn.key=="validStatus"){
			      	switch(oRecord.validStatus){
			      		case '0':elCell.innerHTML="无效";break;
			      		case '1':elCell.innerHTML="有效";break;
			      	}
			   }
			   if(oColumn.key=="svrType"){
				 	switch(oRecord.svrType){
			      		case '<%=IConstants.SVRTYPE_DB %>':elCell.innerHTML="数据库";break;
			      		case '<%=IConstants.SVRTYPE_APPSERVER %>':elCell.innerHTML="应用服务器";break;
			      		case '<%=IConstants.SVRTYPE_APPSYSTEM %>':elCell.innerHTML="应用系统";break;
		      		}
			   }
			   if(oColumn.key=="svrLoginMethod"){
				   while(oSvrloginmethod.indexOf("card")>=0){
					  oSvrloginmethod = oSvrloginmethod.replace("card","磁卡");
					  	// elCell.innerHTML="磁卡";
				     }
				    while(oSvrloginmethod.indexOf("usbkey")>=0){
				    	 oSvrloginmethod =  oSvrloginmethod.replace("usbkey","USBKEY");
						// elCell.innerHTML=elCell.innerHTML+"USEKEY";
				     }
				     while(oSvrloginmethod.indexOf("nameAndPwd")>=0){
				    	 oSvrloginmethod =  oSvrloginmethod.replace("nameAndPwd","账号密码");
    					// elCell.innerHTML=elCell.innerHTML+"账号密码";
				     }
				     while(oSvrloginmethod.indexOf("&")>=0){
				    	 oSvrloginmethod =  oSvrloginmethod.replace("&","和");
					 }
				   while(oSvrloginmethod.indexOf("|")>=0){
				    	 oSvrloginmethod = oSvrloginmethod.replace("|","或");
					 }
					// oRecord.svrloginmethod = oSvrloginmethod ;
				     elCell.innerHTML = oSvrloginmethod;
			   }
			 };
	 			contentColumnHeaders =[
				{key:"svrCode",text:"服务代码",width:"15em",sortable:true,type:"link"},
				{key:"svrName",text:"服务名称",width:"20em",sortable:true},
				{key:"position",text:"集中方式",width:"25em",sortable:true},
				{key:"svrType",text:"服务分类",width:"25em",sortable:true,type:"link"},
			    {key:"validStatus",text:"有效标识",width:"25em",sortable:true,type:"link"},
				{key:"svrLoginMethod",text:"服务认证方式",width:"40em",sortable:true,type:"link"},
				{key:"creatorName",text:"服务创建人",width:"30em",sortable:true},
				{key:"updaterName",text:"最新更新人",width:"30em",sortable:true},
				{key:"modify",text:"修改",width:"20em",sortable:true,type:"link"},
				{key:"logOrOut",text:"启动/注销",width:"30em",type:"link",resizeable:true}
				];
		}
	//查询数据
	var myDataSource ;
	var initialRequest;
	function executeQuery(pageNo,pageSize){
		if(isNaN(parseInt(pageNo))){
			pageNo = 1;
		}
		if(isNaN(parseInt(pageSize))){
			pageSize = 10;
		}
		var myColumnSet = new YAHOO.widget.ColumnSet(contentColumnHeaders);	
		myDataSource = new YAHOO.util.DataSource("${ctx}/utiISvr/svrQuery.do");
		myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
		myDataSource.responseSchema = {
		   resultsList: "data",
		   fields: ["svrCode","svrName","position","companyCode","svrCodeInCompany","svrType","validStatus","svrLoginMethod","creatorName","updaterName"],
		   totalRecords: "totalRecords"
		};
		myDataSource.subscribe("responseParseEvent",SINOSOFT.util.navigation);	
		myDataSource.connMgr.setForm(fm);
		initialRequest = "pageSize="+pageSize+"&pageNo="+pageNo;
		var myConfiges ={
			initialRequest:initialRequest,
			paginator:false
		};   
			contentDataTable = new YAHOO.widget.DataTable("content",myColumnSet,myDataSource,myConfiges);
		}
		YAHOO.util.Event.addListener(window,'load',init);

	    //注销/启动
		function logOutOrIn(svrCode){
			if(confirm("确定要对所选数据进行操作？")){
				//alert("是否对以上数据进行操作？");
	/*		var aoCode;
			var codeList = new Array();;
			var num = 0;
			var n = 0;
			var checkbox = document.getElementsByName("checkboxes");
			for(var j=0;j<checkbox.length;j++){
					if(checkbox[j].checked){
						num = num + 1;
						codeList[n] = checkbox[j].value;
					//	alert(codeList[n]);
						n++;
					}
				}
			    if(num == 0){
				alert("请选择至少一条数据进行修改");
			    }else{
					aoCode = codeList[0];
					for(var i=1;i<codeList.length;i++){
						aoCode = aoCode + " and " +codeList[i];
					}
			    }
	*/		var handleSuccess = function(o){
				var args = new SINOSOFT.util.QueryString( contentDataTable.initialRequest );
				var pageSize = parseInt(args["pageSize"],10);
				var pageNo = parseInt(args["pageNo"],10);
				executeQuery(pageNo,pageSize);
			};
			var callback =
				{
	  				success:handleSuccess
				};
					url = "${ctx}/utiISvr/changeValidStatus.do?svrCode="+svrCode;
					var req = YAHOO.util.Connect.asyncRequest('POST', url, callback,"");
			}else{
				alert("操作已取消");
			}
	    }

		function svrView(svrCode,type,position){
			fm.action = "${ctx}/utiISvr/svrView.do?svrCode="+svrCode+"&type="+type+"&position="+position;
			fm.submit();
		}
		function editRecord(svrCode,type,position){
			fm.action = "${ctx}/utiISvr/prepareSvrModify.do?svrCode="+svrCode+"&type="+type+"&position="+position;
			fm.submit();
		}
	//	YAHOO.util.Event.addListener(window,'load',init);
		
	//插入数据
/*	function insertMethod(){
        fm.action="contextRootPath/utiISvr/prepareSvrInsert.do";
        fm.submit();
        return true;
    }	
    //修改数据
  
    function modifyMethod(){
     //  var  spCode= "";
        var  chCode;
        var checkbox = document.getElementsByName("checkboxes");
        var num = 0;
        for(var j=0;j<checkbox.length;j++){
			if(checkbox[j].checked){
				num = num + 1;
			}
        }
        if(num == 0){
        	alert("请选择一项进行修改");
        }
        else if(num>1){
			alert("只能选择一项进行修改");
        }else{
	        for(var i=0;i<checkbox.length;i++){
				if(checkbox[i].checked){
					chCode = checkbox[i].value;
					//alert(chCode);
					if(hasSpace){
						for(var j=0;j<chCode.length;j++){
							spCode = spCode+chCode.substr(j,1)+" ";
						}
							spCode = spCode.replace(/(\s*$)/g,"");
						//	alert("_"+spCode+"_");
							fm.action="contextRootPath/utiISvr/prepareSvrModify.do?svrcode="+spCode;
						//	alert(spCode);
					        fm.submit();
					}
			
			        fm.action="contextRootPath/utiISvr/prepareSvrModify.do?svrcode="+chCode;
			        fm.submit();
				}
	        }
        return true;
        }
    }
   */
    //查看数据

 /*   function viewMethod(){
        var aoCode = "";
        var checkbox = document.getElementsByName("checkboxes");
        var num = 0;
        for(var j=0;j<checkbox.length;j++){
			if(checkbox[j].checked){
				num = num + 1;
			}
        }
        if(num == 0){
        	alert("请选择一项进行修改");
        }
        else if(num>1){
			alert("只能选择一项进行修改");
        }else{
	        for(var i=0;i<checkbox.length;i++){
				if(checkbox[i].checked){
					aoCode = checkbox[i].value ;
					alert(aoCode);
					fm.action="contextRootPath/utiISvr/svrView.do?svrcode="+aoCode;
			        fm.submit();
				}
	        }
        return true;
        }
    }
  */
 

	</script>