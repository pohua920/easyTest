<!--
****************************************************************************
* DESC       ：显示预赔文字页面
* AUTHOR     : 理赔组
* CREATEDATE ：2004-05-12
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
-->
<%@ include file="/common/taglibs.jsp"%>
   <table class="common" align="center" width="100%">
     <tr>
       <td class="subformtitle" style="text-align:left;">
         <img style="cursor:hand;" src="${ctx}/images/butCollapseBlue.gif" name="RegistTextImg" onclick="showPage(this,RegistText)"><s:text name="prepay.compensationReport"/>： <%-- 预赔报告 --%>
        <br>
      <table class="common" align="center" id="RegistText" style="display:none">
        <tbody>
          <tr>
            <td class="input" style="text-align:center;" colspan="0">
                <iframe class="Composition" ID="Composition" MARGINHEIGHT="1" MARGINWIDTH="1" width="100%" height="240">
                </iframe> 
                 <input type="hidden" stype="width:300px" name="prpLptextContextInnerHTML" value='${prpLptext.context}'>
            </td>
          </tr>
        </tbody>
      </table>
      </td>
    </tr>
    <c:if test="${uwNotionHandleText!=null&&uwNotionHandleText!=''}">
    <tr>
    	<td class="subformtitle" style="text-align:left;">
         <img style="cursor:hand;" src="${ctx}/images/butCollapseBlue.gif" name="HandleTextImg" onclick="showPage(this,HandleText)"><s:text name="prepay.reviewComments"/>： <%-- 核赔退回审核意见 --%>
        <br>
      <table class="common" cellpadding="5" cellspacing="1" id="HandleText" style="display:none">
        <tbody>
          <tr>
            <td class="input" style="text-align:center;" colspan="0">
                 <textarea  rows="5" cols="80" readonly name="uwNotionHandleText">${uwNotionHandleText}</textarea>              
            </td>
          </tr>
        </tbody>
      </table>
      </td>
    </tr>
   </c:if>
  </table>
