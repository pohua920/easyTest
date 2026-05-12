/****************************************************************************
 * DESC       ：代码选择
* Author     : 中国大地项目组
 * CREATEDATE ：2003-01-14
 * MODIFYLIST ：   Name       Date            Reason/Contents
 *          ------------------------------------------------------
 *            sunchenggang 2003-01-22     修改private_Code_CallService
                                          增加读取页面保单号域

 ****************************************************************************/

var DEBUG_MODE = true;

function code_CodeSelect(field)
{ 
 
  if(!fm.inquery && !fm.queryerror)
    window.status="";
  if(event.type=="keyup")
  {
    var charCode=window.event.keyCode;
    if(!(charCode==13 & window.event.ctrlKey))
      return;
  }
  
  
   // alert("双击");
    
    
  private_Code_CallService(field,"select");
}

function code_CodeChange(field) 
{
 /* modify  by  都邦项目组 孙兆云  合并阳光项目组程序  2005-12-07  begin */
  //add by zhulei begin 20051029 清空代码录入域，不做代码校验
	if(field.value==""){
    field.oldvalue="";
  return;
  }
  //add by zhulei begin 20051029 清空代码录入域，不做代码校验
/* modify  by  都邦项目组 孙兆云  合并阳光项目组程序  2005-12-07  end  */	
  if(!fm.inquery && !fm.queryerror)
    window.status="";
   //alert("2222222222222");   
  if(field.oldvalue==field.value)
    return;
    //alert("3333333333"); 
  private_Code_CallService(field,"change");
   //alert("4444444444");
}


function private_Code_CallService(field,codemethod)
{
  //alert("11111111111111111");
  if(fm.inquery==true)
    return;
     //alert("22222222222222222222222");
   fm.inquery = true; //此行用于校验是否正在查询
    var fmcode = parent.fraCode.fm;
   //alert("33333333333333");  
  //请求服务器
  try
  {
    if(DEBUG_MODE==true)
    {
    	//alert("44444444444"); 
      if (field.coderelation==null || field.coderelation=="")
      {
        alert(field.name +i18n.common.coderNotEnmpty);  //.coderelation不能为空
        fm.inquery = false;
        return;
      }

      if (!(field.className=="codecode" || field.className=="codename"))
      {
        alert(field.name +i18n.common.codetypeMustCodecode);  //.codetype 必须为codecode或codename
        fm.inquery = false;
        return;
      }
      if((field.className=="codename") && isNaN(parseInt(field.coderelation,10)))
      {
        alert(i18n.common.oneOnlyInteger);  //codetype 须为codename时,coderelation有且只有一个整数
        fm.inquery = false;
        return;
      }
      if (!(field.codelimit=="must" || field.codelimit=="clear" ||field.codelimit=="none"))
      {
        alert(field.name +i18n.common.codeMustnone);  //.codelimit 必须为must,clear或none
        fm.inquery = false;
        return;
      }

      if (!(field.querytype=="always" || field.querytype=="need"))
      {
        alert(field.name +i18n.common.querytypeMustNeed);  //.querytype 必须为always或need
        fm.inquery = false;
        return;
      }
    }
  
    //alert(fmcode.querytype.value);
    
    //alert(parent.fraCode1.name);
    //alert(parent.fraCode.name);
    //alert(parent.fraCode.fm);
   // alert("field.querytype:" + field.querytype);
   // parent.fraCode.fm.querytype.value=field.querytype;
    //alert("parent.fraCode.fm.querytype.name:" + parent.fraCode.fm.querytype.name);
   //alert("55555555"); 
    fmcode.querytype.value=field.querytype;
    fmcode.codemethod.value=codemethod;
    fmcode.codetype.value=field.codetype;
    //alert("666666666666"); 
    //modify by weishixin add begin 20031031
    
    var RationCode = "";
    if(field.codetype=="ProjectCode")
    {
    	if(fm.CarKindCode.value=="M0")
    	{
           RationCode = "1";
        }
        else
        {
        	RationCode = "2";
        }
        RationCode = RationCode + fm.ExhaustScale.value ;

    	RationCode = RationCode + fm.AreaCode.value + fm.ProjectCode.value;
    	fmcode.codevalue.value=RationCode;
    }
    else if(field.codetype=="ModelCode")
    {
    	fmcode.codevalue.value=""+"|"+""+"|"+""+"|"+field.value;
    }
    else
    {
  
        // alert("7777777777777"); 
   	  fmcode.codevalue.value=field.value;
   }
    //modify by weishixin add end 20031031
    //alert("888888888888"); 
    fmcode.coderelation.value=field.coderelation;
    fmcode.codelimit.value=field.codelimit;
    fmcode.codeclass.value=field.className;
   //alert("99999999999999");
    fmcode.codeindex.value= getElementIndex(field);
     //alert("10101101010101010101");
     try
    {
      fmcode.comcode.value = fm.ComCode.value;
    }catch(noriskcodeE){
    	fmcode.comcode.value = "";
    	}   
   //add by licongliang 2007-7-2 11:14 增加参数隐含域 业务员类型
    try
    {
      fmcode.emptype.value = fm.Handler1Type.value;
      
    }catch(policye)
    {
    fmcode.emptype.value="0";//0 普通业务员 1 营销员
    }
    
    try
    {
      fmcode.riskcode.value=fm.RiskCode.value;  //险种代码 ('PUB')
    }
    catch(noriskcodeE)
    {
      fmcode.riskcode.value='PUB';  //险种代码 ('PUB')
    }
    
    try
    {
      fmcode.language.value=fm.Language.value;  //语种
    }
    catch(nolanguageE)
    {
      fmcode.language.value='C';  //语种
    }
    try
    {
      fmcode.codeother.value=fm.PolicyNo.value;  //单证号
    }
    catch(novisanoE)
    {
      fmcode.codeother.value='';  //单证号
    }
    if(field.codetype=="KindCode4")
    {
      try
      {
        fmcode.language.value = fm.DamageStartDate.value;
      }
      catch(noDamageStartDateE)
      {
        fmcode.language.value = "";
      }
    }
    try
    {
      fmcode.fieldext.value = field.fieldext;
    }
    catch(nofieldextE)
    {
      fmcode.fieldext.value = "";
    }
    //add by zengyangzheng for statreport begin
    if(field.codetype=="Dimension")
    {
    	var strClassCode = new Array();
    	var intClassCode = fm.ClassCode.options.length;
    	var strRiskCategory = fm.RiskCategory.value;
    	var selClassCode = 0;
    	var size = 0;
    	var allClassCode = "";
    	var strReportCode = "";
    	strReportCode = fm.ReportCode.value;
    	if(fm.ReportCode.value=="")
      {
        alert(i18n.common.tabNameNotEnmpty);  //报表名称不能为空！
      }
	    if(strRiskCategory=="" && fm.ClassCode.value != "") {
	    	for(var i = 0; i < intClassCode; i ++) {  //得到选中的值
	    	  if(fm.ClassCode.options[i].selected) {
	    	    strClassCode[selClassCode] = fm.ClassCode.options[i].value;
	    	    selClassCode++;
	    	  }
	    	}
	    	for(var i = 0; i < selClassCode; i ++) {
	    	  allClassCode += strClassCode[i];
	    	}
	    	strRiskCategory = "1";
	    }
      if(strRiskCategory=="" && strClassCode=="") {
         strRiskCategory = "0";
      }
      /* modify  by  都邦项目组 孙兆云  合并阳光项目组程序  2005-12-07  begin */
      /*if(strRiskCategory=="03") strRiskCategory="D";
      else if(strRiskCategory=="14" ||strRiskCategory=="15") strRiskCategory="E";
      else if(strRiskCategory=="02" ||strRiskCategory=="10"
      	||strRiskCategory=="11"||strRiskCategory=="YA") strRiskCategory="Y";
      else 
      	strRiskCategory = "Q";
      fmcode.fieldext.value = fm.ReportCode.value+strRiskCategory+allClassCode;*/
       if(strRiskCategory=="03") strRiskCategory="D";
      else if(strRiskCategory=="14" ||strRiskCategory=="15") strRiskCategory="E";
      else if(strRiskCategory=="09" ||strRiskCategory=="10"
      	||strRiskCategory=="11"||strRiskCategory=="YA") strRiskCategory="Y";
      else 
      	strRiskCategory = "Q";
      
      if(fm.ReportCode.value=="IBNR1")
        strReportCode = "IBNR";
      fmcode.fieldext.value = strReportCode+strRiskCategory+allClassCode;
     /* modify  by  都邦项目组 孙兆云  合并阳光项目组程序  2005-12-07  end  */
    }
    //add by zengyangzheng for statreport end
    // add by gefei begin 浙江除宁波，缴费通知书-银行
    if(field.codetype=="payNoticeComCode")
    {
        var objs = document.getElementsByName("businessno"); 
        for(i=0;i<objs.length;i++)      
        {
            if(objs[i].checked==true)
            {
                fmcode.fieldext.value = objs[i].value;
                break;
            }
        }
    }
    if(field.codetype=="payNoticeBankCode")
    {
        if(field.fieldext==null ||field.fieldext=="")	
    	{
	      if(fm.txtPayNoticeComCode.value=="")
	      {
	        alert(i18n.common.pleaseSelectAgency);  //请先选择机构！
	        fm.txtPayNoticeComCode.focus();
	      }
	      else
	      {
	        fmcode.fieldext.value = fm.txtPayNoticeComCode.value;
	      }
	    }
    }
    // add by gefei end 浙江除宁波，缴费通知书-银行
    //add by jiyonglian 增加渠道明细20101123begin
    if(field.codetype == "BusinessNature")
    {
        fmcode.fieldext.value = fm.BusinessChannel.value;
        if(fm.BusinessChannel.value=="")
        {
          alert(i18n.common.pleaseSelectChannel); //请先选择渠道大类!
          fm.BusinessNature.focus();fmcode.fieldext.value="";
        }
    }
    //add by jiyonglian 增加渠道明细20101123end
    	fmcode.action="/prpall/common/pub/UICodeGet.jsp";
        fmcode.submit();                                      //提交

  }
  catch(E)
  {
    if(DEBUG_MODE==true)
    {
      alert(E);
    }
  }
}

function initAllCodeInput()
{
  for(var i=0;i<fm.elements.length;i++)
  {
    if(fm.elements[i].className=="codecode" || fm.elements[i].className=="codename")
      fm.elements[i].oldvalue = fm.elements[i].value;
  }
}

/**
 @Author     : 中国大地项目组
 @description 检查代码域正在查询
 @param       无
 @return true:正在查询 / false:不在查询
 */
function checkCodeInQuery()
{ 
  //alert("正在查询代码");	 
  if(fm.inquery==true)
  {
    //errorMessage("正在查询代码!");
    window.status = "正在查询代码......";
    return true;
  }
  else
    return false;
}

