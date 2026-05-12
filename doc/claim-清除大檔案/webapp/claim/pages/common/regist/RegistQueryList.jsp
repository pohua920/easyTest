<%--
****************************************************************************
* DESC       ：报案查询条件结果页面
* AUTHOR     ：lijiyuan
* CREATEDATE ：2004-03-01
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
							zhangshi  20080505				修改模糊查询为右模
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app"%>
<%@page import="com.sinosoft.sysframework.common.datatype.*"%>
<%@ page import="com.sinosoft.claim.dto.domain.*"%>
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
    	        window.print()
		pagesetup_set oldheader, oldfooter            '恢复設定
    end function
</script>
<html:html locale="true">
<head>
<app:css />
<title><s:text name="title.registBeforeEdit.queryRegist" /></title>
<html:base />
<script src="/claim/common/js/Common.js"></script>
<script language="javascript">
  <%--案件状态标志处理--%>
  <!--
    function submitForm()
    {
      if((fm.InsuredNameSign.value=="="&&fm.InsuredName.value.length>0)
  	 ||(fm.LicenseNoSign.value=="="&&fm.LicenseNo.value.length>0)
  	 ||(fm.RegistNoSign.value=="="&&fm.RegistNo.value.length>0)
  	 ||(fm.PolicyNoSign.value=="="&&fm.PolicyNo.value.length>0)){
  	 //输入了一个条件，可以查
  	 }else if((fm.RegistNoSign.value=="=*"&&fm.RegistNo.value.length>8)
  	        ||(fm.PolicyNoSign.value=="=*"&&fm.PolicyNo.value.length>8)){
  	 	if("D"==getClassCodeType(fm.RegistNo.value.substr(1,2))
  	 	 ||"D"==getClassCodeType(fm.PolicyNo.value.substr(1,2))){
  	 		alert("车险必须精确查询！");
  	 		return false;
  	 	}else{
  	 		//非车险可以前9位模糊查询
  	 	}  		
  	}else{
  		alert("車險必須輸入備案號碼、保單號碼、牌照號碼、被保險人其中一項精確查詢！\n非車險可以用備案號碼或者保單號碼的前9位進行模糊查詢！");
  		return false;
  	}
      var ref="";
      for(i=0;i<fm.status.length;i++){
        if(fm.status[i].checked==true){
           ref = ref+fm.status[i].value+",";
        }
      }
	  //reason 查询标志
	  fm.searchFlag.value='true';
	  fm.pageNo.value="1";
      fm.caseFlag.value = ref;
      fm.submit();//提交
    }
    //radio双击事件
    function dbclick(){
    	fm.cancelFlag[1].checked=false;
    	fm.cancelFlag[0].checked=false;
    	cancelDate.style.display="none";
    	//alert(cancelDate.style.display);
    }
    //radio单击事件显示注销时间选择项
    function radioclick(){
    	//alert(cancelDate.style.display);
    	if (fm.cancelFlag[0].checked==false){
    	cancelDate.style.display="none";
    	}else{
    	cancelDate.style.display="";
    	}
    }
    function document.onkeydown() 
    { 
    if(event.keyCode==13) 
    { 
      document.getElementById("button").click(); 
      return false; 
    } 
    }  
  //-->
  </script>
<%-- 页面样式  --%>
<link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
</head>
<body>
	<%
		PrpLregistDto prpLregistDto1 = (PrpLregistDto) request
					.getAttribute("prpLregistDto1");
	%>
	<form name="fm" action="/claim/RegistQuery.do" method="post" onSubmit="return validateForm(this);">
		<table width="100%" border="0" align="center" cellpadding="5" cellspacing="1" class="common">
			<tr>
				<td colspan="4" class="formtitle">
					<s:text name="title.registBeforeEdit.titleName" />
					<input type="hidden" name="checkFlag">
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="db.prpLregist.registNo" />
					：
				</td>
				<td class='input'>
					<select class=tag name="RegistNoSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input name="RegistNo" type=text class="query" value="<%=prpLregistDto1.getRegistNo()%>">
				</td>
				<td class='title'>
					<s:text name="db.prpLregist.policyNo" />
					：
				</td>
				<td class='input'>
					<select class=tag name="PolicyNoSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input name="PolicyNo" type=text class="query" value="<%=prpLregistDto1.getPolicyNo()%>">
				</td>
			</tr>
			<tr>
				<td class='title'>
					<s:text name="db.prpLregist.riskCode" />
					：
				</td>
				<td class='input'>
					<select class=tag name="RiskCodeSign">
						<option value="=">=</option>
						<!--<option value="*">*</option>-->
					</select>
					<input name="RiskCode" type=text class="query" value="<%=prpLregistDto1.getRiskCode()%>">
				</td>
				<td class='title'>
					<s:text name="db.prpLregist.licenseNo" />
					：
				</td>
				<td class='input'>
					<select class=tag name="LicenseNoSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input name="LicenseNo" type=text class="query" value="<%=prpLregistDto1.getLicenseNo()%>">
				</td>
			</tr>
			<tr>
				<td class='title'>操作时间：</td>
				<td class='input'>
					<select class=tag name="OperateDateSign">
						<option value="=">=&nbsp;</option>
						<option selected value=">">&gt;&nbsp;</option>
						<option value="<">&lt;&nbsp;</option>
						<option value=">=">&gt;=</option>
						<option value="<=">&lt;=</option>
					</select>
					<input type="text" name="OperateDate" class="query" value="<%=new DateTime(DateTime.current().toString(),
						DateTime.YEAR_TO_DAY).addDay(-7)%>">
					<img style='cursor: hand' align="absmiddle" src="/claim/images/bgcalendar.gif"
						onClick="TogglePopupCalendarWindow('document.fm.OperateDate', '<%=(new DateTime(DateTime.current().toString(),
						DateTime.YEAR_TO_DAY).getYear() - 15)%>', '<%=(new DateTime(DateTime.current().toString(),
						DateTime.YEAR_TO_DAY).getYear() + 2)%>')">
				</td>
				<%--报案查询增加被保险人查询条件--%>
				<td class='title'>被保险人名称：</td>
				<td class='input'>
					<select class=tag name="InsuredNameSign">
						<option value="=">=</option>
						<option value="=*">=*</option>
					</select>
					<input name="InsuredName" type=text class="query" value="<%=prpLregistDto1.getInsuredName()%>">
				</td>
			</tr>
			<!--原因：意健险需要在报案时增加身份证查询条件，查询不用。-->
			<%--
        <td class='title' >身份证号：</td>
        <td class='input' >
          <select class=tag name="IdentifyNumberSign" >
            <option value="=">=</option>
            <option value="=*">=*</option>
          </select> <input type=text name="IdentifyNumber" class="query" >
        </td>
        <input type="hidden" name="IDCardFlag" value="Flag">
        --%>
			<tr>
				<td class='title'>是否註銷：</td>
				<td class='input'>
					<input type="radio" name="cancelFlag" value="1" onDblclick="dbclick();" onClick="radioclick();">
					是
					</input>
					<input type="radio" name="cancelFlag" value="0" onDblclick="dbclick();" onClick="radioclick();">
					否
					</input>
				</td>
				<td class='title'>案件状态：</td>
				<td class='input'>
					<input type="hidden" name="caseFlag" value="">
					<%--input type="checkbox" name="status" value="1"未处理--%>
					<input type="checkbox" name="status" value="2">
					正处理
					<%--没有此种案件状态 2005-07-28<input type="checkbox" name="status" value="3">已处理--%>
					<input type="checkbox" name="status" value="4">
					已处理
			<tr id="cancelDate" style="display: none">
				<td class='title'>註銷开始时间：</td>
				<td class='input'>
					<input type=text name="registStartCancelDate" class="query">
					<img style='cursor: hand' src="/claim/images/bgcalendar.gif" align="absmiddle"
						onClick="TogglePopupCalendarWindow('document.fm.registStartCancelDate', '<%=(new DateTime(DateTime.current().toString(),
						DateTime.YEAR_TO_DAY).getYear() - 15)%>', '<%=(new DateTime(DateTime.current().toString(),
						DateTime.YEAR_TO_DAY).getYear() + 2)%>')">
				</td>
				<td class='title'>註銷结束时间：</td>
				<td class='input'>
					<input type=text name="registEndCancelDate" class="query">
					<img style='cursor: hand' src="/claim/images/bgcalendar.gif" align="absmiddle"
						onClick="TogglePopupCalendarWindow('document.fm.registEndCancelDate', '<%=(new DateTime(DateTime.current().toString(),
						DateTime.YEAR_TO_DAY).getYear() - 15)%>', '<%=(new DateTime(DateTime.current().toString(),
						DateTime.YEAR_TO_DAY).getYear() + 2)%>')">
				</td>
			</tr>
			</td>
			</tr>
			<tr>
				<td class="title" style="color: red" colspan="4">
					"="符号，必须精确查询。<br> "=*"符号，前匹配後模糊的查询。<br> 车险必须输入报案号、保单号、车牌号、被保险人其中一项精确查询！<br> 非车险可以用报案号或者保单号的前9位进行模糊查询！
				</td>
			</tr>
		</table>
		<table width=100%>
			<tr>
				<td width="49%" align=center>
					<div align="right">
						<input name="button" type=button class='button' id="button" onClick="submitForm();" value="<s:text name='button.query.value' />">
						<input type="hidden" name="searchFlag" id="searchFlag">
					</div>
				</td>
				<td width="51%" align=center>
					<div align="center" id="divButton" style="display:">
						<div align="left">
							<input class="button" type="button" name="buttonPrint" value=" 列 印 " onClick="printPage()">
						</div>
					</div>
				</td>
			</tr>
		</table>
		<table border="0" align="center" cellpadding="0" cellspacing="0" class="common">
			<tr>
				<td></td>
			</tr>
		</table>
		<table class="common" cellpadding="5" cellspacing="1">
			<tr>
				<td colspan="6" class="formtitle">报案查询结果信息</td>
			</tr>
			<tr>
				<td class="centertitle">案件状态</td>
				<td class="centertitle">
					<s:text name="db.prpLregist.registNo" />
				</td>
				<td class="centertitle">
					<s:text name="db.prpLregist.policyNo" />
				</td>
				<td class="centertitle">被保险人名称</td>
				<td class="centertitle">
					<s:text name="db.prpLregist.operatorCode" />
				</td>
				<td class="centertitle">操作时间</td>
			</tr>
			<%
				int index = 0;
			%>
			<logic:notEmpty name="prpLregistDto" property="registList">
				<logic:iterate id="prpLregist1" name="prpLregistDto" property="registList">
					<%
						if (index % 2 == 0)
										out.print("<tr class=listodd>");
									else
										out.print("<tr class=listeven>");
					%>
					<td align="center">
						<logic:equal name="prpLregist1" property="cancelDate" value="">
							<logic:equal name="prpLregist1" property="status" value='1'>
               未处理
              </logic:equal>
							<logic:equal name="prpLregist1" property="status" value='2'>
               正处理
              </logic:equal>
							<logic:equal name="prpLregist1" property="status" value='3'>
               已处理
              </logic:equal>
							<logic:equal name="prpLregist1" property="status" value='4'>
               已提交
              </logic:equal>
							<logic:equal name="prpLregist1" property="status" value='5'>
               已撤消
              </logic:equal>
						</logic:equal>
						<logic:notEqual name="prpLregist1" property="cancelDate" value="">
            已註銷
         </logic:notEqual>
					</td>
					</td>
					<td align="center">
						<a
							href="/claim/registFinishQueryList.do?prpLregistRegistNo=<bean:write name='prpLregist1' property='registNo'/>&editType=<bean:write name='prpLregistDto' property='editType'/>&riskCode=<bean:write name="prpLregist1" property="riskCode"/>">
							<bean:write name="prpLregist1" property="registNo" />
						</a>
					</td>
					<!--add by zhouliu start at 2006-6-9
            reason:强三查询
        -->
					<td align="center">
						<logic:iterate id="currelatepolicyNo" name="prpLregist1" property="relatepolicyNo">
							<bean:write name="currelatepolicyNo" />
							<br>
						</logic:iterate>
					</td>
					<!--add by zhouliu end at 2006-6-9-->
					<td align="center">
						<bean:write name="prpLregist1" property="insuredName" />
					</td>
					<td align="center">
						<bean:write name="prpLregist1" property="receiverName" />
					</td>
					<td align="center">
						<bean:write name="prpLregist1" property="operateDate" />
					</td>
					</tr>
					<%
						index++;
					%>
				</logic:iterate>
			</logic:notEmpty>
			<tr class="listtail">
				<td colspan="5">
					<!---add by zhaolu 20060802 start--->
					<table width="100%" class="common" align="center" cellpadding="0" cellspacing="0">
						<tr>
							<bean:define id="pageview" name="prpLregistDto" property="turnPageDto" />
							<%
								PrpLregistDto prpLregistDto = (PrpLregistDto) request
											.getAttribute("prpLregistDto");
									int curPage = prpLregistDto.getTurnPageDto().getPageNo();
							%>
							<%@include file="/common/pub/TurnOverPage.jsp"%>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		</table>
		</tr>
		</table>
		<input type="hidden" name="editType" value="<%=request.getParameter("editType")%>">
	</form>
</body>
</html:html>