<%--
****************************************************************************
* DESC       ：单证登记录入/修改页面
* AUTHOR     ：理赔组
* CREATEDATE ：2004-07-05
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<!--对title处理-->
<title><s:text name="title.certifyBeforeEdit.prpLCertifyRegist" /></title>
<%--单证登记--%>
<%@include file="/common/meta_js.jsp"%>
<%@include file="/common/i18njs.jsp"%>
<%-- 页面样式  --%>
<link rel="stylesheet" type="text/css" href="${ctx }/css/Standard.css">
<script src="${ctx }/pages/common/certify/js/CertifyEdit.js"></script>
<SCRIPT LANGUAGE="JavaScript">
  /**
  *@description 初始化回访问询信息
  *@param       无
  *@return      通过返回true,否则返回false
  */
  function initSet(){
		<c:if test="${prpLqualityCheckList!=null}">
		<c:forEach items="${prpLqualityCheckList}" var="prpLqualityCheck" varStatus="check_status">
		<c:if test="${prpLqualityCheck.checkResult=='0'}">
		   fm.VisitBackQue${check_status.count }[1].checked=true;
		</c:if>
		<c:if test="${prpLqualityCheck.checkResult=='1'}">
		   fm.VisitBackQue${check_status.count }[0].checked=true;
		</c:if>
		<c:if test="${prpLqualityCheck.checkResult=='2'}">
		   fm.VisitBackQue${check_status.count }[2].checked=true;
		</c:if>
		fm.txtQuestionRemark${check_status.count }.value='${prpLqualityCheck.checkRemark}';
		</c:forEach>
		</c:if>
  		return true;
  }
  </SCRIPT>
</head>
<body class=interface <s:if test="#request.editType=='SHOW'||#request.editType=='DELETE'||#request.editTypeOther=='SHOWTASK'">
		 onload="initPage();initSet();readonlyAllInput();"
	</s:if>
	<s:else>
		onload="initPage();initSet();"
	</s:else>>
	<s:if test="nodeType == 'certi'">
		<form name=fm action="${ctx}/certify/certifyEditPost.do" method="post" onsubmit="return validateForm(this);">
			<s:if test="(#request.editType=='ADD'||#request.editType=='EDIT') && #request.prpLcertifyCollect.status!=4 ">
				<s:token></s:token>
			</s:if>
	</s:if>
	<s:else>
		<form name=fm action="${ctx}/certify/certifySavePost.do" method="post" onsubmit="return validateForm(this);">
	</s:else>
	<%-- 1.单证主信息 --%>
	<%@include file="/pages/common/certify/CertifyMainEdit.jsp"%>
	<%-- 2.单证主信息 --%>
	<%@include file="/pages/common/certify/PrpLqualityCheckEdit.jsp"%>
	<%-- 4.报案信息补充说明 --%>
	<%@include file="/pages/common/regist/RegistExtEdit.jsp"%>
	<%-- 5.巨灾代码--%>
	<%@include file="/pages/common/claim/ClaimKelpInfo.jsp"%>
	<s:if test="#request.editTypeOther!='SHOWTASK'">
		<s:if test="#request.nodeType=='check'||#request.nodeType=='certa'||#request.nodeType=='verif'">
			<table cellpadding="0" cellspacing="0" class="common">
				<tr>
					<td class=button style="width: 20%" align="center">
						<!--保存按钮-->
						<input type="button" name=buttonSave class='button' value="<s:text name='button.close.value' />" onclick="javascript:window.close();">
					</td>
				</tr>
			</table>
		</s:if>
		<s:else>
			<%@include file="/pages/common/certify/CertifySave.jsp"%>
		</s:else>
	</s:if>
	</form>
</body>
</html>
