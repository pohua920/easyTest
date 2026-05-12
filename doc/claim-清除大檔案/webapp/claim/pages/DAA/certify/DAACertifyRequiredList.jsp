<%--
****************************************************************************
* DESC       ：索赔清单显示及打印页面
* AUTHOR     ： luqin
* CREATEDATE ： 2005-07-21 
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<%@include file="/common/meta_js.jsp"%>
<%@include file="/common/i18njs.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx }/css/Standard.css">
<script src="${ctx }/pages/DAA/certify/js/DAACertifyEdit.js"></script>
<SCRIPT>
	function exit() {
		window.opener.location.reload();
	}
</SCRIPT>
<body <c:if test="${nodeType=='certi'}">onunload="exit();"</c:if>>
	<form name=fm action="${ctx }/certify/certifyEditPost.do" method="post">
		<table width="92%" align="center" cellspacing="0" cellpadding="0" border="0">
			<tr height="40">
				<td align=top align=center style="font-family: 宋体; font-size: 14pt;">
					<B><s:text name="prompt.certify.motorInsuranceNotic" /><B> <%-- 机动车辆保险索赔须知 --%>
				</td>
			</tr>
		</table>
		<br>
		<table width="92%" align="center" cellspacing="0" cellpadding="0" border="0">
			<tr>
				<td colspan=4 height="20">
					<p>
						<ins>${prpCmainDto.insuredName }</ins>
						：
					</p>
				</td>
			</tr>
			<tr>
				<td colspan=4 height="20">
					<p>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<s:text name="prompt.certify.motorAccident" />
					</p>
				</td>
				<%-- 由於您投保的机动车辆发生了事故，请您在向我公司提交《机动车辆保险索赔申请书》的同时，依照我公司的要求，提供以下有关单证。如果您遇到困难，请随时拨打公司的服务专线电话 “4008817518”，我公司将竭诚为您提供优质、高效的保险服务。 谢谢您的合作！ --%>
			</tr>
			<tr>
				<td colspan=4 height="20">
					<blockquote>
						<p>
							<s:text name="prompt.certify.motorVehicleDetail" />
							：
						</p>
						<%-- 机动车辆索赔材料手续明细如下 --%>
					</blockquote>
				</td>
			</tr>
			<br>
			<s:set var="certifyDtoCount" value="#attr.certifyDtoCount" scope="page" />
			<s:if test="#attr.certifyDtoCount>25">
				<s:set var="certifyDtoCount" value="25" scope="page" />
			</s:if>
			<c:set var="index" value="1" scope="page" />
			<s:set var="strTypeCode_1" value="" scope="page" />
			<s:set var="strTypeName_1" value="" scope="page" />
			<s:iterator var="prpLcertifyDirectDto" value="#attr.certifyDto.prpLcertifyDirectList" status="certify_status">
				<s:set var="strTypeCode" value="#prpLcertifyDirectDto.typeCode.substring(0,2)" scope="page" />
				<s:set var="strTypeName" value="#prpLcertifyDirectDto.typeName" scope="page" />
				<s:if test="#attr.strTypeName!=''">
					<s:if test="#attr.strTypeCode=='01'">
						<tr height="20">
							<td colspan=4>
								<blockquote>
									<p>
										${index }. 《
										<s:text name="prompt.certify.motorInsApplication" />
										》
									</p>
									<c:set var="index" value="${index+1}" scope="page" />
								</blockquote>
							</td>
							<%-- 机动车辆保险索赔申请书 --%>
						</tr>
					</s:if>
					<s:elseif test="#attr.strTypeCode=='02'">
						<tr height="20">
							<td colspan=4>
								<blockquote>
									<p>
										${index }.
										<s:text name="prompt.certify.motorPolicyOriginal" />
									</p>
									<c:set var="index" value="${index+1}" scope="page" />
								</blockquote>
							</td>
							<%-- 机动车辆保险单正本 --%>
						</tr>
					</s:elseif>
					<s:elseif test="#strTypeCode=='03'">
						<s:if test="#certify_status.index==0||#attr.strTypeCode_1!='03'">
							<tr height="20">
								<td colspan=4>
									<blockquote>
										<p>
											${index }.
											<s:text name="prompt.certify.accIssuedDepartment" />
											：
										</p>
										<c:set var="index" value="${index+1}" scope="page" />
									</blockquote>
								</td>
								<%-- 事故处理部门出具的 --%>
							</tr>
							<tr height="20">
								<td colspan=4>
									<blockquote>
										<blockquote>
											<p>${strTypeName }</p>
										</blockquote>
									</blockquote>
								</td>
							</tr>
						</s:if>
						<s:else>
							<tr height="20">
								<td colspan=4>
									<blockquote>
										<blockquote>
											<p>${strTypeName }</p>
										</blockquote>
									</blockquote>
								</td>
							</tr>
						</s:else>
					</s:elseif>
					<s:elseif test="#attr.strTypeCode=='04'">
						<s:if test="#certify_status.index==0||#attr.strTypeCode_1!='04'">
							<tr height="20">
								<td colspan=4>
									<blockquote>
										<p>
											${index }.
											<s:text name="prompt.certify.issArbitratInstitute" />
											：
										</p>
										<c:set var="index" value="${index+1}" scope="page" />
									</blockquote>
								</td>
								<%-- 法院、仲裁机构出具的 --%>
							</tr>
							<tr height="20">
								<td colspan=4>
									<blockquote>
										<blockquote>
											<p>${strTypeName }</p>
										</blockquote>
									</blockquote>
								</td>
							</tr>
						</s:if>
						<s:else>
							<tr height="20">
								<td colspan=4>
									<blockquote>
										<blockquote>
											<p>${strTypeName }</p>
										</blockquote>
									</blockquote>
								</td>
							</tr>
						</s:else>
					</s:elseif>
					<%
						request.setAttribute("CLASSCODE_D_A",com.sinosoft.claim.common.ConstantCodes.CLASSCODE_D_A);
						request.setAttribute("CLASSCODE_D_B",com.sinosoft.claim.common.ConstantCodes.CLASSCODE_D_B);
					%>
					<s:elseif test="#attr.strTypeCode==#attr.CLASSCODE_D_A||#attr.strTypeCode==#attr.CLASSCODE_D_B">
						<s:if test="#certify_status.index==0||(#attr.strTypeCode_1!=#attr.CLASSCODE_D_A&&#attr.strTypeCode_1!=#attr.CLASSCODE_D_B)">
							<tr height="20">
								<td colspan=4>
									<blockquote>
										<p>
											${index }.<s:text name="prompt.certify.invoVehicleProvide" />：
										</p>
										<c:set var="index" value="${index+1}" scope="page" />
									</blockquote>
								</td>
								<%-- 涉及车辆损失还需提供 --%>
							</tr>
							<tr height="20">
								<td colspan=4>
									<blockquote>
										<blockquote>
											<p>${strTypeName }</p>
										</blockquote>
									</blockquote>
								</td>
							</tr>
						</s:if>
						<s:else>
							<tr height="20">
								<td colspan=4>
									<blockquote>
										<blockquote>
											<p>${strTypeName }</p>
										</blockquote>
									</blockquote>
								</td>
							</tr>
						</s:else>
					</s:elseif>
					<s:elseif test="#attr.strTypeCode=='06'">
						<s:if test="#certify_status.index==0||#attr.strTypeCode_1!='06'">
							<tr height="20">
								<td colspan=4>
									<blockquote>
										<p>
											${index }.<s:text name="prompt.certify.invoDamageProvide" />：
										</p>
										<c:set var="index" value="${index+1}" scope="page" />
									</blockquote>
								</td>
								<%-- 涉及财产损失还需提供 --%>
							</tr>
							<tr height="20">
								<td colspan=4>
									<blockquote>
										<blockquote>
											<p>${strTypeName }</p>
										</blockquote>
									</blockquote>
								</td>
							</tr>
						</s:if>
						<s:else>
							<tr height="20">
								<td colspan=4>
									<blockquote>
										<blockquote>
											<p>${strTypeName }</p>
										</blockquote>
									</blockquote>
								</td>
							</tr>
						</s:else>
					</s:elseif>
					<s:elseif test="#attr.strTypeCode=='07'">
						<s:if test="#certify_status.index==0||#attr.strTypeCode_1!='07'">
							<tr height="20">
								<td colspan=4>
									<blockquote>
										<p>
											${index }.<s:text name="prompt.certify.bodilyResidualProvide" />：
										</p>
										<c:set var="index" value="${index+1}" scope="page" />
									</blockquote>
								</td>
								<%-- 涉及人身伤、残、亡损失还需提供 --%>
							</tr>
							<tr height="20">
								<td colspan=4>
									<blockquote>
										<blockquote>
											<p>${strTypeName }</p>
										</blockquote>
									</blockquote>
								</td>
							</tr>
						</s:if>
						<s:else>
							<tr height="20">
								<td colspan=4>
									<blockquote>
										<blockquote>
											<p>${strTypeName }</p>
										</blockquote>
									</blockquote>
								</td>
							</tr>
						</s:else>
					</s:elseif>
					<s:elseif test="#attr.strTypeCode=='08'">
						<s:if test="#certify_status.index==0||#attr.strTypeCode_1!='08'">
							<tr height="20">
								<td colspan=4>
									<blockquote>
										<p>
											${index }.<s:text name="prompt.certify.invoDaoQiangProvide" />：
										</p>
										<c:set var="index" value="${index+1}" scope="page" />
									</blockquote>
								</td>
								<%-- 涉及车辆盗抢案件还需提供 --%>
							</tr>
							<tr height="20">
								<td colspan=4>
									<blockquote>
										<blockquote>
											<p>${strTypeName }</p>
										</blockquote>
									</blockquote>
								</td>
							</tr>
						</s:if>
						<s:else>
							<tr height="20">
								<td colspan=4>
									<blockquote>
										<blockquote>
											<p>${strTypeName }</p>
										</blockquote>
									</blockquote>
								</td>
							</tr>
						</s:else>
					</s:elseif>
					<s:elseif test="#attr.strTypeCode=='09'">
						<s:if test="#certify_status.index==0||#attr.strTypeCode_1!='09'">
							<tr height="20">
								<td colspan=4>
									<blockquote>
										<p>
											${index }.<s:text name="prompt.certify.casesStillProvide" />：
										</p>
										<c:set var="index" value="${index+1}" scope="page" />
									</blockquote>
								</td>
								<%-- 涉及车辆自燃的案件还需提供 --%>
							</tr>
							<tr height="20">
								<td colspan=4>
									<blockquote>
										<blockquote>
											<p>${strTypeName }</p>
										</blockquote>
									</blockquote>
								</td>
							</tr>
						</s:if>
						<s:else>
							<tr height="20">
								<td colspan=4>
									<blockquote>
										<blockquote>
											<p>${strTypeName }</p>
										</blockquote>
									</blockquote>
								</td>
							</tr>
						</s:else>
					</s:elseif>
					<s:elseif test="#attr.strTypeCode=='10'">
						<s:if test="#certify_status.index==0||#attr.strTypeCode_1!='10'">
							<tr height="20">
								<td colspan=4>
									<blockquote>
										<p>
											${index }.<s:text name="prompt.certify.insInsuranceCompany" />：
										</p>
										<c:set var="index" value="${index+1}" scope="page" />
									</blockquote>
								</td>
								<%-- 被保险人索赔时，还须提供以下证件原件，经保险公司验证後留存复印件 --%>
							</tr>
							<tr height="20">
								<td colspan=4>
									<blockquote>
										<blockquote>
											<p>${strTypeName }</p>
										</blockquote>
									</blockquote>
								</td>
							</tr>
						</s:if>
						<s:else>
							<tr height="20">
								<td colspan=4>
									<blockquote>
										<blockquote>
											<p>${strTypeName }</p>
										</blockquote>
									</blockquote>
								</td>
							</tr>
						</s:else>
					</s:elseif>
					<s:elseif test="#attr.strTypeCode=='11'">
						<s:if test="#certify_status.index==0||#attr.strTypeCode_1!='11'">
							<tr height="20">
								<td colspan=4>
									<blockquote>
										<p>
											${index }.<s:text name="prompt.certify.insProvideCompany" />：
										</p>
										<c:set var="index" value="${index+1}" scope="page" />
									</blockquote>
								</td>
								<%-- 被保险人领取赔款时，须提供以下材料和证件，经保险公司验证後留存复印件 --%>
							</tr>
							<tr height="20">
								<td colspan=4>
									<blockquote>
										<blockquote>
											<p>${strTypeName }</p>
										</blockquote>
									</blockquote>
								</td>
							</tr>
						</s:if>
						<s:else>
							<tr height="20">
								<td colspan=4>
									<blockquote>
										<blockquote>
											<p>${strTypeName }</p>
										</blockquote>
									</blockquote>
								</td>
							</tr>
						</s:else>
					</s:elseif>
					<s:elseif test="#attr.strTypeCode=='99'">
						<s:if test="#certify_status.index==0||#attr.strTypeCode_1!='99'">
							<tr height="20">
								<td colspan=4>
									<blockquote>
										<p>
											${index }.<s:text name="prompt.certify.proClaimDocument" />：
										</p>
										<c:set var="index" value="${index+1}" scope="page" />
									</blockquote>
								</td>
								<%-- 需要提供的其它索赔证明和单据 --%>
							</tr>
							<tr height="20">
								<td colspan=4>
									<blockquote>
										<blockquote>
											<p>${strTypeName }</p>
										</blockquote>
									</blockquote>
								</td>
							</tr>
						</s:if>
						<s:else>
							<tr height="20">
								<td colspan=4>
									<blockquote>
										<blockquote>
											<p>${strTypeName }</p>
										</blockquote>
									</blockquote>
								</td>
							</tr>
						</s:else>
					</s:elseif>
					<s:elseif test="#certify_status.index==24">
						<tr height="20">
							<td colspan=4>
								<blockquote>
									<blockquote>
										<p>
											<s:text name="prompt.certify.nextPageContent" />
										</p>
										<%-- (内容过多，其余内容见下一页) --%>
									</blockquote>
								</blockquote>
							</td>
						</tr>
					</s:elseif>
				</s:if>
				<s:set var="strTypeCode_1" value="#attr.strTypeCode" scope="page" />
				<s:set var="strTypeName_1" value="#attr.strTypeName" scope="page" />
			</s:iterator>
			<c:if test="${certifyDtoCount<25}">
				<c:forEach begin="0" end="${30-certifyDtoCount}" step="1">
					<tr height="20">
						<td colspan=4>
							<blockquote>
								<blockquote>
									<p></p>
								</blockquote>
							</blockquote>
						</td>
					</tr>
				</c:forEach>
			</c:if>
			<tr>
				<td colspan=4 height="20">
					<p>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<s:text name="prompt.certify.moreCompreReason" />
					</p>
				</td>
				<%-- 敬请注意：为确保您能够获得更加全面、合理的保险赔偿，我公司在理赔过程中，可能需要您进一步提供上述所列单证 以外的其他证明材料。届时，我公司将及时通知您。感谢您对我们工作的理解与支持！--%>
			</tr>
			<tr>
				<td colspan=4>
					<hr>
				</td>
			</tr>
		</table>
		<table width="92%" align="center" cellspacing="0" cellpadding="0" border="0">
			<tr height="30">
				<td width="30%">
					<s:text name="certainLoss.prpLcheck.insuredName" />：
				</td>
				<%-- 被保险人 --%>
				<td width="20%">${prpCmainDto.insuredName }</td>
				<td width="30%">
					<s:text name="certify.nsuranceCompany" />：
				</td>
				<%-- 保险公司 --%>
				<td width="20%">${strName }</td>
			</tr>
			<tr height="30">
				<td>
					<s:text name="certify.dateReceivegNotice" />：
				</td>
				<%-- 领到《索赔须知》日期 --%>
				<td>
					<s:text name="certify.dateMonthYear" />
				</td>
				<%-- ____年____月____日 --%>
				<td>
					<s:text name="certify.dateDeliveryNotice" />：
				</td>
				<%-- 交付《索赔须知》日期 --%>
				<td>
					<s:text name="certify.dateMonthYear" />
				</td>
				<%-- ____年____月____日 --%>
			</tr>
			<tr height="30">
				<td>
					<s:text name="certify.confirmSigne" />：
				</td>
				<%-- 确认签字 --%>
				<td></td>
				<td>
					<s:text name="certify.agentSignature" />：
				</td>
				<%-- 经办人签字 --%>
				<td></td>
			</tr>
			<tr height="30">
				<td>
					<s:text name="certify.submitMaterialDate" />：
				</td>
				<%-- 提交索赔材料日期 --%>
				<td>
					<s:text name="certify.dateMonthYear" />
				</td>
				<%-- ____年____月____日 --%>
				<td>
					<s:text name="certify.receivedDateMaterials" />：
				</td>
				<%-- 收到索赔材料日期 --%>
				<td>
					<s:text name="certify.dateMonthYear" />
				</td>
				<%-- ____年____月____日 --%>
			</tr>
			<tr height="30">
				<td>
					<s:text name="certify.confirmSigne" />：
				</td>
				<%-- 确认签字 --%>
				<td></td>
				<td>
					<s:text name="certify.agentSignature" />：
				</td>
				<%-- 经办人签字 --%>
				<td></td>
			</tr>
		</table>
		<br>
		<input type="hidden" name="nodeType" value="CertifDirect">
		<script language='javascript'>
			function printPage() {
				tbButton.style.display = "none";
				//add print liudaoping 2013-04-15
				//alert("【列印】功能屬於客制化需求，暫未開發，請知悉！");
				return false;
				window.print();
			}
		</script>
		<%-- include打印按钮 --%>
		<!--<jsp:include page="/common/print/PrintButton.jsp" />     -->
		<table id="tbButton" cellpadding="0" cellspacing="0" width="80%" style="display:">
			<tr>
				<td class=button style="width: 33%" align="center">
					<input type=button name=buttonPrint value="<s:text name='button.print.value' />" class="button" onclick="return printPage()">
				</td>
				<s:if test="#attr.certifyDto.prpLcertifyDirectList.size()>24">
					<td class=button style="width: 33%" align="center">
						<input type=button name=buttonPage value="<s:text name='button.attached.value' />" class="button" onclick="return certifyDirectAdd('${registNo }','certi','${index }')">
					</td>
					<%-- 附 页 --%>
				</s:if>
				<td class=button style="width: 33%" align="center">
					<input type=button name=buttonClose value="<s:text name='button.close.value' />" class="button" onclick="javascript:window.close()">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>