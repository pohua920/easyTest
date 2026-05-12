<%--
****************************************************************************
* DESC       ：显示报案文字页面(1: 出险摘要；2: 拒赔/注销原因；3: 查勘报告)，要传参数TextType
* AUTHOR     ：中科软
* CREATEDATE ：2013-01-17
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<table class="common" align="center" width="100%">
	<tr class=mline>
		<td class="common" style="text-align: left;">
			<img style="cursor: hand;" src="${ctx }/images/butExpandBlue.gif" name="RegistTextImg" onclick="showPage(this,RegistText)">
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
			<table class="common" align="center" id="RegistText" style="display:" cellspacing="1" cellpadding="5">
				<tbody>
					<tr>
						<td class="title" style="text-align: center;">
							<c:if test="${prpLregistText.id.textType == '3'}">
								<c:if test="${editType =='ADD'}">
									<textarea style="wrap: hard" rows="6" cols="80" name="prpLregistTextContextInnerHTML"></textarea>
									<!--出险摘要根据用户定义规则自动生成-->
								</c:if>
								<c:if test="${editType !='ADD'}">
									<textarea style="wrap: hard" rows="6" cols="80" name="prpLregistTextContextInnerHTML">${prpLregistText.context}</textarea>
								</c:if>
							</c:if>
							<c:if test="${prpLregistText.id.textType != '3'}">
								<textarea style="wrap: hard" rows="6" cols="80" name="prpLregistTextContextInnerHTML">${prpLregistText.context}</textarea>
							</c:if>
							<c:if test="${prpLregistText.id.textType == '1'}">
								<input type="button" name="btnRegistText" class=bigbutton value="<s:text name="button.generateCompensate.value" />" onclick="return generateRegistText();">
							</c:if>
						</td>
					</tr>
				</tbody>
			</table>
		</td>
	</tr>
</table>
<c:if test="${not empty contextStr}">
	<table class="common" cellspacing="1" cellpadding="5">
		<tr class=mline>
			<td class="common" style="text-align: left;">
				<img style="cursor: hand;" src="${ctx }/images/butExpandBlue.gif" name="RegistTextImg" onclick="showPage(this,RegistText2)">
				<s:text name="db.regist.registText.textType2" />
				<br>
				<table class="common" align="center" id="RegistText2" style="display:" cellspacing="1" cellpadding="5">
					<tbody>
						<tr>
							<td class="title" style="text-align: center;">
								<textarea style="wrap: hard" rows="6" cols="80" name="prpLregistTextContextInnerHTML2">${contextStr}</textarea>
							</td>
						</tr>
					</tbody>
				</table>
			</td>
		</tr>
	</table>
</c:if>
