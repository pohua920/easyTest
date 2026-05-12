<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%--
****************************************************************************
* DESC       ：索赔清单显示及打印页面
* AUTHOR     ： zhaolu
* CREATEDATE ： 2005-08-07 
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<HTML>
	<HEAD>
		<%@ include file="/common/taglibs.jsp"%>
		<%@include file="/common/meta_js.jsp"%>
		<%@include file="/common/i18njs.jsp"%>
		<link href="${ctx }/css/Standard.css" type=text/css rel=stylesheet>
		<script src="${ctx }/pages/DAA/certify/js/DAACertifyEdit.js"></script>
		<SCRIPT>
		  	function exit(){
		      window.opener.location.reload();
		  	}
 	 </script>
	</HEAD>
	<body <c:if test="${nodeType=='certi' }"> onunload="exit();"</c:if>>
		<form action="${ctx }/certify/certifyEditPost.do" method="post" name="fm">
			<table cellSpacing=0 cellPadding=0 width="92%" align=center border=0>
				<TBODY>
					<tr height=40 align=center>
						<td style="FONT-SIZE: 14pt; FONT-FAMILY: 宋体" align=top>
							<B> ${riskName }<s:text name="certify.stateNote" /><B> </B> </B><%--索赔须知--%>
						</td>
					</tr>
				</TBODY>
			</table>
			<br>
			<table cellSpacing=0 cellPadding=0 width="92%" align=center border=0>
				<TBODY>
					<tr>
						<td colSpan=4 height=20>
							<p>
								<ins>
									<s:text name="certify.to" /><%--致--%> ${prpCmainDto.insuredName }
								</ins>
								：
							</p>
						</td>
					</tr>
					<tr>
						<td colSpan=4 height=20>
							<p>
                                    &nbsp;&nbsp;&nbsp;&nbsp;<s:text name="prompt.certify.covered" />${riskName } <%--您所投保的--%>
								<s:text name="prompt.certify.been" /><%--已於--%>${registDto.prpLregist.damageStartDate.year}
                                <s:text name="print.year" /><%--年--%>${registDto.prpLregist.damageStartDate.month}<s:text name="print.month" /><%--月--%>
                                ${registDto.prpLregist.damageStartDate.day}<s:text name="prompt.certify.sunriseNo" /><%--日出险，赔案号码--%>${prpLclaim.claimNo }<s:text name="prompt.certify.claimApplication" /><%--， 为协助赔案的申请，请依本须知下方列所列清单提供以下有关单证。--%>
							</p>
						</td>
					</tr>
					<tr>
						<td colSpan=4 height=20>
							<blockquote>
								<p>
									${riskName } <s:text name="certify.claimMaterialBelow" />：<%--索赔材料明细如下--%>
								</p>
							</blockquote>
						</td>
					</tr>
					<br>
					<s:set var="certifyDtoCount" value="#attr.certifyDtoCount" scope="page" />
					<s:if test="#attr.certifyDtoCount>25">
						<s:set var="certifyDtoCount" value="25" scope="page" />
					</s:if>
					<c:set var="index" value="1" scope="page"/>
					<s:iterator var="prpLcertifyDirect" value="#attr.certifyDto.prpLcertifyDirectList" status="certify_status">
						<s:set var="strTypeCode" value="#prpLcertifyDirect.typeCode.substring(1,3)" scope="page" />
						<s:set var="strTypeName" value="#prpLcertifyDirect.typeName" scope="page" />
						<s:if test="#attr.strTypeName!=''">
							<tr height=20>
								<td colSpan=4>
									<blockquote>
										<p>
											${index }.${strTypeName }
										</p>
										<c:set var="index" value="${index+1}" scope="page"/>
									</blockquote>
								</td>
							</tr>
							<s:if test="#certify_status.index==24">
								<tr height=20>
									<td colSpan=4>
										<blockquote>
											<blockquote>
												<p>
												<s:text name="prompt.certify.nextPageContent" /><%--(内容过多，其余内容见下一页)--%>
												</p>
											</blockquote>
										</blockquote>
									</td>
								</tr>
							</s:if>
						</s:if>
					</s:iterator>
					<c:if test="${certifyDtoCount<25}">
						<c:forEach begin="0" end="${30-certifyDtoCount}" step="1">
							<tr height=20>
								<td colSpan=4>
									<blockquote>
										<blockquote>
											<p>
											</p>
										</blockquote>
									</blockquote>
								</td>
							</tr>
						</c:forEach>
					</c:if>
					<tr>
						<td colSpan=4 height=20>
							<p>
								&nbsp;&nbsp;&nbsp;&nbsp;<s:text name="prompt.certify.kindlyNote" /><%--敬请注意：为确保您能够获得更加全面、合理的保险赔偿，我公司在理赔过程中，可能需要您进一步提供上述所列单证
								以外的其他证明材料。届时，我公司将及时通知您。感谢您对我们工作的理解与支持！--%>
							</p>
						</td>
					</tr>
					<tr>
						<td colSpan=4>
							<hr>
						</td>
					</tr>
				</TBODY>
			</table>
			<table cellSpacing=0 cellPadding=0 width="92%" align=center border=0>
				<TBODY>
					<tr height=30 align=center>
						<td width="20%">
							<s:text name="db.prpLregist.insuredName" />：<%--被保险人--%>
						</td>
						<td width="30%">
							${prpCmainDto.insuredName }
						</td>
						<td width="20%">
							<s:text name="certify.nsuranceCompany" />：<%--保险公司--%>
						</td>
						<td width="30%">
							<s:text name="print.propertyIns" /><%--财产保险有限公司--%>
						</td>
					</tr>
					<tr height=30 align=center>
						<td>
							<s:text name="certify.dateNotification" />：<%--通知日期--%>
						</td>
						<td>
						<s:text name="certify.dateMonthYear" />	<%--____年____月____日--%>
						</td>
						<td>
							<s:text name="certify.dateReceipt" />：<%--收件日期--%>
						</td>
						<td>
							<s:text name="certify.dateMonthYear" />	<%--____年____月____日--%>
						</td>
					</tr>
					<tr height=30 align=center>
						<td>
							<s:text name="certify.signe" />：<%--签字--%>
						</td>
						<td>
						</td>
						<td>
							<s:text name="certify.signe" />：<%--签字--%>
						</td>
						<td>
						</td>
					</tr>
					<tr height=30 align=center>
						<td>
							<s:text name="certify.contactPhoneNo" />：<%--联络电话--%>
						</td>
						<td>
						</td>
						<td>
							<s:text name="certify.contactPhoneNo" />：<%--联络电话--%>
						</td>
						<td>
						</td>
					</tr>
				</TBODY>
			</table>
			<br>
			<input type="hidden" name="nodeType" value="CertifDirect">
			<script language=javascript>
		      function printPage(){
		          //add print liudaoping 2013-04-15
		          //alert("【列印】功能屬於客制化需求，暫未開發，請知悉！");
		          return false;
		        tbButton.style.display = "none";
		        window.print();
		      }
		    </script>
			<%-- include打印按钮 --%>
			<!--<jsp:include page="/common/print/PrintButton.jsp"  />-->
			<table cellSpacing=0 cellPadding=0 width="80%" id="tbButton">
				<TBODY>
					<tr>
						<td style="WIDTH: 33%" align=middle class="button">
							<input type=button name=buttonPrint value="<s:text name='button.print.value' />" class="button" onclick="return printPage()">
						</td>
						<s:if test="#attr.certifyDto.prpLcertifyDirectList.size()>24">
							<td style="WIDTH: 33%" align=middle class="button">
								<input type=button name=buttonPage value="<s:text name='button.attached.value' />" class="button" onclick="return certifyDirectAdd('${registNo }','certi','${index }')"><%--附 页--%>
							</td>
						</s:if>
						<td style="WIDTH: 33%" align=middle class="button">
							<input type=button name=buttonClose value="<s:text name='button.close.value' />" class="button" onclick="javascript:window.close()">
						</td>
					</tr>
				</TBODY>
			</table>
		</form>
	</body>
</HTML>
