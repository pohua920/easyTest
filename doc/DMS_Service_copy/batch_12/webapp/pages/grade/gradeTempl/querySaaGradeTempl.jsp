<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="cn.com.sinosoft.ims.util.IConstants"%>
<script language="javascript" src="${ctx}/common/js/sinosoft.js"></script>
<html>
<head>
<title>岗位模板管理</title>
<%@ include file="/common/i18njs.jsp"%>
<%@ include file="/common/meta_css.jsp"%>
<%@ page import="cn.com.sinosoft.ims.util.*"%>
</head>
<body id="all_title">

<div id="container">
<div id="crash_menu">
<h2 align="center">查询条件页面</h2>
</div>
<s:form name="fm" action="queryGradeTemplList" namespace="/saaGradeTempl" method="post" >
	<table class="fix_table">
		<tr>
			<td class="bgc_tt short">模板代码</td>
			<td class="long"><input type="text" name="id"  id="id" class='input_w w_30 dt-num'></td>

			<td class="bgc_tt short">模板名称</td>
			<td class="long"><input type="text" name="gradeTemplCName"  class='input_w w_30'></td>

		
		</tr>
			</table>
		
</s:form></div>
	<table>
		<tr align="center">
			<td ><input type="button" class="button_ty" value="查 询" onclick="executeQuery(1,10);" />
			<input type="button" class="button_ty" value="增 加"  onclick="editRecord('${ctx}/saaGradeTempl/prepareInsertGradeTempl.do?editType=insert');" > 
           </td>
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
			 var oId = oRecord.id;
			 var gradeTemplCName=oRecord.gradeTemplCName;
			 var oupdatercode = oRecord.updaterCode;
			 var validStatus = oRecord.validStatus;
			 var creatorcode =oRecord.creatorCode;

			  if(oColumn.key=="modify"){
				  elCell.innerHTML = "<a href=\"#\" onclick=\"editRecord('${ctx}/saaGradeTempl/prepareUpdateGradeTempl.do?editType=update&id="+oId+"')\">修改</a>";
			  }
			 
			  if(oColumn.key=="logOrOut"){
				  if(validStatus == 1){
					  elCell.innerHTML = "<a href=\"#\" onclick=\"logOutOrIn('"+oId+"');\">注销</a>";
				  }else {
					  elCell.innerHTML = "<a href=\"#\" onclick=\"logOutOrIn('"+oId+"');\">启动</a>";
				  }
			  }
			  if(oColumn.key=="validStatus"){
			      	switch(oRecord.validStatus){
			      		case '0':elCell.innerHTML="无效";break;
			      		case '1':elCell.innerHTML="有效";break;
			      	}
			   }
			   if(oColumn.key=="copy"){
				 elCell.innerHTML = "<a href=\"#\" onclick=\"editRecord('${ctx}/saaGradeTempl/prepareCopyGradeTempl.do?editType=copy&id="+oId+"')\">复制</a>"
			   }
			    if(oColumn.key=="gradeTemplCName"){
				 elCell.innerHTML = "<a href=\"#\" onclick=\"editRecord('${ctx}/saaGradeTempl/viewGradeTempl.do?editType=view&id="+oId+"')\">"+gradeTemplCName+"</a>"
			   }
			      if(oColumn.key=="query"){
				 	elCell.innerHTML = "<a href=\"#\" onclick=\"editRecord('${ctx}/saaGradeTempl/viewGradeTempl.do?editType=view&id="+oId+"')\">查看</a>"
			   }
		
			 };
	 			contentColumnHeaders =[
				{key:"id",text:"模板代码",width:"15em",sortable:true},
				{key:"gradeTemplCName",text:"模板名称",width:"30em",sortable:true,type:"link"},
			//	{key:"extendTemplID",text:"继承模板",width:"16em",sortable:true},
				 {key:"validStatus",text:"有效标识",width:"16em",sortable:true,type:"link"},
				 //{key:"query",text:"查看",width:"20em",sortable:true,type:"link"},
				 {key:"modify",text:"修改",width:"16em",sortable:true,type:"link"},
				 {key:"copy",text:"复制",width:"16em",sortable:true,type:"link"},
				{key:"logOrOut",text:"启动/注销",width:"20em",type:"link",resizeable:true}
				];
		}
	//查询数据
	var myDataSource ;
	var initialRequest;
	function executeQuery(pageNo,pageSize){
	     if (!YAHOO.quote.data.datacheck('fm')) {
	  	if(checkLen()){
		    alert("界面输入有误，请核实!");
		    return false;
	  	} 

 	 }else if(isNaN(parseInt(pageNo))){
			pageNo = 1;
		}
		if(isNaN(parseInt(pageSize))){
			pageSize = 10;
		}
		var myColumnSet = new YAHOO.widget.ColumnSet(contentColumnHeaders);	
		myDataSource = new YAHOO.util.DataSource("${ctx}/saaGradeTempl/querySaaGradeTemplList.do");
		myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
		myDataSource.responseSchema = {
		   resultsList: "data",
		   fields: ["id","gradeTemplCName","extendTemplID","validStatus"],
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
		function logOutOrIn(id){
			if(confirm("确定要对所选数据进行操作？")){
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
					url = "${ctx}/saaGradeTempl/changeValidStatus.do?id="+id;
					var req = YAHOO.util.Connect.asyncRequest('POST', url, callback,"");
			}else{
				alert("操作已取消");
			}
	    }


	</script>
