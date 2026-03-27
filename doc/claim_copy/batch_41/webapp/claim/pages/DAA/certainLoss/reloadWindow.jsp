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
<%@page import="com.sinosoft.claim.bl.facade.BLPrpLclaimFittingsFacade"%>
<%@page import="com.sinosoft.claim.dto.domain.PrpLclaimFittingsDto"%>

<script language="javascript">
<%
  try
  {
    PrpLclaimFittingsDto prpLclaimFittingsDto = null;
    Collection fittinsList = (Collection)request.getAttribute("fittingsList");;
    //System.out.println("fittinsList0000000=="+fittinsList.size()); 修改当list为null时候，报的错误。
    if (fittinsList==null||fittinsList.size()==0){
    %>
    window.close();
    <%} 
    Iterator it = fittinsList.iterator();
%>
		var arrData = new Array();
		var arrRow = new Array(); 
		var count = window.opener.getElementCount("prpLcomponentCompName");  
	    var factoryCode = window.opener.fm.prpLrepairFeeRepairFactoryCode.value;
<%    
    while(it.hasNext()){
      prpLclaimFittingsDto = (PrpLclaimFittingsDto)it.next();      
%>    
//      window.opener.insertRowTableComponent('Component','Component_Data',window.opener.fm.buttonComponentInsert);
//      var count = window.opener.getElementCount("prpLcomponentCompName");  
<%
        //System.out.println("prpLclaimFittingsDto.getSysmatchPrice()=" + prpLclaimFittingsDto.getSysmatchPrice()); 
        //System.out.println("prpLclaimFittingsDto.getKindCode() ... " + prpLclaimFittingsDto.getFittingName());  
%>
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
	arrRow.prpLcomponentVerpCompPrice = '<%=prpLclaimFittingsDto.getVerifyPrice()%>';
	arrRow.prpLcomponentRemark = '<%=prpLclaimFittingsDto.getRemark()%>';
	arrRow.prpLcomponentFlag = '1';
	if(factoryCode == "01"){//修理厂4s店，定损单价带入专修价格
		arrRow.prpLcomponentPriceType = "S";
		//arrRow.prpLcomponentSumDefLoss = '<%=prpLclaimFittingsDto.getNative4SPrice()%>';
    arrRow.prpLcomponentMaterialFee = '<%=prpLclaimFittingsDto.getNative4SPrice()%>';
		
	}
	if(factoryCode == "02" || factoryCode == "03"){//修理厂一二类厂，定损单价带入市场价
		arrRow.prpLcomponentPriceType = "M";
		//arrRow.prpLcomponentSumDefLoss =  '<%=prpLclaimFittingsDto.getNativeMarketPrice()%>';
		arrRow.prpLcomponentMaterialFee = '<%=prpLclaimFittingsDto.getNativeMarketPrice()%>';
	}
	if(factoryCode == "04"){//修理厂其他，定损单价带入副厂价
		arrRow.prpLcomponentPriceType = "O";
		//arrRow.prpLcomponentSumDefLoss = '<%=prpLclaimFittingsDto.getNativeMatchPrice()%>';
		arrRow.prpLcomponentMaterialFee = '<%=prpLclaimFittingsDto.getNativeMatchPrice()%>';
	}
	arrData[arrData.length] = arrRow;                 
<%      
    }
%>
    window.opener.insertCarLossFittings(arrData,count);
  	window.close();
<%
  }
  catch(Exception e)
  {
    e.printStackTrace();
    out.println("window.status='沒有查詢到對應的部件信息';");
  }
  
%>                                                     

</script>

