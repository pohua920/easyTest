      <tr>         
        <td class="title" style="width:15%"><s:text name="commonAcci.check.accidentTreatUnit" />：</td><%--事故处理单位--%>
        <td class="input" style="width:85%" colspan="3">
        <input type ="input" name="prpLcheckHandleUnit" class="codecode" style="width:40%" description="<s:text name='certainLoss.prpLscheduleMainWF.Unitcode'/>" value="${prpLcheck.handleUnit}">
        <input name='prpLcheckHandleUnitName' class='codename' maxlength=60  style="width:55%" description="<s:text name='certainLoss.prpLscheduleMainWF.Unit'/>"  value="${prpLcheck.handleUnitName}"
          querytype="always" codetype="ComCode" coderelation="-1" codelimit="none"  style="width:340px">
        </td>
      </tr>       
   