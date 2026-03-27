<%--
****************************************************************************
* DESC       ：财产赔款计算书打印页面
* AUTHOR     ：理赔组
* CREATEDATE ：22004-11-16
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app"%>
<%-- 初始化 --%>
<%@include file="PropCompensateNoneFormatPrintIni.jsp"%>
<html>
<body onload="loadForm();">
	<p align="center">
		<strong><font size="5" face="隶书">( )险赔款计算书</font></strong>
	</p>
	<br>
	<tr>
		<td id="tdCompensateNo">赔案编号：</td>
	</tr>
	</p>
	<table width="96%" height="661" border="1" cellspacing="0">
		<tr>
			<td width="23%">
				<div align="center">被 保 险 人</div>
			</td>
			<td width="27%" id="tdInsuredName">&nbsp;</td>
			<td width="11%">
				<div align="center">保险标的</div>
			</td>
			<td width="39%" id="tdLossName">&nbsp;</td>
		</tr>
		<tr>
			<td>
				<div align="center">保险单 号码</div>
			</td>
			<td id="tdPolicyNo">&nbsp;</td>
			<td>
				<div align="center">保险期间</div>
			</td>
			<td id="tdInsuredDate">&nbsp;</td>
		</tr>
		<tr>
			<td>
				<div align="center">保 险 金 额</div>
			</td>
			<td id="tdSumAmount">&nbsp;</td>
			<td>
				<div align="center">出险日期</div>
			</td>
			<td id="tdDamageStartDate">&nbsp;</td>
		</tr>
		<tr>
			<td>
				<div align="center">批 单 号 码</div>
			</td>
			<td>&nbsp;</td>
			<td>
				<div align="center">出险地点</div>
			</td>
			<td id="tdDamageAddress">&nbsp;</td>
		</tr>
		<tr>
			<td height="190">
				<div align="center">赔款计算方式</div>
			</td>
			<td colspan="3">&nbsp;</td>
		</tr>
		<tr>
			<td colspan="4">
				<div align="left">赔款金额合计（大写）人民币</div>
			</td>
		</tr>
		<tr>
			<td>
				<div align="center">赔 款 核 定</div>
			</td>
			<td>
				<div align="center">金额</div>
			</td>
			<td>
				<div align="center">币种</div>
			</td>
			<td>
				<div align="center">付款说明</div>
			</td>
		</tr>
		<tr>
			<td>
				<div align="center">保 品 损 失</div>
			</td>
			<td>&nbsp;</td>
			<td>
				<div align="center"></div>
			</td>
			<td>请将赔款 付给</td>
		</tr>
		<tr>
			<td>
				<div align="center">第三者 赔付</div>
			</td>
			<td>&nbsp;</td>
			<td>
				<div align="center"></div>
			</td>
			<td>单位：</td>
		</tr>
		<tr>
			<td>
				<div align="center">施救整理费用</div>
			</td>
			<td>&nbsp;</td>
			<td>
				<div align="center"></div>
			</td>
			<td>开户行：</td>
		</tr>
		<tr>
			<td>
				<div align="center">代 理 费</div>
			</td>
			<td>&nbsp;</td>
			<td>
				<div align="center"></div>
			</td>
			<td>帐号：</td>
		</tr>
		<tr>
			<td>
				<div align="center">检 验 费</div>
			</td>
			<td>&nbsp;</td>
			<td>
				<div align="center"></div>
			</td>
			<td>付讫日期及方式：</td>
		</tr>
		<tr>
			<td>
				<div align="center">查 勘 费</div>
			</td>
			<td>&nbsp;</td>
			<td>
				<div align="center"></div>
			</td>
			<td>请将赔款 付给</td>
		</tr>
		<tr>
			<td>
				<div align="center"></div>
			</td>
			<td>&nbsp;</td>
			<td>
				<div align="center"></div>
			</td>
			<td>单位：</td>
		</tr>
		<tr>
			<td>
				<div align="center"></div>
			</td>
			<td>&nbsp;</td>
			<td>
				<div align="center"></div>
			</td>
			<td>开户行：</td>
		</tr>
		<tr>
			<td>
				<div align="center"></div>
			</td>
			<td>&nbsp;</td>
			<td>
				<div align="center"></div>
			</td>
			<td>帐号：</td>
		</tr>
		<tr>
			<td>
				<div align="center">合 计</div>
			</td>
			<td>&nbsp;</td>
			<td>
				<div align="center"></div>
			</td>
			<td>付讫日期及方式：</td>
		</tr>
	</table>
	<table width="96%" height="100" border="0" cellspacing="0">
		<tr>
			<td width="7%">经办：</td>
			<td width="16%">___________</td>
			<td width="9%">覆核：</td>
			<td width="18%">____________</td>
			<td width="8%">经理：</td>
			<td width="15%">___________</td>
			<td width="14%">分管总经理：</td>
			<td width="13%">___________</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td colspan="2">
				<div align="right">年 月 日</div>
			</td>
			<td colspan="2">
				<div align="right">年 月 日</div>
			</td>
			<td colspan="2">
				<div align="right">年 月 日</div>
			</td>
			<td colspan="2">
				<div align="right">年 月 日</div>
			</td>
		</tr>
	</table>
	<p>&nbsp;</p>
</body>
</html>