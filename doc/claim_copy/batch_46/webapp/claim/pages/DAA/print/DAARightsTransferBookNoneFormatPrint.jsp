<%--
****************************************************************************
* DESC       ：权益转让书页面
* AUTHOR     ：caopeng
* CREATEDATE ：2005-12-08
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=gbk"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app"%>
<%-- 初始化 --%>
<%@include file="DAARightsTransferBookNoneFormatPrintIni.jsp"%>
<html>
<head>
<title>机动车辆保险索赔申请书列印</title>
<link rel="stylesheet" type="text/css" href="Standard.css">
</head>
<body bgcolor="#FFFFFF">
	<table width="660" align="center" cellspacing="0" cellpadding="0" border="0" style="font-family: 宋体; font-size: 10pt;">
		<tr height=30>
			<td width="100%" align="center" style="text-align: center; font-family: 宋体; font-size: 14pt;">
				<img src="/claim/images/LOGO.jpg" />
			</td>
		</tr>
		<tr height=30>
			<td height="35" align=center valign="middle" style="font-family: 宋体; font-size: 14pt;">
				<center>
					<p>
						<span class="style6"><strong>权益转让书</strong></span>
					</p>
				</center>
			</td>
		</tr>
		<tr height=30>
			<td align=center valign="middle">
				<table width="631" border="1" cellpadding="0" cellspacing="0" bordercolor="111111" style="font-family: 宋体; font-size: 10pt;">
					<tr>
						<td width="627">
							<table width="627" border="0" cellpadding="0" cellspacing="0" bordercolor="0">
								<tr>
									<td width="627">
										&nbsp;&nbsp;
										<div align="left">
											&nbsp;&nbsp;<span><b> 财产保险有限公司<ins>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</ins>公司：
											</b></span>
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<p align="left">&nbsp;</p>
										<p align="left">
											&nbsp;&nbsp;&nbsp;&nbsp;贵公司签发的机动车辆保险第
											<ins>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</ins>
											号保险单所承保
										</p>
										<p align="left">
											的被保险人
											<ins>
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<ins>&nbsp;&nbsp;&nbsp;</ins>
												<ins>&nbsp;&nbsp;&nbsp;</ins>
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											</ins>
											的
											<ins>
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<ins>&nbsp;&nbsp;&nbsp;</ins>
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<ins>&nbsp;&nbsp;</ins>
												&nbsp;&nbsp;&nbsp;
											</ins>
											车（车牌号码
											<ins>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</ins>
											），
										</p>
										<p align="left">
											於
											<ins>
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<ins>&nbsp;&nbsp;</ins>
											</ins>
											年
											<ins>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</ins>
											月
											<ins>
												<ins>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</ins>
											</ins>
											日
											<ins>
												<ins>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</ins>
											</ins>
											时
											<ins>
												<ins>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</ins>
											</ins>
											分在
											<ins>
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<ins>&nbsp;&nbsp;</ins>
											</ins>
											<ins>&nbsp;&nbsp;&nbsp;&nbsp;</ins>
											省（自治区）
											<ins>
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<ins>&nbsp;&nbsp;</ins>
											</ins>
											市（地区）
										</p>
										<p align="left">
											<ins>
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<ins>&nbsp;&nbsp;</ins>
											</ins>
											县（区）
											<ins>
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<ins>&nbsp;&nbsp;</ins>
											</ins>
											<ins>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</ins>
											地段发生
											<ins>
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<ins>&nbsp;&nbsp;</ins>
											</ins>
											<ins>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</ins>
											<ins>
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<ins>&nbsp;&nbsp;</ins>
											</ins>
											<ins>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</ins>
											事故。
										</p>
										<p align="left">
											该事故应由第三方
											<ins>
												<ins>&nbsp;&nbsp;</ins>
											</ins>
											<ins>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</ins>
											<ins>&nbsp;</ins>
											<ins>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</ins>
											<ins>
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<ins>&nbsp;&nbsp;</ins>
											</ins>
											<ins>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</ins>
											<ins>
												<ins>&nbsp;&nbsp;</ins>
											</ins>
											<ins>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</ins>
											<ins>
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<ins>&nbsp;&nbsp;</ins>
											</ins>
											<ins>&nbsp;&nbsp;&nbsp;&nbsp;</ins>
											担负其经济赔偿责任，
										</p>
										<p align="left">
											依据 财产保险有限公司机动车辆保险条款第
											<ins>&nbsp;&nbsp;</ins>
											<ins>
												<ins>&nbsp;&nbsp;</ins>
											</ins>
											<ins>&nbsp;&nbsp;</ins>
											<ins>&nbsp;&nbsp;</ins>
											<ins>
												<ins>&nbsp;&nbsp;</ins>
											</ins>
											<ins>&nbsp;&nbsp;</ins>
											<ins>&nbsp;&nbsp;</ins>
											<ins>
												<ins>&nbsp;&nbsp;</ins>
											</ins>
											<ins>&nbsp;&nbsp;</ins>
											<ins>&nbsp;&nbsp;</ins>
											<ins>
												<ins>&nbsp;&nbsp;&nbsp;&nbsp;</ins>
											</ins>
											<ins>&nbsp;</ins>
											的规定，请贵公司将
										</p>
										<p align="left">
											其经济损失先予赔付。计人民币（大写）
											<ins>&nbsp;&nbsp;</ins>
											<ins>
												<ins>&nbsp;&nbsp;</ins>
											</ins>
											<ins>&nbsp;&nbsp;</ins>
											<ins>&nbsp;&nbsp;</ins>
											<ins>
												<ins>&nbsp;&nbsp;</ins>
											</ins>
											<ins>&nbsp;&nbsp;</ins>
											<ins>&nbsp;&nbsp;</ins>
											<ins>
												<ins>&nbsp;&nbsp;</ins>
											</ins>
											<ins>
												<ins>&nbsp;&nbsp;</ins>
												<ins>&nbsp;</ins>
												<ins>&nbsp;&nbsp;</ins>
												<ins>&nbsp;</ins>
												&nbsp;&nbsp;
											</ins>
											<ins>&nbsp;&nbsp;</ins>
											<ins>
												<ins>&nbsp;&nbsp;&nbsp;&nbsp;</ins>
											</ins>
											<ins>&nbsp;&nbsp;</ins>
											￥
											<ins>
												&nbsp;
												<ins>&nbsp;&nbsp;</ins>
												<ins>&nbsp;</ins>
												&nbsp;&nbsp;&nbsp;
											</ins>
											<ins>&nbsp;&nbsp;</ins>
											<ins>
												<ins>&nbsp;&nbsp;</ins>
											</ins>
											<ins>&nbsp;&nbsp;</ins>
											<ins>&nbsp;&nbsp;</ins>
											<ins>
												<ins>&nbsp;&nbsp;</ins>
											</ins>
											<ins>&nbsp;&nbsp;</ins>
										</p>
										<p align="left">现将追偿权转让给贵公司，並协助贵公司共同向第三方进行追偿损失。</p>
										<p align="center">&nbsp;</p>
										<p align="center">&nbsp;</p>
									</td>
								</tr>
								<tr>
									<td>
										&nbsp;&nbsp;&nbsp;&nbsp;
										<p align="left">此致</p>
										<p>&nbsp;</p>
										<p>&nbsp;</p>
										<p>&nbsp;</p>
										<p>&nbsp;</p>
										<p>&nbsp;</p>
										<p align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;被保险人（签章）：</p>
										<p align="center">&nbsp;</p>
										<p align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;&nbsp;日</p>
										<p align="center">&nbsp;</p>
										<p align="center">&nbsp;</p>
										<p align="left">&nbsp;</p>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
				<p align="left">&nbsp;&nbsp;附件：</p>
			</td>
		</tr>
	</table>
	<%-- include列印按钮 --%>
	<jsp:include page="/common/print/PrintButton.jsp" />
	<script language='javascript'>
		function printPage() {
			//add print liudaoping 2013-04-15
			//alert("【列印】功能屬於客制化需求，暫未開發，請知悉！");
			return false;
			divButton.style.display = "none";
			window.print();
		}
	</script>
</body>
</html>
