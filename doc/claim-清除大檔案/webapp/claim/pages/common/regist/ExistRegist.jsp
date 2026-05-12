<%--
****************************************************************************
* DESC       ： 出险信息画面
* AUTHOR     ： wangli 
* CREATEDATE ： 2005-05-28
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%> 
<%@ include file="/common/taglibs.jsp"%>
<SCRIPT LANGUAGE="JavaScript">
<!--
//reason:在报案登记画面中，已出险次数的历次出险事故的清单中,可以点击报案号关联到相关案件信息  
/**
 *@description 弹出关联报案信息页面
 *@param       无
 *@return      通过返回true,否则返回false
 */
function showRegist(registNo){	       
	     
    var linkURL = "${ctx}/registFinishQueryList.do?prpLregistRegistNo="+registNo+"&editType=SHOW";	  
    var newWindow = window.open(linkURL,"NewWindow","width=640,height=500,top=0,left=0,toolbar=yes,location=no,directories=no,menubar=no,scrollbars=yes,resizable=yes,status=no");    
}   
function showPicture(registNo){	    
    var linkURL = "${ctx}/DAA/certify/DAACertifyViewFile.jsp?businessNo="+registNo+"&display=all";	  
    var newWindow = window.open(linkURL,"NewWindow","width=640,height=500,top=0,left=0,toolbar=yes,location=no,directories=no,menubar=no,scrollbars=yes,resizable=yes,status=no");    
}   

function showCompensateFee(registNo){	       
	     
    var linkURL = "${ctx}/uiCompensateFee.do?prpLregistRegistNo="+registNo;	  
    var newWindow = window.open(linkURL,"NewWindow","width=300,height=100,top=0,left=0,toolbar=yes,location=no,directories=no,menubar=no,scrollbars=yes,resizable=yes,status=no");    
}  
function buttonOnClickPerilInfoShow(actionName, policyNo, curRegistNo) {
	var sameCount = parseInt(fm.PerilCount.value);
	if (sameCount < 1) {
		fm.button_Peril_Open_Context.disabled = true;
		return;
	}
	var messagedo = "/claim/" + actionName + ".do?policyNo=" + policyNo + "&curRegistNo=" + curRegistNo;
	win = window.open(messagedo, "NewWindow", "status=no,resizable=yes,scrollbars=yes,top=100,left=100,width=700,Height=500");
}
//--> 
</SCRIPT>
<input type=text name="PerilCount" class="readonly" readonly="true" style="width: 50%; text-align ='center'; color: '#9B009B'" value="${prpLregistDto1.perilCount}">
<input type=button ACCESSKEY="." value='...' name='button_Peril_Open_Context' class="smallbutton" onclick="buttonOnClickPerilInfoShow('perilInfoShow',fm.policyno.value,fm.registno.value);">
<!--
<logic:present name="prpLclaimDto" property="registNo">
<input type=hidden name="curRegistNo" value="<bean:write name='prpLclaimDto' property='registNo' filter='true' />">
</logic:present>
-->
<span id="span_Peril_Context" style='width: 700; display: none; position: absolute; background-color: FFFFFF;'>
	<table class="prompt" style="width: 600">
		<tr class="prompt">
			<td class="prompttitle">
				<s:text name="db.prpDrate.serialNo" />
				<%--序号 --%>
			</td>
			<td class="prompttitle">
				<s:text name="prpLbpmMain.mainNo" />
				<%--报案号 --%>
			</td>
			<td class="prompttitle">
				<s:text name="regist.prpLregist.damageTime" />
				<%--出险时间 --%>
			</td>
			<!--
        <td class="prompttitle">已决赔款</td> 
        <td class="prompttitle">未决赔款</td>
        -->
			<td class="prompttitle">
				<s:text name="regist.alreadyPayShow" />
				<%--已决未决赔款显示 --%>
			</td>
			<td class="prompttitle">
				<s:text name="db.prpLclaimStatus.status" />
				<%--案件状态 --%>
			</td>
			<td class="prompttitle">
				<s:text name="prompt.queRegist.Operator" />
				<%--操作员 --%>
			</td>
			<!--
        <td class="prompttitle">联系人</td> 
        <td class="prompttitle">操作代码</td>
        <td class="prompttitle">操作人姓名</td> 
        <td class="prompttitle">出险地点</td> 
        <td class="prompttitle">损失部位</td> 
        <td class="prompttitle">案件照片</td> 
        <td class="prompttitle">联系电话</td>   
        <td class="prompttitle">状态</td>  
        -->
		</tr>
		<!-- 插入出险次数详细信息-->
		<c:if test="${prpLregistDto1.registList!=null }">
			<c:forEach items="${prpLregistDto1.registList}" var="registItem">
				<tr>
					<td class="prompt">${registItem.serialNo}</td>
					<%--resson:在报案登记画面中，已出险次数的历次出险事故的清单中,可以点击报案号关联到相关案件信息--%>
					<td class="prompt">
						<a href="javascript:showRegist('${registItem.registNo}')">${registItem.registNo}</a>
					</td>
					<td class="prompt">${registItem.damageStartDate}</td>
					<td class="prompt">
						<a href="javascript:showCompensateFee('${registItem.registNo}')"><s:text name="regist.alreadyPayShow" />
							<%--已决未决赔款显示 --%></a>
					</td>
					<td class="prompt">${registItem.status}</td>
					<td class="prompt">${registItem.operatorName}</td>
					<!--
	          <td class="prompt"><bean:write name="registItem" property="linkerName"/></td>
	          <td class="prompt"><bean:write name="registItem" property="operatorCode"/></td>
	          <td class="prompt"><bean:write name="registItem" property="operatorName"/></td>
	          <td class="prompt"><bean:write name="registItem" property="damageAddress"/></td>
	          <td class="prompt"><bean:write name="registItem" property="brandName"/></td>
	          <td class="prompt"><a href="javascript:showPicture('<bean:write name='registItem' property='registNo'/>')"><bean:write name='registItem' property='registNo'/></a> </td>
	          <td class="prompt"><bean:write name="registItem" property="phoneNumber"/></td>
	          <td class="prompt"><bean:write name="registItem" property="status"/></td>
	          -->
				</tr>
			</c:forEach>
		</c:if>
		<tr>
			<td colspan=10 class="prompttitle">
				<input type=button name='button_Peril_Close_Context' value='(O)关闭' class="button" ACCESSKEY="O" onclick="hideSubPage(this,'span_Peril_Context')">
			</td>
		</tr>
	</table>
</span>
