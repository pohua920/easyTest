    //注销/启动
var contentDataTable;
var initialRequest;
	function logOutOrIn(){
		if(confirm("确定要对所选数据进行操作？")){
		//	alert("是否对以上数据进行操作？");
			var aoCode;
   		 	var codeList;
       		var checkbox = document.getElementsByName("checkboxes");
        	var num = 0;
        	for(var j=0;j<checkbox.length;j++){
				if(checkbox[j].checked){
					num = num + 1;
				}
	  		}
		    if(num == 0){
		        alert("请选择至少一条数据进行修改");
		        window.location.reload();
		    }else{
				for(var i=0;i<num;i++){
					aoCode = checkbox[i].value;
					if(i==0){
						codeList = aoCode;
					}else{
						codeList = codeList+" and "+aoCode;
					}
				}
		    }
		    var args = new SINOSOFT.util.QueryString( contentDataTable.initialRequest );
			var pageSize = parseInt(args["pageSize"],10);
			var pageNo = parseInt(args["pageNo"],10);
			executeQuery(pageNo,pageSize);
			var url = "contextRootPath/utiISvr/changeValidStatus.do?svrcode="+codeList;
			var req = YAHOO.util.Connect.asyncRequest('POST', url, "");
		//	fm.action="contextRootPath/utiISvr/changeValidStatus.do?svrcode="+codeList;
		//  fm.submit();
	//	    window.location.reload();
 	// 	  	window.onload="init();executeQuery()"
	//		return true;
		}else{
			alert("操作已取消");
		}
    }
	YAHOO.util.Event.addListener(window,'load',init);