<%--
****************************************************************************
* DESC       ：理赔打印公用标题页面
* AUTHOR     ：理赔组
* CREATEDATE ：2004-11-24  
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<table width="96%" align="center" cellspacing="0" cellpadding="0" border="0">
	<tr>
		<td align=center height="25" class=MsoNormal style="TEXT-ALIGN: center; TEXT-INDENT: 0.7pt">
			<b style="mso-bidi-font-weight: normal"> <SPAN lang=EN-US
				style="COLOR: red; FONT-FAMILY: 'Arial Black'; FONT-SIZE: 36pt; LETTER-SPACING: -4pt; mso-bidi-font-size: 10.0pt; mso-fareast-font-family: 楷体_GB2312">CCIC</SPAN>
			</b> <b style="mso-bidi-font-weight: normal"> <SPAN lang=EN-US style="FONT-FAMILY: 'Lucida Sans Unicode'; FONT-SIZE: 24pt; mso-bidi-font-size: 10.0pt; mso-bidi-font-family: 'Times New Roman'">
					<SPAN style="mso-spacerun: yes">&nbsp;</SPAN>
			</SPAN>
			</b> <b style="mso-bidi-font-weight: normal"> <SPAN
				style="FONT-FAMILY: 隶书; FONT-SIZE: 24pt; mso-bidi-font-size: 10.0pt; mso-ascii-font-family: 'Lucida Sans Unicode'; mso-hansi-font-family: 'Lucida Sans Unicode'">财产保险有限公司</SPAN>
			</b>
		</td>
	</tr>
	<tr>
		<td align=center height="25">
			<b> <span lang=EN-US style="LETTER-SPACING: 2pt; mso-fareast-font-family: 黑体; mso-font-width: 90%">China Continent Property&Casualty Insurance Company Ltd.</span>
			</b>
		</td>
	</tr>
	<tr>
		<td colspan="2" height="40" align=top align=center style="text-align: center; font-family: 宋体; font-size: 14pt;">
			<B><%=request.getParameter("PrintTitle")%><B>
		</td>
	</tr>
</table>
