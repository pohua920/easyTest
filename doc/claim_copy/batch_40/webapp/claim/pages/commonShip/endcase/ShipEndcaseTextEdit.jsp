<!--
****************************************************************************
* DESC       ：显示结案文字页面
* AUTHOR     : 理赔组
* CREATEDATE ：2004-06-28
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------ 
****************************************************************************
-->  
<table class="common" align="center" width="100%">
  <tr> 
    <td class="subformtitle" style="text-align:left;">
      <img style="cursor:hand;" src="${ctx}/images/butCollapseBlue.gif" name="EndcaseTextImg" onclick="showPage(this,EndcaseText)"><s:text name="db.prpLltext.text08" />：<%--结案报告--%>
      <br>
      <table class="common" align="center" id="EndcaseText" style="display:none">
        <tbody>
          <tr>
            <td class="input" style="text-align:center;" colspan="0">
<textarea style="wrap:hard" rows="15" cols="80" name="prpLltextContextInnerHTML">${prpLltext.context}</textarea>                               
            </td>
          </tr>
        </tbody>  
      </table>
    </td>
  </tr>
</table>
