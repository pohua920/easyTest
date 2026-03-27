package cn.com.sinosoft.ims.sync;

import ins.framework.common.ServiceFactory;

import java.util.List;

import cn.com.sinosoft.common.model.InputBean;
import cn.com.sinosoft.dms.model.PrpDagent;
import cn.com.sinosoft.dms.model.PrpDagentAll;
import cn.com.sinosoft.dms.model.PrpDbank;
import cn.com.sinosoft.dms.model.PrpDcodeRisk;
import cn.com.sinosoft.dms.model.PrpDcoins;
import cn.com.sinosoft.dms.model.PrpDcompany;
import cn.com.sinosoft.dms.model.PrpDcompanyCheck;
import cn.com.sinosoft.dms.model.PrpDcrossOrg;
import cn.com.sinosoft.dms.model.PrpDdealer;
import cn.com.sinosoft.dms.model.PrpDexch;
import cn.com.sinosoft.dms.model.PrpDnewCode;
import cn.com.sinosoft.dms.model.PrpDnewCodeCom;
import cn.com.sinosoft.dms.model.PrpDplane;
import cn.com.sinosoft.dms.model.PrpDport;
import cn.com.sinosoft.dms.model.PrpDproject;
import cn.com.sinosoft.dms.model.PrpDreinsurer;
import cn.com.sinosoft.dms.model.PrpDresource;
import cn.com.sinosoft.dms.model.PrpDriskEngage;
import cn.com.sinosoft.dms.model.PrpDsettlementByr;
import cn.com.sinosoft.dms.model.PrpDsettlementLkr;
import cn.com.sinosoft.dms.model.PrpDship;
import cn.com.sinosoft.dms.model.PrpDtreatyReten;
import cn.com.sinosoft.dms.model.PrpDtype;
import cn.com.sinosoft.dms.service.facade.PrpDTreatyRetenService;
import cn.com.sinosoft.dms.service.facade.PrpDagentService;
import cn.com.sinosoft.dms.service.facade.PrpDbankService;
import cn.com.sinosoft.dms.service.facade.PrpDcodeRiskService;
import cn.com.sinosoft.dms.service.facade.PrpDcodeService;
import cn.com.sinosoft.dms.service.facade.PrpDcoinsService;
import cn.com.sinosoft.dms.service.facade.PrpDcompanyService;
import cn.com.sinosoft.dms.service.facade.PrpDcrossOrgService;
import cn.com.sinosoft.dms.service.facade.PrpDdealerService;
import cn.com.sinosoft.dms.service.facade.PrpDexchService;
import cn.com.sinosoft.dms.service.facade.PrpDplaneService;
import cn.com.sinosoft.dms.service.facade.PrpDportService;
import cn.com.sinosoft.dms.service.facade.PrpDprojectService;
import cn.com.sinosoft.dms.service.facade.PrpDreinsurerService;
import cn.com.sinosoft.dms.service.facade.PrpDresourceService;
import cn.com.sinosoft.dms.service.facade.PrpDriskEngageService;
import cn.com.sinosoft.dms.service.facade.PrpDsettlementByrService;
import cn.com.sinosoft.dms.service.facade.PrpDsettlementLkrService;
import cn.com.sinosoft.dms.service.facade.PrpDshipService;
import cn.com.sinosoft.dms.service.facade.PrpDtypeService;
import cn.com.sinosoft.ims.log.model.UtiISyncLog;
import cn.com.sinosoft.ims.util.ReadProperties;

/**
 * 功能：用于处理收到的JMS消息 作者： 时间：2009-8-10 19:39 修改：
 * 
 */
public class JmsMessageProcessor {
	private static String companyType = ReadProperties.getString("deployCom");
	static {
		if (companyType != null) {
			companyType.trim();
		} else {
			companyType = "";
		}
	}

	public void processMessage(InputBean inputBean) {
		boolean isReceivable = SyncConstants.DestComCode_Pub.equals(inputBean.getDestComCode())
				|| companyType.equals(inputBean.getDestComCode());
		System.out.println("******请求标识********："+inputBean.getRequestFlag());
		if (SyncConstants.RequestFlag_PrpDtypeMaintain.equals(inputBean.getRequestFlag())) {
			if (isReceivable) {
				prpdTypeMessageProcess(inputBean);
			}
		} else if (SyncConstants.RequestFlag_PrpDcodeMaintain.equals(inputBean.getRequestFlag())) {
			if (isReceivable) {
				prpDnewCodeMessageProcess(inputBean);
			}
		} else if (SyncConstants.RequestFlag_PrpDcompanyMaintain.equals(inputBean.getRequestFlag())) {
			if (isReceivable) {
				prpdCompanyMessageProcess(inputBean);
			}
		} else if (SyncConstants.RequestFlag_PrpDexchMaintain.equals(inputBean.getRequestFlag())) {
			if (isReceivable) {
				prpdExchMessageProcess(inputBean);
			}
		} else if (SyncConstants.RequestFlag_PrpDbankMaintain.equals(inputBean.getRequestFlag())) {
			if (isReceivable) {
				prpdBankMessageProcess(inputBean);
			}
		} else if (SyncConstants.RequestFlag_PrpDagentMaintain.equals(inputBean.getRequestFlag())) {
			if (isReceivable) {
				prpdAgentMessageProcess(inputBean);
			}//渠道接口清分		
		}  else if  (SyncConstants.RequestFlag_PrpDagentAllMaintain.equals(inputBean.getRequestFlag())) {
			if (isReceivable) {
				prpdAgentAllMessageProcess(inputBean);
			}
		}else if (SyncConstants.RequestFlag_PrpDdealerMaintain.equals(inputBean.getRequestFlag())) {
			if (isReceivable) {
				prpdDealerMessageProcess(inputBean);
			}
		} else if (SyncConstants.RequestFlag_PrpDplaneMaintain.equals(inputBean.getRequestFlag())) {
			if (isReceivable) {
				prpdPlaneMessageProcess(inputBean);
			}
		} else if (SyncConstants.RequestFlag_PrpDportMaintain.equals(inputBean.getRequestFlag())) {
			if (isReceivable) {
				prpdPortMessageProcess(inputBean);
			}
		} else if (SyncConstants.RequestFlag_PrpDshipMaintain.equals(inputBean.getRequestFlag())) {
			if (isReceivable) {
				prpdShipMessageProcess(inputBean);
			}
			// 增加 2010-02-03
		} else if (SyncConstants.RequestFlag_PrpDTreatyRetenMaintain.equals(inputBean.getRequestFlag())) {
			if (isReceivable) {
				prpDTreatyRetenMessageProcess(inputBean);
			}
		} else if (SyncConstants.RequestFlag_PrpDnewCodeComMaintain.equals(inputBean.getRequestFlag())) {
			if (isReceivable) {
				prpDnewCodeComMessageProcess(inputBean);
		} 
//		else if (SyncConstants.RequestFlag_PrpDcodeComMaintain.equals(inputBean.getRequestFlag())) {
//			if (isReceivable) {
//				prpdCodeComMessageProcess(inputBean);
//			}
//		}
		} else if (SyncConstants.RequestFlag_PrpDcoinsMaintain.equals(inputBean.getRequestFlag())) {
			if (isReceivable) {
				prpdCoinsMessageProcess(inputBean);
			}
		} else if (SyncConstants.RequestFlag_PrpDcodeRiskMaintain.equals(inputBean.getRequestFlag())) {
			if (isReceivable) {
				prpdCodeRiskMessageProcess(inputBean);
			}
		} else if (SyncConstants.RequestFlag_PrpDreinsurerMaintain.equals(inputBean.getRequestFlag())) {
			if (isReceivable) {
				prpdReinsurerMessageProcess(inputBean);
			}
		} else if (SyncConstants.RequestFlag_PrpDsettlementByrMaintain.equals(inputBean.getRequestFlag())) {
			if (isReceivable) {
				prpdSettlementByrMessageProcess(inputBean);
			}
		} else if (SyncConstants.RequestFlag_PrpDsettlementLkrMaintain.equals(inputBean.getRequestFlag())) {
			if (isReceivable) {
				prpdSettlementLkrMessageProcess(inputBean);
			}
		}
		else if (SyncConstants.RequestFlag_PrpDresourceMaintain.equals(inputBean.getRequestFlag())) {
			if (isReceivable) {
				prpdResourceMessageProcess(inputBean);
			}
		}
		else if (SyncConstants.RequestFlag_PrpDriskEngageMaintain.equals(inputBean.getRequestFlag())) {
			if (isReceivable) {
				prpdriskEngageMessageProcess(inputBean);
			}
		}
		else if (SyncConstants.RequestFlag_PrpDprojectMaintain.equals(inputBean.getRequestFlag())) {
			if (isReceivable) {
				prpdprojectMessageProcess(inputBean);
			}
		}
		else if  (SyncConstants.RequestFlag_PrpDaccountInfoMaintain.equals(inputBean.getRequestFlag())) {
			if (isReceivable) {
				synchroRiskDataMessageProcess(inputBean);
			}
		}
		else if  (SyncConstants.RequestFlag_PrpDclassMaintain.equals(inputBean.getRequestFlag())) {
			if (isReceivable) {
				synchroClassDataMessageProcess(inputBean);
			}
		}
		else if  (SyncConstants.RequestFlag_PrpDplanMaintain.equals(inputBean.getRequestFlag())) {
			if (isReceivable) {
				synchroPlanDataMessageProcess(inputBean);
			}
		}
		//交叉销售PrpDcrossOrg清分
		else if  (SyncConstants.RequestFlag_PrpDcrossOrgMaintain.equals(inputBean.getRequestFlag())) {
			if (isReceivable) {
				PrpDcrossOrgDataMessageProcess(inputBean);
			}
		}
		//交叉销售PrpDcompanyCheck清分
		else if  (SyncConstants.RequestFlag_PrpDcompanyCheckMaintain.equals(inputBean.getRequestFlag())) {
			if (isReceivable) {
				PrpDcompanyCheckDataMessageProcess(inputBean);
			}
		}
	}

	/**
	 * auth:dr
	 * 
	 * @param inputBean
	 *            PrpDsettlementLkr信息处理类
	 */
	private void prpdSettlementLkrMessageProcess(InputBean inputBean) {
		PrpDsettlementLkr prpDsettlementLkr = inputBean.getPrpDsettlementLkr();
		PrpDsettlementLkrService prpDsettlementLkrService = (PrpDsettlementLkrService) ServiceFactory
				.getService("prpDsettlementLkrService");// 获得Spring管理的bean
		List<UtiISyncLog> utiISyncLogList = inputBean.getUtiISyncLogList();
		UtiISyncLog utiISyncLog = null;
		if (utiISyncLogList != null) {
			for (UtiISyncLog utiISyncLogTemp : utiISyncLogList) {
				if (companyType.equals(utiISyncLogTemp.getDestComCode())) {
					utiISyncLog = utiISyncLogTemp;
				}
			}
		}
		if (utiISyncLog == null) {
			utiISyncLog = inputBean.getUtiISyncLog();
		}
		if (utiISyncLog != null) {
			try {
				prpDsettlementLkrService.prpdSettlementLkrMessageProcess(prpDsettlementLkr);
				utiISyncLog.setIsSuccess("1");
				utiISyncLog.setReplayTimes(utiISyncLog.getReplayTimes() + 1);
			} catch (Exception e) {
				utiISyncLog.setIsSuccess("0");
				utiISyncLog.setReplayTimes(utiISyncLog.getReplayTimes() + 1);
				utiISyncLog.setErrorMsg(mergeExceptionMessage(e));
			} finally {
				inputBean.setUtiISyncLog(utiISyncLog);
				BCMessageProducer producer = (BCMessageProducer) ServiceFactory.getService("messageProducer");// 获得Spring管理的bean
				producer.send(inputBean);
			}
		}

	}

	/**
	 * auth:dr
	 * 
	 * @param inputBean
	 *            PrpDsettlementByr信息处理类
	 */
	private void prpdSettlementByrMessageProcess(InputBean inputBean) {
		PrpDsettlementByr prpDsettlementByr = inputBean.getPrpDsettlementByr();
		PrpDsettlementByrService prpDsettlementByrService = (PrpDsettlementByrService) ServiceFactory
				.getService("prpDsettlementByrService");// 获得Spring管理的bean
		List<UtiISyncLog> utiISyncLogList = inputBean.getUtiISyncLogList();
		UtiISyncLog utiISyncLog = null;
		if (utiISyncLogList != null) {
			for (UtiISyncLog utiISyncLogTemp : utiISyncLogList) {
				if (companyType.equals(utiISyncLogTemp.getDestComCode())) {
					utiISyncLog = utiISyncLogTemp;
				}
			}
		}
		if (utiISyncLog == null) {
			utiISyncLog = inputBean.getUtiISyncLog();
		}
		if (utiISyncLog != null) {
			try {
				prpDsettlementByrService.prpdSettlementByrMessageProcess(prpDsettlementByr);
				utiISyncLog.setIsSuccess("1");
				utiISyncLog.setReplayTimes(utiISyncLog.getReplayTimes() + 1);
			} catch (Exception e) {
				utiISyncLog.setIsSuccess("0");
				utiISyncLog.setReplayTimes(utiISyncLog.getReplayTimes() + 1);
				utiISyncLog.setErrorMsg(mergeExceptionMessage(e));
			} finally {
				inputBean.setUtiISyncLog(utiISyncLog);
				BCMessageProducer producer = (BCMessageProducer) ServiceFactory.getService("messageProducer");// 获得Spring管理的bean
				producer.send(inputBean);
			}
		}

	}

	/**
	 * auth:dr
	 * 
	 * @param inputBean
	 *            PrpDreinsurer信息处理类
	 */
	private void prpdReinsurerMessageProcess(InputBean inputBean) {
		PrpDreinsurer prpDreinsurer = inputBean.getPrpDreinsurer();
		PrpDreinsurerService prpDreinsurerService = (PrpDreinsurerService) ServiceFactory
				.getService("prpDreinsurerService");// 获得Spring管理的bean
		List<UtiISyncLog> utiISyncLogList = inputBean.getUtiISyncLogList();
		UtiISyncLog utiISyncLog = null;
		if (utiISyncLogList != null) {
			for (UtiISyncLog utiISyncLogTemp : utiISyncLogList) {
				if (companyType.equals(utiISyncLogTemp.getDestComCode())) {
					utiISyncLog = utiISyncLogTemp;
				}
			}
		}
		if (utiISyncLog == null) {
			utiISyncLog = inputBean.getUtiISyncLog();
		}
		if (utiISyncLog != null) {
			try {
				prpDreinsurerService.prpdReinsurerMessageProcess(prpDreinsurer);
				utiISyncLog.setIsSuccess("1");
				utiISyncLog.setReplayTimes(utiISyncLog.getReplayTimes() + 1);
			} catch (Exception e) {
				utiISyncLog.setIsSuccess("0");
				utiISyncLog.setReplayTimes(utiISyncLog.getReplayTimes() + 1);
				utiISyncLog.setErrorMsg(mergeExceptionMessage(e));
			} finally {
				inputBean.setUtiISyncLog(utiISyncLog);
				BCMessageProducer producer = (BCMessageProducer) ServiceFactory.getService("messageProducer");// 获得Spring管理的bean
				producer.send(inputBean);
			}
		}

	}

	/**
	 * auth:dr
	 * 
	 * @param inputBean
	 *            PrpDcodeRisk信息处理类
	 */
	private void prpdCodeRiskMessageProcess(InputBean inputBean) {
		PrpDcodeRisk prpDcodeRisk = inputBean.getPrpDcodeRisk();
		PrpDcodeRiskService prpDcodeRiskService = (PrpDcodeRiskService) ServiceFactory
				.getService("prpDcodeRiskService");// 获得Spring管理的bean
		List<UtiISyncLog> utiISyncLogList = inputBean.getUtiISyncLogList();
		UtiISyncLog utiISyncLog = null;
		if (utiISyncLogList != null) {
			for (UtiISyncLog utiISyncLogTemp : utiISyncLogList) {
				if (companyType.equals(utiISyncLogTemp.getDestComCode())) {
					utiISyncLog = utiISyncLogTemp;
				}
			}
		}
		if (utiISyncLog == null) {
			utiISyncLog = inputBean.getUtiISyncLog();
		}
		if (utiISyncLog != null) {
			try {
				prpDcodeRiskService.prpdCodeRiskMessageProcess(prpDcodeRisk);
				utiISyncLog.setIsSuccess("1");
				utiISyncLog.setReplayTimes(utiISyncLog.getReplayTimes() + 1);
			} catch (Exception e) {
				utiISyncLog.setIsSuccess("0");
				utiISyncLog.setReplayTimes(utiISyncLog.getReplayTimes() + 1);
				utiISyncLog.setErrorMsg(mergeExceptionMessage(e));
			} finally {
				inputBean.setUtiISyncLog(utiISyncLog);
				BCMessageProducer producer = (BCMessageProducer) ServiceFactory.getService("messageProducer");// 获得Spring管理的bean
				producer.send(inputBean);
			}
		}

	}

	/**
	 * auth:dr
	 * 
	 * @param inputBean
	 *            PrpDcoins信息处理类
	 */
	private void prpdCoinsMessageProcess(InputBean inputBean) {
		PrpDcoins prpDcoins = inputBean.getPrpDcoins();
		PrpDcoinsService prpDcoinsService = (PrpDcoinsService) ServiceFactory.getService("prpDcoinsService");// 获得Spring管理的bean
		List<UtiISyncLog> utiISyncLogList = inputBean.getUtiISyncLogList();
		UtiISyncLog utiISyncLog = null;
		if (utiISyncLogList != null) {
			for (UtiISyncLog utiISyncLogTemp : utiISyncLogList) {
				if (companyType.equals(utiISyncLogTemp.getDestComCode())) {
					utiISyncLog = utiISyncLogTemp;
				}
			}
		}
		if (utiISyncLog == null) {
			utiISyncLog = inputBean.getUtiISyncLog();
		}
		if (utiISyncLog != null) {
			try {
				prpDcoinsService.prpdCoinsMessageProcess(prpDcoins);
				utiISyncLog.setIsSuccess("1");
				utiISyncLog.setReplayTimes(utiISyncLog.getReplayTimes() + 1);
			} catch (Exception e) {
				utiISyncLog.setIsSuccess("0");
				utiISyncLog.setReplayTimes(utiISyncLog.getReplayTimes() + 1);
				utiISyncLog.setErrorMsg(mergeExceptionMessage(e));
			} finally {
				inputBean.setUtiISyncLog(utiISyncLog);
				BCMessageProducer producer = (BCMessageProducer) ServiceFactory.getService("messageProducer");// 获得Spring管理的bean
				producer.send(inputBean);
			}
		}

	}

	/**
	 * auth:dr
	 * 
	 * @param inputBean
	 *            prpdCodeCom信息处理类
	 */
	private void prpDnewCodeComMessageProcess(InputBean inputBean) {
		PrpDnewCodeCom prpDnewCodeCom = inputBean.getPrpDnewCodeCom();
		PrpDcodeService prpDcodeService = (PrpDcodeService) ServiceFactory.getService("prpDcodeService");// 获得Spring管理的bean
		List<UtiISyncLog> utiISyncLogList = inputBean.getUtiISyncLogList();
		UtiISyncLog utiISyncLog = null;
		if (utiISyncLogList != null) {
			for (UtiISyncLog utiISyncLogTemp : utiISyncLogList) {
				if (companyType.equals(utiISyncLogTemp.getDestComCode())) {
					utiISyncLog = utiISyncLogTemp;
				}
			}
		}
		if (utiISyncLog == null) {
			utiISyncLog = inputBean.getUtiISyncLog();
		}
		if (utiISyncLog != null) {
			try {
				prpDcodeService.prpDnewCodeComMessageProcess(prpDnewCodeCom);
				utiISyncLog.setIsSuccess("1");
				utiISyncLog.setReplayTimes(utiISyncLog.getReplayTimes() + 1);
			} catch (Exception e) {
				utiISyncLog.setIsSuccess("0");
				utiISyncLog.setReplayTimes(utiISyncLog.getReplayTimes() + 1);
				utiISyncLog.setErrorMsg(mergeExceptionMessage(e));
			} finally {
				inputBean.setUtiISyncLog(utiISyncLog);
				BCMessageProducer producer = (BCMessageProducer) ServiceFactory.getService("messageProducer");// 获得Spring管理的bean
				producer.send(inputBean);
			}
		}
	}

	/**
	 * auth:guo
	 * 
	 * @param inputBean
	 *            prpDtype信息处理类
	 */
	private void prpdTypeMessageProcess(InputBean inputBean) {
		PrpDtype prpDtype = inputBean.getPrpDtype();
		PrpDtypeService prpDtypeService = (PrpDtypeService) ServiceFactory.getService("prpDtypeService");// 获得Spring管理的bean
		List<UtiISyncLog> utiISyncLogList = inputBean.getUtiISyncLogList();
		UtiISyncLog utiISyncLog = null;
		if (utiISyncLogList != null) {
			for (UtiISyncLog utiISyncLogTemp : utiISyncLogList) {
				if (companyType.equals(utiISyncLogTemp.getDestComCode())) {
					utiISyncLog = utiISyncLogTemp;
				}
			}
		}
		if (utiISyncLog == null) {
			utiISyncLog = inputBean.getUtiISyncLog();
		}
		if (utiISyncLog != null) {
			try {
				prpDtypeService.prpdTypeMessageProcess(prpDtype);
				utiISyncLog.setIsSuccess("1");
				utiISyncLog.setReplayTimes(utiISyncLog.getReplayTimes() + 1);
			} catch (Exception e) {
				utiISyncLog.setIsSuccess("0");
				utiISyncLog.setReplayTimes(utiISyncLog.getReplayTimes() + 1);
				utiISyncLog.setErrorMsg(mergeExceptionMessage(e));
			} finally {
				inputBean.setUtiISyncLog(utiISyncLog);
				BCMessageProducer producer = (BCMessageProducer) ServiceFactory.getService("messageProducer");// 获得Spring管理的bean
				producer.send(inputBean);
			}
		}
	}

	private void prpDnewCodeMessageProcess(InputBean inputBean) {
		PrpDnewCode prpDcode = inputBean.getPrpDcode();
		PrpDcodeService prpDcodeService = (PrpDcodeService) ServiceFactory.getService("prpDcodeService");// 获得Spring管理的bean
		List<UtiISyncLog> utiISyncLogList = inputBean.getUtiISyncLogList();
		UtiISyncLog utiISyncLog = null;
		if (utiISyncLogList != null) {
			for (UtiISyncLog utiISyncLogTemp : utiISyncLogList) {
				if (companyType.equals(utiISyncLogTemp.getDestComCode())) {
					utiISyncLog = utiISyncLogTemp;
					break;
				}
			}
		}
		if (utiISyncLog == null) {
			utiISyncLog = inputBean.getUtiISyncLog();
		}
		if (utiISyncLog != null) {
			try {
				prpDcodeService.prpdCodeMessageProcess(prpDcode);
				utiISyncLog.setIsSuccess("1");
				utiISyncLog.setReplayTimes(utiISyncLog.getReplayTimes() + 1);
			} catch (Exception e) {
				utiISyncLog.setIsSuccess("0");
				utiISyncLog.setReplayTimes(utiISyncLog.getReplayTimes() + 1);
				utiISyncLog.setErrorMsg(mergeExceptionMessage(e));
			} finally {
				inputBean.setUtiISyncLog(utiISyncLog);
				BCMessageProducer producer = (BCMessageProducer) ServiceFactory.getService("messageProducer");// 获得Spring管理的bean
				producer.send(inputBean);
			}

		}
	}

	private void prpdCompanyMessageProcess(InputBean inputBean) {
		PrpDcompany prpDcompany = inputBean.getPrpDcompany();
		PrpDcompanyService prpDcompanyService = (PrpDcompanyService) ServiceFactory.getService("prpDcompanyService");// 获得Spring管理的bean
		List<UtiISyncLog> utiISyncLogList = inputBean.getUtiISyncLogList();
		UtiISyncLog utiISyncLog = null;
		if (utiISyncLogList != null) {
			for (UtiISyncLog utiISyncLogTemp : utiISyncLogList) {
				if (companyType.equals(utiISyncLogTemp.getDestComCode())) {
					utiISyncLog = utiISyncLogTemp;
				}
			}
		}
		if (utiISyncLog == null) {
			utiISyncLog = inputBean.getUtiISyncLog();
		}
		if (utiISyncLog != null) {
			try {
				prpDcompanyService.prpDcompanyMessageProcess(prpDcompany);
				utiISyncLog.setIsSuccess("1");
				utiISyncLog.setReplayTimes(utiISyncLog.getReplayTimes() + 1);
			} catch (Exception e) {
				utiISyncLog.setIsSuccess("0");
				utiISyncLog.setReplayTimes(utiISyncLog.getReplayTimes() + 1);
				utiISyncLog.setErrorMsg(mergeExceptionMessage(e));
			} finally {
				inputBean.setUtiISyncLog(utiISyncLog);
				BCMessageProducer producer = (BCMessageProducer) ServiceFactory.getService("messageProducer");// 获得Spring管理的bean
				producer.send(inputBean);
			}

		}
	}

	private void prpdExchMessageProcess(InputBean inputBean) {
		PrpDexch prpDexch = inputBean.getPrpDexch();
		PrpDexchService prpDexchService = (PrpDexchService) ServiceFactory.getService("prpDexchService");// 获得Spring管理的bean
		List<UtiISyncLog> utiISyncLogList = inputBean.getUtiISyncLogList();
		UtiISyncLog utiISyncLog = null;
		if (utiISyncLogList != null) {
			for (UtiISyncLog utiISyncLogTemp : utiISyncLogList) {
				if (companyType.equals(utiISyncLogTemp.getDestComCode())) {
					utiISyncLog = utiISyncLogTemp;
				}
			}
		}
		if (utiISyncLog == null) {
			utiISyncLog = inputBean.getUtiISyncLog();
		}
		if (utiISyncLog != null) {

			try {
				prpDexchService.prpDexchMessageProcess(prpDexch);
				utiISyncLog.setIsSuccess("1");
				utiISyncLog.setReplayTimes(utiISyncLog.getReplayTimes() + 1);
			} catch (Exception e) {
				utiISyncLog.setIsSuccess("0");
				utiISyncLog.setReplayTimes(utiISyncLog.getReplayTimes() + 1);
				utiISyncLog.setErrorMsg(mergeExceptionMessage(e));
			} finally {
				inputBean.setUtiISyncLog(utiISyncLog);
				BCMessageProducer producer = (BCMessageProducer) ServiceFactory.getService("messageProducer");// 获得Spring管理的bean
				producer.send(inputBean);
			}
		}
	}

	private void prpdBankMessageProcess(InputBean inputBean) {
		PrpDbank prpDbank = inputBean.getPrpDbank();
		PrpDbankService prpDbankService = (PrpDbankService) ServiceFactory.getService("prpDbankService");// 获得Spring管理的bean
		List<UtiISyncLog> utiISyncLogList = inputBean.getUtiISyncLogList();
		UtiISyncLog utiISyncLog = null;
		if (utiISyncLogList != null) {
			for (UtiISyncLog utiISyncLogTemp : utiISyncLogList) {
				if (companyType.equals(utiISyncLogTemp.getDestComCode())) {
					utiISyncLog = utiISyncLogTemp;
				}
			}
		}
		if (utiISyncLog == null) {
			utiISyncLog = inputBean.getUtiISyncLog();
		}
		if (utiISyncLog != null) {
			try {
				prpDbankService.prpdBankMessageProcess(prpDbank);
				utiISyncLog.setIsSuccess("1");
				utiISyncLog.setReplayTimes(utiISyncLog.getReplayTimes() + 1);
			} catch (Exception e) {
				utiISyncLog.setIsSuccess("0");
				utiISyncLog.setReplayTimes(utiISyncLog.getReplayTimes() + 1);
				utiISyncLog.setErrorMsg(mergeExceptionMessage(e));
			} finally {
				inputBean.setUtiISyncLog(utiISyncLog);
				BCMessageProducer producer = (BCMessageProducer) ServiceFactory.getService("messageProducer");// 获得Spring管理的bean
				producer.send(inputBean);
			}

		}
	}

	private void prpdAgentAllMessageProcess(InputBean inputBean) {
		PrpDagentAll prpDagentAll = inputBean.getPrpDagentAll();
		List prpdAgentExtList  = inputBean.getPrpDagentExtList();
		List  prpdContractManageList  = inputBean.getPrpDcontractManageList();
		PrpDagentService prpDagentService = (PrpDagentService) ServiceFactory.getService("prpDagentService");// 获得Spring管理的bean
		List<UtiISyncLog> utiISyncLogList = inputBean.getUtiISyncLogList();
		UtiISyncLog utiISyncLog = null;
		if (utiISyncLogList != null) {
			for (UtiISyncLog utiISyncLogTemp : utiISyncLogList) {
				if (companyType.equals(utiISyncLogTemp.getDestComCode())) {
					utiISyncLog = utiISyncLogTemp;
				}
			}
		}
		if (utiISyncLog == null) {
			utiISyncLog = inputBean.getUtiISyncLog();
		}
		if (utiISyncLog != null) {
			try {
				prpDagentService.prpDagentAllMessageProcess(prpDagentAll,prpdAgentExtList,prpdContractManageList);
				utiISyncLog.setIsSuccess("1");
				utiISyncLog.setReplayTimes(utiISyncLog.getReplayTimes() + 1);
			} catch (Exception e) {
				utiISyncLog.setIsSuccess("0");
				utiISyncLog.setReplayTimes(utiISyncLog.getReplayTimes() + 1);
				utiISyncLog.setErrorMsg(mergeExceptionMessage(e));
			} finally {
				inputBean.setUtiISyncLog(utiISyncLog);
				BCMessageProducer producer = (BCMessageProducer) ServiceFactory.getService("messageProducer");// 获得Spring管理的bean
				producer.send(inputBean);
			}

		}

	}
	private void prpdAgentMessageProcess(InputBean inputBean) {
		PrpDagent prpDagent = inputBean.getPrpDagent();
		PrpDagentService prpDagentService = (PrpDagentService) ServiceFactory.getService("prpDagentService");// 获得Spring管理的bean
		List<UtiISyncLog> utiISyncLogList = inputBean.getUtiISyncLogList();
		UtiISyncLog utiISyncLog = null;
		if (utiISyncLogList != null) {
			for (UtiISyncLog utiISyncLogTemp : utiISyncLogList) {
				if (companyType.equals(utiISyncLogTemp.getDestComCode())) {
					utiISyncLog = utiISyncLogTemp;
				}
			}
		}
		if (utiISyncLog == null) {
			utiISyncLog = inputBean.getUtiISyncLog();
		}
		if (utiISyncLog != null) {
			try {
				prpDagentService.prpDagentMessageProcess(prpDagent);
				utiISyncLog.setIsSuccess("1");
				utiISyncLog.setReplayTimes(utiISyncLog.getReplayTimes() + 1);
			} catch (Exception e) {
				utiISyncLog.setIsSuccess("0");
				utiISyncLog.setReplayTimes(utiISyncLog.getReplayTimes() + 1);
				utiISyncLog.setErrorMsg(mergeExceptionMessage(e));
			} finally {
				inputBean.setUtiISyncLog(utiISyncLog);
				BCMessageProducer producer = (BCMessageProducer) ServiceFactory.getService("messageProducer");// 获得Spring管理的bean
				producer.send(inputBean);
			}

		}

	}
	private void prpdDealerMessageProcess(InputBean inputBean) {
		PrpDdealer prpDdealer = inputBean.getPrpDdealer();
		PrpDdealerService prpDdealerService = (PrpDdealerService) ServiceFactory.getService("prpDdealerService");// 获得Spring管理的bean
		List<UtiISyncLog> utiISyncLogList = inputBean.getUtiISyncLogList();
		UtiISyncLog utiISyncLog = null;
		if (utiISyncLogList != null) {
			for (UtiISyncLog utiISyncLogTemp : utiISyncLogList) {
				if (companyType.equals(utiISyncLogTemp.getDestComCode())) {
					utiISyncLog = utiISyncLogTemp;
				}
			}
		}
		if (utiISyncLog == null) {
			utiISyncLog = inputBean.getUtiISyncLog();
		}
		if (utiISyncLog != null) {
			try {
				prpDdealerService.prpDdealerMessageProcess(prpDdealer);
				utiISyncLog.setIsSuccess("1");
				utiISyncLog.setReplayTimes(utiISyncLog.getReplayTimes() + 1);
			} catch (Exception e) {
				utiISyncLog.setIsSuccess("0");
				utiISyncLog.setReplayTimes(utiISyncLog.getReplayTimes() + 1);
				utiISyncLog.setErrorMsg(mergeExceptionMessage(e));
			} finally {
				inputBean.setUtiISyncLog(utiISyncLog);
				BCMessageProducer producer = (BCMessageProducer) ServiceFactory.getService("messageProducer");// 获得Spring管理的bean
				producer.send(inputBean);
			}

		}

	}

	private void prpdPlaneMessageProcess(InputBean inputBean) {
		PrpDplane PrpDplane = inputBean.getPrpDplane();
		PrpDplaneService prpDplaneService = (PrpDplaneService) ServiceFactory.getService("prpDplaneService");// 获得Spring管理的bean
		List<UtiISyncLog> utiISyncLogList = inputBean.getUtiISyncLogList();
		UtiISyncLog utiISyncLog = null;
		if (utiISyncLogList != null) {
			for (UtiISyncLog utiISyncLogTemp : utiISyncLogList) {
				if (companyType.equals(utiISyncLogTemp.getDestComCode())) {
					utiISyncLog = utiISyncLogTemp;
				}
			}
		}
		if (utiISyncLog == null) {
			utiISyncLog = inputBean.getUtiISyncLog();
		}
		if (utiISyncLog != null) {

			try {
				prpDplaneService.prpDplaneMessageProcess(PrpDplane);
				utiISyncLog.setIsSuccess("1");
				utiISyncLog.setReplayTimes(utiISyncLog.getReplayTimes() + 1);
			} catch (Exception e) {
				utiISyncLog.setIsSuccess("0");
				utiISyncLog.setReplayTimes(utiISyncLog.getReplayTimes() + 1);
				utiISyncLog.setErrorMsg(mergeExceptionMessage(e));
			} finally {
				inputBean.setUtiISyncLog(utiISyncLog);
				BCMessageProducer producer = (BCMessageProducer) ServiceFactory.getService("messageProducer");// 获得Spring管理的bean
				producer.send(inputBean);
			}
		}

	}

	private void prpdPortMessageProcess(InputBean inputBean) {
		PrpDport prpDport = inputBean.getPrpDport();
		PrpDportService prpDportService = (PrpDportService) ServiceFactory.getService("prpDportService");// 获得Spring管理的bean
		List<UtiISyncLog> utiISyncLogList = inputBean.getUtiISyncLogList();
		UtiISyncLog utiISyncLog = null;
		if (utiISyncLogList != null) {
			for (UtiISyncLog utiISyncLogTemp : utiISyncLogList) {
				if (companyType.equals(utiISyncLogTemp.getDestComCode())) {
					utiISyncLog = utiISyncLogTemp;
				}
			}
		}
		if (utiISyncLog == null) {
			utiISyncLog = inputBean.getUtiISyncLog();
		}
		if (utiISyncLog != null) {
			try {
				prpDportService.prpDportMessageProcess(prpDport);
				utiISyncLog.setIsSuccess("1");
				utiISyncLog.setReplayTimes(utiISyncLog.getReplayTimes() + 1);
			} catch (Exception e) {
				utiISyncLog.setIsSuccess("0");
				utiISyncLog.setReplayTimes(utiISyncLog.getReplayTimes() + 1);
				utiISyncLog.setErrorMsg(mergeExceptionMessage(e));
			} finally {
				inputBean.setUtiISyncLog(utiISyncLog);
				BCMessageProducer producer = (BCMessageProducer) ServiceFactory.getService("messageProducer");// 获得Spring管理的bean
				producer.send(inputBean);
			}

		}

	}

	private void prpdShipMessageProcess(InputBean inputBean) {
		PrpDship prpDship = inputBean.getPrpDship();
		PrpDshipService prpDshipService = (PrpDshipService) ServiceFactory.getService("prpDshipService");// 获得Spring管理的bean
		List<UtiISyncLog> utiISyncLogList = inputBean.getUtiISyncLogList();
		UtiISyncLog utiISyncLog = null;
		if (utiISyncLogList != null) {
			for (UtiISyncLog utiISyncLogTemp : utiISyncLogList) {
				if (companyType.equals(utiISyncLogTemp.getDestComCode())) {
					utiISyncLog = utiISyncLogTemp;
				}
			}
		}
		if (utiISyncLog == null) {
			utiISyncLog = inputBean.getUtiISyncLog();
		}
		if (utiISyncLog != null) {
			try {
				prpDshipService.prpdShipMessageProcess(prpDship);
				utiISyncLog.setIsSuccess("1");
				utiISyncLog.setReplayTimes(utiISyncLog.getReplayTimes() + 1);
			} catch (Exception e) {
				utiISyncLog.setIsSuccess("0");
				utiISyncLog.setReplayTimes(utiISyncLog.getReplayTimes() + 1);
				utiISyncLog.setErrorMsg(mergeExceptionMessage(e));
			} finally {
				inputBean.setUtiISyncLog(utiISyncLog);
				BCMessageProducer producer = (BCMessageProducer) ServiceFactory.getService("messageProducer");// 获得Spring管理的bean
				producer.send(inputBean);
			}
		}

	}

	/**
	 * auth:dr
	 * 
	 * @param inputBean
	 *            prpDTreatyRetenMaintain信息处理类 2010-02-03
	 */
	private void prpDTreatyRetenMessageProcess(InputBean inputBean) {
		PrpDtreatyReten prpDTreatyReten = inputBean.getPrpDtreatyReten();
		PrpDTreatyRetenService prpDTreatyRetenService = (PrpDTreatyRetenService) ServiceFactory
				.getService("prpDTreatyRetenService");
		List<UtiISyncLog> utiISyncLogList = inputBean.getUtiISyncLogList();
		UtiISyncLog utiISyncLog = null;
		if (utiISyncLogList != null) {
			for (UtiISyncLog utiISyncLogTemp : utiISyncLogList) {
				if (companyType.equals(utiISyncLogTemp.getDestComCode())) {
					utiISyncLog = utiISyncLogTemp;
				}
			}
		}
		if (utiISyncLog == null) {
			utiISyncLog = inputBean.getUtiISyncLog();
		}
		if (utiISyncLog != null) {
			try {
				prpDTreatyRetenService.prpDTreatyRetenMessageProcess(prpDTreatyReten);
				utiISyncLog.setIsSuccess("1");
				utiISyncLog.setReplayTimes(utiISyncLog.getReplayTimes() + 1);
			} catch (Exception e) {
				utiISyncLog.setIsSuccess("0");
				utiISyncLog.setReplayTimes(utiISyncLog.getReplayTimes() + 1);
				utiISyncLog.setErrorMsg(mergeExceptionMessage(e));
			} finally {
				inputBean.setUtiISyncLog(utiISyncLog);
				BCMessageProducer producer = (BCMessageProducer) ServiceFactory.getService("messageProducer");// 获得Spring管理的bean
				producer.send(inputBean);
			}
		}

	}
	/**
	 * auth:dr
	 * 
	 * @param inputBean
	 *            prpDresourceMaintain信息处理类 2010-10-13
	 */
	private void prpdResourceMessageProcess(InputBean inputBean) {
		PrpDresource prpDresource = inputBean.getPrpDresource();
		PrpDresourceService prpDresourceService = (PrpDresourceService) ServiceFactory
				.getService("prpDresourceService");
		List<UtiISyncLog> utiISyncLogList = inputBean.getUtiISyncLogList();
		UtiISyncLog utiISyncLog = null;
		if (utiISyncLogList != null) {
			for (UtiISyncLog utiISyncLogTemp : utiISyncLogList) {
				if (companyType.equals(utiISyncLogTemp.getDestComCode())) {
					utiISyncLog = utiISyncLogTemp;
				}
			}
		}
		if (utiISyncLog == null) {
			utiISyncLog = inputBean.getUtiISyncLog();
		}
		if (utiISyncLog != null) {
			try {
				prpDresourceService.prpDresourceMessageProcess(prpDresource);
				utiISyncLog.setIsSuccess("1");
				utiISyncLog.setReplayTimes(utiISyncLog.getReplayTimes() + 1);
			} catch (Exception e) {
				utiISyncLog.setIsSuccess("0");
				utiISyncLog.setReplayTimes(utiISyncLog.getReplayTimes() + 1);
				utiISyncLog.setErrorMsg(mergeExceptionMessage(e));
			} finally {
				inputBean.setUtiISyncLog(utiISyncLog);
				BCMessageProducer producer = (BCMessageProducer) ServiceFactory.getService("messageProducer");// 获得Spring管理的bean
				producer.send(inputBean);
			}
		}
	}
	/**
	 * auth:whb
	 * 
	 * @param inputBean
	 *            prpdriskEngageMaintain信息处理类 2010-10-13
	 */
	private void prpdriskEngageMessageProcess(InputBean inputBean) {
		PrpDriskEngage prpDriskEngage = inputBean.getPrpDriskEngage();
		PrpDriskEngageService prpDriskEngageService = (PrpDriskEngageService) ServiceFactory
				.getService("prpDriskEngageService");
		List<UtiISyncLog> utiISyncLogList = inputBean.getUtiISyncLogList();
		UtiISyncLog utiISyncLog = null;
		if (utiISyncLogList != null) {
			for (UtiISyncLog utiISyncLogTemp : utiISyncLogList) {
				if (companyType.equals(utiISyncLogTemp.getDestComCode())) {
					utiISyncLog = utiISyncLogTemp;
				}
			}
		}
		if (utiISyncLog == null) {
			utiISyncLog = inputBean.getUtiISyncLog();
		}
		if (utiISyncLog != null) {
			try {
				prpDriskEngageService.prpDriskEngageMessageProcess(prpDriskEngage);
				utiISyncLog.setIsSuccess("1");
				utiISyncLog.setReplayTimes(utiISyncLog.getReplayTimes() + 1);
			} catch (Exception e) {
				utiISyncLog.setIsSuccess("0");
				utiISyncLog.setReplayTimes(utiISyncLog.getReplayTimes() + 1);
				utiISyncLog.setErrorMsg(mergeExceptionMessage(e));
			} finally {
				inputBean.setUtiISyncLog(utiISyncLog);
				BCMessageProducer producer = (BCMessageProducer) ServiceFactory.getService("messageProducer");// 获得Spring管理的bean
				producer.send(inputBean);
			}
		}
	}
	/**
	 * auth:whb
	 * 
	 * @param inputBean
	 *            prpdprojectMaintain信息处理类 2010-10-13
	 */
	private void prpdprojectMessageProcess(InputBean inputBean) {
		PrpDproject prpDproject = inputBean.getPrpDproject();
		PrpDprojectService prpDprojectService = (PrpDprojectService) ServiceFactory
				.getService("prpDprojectService");
		List<UtiISyncLog> utiISyncLogList = inputBean.getUtiISyncLogList();
		UtiISyncLog utiISyncLog = null;
		if (utiISyncLogList != null) {
			for (UtiISyncLog utiISyncLogTemp : utiISyncLogList) {
				if (companyType.equals(utiISyncLogTemp.getDestComCode())) {
					utiISyncLog = utiISyncLogTemp;
				}
			}
		}
		if (utiISyncLog == null) {
			utiISyncLog = inputBean.getUtiISyncLog();
		}
		if (utiISyncLog != null) {
			try {
				prpDprojectService.prpDprojectMessageProcess(prpDproject);
				utiISyncLog.setIsSuccess("1");
				utiISyncLog.setReplayTimes(utiISyncLog.getReplayTimes() + 1);
			} catch (Exception e) {
				utiISyncLog.setIsSuccess("0");
				utiISyncLog.setReplayTimes(utiISyncLog.getReplayTimes() + 1);
				utiISyncLog.setErrorMsg(mergeExceptionMessage(e));
			} finally {
				inputBean.setUtiISyncLog(utiISyncLog);
				BCMessageProducer producer = (BCMessageProducer) ServiceFactory.getService("messageProducer");// 获得Spring管理的bean
				producer.send(inputBean);
			}
		}
	}
	private void synchroRiskDataMessageProcess(InputBean inputBean) {
		
		List accountInfoList  = inputBean.getAccountInfoList();
		List areaList  = inputBean.getAreaList();
		List riskList = inputBean.getRiskList();
		List riskClauseList  = inputBean.getRiskClauseList();
		List riskClauseKindList  = inputBean.getRiskClauseKindList();
		List riskClauseKindRelationList = inputBean.getRiskClauseKindRelationList();
		List riskEngageList = inputBean.getRiskEngageList();
		List riskItemList  = inputBean.getRiskItemList();
		List riskLimitList  = inputBean.getRiskLimitList();
		List riskShortRateList = inputBean.getRiskShortRateList();
		List newCodeRiskList  = inputBean.getNewCodeRiskList();
		List prpdrckratelowerList  = inputBean.getPrpdrckratelowerList();	
		PrpDriskEngageService prpDriskEngageService = (PrpDriskEngageService) ServiceFactory.getService("prpDriskEngageService");// 获得Spring管理的bean
		List<UtiISyncLog> utiISyncLogList = inputBean.getUtiISyncLogList();
		UtiISyncLog utiISyncLog = null;
		if (utiISyncLogList != null) {
			for (UtiISyncLog utiISyncLogTemp : utiISyncLogList) {
				if (companyType.equals(utiISyncLogTemp.getDestComCode())) {
					utiISyncLog = utiISyncLogTemp;
				}
			}
		}
		if (utiISyncLog == null) {
			utiISyncLog = inputBean.getUtiISyncLog();
		}
		if (utiISyncLog != null) {
			try {
				prpDriskEngageService.synchroRiskDataMessageProcess(accountInfoList, areaList, riskList, riskClauseList, riskClauseKindList, riskClauseKindRelationList,riskEngageList, riskItemList, riskLimitList, riskShortRateList, newCodeRiskList, prpdrckratelowerList);
				utiISyncLog.setIsSuccess("1");
				utiISyncLog.setReplayTimes(utiISyncLog.getReplayTimes() + 1);
			} catch (Exception e) {
				utiISyncLog.setIsSuccess("0");
				utiISyncLog.setReplayTimes(utiISyncLog.getReplayTimes() + 1);
				utiISyncLog.setErrorMsg(mergeExceptionMessage(e));
			} finally {
				inputBean.setUtiISyncLog(utiISyncLog);
				BCMessageProducer producer = (BCMessageProducer) ServiceFactory.getService("messageProducer");// 获得Spring管理的bean
				producer.send(inputBean);
			}

		}
	}
	private void synchroClassDataMessageProcess(InputBean inputBean) {
			
			List classList  = inputBean.getPrpDclassList();		
			PrpDriskEngageService prpDriskEngageService = (PrpDriskEngageService) ServiceFactory.getService("prpDriskEngageService");// 获得Spring管理的bean
			List<UtiISyncLog> utiISyncLogList = inputBean.getUtiISyncLogList();
			UtiISyncLog utiISyncLog = null;
			if (utiISyncLogList != null) {
				for (UtiISyncLog utiISyncLogTemp : utiISyncLogList) {
					if (companyType.equals(utiISyncLogTemp.getDestComCode())) {
						utiISyncLog = utiISyncLogTemp;
					}
				}
			}
			if (utiISyncLog == null) {
				utiISyncLog = inputBean.getUtiISyncLog();
			}
			if (utiISyncLog != null) {
				try {
					prpDriskEngageService.synchroClassDataMessageProcess(classList);
					utiISyncLog.setIsSuccess("1");
					utiISyncLog.setReplayTimes(utiISyncLog.getReplayTimes() + 1);
				} catch (Exception e) {
					utiISyncLog.setIsSuccess("0");
					utiISyncLog.setReplayTimes(utiISyncLog.getReplayTimes() + 1);
					utiISyncLog.setErrorMsg(mergeExceptionMessage(e));
				} finally {
					inputBean.setUtiISyncLog(utiISyncLog);
					BCMessageProducer producer = (BCMessageProducer) ServiceFactory.getService("messageProducer");// 获得Spring管理的bean
					producer.send(inputBean);
				}
			}
	}
	private void synchroPlanDataMessageProcess(InputBean inputBean) {
		
		List prpDplanList  = inputBean.getPrpDplanList();
		List prpDplanClauseKindList  = inputBean.getPrpDplanClauseKindList();
		List prpDplanLimitList  = inputBean.getPrpDplanLimitList();
		PrpDriskEngageService prpDriskEngageService = (PrpDriskEngageService) ServiceFactory.getService("prpDriskEngageService");// 获得Spring管理的bean
		List<UtiISyncLog> utiISyncLogList = inputBean.getUtiISyncLogList();
		UtiISyncLog utiISyncLog = null;
		if (utiISyncLogList != null) {
			for (UtiISyncLog utiISyncLogTemp : utiISyncLogList) {
				if (companyType.equals(utiISyncLogTemp.getDestComCode())) {
					utiISyncLog = utiISyncLogTemp;
				}
			}
		}
		if (utiISyncLog == null) {
			utiISyncLog = inputBean.getUtiISyncLog();
		}
		if (utiISyncLog != null) {
			try {
				prpDriskEngageService.synchroPlanDataMessageProcess(prpDplanList,prpDplanClauseKindList,prpDplanLimitList);
				utiISyncLog.setIsSuccess("1");
				utiISyncLog.setReplayTimes(utiISyncLog.getReplayTimes() + 1);
			} catch (Exception e) {
				utiISyncLog.setIsSuccess("0");
				utiISyncLog.setReplayTimes(utiISyncLog.getReplayTimes() + 1);
				utiISyncLog.setErrorMsg(mergeExceptionMessage(e));
			} finally {
				inputBean.setUtiISyncLog(utiISyncLog);
				BCMessageProducer producer = (BCMessageProducer) ServiceFactory.getService("messageProducer");// 获得Spring管理的bean
				producer.send(inputBean);
			}
		}
}
	//交叉销售PrpDcrossOrg清分
	private void PrpDcrossOrgDataMessageProcess(InputBean inputBean) {
		
		PrpDcrossOrg prpDcrossOrg = inputBean.getPrpDcrossOrg();
		PrpDcrossOrgService prpDcrossOrgService = (PrpDcrossOrgService) ServiceFactory.getService("prpDcrossOrgService");// 获得Spring管理的bean
		List<UtiISyncLog> utiISyncLogList = inputBean.getUtiISyncLogList();
		UtiISyncLog utiISyncLog = null;
		if (utiISyncLogList != null) {
			for (UtiISyncLog utiISyncLogTemp : utiISyncLogList) {
				if (companyType.equals(utiISyncLogTemp.getDestComCode())) {
					utiISyncLog = utiISyncLogTemp;
				}
			}
		}
		if (utiISyncLog == null) {
			utiISyncLog = inputBean.getUtiISyncLog();
		}
		if (utiISyncLog != null) {
			try {
				prpDcrossOrgService.prpDcrossOrgDataMessageProcess(prpDcrossOrg);
				utiISyncLog.setIsSuccess("1");
				utiISyncLog.setReplayTimes(utiISyncLog.getReplayTimes() + 1);
			} catch (Exception e) {
				utiISyncLog.setIsSuccess("0");
				utiISyncLog.setReplayTimes(utiISyncLog.getReplayTimes() + 1);
				utiISyncLog.setErrorMsg(mergeExceptionMessage(e));
			} finally {
				inputBean.setUtiISyncLog(utiISyncLog);
				BCMessageProducer producer = (BCMessageProducer) ServiceFactory.getService("messageProducer");// 获得Spring管理的bean
				producer.send(inputBean);
			}
		}
	}
	//交叉销售PrpDcompanyCheck清分
	private void PrpDcompanyCheckDataMessageProcess(InputBean inputBean) {
		
		PrpDcompanyCheck  prpDcompanyCheck = inputBean.getPrpDcompanyCheck();
		PrpDcrossOrgService prpDcrossOrgService = (PrpDcrossOrgService) ServiceFactory.getService("prpDcrossOrgService");// 获得Spring管理的bean
		List<UtiISyncLog> utiISyncLogList = inputBean.getUtiISyncLogList();
		UtiISyncLog utiISyncLog = null;
		if (utiISyncLogList != null) {
			for (UtiISyncLog utiISyncLogTemp : utiISyncLogList) {
				if (companyType.equals(utiISyncLogTemp.getDestComCode())) {
					utiISyncLog = utiISyncLogTemp;
				}
			}
		}
		if (utiISyncLog == null) {
			utiISyncLog = inputBean.getUtiISyncLog();
		}
		if (utiISyncLog != null) {
			try {
				prpDcrossOrgService.prpDcompanyCheckDataMessageProcess(prpDcompanyCheck);
				utiISyncLog.setIsSuccess("1");
				utiISyncLog.setReplayTimes(utiISyncLog.getReplayTimes() + 1);
			} catch (Exception e) {
				utiISyncLog.setIsSuccess("0");
				utiISyncLog.setReplayTimes(utiISyncLog.getReplayTimes() + 1);
				utiISyncLog.setErrorMsg(mergeExceptionMessage(e));
			} finally {
				inputBean.setUtiISyncLog(utiISyncLog);
				BCMessageProducer producer = (BCMessageProducer) ServiceFactory.getService("messageProducer");// 获得Spring管理的bean
				producer.send(inputBean);
			}
		}
	}
	private String mergeExceptionMessage(Throwable e){
		StringBuffer result = new StringBuffer(256);
		String currException = e.toString();
		result.append(currException);
		if(e.getCause()!=null){
			result.append(" >>>> "+mergeExceptionMessage(e.getCause()));
		} 
		return result.toString();
	}
}
