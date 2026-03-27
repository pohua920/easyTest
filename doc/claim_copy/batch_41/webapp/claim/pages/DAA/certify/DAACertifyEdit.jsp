<%--
****************************************************************************
* DESC       ：单证登记录入/修改页面
* AUTHOR     ：理赔组
* CREATEDATE ：2004-07-05
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<html xmlns:mpc>
<head>
<%@ include file="/common/taglibs.jsp"%>
<%@include file="/common/meta_js.jsp"%>
<%@include file="/common/i18njs.jsp"%>
  <!--对title处理-->
    <title>
    <%-- 单证登记 --%><s:text name="certainLoss.prpLcertifyCollect.prpLCertifyRegist"/></title>
    <%-- 页面样式  --%>
    <script src="${ctx}/pages/DAA/certify/js/DAACertifyEdit.js"></script>
    <%-- 标签页样式 --%>

    <!--这个函数是调动所能用到的通用js的过程，一般包括最常用的js的函数声明都在StaticJavacript中
	-->
    <link rel="stylesheet" type="text/css" href="${ctx }/css/Standard.css">
   
    <%-- 标签页样式 --%>
	<jsp:include page="/behaviors/MpcStyle.jsp" />
<script language="Javascript" src="${ctx }/common/js/InputCode.js" ></script>
    <!--这个函数是调动所能用到的通用js的过程，一般包括最常用的js的函数声明都在meta_js.jsp中-->
   
  <SCRIPT LANGUAGE="JavaScript">
  /**
  *@description 初始化回访问询信息
  *@param       无
  *@return      通过返回true,否则返回false
  */
  function initSet(){
	<c:if test="${prpLqualityCheckList!=null}">
     <c:forEach items="${prpLqualityCheckList}" var="prpLqualityCheck" varStatus="checkStatus">
     	<c:if test="${prpLqualityCheck.checkResult=='0'}">
     		fm.VisitBackQue${checkStatus.count}[1].checked=true;
     	</c:if>
     	<c:if test="${prpLqualityCheck.checkResult=='1'}">
     		fm.VisitBackQue${checkStatus.count}[0].checked=true;
     	</c:if>
     	<c:if test="${prpLqualityCheck.checkResult=='2'}">
     		fm.VisitBackQue${checkStatus.count}[2].checked=true;
     	</c:if>
     	fm.txtQuestionRemark${checkStatus.count}.value='${prpLqualityCheck.checkRemark}';
     </c:forEach>
	</c:if>
  	return true;
  }
  </SCRIPT>
   <script type="text/javascript">
		//mpc调整
		$(function(){
		     initWindow();
	         $(window).resize(function(){
				initWindow();
	         });
		})
   </script>
  </head>
<!--<body class=interface  onload="initPage();initSet();">类的初始化，几个页面上都用到-->
	<s:if test="#request.editType=='SHOW'||#request.editType=='DELETE'">
		<body class=interface onload="initPage();initSet();readonlyAllInput();oMPC.style.visibility='visible';">
	</s:if>
	<s:else>
		<body class=interface onload="initPage();initSet();oMPC.style.visibility='visible';">
	</s:else>
	<DIV id="mainLayer" class="mainLayer">
	<s:if test="nodeType == 'certi'">
		<form name=fm action="${ctx}/certify/certifyEditPost.do" method="post" onsubmit="return validateForm(this);">
		<s:if test="(#request.editType=='ADD'||#request.editType=='EDIT') && #request.prpLcertifyCollect.status!=4 ">
			<s:token></s:token>
		</s:if>
	</s:if>
	<s:else>
		<form name=fm action="${ctx}/certify/certifySavePost.do" method="post" onsubmit="return validateForm(this);">
	</s:else>
			<input type="hidden" name="nodeType" value="certi">
			<input type="hidden" name="editType" value="${editType}">
			<TABLE id="btnTable" cellpadding="0" cellspacing="0" border="0">
				<TR>
					<td align="left">
						<input class=bigbutton type="button" name="messageSave" value="<s:text name="button.claimsProcessingRecords.value" />" onclick="openWinSave(fm.RegistNo.value,'${prpLcertifyCollect.policyNo}',fm.riskCode.value,'certi','');">
						<%--赔案处理记录 --%>
						<s:if test="(#request.editType=='ADD'||#request.editType=='EDIT') && #request.prpLcertifyCollect.status!=4">
							<input class=button type="button" name="buttonCertifyDirect" value="<s:text name="button.stateClaim.value" />" onClick="doCertifyDirect('${prpLcertifyCollect.id.businessNo}','certi','${prpLcertifyCollect.riskCode}')">
						<%--索赔清单 --%>
						</s:if>
						<input class=bigbutton type="hidden" name="certifyDirectPrint" value="<s:text name="certify.ClaimEIR" />" onClick="certifyDirectList('${prpLcertifyCollect.id.businessNo}','certi')">
						
					</td>
					<%--索赔材料交接单--%>
				</tr>
			</table>
			<mpc:container ID="oMPC">
				<%-- 1.1.单证基本信息页面 --%>
				<mpc:page ID="tabMain" TABTITLE="<s:text name="certify.CollectInfo" />" TABTEXT="<s:text name="certify.CollectInfo" />">
					<%--单证收集信息--%>
					<DIV name="tabMain" class="tabMain">
						<%-- 1.1.1.定损基本讯息 --%>
						<%@include file="/pages/DAA/certify/DAACertifyMainEdit.jsp"%>
					</DIV>
				</mpc:page>
				<mpc:page ID="tabMain" TABTITLE="<s:text name="button.stateClaim.value" />" TABTEXT="<s:text name="button.stateClaim.value" />">
					<%--索赔清单 --%>
					<CENTER>
						<DIV name="tabMain" class="tabMain">
							<%@include file="/pages/DAA/certify/DAAcertifyDirect.jsp"%>
						</DIV>
					</CENTER>
				</mpc:page>
			</mpc:container>
			<TABLE id="btnCommon" class="common">
				<TR>
					<TD align="center">
						<CENTER><%@include file="/pages/DAA/certify/DAACertifySave.jsp"%></CENTER>
					</td>
				</tr>
			</table>
		</form>
	</DIV>
	</body>
</html>
