 /*****************************************************************************
  * DESC       ：车险打印的具体脚本函数页面
  * AUTHOR     ：中科軟
  * CREATEDATE ： 2004-11-16
  * MODIFYLIST ：   Name       Date            Reason/Contents
  *          ------------------------------------------------------
  ****************************************************************************/

 function DAARegistloadForm() {

 	tdRegistNo.innerHTML = '报案编号：' + "<bean:write name='prpLregistDto' property='registNo'/>";
 	tdReportType.innerHTML = '报案方式：' + '电话报案';
 	tdReportorName.innerHTML = '报案人：' + '6765';
 	tdReportDate.innerHTML = '报案时间：' + '2004-11-01' + ' ' + '18';
 	tdLinkerName.innerHTML = '联系人：' + '54765';
 	tdPhoneNumber.innerHTML = '联系电话：' + '564765';
 	tdDamageStartDate.innerHTML = '出险时间：' + '2004-11-01' + ' ' + '18';
 	tdDamageName.innerHTML = '出险原因：' + '洪水';
 	tdDamageAddress.innerHTML = '出险地点：' + '564756';
 	tdFirstSiteFlag.innerHTML = '是否是第一现场报案：' + '是';
 	tdHandleUnit.innerHTML = '处理部门：' + '42010102';
 	tdComCode.innerHTML = '承保公司：' + '西安分公司核保部';
 	//*****保险关系人表PrpCinsured*****
 	tdInsuredName.innerHTML = '被保险人：' + '韩莎莎';
 	tdInsuredType.innerHTML = '客户类别：' + '个人';
 	tdInsuredAddress.innerHTML = '被保险人住址：' + '上海商城路';
 	tdInsuredPostCode.innerHTML = '邮政编码：' + '';
 	tdInsuredLinkerName.innerHTML = '联系人：' + '韩莎莎';
 	tdInsuredPhoneNumber.innerHTML = '固定电话：' + '87511338';
 	tdInsuredMobile.innerHTML = '移动电话：' + '13891918919';
 	tdBusinessSort.innerHTML = '被保险人单位性质：' + '';
 	//*****保单基本信息表PrpCmain*****
 	tdInsuredTerm.innerHTML = '保险期限：' + '2004/6/8-2005/6/7';
 	tdSumPremium.innerHTML = '保险费：' + '4,586.41' + '元';
 	tdArgueSolution.innerHTML = '争议解决方式：' + '诉讼';
 	tdOperatorName.innerHTML = '签单人：' + '钱萍';
 	tdHandlerName.innerHTML = '经办人：' + '钱萍';
 	tdUnderwriteName.innerHTML = '核保人：' + 'admin';
 	tdUserName.innerHTML = '抄单人：' + 'admin';
 	tdInputDate.innerHTML = '抄单日期：' + '2004年11月10日';
 	//*****理赔车辆信息PrpLthirdParty*****
 	tdLicenseNo.innerHTML = '号牌号码：';
 	tdUseYears.innerHTML = '已使用年限：';
 	tdLicenseColorCode.innerHTML = '号牌底色：';
 	tdBrandName.innerHTML = '厂牌型号：';
 	tdEngineNo.innerHTML = '发动机号：';
 	tdFrameNo.innerHTML = '车架号：';
 	tdCarKindCode.innerHTML = '车辆种类：';
 	tdClauseType.innerHTML = '基本险条款类别：';

 	tdLicenseNo.innerHTML = '号牌号码：' + '陕A1234';
 	tdUseYears.innerHTML = '已使用年限：' + '1' + '年';

 	tdLicenseColorCode.innerHTML = '号牌底色：' + '蓝';
 	tdBrandName.innerHTML = '厂牌型号：' + '桑塔纳';
 	tdEngineNo.innerHTML = '发动机号：' + '456853';
 	tdFrameNo.innerHTML = '车架号：' + '456853';

 	tdCarKindCode.innerHTML = '车辆种类：' + '客车';

 	tdClauseType.innerHTML = '基本险条款类别：' + '机动车辆损失保险条款';

 	tdVINNo.innerHTML = 'VIN码：' + '';

 	tdUseNatureCode.innerHTML = '车辆使用性质：' + '非营业用(不含家庭自用)';
 	tdSeatCount.innerHTML = '核定载客 ' + '5' + ' 人' + '<br>' + ' 核定载质量 ' + '0.00' + ' 千克';

 	tdEnrollDate.innerHTML = '车辆初次登记日期：' + '2002-10-01';

 	tdRunAreaName.innerHTML = '车辆行驶網域：' + '中华人民共和国境内(不含港澳台)';
 	tdPurchasePrice.innerHTML = '新车购置价：' + '200,000.00' + '元';

 	//*****车险驾驶员信息表PrpLdriver*****
 	tdDriverName.innerHTML = '驾驶人员名称：';
 	tdDrivingCarType.innerHTML = '准驾车型：';
 	tdReceiveLicenseDate.innerHTML = '驾驶证初次领证日期：';
 	tdDrivingLicenseNo.innerHTML = '驾驶证号码：';

 	//*****车辆驾驶员关系表PrpCcarDriver*****
 	tdDriverName1.innerHTML = '主驾驶人员姓名：';
 	tdDrivingLicenseno1.innerHTML = '驾驶证号码：';
 	tdAcceptLicenseDate1.innerHTML = '初次领证日期：';
 	tdDriverName2.innerHTML = '从驾驶人员姓名：';
 	tdDrivingLicenseno2.innerHTML = '驾驶证号码：';
 	tdAcceptLicenseDate2.innerHTML = '初次领证日期：';

 	tdKindName1.innerHTML = '车辆损失险' + '(' + 'A' + ')';
 	tdAmount1.innerHTML = '' + '200,000.00' + '';

 	tdKindName2.innerHTML = '第三者责任险' + '(' + 'B' + ')';
 	tdAmount2.innerHTML = '' + '100,000.00' + '';

 	tdKindName3.innerHTML = '附加车上人员责任险' + '(' + 'D1' + ')';
 	tdAmount3.innerHTML = '' + '10,000.00' + '/座*2座';

 	tdKindName4.innerHTML = '附加车上人员责任险' + '(' + 'D1' + ')';
 	tdAmount4.innerHTML = '' + '5,000.00' + '/座*3座';

 	tdKindName5.innerHTML = '不计免赔特约' + '(' + 'M' + ')';
 	tdAmount5.innerHTML = '' + '' + '';


 	//*****特别约定表PrpCengage*****

 	fm.Engage.value = ' 特别约定：' + '';

 	fm.PheadText.value = '';

 	//*****立案信息表PrpLclaim*****

 	fm.ClaimText.value = '出险摘要：4565436\r\n';
 	tdPheadCount.innerHTML = '本单批改次数：' + '0';
 	tdClaimCount.innerHTML = '车辆出险次数：' + '0';
 	tdCompensateCount.innerHTML = '赔款次数：' + '0';
 	tdSumPaid.innerHTML = '赔款总计：' + '0.00';
 }