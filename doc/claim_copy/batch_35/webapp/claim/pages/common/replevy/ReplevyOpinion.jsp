<%@ include file="/common/taglibs.jsp"%>
<table class="common" cellspacing="1" cellpadding="5" <c:if test="${empty requestScope.swfLogList}">style="display: none"</c:if>>
    <tr>
        <td class="common" colspan="4" style="text-align: left;">
            <img style="cursor: hand;" src="/claim/images/butExpandBlue.gif" name="ReplevyOpinionImg" onclick="showPage(this,spanReplevyOpinion)">審批處理意見訊息
            <br>
            <!--定核损意见详细信息-->
            <span id="spanReplevyOpinion" style="" cellspacing="1" cellpadding="0"><%-- 多行输入展现域 --%>
                <table class="common" id="ReplevyOpinion" cellspacing="1" cellpadding="5">
                    <thead>
                        <tr>
                            <td class="centertitle" style="width: 6%">序號</td>
                            <td class="centertitle" style="width: 18%">時間</td>
                            <td class="centertitle" style="width: 12%">審核級別</td>
                            <td class="centertitle" style="width: 12%">處理人員</td>
                            <td class="centertitle" style="width: 15%">處理意見</td>
                            <td class="centertitle" style="width: 35%">內容</td>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${requestScope.swfLogList}" varStatus="stat" var="tempSwfLog">
                            <tr>
                                <td class="input" style="width: 6%" align="center"><c:out value="${stat.count}" /></td>
                                <td class="input" style="width: 18%" align="center">
                                    <rc:rcDate style="width:150px" name="flowInTime" class="readonly" readonly="true" wdatePicker="false" value="${tempSwfLog.submitTime}" />
                                </td>
                                <td class="input" style="width: 12%" align="center"><c:out value="${tempSwfLog.nodeName}" /></td>
                                <td class="input" style="width: 12%" align="center"><c:out value="${tempSwfLog.handlerName}" /></td>
                                <c:forEach var="swfNotion" items="${tempSwfLog.swfNotionList}" end="0">
                                    <td class="input" style="width: 15%" align="center">
                                       <c:choose>
                                          <c:when test="${swfNotion.flag=='1'}">提交審核</c:when>
                                          <c:when test="${swfNotion.flag=='2'}">審核通過</c:when>
                                          <c:when test="${swfNotion.flag=='3'}">提交上級</c:when>
                                          <c:when test="${swfNotion.flag=='4'}">駁回修改</c:when>
                                          <c:otherwise></c:otherwise>
                                       </c:choose>
                                    </td>
                                    <td class="input" style="width: 45%"  align="center">${swfNotion.handleText}</td>
                                </c:forEach>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </span>
        </td>
    </tr>
</table>