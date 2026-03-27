<%--
****************************************************************************
* DESC       : 工作流节点状态查询结果页面
* AUTHOR     : 理赔组
* CREATEDATE ：2013-02-16
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
        <table>
            <tr>
                <%--
                    //1.为了查勘登记所使用的输入域，此处输入的name名称必须与查勘登记录入的名称相同，否则UIfacade会有问题
                --%>
                <input type="hidden" name="recordCount" class="common" value="${fn:length(requestScope.swfLog.swfLogList)}">
                <input type="hidden" name="swfLogFlowID" class="common" value="">
                <input type="hidden" name="swfLogLogNo" class="common" value="">
                <input type="hidden" name="bussinessNo" class="common" value="">
                <c:if test="${swfLog.nodeType!='commo'}">
                    <input type="hidden" name="nodeType" class="common" value="<c:out value='${param.nodeType}'/>">
                </c:if>
                <input type="hidden" name="status" class="common" value="<c:out value='${param.status}'/>">
                <input type="hidden" name="alertMessage" class="common" value="<c:out value='${swfLog.alertMessage}'/>">
                <input type="hidden" name="userLastAction" class="common" value="">
                <input type="hidden" name="flag" value="${param.flag}">
                <input type="hidden" name="editType" value="${pageScope.editType}">
                <input type="hidden" name="FuncName" value="<c:out value='${param.FuncName}'/>">
                <input type="hidden" name="searchFlag" value="">
                <input type="hidden" name="searchField" value="${param.searchField}">
                <input type="hidden" name="searchLabel" value="${param.searchLabel}">
                <input type="hidden" name="type" value="<c:out value='${param.type}'/>" />
            </tr>
        </table>
    </form>
</body>
<script language="javascript">
   fm.queryButton.disabled = false;
</script>
</html>