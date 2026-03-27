<%--
****************************************************************************
* DESC       ：理赔系统标题页面
* AUTHOR   	  ：YANGXIAOGANG
* CREATEDATE ：2004-07-19
* MODIFYLIST ：Name          Date            Reason/Contents
*              ------------------------------------------------------
*              ZHANGYING     2004-07-23      按规范整理
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%@include file="/common/meta_css.jsp"%>
<%@include file="/common/i18njs.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<%@ page import="com.sinosoft.utiall.blsvr.BLUtiUserGrade"%>
<%@ page import="com.sinosoft.utiall.blsvr.BLPrpDcompany"%>
<%@ page import="com.sinosoft.utiall.schema.UtiUserGradeSchema"%>
<%@ page import="com.sinosoft.utility.error.UserException"%>
<%@ page import="com.sinosoft.utility.database.DbPool"%>
<html>
<head>
<script language='JavaScript'>
function query() {
    var strURL = "/workflow/common/QueryIndex.jsp";
    var newWindow = window.open(strURL, "aa", 'width=650,height=450,top=0,left=0,toolbar=1,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=1');
    newWindow.focus();
}

function queryAuLaw() {
    var strURL = "/workflow/overall/function/QueryLawOverview.jsp";
    if (fm.auLawRgrade.value == "1" && fm.auLawXgrade.value == "1") {
        var newWindow = window.open(strURL, "aa", 'width=650,height=450,top=0,left=0,toolbar=1,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=1');
        newWindow.focus();
    }
}

function queryAuKnowledge() {
    var strURL = "/workflow/overall/function/QueryKnowledgeOverview.jsp";
    if (fm.auKnowledgeRgrade.value == "1" && fm.auKnowledgeXgrade.value == "1") {
        var newWindow = window.open(strURL, "aa", 'width=650,height=450,top=0,left=0,toolbar=1,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=1');
        newWindow.focus();
    }
}

function editPaper() {
    if (fm.auPaperRgrade.value == "1" && fm.auPaperXgrade.value == "1") {
        parent.fraInterface.window.location = "/workflow/audit/project/QueryPaperNature.jsp";
    }
}

function showHideFrame() {
    try {
        var cols = parent.fraSet.cols;
    	var left = cols.substring(0,cols.indexOf(","));
		var right = cols.substring(cols.indexOf(","));
        if (left=="0%"||left=="0") {
            window.parent.document.getElementById("fraSet").cols = "180"+right;
            document.getElementById('spShowHideFrame').style.display="none";
            document.getElementById('spShowHideFrame1').style.display="block";
        } else{
            window.parent.document.getElementById("fraSet").cols = "0%"+right;
            document.getElementById('spShowHideFrame1').style.display="none";
            document.getElementById('spShowHideFrame').style.display="block";
        }
    } catch (re) {
	}
}
//链接到首页

function showFirstPage() {

}

function menuSwitch(menuSwitchInput) {
	// resize MPC
    var fraInterface = parent.window.frames["fraInterface"];
    var mpc = fraInterface.document.getElementById("oMPC");
    var page;
    var count;
    //为什么加此代码，作用是什么？ --李平
    // if(mpc!=null){
    //count=fraInterface.document.getElementById("pageCount").value;
    //for(var i=1;i<=count;i++){
    //page[0]=document.getElementById("page"+count);
    //}
    //}
    var mpcWidth;
    if (menuSwitchInput.value == "close") {
        menuSwitchInput.src = "/claim/images/cpClaimTitleShow.gif";
        menuSwitchInput.value = "open";
        //parent.fraMenuReport.cols = "0,*";
        parent.fraSet.cols = "1,*";
        //mpc resize
        if (mpc != null) {
            mpc.style.width = "1010px";
        }
        return;
    }
    if (menuSwitchInput.value == "open") {
        menuSwitchInput.src = "/claim/images/cpClaimTitleHidden.gif";
        menuSwitchInput.value = "close";
        //parent.fraMenuReport.cols = "180,*";
        parent.fraSet.cols = "180,*";
        //mpc resize
        if (mpc != null) {
            mpc.style.width = "830px";
        }
        return;
    }
}

function setPositionSpan(text) {
    document.all("currentPositionSpan").innerText = "　" + text;
}
function showChat(){
	try{
		var left = parent.fraSet.cols.substring(0,parent.fraSet.cols.lastIndexOf(",") + 1);
		var right = parent.fraSet.cols.substring(parent.fraSet.cols.lastIndexOf(",") + 1);
		if(right == "0"){
	  		parent.fraSet.cols = left + "180";
		}else if(right == "180"){
			parent.fraSet.cols = left + "0";
		}
	}catch(re){}   
}
</script>
<script language="JavaScript" type="text/javascript" src="/claim/common/js/leftMenu.js"></script>
<%
	String strUserCode = (String) session.getAttribute("userCode");
	String strUserName = (String) session.getAttribute("userName");
	String strUserPassword = (String) session.getAttribute("password");
	String strComCode = (String) session.getAttribute("comCode");
	String strComName = (String) session.getAttribute("comName");
	String strGradeCode = (String) session.getAttribute("gradeCodes");
%>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" style="background:url(${ctx}/images/body_bg.gif) top repeat-x; padding-top:3px;">
	<form name="fm">
		<input type="hidden" name="LogonFromPage" value="UITitle">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td style="padding: 0px 0px 0px 8px;">
					<%-- <img src="${ctx}/images/logo.png" height="63px" width="412px" style="position: relative; margin-top: -5px"> --%>
					<img src="${ctx}/images/logo100.png" height="63px" width="412px" style="position: relative; margin-top: -5px">
				</td>
				<td align="right" valign="top" width="100%">
					<table width="90%" border="0" cellspacing="0" cellpadding="0" style="background: url(${ctx}/images/top_c.gif) repeat-x;">
						<tr>
							<td align="left" width="5%">
								<img src="${ctx}/images/top_l.gif" />
							</td>
							<td align="right" width="50%">
								<span style="width: 120px"><%=strUserName%>&nbsp;</span>
								
							</td>
							<td align="right" width="25%">
								<span style="width: 220px"><s:text name="claim.common.handledept" />：<%=strComName%>&nbsp;</span>
							</td>
							<script>
								//parent.document.frames("fraTitle").setOptionComCode("<%=strComCode%>");
							</script>
							<td align="right" width="15%">
								<span style="width: 120px">
								<%
									out.println(new SimpleDateFormat("yyyy年MM月dd日").format(new Date()));
								%>
								&nbsp;</span>
							</td>
							<td align="right" width="5%">
								<img src="${ctx}/images/top_r.gif" />
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<table width=100% border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="17%" align=center class=white>
					<span id="spShowHideFrame1" onClick="showHideFrame()">
						<font color=white>←隱藏功能選單</font>
					</span>
					<span id="spShowHideFrame" onClick="showHideFrame()" style="display: none;">
						<font color=white>←顯示功能選單</font>
					</span>
				</td>
				<td width="53%" class=white>
					<nobr>
						<s:text name="pub.currentLocation" />
						<%--您当前所处的位置 --%>
						：
					</nobr>
					<span id="currentPositionSpan" />
				</td>
				<td width="20%" class=white align="center">
					${userName}&nbsp;|
					<%
						out.println(new SimpleDateFormat("yyyy.MM.dd").format(new Date()));
					%>
				</td>
				<td width="10%" class=white align="center">
					<span>
						<a href="javascript:void(0)" onclick="showChat();" style="color: white">即時通訊</a>
					</span>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>