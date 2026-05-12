<%--
****************************************************************************
* DESC       ：机动车辆保险修理项目附页
* AUTHOR     ：理赔组
* CREATEDATE ：2004-11-16
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@include file="DAALossNoneFormatPrintIni.jsp"%>
<%
	if (intComponentCount < 18) {
		strMessage = "抱歉!更换项目未超过17项,不需列印清单附页,请您列印清单。";
		//System.out.println(strMessage);
%>
<jsp:include page="/common/pub/UIErrorPage.jsp">
	<jsp:param name="Picture" value="F" />
	<jsp:param name="Content" value="<%=strMessage%>" />
</jsp:include>
<%
	return;
	}
%>
<html>
<head>
<title>机动车辆保险车辆换件项目清单附表列印</title>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
<link rel="stylesheet" type="text/css" href="/claim/DAA/print/StandardPrint.css">
</head>
<body bgcolor="#FFFFFF" onload="loadForm();">
	<%
		for (int pageNum = 1; pageNum <= intComponentCount / 17; pageNum++) {
	%>
	<!-- 标题部分 -->
	<table width="90%" align="center" cellspacing="0" cellpadding="0" border="0">
		<tr height=30>
			<td colspan="3" align="center">
				<img src="/claim/images/LOGO.jpg" />
			</td>
		</tr>
		<tr height=30>
			<td colspan="3" align=center style="font-family: 宋体; font-size: 14pt;">
				<B><center>
						机动车辆保险车辆损失情况确认书 <br> <span>零配件更换项目清单附页</span>
						<center>
							<B>
			</td>
		</tr>
		<tr height=20>
			<td colspan="3" style="font-family: 宋体; font-size: 10pt;">
				被保险人：
				<%=strInsuredName%>
			</td>
		</tr>
		<tr height=20>
			<td align=left width="40%" id="tdInsuredName" style="font-family: 宋体; font-size: 10pt;">
				报案号：
				<%=strRegistNo%>
			</td>
			<td align=left id="tdRegistNo" width="35%" style="font-family: 宋体; font-size: 10pt;">交强险承保公司：</td>
			<td width="25%" align=right style="font-family: 宋体; font-size: 10pt;">
				共
				<%=intComponentCount / 17 + 1%>
				页&nbsp;第
				<%=pageNum + 1%>
				页
			</td>
		</tr>
	</table>
	<table border=1 width="100%" align="center" cellspacing="0" cellpadding="0" border="0" style="border-collapse: collapse" bordercolor="#111111" style="font-family:宋体; font-size:10pt;">
		<tr>
			<td height=20 colspan="1" width="12%" align="center">号牌号码</td>
			<td height=20 colspan="1" width="22%" align="center"><%=licenseNo%></td>
			<td height=20 colspan="2" align="center">保单号码</td>
			<td height=20 colspan="2" align="center"><%=strPolicyNo%></td>
		</tr>
		<tr>
			<td height=20 colspan="1" align="center">厂牌型号</td>
			<td height=20 colspan="1" align="center"><%=strBrandName%></td>
			<td height=20 colspan="2" align="center">发动机号</td>
			<td height=20 colspan="2" align="center"><%=strEngineNo%></td>
		</tr>
	</table>
	<!-- 主体部分 -->
	<table border=1 width="100%" align="center" cellspacing="0" cellpadding="2" style="border-collapse: collapse" bordercolor="#111111" style="font-family:宋体; font-size:10pt;">
		<tr align="center" height="30">
			<td width="10%">序号</td>
			<td width="40%">更换配件名称</td>
			<td width="10%">数量</td>
			<td width="25%">配件价格</td>
			<td width="15%">定价</td>
		</tr>
		<%
			if (intComponentCount < (pageNum + 1) * 17 + 1 && intComponentCount > pageNum * 17) {
					for (index = pageNum * 17; index < (pageNum + 1) * 17; index++) {
		%>
		<tr height=25>
			<td width="10%" align="center"><%=index - pageNum * 17 + 1%></td>
			<%
				if (index < changeListInfo.size()) {
			%>
			<td width="40%" align="center"><%=((RepairContentDto) changeListInfo.get(index)).getChangeName()%></td>
			<td width="10%" align="center"><%=new DecimalFormat("#,##0").format(((RepairContentDto) changeListInfo.get(index)).getChangeCount())%></td>
			<td width="25%" align="center"><%=new DecimalFormat("#,##0.00").format(((RepairContentDto) changeListInfo.get(index)).getChangeFee())%></td>
			<td width="15%" align="center"><%=new DecimalFormat("#,##0.00").format((((RepairContentDto) changeListInfo.get(index)).getChangeFee()) * (((RepairContentDto) changeListInfo.get(index)).getChangeCount()))%>
			</td>
			<%
				} else {
			%>
			<td width="40%" align="center"></td>
			<td width="10%" align="center"></td>
			<td width="25%" align="center"></td>
			<td width="15%" align="center"></td>
			<%
				}
			%>
		</tr>
		<%
			}
				} else if (changeListInfo.size() > (pageNum + 1) * 17) {
					for (int i = 0; i < 17; i++) {
		%>
		<tr height=25>
			<td width="10%" align="center"><%=i + 1%></td>
			<td width="40%" align="center"><%=((RepairContentDto) changeListInfo.get(i + pageNum * 17)).getChangeName()%></td>
			<td width="10%" align="center"><%=new DecimalFormat("#,##0").format(((RepairContentDto) changeListInfo.get(i + pageNum * 17)).getChangeCount())%></td>
			<td width="25%" align="center"><%=new DecimalFormat("#,##0.00").format(((RepairContentDto) changeListInfo.get(i + pageNum * 17)).getChangeFee())%></td>
			<td width="15%" align="center"><%=new DecimalFormat("#,##0.00").format((((RepairContentDto) changeListInfo.get(i + pageNum * 17)).getChangeFee()) * (((RepairContentDto) changeListInfo.get(i + pageNum * 17)).getChangeCount()))%>
			</td>
		</tr>
		<%
			}
				}
		%>
		<%
			double tdSumMaterialFee = 0.00;
				if (intComponentCount > pageNum * 17 && intComponentCount < (pageNum + 1) * 17 + 1) {
					for (index = pageNum * 17; index < intComponentCount; index++) {
						tdSumMaterialFee += (((RepairContentDto) changeListInfo.get(index)).getChangeFee()) * (((RepairContentDto) changeListInfo.get(index)).getChangeCount());
					}
				} else if (intComponentCount > (pageNum + 1) * 17) {
					for (index = pageNum * 17; index < (pageNum + 1) * 17; index++) {
						tdSumMaterialFee += (((RepairContentDto) changeListInfo.get(index)).getChangeFee()) * (((RepairContentDto) changeListInfo.get(index)).getChangeCount());
					}
				}
		%>
		<tr height=30>
			<td colspan="3" align=center>
				材料费小计：
				<%=new DecimalFormat("#,##0.00").format(tdSumMaterialFee)%>
			</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<!-- 
			<tr height=30 rowspan="3">
				<td colspan="2">
					<table>
						<tr>
							<td>
								查勘定损人：
								<%=strHandlerName%>
							</td>
						</tr>
						<tr>
							<td>
								&nbsp;
							</td>
						</tr>
						<tr>
							<td align="right">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<%--=verpDate.getYear()--%>
								&nbsp;&nbsp;&nbsp;&nbsp;年
								<%--=verpDate.getMonth()--%>
								&nbsp;&nbsp;月
								<%--=verpDate.getDay()--%>
								&nbsp;&nbsp;日
							</td>
						</tr>
					</table>
				</td>
				<td colspan="3">
					<table>
						<tr>
							<td>
								核价人：
								<%=strApproverName%>
							</td>
						</tr>
						<tr>
							<td>
								&nbsp;
							</td>
						</tr>
						<tr>
							<td align="right">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<%--=verpDate.getYear()--%>
								&nbsp;&nbsp;&nbsp;&nbsp;年
								<%--=verpDate.getMonth()--%>
								&nbsp;&nbsp;月
								<%--=verpDate.getDay()--%>
								&nbsp;&nbsp;日
							</td>
						</tr>
					</table>
				</td>
			</tr>
			 -->
	</table>
	<!-- add by liuwei at 2011-02-16 签章栏 start -->
	<table border=1 width="100%" align="center" cellspacing="0" cellpadding="0" border="0" style="border-collapse: collapse" bordercolor="#111111" style="font-family:宋体; font-size:10pt;">
		<tr>
			<td height="100" align="left" valign="middle" width="25%">保险公司（签章） <br> <br> 查勘定损人： <br> 日期： <br> 核价人： <br> 日期： <br>
			</td>
			<td height="100" valign="middle" align="left" width="25%">被保险人（签章）： <br> <br> <br> <br> <br> 日期： <br>
			</td>
			<td height="100" align="left" valign="middle" width="25%">修理厂（签章）： <br> <br> <br> <br> <br> 日期： <br>
			</td>
			<td height="100" align="left" valign="middle" width="25%">第三者或受损方（签章）： <br> <br> <br> <br> <br> 日期： <br>
			</td>
		</tr>
	</table>
	<!-- add by liuwei at 2011-02-16 签章栏 start -->
	<br>
	<br>
	<%
		}
	%>
	<script language='javascript'>
      function printPage()
      {
    	//add print liudaoping 2013-04-15
          //alert("【列印】功能屬於客制化需求，暫未開發，請知悉！");
          return false;
        tbButton.style.display = "none";
        window.print();
      }
    </script>
	<!-- 按钮部分 -->
	<table id="tbButton" cellpadding="0" cellspacing="0" width="80%" align="center" style="display:">
		<tr>
			<td class=button style="width: 50%" align="center"><input type=button name=buttonPrint value=" 列 印 " class="button" onclick="return printPage()"></td>
			<td class=button style="width: 50%" align="center"><input type=button name=buttonClose value=" 关 闭 " class="button" onclick="javascript:window.close()"></td>
		</tr>
	</table>
</body>
</html>
