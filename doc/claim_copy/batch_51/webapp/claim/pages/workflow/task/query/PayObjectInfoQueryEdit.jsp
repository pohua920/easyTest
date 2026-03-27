<%--mantis：CLM0265，處理人員：DP0713，需求單編號：新核心-DP自動化功能--%>
<%@page contentType="text/html; charset=GBK"%>
<%@include file="/common/taglibs.jsp"%>
<%@page import="java.util.*" %>
<html>
<head>
    <title><s:text name="title.wfLogBeforeEdit.nodeFlag" /></title>
    <%@include file="/common/meta_css.jsp"%>
    <%@include file="/common/i18njs.jsp"%>
    <%@include file="/common/meta_js.jsp"%>
    <script src="/claim/pages/workflow/task/js/WfLogStatusList.js"> </script>
    <script language="javascript">
        function submitForm(field){
        // reason:当按下某一按钮时请将这个按钮变灰，否则用户可能多按引发错误
            field.disabled = true;
            fm.searchFlag.value = "true";
            fm.submit();//提交
        }
        $(document).ready(function(){
	    	$(document).keydown(function (e) {
			    var e = e?e:window.event;
			    var code = e.keyCode?e.keyCode:e.which;
			    if (code == 13) {
			        $("#button").click();
			    }
			})
	    });
        function queryUrgentCase(){ 
            var linkURL = "/claim/wfLogQuery.do?editType=urgentCase&nodeType=urgentCase";<%--紧急案件清单--%>
            var newWindow = window.open(linkURL,"<s:text name='title.compensate.emergencyCaseListing'/>","width=800,height=600,top=0,left=0,toolbar=yes,location=no,directories=no,menubar=no,scrollbars=yes,resizable=yes,status=no");
        }
        function changeClick(){
        	queryConditionChange = "1";//動了搜尋條件改成1，接著後面把搜尋條件page=1
        	$('input[name="queryConditionChange"]').val("1");
        }
    </script>
</head>
<body>
    <!-- form name="fm" action="/claim/wfLogQuery.do"  method="post" -->
    <form name="fm" action="/claim/payObjectInfoQuery.do"  method="post" >
        <%@include file="/pages/workflow/task/query/QueryStatusTitle.jsp"%>
        
        
		<table border="0" align="center" cellpadding="5" cellspacing="1" class="common">
		    <tr>
		        <td colspan="4" class="formtitle">
		            <s:text name="regist.prpLregist.queryConditions" /><%-- 查詢條件 --%>
		        </td>
		    </tr>
		    <tr>
		        <td class='title'>
		         <s:text name="db.prpLcompensate.compensateNo" /><%-- 賠款計算書號碼 --%>：
		        </td>
		        <td class='input'>
		            <select class=tag name="BusinessNoSign">
		                <option value="=">=</option>
		                <option value="=*">=*</option>
		            </select>
		            <input type=text name="BusinessNo" class="query" style="width: 70%" value="${param.BusinessNo}" onchange="changeClick();">
		        </td>
		        <td class='title'><s:text name="db.prpCmain.policyNo" /><%-- 保單號碼 --%>：</td>
		        <td class='input'>
		            <select class=tag name="PolicyNoSign">
		                <option value="=">=</option>
		                <option value="=*">=*</option>
		            </select>
		            <input type=text name="PolicyNo" class="query" style="width: 70%" value="${param.PolicyNo}" onchange="changeClick();">
		        </td>
		    </tr>
		    <tr>
		        <td class='title'>
		        
		        </td>
		        <td class='input'>
		       	   <input type="hidden" name="queryConditionChange" value="">
		       	   <input type="hidden" name="preOperatorCode" value="${operatorCode}">
		           <input type="checkbox" name="operatorCode" value="0" onclick="checkOperatorCode('0');" checked>一般修改作業&nbsp;&nbsp;&nbsp;
		           <input type="checkbox" name="operatorCode" value="1" onclick="checkOperatorCode('1');">覆核作業
		            
		        </td>
		        <td class='title' >狀態：</td>
		        <td class='input'>
		            <select class=tag name="InputStatusCondition" onchange="changeClick();">
		                <option value=""></option>
		                <option value="-1" <c:if test="${param.InputStatusCondition=='-1'}"><c:out value="selected"/></c:if>>退回</option>
		                <option value="1" <c:if test="${param.InputStatusCondition=='1'}"><c:out value="selected"/></c:if>>待審核</option>
		                <option value="2" <c:if test="${param.InputStatusCondition=='2'}"><c:out value="selected"/></c:if>>審核通過</option>
		            </select>
		        </td>
		    </tr>
		    <tr>
		        <td class='button' colspan="4">
		            <input type=button id="button" name="queryButton" class='button' disabled value="查 詢" onClick="submitForm(this);">
		        </td>
		    </tr>
		</table>
		
		
		
		<table class="common" cellpadding="5" cellspacing="1">
		    <tr>
		        <td colspan=15 class="formtitle">${pageScope.strTitle}<s:text name="compensate.adjustmentInformation" /></td>
		    </tr>
		    <tr>
		        <td class="centertitle" style="width: 4%"><s:text name="regist.prpLregist.serialNo" /><%-- 序號 --%></td>
		        <td class="centertitle" ><s:text name="db.prpLpersonloss.compensateNo" /><%-- 赔款计算书号 --%></td>
		        <td class="centertitle" ><s:text name="db.prpCmain.policyNo" /><%-- 保單號碼 --%></td>
		        <td class="centertitle" ><s:text name="db.prpLregist.riskCode" /><%-- 險種 --%></td>
		        <td class="centertitle"><s:text name="db.prpCmain.insuredName" /><%-- 被保险人名称 --%></td>
		        <td class="centertitle"><s:text name="db.prpLregist.damageDate" /><%-- 出險日期 --%></td>
		        <td class="centertitle"><s:text name="db.prpLcompensate.sumPaid" /><%-- 賠付金額 --%></td>
		        <td class="centertitle"><s:text name="prpLclaim.claimNo" /><%-- 立案號碼 --%></td>
		        <td class="centertitle"><s:text name='db.prpLregist.operatorCode'/><%-- 操作人員 --%></td>
		        <td class="centertitle">修改人員</td>
		        <td class="centertitle">覆核人員</td>
		        <td class="centertitle">狀態</td>
		        <td class="centertitle">流出時間<%-- 時間 --%></td>
		    </tr>
		<c:forEach items="${requestScope.swfLog.swfLogList}" var="tempSwfLog" varStatus="stat">
		    <c:set var="flowStr" value="&swfLogFlowID=${tempSwfLog.id.flowID}&swfLogLogNo=${tempSwfLog.id.logNo}&status=${tempSwfLog.nodeStatus}&riskCode=${tempSwfLog.riskCode}&editType=${pageScope.editType}&nodeType=${tempSwfLog.nodeType}&businessNo=${tempSwfLog.businessNo}&keyIn=${tempSwfLog.keyIn}&policyNo=${tempSwfLog.policyNo}&modelNo=${tempSwfLog.modelNo}&nodeNo=${tempSwfLog.nodeNo}&dfFlag=${tempSwfLog.dfFlag}&actorId=${tempSwfLog.actorId}&processId=${tempSwfLog.processId}"/>
		    <c:set var="flowStrStart" value="${ctx}/compensate/payObjectInfoEdit.do?prpLcompensateCompensateNo=${tempSwfLog.businessNo}&dpLogInputStatus=${tempSwfLog.dpLogInputStatus}&level=0" />
		    <c:choose>
		        <c:when test="${stat.index%2==0}"><tr class="listodd"></c:when>
		        <c:otherwise><tr class="listeven"></c:otherwise>
		    </c:choose>
		        <td align="center">${stat.count}</td>
		        <td align="center">
		            <a href="${flowStrStart}${flowStr}" title="${tempSwfLog.titleStr}" ><c:out value="${tempSwfLog.businessNo}" /></a>
		        </td>
		        <td align="center">
		            <c:forEach items="${tempSwfLog.relatePolicyList}" var="relatePolicy" varStatus="stat">
		                <c:out value="${relatePolicy.id.policyNo}" />
		                <c:if test="${stat.index + 1 == stat.count}"><br/></c:if>
		            </c:forEach>
		        </td>
		        <td align="center"><c:out value="${tempSwfLog.riskCodeName}"/></td>
		        <td align="center"><c:out value="${tempSwfLog.insuredName}" /></td>
		        <td align="center"><rc:rcDate name="damageDate" class="readonly" style="width:80px" readonly="true" wdatePicker="false" value="${tempSwfLog.damageDate}" /></td>
		        <td align="center"><fmt:formatNumber value="${tempSwfLog.sumPaid}" pattern="#"/></td>
		        <!-- 
		        <td align="center">
		            <a href="/claim/claimFinishQueryList.do?prpLclaimClaimNo=${tempSwfLog.keyIn}${flowStr}" title="${tempSwfLog.titleStr}">${tempSwfLog.keyIn}</a>
		        </td>
		         -->
		         <td align="center">${tempSwfLog.keyIn}</td>
		        <td align="center"><c:out value="${tempSwfLog.handlerName}" /></td>
		        <td align="center">${tempSwfLog.inputUser}</td>
		        <td align="center">${tempSwfLog.reviewUser}</td>
		        <td align="center">
			        <c:choose>
		                <c:when test="${tempSwfLog.dpLogInputStatus=='-1'}">退回</c:when>
		                <c:when test="${tempSwfLog.dpLogInputStatus=='1'}">待審核</c:when>
		                <c:when test="${tempSwfLog.dpLogInputStatus=='2'}">審核通過</c:when>
		                <c:otherwise></c:otherwise>
		            </c:choose>
		        </td>
		        <td align="center">
		            <c:choose>
		                <c:when test="${tempSwfLog.nodeStatus == '2'}">
		                    <rc:rcDate name="handleTime" class="readonly" readonly="true" wdatePicker="false"  style="width:150px" value="${tempSwfLog.handleTime}" /> 
		                </c:when>
		                <c:when test="${tempSwfLog.nodeStatus == '4'}">
		                    <rc:rcDate name="submitTime" class="readonly" readonly="true" wdatePicker="false"  style="width:150px" value="${tempSwfLog.submitTime}" />
		                </c:when>
		                <c:otherwise>
		                    <rc:rcDate name="flowInTime" class="readonly" readonly="true" wdatePicker="false"  style="width:150px" value="${tempSwfLog.flowInTime}" />
		                </c:otherwise>
		            </c:choose>
		            <input name="flowID" type="hidden" value="<c:out value='${tempSwfLog.id.flowID}'/>">
		            <input name="logNo" type="hidden" value="<c:out value='${tempSwfLog.id.logNo}'/>">
		            <input name="keyIN" type="hidden" value="<c:out value='${tempSwfLog.keyIn}'/>">
		        </td>
		    </tr>
		</c:forEach>
		    <tr class="listtail">
		        <td colspan="10" align="center">
		            <%@include file="/pages/common/pub/TurnPage.jsp"%>
		        </td>
		    </tr>
		</table>
		<%@ include file="/pages/workflow/task/query/QueryBottom.jsp"%>
		
		
		
		
		
		
		
		<input type="hidden" name="chargeType" value="${param.chargeType}"/>
        <%-- 为了查勘登记所使用的输入域，此处输入的name名称必须与查勘登记录入的名称相同，否则UIfacade会有问题--%>
        <input type="hidden" name="recordCount" class="common" value="">
        <input type="hidden" name="swfLogFlowID" class="common" value="">
        <input type="hidden" name="swfLogLogNo" class="common" value="">
        <input type="hidden" name="bussinessNo" class="common" value="">
        <input type="hidden" name="nodeType" class="common" value="compp">
        <input type="hidden" name="status" class="common" value="4">
        <input type="hidden" name="userLastAction" class="common" value="">
        <input type="hidden" name="flag" value="${param.flag}">
        <input type="hidden" name="editType" value="">
        <input type="hidden" name="FuncName" value="${param.FuncName}">
        <input type="hidden" name="searchFlag" value="">
        <input type="hidden" name="searchField" value="${param.searchField}">
        <input type="hidden" name="searchLabel" value="${param.searchLabel}">
        <input type="hidden" name="method" value="${param.method}">
        <input type="hidden" name="type" value="<c:out value='${param.type}'/>" />
    </form>
</body>







<script language="javascript">
   fm.queryButton.disabled = false;
</script>
<c:if test="${not empty param.RegistNoSign}">
    <script type="text/javascript">$(":input[name='RegistNoSign']").val("${param.RegistNoSign}");</script>
</c:if>
<c:if test="${not empty param.BusinessNoSign}">
    <script type="text/javascript">$(":input[name='BusinessNoSign']").val("${param.BusinessNoSign}");</script>
</c:if>
<c:if test="${not empty param.PolicyNoSign}">
    <script type="text/javascript">$(":input[name='PolicyNoSign']").val("${param.PolicyNoSign}");</script>
</c:if>
<c:if test="${not empty param.RiskCodeNoSign}">
    <script type="text/javascript">$(":input[name='RiskCodeNoSign']").val("${param.RiskCodeNoSign}");</script>
</c:if>
<c:if test="${not empty param.insuredNameSign}">
    <script type="text/javascript">$(":input[name='insuredNameSign']").val("${param.insuredNameSign}");</script>
</c:if>
<c:if test="${not empty param.InsuredIdentifyNumberSign}">
    <script type="text/javascript">$(":input[name='InsuredIdentifyNumberSign']").val("${param.InsuredIdentifyNumberSign}");</script>
</c:if>
<c:if test="${not empty param.AppliIdentifyNumberSign}">
    <script type="text/javascript">$(":input[name='AppliIdentifyNumberSign']").val("${param.AppliIdentifyNumberSign}");</script>
</c:if>
<c:if test="${not empty param.chargeType && param.chargeType =='D'}">
    <script type="text/javascript">
    	$(":input[name='RiskCode']").val(RISKINFO.RISKCODE_DAZ);
    	$(":input[name='RiskCode']").attr("readonly",true);
    </script>
</c:if>
<script type="text/javascript">
    function queryUrgentCase(){ 
      var linkURL = "/claim/wfLogQuery.do?editType=urgentCase&nodeType=urgentCase";<%--紧急案件清单--%>
      var newWindow = window.open(linkURL,"<s:text name='title.compensate.emergencyCaseListing'/>","width=800,height=600,top=0,left=0,toolbar=yes,location=no,directories=no,menubar=no,scrollbars=yes,resizable=yes,status=no");
    } 
    
    function checkOperatorCode(opt){
       	$(":input[name='operatorCode']")[0].checked="";//clear
       	$(":input[name='operatorCode']")[1].checked="";//clear
    	
       	$(":input[name='operatorCode']")[opt].checked = "checked";
    }
</script>

<!-- CHOOSE VALUE -->
<c:if test="${not empty param.operatorCode}">
    <script type="text/javascript">
    	checkOperatorCode(${param.operatorCode});
    </script>
</c:if>
</html>