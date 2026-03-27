<%--
****************************************************************************
* DESC       ：通用按钮画面
* AUTHOR     : 中科软
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ include file="/common/taglibs.jsp"%>
<script language='javascript'>
    //显示列印窗口
    function printWindow(registNo,strWindowName){
        strUrl = "/claim/ClaimPrint.do?printType=CopyPrint&RegistNo=" + registNo;
        var pageWidth = screen.availWidth - 10;
        var pageHeight = screen.availHeight - 30;
        if (pageWidth < 100) {
            pageWidth = 100;
        }
        if (pageHeight < 100) {
            pageHeight = 100;
        }
        var newWindow = window.open(strUrl, strWindowName, 'width=' + pageWidth + ',height=' + pageHeight + ',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1.resizable=1,status=0');
        newWindow.focus();
        return newWindow;
    }
</script>
<c:choose>
    <c:when test="${param.editType=='SHOW'||param.editType=='DELETE'}">
        <%-- 保存通用按钮 --%>
        <table cellpadding="0" cellspacing="0" width="80%" class="common">
            <tr>
                <%-- 隐藏所按的保存按钮是哪个的标志--%>
                <td align="center">
                    <input type="hidden" name=buttonSaveType value="1">
                    <%--列印承保理赔信息--%>
                    <!--取消按钮-->
                    <input type=button name=buttonCancel class='button' value="<s:text name='button.return.value' />" onclick="javascript:history.go(-1);">
                </td>
            </tr>
        </table>
        <table cellpadding="0" cellspacing="0" width="0" height="0" id="buttonArea">
        </table>
    </c:when>
    <c:otherwise>
        <%-- 保存通用按钮 --%>
        <table id="buttonArea" cellpadding="0" cellspacing="0" width="80%" style="display:">
            <input type="hidden" name=buttonSaveType value="1">
            <tr>
                <td align="center">
                    <c:if test="${requestScope.prpLcompensate.status!='4'}">
                        <!--保存按钮-->
                        <input type="button" name=buttonSave class='button' value="<s:text name='button.save.value' />" onclick="return saveForm(this,'2');">
                        <!-- 如果涉及联共保，提交前，先调用联共保分摊按钮的方法 -->
                        <c:choose>
                            <c:when test="${not empty requestScope.coinsFlag}">
                                <c:choose>
                                    <c:when test="${requestScope.coinsFlag=='1'||requestScope.coinsFlag=='2'||requestScope.coinsFlag=='3'}">
                                        <input type="button" name=buttonSaveFinishSubmit class='button' value="<s:text name='button.submit.value'/>" onclick="if(!fm.countFlag.value=='1'){alert('請先產生聯共保分攤信息！')}else{return saveForm(this,'4')};">
                                    </c:when>
                                    <c:otherwise>
                                        <input type="button" name=buttonSaveFinishSubmit class='button' value="<s:text name='button.submit.value'/>" onclick="return saveForm(this,'4');">
                                    </c:otherwise>
                                </c:choose>
                            </c:when>
                            <c:otherwise>
                                <input type="button" name=buttonSaveFinishSubmit class='button' value="<s:text name='button.submit.value'/>" onclick="return saveForm(this,'4');">
                            </c:otherwise>
                        </c:choose>
                        <!--取消按钮-->
                        <input type=button name=buttonCancel class='button' value="<s:text name='button.cancel.value'/>" onclick="return resetForm();">
                        <c:if test="${param.status=='0'}">
                            <!--放弃任务按钮style="width:33%"-->
                            <input type=button name=buttonGiveup class='button' value="<s:text name='button.giveUpTask.value'/>" onclick="taskGiveup();">
                        </c:if>
                    </c:if>
                    <c:if test="${requestScope.prpLcompensate.status=='4'}">
                        <!--取消按钮-->
                        <input type=button name=buttonBack class='button' value="<s:text name='button.return.value' />" onclick="return history.back();">
                    </c:if>
                </td>
                <%@include file="/pages/common/sendUndwrt/SendUndwrtButton.jsp"%>
            </tr>
        </table>
    </c:otherwise>
</c:choose>