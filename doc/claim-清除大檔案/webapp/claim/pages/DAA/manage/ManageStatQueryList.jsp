

<%
	midResultConfigDto = null;

	ArrayList arrCollectionSum = (ArrayList) session.getAttribute("CollectionSum");
	request.getAttribute("Results");

	/*
	 String strLogonUser = request.getParameter("LogonUser");
	 String strComCode = request.getParameter("ComCode");
	 String strCompanyFlag = request.getParameter("CompanyFlag");
	 String strConfigOwner = request.getParameter("ConfigOwner");
	 String strRiskCodeSign = request.getParameter("RiskCodeSign");
	 String strRiskCode = request.getParameter("RiskCode");
	 String strDateType = request.getParameter("DateType");
	 String strOverDays = request.getParameter("OverDays");
	 String strDateStart = request.getParameter("DateStart");
	 String strDateEnd = request.getParameter("DateEnd");
	 String strPageNo = request.getParameter("PageNo");
	 */
	String strQueryFlag = request.getParameter("QueryFlag");
	String strResultType = request.getParameter("ResultType");
	String strQueryStyle = request.getParameter("QueryStyle");
	String strReportType = request.getParameter("ReportType");

	String strItemColumnName = "";
	String strItemColumn = "";
	String strGif = "";

	//  int intRowsPerPage = Integer.parseInt(AppConfig.get("sysconst.ROWS_PERPAGE"));
	int intRowsPerPage = 100;
	int intPageNo = 0;
	i = 0;
	int j = 0;
	if (strQueryFlag == null || strQueryFlag.length() == 0)
		strQueryFlag = "";
	if (strResultType == null || strResultType.length() == 0)
		strResultType = "";
	if (strQueryStyle == null || strQueryStyle.length() == 0)
		strQueryStyle = "1";
	/*  
	 if(strLogonUser==null||strLogonUser.length()==0)
	 strLogonUser = "";
	 if(strComCode==null||strComCode.length()==0)
	 strComCode = "";
	 if(strCompanyFlag==null||strCompanyFlag.length()==0)
	 strCompanyFlag = "1";
	 if(strConfigOwner==null||strConfigOwner.length()==0)
	 strConfigOwner = "";
	 if(strRiskCodeSign==null||strRiskCodeSign.length()==0)
	 strRiskCodeSign = "";
	 if(strRiskCode==null||strRiskCode.length()==0)
	 strRiskCode = "";
	 if(strDateType==null||strDateType.length()==0)
	 strDateType = "";
	 if(strOverDays==null||strOverDays.length()==0)
	 strOverDays = "";
	 if(strDateStart==null||strDateStart.length()==0)
	 strDateStart = "";
	 if(strDateEnd==null||strDateEnd.length()==0)
	 strDateEnd = "";
	 if(strPageNo==null||strPageNo.length()==0)
	 intPageNo = 1;
	 else
	 intPageNo = Integer.parseInt(strPageNo);

	 strGif = "ImgAnaRepyn"+strResultType.substring(5)+".GIF";
	 */
%>
<script language="javascript">
/**
*@保存excel
*/
function saveExcel() {
	fm.ExcelFlag.value = "1";
	fm.PageFlag.value = "0";
	fm.QueryStyle.value = "1";
	fm.submit();
	fm.QueryStyle.value = "6";
}
</script>
<table border="0" align="center" cellpadding="0" cellspacing="0" class="common">
	<tr>
		<td width="50">
			<img src="/claim/images/BtnAnaRepEduceExcel.GIF" class="button" onclick="saveExcel();">
		</td>
		<td width="50">
			<img src="/claim/images/BtnAnaRepPrint.gif" class="button" onclick="print();return false;">
		</td>
	</tr>
</table>
<table class=common cellpadding="5" cellspacing="1">
	<tr>
		<td colspan=5 class="formtitle">
			<s:text name="manage.queryResult" />
		</td>
	</tr>
	<!--查询结果信息-->
	<tr>
		<%
			//System.out.println("lijiyuan hurry11");

			//  if(arrCollectionSum.size()!=0)
			if (resultSumConfig.size() != 0)

			{
		%>
		<td class="centertitle">
			<s:text name="db.prpLCitemKind.itemKindNo" />
		</td>
		<!--序号-->
		<%
			for (i = 0; i < resultSumConfig.size(); i++) {
					midResultConfigDto = new MidResultConfigDto();
					midResultConfigDto = (MidResultConfigDto) resultSumConfig.get(i);
					strItemColumnName = midResultConfigDto.getItemColumnName();
		%>
		<td class="centertitle"><%=strItemColumnName%></td>
		<%
			}
			}
		%>
	</tr>
	<logic:notEmpty name="Results">
		<logic:iterate id="Results" name="Results">
			<%
				j = j + 1;
			%>
			<tr class=listodd>
				<%
					if (arrCollectionSum == null)
								return;
							if (arrCollectionSum.size() != 0) {
				%>
				<!--              <td class="common"><%=j + (intPageNo - 1) * intRowsPerPage%></td>-->
				<td class="common"><%=j%></td>
				<%
					for (i = 0; i < arrCollectionSum.size(); i++) {
									midResultConfigDto = new MidResultConfigDto();
									midResultConfigDto = (MidResultConfigDto) arrCollectionSum.get(i);

									strItemColumn = midResultConfigDto.getItemColumn();
									//System.out.println(strItemColumn);
									if (strItemColumn.equals("comCode") || strItemColumn.equals("handler1Code") || strItemColumn.equals("operatorCode"))
										strItemColumn = strItemColumn + "Name";
				%>
				<td align="center">
					<bean:write name="Results" property="<%=strItemColumn%>" />
				</td>
				<%
					}
							}
				%>
			</tr>
		</logic:iterate>
	</logic:notEmpty>
	<logic:notEmpty name="AbstractDto">
		<input type="hidden" name="PageCount" value="<bean:write format="#00" name="AbstractDto" property="pageCount"/>">
		<input type="hidden" name="PageNo" value="<bean:write format="#00" name="AbstractDto" property="pageNo"/>">
		<input type="hidden" name="PrePageNo" value="<bean:write format="#00" name="AbstractDto" property="prePageNo"/>">
		<input type="hidden" name="NextPageNo" value="<bean:write format="#00" name="AbstractDto" property="nextPageNo"/>">
		<input type="hidden" name="CurrentPage">
</table>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr class=common>
		<td><s:text name="manage.total" />
			<bean:write name="AbstractDto" property="pageCount" />
			<s:text name="navigator.page" />，<s:text name="manage.total" />
			<bean:write name="AbstractDto" property="rowsCount" />
			<s:text name="manage.article" /></td>
	</tr>
	<!--共-->
	<!--页-->
	<!--共-->
	<!--条-->
	<tr>
		<td align="right"><s:text name="manage.subsection" />
			<bean:write name="AbstractDto" property="pageNo" />
			<s:text name="navigator.page" />，<!--第-->
			<!--页--> <a href="#" onclick="return previousPage();" class="color"> <s:text name="manage.previousPage" />
				<!--上一页-->
		</a> <a href="#" onclick="return nextPage();" class="color"> <s:text name="manage.nextPage" />
				<!--下一页-->
		</a> <s:text name="manage.skip" />
			<!--跳到--> <input type="text" name="GoPageNo" class="common1" style="width: 40px"> <img src="images/BtnAnaRepGo.gif" width="23" height="13" align="absmiddle" class="button"
			onclick="return goPage();"></td>
	</tr>
</table>
</td>
</tr>
</logic:notEmpty>
</table>
</td>
</tr>
</table>
