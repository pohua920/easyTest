<%--
****************************************************************************
* DESC       ：显示报案文字页面(1: 出险摘要；2: 拒赔/注销原因；3: 查勘报告)，要传参数TextType
* AUTHOR     ：魏士鑫
* CREATEDATE ：2004-03-03
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ include file="/common/taglibs.jsp"%>
<table class="common" align="center" width="100%">
	<tr class=mline>
		<td style="text-align: left;" class="subformtitle">
			<img style="cursor: hand;" src="${ctx }/images/butCollapseBlue.gif" name="RegistTextImg" onclick="showPage(this,RegistText)">
			<c:if test="${prpLregistText.id.textType == '1'}">
				<s:text name="db.regist.registText.textType1" />
				<!-- 出险摘要 -->
			</c:if>
			<c:if test="${prpLregistText.id.textType == '2'}">
				<s:text name="db.regist.registText.textType2" />
				<!-- 拒赔文字-->
			</c:if>
			<c:if test="${prpLregistText.id.textType == '3'}">
				<s:text name="db.regist.registText.textType3" />
				<!-- 查勘报告 -->
			</c:if>
			<br>
			<table class="common" align="center" id="RegistText" style="display: none">
				<tbody>
					<tr>
						<td class="title" style="text-align: center;" colspan="0">
							<textarea style="wrap: hard" rows="15" cols="80" name="prpLregistTextContextInnerHTML">${prpLregistText.context}</textarea>
							<c:if test="${prpLregistText.id.textType=='1'}">
								<input type="button" name="btnRegistText" value="<s:text name='button.generateCompensate.value' />" class="bigbutton" onclick="return generateRegistText();">
								<%--生成出险摘要--%>
							</c:if>
						</td>
					</tr>
				</tbody>
			</table>
		</td>
	</tr>
</table>
