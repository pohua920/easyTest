<%--
****************************************************************************
* DESC       ：显示立案文字页面(1出险摘要/2查勘报告/3结案报告)，要传参数TextType
* AUTHOR     ：中科软
* CREATEDATE ：2004-06-17
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
*这里需要重新整理texttype的值，有比较严重的问题
****************************************************************************
--%>
<table class="common" align="center" width="100%">
	<tr>
		<td class="subformtitle" style="text-align: left">
			<img style="cursor: hand;" src="/claim/images/butCollapseBlue.gif" name="LtextImg" onclick="showPage(this,Ltext)">
			<logic:equal name="prpLltextDto" property="textType" value="1">
				<s:text name="db.prpLltext.text1" />
				<%-- 查勘报告--%>
			</logic:equal>
			<logic:equal name="prpLltextDto" property="textType" value="2">
				<s:text name="db.prpLltext.text2" />
				<%--结案报告--%>
			</logic:equal>
			<logic:equal name="prpLltextDto" property="textType" value="3">
				<s:text name="db.prpLltext.text3" />
				<%--[texttype=1] 出险摘要--%>
			</logic:equal>
			<logic:equal name="prpLltextDto" property="textType" value="4">
				<s:text name="db.prpLltext.text4" />
				<%-- 立案报告--%>
			</logic:equal>
			<logic:equal name="prpLltextDto" property="textType" value="5">
				<s:text name="db.prpLltext.text5" />
				<%-- 追偿及转让文字--%>
			</logic:equal>
			<logic:equal name="prpLltextDto" property="textType" value="08">
				<s:text name="db.prpLltext.text08" />
				<%-- 结案登记上的结案报告 --%>
			</logic:equal>
			<logic:equal name="prpLltextDto" property="textType" value="09">
			<%-- 出险摘要 --%>
           	  事故摘要
           </logic:equal>
			<logic:equal name="prpLltextDto" property="textType" value="10">
				<s:text name="db.prpLltext.text10" />
				<%-- 注销/拒赔文本 --%>
			</logic:equal>
			<br>
			<table class="common" align="center" id="Ltext" style="display: none">
				<tbody>
					<tr>
						<td class="title" style="text-align: center;">
							<textarea style="wrap: hard" rows="15" cols="80" name="prpLltextContextInnerHTML"><bean:write name="prpLltextDto" property="context" /></textarea>
						</td>
					</tr>
				</tbody>
			</table>
		</td>
	</tr>
</table>