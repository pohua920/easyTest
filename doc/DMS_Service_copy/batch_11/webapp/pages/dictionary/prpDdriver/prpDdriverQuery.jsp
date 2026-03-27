<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%-- moidfy  update by tongziliang 2011-10-09 reason:修改页面的按钮样式和优化页面样式 --%>
<link rel="stylesheet" href="${ctx}/style/popup/login.css" type="text/css"></link>
<html>
<head>
<title>机动车险司机代码表</title>
<%@ include file="/common/i18njs.jsp"%>
<%@ include file="/common/meta_css.jsp"%>

</head>

<body id="all_title">

<div id="wrapper">
<div id="container">
<div id="crash_menu">
<h2 align="center">请输入查询条件</h2>
</div>
<s:form name="fm" action="queryprpDagent" namespace="/dictionary" method="post">
<s:hidden name="flag" id="flag"></s:hidden>
	<table class="fix_table">	
		<tr>
			<td class="bgc_tt short">驾证号码</td>			
			<td class="long"><input name="prpDdriver.drivingLicenseNo"
				id="prpDdriver.drivingLicenseNo" class='input_w w_15'></td>			
			<td class="bgc_tt short">驾驶员姓名</td>
			<td class="long"><input name="prpDdriver.driverName"
				id="prpDdriver.driverName" class='input_w w_15'></td>
			<td colspan="2" align="center">
			<button type="button"
				 value="" onclick="executeQuery(1,10);"><span><em>查 询</em></span></button>
<!--			<input type="button"-->
<!--				class="button_ty" value="查 询" onclick="executeQuery(1,10);">-->
			</td>
			<td colspan="2" align="center">
			<button type="button"
				 value="" onclick="editRecord('${ctx}/dictionary/prepareInsertPrpDdriver.do?editType=insert');"><span><em>增 加</em></span></button>
<!--			<input type="button"-->
<!--				class="button_ty" value="增 加" onclick="editRecord('${ctx}/dictionary/prepareInsertPrpDdriver.do?editType=insert');">-->
			</td>
			<td colspan="2" align="center">
			<button type="button"
				 value="" onclick="deleteMethod();"><span><em>删除</em></span></button>
<!--			<input type="button"-->
<!--				class="button_ty" value="删除" onclick="deleteMethod();">-->
			</td>
		</tr>
	</table>
</s:form></div>
<div id="content_navigation" class="query" align="right"></div>
<div id="content" class="sort"></div>

<div id="content_navigation" class="query" align="right"></div>
</div>
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
	
	
    function init() {
        //var userCode_tip = new YAHOO.widget.Tooltip("userCode_tip",{text:"请双击选择员工代码",context:"saaUser.userCode",zIndex:300});
        //var comCode_tip = new YAHOO.widget.Tooltip("comCode_tip",{text:"请双击选择机构代码",context:"saaUser.comCode",zIndex:300});	
        YAHOO.widget.Column.formatLink = function(elCell, oRecord, oColumn,oData) {
            var drivingLicenseNo = oRecord.drivingLicenseNo;
            if (oColumn.key == "drivingLicenseNo") {
                elCell.innerHTML = "<a href=\"#\" onclick=\"showRecord('${ctx}/dictionary/prepareUpdatePrpDdriver.do?drivingLicenseNo="
                    + drivingLicenseNo + "&editType=view')\">"+oData+"</a>"
            }
            if (oColumn.key == "edit") {
                elCell.innerHTML = "<a href=\"#\" onclick=\"editRecord('${ctx}/dictionary/prepareUpdatePrpDdriver.do?drivingLicenseNo="
                        + drivingLicenseNo + "&editType=update')\">修改</a>";
            }
            
            if(oColumn.key == "chkbox"){
            	elCell.innerHTML = "<input type='checkbox' name='chkbox' value='"+drivingLicenseNo+"'>";
            }
            
            if (oColumn.key == "validStatus") {
                switch (oRecord.validStatus) {
                    case '0':
                        elCell.innerHTML = "无效";
                        break;
                    case '1':
                        elCell.innerHTML = "有效";
                        break;
                }
            }
            
            if (oColumn.key == "driverSex") {
                switch (oRecord.driverSex) {
                    case '1':
                        elCell.innerHTML = "男";
                        break;
                    case '0':
                        elCell.innerHTML = "未知";
                        break;
                    case '2':
                        elCell.innerHTML = "女";
                        break;
                     case '9':
                        elCell.innerHTML = "未说明";
                        break;
                }
            }
        };

        contentColumnHeaders = [
		  {key :"chkbox",text :"选择",width :"10em",sortable :false,resizeable :true,type:"link"},
          {key :"drivingLicenseNo",text :"驾证号码",width :"20em",sortable :true,type:"link"}, 
          {key :"driverName",text :"驾驶员姓名",width :"25em",sortable :true}, 
          {key :"awardLicenseOrgan",text :"领证机关",width :"50em",sortable :true}, 
          {key :"edit",text :"修改",width :"10em",type :"link",resizeable :true}];
          
          executeQuery(1,10);
        }
    //Query Data
    function executeQuery(pageNo, pageSize) {
        if (isNaN(parseInt(pageNo))) {
            pageNo = 1;
        }
        if (isNaN(parseInt(pageSize))) {
            pageSize = 10;
        }
        var myColumnSet = new YAHOO.widget.ColumnSet(contentColumnHeaders);
        var myDataSource = new YAHOO.util.DataSource("${ctx}/dictionary/queryPrpDdriver.do");
        myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
        myDataSource.responseSchema = {
            resultsList :"data",
            fields : [ "drivingLicenseNo", "driverName", "driverSex", "identifyNumber","awardLicenseOrgan","drivingCarType" ],
            totalRecords :"totalRecords"
        };
        myDataSource.subscribe("responseParseEvent", SINOSOFT.util.navigation);
        myDataSource.connMgr.setForm(fm);
        var initialRequest = "pageSize=" + pageSize + "&pageNo=" + pageNo;
        var myConfiges = {
            initialRequest :initialRequest,
            paginator :false
        };
        contentDataTable = new YAHOO.widget.DataTable("content", myColumnSet,myDataSource, myConfiges);
    }
    YAHOO.util.Event.addListener(window, 'load', init);
    function deleteMethod(){
    	var chkbox = document.getElementsByName('chkbox');
    	var flag = false;
    	var checkedValue="";
    	if(chkbox.length==0){
			alert("没有选中列！");
			return false;
        }else{
        	for(var j=0;j<chkbox.length;j++){
				if(chkbox[j].checked){
					flag = true;
					if(checkedValue==""){
						checkedValue=chkbox[j].value;
					}else{
						checkedValue+=","+chkbox[j].value;
					}
				}
			}
			if(flag){
				deleteRecord('${ctx}/dictionary/deletePrpDdriver.do?chkbox='+checkedValue);
			}else{
				alert("没有选中列！");
			}
         }
    }
</script>