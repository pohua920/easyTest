var params;

function pageLoadIni(){
	params = {
			"pageNo" : pageNo,
			"pageSize" : pageSize,
	        "projectDto.comCode" : $("#comCode").val(),
	        "projectDto.projectTitle" : $("#projectTitle").val(),
	        "projectDto.beginTime" : $("#beginTime").val(),
	        "projectDto.endTime" : $("#endTime").val(),
	        "projectDto.isHotFlag" : $("#hotFlag").val()
        	};
}

function zhAdd(){
	//var data = $("#fm").serializeArray();
	//var data = $("#fm").serialize();
	//alert(fm.insuredName[0].value);
	//alert($("#insuredName")[0].value);
	//alert($('input[name="insuredName"]').index());
	//alert($('input[name="insuredName"]').length);
	//alert(encodeURI(encodeURI($('input[name="insuredName"]').val())));
	var data = {
			"zhInfoVo.zhNo" : encodeURI($("#zhNo1").val(),"utf-8"),
	        "zhInfoVo.insuredName" : encodeURI($("#insuredName1").val(),"utf-8"),
	        "zhInfoVo.zhCode" : encodeURI($("#zhCode1").val(),"utf-8"),
	        "zhInfoVo.zhText" : encodeURI($("#zhText1").val(),"utf-8"),
	        "zhInfoVo.zhDate" : encodeURI($("#zhDate1").val(),"utf-8"),
	        "zhInfoVo.firstDate" : encodeURI($("#firstDate1").val(),"utf-8"),
	        "zhInfoVo.secondDate" : encodeURI($("#secondDate1").val(),"utf-8"),
	        "zhInfoVo.thirdDate" : encodeURI($("#thirdDate1").val(),"utf-8"),
	        "zhInfoVo.dealStatus" : encodeURI($("#dealStatus1").val(),"utf-8"),
	        "zhInfoVo.replyDate" : encodeURI($("#replyDate1").val(),"utf-8")
        	};
	$.getJSON("/undwrt/taskCheck/zhAdd.do",data,zhShow);
}
function zhShow(json){
	 var innerHtml = "";
	 var startIndex = json.startIndex;
	 var recordsReturned = json.totalRecords;
	 innerHtml += "<table class=\"common\" cellpadding=\"5\" cellspacing=\"1\" align=\"center\">" +
	 "	<thead><tr class=listtitle><td colspan=\"11\">照會訊息</td></tr></thead>" +
	 "	<tbody>" +
	 "		<tr class=common>" +
	 "			<td width=\"4%\">" +
	 "				序號</td>" +
	 "			<td width=\"10%\">" +
	 "				被保險人</td>" +
	 "			<td width=\"16%\" colspan=\"2\">" +
	 "				照會代碼</td>" +
	 "			<td width=\"10%\">" +
	 "				照會日期</td>" +
	 "			<td width=\"10%\">" +
	 "				第一次回覆期限</td>" +
	 "			<td width=\"10%\">" +
	 "				第二次回覆期限</td>" +
	 "			<td width=\"10%\">" +
	 "				第三次回覆期限</td>" +
	 "			<td width=\"10%\">" +
	 "				處理狀態 <br>Y已處理 N未處理</td>" +
	 "			<td width=\"10%\">" +
	 "				照會回覆日期</td>" +
	 "			<td width=\"10%\">" +
	 "				操作</td></tr>";
	 for(startIndex; startIndex<recordsReturned; startIndex++){
	 innerHtml += "<tr class=common><td width=\"4%\">" +
	 "				<input class=\"formtitle1\" readonly name=\"zhNo\" value=\""+ json.data[startIndex].zhNo + "\"/></td>                            	" +
	 "			<td width=\"10%\">" +
	 "				<input class=\"free\" name=\"insuredName\" value=\""+ json.data[startIndex].insuredName + "\"/></td>                  	" +
	 "			<td width=\"8%\">" +
	 "				<input class=\"free\" name=\"zhCode\" value=\""+ json.data[startIndex].zhCode + "\"/></td>                            	" +
	 "			<td width=\"8%\">" +
	 "				<input class=\"button\" type=\"button\" name=\"zhTextButton\" value=\"照會內容\" onclick=\"show_zhText(this,'zhText_sub_span')\"/> " +
	 "				<span id=\"zhText_sub_span\" style=\"display:none;position:absolute;background-color:C0C0C0;\">" +
	 "					<table class=\"sub\">" +
	 "						<tr><td class=\"title\">																											" +
	 "							<textarea name=\"zhText\" rows=\"10\" cols=\"50\" class=\"common3\">"+ json.data[startIndex].zhText + "</textarea>		" +
	 "						</td></tr>" +
	 "						<tr><td align=\"center\">                                                                                               	" +
	 "							<input type=\"button\" name=\"zhTextclose\" class=\"button\" alt=\"确定\" value=\"确 定\" " +
	 "								onclick=\"close_zhText(this,'zhText_sub_span')\">   " +
	 "						</td></tr>" +
	 "					</table>" +
	 "				</span></td>                                                                                                                  		" +
	 "			<td width=\"10%\">" +
	 "				<input type=\"hidden\" class=\"free\" name=\"zhDate\" value=\"\"/>                   		" +
	 "				<input class=\"free\" name=\"zhDateRC\" value=\""+ json.data[startIndex].zhDate + "\" " +
	 "					onFocus=\"WdatePicker({dateFmt:'yyy-MM-dd'})\" onchange=\"getToRcDateValue(fm,this)\"/></td>" +
	 "			<td width=\"10%\">" +
	 "				<input type=\"hidden\" class=\"free\" name=\"firstDate\" value=\"\"/>                      		" +
	 "				<input class=\"free\" name=\"firstDateRC\" value=\""+ json.data[startIndex].firstDate + "\" " +
	 "					onFocus=\"WdatePicker({dateFmt:'yyy-MM-dd'})\" onchange=\"getToRcDateValue(fm,this)\"/></td>" +
	 "			<td width=\"10%\">" +
	 "				<input type=\"hidden\" class=\"free\" name=\"secondDate\" value=\"\"/>                     	" +
	 "				<input class=\"free\" name=\"secondDateRC\" value=\""+ json.data[startIndex].secondDate + "\" " +
	 "					onFocus=\"WdatePicker({dateFmt:'yyy-MM-dd'})\" onchange=\"getToRcDateValue(fm,this)\"/></td>" +
	 "			<td width=\"10%\">" +
	 "				<input type=\"hidden\" class=\"free\" name=\"thirdDate\" value=\"\"/>                     		" +
	 "				<input class=\"free\" name=\"thirdDateRC\" value=\""+ json.data[startIndex].thirdDate + "\" " +
	 "					onFocus=\"WdatePicker({dateFmt:'yyy-MM-dd'})\" onchange=\"getToRcDateValue(fm,this)\"/></td>" +
	 "			<td width=\"10%\">" +
	 "				<input type=\"hidden\" class=\"free\" name=\"dealStatusCopy\" value=\""+ json.data[startIndex].dealStatus + "\"/>" +
	 "				<select class=\"common\" id=\"dealStatus\" name=\"dealStatus\">" +
	 "					<option value=\"1\">Y-已處理</option><option value=\"2\">N-未處理</option></select></td>" +
	 "			<td width=\"10%\">" +
	 "				<input type=\"hidden\" class=\"free\" name=\"replyDate\" value=\"\"/>                         	" +
	 "				<input class=\"free\" name=\"replyDateRC\" value=\""+ json.data[startIndex].replyDate + "\" " +
	 "					onFocus=\"WdatePicker({dateFmt:'yyy-MM-dd'})\" onchange=\"getToRcDateValue(fm,this)\"/></td>" +
	 "			<td width=\"10%\">" +
	 "				<input type=\"button\" class=\"button2\" name=\"zhDeleteB\" value=\"刪除\" onclick=\"deleteRow(this,'ZH')\"/>                        	" +
	 "				<input type=\"button\" class=\"button2\" name=\"zhUpdateB\" value=\"修改\" /></td></tr>";
	 }
	 innerHtml += "</tbody><tfoot><tr class=common>                                                                                               		" +
	 "			<td width=\"4%\">" +
	 "				<input class=\"formtitle1\" readonly id=\"zhNo1\" name=\"zhNo1\" value=\"\"/></td>                                                           	" +
	 "			<td width=\"10%\">" +
	 "				<input class=\"free\" id=\"insuredName1\" name=\"insuredName1\" value=\"\"/></td>                                                                  	" +
	 "			<td width=\"8%\">" +
	 "				<input class=\"free\" id=\"zhCode1\" name=\"zhCode1\" value=\"\"/></td>                                                               	" +
	 "			<td width=\"8%\">" +
	 "				<input class=\"button\" type=\"button\" name=\"zhTextButton1\" value=\"照會內容\" onclick=\"show_zhText(this,'zhText_sub_span')\"/> " +
	 "				<span id=\"zhText_sub_span\" style=\"display:none;position:absolute;background-color:C0C0C0;\">" +
	 "					<table class=\"sub\">" +
	 "						<tr><td class=\"title\">																											" +
	 "							<textarea id=\"zhText1\" name=\"zhText1\" rows=\"10\" cols=\"50\" class=\"common3\"></textarea>		" +
	 "						</td></tr>" +
	 "						<tr><td align=\"center\">                                                                                               	" +
	 "							<input type=\"button\" name=\"zhTextclose1\" class=\"button\" alt=\"确定\" value=\"确 定\" " +
	 "								onclick=\"close_zhText(this,'zhText_sub_span')\">   " +
	 "						</td></tr>" +
	 "					</table>" +
	 "				</span></td>                                                                                                                  		" +
	 "			<td width=\"10%\">" +
	 "				<input type=\"hidden\" class=\"free\" id=\"zhDate1\" name=\"zhDate1\" value=\"\"/>                                                              	" +
	 "				<input class=\"free\" name=\"zhDate1RC\" value=\"\" " +
	 "					onFocus=\"WdatePicker({dateFmt:'yyy-MM-dd'})\" onchange=\"getToRcDateValue(fm,this)\"/></td>" +
	 "			<td width=\"10%\">" +
	 "				<input type=\"hidden\" class=\"free\" id=\"firstDate1\" name=\"firstDate1\" value=\"\"/>                                                                    	" +
	 "				<input class=\"free\" name=\"firstDate1RC\" value=\"\" " +
	 "					onFocus=\"WdatePicker({dateFmt:'yyy-MM-dd'})\" onchange=\"getToRcDateValue(fm,this)\"/></td>" +
	 "			<td width=\"10%\">" +
	 "				<input type=\"hidden\" class=\"free\" id=\"secondDate1\" name=\"secondDate1\" value=\"\"/>                                                                   	" +
	 "				<input class=\"free\" name=\"secondDate1RC\" value=\"\" " +
	 "					onFocus=\"WdatePicker({dateFmt:'yyy-MM-dd'})\" onchange=\"getToRcDateValue(fm,this)\"/></td>" +
	 "			<td width=\"10%\">" +
	 "				<input type=\"hidden\" class=\"free\" id=\"thirdDate1\" name=\"thirdDate1\" value=\"\"/>                                                                    	" +
	 "				<input class=\"free\" name=\"thirdDate1RC\" value=\"\" " +
	 "					onFocus=\"WdatePicker({dateFmt:'yyy-MM-dd'})\" onchange=\"getToRcDateValue(fm,this)\"/></td>" +
	 "			<td width=\"10%\">" +
	 /*"				<input class=\"free\" id=\"dealStatus1\" name=\"dealStatus1\" value=\"\"/>               " +
	 "				<s:select cssClass=\"common\" id=\"dealStatus1\" name=\"dealStatus1\" list=\"#{'11':'Y-已處理','2':'N-未處理'}\" " +
	 "					listKey=\"key\" listValue=\"value\" value=\"\" /></td>" +*/
	 "				<select class=\"common\" id=\"dealStatus1\" name=\"dealStatus1\">" +
	 "					<option value=\"1\">Y-已處理</option><option value=\"2\">N-未處理</option></select></td>" +
	 "			<td width=\"10%\">" +
	 "				<input type=\"hidden\" class=\"free\" id=\"replyDate1\" name=\"replyDate1\" value=\"\"/>                                                                    	" +
	 "				<input class=\"free\" name=\"replyDate1RC\" value=\"\" " +
	 "					onFocus=\"WdatePicker({dateFmt:'yyy-MM-dd'})\" onchange=\"getToRcDateValue(fm,this)\"/></td>" +
	 "			<td width=\"10%\" align=\"center\">" +
	 "				<input type=\"button\" class=\"button2\" name=\"zhAddB\" value=\"新增\" onclick=\"zhAdd();\"/></td>                               	  	" +
	 "			</tr></tfoot></table>                                                                                                            		";
	 
     $("#spanZH").html(innerHtml);
     var zhCount = $('input[name="dealStatusCopy"]').length;
     for(var i=0;i<zhCount;i++){
    	 if(zhCount>1){
    		 fm.dealStatus[i].value = fm.dealStatusCopy[i].value;
    	 }else{
    		 fm.dealStatus.value = fm.dealStatusCopy.value;
    	 }
     }
}

function show_zhText(field,spanID){
	//var intIndex = parseInt(getElementOrder(field),10) -1;
	var span = eval(spanID);
	span.style.display = "";
}
function close_zhText(field,spanID){
	//var intIndex = parseInt(getElementOrder(field),10) - 1;
	//var span = eval(spanID + "(" + intIndex + ")");
	var span = eval(spanID);
	span.style.display ='none';
}

function checkDealStatus(){
	var dealStatusCount = $('input[name="dealStatus"]').length;
	if(fm.hiClassCode.value == "C1" && dealStatusCount > 0){
		if(dealStatusCount == 1){
			if(fm.dealStatus.value== "N"){
				alert("尚有未處理的照會，不能審核通過！");
				return false;
			}
		}else{
			for(var i=0;i<dealStatusCount;i++){
				if(fm.dealStatus[i].value== "N"){
					alert("尚有未處理的照會，不能審核通過！");
					return false;
				}
			}
		}
	}
	return true;
}