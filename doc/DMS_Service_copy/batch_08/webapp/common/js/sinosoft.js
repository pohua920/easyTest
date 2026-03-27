var SINOSOFT = function(){};
SINOSOFT.util =  function(){};
SINOSOFT.util.QueryString = function(queryString){
	this.queryString = queryString;
	var args = queryString.split("&");
	for(var i=0;i<args.length;i++){
		var arg = args[i].split("=");
		this[ arg[0] ] = arg[1];
	}
};

//navigation
SINOSOFT.util.navigation = function(oRequest,oParsedResponse,oCallback,oCaller){ 

	var args = new SINOSOFT.util.QueryString( oRequest.request );
	var pageSize = parseInt(args["pageSize"],10);
	var pageNo = parseInt(args["pageNo"],10);
	
	var totalRecords = contentDataTable.dataSource.totalRecords;
	var navigation = document.getElementsByName(contentDataTable._elContainer.id+"_navigation")
	
	var pageCount = parseInt(Math.ceil( totalRecords * 1.0 / pageSize));
    var markup = "["+ i18n.navigator.page +" "+pageNo +" / "+ pageCount +"]&nbsp;&nbsp;["+ totalRecords +" "+i18n.navigator.records+"]&nbsp;&nbsp;";
    var isFirstPage = (pageNo == 1) ? true : false;
    var isLastPage = (pageNo == pageCount) ? true : false;
    var firstPageLink = (isFirstPage) ?
            " <span class=\"" + YAHOO.widget.DataTable.CLASS_FIRSTPAGE + "\">["+ i18n.navigator.first +"]</span> " :
            " <a href=\"#\" onclick=\"executeQuery(1,"+pageSize+")\" class=\"" + YAHOO.widget.DataTable.CLASS_FIRSTLINK + "\">["+ i18n.navigator.first +"]</a> ";
    var prevPageLink = (isFirstPage) ?
            " <span class=\"" + YAHOO.widget.DataTable.CLASS_PREVPAGE + "\">["+ i18n.navigator.prev +"]</span> " :
            " <a href=\"#\" onclick=\"executeQuery("+ (pageNo-1) +","+pageSize+")\" class=\"" + YAHOO.widget.DataTable.CLASS_PREVLINK + "\">["+ i18n.navigator.prev +"]</a> " ;
    var nextPageLink = (isLastPage) ?
            " <span class=\"" + YAHOO.widget.DataTable.CLASS_NEXTPAGE + "\">["+ i18n.navigator.next +"]</span> " :
            " <a href=\"#\" onclick=\"executeQuery("+ (pageNo+1) +","+pageSize+")\" class=\"" + YAHOO.widget.DataTable.CLASS_NEXTLINK + "\">["+ i18n.navigator.next +"]</a> " ;
    var lastPageLink = (isLastPage) ?
            " <span class=\"" + YAHOO.widget.DataTable.CLASS_LASTPAGE + "\">["+ i18n.navigator.last +"]</span> " :
            " <a href=\"#\" onclick=\"executeQuery("+ pageCount +","+pageSize+")\" class=\"" + YAHOO.widget.DataTable.CLASS_LASTLINK + "\">["+ i18n.navigator.last +"]</a> ";
    var selectOption = " <select class=\""+ YAHOO.widget.DataTable.CLASS_PAGESELECT +"\" onchange=\"executeQuery(1,this.value)\">";
    var arrOptions = [10,20,50];
    for(var i=0; i<arrOptions.length; i++){
	    var optionI = (pageSize==arrOptions[i])?
	    		"<option value="+arrOptions[i]+" selected>"+arrOptions[i]+"</option>":
	    		"<option value="+arrOptions[i]+">"+arrOptions[i]+"</option>";
	    selectOption += optionI;
    }
    selectOption += "</select>";
    markup += firstPageLink + prevPageLink;
    markup += nextPageLink + lastPageLink;
    markup += selectOption;
    for(var i=0;i<navigation.length;i++)
    {
	    if(totalRecords>0){
		    navigation[i].innerHTML = markup; 
		}else{

	        var elBody = contentDataTable._elBody;
	        var elRows = contentDataTable._elBody.rows;
	        while(elBody.hasChildNodes() && (elRows.length > 0)) {
	            elBody.deleteRow(elRows.length-1);
	        }
			navigation[i].innerHTML = "";
		}
	}
}; 

//navigation
SINOSOFT.util.navigation1 = function(oRequest,oParsedResponse,oCallback,oCaller){ 

	var args = new SINOSOFT.util.QueryString( oRequest.request );
	var pageSize = parseInt(args["pageSize"],10);
	var pageNo = parseInt(args["pageNo"],10);
	
	var totalRecords = contentDataTable1.dataSource.totalRecords;
	var navigation = document.getElementsByName(contentDataTable1._elContainer.id+"_navigation")
	
	var pageCount = parseInt(Math.ceil( totalRecords * 1.0 / pageSize));
    var markup = "["+ i18n.navigator.page +" "+pageNo +" / "+ pageCount +"]&nbsp;&nbsp;["+ totalRecords +" "+i18n.navigator.records+"]&nbsp;&nbsp;";
    var isFirstPage = (pageNo == 1) ? true : false;
    var isLastPage = (pageNo == pageCount) ? true : false;
    var firstPageLink = (isFirstPage) ?
            " <span class=\"" + YAHOO.widget.DataTable.CLASS_FIRSTPAGE + "\">["+ i18n.navigator.first +"]</span> " :
            " <a href=\"#\" onclick=\"executeQuery1(1,"+pageSize+")\" class=\"" + YAHOO.widget.DataTable.CLASS_FIRSTLINK + "\">["+ i18n.navigator.first +"]</a> ";
    var prevPageLink = (isFirstPage) ?
            " <span class=\"" + YAHOO.widget.DataTable.CLASS_PREVPAGE + "\">["+ i18n.navigator.prev +"]</span> " :
            " <a href=\"#\" onclick=\"executeQuery1("+ (pageNo-1) +","+pageSize+")\" class=\"" + YAHOO.widget.DataTable.CLASS_PREVLINK + "\">["+ i18n.navigator.prev +"]</a> " ;
    var nextPageLink = (isLastPage) ?
            " <span class=\"" + YAHOO.widget.DataTable.CLASS_NEXTPAGE + "\">["+ i18n.navigator.next +"]</span> " :
            " <a href=\"#\" onclick=\"executeQuery1("+ (pageNo+1) +","+pageSize+")\" class=\"" + YAHOO.widget.DataTable.CLASS_NEXTLINK + "\">["+ i18n.navigator.next +"]</a> " ;
    var lastPageLink = (isLastPage) ?
            " <span class=\"" + YAHOO.widget.DataTable.CLASS_LASTPAGE + "\">["+ i18n.navigator.last +"]</span> " :
            " <a href=\"#\" onclick=\"executeQuery1("+ pageCount +","+pageSize+")\" class=\"" + YAHOO.widget.DataTable.CLASS_LASTLINK + "\">["+ i18n.navigator.last +"]</a> ";
    var selectOption = " <select class=\""+ YAHOO.widget.DataTable.CLASS_PAGESELECT +"\" onchange=\"executeQuery1(1,this.value)\">";
    var arrOptions = [10,20,50];
    for(var i=0; i<arrOptions.length; i++){
	    var optionI = (pageSize==arrOptions[i])?
	    		"<option value="+arrOptions[i]+" selected>"+arrOptions[i]+"</option>":
	    		"<option value="+arrOptions[i]+">"+arrOptions[i]+"</option>";
	    selectOption += optionI;
    }
    selectOption += "</select>";
    markup += firstPageLink + prevPageLink;
    markup += nextPageLink + lastPageLink;
    markup += selectOption;
    for(var i=0;i<navigation.length;i++)
    {
	    if(totalRecords>0){
		    navigation[i].innerHTML = markup; 
		}else{

	        var elBody = contentDataTable1._elBody;
	        var elRows = contentDataTable1._elBody.rows;
	        while(elBody.hasChildNodes() && (elRows.length > 0)) {
	            elBody.deleteRow(elRows.length-1);
	        }
			navigation[i].innerHTML = "";
		}
	}
}; 

//navigation
SINOSOFT.util.navigation2 = function(oRequest,oParsedResponse,oCallback,oCaller){ 

	var args = new SINOSOFT.util.QueryString( oRequest.request );
	var pageSize = parseInt(args["pageSize"],10);
	var pageNo = parseInt(args["pageNo"],10);
	
	var totalRecords = contentDataTable2.dataSource.totalRecords;
	var navigation = document.getElementsByName(contentDataTable2._elContainer.id+"_navigation")
	
	var pageCount = parseInt(Math.ceil( totalRecords * 1.0 / pageSize));
    var markup = "["+ i18n.navigator.page +" "+pageNo +" / "+ pageCount +"]&nbsp;&nbsp;["+ totalRecords +" "+i18n.navigator.records+"]&nbsp;&nbsp;";
    var isFirstPage = (pageNo == 1) ? true : false;
    var isLastPage = (pageNo == pageCount) ? true : false;
    var firstPageLink = (isFirstPage) ?
            " <span class=\"" + YAHOO.widget.DataTable.CLASS_FIRSTPAGE + "\">["+ i18n.navigator.first +"]</span> " :
            " <a href=\"#\" onclick=\"executeQuery2(1,"+pageSize+")\" class=\"" + YAHOO.widget.DataTable.CLASS_FIRSTLINK + "\">["+ i18n.navigator.first +"]</a> ";
    var prevPageLink = (isFirstPage) ?
            " <span class=\"" + YAHOO.widget.DataTable.CLASS_PREVPAGE + "\">["+ i18n.navigator.prev +"]</span> " :
            " <a href=\"#\" onclick=\"executeQuery2("+ (pageNo-1) +","+pageSize+")\" class=\"" + YAHOO.widget.DataTable.CLASS_PREVLINK + "\">["+ i18n.navigator.prev +"]</a> " ;
    var nextPageLink = (isLastPage) ?
            " <span class=\"" + YAHOO.widget.DataTable.CLASS_NEXTPAGE + "\">["+ i18n.navigator.next +"]</span> " :
            " <a href=\"#\" onclick=\"executeQuery2("+ (pageNo+1) +","+pageSize+")\" class=\"" + YAHOO.widget.DataTable.CLASS_NEXTLINK + "\">["+ i18n.navigator.next +"]</a> " ;
    var lastPageLink = (isLastPage) ?
            " <span class=\"" + YAHOO.widget.DataTable.CLASS_LASTPAGE + "\">["+ i18n.navigator.last +"]</span> " :
            " <a href=\"#\" onclick=\"executeQuery2("+ pageCount +","+pageSize+")\" class=\"" + YAHOO.widget.DataTable.CLASS_LASTLINK + "\">["+ i18n.navigator.last +"]</a> ";
    var selectOption = " <select class=\""+ YAHOO.widget.DataTable.CLASS_PAGESELECT +"\" onchange=\"executeQuery2(1,this.value)\">";
    var arrOptions = [10,20,50];
    for(var i=0; i<arrOptions.length; i++){
	    var optionI = (pageSize==arrOptions[i])?
	    		"<option value="+arrOptions[i]+" selected>"+arrOptions[i]+"</option>":
	    		"<option value="+arrOptions[i]+">"+arrOptions[i]+"</option>";
	    selectOption += optionI;
    }
    selectOption += "</select>";
    markup += firstPageLink + prevPageLink;
    markup += nextPageLink + lastPageLink;
    markup += selectOption;
    for(var i=0;i<navigation.length;i++)
    {
	    if(totalRecords>0){
		    navigation[i].innerHTML = markup; 
		}else{

	        var elBody = contentDataTable2._elBody;
	        var elRows = contentDataTable2._elBody.rows;
	        while(elBody.hasChildNodes() && (elRows.length > 0)) {
	            elBody.deleteRow(elRows.length-1);
	        }
			navigation[i].innerHTML = "";
		}
	    
	}
}; 

//navigation
SINOSOFT.util.navigation3 = function(oRequest,oParsedResponse,oCallback,oCaller){ 

	var args = new SINOSOFT.util.QueryString( oRequest.request );
	var pageSize = parseInt(args["pageSize"],10);
	var pageNo = parseInt(args["pageNo"],10);
	
	var totalRecords = contentDataTable3.dataSource.totalRecords;
	var navigation = document.getElementsByName(contentDataTable3._elContainer.id+"_navigation")
	
	var pageCount = parseInt(Math.ceil( totalRecords * 1.0 / pageSize));
    var markup = "["+ i18n.navigator.page +" "+pageNo +" / "+ pageCount +"]&nbsp;&nbsp;["+ totalRecords +" "+i18n.navigator.records+"]&nbsp;&nbsp;";
    var isFirstPage = (pageNo == 1) ? true : false;
    var isLastPage = (pageNo == pageCount) ? true : false;
    var firstPageLink = (isFirstPage) ?
            " <span class=\"" + YAHOO.widget.DataTable.CLASS_FIRSTPAGE + "\">["+ i18n.navigator.first +"]</span> " :
            " <a href=\"#\" onclick=\"executeQuery3(1,"+pageSize+")\" class=\"" + YAHOO.widget.DataTable.CLASS_FIRSTLINK + "\">["+ i18n.navigator.first +"]</a> ";
    var prevPageLink = (isFirstPage) ?
            " <span class=\"" + YAHOO.widget.DataTable.CLASS_PREVPAGE + "\">["+ i18n.navigator.prev +"]</span> " :
            " <a href=\"#\" onclick=\"executeQuery3("+ (pageNo-1) +","+pageSize+")\" class=\"" + YAHOO.widget.DataTable.CLASS_PREVLINK + "\">["+ i18n.navigator.prev +"]</a> " ;
    var nextPageLink = (isLastPage) ?
            " <span class=\"" + YAHOO.widget.DataTable.CLASS_NEXTPAGE + "\">["+ i18n.navigator.next +"]</span> " :
            " <a href=\"#\" onclick=\"executeQuery3("+ (pageNo+1) +","+pageSize+")\" class=\"" + YAHOO.widget.DataTable.CLASS_NEXTLINK + "\">["+ i18n.navigator.next +"]</a> " ;
    var lastPageLink = (isLastPage) ?
            " <span class=\"" + YAHOO.widget.DataTable.CLASS_LASTPAGE + "\">["+ i18n.navigator.last +"]</span> " :
            " <a href=\"#\" onclick=\"executeQuery3("+ pageCount +","+pageSize+")\" class=\"" + YAHOO.widget.DataTable.CLASS_LASTLINK + "\">["+ i18n.navigator.last +"]</a> ";
    var selectOption = " <select class=\""+ YAHOO.widget.DataTable.CLASS_PAGESELECT +"\" onchange=\"executeQuery3(1,this.value)\">";
    var arrOptions = [10,20,50];
    for(var i=0; i<arrOptions.length; i++){
	    var optionI = (pageSize==arrOptions[i])?
	    		"<option value="+arrOptions[i]+" selected>"+arrOptions[i]+"</option>":
	    		"<option value="+arrOptions[i]+">"+arrOptions[i]+"</option>";
	    selectOption += optionI;
    }
    selectOption += "</select>";
    markup += firstPageLink + prevPageLink;
    markup += nextPageLink + lastPageLink;
    markup += selectOption;
    for(var i=0;i<navigation.length;i++)
    {
	    if(totalRecords>0){
		    navigation[i].innerHTML = markup; 
		}else{

	        var elBody = contentDataTable3._elBody;
	        var elRows = contentDataTable3._elBody.rows;
	        while(elBody.hasChildNodes() && (elRows.length > 0)) {
	            elBody.deleteRow(elRows.length-1);
	        }
			navigation[i].innerHTML = "";
		}
	}
}; 

//navigation
SINOSOFT.util.navigation4 = function(oRequest,oParsedResponse,oCallback,oCaller){ 

	var args = new SINOSOFT.util.QueryString( oRequest.request );
	var pageSize = parseInt(args["pageSize"],10);
	var pageNo = parseInt(args["pageNo"],10);
	
	var totalRecords = contentDataTable4.dataSource.totalRecords;
	var navigation = document.getElementsByName(contentDataTable4._elContainer.id+"_navigation")
	
	var pageCount = parseInt(Math.ceil( totalRecords * 1.0 / pageSize));
    var markup = "["+ i18n.navigator.page +" "+pageNo +" / "+ pageCount +"]&nbsp;&nbsp;["+ totalRecords +" "+i18n.navigator.records+"]&nbsp;&nbsp;";
    var isFirstPage = (pageNo == 1) ? true : false;
    var isLastPage = (pageNo == pageCount) ? true : false;
    var firstPageLink = (isFirstPage) ?
            " <span class=\"" + YAHOO.widget.DataTable.CLASS_FIRSTPAGE + "\">["+ i18n.navigator.first +"]</span> " :
            " <a href=\"#\" onclick=\"executeQuery4(1,"+pageSize+")\" class=\"" + YAHOO.widget.DataTable.CLASS_FIRSTLINK + "\">["+ i18n.navigator.first +"]</a> ";
    var prevPageLink = (isFirstPage) ?
            " <span class=\"" + YAHOO.widget.DataTable.CLASS_PREVPAGE + "\">["+ i18n.navigator.prev +"]</span> " :
            " <a href=\"#\" onclick=\"executeQuery4("+ (pageNo-1) +","+pageSize+")\" class=\"" + YAHOO.widget.DataTable.CLASS_PREVLINK + "\">["+ i18n.navigator.prev +"]</a> " ;
    var nextPageLink = (isLastPage) ?
            " <span class=\"" + YAHOO.widget.DataTable.CLASS_NEXTPAGE + "\">["+ i18n.navigator.next +"]</span> " :
            " <a href=\"#\" onclick=\"executeQuery4("+ (pageNo+1) +","+pageSize+")\" class=\"" + YAHOO.widget.DataTable.CLASS_NEXTLINK + "\">["+ i18n.navigator.next +"]</a> " ;
    var lastPageLink = (isLastPage) ?
            " <span class=\"" + YAHOO.widget.DataTable.CLASS_LASTPAGE + "\">["+ i18n.navigator.last +"]</span> " :
            " <a href=\"#\" onclick=\"executeQuery4("+ pageCount +","+pageSize+")\" class=\"" + YAHOO.widget.DataTable.CLASS_LASTLINK + "\">["+ i18n.navigator.last +"]</a> ";
    var selectOption = " <select class=\""+ YAHOO.widget.DataTable.CLASS_PAGESELECT +"\" onchange=\"executeQuery4(1,this.value)\">";
    var arrOptions = [10,20,50];
    for(var i=0; i<arrOptions.length; i++){
	    var optionI = (pageSize==arrOptions[i])?
	    		"<option value="+arrOptions[i]+" selected>"+arrOptions[i]+"</option>":
	    		"<option value="+arrOptions[i]+">"+arrOptions[i]+"</option>";
	    selectOption += optionI;
    }
    selectOption += "</select>";
    markup += firstPageLink + prevPageLink;
    markup += nextPageLink + lastPageLink;
    markup += selectOption;
    for(var i=0;i<navigation.length;i++)
    {
	    if(totalRecords>0){
		    navigation[i].innerHTML = markup; 
		}else{

	        var elBody = contentDataTable4._elBody;
	        var elRows = contentDataTable4._elBody.rows;
	        while(elBody.hasChildNodes() && (elRows.length > 0)) {
	            elBody.deleteRow(elRows.length-1);
	        }
			navigation[i].innerHTML = "";
		}
	}
}; 

//navigation
SINOSOFT.util.navigation5 = function(oRequest,oParsedResponse,oCallback,oCaller){ 

	var args = new SINOSOFT.util.QueryString( oRequest.request );
	var pageSize = parseInt(args["pageSize"],10);
	var pageNo = parseInt(args["pageNo"],10);
	
	var totalRecords = contentDataTable5.dataSource.totalRecords;
	var navigation = document.getElementsByName(contentDataTable5._elContainer.id+"_navigation")
	
	var pageCount = parseInt(Math.ceil( totalRecords * 1.0 / pageSize));
    var markup = "["+ i18n.navigator.page +" "+pageNo +" / "+ pageCount +"]&nbsp;&nbsp;["+ totalRecords +" "+i18n.navigator.records+"]&nbsp;&nbsp;";
    var isFirstPage = (pageNo == 1) ? true : false;
    var isLastPage = (pageNo == pageCount) ? true : false;
    var firstPageLink = (isFirstPage) ?
            " <span class=\"" + YAHOO.widget.DataTable.CLASS_FIRSTPAGE + "\">["+ i18n.navigator.first +"]</span> " :
            " <a href=\"#\" onclick=\"executeQuery5(1,"+pageSize+")\" class=\"" + YAHOO.widget.DataTable.CLASS_FIRSTLINK + "\">["+ i18n.navigator.first +"]</a> ";
    var prevPageLink = (isFirstPage) ?
            " <span class=\"" + YAHOO.widget.DataTable.CLASS_PREVPAGE + "\">["+ i18n.navigator.prev +"]</span> " :
            " <a href=\"#\" onclick=\"executeQuery5("+ (pageNo-1) +","+pageSize+")\" class=\"" + YAHOO.widget.DataTable.CLASS_PREVLINK + "\">["+ i18n.navigator.prev +"]</a> " ;
    var nextPageLink = (isLastPage) ?
            " <span class=\"" + YAHOO.widget.DataTable.CLASS_NEXTPAGE + "\">["+ i18n.navigator.next +"]</span> " :
            " <a href=\"#\" onclick=\"executeQuery5("+ (pageNo+1) +","+pageSize+")\" class=\"" + YAHOO.widget.DataTable.CLASS_NEXTLINK + "\">["+ i18n.navigator.next +"]</a> " ;
    var lastPageLink = (isLastPage) ?
            " <span class=\"" + YAHOO.widget.DataTable.CLASS_LASTPAGE + "\">["+ i18n.navigator.last +"]</span> " :
            " <a href=\"#\" onclick=\"executeQuery5("+ pageCount +","+pageSize+")\" class=\"" + YAHOO.widget.DataTable.CLASS_LASTLINK + "\">["+ i18n.navigator.last +"]</a> ";
    var selectOption = " <select class=\""+ YAHOO.widget.DataTable.CLASS_PAGESELECT +"\" onchange=\"executeQuery5(1,this.value)\">";
    var arrOptions = [10,20,50];
    for(var i=0; i<arrOptions.length; i++){
	    var optionI = (pageSize==arrOptions[i])?
	    		"<option value="+arrOptions[i]+" selected>"+arrOptions[i]+"</option>":
	    		"<option value="+arrOptions[i]+">"+arrOptions[i]+"</option>";
	    selectOption += optionI;
    }
    selectOption += "</select>";
    markup += firstPageLink + prevPageLink;
    markup += nextPageLink + lastPageLink;
    markup += selectOption;
    for(var i=0;i<navigation.length;i++)
    {
	    if(totalRecords>0){
		    navigation[i].innerHTML = markup; 
		}else{

	        var elBody = contentDataTable5._elBody;
	        var elRows = contentDataTable5._elBody.rows;
	        while(elBody.hasChildNodes() && (elRows.length > 0)) {
	            elBody.deleteRow(elRows.length-1);
	        }
			navigation[i].innerHTML = "";
		}
	}
}; 


//edit record
function editRecordBig(url){
	var newWindow = openWindow(contextRootPath+"/common/loading/openLoading.html", "_blank", "alwaysRaised,channelmode,status,scrollbars,resizable");
	newWindow.location=url;
}
//2009.8.5 滑立敏  修改  弹出窗口大小
function editRecord(url){
	var width=700;
	var height=400;
	 var left, top;
     left = (window.screen.availWidth - width) / 2;
     top = (window.screen.availHeight - height) / 2;
     var per = " ,width="+width+",height="+height+",left="+left+",top="+top;
	var newWindow = openWindow(contextRootPath+"/common/loading/openLoading.html", "_blank", "alwaysRaised,status,scrollbars,resizable"+per);
	newWindow.location=url;
}
//show record
function showRecordBig(url){
	var newWindow = openWindow(contextRootPath+"/common/loading/openLoading.html", "_blank", "alwaysRaised,channelmode,status,scrollbars,resizable");
	newWindow.location=url;
}
//2009.8.5 滑立敏  修改  弹出窗口大小
function showRecord(url){
	var width=700;
	var height=400;
	 var left, top;
     left = (window.screen.availWidth - width) / 2;
     top = (window.screen.availHeight - height) / 2;
     var per = " ,width=" + width + ",height=" + height + ",left=" + left + ",top=" + top +',screenX=' + left + ',screenY=' + top;
	var newWindow = openWindow(contextRootPath+"/common/loading/openLoading.html", "_blank", "alwaysRaised,status,scrollbars,resizable"+per);
	newWindow.location=url;
}
//------------------------------------------------------
//edit record over
function editRecordOver(){
	YAHOO.query.container["dialogEdit"].hide();
	var args = new SINOSOFT.util.QueryString( contentDataTable.initialRequest );
	var pageSize = parseInt(args["pageSize"],10);
	var pageNo = parseInt(args["pageNo"],10);
	executeQuery(pageNo,pageSize);
}

//init Edit Dialog
function initEditDialog(dialogid,iframeid){
	//return;
	var handleSubmit = function() {
		//document.frames(iframeid).window.submitForm();
		//document.frames(iframeid).document.all("fm").submit();
		
		document.getElementById(iframeid).contentWindow.document.forms(0).submit();
	};
	var handleCancel = function() {
		this.cancel();
	};

	YAHOO.query.container[dialogid] = new YAHOO.widget.Dialog(dialogid, 
																{ width : "810px",
																  fixedcenter : true,
																  visible : false, 
																  constraintoviewport : true
																  //,buttons : [ { text:i18n.prompt.ok, handler:handleSubmit, isDefault:true},
																  //			  { text:i18n.prompt.cancel, handler:handleCancel } ]
																 } );

	YAHOO.query.container[dialogid].render();	 
}

//init Show Dialog
function initShowDialog(dialogid,iframeid){
	//return;
	var handleCancel = function() {
		this.cancel();
	};

	YAHOO.query.container[dialogid] = new YAHOO.widget.Dialog(dialogid, 
																{ width : "810px",
																  fixedcenter : true,
																  visible : false, 
																  constraintoviewport : true,
																  buttons : [ { text:i18n.prompt.ok, handler:handleCancel, isDefault:true } ]
																 } );

	YAHOO.query.container[dialogid].render();	 
}

//delete record
function deleteRecord(url){
	if(confirm("确认要删除?")){
	var handleSuccess = function(o){
		var args = new SINOSOFT.util.QueryString( contentDataTable.initialRequest );
		var pageSize = parseInt(args["pageSize"],10);
		var pageNo = parseInt(args["pageNo"],10);
		executeQuery(pageNo,pageSize);
	};
	var handleFailure = function(o){
		if(o.responseText !== undefined){
			//var msg = i18n.errors.deletefail+"!\n"+ o.status +" " + o.statusText;
			var msg = "您没有改功能权限！";
			alert(msg);
		}
	};
	var callback =
	{
	  success:handleSuccess,
	  failure:handleFailure
	};
	var req = YAHOO.util.Connect.asyncRequest('POST', url, callback, "");
	}
}



function checkLength(field)
{
  var str;
  var count  = 0;
  var value  = field.value;
  var length = field.maxLength;

  if(value=="")
  {
    return true;
  }


  if(isNaN(parseInt(length)))
    return true;

  for(var i=0;i<value.length;i++)
  {
    str = escape(value.charAt(i));
    if(str.substring(0,2)=="%u" && str.length==6)
      count = count + 2;
    else
      count = count + 1;
  }

  if(count>length)
  {
    alert("��������ݳ�����\n" +  "��󳤶�Ϊ" + length + "��Ӣ���ַ�\n���������룡");
    field.focus();
    field.select();
    return false;
  }
  return true;
}

function compareFullDate(date1,date2)
{
  var DATE_DELIMITER = "/";
  if(date1.indexOf(DATE_DELIMITER)<0)
     DATE_DELIMITER = "-";
  var strValue1=date1.split(DATE_DELIMITER);
  var date1Temp=new Date(strValue1[0],parseInt(strValue1[1],10)-1,parseInt(strValue1[2],10));

  var strValue2=date2.split(DATE_DELIMITER);
  var date2Temp=new Date(strValue2[0],parseInt(strValue2[1],10)-1,parseInt(strValue2[2],10));

  if(date1Temp.getTime()==date2Temp.getTime())
    return 0;
  else if(date1Temp.getTime()>date2Temp.getTime())
    return 1;
  else
    return -1;
}

SINOSOFT.util.dateCompare = function(key,sortdir){
	var func = null;
	if(sortdir=="desc"){
		func = function(a,b){
			var diff = a[key].time - b[key].time;
			if(diff<0){
				return 1;
			}if(diff>0){
				return -1;
			}else{
				return 0;
			}
		};
	}else if(sortdir=="asc"){
		func = function(a,b){
			var diff = a[key].time - b[key].time;
			if(diff<0){
				return -1;
			}if(diff>0){
				return 1;
			}else{
				return 0;
			}
		};
	}else{
	}
	return func;
}

