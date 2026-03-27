<%--
****************************************************************************
* DESC       : 工作流节点状态查询结果页面
* AUTHOR     : 理赔组
* CREATEDATE ：2013-02-16
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@page import="com.sinosoft.sysframework.reference.AppConfig"%>
<%@page import="com.sinosoft.claim.common.ConstantCodes"%>
<%@ include file="/common/taglibs.jsp"%>
<%@page import="com.sinosoft.claim.dto.custom.UserDto"%>
<%@page import="ins.framework.common.ServiceFactory"%>
<%@page import="ins.framework.common.*"%>
<%@page import="ins.framework.utils.DataUtils"%>
<%@page import="com.sinosoft.claim.common.service.facade.CodeService"%>
<%@page import="com.sinosoft.claim.schema.model.*"%>
<%
	String comLevel = (String) request.getAttribute("comLevel");
	CodeService codeService = (CodeService) ServiceFactory.getService("codeService");
	//取一下用户吧。。。
	UserDto user = (UserDto) session.getAttribute("user");
	//显示简易赔案的权限
	boolean quickCaseWritePower = false;
	if (user != null) {
		quickCaseWritePower = user.getQuickCaseWritePower();
	}
%>
<html locale="true">
<head>
<title><s:text name="title.wfLogBeforeEdit.nodeFlag" />
	<%--工作流节点状态 --%></title>
<%@include file="/common/meta_css.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<script src="/claim/pages/workflow/task/js/WfLogStatusList.js"> </script>
<script language="javascript">
function submitForm(field) {
    fm.pageNo.value = "1";
    fm.searchFlag.value = "true";
    // reason:当按下某一按钮时请将这个按钮变灰，否则用户可能多按引发错误
    field.disabled = true;
    fm.submit(); //提交
}
//有计算书还没核赔通过就不能在出计算书

function compeCount(count) {
    if (count > 0 && fm.editType.value != 'CANCEL') {
        alert("<s:text name='prompt.workFlow.compeCount'/>"); <%--此案件还有未核赔通过或未提交的计算书，不得再出计算书。--%>
        return false;
    }
    return true;
}
//已经注销的保单不得立案

function otherFlag(otherFlag) {
    if (otherFlag == "1") {
        alert("<s:text name='prompt.workFlow.otherFlag'/>"); <%--此保单已被注销，不能立案。--%>
        return false;
    }
    return true;
}
//确认是否要做简易赔案的操作

function checkQuickCase(registNo) { <%--请确认要把报案为'"+registNo+"'转为简易赔案並进行处理？--%>
    var message = "<s:text name='prompt.workFlow.checkQuickCase'/>'" + registNo + "'<s:text name='prompt.workFlow.checkQuickCase2'/>";
    if (window.confirm(message) == false) {
        return false;
    }
    return true;
}

function document.onkeydown() {
    if (event.keyCode == 13) {
        document.getElementById("button").click();
        return false;
    }
}
//紧急案件清单

function queryUrgentCase() {
    var linkURL = "/claim/wfLogQuery.do?editType=urgentCase&nodeType=urgentCase"; <%--紧急案件清单--%>
    var newWindow = window.open(linkURL, "<s:text name='title.compensate.emergencyCaseListing'/>", "width=800,height=600,top=0,left=0,toolbar=yes,location=no,directories=no,menubar=no,scrollbars=yes,resizable=yes,status=no");
}

function ifSubmit(registNo) { <%--请确认要把报案为+registNo + 回退到单证环节--%>
    var message = "<s:text name='prompt.workFlow.checkQuickCase'/>'" + registNo + "'<s:text name='prompt.workFlow.checkQuickCase3'/>？";
    if (window.confirm(message) == false) {
        return false;
    }
    return true;
}
</script>
</head>
<body onload="ShowAlertMessage();document.onkeydown();">
	<%
		int index = 0;
		String strindex = "";
		String searchField = request.getParameter("searchField");
		String searchLabel = request.getParameter("searchLabel");
		if (searchField == null)
			searchField = "businessNo";
		if (searchLabel == null)
			searchLabel = "businessNo";
		String nodeStatus = request.getParameter("status");
		String nodeType = request.getParameter("nodeType");
		String editType = request.getParameter("editType");
		String method = request.getParameter("method");
		if (editType == null)
			editType = "";
		if (nodeType == null) {
			nodeType = "commo";
		}
		String funcName = request.getParameter("FuncName"); //表示是注销申请/特殊赔案申请的.
		String funcDesc = "";
		int intriskcode = 5;

		SwfLog swfLog = null;
		SwfLog swfLog1 = (SwfLog) request.getAttribute("swfLog");
		String msg = "\n" + swfLog1.getAlertMessage();
		//以险种为依据判断页面跳转
		String riskCode = "";
		String strTitle = "";
		String subTitleTime = "";
		String subTitleColName = "";
		String nowNodeStatus = ""; //做每行状态判断用的
		int colNumber = 5; //基本的列数 基本显示是5列
		String carFlag1 = ""; //表示是否是保单车辆
		String carFlag = ""; //表示是否是保单车辆
		String typeFlag = ""; //表示每行的typeflag的内容
		String dealHref = ""; //处理功能，按钮上的联接
		String backHref = ""; //处理回退的功能，按钮上的联接
		String quickCaseHref = ""; //处理简易赔案的功能，按钮上的联接
		String dispHref = ""; //显示的联接内容
		String flowStr = ""; //用来传递flowid和logNo的後半字串後来又加入riskCode和editType,因为每条都传递的
		String EditLastType = ""; //用来临时保存editType的方式。
		int afterDay = 0; //如果是立案，计算过去的天数
		int afterHour = 0; //如果是立案，计算过去的小时
		int claimLimit = Integer.parseInt(AppConfig.get("sysconst.ClaimLimitHour")); //获得立案时限制
		String toDoTitle = "业务号"; //待处理显示的业务号的名称
		if (nodeStatus.equals("2")) {
			if (editType.equals("DELETE")) {
				editType = "DELETE";
			} else {
				editType = "EDIT";
			}
		}
		if (nodeStatus.equals("3")) {
			// strTitle = "已退回";
			// subTitleTime = "退回时间";
			editType = "EDIT";
		}
		if (nodeStatus.equals("4")) {
			editType = "SHOW";
		}
		if (nodeStatus.equals("0")) {
			editType = "ADD";
		}
		if (nodeStatus.equals("-1")) {
			editType = "CANCEL";
		}
		if (nodeStatus.equals("99")) {
			editType = "SHOW";
			colNumber = 6;
		}
		if (nodeStatus.equals("4") && "modify".equals(method)) {
			editType = "EDIT";

		}
	%>
	<%
		if ((nodeType.equals("compe") && (nodeStatus.equals("0"))) || ((nodeType.equals("compp")) && (nodeStatus.equals("2")))) {
	%>
	<s:set var="toDoTitle" value="%{getText('db.prpLclaim.claimNo')}" scope="page" />
	<%-- 立案号 --%>
	<%
		} else {
	%>
	<s:set var="toDoTitle" value="%{getText('db.prpLregist.registNo')}" scope="page" />
	<%-- 备案号 --%>
	<%
		}
	%>
	<s:set var="strTitle" value="''" scope="page" />
	<%--正在处理 --%>
	<s:set var="subTitleTime" value="''" scope="page" />
	<%-- 业务号 --%>
	<s:if test="#parameters.status[0]==2">
		<s:set var="strTitle" value="%{getText('check.dealingWith')}" scope="page" />
		<%--正在处理 --%>
		<s:set var="subTitleTime" value="%{getText('guarantee.dealIime')}" scope="page" />
		<%-- 处理时间 --%>
	</s:if>
	<s:elseif test="#parameters.status[0]==3">
		<s:set var="strTitle" value="%{getText('check.dealingWith')}" scope="page" />
		<%--已退回  --%>
		<s:set var="subTitleTime" value="%{getText('workflow.backTime')}" scope="page" />
		<%-- 退回时间 --%>
	</s:elseif>
	<s:elseif test="#parameters.status[0]==4">
		<s:set var="strTitle" value="%{getText('common.status.treated')}" scope="page" />
		<%--已处理--%>
		<s:set var="subTitleTime" value="%{getText('workflow.flowTime')}" scope="page" />
		<%-- 流出时间 --%>
	</s:elseif>
	<s:elseif test="#parameters.status[0]==0">
		<s:set var="strTitle" value="%{getText('common.status.untreated')}" scope="page" />
		<%--未处理 --%>
		<s:set var="subTitleTime" value="%{getText('claim.intoTime')}" scope="page" />
		<%-- 流入时间 --%>
	</s:elseif>
	<s:elseif test="#parameters.status[0]==-1">
		<s:set var="strTitle" value="''" scope="page" />
		<%----%>
		<s:set var="subTitleTime" value="%{getText('claim.intoTime')}" scope="page" />
		<%-- 流入时间 --%>
	</s:elseif>
	<s:elseif test="#parameters.status[0]==99">
		<s:set var="strTitle" value="''" scope="page" />
		<%-- --%>
		<s:set var="subTitleTime" value="%{getText('guarantee.dealIime')}" scope="page" />
		<%-- 处理时间 --%>
	</s:elseif>
	<s:if test="#parameters.status[0]==4&&#parameters.method[0]=='modify'">
		<s:set var="strTitle" value="%{getText('common.status.treated')}" scope="page" />
		<%--已处理 --%>
		<s:set var="subTitleTime" value="%{getText('workflow.flowTime')}" scope="page" />
		<%--流出时间 --%>
	</s:if>
	<input type="hidden" name="testMessage" class="common" value=<%=msg%>>
	<form name="fm" action="/claim/wfLogQuery.do" method="post" onSubmit="return validateForm(this);">
		<table border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td colspan=4 class="formtitle">
					<s:text name="common.status.queryConditions" />
					<%--查询条件 --%>
				</td>
			</tr>
			<tr>
				<td class='title'>
					<%
						if (searchLabel.equals("businessNo")) {
							// subTitleColName = "业务号";
					%><s:set name="subTitleColName" value="%{getText('sendUndwrt.BusinessNumber')}" />
					<%-- 业务号 --%>
					<%
						} else if (searchLabel.equals("businessNo") && nodeType.equals("claim")) {
							//subTitleColName = "报案号";
					%><s:set name="subTitleColName" value="%{getText('prompt.queRegist.RegistNo')}" />
					<%-- 报案号 --%>
					<%
						} else if (searchLabel.equals("registNo")) {
					%><s:set name="subTitleColName" value="%{getText('db.prpLregist.registNo')}" />
					<%-- 备案号码 --%>
					<%
						//subTitleColName = "报案号";
						} else if (searchLabel.equals("claimNo")) {
					%><s:set name="subTitleColName" value="%{getText('db.prpLclaim.claimNo')}" />
					<%-- 立案号 --%>
					<%
						// subTitleColName = "立案号";
						} else if (searchLabel.equals("compensateNo")) {
					%><s:set name="subTitleColName" value="%{getText('db.prpLcompensate.compensateNo')}" />
					<%-- 赔款计算书号 --%>
					<%
						// subTitleColName = "赔款计算书号";
						} else if (searchLabel.equals("endCaseNo")) {
					%><s:set name="subTitleColName" value="%{getText('db.prpLcompensate.caseNo')}" />
					<%-- 结案号 --%>
					<%
						// subTitleColName = "结案号";
						} else if (searchLabel.equals("specialNo")) {
					%><s:set name="subTitleColName" value="%{getText('claim.special.fileNumber')}" />
					<%-- 特殊归档号 --%>
					<%
						// subTitleColName = "特殊归档号";
						} else if (searchLabel.equals("checkNo")) {
					%><s:set name="subTitleColName" value="%{getText('db.prpLacciCheck.checkNo')}" />
					<%-- 调查号 --%>
					<%
						// subTitleColName = "调查号";
						}
						//out.print(subTitleColName);
					%>
					<s:property value="#attr.subTitleColName" />
				</td>
				<td class='input'>
					<select class=tag name="BusinessNoSign">
						<option value="=">=</option>
						<option value="=*" <c:if test="${param.BusinessNoSign=='=*'}"> selected="selected"</c:if>>=*</option>
					</select>
					<input type=text name="BusinessNo" class="query" style="width: 70%" value="<c:out value='${param.BusinessNo}'/>">
				</td>
				<td class='title'>
					<s:text name="db.prpLclaim.policyNo" />
				</td>
				<td class='input'>
					<select class=tag name="PolicyNoSign">
						<option value="=">=</option>
						<option value="=*" <c:if test="${param.PolicyNoSign=='=*'}"> selected="selected"</c:if>>=*</option>
					</select>
					<input type=text name="PolicyNo" class="query" style="width: 70%" value="<c:out value='${param.PolicyNo}'/>">
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="db.prpLregist.riskCode" />
				</td>
				<td class='input'>
					<select class=tag name="RiskCodeNoSign">
						<option value="=">=</option>
					</select>
					<input type=text name="RiskCode" class="query" style="width: 70%" value="<c:out value='${param.RiskCode}'/>">
				</td>
				<td class='title'>
					<s:property value="#attr.subTitleTime" />
				</td>
				<td class='input'>
					<%-- <input type=text style="width:85" width="30%" name="statStartDate" class="Wdate" onfocus="WdatePicker({lang:'zh-tw',maxDate:'%y-%M-%d'})" value="<c:out value='${param.statStartDate}'/>" >--%>
					<rc:rcDate name="statStartDate" style="width:41%" value="${param.statStartDate}" />
					&nbsp;
					<s:text name="prompt.to" />
					<%--<input type=text style="width:85" name="statEndDate" class="Wdate" onfocus="WdatePicker({lang:'zh-tw',maxDate:'%y-%M-%d'})" value="<c:out value='${param.statEndDate}'/>" >--%>
					<rc:rcDate name="statEndDate" style="width:41%" value="${param.statEndDate}" />
				</td>
			</tr>
			<tr>
				<%
					if (!(searchLabel.equals("compensateNo") && nodeType.equals("compp")) && !(searchLabel.equals("claimNo") && nodeType.equals("claim")) && !(searchLabel.equals("claimNo") && nodeType.equals("compe"))
							&& !(searchLabel.equals("claimNo") && nodeType.equals("endca")) && !(searchLabel.equals("checkNo") && nodeType.equals("check")) && !(searchLabel.equals("claimNo") && nodeType.equals("cance"))
							&& !(searchLabel.equals("specialNo") && nodeType.equals("speci"))) {
				%>
				<td class='title'>
					<s:text name="db.prpCmain.insured" />
				</td>
				<td class='input'>
					<select class=tag name="insuredNameSign">
						<option value="=">=</option>
						<option value="=*" <c:if test="${param.insuredNameSign=='=*'}"> selected="selected"</c:if>>=*</option>
					</select>
					<input type=text name="insuredName" class="query" style="width: 70%" value="<c:out value='${param.insuredName}'/>">
				</td>
				<td class='title'>
					<c:if test="${param.nodeType=='speci'}">
						<s:text name="db.prpLregist.registNo" />
					</c:if>
				</td>
				<td class='input'>
					<c:if test="${param.nodeType=='speci'}">
						<select class=tag name="RegistNoSign">
							<option value="=">=</option>
							<option value="=*">=*</option>
						</select>
						<input type=text name="RegistNo" class="query" style="width: 70%">
					</c:if>
				</td>
				<td class='title'>
					<c:if test="${param.nodeType=='verip'}">
						<s:text name="db.prpLregist.conSignType" />
					</c:if>
				</td>
				<td class='input'>
					<c:if test="${param.nodeType=='verip'}">
						<select class=tag name="conSignTypeSign">
							<option value="=">=</option>
						</select>
						<select class=query name="conSignType" style="width: 160">
							<c:if test="${requestScope.comLevel!=1}">
								<option value="verip">
									<s:text name="regist.prpLregist.localTrust" />
								</option>
							</c:if>
							<option value="verpo">
								<s:text name="regist.prpLregist.diffTrust" />
							</option>
						</select>
					</c:if>
				</td>
				<%
					} else {
						if (searchLabel.equals("compensateNo") && nodeType.equals("compp")) {
				%>
				<td class='title'>
					<s:text name="db.prpLregist.registNo" />
				</td>
				<td class='input'>
					<select class=tag name="ComppRegistNoSign">
						<option value="=">=</option>
						<option value="=*" <c:if test="${param.ComppRegistNoSign=='=*'}"> selected="selected"</c:if>>=*</option>
					</select>
					<input type=text name="ComppRegistNo" class="query" style="width: 70%" value="<c:out value='${param.ComppRegistNo}'/>">
				</td>
				<%
					} else if (searchLabel.equals("claimNo") && nodeType.equals("claim")) {
				%>
				<td class='title'>
					<s:text name="db.prpLregist.registNo" />
				</td>
				<td class='input'>
					<select class=tag name="ClaimRegistNoSign">
						<option value="=">=</option>
						<option value="=*" <c:if test="${param.ClaimRegistNoSign=='=*'}"> selected="selected"</c:if>>=*</option>
					</select>
					<input type=text name="ClaimRegistNo" class="query" style="width: 70%" value="<c:out value='${param.ClaimRegistNo}'/>">
				</td>
				<%
					} else if (searchLabel.equals("claimNo") && nodeType.equals("compe")) {
				%>
				<td class='title'>
					<s:text name="db.prpLregist.registNo" />
				</td>
				<td class='input'>
					<select class=tag name="CompeRegistNoSign">
						<option value="=" selected>=</option>
					</select>
					<input type=text name="CompeRegistNo" class="query" style="width: 70%" value="<c:out value='${param.CompeRegistNo}'/>">
				</td>
				<%
					} else if (searchLabel.equals("claimNo") && nodeType.equals("endca")) {
				%>
				<td class='title'>
					<s:text name="db.prpLregist.registNo" />
				</td>
				<td class='input'>
					<select class=tag name="EndcaRegistNoSign">
						<option value="=" selected>=</option>
					</select>
					<input type=text name="EndcaRegistNo" class="query" style="width: 70%" value="<c:out value='${param.EndcaRegistNo}'/>">
				</td>
				<%
					} else if (searchLabel.equals("checkNo") && nodeType.equals("check")) {
				%>
				<td class='title'>
					<s:text name="db.prpLregist.registNo" />
				</td>
				<td class='input'>
					<select class=tag name="RegistNoSign">
						<option value="=">=</option>
					</select>
					<input type=text name="RegistNo" class="query" style="width: 70%" value="<c:out value='${param.RegistNo}'/>">
				</td>
				<%
					} else if (searchLabel.equals("claimNo") && nodeType.equals("cance")) {
				%>
				<td class='title'>
					<s:text name="db.prpLregist.registNo" />
				</td>
				<td class='input'>
					<select class=tag name="RegistNoSign">
						<option value="=">=</option>
					</select>
					<input type=text name="RegistNo" class="query" style="width: 70%" value="<c:out value='${param.RegistNo}'/>">
				</td>
				<%
					} else if (searchLabel.equals("specialNo") && nodeType.equals("speci")) {
				%>
				<td class='title'>
					<s:text name="db.prpLregist.registNo" />
				</td>
				<td class='input'>
					<select class=tag name="RegistNoSign">
						<option value="=">=</option>
					</select>
					<input type=text name="RegistNo" class="query" style="width: 70%" value="<c:out value='${param.RegistNo}'/>">
				</td>
				<%
					}
				%>
				<td class='title'>
					<s:text name="db.prpLregist.insuredName" />
				</td>
				<td class='input'>
					<select class=tag name="insuredNameSign">
						<option value="=">=</option>
						<option value="=*" <c:if test="${param.insuredNameSign=='=*'}"> selected="selected"</c:if>>=*</option>
					</select>
					<input type=text name="insuredName" class="query" style="width: 70%" value="<c:out value='${param.insuredName}'/>">
				</td>
				<%
					}
				%>
			</tr>
			<tr>
				<!-- 非车险案件只能在报案环节注销 -->
				<%
					if ((nodeType.equals("claim") || nodeType.equals("check") || nodeType.equals("certi")) || nodeType.equals("compe") && nodeStatus.equals("-1")) {
				%>
				<td class="title" style="color: red" colspan="3">
					<s:text name="prompt.schedule.query1" />
					<%--"="符号，必须精确查询。 --%>
					<br>
					<s:text name="prompt.schedule.query2" />
					<%-- "=*"符号，前匹配後模糊的查询。 --%>
					<br>
				</td>
				<td class="title" style="color: red" colspan="1">
					<s:text name="workflow.query3" />
					<%--非车险立案提交後，便不能再做注销！！！ --%>
				</td>
				<%
					} else {
				%>
				<td class="title" style="color: red" colspan="2">
					<s:text name="prompt.schedule.query1" />
					<%--"="符号，必须精确查询。 --%>
					<br>
					<s:text name="prompt.schedule.query2" />
					<%-- "=*"符号，前匹配後模糊的查询。 --%>
					<br>
				</td>
				<!-- 理算环节增加紧急案件清单 -->
				<%
					if ((nodeType.equals("compe") && (nodeStatus.equals("0"))) || ((nodeType.equals("compp")) && (nodeStatus.equals("2")))) {
				%>
				<td class="title" style="color: red" colspan="2" align='center'>
					<%--紧急案件清单 --%>
					<input type=button id="button" name="urgentCaseButton" class='bigbutton' value="<s:text name='title.compensate.emergencyCaseListing'/>" onClick="queryUrgentCase();">
					<br> <font color='red'><s:text name="regist.prpLregist.emergencyCaseList" /></font>
				</td>
				<%
					} else {
				%>
				<td class="title" style="color: red" colspan="2"></td>
				<%
					}
					}
				%>
			</tr>
			<tr>
				<td class='button' colspan="4">
					<input type=button id="button" name="queryButton" class='button' disabled value="<s:text name='button.query.value' />" onClick="submitForm(this);">
				</td>
			</tr>
		</table>
		<table class="common" cellpadding="5" cellspacing="1">
			<tr>
				<td colspan=12 class="formtitle">
					<%-- 增加国际化，不使用java代码
                //根据节点的类型显示内容
                if (swfLog1.getNodeType().equals("commo")) {
                    strTitle = strTitle + "信息";
                    subTitleColName = "业务号码";
                }
                if (swfLog1.getNodeType().equals("regis")) {
                    strTitle = strTitle + "报案信息";
                    subTitleColName = "报案号";
                }
                if (swfLog1.getNodeType().equals("check")) {
                    if (request.getAttribute("com_sinosoft_type") != null && request.getAttribute("com_sinosoft_type").equals("acci")) {
                        strTitle = strTitle + "调查信息";
                        subTitleColName = "调查号";
                    } else {
                        strTitle = strTitle + "查勘信息";
                        subTitleColName = "报案号";
                    }
                }
                if (swfLog1.getNodeType().equals("certa")) {
                    strTitle = strTitle + "定损信息";
                    subTitleColName = "报案号";
                }
                if (swfLog1.getNodeType().equals("verip")) {
                    strTitle = strTitle + "核价信息";
                    subTitleColName = "报案号";
                }
                if (swfLog1.getNodeType().equals("verif")) {
                    strTitle = strTitle + "核损信息";
                    subTitleColName = "报案号";
                }
                if (swfLog1.getNodeType().equals("propc")) {
                    strTitle = strTitle + "财产定损信息";
                    subTitleColName = "报案号";
                }
                if (swfLog1.getNodeType().equals("backc")) {
                    strTitle = strTitle + "复勘信息";
                    subTitleColName = "报案号";
                }
                if (swfLog1.getNodeType().equals("propv")) {
                    strTitle = strTitle + "财产核损信息";
                    subTitleColName = "报案号";
                }
                if (swfLog1.getNodeType().equals("claim")) {
                    strTitle = strTitle + "立案信息";
                    subTitleColName = "立案号";
                    if (nodeStatus.equals("0"))
                        toDoTitle = "报案号";
                }
                if (swfLog1.getNodeType().equals("prepa")) {
                    strTitle = strTitle + "预赔信息";
                    subTitleColName = "报案号";
                }
                if (swfLog1.getNodeType().equals("endca")) {
                    strTitle = strTitle + "结案信息";
                    subTitleColName = "结案号";
                }
                if (swfLog1.getNodeType().equals("certi")) {
                    strTitle = strTitle + "单证信息";
                    subTitleColName = "报案号";
                    if (nodeStatus.equals("0"))
                        toDoTitle = "报案号";
                }
                if (swfLog1.getNodeType().equals("compe")) {
                    strTitle = strTitle + "理算信息";
                    subTitleColName = "立案号";
                    if (nodeStatus.equals("0"))
                        toDoTitle = "立案号";
                }
                if (swfLog1.getNodeType().equals("sched")) {
                    strTitle = strTitle + "调度信息";
                    subTitleColName = "报案号";
                }
                if (swfLog1.getNodeType().equals("wound")) {
                    strTitle = strTitle + "人伤跟踪信息";
                    subTitleColName = "报案号";
                }
                if (swfLog1.getNodeType().equals("cance")) {
                    strTitle = strTitle + "注销/拒赔信息";
                    subTitleColName = "业务号";
                }
                if (swfLog1.getNodeType().equals("veriw")) {
                    strTitle = strTitle + "人伤核损信息";
                    subTitleColName = "报案号";
                }
                if (swfLog1.getNodeType().equals("compp")) {
                    strTitle = strTitle + "计算书信息";
                    subTitleColName = "赔款计算书号";
                }
                if (swfLog1.getNodeType().equals("speci")) {
                    strTitle = strTitle + "特殊赔案信息";
                    subTitleColName = "特殊归档号";
                }
                if (swfLog1.getNodeType().equals("commo")) {
                    strTitle = strTitle + "信息";
                    subTitleColName = "业务号码";
                }
                if (swfLog1.getNodeType().equals("backv")) {
                    strTitle = strTitle + "回访信息";
                    subTitleColName = "报案号";
                }
                if (swfLog1.getNodeType().equals("veric")) {
                    strTitle = strTitle + "核赔信息";
                    subTitleColName = "业务号";
                }
               // out.print(strTitle);
            --%>
					<s:if test="#attr.swfLog.nodeType=='commo'">
						<s:set var="strTitle" value="%{#attr.strTitle+getText('workflow.statusMes')}" />
						<%--strTitle =strTitle + "信息" --%>
						<s:set var="subTitleColName" value="%{getText('workflow.businessNo')}" />
						<%--业务号码--%>
					</s:if>
					<s:elseif test="#attr.swfLog.nodeType=='regis'">
						<s:set var="strTitle" value="%{#attr.strTitle+getText('button.reportedInformation.value')}" />
						<%--strTitle =strTitle + "报案信息" --%>
						<s:set var="subTitleColName" value="%{getText('prpLregist.registNo')}" />
						<%--报案号--%>
					</s:elseif>
					<s:elseif test="#attr.swfLog.nodeType=='check'">
						<s:if test="#attr.com_sinosoft_type!=null&&#attr.com_sinosoft_type=='acci'">
							<s:set var="strTitle" value="%{#attr.strTitle+getText('prompt.accicheck.message')}" />
							<%--strTitle =strTitle + "调查信息" --%>
							<s:set var="subTitleColName" value="%{getText('db.prpLacciCheck.checkNo')}" />
							<%--报案号--%>
						</s:if>
						<s:else>
							<s:set var="strTitle" value="%{#attr.strTitle+getText('check.surveyInfo')}" />
							<%--strTitle =strTitle + "查勘信息" --%>
							<s:set var="subTitleColName" value="%{getText('prpLregist.registNo')}" />
							<%--报案号--%>
						</s:else>
					</s:elseif>
					<s:elseif test="#attr.swfLog.nodeType=='certa'">
						<s:set var="strTitle" value="%{#attr.strTitle+getText('prompt.CertainLoss.message')}" />
						<%--strTitle =strTitle + "定损信息" --%>
						<s:set var="subTitleColName" value="%{getText('prpLregist.registNo')}" />
						<%--报案号--%>
					</s:elseif>
					<s:elseif test="#attr.swfLog.nodeType=='verip'">
						<s:set var="strTitle" value="%{#attr.strTitle+getText('prompt.verifyPrice.message')}" />
						<%--strTitle =strTitle + "核价信息" --%>
						<s:set var="subTitleColName" value="%{getText('prpLregist.registNo')}" />
						<%--报案号--%>
					</s:elseif>
					<s:elseif test="#attr.swfLog.nodeType=='verif'">
						<s:set var="strTitle" value="%{#attr.strTitle+getText('prompt.verifyLoss.message')}" />
						<%--strTitle =strTitle + "核损信息" --%>
						<s:set var="subTitleColName" value="%{getText('prpLregist.registNo')}" />
						<%--报案号--%>
					</s:elseif>
					<s:elseif test="#attr.swfLog.nodeType=='propc'">
						<s:set var="strTitle" value="%{#attr.strTitle+getText('prompt.CertainLoss.enterLeast')}" />
						<%--strTitle =strTitle + "财产定损信息" --%>
						<s:set var="subTitleColName" value="%{getText('prpLregist.registNo')}" />
						<%--报案号--%>
					</s:elseif>
					<s:elseif test="#attr.swfLog.nodeType=='backc'">
						<s:set var="strTitle" value="%{#attr.strTitle+getText('prompt.check.message')}" />
						<%--strTitle =strTitle + "复勘信息" --%>
						<s:set var="subTitleColName" value="%{getText('prpLregist.registNo')}" />
						<%--报案号--%>
					</s:elseif>
					<s:elseif test="#attr.swfLog.nodeType=='propv'">
						<s:set var="strTitle" value="%{#attr.strTitle+getText('prompt.verifyLoss.enterLeast')}" />
						<%--strTitle =strTitle + "财产核损信息" --%>
						<s:set var="subTitleColName" value="%{getText('prpLregist.registNo')}" />
						<%--报案号--%>
					</s:elseif>
					<s:elseif test="#attr.swfLog.nodeType=='claim'">
						<s:set var="strTitle" value="%{#attr.strTitle+getText('info.claim')}" />
						<%--strTitle =strTitle + "立案信息" --%>
						<s:set var="subTitleColName" value="%{getText('db.prpLlawsuit.claimNo')}" />
						<%--立案号--%>
						<s:if test="#parpms.status[0]==0">
							<s:set var="toDoTitle" value="%{getText('prpLregist.registNo')}" />
							<%--报案号--%>
						</s:if>
					</s:elseif>
					<s:elseif test="#attr.swfLog.nodeType=='prepa'">
						<s:set var="strTitle" value="%{#attr.strTitle+getText('prompt.prepay.message')}" />
						<%--strTitle =strTitle + "预赔信息" --%>
						<s:set var="subTitleColName" value="%{getText('prpLregist.registNo')}" />
						<%--报案号--%>
					</s:elseif>
					<s:elseif test="#attr.swfLog.nodeType=='endca'">
						<s:set var="strTitle" value="%{#attr.strTitle+getText('info.endCase')}" />
						<%--strTitle =strTitle + "结案信息" --%>
						<s:set var="subTitleColName" value="%{getText('db.prpLcompensate.caseNo')}" />
						<%--结案号--%>
						<s:if test="#parameters.status[0]==0">
							<s:set var="toDoTitle" value="%{getText('query.claimNumber')}" />
							<%--赔案号--%>
						</s:if>
					</s:elseif>
					<s:elseif test="#attr.swfLog.nodeType=='certi'">
						<s:set var="strTitle" value="%{#attr.strTitle+getText('prompt.certify.message')}" />
						<%--strTitle =strTitle + "结案信息" --%>
						<s:set var="subTitleColName" value="%{getText('prpLregist.registNo')}" />
						<%--报案号--%>
						<s:if test="#attr.status[0]==0">
							<s:set var="toDoTitle" value="%{getText('prpLregist.registNo')}" />
							<%--报案号--%>
						</s:if>
					</s:elseif>
					<s:elseif test="#attr.swfLog.nodeType=='compe'">
						<s:set var="strTitle" value="%{#attr.strTitle+getText('compensate.adjustmentInformation')}" />
						<%--strTitle =strTitle + "理算信息" --%>
						<s:set var="subTitleColName" value="%{getText('prpLclaim.claimNo')}" />
						<%--立案号--%>
						<s:if test="#attr.status[0]==0">
							<s:set var="toDoTitle" value="%{getText('prpLclaim.claimNo')}" />
							<%--立案号--%>
						</s:if>
						<s:if test="#parameters.status[0]==-1">
							<s:set var="toDoTitle" value="%{getText('prpLclaim.claimNo')}" />
							<%--立案号--%>
						</s:if>
					</s:elseif>
					<s:elseif test="#attr.swfLog.nodeType=='sched'">
						<s:set var="strTitle" value="%{#attr.strTitle+getText('check.schedulInfo')}" />
						<%--strTitle =strTitle + "调度信息" --%>
						<s:set var="subTitleColName" value="%{getText('prpLregist.registNo')}" />
						<%--报案号--%>
					</s:elseif>
					<s:elseif test="#attr.swfLog.nodeType=='wound'">
						<s:set var="strTitle" value="%{#attr.strTitle+getText('info.personTrack')}" />
						<%--strTitle =strTitle + "人伤跟踪信息" --%>
						<s:set var="subTitleColName" value="%{getText('prpLregist.registNo')}" />
						<%--报案号--%>
					</s:elseif>
					<s:elseif test="#attr.swfLog.nodeType=='cance'">
						<s:set var="strTitle" value="%{#attr.strTitle+getText('prompt.claimCancel.message')}" />
						<%--strTitle =strTitle + "注销/拒赔信息" --%>
						<s:set var="subTitleColName" value="%{getText('sendUndwrt.BusinessNumber')}" />
						<%--业务号--%>
						<s:if test="#parameters.status[0]==0">
							<s:set var="toDoTitle" value="%{getText('sendUndwrt.BusinessNumber')}" />
							<%-- 业务号 --%>
						</s:if>
					</s:elseif>
					<s:elseif test="#attr.swfLog.nodeType=='veriw'">
						<s:set var="strTitle" value="%{#attr.strTitle+getText('prompt.personverifyLoss.message')}" />
						<%--strTitle =strTitle + "人伤核损信息" --%>
						<s:set var="subTitleColName" value="%{getText('prpLregist.registNo')}" />
						<%--报案号--%>
					</s:elseif>
					<s:elseif test="#attr.swfLog.nodeType=='compp'">
						<s:set var="strTitle" value="%{#attr.strTitle+getText('prompt.compensate.message')}" />
						<%--strTitle =strTitle + "计算书信息" --%>
						<s:set var="subTitleColName" value="%{getText('db.prpLpersonloss.compensateNo')}" />
						<%--赔款计算书号--%>
					</s:elseif>
					<s:elseif test="#attr.swfLog.nodeType=='speci'">
						<s:set var="strTitle" value="%{#attr.strTitle+getText('specialCase.SpecialClaims.message')}" />
						<%--strTitle =strTitle + "特殊赔案信息" --%>
						<s:set var="subTitleColName" value="%{getText('claim.specailCaseNumber')}" />
						<%--特殊归档号--%>
						<s:if test="#parameters.status[0]==0">
							<s:set var="toDoTitle" value="%{getText('workflow.businessNo')}" />
							<%--业务号码--%>
						</s:if>
					</s:elseif>
					<s:elseif test="#attr.swfLog.nodeType=='commo'">
						<s:set var="strTitle" value="%{#attr.strTitle+getText('workflow.statusMes')}" />
						<%--strTitle =strTitle + "信息" --%>
						<s:set var="subTitleColName" value="%{getText('workflow.businessNo')}" />
						<%--业务号码--%>
					</s:elseif>
					<s:elseif test="#attr.swfLog.nodeType=='backv'">
						<s:set var="strTitle" value="%{#attr.strTitle+getText('backVisit.reviewInformation')}" />
						<%--strTitle =strTitle + "信息" --%>
						<s:set var="subTitleColName" value="%{getText('prpLregist.registNo')}" />
						<%--报案号--%>
					</s:elseif>
					<s:elseif test="#attr.swfLog.nodeType=='veric'">
						<s:set var="strTitle" value="%{#attr.strTitle+getText('workflow.undwrt.message')}" />
						<%--strTitle =strTitle + "核赔信息" --%>
						<s:set var="subTitleColName" value="%{getText('sendUndwrt.BusinessNumber')}" />
						<%--业务号--%>
					</s:elseif>
					<s:property value="#attr.strTitle" />
				</td>
			</tr>
			<tr>
				<td class="centertitle" style="width: 4%">
					<s:text name="regist.prpLregist.serialNo" />
				</td>
				<%
					if (nodeStatus.equals("0") || nodeStatus.equals("-1") || nodeStatus.equals("99")) {
				%>
				<td class="centertitle" style="width: 8%">
					<s:text name="regist.prpLregist.status" />
				</td>
				<td class="centertitle" style="width: 16%">
					<s:property value="#attr.toDoTitle" />
				</td>
				<%
					} else {
				%>
				<td class="centertitle" style="width: 16%">
					<s:property value="#attr.subTitleColName" />
				</td>
				<%
					}
				%>
				<td class="centertitle" style="width: 16%">
					<s:text name="db.prpCmain.policyNo" />
				</td>
				<td class="centertitle" style="width: 14%">
					<s:text name="db.prpLregist.riskCode" />
				</td>
				<%
					if (nodeType.equals("certa")) {
				%>
				<td class="centertitle">要保人</td>
				<%
					} else {
				%>
				<td class="centertitle">被保險人</td>
				<%
					}
					if (nodeType.equals("wound")||nodeType.equals("veriw")) {
				%>
				<td class="centertitle">
					人員名稱
				</td>
				<%
					}
					if (nodeType.equals("claim") && nodeStatus.equals("0")) {//如果等於立案节点
				%>
				<td class="centertitle">
					<s:text name="workflow.leave" />
					<%--剩余(H) --%>
				</td>
				<%
					colNumber = colNumber + 1; //列增加1行,如果是定损的话，加入定损类型
					} else if (nodeType.equals("certa")) {//如果等於定损节点
				%>
				<td class="centertitle">
					<s:text name="workflow.quickLength" />
					<%--紧急程度 --%>
				</td>
				<td class="centertitle">
					<s:text name="db.prpCitem_car.licenseNo" />
					<%--车牌号码 --%>
				</td>
				<td class="centertitle">
					<s:text name="regist.prpLregist.insureCar" />
					<%--保单车辆 --%>
				</td>
				<%
					colNumber = colNumber + 3; //列增加1行,如果是定损的话，加入定损类型
					} else if (nodeType.equals("verip")) {//如果等於核价节点
				%>
				<td class="centertitle">
					<s:text name="db.prpCitem_car.licenseNo" />
					<%--车牌号码 --%>
				</td>
				<td class="centertitle">
					<s:text name="regist.prpLregist.insureCar" />
					<%--保单车辆 --%>
				</td>
				<%
					colNumber = colNumber + 2; //列增加1行,如果是核价的话，加入核价类型
					} else if (nodeType.equals("verif")) {//如果等於核损节点
				%>
				<td class="centertitle">
					<s:text name="db.prpCitem_car.licenseNo" />
					<%--车牌号码 --%>
				</td>
				<td class="centertitle">
					<s:text name="regist.prpLregist.insureCar" />
					<%--保单车辆 --%>
				</td>
				<%
					colNumber = colNumber + 2; //列增加1行,如果是核损的话，加入核损类型
					} else if (nodeType.equals("backv")) {//如果等於回访节点
				%>
				<td class="centertitle">
					<s:text name="workflow.visitType" />
					<%--回访类型 --%>
				</td>
				<%
					colNumber = colNumber + 1; //列增加1行,回访类型
					} else if (nodeType.equals("check")) {//如果等於查勘节点
						//原因意健险没有车牌号码这一列
						if ("acci".equals(request.getAttribute("com_sinosoft_type"))) {
				%>
				<td class="centertitle">
					<s:text name="db.prpLloss.licenseNo" />
					<%--车牌号码 --%>
				</td>
				<%
					colNumber = colNumber + 1; //列增加4行,加入车牌号码等
						}
					} else if (nodeType.equals("compp") || nodeType.equals("veric")) {//如果等於计算书或者特殊赔案节点
				%>
				<td class="centertitle">
					<s:text name="db.prpLclaim.claimNo" />
					<%--立案号码 --%>
				</td>
				<%
					colNumber = colNumber + 1; //列增加4行,加入车牌号码等
					} else if (nodeType.equals("speci")) {
						if (nodeStatus.equals("0")) {
				%>
				<td class="centertitle">
					<s:text name="workflow.taskType" />
					<%--立案类型 --%>
				</td>
				<%
					} else {
				%>
				<td class="centertitle">
					<s:text name="db.prpLclaim.claimNo" />
					<%--立案号码 --%>
				</td>
				<%
					}
						colNumber = colNumber + 1; //列增加1行,加入车牌号码等
					} else if (nodeType.equals("sched") || nodeType.equals("schel")) {//如果等於定损调度或者查勘调度节点
				%>
				<td class="centertitle">
					<s:text name="workflow.dangerMark" />
					<%--出险标的 --%>
				</td>
				<%
					if (nodeStatus.equals("0")) {
				%>
				<td class="centertitle" style="width: 12%">
					<s:text name="workflow.wait" />
					<%--等候 --%>
				</td>
				<td class="centertitle">
					<s:text name="workflow.whetherCheckedAdjust" />
					<%--是否查勘调度 --%>
				</td>
				<%
					colNumber = colNumber + 1;
						}
						colNumber = colNumber + 2; //列增加1行,加入车牌号码等
					}
					if (!nodeType.equals("compe")) {//如果不等於理算节点，全都显示处理人员这一列
				%>
				<td class="centertitle">
					<s:text name="workflow.dealPerson" />
					<%--处理人员 --%>
				</td>
				<%
					}
					if (nodeType.equals("certa") && nodeStatus.equals("3")) {//定损退回节点，标志由哪退回
				%>
				<td class="centertitle">
					<s:text name="workflow.returnNode" />
					<%--退回节点 --%>
				</td>
				<%
					} else if (nodeType.equals("compe")) {//如果等於理算节点
				%>
				<td class="centertitle">
					<s:text name="quickCase.operator" />
					<%--操作人员 --%>
				</td>
				<%
					colNumber = colNumber + 2;
					}
					if (!nodeType.equals("sched")) {
				%>
				<td class="centertitle" style="width: 16%">
					<s:property value="#attr.subTitleTime" />
				</td>
				<%
					}
					//如果等於通用节点
					if (nodeType.equals("commo")) {
				%>
				<td class="centertitle" style="width: 16%">
					<s:text name="db.prpGnode.nodeName" />
					<%--节点名称 --%>
				</td>
				<%
					colNumber = colNumber + 1;
					}
					if ((nodeStatus.equals("0") || nodeStatus.equals("-1") || nodeStatus.equals("4") || nodeStatus.equals("2")) && !nodeType.equals("compp")) {
				%>
				<td class="centertitle" style="width: 5%">
					<s:text name="replevy.operate" />
					<%--操作 --%>
				</td>
				<%
					colNumber = colNumber + 2;
					}
				%>
			</tr>
			<%
				//原因需要区别意健险和其他险种，点不同险种的菜单时显示不同的页面信息。以下代码用於显示意健险的界面.
				SwfLog swfLogTmp = (SwfLog) request.getAttribute("swfLog");
				int countLogDto = swfLogTmp.getSwfLogList().size();
			%>
			<%
				//原因以下代码用於显示非意键险时的界面。
			%>
			<c:forEach items="${requestScope.swfLog.swfLogList}" var="swfLogList1" varStatus="stat">
				<c:choose>
					<c:when test="${stat.index%2==0}">
						<tr class="listodd">
					</c:when>
					<c:otherwise>
						<tr class="listeven">
					</c:otherwise>
				</c:choose>
				<td align="center">
					<c:out value="${stat.count}" />
				</td>
				<s:set var="carFlag" value="%{getText('certainLoss.thirdCarLoss.thirdCar')}" scope="page" />
				<%--三者车 --%>
				<%
					strindex = "'" + String.valueOf(index) + "'";
						//取得该行的DTO的数据 判断节点类型和状态，根据不同的状态，实现不同的按扭内容
						swfLog = swfLog1.getSwfLogList().get(index);
						typeFlag = swfLog.getTypeFlag();
						dealHref = ""; //清空联接内容
						backHref = ""; //清空处理回退的联接
						dispHref = ""; //清空显示的联接
						quickCaseHref = ""; //清空简易赔案的操作联结
						// carFlag = "三者车"; 
						//判断insureCarFlag的属性
						EditLastType = editType; //复制
						String strRiskTypeNow = codeService.translateRiskCodetoRiskType(swfLog.getRiskCode());
						if ("1".equals(swfLog.getInsureCarFlag())) {
							//carFlag = "标的车";
				%>
				<s:set var="carFlag" value="%{getText('certainLoss.thirdCarLoss.car')}" scope="page" />
				<%
					}
						if (!"D".equals(strRiskTypeNow)) {
							//carFlag = "";
				%>
				<s:set var="carFlag" value="''" scope="page" />
				<%
					}
						nowNodeStatus = swfLog.getNodeStatus();
						if (nodeStatus.equals("0") || nodeStatus.equals("-1") || nodeStatus.equals("99")) { //判断当前的任務是哪种类型的，根据任務的节点的状态
				%>
				<td align="center">
					<c:choose>
						<c:when test="${swfLogList1.nodeStatus=='3'}">
							<c:if test="${param.status!='-1'}">
								<%
									EditLastType = "EDIT";
								%>
							</c:if>
							<s:text name="schedule.returnDeal" />
							<%--回退处理 --%>
						</c:when>
						<c:when test="${swfLogList1.nodeStatus=='4'}">
							<s:text name="common.status.submited" />
							<%--已提交 --%>
						</c:when>
						<c:when test="${swfLogList1.nodeStatus=='5'}">
							<s:text name="workflow.notPassReturn" />
							<%--不通过退回 --%>
						</c:when>
						<c:when test="${swfLogList1.nodeStatus=='2'}">
							<s:text name="check.dealingWith" />
						</c:when>
						<%--正在处理 --%>
						<c:when test="${swfLogList1.nodeStatus=='0'}">
							<c:choose>
								<c:when test="${param.nodeType=='compe'}">
									<c:choose>
										<c:when test="${swfLogList1.compeFlag=='1'}">
											<s:text name="workflow.undwrt.notPass" />
										</c:when>
										<%--核赔未通过 --%>
										<c:when test="${swfLogList1.compeFlag=='2'}">
											<s:text name="button.NuclearThrough.value" />
										</c:when>
										<%--核赔通过 --%>
										<c:when test="${swfLogList1.compeFlag=='0'}">
											<s:text name="workflow.compensate.notComp" />
										</c:when>
										<%--未出计算书--%>
										<c:otherwise></c:otherwise>
									</c:choose>
								</c:when>
								<c:otherwise>
									<s:text name="guarantee.newDeal" />
								</c:otherwise>
								<%--新处理 --%>
							</c:choose>
							<c:if test="${swfLogList1.nodeType=='cance'}">
								<%
									EditLastType = "CANCELEDIT";
								%>
							</c:if>
						</c:when>
						<c:otherwise></c:otherwise>
					</c:choose>
				</td>
				<%
					}
						// 把工作流号码等等也需要 加载到link中 加入传进保单号码的过程
						flowStr = "&swfLogFlowID=" + swfLog.getId().getFlowID() + "&swfLogLogNo=" + swfLog.getId().getLogNo() + "&status=" + swfLog.getNodeStatus() + "&riskCode=" + swfLog.getRiskCode() + "&editType=" + EditLastType + "&nodeType="
								+ swfLog.getNodeType() + "&businessNo=" + swfLog.getBusinessNo() + "&keyIn=" + swfLog.getKeyIn() + "&policyNo=" + swfLog.getPolicyNo() + "&modelNo=" + swfLog.getModelNo() + "&nodeNo=" + swfLog.getNodeNo() + "&dfFlag="
								+ swfLog.getDfFlag() + "&actorId=" + swfLog.getActorId() + "&processId=" + swfLog.getProcessId();
						//如果是车险的，定损和查勘增加一个简易赔案的功能
						if (("D".equals(codeService.translateRiskCodetoRiskType(swfLog.getRiskCode()))) && (swfLog.getNodeType().equals("check") || swfLog.getNodeType().equals("certa"))
								&& (swfLog.getNodeStatus().equals("0") || swfLog.getNodeStatus().equals("2"))) {
							quickCaseHref = "/claim/quickCaseBeforeEdit.do?registNo=" + swfLog.getRegistNo() + "&riskCode=" + swfLog.getRiskCode() + "&quickCaseStatus=01" + "&policyNo=" + swfLog.getPolicyNo() + "&editType=ADD" + "&nodeType="
									+ swfLog.getNodeType() + "&swfLogFlowID=" + swfLog.getId().getFlowID() + "&swfLogLogNo=" + swfLog.getId().getLogNo();
						}
						//以下是根据节点名称的不同，在显示第一列的超链的内容不同。（一般是业务号码）
						// (nodeStatus.equals("0")&&nowNodeStatus.equals("0")是表示待处理任務中的新任務
						// nodeStatus.equals("-1") 表示的是按下注销拒赔申请功能選單後的任務显示。
				%>
				<td align="center">
					<c:choose>
						<c:when test="${swfLogList1.nodeType=='prepa'}">
							<%
								if ((nodeStatus.equals("0") && nowNodeStatus.equals("0"))) {
												dealHref = "/claim/claimFinishQueryList.do?prpLclaimClaimNo=" + swfLog.getKeyIn() + flowStr;
											} else if (nodeStatus.equals("-1")) { //注销拒赔申请
												if (nodeStatus.equals("-1") && funcName != null && funcName.equals("specialApply")) {
													dealHref = "/claim/specailCaseQuery.do?" + flowStr;
												} else {
													dealHref = "/claim/claimBeforeCancel.do?ClaimNo=" + swfLog.getKeyIn() + flowStr;
												}
											} else if (nowNodeStatus.equals("3")) {//区分是退回的方式
												dealHref = "/claim/prepayFinishQueryList.do?prpLprepayPrepayNo=" + swfLog.getKeyIn() + flowStr;
											} else {
												dealHref = "/claim/prepayFinishQueryList.do?prpLprepayPrepayNo=" + swfLog.getKeyIn() + flowStr;
											}
											dispHref = swfLog.getKeyIn();
							%>
							<input name=keyIN type="hidden" value="<c:out value='${swfLogList1.businessNo}'/>">
						</c:when>
						<c:when test="${swfLogList1.nodeType=='regis'}">
							<%
								if (nodeStatus.equals("0")) {
												dealHref = "/claim/registBeforeEdit.do?prpCmainPolicyNo=" + swfLog.getBusinessNo() + flowStr;
											} else {
												dealHref = "/claim/registFinishQueryList.do?prpLregistRegistNo=" + swfLog.getBusinessNo() + flowStr + "&updateExt=true";
											}
											dispHref = swfLog.getBusinessNo();
							%>
						</c:when>
						<c:when test="${swfLogList1.nodeType=='veric'}">
							<%
								//暂时用来做核赔通过的功能
											if (nodeStatus.equals("99")) {
												if (nowNodeStatus.equals("0") || nowNodeStatus.equals("5")) {
													//businessNo为归档号码
													dealHref = "/claim/compensate/compensateFinishQueryList.do?prpLcompensateCompensateNo=" + swfLog.getBusinessNo() + flowStr;
													dispHref = swfLog.getBusinessNo();
												} else {//keyOut为归档号码
													dealHref = "/claim/compensate/compensateFinishQueryList.do?prpLcompensateCompensateNo=" + swfLog.getKeyOut() + flowStr;
													dispHref = swfLog.getKeyOut();
												}
											} else {
												dealHref = "/claim/compensate/compensateApprove.do?prpLcompensateCompensateNo=" + swfLog.getBusinessNo() + "&keyString=" + swfLog.getKeyIn() + flowStr;
												dispHref = swfLog.getBusinessNo();
											}
							%>
						</c:when>
						<c:when test="${swfLogList1.nodeType=='compe'}">
							<%
								riskCode = swfLog.getRiskCode();
											//如果是车险的，可以增加一个回退定损的功能
											if ("D".equals(codeService.translateRiskCodetoRiskType(swfLog.getRiskCode()))) {
												backHref = "/claim/compensate/compensateBackBeforeQuery.do?claimNo=" + swfLog.getBusinessNo() + "&registNo=" + swfLog.getRegistNo() + flowStr;
											}
											if ("D".equals(strRiskTypeNow)) {
												String flowStr1 = "&swfLogFlowID=" + swfLog.getId().getFlowID() + "&swfLogLogNo=" + swfLog.getId().getLogNo() + "&status=" + swfLog.getNodeStatus() + "&riskCode=" + swfLog.getRiskCode() + "&editType=" + EditLastType
														+ "&nodeType=" + swfLog.getNodeType() + "&businessNo=" + swfLog.getBusinessNo() + "&policyNo=" + swfLog.getPolicyNo() + "&modelNo=" + swfLog.getModelNo() + "&nodeNo=" + swfLog.getNodeNo() + "&actorId="
														+ swfLog.getActorId() + "&processId=" + swfLog.getProcessId();
												dealHref = "/claim/compensate/compensateBeforeEdit.do?ClaimNo=" + swfLog.getKeyIn() + flowStr1 + "&compeCount=" + swfLog.getCompeCount() + "&caseType=" + swfLog.getTypeFlag();
											} else {
												dealHref = "/claim/compensate/compensateBeforeEdit.do?ClaimNo=" + swfLog.getKeyIn() + "&caseType=" + swfLog.getTypeFlag() + flowStr + "&compeCount=" + swfLog.getCompeCount();
											}
											if (nodeStatus.equals("-1")) {
												dealHref = "/claim/claimBeforeCancel.do?ClaimNo=" + swfLog.getKeyIn() + flowStr + "&regsitNo=" + swfLog.getRegistNo();
											}
											dispHref = swfLog.getKeyIn();
							%>
						</c:when>
						<c:when test="${swfLogList1.nodeType=='compp'}">
							<%
								dealHref = "/claim/compensate/compensateFinishQueryList.do?prpLcompensateCompensateNo=" + swfLog.getBusinessNo() + flowStr;
											dispHref = swfLog.getBusinessNo();
							%>
						</c:when>
						<c:when test="${swfLogList1.nodeType=='speci'}">
							<%
								if (nowNodeStatus.equals("0")) {
												if (typeFlag.equals("3") || typeFlag.equals("4") || typeFlag.equals("6")) {
													//特殊赔案中的通融和预付车险和非车险不同
													if ("D".equals(strRiskTypeNow)) { //add liuyanmei
														dealHref = "/claim/compensate/compensateBeforeEditList.do?ClaimNo=" + swfLog.getKeyIn() + "&caseType=" + swfLog.getTypeFlag() + "&ClaimNoSign= =" + flowStr;
													} else {
														dealHref = "/claim/compensate/compensateBeforeEdit.do?ClaimNo=" + swfLog.getKeyIn() + "&caseType=" + swfLog.getTypeFlag() + "&ClaimNoSign= =" + flowStr;
													}
												}
												if (typeFlag.equals("7") || typeFlag.equals("8") || typeFlag.equals("5")) {
													dealHref = "/claim/prepayBeforeEdit.do?ClaimNo=" + swfLog.getKeyIn() + "&caseType=" + swfLog.getTypeFlag() + flowStr;
												}
												dispHref = swfLog.getKeyIn();
											} else {
												if (typeFlag.equals("3") || typeFlag.equals("4") || typeFlag.equals("6")) {
													dealHref = "/claim/compensate/compensateFinishQueryList.do?ClaimNo=" + swfLog.getKeyIn() + "&prpLcompensateCompensateNo=" + swfLog.getKeyOut() + "&caseType=" + typeFlag + flowStr;
												}
												if (typeFlag.equals("7") || typeFlag.equals("8") || typeFlag.equals("5")) {
													dealHref = "/claim/prepayFinishQueryList.do?ClaimNo=" + swfLog.getKeyIn() + "&prpLprepayPrepayNo=" + swfLog.getKeyOut() + "&caseType=" + typeFlag + flowStr;
												}
												dispHref = swfLog.getKeyOut();
											}
							%>
						</c:when>
						<c:when test="${swfLogList1.nodeType=='claim'}">
							<%
								if ((nodeStatus.equals("0") && nowNodeStatus.equals("0"))) {
												dealHref = "/claim/claimBeforeEdit.do?RegistNo=" + swfLog.getKeyIn() + flowStr;
												dispHref = swfLog.getKeyIn();
											} else if (nodeStatus.equals("-1")) { //注销拒赔申请
												if (!nowNodeStatus.equals("0")) {
													if (nodeStatus.equals("-1") && funcName != null && funcName.equals("specialApply")) {
														dealHref = "/claim/specailCaseQuery.do?" + flowStr;
													} else {
														dealHref = "/claim/claimBeforeCancel.do?ClaimNo=" + swfLog.getKeyOut() + flowStr;
													}
													dispHref = swfLog.getBusinessNo();
												} else {
													if (nodeStatus.equals("-1") && funcName != null && funcName.equals("specialApply")) {
														dealHref = "/claim/specailCaseQuery.do?" + flowStr;
													} else {
														dealHref = "/claim/claimBeforeCancel.do?RegistNo=" + swfLog.getBusinessNo() + "&type=" + request.getAttribute("com_sinosoft_type") + flowStr;
													}
													dispHref = swfLog.getBusinessNo();
												}
											} else {
												if (funcName != null && funcName.equals("addLoss")) {
													String Lossstr = "";
													Lossstr = "&swfLogFlowID=" + swfLog.getId().getFlowID() + "&swfLogLogNo=" + swfLog.getId().getLogNo() + "&status=" + swfLog.getNodeStatus() + "&riskCode=" + swfLog.getRiskCode() + "&editType=" + "LOSS" + "&nodeType="
															+ swfLog.getNodeType() + "&businessNo=" + swfLog.getBusinessNo() + "&keyIn=" + swfLog.getKeyIn() + "&policyNo=" + swfLog.getPolicyNo() + "&modelNo=" + swfLog.getModelNo() + "&nodeNo=" + swfLog.getNodeNo();
													dealHref = "/claim/claimFinishQueryList.do?prpLclaimClaimNo=" + swfLog.getKeyOut() + Lossstr;
												} else {
													dealHref = "/claim/claimFinishQueryList.do?prpLclaimClaimNo=" + swfLog.getKeyOut() + flowStr;
												}
												dispHref = swfLog.getKeyOut();
											}
							%>
						</c:when>
						<c:when test="${swfLogList1.nodeType=='check'}">
							<%
								if ((nodeStatus.equals("0") && nowNodeStatus.equals("0"))) {
												dealHref = "/claim/check/checkBeforeEdit.do?RegistNo=" + swfLog.getKeyIn() + "&lossItemName=" + swfLog.getLossItemName() + "&lossItemCode=" + DataUtils.dbNullToEmpty(swfLog.getLossItemCode()) + "&insureCarFlag="
														+ swfLog.getInsureCarFlag() + flowStr;
												dispHref = swfLog.getKeyIn();
											} else if (nodeStatus.equals("-1")) { //注销拒赔申请
												if (nodeStatus.equals("-1") && funcName != null && funcName.equals("specialApply")) {
													dealHref = "/claim/specailCaseQuery.do?" + flowStr;
												} else {
													dealHref = "/claim/claimBeforeCancel.do?RegistNo=" + swfLog.getKeyIn() + "&type=" + request.getAttribute("com_sinosoft_type") + flowStr;
												}
												dispHref = swfLog.getKeyIn();
											} else {
												dealHref = "/claim/check/checkFinishQueryList.do?prpLcheckCheckNo=" + swfLog.getKeyIn() + "&type=" + request.getAttribute("com_sinosoft_type") + "&lossItemCode=" + DataUtils.dbNullToEmpty(swfLog.getLossItemCode())
														+ "&lossItemName=" + swfLog.getLossItemName() + "&insureCarFlag=" + swfLog.getInsureCarFlag() + flowStr;
												dispHref = swfLog.getKeyIn();
											}
							%>
						</c:when>
						<c:when test="${swfLogList1.nodeType=='endca'}">
							<%
								dealHref = "/claim/endcase/endcaseBeforeEdit.do?ClaimNo=" + swfLog.getKeyIn() + "&ClaimNoSign= =" + "&caseType=" + swfLog.getTypeFlag() + flowStr;
											dispHref = swfLog.getKeyIn();
							%>
						</c:when>
						<c:when test="${swfLogList1.nodeType=='certi'}">
							<%
								if ((nodeStatus.equals("0") && nowNodeStatus.equals("0"))) {
												dealHref = "/claim/certifyBeforeEdit.do?RegistNo=" + swfLog.getKeyIn() + flowStr;
											} else if (nodeStatus.equals("-1")) { //注销拒赔申请
												if (nodeStatus.equals("-1") && funcName != null && funcName.equals("specialApply")) {
													dealHref = "/claim/specailCaseQuery.do?" + flowStr;
												} else {
													dealHref = "/claim/claimBeforeCancel.do?RegistNo=" + swfLog.getKeyIn() + "&type=" + request.getAttribute("com_sinosoft_type") + flowStr;
												}
											} else {
												dealHref = "/claim/certifyFinishQueryList.do?prpLcertifyCertifyNo=" + swfLog.getKeyIn() + flowStr;
											}
											dispHref = swfLog.getKeyIn();
							%>
						</c:when>
						<c:when test="${swfLogList1.nodeType=='certa'}">
							<%
								if ((nodeStatus.equals("0") && nowNodeStatus.equals("0"))) {
												dealHref = "/claim/certainLoss/certainLossBeforeEdit.do?RegistNo=" + swfLog.getKeyIn() + "&lossTypeFlag=" + swfLog.getTypeFlag() + "&insureCarFlag=" + swfLog.getInsureCarFlag() + "&lossItemCode="
														+ DataUtils.dbNullToEmpty(swfLog.getLossItemCode()) + "&lossItemName=" + swfLog.getLossItemName() + flowStr;
											} else if (nodeStatus.equals("-1")) { //注销拒赔申请
												if (nodeStatus.equals("-1") && funcName != null && funcName.equals("specialApply")) {
													dealHref = "/claim/specailCaseQuery.do?" + flowStr;
												} else {
													dealHref = "/claim/claimBeforeCancel.do?RegistNo=" + swfLog.getKeyIn() + "&type=" + request.getAttribute("com_sinosoft_type") + flowStr;
												}

											} else if (nowNodeStatus.equals("3")) {//区分是退回的方式
												dealHref = "/claim/certainLoss/certainLossFinishQueryList.do?prpLverifyLossRegistNo=" + swfLog.getKeyIn() + "&lossTypeFlag=" + swfLog.getTypeFlag() + "&insureCarFlag=" + swfLog.getInsureCarFlag() + "&lossItemCode="
														+ DataUtils.dbNullToEmpty(swfLog.getLossItemCode()) + "&lossItemName=" + swfLog.getLossItemName() + "&flag=1" + flowStr;
											} else {
												dealHref = "/claim/certainLoss/certainLossFinishQueryList.do?prpLverifyLossRegistNo=" + swfLog.getKeyIn() + "&lossTypeFlag=" + swfLog.getTypeFlag() + "&insureCarFlag=" + swfLog.getInsureCarFlag() + "&lossItemCode="
														+ DataUtils.dbNullToEmpty(swfLog.getLossItemCode()) + "&lossItemName=" + swfLog.getLossItemName() + flowStr;
											}
											dispHref = swfLog.getKeyIn();
							%>
						</c:when>
						<c:when test="${swfLogList1.nodeType=='verip'}">
							<%
								if ((nodeStatus.equals("0") && nowNodeStatus.equals("0"))) {
												dealHref = "/claim/verifyPriceFinishQueryList.do?RegistNo=" + swfLog.getKeyIn() + "&lossTypeFlag=" + swfLog.getTypeFlag() + "&insureCarFlag=" + swfLog.getInsureCarFlag() + "&lossItemCode="
														+ DataUtils.dbNullToEmpty(swfLog.getLossItemCode()) + "&lossItemName=" + swfLog.getLossItemName() + flowStr;
											} else if (nodeStatus.equals("-1")) { //注销拒赔申请
												if (nodeStatus.equals("-1") && funcName != null && funcName.equals("specialApply")) {
													dealHref = "/claim/specailCaseQuery.do?" + flowStr;
												} else {
													dealHref = "/claim/claimBeforeCancel.do?RegistNo=" + swfLog.getKeyIn() + "&type=" + request.getAttribute("com_sinosoft_type") + flowStr;
												}
											} else {
												dealHref = "/claim/verifyPriceFinishQueryList.do?prpLverifyLossRegistNo=" + swfLog.getKeyIn() + "&lossTypeFlag=" + swfLog.getTypeFlag() + "&insureCarFlag=" + swfLog.getInsureCarFlag() + "&lossItemCode="
														+ DataUtils.dbNullToEmpty(swfLog.getLossItemCode()) + "&lossItemName=" + swfLog.getLossItemName() + "&handleDept=" + swfLog.getHandleDept() + "&deptName=" + swfLog.getDeptName() + flowStr;
											}
											dispHref = swfLog.getKeyIn();
							%>
						</c:when>
						<c:when test="${swfLogList1.nodeType=='verpo'}">
							<%
								if ((nodeStatus.equals("0") && nowNodeStatus.equals("0"))) {
												dealHref = "/claim/verifyPriceFinishQueryList.do?RegistNo=" + swfLog.getKeyIn() + "&lossTypeFlag=" + swfLog.getTypeFlag() + "&insureCarFlag=" + swfLog.getInsureCarFlag() + "&lossItemCode="
														+ DataUtils.dbNullToEmpty(swfLog.getLossItemCode()) + "&lossItemName=" + swfLog.getLossItemName() + flowStr;
											} else if (nodeStatus.equals("-1")) { //注销拒赔申请
												if (nodeStatus.equals("-1") && funcName != null && funcName.equals("specialApply")) {
													dealHref = "/claim/specailCaseQuery.do?" + flowStr;
												} else {
													dealHref = "/claim/claimBeforeCancel.do?RegistNo=" + swfLog.getKeyIn() + "&type=" + request.getAttribute("com_sinosoft_type") + flowStr;
												}
											} else {
												dealHref = "/claim/verifyPriceFinishQueryList.do?prpLverifyLossRegistNo=" + swfLog.getKeyIn() + "&lossTypeFlag=" + swfLog.getTypeFlag() + "&insureCarFlag=" + swfLog.getInsureCarFlag() + "&lossItemCode="
														+ DataUtils.dbNullToEmpty(swfLog.getLossItemCode()) + "&lossItemName=" + swfLog.getLossItemName() + flowStr;
											}
											dispHref = swfLog.getKeyIn();
							%>
						</c:when>
						<c:when test="${swfLogList1.nodeType=='verif'}">
							<%
								if ((nodeStatus.equals("0") && nowNodeStatus.equals("0"))) {
												dealHref = "/claim/verifyLoss/verifyLossFinishQueryList.do?RegistNo=" + swfLog.getKeyIn() + "&lossTypeFlag=" + swfLog.getTypeFlag() + "&insureCarFlag=" + swfLog.getInsureCarFlag() + "&lossItemCode="
														+ DataUtils.dbNullToEmpty(swfLog.getLossItemCode()) + "&lossItemName=" + swfLog.getLossItemName() + flowStr;
											} else if (nodeStatus.equals("-1")) { //注销拒赔申请
												if (nodeStatus.equals("-1") && funcName != null && funcName.equals("specialApply")) {
													dealHref = "/claim/specailCaseQuery.do?" + flowStr;
												} else {
													dealHref = "/claim/claimBeforeCancel.do?RegistNo=" + swfLog.getKeyIn() + "&type=" + request.getAttribute("com_sinosoft_type") + flowStr;
												}
											} else {
												dealHref = "/claim/verifyLoss/verifyLossFinishQueryList.do?prpLverifyLossRegistNo=" + swfLog.getKeyIn() + "&lossTypeFlag=" + swfLog.getTypeFlag() + "&insureCarFlag=" + swfLog.getInsureCarFlag() + "&lossItemCode="
														+ DataUtils.dbNullToEmpty(swfLog.getLossItemCode()) + "&lossItemName=" + swfLog.getLossItemName() + flowStr;
											}
											dispHref = swfLog.getKeyIn();
							%>
						</c:when>
						<c:when test="${swfLogList1.nodeType=='veriw'}">
							<%
								if ((nodeStatus.equals("0") && nowNodeStatus.equals("0"))) {
												dealHref = "/claim/verifyLoss/verifyLossFinishQueryList.do?RegistNo=" + swfLog.getKeyIn() + "&lossTypeFlag=" + swfLog.getTypeFlag() + "&insureCarFlag=" + swfLog.getInsureCarFlag() + "&lossItemCode="
														+ DataUtils.dbNullToEmpty(swfLog.getLossItemCode()) + "&lossItemName=" + swfLog.getLossItemName() + flowStr;
											} else if (nodeStatus.equals("-1")) { //注销拒赔申请
												if (nodeStatus.equals("-1") && funcName != null && funcName.equals("specialApply")) {
													dealHref = "/claim/specailCaseQuery.do?" + flowStr;
												} else {
													dealHref = "/claim/claimBeforeCancel.do?RegistNo=" + swfLog.getKeyIn() + "&type=" + request.getAttribute("com_sinosoft_type") + flowStr;
												}

											} else {
												dealHref = "/claim/verifyLoss/verifyLossFinishQueryList.do?prpLverifyLossRegistNo=" + swfLog.getKeyIn() + "&lossTypeFlag=" + swfLog.getTypeFlag() + "&insureCarFlag=" + swfLog.getInsureCarFlag() + "&lossItemCode="
														+ DataUtils.dbNullToEmpty(swfLog.getLossItemCode()) + "&lossItemName=" + swfLog.getLossItemName() + "&flag=1" + flowStr;
											}
											dispHref = swfLog.getKeyIn();
							%>
						</c:when>
						<c:when test="${swfLogList1.nodeType=='sched'}">
							<%
								if (nodeStatus.equals("0") && (nowNodeStatus.equals("0") || nowNodeStatus.equals("3"))) {
												dealHref = "/claim/registFinishQueryList.do?prpLregistRegistNo=" + swfLog.getKeyIn() + flowStr;
												dealHref = "/claim/schedule/scheduleBeforeEdit.do?prpLscheduleMainWFRegistNo=" + swfLog.getKeyIn() + "&prpLscheduleMainWFSurveyNo=0" + "&scheduleType=sched" + "&handleDept=" + swfLog.getHandleDept() + "&endflag="
														+ swfLog.getEndFlag() + "&commiFlag=0" + flowStr;
												dispHref = swfLog.getKeyIn();
											} else if (nodeStatus.equals("-1")) { //注销拒赔申请
												if (nodeStatus.equals("-1") && funcName != null && funcName.equals("specialApply")) {
													dealHref = "/claim/specailCaseQuery.do?" + flowStr;
												} else {
													dealHref = "/claim/claimBeforeCancel.do?RegistNo=" + swfLog.getBusinessNo() + flowStr;
												}
												dispHref = swfLog.getBusinessNo();
											} else {
												dealHref = "/claim/schedule/scheduleFinishQueryList.do?prpLscheduleMainWFRegistNo=" + swfLog.getBusinessNo() + "&prpLscheduleMainWFScheduleID=1" + "&scheduleType=sched" + "&handleDept=" + swfLog.getHandleDept()
														+ "&endflag=" + swfLog.getEndFlag() + "&commiFlag=0" + "&handlerCode=" + swfLog.getHandlerCode() + flowStr;
												dispHref = swfLog.getBusinessNo();
											}
							%>
						</c:when>
						<c:when test="${swfLogList1.nodeType=='wound'}">
							<%
								if ((nodeStatus.equals("0") && nowNodeStatus.equals("0"))) {
												dealHref = "/claim/certainLoss/certainLossBeforeEdit.do?RegistNo=" + swfLog.getKeyIn() + "&lossTypeFlag=0" + "&insureCarFlag=" + DataUtils.dbNullToEmpty(swfLog.getInsureCarFlag()) + "&lossItemCode="
														+ DataUtils.dbNullToEmpty(swfLog.getLossItemCode()) + "&lossItemName=" + swfLog.getLossItemName() + flowStr;
											} else if (nodeStatus.equals("-1")) { //注销拒赔申请
												if (nodeStatus.equals("-1") && funcName != null && funcName.equals("specialApply")) {
													dealHref = "/claim/specailCaseQuery.do?" + flowStr;
												} else {
													dealHref = "/claim/claimBeforeCancel.do?RegistNo=" + swfLog.getKeyIn() + "&type=" + request.getAttribute("com_sinosoft_type") + flowStr;
												}
											} else if (nowNodeStatus.equals("3")) {//区分是退回的方式
												dealHref = "/claim/certainLoss/certainLossBeforeEdit.do?RegistNo=" + swfLog.getKeyIn() + "&lossTypeFlag=0" + "&insureCarFlag=" + DataUtils.dbNullToEmpty(swfLog.getInsureCarFlag()) + "&lossItemCode="
														+ DataUtils.dbNullToEmpty(swfLog.getLossItemCode()) + "&lossItemName=" + swfLog.getLossItemName() + "&flag=1" + flowStr;
											} else {
												dealHref = "/claim/certainLoss/certainLossFinishQueryList.do?prpLverifyLossRegistNo=" + swfLog.getKeyIn() + "&lossTypeFlag=0" + "&insureCarFlag=" + swfLog.getInsureCarFlag() + "&lossItemCode="
														+ DataUtils.dbNullToEmpty(swfLog.getLossItemCode()) + "&lossItemName=" + swfLog.getLossItemName() + flowStr;
											}
											dispHref = swfLog.getKeyIn();
							%>
						</c:when>
						<c:when test="${swfLogList1.nodeType=='cance'}">
							<%
								if (nodeStatus.equals("0")) {
												dealHref = "/claim/claimBeforeCancel.do?ClaimNo=" + swfLog.getKeyIn() + flowStr + "&typeFlag=" + swfLog.getTypeFlag() + "&flowInTime=" + swfLog.getFlowInTime();
											} else {
												dealHref = "/claim/claimFinishQueryList.do?prpLclaimClaimNo=" + swfLog.getKeyIn() + flowStr;
											}
											dispHref = swfLog.getKeyIn();
							%>
						</c:when>
						<c:when test="${swfLogList1.nodeType=='propc'}">
							<%
								if ((nodeStatus.equals("0") && nowNodeStatus.equals("0"))) {
												dealHref = "/claim/certainLoss/certainLossBeforeEdit.do?RegistNo=" + swfLog.getKeyIn() + "&lossTypeFlag=0" + "&insureCarFlag=" + DataUtils.dbNullToEmpty(swfLog.getInsureCarFlag()) + "&lossItemCode="
														+ DataUtils.dbNullToEmpty(swfLog.getLossItemCode()) + "&lossItemName=" + swfLog.getLossItemName() + flowStr;
											} else if (nodeStatus.equals("-1")) { //注销拒赔申请
												if (nodeStatus.equals("-1") && funcName != null && funcName.equals("specialApply")) {
													dealHref = "/claim/specailCaseQuery.do?" + flowStr;
												} else {
													dealHref = "/claim/claimBeforeCancel.do?RegistNo=" + swfLog.getKeyIn() + "&type=" + request.getAttribute("com_sinosoft_type") + flowStr;
												}
											} else if (nowNodeStatus.equals("3")) { //区分是退回的方式
												dealHref = "/claim/certainLoss/certainLossBeforeEdit.do?RegistNo=" + swfLog.getKeyIn() + "&lossTypeFlag=0" + "&insureCarFlag=" + DataUtils.dbNullToEmpty(swfLog.getInsureCarFlag()) + "&lossItemCode="
														+ DataUtils.dbNullToEmpty(swfLog.getLossItemCode()) + "&lossItemName=" + swfLog.getLossItemName() + "&flag=1" + flowStr;
											} else {
												dealHref = "/claim/certainLoss/certainLossFinishQueryList.do?prpLverifyLossRegistNo=" + swfLog.getKeyIn() + "&lossTypeFlag=0" + "&insureCarFlag=" + DataUtils.dbNullToEmpty(swfLog.getInsureCarFlag()) + "&lossItemCode="
														+ DataUtils.dbNullToEmpty(swfLog.getLossItemCode()) + "&lossItemName=" + swfLog.getLossItemName() + flowStr;
											}
											dispHref = swfLog.getKeyIn();
							%>
						</c:when>
						<c:when test="${swfLogList1.nodeType=='propv'}">
							<%
								if ((nodeStatus.equals("0") && nowNodeStatus.equals("0"))) {
												dealHref = "/claim/verifyLoss/verifyLossFinishQueryList.do?RegistNo=" + swfLog.getKeyIn() + "&lossTypeFlag=" + swfLog.getTypeFlag() + "&insureCarFlag=" + DataUtils.dbNullToEmpty(swfLog.getInsureCarFlag())
														+ "&lossItemCode=" + DataUtils.dbNullToEmpty(swfLog.getLossItemCode()) + "&lossItemName=" + swfLog.getLossItemName() + flowStr;
											} else if (nodeStatus.equals("-1")) { //注销拒赔申请
												if (nodeStatus.equals("-1") && funcName != null && funcName.equals("specialApply")) {
													dealHref = "/claim/specailCaseQuery.do?" + flowStr;
												} else {
													dealHref = "/claim/claimBeforeCancel.do?RegistNo=" + swfLog.getKeyIn() + "&type=" + request.getAttribute("com_sinosoft_type") + flowStr;
												}
											} else {
												dealHref = "/claim/verifyLoss/verifyLossFinishQueryList.do?prpLverifyLossRegistNo=" + swfLog.getKeyIn() + "&lossTypeFlag=" + swfLog.getTypeFlag() + "&insureCarFlag=" + DataUtils.dbNullToEmpty(swfLog.getInsureCarFlag())
														+ "&lossItemCode=" + DataUtils.dbNullToEmpty(swfLog.getLossItemCode()) + "&lossItemName=" + swfLog.getLossItemName() + "&flag=1" + flowStr;
											}
											dispHref = swfLog.getKeyIn();
							%>
						</c:when>
						<c:when test="${swfLogList1.nodeType=='backc'}">
							<%
								if ((nodeStatus.equals("0") && nowNodeStatus.equals("0"))) {
												dealHref = "/claim/verifyLoss/verifyLossFinishQueryList.do?RegistNo=" + swfLog.getKeyIn() + "&lossTypeFlag=" + swfLog.getTypeFlag() + "&insureCarFlag=" + DataUtils.dbNullToEmpty(swfLog.getInsureCarFlag())
														+ "&lossItemCode=" + DataUtils.dbNullToEmpty(swfLog.getLossItemCode()) + "&lossItemName=" + swfLog.getLossItemName() + flowStr;
											} else if (nodeStatus.equals("-1")) { //注销拒赔申请
												if (nodeStatus.equals("-1") && funcName != null && funcName.equals("specialApply")) {
													dealHref = "/claim/specailCaseQuery.do?" + flowStr;
												} else {
													dealHref = "/claim/claimBeforeCancel.do?RegistNo=" + swfLog.getKeyIn() + "&type=" + request.getAttribute("com_sinosoft_type") + flowStr;
												}
											} else {
												dealHref = "/claim/verifyLoss/verifyLossFinishQueryList.do?prpLverifyLossRegistNo=" + swfLog.getKeyIn() + "&lossTypeFlag=" + swfLog.getTypeFlag() + "&insureCarFlag=" + DataUtils.dbNullToEmpty(swfLog.getInsureCarFlag())
														+ "&lossItemCode=" + DataUtils.dbNullToEmpty(swfLog.getLossItemCode()) + "&lossItemName=" + swfLog.getLossItemName() + "&flag=1" + flowStr;
											}
											dispHref = swfLog.getKeyIn();
							%>
						</c:when>
						<c:when test="${swfLogList1.nodeType=='backv'}">
							<%
								if ((nodeStatus.equals("0") && nowNodeStatus.equals("0"))) {
												dealHref = "/claim/backVisitBeforeEdit.do?RegistNo=" + swfLog.getKeyIn() + "&lossTypeFlag=" + swfLog.getTypeFlag() + "&insureCarFlag=" + DataUtils.dbNullToEmpty(swfLog.getInsureCarFlag()) + "&lossItemCode="
														+ DataUtils.dbNullToEmpty(swfLog.getLossItemCode()) + "&lossItemName=" + swfLog.getLossItemName() + flowStr;
											} else {
												dealHref = "/claim/backVisitBeforeEdit.do?RegistNo=" + swfLog.getKeyIn() + "&lossTypeFlag=" + swfLog.getTypeFlag() + "&insureCarFlag=" + DataUtils.dbNullToEmpty(swfLog.getInsureCarFlag()) + "&lossItemCode="
														+ DataUtils.dbNullToEmpty(swfLog.getLossItemCode()) + "&lossItemName=" + swfLog.getLossItemName() + "&flag=1" + flowStr;
											}
											dispHref = swfLog.getKeyIn();
							%>
						</c:when>
						<c:otherwise></c:otherwise>
					</c:choose>
					<c:choose>
						<%--value=1 表示这个保单已经注销，不得立案--%>
						<c:when test="${swfLogList1.otherFlag=='1'}">
							<c:choose>
								<c:when test="${swfLogList1.dfFlag=='Y'}">
									<a href="<%=dealHref%>" title="<c:out value='${swfLogList1.titleStr}'/>" onclick="checkDfFlag();return otherFlag('<c:out value='${swfLogList1.otherFlag}'/>');"><%=dispHref%></a>
								</c:when>
								<c:otherwise>
									<a href="<%=dealHref%>" title="<c:out value='${swfLogList1.titleStr}'/>" onclick="return otherFlag('<c:out value='${swfLogList1.otherFlag}'/>');"><%=dispHref%></a>
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
							<a href="<%=dealHref%>" title="<c:out value='${swfLogList1.titleStr}'/>" onclick="return compeCount('<c:out value='${swfLogList1.compeCount}'/>');"><%=dispHref%></a>
						</c:otherwise>
					</c:choose>
				</td>
				<td align="center">
					<%--
						//保单号码的内容
					--%>
					<c:forEach items="${swfLogList1.relatePolicyList}" var="relatePolicy">
						<c:out value="${relatePolicy.id.policyNo}" />
					</c:forEach>
				</td>
				<td align="center">
					<c:out value="${swfLogList1.riskCodeName}" />
				</td>
				<td align="center" width="10%">
					<c:out value="${swfLogList1.insuredName}" />
				</td>
				<%--
					//如果是定损核损，列出类型中的内容
				--%>
				<c:choose>
					<c:when test="${swfLog.nodeType=='certa'}">
						<td align="center">
							<c:if test="${swfLogList1.exigenceGree=='0'}">
								<s:text name="workflow.quick" />
								<%--紧急 --%>
							</c:if>
							<c:if test="${swfLogList1.exigenceGree=='1'}">
								<s:text name="workflow.normal" />
								<%--一般 --%>
							</c:if>
						</td>
						<td align="center">
							<c:out value="${swfLogList1.lossItemName}" />
							<c:if test="${swfLogList1.insureCarFlag!='1'}">(<s:text name="certainLoss.thirdCarLoss.thirdCar" />)</c:if>
							<%--三者车 --%>
						</td>
						<td align="center">
							<s:property value="#attr.carFlag" />
						</td>
					</c:when>
					<c:when test="${swfLog.nodeType=='verip'||swfLog.nodeType=='verif'}">
						<td align="center">
							<c:out value="${swfLogList1.lossItemName}" />
						</td>
						<td align="center">
							<s:property value="#attr.carFlag" />
						</td>
					</c:when>
					<c:when test="${swfLog.nodeType=='compp'}">
						<%--
							//如果是计算书类型的，写立案号码
						--%>
						<td align="center">
							<a href="/claim/claimFinishQueryList.do?prpLclaimClaimNo=<c:out value='${swfLogList1.keyIn}'/><%=flowStr%>" title="<c:out value='${swfLogList1.titleStr}'/>"><c:out value='${swfLogList1.keyIn}' /></a>
						</td>
					</c:when>
					<c:when test="${swfLog.nodeType=='speci'}">
						<%--
							//如果是特殊案件类型的，写立案号码
						--%>
						<td align="center">
							<c:if test="${swfLogList1.nodeStatus=='0'}">
								<c:choose>
									<c:when test="${swfLogList1.typeFlag=='3'}">
										<s:text name="specialCase.Accommodation" />
										<%--通融--%>
									</c:when>
									<c:when test="${swfLogList1.typeFlag=='4'}">
										<s:text name="specialCase.Repay" />
										<%--预付--%>
									</c:when>
									<c:when test="${swfLogList1.typeFlag=='5'}">
										<s:text name="check.advance" />
										<%--预赔--%>
									</c:when>
									<c:when test="${swfLogList1.typeFlag=='6'}">
										<s:text name="check.other" />
										<%--其它--%>
									</c:when>
									<c:otherwise></c:otherwise>
								</c:choose>
							</c:if>
							<%
								if ((!swfLog.getNodeStatus().equals("0")) && (!swfLog.getNodeStatus().equals("3"))) {
							%>
							<a href="/claim/claimFinishQueryList.do?prpLclaimClaimNo=<c:out value='${swfLogList1.keyIn}'/><%=flowStr%>" title="<c:out value='${swfLogList1.titleStr}'/>"><c:out value='${swfLogList1.keyIn}' /></a>
							<%
								}
							%>
						</td>
					</c:when>
					<c:when test="${swfLog.nodeType=='veric'}">
						<%--
							//如果是核赔类型的，写立案号码
						--%>
						<td align="center">
							<a href="/claim/claimFinishQueryList.do?prpLclaimClaimNo=<c:out value='${swfLogList1.keyIn}'/><%=flowStr%>" title="<c:out value='${swfLogList1.titleStr}'/>"><c:out value='${swfLogList1.keyIn}' /></a>
						</td>
					</c:when>
					<c:when test="${swfLog.nodeType=='wound'}">
						<td align="center">
							<c:out value="${swfLogList1.lossItemName}" />
						</td>
					</c:when>
					<c:when test="${swfLog.nodeType=='veriw'}">
						<td align="center">
							<c:out value="${swfLogList1.lossItemName}" />
						</td>
					</c:when>
					<c:otherwise></c:otherwise>
				</c:choose>
				<%
					//如果是回访
						if (nodeType.equals("backv")) {
							String backvType = "查勘回访";//backVisit.surveyVisit
				%>
				<s:set var="backvType" value="%{getText('backVisit.surveyVisit')}" />
				<%
					if (swfLog.getTypeFlag().equals("2")) {
								backvType = "定损回访";//backVisit.feeBack
				%>
				<s:set var="backvType" value="%{getText('backVisit.feeBack')}" />
				<%
					}
				%>
				<td align="center">${backvType }</td>
				<%
					}
				%>
				<c:choose>
					<c:when test="${swfLog.nodeType=='check'}">
						<%--
							//如果是查勘类型的，写具体车牌号码
						--%>
						<c:if test="${com_sinosoft_type=='acci'}">
							<td align="center">
								<c:out value="${swfLogList1.lossItemName}" />
							</td>
						</c:if>
					</c:when>
					<c:when test="${swfLog.nodeType=='claim'}">
						<%--
							//如果是查勘类型的，写具体车牌号码，保单车辆，调度号，调度人
						--%>
						<%
							afterDay = 0; //计算时间的
										afterHour = 0;
										if (nodeStatus.equals("0")) {
											afterDay = DateTime.intervalDay(new DateTime(swfLog.getFlowInTime(), DateTime.YEAR_TO_DAY), swfLog.getTimeLimit(), new DateTime(DateTime.current(), DateTime.YEAR_TO_DAY), DateTime.current().getHour());
											//-1是因为如果不到一天，是按照一天计算的，因为我要计算小时，所以就应该记为0天的
											afterHour = (afterDay - 1) * 24 - swfLog.getTimeLimit() + DateTime.current().getHour();
											//剩余时间计算
											afterHour = claimLimit - afterHour;
						%>
						<td align="center"><%=afterHour%></td>
						<%
							}
						%>
					</c:when>
					<c:otherwise></c:otherwise>
				</c:choose>
				<%
					//如果等於定损调度或者查勘调度节点
						if (nodeType.equals("sched") || nodeType.equals("schel")) {
				%>
				<td align="center">
					<c:out value="${swfLogList1.lossItemName}" />
				</td>
				<%
					if (nodeStatus.equals("0")) {
				%>
				<td align="center">
					<c:out value="${swfLogList1.stopTimeDesc}" />
				</td>
				<%
					if ("10".equals(swfLog.getTypeFlag())) {
				%>
				<td align="center">
					<s:text name="regist.prpLregist.yes" />
					<%--是 --%>
				</td>
				<%
					} else {
				%>
				<td align="center">
					<s:text name="regist.prpLregist.no" />
					<%--否 --%>
				</td>
				<%
					}
							}
						}
				%>
				<td align="center">
					<c:out value="${swfLogList1.handlerName}" />
				</td>
				<%
					//写操作人数据
				%>
				<%
					//定损退回节点，标志由哪退回   
						if (nodeType.equals("certa") && nodeStatus.equals("3")) {
				%>
				<td align="center" width="5%">
					<c:choose>
						<c:when test="${swfLogList1.businessType=='verip'}">
							<s:text name="workflow.hejia" />
							<%--核价 --%>
						</c:when>
						<c:when test="${swfLogList1.businessType=='verif'}">
							<s:text name="query.hesun" />
							<%--核损 --%>
						</c:when>
						<c:when test="${swfLogList1.businessType=='compe'}">
							<s:text name="sendUndwrt.Adjusting" />
							<%--理算 --%>
						</c:when>
						<c:otherwise></c:otherwise>
					</c:choose>
				</td>
				<%
					if (swfLog.getBusinessType().equals("")) {
				%>
				<td align="center"></td>
				<%
					}
				%>
				<%
					}
				%>
				<%
					if (!nodeType.equals("sched")) {
				%>
				<td align="center">
					<%
						//修改查看的时间，当退回的时候，显示的是退回的时间，而不是操作的时间。
								if (nodeStatus.equals("4")) {
					%>
		                   <%--<c:out value="${swfLogList1.submitTime}" />--%>
		                 <rc:rcDate name="submitTime" class="readonly" readonly="true" wdatePicker="false"  style="width:150px" value="${swfLogList1.submitTime}" /> 
		               <%
 		               	} else if (nodeStatus.equals("3")) {
 		               %>
		                <%-- <c:out value="${swfLogList1.flowInTime}" />--%>
		                <rc:rcDate name="flowInTime" class="readonly" readonly="true" wdatePicker="false"  style="width:150px" value="${swfLogList1.flowInTime}" /> 
		               <%
 		               	} else {
 		               				if ("3".equals(nowNodeStatus) && "certi".equals(nodeType)) {
 		               %>
		                    <%-- <c:out value="${swfLogList1.flowInTime}" />--%>
		                    <rc:rcDate name="flowInTime" class="readonly" readonly="true" wdatePicker="false"  style="width:150px" value="${swfLogList1.flowInTime}" /> 
		                   <%
 		                   	} else {
 		                   					if (nodeStatus.equals("0") || nodeStatus.equals("-1")) {
 		                   %>
			                     <c:if test="${not empty swfLogList1.flowInTime}">
			                       <rc:rcDate name="flowInTime" class="readonly" readonly="true" wdatePicker="false"  style="width:150px" value="${swfLogList1.flowInTime}" /> 
			                     </c:if>
			                   <%
			                   	} else if (nodeStatus.equals("2")) {
			                   %>
			                     <c:if test="${not empty swfLogList1.handleTime}">
			                       <rc:rcDate name="handleTime" class="readonly" readonly="true" wdatePicker="false"  style="width:150px" value="${swfLogList1.handleTime}" /> 
			                     </c:if>
			                   <%
			                   	} else {
			                   %>
			                     <c:if test="${not empty swfLogList1.submitTime}">
			                       <rc:rcDate name="submitTime" class="readonly" readonly="true" wdatePicker="false"  style="width:150px" value="${swfLogList1.submitTime}" /> 
			                     </c:if>
			                  <%
			                  	}
			                  				}
			                  			}
			                  %>
					<%
						//以下为每行隐含中的内容的具体信息
					%>
					<input name="flowID" type="hidden" value="<c:out value='${swfLogList1.id.flowID}'/>">
					<input name="logNo" type="hidden" value="<c:out value='${swfLogList1.id.logNo}'/>">
					<input name="keyIN" type="hidden" value="<c:out value='${swfLogList1.keyIn}'/>">
				</td>
				<%
					}
				%>
				<c:if test="${swfLog.nodeType=='commo'}">
					<td align="center">
						<c:out value='${swfLogList1.nodeTypeName}' />
					</td>
				</c:if>
				<%
					if (nodeStatus.equals("0")) {
				%>
				<td align="center">
					<%
						if (swfLog.getNodeStatus().equals("3")
										|| swfLog.getNodeType().equals("claim") // 已立案，结案不能回退,目前只限制在已经处理的回退任務//这些都不能回退的
										|| swfLog.getNodeType().equals("sched") || swfLog.getNodeType().equals("schel") || swfLog.getNodeType().equals("backv") || swfLog.getNodeType().equals("check") || swfLog.getNodeType().equals("certa")
										|| swfLog.getNodeType().equals("wound") || swfLog.getNodeType().equals("backc") || swfLog.getNodeType().equals("speci")) {
					%>
					<a href="<%=dealHref%>" onClick="return otherFlag('<c:out value="${swfLogList1.otherFlag}"/>')"><img name=buttonDistribute src="/claim/images/butDeal.gif" border="0" hspace="5" alt="<s:text name='workflow.select.deal'/>"></a>
					<%--选择处理 --%>
					<%
						if (quickCaseWritePower && (swfLog.getNodeType().equals("check") || swfLog.getNodeType().equals("certa")) && !quickCaseHref.equals("")) {
					%><a href="<%=quickCaseHref%>" onClick="return checkQuickCase('<c:out value="${swfLogList1.registNo}"/>')"><img name=buttonDistribute src="/claim/images/butQuickCase.gif" border="0" style="display: none" hspace="0" alt="<s:text name='workflow.specialCase.deal'/>">
					<%--简易赔案处理 --%> <%
 	}
 %> <%
 	} else {
 				if (!backHref.equals("")) {
 %> <a href="<%=dealHref%>" onclick="return compeCount('<%=swfLog.getCompeCount()%>')"><img name=buttonDistribute src="/claim/images/butDeal.gif" border="0" hspace="5" alt="<s:text name='workflow.select.deal'/>"></a>
					<%--选择处理 --%> <%--核赔通过的理算不允许回退到定损了 --%> <%
 	if ((!"2".equals(swfLog.getCompeFlag())) && (!"1".equals(swfLog.getCompeFlag()))) {
 %> <a href="<%=backHref%>" onClick="return otherFlag('<%=swfLog.getOtherFlag()%>')"><img name=buttonDistribute src="/claim/images/butBack.gif" border="0" hspace="0" alt="<s:text name='schedule.returnDeal'/>"></a>
					<%--回退处理 --%> <%
 	}
 %> <%
 	} else {
 %> <a href="<%=dealHref%>" onclick="return compeCount('<%=swfLog.getCompeCount()%>')"><img name=buttonDistribute src="/claim/images/butDeal.gif" border="0" hspace="5" alt="<s:text name='workflow.select.deal'/>"></a>
					<%--选择处理 --%> <%
 	}
 				//理算环节增加回退到单证环境按钮
 				String riskcode = swfLog.getRiskCode();
 				String classcode = riskcode.substring(0, 2);
 				if ("compe".equals(swfLog.getNodeType()) && "0".equals(swfLog.getCompeFlag()) && !"27".equals(classcode)) {
 %> <a href="/claim/compensate/compensateBackCerti.do?swfLogFlowID=<%=swfLog.getId().getFlowID()%>&swfLogLogNo=<%=swfLog.getId().getLogNo()%>" onClick="return ifSubmit('<%=swfLog.getRegistNo()%>')"><img name=buttonDistribute src="/claim/images/butBack.gif" border="0"
							hspace="0" alt="<s:text name='prompt.workFlow.checkQuickCase3'/>"></a>
					<%--回退到单证环节 --%> <%
 	}
 			}
 %>
				</td>
				<%
					}
						//简易赔案特殊的内容
						if (quickCaseWritePower && nodeStatus.equals("2") && (swfLog.getNodeType().equals("check") || swfLog.getNodeType().equals("certa")) && !quickCaseHref.equals("")) {
				%>
				<td>
					<a href="<%=quickCaseHref%>" onclick="return checkQuickCase('<%=swfLog.getRegistNo()%>')"><img name=buttonDistribute src="/claim/images/butQuickCase.gif" border="0" hspace="0" style="display: none" alt="<s:text name='workflow.specialCase.deal'/>"></a>
				</td>
				<%--简易赔案处理 --%>
				<%
					}
						//显示注销申请的处理按钮
						if (nodeStatus.equals("-1") && funcName != null && funcName.equals("cancelApply")) {
				%>
				<td align="center">
					<a href="<%=dealHref%>"><img name=buttonDistribute src="/claim/images/butCancel.gif" border="0" hspace="5" alt="<s:text name='claim.cancelReject'/>"></a>&nbsp;
				</td>
				<%--注销/拒赔  --%>
				<%
					}
						//显示特殊赔案申请的处理按钮
						if (nodeStatus.equals("-1") && funcName != null && funcName.equals("specialApply")) {
							dealHref = "/claim/specailCaseQuery.do?" + flowStr;
				%>
				<td align="center">
					<a href="<%=dealHref%>" border="0" hspace="5" alt="<s:text name="specialCase.specialCas.applye"/>"><s:text name="workflow.status.applye" /></a>&nbsp;
				</td>
				<%--特殊赔案申请,申请  --%>
				<%
					}
						if (nodeStatus.equals("4") &&!"sched,claim,certa,verip,verpo,compp,verif,wound,veriw,propc,propv".contains(nodeType)&& !"modify".equals(method)) {
				%>
				<%
					//立案应该封掉“收回”功能。
				%>
				<td align="center">
					<a style = "display: none" href="/claim/workflow/processWorkflow.do?editType=recycle&flowID=<c:out value='${swfLogList1.id.flowID}'/>&logNo=<c:out value='${swfLogList1.id.logNo}'/>"><s:text name="workflow.withdraw" /></a>&nbsp;
				</td>
				<%--收回 --%>
				<%
					} else if ((nodeStatus.equals("4") && "sched,claim,certa,verip,verpo,compp,verif,wound,veriw,propc,propv".contains(nodeType))||
					(nodeStatus.equals("2") && "sched,regis,check,claim,certi,certa,verif,wound,veriw,propc,propv".contains(nodeType))) {
				%>
					<td align="center"></td>
					<%
						}
							index++;
					%>
			</c:forEach>
			<tr class="listtail">
				<td colspan='<%=colNumber%>' align="center">
					<%@include file="/pages/common/pub/TurnPage.jsp"%>
				</td>
			</tr>
		</table>
		<table>
			<tr>
				<%--
					//1.为了查勘登记所使用的输入域，此处输入的name名称必须与查勘登记录入的名称相同，否则UIfacade会有问题
				--%>
				<input type="hidden" name="recordCount" class="common" value="<%=index%>">
				<input type="hidden" name="swfLogFlowID" class="common" value="">
				<input type="hidden" name="swfLogLogNo" class="common" value="">
				<input type="hidden" name="bussinessNo" class="common" value="">
				<c:if test="${swfLog.nodeType!='commo'}">
					<input type="hidden" name="nodeType" class="common" value="<c:out value='${param.nodeType}'/>">
				</c:if>
				<input type="hidden" name="status" class="common" value="<c:out value='${param.status}'/>">
				<input type="hidden" name="alertMessage" class="common" value="<c:out value='${swfLog.alertMessage}'/>">
				<input type="hidden" name="userLastAction" class="common" value="">
				<input type="hidden" name="flag" value="">
				<input type="hidden" name="editType" value="<%=editType%>">
				<input type="hidden" name="FuncName" value="<c:out value='${param.FuncName}'/>">
				<input type="hidden" name="searchFlag" value="">
				<input type="hidden" name="searchField" value="<%=searchField%>">
				<input type="hidden" name="searchLabel" value="<%=searchLabel%>">
				<input type="hidden" name="type" value="<c:out value='${requestScope.com_sinosoft_type}'/>" />
			</tr>
		</table>
	</form>
</body>
</html>
<script language="javascript">
   fm.queryButton.disabled = false;
</script>