<%--
****************************************************************************
* DESC       ：显示报案文字页面()，要传参数TextType
* AUTHOR     ：qinyongli
* CREATEDATE ：2005-08-15
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ include file="/common/taglibs.jsp"%>
<table class="common">
	<tr class=mline>
		<td class="subformtitle" style="text-align: left;">
			<img style="cursor: hand;" src="${ctx }/images/butCollapseBlue.gif" name="RegistTextImg" onclick="showPage(this,RegistText)"><s:text name="prompt.queRegist.Summary"/> <br>
			<table class="common" align="center" id="RegistText" style="display: none" cellspacing="1" cellpadding="5">
				<tbody>
					<tr>
						<td class="title" style="text-align: center;">
							<textarea style="width: 700px" rows="15" cols="80" name="prpLregistTextContextInnerHTML">${prpLregistText.context}</textarea>
							<!--Reason:根据需求，报案摘要根据用户定义规则自动生成-->
							<c:if test="${prpLregistText.id.textType=='1'}">
								<input type="button" name="btnRegistText" class=bigbutton value="<s:text name="produce.queRegist.Summary"/>" onclick="return generateRegistText();">
							</c:if>
						</td>
					</tr>
				</tbody>
			</table>
		</td>
	</tr>
</table>
<c:if test="${strContext!=null&&strContext!=''}">
	<table class="common" cellspacing="1" cellpadding="5">
		<tr class=mline>
			<td class="subformtitle" style="text-align: left;">
				<img style="cursor: hand;" src="${ctx }/images/butCollapseBlue.gif" name="RegistTextImg" onclick="showPage(this,RegistText2)">
				<s:text name="db.regist.registText.textType2" />
				<br>
				<table class="common" align="center" id="RegistText2" style="display: none" cellspacing="1" cellpadding="5">
					<tbody>
						<tr>
							<td class="title" style="text-align: center;">
								<textarea style="wrap: hard" rows="15" cols="80" name="prpLregistTextContextInnerHTML2">${strContext }</textarea>
							</td>
						</tr>
					</tbody>
				</table>
			</td>
		</tr>
	</table>
</c:if>