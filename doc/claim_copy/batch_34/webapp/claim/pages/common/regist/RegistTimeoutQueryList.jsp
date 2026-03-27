<%--
****************************************************************************
* DESC       ：报案查询条件结果页面
* AUTHOR     ：lijiyuan
* CREATEDATE ：2004-03-01
* MODIFYLIST ：   id       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************/
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/claim-app.tld" prefix="app"%>
<%@ page import="com.sinosoft.claim.dto.domain.*"%>
<%
	//原因：向页面中增加一个打印按钮
%>
<script src="/claim/common/js/showpage.js"> </script
	

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
</head>
<body>
	<input type="hidden" name="pageFlag">
	<form name="fm" action="/claim/RegistQuery.do" method="post">
		<table border="0" align="center" cellpadding="0" cellspacing="0" class="common">
			<tr>
				<td>
					<div align="center" id="divButton" style="display:">
						<input class="button" type="button" name="buttonPrint" value=" 列 印 " onclick="printPage()">
					</div>
				</td>
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
			<%int index = 0;%>
			<logic:notEmpty name="prpLregistDto" property="registList">
				<logic:iterate id="prpLregist1" name="prpLregistDto" property="registList">
					<%if (index % 2 == 0)
							out.print("<tr class=listodd>");
						else
							out.print("<tr class=listeven>");%>
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
					<td align="center">
						<logic:iterate id="currelatepolicyNo" name="prpLregist1" property="relatepolicyNo">
							<bean:write name="currelatepolicyNo" />
							<br>
						</logic:iterate>
					</td>
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
					<%index++;%>
				</logic:iterate>
			</logic:notEmpty>
			<tr class="listtail">
				<td colspan="5">
					<table width="100%" class="common" align="center" cellpadding="0" cellspacing="0">
						<tr>
							<bean:define id="pageview" name="prpLregistDto" property="turnPageDto" />
							<%PrpLregistDto prpLregistDto = (PrpLregistDto) request.getAttribute("prpLregistDto");
								int curPage = prpLregistDto.getTurnPageDto().getPageNo();%>
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