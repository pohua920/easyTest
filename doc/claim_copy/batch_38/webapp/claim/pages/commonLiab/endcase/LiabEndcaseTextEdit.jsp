<!--
****************************************************************************
* DESC       ：显示结案文字页面
* AUTHOR     : 中科软
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------ 
****************************************************************************
-->  
<%@ include file="/common/taglibs.jsp"%>
<table class="common" align="center" width="100%">
  <tr> 
    <td class="subformtitle" style="text-align:left;">
      <img style="cursor:hand;" src="/claim/images/butCollapseBlue.gif" name="EndcaseTextImg" onclick="showPage(this,EndcaseText)"><s:text name="db.prpLltext.text2"/>：
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
