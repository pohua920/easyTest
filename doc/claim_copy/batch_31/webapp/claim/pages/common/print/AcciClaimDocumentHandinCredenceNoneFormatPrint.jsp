<%--
****************************************************************************
* DESC       ：人身险索赔文件交接凭证打印
* AUTHOR     ：caopeng
* CREATEDATE ：2005-12-13
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=gbk"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%-- 初始化 --%>
<%@include file="AcciClaimDocumentHandinCredenceNoneFormatPrintIni.jsp"%>
<html>
<head>
<title>人身险索赔文件交接凭证列印</title>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
</head>
<body onLoad="loadForm();">
	<table width="92%" align="center" cellspacing="0" cellpadding="0" border="0">
		<tr>
			<td colspan="3" height="40" align=center style="font-family: 宋体; font-size: 14pt;">
				<img src="/claim/images/LOGO.jpg" />
			</td>
		</tr>
		<tr>
			<td colspan="3" height="40" align=center style="font-family: 宋体; font-size: 14pt;">
				<B>人身险索赔文件交接凭证<B>
			</td>
		</tr>
	</table>
	<table border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#111111" style="border-collapse: collapse; font-family: 宋体; font-size: 10pt;">
		<tr valign="middle">
			<td height="25" colspan="8">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr height="25">
						<td width="50%">
							&nbsp;申请人姓名： <span id="spAcciName"></span>
						</td>
						<td width="50%">
							保单号： <span id="spPolicyNo">&nbsp;</span>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr valign="middle">
			<td height="25" colspan="8">
				<p>&nbsp;本公司收到並留存下列申请文件：（如非对文件形式特别备注，留存形式均为原件）</p>
			</td>
		</tr>
		<tr valign="middle">
			<td width="28%" height="25">
				<p align="center">文件名称
			</td>
			<td width="59" height="25">
				<p align="center">份数
			</td>
			<td height="25" colspan="3">
				<p align="center">形式备注
			</td>
			<td width="172" height="25">
				<p align="center">文件名称
			</td>
			<td width="54" height="25">
				<p align="center">份数
			</td>
			<td width="83" height="25">
				<p align="center">形式备注
			</td>
		</tr>
		<tr valign="middle">
			<td width="186" height="20" id="tdTypeName1">&nbsp;1 保险单</td>
			<td width="59" height="20">&nbsp;</td>
			<td height="20" colspan="3">&nbsp;</td>
			<td width="172" height="20" id="tdTypeName16">&nbsp;16 死亡宣告判决书</td>
			<td width="54" height="20">&nbsp;</td>
			<td width="83" height="20">&nbsp;</td>
		</tr>
		<tr valign="middle">
			<td width="186" height="20" id="tdTypeName2">&nbsp;2 末次和出险当期保费收据</td>
			<td width="59" height="20">&nbsp;</td>
			<td height="20" colspan="3">&nbsp;</td>
			<td width="172" height="20" id="tdTypeName17">&nbsp;17 户口註銷证明</td>
			<td width="54" height="20">&nbsp;</td>
			<td width="83" height="20">&nbsp;</td>
		</tr>
		<tr valign="middle">
			<td width="186" height="20" id="tdTypeName3">&nbsp;3 事故者身份证明</td>
			<td width="59" height="20">&nbsp;</td>
			<td height="20" colspan="3">&nbsp;</td>
			<td width="172" height="20" id="tdTypeName18">&nbsp;18 丧葬证明</td>
			<td width="54" height="20">&nbsp;</td>
			<td width="83" height="20">&nbsp;</td>
		</tr>
		<tr valign="middle">
			<td width="186" height="20" id="tdTypeName4">&nbsp;4 受益人身份证明</td>
			<td width="59" height="20">&nbsp;&nbsp;</td>
			<td height="20" colspan="3">&nbsp;</td>
			<td width="172" height="20" id="tdTypeName19">&nbsp;19 残疾鉴定证明</td>
			<td width="54" height="20">&nbsp;</td>
			<td width="83" height="20">&nbsp;</td>
		</tr>
		<tr valign="middle">
			<td width="186" height="20" id="tdTypeName5">&nbsp;5 受益人与事故者关系证明</td>
			<td width="59" height="20">&nbsp;</td>
			<td height="20" colspan="3">&nbsp;</td>
			<td width="172" height="20" id="tdTypeName20">&nbsp;20 影象检查报告书</td>
			<td width="54" height="20">&nbsp;</td>
			<td width="83" height="20">&nbsp;</td>
		</tr>
		<tr valign="middle">
			<td width="186" height="20" id="tdTypeName6">&nbsp;6 继承人身份证明</td>
			<td width="59" height="20">&nbsp;</td>
			<td height="20" colspan="3">&nbsp;</td>
			<td width="172" height="20" id="tdTypeName21">&nbsp;21 医疗诊断证明书</td>
			<td width="54" height="20">&nbsp;</td>
			<td width="83" height="20">&nbsp;</td>
		</tr>
		<tr valign="middle">
			<td width="186" height="20" id="tdTypeName7">&nbsp;7 继承人与事故者关系证明</td>
			<td width="59" height="20">&nbsp;</td>
			<td height="20" colspan="3">&nbsp;</td>
			<td width="172" height="20" id="tdTypeName22">&nbsp;22 病历</td>
			<td width="54" height="20">&nbsp;</td>
			<td width="83" height="20">&nbsp;</td>
		</tr>
		<tr valign="middle">
			<td width="186" height="20" id="tdTypeName8">&nbsp;8 遗产继承文件</td>
			<td width="59" height="20">&nbsp;</td>
			<td height="20" colspan="3">&nbsp;</td>
			<td width="172" height="20" id="tdTypeName23">&nbsp;23 处方</td>
			<td width="54" height="20">&nbsp;</td>
			<td width="83" height="20">&nbsp;</td>
		</tr>
		<tr valign="middle">
			<td width="186" height="20" id="tdTypeName9">&nbsp;9 索赔委托书</td>
			<td width="59" height="20">&nbsp;</td>
			<td height="20" colspan="3">&nbsp;</td>
			<td width="172" height="20" id="tdTypeName24">&nbsp;24 病理检验报告</td>
			<td width="54" height="20">&nbsp;</td>
			<td width="83" height="20">&nbsp;</td>
		</tr>
		<tr valign="middle">
			<td width="186" height="20" id="tdTypeName10">&nbsp;10 受托人身份证明</td>
			<td width="59" height="20">&nbsp;</td>
			<td height="20" colspan="3">&nbsp;</td>
			<td width="172" height="20" id="tdTypeName25">&nbsp;25 血液检验报告</td>
			<td width="54" height="20">&nbsp;</td>
			<td width="83" height="20">&nbsp;</td>
		</tr>
		<tr valign="middle">
			<td width="186" height="20" id="tdTypeName11">&nbsp;11 监护人身份证明</td>
			<td width="59" height="20">&nbsp;</td>
			<td height="20" colspan="3">&nbsp;</td>
			<td width="172" height="20" id="tdTypeName26">&nbsp;26 手术证明</td>
			<td width="54" height="20">&nbsp;</td>
			<td width="83" height="20">&nbsp;</td>
		</tr>
		<tr valign="middle">
			<td width="186" height="20" id="tdTypeName12">&nbsp;12 意外事故证明</td>
			<td width="59" height="20">&nbsp;</td>
			<td height="20" colspan="3">&nbsp;</td>
			<td width="172" height="20" id="tdTypeName27">&nbsp;27 住院医疗证明</td>
			<td width="54" height="20">&nbsp;</td>
			<td width="83" height="20">&nbsp;</td>
		</tr>
		<tr valign="middle">
			<td width="186" height="20" id="tdTypeName13">&nbsp;13 居民医学死亡证明</td>
			<td width="59" height="20">&nbsp;</td>
			<td height="20" colspan="3">&nbsp;</td>
			<td width="172" height="20" id="tdTypeName28">
				&nbsp;28 医疗费凭证（收据）<br> &nbsp;票面金额：
			</td>
			<td width="54" height="20">&nbsp;</td>
			<td width="83" height="20">&nbsp;</td>
		</tr>
		<tr valign="middle">
			<td width="186" height="20" id="tdTypeName14">&nbsp;14 尸体检验证明</td>
			<td width="59" height="20">&nbsp;</td>
			<td height="20" colspan="3">&nbsp;</td>
			<td width="172" height="20" id="tdTypeName29">&nbsp;29 医疗费结算表明细表</td>
			<td width="54" height="20">&nbsp;</td>
			<td width="83" height="20">&nbsp;</td>
		</tr>
		<tr valign="middle">
			<td width="186" height="20" id="tdTypeName15">&nbsp;15 警方死亡证明书</td>
			<td width="59" height="20">&nbsp;</td>
			<td height="20" colspan="3">&nbsp;&nbsp;</td>
			<td width="172" height="20" id="tdTypeName30">&nbsp;30 住院天数证明</td>
			<td width="54" height="20">&nbsp;</td>
			<td width="83" height="20">&nbsp;</td>
		</tr>
		<tr valign="middle">
			<td height="20" id="tdTypeName31">&nbsp;</td>
			<td height="20">&nbsp;</td>
			<td height="20" colspan="3">&nbsp;</td>
			<td height="20" id="tdTypeName32">&nbsp;其他</td>
			<td height="20">&nbsp;</td>
			<td height="20">&nbsp;</td>
		</tr>
		<tr valign="middle">
			<td height="20" colspan="8">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr valign="middle">
						<td width="37%">
							<div align="left">
								&nbsp;共（<span id="spIntSize">&nbsp;</span>）项（ ）页
							</div>
						</td>
						<td width="18%">
							<div align="right">签收人：</div>
						</td>
						<td width="45%">
							<div align="right">签收日期：&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;月&nbsp;&nbsp;日&nbsp;</div>
						</td>
					</tr>
				</table>
		</tr>
		<tr valign="middle">
			<td height="20" colspan="8">
				<p>&nbsp;还需补充提供的证明材料：</p>
			</td>
		</tr>
		<tr valign="middle">
			<td height="20" colspan="3">&nbsp;</td>
			<td height="20" colspan="5">&nbsp;</td>
		</tr>
		<tr valign="middle">
			<td height="20" colspan="3">&nbsp;</td>
			<td height="20" colspan="5">&nbsp;</td>
		</tr>
		<tr valign="middle">
			<td height="20" colspan="3">&nbsp;</td>
			<td height="20" colspan="5">&nbsp;</td>
		</tr>
		<tr valign="middle">
			<td height="20" colspan="8">
				<p align="right">签收人：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 签收日期：&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;月&nbsp;&nbsp;日&nbsp;</p>
			</td>
		</tr>
		<tr>
			<td colspan="4" valign="top">
				<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td height="25" colspan="2">&nbsp;理赔决定通知书签收栏：</td>
					</tr>
					<tr>
						<td height="120" colspan="2">&nbsp;</td>
					</tr>
					<tr>
						<td height="25">
							<div align="left">&nbsp;签收人：</div>
						</td>
						<td>
							<div align="right">&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;月&nbsp;&nbsp;日&nbsp;</div>
						</td>
					</tr>
				</table>
			</td>
			<td colspan="4" valign="top">
				<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td height="25" colspan="2">&nbsp;索赔文件退件签收栏：</td>
					</tr>
					<tr>
						<td height="40" colspan="2" valign="top">&nbsp;1、</td>
					</tr>
					<tr>
						<td height="40" colspan="2" valign="top">&nbsp;2、</td>
					</tr>
					<tr>
						<td height="40" colspan="2" valign="top">&nbsp;3、</td>
					</tr>
					<tr>
						<td height="25">
							<div align="left">&nbsp;签收人：</div>
						</td>
						<td>
							<div align="right">&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;月&nbsp;&nbsp;日&nbsp;</div>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<table width="645" border="0" align="center" cellpadding="0" cellspacing="0" style="font-family: 宋体; font-size: 10pt;">
		<tr>
			<td colspan="3">
				<p>说明：</p>
			</td>
		</tr>
		<tr>
			<td colspan="3">&nbsp;&nbsp;&nbsp;&nbsp;1．本交接凭证系本公司收到索赔文件的凭证，本交接凭证一式两份，保险公司和客户各执一份。</td>
		</tr>
		<tr>
			<td colspan="3">&nbsp;&nbsp;&nbsp;&nbsp;2．申请人应在&nbsp;5日内将要求补交的证明文件提交本公司理赔人员，否则会因所提供的文件材料不全而导致不予立案並退件。</td>
		</tr>
		<tr>
			<td colspan="3">&nbsp;&nbsp;&nbsp;&nbsp;3．申请人请妥善保存本交接凭证，结案时，须在“理赔决定通知书”及“索赔文件退件”签收栏上签收後，以此凭证换回可退还本次索赔文件之原始单据。</td>
		</tr>
		<tr>
			<td width="430">&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td width="190">
				<div align="right">财产保险有限公司</div>
			</td>
			<td width="25">&nbsp;</td>
		</tr>
		<tr>
			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td>
				<div align="center">（理赔业务章）</div>
			</td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td>
				<div align="right">&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;月&nbsp;&nbsp;日</div>
			</td>
			<td>&nbsp;</td>
		</tr>
	</table>
	<!--include打印按钮-->
	<jsp:include page="/common/print/PrintButton.jsp" />
</body>
</html>
