package com.sinosoft.dmsdriver.service.server;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sinosoft.dmsdriver.domain.GetIdentityReqPacket.GetIdentityReqPacket;
import com.sinosoft.dmsdriver.domain.common.PageResPacket;
import com.sinosoft.dmsdriver.domain.common.RequestHeadSchema;
import com.sinosoft.dmsdriver.domain.common.RequestPacket;
import com.sinosoft.dmsdriver.domain.getCode.GetCodeReqPacket;
import com.sinosoft.dmsdriver.domain.getCodeWithRiskOrCom.GetCodeWithReqPacket;
import com.sinosoft.dmsdriver.domain.getCompany.GetCompanyReqPacket;
import com.sinosoft.dmsdriver.domain.getContractManageReqPacket.GetContractManageReqPacket;
import com.sinosoft.dmsdriver.domain.getIdentityDesc.GetIdentityDescReqPacket;
import com.sinosoft.dmsdriver.domain.getItem.GetItemReqPacket;
import com.sinosoft.dmsdriver.domain.getPlanReqPacket.GetPlanReqPacket;
import com.sinosoft.dmsdriver.domain.getPrpDbank.GetPrpDbankReqPacket;
import com.sinosoft.dmsdriver.domain.getPrpDcodeKind.GetPrpDcodeKindReqPacket;
import com.sinosoft.dmsdriver.domain.getPrpDcrossOrg.GetPrpDcrossOrgReqPacket;
import com.sinosoft.dmsdriver.domain.getPrpDcurrencyAndExchRate.GetPrpDcurrencyAndExchRateReqPacket;
import com.sinosoft.dmsdriver.domain.getPrpDdisaster.PrpDdisasterReqPacket;
import com.sinosoft.dmsdriver.domain.getPrpDport.GetPrpDportReqPacket;
import com.sinosoft.dmsdriver.domain.getPrpDtype.GetPrpDtypeReqPacket;
import com.sinosoft.dmsdriver.domain.getRisk.GetRiskReqPacket;
import com.sinosoft.dmsdriver.domain.getRiskEngage.GetRiskEngageReqPacket;
import com.sinosoft.dmsdriver.domain.getSimpleTreaty.GetSimpleTreatyReqPacket;
import com.sinosoft.dmsdriver.domain.getTaxAuthorities.GetTaxAuthoritiesReqPacket;
import com.sinosoft.dmsdriver.domain.getTradeCodes.GetTradeCodesReqPacket;
import com.sinosoft.dmsdriver.domain.getprojects.GetProjectsReqPacket;
import com.sinosoft.dmsdriver.domain.getprpdcurrency.GetPrpDcurrencyReqPacket;
import com.sinosoft.dmsdriver.domain.getprpdriskitem.GetPrpDriskItemReqPacket;
import com.sinosoft.dmsdriver.domain.getprpdstatistics.GetPrpDstatisticsReqPacket;
import com.sinosoft.dmsdriver.domain.getresource.GetResourceReqPacket;
import com.sinosoft.dmsdriver.domain.getshortrate.GetShortRateReqPacket;
import com.sinosoft.dmsdriver.model.PrpDclauseReport;
import com.sinosoft.dmsdriver.model.PrpDcustomerFXQ;
import com.sinosoft.dmsdriver.model.PrpDcustomerIdv;
import com.sinosoft.dmsdriver.model.PrpDcustomerUnit;
import com.sinosoft.dmsdriver.model.PrpDitemShip;
import com.sinosoft.dmsdriver.model.PrpDnewCode;
import com.sinosoft.dmsdriver.model.PrpDplane;
import com.sinosoft.dmsdriver.model.PrpDriskClauseKind;
import com.sinosoft.dmsdriver.model.PrpDriskEngage;
import com.sinosoft.dmsdriver.service.common.DictPage;
import com.sinosoft.dmsdriver.service.common.ServiceInfoConst;
import com.sinosoft.dmsdriver.service.transform.GetBankService;
import com.sinosoft.dmsdriver.service.transform.GetCodeAndNameService;
import com.sinosoft.dmsdriver.service.transform.GetCodeService;
import com.sinosoft.dmsdriver.service.transform.GetCodeWithService;
import com.sinosoft.dmsdriver.service.transform.GetCompanyService;
import com.sinosoft.dmsdriver.service.transform.GetContractManageService;
import com.sinosoft.dmsdriver.service.transform.GetIdentityDescService;
import com.sinosoft.dmsdriver.service.transform.GetIdentityService;
import com.sinosoft.dmsdriver.service.transform.GetItemService;
import com.sinosoft.dmsdriver.service.transform.GetPlanService;
import com.sinosoft.dmsdriver.service.transform.GetProjectsService;
import com.sinosoft.dmsdriver.service.transform.GetPrpDcodeKindService;
import com.sinosoft.dmsdriver.service.transform.GetPrpDcrossOrgService;
import com.sinosoft.dmsdriver.service.transform.GetPrpDcurrencyAndExchRateService;
import com.sinosoft.dmsdriver.service.transform.GetPrpDcurrencyService;
import com.sinosoft.dmsdriver.service.transform.GetPrpDdisasterService;
import com.sinosoft.dmsdriver.service.transform.GetPrpDportsService;
import com.sinosoft.dmsdriver.service.transform.GetPrpDriskItemService;
import com.sinosoft.dmsdriver.service.transform.GetPrpDstatisticsService;
import com.sinosoft.dmsdriver.service.transform.GetPrpDtypeService;
import com.sinosoft.dmsdriver.service.transform.GetResourceService;
import com.sinosoft.dmsdriver.service.transform.GetRiskEngageService;
import com.sinosoft.dmsdriver.service.transform.GetRiskService;
import com.sinosoft.dmsdriver.service.transform.GetShortRateService;
import com.sinosoft.dmsdriver.service.transform.GetSimpleTreatyService;
import com.sinosoft.dmsdriver.service.transform.GetTaxAuthoritiesService;
import com.sinosoft.dmsdriver.service.transform.GetTradeCodesService;
import com.sinosoft.dmsdriver.service.transform.RequestService;
import com.sinosoft.dmsdriver.service.transform.impl.GetBankServiceImpl;
import com.sinosoft.dmsdriver.service.transform.impl.GetCodeAndNameServiceImpl;
import com.sinosoft.dmsdriver.service.transform.impl.GetCodeServiceImpl;
import com.sinosoft.dmsdriver.service.transform.impl.GetCodeWithServiceImpl;
import com.sinosoft.dmsdriver.service.transform.impl.GetCompanyServiceImpl;
import com.sinosoft.dmsdriver.service.transform.impl.GetContractManageServiceImpl;
import com.sinosoft.dmsdriver.service.transform.impl.GetIdentityDescServiceImpl;
import com.sinosoft.dmsdriver.service.transform.impl.GetIdentityServiceImpl;
import com.sinosoft.dmsdriver.service.transform.impl.GetItemServiceImpl;
import com.sinosoft.dmsdriver.service.transform.impl.GetPlanServiceImpl;
import com.sinosoft.dmsdriver.service.transform.impl.GetProjectsServiceImpl;
import com.sinosoft.dmsdriver.service.transform.impl.GetPrpDcodeKindServiceImpl;
import com.sinosoft.dmsdriver.service.transform.impl.GetPrpDcrossOrgServiceImpl;
import com.sinosoft.dmsdriver.service.transform.impl.GetPrpDcurrencyAndExchRateServiceImpl;
import com.sinosoft.dmsdriver.service.transform.impl.GetPrpDcurrencyServiceImpl;
import com.sinosoft.dmsdriver.service.transform.impl.GetPrpDdisasterServiceImpl;
import com.sinosoft.dmsdriver.service.transform.impl.GetPrpDportsServiceImpl;
import com.sinosoft.dmsdriver.service.transform.impl.GetPrpDriskItemServiceImpl;
import com.sinosoft.dmsdriver.service.transform.impl.GetPrpDstatisticsServiceImpl;
import com.sinosoft.dmsdriver.service.transform.impl.GetPrpDtypeServiceImpl;
import com.sinosoft.dmsdriver.service.transform.impl.GetResourceServiceImpl;
import com.sinosoft.dmsdriver.service.transform.impl.GetRiskEngageServiceImpl;
import com.sinosoft.dmsdriver.service.transform.impl.GetRiskServiceImpl;
import com.sinosoft.dmsdriver.service.transform.impl.GetShortRateServiceImpl;
import com.sinosoft.dmsdriver.service.transform.impl.GetSimpleTreatyServiceImpl;
import com.sinosoft.dmsdriver.service.transform.impl.GetTaxAuthoritiesServiceImpl;
import com.sinosoft.dmsdriver.service.transform.impl.GetTradeCodesServiceImpl;
import com.sinosoft.dmsdriver.service.transform.impl.RequestServiceImpl;
import com.sinosoft.dmsdriver.util.PubFun;

public class PageService
{
  private static Log log = LogFactory.getLog(PageService.class);

  public static DictPage getCode(String systemcode, String codeType, int pageNO, int pageSize) throws Exception
  {
    if ((pageNO < 0) || (pageSize < 0)) {
      log.error("pageNO����pageSizeС��0��");
      return null; 
    }

    GetCodeReqPacket getCodeReqPacket = new GetCodeReqPacket();
    GetCodeService getCodeService = new GetCodeServiceImpl();
    getCodeReqPacket.getHEAD().setREQUEST_TYPE("D77");
    getCodeReqPacket.getHEAD().setSYSTEMCODE(systemcode);
    getCodeReqPacket.getBODY().setCodeType(codeType);
    getCodeReqPacket.getBODY().setCodeFlag("1");
    getCodeReqPacket.getBODY().getDictPage().setPageNo(pageNO);
    getCodeReqPacket.getBODY().getDictPage().setPageSize(pageSize);
    PageResPacket res = getCodeService.execute(getCodeReqPacket);
    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }

    return res.getBODY();
  }

  public static DictPage getOldCode(String systemcode, String codeType, int pageNO, int pageSize) throws Exception
  {
    if ((pageNO < 0) || (pageSize < 0)) {
      log.error("pageNO����pageSizeС��0��");
      return null;
    }
    GetCodeReqPacket getCodeReqPacket = new GetCodeReqPacket();
    GetCodeService getCodeService = new GetCodeServiceImpl();
    getCodeReqPacket.getHEAD().setREQUEST_TYPE("D77");
    getCodeReqPacket.getHEAD().setSYSTEMCODE(systemcode);
    getCodeReqPacket.getBODY().setCodeType(codeType);
    getCodeReqPacket.getBODY().setCodeFlag("2");
    getCodeReqPacket.getBODY().getDictPage().setPageNo(pageNO);
    getCodeReqPacket.getBODY().getDictPage().setPageSize(pageSize);
    PageResPacket res = getCodeService.execute(getCodeReqPacket);
    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    return res.getBODY();
  }

  public static DictPage getCodeAndName(String systemcode, String codeType, int pageNO, int pageSize) throws Exception
  {
    if ((pageNO < 0) || (pageSize < 0)) {
      log.error("pageNO����pageSizeС��0��");
      return null;
    }
    GetCodeReqPacket getCodeReqPacket = new GetCodeReqPacket();
    GetCodeAndNameService getCodeAndNameService = new GetCodeAndNameServiceImpl();
    getCodeReqPacket.getHEAD().setREQUEST_TYPE("D78");
    getCodeReqPacket.getHEAD().setSYSTEMCODE(systemcode);
    getCodeReqPacket.getBODY().setCodeType(codeType);
    getCodeReqPacket.getBODY().setCodeType("1");
    getCodeReqPacket.getBODY().getDictPage().setPageNo(pageNO);
    getCodeReqPacket.getBODY().getDictPage().setPageSize(pageSize);
    PageResPacket res = getCodeAndNameService.execute(getCodeReqPacket);
    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    return res.getBODY();
  }

  public static DictPage getOldCodeAndName(String systemcode, String codeType, int pageNO, int pageSize) throws Exception
  {
    if ((pageNO < 0) || (pageSize < 0)) {
      log.error("pageNO����pageSizeС��0��");
      return null;
    }
    GetCodeReqPacket getCodeReqPacket = new GetCodeReqPacket();
    GetCodeAndNameService getCodeAndNameService = new GetCodeAndNameServiceImpl();
    getCodeReqPacket.getHEAD().setREQUEST_TYPE("D78");
    getCodeReqPacket.getHEAD().setSYSTEMCODE(systemcode);
    getCodeReqPacket.getBODY().setCodeType(codeType);
    getCodeReqPacket.getBODY().setCodeType("2");
    getCodeReqPacket.getBODY().getDictPage().setPageNo(pageNO);
    getCodeReqPacket.getBODY().getDictPage().setPageSize(pageSize);
    PageResPacket res = getCodeAndNameService.execute(getCodeReqPacket);
    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    return res.getBODY();
  }

  public static DictPage getCompany(String systemcode, String condition, int pageNO, int pageSize) throws Exception
  {
    if ((pageNO < 0) || (pageSize < 0)) {
      log.error("pageNO����pageSizeС��0��");
      return null;
    }
    GetCompanyReqPacket getCompanyReqPacket = new GetCompanyReqPacket();
    GetCompanyService getCompanyService = new GetCompanyServiceImpl();
    getCompanyReqPacket.getHEAD().setSYSTEMCODE(systemcode);
    getCompanyReqPacket.getHEAD().setREQUEST_TYPE("D79");
    getCompanyReqPacket.getBODY().getDictPage().setPageNo(pageNO);
    getCompanyReqPacket.getBODY().getDictPage().setPageSize(pageSize);
    getCompanyReqPacket.getBODY().setCondition(condition);
    PageResPacket res = getCompanyService.execute(getCompanyReqPacket);
    if ("0".equals(res.getHEAD().getRESPONSE_CODE())) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    return res.getBODY();
  }

  public static DictPage getCodeWithRisk(String systemCode, String codeType, String codeCode, String codeCName, String riskCode, int pageNO, int pageSize)
    throws Exception
  {
    if ((pageNO < 0) || (pageSize < 0)) {
      log.error("pageNO����pageSizeС��0��");
      return null;
    }
    GetCodeWithReqPacket getCodeWithReqPacket = new GetCodeWithReqPacket();
    GetCodeWithService getCodeWithService = new GetCodeWithServiceImpl();
    getCodeWithReqPacket.getHEAD().setREQUEST_TYPE("D80");
    getCodeWithReqPacket.getHEAD().setSYSTEMCODE(systemCode);
    getCodeWithReqPacket.getBODY().setCodeType(codeType);
    getCodeWithReqPacket.getBODY().setCodeCode(codeCode);
    getCodeWithReqPacket.getBODY().setCodeCName(codeCName);
    getCodeWithReqPacket.getBODY().setWithCode(riskCode);
    getCodeWithReqPacket.getBODY().setCodeFlag("1");
    getCodeWithReqPacket.getBODY().getDictPage().setPageNo(pageNO);
    getCodeWithReqPacket.getBODY().getDictPage().setPageSize(pageSize);
    PageResPacket res = getCodeWithService.execute(getCodeWithReqPacket);
    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    return res.getBODY();
  }

  public static DictPage getSubCodeWithRisk(String systemCode, String codeType, String codeCode, String codeCName, String riskCode, String upperCode, int pageNO, int pageSize)
    throws Exception
  {
    if ((pageNO < 0) || (pageSize < 0)) {
      log.error("pageNO����pageSizeС��0��");
      return null;
    }
    GetCodeWithReqPacket getCodeWithReqPacket = new GetCodeWithReqPacket();
    GetCodeWithService getCodeWithService = new GetCodeWithServiceImpl();
    getCodeWithReqPacket.getHEAD().setREQUEST_TYPE("D147");
    getCodeWithReqPacket.getHEAD().setSYSTEMCODE(systemCode);
    getCodeWithReqPacket.getBODY().setCodeType(codeType);
    getCodeWithReqPacket.getBODY().setCodeCode(codeCode);
    getCodeWithReqPacket.getBODY().setCodeCName(codeCName);
    getCodeWithReqPacket.getBODY().setUpperCode(upperCode);
    getCodeWithReqPacket.getBODY().setWithCode(riskCode);
    getCodeWithReqPacket.getBODY().setCodeFlag("1");
    getCodeWithReqPacket.getBODY().getDictPage().setPageNo(pageNO);
    getCodeWithReqPacket.getBODY().getDictPage().setPageSize(pageSize);
    PageResPacket res = getCodeWithService.execute(getCodeWithReqPacket);
    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    return res.getBODY();
  }

  public static DictPage getCodeWithRisk(String systemCode, String codeType, String codeCode, String codeCName, String riskCode, String ignoreCode, int pageNO, int pageSize)
    throws Exception
  {
    if ((pageNO < 0) || (pageSize < 0)) {
      log.error("pageNO����pageSizeС��0��");
      return null;
    }
    GetCodeWithReqPacket getCodeWithReqPacket = new GetCodeWithReqPacket();
    GetCodeWithService getCodeWithService = new GetCodeWithServiceImpl();
    getCodeWithReqPacket.getHEAD().setREQUEST_TYPE("D80");
    getCodeWithReqPacket.getHEAD().setSYSTEMCODE(systemCode);
    getCodeWithReqPacket.getBODY().setCodeType(codeType);
    getCodeWithReqPacket.getBODY().setCodeCode(codeCode);
    getCodeWithReqPacket.getBODY().setCodeCName(codeCName);
    getCodeWithReqPacket.getBODY().setWithCode(riskCode);
    getCodeWithReqPacket.getBODY().setCodeFlag("1");
    getCodeWithReqPacket.getBODY().setIgnoreCode(ignoreCode);
    getCodeWithReqPacket.getBODY().getDictPage().setPageNo(pageNO);
    getCodeWithReqPacket.getBODY().getDictPage().setPageSize(pageSize);
    PageResPacket res = getCodeWithService.execute(getCodeWithReqPacket);
    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    return res.getBODY();
  }

  public static DictPage getCodeWithRisk(String systemCode, String codeType, String codeCode, String codeCName, String riskCode, String ignoreCode, String extraCodeCode, int pageNO, int pageSize)
    throws Exception
  {
    if ((pageNO < 0) || (pageSize < 0)) {
      log.error("pageNO����pageSizeС��0��");
      return null;
    }
    GetCodeWithReqPacket getCodeWithReqPacket = new GetCodeWithReqPacket();
    GetCodeWithService getCodeWithService = new GetCodeWithServiceImpl();
    getCodeWithReqPacket.getHEAD().setREQUEST_TYPE("D80");
    getCodeWithReqPacket.getHEAD().setSYSTEMCODE(systemCode);
    getCodeWithReqPacket.getBODY().setCodeType(codeType);
    getCodeWithReqPacket.getBODY().setCodeCode(codeCode);
    getCodeWithReqPacket.getBODY().setCodeCName(codeCName);
    getCodeWithReqPacket.getBODY().setWithCode(riskCode);
    getCodeWithReqPacket.getBODY().setCodeFlag("1");
    getCodeWithReqPacket.getBODY().setIgnoreCode(ignoreCode);
    getCodeWithReqPacket.getBODY().setExtraCodeCode(extraCodeCode);
    getCodeWithReqPacket.getBODY().getDictPage().setPageNo(pageNO);
    getCodeWithReqPacket.getBODY().getDictPage().setPageSize(pageSize);
    PageResPacket res = getCodeWithService.execute(getCodeWithReqPacket);
    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    return res.getBODY();
  }

  public static DictPage getOldCodeWithRisk(String systemCode, String codeType, String codeCode, String codeCName, String riskCode, int pageNO, int pageSize)
    throws Exception
  {
    if ((pageNO < 0) || (pageSize < 0)) {
      log.error("pageNO����pageSizeС��0��");
      return null;
    }
    GetCodeWithReqPacket getCodeWithReqPacket = new GetCodeWithReqPacket();
    GetCodeWithService getCodeWithService = new GetCodeWithServiceImpl();
    getCodeWithReqPacket.getHEAD().setREQUEST_TYPE("D80");
    getCodeWithReqPacket.getHEAD().setSYSTEMCODE(systemCode);
    getCodeWithReqPacket.getBODY().setCodeType(codeType);
    getCodeWithReqPacket.getBODY().setCodeCode(codeCode);
    getCodeWithReqPacket.getBODY().setCodeCName(codeCName);
    getCodeWithReqPacket.getBODY().setWithCode(riskCode);
    getCodeWithReqPacket.getBODY().setCodeFlag("2");
    getCodeWithReqPacket.getBODY().getDictPage().setPageNo(pageNO);
    getCodeWithReqPacket.getBODY().getDictPage().setPageSize(pageSize);
    PageResPacket res = getCodeWithService.execute(getCodeWithReqPacket);
    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    return res.getBODY();
  }

  public static DictPage getRiskEngage(String systemCode, String riskCode, String language, String clauseCode, int pageNO, int pageSize)
    throws Exception
  {
    if ((pageNO < 0) || (pageSize < 0)) {
      log.error("pageNO����pageSizeС��0��");
      return null;
    }
    GetRiskEngageReqPacket getRiskEngageReqPacket = new GetRiskEngageReqPacket();
    GetRiskEngageService getRiskEngageService = new GetRiskEngageServiceImpl();
    getRiskEngageReqPacket.getHEAD().setREQUEST_TYPE("D82");
    getRiskEngageReqPacket.getHEAD().setSYSTEMCODE(systemCode);
    getRiskEngageReqPacket.getBODY().setRiskCode(riskCode);
    getRiskEngageReqPacket.getBODY().setLanguage(language);
    getRiskEngageReqPacket.getBODY().setClauseCode(clauseCode);
    getRiskEngageReqPacket.getBODY().getDictPage().setPageNo(pageNO);
    getRiskEngageReqPacket.getBODY().getDictPage().setPageSize(pageSize);
    PageResPacket res = getRiskEngageService
      .execute(getRiskEngageReqPacket);
    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    return res.getBODY();
  }

  public static DictPage getRiskEngage(String systemCode, String riskCode, String language, int pageNO, int pageSize) throws Exception
  {
    if ((pageNO < 0) || (pageSize < 0)) {
      log.error("pageNO����pageSizeС��0��");
      return null;
    }
    GetRiskEngageReqPacket getRiskEngageReqPacket = new GetRiskEngageReqPacket();
    GetRiskEngageService getRiskEngageService = new GetRiskEngageServiceImpl();
    getRiskEngageReqPacket.getHEAD().setREQUEST_TYPE("D82");
    getRiskEngageReqPacket.getHEAD().setSYSTEMCODE(systemCode);
    getRiskEngageReqPacket.getBODY().setRiskCode(riskCode);
    getRiskEngageReqPacket.getBODY().setLanguage(language);
    getRiskEngageReqPacket.getBODY().getDictPage().setPageNo(pageNO);
    getRiskEngageReqPacket.getBODY().getDictPage().setPageSize(pageSize);
    PageResPacket res = getRiskEngageService
      .execute(getRiskEngageReqPacket);
    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    return res.getBODY();
  }

  public static DictPage getRiskEngage(String systemCode, String riskCode, String language, String clauseCode, String engageCode, int pageNO, int pageSize, String extraCondition)
    throws Exception
  {
    if ((pageNO < 0) || (pageSize < 0)) {
      log.error("pageNO����pageSizeС��0��");
      return null;
    }
    GetRiskEngageReqPacket getRiskEngageReqPacket = new GetRiskEngageReqPacket();
    GetRiskEngageService getRiskEngageService = new GetRiskEngageServiceImpl();
    getRiskEngageReqPacket.getHEAD().setREQUEST_TYPE("D82");
    getRiskEngageReqPacket.getHEAD().setSYSTEMCODE(systemCode);
    getRiskEngageReqPacket.getBODY().setRiskCode(riskCode);
    getRiskEngageReqPacket.getBODY().setLanguage(language);
    getRiskEngageReqPacket.getBODY().setClauseCode(clauseCode);
    getRiskEngageReqPacket.getBODY().setEngageCode(engageCode);

    getRiskEngageReqPacket.getBODY().getDictPage().setPageNo(pageNO);
    getRiskEngageReqPacket.getBODY().getDictPage().setPageSize(pageSize);
    PageResPacket res = getRiskEngageService
      .execute(getRiskEngageReqPacket);
    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    return res.getBODY();
  }
  
  public static DictPage getRiskEngage(String systemCode, String riskCode, String language, String clauseCode, String engageCode, int pageNO, int pageSize, String extraCondition,String initFlag )
  throws Exception
  {
		if ((pageNO < 0) || (pageSize < 0)) {
			log.error("pageNO????pageSize???0??");
			return null;
		}
		GetRiskEngageReqPacket getRiskEngageReqPacket = new GetRiskEngageReqPacket();
		GetRiskEngageService getRiskEngageService = new GetRiskEngageServiceImpl();
		getRiskEngageReqPacket.getHEAD().setREQUEST_TYPE("D82");
		getRiskEngageReqPacket.getHEAD().setSYSTEMCODE(systemCode);
		getRiskEngageReqPacket.getBODY().setRiskCode(riskCode);
		getRiskEngageReqPacket.getBODY().setLanguage(language);
		getRiskEngageReqPacket.getBODY().setClauseCode(clauseCode);
		getRiskEngageReqPacket.getBODY().setEngageCode(engageCode);
		getRiskEngageReqPacket.getBODY().setInitFlag(initFlag);
		getRiskEngageReqPacket.getBODY().getDictPage().setPageNo(pageNO);
		getRiskEngageReqPacket.getBODY().getDictPage().setPageSize(pageSize);
		PageResPacket res = getRiskEngageService
				.execute(getRiskEngageReqPacket);
		if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
			throw new Exception(res.getHEAD().getERROR_MESSAGE());
		}
		return res.getBODY();
  }

  public static DictPage getRiskEngage(String systemCode, String riskCode, String language, String clauseCode, String engageCode, String extraEngageCode, int pageNO, int pageSize)
    throws Exception
  {
    if ((pageNO < 0) || (pageSize < 0)) {
      log.error("pageNO����pageSizeС��0��");
      return null;
    }
    GetRiskEngageReqPacket getRiskEngageReqPacket = new GetRiskEngageReqPacket();
    GetRiskEngageService getRiskEngageService = new GetRiskEngageServiceImpl();
    getRiskEngageReqPacket.getHEAD().setREQUEST_TYPE("D82");
    getRiskEngageReqPacket.getHEAD().setSYSTEMCODE(systemCode);
    getRiskEngageReqPacket.getBODY().setRiskCode(riskCode);
    getRiskEngageReqPacket.getBODY().setLanguage(language);
    getRiskEngageReqPacket.getBODY().setClauseCode(clauseCode);
    getRiskEngageReqPacket.getBODY().setEngageCode(engageCode);
    getRiskEngageReqPacket.getBODY().setExtraEngageCode(extraEngageCode);
    getRiskEngageReqPacket.getBODY().getDictPage().setPageNo(pageNO);
    getRiskEngageReqPacket.getBODY().getDictPage().setPageSize(pageSize);
    PageResPacket res = getRiskEngageService
      .execute(getRiskEngageReqPacket);
    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    return res.getBODY();
  }

  public static DictPage getTaxAuthorities(String systemCode, String comCode, int pageNO, int pageSize) throws Exception
  {
    if ((pageNO < 0) || (pageSize < 0)) {
      log.error("pageNO����pageSizeС��0��");
      return null;
    }
    GetTaxAuthoritiesReqPacket getTaxAuthoritiesReqPacket = new GetTaxAuthoritiesReqPacket();
    GetTaxAuthoritiesService getTaxAuthoritiesService = new GetTaxAuthoritiesServiceImpl();
    getTaxAuthoritiesReqPacket.getHEAD().setREQUEST_TYPE("D83");
    getTaxAuthoritiesReqPacket.getHEAD().setSYSTEMCODE(systemCode);
    getTaxAuthoritiesReqPacket.getBODY().setComCode(comCode);
    getTaxAuthoritiesReqPacket.getBODY().getDictPage().setPageNo(pageNO);
    getTaxAuthoritiesReqPacket.getBODY().getDictPage()
      .setPageSize(pageSize);
    PageResPacket res = getTaxAuthoritiesService
      .execute(getTaxAuthoritiesReqPacket);
    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    return res.getBODY();
  }

  public static DictPage getrisk(String systemCode, String classCode, String riskCodeOrName, int pageNO, int pageSize) throws Exception
  {
    if ((pageNO < 0) || (pageSize < 0)) {
      log.error("pageNO����pageSizeС��0��");
      return null;
    }
    GetRiskReqPacket getRiskReqPacket = new GetRiskReqPacket();
    GetRiskService getRiskService = new GetRiskServiceImpl();
    getRiskReqPacket.getHEAD().setREQUEST_TYPE("D84");
    getRiskReqPacket.getHEAD().setSYSTEMCODE(systemCode);
    getRiskReqPacket.getBODY().setClassCode(classCode);
    getRiskReqPacket.getBODY().setRiskCode(riskCodeOrName);
    getRiskReqPacket.getBODY().getDictPage().setPageNo(pageNO);
    getRiskReqPacket.getBODY().getDictPage().setPageSize(pageSize);
    PageResPacket res = getRiskService.execute(getRiskReqPacket);
    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    return res.getBODY();
  }

  public static DictPage getRisk(String systemCode, String classCode, String riskCode, String reverseType, int pageNO, int pageSize)
    throws Exception
  {
    if ((pageNO < 0) || (pageSize < 0)) {
      log.error("pageNO����pageSizeС��0��");
      return null;
    }
    GetRiskReqPacket getRiskReqPacket = new GetRiskReqPacket();
    GetRiskService getRiskService = new GetRiskServiceImpl();
    getRiskReqPacket.getHEAD().setREQUEST_TYPE("D84");
    getRiskReqPacket.getHEAD().setSYSTEMCODE(systemCode);
    getRiskReqPacket.getBODY().setClassCode(classCode);
    getRiskReqPacket.getBODY().setRiskCode(riskCode);
    getRiskReqPacket.getBODY().setReverseType(reverseType);
    getRiskReqPacket.getBODY().getDictPage().setPageNo(pageNO);
    getRiskReqPacket.getBODY().getDictPage().setPageSize(pageSize);
    PageResPacket res = getRiskService.execute(getRiskReqPacket);
    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    return res.getBODY();
  }

  public static DictPage getclass(String systemCode, String classCode, int pageNO, int pageSize) throws Exception
  {
    if ((pageNO < 0) || (pageSize < 0)) {
      log.error("pageNO����pageSizeС��0��");
      return null;
    }
    GetRiskReqPacket getRiskReqPacket = new GetRiskReqPacket();
    GetRiskService getRiskService = new GetRiskServiceImpl();
    getRiskReqPacket.getHEAD().setREQUEST_TYPE("D85");
    getRiskReqPacket.getHEAD().setSYSTEMCODE(systemCode);
    getRiskReqPacket.getBODY().setClassCode(classCode);
    getRiskReqPacket.getBODY().getDictPage().setPageNo(pageNO);
    getRiskReqPacket.getBODY().getDictPage().setPageSize(pageSize);
    PageResPacket res = getRiskService.execute(getRiskReqPacket);
    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    return res.getBODY();
  }

  public static DictPage getclass(String systemCode, String classCode, String reverseType, String validStatus, int pageNO, int pageSize)
    throws Exception
  {
    if ((pageNO < 0) || (pageSize < 0)) {
      log.error("pageNO����pageSizeС��0��");
      return null;
    }
    GetRiskReqPacket getRiskReqPacket = new GetRiskReqPacket();
    GetRiskService getRiskService = new GetRiskServiceImpl();
    getRiskReqPacket.getHEAD().setREQUEST_TYPE("D113");
    getRiskReqPacket.getHEAD().setSYSTEMCODE(systemCode);
    getRiskReqPacket.getHEAD().setVALIDSTATUS(validStatus);
    getRiskReqPacket.getHEAD().setPAGENO(pageNO);
    getRiskReqPacket.getHEAD().setPAGESIZE(pageSize);
    getRiskReqPacket.getBODY().setClassCode(classCode);
    getRiskReqPacket.getBODY().setReverseType(reverseType);
    PageResPacket res = getRiskService.execute(getRiskReqPacket);
    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    return res.getBODY();
  }

  public static DictPage getPrpDtreatyReten(String systemCode, String uwYear, String classCode, String riskCode, int pageNO, int pageSize)
    throws Exception
  {
    if ((pageNO < 0) || (pageSize < 0)) {
      log.error("pageNO����pageSizeС��0��");
      return null;
    }
    RequestPacket requestPacket = new RequestPacket();
    RequestService requestService = new RequestServiceImpl();
    requestPacket.getHEAD().setREQUEST_TYPE("D86");
    requestPacket.getHEAD().setSYSTEMCODE(systemCode);
    Map values = new HashMap();
    values.put("uwYear", uwYear);
    values.put("classCode", classCode);
    values.put("riskCode", riskCode);
    values.put("pageNO", Integer.valueOf(pageNO));
    values.put("pageSize", Integer.valueOf(pageSize));
    requestPacket.getBODY().setValues(values);
    PageResPacket res = requestService.execute(requestPacket);
    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    return res.getBODY();
  }

  public static DictPage getAgent(String systemCode, String codeOrName, Date operateDate, String riskCode, String comCode, String channelType, int pageNO, int pageSize)
    throws Exception
  {
    if ((pageNO < 0) || (pageSize < 0)) {
      log.error("pageNO����pageSizeС��0��");
      return null;
    }
    RequestPacket requestPacket = new RequestPacket();
    RequestService requestService = new RequestServiceImpl();
    requestPacket.getHEAD().setREQUEST_TYPE("D95");
    requestPacket.getHEAD().setSYSTEMCODE(systemCode);
    Map values = new HashMap();
    values.put("codeOrName", codeOrName);
    values.put("operateDate", PubFun.DateToStr(operateDate));
    values.put("riskCode", riskCode);
    values.put("comCode", comCode);
    values.put("channelType", channelType);
    values.put("pageNO", Integer.valueOf(pageNO));
    values.put("pageSize", Integer.valueOf(pageSize));
    requestPacket.getBODY().setValues(values);
    PageResPacket res = requestService.execute(requestPacket);
    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    return res.getBODY();
  }
  public static DictPage getAgent(String systemCode, String codeOrName, Date operateDate, String riskCode, String comCode, String businessNature, String agentType, String handlerIdentifyNumber,int pageNO, int pageSize)
		    throws Exception
		  {
		    if ((pageNO < 0) || (pageSize < 0)) {
		      log.error("pageNO����pageSizeС��0��");
		      return null;
		    }
		    RequestPacket requestPacket = new RequestPacket();
		    RequestService requestService = new RequestServiceImpl();
		    requestPacket.getHEAD().setREQUEST_TYPE("D95");
		    requestPacket.getHEAD().setSYSTEMCODE(systemCode);
		    Map values = new HashMap();
		    values.put("handlerIdentifyNumber", handlerIdentifyNumber);
		    values.put("agentType", agentType);
		    
		    values.put("codeOrName", codeOrName);
		    values.put("operateDate", PubFun.DateToStr(operateDate));
		    values.put("riskCode", riskCode);
		    values.put("comCode", comCode);
		    values.put("businessNature", businessNature);
		    values.put("pageNO", Integer.valueOf(pageNO));
		    values.put("pageSize", Integer.valueOf(pageSize));
		    requestPacket.getBODY().setValues(values);
		    PageResPacket res = requestService.execute(requestPacket);
		    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
		      throw new Exception(res.getHEAD().getERROR_MESSAGE());
		    }
		    return res.getBODY();
		  }
  public static DictPage getAgent(String systemCode, String agentCode, int pageNo, int pageSize) throws Exception
  {
    if ((pageNo < 0) || (pageSize < 0)) {
      log.error("pageNO����pageSizeС��0��");
      return null;
    }
    RequestPacket requestPacket = new RequestPacket();
    RequestService requestService = new RequestServiceImpl();
    requestPacket.getHEAD().setREQUEST_TYPE("D124");
    requestPacket.getHEAD().setSYSTEMCODE(systemCode);
    requestPacket.getHEAD().setPAGENO(pageNo);
    requestPacket.getHEAD().setPAGESIZE(pageSize);
    Map values = new HashMap();
    values.put("agentCode", agentCode);
    requestPacket.getBODY().setValues(values);
    PageResPacket res = requestService.execute(requestPacket);
    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    return res.getBODY();
  }

  public static DictPage getReverseRiskEngage(String systemCode, String riskCode, String engageCode, String reverseType, int pageNO, int pageSize)
    throws Exception
  {
    if ((pageNO < 0) || (pageSize < 0)) {
      log.error("pageNO����pageSizeС��0��");
      return null;
    }
    RequestPacket requestPacket = new RequestPacket();
    RequestService requestService = new RequestServiceImpl();
    requestPacket.getHEAD().setREQUEST_TYPE("D137");
    requestPacket.getHEAD().setSYSTEMCODE(systemCode);
    Map values = new HashMap();
    values.put("riskCode", riskCode);
    values.put("engageCode", engageCode);
    values.put("reverseType", reverseType);
    values.put("pageNO", Integer.valueOf(pageNO));
    values.put("pageSize", Integer.valueOf(pageSize));
    requestPacket.getBODY().setValues(values);
    PageResPacket res = requestService.execute(requestPacket);
    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    return res.getBODY();
  }

  public static DictPage getRiskLimit(String systemCode, String riskCode, String limitCode, String reverseType, int pageNO, int pageSize)
    throws Exception
  {
    if ((pageNO < 0) || (pageSize < 0)) {
      log.error("pageNO����pageSizeС��0��");
      return null;
    }
    RequestPacket requestPacket = new RequestPacket();
    RequestService requestService = new RequestServiceImpl();
    requestPacket.getHEAD().setREQUEST_TYPE("D136");
    requestPacket.getHEAD().setSYSTEMCODE(systemCode);
    Map values = new HashMap();
    values.put("riskCode", riskCode);
    values.put("limitCode", limitCode);
    values.put("reverseType", reverseType);
    values.put("pageNO", Integer.valueOf(pageNO));
    values.put("pageSize", Integer.valueOf(pageSize));
    requestPacket.getBODY().setValues(values);
    PageResPacket res = requestService.execute(requestPacket);
    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    return res.getBODY();
  }

  public static DictPage getRiskItem(String systemCode, String riskCode, String itemCode, String reverseType, int pageNO, int pageSize)
    throws Exception
  {
    if ((pageNO < 0) || (pageSize < 0)) {
      log.error("pageNO����pageSizeС��0��");
      return null;
    }
    RequestPacket requestPacket = new RequestPacket();
    RequestService requestService = new RequestServiceImpl();
    requestPacket.getHEAD().setREQUEST_TYPE("D135");
    requestPacket.getHEAD().setSYSTEMCODE(systemCode);
    Map values = new HashMap();
    values.put("riskCode", riskCode);
    values.put("itemCode", itemCode);
    values.put("reverseType", reverseType);
    values.put("pageNO", Integer.valueOf(pageNO));
    values.put("pageSize", Integer.valueOf(pageSize));
    requestPacket.getBODY().setValues(values);
    PageResPacket res = requestService.execute(requestPacket);
    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    return res.getBODY();
  }

  public static DictPage getInfomation(String systemCode, String proviceCode, String cityCode, String countyCode, Date validDate, String fileCode, String fileName, String regulationType, int pageNo, int pageSize)
    throws Exception
  {
    if ((pageNo < 0) || (pageSize < 0)) {
      log.error("pageNO����pageSizeС��0��");
      return null;
    }
    RequestPacket requestPacket = new RequestPacket();
    RequestService requestService = new RequestServiceImpl();
    requestPacket.getHEAD().setREQUEST_TYPE("D145");
    requestPacket.getHEAD().setSYSTEMCODE(systemCode);
    Map values = new HashMap();
    values.put("proviceCode", proviceCode);
    values.put("cityCode", cityCode);
    values.put("countyCode", countyCode);
    values.put("validDate", validDate);
    values.put("fileCode", fileCode);
    values.put("fileName", fileName);
    values.put("regulationType", regulationType);
    values.put("pageNo", Integer.valueOf(pageNo));
    values.put("pageSize", Integer.valueOf(pageSize));
    requestPacket.getBODY().setValues(values);
    PageResPacket res = requestService.execute(requestPacket);
    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    return res.getBODY();
  }

  public static DictPage getRiskClause(String systemCode, String riskCode, String clauseCode, String reverseType, int pageNO, int pageSize, String comCode)
    throws Exception
  {
    if ((pageNO < 0) || (pageSize < 0)) {
      log.error("pageNO����pageSizeС��0��");
      return null;
    }
    RequestPacket requestPacket = new RequestPacket();
    RequestService requestService = new RequestServiceImpl();
    requestPacket.getHEAD().setREQUEST_TYPE(ServiceInfoConst.GETRISKCLAUSE);
    requestPacket.getHEAD().setSYSTEMCODE(systemCode);
    Map values = new HashMap();
    values.put("riskCode", riskCode);
    values.put("clauseCode", clauseCode);
    values.put("reverseType", reverseType);
    values.put("pageNO", Integer.valueOf(pageNO));
    values.put("pageSize", Integer.valueOf(pageSize));
    values.put("comCode", comCode);
    requestPacket.getBODY().setValues(values);
    PageResPacket res = requestService.execute(requestPacket);
    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    return res.getBODY();
  }
  
  public static DictPage getPrpDkindReport(String systemCode, String riskCode, String kindCode, String userNature,boolean deductibleType_flag, int pageNO, int pageSize)
  throws Exception
  {
  if ((pageNO < 0) || (pageSize < 0)) {
    log.error("pageNO����pageSizeС��0��");
    return null;
  }
  RequestPacket requestPacket = new RequestPacket();
  RequestService requestService = new RequestServiceImpl();
  requestPacket.getHEAD().setREQUEST_TYPE("D163");
  requestPacket.getHEAD().setSYSTEMCODE(systemCode);
  Map values = new HashMap();
  values.put("riskCode", riskCode);
  values.put("kindCode", kindCode);
  values.put("userNature", userNature);
  values.put("pageNO", Integer.valueOf(pageNO));
  values.put("pageSize", Integer.valueOf(pageSize));
  values.put("deductibleType_flag", deductibleType_flag);
  requestPacket.getBODY().setValues(values);
  PageResPacket res = requestService.execute(requestPacket);
  if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
    throw new Exception(res.getHEAD().getERROR_MESSAGE());
  }
  return res.getBODY();
 }   
  public static DictPage getPrpDkindProduct(String systemCode, String riskCode, String kindCode, String userNature, int pageNO, int pageSize)
  throws Exception
  {
  if ((pageNO < 0) || (pageSize < 0)) {
    log.error("pageNO����pageSizeС��0��");
    return null;
  }
  RequestPacket requestPacket = new RequestPacket();
  RequestService requestService = new RequestServiceImpl();
  requestPacket.getHEAD().setREQUEST_TYPE("D161");
  requestPacket.getHEAD().setSYSTEMCODE(systemCode);
  Map values = new HashMap();
  values.put("riskCode", riskCode);
  values.put("kindCode", kindCode);
  values.put("userNature", userNature);
  values.put("pageNO", Integer.valueOf(pageNO));
  values.put("pageSize", Integer.valueOf(pageSize));
  requestPacket.getBODY().setValues(values);
  PageResPacket res = requestService.execute(requestPacket);
  if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
    throw new Exception(res.getHEAD().getERROR_MESSAGE());
  }
  return res.getBODY();
 }  
  
  //add by zhongjiang begin
  public static DictPage getAllowcarKind(String systemCode,String kindCode, String riskCode,int pageNo, int pageSize)
  throws Exception
  {
	  if ((pageNo < 0) || (pageSize < 0)) {
		    log.error("pageNO����pageSizeС��0��");
		    return null;
		  }
	  RequestPacket requestPacket = new RequestPacket();
	  RequestService requestService = new RequestServiceImpl();
	  requestPacket.getHEAD().setREQUEST_TYPE("D162");
	  requestPacket.getHEAD().setSYSTEMCODE(systemCode);
	  Map hashMap = new HashMap();
	  hashMap.put("riskCode",riskCode);
	  hashMap.put("kindCode",kindCode);
	  hashMap.put("pageNO", Integer.valueOf(pageNo));
	  hashMap.put("pageSize", Integer.valueOf(pageSize));
	  requestPacket.getBODY().setValues(hashMap);
	  PageResPacket respacket = requestService.execute(requestPacket);
	  if(respacket.getHEAD().getRESPONSE_CODE().equals("0")){
		  throw new Exception(respacket.getHEAD().getERROR_MESSAGE());
	  }
	  return respacket.getBODY();
  	  }

  public static DictPage getRiskClause(String systemCode, String riskCode, String clauseCode, String reverseType, String queryType, String firstLevel, int pageNO, int pageSize)
    throws Exception
  {
    if ((pageNO < 0) || (pageSize < 0)) {
      log.error("pageNO����pageSizeС��0��");
      return null;
    }
    RequestPacket requestPacket = new RequestPacket();
    RequestService requestService = new RequestServiceImpl();
    requestPacket.getHEAD().setREQUEST_TYPE(ServiceInfoConst.GETRISKCLAUSE);
    requestPacket.getHEAD().setSYSTEMCODE(systemCode);
    if ((queryType == null) || ("".equals(queryType))) {
      throw new Exception("queryType��ѯ����,��β���Ϊ�գ�");
    }
    if (("1".equals(queryType)) && ((
      (firstLevel == null) || ("".equals(firstLevel))))) {
      throw new Exception("firstLevel��������,��β���Ϊ�գ�");
    }
    Map values = new HashMap();
    values.put("riskCode", riskCode);
    values.put("clauseCode", clauseCode);
    values.put("reverseType", reverseType);
    values.put("queryType", queryType);
    values.put("firstLevel", firstLevel);
    values.put("pageNO", Integer.valueOf(pageNO));
    values.put("pageSize", Integer.valueOf(pageSize));
    requestPacket.getBODY().setValues(values);
    PageResPacket res = requestService.execute(requestPacket);
    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    return res.getBODY();
  }

  public static DictPage getRiskClause(String systemCode, String riskCode, String clauseCode, String reverseType, String queryType, String firstLevel, String validStatus, int pageNO, int pageSize)
    throws Exception
  {
    if ((pageNO < 0) || (pageSize < 0)) {
      log.error("pageNO����pageSizeС��0��");
      return null;
    }
    RequestPacket requestPacket = new RequestPacket();
    RequestService requestService = new RequestServiceImpl();
    requestPacket.getHEAD().setREQUEST_TYPE(ServiceInfoConst.GETRISKCLAUSE);
    requestPacket.getHEAD().setVALIDSTATUS(validStatus);
    requestPacket.getHEAD().setSYSTEMCODE(systemCode);
    if ((queryType == null) || ("".equals(queryType))) {
      throw new Exception("queryType��ѯ����,��β���Ϊ�գ�");
    }
    if (("1".equals(queryType)) && ((
      (firstLevel == null) || ("".equals(firstLevel))))) {
      throw new Exception("firstLevel��������,��β���Ϊ�գ�");
    }
    Map values = new HashMap();
    values.put("riskCode", riskCode);
    values.put("clauseCode", clauseCode);
    values.put("reverseType", reverseType);
    values.put("queryType", queryType);
    values.put("firstLevel", firstLevel);
    values.put("pageNO", Integer.valueOf(pageNO));
    values.put("pageSize", Integer.valueOf(pageSize));
    requestPacket.getBODY().setValues(values);
    PageResPacket res = requestService.execute(requestPacket);
    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    return res.getBODY();
  }

  public static DictPage getRiskClauses(String systemCode, String riskCode, String clauseCodes, String reverseType, int pageNO, int pageSize)
    throws Exception
  {
    if ((pageNO < 0) || (pageSize < 0)) {
      log.error("pageNO����pageSizeС��0��");
      return null;
    }
    RequestPacket requestPacket = new RequestPacket();
    RequestService requestService = new RequestServiceImpl();
    requestPacket.getHEAD().setREQUEST_TYPE(ServiceInfoConst.GETRISKCLAUSE);
    requestPacket.getHEAD().setSYSTEMCODE(systemCode);
    Map values = new HashMap();
    values.put("riskCode", riskCode);
    values.put("clauseCodes", clauseCodes);
    values.put("reverseType", reverseType);
    values.put("pageNO", Integer.valueOf(pageNO));
    values.put("pageSize", Integer.valueOf(pageSize));
    requestPacket.getBODY().setValues(values);
    PageResPacket res = requestService.execute(requestPacket);
    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    return res.getBODY();
  }

  public static DictPage getRiskClauses(String systemCode, String riskCode, String clauseCodes, String reverseType, String validStatus, int pageNO, int pageSize)
    throws Exception
  {
    if ((pageNO < 0) || (pageSize < 0)) {
      log.error("pageNO����pageSizeС��0��");
      return null;
    }
    RequestPacket requestPacket = new RequestPacket();
    RequestService requestService = new RequestServiceImpl();
    requestPacket.getHEAD().setREQUEST_TYPE(ServiceInfoConst.GETRISKCLAUSE);
    requestPacket.getHEAD().setVALIDSTATUS(validStatus);
    requestPacket.getHEAD().setSYSTEMCODE(systemCode);
    Map values = new HashMap();
    values.put("riskCode", riskCode);
    values.put("clauseCodes", clauseCodes);
    values.put("reverseType", reverseType);
    values.put("pageNO", Integer.valueOf(pageNO));
    values.put("pageSize", Integer.valueOf(pageSize));
    requestPacket.getBODY().setValues(values);
    PageResPacket res = requestService.execute(requestPacket);
    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    return res.getBODY();
  }

  public static DictPage getRiskClauseKind(String systemCode, String riskCode, String clauseCode, String kindCode, String reverseType, String validStatus, int pageNO, int pageSize)
    throws Exception
  {
    if ((pageNO < 0) || (pageSize < 0)) {
      log.error("pageNO����pageSizeС��0��");
      return null;
    }
    RequestPacket requestPacket = new RequestPacket();
    RequestService requestService = new RequestServiceImpl();
    requestPacket.getHEAD().setREQUEST_TYPE("D97");
    requestPacket.getHEAD().setSYSTEMCODE(systemCode);
    requestPacket.getHEAD().setVALIDSTATUS(validStatus);
    Map values = new HashMap();
    values.put("riskCode", riskCode);
    values.put("clauseCode", clauseCode);
    values.put("kindCode", kindCode);
    values.put("reverseType", reverseType);
    values.put("pageNO", Integer.valueOf(pageNO));
    values.put("pageSize", Integer.valueOf(pageSize));
    requestPacket.getBODY().setValues(values);
    PageResPacket res = requestService.execute(requestPacket);
    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    return res.getBODY();
  }

  public static DictPage getRiskClauseKindSub(String systemCode, String riskCode, String clauseCode, String upperKindCode, String kindLevel, String validStatus, int pageNO, int pageSize)
    throws Exception
  {
    if ((pageNO < 0) || (pageSize < 0)) {
      log.error("pageNO����pageSizeС��0��");
      return null;
    }
    RequestPacket requestPacket = new RequestPacket();
    RequestService requestService = new RequestServiceImpl();
    requestPacket.getHEAD().setREQUEST_TYPE("D146");
    requestPacket.getHEAD().setSYSTEMCODE(systemCode);
    requestPacket.getHEAD().setVALIDSTATUS(validStatus);
    Map values = new HashMap();
    values.put("riskCode", riskCode);
    values.put("clauseCode", clauseCode);
    values.put("upperKindCode", upperKindCode);
    values.put("kindLevel", kindLevel);
    values.put("pageNO", Integer.valueOf(pageNO));
    values.put("pageSize", Integer.valueOf(pageSize));
    requestPacket.getBODY().setValues(values);
    PageResPacket res = requestService.execute(requestPacket);
    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    return res.getBODY();
  }

  public static DictPage getRiskClauseKindRelation(String systemCode, String riskCode, String clauseCode, String kindCode, String relationType, String relationFlag, int pageNO, int pageSize)
    throws Exception
  {
    if ((pageNO < 0) || (pageSize < 0)) {
      log.error("pageNO����pageSizeС��0��");
      return null;
    }
    RequestPacket requestPacket = new RequestPacket();
    RequestService requestService = new RequestServiceImpl();
    requestPacket.getHEAD().setREQUEST_TYPE("D150");
    requestPacket.getHEAD().setSYSTEMCODE(systemCode);
    Map values = new HashMap();
    values.put("riskCode", riskCode);
    values.put("clauseCode", clauseCode);
    values.put("kindCode", kindCode);
    values.put("relationType", relationType);
    values.put("relationFlag", relationFlag);
    values.put("pageNO", Integer.valueOf(pageNO));
    values.put("pageSize", Integer.valueOf(pageSize));
    requestPacket.getBODY().setValues(values);
    PageResPacket res = requestService.execute(requestPacket);
    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    return res.getBODY();
  }

  public static DictPage getPrpDclauseKind(String systemCode, String riskCode, String clauseCode, String kindCode, String reverseType, int pageNO, int pageSize)
    throws Exception
  {
    if ((pageNO < 0) || (pageSize < 0)) {
      log.error("pageNO����pageSizeС��0��");
      return null;
    }
    RequestPacket requestPacket = new RequestPacket();
    RequestService requestService = new RequestServiceImpl();
    requestPacket.getHEAD().setREQUEST_TYPE("D9797");
    requestPacket.getHEAD().setSYSTEMCODE(systemCode);
    Map values = new HashMap();
    values.put("riskCode", riskCode);
    values.put("clauseCode", clauseCode);
    values.put("kindCode", kindCode);
    values.put("reverseType", reverseType);
    values.put("pageNO", Integer.valueOf(pageNO));
    values.put("pageSize", Integer.valueOf(pageSize));
    requestPacket.getBODY().setValues(values);
    PageResPacket res = requestService.execute(requestPacket);
    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    return res.getBODY();
  }

  /**
   * @���� linzhongxia
   * @���� 2013-06-03
   * @param systemCode
   * @param riskCode
   * @param clauseType
   * @param kindCode
   * @param pageNo
   * @param pageSize
   * @return
   * @throws Exception
   */
  public static DictPage getPrpDclauseKind(String systemCode, String riskCode,String clauseType, String kindCode, int pageNo, int pageSize,String operate,String remark)
  throws Exception
  {
   if ((pageNo < 0) || (pageSize < 0)) { 
     log.error("pageNO����pageSizeС��0��");
     return null;
   }
   RequestPacket requestPacket = new RequestPacket();
   RequestService requestService = new RequestServiceImpl();
   requestPacket.getHEAD().setREQUEST_TYPE("D9797");
   requestPacket.getHEAD().setSYSTEMCODE(systemCode);
   Map values = new HashMap();
   values.put("pageNo", Integer.valueOf(pageNo));
   values.put("pageSize", Integer.valueOf(pageSize));
   values.put("clauseType", clauseType);
   values.put("kindCode", kindCode);
   values.put("riskCode", riskCode);
   values.put("operate", operate);
   values.put("remark", remark);
   requestPacket.getBODY().setValues(values);
   PageResPacket res = requestService.execute(requestPacket);
   if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
     throw new Exception(res.getHEAD().getERROR_MESSAGE());
   }
   return res.getBODY();
  }  
  
  public static DictPage getRiskClauseKind(String systemCode, String riskCode, String clauseCode, String kindCode, String reverseType, int pageNO, int pageSize)
    throws Exception
  {
    if ((pageNO < 0) || (pageSize < 0)) {
      log.error("pageNO����pageSizeС��0��");
      return null;
    }
    RequestPacket requestPacket = new RequestPacket();
    RequestService requestService = new RequestServiceImpl();
    requestPacket.getHEAD().setREQUEST_TYPE("D97");
    requestPacket.getHEAD().setSYSTEMCODE(systemCode);
    Map values = new HashMap();
    values.put("riskCode", riskCode);
    values.put("clauseCode", clauseCode);
    values.put("kindCode", kindCode);
    values.put("reverseType", reverseType);
    values.put("pageNO", Integer.valueOf(pageNO));
    values.put("pageSize", Integer.valueOf(pageSize));
    requestPacket.getBODY().setValues(values);
    PageResPacket res = requestService.execute(requestPacket);
    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    return res.getBODY();
  }

  public static DictPage getRiskClauseKind(String systemCode, String riskCode, String clauseCode, String kindCode, String reverseType, int pageNO, int pageSize,String ms_flag,String useNatureCode)
  throws Exception
{
  if ((pageNO < 0) || (pageSize < 0)) {
    log.error("pageNO����pageSizeС��0��");
    return null;
  }
  RequestPacket requestPacket = new RequestPacket();
  RequestService requestService = new RequestServiceImpl();
  requestPacket.getHEAD().setREQUEST_TYPE("D97");
  requestPacket.getHEAD().setSYSTEMCODE(systemCode);
  Map values = new HashMap();
  values.put("riskCode", riskCode);
  values.put("clauseCode", clauseCode);
  values.put("kindCode", kindCode);
  values.put("reverseType", reverseType);
  values.put("pageNO", Integer.valueOf(pageNO));
  values.put("pageSize", Integer.valueOf(pageSize));
  values.put("ms_flag", ms_flag); 
  //add by zhongjiang begin
  values.put("useNatureCode", useNatureCode);
  //add by zhongjiang end
  requestPacket.getBODY().setValues(values);
  PageResPacket res = requestService.execute(requestPacket);
  if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
    throw new Exception(res.getHEAD().getERROR_MESSAGE());
  }
  return res.getBODY();
}
  
  
  public static DictPage getPrpDcustomerUnit(String systemCode, int pageNO, int pageSize, String organizeCode, String customerCode,String customerCName,String unitCode)
    throws Exception 
  {
    if ((pageNO < 0) || (pageSize < 0)) {
      log.error("pageNO����pageSizeС��0��");
      throw new Exception("pageNO����pageSizeС��0��");
    }
    RequestPacket requestPacket = new RequestPacket();
    RequestService requestService = new RequestServiceImpl();
    requestPacket.getHEAD().setREQUEST_TYPE("D158");
    requestPacket.getHEAD().setSYSTEMCODE(systemCode);
    Map values = new HashMap();
    values.put("pageNO", Integer.valueOf(pageNO));
    values.put("pageSize", Integer.valueOf(pageSize));
    values.put("organizeCode", organizeCode);
    values.put("customerCode", customerCode);
    values.put("customerCName", customerCName);
    values.put("unitCode",unitCode);
    requestPacket.getBODY().setValues(values);
    PageResPacket res = requestService.execute(requestPacket);
    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    return res.getBODY();
  }
  public static DictPage getPrpDcustomerFXQ(String systemCode, int pageNO, int pageSize, String customerType, String customerCode)
		    throws Exception 
		  {
	     System.out.println("------------------����pageservice--------------");
		    if ((pageNO < 0) || (pageSize < 0)) {
		      log.error("pageNO����pageSizeС��0��");
		      throw new Exception("pageNO����pageSizeС��0��");
		    }
		    RequestPacket requestPacket = new RequestPacket();
		    RequestService requestService = new RequestServiceImpl();
		    requestPacket.getHEAD().setREQUEST_TYPE("D164");
		    requestPacket.getHEAD().setSYSTEMCODE(systemCode);
		    Map values = new HashMap();
		    values.put("pageNO", Integer.valueOf(pageNO));
		    values.put("pageSize", Integer.valueOf(pageSize));
		    values.put("customerType", customerType);
		    values.put("customerCode", customerCode);
		    requestPacket.getBODY().setValues(values);
		    PageResPacket res = requestService.execute(requestPacket);
		    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
		      throw new Exception(res.getHEAD().getERROR_MESSAGE());
		    }
		    return res.getBODY();
		  }
  public static DictPage savePrpDcustomerIdv(String systemCode, PrpDcustomerIdv type, String comCode)
    throws Exception
  {
    if (type == null) {
      log.error("PrpDcustomerIdv ����Ϊ�գ�");
      throw new Exception("PrpDcustomerIdv ����Ϊ�գ�");
    }
    RequestPacket requestPacket = new RequestPacket();
    RequestService requestService = new RequestServiceImpl();
    requestPacket.getHEAD().setREQUEST_TYPE("D159");
    requestPacket.getHEAD().setSYSTEMCODE(systemCode);
    Map values = new HashMap();
    values.put("prpDcustomerIdv", type);
    values.put("comCode", comCode);
    requestPacket.getBODY().setValues(values);
    PageResPacket res = requestService.execute(requestPacket);
    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    return res.getBODY();
  }

//add by fengyang 保存新增的船舶信息　
  public static DictPage savePrpDitemShip(String systemCode, PrpDitemShip type, String comCode)
		     throws Exception
		   {
		     if (type == null)
		     {
		       log.error("PrpDitemShip 是");
		       throw new Exception("PrpDitemShip 是");
		     }
		     RequestPacket requestPacket = new RequestPacket();
		     RequestService requestService = new RequestServiceImpl();
		     requestPacket.getHEAD().setREQUEST_TYPE("D168");
		     requestPacket.getHEAD().setSYSTEMCODE(systemCode);
		     Map values = new HashMap();
		     values.put("prpDitemShip", type);
		     values.put("comCode", comCode);
		     requestPacket.getBODY().setValues(values);
		     PageResPacket res = requestService.execute(requestPacket);
		     if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
		       throw new Exception(res.getHEAD().getERROR_MESSAGE());
		     }
		     return res.getBODY();
		   }
  
//add by fengyang 保存新增的飞机信息
//add by yjm 保存條款 20150331 start
  public static DictPage saveClauseMaintenance(String systemCode, List<PrpDriskClauseKind> prpDriskClauseKindList,String operationType)
		     throws Exception
   {
     RequestPacket requestPacket = new RequestPacket();
     RequestService requestService = new RequestServiceImpl();
     requestPacket.getHEAD().setREQUEST_TYPE("D174");
     requestPacket.getHEAD().setSYSTEMCODE(systemCode);
     Map values = new HashMap();
     values.put("prpDriskClauseKindList", prpDriskClauseKindList);
     values.put("operationType", operationType);
     requestPacket.getBODY().setValues(values);
     PageResPacket res = requestService.execute(requestPacket);
     if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
       throw new Exception(res.getHEAD().getERROR_MESSAGE());
     }
     return res.getBODY();
   }
//add by yjm 保存條款 20150331 end
//add by yjm 保存特約及附加條款 20150331 start
  public static DictPage saveEngageMaintenance(String systemCode, List<PrpDriskEngage> prpDriskEngageList,String operationType)
		     throws Exception
   {
     RequestPacket requestPacket = new RequestPacket();
     RequestService requestService = new RequestServiceImpl();
     requestPacket.getHEAD().setREQUEST_TYPE("D173");
     requestPacket.getHEAD().setSYSTEMCODE(systemCode);
     Map values = new HashMap();
     values.put("prpDriskEngageList", prpDriskEngageList);
     values.put("operationType", operationType);
     requestPacket.getBODY().setValues(values);
     PageResPacket res = requestService.execute(requestPacket);
     if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
       throw new Exception(res.getHEAD().getERROR_MESSAGE());
     }
     return res.getBODY();
   }
//add by yjm 保存特約及附加條款 20150331 end
//add by mjx 保存新增的文案号 20150225
  public static DictPage saveCopyNumber(String systemCode, List<PrpDclauseReport> prpDclauseReportList, PrpDclauseReport prpDclauseReportParam,String operationType)
		     throws Exception
		   {
		     RequestPacket requestPacket = new RequestPacket();
		     RequestService requestService = new RequestServiceImpl();
		     requestPacket.getHEAD().setREQUEST_TYPE("D171");
		     requestPacket.getHEAD().setSYSTEMCODE(systemCode);
		     Map values = new HashMap();
		     values.put("prpDclauseReportList", prpDclauseReportList);
		     values.put("operationType", operationType);
		     values.put("prpDclauseReportParam", prpDclauseReportParam);
		     requestPacket.getBODY().setValues(values);
		     PageResPacket res = requestService.execute(requestPacket);
		     if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
		       throw new Exception(res.getHEAD().getERROR_MESSAGE());
		     }
		     return res.getBODY();
		   }
//add by mjx 保存新增的文案号 20150225 end
//add by  mjx 保存职业类别  20150302  start
  public static DictPage saveOrUpdateOccupation(String systemCode, List<PrpDnewCode> prpDnewCodes)
		     throws Exception
		   {
		     RequestPacket requestPacket = new RequestPacket();
		     RequestService requestService = new RequestServiceImpl();
		     requestPacket.getHEAD().setREQUEST_TYPE("D172");
		     requestPacket.getHEAD().setSYSTEMCODE(systemCode);
		     Map values = new HashMap();
		     values.put("prpDnewCodes", prpDnewCodes);
		     requestPacket.getBODY().setValues(values);
		     PageResPacket res = requestService.execute(requestPacket);
		     if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
		       throw new Exception(res.getHEAD().getERROR_MESSAGE());
		     }
		     return res.getBODY();
		   }
//add by  mjx 保存职业类别  20150302  end
  
//add by fengyang 保存新增的飞机信息　
  public static DictPage savePrpDplane(String systemCode, PrpDplane type, String comCode)
		     throws Exception
		   {
		     if (type == null)
		     {
		       log.error("PrpDplane 是");
		       throw new Exception("PrpDplane 是");
		     }
		     RequestPacket requestPacket = new RequestPacket();
		     RequestService requestService = new RequestServiceImpl();
		     requestPacket.getHEAD().setREQUEST_TYPE("D170");
		     requestPacket.getHEAD().setSYSTEMCODE(systemCode);
		     Map values = new HashMap();
		     values.put("prpDplane", type);
		     values.put("comCode", comCode);
		     requestPacket.getBODY().setValues(values);
		     PageResPacket res = requestService.execute(requestPacket);
		     if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
		       throw new Exception(res.getHEAD().getERROR_MESSAGE());
		     }
		     return res.getBODY();
		   }
  
  public static DictPage savePrpDcustomerUnit(String systemCode, PrpDcustomerUnit type, PrpDcustomerFXQ typetwo, String comCode) throws Exception
  {
    if (type == null) {
      log.error("PrpDcustomerUnit ����Ϊ�գ�");
      throw new Exception("PrpDcustomerUnit ����Ϊ�գ�");
    }
    RequestPacket requestPacket = new RequestPacket();
    RequestService requestService = new RequestServiceImpl();
    requestPacket.getHEAD().setREQUEST_TYPE("D160");
    requestPacket.getHEAD().setSYSTEMCODE(systemCode);
    Map values = new HashMap();
    values.put("prpDcustomerUnit", type);
    values.put("prpDcustomerFXQ", typetwo);
    values.put("comCode", comCode);
    requestPacket.getBODY().setValues(values);
    PageResPacket res = requestService.execute(requestPacket);
    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    return res.getBODY();
  }

  public static DictPage getPrpDcustomerIdv(String systemCode, String identifyType, int pageNO, int pageSize, String identifyNumber, String customerCName, String customerCode)
    throws Exception
  {
    if ((pageNO < 0) || (pageSize < 0)) {
      log.error("pageNO����pageSizeС��0��");
      throw new Exception("pageNO����pageSizeС��0��");
    }
    RequestPacket requestPacket = new RequestPacket();
    RequestService requestService = new RequestServiceImpl();
    requestPacket.getHEAD().setREQUEST_TYPE("D157");
    requestPacket.getHEAD().setSYSTEMCODE(systemCode);
    Map values = new HashMap();
    values.put("pageNO", Integer.valueOf(pageNO));
    values.put("pageSize", Integer.valueOf(pageSize));
    values.put("identifyType", identifyType);
    values.put("identifyNumber", identifyNumber);
    values.put("customerCName", customerCName);
    values.put("customerCode", customerCode);
    requestPacket.getBODY().setValues(values);
    PageResPacket res = requestService.execute(requestPacket);
    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    return res.getBODY();
  }
  //查询船舶信息add by fengyang 
  public static DictPage getPrpDship(String systemCode, int pageNO, int pageSize, String shipCode, String shipNo, String shipCName, String shipEName)
		     throws Exception
		   {
		     if ((pageNO < 0) || (pageSize < 0))
		     {
		       log.error("pageNO是");
		       throw new Exception("pageNO是");
		     }
		     RequestPacket requestPacket = new RequestPacket();
		     RequestService requestService = new RequestServiceImpl();
		     requestPacket.getHEAD().setREQUEST_TYPE("D167");
		     requestPacket.getHEAD().setSYSTEMCODE(systemCode);
		     Map values = new HashMap();
		     values.put("pageNO", Integer.valueOf(pageNO));
		     values.put("pageSize", Integer.valueOf(pageSize));
		     values.put("shipCode", shipCode);
		     values.put("shipNo", shipNo);
		     values.put("shipCName", shipCName);
		     values.put("shipEName", shipEName);
		     requestPacket.getBODY().setValues(values);
		     PageResPacket res = requestService.execute(requestPacket);
		     if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
		       throw new Exception(res.getHEAD().getERROR_MESSAGE());
		     }
		     return res.getBODY();
		   }
  
  //查询飞机信息add by fengyang 
  public static DictPage getPrpDplane(String systemCode, int pageNO, int pageSize, String planeType, String registrationMarks, String labelNo, String licenseNo)
		     throws Exception
		   {
		     if ((pageNO < 0) || (pageSize < 0))
		     {
		       log.error("pageNO是");
		       throw new Exception("pageNO是");
		     }
		     RequestPacket requestPacket = new RequestPacket();
		     RequestService requestService = new RequestServiceImpl();
		     requestPacket.getHEAD().setREQUEST_TYPE("D169");
		     requestPacket.getHEAD().setSYSTEMCODE(systemCode);
		     Map values = new HashMap();
		     values.put("pageNO", Integer.valueOf(pageNO));
		     values.put("pageSize", Integer.valueOf(pageSize));
		     values.put("planeType", planeType);
		     values.put("registrationMarks", registrationMarks);
		     values.put("labelNo", labelNo);
		     values.put("licenseNo", licenseNo);
		     requestPacket.getBODY().setValues(values);
		     PageResPacket res = requestService.execute(requestPacket);
		     if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
		       throw new Exception(res.getHEAD().getERROR_MESSAGE());
		     }
		     return res.getBODY();
		   }
  public static DictPage getPrpDkind(String systemCode, String riskCode, String kindCode, int pageNO, int pageSize, String ms_Flag)
    throws Exception
  {
    if ((pageNO < 0) || (pageSize < 0)) {
      log.error("pageNO����pageSizeС��0��");
      throw new Exception("pageNO����pageSizeС��0��");
    }
    RequestPacket requestPacket = new RequestPacket();
    RequestService requestService = new RequestServiceImpl();
    requestPacket.getHEAD().setREQUEST_TYPE("D114");
    requestPacket.getHEAD().setSYSTEMCODE(systemCode);
    Map values = new HashMap();
    values.put("riskCode", riskCode);
    values.put("kindCode", kindCode);
    values.put("pageNO", Integer.valueOf(pageNO));
    values.put("pageSize", Integer.valueOf(pageSize));
    if ((ms_Flag != null) && (!("".equals(ms_Flag)))) {
      values.put("flag", ms_Flag);
    }

    requestPacket.getBODY().setValues(values);
    PageResPacket res = requestService.execute(requestPacket);
    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    return res.getBODY();
  }

  public static DictPage getPrpDlimit(String systemCode, String riskCode, String limitCode, String kindCode, String itemCode, int pageNo, int pageSize)
    throws Exception
  {
    if ((pageNo < 0) || (pageSize < 0)) {
      log.error("pageNO����pageSizeС��0��");
      throw new Exception("pageNO����pageSizeС��0��");
    }
    RequestPacket requestPacket = new RequestPacket();
    RequestService requestService = new RequestServiceImpl();
    requestPacket.getHEAD().setREQUEST_TYPE("D115");
    requestPacket.getHEAD().setSYSTEMCODE(systemCode);
    Map values = new HashMap();
    values.put("riskCode", riskCode);
    values.put("kindCode", kindCode);
    values.put("pageNo", Integer.valueOf(pageNo));
    values.put("pageSize", Integer.valueOf(pageSize));
    requestPacket.getBODY().setValues(values);
    PageResPacket res = requestService.execute(requestPacket);
    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    return res.getBODY();
  }

  public static DictPage getAccountInfo(String systemCode, String riskCode, String clauseCode, String kindCode, String accountType, int pageNO, int pageSize)
    throws Exception
  {
    if ((pageNO < 0) || (pageSize < 0)) {
      log.error("pageNO����pageSizeС��0��");
      return null;
    }
    RequestPacket requestPacket = new RequestPacket();
    RequestService requestService = new RequestServiceImpl();
    requestPacket.getHEAD().setREQUEST_TYPE("D98");
    requestPacket.getHEAD().setSYSTEMCODE(systemCode);
    Map values = new HashMap();
    values.put("riskCode", riskCode);
    values.put("clauseCode", clauseCode);
    values.put("kindCode", kindCode);
    values.put("accountType", accountType);
    values.put("pageNO", Integer.valueOf(pageNO));
    values.put("pageSize", Integer.valueOf(pageSize));
    requestPacket.getBODY().setValues(values);
    PageResPacket res = requestService.execute(requestPacket);
    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    return res.getBODY();
  }

  public static DictPage getReinsurer(String systemCode, String codeOrName, int pageNo, int pageSize) throws Exception
  {
    if ((pageNo < 0) || (pageSize < 0)) {
      log.error("pageNO����pageSizeС��0��");
      return null;
    }
    RequestPacket requestPacket = new RequestPacket();
    RequestService requestService = new RequestServiceImpl();
    requestPacket.getHEAD().setREQUEST_TYPE("D99");
    requestPacket.getHEAD().setSYSTEMCODE(systemCode);
    Map values = new HashMap();
    values.put("pageNo", Integer.valueOf(pageNo));
    values.put("pageSize", Integer.valueOf(pageSize));
    values.put("codeOrName", (codeOrName == null) ? "" : codeOrName);
    requestPacket.getBODY().setValues(values);
    PageResPacket res = requestService.execute(requestPacket);
    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    return res.getBODY();
  }

  public static DictPage getCoins(String systemCode, String makeCom, String codeOrName, int pageNo, int pageSize) throws Exception
  {
    if ((pageNo < 0) || (pageSize < 0)) {
      log.error("pageNO����pageSizeС��0��");
      return null;
    }
    RequestPacket requestPacket = new RequestPacket();
    RequestService requestService = new RequestServiceImpl();
    requestPacket.getHEAD().setREQUEST_TYPE("D100");
    requestPacket.getHEAD().setSYSTEMCODE(systemCode);
    Map values = new HashMap();
    values.put("pageNo", Integer.valueOf(pageNo));
    values.put("pageSize", Integer.valueOf(pageSize));
    values.put("makeCom", makeCom);
    values.put("codeOrName", (codeOrName == null) ? "" : codeOrName);
    requestPacket.getBODY().setValues(values);
    PageResPacket res = requestService.execute(requestPacket);
    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    return res.getBODY();
  }

  public static DictPage getPrpDriskLimit(String systemCode, String riskCode, String limitCode, String clauseCode, String kindCode, String itemCode, String limitLevel, int pageNo, int pageSize)
    throws Exception
  {
    if ((pageNo < 0) || (pageSize < 0)) {
      log.error("pageNO����pageSizeС��0��");
      return null;
    }
    RequestPacket requestPacket = new RequestPacket();
    RequestService requestService = new RequestServiceImpl();
    requestPacket.getHEAD().setREQUEST_TYPE("D101");
    requestPacket.getHEAD().setSYSTEMCODE(systemCode);

    Map values = new HashMap();
    values.put("pageNo", Integer.valueOf(pageNo));
    values.put("pageSize", Integer.valueOf(pageSize));
    values.put("riskCode", (riskCode == null) ? "" : riskCode);
    values.put("limitCode", (limitCode == null) ? "" : limitCode);
    values.put("clauseCode", (clauseCode == null) ? "" : clauseCode);
    values.put("kindCode", (kindCode == null) ? "" : kindCode);
    values.put("itemCode", (itemCode == null) ? "" : itemCode);
    values.put("limitLevel", (limitLevel == null) ? "" : limitLevel);
    requestPacket.getBODY().setValues(values);
    PageResPacket res = requestService.execute(requestPacket);
    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    return res.getBODY();
  }

  public static DictPage getPrpDriskLimit(String systemCode, String riskCode, String limitCode, String clauseCode, String kindCode, String itemCode, String limitLevel, String isRecorded, int pageNo, int pageSize)
    throws Exception
  {
    if ((pageNo < 0) || (pageSize < 0)) {
      log.error("pageNO����pageSizeС��0��");
      return null;
    }
    RequestPacket requestPacket = new RequestPacket();
    RequestService requestService = new RequestServiceImpl();
    requestPacket.getHEAD().setREQUEST_TYPE("D101");
    requestPacket.getHEAD().setSYSTEMCODE(systemCode);

    Map values = new HashMap();
    values.put("pageNo", Integer.valueOf(pageNo));
    values.put("pageSize", Integer.valueOf(pageSize));
    values.put("riskCode", (riskCode == null) ? "" : riskCode);
    values.put("limitCode", (limitCode == null) ? "" : limitCode);
    values.put("clauseCode", (clauseCode == null) ? "" : clauseCode);
    values.put("kindCode", (kindCode == null) ? "" : kindCode);
    values.put("itemCode", (itemCode == null) ? "" : itemCode);
    values.put("limitLevel", (limitLevel == null) ? "" : limitLevel);
    values.put("isRecorded", (isRecorded == null) ? "" : isRecorded);
    requestPacket.getBODY().setValues(values);
    PageResPacket res = requestService.execute(requestPacket);
    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    return res.getBODY();
  }  
  
  
  

  public static DictPage getPrpDriskLimit(String systemCode, String riskCode, String limitCode, String clauseCode, String kindCode, String itemCode, String limitLevel, String isRecorded, String extraLimitCode, int pageNo, int pageSize)
    throws Exception
  {
    if ((pageNo < 0) || (pageSize < 0)) {
      log.error("pageNO����pageSizeС��0��");
      return null;
    }
    RequestPacket requestPacket = new RequestPacket();
    RequestService requestService = new RequestServiceImpl();
    requestPacket.getHEAD().setREQUEST_TYPE("D101");
    requestPacket.getHEAD().setSYSTEMCODE(systemCode);

    Map values = new HashMap();
    values.put("pageNo", Integer.valueOf(pageNo));
    values.put("pageSize", Integer.valueOf(pageSize));
    values.put("riskCode", (riskCode == null) ? "" : riskCode);
    values.put("limitCode", (limitCode == null) ? "" : limitCode);
    values.put("clauseCode", (clauseCode == null) ? "" : clauseCode);
    values.put("kindCode", (kindCode == null) ? "" : kindCode);
    values.put("itemCode", (itemCode == null) ? "" : itemCode);
    values.put("limitLevel", (limitLevel == null) ? "" : limitLevel);
    values.put("isRecorded", (isRecorded == null) ? "" : isRecorded);
    values.put("extraLimitCode", (extraLimitCode == null) ? "" : 
      extraLimitCode);
    requestPacket.getBODY().setValues(values);
    PageResPacket res = requestService.execute(requestPacket);
    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    return res.getBODY();
  }
  
  public static DictPage getPrpDriskLimit(String systemCode, String riskCode, String limitCode, String clauseCode, String kindCode, String itemCode, String limitLevel, String isRecorded, String extraLimitCode, int pageNo, int pageSize , String valueFlag)
  throws Exception 
{
  if ((pageNo < 0) || (pageSize < 0)) {
    log.error("pageNO����pageSizeС��0��");
    return null;
  }
  RequestPacket requestPacket = new RequestPacket();
  RequestService requestService = new RequestServiceImpl();
  requestPacket.getHEAD().setREQUEST_TYPE("D101");
  requestPacket.getHEAD().setSYSTEMCODE(systemCode);

  Map values = new HashMap();
  values.put("pageNo", Integer.valueOf(pageNo));
  values.put("pageSize", Integer.valueOf(pageSize));
  values.put("riskCode", riskCode == null ? "" : riskCode);
  values.put("limitCode", limitCode == null ? "" : limitCode);
  values.put("clauseCode", clauseCode == null ? "" : clauseCode);
  values.put("kindCode", kindCode == null ? "" : kindCode);
  values.put("itemCode", itemCode == null ? "" : itemCode);
  values.put("limitLevel", limitLevel == null ? "" : limitLevel);
  values.put("isRecorded", isRecorded == null ? "" : isRecorded);
  values.put("extraLimitCode", extraLimitCode == null ? "" : 
    extraLimitCode);
  values.put("valueFlag", valueFlag == null ? "" : valueFlag);
  requestPacket.getBODY().setValues(values);
  PageResPacket res = requestService.execute(requestPacket);
  if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
    throw new Exception(res.getHEAD().getERROR_MESSAGE());
  }
  return res.getBODY();
}

  public static DictPage getPrpDsettlementLkr(String systemCode, String fieldValue, int pageNo, int pageSize) throws Exception
  {
    if ((pageNo < 0) || (pageSize < 0)) {
      log.error("pageNO����pageSizeС��0��");
      return null;
    }
    RequestPacket requestPacket = new RequestPacket();
    RequestService requestService = new RequestServiceImpl();
    requestPacket.getHEAD().setREQUEST_TYPE("D103");
    requestPacket.getHEAD().setSYSTEMCODE(systemCode);
    Map values = new HashMap();
    values.put("pageNo", Integer.valueOf(pageNo));
    values.put("pageSize", Integer.valueOf(pageSize));
    values.put("fieldValue", (fieldValue == null) ? "" : fieldValue);
    requestPacket.getBODY().setValues(values);
    PageResPacket res = requestService.execute(requestPacket);
    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    return res.getBODY();
  }

  public static DictPage getPrpDsettlementByr(String systemCode, String fieldValue, int pageNo, int pageSize) throws Exception
  {
    if ((pageNo < 0) || (pageSize < 0)) {
      log.error("pageNO����pageSizeС��0��");
      return null;
    }
    RequestPacket requestPacket = new RequestPacket();
    RequestService requestService = new RequestServiceImpl();
    requestPacket.getHEAD().setREQUEST_TYPE("D104");
    requestPacket.getHEAD().setSYSTEMCODE(systemCode);
    Map values = new HashMap();
    values.put("pageNo", Integer.valueOf(pageNo));
    values.put("pageSize", Integer.valueOf(pageSize));
    values.put("fieldValue", (fieldValue == null) ? "" : fieldValue);
    requestPacket.getBODY().setValues(values);
    PageResPacket res = requestService.execute(requestPacket);
    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    return res.getBODY();
  }

  public static DictPage getPrpDstatistics(String systemCode, String makeCom, int pageNo, int pageSize) throws Exception
  {
    if ((pageNo < 0) || (pageSize < 0)) {
      log.error("pageNO����pageSizeС��0��");
      throw new Exception("pageNO����pageSizeС��0��");
    }
    GetPrpDstatisticsReqPacket requestPacket = new GetPrpDstatisticsReqPacket();
    requestPacket.getHEAD().setREQUEST_TYPE("D105");
    requestPacket.getHEAD().setSYSTEMCODE(systemCode);
    requestPacket.getHEAD().setPAGENO(pageNo);
    requestPacket.getHEAD().setPAGESIZE(pageSize);
    requestPacket.getBODY().setMAKECOM(makeCom);
    GetPrpDstatisticsService requestService = new GetPrpDstatisticsServiceImpl();
    PageResPacket res = requestService.execute(requestPacket);
    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    return res.getBODY();
  }

  public static DictPage getPrpDcurrency(String systemCode, String currencyCode, String currencyName, String validStatus, int pageNo, int pageSize)
    throws Exception
  {
    if ((pageNo < 0) || (pageSize < 0)) {
      log.error("pageNO����pageSizeС��0��");
      throw new Exception("pageNO����pageSizeС��0��");
    }
    GetPrpDcurrencyReqPacket requestPacket = new GetPrpDcurrencyReqPacket();
    requestPacket.getHEAD().setREQUEST_TYPE("D110");
    requestPacket.getHEAD().setSYSTEMCODE(systemCode);
    requestPacket.getHEAD().setPAGENO(pageNo);
    requestPacket.getHEAD().setPAGESIZE(pageSize);
    requestPacket.getHEAD().setVALIDSTATUS(validStatus);
    requestPacket.getBODY().setCURRENCYCODE(currencyCode);
    requestPacket.getBODY().setCURRENCYNAME(currencyName);
    GetPrpDcurrencyService requestService = new GetPrpDcurrencyServiceImpl();
    PageResPacket res = requestService.execute(requestPacket);
    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    return res.getBODY();
  }

  public static DictPage getPrpDdisaster(String systemcode, String disasterCodeOrName, String validStatus, Date damageDate, int pageNO, int pageSize)
    throws Exception
  {
    if ((pageNO < 0) || (pageSize < 0)) {
      log.error("pageNO����pageSizeС��0��");
      return null;
    }
    PrpDdisasterReqPacket prpDdisasterReqPacket = new PrpDdisasterReqPacket();
    GetPrpDdisasterService prpDdisasterService = new GetPrpDdisasterServiceImpl();
    prpDdisasterReqPacket.getHEAD().setREQUEST_TYPE("D111");
    prpDdisasterReqPacket.getHEAD().setSYSTEMCODE(systemcode);
    prpDdisasterReqPacket.getBODY().setDISASTERCODE(disasterCodeOrName);
    prpDdisasterReqPacket.getBODY().setDAMAGEDATE(damageDate);
    prpDdisasterReqPacket.getHEAD().setPAGENO(pageNO);
    prpDdisasterReqPacket.getHEAD().setPAGESIZE(pageSize);
    prpDdisasterReqPacket.getHEAD().setVALIDSTATUS(validStatus);
    PageResPacket res = prpDdisasterService.execute(prpDdisasterReqPacket);

    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    return res.getBODY();
  }

  public static DictPage getPrpDdisaster(String systemcode, String disasterCodeOrName, String validStatus, int pageNO, int pageSize)
    throws Exception
  {
    if ((pageNO < 0) || (pageSize < 0)) {
      log.error("pageNO����pageSizeС��0��");
      return null;
    }
    PrpDdisasterReqPacket prpDdisasterReqPacket = new PrpDdisasterReqPacket();
    GetPrpDdisasterService prpDdisasterService = new GetPrpDdisasterServiceImpl();
    prpDdisasterReqPacket.getHEAD().setREQUEST_TYPE("D111");
    prpDdisasterReqPacket.getHEAD().setSYSTEMCODE(systemcode);
    prpDdisasterReqPacket.getBODY().setDISASTERCODE(disasterCodeOrName);
    prpDdisasterReqPacket.getHEAD().setPAGENO(pageNO);
    prpDdisasterReqPacket.getHEAD().setPAGESIZE(pageSize);
    prpDdisasterReqPacket.getHEAD().setVALIDSTATUS(validStatus);
    PageResPacket res = prpDdisasterService.execute(prpDdisasterReqPacket);

    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    return res.getBODY();
  }

  public static DictPage getPrpDtype(String systemcode, String codeType, String codeTypeName, String validStatus, int pageNO, int pageSize)
    throws Exception
  {
    if ((pageNO < 0) || (pageSize < 0)) {
      log.error("pageNO����pageSizeС��0��");
      return null;
    }
    GetPrpDtypeReqPacket packet = new GetPrpDtypeReqPacket();
    GetPrpDtypeService service = new GetPrpDtypeServiceImpl();
    packet.getHEAD().setREQUEST_TYPE("D112");
    packet.getHEAD().setSYSTEMCODE(systemcode);
    packet.getHEAD().setVALIDSTATUS(validStatus);
    packet.getHEAD().setPAGENO(pageNO);
    packet.getHEAD().setPAGESIZE(pageSize);
    packet.getBODY().setCODETYPE(codeType);
    packet.getBODY().setCODETYPENAME(codeTypeName);
    PageResPacket res = service.execute(packet);

    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    return res.getBODY();
  }

  public static DictPage getPrpDriskItem(String systemCode, String riskCode, String itemCode, String upperItemCode, String clauseCode, String extraItemCode, int pageNo, int pageSize)
    throws Exception
  {
    if ((pageNo < 0) || (pageSize < 0)) {
      log.error("pageNO����pageSizeС��0��");
      throw new Exception("pageNO����pageSizeС��0��");
    }
    GetPrpDriskItemReqPacket requestPacket = new GetPrpDriskItemReqPacket();
    requestPacket.getHEAD().setREQUEST_TYPE("D120");
    requestPacket.getHEAD().setSYSTEMCODE(systemCode);
    requestPacket.getHEAD().setPAGENO(pageNo);
    requestPacket.getHEAD().setPAGESIZE(pageSize);
    requestPacket.getBODY().setRISKCODE(riskCode);
    requestPacket.getBODY().setITEMCODE(itemCode);
    requestPacket.getBODY().setUPPERITEMCODE(upperItemCode);
    requestPacket.getBODY().setCLAUSECODE(clauseCode);
    requestPacket.getBODY().setEXTRAITEMCODE(extraItemCode);
    GetPrpDriskItemService requestService = new GetPrpDriskItemServiceImpl();
    PageResPacket res = requestService.execute(requestPacket);
    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    return res.getBODY();
  }

  public static DictPage getPrpDriskItem(String systemCode, String riskCode, String itemCode, String upperItemCode, String clauseCode, int pageNo, int pageSize)
    throws Exception
  {
    if ((pageNo < 0) || (pageSize < 0)) {
      log.error("pageNO����pageSizeС��0��");
      throw new Exception("pageNO����pageSizeС��0��");
    }
    GetPrpDriskItemReqPacket requestPacket = new GetPrpDriskItemReqPacket();
    requestPacket.getHEAD().setREQUEST_TYPE("D120");
    requestPacket.getHEAD().setSYSTEMCODE(systemCode);
    requestPacket.getHEAD().setPAGENO(pageNo);
    requestPacket.getHEAD().setPAGESIZE(pageSize);
    requestPacket.getBODY().setRISKCODE(riskCode);
    requestPacket.getBODY().setITEMCODE(itemCode);
    requestPacket.getBODY().setUPPERITEMCODE(upperItemCode);
    requestPacket.getBODY().setCLAUSECODE(clauseCode);
    GetPrpDriskItemService requestService = new GetPrpDriskItemServiceImpl();
    PageResPacket res = requestService.execute(requestPacket);
    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    return res.getBODY();
  }

  public static DictPage getPrpDriskItem(String systemCode, String riskCode, String itemCode, String upperItemCode, int pageNo, int pageSize)
    throws Exception
  {
    if ((pageNo < 0) || (pageSize < 0)) {
      log.error("pageNO����pageSizeС��0��");
      throw new Exception("pageNO����pageSizeС��0��");
    }
    GetPrpDriskItemReqPacket requestPacket = new GetPrpDriskItemReqPacket();
    requestPacket.getHEAD().setREQUEST_TYPE("D120");
    requestPacket.getHEAD().setSYSTEMCODE(systemCode);
    requestPacket.getHEAD().setPAGENO(pageNo);
    requestPacket.getHEAD().setPAGESIZE(pageSize);
    requestPacket.getBODY().setRISKCODE(riskCode);
    requestPacket.getBODY().setITEMCODE(itemCode);
    requestPacket.getBODY().setUPPERITEMCODE(upperItemCode);
    GetPrpDriskItemService requestService = new GetPrpDriskItemServiceImpl();
    PageResPacket res = requestService.execute(requestPacket);
    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    return res.getBODY();
  }

  public static DictPage getPrpDports(String systemCode, String portCode, int pageNo, int pageSize) throws Exception
  {
    if ((pageNo < 0) || (pageSize < 0)) {
      log.error("pageNO����pageSizeС��0��");
      throw new Exception("pageNO����pageSizeС��0��");
    }
    GetPrpDportReqPacket requestPacket = new GetPrpDportReqPacket();
    requestPacket.getHEAD().setREQUEST_TYPE("D121");
    requestPacket.getHEAD().setSYSTEMCODE(systemCode);
    requestPacket.getHEAD().setPAGENO(pageNo);
    requestPacket.getHEAD().setPAGESIZE(pageSize);
    requestPacket.getBODY().setPORTCODE(portCode);
    GetPrpDportsService requestService = new GetPrpDportsServiceImpl();
    PageResPacket res = requestService.execute(requestPacket);
    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    return res.getBODY();
  }

  public static DictPage getBank(String systemCode, String bankCode, String bankName, int pageNo, int pageSize) throws Exception
  {
    GetPrpDbankReqPacket request = new GetPrpDbankReqPacket();
    GetBankService service = new GetBankServiceImpl();
    request.getHEAD().setREQUEST_TYPE("D118");
    request.getHEAD().setSYSTEMCODE(systemCode);
    request.getHEAD().setPAGENO(pageNo);
    request.getHEAD().setPAGESIZE(pageSize);
    request.getBODY().setBANKCODE(bankCode);
    request.getBODY().setBANKNAME(bankName);

    PageResPacket res = service.execute(request);
    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    return res.getBODY();
  }

  public static DictPage getShortRate(String systemCode, String riskCode, String clauseCode, String rateType, Date startDate, int startHour, Date endDate, int endHour, int pageNo, int pageSize)
    throws Exception
  {
    GetShortRateReqPacket request = new GetShortRateReqPacket();
    GetShortRateService service = new GetShortRateServiceImpl();
    RequestHeadSchema requestHeadSchema = new RequestHeadSchema();
    requestHeadSchema = request.getHEAD();
    System.out.println(requestHeadSchema.getClass() + 
      "-------------------------------");
    request.getHEAD().setREQUEST_TYPE("D122");
    request.getHEAD().setSYSTEMCODE(systemCode);
    request.getHEAD().setPAGENO(pageNo);
    request.getHEAD().setPAGESIZE(pageSize);
    request.getBODY().setRISKCODE(riskCode);
    request.getBODY().setCLAUSECODE(clauseCode);
    request.getBODY().setRATETYPE(rateType);
    request.getBODY().setSTARTDATE(startDate);
    request.getBODY().setSTARTHOUR(startHour);
    request.getBODY().setENDDATE(endDate);
    request.getBODY().setENDHOUR(endHour);
    System.out.println("dms接收getShortRate:startDate:" + startDate);
    PageResPacket res = service.execute(request);
    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    return res.getBODY();
  }

  public static DictPage getRationShortRate(String systemCode, String riskCode, String areaCode, String clauseCode, String rateType, String rationCode, Date startDate, int startHour, Date endDate, int endHour, int pageNo, int pageSize)
    throws Exception
  {
    GetShortRateReqPacket request = new GetShortRateReqPacket();
    GetShortRateService service = new GetShortRateServiceImpl();
    request.getHEAD().setREQUEST_TYPE("D148");
    request.getHEAD().setSYSTEMCODE(systemCode);
    request.getHEAD().setPAGENO(pageNo);
    request.getHEAD().setPAGESIZE(pageSize);
    request.getBODY().setRISKCODE(riskCode);
    request.getBODY().setCLAUSECODE(clauseCode);
    request.getBODY().setRATIONCODE(rationCode);
    request.getBODY().setAREACODE(areaCode);
    request.getBODY().setRATETYPE(rateType);
    request.getBODY().setSTARTDATE(startDate);
    request.getBODY().setSTARTHOUR(startHour);
    request.getBODY().setENDDATE(endDate);
    request.getBODY().setENDHOUR(endHour);

    PageResPacket res = service.execute(request);
    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    return res.getBODY();
  }

  public static DictPage getPrpDcrossOrg(String systemCode, String orgcod, String comp_cod, String org_lvl, int pageNO, int pageSize)
    throws Exception
  {
    if ((pageNO < 0) || (pageSize < 0)) {
      log.error("pageNO����pageSizeС��0��");
      return null;
    }
    GetPrpDcrossOrgReqPacket getPrpDcrossOrgReqPacket = new GetPrpDcrossOrgReqPacket();
    GetPrpDcrossOrgService getPrpDcrossOrgService = new GetPrpDcrossOrgServiceImpl();
    getPrpDcrossOrgReqPacket.getHEAD().setREQUEST_TYPE("D119");
    getPrpDcrossOrgReqPacket.getHEAD().setSYSTEMCODE(systemCode);
    getPrpDcrossOrgReqPacket.getHEAD().setPAGENO(pageNO);
    getPrpDcrossOrgReqPacket.getHEAD().setPAGESIZE(pageSize);
    getPrpDcrossOrgReqPacket.getBODY().setOrgcod(orgcod);
    getPrpDcrossOrgReqPacket.getBODY().setComp_cod(comp_cod);
    getPrpDcrossOrgReqPacket.getBODY().setOrg_lvl(org_lvl);
    PageResPacket res = getPrpDcrossOrgService
      .execute(getPrpDcrossOrgReqPacket);

    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    return res.getBODY();
  }

  public static DictPage getContractManage(String systemCode, String contractObjectCode, String validStatus, int pageNO, int pageSize)
    throws Exception
  {
    if ((pageNO < 0) || (pageSize < 0)) {
      log.error("pageNO����pageSizeС��0��");
      return null;
    }
    GetContractManageReqPacket getContractManageReqPacket = new GetContractManageReqPacket();
    GetContractManageService getContractManageService = new GetContractManageServiceImpl();
    getContractManageReqPacket.getHEAD().setREQUEST_TYPE("D125");
    getContractManageReqPacket.getHEAD().setSYSTEMCODE(systemCode);
    getContractManageReqPacket.getHEAD().setPAGENO(pageNO);
    getContractManageReqPacket.getHEAD().setPAGESIZE(pageSize);
    getContractManageReqPacket.getBODY().setContractObjectCode(contractObjectCode);
    getContractManageReqPacket.getBODY().setValidStatus(validStatus);
    PageResPacket res = getContractManageService
      .execute(getContractManageReqPacket);
    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    return res.getBODY();
  }

  public static DictPage getPlan(String systemCode, String rationCode, String riskCode, String[] comCodes, String rationType, int pageNO, int pageSize)
    throws Exception
  {
    if ((pageNO < 0) || (pageSize < 0)) {
      log.error("pageNO����pageSizeС��0��");
      return null;
    }
    GetPlanReqPacket getPlanReqPacket = new GetPlanReqPacket();
    GetPlanService getPlanService = new GetPlanServiceImpl();
    com.sinosoft.dmsdriver.domain.common.RequestHeadPacket rqh = new com.sinosoft.dmsdriver.domain.common.RequestHeadPacket();
    rqh = getPlanReqPacket.getHEAD();
    System.out.println(getPlanReqPacket.getHEAD().getClass() + 
      "----------------------------------------------");
    getPlanReqPacket.getHEAD().setREQUEST_TYPE("D126");
    getPlanReqPacket.getHEAD().setSYSTEMCODE(systemCode);
    getPlanReqPacket.getHEAD().setPAGENO(pageNO);
    getPlanReqPacket.getHEAD().setPAGESIZE(pageSize);
    getPlanReqPacket.getBODY().setRationCode(rationCode);
    getPlanReqPacket.getBODY().setRiskCode(riskCode);
    getPlanReqPacket.getBODY().setComCodes(comCodes);
    getPlanReqPacket.getBODY().setRationType(rationType);
    PageResPacket res = getPlanService.execute(getPlanReqPacket);

    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    return res.getBODY();
  }

  public static DictPage getPrpDcurrencyAndExchRate(String systemCode, String currencyCode, String currencyName, String validStatus, int pageNO, int pageSize)
    throws Exception
  {
    if ((pageNO < 0) || (pageSize < 0)) {
      log.error("pageNO����pageSizeС��0��");
      return null;
    }
    GetPrpDcurrencyAndExchRateReqPacket getPrpDcurrencyAndExchRateReqPacket = new GetPrpDcurrencyAndExchRateReqPacket();
    GetPrpDcurrencyAndExchRateService getPrpDcurrencyAndExchRateService = new GetPrpDcurrencyAndExchRateServiceImpl();
    getPrpDcurrencyAndExchRateReqPacket.getHEAD().setREQUEST_TYPE("D129");
    getPrpDcurrencyAndExchRateReqPacket.getHEAD().setSYSTEMCODE(systemCode);
    getPrpDcurrencyAndExchRateReqPacket.getHEAD().setPAGENO(pageNO);
    getPrpDcurrencyAndExchRateReqPacket.getHEAD().setPAGESIZE(pageSize);
    getPrpDcurrencyAndExchRateReqPacket.getBODY().setCurrencyCode(
      currencyCode);
    getPrpDcurrencyAndExchRateReqPacket.getBODY().setCurrencyName(
      currencyName);
    getPrpDcurrencyAndExchRateReqPacket.getBODY().setValidStatus(
      validStatus);
    PageResPacket res = getPrpDcurrencyAndExchRateService
      .execute(getPrpDcurrencyAndExchRateReqPacket);

    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    return res.getBODY();
  }

  public static DictPage getPlan(String systemcode, String planCode, int pageNO, int pageSize) throws Exception
  {
    if ((pageNO < 0) || (pageSize < 0)) {
      log.error("pageNO����pageSizeС��0��");
      return null;
    }
    GetPlanReqPacket getPlanReqPacket = new GetPlanReqPacket();
    GetPlanService getPlanService = new GetPlanServiceImpl();
    getPlanReqPacket.getHEAD().setREQUEST_TYPE("D126");
    getPlanReqPacket.getHEAD().setSYSTEMCODE(systemcode);
    getPlanReqPacket.getHEAD().setPAGENO(pageNO);
    getPlanReqPacket.getHEAD().setPAGESIZE(pageSize);
    getPlanReqPacket.getBODY().setRationCode(planCode);
    PageResPacket res = getPlanService.execute(getPlanReqPacket);

    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    return res.getBODY();
  }

  public static DictPage getIdentity(String systemCode, String identifierCode, String identifierName, String portCode, String portName, String countryCode, String countryCName, String countryEName, String identifierType, int pageNO, int pageSize)
    throws Exception
  {
    if ((pageNO < 0) || (pageSize < 0)) {
      log.error("pageNO����pageSizeС��0��");
      return null;
    }
    GetIdentityReqPacket getIdentityReqPacket = new GetIdentityReqPacket();
    GetIdentityService getIdentityService = new GetIdentityServiceImpl();
    getIdentityReqPacket.getHEAD().setREQUEST_TYPE("D127");
    getIdentityReqPacket.getHEAD().setSYSTEMCODE(systemCode);
    getIdentityReqPacket.getHEAD().setPAGENO(pageNO);
    getIdentityReqPacket.getHEAD().setPAGESIZE(pageSize);
    getIdentityReqPacket.getBODY().setCountryCName(countryCName);
    getIdentityReqPacket.getBODY().setCountryCode(countryCode);
    getIdentityReqPacket.getBODY().setCountryEName(countryEName);
    getIdentityReqPacket.getBODY().setIdentifierCode(identifierCode);
    getIdentityReqPacket.getBODY().setIdentifierName(identifierName);
    getIdentityReqPacket.getBODY().setIdentifierType(identifierType);
    getIdentityReqPacket.getBODY().setPortCode(portCode);
    getIdentityReqPacket.getBODY().setPortName(portName);
    PageResPacket res = getIdentityService.execute(getIdentityReqPacket);
    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    return res.getBODY();
  }

  public static DictPage getCompanys(String systemCode, String comCodeOrName, String upperComCode, String flag, String validStatus, int pageNo, int pageSize)
    throws Exception
  {
    if ((pageNo < 0) || (pageSize < 0)) {
      log.error("pageNO����pageSizeС��0��");
      return null;
    }
    GetCompanyReqPacket getCompanyReqPacket = new GetCompanyReqPacket();
    GetCompanyService getCompanyService = new GetCompanyServiceImpl();
    getCompanyReqPacket.getHEAD().setSYSTEMCODE(systemCode);
    getCompanyReqPacket.getHEAD().setREQUEST_TYPE("D123");
    getCompanyReqPacket.getHEAD().setPAGENO(pageNo);
    getCompanyReqPacket.getHEAD().setPAGESIZE(pageSize);
    getCompanyReqPacket.getBODY().setComCodeOrName(comCodeOrName);
    getCompanyReqPacket.getBODY().setUpperComCode(upperComCode);
    getCompanyReqPacket.getBODY().setFlag(flag);
    getCompanyReqPacket.getBODY().setValidStatus(validStatus);

    PageResPacket res = getCompanyService.execute(getCompanyReqPacket);
    if ("0".equals(res.getHEAD().getRESPONSE_CODE())) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    return res.getBODY();
  }

  public static DictPage getProjects(String systemCode, String projectCode, String comCode, int pageNo, int pageSize) throws Exception
  {
    if ((pageNo < 0) || (pageSize < 0)) {
      log.error("pageNO����pageSizeС��0��");
      return null;
    }
    GetProjectsReqPacket getProjectsReqPacket = new GetProjectsReqPacket();
    GetProjectsService getProjectsService = new GetProjectsServiceImpl();
    getProjectsReqPacket.getHEAD().setSYSTEMCODE(systemCode);
    getProjectsReqPacket.getHEAD().setREQUEST_TYPE("D128");
    getProjectsReqPacket.getHEAD().setPAGENO(pageNo);
    getProjectsReqPacket.getHEAD().setPAGESIZE(pageSize);
    getProjectsReqPacket.getBODY().setComCode(comCode);
    getProjectsReqPacket.getBODY().setProjectCode(projectCode);

    PageResPacket res = getProjectsService.execute(getProjectsReqPacket);
    if ("0".equals(res.getHEAD().getRESPONSE_CODE())) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    return res.getBODY();
  }

  public static DictPage getResource(String systemCode, String resourceCodeOrName, String projectCode, String agentCode, String comCode, int pageNo, int pageSize)
    throws Exception
  {
    if ((pageNo < 0) || (pageSize < 0)) {
      log.error("pageNO����pageSizeС��0��");
      return null;
    }
    GetResourceReqPacket getResourceReqPacket = new GetResourceReqPacket();
    GetResourceService getResourceService = new GetResourceServiceImpl();
    getResourceReqPacket.getHEAD().setSYSTEMCODE(systemCode);
    getResourceReqPacket.getHEAD().setREQUEST_TYPE("D131");
    getResourceReqPacket.getHEAD().setPAGENO(pageNo);
    getResourceReqPacket.getHEAD().setPAGESIZE(pageSize);
    getResourceReqPacket.getBODY()
      .setResourceCodeOrName(resourceCodeOrName);
    getResourceReqPacket.getBODY().setProjectCode(projectCode);
    getResourceReqPacket.getBODY().setAgentCode(agentCode);
    getResourceReqPacket.getBODY().setComCode(comCode);

    PageResPacket res = getResourceService.execute(getResourceReqPacket);
    if ("0".equals(res.getHEAD().getRESPONSE_CODE())) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    return res.getBODY();
  }

  public static DictPage getSimpleTreaty(String systemCode, String classCode, String riskCode, String sectionNo, String startDate, String endDate, int pageNo, int pageSize)
    throws Exception
  {
    if ((pageNo < 0) || (pageSize < 0)) {
      log.error("pageNO����pageSizeС��0��");
      return null;
    }
    GetSimpleTreatyReqPacket getSimpleTreatyReqPacket = new GetSimpleTreatyReqPacket();
    GetSimpleTreatyService getSimpleTreatyService = new GetSimpleTreatyServiceImpl();
    getSimpleTreatyReqPacket.getHEAD().setSYSTEMCODE(systemCode);
    getSimpleTreatyReqPacket.getHEAD().setREQUEST_TYPE("D132");
    getSimpleTreatyReqPacket.getHEAD().setPAGENO(pageNo);
    getSimpleTreatyReqPacket.getHEAD().setPAGESIZE(pageSize);
    getSimpleTreatyReqPacket.getBODY().setClassCode(classCode);
    getSimpleTreatyReqPacket.getBODY().setRiskCode(riskCode);
    getSimpleTreatyReqPacket.getBODY().setSectionNo(sectionNo);
    getSimpleTreatyReqPacket.getBODY().setStartDate(startDate);
    getSimpleTreatyReqPacket.getBODY().setEndDate(endDate);
    PageResPacket res = getSimpleTreatyService
      .execute(getSimpleTreatyReqPacket);
    if ("0".equals(res.getHEAD().getRESPONSE_CODE())) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    return res.getBODY();
  }

  public static DictPage getTradeCodes(String systemCode, String upperCode, String riskCode, int pageNo, int pageSize) throws Exception
  {
    if ((pageNo < 0) || (pageSize < 0)) {
      log.error("pageNO����pageSizeС��0��");
      return null;
    }
    GetTradeCodesReqPacket getTradeCodesReqPacket = new GetTradeCodesReqPacket();
    GetTradeCodesService getTradeCodesService = new GetTradeCodesServiceImpl();
    getTradeCodesReqPacket.getHEAD().setSYSTEMCODE(systemCode);
    getTradeCodesReqPacket.getHEAD().setREQUEST_TYPE("D133");
    getTradeCodesReqPacket.getHEAD().setPAGENO(pageNo);
    getTradeCodesReqPacket.getHEAD().setPAGESIZE(pageSize);
    getTradeCodesReqPacket.getBODY().setUpperCode(upperCode);
    getTradeCodesReqPacket.getBODY().setRiskCode(riskCode);
    PageResPacket res = getTradeCodesService
      .execute(getTradeCodesReqPacket);
    if ("0".equals(res.getHEAD().getRESPONSE_CODE())) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    return res.getBODY();
  }

  public static DictPage getShortRate(String systemCode, String riskCode, String clauseCode, String rateType, int newShortTerm, int oldShortTerm, int pageNo, int pageSize)
    throws Exception
  {
    GetShortRateReqPacket request = new GetShortRateReqPacket();
    GetShortRateService service = new GetShortRateServiceImpl();
    request.getHEAD().setREQUEST_TYPE("D134");
    request.getHEAD().setSYSTEMCODE(systemCode);
    request.getHEAD().setPAGENO(pageNo);
    request.getHEAD().setPAGESIZE(pageSize);
    request.getBODY().setRISKCODE(riskCode);
    request.getBODY().setCLAUSECODE(clauseCode);
    request.getBODY().setRATETYPE(rateType);
    request.getBODY().setNEWSHORTTERM(newShortTerm);
    request.getBODY().setOLDSHORTTERM(oldShortTerm);
    PageResPacket res = service.execute(request);
    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    return res.getBODY();
  }

  public static DictPage getPrpDcodeKind(String systemCode, String riskCode, String codeType, String kindCode, int pageNo, int pageSize)
    throws Exception
  {
    GetPrpDcodeKindReqPacket request = new GetPrpDcodeKindReqPacket();
    GetPrpDcodeKindService service = new GetPrpDcodeKindServiceImpl();
    request.getHEAD().setREQUEST_TYPE("D141");
    request.getHEAD().setSYSTEMCODE(systemCode);
    request.getHEAD().setPAGENO(pageNo);
    request.getHEAD().setPAGESIZE(pageSize);
    request.getBODY().setRISKCODE(riskCode);
    request.getBODY().setCODETYPE(codeType);
    request.getBODY().setKINDCODE(kindCode);
    PageResPacket res = service.execute(request);
    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    return res.getBODY();
  }

  public static DictPage getItem(String systemCode, String riskCode, int pageNo, int pageSize) throws Exception
  {
    GetItemReqPacket request = new GetItemReqPacket();
    GetItemService service = new GetItemServiceImpl();
    request.getHEAD().setREQUEST_TYPE("D143");
    request.getHEAD().setSYSTEMCODE(systemCode);
    request.getHEAD().setPAGENO(pageNo);
    request.getHEAD().setPAGESIZE(pageSize);
    request.getBODY().setRISKCODE(riskCode);
    PageResPacket res = service.execute(request);
    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    return res.getBODY();
  }

  public static DictPage getIdentityDesc(String systemCode, String identifierCode, int pageNo, int pageSize) throws Exception
  {
    GetIdentityDescReqPacket request = new GetIdentityDescReqPacket();
    GetIdentityDescService service = new GetIdentityDescServiceImpl();
    request.getHEAD().setREQUEST_TYPE("D144");
    request.getHEAD().setSYSTEMCODE(systemCode);
    request.getHEAD().setPAGENO(pageNo);
    request.getHEAD().setPAGESIZE(pageSize);
    request.getBODY().setIDENTIFIERCODE(identifierCode);
    PageResPacket res = service.execute(request);
    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    return res.getBODY();
  }

  public static DictPage getRationRate(String systemCode, String riskCode, String areaCode, String clauseCode, String kindCode, String rationCode, Date startDate, int startHour, Date endDate, int endHour, int pageNo, int pageSize)
    throws Exception
  {
    RequestPacket requestPacket = new RequestPacket();
    RequestService requestService = new RequestServiceImpl();
    requestPacket.getHEAD().setREQUEST_TYPE("D154");
    requestPacket.getHEAD().setSYSTEMCODE(systemCode);
    Map values = new HashMap();
    values.put("riskCode", riskCode);
    values.put("areaCode", areaCode);
    values.put("clauseCode", clauseCode);
    values.put("kindCode", kindCode);
    values.put("rationCode", rationCode);
    values.put("startDate", startDate);
    values.put("startHour", Integer.valueOf(startHour));
    values.put("endDate", endDate);
    values.put("endHour", Integer.valueOf(endHour));
    values.put("pageNo", Integer.valueOf(pageNo));
    values.put("pageSize", Integer.valueOf(pageSize));
    requestPacket.getBODY().setValues(values);
    PageResPacket res = requestService.execute(requestPacket);
    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
      throw new Exception(res.getHEAD().getERROR_MESSAGE());
    }
    return res.getBODY();
  }

  public static DictPage getPrpDCodeWithRisk(String systemCode, String codeType, String codeCode, String codeCName, String riskCode, int pageNO, int pageSize)
		    throws Exception
		  {
		    if ((pageNO < 0) || (pageSize < 0)) {
		      log.error("pageNO����pageSizeС��0��");
		      return null;
		    }
		    GetCodeWithReqPacket getCodeWithReqPacket = new GetCodeWithReqPacket();
		    GetCodeWithService getCodeWithService = new GetCodeWithServiceImpl();
		    getCodeWithReqPacket.getHEAD().setREQUEST_TYPE("ALL80");
		    getCodeWithReqPacket.getHEAD().setSYSTEMCODE(systemCode);
		    getCodeWithReqPacket.getBODY().setCodeType(codeType);
		    getCodeWithReqPacket.getBODY().setCodeCode(codeCode);
		    getCodeWithReqPacket.getBODY().setCodeCName(codeCName);
		    getCodeWithReqPacket.getBODY().setWithCode(riskCode);
		    getCodeWithReqPacket.getBODY().setCodeFlag("1");
		    getCodeWithReqPacket.getBODY().getDictPage().setPageNo(pageNO);
		    getCodeWithReqPacket.getBODY().getDictPage().setPageSize(pageSize);
		    PageResPacket res = getCodeWithService.execute(getCodeWithReqPacket);
		    if (res.getHEAD().getRESPONSE_CODE().equals("0")) {
		      throw new Exception(res.getHEAD().getERROR_MESSAGE());
		    }
		    return res.getBODY();
  }
  
  public static DictPage getListByPlanNo(String systemCode,String planNo) throws Exception{
      RequestPacket requestPacket = new RequestPacket();
      RequestService requestService = new RequestServiceImpl();
      requestPacket.getHEAD().setREQUEST_TYPE("D165");
      requestPacket.getHEAD().setSYSTEMCODE(systemCode);
      Map values = new HashMap();
      //values.put("prpDrationClauseKind", type);
      values.put("rationCode", planNo);
      requestPacket.getBODY().setValues(values);
      PageResPacket res = requestService.execute(requestPacket);
      if(res.getHEAD().getRESPONSE_CODE().equals("0"))
          throw new Exception(res.getHEAD().getERROR_MESSAGE());
      else
          return res.getBODY();
  }
}