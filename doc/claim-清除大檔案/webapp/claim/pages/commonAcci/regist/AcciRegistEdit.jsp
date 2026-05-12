<%--
****************************************************************************
* DESC       ：报案登记录入/修改页面/非车险通用
* AUTHOR     ：中科软
* CREATEDATE ：2014-03-12 
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<html xmlns:mpc>
<head>
<!--对title处理-->
<title><s:text name="title.registBeforeEdit.editRegist" /></title>
<%-- 标签页样式 --%>
<jsp:include page="/behaviors/MpcStyle.jsp" />
<%-- 页面样式  --%>
<%@ include file="/common/meta_js.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx }/css/Standard.css">
<script src="/claim/pages/commonAcci/regist/js/AcciRegistEdit.js"></script>
<script src="/claim/pages/common/regist/js/95519PerfectEdit.js"></script>
<script language="Javascript" src="/claim/common/js/InputCode.js"></script>
<script src="${ctx}/pages/DAA/compensate/js/autoHospital.js"></script>
<script type="text/javascript">
javascript: window.history.forward(1);

function termTypeChangge() { //当选择团单免导时触发是否放开被保险人输入域
    var termFlag = document.getElementsByName("termFlag");
    if (termFlag.length > 0 && termFlag[0].checked == true) {
        document.getElementById("prpLregistInsuredNameSpan").innerHTML = "<input type=text name=\"prpLregistInsuredName\" title=\"被保险人名称\" style=\"width:40%\" class=\"input\"	value=\"\">";
    } else {
        document.getElementById("prpLregistInsuredNameSpan").innerHTML = "<input type=text name=\"prpLregistInsuredName\" title=\"被保险人名称\" style=\"width:40%\" class=\"codecode\"	value=\"\" ondblclick=\"getCinsured(this)\" onkeyup=\"getCinsured(this)\" onchange=\"getCinsured(this)\">";
    }
} </script>
<script type="text/javascript">
	//mpc调整
	$(function() {
		initWindow();
		$(window).resize(function() {
			initWindow();
		});
	})
</script>
</head>
<c:set var="oldRegistLastAccessedTime" value="" scope="session"></c:set>
<s:if test="#parameters.editTypeOther=='SHOWTASK'">
	<body class="interface" onload="initPage();initSet();readonlyAllInput();hideButton('buttonLayer');oMPC.style.visibility='visible';">
</s:if>
<s:elseif test="#request.editType=='DELETE'||#request.editType=='SHOW'">
	<body class="interface" onload="initPage();initSet();readonlyAllInput();disabledAllButton('buttonArea');oMPC.style.visibility='visible';">
</s:elseif>
<s:else>
	<body class="interface" onload="initPage();initSet();oMPC.style.visibility='visible';">
</s:else>
<DIV id="mainLayer" class="mainLayer">
	<form name=fm action="${ctx }/registSave.do" method="post" onsubmit="return validateForm(this,'Driver','');" autocomplete="off">
		<c:if test="${editType == 'ADD' || editType == 'EDIT' || editType == 'PERFECT'}">
			<s:token></s:token>
		</c:if>
		<TABLE id="btnTable" cellpadding="0" cellspacing="0" border="0" style="height: 22px">
			<TR>
				<TD align="left"></TD>
			</TR>
		</TABLE>
		<mpc:container ID="oMPC">
			<%-- 1.1.报案基本信息页面 --%>
			<mpc:page ID="tabMain" TABTITLE="基本訊息" TABTEXT="基本訊息">
				<CENTER>
					<DIV name="tabMain" class="tabMain">
						<%-- 1.报案主信息头 --%>
						<%@include file="/pages/commonAcci/regist/AcciRegistMainHeadEdit.jsp"%>
						<%-- 3.报案主信息结尾 --%>
						<%@include file="/pages/commonAcci/regist/AcciRegistMainTailEdit.jsp"%>
						<%@include file="/pages/commonAcci/regist/AcciRegistTextEdit.jsp"%>
						<%@include file="/pages/commonAcci/regist/AcciRegistTextEdit2.jsp"%>
						<%-- modify by liyanjie move 4.巨灾代码  20051024--%>
						<%-- 4.巨灾代码--%>
						<%@include file="/pages/common/regist/RegistKelpInfo.jsp"%>
						<%-- 5.报案修改人信息、95519报案补充信息--%>
						<c:if test="${editType=='SHOW'||editType=='PERFECT'}">
							<%@include file="/pages/common/regist/ModifyInfo.jsp"%>
							<%@include file="/pages/common/regist/95519AdditionalInfo.jsp"%>
						</c:if>
					</DIV>
				</CENTER>
			</mpc:page>
			<%-- 1.2.报案受损信息页面 --%>
			<mpc:page ID="tabMain" TABTITLE="受損訊息" TABTEXT="受损訊息">
				<CENTER>
					<DIV name="tabMain" class="tabMain">
						<%@include file="/pages/common/regist/RegistPersonTraceEdit.jsp"%>
					</DIV>
				</CENTER>
			</mpc:page>
		</mpc:container>
		<TABLE id="btnCommon" class="common">
			<TR>
				<TD align="center">
					<c:if test="${param.editTypeOther!='SHOWTASK'}">
						<%-- 保存通用按钮 --%>
						<%@include file="/pages/commonAcci/regist/AcciRegistSave.jsp"%>
					</c:if>
				</TD>
			</TR>
		</TABLE>
	</form>
</DIV>
</body>
</html>