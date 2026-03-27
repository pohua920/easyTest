<%--
****************************************************************************
* DESC       ：出险信息画面
* AUTHOR     ：中科软 
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ include file="/common/taglibs.jsp"%>
<SCRIPT LANGUAGE="JavaScript">
<!--
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

function hideSubPage2(field,tableName)
{
  var order = parseInt(getElementOrder(field));
  var obj = document.getElementsByName(tableName)[order-1];
  obj.style.display ='none';
}
function buttonOnClick2(strSubPageCode)
{
  showSubPage2(strSubPageCode);

}
function showSubPage2(spanID,leftMove)
{
  var intLeftMove = (leftMove==null?0:leftMove);
  var span = eval(spanID );
  var strTemp = span.id;

  var ex=window.event.clientX+document.body.scrollLeft;  //得到事件的坐标x
  var ey=window.event.clientY+document.body.scrollTop;   //得到事件的坐标y

  ex = ex - 520;

  if (ex<0)
  {
    ex = 0;
  }
  ex = ex - intLeftMove;

  span.style.left=ex;
  span.style.top=ey;
  span.style.display ='';
}
function getElementOrder(field)
{
    var i = 0;
    var order = 0;
    var elements = document.getElementsByName(field.name);
    for(i=0;i<elements.length;i++)
    {
        order++;
        if(elements[i]==field)
        {
            break;
        }
    }

    return order;
}
function showEndorse(coreURL,endorseNo,riskCode){
    var linkURL = fm.core_URL.value+'common/pg/UIEndorsePtextShow.jsp?BizNo='+endorseNo;
    var newWindow = window.open(linkURL,"NewWindow","width=640,height=500,top=0,left=0,toolbar=yes,location=no,directories=no,menubar=no,scrollbars=yes,resizable=yes,status=no");    

}   
//--> 
</SCRIPT>
<input type="hidden" name="core_URL" value="${core_URL}">
<input type=text name="PerilCount" class="readonly" readonly="true" style="width: 20%; text-align ='center'; color: '#9B009B'" value="${prpLregistDto1.perilCount}">
<input type="hidden" name="checkFlag" value="${checkFlag }">
<input title="點選此處可獲得已出險相關信息" type=button ACCESSKEY="." value='...' class="button" name='button_Peril_Open_Context' onclick="buttonOnClick('perilInfoShow',fm.policyno.value,fm.registno.value);">
<span id="span_Peril_Context" style='width: 800; display: none; position: absolute; background-color: FFFFFF;'>
	<table class="prompt" style="width: 400">
		<tr class="prompt">
			<td class="prompttitle">
				<s:text name="db.prpLmedicine.serialNo" />
			</td>
			<%--序号--%>
			<td class="prompttitle">
				<s:text name="prpLregist.registNo" />
			</td>
			<%--报案号--%>
			<!--原因：在界面上增加一些信息-->
			<td class="prompttitle">
				<s:text name="check.claimNum" />
			</td>
			<%--赔案号--%>
			<td class="prompttitle">
				<s:text name="pub.lossAssessAmount" />
			</td>
			<%--估损金额CNY--%>
			<td class="prompttitle">
				<s:text name="pub.compensatAmount" />
			</td>
			<%--赔付金额CNY--%>
			<td class="prompttitle">
				<s:text name="regist.prpLregist.damageTime" />
			</td>
			<%--出险时间--%>
			<td class="prompttitle">
				<s:text name="db.prpLregist.linkerName " />
			</td>
			<%--联系人--%>
			<td class="prompttitle">
				<s:text name="commonLiab.regist.operatCode" />
			</td>
			<%--操作代码--%>
			<td class="prompttitle">
				<s:text name="commonLiab.regist.personName" />
			</td>
			<%--操作人姓名--%>
			<td class="prompttitle">
				<s:text name="db.prpLregist.damageAddress" />
			</td>
			<%--出险地点--%>
			<td class="prompttitle">
				<s:text name="pcertainLoss.thirdCarLoss.prpLchecDemagePart" />
			</td>
			<%--损失部位--%>
			<td class="prompttitle">
				<s:text name="regist.prpLregist.casePhoto" />
			</td>
			<%--案件照片--%>
			<td class="prompttitle">
				<s:text name="certainLoss.prpLscheduleMainWF.prpLscheduleMainWFPhoneNumbe" />
			</td>
			<%--联系电话--%>
			<td class="prompttitle">
				<s:text name="regist.prpLregist.status" />
			</td>
			<%--状态--%>
		</tr>
		<!-- 插入出险次数详细信息-->
		<c:if test="${registClaimDtoList!=null}">
			<c:forEach items="${registClaimDtoList}" var="registClaimDto" varStatus="registClaimDto_status">
				<tr>
					<td class="prompt">${registClaimDto_status.count }</td>
					<%--resson:在报案登记画面中，已出险次数的历次出险事故的清单中,可以点击报案号关联到相关案件信息--%>
					<td class="prompt">
						<a href="javascript:showRegist('${registClaimDto.registNo}">${registClaimDto.registNo}</a>
					</td>
					<!--原因：在界面上增加一些信息-->
					<td class="prompt">${registClaimDto.claimNo}</td>
					<td class="prompt">${registClaimDto.sumClaim}</td>
					<td class="prompt">${registClaimDto.sumPaidShow}</td>
					<td class="prompt">${registClaimDto.damageStartDate}</td>
					<td class="prompt">${registClaimDto.linkerName}</td>
					<td class="prompt">${registClaimDto.operatorCode}</td>
					<td class="prompt">${registClaimDto.operatorName}</td>
					<td class="prompt">${registClaimDto.damageAddress}</td>
					<td class="prompt">${registClaimDto.brandName}</td>
					<td class="prompt">
						<a href="javascript:showPicture('${registClaimDto.registNo}')">${registClaimDto.registNo}</a>
					</td>
					<td class="prompt">${registClaimDto.phoneNumber}</td>
					<td class="prompt">${registClaimDto.status}</td>
				</tr>
			</c:forEach>
		</c:if>
		<tr>
			<td colspan=14 class="prompttitle">
				<input type=button name='button_Peril_Close_Context' class="button" value="<s:text name='button.close.value' />" ACCESSKEY="O" onclick="hideSubPage(this,'span_Peril_Context')">
			</td>
		</tr>
	</table>
</span>
<s:if test="#attr.prpPheadDtoList!=null&&#attr.prpPheadDtoList.size()>0">
	<input title="點選此处可获得历次批单相关信息" type=button ACCESSKEY="." class="button" value="<s:text name='button.everySingle.value' />" name='button_Peril_Open_Context2'
		onclick="buttonOnClick2('span_Peril_Context2');">
	<%--历次批单--%>
</s:if>
<span id="span_Peril_Context2" style='width: 800; display: none; position: absolute; background-color: FFFFFF;'>
	<table class=common cellpadding="5" cellspacing="1">
		<tr>
			<td class="prompttitle">
				<s:text name="db.prpLmedicine.serialNo" />
			</td>
			<%--序号--%>
			<td class="prompttitle">
				<s:text name="regist.prpLregist.endorseNo" />
			</td>
			<%--批单号--%>
			<td class="prompttitle">
				<s:text name="regist.prpLregist.endorseStartDate" />
			</td>
			<%--批单生效日期--%>
		</tr>
		<c:if test="${prpPheadDtoList!=null}">
			<c:forEach items="${prpPheadDtoList}" var="prpPheadDto" varStatus="prpPheadDto_status">
				<tr>
					<td class="prompt">${prpPheadDto_status.count }</td>
					<%--resson:在报案登记画面中，已出险次数的历次出险事故的清单中,可以点击报案号关联到相关案件信息--%>
					<td class="prompt">
						<a href="javascript:showEndorse(fm.coreURL.value,'${prpPheadDto.endorseNo}','${prpPheadDto.riskCode}')">${prpPheadDto.endorseNo}</a>
					</td>
					<td class="prompt">${prpPheadDto.validDate }</td>
				</tr>
			</c:forEach>
		</c:if>
		<tr>
			<td colspan=14 class="common">
				<input type=button name='button_Peril_Close_Context2' value="<s:text name='button.close.value' />" class="button" ACCESSKEY="O" onclick="hideSubPage2(this,'span_Peril_Context2')">
			</td>
		</tr>
	</table>
</span>