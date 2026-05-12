<%--
****************************************************************************
* DESC       ：在页面中增加“巨灾一级代码”、巨灾二级代码”录入域
* AUTHOR     ：理赔组
* CREATEDATE ：2013-07-10
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<script language='javascript'>
    /***
     * 二级巨灾代码必须关联一级巨灾代码，当一级巨灾代码改变时清空二级巨灾代码 
     */
    function clearCatastropheCode2(){
        var prpCatastropheCode2 = document.getElementsByName('prpCatastropheCode2');
        var prpCatastropheName2 = document.getElementsByName('prpCatastropheName2');
        if (prpCatastropheCode2.length > 0 && prpCatastropheName2.length > 0) {
            prpCatastropheCode2[0].value = '';
            prpCatastropheName2[0].value = '';
        }
    }
</script>
<table class="common" align="center" width="100%">
    <tr>
        <td class="common" style="text-align: left" colspan=4>
            <img style="cursor: hand;" src="${ctx }/images/butCollapseBlue.gif" name="LtextImg" onclick="showPage(this,LlossCatas)">
            <s:text name="regist.prpLregist.catastrophe" /><%--巨灾信息--%><br>
            <table class="common" align="center" id="LlossCatas" style="display: none">
                <tbody>
                    <tr>
                        <td class="title" style="width: 10%">
                            <s:text name="regist.prpLregist.catastropheCode1" />：<%-- 巨災類型 --%>
                        </td>
                        <td class="input" style="width: 20%">
                            <input name="prpCatastropheCode1" class="codecode" style="width: 98%" maxlength=5 value="${prpLclaim.catastropheCode1}" ondblclick="code_CodeSelect(this, 'CatastropheCode','0,1','Y');"
                                onchange="code_CodeSelect(this, 'CatastropheCode','0,1','Y');" onkeyup="code_CodeSelect(this, 'CatastropheCode','0,1','Y');">
                        </td>
                        <td class="title" style="width: 10%">
                            <s:text name="regist.prpLregist.catastropheName1" />：<%-- 巨災名稱 --%>
                        </td>
                        <td class="input" style="width: 20%">
                            <input name="prpCatastropheName1" class="codecode" style="width: 98%" maxlength=30 value="${prpLclaim.catastropheName1}">
                        </td>
                        <td class="title" style="width: 10%">
                            <s:text name="regist.prpLregist.catastropheCode" />：<%-- 巨災代碼 --%>
                        </td>
                        <td class="input" style="width: 20%">
                            <input name="prpCatastropheName2" class="input" style="width: 98% maxlength=30" value="${prpLclaim.catastropheName2}">
                        </td>
                    </tr>
                </tbody>
            </table>
        </td>
    </tr>
</table>