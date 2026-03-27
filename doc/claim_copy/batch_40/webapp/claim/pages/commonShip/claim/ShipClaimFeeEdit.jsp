<%--
****************************************************************************
* DESC       ：显示估损金额页面(非车险使用)
* AUTHOR     ：中科软
* CREATEDATE ：2004-10-15
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>


   <table class="common" align="center" style="width:100%">
    <tr class=mline>
      <td class="subformtitle" style>
        <img style="cursor:hand;" src="/claim/images/butCollapseBlue.gif"
             name="ClaimFeeImg" onclick="showPage(this,spanClaimFee)">
             <s:text name="claim.infoAmountLoss" /><%--估损金额信息--%>
             <font color="#FF0000">*</font>
	 	
        <span style="display:none"> 
          <table id="ClaimFee_Data" class="common" cellspacing="1" cellpadding="0" >
            <tbody>
      		<tr>
      		
      		   <td class="input" style="width:48%" style="align:center">
      		       		    
      		    <input type=text name="prpLclaimFeeCurrency" class="readonly" readonly style="width:30%" title="币别代码" 
                        ondblclick="code_CodeSelect(this, 'Currency');"
                        onkeyup= "code_CodeSelect(this, 'Currency');">
                    <input type=text name="prpLclaimFeeCurrencyName" class="readonly"  readonly style="width:60%" title="币别名称"  
                        ondblclick="code_CodeSelect(this, 'Currency','-1','always','none','post');"
                        onkeyup= "code_CodeSelect(this, 'Currency','-1','always','none','post');">
                  </td>
                  
      		   <td class="input" style="width:48%">
      		     <input name="prpLclaimFeeSumClaim" class=common  style="text-align:right" >
      		   </td>
                   <td class="input" style="width:4%">
                       <div align="center" >            
                        <input type=button ACCESSKEY="-" name='button_ClaimFee_Delete' class="smallbutton" value='-' onclick="deleteRow(this,'ClaimFee')">
                        <input type="hidden" name="prpLclaimFeeFlag">
                       </div>
                    
                    </td>
      	        </tr>
            </tbody>
          </table>
        </span>
         
        <span  id="spanClaimFee" style="display:none">
        <table id="ClaimFee" class="common" style="width:100%">
          <thead>
            <tr>
              <td class="centertitle" style="width:48%">
                <s:text name="db.prpLperson.currency" /><%--币别--%>
              </td>
              <td class="centertitle" style="width:48%" >
                <s:text name="db.prpLregist.estimateLoss" /><%--估损金额--%>
              </td> 
              <td  class="centertitle"  style="width:4%">
                 <input onclick="collectClaimFee();" type="button" class="button" value="<s:text name='button.summary.value' />"><%--汇总--%>
              </td>
            </tr> 
          </thead>
          <tfoot>
            <tr>
            <td class="title" colspan=2 style="width:96%" ><s:text name="prompt.schedule.addRename10" /><%--(按"+"号键增加估损金额，按"-"号键删除信息)--%>
              <td class="title" style="width:4%">
                <p align="center">
                <input onclick="insertRow('ClaimFee'); " type="smallbutton" class="button" value="+">
                
                </p>
              </td>
             </tr>
          </tfoot>
          <tbody>
          <%int indexfee=0;%>
        
           <logic:iterate id="claimFee" name="prpLclaimFeeDto" property="claimFeeList">
             <!-- 插入估损金额信息-->
             <tr>
      		
      		   <td class="input" style="width:48%" style="align:center">
      		       		    
      		    <input type=text name="prpLclaimFeeCurrency" class="readonly" readonly style="width:30%" title="币别" value="<bean:write name='claimFee' property='currency'/>"
                        ondblclick="code_CodeSelect(this, 'Currency');"
                        onkeyup= "code_CodeSelect(this, 'Currency');">
                    <input type=text name="prpLclaimFeeCurrencyName" class="readonly" readonly style="width:60%" title="币别"  value="<bean:write name='claimFee' property='currencyName'/>"
                        ondblclick="code_CodeSelect(this, 'Currency','-1','always','none','post');"
                        onkeyup= "code_CodeSelect(this, 'Currency','-1','always','none','post');">
                  </td>
                  
      		   <td class="input" style="width:48%">
      		     <input name="prpLclaimFeeSumClaim" class=common style="text-align:right" value="<bean:write name='claimFee' property='sumClaim' format="0.00" />">
      		   </td>
                   <td class="input" style="width:4%">
                       <div align="center" >            
                        <input type=button ACCESSKEY="-" name='button_ClaimFee_Delete' class="smallbutton" value='-' onclick="deleteRow(this,'ClaimFee')">
                        <input type="hidden" name="prpLclaimFeeFlag">
                       </div>
                    
                    </td>
      	        </tr>
             
             <%    indexfee++;%>
           </logic:iterate>
          </tbody>
        </table>
        <span>
      </td>
    </tr>
  </table>



