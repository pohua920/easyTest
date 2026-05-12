<%--
****************************************************************************
* DESC       ：显示「审核片语」及「审核意见」二个栏位
* AUTHOR     ：中科软
* MODIFYLIST ：Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@	page contentType="text/html; charset=GBK" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<c:if test="${needUndwrtFlag == 'Y'}">
	<c:if test="${sendUndwrtFlag == 'Y'}">
		<table class="subtable" cellpadding="0" cellspacing="1" width="100%" border="0">
			<tr>
				<td>
					<table class="common" cellpadding="0" cellspacing="0" width="100%">
						<tr>
							<td class="left"></td>
							<td class="right"></td>
							<td class="left"></td>
							<td class="right"></td>
							<td class="left"></td>
							<td class="right"></td>
						</tr>
						<tr>
							<td class="left">
								<s:text name="common.check.phrase" />:
							</td>
							<td class="right">
								<select id="undwrtPhrase" name="undwrtPhrase">
									<option value="A" selected="selected">
										<s:text name="common.check.agree"/><%--同意--%>
									</option>
									<option value="B">
										<s:text name="common.check.needReview"/><%--需复核--%>
									</option>
									<option value="C">
										<s:text name="common.check.notAgree"/><%--不同意--%>
									</option>
								</select>
							</td>
							<td class="left" colspan="4"></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<br>
		<table class="common" align="center" width="100%">
			<tr class=mline>
				<td style="text-align: left;" class="left">
					<img style="cursor: hand;" src="${ctx }/images/butCollapseBlue.gif"
						name="UndwrtTextImg" onclick="showPage(this,UndwrtText)">
					<s:text name='guarantee.checkedAdvice'/><%-- 审核意见 --%>
					<br>
					<table class="common" align="center" id="UndwrtText" style="display: none">
						<tbody>
							<tr>
								<td class="title" style="text-align: center;" colspan="0">
									<textarea style="wrap: hard;" rows="15" cols="80" id="undwrtTextContextInnerHTML" name="undwrtTextContextInnerHTML"></textarea>
								</td>
							</tr>
						</tbody>
					</table>
				</td>
			</tr>
		</table>
		</c:if>
	</c:if>