<%--
****************************************************************************
* DESC       ：未决赔款汇总查询结果显示
* AUTHOR     ：中科软
* CREATEDATE ：2004-09-28
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app"%>
<%@ page import="com.sinosoft.claim.dto.domain.*"%>
<%@ page import="java.text.*"%>
<html locale="true">
<head>
<app:css />
<title><s:text name="query.claimsSummaryList" /> <%--未决赔款汇总清单 --%></title>
<script src="/claim/common/js/showpage.js">
	
</script>
<script language=javascript>
	function reLoadList() {
		document.location.reload()
	}
</script>
<html:base />
</head>
<body onkeydown="if (event.keyCode==116){ reLoadList()}">
	<base target="_self">
	<form name="fm" method="post">
		<table class="common" cellpadding="5" cellspacing="1">
			<tr>
				<td colspan=12 class="formtitle">
					<s:text name="query.noClaimsShowList" />
					<%--未决赔款显示列表 --%>
				</td>
			</tr>
			<tr>
				<td class="centertitle" style="width: 5%">
					<s:text name="db.prpDrisk.riskCode" />
					<%--险种代码 --%>
				</td>
				<td class="centertitle" style="width: 8%">
					<s:text name="check.claimNum" />
					<%--赔案号 --%>
				</td>
				<td class="centertitle" style="width: 8%">
					<s:text name="prompt.queRegist.PolicyNo" />
					<%--保单号 --%>
				</td>
				<td class="centertitle" style="width: 19%">
					<s:text name="db.prpLregist.insuredName" />
					<%--被保险人 --%>
				</td>
				<td class="centertitle" style="width: 8%">
					<s:text name="db.prpLCMain.startDate" />
					<%--起保日期 --%>
				</td>
				<td class="centertitle" style="width: 8%">
					<s:text name="db.prpLclaim.claimDate" />
					<%--立案日期 --%>
				</td>
				<td class="centertitle" style="width: 8%">
					<s:text name="db.prpLclaim.endCaseDate" />
					<%--结案日期 --%>
				</td>
				<td class="centertitle" style="width: 4%">
					<s:text name="db.prpLclaimpolicy.currency" />
					<%--币别 --%>
				</td>
				<td class="centertitle" style="width: 8%">
					<s:text name="db.prpLregist.estimateLoss" />
					<%--估损金额 --%>
				</td>
				<td class="centertitle" style="width: 8%">
					<s:text name="query.takeResponsibility" />
					<%--自负责任 --%>
				</td>
				<td class="centertitle" style="width: 8%">
					<s:text name="db.prpCmain.makeCom" />
					<%--出单机构 --%>
				</td>
				<td class="centertitle" style="width: 8%">
					<s:text name="db.prpLregist.handler1Name" />
					<%--经办人 --%>
				</td>
				<input type="hidden" name="sumClaim" value=0>
				<input type="hidden" name="sumDefLoss" value=0>
			</tr>
			<%int index=0;%>
			<% double sumClaim=0;
      double sumDefLoss=0;

     PrpLclaimDto prpLclaimDto= null;
     PrpLclaimDto prpLclaimDto1 = (PrpLclaimDto)request.getAttribute("prpLclaimDto");
      %>
			<logic:notEmpty name="prpLclaimDto" property="claimList">
				<logic:iterate id="prpLclaim1" name="prpLclaimDto" property="claimList">
					<%
          if(index %2== 0)
               out.print("<tr class=listodd>");
          else
               out.print("<tr class=listeven>");
%>
					<tr class=common>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="left"></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="right"></td>
						<td align="right"></td>
						<%

            prpLclaimDto = (PrpLclaimDto) ((ArrayList)prpLclaimDto1.getClaimList()).get(index);
            sumClaim = sumClaim+prpLclaimDto.getSumClaim();
            sumDefLoss =sumDefLoss+ prpLclaimDto.getSumDefLoss();

   %>
						<td align="center">》</td>
						<td align="center">
							-
							<bean:write name="prpLclaim1" property="handlerCode" />
						</td>
					</tr>
					<%        index++;%>
				</logic:iterate>
			</logic:notEmpty>
			<tr class="listtail">
				<td colspan="12">
					<% DecimalFormat nf = new DecimalFormat("###,##0.00");%>
					<s:text name="query.sum1" />
					<%--总计：共--%><%= index --%><s:text name="query.sum2" />
					<%--笔报案  总估损金额：共--%><%= nf.format(sumClaim)--%><s:text name="query.sum3" />
					<%--元  总自负责任金额：共--%><%= nf.format(sumDefLoss)--%><s:text name="print.yuan" />
					<%--元 --%>
					<input type="hidden" name="conditions" value=<bean:write name="prpLclaimDto" property="remark"/>>
				</td>
			<tr>
				<td colspan=12 class=button>
					<input type=button value=" <s:text name="query.claimsSummaryQueryResult" />关  闭 " class="button" onclick="window.close()">
				</td>
			</tr>
			</tr>
		</table>
		</tr>
		</table>
	</form>
</body>
</htm-l:html>