<%--
****************************************************************************
* DESC       ：已超时报案查询结果显示页面
* AUTHOR     ：中科软
* MODIFYLIST ：id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<%@include file="/common/i18njs.jsp"%>
<%@include file="/common/meta_css.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<title><s:text name="title.registBeforeEdit.titleName" /></title>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<%--原因：向页面中增加一个打印按钮--%>
<script src="/claim/common/js/showpage.js"> </script>
<script>
    function submitForm(){
  		if(fm.RiskCode.value == ""){
  			alert("必须輸入险种！");
  			return;
  		}
  		fm.submit();
  	}
    function document.onkeydown() 
    {
    if(event.keyCode==13) 
    { 
      document.getElementById("button").click(); 
      return false; 
    }
    }
</script>
<script type="text/javascript">
var isFirstLoad = true;
var contentDataTable;
var contentColumnHeaders;
YAHOO.namespace("query.container");

/*
 *@description:初始化查询结果页面
 *@param varSigns 无
 *@return boolean 活动组别结果集    
*/
function init(){
	YAHOO.widget.DataTable.formatLink = function(elCell, oRecord, oColumn, oData) {
		if(oColumn.key =="registNo"){
				elCell.innerHTML="<a href=\"/claim/regist/registBeforeEdit.do?prpLregistRegistNo=" + oRecord.getData("registNo") + "&editType=SHOW&riskCode=" + oRecord.getData("riskCode") + "\">"+ oData+"</a>";
		}else if(oColumn.key =="powerEdit"){
				elCell.innerHTML="<a href=\"#\" onclick=\"showDlg('權限配置','/saaUserGrade/prepareUpdateUserGradePower.do?userCode=" + oRecord.getData("userCode") + "')\">配置</a>";
		}else if(oColumn.key =="status"){
			var statusStr = "";
			if(oRecord.getData("caseType") == null) {
		        if(oData=="1") {
					statusStr = "未處理";
		        } else if(oData=="2"){
		         	statusStr = "正處理";
		        } else if(oData=="3"){
					statusStr = "已處理";
		        } else if(oData=="4"){
					statusStr = "已提交";
		        } else if(oData=="5"){
					statusStr = "已撤消";
		        }
			}else if(oRecord.getData("caseType")=="0"){
				statusStr = "已註銷";
	        } else if(oRecord.getData("caseType")=="1"){
	         	statusStr = "已拒赔";
	        } else if(oRecord.getData("caseType")=="2"){
	         	statusStr = "已结案";
	        }
        	elCell.innerHTML = statusStr
		}else if(oColumn.key =="operatorName"){
			elCell.innerHTML=oRecord.getData("operatorCode")+"("+oRecord.getData("operatorName")+")";
		}else if(oColumn.key =="reportDate") {
			if(oData!=null){
				var date = new Date(oData.time);
				elCell.innerHTML = formatDate(date,'yyy-MM-dd');
			}
		}else{
			elCell.innerHTML = oData;
		}
	}; 
	contentColumnHeaders =[
		{key:"status",label:"<s:text name="db.prpLclaimStatus.status" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},<%-- 案件状态 --%>
		{key:"registNo",label:"<s:text name="db.prpLregist.registNo" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},<%-- 报案号 --%>
		{key:"policyNo",label:"<s:text name="db.prpLregist.policyNo" />",width:"40em",sortable:true},<%-- 保单号码 --%>
		{key:"insuredName",label:"<s:text name="db.prpCmain.insuredName" />",width:"40em",sortable:true},<%-- 被保险人名称 --%>
		{key:"operatorName",label:"<s:text name="db.utiTtyRecord.userName" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink},<%-- 操作员 --%>
		{key:"reportDate",label:"<s:text name="db.prpLclaimStatus.operatedate" />",width:"40em",sortable:true,formatter:YAHOO.widget.DataTable.formatLink}<%-- 操作时间 --%>
		];
	executeQuery(1,10);
}

/*
 *@description:可以批次切分活动组别结果集 
 *@param  pageNo，pageSize
 *@return  活动组别结果集
*/
function executeQuery(pageNo,pageSize){
	if(isNaN(parseInt(pageNo))){ 
		pageNo = 1;
	}
	if(isNaN(parseInt(pageSize))){
		pageSize = 10;
	} 
	var myColumnSet = new YAHOO.widget.ColumnSet(contentColumnHeaders);	
	var myDataSource = new YAHOO.util.DataSource("${ctx}/claim/claimQuery.do");
	myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
	myDataSource.connMethodPost = true; 
	myDataSource.responseSchema = {
	   resultsList: "data",
	   fields: ["status","registNo","policyNo","insuredName","riskCode","operatorCode","operatorName","reportDate"],
	   metaFields : {
			totalRecords : "totalRecords"
		}
	}; 
	myDataSource.subscribe("responseParseEvent",SINOSOFT.util.navigation);
	myDataSource.connMgr.setForm(fm);
	var initialRequest = "pageSize="+pageSize+"&pageNo="+pageNo;
	var myConfiges ={
		initialRequest:initialRequest,
		paginator:false
	};
	if (isFirstLoad==true){ 
		contentDataTable = new YAHOO.widget.DataTable("content",myColumnSet,myDataSource,myConfiges); 
		contentDataTable.initialRequest = initialRequest;
		isFirstLoad = false;	 
	}else{
		contentDataTable._oRecordSet = new YAHOO.widget.RecordSet();
		contentDataTable.initialRequest = initialRequest;
		contentDataTable.dataSource = myDataSource;
		contentDataTable.dataSource.sendRequest(contentDataTable.initialRequest, contentDataTable.onDataReturnReplaceRows, contentDataTable);
	}  
	document.getElementById("tableResullt").style.display="";
}

//init on load
YAHOO.util.Event.addListener(window,'load',init);
/*
 *@description:弹出页面
 *@param  title，url
 *@return  
*/
function showDlg(title,url){
	submitDlg = new YAHOO.widget.Panel("submitDlg",{iframe:true, visible:false, width:780, height:463, underlay:"shadow", constraintoviewport:true, fixedcenter:true, modal:true, zIndex:320});
	submitDlg.setHeader(title);
	submitDlg.setBody("<iframe name='submitFrame' src='javascript:false;'  frameborder='0' style='margin:0; padding:0; width:98%; height: 97%' align='left'></iframe>");
	submitDlg.render(document.body);
	submitDlg.show();
	var oldTarget = fm.target;
	var oldAction = fm.action;
	fm.target="submitFrame";
	fm.action = contextRootPath+url; // 链接
	fm.submit();
	fm.target = oldTarget;
	fm.action = oldAction;
}

</script>
<script language="VBScript">
    // 避免弹出安全警告框的说明：Internet选项=〉安全=〉受信任的站点
    // 1.将网站加入受信任站点，
    // 2.自定义级别中 启用 对没有标记为安全的ActiveX控件进行初始化和脚本运行

    dim hkey_root,hkey_path,hkey_key
    hkey_root="HKEY_CURRENT_USER"
    hkey_path="\Software\Microsoft\Internet Explorer\PageSetup"

    dim oldheader,oldfooter

    '//设置网页打印的页眉页脚，上下左右
    function pagesetup_set(header,footer)
        on error resume next
        Set RegWsh = CreateObject("WScript.Shell")
        hkey_key="\header"
        RegWsh.RegWrite hkey_root+hkey_path+hkey_key,header
        hkey_key="\footer"
        RegWsh.RegWrite hkey_root+hkey_path+hkey_key,footer


        hkey_key="\margin_left" '左
        RegWsh.RegWrite hkey_root+hkey_path+hkey_key,"0.73"
        hkey_key="\margin_right" '右
        RegWsh.RegWrite hkey_root+hkey_path+hkey_key,"0.70"
        hkey_key="\margin_top" '上
        RegWsh.RegWrite hkey_root+hkey_path+hkey_key,"1.1"
        hkey_key="\margin_bottom" '下
        RegWsh.RegWrite hkey_root+hkey_path+hkey_key,"1"

    end function
    '//设置网页打印的页眉页脚,上下左右为默认值
    function pagesetup_default()
        on error resume next
        Set RegWsh = CreateObject("WScript.Shell")
        hkey_key="\header"
        RegWsh.RegWrite hkey_root+hkey_path+hkey_key,"&w&b页码，&p/&P"
        hkey_key="\footer"
        RegWsh.RegWrite hkey_root+hkey_path+hkey_key,"&u&b&d"

        hkey_key="\margin_left" '左
        RegWsh.RegWrite hkey_root+hkey_path+hkey_key,"0.75"     '(对应 19.05毫米)
        hkey_key="\margin_right" '右
        RegWsh.RegWrite hkey_root+hkey_path+hkey_key,"0.75"
        hkey_key="\margin_top" '上
        RegWsh.RegWrite hkey_root+hkey_path+hkey_key,"0.75"
        hkey_key="\margin_bottom" '下
        RegWsh.RegWrite hkey_root+hkey_path+hkey_key,"0.75"
    end function

    '//显示页面设置
    function pagesetup_get()
        on error resume next
        Set RegWsh = CreateObject("WScript.Shell")
        hkey_key="\header"
        oldheader=RegWsh.RegRead(hkey_root+hkey_path+hkey_key)
        hkey_key="\footer"
        oldfooter=RegWsh.RegRead(hkey_root+hkey_path+hkey_key)

        'hkey_key="\margin_left" '左
        'message = message & "左:" & RegWsh.RegRead( hkey_root+hkey_path+hkey_key) & vbCrLf
        'hkey_key="\margin_right" '右
        'message = message & "右:" & RegWsh.RegRead( hkey_root+hkey_path+hkey_key) & vbCrLf
        'hkey_key="\margin_top" '上
        'message = message & "上:" & RegWsh.RegRead( hkey_root+hkey_path+hkey_key) & vbCrLf
        'hkey_key="\margin_bottom" '下
        'message = message & "下:" & RegWsh.RegRead( hkey_root+hkey_path+hkey_key) & vbCrLf
        '
        'msgbox (message)
    end function

    function printPage()
		pagesetup_get()         '读取旧值
		header=""
		footer=""
		pagesetup_get()
		pagesetup_set header, footer
		divButton.style.display = "none"
    	        window.print()
		pagesetup_set oldheader, oldfooter            '恢复設定
    end function
</script>
<head>
<title><s:text name="title.registBeforeEdit.titleName" /></title>
</head>
<body class="yui-skin-sam">
	<form name="fm" action="/claim/ClaimQuery.do" method="post">
		<input type="hidden" name="pageFlag">
		<%--去掉查询条件，添加打印按钮
    <table id="queryTable" width="100%" border="0" align="center" cellpadding="4" cellspacing="1"  class="common">
      <tr>
	    <td colspan="4" class="formtitle"><s:text name="title.registTimeOut.titleName" /><!--查询超时信息 --></td>
        </tr>
      <tr>
        <td class='title' ><s:text name="db.prpLregist.registNo" />：</td> <!--报案号 -->
        <td class='input' >
        <select class=tag name="RegistNoSign" >
            <option value="=">=</option>
            <option value="=*">=*</option>
          </select> <input type=text name="RegistNo" class="query" >
        </td>
        <td class='title' ><s:text name="db.prpLregist.policyNo" />：</td><!--保单号码 -->
        <td class='input' >
        <select class=tag name="PolicyNoSign" >
            <option value="=">=</option>
            <option value="=*">=*</option>
          </select> <input type=text name="PolicyNo" class="query" >
        </td>
      </tr>
      <tr>  
        <td class='title' ><s:text name="db.prpLregist.insuredName" />：</td><!--被保险人 -->
        <td class='input' >
          <select class=tag name="InsuredNameSign" >
            <option value="=">=</option>
            <option value="=*">=*</option>
          </select> <input type=text name="InsuredName" class="query" >
        </td>
        <td class='title' ><s:text name="db.prpLregist.riskCode" />:</td><!--险种代码 -->
        <td class='input' >  
             <select class=tag name="RiskCodeSign" >
                <option value="=">=</option>
                <!--<option value="*">*&nbsp;</option>-->
             </select> <input type=text name="RiskCode" class="query" > </td>
      </tr>
      <tr>
	    <td class="title" style="color:red" colspan="4">
	   <s:text name="prompt.schedule.query1"/><br><!-- "="符号，必须精确查询。 -->
	   <s:text name="prompt.schedule.query2"/> <!--"=*"符号，前匹配後模糊的查询。 -->
	    </td>
  	  </tr>
      </table>
       --%>
		<table width=100%>
			<tr>
				<td class='button' colspan="6">
					<div align="center" id="divButton" style="display:">
						<input class="button" type="button" name="buttonPrint" value=" 列 印 " onclick="printPage()">
					</div>
				</td>
			</tr>
		</table>
		<table width="98%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td>
					<table width="100%" border="0" cellspacing="0" cellpadding="0" id="tableResullt" style="display: none;">
						<tr>
							<td>
								<div id="content_message" style="display: none;"></div>
								<div id="listShowCont" align="left">
									<div id="listShow">
										<div id="content" class="sort"></div>
										<div id="content_navigation" class="query" style="text-align: center;"></div>
									</div>
								</div>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<input type="hidden" name="editType" value="${param.editType}">
	</form>
</body>
<script language="javascript" src="${ctx}/common/dwr/engine.js"></script>
<script language="javascript" src="${ctx}/common/dwr/util.js"></script>
<script language="javascript" src="${ctx}/common/js/prototype.js"></script>
<script language="javascript" src="${ctx}/common/js/CodeSelect.js"></script>
</html>