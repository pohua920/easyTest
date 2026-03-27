<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/i18njs.jsp"%>
<table class="common" cellpadding="5" cellspacing="1" align="center"
	border="0">
	<tr>
		<td class="common" style="text-align: left">
			<img style="cursor: hand;" src="/undwrt/common/images/butExpand.gif"
				name="clauseImg" onclick="showPage(this,spanxfac);">&nbsp;&nbsp;<s:text name="undwrt.pages.undwrtDeal.FeoXLayerShow"/>
			<br>
			<span id="spanxfac" style="">
				<table id="xfac" name="xfac" class="common"  cellpadding="5" cellspacing="1" border="0">
					<tbody>
					<s:if test="#request.feoXFacDtoList!=null">
					<s:iterator id="feoXFacDto" status="statu" value="#request.feoXFacDtoList">
								<tr>
									<td>
										<table class="common" cellpadding="3" cellspacing="0">
											<thead>
												<tr class=listtitle>
													<td class="listtitle" style="width: 5%"><s:text name="undwrt.pages.undwrtDeal.hierarchyCode"/></td>
													<td class="listtitle" style="width: 5%"><s:text name="undwrt.pages.undwrtDeal.currency"/></td>
													<td class="listtitle" style="width: 10%"><s:text name="undwrt.pages.undwrtDeal.attachmentPoint"/></td>
													<td class="listtitle" style="width: 10%"><s:text name="undwrt.pages.undwrtDeal.indemnityLimit"/></td>
													<td class="listtitle" style="width: 10%"><s:text name="undwrt.pages.undwrtDeal.hierarchyPremium"/></td>
													<td class="listtitle" style="width: 10%"><s:text name="undwrt.pages.undwrtDeal.disengagementShare"/>(%)</td>
													<td class="listtitle" style="width: 10%"><s:text name="undwrt.pages.undwrtDeal.hierarchyDividePremium"/></td>
													<td class="listtitle" style="width: 10%"><s:text name="undwrt.pages.undwrtDeal.recoveryNumber"/></td>
													<td class="listtitle" style="width: 10%"><s:text name="undwrt.pages.undwrtDeal.recoverPremiumType"/></td>
													<td class="listtitle" style="width: 10%"><s:text name="undwrt.pages.undwrtDeal.sumResponsibilityLimit"/></td>
													<td class="listtitle" style="width: 10%"><s:text name="undwrt.pages.undwrtDeal.recoverRatio"/>(%)</td>
												</tr>
											</thead>
											<tbody>
												<tr class=common>
													<td align=center><s:property value="#feoXFacDto.feoXLayerDto.layerNo" /></td>
													<td><s:property value="#feoXFacDto.feoXLayerDto.currency" /></td>
													<td align=right><fmt:formatNumber value="${feoXLayerDto.excessLoss}" pattern="#,##0.00"/></td>
													<td align=right><fmt:formatNumber value="${feoXLayerDto.layerQuota}" pattern="#,##0.00"/></td>
													<td align=right><fmt:formatNumber value="${feoXLayerDto.layerPremium}" pattern="#,##0.00"/></td>
													<td align=right><fmt:formatNumber value="${feoXLayerDto.shareRate}" pattern="#,##0.00"/></td>
													<td align=right><fmt:formatNumber value="${feoXLayerDto.layerRePreium}" pattern="#,##0.00"/></td>
													<td align=right><bean:write name="feoXFacDto" property="feoXLayerDto.reinstTimes" /></td>
													<td align=right><select name="reinstType" class="five">
													      <s:if test='#feoXFacDto.feoXLayerDto.reinstType=="0"'>
																<option value="0" selected="selected">free</option>
															</s:if>
															<s:if test='#feoXFacDto.feoXLayerDto.reinstType=="1"'>
																<option value="1" selected="selected"><s:text name="undwrt.pages.undwrtDeal.regularRatioRecover"/></option>
															</s:if>
															<s:if test='#feoXFacDto.feoXLayerDto.reinstType=="2"'>
																<option value="2" selected="selected"><s:text name="undwrt.pages.undwrtDeal.dateRatioRecover"/></option>
															</s:if>
														</select>
													</td>
													<td align=right><fmt:formatNumber value="${feoXLayerDto.totalQuota}" pattern="#,##0.00"/></td>
													<td align=right><fmt:formatNumber value="${feoXLayerDto.reinstRate}" pattern="#,##0.00"/></td>
												</tr>
											</tbody>
										</table>
									</td>
								</tr>
								<tr>
									<td colspan="11">
										<%--临分超赔接受人信息--%>
										<%@include file="FeoXFacReinsReceiveShow.jsp"%>
									</td>
								</tr>
							</s:iterator>
						</s:if>
					</tbody>
				</table> </span>
</table>