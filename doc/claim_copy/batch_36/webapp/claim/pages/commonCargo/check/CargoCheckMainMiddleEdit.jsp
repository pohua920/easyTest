<tr>
	<td class="title">
		<s:text name="check.treatmentUnits" />
		：
	</td>
	<%-- 事故处理单位 --%>
	<td class="input" colspan="3">
		<input type="input" name="prpLcheckHandleUnit" class="codecode"
			style="width: 40%" description="处理部门代码"
			value="${prpLcheck.handleUnit}">
		<input name='prpLcheckHandleUnitName' class='codename' maxlength=60
			style="width: 55%" description="处理部门"
			value="${prpLcheck.handleUnitName}"
			querytype="always" codetype="ComCode" coderelation="-1"
			codelimit="none" style="width:340px">
	</td>
</tr>
