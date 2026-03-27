<%--
****************************************************************************
* DESC       ：赔偿限额/免赔额显示画面 
* AUTHOR     ：中科软
* MODIFYLIST ：Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<!--建立显示的录入条，可以收缩显示的-->
<table class="common" align="center" width="100%">
    <tr class=mline>
        <td class="common" colspan="4" style="text-align: left">
            <img style="cursor: hand;" src="/claim/images/butCollapseBlue.gif" name="LimitImg" onclick="showPage(this,Limit)">
            <s:text name="compensate.sumInsured" />
            <%--保险金额/免赔额--%>
            <table class=common cellpadding="5" cellspacing="1" id="Limit" style="display: none">
                <thead>
                    <tr>
                        <td class="centertitle">
                            <s:text name="commonAcci.compensate.allSinglePart" /><%--全单/部分--%>
                        </td>
                        <td class="centertitle">
                            <s:text name="db.prpCprofit.itemkindNo" /><%--标的险别序号--%>
                        </td>
                        <td class="centertitle" style="width: 40%">
                            <s:text name="commonAcci.compensate.limitFranchiseType" /><%--限额/免赔额类型--%>
                        </td>
                        <td class="centertitle">
                            <s:text name="db.prpLperson.currency" /><%--币别--%>
                        </td>
                        <td class="centertitle">
                            <s:text name="compensate.sumInsured" /><%--保险金额/免赔额--%>
                        </td>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="limitList" items="${requestScope.prpClimitList}">
                        <tr class=oddrow>
                            <td class="centertitle">${limitList.limitGrade}</td>
                            <td class="centertitle">${limitList.limitNo}</td>
                            <td class="centertitle">${limitList.limitTypeName}</td>
                            <td class="centertitle">${limitList.currencyName}</td>
                            <td class="centertitle">
                                <fmt:formatNumber value="${limitList.limitFee}" pattern="#" />
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </td>
    </tr>
</table>