package com.sinosoft.undwrt.common.util;

import java.text.NumberFormat;

import com.sinosoft.sff.blsvr.BLPrpJpayRefRec;
import com.sinosoft.sysframework.exceptionlog.UserException;
import com.sinosoft.sysframework.reference.DBManager;
import com.sinosoft.undwrt.common.vo.HeBaoConditionVo;
import com.sinosoft.utility.SysConfig;

/**
 * The Class StandardCheckAction.
 */
public class StandardCheckAction {

 // 核保高级条件判断
    /**
	 * Check hebao.
	 * 
	 * @param iBusinessNo
	 *            the i business no
	 * @param heBaoConditionDto
	 *            the he bao condition dto
	 * @param dbManager
	 *            the db manager
	 * @param iBusinessType
	 *            the i business type
	 * @return true, if successful
	 * @throws Exception
	 *             the exception
	 */
 public boolean checkHebao(String iBusinessNo,
            HeBaoConditionVo heBaoConditionDto, DBManager dbManager,
            String iBusinessType) throws Exception {
//
//        BLBusinessData blBusinessData = null;
//        if (iBusinessType.equals("proposal")) {// 判断业务类型
//            blBusinessData = new BLBusinessProposalData(iBusinessNo, dbManager);
//        } else if (iBusinessType.equals("policy")) {
//            blBusinessData = new BLBusinessPolicyData(iBusinessNo, dbManager);
//        } else if (iBusinessType.equals("endorse")) {
//            blBusinessData = new BLBusinessEndorseData(iBusinessNo, dbManager);
//        } else {
//            return false;// 配置错误
//        }
//        if (!blBusinessData.getRiskCode().equals("00000")) {
//            //System.out.println("-------------------------车险核保获取业务数据成功！！\n");
//        } else {
//            //System.out.println("----------------------业务类型不对\n");
//            return false; // 判断数据初始化是否成功，如果失败，说明业务类型错误，系统会选择其他业务类型 。
//        }
//        // added by gengxiaobo begin 20080619 添加保单注销控制，已打印过发票、已作过收费的保单不能注销 。
//        if (iBusinessType.equals("endorse")) {
//            if ("19".equals(blBusinessData.getStrEndortype())) {
//                BLPrpJpayRefRec blPrpJpayRefRec=new BLPrpJpayRefRec();
//
//                if (blPrpJpayRefRec.isInvoicePrinted("P", blBusinessData.getTempPolicyNo(), "")) {
//                    String title = "此保单不能注销!保单已打印过发票。";
//                    this.throwStringException(title, "");
//                    return false;
//                }
//                if (blPrpJpayRefRec.isRealPay("P", blBusinessData.getTempPolicyNo())){
//                    String title = "本批单对应的" + blBusinessData.getTempPolicyNo() + "号保单已经实收或者部分实收，不能进行注销批改操作，请进行全单退保操作。";
//                    this.throwStringException(title, "");
//                    return false;
//                }           
//            }
//            if("05".equals(blBusinessData.getClassCode())&&blBusinessData.getStrEndortype().indexOf("57")>-1) {
//                if(("N").equals(heBaoConditionDto.getEndorDisRate())){
//                    String title = "批单的核批权限不符合条件!";
//                    String standardData = "您没有权限核保批改类型为手续费批改的批单";
//                    String businessData = "当前批单的保费变化量是";
//                    this.throwExceptions(title, standardData, businessData);
//                    return false;  
//                }
//            }
//            if("54".equals(blBusinessData.getStrEndortype())){//增加保单停效批改校验 ruanzhongxi_leave
//                if(blBusinessData.getUnionpayCount()>0){
//                    String title = "保单失效批改不符合条件!";
//                    String standardData = "该保单有未处理的批次扣款数据,请下发修改后删除批单，待收付系统上传回复文档后次日再做批改操作！";
//                    this.throwStringException(title, standardData);
//                    return false;
//                }
//            }
//        }
//        // added by gengxiaobo end 20080619 添加保单注销控制，已打印过发票、已作过收费的保单不能注销 。
//        
//        //added by gengxiaobo begin 20080620 车险重复投保校验。
//        if(iBusinessType.equals("proposal")&&blBusinessData.getClassCode().equals("05")){
//            String strCheckRtn = check(blBusinessData.getTempProposalNo());
//            if(!"false".equals(strCheckRtn)){
//                String title = "车险重复投保！";
//                this.throwStringException(title,strCheckRtn);
//                return false;
//            }           
//        }
//        
//       if("2202".equals(blBusinessData.getRiskCode())||"13".equals(blBusinessData.getClassCode())){
//           if(!"Y".equals(heBaoConditionDto.getAllowCheck())){
//               System.out.println("blBusinessData.getNodeName()==="+blBusinessData.getNodeName());
//               String title = "核保级别不够!";
//               String standardData = "此险种需要总公司审核通过!";
//               String businessData = "在分公司级别";
//               this.throwException(title, standardData, businessData);
//               return false;
//           }
//       }
//       
//       //add by zhaoning20091125 begin Reason:对于企财险如果做了拆分危险单位则需要总公司核保
//       if((iBusinessType.equals("proposal")||iBusinessType.equals("policy"))&&blBusinessData.getClassCode().equals("01")){
//           if(blBusinessData.getDangerUnitCount()>=2&&heBaoConditionDto.getParentCompanyCheck().equals("N")){
//               String title = "此保单进行过危险单位划分，请提交总公司核保！";
//               this.throwStringException(title, "");
//               return false;
//           }
//       }
//       //add by zhaoning20091125 end
//       
//       //add by zhaoning20100128 begin Reason:2010年非车险核保权限
//       if((iBusinessType.equals("proposal")||iBusinessType.equals("policy"))&&
//               (blBusinessData.getClassCode().equals("11")||blBusinessData.getClassCode().equals("22"))){
//           if(blBusinessData.getDangerUnitCount()>=2&&heBaoConditionDto.getAllowSplitDangerUnit().equals("N")){
//                String title = "是否允许划分风险单位不符合条件!";
//                String standardData = "无权审核划分风险单位的业务";
//                String businessData = "划分风险单位的业务";
//                this.throwException(title, standardData, businessData);
//                return false;
//           }
//       }
//       
//       if(blBusinessData.getRiskCode().equals("0402")){
//           if ((heBaoConditionDto.getSumAmount0400600()>-1)&&(heBaoConditionDto.getSumAmount0400600() < blBusinessData.getSumAmount0400600())){
//               String title = "财产损失保险保险金额不符合条件!";
//               String standardData = "小于等于"
//                        + NumberFormat.getInstance().format(heBaoConditionDto.getSumAmount0400600())
//                        + "元";
//               String businessData = NumberFormat.getInstance().format(blBusinessData.getSumAmount0400600())
//                        + "元";
//               this.throwException(title, standardData, businessData);
//               return false;
//           }
//           
//           if ((heBaoConditionDto.getSumAmount0400700()>-1)&&(heBaoConditionDto.getSumAmount0400700() < blBusinessData.getSumAmount0400700())){
//               String title = "还贷保证保险保险金额不符合条件!";
//               String standardData = "小于等于"
//                        + NumberFormat.getInstance().format(heBaoConditionDto.getSumAmount0400700())
//                        + "元";
//               String businessData = NumberFormat.getInstance().format(blBusinessData.getSumAmount0400700())
//                        + "元";
//               this.throwException(title, standardData, businessData);
//               return false;
//           }
//       }
//       
//       if(blBusinessData.getRiskCode().equals("0401")||blBusinessData.getRiskCode().equals("0403")){
//            if ((heBaoConditionDto.getSumAmount()>-1)
//                    &&(heBaoConditionDto.getSumAmount() < blBusinessData
//                    .getSumAmount())){
//                String title = "总保额不符合条件!";
//                String standardData = "小于等于"
//                        + NumberFormat.getInstance().format(
//                                heBaoConditionDto.getSumAmount())
//                        + "元";
//                String businessData = NumberFormat.getInstance().format(
//                        blBusinessData.getSumAmount())
//                        + "元";
//                this.throwException(title, standardData, businessData);
//                return false;
//            }
//       }
//       
//       if(blBusinessData.getClassCode().equals("11")||blBusinessData.getRiskCode().equals("2203")){
//           if((heBaoConditionDto.getSumAmount()>-1) && (heBaoConditionDto.getSumAmount() < blBusinessData.getSumAmount())){
//               String title = "总保额不符合条件!";
//               String standardData = "小于等于"
//                        + NumberFormat.getInstance().format(
//                                heBaoConditionDto.getSumAmount())
//                        + "元";
//               String businessData = NumberFormat.getInstance().format(
//                        blBusinessData.getSumAmount())
//                        + "元";
//               this.throwException(title, standardData, businessData);
//               return false;
//           }
//       }
//       
//       if(iBusinessType.equals("policy")&&blBusinessData.getCoverNoteFlag().equals("Y")&&heBaoConditionDto.getCoverNoteFlag().equals("N")){
//           String title = "核保级别不够!";
//           String standardData = "您没有权限核保!";
//           String businessData = "暂保单";
//           this.throwException(title, standardData, businessData);
//           return false;
//       }
//       //add by zhaoning20100128 end
//
//       if(iBusinessType.equals("endorse") && !"05".equals(blBusinessData.getClassCode())){
//           System.out.println("blBusinessData.getRiskCode()=="+blBusinessData.getRiskCode());
//           System.out.println("heBaoConditionDto.getChgPremium()=="+heBaoConditionDto.getChgPremium());
//           if(blBusinessData.getChgPremium()<0&&(!("Y").equals(heBaoConditionDto.getChgPremium()))){
//               //add by yanglibo begin 20080812 修改核批时权限不够的提示语
//               String title = "核保级别不够!";
//               String standardData = "你的批改退费权限是" + 0; //"该批单需要二级B才能审核通过";
//               String businessData = "当前批单的保费变化量是" + blBusinessData.getChgPremium();
//               this.throwException(title, standardData, businessData);
//               return false;        
//               // add by yanglibo begin 20080812 修改核批时权限不够的提示语          
//           }
//       }
//       
//       //增加可配置性，使用UNDERWRITE_CLASSCODE_BY_PARENTCOMPANY
//       System.out.println("**********UNDERWRITE_CLASSCODE_BY_PARENTCOMPANY==="+SysConfig.getProperty("UNDERWRITE_CLASSCODE_BY_PARENTCOMPANY"));
//       System.out.println("heBaoConditionDto.getAllowCheck()==="+heBaoConditionDto.getAllowCheck());
//       if(SysConfig.getProperty("UNDERWRITE_CLASSCODE_BY_PARENTCOMPANY").indexOf(blBusinessData.getClassCode())>-1 
//               && !blBusinessData.getRiskCode().equals("1102")){
//           if(!"Y".equals(heBaoConditionDto.getAllowCheck())){
//               String title = "核保级别不够!";
//               String standardData = "此险种需要总公司审核通过!";
//               String businessData = "在分公司级别";
//               this.throwException(title, standardData, businessData);
//               return false;
//           }
//       }
//       
//       /*********************************************************公共核保部分**********************************************************************/
//       //直接倒签单天数
//       if ((heBaoConditionDto.getDirectDay()>-1)
//                && heBaoConditionDto.getDirectDay() < blBusinessData
//                        .getDirectDay()) {
//            String title = "直接倒签单天数不符合条件!";
//            String standardData = "小于等于" + heBaoConditionDto.getDirectDay()
//                    + "天";
//            String businessData = blBusinessData.getDirectDay() + "天";
//            this.throwException(title, standardData, businessData);
//            return false;
//       }
//       //代理倒签单天数
//       else if ((heBaoConditionDto.getAgentDay()>-1)
//                && heBaoConditionDto.getAgentDay() < blBusinessData
//                        .getAgentDay()) {
//            String title = "代理倒签单天数不符合条件!";
//            String standardData = "小于等于" + heBaoConditionDto.getAgentDay()
//                    + "天";
//            String businessData = blBusinessData.getAgentDay() + "天";
//            this.throwException(title, standardData, businessData);
//            return false;
//        }
//        // modify by yanglibo 20090401 begin reason :非车险保单注销权限调整
//        else if (heBaoConditionDto.getWriteOffDays()>-1&&
//                (heBaoConditionDto.getWriteOffDays() < blBusinessData
//                .getWriteOffDays())) {
//             String title = "保单注销权限不够!";
//             String standardData = "此保单需要核保二级A审核通过!";
//             String businessData = blBusinessData.getNodeName();
//             this.throwException(title, standardData, businessData);
//            return false;
//        }
//        else if ("05".equals(blBusinessData.getClassCode())&&"19".equals(blBusinessData.getStrEndortype())&&heBaoConditionDto.getUndoContractDate()>0 &&
//                (heBaoConditionDto.getUndoContractDate() < blBusinessData
//                        .getUndoContractDate())) {
//                    String title = "解除合同权限天数不符合条件!";
//                    String standardData = "小于等于"
//                            + heBaoConditionDto.getUndoContractDate() + "天";
//                    String businessData = blBusinessData.getUndoContractDate() + "天";
//                    this.throwException(title, standardData, businessData);
//                    return false;
//        }
//                //modify by yanglibo 20090401 end reason :非车险保单注销权限调整
//        else if ("05".equals(blBusinessData.getClassCode())&&"21".equals(blBusinessData.getStrEndortype())&&heBaoConditionDto.getUndoContractDate()>0 &&
//                (heBaoConditionDto.getUndoContractDate() < blBusinessData
//                        .getUndoContractDate())) {
//                    String title = "解除合同权限天数不符合条件!";
//                    String standardData = "小于等于"
//                            + heBaoConditionDto.getUndoContractDate() + "天";
//                    String businessData = blBusinessData.getUndoContractDate() + "天";
//                    this.throwException(title, standardData, businessData);
//                    return false;
//        }
//        //modify by duhaichao 20101017 reason:非车解除合同权限天数的调整
//        /*else if ((!("05".equals(blBusinessData.getClassCode()))&&!("15".equals(blBusinessData.getClassCode()))&&"21".equals(blBusinessData.getStrEndortype())&&heBaoConditionDto.getUndoContractDate()<0)
//                ||(!("05".equals(blBusinessData.getClassCode()))&&!("15".equals(blBusinessData.getClassCode()))&&"21".equals(blBusinessData.getStrEndortype())&&heBaoConditionDto.getUndoContractDate()<blBusinessData.getUndoContractDate()&&heBaoConditionDto.getUndoContractDate()!=0)) {
//            String title = "解除合同权限天数不符合条件!";
//            String standardData = "小于等于"
//                    + heBaoConditionDto.getUndoContractDate() + "天";
//            String businessData = blBusinessData.getUndoContractDate() + "天";
//            this.throwException(title, standardData, businessData);
//            return false;*/
//        else if ((!("05".equals(blBusinessData.getClassCode())) && "21".equals(blBusinessData.getStrEndortype())
//                    && heBaoConditionDto.getUndoContractDate()<0 && heBaoConditionDto.getUndoContractDate()>-1)
//                ||(!("05".equals(blBusinessData.getClassCode()))&&"21".equals(blBusinessData.getStrEndortype())
//                        && heBaoConditionDto.getUndoContractDate()<blBusinessData.getUndoContractDate() && heBaoConditionDto.getUndoContractDate()>-1)) {
//            String title = "解除合同权限天数不符合条件!";
//            String standardData = "小于等于"
//                    + heBaoConditionDto.getUndoContractDate() + "天";
//            String businessData = blBusinessData.getUndoContractDate() + "天";
//            this.throwException(title, standardData, businessData);
//            return false;           
//            /*重复的校验
//             *  }
//        else if(blBusinessData.getClassCode().equals("23")||blBusinessData.getClassCode().equals("09")||blBusinessData.getClassCode().equals("27")){
//            if(heBaoConditionDto.getUndoContractDate()>0 &&
//                    (heBaoConditionDto.getUndoContractDate() < blBusinessData
//                            .getUndoContractDate())){
//                String title = "解除合同权限天数不符合条件!";
//                String standardData = "小于等于"
//                        + heBaoConditionDto.getUndoContractDate() + "天";
//                String businessData = blBusinessData.getUndoContractDate() + "天";
//                this.throwException(title, standardData, businessData);
//                return false;
//            }*/
//        }
//       
//       //解除合同时退保保费
//       if (blBusinessData.getClassCode().equals("01")||blBusinessData.getClassCode().equals("07")
//           ||blBusinessData.getClassCode().equals("23")) {
//           if ((heBaoConditionDto.getTuiBaoPremium()>-1)&&
//                (heBaoConditionDto.getTuiBaoPremium() < blBusinessData
//                    .getTuiBaoPremium())) {
//               System.out.println("----------------------核保因子-解除合同时退保保费：标准配置==" + heBaoConditionDto.getTuiBaoPremium());
//               System.out.println("----------------------核保因子-解除合同时退保保费：实际业务==" + blBusinessData.getTuiBaoPremium());
//               String title = "解除合同时退保保费不符合权限!";
//               String standardData = "小于等于"
//                        + NumberFormat.getInstance().format(
//                                heBaoConditionDto.getTuiBaoPremium())
//                        + "元";
//               String businessData = NumberFormat.getInstance().format(
//                        blBusinessData.getTuiBaoPremium())
//                        + "元";
//               this.throwException(title, standardData, businessData);
//               return false;
//            }
//       }
//       
//       /*
//        * modify by DuHCH 20110725 begin  reason:客户无要求，忽略此校验
//        * //add by zhangruifeng reason:01、07险类时当批改重新进行了风险评估需要总公司进行核保
//       if(iBusinessType.equals("endorse")&&(blBusinessData.getClassCode().equals("01")||blBusinessData.getClassCode().equals("07"))){
//           if(blBusinessData.isNewRiskEvaluate()==true){//当点击了风险评估
//               if(!"Y".equals(heBaoConditionDto.getAllowCheck())){
//                   String title = "批单进行风险评估后需要由总公司进行审核!";
//                   String standardData = "该批单需要一级C以上才能审核通过";
//                   String businessData = blBusinessData.getNodeName();
//                   this.throwException(title, standardData, businessData);
//                   return false;
//               }
//           }
//       }
//       * modify by DuHCH 20110725 end
//       */
//       
//       //added by xiongguojun 20090818 货运险启运日期批改 begin
//       if (iBusinessType.equals("endorse") && (blBusinessData.getClassCode().equals("09") || blBusinessData.getClassCode().equals("10"))) {
//           System.out.println("货运险启运日期批改前");
//           if ("83".equals(blBusinessData.getStrEndortype())) {
//               System.out.println("进入货运险启运日期批改");
//               System.out.println("heBaoConditionDto.getFreightStartDate()================="+heBaoConditionDto.getFreightStartDate());
//               if (heBaoConditionDto.getFreightStartDate() == -1) {
//                   String title = "核保级别不够!";
//                   String standardData = "该批单需要二级A及以上才能审核通过";
//                   String businessData = blBusinessData.getNodeName();
//                   this.throwException(title, standardData, businessData);
//                   return false; 
//               }
//           }
//       }
//       //added by xiongguojun 20090818 货运险启运日期批改 end
//       
//            /*
//             * if ((heBaoConditionDto.getSumAmount()>-1)
//                    &&(heBaoConditionDto.getSumAmount() < blBusinessData
//                    .getSumAmount())){
//                String title = "总保额/累计赔偿限额不符合条件!";
//                String standardData = "小于等于"
//                        + NumberFormat.getInstance().format(
//                                heBaoConditionDto.getSumAmount())
//                        + "元";
//                String businessData = NumberFormat.getInstance().format(
//                        blBusinessData.getSumAmount())
//                        + "元";
//                this.throwException(title, standardData, businessData);
//                return false;
//            }
//            */
//       
//       /*********************************************************车险核保部分**********************************************************************/
//       
//       if("05".equals(blBusinessData.getClassCode())){
//           System.out.println("--------保险期间业务值-------==" + blBusinessData.getCBMonthLimit());
//           System.out.println("--------保险期间配置值-------==" + heBaoConditionDto.getCBMonthLimit());
//           if(iBusinessType.equals("endorse") && blBusinessData.getRiskCode().equals("0501")){
//               System.out.println("--------批改类型业务值-------==" + blBusinessData.getStrEndortype());
//               System.out.println("--------批改类型配置值-------==" + heBaoConditionDto.getEndorsePower());
//               if((!"42".equals(blBusinessData.getStrEndortype()))&&"A".equals(heBaoConditionDto.getEndorsePower())){
//                    String title = "批改权限不符合!";
//                    String standardData = "只能对批改车牌号的批单业务进行处理";
//                    String businessData = "非批改车牌号的业务";
//                    this.throwException(title, standardData, businessData);
//                    return false;
//               }
//              else if((  blBusinessData.getStrEndortype().indexOf("57")>-1  //批改手续费 
//                      || blBusinessData.getStrEndortype().indexOf("01")>-1  //变更保险期限
//                      || blBusinessData.getStrEndortype().indexOf("87")>-1  //保险人合同解除
//                      || blBusinessData.getStrEndortype().indexOf("19")>-1  //注销保单
//                      || blBusinessData.getStrEndortype().indexOf("80")>-1  //停驶批改
//                      || blBusinessData.getStrEndortype().indexOf("81")>-1  //复驶批改
//                      || blBusinessData.getStrEndortype().indexOf("14")>-1  //赔款后减少保额
//                      || blBusinessData.getStrEndortype().indexOf("03")>-1) //遗失保单
//                      && ("D".equals(heBaoConditionDto.getEndorsePower())))
//              {
//                    String title = "批改权限不符合!";
//                    String standardData = "普通批改和全单退保";
//                    String businessData = "其它批改类型的业务";
//                    this.throwException(title, standardData, businessData);
//                    return false;
//               }
//           }
//           if(blBusinessData.getRiskCode().equals("0501")||blBusinessData.getRiskCode().equals("0502")||blBusinessData.getRiskCode().equals("0503")
//                   ||blBusinessData.getRiskCode().equals("0510")){
//                if ((heBaoConditionDto.getGroupCarSum()>-1)&&
//                        heBaoConditionDto.getGroupCarSum() < blBusinessData
//                        .getGroupCarSum()) {
//                    String title = "团车数量不符合条件!";
//                    String standardData = "小于等于"
//                            + heBaoConditionDto.getGroupCarSum() + "辆";
//                    String businessData = blBusinessData.getGroupCarSum() + "辆";
//                    this.throwException(title, standardData, businessData);
//                    return false;
//                }
//                if (heBaoConditionDto.getPermitBidding().equals("N")
//                        && blBusinessData.getPermitBidding().equals("Y")) {
//                    String title = "招标条件不符合!";
//                    String standardData = "不允许招标";
//                    String businessData = "招标业务";
//                    this.throwException(title, standardData, businessData);
//                    return false;
//                }
//                else if ((heBaoConditionDto.getUsingYearLimit()>0)&&    
//                        heBaoConditionDto.getUsingYearLimit() < blBusinessData
//                        .getUseYears()) {
//                    String title = "车使用年限不符合条件!";
//                    String standardData = "小于等于"
//                            + heBaoConditionDto.getUsingYearLimit() + "年";
//                    String businessData = blBusinessData.getUseYears() + "年";
//                    this.throwException(title, standardData, businessData);
//                    return false;
//                }
//                else if ((heBaoConditionDto.getOnlyABYears()>0)&&   
//                        heBaoConditionDto.getOnlyABYears() < blBusinessData
//                        .getOnlyABYears()) {
//                    String title = "车使用年限不符合条件!";
//                    String standardData = "小于等于"
//                            + heBaoConditionDto.getOnlyABYears() + "年";
//                    String businessData = blBusinessData.getOnlyABYears() + "年";
//                    this.throwException(title, standardData, businessData);
//                    return false;
//                }
//                else if (heBaoConditionDto.getCBMonthLimit()>0 && heBaoConditionDto.getCBMonthLimit()>blBusinessData.getCBMonthLimit()) {
//                    String title = "保险期限不符合条件!";
//                    String standardData = "保险期限在一年或一年以上的业务";
//                    String businessData = blBusinessData.getCBMonthLimit() + "月的业务";
//                    this.throwException(title, standardData, businessData);
//                    return false;
//                }
//                else if ("Y".equals(blBusinessData.getChooseZF())&& "N".equals(heBaoConditionDto.getChooseZF())) {
//                    String title = "投保了指定附加险不符合条件!";
//                    String standardData = "不能审核投保" + blBusinessData.getKindCName() + "的附加险";
//                    String businessData = "投保了"+blBusinessData.getKindCode()+"-"+blBusinessData.getKindCName();
//                    this.throwException(title, standardData, businessData);
//                    return false;
//                }else if ("Y".equals(blBusinessData.getChooseTD())&& "N".equals(heBaoConditionDto.getChooseTD())) {
//                    String title = "投保了特定条款不符合条件!";
//                    String standardData = "不能审核投保" + blBusinessData.getKindCName() + "的附加险";
//                    String businessData = "投保了"+blBusinessData.getKindCode()+"-"+blBusinessData.getKindCName();
//                    this.throwException(title, standardData, businessData);
//                    return false;
//                }
//                else if ("Y".equals(blBusinessData.getNotChooseB())&&"N".equals(heBaoConditionDto.getNotChooseB())) {
//                    String title = "未投保第三者责任险的投保单不符合条件!";
//                    String standardData = "不能审核未投保第三者责任险的投保单";
//                    String businessData = "未投保第三者责任险的投保单，您需要提交到核保一级C或以上核保级别才能核保";
//                    this.throwException(title, standardData, businessData);
//                    return false;
//                }
//                else if ((heBaoConditionDto.getChooseG1Years()>-1)&& 
//                        heBaoConditionDto.getChooseG1Years() < blBusinessData
//                        .getChooseG1Years()) {
//                    String title = "投保盗抢险且选择不计免赔的车辆使用年限不符合条件!";
//                    String standardData = "小于等于"
//                            + heBaoConditionDto.getChooseG1Years() + "年";
//                    String businessData = blBusinessData.getChooseG1Years() + "年";
//                    this.throwException(title, standardData, businessData);
//                    return false;
//                }
//                else if ((heBaoConditionDto.getChooseL1Years()>0)&& 
//                        heBaoConditionDto.getChooseL1Years() < blBusinessData
//                        .getChooseL1Years()) {
//                    String title = "投保划痕险且选择不计免赔的车辆使用年限不符合条件!";
//                    String standardData = "小于等于"
//                            + heBaoConditionDto.getChooseL1Years() + "年";
//                    String businessData = blBusinessData.getChooseL1Years() + "年";
//                    this.throwException(title, standardData, businessData);
//                    return false;
//                }
//                else if ((heBaoConditionDto.getChooseLPA4Years()>0)&&   
//                        heBaoConditionDto.getChooseLPA4Years() < blBusinessData
//                        .getChooseLPA4Years()) {
//                    String title = "投保专修厂维修特约险或零配件更换险的车辆使用年限不符合条件!";
//                    String standardData = "小于等于"
//                            + heBaoConditionDto.getChooseLPA4Years() + "年";
//                    String businessData = blBusinessData.getChooseLPA4Years() + "年";
//                    this.throwException(title, standardData, businessData);
//                    return false;
//                }
//                else if ((heBaoConditionDto.getChooseNA0Years()>0)&&    
//                        heBaoConditionDto.getChooseNA0Years() < blBusinessData
//                        .getChooseNA0Years()) {
//                    String title = "非营业客车投保车损或盗抢或其相关附加险的使用年限不符合条件!";
//                    String standardData = "小于等于"
//                            + heBaoConditionDto.getChooseNA0Years() + "年";
//                    String businessData = blBusinessData.getChooseNA0Years() + "年";
//                    this.throwException(title, standardData, businessData);
//                    return false;
//                }
//                else if ((heBaoConditionDto.getChooseNH0Years()>0)&&    
//                        heBaoConditionDto.getChooseNH0Years() < blBusinessData
//                        .getChooseNH0Years()) {
//                    String title = "非营业货车投保车损或盗抢或其相关附加险的使用年限不符合条件!";
//                    String standardData = "小于等于"
//                            + heBaoConditionDto.getChooseNH0Years() + "年";
//                    String businessData = blBusinessData.getChooseNH0Years() + "年";
//                    this.throwException(title, standardData, businessData);
//                    return false;
//                }
//                else if ((heBaoConditionDto.getChooseYYears()>0)&&  
//                        heBaoConditionDto.getChooseYYears() < blBusinessData
//                        .getChooseYYears()) {
//                    String title = "营业用车投保车损或盗抢或其相关附加险的使用年限不符合条件!";
//                    String standardData = "小于等于"
//                            + heBaoConditionDto.getChooseYYears() + "年";
//                    String businessData = blBusinessData.getChooseYYears() + "年";
//                    this.throwException(title, standardData, businessData);
//                    return false;
//                }
//                else if ((heBaoConditionDto.getChooseJYears()>0)&&  
//                        heBaoConditionDto.getChooseJYears() < blBusinessData
//                        .getChooseJYears()) {
//                    String title = "家庭自用汽车投保车损或盗抢或其相关附加险的使用年限不符合条件!";
//                    String standardData = "小于等于"
//                            + heBaoConditionDto.getChooseJYears() + "年";
//                    String businessData = blBusinessData.getChooseJYears() + "年";
//                    this.throwException(title, standardData, businessData);
//                    return false;
//                }
//                else if ((heBaoConditionDto.getChooseEYears()>0)&&  
//                        heBaoConditionDto.getChooseEYears() <= blBusinessData
//                        .getChooseEYears()) {
//                    String title = "投保自燃险车龄年限不符合条件!";
//                    String standardData = "小于"
//                            + heBaoConditionDto.getChooseEYears() + "年";
//                    String businessData = blBusinessData.getChooseEYears() + "年";
//                    this.throwException(title, standardData, businessData);
//                    return false;
//                }
//                else if ((heBaoConditionDto.getChooseEJYears()>-1)&&
//                        heBaoConditionDto.getChooseEJYears() < blBusinessData.getChooseEJYears()) {
//                    String title = "投保自燃险的家用车车龄年限不符合条件!";
//                    String standardData = "小于等于"
//                            + heBaoConditionDto.getChooseEYears() + "年";
//                    String businessData = blBusinessData.getChooseEYears() + "年";
//                    this.throwException(title, standardData, businessData);
//                    return false;
//                }
//                else if ((heBaoConditionDto.getRabateRate()>-1)&&
//                        (heBaoConditionDto.getRabateRate() > ((1-blBusinessData
//                            .getDiscount())*100))) {
//                        String title = "费率折扣比例不符合条件!";
//                        String standardData = "大于等于"
//                                + NumberFormat.getInstance().format(
//                                        heBaoConditionDto.getRabateRate());
//                        String businessData = NumberFormat.getInstance().format((1-
//                                blBusinessData.getDiscount())*100);
//                        this.throwException(title, standardData, businessData);
//                        return false;
//                }
//                else if ((heBaoConditionDto.getAmountA()>-1)&&
//                        (heBaoConditionDto.getAmountA() < blBusinessData
//                            .getAmountA())) {
//                        String title = "车损最大保额不符合条件!";
//                        String standardData = "小于等于"
//                                + NumberFormat.getInstance().format(
//                                        heBaoConditionDto.getAmountA())
//                                + "元";
//                        String businessData = NumberFormat.getInstance().format(
//                                blBusinessData.getAmountA())
//                                + "元";
//                        this.throwException(title, standardData, businessData);
//                        return false;
//                }
//                else if ((heBaoConditionDto.getAmountG()>-1)&&
//                        (heBaoConditionDto.getAmountG() < blBusinessData
//                            .getAmountG()) ){
//                        String title = "全车盗抢险最大保额/赔偿限额（元）的值不符合条件!";
//                        String standardData = "小于等于"
//                                + NumberFormat.getInstance().format(
//                                        heBaoConditionDto.getAmountG())
//                                + "元";
//                        String businessData = NumberFormat.getInstance().format(
//                                blBusinessData.getAmountG())
//                                + "元";
//                        this.throwException(title, standardData, businessData);
//                        return false;
//                }
//                else if ((heBaoConditionDto.getAmountB()>-1)&&
//                        (heBaoConditionDto.getAmountB() < blBusinessData
//                            .getAmountB())) {
//                        String title = "三者险赔偿限额综合不符合条件!";
//                        String standardData = "小于等于"
//                                + NumberFormat.getInstance().format(
//                                        heBaoConditionDto.getAmountB())
//                                + "元";
//                        String businessData = NumberFormat.getInstance().format(
//                                blBusinessData.getAmountB())
//                                + "元";
//                        this.throwException(title, standardData, businessData);
//                        return false;
//                }
//                else if ((heBaoConditionDto.getAmountD1()>-1)&&
//                        (heBaoConditionDto.getAmountD1() < blBusinessData
//                            .getAmountD11())) {
//                        String title = "车上人员责任险(驾驶人)不符合条件!";
//                        String standardData = "小于等于"
//                                + NumberFormat.getInstance().format(
//                                        heBaoConditionDto.getAmountD1())
//                                + "元";
//                        String businessData = NumberFormat.getInstance().format(
//                                blBusinessData.getAmountD11())
//                                + "元";
//                        this.throwException(title, standardData, businessData);
//                        return false;
//                }
//                else if ((heBaoConditionDto.getAmountD1()>-1)&&
//                        (heBaoConditionDto.getAmountD1() < blBusinessData
//                            .getAmountD12())) {
//                        String title = "车上人员责任险(乘客)不符合条件!";
//                        String standardData = "小于等于"
//                                + NumberFormat.getInstance().format(
//                                        heBaoConditionDto.getAmountD1())
//                                + "元";
//                        String businessData = NumberFormat.getInstance().format(
//                                blBusinessData.getAmountD12())
//                                + "元";
//                        this.throwException(title, standardData, businessData);
//                        return false;
//                }
//                else if ((heBaoConditionDto.getAmountAMJ()>-1)&&
//                        (heBaoConditionDto.getAmountAMJ() < blBusinessData
//                            .getAmountA())) {
//                        String title = "车损最大保额不符合条件!";
//                        String standardData = "小于等于"
//                                + NumberFormat.getInstance().format(
//                                        heBaoConditionDto.getAmountAMJ())
//                                + "元";
//                        String businessData = NumberFormat.getInstance().format(
//                                blBusinessData.getAmountA())
//                                + "元";
//                        this.throwException(title, standardData, businessData);
//                        return false;
//                }
//                else if ((heBaoConditionDto.getAmountGMJ()>-1)&&
//                        (heBaoConditionDto.getAmountGMJ() < blBusinessData
//                            .getAmountG()) ){
//                        String title = "全车盗抢险最大保额/赔偿限额（元）的值不符合条件!";
//                        String standardData = "小于等于"
//                                + NumberFormat.getInstance().format(
//                                        heBaoConditionDto.getAmountG())
//                                + "元";
//                        String businessData = NumberFormat.getInstance().format(
//                                blBusinessData.getAmountG())
//                                + "元";
//                        this.throwException(title, standardData, businessData);
//                        return false;
//                }
//                else if ((heBaoConditionDto.getAmountBMJ()>-1)&&
//                        (heBaoConditionDto.getAmountBMJ() < blBusinessData
//                            .getAmountB())) {
//                        String title = "三者险赔偿限额综合不符合条件!";
//                        String standardData = "小于等于"
//                                + NumberFormat.getInstance().format(
//                                        heBaoConditionDto.getAmountB())
//                                + "元";
//                        String businessData = NumberFormat.getInstance().format(
//                                blBusinessData.getAmountB())
//                                + "元";
//                        this.throwException(title, standardData, businessData);
//                        return false;
//                }
//                else if ((heBaoConditionDto.getAmountD11MJ()>-1)&&
//                        (heBaoConditionDto.getAmountD11MJ() < blBusinessData
//                            .getAmountD11())) {
//                        String title = "车上人员责任险(驾驶人)不符合条件!";
//                        String standardData = "小于等于"
//                                + NumberFormat.getInstance().format(
//                                        heBaoConditionDto.getAmountD1())
//                                + "元";
//                        String businessData = NumberFormat.getInstance().format(
//                                blBusinessData.getAmountD11())
//                                + "元";
//                        this.throwException(title, standardData, businessData);
//                        return false;
//                }
//                else if ((heBaoConditionDto.getAmountD11MJ()>-1)&&
//                        (heBaoConditionDto.getAmountD11MJ() < blBusinessData
//                            .getAmountD12())) {
//                        String title = "车上人员责任险(乘客)不符合条件!";
//                        String standardData = "小于等于"
//                                + NumberFormat.getInstance().format(
//                                        heBaoConditionDto.getAmountD1())
//                                + "元";
//                        String businessData = NumberFormat.getInstance().format(
//                                blBusinessData.getAmountD12())
//                                + "元";
//                        this.throwException(title, standardData, businessData);
//                        return false;
//                }
//                else if ((heBaoConditionDto.getAmountManSum()>-1)&&
//                        (heBaoConditionDto.getAmountManSum() < blBusinessData
//                                .getAmountManSum())){
//                    String title = "车上人员责任险总保额不符合条件!";
//                    String standardData = "小于等于"
//                            + NumberFormat.getInstance().format(
//                                    heBaoConditionDto.getAmountManSum())
//                            + "元";
//                    String businessData = NumberFormat.getInstance().format(
//                            blBusinessData.getAmountManSum())
//                            + "元";
//                    this.throwException(title, standardData, businessData);
//                    return false;
//                }
//                else if ((heBaoConditionDto.getAmountD2()>-1)&&
//                        (heBaoConditionDto.getAmountD2() < blBusinessData
//                            .getAmountD2())) {
//                        String title = "车上货物责任险不符合条件!";
//                        String standardData = "小于等于"
//                                + NumberFormat.getInstance().format(
//                                        heBaoConditionDto.getAmountD2())
//                                + "元";
//                        String businessData = NumberFormat.getInstance().format(
//                                blBusinessData.getAmountD2())
//                                + "元";
//                        this.throwException(title, standardData, businessData);
//                        return false;
//                }
//                else if ((heBaoConditionDto.getAmountW()>-1)&&
//                        (heBaoConditionDto.getAmountW() < blBusinessData
//                            .getAmountW())) {
//                        String title = "随行物品损失保险金额不符合条件!";
//                        String standardData = "小于等于"
//                                + NumberFormat.getInstance().format(
//                                        heBaoConditionDto.getAmountW())
//                                + "元";
//                        String businessData = NumberFormat.getInstance().format(
//                                blBusinessData.getAmountW())
//                                + "元";
//                        this.throwException(title, standardData, businessData);
//                        return false;
//                }
//                else if ((heBaoConditionDto.getAmountG1()>-1)&&
//                        (heBaoConditionDto.getAmountG1() < blBusinessData
//                            .getAmountG1())) {
//                        //String title = "第四类特种车盗抢险保额不符合条件!";
//                        String title = "特种车盗抢险保额不符合条件!";
//                        String standardData = "小于等于"
//                                + NumberFormat.getInstance().format(
//                                        heBaoConditionDto.getAmountG1())
//                                + "元";
//                        String businessData = NumberFormat.getInstance().format(
//                                blBusinessData.getAmountG1())
//                                + "元";
//                        this.throwException(title, standardData, businessData);
//                        return false;
//                }
//                else if ((heBaoConditionDto.getAmountAG1()>-1)&&
//                        (heBaoConditionDto.getAmountAG1() < blBusinessData
//                            .getAmountAG1())) {
//                        //String title = "第四类特种车车损险保额不符合条件!";
//                        String title = "特种车车损险保额不符合条件!";
//                        String standardData = "小于等于"
//                                + NumberFormat.getInstance().format(
//                                        heBaoConditionDto.getAmountAG1())
//                                + "元";
//                        String businessData = NumberFormat.getInstance().format(
//                                blBusinessData.getAmountAG1())
//                                + "元";
//                        this.throwException(title, standardData, businessData);
//                        return false;
//                }
//                else if ((heBaoConditionDto.getAmountBG1()>-1)&&
//                        (heBaoConditionDto.getAmountBG1() < blBusinessData
//                            .getAmountBG1())) {
//                        //String title = "第四类特种车三者险保额不符合条件!";
//                        String title = "特种车三者险保额不符合条件!";
//                        String standardData = "小于等于"
//                                + NumberFormat.getInstance().format(
//                                        heBaoConditionDto.getAmountBG1())
//                                + "元";
//                        String businessData = NumberFormat.getInstance().format(
//                                blBusinessData.getAmountBG1())
//                                + "元";
//                        this.throwException(title, standardData, businessData);
//                        return false;
//                }
//                //modify by zhangruifeng 20080304 begin 增加车损险净自留额的控制
//                else if ((heBaoConditionDto.getSuttleAmountA ()>-1)&&
//                        (heBaoConditionDto.getSuttleAmountA () < blBusinessData
//                            .getSuttleAmountA ())) {
//                        String title = "车损险净自留额不符合条件，请进行分保!";
//                        String standardData = "小于等于"
//                                + NumberFormat.getInstance().format(
//                                        heBaoConditionDto.getSuttleAmountA ())
//                                + "元";
//                        String businessData = NumberFormat.getInstance().format(
//                                blBusinessData.getSuttleAmountA ())
//                                + "元";
//                        this.throwException(title, standardData, businessData);
//                        return false;
//                }
//                else if ((heBaoConditionDto.getSuttleAmountB ()>-1)&&
//                        (heBaoConditionDto.getSuttleAmountB () < blBusinessData
//                            .getSuttleAmountB ())) {
//                        String title = "三者险净自留额不符合条件，请进行分保!";
//                        String standardData = "小于等于"
//                                + NumberFormat.getInstance().format(
//                                        heBaoConditionDto.getSuttleAmountB ())
//                                + "元";
//                        String businessData = NumberFormat.getInstance().format(
//                                blBusinessData.getSuttleAmountB ())
//                                + "元";
//                        this.throwException(title, standardData, businessData);
//                        return false;
//                }
//                else if ((heBaoConditionDto.getAmountNew()>-1) && ((blBusinessData.getAmountA() * 0.05) < blBusinessData.getAmountNew())){
//                    String title = "新增设备金额不符合条件!";
//                    String standardData = "小于等于车损险保额5%的业务";
//                    String businessData = NumberFormat.getInstance().format(
//                            blBusinessData.getAmountNew ())
//                            + "元";
//                    this.throwException(title, standardData, businessData);
//                    return false;
//                }
//                else if ((heBaoConditionDto.getChooseAPrice()>-1)&&
//                        (heBaoConditionDto.getChooseAPrice() < blBusinessData
//                                .getChooseAPrice())){
//                    String title = "投保车损险的新车购置价不符合条件!";
//                    String standardData = "小于等于"
//                            + NumberFormat.getInstance().format(
//                                    heBaoConditionDto.getChooseAPrice ())
//                            + "元";
//                    String businessData = NumberFormat.getInstance().format(
//                            blBusinessData.getChooseAPrice ())
//                            + "元";
//                    this.throwException(title, standardData, businessData);
//                    return false;
//                }
//                else if ((heBaoConditionDto.getTonCountH0()>-1)&&
//                        (heBaoConditionDto.getTonCountH0() < blBusinessData
//                                .getTonCountH0())){
//                    String title = "货车核定载质量不符合条件!";
//                    String standardData = "小于等于"
//                            + NumberFormat.getInstance().format(
//                                    heBaoConditionDto.getTonCountH0 ())
//                            + "吨";
//                    String businessData = NumberFormat.getInstance().format(
//                            blBusinessData.getTonCountH0 ())
//                            + "吨";
//                    this.throwException(title, standardData, businessData);
//                    return false;
//                }
//                else if ((heBaoConditionDto.getAmountL()>-1)&&
//                        (heBaoConditionDto.getAmountL() < blBusinessData
//                                .getAmountL())){
//                    String title = "车身划痕险保额不符合条件!";
//                    String standardData = "小于等于"
//                            + NumberFormat.getInstance().format(
//                                    heBaoConditionDto.getAmountL())
//                            + "元";
//                    String businessData = NumberFormat.getInstance().format(
//                            blBusinessData.getAmountL())
//                            + "元";
//                    this.throwException(title, standardData, businessData);
//                    return false;
//                }
//                else if ((heBaoConditionDto.getAmountR()>-1)&&
//                        (heBaoConditionDto.getAmountR() < blBusinessData
//                                .getAmountR())){
//                    String title = "交通事故精神损害赔偿责任险保额不符合条件!";
//                    String standardData = "小于等于"
//                            + NumberFormat.getInstance().format(
//                                    heBaoConditionDto.getAmountR())
//                            + "元";
//                    String businessData = NumberFormat.getInstance().format(
//                            blBusinessData.getAmountR())
//                            + "元";
//                    this.throwException(title, standardData, businessData);
//                    return false;
//                }
//                else if ((heBaoConditionDto.getAmountRPer()>-1)&&
//                        (heBaoConditionDto.getAmountRPer() < blBusinessData
//                                .getAmountRPer())){
//                    String title = "交通事故精神损害赔偿责任险每人每次限额不符合条件!";
//                    String standardData = "小于等于"
//                            + NumberFormat.getInstance().format(
//                                    heBaoConditionDto.getAmountRPer())
//                            + "元";
//                    String businessData = NumberFormat.getInstance().format(
//                            blBusinessData.getAmountRPer())
//                            + "元";
//                    this.throwException(title, standardData, businessData);
//                    return false;
//                }
//           }
//           // add by xuning gpic 20080411 0505提车险控制总保额，0504不控制 begin 
//           if(blBusinessData.getRiskCode().equals("0505")){
//                        if ((heBaoConditionDto.getSumAmount()>-1)
//                                &&(heBaoConditionDto.getSumAmount() < blBusinessData
//                                .getSumAmount())){
//                            String title = "总保额不符合条件!";
//                            String standardData = "小于等于"
//                                    + NumberFormat.getInstance().format(
//                                            heBaoConditionDto.getSumAmount())
//                                    + "元";
//                            String businessData = NumberFormat.getInstance().format(
//                                    blBusinessData.getSumAmount())
//                                    + "元";
//                            this.throwException(title, standardData, businessData);
//                            return false;
//                        }
//           }
//          // add by xuning gpic 20080411 0505提车险控制总保额，0504不控制 end
//           
//           if(blBusinessData.getRiskCode().equals("0502")||blBusinessData.getRiskCode().equals("0503")){
//               if (!heBaoConditionDto.getUsingProperty().equals("999")){
//                    String title = "审核的业务类型不符合条件!";
//                    String standardData = "审核非特种车、摩托车、拖拉机的业务!";
//                    String businessData = "特种车、摩托车、拖拉机的业务";
//                    this.throwException(title, standardData, businessData);
//                    return false;
//               }
//           }
//           else if(blBusinessData.getRiskCode().equals("0501")){
//               if((!heBaoConditionDto.getUsingProperty().equals("999"))//非车的置为999和车险所有权限用
//                    && (!blBusinessData.getUseNatureCode().equals(""))
//                    && (heBaoConditionDto.getUsingProperty().indexOf(blBusinessData.getUseNatureCode())<0)) {
//                String title = "使用性质不符合条件!";
//                String standardData = "非营业性";
//                String businessData = "营业车辆";
//                this.throwException(title, standardData, businessData);
//                return false;
//               }
//           }
//       }
//        /*************************************************非车部分**************************************************************************/
//       if(blBusinessData.getClassCode().equals("03")){
//           if ((heBaoConditionDto.getAmountPer03010001()>-1)&&
//                (heBaoConditionDto.getAmountPer03010001() < blBusinessData
//                    .getAmountPer03010001())){
//                String title = "房屋及附属设备保险金额/每户不符合条件!";
//                String standardData = "小于等于"
//                        + NumberFormat.getInstance().format(
//                                heBaoConditionDto.getAmountPer03010001())
//                        + "元";
//                String businessData = NumberFormat.getInstance().format(
//                        blBusinessData.getAmountPer03010001())
//                        + "元";
//                this.throwException(title, standardData, businessData);
//                return false;
//            }
//            else if ((heBaoConditionDto.getAmoutPer03010002()>-1)&&
//                        (heBaoConditionDto.getAmoutPer03010002() < blBusinessData
//                            .getAmountPer03010002())) {
//                String title = "室内装潢保险金额/每户不符合条件!";
//                String standardData = "小于等于"
//                        + NumberFormat.getInstance().format(
//                                heBaoConditionDto.getAmoutPer03010002())
//                        + "元";
//                String businessData = NumberFormat.getInstance().format(
//                        blBusinessData.getAmountPer03010002())
//                        + "元";
//                this.throwException(title, standardData, businessData);
//                return false;
//            }
//            else if((heBaoConditionDto.getAmountPer9000452()>-1)&&
//                    (heBaoConditionDto.getAmountPer9000452() < blBusinessData
//                            .getAmountPer9000452())){
//                String title = "附加盗抢保险条款金额/每户不符合条件!";
//                String standardData = "小于等于"
//                        + NumberFormat.getInstance().format(
//                                heBaoConditionDto.getAmountPer9000452())
//                        + "元";
//                String businessData = NumberFormat.getInstance().format(
//                        blBusinessData.getAmountPer9000452())
//                        + "元";
//                this.throwException(title, standardData, businessData);
//                return false;               
//            }
//            else if((heBaoConditionDto.getAmountPer9000453()>-1)&&
//                    (heBaoConditionDto.getAmountPer9000453() < blBusinessData
//                            .getAmountPer9000453())){
//                String title = "附加家用电器用电安全保险条款金额/每户不符合条件!";
//                String standardData = "小于等于"
//                        + NumberFormat.getInstance().format(
//                                heBaoConditionDto.getAmountPer9000453())
//                        + "元";
//                String businessData = NumberFormat.getInstance().format(
//                        blBusinessData.getAmountPer9000453())
//                        + "元";
//                this.throwException(title, standardData, businessData);
//                return false;               
//            }
//            else if((heBaoConditionDto.getAmountPer9000454()>-1)&&
//                    (heBaoConditionDto.getAmountPer9000454() < blBusinessData
//                            .getAmountPer9000454())){
//                String title = "附加管道破裂及水渍保险条款金额/每户不符合条件!";
//                String standardData = "小于等于"
//                        + NumberFormat.getInstance().format(
//                                heBaoConditionDto.getAmountPer9000454())
//                        + "元";
//                String businessData = NumberFormat.getInstance().format(
//                        blBusinessData.getAmountPer9000454())
//                        + "元";
//                this.throwException(title, standardData, businessData);
//                return false;               
//            }
//            else if((heBaoConditionDto.getAmountPer9000449()>-1)&&
//                    (heBaoConditionDto.getAmountPer9000449() < blBusinessData
//                            .getAmountPer9000449())){
//                String title = "附加居家责任保险条款金额/每户不符合条件!";
//                String standardData = "小于等于"
//                        + NumberFormat.getInstance().format(
//                                heBaoConditionDto.getAmountPer9000449())
//                        + "元";
//                String businessData = NumberFormat.getInstance().format(
//                        blBusinessData.getAmountPer9000449())
//                        + "元";
//                this.throwException(title, standardData, businessData);
//                return false;               
//            }
//            else if((heBaoConditionDto.getAmountPer9000450()>-1)&&
//                    (heBaoConditionDto.getAmountPer9000450() < blBusinessData
//                            .getAmountPer9000450())){
//                String title = "附加家庭伤害保险条款金额/每户不符合条件!";
//                String standardData = "小于等于"
//                        + NumberFormat.getInstance().format(
//                                heBaoConditionDto.getAmountPer9000450())
//                        + "元";
//                String businessData = NumberFormat.getInstance().format(
//                        blBusinessData.getAmountPer9000450())
//                        + "元";
//                this.throwException(title, standardData, businessData);
//                return false;               
//            }
//            else if((heBaoConditionDto.getAmountPer9000451()>-1)&&
//                    (heBaoConditionDto.getAmountPer9000451() < blBusinessData
//                            .getAmountPer9000451())){
//                String title = "附加家庭意外骨折医疗保险条款金额/每户不符合条件!";
//                String standardData = "小于等于"
//                        + NumberFormat.getInstance().format(
//                                heBaoConditionDto.getAmountPer9000451())
//                        + "元";
//                String businessData = NumberFormat.getInstance().format(
//                        blBusinessData.getAmountPer9000451())
//                        + "元";
//                this.throwException(title, standardData, businessData);
//                return false;               
//            }
//            //add by hanxiao 20091027 begin 03家财险类0307增加每人保额03险类其他险种可以不配置
//            if ((heBaoConditionDto.getUnitAmount03()>-1)&&heBaoConditionDto.getUnitAmount03() < blBusinessData
//                    .getUnitAmount03()){
//               String title = "每人保额不符合条件!";
//                String standardData = "小于等于"
//                        + NumberFormat.getInstance().format(
//                                heBaoConditionDto.getUnitAmount03())
//                        + "元";
//                String businessData = NumberFormat.getInstance().format(
//                        blBusinessData.getUnitAmount03())
//                        + "元";
//                this.throwException(title, standardData, businessData);
//                return false;
//            }
//       }
//       //07建工险
//       if(blBusinessData.getClassCode().equals("07")){
//           System.out.println("heBaoConditionDto.getThirdLimitSum07()=="+heBaoConditionDto.getThirdLimitSum07());
//           System.out.println("blBusinessData.getThirdLimitSum07()=="+blBusinessData.getThirdLimitSum07());
//        if ((heBaoConditionDto.getThirdLimitSum07()>-1)&&
//                (heBaoConditionDto.getThirdLimitSum07() < blBusinessData
//                    .getThirdLimitSum07())){
//                String title = "第三者累计赔偿限额不符合条件!";
//                String standardData = "小于等于"
//                        + NumberFormat.getInstance().format(
//                                heBaoConditionDto.getThirdLimitSum07())
//                        + "元";
//                String businessData = NumberFormat.getInstance().format(
//                        blBusinessData.getThirdLimitSum07())
//                        + "元";
//                this.throwException(title, standardData, businessData);
//                return false;
//            }
//            else if ((heBaoConditionDto.getThirdLimitAcc07()>-1)&&
//                    (heBaoConditionDto.getThirdLimitAcc07() < blBusinessData
//                    .getThirdLimitAcc07())) {
//                String title = "第三者责任每次事故赔偿限额不符合条件!";
//                String standardData = "小于等于"
//                        + NumberFormat.getInstance().format(
//                                heBaoConditionDto.getThirdLimitAcc07())
//                        + "元";
//                String businessData = NumberFormat.getInstance().format(
//                        blBusinessData.getThirdLimitAcc07())
//                        + "元";
//                this.throwException(title, standardData, businessData);
//                return false;
//            }
//            
//          }
//       //09  0907国内水路、陆路货物运输保险、0908物流货物保险
//       if(blBusinessData.getClassCode().equals("09")){
//           if ((heBaoConditionDto.getSumAmount()>-1)&&
//                    (heBaoConditionDto.getSumAmount() < blBusinessData
//                        .getSumAmount())){
//                    String title = "运次保险金额不符合条件!";
//                    String standardData = "小于等于"
//                            + NumberFormat.getInstance().format(
//                                    heBaoConditionDto.getSumAmount())
//                            + "元";
//                    String businessData = NumberFormat.getInstance().format(
//                            blBusinessData.getSumAmount())
//                            + "元";
//                    this.throwException(title, standardData, businessData);
//                    return false;
//                }
//           else if (heBaoConditionDto.getPrepayProtocol().equals("N") && blBusinessData
//                    .getPrepayProtocol().equals("N")) {
//                String title = "是否允许预约协议不符合条件!";
//                String standardData = "无权审核预约协议";
//                String businessData = "预约协议";
//                this.throwException(title, standardData, businessData);
//                return false;
//            }
//       }
//       //10进出口货运险
//       if(blBusinessData.getClassCode().equals("10")|| blBusinessData.getClassCode().equals("09")){
//           System.out.println("heBaoConditionDto.getShipAge()=="+heBaoConditionDto.getShipAge());
//           System.out.println("blBusinessData.getPlusRate()=="+blBusinessData.getShipAge());
//           System.out.println("heBaoConditionDto.getPlusRate()==="+heBaoConditionDto.getPlusRate());
//           System.out.println("blBusinessData.getPlusRate()==="+blBusinessData.getPlusRate());
//        if ((heBaoConditionDto.getSumAmount()>-1)&&
//                (heBaoConditionDto.getSumAmount() < blBusinessData
//                    .getSumAmount())){
//                String title = "运次保险金额不符合条件!";
//                String standardData = "小于等于"
//                        + NumberFormat.getInstance().format(
//                                heBaoConditionDto.getSumAmount())
//                        + "元";
//                String businessData = NumberFormat.getInstance().format(
//                        blBusinessData.getSumAmount())
//                        + "元";
//                this.throwException(title, standardData, businessData);
//                return false;
//            }
//            else if ((heBaoConditionDto.getPlusRate()>-1)&&
//                    (heBaoConditionDto.getPlusRate() < blBusinessData
//                    .getPlusRate())) {
//                String title = "加成比例不符合条件!";
//                String standardData = "小于等于"
//                        + NumberFormat.getInstance().format(
//                                heBaoConditionDto.getPlusRate());
//                String businessData = NumberFormat.getInstance().format(
//                        blBusinessData.getPlusRate());
//                this.throwException(title, standardData, businessData);
//                return false;
//            }
//            else if (blBusinessData.getRiskCode().equals("0907")&&(heBaoConditionDto.getShipAge()>-1)&&
//                    (heBaoConditionDto.getShipAge() < blBusinessData
//                    .getShipAge())) {
//                String title = "船龄不符合条件!";
//                String standardData = "小于等于"
//                        + NumberFormat.getInstance().format(
//                                heBaoConditionDto.getShipAge())
//                        + "年";
//                String businessData = NumberFormat.getInstance().format(
//                        blBusinessData.getShipAge())
//                        + "年";
//                this.throwException(title, standardData, businessData);
//                return false;
//            }
//             else if (blBusinessData.getRiskCode().equals("1001")&&(heBaoConditionDto.getShipAge()>-1)&&
//                        (heBaoConditionDto.getShipAge() < blBusinessData
//                        .getShipAge())) {
//                    String title = "船龄不符合条件!";
//                    String standardData = "小于等于"
//                            + NumberFormat.getInstance().format(
//                                    heBaoConditionDto.getShipAge())
//                            + "年";
//                    String businessData = blBusinessData.getShipAge1001();
//                    this.throwException(title, standardData, businessData);
//                    return false;
//                }
//             //     added by liuwei begin 20090303 0911国内货运险（08版）增加附加险核保因子
//             else if (heBaoConditionDto.getAllow0911700().equals("N") && blBusinessData
//                    .getAllow0911700().equals("Y")) {
//                 String title = "附加提货不着扩展条款核保级别不够!";
//                 String standardData = "此附加险需要总公司一级C以上才能审核通过!";
//                 String businessData = "分公司级别";
//                   this.throwException(title, standardData, businessData);
//                return false;
//            }
//             //     added by liuwei end 20090303
//            else if (heBaoConditionDto.getPrepayProtocol().equals("N") && blBusinessData
//                    .getPrepayProtocol().equals("N")) {
//                String title = "是否允许预约协议不符合条件!";
//                String standardData = "无权审核预约协议";
//                String businessData = "预约协议";
//                this.throwException(title, standardData, businessData);
//                return false;
//            }
//          }
//       //11 船舶建造险
//       if(blBusinessData.getClassCode().equals("11")){
//           if((heBaoConditionDto.getSumAmount()>-1) && (heBaoConditionDto.getSumAmount() < blBusinessData.getSumAmount())){
//               String title = "总保额不符合条件!";
//               String standardData = "小于等于"
//                        + NumberFormat.getInstance().format(
//                                heBaoConditionDto.getSumAmount())
//                        + "元";
//               String businessData = NumberFormat.getInstance().format(
//                        blBusinessData.getSumAmount())
//                        + "元";
//               this.throwException(title, standardData, businessData);
//               return false;
//           }
//           //1103险种
//           if(blBusinessData.getRiskCode().equals("1103")){
//               if ((heBaoConditionDto.getPlusRate()>-1)&&
//                        (heBaoConditionDto.getPlusRate() < blBusinessData
//                        .getPlusRate())) {
//                    String title = "加成比例不符合条件!";
//                    String standardData = "小于等于"
//                            + NumberFormat.getInstance().format(
//                                    heBaoConditionDto.getPlusRate());
//                    String businessData = NumberFormat.getInstance().format(
//                            blBusinessData.getPlusRate());
//                    this.throwException(title, standardData, businessData);
//                    return false;
//                }
//           }
//           //1102险种
////         if(blBusinessData.getRiskCode().equals("1102")||blBusinessData.getRiskCode().equals("1103")){
////             if((heBaoConditionDto.getSumAmount()>-1) && (heBaoConditionDto.getSumAmount() < blBusinessData.getSumAmount())){
////                 String title = "总保额不符合条件!";
////                 String standardData = "小于等于"
////                          + NumberFormat.getInstance().format(
////                                  heBaoConditionDto.getSumAmount())
////                          + "元";
////                 String businessData = NumberFormat.getInstance().format(
////                          blBusinessData.getSumAmount())
////                          + "元";
////                 this.throwException(title, standardData, businessData);
////                 return false;
////             }
////         }
//       }
//         //15责任险类
//       if(blBusinessData.getClassCode().equals("15")){
//        if ((heBaoConditionDto.getLimitManAcc01()>-1)&&
//                (heBaoConditionDto.getLimitManAcc01() < blBusinessData
//                    .getLimitManAcc01())){
//                //String title = "每人事故赔偿限额不符合条件!";
//                String title = "每人责任限额不符合条件!";
//                String standardData = "小于等于"
//                        + NumberFormat.getInstance().format(
//                                heBaoConditionDto.getLimitManAcc01())
//                        + "元";
//                String businessData = NumberFormat.getInstance().format(
//                        blBusinessData.getLimitManAcc01())
//                        + "元";
//                this.throwException(title, standardData, businessData);
//                return false;
//            }
//            else if ((heBaoConditionDto.getLimitAcc12()>-1)&&
//                    (heBaoConditionDto.getLimitAcc12() < blBusinessData
//                    .getLimitAcc12())) {
//                String title = "每次事故赔偿限额不符合条件!";
//                String standardData = "小于等于"
//                        + NumberFormat.getInstance().format(
//                                heBaoConditionDto.getLimitAcc12())
//                        + "元";
//                String businessData = NumberFormat.getInstance().format(
//                        blBusinessData.getLimitAcc12())
//                        + "元";
//                this.throwException(title, standardData, businessData);
//                return false;
//            }
//            //added by xiongguojun 20090909 增加15险类对每人人身伤亡责任限额的控制    begin
//            else if ((heBaoConditionDto.getLimitManAcc05()>-1)&&
//                    (heBaoConditionDto.getLimitManAcc05() < blBusinessData
//                            .getLimitManAcc05())) {
//                String title = "每人人身伤亡责任限额不符合条件!";
//                String standardData = "小于等于"
//                    + NumberFormat.getInstance().format(
//                            heBaoConditionDto.getLimitManAcc05())
//                            + "元";
//                String businessData = NumberFormat.getInstance().format(
//                        blBusinessData.getLimitManAcc05())
//                        + "元";
//                this.throwException(title, standardData, businessData);
//                return false;
//            }
//            //added by xiongguojun 20090909 增加15险类对每人人身伤亡责任限额的控制    end
//            else if ((heBaoConditionDto.getLimitAcc03()>-1)&&
//                    (heBaoConditionDto.getLimitAcc03() < blBusinessData
//                            .getLimitAcc03())){
//                String title = "每次事故财产损失赔偿限额不符合条件!";
//                String standardData = "小于等于"
//                        + NumberFormat.getInstance().format(
//                                heBaoConditionDto.getLimitAcc03())
//                        + "元";
//                String businessData = NumberFormat.getInstance().format(
//                        blBusinessData.getLimitAcc03())
//                        + "元";
//                this.throwException(title, standardData, businessData);
//                return false;               
//            }
//            else if ((heBaoConditionDto.getSumAmount()>-1)&&
//                    (heBaoConditionDto.getSumAmount() < blBusinessData
//                            .getSumAmount())) {
//                        //String title = "累计赔偿限额不符合条件!";
//                        String title = "总累计责任限额不符合条件!";
//                        String standardData = "小于等于"
//                                + NumberFormat.getInstance().format(
//                                        heBaoConditionDto.getSumAmount())
//                                + "元";
//                        String businessData = NumberFormat.getInstance().format(
//                                blBusinessData.getSumAmount())
//                                + "元";
//                        this.throwException(title, standardData, businessData);
//                        return false;
//                    }
//            //1506道路危险货物承运人
//             else if ((heBaoConditionDto.getLimitCargoAcc()>-1)&&
//                    (heBaoConditionDto.getLimitCargoAcc() < blBusinessData
//                    .getLimitCargoAcc())) {
//                String title = "每次事故赔偿限额不符合条件!";
//                String standardData = "小于等于"
//                        + NumberFormat.getInstance().format(
//                                heBaoConditionDto.getLimitCargoAcc())
//                        + "元";
//                String businessData = NumberFormat.getInstance().format(
//                        blBusinessData.getLimitCargoAcc())
//                        + "元";
//                this.throwException(title, standardData, businessData);
//                return false;
//            }
//            //added by xiongguojun 20090327 1506附加核乏料运输第三者责任保险条款 begin
//            else if ((heBaoConditionDto.getLimitThirdAcc4()>-1)&&
//                    (heBaoConditionDto.getLimitThirdAcc4() < blBusinessData
//                    .getLimitThirdAcc4())) {
//                String title = "核乏料运输第三者责任保险条款累计责任限额不符合条件!";
//                String standardData = "小于等于"
//                        + NumberFormat.getInstance().format(
//                                heBaoConditionDto.getLimitThirdAcc4())
//                        + "元";
//                String businessData = NumberFormat.getInstance().format(
//                        blBusinessData.getLimitThirdAcc4())
//                        + "元";
//                this.throwException(title, standardData, businessData);
//                return false;
//            }
//            //added by xiongguojun 20090327 1506附加核乏料运输第三者责任保险条款 end
//             else if ((heBaoConditionDto.getLimitThirdAcc()>-1)&&
//                    (heBaoConditionDto.getLimitThirdAcc() < blBusinessData
//                    .getLimitThirdAcc())) {
//                String title = "每次事故赔偿限额不符合条件!";
//                String standardData = "小于等于"
//                        + NumberFormat.getInstance().format(
//                                heBaoConditionDto.getLimitThirdAcc())
//                        + "元";
//                String businessData = NumberFormat.getInstance().format(
//                        blBusinessData.getLimitThirdAcc())
//                        + "元";
//                this.throwException(title, standardData, businessData);
//                return false;
//            }
//              else if ((heBaoConditionDto.getLimitThirdAcc2()>-1)&&
//                    (heBaoConditionDto.getLimitThirdAcc2() < blBusinessData
//                    .getLimitThirdAcc2())) {
//                String title = "每次事故赔偿限额不符合条件!";
//                String standardData = "小于等于"
//                        + NumberFormat.getInstance().format(
//                                heBaoConditionDto.getLimitThirdAcc2())
//                        + "元";
//                String businessData = NumberFormat.getInstance().format(
//                        blBusinessData.getLimitThirdAcc2())
//                        + "元";
//                this.throwException(title, standardData, businessData);
//                return false;
//            }
//           else if ((heBaoConditionDto.getLimitThirdAcc2()>-1)&&
//                    (heBaoConditionDto.getLimitThirdAcc2() < blBusinessData
//                    .getLimitThirdAcc2())) {
//                String title = "每次事故赔偿限额不符合条件!";
//                String standardData = "小于等于"
//                        + NumberFormat.getInstance().format(
//                                heBaoConditionDto.getLimitThirdAcc2())
//                        + "元";
//                String businessData = NumberFormat.getInstance().format(
//                        blBusinessData.getLimitThirdAcc2())
//                        + "元";
//                this.throwException(title, standardData, businessData);
//                return false;
//            }else if ((heBaoConditionDto.getLimitThirdAccB()>-1)&&
//                    (heBaoConditionDto.getLimitThirdAccB() < blBusinessData
//                            .getLimitThirdAccB())) {
//                        String title = "每次事故赔偿限额不符合条件!";
//                        String standardData = "小于等于"
//                                + NumberFormat.getInstance().format(
//                                        heBaoConditionDto.getLimitThirdAccB())
//                                + "元";
//                        String businessData = NumberFormat.getInstance().format(
//                                blBusinessData.getLimitThirdAccB())
//                                + "元";
//                        this.throwException(title, standardData, businessData);
//                        return false;
//                    //1515,1526每次事故赔偿限额
//                    }else if ((heBaoConditionDto.getLimitAcc1()>-1)&&
//                            (heBaoConditionDto.getLimitAcc1() < blBusinessData
//                                    .getLimitAcc1())) {
//                                String title = "每次事故赔偿限额不符合条件!";
//                                String standardData = "小于等于"
//                                        + NumberFormat.getInstance().format(
//                                                heBaoConditionDto.getLimitAcc1())
//                                        + "元";
//                                String businessData = NumberFormat.getInstance().format(
//                                        blBusinessData.getLimitAcc1())
//                                        + "元";
//                                this.throwException(title, standardData, businessData);
//                                return false;
//                    }else if ((heBaoConditionDto.getLimitAcc2()>-1)&&
//                            (heBaoConditionDto.getLimitAcc2() < blBusinessData
//                                    .getLimitAcc2())) {
//                        //System.out.println("heBaoConditionDto.getLimitAcc2()="+heBaoConditionDto.getLimitAcc2());
//                                String title = "每次事故赔偿限额不符合条件!";
//                                String standardData = "小于等于"
//                                        + NumberFormat.getInstance().format(
//                                                heBaoConditionDto.getLimitAcc2())
//                                        + "元";
//                                String businessData = NumberFormat.getInstance().format(
//                                        blBusinessData.getLimitAcc2())
//                                        + "元";
//                                this.throwException(title, standardData, businessData);
//                                return false;
//                    }else if ((heBaoConditionDto.getLimitAcc3()>-1)&&
//                            (heBaoConditionDto.getLimitAcc3() < blBusinessData
//                                    .getLimitAcc3())) {
//                                String title = "每次事故赔偿限额不符合条件!";
//                                String standardData = "小于等于"
//                                        + NumberFormat.getInstance().format(
//                                                heBaoConditionDto.getLimitAcc3())
//                                        + "元";
//                                String businessData = NumberFormat.getInstance().format(
//                                        blBusinessData.getLimitAcc3())
//                                        + "元";
//                                this.throwException(title, standardData, businessData);
//                                return false;
//                    }else if ((heBaoConditionDto.getSumAmount3()>-1)&&
//                            (heBaoConditionDto.getSumAmount3() < blBusinessData
//                                    .getSumAmount3())) {
//                                String title = "累计赔偿限额不符合条件!";
//                                String standardData = "小于等于"
//                                        + NumberFormat.getInstance().format(
//                                                heBaoConditionDto.getSumAmount3())
//                                        + "元";
//                                String businessData = NumberFormat.getInstance().format(
//                                        blBusinessData.getSumAmount3())
//                                        + "元";
//                                this.throwException(title, standardData, businessData);
//                                return false;
//                    }else if ((heBaoConditionDto.getSumAmount1()>-1)&&
//                            (heBaoConditionDto.getSumAmount1() < blBusinessData
//                                    .getSumAmount1())) {
//                                String title = "累计赔偿限额不符合条件!";
//                                String standardData = "小于等于"
//                                        + NumberFormat.getInstance().format(
//                                                heBaoConditionDto.getSumAmount1())
//                                        + "元";
//                                String businessData = NumberFormat.getInstance().format(
//                                        blBusinessData.getSumAmount1())
//                                        + "元";
//                                this.throwException(title, standardData, businessData);
//                                return false;
//                    }else if ((heBaoConditionDto.getSumAmount2()>-1)&&
//                            (heBaoConditionDto.getSumAmount2() < blBusinessData
//                                    .getSumAmount2())) {
//                                String title = "累计赔偿限额不符合条件!";
//                                String standardData = "小于等于"
//                                        + NumberFormat.getInstance().format(
//                                                heBaoConditionDto.getSumAmount2())
//                                        + "元";
//                                String businessData = NumberFormat.getInstance().format(
//                                        blBusinessData.getSumAmount2())
//                                        + "元";
//                                this.throwException(title, standardData, businessData);
//                                return false;
//                    }
//                     
//                    else if ((heBaoConditionDto.getLimitManAcc11()>-1)&&
//                            (heBaoConditionDto.getLimitManAcc11() < blBusinessData
//                                    .getLimitManAcc11())) {
//                                String title = "每人人身伤亡赔偿限额(高级管理人员)不符合条件!";
//                                String standardData = "小于等于"
//                                        + NumberFormat.getInstance().format(
//                                                heBaoConditionDto.getLimitManAcc11())
//                                        + "元";
//                                String businessData = NumberFormat.getInstance().format(
//                                        blBusinessData.getLimitManAcc11())
//                                        + "元";
//                                this.throwException(title, standardData, businessData);
//                                return false;
//                    }else if ((heBaoConditionDto.getLimitManAcc12()>-1)&&
//                            (heBaoConditionDto.getLimitManAcc12() < blBusinessData
//                                    .getLimitManAcc12())) {
//                        
//                                String title = "每人人身伤亡赔偿限额(其他人员)不符合条件!";
//                                String standardData = "小于等于"
//                                        + NumberFormat.getInstance().format(
//                                                heBaoConditionDto.getLimitManAcc12())
//                                        + "元";
//                                String businessData = NumberFormat.getInstance().format(
//                                        blBusinessData.getLimitManAcc12())
//                                        + "元";
//                                this.throwException(title, standardData, businessData);
//                                return false;
//                    }else if ((heBaoConditionDto.getLimitManAcc13()>-1)&&
//                            (heBaoConditionDto.getLimitManAcc13() < blBusinessData
//                                    .getLimitManAcc13())) {
//                                String title = "每人意外伤害医疗费用(高级管理人员)不符合条件!";
//                                String standardData = "小于等于"
//                                        + NumberFormat.getInstance().format(
//                                                heBaoConditionDto.getLimitManAcc13())
//                                        + "元";
//                                String businessData = NumberFormat.getInstance().format(
//                                        blBusinessData.getLimitManAcc13())
//                                        + "元";
//                                this.throwException(title, standardData, businessData);
//                                return false;
//                    }else if ((heBaoConditionDto.getLimitManAcc14()>-1)&&
//                            (heBaoConditionDto.getLimitManAcc14() < blBusinessData
//                                    .getLimitManAcc14())) {
//                                String title = "每人意外伤害医疗费用(其他人员)不符合条件!";
//                                String standardData = "小于等于"
//                                        + NumberFormat.getInstance().format(
//                                                heBaoConditionDto.getLimitManAcc14())
//                                        + "元";
//                                String businessData = NumberFormat.getInstance().format(
//                                        blBusinessData.getLimitManAcc14())
//                                        + "元";
//                                this.throwException(title, standardData, businessData);
//                                return false;
//                    }
//                   //modify by liuwei 20090512 begin 1598停车场责任险产品个性化双核条件配置
//                    else if ((heBaoConditionDto.getLimitFeeOneCar()>-1)&&
//                            (heBaoConditionDto.getLimitFeeOneCar() < blBusinessData
//                                    .getLimitFeeOneCar())) {
//                                String title = "每次事故每车位赔偿限额不符合条件!";
//                                String standardData = "小于等于"
//                                        + NumberFormat.getInstance().format(
//                                                heBaoConditionDto.getLimitFeeOneCar())
//                                        + "元";
//                                String businessData = NumberFormat.getInstance().format(
//                                        blBusinessData.getLimitFeeOneCar())
//                                        + "元";
//                                this.throwException(title, standardData, businessData);
//                                return false;
//                    }
//                 //modify by liuwei 20090512 end
//                  //add by zhouhui 20090625 begin 1548每次事故责任限额，累计责任限额
//                    else if ((heBaoConditionDto.getLimitAmount02()>-1)&&
//                            (heBaoConditionDto.getLimitAmount02() < blBusinessData
//                                    .getLimitAmount02())) {
//                                String title = "每次事故责任限额不符合条件!";
//                                String standardData = "小于等于"
//                                        + NumberFormat.getInstance().format(
//                                                heBaoConditionDto.getLimitAmount02())
//                                        + "元";
//                                String businessData = NumberFormat.getInstance().format(
//                                        blBusinessData.getLimitAmount02())
//                                        + "元";
//                                this.throwException(title, standardData, businessData);
//                                return false;
//                    }
//                    else if ((heBaoConditionDto.getLimitAmount03()>-1)&&
//                            (heBaoConditionDto.getLimitAmount03() < blBusinessData
//                                    .getLimitAmount03())) {
//                                String title = "累计责任限额不符合条件!";
//                                String standardData = "小于等于"
//                                        + NumberFormat.getInstance().format(
//                                                heBaoConditionDto.getLimitAmount03())
//                                        + "元";
//                                String businessData = NumberFormat.getInstance().format(
//                                        blBusinessData.getLimitAmount03())
//                                        + "元";
//                                this.throwException(title, standardData, businessData);
//                                return false;
//                    }else if ((heBaoConditionDto.getLimitManHeal01()>-1)&&
//                            (heBaoConditionDto.getLimitManHeal01() < blBusinessData
//                                    .getLimitManHeal01())) {
//                                        
//                                String title = "每人医疗费用赔偿限额不符合条件!";
//                                String standardData = "小于等于"
//                                        + NumberFormat.getInstance().format(
//                                                heBaoConditionDto.getLimitManHeal01())
//                                        + "元";
//                                String businessData = NumberFormat.getInstance().format(
//                                        blBusinessData.getLimitManHeal01())
//                                        + "元";
//                                this.throwException(title, standardData, businessData);
//                                return false;
//                    }
//                    ///add by zhouhui 20090625 end 1548每次事故责任限额，累计责任限额                  
//          }
//            // 27意健险
//            if (blBusinessData.getClassCode().equals("27")) {
//                if ((heBaoConditionDto.getUnitProportion() > -1)
//                        && (heBaoConditionDto.getUnitProportion() > blBusinessData
//                                .getUnitProportion())) {
//                    String title = "团单最低人数比例不符合条件!";
//                    String standardData = "大于等于"
//                            + NumberFormat.getInstance().format(
//                                    heBaoConditionDto.getUnitProportion()*100)
//                            + "%";
//                    String businessData = NumberFormat.getInstance().format(
//                            blBusinessData.getUnitProportion()*100)
//                            + "%";
//                    this.throwException(title, standardData, businessData);
//                    return false;
//                } 
//                if (blBusinessData.getRiskCode().equals("2701") || blBusinessData.getRiskCode().equals("2711")
//                    || blBusinessData.getRiskCode().equals("2705") || blBusinessData.getRiskCode().equals("2728")
//                    || blBusinessData.getRiskCode().equals("2738") || blBusinessData.getRiskCode().equals("2741")) {
//                    if ((heBaoConditionDto.getSumAmountPer1() > -1)
//                            && (heBaoConditionDto.getSumAmountPer1() < blBusinessData
//                                    .getSumAmountPer1())) {
//                        String title = "每人保险金额(一类职业)不符合条件!";
//                        String standardData = "小于等于"
//                                + NumberFormat.getInstance().format(
//                                        heBaoConditionDto.getSumAmountPer1())
//                                + "元";
//                        String businessData = NumberFormat.getInstance()
//                                .format(blBusinessData.getSumAmountPer1())
//                                + "元";
//                        this.throwException(title, standardData, businessData);
//                        return false;
//                    }
//                    else if ((heBaoConditionDto.getSumAmountPer2() > -1)
//                            && (heBaoConditionDto.getSumAmountPer2() < blBusinessData
//                                    .getSumAmountPer2())) {
//                        String title = "每人保险金额(二类职业)不符合条件!";
//                        String standardData = "小于等于"
//                                + NumberFormat.getInstance().format(
//                                        heBaoConditionDto.getSumAmountPer2())
//                                + "元";
//                        String businessData = NumberFormat.getInstance()
//                                .format(blBusinessData.getSumAmountPer2())
//                                + "元";
//                        this.throwException(title, standardData, businessData);
//                        return false;
//                    }
//                    else if ((heBaoConditionDto.getSumAmountPer3() > -1)
//                            && (heBaoConditionDto.getSumAmountPer3() < blBusinessData
//                                    .getSumAmountPer3())) {
//                        String title = "每人保险金额(三类职业)不符合条件!";
//                        String standardData = "小于等于"
//                                + NumberFormat.getInstance().format(
//                                        heBaoConditionDto.getSumAmountPer3())
//                                + "元";
//                        String businessData = NumberFormat.getInstance()
//                                .format(blBusinessData.getSumAmountPer3())
//                                + "元";
//                        this.throwException(title, standardData, businessData);
//                        return false;
//                    }
//                    else if ((heBaoConditionDto.getSumAmountPer4() > -1)
//                            && (heBaoConditionDto.getSumAmountPer4() < blBusinessData
//                                    .getSumAmountPer4())) {
//                        String title = "每人保险金额(四类职业)不符合条件!";
//                        String standardData = "小于等于"
//                                + NumberFormat.getInstance().format(
//                                        heBaoConditionDto.getSumAmountPer4())
//                                + "元";
//                        String businessData = NumberFormat.getInstance()
//                                .format(blBusinessData.getSumAmountPer4())
//                                + "元";
//                        this.throwException(title, standardData, businessData);
//                        return false;
//                    }
//                    else if ((heBaoConditionDto.getSumAmountPer5() > -1)
//                            && (heBaoConditionDto.getSumAmountPer5() < blBusinessData
//                                    .getSumAmountPer5())) {
//                        String title = "每人保险金额(五类职业)不符合条件!";
//                        String standardData = "小于等于"
//                                + NumberFormat.getInstance().format(
//                                        heBaoConditionDto.getSumAmountPer5())
//                                + "元";
//                        String businessData = NumberFormat.getInstance()
//                                .format(blBusinessData.getSumAmountPer5())
//                                + "元";
//                        this.throwException(title, standardData, businessData);
//                        return false;
//                    }
//                    else if ((heBaoConditionDto.getSumAmountPer6() > -1)
//                            && (heBaoConditionDto.getSumAmountPer6() < blBusinessData
//                                    .getSumAmountPer6())) {
//                        String title = "每人保险金额(六类职业)不符合条件!";
//                        String standardData = "小于等于"
//                                + NumberFormat.getInstance().format(
//                                        heBaoConditionDto.getSumAmountPer6())
//                                + "元";
//                        String businessData = NumberFormat.getInstance()
//                                .format(blBusinessData.getSumAmountPer6())
//                                + "元";
//                        this.throwException(title, standardData, businessData);
//                        return false;
//                    }
//                }
//                else  if(blBusinessData.getRiskCode().equals("2706")||blBusinessData.getRiskCode().equals("2710")){
//                }
//                
//                else {
//                    if ((heBaoConditionDto.getSumAmountPer() > -1)
//                            && (heBaoConditionDto.getSumAmountPer() < blBusinessData
//                                    .getSumAmountPer())) {
//                        String title = "每人保险金额不符合条件!";
//                        String standardData = "小于等于"
//                                + NumberFormat.getInstance().format(
//                                        heBaoConditionDto.getSumAmountPer())
//                                + "元";
//                        String businessData = NumberFormat.getInstance()
//                                .format(blBusinessData.getSumAmountPer())
//                                + "元";
//                        this.throwException(title, standardData, businessData);
//                        return false;
//                    }
//                }
//
//                if ((heBaoConditionDto.getSubAmountPer01() > -1)
//                        && (heBaoConditionDto.getSubAmountPer01() < blBusinessData
//                                .getSubAmountPer01())) {
//                    String title = "意外伤害医疗费用(每人保险金额)不符合条件!";
//                    String standardData = "小于等于"
//                            + NumberFormat.getInstance().format(
//                                    heBaoConditionDto.getSubAmountPer01())
//                            + "元";
//                    String businessData = NumberFormat.getInstance()
//                            .format(blBusinessData.getSubAmountPer01())
//                            + "元";
//                    this.throwException(title, standardData, businessData);
//                    return false;
//                }
//                if ((heBaoConditionDto.getSubAmountPer02() > -1)
//                        && (heBaoConditionDto.getSubAmountPer02() < blBusinessData
//                                .getSubAmountPer02())) {
//                    String title = "意外伤害生活津贴(每日津贴金额)不符合条件!";
//                    String standardData = "小于等于"
//                            + NumberFormat.getInstance().format(
//                                    heBaoConditionDto.getSubAmountPer02())
//                            + "元";
//                    String businessData = NumberFormat.getInstance()
//                            .format(blBusinessData.getSubAmountPer02())
//                            + "元";
//                    this.throwException(title, standardData, businessData);
//                    return false;
//                }
//                if ((heBaoConditionDto.getSubAmountPer03() > -1)
//                        && (heBaoConditionDto.getSubAmountPer03() < blBusinessData
//                                .getSubAmountPer03())) {
//                    String title = "学生幼儿意外伤害医疗(每人保险金额)不符合条件!";
//                    String standardData = "小于等于"
//                            + NumberFormat.getInstance().format(
//                                    heBaoConditionDto.getSubAmountPer03())
//                            + "元";
//                    String businessData = NumberFormat.getInstance()
//                            .format(blBusinessData.getSubAmountPer03())
//                            + "元";
//                    this.throwException(title, standardData, businessData);
//                    return false;
//                }
//            
//            }
//            /** ******************保证保险核保权限校验******************* */
//            if("22".equals(blBusinessData.getClassCode())){
//                if(heBaoConditionDto.getSumAmount()>-1 && 
//                        blBusinessData.getSumAmount()>heBaoConditionDto.getSumAmount()){
//                    String title = "累计赔偿限额不符合条件!";
//                    String standardData = "小于等于"
//                        + NumberFormat.getInstance().format(
//                                heBaoConditionDto.getSumAmount())
//                        + "元";
//                    String businessData = NumberFormat.getInstance()
//                            .format(blBusinessData.getSumAmount())
//                        + "元";
//                    this.throwException(title, standardData, businessData);
//                    return false;
//                }else if(heBaoConditionDto.getLimitAcc12()>-1 && 
//                        blBusinessData.getLimitAcc12()>heBaoConditionDto.getLimitAcc12()){
//                    String title = "每次事故赔偿限额不符合条件!";
//                    String standardData = "小于等于"
//                        + NumberFormat.getInstance().format(
//                                heBaoConditionDto.getLimitAcc12())
//                        + "元";
//                    String businessData = NumberFormat.getInstance()
//                            .format(blBusinessData.getLimitAcc12())
//                        + "元";
//                    this.throwException(title, standardData, businessData);
//                    return false;
//                }else if(heBaoConditionDto.getLimitManAcc01()>-1 && 
//                        blBusinessData.getLimitManAcc01()>heBaoConditionDto.getLimitManAcc01()){
//                    String title = "每次事故每人赔偿限额不符合条件!";
//                    String standardData = "小于等于"
//                        + NumberFormat.getInstance().format(
//                                heBaoConditionDto.getLimitManAcc01())
//                        + "元";
//                    String businessData = NumberFormat.getInstance()
//                            .format(blBusinessData.getLimitManAcc01())
//                        + "元";
//                    this.throwException(title, standardData, businessData);
//                    return false;
//                }
//            }
//            
//            //01,03,07财产险，工程险 23组合险
//            System.out.println("---------------------"+blBusinessData.getSumAmount());
//            System.out.println("---------------------"+heBaoConditionDto.getSumAmount());
//            if (blBusinessData.getClassCode().equals("01")||blBusinessData.getClassCode().equals("03")
//                    ||blBusinessData.getClassCode().equals("07") ||blBusinessData.getClassCode().equals("23")) {                
//                    //add by yanglibo 20081112 begin reason:增加01险类中的国民经济行业保险核保的控制,增加相应的提
//                        if ((heBaoConditionDto.getSumAmountS() > -1)
//                                && (blBusinessData
//                                        .getSumAmount() < heBaoConditionDto.getSumAmountS())) {
//                            String title = "01险类中的国民经济行业代码为C20、C21和C22保额不符合条件!";    
//                            String standardData = "大于等于"
//                                + NumberFormat.getInstance().format(
//                                        heBaoConditionDto.getSumAmountS())
//                                + "元";
//                        String businessData = NumberFormat.getInstance()
//                                .format(blBusinessData.getSumAmount())
//                                + "元";
//                        this.throwException(title, standardData, businessData);
//                        return false;
//                        }
//                 //add by yanglibo end 20081112
//                System.out.println("heBaoConditionDto.getSumAmount()=="+heBaoConditionDto.getSumAmount());
//                System.out.println("blBusinessData.getSumAmount()=="+blBusinessData.getSumAmount());
//                //add by yanglibo begin 20090504 增加河南占有性质核保权限
//                if ((heBaoConditionDto.getSumAmountPN() > -1)
//                        && (5000000>blBusinessData
//                                .getSumAmount()||blBusinessData
//                                .getSumAmount()> heBaoConditionDto.getSumAmountPN())) {
//                    String title = "01险类中的占有性质为金属专储和粮食专储时，保额不符合条件!";    
//                    String standardData = "小于等于"
//                        + NumberFormat.getInstance().format(
//                                heBaoConditionDto.getSumAmountPN())
//                        + "元；大于等于5000000元";
//                String businessData = NumberFormat.getInstance()
//                        .format(blBusinessData.getSumAmount())
//                        + "元";
//                this.throwException(title, standardData, businessData);
//                return false;
//                }   
//                //add by yanglibo begin 20090504 增加河南占有性质核保权限
//                if ((heBaoConditionDto.getSumAmount() > -1)
//                        && (heBaoConditionDto.getSumAmount() < blBusinessData
//                                .getSumAmount())) {
//                    //String title = "操作权限不符合条件!";
//                    String title = "保险金额不符合条件!";
//                    String standardData = "小于等于"
//                        + NumberFormat.getInstance().format(
//                                heBaoConditionDto.getSumAmount())
//                        + "元";
//                String businessData = NumberFormat.getInstance()
//                        .format(blBusinessData.getSumAmount())
//                        + "元";
//                this.throwException(title, standardData, businessData);
//                return false;
//                }           
//                if ((heBaoConditionDto.getAmount() > -1)
//                        && (heBaoConditionDto.getAmount() < blBusinessData
//                                .getAmount())) {
//                    String title = "硬件损失保额不符合条件!";
//                    String standardData = "小于等于"
//                        + NumberFormat.getInstance().format(
//                                heBaoConditionDto.getAmount())
//                        + "元";
//                String businessData = NumberFormat.getInstance()
//                        .format(blBusinessData.getAmount())
//                        + "元";
//                this.throwException(title, standardData, businessData);
//                return false;
//                } 
//                if ((heBaoConditionDto.getLimitManAcc() > -1)
//                        && (heBaoConditionDto.getLimitManAcc() < blBusinessData
//                                .getLimitManAcc())) {
//                    //String title = "数据复制费用每次赔偿限额不符合条件!";
//                    String title = "第三者责任每次事故赔偿限额不符合条件!";
//                    String standardData = "小于等于"
//                        + NumberFormat.getInstance().format(
//                                heBaoConditionDto.getLimitManAcc())
//                        + "元";
//                String businessData = NumberFormat.getInstance()
//                        .format(blBusinessData.getLimitManAcc())
//                        + "元";
//                this.throwException(title, standardData, businessData);
//                return false;
//                } 
//                if ((heBaoConditionDto.getSumAmount08() > -1)
//                        && (heBaoConditionDto.getSumAmount08() < blBusinessData
//                                .getSumAmount08())) {
//                    //String title = "增加费用累计赔偿限额不符合条件!";
//                    String title = "第三者累计赔偿限额不符合条件!";
//                    String standardData = "小于等于"
//                        + NumberFormat.getInstance().format(
//                                heBaoConditionDto.getSumAmount08())
//                        + "元";
//                String businessData = NumberFormat.getInstance()
//                        .format(blBusinessData.getSumAmount08())
//                        + "元";
//                this.throwException(title, standardData, businessData);
//                return false;
//                } 
//                //add by hanxiao 20090226 begin 0125工程机械设备保险增加附加险保额核保因子
//                if(blBusinessData.getRiskCode().equals("0125")){
//                 System.out.println("heBaoConditionDto.getSumAmount0145100()=="+heBaoConditionDto.getSumAmount0145100());
//               System.out.println("blBusinessData.getSumAmount0145100()=="+blBusinessData.getSumAmount0145100());
//                    if ((heBaoConditionDto.getSumAmount0145100() > -1)
//                            && (heBaoConditionDto.getSumAmount0145100() < blBusinessData
//                                    .getSumAmount0145100())) {
//                        String title = "附加自燃损失保险条款保额不符合条件!";
//                        String standardData = "小于等于"
//                                + NumberFormat.getInstance().format(
//                                        heBaoConditionDto.getSumAmount0145100())
//                                        + "元";
//                        String businessData = NumberFormat.getInstance()
//                                .format(blBusinessData.getSumAmount0145100())
//                                + "元";
//                        this.throwException(title, standardData, businessData);
//                        return false;
//                    } 
//                 System.out.println("heBaoConditionDto.getSumAmount0145200()=="+heBaoConditionDto.getSumAmount0145200());
//                   System.out.println("blBusinessData.getSumAmount0145200()=="+blBusinessData.getSumAmount0145200());
//                    if ((heBaoConditionDto.getSumAmount0145200() > -1)
//                            && (heBaoConditionDto.getSumAmount0145200() < blBusinessData
//                                    .getSumAmount0145200())) {
//                        String title = "附加第三者责任保险条款保额不符合条件!";
//                        String standardData = "小于等于"
//                                + NumberFormat.getInstance().format(
//                                        heBaoConditionDto.getSumAmount0145200())
//                                        + "元";
//                        String businessData = NumberFormat.getInstance()
//                                .format(blBusinessData.getSumAmount0145200())
//                                + "元";
//                        this.throwException(title, standardData, businessData);
//                        return false;
//                    }
//                 System.out.println("heBaoConditionDto.getSumAmount0145300()=="+heBaoConditionDto.getSumAmount0145300());
//                   System.out.println("blBusinessData.getSumAmount0145300()=="+blBusinessData.getSumAmount0145300());
//                    if ((heBaoConditionDto.getSumAmount0145300() > -1)
//                            && (heBaoConditionDto.getSumAmount0145300() < blBusinessData
//                                    .getSumAmount0145300())) {
//                        String title = "附加全车盗抢保险条款保额不符合条件!";
//                        String standardData = "小于等于"
//                                + NumberFormat.getInstance().format(
//                                        heBaoConditionDto.getSumAmount0145300())
//                                        + "元";
//                        String businessData = NumberFormat.getInstance()
//                                .format(blBusinessData.getSumAmount0145300())
//                                + "元";
//                        this.throwException(title, standardData, businessData);
//                        return false;
//                    }
//                 System.out.println("heBaoConditionDto.getSumAmount0145400()=="+heBaoConditionDto.getSumAmount0145400());
//                   System.out.println("blBusinessData.getSumAmount0145400()=="+blBusinessData.getSumAmount0145400());
//                    if ((heBaoConditionDto.getSumAmount0145400() > -1)
//                            && (heBaoConditionDto.getSumAmount0145400() < blBusinessData
//                                    .getSumAmount0145400())) {
//                        String title = "附加工程机械设备操作人员责任保险条款保额不符合条件!";
//                        String standardData = "小于等于"
//                                + NumberFormat.getInstance().format(
//                                        heBaoConditionDto.getSumAmount0145400())
//                                        + "元";
//                        String businessData = NumberFormat.getInstance()
//                                .format(blBusinessData.getSumAmount0145400())
//                                + "元";
//                        this.throwException(title, standardData, businessData);
//                        return false;
//                    }
//                    if ((heBaoConditionDto.getSumAmount0145500() > -1)
//                            && (heBaoConditionDto.getSumAmount0145500() < blBusinessData
//                                    .getSumAmount0145500())) {
//                        String title = "附加碰撞、倾覆保险保额不符合条件!";
//                        String standardData = "小于等于"
//                                + NumberFormat.getInstance().format(
//                                        heBaoConditionDto.getSumAmount0145500())
//                                        + "元";
//                        String businessData = NumberFormat.getInstance()
//                                .format(blBusinessData.getSumAmount0145500())
//                                + "元";
//                        this.throwException(title, standardData, businessData);
//                        return false;
//                    }
//                }//add by hanxiao 20090226 end 0125工程机械设备保险增加附加险保额核保因子
//            }
//        
//        /**预约协议控制核保 */
//        if (blBusinessData.getRiskCode().equals("9999")||blBusinessData.getRiskCode().equals("9997")){
//            if ((heBaoConditionDto.getSumAmount() > -1)
//                    && (heBaoConditionDto.getSumAmount() < blBusinessData
//                            .getSumAmount())) {
//                String title = "预约协议保额不符合条件!";  
//                String standardData = "小于等于"
//                    + NumberFormat.getInstance().format(
//                            heBaoConditionDto.getSumAmount())
//                    + "元";
//            String businessData = NumberFormat.getInstance()
//                    .format(blBusinessData.getSumAmount())
//                    + "元";
//            this.throwException(title, standardData, businessData);
//            return false;
//            } 
//        }
//
//        /**新增部分－家财险、企财险、房贷险、建工险增加承保年限的控制*/
//        if (blBusinessData.getClassCode().equals("01")||blBusinessData.getClassCode().equals("03")
//            ||blBusinessData.getClassCode().equals("04")||blBusinessData.getClassCode().equals("07")) {
//            if ((heBaoConditionDto.getCBYearLimit()>-1)
//                    && heBaoConditionDto.getCBYearLimit() < blBusinessData
//                            .getCBYearLimit()) {
//                String title = "保险期限年数不符合条件!";
//                String standardData = "小于等于" + heBaoConditionDto.getCBYearLimit()
//                        + "年";
//                String businessData = blBusinessData.getCBYearLimit() + "年";
//                this.throwException(title, standardData, businessData);
//                return false;
//            }       
//        }
//        /** ***********再保部分************ */
//        System.out.println(heBaoConditionDto.getTrialAmount());
//        System.out.println(blBusinessData.getTrialAmount());
//        if ((heBaoConditionDto.getTrialAmount()>-1)
//                &&(heBaoConditionDto.getTrialAmount() < blBusinessData
//                    .getTrialAmount())) {
//            
//                String title = "附加自留保额不符合条件!";
//                String standardData = "小于等于"
//                        + NumberFormat.getInstance().format(
//                                heBaoConditionDto.getTrialAmount())
//                        + "元";
//                String businessData = NumberFormat.getInstance().format(
//                        blBusinessData.getTrialAmount())
//                        + "元";
//                this.throwException(title, standardData, businessData);
//                return false;
//            }
//           else if ((heBaoConditionDto.getTrialPremium()>-1)&&
//                   (heBaoConditionDto.getTrialPremium() < blBusinessData
//                    .getTrialPremium())) {
//                String title = "附加自留保费不符合条件!";
//                String standardData = "小于等于"
//                        + NumberFormat.getInstance().format(
//                                heBaoConditionDto.getTrialPremium())
//                        + "元";
//                String businessData = NumberFormat.getInstance().format(
//                        blBusinessData.getTrialPremium())
//                        + "元";
//                this.throwException(title, standardData, businessData);
//                return false;
//            }         
//            else if ((heBaoConditionDto.getAllowSplit().equals("N")&&blBusinessData
//                    .getAllowSplit().equals("Y"))) {
//                    String title = "是否允许临分（含特约）不符合条件!";
//                    String standardData = "无权审核临分业务";
//                    String businessData = "临分业务";
//                    this.throwException(title, standardData, businessData);
//                    return false;
//                }
//       
//       // 23组合险
//       if(blBusinessData.getClassCode().equals("23")){
//           if (blBusinessData.getRiskCode().equals("2351")||blBusinessData.getRiskCode().equals("2352")
//            ||blBusinessData.getRiskCode().equals("2355")){
//               //每人保险金额
//               if((heBaoConditionDto.getSumAmountPer()>-1)&&
//                            (heBaoConditionDto.getSumAmountPer() < blBusinessData
//                                .getSumAmountPer())){
//                    String title = "每人保险金额不符合条件!";
//                    String standardData = "小于等于"
//                            + NumberFormat.getInstance().format(
//                                    heBaoConditionDto.getSumAmountPer())
//                            + "元";
//                    String businessData = NumberFormat.getInstance().format(
//                            blBusinessData.getSumAmountPer())
//                            + "元";
//                    this.throwException(title, standardData, businessData);
//                    return false;          
//               }
//               // 意外伤害医疗
//               if((heBaoConditionDto.getSumAmountYL()>-1)&&
//                            (heBaoConditionDto.getSumAmountYL() < blBusinessData
//                                .getSumAmountYL())){
//                    String title = "意外伤害医疗金额不符合条件!";
//                    String standardData = "小于等于"
//                            + NumberFormat.getInstance().format(
//                                    heBaoConditionDto.getSumAmountYL())
//                            + "元";
//                    String businessData = NumberFormat.getInstance().format(
//                            blBusinessData.getSumAmountYL())
//                            + "元";
//                    this.throwException(title, standardData, businessData);
//                    return false;
//                }
//           }
//           if(blBusinessData.getRiskCode().equals("2352")){
//               //房屋及附属设备保险金额/每户
//               if((heBaoConditionDto.getAmountPer03010001()>-1)&&
//                    (heBaoConditionDto.getAmountPer03010001() < blBusinessData
//                        .getAmountPer03010001())){
//                String title = "房屋及附属设备保险金额/每户不符合条件!";
//                String standardData = "小于等于"
//                        + NumberFormat.getInstance().format(
//                                heBaoConditionDto.getAmountPer03010001())
//                        + "元";
//                String businessData = NumberFormat.getInstance().format(
//                        blBusinessData.getAmountPer03010001())
//                        + "元";
//                this.throwException(title, standardData, businessData);
//                return false;
//               }
//               //室内装潢保险金额/每户
//               if((heBaoConditionDto.getAmountPer03010002()>-1)&&
//                        (heBaoConditionDto.getAmountPer03010002() < blBusinessData
//                            .getAmountPer03010002())){
//                    String title = "室内装潢保险金额/每户不符合条件!";
//                    String standardData = "小于等于"
//                            + NumberFormat.getInstance().format(
//                                    heBaoConditionDto.getAmountPer03010002())
//                            + "元";
//                    String businessData = NumberFormat.getInstance().format(
//                            blBusinessData.getAmountPer03010002())
//                            + "元";
//                    this.throwException(title, standardData, businessData);
//                    return false;
//               }
//               //附加盗抢保险/每户
//               if((heBaoConditionDto.getAmountPer9000452()>-1)&&
//                        (heBaoConditionDto.getAmountPer9000452() < blBusinessData
//                            .getAmountPer9000452())){
//                    String title = "附加盗抢保险金额/每户不符合条件!";
//                    String standardData = "小于等于"
//                            + NumberFormat.getInstance().format(
//                                    heBaoConditionDto.getAmountPer9000452())
//                            + "元";
//                    String businessData = NumberFormat.getInstance().format(
//                            blBusinessData.getAmountPer9000452())
//                            + "元";
//                    this.throwException(title, standardData, businessData);
//                    return false;
//               }
//           }
//           if(blBusinessData.getRiskCode().equals("2353") || blBusinessData.getRiskCode().equals("2354")){
//               //室内装潢/家用电器/衣物床上用品/家具及其他保险金额/每户
//               if((heBaoConditionDto.getAmountPer()>-1)&&
//                        (heBaoConditionDto.getAmountPer() < blBusinessData
//                            .getAmountPer())){
//                //String title = "室内装潢/家用电器/衣物床上用品/家具及其他保险金额/每户不符合条件!";
//                  if("23540009".equals(blBusinessData.getProductcode())||"23540010".equals(blBusinessData.getProductcode())){
//                      String title = "家庭财产综合保险金额/每户不符合条件!";
//                      String standardData = "小于等于"
//                            + NumberFormat.getInstance().format(
//                                heBaoConditionDto.getAmountPer())
//                                + "元";
//                      String businessData = NumberFormat.getInstance().format(
//                              blBusinessData.getAmountPer())
//                              + "元";
//                      this.throwException(title, standardData, businessData);
//                      return false;
//                  }else{
//                      String title = "家庭财产火灾损失保险金额/每户不符合条件!";
//                      String standardData = "小于等于"
//                        + NumberFormat.getInstance().format(
//                                heBaoConditionDto.getAmountPer())
//                        + "元";
//                      String businessData = NumberFormat.getInstance().format(
//                        blBusinessData.getAmountPer())
//                        + "元";
//                      this.throwException(title, standardData, businessData);
//                      return false;
//                  }             
//               }
//               // 附加居家责任保险
//               if((heBaoConditionDto.getAmountPer9000449()>-1)&&
//                        (heBaoConditionDto.getAmountPer9000449() < blBusinessData
//                            .getAmountPer9000449())){
//                String title = "附加居家责任保险金额/每户不符合条件!";
//                    String standardData = "小于等于"
//                            + NumberFormat.getInstance().format(
//                                    heBaoConditionDto.getAmountPer9000449())
//                            + "元";
//                    String businessData = NumberFormat.getInstance().format(
//                            blBusinessData.getAmountPer9000449())
//                            + "元";
//                    this.throwException(title, standardData, businessData);
//                    return false;
//               }
//               // 附加家庭意外骨折医疗保险
//               if((heBaoConditionDto.getAmountPer9000451()>-1)&&
//                        (heBaoConditionDto.getAmountPer9000451() < blBusinessData
//                            .getAmountPer9000451())){
//                String title = "附加家庭意外骨折医疗保险金额/每户不符合条件!";
//                    String standardData = "小于等于"
//                            + NumberFormat.getInstance().format(
//                                    heBaoConditionDto.getAmountPer9000451())
//                            + "元";
//                    String businessData = NumberFormat.getInstance().format(
//                            blBusinessData.getAmountPer9000451())
//                            + "元";
//                    this.throwException(title, standardData, businessData);
//                    return false;
//               }
//               // 附加盗抢保险
//               if((heBaoConditionDto.getAmountPer9000452()>-1)&&
//                        (heBaoConditionDto.getAmountPer9000452() < blBusinessData
//                            .getAmountPer9000452())){
//                String title = "附加盗抢保险金额/每户不符合条件!";
//                    String standardData = "小于等于"
//                            + NumberFormat.getInstance().format(
//                                    heBaoConditionDto.getAmountPer9000452())
//                            + "元";
//                    String businessData = NumberFormat.getInstance().format(
//                            blBusinessData.getAmountPer9000452())
//                            + "元";
//                    this.throwException(title, standardData, businessData);
//                    return false;
//               }
//               //附加家用电器用电安全保险
//               if((heBaoConditionDto.getAmountPer9000453()>-1)&&
//                        (heBaoConditionDto.getAmountPer9000453() < blBusinessData
//                            .getAmountPer9000453())){
//                String title = "附加家用电器用电安全保险金额/每户不符合条件!";
//                    String standardData = "小于等于"
//                            + NumberFormat.getInstance().format(
//                                    heBaoConditionDto.getAmountPer9000453())
//                            + "元";
//                    String businessData = NumberFormat.getInstance().format(
//                            blBusinessData.getAmountPer9000453())
//                            + "元";
//                    this.throwException(title, standardData, businessData);
//                    return false;
//               }
//               //附加管道破裂及水渍保险
//               if((heBaoConditionDto.getAmountPer9000454()>-1)&&
//                        (heBaoConditionDto.getAmountPer9000454() < blBusinessData
//                            .getAmountPer9000454())){
//                String title = "附加管道破裂及水渍保险金额/每户不符合条件!";
//                    String standardData = "小于等于"
//                            + NumberFormat.getInstance().format(
//                                    heBaoConditionDto.getAmountPer9000454())
//                            + "元";
//                    String businessData = NumberFormat.getInstance().format(
//                            blBusinessData.getAmountPer9000454())
//                            + "元";
//                    this.throwException(title, standardData, businessData);
//                    return false;
//               }
//               //附加家庭成员屋内意外伤害身故、残疾保险金
//               if((heBaoConditionDto.getAmountPer9000450()>-1)&&
//                        (heBaoConditionDto.getAmountPer9000450() < blBusinessData
//                            .getAmountPer9000450())){
//                String title = "附加家庭成员屋内意外伤害身故、残疾保险金额/每户不符合条件!";
//                    String standardData = "小于等于"
//                            + NumberFormat.getInstance().format(
//                                    heBaoConditionDto.getAmountPer9000450())
//                            + "元";
//                    String businessData = NumberFormat.getInstance().format(
//                            blBusinessData.getAmountPer9000450())
//                            + "元";
//                    this.throwException(title, standardData, businessData);
//                    return false;
//               }
//           }
//           
//           //公路综合险
//           if (blBusinessData.getRiskCode().equals("2311")||blBusinessData.getRiskCode().equals("2312"))
//           {
//               if ((heBaoConditionDto.getSumAmount2300200()>-1)&&
//                        (heBaoConditionDto.getSumAmount2300200() < blBusinessData
//                            .getSumAmount2300200())){
//                        String title = "公路财产保险金额不符合条件!";
//                        String standardData = "小于等于"
//                                + NumberFormat.getInstance().format(
//                                        heBaoConditionDto.getSumAmount2300200())
//                                + "元";
//                        String businessData = NumberFormat.getInstance().format(
//                                blBusinessData.getSumAmount2300200())
//                                + "元";
//                        this.throwException(title, standardData, businessData);
//                        return false;
//                    }
//                    else if ((heBaoConditionDto.getSumAmount2300400()>-1)&&
//                            (heBaoConditionDto.getSumAmount2300400() < blBusinessData
//                            .getSumAmount2300400())) {
//                        String title = "公众责任保险金额不符合条件!";
//                        String standardData = "小于等于"
//                                + NumberFormat.getInstance().format(
//                                        heBaoConditionDto.getSumAmount2300400())
//                                + "元";
//                        String businessData = NumberFormat.getInstance().format(
//                                blBusinessData.getSumAmount2300400())
//                                + "元";
//                        this.throwException(title, standardData, businessData);
//                        return false;
//                    }   
//                    else if ((heBaoConditionDto.getSumAmount2300500()>-1)&&
//                            (heBaoConditionDto.getSumAmount2300500() < blBusinessData
//                            .getSumAmount2300500())) {
//                        String title = "雇主责任保险金额不符合条件!";
//                        String standardData = "小于等于"
//                                + NumberFormat.getInstance().format(
//                                        heBaoConditionDto.getSumAmount2300500())
//                                + "元";
//                        String businessData = NumberFormat.getInstance().format(
//                                blBusinessData.getSumAmount2300500())
//                                + "元";
//                        this.throwException(title, standardData, businessData);
//                        return false;
//                    }
//                    else if ((heBaoConditionDto.getLimit2300500()>-1)&&
//                         (heBaoConditionDto.getLimit2300500() < blBusinessData
//                         .getLimit2300500())) {
//                       String title = "雇主责任保险每人每次事故不符合条件!";
//                       String standardData = "小于等于"
//                              + NumberFormat.getInstance().format(
//                                    heBaoConditionDto.getLimit2300500())
//                              + "元";
//                       String businessData = NumberFormat.getInstance().format(
//                              blBusinessData.getLimit2300500())
//                              + "元";
//                      this.throwException(title, standardData, businessData);
//                      return false;
//                    }
//                    else if ((heBaoConditionDto.getSumAmount2300600()>-1)&&
//                            (heBaoConditionDto.getSumAmount2300600() < blBusinessData
//                            .getSumAmount2300600())) {
//                        String title = "现金保险保险金额不符合条件!";
//                        String standardData = "小于等于"
//                                + NumberFormat.getInstance().format(
//                                        heBaoConditionDto.getSumAmount2300600())
//                                + "元";
//                        String businessData = NumberFormat.getInstance().format(
//                                blBusinessData.getSumAmount2300600())
//                                + "元";
//                        this.throwException(title, standardData, businessData);
//                        return false;
//                    }              
//               }
//           //add by gengxiaobo 20080326 起重机械综合保险
//           if (blBusinessData.getRiskCode().equals("2313"))
//           {
//               if ((heBaoConditionDto.getSumAmount2301500()>-1)&&
//                        (heBaoConditionDto.getSumAmount2301500() < blBusinessData
//                            .getSumAmount2301500())){
//                        String title = "财产损失保险每一单个设备保险金额不符合条件!";
//                        String standardData = "小于等于"
//                                + NumberFormat.getInstance().format(
//                                        heBaoConditionDto.getSumAmount2301500())
//                                + "元";
//                        String businessData = NumberFormat.getInstance().format(
//                                blBusinessData.getSumAmount2301500())
//                                + "元";
//                        this.throwException(title, standardData, businessData);
//                        return false;
//                    }
//                    else if ((heBaoConditionDto.getSumAmount2301600()>-1)&&
//                            (heBaoConditionDto.getSumAmount2301600() < blBusinessData
//                            .getSumAmount2301600())) {
//                        String title = "第三者责任保险金额不符合条件!";
//                        String standardData = "小于等于"
//                                + NumberFormat.getInstance().format(
//                                        heBaoConditionDto.getSumAmount2301600())
//                                + "元";
//                        String businessData = NumberFormat.getInstance().format(
//                                blBusinessData.getSumAmount2301600())
//                                + "元";
//                        this.throwException(title, standardData, businessData);
//                        return false;
//                    }   
//                    else if ((heBaoConditionDto.getSumAmount2301800()>-1)&&
//                            (heBaoConditionDto.getSumAmount2301800() < blBusinessData
//                            .getSumAmount2301800())) {
//                        String title = "雇主责任保险金额不符合条件!";
//                        String standardData = "小于等于"
//                                + NumberFormat.getInstance().format(
//                                        heBaoConditionDto.getSumAmount2301800())
//                                + "元";
//                        String businessData = NumberFormat.getInstance().format(
//                                blBusinessData.getSumAmount2301800())
//                                + "元";
//                        this.throwException(title, standardData, businessData);
//                        return false;
//                    }      
//               } 
//           //add by hanxiao 20091118 2310家财险部分
//           if (blBusinessData.getRiskCode().equals("2310"))
//           {
//               if ((heBaoConditionDto.getSumAmount0300100()>-1)&&
//                    (heBaoConditionDto.getSumAmount0300100() < blBusinessData
//                        .getSumAmount0300100())){
//                    String title = "家财险保险金额不符合条件!";
//                    String standardData = "小于等于"
//                            + NumberFormat.getInstance().format(
//                                    heBaoConditionDto.getSumAmount0300100())
//                            + "元";
//                    String businessData = NumberFormat.getInstance().format(
//                            blBusinessData.getSumAmount0300100())
//                            + "元";
//                    this.throwException(title, standardData, businessData);
//                    return false;
//                
//               }
//           }
//           
//           if (blBusinessData.getRiskCode().equals("2315")){
//               if ((heBaoConditionDto.getSumAmount0300100()>-1)&&(heBaoConditionDto.getSumAmount0300100() < blBusinessData.getSumAmount0300100())){
//                        String title = "企财综合险保额不符合条件!";
//                        String standardData = "小于等于"
//                                + NumberFormat.getInstance().format(
//                                        heBaoConditionDto.getSumAmount0300100())
//                                + "元";
//                        String businessData = NumberFormat.getInstance().format(
//                                blBusinessData.getSumAmount0300100())
//                                + "元";
//                        this.throwException(title, standardData, businessData);
//                        return false;                   
//               }
//               else if ((heBaoConditionDto.getLimitAcc12()>-1)&&(heBaoConditionDto.getLimitAcc12() < blBusinessData.getLimitAcc12())) {
//                    String title = "每次事故赔偿限额不符合条件!";
//                    String standardData = "小于等于"
//                            + NumberFormat.getInstance().format(
//                                    heBaoConditionDto.getLimitAcc12())
//                            + "元";
//                    String businessData = NumberFormat.getInstance().format(
//                            blBusinessData.getLimitAcc12())
//                            + "元";
//                    this.throwException(title, standardData, businessData);
//                    return false;
//                }
//                else if ((heBaoConditionDto.getLimitManAcc05()>-1)&&(heBaoConditionDto.getLimitManAcc05() < blBusinessData.getLimitManAcc05())) {
//                    String title = "每人人身伤亡责任限额不符合条件!";
//                    String standardData = "小于等于"
//                        + NumberFormat.getInstance().format(
//                                heBaoConditionDto.getLimitManAcc05())
//                                + "元";
//                    String businessData = NumberFormat.getInstance().format(
//                            blBusinessData.getLimitManAcc05())
//                            + "元";
//                    this.throwException(title, standardData, businessData);
//                    return false;
//                }
//                else if ((heBaoConditionDto.getLimitAcc03()>-1)&&(heBaoConditionDto.getLimitAcc03() < blBusinessData.getLimitAcc03())){
//                    String title = "每次事故财产损失赔偿限额不符合条件!";
//                    String standardData = "小于等于"
//                            + NumberFormat.getInstance().format(
//                                    heBaoConditionDto.getLimitAcc03())
//                            + "元";
//                    String businessData = NumberFormat.getInstance().format(
//                            blBusinessData.getLimitAcc03())
//                            + "元";
//                    this.throwException(title, standardData, businessData);
//                    return false;               
//                }
//           }
//       }
//       
//       //30综合险
//       if(blBusinessData.getClassCode().equals("30")){
//           System.out.println("------------------------------------份数=="+blBusinessData.sumquantity);
//           if ((heBaoConditionDto.getSumAmount300101()>-1)&&
//                   (heBaoConditionDto.getSumAmount300101() < blBusinessData.getSumAmount300101()/blBusinessData.sumquantity)){
//                    String title = "第三人人身伤亡保险金额不符合条件!";
//                    String standardData = "小于等于"
//                            + NumberFormat.getInstance().format(
//                                    heBaoConditionDto.getSumAmount300101())
//                            + "元";
//                    String businessData = NumberFormat.getInstance().format(
//                            blBusinessData.getSumAmount300101())
//                            + "元";
//                    this.throwException(title, standardData, businessData);
//                    return false;
//                
//           }else if((heBaoConditionDto.getSumAmount300102()>-1)&&
//                   (heBaoConditionDto.getSumAmount300102() < blBusinessData.getSumAmount300102()/blBusinessData.sumquantity)){
//                    String title = "第三人财产损失保险金额不符合条件!";
//                    String standardData = "小于等于"
//                            + NumberFormat.getInstance().format(
//                                    heBaoConditionDto.getSumAmount300102())
//                            + "元";
//                    String businessData = NumberFormat.getInstance().format(
//                            blBusinessData.getSumAmount300102())
//                            + "元";
//                    this.throwException(title, standardData, businessData);
//                    return false;
//                
//           }else if((heBaoConditionDto.getSumAmount300103()>-1)&&
//                   (heBaoConditionDto.getSumAmount300103() < blBusinessData.getSumAmount300103()/blBusinessData.sumquantity)){
//                    String title = "衣物行李球具损失保险金额不符合条件!";
//                    String standardData = "小于等于"
//                            + NumberFormat.getInstance().format(
//                                    heBaoConditionDto.getSumAmount300103())
//                            + "元";
//                    String businessData = NumberFormat.getInstance().format(
//                            blBusinessData.getSumAmount300103())
//                            + "元";
//                    this.throwException(title, standardData, businessData);
//                    return false;
//                
//           }else if((heBaoConditionDto.getSumAmount300104()>-1)&&
//                   (heBaoConditionDto.getSumAmount300104() < blBusinessData.getSumAmount300104()/blBusinessData.sumquantity)){
//                    String title = "球童特别费用保险金额不符合条件!";
//                    String standardData = "小于等于"
//                            + NumberFormat.getInstance().format(
//                                    heBaoConditionDto.getSumAmount300104())
//                            + "元";
//                    String businessData = NumberFormat.getInstance().format(
//                            blBusinessData.getSumAmount300104())
//                            + "元";
//                    this.throwException(title, standardData, businessData);
//                    return false;
//                
//           }else if((heBaoConditionDto.getSumAmount300105()>-1)&&
//                   (heBaoConditionDto.getSumAmount300105() < blBusinessData.getSumAmount300105()/blBusinessData.sumquantity)){
//                    String title = "意外事故医疗费用保险金额不符合条件!";
//                    String standardData = "小于等于"
//                            + NumberFormat.getInstance().format(
//                                    heBaoConditionDto.getSumAmount300105())
//                            + "元";
//                    String businessData = NumberFormat.getInstance().format(
//                            blBusinessData.getSumAmount300105())
//                            + "元";
//                    this.throwException(title, standardData, businessData);
//                    return false;
//                
//           }else if((heBaoConditionDto.getSumAmount300106()>-1)&&
//                   (heBaoConditionDto.getSumAmount300106() < blBusinessData.getSumAmount300106()/blBusinessData.sumquantity)){
//                    String title = "一杆进洞费用保险金额不符合条件!";
//                    String standardData = "小于等于"
//                            + NumberFormat.getInstance().format(
//                                    heBaoConditionDto.getSumAmount300106())
//                            + "元";
//                    String businessData = NumberFormat.getInstance().format(
//                            blBusinessData.getSumAmount300106())
//                            + "元";
//                    this.throwException(title, standardData, businessData);
//                    return false;
//           }
//       }
//       
//       /*
//        * modify
//        * by duhaichao
//        * 20101017
//        * 暂时没有此险种1505 此险类29
//        * begin
//        * //added by LanNing begin 20080225 投资金产品
//       if(blBusinessData.getClassCode().equals("29")){
//           System.out.println("--getMortgage="+blBusinessData.getMortgage());
//           if(("N").equals(blBusinessData.getMortgage())&&(heBaoConditionDto.getInvestment()>-1)&&
//                     (heBaoConditionDto.getInvestment() < blBusinessData
//                             .getInvestment())) {
//               String title = "投资金不符合条件!";
//               String standardData = "小于等于"
//                        + NumberFormat.getInstance().format(
//                        heBaoConditionDto.getInvestment())
//                        + "元";
//               String businessData = NumberFormat.getInstance().format(
//                        blBusinessData.getInvestment())
//                        + "元";
//               this.throwException(title, standardData, businessData);
//               return false;
//           }else if (!("Y").equals(heBaoConditionDto.getMortgage())&&("Y").equals(blBusinessData.getMortgage())){
//               String title = "投资金产品的质押或解除质押批改需要省公司以上审核!";
//               String standardData = "无权核批质押或解除质押业务";
//               String businessData = "质押或解除质押业务";
//               this.throwException(title, standardData, businessData);
//               return false;
//           }
//       }
//       //added by LanNing end 20080225 投资金产品
//
//       //added by LanNing begin 20080421 1505每次事故赔偿限额
//       if(blBusinessData.getRiskCode().equals("1505")){
//           if((heBaoConditionDto.getLimit02Fee1505()>-1)&&
//                     (heBaoConditionDto.getLimit02Fee1505() < blBusinessData
//                             .getLimit02Fee1505())) {
//               String title = "每次事故赔偿限额不符合条件! 每次事故赔偿限额=保单每次事故赔偿限额/车量总数！";
//               String standardData = "小于等于"
//                        + NumberFormat.getInstance().format(
//                        heBaoConditionDto.getLimit02Fee1505())
//                        + "元";
//               String businessData = NumberFormat.getInstance().format(
//                        blBusinessData.getLimit02Fee1505())
//                        + "元";
//               this.throwException(title, standardData, businessData);
//               return false;
//           }
//           //added by gengxiaobo begin 20080610 增加最大车累计赔偿限额
//           if((heBaoConditionDto.getLimit03Fee1505()>-1)&&
//                     (heBaoConditionDto.getLimit03Fee1505() < blBusinessData
//                             .getLimit03Fee1505())) {
//               String title = "最大车累计赔偿限额不符合条件!最大车累计赔偿限额＝保单累计赔偿限额*最大车人数/总人数！";
//               String standardData = "小于等于"
//                        + NumberFormat.getInstance().format(
//                        heBaoConditionDto.getLimit03Fee1505())
//                        + "元";
//               String businessData = NumberFormat.getInstance().format(
//                        blBusinessData.getLimit03Fee1505())
//                        + "元";
//               this.throwException(title, standardData, businessData);
//               return false;
//           }  
//           //added by gengxiaobo end 20080610 增加最大车累计赔偿限额
//           
//           
//       }
//       //added by LanNing end 20080421 1505每次事故赔偿限额 
//        * 
//        * modify by duhaichao 20101017 EDN
//        * 
//        * */ 
//
//       //add by zhangruifeng 20080611 begin 卡折业务不自动核批，且三级B(含)以上允许核保
//       System.out.println("blBusinessData.getClassCode()-----------=="+SysConfig.getProperty("PROPOSALTOPOLICY_AUTO_EXCEPT"));
//       System.out.println("blBusinessData.getClassCode()-----------=="+blBusinessData.getClassCode());
//       if(iBusinessType.equals("endorse")){
//            if(SysConfig.getProperty("PROPOSALTOPOLICY_AUTO_EXCEPT").indexOf(blBusinessData.getClassCode())>-1){
//                if ((heBaoConditionDto.getSumAmount()>-1)
//                        &&(heBaoConditionDto.getSumAmount() < blBusinessData
//                        .getSumAmount())){
//                    String title = "总保额不符合条件!";
//                    String standardData = "小于等于"
//                            + NumberFormat.getInstance().format(
//                                    heBaoConditionDto.getSumAmount())
//                            + "元";
//                    String businessData = NumberFormat.getInstance().format(
//                            blBusinessData.getSumAmount())
//                            + "元";
//                    this.throwException(title, standardData, businessData);
//                    return false;
//                }
//            }
//            //add by zhangruifeng 20080611 end
//       }
//     //add by zhouhui 20090625 begin 批改保险期限时，短期费率标志为3时，只能1c级以上才能核过      
//       //add by zhouhui 20090805 begin 增加险种的判断
//       System.out.println("SysConfig.getProperty(\"NOPREMIUMDELAY\")--------=="+SysConfig.getProperty("NOPREMIUMDELAY"));
//       if(SysConfig.getProperty("NOPREMIUMDELAY").indexOf(blBusinessData.getRiskCode())>-1){
//        if(iBusinessType.equals("endorse")&&"01".equals(blBusinessData.getStrEndortype())){
//            if ("N".equals(heBaoConditionDto.getShortRateFlag()) && "3".equals(blBusinessData
//                    .getShortRateFlag()) && !blBusinessData.getRiskCode().equals("2729")) {
//                 String title = "不计保费保险期限顺延的核保级别不够!";
//                 String standardData = "不计保费保险期限顺延需要总公司一级C以上才能审核通过!";
//                 String businessData = "分公司级别";
//                   this.throwException(title, standardData, businessData);
//                return false;
//            }
//        }
//       }
//       //add by zhouhui 20090805 end 增加险种的判断       
//      //add by zhouhui 20090625 end 批改保险期限时，短期费率标志为3时，只能1c级以上才能核过
//
        return true;    
    
    }
    
    /**
	 * Throw string exception.
	 * 
	 * @param title
	 *            the title
	 * @param str
	 *            the str
	 * @throws UserException
	 *             the user exception
	 */
    public void throwStringException(String title, String str)
    throws UserException {
        throw new UserException(2005, 829, title, str);
    }
    
    /**
	 * Throw exceptions.
	 * 
	 * @param title
	 *            the title
	 * @param standardData
	 *            the standard data
	 * @param businessData
	 *            the business data
	 * @throws UserException
	 *             the user exception
	 */
    public void throwExceptions(String title, String standardData,
            String businessData) throws UserException {
        
        throw new UserException(2005, 829, title,  standardData
                + "；" );
    }
    
    /**
	 * Throw exception.
	 * 
	 * @param title
	 *            the title
	 * @param standardData
	 *            the standard data
	 * @param businessData
	 *            the business data
	 * @throws UserException
	 *             the user exception
	 */
    public void throwException(String title, String standardData,
            String businessData) throws UserException {
        
        throw new UserException(2005, 829, title, "您的权限为：" + standardData
                + "；而当前的业务为：" + businessData + "。");
    }
}
