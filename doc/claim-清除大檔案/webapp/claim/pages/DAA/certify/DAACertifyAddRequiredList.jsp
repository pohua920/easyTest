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
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<%@include file="/common/meta_js.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx }/css/Standard.css">
<script src="${ctx }/pages/DAA/certify/js/DAACertifyEdit.js"></script>
<SCRIPT>
	function exit() {
		window.opener.location.reload();
	}
</SCRIPT>
</head>
<s:if test="#parameters.nodeType=='certi'">
	<body onUnload="exit();">
</s:if>
<s:else>
	<body>
</s:else>
<form name=fm action="${ctx }/certifySave.do" method="post">
	<table width="92%" align="center" cellspacing="0" cellpadding="0" border="0">
		<tr height="40">
			<td align=top align=center style="font-family: 宋体; font-size: 14pt;">
				<B><s:text name="prompt.certify.motorInsuranceNotice" /><B>
						<%-- 机动车辆保险索赔须知 --%>
			</td>
		</tr>
	</table>
	<s:set var="certifyDtoCount" value="0" scope="page" />
	<s:if test="#attr.certifyDto.prpLcertifyDirectList!=null">
		<s:set var="certifyDtoCount" value="#attr.certifyDto.prpLcertifyDirectList.size()" scope="page" />
	</s:if>
	<c:set var="index" value="${index}" scope="page" />
	<br>
	<table width="92%" align="center" cellspacing="0" cellpadding="0" border="0">
		<tr>
			<td colspan=4 height="20">
				<blockquote>
					<p>
						<s:text name="prompt.certify.motorVehicleDetail(connected to)" />:
						<%-- 机动车辆索赔材料手续明细如下：（接上页） --%>
					</p>
				</blockquote>
			</td>
		</tr>
		<br>
		<s:set var="strTypeCode" value="" scope="page" />
		<s:set var="strTypeName" value="" scope="page" />
		<s:set var="strTypeCode_i" value="" scope="page" />
		<s:set var="strTypeName_i" value="" scope="page" />
		<s:iterator begin="25" end="#attr.certifyDtoCount" step="1" var="i" status="prpLcertifyDirect_status">
			<s:set var="strTypeCode" value="#attr.certifyDto.prpLcertifyDirectList.get(#i).typeCode.substring(0,2)" scope="page" />
			<s:set var="strTypeName" value="#attr.certifyDto.prpLcertifyDirectList.get(#i).typeName" scope="page" />
			<s:if test="#attr.strTypeName!=''">
				<s:if test="#attr.strTypeCode=='01'">
					<tr height="20">
						<td colspan=4>
							<blockquote>
								<p>
									${index }. 《<s:text name="prompt.certify.motorInsApplication" />》
									<%-- 机动车辆保险索赔申请书 --%>
								</p>
								<c:set var="index" value="${index+1}" scope="page" />
							</blockquote>
						</td>
					</tr>
				</s:if>
				<s:elseif test="#attr.strTypeCode=='02'">
					<tr height="20">
						<td colspan=4>
							<blockquote>
								<p>
									${index }.
									<s:text name="prompt.certify.motorPolicyOriginal" />
									<%-- 机动车辆保险单正本 --%>
								</p>
								<c:set var="index" value="${index+1}" scope="page" />
							</blockquote>
						</td>
					</tr>
				</s:elseif>
				<s:elseif test="#attr.strTypeCode=='03'">
					<s:if test="#attr.strTypeCode_i!='03'">
						<tr height="20">
							<td colspan=4>
								<blockquote>
									<p>
										${index }.
										<s:text name="prompt.certify.accIssuedDepartment" />：
									</p>
									<%-- 事故处理部门出具的 --%>
									<c:set var="index" value="${index+1}" scope="page" />
								</blockquote>
							</td>
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
					<s:if test="#attr.strTypeCode_i!='04'">
						<tr height="20">
							<td colspan=4>
								<blockquote>
									<p>
										${index }.
										<s:text name="prompt.certify.issArbitratInstitute" />：
									</p>
									<%-- 法院、仲裁机构出具的 --%>
									<c:set var="index" value="${index+1}" scope="page" />
								</blockquote>
							</td>
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
					request.setAttribute("CLASSCODE_D_A", com.sinosoft.claim.common.ConstantCodes.CLASSCODE_D_A);
					request.setAttribute("CLASSCODE_D_B", com.sinosoft.claim.common.ConstantCodes.CLASSCODE_D_B);
				%>
				<s:elseif test="#attr.strTypeCode==#attr.CLASSCODE_D_A||#attr.strTypeCode==#attr.CLASSCODE_D_B">
					<s:if test="#attr.i==0&&(#attr.strTypeCode_i!=#attr.CLASSCODE_D_A&&#attr.strTypeCode_i!=#attr.CLASSCODE_D_B)">
						<tr height="20">
							<td colspan=4>
								<blockquote>
									<p>
										${index }.<s:text name="prompt.certify.invoVehicleProvide" />：
									</p>
									<%-- 涉及车辆损失还需提供 --%>
									<c:set var="index" value="${index+1}" scope="page" />
								</blockquote>
							</td>
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
					<s:if test="#attr.strTypeCode_i!='06'">
						<tr height="20">
							<td colspan=4>
								<blockquote>
									<p>
										${index }.<s:text name="prompt.certify.invoDamageProvide" />：
									</p>
									<%-- 涉及财产损失还需提供 --%>
									<c:set var="index" value="${index+1}" scope="page" />
								</blockquote>
							</td>
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
					<s:if test="#attr.i==25||#attr.strTypeCode_i!='07'">
						<tr height="20">
							<td colspan=4>
								<blockquote>
									<p>
										${index }.<s:text name="prompt.certify.bodilyResidualProvide" />：
									</p>
									<%-- 涉及人身伤、残、亡损失还需提供 --%>
									<c:set var="index" value="${index+1}" scope="page" />
								</blockquote>
							</td>
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
					<s:if test="#attr.i==25||#attr.strTypeCode_i!='08'">
						<tr height="20">
							<td colspan=4>
								<blockquote>
									<p>
										${index }.<s:text name="prompt.certify.invoDaoQiangProvide" />：
									</p>
									<%-- 涉及车辆盗抢案件还需提供 --%>
									<c:set var="index" value="${index+1}" scope="page" />
								</blockquote>
							</td>
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
					<s:if test="#attr.i==25||#attr.strTypeCode_i!='09'">
						<tr height="20">
							<td colspan=4>
								<blockquote>
									<p>
										${index }.<s:text name="prompt.certify.casesStillProvide" />：
									</p>
									<%-- 涉及车辆自燃的案件还需提供 --%>
									<c:set var="index" value="${index+1}" scope="page" />
								</blockquote>
							</td>
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
					<s:if test="#attr.i==25||#attr.strTypeCode_i!='10'">
						<tr height="20">
							<td colspan=4>
								<blockquote>
									<p>
										${index }.<s:text name="prompt.certify.insInsuranceCompany" />：
									</p>
									<%-- 被保险人索赔时，还须提供以下证件原件，经保险公司验证後留存复印件 --%>
									<c:set var="index" value="${index+1}" scope="page" />
								</blockquote>
							</td>
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
					<s:if test="#attr.i==25||#attr.strTypeCode_i!='11'">
						<tr height="20">
							<td colspan=4>
								<blockquote>
									<p>
										${index }.<s:text name="prompt.certify.insProvideCompany" />：
									</p>
									<%-- 被保险人领取赔款时，须提供以下材料和证件，经保险公司验证後留存复印件 --%>
									<c:set var="index" value="${index+1}" scope="page" />
								</blockquote>
							</td>
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
					<s:if test="#attr.i==25||#attr.strTypeCode_i!='99'">
						<tr height="20">
							<td colspan=4>
								<blockquote>
									<p>
										${index }.<s:text name="prompt.certify.proClaimDocument" />：
									</p>
									<%-- 需要提供的其它索赔证明和单据 --%>
									<c:set var="index" value="${index+1}" scope="page" />
								</blockquote>
							</td>
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
			</s:if>
			<s:set var="strTypeCode_i" value="#attr.strTypeCode" scope="page" />
			<s:set var="strTypeName_i" value="#attr.strTypeName" scope="page" />
		</s:iterator>
	</table>
	<input type="hidden" name="nodeType" value="CertifDirect">
	<jsp:include page="/pages/common/print/PrintButton.jsp" />
</form>
</body>
</html>