package cn.com.sinosoft.inf;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.sinosoft.inf.PMS.service.transform.TransFindPrpDclassById;
import cn.com.sinosoft.inf.PMS.service.transform.TransFindPrpDframeById;
import cn.com.sinosoft.inf.PMS.service.transform.TransFindPrpDitemById;
import cn.com.sinosoft.inf.PMS.service.transform.TransFindPrpDitemTypeById;
import cn.com.sinosoft.inf.PMS.service.transform.TransFindPrpDplanById;
import cn.com.sinosoft.inf.PMS.service.transform.TransFindPrpDriskById;
import cn.com.sinosoft.inf.PMS.service.transform.TransFindPrpDriskClauseKindById;
import cn.com.sinosoft.inf.PMS.service.transform.TransFindPrpDriskEngageById;
import cn.com.sinosoft.inf.PMS.service.transform.TransFindPrpDriskItemById;
import cn.com.sinosoft.inf.PMS.service.transform.TransFindPrpDriskLimitById;
import cn.com.sinosoft.inf.PMS.service.transform.TransFindPrpDriskShortRateById;
import cn.com.sinosoft.inf.dict.server.common.DataTransformer;
import cn.com.sinosoft.inf.dict.server.common.ServiceInfoConst;
import cn.com.sinosoft.inf.dict.server.transform.impl.CodeTransformServiceImpl;
import cn.com.sinosoft.inf.dict.server.transform.impl.CodeTranslateServiceImpl;
import cn.com.sinosoft.inf.dict.server.transform.impl.CodeTypeTranslateServiceImpl;
import cn.com.sinosoft.inf.dict.server.transform.impl.CountWorkDayserviceImpl;
import cn.com.sinosoft.inf.dict.server.transform.impl.ExchangeServiceImpl;
import cn.com.sinosoft.inf.dict.server.transform.impl.FindCompanyByConditionServiceImpl;
import cn.com.sinosoft.inf.dict.server.transform.impl.GetBankServiceImpl;
import cn.com.sinosoft.inf.dict.server.transform.impl.GetClassServiceImpl;
import cn.com.sinosoft.inf.dict.server.transform.impl.GetCodeAndNameServiceImpl;
import cn.com.sinosoft.inf.dict.server.transform.impl.GetCodeServiceImpl;
import cn.com.sinosoft.inf.dict.server.transform.impl.GetCodeWithServiceImpl;
import cn.com.sinosoft.inf.dict.server.transform.impl.GetCompanyServiceImpl;
import cn.com.sinosoft.inf.dict.server.transform.impl.GetCompanysServiceImpl;
import cn.com.sinosoft.inf.dict.server.transform.impl.GetContractManageServiceImpl;
import cn.com.sinosoft.inf.dict.server.transform.impl.GetIdentityDescServiceImpl;
import cn.com.sinosoft.inf.dict.server.transform.impl.GetIdentityServiceImpl;
import cn.com.sinosoft.inf.dict.server.transform.impl.GetItemServiceImpl;
import cn.com.sinosoft.inf.dict.server.transform.impl.GetNewShortRateServiceImpl;
import cn.com.sinosoft.inf.dict.server.transform.impl.GetPlanInfoNewServiceImpl;
import cn.com.sinosoft.inf.dict.server.transform.impl.GetPlanInfoServiceImpl;
import cn.com.sinosoft.inf.dict.server.transform.impl.GetPlanServiceImpl;
import cn.com.sinosoft.inf.dict.server.transform.impl.GetPlanWhetherHasFixedServiceImpl;
import cn.com.sinosoft.inf.dict.server.transform.impl.GetProjectsServiceImpl;
import cn.com.sinosoft.inf.dict.server.transform.impl.GetPrpDCodeWithServiceImpl;
import cn.com.sinosoft.inf.dict.server.transform.impl.GetPrpDagentServiceImpl;
import cn.com.sinosoft.inf.dict.server.transform.impl.GetPrpDbankServiceImpl;
import cn.com.sinosoft.inf.dict.server.transform.impl.GetPrpDcodeKindServiceImpl;
import cn.com.sinosoft.inf.dict.server.transform.impl.GetPrpDcodeListServiceImpl;
import cn.com.sinosoft.inf.dict.server.transform.impl.GetPrpDcodeServiceImpl;
import cn.com.sinosoft.inf.dict.server.transform.impl.GetPrpDcompanyListServiceImpl;
import cn.com.sinosoft.inf.dict.server.transform.impl.GetPrpDcompanyServiceImpl;
import cn.com.sinosoft.inf.dict.server.transform.impl.GetPrpDcrossOrgServiceImpl;
import cn.com.sinosoft.inf.dict.server.transform.impl.GetPrpDcurrencyAndExchRateServiceImpl;
import cn.com.sinosoft.inf.dict.server.transform.impl.GetPrpDcurrencyServiceImpl;
import cn.com.sinosoft.inf.dict.server.transform.impl.GetPrpDdealerServiceImpl;
import cn.com.sinosoft.inf.dict.server.transform.impl.GetPrpDdisasterServiceImpl;
import cn.com.sinosoft.inf.dict.server.transform.impl.GetPrpDexchServiceImpl;
import cn.com.sinosoft.inf.dict.server.transform.impl.GetPrpDportServiceImpl;
import cn.com.sinosoft.inf.dict.server.transform.impl.GetPrpDportsServiceImpl;
import cn.com.sinosoft.inf.dict.server.transform.impl.GetPrpDriskItemServiceImpl;
import cn.com.sinosoft.inf.dict.server.transform.impl.GetPrpDstatisticsServiceImpl;
import cn.com.sinosoft.inf.dict.server.transform.impl.GetPrpDtypeServiceImpl;
import cn.com.sinosoft.inf.dict.server.transform.impl.GetRationRateServiceImpl;
import cn.com.sinosoft.inf.dict.server.transform.impl.GetResourceServiceImpl;
import cn.com.sinosoft.inf.dict.server.transform.impl.GetRiskEngageServiceImpl;
import cn.com.sinosoft.inf.dict.server.transform.impl.GetRiskServiceImpl;
import cn.com.sinosoft.inf.dict.server.transform.impl.GetShortRateRationServiceImpl;
import cn.com.sinosoft.inf.dict.server.transform.impl.GetShortRateServiceImpl;
import cn.com.sinosoft.inf.dict.server.transform.impl.GetSimpleTreatyServiceImpl;
import cn.com.sinosoft.inf.dict.server.transform.impl.GetSubCodeServiceImpl;
import cn.com.sinosoft.inf.dict.server.transform.impl.GetTaxAuthoritiesServiceImpl;
import cn.com.sinosoft.inf.dict.server.transform.impl.GetTradeCodesServiceImpl;
import cn.com.sinosoft.inf.dict.server.transform.impl.GetUpperCodeServiceImpl;
import cn.com.sinosoft.inf.dict.server.transform.impl.ReverseCodeTyeAndCodeserviceImpl;
import cn.com.sinosoft.inf.dict.server.transform.impl.RiskTransformServiceImpl;
import cn.com.sinosoft.inf.dict.server.transform.impl.SynServiceImpl;
import cn.com.sinosoft.inf.dict.server.transform.impl.TransServiceImpl;
import cn.com.sinosoft.inf.dict.server.transform.impl.TransServiceSubImpl;
import cn.com.sinosoft.inf.dict.server.transform.impl.TranslateCodeServiceImpl;
import cn.com.sinosoft.inf.dict.server.transform.impl.TranslateLimitServiceImpl;
import cn.com.sinosoft.inf.dict.server.transform.impl.UpdatePrpDstatisticsServiceImpl;

import com.sinosoft.sysframework.exception.BusinessException;

/**
 * 2009.7.29 by ain
 * */
public class Dispatcher {
	// private static final String SYSTEMCODE = "<SYSTEMCODE>";
	private static Log logger = LogFactory.getLog(Dispatcher.class);
	private static final String requestTypeNode = "<REQUEST_TYPE>";
	private static final String TEXT_EMPTY = "";
	private static final Dispatcher instance = new Dispatcher();
	private static final CodeTypeTranslateServiceImpl codeTypeTranslateServiceImpl = new CodeTypeTranslateServiceImpl();
	private static final DataTransformer ExchangeServiceImpl = new ExchangeServiceImpl();
//	private static final DataTransformer GetCountServiceImpl = new GetCountServiceImpl();
	private static final DataTransformer getPrpDcompanyService = new GetPrpDcompanyServiceImpl();
	private static final DataTransformer getPrpDcompanyListService = new GetPrpDcompanyListServiceImpl();
	private static final DataTransformer findCompanyByConditionService = new FindCompanyByConditionServiceImpl();
	private static final DataTransformer GetPrpDagentServiceImpl = new GetPrpDagentServiceImpl();
	private static final DataTransformer GetPrpDbankServiceImpl = new GetPrpDbankServiceImpl();
	private static final DataTransformer GetPrpDdealerServiceImpl = new GetPrpDdealerServiceImpl();
//	private static final DataTransformer GetPrpDdriverServiceImpl = new GetPrpDdriverServiceImpl();
	private static final DataTransformer GetPrpDexchServiceImpl = new GetPrpDexchServiceImpl();
//	private static final DataTransformer GetPrpDplaneServiceImpl = new GetPrpDplaneServiceImpl();
	private static final DataTransformer GetPrpDportServiceImpl = new GetPrpDportServiceImpl();
//	private static final DataTransformer GetPrpDshipServiceImpl = new GetPrpDshipServiceImpl();
	private static final DataTransformer GetPrpDcodeServiceImpl = new GetPrpDcodeServiceImpl();
	private static final DataTransformer GetPrpDtypeServiceImpl = new GetPrpDtypeServiceImpl();
	private static final DataTransformer GetSubCodeServiceImpl = new GetSubCodeServiceImpl();
	private static final DataTransformer GetUpperCodeServiceImpl = new GetUpperCodeServiceImpl();
	private static final DataTransformer TranslateCodeServiceImpl = new TranslateCodeServiceImpl();
	private static final DataTransformer getPrpDcodeListServiceImpl = new GetPrpDcodeListServiceImpl();
//	private static final DataTransformer findCodeByConditionServiceImpl = new FindCodeByConditionServiceImpl();
	private static final DataTransformer synServiceImpl = new SynServiceImpl();
	private static final DataTransformer transFindprpDclassById = new TransFindPrpDclassById();
	private static final DataTransformer transFindprpDriskById = new TransFindPrpDriskById();
	private static final DataTransformer transFindprpDframeById = new TransFindPrpDframeById();
	private static final DataTransformer transFindprpDitemById = new TransFindPrpDitemById();
	private static final DataTransformer transFindprpDitemTypeById = new TransFindPrpDitemTypeById();
//	private static final DataTransformer transFindprpDlimitById = new TransFindPrpDlimitById();
	private static final DataTransformer transFindprpDplanById = new TransFindPrpDplanById();
	private static final DataTransformer transFindPrpDriskClauseKindById = new TransFindPrpDriskClauseKindById();
	private static final DataTransformer transFindPrpDriskEngageById = new TransFindPrpDriskEngageById();
	private static final DataTransformer transFindPrpDriskItemById = new TransFindPrpDriskItemById();
	private static final DataTransformer transFindPrpDriskLimitById = new TransFindPrpDriskLimitById();
	private static final DataTransformer transFindPrpDriskShortRateById = new TransFindPrpDriskShortRateById();
//	private static final DataTransformer transFindprpDclassByCondition = new TransFindPrpDclassByCondition();
//	private static final DataTransformer transFindprpDframeByCondition = new TransFindPrpDframeByCondition();
//	private static final DataTransformer transFindprpDitemByCondition = new TransFindPrpDitemByCondition();
//	private static final DataTransformer transFindprpDitemTypeByCondition = new TransFindPrpDitemTypeByCondition();
//	private static final DataTransformer transFindprpDlimitByCondition = new TransFindPrpDlimitByCondition();
//	private static final DataTransformer transFindprpDplanByCondition = new TransFindPrpDplanByCondition();
//	private static final DataTransformer transFindPrpDriskClauseKindByCondition = new TransFindPrpDriskClauseKindByCondition();
//	private static final DataTransformer transFindPrpDriskEngageByCondition = new TransFindPrpDriskEngageByCondition();
//	private static final DataTransformer transFindPrpDriskItemByCondition = new TransFindPrpDriskItemByCondition();
//	private static final DataTransformer transFindPrpDriskLimitByCondition = new TransFindPrpDriskLimitByCondition();
//	private static final DataTransformer transFindPrpDriskShortRateByCondition = new TransFindPrpDriskShortRateByCondition();
//	private static final DataTransformer transFindPrpDcodeComByCondition = new TransFindPrpDcodeComByCondition();
	private static final DataTransformer getCodeServiceImpl = new GetCodeServiceImpl();
	private static final DataTransformer getCodeAndNameServiceImpl = new GetCodeAndNameServiceImpl();
	private static final DataTransformer getCompanyServiceImpl = new GetCompanyServiceImpl();
	private static final DataTransformer getCompanysServiceImpl = new GetCompanysServiceImpl();
	private static final DataTransformer getCodeWithImpl = new GetCodeWithServiceImpl();
	private static final DataTransformer getPrpDCodeWithImpl = new GetPrpDCodeWithServiceImpl();
	private static final DataTransformer getTaxAuthoritiesServiceImpl = new GetTaxAuthoritiesServiceImpl();
	private static final DataTransformer getRiskServiceImpl = new GetRiskServiceImpl();
	private static final DataTransformer getClassServiceImpl = new GetClassServiceImpl();
	private static final DataTransformer transServiceImpl = new TransServiceImpl();
	private static final DataTransformer getRiskEngageImpl = new GetRiskEngageServiceImpl();
	private static final DataTransformer getPrpDstatisticsServiceImpl = new GetPrpDstatisticsServiceImpl();
	private static final DataTransformer updatePrpDstatisticsService = new UpdatePrpDstatisticsServiceImpl();
	private static final DataTransformer codeTransformService = new CodeTransformServiceImpl();
	private static final DataTransformer riskTransformService = new RiskTransformServiceImpl();
	private static final DataTransformer codeTranslateService = new CodeTranslateServiceImpl();
	private static final DataTransformer getPrpDcurrencyService = new GetPrpDcurrencyServiceImpl();
	private static final DataTransformer getPlanInfoService = new GetPlanInfoServiceImpl();
	
	private static final DataTransformer prpdDisasterImpl =  new GetPrpDdisasterServiceImpl();
	private static final DataTransformer prpdType =  new GetPrpDtypeServiceImpl();
	private static final DataTransformer prpdClass = new GetClassServiceImpl();
	private static final DataTransformer prpDbank = new GetBankServiceImpl();
	private static final DataTransformer getShortRate = new GetShortRateServiceImpl();
	private static final DataTransformer getShortRateRation = new GetShortRateRationServiceImpl();
	
	/**2010-05-12 by wanghaib*/
	private static final DataTransformer getPrpDcrossOrgServiceImpl =  new GetPrpDcrossOrgServiceImpl();
	
	/**2010-07-19 by wanghaib*/
	private static final DataTransformer getContractManageServiceImpl = new GetContractManageServiceImpl();
	
	/**2010-07-21 by wanghaib*/
	private static final DataTransformer getPlanServiceImpl = new GetPlanServiceImpl();
	
	/**2010-07-22 by wanghaib*/
	private static final DataTransformer getIdentityServiceImpl = new GetIdentityServiceImpl();
	
	/**2010-09-16 by wanghaib*/
	private static final DataTransformer getPrpDcurrencyAndExchRateServiceImpl = new GetPrpDcurrencyAndExchRateServiceImpl();
	
	/**2010-09-28 by wanghaib*/
	private static final DataTransformer getPlanWhetherHasFixedServiceImpl = new GetPlanWhetherHasFixedServiceImpl();
	
	private static final DataTransformer getPrpDriskItemServiceImpl =  new GetPrpDriskItemServiceImpl();
	
	private static final DataTransformer getPrpDportsServiceImpl =  new GetPrpDportsServiceImpl();
	
	private static final DataTransformer getProjectsServiceImpl =  new GetProjectsServiceImpl();
	
	private static final DataTransformer getResourceServiceImpl =  new GetResourceServiceImpl();
	
	/**2010-12-16 by wanghaib*/
	private static final DataTransformer getSimpleTreatyServiceImpl =  new GetSimpleTreatyServiceImpl();
	
	/**2011-01-04 by wanghaib*/
	private static final DataTransformer getTradeCodesServiceImpl =  new GetTradeCodesServiceImpl();
	
	/**2011-01-05 by wanghaib*/
	private static final DataTransformer getNewShortRateServiceImpl =  new GetNewShortRateServiceImpl();
	
	/**2011-01-20 by wanghaib*/
	private static final DataTransformer reverseCodeTyeAndCodeserviceImpl =  new ReverseCodeTyeAndCodeserviceImpl();
	
	/**2011-01-20 by wanghaib*/
	private static final DataTransformer translateLimitServiceImpl =  new TranslateLimitServiceImpl();
	
	/**2011-01-20 by wanghaib*/
	private static final DataTransformer getPrpDcodeKindServiceImpl =  new GetPrpDcodeKindServiceImpl();
	
	/**2011-03-28 by wanghaib*/
	private static final DataTransformer countWorkDayserviceImpl =  new CountWorkDayserviceImpl();
	
	/**2011-03-30 by wanghaib*/
	private static final DataTransformer getItemServiceImpl =  new GetItemServiceImpl();
	
	/**2011-04-08 by wanghaib*/
	private static final DataTransformer getIdentityDescServiceImpl =  new GetIdentityDescServiceImpl();
	/**2011-04-08 by wanghaib*/
	private static final DataTransformer getRationRateServiceImpl = new GetRationRateServiceImpl();
	
	/**2012-03-29 by wpf */
	private static final DataTransformer getPlanInfoNewService = new GetPlanInfoNewServiceImpl();
	
	/** mantis：CAR0027，處理人員：DP0706，需求單編號：mantis：CAR0027 :因DMS 正式機上的程式與開發版本有所差異故將DNS查詢方法抽出並另外改寫*/
	private static final DataTransformer transServiceSubImpl = new TransServiceSubImpl();

	
	// private String requestType="";o
	/** 获取单实例 */
	public static Dispatcher getInstance() {
		return instance;
	}

	/** 私有构造方法 */
	private Dispatcher() {
	}

	/** 业务分发，转到相应的处理类 */
	public String handle(String requestMessage) throws Exception {
		String responsexml = "";
		String requestType = getTagValue(requestMessage, requestTypeNode);
		System.out.println("added by yuyiqiang 20130224,进入DMS的服务分发器中 ============================================================");
		System.out.println("请求类型是"+requestType+" ============================================================");
		logger.debug("■ ■ ■ ■ ■ ■ 请求类型 ■ ■ ■ ■ ■ ■ :" + requestType);
		/** 代码类型翻译 */
		if (ServiceInfoConst.CODETYPETRANSLATE.equals(requestType)) {
			responsexml = codeTypeTranslateServiceImpl.execute(requestMessage);
		}
		/** 兑换货币 */
		else if (ServiceInfoConst.EXCHANGE.equals(requestType)) {
			responsexml = ExchangeServiceImpl.execute(requestMessage);
		}
		/** 获取指定查询条件的结果数 D27 */
//		else if (ServiceInfoConst.GETCOUNT.equals(requestType)) {
//			responsexml = GetCountServiceImpl.execute(requestMessage);
//		}
		/** 获得指定机构信息||获得上级机构 */
		else if (ServiceInfoConst.GETPRPDCOMPANY.equals(requestType)
				|| ServiceInfoConst.GETUPPERPRPDCOMPANY.equals(requestType)) {
			responsexml = getPrpDcompanyService.execute(requestMessage);
		}
		/** 获得直接下级机构||获得所有下级机构 */
		else if (ServiceInfoConst.GETSUBCOMCODE.equals(requestType)
				|| ServiceInfoConst.GETALLSUBCOMCODE.equals(requestType)) {
			responsexml = getPrpDcompanyListService.execute(requestMessage);
		}
		/** 通过条件获得公司信息 */
		else if (ServiceInfoConst.FINDCOMPANYBYCONDITION.equals(requestType)) {
			responsexml = findCompanyByConditionService.execute(requestMessage);
		}
		/** 获得指定渠道 */
		else if (ServiceInfoConst.GETPRPDAGENT.equals(requestType)) {
			responsexml = GetPrpDagentServiceImpl.execute(requestMessage);
		}
		/** 获得指定金融机构 */
		else if (ServiceInfoConst.GETPRPDBANK.equals(requestType)) {
			responsexml = GetPrpDbankServiceImpl.execute(requestMessage);
		}
		/** 获取一条代码记录 D24 */
		else if (ServiceInfoConst.GETPRPDCODE.equals(requestType)) {
			responsexml = GetPrpDcodeServiceImpl.execute(requestMessage);
		}
		/** 获得指定经销商 */
		else if (ServiceInfoConst.GETPRPDDEALER.equals(requestType)) {
			responsexml = GetPrpDdealerServiceImpl.execute(requestMessage);
		}
		/** 获得指定司机代码 */
//		else if (ServiceInfoConst.GETPRPDDRIVER.equals(requestType)) {
//			responsexml = GetPrpDdriverServiceImpl.execute(requestMessage);
//		}
		/** 获取兑换率 */
		else if (ServiceInfoConst.GETPRPDEXCH.equals(requestType)) {
			responsexml = GetPrpDexchServiceImpl.execute(requestMessage);
		}
		/** 获得指定飞机代码 */
//		else if (ServiceInfoConst.GETPRPDPLANE.equals(requestType)) {
//			responsexml = GetPrpDplaneServiceImpl.execute(requestMessage);
//		}
		/** 获得指定港口代码 */
		else if (ServiceInfoConst.GETPRPDPORT.equals(requestType)) {
			responsexml = GetPrpDportServiceImpl.execute(requestMessage);
		}
		/** 获得指定船舶代码 */
//		else if (ServiceInfoConst.GETPRPDSHIP.equals(requestType)) {
//			responsexml = GetPrpDshipServiceImpl.execute(requestMessage);
//		}
		/** 获取代码类型 D23 */
		else if (ServiceInfoConst.GETPRPDTYPE.equals(requestType)) {
			responsexml = GetPrpDtypeServiceImpl.execute(requestMessage);
		}
		/** 获取指定代码的直接下级代码 */
		else if (ServiceInfoConst.GETSUBCODE.equals(requestType)) {
			responsexml = GetSubCodeServiceImpl.execute(requestMessage);
		}
		/** 获取指定代码的直接上级代码 */
		else if (ServiceInfoConst.GETUPPERCODE.equals(requestType)) {
			responsexml = GetUpperCodeServiceImpl.execute(requestMessage);
		}
		/** 代码翻译 D21 */
		else if (ServiceInfoConst.TRANSLATECODE.equals(requestType)) {
			responsexml = TranslateCodeServiceImpl.execute(requestMessage);
		}
	    else if (ServiceInfoConst.GETPRPDCODELIST.equals(requestType)) {
			responsexml = getPrpDcodeListServiceImpl.execute(requestMessage);
		} 
//		/** 通过condition获得prpDcode */
//	    else if (ServiceInfoConst.FINDCODEBYCONDITION.equals(requestType)) {
//			responsexml = findCodeByConditionServiceImpl
//					.execute(requestMessage);
//		} 
		else if (ServiceInfoConst.FINDPRPDCLASSBYID.equals(requestType)) {
			responsexml = transFindprpDclassById.execute(requestMessage);
		}
		/*************** hualimin 2009-9-2 ********************/
//		else if (ServiceInfoConst.FINDPRPDENGAGEBYID.equals(requestType)) {
//			responsexml = transFindprpDengageById.execute(requestMessage);
//		} 
		else if (ServiceInfoConst.FINDPRPDFRAMEBYID.equals(requestType)) {
			responsexml = transFindprpDframeById.execute(requestMessage);
		} else if (ServiceInfoConst.FINDPRPDITEMBYID.equals(requestType)) {
			responsexml = transFindprpDitemById.execute(requestMessage);
		} else if (ServiceInfoConst.FINDPRPDITEMTYPEBYID.equals(requestType)) {
			responsexml = transFindprpDitemTypeById.execute(requestMessage);
		} 
//		else if (ServiceInfoConst.FINDPRPDKINDBYID.equals(requestType)) {
//			responsexml = transFindprpDkindById.execute(requestMessage);
//		} 
//		else if (ServiceInfoConst.FINDPRPDLIMITBYID.equals(requestType)) {
//			responsexml = transFindprpDlimitById.execute(requestMessage);
//		} 
//		else if (ServiceInfoConst.FINDPRPDMATERIALINFOBYID
//				.equals(requestType)) {
//			responsexml = transFindprpDmaterialInfoById.execute(requestMessage);
//		}
		else if (ServiceInfoConst.FINDPRPDPLANBYID.equals(requestType)) {
			responsexml = transFindprpDplanById.execute(requestMessage);
		} 
		else if (ServiceInfoConst.FINDPRPDRISKBYID.equals(requestType)) {
			responsexml = transFindprpDriskById.execute(requestMessage);
		}
		/************* hualimin 2009-9-2 ******************************/
		else if (ServiceInfoConst.FINDPRPDRISKCLAUSEKINDBYID.equals(requestType)) {
			responsexml = transFindPrpDriskClauseKindById.execute(requestMessage);
		} else if (ServiceInfoConst.FINDPRPDRISKENGAGEBYID.equals(requestType)) {
			responsexml = transFindPrpDriskEngageById.execute(requestMessage);
		} else if (ServiceInfoConst.FINDPRPDRISKITEMBYID.equals(requestType)) {
			responsexml = transFindPrpDriskItemById.execute(requestMessage);
		} else if (ServiceInfoConst.FINDPRPDRISKLIMITBYID.equals(requestType)) {
			responsexml = transFindPrpDriskLimitById.execute(requestMessage);
		} else if (ServiceInfoConst.FINDPRPDRISKSHORTRATEBYID
				.equals(requestType)) {
			responsexml = transFindPrpDriskShortRateById
					.execute(requestMessage);
		} 

		// ****************************通过条件查询***2009-9-4***hualimin*********
//		else if (ServiceInfoConst.FINDPRPDCLASSBYCONDITION.equals(requestType)) {
//			responsexml = transFindprpDclassByCondition.execute(requestMessage);
//		} 
//		else if (ServiceInfoConst.FINDPRPDENGAGEBYCONDITION
//				.equals(requestType)) {
//			responsexml = transFindprpDengageByCondition
//					.execute(requestMessage);
//		} 
//		else if (ServiceInfoConst.FINDPRPDFRAMEBYCONDITION
//				.equals(requestType)) {
//			responsexml = transFindprpDframeByCondition.execute(requestMessage);
//		} else if (ServiceInfoConst.FINDPRPDITEMBYCONDITION.equals(requestType)) {
//			responsexml = transFindprpDitemByCondition.execute(requestMessage);
//		} else if (ServiceInfoConst.FINDPRPDITEMTYPEBYCONDITION
//				.equals(requestType)) {
//			responsexml = transFindprpDitemTypeByCondition
//					.execute(requestMessage);
//		} 
//		else if (ServiceInfoConst.FINDPRPDKINDBYCONDITION.equals(requestType)) {
//			responsexml = transFindprpDkindByCondition.execute(requestMessage);
//		}
//		else if (ServiceInfoConst.FINDPRPDLIMITBYCONDITION
//				.equals(requestType)) {
//			responsexml = transFindprpDlimitByCondition.execute(requestMessage);
//		} 
//		else if (ServiceInfoConst.FINDPRPDMATERIALINFOBYCONDITION
//				.equals(requestType)) {
//			responsexml = transFindprpDmaterialInfoByCondition
//					.execute(requestMessage);
//		} 
//		else if (ServiceInfoConst.FINDPRPDPLANBYCONDITION.equals(requestType)) {
//			responsexml = transFindprpDplanByCondition.execute(requestMessage);
//		} 
//		else if (ServiceInfoConst.FINDPRPDRISKBYCONDITION.equals(requestType)) {
//			responsexml = transFindprpDriskByCondition.execute(requestMessage);
//		} 
//		else if (ServiceInfoConst.FINDPRPDRISKCLAUSEKINDBYCONDITION
//				.equals(requestType)) {
//			responsexml = transFindPrpDriskClauseKindByCondition
//					.execute(requestMessage);
//		}
//		else if (ServiceInfoConst.FINDPRPDRISKENGAGEBYCONDITION.equals(requestType)) {
//			responsexml = transFindPrpDriskEngageByCondition.execute(requestMessage);
//		}
//		else if (ServiceInfoConst.FINDPRPDRISKITEMBYCONDITION.equals(requestType)) {
//			responsexml = transFindPrpDriskItemByCondition.execute(requestMessage);
//		} else if (ServiceInfoConst.FINDPRPDRISKLIMITBYCONDITION.equals(requestType)) {
//			responsexml = transFindPrpDriskLimitByCondition.execute(requestMessage);
//		} else if (ServiceInfoConst.FINDPRPDRISKSHORTRATEBYCONDITION.equals(requestType)) {
//			responsexml = transFindPrpDriskShortRateByCondition.execute(requestMessage);
//		} else if (ServiceInfoConst.FINDPRPDCODECOMBYCONDITION.equals(requestType)) {
//			responsexml = transFindPrpDcodeComByCondition.execute(requestMessage);
//		}
		//*****************************通过条件查询******2009-9-4******hualimin*******
		// ******

		// *******************************分页功能*********2009-11-9****hualimin****
		// start*****

		else if (ServiceInfoConst.GETCODE.equals(requestType)) {// 获得对应codeType的代码
			responsexml = getCodeServiceImpl.execute(requestMessage);
		} else if (ServiceInfoConst.GETCODEANDNAME.equals(requestType)) {
			responsexml = getCodeAndNameServiceImpl.execute(requestMessage);
		} else if (ServiceInfoConst.GETCOMPANY.equals(requestType)) {
			responsexml = getCompanyServiceImpl.execute(requestMessage);
		} else if (ServiceInfoConst.GETCOMPANYS.equals(requestType)) {
			responsexml = getCompanysServiceImpl.execute(requestMessage);
		} else if (ServiceInfoConst.GETCODEWITHRISK.equals(requestType)
				|| ServiceInfoConst.GETCODEWITHCOM.equals(requestType)
				|| ServiceInfoConst.GETSUBCODEWITHRISK.equals(requestType)) {
			responsexml = getCodeWithImpl.execute(requestMessage);
		}else if (ServiceInfoConst.GETPRPDCODEWITHRISK.equals(requestType)) {
			responsexml = getPrpDCodeWithImpl.execute(requestMessage);
		} 		else if (ServiceInfoConst.GETRISKENGAGE.equals(requestType)) {
			responsexml = getRiskEngageImpl.execute(requestMessage);
		} else if (ServiceInfoConst.GETTAXAUTHORITIES.equals(requestType)) {
			responsexml = getTaxAuthoritiesServiceImpl.execute(requestMessage);
		} else if (ServiceInfoConst.GETRISK.equals(requestType)) {
			responsexml = getRiskServiceImpl.execute(requestMessage);
		} else if (ServiceInfoConst.GETCLASS.equals(requestType)) {
			responsexml = getClassServiceImpl.execute(requestMessage);
		}else if(ServiceInfoConst.PRPDDISASTER.equals(requestType)){
			responsexml = prpdDisasterImpl.execute(requestMessage);
		}else if(ServiceInfoConst.PRPDTYPE.equals(requestType)){
			responsexml = prpdType.execute(requestMessage);
		}else if(ServiceInfoConst.PRPDCLASS.equals(requestType)){ 
			responsexml = prpdClass.execute(requestMessage);
		}else if(ServiceInfoConst.GETBANK.equals(requestType)){
			responsexml = prpDbank.execute(requestMessage);
		}else if(ServiceInfoConst.GETSHORTRATE.equals(requestType)){
			responsexml = getShortRate.execute(requestMessage);
		}else if(ServiceInfoConst.GETSHORTRATERATION.equals(requestType)){
			responsexml = getShortRateRation.execute(requestMessage);
		}
		/**** 统一请求和返回报文结构后分发器*只需要分发到同一个接口TransServiceImpl中********start */
		else if (ServiceInfoConst.GETPRPDTREATYRETEN.equals(requestType)
//				|| ServiceInfoConst.GETRISKBYCONDITION.equals(requestType)
				|| ServiceInfoConst.GETPRINTTYPE.equals(requestType)
				|| ServiceInfoConst.GETSERVICEINFOBYCODE.equals(requestType)
				|| ServiceInfoConst.GETSERVICEINFOBYCODES.equals(requestType)
				|| ServiceInfoConst.GETURLBYCODE.equals(requestType)
				|| ServiceInfoConst.GETAGENT.equals(requestType)
				|| ServiceInfoConst.GETAGENTBYCODE.equals(requestType)
				|| ServiceInfoConst.GETRISKCLAUSE.equals(requestType)
				|| ServiceInfoConst.GETRISKCLAUSEKIND.equals(requestType)
				|| ServiceInfoConst.GETPRPDCLAUSEKIND.equals(requestType)//added by yuyiqiang 20130226查询条款险别的关系
				|| ServiceInfoConst.PRPDKIND.equals(requestType)
				|| ServiceInfoConst.GETPRPDCUSTOMERUNIT.equals(requestType) 
				|| ServiceInfoConst.GETPRPDCUSTOMERIDV.equals(requestType) //added by wanglianzhou 20130414 客户校验
				|| ServiceInfoConst.SAVEPRPDCUSTOMERIDV.equals(requestType) //added by wanglianzhou 201304423 新增个人信息
				|| ServiceInfoConst.SAVEPRPDCUSTOMERUNIT.equals(requestType) //added by wanglianzhou 201304427 新增单位信息
				|| ServiceInfoConst.PRPDLIMIT.equals(requestType)
				|| ServiceInfoConst.GETACCOUNTINFO.equals(requestType)
				|| ServiceInfoConst.GETREINSURER.equals(requestType)
				|| ServiceInfoConst.GETPRPDOLDCODE.equals(requestType)
				|| ServiceInfoConst.GETCOINS.equals(requestType)
				|| ServiceInfoConst.PRPDRISKLIMIT.equals(requestType)
				|| ServiceInfoConst.PRPDSETTLEMENTLKR.equals(requestType)
				|| ServiceInfoConst.PRPDSETTLEMEMTBYR.equals(requestType)
			    || ServiceInfoConst.GETRISKITEM.equals(requestType)
			    || ServiceInfoConst.GETRISKILIMIT.equals(requestType)
			    //MODIFY BEGIN-ADD-chenyi-20110513-reason:查询社保地方政策资料
			    || ServiceInfoConst.GETINFOMATION.equals(requestType)
			    //MODIFY END-ADD-chenyi-20110513-reason:查询社保地方政策资料
			    //MODIFY BEGIN-ADD-renshuo-20110513-reason:查询二级责任
			    || ServiceInfoConst.GETRISKCLAUSEKINDSUB.equals(requestType)
			    //MODIFY END-ADD-renshuo-20110513-reason:查询二级责任
			     //MODIFY BEGIN-ADD-renshuo-20110714-reason:查询责任互斥依赖关系
			    || ServiceInfoConst.GETRISKCLAUSEKINDRELATION.equals(requestType)
			    //MODIFY END-ADD-renshuo-20110714-reason:查询责任互斥依赖关系
			    || ServiceInfoConst.GETREVERRISKIENGAGE.equals(requestType)
			    || ServiceInfoConst.GETPRPDKINDPRODUCT.equals(requestType)
			    || ServiceInfoConst.GETPRPDCUSTOMERFXQ.equals(requestType)
			    // and by xuli 20130624險別对应文案号信息查询
			    || ServiceInfoConst.GETPRPDKINDREPORT.equals(requestType)
			    || ServiceInfoConst.GETPRPDKINDPRODUCT.equals(requestType)
			    || ServiceInfoConst.ALLOWCARKIND.equals(requestType)
			    || ServiceInfoConst.GETPRDITEMSHIP.equals(requestType)
			    || ServiceInfoConst.GETPRDITEMPLANE.equals(requestType)
			    || ServiceInfoConst.SAVEPRPDITEMSHIP.equals(requestType) 
			    || ServiceInfoConst.SAVECOPYNUMBER.equals(requestType) 
			    || ServiceInfoConst.SAVEORUPDATEOCCUPATION.equals(requestType)
			    || ServiceInfoConst.SAVEPRPDPLANE.equals(requestType)
			    || ServiceInfoConst.SAVEENGAGEMAINTENANCE.equals(requestType)//add yjm 20150331 特約及條款
			    || ServiceInfoConst.SAVECLAUSEMAINTENANCE.equals(requestType)){
			responsexml = transServiceImpl.execute(requestMessage);
		}
		/*** 统一请求和返回报文结构后分发器**在TransServiceImpl中会根据具体请求进行转发*********end **/
		// *************分页功能*********2009-11-9*******hualimin**end**
		/** 产品创新引擎同步接口 */
		/** 渠道信息同步接口 */
		else if (ServiceInfoConst.SYNCHRORISKDATA.equals(requestType)
				|| ServiceInfoConst.SYNCHROFRAMEDATA.equals(requestType)
				|| ServiceInfoConst.SYNCHROPLANDATA.equals(requestType)
				|| ServiceInfoConst.SYNCHROCLASSDATA.equals(requestType)
				|| ServiceInfoConst.SYNCHROAGENTDATA.equals(requestType)
				//modify begin add by guyanqing 2011-09-28 reason:
				|| ServiceInfoConst.SYNCHROREVISERISKDATA.equals(requestType)
				|| ServiceInfoConst.SYNCHROREVISECLAUSEDATA.equals(requestType)
			|| ServiceInfoConst.SYNCHROMODIFYRISKDATA.equals(requestType)
				|| ServiceInfoConst.SYNCHROMODIFYCLAUSEREPORTDATA.equals(requestType)
			    || ServiceInfoConst.SYNCHROPRODUCTSETDATA.equals(requestType)){
			   //modify begin add by guyanqing 2011-09-28 reason:
			responsexml = synServiceImpl.execute(requestMessage);
		}
		else if (ServiceInfoConst.GETPRPDSTATISTICS.equals(requestType)) {
			responsexml = getPrpDstatisticsServiceImpl.execute(requestMessage);
		}
		else if (ServiceInfoConst.UPDATEPRPDSTATISTICS.equals(requestType)) {
			responsexml = updatePrpDstatisticsService.execute(requestMessage);
		}
		else if (ServiceInfoConst.CODETRANSFORM.equals(requestType)) {
			responsexml = codeTransformService.execute(requestMessage);
		}
		else if (ServiceInfoConst.RISKTRANSFORM.equals(requestType)) {
			responsexml = riskTransformService.execute(requestMessage);
		}
		else if (ServiceInfoConst.CODETRANSLATE.equals(requestType)) {
			responsexml = codeTranslateService.execute(requestMessage);
		}
		else if(ServiceInfoConst.GETPRPDCURRENCY.equals(requestType)) {
			responsexml = getPrpDcurrencyService.execute(requestMessage);
		}
		/** 2012-03-29 by wpf */
		else if (ServiceInfoConst.GETPLANINFO.equals(requestType)) {
			responsexml = getPlanInfoService.execute(requestMessage);
		}
		else if (ServiceInfoConst.GETPLANINFONEW.equals(requestType)) {
			responsexml = getPlanInfoNewService.execute(requestMessage);
		}

		/**2010-05-12 by wanghaibo*/
		else if(ServiceInfoConst.GETPRPDCROSSORG.equals(requestType)) {
			responsexml = getPrpDcrossOrgServiceImpl.execute(requestMessage);
		}
		/**2010-07-19 by wanghaibo*/
		else if(ServiceInfoConst.GETCONTRACTMANAGE.equals(requestType)) {
			responsexml = getContractManageServiceImpl.execute(requestMessage);
		}
		/**2010-07-21 by wanghaibo*/
		else if(ServiceInfoConst.GETPLAN.equals(requestType)) {
			responsexml = getPlanServiceImpl.execute(requestMessage);
		}
		/**2010-07-22 by wanghaibo*/
		else if(ServiceInfoConst.GETIDENTITY.equals(requestType)) {
			responsexml = getIdentityServiceImpl.execute(requestMessage);
		}
		/**2010-09-16 by wanghaibo*/
		else if (ServiceInfoConst.PrpDcurrencyAndExchRate.equals(requestType)){
			responsexml = getPrpDcurrencyAndExchRateServiceImpl.execute(requestMessage);		
		}
		/**2010-09-28 by wanghaibo*/
		else if(ServiceInfoConst.GetPlanWhetherHasFixed.equals(requestType)){
			responsexml = getPlanWhetherHasFixedServiceImpl.execute(requestMessage);
		}
		else if(ServiceInfoConst.GETPRPDRISKITEM.equals(requestType)) {
			responsexml = getPrpDriskItemServiceImpl.execute(requestMessage);
		}
		else if(ServiceInfoConst.GETPRPDPORTS.equals(requestType)) {
			responsexml = getPrpDportsServiceImpl.execute(requestMessage);
		}
		else if(ServiceInfoConst.GETPROJECTS.equals(requestType)) {
			responsexml = getProjectsServiceImpl.execute(requestMessage);
		}
		else if(ServiceInfoConst.GETRESOURCE.equals(requestType)) {
			responsexml = getResourceServiceImpl.execute(requestMessage);
		}
		/** 2010-12 by wanghaibo*/
		else if(ServiceInfoConst.GETSIMPLETREATY.equals(requestType)) {
			responsexml = getSimpleTreatyServiceImpl.execute(requestMessage);
		}
		/** 2011-01-04 by wanghaibo*/
		else if(ServiceInfoConst.GETTRADECODES.equals(requestType)) {
			responsexml = getTradeCodesServiceImpl.execute(requestMessage);
		}
		/** 2011-01-05 by wanghaibo*/
		else if(ServiceInfoConst.NEWGETSHORTRATE.equals(requestType)) {
			responsexml = getNewShortRateServiceImpl.execute(requestMessage);
		}
		/** 2011-01-20 by wanghaibo*/
		else if(ServiceInfoConst.REVERSECODETYPEANDCODE.equals(requestType)) {
			responsexml = reverseCodeTyeAndCodeserviceImpl.execute(requestMessage);
		}
		/** 2011-03-03 by wanghaibo*/
		else if(ServiceInfoConst.TRANSLATELIMIT.equals(requestType)) {
			responsexml = translateLimitServiceImpl.execute(requestMessage);
		}
		/** 2011-03-11 by wanghaibo*/
		else if(ServiceInfoConst.GETPRPDCODEKIND.equals(requestType)) {
			responsexml = getPrpDcodeKindServiceImpl.execute(requestMessage);
		}
		/** 2011-03-28 by wanghaibo*/
		else if(ServiceInfoConst.COUNTWORKDAY.equals(requestType)) {
			responsexml = countWorkDayserviceImpl.execute(requestMessage);
		}
		/** 2011-03-30 by wanghaibo*/
		else if(ServiceInfoConst.GETITEM.equals(requestType)) {
			responsexml = getItemServiceImpl.execute(requestMessage);
		}/** 2011-03-30 by wanghaibo*/
		else if(ServiceInfoConst.GETIDENTITYDESC.equals(requestType)) {
			responsexml = getIdentityDescServiceImpl.execute(requestMessage);
		}
		/** 2011-06-23 by guyanqing*/
		else if(ServiceInfoConst.GETPRPDBYCONDITON.equals(requestType)){
			responsexml = getPrpDcodeListServiceImpl.execute(requestMessage);
		}
		/**2011-10-27 by guyanqing 方案费率*/
		else if(ServiceInfoConst.GETRATIONRATE.equals(requestType)){
			responsexml = getRationRateServiceImpl.execute(requestMessage);
		}
		/**mantis：CAR0027，處理人員：DP0706，需求單編號：mantis：CAR0027 :
		 * 因DMS 正式機上的程式與開發版本有所差異故將DNS查詢方法抽出並另外改寫*/
		else if(ServiceInfoConst.SAVEPRPDCUSTOMERUNITNEW.equals(requestType)){
			responsexml = transServiceSubImpl.execute(requestMessage);
		}
		else {
			System.out.println("不存在的接口编号为：=======↓ " + requestType + " ↓=======");
			throw new BusinessException(ServiceInfoConst.ERROR_CODE_EXC, ServiceInfoConst.ERROR_MESSAGE_EXC);
		}
		return responsexml;
	}

	/** 通过字符串截取，获得标签的值 */
	private String getTagValue(String requestMessage, String tag) {
		int beginIndex = requestMessage.indexOf(tag);// 得到开始标记<tag>中的"<"的起始位置
		/** 解决xstream下划线问题**************start */
		if (beginIndex == -1) {
			tag = tag.replace("_", "__");
			beginIndex = requestMessage.indexOf(tag);
		}
		/** 解决xstream下划线问题**************end */
		int endIndex = -1;

		String tagValue = "";

		if (beginIndex >= 0) {
			String endTag = "</" + tag.substring(1);
			endIndex = requestMessage.indexOf(endTag); // 得到结束标记</tag>中的"<"的起始位置
			tagValue = requestMessage.substring(beginIndex + tag.length(),
					endIndex);
			tagValue = tagValue.replaceAll("\r", TEXT_EMPTY);
			tagValue = tagValue.replaceAll("\n", TEXT_EMPTY);
			tagValue = tagValue.replaceAll("\t", TEXT_EMPTY);
			tagValue = tagValue.trim();
		}
		return tagValue;
	}
}
