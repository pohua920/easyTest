
<tr>
	<td class="title" style="width: 15%">
		<s:text name="commonAcci.check.accidentTreatUnit" />：
	</td>
	<%--事故处理单位--%>
	<td class="input" style="width: 85%" colspan="3">
		<input type="input" name="prpLcheckHandleUnit" class="codecode" style="width: 40%" description="处理部门代码" value="<bean:write name='prpLcheckDto' property='handleUnit'/>">
		<!-- mantis： CLM0017，處理人員：Sam，需求單編號：CLM0017，原住名姓名調整作業_車 -->
		<input name='prpLcheckHandleUnitName' class='codename' maxlength="100" style="width: 55%" description="处理部门" value="<bean:write name='prpLcheckDto' property='handleUnitName'/>" querytype="always" codetype="ComCode" coderelation="-1" codelimit="none" style="width:340px">
	</td>
</tr>
