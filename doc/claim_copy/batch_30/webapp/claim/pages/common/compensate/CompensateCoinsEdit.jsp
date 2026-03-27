<%@ page contentType="text/html; charset=GBK" %>
<%@page import="java.text.DecimalFormat"%>
<%@page import="com.sinosoft.claim.schema.model.*"%>
<%@page import="com.sinosoft.claim.dto.custom.*"%>
<%@page import="java.util.*"%> 

<script>
	
	
/**function deleteallRow1(pageCode,dataPageCode)
　　{
　　  var index = 0;  //当前table索引
　　  var oTBODY = parent.fraInterface.getElementsByName(pageCode)[0].tBodies.item ;
　　  var oTBODYData = parent.fraInterface.getElementById(dataPageCode).tBodies.item ;
　　  var oldelementNumber= oTBODY.rows.length;
　　  
　　  for(var i=0;i<oldelementNumber;i++)
　　  {
　　    oTBODY.removeChild(oTBODY.rows[0]);
　　  }
　　
   }*/
   
   function deleteallRowCoins(pageCode,dataPageCode)
　　{
　　  var index = 0;  //当前table索引
　　  var oTBODY = parent.fraInterface.fm.document.getElementsByName(pageCode)[0].tBodies.item(0);
　　  var oTBODYData = parent.fraInterface.fm.document.getElementById(dataPageCode).tBodies.item(0);
　　  var oldelementNumber= oTBODY.rows.length;
　　  
　　  for(var i=0;i<oldelementNumber;i++)
　　  {
　　    oTBODY.removeChild(oTBODY.rows[0]);
　　  }
   }
   function calSumPaid(chiefFlag,sum){
	   if(chiefFlag == "1"){
		  if( sum > parseInt( parent.fraInterface.fm.prpLcoinsCoinsSumpaid[count-1].value)){
			  parent.fraInterface.fm.prpLcoinsCoinsSumpaid[count-1].value = parseInt(sum)+1;
		  }else {
			  parent.fraInterface.fm.prpLcoinsCoinsSumpaid[count-1].value = parseInt(sum);
		  }
	   }else if(chiefFlag == "0"){
		   parent.fraInterface.fm.prpLcoinsCoinsSumpaid[count-1].value = parseInt( parent.fraInterface.fm.prpLcoinsCoinsSumpaid[count-1].value);
	   }
   }
   
     //需要增加一个函数删除原来所有的分摊数据的方法，由於在理算数据保存再次打开後，再进行分摊试算，则出现重复的计算数据，会出现错误。
     
<%
  
  PrpLcfeecoins prpLcfeecoinsDto = (PrpLcfeecoins)request.getAttribute("prpLcfeecoinsAll");
   
      
  try
  {

    List<PrpLcfeecoins> coinsList = null;
    
    coinsList = prpLcfeecoinsDto.getPrpLcfeecoinsList();
    double prpLcompensateSumCoinUs = 0;//我方赔款金额
    double prpLcompensateSumCoinUsFee = 0;//我方费用金额
    double prpLcompensateSumCoinForOther = 0;//代付赔款金额
    double prpLcompensateSumCoinForOtherFee = 0;//代付费用金额
    //首先，清空已经有的数据：
    %>
    var isPayForOther = parent.fraInterface.document.getElementsByName("isPayForOther");
    if(isPayForOther.length>1 && isPayForOther[0].checked==false && isPayForOther[1].checked==false)
    {
      alert("請先選擇是否代付賠款,系統默認成了代付賠款！如不代付,請重新進行選擇！");
      isPayForOther[0].checked=true;
    }
   deleteallRowCoins('Coins','Coins_Data');
    
  <%
    Iterator it = coinsList.iterator();
    while(it.hasNext()){      
      prpLcfeecoinsDto = (PrpLcfeecoins)it.next();
      //if ("03".equals(prpLcfeecoinsDto.getChargeCode())) {
    	//  continue;
      //}
%>
     // parent.fraInterface.deleteRow(this,'Coins');
      //parent.fraInterface.deleteallRow1('Coins','Coins_Data');
      parent.fraInterface.insertRow('Coins');
      var count = parent.fraInterface.getElementCount("prpLcoinsSerialNo");  
      parent.fraInterface.fm.prpLcoinsSerialNo[count-1].value = '<%=prpLcfeecoinsDto.getId().getSerialNo()%>';
      parent.fraInterface.fm.prpLcoinsChargeCode[count-1].value = '<%=prpLcfeecoinsDto.getChargeCode()%>';
      parent.fraInterface.fm.prpLcoinsChargeName[count-1].value = '<%=prpLcfeecoinsDto.getChargeName()%>';
      parent.fraInterface.fm.prpLcoinsChiefFlag[count-1].value = '<%=prpLcfeecoinsDto.getChiefFlag()%>';
      parent.fraInterface.fm.prpLcoinsCoinsCode[count-1].value = '<%=prpLcfeecoinsDto.getCoinsCode()%>';
      parent.fraInterface.fm.prpLcoinsCoinsName[count-1].value = '<%=prpLcfeecoinsDto.getCoinsName()%>';
      parent.fraInterface.fm.prpLcoinsCurrency[count-1].value = '<%=prpLcfeecoinsDto.getCurrency()%>';
      parent.fraInterface.fm.prpLcoinsCoinsRate[count-1].value = '<%=prpLcfeecoinsDto.getCoinsRate()%>';
      
    parent.fraInterface.fm.prpLcoinsCoinsSumpaid[count-1].value = '<%=new DecimalFormat("#").format(prpLcfeecoinsDto.getCoinsSumPaid())%>';
      calSumPaid(parent.fraInterface.fm.prpLcoinsChiefFlag[count-1].value,parent.fraInterface.fm.prpLcoinsCoinsSumpaid[count-1].value);
     // parent.fraInterface.fm.prpLcoinsSumpaid[count-1].value = '<%=prpLcfeecoinsDto.getCoinsSumPaid()%>';
      parent.fraInterface.fm.prpLcoinsCoinsType[count-1].value = '<%=prpLcfeecoinsDto.getCoinsType()%>';
      parent.fraInterface.fm.prpLcoinsLossFeeType[count-1].value = '<%=prpLcfeecoinsDto.getLossFeeType()%>';
      <%if(prpLcfeecoinsDto.getLossFeeType().equals("0")){%>
      parent.fraInterface.fm.prpLcoinsTypeForShow[count-1].value = '賠款';
      <%} 
      if(prpLcfeecoinsDto.getLossFeeType().equals("1")){%>
      parent.fraInterface.fm.prpLcoinsTypeForShow[count-1].value = '費用';
      <%}
      if(prpLcfeecoinsDto.getChiefFlag().equals("2")){%>
      parent.fraInterface.fm.prpLcoinsChiefFlagShow[count-1].value = '是';
      <%}
      if(prpLcfeecoinsDto.getChiefFlag().equals("1")){%>
      parent.fraInterface.fm.prpLcoinsChiefFlagShow[count-1].value = '否';
      <%}
      if(prpLcfeecoinsDto.getCoinsType().equals("1")){%>
      parent.fraInterface.fm.prpLcoinsCoinsTypeShow[count-1].value = '我方';
      <%}
      if(prpLcfeecoinsDto.getCoinsType().equals("2")){%>
      parent.fraInterface.fm.prpLcoinsCoinsTypeShow[count-1].value = '系統內他方';
      <%}
      if(prpLcfeecoinsDto.getCoinsType().equals("3")){%>
      parent.fraInterface.fm.prpLcoinsCoinsTypeShow[count-1].value = '系統外他方';
      <%}
      //我方赔款              
      if(prpLcfeecoinsDto.getLossFeeType().equals("0") && prpLcfeecoinsDto.getCoinsType().equals("1")){
          prpLcompensateSumCoinUs = prpLcompensateSumCoinUs + prpLcfeecoinsDto.getCoinsSumPaid();
      }else if(prpLcfeecoinsDto.getLossFeeType().equals("1") && prpLcfeecoinsDto.getCoinsType().equals("1")){
      //我方费用
          prpLcompensateSumCoinUsFee = prpLcompensateSumCoinUsFee + prpLcfeecoinsDto.getCoinsSumPaid();
      }else if(prpLcfeecoinsDto.getLossFeeType().equals("0") && !prpLcfeecoinsDto.getCoinsType().equals("1")){
      //他方赔款
          prpLcompensateSumCoinForOther = prpLcompensateSumCoinForOther + prpLcfeecoinsDto.getCoinsSumPaid();
      }else if(prpLcfeecoinsDto.getLossFeeType().equals("1") && !prpLcfeecoinsDto.getCoinsType().equals("1")){
      //他方费用
          prpLcompensateSumCoinForOtherFee = prpLcompensateSumCoinForOtherFee + prpLcfeecoinsDto.getCoinsSumPaid();
      }
    }
    %>
    var prpLcompensateSumCoinUs = parent.fraInterface.document.getElementsByName("prpLcompensateSumCoinUs");
    var prpLcompensateSumCoinUsFee = parent.fraInterface.document.getElementsByName("prpLcompensateSumCoinUsFee");
    var prpLcompensateSumCoinForOther = parent.fraInterface.document.getElementsByName("prpLcompensateSumCoinForOther");
    var prpLcompensateSumCoinForOtherFee = parent.fraInterface.document.getElementsByName("prpLcompensateSumCoinForOtherFee");
    var prpLcompensateSumCoinForOtherBak = parent.fraInterface.document.getElementsByName("prpLcompensateSumCoinForOtherBak");
    var prpLcompensateSumCoinForOtherFeeBak = parent.fraInterface.document.getElementsByName("prpLcompensateSumCoinForOtherFeeBak");
    if(prpLcompensateSumCoinUs.length>0)
    {
      prpLcompensateSumCoinUs[0].value='<%=new DecimalFormat("#").format(prpLcompensateSumCoinUs)%>';
    }
    if(prpLcompensateSumCoinUsFee.length>0)
    {
      prpLcompensateSumCoinUsFee[0].value='<%=new DecimalFormat("#").format(prpLcompensateSumCoinUsFee)%>';
    }
    if(prpLcompensateSumCoinForOther.length>0 && isPayForOther.length>0 && isPayForOther[0].checked==true)
    {
      prpLcompensateSumCoinForOther[0].value='<%=new DecimalFormat("#").format(prpLcompensateSumCoinForOther)%>';
      prpLcompensateSumCoinForOtherBak[0].value='<%=new DecimalFormat("#").format(prpLcompensateSumCoinForOther)%>';
    }else{
      prpLcompensateSumCoinForOther[0].value='0';
      prpLcompensateSumCoinForOtherBak[0].value='<%=new DecimalFormat("#").format(prpLcompensateSumCoinForOther)%>';
    }
    if(prpLcompensateSumCoinForOtherFee.length>0 && isPayForOther.length>0 && isPayForOther[0].checked==true)
    {
      prpLcompensateSumCoinForOtherFee[0].value='<%=new DecimalFormat("#").format(prpLcompensateSumCoinForOtherFee)%>';
      prpLcompensateSumCoinForOtherFeeBak[0].value='<%=new DecimalFormat("#").format(prpLcompensateSumCoinForOtherFee)%>';
    }else{
      prpLcompensateSumCoinForOtherFee[0].value='0';
      prpLcompensateSumCoinForOtherFeeBak[0].value='<%=new DecimalFormat("#").format(prpLcompensateSumCoinForOtherFee)%>';
    }
    <%
    
  }
  catch(Exception e)
  {
    e.printStackTrace();
    out.println("window.status='没有查询到对应的信息';");
  }
  
%>                                                     

</script>