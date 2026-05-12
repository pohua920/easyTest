<%@	page contentType="text/html; charset=GBK" language="java"%>
<%--
****************************************************************************
* DESC       ：已出险信息显示画面（非车险）
* AUTHOR     ： Sinosoft
* CREATEDATE ： 2005-09-26
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta_js.jsp"%>
<html>
<head>
<title><s:text name="title.registBeforeEdit.damage" /></title>
<%--已出险信息显示--%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<SCRIPT LANGUAGE="JavaScript">
	//按钮单击事件，用於相同保单号码多报案的显示
    //reason:在报案登记画面中，已出险次数的历次出险事故的清单中,可以点击报案号关联到相关案件信息  
    /**
     *@description 弹出关联报案信息页面
     *@param       无
     *@return      通过返回true,否则返回false
     */
    function showRegist(registNo){
	    var linkURL = "/claim/registFinishQueryList.do?prpLregistRegistNo=" + registNo + "&editType=SHOW";
	    var newWindow = window.open(linkURL, "NewWindow", "width=640,height=500,top=0,left=0,toolbar=yes,location=no,directories=no,menubar=no,scrollbars=yes,resizable=yes,status=no");
    }
    function showPicture(registNo){
	    var linkURL = "/claim/pages/common/certify/CertifyViewAllFile.jsp?businessNo=" + registNo;
	    var newWindow = window.open(linkURL, "NewWindow", "width=640,height=500,top=0,left=0,toolbar=yes,location=no,directories=no,menubar=no,scrollbars=yes,resizable=yes,status=no");
    }
    $(function(){
        init_td_width();
        $(window).resize(function(){
           init_td_width();
        });
    })
    /***
     *构建一个内容可下拉的表格
     */
    function init_td_width(){
        var $td = $("#tableHead td");//取tableHead下每个td宽，使tableBody的每行数据与之对齐
        $("#tableBody tr").each(function(i,tr){
            $(tr).children("td").each(function(i,td){
                $(td).width($($td[i]).width());
            });
        });
        var $tableHead = $("#tableHead");
        var $spanContent = $("#spanContent");
        var $tableBody = $("#tableBody").width($tableHead.width());
        var $tableFoot = $("#tableFoot").width($tableHead.width());
        var heigth = $(window).height();
        var bodyHeigth = $tableBody.height();
        var width = $("#tableHead").width();
        var maxHeigth = $(window).height() - $tableHead.height() - $tableFoot.height();//span最大高度
        $tableBody.width(width);
        $tableFoot.width(width);
        if (maxHeigth >= bodyHeigth) {
            maxHeigth = bodyHeigth + 1;
        }else{
            width += 18;
        }
        $spanContent.width(width);
        $spanContent.height(maxHeigth);
        
        
    }
</SCRIPT>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginhigh="0" style="overflow-y: hidden;">
    <table id="tableHead" class="common" cellpadding="5" cellspacing="1" style="width: 1315px">
        <thead>
            <tr>
                <td class="prompttitle" style="width: 50px">序號</td>
                <td class="prompttitle" style="width: 180px">備案號碼</td>
                <td class="prompttitle" style="width: 160px">賠案號碼</td>
                <td class="prompttitle" style="width: 100px">估損金額NTD</td>
                <td class="prompttitle" style="width: 100px">賠付金額NTD</td>
                <td class="prompttitle" style="width: 85px">出險時間</td>
                <td class="prompttitle" style="width: 180px">出險地點</td>
                <td class="prompttitle" style="width: 180px">出險原因</td>
                <td class="prompttitle" style="width: 180px">案件照片</td>
                <td class="prompttitle" style="width: 100px">狀態</td>
            </tr>
        </thead>
    </table>
    <span id="spanContent" style="overflow-y: auto; overflow-x: hidden;">
        <table id="tableBody" class="common" cellpadding="5" cellspacing="1">
            <tbody>
            	<c:set var="index" value="1"/>
                <c:forEach items="${requestScope.registClaimDtoList}" var="registClaimDto" varStatus="stat">
                    <c:if test="${pageScope.registClaimDto.registNo != requestScope.curRegistNo}">
                        <tr style="display: block;">
                            <td class="prompt" align="center">
                                <c:out value="${index}" />
                                <c:set var="index" value="${index+1}"/>
                            </td>
                            <td class="prompt" align="center">
                                <a href="javascript:showRegist('${pageScope.registClaimDto.registNo}')">
                                    <c:out value="${pageScope.registClaimDto.registNo}" />
                                </a>
                            </td>
                            <td class="prompt" align="center">
                                <c:out value="${pageScope.registClaimDto.claimNo}" />
                            </td>
                            <td class="prompt" align="center">
                                <fmt:formatNumber value="${pageScope.registClaimDto.sumClaim}" pattern="#" />
                            </td>
                            <td class="prompt" align="center">
                                <fmt:formatNumber value="${pageScope.registClaimDto.sumPaidShow}" pattern="#" />
                            </td>
                            <td class="prompt" align="center">
                                <rc:rcDate name="damageStartDate" class="readonly" style="width: 80px" value="${pageScope.registClaimDto.damageStartDate}" wdatePicker="false" />
                            </td>
                            <td class="prompt" align="center">
                                <c:out value="${pageScope.registClaimDto.damageAreaName}" />
                            </td>
                            <td class="prompt" align="center">
                                <c:out value="${pageScope.registClaimDto.damageName}" />
                            </td>
                            <td class="prompt" align="center">
                                <a href="javascript:showPicture('${pageScope.registClaimDto.registNo}')">
                                    <c:out value="${pageScope.registClaimDto.registNo}" />
                                </a>
                            </td>
                            <td class="prompt" align="center">
                                <c:out value="${pageScope.registClaimDto.status}" />
                            </td>
                        </tr>
                    </c:if>
                </c:forEach>
            </tbody>
        </table>
    </span>
    <table class="common" cellpadding="5" cellspacing="1" id="tableFoot">
        <tfoot>
            <tr>
                <td colspan="10" class="common" align="center">
                    <input type=button name='button_Peril_Close_Context' class=button value="<s:text name='button.close.value' />" ACCESSKEY="O" onclick="window.close()">
                </td>
            </tr>
        </tfoot>
    </table>
</body>
</html>
