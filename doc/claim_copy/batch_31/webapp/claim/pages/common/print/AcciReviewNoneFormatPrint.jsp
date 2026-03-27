<%--

****************************************************************************

* DESC       ：显示(非车险)立案登记的险别估损金额页面

* AUTHOR     ：luqin

* CREATEDATE ：2005-06-19

* MODIFYLIST ：   Name       Date            Reason/Contents

*          ------------------------------------------------------

****************************************************************************

--%>
<%@ page contentType="text/html; charset=GBK"%>
<%-- 初始化 --%>
<%@include file="AcciReviewNoneFormatPrintIni.jsp"%>
<html>
<body onload="loadForm();">
	<p></p>
	<div align="center">
		<center>
			<table border="0" cellpadding="0" cellspacing="0" width="96%">
				<tr>
					<td width="100%">
						<div align="center">
							<center>
								<table border="0" cellpadding="0" cellspacing="0" width="96%" height="186">
									<tr>
										<td width="10%" height="36">列印次数：</td>
										<td width="23%" id="tdTimes">第&nbsp; 次列印</td>
										<td width="10%">理赔案件号：</td>
										<td width="24%" id="tdCompensateNo"></td>
										<td width="10%">业务员姓名：</td>
										<td width="23%" id="tdOperatorName"></td>
									</tr>
									<tr>
										<td width="10%" height="36">列印时间：</td>
										<td width="23%" id="tdPrintTime"></td>
										<td width="10%">保单号：</td>
										<td width="24%" id="tdPolicyNo"></td>
										<td width="10%">业务员代码：</td>
										<td width="23%" id="tdOperatorCode"></td>
									</tr>
									<tr>
										<td width="10%" height="36">条形码：</td>
										<td width="23%"></td>
										<td width="10%">客户号：</td>
										<td width="24%" id="tdInsuredCode"></td>
										<td width="10%">业务员部门：</td>
										<td width="23%" id="tdComName"></td>
									</tr>
									<tr>
										<td width="10%" height="36">通讯地址：</td>
										<td width="57%" colspan="3" id="tdPostAddress"></td>
										<td width="10%">系统记录号：</td>
										<td width="23%"></td>
									</tr>
									<tr>
										<td width="16%" height="36">联系电话：</td>
										<td width="50%" colspan="3" id="tdPhoneNumber"></td>
										<td width="17%">咨询电话：</td>
										<td width="17%"></td>
									</tr>
								</table>
							</center>
						</div>
						<p align="center">
							<font size="5"><b>核保决定通知函</b></font>
						</p>
						<p>
							尊敬的客户&nbsp;<%=strLinkerName%>&nbsp; 先生（女士）
						</p>
						<p>
							&nbsp;&nbsp;&nbsp; 感谢您对本公司的信任，在本公司投保。因被保险人健康状况等因素发生改变，本公司在下一年度对保单&nbsp;<%=strPolicyNo%>&nbsp; 的一年期短期险作出如下审核意见，本保单年度的保险责任不变。
						</p>
						<p></p>
						<table border="0" cellpadding="0" cellspacing="0" width="96%">
							<tr>
								<td width="20%" height="36">客户</td>
								<td width="20%">险种</td>
								<td width="20%">审核意见</td>
								<td width="20%">原因</td>
								<td width="20%">生效时间</td>
							</tr>
							<tr>
								<td width="20%" height="108"></td>
								<td width="20%"></td>
								<td width="20%"></td>
								<td width="20%"></td>
								<td width="20%"></td>
							</tr>
							<tr>
								<td width="20%" height="36"></td>
								<td width="20%"></td>
								<td width="20%"></td>
								<td width="20%">
									<p>分公司意健险部</p>
									<p>公司签章</p>
								</td>
								<td width="20%"></td>
							</tr>
						</table>
						<p>&nbsp; （以上手写无效）</p>
						<p>&nbsp;温 馨 提 示</p>
						<p>
							⑴为了您的保险权益不受损害，收到本通知函後，请仔细阅读函件内容，做出相应尽快处理有决定，並关事宜。於&nbsp;&nbsp; 年&nbsp; 月&nbsp; 日之前由您本人或委托本公司业务员到本公司回复您的处理意见，或请拨打咨询电话&nbsp;&nbsp;&nbsp; 预约本公司服务人员上门收取。逾期未回复您的意见，本公司视为您将放弃该险种下一年度续保，该险种将到期终止。
							<o:p>
						</p>
						<p>
							⑵若您不接受一年期险种的下一年度的审核意见，本公司在下一保单年度将不再提供该险种的保障，且不收取该险种的下一年度保费。
							<o:p>
						</p>
						<p>
							⑶因该险种为一年期险种，若您今後存在补充或新增的风险，本公司仍将会对该险种的续期承保条件进行重新审核。下个保单年度的续保条件，以本公司下个年度续保前最新的核保决定为准。当续期承保条件发生变化时，需要您再次确认。
							<o:p>
						</p>
						<p>
							⑷若您同意上述核保决定，本公司将按合約约定收取下一年度保费。
							<o:p>
						</p>
						⑸因上述险种为一年期险种。若上述核保决定为拒保，则本公司将在上述核保决定生效时起不再续保上述险种。
						<p align="center">
							<font size="3"><b>客户意见栏</b></font>
						</p>
						<p>
							□接受核保决定
							<o:p>
						</p>
						<p>□不接受核保决定，原因：</p>
						<p></p>
						<p></p>
						<p></p>
						<div align="center">
							<center>
								<table border="0" cellpadding="0" cellspacing="0" width="96%">
									<tr>
										<td width="16%" height="36">要保人签字：</td>
										<td width="12%"></td>
										<td width="22%">被保险人/监护人签字：</td>
										<td width="13%"></td>
										<td width="26%">其他被保险人/监护人签字：</td>
										<td width="11%"></td>
									</tr>
									<tr>
										<td width="16%" height="36">签字日期：</td>
										<td width="12%"></td>
										<td width="22%">签字日期：</td>
										<td width="13%"></td>
										<td width="26%">签字日期：</td>
										<td width="11%"></td>
									</tr>
									<tr>
										<td width="16%" height="36">公司地址：</td>
										<td width="47%" colspan="3"></td>
										<td width="26%">邮编：</td>
										<td width="11%"></td>
									</tr>
								</table>
							</center>
						</div>
					</td>
				</tr>
			</table>
		</center>
	</div>
	<!--include列印按钮-->
	<jsp:include page="/common/print/PrintButton.jsp" />
</body>
</html>
