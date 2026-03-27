<%--
****************************************************************************
* DESC       ：出险後抄单打印头信息页面
* AUTHOR     ： 项目组
* CREATEDATE ： 2005-09-17
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%--初始化--%>
<%@include file="CopyNoneFormatPrintHead08Ini.jsp"%>
<html>
<head></head>
<body font-size:11pt>
	<table align="center" width="90%" style="font-family: 宋体; font-size: 11pt;">
		<tr>
			<td colspan="5" align="center" style="font-size: 14pt">
				<b> 财产保险有限公司&nbsp;&nbsp;<%=strRiskName%>承保理赔信息
				</b>
			</td>
		</tr>
		<tr>
			<td colspan="5">
				<hr align="center" width="100%" size="4" />
			</td>
		</tr>
		<tr>
			<td colspan="5">
				报案号：<%= strRegistNo %></td>
		</tr>
		<tr>
			<td colspan="5">
				保险单号：<%= strPolicyNo %></td>
		</tr>
		<tr>
			<td>
				被保险人名称：<%= strPolicyName %></td>
		</tr>
		<tr>
			<td></td>
			<td>工程所有人：</td>
			<td colspan="3">
				<%
  for(i=0;i<arrInsuredName.length;i++){
    if(arrInsuredNature[i].equals("0") && arrInsuredFlag[i].equals("1")){
%>
				<%=arrInsuredName[i]%>
				<%
    }
  }
%>
			</td>
		</tr>
		<tr>
			<td></td>
			<td>工程承包人：</td>
			<td colspan="3">
				<%
  for(i=0;i<arrInsuredName.length;i++){
    if(arrInsuredNature[i].equals("1") && arrInsuredFlag[i].equals("1")){
%>
				<%=arrInsuredName[i]%>
				<%
    }
  }
%>
			</td>
		</tr>
		<tr>
			<td></td>
			<td>工程分包人：</td>
			<td colspan="3">
				<%
  for(i=0;i<arrInsuredName.length;i++){
    if(arrInsuredNature[i].equals("2") && arrInsuredFlag[i].equals("1")){
%>
				<%=arrInsuredName[i]%>
				<%
    }
  }
%>
			</td>
		</tr>
		<tr>
			<td></td>
			<td>其它关系方：</td>
			<td colspan="3">
				<%
  for(i=0;i<arrInsuredName.length;i++){
    if(arrInsuredNature[i].equals("9") && arrInsuredFlag[i].equals("1")){
%>
				<%=arrInsuredName[i]%>
				<%
    }
  }
%>
			</td>
		</tr>
		<tr>
			<td colspan="5">
				保险工程名称：<%=strConstructionName%></td>
		</tr>
		<tr>
			<td colspan="5">保险工程地址：</td>
		</tr>
		<%
  for(i=0;i<arrItemAddress.length;i++){
%>
		<tr>
			<td align="center"><%=i+1%>、
			</td>
			<td colspan="4"><%=arrItemAddress[i]%></td>
		</tr>
		<%}%>
		<tr>
			<td colspan="5">
				<hr align="center" width="100%" size="2" />
			</td>
		</tr>
		<!-- 物质损失部分 -->
		<tr>
			<td colspan="5">物质损失部分：</td>
		</tr>
		<tr>
			<td></td>
			<td>保险标的名称</td>
			<td>币种</td>
			<td colspan="2">保险金额/赔偿限额</td>
		</tr>
		<%
  int j = 0;
  double dbSumTemp=0.0;
  for(i=0;i<strKindName.length;i++){
    if(strKindCode[i].equals("001")){
      j = j + 1;
      //zhulei leave 20051010：只是简单的金额累加，但是没有考虑币种兑换率的问题。
      dbSumTemp = dbSumTemp + dbAmount[i];
%>
		<tr>
			<td align="center"><%=j %>
			</td>
			<td><%=strItemDetailName[i]%></td>
			<td><%=strKindCurrency[i]%></td>
			<td colspan="2"><%= new DecimalFormat("#,##0.00").format(dbAmount[i])%></td>
		</tr>
		<%
    }
  }
%>
		<tr>
			<td></td>
			<td>保额小计：</td>
			<td colspan="3"><%=  new DecimalFormat("#,##0.00").format(dbSumTemp)%>
			</td>
		</tr>
		<%//非0803险种start
 if(!strRiskCode.equals("0803")){ %>
		<!-- 特种风险赔偿限额部分 -->
		<tr>
			<td colspan="5">特种风险赔偿限额：</td>
		</tr>
		<tr>
			<td></td>
			<td>特种风险种类</td>
			<td>币种</td>
			<td colspan="2">赔偿限额</td>
		</tr>
		<%
  j = 0;
  dbSumTemp = 0.0;
  for(i=0;i< strLimitType.length;i++){
    //System.out.println(">>>>=====strLimitType=="+strLimitType[i]);
    //if(arrLimitGrade[i].equals("2") && (strLimitType[i].equals("01")||strLimitType[i].equals("02")||strLimitType[i].equals("03")||strLimitType[i].equals("04")||strLimitType[i].equals("12")||strLimitType[i].equals("13")||strLimitType[i].equals("14")||strLimitType[i].equals("15")) ){ 
    if(strLimitType[i].equals("01")||strLimitType[i].equals("02")||strLimitType[i].equals("03")||strLimitType[i].equals("04")||strLimitType[i].equals("12")||strLimitType[i].equals("13")||strLimitType[i].equals("14")||strLimitType[i].equals("15")){ 
      j = j + 1;
      dbSumTemp = dbSumTemp + dbLimitFee[i];
%>
		<tr>
			<td align="center"><%=j %>
			</td>
			<td><%=arrLimitTypeName[i]%></td>
			<td><%=strLimitCurrency[i]%></td>
			<td colspan="2"><%=new DecimalFormat("0.00").format(dbLimitFee[i])%></td>
		</tr>
		<%
    }
  }
%>
		<tr>
			<td></td>
			<td>保额小计：</td>
			<td colspan="3"><%=dbSumTemp%>
			</td>
		</tr>
		<!-- 第三者责任部分 -->
		<tr>
			<td colspan="5">第三者责任部分：</td>
		</tr>
		<tr></tr>
		<td align="right">赔偿限额：
		<td colspan="4">
			每次事故赔偿限额：<%=strCurrency10%>
			<%=new DecimalFormat("0.00").format(dbLimit10)%></td>
		</tr>
		<tr></tr>
		<td align="right">其中：</td>
		<td colspan="4">
			财产损失赔偿限额：<%=strCurrency07%>
			<%=new DecimalFormat("0.00").format(dbLimit07)%>
		</td>
		</tr>
		<tr></tr>
		<td></td>
		<td colspan="4">
			人身伤亡赔偿限额：<%=strCurrency06%>
			<%=new DecimalFormat("0.00").format(dbLimit06)%>
		</td>
		</tr>
		<tr></tr>
		<td></td>
		<td colspan="4">
			每人赔偿限额：<%=strCurrency05%>
			<%=new DecimalFormat("0.00").format(dbLimit05)%>
		</td>
		</tr>
		<tr></tr>
		<td colspan="5">
			&nbsp;&nbsp;&nbsp;&nbsp;保险期内累计赔偿限额：<%=strCurrency11%><%=new DecimalFormat("#,##0.00").format(dbLimit11)%></td>
		</tr>
		<%
 //非0803险种end   
  }
  else if(strRiskCode.equals("0803")){
   
%>
		<tr>
			<td colspan="5">利润损失部分：</td>
		</tr>
		<tr>
			<td></td>
			<td>保险标的名称</td>
			<td>币种</td>
			<td colspan="2">保险金额/赔偿限额</td>
		</tr>
		<% }%>
		<%
  int m = 0;
  double sumTemp=0.0;
  for(i=0;i<strKindName.length;i++){
    if(strKindCode[i].equals("100")){
      j = j + 1;
      //zhulei leave 20051010：只是简单的金额累加，但是没有考虑币种兑换率的问题。
      sumTemp = sumTemp + dbAmount[i];
%>
		<tr>
			<td align="center"><%=j %>
			</td>
			<td><%=strItemDetailName[i]%></td>
			<td><%=strKindCurrency[i]%></td>
			<td colspan="2"><%=dbAmount[i]%></td>
		</tr>
		<%
    }
  }
%>
		<tr></tr>
		<td>每次事故绝对免赔：</td>
		<td>物质损失部分：</td>
		<td colspan="4"><%=strCurrency09%>
			<%=new DecimalFormat("0.00").format(dbLimit09)%>
		</td>
		</tr>
		<% if(!strRiskCode.equals("0803")){
   
%>
		<tr>
			<td></td>
			<td>第三者责任部分：</td>
			<td colspan="4"></td>
		</tr>
		<% }else {%>
		<tr>
			<td></td>
			<td>利润损失部分：</td>
			<td colspan="4">
				<%=new DecimalFormat("0.00").format(dbDeductible)%></td>
		</tr>
		<% }%>
		<tr>
			<td colspan="2">
				投保日期：<%=OperateDate %></td>
			<td colspan="3">
				輸入日期：<%=UnderWriteEndDate %></td>
		</tr>
		<tr>
			<td colspan="2">
				签单日期：<%=SignDate %></td>
			<td colspan="3">
				出单日期：<%=InputDate %></td>
		</tr>
		<tr>
			<td colspan="5">
				保险期限：从<%=StartDate %>
				至
				<%=EndDate %></td>
		</tr>
		<tr>
			<td colspan="5">安装期：</td>
		</tr>
		<tr>
			<td></td>
			<td colspan="4">
				从<%=strStartFixDate %>
				至
				<%=strEndFixDate %></td>
		</tr>
		<tr>
			<td colspan="5">试车期：</td>
		</tr>
		<tr>
			<td></td>
			<td colspan="4"><%=strTestPeriod%>天
			</td>
		</tr>
		<tr>
			<td></td>
			<td colspan="4">
				从<%=strTestStartDate %>
				至
				<%=strTestEndDate %></td>
		</tr>
		<tr>
			<td colspan="5">保证期：</td>
		</tr>
		<tr>
			<td></td>
			<td colspan="4">
				从<%=strStartAddDate %>
				至
				<%=strEndAddDate %></td>
		</tr>
		<tr>
			<td colspan="5">
				是否涉及联保、共保：<%= strCoinsFlag %></td>
		</tr>
		<tr>
			<td valign="top">
				<b>特别约定：</b>
			</td>
			<td colspan="4"><%=strEngage%></td>
		</tr>
	</table>
</body>
</html>