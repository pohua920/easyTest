<%--
****************************************************************************
* DESC       ：报案登记框架页面
* AUTHOR     ：中科软
* CREATEDATE ：2013-01-17
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta_js.jsp"%>
<%@ page import="com.sinosoft.sysframework.reference.*"%>
<%@ page import="com.sinosoft.sysframework.common.datatype.*" %>
<html xmlns:mpc>
<head>
<!--对title处理-->
<title><s:text name="title.registBeforeEdit.editRegist" /></title>
<%-- 页面样式  --%>
<link rel="stylesheet" type="text/css" href="${ctx }/css/Standard.css">
<script src="${ctx }/pages/DAA/regist/js/DAARegistEdit.js"></script>
<script src="${ctx }/pages/DAA/regist/js/DAAThirdCarLossEdit.js"></script>
<script src="${ctx }/common/js/ProcessValidate.js"></script>
<%-- 标签页样式 --%>
<jsp:include page="/behaviors/MpcStyle.jsp" />
<style type="text/css">
ol li {
	margin: 8px
}

#tags {
	height: 100%;
	width: 100%;
	margin: 0;
	padding: 0;
	margin-left: 10px
}

#tags li {
	float: left;
	margin-right: 1px;
	border: 1px solid #aecbd4;
	height: 20px;
	list-style-type: none
}

#tags li a {
	text-decoration: none;
	float: left;
	height: 23px;
	padding: 0px 10px;
	line-height: 20px;
	color: #000000
}

#tags li.emptyTag {
	width: 1px;
	background: none
}

#tags li.selectTag {
	background-position: left top;
	position: relative;
	height: 22px;
	margin-bottom: -2px
}

#tags li.selectTag a {
	background-position: right top;
	color: #000000;
	background: #009966;
	height: 22px;
	line-height: 22px;
}

#tagContent {
	padding: 1px;
	background-color: #fff;
}

.tagContent {
	height: 100%;
	color: #474747;
	width: 100%;
	display: none
}

#tagContent div.selectTag {
	display: block
}
</style>
<script type="text/javascript">
	//mpc调整
	$(function() {
		initWindowNoBtn();
		$(window).resize(function() {
			initWindowNoBtn();
		});
		//mantis： CLM0095 ，處理人員：BK007 蘇哲，需求單編號：CLM0095.新核心-備案結點預設開啟"基本訊息"-start
		var Tabs = window.document.all("oMPC");
		Tabs.selectedIndex=2;
		Tabs.DoPropChange("selectedIndex");
		//mantis： CLM0095 ，處理人員：BK007 蘇哲，需求單編號：CLM0095.新核心-備案結點預設開啟"基本訊息"-end
	})
</script>
</head>
<%--强三--%>
<c:choose>
	<c:when test="${editType eq 'SHOW'||editType eq 'DELETE'}">
		<body onload="initPage();initSet();initSet_qs();readonlyAllInput();eidtRegistExt('RegistExt');eidtRegistExt('RegistExt_Data');changeProperties();oMPC.style.visibility='visible';">
	</c:when>
	<c:otherwise>
		<body onload="initPage();initSet();initSet_qs();changeProperties();oMPC.style.visibility='visible';">
	</c:otherwise>
</c:choose>
<DIV id="mainLayer" class="mainLayerNoBtn">
	<form name="fm" action="${ctx }/regist/registSave.do" method="post" onsubmit="return validateForm(this,'Driver','');">
		<c:if test="${editType == 'ADD' || editType == 'EDIT' || editType == 'PERFECT'}">
			<s:token></s:token>
		</c:if>
		<input type="hidden" name="nodeType" value="regis">
		<input type="hidden" name="editType" value="${editType}">
		<c:set value="" var="oldRegistLastAccessedTime" scope="session"></c:set>
		<input type="hidden" id="pageCount" value="2">
		<mpc:container ID="oMPC">
			<%-- 1.3.报案保单信息页面 --%>
			<mpc:page ID="tabMain" TABTITLE="<s:text name="regist.prpLregist.registPolicy"/>" TABTEXT="<s:text name="regist.prpLregist.registPolicy"/>">
				<CENTER>
					<DIV id="page2" name="tabMain" class="tabMain">
						<%-- 1.3.1.保单基本信息 --%>
						<%@include file="/pages/DAA/regist/DAARegistPolicyMain.jsp"%>
						<%-- 1.3.2.保单保别信息 --%>
						<%@include file="/pages/DAA/regist/DAARegistPolicyRiskEdit.jsp"%>
						<%-- 1.3.3.特别约定 --%>
						<%
							//@include file="/pages/DAA/compensate/DAACompensateCengage.jsp"
						%>
					</DIV>
				</CENTER>
			</mpc:page>
			<%-- 1.1.报案基本信息页面 --%>
			<mpc:page ID="tabMain" TABTITLE="<s:text name="regist.prpLregist.registMain"/>" TABTEXT="<s:text name="regist.prpLregist.registMain"/>">
				<CENTER>
					<DIV id="page1" name="tabMain" class="tabMain">
						<%-- 1.1.1.报案主信息 --%>
						<%@include file="/pages/DAA/regist/DAARegistMainEdit.jsp"%>
						<c:choose>
							<c:when test="${not empty paramPrpallRegist&&paramPrpallRegist== 'DAA'}"></c:when>
							<c:otherwise>
								<%-- 1.3.1.出险备注信息 --%>
								<%@include file="/pages/DAA/regist/DAARegistTextEdit.jsp"%>
								<%-- 1.3.2.巨灾代码信息 --%>
								<%@include file="/pages/DAA/regist/RegistKelpInfo.jsp"%>
								<%-- 1.3.3.报案信息补充说明 --%>
								<%@include file="/pages/DAA/regist/DAARegistExtEdit.jsp"%>
							</c:otherwise>
						</c:choose>
					</DIV>
				</CENTER>
			</mpc:page>
			<%-- 1.2.报案受损信息页面 --%>
			<mpc:page ID="tabMain" TABTITLE="<s:text name="regist.prpLregist.registLoss"/>" TABTEXT="<s:text name="regist.prpLregist.registLoss"/>">
				<DIV id="page3" name="tabMain" class="tabMain">
					<%@include file="/pages/DAA/regist/DAARegistLossEdit.jsp"%>
				</DIV>
			</mpc:page>
		</mpc:container>
		<TABLE id="btnCommon" class="common">
			<TR>
				<TD>
					<c:choose>
						<c:when test="${not empty param.paramPrpallRegist && param.paramPrpallRegist == 'DAA' }">
							<center>
								<input type="button" name=buttonSave class='button' value="關 閉" onclick="window.close();">
							</center>
						</c:when>
						<c:otherwise>
							<center><%@include file="/pages/DAA/regist/DAARegistSave.jsp"%></center>
						</c:otherwise>
					</c:choose>
				</TD>
			</TR>
		</TABLE>
	</form>
</DIV>
<script type="text/javascript">
	//嵌套标签页选择
	function selectTag(showContent, selfObj) {
		// 操作标签
		var tag = document.getElementById("tags").getElementsByTagName("li");
		var taglength = tag.length;
		for (i = 0; i < taglength; i++) {
			tag[i].className = "";
		}
		selfObj.parentNode.className = "selectTag";
		// 操作内容
		for (i = 0; j = document.getElementById("tagContent" + i); i++) {
			j.style.display = "none";
		}
		document.getElementById(showContent).style.display = "block";
	}
	function setStyle() {
		var tag = document.getElementById("tagContent").getElementsByTagName(
				"div");
		var taglength = tag.length;
		for (i = 0; i < taglength; i++) {
			tag[i].style.width = document.body.offsetWidth;
			tag[i].style.Height = document.body.offsetHeight;
		}
	}
</script>
<%
	Object o = request.getAttribute("flushflag");
	String flushflag = "";
	if (o != null && !"".equals(o)) {
		flushflag = (String) o;
	}
	if (flushflag.equals("1")) {
%>
<script type="text/javascript">
			var Tabs = window.document.all("oMPC");
			Tabs.selectedIndex=2;
		</script>
<%
	}
%>
</body>
</html>