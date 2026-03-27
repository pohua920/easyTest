package com.sinosoft.dmsdriver.service.server;



import com.sinosoft.bpsdriver.service.common.SvrCodeConst;
import com.sinosoft.dmsdriver.domain.FindPrpDplanById.PrpDplanListResBody;
import com.sinosoft.dmsdriver.domain.FindPrpDplanById.PrpDplanListResPacket;
import com.sinosoft.dmsdriver.domain.FindPrpDplanById.PrpDplanResInfo;
import com.sinosoft.dmsdriver.domain.FindPrpDplanById.PrpDplanResList;
import com.sinosoft.dmsdriver.domain.FindPrpDplanById.SendPrpDplanIdReqBody;
import com.sinosoft.dmsdriver.domain.FindPrpDplanById.SendPrpDplanIdReqPacket;
import com.sinosoft.dmsdriver.domain.FindPrpDriskById.PrpDriskListResBody;
import com.sinosoft.dmsdriver.domain.FindPrpDriskById.PrpDriskListResPacket;
import com.sinosoft.dmsdriver.domain.FindPrpDriskById.PrpDriskResInfo;
import com.sinosoft.dmsdriver.domain.FindPrpDriskById.PrpDriskResList;
import com.sinosoft.dmsdriver.domain.FindPrpDriskById.SendPrpDriskIdReqBody;
import com.sinosoft.dmsdriver.domain.FindPrpDriskById.SendPrpDriskIdReqPacket;
import com.sinosoft.dmsdriver.domain.codeTypeTranslate.CodeTypeTranslateReqBody;
import com.sinosoft.dmsdriver.domain.codeTypeTranslate.CodeTypeTranslateReqPacket;
import com.sinosoft.dmsdriver.domain.codeTypeTranslate.CodeTypeTranslateResBody;
import com.sinosoft.dmsdriver.domain.codeTypeTranslate.CodeTypeTranslateResPacket;
import com.sinosoft.dmsdriver.domain.codetransform.CodeTransformReqBody;
import com.sinosoft.dmsdriver.domain.codetransform.CodeTransformReqPacket;
import com.sinosoft.dmsdriver.domain.codetransform.CodeTransformResBody;
import com.sinosoft.dmsdriver.domain.codetransform.CodeTransformResPacket;
import com.sinosoft.dmsdriver.domain.codetranslate.CodeTranslateReqBody;
import com.sinosoft.dmsdriver.domain.codetranslate.CodeTranslateReqPacket;
import com.sinosoft.dmsdriver.domain.codetranslate.CodeTranslateResBody;
import com.sinosoft.dmsdriver.domain.codetranslate.CodeTranslateResPacket;
import com.sinosoft.dmsdriver.domain.codetranslate.TranslateVO;
import com.sinosoft.dmsdriver.domain.common.PageResPacket;
import com.sinosoft.dmsdriver.domain.common.RequestBodySchema;
import com.sinosoft.dmsdriver.domain.common.RequestHeadPacket;
import com.sinosoft.dmsdriver.domain.common.RequestHeadSchema;
import com.sinosoft.dmsdriver.domain.common.RequestPacket;
import com.sinosoft.dmsdriver.domain.common.ResponseHeadSchema;
import com.sinosoft.dmsdriver.domain.countWorkDay.CountWorkDayReqBody;
import com.sinosoft.dmsdriver.domain.countWorkDay.CountWorkDayReqPacket;
import com.sinosoft.dmsdriver.domain.countWorkDay.CountWorkDayResBody;
import com.sinosoft.dmsdriver.domain.countWorkDay.CountWorkDayResPacket;
import com.sinosoft.dmsdriver.domain.exchange.ExchangeReqBody;
import com.sinosoft.dmsdriver.domain.exchange.ExchangeReqPacket;
import com.sinosoft.dmsdriver.domain.exchange.ExchangeResBody;
import com.sinosoft.dmsdriver.domain.exchange.ExchangeResPacket;
import com.sinosoft.dmsdriver.domain.findCompanyByCondition.FindCompanyByConditionReqBody;
import com.sinosoft.dmsdriver.domain.findCompanyByCondition.FindCompanyByConditionReqPacket;
import com.sinosoft.dmsdriver.domain.findPrpDclassById.PrpDclassListResBody;
import com.sinosoft.dmsdriver.domain.findPrpDclassById.PrpDclassListResPacket;
import com.sinosoft.dmsdriver.domain.findPrpDclassById.PrpDclassResInfo;
import com.sinosoft.dmsdriver.domain.findPrpDclassById.PrpDclassResList;
import com.sinosoft.dmsdriver.domain.findPrpDclassById.SendPrpDclassIdReqBody;
import com.sinosoft.dmsdriver.domain.findPrpDclassById.SendPrpDclassIdReqPacket;
import com.sinosoft.dmsdriver.domain.getCode.PrpDcodeInfo;
import com.sinosoft.dmsdriver.domain.getPlanInfo.GetPlanInfoReqBody;
import com.sinosoft.dmsdriver.domain.getPlanInfo.GetPlanInfoReqPacket;
import com.sinosoft.dmsdriver.domain.getPlanInfo.GetPlanInfoResBody;
import com.sinosoft.dmsdriver.domain.getPlanInfo.GetPlanInfoResPacket;
import com.sinosoft.dmsdriver.domain.getPlanWhetherHasFixed.GetPlanWhetherHasFixedReqBody;
import com.sinosoft.dmsdriver.domain.getPlanWhetherHasFixed.GetPlanWhetherHasFixedReqPacket;
import com.sinosoft.dmsdriver.domain.getPlanWhetherHasFixed.GetPlanWhetherHasFixedResBody;
import com.sinosoft.dmsdriver.domain.getPlanWhetherHasFixed.GetPlanWhetherHasFixedResPacket;
import com.sinosoft.dmsdriver.domain.getPrpDagent.GetPrpDagentReqBody;
import com.sinosoft.dmsdriver.domain.getPrpDagent.GetPrpDagentReqPacket;
import com.sinosoft.dmsdriver.domain.getPrpDagent.GetPrpDagentResBody;
import com.sinosoft.dmsdriver.domain.getPrpDagent.GetPrpDagentResPacket;
import com.sinosoft.dmsdriver.domain.getPrpDagent.PrpDagentInfo;
import com.sinosoft.dmsdriver.domain.getPrpDbank.GetPrpDbankReqBody;
import com.sinosoft.dmsdriver.domain.getPrpDbank.GetPrpDbankReqPacket;
import com.sinosoft.dmsdriver.domain.getPrpDbank.GetPrpDbankResBody;
import com.sinosoft.dmsdriver.domain.getPrpDbank.GetPrpDbankResPacket;
import com.sinosoft.dmsdriver.domain.getPrpDbank.PrpDbankInfo;
import com.sinosoft.dmsdriver.domain.getPrpDcode.GetPrpDcodeReqBody;
import com.sinosoft.dmsdriver.domain.getPrpDcode.GetPrpDcodeReqPacket;
import com.sinosoft.dmsdriver.domain.getPrpDcode.GetPrpDcodeResBody;
import com.sinosoft.dmsdriver.domain.getPrpDcode.GetPrpDcodeResPacket;
import com.sinosoft.dmsdriver.domain.getPrpDcode.PrpDcodeResInfo;
import com.sinosoft.dmsdriver.domain.getPrpDcodeList.GetPrpDcodeListReqBody;
import com.sinosoft.dmsdriver.domain.getPrpDcodeList.GetPrpDcodeListReqPacket;
import com.sinosoft.dmsdriver.domain.getPrpDcodeList.GetPrpDcodeListResBody;
import com.sinosoft.dmsdriver.domain.getPrpDcodeList.GetPrpDcodeListResPacket;
import com.sinosoft.dmsdriver.domain.getPrpDcodeList.PrpDcodeList;
import com.sinosoft.dmsdriver.domain.getPrpDcodeList.PrpDcodeListResInfo;
import com.sinosoft.dmsdriver.domain.getPrpDcompany.GetPrpDcompanyReqBody;
import com.sinosoft.dmsdriver.domain.getPrpDcompany.GetPrpDcompanyReqPacket;
import com.sinosoft.dmsdriver.domain.getPrpDcompany.GetPrpDcompanyResBody;
import com.sinosoft.dmsdriver.domain.getPrpDcompany.GetPrpDcompanyResPacket;
import com.sinosoft.dmsdriver.domain.getPrpDcompany.PrpDcompanyResInfo;
import com.sinosoft.dmsdriver.domain.getPrpDcompanyList.GetPrpDcompanyListResBody;
import com.sinosoft.dmsdriver.domain.getPrpDcompanyList.GetPrpDcompanyListResPacket;
import com.sinosoft.dmsdriver.domain.getPrpDcompanyList.PrpDcompanyList;
import com.sinosoft.dmsdriver.domain.getPrpDdealer.GetPrpDdealerReqBody;
import com.sinosoft.dmsdriver.domain.getPrpDdealer.GetPrpDdealerReqPacket;
import com.sinosoft.dmsdriver.domain.getPrpDdealer.GetPrpDdealerResBody;
import com.sinosoft.dmsdriver.domain.getPrpDdealer.GetPrpDdealerResPacket;
import com.sinosoft.dmsdriver.domain.getPrpDdealer.PrpDdealerResInfo;
import com.sinosoft.dmsdriver.domain.getPrpDexch.GetPrpDexchReqBody;
import com.sinosoft.dmsdriver.domain.getPrpDexch.GetPrpDexchReqPacket;
import com.sinosoft.dmsdriver.domain.getPrpDexch.GetPrpDexchResBody;
import com.sinosoft.dmsdriver.domain.getPrpDexch.GetPrpDexchResPacket;
import com.sinosoft.dmsdriver.domain.getPrpDexch.PrpDexchInfo;
import com.sinosoft.dmsdriver.domain.getPrpDport.GetPrpDportReqBody;
import com.sinosoft.dmsdriver.domain.getPrpDport.GetPrpDportReqPacket;
import com.sinosoft.dmsdriver.domain.getPrpDport.GetPrpDportResBody;
import com.sinosoft.dmsdriver.domain.getPrpDport.GetPrpDportResPacket;
import com.sinosoft.dmsdriver.domain.getPrpDport.PrpDportResInfo;
import com.sinosoft.dmsdriver.domain.getPrpDtype.GetPrpDtypeReqBody;
import com.sinosoft.dmsdriver.domain.getPrpDtype.GetPrpDtypeReqPacket;
import com.sinosoft.dmsdriver.domain.getSubCode.GetSubCodeReqBody;
import com.sinosoft.dmsdriver.domain.getSubCode.GetSubCodeReqPacket;
import com.sinosoft.dmsdriver.domain.getSubCode.GetSubCodeResBody;
import com.sinosoft.dmsdriver.domain.getSubCode.GetSubCodeResPacket;
import com.sinosoft.dmsdriver.domain.getSubCode.SubCodeList;
import com.sinosoft.dmsdriver.domain.getSubCode.SubCodeResInfo;
import com.sinosoft.dmsdriver.domain.getUpperCode.GetUpperCodeReqBody;
import com.sinosoft.dmsdriver.domain.getUpperCode.GetUpperCodeReqPacket;
import com.sinosoft.dmsdriver.domain.getUpperCode.GetUpperCodeResBody;
import com.sinosoft.dmsdriver.domain.getUpperCode.GetUpperCodeResPacket;
import com.sinosoft.dmsdriver.domain.getUpperCode.UpperCodeResInfo;
import com.sinosoft.dmsdriver.domain.productSYN.RationObj;
import com.sinosoft.dmsdriver.domain.reverseCodeTyeAndCode.ReverseCodeTyeAndCodeReqBody;
import com.sinosoft.dmsdriver.domain.reverseCodeTyeAndCode.ReverseCodeTyeAndCodeReqPacket;
import com.sinosoft.dmsdriver.domain.reverseCodeTyeAndCode.ReverseCodeTyeAndCodeResBody;
import com.sinosoft.dmsdriver.domain.reverseCodeTyeAndCode.ReverseCodeTyeAndCodeResPacket;
import com.sinosoft.dmsdriver.domain.risktransform.RiskTransformReqBody;
import com.sinosoft.dmsdriver.domain.risktransform.RiskTransformReqPacket;
import com.sinosoft.dmsdriver.domain.risktransform.RiskTransformResBody;
import com.sinosoft.dmsdriver.domain.risktransform.RiskTransformResPacket;
import com.sinosoft.dmsdriver.domain.translateCode.TranslateCodeReqBody;
import com.sinosoft.dmsdriver.domain.translateCode.TranslateCodeReqPacket;
import com.sinosoft.dmsdriver.domain.translateCode.TranslateCodeResBody;
import com.sinosoft.dmsdriver.domain.translateCode.TranslateCodeResPacket;
import com.sinosoft.dmsdriver.domain.translateLimit.TranslateLimitReqBody;
import com.sinosoft.dmsdriver.domain.translateLimit.TranslateLimitReqPacket;
import com.sinosoft.dmsdriver.domain.translateLimit.TranslateLimitResBody;
import com.sinosoft.dmsdriver.domain.translateLimit.TranslateLimitResPacket;
import com.sinosoft.dmsdriver.domain.updateprpdstatistics.UpdatePrpDstatisticsReqBody;
import com.sinosoft.dmsdriver.domain.updateprpdstatistics.UpdatePrpDstatisticsReqPacket;
import com.sinosoft.dmsdriver.model.IPServiceConfig;
import com.sinosoft.dmsdriver.model.PrpDaccountInfo;
import com.sinosoft.dmsdriver.model.PrpDagent;
import com.sinosoft.dmsdriver.model.PrpDbank;
import com.sinosoft.dmsdriver.model.PrpDclass;
import com.sinosoft.dmsdriver.model.PrpDcode;
import com.sinosoft.dmsdriver.model.PrpDcodeId;
import com.sinosoft.dmsdriver.model.PrpDcompany;
import com.sinosoft.dmsdriver.model.PrpDdealer;
import com.sinosoft.dmsdriver.model.PrpDexch;
import com.sinosoft.dmsdriver.model.PrpDexchId;
import com.sinosoft.dmsdriver.model.PrpDkindCar;
import com.sinosoft.dmsdriver.model.PrpDkindProduct;
import com.sinosoft.dmsdriver.model.PrpDkindReport;
import com.sinosoft.dmsdriver.model.PrpDplan;
import com.sinosoft.dmsdriver.model.PrpDport;
import com.sinosoft.dmsdriver.model.PrpDration;
import com.sinosoft.dmsdriver.model.PrpDrisk;
import com.sinosoft.dmsdriver.model.PrpDriskClause;
import com.sinosoft.dmsdriver.model.PrpDriskClauseId;
import com.sinosoft.dmsdriver.model.PrpDriskClauseKind;
import com.sinosoft.dmsdriver.model.PrpDriskEngage;
import com.sinosoft.dmsdriver.model.PrpDriskEngageId;
import com.sinosoft.dmsdriver.model.PrpDriskItem;
import com.sinosoft.dmsdriver.model.PrpDriskItemId;
import com.sinosoft.dmsdriver.model.PrpDriskLimit;
import com.sinosoft.dmsdriver.model.PrpDriskLimitId;
import com.sinosoft.dmsdriver.model.PrpDstatistics;
import com.sinosoft.dmsdriver.model.PrpDtype;
import com.sinosoft.dmsdriver.service.common.DictPage;
import com.sinosoft.dmsdriver.service.transform.CodeTransformService;
import com.sinosoft.dmsdriver.service.transform.CodeTranslateService;
import com.sinosoft.dmsdriver.service.transform.CodeTypeTranslateService;
import com.sinosoft.dmsdriver.service.transform.CountWorkDayService;
import com.sinosoft.dmsdriver.service.transform.ExchangeService;
import com.sinosoft.dmsdriver.service.transform.FindCompanyByConditionService;
import com.sinosoft.dmsdriver.service.transform.FindPrpDclassByIdService;
import com.sinosoft.dmsdriver.service.transform.FindPrpDplanByIdService;
import com.sinosoft.dmsdriver.service.transform.FindPrpDriskByIdService;
import com.sinosoft.dmsdriver.service.transform.GetPlanInfoService;
import com.sinosoft.dmsdriver.service.transform.GetPlanWhetherHasFixedService;
import com.sinosoft.dmsdriver.service.transform.GetPrpDagentService;
import com.sinosoft.dmsdriver.service.transform.GetPrpDbankService;
import com.sinosoft.dmsdriver.service.transform.GetPrpDcodeListService;
import com.sinosoft.dmsdriver.service.transform.GetPrpDcodeService;
import com.sinosoft.dmsdriver.service.transform.GetPrpDcompanyListService;
import com.sinosoft.dmsdriver.service.transform.GetPrpDcompanyService;
import com.sinosoft.dmsdriver.service.transform.GetPrpDdealerService;
import com.sinosoft.dmsdriver.service.transform.GetPrpDexchService;
import com.sinosoft.dmsdriver.service.transform.GetPrpDportService;
import com.sinosoft.dmsdriver.service.transform.GetPrpDtypeService;
import com.sinosoft.dmsdriver.service.transform.GetSubCodeService;
import com.sinosoft.dmsdriver.service.transform.GetUpperCodeService;
import com.sinosoft.dmsdriver.service.transform.RequestService;
import com.sinosoft.dmsdriver.service.transform.ReverseCodeTyeAndCodeService;
import com.sinosoft.dmsdriver.service.transform.RiskTransformService;
import com.sinosoft.dmsdriver.service.transform.TranslateCodeService;
import com.sinosoft.dmsdriver.service.transform.TranslateLimitService;
import com.sinosoft.dmsdriver.service.transform.UpdatePrpDstatisticsService;
import com.sinosoft.dmsdriver.service.transform.impl.CodeTransformServiceImpl;
import com.sinosoft.dmsdriver.service.transform.impl.CodeTranslateServiceImpl;
import com.sinosoft.dmsdriver.service.transform.impl.CodeTypeTranslateImpl;
import com.sinosoft.dmsdriver.service.transform.impl.CountWorkDayServiceImpl;
import com.sinosoft.dmsdriver.service.transform.impl.ExchangeImpl;
import com.sinosoft.dmsdriver.service.transform.impl.FindCompanyByConditionServiceImpl;
import com.sinosoft.dmsdriver.service.transform.impl.FindPrpDclassByIdServiceImpl;
import com.sinosoft.dmsdriver.service.transform.impl.FindPrpDplanByIdServiceImpl;
import com.sinosoft.dmsdriver.service.transform.impl.FindPrpDriskByIdServiceImpl;
import com.sinosoft.dmsdriver.service.transform.impl.GetPlanInfoServiceImpl;
import com.sinosoft.dmsdriver.service.transform.impl.GetPlanWhetherHasFixedServiceImpl;
import com.sinosoft.dmsdriver.service.transform.impl.GetPrpDagentServiceImpl;
import com.sinosoft.dmsdriver.service.transform.impl.GetPrpDbankServiceImpl;
import com.sinosoft.dmsdriver.service.transform.impl.GetPrpDcodeListServiceImpl;
import com.sinosoft.dmsdriver.service.transform.impl.GetPrpDcodeServiceImpl;
import com.sinosoft.dmsdriver.service.transform.impl.GetPrpDcompanyListServiceImpl;
import com.sinosoft.dmsdriver.service.transform.impl.GetPrpDcompanyServiceImpl;
import com.sinosoft.dmsdriver.service.transform.impl.GetPrpDdealerServiceImpl;
import com.sinosoft.dmsdriver.service.transform.impl.GetPrpDexchServiceImpl;
import com.sinosoft.dmsdriver.service.transform.impl.GetPrpDportServiceImpl;
import com.sinosoft.dmsdriver.service.transform.impl.GetPrpDtypeServiceImpl;
import com.sinosoft.dmsdriver.service.transform.impl.GetSubCodeServiceImpl;
import com.sinosoft.dmsdriver.service.transform.impl.GetUpperCodeServiceImpl;
import com.sinosoft.dmsdriver.service.transform.impl.RequestServiceImpl;
import com.sinosoft.dmsdriver.service.transform.impl.ReverseCodeTyeAndCodeServiceImpl;
import com.sinosoft.dmsdriver.service.transform.impl.RiskTransformServiceImpl;
import com.sinosoft.dmsdriver.service.transform.impl.TranslateCodeServiceImpl;
import com.sinosoft.dmsdriver.service.transform.impl.TranslateLimitServiceImpl;
import com.sinosoft.dmsdriver.service.transform.impl.UpdatePrpDstatisticsServiceImpl;
import com.sinosoft.dmsdriver.util.PubFun;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.sinosoft.dmsdriver.service.server.PageService;

public class DictAPIService
{
  private static Log log = LogFactory.getLog(DictAPIService.class);

  public static PrpDrisk findPrpDriskById(String systemCode, String riskcode)
    throws Exception
  {
    FindPrpDriskByIdService findPrpDriskByIdService = new FindPrpDriskByIdServiceImpl();
    SendPrpDriskIdReqPacket sendPrpDriskIdReqPacket = new SendPrpDriskIdReqPacket();

    sendPrpDriskIdReqPacket.getHEAD().setSYSTEMCODE(systemCode);
    sendPrpDriskIdReqPacket.getHEAD().setREQUEST_TYPE(
      "D53");
    sendPrpDriskIdReqPacket.getBODY().setRISKCODE(riskcode);
    PrpDriskListResPacket res = new PrpDriskListResPacket();
    if (("".equals(systemCode)) || ("".equals(riskcode))) {
      throw new Exception("未查询到相关信息！");
    }
    res = findPrpDriskByIdService.execute(sendPrpDriskIdReqPacket);
    if ("0".equals(res.getHEAD().getRESPONSE_CODE())) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    List list = Arrays.asList(res.getBODY()
      .getPRPDRISKLIST().getPRPDRISK());
    PrpDrisk prpDrisk = null;
    if (list.size() > 0) {
      prpDrisk = new PrpDrisk();
      prpDrisk.setRiskCode(((PrpDriskResInfo)list.get(0)).getRISKCODE());
      prpDrisk.setRiskCName(((PrpDriskResInfo)list.get(0)).getRISKCNAME());
      prpDrisk.setRiskTName(((PrpDriskResInfo)list.get(0)).getRISKTNAME());
      prpDrisk.setRiskEName(((PrpDriskResInfo)list.get(0)).getRISKENAME());
      prpDrisk.setRiskAttribute(((PrpDriskResInfo)list.get(0)).getRISKATTRIBUTE());
      prpDrisk.setSaleAreaLevel(((PrpDriskResInfo)list.get(0)).getSALEAREALEVEL());
      prpDrisk.setSaleAreaCode(((PrpDriskResInfo)list.get(0)).getSALEAREACODE());
      prpDrisk.setMaterialContxt(((PrpDriskResInfo)list.get(0)).getMATERIALCONTXT());
      prpDrisk.setClassCode(((PrpDriskResInfo)list.get(0)).getCLASSCODE());
      prpDrisk.setFrameCode(((PrpDriskResInfo)list.get(0)).getFRAMECODE());
      prpDrisk.setRiskSCName(((PrpDriskResInfo)list.get(0)).getRISKSCNAME());
      prpDrisk.setRiskSEName(((PrpDriskResInfo)list.get(0)).getRISKSENAME());
      prpDrisk.setPolicyProcessFlag(((PrpDriskResInfo)list.get(0)).getPOLICYPROCESSFLAG());
      prpDrisk.setRequiredFlag(((PrpDriskResInfo)list.get(0)).getREQUIREDFLAG());
      prpDrisk.setRateUnit(Integer.getInteger(((PrpDriskResInfo)list.get(0)).getRATEUNIT()));
      prpDrisk.setShortRateFlag(((PrpDriskResInfo)list.get(0)).getSHORTRATEFLAG());
      prpDrisk.setClassFlag(((PrpDriskResInfo)list.get(0)).getCLASSFLAG());
      prpDrisk.setRiskFlag(((PrpDriskResInfo)list.get(0)).getRISKFLAG());
      prpDrisk.setEndUpdaterCode(((PrpDriskResInfo)list.get(0)).getENDUPDATERCODE());
      prpDrisk.setProjectCode(((PrpDriskResInfo)list.get(0)).getPROJECTCODE());
      if ((((PrpDriskResInfo)list.get(0)).getOPERATETIMEFORHIS() != null) && 
        (!"".equals(((PrpDriskResInfo)list.get(0)).getOPERATETIMEFORHIS()))) {
        prpDrisk.setOperateTimeForHis(PubFun.StrToDate(
          ((PrpDriskResInfo)list.get(0)).getOPERATETIMEFORHIS()));
      }

      prpDrisk.setPlanInd(((PrpDriskResInfo)list.get(0)).getPLANIND());

      prpDrisk.setAccountLevel(((PrpDriskResInfo)list.get(0)).getACCOUNTLEVEL());
      prpDrisk.setReinsLevel(((PrpDriskResInfo)list.get(0)).getREINSLEVEL());
      prpDrisk.setManagementLevel(
        ((PrpDriskResInfo)list.get(0)).getMANAGEMENTLEVEL());
      prpDrisk.setStatLevel(((PrpDriskResInfo)list.get(0)).getSTATLEVEL());

      prpDrisk.setCreatorCode(((PrpDriskResInfo)list.get(0)).getCREATORCODE());
      prpDrisk.setUpdaterCode(((PrpDriskResInfo)list.get(0)).getUPDATERCODE());
      prpDrisk.setValidInd(((PrpDriskResInfo)list.get(0)).getVALIDIND());
      prpDrisk.setTcol1(((PrpDriskResInfo)list.get(0)).getTCOL1());
      prpDrisk.setTcol2(((PrpDriskResInfo)list.get(0)).getTCOL2());
      prpDrisk.setTcol3(((PrpDriskResInfo)list.get(0)).getTCOL3());
      prpDrisk.setRemark(((PrpDriskResInfo)list.get(0)).getREMARK());
      prpDrisk.setFlag(((PrpDriskResInfo)list.get(0)).getFLAG());
      if ((((PrpDriskResInfo)list.get(0)).getCREATETIME() != null) && 
        (!"".equals(((PrpDriskResInfo)list.get(0)).getCREATETIME()))) {
        prpDrisk.setCreateTime(PubFun.StrToDate(
          ((PrpDriskResInfo)list.get(0)).getCREATETIME()));
      }
      if ((((PrpDriskResInfo)list.get(0)).getUPDATETIME() != null) && 
        (!"".equals(((PrpDriskResInfo)list.get(0)).getUPDATETIME()))) {
        prpDrisk.setUpdateTime(PubFun.StrToDate(
          ((PrpDriskResInfo)list.get(0)).getUPDATETIME()));
      }
      if ((((PrpDriskResInfo)list.get(0)).getVALIDDATE() != null) && 
        (!"".equals(((PrpDriskResInfo)list.get(0)).getVALIDDATE()))) {
        prpDrisk.setValidDate(PubFun.StrToDate(
          ((PrpDriskResInfo)list.get(0)).getVALIDDATE()));
      }
      if ((((PrpDriskResInfo)list.get(0)).getINVALIDDATE() != null) && 
        (!"".equals(((PrpDriskResInfo)list.get(0)).getINVALIDDATE()))) {
        prpDrisk.setInvalidDate(PubFun.StrToDate(
          ((PrpDriskResInfo)list.get(0)).getINVALIDDATE()));
      }
    }
    return prpDrisk;
  }

  public static PrpDplan findPrpDplanById(String systemCode, String plancode)
    throws Exception
  {
    FindPrpDplanByIdService findPrpDplanByIdService = new FindPrpDplanByIdServiceImpl();
    SendPrpDplanIdReqPacket sendPrpDplanIdReqPacket = new SendPrpDplanIdReqPacket();

    sendPrpDplanIdReqPacket.getHEAD().setSYSTEMCODE(systemCode);
    sendPrpDplanIdReqPacket.getHEAD().setREQUEST_TYPE("D52");
    sendPrpDplanIdReqPacket.getBODY().setPLANCODE(plancode);
    PrpDplanListResPacket res = new PrpDplanListResPacket();
    if (("".equals(systemCode)) || ("".equals(plancode))) {
      throw new Exception("未查询到相关信息！");
    }
    res = findPrpDplanByIdService.execute(sendPrpDplanIdReqPacket);
    if ("0".equals(res.getHEAD().getRESPONSE_CODE())) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    List list = Arrays.asList(res.getBODY().getPRPDPLANLIST().getPRPDPLAN());
    PrpDplan prpDplan = null;
    if (list.size() > 0) {
      prpDplan = new PrpDplan();
      prpDplan.setPlanCode(((PrpDplanResInfo)list.get(0)).getPLANCODE());
      prpDplan.setPlanCName(((PrpDplanResInfo)list.get(0)).getPLANCNAME());
      prpDplan.setPlanTName(((PrpDplanResInfo)list.get(0)).getPLANTNAME());
      prpDplan.setPlanSName(((PrpDplanResInfo)list.get(0)).getPLANSNAME());
      prpDplan.setPlanEName(((PrpDplanResInfo)list.get(0)).getPLANENAME());
      prpDplan.setIsFixedFlag(((PrpDplanResInfo)list.get(0)).getISFIXEDFLAG());
      prpDplan.setFrameCode(((PrpDplanResInfo)list.get(0)).getFRAMECODE());

      prpDplan.setRiskCode(((PrpDplanResInfo)list.get(0)).getRISKCODE());

      prpDplan.setCreaterCode(((PrpDplanResInfo)list.get(0)).getCREATORCODE());
      prpDplan.setUpdaterCode(((PrpDplanResInfo)list.get(0)).getUPDATERCODE());
      prpDplan.setContentNumber(((PrpDplanResInfo)list.get(0)).getCONTENTNUMBER());
      prpDplan.setValidInd(((PrpDplanResInfo)list.get(0)).getVALIDIND());
      prpDplan.setTcol1(((PrpDplanResInfo)list.get(0)).getTCOL1());
      prpDplan.setTcol2(((PrpDplanResInfo)list.get(0)).getTCOL2());
      prpDplan.setTcol3(((PrpDplanResInfo)list.get(0)).getTCOL3());
      prpDplan.setRemark(((PrpDplanResInfo)list.get(0)).getREMARK());
      prpDplan.setFlag(((PrpDplanResInfo)list.get(0)).getFLAG());
      if ((((PrpDplanResInfo)list.get(0)).getCREATETIME() != null) && (!"".equals(((PrpDplanResInfo)list.get(0)).getCREATETIME()))) {
        prpDplan.setCreateTime(PubFun.StrToDate(((PrpDplanResInfo)list.get(0)).getCREATETIME()));
      }
      if ((((PrpDplanResInfo)list.get(0)).getUPDATETIME() != null) && (!"".equals(((PrpDplanResInfo)list.get(0)).getUPDATETIME()))) {
        prpDplan.setUpdateTime(PubFun.StrToDate(((PrpDplanResInfo)list.get(0)).getUPDATETIME()));
      }
      if ((((PrpDplanResInfo)list.get(0)).getVALIDDATE() != null) && (!"".equals(((PrpDplanResInfo)list.get(0)).getVALIDDATE()))) {
        prpDplan.setValidDate(PubFun.StrToDate(((PrpDplanResInfo)list.get(0)).getVALIDDATE()));
      }
      if ((((PrpDplanResInfo)list.get(0)).getINVAIDDATE() != null) && (!"".equals(((PrpDplanResInfo)list.get(0)).getINVAIDDATE()))) {
        prpDplan.setInvalidDate(PubFun.StrToDate(((PrpDplanResInfo)list.get(0)).getINVAIDDATE()));
      }
    }
    return prpDplan;
  }

  public static PrpDclass findPrpDclassById(String systemCode, String classcode)
    throws Exception
  {
    FindPrpDclassByIdService findPrpDclassByIdService = new FindPrpDclassByIdServiceImpl();
    SendPrpDclassIdReqPacket sendPrpDclassIdReqPacket = new SendPrpDclassIdReqPacket();

    sendPrpDclassIdReqPacket.getHEAD().setSYSTEMCODE(systemCode);
    sendPrpDclassIdReqPacket.getHEAD().setREQUEST_TYPE("D44");
    sendPrpDclassIdReqPacket.getBODY().setCLASSCODE(classcode);
    PrpDclassListResPacket res = new PrpDclassListResPacket();
    if (("".equals(systemCode)) || ("".equals(classcode))) {
      throw new Exception("未查询到相关信息！");
    }
    res = findPrpDclassByIdService.execute(sendPrpDclassIdReqPacket);
    if ("0".equals(res.getHEAD().getRESPONSE_CODE())) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    List list = Arrays.asList(res.getBODY().getPRPDCLASSLIST().getPRPDCLASS());
    PrpDclass prpDclass = null;
    if (list.size() > 0) {
      prpDclass = new PrpDclass();
      prpDclass.setClassCode(((PrpDclassResInfo)list.get(0)).getCLASSCODE());
      prpDclass.setClassCName(((PrpDclassResInfo)list.get(0)).getCLASSCNAME());
      prpDclass.setClassSCName(((PrpDclassResInfo)list.get(0)).getCLASSSCNAME());
      prpDclass.setClassTName(((PrpDclassResInfo)list.get(0)).getCLASSTNAME());
      prpDclass.setClassEName(((PrpDclassResInfo)list.get(0)).getCLASSENAME());
      prpDclass.setClassSEName(((PrpDclassResInfo)list.get(0)).getCLASSSENAME());
      prpDclass.setCreatorCode(((PrpDclassResInfo)list.get(0)).getCREATORCODE());
      if ((((PrpDclassResInfo)list.get(0)).getCREATETIME() != null) && (!"".equals(((PrpDclassResInfo)list.get(0)).getCREATETIME()))) {
        prpDclass.setCreateTime(PubFun.StrToDate(((PrpDclassResInfo)list.get(0)).getCREATETIME()));
      }
      prpDclass.setUpdaterCode(((PrpDclassResInfo)list.get(0)).getUPDATERCODE());
      if ((((PrpDclassResInfo)list.get(0)).getUPDATETIME() != null) && (!"".equals(((PrpDclassResInfo)list.get(0)).getUPDATETIME()))) {
        prpDclass.setUpdateTime(PubFun.StrToDate(((PrpDclassResInfo)list.get(0)).getUPDATETIME()));
      }
      if ((((PrpDclassResInfo)list.get(0)).getVALIDDATE() != null) && (!"".equals(((PrpDclassResInfo)list.get(0)).getVALIDDATE()))) {
        prpDclass.setValidDate(PubFun.StrToDate(((PrpDclassResInfo)list.get(0)).getVALIDDATE()));
      }
      if ((((PrpDclassResInfo)list.get(0)).getINVAIDDATE() != null) && (!"".equals(((PrpDclassResInfo)list.get(0)).getINVAIDDATE()))) {
        prpDclass.setInvalidDate(PubFun.StrToDate(((PrpDclassResInfo)list.get(0)).getINVAIDDATE()));
      }

      prpDclass.setValidInd(((PrpDclassResInfo)list.get(0)).getVALIDIND());
      prpDclass.setTcol1(((PrpDclassResInfo)list.get(0)).getTCOL1());
      prpDclass.setTcol2(((PrpDclassResInfo)list.get(0)).getTCOL2());
      prpDclass.setTcol3(((PrpDclassResInfo)list.get(0)).getTCOL3());
      prpDclass.setRemark(((PrpDclassResInfo)list.get(0)).getREMARK());
      prpDclass.setFlag(((PrpDclassResInfo)list.get(0)).getFLAG());
    }
    return prpDclass;
  }

  public static List<PrpDcode> getPrpDcodeList(String systemCode, String codeType)
    throws Exception
  {
    GetPrpDcodeListService getPrpDcodeListService = new GetPrpDcodeListServiceImpl();

    GetPrpDcodeListReqPacket getPrpDcodeListReqPacket = new GetPrpDcodeListReqPacket();
    getPrpDcodeListReqPacket.getHEAD().setSYSTEMCODE(systemCode);
    getPrpDcodeListReqPacket.getHEAD().setREQUEST_TYPE("D41");
    getPrpDcodeListReqPacket.getBODY().setCODETYPE(codeType);
    GetPrpDcodeListResPacket res = new GetPrpDcodeListResPacket();
    if (("".equals(systemCode)) || ("".equals(codeType)))
      throw new Exception("未查询到相关信息！");
    if ((systemCode.getBytes().length > 10) || (codeType.getBytes().length > 20)) {
      throw new Exception("入参错误！");
    }

    res = getPrpDcodeListService.execute(getPrpDcodeListReqPacket);
    List prpDcodeList = new ArrayList();
    if ("0".equals(res.getHEAD().getRESPONSE_CODE()))
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    if ("9100".equals(res.getHEAD().getERROR_CODE())) {
      return prpDcodeList;
    }
    List list = Arrays.asList(res.getBODY().getCODELIST().getCODEINFO());
    for (int i = 0; i < list.size(); ++i) {
      PrpDcodeListResInfo prpDcodeListResInfo = (PrpDcodeListResInfo)list.get(i);
      PrpDcode prpDcode = new PrpDcode();
      PrpDcodeId prpDcodeId = new PrpDcodeId();
      prpDcodeId.setCodeCode(prpDcodeListResInfo.getCODECODE());
      prpDcodeId.setCodeType(prpDcodeListResInfo.getCODETYPE());
      prpDcode.setId(prpDcodeId);
      prpDcode.setCodeCName(prpDcodeListResInfo.getCODECNAME());
      prpDcode.setCodeEName(prpDcodeListResInfo.getCODEENAME());
      prpDcode.setNewCodeCode(prpDcodeListResInfo.getNEWCODECODE());
      prpDcode.setValidStatus(prpDcodeListResInfo.getVALIDSTATUS());
      prpDcode.setFlag(prpDcodeListResInfo.getFLAG());
      prpDcodeList.add(prpDcode);
    }
    return prpDcodeList;
  }

  public static String codeTypeTranslate(String systemCode, String codeType)
    throws Exception
  {
    CodeTypeTranslateService codeTypeTranslateService = new CodeTypeTranslateImpl();

    CodeTypeTranslateReqPacket codeTypeTranslateReqPacket = new CodeTypeTranslateReqPacket();
    codeTypeTranslateReqPacket.getHEAD().setREQUEST_TYPE("D22");
    codeTypeTranslateReqPacket.getHEAD().setSYSTEMCODE(systemCode);
    codeTypeTranslateReqPacket.getBODY().setCODETYPE(codeType);
    CodeTypeTranslateResPacket res = new CodeTypeTranslateResPacket();
    if (("".equals(systemCode)) || ("".equals(codeType)))
      throw new Exception("未查询到相关信息！");
    if ((systemCode.getBytes().length > 10) || (codeType.getBytes().length > 20)) {
      throw new Exception("入参错误！");
    }

    res = codeTypeTranslateService.execute(codeTypeTranslateReqPacket);
    if ("0".equals(res.getHEAD().getRESPONSE_CODE())) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    String codeTypeCName = "";
    codeTypeCName = res.getBODY().getCODETYPECNAME();
    return codeTypeCName;
  }

  public static String translateLimit(String systemCode, String riskCode, String limitCode)
    throws Exception
  {
    TranslateLimitService translateLimitService = new TranslateLimitServiceImpl();

    TranslateLimitReqPacket translateLimitReqPacket = new TranslateLimitReqPacket();
    translateLimitReqPacket.getHEAD().setREQUEST_TYPE("D140");
    translateLimitReqPacket.getHEAD().setSYSTEMCODE(systemCode);
    translateLimitReqPacket.getBODY().setRISKCODE(riskCode);
    translateLimitReqPacket.getBODY().setLIMITCODE(limitCode);
    TranslateLimitResPacket res = new TranslateLimitResPacket();
    if (("".equals(riskCode)) || ("".equals(limitCode)))
      throw new Exception("入参不能为空！");
    if ((systemCode.getBytes().length > 10) || (riskCode.getBytes().length > 20) || (limitCode.getBytes().length > 20)) {
      throw new Exception("入参错误！");
    }

    res = translateLimitService.execute(translateLimitReqPacket);
    if ("0".equals(res.getHEAD().getRESPONSE_CODE())) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    String codeCName = "";
    codeCName = res.getBODY().getCODECNAME();
    return codeCName;
  }

  public static Double exchange(String systemCode, Date currDate, String baseCurrency, String exchCurrency, double amount)
    throws Exception
  {
    ExchangeService exchangeService = new ExchangeImpl();

    ExchangeReqPacket exchangeReqPacket = new ExchangeReqPacket();
    exchangeReqPacket.getHEAD().setREQUEST_TYPE("D33");
    exchangeReqPacket.getHEAD().setSYSTEMCODE(systemCode);
    exchangeReqPacket.getBODY().setAMOUNT(Double.toString(amount));
    exchangeReqPacket.getBODY().setBASECURRENCY(baseCurrency);
    exchangeReqPacket.getBODY().setCURRDATE(PubFun.DateToStr(currDate));
    exchangeReqPacket.getBODY().setEXCHCURRENCY(exchCurrency);
    ExchangeResPacket res = new ExchangeResPacket();
    if (("".equals(systemCode)) || (currDate == null) || ("".equals(baseCurrency)) || ("".equals(exchCurrency)))
      throw new Exception("未查询到相关信息！");
    if ((systemCode.getBytes().length > 10) || (baseCurrency.getBytes().length > 3) || 
      (exchCurrency.length() > 3)) {
      throw new Exception("入参错误！");
    }

    res = exchangeService.execute(exchangeReqPacket);

    Double exchedAmount = Double.valueOf(Double.parseDouble(res.getBODY().getEXCHEDAMOUNT()));
    return exchedAmount;
  }

  public static List<PrpDcompany> getAllSubCompany(String systemCode, String comCode)
    throws Exception
  {
    GetPrpDcompanyListService getPrpDcompanyListService = new GetPrpDcompanyListServiceImpl();

    GetPrpDcompanyReqPacket getPrpDcompanyReqPacket = new GetPrpDcompanyReqPacket();
    getPrpDcompanyReqPacket.getHEAD().setSYSTEMCODE(systemCode);
    getPrpDcompanyReqPacket.getHEAD().setREQUEST_TYPE("D31");
    getPrpDcompanyReqPacket.getBODY().setCOMCODE(comCode);
    GetPrpDcompanyListResPacket res = new GetPrpDcompanyListResPacket();
    if (("".equals(systemCode)) || ("".equals(comCode))) {
      throw new Exception("未查询到相关信息！");
    }

    res = getPrpDcompanyListService.execute(getPrpDcompanyReqPacket);
    List companyList = new ArrayList();
    if ("0".equals(res.getHEAD().getRESPONSE_CODE()))
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    if ("9100".equals(res.getHEAD().getERROR_CODE())) {
      return companyList;
    }
    List list = Arrays.asList(res.getBODY().getPRPDCOMPANYLIST().getPRPDCOMPANY());
    for (int i = 0; i < list.size(); ++i) {
      PrpDcompany prpDcompany = new PrpDcompany();
      PrpDcompanyResInfo prpDcompanyResInfo = (PrpDcompanyResInfo)list.get(i);
      prpDcompany.setComCode(prpDcompanyResInfo.getCOMCODE());
      prpDcompany.setComCName(prpDcompanyResInfo.getCOMCNAME());
      prpDcompany.setComEName(prpDcompanyResInfo.getCOMENAME());
      prpDcompany.setAddressCName(prpDcompanyResInfo.getADDRESSCNAME());
      prpDcompany.setAddressEName(prpDcompanyResInfo.getADDRESSENAME());
      prpDcompany.setPostCode(prpDcompanyResInfo.getPOSTCODE());
      prpDcompany.setPhoneNumber(prpDcompanyResInfo.getPHONENUMBER());
      prpDcompany.setFaxNumber(prpDcompanyResInfo.getFAXNUMBER());
      prpDcompany.setUpperComCode(prpDcompanyResInfo.getUPPERCOMCODE());
      prpDcompany.setInsurerName(prpDcompanyResInfo.getINSURERNAME());
      prpDcompany.setComType(prpDcompanyResInfo.getCOMTYPE());
      prpDcompany.setManager(prpDcompanyResInfo.getMANAGER());
      prpDcompany.setAccountant(prpDcompanyResInfo.getACCOUNTANT());
      prpDcompany.setRemark(prpDcompanyResInfo.getREMARK());
      prpDcompany.setNewComCode(prpDcompanyResInfo.getNEWCOMCODE());
      prpDcompany.setComKind(prpDcompanyResInfo.getCOMKIND());
      prpDcompany.setUpdateFlag(prpDcompanyResInfo.getUPDATEFLAG());
      prpDcompany.setUpdateDate(prpDcompanyResInfo.getUPDATEDATE());
      prpDcompany.setOperatorComCode(prpDcompanyResInfo.getOPERATORCOMCODE());
      prpDcompany.setAcntUnit(prpDcompanyResInfo.getACNTUNIT());
      prpDcompany.setArticleCode(prpDcompanyResInfo.getARTICLECODE());
      prpDcompany.setComFlag(prpDcompanyResInfo.getCOMFLAG());
      prpDcompany.setCenterFlag(prpDcompanyResInfo.getCENTERFLAG());
      prpDcompany.setBranchType(prpDcompanyResInfo.getBRANCHTYPE());
      if ((!"".equals(prpDcompanyResInfo.getCOMLEVEL())) && (prpDcompanyResInfo.getCOMLEVEL() != null)) {
        prpDcompany.setComLevel(new BigDecimal(prpDcompanyResInfo.getCOMLEVEL()));
      }
      prpDcompany.setFlag(prpDcompanyResInfo.getFLAG());
      prpDcompany.setValidStatus(prpDcompanyResInfo.getVALIDSTATUS());
      prpDcompany.setUpperPath(prpDcompanyResInfo.getGRADE());
      companyList.add(prpDcompany);
    }
    return companyList;
  }

  public static PrpDagent getPrpDagent(String systemCode, String agentCode)
    throws Exception
  {
    GetPrpDagentService getPrpDagentService = new GetPrpDagentServiceImpl();

    GetPrpDagentReqPacket getPrpDagentReqPacket = new GetPrpDagentReqPacket();
    getPrpDagentReqPacket.getHEAD().setSYSTEMCODE(systemCode);
    getPrpDagentReqPacket.getHEAD().setREQUEST_TYPE("D35");
    getPrpDagentReqPacket.getBODY().setAGENTCODE(agentCode);
    GetPrpDagentResPacket res = new GetPrpDagentResPacket();
    if (("".equals(systemCode)) || ("".equals(agentCode))) {
      throw new Exception("未查询到相关信息！");
    }
    if ("0".equals(res.getHEAD().getRESPONSE_CODE())) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }

    res = getPrpDagentService.execute(getPrpDagentReqPacket);
    PrpDagent prpDagent = new PrpDagent();
    if ("9100".equals(res.getHEAD().getERROR_CODE())) {
      return null;
    }
    prpDagent.setAgentCode(res.getBODY().getPRPDAGENTINFO().getAGENTNAME());
    prpDagent.setAgentName(res.getBODY().getPRPDAGENTINFO().getAGENTNAME());
    prpDagent.setAddressName(res.getBODY().getPRPDAGENTINFO().getADDRESSNAME());
    prpDagent.setPostCode(res.getBODY().getPRPDAGENTINFO().getPOSTCODE());
    prpDagent.setAgentType(res.getBODY().getPRPDAGENTINFO().getAGENTTYPE());
    prpDagent.setPermitNo(res.getBODY().getPRPDAGENTINFO().getPERMITNO());
    prpDagent.setLinkerName(res.getBODY().getPRPDAGENTINFO().getLINKERNAME());
    if ((!"".equals(res.getBODY().getPRPDAGENTINFO().getBARGAINDATE())) && 
      (res.getBODY().getPRPDAGENTINFO().getBARGAINDATE() != null)) {
      prpDagent.setBargainDate(PubFun.StrToDate(res.getBODY().getPRPDAGENTINFO().getBARGAINDATE()));
    }
    prpDagent.setPhoneNumber(res.getBODY().getPRPDAGENTINFO().getPHONENUMBER());
    prpDagent.setFaxNumber(res.getBODY().getPRPDAGENTINFO().getFAXNUMBER());
    prpDagent.setComCode(res.getBODY().getPRPDAGENTINFO().getCOMCODE());
    prpDagent.setUpperAgentCode(res.getBODY().getPRPDAGENTINFO().getUPPERAGENTCODE());
    prpDagent.setNewAgentCode(res.getBODY().getPRPDAGENTINFO().getNEWAGENTCODE());
    prpDagent.setAgentNature(res.getBODY().getPRPDAGENTINFO().getAGENTNATURE());
    prpDagent.setArticleCode(res.getBODY().getPRPDAGENTINFO().getARTICLECODE());
    return prpDagent;
  }

  public static PrpDbank getPrpDbank(String systemCode, String bankCode)
    throws Exception
  {
    GetPrpDbankService getPrpDbankService = new GetPrpDbankServiceImpl();

    GetPrpDbankReqPacket getPrpDbankReqPacket = new GetPrpDbankReqPacket();
    getPrpDbankReqPacket.getHEAD().setSYSTEMCODE(systemCode);
    getPrpDbankReqPacket.getHEAD().setREQUEST_TYPE("D34");
    getPrpDbankReqPacket.getBODY().setBANKCODE(bankCode);
    GetPrpDbankResPacket res = new GetPrpDbankResPacket();
    if (("".equals(systemCode)) || ("".equals(bankCode))) {
      throw new Exception("入参不能为空");
    }

    res = getPrpDbankService.execute(getPrpDbankReqPacket);
    if ("0".equals(res.getHEAD().getRESPONSE_CODE())) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    if ("9100".equals(res.getHEAD().getERROR_CODE())) {
      return null;
    }
    PrpDbank prpDbank = new PrpDbank();
    prpDbank.setBankCode(res.getBODY().getPRPDBANKINFO().getBANKCODE());
    prpDbank.setBankName(res.getBODY().getPRPDBANKINFO().getBANKNAME());
    prpDbank.setCustomerCode(res.getBODY().getPRPDBANKINFO().getCUSTOMERCODE());
    prpDbank.setAddressName(res.getBODY().getPRPDBANKINFO().getADDRESSNAME());
    prpDbank.setPostCode(res.getBODY().getPRPDBANKINFO().getPOSTCODE());
    prpDbank.setBankType(res.getBODY().getPRPDBANKINFO().getBANKTYPE());
    prpDbank.setLinkerName(res.getBODY().getPRPDBANKINFO().getLINKERNAME());
    prpDbank.setPhoneNumber(res.getBODY().getPRPDBANKINFO().getPHONENUMBER());
    prpDbank.setFaxNumber(res.getBODY().getPRPDBANKINFO().getFAXNUMBER());
    if ((!"".equals(res.getBODY().getPRPDBANKINFO().getARREARAGERATE())) && 
      (res.getBODY().getPRPDBANKINFO().getARREARAGERATE() != null)) {
      prpDbank.setArrearageRate(new BigDecimal(res.getBODY().getPRPDBANKINFO().getARREARAGERATE()));
    }
    if ((!"".equals(res.getBODY().getPRPDBANKINFO().getARREARAGECOFF())) && 
      (res.getBODY().getPRPDBANKINFO().getARREARAGECOFF() != null)) {
      prpDbank.setArrearageCoff(new BigDecimal(res.getBODY().getPRPDBANKINFO().getARREARAGECOFF()));
    }
    prpDbank.setComCode(res.getBODY().getPRPDBANKINFO().getCOMCODE());
    return prpDbank;
  }

  public static PrpDcode getPrpDcode(String systemCode, String codeCode, String codeType)
    throws Exception
  {
    GetPrpDcodeService getPrpDcodeService = new GetPrpDcodeServiceImpl();

    GetPrpDcodeReqPacket getPrpDcodeReqPacket = new GetPrpDcodeReqPacket();
    getPrpDcodeReqPacket.getHEAD().setSYSTEMCODE(systemCode);
    getPrpDcodeReqPacket.getHEAD().setREQUEST_TYPE("D24");
    getPrpDcodeReqPacket.getBODY().setCODECODE(codeCode);
    getPrpDcodeReqPacket.getBODY().setCODETYPE(codeType);
    GetPrpDcodeResPacket res = new GetPrpDcodeResPacket();
    if (("".equals(systemCode)) || ("".equals(codeCode)) || ("".equals(codeType))) {
      throw new Exception("未查询到相关信息！");
    }

    res = getPrpDcodeService.execute(getPrpDcodeReqPacket);
    if ("0".equals(res.getHEAD().getRESPONSE_CODE())) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    if ("9100".equals(res.getHEAD().getERROR_CODE())) {
      return null;
    }
    PrpDcode prpDcode = new PrpDcode();
    PrpDcodeId prpDcodeId = new PrpDcodeId();
    prpDcodeId.setCodeCode(res.getBODY().getPRPDCODERESINFO().getCODECODE());
    prpDcodeId.setCodeType(res.getBODY().getPRPDCODERESINFO().getCODETYPE());
    prpDcode.setId(prpDcodeId);
    prpDcode.setCodeCName(res.getBODY().getPRPDCODERESINFO().getCODECNAME());
    prpDcode.setCodeEName(res.getBODY().getPRPDCODERESINFO().getCODEENAME());
    prpDcode.setNewCodeCode(res.getBODY().getPRPDCODERESINFO().getNEWCODECODE());
    prpDcode.setValidStatus(res.getBODY().getPRPDCODERESINFO().getVALIDSTATUS());
    prpDcode.setFlag(res.getBODY().getPRPDCODERESINFO().getFLAG());
    return prpDcode;
  }

  public static PrpDcode getPrpDoldCode(String systemCode, String codeCode, String codeType)
    throws Exception
  {
    RequestPacket requestPacket = new RequestPacket();
    RequestService requestService = new RequestServiceImpl();
    requestPacket.getHEAD().setREQUEST_TYPE("D116");
    requestPacket.getHEAD().setSYSTEMCODE(systemCode);
    Map values = new HashMap();
    values.put("codeCode", (codeCode == null) ? "" : codeCode);
    values.put("codeType", (codeType == null) ? "" : codeType);
    requestPacket.getBODY().setValues(values);
    PageResPacket res = requestService.execute(requestPacket);
    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    List list = res.getBODY().getData();
    if ((list == null) || (list.size() <= 0)) {
      return null;
    }
    PrpDcodeInfo codeInfo = (PrpDcodeInfo)list.get(0);
    PrpDcode code = new PrpDcode();
    code.setId(new PrpDcodeId());
    code.getId().setCodeType(codeInfo.getId().getCodeType());
    code.getId().setCodeCode(codeInfo.getId().getCodeCode());
    code.setCodeCName(codeInfo.getCodeCName());
    code.setCodeEName(codeInfo.getCodeEName());
    code.setNewCodeCode(codeInfo.getNewCodeCode());
    code.setValidStatus(codeInfo.getValidStatus());
    code.setFlag(codeInfo.getFlag());
    return code;
  }

  public static PrpDcompany getPrpDcompany(String systemCode, String comcode)
    throws Exception
  {
    GetPrpDcompanyService getPrpDcompanyService = new GetPrpDcompanyServiceImpl();

    GetPrpDcompanyReqPacket getPrpDcompanyReqPacket = new GetPrpDcompanyReqPacket();
    getPrpDcompanyReqPacket.getHEAD().setSYSTEMCODE(systemCode);
    getPrpDcompanyReqPacket.getHEAD().setREQUEST_TYPE("D28");
    getPrpDcompanyReqPacket.getBODY().setCOMCODE(comcode);
    GetPrpDcompanyResPacket res = new GetPrpDcompanyResPacket();
    if (("".equals(systemCode)) || ("".equals(comcode))) {
      throw new Exception("未查询到相关信息！");
    }

    res = getPrpDcompanyService.execute(getPrpDcompanyReqPacket);
    if ("0".equals(res.getHEAD().getRESPONSE_CODE())) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    if ("9100".equals(res.getHEAD().getERROR_CODE())) {
      return null;
    }
    PrpDcompany prpDcompany = new PrpDcompany();
    prpDcompany.setComCode(res.getBODY().getPRPDCOMPANY().getCOMCODE());
    prpDcompany.setComCName(res.getBODY().getPRPDCOMPANY().getCOMCNAME());
    prpDcompany.setComEName(res.getBODY().getPRPDCOMPANY().getCOMENAME());
    prpDcompany.setAddressCName(res.getBODY().getPRPDCOMPANY().getADDRESSCNAME());
    prpDcompany.setAddressEName(res.getBODY().getPRPDCOMPANY().getADDRESSENAME());
    prpDcompany.setPostCode(res.getBODY().getPRPDCOMPANY().getPOSTCODE());
    prpDcompany.setPhoneNumber(res.getBODY().getPRPDCOMPANY().getPHONENUMBER());
    prpDcompany.setFaxNumber(res.getBODY().getPRPDCOMPANY().getFAXNUMBER());
    prpDcompany.setUpperComCode(res.getBODY().getPRPDCOMPANY().getUPPERCOMCODE());
    prpDcompany.setInsurerName(res.getBODY().getPRPDCOMPANY().getINSURERNAME());
    prpDcompany.setComType(res.getBODY().getPRPDCOMPANY().getCOMTYPE());
    prpDcompany.setManager(res.getBODY().getPRPDCOMPANY().getMANAGER());
    prpDcompany.setAccountant(res.getBODY().getPRPDCOMPANY().getACCOUNTANT());
    prpDcompany.setRemark(res.getBODY().getPRPDCOMPANY().getREMARK());
    prpDcompany.setNewComCode(res.getBODY().getPRPDCOMPANY().getNEWCOMCODE());
    prpDcompany.setComKind(res.getBODY().getPRPDCOMPANY().getCOMKIND());
    prpDcompany.setUpdateFlag(res.getBODY().getPRPDCOMPANY().getUPDATEFLAG());
    prpDcompany.setUpdateDate(res.getBODY().getPRPDCOMPANY().getUPDATEDATE());
    prpDcompany.setOperatorComCode(res.getBODY().getPRPDCOMPANY().getOPERATORCOMCODE());
    prpDcompany.setAcntUnit(res.getBODY().getPRPDCOMPANY().getACNTUNIT());
    prpDcompany.setArticleCode(res.getBODY().getPRPDCOMPANY().getARTICLECODE());
    prpDcompany.setComFlag(res.getBODY().getPRPDCOMPANY().getCOMFLAG());
    prpDcompany.setCenterFlag(res.getBODY().getPRPDCOMPANY().getCENTERFLAG());
    prpDcompany.setBranchType(res.getBODY().getPRPDCOMPANY().getBRANCHTYPE());
    if ((!"".equals(res.getBODY().getPRPDCOMPANY().getCOMLEVEL())) && 
      (res.getBODY().getPRPDCOMPANY().getCOMLEVEL() != null)) {
      prpDcompany.setComLevel(new BigDecimal(res.getBODY().getPRPDCOMPANY().getCOMLEVEL()));
    }
    prpDcompany.setFlag(res.getBODY().getPRPDCOMPANY().getFLAG());
    prpDcompany.setValidStatus(res.getBODY().getPRPDCOMPANY().getVALIDSTATUS());
    prpDcompany.setUpperPath(res.getBODY().getPRPDCOMPANY().getGRADE());
    return prpDcompany;
  }

  public static PrpDdealer getPrpDdealer(String systemCode, String dealerCode)
    throws Exception
  {
    GetPrpDdealerService getPrpDdealerService = new GetPrpDdealerServiceImpl();

    GetPrpDdealerReqPacket getPrpDdealerReqPacket = new GetPrpDdealerReqPacket();
    getPrpDdealerReqPacket.getHEAD().setSYSTEMCODE(systemCode);
    getPrpDdealerReqPacket.getHEAD().setREQUEST_TYPE("D36");
    getPrpDdealerReqPacket.getBODY().setDEALERCODE(dealerCode);
    GetPrpDdealerResPacket res = new GetPrpDdealerResPacket();
    if (("".equals(systemCode)) || ("".equals(dealerCode))) {
      throw new Exception("未查询到相关信息！");
    }

    res = getPrpDdealerService.execute(getPrpDdealerReqPacket);
    if ("0".equals(res.getHEAD().getRESPONSE_CODE())) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    if ("9100".equals(res.getHEAD().getERROR_CODE())) {
      return null;
    }
    PrpDdealer prpDdealer = new PrpDdealer();
    prpDdealer.setDealerCode(res.getBODY().getPRPDDEALER().getDEALERCODE());
    prpDdealer.setDealerName(res.getBODY().getPRPDDEALER().getDEALERNAME());
    prpDdealer.setCustomerCode(res.getBODY().getPRPDDEALER().getCUSTOMERCODE());
    prpDdealer.setAddressName(res.getBODY().getPRPDDEALER().getADDRESSNAME());
    prpDdealer.setPostCode(res.getBODY().getPRPDDEALER().getPOSTCODE());
    prpDdealer.setDealerType(res.getBODY().getPRPDDEALER().getDEALERTYPE());
    if ((!"".equals(res.getBODY().getPRPDDEALER().getCAPITAL())) && 
      (res.getBODY().getPRPDDEALER().getCAPITAL() != null)) {
      prpDdealer.setCapital(new BigDecimal(res.getBODY().getPRPDDEALER().getCAPITAL()));
    }
    prpDdealer.setDealerGrade(res.getBODY().getPRPDDEALER().getDEALERGRADE());
    prpDdealer.setCarType(res.getBODY().getPRPDDEALER().getCARTYPE());
    prpDdealer.setLinkerName(res.getBODY().getPRPDDEALER().getLINKERNAME());
    prpDdealer.setPhoneNumber(res.getBODY().getPRPDDEALER().getPHONENUMBER());
    prpDdealer.setFaxNumber(res.getBODY().getPRPDDEALER().getFAXNUMBER());
    if ((!"".equals(res.getBODY().getPRPDDEALER().getARREARAGERATE())) && 
      (res.getBODY().getPRPDDEALER().getARREARAGERATE() != null)) {
      prpDdealer.setArrearageRate(new BigDecimal(res.getBODY().getPRPDDEALER().getARREARAGERATE()));
    }
    if ((!"".equals(res.getBODY().getPRPDDEALER().getARREARAGECOFF())) && 
      (res.getBODY().getPRPDDEALER().getARREARAGECOFF() != null)) {
      prpDdealer.setArrearageCoff(new BigDecimal(res.getBODY().getPRPDDEALER().getARREARAGECOFF()));
    }
    prpDdealer.setComCode(res.getBODY().getPRPDDEALER().getCOMCODE());
    return prpDdealer;
  }

  public static PrpDexch getPrpDexch(String systemCode, Date exchDate, String baseCurrency, String exchCurrency)
    throws Exception
  {
    GetPrpDexchService getPrpDexchService = new GetPrpDexchServiceImpl();

    GetPrpDexchReqPacket getPrpDexchReqPacket = new GetPrpDexchReqPacket();
    getPrpDexchReqPacket.getHEAD().setSYSTEMCODE(systemCode);
    getPrpDexchReqPacket.getHEAD().setREQUEST_TYPE("D32");

    getPrpDexchReqPacket.getBODY().setEXCHDATE(PubFun.DateToStr(exchDate));
    getPrpDexchReqPacket.getBODY().setBASECURRENCY(baseCurrency);
    getPrpDexchReqPacket.getBODY().setEXCHCURRENCY(exchCurrency);
    GetPrpDexchResPacket res = new GetPrpDexchResPacket();
    if (("".equals(systemCode)) || ("".equals(baseCurrency)) || ("".equals(exchCurrency)) || (exchDate == null)) {
      throw new Exception("未查询到相关信息！");
    }

    res = getPrpDexchService.execute(getPrpDexchReqPacket);
    if ("0".equals(res.getHEAD().getRESPONSE_CODE())) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    if ("9100".equals(res.getHEAD().getERROR_CODE())) {
      return null;
    }
    PrpDexch prpDexch = new PrpDexch();
    PrpDexchId prpDexchId = new PrpDexchId();
    prpDexchId.setBaseCurrency(res.getBODY().getPRPDEXCHINFO().getBASECURRENCY());
    prpDexchId.setExchCurrency(res.getBODY().getPRPDEXCHINFO().getEXCHCURRENCY());
    if ((!"".equals(res.getBODY().getPRPDEXCHINFO().getEXCHDATE())) && 
      (res.getBODY().getPRPDEXCHINFO().getEXCHDATE() != null)) {
      prpDexchId.setExchDate(PubFun.StrToDate(res.getBODY().getPRPDEXCHINFO().getEXCHDATE()));
    }
    prpDexch.setId(prpDexchId);
    if ((!"".equals(res.getBODY().getPRPDEXCHINFO().getBASE())) && 
      (res.getBODY().getPRPDEXCHINFO().getBASE() != null)) {
      prpDexch.setBase(Integer.valueOf(Integer.parseInt(res.getBODY().getPRPDEXCHINFO().getBASE())));
    }
    if ((!"".equals(res.getBODY().getPRPDEXCHINFO().getEXCHRATE())) && 
      (res.getBODY().getPRPDEXCHINFO().getEXCHRATE() != null)) {
      prpDexch.setExchRate(new BigDecimal(res.getBODY().getPRPDEXCHINFO().getEXCHRATE()));
    }
    if ((!"".equals(res.getBODY().getPRPDEXCHINFO().getBUYPRICE())) && 
      (res.getBODY().getPRPDEXCHINFO().getBUYPRICE() != null)) {
      prpDexch.setBuyPrice(new BigDecimal(res.getBODY().getPRPDEXCHINFO().getBUYPRICE()));
    }
    if ((!"".equals(res.getBODY().getPRPDEXCHINFO().getSALEPRICE())) && 
      (res.getBODY().getPRPDEXCHINFO().getSALEPRICE() != null)) {
      prpDexch.setSalePrice(new BigDecimal(res.getBODY().getPRPDEXCHINFO().getSALEPRICE()));
    }
    if ((!"".equals(res.getBODY().getPRPDEXCHINFO().getCASHPRICE())) && 
      (res.getBODY().getPRPDEXCHINFO().getCASHPRICE() != null)) {
      prpDexch.setCashPrice(new BigDecimal(res.getBODY().getPRPDEXCHINFO().getCASHPRICE()));
    }
    return prpDexch;
  }

  public static PrpDport getPrpDport(String systemCode, String portNo)
    throws Exception
  {
    GetPrpDportService getPrpDportService = new GetPrpDportServiceImpl();

    GetPrpDportReqPacket getPrpDportReqPacket = new GetPrpDportReqPacket();
    getPrpDportReqPacket.getHEAD().setSYSTEMCODE(systemCode);
    getPrpDportReqPacket.getHEAD().setREQUEST_TYPE("D39");
    getPrpDportReqPacket.getBODY().setPORTCODE(portNo);
    GetPrpDportResPacket res = new GetPrpDportResPacket();
    if (("".equals(systemCode)) || ("".equals(portNo))) {
      throw new Exception("未查询到相关信息！");
    }

    res = getPrpDportService.execute(getPrpDportReqPacket);
    if ("0".equals(res.getHEAD().getRESPONSE_CODE())) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    if ("9100".equals(res.getHEAD().getERROR_CODE())) {
      return null;
    }
    PrpDport prpDport = new PrpDport();
    prpDport.setPortCode(res.getBODY().getPRPDPORT().getPORTCODE());
    prpDport.setPortCName(res.getBODY().getPRPDPORT().getPORTCNAME());
    prpDport.setPortEName(res.getBODY().getPRPDPORT().getPORTENAME());
    prpDport.setCountryCode(res.getBODY().getPRPDPORT().getCOUNTRYCODE());
    prpDport.setCountryCName(res.getBODY().getPRPDPORT().getCOUNTRYCNAME());
    prpDport.setCountryEName(res.getBODY().getPRPDPORT().getCOUNTRYENAME());
    prpDport.setNewPortCode(res.getBODY().getPRPDPORT().getNEWPORTCODE());
    return prpDport;
  }

  public static PrpDtype getPrpDtype(String systemCode, String codeType)
    throws Exception
  {
    GetPrpDtypeService getPrpDtypeService = new GetPrpDtypeServiceImpl();

    GetPrpDtypeReqPacket getPrpDtypeReqPacket = new GetPrpDtypeReqPacket();
    getPrpDtypeReqPacket.getHEAD().setSYSTEMCODE(systemCode);
    getPrpDtypeReqPacket.getHEAD().setREQUEST_TYPE("D23");
    getPrpDtypeReqPacket.getBODY().setCODETYPE(codeType);
    PageResPacket res = null;
    if (("".equals(systemCode)) || ("".equals(codeType))) {
      throw new Exception("未查询到相关信息！");
    }

    res = getPrpDtypeService.execute(getPrpDtypeReqPacket);
    List list = res.getBODY().getData();

    if ("0".equals(res.getHEAD().getRESPONSE_CODE())) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    if ("9100".equals(res.getHEAD().getERROR_CODE())) {
      return null;
    }
    PrpDtype prpDtype = new PrpDtype();
    if (list.size() > 0)
      prpDtype = (PrpDtype)list.get(0);
    return prpDtype;
  }

  public static List<PrpDcode> getSubCode(String systemCode, String codeType, String codeCode)
    throws Exception
  {
    GetSubCodeService getSubCodeListService = new GetSubCodeServiceImpl();

    GetSubCodeReqPacket getSubCodeReqPacket = new GetSubCodeReqPacket();
    getSubCodeReqPacket.getHEAD().setSYSTEMCODE(systemCode);
    getSubCodeReqPacket.getHEAD().setREQUEST_TYPE("D26");
    getSubCodeReqPacket.getBODY().setCODECODE(codeCode);
    getSubCodeReqPacket.getBODY().setCODETYPE(codeType);
    GetSubCodeResPacket res = new GetSubCodeResPacket();
    if (("".equals(systemCode)) || ("".equals(codeType)) || ("".equals(codeCode))) {
      throw new Exception("未查询到相关信息！");
    }

    res = getSubCodeListService.execute(getSubCodeReqPacket);
    List prpDcodeList = new ArrayList();
    if ("0".equals(res.getHEAD().getRESPONSE_CODE())) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    List list = Arrays.asList(res.getBODY().getSUBCODELIST().getSUBCODE());
    for (int i = 0; i < list.size(); ++i) {
      SubCodeResInfo subCodeResInfo = (SubCodeResInfo)list.get(i);
      PrpDcode prpDcode = new PrpDcode();
      PrpDcodeId prpDcodeId = new PrpDcodeId();
      prpDcodeId.setCodeCode(subCodeResInfo.getCODECODE());
      prpDcodeId.setCodeType(subCodeResInfo.getCODETYPE());
      prpDcode.setId(prpDcodeId);
      prpDcode.setCodeCName(subCodeResInfo.getCODECNAME());
      prpDcode.setCodeCode1(subCodeResInfo.getCODECODE1());
      prpDcode.setCodeCode2(subCodeResInfo.getCODECODE2());
      prpDcode.setCodeCode3(subCodeResInfo.getCODECODE3());
      prpDcode.setCodeCode4(subCodeResInfo.getCODECODE4());
      prpDcode.setCodeCode5(subCodeResInfo.getCODECODE5());
      prpDcode.setCodeEName(subCodeResInfo.getCODEENAME());
      prpDcode.setNewCodeCode(subCodeResInfo.getNEWCODECODE());
      prpDcodeList.add(prpDcode);
    }
    return prpDcodeList;
  }

  public static List<PrpDcompany> getSubCompany(String systemCode, String comCode)
    throws Exception
  {
    GetPrpDcompanyListService getPrpDcompanyListService = new GetPrpDcompanyListServiceImpl();

    GetPrpDcompanyReqPacket getPrpDcompanyReqPacket = new GetPrpDcompanyReqPacket();
    getPrpDcompanyReqPacket.getHEAD().setSYSTEMCODE(systemCode);
    getPrpDcompanyReqPacket.getHEAD().setREQUEST_TYPE("D30");
    getPrpDcompanyReqPacket.getBODY().setCOMCODE(comCode);
    GetPrpDcompanyListResPacket res = new GetPrpDcompanyListResPacket();
    if (("".equals(systemCode)) || ("".equals(comCode))) {
      throw new Exception("未查询到相关信息！");
    }

    res = getPrpDcompanyListService.execute(getPrpDcompanyReqPacket);
    List companyList = new ArrayList();
    if ("0".equals(res.getHEAD().getRESPONSE_CODE())) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    List list = Arrays.asList(res.getBODY().getPRPDCOMPANYLIST().getPRPDCOMPANY());
    for (int i = 0; i < list.size(); ++i) {
      PrpDcompany prpDcompany = new PrpDcompany();
      PrpDcompanyResInfo prpDcompanyResInfo = (PrpDcompanyResInfo)list.get(i);
      prpDcompany.setComCode(prpDcompanyResInfo.getCOMCODE());
      prpDcompany.setComCName(prpDcompanyResInfo.getCOMCNAME());
      prpDcompany.setComEName(prpDcompanyResInfo.getCOMENAME());
      prpDcompany.setAddressCName(prpDcompanyResInfo.getADDRESSCNAME());
      prpDcompany.setAddressEName(prpDcompanyResInfo.getADDRESSENAME());
      prpDcompany.setPostCode(prpDcompanyResInfo.getPOSTCODE());
      prpDcompany.setPhoneNumber(prpDcompanyResInfo.getPHONENUMBER());
      prpDcompany.setFaxNumber(prpDcompanyResInfo.getFAXNUMBER());
      prpDcompany.setUpperComCode(prpDcompanyResInfo.getUPPERCOMCODE());
      prpDcompany.setInsurerName(prpDcompanyResInfo.getINSURERNAME());
      prpDcompany.setComType(prpDcompanyResInfo.getCOMTYPE());
      prpDcompany.setManager(prpDcompanyResInfo.getMANAGER());
      prpDcompany.setAccountant(prpDcompanyResInfo.getACCOUNTANT());
      prpDcompany.setRemark(prpDcompanyResInfo.getREMARK());
      prpDcompany.setNewComCode(prpDcompanyResInfo.getNEWCOMCODE());
      prpDcompany.setComKind(prpDcompanyResInfo.getCOMKIND());
      prpDcompany.setUpdateFlag(prpDcompanyResInfo.getUPDATEFLAG());
      prpDcompany.setUpdateDate(prpDcompanyResInfo.getUPDATEDATE());
      prpDcompany.setOperatorComCode(prpDcompanyResInfo.getOPERATORCOMCODE());
      prpDcompany.setAcntUnit(prpDcompanyResInfo.getACNTUNIT());
      prpDcompany.setArticleCode(prpDcompanyResInfo.getARTICLECODE());
      prpDcompany.setComFlag(prpDcompanyResInfo.getCOMFLAG());
      prpDcompany.setCenterFlag(prpDcompanyResInfo.getCENTERFLAG());
      prpDcompany.setBranchType(prpDcompanyResInfo.getBRANCHTYPE());
      if ((!"".equals(prpDcompanyResInfo.getCOMLEVEL())) && (prpDcompanyResInfo.getCOMLEVEL() != null)) {
        prpDcompany.setComLevel(new BigDecimal(prpDcompanyResInfo.getCOMLEVEL()));
      }
      prpDcompany.setFlag(prpDcompanyResInfo.getFLAG());
      prpDcompany.setValidStatus(prpDcompanyResInfo.getVALIDSTATUS());
      prpDcompany.setUpperPath(prpDcompanyResInfo.getGRADE());
      companyList.add(prpDcompany);
    }
    return companyList;
  }

  public static PrpDcode getUpperCode(String systemCode, String codeType, String codeCode)
    throws Exception
  {
    GetUpperCodeService getUpperCodeService = new GetUpperCodeServiceImpl();

    GetUpperCodeReqPacket getUpperCodeReqPacket = new GetUpperCodeReqPacket();
    getUpperCodeReqPacket.getHEAD().setSYSTEMCODE(systemCode);
    getUpperCodeReqPacket.getHEAD().setREQUEST_TYPE("D25");
    getUpperCodeReqPacket.getBODY().setCODECODE(codeCode);
    getUpperCodeReqPacket.getBODY().setCODETYPE(codeType);
    GetUpperCodeResPacket res = new GetUpperCodeResPacket();
    if (("".equals(systemCode)) || ("".equals(codeType)) || ("".equals(codeCode))) {
      throw new Exception("未查询到相关信息！");
    }

    res = getUpperCodeService.execute(getUpperCodeReqPacket);
    if ("0".equals(res.getHEAD().getRESPONSE_CODE())) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    if ("9100".equals(res.getHEAD().getERROR_CODE())) {
      return null;
    }
    PrpDcode prpDcode = new PrpDcode();
    PrpDcodeId prpDcodeId = new PrpDcodeId();
    prpDcodeId.setCodeCode(res.getBODY().getUPPERCODERESINFO().getCODECODE());
    prpDcodeId.setCodeType(res.getBODY().getUPPERCODERESINFO().getCODETYPE());
    prpDcode.setId(prpDcodeId);
    prpDcode.setCodeCName(res.getBODY().getUPPERCODERESINFO().getCODECNAME());
    prpDcode.setCodeCode1(res.getBODY().getUPPERCODERESINFO().getCODECODE1());
    prpDcode.setCodeCode2(res.getBODY().getUPPERCODERESINFO().getCODECODE2());
    prpDcode.setCodeCode3(res.getBODY().getUPPERCODERESINFO().getCODECODE3());
    prpDcode.setCodeCode4(res.getBODY().getUPPERCODERESINFO().getCODECODE4());
    prpDcode.setCodeCode5(res.getBODY().getUPPERCODERESINFO().getCODECODE5());
    prpDcode.setCodeEName(res.getBODY().getUPPERCODERESINFO().getCODEENAME());
    prpDcode.setNewCodeCode(res.getBODY().getUPPERCODERESINFO().getNEWCODECODE());
    return prpDcode;
  }
  
  
  public static PrpDcompany getUpperPrpDcompany(String systemCode, String comCode)
    throws Exception
  {
    GetPrpDcompanyService getPrpDcompanyService = new GetPrpDcompanyServiceImpl();

    GetPrpDcompanyReqPacket getPrpDcompanyReqPacket = new GetPrpDcompanyReqPacket();
    getPrpDcompanyReqPacket.getHEAD().setSYSTEMCODE(systemCode);
    getPrpDcompanyReqPacket.getHEAD().setREQUEST_TYPE("D29");
    getPrpDcompanyReqPacket.getBODY().setCOMCODE(comCode);
    GetPrpDcompanyResPacket res = new GetPrpDcompanyResPacket();
    if (("".equals(systemCode)) || ("".equals(comCode))) {
      throw new Exception("未查询到相关信息！");
    }

    res = getPrpDcompanyService.execute(getPrpDcompanyReqPacket);
    if ("0".equals(res.getHEAD().getRESPONSE_CODE())) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    if ("9100".equals(res.getHEAD().getERROR_CODE())) {
      return null;
    }
    PrpDcompany prpDcompany = new PrpDcompany();
    prpDcompany.setComCode(res.getBODY().getPRPDCOMPANY().getCOMCODE());
    prpDcompany.setComCName(res.getBODY().getPRPDCOMPANY().getCOMCNAME());
    prpDcompany.setComEName(res.getBODY().getPRPDCOMPANY().getCOMENAME());
    prpDcompany.setAddressCName(res.getBODY().getPRPDCOMPANY().getADDRESSCNAME());
    prpDcompany.setAddressEName(res.getBODY().getPRPDCOMPANY().getADDRESSENAME());
    prpDcompany.setPostCode(res.getBODY().getPRPDCOMPANY().getPOSTCODE());
    prpDcompany.setPhoneNumber(res.getBODY().getPRPDCOMPANY().getPHONENUMBER());
    prpDcompany.setFaxNumber(res.getBODY().getPRPDCOMPANY().getFAXNUMBER());
    prpDcompany.setUpperComCode(res.getBODY().getPRPDCOMPANY().getUPPERCOMCODE());
    prpDcompany.setInsurerName(res.getBODY().getPRPDCOMPANY().getINSURERNAME());
    prpDcompany.setComType(res.getBODY().getPRPDCOMPANY().getCOMTYPE());
    prpDcompany.setManager(res.getBODY().getPRPDCOMPANY().getMANAGER());
    prpDcompany.setAccountant(res.getBODY().getPRPDCOMPANY().getACCOUNTANT());
    prpDcompany.setRemark(res.getBODY().getPRPDCOMPANY().getREMARK());
    prpDcompany.setNewComCode(res.getBODY().getPRPDCOMPANY().getNEWCOMCODE());
    prpDcompany.setComKind(res.getBODY().getPRPDCOMPANY().getCOMKIND());
    prpDcompany.setUpdateFlag(res.getBODY().getPRPDCOMPANY().getUPDATEFLAG());
    prpDcompany.setUpdateDate(res.getBODY().getPRPDCOMPANY().getUPDATEDATE());
    prpDcompany.setOperatorComCode(res.getBODY().getPRPDCOMPANY().getOPERATORCOMCODE());
    prpDcompany.setAcntUnit(res.getBODY().getPRPDCOMPANY().getACNTUNIT());
    prpDcompany.setArticleCode(res.getBODY().getPRPDCOMPANY().getARTICLECODE());
    prpDcompany.setComFlag(res.getBODY().getPRPDCOMPANY().getCOMFLAG());
    prpDcompany.setCenterFlag(res.getBODY().getPRPDCOMPANY().getCENTERFLAG());
    prpDcompany.setBranchType(res.getBODY().getPRPDCOMPANY().getBRANCHTYPE());
    if ((!"".equals(res.getBODY().getPRPDCOMPANY().getCOMLEVEL())) && 
      (res.getBODY().getPRPDCOMPANY().getCOMLEVEL() != null)) {
      prpDcompany.setComLevel(new BigDecimal(res.getBODY().getPRPDCOMPANY().getCOMLEVEL()));
    }
    prpDcompany.setFlag(res.getBODY().getPRPDCOMPANY().getFLAG());
    prpDcompany.setValidStatus(res.getBODY().getPRPDCOMPANY().getVALIDSTATUS());
    prpDcompany.setUpperPath(res.getBODY().getPRPDCOMPANY().getGRADE());
    return prpDcompany;
  }

  public static String translateCode(String systemCode, String codeType, String codeCode, String language)
    throws Exception
  {
    TranslateCodeService translateCodeService = new TranslateCodeServiceImpl();

    TranslateCodeReqPacket translateCodeReqPacket = new TranslateCodeReqPacket();
    RequestHeadSchema head = new RequestHeadSchema();
    TranslateCodeReqBody body = new TranslateCodeReqBody();
    head.setREQUEST_TYPE("D21");
    head.setSYSTEMCODE(systemCode);
    body.setCODECODE(codeCode);
    body.setCODETYPE(codeType);
    body.setLANGUAGE(language);
    translateCodeReqPacket.setBODY(body);
    translateCodeReqPacket.setHEAD(head);
    TranslateCodeResPacket res = new TranslateCodeResPacket();
    if (("".equals(systemCode)) || ("".equals(codeType)) || ("".equals(codeCode)) || ("".equals(language))) {
      throw new Exception("未查询到相关信息！");
    }

    res = translateCodeService.execute(translateCodeReqPacket);
    if ("0".equals(res.getHEAD().getRESPONSE_CODE())) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    String codeName = res.getBODY().getCODENAME();
    return codeName;
  }

  public static String translateCode(String systemCode, String codeType, String codeCode, String codeFlag, String language)
    throws Exception
  {
    TranslateCodeService translateCodeService = new TranslateCodeServiceImpl();

    TranslateCodeReqPacket translateCodeReqPacket = new TranslateCodeReqPacket();
    RequestHeadSchema head = new RequestHeadSchema();
    TranslateCodeReqBody body = new TranslateCodeReqBody();
    head.setREQUEST_TYPE("D21");
    head.setSYSTEMCODE(systemCode);
    body.setCODECODE(codeCode);
    body.setCODETYPE(codeType);
    body.setLANGUAGE(language);
    body.setCODEFLAG(codeFlag);
    translateCodeReqPacket.setBODY(body);
    translateCodeReqPacket.setHEAD(head);
    TranslateCodeResPacket res = new TranslateCodeResPacket();
    if (("".equals(systemCode)) || ("".equals(codeType)) || ("".equals(codeCode)) || ("".equals(language))) {
      throw new Exception("未查询到相关信息！");
    }

    res = translateCodeService.execute(translateCodeReqPacket);
    if ("0".equals(res.getHEAD().getRESPONSE_CODE())) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    String codeName = res.getBODY().getCODENAME();
    return codeName;
  }

  public static List<PrpDcompany> findCompanyByCondition(String systemcode, String condition)
    throws Exception
  {
    FindCompanyByConditionService findCompanyByConditionService = new FindCompanyByConditionServiceImpl();

    FindCompanyByConditionReqPacket findCompanyByConditionReqPacket = new FindCompanyByConditionReqPacket();
    findCompanyByConditionReqPacket.getHEAD().setSYSTEMCODE(systemcode);
    findCompanyByConditionReqPacket.getHEAD().setREQUEST_TYPE("D43");
    findCompanyByConditionReqPacket.getBODY().setCONDITION(condition);
    GetPrpDcompanyListResPacket res = new GetPrpDcompanyListResPacket();
    if ("".equals(systemcode)) {
      throw new Exception("未查询到相关信息！");
    }

    res = findCompanyByConditionService.execute(findCompanyByConditionReqPacket);
    List prpDcompanyList = new ArrayList();
    if ("0".equals(res.getHEAD().getRESPONSE_CODE())) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    List list = Arrays.asList(res.getBODY().getPRPDCOMPANYLIST().getPRPDCOMPANY());
    for (int i = 0; i < list.size(); ++i) {
      PrpDcompanyResInfo prpDcompanyResInfo = (PrpDcompanyResInfo)list.get(i);
      PrpDcompany prpDcompany = new PrpDcompany();
      prpDcompany.setComCode(prpDcompanyResInfo.getCOMCODE());
      prpDcompany.setComCName(prpDcompanyResInfo.getCOMCNAME());
      prpDcompany.setComEName(prpDcompanyResInfo.getCOMENAME());
      prpDcompany.setAddressCName(prpDcompanyResInfo.getADDRESSCNAME());
      prpDcompany.setAddressEName(prpDcompanyResInfo.getADDRESSENAME());
      prpDcompany.setPostCode(prpDcompanyResInfo.getPOSTCODE());
      prpDcompany.setPhoneNumber(prpDcompanyResInfo.getPHONENUMBER());
      prpDcompany.setFaxNumber(prpDcompanyResInfo.getFAXNUMBER());
      prpDcompany.setUpperComCode(prpDcompanyResInfo.getUPPERCOMCODE());
      prpDcompany.setInsurerName(prpDcompanyResInfo.getINSURERNAME());
      prpDcompany.setComType(prpDcompanyResInfo.getCOMTYPE());
      prpDcompany.setManager(prpDcompanyResInfo.getMANAGER());
      prpDcompany.setAccountant(prpDcompanyResInfo.getACCOUNTANT());
      prpDcompany.setRemark(prpDcompanyResInfo.getREMARK());
      prpDcompany.setNewComCode(prpDcompanyResInfo.getNEWCOMCODE());
      prpDcompany.setComKind(prpDcompanyResInfo.getCOMKIND());
      prpDcompany.setUpdateFlag(prpDcompanyResInfo.getUPDATEFLAG());
      prpDcompany.setUpdateDate(prpDcompanyResInfo.getUPDATEDATE());
      prpDcompany.setOperatorComCode(prpDcompanyResInfo.getOPERATORCOMCODE());
      prpDcompany.setAcntUnit(prpDcompanyResInfo.getACNTUNIT());
      prpDcompany.setArticleCode(prpDcompanyResInfo.getARTICLECODE());
      prpDcompany.setComFlag(prpDcompanyResInfo.getCOMFLAG());
      prpDcompany.setCenterFlag(prpDcompanyResInfo.getCENTERFLAG());
      prpDcompany.setBranchType(prpDcompanyResInfo.getBRANCHTYPE());
      if ((!"".equals(prpDcompanyResInfo.getCOMLEVEL())) && (prpDcompanyResInfo.getCOMLEVEL() != null)) {
        prpDcompany.setComLevel(new BigDecimal(prpDcompanyResInfo.getCOMLEVEL()));
      }
      prpDcompany.setFlag(prpDcompanyResInfo.getFLAG());
      prpDcompany.setValidStatus(prpDcompanyResInfo.getVALIDSTATUS());
      prpDcompany.setUpperPath(prpDcompanyResInfo.getGRADE());
      prpDcompanyList.add(prpDcompany);
    }
    return prpDcompanyList;
  }

  public static String getUrlByCode(String systemCode)
    throws Exception
  {
    RequestPacket requestPacket = new RequestPacket();
    RequestService requestService = new RequestServiceImpl();
    requestPacket.getHEAD().setREQUEST_TYPE("D94");
    String property = System.getProperty("environmenTypeCode");
    //modified by yuyiqiang 20130401
    if(("".equals(property)) || (property == null)) {
    	property = "ccicbase";
    	System.out.println("环境变量"+property);
    }
    if (("".equals(property)) || (property == null)) {
      throw new Exception("没有environmenTypeCode环境变量！");
    }
    log.debug("property is:" + property);
    requestPacket.getHEAD().setSYSTEMCODE(systemCode);
    Map values = new HashMap();

    values.put("EnvironmentCode", property);
    requestPacket.getBODY().setValues(values);
    PageResPacket res = requestService.execute(requestPacket);
    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    String url = "";
    DictPage dictPage = res.getBODY();
    if (dictPage.getData().size() > 0)
      url = (String)dictPage.getData().get(0);
    else {
      log.info("没有相应服务配置：EnvironmentCode:" + property + ",systemCode:" + systemCode);
    }
    log.debug("IpConfig url is：" + url);
    return url;
  }
  public static String getUrlByCode(String systemCode, String property) throws Exception {
    RequestPacket requestPacket = new RequestPacket();
    RequestService requestService = new RequestServiceImpl();
    requestPacket.getHEAD().setREQUEST_TYPE("D94");
    if (("".equals(property)) || (property == null)) {
      throw new Exception("请指定环境参数");
    }
    log.debug("property is:" + property);
    requestPacket.getHEAD().setSYSTEMCODE(systemCode);
    Map values = new HashMap();

    values.put("EnvironmentCode", property);
    requestPacket.getBODY().setValues(values);
    PageResPacket res = requestService.execute(requestPacket);
    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    String url = "";
    DictPage dictPage = res.getBODY();
    if (dictPage.getData().size() > 0)
      url = (String)dictPage.getData().get(0);
    else {
      log.info("没有相应服务配置：EnvironmentCode:" + property + ",systemCode:" + systemCode);
    }
    log.debug("IpConfig url is：" + url);
    return url;
  }

  public static IPServiceConfig getServiceInfoByCode(String systemCode) throws Exception {
    RequestPacket requestPacket = new RequestPacket();
    RequestService requestService = new RequestServiceImpl();
    requestPacket.getHEAD().setREQUEST_TYPE("D93");
    requestPacket.getHEAD().setSYSTEMCODE(systemCode);
    Map values = new HashMap();

    String property = System.getProperty("environmenTypeCode");
    //modified by yuyiqiang 20130401
    if(("".equals(property)) || (property == null)) {
    	property = "ccicbase";
    	System.out.println("环境变量"+property);
    }
    if (("".equals(property)) || (property == null)) {
      log.warn("没有环境变量");
      throw new Exception("没有环境变量！");
    }
    log.debug("property is:" + property);
    values.put("EnvironmentCode", property);
    requestPacket.getBODY().setValues(values);
    PageResPacket res = requestService.execute(requestPacket);
    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    IPServiceConfig config = null;
    DictPage dictPage = res.getBODY();
    if (dictPage.getData().size() > 0)
      config = (IPServiceConfig)dictPage.getData().get(0);
    else {
      log.info("没有相应服务配置：EnvironmentCode:" + property + ",systemCode:" + systemCode);
    }
    return config;
  }

  public static IPServiceConfig getServiceInfoByCode(String systemCode, String property) throws Exception {
    RequestPacket requestPacket = new RequestPacket();
    RequestService requestService = new RequestServiceImpl();
    requestPacket.getHEAD().setREQUEST_TYPE("D93");
    requestPacket.getHEAD().setSYSTEMCODE(systemCode);
    Map values = new HashMap();

    if (("".equals(property)) || (property == null)) {
      throw new Exception("请指定环境参数！");
    }
    log.debug("property is:" + property);
    values.put("EnvironmentCode", property);
    requestPacket.getBODY().setValues(values);
    PageResPacket res = requestService.execute(requestPacket);
    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    IPServiceConfig config = null;
    DictPage dictPage = res.getBODY();
    if (dictPage.getData().size() > 0)
      config = (IPServiceConfig)dictPage.getData().get(0);
    else {
      log.info("没有相应服务配置：EnvironmentCode:" + property + ",systemCode:" + systemCode);
    }
    return config;
  }

  public static List<IPServiceConfig> getServiceInfoByEnvironmentCode(String systemCode) throws Exception {
    RequestPacket requestPacket = new RequestPacket();
    RequestService requestService = new RequestServiceImpl();
    requestPacket.getHEAD().setREQUEST_TYPE("D139");
    requestPacket.getHEAD().setSYSTEMCODE(systemCode);
    Map values = new HashMap();

    String property = System.getProperty("environmenTypeCode");
    //modified by yuyiqiang 20130401
    if(("".equals(property)) || (property == null)) {
    	property = "ccicbase";
    	System.out.println("环境变量"+property);
    }
    if (("".equals(property)) || (property == null)) {
      log.warn("没有环境变量");
      throw new Exception("没有环境变量！");
    }
    log.debug("property is:" + property);
    values.put("EnvironmentCode", property);
    requestPacket.getBODY().setValues(values);
    PageResPacket res = requestService.execute(requestPacket);
    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    List list = null;
    DictPage dictPage = res.getBODY();
    if (dictPage.getData().size() > 0)
      list = dictPage.getData();
    else {
      log.info("没有相应服务配置：EnvironmentCode:" + property + ",systemCode:" + systemCode);
    }
    return list;
  }

  public static String ReverseRiskCode(String systemCode, String riskCode, String reverseType)
    throws Exception
  {
    DictPage page = PageService.getRisk(systemCode, "", riskCode, reverseType, 0, 0);
    List list = page.getData();
    String code = null;
    if ((list != null) && (list.size() > 0)) {
      PrpDrisk prpDrisk = (PrpDrisk)list.get(0);
      if (!"2".equals(reverseType))
        code = prpDrisk.getOldRiskCode();
      else {
        code = prpDrisk.getRiskCode();
      }
    }
    return code;
  }

  public static String ReverseRiskLimit(String systemCode, String riskCode, String limitCode, String reverseType)
    throws Exception
  {
    DictPage page = PageService.getRiskLimit(systemCode, riskCode, limitCode, reverseType, 1, 1);
    List list = page.getData();
    String code = "";
    if ((list != null) && (list.size() > 0)) {
      PrpDriskLimit prpDriskLimit = (PrpDriskLimit)list.get(0);
      if ("1".equals(reverseType))
        code = prpDriskLimit.getOldLimitCode();
      else {
        code = prpDriskLimit.getId().getLimitCode();
      }
    }
    return code;
  }

  public static String ReverseRiskEngage(String systemCode, String riskCode, String engageCode, String reverseType)
    throws Exception
  {
    DictPage page = PageService.getReverseRiskEngage(systemCode, riskCode, engageCode, reverseType, 1, 1);
    List list = page.getData();
    String code = "";
    if ((list != null) && (list.size() > 0)) {
      PrpDriskEngage prpDriskEngage = (PrpDriskEngage)list.get(0);
      if ("1".equals(reverseType))
        code = prpDriskEngage.getOldEngageCode();
      else {
        code = prpDriskEngage.getId().getEngageCode();
      }
    }
    return code;
  }

  public static String ReverseRiskItem(String systemCode, String riskCode, String itemCode, String reverseType)
    throws Exception
  {
    DictPage page = PageService.getRiskItem(systemCode, riskCode, itemCode, reverseType, 1, 1);
    List list = page.getData();
    String code = "";
    if ((list != null) && (list.size() > 0)) {
      PrpDriskItem prpDriskItem = (PrpDriskItem)list.get(0);
      if ("1".equals(reverseType))
        code = prpDriskItem.getOldItemCode();
      else {
        code = prpDriskItem.getId().getItemCode();
      }
    }
    return code;
  }

  public static String ReverseRiskClause(String systemCode, String riskCode, String clauseCode, String reverseType)
    throws Exception
  {
	//modified by yuyiqiang 20130401
	//DictPage page = PageService.getRiskClause(systemCode, riskCode, clauseCode, reverseType, 1, 1);
    DictPage page = PageService.getRiskClause(systemCode, riskCode, clauseCode, reverseType, 1, 1,"");
    List list = page.getData();
    String code = "";
    if ((list != null) && (list.size() > 0)) {
      PrpDriskClause prpDriskClause = (PrpDriskClause)list.get(0);
      if ("1".equals(reverseType))
        code = prpDriskClause.getOldClauseCode();
      else {
        code = prpDriskClause.getId().getClauseCode();
      }
    }

    return code;
  }

  //linzhongxia   ReverseRiskClause
  public static String getPrpDkindProduct(String systemCode, String riskCode, String kindCode, String userNature)
     throws Exception
  {
      DictPage page = PageService.getPrpDkindProduct(systemCode, riskCode, kindCode, userNature, 1, 1);
      List list = page.getData();
      String productCode = "";
      if ((list != null) && (list.size() > 0)) {
          PrpDkindProduct prpDkindProduct = (PrpDkindProduct)list.get(0);
          productCode  = prpDkindProduct.getProductCode(); 
      }      
      return productCode;
  }
  
  //add by zhongjiang begin
  public static String getAllowcarKind(String systemCode,String kindCode, String riskCode)
  throws Exception
  {
	  String allowcarkind="";
	  DictPage page=PageService.getAllowcarKind(systemCode,kindCode, riskCode, 1, 1);
	  List<PrpDkindCar> list = page.getData();
	  if(list != null && (list.size()) > 0){
		  PrpDkindCar prpDkindCar=list.get(0);
		  allowcarkind=prpDkindCar.getAllowCarKind();
		  }
	  return allowcarkind;
  }
  
  
  public static String ReverseRiskClauseKind(String systemCode, String riskCode, String clauseCode, String kindCode, String reverseType)
    throws Exception
  {
    DictPage page = PageService.getRiskClauseKind(systemCode, riskCode, clauseCode, kindCode, reverseType, "9", 1, 1);
    List list = page.getData();
    String code = "";
    if ((list != null) && (list.size() > 0)) {
      PrpDriskClauseKind prpDriskClauseKind = (PrpDriskClauseKind)list.get(0);
      if ("1".equals(reverseType))
        code = prpDriskClauseKind.getOldKindCode();
      else {
        code = prpDriskClauseKind.getKindCode();
      }
    }

    return code;
  }

  public static String ReverseClass(String systemCode, String classCode, String reverseType)
    throws Exception
  {
    DictPage page = PageService.getclass(systemCode, classCode, reverseType, "9", 0, 0);
    List list = page.getData();
    String code = "";
    if ((list != null) && (list.size() > 0)) {
      PrpDclass PrpDclass = (PrpDclass)list.get(0);
      if ("1".equals(reverseType))
        code = PrpDclass.getOldClassCode();
      else {
        code = PrpDclass.getClassCode();
      }
    }

    return code;
  }

  public static String getAccountInfo(String systemCode, String riskCode, String clauseCode, String kindCode, String accountType)
    throws Exception
  {
    String accountCode = "";
    DictPage page = PageService.getAccountInfo(systemCode, riskCode, clauseCode, kindCode, accountType, 1, 1);
    if ((page.getData() != null) && (page.getData().size() > 0)) {
      PrpDaccountInfo accountInfo = (PrpDaccountInfo)page.getData().get(0);
      accountCode = accountInfo.getAccountCode();
    }

    return accountCode;
  }

  public static List codeTransform(String systemCode, String codeType, String codeCode, String transType)
    throws Exception
  {
    CodeTransformReqPacket requestPacket = new CodeTransformReqPacket();
    CodeTransformService requestService = new CodeTransformServiceImpl();
    requestPacket.getHEAD().setREQUEST_TYPE("D107");
    requestPacket.getHEAD().setSYSTEMCODE(systemCode);

    requestPacket.getBODY().setCODETYPE(codeType);
    requestPacket.getBODY().setCODECODE(codeCode);
    requestPacket.getBODY().setTRANSTYPE(transType);

    CodeTransformResPacket res = requestService.execute(requestPacket);
    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    return res.getBODY().getCODECODE();
  }

  public static List riskTransform(String systemCode, String riskCode, String clauseCode, String kindCode, String transType)
    throws Exception
  {
    RiskTransformReqPacket requestPacket = new RiskTransformReqPacket();
    RiskTransformService requestService = new RiskTransformServiceImpl();
    requestPacket.getHEAD().setREQUEST_TYPE("D108");
    requestPacket.getHEAD().setSYSTEMCODE(systemCode);

    requestPacket.getBODY().setRISKCODE(riskCode);
    requestPacket.getBODY().setCLAUSECODE(clauseCode);
    requestPacket.getBODY().setKINDCODE(kindCode);
    requestPacket.getBODY().setTRANSTYPE(transType);

    RiskTransformResPacket res = requestService.execute(requestPacket);
    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    return res.getBODY().getCODECODE();
  }

  public static List reverseCodeTyeAndCode(String systemCode, List codeVolist, String reverseType)
    throws Exception
  {
    ReverseCodeTyeAndCodeReqPacket requestPacket = new ReverseCodeTyeAndCodeReqPacket();
    ReverseCodeTyeAndCodeService requestService = new ReverseCodeTyeAndCodeServiceImpl();
    requestPacket.getHEAD().setREQUEST_TYPE("D138");
    requestPacket.getHEAD().setSYSTEMCODE(systemCode);

    requestPacket.getBODY().setREVERSETYPE(reverseType);
    requestPacket.getBODY().setCODEVOLIST(codeVolist);
    ReverseCodeTyeAndCodeResPacket res = requestService.execute(requestPacket);
    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    return res.getBODY().getCODEVOLIST();
  }

  public static List codeTranslate(String systemCode, List<TranslateVO> list, String transType)
    throws Exception
  {
    CodeTranslateReqPacket requestPacket = new CodeTranslateReqPacket();
    CodeTranslateService requestService = new CodeTranslateServiceImpl();
    requestPacket.getHEAD().setREQUEST_TYPE("D109");
    requestPacket.getHEAD().setSYSTEMCODE(systemCode);

    requestPacket.getBODY().setTRANSLATELIST(list);
    requestPacket.getBODY().setTRANSTYPE(transType);
    CodeTranslateResPacket res = requestService.execute(requestPacket);
    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    return res.getBODY().getTRANSLATELIST();
  }

  public static String updatePrpDstatistics(String systemCode, PrpDstatistics prpDstatistics) {
    String returnMsg = null;
    UpdatePrpDstatisticsReqPacket requestPacket = new UpdatePrpDstatisticsReqPacket();
    requestPacket.getHEAD().setREQUEST_TYPE("D106");
    requestPacket.getHEAD().setSYSTEMCODE(systemCode);
    requestPacket.getBODY().setPrpDstatistics(prpDstatistics);
    UpdatePrpDstatisticsService requestService = new UpdatePrpDstatisticsServiceImpl();
    try {
      returnMsg = requestService.execute(requestPacket);
    } catch (Exception e) {
      returnMsg = "0" + e.getMessage();
      e.printStackTrace();
    }
    return returnMsg;
  }

  public static RationObj getRationInfo(String systemCode, PrpDration prpDration) throws Exception {
    GetPlanInfoReqPacket requestPacket = new GetPlanInfoReqPacket();
    GetPlanInfoService requestService = new GetPlanInfoServiceImpl();
    requestPacket.getHEAD().setREQUEST_TYPE("D117");
    requestPacket.getHEAD().setSYSTEMCODE(systemCode);
    requestPacket.getBODY().setPrpDration(prpDration);
    GetPlanInfoResPacket res = requestService.execute(requestPacket);
    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    return res.getBODY().getRationObj();
  }

  public static String getPlanWhetherHasFixed(String systemCode, String riskCode)
    throws Exception
  {
    GetPlanWhetherHasFixedReqPacket getPlanWhetherHasFixedReqPacket = new GetPlanWhetherHasFixedReqPacket();
    GetPlanWhetherHasFixedService getPlanWhetherHasFixedService = new GetPlanWhetherHasFixedServiceImpl();
    getPlanWhetherHasFixedReqPacket.getHEAD().setREQUEST_TYPE("D130");
    getPlanWhetherHasFixedReqPacket.getHEAD().setSYSTEMCODE(systemCode);
    getPlanWhetherHasFixedReqPacket.getBODY().setRiskCode(riskCode);
    GetPlanWhetherHasFixedResPacket res = new GetPlanWhetherHasFixedResPacket();
    if (("".equals(systemCode)) || ("".equals(riskCode))) {
      throw new Exception("未查询到相关信息！");
    }

    res = getPlanWhetherHasFixedService.execute(getPlanWhetherHasFixedReqPacket);
    if ("0".equals(res.getHEAD().getRESPONSE_CODE())) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    String result = "";
    result = res.getBODY().getResult();
    return result;
  }

  public static Date countWorkDay(String systemCode, Date date, int n, String flag)
    throws Exception
  {
    CountWorkDayReqPacket countWorkDayReqPacket = new CountWorkDayReqPacket();
    CountWorkDayService countWorkDayService = new CountWorkDayServiceImpl();
    countWorkDayReqPacket.getHEAD().setREQUEST_TYPE("D142");
    countWorkDayReqPacket.getHEAD().setSYSTEMCODE(systemCode);
    countWorkDayReqPacket.getBODY().setDate(date);
    countWorkDayReqPacket.getBODY().setN(n);
    countWorkDayReqPacket.getBODY().setFlag(flag);
    CountWorkDayResPacket res = new CountWorkDayResPacket();
    if (("".equals(systemCode)) || ("".equals(date)) || ("".equals(Integer.valueOf(n))) || ("".equals(flag))) {
      throw new Exception("入参不能为空！");
    }

    res = countWorkDayService.execute(countWorkDayReqPacket);
    if ("0".equals(res.getHEAD().getRESPONSE_CODE())) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    Date result = new Date();
    result = res.getBODY().getResult();
    return result;
  }
  //and by xuli 20130623  获取文案号
	public static String getPrpDkindReport(String systemCode, String riskCode,
			String kindCode,String userNature,boolean deductibleType_flag, int pageNo, int pageSize) throws Exception {
		DictPage page = PageService.getPrpDkindReport(systemCode, riskCode,
				kindCode,userNature, deductibleType_flag,pageNo, pageSize);
		List list = page.getData();
		String productCode = "";
		if ((list != null) && (list.size() > 0)) {
			PrpDkindReport prpDkindReport = (PrpDkindReport) list.get(0);
			productCode += prpDkindReport.getReportNo();
			if ((list.size() >1)) {
				PrpDkindReport prpDkindReport1 = (PrpDkindReport) list.get(1);
				productCode += "," + prpDkindReport1.getReportNo();
			}
		}
		return productCode;
	}
}
