<%--
****************************************************************************
* DESC       ： 显示换件信息
* AUTHOR     ： sunchenggang
* CREATEDATE ： 2005-12-10
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@page contentType="text/html;charset=gb2312"%>                    

<%-- 引入bean类部分 --%>
<%@page import="java.util.*"%>
<%@page import="com.sinosoft.claim.bl.facade.BLPrpLclaimFittingsFacade"%>
<%@page import="com.sinosoft.claim.dto.domain.PrpLclaimFittingsDto"%>

	
<script language="javascript">


<%
  try
  {
    PrpLclaimFittingsDto prpLclaimFittingsDto = null;
    String lossItemCode;
    Collection fittinsList = (Collection)request.getAttribute("fittingsList");
    lossItemCode = (String)request.getAttribute("lossItemCode");
    if (fittinsList==null||fittinsList.size()==0){
%>
    window.close();
<%} 
        
    Iterator it = fittinsList.iterator();
%>
		var arrData = new Array();
		var arrRow = new Array(); 
		var lossItemCode = <%=lossItemCode%>;
		//var count = window.opener.getElementCount("prpLcomponentCompName");  
		var factoryCode = window.opener.fm.prpLcarLossRepairFactoryCode[lossItemCode].value;
<%    
    while(it.hasNext()){
      prpLclaimFittingsDto = (PrpLclaimFittingsDto)it.next();      
%>    

      
<%

%>
	var verpComprice;
	arrRow = new Array(); 
	arrRow.prpLcomponentKindCode = '<%=prpLclaimFittingsDto.getKindCode()%>';
	arrRow.prpLcomponentKindName = '<%=prpLclaimFittingsDto.getKindName()%>';
	arrRow.prpLcomponentCompCode = '<%=prpLclaimFittingsDto.getFittingCode()%>';	
	arrRow.prpLcomponentCompName = '<%=prpLclaimFittingsDto.getFittingName()%>';
	arrRow.prpLcomponentMaterialFee = '<%=prpLclaimFittingsDto.getCertaPrice()%>';
	arrRow.prpLcomponentQuantity = '<%=prpLclaimFittingsDto.getCertaQuantity()%>';
	arrRow.prpLcomponentOriginalId = '<%=prpLclaimFittingsDto.getOriginalID()%>';
	arrRow.prpLcomponentIndId = '<%=prpLclaimFittingsDto.getIndId()%>';
	arrRow.prpLcomponentSys4SPrice = '<%=prpLclaimFittingsDto.getSys4SPrice()%>';
	arrRow.prpLcomponentSysMarketPrice = '<%=prpLclaimFittingsDto.getSysMarketPrice()%>';
	arrRow.prpLcomponentSysMatchPrice = '<%=prpLclaimFittingsDto.getSysmatchPrice()%>';
	arrRow.prpLcomponentNative4SPrice = '<%=prpLclaimFittingsDto.getNative4SPrice()%>';
	arrRow.prpLcomponentNativeMarketPrice = '<%=prpLclaimFittingsDto.getNativeMarketPrice()%>';
	arrRow.prpLcomponentNativeMatchPrice = '<%=prpLclaimFittingsDto.getNativeMatchPrice()%>';
	//arrRow.prpLcomponentVerpCompPrice = '<%=prpLclaimFittingsDto.getVerifyPrice()%>';
	arrRow.prpLcomponentRemark = '<%=prpLclaimFittingsDto.getRemark()%>';
	arrRow.prpLcomponentFlag = '1';
	if(factoryCode == "01"){//修理厂4s店，定损单价带入专修价格
		arrRow.prpLcomponentPriceType = "S";
		arrRow.prpLcomponentMaterialFee = '<%=prpLclaimFittingsDto.getNative4SPrice()%>';
		materialFee = point(round(parseFloat(<%=prpLclaimFittingsDto.getNative4SPrice()%>)*<%=prpLclaimFittingsDto.getCertaQuantity()%>,0),0);
		arrRow.prpLcomponentSumDefLoss = materialFee;
	}
	if(factoryCode == "02" || factoryCode == "03"){//修理厂一二类厂，定损单价带入市场价
		arrRow.prpLcomponentPriceType = "M";
		arrRow.prpLcomponentMaterialFee =  '<%=prpLclaimFittingsDto.getNativeMarketPrice()%>';
		materialFee = point(round(parseFloat(<%=prpLclaimFittingsDto.getNativeMarketPrice()%>)*<%=prpLclaimFittingsDto.getCertaQuantity()%>,0),0);
		arrRow.prpLcomponentSumDefLoss = materialFee;
	}
	if(factoryCode == "04"){//修理厂其他，定损单价带入副厂价
		arrRow.prpLcomponentPriceType = "O";
		arrRow.prpLcomponentMaterialFee = '<%=prpLclaimFittingsDto.getNativeMatchPrice()%>';
		materialFee = point(round(parseFloat(<%=prpLclaimFittingsDto.getNativeMatchPrice()%>)*<%=prpLclaimFittingsDto.getCertaQuantity()%>,0),0);
		arrRow.prpLcomponentSumDefLoss = materialFee;
	}
	
	arrData[arrData.length] = arrRow;                 
<%      
    }
%>
    window.opener.quickCaseInsertCarLossFittings(arrData,lossItemCode);
  	window.close();
<%
  }
  catch(Exception e)
  {
    e.printStackTrace();
    out.println("window.status='没有查询到对应的部件信息';");
  }
  
%>                                                     

</script>

