
<%@ page contentType="text/html; charset=GBK"%>
<%@page import="com.sinosoft.claim.dto.custom.UserDto"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.sinosoft.claimciplatform.dto.domain.CIClaimDemandDto"%>
<html>
<%@ include file="/common/taglibs.jsp"%>
<head>
<title><s:text name="title.toolsBeforeEdit.DataTools" /></title>
<%-- 平台数据上出工具 --%>

<script language="javascript">
 
function submitForm()
{  if(fm.all("claimUpload")[5].checked == true){

   }else{
       if(fm.uploadRegistNo.value ==""&&fm.uploadCompensateNo.value==""){
           alert("报案号和理算书号不能都为空");
           fm.registNo.focus();
           return false;
       }
   }
   for(var i=0;i<fm.claimUpload.length;i++){
       if(fm.all("claimUpload")[i].checked == true){
           fm.submit();
           return true;
       }
   }
   
   for(var j=0;j<fm.claimUploadNew.length;j++){
       if(fm.all("claimUploadNew")[j].checked == true){
           fm.submit();
           return true;
       }
   }
   alert("请选择案件环节");
   return false;
}
function init(){
  if(fm.all("claimUpload")[6].checked == true){
    fm.all("claimUpload")[0].checked = false;
    fm.all("claimUpload")[3].checked = false;
    fm.all("claimUpload")[4].checked = false;
  }
}

function document.onkeydown() {
    if (event.keyCode == 13) {
        document.getElementById("button").click();
        return false;
    }
} 

    
</script>
<style type="text/css">
.title {
    FONT-SIZE: 11pt;
    COLOR: #000000;
    BACKGROUND-COLOR: #F7F7F7;
    width: 5%
}

.formtitle {
    font-size: 11pt;
    background: #5cb095;
    color: #ffffff;
    height: 25px;
    text-align: center;
}

.button {
    FONT-SIZE: 11pt;
    COLOR: #000000;
    TEXT-ALIGN: center;
}
</style>
</head>
<%
        UserDto user = (UserDto) session.getAttribute("user");
        String messge = user.getUserMessage();
        ArrayList ciClaimDemandDtoList = new ArrayList();
        String uploadType = "";
        String compensateFlag = "";
        if (request.getAttribute("ciClaimDemandDtoList") != null) {
            ciClaimDemandDtoList = (ArrayList) request.getAttribute("ciClaimDemandDtoList");
        }
        if (request.getAttribute("uploadType") != null) {
            uploadType = request.getAttribute("uploadType").toString();
        }
        if (request.getAttribute("compensateFlag") != null) {
            compensateFlag = request.getAttribute("compensateFlag").toString();
        }
%>
<body onload="document.onkeydown();">
    <form name="fm" action="/claim/platformUpload.do" method="post">
        <%
            if ("".equals(uploadType)) {
        %>
        <table width="100%" border="0" align="center" cellpadding="4" cellspacing="1" class="common">
            <tr>
                <td colspan="9" class="formtitle">
                    <s:text name="tools.DataUpload" />
                    <%-- 平台数据上传 --%>
                </td>
            </tr>
            <tr>
                <td class='title' colspan="1" width='5%'>
                    <s:text name="prpLregist.registNo" />：
                    <%-- 报案号 --%>
                </td>
                <td class='title' colspan="1" width='25%'>
                    <textarea class=common1 name="uploadRegistNo" style="width: 200px" rows="5"></textarea>
                </td>
                <td class='title' colspan="1" width='14%'>
                    <input type=checkbox name='claimUpload' value="0" onclick="init()">
                    <s:text name="tools.ReportUpload" />
                    <%-- 报案上传 --%>
                </td>
                <td class='title' colspan="1">
                    <input type=checkbox name='claimUpload' width='14%' value="1" onclick="init()">
                    <s:text name="tools.OfficeUpload" />
                    <%-- 立案上传 --%>
                </td>
                <td class='title' colspan="1">
                    <input type=checkbox name='claimUpload' width='14%' value="6" onclick="init()">
                    <s:text name="tools.AdjustingUpload" />
                    <%-- 理算上传 --%>
                </td>
                <td class='title' colspan="1">
                    <input type=checkbox name='claimUpload' width='14%' value="2" onclick="init()">
                    <s:text name="tools.CompletedUpload" />
                    <%-- 结案上传 --%>
                </td>
                <td class='title' colspan="1">
                    <input type=checkbox name='claimUpload' width='14%' value="7" onclick="init()">
                    <s:text name="tools.PaidUpload" />
                    <%-- 赔付上传 --%>
                </td>
                <td class='title' colspan="1">
                    <input type=checkbox name='claimUpload' width='14%' value="3" onclick="init()">
                    <s:text name="tools.CancellationUpload" />
                    <%-- 报案注销上传 --%>
                </td>
                <td class='title' colspan="1">
                    <input type=checkbox name='claimUpload' width='14%' value="4" onclick="init()">
                    <s:text name="tools.OfficeOffUpload" />
                    <%-- 立案注销上传 --%>
                </td>
            </tr>
            <tr>
                <td class='title' colspan="1" width='5%'>
                    <s:text name="tools.AdjustingNumber" />：
                    <%-- 理算书号 --%>
                </td>
                <td class='title' colspan="1" width='25%'>
                    <textarea class=common1 name="uploadCompensateNo" style="width: 200px" rows="5"></textarea>
                </td>
                <td class='title' colspan="6" width='14%'>
                    <input type=hidden name='claimUploadNew' value="1">
                    <input type=checkbox name='claimUploadNew' value="0">
                    <s:text name="tools.ClosingAppend" />
                    <%-- 结案追加 --%>
                </td>
            </tr>
        </table>
        <table width=100%>
            <tr>
                <td class='button' colspan="4">
                    <input id="button" type=button class='button' value="<s:text name='button.submit.value'/>" <%-- 提交 --%>
                        onClick="submitForm();">
                </td>
            </tr>
            <tr>
                <td class='button' colspan="4">
                    <%=messge%>
                </td>
            </tr>
        </table>
        <%
            } else {
        %>
        <table width="100%" border="0" align="center" cellpadding="4" cellspacing="1" class="common">
            <tr>
                <td colspan="8" class="formtitle">
                    <s:text name="tools.DataUpload" />
                    <%-- 平台数据上传 --%>
                </td>
            </tr>
            <tr>
                <td class='title' colspan="1" width='5%'>
                    <s:text name="prpLregist.registNo" />：
                    <%-- 报案号 --%>
                </td>
                <td class='title' colspan="1" width='25%'>
                    <textarea class=common1 name="uploadRegistNo" style="width: 200px" rows="5" readOnly>
                    <%
                        for (int i = 0; i < ciClaimDemandDtoList.size(); i++) {
                                    out.println(((CIClaimDemandDto) ciClaimDemandDtoList.get(i)).getRegistNo());
                                }
                    %>
                    </textarea>
                </td>
                <td class='title' colspan="1" width='14%'>
                    <input type=checkbox name='claimUpload' value="0" <%if (uploadType.equals("regist")) {%> onClick="this.checked=true" checked <%} else {%> onClick="this.checked=false" <%}%>>
                    <s:text name="tools.ReportUpload" />
                    <%-- 报案上传 --%>
                </td>
                <td class='title' colspan="1">
                    <input type=checkbox name='claimUpload' width='14%' value="1" <%if (uploadType.equals("claim")) {%> onClick="this.checked=true" checked <%} else {%> onClick="this.checked=false" <%}%>>
                    <s:text name="tools.OfficeUpload" />
                    <%-- 立案上传 --%>
                </td>
                <td class='title' colspan="1">
                    <input type=checkbox name='claimUpload' width='14%' value="6" <%if (uploadType.equals("claim") && "1".equals(compensateFlag)) {%> onClick="this.checked=true" checked <%} else {%> onClick="this.checked=false" <%}%>>
                    <s:text name="tools.AdjustingUpload" />
                    <%--理算上传  --%>
                </td>
                <td class='title' colspan="1">
                    <input type=checkbox name='claimUpload' width='14%' value="2" <%if (uploadType.equals("underwrite")) {%> onClick="this.checked=true" checked <%} else {%> onClick="this.checked=false" <%}%>>
                    <s:text name="tools.CompletedUpload" />
                    <%-- 结案案上传 --%>
                </td>
                <td class='title' colspan="1">
                    <input type=checkbox name='claimUpload' width='14%' value="3" onClick="this.checked=false">
                    <s:text name="tools.CancellationUpload" />
                    <%-- 报案注销上传 --%>
                </td>
                <td class='title' colspan="1">
                    <input type=checkbox name='claimUpload' width='14%' value="4" onClick="this.checked=false">
                    <s:text name="tools.OfficeOffUpload" />
                    <%-- 立案注销上传 --%>
                </td>
            </tr>
        </table>
        <table width=100%>
            <tr>
                <td class='button' colspan="4">
                    <input id="button" type=button class='button' value="<s:text name='button.submit.value'/>" <%-- 提交 --%>
                        onClick="submitForm();">
                </td>
            </tr>
            <tr>
                <td class='button' colspan="4">
                    <font color='red'><s:text name="prompt.tools.message" /></font>
                    <%-- 该事故下有在前面的环节上传行业协会平台失败的交强险报案，请先补传数据！ --%>
                </td>
            </tr>
        </table>
        <%
            }
        %>
    </form>
</body>
</html>