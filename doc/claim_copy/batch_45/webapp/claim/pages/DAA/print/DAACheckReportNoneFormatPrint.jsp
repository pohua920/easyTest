<%--
****************************************************************************
* DESC       ：机动车辆现场查勘记录打印页面
* AUTHOR     ：理赔组
* CREATEDATE ：2004-11-16
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ page import="com.sinosoft.claimprint.ui.dto.DAAPrpLCheckPrintDto"%>
<%@ page import="com.sinosoft.claim.dto.domain.PrpLthirdPartyDto"%>
<%@ page import="com.sinosoft.claim.dto.domain.PrpLdriverDto"%>
<html>
<link rel="stylesheet" type="text/css" href="/claim/DAA/print/StandardPrint.css">
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<title>机动车辆保险事故现场查勘记录</title>
<body bgcolor="#FFFFFF">
	<!-- 标题部分 -->
	<table width="100%" align="center" cellspacing="0" cellpadding="0" border="0">
		<tr>
			<td colspan="3" height="50" align=center style="text-align: center; font-family: 宋体; font-size: 14pt;">
				<B>机动车辆保险事故现场查勘记录<br>
			</td>
		</tr>
		<tr>
			<td align=left style="font-family: 宋体; font-size: 9pt; width: 33%">
				报案号
				<bean:write name="dAAPrpLCheckPrintDto" property="registNo" />
			</td>
			<td align=left style="font-family: 宋体; font-size: 9pt; width: 33%">
				交强险保单号
				<bean:write name="compPolicyNo" name="compPolicyNo" />
			</td>
			<td align=left style="font-family: 宋体; font-size: 9pt; width: 33%">
				交强险立案号
				<bean:write name="compClaimNo" name="compClaimNo" />
			</td>
		</tr>
		<tr>
			<td align=left style="font-family: 宋体; font-size: 9pt; width: 33%">
				被保险人
				<bean:write name="insuredName" name="insuredName" />
			</td>
			<td align=left style="font-family: 宋体; font-size: 9pt; width: 33%">
				商业险保单号
				<bean:write name="dAAPrpLCheckPrintDto" property="policyNo" />
			</td>
			<td align=left style="font-family: 宋体; font-size: 9pt; width: 33%">
				商业险立案号
				<bean:write name="dAAPrpLCheckPrintDto" property="claimNo" />
			</td>
		</tr>
	</table>
	<table border=1 width="100%" align="center" cellspacing="0" cellpadding="2" style="border-collapse: collapse; font-family: 宋体; font-size: 9pt;" bordercolor="#111111">
		<tr>
			<td rowspan="2" colspan="1">
				<strong>保险车辆</strong>
			</td>
			<td colspan="1">
				厂牌型号：
				<bean:write name="dAAPrpLCheckPrintDto" property="brandName" />
			</td>
			<td width="38%" colspan="1">
				发动机号:
				<bean:write name="dAAPrpLCheckPrintDto" property="engineNo" />
			</td>
			<td width="23%" colspan="3">车辆已行驶里程：</td>
			<td width="15%" colspan="3">已使用年限：</td>
		</tr>
		<tr>
			<td width="19%" height=24 colspan="1">
				号牌号码：
				<bean:write name="dAAPrpLCheckPrintDto" property="licenseNo" />
			</td>
			<td width="50%" height=24 colspan="4">
				车架号（VIN）：
				<bean:write name="dAAPrpLCheckPrintDto" property="frameNo" />
			</td>
			<td width="12%" height=24 colspan="3">初次登记日期：</td>
		</tr>
		<tr>
			<td width="30%" height=24 colspan="2">
				驾驶员姓名：
				<bean:write name="dAAPrpLCheckPrintDto" property="driverName" />
			</td>
			<td width="50%" height=24 colspan="2">
				性别：
				<input name="sex" type="checkbox" onclick=this.checked=false '<login:equal name="dAAPrpLCheckPrintDto" property="driverSax" value="男">checked</login:equal>' value="男" />
				男&nbsp;&nbsp;
				<input name="sex" type="checkbox" onclick=this.checked=false '<login:equal name="dAAPrpLCheckPrintDto" property="driverSax" value="女">checked</login:equal>'  value="女" />
				女
			</td>
			<td width="19%" height=24 colspan="1">年龄：</td>
			<td width="19%" height=24 colspan="4">准驾车型：□ A□ B□ C□ 其他_____</td>
		</tr>
		<tr>
			<td width="10%" height=24 colspan="3">初次领证日期：</td>
			<td width="62%" height=24 colspan="6">
				驾驶证号码：
				<bean:write name="dAAPrpLCheckPrintDto" property="driverLicenseNo" />
			</td>
		</tr>
		<tr>
			<td width="4%" height=24 rowspan="2" colspan="1">职业分类</td>
			<td width="78%" height=24 colspan="4">□职业驾驶员 □国家社会管理者 □企业管理人员 □私营企业主 □专业技术人员 □军人</td>
			<td width="62%" height=24 colspan="4">文化程度：□研究生及以上 □大学本科</td>
		</tr>
		<tr>
			<td width="78%" height=24 colspan="4">□个体工商户 □商业服务业员工 □业工人 □农业劳动者 □办事人员 □其他</td>
			<td width="62%" height=24 colspan="4">□大专 □中专 □高中 □初中及以下</td>
		</tr>
		<tr>
			<td width="62%" height=20 colspan="2">
				查勘时间：
				<bean:write name="dAAPrpLCheckPrintDto" property="checkDate" />
			</td>
			<td width="62%" height=20 colspan="3">
				查勘地点：
				<bean:write name="dAAPrpLCheckPrintDto" property="checkAddress" />
			</td>
			<td width="62%" height=20 colspan="4">
				是否第一现场：
				<input name="firstSite" type="checkbox" onclick=this.checked=false '<logic:equal name="dAAPrpLCheckPrintDto" property="firstSite" value="">checked</logic:equal>' />
				是
				<input name="firstSite" type="checkbox" onclick=this.checked=false '<logic:equal name="dAAPrpLCheckPrintDto" property="firstSite" value="">checked</logic:equal>' />
				否
			</td>
		</tr>
		<%
			DAAPrpLCheckPrintDto dAAPrpLCheckPrintDto = (DAAPrpLCheckPrintDto) request.getAttribute("dAAPrpLCheckPrintDto");
			if (dAAPrpLCheckPrintDto.getPrpLthirdPartyDtoList() != null) {
				for (int i = 1; i < dAAPrpLCheckPrintDto.getPrpLthirdPartyDtoList().size(); i++) {
					PrpLthirdPartyDto prpLthirdPartyDto = (PrpLthirdPartyDto) dAAPrpLCheckPrintDto.getPrpLthirdPartyDtoList().get(i);
		%>
		<tr>
			<td rowspan=4 colspan=1 width="4%">第三方车辆</td>
			<td colspan=3 height=24>
				交强险保单号：
				<bean:write name="compPolicyNo" name="compPolicyNo" />
			</td>
			<td colspan=3 height=24>交强险承保公司：</td>
			<td colspan=1 height=24>车辆已行驶里程：</td>
		</tr>
		<tr>
			<td colspan=1 width="19%">
				厂牌型号：<%=prpLthirdPartyDto.getBrandName()%></td>
			<td colspan=3 height=24>
				号牌号码:
				<%=prpLthirdPartyDto.getLicenseNo()%></td>
			<td colspan=3 height=24>有无商业保险：□有 □无</td>
			<td colspan=1 height=24>商业保险承保公司：</td>
		</tr>
		<%
			if (dAAPrpLCheckPrintDto.getPrpLdriverDtoList() != null) {
						PrpLdriverDto prpLdriverDto = (PrpLdriverDto) dAAPrpLCheckPrintDto.getPrpLdriverDtoList().get(i - 1);
		%>
		<tr>
			<td width="19%" height="33" colspan=1 aligh="center">
				驾驶员人员姓名:<%=prpLdriverDto.getDriverName()%></td>
			<td colspan=6 width="10%" aligh="center">
				驾驶证号：<%=prpLdriverDto.getDrivingLicenseNo()%></td>
			<td width="12%" height="28" colspan=1 aligh="center">车辆初次登记日期：</td>
		</tr>
		<tr>
			<td width="19%" height="33" colspan=1 aligh="center">初次领证日期：</td>
			<td colspan=4 width="10%" aligh="center">
				准驾车型：<%=prpLdriverDto.getDrivingCarType()%></td>
			<td width="5%" height="28" colspan=2 aligh="center">职业：</td>
			<td width="12%" height="28" colspan=1 aligh="center">车辆已使用年限：</td>
		</tr>
		<%
			} else {
		%>
		<tr>
			<td width="4%" height="33" colspan=1 aligh="center">驾驶员人员姓名:</td>
			<td colspan=6 width="78%" aligh="center">驾驶证号：</td>
			<td width="4%" height="28" colspan=1 aligh="center">车辆初次登记日期：</td>
		</tr>
		<tr>
			<td width="4%" height="30" colspan=1 aligh="center">初次领证日期：</td>
			<td colspan=4 width="74%" aligh="center">准驾车型：</td>
			<td width="5%" height="28" colspan=2 aligh="center">职业：</td>
			<td width="4%" height="28" colspan=1 aligh="center">车辆已使用年限：</td>
		</tr>
		<%
			}
				}
			}
		%>
		<tr>
			<td width="4%" height=24 width="4%" colspan="1">现场查勘时请按右侧所列内容仔细查验並认真完整填写</td>
			<td height="18" colspan="8">
				&nbsp;1、出险原因：□碰撞 □倾覆 □火灾 □爆炸 □自燃 □外界物体倒塌 □外界物体坠落 □雷击 □暴风 □暴雨 □洪水 □雹灾 □其它（ ）<br> &nbsp;2、事故原因：□制动失灵 □转向失灵 □其他机械故障 □疲劳驾驶 □超速行驶 □违章並线 □逆向行驶 □安全间距不够 □违章装载 □其他违章行驶 □疏忽大意、措施不当 □其他<br>
				&nbsp;3、是否在本公司投保了交强险：□是 否□（交强险承保公司是否要求本公司代查勘 □是 □否）<br> &nbsp;4、事故所涉及的商业保险：□车损险 □三责险 □盗抢险 □玻璃单独破碎险 □自燃损失险<br>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; □车上人员责任险 □车上货物责任险 □其它（ ）<br> &nbsp;5、保险车辆的号牌号码、发动机号、车架号与保险单上所载明的是否相符 □是 □否<br> &nbsp;6、出险时间是否在保险有效期限内 □是 □否<br>
				&nbsp;7、是否属於追偿案件 □是（是否属於交强险的垫付案件 □是，垫付种类 □否） □否<br> &nbsp;8、出险时间接近保险起讫期的，有无相应时间证明 □有 □无<br> &nbsp;9、出险地点：⑴ 分类：□高速公路 □普通公路 □城市道路 □乡村便道和机耕路 □场院及其他；<br>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ⑵ 与报案人所报是否一致：□是 □否<br> 10、实际使用性质与保险单上所载明的是否一致 □是 □否<br> 11、保险车辆驾驶人员情况与报案人所述是否一致 □是 □否<br>
				12、保险车辆驾驶人员的驾驶证是否有效 □是 □否<br> 13、保险车辆驾驶人员准驾车型与实际驾驶车辆是否相符 □是 □否<br> 14、使用各种专用机械车、特种车的人员是否有国家有关部门核发的有效操作证 □是 □否<br> 15、驾驶营业性客车的驾驶人员是否有国家有关部门核发的有效资格证书 □是 □否<br>
				16、保险车辆驾驶人员是否为被保险人允许的驾驶人员 □是 □否<br> 17、保险车辆驾驶人员是否为保险合約约定的驾驶人员 □是 □否 □保险合約未约定<br> 18、保险车辆驾驶人员是否为酒後驾车 □是 □否 <br> 19、事故车辆损失痕迹与事故现场痕迹是否吻合 □是 □否<br> 20、保险车辆安全配置情况：□安全气囊 □ABS □倒车雷达
				□卫星定位 □其它防盗装置（ ） □停车场 21、第三者车辆是否在其他保险公司参加保险 □是（是否已向其承保公司报案、索赔 □是 □否 ） □否 22、事故是否涉及第三方人身伤亡□是（伤 人，亡 人 ，是否在交强险限额内□是□否□待确定）□否<br> 23、事故是否涉及第三方财产损失 □是（是否在交强险限额内 □是 □否 □待确定） □否 <br>
				24、事故是否涉及本车上人员伤亡□是（伤 人，亡 人，是否在交强险限额内□是□否□待确定）□否<br> 25、确定或预计责任划分： <br> 26、保险车辆损失程度： □全部损失 □部分损失<br> 27、是否属於交强险的保险责任：□是 □否 □待确定（
				原因是&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;）<br> 28、是否属於商业保险的保险责任：□是 □否
				□待确定（ 原因是&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;）<br> <strong>29、其它需要说明的内容：</strong>
				<br>
			</td>
		</tr>
		<tr>
			<td height=24 rowspan="5" colspan="1">估计损失：</td>
			<td colspan=8 width="95%" height=24>1、标的车辆：车辆损失______元，吊车费______元，拖车费______元，其它施救费______元。</td>
		</tr>
		<tr>
			<td colspan=8 aligh="left">2、本车人员：住院人数______人，预计费用______元，未住院人数______人，预计费用______元。</td>
		</tr>
		<tr>
			<td colspan=8 aligh="left">3、对方车辆：车辆损失______元，吊车费______元，拖车费______元，其它施救费______元。</td>
		</tr>
		<tr>
			<td colspan=8 aligh="left">4、对方人员：住院人数______人，预计费用______元，未住院人数______人，预计费用______元。</td>
		</tr>
		<tr>
			<td colspan=8 aligh="left">5、其他损失预估：</td>
		</tr>
		<tr>
			<td colspan=9 aligh="left">
				<strong>风险标识：</strong>□常规 □延迟报案 □无法取证 □损失鉴定的风险 □车辆修理的风险 □领取赔款的风险
			</td>
		</tr>
		<tr>
			<td rowspan="2" colspan=7 aligh="left" align="left" valign="top">
				&nbsp;&nbsp;查勘人意见（包括事故经过简单描述和初步责任认定）：
				<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					查勘人签字：
			</td>
			<td height="30" colspan="2" aligh="center">
				<p align="center">询问笔录&nbsp;&nbsp;&nbsp;&nbsp;张
			</td>
		</tr>
		<tr>
			<td height="25" colspan="2" aligh="center">
				<p align="center">事故照片&nbsp;&nbsp;&nbsp;&nbsp;张
			</td>
		</tr>
	</table>
	<table width="92%" align="center" cellspacing="0" cellpadding="0" border="0">
		<tr>
			<td>说明：1、估计损失金额单位为人民币元。&nbsp;2、第三方车辆不止一辆的，可增加《机动车辆现场查勘记录》用纸。</td>
		</tr>
	</table>
	<jsp:include page="/common/print/PrintButton.jsp" />
</body>
</html>
