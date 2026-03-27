<%--
****************************************************************************
* DESC       ：显示赔款计算过程
* AUTHOR     ：中科软
* MODIFYLIST ：Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<table class="common" align="center">
    <tr>
        <td class="common" style="text-align: left">
            <img style="cursor: hand;" src="/claim/images/butCollapseBlue.gif" name="RegistTextImg" onclick="showPage(this,RegistText)">
            <s:text name="commonLiab.compensate.reparateProcess" />
            <%--赔款计算过程--%>
            <br>
            <table class="common" align="center" id="RegistText" style="display: none">
                <tbody>
                    <tr>
                        <td class="input" style="text-align: center;" colspan="0">
                            <textarea style="wrap: hard" rows="15" cols="80" name="prpLctextContextInnerHTML">${prpLctext.context}</textarea>
                            <br>
                        </td>
                    </tr>
                </tbody>
            </table>
        </td>
    </tr>
</table>
