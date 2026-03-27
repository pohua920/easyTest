<%@page import="com.sun.xml.bind.v2.schemagen.xmlschema.Import"%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/i18njs.jsp"%>
<%@ page import="com.sinosoft.sysframework.reference.AppConfig"%>
<%@ page import="java.util.Collection,java.util.ArrayList"%>
<%@ page import="com.sinosoft.common.schema.model.PrpTmain"%>
<%@ taglib prefix="rc" uri="http://util.one.sinosoft.com/RCDate"%>
<%@ include file="CommonStyle.html"%>
<%@page import="com.sinosoft.undwrt.pub.InternationalizationUtil" %>

<html>
  <head>
   <title><s:text name="undwrt.pages.undwrtDeal.CommonDangerReinsUnit"/></title>
    <!-- 公用函数 -->
    <script src="/undwrt/common/js/CommonTaskDeal.js"></script>
    <script src="/undwrt/common/js/Common_undwrt.js"></script>
    <jsp:include page="/common/meta_css.jsp" />

	<script type="text/javascript">
    
 function showValue()
 {    
	 if(isNaN(fm.CheckBoxSpecialFacShareHidden.length))
	 {
   	 	if (fm.CheckBoxSpecialFacShareHidden.value=="1") 
    	{
     		 fm.CheckBoxSpecialFacShare.checked=true;   
  		}
    	if (fm.CheckBoxFacShareHidden.value=="1") 
   		{
      		fm.CheckBoxFacShare.checked=true;   
  		}
	}
	else
	{
		for(var j=0;j<fm.CheckBoxSpecialFacShareHidden.length;j++)
		{
	   	 	if (fm.CheckBoxSpecialFacShareHidden[j].value=="1") 
	    	{
	     		 fm.CheckBoxSpecialFacShare[j].checked=true;   
	  		}
	    	if (fm.CheckBoxFacShareHidden[j].value=="1") 
	   		{
	      		fm.CheckBoxFacShare[j].checked=true;   
	  		}
		}
	}
 
    //增加接收人信息中标志
     var count = fm.ReinsName.length;   
     
    	for(i=1;i<count;i++)
        {
          if (fm.feoReinsReceiveReinsType[i].value == '0')
           { 
               fm.reinsTypeCheckBox[i].checked = true;
           }
           if (fm.feoReinsReceiveCurrencyFlag[i].value == '0')
           { 
              fm.currencyFlagCheckBox[i].checked = true;
           }
           if (fm.feoReinsReceiveFacFlag[i].value == '1')
           {
               fm.facFlagCheckBox[i].checked = true;
           }
        }
   //控制增加接受人
   if(fm.feoEnquiryDtoVerifyFlag.value=="1")
   {
      fm.addReinsReceive.disabled=true;  
      for(j=1;j<fm.deleteReinsReceiveButton.length;j++)
      {
      fm.deleteReinsReceiveButton[j].disabled=true;  
      }
   }      
}   
    
    
 function changeValue(field){
    var index= getElementIndex(field);
    var temp = fm.elements[index+1].value;
    if (temp=='0') 
    {
      fm.elements[index+1].value=1;   
    }
    else
    {
      fm.elements[index+1].value=0;
    }
  }
  
  function checkReins()

  {

  	  var i = 0;

  	  var j = 0;

  	  var k = 0;

  	  for (i=1;i<fm.ReinsCode.length;i++)

  	  {

  	  	  for (j=1;j<fm.ReinsCode.length;j++)

  	  	  {

  	  	  	  if (i != j && fm.ReinsCode[i].value == fm.ReinsCode[j].value)

  	  	  	  {

  	  	  	  	  if (fm.FinalReinsCode[i].value == fm.FinalReinsCode[j].value)

  	  	  	  	  {

  	  	  	  	  	  alert("<s:text name='undwrt.pages.undwrtDeal.accepter'/>"+fm.ReinsCode[i].value+"<s:text name='undwrt.pages.undwrtDeal.finalAccepter'/>"+fm.FinalReinsCode[j].value+"<s:text name='undwrt.pages.undwrtDeal.messagesRepeat'/>");

  	  	  	  	  	  return false;

  	  	  	  	  }

  	  	  	  }

  	  	  }

  	  }

  	  return true;

  }
  
     function saveReinsReceiveInfo()
     { 
    	if(isNaN(fm.CheckBoxSpecialFacShare.length))
    	{
    		 if (fm.CheckBoxSpecialFacShare.checked==true) 
       		 {
         		 fm.CheckBoxSpecialFacShareHidden.value="1";   
       		 }
       		 else
       		 {
         		fm.CheckBoxSpecialFacShareHidden.value="0";   
        	 }
          if (fm.CheckBoxFacShare.checked==true) 
       		 {
          		fm.CheckBoxFacShareHidden.value="1";   
       		 }
          else
        	{
        		fm.CheckBoxFacShareHidden.value="0";   
        	}
   		 }
    	else
    	{
    		for (var j=0;j<fm.CheckBoxSpecialFacShare.length;j++)
    		{
    			 if (fm.CheckBoxSpecialFacShare[j].checked==true) 
           		 {
             		 fm.CheckBoxSpecialFacShareHidden[j].value="1";   
           		 }
           		 else
           		 {
             		fm.CheckBoxSpecialFacShareHidden[j].value="0";   
            	 }
              if (fm.CheckBoxFacShare[j].checked==true) 
           		 {
              		fm.CheckBoxFacShareHidden[j].value="1";   
           		 }
              else
            	{
            		fm.CheckBoxFacShareHidden[j].value="0";   
            	}
    		}
    	}
     //add by dongyanqi接受人信息中的标志赋值
	    var count = fm.ReinsName.length;     
   
        	for(i=1;i<count;i++)
        {        
           if (fm.reinsTypeCheckBox[i].checked == true)
           {  
               fm.feoReinsReceiveReinsType[i].value = "0";
           }else
           {
           		fm.feoReinsReceiveReinsType[i].value = "1";
           }
           
           if (fm.currencyFlagCheckBox[i].checked == true)
           {  
               fm.feoReinsReceiveCurrencyFlag[i].value = "0";
           }else
           {
           		fm.feoReinsReceiveCurrencyFlag[i].value = "1";
           }
           if (fm.facFlagCheckBox[i].checked == true)
           {
               fm.feoReinsReceiveFacFlag[i].value = "1";
           }else
           	{
           		fm.feoReinsReceiveFacFlag[i].value = "0";
           		}
        }
       //保存设置为未提交再保
        fm.feoEnquiryDtoVerifyFlag.value="0";      
        fm.method="post";
        fm.action="/undwrt/saveReins/enquiryReins.do?type=saveReinsReceive";
        fm.submit();      
     }
     
     function commitReins()
     { 
      
       // if ((fm.EnquiryNo.value == "") )
         //{
            // window.alert("<s:text name='undwrt.pages.undwrtDeal.CommonDangerReinsUnit.alert1'/>");
             //return false;
         //}else          
        // {    
            // if(!confirm("<s:text name='undwrt.pages.undwrtDeal.CommonDangerReinsUnit.confirm'/>")) 
            // {
            // return false;
            // }
            // else
            // {
            //fm.method="post";
             //fm.action="/undwrt/saveReins/enquiryReins.do?type=verifyEnquiry";
             //fm.submit();
             //}
       // }
        fm.method="post";
        fm.action="/undwrt/saveReins/enquiryReins.do?type=verifyEnquiry";
        fm.submit();
     }
     
     function checkPass()
     {
         var count = fm.ReinsCode.length;
         var specialFacShare = fm.SpecialFacShare.value;
         
         var facShare = fm.FacShare.value;
         var specialFacShareSum = 0;
         var facShareSum = 0;  
                  
         <%--标志与比例一致性的校验--%>
         if ((fm.CheckBoxSpecialFacShare.checked == true) && (fm.SpecialFacShare.value == 0))
         {
             window.alert("<s:text name='undwrt.pages.undwrtDeal.CommonDangerReinsUnit.alert2'/>"); 
             return false;
         }
         if ((fm.CheckBoxSpecialFacShare.checked == false) && (fm.SpecialFacShare.value != 0))
         {
             window.alert("<s:text name='undwrt.pages.undwrtDeal.CommonDangerReinsUnit.alert3'/>");
             return false;
         }
         if ((fm.CheckBoxFacShare.checked == true) && (fm.FacShare.value == 0))
         {
             window.alert("<s:text name='undwrt.pages.undwrtDeal.CommonDangerReinsUnit.alert2'/>");
             return false;
         }
         if ((fm.CheckBoxFacShare.checked == false) && (fm.FacShare.value != 0))
         {
             window.alert("<s:text name='undwrt.pages.undwrtDeal.CommonDangerReinsUnit.alert3'/>");
             return false;
         }
         
         for (i=1;i<count;i++)
         {   
             if ((fm.ReinsCode[i].value=="") || (fm.FinalReinsCode[i].value==""))
             {
                 window.alert("<s:text name='undwrt.pages.undwrtDeal.CommonDangerReinsUnit.alert4'/>");
                 return false;
             }
             <%--如果是经纪人，reinsCode与finalReinsCode不得相同--%>
             
             <%--累计特约接受人与普通接受人各自的接受份额，用于后续的比例校验--%>
             
             if (fm.facFlagCheckBox[i].checked == true)
             {
                specialFacShareSum = specialFacShareSum + parseFloat(fm.ShareRate[i].value);    
                specialFacShareSum = round(specialFacShareSum,6);          
                 
             }else 
             {
                  facShareSum = facShareSum + parseFloat(fm.ShareRate[i].value);
                   facShareSum = round(facShareSum,6);
             }
         }
         <%--window.alert("特约比例"+specialFacShare+";特约合计"+specialFacShareSum+";普通比例"+facShare+";普通合计"+facShareSum);--%>
         if (specialFacShare != specialFacShareSum)
         {
             window.alert("<s:text name='undwrt.pages.undwrtDeal.CommonDangerReinsUnit.alert5'/>" );
             return false;
         }
         if (facShare != facShareSum)
         {
             window.alert("<s:text name='undwrt.pages.undwrtDeal.CommonDangerReinsUnit.alert6'/>");
             return false;
         }
         return true;
     }
     
     function showSpan(field,strFlag)

     {
       var strSpanName = "span_ItemKind"+strFlag+"_Context";
       var index = getElementOrder(field)-1;
       var strQueryString = "";
       if(fm.assessLevel[index].value !=""){
          strQueryString = strQueryString + "<s:text name='undwrt.pages.undwrtDeal.standardPUL'/>(Standard & Poor’s)--"+fm.assessLevel[index].value+"\n";
       }
       if(fm.assessLevel3[index].value != ""){
          strQueryString = strQueryString + "<s:text name='undwrt.pages.undwrtDeal.Moody'/>(Moody's Investors Service)--" +fm.assessLevel3[index].value+"\n";
       }
       if(fm.assessLevel2[index].value != ""){
          strQueryString = strQueryString + "A.M.Best--" +fm.assessLevel2[index].value+"\n";
       }
       if(fm.assessLevel4[index].value != ""){
          strQueryString = strQueryString + "Fitch--" +fm.assessLevel4[index].value+"\n";
       }
       if(fm.assessLevel5[index].value != ""){
          strQueryString = strQueryString + "<s:text name='undwrt.pages.undwrtDeal.ChinaCreditRating'/>--" +fm.assessLevel5[index].value+"\n";
       }
       var span = eval(strSpanName + "(" + index + ")");
       var ex=window.event.clientX+document.body.scrollLeft-320;  //得到事件的坐标x
       var ey=window.event.clientY+document.body.scrollTop;   //得到事件的坐标y
       span.style.left=ex;
       span.style.top=ey;
       span.style.display ='';
       fm.all("ItemKind"+strFlag+"_Context")[index].value = strQueryString;

     }
       function hidenSpan(field,spanID)
     {
       var index = getElementOrder(field)-1;
       var spanIDName= spanID+index;
       var span = eval(spanID + "(" + index + ")");
       span.style.display="none";
     }
    
    </script>
    <!-- 页面样式 -->
    <link rel="stylesheet" type="text/css" href="/undwrt/css/Standard.css">
    </head>

<body bgcolor="#FFFFFF" onload="showValue();">
  <%
 
      String certiNo   = (String)session.getAttribute("CertiNo");
      String certiType = (String)session.getAttribute("CertiType");
      String riskCode  = (String)session.getAttribute("riskCode");
      String policyNo  = "";    //批单所对应的保单号s
      String proposalNo = "";   //批单所对应的投保单号
      String assessLevel = (String)request.getAttribute("AssessLevel");
    
      String facShareRate1 = "";
      String facShareRate2 = "";

      String certiName = "";
      String strDisable = "";
      String reinsIntent = "";
      InternationalizationUtil internal = new InternationalizationUtil();
      if(certiType.equals("E"))
      {
        strDisable = "disabled";  //批改时，分保标志不能修改
      }

      if(certiType.equals("T")){ 

          certiName=internal.getText("undwrt.pages.undwrtDeal.insureBill");
      }
      if(certiType.equals("P")) {
          certiName=internal.getText("undwrt.pages.undwrtDeal.insurancePolicy");
      }
      if(certiType.equals("E")) {
          certiName=internal.getText("undwrt.pages.undwrtDeal.correctBill");
          policyNo = (String)request.getParameter("policyNo");
          proposalNo = (String)request.getParameter("proposalNo");
      }
      if(certiType.equals("Y")) {
          certiName=internal.getText("undwrt.pages.undwrtDeal.advancePay");
      }
      if(certiType.equals("C")||certiType.equals("J")){
          certiName=internal.getText("undwrt.pages.undwrtDeal.truePay");certiType="J";
      }
      
  %>

 <form name="fm" method="post" action="CommonReinsSave.jsp">
	<table class="common" cellpadding="5" cellspacing="1" align="center">
		<tr>
			<td colspan=6 class=listtitle>
				<%--臨分意向確認 --%>
				<s:text name="undwrt.pages.undwrtDeal.partIntentionOk"/>
			</td>
		</tr>
		
        <tr>
			<td class="title">
				<%--險種 --%>
				<s:text name="riskName"/>：</td>
            <td class="title">
            	<input type="text" name="RiskCode" readonly value="<%=riskCode%>"></td>
            <td class="title" colspan=4></td>
        </tr>
        
        <tr>
        	<td class="title" >
        		<%--業務號 --%>
        		<s:text name="undwrt.pages.undwrtDeal.certiNo"/>：</td>
            <td class="input" colspan=2 >
            	<%=certiNo%>
            	<input type="hidden" name="policyNo" value="<%=policyNo%>">
            	<input type="hidden" name="proposalNo" value="<%=proposalNo%>">
            	<input type="hidden" name=certiType value="<%=certiType%>">
        		<input type="hidden" name=certiNo value="<%=certiNo%>">
        		<input type="hidden" name="dangerNos" value="<s:property value="dangerNos"/>">
        		<input type="hidden" name="iRiskCode" value="<s:property value="iRiskCode"/>">
        		<input type="hidden" name="whetherFacing" value="<s:property value="whetherFacing"/>">
            </td>
            <td class="title">
            	<%--業務類型 --%>
            	<s:text name="undwrt.pages.undwrtDeal.certiType"/>：</td>
			<td class="input" colspan=2 >
				<%=certiName%></td>
        </tr>
        </table>
		<!-- 商火危险单位临分循环开始 -->
		<s:if test="facingList != null">
		<s:iterator value="facingList" status="statu" id="facingList">
		<s:if test="enquiryList!=null">
		<s:iterator value="enquiryList" status="statu" id="enquiryVO">
		<s:if test="#enquiryVO.feoEnquiryVO.dangerNo==#facingList">
		<table>
		<tr>       
            <td colspan=1 class="input">
        		<%--危险单位号 --%>
        		<s:text name="危險單位號"/>：
        		<input type="text" readonly="readonly" name="dangerNo" value="<s:property value="#facingList"/>">
        	</td>
        </tr>
        <tr>
        	<td colspan=6 class="input">
        		<%--臨分意向 --%>
        		<s:text name="undwrt.pages.undwrtDeal.partIntention"/>：
        	</td>
        </tr>
        
        <tr>
            <td class=input colspan=6 align="center">
                <textarea class="big" wrap="hard" name="Remarks" rows="8" cols="80" maxLength="255" 
                	description="分保意向" value=""><s:property value="#enquiryVO.feoEnquiryVO.remarks"/>
                </textarea>
            </td>
        </tr>
        
        <tr>
      		<td class="input">
      			<input type="CheckBox" name="CheckBoxSpecialFacShare"> 
            	<input type="hidden" name="CheckBoxSpecialFacShareHidden" value="<s:property value="#enquiryVO.feoEnquiryVO.specialFacFlag"/>"/>
            	<s:text name="undwrt.CommonDangerReinsUnit.needSpecialTempDivide"/></td>
            <td class="title">
            	<s:text name="undwrt.pages.undwrtDeal.contributingPartRatio"/>(%)：</td>
            <td class="text">
            	<input type="text" name="SpecialFacShare" 
            		value="<fmt:formatNumber value="${enquiryVO.feoEnquiryVO.specialFacShare}" pattern="0.000000"/>"></td>
        </tr>
        
        <tr>
            <td class="input">
            	<input type="CheckBox" name="CheckBoxFacShare">
            	<input type="hidden" name="CheckBoxFacShareHidden" value="<s:property value="#enquiryVO.feoEnquiryVO.facFlag"/>">
            	<s:text name="undwrt.CommonDangerReinsUnit.needSpecialCommonDivide"/></td>
            <td class="title">
            	<s:text name="undwrt.pages.undwrtDeal.commonPartRatio"/>(%)：</td>
            <td class="text">
            	<input type="text" name="FacShare" 
            		value="<fmt:formatNumber value="${enquiryVO.feoEnquiryVO.facShare}" pattern="0.000000"/>"></td>        
        </tr>
    </table>
    
	<!--条件新增的空DATA表格-->
    <!--条件UI显示表格-->
    <span style="display:">
    <table id="reinsReceiveDATA_<s:property value="#facingList"/>" name="reinsReceiveDATA_<s:property value="#facingList"/>" style="display:none">
    <tbody>
		<tr>
			<td class=text width="15%">
				<!--接受人-->
       			<input class="codestyle2" Style="width:20%" name=ReinsCode maxlength=10 ONDBLCLICK="openreinspage(this)">
       			<input type="text" Style="width:80%" class=common name="ReinsName">
        		<input type="hidden" name="PayCode">
          		<input type="hidden" name="PayName">
          		<input type="hidden" name=addDangerNo value="<s:property value="#facingList"/>">
     		</td>
     		<td class=text width="5%">
     			<!--接受人类型，默认不打勾，为1-接受人 -->
                <input type="CheckBox" name="reinsTypeCheckBox"/>
                <input type="hidden" name="feoReinsReceiveReinsType"/>
        	</td>
            <td class=text width="19%">
				<!--最终接受人-->
           		<input class="codestyle2" Style="width:20%" name=FinalReinsCode maxlength=10 ONDBLCLICK="openreinspage(this)">
           		<input type="text" Style="width:80%" class=common name="FinalReinsName">
       			<input type="hidden" name="assessLevel" value="">
				<input type="hidden" name="assessLevel2" value="">
				<input type="hidden" name="assessLevel3" value="">
				<input type="hidden" name="assessLevel4" value="">
				<input type="hidden" name="assessLevel5" value="">
			</td>
            <td class="text" width="5%">
            	<input class=common name=ShareRate value='0.0000'/></td>
            <td class="text" width="5%">
            	<input class=common name=CommRate  value='0.0000'/></td>
            <td class="text" width="5%">
            	<input class=common name=WrittenLine value='0.0000'/></td>
            <td class="text" width="5%">
            	<input class=common name=WrittenComm value='0.0000'/></td>
            <td class="text" width="5%">
            	<input class=common name=OfferedLine value='0.0000'/></td>
            <td class="text" width="5%">
            	<input class=common name=OfferedComm value='0.0000'/></td>
		    <td class="text" width="5%">
		    	<input class=common name=TaxRate value='0.0000'/></td>
            <td class="text" width="5%">
            	<input class=common name=OthRate value='0.0000'/></td>
            <td class="text" width="4%">
            	<!--特约临分标志，默认打勾，为1-特约 -->
                <input type="CheckBox" name="facFlagCheckBox"/>                
                <input type="hidden" name="feoReinsReceiveFacFlag"/>
       		</td>
        	<td class=text width="5%">
        		<!--按原币分保标志，默认不打勾，为1-原币分保 -->
                <input type="CheckBox" name="currencyFlagCheckBox" checked=true/>                
        		<input type="hidden" name="feoReinsReceiveCurrencyFlag" >
         		<input type="hidden" name="feoReinsReceiveCurrency" value=""/>
           		<input type="hidden" name="feoReinsReceiveAmount" value="0"/>
             	<input type="hidden" name="feoReinsReceiveChgAmount" value="0"/>
             	<input type="hidden" name="feoReinsReceiveRate" value="0"/>
             	<input type="hidden" name="feoReinsReceivePremium" value="0"/>
              	<input type="hidden" name="feoReinsReceiveChgPremium" value="0"/>
              	<input type="hidden" name="feoReinsReceiveDeductible" value="0"/>
              	<input type="hidden" name="feoReinsReceiveDeductibleRate" value="0"/>
              	<input type="hidden"   name="feoReinsReceiveRemark" value=""/>
              	<input type="hidden"   name="feoReinsReceiveSpecialProvisions" value=""/> 
            	<input type="hidden" name="feoReinsReceiveDiffFlag" value="0000000000" >
   			</td>
       		<td class=text width="5%">
       			<!--评级信息-->
                <input onclick="showSpan(this,'Sub');" type="button" class="button" name="add_row_special" 
                	value="<s:text name='undwrt.pages.undwrtDeal.gradeMessages'/>"/>
      			<span id="span_ItemKindSub_Context" style="width:320;height:100;display:none;position:absolute;background-color:C0C0C0;">
           			<table class="sub">
                    	<tr>
                      		<td class="title">
                        		<textarea type="text" name="ItemKindSub_Context" class="common3" rows="20" cols="100" 
                        			style="width:320;height:100" description="附加险/附加条款的条款内容"></textarea>
                      		</td>
                    	</tr>
                    	<tr>
                      		<td align="center">
                        		<input type="button" name="button_ItemKindSub_Context_Close" class="button" alt="<s:text name='prompt.ok'/>" value="<s:text name='prompt.ok'/>" 
                          			onclick="hidenSpan(this,'span_ItemKindSub_Context')"/>
                      		</td>
                    	</tr>
             		</table>
       			</span>
			</td>
    		<td class=text width="10%">
          		<!-- luyang: 当下面元素为IMG时，fm.elements不认为IMG是表单里的元素。可能和浏览器版本有关，延后处理-->
               	<input type="button" name="deleteReinsReceiveButton_<s:property value="#facingList"/>" 
               		value="<s:text name='prompt.del'/>" class="button" alt="<s:text name='prompt.del'/>" 
               		src="/undwrt/common/images/butDeleteBlue.gif" 
               		onclick="deleteRow('reinsReceiveUI_<s:property value="#facingList"/>',this,1,1);"/>
             </td>
		</tr>
       </tbody>
	</table>
    
    <table class="common" cellpadding="5" cellspacing="1" align="center" id="reinsReceiveUI_<s:property value="#facingList"/>" name="reinsReceiveUI_<s:property value="#facingList"/>">
		<thead>
     		<tr class=listtitle >
     			<td width="15%"><s:text name="undwrt.pages.undwrtDeal.accepter"/></td>
                <td width="5%"><s:text name="undwrt.pages.undwrtDeal.broker"/></td>
                <td width="19%"><s:text name="undwrt.pages.undwrtDeal.finalAccepter"/></td>
                <!-- update by liuzhiyong start 20100907 将临分意向中有singed Line等三组字段中文显示-->      
	            <td class="centertitle2"  style="width:5%" ><s:text name="undwrt.pages.undwrtDeal.coveringShare"/></td>
	            <td class="centertitle2"  style="width:5%" ><s:text name="undwrt.pages.undwrtDeal.coveringFactorage"/></td>           
	            <td class="centertitle2"  style="width:5%" ><s:text name="undwrt.pages.undwrtDeal.acceptShare"/></td>
	            <td class="centertitle2"  style="width:5%" ><s:text name="undwrt.pages.undwrtDeal.acceptFactorage"/></td>
	            <td class="centertitle2"  style="width:5%" ><s:text name="undwrt.pages.undwrtDeal.inviteShare"/></td>
	            <td class="centertitle2"  style="width:5%" ><s:text name="undwrt.pages.undwrtDeal.inviteFactorage"/></td>
	            <!-- update by liuzhiyong end 20100907 将临分意向中有singed Line等三组字段中文显示-->                
                <td width="5%"><s:text name="undwrt.pages.undwrtDeal.percentageTax"/>%</td>
                <td width="5%"><s:text name="undwrt.pages.undwrtDeal.otherExpensesRatio"/>%</td>             
                <td width="4%"><s:text name="undwrt.pages.undwrtDeal.special"/></td>
                <td width="5%"><s:text name="undwrt.pages.undwrtDeal.payCurrencyReinsurance"/></td>
                <td width="5%"><s:text name="undwrt.pages.undwrtDeal.gradeMessages"/></td>  
                <td width="1%">*</td>
            </tr>
		</thead>
		<tbody>
		<s:if test="#enquiryVO.feoEnquiryVO.verifyFlag!=3">
        <s:if test="#enquiryVO.feoReinsReceiveVOList!=null">
		<s:iterator id="feoReinsReceive" status="statu" value="#enquiryVO.feoReinsReceiveVOList">
			<tr>
			<input type="hidden" name="addDangerNo" value="<s:property value="#facingList"/>">
				<td class="text" width="15%">
			  		<input type="text" Style="width:20%" class="codestyle2" name="ReinsCode" value="<s:property value="#feoReinsReceive.reinsCode" />" class="codestyle1" ondblclick="openreinspage(this);">
			   		<input type="text"  Style="width:80%" class="common"name="ReinsName" value="<s:property value="#feoReinsReceive.reinsName" />"> 
			     	<input type="hidden" name="PayCode" value="<s:property value="#feoReinsReceive.payCode" />">
			     	<input type="hidden" name="PayName" value="<s:property value="#feoReinsReceive.payName" />">
			  	</td>
			 	<td class="text" width="5%" > 
			 		<input type="CheckBox" name="reinsTypeCheckBox" 
			     	<s:if test="#feoReinsReceive.reinsType==0">
			      		checked 
			    	</s:if>/>
			        <input type="hidden" name="feoReinsReceiveReinsType" 
			        	value="<s:property value="#feoReinsReceive.reinsType" />">
			    </td> 
			 	<td class="text" width="19%" >
			        <input type="text" Style="width:20%" class="codestyle2" name="FinalReinsCode"  value="<s:property value="#feoReinsReceive.finalReinsCode" />" class="codestyle1" ondblclick="openreinspage(this);">
			        <input type="text" Style="width:80%" class="common" name="FinalReinsName" value="<s:property value="#feoReinsReceive.finalReinsName" />">   
			        <input type="hidden" name="assessLevel" value="<s:property value="#feoReinsReceive.assessLevel" />">
			        <input type="hidden" name="assessLevel2" value="<s:property value="#feoReinsReceive.assessLevel2" />">
			        <input type="hidden" name="assessLevel3" value="<s:property value="#feoReinsReceive.assessLevel3" />">
			        <input type="hidden" name="assessLevel4" value="<s:property value="#feoReinsReceive.assessLevel4" />">
			        <input type="hidden" name="assessLevel5" value="<s:property value="#feoReinsReceive.assessLevel5" />">
			 	</td>
				<td class="text" width="5%" >
					<input  type="text" name="ShareRate" value="<fmt:formatNumber value="${signedLine}" pattern="0.000000"/>" class="common" onblur="checkDecimal(this,9,6,'','')" ></td>
				<td class="text" width="5%" >
					<input  type="text" name="CommRate" value="<fmt:formatNumber value="${signedComm}" pattern="0.0000"/>" class="common" onblur="checkDecimal(this,9,6,'','')"></td> 
			 	<td class="text" width="5%" >
			 		<input  type="text" name="WrittenLine" value="<fmt:formatNumber value="${writtenLine}" pattern="0.000000"/>" class="common" onblur="checkDecimal(this,9,6,'','')" ></td>
			   	<td class="text" width="5%" >
			   		<input  type="text" name="WrittenComm" value="<fmt:formatNumber value="${writtenComm}" pattern="0.0000"/>" class="common" onblur="checkDecimal(this,9,6,'','')"></td> 
			  	<td class="text" width="5%" >
			  		<input  type="text" name="OfferedLine" value="<fmt:formatNumber value="${offeredLine}" pattern="0.000000"/>" class="common" onblur="checkDecimal(this,9,6,'','')" ></td>
			  	<td class="text" width="5%" >
			  		<input  type="text" name="OfferedComm" value="<fmt:formatNumber value="${offeredComm}" pattern="0.0000"/>" class="common" onblur="checkDecimal(this,9,6,'','')"></td> 	      
			  	<td class="text" width="5%" >
			  		<input  type="text" name="TaxRate" value="<fmt:formatNumber value="${taxRate}" pattern="0.0000"/>" onblur="checkDecimal(this,9,6,'','')" class="common"></td>
			  	<td class="text" width="5%" >
			  		<input  type="text" name="OthRate" value="<fmt:formatNumber value="${othRate}" pattern="0.0000"/>" class="common" onblur="checkDecimal(this,9,6,'','')"></td>  
			  	<td class="text" width="4%" >
			  		<input type="CheckBox" name="facFlagCheckBox" /><input type="hidden" name="feoReinsReceiveFacFlag" value="<s:property value="#feoReinsReceive.facFlag" />" ></td> 
			 	<td class="text" width="5%" >
			 		<input type="CheckBox" name="currencyFlagCheckBox"  />
					<input type="hidden" name="feoReinsReceiveCurrencyFlag" value="<s:property value="#feoReinsReceive.currencyFlag" />" >
					<input type="hidden" name="feoReinsReceiveCurrency" value="<s:property value="#feoReinsReceive.currency" />"/>
                	<input type="hidden" name="feoReinsReceiveAmount" value="<s:property value="#feoReinsReceive.amount" />"/>
                	<input type="hidden" name="feoReinsReceiveChgAmount" value="<s:property value="#feoReinsReceive.chgAmount" />"/>
                	<input type="hidden" name="feoReinsReceiveRate" value="<s:property value="#feoReinsReceive.rate" />"/>
                	<input type="hidden" name="feoReinsReceivePremium" value="<s:property value="#feoReinsReceive.premium" />"/>
               	 	<input type="hidden" name="feoReinsReceiveChgPremium" value="<s:property value="#feoReinsReceive.chgPremium" />"/>
                	<input type="hidden" name="feoReinsReceiveDeductible" value="<s:property value="#feoReinsReceive.deductible" />"/>
                	<input type="hidden" name="feoReinsReceiveDeductibleRate" value="<s:property value="#feoReinsReceive.deductibleRate" />"/>
                	<input type="hidden" name="feoReinsReceiveRemark" value="<s:property value="#feoReinsReceive.remark" />"/>
                	<input type="hidden" name="feoReinsReceiveSpecialProvisions" value="<s:property value="#feoReinsReceive.specialProvisions" />"/>
                	<input type="hidden" name="feoReinsReceiveDiffFlag" value="<s:property value="#feoReinsReceive.flag" />" >
				</td>
               	<td class=text width="5%">
               		<!--评级信息-->
                  	<input onclick="showSpan(this,'Sub');" type="button" class="button" name="add_row_special" value="<s:text name='undwrt.pages.undwrtDeal.gradeMessages'/>">
                   		<span id="span_ItemKindSub_Context" style="width:320;height:100;display:none;position:absolute;background-color:C0C0C0;">
                       		<table class="sub">
                            	<tr>
                              		<td class="title">
                                 		<textarea type="text" name="ItemKindSub_Context" class="common3" rows="20" cols="100" style="width:320;height:100" 
                                 			description="<s:text name='undwrt.pages.undwrtDeal.additionalContent'/>"></textarea>
                             		</td>
                            	</tr>
                            	<tr>
                               		<td align="center">
                                   		<input type="button" name="button_ItemKindSub_Context_Close" class="button" alt="<s:text name='prompt.ok'/>" value="<s:text name='prompt.ok'/>"
                                      		onclick="hidenSpan(this,'span_ItemKindSub_Context')">
                               		</td>
                            	</tr>
                         	</table>
                       	</span>
       			</td>
				<td class="text" width="10%" > <input type="button" name="deleteReinsReceiveButton" 
					value="<s:text name='prompt.del'/>" class="button" alt="<s:text name='prompt.del'/>" 
					src="/undwrt/common/images/butDeleteBlue.gif" onclick="deleteRow('reinsReceiveUI_<s:property value="#facingList"/>',this,1,1);"></td>
			</tr>  
		</s:iterator>
		</s:if>
		</s:if>
		</tbody>
        <tfoot>
     		<tr>
        		<td class="text" colspan=14 align="center">               
                	<Input name="addReinsReceive" class="button" type="button" value="<s:text name='undwrt.pages.undwrtDeal.addAccepter'/>" 
                		alt="<s:text name='undwrt.pages.undwrtDeal.addAccepter'/>" 
                		src="/undwrt/common/images/butAddReinsReceive.gif" 
                		onclick="insertRow('reinsReceiveUI_<s:property value="#facingList"/>','reinsReceiveDATA_<s:property value="#facingList"/>');">
              	</td>
      		</tr>
		</tfoot>
 		
	</table>
  	</span>
  	</s:if>
		</s:iterator>
		</s:if>
  	</s:iterator>
	</s:if>
	<!-- 商火危险单位临分循环结束 -->  
  	<br>
  	
  	<jsp:include page="FeoXLayerShow.jsp"/>
  	<table align="center" class="common" cellpadding="5" cellspacing="1">
		<tr>
   			<td class="title">
   				<%--询价单号 --%>
   				詢價單號
   			</td>
   			<td  class="title">
		      <%--危险单位号 --%>
		    	 危險單位號
		   </td>
   			<td class="title">
   				<%--再保確認狀態 --%>
   				<s:text name="undwrt.pages.undwrtDeal.reinsuranceAffirmStates"/>
   			</td>
      	</tr>
      	<s:if test="enquiryList!=null">
      	<input type="hidden" name="feoEnquiryDtoVerifyFlag" readonly value="<s:property value="#enquiryVO.feoEnquiryVO.verifyFlag" />">
      	<s:iterator value="enquiryList" status="statu" id="enquiryVO">
      	<tr>
   			<td class="title" >
   				<%--询价单号 --%>
   				<s:property value="#enquiryVO.feoEnquiryVO.enquiryNo" />
   				<input type="hidden" name="enquiryNo" value="<s:property value="#enquiryVO.feoEnquiryVO.enquiryNo" />">
   			</td>
   			<td  class="title" >
		      <%--危险单位号 --%>
		    	<s:property value="#enquiryVO.feoEnquiryVO.dangerNo" />
		   </td>
         	<td class="text">
             	<s:if test="#enquiryVO.feoEnquiryVO.verifyFlag==0">
             		<%--未提交再保 --%>
             		<s:text name="undwrt.pages.undwrtDeal.notSubmitReinsurance"/>
           		</s:if>
            	<s:if test="#enquiryVO.feoEnquiryVO.verifyFlag==1">
                    <%--待再保确认 --%>
                    <s:text name="undwrt.pages.undwrtDeal.waitReinsuranceAffirm"/>
              	</s:if>
           		<s:if test="#enquiryVO.feoEnquiryVO.verifyFlag==2">
                    <%--待再保通过 --%>
                    <s:text name="undwrt.pages.undwrtDeal.waitReinsurancePass"/>
              	</s:if>
            	<s:if test="#enquiryVO.feoEnquiryVO.verifyFlag==3">
                    <%--再保通过 --%>
                    <s:text name="undwrt.pages.undwrtDeal.reinsurancePass"/>
              	</s:if>
            	<s:if test="#enquiryVO.feoEnquiryVO.verifyFlag==4">
                    <%--再保不通过 --%>
                    <s:text name="undwrt.pages.undwrtDeal.reinsuranceNotPass"/>
               	</s:if>
            	<s:if test="#enquiryVO.feoEnquiryVO.verifyFlag==5">
                   <%--非临分 --%>
                   <s:text name="undwrt.pages.undwrtDeal.noPart"/>
              	</s:if>
               	<s:if test="#enquiryVO.feoEnquiryVO.verifyFlag==9">
               		<%--注销 --%>
                    <s:text name="undwrt.logout"/>
              	</s:if>
       		</td>
      		</tr>
      		</s:iterator>
      	</s:if>
      	<tr>
      		<td colspan="3" class=title>
      			<%--再保回饋意見 --%>
      			<s:text name="undwrt.pages.undwrtDeal.reinsuranceFeedbackOpinion"/>
      		</td>
      	</tr>
      	
      	<tr>
      		<td colspan="3">
		      	<table class="common" cellpadding="5" cellspacing="1" align="center" id="reinsVerifyUI" name="reinsVerifyUI">
		        	<thead>
		            	<tr class=listtitle>
      					<td width="10%" align="center">
      						<%--詢價單號(系統自動產生) --%>
      						<s:text name="undwrt.pages.undwrtDeal.inquiryListNo"/>(<s:text name="undwrt.pages.undwrtDeal.systemAutogeneration"/>)</td>
		                	<td width="10%" align="center">
		                		<%--確認次數 --%>
		                		<s:text name="undwrt.pages.undwrtDeal.numberAffirm"/>
		                	</td>
		                	<td width="60%" align="center">
		                		<%--再保意見 --%>
		                		<s:text name="undwrt.pages.undwrtDeal.reinsuranceOpinion"/>
		                	</td>
		                	<td width="10%" align="center">
		                		<%--危险单位号 --%>
		                		危險單位號
		                	</td>
		                	<td width="10%" align="center">
		                		<%--處理時間 --%>
		                		<s:text name="undwrt.pages.undwrtDeal.processingDate"/>
		                	</td>
		            	</tr>
		        	</thead>
		        	<s:if test="enquiryList!=null">
		        	<s:iterator id="enquiryVO" status="statu" value="enquiryList">
		        		<s:if test="#enquiryVO.feoReinsVerifyVOList!=null">
		        		<s:iterator id="feoReinsVerifyVO" status="statu" value="#enquiryVO.feoReinsVerifyVOList">
				    	<tr>
				        	<td class="text" width="10%">
				        		<s:property value="#feoReinsVerifyVO.enquiryNo" />
				        	</td> 
				        	<td class="text" width="10%">
				        		<s:property value="#feoReinsVerifyVO.serialNo" />
				        	</td>  
					    	<td class="text" width="60%">
					    		<textarea name="feoReinsVerify" rows="3" cols="100" readonly="true" styleClass="one">${verifyText }</textarea>
					    	</td>
					    					        	</td> 
				        	<td class="text" width="10%">
				        		<s:property value="#feoReinsVerifyVO.dangerNo" />
				        	</td>  
					    	<td class="text" width="10%">
					    		<rc:rcDate value = "${verifyDate }" format="yyyy-MM-dd"/>
					    	</td>
				    	</tr>
				    	</s:iterator>
				    	</s:if>
					</s:iterator>
					</s:if>
		        	<tfoot></tfoot>
		        	<tbody></tbody>
		      	</table>
	      	</td>
      	</tr>
  	</table>
  	<div align="center" id="divButton" style="display:">
  		<table class="common" cellpadding="5" cellspacing="0" align="center">
  			<tr>
      			<td class=button width=33% align="center">
      				<%--儲存 --%>
      				<Input name="butSave" class="button" type="button" value="<s:text name='undwrt.CommonDealContent.tempSave'/>" 
      				alt="<s:text name='undwrt.save'/>" src="/undwrt/common/images/butSave.gif" 
      				onclick="return saveReinsReceiveInfo();"></td>
      			<td class=button width=33% align="center">
      				<%--提交再保 --%>
      				<Input name="buttonTransmitReins" class="longbutton" type="button" value="<s:text name='undwrt.pages.undwrtDeal.submitReinsurance'/>" 
      					alt="<s:text name='undwrt.pages.undwrtDeal.submitReinsurance'/>" 
      					src="/undwrt/common/images/butTransmitReins.gif" onclick="return commitReins();"></td>
      			<td class=button width=33% align="center">
      				<%--关 闭 --%>
      				<Input name="buttonCancel" class="button" type="button" value="<s:text name='undwrt.close'/>" 
      					alt="<s:text name='undwrt.close'/>" src="/undwrt/common/images/butCancel.gif" 
      					onclick="window.close()"></td>
			</tr>
  		</table>
  	</div>
</form>
</body>
</html>