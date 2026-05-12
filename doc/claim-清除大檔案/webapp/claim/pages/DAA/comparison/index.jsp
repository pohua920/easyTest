<%--
****************************************************************************
* DESC       ¡êo2¨¦?¡À2¨¦?¡¥¨º?¨¨?????
* AUTHOR     ¡êo ¨¤¨ª?a¡Á¨¦
* CREATEDATE ¡êo 2004-06-07
* MODIFYLIST ¡êo   Name Sunhao      Date  2004-08-24          Reason/Contents
           1. ???¨®3¦Ì??o?¡ê?¡ã??t¡Á¡ä¨¬?¡ê?2¨´¡Á¡Â¨º¡À??2¨¦?¡¥¨¬??t
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app" %>
<%@page import="com.sinosoft.sysframework.reference.*"%>
<%@ page import="com.sinosoft.claim.dto.custom.*" %>
<%@ page import="com.sinosoft.claim.dto.domain.*" %>
<%@ page import="com.sinosoft.sysframework.common.datatype.*" %>

<%
 //add by liuyanmei 20051110 
  String riskType= request.getParameter("type");
  //System.out.println("----------------------riskType-----"+ riskType );
%>
<script>
//add by liping 20070109 start ¡ã¡ä?£¤?¨¬¨®|??3¦Ì
    function document.onkeydown() 
    { 
    if(event.keyCode==13) 
    { 
      document.getElementById("button").click(); 
      return false; 
    } 
    }  
    // add by liping 20070109 end
</script>
<!--

//-->
</script>
<html>
<head>
<title><s:text name="title.claimBeforeEdit.titleName" /></title>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
</head>
<script type="text/javascript">
  function submitform(field,saveType)
   {
        if(saveType=='2'){
        var   strUrl = "/claim/comparison.do?savetype="+saveType +"&nowDate=" +fm.nowDate.value + "&comCode="+fm.comCode.value;
        }else if(saveType=='3'){
         var   strUrl = "/claim/comparison.do?savetype="+saveType +"&nowDate=" +fm.nowDate.value + "&comCode="+fm.comCode.value+"&detailType="+fm.detailType.value;
        }
        var newWindow = window.open(strUrl,'','width='+700+',height='+500+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1.resizable=1,status=0');
        newWindow.focus();
        return newWindow;
   }
  </script>
<body>
	<form name="fm" action="" method="post">
		<table border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td colspan="4" class="formtitle">
					<s:text name="comparison.dataComparison" />
				</td>
			</tr>
			<%-- ¨ºy?Y¡À¨¨?? --%>
			<tr>
				<td class='title'>
					<s:text name="comparison.pleaseSelectTime" />
					¡êo
				</td>
				<%-- ??????¨º¡À?? --%>
				<td class='input'>
					<input type=text name="nowDate" class="query" value="<%= new DateTime(DateTime.current().toString(),DateTime.YEAR_TO_DAY).addYear(-1) %>">
					<img style='cursor: hand' src="/claim/images/bgcalendar.gif" align="absmiddle"
						onclick="TogglePopupCalendarWindow('document.fm.nowDate', '<%=new DateTime(DateTime.current(),DateTime.YEAR_TO_DAY).getYear()-15 %>', '<%=new DateTime(DateTime.current(),DateTime.YEAR_TO_DAY).getYear()+2 %>')">
				</td>
				<td class='title'>
					<s:text name="comparison.branchName" />
					¡êo
				</td>
				<%-- ¡¤?1?????3? --%>
				<td class='title'>
					<select name="comCode">
						<option value="11">
							<s:text name="comparison.beijingBranch" />
						</option>
						<%-- ¡À¡À??¡¤?1??? --%>
					</select>
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="comparison.dataQueryTypes" />
					¡êo
				</td>
				<%-- ¨ºy?Y2¨¦?¡¥¨¤¨¤D¨ª --%>
				<td class='title'>
					<select name="detailType">
						<option value="1">
							<s:text name="comparison.insuConfirmNumber" />
						</option>
						<%-- ¨ª?¡À¡ê¨¨¡¤¨¨??t¨ºy?¡Â?? --%>
						<option value="2">
							<s:text name="comparison.cancelPolicyNumber" />
						</option>
						<%-- ¡Á¡é?¨²¡À¡ê¦Ì£¤?t¨ºy?¡Â?? --%>
						<option value="3">
							<s:text name="comparison.surrenderNumber" />
						</option>
						<%-- ¨ª?¡À¡ê?t¨ºy?¡Â?? --%>
						<option value="4">
							<s:text name="comparison.strongReport" />
						</option>
						<%-- ????¡À¡§¡ã?¨ºy?¡Â?? --%>
						<option value="5">
							<s:text name="comparison.strongRegistered" />
						</option>
						<%-- ????¨¢¡é¡ã?¨ºy?¡Â?? --%>
						<option value="6">
							<s:text name="comparison.showingStrongArithmetic" />
						</option>
						<%-- ????¨¤???¨ºy?¡Â?? --%>
						<option value="7">
							<s:text name="comparison.strongJieAnShu" />
						</option>
						<%-- ?????¨¢¡ã?¨ºy?¡Â?? --%>
						<option value="8">
							<s:text name="comparison.strongCasesCancellation" />
						</option>
						<%-- ????¡Á¡é?¨²¡ã??t¨ºy?¡Â?? --%>
						<option value="9">
							<s:text name="comparison.businessReport" />
						</option>
						<%-- ¨¦¨¬¨°¦Ì¡À¡§¡ã?¨ºy?¡Â?? --%>
						<option value="10">
							<s:text name="comparison.businessRegisteredSub" />
						</option>
						<%-- ¨¦¨¬¨°¦Ì¨¢¡é¡ã?¨ºy?¡Â?? --%>
						<option value="11">
							<s:text name="comparison.businessDepartment" />
						</option>
						<%-- ¨¦¨¬¨°¦Ì¨¤???¨ºy?¡Â?? --%>
						<option value="12">
							<s:text name="comparison.businessJieAnShu" />
						</option>
						<%-- ¨¦¨¬¨°¦Ì?¨¢¡ã?¨ºy?¡Â?? --%>
						<option value="13">
							<s:text name="comparison.businessCasesCancellation" />
						</option>
						<%-- ¨¦¨¬¨°¦Ì¡Á¡é?¨²¡ã??t¨ºy?¡Â?? --%>
					</select>
				</td>
				<td colspan="2" class='title'></td>
			</tr>
			<tr>
				<td colspan="4" align="center" class='title'>
					<input type="button" value="¨ºy?Y¡À¨¨??" onclick=" return submitform(this,'2'); ">
					&nbsp;&nbsp;
					<input type="button" value="?¡Â??2¨¦?¡¥" onclick="return submitform(this,'3');">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
