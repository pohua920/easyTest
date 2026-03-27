<%--
****************************************************************************
* DESC       ：超时赔付查询结果页面
* AUTHOR     ：理赔组 chenjie
* CREATEDATE ：2013-03-08
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
需要修改“已超时赔付查询”路径为 /claim/pages/common/query/TimeoutQueryList.jsp
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<script src="/claim/common/js/showpage.js"></script>
<script language="VBScript">
<%--
// 避免弹出安全警告框的说明：Internet选项=〉安全=〉受信任的站点
// 1.将网站加入受信任站点，
// 2.自定义级别中 启用 对没有标记为安全的ActiveX控件进行初始化和脚本运行
--%>
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

<html locale="true">
<head>
  <%@include file="/common/i18njs.jsp"%>
  <%@include file="/common/meta_css.jsp"%>
  <%@include file="/common/meta_js.jsp"%>
  <link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
  <title><s:text name="title.query.outtimePayQueryResultPage" />超时赔付查询结果页面</title>
  <script language="javascript">
  var serialNo = 1;
    var isFirstLoad = true;
	var contentDataTable;
    var contentColumnHeaders;
    YAHOO.namespace("query.container");
    <%--
     *@description:初始化查询结果页面
     *@param varSigns 无
     *@return boolean 活动组别结果集    
     *@author 中科软
    --%>
    function init(){
        YAHOO.widget.DataTable.formatLink = function(elCell, oRecord, oColumn, oData) {
           if(oColumn.key =="operateDate"){
				if(oData!=null){
					var date = new Date(oData.time);
					elCell.innerHTML = formatDate(date,'yyy-MM-dd');
				}
            }else if(oColumn.key =="serialNo"){
        		elCell.innerHTML = serialNo++;
        	}else{
                elCell.innerHTML = oData;
            }
        }; 
        contentColumnHeaders =[
 			 {key:"serialNo",label:"<s:text name='db.prpDrate.serialNo'/>",width:"40em",formatter:YAHOO.widget.DataTable.formatLink},                  
             {key:"businessNo",label:"<s:text name="check.claimNum" />",width:"40em"},<%--赔案号--%>
             {key:"policyNo",label:"<s:text name="db.prpCmain.policyNo" />",width:"40em"},<%--保单号--%>
             {key:"riskCodeName",label:"<s:text name="db.prpDdbs.riskCode" />",width:"40em"},<%--险种--%>
             {key:"insuredName",label:"<s:text name="db.prpCmain.insuredName" />",width:"40em"},<%--被保险人名称--%>
             {key:"operateDate",label:"<s:text name="certify.submitTime" />",width:"40em",formatter:YAHOO.widget.DataTable.formatLink}<%--单证提交时间--%>
            ];
       executeQuery(1,20);
    }
    <%--
     *@description:可以批次切分活动组别结果集 
     *@param  pageNo，pageSize
     *@return  活动组别结果集
     *@author 中科软
    --%>
    function executeQuery(pageNo,pageSize){
    	serialNo = 1;
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
           fields: ["businessNo","policyNo","riskCodeName","insuredName","operateDate"],
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
    YAHOO.util.Event.addListener(window,'load',init);;
    <%--
     *@description:弹出页面
     *@param  title，url
     *@return  
     *@author 中科软
    --%>
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
</head>
<body class="yui-skin-sam">
	<input type="hidden" name="pageFlag">
	<form name="fm" action="${ctx}/claim/ClaimQuery.do" method="post">
		<table border="0" align="center" cellpadding="0" cellspacing="0" class="common">
			<tr>
				<td>
					<div align="center" id="divButton" style="display: none;">
						<input class="button" type="button" name="buttonPrint" value=" 列 印 " onclick="printPage()">
					</div>
				</td>
			</tr>
		</table>
		<table class="common" cellpadding="5" cellspacing="1">
			<tr>
				<td colspan="6" class="formtitle">
					<s:text name="title.query.outtimePayQueryResultPage" /><%--超时赔付查询结果信息 --%>
				</td>
			</tr>
		</table>
	    <table width="98%" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td>
	            <table  width="100%" border="0" cellspacing="0" cellpadding="0" id="tableResullt" style="display: none;">
	                <tr>
	                    <td>
	                        <div id="content_message" style="display: none;"></div>
	                        <div id="listShowCont" align = "left">
	                            <div id="listShow" >
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
	    <c:choose>
	       <c:when test="${empty param.editType}"><input type="hidden" name="editType" value="compeTimeOut"></c:when>
	       <c:otherwise><input type="hidden" name="editType" value="${param.editType}"></c:otherwise>
	    </c:choose>
	</form>
</body>
</html>