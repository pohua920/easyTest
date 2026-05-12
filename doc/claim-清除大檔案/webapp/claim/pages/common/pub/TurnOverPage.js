/*
****************************************************************************
* DESC       ：单证发放限制脚本 
* AUTHOR     ：liubvo 
* CREATEDATE ：2005-01-25
* MODIFYLIST ：  Name       Date            Reason/Contents
*          ------------------------------------------------------


/**
 @description 分页判断 
 @param       page当前页号
 @return      无 
 */
function currPage( page ){
	document.forms[0].pageNo.value = page;
	//document.forms[0].editType.value = "RegistBeforeQuery";
	document.forms[0].submit();
  } 
   
/**
 @description 前一页
 @param       page当前页号
 @return      无
 */
function PrePage( page ){
	currPage( page-1 );
   }
   
/**
 @description 後一页
 @param       page当前页号
 @return      无
 */
function NextPage( page ){
	currPage( page+1 );
   }
   
/**
 @description 跳到任意页
 @param       无
 @return      无
 */
function ChangePage(){

	var page = document.forms[0].changepage.value;
	if (CheckPage(page)) currPage(page);			    
}


/**
 @description 跳到首页
 @param       无
 @return      无
 */
function FirstPage(){

	var page = 1;	
	currPage(page);			    
}

/**
 @description 跳到尾页
 @param       无
 @return      无
 */
function LastPage(){

	var page = document.forms[0].totalPage.value;	
	currPage(page);			    
}


/**
 @description 校验页数
 @param       无
 @return      无
 */
function CheckPage(pageNo){
  
  var page = parseInt(pageNo);	
  var totalPage=parseInt(document.forms[0].totalPage.value);
	
	if (isNaN(page))
	{
		alert("請輸入合法的頁數！");
    return false;
	}
	
	if (page.toString().length!=pageNo.length)
	{
		alert("请输入整数类型的页数！");
    return false;
	}
	
	if (page<1) 
	{
		alert("跳转的页数不能小於1！");
    return false;
	}
	
	if (page>totalPage) 
	{
		alert("跳轉的頁數不能大於總頁數！");
    return false;
	}
	
	return true;
				    
}
