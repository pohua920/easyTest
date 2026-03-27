<%--
****************************************************************************
* DESC       ：查询合同帐单页面
* AUTHOR     ：liuyang
* CREATEDATE ：2003-12-29 
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app"%>
<html:html>
<head>
<app:css />
<title>QueryFzAcc</title>
<script language="javascript">
	function submitForm() {
		opener.QueryForContract(fm.type.value, fm.classcode.value,
				fm.uwyear.value);
		window.close();
	}
</script>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginhigh="0">
	<form name="fm">
		<table width="100%" height="100%" width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td align="center">
					<table width="510" border="0" cellpadding="0" cellspacing="0" align="center">
						<tr>
							<td width="172" height="30" align="center" background="/claim/images/bgBarBig.gif" class="button">
								<font color="#FFFFFF" style='font-size: 12px'><b>查询对内帐单</b></font>
							</td>
							<td width="328" background="/claim/images/bgBarHorizontalLine.gif">&nbsp;</td>
							<td align="right">
								<img src="/claim/images/bgClaimBlueRightNode.gif" width="10" height="30">
							</td>
						</tr>
					</table>
					<table width="510" border="0" cellpadding="0" cellspacing="0" align="center">
						<tr>
							<td width="6" height="130" background="/claim/images/bgBarVerticalLine1.gif"></td>
							<td>
								<table width="494" height="100%" border="0" cellpadding="0" cellspacing="1">
									<tr>
										<td bgcolor="EFFAFF">
											<table align="center" border="0" cellpadding="5" cellspacing="0">
												<tr>
													<td class="title2">
														<s:text name="archive.riskClass" />
														：
														<%--险类--%>
														<select name="classcode">
															<option>
																<s:text name="pub.pleaseSelectClassCode" />
															</option>
															<%-- ---请选择险类代码--- --%>
															<option value="Q">Q</option>
															<option value="A">A</option>
														</select>
													</td>
													<td class="title2">
														<s:text name="pub.annual" />
														：
														<%--年度--%>
														<select name="uwyear">
															<option>
																<s:text name="pub.pleaseSelectAnnual" />
															</option>
															<%-- ---请选择年度--- --%>
															<option value="2000">2000</option>
															<option value="2001">2001</option>
															<option value="2002">2002</option>
															<option value="2003">2003</option>
														</select>
													</td>
												</tr>
												>
												<tr>
													<input type="hidden" name="type" value="<%=request.getParameter("type")%>">
													<td class="title2" width="50%" align="center">
														<input type="submit" class="button1" onclick="submitForm();" value="<s:text name='button.submit.value' />">
													</td>
													<td class="title2" width="50%" align="center">
														<input type="reset" class="button1" value="<s:text name='button.reset.value' />">
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</td>
							<td width="10" background="/claim/images/bgBarVerticalLine2.gif"></td>
						</tr>
					</table>
					<table width="510" border="0" cellpadding="0" cellspacing="0" align="center">
						<tr>
							<td>
								<img src="/claim/images/bgBlueLeftNode.gif" width="6" height="17">
							</td>
							<td width="494" align="center" valign="middle" background="/claim/images/bgBlueLine.gif"></td>
							<td align="right">
								<img src="/claim/images/bgBlueRightNode.gif" width="10" height="17">
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</form>
</body>
</html:html>
