//查询信息，并改变页面的页码
function goAjaxCurrPage(page, methodName) {
	methodName(page);
}

// 判断输入頁码是否有效,methodName只是一个字符串，而methodName2是一个方法
function justAjaxPageExist(inputPage, totalPage, methodName, methodName2) {
	if (inputPage > totalPage || inputPage <= 0) {
		jAlert("您輸入的頁碼【" + inputPage + "】不存在，請重新輸入!","提示!");
		$("#" + methodName).val("");
		return false;
	}
	// 如果頁面有效调用方法查询信息
	goAjaxCurrPage(inputPage, methodName2);
	return true;
}
// 分頁頁数的展示,第一个参数是展示頁数的div的id,第二个参数是存放链接图片的div的id，第三个是后台传回的json对象
function showPage(showPageDiv, goImgDiv, obj) {
	var pageString = "";
	pageString += "<div align='right'>滿足條件的記錄爲" + obj.totalCount + " 條　第";
	if (obj.data.length == 0) {
		pageString += "0";
	} else {
		pageString += obj.currentPageNo;
	}
	pageString += " 頁/共" + obj.totalPageCount + " 頁";
	if (obj.data.length == 0 || obj.currentPageNo <= 1) {
		pageString += "&nbsp;&nbsp;<font color='#808080'>首頁</font>&nbsp;<font color='#808080'>前頁</font>";
	} else {
		pageString += "&nbsp;&nbsp;<a href='javascript:goAjaxCurrPage(1,"
				+ obj.methodName + ")'><u>首頁</u></a>";
		pageString += "&nbsp;<a href='javascript:goAjaxCurrPage("
				+ (obj.currentPageNo - 1) + "," + obj.methodName
				+ ")'><u>前頁</u></a>";
	}
	if (obj.data.length != 0 && obj.totalPageCount > obj.currentPageNo) {
		pageString += "&nbsp;<a href='javascript:goAjaxCurrPage("
				+ (obj.currentPageNo + 1) + "," + obj.methodName
				+ ")' ><u>後頁</u></a>";
	} else {
		pageString += "&nbsp;<font color='#808080'>後頁</font>";
	}
	if (obj.data.length != 0 && obj.currentPageNo != obj.totalPageCount && obj.totalPageCount > 0) {
		pageString += "&nbsp;<a href='javascript:goAjaxCurrPage("
				+ obj.totalPageCount + "," + obj.methodName
				+ ")'><u>尾頁</u></a>";
	} else {
		pageString += "&nbsp;<font color='#808080'>尾頁</font>";
	}
	pageString += "&nbsp;&nbsp;跳到<input type='text' id='"
			+ obj.methodName+"TID"
			+ "' size='2' class='common' style='width:3%' maxlength='8'>頁<a href='javascript:void(0)' onclick='return justAjaxPageExist($(\"#"
			+ obj.methodName + "TID\").val()," + obj.totalPageCount + ",\""
			+ obj.methodName + "TID\"," + obj.methodName + ")'></a>";
	$("#" + showPageDiv).html(pageString);
	$("#" + showPageDiv + " a:last").html($("#" + goImgDiv).html());
}