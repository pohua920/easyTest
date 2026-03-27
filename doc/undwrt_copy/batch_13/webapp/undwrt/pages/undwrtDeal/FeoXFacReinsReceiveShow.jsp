<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/i18njs.jsp"%>
<table class="common" border="0" cellspacing="0" cellpadding="4">
	<input type="hidden" name="sumPremium" value="">
	<tr>
		<td width="2%"></td>
		<td class="common" colspan="9" style="text-align:left"><s:text name="undwrt.pages.undwrtDeal.FeoXFacReinsReceiveShow"/>
			<br>
			<span id="spanReins" style="">
				<table class="list" border="0" cellpadding="5" cellspacing="1" id="Reins" name="Reins">
					<thead>
						<tr>
							<td class="listtitle" width="20%" colspan=2 align=center><s:text name="undwrt.pages.undwrtDeal.accepter"/></td>
							<td class="listtitle" width="8%" align=left><s:text name="undwrt.pages.undwrtDeal.broker"/></td>
							<td class="listtitle" width="20%" colspan=2 align=center><s:text name="undwrt.pages.undwrtDeal.finalAccepter"/></td>
							<td class="listtitle" width="10%" align=right><s:text name="undwrt.pages.undwrtDeal.acceptShare"/>(%)</td>
							<td class="listtitle" width="15%" align=right><s:text name="undwrt.pages.undwrtDeal.dividePremium"/></td>
						</tr>
					</thead>
					<tbody>
					<s:if test="#feoXFacDto.feoXReinsDtoList!=null">
					        <s:iterator id="feoXReinsDto" status="statu" value="#feoXFacDto.feoXReinsDtoList">
								<tr>
									<td class="queryresult" align="left">
									    <s:property value="#feoXReinsDto.reinsCode" />
									</td>
									<td class="queryresult" align="left">
										<s:property value="#feoXReinsDto.reinsName" />
									</td>
                                    <s:if test='#feoXReinsDto.brokerFlag=="1"'>
										<td class="queryresult" align="center">
											<input type="checkbox" name="broker" checked="true" disabled>
										</td>
									</s:if>
									<s:if test='#feoXReinsDto.brokerFlag=="0"'>
										<td class="queryresult" align="center">
											<input type="checkbox" name="broker" disabled>
										</td>
									</s:if>

									<td class="queryresult" align="center">
										<s:property value="#feoXReinsDto.freinsCode" />
									</td>
									<td class="queryresult" align="left">
										<s:property value="#feoXReinsDto.freinsName" />
									</td>
									<td class="queryresult" align="center">
										<fmt:formatNumber value="${feoXReinsDto.shareRate}" pattern="#,##0.000000"/>
									</td>
									<td class="queryresult" align="right">
										<fmt:formatNumber value="${feoXReinsDto.premium}" pattern="#,##0.00"/>
									</td>
								</tr>
							</s:iterator>
						</s:if>
					</tbody>
				</table>
			</span>
		</td>
	</tr>
</table>

