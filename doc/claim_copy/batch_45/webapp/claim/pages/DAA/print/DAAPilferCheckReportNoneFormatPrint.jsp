<%--
****************************************************************************
* DESC       ：机动车辆盗抢险查勘报告打印页面
* AUTHOR     ：zhulianyu
* CREATEDATE ：2005-11-07
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%-- 初始化 --%>
<%@include file="DAAPilferCheckReportNoneFormatPrintIni.jsp"%>
<html>
<link rel="stylesheet" type="text/css" href="/claim/DAA/print/StandardPrint.css">
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<body bgcolor="#FFFFFF">
	<!-- 标题部分 -->
	<table width="92%" align="center" cellspacing="0" cellpadding="0" border="0">
		<tr height="40">
			<td colspan="2" align="center">
				<img src="/claim/images/LOGO.jpg" />
			</td>
		</tr>
		<tr height="40">
			<td colspan="2" align=center style="text-align: center; font-family: 宋体; font-size: 14pt;">
				<B> 机动车辆保险盗抢险查勘报告<B>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>&nbsp;</td>
								<td height="25">
									<div align="right">
										报案号：<%=strRegistNo%></div>
								</td>
							</tr>
						</table>
			</td>
		</tr>
	</table>
	<!-- 主体部分 -->
	<table border=1 width="92%" align="center" cellspacing="0" cellpadding="2" style="border-collapse: collapse; font-family: 宋体; font-size: 10pt;" bordercolor="#111111">
		<tr>
			<td width="14%" height=24 colspan="1" align="center">被保险人</td>
			<td height=24 colspan="3" align="center"><%=strInsuredName%></td>
			<td width="10%" height=24 colspan="1" align="center">厂牌型号</td>
			<td width="14%" height=24 colspan="1" align="center"><%=strBrandName%></td>
			<td width="10%" height=24 colspan="1" align="center">号牌号码</td>
			<td height=24 colspan="2" align="center"><%=strLicenseNo%></td>
		</tr>
		<tr>
			<td height=25 colspan="1" align="center">出险地点</td>
			<td height=25 colspan="3" align="center"><%=strDamageAddress%></td>
			<td height=25 colspan="1" align="center">出险时间</td>
			<td height=25 colspan="1" align="center"><%=strdamageStartDate%></td>
			<td height=25 colspan="1" align="center">使用性质</td>
			<td height=25 colspan="2" align="center"><%=strUseNatureName%></td>
		</tr>
		<tr>
			<td height=25 colspan="1" align="center">行驶证有效期</td>
			<td height=25 colspan="3" align="center">&nbsp;</td>
			<td height=25 colspan="1" align="center">查勘地点</td>
			<td height=25 colspan="4" align="center"><%=strCheckSite%></td>
		</tr>
		<tr>
			<td height=25 colspan="1" align="center">驾驶证号码</td>
			<td height=25 colspan="3" align="center"><%=strD2%></td>
			<td height=25 colspan="1" align="center">驾驶证有效期</td>
			<td height=25 colspan="4" align="center"><%=strD3%></td>
		</tr>
		<tr>
			<td height=25 colspan="1" align="center">驾驶员姓名</td>
			<td width="16%" height=25 colspan="1" align="center"><%=strD1%></td>
			<td height=25 width="7%" colspan="1" align="center">性别</td>
			<td width="10%" height=25 colspan="1" align="center">□男&nbsp;□女</td>
			<td height=25 colspan="1" align="center">婚姻状况</td>
			<td height=25 colspan="1" align="center">□已婚 □单身</td>
			<td height=25 colspan="1" align="center">职业</td>
			<td height=25 colspan="2" align="center"><%=strBusinessName%></td>
		</tr>
		<tr>
			<td height=25 colspan="1" align="center">防盗装置</td>
			<td height=25 colspan="4" align="center">□电子防盗装置 □机械防盗装置 □无防盗装置</td>
			<td height=25 colspan="1" align="center">固定停放地点</td>
			<td height=25 colspan="1" align="center">□有 □无</td>
			<td width="9%" height=25 colspan="1" align="center">购车收據是否丢失</td>
			<td width="10%" height=25 colspan="1" align="center">□是 □否</td>
		</tr>
		<tr>
			<td height=25 colspan="1" align="center">行驶证是否丢失</td>
			<td height=25 colspan="1" align="center">□是 □否</td>
			<td height=25 colspan="2" align="center">附加费证是否丢失</td>
			<td height=25 colspan="1" align="center">□是 □否</td>
			<td height=25 colspan="1" align="center">车钥匙丢失</td>
			<td height=25 colspan="3" align="center">□无 □一把 □二把 □多把</td>
		</tr>
		<tr>
			<td height=25 colspan="9" align="left">查勘环节:</td>
		</tr>
		<tr>
			<td valign="top" rowspan=1 colspan=9 height=200>
				出险地查勘情况:<br>
				<br>&nbsp;&nbsp;&nbsp;&nbsp;<%=tempContext%>
				<br>
				<br>
				<br>
				<br>
				<br>
				<br>
				<br>
				<table width="80%" align="center" cellspacing="0" cellpadding="2" style="border-collapse: collapse; font-family: 宋体; font-size: 10pt;" bordercolor="#111111">
					<tr>
						<td align="right">
							查勘人:
							<%=strChecker%>&nbsp;&nbsp;查勘时间：<%=checkDate.getYear()%>年<%=checkDate.getMonth()%>月<%=checkDate.getDay()%>日
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td valign="top" rowspan=1 colspan=9 height=200>
				到公安部门了解的情况:<br>
				<br>&nbsp;&nbsp;&nbsp;&nbsp;
				<!--%=tempContext%-->
				<br>
				<br>
				<br>
				<br>
				<br>
				<br>
				<br>
				<table width="80%" align="center" cellspacing="0" cellpadding="2" style="border-collapse: collapse; font-family: 宋体; font-size: 10pt;" bordercolor="#111111">
					<tr>
						<td align="right">
							查勘人:
							<!--%=strChecker%-->
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;查勘时间：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp&nbsp&nbsp年&nbsp;&nbsp&nbsp&nbsp;月&nbsp;&nbsp&nbsp&nbsp;日
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td valign="top" rowspan=1 colspan=9 height=200>
				到车管部门调查的情况:<br> <br>&nbsp;&nbsp;&nbsp;&nbsp;
				<!--%=tempContext%-->
				<br>
				<br>
				<br>
				<br>
				<br>
				<br>
				<br>
				<table width="80%" align="center" cellspacing="0" cellpadding="2" style="border-collapse: collapse; font-family: 宋体; font-size: 10pt;" bordercolor="#111111">
					<tr>
						<td align="right">
							查勘人:
							<!--%=strChecker%-->
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;查勘时间：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp&nbsp&nbsp年&nbsp;&nbsp&nbsp&nbsp;月&nbsp;&nbsp&nbsp&nbsp;日
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr style="display: none">
			<td valign="top" rowspan=1 colspan=3>
				根据查勘情况初步判断,被保险人在本次事故中负：<br>
				<br>
				<br> □全部责任 □主责 □同责 □次责 □无责 □无法判断
			</td>
		</tr>
		<tr style="display: none">
			<td valign="top" rowspan=1 colspan=6>
				发动机号，车架号拓印粘贴处：<br>
				<br>
				<br>
				<br> <br>
				<br>
				<br>
				<br>
			</td>
		</tr>
	</table>
	<table border=1 width="92%" align="center" cellspacing="0" cellpadding="2" style="border-collapse: collapse; font-family: 宋体; font-size: 10pt; display: none;" bordercolor="#111111">
		<tr>
			<td height=25 rowspan=12 colspan="1" width="2%">预估损失</td>
			<td height=25 colspan="1" align="center" width="8%">损失项目</td>
			<td height=25 colspan="1" align="center" width="18%">预估损失金额</td>
			<td height=25 colspan="2" width="70%" bordercolor="#FFFFFF">&nbsp;&nbsp&nbsp现场图&nbsp;&nbsp;&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp;&nbsp;&nbsp北↑</td>
		</tr>
		<tr>
			<td height=25 colspan="1" align="center" width="8%">本车车损</td>
			<td height=25 colspan="1" align="center" width="18%"></td>
		</tr>
		<tr>
			<td height=25 colspan="1" align="center" width="8%">三者车损</td>
			<td height=25 colspan="1" align="center" width="18%"></td>
		</tr>
		<tr>
			<td height=25 colspan="1" align="center" width="8%">三者人伤</td>
			<td height=25 colspan="1" align="center" width="18%"></td>
		</tr>
		<tr>
			<td height=25 colspan="1" align="center" width="8%">三者物损</td>
			<td height=25 colspan="1" align="center" width="18%"></td>
		</tr>
		<tr>
			<td height=25 colspan="1" align="center" width="8%">施救费</td>
			<td height=25 colspan="1" align="center" width="18%"></td>
		</tr>
		<tr>
			<td height=25 colspan="1" width="8%"></td>
			<td height=25 colspan="1" width="18%"></td>
		</tr>
		<tr>
			<td height=25 colspan="1" width="8%"></td>
			<td height=25 colspan="1" width="18%"></td>
		</tr>
		<tr>
			<td height=25 colspan="1" width="8%"></td>
			<td height=25 colspan="1" width="18%"></td>
			<td height=25 colspan="2" width="70%" bordercolor="#FFFFFF">&nbsp;&nbsp&nbsp查勘时间：</td>
		</tr>
		<tr>
			<td height=25 colspan="1" width="8%"></td>
			<td height=25 colspan="1" width="18%"></td>
			<td height=25 colspan="2" width="70%" bordercolor="#FFFFFF">&nbsp;&nbsp&nbsp查勘地点：</td>
		</tr>
		<tr>
			<td height=25 colspan="1" width="8%"></td>
			<td height=25 colspan="1" width="18%"></td>
			<td height=25 colspan="2" width="70%" bordercolor="#FFFFFF">&nbsp;&nbsp&nbsp查勘人：&nbsp;&nbsp&nbsp&nbsp;&nbsp&nbsp;&nbsp&nbsp;&nbsp当事人签章：</td>
		</tr>
		<tr>
			<td height=25 colspan="1" width="8%">合计金额</td>
			<td height=25 colspan="1" width="18%"></td>
			<td height=25 colspan="2" width="70%" bordercolor="#FFFFFF">&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp月&nbsp;&nbsp;&nbsp;&nbsp;&nbsp日</td>
		</tr>
	</table>
	<%-- include打印按钮 --%>
	<jsp:include page="/common/print/PrintButton.jsp" />
</body>
</html>
