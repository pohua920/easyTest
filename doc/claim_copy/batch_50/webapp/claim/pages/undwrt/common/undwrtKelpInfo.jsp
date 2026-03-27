<%--
****************************************************************************
* DESC       ：在页面中增加“巨灾一级代码”、巨灾二级代码”录入域
* AUTHOR     ：理赔组
* CREATEDATE ：2013-07-10
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ include file="/common/taglibs.jsp"%>
<table class="common" align="center" width="100%">
    <tr>
        <td class="input4" style="text-align: left" colspan=4>
            <img style="cursor: hand;" src="${ctx }/images/butCollapseBlue.gif" name="LtextImg" onclick="showPage(this,LlossCatas)">
            <s:text name="regist.prpLregist.catastrophe" /><%--巨灾信息--%>
            <br>
            <table class="common" align="center" id="LlossCatas" style="display: none">
                <tbody>
                    <tr>
                        <td class="title" style="width: 10%">
                            <s:text name="regist.prpLregist.catastropheCode1" /><%-- 巨災類型 --%>
                        </td>
                        <td class="input4" style="width: 20%">
                            <input name="prpCatastropheCode1" class="readonly" readonly style="width: 98%" maxlength=5 value="${prpLclaim.catastropheCode1}">
                        </td>
                        <td class="title" style="width: 10%">
                            <s:text name="regist.prpLregist.catastropheName1" /><%-- 巨災名稱 --%>
                        </td>
                        <td class="input4" style="width: 20%">
                            <input name="prpCatastropheName1" class="readonly" readonly style="width: 98%" maxlength=30 value="${prpLclaim.catastropheName1}">
                        </td>
                        <td class="title" style="width: 10%">
                            <s:text name="regist.prpLregist.catastropheCode" /><%-- 巨災代碼 --%>
                        </td>
                        <td class="input4" style="width: 20%">
                            <input name="prpCatastropheName2" class="readonly" readonly style="width: 98% maxlength = 30" value="${prpLclaim.catastropheName2}">
                        </td>
                    </tr>
                </tbody>
            </table>
        </td>
    </tr>
</table>