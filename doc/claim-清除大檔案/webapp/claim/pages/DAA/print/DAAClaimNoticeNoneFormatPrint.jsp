
<%@page import="com.sinosoft.claim.common.ConstantCodes"%><%@include file="DAAClaimNoticeNoneFormatPrintIni.jsp"%>
<html>
<head>
<title>机动车辆保险索赔须知列印</title>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<link rel="stylesheet" type="text/css" href="/claim/DAA/print/StandardPrint.css">
</head>
<body bgcolor="#FFFFFF">
	<!-- 标题部分 -->
	<table width="105%" align="center" cellspacing="2" cellpadding="2" border="0">
		<tr height="15">
			<td colspan="3" align="center">
				<img src="/claim/images/LOGO.jpg" />
			</td>
		</tr>
		<tr height="5">
			<td colspan="3" align=center style="font-family: 宋体; font-size: 14pt;">
				<B><center>
						机动车辆保险索赔须知
						<center>
							<B>
			</td>
		</tr>
	</table>
	<!-- 主体部分 -->
	<table width="100%" align="center" cellspacing="0" cellpadding="0" border="1" style="font-size: 10pt; border-collapse: collapse; bordercolor: #111111;">
		<tr>
			<td>
				<table align="center" cellspacing="2" cellpadding="2" border="0">
					<tr>
						<td>
							<u>&nbsp;&nbsp;&nbsp;&nbsp; <%=strInsuredName%> &nbsp;&nbsp;&nbsp;&nbsp;
							</u>（被保险人名称／姓名）：
						</td>
					</tr>
					<tr>
						<td>
							<b>&nbsp;&nbsp;&nbsp;&nbsp;为确保您的合法权益得到充分保障，请您认真阅读本索赔须知，並按保险人的要求提供相关索赔单证和材料。如果您遇到困难，请随时拨打本公司的服务专线电话 “<font color="red" style="font-family: 宋体; font-size: 14pt;">4008817518</font>”，本公司将竭诚为您提供优质、高效的保险服务。
							</b>
						</td>
					</tr>
					<tr>
						<td style="font-family: 宋体; font-size: 10pt;">
							<table>
								<tr>
									<td>一、</td>
									<td>按照我国交通事故处理相关法律法规，对於事故造成的损失，应当通过机动车交通事故责任交强险进行赔偿处理；超过机动 车交通事故责任交强险责任限额的部分，保险人根据商业机动车辆保险合約的约定进行赔偿处理。</td>
								</tr>
								<tr>
									<td>二、</td>
									<td>本公司自收到您提供的证明和资料之日起5日内，对是否属於保险责任作出核定；属於保险责任的，本公司在与您达成赔偿保 险金的协议後10日内，赔偿保险金。</td>
								</tr>
								<tr>
									<td>三、</td>
									<td>事故中其它机动车投保机动车交通事故责任交强险的情况对赔偿处理有重大影响请务必将相关资料並告知本公司。</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td 　style="font-family: 宋体; font-size: 10pt;">
							<b>理赔单证：</b>请您尽早提交下列经保险人确认的单证，以便於您及时获得保险赔偿。
						</td>
					</tr>
					<tr>
						<td 　style="font-family: 宋体; font-size: 10pt;">
							<table>
								<tr>
									<td width="5%">&nbsp;&nbsp;1．</td>
									<td width="95%">
										<%
											if (certifyMessage.get("0101") == null) {
										%>
										□
										<%
											} else {
										%>
										■
										<%
											}
										%>
										《机动车辆保险索赔申请书》 &nbsp;2．□ 机动车交通事故责任交强险单正本&nbsp;3．□ 机动车辆商业保险单正本
									</td>
								</tr>
								<tr>
									<td width="5%">&nbsp;&nbsp;4．</td>
									<td width="95%">
										事故处理部门出具的：
										<%
										if (certifyMessage.get("0301") == null) {
									%>
										□
										<%
										} else {
									%>
										■
										<%
										}
									%>
										交通事故责任认定书
										<%
										if (certifyMessage.get("0302") == null) {
									%>
										□
										<%
										} else {
									%>
										■
										<%
										}
									%>
										调解书
										<%
										if (certifyMessage.get("0303") == null) {
									%>
										□
										<%
										} else {
									%>
										■
										<%
										}
									%>
										简易事故处理书
										<%
										if (certifyMessage.get("0304") == null) {
									%>
										□
										<%
										} else {
									%>
										■
										<%
										}
									%>
										其它证明 （&nbsp;&nbsp;&nbsp;&nbsp;）
									</td>
								</tr>
								<tr>
									<td width="5%">&nbsp;&nbsp;5．</td>
									<td width="95%">
										法院、仲裁机构出具的：
										<%
										if (certifyMessage.get("0401") == null) {
									%>
										□
										<%
										} else {
									%>
										■
										<%
										}
									%>
										裁定书 &nbsp;&nbsp;
										<%
											if (certifyMessage.get("0402") == null) {
										%>
										□
										<%
											} else {
										%>
										■
										<%
											}
										%>
										裁决书 &nbsp;
										<%
											if (certifyMessage.get("0403") == null) {
										%>
										□
										<%
											} else {
										%>
										■
										<%
											}
										%>
										调解书 &nbsp;
										<%
											if (certifyMessage.get("0404") == null) {
										%>
										□
										<%
											} else {
										%>
										■
										<%
											}
										%>
										判决书 &nbsp;
										<%
											if (certifyMessage.get("0404") == null) {
										%>
										□
										<%
											} else {
										%>
										■
										<%
											}
										%>
										仲裁书
									</td>
								</tr>
								<tr>
									<td width="5%">&nbsp;&nbsp;6．</td>
									<td width="95%">
										涉及车辆损失还需提供：
										<%
										if (certifyMessage.get(ConstantCodes.RISKCODE_DAA) == null) {
									%>
										□
										<%
										} else {
									%>
										■
										<%
										}
									%>
										《机动车辆保险车辆损失情况确认书》和《零配件更换项目清单》及附页
									</td>
								</tr>
								<tr>
									<td width="5%"></td>
									<td width="95%">
										<%
											if (certifyMessage.get("0502") == null) {
										%>
										□
										<%
											} else {
										%>
										■
										<%
											}
										%>
										车辆修理的正式发票（即“汽车维修业专用收據”）
										<%
											if (certifyMessage.get("0503") == null) {
										%>
										□
										<%
											} else {
										%>
										■
										<%
											}
										%>
										修理材料清单
										<%
											if (certifyMessage.get("0504") == null) {
										%>
										□
										<%
											} else {
										%>
										■
										<%
											}
										%>
										结算清单
									</td>
								</tr>
								<tr>
									<td width="5%">&nbsp;&nbsp;7．</td>
									<td width="95%">
										涉及财产损失还需提供：
										<%
										if (certifyMessage.get("0602") == null) {
									%>
										□
										<%
										} else {
									%>
										■
										<%
										}
									%>
										设备总体造价及损失程度证明
										<%
										if (certifyMessage.get("0603") == null) {
									%>
										□
										<%
										} else {
									%>
										■
										<%
										}
									%>
										设备恢复的工程预算
									</td>
								</tr>
								<tr>
									<td width="5%">&nbsp;&nbsp;</td>
									<td width="95%">
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<%
											if (certifyMessage.get("0604") == null) {
										%>
										□
										<%
											} else {
										%>
										■
										<%
											}
										%>
										财产损失清单 &nbsp;
										<%
											if (certifyMessage.get("0605") == null) {
										%>
										□
										<%
											} else {
										%>
										■
										<%
											}
										%>
										购置、修复受损财产的有关费用单据
									</td>
								</tr>
								<tr>
									<td width="5%">&nbsp;&nbsp;8．</td>
									<td width="95%">涉及人身伤、残、亡损失还需提供：</td>
								</tr>
								<tr>
									<td width="5%">&nbsp;&nbsp;</td>
									<td width="95%">
										&nbsp;
										<%
											if (certifyMessage.get("0701") == null) {
										%>
										□
										<%
											} else {
										%>
										■
										<%
											}
										%>
										县级以上医院诊断证明 &nbsp;
										<%
											if (certifyMessage.get("0702") == null) {
										%>
										□
										<%
											} else {
										%>
										■
										<%
											}
										%>
										出院通知书 &nbsp;
										<%
											if (certifyMessage.get("0704") == null) {
										%>
										□
										<%
											} else {
										%>
										■
										<%
											}
										%>
										医疗费报销凭证（须附处方及治疗、用药明细单据）
									</td>
								</tr>
								<tr>
									<td width="5%">&nbsp;&nbsp;</td>
									<td width="95%">
										&nbsp;
										<%
											if (certifyMessage.get("0705") == null) {
										%>
										□
										<%
											} else {
										%>
										■
										<%
											}
										%>
										伤、残、亡人员误工证明及收入情况证明（收入超过纳税金额的应提交纳税证明）
									</td>
								</tr>
								<tr>
									<td width="5%">&nbsp;&nbsp;</td>
									<td width="95%">
										&nbsp;
										<%
											if (certifyMessage.get("0706") == null) {
										%>
										□
										<%
											} else {
										%>
										■
										<%
											}
										%>
										护理人员误工证明及收入情况证明（收入超过纳税金额的应提交纳税证明）
									</td>
								</tr>
								<tr>
									<td width="5%">&nbsp;&nbsp;</td>
									<td width="95%">
										&nbsp;
										<%
											if (certifyMessage.get("0703") == null) {
										%>
										□
										<%
											} else {
										%>
										■
										<%
											}
										%>
										需要护理人员证明 &nbsp;
										<%
											if (certifyMessage.get("0707") == null) {
										%>
										□
										<%
											} else {
										%>
										■
										<%
											}
										%>
										残者须提供法医伤残鉴定书 &nbsp;
										<%
											if (certifyMessage.get("0708") == null) {
										%>
										□
										<%
											} else {
										%>
										■
										<%
											}
										%>
										亡者须提供死亡证明
									</td>
								</tr>
								<tr>
									<td width="5%">&nbsp;&nbsp;</td>
									<td width="95%">
										&nbsp;
										<%
											if (certifyMessage.get("0709") == null) {
										%>
										□
										<%
											} else {
										%>
										■
										<%
											}
										%>
										被扶养人证明材料 &nbsp;
										<%
											if (certifyMessage.get("0710") == null) {
										%>
										□
										<%
											} else {
										%>
										■
										<%
											}
										%>
										户籍派出所出具的受害者家庭情况证明 &nbsp;
										<%
											if (certifyMessage.get("0711") == null) {
										%>
										□
										<%
											} else {
										%>
										■
										<%
											}
										%>
										户口 &nbsp;
										<%
											if (certifyMessage.get("0712") == null) {
										%>
										□
										<%
											} else {
										%>
										■
										<%
											}
										%>
										丧失劳动能力证明
									</td>
								</tr>
								<tr>
									<td width="5%">&nbsp;&nbsp;</td>
									<td width="95%">
										&nbsp;
										<%
											if (certifyMessage.get("0713") == null) {
										%>
										□
										<%
											} else {
										%>
										■
										<%
											}
										%>
										交通费报销凭证 &nbsp;
										<%
											if (certifyMessage.get("0714") == null) {
										%>
										□
										<%
											} else {
										%>
										■
										<%
											}
										%>
										住宿费报销凭证 &nbsp;
										<%
											if (certifyMessage.get("0715") == null) {
										%>
										□
										<%
											} else {
										%>
										■
										<%
											}
										%>
										参加事故处理人员工资证明（收入超过纳税金额的应提交纳税证明）
										<%
											if (certifyMessage.get("0716") == null) {
										%>
										□
										<%
											} else {
										%>
										■
										<%
											}
										%>
										向第三方支付赔偿费用的赔偿证明（须由事故处理部门签章确认）
									</td>
								</tr>
								<tr>
									<td width="5%">&nbsp;&nbsp;9．</td>
									<td width="95%">涉及车辆盗抢案件还需提供：</td>
								</tr>
								<tr>
									<td width="5%">&nbsp;&nbsp;</td>
									<td width="95%">
										&nbsp;
										<%
											if (certifyMessage.get("0801") == null) {
										%>
										□
										<%
											} else {
										%>
										■
										<%
											}
										%>
										机动车行驶证（原件）
										<%
											if (certifyMessage.get("0802") == null) {
										%>
										□
										<%
											} else {
										%>
										■
										<%
											}
										%>
										出险地县级以上公安刑侦部门出具的盗抢案件立案证明
										<%
											if (certifyMessage.get("0803") == null) {
										%>
										□
										<%
											} else {
										%>
										■
										<%
											}
										%>
										已登报声明的证明
									</td>
								</tr>
								<tr>
									<td width="5%">&nbsp;&nbsp;</td>
									<td width="95%">
										&nbsp;
										<%
											if (certifyMessage.get("0804") == null) {
										%>
										□
										<%
											} else {
										%>
										■
										<%
											}
										%>
										车购附加费缴费凭证和收据（原件）或车辆购置完税证明和代征车辆购置缴税收据或免税证明（原件）
									</td>
								</tr>
								<tr>
									<td width="5%">&nbsp;&nbsp;</td>
									<td width="95%">
										&nbsp;
										<%
											if (certifyMessage.get("0805") == null) {
										%>
										□
										<%
											} else {
										%>
										■
										<%
											}
										%>
										机动车登记证书（原件） &nbsp;
										<%
											if (certifyMessage.get("0806") == null) {
										%>
										□
										<%
											} else {
										%>
										■
										<%
											}
										%>
										车辆停驶手续证明 &nbsp;
										<%
											if (certifyMessage.get("0807") == null) {
										%>
										□
										<%
											} else {
										%>
										■
										<%
											}
										%>
										机动车购车凭证 &nbsp;
										<%
											if (certifyMessage.get("0808") == null) {
										%>
										□
										<%
											} else {
										%>
										■
										<%
											}
										%>
										全套车钥匙
									</td>
								</tr>
								<tr>
									<td width="5%">&nbsp;&nbsp;10．</td>
									<td width="95%">
										涉及车辆火灾（自燃）的案件还需提供：
										<%
										if (certifyMessage.get("0901") == null) {
									%>
										□
										<%
										} else {
									%>
										■
										<%
										}
									%>
										消防部门出具的《火灾鉴定报告证明》。
									</td>
								</tr>
								<tr>
									<td width="5%">&nbsp;&nbsp;11．</td>
									<td width="95%">只在本公司投保车辆商业保险的，索赔时须提供交强险承保公司的赔款凭据(必须加盖理赔专用章)和各赔偿项目、金额的证明材料。</td>
								</tr>
								<tr>
									<td width="5%">&nbsp;&nbsp;12．</td>
									<td width="95%">被保险人索赔时，还须提供以下证件原件，经保险公司验证後留存复印件：</td>
								</tr>
								<tr>
									<td width="5%">&nbsp;&nbsp;</td>
									<td width="95%">
										&nbsp;
										<%
											if (certifyMessage.get("1001") == null) {
										%>
										□
										<%
											} else {
										%>
										■
										<%
											}
										%>
										保险车辆《机动车行驶证》 &nbsp;
										<%
											if (certifyMessage.get("1002") == null) {
										%>
										□
										<%
											} else {
										%>
										■
										<%
											}
										%>
										肇事驾驶人员的《机动车驾驶证》
									</td>
								</tr>
								<tr>
									<td width="5%">&nbsp;&nbsp;13．</td>
									<td width="95%">办理垫付／预付手续时，应提交公安机关交通管理部门要求垫付／预付受害人抢救费用的通知书和被保险人的《权益转让书》。</td>
								</tr>
								<tr>
									<td width="5%">&nbsp;&nbsp;14．</td>
									<td width="95%">领取赔款时，须提供以下材料和证件，经保险公司验证後留存复印件：</td>
								</tr>
								<tr>
									<td width="5%">&nbsp;&nbsp;</td>
									<td width="95%">&nbsp;⑴ 被保险人是自然人，本人领取赔款时，须提供被保险人的身份证。</td>
								</tr>
								<tr>
									<td width="5%">&nbsp;&nbsp;</td>
									<td width="95%">&nbsp;⑵ 非被保险人本人领取赔款，或被保险人非自然人的，领取赔款时，须提供：</td>
								</tr>
								<tr>
									<td width="5%">&nbsp;&nbsp;</td>
									<td width="95%">
										&nbsp;
										<%
											if (certifyMessage.get("1101") == null) {
										%>
										□
										<%
											} else {
										%>
										■
										<%
											}
										%>
										领取赔款授权书 &nbsp;
										<%
											if (certifyMessage.get("1102") == null) {
										%>
										□
										<%
											} else {
										%>
										■
										<%
											}
										%>
										被保险人身份证明 &nbsp;
										<%
											if (certifyMessage.get("1103") == null) {
										%>
										□
										<%
											} else {
										%>
										■
										<%
											}
										%>
										领取赔款人员身份证明
									</td>
								</tr>
								<tr>
									<td width="5%">&nbsp;&nbsp;15．</td>
									<td width="95%">需要提供的其它索赔证明和单据：</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td 　style="font-family: 宋体; font-size: 10pt;">
							<table>
								<tr>
									<td width="10%">
										<b>敬请注意：</b>
									</td>
									<td width="90%">
										<b>为确保您能够获得更加全面、合理的保险赔偿，本公司在理赔过程中，可能需要您进一步提供上述所列单证以外的其他证明届时，本公司将及时通知您。感谢您对我们工作的理解与支持！</b>
									</td>
								</tr>
							</table>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td 　style="font-family: 宋体; font-size: 10pt;">
				<b>&nbsp;索赔地点指南（公司地址、询问电话）: </b>
			</td>
		</tr>
		<tr>
			<td>
				<table align="left" cellspacing="2" cellpadding="2" border="0" width="100%">
					<tr>
						<td align="left" width="50%">
							<b>被保险人：</b>&nbsp;
							<%=strInsuredName%></td>
						<td width="50%">
							<b>保险公司：</b>
						</td>
					</tr>
					<tr>
						<td align="left" width="50%">领到《索赔须知》日期： &nbsp;&nbsp;&nbsp;&nbsp; 年 &nbsp;&nbsp; 月 &nbsp;&nbsp; 日</td>
						<td width="50%">交付《索赔须知》日期： &nbsp;&nbsp;&nbsp;&nbsp; 年 &nbsp;&nbsp; 月 &nbsp;&nbsp; 日</td>
					</tr>
					<tr>
						<td align="left" width="50%">确认签字：</td>
						<td width="50%">经办人签字：</td>
					</tr>
					<tr>
						<td align="left" width="50%">提交索赔材料日期： &nbsp;&nbsp;&nbsp;&nbsp; 年 &nbsp;&nbsp; 月 &nbsp;&nbsp; 日</td>
						<td width="50%">收到索赔材料日期： &nbsp;&nbsp;&nbsp;&nbsp; 年 &nbsp;&nbsp; 月 &nbsp;&nbsp; 日</td>
					</tr>
					<tr>
						<td align="left" width="50%">确认签字：</td>
						<td width="50%">经办人签字：</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<!-- 按钮部分 -->
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
</html>
