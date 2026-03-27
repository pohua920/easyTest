<%@ include file="/common/taglibs.jsp"%>
<table class=common cellpadding="5" cellspacing="1" >
   <tr>
      <td class="title"><s:text name="db.prpLregist.receiverName" />:</td>
      <td class="input" colspan=3>
        <input type=hidden name="prpLregistReceiverCode" class="codecode"  style="width:40px" title="<s:text name="db.prpLregist.receiverName"/>" value="${prpLregist.receiverCode}"
             ondblclick="code_CodeSelect(this, 'handerCode','0,1','Y');"
             onchange="code_CodeSelect(this, 'handerCode','0,1','Y');"
             onkeyup= "code_CodeSelect(this, 'handerCode','0,1','Y');">
        <input type=text name="prpLregistReceiverName" class="codecode" style="width:125px" title="<s:text name="db.prpLregist.receiverName"/>" value="${prpLregist.receiverName }"
             ondblclick="code_CodeSelect(this, 'handerCode','-1,0','Y','N');"
             onchange="code_CodeSelect(this, 'handerCode','-1,0','Y','N');"
             onkeyup= "code_CodeSelect(this, 'handerCode','-1,0','Y','N');">
        <img src="${ctx }/images/bgDoubleClick1.gif" width="13" height="13" align="absmiddle">
        <img src="${ctx }/images/bgMarkMustInput.jpg">
      </td>
    </tr> 
    <tr>
        <td class="title"><s:text name="db.prpLregist.handler1Code" />:</td>
        <td class="input">
          <input type=hidden name="prpLregistHandler1Code" value="${prpLregist.handler1Code}">
          <input type=text   name="prpLregistHandler1Name" title="<s:text name="db.prpLregist.handler1Code"/>" class="readonly" readonly="true" value="${prpLregist.handler1Name}">
        </td>
        <td class="title"><s:text name="db.prpLregist.comCode" />:</td>
        <td class="input">
          <input type=hidden name="prpLregistComCode" value = "${prpLregist.comCode}">
          <input type=text   name="prpLregistComName" title="<s:text name="db.prpLregist.comCode"/>" class="readonly" readonly="true" value = "${prpLregist.comName}">
        </td>
      </tr> 
    <tr>
        <td class="title"><s:text name="db.prpLregist.operatorCode" />:</td>
        <td class="input">
          <input type=text name="prpLregistOperatorCode" title="<s:text name="db.prpLlawsuit.operatorCode"/>" class="readonly" style="width:80px" readonly="true" value="${prpLregist.operatorCode}">
          <input type=text name="prpLregistOperatorName" title="<s:text name="general.handlerName"/>" class="readonly" style="width:80px" readonly="true" value="${prpLregist.operatorName}">
        </td>
        <td class="title"><s:text name="commonAcci.claim.claimRegistDepart"/>:</td><%-- 理赔登记部门 --%>
        <td class="input">
          <input type=text name="prpLregistMakeCom" title="<s:text name="commonAcci.claim.claimRegistDepart"/>" class="readonly" style="width:80px" readonly="true" value="${prpLregist.makeCom}">
          <input type=text name="prpLregistMakeComName" title="<s:text name="commonAcci.claim.claimRegistDepart"/>" class="readonly" style="width:200px" readonly="true" value="${prpLregist.makeComName}">
        </td>
    </tr>
    <tr style='display:none'>
      <td class="title"><s:text name="db.prpLregist.acceptFlag" />:</td>
      <td class="input" >
      	<input type="radio" name="acceptFlag" value="Y" <c:if test="${prpLregist.acceptFlag=='Y' }">checked="checked"</c:if>><s:text name="regist.prpLregist.yes"/><%-- 是 --%>
        <input type="radio" name="acceptFlag" value="N" <c:if test="${prpLregist.acceptFlag=='N' }">checked="checked"</c:if>><s:text name="regist.prpLregist.no"/><%--否  --%>
        <img src="${ctx }/images/bgMarkMustInput.jpg">
      </td>
      <td class="title"><s:text name="db.prpLregist.repeatInsureFlag" />:</td>
      <td class="input">
      <input type="radio" name="repeatInsureFlag" value="Y" <c:if test="${prpLregist.repeatInsureFlag=='Y' }">checked="checked"</c:if>><s:text name="regist.prpLregist.yes"/><%-- 是 --%>
        <input type="radio" name="repeatInsureFlag" value="N" <c:if test="${prpLregist.repeatInsureFlag=='N' }">checked="checked"</c:if>><s:text name="regist.prpLregist.no"/><%--否  --%>
        <img src="${ctx }/images/bgMarkMustInput.jpg">      
      </td>
    </tr>
    <tr>
      <td class="title"><s:text name="db.prpLclaim.remark"/>:</td><%-- 备注 --%>
      <td class="input" colspan=3>
        <textarea  style="width:750px;overflow-x:visible;" name='prpLregistRemark' rows=4 cols=40 title="备注">${prpLregist.remark}</textarea>
      </td>
      </tr>
    </table>