<%--
****************************************************************************
* DESC       ：显示报案文字页面(1: 出险摘要；2: 拒赔/注销原因；3: 查勘报告)，要传参数TextType
* AUTHOR     ：魏士鑫
* CREATEDATE ：2004-03-03
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
  
   <table class="common" align="center" width="100%" cellpadding="5" cellspacing="1">
    <tr class=mline>
      <td class="subformtitle" style="text-align:left;">
        <img style="cursor:hand;" src="${ctx}/images/butCollapseBlue.gif"   
             name="RegistTextImg" onclick="showPage(this,RegistText)">
         <logic:equal name="prpLregistTextDto" property="textType" value="1">
               <s:text name="db.regist.registText.textType1" />
         </logic:equal> 
               <logic:equal name="prpLregistTextDto" property="textType" value="2">
               <s:text name="db.regist.registText.textType2" />
         </logic:equal>    
         <logic:equal name="prpLregistTextDto" property="textType" value="3">
               <s:text name="db.regist.registText.textType3" />
         </logic:equal>
        <br>
      <table class="common" align="center" id="RegistText" style="display:none" cellpadding="5" cellspacing="1">
          <tbody>
            <tr>
              <td class="title" style="text-align:center;" colspan="0">
              <textarea style="wrap:hard" rows="15" cols="80" name="prpLregistTextContextInnerHTML"><bean:write name="prpLregistTextDto" property="context"/></textarea>              
               
                <!--Modify by chenrenda add begin 20050410-->
                <!--Reason:根据大地需求，出险摘要根据用户定义规则自动生成-->
                 <logic:equal name="prpLregistTextDto" property="textType" value="1">
                   <input type="button" name="btnRegistText"  class=bigbutton value="<s:text name='button.generateCompensate.value' />" class="bigbutton" onclick="return generateRegistText();">
                 </logic:equal>       <%--生成出险摘要--%>
                <!--Modify by chenrenda add end 20050410-->
              </td>
            </tr>
          </tbody>
        </table>
      </td>
    </tr>
  </table>
