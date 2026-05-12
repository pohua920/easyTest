<%--
****************************************************************************
* DESC       ：列印前输入业务号页面
* AUTHOR     ： lixiang
* CREATEDATE ： 2004-11-12
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=gbk" %>
<%@include file="/common/taglibs.jsp"%>
<%@include file="/common/i18njs.jsp"%>
<%@include file="/common/meta_css.jsp"%>


<%@page import="com.sinosoft.claim.dto.custom.*"%>
<%@page import="com.sinosoft.sysframework.common.datatype.DateTime"%>

<%
  String strPrintType = request.getParameter("printType");
  System.out.println("strPrintType="+strPrintType);
  String strTitleName = "";
  String strBizName   = "";
  String strWherePart = "";
  int    intCount     = 0;
  String strMessage    = "";
  String strSerialNo = "";//车牌号码
  String strPolicyNo = "";//保单号
  String strInsuredName = "";//被保险人 
  String styleStr = "";
  if(strPrintType.equals("LossCarDetail") || strPrintType.equals("CarClaim") || strPrintType.equals("CustomerInterview")){//部分情况不需录入业务号
  	styleStr="display:none;";
  }
  if( strPrintType.equals("Regist") )
  {
    //1.报案记录（代抄单）
    strTitleName = "機動車輛保險備案記錄（承保理賠訊息）列印";
    strBizName   = "備案號碼";
    strPolicyNo  = "保單號碼";
    strInsuredName = "被保險人";
    strSerialNo  = "牌照號碼";
  }
  
  if( strPrintType.equals("Pilfer") )
  {
    //2.出险（盗抢）证明
    strTitleName = "出险（盗抢）证明列印";
    strBizName   = "赔案号";
  }
  if( strPrintType.equals("Cancelnotice") )
  {
    //3.拒赔通知书
    strTitleName = "拒赔通知书列印";
    strBizName   = "赔案号";
  }
  if (strPrintType.equals("Cancelcompel"))
  {
	strTitleName = "事故责任强制保险拒赔通知书列印";
    strBizName   = "赔案号";
  }
 
  if (strPrintType.equals("PromisesFeeForService"))
  {
	strTitleName = "承诺支付医疗费用担保函列印";
    strBizName   = "赔案号";
  }
 
  if( strPrintType.equals("Canceltrans") )
  {
    //4.拒赔案件报告书
    strTitleName = "拒赔案件报告书列印";
    strBizName   = "赔案号";
  }
  if( strPrintType.equals("LossSimple") )
  {
    //5.损失情况简易确认书
    strTitleName = "损失情况简易确认书列印";
    strBizName   = "赔案号";
  }
  if( strPrintType.equals("Loss") )
  {
    //6.损失情况确认书
    strTitleName = "定损报告明细表列印";
    strBizName   = "赔案号";
  }
  if( strPrintType.equals("ComponentList") )
  {
    //7.零部件更换项目清单
    strTitleName = "零配件更换项目清单列印";
    strBizName   = "赔案号";
  }
  if( strPrintType.equals("ComponentAdd") )
  {
    //8.零部件更换项目清单附表
    strTitleName = "零配件更换项目清单附表列印";
   // strBizName   = "赔案号";
    strBizName   = "赔案号";
  }
  if( strPrintType.equals("RepairList") )
  {
    //9.修理项目清单
    strTitleName = "修理项目清单列印";
    strBizName   = "赔案号";
  }
  if( strPrintType.equals("RepairAdd") )
  {
    //10.修理项目清单附表
    strTitleName = "修理项目清单附表列印";
    strBizName   = "赔案号";
  }
  if( strPrintType.equals("PropList") )
  {
    //11.财产损失确认书
    strTitleName = "财产损失确认书列印";
    strBizName   = "赔案号";
  }
  if( strPrintType.equals("Compensate") )
  {
    //12.赔款计算书
    strTitleName = "赔款计算书列印";
    strBizName   = "赔款计算书号";    
  }
  if( strPrintType.equals("CompensateAdd") )
  {
    //13.赔款计算书附页
    strTitleName = "赔款计算书附页列印";
    strBizName   = "赔款计算书号";
  }
  if( strPrintType.equals("PayStatList") )
  {
    //14.赔款统计明细表
    strTitleName = "赔款统计明细表";
    strBizName   = "归档号";
  }
  if( strPrintType.equals("Drawnotice") )
  {
    //15.领取赔款通知书
    strTitleName = "领取赔款通知书列印";
//    strBizName   = "归档号";
    strBizName   = "赔款计算书号";
  }
  if( strPrintType.equals("Prepay") )
  {
    //16.预付赔款审批表
    strTitleName = "预付赔款审批表列印";
    strBizName   = "预赔号";
  }
  if( strPrintType.equals("Pressnotice") )
  {
    //17.註銷通知书
    strTitleName = "拒赔註銷案件通知书列印";
    strBizName   = "赔案号";
  }
  if( strPrintType.equals("PressnoticeEnd") )
  {
    //17.结案催告通知书
    strTitleName = "结案催告通知书列印";
    strBizName   = "赔案号";
  }
  if( strPrintType.equals("Endcase") )
  {
    //18.结案报告书
    strTitleName = "结案报告书列印";
          // strBizName   = "归档号";
            strBizName   = "赔案号";
  }
  if( strPrintType.equals("EndcaseAdd") )
  {
    //18.结案报告书
    strTitleName = "结案报告书附页列印";
          // strBizName   = "归档号";
            strBizName   = "赔案号";
  }
  if(strPrintType.equals("HistoryFile"))
  {
    //19.原始保单及出险前批单
    strTitleName = "原始保单及出险前批单列印";
          strBizName   = "保单号";
  }
  if(strPrintType.equals("FileOnRisk"))
  {
    //20.出险时保单
    strTitleName = "出险时保单列印";
          strBizName   = "保单号";
  }

  if(strPrintType.equals("BackcRecord"))
  {

    strTitleName = "回勘通知书列印";
          strBizName   = "报案号";
          strSerialNo = "车辆序号";
  }


  if(strPrintType.equals("BackcInform"))
  {

    strTitleName = "机动车回勘记录列印";
          strBizName   = "报案号";
          strSerialNo = "车辆序号";
  }

  //原因：增加13个不同的链接
  if (strPrintType.equals("LogoutClaimEntrol")) {
      //21.註銷赔案登记簿
      strTitleName = "註銷赔案登记簿列印";
      strBizName   = "归档号";
  }

  if (strPrintType.equals("PrepayClaimKnow")) {
      //22.预付赔款审批表
      strTitleName = "预付赔款审批表列印";
      strBizName   = "归档号";
  }

  if (strPrintType.equals("HeresyCheckSubmit")) {
      //23.代查勘委托书
      strTitleName = "代查勘委托书列印";
      strBizName   = "报案号";
  }

  if (strPrintType.equals("StopClaimEntrol")) {
      //24.已决赔案登记簿
      strTitleName = "已决赔案登记簿列印";
      strBizName   = "";
  }

  if (strPrintType.equals("NotClaimEntrol")) {
      //25.未决赔案登记簿
      strTitleName = "未决赔案登记簿列印";
      strBizName   = "";
  }

  if (strPrintType.equals("LoseCallBackEntrol")) {
      //26.损余物资回收登记簿
      strTitleName = "损余物资回收登记簿列印";
      strBizName   = "";
  }

  if (strPrintType.equals("LawArbitrogeQuestion")) {
      //27.诉讼仲裁案件审诉表
      strTitleName = "诉讼仲裁案件审诉表列印";
      strBizName   = "";
  }

  if (strPrintType.equals("CheckOrderLetter")) {
      //28.受理查勘定损复函
      strTitleName = "受理查勘定损复函列印";
      strBizName   = "";
  }

  if (strPrintType.equals("EndCaseHurry")) {
      //29.结案催款书
      strTitleName = "结案催款书列印";
      strBizName   = "";
  }

  if (strPrintType.equals("CallBackDispose")) {
      //30.回收损余物资处理单
      strTitleName = "回收损余物资处理单列印";
      strBizName   = "";
  }

  if (strPrintType.equals("SubRogationEntrol")) {
      //31.代位追偿案件登记簿
      strTitleName = "代位追偿案件登记簿列印";
      strBizName   = "";
  }

  if (strPrintType.equals("ReportRegistEntrol")) {
      //32.报案立案登记簿
      strTitleName = "报案立案登记簿列印";
      strBizName   = "报案号";
  }

  if (strPrintType.equals("BothEraEntrol")) {
      //33.“双代”登记簿
      strTitleName = "“双代”登记簿列印";
      strBizName   = "";
  }
  
  if( strPrintType.equals("check") )
  {
    //34.现场查勘记录
     strTitleName = "查勘記錄列印";
     strBizName   = "備案號碼";
  }
  if ( strPrintType.equals("CheckAdd"))
  {
  	//34.现场查勘记录附页
     strTitleName = "查勘记录列印附页";
     strBizName   = "报案号";
  }
  if( strPrintType.equals("PersonLoss") )
  {
    //34.人员伤亡费用清单
     strTitleName = "人员伤亡费用清单列印";
     strBizName   = "赔案号";
  }
  if( strPrintType.equals("DAAPilfer") )
  {
    //35.车险盗抢险查勘报告
     strTitleName = "车险盗抢险查勘报告列印";
     strBizName   = "报案号";
  }   
  if( strPrintType.equals("DAAClaimDocument") )
  {
    //36.机动车辆保险索赔材料交接单
     strTitleName = "机动车辆保险索赔材料交接单列印";
     strBizName   = "报案号";
  }  
  if( strPrintType.equals("IndemnityReceipt") )
  {
    //37.赔款收据
     strTitleName = "赔款收据列印";
     strBizName   = "赔款计算书号";
  }
    if( strPrintType.equals("IndemnityReceiptIntend"))
  {
    //37.赔款收据(预赔)
     strTitleName = "赔款收据列印";
     strBizName   = "赔款计算书号";
  }
   if( strPrintType.equals("ClaimNotice") )
  {
    //38.索赔须知
     strTitleName = "索赔须知列印";
     strBizName   = "报案号";
  }
   if( strPrintType.equals("InvesNotes") )
  {
    //39.机动车辆保险调查报告调查笔录
     strTitleName = "机动车辆保险调查报告调查笔录列印";
     strBizName   = "报案号";
  }
  if( strPrintType.equals("RobInvesNotes") )
  {
    //40.盗抢险调查笔录
     strTitleName = "盗抢险调查笔录列印";
     strBizName   = "报案号";
  }
  if( strPrintType.equals("ClaimApply") )
  {
    //41.理赔申请书 
     strTitleName = "理赔申请书列印";
     strBizName   = "立案号";
  }
  if( strPrintType.equals("PersonDamageInvesReport") )
  {
    //42.人伤调查报告 
     strTitleName = "人伤调查报告列印";
     strBizName   = "报案号";
  }
  if( strPrintType.equals("PersonDamageInvesNotes") )
  {
    //43.人伤调查笔录 
     strTitleName = "人伤调查笔录列印";
     strBizName   = "报案号";
  }
  if( strPrintType.equals("RightsTransferBook") )
  {
    //44.权益转让书 
     strTitleName = "权益转让书列印";
     strBizName   = "";
  }
  if( strPrintType.equals("RefuseCancelReport") )
  {
    //45.拒赔註銷案件报告
    strTitleName = "拒赔註銷案件报告列印";
    strBizName   = "赔案号";
  }
  if( strPrintType.equals("AccidentTotalLossCard") )
  {
    //46.事故车辆全损单
    strTitleName = "事故车辆全损单列印";
    strBizName   = "赔案号";
  }
  if( strPrintType.equals("IndemnityNotice") )
  {
    //47.赔款通知书
     strTitleName = "赔款通知书列印";
     strBizName   = "赔款计算书号";
  }
  if( strPrintType.equals("PrepareIndemnityExamineCard") )
  {
    //48.预付赔款通融赔付审批单
     strTitleName = "预付赔款通融赔付审批单列印";
     strBizName   = "赔款计算书号";
  }
  if( strPrintType.equals("AuthorizationTrustBook") )
  {
    //49.授权委托书
     strTitleName = "授权委托书列印";
     strBizName   = "";
  }
  if( strPrintType.equals("AccidentPhotoCard") )
  {
    //50.事故照片粘贴单列印
     strTitleName = "事故照片粘貼單列印";
     strBizName   = "賠案號碼";
  }
  if( strPrintType.equals("OriginNoteCard") )
  {
    //51.原始单证粘贴页
     strTitleName = "原始单证粘贴页列印";
     strBizName   = "赔案号";
  }
  
   if( strPrintType.equals("CompensateReport") )
  {
     strTitleName = "理算報告書";
     strBizName   = "賠款計算書號碼";
  }
         
  if (strPrintType.equals("CoinsType")) {
      //联共保计算书
      strTitleName = "联共保计算书列印";
      strBizName   = "赔款计算书号";
  }
  
  if (strPrintType.equals("ItemLossConfirmation")) {
      //财产损失确认书
      strTitleName = "财产损失确认书列印";
      strBizName   = "报案号";
  }
  
   if (strPrintType.equals("ClaimBack")) {
      //索赔材料回执单
      strTitleName = "索赔材料回执单列印";
      strBizName   = "报案号";
  }
    if (strPrintType.equals("ReparationReport")) {
      //索赔材料回执单
      strTitleName = "赔案报告书列印";
      strBizName   = "赔案号";
  }
  if (strPrintType.equals("AgentType")) {
      //索赔材料回执单
      strTitleName = "垫付收据列印";
      strBizName   = "报案号";
  }
	  if (strPrintType.equals("QuickCase")) {
	//快速处理赔案审批
	strTitleName = "简易赔案列印";
	strBizName   = "报案号";
	}
  if( strPrintType.equals("LossCarDetail") ){
    strTitleName = "失竊車輛應備明細表";
  }
  if( strPrintType.equals("CarClaim") ){
    strTitleName = "車險理賠申請所需文件";
  }
    if( strPrintType.equals("BzPay") ){
    	strTitleName = "強制險現金給付審核表";
    	strBizName   = "預賠號碼";
  	}
  	if( strPrintType.equals("CarCase") ){
    	strTitleName = "汽車險賠案查證記錄表";
    	strBizName   = "賠案號碼";
  	}
  	if( strPrintType.equals("ClaimStatement") ){
    	strTitleName = "汽車險理賠計算書";
    	strBizName   = "賠款計算書號碼";
  	}
	if( strPrintType.equals("ClaimApplication") ){
    	strTitleName = "汽車險理賠申請書";
    	strBizName   = "賠案號碼";
  	}
	if(strPrintType.equals("ClaimStatementReplevy") ){
    	strTitleName = "汽車險追償計算書";
    	strBizName   = "追償計算書號碼";
  	}
  	if( strPrintType.equals("CustomerInterview") ){
    strTitleName = "失竊車客戶訪談表";
  }
%>
<html>
<head>
  <title>理赔列印前输入单证号</title>
  <%-- 页面样式  --%>
  <link rel="stylesheet" type="text/css" href="/claim/css/Standard.css">
  <script src="/claim/DAA/print/js/DAAPrintBeforeEdit.js"></script>
  <script language="Javascript" src="/claim/common/js/jquery-1.7.2.min.js"></script>
  <script language='javascript'>
      function loadForm()
      {
        TitleName.innerHTML = '<%=strTitleName%>';
        fm.PrintType.value  = '<%=strPrintType%>';
        BizName.innerHTML   = '<%=strBizName%>' + '：';
        fm.BizName.value    = '<%=strBizName%>';

        if("<%=strPrintType%>"=="FileOnRisk" || "<%=strPrintType%>"=="HistoryFile")
        {
          trEndDate.style.display="";
          registNo.style.display="";
        }
        
        if ("<%=strPrintType%>"=="Loss"  || "<%=strPrintType%>"=="ComponentList"  ||"<%=strPrintType%>"=="ComponentAdd"
             ||"<%=strPrintType%>"=="RepairList" || "<%=strPrintType%>"=="RepairAdd"  )
         {
           registNo.style.display="";
         }   
        
         if ("<%=strPrintType%>"=="Compensate"  || "<%=strPrintType%>"=="CompensateAdd" 
             ||"<%=strPrintType%>"=="Endcase"  ||"<%=strPrintType%>"=="EndcaseAdd" )
         {
           registNo.style.display="";
          
         }   
     
        if ("<%=strPrintType%>"=="FileOnRisk")
        {
          fm.EndDate.value= '<%= new DateTime(DateTime.current().toString(),DateTime.YEAR_TO_DAY) %>';
          fm.EndDateHour.value="<%= DateTime.current().getHour() %>";
        }
        if ("<%=strPrintType%>"=="BackcRecord"|| "<%=strPrintType%>"=="BackcInform")
        {
         trSerialNo.style.display="";
        }
        if ("<%=strPrintType%>"=="Regist")
        {
          trPolicyNo.style.display="";
          trInsuredName.style.display="";
          trSerialNo.style.display="";
        }
      }

      function checkForm()
      {    
          if(fm.PrintType.value == 'LossCarDetail' || fm.PrintType.value == 'CarClaim' || fm.PrintType.value == 'CustomerInterview'){
          	return true;
          }
      
        var businessNo= fm.BizNo.value;
        if( fm.PrintType.value=="FileOnRisk" || fm.PrintType.value == "ComponentList" 
                ||  fm.PrintType.value == "ComponentAdd"  ||  fm.PrintType.value == "RepairList" 
                 ||  fm.PrintType.value == "RepairAdd"  || fm.PrintType.value == "Loss"  
                 ||   fm.PrintType.value == "Endcase"  || fm.PrintType.value == "EndcaseAdd"   )        //1.报案记录（代抄单）
          {
            var registNo=fm.registNo.value;
             if(registNo.length<1 && businessNo.length<1 )
            {
              alert( "備案號碼、"+fm.BizName.value+"不能全爲空!");
              return false;
            }
         
       }
       else if (fm.PrintType.value=="Compensate"  || fm.PrintType.value=="CompensateAdd") {
           var registNo=fm.registNo.value;
            var claimNo=fm.claimNo.value; 
             if(registNo.length<1 && businessNo.length<1  && claimNo.length<1) 
            {
              alert( "備案號碼、"+fm.BizName.value+"、賠案號碼不能全爲空!");
              return false;
            }
       
       
       }else if(!(fm.PrintType.value == "Regist" )){
        
         if(businessNo.length<1 )
          {
            alert(fm.BizName.value + "不能爲空!");
            return false;
          }
         
         // else if(trim(fm.BizNo.value).length!=21 && trim(fm.BizNo.value).length != 25 && trim(fm.BizNo.value).length !=22)
         // {
         // fm.BizNo.focus();
         // errorMessage(fm.BizName.value + "应为21位或25!");
         //   return false;
         // }
        }  
        else{
          if((fm.BizNo.value.length<1)&&(fm.PolicyNo.value.length<1)&&(fm.InsuredName.value.length<1)&&(fm.SerialNo.value.length<1)){
            alert("所有選項不能都爲空");
            return false;
          }
        }

        return true;
      }


      function submitForm(field)
      {
        if(checkForm()==true)
        {
          var strUrl = "";

          if( fm.PrintType.value=="Regist" )          //1.报案记录（代抄单）yf
          {
            strUrl = "${ctx}/regist/registBeforeQuery.do?editType=PRINT&RegistNo=" +fm.BizNo.value+"&PolicyNo=" + fm.PolicyNo.value+"&InsuredName=" + fm.InsuredName.value+"&LicenseNo=" + fm.SerialNo.value;
          }
          if( fm.PrintType.value=="Pilfer" )          //2.出险（盗抢）证明
          {
            strUrl = "/claim/ClaimPrint.do?printType="+fm.PrintType.value+"&ClaimNo=" + fm.BizNo.value;
          }
          if( fm.PrintType.value=="Cancelnotice" )    //3.拒赔通知书
          {
            strUrl = "/claim/ClaimPrint.do?printType="+fm.PrintType.value+"&ClaimNo=" + fm.BizNo.value;
          }
          if( fm.PrintType.value=="Canceltrans" )     //4.拒赔案件报告书
          {
            strUrl = "/claim/ClaimPrint.do?printType="+fm.PrintType.value+"&ClaimNo=" + fm.BizNo.value;
          }
          if( fm.PrintType.value=="LossSimple" )      //5.损失情况简易确认书
          {
            strUrl = "/claim/ClaimPrint.do?printType="+fm.PrintType.value+"&ClaimNo=" + fm.BizNo.value;
          }
          if( fm.PrintType.value=="Loss" )            //6.损失情况确认书
          {
            strUrl = "/claim/ClaimPrint.do?printType="+fm.PrintType.value+"&RegistNo=" + fm.registNo.value +"&ClaimNo=" + fm.BizNo.value;
          }
        
		     if (fm.PrintType.value=="Cancelcompel")
		      {
			      strUrl="/claim/ClaimPrint.do?printType="+fm.PrintType.value+"&ClaimNo=" + fm.BizNo.value;
		      }
         /* if( fm.PrintType.value=="ComponentList" )   //7.零部件更换项目清单
          {
             strUrl = "/claim/ClaimPrint.do?printType="+fm.PrintType.value+"&RegistNo=" + fm.registNo.value ; //+"&ClaimNo=" + fm.BizNo.value;
           // strUrl = "/claim/ClaimPrint.do?printType="+fm.PrintType.value+"&RegistNo=" + fm.registNo.value +"&ClaimNo=" + fm.BizNo.value;
          }
          if( fm.PrintType.value=="ComponentAdd" )    //8.零部件更换项目清单附表
          {
            strUrl = "/claim/ClaimPrint.do?printType="+fm.PrintType.value+"&RegistNo=" + fm.registNo.value +"&ClaimNo=" + fm.BizNo.value;
          }
          if( fm.PrintType.value=="RepairList" )      //9.修理项目清单
          {
             // strUrl = "/claim/ClaimPrint.do?&ClaimNo=" + fm.BizNo.value;
             strUrl = "/claim/ClaimPrint.do?printType="+fm.PrintType.value+"&RegistNo=" + fm.registNo.value +"&ClaimNo=" + fm.BizNo.value;
          }
          if( fm.PrintType.value=="RepairAdd" )       //10.修理项目清单附表
          {
            strUrl = "/claim/ClaimPrint.do?printType="+fm.PrintType.value+"&RegistNo=" + fm.registNo.value +"&ClaimNo=" + fm.BizNo.value;
          }
          */
          
          if( fm.PrintType.value=="PropList" )        //11.财产损失确认书
          {
            strUrl = "/claim/ClaimPrint.do?printType="+fm.PrintType.value+"&ClaimNo=" + fm.BizNo.value;
          }
         /* if( fm.PrintType.value=="Compensate" )      //12.赔款计算书
          {
           strUrl = "/claim/ClaimPrint.do?printType="+fm.PrintType.value+"&RegistNo=" + fm.registNo.value +"&CompensateNo=" + fm.BizNo.value;
          }
          if( fm.PrintType.value=="CompensateAdd" )   //13.赔款计算书附页
          {
            strUrl = "/claim/ClaimPrint.do?printType="+fm.PrintType.value+"&RegistNo=" + fm.registNo.value +"&CompensateNo=" + fm.BizNo.value;
          }
          */
          if( fm.PrintType.value=="PayStatList" )     //14.赔款统计明细表
          {
            strUrl = "/claim/ClaimPrint.do?printType="+fm.PrintType.value+"&CaseNo=" + fm.BizNo.value;
          }
          if( fm.PrintType.value=="Drawnotice" )      //15.领取赔款通知书
          {
            strUrl = "/claim/ClaimPrint.do?printType="+fm.PrintType.value+"&CompensateNo=" + fm.BizNo.value;
          }
          if( fm.PrintType.value=="Prepay" )          //16.预付赔款审批表yf
          {
            strUrl = "/claim/claimPrint.do?printType="+fm.PrintType.value+"&PreCompensateNo=" + fm.BizNo.value+"&actionType="+fm.PrintType.value;;
          }
          if( fm.PrintType.value=="Pressnotice" )     //17.註銷通知书
          {
            strUrl = "/claim/ClaimPrint.do?printType="+fm.PrintType.value+"&ClaimNo=" + fm.BizNo.value;
          }
          if( fm.PrintType.value=="PressnoticeEnd" )     //17.结案催告通知书
          {
            strUrl = "/claim/ClaimPrint.do?printType="+fm.PrintType.value+"&ClaimNo=" + fm.BizNo.value;
          }
          if( fm.PrintType.value=="Endcase" )         //18.结案报告书
          {
            strUrl = "/claim/ClaimPrint.do?printType="+fm.PrintType.value+"&RegistNo=" + fm.registNo.value +"&ClaimNo=" + fm.BizNo.value;
          }
          if( fm.PrintType.value=="EndcaseAdd" )         //18.结案报告书附页
          {
            strUrl = "/claim/ClaimPrint.do?printType="+fm.PrintType.value+"&RegistNo=" + fm.registNo.value +"&ClaimNo=" + fm.BizNo.value;
          }
          if( fm.PrintType.value=="HistoryFile" )     //19.原始保单及出险前批单
          {
            strUrl = "/claim/ClaimPrint.do?printType="+fm.PrintType.value+"&PolicyNo=" + fm.BizNo.value;
          }
          if( fm.PrintType.value=="FileOnRisk" )      //20.出险时保单
          {
            strUrl = "/claim/ClaimPrint.do?printType="+fm.PrintType.value+"&RegistNo=" + fm.registNo.value +"&PolicyNo=" + fm.BizNo.value + "&EndDate=" + fm.EndDate.value +" &EndDateHour=" + fm.EndDateHour.value;

          }
          if( fm.PrintType.value=="BackcInform" )      //21.修复验车通知书
          {
            strUrl = "/claim/ClaimPrint.do?printType="+fm.PrintType.value+"&RegistNo=" + fm.BizNo.value +"&SerialNo="+fm.SerialNo.value;

          }
          if( fm.PrintType.value=="BackcRecord" )      //22.机动车辆修复验车记录
          {
            strUrl = "/claim/ClaimPrint.do?printType="+fm.PrintType.value+"&RegistNo=" + fm.BizNo.value +"&SerialNo="+fm.SerialNo.value;

          }

          //原因：增加13个链接
          if (fm.PrintType.value == "LogoutClaimEntrol")  //23.註銷赔案登记簿
          {
              strUrl = "/claim/ClaimPrint.do?printType="+fm.PrintType.value+"&RegistNo=" + fm.BizNo.value +"&SerialNo="+fm.SerialNo.value;
          }

          if (fm.PrintType.value == "PrepayClaimKnow")    //24.预付赔款审批表
          {
              strUrl = "/claim/ClaimPrint.do?printType="+fm.PrintType.value+"&RegistNo=" + fm.BizNo.value +"&SerialNo="+fm.SerialNo.value;
          }

          if (fm.PrintType.value == "HeresyCheckSubmit")   //25.代查勘委托书
          {
              strUrl = "/claim/ClaimPrint.do?printType="+fm.PrintType.value+"&RegistNo=" + fm.BizNo.value +"&SerialNo="+fm.SerialNo.value;
          }

          if (fm.PrintType.value == "StopClaimEntrol")     //26.已决赔案登记簿
          {
              strUrl = "/claim/ClaimPrint.do?printType="+fm.PrintType.value+"&RegistNo=" + fm.BizNo.value +"&SerialNo="+fm.SerialNo.value;
          }

          if (fm.PrintType.value == "NotClaimEntrol")      //27.未决赔案登记簿
          {
              strUrl = "/claim/ClaimPrint.do?printType="+fm.PrintType.value+"&RegistNo=" + fm.BizNo.value +"&SerialNo="+fm.SerialNo.value;
          }

          if (fm.PrintType.value == "LoseCallbackEntrol")   //28.损余物资回收登记簿
          {
              strUrl = "/claim/ClaimPrint.do?printType="+fm.PrintType.value+"&RegistNo=" + fm.BizNo.value +"&SerialNo="+fm.SerialNo.value;
          }

          if (fm.PrintType.value == "LawArbitrogeQuestion") //29.诉讼仲裁案件审诉表
          {
              strUrl = "/claim/ClaimPrint.do?printType="+fm.PrintType.value+"&RegistNo=" + fm.BizNo.value +"&SerialNo="+fm.SerialNo.value;
          }

          if (fm.PrintType.value == "CheckOrderLetter")     //30.受理查勘定损复函
          {
              strUrl = "/claim/ClaimPrint.do?printType="+fm.PrintType.value+"&RegistNo=" + fm.BizNo.value +"&SerialNo="+fm.SerialNo.value;
          }

          if (fm.PrintType.value == "EndCaseHurry")         //31.结案催告书
          {
              strUrl = "/claim/ClaimPrint.do?printType="+fm.PrintType.value+"&RegistNo=" + fm.BizNo.value +"&SerialNo="+fm.SerialNo.value;
          }

          if (fm.PrintType.value == "CallBackLoseDispose")   //32.回收损余物资处理单
          {
              strUrl = "/claim/ClaimPrint.do?printType="+fm.PrintType.value+"&RegistNo=" + fm.BizNo.value +"&SerialNo="+fm.SerialNo.value;
          }

          if (fm.PrintType.value == "SubRogationEntrol")     //33.代位追偿案件登记簿
          {
              strUrl = "/claim/ClaimPrint.do?printType="+fm.PrintType.value+"&RegistNo=" + fm.BizNo.value +"&SerialNo="+fm.SerialNo.value;
          }

          if (fm.PrintType.value == "ReportRegistEntrol")     //34.报案立案登记簿
          {
              strUrl = "/claim/ClaimPrint.do?printType="+fm.PrintType.value+"&RegistNo=" + fm.BizNo.value +"&SerialNo="+fm.SerialNo.value;
          }

          if (fm.PrintType.value == "BothEraEntrol")          //35.“双代”登记簿
          {
              strUrl = "/claim/ClaimPrint.do?printType="+fm.PrintType.value+"&RegistNo=" + fm.BizNo.value +"&SerialNo="+fm.SerialNo.value;
          }
          if (fm.PrintType.value == "check")          //36.现场查勘记录2013-4-25 ln
          {   
              strUrl = "${ctx}/print/claimPrint.do?printType="+fm.PrintType.value+"&RegistNo=" + fm.BizNo.value+"&actionType="+fm.PrintType.value;
             
          }
          if (fm.PrintType.value == "LossCarDetail")          //36.失竊車輛應備明細表2013-4-27 ln
          {   
              strUrl = "${ctx}/print/claimPrint.do?printType="+fm.PrintType.value+"&actionType="+fm.PrintType.value;
             
          }
            if (fm.PrintType.value == "CarClaim")          //36.車險理賠申請所需文件2013-4-27 ln
          {   
              strUrl = "${ctx}/print/claimPrint.do?printType="+fm.PrintType.value+"&actionType="+fm.PrintType.value;
             
          }
           if (fm.PrintType.value == "BzPay")          //36.強制險現金給付審核表2013-4-27 ln
          {   
              strUrl = "${ctx}/print/claimPrint.do?printType="+fm.PrintType.value+"&PrepayNo=" + fm.BizNo.value+"&actionType="+fm.PrintType.value;
          }
            if (fm.PrintType.value == "CarCase")          //36.汽 車 險 賠 案 查 證 記 錄 表2013-4-27 ln
          {   
              strUrl = "${ctx}/print/claimPrint.do?printType="+fm.PrintType.value+"&ClaimNo=" + fm.BizNo.value+"&actionType="+fm.PrintType.value;
             
          }
          
              if (fm.PrintType.value == "ClaimStatement")          //36.汽車險理賠計算書2013-4-27 ln
          {   
              strUrl = "${ctx}/print/claimPrint.do?printType="+fm.PrintType.value+"&CompensateNo=" + fm.BizNo.value+"&actionType="+fm.PrintType.value;
             
          }
              if (fm.PrintType.value == "ClaimStatementReplevy"){//汽車險追償計算書
                  strUrl = "${ctx}/print/claimPrint.do?printType="+fm.PrintType.value+"&CompensateNo=" + fm.BizNo.value+"&actionType="+fm.PrintType.value;
              }
              if (fm.PrintType.value == "ClaimApplication")          //36.汽車險理賠申請書2013-4-27 ln
          {   
              strUrl = "${ctx}/print/claimPrint.do?printType="+fm.PrintType.value+"&claimNo=" + fm.BizNo.value+"&actionType="+fm.PrintType.value;
             
          }
              if (fm.PrintType.value == "CustomerInterview")          //失竊車客戶訪談表2013-5-14 yf
          {   
              strUrl = "${ctx}/print/claimPrint.do?printType="+fm.PrintType.value+"&actionType="+fm.PrintType.value;
             
          }
          if (fm.PrintType.value == "CheckAdd")      //plus36.现场查勘记录附表
          {
          	  strUrl = "/claim/ClaimPrint.do?printType="+fm.PrintType.value+"&RegistNo=" + fm.BizNo.value+"&actionType="+fm.PrintType.value;
          }
          if (fm.PrintType.value == "PerssonLoss")          //37.人员伤亡费用清单
          {
              strUrl = "/claim/ClaimPrint.do?printType="+fm.PrintType.value+"&ClaimNo=" + fm.BizNo.value;
          }
          if (fm.PrintType.value == "DAAPilfer")          //1e35.车险盗抢险查勘报告
          {
              strUrl = "/claim/ClaimPrint.do?printType="+fm.PrintType.value+"&RegistNo=" + fm.BizNo.value;
          }           
          if (fm.PrintType.value == "DAAClaimDocument")  //1e36.机动车辆保险索赔材料交接单 
          {
              strUrl = "/claim/ClaimPrint.do?printType="+fm.PrintType.value+"&RegistNo=" + fm.BizNo.value;
          }
          
           if( fm.PrintType.value=="CoinsType")//联共保计算书
          {
              strUrl = "/claim/ClaimPrint.do?printType="+fm.PrintType.value+"&CompensateNo=" + fm.BizNo.value;
          }
		  if (fm.PrintType.value == "IndemnityReceipt")  //1e37.赔款收据 
          {
              var coinFlagList = document.getElementsByName("coinFlag");
              var index=3;
              for(var i=0;i<coinFlagList.length;i++)
              {
                if(coinFlagList[i].checked==true)
                {
                  index=i;
                  break;
                }
              }
	            strUrl = "/claim/ClaimPrint.do?printType="+fm.PrintType.value+"&CompensateNo=" + fm.BizNo.value+"&coinFlag="+index;
              
          }
		  if (fm.PrintType.value == "IndemnityReceiptIntend")  //1e37.赔款收据 
          {
              var coinFlagList = document.getElementsByName("coinFlag");
              var index=3;
              for(var i=0;i<coinFlagList.length;i++)
              {
                if(coinFlagList[i].checked==true)
                {
                  index=i;
                  break;
                }
              }
	            strUrl = "/claim/ClaimPrint.do?printType="+fm.PrintType.value+"&CompensateNo=" + fm.BizNo.value+"&coinFlag="+index;
              
          }
		  if (fm.PrintType.value == "ClaimNotice")  //1e38.索赔须知 yf
          {
              strUrl = "${ctx}/print/claimPrint.do?printType="+fm.PrintType.value+"&RegistNo=" + fm.BizNo.value+"&actionType="+fm.PrintType.value;
          }
		  if (fm.PrintType.value == "InvesNotes")  //1e39.调查笔录 
          {
              strUrl = "/claim/ClaimPrint.do?printType="+fm.PrintType.value+"&RegistNo=" + fm.BizNo.value;
          }
		  if (fm.PrintType.value == "RobInvesNotes")  //1e40.盗抢险调查笔录
          {
              strUrl = "/claim/ClaimPrint.do?printType="+fm.PrintType.value+"&RegistNo=" + fm.BizNo.value;
          }
		  if (fm.PrintType.value == "ClaimApply")  //1e41.索赔申请书
          {
              strUrl = "/claim/ClaimPrint.do?printType="+fm.PrintType.value+"&ClaimNO=" + fm.BizNo.value;
          }
		  if (fm.PrintType.value == "PersonDamageInvesReport")  //1e42.人伤调查报告
          {
              strUrl = "/claim/ClaimPrint.do?printType="+fm.PrintType.value+"&RegistNo=" + fm.BizNo.value;
          }
		  if (fm.PrintType.value == "PersonDamageInvesNotes")  //1e43.人伤调查笔录
          {
              strUrl = "/claim/ClaimPrint.do?printType="+fm.PrintType.value+"&RegistNo=" + fm.BizNo.value;
          }
		  if (fm.PrintType.value == "RightsTransferBook")  //1e44.权益转让书
          {
              strUrl = "/claim/ClaimPrint.do?printType="+fm.PrintType.value+"&RegistNo=" + fm.BizNo.value;
          }
		  if (fm.PrintType.value == "RefuseCancelReport")  //1e45.拒赔註銷案件报告
          {
              strUrl = "/claim/ClaimPrint.do?printType="+fm.PrintType.value+"&ClaimNo=" + fm.BizNo.value;
          }
          if (fm.PrintType.value == "AccidentTotalLossCard")  //1e46.事故车辆全损单
          {
              strUrl = "/claim/ClaimPrint.do?printType="+fm.PrintType.value+"&ClaimNo=" + fm.BizNo.value;
          }
		  if (fm.PrintType.value == "IndemnityNotice")  //1e47.赔款通知书 
          {
              strUrl = "/claim/ClaimPrint.do?printType="+fm.PrintType.value+"&CompensateNo=" + fm.BizNo.value;
          }
		  if (fm.PrintType.value == "PrepareIndemnityExamineCard")  //1e48.预付赔款通融赔付审批单 
          {
              strUrl = "/claim/ClaimPrint.do?printType="+fm.PrintType.value+"&CompensateNo=" + fm.BizNo.value;
          }
		  if (fm.PrintType.value == "AuthorizationTrustBook")  //1e49.授权委托书 
          {
              strUrl = "/claim/ClaimPrint.do?printType="+fm.PrintType.value+"&RegistNo=" + fm.BizNo.value;
          }
		  if (fm.PrintType.value == "AccidentPhotoCard")  //1e50.事故照片粘贴单 yf
          {
              strUrl = "${ctx}/print/claimPrint.do?printType="+fm.PrintType.value+"&ClaimNo=" + fm.BizNo.value+"&actionType="+fm.PrintType.value;
          }
		  if (fm.PrintType.value == "OriginNoteCard")  //1e51.原始单证粘贴页 
          {
              strUrl = "/claim/ClaimPrint.do?printType="+fm.PrintType.value+"&ClaimNo=" + fm.BizNo.value;
          }
      	  if (fm.PrintType.value == "CompensateReport")          //52.理算报告书yf
          {
              strUrl = "${ctx}/print/claimPrint.do?printType="+fm.PrintType.value+"&CompensateNo=" + fm.BizNo.value+"&actionType="+fm.PrintType.value;
          } 
          if (fm.PrintType.value == "ItemLossConfirmation")          //53.索赔材料回执单
          {
              strUrl = "/claim/ClaimPrint.do?printType="+fm.PrintType.value+"&RegistNo=" + fm.BizNo.value;
          }   
          if (fm.PrintType.value == "ClaimBack")          //54.财产损失确认书
          {
              strUrl = "/claim/ClaimPrint.do?printType="+fm.PrintType.value+"&RegistNo=" + fm.BizNo.value;
          }  
          if (fm.PrintType.value == "ReparationReport")          //55.赔案报告书
          {
              strUrl = "/claim/ClaimPrint.do?printType="+fm.PrintType.value+"&ClaimNo=" + fm.BizNo.value;
          } 
          if (fm.PrintType.value == "AgentType")          //55.垫付收据
          {
              strUrl = "/claim/ClaimPrint.do?printType="+fm.PrintType.value+"&RegistNo=" + fm.BizNo.value;
          } 

		  //简易赔案  快速处理赔案审批表
		  if (fm.PrintType.value == "QuickCase")
		  {
              strUrl ="/claim/ClaimPrint.do?printType="+fm.PrintType.value+"&RegistNo=" + fm.BizNo.value;
          }
		  
          
           if( fm.PrintType.value=="RepairList"  || fm.PrintType.value=="RepairAdd" ||fm.PrintType.value=="ComponentList" ||fm.PrintType.value=="Loss"
              ||fm.PrintType.value=="ComponentAdd" ) {
             //fm.action="/claim/ClaimPrint.do?printType="+fm.PrintType.value+"&ClaimNo=" + fm.BizNo.value;
             fm.action="/claim/ClaimPrint.do?printType="+fm.PrintType.value+"&RegistNo=" + fm.registNo.value+"&ClaimNo=" + fm.BizNo.value;
         	 // reason:当按下某一按钮时请将这个按钮变灰，否则用户可能多按引发错误
         	 field.disabled = true;
             fm.submit();
           }
           else if( fm.PrintType.value=="Compensate" || fm.PrintType.value=="CompensateAdd"  ) {
        	   // reason:当按下某一按钮时请将这个按钮变灰，否则用户可能多按引发错误
        	   field.disabled = true;
               var newWindow = window.open("/claim/compensateQuery.do?PrintType="+fm.PrintType.value+"&CompensateNo=" + fm.BizNo.value+"&RegistNo=" + fm.registNo.value+"&ClaimNo=" + fm.claimNo.value +"&editType=PRINT","NewWindow","width=600,height=500,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0");
             //fm.action="/claim/compensateQuery.do?PrintType="+fm.PrintType.value+"&ClaimNo=" + fm.claimNo.value +"&editType=PRINT";
             //fm.submit();
           }
           else{     //9.修理项目清单
           var printType = fm.PrintType.value;
           if(printType=="CarClaim" || printType=="LossCarDetail" || printType=="CustomerInterview" || printType=="Regist"){
        		// reason:当按下某一按钮时请将这个按钮变灰，否则用户可能多按引发错误
        		field.disabled = true;
           		printWindow(strUrl,"列印1");
           }else{
           var bizNo = fm.BizNo.value;
                $.ajax({
				type:"get",
 				url:"${ctx}/print/claimPrintCheck.do",
 				cache:false,
 				dataType:"text",
 				async:false,
				data:"claimNo="+bizNo+"&compensateNo="+bizNo+"&registNo="+bizNo+"&prepayNo="+bizNo+"&printType="+printType,
					success:function(data){
						if (data.length>0){
							// reason:当按下某一按钮时请将这个按钮变灰，否则用户可能多按引发错误
							field.disabled = true;
               				printWindow(strUrl,"列印1");
		    			}else{
		    				if(printType=="AccidentPhotoCard" || printType=="CarCase"){
		    					alert("賠案號碼不存在！");
		    				}else if(printType=="ClaimApplication"){
		    					alert("賠案號碼不存在！");
			    			}else if( printType=="ClaimStatement" || printType=="CompensateReport"){
		    					alert("賠款計算書號碼不存在！");
		    				}else if(printType=="ClaimStatementReplevy"){
		    					alert("追償計算書號碼不存在！");
		    				} else if(printType=="check"){
		    					alert("備案號碼不存在！");
		    				}
		    				else if(printType=="BzPay"){
		    					alert("預賠號碼不存在！");
		    				}
		    				return false;
		    			}
					}
				});
           }
          }
        }
      }
      //显示列印窗口
      function printWindow(strURL,strWindowName)
      { 
        var pageWidth=screen.availWidth-10;
        var pageHeight=screen.availHeight-30;
        if (pageWidth<100 )
          pageWidth = 100;
        if (pageHeight<100 )
          pageHeight = 100;
        var newWindow = window.open(strURL,strWindowName,'width='+pageWidth+',height='+pageHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1.resizable=1,status=0');
        try{
        	newWindow.focus();
        }catch(e){
        }
        return newWindow;
      }
    </script>
<body class="interface" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="loadForm();"">
<form name="fm" method="post"   onsubmit="return validateForm(this);">

  <table border="0" align="center" cellpadding="0" cellspacing="0" class="common">
    <tr>
      <td width="100%" height="26" valign="bottom">
        <table width="100%" height="19" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="100%" class="formtitle" id="TitleName"></td>
          </tr>
        </table>
      </td>
      <td valign="bottom"><font color="#666666">&nbsp;</font></td>
    </tr>
  </table>
    <table width="90%" border="0" align="center" cellpadding="4" cellspacing="1" bgcolor="#2D8EE1" class="common">
        <tr  style="display:none" id="registNo">
        <td class='common' style="width:20%" >備案號碼：</td>
        <td class='input'> 
            <input class="common" type='text' name='registNo' maxlength='25' style="width:320px">
        </td>
      </tr>
      <tr style="<%=styleStr %>">
        <td class='common' style="width:20%" id="BizName"></td>
        <td class='input'>
            <input type='hidden' name='BizName'>
            <input class="common" type='text' name='BizNo' maxlength='25' style="width:320px">
        <% if( !strPrintType.equals("Regist") )
          {%>
        <img src="/claim/images/bgMarkMustInput.jpg">
        <%} %>
        </td>
      </tr>
          <% if( strPrintType.equals("IndemnityReceipt") )
          {%>
          <tr>
            <td colspan="2">
            <font color="red">共保时收据金额打我方分摊的金额还是总赔款金额(本标志只对共保案件起效，非共保案件可不选择)</font><br>
            我方分摊金额<input type="radio" value="0" name="coinFlag"/>
            总赔款金额<input type="radio" value="1" name="coinFlag"/>
            </td>
          </tr>
          <%} %>
          <% if( strPrintType.equals("IndemnityReceiptIntend") )
          {%>
          <tr>
            <td colspan="2">
            <font color="red">共保时收据金额打我方分摊的金额还是总赔款金额(本标志只对共保案件起效，非共保案件可不选择)</font><br>
            我方分摊金额<input type="radio" value="0" name="coinFlag"/>
            总赔款金额<input type="radio" value="1" name="coinFlag"/>
            </td>
          </tr>
          <%} %>
      <%if(strPrintType.equals("Compensate") ||strPrintType.equals("CompensateAdd") ){%>
      <tr>
        <td class='common' style="width:20%">賠案號碼：</td>
        <td class='input'>            
            <input class="common" type='text' name='claimNo' maxlength='25' style="width:320px">
        </td>
      </tr>
    <%}%>
      <tr style="display:none" id="trPolicyNo">
        <td class="common" style="width:20%">保單號碼：</td>
        <td class="input">
          <input class="common" type="text" name="PolicyNo" maxlength="22" style="width:320px">
        </td>
      </tr>
      <tr style="display:none" id="trInsuredName">
        <td class="common" style="width:20%">被保險人：</td>
        <td class="input">
          <!-- mantis： CLM0017，處理人員：Sam，需求單編號：CLM0017，原住名姓名調整作業_車 -->
          <input class="common" type="text" name="InsuredName" maxlength="100" style="width:320px">
        </td>
      </tr>
      <tr  style="display:none" id="trSerialNo">
        <td class='common' style="width:20%">牌照號碼：</td>
        <td class='input'>
           <input class="common" type='text' name='SerialNo' maxlength='22' style="width:320px">
        </td>
      </tr>
      <tr  style="display:none" id="trEndDate">
        <td class='common' style="width:20%">截止日期：</td>
        <td class='input'>
            <input class="common" type='text' name='EndDate' style="width:80" maxlength="10">日
            <input class="common" type='text' name='EndDateHour' style="width:20">时
        </td>
      </tr>
      <tr>
        <td class="common" align="center" style="width:100%"  colspan="2">
         <!-- LN2013-4-27 -->
        <%if(strPrintType.equals("LossCarDetail") || strPrintType.equals("CarClaim") || strPrintType.equals("CustomerInterview")){ %>
            <input type=button value="列 印" class='button' onClick="submitForm(this);">
        <%}else{ %>
         	<input type=button value="下一步" class='button' onClick="submitForm(this);">
        <%} %> 
         <!-- LN2013-4-27 end-->
         <input type='hidden' name="PrintType">
        </td>
      </tr>
    </table>
  </form>
</body>
</html>



