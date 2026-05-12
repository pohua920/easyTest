<%--
****************************************************************************
* DESC       ：出险信息画面
* AUTHOR     ： Sinosoft 
* CREATEDATE ： 2004-12-09
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ include file="/common/taglibs.jsp"%>
<SCRIPT LANGUAGE="JavaScript">
function hideSubPage2(field, tableName) {
	var order = parseInt(getElementOrder(field));
	var obj = document.getElementsByName(tableName)[order - 1];
	obj.style.display = 'none';
}

function buttonOnClick2(strSubPageCode) {
	showSubPage2(strSubPageCode);

}

function showSubPage2(spanID, leftMove) {
	var intLeftMove = (leftMove == null ? 0 : leftMove);
	var span = eval(spanID);
	var strTemp = span.id;

	var ex = window.event.clientX + document.body.scrollLeft; //得到事件的坐标x
	var ey = window.event.clientY + document.body.scrollTop; //得到事件的坐标y

	ex = ex - 520;

	if (ex < 0) {
		ex = 0;
	}
	ex = ex - intLeftMove;

	span.style.left = ex;
	span.style.top = ey;
	span.style.display = '';
}

function getElementOrder(field) {
	var i = 0;
	var order = 0;
	var elements = document.getElementsByName(field.name);
	for (i = 0; i < elements.length; i++) {
		order++;
		if (elements[i] == field) {
			break;
		}
	}

	return order;
}

function showEndorse(endorseNo, riskCode) {
	var linkURL = '/prpall/common/pg/UIEndorsePtextShow.jsp?BizNo=' + endorseNo;
	//var linkURL = '/prpall/'+riskCode+'/tbcbpg/UIPrPoEn'+riskCode+'Show.jsp?BIZTYPE=ENDORSE&zzBizNo='+endorseNo+'&SHOWTYPE=SHOW';	  
	var newWindow = window.open(linkURL, "NewWindow", "width=640,height=500,top=0,left=0,toolbar=yes,location=no,directories=no,menubar=no,scrollbars=yes,resizable=yes,status=no");
} 
</SCRIPT>
<input type=text name="PerilCount" class="readonly" readonly="true" style="width: 50%; text-align ='center'; color: '#9B009B'" value="${prpLregistDto1.perilCount}">
<input type="hidden" name="checkFlag" value="${checkFlag }">
<input title="點選此處可獲得已出險相關訊息" type=button ACCESSKEY="." value='...' class="smallbutton" name='button_Peril_Open_Context' onclick="buttonOnClick('perilInfoShow','${policyNo }','${curRegistNo}');">
<s:if test="#attr.prpPheadDtoList.size()>0">
	<input title="點選此處可獲得歷次批單相關訊息" type=button ACCESSKEY="." class="button" value='历次批单' name='button_Peril_Open_Context2' onclick="buttonOnClick2('span_Peril_Context');">
</s:if>
<span id="span_Peril_Context" style='width: 800; display: none; position: absolute; background-color: FFFFFF;'>
	<table class=common cellpadding="5" cellspacing="1">
		<tr>
			<td class="prompttitle">
				<%-- 序号 --%>
				<s:text name="regist.prpLregist.serialNo" />
			</td>
			<td class="prompttitle">
				<%-- 批单号 --%>
				<s:text name="regist.prpLregist.endorseNo" />
			</td>
			<td class="prompttitle">
				<%-- 批单生效日期 --%>
				<s:text name="regist.prpLregist.endorseStartDate" />
			</td>
		</tr>
		<c:forEach items="${prpPheadDtoList}" var="prpPheadDto" varStatus="prpPheadDtoStatus">
			<tr>
				<td class="prompt">${prpPheadDtoStatus.count }</td>
				<%--modify by liujianbo modify 20050321 start--%>
				<%--resson:在报案登记画面中，已出险次数的历次出险事故的清单中,可以点击报案号关联到相关案件信息--%>
				<td class="prompt">
					<a href="javascript:showEndorse('${prpPheadDto.endorseNo }',${prpPheadDto.riskCode}')">${prpPheadDto.endorseNo}</a>
				</td>
				<td class="prompt">${prpPheadDto.validDate}</td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan=14 class="common">
				<input type=button name='button_Peril_Close_Context' value='(O)关闭' class="button" ACCESSKEY="O" onclick="hideSubPage2(this,'span_Peril_Context')">
			</td>
		</tr>
	</table>
</span>