<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="cn.com.sinosoft.ims.util.IConstants"%>

<html>
<head>
<title>帐户归并</title>
<%@ include file="/common/i18njs.jsp"%>
<%@ include file="/common/meta_css.jsp"%>

</head>

<body id="all_title">

<div id="wrapper">
<div id="container">
<div id="crash_menu">
<h2 align="center">帐户归并</h2>
</div>

<div id="content_navigation" class="query" align="center"></div>
<div id="content" class="sort"></div>
<div id="content_navigation" class="query" align="center"></div>
</div>
<s:form name="fm" action="" >
<input type="hidden" name="userCodeChoose">
	<table class="fix_table">	
		<tr align="center">
			<td align="center"  colspan="4">
                <input type='button' class="button_ty" name=buttonInsert value="确定" onclick="subMethod()">
                <input type='button' class="button_ty" name=buttonInsert value="手工调整" onclick="manulAdjust()">

            </td>
		</tr>
		
	</table>
</s:form></div>

</body>
</html>
<%@ include file="/common/meta_js.jsp"%>
<script language="javascript" src="${ctx}/common/dwr/engine.js"></script>
<script language="javascript" src="${ctx}/common/dwr/util.js"></script>
<script language="javascript" src="${ctx}/common/js/CodeSelect.js"></script>
<script type="text/javascript">
	var contentDataTable;
	var contentColumnHeaders; 
	YAHOO.namespace("query.container"); 
	var userCodeCh;

	
	
	function init(){
		
		 YAHOO.widget.Column.formatLink = function(elCell, oRecord, oColumn, oData) { 
			 //for(var key in oRecord){
            // alert(key+"="+oRecord[key]);
           //   }
			 var userCode = oRecord.userCode;
			 var userName = oRecord.userName;
			 var accCode = oRecord.accCode;
			 var svrCode = oRecord.svrCode;
			 var matchRule = oRecord.matchRule;
			 var matchSimilar = oRecord.matchSimilar;

			 userCodeCh = userCode;
			
			 var accUser = accCode + "and" + userCode;
			 //将需要的字段值拼成串
             //var oId = 	userCode  + "," + userName + "," + accCode + "," + svrCode + "," + matchRule;
             //alert("--ooIdId-"+oId);
			 
			 if(oColumn.key=="serialNo"){
				if(matchSimilar>=90){
					elCell.innerHTML = "<input type=\"checkbox\"  checked=\"true\" name=\"checkboxes\" value="+accUser+" ></input>";
					
				}else if(matchSimilar>=50){
					elCell.innerHTML = "<input type=\"checkbox\"  name=\"checkboxes\" value="+accUser+" ></input>";
				}else{
					elCell.innerHTML = "<input type=\"checkbox\" name=\"checkboxes\" value="+accCode+" ></input>";
				}
	    		  
		     } 
		    
		    
		 };
		
		contentColumnHeaders =[

            {key:"serialNo",text:"序号",width:"10em",type:"link",resizeable:true},
			{key:"svrCode",text:"服务代码",width:"20em",sortable:true},
			{key:"svrName",text:"服务名称",width:"20em",sortable:true},
			{key:"accCode",text:"账号代码",width:"20em",sortable:true},
			{key:"accName",text:"账号名称",width:"20em",sortable:true},
			{key:"userCode",text:"用户代码",width:"30em",sortable:true},
			{key:"userName",text:"用户名称",width:"20em",sortable:true},
			{key:"matchRule",text:"匹配规则",width:"20em",sortable:true},
			{key:"matchSimilar",text:"相似度(%)",width:"20em",sortable:true}
			
			]; 
		executeQuery(1,10);
	}
	//Query Data
	function executeQuery(pageNo,pageSize){
		
		if(isNaN(parseInt(pageNo))){ 
			pageNo = 1;
		}
		if(isNaN(parseInt(pageSize))){
			pageSize = 10;
		}
		
		var myColumnSet = new YAHOO.widget.ColumnSet(contentColumnHeaders);	
		var myDataSource = new YAHOO.util.DataSource("${ctx}/utiIUser/QueryUserAcc.do");
	    myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
		myDataSource.responseSchema = {
		   resultsList: "data",
		   fields: ["svrCode", "svrName","accCode",
						"accName","userCode", "userName", "matchRule","matchSimilar"],
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
	
	// 确定  
    function subMethod(){

    	var aoCode;
        var checkbox = document.getElementsByName("checkboxes");
        var num = 0;
        var codeList ;
       
        
        for(var j=0;j<checkbox.length;j++){
			if(checkbox[j].checked){
				num = num + 1;
			}
        }
        if(num == 0){
        	alert("请至少选择一项进行确定！");
        	return false;
        }else{
			for(var i=0;i<checkbox.length;i++){
				if(checkbox[i].checked){
					aoCode = checkbox[i].value;
					if(aoCode.indexOf('and') < 0 ){
						return false;
					}
					if(num==1){
						codeList = aoCode;						
					}else{
						codeList = codeList+","+aoCode;
					}
				}
			}
			fm.action="contextRootPath/utiIUser/updateUtiIAccAtrr.do?strKey="+codeList;
	        fm.target="page";
	        fm.submit();
	        return true;
	    }

	}
	//手工调整
	function manulAdjust(){
		var aoCode;
        var checkbox = document.getElementsByName("checkboxes");
        var num = 0;
        for(var j=0;j<checkbox.length;j++){
			if(checkbox[j].checked){
				num = num + 1;
			}
        }
        if(num == 0){
        	alert("请选择一项进行调整！");
        }
        else if(num>1){
			alert("只能选择一项进行调整！");
        }else{
	        for(var i=0;i<checkbox.length;i++){
				if(checkbox[i].checked){
					aoCode = checkbox[i].value ;
					fm.action="contextRootPath/utiIUser/preManualAdjust.do?accCode="+aoCode;
					fm.target="page";
					fm.submit();
					
				}
	        }
        return true;
        }
	}
	
	</script>