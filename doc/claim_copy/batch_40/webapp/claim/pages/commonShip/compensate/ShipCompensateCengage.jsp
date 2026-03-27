
<%--
****************************************************************************
* DESC       ：特别约定显示画面
* AUTHOR     ：理赔组
* CREATEDATE ： 2004-05-20
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<!--建立显示的录入条，可以收缩显示的-->
<%@ include file="/common/taglibs.jsp"%>
<table class="common" align="center" width="100%">
    <tr class=mline>
        <td class="common" colspan="4" style="text-align: left">
            <img style="cursor: hand;" src="/claim/images/butCollapseBlue.gif" name="EngageImg" onclick="showPage(this,spanEngage)">
            <s:text name="certainLoss.prpLcheck.specialize" />
            <br>
            <%--特别约定--%>
            <span id="spanEngage" style="display: none">
                <table class=common cellpadding="5" cellspacing="1" id="Engage">
                    <thead>
                        <tr>
                            <td class="centertitle" width="15%">序號</td>
                            <td class="centertitle" width="35%">特約代碼</td>
                            <td class="centertitle" colspan="2" width="50%">特約名稱</td>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${requestScope.prpCengage.prpCengageList}" var="prpCengageTemp" varStatus="prpCengageTempStatus">
                            <tr>
                                <td class="centertitle" width="15%">${pageScope.prpCengageTemp.id.serialNo}</td>
                                <td class="centertitle" width="35%">${pageScope.prpCengageTemp.clauseCode}</td>
                                <td class="centertitle" width="35%">${pageScope.prpCengageTemp.clauses}</td>
                                <td class="centertitle" width="15%">
                                    <input type="button" ACCESSKEY="."  value='...' name='button_Engage_Open_Context' onclick="buttonOnClick(this);">
                                    <span id="span_Engage_Context" style='width: 520; display: none; position: absolute; background-color: FFFFFF;'>
                                        <table class="common">
                                            <tr>
                                                <td class="prompttitle">
                                                    <s:text name="certainLoss.prpLcheck.prpLcheckspecializeInfo" /><%-- 特别约定详细信息 --%>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="prompt"${pageScope.prpCengageTemp.context}</td>
                                            </tr>
                                            <tr>
                                                <td class="common">
                                                    <input type=button class=button name='button_Engage_Close_Context' value="<s:text name='button.close.value' />" ACCESSKEY="O" onclick="hideSpan(this);">
                                                </td>
                                            </tr>
                                        </table>
                                    </span>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </span>
        </td>
    </tr>
</table>
<script language="javascript">
    function showSpan(field){
        var span = $(field).next("span").get(0);
        var ex = window.event.clientX + document.body.scrollLeft; //得到事件的坐标x
        var ey = window.event.clientY + document.body.scrollTop; //得到事件的坐标y
        ex = ex - 520;
        if (ex < 0) {
            ex = 0;
        }
        span.style.left = ex;
        span.style.top = ey;
        span.style.display = '';
    }

    function hideSpan(field){
        $(field).closest("span").hide();
    }
</script>