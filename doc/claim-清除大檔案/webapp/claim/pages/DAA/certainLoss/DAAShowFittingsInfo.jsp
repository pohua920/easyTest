<%--
****************************************************************************
* DESC       ： 显示换件信息
* AUTHOR     ： 中科软
* CREATEDATE ： 2005-12-10
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@page contentType="text/html;charset=GBK"%>
<%-- 引入bean类部分 --%>
<%@page import="java.util.*"%>
<%@page import="com.sinosoft.claim.dto.domain.PrpLclaimFittingsDto"%>
<%@page import="com.sinosoft.claim.ui.control.facade.UIClaimFittingsSaveFacade"%>
<%@page import="com.sinosoft.claim.dto.claimdamages.*"%>
<%@page import="com.sinosoft.claim.bl.facade.BLPrpLscheduleMainWFFacade"%>
<%@page import="com.sinosoft.claim.dto.domain.PrpLscheduleMainWFDto"%>
<%@page import="com.sinosoft.claim.bl.facade.BLPrpDcompanyFacade"%>
<%@page import="com.sinosoft.claim.dto.domain.PrpDcompanyDto"%>
<%@page import="com.sinosoft.claim.dto.custom.UserDto"%>
<%@page import="com.sinosoft.claim.util.StringConvert"%>
<script>
function setReadonlyOfElement(iElement) {
 	if (iElement.type != null) {
 		if (iElement.type == "select-one") {
 			if (iElement.setReadonlyFlag == true) {
 				return;
 			} else {
 				iElement.setReadonlyFlag = true;
 			}

 			var optionTags = new Array();
 			var index = 0;
 			for (var j = iElement.options.length - 1; j >= 0; j--) {
 				var tag = new Array();
 				tag["value"] = iElement.options[j].value;
 				tag["text"] = iElement.options[j].text;
 				optionTags[index++] = tag;
 				if (iElement.options[j].value != iElement.value) {
 					iElement.remove(j);
 				}
 			}
 			iElement.optionTags = optionTags;
 		} else if (iElement.type == "text") {
 			iElement.onfocus = null;
 			iElement.readOnly = true;
 			iElement.className = "readonly";
 		}
 	}
 }

 var partIds = window.opener.fm.prpLcomponentIndId;
 var repairIds = window.opener.fm.prpLrepairFeeIndId;

 function checkComponent(partId) {
 	for (var i = 0; i < partIds.length; i++) {
 		if (partId == partIds[i].value) {
 			return true;
 		}
 	}

 	return false;
 }

 function checkRepair(repairID) {
 	for (var i = 0; i < repairIds.length; i++) {
 		if (repairID == repairIds[i].value) {
 			return true;
 		}
 	}

 	return false;
 }

 <%

 String registNo = request.getParameter("registNo");
 String lossItemCode = request.getParameter("lossItemCode");


 UserDto user = (UserDto) session.getAttribute("user"); //用户信息
 String comCode = "";
 if (null == user) {
 	user = (UserDto) request.getSession().getAttribute("user");
 }
 if (null != user) {
 	comCode = user.getComCode();
 }
 boolean isEstimateCom = false;
 BLPrpDcompanyFacade prpDcompanyFacade = new BLPrpDcompanyFacade();

 if (comCode != null && !comCode.equals("")) {
 	PrpDcompanyDto prpDcompanyDto = prpDcompanyFacade.findByPrimaryKey(comCode);
 	if (prpDcompanyDto != null) {
 		String comAttribute = prpDcompanyDto.getComAttribute();
 		if (comAttribute != null && comAttribute.equals("1")) {
 			isEstimateCom = true;
 		}
 	}
 }
 isEstimateCom = false;



 String conditiong = "";

 try {

 	PrpLclaimFittingsDto prpLclaimFittingsDto = null;
 	LossRtnBodyDto lossRtnBodyDto = new UIClaimFittingsSaveFacade().getReturnObj(registNo, lossItemCode);
 	LossInfoDto lossInfoDto = lossRtnBodyDto.getLossInfoDto(); //主信息
 	List < LossFitInfoDto > lossFitInfoDtos = lossRtnBodyDto.getLossFitInfoDtos(); //零部件信息
 	List < LossRepairInfoDto > lossRepairinfoDtos = lossRtnBodyDto.getLossRepairinfoDtos(); //修理项目费用
 	List < LossAssistInfoDto > lossAssistInfoDtos = lossRtnBodyDto.getLossAssistInfoDtos(); //辅料信息
 	%>
 	if (window.opener.fm.prpLverifyLossLossItemCode.value == "1") { //标的车
 		window.opener.fm.prpLcarLossCarKindName.value = '<%=lossInfoDto.getVehKindName()%>';
 	} else {
 		window.opener.fm.carKindCode.value = '<%=lossInfoDto.getVehKindCode()%>';
 	}
 	window.opener.fm.prpLcarLossModelCode.value = '<%=lossInfoDto.getVehCertainCode()%>';
 	window.opener.fm.prpLcarLossBrandName.value = '<%=lossInfoDto.getVehCertainName()%>';
 	window.opener.fm.prpLcarLossCarKindCode.value = '<%=lossInfoDto.getVehKindCode()%>';
 	//罗畅 自定义车型//window.opener.document.getElementById("flag").innerHTML =  '<%="1".equals(lossInfoDto.getSelfConfigFlag())?"是":"否"%>';
 	//罗畅 自定义车型//window.opener.fm.prpLcarLossSelfConfigFlag.value = '<%=lossInfoDto.getSelfConfigFlag()%>';
 	window.opener.fm.prpLcarLossSumManager.value = '<%=lossInfoDto.getManageFeeRate()%>';
 	window.opener.fm.prpLcarLossSumManageFeeRate.value = '<%=lossInfoDto.getManageFeeRate()%>';
 	window.opener.fm.prpLcarLossSumManager.onblur();
 	//window.opener.fm.prpLcarLossRepairFactoryCode.value = '<%=lossInfoDto.getChgCompSetCode()%>';
 	window.opener.fm.prpLrepairFeeRepairFactoryCode.value = '<%=lossInfoDto.getChgCompSetCode()%>';

 	<%
 		String fitFlag = "";
 	for (LossFitInfoDto fitInfoDto: lossFitInfoDtos) { //零部件
 		fitFlag = fitInfoDto.getSelfConfigFlag(); %>
 		if (!checkComponent('<%=fitInfoDto.getPartId()%>') && '<%=fitInfoDto.getStatus()%>' != 'D') {
 			window.opener.insertRowTableComponent('Component', 'Component_Data', window.opener.fm.buttonGetFittings);

 			var count = window.opener.getElementCount("prpLcomponentCompName");

 			<%
 			if (!"0".equals(fitFlag)) {
 				fitFlag = "1"; %>
 				var tr = window.opener.fm.prpLcomponentCompCode[count - 1].parentElement.parentElement;
 				var tds = tr.getElementsByTagName("td");
 				//for(var i=0;i<tds.length;i++){
 				//	tds[i].style.backgroundColor = "white";
 				//}

 				var inputs = tr.getElementsByTagName("input");
 				for (var i = 0; i < inputs.length; i++) {
 					if (inputs[i].type == "text") {
 						inputs[i].style.color = "#CC3333"
 					}
 				} <%
 			} %>
 				window.opener.fm.prpLcomponentKindCode[count - 1].value = '<%=lossInfoDto.getInsureTermCode()%>';
 			window.opener.fm.prpLcomponentKindName[count - 1].value = '<%=lossInfoDto.getInsureTerm()%>';
 			window.opener.fm.prpLcomponentIndId[count - 1].value = '<%=fitInfoDto.getPartId()%>';
 			window.opener.fm.prpLcomponentPartCode[count - 1].value = '<%=fitInfoDto.getPartGroupCode()%>';
 			window.opener.fm.prpLcomponentPartName[count - 1].value = '<%=fitInfoDto.getPartGroupName()%>';
 			window.opener.fm.prpLcomponentFlag[count - 1].value = '<%=fitFlag%>';
 			setReadonlyOfElement(window.opener.fm.prpLcomponentPartName[count - 1]);
 			window.opener.fm.prpLcomponentCompCode[count - 1].value = '<%=fitInfoDto.getPartStandardCode()%>';
 			window.opener.fm.prpLcomponentCompName[count - 1].value = '<%=fitInfoDto.getPartStandard()%>';
 			setReadonlyOfElement(window.opener.fm.prpLcomponentCompName[count - 1]);
 			window.opener.fm.prpLcomponentOriginalId[count - 1].value = '<%=fitInfoDto.getOriginalId()%>';
 			setReadonlyOfElement(window.opener.fm.prpLcomponentOriginalId[count - 1]);
 			window.opener.fm.prpLcomponentMaterialFee[count - 1].value = '<%=fitInfoDto.getLossPrice()%>';
 			//window.opener.fm.prpLcomponentSumCheckLoss[count-1].value = '<%=fitInfoDto.getRepairSitePrice()%>';
 			//setReadonlyOfElement(window.opener.fm.prpLcomponentSumCheckLoss[count-1]);
 			window.opener.fm.prpLcomponentQuantity[count - 1].value = '<%=fitInfoDto.getLossCount()%>';
 			window.opener.fm.prpLcomponentRestFee[count - 1].value = '<%=fitInfoDto.getRemnant()%>';
 			//window.opener.fm.prpLcomponentIndId[count-1].value = '';
 			window.opener.fm.prpLcomponentIfRemain[count - 1].value = '<%=fitInfoDto.getIfRemain()%>';
 			window.opener.fm.prpLcomponentMaterialFee[count - 1].onblur();
 			window.opener.fm.prpLcomponentSys4SPrice[count - 1].value = '<%=fitInfoDto.getRefPrice1()%>';
 			window.opener.fm.prpLcomponentSysMarketPrice[count - 1].value = '<%=fitInfoDto.getRefPrice2()%>';
 			window.opener.fm.prpLcomponentSysMatchPrice[count - 1].value = '<%=fitInfoDto.getRefPrice3()%>'; <%
 			if (isEstimateCom) { %>
 					window.opener.fm.prpLcomponentNative4SPrice[count - 1].value = '<%=fitInfoDto.getLocPrice1()%>';
 				window.opener.fm.prpLcomponentNative4SPrice[count - 1].style.display = "none"; <%
 			} else { %>
 					window.opener.fm.prpLcomponentNative4SPrice[count - 1].value = '<%=fitInfoDto.getLocPrice1()%>'; <%
 			} %>

 			//window.opener.fm.prpLcomponentNative4SPrice[count-1].value = '********';
 			setReadonlyOfElement(window.opener.fm.prpLcomponentNative4SPrice[count - 1]); <%
 			if (isEstimateCom) { %>
 					window.opener.fm.prpLcomponentNativeMarketPrice[count - 1].style.display = "none";
 				window.opener.fm.prpLcomponentNativeMarketPrice[count - 1].value = '<%=fitInfoDto.getLocPrice2()%>'; <%
 			} else { %>
 					window.opener.fm.prpLcomponentNativeMarketPrice[count - 1].value = '<%=fitInfoDto.getLocPrice2()%>'; <%
 			} %>

 			//window.opener.fm.prpLcomponentNativeMarketPrice[count-1].value = '********';
 			setReadonlyOfElement(window.opener.fm.prpLcomponentNativeMarketPrice[count - 1]);
 			window.opener.fm.prpLcomponentNativeMatchPrice[count - 1].value = '<%=fitInfoDto.getLocPrice3()%>';
 			setReadonlyOfElement(window.opener.fm.prpLcomponentNativeMatchPrice[count - 1]);
 			window.opener.fm.prpLcomponentRemark[count - 1].value = '<%=StringConvert.replace(fitInfoDto.getRemark(),"'
 			","\\\
 			'")%>';
 			//window.opener.fm.prpLcomponentFlag[count-1].value = '1';
 		} else if ('<%=fitInfoDto.getStatus()%>' == 'D') {
 			for (var i = 0; i < partIds.length; i++) {
 				if ('<%=fitInfoDto.getPartId()%>' == partIds[i].value) {
 					window.opener.fm.buttonComponentDelete[i].onclick();
 					break;
 				}
 			}
 		} <%
 	}
 	double countRepairFee = 0d;
 	String repairSelfConfigFlag = "";
 	for (LossRepairInfoDto lossRepairInfoDto: lossRepairinfoDtos) { //修理项目费用
 		//countRepairFee+=Double.parseDouble(lossRepairInfoDto.getRepairFee());
 		repairSelfConfigFlag = lossRepairInfoDto.getSelfConfigFlag(); %>
 		if (!checkRepair('<%=lossRepairInfoDto.getRepairId()%>') && '<%=lossRepairInfoDto.getStatus()%>' != 'D') {
 			window.opener.insertRowTableRepairFee('RepairFee', 'RepairFee_Data', window.opener.fm.buttonRepairFee);
 			var RepairInfocount = window.opener.getElementCount("prpLrepairFeeRepairType"); <%
 			if (!"0".equals(repairSelfConfigFlag)) { %>
 				var tr = window.opener.fm.prpLrepairFeeRepairType[RepairInfocount - 1].parentElement.parentElement;
 				var tds = tr.getElementsByTagName("td");
 				//for(var i=0;i<tds.length;i++){
 				//tds[i].style.backgroundColor = "yellow";
 				//}

 				var inputs = tr.getElementsByTagName("input");
 				for (var i = 0; i < inputs.length; i++) {
 					if (inputs[i].type == "text") {
 						inputs[i].style.color = "#CC3333"
 					}
 				} <%
 			} %>
 				window.opener.fm.prpLrepairFeeKindCode[RepairInfocount - 1].value = '<%=lossInfoDto.getInsureTermCode()%>';
 			window.opener.fm.prpLrepairFeeKindName[RepairInfocount - 1].value = '<%=lossInfoDto.getInsureTerm()%>';
 			window.opener.fm.prpLrepairFeeIndId[RepairInfocount - 1].value = '<%=lossRepairInfoDto.getRepairId()%>';
 			window.opener.fm.prpLrepairFeeFlag[RepairInfocount - 1].value = '<%=repairSelfConfigFlag%>'
 			window.opener.fm.prpLrepairFeeRepairType[RepairInfocount - 1].value = '<%=lossRepairInfoDto.getRepairCode()%>';
 			setReadonlyOfElement(window.opener.fm.prpLrepairFeeRepairType[RepairInfocount - 1]);
 			//window.opener.fm.code[RepairInfocount -1].value = '<%=lossRepairInfoDto.getRepairKindCode()%>';
 			//window.opener.fm.name[RepairInfocount -1].value = '<%=lossRepairInfoDto.getRepairKindName()%>';

 			//setReadonlyOfElement(window.opener.fm.name[RepairInfocount -1]);
 			window.opener.fm.prpLrepairFeeCompName[RepairInfocount - 1].value = '<%=lossRepairInfoDto.getRepairItemName()%>';
 			setReadonlyOfElement(window.opener.fm.prpLrepairFeeCompName[RepairInfocount - 1]);
 			window.opener.fm.prpLrepairFeeCompCode[RepairInfocount - 1].value = '<%=lossRepairInfoDto.getRepairItemCode()%>';
 			window.opener.fm.prpLrepairFeeManHour[RepairInfocount - 1].value = '<%=lossRepairInfoDto.getManHour()%>';
 			window.opener.fm.prpLrepairFeeManHourUnitPrice[RepairInfocount - 1].value = '<%=lossRepairInfoDto.getUnitManHourPrice()%>';
 			window.opener.fm.prpLrepairFeeSumDefLoss[RepairInfocount - 1].value = '<%=lossRepairInfoDto.getRepairFee()%>';
 			window.opener.fm.prpLrepairFeeRemark[RepairInfocount - 1].value = '<%=StringConvert.replace(lossRepairInfoDto.getRemark(),"'
 			","\\\
 			'")%>';
 			window.opener.fm.prpLrepairFeeManHourUnitPrice[RepairInfocount - 1].onblur();
 		} else if ('<%=lossRepairInfoDto.getStatus()%>' == 'D') {
 			for (var i = 0; i < repairIds.length; i++) {
 				if ('<%=lossRepairInfoDto.getRepairId()%>' == repairIds[i].value) {
 					window.opener.fm.buttonRepairFeeDelete[i].onclick();
 					break;
 				}
 			}
 		} <%
 	}

 	for (LossAssistInfoDto lossAssistInfoDto: lossAssistInfoDtos) { //辅料信息
 		//countRepairFee += Double.parseDouble(lossAssistInfoDto.getMaterialFee());
 		String selfConfigFlag = lossAssistInfoDto.getSelfConfigFlag(); %>
 		if (!checkRepair('<%=lossAssistInfoDto.getAssistId()%>') && '<%=lossAssistInfoDto.getStatus()%>' != 'D') {
 			window.opener.insertRowTableRepairFee('RepairFee', 'RepairFee_Data', window.opener.fm.buttonRepairFee);
 			var AssistInfocount = window.opener.getElementCount("prpLrepairFeeRepairType"); <%
 			if (!"0".equals(selfConfigFlag)) { %>
 				var tr = window.opener.fm.prpLrepairFeeRepairType[AssistInfocount - 1].parentElement.parentElement;
 				var tds = tr.getElementsByTagName("td");
 				//for(var i=0;i<tds.length;i++){
 				//	tds[i].style.backgroundColor = "white";
 				//}

 				var inputs = tr.getElementsByTagName("input");
 				for (var i = 0; i < inputs.length; i++) {
 					if (inputs[i].type == "text") {
 						inputs[i].style.color = "#CC3333"
 					}
 				} <%
 			} %>
 				window.opener.fm.prpLrepairFeeKindCode[AssistInfocount - 1].value = '<%=lossInfoDto.getInsureTermCode()%>';
 			window.opener.fm.prpLrepairFeeKindName[AssistInfocount - 1].value = '<%=lossInfoDto.getInsureTerm()%>';
 			window.opener.fm.prpLrepairFeeIndId[AssistInfocount - 1].value = '<%=lossAssistInfoDto.getAssistId()%>';
 			window.opener.fm.prpLrepairFeeFlag[AssistInfocount - 1].value = '<%=selfConfigFlag%>';
 			window.opener.fm.prpLrepairFeeRepairType[AssistInfocount - 1].value = '99'; //辅料其他
 			setReadonlyOfElement(window.opener.fm.prpLrepairFeeRepairType[AssistInfocount - 1]);
 			//setReadonlyOfElement(window.opener.fm.name[AssistInfocount -1]);
 			window.opener.fm.prpLrepairFeeCompName[AssistInfocount - 1].value = '<%=lossAssistInfoDto.getMaterialName()%>';
 			setReadonlyOfElement(window.opener.fm.prpLrepairFeeCompName[AssistInfocount - 1]);
 			window.opener.fm.prpLrepairFeeManHour[AssistInfocount - 1].value = '<%=lossAssistInfoDto.getCount()%>';
 			window.opener.fm.prpLrepairFeeManHourUnitPrice[AssistInfocount - 1].value = '<%=lossAssistInfoDto.getUnitPrice()%>';

 			//window.opener.fm.prpLrepairFeeManHour[AssistInfocount -1].value ='1';
 			//window.opener.fm.prpLrepairFeeManHourUnitPrice[AssistInfocount -1].value ='<%=lossAssistInfoDto.getMaterialFee()%>';

 			window.opener.fm.prpLrepairFeeSumDefLoss[AssistInfocount - 1].value = '<%=lossAssistInfoDto.getMaterialFee()%>';
 			window.opener.fm.prpLrepairFeeRemark[AssistInfocount - 1].value = '<%=StringConvert.replace(lossAssistInfoDto.getRemark(),"'
 			","\\\
 			'")%>';
 			window.opener.fm.prpLrepairFeeManHourUnitPrice[AssistInfocount - 1].onblur();
 		} else if ('<%=lossAssistInfoDto.getStatus()%>' == 'D') {
 			for (var i = 0; i < repairIds.length; i++) {
 				if ('<%=lossAssistInfoDto.getAssistId()%>' == repairIds[i].value) {
 					window.opener.fm.buttonRepairFeeDelete[i].onclick();
 					break;
 				}
 			}
 		} <%
 	} %>
 	//window.opener.fm.SumDefLoss1.value = '<%=countRepairFee%>';
 	//window.opener.fm.SumDefLoss4.value = '<%=countRepairFee%>
 	';
<%
    	if(isEstimateCom) {
    %>
	
<%    		
    	} else {
    %>
	//window.opener.fm.prpLcarLossRepairFactoryCode.onchange();
<%		
    	}
    %>
	
<%
    
    //清空Map
    new UIClaimFittingsSaveFacade().removeAllreturnObjMap(registNo);
  }
  catch(Exception e)
  {
    e.printStackTrace();
    out.println("window.status='沒有查詢到對應的部件信息';");
  }
  
%>
	window.close();
</script>
