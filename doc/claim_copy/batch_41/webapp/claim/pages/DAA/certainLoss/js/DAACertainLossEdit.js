/*****************************************************************************
 * DESC       ：定损登记的脚本函数页面
 * AUTHOR     ：理赔组
 * CREATEDATE ： 2013-03-13
 * MODIFYLIST ：   Name       Date            Reason/Contents
 *          ------------------------------------------------------
 ****************************************************************************/

function insertRowCertainLossExt(RowCode){
  insertRow(RowCode);

  var element1 = document.getElementsByName('prpLverifyLossExtTitle');
  var i = element1.length - 1;
    
  if(fm.nodeType.value=="verip" || fm.nodeType.value=="verpo" ){
    if(fm.verpOpinion.value=="01")
      fm.prpLverifyLossExtTitle[i].value = "同意定損";
    else if(fm.verpOpinion.value=="02")
      fm.prpLverifyLossExtTitle[i].value = "補充信息";
    else if(fm.verpOpinion.value=="03")
      fm.prpLverifyLossExtTitle[i].value = "價格異議發回";
    else if(fm.verpOpinion.value=="04")
      fm.prpLverifyLossExtTitle[i].value = "價格已修正";
    else if(fm.verpOpinion.value=="05")
      fm.prpLverifyLossExtTitle[i].value = "向外詢價";
  }

  if(fm.nodeType.value=="verif"){
    if(fm.verifyOpinion.value=="01")
      fm.prpLverifyLossExtTitle[i].value = "同意定損";
    else if(fm.verifyOpinion.value=="02")
      fm.prpLverifyLossExtTitle[i].value = "價格異議";
    else if(fm.verifyOpinion.value=="03")
      fm.prpLverifyLossExtTitle[i].value = "信息不充分";
    else if(fm.verifyOpinion.value=="04")
      fm.prpLverifyLossExtTitle[i].value = "出具檢驗報告";
    else if(fm.verifyOpinion.value=="05")
      fm.prpLverifyLossExtTitle[i].value = "其它";
    
  }
}


/**
 *@description 回勘初始化的只读项设置
 *@param       无
 *@return      通过返回true,否则返回false
 */
function initReadonly()
{ 
  var element1 = document.getElementsByName('prpLcomponentKindCode');
  if(element1.length>1)  {
    for(i=0;i<element1.length;i++)
    {
      fm.prpLcomponentSys4SPrice[i].style.display = "none";
      fm.prpLcomponentSysMarketPrice[i].style.display = "none";
      fm.prpLcomponentSysMatchPrice[i].style.display = "none";
      fm.prpLcomponentNative4SPrice[i].style.display = "none";
      fm.prpLcomponentNativeMarketPrice[i].style.display = "none";
      fm.prpLcomponentNativeMatchPrice[i].style.display = "none";

      setReadonlyOfElementOfLoss(fm.prpLcomponentVerpCompPrice[i]); 
    }
  }
  else{
      fm.prpLcomponentSys4SPrice.style.display = "none";
      fm.prpLcomponentSysMarketPrice.style.display = "none";
      fm.prpLcomponentSysMatchPrice.style.display = "none";
      fm.prpLcomponentNative4SPrice.style.display = "none";
      fm.prpLcomponentNativeMarketPrice.style.display = "none";
      fm.prpLcomponentNativeMatchPrice.style.display = "none";
      
      setReadonlyOfElementOfLoss(fm.prpLcomponentVerpCompPrice); 
  }   
 
  tdNextHandleDept.innerHTML = "";
  fm.nextDeptName.style.display = "none";
  //tdnextHandlerCode.innerHTML = "";
  fm.nextHandlerName.style.display = "none";

  setReadonlyOfElementOfLoss(fm.prpLcarLossSumVeriManager);   
    
  //setReadonlyOfElementOfLoss(fm.prpLverifyLossVerifyOpinion);  
  setReadonlyOfElementOfLoss(fm.prpLverifyLossVerifyRemark);  
  setReadonlyOfElementOfLoss(fm.prpLverifyLossVerpRemark);  



  return true; 
} 


/**
 @author      孙成刚
 @description 使一个输入域设置为只读
 @param       iElement: 域   
 @return      无
 */
function setReadonlyOfElementOfLoss(iElement)
{
  if(iElement.type!=null){
    if(iElement.type=="select-one")
    {
      iElement.disabled = true;
    } 
   
    else if (iElement.type=="text")
    {
      iElement.onfocus = null;                   
      iElement.readOnly = true;
      iElement.className = "readonly";
    }
  }
}


/**
 *@description 检查定损登记
 *@param       无
 *@return      通过返回true,否则返回false
 */
function checkForm()
{
    return true;
}

/**
 *@description 提交
 *@param       无
 *@return      通过返回true,否则返回false
 */
function submitForm()
{
	if(checkForm()==false)
	{
		return false;
	}
	fm.buttonSave.disabled = true;
	fm.submit();
	return true;
}

/**
 *@description 清除
 *@param       无
 *@return      通过返回true,否则返回false
 */
function resetForm()
{
	if(window.confirm("確定要清除嗎？"))
	{
		location.href = location.href;
		return true;
	}
	else
	{
		return false;
	}
}

/**
 *@description 根据按钮状态保存报案数据
 *@param       this
 *@param       保存状态
 *@return      通过返回true,否则返回false
 */	  
function saveForm(field,saveType)
{
	var element = document.getElementsByName('prpLverifyLossExtTitle');
	var i = element.length - 1;
	var addNewRepairCompentRow=-1;//默认没有增加一条换件或者修理的数据
	var lossItemCode = fm.prpLverifyLossLossItemCode.value;
	var nodeType = fm.prpLverifyLossNodeType.value;
	var compensatebackFlag=fm.prpLverifyLossCompensateFlag.value; //理算退回的标记
		//reason: ValidateData.js中的校验不起作用时，因为没有调用校验方法
		if(nodeType=="propc"){
			if(!validateForm(fm,'Prop_Data')) 
			{
				return false;
			}
		}  else {
			//由於超过页面上的对象超过2千以後，速度很慢，並且反映出ie错误，所以将这个校验删除了
			//if(!validateForm(fm,'RepairFee_Data,Component_Data'))    //因为页面上没有查勘相关对象了(,ThirdParty_Data,Driver_Data)
			// {
			//   return false;
			// } 
		}
		//Reason:在页面中对定损金额进行判断
		var prpLclaimGradeValueUpper = trim(fm.prpLclaimGradeValueUpper.value);//金额上限
		var prpLclaimGradeValueLower = trim(fm.prpLclaimGradeValueLower.value);//金额下限

		var SumDefLoss2 = fm.SumDefLoss2.value;//换件合计
		var SumDefLoss1 = fm.SumDefLoss1.value;//修理合计
		var SumDefComponentFee = 0;
		var SumDefRepairFee = 0;
		var prpLClaimGradeSumDefLoss = 0;
		var prpLclaimGradeValueUpperFee = 0;
		var prpLclaimGradeValueLowerFee = 0;

		SumDefComponentFee = parseFloat(SumDefLoss2);
		SumDefRepairFee = parseFloat(SumDefLoss1);
		prpLclaimGradeValueUpperFee = parseFloat(prpLclaimGradeValueUpper);
		prpLclaimGradeValueLowerFee = parseFloat(prpLclaimGradeValueLower);

		if(isNaN(SumDefComponentFee)||SumDefComponentFee.length<1){
			SumDefComponentFee = 0;
		}
		if(isNaN(SumDefRepairFee)||SumDefRepairFee.length<1) {
			SumDefRepairFee = 0;
		}
		prpLClaimGradeSumDefLoss = SumDefRepairFee+SumDefComponentFee;

		//判断是否超过最高权限
		var prpLclaimGradeFlag = trim(fm.prpLclaimGradeFlag.value);
		if(!prpLclaimGradeFlag=="1")
		{
			if((prpLClaimGradeSumDefLoss>prpLclaimGradeValueUpperFee))
			{
				errorMessage("您所輸入的定损金额"+prpLClaimGradeSumDefLoss+"不在您的权限之内!");
				return false;
			}    
		}

		//Reason:对輸入险别进行判断
		var prpLcarLossInsureCarFlagName = trim(fm.prpLcarLossInsureCarFlagName.value);
		var KindCode = "";

		//取消框架js判断後，系统非空元素需要单独判断 begin
		var prpLverifyLossRegistNo = trim(fm.prpLverifyLossRegistNo.value);
		var prpLverifyLossPolicyNo = trim(fm.prpLverifyLossPolicyNo.value);
		var prpLverifyLossLossItemCode = trim(fm.prpLverifyLossLossItemCode.value);
		var prpLverifyLossDefLossDate = trim(fm.prpLverifyLossDefLossDate.value);
		var prpLverifyLossMakeCom = trim(fm.prpLverifyLossMakeCom.value);
		var prpLverifyLossComCode = trim(fm.prpLverifyLossComCode.value);
		if(prpLverifyLossRegistNo==null||prpLverifyLossRegistNo.length<1){
			errorMessage("报案号不能为空!");
			field.disabled = false; 
			return false;    
		}
		if(prpLverifyLossPolicyNo==null||prpLverifyLossPolicyNo.length<1){
			errorMessage("保单号不能为空!");
			field.disabled = false; 
			return false;    
		}
		if(prpLverifyLossLossItemCode==null||prpLverifyLossLossItemCode.length<1){
			errorMessage("标的序号不能为空!");
			field.disabled = false; 
			return false;    
		}
		if(prpLverifyLossMakeCom==null||prpLverifyLossMakeCom.length<1){
			errorMessage("出单机构不能为空!");
			field.disabled = false; 
			return false;    
		}
		if(prpLverifyLossComCode==null||prpLverifyLossComCode.length<1){
			errorMessage("归属机构不能为空!");
			field.disabled = false; 
			return false;    
		}
		//取消框架js判断後，系统非空元素需要单独判断 end
		var repairFeeCount=getElementCount("carLossRepairFeeLossItemCode");
		var lossComponentLossCount=getElementCount("carLossComponentLossItemCode");
		for(var j=1;j<lossComponentLossCount;j++)
		{
			KindCode = trim(fm.prpLcomponentKindCode[j].value);
			//理算回退的判断是否增加新行了。。
			if (fm.prpLcomponentCompensateBackFlag[j].value!="1") addNewRepairCompentRow=addNewRepairCompentRow+1;
			if (KindCode=="") {
				errorMessage("零配件更換專案費用清單的第"+j+"行沒有輸入險別!");
				fm.prpLcomponentKindName[j].focus();         
				fm.prpLcomponentKindName[j].select();  
				return false; 	
			}
		}
		for(var j=1;j<repairFeeCount;j++)
		{
			KindCode = trim(fm.prpLrepairFeeKindCode[j].value);
			if (fm.prpLrepairFeeCompensateBackFlag[j].value!="1") addNewRepairCompentRow=addNewRepairCompentRow+1;
			if (KindCode=="") {
				errorMessage("修理專案費用清單的第"+j+"行沒有輸入險別!");
				fm.prpLrepairFeeKindName[j].focus();         
				fm.prpLrepairFeeKindName[j].select(); 
				return false; 	
			}
		}
		if(prpLcarLossInsureCarFlagName=="是")
		{
			//可以輸入的险别有： 
			//车辆损失险--A；    
			//火灾、爆炸、自燃损失险--E；
			//玻璃单独破碎险--F；
			//新增设备损失保险--X；
			//自燃损失险--Z；
			//起重、装卸、挖掘车辆损失险--K1；
			//特种车辆固定设备、仪器损坏险--K2；
			//车身划痕损失险--L
		} else {
			var repairFeeCount=getElementCount("carLossRepairFeeLossItemCode");
			var lossComponentLossCount=getElementCount("carLossComponentLossItemCode");
			for(var j=1;j<repairFeeCount;j++)
			{
				KindCode = trim(fm.prpLrepairFeeKindCode[j].value);
				//设置部位的名称：
				fm.prpLrepairFeePartName[j].value=fm.prpLrepairFeePartCode[j].options[fm.prpLrepairFeePartCode[j].selectedIndex].text
				//理算回退的判断是否增加新行了。。
				if (fm.prpLrepairFeeCompensateBackFlag[j].value!="1") addNewRepairCompentRow=addNewRepairCompentRow+1;
				//claim 新险种上线，这个判断先去掉
			}
			for(var j=1;j<lossComponentLossCount;j++)
			{
				KindCode = trim(fm.prpLcomponentKindCode[j].value);
				//设置部位的名称：
				//理算回退的判断是否增加新行了。。
				if (fm.prpLcomponentCompensateBackFlag[j].value!="1") addNewRepairCompentRow=addNewRepairCompentRow+1;
			}
		}

		//如果是提交，判断是否不是nextNodeNo或者人员为空！！！！
//		if(fm.prpLverifyLossLossItemCode.value=="1"){
			//修理，换件，财产损失必录一项才能进行
//			var carLossRepairFeeLossItemCode = fm.carLossRepairFeeLossItemCode.length;
//			var carLossComponentLossItemCode = fm.carLossComponentLossItemCode.length;
//			if(carLossRepairFeeLossItemCode == undefined &&carLossComponentLossItemCode == undefined)
//			{ 
				//reason:itest:434 定损任务处理，三者车辆定损，如果没有保三者险，则任务流转不下去
				//alert("修理项目费用清单，零部件更换项目费用清单，至少輸入一项才能保存");    
				//return false;  
//			}
//		}
		if (saveType=="4") {
			if(trim(fm.carName1.value) == "标的车"){
				if(trim(fm.prpLcarLossLossItemName.value) == ""){
					alert(i18n.certainLoss.licensePlateNoEmpty);  // 车牌号码不能为空！
					return false;
				}
				var re=/^[\u4e00-\u9fa5a-zA-Z0-9- ]{1,12}$/;
				if((trim(fm.prpLcarLossLossItemName.value)).search(re)==-1)
				{
					alert("輸入的牌照號碼格式不正確");
					return false;
				}
			}
			if(i>0&&fm.prpLverifyLossExtTitle[i].value == ""){
				alert(i18n.certainLoss.pleaseEnterFee);  // 请输入定损意见！
				return false;
			}
			if(nodeType=="propc"){
				if(checkPropKindCode()==false){   return false;   }
				var prpLpropSerialNo = fm.prpLpropSerialNo.length
				if(prpLpropSerialNo == undefined)       
				{ 
					alert(i18n.certainLoss.enterLeastSubmitList);  // 财产定损清单至少輸入一项才能提交
					return false; 
				} 
			}
//			else if(fm.prpLverifyLossLossItemCode.value=="1"){
//				//修理，换件，财产损失必录一项才能进行
//				var carLossRepairFeeLossItemCode = fm.carLossRepairFeeLossItemCode.length;
//				var carLossComponentLossItemCode = fm.carLossComponentLossItemCode.length;
//				//var personSerialNo = fm.personSerialNo.length; 
//				if(carLossRepairFeeLossItemCode == undefined &&carLossComponentLossItemCode == undefined)
//				{ 
//					//reason:itest:434 定损任务处理，三者车辆定损，如果没有保三者险，则任务流转不下去
//					//        alert("修理项目费用清单，零部件更换项目费用清单，二者至少輸入一项");
//					//        return false; 
//				}
//			} 
//			else {
//				//修理，换件，财产损失必录一项才能进行
//				var carLossRepairFeeLossItemCode = fm.carLossRepairFeeLossItemCode.length;
//				var carLossComponentLossItemCode = fm.carLossComponentLossItemCode.length;
//				if(carLossRepairFeeLossItemCode == undefined &&carLossComponentLossItemCode == undefined)
//				{  
//					//reason:itest:434 定损任务处理，三者车辆定损，如果没有保三者险，则任务流转不下去
//					//        alert("修理项目费用清单，零部件更换项目费用清单,二者至少輸入一项");
//					//        return false; 
//				} 
//				//增加对定损核损金额不一致时，不能提交核损的限制：
//			}
		}
		//如果是理算回退的，並且没有增加新行的话，那么可以直接提交到理算 
		if (compensatebackFlag=="1" &&addNewRepairCompentRow<0 ) fm.NextComeBackCompensate.value="1";
		if (saveType == 'CheckInput') {
			fm.buttonSaveType.value = '2';
		} else {
			fm.buttonSaveType.value = saveType;
		}
		//reason:当按下某一按钮时请将这个按钮变灰，否则用户可能多按引发错误
		field.disabled = true;
		fm.submit();
}


/**
 @author      理赔组
 @description 校验险别
 @return      boolean: 合法为true,否则为false  
 */

function checkPropKindCode()
{   
 
  //校验輸入险别
  //只能輸入以下险别  
  //第三者责任险--B；
  //车上货物责任险--D2；
  //车载货物掉落责任险--H；
  if(fm.prpLpropKindCode.length>0){
    for(i=1;i<fm.prpLpropKindCode.length;i++){
      var kindCode = fm.prpLpropKindCode[i].value;
      if(kindCode!=RISKINFO.KINDCODE_D_B && kindCode!=RISKINFO.KINDCODE_D_D2 && kindCode!="H")
      {
        alert(i18n.certainLoss.propertyLossOtherLoss);  // 财产损失项目中,只能輸入B、D2、H
														// 三个险别的损失！
        //fm.prpLpropKindCode[i].focus();       
      	//fm.prpLpropKindCode[i].select();
        return false; 
      }    
    }  
  }
  return true;
} 

/**
 *@description 实现修理换件，人员，费用的显示变换
 *@param       spanID span编号
 *@param       spanName span的名称
 */
function changeSpan(spanID,spanName){ 

  var spanNameRepairComponentObject = eval("SpanRepairComponent");
  var spanNamePersonObject = eval("SpanPerson");
  var spanNamePropObject = eval("SpanProp");

  if(spanName.id == "SpanRepairComponent"){
    spanNameRepairComponentObject.style.display="";
    spanNamePersonObject.style.display="none";
    spanNamePropObject.style.display="none";
  } else if(spanName.id == "SpanPerson") {
    spanNameRepairComponentObject.style.display="none";
    spanNamePersonObject.style.display="";
    spanNamePropObject.style.display="none";     
  } else if(spanName.id == "SpanProp") {
    spanNameRepairComponentObject.style.display="none";
    spanNamePersonObject.style.display="none";
    spanNamePropObject.style.display="";     
  }

}   
  
/**
 *@description 根据工时，工时单价和材料费用计算定损金额
 *@param       无
 *@return      通过返回true,否则返回false
 */
 
 function getSumDefLoss(field,ext)
 {  
  var fieldname=field.name; 
  var i = 0;
  var findex=0;       
  var manHourFee;
  var materialFee; 
  var sumDefLoss;
  var ManHour;
  var Quantity;

  for(i=1;i<fm.all(fieldname).length;i++)
  {
    if( fm.all(fieldname)[i] == field )
    {
      findex=i;
      break;
    }
  } 
 
  if(ext==1){
    ManHour = parseFloat(fm.all("prpLrepairFeeManHour")[findex].value);
    manHourFee  = parseFloat(fm.all("prpLrepairFeeManHourUnitPrice")[findex].value)      
    materialFee = parseFloat(fm.all("prpLrepairFeeMaterialFee")[findex].value); 
    if(isNaN(manHourFee)||manHourFee.length<1){
      manHourFee = 0;
    }
    if(isNaN(materialFee)||materialFee.length<1) {
      materialFee = 0;
    }
    if(manHourFee==0 && materialFee==0){
      //return true;  
    }
    if(isNaN(ManHour)||ManHour.length<1){
      ManHour = 0; 
    }
    sumDefLoss = (manHourFee*ManHour) + materialFee;    
//    fm.all("prpLrepairFeeSumDefLoss")[findex].value = point(round(sumDefLoss,2),2);
    fm.all("prpLrepairFeeSumDefLoss")[findex].value = point(round(sumDefLoss,0),0);
    sumRepairFee();
  } else {    
    sumDefLoss = parseFloat(fm.all("prpLcomponentSumDefLoss")[findex].value);
    quantity  = parseFloat(fm.all("prpLcomponentQuantity")[findex].value)      
    manHourFee = parseFloat(fm.all("prpLcomponentManHourFee")[findex].value); 
    materialFee = parseFloat(fm.all("prpLcomponentMaterialFee")[findex].value);
    materialRestFee = parseFloat(fm.all("prpLcomponentRestFee")[findex].value);
                                     
    if(isNaN(quantity)||quantity==''){
      quantity = 0;
    }
    if(isNaN(manHourFee)||manHourFee==''){
      manHourFee = 0;
    }																 
    if(isNaN(materialFee)||materialFee==''){
      materialFee = 0;
    }
    if(quantity==0 && materialFee==0 ){
      return true;   
    }		
    if(isNaN(materialRestFee)||materialRestFee==''){
      materialRestFee = 0;
    }
    fm.all("prpLcomponentVerpCompPrice")[findex].value = materialRestFee * quantity;
    
//    sumDefLoss = parseFloat(point(round(quantity*materialFee,2),2)) + parseFloat(point(round(manHourFee,2),2));
    sumDefLoss = parseFloat(point(round(quantity*materialFee,0),0)) + parseFloat(point(round(manHourFee,0),0));
//    fm.all("prpLcomponentSumDefLoss")[findex].value = point(round(sumDefLoss,2),2); 
    fm.all("prpLcomponentSumDefLoss")[findex].value = point(round(sumDefLoss,0),0); 
    sumComponentFee();
  } 
  //汇总偏差定损金额
  fm.prpLverifyLossWarpDefLoss.value=parseFloat(fm.SumDefLoss1.value)+parseFloat(fm.SumDefLoss2.value);  
  
  return true;  

       
 } 

/**
 *@description 计算工时费合计、核价工时费合计
 *@param       无
 *@return      无
 */ 
  function sumRepairFee()
  {
   var sumRepairDefFee = 0 ;//总修理定损费
   var sumRepairVerifyFee = 0 ;//总修理核价费
   var prpLrepairFeeSumDefLoss = 0 ;
   var prpLrepairFeeVeriSumLoss = 0 ;
   var sumFloatRate = 0; 
   var sumRepairDefFeeTemp = 0;
   var sumRepairVerifyFeeTemp = 0;
try{
   for(i=1;i<fm.all("prpLrepairFeeManHour").length;i++){
   	    prpLrepairFeeVeriSumLoss = point(round(parseFloat(fm.all("prpLrepairFeeVeriSumLoss")[i].value),0),0);
   	    prpLrepairFeeSumDefLoss = point(round(parseFloat(fm.all("prpLrepairFeeSumDefLoss")[i].value),0),0);
	   	if(isNaN(prpLrepairFeeVeriSumLoss)){
	      prpLrepairFeeVeriSumLoss = 0;
	    }
	    if(isNaN(prpLrepairFeeSumDefLoss)) {
	      prpLrepairFeeSumDefLoss = 0;
	    }
        
      sumRepairDefFee    = sumRepairDefFee + prpLrepairFeeSumDefLoss;
      sumRepairVerifyFee = sumRepairVerifyFee + prpLrepairFeeVeriSumLoss;
   }
   
   //定损工时费合计
   sumFloatRate = parseFloat(fm.prpLcarLossSumFloatRate.value); 
   if( isNaN(sumFloatRate)||sumFloatRate== ""){
  		fm.prpLcarLossSumFloatRate.value = 0;
  		sumFloatRate = parseFloat(fm.prpLcarLossSumFloatRate.value); 
  }
   sumRepairDefFeeTemp = sumRepairDefFee * (1+parseFloat(sumFloatRate)/100);
   sumRepairDefFeeTemp = round(sumRepairDefFeeTemp,0);
   sumRepairVerifyFeeTemp = sumRepairVerifyFee * (1+parseFloat(sumFloatRate)/100);
   fm.SumDefLoss1.value = sumRepairDefFeeTemp;
//   fm.SumDefLoss1.value = point(round(sumRepairDefFeeTemp,0),0)
   //核价工时费合计
   sumRepairVerifyFeeTemp = round(sumRepairVerifyFeeTemp,0);
   fm.SumVerifyLoss1.value = sumRepairVerifyFeeTemp;
//   fm.SumVerifyLoss1.value = point(round(sumRepairVerifyFeeTemp,0),0)
   //计算总的定损金额
   sumPreDefLoss();
}catch(ex){}
   return true;
  } 

/**
 *@description 计算换件费合计、核价换件费合计
 *@param       无
 *@return      无
 */ 
  function sumComponentFee() 
  { 
   var sumComponentDefFee = 0 ;//总换件定损费
   var sumComponentManHourFee  = 0 ; //总工时费
   var sumComponentMaterialFee = 0 ; //总材料费
   var sumComponentMaterialRestFee=0;
   var quantity = 0 ;
   var manHourFee = 0 ;
   var materialFee = 0 ;
   if(fm.all("SumDefLoss2")!=null){//换件费合计
     if(fm.all("SumDefLoss2").length>0){ 
       for(j=0;j<fm.all("SumDefLoss2").length;j++){
         for(i=1;i<fm.all("prpLcomponentQuantity").length;i++){//更换数量
            if(j == fm.all("carLossComponentLossItemCode")[i].value){
         	    quantity = parseFloat(fm.all("prpLcomponentQuantity")[i].value);//更换数量
         	    manHourFee = parseFloat(fm.all("prpLcomponentManHourFee")[i].value);
         	    materialFee = parseFloat(fm.all("prpLcomponentMaterialFee")[i].value);
              materialRestFee = parseFloat(fm.all("prpLcomponentRestFee")[i].value);
        	   	if(isNaN(quantity)||quantity.length<1){ 
        	      quantity = 0;
        	    }
        	    if(isNaN(manHourFee)||manHourFee.length<1) {
        	      manHourFee = 0;
        	    }
        	    if(isNaN(materialFee)||materialFee.length<1){ 
        	      materialFee = 0;
        	    }
			    if(isNaN(materialRestFee)||materialRestFee.length<1||fm.all("prpLcomponentIfRemain")[i].value == "1"){
			      materialRestFee = 0;
			    }

//              sumComponentManHourFee   = sumComponentManHourFee + parseFloat(point(round(quantity*materialFee,2),2));
//              sumComponentMaterialFee  = sumComponentMaterialFee + parseFloat(point(round(manHourFee,2),2));
//              sumComponentMaterialRestFee =sumComponentMaterialRestFee +parseFloat(point(round(materialRestFee,2),2));
			    sumComponentManHourFee   = sumComponentManHourFee + parseFloat(point(round(quantity*materialFee,0),0));
	              sumComponentMaterialFee  = sumComponentMaterialFee + parseFloat(point(round(manHourFee,0),0));
	              sumComponentMaterialRestFee =sumComponentMaterialRestFee +parseFloat(point(round(materialRestFee,0),0));
			    //alert(sumComponentManHourFee+"::00::"+sumComponentManHourFee);
              sumComponentDefFee = sumComponentManHourFee+sumComponentMaterialFee-sumComponentMaterialRestFee;
           }
         }
         
//         fm.all("SumDefLoss2")[j].value = point(round(sumComponentDefFee,2),2);
//         fm.all("SumManHourFee2")[j].value = point(round(sumComponentManHourFee,2),2);
//         fm.all("SumMaterialFee2")[j].value = point(round(sumComponentMaterialFee,2),2);
         fm.all("SumDefLoss2")[j].value = point(round(sumComponentDefFee,0),0);
         fm.all("SumManHourFee2")[j].value = point(round(sumComponentManHourFee,0),0);
         fm.all("SumMaterialFee2")[j].value = point(round(sumComponentMaterialFee,0),0);
         
         sumComponentDefFee = 0 ;
         sumComponentManHourFee  = 0 ;
         sumComponentMaterialFee = 0 ;       
         sumPreDefLoss();
       }
     } else { 
       var sumComponentSumDefLoss=0; //小计
       var materialRestFee=0;
       //fm.prpLcarLossSumRest.value=0;
       sumComponentMaterialRestFee = 0;
       for(i=1;i<fm.all("prpLcomponentQuantity").length;i++)
       {
       	  
       	    quantity = parseFloat(fm.all("prpLcomponentQuantity")[i].value);
       	    manHourFee = parseFloat(fm.all("prpLcomponentManHourFee")[i].value);
       	    materialFee = parseFloat(fm.all("prpLcomponentMaterialFee")[i].value);
            materialRestFee = parseFloat(fm.all("prpLcomponentRestFee")[i].value);            
            SumDefLoss = parseFloat(fm.all("prpLcomponentSumDefLoss")[i].value);
        
    	   	if(isNaN(quantity)||quantity.length<1||quantity==''){
    	      quantity = 0;
    	    }
    	    if(isNaN(manHourFee)||manHourFee.length<1||manHourFee=='') {
    	      manHourFee = 0;
    	    }
    	    if(isNaN(materialFee)||materialFee.length<1||materialFee==''){
    	      materialFee = 0;
    	    }
    	    if(isNaN(materialRestFee)||materialRestFee.length<1||materialRestFee==''||fm.all("prpLcomponentIfRemain")[i].value == "1"){
    	      materialRestFee = 0;
    	    }
    	    if(isNaN(SumDefLoss)||SumDefLoss.length<1||SumDefLoss=='')
    	    {
    	        SumDefLoss = 0;
    	    }
//            sumComponentManHourFee   = sumComponentManHourFee + parseFloat(point(round(quantity*materialFee,2),2));
//            sumComponentMaterialFee  = sumComponentMaterialFee + parseFloat(point(round(manHourFee,2),2));
//            sumComponentMaterialRestFee =sumComponentMaterialRestFee +parseFloat(point(round(materialRestFee,2),2));
//            sumComponentSumDefLoss = sumComponentSumDefLoss +  parseFloat(point(round(SumDefLoss,2),2));             
    	    sumComponentManHourFee   = sumComponentManHourFee + parseFloat(point(round(quantity*materialFee,0),0));
            sumComponentMaterialFee  = sumComponentMaterialFee + parseFloat(point(round(manHourFee,0),0));
            sumComponentMaterialRestFee =sumComponentMaterialRestFee +parseFloat(point(round(materialRestFee,0),0));
            sumComponentSumDefLoss = sumComponentSumDefLoss +  parseFloat(point(round(SumDefLoss,0),0));             
       }  
//       fm.prpLcarLossSumCertainLoss.value = point(round(sumComponentSumDefLoss,2),2);
       fm.prpLcarLossSumCertainLoss.value = point(round(sumComponentSumDefLoss,0),0);
//       if(sumComponentMaterialRestFee==0){
//    	   sumComponentMaterialRestFee = fm.prpLcarLossSumRest.value;
//       }else{
//       }
       fm.prpLcarLossSumRest.value = sumComponentMaterialRestFee;
       
       var SumManager = parseFloat(fm.prpLcarLossSumManager.value);       
       if( isNaN(SumManager)||SumManager== ""){
            SumManager = 0;
       		fm.prpLcarLossSumManager.value = 0;
       }
       var sumManageFee = sumComponentSumDefLoss * (parseFloat(SumManager)/100);// 管理費計算
       //總費用計算＝配件費－殘值+運費+(-)管理費
//       var SumDefLoss2 = sumComponentSumDefLoss - sumComponentMaterialRestFee + parseFloat(fm.prpLcarLossSumTransFee.value) + sumManageFee;    
       var SumDefLoss2 = point(round(sumComponentSumDefLoss,0),0) - point(round(sumComponentMaterialRestFee,0),0) + point(round(parseFloat(fm.prpLcarLossSumTransFee.value),0),0) + point(round(sumManageFee,0),0);    
       
       //SumDefLoss2 = SumDefLoss2 * (1+parseFloat(SumManager)/100);
//       fm.SumDefLoss2.value = point(round(SumDefLoss2,2),2);
//       fm.SumDefLoss2.value = point(round(SumDefLoss2,0),0);
       fm.SumDefLoss2.value = SumDefLoss2;
       fm.prpLcarLossSumRest.value = sumComponentMaterialRestFee;
//       fm.SumManHourFee2.value = point(round(sumComponentManHourFee,2),2);
//       fm.SumMaterialFee2.value = point(round(sumComponentMaterialFee,2),2);
       fm.SumManHourFee2.value = point(round(sumComponentManHourFee,0),0);
       fm.SumMaterialFee2.value = point(round(sumComponentMaterialFee,0),0);
       fm.prpLcarLossSumRest.value = sumComponentMaterialRestFee;       
       sumPreDefLoss();
     }  
   }   
   return true;
  }
  function sumComponentFee1(obj) 
  { 
   var sumComponentDefFee = 0 ;//总换件定损费
   var sumComponentManHourFee  = 0 ; //总工时费
   var sumComponentMaterialFee = 0 ; //总材料费
   var sumComponentMaterialRestFee=0;
   var quantity = 0 ;
   var manHourFee = 0 ;
   var materialFee = 0 ;
   sumComponentMaterialRestFee = obj.value;
   if(isNaN(sumComponentMaterialRestFee)){
	   sumComponentMaterialRestFee = 0;
   }
   if(fm.all("SumDefLoss2")!=null){
     if(fm.all("SumDefLoss2").length>0){ 
       for(j=0;j<fm.all("SumDefLoss2").length;j++){
         for(i=1;i<fm.all("prpLcomponentQuantity").length;i++){
            if(j == fm.all("carLossComponentLossItemCode")[i].value){
         	    quantity = parseFloat(fm.all("prpLcomponentQuantity")[i].value);//更换数量
         	    manHourFee = parseFloat(fm.all("prpLcomponentManHourFee")[i].value);
         	    materialFee = parseFloat(fm.all("prpLcomponentMaterialFee")[i].value);
        	   	if(isNaN(quantity)||quantity.length<1){ 
        	      quantity = 0;
        	    }
        	    if(isNaN(manHourFee)||manHourFee.length<1) {
        	      manHourFee = 0;
        	    }
        	    if(isNaN(materialFee)||materialFee.length<1){ 
        	      materialFee = 0;
        	    }

//              sumComponentManHourFee   = sumComponentManHourFee + parseFloat(point(round(quantity*materialFee,2),2));
//              sumComponentMaterialFee  = sumComponentMaterialFee + parseFloat(point(round(manHourFee,2),2));
        	    sumComponentManHourFee   = sumComponentManHourFee + parseFloat(point(round(quantity*materialFee,0),0));
                sumComponentMaterialFee  = sumComponentMaterialFee + parseFloat(point(round(manHourFee,0),0));
        	    sumComponentDefFee = sumComponentManHourFee+sumComponentMaterialFee-sumComponentMaterialRestFee;
           }
         }
         
//         fm.all("SumDefLoss2")[j].value = point(round(sumComponentDefFee,2),2);
//         fm.all("SumManHourFee2")[j].value = point(round(sumComponentManHourFee,2),2);
//         fm.all("SumMaterialFee2")[j].value = point(round(sumComponentMaterialFee,2),2);
         fm.all("SumDefLoss2")[j].value = point(round(sumComponentDefFee,0),0);
         fm.all("SumManHourFee2")[j].value = point(round(sumComponentManHourFee,0),0);
         fm.all("SumMaterialFee2")[j].value = point(round(sumComponentMaterialFee,0),0);
         
         sumComponentDefFee = 0 ;
         sumComponentManHourFee  = 0 ;
         sumComponentMaterialFee = 0 ;       
         sumPreDefLoss();
       }
     } else { 
       var sumComponentSumDefLoss=0; //小计
       var materialRestFee=0;
      // fm.prpLcarLossSumRest.value=0;
       for(i=1;i<fm.all("prpLcomponentQuantity").length;i++)
       {
       	  
       	    quantity = parseFloat(fm.all("prpLcomponentQuantity")[i].value);
       	    manHourFee = parseFloat(fm.all("prpLcomponentManHourFee")[i].value);
       	    materialFee = parseFloat(fm.all("prpLcomponentMaterialFee")[i].value);
            SumDefLoss = parseFloat(fm.all("prpLcomponentSumDefLoss")[i].value);
        
    	   	if(isNaN(quantity)||quantity.length<1||quantity==''){
    	      quantity = 0;
    	    }
    	    if(isNaN(manHourFee)||manHourFee.length<1||manHourFee=='') {
    	      manHourFee = 0;
    	    }
    	    if(isNaN(materialFee)||materialFee.length<1||materialFee==''){
    	      materialFee = 0;
    	    }
    	    if(isNaN(SumDefLoss)||SumDefLoss.length<1||SumDefLoss=='')
    	    {
    	        SumDefLoss = 0;
    	    }
//            sumComponentManHourFee   = sumComponentManHourFee + parseFloat(point(round(quantity*materialFee,2),2));
//            sumComponentMaterialFee  = sumComponentMaterialFee + parseFloat(point(round(manHourFee,2),2));
//            sumComponentSumDefLoss = sumComponentSumDefLoss +  parseFloat(point(round(SumDefLoss,2),2));    
    	    sumComponentManHourFee   = sumComponentManHourFee + parseFloat(point(round(quantity*materialFee,0),0));
            sumComponentMaterialFee  = sumComponentMaterialFee + parseFloat(point(round(manHourFee,0),0));
            sumComponentSumDefLoss = sumComponentSumDefLoss +  parseFloat(point(round(SumDefLoss,0),0));             
       }  
//       fm.prpLcarLossSumCertainLoss.value = point(round(sumComponentSumDefLoss,2),2);
       fm.prpLcarLossSumCertainLoss.value = point(round(sumComponentSumDefLoss,0),0);
     //  var SumDefLoss2 = sumComponentSumDefLoss - sumComponentMaterialRestFee + parseFloat(fm.prpLcarLossSumTransFee.value);       
       var SumManager = parseFloat(fm.prpLcarLossSumManager.value);       
       if( isNaN(SumManager)||SumManager== ""){
            SumManager = 0;
       		fm.prpLcarLossSumManager.value = 0;
       }
       var sumManageFee = sumComponentSumDefLoss * (parseFloat(SumManager)/100);
//       var SumDefLoss2 = sumComponentSumDefLoss - sumComponentMaterialRestFee + parseFloat(fm.prpLcarLossSumTransFee.value) + sumManageFee;       
       var SumDefLoss2 = point(round(sumComponentSumDefLoss,0),0) - point(round(sumComponentMaterialRestFee,0),0) + point(round(parseFloat(fm.prpLcarLossSumTransFee.value),0),0) + point(round(sumManageFee,0),0);       
       
       
      // SumDefLoss2 = SumDefLoss2 * (1+parseFloat(SumManager)/100);
//       fm.SumDefLoss2.value = point(round(SumDefLoss2,2),2);    
//       fm.SumDefLoss2.value = point(round(SumDefLoss2,0),0);     
       fm.SumDefLoss2.value = SumDefLoss2;     
       fm.prpLcarLossSumRest.value = sumComponentMaterialRestFee;       
//       fm.SumManHourFee2.value = point(round(sumComponentManHourFee,2),2);
//       fm.SumMaterialFee2.value = point(round(sumComponentMaterialFee,2),2);
       fm.SumManHourFee2.value = point(round(sumComponentManHourFee,0),0);
       fm.SumMaterialFee2.value = point(round(sumComponentMaterialFee,0),0);
       fm.prpLcarLossSumRest.value = sumComponentMaterialRestFee;       
       sumPreDefLoss();
     }  
   }   
   return true;
  }
  
  
/**
 *@description 计算总的定损金额、总的核价金额、偏差定损金额
 *@param       无
 *@return      通过返回true,否则返回false
 */ 
 
 function sumCarPreDefLoss()
 {
 	 var sumRepairLoss     = 0 ;//修理
 	 var sumComponentLoss  = 0 ;//换件
 	 var sumRepairVerifyLoss     = 0 ;//修理
 	 var sumComponentVerifyLoss  = 0 ;//换件
 	 
 	 var sumPreDefLoss     = 0 ;
 	 var sumVerifyLoss     = 0 ;
 	 var firstDefLoss      = 0 ; //初次定损金额
 	 var warpDefLoss       = 0 ; //偏差定损金额
   var logStatus = ""; //任务状态 
     
try{
     logStatus = fm.prpLverifyLossDtoStatus.value;
}catch(ex){logStatus="";}	   

   sumRepairLoss = parseFloat(fm.SumDefLoss1.value);
   sumComponentLoss = parseFloat(fm.SumDefLoss2.value);
   
   sumRepairVerifyLoss = parseFloat(fm.SumVerifyLoss1.value);
   sumComponentVerifyLoss = parseFloat(fm.SumVerifyLoss2.value);
  
 	 if(isNaN(sumRepairLoss))  sumRepairLoss = 0 ;
 	 if(isNaN(sumRepairVerifyLoss))  sumRepairVerifyLoss = 0 ;
   if(isNaN(sumComponentLoss)) sumComponentLoss = 0 ;
   if(isNaN(sumComponentVerifyLoss)) sumComponentVerifyLoss = 0 ;
 	   
   sumPreDefLoss = parseFloat(sumRepairLoss)+ parseFloat(sumComponentLoss); 
   sumVerifyLoss = parseFloat(sumRepairVerifyLoss)+ parseFloat(sumComponentVerifyLoss); 
  
   //总的定损金额
   //fm.prpLcarLossSumCertainLoss.value = point(round(sumPreDefLoss,2),2);

   if(logStatus!="1" && logStatus!="2"){
     //总的核价金额
//     fm.prpLcarLossSumVerifyLoss.value = point(round(sumVerifyLoss,2),2);
	   fm.prpLcarLossSumVerifyLoss.value = point(round(sumVerifyLoss,0),0);
   }else{
     //总的核价金额
//     fm.prpLcarLossSumVerifyLoss.value = point(round(sumPreDefLoss,2),2);   
     fm.prpLcarLossSumVerifyLoss.value = point(round(sumPreDefLoss,0),0);   
   }     
   
   firstDefLoss = parseFloat(fm.prpLverifyLossFirstDefLoss.value);
  
 	 if(isNaN(firstDefLoss))  firstDefLoss = 0 ;
 	 
 	 warpDefLoss = firstDefLoss - sumVerifyLoss;
 	 
 	 //偏差定损金额
 	 //reasion:这个偏差金额重新计算很奇怪，那初次定损怎么计算进去的呢？
 	 
//   fm.prpLverifyLossWarpDefLoss.value = point(round(warpDefLoss,2),2);
 	fm.prpLverifyLossWarpDefLoss.value = point(round(warpDefLoss,0),0);
 }      
 
 
/**
 *@description 设值页面的一些初始化信息
 *@param       无
 *@return      通过返回true,否则返回false
 */
function initSet()
{  
  var sumloss    = "prpLpropSumLoss";//受损金额
  var sumreject  = "prpLpropSumReject";//残值
  var sumdefloss = "prpLpropSumDefLoss";//定损金额
  var sumvalue;
  var sumallvalue;    
  
  //增加保费是否实收提示
  var payFee = fm.prpLcertainLossPayFee.value;
  var errorMessage = "";
  if(payFee==-1){
      errorMessage=errorMessage+i18n.certainLoss.policyPremiumNoPay;  // 此保单保费未缴,请慎重处理！！
																		// \n
  }else if(payFee==0){
      errorMessage=errorMessage+i18n.certainLoss.policyPremiumPay;  // 此保单已缴未缴全,请慎重处理！！！
  }
  if(errorMessage != ""){
      alert(errorMessage);
  }
  
  if(fm.all(sumloss)!=null){
    for(i=1;i<fm.all(sumloss).length;i++)
    {
      sumvalue = fm.all(sumloss)[i].value; 
  
      if(isNaN(sumvalue)||sumvalue.length<1){
        sumvalue = 0;
      }
      if(isNaN(sumallvalue)||sumallvalue.length<1){
      sumallvalue = 0;
      	}
      sumallvalue = parseFloat(sumallvalue) + parseFloat(sumvalue);
    } 
//    fm.prpLpropSumSumLoss.value=point(round(sumallvalue,2),2); 
    fm.prpLpropSumSumLoss.value=point(round(sumallvalue,0),0); 
  }

  sumallvalue=0;
  
  if(fm.all(sumloss)!=null){
    for(i=1;i<fm.all(sumreject).length;i++)
    {
      sumvalue = fm.all(sumreject)[i].value;  
  
      if(isNaN(sumvalue)||sumvalue.length<1)
      sumvalue = 0;
      if(isNaN(sumallvalue)||sumallvalue.length<1)
      sumallvalue = 0;
      
      sumallvalue = parseFloat(sumallvalue) + parseFloat(sumvalue);
    } 
//    fm.prpLpropSumSumReject.value=point(round(sumallvalue,2),2); 
    fm.prpLpropSumSumReject.value=point(round(sumallvalue,0),0); 
  }

  sumallvalue=0;
  
  if(fm.all(sumloss)!=null){
    for(i=1;i<fm.all(sumdefloss).length;i++)
    {
      sumvalue = fm.all(sumdefloss)[i].value;  
      if(isNaN(sumvalue)||sumvalue.length<1) {
        sumvalue = 0;  
      }
      if(isNaN(sumallvalue)||sumallvalue.length<1){
        sumallvalue = 0; 
      }
      sumallvalue = parseFloat(sumallvalue) + parseFloat(sumvalue);
    }  
//    fm.prpLpropSumSumDefLoss.value=point(round(sumallvalue,2),2); 
    fm.prpLpropSumSumDefLoss.value=point(round(sumallvalue,0),0); 
  }
  
  sumRepairFee(); 
  sumComponentFee();
  sumPersonLossFee();
  sumPreDefLoss();  
  if(fm.nodeType.value=='certa' && fm.prpLcarLossLossItemName==undefined){
      alert(i18n.certainLoss.chargedTaskcorrespondFee);  // 该定损任务对应的车辆信息不存在，请注销当前定损任务！
  }
  return true;
} 
 

/**
 *@description 设值页面的一些初始化信息
 *@param       无
 *@return      通过返回true,否则返回false
 */
function initSetForCheck()
{  
  var sumloss    = "prpLpropSumLoss";
  var sumreject  = "prpLpropSumReject";
  var sumdefloss = "prpLpropSumDefLoss";
  var sumvalue;
  var sumallvalue;    
  if(fm.all(sumloss)!=null){
    for(i=1;i<fm.all(sumloss).length;i++)
    {
      sumvalue = fm.all(sumloss)[i].value; 
  
      if(isNaN(sumvalue)||sumvalue.length<1){
        sumvalue = 0;
      }
      if(isNaN(sumallvalue)||sumallvalue.length<1)
      sumallvalue = 0;
      sumallvalue = parseFloat(sumallvalue) + parseFloat(sumvalue);
    } 
//    fm.prpLpropSumSumLoss.value=point(round(sumallvalue,2),2); 
    fm.prpLpropSumSumLoss.value=point(round(sumallvalue,0),0); 
  }
  sumallvalue=0;
  
  if(fm.all(sumloss)!=null){
    for(i=1;i<fm.all(sumreject).length;i++)
    {
      sumvalue = fm.all(sumreject)[i].value;  
  
      if(isNaN(sumvalue)||sumvalue.length<1)
      sumvalue = 0;
      if(isNaN(sumallvalue)||sumallvalue.length<1)
      sumallvalue = 0;
      
      sumallvalue = parseFloat(sumallvalue) + parseFloat(sumvalue);
    } 
//    fm.prpLpropSumSumReject.value=point(round(sumallvalue,2),2); 
    fm.prpLpropSumSumReject.value=point(round(sumallvalue,0),0); 
  }
  sumallvalue=0;
  
  if(fm.all(sumloss)!=null){
    for(i=1;i<fm.all(sumdefloss).length;i++)
    {
      sumvalue = fm.all(sumdefloss)[i].value;  
      if(isNaN(sumvalue)||sumvalue.length<1) {
        sumvalue = 0;  
      }
      if(isNaN(sumallvalue)||sumallvalue.length<1){
        sumallvalue = 0; 
      }
      sumallvalue = parseFloat(sumallvalue) + parseFloat(sumvalue);
    }  
//    fm.prpLpropSumSumDefLoss.value=point(round(sumallvalue,2),2); 
    fm.prpLpropSumSumDefLoss.value=point(round(sumallvalue,0),0); 
  }
  
  /* 以下这段不知道作用，所以在定损修改後，查勘报出错，但是在查勘处，没有发现这些值在哪里是需要计算的。
  sumRepairFee(); 
  sumComponentFee();
  sumPersonLossFee();
  sumPreDefLoss();  
  */
  return true;
} 

/**
 *@description 计算总的定损金额
 *@param       无
 *@return      通过返回true,否则返回false
 */ 
 
 function sumPreDefLoss()
 {
 	var sumPropLoss       = 0 ;//财产
 	var sumRepairLoss     = 0 ;//修理
 	var sumComponentLoss  = 0 ;//换件
 	var sumPersonLoss=0 ;      //人员
 
 	var sumPreDefLoss     = 0 ;
  if(fm.prpLpropSumSumDefLoss!=undefined){
 	sumPropLoss = fm.prpLpropSumSumDefLoss.value;
 	}
 	if(isNaN(sumPropLoss))
 	  sumPropLoss = 0 ;
 	if(fm.all("SumDefLoss1")!=null){  
    if(fm.all("SumDefLoss1").length>0){
      for(i=0;i<fm.all("SumDefLoss1").length;i++){
        sumRepairLoss = parseFloat(sumRepairLoss) +parseFloat(fm.all("SumDefLoss1")[i].value);
      }
    } else { 	     
      sumRepairLoss = fm.SumDefLoss1.value;
    }
  }
 	if(isNaN(sumRepairLoss)||sumRepairLoss.length<1)
 	   sumRepairLoss = 0 ;
 	   
 	if(fm.all("SumDefLoss2")!=null){  
   	if(fm.all("SumDefLoss2").length>0){
      for(i=0;i<fm.all("SumDefLoss2").length;i++){
        sumComponentLoss = parseFloat(sumComponentLoss) +parseFloat(fm.all("SumDefLoss2")[i].value);
      }
    } else { 	     
      sumComponentLoss = fm.SumDefLoss2.value;
    }
  }  
 	if(isNaN(sumComponentLoss)||sumComponentLoss.length<1)
 	   sumComponentLoss = 0 ;
 	   
 	//sumPersonLoss = fm.sumPersonLoss.value;//人员定损总和未有
 	//if(isNaN(sumPersonLoss))
 	//  sumPersonLoss = 0 ;  
 	  	//alert(sumRepairLoss+"::00::"+sumPropLoss+"::00::"+sumComponentLoss+"::00::"+sumPersonLoss);  
 	  	if (fm.all("SumDefLoss1") != null)
 	  	{
 	  		sumRepairLoss = fm.all("SumDefLoss1").value;
 	  	}
 	sumPreDefLoss =   parseFloat(sumRepairLoss)+ parseFloat(sumPropLoss)+ parseFloat(sumComponentLoss)+parseFloat(sumPersonLoss);
 	
 	//reasion:人伤的定损总金额没影子了
 	if (fm.all("prpLpersonSumDefLossSum") != null)
 	{
 		 var vlength = fm.all("prpLpersonSumLossSum").length; 
 		 var introw=0;
 		 sumPersonLoss =0;
 		 for (introw=1;introw<vlength;introw++){
 		 	
 		 	sumPersonLoss = sumPersonLoss+ parseFloat(fm.prpLpersonSumLossSum[introw].value);
 		}
		sumPreDefLoss = sumPersonLoss //设置定损人员的总金额=人伤总额
	}
 	
 	if (fm.all("prpLcarLossSumCertainLoss") != null)
 	{
		fm.all("prpLcarLossSumCertainLoss").value = pointTwo(sumPreDefLoss);
	}
            try{
			//fm.prpLcarLossSumCertainLoss.value=point(round(sumPreDefLoss,2),2); 
			
	             }catch(ex1)

	            {
	                return false;
	            }


 	//fm.prpLverifyLossSumPreDefLoss.value = sumPreDefLoss; 为什么要注销掉这句话，导致定损总金额为0了。
 	 //增加定损总金额和核损总金额的计算。
 	 
 	fm.prpLverifyLossSumPreDefLoss.value = sumPreDefLoss
 	fm.prpLverifyLossSumDefLoss.value = sumPreDefLoss
 	fm.prpLverifyLossWarpDefLoss.value= sumPreDefLoss
//reason:增加计算定损偏差的金额 
	if (fm.all("SumDefLoss1")!=null&&fm.all("SumDefLoss2")!=null){
	  fm.prpLverifyLossWarpDefLoss.value=parseFloat(fm.SumDefLoss1.value)+parseFloat(fm.SumDefLoss2.value);
	}
 }  
 
/**
 @description 计算人员伤亡费用清单核损金额
 @param       Field: 触发域 
 @return      无     
 @see         calculateAll
*/   
function calSumPersonDefLoss(field){
	var fieldName=field.name;
	var index =  $("input[name='"+fieldName+"']").index(field);
	if(index>-1){
		var prpLpersonSumLoss = $("input[name='prpLpersonSumLoss']").get(index);
		var sumLoss = prpLpersonSumLoss.value;
		if(!$.isNumeric(sumLoss)){
			sumLoss = 0;
			prpLpersonSumLoss.value = 0;
		}
		var prpLpersonSumReject = $("input[name='prpLpersonSumReject']").get(index);
		var sumReject = prpLpersonSumReject.value;
		if(!$.isNumeric(sumReject)){
			sumReject = 0;
			prpLpersonSumReject.value = 0;
		}
		var prpLpersonSumDefLoss = $("input[name='prpLpersonSumDefLoss']").get(index);
		var sumDefLoss = parseFloat(sumLoss) - parseFloat(sumReject); 
		prpLpersonSumDefLoss.value = point(round(sumDefLoss,0),0);
		sumPersonLossFee();
  }
} 
 
  /**
   *@description 汇总人员伤亡费用计算 
   *             对人伤的受损金额，剔除金额，核损金额进行汇总 
   *@param       无
   *@return      无
   */  
  function sumPersonLossFee()  
  { 
    /**********************/
    var prpLpersonSumLossSum = 0 ;        //受损金额
    var prpLpersonSumRejectSum  = 0 ;     //剔除金额
    var prpLpersonSumDefLossSum = 0 ;     //核损金额 
    var sumLoss = 0 ;
    var sumReject = 0 ;
    var sumDefLoss = 0 ; 
    var vlength = 0; 
    //fm.all("SumDefLoss2").length>0
    if(isNaN(fm.all("prpLpersonSumLossSum"))){
      vlength = fm.all("prpLpersonSumLossSum").length; 
    }  
    //var vlength = fm.all("prpLpersonSumLossSum").length;  
    if(vlength>0){   
      for(j=1;j<fm.all("prpLpersonSumLossSum").length;j++){
        for(i=1;i<fm.all("prpLpersonSumLoss").length;i++){  
          if(j == fm.all("personSerialNo")[i].value){  
            sumLoss = parseFloat(fm.all("prpLpersonSumLoss")[i].value); 
            sumReject = parseFloat(fm.all("prpLpersonSumReject")[i].value); 
            sumDefLoss = parseFloat(fm.all("prpLpersonSumDefLoss")[i].value); 
           	if(isNaN(sumLoss)||sumLoss.length<1){
              quantity = 0;
            } 
            if(isNaN(sumReject)||sumReject.length<1){
              manHourFee = 0;
            }
            if(isNaN(sumDefLoss)||sumDefLoss.length<1){ 
              materialFee = 0;
            }
//            prpLpersonSumLossSum   = prpLpersonSumLossSum + parseFloat(point(round(sumLoss,2),2));
//            prpLpersonSumRejectSum  = prpLpersonSumRejectSum + parseFloat(point(round(sumReject,2),2));
//            prpLpersonSumDefLossSum = prpLpersonSumDefLossSum+parseFloat(point(round(sumDefLoss,2),2));
            prpLpersonSumLossSum   = prpLpersonSumLossSum + parseFloat(point(round(sumLoss,0),0));
            prpLpersonSumRejectSum  = prpLpersonSumRejectSum + parseFloat(point(round(sumReject,0),0));
            prpLpersonSumDefLossSum = prpLpersonSumDefLossSum+parseFloat(point(round(sumDefLoss,0),0));
          }    
        }
//        fm.all("prpLpersonSumLossSum")[j].value = point(round(prpLpersonSumLossSum,2),2);
//        fm.all("prpLpersonSumRejectSum")[j].value = point(round(prpLpersonSumRejectSum,2),2);
//        fm.all("prpLpersonSumDefLossSum")[j].value = point(round(prpLpersonSumDefLossSum,2),2); 
        fm.all("prpLpersonSumLossSum")[j].value = point(round(prpLpersonSumLossSum,0),0);
        fm.all("prpLpersonSumRejectSum")[j].value = point(round(prpLpersonSumRejectSum,0),0);
        fm.all("prpLpersonSumDefLossSum")[j].value = point(round(prpLpersonSumDefLossSum,0),0); 
        prpLpersonSumLossSum = 0 ;        //受损金额
        prpLpersonSumRejectSum  = 0 ;     //剔除金额
        prpLpersonSumDefLossSum = 0 ;     //核损金额 
        sumPreDefLoss();   
      } 
      
    } else if(vlength==0){
    } else {
      for(i=1;i<fm.all("prpLpersonSumLoss").length;i++)
      {
          sumLoss = parseFloat(fm.all("prpLpersonSumLoss")[i].value); 
          sumReject = parseFloat(fm.all("prpLpersonSumReject")[i].value); 
          sumDefLoss = parseFloat(fm.all("prpLpersonSumDefLoss")[i].value); 
       	if(isNaN(sumLoss)||sumLoss.length<1){
          quantity = 0;
        }
        if(isNaN(sumReject)||sumReject.length<1){
          manHourFee = 0;
        }
        if(isNaN(sumDefLoss)||sumDefLoss.length<1){ 
          materialFee = 0;
        }
//        prpLpersonSumLossSum   = prpLpersonSumLossSum + parseFloat(point(round(sumLoss,2),2));
//        prpLpersonSumRejectSum  = prpLpersonSumRejectSum + parseFloat(point(round(sumReject,2),2));
//        prpLpersonSumDefLossSum = prpLpersonSumDefLossSum+parseFloat(point(round(sumDefLoss,2),2));
        prpLpersonSumLossSum   = prpLpersonSumLossSum + parseFloat(point(round(sumLoss,0),0));
        prpLpersonSumRejectSum  = prpLpersonSumRejectSum + parseFloat(point(round(sumReject,0),0));
        prpLpersonSumDefLossSum = prpLpersonSumDefLossSum+parseFloat(point(round(sumDefLoss,0),0));
      } 
//      fm.prpLpersonSumLossSum.value = point(round(prpLpersonSumLossSum,2),2);
//      fm.prpLpersonSumRejectSum.value = point(round(prpLpersonSumRejectSum,2),2);
//      fm.prpLpersonSumDefLossSum.value = point(round(prpLpersonSumDefLossSum,2),2); 
      fm.prpLpersonSumLossSum.value = point(round(prpLpersonSumLossSum,0),0);
      fm.prpLpersonSumRejectSum.value = point(round(prpLpersonSumRejectSum,0),0);
      fm.prpLpersonSumDefLossSum.value = point(round(prpLpersonSumDefLossSum,0),0); 
      sumPreDefLoss(); 
    }
    
  }
  
   
  /**
   *@description 查看制定的文件
   *@param       fileName，businessNo，uploadFileName
   */
  function viewFile(fileName,businessNo){
    //document.fm.action="/claim/DAA/certify/download.jsp?fileName="+fileName+"&businessNo="+businessNo+"&uploadFileName="+uploadFileName;
    //document.fm.submit();
    window.open("/claim/pages/DAA/certify/CertifyViewFile.jsp?fileName="+fileName+"&businessNo="+businessNo,businessNo,"resizable=0,scrollbars=1,width=800,height=600");
  }



/**
 *@description 根据按钮状态保存报案数据
 *@param       this
 *@param       保存状态
 *@return      通过返回true,否则返回false
 */	  
function fileSave(field,saveType)
{   
  //reason: ValidateData.js中的校验不起作用时，因为没有调用校验方法
 
 if(!validateForm(fm,'Certify_Data'))
  {
    return false;
  }
  
  //reason:当按下某一按钮时请将这个按钮变灰，否则用户可能多按引发错误
  field.disabled = true;
  
  fm.submit();
  //return true;   	 
}

/**
 *@description 根据按钮状态保存报案数据
 *@param       this
 *@param       保存状态
 *@return      通过返回true,否则返回false
 */	  
function fileSavePop(field,saveType)
{   
  //reason: ValidateData.js中的校验不起作用时，因为没有调用校验方法
 
 if(!validateForm(fm,'Certify_Data'))
  {
    return false;
  }
  
  //reason:当按下某一按钮时请将这个按钮变灰，否则用户可能多按引发错误
  field.disabled = true;
  fm.target="_self";
  fm.submit(); 
  //return true;   	
}

//reason: 统一使用ClaimPub.js 里的方法，便於维护
/*
//按钮单击事件，用於相同保单号码多报案的显示
function buttonOnClick(strSubPageCode)
{
   var sameCount = parseInt(fm.PerilCount.value);
  
  if (sameCount <1) 
  {
  	fm.button_Peril_Open_Context.disabled = true;
  	return;
  }
  showSubPage1(strSubPageCode);
 
}
//显示输入框
//leftMove 默认值0，坐标左移leftMove
function showSubPage1(spanID,leftMove) 
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
*/

/**
 @author      孙成刚
 @description 使一个输入域设置为只读
 @param       iElement: 域   
 @return      无
 */
function setReadonlyOfElement(iElement)
{
  if (iElement.type=="text")
  {
    if(iElement.onblur!=null)
    {
      //iElement.oldOnblur = getFunctionName(iElement.onblur.toString());
    }
    //iElement.onblur = functionDoNothing;

    if(iElement.ondblclick!=null)
    {
      //iElement.oldOndblclick = getFunctionName(iElement.ondblclick.toString());
    }
    //iElement.ondblclick = functionDoNothing;

    if(iElement.onfocus!=null)
    {
      //iElement.oldOnfocus = getFunctionName(iElement.onfocus.toString());
    } 
    //iElement.onfocus = functionDoNothing;
    
    iElement.readOnly  = true;
    iElement.value     = "";
    iElement.className = "readonly";
  }
}


/**
 @author      孙成刚
 @description 使一个输入域设置为可输入
 @param       iElement: 域   
 @return      无
 */
function undoSetReadonlyOfElement(iElement)
{
  iElement.readOnly = false;  
  iElement.className = 'common';
}   
                           
/**
 @author      孙成刚
 @description 设置该页属性（只读或可写）
 @param       无
 @return      无
 */
function setPropertyOfPage(Field)
{   
  
  var fieldname=Field.name;
  var findex=0; 
  
  for(i=1;i<fm.all(fieldname).length;i++)
  {
    if( fm.all(fieldname)[i] == Field )
    {
      findex=i;
      break;
    }
  }    
  
  var prpLpersonPayPersonType = parseInt(Field.value);
     
  //关联只能是伤残亡的，只有伤残才能輸入伤残比例
  if(prpLpersonPayPersonType ==1 || prpLpersonPayPersonType==4 || prpLpersonPayPersonType==5)
  {
    setReadonlyOfElement(fm.prpLpersonRelatePersonNo[findex]);   
    if(prpLpersonPayPersonType==5)  //人员类型为亡的
    {
      setReadonlyOfElement(fm.prpLpersonLossRate[findex]);   
    } 
    else
    {
      undoSetReadonlyOfElement(fm.prpLpersonLossRate[findex]);           
    } 
  }
  else
  {
    undoSetReadonlyOfElement(fm.prpLpersonRelatePersonNo[findex]);   
    setReadonlyOfElement(fm.prpLpersonLossRate[findex]);  
  }
}

/**
 @author      孙成刚
 @description 校验关联人员
 @param       Field: 触发域  
 @return      boolean: 合法为true,否则为false  
 @see         UICommon.js#isEmpty、checkInteger 
 */ 
function checkRelatePersonNo(Field)
{
  /*             
  if(isEmpty(Field))
    return true;
    
  if (checkInteger(Field,1,100)==false)
  {
    return false;
  }    
  */
  /* 
  var fieldname=Field.name; 
  var findex=0;  
  var i = 0; 
  var j = 0; 
  for(i=1;i<fm.all(fieldname).length;i++)
  {
    if( fm.all(fieldname)[i] == Field )
    {
      findex=i;
      break; 
    }
  }    
  var bFind = false;
  for(j=1;j<fm.all("prpLpersonSerialNo").length;j++)
  {
    if(fm.all("prpLpersonSerialNo")[j].value == Field.value )
    {    
      if(fm.prpLpersonPayPersonType[j].value!="1"
        &&fm.prpLpersonPayPersonType[j].value!="4"
        &&fm.prpLpersonPayPersonType[j].value!="5") {
      	  alert("不能关联伤残亡以外的人员类型,请重新输入！");
      	  Field.focus();   
      	  Field.select();  
      	  return false;  
      }     
      bFind=true; 
  		break;
    } 
  }
	if(bFind==false)
	{
	  alert("该关联人员不存在,请重新输入！");
	  Field.focus();
	  Field.select(); 
	  return false;
  }    
  */    
  return true;   
}



/**
 *@description 弹出报案的画面
 *@param       无
 *@return      通过返回true,否则返回false
 */
function relateRegist(){	
    var registNo = fm.RegistNo.value;
	var linkURL = "/claim/regist/registFinishQueryList.do?prpLregistRegistNo="
	                  +registNo+"&editType=SHOW&riskCode="+fm.riskcode.value;
	//当不是从弹出窗口弹出窗口时，弹出窗口显示【关闭】按钮而不是【退回】按钮 
    if(opener==undefined){
	    linkURL=linkURL+"&ifclose=true";
    }
    var newWindow = window.open(linkURL,"NewWindow","width=640,height=500,top=0,left=0,toolbar=yes,location=no,directories=no,menubar=no,scrollbars=yes,resizable=yes,status=no");
} 

/**
 *@description 弹出查勘的画面
 *@param       无
 *@return      通过返回true,否则返回false
 */
function relateCheck(){	  
    var registNo = fm.RegistNo.value;
	var linkURL = "/claim/check/checkFinishQueryList.do?prpLcheckCheckNo="
	                  +registNo+"&editType=SHOW&riskCode="+fm.riskcode.value;
	//当不是从弹出窗口弹出窗口时，弹出窗口显示【关闭】按钮而不是【退回】按钮
    if(opener==undefined){
	    linkURL=linkURL+"&ifclose=true";
    }
    var newWindow = window.open(linkURL,"NewWindow","width=640,height=500,top=0,left=0,toolbar=yes,location=no,directories=no,menubar=no,scrollbars=yes,resizable=yes,status=no");
} 
//reason: 统一使用ClaimPub.js 里的方法，便於维护
/**
 *@description 弹出关联页面
 *@param       无
 *@return      通过返回true,否则返回false
 */
 /*
function relate(){	
		var policyNo = fm.PolicyNo.value;
		var registNo = fm.RegistNo.value;
    var newWindow = window.open("/claim/RelateBusinessNo.do?policyNo=" +policyNo+ "&registNo=" +registNo,"NewWindow","width=640,height=500,top=0,left=0,toolbar=yes,location=no,directories=no,menubar=no,scrollbars=yes,resizable=yes,status=no");
} 
*/

/**
 *@description 根据报价，管理费率计算零部件的价格
 *@param       无
 *@return      通过返回true,否则返回false
 */
 
 function getMaterialFee(field,ext) 
 {  
  var fieldname=field.name; 
  var i = 0;
  var findex=0;       
  var manHourFee;
  var materialFee; 
  var sumDefLoss;
  var ManHour;
  var Quantity;

  for(i=1;i<fm.all(fieldname).length;i++)
  {
    if( fm.all(fieldname)[i] == field )
    {
      findex=i;
      break;
    }  
  } 
  if(fieldname=="prpLcomponentQuotedPrice"){ 
    QuotedPrice = parseFloat(field.value);   
    MaterialFee = QuotedPrice*(1+fm.prpLcarLossSumManageFeeRate.value/100);
//    fm.all("prpLcomponentMaterialFee")[findex].value = point(round(MaterialFee,2),2);
    fm.all("prpLcomponentMaterialFee")[findex].value = point(round(MaterialFee,0),0);
    fm.all("prpLcomponentMaterialFee")[findex].onblur();
  }else{
    for( i = 1; i < fm.all("prpLcomponentMaterialFee").length; i++ ) { 
      QuotedPrice = parseFloat(fm.all("prpLcomponentQuotedPrice")[i].value); 
      MaterialFee = QuotedPrice*(1+field.value/100); 
//      fm.all("prpLcomponentMaterialFee")[i].value = point(round(MaterialFee,2),2);
      fm.all("prpLcomponentMaterialFee")[i].value = point(round(MaterialFee,0),0);
      fm.all("prpLcomponentMaterialFee")[i].onblur(); 
    }
  }
  return true;         
 } 

/**
 *@description 设置伤情类别得隐含域
 *@param       无
 *@return      通过返回true,否则返回false
 */
function woundCodeChange(field)
{
  var fieldname=field.name; 
  var i = 0;
  var findex=0;       
  var manHourFee;
  var materialFee; 
  var sumDefLoss;
  var ManHour;
  var Quantity;

  for(i=1;i<fm.all(fieldname).length;i++)
  {  
    if( fm.all(fieldname)[i] == field )
    {
      findex=i;
      break;
    }  
  }     
  
  //更改隐含项的值
  if(field.checked==true){
    fm.all(fieldname+"Txt")[findex].value = "1";
  } else {    
    fm.all(fieldname+"Txt")[findex].value = "0"; 
  }      
  return true; 
  
  
} 
  
/**
 @author      理赔组
 @description 修理/换件费用清单汇总
 @param       无 
 @return      boolean: 合法为true,否则为false  
 @see         UICommon.js#point、round
 @see         UIMulLine.js#private_getRowsCount
*/   
function calculateAll() 
{   
  var i = 0;  
  var materialFeeL   = 0; //计算车身划痕险损失费用合计
  var manHourFee     = 0;
  var materialFee    = 0;
  var sumManHourFee  = 0;
  var sumMaterialFee = 0; 
  var SumDefLoss     = 0;
  var TotalSumDefLoss     = 0;
  var ManHour; 
  var Quantity;

      var sumComponentVerpCompPrice = 0 ; //换件费合计
     var sumManageFee = 0; //管理费 
     var sumVerifyManageFee = 0; //总公司核定管理费
     var sumTax = 0;       //税金   
     var sumTransFee = 0;  //运费
     var sumRestFee = 0;   //残值合计
     var quantity = 0 ;     
     var verpCompPrice = 0 ; 
  //修理费用清单汇总  

  if(fm.prpLrepairFeeCompCode.length>0){
    for(i=1;i<fm.prpLrepairFeeCompCode.length;i++) 
    {        
      ManHour     = parseFloat(fm.all("prpLrepairFeeManHour")[i].value); 
      manHourFee  = parseFloat(fm.all("prpLrepairFeeManHourUnitPrice")[i].value);
      SumDefLoss  = parseFloat(fm.all("prpLrepairFeeSumDefLoss")[i].value); 
      
      if(isNaN(ManHour))
        ManHour = 0;      
      if(isNaN(manHourFee)) 
        manHourFee = 0;
      if(isNaN(SumDefLoss))
        SumDefLoss = 0;
          


      sumManHourFee  = sumManHourFee+manHourFee*ManHour;
      //sumMaterialFee = sumMaterialFee + materialFee;
      TotalSumDefLoss = TotalSumDefLoss + SumDefLoss;

      //计算车身划痕险损失费用合计
      if(fm.prpLrepairFeeKindCode[i].value=="L")
        materialFeeL = materialFeeL + materialFee; 
    }

    if(materialFeeL>5000) 
    {
      alert(i18n.certainLoss.scratchesLossDanger);  // 车身划痕损失险修理费用最大为5000元!
      return false;
    }       
//    fm.SumManHourFee1.value  = point(round(sumManHourFee,2),2);        
//    fm.SumDefLoss1.value = point(round(sumManHourFee,2),2);          
    fm.SumManHourFee1.value  = point(round(sumManHourFee,0),0);        
    fm.SumDefLoss1.value = point(round(sumManHourFee,0),0);       

  } else{
    fm.SumManHourFee1.value  = 0.00;        
    fm.SumMaterialFee1.value = 0.00;
    fm.SumDefLoss1.value = 0.00; 

  }
        
  sumManHourFee  = 0;
  sumMaterialFee = 0; 
          
  //换件费用清单汇总      
  if(fm.prpLcomponentCompName.length>0)
  {
    for(i=1;i<fm.prpLcomponentCompName.length;i++) 
    {               
      manHourFee  = parseFloat(fm.all("prpLcomponentManHourFee")[i].value);
      materialFee = parseFloat(fm.all("prpLcomponentMaterialFee")[i].value);
      Quantity    = parseInt(fm.all("prpLcomponentQuantity")[i].value); 
      SumDefLoss  = parseFloat(fm.all("prpLcomponentSumDefLoss")[i].value);

      if(isNaN(Quantity))
        Quantity = 0;
      if(isNaN(manHourFee))
        manHourFee = 0;
      if(isNaN(materialFee))
        materialFee = 0;
      if(isNaN(SumDefLoss))
        SumDefLoss = 0;
                       
      sumManHourFee   = sumManHourFee + manHourFee;
      sumMaterialFee  = sumMaterialFee + materialFee*Quantity;
      TotalSumDefLoss = TotalSumDefLoss + SumDefLoss;          
    
    }  
    
    //if(TotalSumDefLoss>3000 && fm.EscapeFlag.value=='2')
    if(TotalSumDefLoss>3000)
    {       
      //alert("该赔案为互碰自赔案,累计损失金额不能超过3000元！");
      return false;      
    }   
        
//    fm.SumManHourFee2.value  = point(round(sumManHourFee,2),2);        
//    fm.SumMaterialFee2.value = point(round(sumMaterialFee,2),2);     
    fm.SumManHourFee2.value  = point(round(sumManHourFee,0),0);        
    fm.SumMaterialFee2.value = point(round(sumMaterialFee,0),0);      
//===============================
     sumManageFee = parseFloat(fm.all("prpLcarLossSumManager").value); //管理费 
     sumVeriManageFee = parseFloat(fm.all("prpLcarLossSumVeriManager").value); //总公司核定管理费
     sumTax = 0;//parseFloat(fm.all("prpLcarLossSumTax").value);       //税金   
     sumTransFee = 0;//parseFloat(fm.all("prpLcarLossSumTransFee").value);  //运费
     sumRestFee = parseFloat(fm.all("prpLcarLossSumRest").value);   //残值合计
     
     if(isNaN(sumManageFee)) sumManageFee = 0;
     if(isNaN(sumVeriManageFee)) sumVeriManageFee = 0;
     if(isNaN(sumTax)) sumTax = 0;
     if(isNaN(sumTransFee)) sumTransFee = 0;
     if(isNaN(sumRestFee)) sumRestFee = 0;

 
     if(sumVeriManageFee!=0)
       sumManageFee = sumVeriManageFee;
         
     for(i=1;i<fm.all("prpLcomponentQuantity").length;i++)
     {
   	    quantity = parseFloat(fm.all("prpLcomponentQuantity")[i].value);   	    
   	    verpCompPrice = parseFloat(fm.all("prpLcomponentMaterialFee")[i].value);
   	
      	 if(isNaN(quantity)) quantity = 0;
        
        if(isNaN(verpCompPrice)) verpCompPrice = 0; 
          
//        sumComponentVerpCompPrice   = sumComponentVerpCompPrice + parseFloat(point(round(quantity*verpCompPrice,2),2));   
        sumComponentVerpCompPrice   = sumComponentVerpCompPrice + parseFloat(point(round(quantity*verpCompPrice,0),0));        
     } 
     
     //换件费合计 = 核价合计 + 管理费 + 税金 + 运费 – 残值合计(核价合计 = 每一个配件的核价 × 数量之和)
     sumComponentVerpCompPrice = sumComponentVerpCompPrice + sumManageFee + sumTax + sumTransFee - sumRestFee;
     
     //换件费合计
     //alert("计算fm.SumDefLoss2.value 2")
//     fm.SumDefLoss2.value = point(round(sumComponentVerpCompPrice,2),2);
//============================================
//    fm.SumDefLoss2.value = point(round(sumManHourFee+sumMaterialFee,2),2);   
     fm.SumDefLoss2.value = point(round(sumManHourFee+sumMaterialFee,0),0);  
  } else {
    fm.SumManHourFee2.value  = 0.00;        
    fm.SumMaterialFee2.value = 0.00; 
//    alert("计算fm.SumDefLoss2.value 3")
    fm.SumDefLoss2.value = 0.00;  
  }   
   
  return true; 

}
  
/**
*@description 处理索赔资料清单 
*@param       businessNo 
*/
function doCertifyDirect(businessNo,nodeType){      
  window.open( "/claim/certifyBeforeEdit.do?RegistNo="+businessNo+"&editType=CertifyDirect&nodeType="+nodeType,"winName","resizable=0,scrollbars=1,width=800,height=600");
}    

//reason: 统一使用ClaimPub.js 里的方法，便於维护
/**
 *@description 弹出单证页面
 *@param       无
 *@return      通过返回true,否则返回false
 */
/*
function openCertify(registNo){   
  var win;
  var messagedo="/claim/certifyFinishQueryList.do?prpLcertifyCertifyNo="+registNo+"&editType=EDIT&nodeType=certa";  
  //win=window.showModalDialog(messagedo,"dialogTop=0;dialogLeft=0;dialogWidth=800;dialogHeight=600");
  //win=window.open(messagedo,"NewWindow","dialogWidth:750px;dialogHeight:550px; dialogLeft:15px;dialogTop:15px;center:yes;help:yes;resizable:yes;status:yes");
  win=window.open(messagedo,"NewWindow","status=no,resizable=yes,scrollbars=yes,width=700,Height=500");
} 
*/
//计算残值总额
function calculateSumRestFee(field){   
  
  var count=getElementCount("prpLcomponentRestFee");
  var restFee = 0;
  var temp = 0;
  
  for(var j=1;j<count;j++)
  {
    
    temp = parseFloat(fm.prpLcomponentRestFee[j].value);
    if(isNaN(temp))
    {
      temp = 0;      
    }
    restFee = restFee + temp;
  }
  fm.prpLcarLossSumRest.value=restFee;
} 

/**
 *@description
 *@param       无
 *@return      无
 */ 
  function setNextNode() 
  { 
     var repairFactoryType = "";  //修理厂类型
     var materialFee = 0;         //上报价格
     var sysPrice = 0 ;   //核价默认值
     var sys4SPrice = 0;          //系统专修价 
     var sysMarketPrice = 0;      //系统市场价
     var sumTax = 0;              //本地专修价
     var sumTransFee = 0;         //本地市场价
     var condition = 0;
          
     repairFactoryType = fm.prpLrepairFeeRepairFactoryCode.value;
     
     if (( parseFloat(fm.SumDefLoss1.value) > parseFloat(fm.prpLclaimGradeSumWorkHourFee.value))||
          (parseFloat (fm.SumDefLoss2.value) >  parseFloat(fm.prpLclaimGradeSumChangCompFee.value)))
     {  
     	  condition = 1;
     	}

     for(i=1;i<fm.all("prpLcomponentSys4SPrice").length;i++)
     {
   	    materialFee = parseFloat(fm.all("prpLcomponentMaterialFee")[i].value);   	    


   	    if(fm.all("prpLcomponentFlag")[i].value=="1"){  //标准配件
     	    sys4SPrice = parseFloat(fm.all("prpLcomponentSys4SPrice")[i].value);   	    
     	    sysMarketPrice = parseFloat(fm.all("prpLcomponentSysMarketPrice")[i].value);
     	    native4SPrice = parseFloat(fm.all("prpLcomponentNative4SPrice")[i].value);   	    
     	    nativeMarketPrice = parseFloat(fm.all("prpLcomponentNativeMarketPrice")[i].value);
     	
         	if(isNaN(materialFee)) materialFee = 0;
         	if(isNaN(sys4SPrice)) sys4SPrice = 0;
         	if(isNaN(sysMarketPrice)) sysMarketPrice = 0;
         	if(isNaN(native4SPrice)) native4SPrice = 0;
         	if(isNaN(nativeMarketPrice)) nativeMarketPrice = 0;
         	
         	if(repairFactoryType=="01"){ //修理厂为专修店
         	  if(native4SPrice==0){         	    
         	    sysPrice = sys4SPrice;         	           	    
         	  }else{
         	    sysPrice = native4SPrice;         	           	           	  
         	  }
         	}else{      //修理厂为一类厂、二类厂以下
         	  if(nativeMarketPrice==0){
         	      sysPrice = sysMarketPrice;
         	  }else{
        	      sysPrice = nativeMarketPrice;
         	  }       	
          }

        	if (materialFee>sysPrice){
         		condition = 1;
        		break;		
        	}
        }else{    //自定义配件
	        condition = 1;	  
	        break;
        }        
     }  

     if(condition==1){
     	 fm.verifyPriceFlag.value = "1";
     }     
  }   

function initCarLossSum(type)
{ 
	if (type==1){
		//      setReadonlyOfElementOfLoss(fm.prpLcarLossSumTransFee); 
		//      setReadonlyOfElementOfLoss(fm.prpLcarLossSumTax); 
		      setReadonlyOfElementOfLoss(fm.prpLcarLossSumManager); 
		      setReadonlyOfElementOfLoss(fm.prpLcarLossSumRest); 
			//fm.prpLcarLossSumTransFee.value=0;
			//fm.prpLcarLossSumTax.value=0;
			fm.prpLcarLossSumManager.value=0;
			fm.prpLcarLossSumRest.value=0;
			  
		}else{
		//      setNoReadonlyOfElementOfLoss(fm.prpLcarLossSumTransFee); 
		//      setNoReadonlyOfElementOfLoss(fm.prpLcarLossSumTax); 
		      setNoReadonlyOfElementOfLoss(fm.prpLcarLossSumManager); 
		      setNoReadonlyOfElementOfLoss(fm.prpLcarLossSumRest); 
		}
}		
     

function setNoReadonlyOfElementOfLoss(iElement)
{
  if(iElement.type!=null){
    if(iElement.type=="select-one")
    {
      iElement.disabled = true;
    } 
   
    else if (iElement.type=="text")
    {
      iElement.onfocus = null;                   
      iElement.readOnly = false;
      iElement.className = "input";
    }
  }
}

function setFirstMaterialFee(field)
{

  var fieldname=field.name; 
  var i = 0;
  var findex=0;       
  var manHourFee;
  var materialFee; 
  var sumDefLoss;
  var ManHour;
  var quantity;

 if (fm.certainLossFirst.value=="First"){

  for(i=1;i<fm.all(fieldname).length;i++)
  {
    if( fm.all(fieldname)[i] == field )
    {
      findex=i;
      break;
    }
  } 
    ManHour = parseFloat(fm.all("prpLcomponentMaterialFee")[findex].value);
    fm.all("prpLcomponentFirstMaterialFee")[findex].value = ManHour;
 	}
}


function openWinCertainLossCarModel()
 {
    var submitStr  = "/claim/pages/DAA/certainLoss/DAACertainLossCarModel.jsp" ;
 	window.open(submitStr,'车型查询','width=950,height=600,top=50,left=50,toolbar=0,location=0,directories=0,menubar=0,scrollbars=yes,resizable=yes,status=no');
 }
 
 function checkDeductible(){
	 var sumCertainLossA=0.0;
		var tempLossA=0;
		var prpLDeductible = parseFloat(fm.prpLDeductible.value);
 		var tempflag ="0";
 		var carLossComponentLossItemCodeCount = getElementCount("carLossComponentLossItemCode");
    	//换件费
    	for(var index=1;index < carLossComponentLossItemCodeCount;index++)
    	{
    		
    		var kindCode = trim(fm.prpLcomponentKindCode[index].value);
    	    if(kindCode == RISKINFO.KINDCODE_D_A){ 
	    		tempLossA=parseFloat(fm.prpLcomponentVerpCompPrice[index].value);   
	    		if(isNaN(tempLossA))
			    {
			      tempLossA = 0;      
			    } 
			   sumCertainLossA = sumCertainLossA + tempLossA;    
    	    }else{ tempflag="1";
        	}
       
      }
      
      var carLossRepairFeeLossItemCodeCount = getElementCount("carLossRepairFeeLossItemCode");
    	
    	//修理费
    	for(var index=1;index < carLossRepairFeeLossItemCodeCount;index++)
    	{
    		
    		var kindCode = trim(fm.prpLrepairFeeKindCode[index].value);
    	  if(kindCode == RISKINFO.KINDCODE_D_A){
    	  	tempLossA=parseFloat(fm.prpLrepairFeeSumDefLoss[index].value);   
	    		if(isNaN(tempLossA))
			    {
			      tempLossA = 0;      
			    } 
			   sumCertainLossA = sumCertainLossA + tempLossA;    
        }else{ 
        	tempflag="1";
        	}
      }
      if(sumCertainLossA<prpLDeductible&&tempflag!="1"){
        	alert(i18n.certainLoss.optionalFranchiseProcess);  // 车损险定损金额小於可选免赔额，请做免赔额注销处理
    	return false;
      } 
    }
    
    /**
 *@description 将案件转换为简易赔案
 *@return      通过返回true,否则返回false
 */	  
function changeToQuickCase(){
	
	//需要增加简易赔案的处理过程
	//由於入口成功之後，是要读registNo和quickCaseStatus所以要传参数的。
	fm.action="/claim/quickCaseBeforeEdit.do?registNo="+fm.prpLverifyLossRegistNo.value+"&editType=ADD&quickCaseStatus=01";
	fm.submit();
}
//控制按钮的可见和不可见状态
function changeAdvanceStatus(field)
{
   if(field.value=='1')//全责垫付
   {
      fm.getFromPlatForm.style.display="";
   }
   else//其他
   {
      fm.getFromPlatForm.style.display="none";
   }
}
//輸入无责方的信息
//用来打开一个新的页面然後重新輸入无责方的信息
function inputNullInfo()
{
   var registNo = fm.prplCheckRegistNoShow.value;
   var newWindow = window.open("/claim/advance.do?registNo="+registNo+"&flagAgent=1","NewWindow","width=640,height=300,top=0,left=0,toolbar=no,location=no,directories=no,menubar=no,scrollbars=yes,resizable=yes,status=no");
}
