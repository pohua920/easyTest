<%--
****************************************************************************
* DESC       ：车险理赔TITLE页面
* AUTHOR     ：中科软
* CREATEDATE ：2004-03-23
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app"%>
<%@page import="com.sinosoft.claim.dto.custom.UserDto"%>
<%@page import="javax.servlet.http.*"%>
<%@page import="java.util.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%
	UserDto user = (UserDto) session.getAttribute("user");
	String userName = user.getUserName();
	String comName = user.getComName();
%>
<html>
<head>
<app:css />
<link href="css.css" rel="stylesheet" type="text/css">
<script language="JavaScript">
          function query() {
		var strURL = "/workflow/common/QueryIndex.jsp";
		var newWindow = window
				.open(
						strURL,
						"aa",
						'width=650,height=450,top=0,left=0,toolbar=1,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=1');
		newWindow.focus();
	}

	function queryAuLaw() {
		var strURL = "/workflow/overall/function/QueryLawOverview.jsp";
		if (fm.auLawRgrade.value == "1" && fm.auLawXgrade.value == "1") {
			var newWindow = window
					.open(
							strURL,
							"aa",
							'width=650,height=450,top=0,left=0,toolbar=1,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=1');
			newWindow.focus();
		}
	}

	function queryAuKnowledge() {
		var strURL = "/workflow/overall/function/QueryKnowledgeOverview.jsp";
		if (fm.auKnowledgeRgrade.value == "1"
				&& fm.auKnowledgeXgrade.value == "1") {
			var newWindow = window
					.open(
							strURL,
							"aa",
							'width=650,height=450,top=0,left=0,toolbar=1,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=1');
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
			if (parent.fraSet.cols == "0%,*") {
				parent.fraSet.cols = "180,*";
				menuPowerImage.src = "/claim/images/butHide.gif";
			} else if (parent.fraSet.cols == "180,*") {
				parent.fraSet.cols = "0%,*";
				menuPowerImage.src = "/claim/images/butShow.gif";
			}
		} catch (re) {
		}
	}
	//链接到首页
	function showFirstPage() {

	}

	function menuSwitch(menuSwitchInput) {
		// reason: resize MPC
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
	function setCommand(text) {
		document.all("command").innerText = "　" + text;
	}
        </script>
          <script language="JavaScript"
             type="text/javascript" src="/claim/common/js/leftMenu.js">
          </script>
<
	
y leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" style="background: url(/claim/images/body_bg.gif) top repeat-x; padding-top: 3px;">
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td height="55" align="center" valign="bottom">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td style="padding: 8px 0 9px 9px;">
            				<img src="/claim/images/logo.png" height="44px" width="412px">
						</td>
						<td align="right" valign="top" width="100%">
							<table width="90%" border="0" cellspacing="0" cellpadding="0" style="background: url(/claim/images/top_c.gif) repeat-x;">
								<tr>
									<td align="left" width="5%">
										<img src="/claim/images/top_l.gif" />
									</td>
									<td align="right" width="50%"><%=userName%></td>
									<td align="right" width="25%"><%=comName%></td>
									<td align="right" width="15%"><%=new SimpleDateFormat("yyyy年MM月dd日").format(new Date())%></td>
									<td align="right" width="5%">
										<img src="/claim/images/top_r.gif" />
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<!--<tr>
    <td width="60%" background="/claim/images/BgVisaTop.gif"><img src="/claim/images/cpClaimTitle.gif"><img src="/claim/images/ImgClaimFlowSystem.gif"></td>
    <td width="15%" background="/claim/images/BgVisaTop.gif" class=common aglin=right> <span id="spanUserName"><img src="/claim/images/ImgIcon_1.gif" align="absmiddle" > <%=userName%></span></td>
    <td width="15%" background="/claim/images/BgVisaTop.gif" class=common aglin=right> <span id="spanUserName"><img src="/claim/images/ImgIcon_1.gif" align="absmiddle" > </span><span id="spanProjectName"><%=comName%></span> </td>
    <td width="10%" background="/claim/images/BgVisaTop.gif" class=common aglin=center> <span id="spanUserName"><img src="/claim/images/ImgIcon_1.gif" align="absmiddle" > </span>
                                                  <span id="spanDate"><%=new SimpleDateFormat("yyyy年MM月dd日").format(new Date())%></span>
    </td>
  </tr>-->
	</table>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" height="26">
		<tr>
			<td width="17%" align=center class=white>
				<span id="spShowHideFrame1" onClick="showHideFrame()"><font color=white><s:text name="pub.hiddenMenu" /></font></span>
				<%--←隐藏功能選單--%>
				<span id="spShowHideFrame" onClick="showHideFrame()" style="display: none;"><font color=white><s:text name="pub.showMenu" /></font></span>
			</td>
			<%--←显示菜单--%>
			<td width="83%" class=white>
				<nobr>
					<s:text name="pub.currentLocation" />
					：
					<%--您当前所处的位置--%>
				</nobr>
				<span id="command" />
			</td>
		</tr>
	</table>
</body>
</html>
