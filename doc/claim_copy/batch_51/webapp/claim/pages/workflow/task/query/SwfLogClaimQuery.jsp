<%--
****************************************************************************
* DESC       : 工作流节点状态查询结果页面
* AUTHOR     : 理赔组
* CREATEDATE ： 2014-04-09
* MODIFYLIST ： 立案节点的工作流任务查询 (用于组织待处理、正处理、已处理立案任务、申请注销/拒赔 查询介面的查询条件)
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ include file="/common/taglibs.jsp"%>
<table border="0" align="center" cellpadding="5" cellspacing="1" class="common">
    <tr>
        <td colspan="4" class="formtitle">
            <s:text name="regist.prpLregist.queryConditions" /><%-- 查詢條件 --%>
        </td>
    </tr>
    <tr>
    <c:choose>
        <c:when test="${param.status == '0'}">
            <td class='title'><%-- 未处理 --%>
                <s:text name="db.prpLregist.registNo" /><%-- 備案號碼 --%>：
            </td>
            <td class='input'>
                <select class=tag name="RegistNoSign">
                    <option value="=">=</option>
                    <option value="=*">=*</option>
                </select>
                <input type=text name="RegistNo" class="query" style="width: 70%" value="${param.RegistNo}">
            </td>
        </c:when>
        <c:otherwise>
            <td class='title'><%-- 正处理、已处理、注销拒赔 --%>
                <s:text name="db.prpLclaim.claimNo" /><%-- 立案號碼 --%>：
            </td>
            <td class='input'>
                <select class=tag name="BusinessNoSign">
                    <option value="=">=</option>
                    <option value="=*">=*</option>
                </select>
                <input type=text name="BusinessNo" class="query" style="width: 70%" value="${param.BusinessNo}">
            </td>
        </c:otherwise>
    </c:choose>
        <td class='title'><s:text name="db.prpCmain.policyNo" /><%-- 保單號碼 --%>：</td>
        <td class='input'>
            <select class=tag name="PolicyNoSign">
                <option value="=">=</option>
                <option value="=*">=*</option>
            </select>
            <input type=text name="PolicyNo" class="query" style="width: 70%" value="${param.PolicyNo}">
        </td>
    </tr>
    <tr>
        <td class='title'><s:text name="db.prpLregist.riskCode" /><%-- 險種 --%>：</td>
        <td class='input'>
            <select class=tag name="RiskCodeNoSign">
                <option value="=">=</option>
            </select>
            <input type=text name="RiskCode" class="query" style="width: 70%" value="${param.RiskCode}">
        </td>
        <td class='title'>
            <c:choose>
               <c:when test="${param.status == '2'}"><s:text name="guarantee.dealIime" /><%-- 處理時間 --%></c:when>
               <c:when test="${param.status == '4'}"><s:text name="workflow.flowTime" /><%-- 流出時間 --%>：</c:when>
               <c:otherwise><s:text name="general.flowInTime" /><%-- 流入時間 --%>：</c:otherwise>
            </c:choose>
        </td>
        <td class='input'>
            <rc:rcDate name="statStartDate" value="${param.statStartDate}" style="width: 41.5%"/>
            &nbsp;<s:text name="prompt.to" />&nbsp;
            <rc:rcDate name="statEndDate" value="${param.statEndDate}" style="width: 42%"/>
        </td>
    </tr>
    <tr>
        <td class='title'><s:text name="db.prpCmain.insuredName" /><%--被保险人名称 --%>：</td>
        <td class='input'>
            <select class=tag name="insuredNameSign">
                <option value="=">=</option>
                <option value="=*">=*</option>
            </select>
            <input type=text name="insuredName" class="query" style="width: 70%" value="${param.insuredName}" >
        </td>
        <td class='title'><s:text name="db.prpCmain.insured" />ID：<%-- 身份证号--%></td>    
        <td class='input'>
            <select class=tag name="InsuredIdentifyNumberSign">
                <option value="=">=</option>
            </select>
            <input type=text name="InsuredIdentifyNumber" class="query" style="width: 70%" value="<c:out value="${param.InsuredIdentifyNumber}"/>">
        </td>
    </tr>
<c:if test="${param.status == '2' || param.status == '4'}">
    <tr>
        <td class='title'><%-- 正处理、已处理调换备案号码的位置 --%>
            <s:text name="db.prpLregist.registNo" /><%-- 備案號碼 --%>：
        </td>
        <td class='input'>
            <select class=tag name="RegistNoSign">
                <option value="=">=</option>
                <option value="=*">=*</option>
            </select>
            <input type=text name="RegistNo" class="query" style="width: 70%" value="${param.RegistNo}">
        </td>
        <td class='title'></td>
        <td class='input'></td>
    </tr>
</c:if>
<c:if test="${param.status == '0'}"><%-- 正处理增加 --%>
    <tr>
        <td class='title'><s:text name="db.prpLregist.appliName" />：<%-- 要保人名称--%></td>
        <td class='input'>
            <select class=tag name="AppliNameSign">
                <option value="=">=&nbsp;</option>
            </select>
            <input type=text name="AppliName" class="query" style="width: 70%" value="<c:out value="${param.AppliName}"/>">
        </td>
        <td class='title'><s:text name="db.prpLregist.appliNameCode" />ID：<%-- 要保人身份证号--%></td>
        <td class='input'>
            <select class=tag name="AppliIdentifyNumberSign">
                <option value="=">=</option>
            </select>
            <input type=text name="AppliIdentifyNumber" class="query" style="width: 70%" value="<c:out value="${param.AppliIdentifyNumber}"/>">
        </td>
    </tr>
</c:if>
<c:if test="${param.status == '0' || param.status == '2' || param.status == '4'}"><%-- 未处理、正处理,已处理 --%>
    <tr>
        <td class='title'><s:text name="query.damageDate" />：<%-- 事故日期 --%></td>
        <td class='input'  align="left">
            <rc:rcDate name="damageStartDate" style="width: 41.5%" value="${pageScope.damageStartDate}"/>
            &nbsp;<s:text name="prompt.to" />&nbsp;
            <rc:rcDate name="damageEndDate" style="width: 42%" value="${pageScope.damageEndDate}"/>
        </td>
        <%-- add by zhuyongwei reason:增加“車牌號碼”查询条件 begin --%>
        <td class='title'><s:text name="db.prpLregist.licenseNo" />：<%-- 車牌號碼--%></td>
        <td class='input'>
         <select class=tag name="LicenseNoSign">
                <option value="=">=</option>
         </select>
        <input type=text name="LicenseNo" class="query" style="width: 70%" value="${pageScope.LicenseNo}">
        </td>
        <%-- add by zhuyongwei reason:增加“車牌號碼”查询条件 end --%>
    </tr>
</c:if>
    <tr>
        <td class="title" style="color: red" colspan="2">
            <s:text name="prompt.schedule.query1" /><%--"="符号，必须精确查询。 --%><br>
            <s:text name="prompt.schedule.query2" /><%-- "=*"符号，前匹配後模糊的查询。 --%><br>
        </td>
        <td class="title" style="color: red" colspan="2">
            <c:if test="${param.status == '-1'}">
                <s:text name="workflow.query3" /><%--非车险立案提交後，便不能再做註銷！！！ --%>
            </c:if>
        </td>
    </tr>
    <tr>
        <td class='button' colspan="4">
            <input type=button id="button" name="queryButton" class='button' disabled value="查 詢" onClick="submitForm(this);">
        </td>
    </tr>
</table>
<%/** 匹配符的默认值 */%>
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
<c:if test="${not empty param.AppliNameSign}">
    <script type="text/javascript">$(":input[name='AppliNameSign']").val("${param.AppliNameSign}");</script>
</c:if>
<c:if test="${not empty param.InsuredIdentifyNumberSign}">
    <script type="text/javascript">$(":input[name='InsuredIdentifyNumberSign']").val("${param.InsuredIdentifyNumberSign}");</script>
</c:if>
<c:if test="${not empty param.AppliIdentifyNumberSign}">
    <script type="text/javascript">$(":input[name='AppliIdentifyNumberSign']").val("${param.AppliIdentifyNumberSign}");</script>
</c:if>
<c:if test="${not empty param.LicenseNo}">
    <script type="text/javascript">$(":input[name='LicenseNo']").val("${param.LicenseNo}");</script>
</c:if>
