<%--
****************************************************************************
* DESC	   ：索赔资料清单修改页面
* AUTHOR	 ：理赔组
* CREATEDATE ：2005-03-25
* MODIFYLIST ：   Name	   Date			Reason/Contents
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<!--对title处理-->
<title><s:text name="title.certifyBeforeEdit.claimInformationList" /></title>
<%--索赔资料清单--%>
<%@include file="/common/meta_js.jsp"%>
<%@include file="/common/i18njs.jsp"%>
<%-- 页面样式  --%>
<link rel="stylesheet" type="text/css" href="${ctx }/css/Standard.css">
<script src="${ctx }/pages/DAA/certify/js/DAACertifyEdit.js"></script>
<SCRIPT>
	function exit(){
		window.opener.location.reload();
	}
</SCRIPT>
</head>
<body <c:if test="${nodeType=='certi' }"> onunload="exit();"</c:if>>
	<form name=fm action="${ctx }/certify/certifySavePost.do" method="post">
		<input type="hidden" name="riskCode" value="${riskCode }">
		<table border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td colspan=4 class="formtitle">
					<s:text name="certify.claimInformationList" />
				</td>
			</tr>
			<%--索赔资料清单--%>
			<tr>
				<td class="title">
					<s:text name="db.prpLcheckExt.registNo" />:
				</td>
				<%--报案号码--%>
				<td class="input">
					<input type="text" name="prpLcertifyCollectBusinessNo" class="readonly" readonly="true" value="${prpLcertifyCollect.id.businessNo }">
				</td>
				<td class="title">
					<s:text name="db.prpLcheckExt.policyNo" />:
				</td>
				<%--保单号码--%>
				<td class="input">
					<input type="text" name="prpLcertifyCollectPolicyNo" class="readonly" readonly="true" value="${prpLcertifyCollect.policyNo}">
				</td>
			</tr>
		</table>
		<table border="0" cellpadding="5" cellspacing="1" class="common">
			<s:iterator value="#attr.imageTypeMap" var="imageType">
				<tr>
					<td class="centertitle" style="width: 100%" colspan="6">
						<s:property value="#attr.certifyTypeList[#imageType.key]" />
					</td>
				</tr>
				<tr>
					<td class="subformtitle" style="width: 10%">
						<s:text name="certainLoss.prpLcertifyCollect.requireSign" />
					</td>
					<%--需要标志--%>
					<td class="subformtitle" style="width: 60%">
						<s:text name="certainLoss.prpLcertifyCollect.billType" />
					</td>
					<%--清单类型--%>
				</tr>
				<s:set var="imageTypeListSize" value="0" scope="page" />
				<s:if test="#attr.imageTypeList!=null">
					<s:set var="imageTypeListSize" value="#attr.imageTypeList.size()" scope="page" />
					<s:iterator var="prpDcodeDto" value="#imageType.value" status="prpDcodeDto_status">
						<s:set var="requireUploadFlag" value="" scope="page" />
						<s:set var="requireDisabledFlag" value="" scope="page" />
						<s:set var="requireTxt" value="" scope="page" />
						<s:if test="#attr.prpLcertifyDirect.certifyDirectList!=null&&#attr.prpLcertifyDirect.certifyDirectList.size()>0">
							<s:iterator var="prpLcertifyDirectTemp" value="#attr.prpLcertifyDirect.certifyDirectList">
								<s:if test="#attr.prpDcodeDto.id.codeCode==#attr.prpLcertifyDirectTemp.typeCode">
									<s:set var="requireUploadFlag" value="'checked'" scope="page" />
									<s:set var="requireDisabledFlag" value="'disabled'" scope="page" />
									<s:set var="requireTxt" value="#attr.prpDcodeDto.id.codeCode" scope="page" />
								</s:if>
							</s:iterator>
						</s:if>
						<tr>
							<td class="input" style="width: 10%">
								<input type="checkbox" name="prpLcertifyDirect" ${requireUploadFlag } onClick="return fcDirectCodeChange(this);">
								<input type="hidden" name="prpLcertifyDirectCode" value="${requireTxt }">
								<input type="hidden" name="code" value="${prpDcodeDto.id.codeCode }">
								<input type="hidden" name="prpLcertifyDirectLossItemCode" value="${prpDcodeDto_status.count }">
							</td>
							<td class="input" style="width: 90%">
								<input type="text" name="prpLcertifyDirectTypeName" class="readonly" readonly="true" value="${prpDcodeDto.codeCName}">
							</td>
						</tr>
					</s:iterator>
				</s:if>
			</s:iterator>
		</table>
		<table cellpadding="0" cellspacing="0" class="common">
			<tr>
				<td class="button" align="center">
					<!--保存-->
					<input type="button" name=buttonSave class='button' value="保存" onclick="return saveCertifyDirect();">
					&nbsp;&nbsp;
					<!--關閉-->
					<input type="button" name=buttonClose class='button' value="<s:text name='button.close.value' />" onclick="javascript:window.close();">
				</td>
			</tr>
		</table>
		<input type="hidden" name="classCount" value="1">
		<input type="hidden" name="nodeType" value="CertifDirect">
		<input type="hidden" name="imageTypeListSize" value="${imageTypeListSize }">
	</form>
</body>
</html>
