<%--
****************************************************************************
* DESC       ：显示实赔文字页面
* AUTHOR     ：中科软
* MODIFYLIST ：Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
    function getContext(field){
        var contextNo = $(field).val();
        var $contextInnerHTML = $(":input[name='prpLltextContextInnerHTML']");
        if(contextNo == ""){
            $contextInnerHTML.val("");
        }else{
            var riskCode = fm.prpLcompensateRiskCode.value;
            $.post("${ctx}/compensate/getCompeContext.do", {contextNo : contextNo,riskCode : riskCode}, function(data){
                $contextInnerHTML.val($.trim(data));//
            },"html");
        }
    }
</script>
<table class="common" cellpadding="5" cellspacing="1" id="Lltext" style="display: block">
    <tr>
        <td class="common" style="text-align: left;">
            <img style="cursor: hand;" src="/claim/images/butExpandBlue.gif" name="LlTextImg" onclick="showPage(this,LlText2)">
            <input class=readonly readonly name="tdLltextTitle" value="理算說明" style="width: 75px"><br>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <font color="red">理算說明類型：</font>
            <select name="prpLcompensateContextNo" class="input" onchange="getContext(this);" <c:if test="${empty requestScope.CompeContext}">style="width: 250px;"</c:if> >
                <option value="" ></option>
                <c:forEach items="${requestScope.CompeContext}" var="temp">
                    <option value="${temp.id.contextNo}" style="color:red"><c:out value="${temp.title}" /></option>
                </c:forEach>
            </select>
            <script type="text/javascript">
                $(":input[name='prpLcompensateContextNo']").val("${prpLcompensate.contextNo}");
            </script>
            <br>
            <table class="common" align="center" id="LlText2" style="display: block">
                <tbody>
                    <tr>
                        <td class="input" style="text-align: center;" colspan="0">
                            <textarea style="wrap: hard;text-align: left;" rows="15" cols="80" name="prpLltextContextInnerHTML">${prpLltext.context}</textarea>
                        </td>
                    </tr>
                </tbody>
            </table>
        </td>
    </tr>
</table>
<c:choose>
    <c:when test="${requestScope.recaseFlag=='0' || param.editType=='SHOW'}">
        <script language="javascript">changePrpLcompensateFinallyFlag();</script>
    </c:when>
    <c:otherwise>
        <script language="javascript">changePrpLcompensateFinallyFlag1();</script>
    </c:otherwise>
</c:choose>