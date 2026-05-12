<%--
****************************************************************************
* DESC       ：简易赔案框架页面
* AUTHOR     ：zhaohui
* CREATEDATE ：2007-6-12
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app"%>
<%@ page import="com.sinosoft.sysframework.reference.*"%>
<%@ page import="com.sinosoft.claim.dto.custom.*"%>
<%@ page import="com.sinosoft.claim.dto.domain.*"%>
<%@ page import="com.sinosoft.sysframework.common.datatype.*"%>
<%@ page import="java.util.ArrayList"%>
<html xmlns:mpc>
<head>
<!--对title处理-->
<title><s:text name="quickCase.simpleClaim" /> <!-- 简易赔案 --></title>
<app:css />
<!-- 页面样式  -->
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<!-- 标签页样式 -->
<jsp:include page="/behaviors/MpcStyle.jsp" />
<!--这个函数是调动所能用到的通用js的过程，一般包括最常用的js的函数声明都在meta_js.jsp中-->
<script src="/claim/DAA/quickCase/js/DAAQuickCaseEdit.js"></script>
<!-- 内层标签页样式 -->
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
<app:claimCodeInput />
</head>
<%
	//如果是定损进入简易赔案或者查勘当前处理人和以前查勘不为同一个人，则查勘信息只读，调用readonlyCheckInput()这个js即可。
	String strotheronload = "";
	String buttonReaOnly = "";
	//
	if ("false".equals(request.getAttribute("checkIsSubmit"))) {
		strotheronload = "readonlyCheckInput();disabledCheckButton('CheckCar');disabledCheckButton('CheckProp')";
		buttonReaOnly = "disabled";
	}
%>
<script src="/claim/DAA/quickCase/js/DAAQuickCaseEdit.js"></script>
<script src="/claim/DAA/quickCase/js/DAAQuickCaseCompensateEdit.js"></script>
<script src="/claim/DAA/compensate/js/DAAlLossEdit.js"></script>
<script src="/claim/DAA/quickCase/js/DAAQuickCaseCompensateDWR.js"></script>
<%
	String editType = request.getParameter("editType");

	if (editType.equals("SHOW") || editType.equals("DELETE")) {
%>
<body onload="oMPC.style.visibility='visible';initPaySet();readonlyAllInput();changeIndemnityDuty();initExceptDeductible();<%=strotheronload%>;backCheckSelect()">
	<%
		} else {
	%>

<body onload="oMPC.style.visibility='visible';initPaySet();changeIndemnityDuty();initExceptDeductible();<%=strotheronload%>;">
	<%
		}
	%>
	<form name=fm action="/claim/quickCaseSave.do" method="post" onsubmit="">
		<input type="hidden" name="quickcaseEditType" value='<%=editType%>'>
		<input type="hidden" name="org.apache.struts.taglib.html.TOKEN" value="<%=session.getAttribute("org.apache.struts.action.TOKEN")%>">
		<input type="hidden" id="pageCount" value="2">
		<%-- 功能按钮 --%>
		<DIV id="buttonLayer" style="position: absolute; top: 2px; right: 0px; z-index: 1;">
			<%@include file="/DAA/quickCase/DAAQuickCaseSave.jsp"%>
		</DIV>
		<DIV id="mainLayer" style="position: absolute; top: 30px; left: 2px; height: 520px; z-index: 1;">
			<mpc:container ID="oMPC" style="width:830px;height:520px;">
				<%-- 1.1.查勘信息 --%>
				<mpc:page ID="tabMain" TABTITLE="查勘訊息" TABTEXT="查勘訊息">
					<CENTER>
						<DIV style="height: 515px; background-color: #F7F7F7; overflow: scroll;">
							<%-- 查勘基本信息 --%>
							<%@include file="/DAA/quickCase/DAAQuickCaseCheckHead.jsp"%>
							<%-- 设置免赔率 --%>
							<%@include file="/DAA/quickCase/DAAQuickCaseDeductCond.jsp"%>
							<%-- 查勘损失信息 --%>
							<%@include file="/DAA/quickCase/DAAQuickCaseCheckLossMain.jsp"%>
						</DIV>
					</CENTER>
				</mpc:page>
				<%-- 1.2.定损损失信息 --%>
				<mpc:page ID="tabMain" TABTITLE="定損損失訊息" TABTEXT="定損損失訊息" onclick="flashCertainLossList();">
					<DIV>
						<%@include file="/DAA/quickCase/DAAQuickCaseCertainLossMain.jsp"%>
					</DIV>
				</mpc:page>
				<%-- 1.3.赔付信息 --%>
				<mpc:page ID="tabMain" TABTITLE="賠付訊息" TABTEXT="賠付訊息" onclick="flashCompensateList();">
					<CENTER>
						<DIV style="height: 515px; background-color: #F7F7F7; overflow: scroll;">
							<%@include file="/DAA/quickCase/DAAQuickCaseCompensateEdit.jsp"%>
						</DIV>
					</CENTER>
				</mpc:page>
			</mpc:container>
		</DIV>
	</form>
	<script language=javascript>
		//reason:对删除车辆进行控制
		function beforeDelectRow(field) {
	var count = getElementCount("buttonCertainLossCarDelete");
	if (count >= 3) {
		var row = "";
		for (var i = 0; i < count; i++) {
			if (fm.all("buttonCertainLossCarDelete")[i] == field) {
				row = i - 1;
				break;
			}
		}
		if (fm.all("buttonFlag")[row].value == "disabled") {
			alert("本车为标的车或者已经流入定损不允许删除");
		} else {
			deleteRow(field, 'CertainLossCar');
		}
	} else {
		if (fm.all("buttonFlag").value == "disabled") {
			alert("本车为标的车或者已经流入定损不允许删除");
		} else {
			deleteRow(field, 'CertainLossCar');
		}

	}

}

function beforeDelectCheckCarRow(field) {
	var count = getElementCount("buttonCheckCarDelete");
	if (count >= 3) {
		var row = "";
		for (var i = 0; i < count; i++) {
			if (fm.all("buttonCheckCarDelete")[i] == field) {
				row = i - 1;
				break;
			}
		}
		if (fm.all("buttonFlag")[row].value == "disabled") {
			alert("本车为标的车或者已经流入定损不允许删除");
		} else {
			isDelete(field);
		}
	} else {
		if (fm.all("buttonFlag").value == "disabled") {
			alert("本车为标的车或者已经流入定损不允许删除");
		} else {
			isDelete(field);
		}

	}

}
//嵌套标签页实现脚本

function selectTag(showContent, selfObj) {
	// 操作标签
	var tag = document.getElementById("tags")
		.getElementsByTagName("li");
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

//嵌套标签页样式设置

function setStyle() {
	var tag = document.getElementById("tagContent")
		.getElementsByTagName("div");
	var taglength = tag.length;
	for (i = 0; i < taglength; i++) {
		tag[i].style.width = document.body.offsetWidth;
		tag[i].style.Height = document.body.offsetHeight;
	}
}
	</script>
</body>
</html>