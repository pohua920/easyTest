<%@ page language="java" pageEncoding="GBK"%>
<%@page import="com.sinosoft.claimprint.ui.dto.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>


<html:html lang="true">
<head>
	<html:base />

	<title>索赔资料回执单</title>

	<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
</head>

<body>
	<%
		DAAPrpLClaimBackPrintDto dAAPrpLClaimBackPrintDto = (DAAPrpLClaimBackPrintDto) request.getAttribute("dAAPrpLClaimBackPrintDto");
		String registNo = dAAPrpLClaimBackPrintDto.getRegistNo();
		String InsuredName = dAAPrpLClaimBackPrintDto.getInsuredName();
		String businessPolicyNo = dAAPrpLClaimBackPrintDto.getBusinessPolicyNo();
		String compelPolicyNo = dAAPrpLClaimBackPrintDto.getCompelPolicyNo();
		String compelClaimNo = dAAPrpLClaimBackPrintDto.getCompelClaimNo();
		String businessClaimNo = dAAPrpLClaimBackPrintDto.getBusinessClaimNo();
		String damageStartDate = dAAPrpLClaimBackPrintDto.getDamageStartDate();
	%>
	<table width="85%" align="center" cellspacing="0" cellpadding="0"
		border="0">
		<tr>
			<td colspan="3" height="40" align=center
				style="font-family:宋体; font-size:14pt;">
				<img src="/claim/images/LOGO.jpg" />
			</td>
		</tr>
		<tr>
			<td colspan="3" height="40" align=center
				style="text-align:center; font-family:宋体; font-size:14pt;">
				<B>索赔资料回执单</B>
			</td>
		</tr>
	</table>
	<br>
	<br>
	<br>
	<br>
	<br>
	<table width="95%" align="center" cellspacing="0" cellpadding="0"
		border="0" style="font-family:宋体; font-size:10pt;">
		<tr>
			<td style="font-family:宋体; font-size:10pt;" width="20%">
				赔款流转查询密码：
			</td>
			<td style="font-family:宋体; font-size:10pt;" width="30%">
				&nbsp;
			</td>
			<td style="font-family:宋体; font-size:10pt;" width="20%">
				索赔材料交接单编号：
			</td>
			<td style="font-family:宋体; font-size:10pt;" width="30%">
				&nbsp;
			</td>
		</tr>
	</table>
	<table width="100%" align="center" cellspacing="0" cellpadding="0"
		border="1" style="font-family:宋体; font-size:11pt;"
		style="font-size: 10pt;border-collapse:collapse; bordercolor:#111111;">
		<tr>
			<td width="15%">
				被保险人
			</td>
			<td width="35%">
				&nbsp;
				<%=InsuredName%>
			</td>
			<td width="15%">
				出险时间
			</td>
			<td width="35%">
				&nbsp;
				<%=damageStartDate%>
			</td>
		</tr>
		<tr>
			<td>
				交强险保单号
			</td>
			<td>
				&nbsp;
				<%=compelPolicyNo%>
			</td>
			<td>
				商业保险保单号
			</td>
			<td>
				&nbsp;
				<%=businessPolicyNo%>
			</td>
		</tr>
		<tr>
			<td>
				交强险立案号
			</td>
			<td>
				&nbsp;
				<%=compelClaimNo%>
			</td>
			<td>
				商业保险立案号
			</td>
			<td>
				&nbsp;
				<%=businessClaimNo%>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<table width="100%" align="center" cellspacing="0" cellpadding="0"
					border="0" style="font-family:宋体; font-size:11pt;">
					<tr>
						<td>
							<b>索赔基础材料：</b>
						</td>
					</tr>
					<tr>
						<td>
							□索赔申请书（保险人提供）
						</td>
					</tr>
					<tr>
						<td>
							□交强险单（正本）
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; 
							原件□ &nbsp;&nbsp; 复印件□
						</td>
					</tr>
					<tr>
						<td>
							□商业保险单（正本）&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp; 原件□ &nbsp;&nbsp; 复印件□
						</td>
					</tr>
					<tr>
						<td>
							□车辆行驶证（正、副证）复印件
						</td>
					</tr>
					<tr>
						<td>
							□驾驶证（正、副证）复印件
						</td>
					</tr>
					<tr>
						<td>
							□营运车辆服务证复印件
						</td>
					</tr>
					<tr>
						<td>
							□交警责任认定书或事故证明 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 原件□
							&nbsp;&nbsp; 复印件□
						</td>
					</tr>
					<tr>
						<td>
							□法院调解或判决书 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp; 原件□ &nbsp;&nbsp; 复印件□
						</td>
					</tr>
					<tr>
						<td>
							&nbsp;
						</td>
					</tr>
				</table>
			</td>
			<td colspan="2" rowspan="2">
				<table width="100%" align="center" cellspacing="0" cellpadding="0"
					border="0" style="font-family:宋体; font-size:11pt;">
					<tr>
						<td>
							<b>汽车盗抢险索赔材料：</b>
						</td>
					</tr>
					<tr>
						<td>
							□出险当地县级以上公安刑侦部门证明
						</td>
					</tr>
					<tr>
						<td>
							□车辆行驶证
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							原件□ &nbsp;&nbsp; 复印件□
						</td>
					</tr>
					<tr>
						<td>
							□车辆停放当事人驾驶证（正、副证）复印件
						</td>
					</tr>
					<tr>
						<td>
							□车辆来历证明或购车原始收據 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							原件□ &nbsp;&nbsp; 复印件□
						</td>
					</tr>
					<tr>
						<td>
							□购置附加费凭证
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							原件□ &nbsp;&nbsp; 复印件□
						</td>
					</tr>
					<tr>
						<td>
							□车钥匙（ ）把
						</td>
					</tr>
					<tr>
						<td>
							□车辆报停手续
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							原件□ &nbsp;&nbsp; 复印件□
						</td>
					</tr>
					<tr>
						<td>
							□登报声明原件（ ）份
						</td>
					</tr>
					<tr>
						<td>
							□权益转让书
						</td>
					</tr>
					<tr>
						<td>
							□营业执照或身份证复印件
						</td>
					</tr>
					<tr>
						<td>
							□汽车出厂原始证明
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							原件□ &nbsp;&nbsp; 复印件□
						</td>
					</tr>
					<tr>
						<td>
							□机动车登记证书或整车出厂证明或进口证明
						</td>
					</tr>
					<tr>
						<td>
							&nbsp;
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<table width="100%" align="center" cellspacing="0" cellpadding="0"
					border="0" style="font-family:宋体; font-size:11pt;">
					<tr>
						<td>
							<b>交通事故责任交强险索赔材料：</b>
						</td>
					</tr>
					<tr>
						<td>
							□公安部门要求垫付／预付的通知书
						</td>
					</tr>
					<tr>
						<td>
							□医疗部门的抢救医疗费用清单。
						</td>
					</tr>
					<tr>
						<td>
							□被保险人的《权益转让书》。
						</td>
					</tr>
					<tr>
						<td>
							&nbsp;
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<table width="100%" align="center" cellspacing="0" cellpadding="0"
					border="0" style="font-family:宋体; font-size:11pt;">
					<tr>
						<td>
							<b>车辆损失的索赔材料：</b>
						</td>
					</tr>
					<tr>
						<td>
							□修理协议
						</td>
					</tr>
					<tr>
						<td>
							□修理收據（ &nbsp;&nbsp; ）份 总金额（ &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							）元
						</td>
					</tr>
					<tr>
						<td>
							□修理清单
						</td>
					</tr>
					<tr>
						<td>
							□定损协议书
						</td>
					</tr>
					<tr>
						<td>
							&nbsp;
						</td>
					</tr>
				</table>
			</td>
			<td colspan="2" rowspan="2">
				<table width="100%" align="center" cellspacing="0" cellpadding="0"
					border="0" style="font-family:宋体; font-size:11pt;">
					<tr>
						<td>
							<b>其他需要提交的索赔材料：</b>
						</td>
					</tr>
					<tr>
						<td>
							□ ________________________________________
						</td>
					</tr>
					<tr>
						<td>
							□ ________________________________________
						</td>
					</tr>
					<tr>
						<td>
							□ ________________________________________
						</td>
					</tr>
					<tr>
						<td>
							□ ________________________________________
						</td>
					</tr>
					<tr>
						<td>
							□ ________________________________________
						</td>
					</tr>
					<tr>
						<td>
							□ ________________________________________
						</td>
					</tr>
					<tr>
						<td>
							□ ________________________________________
						</td>
					</tr>
					<tr>
						<td>
							&nbsp;
						</td>
					</tr>
					<tr>
						<td>
							&nbsp;
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td rowspan="3" colspan="2">
				<table width="100%" align="center" cellspacing="0" cellpadding="0"
					border="0" style="font-family:宋体; font-size:11pt;">
					<tr>
						<td>
							<b>财产损失和人伤案件的索赔材料：</b>
						</td>
					</tr>
					<tr>
						<td>
							□物品损失鉴定书
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							原件□ &nbsp;&nbsp; 复印件□
						</td>
					</tr>
					<tr>
						<td>
							□第三者责任险物损收據 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 原件□
							&nbsp;&nbsp; 复印件□
						</td>
					</tr>
					<tr>
						<td>
							□损失清单 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 原件□ &nbsp;&nbsp; 复印件□
						</td>
					</tr>
					<tr>
						<td>
							□医疗手册 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; □就医病历（ &nbsp;&nbsp;&nbsp;&nbsp; ）页
						</td>
					</tr>
					<tr>
						<td>
							□医疗费收據（ &nbsp;&nbsp;&nbsp;&nbsp; ）份 &nbsp; 总金额（
							&nbsp;&nbsp;&nbsp;&nbsp; ）元

						</td>
					</tr>
					<tr>
						<td>
							□医疗费结算明细表

						</td>
					</tr>
					<tr>
						<td>
							□转院医疗证明

						</td>
					</tr>
					<tr>
						<td>
							□出院证明

						</td>
					</tr>
					<tr>
						<td>
							□残疾鉴定书
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							原件□ &nbsp;&nbsp; 复印件□

						</td>
					</tr>
					<tr>
						<td>
							□死亡证明书或户口註銷证明 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							原件□ &nbsp;&nbsp; 复印件□

						</td>
					</tr>
					<tr>
						<td>
							□被抚养人证明
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							原件□ &nbsp;&nbsp; 复印件□

						</td>
					</tr>
					<tr>
						<td>
							□残疾用具收據
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							原件□
							&nbsp;&nbsp; 复印件□

						</td>
					</tr>
					<tr>
						<td>
							□二次医疗费用就诊医院证明 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 原件□
							&nbsp;&nbsp; 复印件□

						</td>
					</tr>
					<tr>
						<td>
							□病休证明 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							原件□ &nbsp;&nbsp; 复印件□

						</td>
					</tr>
					<tr>
						<td>
							□施救费收據

						</td>
					</tr>
					<tr>
						<td>
							□误工/护理人工资证明 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							原件□ &nbsp;&nbsp; 复印件□
						</td>
					</tr>
					<tr>
						<td>
							□道路交通事故经济赔偿凭证
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<table width="100%" align="center" cellspacing="0" cellpadding="0"
					border="0" style="font-family:宋体; font-size:11pt;">
					<tr>
						<td>
							<b>索赔材料交接须知</b>
						</td>
					</tr>
					<tr>
						<td>
							尊敬的被保险人：
						</td>
					</tr>
					<tr>
						<td>
							&nbsp;&nbsp;&nbsp;&nbsp;您的索赔材料已经提交齐全，请您於&nbsp;&nbsp;月&nbsp;&nbsp;日
						</td>
					</tr>
					<tr>
						<td>
							与本公司联系，确定领取赔款的时间。您领取赔款时，请
						</td>
					</tr>
					<tr>
						<td>
							携带身份证（单位财务专用印章）。
						</td>
					</tr>
					<tr>
						<td>
							&nbsp;&nbsp;&nbsp;&nbsp;联系人：
						</td>
					</tr>
					<tr>
						<td>
							&nbsp;&nbsp;&nbsp;&nbsp;联系电话：
						</td>
					</tr>
					<tr>
						<td>
							&nbsp;
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<table width="100%" align="center" cellspacing="0" cellpadding="0"
					border="0" style="font-family:宋体; font-size:11pt;">

					<tr>
						<td>
							被保险人代表（交单人）：
						</td>
					</tr>
					<tr>
						<td>
							签章
						</td>
					</tr>
					<tr>
						<td align="right">
							交接时间：&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;月&nbsp;&nbsp;日
						</td>
					</tr>
					<tr>
						<td>
							&nbsp;
						</td>
					</tr>
					<tr>
						<td>
							保险人代表（接单人）：
						</td>
					</tr>
					<tr>
						<td>
							签章
						</td>
					</tr>
					<tr>
						<td align="right">
							交接时间：&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;月&nbsp;&nbsp;日
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<br>
	<br>

	<%-- include打印按钮 --%>
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
</html:html>
