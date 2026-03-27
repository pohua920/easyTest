<%--
****************************************************************************
* DESC       ：立案查询结果显示页面
* AUTHOR     ：lijiyuan
* CREATEDATE ：2004-03-01
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
							zhangshi		20130201			修改*操作符为=*将like '%%'改为左%右匹配查询
****************************************************************************/
--%>

<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app" %>
<%@ page import="com.sinosoft.claim.dto.domain.PrpLclaimDto" %>
<%
    //原因：向页面中增加一个打印按钮
%>
 <script src="/claim/common/js/showpage.js"> </script>
    <script language="VBScript">
    // 避免弹出安全警告框的说明：Internet选项=〉安全=〉受信任的站点
    // 1.将网站加入受信任站点，
    // 2.自定义级别中 启用 对没有标记为安全的ActiveX控件进行初始化和脚本运行

    dim hkey_root,hkey_path,hkey_key
    hkey_root="HKEY_CURRENT_USER"
    hkey_path="\Software\Microsoft\Internet Explorer\PageSetup"

    dim oldheader,oldfooter

    '//设置网页打印的页眉页脚，上下左右
    function pagesetup_set(header,footer)
        on error resume next
        Set RegWsh = CreateObject("WScript.Shell")
        hkey_key="\header"
        RegWsh.RegWrite hkey_root+hkey_path+hkey_key,header
        hkey_key="\footer"
        RegWsh.RegWrite hkey_root+hkey_path+hkey_key,footer


        hkey_key="\margin_left" '左
        RegWsh.RegWrite hkey_root+hkey_path+hkey_key,"0.73"
        hkey_key="\margin_right" '右
        RegWsh.RegWrite hkey_root+hkey_path+hkey_key,"0.70"
        hkey_key="\margin_top" '上
        RegWsh.RegWrite hkey_root+hkey_path+hkey_key,"1.1"
        hkey_key="\margin_bottom" '下
        RegWsh.RegWrite hkey_root+hkey_path+hkey_key,"1"

    end function
    '//设置网页打印的页眉页脚,上下左右为默认值
    function pagesetup_default()
        on error resume next
        Set RegWsh = CreateObject("WScript.Shell")
        hkey_key="\header"
        RegWsh.RegWrite hkey_root+hkey_path+hkey_key,"&w&b页码，&p/&P"
        hkey_key="\footer"
        RegWsh.RegWrite hkey_root+hkey_path+hkey_key,"&u&b&d"

        hkey_key="\margin_left" '左
        RegWsh.RegWrite hkey_root+hkey_path+hkey_key,"0.75"     '(对应 19.05毫米)
        hkey_key="\margin_right" '右
        RegWsh.RegWrite hkey_root+hkey_path+hkey_key,"0.75"
        hkey_key="\margin_top" '上
        RegWsh.RegWrite hkey_root+hkey_path+hkey_key,"0.75"
        hkey_key="\margin_bottom" '下
        RegWsh.RegWrite hkey_root+hkey_path+hkey_key,"0.75"
    end function

    '//显示页面设置
    function pagesetup_get()
        on error resume next
        Set RegWsh = CreateObject("WScript.Shell")
        hkey_key="\header"
        oldheader=RegWsh.RegRead(hkey_root+hkey_path+hkey_key)
        hkey_key="\footer"
        oldfooter=RegWsh.RegRead(hkey_root+hkey_path+hkey_key)

        'hkey_key="\margin_left" '左
        'message = message & "左:" & RegWsh.RegRead( hkey_root+hkey_path+hkey_key) & vbCrLf
        'hkey_key="\margin_right" '右
        'message = message & "右:" & RegWsh.RegRead( hkey_root+hkey_path+hkey_key) & vbCrLf
        'hkey_key="\margin_top" '上
        'message = message & "上:" & RegWsh.RegRead( hkey_root+hkey_path+hkey_key) & vbCrLf
        'hkey_key="\margin_bottom" '下
        'message = message & "下:" & RegWsh.RegRead( hkey_root+hkey_path+hkey_key) & vbCrLf
        '
        'msgbox (message)
    end function

    function printPage()
		pagesetup_get()         '读取旧值
		header=""
		footer=""
		pagesetup_get()
		pagesetup_set header, footer
		divButton.style.display = "none"
		queryTable.style.display = "none"
		queryButton.style.display = "none"
    	        window.print()
		pagesetup_set oldheader, oldfooter            '恢复設定
    end function
</script>
<script>
    function submitForm()
  {
  
    if(fm.RiskCode.value == ""){
  		alert("必须輸入险种！");
  		return;
  	}
    fm.pageNo.value="1";
    fm.searchFlag.value="true";
    fm.submit();//提交
  }
    function document.onkeydown() 
    { 
    if(event.keyCode==13) 
    { 
      document.getElementById("button").click(); 
      return false; 
    } 
    }  
</script>
<html:html locale="true">
<head>
    <app:css />

  <title><s:text name="title.claimBeforeEdit.queryClaim"/></title>
  
  <html:base/>
</head>

<body onLoad="document.onkeydown();" >
<!---<form name="fm" action="/claim/ClaimQuery.do?editType=claimTimeOut&nodeType=claim"  method="post"  validateForm(this);>--->
<form name="fm" action="/claim/ClaimQuery.do" method="post">
<input type="hidden" name="pageFlag">
<input type="hidden" name="searchFlag" value="" >
<logic:empty name="type">
    <table id="queryTable" width="100%" border="0" align="center" cellpadding="4" cellspacing="1"  class="common">
      <tr>

	          <td colspan="4" class="formtitle"><s:text name="claim.queryTimeoutInfo" /></td><%--查询超时立案信息--%>

	        </tr>
      <tr>
        <td class='title' ><s:text name="db.prpLclaim.claimNo" />：</td>
        <td class='input' >
        <select class=tag name="ClaimNoSign" >
            <option value="=">=</option>
            <option value="=*">=*</option>
          </select> <input type=text name="ClaimNo" class="query" >
        </td>
        <td class='title' ><s:text name="db.prpLclaim.policyNo" />：</td>
        <td class='input' >
        <select class=tag name="PolicyNoSign" >
            <option value="=">=</option>
            <option value="=*">=*</option>
          </select> <input type=text name="PolicyNo" class="query" >
        </td>
      </tr>
      <tr>  
        <td class='title' ><s:text name="db.prpLCMain.insuredName" />:</td><%--被保险人名称--%>
        <td class='input' >
          <select class=tag name="InsuredNameSign" >
            <option value="=">=</option>
            <option value="=*">=*</option>
          </select> <input type=text name="InsuredName" class="query" >
        </td>
        <td class='title' ><s:text name="regist.prpLregist.riskCodeName" /></td><%--险种--%>
        <td class='input' >  
             <select class=tag name="RiskCodeSign" >
                <option value="=">=</option>
                <!--<option value="*">*&nbsp;</option>-->
             </select> <input type=text name="RiskCode" class="query" > </td>
      </tr>
      <tr>
      	<td class='title' ><s:text name="regist.prpLregist.registNo" />：</td><%--报案号--%>
      	<td class='input' >
      		<select class=tag name="RegistNoSign" >
            <option value="=">=</option>
            <option value="=*">=*</option>
          </select> <input type=text name="RegistNo" class="query" >
      	</td>
      	<td class='title' ></td>
      	<td class='input' ></td>
      </tr>
      <tr>
    <td class="title" style="color:red" colspan="4">
    <s:text name="prompt.schedule.query1" /><br><%--"="符号，必须精确查询。--%>
    <s:text name="prompt.schedule.query2" /><<%--"=*"符号，前匹配後模糊的查询。--%>
    </td>
  </tr>
      </table>
      <table width=100%>
      <tr>
       <td class='button' colspan="4">
          <input type=button id="button" class='button' name="queryButton" value="<s:text name='button.query.value' />" onClick="submitForm()">
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          <input class="button" type="button"  name="divButton" value="<s:text name='button.print.value' />"  onclick="printPage()">
         </td>
      </tr>
    </table>
  </logic:empty>
  
  <table  class="common" cellpadding="5" cellspacing="1" >
  <tr>



 <!-- <table class="common" cellpadding="5" cellspacing="1" >-->
   <td colspan=5 class="formtitle"><s:text name="claim.fileInfoQuery" /></td></tr><%--立案查询结果信息--%>
     <tr>
        <td class="centertitle" ><s:text name="certainLoss.prpLscheduleMainWF.caseState" /></td><%--案件状态--%>
        <td class="centertitle" ><s:text name="db.prpLclaim.claimNo"/></td>
        <td class="centertitle" ><s:text name="prpLbpmMain.mainNo" /></td><%--报案号--%>
        <td class="centertitle" ><s:text name="db.prpLlawsuit.operatorCode" /></td><%--操作员--%>
        <td class="centertitle" ><s:text name="db.prpLclaimStatus.operatedate" /></td><%--操作时间--%>
     </tr>
     <%int index=0;%>
     <logic:notEmpty  name="prpLclaimDto"  property="claimList">
     <logic:iterate id="prpLclaim1"  name="prpLclaimDto"  property="claimList">
<%
          if(index %2== 0)
               out.print("<tr class=listodd>");
          else
               out.print("<tr class=listeven>");
%>
       <tr class=common> <td align="center">
       
       <logic:equal name="prpLclaim1" property="caseType" value="" >
        <logic:equal name="prpLclaim1" property="status" value='1' >
         <s:text name="common.status.untreated" /><%--未处理--%>
        </logic:equal>
        <logic:equal name="prpLclaim1" property="status" value='2' >
         <s:text name="common.status.intreating" /><%--正处理--%>
        </logic:equal>
        <logic:equal name="prpLclaim1" property="status" value='3' >
         <s:text name="common.status.treated" /><%--已处理--%>
        </logic:equal>
        <logic:equal name="prpLclaim1" property="status" value='4' >
         <s:text name="common.status.submited" /><%--已提交--%>
        </logic:equal>
        <logic:equal name="prpLclaim1" property="status" value='5' >
         <s:text name="common.status.revoked" /><%--已撤消--%>
        </logic:equal>
        
        </logic:equal>
         
        
        <logic:equal name="prpLclaim1" property="caseType" value="0" >
         <s:text name="common.status.cancled" /><%--已注销--%>
        </logic:equal>
        <logic:equal name="prpLclaim1" property="caseType" value="1" >
         <s:text name="claim.rejectClaims" /><%--已拒赔--%>
        </logic:equal>
         <logic:equal name="prpLclaim1" property="caseType" value="2" >
         <s:text name="claim.case" /><%--已结案--%>
        </logic:equal>
       
        
        </td>
        <td align="center"><a href="/claim/claimFinishQueryList.do?prpLclaimClaimNo=<bean:write name='prpLclaim1' property='claimNo'/>&editType=SHOW&riskCode=<bean:write name="prpLclaim1" property="riskCode"/>"><bean:write name="prpLclaim1" property="claimNo"/></a></td>
        <td align="center"><bean:write name="prpLclaim1" property="registNo"/></td>
        <td align="center"><bean:write name="prpLclaim1" property="operatorCode"/></td>
        <td align="center"><bean:write name="prpLclaim1" property="operateDate"/></td>
      </tr>
<%        index++;%>
      </logic:iterate>
      </logic:notEmpty>
      <tr class="listtail">
	      <td colspan="5">
	       <table width="100%" class="common" align="center" cellpadding="0" cellspacing="0">
              <tr>   
                  <bean:define id="pageview" name="prpLclaimDto" property="turnPageDto"/>  
<% 
  PrpLclaimDto prpLclaimDto = (PrpLclaimDto)request.getAttribute("prpLclaimDto"); 
  int curPage = prpLclaimDto.getTurnPageDto().getPageNo(); 
%>                   
                  <%@include file="/common/pub/TurnOverPage.jsp" %>   
              </tr> 
          </table>
	      </td>
      </tr>
    </table>
    </tr>
  </table>
<input type="hidden" name="editType" value="<%=request.getParameter("editType")%>">
</form>

</body>
</html:html>