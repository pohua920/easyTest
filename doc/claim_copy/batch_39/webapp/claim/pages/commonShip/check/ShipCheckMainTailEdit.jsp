      <tr>         
        <td class="title" ><s:text name="check.shipName" />:</td><%--货主名称--%>
        <td class="input"  colspan="3">
          <input type="text" name="prpLextSalvor" class="input" style="width:100px" value="<bean:write name='prpLextDto' property='salvor'/>">
          <img src="/claim/images/bgMarkMustInput.jpg">
          
        </td>
      </tr>
     <tr>         
        <td class="title" ><s:text name="check.dischargeDate" />:</td> <%--卸货日期--%>
        <td class="input" >
        <input type="text" name="prpLextUnloadDate" class="input" style="width:140px"  value="<bean:write name='prpLextDto' property='unloadDate'/>" >
        </td>

        <td class="title" ><s:text name="check.ladeBill" />:</td>   <%--提单/运单--%>
        <td class="input" >
          <input type="text" name="prpLextLoadingNo" class="input" style="width:100%"  value="<bean:write name='prpLextDto' property='remark'/>" >
        </td>      
      </tr>
      

	

      <tr>         
        <td class="title" ><s:text name="certainLoss.prpLacciCheck.prpLacciCheckRemark"/></td><%--备  注--%>
        <td class="input"  colspan="3">
          <textarea  style="width:600px;overflow-x:visible;" name='prpLcheckRemark' rows=4 cols=40 title="备注"><bean:write name='prpLcheckDto' property='remark'/></textarea>
        </td>
      </tr>        
    </table>