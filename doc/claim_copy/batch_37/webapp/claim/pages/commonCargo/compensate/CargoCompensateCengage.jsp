
<%--
****************************************************************************
* DESC       ：特别约定显示画面
* AUTHOR     ：理赔组
* CREATEDATE ： 2004-05-20
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<!--建立显示的录入条，可以收缩显示的-->   
   <table class="common" align="center" width="100%">
    <tr class=mline>
      <td class="subformtitle" colspan="4" style="text-align:left">
        <img style="cursor:hand;" src="/claim/images/butCollapseBlue.gif"
          name="EngageImg" onclick="showPage(this,spanEngage)"><s:text name="commonAcci.compensate.specificallyAgreed"/><br> <%-- 特别约定 --%>
        <span style="display:none">
          <table id="Engage_Data">
            <tbody>   
            </tbody>
          </table>
        </span>

        <span id="spanEngage" style="display:none">
          <table class="sub" style='width:100%' id="Engage">
            <thead>
              <tr>
                <td class="centertitle"><s:text name="regist.prpLregist.serialNo"/></td>  <%-- 序号 --%>
                <td class="centertitle"><s:text name="certainLoss.prpLcheck.specializeCode"/></td> <%--  特约代码--%>
                <td class="centertitle" colspan="3"><s:text name="certainLoss.prpLcheck.specializeName"/></td> <%--特约名称  --%>
              </tr>
            </thead>
            <tbody>
          <logic:notEmpty  name="prpCengageDto"  property="prpCengageList"> 
         <logic:iterate id="cengage" name="prpCengageDto" property="prpCengageList">
              <tr class=oddrow>
                <td class="centertitle"><bean:write name='cengage' property='serialNo'/></td>
                <td class="centertitle"><bean:write name='cengage' property='clauseCode'/></td>
                <td class="centertitle"><bean:write name='cengage' property='clauses'/></td> 
                <td class="centertitle"><input type=button ACCESSKEY="." class="smallbutton" value='...' name='button_Engage_Open_Context'
                      onclick="buttonOnClick(this,'ClauseCodeSpeContext',-2,1,'span_Engage_Context');">
                  <span id="span_Engage_Context"
                        style='width:520;display:none;position:absolute;background-color:FFFFFF;'>
                    <table class=sub>
                      <tr>
                        <td class=title><textarea name=ClausesContext rows=20 cols=80 class=readonly readonly><bean:write name='cengage' property='context'/></textarea></td>
                      </tr>
                      <tr> 
                        <td colspan=2 align=center>
                          <input type=button name = 'button_Engage_Close_Context' class="button" value='(O)<s:text name="button.determine.value"/>' <%-- 确定 --%>
                            ACCESSKEY="O"
                            onclick="hideSubPage(this,'span_Engage_Context')">
                        </td>
                      </tr>
                    </table>   
                  </span>
                </td>
              </tr>
        </logic:iterate>
      </logic:notEmpty>
            </tbody>
          </table>
        </span>
      </td>
    </tr>
  </table>
 
  
