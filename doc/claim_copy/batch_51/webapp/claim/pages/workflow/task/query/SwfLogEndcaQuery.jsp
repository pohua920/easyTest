<%--
****************************************************************************
* DESC       : 工作流节点状态查询结果页面
* AUTHOR     : 理赔组
* CREATEDATE ： 2014-04-09
* MODIFYLIST ： 结案节点的工作流任务查询 (用于组织待处理 结案 查询介面的查询条件)
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
        <td class='title'><s:text name="general.flowInTime" /><%-- 流入時間 --%>：</td>
        <td class='input'>
            <rc:rcDate name="statStartDate" style="width:41.5%" value="${param.statStartDate}" />
            &nbsp;<s:text name="prompt.to" />&nbsp;
            <rc:rcDate name="statEndDate" style="width:42%" value="${param.statEndDate}" />
        </td>
    </tr>
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
        <td class='title'><s:text name="db.prpCmain.insured" /><%--被保险人名称 --%>：</td>
        <td class='input'>
            <select class=tag name="insuredNameSign">
                <option value="=">=</option>
                <option value="=*">=*</option>
            </select>
            <input type=text name="insuredName" class="query" style="width: 70%" value="${param.insuredName}" >
        </td>
    </tr>
    <tr>
        <td class='title'><s:text name="db.prpCmain.insured" />ID：<%-- 身份证号--%></td>    
        <td class='input'>
            <select class=tag name="InsuredIdentifyNumberSign">
                <option value="=">=</option>
            </select>
            <input type=text name="InsuredIdentifyNumber" class="query" style="width: 70%" value="<c:out value="${param.InsuredIdentifyNumber}"/>">
        </td>
        <td class='title'></td>
        <td class='input'></td>
    </tr>
    <tr>
        <td class="title" style="color: red" colspan="2">
            <s:text name="prompt.schedule.query1" /><%--"="符号，必须精确查询。 --%><br>
            <s:text name="prompt.schedule.query2" /><%-- "=*"符号，前匹配後模糊的查询。 --%><br>
        </td>
        <td class="title" style="color: red" colspan="2"></td>
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
<c:if test="${not empty param.InsuredIdentifyNumberSign}">
    <script type="text/javascript">$(":input[name='InsuredIdentifyNumberSign']").val("${param.InsuredIdentifyNumberSign}");</script>
</c:if>