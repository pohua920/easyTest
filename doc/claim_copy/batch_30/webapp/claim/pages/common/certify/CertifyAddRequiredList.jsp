<%--
****************************************************************************
* DESC       ：索赔清单显示及打印页面
* AUTHOR     ： zhaolu
* CREATEDATE ： 2006-08-07 
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
	<head>
		<link rel="stylesheet" type="text/css" href="${ctx }/css/Standard.css">
		<script src="${ctx }/pages/DAA/certify/js/DAACertifyEdit.js"></script>
			<%@include file="/common/meta_js.jsp"%>
		<SCRIPT>
	function exit() {
		window.opener.location.reload();
	}
  </SCRIPT>
	</head>
	<body <c:if test="${param.nodeType=='certi'}">onunload="exit();"</c:if>>
		<form name=fm action="${ctx }/certifySave.do" method="post">
			<table width="92%" align="center" cellspacing="0" cellpadding="0" border="0">
				<tr height="40">
					<td align=top align=center style="font-family: 宋体; font-size: 14pt;">
						<B>${riskName }<s:text name="certify.stateNote" /> </B>
						<%--索赔须知--%>
					</td>
				</tr>
			</table>
			<br>
			<table width="92%" align="center" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td colspan=4 height="20">
						<blockquote>
							<p>
								${riskName }
								<s:text name="prompt.certify.stateNoteDetail(connected to)" />
							</p>
							<%--索赔材料手续明细如下：（接上页）--%>
						</blockquote>
					</td>
				</tr>
				<br>
				<c:set var="index" value="${index}" scope="page" />
				<s:set var="certifyDtoCount" value="0" scope="page" />
				<s:if test="#attr.certifyDto.prpLcertifyDirectList!=null">
					<s:set var="certifyDtoCount" value="#attr.certifyDto.prpLcertifyDirectList.size()" scope="page" />
				</s:if>
				<s:iterator begin="25" end="#attr.certifyDtoCount" step="1" var="i" status="">
					<s:set var="strTypeName" value="#attr.certifyDto.prpLcertifyDirectList.get(i).typeName" scope="page" />
					<s:if test="#attr.strTypeName!=''">
						<tr height="20">
							<td colspan=4>
								<blockquote>
									<p>
										${index}.${strTypeName }
									</p>
									<c:set var="index" value="${index+1}" scope="page" />
								</blockquote>
							</td>
						</tr>
					</s:if>
				</s:iterator>
				<input type="hidden" name="nodeType" value="CertifDirect">
			</table>
		</form>
		<jsp:include page="/pages/common/print/PrintButton.jsp" />
	</body>
	<!--这个函数是调动所能用到的通用js的过程，一般包括最常用的js的函数声明都在meta_js.jsp中-->
</html>