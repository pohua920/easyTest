<%--
****************************************************************************
* DESC       ：显示承保险别的页面
* AUTHOR     ： lixiang
* CREATEDATE ： 2005-02-18
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%> 
      <table class="common" align="center" width="100%"> 
    <tr>
      <td class="subformtitle" style="text-align:left;">
        <img style="cursor:hand;" src="/claim/images/butExpandBlue.gif"   
             name="RegistPolicyRiskImg" onclick="showPage(this,RegistPolicyRisk)">
             <s:text name="commonAcci.regist.kindBenefitInformation" /><%--险种保益信息--%>
        <br>
       
         <%
        //reason:保单承保险别位置上移动，保单号後，只显示承保险别%>
       
        <table class="common" align="center" id="RegistPolicyRisk" >
        
         </tbody>
    <%
     //reason:去掉节点名称的转换
     //报案对象
     int indexCitemKind=0;%>
     <tr>
        <td class="prompttitle" ><s:text name="db.prpLmedicine.serialNo" /></td>  <%--序号--%>
        <td class="prompttitle" ><s:text name="regist.prpLregist.kindCode" /></td><%-- 险别代码--%>
        <td class="prompttitle" ><s:text name="regist.prpLregist.kindName" /></td> <%-- 险别名称--%>
        <td class="prompttitle" ><s:text name="regist.prpLregist.itemDetailName" /></td>  <%-- 标的明细名称--%>
        <td class="prompttitle" ><s:text name="db.prpLperson.currency" /></td>  <%-- 币别--%>
        <td class="prompttitle" ><s:text name="regist.prpLregist.unitAmount" /></td> <%-- 每人保额--%>
        <td class="prompttitle" ><s:text name="regist.prpLregist.sumQuantity" /></td>   <%--人数 --%>
        <td class="prompttitle" ><s:text name="regist.prpLregist.sumPremium" /></td>  <%--保费 --%>
        <td class="prompttitle" ><s:text name="regist.prpLregist.sumAmount" /></td>    <%--保险金额 --%>
        
     </tr>
     <c:forEach var="prpCitemKindList" items="${prpCitemKind.prpCitemKindList}" varStatus="indexCitemKind">
          <%if(indexCitemKind %2== 0)
               out.print("<tr class=listodd>");
          else 
               out.print("<tr class=listeven>");
       %> 
       <td ><%=indexCitemKind+1%></td>
       <td >${prpCitemKindList.kindCode }</td>
       <td >${prpCitemKindList.kindName }</td>
       <td >${prpCitemKindList.itemDetailName }</td>
       <td >${prpCitemKindList.currency }</td>
       <td >${prpCitemKindList.unitAmount }</td>
       <td >${prpCitemKindList.quantity }</td>
       <td >${prpCitemKindList.premium }</td>
       <td >${prpCitemKindList.amount }</td>
       </tr>   
    <% indexCitemKind++;%>
  </c:forEach>
          </tbody>
        </table>
      </td>
    </tr>
  </table>
 