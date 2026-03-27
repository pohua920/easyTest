/*
 * @(#)DictionaryServiceImpl.java
 *
 * Copyright 2009 sinosoft, Inc. All rights reserved.
 */
package cn.com.sinosoft.dms.webservice.spring;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import cn.com.sinosoft.common.util.DateUtil;
import cn.com.sinosoft.dms.model.IPServiceConfig;
import cn.com.sinosoft.dms.model.PrpDChannelRationClauseKind;
import cn.com.sinosoft.dms.model.PrpDChannelRationEngage;
import cn.com.sinosoft.dms.model.PrpDChannelRationPeriodRate;
import cn.com.sinosoft.dms.model.PrpDRationEngage;
import cn.com.sinosoft.dms.model.PrpDRationEngageId;
import cn.com.sinosoft.dms.model.PrpDRationPeriodRate;
import cn.com.sinosoft.dms.model.PrpDagent;
import cn.com.sinosoft.dms.model.PrpDagentAll;
import cn.com.sinosoft.dms.model.PrpDbank;
import cn.com.sinosoft.dms.model.PrpDclass;
import cn.com.sinosoft.dms.model.PrpDclause;
import cn.com.sinosoft.dms.model.PrpDclauseKind;
import cn.com.sinosoft.dms.model.PrpDclauseKindId;
import cn.com.sinosoft.dms.model.PrpDclauseReport;
import cn.com.sinosoft.dms.model.PrpDclauseReportId;
import cn.com.sinosoft.dms.model.PrpDcode;
import cn.com.sinosoft.dms.model.PrpDcodeCom;
import cn.com.sinosoft.dms.model.PrpDcompany;
import cn.com.sinosoft.dms.model.PrpDcustomer;
import cn.com.sinosoft.dms.model.PrpDdealer;
import cn.com.sinosoft.dms.model.PrpDdriver;
import cn.com.sinosoft.dms.model.PrpDexch;
import cn.com.sinosoft.dms.model.PrpDframe;
import cn.com.sinosoft.dms.model.PrpDitem;
import cn.com.sinosoft.dms.model.PrpDitemType;
import cn.com.sinosoft.dms.model.PrpDkindReport;
import cn.com.sinosoft.dms.model.PrpDmaterialInfo;
import cn.com.sinosoft.dms.model.PrpDnewCode;
import cn.com.sinosoft.dms.model.PrpDnewCodeId;
import cn.com.sinosoft.dms.model.PrpDnewCodeRisk;
import cn.com.sinosoft.dms.model.PrpDnewCodeRiskId;
import cn.com.sinosoft.dms.model.PrpDplane;
import cn.com.sinosoft.dms.model.PrpDport;
import cn.com.sinosoft.dms.model.PrpDration;
import cn.com.sinosoft.dms.model.PrpDrationClauseKind;
import cn.com.sinosoft.dms.model.PrpDrationClauseKindId;
import cn.com.sinosoft.dms.model.PrpDrationCondition;
import cn.com.sinosoft.dms.model.PrpDrisk;
import cn.com.sinosoft.dms.model.PrpDriskClause;
import cn.com.sinosoft.dms.model.PrpDriskClauseKind;
import cn.com.sinosoft.dms.model.PrpDriskClauseKindId;
import cn.com.sinosoft.dms.model.PrpDriskEngage;
import cn.com.sinosoft.dms.model.PrpDriskEngageId;
import cn.com.sinosoft.dms.model.PrpDriskItem;
import cn.com.sinosoft.dms.model.PrpDriskItemId;
import cn.com.sinosoft.dms.model.PrpDriskLimit;
import cn.com.sinosoft.dms.model.PrpDriskLimitId;
import cn.com.sinosoft.dms.model.PrpDriskShortRate;
import cn.com.sinosoft.dms.model.PrpDriskShortRateId;
import cn.com.sinosoft.dms.model.PrpDset;
import cn.com.sinosoft.dms.model.PrpDsetChannel;
import cn.com.sinosoft.dms.model.PrpDship;
import cn.com.sinosoft.dms.model.PrpDstartPlace;
import cn.com.sinosoft.dms.model.PrpDstatistics;
import cn.com.sinosoft.dms.model.PrpDtype;
import cn.com.sinosoft.dms.model.PrpdChannelInfo;
import cn.com.sinosoft.dms.model.UtiCalendar;
import cn.com.sinosoft.dms.service.facade.PrpDagentService;
import cn.com.sinosoft.dms.service.facade.PrpDbankService;
import cn.com.sinosoft.dms.service.facade.PrpDcodeService;
import cn.com.sinosoft.dms.service.facade.PrpDcompanyService;
import cn.com.sinosoft.dms.service.facade.PrpDdealerService;
import cn.com.sinosoft.dms.service.facade.PrpDdriverService;
import cn.com.sinosoft.dms.service.facade.PrpDexchService;
import cn.com.sinosoft.dms.service.facade.PrpDplaneService;
import cn.com.sinosoft.dms.service.facade.PrpDportService;
import cn.com.sinosoft.dms.service.facade.PrpDshipService;
import cn.com.sinosoft.dms.service.facade.PrpDtypeService;
import cn.com.sinosoft.dms.util.BoCopyUtil;
import cn.com.sinosoft.dms.vo.PrpDplan;
import cn.com.sinosoft.dms.webservice.facade.DictionaryService;
import cn.com.sinosoft.inf.dict.server.common.DictPage;
import cn.com.sinosoft.inf.dict.server.common.ServiceInfoConst;
import cn.com.sinosoft.inf.dict.xmlmsg.agentSYN.AgentSYNObj;
import cn.com.sinosoft.inf.dict.xmlmsg.codetranslate.TranslateVO;
import cn.com.sinosoft.inf.dict.xmlmsg.getCode.PrpDcodeInfo;
import cn.com.sinosoft.inf.dict.xmlmsg.getPlanInfo.TranslateObj;
import cn.com.sinosoft.inf.dict.xmlmsg.productSYN.ClassObj;
import cn.com.sinosoft.inf.dict.xmlmsg.productSYN.ClauseReportObj;
import cn.com.sinosoft.inf.dict.xmlmsg.productSYN.FrameObj;
import cn.com.sinosoft.inf.dict.xmlmsg.productSYN.ProductSetObj;
import cn.com.sinosoft.inf.dict.xmlmsg.productSYN.RationObj;
import cn.com.sinosoft.inf.dict.xmlmsg.productSYN.RiskObj;
import cn.com.sinosoft.saa.service.facade.CodeService;

import com.sinosoft.dmsdriver.model.PrpDcustomerIdv;
import com.sinosoft.dmsdriver.model.PrpDitemShip;


/**
 * 
 * 
 * @author 毋雷
 * @version 1.0 2009-7-9
 */
/**
 * @author Administrator
 * 
 */
public class DictionaryServiceImpl extends GenericDaoHibernate implements
		DictionaryService {
	private static Log logger = LogFactory.getLog(DictionaryServiceImpl.class);
	private CodeService codeService;
	private PrpDexchService prpDexchService;
	private PrpDcompanyService prpDcompanyService;
	private PrpDagentService prpDagentService;
	private PrpDbankService prpDbankService;
	private PrpDdealerService prpDdealerService;
	private PrpDshipService prpDshipService;
	private PrpDportService prpDportService;
	private PrpDplaneService prpDplaneService;
	private PrpDdriverService prpDdriverService;
	private PrpDcodeService prpDcodeService;
	private PrpDtypeService prpDtypeService;

	public PrpDtypeService getPrpDtypeService() {
		return prpDtypeService;
	}

	public void setPrpDtypeService(PrpDtypeService prpDtypeService) {
		this.prpDtypeService = prpDtypeService;
	}

	public PrpDcodeService getPrpDcodeService() {
		return prpDcodeService;
	}

	public void setPrpDcodeService(PrpDcodeService prpDcodeService) {
		this.prpDcodeService = prpDcodeService;
	}

	public CodeService getCodeService() {
		return codeService;
	}

	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}

	public PrpDexchService getPrpDexchService() {
		return prpDexchService;
	}

	public void setPrpDexchService(PrpDexchService prpDexchService) {
		this.prpDexchService = prpDexchService;
	}

	public PrpDagentService getPrpDagentService() {
		return prpDagentService;
	}

	public void setPrpDagentService(PrpDagentService prpDagentService) {
		this.prpDagentService = prpDagentService;
	}

	public PrpDbankService getPrpDbankService() {
		return prpDbankService;
	}

	public void setPrpDbankService(PrpDbankService prpDbankService) {
		this.prpDbankService = prpDbankService;
	}

	public PrpDdealerService getPrpDdealerService() {
		return prpDdealerService;
	}

	public void setPrpDdealerService(PrpDdealerService prpDdealerService) {
		this.prpDdealerService = prpDdealerService;
	}

	public PrpDshipService getPrpDshipService() {
		return prpDshipService;
	}

	public void setPrpDshipService(PrpDshipService prpDshipService) {
		this.prpDshipService = prpDshipService;
	}

	public PrpDportService getPrpDportService() {
		return prpDportService;
	}

	public void setPrpDportService(PrpDportService prpDportService) {
		this.prpDportService = prpDportService;
	}

	public PrpDplaneService getPrpDplaneService() {
		return prpDplaneService;
	}

	public void setPrpDplaneService(PrpDplaneService prpDplaneService) {
		this.prpDplaneService = prpDplaneService;
	}

	public PrpDdriverService getPrpDdriverService() {
		return prpDdriverService;
	}

	public void setPrpDdriverService(PrpDdriverService prpDdriverService) {
		this.prpDdriverService = prpDdriverService;
	}

	public PrpDcompanyService getPrpDcompanyService() {
		return prpDcompanyService;
	}

	public void setPrpDcompanyService(PrpDcompanyService prpDcompanyService) {
		this.prpDcompanyService = prpDcompanyService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.com.sinosoft.dms.webservice.facade.DictionaryService#codeTypeTranslate
	 * (java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public String codeTypeTranslate(String systemCode, String codeType) {
		String result = codeService.codeTypeTranslate(systemCode, codeType);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.com.sinosoft.dms.webservice.facade.DictionaryService#exchange(java
	 * .lang.String, java.util.Date, java.lang.String, java.lang.String, double)
	 */
	public double exchange(String systemCode, Date currDate,
			String baseCurrency, String exchCurrency, double amount) {
		logger.debug("★接口 exchange 开始");
		PrpDexch prpDexch = prpDexchService.getLastPrpDexch(currDate,
				baseCurrency, exchCurrency);
		double result = -1;
		if (prpDexch != null && prpDexch.getExchRate() != null) {
			// 兑换金额乘以兑换率
			result = prpDexch.getExchRate().multiply(new BigDecimal(amount))
					.doubleValue();
			logger.debug("☆接口 exchange 返回结果：" + result);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.com.sinosoft.dms.webservice.facade.DictionaryService#getSubComCode
	 * (java.lang.String, java.lang.String)
	 */
	public List<PrpDcompany> getSubCompany(String systemCode, String comCode) {
		logger.debug("★ getSubComCode 开始,systemCode = " + systemCode);
		logger.debug("comcode = " + comCode);
		List<PrpDcompany> result = prpDcompanyService.getSubCode1(comCode);
		logger.debug("☆ getSubComCode 返回结果：" + result);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.com.sinosoft.dms.webservice.facade.DictionaryService#getAllSubComCode
	 * (java.lang.String, java.lang.String, java.lang.String)
	 */
	public List<PrpDcompany> getAllSubCompany(String systemCode, String comCode) {
		logger.debug("★ getAllSubComCode 开始,systemCode = " + systemCode);
		logger.debug("comcode = " + comCode);
		List<PrpDcompany> result = prpDcompanyService.getAllSubCompany(comCode);
		logger.debug("☆ getAllSubComCode 返回结果：" + result);
		return result;
	}

	public int getCount(String systemCode, String tableName, String condition) {
		int result = 0;
		logger.debug("★ getCount 开始,systemCode = " + systemCode);
		// 条件字符串去掉 where 关键字
		int index = condition.toLowerCase().indexOf("where");
		if (index >= 0) {
			condition = condition.substring(index + 5).trim();
		}
		StringBuffer hql = new StringBuffer(256);
		hql.append("select count(*) from " + tableName + " a where ");
		if ("".equals(condition) || condition == null) {
			hql.append("1=1");
		} else {
			hql.append(condition);
		}
		List list = findByHql(hql.toString());
		if (list.size() > 0) {
			long o = (Long) list.get(0);
			result = (int) o;
		}
		logger.debug("☆ getCount 返回结果：" + result);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.com.sinosoft.dms.webservice.facade.DictionaryService#getListByCondition
	 * (java.lang.String, java.lang.String, java.lang.String)
	 */
	public List getListByCondition(String systemCode, String tableName,
			String condition) {
		logger.debug("★ getListByCondition 开始,systemCode = " + systemCode);
		// 条件字符串去掉 where 关键字
		int index = condition.toLowerCase().trim().indexOf("where");
		if (index >= 0) {
			condition = condition.substring(index + 5).trim();
		}
		StringBuffer hql = new StringBuffer(256);
		hql.append("from " + tableName + " where " + condition);
		List result = findByHql(hql.toString());
		logger.debug("☆ getListByCondition 返回结果：" + result);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.com.sinosoft.dms.webservice.facade.DictionaryService#getPrpDagent(
	 * java.lang.String, java.lang.String)
	 */
	public PrpDagent getPrpDagent(String systemCode, String agentCode) {
		logger.debug("★ getPrpDagent 开始,systemCode = " + systemCode);
		PrpDagent result = prpDagentService.findByPrimaryKey1(agentCode);
		logger.debug("☆ getPrpDagent 返回结果：" + result);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.com.sinosoft.dms.webservice.facade.DictionaryService#getPrpDbank(java
	 * .lang.String, java.lang.String)
	 */
	public PrpDbank getPrpDbank(String systemCode, String bankCode) {
		logger.debug("★ getPrpDbank 开始,systemCode = " + systemCode);
		PrpDbank result = prpDbankService.findByPrimaryKey1(bankCode);
		logger.debug("☆ getPrpDbank 返回结果：" + result);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.com.sinosoft.dms.webservice.facade.DictionaryService#getPrpDcode(java
	 * .lang.String, java.lang.String, java.lang.String)
	 */
	public PrpDnewCode getPrpDcode(String systemCode, String codeCode,
			String codeType) {
		logger.debug("★ getPrpDcode 开始,systemCode = " + systemCode);
		PrpDnewCodeId id = new PrpDnewCodeId();
		id.setCodeCode(codeCode);
		id.setCodeType(codeType);
		PrpDnewCode result = prpDcodeService.findByPrimaryKey1(id);
		logger.debug("☆ getPrpDcode 返回结果：" + result);
		return result;
	}

	/**
	 * 查询旧prpDcode表
	 */
	public PrpDcode getPrpDoldCode(String systemCode, Map values) {
		String codeType = (String) values.get("codeType");
		String codeCode = (String) values.get("codeCode");
		codeType = codeType == null ? "" : codeType;
		codeCode = codeCode == null ? "" : codeCode;
		StringBuffer hql = new StringBuffer(64);
		hql.append("from PrpDcode where codeType = ? and codeCode = ?");
		List result = super.findByHql(hql.toString(), codeType, codeCode);
		if (result != null && result.size() > 0) {
			return (PrpDcode) result.get(0);
		} else {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.com.sinosoft.dms.webservice.facade.DictionaryService#getPrpDcompany
	 * (java.lang.String, java.lang.String)
	 */
	public PrpDcompany getPrpDcompany(String systemCode, String comCode) {
		logger.debug("★ getPrpDcompany 开始,systemCode = " + systemCode);
		PrpDcompany result = prpDcompanyService.getPrpDcompany1(comCode);
		logger.debug("☆ getPrpDcompany 返回结果：" + result);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.com.sinosoft.dms.webservice.facade.DictionaryService#getPrpDdealer
	 * (java.lang.String, java.lang.String)
	 */
	public PrpDdealer getPrpDdealer(String systemCode, String dealerCode) {
		logger.debug("★ getPrpDdealer 开始,systemCode = " + systemCode);
		PrpDdealer result = prpDdealerService.findByPrimaryKey1(dealerCode);
		logger.debug("☆ getPrpDdealer 返回结果：" + result);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.com.sinosoft.dms.webservice.facade.DictionaryService#getPrpDdriver
	 * (java.lang.String, java.lang.String)
	 */
	public PrpDdriver getPrpDdriver(String systemCode, String drivingLicenseNo) {
		logger.debug("★ getPrpDdriver 开始,systemCode = " + systemCode);
		PrpDdriver result = prpDdriverService
				.findByPrimaryKey(drivingLicenseNo);
		logger.debug("☆ getPrpDdriver 返回结果：" + result);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.com.sinosoft.dms.webservice.facade.DictionaryService#getPrpDexch(java
	 * .lang.String, java.util.Date, java.lang.String, java.lang.String)
	 */
	public PrpDexch getPrpDexch(String systemCode, Date exchDate,
			String baseCurrency, String exchCurrency) {
		// PrpDexchId id = new PrpDexchId();
		// id.setBaseCurrency(baseCurrency);
		// id.setExchCurrency(exchCurrency);
		// id.setExchDate(exchDate);
		// PrpDexch result = prpDexchService.findByPrimaryKey(id);
		PrpDexch result = prpDexchService.getLastPrpDexchs(exchDate,
				baseCurrency, exchCurrency);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.com.sinosoft.dms.webservice.facade.DictionaryService#getPrpDplane(
	 * java.lang.String, java.lang.String)
	 */
	public PrpDplane getPrpDplane(String systemCode, String licenceNo) {
		logger.debug("★ getPrpDplane 开始,systemCode = " + systemCode);
		PrpDplane result = prpDplaneService.findByPrimaryKey1(licenceNo);
		logger.debug("☆ getPrpDplane 返回结果：" + result);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.com.sinosoft.dms.webservice.facade.DictionaryService#getPrpDport(java
	 * .lang.String, java.lang.String)
	 */
	public PrpDport getPrpDport(String systemCode, String portNo) {
		logger.debug("★ getPrpDport 开始,systemCode = " + systemCode);
		PrpDport result = prpDportService.findByPrimaryKey1(portNo);
		logger.debug("☆ getPrpDport 返回结果：" + result);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.com.sinosoft.dms.webservice.facade.DictionaryService#getPrpDship(java
	 * .lang.String, java.lang.String)
	 */
	public PrpDship getPrpDship(String systemCode, String shipCode) {
		logger.debug("★ getPrpDship 开始,systemCode = " + systemCode);
		PrpDship result = prpDshipService.findByPrimaryKey1(shipCode);
		logger.debug("☆ getPrpDship 返回结果：" + result);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.com.sinosoft.dms.webservice.facade.DictionaryService#getPrpDtype(java
	 * .lang.String, java.lang.String)
	 */
	public PrpDtype getPrpDtype(String systemCode, String codeType) {
		logger.debug("★ getPrpDtype 开始,systemCode = " + systemCode);
		PrpDtype result = prpDtypeService.findByPrimaryKey1(codeType);
		logger.debug("☆ getPrpDtype 返回结果：" + result);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.com.sinosoft.dms.webservice.facade.DictionaryService#getSubCode(java
	 * .lang.String, java.lang.String, java.lang.String) 添加validStatus校验
	 */
	public List getSubCode(String systemCode, String codeType, String codeCode) {
		List list = prpDcodeService.getSubCode(codeType, codeCode);
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.com.sinosoft.dms.webservice.facade.DictionaryService#getUpperCode(
	 * java.lang.String, java.lang.String, java.lang.String)
	 */
	public PrpDnewCode getUpperCode(String systemCode, String codeType,
			String codeCode) {
		PrpDnewCodeId id = new PrpDnewCodeId();
		id.setCodeCode(codeCode);
		id.setCodeType(codeType);
		String upCode = null;

		try {
			upCode = prpDcodeService.getuplevel(id);
			if (upCode == null) {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		id.setCodeCode(upCode);
		PrpDnewCode prpDcode = prpDcodeService.findByPrimaryKey(id);
		logger.debug("☆ getUpperCode 返回结果：" + prpDcode);
		return prpDcode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.com.sinosoft.dms.webservice.facade.DictionaryService#getUpperComCode
	 * (java.lang.String, java.lang.String)
	 */
	public PrpDcompany getUpperPrpDcompany(String systemCode, String comCode) {
		logger.debug("★ getUpperComCode 开始,systemCode = " + systemCode);
		PrpDcompany prpDcompany = prpDcompanyService.getPrpDcompany(comCode);
		if (prpDcompany != null) {
			prpDcompany = prpDcompanyService.getUpprpDcompany(prpDcompany);
		}
		return prpDcompany;
	}

	/**
	 * 新代码翻译接口
	 */
	public String translateCode(String systemCode, String codeType,
			String codeCode, String language) {
		String result = codeService.translateCode(systemCode, codeType.trim(),
				codeCode.trim(), "1", language);
		return result;
	}

	/**
	 * 新老代码翻译接口
	 * 
	 * @param systemCode
	 * @param codeType
	 * @param codeCode
	 * @param codeFlag
	 *            1:新代码 0:老代码
	 * @param language
	 * @return
	 */
	public String translateCode(String systemCode, String codeType,
			String codeCode, String codeFlag, String language) {
		String result = codeService.translateCode(systemCode, codeType.trim(),
				codeCode.trim(), codeFlag, language);
		return result;
	}

	public List<PrpDnewCode> getPrpDcodeBytype(String systemCode,
			String codeType) {
		if (null != codeType && !"".equals(codeType)) {
			String hql = "from PrpDnewCode a where a.id.codeType = '"
					+ codeType + "' and a.validStatus='1'";
			List list = super.findByHql(hql);
			return list;
		} else {
			return null;
		}
	}

	public List<PrpDnewCode> findCodeByCondition(String systemCode,
			String condition) {
		StringBuffer hql = new StringBuffer(256);
		hql.append("from PrpDnewCode a where a.validStatus = 1 and ");
		if (condition == null || "".equals(condition)) {
			hql.append("1=1 ");
		} else {
			hql.append(condition);
		}

		List list = super.findByHql(hql.toString());
		return list;
	}

	// public String getCodeLevel(String systemCode, String codeType,
	// String codeCode) {
	// String codeLevel = prpDcodeService.getCodeLevel(systemCode, codeType,
	// codeCode);
	// return codeLevel;
	// }

	public List<PrpDclass> findPrpDclassById(String systemcode, String classcode) {
		String hql = "from PrpDclass a where a.validInd = 1 and a.classCode = '"
				+ classcode + "'";
		List list = super.findByHql(hql);
		return list;
	}

	public List<PrpDframe> findPrpDframeById(String systemcode, String framecode) {
		String hql = "from PrpDframe a where a.validInd = 1 and a.frameCode = '"
				+ framecode + "'";
		List list = super.findByHql(hql);
		return list;
	}

	public List<PrpDitem> findPrpDitemById(String systemcode, String itemcode) {
		String hql = "from PrpDitem a where a.validInd = 1 a.itemCode = '"
				+ itemcode + "'";
		List list = super.findByHql(hql);
		return list;
	}

	public List<PrpDitemType> findPrpDitemTypeById(String systemcode,
			String itemtype) {
		String hql = "from PrpDitemType a where a.validInd = 1 and a.itemType = '"
				+ itemtype + "'";
		List list = super.findByHql(hql);
		return list;
	}

	public List<PrpDmaterialInfo> findPrpDmaterialInfoById(String systemcode,
			String materialid) {
		String hql = "from PrpDmaterialInfo a where a.validInd = 1 and a.materialID = '"
				+ materialid + "'";
		List list = super.findByHql(hql);
		return list;
	}

	public List<PrpDplan> findPrpDplanById(String systemcode, String plancode) {
		String hql = "from PrpDplan a where a.validInd = 1 and a.planCode = '"
				+ plancode + "'";
		List list = super.findByHql(hql);
		return list;
	}

	public List<PrpDrisk> findPrpDriskById(String systemcode, String riskcode) {
		String hql = "from PrpDrisk a where a.validInd = 1 and a.riskCode = '"
				+ riskcode + "'";
		List list = super.findByHql(hql);
		return list;
	}

	public List<PrpDcompany> findCompanyByCondition(String systemcode,
			String condition) {
		StringBuffer hql = new StringBuffer(256);
		hql.append("from PrpDcompany a where a.validStatus = 1 and ");
		if ("".equals(condition) || condition == null) {
			hql.append(" 1=1");
		} else {
			hql.append(condition);
		}
		List list = super.findByHql(hql.toString());

		return list;
	}

	public List<PrpDriskClauseKind> findPrpDriskClauseKindById(
			String systemcode, PrpDriskClauseKindId prpDriskClauseKindId) {
		String hql = "from PrpDriskClauseKind a a.validInd = 1 and where a.id.riskCode = '"
				+ prpDriskClauseKindId.getRiskCode()
				+ "' and a.id.clauseKindID = '"
				+ prpDriskClauseKindId.getClauseCode() + "'";
		List list = super.findByHql(hql);
		return list;
	}

	public List<PrpDriskEngage> findPrpDriskEngageById(String systemcode,
			PrpDriskEngageId prpDriskEngageId) {
		String hql = "from PrpDriskEngage a where a.validInd = 1 and a.id.riskCode = '"
				+ prpDriskEngageId.getRiskCode()
				+ "' and a.id.engageCode = '"
				+ prpDriskEngageId.getEngageCode() + "'";
		List list = super.findByHql(hql);
		return list;
	}

	public List<PrpDriskItem> findPrpDriskItemById(String systemcode,
			PrpDriskItemId prpDriskItemId) {
		String hql = "from PrpDriskItem a where a.validInd = 1 and a.id.riskCode = '"
				+ prpDriskItemId.getRiskCode()
				+ "' and a.id.itemCode = '"
				+ prpDriskItemId.getItemCode() + "'";
		List list = super.findByHql(hql);
		return list;
	}

	public List<PrpDriskLimit> findPrpDriskLimitById(String systemcode,
			PrpDriskLimitId prpDriskLimitId) {
		String hql = "from PrpDriskLimit a where a.validInd = 1 and a.id.riskCode = '"
				+ prpDriskLimitId.getRiskCode()
				+ "' and a.id.serialNo = '"
				+ prpDriskLimitId.getSerialNo()
				+ "' and a.id.limitCode= '"
				+ prpDriskLimitId.getLimitCode() + "'";
		List list = super.findByHql(hql);
		return list;
	}

	public List<PrpDriskShortRate> findPrpDriskShortRateById(String systemcode,
			PrpDriskShortRateId prpDriskShortRateId) {
		String hql = "from PrpDriskShortRate a where a.validInd = 1 and a.id.riskCode = '"
				+ prpDriskShortRateId.getRiskCode()
				+ "' and a.id.serialNo = '"
				+ prpDriskShortRateId.getSerialNo()
				+ "' and a.id.shortRateID= '"
				+ prpDriskShortRateId.getShortRateID() + "'";
		List list = super.findByHql(hql);
		return list;
	}

	public List<PrpDclass> findPrpDclassByCondition(String systemcode,
			String condition) {
		StringBuffer hql = new StringBuffer(256);
		hql.append("from PrpDclass a where a.validInd = 1 and ");
		if ("".equals(condition) || condition == null) {
			hql.append(" 1=1");
		} else {
			hql.append(condition);
		}
		List list = super.findByHql(hql.toString());
		return list;
	}

	public List<PrpDframe> findPrpDframeByCondition(String systemcode,
			String condition) {
		StringBuffer hql = new StringBuffer(256);
		hql.append("from PrpDframe a where a.validInd = 1 and ");
		if ("".equals(condition) || condition == null) {
			hql.append(" 1=1");
		} else {
			hql.append(condition);
		}
		List list = super.findByHql(hql.toString());
		return list;
	}

	public List<PrpDitem> findPrpDitemByCondition(String systemcode,
			String condition) {
		StringBuffer hql = new StringBuffer(256);
		hql.append("from PrpDitem a where a.validInd = 1 and ");
		if ("".equals(condition) || condition == null) {
			hql.append(" 1=1");
		} else {
			hql.append(condition);
		}
		List list = super.findByHql(hql.toString());
		return list;
	}

	public List<PrpDitemType> findPrpDitemTypeByCondition(String systemcode,
			String condition) {
		StringBuffer hql = new StringBuffer(256);
		hql.append("from PrpDitemType a where a.validInd = 1 and ");
		if ("".equals(condition) || condition == null) {
			hql.append(" 1=1");
		} else {
			hql.append(condition);
		}
		List list = super.findByHql(hql.toString());
		return list;
	}

	public List<PrpDmaterialInfo> findPrpDmaterialInfoByCondition(
			String systemcode, String condition) {
		StringBuffer hql = new StringBuffer(256);
		hql.append("from PrpDmaterialInfo a where a.validInd = 1 and ");
		if ("".equals(condition) || condition == null) {
			hql.append(" 1=1");
		} else {
			hql.append(condition);
		}
		List list = super.findByHql(hql.toString());
		return list;
	}

	// public List<PrpDplan> findPrpDplanByCondition(String systemcode,
	// String condition) {
	// StringBuffer hql = new StringBuffer(256);
	// hql.append("from PrpDplan a where a.validInd = 1 and ");
	// if ("".equals(condition) || condition == null) {
	// hql.append(" 1=1");
	// } else {
	// hql.append(condition);
	// }
	// List list = super.findByHql(hql.toString());
	// return list;
	// }

	public List<PrpDrisk> findPrpDriskByCondition(String systemcode,
			String condition) {
		StringBuffer hql = new StringBuffer(256);
		hql.append("from PrpDrisk a where a.validInd = 1 and ");
		if ("".equals(condition) || condition == null) {
			hql.append(" 1=1");
		} else {
			hql.append(condition);
		}
		List list = super.findByHql(hql.toString());
		return list;
	}

	public List<PrpDriskClauseKind> findPrpDriskClauseKindByCondition(
			String systemcode, String condition) {
		StringBuffer hql = new StringBuffer(256);
		hql.append("from PrpDriskClauseKind a where a.validInd and ");
		if ("".equals(condition) || condition == null) {
			hql.append(" 1=1");
		} else {
			hql.append(condition);
		}

		List list = super.findByHql(hql.toString());
		return list;
	}

	public List<PrpDriskEngage> findPrpDriskEngageByCondition(
			String systemcode, String condition) {
		StringBuffer hql = new StringBuffer(256);
		hql.append("from PrpDriskEngage a where a.validInd = 1 and ");
		if ("".equals(condition) || condition == null) {
			hql.append(" 1=1");
		} else {
			hql.append(condition);
		}

		List list = super.findByHql(hql.toString());
		return list;
	}

	public List<PrpDriskItem> findPrpDriskItemByCondition(String systemcode,
			String condition) {
		StringBuffer hql = new StringBuffer(256);
		hql.append("from PrpDriskItem a where a.validInd = 1 and ");
		if ("".equals(condition) || condition == null) {
			hql.append(" 1=1");
		} else {
			hql.append(condition);
		}

		List list = super.findByHql(hql.toString());
		return list;
	}

	public List<PrpDriskLimit> findPrpDriskLimitByCondition(String systemcode,
			String condition) {

		StringBuffer hql = new StringBuffer(256);
		hql.append("from PrpDriskLimit a where a.validInd = 1 and ");
		if ("".equals(condition) || condition == null) {
			hql.append(" 1=1");
		} else {
			hql.append(condition);
		}
		List list = super.findByHql(hql.toString());
		return list;
	}

	public List<PrpDriskShortRate> findPrpDriskShortRateByCondition(
			String systemcode, String condition) {
		StringBuffer hql = new StringBuffer(256);
		hql.append("from PrpDriskShortRate a where a.validInd = 1 and ");
		if ("".equals(condition) || condition == null) {
			hql.append(" 1=1");
		} else {
			hql.append(condition);
		}

		List list = super.findByHql(hql.toString());
		return list;
	}

	public List<PrpDcodeCom> findPrpDcodeComByCondition(String systemcode,
			String condition) {
		StringBuffer hql = new StringBuffer(256);
		hql.append("from PrpDcodeCom a where a.validStatus = 1 and ");
		if ("".equals(condition) || condition == null) {
			hql.append(" 1=1");
		} else {
			hql.append(condition);
		}
		List list = super.findByHql(hql.toString());
		return list;
	}

	/*
	 * 通过codeType获得prpDcode
	 */
	public DictPage getCode(String systemCode, String codeType,String codeFlag, int pageNo, int pageSize) throws Exception {
		DictPage dictPage = new DictPage();
		List list = new ArrayList();
		PrpDcodeInfo prpDcodeInfo = null;

		StringBuffer hql = new StringBuffer(128);
		if (!"2".equals(codeFlag)) {
			hql.append("from PrpDnewCode a where a.validStatus=1");
			hql.append(" and a.id.codeType = '").append(codeType).append("'");
			PrpDnewCode newCode = null;
			if (pageNo == 0 || pageSize == 0) {
				list = super.findByHql(hql.toString());
				List temp = new ArrayList();
				for (int i = 0; i < list.size(); i++) {
					prpDcodeInfo = new PrpDcodeInfo();
					newCode = (PrpDnewCode) list.get(i);
					prpDcodeInfo.setId(new PrpDnewCodeId());
					prpDcodeInfo.getId().setCodeType(
							newCode.getId().getCodeType());
					prpDcodeInfo.getId().setCodeCode(
							newCode.getId().getCodeCode());
					prpDcodeInfo.setCodeCName(newCode.getCodeCName());
					prpDcodeInfo.setCodeEName(newCode.getCodeEName());
					prpDcodeInfo.setNewCodeCode(newCode.getNewCodeCode());
					prpDcodeInfo.setOldCodeType(newCode.getOldCodeType());
					prpDcodeInfo.setOldCodeCode(newCode.getOldCodeCode());
					prpDcodeInfo.setValidStatus(newCode.getValidStatus());
					prpDcodeInfo.setFlag(newCode.getFlag());
					temp.add(prpDcodeInfo);
				}
				dictPage.setData(temp);
			} else {
				Page page = super.findByHql(hql.toString(), pageNo, pageSize);
				for (int i = 0; i < page.getResult().size(); i++) {
					prpDcodeInfo = new PrpDcodeInfo();
					newCode = (PrpDnewCode) page.getResult().get(i);
					prpDcodeInfo.setId(new PrpDnewCodeId());
					prpDcodeInfo.getId().setCodeType(
							newCode.getId().getCodeType());
					prpDcodeInfo.getId().setCodeCode(
							newCode.getId().getCodeCode());
					prpDcodeInfo.setCodeCName(newCode.getCodeCName());
					prpDcodeInfo.setCodeEName(newCode.getCodeEName());
					prpDcodeInfo.setNewCodeCode(newCode.getNewCodeCode());
					prpDcodeInfo.setOldCodeCode(newCode.getOldCodeCode());
					prpDcodeInfo.setValidStatus(newCode.getValidStatus());
					prpDcodeInfo.setFlag(newCode.getFlag());
					list.add(prpDcodeInfo);
				}
				dictPage.setData(list);
				dictPage.setPageNo(pageNo);
				dictPage.setPageSize(pageSize);
				dictPage.setPageCount(page.getTotalPageCount());
				dictPage.setTotalRecordCount(page.getTotalCount());
			}
		} else {
			hql.append("from PrpDcode a where a.validStatus=1");
			hql.append(" and a.id.codeType = '").append(codeType).append("'");
			PrpDcode code = new PrpDcode();
			if (pageNo == 0 || pageSize == 0) {
				list = super.findByHql(hql.toString());
				List temp = new ArrayList();
				for (int i = 0; i < list.size(); i++) {
					prpDcodeInfo = new PrpDcodeInfo();
					code = (PrpDcode) list.get(i);
					prpDcodeInfo.setId(new PrpDnewCodeId());
					prpDcodeInfo.getId()
							.setCodeType(code.getId().getCodeType());
					prpDcodeInfo.getId()
							.setCodeCode(code.getId().getCodeCode());
					prpDcodeInfo.setCodeCName(code.getCodeCName());
					prpDcodeInfo.setCodeEName(code.getCodeEName());
					prpDcodeInfo.setNewCodeCode(code.getNewCodeCode());
					prpDcodeInfo.setValidStatus(code.getValidStatus());
					prpDcodeInfo.setFlag(code.getFlag());
					// BeanUtils.copyProperties(prpDcodeInfo, list.get(i));
					temp.add(prpDcodeInfo);
				}
				dictPage.setData(temp);
			} else {
				Page page = super.findByHql(hql.toString(), pageNo, pageSize);
				List templist = page.getResult();
				for (int i = 0; i < templist.size(); i++) {
					prpDcodeInfo = new PrpDcodeInfo();
					code = (PrpDcode) templist.get(i);
					prpDcodeInfo.setId(new PrpDnewCodeId());
					prpDcodeInfo.getId()
							.setCodeType(code.getId().getCodeType());
					prpDcodeInfo.getId()
							.setCodeCode(code.getId().getCodeCode());
					prpDcodeInfo.setCodeCName(code.getCodeCName());
					prpDcodeInfo.setCodeEName(code.getCodeEName());
					prpDcodeInfo.setNewCodeCode(code.getNewCodeCode());
					prpDcodeInfo.setValidStatus(code.getValidStatus());
					prpDcodeInfo.setFlag(code.getFlag());
					list.add(prpDcodeInfo);
				}
				dictPage.setData(list);
				dictPage.setPageNo(pageNo);
				dictPage.setPageSize(pageSize);
				dictPage.setPageCount(page.getTotalPageCount());
				dictPage.setTotalRecordCount(page.getTotalCount());
			}
		}
		return dictPage;
	}

	public DictPage getCompany(String systemCode, String condition, int pageNo,
			int pageSize) throws Exception {
		DictPage dictPage = new DictPage();
		List list = new ArrayList();
		StringBuffer hql = new StringBuffer(256);
		hql.append("from PrpDcompany a where a.validStatus = 1 and ");
		if ("".equals(condition) || condition == null) {
			hql.append(" 1=1");
		} else {
			hql.append(condition);
		}
		if (pageNo == 0 || pageSize == 0) {
			int count = getCount("dms", "PrpDcompany", condition);
			if (count > 1000) {
				logger.error("系统" + systemCode + "查询记录数过大，不予返回！");
				throw new Exception("系统" + systemCode + "调用接口"
						+ ServiceInfoConst.GETCOMPANY + "查询记录数过大，请缩小查询范围");
			} else {
				list = super.findByHql(hql.toString());
			}
			dictPage.setData(list);
		} else {
			Page page = super.findByHql(hql.toString(), pageNo, pageSize);
			list = page.getResult();
			dictPage.setData(list);
			dictPage.setPageNo(pageNo);
			dictPage.setPageSize(pageSize);
			dictPage.setPageCount(page.getTotalPageCount());
			dictPage.setTotalRecordCount(page.getTotalCount());
		}
		return dictPage;
	}

	public DictPage getCodeWithCom(String systemCode, String codeType,
			String codeCode, String codeCName, String withCode, int pageNo,
			int pageSize) {
		DictPage dictPage = new DictPage();
		int count = getCount("dms", "PrpDcodeCom", "codeType = '" + codeType
				+ "' and comCode = '" + withCode + "'");
		if (count > 0) {
			StringBuffer hql = new StringBuffer(256);
			hql.append(
					"select a.id.codeCode,a.codeCName from PrpDnewCode a,PrpDcodeCom b where b.id.comCode='")
					.append(withCode)
					.append("' and b.id.codeType = '")
					.append(codeType)
					.append("' and a.id.codeCode=b.id.codeCode and a.id.codeType=b.id.codeType")
					.append(" and a.id.codeCode like '")
					.append(codeCode + "%'").append(" and a.codeCName like '")
					.append(codeCName + "%'").append(" and a.validStatus = 1");
			if (pageNo == 0 || pageSize == 0) {
				List list = super.findByHql(hql.toString());
				dictPage.setData(list);
			} else {
				Page page = super.findByHql(hql.toString(), pageNo, pageSize);
				dictPage.setData(page.getResult());
				dictPage.setPageCount(page.getTotalPageCount());
				dictPage.setPageNo(pageNo);
				dictPage.setPageSize(pageSize);
				dictPage.setTotalRecordCount(page.getTotalCount());
			}
		} else {
			StringBuffer hql = new StringBuffer(256);
			hql.append(
					"select a.id.codeCode,a.codeCName from PrpDnewCode a where ")
					.append("a.id.codeType = '").append(codeType)
					.append("' and a.id.codeCode like '").append(codeCode)
					.append("%' and a.codeCName like '")
					.append(codeCName + "%' and a.validStatus = 1");
			if (pageNo == 0 || pageSize == 0) {
				List list = super.findByHql(hql.toString());
				dictPage.setData(list);
			} else {
				Page page = super.findByHql(hql.toString(), pageNo, pageSize);
				dictPage.setData(page.getResult());
				dictPage.setPageCount(page.getTotalPageCount());
				dictPage.setPageNo(pageNo);
				dictPage.setPageSize(pageSize);
				dictPage.setTotalRecordCount(page.getTotalCount());
			}
		}
		return dictPage;
	}

	/**
	 * @param systemCode
	 * @param codeType
	 * @param codeCode
	 *            代码 模糊查询
	 * @param codeCName
	 *            代码中文名 模糊查询
	 * @param riskCode
	 * @param IgnoreCode
	 *            忽略代码 在逻辑中排除传入的代码，入参可传入多个代码，用","分隔
	 * @param extraCodeCode
	 *            新添加extraCodeCode参数,用来查询原逻辑有效的数据 + 查询extraCodeCode不管是否有效的数据
	 * @param pageNO
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public DictPage getCodeWithRisk(String systemCode, String codeType,
			String codeCode, String codeName, String withCode,
			String ignoreCode, String extraCodeCode, String upperCode,
			int pageNo, int pageSize) {
		DictPage dictPage = new DictPage();
		// int count = getCount("dms", "PrpDnewCodeRisk", "codeType = '" +
		// codeType
		// + "' and riskCode in ('PUB','" + withCode + "')");
		//modify by fengyang 20140519 start reason:增加对船舶种类的判断
		String codeName1="";
		String disPlayNo="";
		if(codeType.equals("ShipType")){
			 codeName1=codeName;
			 codeName="";
			if(codeName1.equals("A")){
				disPlayNo="1";
			}
			if(codeName1.equals("B")){
				disPlayNo="2";
			}
			if(codeName1.equals("C")){
				disPlayNo="3";
			}
			if(codeName1.equals("D")){
				disPlayNo="4";
			}
			if(codeName1.equals("E")){
				disPlayNo="5";
			}
		}
		if(codeType.equals("DangerousCode") || codeType.equals("constructType")){
	        codeName1=codeName;
	        codeName="";
	       if(codeName1.equals("01")){
	           disPlayNo="1.01";
	       }
	       if(codeName1.equals("02")){
	           disPlayNo="1.02";
	       }
	       if(codeName1.equals("03")){
	           disPlayNo="1.03";
	       }
	       if(codeName1.equals("04")){
	           disPlayNo="1.04";
	       }
	       if(codeName1.equals("05")){
	           disPlayNo="1.05";
	       }
	       if(codeName1.equals("06")){
	           disPlayNo="1.06";
	       }
	       if(codeName1.equals("07")){
	           disPlayNo="1.07";
	       }
	       if(codeName1.equals("08")){
	           disPlayNo="1.08";
	       }
	       if(codeName1.equals("09")){
	           disPlayNo="1.09";
	       }
	   }
			//modify by fengyang 20140519 end
			if (withCode != null && !"".equals(withCode)) {
				StringBuffer hql = new StringBuffer(256);
				if(codeType.equals("ExceptCode")){
					if("P".equals(codeCode)){
						System.out.println(codeCode);
						hql.append(
								"select a.id.codeCode,a.codeCName,a.flag from PrpDnewCode a,PrpDnewCodeRisk b where b.id.riskCode in ('PUB','")
								.append(withCode)
								.append("','")
								.append(withCode.substring(0, 1))
								.append("') and b.id.codeType = '")
								.append(codeType)
								.append("' and a.id.codeCode=b.id.codeCode and a.id.codeType=b.id.codeType")
								.append(" and (a.id.codeCode like '")
								.append("P%' or a.codeCName like '")
								.append("P%') and (a.codeCName like '").append(codeName)
								.append("%' or a.codeEName like '").append(codeName)
								.append("%') ").append(" and (a.validStatus = 1)");
					}else{
						hql.append(
								"select a.id.codeCode,a.codeCName,a.flag from PrpDnewCode a,PrpDnewCodeRisk b where b.id.riskCode in ('PUB','")
								.append(withCode)
								.append("','")
								.append(withCode.substring(0, 1))
								.append("') and b.id.codeType = '")
								.append(codeType)
								.append("' and a.id.codeCode=b.id.codeCode and a.id.codeType=b.id.codeType")
								.append(" and (a.id.codeCode not like '")
								.append("P%' or a.codeCName like '")
								.append("P%') and (a.codeCName like '").append(codeName)
								.append("%' or a.codeEName like '").append(codeName)
								.append("%') ").append(" and (a.validStatus = 1)");
					}
				}else{
				hql.append(
						"select a.id.codeCode,a.codeCName,a.flag from PrpDnewCode a,PrpDnewCodeRisk b where b.id.riskCode in ('PUB','")
						.append(withCode)
						.append("','")
						.append(withCode.substring(0, 1))
						.append("') and b.id.codeType = '")
						.append(codeType)
						.append("' and a.id.codeCode=b.id.codeCode and a.id.codeType=b.id.codeType")
						.append(" and (a.id.codeCode like '").append(codeCode)
						.append("%' or a.codeCName like '").append(codeCode)
						.append("%') and (a.codeCName like '").append(codeName)
						.append("%' or a.codeEName like '").append(codeName)
						.append("%') ").append(" and (a.validStatus = 1)");
				}
				if (disPlayNo != null && !"".equals(disPlayNo)) {
					hql.append(" and b.disPlayNo="+disPlayNo+"");
				}
				if (ignoreCode != null && !"".equals(ignoreCode)) {
					hql.append(" and a.id.codeCode not in(" + ignoreCode + ")");
				}
				if (StringUtils.isNotBlank(upperCode)) {
					hql.append(" and a.upperCode in ('" + upperCode + "')");
				}
				if (extraCodeCode != null && !"".equals(extraCodeCode)) {
					String extraCodeCodes = extraCodeCode.replace(",", "','");
					StringBuffer con = new StringBuffer(256);
					con.append(" or( b.id.riskCode in ('PUB','")
							.append(withCode)
							.append("','")
							.append(withCode.substring(0, 1))
							.append("') and b.id.codeType = '")
							.append(codeType)
							.append("' and a.id.codeCode=b.id.codeCode and a.id.codeType=b.id.codeType")
							.append(" and a.id.codecode in('")
							.append(extraCodeCodes).append("')");
					if (ignoreCode != null && !"".equals(ignoreCode)) {
						con.append(" and a.id.codeCode not in(" + ignoreCode + ")");
					}
					con.append(")");
					hql.append(con);
				}
				if ("SailScope".equals(codeType)){//add by CSY 20151106  排序  
					hql.append(" order by to_number(b.disPlayNo)") ;
				}
				if (pageNo == 0 || pageSize == 0) {
					List list = super.findByHql(hql.toString());
					dictPage.setData(list);
				} else {
					Page page = super.findByHql(hql.toString(), pageNo, pageSize);
					dictPage.setData(page.getResult());
					dictPage.setPageCount(page.getTotalPageCount());
					dictPage.setPageNo(pageNo);
					dictPage.setPageSize(pageSize);
					dictPage.setTotalRecordCount(page.getTotalCount());
				}
			} else {
				StringBuffer hql = new StringBuffer(256);
				hql.append(
						"select a.id.codeCode,a.codeCName from PrpDnewCode a where ")
						.append("a.id.codeType = '").append(codeType)
						.append("' and a.id.codeCode like '").append(codeCode)
						.append("%' and (a.codeCName like '").append(codeName)
						.append("%' or a.codeEName like '").append(codeName)
						.append("%') and a.validStatus = 1");
				if (ignoreCode != null && !"".equals(ignoreCode)) {
					hql.append(" and a.id.codeCode not in(" + ignoreCode + ")");
				}
				if (extraCodeCode != null && !"".equals(extraCodeCode)) {
					StringBuffer conn = new StringBuffer(256);
					String extraCodeCodes = extraCodeCode.replace(",", "','");
					conn.append(" or(a.id.codeType = '").append(codeType)
							.append("'").append(" and a.id.codeCode in('")
							.append(extraCodeCodes).append("')");
					if (ignoreCode != null && !"".equals(ignoreCode)) {
						conn.append(" and a.id.codeCode not in(" + ignoreCode + ")");
					}
					conn.append(")");
					hql.append(conn);
				}
				if (pageNo == 0 || pageSize == 0) {
					List list = super.findByHql(hql.toString());
					dictPage.setData(list);
				} else {
					Page page = super.findByHql(hql.toString(), pageNo, pageSize);
					dictPage.setData(page.getResult());
					dictPage.setPageCount(page.getTotalPageCount());
					dictPage.setPageNo(pageNo);
					dictPage.setPageSize(pageSize);
					dictPage.setTotalRecordCount(page.getTotalCount());
				}
			}
		
		return dictPage;
	}
	/**
	 * @param systemCode
	 * @param codeType
	 * @param codeCode
	 *            代码 模糊查询
	 * @param codeCName
	 *            代码中文名 模糊查询
	 * @param riskCode
	 * @param IgnoreCode
	 *            忽略代码 在逻辑中排除传入的代码，入参可传入多个代码，用","分隔
	 * @param extraCodeCode
	 *            新添加extraCodeCode参数,用来查询原逻辑有效的数据 + 查询extraCodeCode不管是否有效的数据
	 * @param pageNO
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public DictPage getPrpDCodeWithRisk(String systemCode, String codeType,
			String codeCode, String codeName, String withCode,
			String ignoreCode, String extraCodeCode, String upperCode,
			int pageNo, int pageSize) {
		DictPage dictPage = new DictPage();
		// int count = getCount("dms", "PrpDnewCodeRisk", "codeType = '" +
		// codeType
		// + "' and riskCode in ('PUB','" + withCode + "')");
		if (withCode != null && !"".equals(withCode)) {
			StringBuffer hql = new StringBuffer(256);
			hql.append(
					"select a.id.codeType,a.id.codeCode,a.codeCName,a.codeEName,a.flag,a.oldCodeCode,a.newCodeCode, " +
					" a.validDate from PrpDnewCode a,PrpDnewCodeRisk b where b.id.riskCode in ('PUB','")
					.append(withCode)
					.append("','")
					.append(withCode.substring(0, 1))
					.append("') and b.id.codeType = '")
					.append(codeType)
					.append("' and a.id.codeCode=b.id.codeCode and a.id.codeType=b.id.codeType")
					.append(" and (a.id.codeCode like '").append(codeCode)
					.append("%' or a.codeCName like '").append(codeCode)
					.append("%') and (a.codeCName like '").append(codeName)
					.append("%' or a.codeEName like '").append(codeName)
					.append("%') ").append(" and a.validStatus = 1");
			if (ignoreCode != null && !"".equals(ignoreCode)) {
				hql.append(" and a.id.codeCode not in(" + ignoreCode + ")");
			}
			if (StringUtils.isNotBlank(upperCode)) {
				hql.append(" and a.upperCode in ('" + upperCode + "')");
			}
			if (extraCodeCode != null && !"".equals(extraCodeCode)) {
				String extraCodeCodes = extraCodeCode.replace(",", "','");
				StringBuffer con = new StringBuffer(256);
				con.append(" or( b.id.riskCode in ('PUB','")
						.append(withCode)
						.append("','")
						.append(withCode.substring(0, 1))
						.append("') and b.id.codeType = '")
						.append(codeType)
						.append("' and a.id.codeCode=b.id.codeCode and a.id.codeType=b.id.codeType")
						.append(" and a.id.codecode in('")
						.append(extraCodeCodes).append("')");
				if (ignoreCode != null && !"".equals(ignoreCode)) {
					con.append(" and a.id.codeCode not in(" + ignoreCode + ")");
				}
				con.append(")");
				hql.append(con);
			}
			if (pageNo == 0 || pageSize == 0) {
				List list = super.findByHql(hql.toString());
				dictPage.setData(list);
			} else {
				Page page = super.findByHql(hql.toString(), pageNo, pageSize);
				List<PrpDcodeInfo> list=new ArrayList<PrpDcodeInfo>();
				for(int m=0;m<page.getResult().size();m++){
				Object[] result=(Object[])page.getResult().get(m);	
				 PrpDcodeInfo prpDcodeInfo =new PrpDcodeInfo();
				 PrpDnewCodeId prpDcodeId =new PrpDnewCodeId();
				 prpDcodeId.setCodeType((String)result[0]);
				 prpDcodeId.setCodeCode((String)result[1]);
				 prpDcodeInfo.setId(prpDcodeId);
				 prpDcodeInfo.setCodeCName((String)result[2]);
				 prpDcodeInfo.setCodeEName((String)result[3]);
				 prpDcodeInfo.setFlag((String)result[4]);
				 prpDcodeInfo.setOldCodeCode((String)result[5]);
				 prpDcodeInfo.setNewCodeCode((String)result[6]);
				 prpDcodeInfo.setValidStatus((String)result[7]);
				 list.add(prpDcodeInfo);
				}
				dictPage.setData(list);
				dictPage.setPageCount(page.getTotalPageCount());
				dictPage.setPageNo(pageNo);
				dictPage.setPageSize(pageSize);
				dictPage.setTotalRecordCount(page.getTotalCount());
			}
		} else {
			StringBuffer hql = new StringBuffer(256);
			hql.append(
					"select a.id.codeType,a.id.codeCode,a.codeCName,a.codeEName,a.flag,a.oldCodeCode,a.newCodeCode, " +
					" a.validDate from PrpDnewCode a where ")
					.append("a.id.codeType = '").append(codeType)
					.append("' and a.id.codeCode like '").append(codeCode)
					.append("%' and (a.codeCName like '").append(codeName)
					.append("%' or a.codeEName like '").append(codeName)
					.append("%') and a.validStatus = 1");
			if (ignoreCode != null && !"".equals(ignoreCode)) {
				hql.append(" and a.id.codeCode not in(" + ignoreCode + ")");
			}
			if (extraCodeCode != null && !"".equals(extraCodeCode)) {
				StringBuffer conn = new StringBuffer(256);
				String extraCodeCodes = extraCodeCode.replace(",", "','");
				conn.append(" or(a.id.codeType = '").append(codeType)
						.append("'").append(" and a.id.codeCode in('")
						.append(extraCodeCodes).append("')");
				if (ignoreCode != null && !"".equals(ignoreCode)) {
					conn.append(" and a.id.codeCode not in(" + ignoreCode + ")");
				}
				conn.append(")");
				hql.append(conn);
			}
			if (pageNo == 0 || pageSize == 0) {
				List list = super.findByHql(hql.toString());
				dictPage.setData(list);
			} else {
				Page page = super.findByHql(hql.toString(), pageNo, pageSize);
				List<PrpDcodeInfo> list=new ArrayList<PrpDcodeInfo>();
				for(int m=0;m<page.getResult().size();m++){
				Object[] result=(Object[])page.getResult().get(m);	
				 PrpDcodeInfo prpDcodeInfo =new PrpDcodeInfo();
				 PrpDnewCodeId prpDcodeId =new PrpDnewCodeId();
				 prpDcodeId.setCodeType((String)result[0]);
				 prpDcodeId.setCodeCode((String)result[1]);
				 prpDcodeInfo.setId(prpDcodeId);
				 prpDcodeInfo.setCodeCName((String)result[2]);
				 prpDcodeInfo.setCodeEName((String)result[3]);
				 prpDcodeInfo.setFlag((String)result[4]);
				 prpDcodeInfo.setOldCodeCode((String)result[5]);
				 prpDcodeInfo.setNewCodeCode((String)result[6]);
				 prpDcodeInfo.setValidStatus((String)result[7]);
				 list.add(prpDcodeInfo);
				}
				dictPage.setData(list);
				dictPage.setPageCount(page.getTotalPageCount());
				dictPage.setPageNo(pageNo);
				dictPage.setPageSize(pageSize);
				dictPage.setTotalRecordCount(page.getTotalCount());
			}
		}
		return dictPage;
	}
	
	public DictPage getOldCodeWithRisk(String systemCode, String codeType,
			String codeCode, String codeName, String withCode, int pageNo,
			int pageSize) {
		DictPage dictPage = new DictPage();
		int count = getCount("dms", "PrpDcodeRisk", "codeType = '" + codeType
				+ "' and riskCode in ('PUB','" + withCode + "')");
		if (count > 0) {
			StringBuffer hql = new StringBuffer(256);
			hql.append(
					"select a.id.codeCode,a.codeCName from PrpDcode a,PrpDcodeRisk b where b.id.riskCode in ('PUB','")
					.append(withCode)
					.append("') and b.id.codeType = '")
					.append(codeType)
					.append("' and a.id.codeCode=b.id.codeCode and a.id.codeType=b.id.codeType")
					.append(" and a.id.codeCode like '").append(codeCode)
					.append("%' and (a.codeCName like '").append(codeName)
					.append("%' or a.codeEName like '").append(codeName)
					.append("%') ").append(" and a.validStatus = 1");

			if (pageNo == 0 || pageSize == 0) {
				List list = super.findByHql(hql.toString());
				dictPage.setData(list);
			} else {
				Page page = super.findByHql(hql.toString(), pageNo, pageSize);
				dictPage.setData(page.getResult());
				dictPage.setPageCount(page.getTotalPageCount());
				dictPage.setPageNo(pageNo);
				dictPage.setPageSize(pageSize);
				dictPage.setTotalRecordCount(page.getTotalCount());
			}

		} else {
			StringBuffer hql = new StringBuffer(256);
			hql.append(
					"select a.id.codeCode,a.codeCName from PrpDcode a where ")
					.append("a.id.codeType = '").append(codeType)
					.append("' and a.id.codeCode like '").append(codeCode)
					.append("%' and (a.codeCName like '").append(codeName)
					.append("%' or a.codeEName like '").append(codeName)
					.append("%') and a.validStatus = 1");
			if (pageNo == 0 || pageSize == 0) {
				List list = super.findByHql(hql.toString());
				dictPage.setData(list);
			} else {
				Page page = super.findByHql(hql.toString(), pageNo, pageSize);
				dictPage.setData(page.getResult());
				dictPage.setPageCount(page.getTotalPageCount());
				dictPage.setPageNo(pageNo);
				dictPage.setPageSize(pageSize);
				dictPage.setTotalRecordCount(page.getTotalCount());
			}
		}
		return dictPage;
	}

	/**
	 * 获得特约代码
	 * 
	 * @param systemCode
	 *            系统代码
	 * @param riskCode
	 *            险种代码
	 * @param language
	 *            语种
	 * @param clauseCode
	 *            条款代码
	 * @param engageCode
	 *            特别约定代码
	 * @param extraEngageCode
	 *            无论有效/无效数据的查询(可传多参数)
	 * @return DictPage
	 * @throws Exception
	 */
	public DictPage getRiskEngage(String systemCode, String riskCode,
			String language, String clauseCode, String engageCode,
			String extraEngageCode, int pageNo, int pageSize,
			String extraCondition,String initFlag) throws Exception {
		DictPage dictPage = new DictPage();
		List list = new ArrayList();
		StringBuffer hql = new StringBuffer(256);
		hql.append(
				"from PrpDriskEngage a where a.validInd = 1 and a.id.riskCode in ('")
				.append(riskCode).append("','PUB') and a.id.clauseCode like '")
				.append(clauseCode).append("%' and a.language = '")
				.append(language).append("'");
		if (engageCode != null && !"".equals(engageCode)) {
			hql.append(" and a.id.engageCode like '" + engageCode + "%'");
		}
		if (initFlag != null && !"".equals(initFlag)) {
			hql.append(" and a.autoFlag like '" + initFlag + "%'");		
		}

		/* extraEngageCode参数的意义为：这个入参表示对engageCode代码进行包含无效数据的查询 start */
		if (extraEngageCode != null && !"".equals(extraEngageCode)) {
			String extraEngageCodes = extraEngageCode.replace(",", "','");
			hql.append(" or (a.id.riskCode in ('").append(riskCode)
					.append("','PUB') and a.id.clauseCode like '")
					.append(clauseCode).append("%' and a.language = '")
					.append(language).append("'");
			hql.append(" and a.id.engageCode in('").append(extraEngageCodes)
					.append("'))");
		}
		/* extraEngageCode参数的意义为：这个入参表示对engageCode代码进行包含无效数据的查询 end */
		// modify add by guyanqing 2012-02-06 reason:增加保单归属机构与特别约定适用区域控制
		if (extraCondition != null && !"".equals(extraCondition)) {
			PrpDcompany prpDCompany = prpDcompanyService
					.getPrpDcompany(extraCondition);
			String upperCompany = prpDCompany.getUpperPath();
			String[] upperCompanys = upperCompany.split(",");
			hql.append(" and (areaCode is null or  areaCode like  '%31000000%' ");
			for (String company : upperCompanys) {
				hql.append("or areaCode " + " like '%" + company + "'");
			}
			hql.append(")");
		}
		// modify end by guyanqing 2012-02-06 reason:增加保单归属机构与特别约定适用区域控制
		    hql.append(" order by riskCode,clauseCode,engageCode");
		if (pageNo == 0 || pageSize == 0) {
			String condition = hql.toString().substring(
					hql.toString().indexOf("where") + 5,
					hql.toString().length());
			int count = getCount("dms", "PrpDriskEngage", condition);
			if (count > 1000) {
				logger.error("系统" + systemCode + "查询记录数过大，不予返回！");
				throw new Exception("系统" + systemCode + "调用接口"
						+ ServiceInfoConst.GETRISKENGAGE + "查询记录数过大，请缩小查询范围");
			} else {
				list = super.findByHql(hql.toString());
			}
			dictPage.setData(list);
		} else {
			Page page = super.findByHql(hql.toString(), pageNo, pageSize);
			list = page.getResult();
			dictPage.setData(list);
			dictPage.setPageNo(pageNo);
			dictPage.setPageSize(pageSize);
			dictPage.setPageCount(page.getTotalPageCount());
			dictPage.setTotalRecordCount(page.getTotalCount());
		}
		return dictPage;
	}

	public DictPage getTaxAuthorities(String systemCode, String userCode,
			String comCode, int pageNo, int pageSize) throws Exception {
		StringBuffer comCodes = new StringBuffer(64);
		DictPage dictPage = new DictPage();
		StringBuffer hql = new StringBuffer(256);
		List list = new ArrayList();
		String upComCodes = getAllUpperComCode(comCode); // 获得所有上级机构，包括当前机构
		if (upComCodes != null && upComCodes != "") {
			comCodes.append("('").append(upComCodes.replace(",", "','"))
					.append("')");
		} else {
			comCodes.append("('')");
		}
		hql.append("from PrpDtaxAuthorities a where a.validStatus = 1 and ")
				.append("a.comCode in ").append(comCodes);

		if (pageNo == 0 || pageSize == 0) {
			String condition = hql.substring(
					hql.toString().indexOf("where") + 5, hql.toString()
							.length());
			int count = getCount("dms", "PrpDtaxAuthorities", condition);
			if (count > 1000) {
				logger.error("系统：" + systemCode + "查询记录数过大，不予返回！");
				throw new Exception("系统" + systemCode + "调用接口"
						+ ServiceInfoConst.GETTAXAUTHORITIES
						+ "查询记录数过大，请缩小查询范围");
			} else {
				list = super.findByHql(hql.toString());
			}
			dictPage.setData(list);
		} else {
			Page page = super.findByHql(hql.toString(), pageNo, pageSize);
			list = page.getResult();
			dictPage.setData(list);
			dictPage.setPageNo(pageNo);
			dictPage.setPageSize(pageSize);
			dictPage.setPageCount(page.getTotalPageCount());
			dictPage.setTotalRecordCount(page.getTotalCount());
		}
		return dictPage;
	}

	// riskCode多个参数传递的时候，把"DAA,DAZ,DAT"里面的,替换为','
	public DictPage getRisk(String systemCode, String classCode,
			String riskCode, String reverseType, int pageNo, int pageSize)
			throws Exception {
		DictPage dictPage = new DictPage();
		StringBuffer hql = new StringBuffer(256);
		hql.append("from PrpDrisk a ");
		boolean hasFirstCon = false;
		List list = null;
		String riskCodes = null;
		if (riskCode != null && !"".equals(riskCode)) {
			riskCodes = riskCode.replace(",", "','");
		} else {
			riskCode = "";
		}

		if (!"PUB".equals(classCode)) {
			if (classCode != null && !"".equals(classCode)) {
				hql.append(hasFirstCon ? " and " : " where ");
				hql.append("a.classCode = '").append(classCode).append("'");
				hasFirstCon = true;
			}
			if (riskCode != null && !"".equals(riskCode)) {
				hql.append(hasFirstCon ? " and " : " where ");
				hasFirstCon = true;
				if (!"2".equals(reverseType)) {
					hql.append(" (a.riskCode ");
				} else {
					hql.append(" (a.oldRiskCode ");
				}
				if (riskCode.contains(",")) {
					hql.append("in ('").append(riskCodes).append("'))");
				} else {
					hql.append(" like '").append(riskCode)
							.append("%' or a.riskCName like '")
							.append(riskCode).append("%'");
					hql.append(" or a.riskEName like '").append(riskCode)
							.append("%')");
				}
			}
		}

		if (classCode != null && "PUB".equals(classCode)) {
			hql.append(" where 1=1");
			hasFirstCon = true;
			if (riskCode != null && !"".equals(riskCode)) {
				hql.append(hasFirstCon ? " and " : " where ");
				if (!"2".equals(reverseType)) {
					hql.append(" (a.riskCode ");
				} else {
					hql.append(" (a.oldRiskCode ");
				}
				if (riskCode.contains(",")) {
					hql.append("in ('").append(riskCodes).append("'))");
				} else {
					hql.append(" like '").append(riskCode)
							.append("%' or a.riskCName like '")
							.append(riskCode).append("%'");
					hql.append(" or a.riskEName like '").append(riskCode)
							.append("%')");
				}
			}
		}
		// else if (classCode == null || "".equals(classCode)) {
		// if (!"2".equals(reverseType)) {
		// hql.append("from PrpDrisk a where ");
		// if(riskCode.contains(",")){
		// hql.append("a.riskCode in ('").append(riskCodes).append("')");
		// }else {
		// hql.append("a.riskCode like ('").append(riskCodes).append("%') or a.riskCName like('")
		// .append(riskCode).append("'))");
		// hql.append(" or a.riskEName like ('").append(riskCode).append("%')");
		// }
		// } else {
		// hql.append("from PrpDrisk a where a.oldRiskCode in ('").append(riskCodes).append("')");
		// }
		// } else {
		// if (!"2".equals(reverseType)) {
		// hql.append("from PrpDrisk a where a.classCode = '").append(classCode)
		// .append("' and a.riskCode in('").append(riskCodes).append("')");
		// } else {
		// hql.append("from PrpDrisk a where a.classCode = '").append(classCode)
		// .append("' and a.oldRiskCode in('").append(riskCodes).append("')");
		// }
		// }
		hql.append(hasFirstCon ? " and " : " where ");
		hql.append("a.validInd = '1'");
		hql.append(" and (sysdate between validdate and invaliddate or sysdate>= validdate and invaliddate is null )");
		if (pageNo == 0 || pageSize == 0) {
			hql.append(" order by classCode,riskCode ");
			list = super.findByHql(hql.toString());
			dictPage.setData(list);
		} else {
			hql.append(" order by classCode,riskCode ");
			Page page = super.findByHql(hql.toString(), pageNo, pageSize);
			list = page.getResult();
			dictPage.setData(list);
			dictPage.setPageNo(pageNo);
			dictPage.setPageSize(pageSize);
			dictPage.setPageCount(page.getTotalPageCount());
			dictPage.setTotalRecordCount(page.getTotalCount());
		}
		return dictPage;
	}

	public DictPage getClass(String systemCode, String classCode, int pageNo,
			int pageSize) throws Exception {
		DictPage dictPage = new DictPage();
		StringBuffer hql = new StringBuffer(256);
		List list = new ArrayList();
		if (classCode == null || "".equals(classCode)) {// 如果classCode没有值则查询所有prpDclass数据
			hql.append("from PrpDclass a where a.validInd = 1");
		} else {
			hql.append("from PrpDclass a where a.classCode = '")
					.append(classCode).append("' and a.validInd = 1");
		}
		if (pageNo == 0 || pageSize == 0) {
			String condition = hql.toString().substring(
					hql.toString().indexOf("where") + 5,
					hql.toString().length());
			int count = getCount("dms", "PrpDclass", condition);
			if (count > 1000) {
				logger.error("系统" + systemCode + "查询记录数过大，不予返回！");
				throw new Exception("系统" + systemCode + "调用接口"
						+ ServiceInfoConst.GETCLASS + "查询记录数过大，请缩小查询范围");
			} else {
				hql.append(" order by classCode ");
				list = super.findByHql(hql.toString());
			}
			dictPage.setData(list);
		} else {
			hql.append(" order by classCode ");
			Page page = super.findByHql(hql.toString(), pageNo, pageSize);
			list = page.getResult();
			dictPage.setData(list);
			dictPage.setPageNo(pageNo);
			dictPage.setPageSize(pageSize);
			dictPage.setPageCount(page.getTotalPageCount());
			dictPage.setTotalRecordCount(page.getTotalCount());
		}
		return dictPage;
	}

	/**
	 * 获得所有上级机构，包括当前机构 实现方式：通过查询机构表的级别数字段，通过截取获得。
	 * 
	 * @param comCode
	 *            当前机构代码
	 * @return
	 */
	private String getAllUpperComCode(String comCode) {
		List list = new ArrayList();
		StringBuffer hql = new StringBuffer(256);
		String codes = null;
		hql.append("select upperPath from PrpDcompany where comCode = '")
				.append(comCode).append("' and validStatus = '1'");
		list = super.findByHql(hql.toString());
		if (list.size() > 0) {
			codes = (String) list.get(0);
		}
		return codes;
	}

	/**
	 * 获得所有上级机构
	 * */
	private String getAllUpperCom(String comCode) {
		String uperPath = "";
		List prpDcompany = super
				.findByHql("from PrpDcompany a where a.comCode = '" + comCode
						+ "' and a.validStatus = '1'");
		if (prpDcompany.size() > 0) {
			uperPath = ((PrpDcompany) prpDcompany.get(0)).getUpperPath();
		}
		String AllUpperCom = uperPath.replace(",", "','");
		return AllUpperCom;
	}

	public DictPage getPrpDtreatyReten(String systemCode, Map values)
			throws Exception {
		int pageNo = (Integer) values.get("pageNO");
		int pageSize = (Integer) values.get("pageSize");
		String uwYear = (String) values.get("uwYear");
		String classCode = (String) values.get("classCode");
		String riskCode = (String) values.get("riskCode");
		DictPage dictPage = new DictPage();
		StringBuffer hql = new StringBuffer(256);
		List list = new ArrayList();
		hql.append("from PrpDtreatyReten a where a.id.classCode = '")
				.append(classCode).append("'").append(" and a.id.uwYear = '")
				.append(uwYear).append("' and a.id.riskCode = '")
				.append(riskCode).append("'");

		/*********** 如果第一次查询出的记录为空用riskCode为PUB查询 ***************/
		String condition = hql.toString().substring(
				hql.toString().indexOf("where") + 5, hql.toString().length());// 字符串截取
		// ，
		// 获得where条件后的
		// sql语句
		int count = getCount("dms", "PrpDtreatyReten", condition);
		if (count == 0) {
			hql = new StringBuffer(256);
			hql.append("from PrpDtreatyReten a where a.id.classCode = '")
					.append(classCode).append("'")
					.append(" and a.id.uwYear = '").append(uwYear)
					.append("' and a.id.riskCode = '").append("PUB")
					.append("'");
		}
		/*********** 如果第一次查询出的记录为空用riskCode为PUB查询 *****************/
		if (pageNo == 0 || pageSize == 0) {
			condition = hql.toString().substring(
					hql.toString().indexOf("where") + 5,
					hql.toString().length());// 字符串截取，获得where条件后的 sql语句
			count = getCount("dms", "PrpDtreatyReten", condition);
			if (count > 1000) {
				logger.error("系统" + systemCode + "查询记录数过大，不予返回！");
				throw new Exception("系统" + systemCode + "调用接口"
						+ ServiceInfoConst.GETPRPDTREATYRETEN
						+ "查询记录数过大，请缩小查询范围");
			} else {
				list = super.findByHql(hql.toString());
			}
			dictPage.setData(list);
		} else {
			Page page = super.findByHql(hql.toString(), pageNo, pageSize);
			list = page.getResult();
			dictPage.setData(list);
			dictPage.setPageNo(pageNo);
			dictPage.setPageSize(pageSize);
			dictPage.setPageCount(page.getTotalPageCount());
			dictPage.setTotalRecordCount(page.getTotalCount());
		}
		return dictPage;
	}

	public String synClassData(String systemCode, Object data) {
		ClassObj classObj = (ClassObj) data;
		List<PrpDclass> list = classObj.getPrpDclass();
		if (list.size() > 0) {
			super.saveAll(list);
		}
		// JMS清分，调用qingFenSynClassData
		// modify by liuxiaofei 20110712 del reason:不需要数据清分功能 begin
		/*
		 * SynServiceImpl syn = new SynServiceImpl();
		 * syn.qingFenSynClassData(list);
		 */
		// modify by liuxiaofei 20110712 del reason:不需要数据清分功能 end
		// 执行成功则返回1
		return "1";
	}

	public String synFrameDataData(String systemCode, Object data) {
		FrameObj frameObj = (FrameObj) data;
		List list = frameObj.getPrpDframe();
		if (list.size() > 0) {
			super.saveAll(list);
		}
		return "1";
	}

	public String synPlanData(String systemCode, Object data) {
		// modify begin modify by guyanqing 2011-06-01
		/*
		 * PlanObj planObj = (PlanObj) data; List planList =
		 * planObj.getPrpDplan(); List planClauseKindList =
		 * planObj.getPrpDplanClauseKind(); List areaList =
		 * planObj.getPrpDarea(); List planLimitList =
		 * planObj.getPrpDplanLimit();
		 */
		RationObj planObj = (RationObj) data;
		List planList = planObj.getPrpDration();
		for (Object o : planList) {
			PrpDrisk risk = new PrpDrisk();
			cn.com.sinosoft.dms.model.PrpDration ration = (cn.com.sinosoft.dms.model.PrpDration) o;
			//ration.setPlanCode(ration.getRationCode().substring(0, 3));
			//modify by fengyang :临时存入planCode值
			//ration.setPlanCode(planObj.getPrpDrationClauseKind().get(0).getId().getRiskCode());
		}
		List planClauseKindList = planObj.getPrpDrationClauseKind();
		List planLimitList = planObj.getPrpDrationLimit();
		List areaList = planObj.getPrpDarea();
		List rationShortrateList = planObj.getPrpDrationShortrate();
		List rationEngageList = planObj.getPrpDrationEngage();

		/** add by wpf 2012-04-27 begin reason:添加个性信息的保存 */

		List channelInfoList = planObj.getPrpdChannelInfo();
		List channelClauseKindList = planObj.getPrpDChannelRationClauseKind();
		List channelRationEngageList = planObj.getPrpDChannelRationEngage();
		List channelRationPeriodRateList = planObj
				.getPrpDChannelRationPeriodRate();
		List channelCoinsList = planObj.getPrpdChannelCoins();
		List rationPeriodRateList = planObj.getPrpDRationPeriodRate();
		/** add by cuishang 2014-03-03 end reason:��Ӹ�����Ϣ�ı��� */
		List rationRelationList = planObj.getPrpDrationRelation();
		/** add by wpf 2012-04-27 end reason:��Ӹ�����Ϣ�ı��� */
		/**add by fengyang 20140417 reason:增加方案下的承保条件设定信息同步*/
		List rationConditionList=planObj.getPrpDrationCondition();

		if (planList != null && planList.size() > 0) {
			super.saveAll(planList);
		}
		if (planClauseKindList != null && planClauseKindList.size() > 0) {
			super.saveAll(planClauseKindList);
		}
		if (planLimitList != null && planLimitList.size() > 0) {
			super.saveAll(planLimitList);
		}
		if (areaList != null && areaList.size() > 0) {
			super.saveAll(areaList);
		}
		if (rationShortrateList != null && rationShortrateList.size() > 0) {
			super.saveAll(rationShortrateList);
		}
		if (rationEngageList != null && rationEngageList.size() > 0) {
			super.saveAll(rationEngageList);
		}
		/** add by wpf 2012-04-27 begin reason:添加个性信息的保存 */

		if (channelInfoList != null && channelInfoList.size() > 0) {
			super.saveAll(channelInfoList);
		}
		if (channelClauseKindList != null && channelClauseKindList.size() > 0) {
			super.saveAll(channelClauseKindList);
		}
		if (channelRationEngageList != null
				&& channelRationEngageList.size() > 0) {
			super.saveAll(channelRationEngageList);
		}
		if (channelRationPeriodRateList != null
				&& channelRationPeriodRateList.size() > 0) {
			super.saveAll(channelRationPeriodRateList);
		}
		if (channelCoinsList != null && channelCoinsList.size() > 0) {
			super.saveAll(channelCoinsList);
		}
		if (rationPeriodRateList != null && rationPeriodRateList.size() > 0) {
			super.saveAll(rationPeriodRateList);
		}
		if(null!=rationRelationList&&!rationRelationList.isEmpty()){
			super.saveAll(rationRelationList);
		}
		if(null!=rationConditionList&&!rationConditionList.isEmpty()){
			super.saveAll(rationConditionList);
		}
		
		/** add by wpf 2012-04-27 end reason:��Ӹ�����Ϣ�ı��� */
		// modify begin del by guaynqing 2011-06-02 reason:����Ҫ��ֲ���
		// JMS��֣�����qingFenSynPlanData
		/*
		 * SynServiceImpl syn = new SynServiceImpl();
		 * syn.qingFenSynPlanData(planList,planClauseKindList,planLimitList);
		 */
		// modify end del by guaynqing 2011-06-02 reason:不需要清分操作
		// 执行成功则返回1
		return "1";
	}

	public String synShortRiskData(String systemCode, Object data) {
		RiskObj riskObj = (RiskObj) data;
		List accountInfoList = riskObj.getPrpDaccountInfo();// 核算信息表
		List areaList = riskObj.getPrpDarea();// 区域表
		List riskList = riskObj.getPrpDrisk();// 产品定义表
		List riskClauseList = riskObj.getPrpDriskClause();// 产品条款定义表
		List riskClauseKindList = riskObj.getPrpDriskClauseKind();// 产品条款责任表
		List riskClauseKindRelationList = riskObj
				.getPrpDriskClauseKindRelation();// 产品条款/责任关系表
		List riskEngageList = riskObj.getPrpDriskEngage();// 产品特别约定表
		List riskItemList = riskObj.getPrpDriskItem();// 产品标的表
		List riskLimitList = riskObj.getPrpDriskLimit();// 产品限额/免赔额表
		List riskShortRateList = riskObj.getPrpDriskShortRate();// 产品短期费率表
		List newCodeRiskList = riskObj.getPrpDnewCodeRisk();//
		List prpdrckratelowerList = riskObj.getPrpDRCKRateLower();// 费率下限表
		List prpDclass=riskObj.getPrpDclass();
		for(Object entity:riskList){
			String riskCode=((PrpDrisk)entity).getRiskCode();
			List list=super.findByHql("from PrpDrisk p where p.riskCode='"+riskCode+"'");
//			if(null==list)
			if(list.size()==0)
			super.save(entity);
		}
//		super.saveAll(riskList);
		
		if(null!=prpDclass&&prpDclass.size()>0){
			super.saveAll(prpDclass);
		}
		// if (accountInfoList.size() > 0) {
		super.saveAll(accountInfoList);
		// }
		// if (areaList.size() > 0) {
		super.saveAll(areaList);
		// }
		// if (riskList.size() > 0) {
		super.saveAll(riskList);
		// }
		// if (riskClauseList.size() > 0) {
		super.saveAll(riskClauseList);
		// }
		// if (riskClauseKindList.size() > 0) {
		super.saveAll(riskClauseKindList);
		// }
		// if (riskClauseKindMinPremiumList.size() > 0) {
		// }
		// if (riskClauseKindRelationList.size() > 0) {
		super.saveAll(riskClauseKindRelationList);
		// }
		// if (riskEngageList.size() > 0) {
		super.saveAll(riskEngageList);
		// }
		// if (riskItemList.size() > 0) {
		super.saveAll(riskItemList);
		// }
		// if (riskLimitList.size() > 0) {
		super.saveAll(riskLimitList);
		// }
		// if (riskMinPremiumList.size() > 0) {
		// }
		// if (riskShortRateList.size() > 0) {
		super.saveAll(riskShortRateList);
		// }
		// if (riskShortRateList.size() > 0) {
		super.saveAll(prpdrckratelowerList);
		// }
		/*if (newCodeRiskList != null && newCodeRiskList.size() > 0) {
			Set set = new HashSet();
			StringBuffer hql = new StringBuffer();
			StringBuffer codeTypes = new StringBuffer();
			PrpDnewCodeRisk codeRisk = null;
			String codeType = null;
			for (Object o : newCodeRiskList) {
				codeRisk = (PrpDnewCodeRisk) o;
				codeType = codeRisk.getId().getCodeType();
				if (set.add(codeType)) {
					codeTypes.append(",'");
					codeTypes.append(codeType);
					codeTypes.append("'");
				}
			}
			if (codeTypes.length() > 0) {
				hql.append("delete from PrpDnewCodeRisk a where a.id.codeType in (");
				hql.append(codeTypes.substring(1));
				hql.append(")");
				super.getSession().createQuery(hql.toString()).executeUpdate();
				super.saveAll(newCodeRiskList);
			}
		}*/
		// JMS清分，调用qingFenSynRiskData
		// modify by liuxiaofei 20110712 del reason:不需要数据清分功能 begin
		/*
		 * SynServiceImpl syn = new SynServiceImpl();
		 * syn.qingFenSynRiskData(accountInfoList, areaList,
		 * riskList,riskClauseList,riskClauseKindList,
		 * riskClauseKindRelationList
		 * ,riskEngageList,riskItemList,riskLimitList,riskShortRateList,
		 * newCodeRiskList,prpdrckratelowerList);
		 */
		// modify by liuxiaofei 20110712 del reason:不需要数据清分功能 end
		// 执行成功则返回1
		return "1";
	}

	/*
	 * 渠道信息接口
	 */
	public String synPrpDAgentData(String systemCode, Object data)
			throws Exception {
		AgentSYNObj agentSYNObj = (AgentSYNObj) data;
		List prpdAgentExtList = agentSYNObj.getPrpDagentExtList();
		List prpdContractManageList = agentSYNObj.getPrpDcontractManageList();
		PrpDagentAll prpDagent = agentSYNObj.getPrpDagent();
		if (prpDagent != null) {
			super.getHibernateTemplate().saveOrUpdate(prpDagent);
			if (prpdAgentExtList.size() > 0) {
				super.getHibernateTemplate().saveOrUpdateAll(prpdAgentExtList);
			}
			if (prpdContractManageList.size() > 0) {
				super.getHibernateTemplate().saveOrUpdateAll(
						prpdContractManageList);
			}
			// JMS清分，调用qingFenPrpDAgentAll
			// modify by liuxiaofei 20110712 del reason:不需要数据清分功能 begin
			// prpDagentService.qingFenSynPrpDAgentData(prpDagent,
			// prpdAgentExtList, prpdContractManageList);
			// modify by liuxiaofei 20110712 del reason:不需要数据清分功能 end
			// 执行成功则返回1
			return "1";

		} else {
			throw new Exception("prpDagent数据不能为空");
		}
	}

	public DictPage getPrpDriskByCondition(String systemCode, Map values)
			throws Exception {
		int pageNo = (Integer) values.get("pageNO");
		int pageSize = (Integer) values.get("pageSize");
		String condition = (String) values.get("condition");
		DictPage dictPage = new DictPage();
		StringBuffer hql = new StringBuffer(256);
		List list = new ArrayList();
		hql.append("from PrpDrisk a where a.validInd = 1 and ");
		if ("".equals(condition) || condition == null) {
			hql.append(" 1=1");
		} else {
			hql.append(condition);
		}
		if (pageNo == 0 || pageSize == 0) {
			int count = getCount("dms", "PrpDrisk", condition);
			if (count > 1000) {
				logger.error("系统" + systemCode + "查询记录数过大，不予返回！");
				throw new Exception("系统" + systemCode + "调用接口"
						+ ServiceInfoConst.GETRISKBYCONDITION
						+ "查询记录数过大，请缩小查询范围");
			} else {
				list = super.findByHql(hql.toString());
			}
			dictPage.setData(list);
		} else {
			Page page = super.findByHql(hql.toString(), pageNo, pageSize);
			list = page.getResult();
			dictPage.setData(list);
			dictPage.setPageNo(pageNo);
			dictPage.setPageSize(pageSize);
			dictPage.setPageCount(page.getTotalPageCount());
			dictPage.setTotalRecordCount(page.getTotalCount());
		}
		return dictPage;
	}

	// 通过serverCode服务代码和environmentCode环境变量获取ipServiceConfig表的IP服务信息
	public DictPage getServiceInfoByCode(String systemCode, Map values) {
		String environmentCode = (String) values.get("EnvironmentCode");
		DictPage dictPage = new DictPage();
		StringBuffer hql = new StringBuffer(256);
		List list = new ArrayList();
		hql.append(
				"from IPServiceConfig a where a.validStatus = '1' and a.id.serverCode = '")
				.append(systemCode).append("' and a.id.environmentCode = '")
				.append(environmentCode).append("'");
		list = super.findByHql(hql.toString());
		dictPage.setData(list);
		return dictPage;
	}

	// 通过environmentCode环境变量获取ipServiceConfig表该环境变量下的所有系统IP服务信息
	public DictPage getServiceInfoByEnvironmentCode(String systemCode,
			Map values) {
		String environmentCode = (String) values.get("EnvironmentCode");
		DictPage dictPage = new DictPage();
		StringBuffer hql = new StringBuffer(256);
		List list = new ArrayList();
		hql.append(" from IPServiceConfig a where a.validStatus = '1'");
		if (systemCode != null && !"".equals(systemCode)) {
			String systemCodes = systemCode.replace(",", "','");
			hql.append(" and a.id.serverCode in('").append(systemCodes)
					.append("')");
		}
		if (environmentCode != null && !"".equals(environmentCode)) {
			hql.append(" and a.id.environmentCode = '").append(environmentCode)
					.append("'");
		}
		list = super.findByHql(hql.toString());
		dictPage.setData(list);
		return dictPage;
	}

	public DictPage getUrlByCode(String systemCode, Map values) {
		StringBuffer url = new StringBuffer(256);
		String environmentCode = (String) values.get("EnvironmentCode");
		DictPage dictPage = new DictPage();
		StringBuffer hql = new StringBuffer(256);
		List list = new ArrayList();
		hql.append(
				"from IPServiceConfig a where a.validStatus = '1' and a.id.serverCode = '")
				.append(systemCode).append("' and a.id.environmentCode = '")
				.append(environmentCode).append("'");
		list = super.findByHql(hql.toString());
		List urlList = new ArrayList();
		if (list.size() > 0) {
			IPServiceConfig ipConfig = (IPServiceConfig) list.get(0);
			if (ipConfig.getProteclType() != null
					&& !"".equals(ipConfig.getProteclType())) {
				url.append(ipConfig.getProteclType()).append("://");
			}
			if (ipConfig.getServerIP() != null
					&& !"".equals(ipConfig.getServerIP())) {
				url.append(ipConfig.getServerIP());
			}
			if (ipConfig.getServerPort() != null
					&& !"".equals(ipConfig.getServerPort())) {
				url.append(":").append(ipConfig.getServerPort());
			}
			if (ipConfig.getServerAppName() != null
					&& !"".equals(ipConfig.getServerAppName())) {
				url.append("/").append(ipConfig.getServerAppName());
			}
			if (ipConfig.getMethods() != null
					&& !"".equals(ipConfig.getMethods())) {
				url.append("/").append(ipConfig.getMethods());
			}
			urlList.add(url.toString());
			dictPage.setData(urlList);
		}
		dictPage.setData(urlList);
		return dictPage;
	}

	/**
  	SELECT a FROM
	    PrpDagent a,
	    PrpDagentExt b
	WHERE
		a.validStatus = 1
	AND b.validStatus = 1
	AND a.id.agentCode = b.id.agentCode
	AND(
	        a.id.agentCode LIKE '%'
	     OR a.agentName LIKE '%'
	    )
	AND SUBSTR(a.flag,2,1) = 'N'
	AND(
	    b.classCode IN ('PUB','160001')
	    AND a.agentType LIKE '0%'
	    AND
	        (
	            b.comCode IN ('00000000')
	        AND b.agentNature = '1'
	         OR b.comCode = '00000000'
	        )
	    )
  
  prpDagent.agentType 渠道类型
  prpDagent.agentNature 是否允许归属机构的下级机构使用
  prpDagent.bargainDate   合同期
 * @throws Exception
 * */
/*
 * 2010-11-10 wulei	查询渠道不考虑合同期
 */
	public DictPage getAgent(String systemCode, Map values) throws Exception {
		int pageNo = (Integer) values.get("pageNO");
		int pageSize = (Integer) values.get("pageSize");
		String operateDate = (String) values.get("operateDate");
		String riskCode = (String) values.get("riskCode");
		//and by 徐莉 220130523
		String agentType = (String) values.get("agentType");
		//and by 徐莉 220130523
		String classCode = null;
		String oldClassCode = null;
		DictPage riskPage = this.getRisk("dms", null, riskCode, "1", 0, 0);
		List<PrpDrisk> riskList = riskPage.getData();
		if(!riskList.isEmpty()){
			classCode = riskList.get(0).getClassCode();
			DictPage classPage = this.getClass("dms", classCode, 0, 0);
			List<PrpDclass> classList = classPage.getData();
			if(!classList.isEmpty()) {
				oldClassCode = classList.get(0).getOldClassCode();
			}
		}
		String comCode = (String) values.get("comCode");
		String businessNature = (String) values.get("businessNature");
		String codeOrName = (String) values.get("codeOrName");
		String handlerIdentifyNumber = (String) values.get("handlerIdentifyNumber");
		codeOrName = codeOrName == null?"":codeOrName;
		handlerIdentifyNumber = handlerIdentifyNumber == null?"":handlerIdentifyNumber;
		DictPage dictPage = new DictPage();
		StringBuffer hql = new StringBuffer(256);
		StringBuffer hql2 = new StringBuffer(256);
		String allUpperCom = getAllUpperCom(comCode);
		List list = null;
		StringBuffer condition = new StringBuffer(256);
		String channelTypesql = "";
		//and by xuli 20130524
		if(businessNature!=null&&!"".equals(businessNature)){

		         if("40".equals(businessNature)){
		        	 channelTypesql =" and a.channelType in ('11','12','13')";
		         }else{
		        	 channelTypesql =" and a.channelType = '"+businessNature+"'";
		         }       
		}
		if("E".equals(businessNature)){
			//无值直接返回
			return dictPage;
		}
		else if("2G".equals(businessNature)||"2H".equals(businessNature)){
			
    		condition.append(" where a.validStatus = 1 AND a.channelType='"+businessNature+"'");
    		condition.append(" and (a.id.agentCode like '" + codeOrName + "%' or a.agentName like '" + codeOrName + "%')  order by a.id.agentCode ");
    		
		}else{
			//批次报价查询代理
			if("P".equals(agentType)){
				condition.append(" where a.agentType like '1%'");
				condition.append(channelTypesql);
			}else{
				condition.append(" where a.validStatus = 1 ");
			}
				if("1".equals(agentType)){
					//and by xuli 通路查询 20130523
					condition.append(channelTypesql);	
					//and by xuli 增加代理人日期查询
					condition.append(" and a.agentType like '" + agentType + "%'");
					//and by xuli 20130618 业务员按照身份证号查询
					condition.append(" and a.identifyNumber like '" + handlerIdentifyNumber + "%'");
					//edit by xuli 20130605 startjoindate和endjoindate可以为空
					
					//登录日
					condition.append(" and a.loginDate<=to_date('"+operateDate+"','yyyy-mm-dd')");
					//停止招攬期間始期
					condition.append(" and (");
					condition.append(" (a.startJoinDate>=to_date('"+operateDate+"','yyyy-mm-dd') or a.startJoinDate is null) ");
					condition.append("		or ");//停止招攬終止期
					condition.append(" (a.endJoinDate<=to_date('"+operateDate+"','yyyy-mm-dd') or a.endJoinDate is null) ");
					condition.append(" ) ");
					//登录到期日
					condition.append(" and a.loginEndDate>=to_date('"+operateDate+"','yyyy-mm-dd') ");
					if(classCode!=null && !"".equals(classCode.trim())){
						String sql = "";//1-产险,2-产险+健康险,3-车险,4-寿险,5-寿险+车险
						if("A".equals(classCode)||"B".equals(classCode)){
							sql = "'1','2','3','5'";
						}else if("C".equals(classCode)||"E".equals(classCode)
								||"F".equals(classCode)||"M".equals(classCode)){
							sql = "'1','2'";
						}else if("C1".equals(classCode)){
							sql = "'1','2','4','5'";//意健險還有詳細的處理規則，在對應頁面判斷
						}
						if(sql.length()!=0){
							condition.append(" and a.loginCode in ("+ sql +") ");
						}
					}
					condition.append(" order by a.identifyNumber ");
				}else if("2".equals(agentType)||"3".equals(agentType)){
					//and by xuli 20130618 代理人业务员按照代理号查询					
					condition.append(channelTypesql);
					//condition.append(" and a.agentType in ('2','3')");
					//if(!"A01".equals(riskCode) && !"B01".equals(riskCode)) {
						condition.append(" and a.agentType in ('2','3')");
					//} else {
					//	condition.append(" and a.agentType like '"+agentType+"%'");
					//}				
					condition.append(" and (a.userCode like '" + codeOrName + "%' or a.agentName like '" + codeOrName + "%' or a.id.agentCode like '" + codeOrName + "%') ");
					//'是否限制出單(1,是 0,否)';
					condition.append(" and a.distanceFlag='0'")
					//'代理人、经纪人終止日期'
					.append(" and (a.validEndDate>=to_date('"+operateDate+"','yyyy-mm-dd') or a.validEndDate is null) order by a.id.agentCode " );
				}else if("4".equals(agentType)){
					condition.append(" and a.uniteCod like '" + handlerIdentifyNumber + "'");
				//批次报价查询代理
				}else if ("P".equals(agentType)){
					condition.append(" and a.identifyNumber like '" + handlerIdentifyNumber + "%'");
				}

		}	
		hql.append("select a").append(" from PrpDagent a ").append(condition);
		hql2.append("select a").append(" from PrpYDDagent a ").append("where a.agentId like '"+handlerIdentifyNumber+"%'  and a.verifyRemark = '1' ");
		//删除与险别的关联关系 by xuli 20130528

		if (pageNo == 0 || pageSize == 0) {
			StringBuffer sql = new StringBuffer(256);
			//删除与险别的关联关系 by xuli 20130528
			/*sql.append("select count(*) from PrpDagent a,PrpDagentExt b")
					.append(condition);*/
			sql.append("select count(*) from PrpDagent a").append(condition);
			//删除与险别的关联关系 by xuli 20130528
			long count = (Long) super.findByHql(sql.toString()).get(0);

			if (count > 1000) {
				logger.error("系统" + systemCode + "查询记录数过大，不予返回！");
				throw new Exception("系统"+systemCode+"调用接口"+ServiceInfoConst.GETAGENT + "查询记录数过大，请缩小查询范围");
			} else {
				list = super.findByHql(hql.toString());
			}
			if(list==null){
				list = super.findByHql(hql2.toString());
			}
			dictPage.setData(list);
	   }else{
			Page page=null;
			Page page2=new Page();
			if("5".equals(agentType)){
				StringBuffer hql7=new StringBuffer(256);
				hql7.append("select a").append(" from PrpYDDagent a ").append("where a.unitCode like '"+handlerIdentifyNumber+"'");
				page = super.findByHql(hql7.toString(), pageNo, pageSize);
				list = page.getResult();
			}else{
				page = super.findByHql(hql.toString(), pageNo, pageSize);
				list = page.getResult();
				if("1".equals(agentType)){
					if(list.size()==0&&!"".equals(handlerIdentifyNumber)){
						page = super.findByHql(hql2.toString(), pageNo, pageSize);
						list = page.getResult();
					}else if("".equals(handlerIdentifyNumber)&&"21,22,23,31,32".indexOf(businessNature)>-1){
						if("".equals(businessNature)){//注意：这里的businessNature为通路别
						    page2 = super.findByHql(hql2.toString(), pageNo, pageSize);
						}else{
							hql2.append("and a.unitCode in ( select b.uniteCod from PrpDagent b where b.channelType = '"+businessNature+"')");
							page2 = super.findByHql(hql2.toString(), pageNo, pageSize);
							list.removeAll(list);
							list.addAll(page2.getResult());
						}
						if(pageNo>=page.getTotalPageCount()&&"".equals(businessNature)){
							//如果prpdagent最后一页处理完   就开始查prpyddagent
							Page page1EndPage=super.findByHql(hql.toString(), (int)page.getTotalPageCount(), pageSize);
							List list1=page1EndPage.getResult();
							int count1=list1.size();
							Page page2EndPage=super.findByHql(hql2.toString(), (int)page2.getTotalPageCount(), pageSize);
							List list2=page2EndPage.getResult();
							int count2=list2.size();
							int pageCountNow=0;
							//update by yjm 20151214 解决空白页的问题 start
							/*if(count1+count2>pageSize){
								pageCountNow=pageNo-(int)page.getTotalPageCount()+1;
							}else{
								pageCountNow=pageNo-(int)page.getTotalPageCount();
							}*/
							if(count1+count2>pageSize){
								pageCountNow=pageNo-(int)page.getTotalPageCount();
							}else{
								pageCountNow=pageNo-(int)page.getTotalPageCount()+1;
							}
							//update by yjm 20151214 解决空白页的问题 end
							page2 = super.findByHql(hql2.toString(), pageCountNow, pageSize);
							list.removeAll(list);
							list.addAll(page2.getResult());
						}
						Page page3=new Page(0, page.getTotalCount()+page2.getTotalCount(), pageSize, list);
						dictPage.setData(list);
						dictPage.setPageNo(pageNo);
						dictPage.setPageSize(pageSize);
						dictPage.setPageCount(page3.getTotalPageCount());
						dictPage.setTotalRecordCount(page3.getTotalCount());
						return dictPage;
					}
				}
			}
			dictPage.setData(list);
			dictPage.setPageNo(pageNo);
			dictPage.setPageSize(pageSize);
			dictPage.setPageCount(page.getTotalPageCount());
			dictPage.setTotalRecordCount(page.getTotalCount());
		}
		return dictPage;
	}

	// 单证系统的调用接口
	public DictPage getAgent(String systemCode, String agentCode, int pageNo,
			int pageSize) throws Exception {
		DictPage dictPage = new DictPage();
		StringBuffer hql = new StringBuffer(256);
		List list = new ArrayList();
		StringBuffer condition = new StringBuffer(256);
		hql.append(" from PrpDagent a ");
		condition.append(" where a.validStatus = 1");
		if (agentCode != null && !"".equals(agentCode)) {
			condition.append(" and a.agentCode like '" + agentCode + "%'");
		}
		hql.append(condition);
		hql.append(" and substr(a.flag,2,1) = 'N'");
		hql.append(" and length(a.agentType) = 6 ");
		if (pageNo == 0 || pageSize == 0) {
			int count = getCount("dms", "PrpDagent", condition.toString());
			if (count > 1000) {
				logger.error("系统" + systemCode + "调用接口"
						+ ServiceInfoConst.GETAGENTBYCODE + "查询记录数过大，请缩小查询范围");
				throw new Exception("系统" + systemCode + "调用接口"
						+ ServiceInfoConst.GETAGENTBYCODE + "查询记录数过大，请缩小查询范围");
			} else {
				list = super.findByHql(hql.toString());
			}
			dictPage.setData(list);
		} else {
			Page page = super.findByHql(hql.toString(), pageNo, pageSize);
			list = page.getResult();
			dictPage.setData(list);
			dictPage.setPageNo(pageNo);
			dictPage.setPageSize(pageSize);
			dictPage.setPageCount(page.getTotalPageCount());
			dictPage.setTotalRecordCount(page.getTotalCount());
		}
		return dictPage;
	}

	/**
	 * @param systemCode
	 *            系统代码
	 * @param riskCode
	 *            险种代码
	 * @param engageCode
	 *            特约代码
	 * @param reverseType
	 *            1:engageCode→oldEngageCode 2:oldEngageCode→engageCode
	 * @param pageNO
	 * @param pageSize
	 * @author wanghaibo 2011-01-19
	 * @return
	 * @throws Exception
	 */
	public DictPage getReverseRiskEngage(String systemCode, Map values)
			throws Exception {
		int pageNo = (Integer) values.get("pageNO");
		int pageSize = (Integer) values.get("pageSize");
		String riskCode = (String) values.get("riskCode");
		String engageCode = (String) values.get("engageCode");
		String reverseType = (String) values.get("reverseType");
		DictPage dictPage = new DictPage();
		StringBuffer hql = new StringBuffer("from PrpDriskEngage a where 1=1");
		StringBuffer condition = new StringBuffer(256);
		List list = new ArrayList();
		if ("2".equals(reverseType)) {
			if (riskCode != null && !"".equals(riskCode)) {
				condition.append(" and a.id.riskCode like '").append(riskCode)
						.append("%'");
			}
			if (engageCode != null && !"".equals(engageCode)) {
				condition.append(" and a.oldEngageCode like '")
						.append(engageCode).append("%'");

			}
		} else {
			if (riskCode != null && !"".equals(riskCode)) {
				condition.append(" and a.id.riskCode like '").append(riskCode)
						.append("%'");
			}
			if (engageCode != null && !"".equals(engageCode)) {
				condition.append(" and a.id.engageCode like '")
						.append(engageCode).append("%'");
			}
		}
		condition.append(" and a.validInd = 1");
		hql.append(condition);
		if (pageNo == 0 || pageSize == 0) {
			int count = getCount("dms", "PrpDriskEngage",
					condition.substring(4));
			if (count > 1000) {
				logger.error("系统" + systemCode + "查询记录数过大，不予返回！");
				throw new Exception("系统" + systemCode + "调用接口"
						+ ServiceInfoConst.GETREVERRISKIENGAGE
						+ "查询记录数过大，请缩小查询范围");
			} else {
				list = super.findByHql(hql.toString());
			}
			dictPage.setData(list);
		} else {
			Page page = super.findByHql(hql.toString(), pageNo, pageSize);
			list = page.getResult();
			dictPage.setData(list);
			dictPage.setPageNo(pageNo);
			dictPage.setPageSize(pageSize);
			dictPage.setPageCount(page.getTotalPageCount());
			dictPage.setTotalRecordCount(page.getTotalCount());
		}
		return dictPage;
	}

	/**
	 * @param systemCode
	 *            系统代码
	 * @param riskCode
	 *            险种代码
	 * @param limitCode
	 *            限额/免赔代码
	 * @param reverseType
	 *            1:limitCode→oldLimitCode 2:oldLimitCode→limitCode
	 * @param pageNO
	 * @param pageSize
	 * @author wanghaibo 2011-01-19
	 * @return
	 * @throws Exception
	 */
	public DictPage getRiskLimit(String systemCode, Map values)
			throws Exception {
		int pageNo = (Integer) values.get("pageNO");
		int pageSize = (Integer) values.get("pageSize");
		String riskCode = (String) values.get("riskCode");
		String limitCode = (String) values.get("limitCode");
		String reverseType = (String) values.get("reverseType");
		DictPage dictPage = new DictPage();
		StringBuffer hql = new StringBuffer("from PrpDriskLimit a where 1=1");
		StringBuffer condition = new StringBuffer(256);
		List list = new ArrayList();
		if ("2".equals(reverseType)) {
			if (riskCode != null && !"".equals(riskCode)) {
				condition.append(" and a.id.riskCode like '").append(riskCode)
						.append("%'");
			}
			if (limitCode != null && !"".equals(limitCode)) {
				condition.append(" and a.oldLimitCode like '")
						.append(limitCode).append("%'");

			}
		} else {
			if (riskCode != null && !"".equals(riskCode)) {
				condition.append(" and a.id.riskCode like '").append(riskCode)
						.append("%'");
			}
			if (limitCode != null && !"".equals(limitCode)) {
				condition.append(" and a.id.limitCode like '")
						.append(limitCode).append("%'");
			}
		}
		condition.append(" and a.validInd = 1");
		hql.append(condition);
		if (pageNo == 0 || pageSize == 0) {
			int count = getCount("dms", "PrpDriskLimit", condition.substring(4));
			if (count > 1000) {
				logger.error("系统" + systemCode + "查询记录数过大，不予返回！");
				throw new Exception("系统" + systemCode + "调用接口"
						+ ServiceInfoConst.GETRISKILIMIT + "查询记录数过大，请缩小查询范围");
			} else {
				list = super.findByHql(hql.toString());
			}
			dictPage.setData(list);
		} else {
			Page page = super.findByHql(hql.toString(), pageNo, pageSize);
			list = page.getResult();
			dictPage.setData(list);
			dictPage.setPageNo(pageNo);
			dictPage.setPageSize(pageSize);
			dictPage.setPageCount(page.getTotalPageCount());
			dictPage.setTotalRecordCount(page.getTotalCount());
		}
		return dictPage;
	}

	/**
	 * @param systemCode
	 *            系统代码
	 * @param riskCode
	 *            险种代码
	 * @param itemCode
	 *            标的代码
	 * @param reverseType
	 *            1:itemCode→oldItemCode 2:oldItemCode→itemCode
	 * @param pageNO
	 * @param pageSize
	 * @author wanghaibo 2011-01-19
	 * @return
	 * @throws Exception
	 */
	public DictPage getRiskItem(String systemCode, Map values) throws Exception {
		int pageNo = (Integer) values.get("pageNO");
		int pageSize = (Integer) values.get("pageSize");
		String riskCode = (String) values.get("riskCode");
		String itemCode = (String) values.get("itemCode");
		String reverseType = (String) values.get("reverseType");
		DictPage dictPage = new DictPage();
		StringBuffer hql = new StringBuffer("from PrpDriskItem a where 1=1");
		StringBuffer condition = new StringBuffer(256);
		List list = new ArrayList();
		if ("2".equals(reverseType)) {
			if (riskCode != null && !"".equals(riskCode)) {
				condition.append(" and a.id.riskCode like '").append(riskCode)
						.append("%'");
			}
			if (itemCode != null && !"".equals(itemCode)) {
				condition.append(" and a.oldItemCode like '").append(itemCode)
						.append("%'");

			}
		} else {
			if (riskCode != null && !"".equals(riskCode)) {
				condition.append(" and a.id.riskCode like '").append(riskCode)
						.append("%'");
			}
			if (itemCode != null && !"".equals(itemCode)) {
				condition.append(" and a.id.itemCode like '").append(itemCode)
						.append("%'");
			}
		}
		condition.append(" and a.validInd = 1");
		hql.append(condition);
		if (pageNo == 0 || pageSize == 0) {
			int count = getCount("dms", "PrpDriskItem", condition.substring(4));
			if (count > 1000) {
				logger.error("系统" + systemCode + "查询记录数过大，不予返回！");
				throw new Exception("系统" + systemCode + "调用接口"
						+ ServiceInfoConst.GETRISKITEM + "查询记录数过大，请缩小查询范围");
			} else {
				list = super.findByHql(hql.toString());
			}
			dictPage.setData(list);
		} else {
			Page page = super.findByHql(hql.toString(), pageNo, pageSize);
			list = page.getResult();
			dictPage.setData(list);
			dictPage.setPageNo(pageNo);
			dictPage.setPageSize(pageSize);
			dictPage.setPageCount(page.getTotalPageCount());
			dictPage.setTotalRecordCount(page.getTotalCount());
		}
		return dictPage;
	}

	/**
	 * @param systemCode
	 *            系统代码
	 * @param riskCode
	 *            险种代码
	 * @param clauseCode
	 *            条款代码
	 * @param reverseType
	 *            1:clauseCode→oldClauseCode 2:oldClauseCode→clauseCode
	 * @param queryType
	 *            查询类型：0（默认）.条款查询、1.条款内容查询
	 * @param firstLevel
	 *            条款类型：1.扩展类、2.限制类、3.规范类
	 * @param pageNO
	 * @param pageSize
	 * @author wanghaibo 2010-12-31 add queryType,firstLevel
	 * @return
	 * @throws Exception
	 */
	public DictPage getRiskClause(String systemCode, Map values)
			throws Exception {
		int pageNo = (Integer) values.get("pageNO");
		int pageSize = (Integer) values.get("pageSize");
		String riskCode = (String) values.get("riskCode");
		String clauseCode = (String) values.get("clauseCode");
		String clauseCodes = (String) values.get("clauseCodes");
		String reverseType = (String) values.get("reverseType");
		String queryType = (String) values.get("queryType");
		String firstLevel = (String) values.get("firstLevel");
		String validStatus = (String) values.get("validStatus");
		String comCode = (String) values.get("comCode");// add by zhupeng
														// 20120214
														// reason:增加条款机构代码查询
		DictPage dictPage = new DictPage();
		StringBuffer hql = new StringBuffer("from PrpDriskClause a where 1=1");
		StringBuffer condition = new StringBuffer(256);
		List list = new ArrayList();
		String classCode = null;
		DictPage riskPage = this.getRisk("dms", null, riskCode, "1", 0, 0);
		List<PrpDrisk> riskList = riskPage.getData();
		if (!riskList.isEmpty()) {
			classCode = riskList.get(0).getClassCode();
		}
		if ("2".equals(reverseType)) {
			if (riskCode != null && !"".equals(riskCode)) {
				condition.append(" and a.id.riskCode like '").append(riskCode)
						.append("%'");
			}
			if (clauseCode != null && !"".equals(clauseCode)) {
				condition.append(" and a.oldClauseCode like '%")
						.append(clauseCode).append("%'");
			}
			if (clauseCodes != null && !"".equals(clauseCodes)) {
				condition.append(" and a.oldClauseCode in (")
						.append(clauseCodes).append(")");
			}
		} else {
			if (riskCode != null && !"".equals(riskCode)) {
				condition.append(" and a.id.riskCode like '").append(riskCode)
						.append("%'");
			}
			if (clauseCode != null && !"".equals(clauseCode)) {
				condition.append(" and a.id.clauseCode like '")
						.append(clauseCode).append("%'");
			}
			if (clauseCodes != null && !"".equals(clauseCodes)) {
				condition.append(" and a.id.clauseCode in (")
						.append(clauseCodes).append(")");
			}
		}
		if (!"1".equals(queryType)) {
			if ("01,03".indexOf(classCode) >= 0) {
				condition.append(" and a.firstLevel != '3' ");
			}
		} else {
			condition.append(" and a.firstLevel ='").append(firstLevel)
					.append("'");
		}
		if (!"9".equals(validStatus)) {
			if ("0".equals(validStatus)) {
				condition.append(" and a.validInd = 0");
			} else {
				condition.append(" and a.validInd = 1");
				condition
						.append(" and (sysdate between validdate and invaliddate or sysdate>= validdate and invaliddate is null )");
			}
		}
		// modify add by zhupeng 2012-02-14 reason:增加保单归属机构与特别约定适用区域控制
		if (comCode != null && !"".equals(comCode)) {
			PrpDcompany prpDCompany = prpDcompanyService
					.getPrpDcompany(comCode);
			String upperCompany = prpDCompany.getUpperPath();
			String[] upperCompanys = upperCompany.split(",");
			hql.append(" and (areaCode is null or  areaCode like  '%31000000%' ");
			for (String company : upperCompanys) {
				hql.append("or areaCode " + " like '%" + company + "'");
			}
			hql.append(")");
		}
		// modify end by zhupeng 2012-02-14 reason:增加保单归属机构与特别约定适用区域控制
		StringBuffer order = new StringBuffer(condition)
				.append(" order by a.clauseAttribute");
		hql.append(condition);
		if (pageNo == 0 || pageSize == 0) {
			int count = getCount("dms", "PrpDriskClause",
					condition.substring(4));
			if (count > 1000) {
				logger.error("系统" + systemCode + "查询记录数过大，不予返回！");
				throw new Exception("系统" + systemCode + "调用接口"
						+ ServiceInfoConst.GETRISKCLAUSE + "查询记录数过大，请缩小查询范围");
			} else {
				list = super.findByHql(hql.append(order).toString());
				if ("0".equals(queryType)) {
					for (int i = 0; i < list.size(); i++) {
						PrpDriskClause prpdRiskClause = (PrpDriskClause) list
								.get(i);
						prpdRiskClause.setClauseDesc(null);
						prpdRiskClause.setClauseEDesc(null);
					}
				}
			}
			dictPage.setData(list);
		} else {
			Page page = super.findByHql(hql.append(order).toString(), pageNo,
					pageSize);
			list = page.getResult();
			if ("0".equals(queryType)) {
				for (int i = 0; i < list.size(); i++) {
					PrpDriskClause prpdRiskClause = (PrpDriskClause) list
							.get(i);
					prpdRiskClause.setClauseDesc(null);
					prpdRiskClause.setClauseEDesc(null);
				}
			}
			dictPage.setData(list);
			dictPage.setPageNo(pageNo);
			dictPage.setPageSize(pageSize);
			dictPage.setPageCount(page.getTotalPageCount());
			dictPage.setTotalRecordCount(page.getTotalCount());
		}
		return dictPage;
	}

	public DictPage getAccountInfo(String systemCode, Map values)
			throws Exception {
		int pageNo = (Integer) values.get("pageNO");
		int pageSize = (Integer) values.get("pageSize");
		String riskCode = (String) values.get("riskCode");
		String clauseCode = (String) values.get("clauseCode");
		String kindCode = (String) values.get("kindCode");
		String accountType = (String) values.get("accountType");
		boolean hasFirstCon = false;
		DictPage dictPage = new DictPage();
		StringBuffer condition = new StringBuffer(32);
		StringBuffer hql = new StringBuffer(256);
		List list = new ArrayList();
		hql.append("from PrpDaccountInfo a");
		if (riskCode != null && !"".equals(riskCode)) {
			condition.append(hasFirstCon ? " and " : " where ");
			hasFirstCon = true;
			condition.append(" a.riskCode = '").append(riskCode).append("'");
		}
		if (clauseCode != null && !"".equals(clauseCode)) {
			condition.append(hasFirstCon ? " and " : " where ");
			hasFirstCon = true;
			condition.append(" a.clauseCode in ('PUB','").append(clauseCode)
					.append("')");
		}
		if (kindCode != null && !"".equals(kindCode)) {
			condition.append(hasFirstCon ? " and " : " where ");
			hasFirstCon = true;
			condition.append(" a.kindCode in ('PUB','").append(kindCode)
					.append("')");
		}
		if (accountType != null && !"".equals(accountType)) {
			condition.append(hasFirstCon ? " and " : " where ");
			hasFirstCon = true;
			condition.append(" a.accountType = '").append(accountType)
					.append("'");
		}

		condition.append(hasFirstCon ? " and " : " where ");
		hasFirstCon = true;
		condition.append(" a.validInd = 1");

		hql.append(condition);
		// condition.append(" where a.riskCode = '").append(riskCode).append(
		// "' and a.clauseCode = '").append(clauseCode).append(
		// "' and a.kindCode = '").append(kindCode).append(
		// "' and a.accountType = '").append(accountType).append(
		// "' and a.validInd = 1");
		if (pageNo == 0 || pageSize == 0) {
			int count = getCount(systemCode, "PrpDaccountInfo",
					condition.toString());
			if (count > 1000) {
				logger.error("系统" + systemCode + "调用接口"
						+ ServiceInfoConst.GETACCOUNTINFO + "查询记录数过大，请缩小查询范围");
				throw new Exception("系统" + systemCode + "调用接口"
						+ ServiceInfoConst.GETACCOUNTINFO + "查询记录数过大，请缩小查询范围");
			} else {
				list = super.findByHql(hql.toString());
			}
		} else {
			Page page = super.findByHql(hql.toString(), pageNo, pageSize);
			list = page.getResult();
			dictPage.setData(list);
			dictPage.setPageNo(pageNo);
			dictPage.setPageSize(pageSize);
			dictPage.setPageCount(page.getTotalPageCount());
			dictPage.setTotalRecordCount(page.getTotalCount());
		}
		dictPage.setData(list);
		return dictPage;
	}

	//add by xuli 20130623
	
	
	public DictPage getPrpDkindReport(String systemCode, Map values)throws Exception {
        int pageNo = 1;
        int pageSize = 10;
        String riskCode = (String) values.get("riskCode");
        String kindCode = (String) values.get("kindCode");
        String userNature = (String) values.get("userNature");
        StringBuffer hql;
        //        boolean deductibleType_flag = (Boolean)values.get("deductibleType_flag");
        DictPage dictPage = new DictPage();
        StringBuffer condition = new StringBuffer(256);
    	//sql="select a from PrpDclauseReport a,PrpDriskClause b where a.id.clauseCode = b.id.clauseCode and b.id.riskCode='"+riskCode+"'";
        //StringBuffer hql = new StringBuffer("from PrpDkindReport where 1=1  ");
        if(null!=riskCode && ("A01".equals(riskCode)||"B01".equals(riskCode))) {
            boolean deductibleType_flag = (Boolean)values.get("deductibleType_flag");
            hql = new StringBuffer("from PrpDkindReport where 1=1  ");           
            List list = new ArrayList();
            if (riskCode != null && !"".equals(riskCode)) {
                condition.append(" and id.riskCode ='").append(riskCode).append("'");
            }
            if (kindCode != null && !"".equals(kindCode)) {
                condition.append(" and id.kindCode ='").append(kindCode).append("'");
            }
            if (userNature != null && !"".equals(userNature)) {
                condition.append(" and id.userNature in('").append(userNature).append("'");
            }
            if (deductibleType_flag) {
    			if (condition.indexOf("userNature") != -1) {
    				condition.append(" ,'3')");
    			}else{
    				 condition.append(" and userNature ='3'");
    			}
            }else{
    			if (condition.indexOf("userNature") != -1) {
    				condition.append(")");
    			}
            }
            hql.append(condition);
    		if (pageNo == 0 || pageSize == 0) {
    			StringBuffer sql = new StringBuffer(256);
    			sql.append("select count(*) from PrpDkindReport  where 1=1 and").append(condition);
    			//删除与险别的关联关系 by xuli 20130528
    			long count = (Long) super.findByHql(sql.toString()).get(0);
    			if (count > 1000) {
    				logger.error("系统" + systemCode + "查询记录数过大，不予返回！");
    				throw new Exception("系统"+systemCode+"调用接口"+ServiceInfoConst.GETPRPDKINDREPORT + "查询记录数过大，请缩小查询范围");
    			} else {
    				list = super.findByHql(hql.toString());
    			}
    			dictPage.setData(list);
    		} else {
    			Page page = super.findByHql(hql.toString(), pageNo, pageSize);
    			list = page.getResult();
    			dictPage.setData(list);
    			
    			dictPage.setPageNo(pageNo);
    			dictPage.setPageSize(pageSize);
    			dictPage.setPageCount(page.getTotalPageCount());
    			dictPage.setTotalRecordCount(page.getTotalCount());
    		}
        } else {
        	hql = new StringBuffer("select a from PrpDclauseReport a,PrpDriskClause b where 1=1 and a.id.clauseCode = b.id.clauseCode ");
            List<PrpDclauseReport> list = new ArrayList<PrpDclauseReport>();
            List<PrpDkindReport> listReport=new ArrayList<PrpDkindReport>();
            /*if (riskCode != null && !"".equals(riskCode)) {
                condition.append(" and riskCode ='").append(riskCode).append("'");
            }*/
            if (riskCode != null && !"".equals(riskCode)) {
                condition.append(" and b.id.riskCode='"+riskCode+"'");
            }
            hql.append(condition);
    		if (pageNo == 0 || pageSize == 0) {
    			StringBuffer sql = new StringBuffer(256);
    			sql.append("select count(*) from PrpDclauseReport a,PrpDriskClause b where 1=1 and a.id.clauseCode = b.id.clauseCode ")
    			.append(condition);
    			//ɾ�����ձ�Ĺ�����ϵ by xuli 20130528
    			long count = (Long) super.findByHql(sql.toString()).get(0);
    			if (count > 1000) {
    				logger.error("ϵͳ" + systemCode + "��ѯ��¼������践�أ�");
    				throw new Exception("ϵͳ"+systemCode+"���ýӿ�"+ServiceInfoConst.GETPRPDKINDREPORT + "��ѯ��¼��������С��ѯ��Χ");
    			} else {
    				list = super.findByHql(hql.toString());
    				System.out.println(list.size()+"======================");
    				for(PrpDclauseReport entity:list){
    					PrpDkindReport report=new PrpDkindReport();
    					if(""!=entity.getId().getReportNo()&&null!=entity.getId().getReportNo())
    						report.setReportNo(entity.getId().getReportNo());
    					if(""!=entity.getId().getClauseCode()&&null!=entity.getId().getClauseCode())
    						report.setClauseCode(entity.getId().getClauseCode());
    					if(""!=entity.getId().getVersionno()&&null!=entity.getId().getVersionno())
    						report.setVersionNo(entity.getId().getVersionno());
    					if(""!=riskCode&&null!=riskCode)
    						report.getId().setRiskCode(riskCode);
    					if(""!=userNature&&null!=userNature)
    						report.getId().setUserNature(userNature);
    					if(null!=entity.getValidDate())
    						report.setValidDate(entity.getValidDate());
    					if(null!=entity.getInvalidDate())
    						report.setInValidDate(entity.getInvalidDate());
    					listReport.add(report);
    				}
    			}
    			dictPage.setData(listReport);
    		} else {
    			StringBuffer sql = new StringBuffer(256);
    			sql.append("select count(*) from PrpDclauseReport a,PrpDriskClause b where 1=1 and a.id.clauseCode = b.id.clauseCode ")
    			.append(condition);
    			//ɾ�����ձ�Ĺ�����ϵ by xuli 20130528
    			long count = (Long) super.findByHql(sql.toString()).get(0);
    			System.out.println(count+"======================");
    			System.out.println(hql);
    			Page page = super.findByHql(hql.toString(), pageNo, pageSize);
    			list = page.getResult();
    			if(list==null||list.size()==0)
    				return null;
    			for(PrpDclauseReport entity:list){
    				PrpDkindReport report=new PrpDkindReport();
    				if(""!=entity.getId().getReportNo()&&null!=entity.getId().getReportNo())
    					report.setReportNo(entity.getId().getReportNo());
    				if(""!=entity.getId().getClauseCode()&&null!=entity.getId().getClauseCode())
    					report.setClauseCode(entity.getId().getClauseCode());
    				if(""!=entity.getId().getVersionno()&&null!=entity.getId().getVersionno())
    					report.setVersionNo(entity.getId().getVersionno());
//    				if(""!=riskCode&&null!=riskCode)
//    					report.getId().setRiskCode(riskCode);
//    				if(""!=userNature&&null!=userNature)
//    					report.getId().setUserNature(userNature);
    				if(null!=entity.getValidDate())
    					report.setValidDate(entity.getValidDate());
    				if(null!=entity.getInvalidDate())
    					report.setInValidDate(entity.getInvalidDate());
    				
    				listReport.add(report);
    			}
    			System.out.println("couoooooooooo+"+listReport.get(0).getReportNo());
    			dictPage.setData(listReport);
    			
    			dictPage.setPageNo(pageNo);
    			dictPage.setPageSize(pageSize);
    			dictPage.setPageCount(page.getTotalPageCount());
    			dictPage.setTotalRecordCount(page.getTotalCount());
    		}
            System.out.println("dictpage count:================"+dictPage.getData().size());            
        }
        return dictPage;
        
    }

	//add by linzhongxia 
	
	
	public DictPage getPrpDkindProduct(String systemCode, Map values){
        int pageNo = (Integer) values.get("pageNO");
        int pageSize = (Integer) values.get("pageSize");
        String riskCode = (String) values.get("riskCode");
        String kindCode = (String) values.get("kindCode");
        String userNature = (String) values.get("userNature");
        DictPage dictPage = new DictPage();
        StringBuffer hql = new StringBuffer("from PrpDkindProduct where 1=1 and ");
        StringBuffer condition = new StringBuffer(256);
        List list = new ArrayList();
        if (riskCode != null && !"".equals(riskCode)) {
            condition.append(" riskCode ='").append(riskCode).append("' and ");
        }
        if (kindCode != null && !"".equals(kindCode)) {
            condition.append(" kindCode ='").append(kindCode).append("' and ");
        }
        if (userNature != null && !"".equals(userNature)) {
            condition.append(" userNature = '").append(userNature).append("'");
        }
        hql.append(condition);
        Page page = super.findByHql(hql.toString(), pageNo, pageSize);
        list = page.getResult();
        dictPage.setData(list);
        dictPage.setPageNo(pageNo);
        dictPage.setPageSize(pageSize);
        dictPage.setPageCount(page.getTotalPageCount());
        dictPage.setTotalRecordCount(page.getTotalCount());
        
        return dictPage;
    }
	
	
	//begin add by zhongjiang 此方法用于获取险别对应适用车型信息 end
	public DictPage getAllowcarKind(String systemCode,Map values) {
		DictPage dictPage = new DictPage();
		int pageNo = (Integer) values.get("pageNO");
	    int pageSize = (Integer) values.get("pageSize");
		String riskCode = (String) values.get("riskCode");
	    String kindCode = (String) values.get("kindCode");
	    List arraylist=new ArrayList();
		StringBuffer hql=new StringBuffer("from PrpDkindCar where 1=1 and ");
		StringBuffer stringbuff=new StringBuffer(); 
		if(kindCode!=null){ 
			stringbuff.append(" kindCode ='").append(kindCode).append("' and ");
		}
		if(riskCode!=null && !"".equals(riskCode)){
			stringbuff.append(" riskCode ='").append(riskCode).append("' ");
		}
			hql.append(stringbuff);
			Page  page= super.findByHql(hql.toString(), pageNo, pageSize);
			arraylist=page.getResult();
			dictPage.setData(arraylist);
			dictPage.setPageNo(pageNo);
		    dictPage.setPageSize(pageSize);
		    dictPage.setPageCount(page.getTotalPageCount());
	        dictPage.setTotalRecordCount(page.getTotalCount());
		return dictPage;
	}

	
	// added by yuyiqiang 20130226 begin 条款、险别关系查询
	public DictPage getPrpDclauseKind(String systemCode, Map values)
			throws Exception {
		int pageNo = 1;
		int pageSize = 50;
		String riskCode = (String) values.get("riskCode");
		String clauseCode = (String) values.get("clauseCode");
		String kindCode = (String) values.get("kindCode");
		String validStatus = (String) values.get("validStatus");
		String reverseType = (String) values.get("reverseType");
		String codeType = (String) values.get("codeType");
		//add by linzhongxia
		String clauseType = (String) values.get("clauseType");
		String operate    = (String) values.get("operate");
		
		DictPage dictPage = new DictPage();
		StringBuffer condition = new StringBuffer(256);
		// StringBuffer hql = new
		// StringBuffer("from PrpDriskClauseKind a where 1=1");
		StringBuffer hql = new StringBuffer("");
		List list = new ArrayList();
		List prpDclauseKindlist = new ArrayList();
		PrpDclauseKind prpDclauseKind = new PrpDclauseKind();
		PrpDclauseKindId prpDclauseKindId = new PrpDclauseKindId();
		String sql ="";
        if("check".equals(operate)){
           sql = "from PrpDclauseKind where riskcode='"+riskCode+"' and clauseType='"+clauseType+"' and kindCode='"+kindCode+"'";
        }else {
           sql = "select a.id.riskCode,a.id.clauseType,a.id.kindCode,a.id.relateKindCode,a.flag FROM PrpDclauseKind a, PrpDcode b, PrpDkind c WHERE b.id.codeType='"
                + clauseCode
                + "' AND a.id.clauseType=b.id.codeCode AND b.validStatus='1' AND a.id.riskCode='"
                + riskCode
                + "' AND c.id.riskCode=a.id.riskCode AND a.id.relateKindCode=c.id.kindCode AND c.validStatus='1'"; 
        }  
	    hql.append(sql);

	    if (pageNo == 0 || pageSize == 0) {
	  	  int count = getCount("dms", "PrpDclauseKind",
				condition.substring(4));
		  if (count > 1000) {
			  logger.error("系统" + systemCode + "查询记录数过大，不予返回！");
		      throw new Exception("系统" + systemCode + "调用接口"
					+ ServiceInfoConst.GETRISKCLAUSEKIND
					+ "查询记录数过大，请缩小查询范围");
		  } else {
		  	list = super.findByHql(hql.toString());
		  }
		    dictPage.setData(list);
	    } else {
		   Page page = super.findByHql(hql.toString(), pageNo, pageSize);
//		  list = super.findByHql(hql.toString());
//		  for (int i = 0; i < list.size(); i++) {
//		  	Object[] arr = (Object[]) list.get(i);
//		  	prpDclauseKindId.setRiskCode(arr[0] + "");// 注意：不能使用toString()，防止空指针
//		  	prpDclauseKindId.setClauseType(arr[1] + "");
//		   	prpDclauseKindId.setKindCode(arr[2] + "");
//		  	prpDclauseKindId.setRelateKindCode(arr[3] + "");
//		  	prpDclauseKind.setId(prpDclauseKindId);
//		  	prpDclauseKind.setFlag(arr[4] + "");
//
//			prpDclauseKindlist.add(prpDclauseKind);
//		  }
		   list = page.getResult();
		  dictPage.setData(list);
		  dictPage.setPageNo(pageNo);
		  dictPage.setPageSize(pageSize);
		  dictPage.setPageCount(page.getTotalPageCount());
		  dictPage.setTotalRecordCount(page.getTotalCount());
	   }
	   return dictPage;
	}

	// added by yuyiqiang 20130226 end
	public DictPage getRiskClauseKind(String systemCode, Map values)
			throws Exception {
		int pageNo = (Integer) values.get("pageNO");
		int pageSize = (Integer) values.get("pageSize");
		String riskCode = (String) values.get("riskCode");
		String clauseCode = (String) values.get("clauseCode");
		String kindCode = (String) values.get("kindCode");
		String validStatus = (String) values.get("validStatus");
		String reverseType = (String) values.get("reverseType");
		String ms_flag     = (String) values.get("ms_flag");
		//add by zhongjiang begin
		String useNatureCode=(String)values.get("useNatureCode");
		//add by zhongjiang end
		DictPage dictPage = new DictPage();
		StringBuffer condition = new StringBuffer(256);
		StringBuffer hql = new StringBuffer(
				"from PrpDriskClauseKind a where 1=1");
		List list = new ArrayList();
		if("3".equals(reverseType)){
            if (riskCode != null && !"".equals(riskCode)) {
                condition.append(" and a.id.riskCode = '").append(riskCode)
                        .append("'");
            }
            if (kindCode != null && !"".equals(kindCode)) {
                condition.append(" and a.kindCode like '").append(kindCode)
                        .append("'");
            }
		    
		}else if (!"2".equals(reverseType)) {
			if (riskCode != null && !"".equals(riskCode)) {
				condition.append(" and a.id.riskCode = '").append(riskCode).append("'");;
			}
			if (clauseCode != null && !"".equals(clauseCode)) {
				condition.append(" and a.id.clauseCode = '")
						.append(clauseCode).append("'");;
			}
			if (kindCode != null && !"".equals(kindCode)) {
				condition.append(" and (a.kindCode = '").append(kindCode).append("'");;
				condition.append(" or a.kindName = '").append(kindCode)
						.append("')");
			}
		} else {
			if (riskCode != null && !"".equals(riskCode)) {
				condition.append(" and a.id.riskCode = '").append(riskCode).append("'");;
			}
			if (clauseCode != null && !"".equals(clauseCode)) {
				condition.append(" and a.id.clauseCode = '")
						.append(clauseCode).append("'");;
			}
			if (kindCode != null && !"".equals(kindCode)) {
				condition.append(" and a.oldKindCode = '").append(kindCode).append("'");;
			}
		}
		if (!"9".equals(validStatus)) {
			if ("0".equals(validStatus)) {
				condition.append(" and a.validInd = 0");
			} else {
				condition.append(" and a.validInd = 1");
				condition
						.append(" and (sysdate between validdate and invaliddate or sysdate>= validdate and invaliddate is null )");
			}
		}
		if(!"".equals(ms_flag) && ms_flag != null){
		    if("M".equals(ms_flag)){
		        ms_flag = "1"; 
			}else if("T".equals(ms_flag)){
		        ms_flag = "3";
		    }else{

		        ms_flag = "2";
		    }
		    condition.append(" and a.tcol1 = '").append(ms_flag).append("'");		    
		}
		
		//add by zhongjiang begin
		if(!"".equals(useNatureCode) && useNatureCode != null){
		    condition.append(" and a.tcol2 = '").append(useNatureCode).append("'");		    
		}
		//add by zhongjiang end
		condition.append(" order by a.upperKindCode");// modify update by
														// renshuo 2011-10-17
		if("MC".equals(riskCode)||"OH".equals(riskCode)
				||"AV".equals(riskCode)||"CF".equals(riskCode)
				||"CL".equals(riskCode)||"FL".equals(riskCode)
				||"EV".equals(riskCode)||"EW".equals(riskCode)
				||"FV".equals(riskCode)||"FW".equals(riskCode)){
			condition.append(",a.kindCode");
		}
		hql.append(condition);
		if (pageNo == 0 || pageSize == 0) {
			int count = getCount("dms", "PrpDriskClauseKind",
					condition.substring(4));
			if (count > 1000) {
				logger.error("系统" + systemCode + "查询记录数过大，不予返回！");
				throw new Exception("系统" + systemCode + "调用接口"
						+ ServiceInfoConst.GETRISKCLAUSEKIND
						+ "查询记录数过大，请缩小查询范围");
			} else {
				list = super.findByHql(hql.toString());
			}
			dictPage.setData(list);
		} else {
			Page page = super.findByHql(hql.toString(), pageNo, pageSize);
			list = page.getResult();
			dictPage.setData(list);
			dictPage.setPageNo(pageNo);
			dictPage.setPageSize(pageSize);
			dictPage.setPageCount(page.getTotalPageCount());
			dictPage.setTotalRecordCount(page.getTotalCount());
		}
		return dictPage;
	}

	public DictPage getRiskClauseKindSub(String systemCode, Map values)
			throws Exception {
		int pageNo = (Integer) values.get("pageNO");
		int pageSize = (Integer) values.get("pageSize");
		String riskCode = (String) values.get("riskCode");
		String clauseCode = (String) values.get("clauseCode");
		String upperKindCode = (String) values.get("upperKindCode");
		String validStatus = (String) values.get("validStatus");
		String kindLevel = (String) values.get("kindLevel");
		DictPage dictPage = new DictPage();
		StringBuffer condition = new StringBuffer(256);
		StringBuffer hql = new StringBuffer(
				"from PrpDriskClauseKind a where 1=1");
		List list = new ArrayList();
		if (riskCode != null && !"".equals(riskCode)) {
			condition.append(" and a.id.riskCode = '").append(riskCode).append("'");
		}
		if (clauseCode != null && !"".equals(clauseCode)) {
			condition.append(" and a.id.clauseCode = '").append(clauseCode).append("'");
		}
		if (upperKindCode != null && !"".equals(upperKindCode)) {
			condition.append(" and (a.upperKindCode like '")
					.append(upperKindCode).append("%'");
			condition.append(" or a.upperKindName like '")
					.append(upperKindCode).append("%')");
		}
		if (kindLevel == null && "".equals(kindLevel)) {// modify update by
														// renshuo 2011-10-17
			condition.append(" and a.kindLevel = 2");
		} else {
			condition.append(" and a.kindLevel = ").append(kindLevel);
		}
		if (!"9".equals(validStatus)) {
			if ("0".equals(validStatus)) {
				condition.append(" and a.validInd = 0");
			} else {
				condition.append(" and a.validInd = 1");
				condition
						.append(" and (sysdate between validdate and invaliddate or sysdate>= validdate and invaliddate is null )");
			}
		}
		hql.append(condition);
		if (pageNo == 0 || pageSize == 0) {
			int count = getCount("dms", "PrpDriskClauseKind",
					condition.substring(4));// modify by renshuo 2011-07-13
											// reason:调整名称
			if (count > 1000) {
				logger.error("系统" + systemCode + "查询记录数过大，不予返回！");
				throw new Exception("系统" + systemCode + "调用接口"
						+ ServiceInfoConst.GETRISKCLAUSEKINDSUB
						+ "查询记录数过大，请缩小查询范围");// modify by renshuo 2011-07-13
												// reason:调整名称
			} else {
				list = super.findByHql(hql.toString());
			}
			dictPage.setData(list);
		} else {
			Page page = super.findByHql(hql.toString(), pageNo, pageSize);
			list = page.getResult();
			dictPage.setData(list);
			dictPage.setPageNo(pageNo);
			dictPage.setPageSize(pageSize);
			dictPage.setPageCount(page.getTotalPageCount());
			dictPage.setTotalRecordCount(page.getTotalCount());
		}
		return dictPage;
	}

	// modify add begin by renshuo 2011-07-12 reason:增加条款责任互斥条件查询
	public DictPage getRiskClauseKindRelation(String systemCode, Map values)
			throws Exception {
		int pageNo = (Integer) values.get("pageNO");
		int pageSize = (Integer) values.get("pageSize");
		String riskCode = (String) values.get("riskCode");
		String clauseCode = (String) values.get("clauseCode");
		String kindCode = (String) values.get("kindCode");
		String relationType = (String) values.get("relationType");// 关系类型
		String relationFlag = (String) values.get("ralationFlag");// 互斥或依赖标识
		DictPage dictPage = new DictPage();
		StringBuffer condition = new StringBuffer(256);
		StringBuffer hql = new StringBuffer(
				"from PrpDriskClauseKindRelation  where 1=1");
		List list = new ArrayList();
		if (riskCode != null && !"".equals(riskCode)) {
			condition.append(" and riskCode = '").append(riskCode)
					.append("'");
		}
		if (clauseCode != null && !"".equals(clauseCode)) {
			condition.append(" and clauseCode = '").append(clauseCode)
					.append("'");
		}
		if (StringUtils.isNotBlank(relationType)) {
			if ("1".equals(relationType)) {// 按条款进行显示
				condition.append(" and relationCode = '").append(clauseCode)
						.append("'");
				condition.append(" and relationType = '")
						.append(relationType).append("'");
			} else if ("2".equals(relationType)) {// 按责任进行显示
				condition.append(" and relationCode ='").append(clauseCode)
						.append("-").append(kindCode).append("'");
				condition.append(" and relationType = '")
						.append(relationType).append("'");
			} else {// 全部查询
				condition.append(" and relationCode = '")
						.append(clauseCode).append("'");
				condition.append(" and relationType is not null");
			}
		}
		if (StringUtils.isNotBlank(relationFlag)) {// 互斥或依赖关系
			condition.append(" and relationFlag = '").append(relationFlag)
					.append("'");
		}
		condition.append(" and validInd = 1");
		condition
				.append(" and (sysdate between validdate and invaliddate or sysdate>= validdate and invaliddate is null )");
		hql.append(condition);
		if (pageNo == 0 || pageSize == 0) {
			int count = getCount("dms", "PrpDriskClauseKindRelation",
					condition.substring(4));
			if (count > 1000) {
				logger.error("系统" + systemCode + "查询记录数过大，不予返回！");
				throw new Exception("系统" + systemCode + "调用接口"
						+ ServiceInfoConst.GETRISKCLAUSEKINDRELATION
						+ "查询记录数过大，请缩小查询范围");
			} else {
				list = super.findByHql(hql.toString());
			}
			dictPage.setData(list);
		} else {
			Page page = super.findByHql(hql.toString(), pageNo, pageSize);
			list = page.getResult();
			dictPage.setData(list);
			dictPage.setPageNo(pageNo);
			dictPage.setPageSize(pageSize);
			dictPage.setPageCount(page.getTotalPageCount());
			dictPage.setTotalRecordCount(page.getTotalCount());
		}
		return dictPage;
	}

	/**
	 * @author wanglianzhou 新增个人信息
	 * @param
	 * @return DictPage
	 */
	public DictPage savePrpDcustomerIdv(String systemCode, Map values)
			throws Exception {
		System.out.println("------------進入個人保存！----------------");
		String comCode = (String) values.get("comCode");
		PrpDcustomerIdv typeOne = (PrpDcustomerIdv) values
				.get("prpDcustomerIdv");
		cn.com.sinosoft.dms.model.PrpDcustomerIdv type = new cn.com.sinosoft.dms.model.PrpDcustomerIdv();
		PrpDcustomer prpDcustomer = new PrpDcustomer();
		if (typeOne != null) {
			if(null!=typeOne.getCustomerCode()&&!"".equals(typeOne.getCustomerCode())){
			   prpDcustomer = (PrpDcustomer) super.get(PrpDcustomer.class,
						typeOne.getCustomerCode());
			   //modify by liudezhen 20170216 start
			   if(prpDcustomer == null){
				   prpDcustomer = new PrpDcustomer();
			   }
			   //modify by liudezhen 20170216 end
			}
			prpDcustomer.setAddressCName(typeOne.getAddressCName());
			prpDcustomer.setAddressEName(typeOne.getAddressEName());
			prpDcustomer.setArticleCode(typeOne.getArticleCode());
			prpDcustomer.setBlackState(typeOne.getBlackState());
			prpDcustomer.setCustomerCName(typeOne.getCustomerCName());
			prpDcustomer.setCustomerEName(typeOne.getCustomerEName());
			prpDcustomer.setCustomerFlag(typeOne.getCustomerFlag());
			prpDcustomer.setCustomerType("1");
			prpDcustomer.setInputDate(typeOne.getInputDate());
			prpDcustomer.setOperatorCode(typeOne.getOperatorCode());
			prpDcustomer.setShortHandCode(typeOne.getShortHandCode());
			prpDcustomer.setValidStatus(typeOne.getValidStatus());
			//modify by fengyang 20140708 reason:增加对修改要保人时的判断
			String customerCode=typeOne.getCustomerCode();
			if(customerCode==null||"".equals(customerCode)){
			String strSqlStatement = "select for_customercode_9.nextval from dual ";
			//modify by liuxi 201308091607 begin
			//List templist = this.getHibernateTemplate().getSessionFactory().openSession().createSQLQuery(strSqlStatement).list();
			List templist = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(strSqlStatement).list();
			//modify by liuxi 201308091607 begin			
			String billNo = null ;
	      	 if(templist != null && !templist.isEmpty()){
	      		 billNo =((BigDecimal)templist.get(0)).toString();
	      	 }
			String iCustomerType = typeOne.getCustomerKind();
			String strCustomerCode ;
			if(null!=typeOne.getCustomerCode()&&!"".equals(typeOne.getCustomerCode())){
				strCustomerCode=typeOne.getCustomerCode();
			}else{
				if (iCustomerType.equals("1")) {
					strCustomerCode = "5" + comCode.trim().substring(0, 2)
							+ billNo;
				} else {
					strCustomerCode = "0" + comCode.trim().substring(0, 2)
							+ billNo;
				}
			}
			
			prpDcustomer.setCustomerCode(strCustomerCode); // added by wanglz
																// 20130424
			}else{
				prpDcustomer.setCustomerCode(typeOne.getCustomerCode());
			}
			// prpDcustomer.setCustomerType(typeOne.getCustomerKind());
			prpDcustomer.setCustomerType("1");
			prpDcustomer.setValidStatus("1");//��ʱд��  wlz 20130507
			Date date=new Date();
			type.setInputDate(date);
			type.setAccount(typeOne.getAccount());// �����˺�
			type.setAddressCName(typeOne.getAddressCName());// ��ַ�������
			type.setAddressEName(typeOne.getAddressEName());// ��ַӢ�����
			type.setAge(typeOne.getAge());// ����
			type.setArticleCode(typeOne.getArticleCode());type.setBank(typeOne.getBank());// ����
			type.setBirthDate(typeOne.getBirthDate());type.setBlackState(typeOne.getBlackState());// �����־
			type.setComCode(typeOne.getComCode());// ��ҵ�����
			type.setCreditLevel(typeOne.getCreditLevel());
			type.setCustomerCName(typeOne.getCustomerCName());type.setCustomerCode(typeOne.getCustomerCode());
			type.setCustomerEName(typeOne.getCustomerEName());type.setCustomerFlag(typeOne.getCustomerFlag());
			type.setCustomerKind(typeOne.getCustomerKind());type.setDeathDate(typeOne.getDeathDate());
			type.setEducationCode(typeOne.getEducationCode());type.setEmail(typeOne.getEmail());
			type.setFaxNumber(typeOne.getFaxNumber());type.setFlag(typeOne.getFlag());
			type.setIdentifyNumber(typeOne.getIdentifyNumber());type.setIdentifyType(typeOne.getIdentifyType());
			type.setHandlerCode(typeOne.getHandlerCode());//type.setInputDate(type.getInputDate());
			type.setLinkAddress(typeOne.getLinkAddress());type.setLowerViewFlag(typeOne.getLowerViewFlag());
			type.setMobile(typeOne.getMobile());type.setNetAddress(typeOne.getNetAddress());
			type.setNewCustomerCode(typeOne.getNewCustomerCode());type.setOccupationCode(typeOne.getOccupationCode());
			type.setOperatorCode(typeOne.getOperatorCode());
			type.setPager(typeOne.getPager());type.setPassword(typeOne.getPassword());
			type.setPhoneNumber(typeOne.getPhoneNumber());
			type.setPostCode(typeOne.getPostCode());type.setSex(typeOne.getSex());type.setShortHandCode(typeOne.getShortHandCode());
			type.setTopLevelFlag(typeOne.getTopLevelFlag());type.setUnit(typeOne.getUnit());
			type.setUnitAddress(typeOne.getUnitAddress());type.setUpdateDate(typeOne.getUpdateDate());
			type.setValidStatus(typeOne.getValidStatus());type.setNewCustomerCode(typeOne.getNewCustomerCode());
			type.setRoomPostCode(typeOne.getRoomPostCode());type.setRoomAddress(typeOne.getRoomAddress());
			type.setVerifyNumber(typeOne.getVerifyNumber());type.setLinkerName(typeOne.getLinkerName());
			type.setLoanAccount(typeOne.getLoanAccount());
			
			type.setOccupationName(typeOne.getOccupationName());
			type.setRoomPAreaNumber(typeOne.getRoomPAreaNumber());
			type.setRoomPExtNumber(typeOne.getRoomPExtNumber());
			type.setNationalityAddress(typeOne.getNationalityAddress());
			type.setPhoneAreaNumber(typeOne.getPhoneAreaNumber());
			type.setPhoneExtNumber(typeOne.getPhoneExtNumber());
			type.setIdentifyStartDate(typeOne.getIdentifyStartDate());
			type.setIdentifyEndDate(typeOne.getIdentifyEndDate());
			
			type.setCreditNumber(typeOne.getCreditNumber());type.setCollateralNumber(typeOne.getCollateralNumber());
			type.setLoansBehalfNumber(typeOne.getLoansBehalfNumber());type.setLoansDepartment(typeOne.getLoansDepartment());
			type.setMobileTelephone(typeOne.getMobileTelephone());
			if (type.getValidStatus() == null) {
				type.setValidStatus("1");
			}
		}
		super.save(prpDcustomer);
		prpDcustomer = (PrpDcustomer) super.get(PrpDcustomer.class,
				prpDcustomer.getCustomerCode());
		type.setPrpDcustomer(prpDcustomer);
		type.setCustomerCode(prpDcustomer.getCustomerCode());
		if (type.getNewCustomerCode() == null) {
			type.setNewCustomerCode(prpDcustomer.getCustomerCode());
		}
		super.save(type);
		DictPage dictPage = new DictPage();
		
			
		List<cn.com.sinosoft.dms.model.PrpDcustomerIdv> list = new ArrayList<cn.com.sinosoft.dms.model.PrpDcustomerIdv>();
		list.add(type);
		List<com.sinosoft.dmsdriver.model.PrpDcustomerIdv> list1 = new ArrayList<com.sinosoft.dmsdriver.model.PrpDcustomerIdv>();
		for (cn.com.sinosoft.dms.model.PrpDcustomerIdv typeIdv : list) {
			com.sinosoft.dmsdriver.model.PrpDcustomerIdv type1 = new com.sinosoft.dmsdriver.model.PrpDcustomerIdv();
			PrpDcustomer prpDcustomer1 = super.get(PrpDcustomer.class, typeIdv.getCustomerCode());
			Date date=new Date();
			type1.setInputDate(date);
			type1.setCustomerType(prpDcustomer1.getCustomerType());
			type1.setAccount(typeIdv.getAccount());type1.setAddressCName(typeIdv.getAddressCName());
			type1.setAddressEName(typeIdv.getAddressEName());type1.setAge(typeIdv.getAge());
			type1.setArticleCode(typeIdv.getArticleCode());type1.setBank(typeIdv.getBank());
			type1.setBirthDate(typeIdv.getBirthDate());type1.setBlackState(typeIdv.getBlackState());
			type1.setComCode(typeIdv.getComCode());type1.setCreditLevel(typeIdv.getCreditLevel());
			type1.setCustomerCName(typeIdv.getCustomerCName());type1.setCustomerCode(typeIdv.getCustomerCode());
			type1.setCustomerEName(typeIdv.getCustomerEName());type1.setCustomerFlag(typeIdv.getCustomerFlag());
			type1.setCustomerKind(typeIdv.getCustomerKind());type1.setDeathDate(typeIdv.getDeathDate());
			type1.setEducationCode(typeIdv.getEducationCode());type1.setEmail(typeIdv.getEmail());
			type1.setFaxNumber(typeIdv.getFaxNumber());type1.setFlag(typeIdv.getFlag());
			type1.setHandlerCode(typeIdv.getHandlerCode());type1.setHealth(typeIdv.getHealth());
			type1.setIdentifyNumber(typeIdv.getIdentifyNumber());type1.setIdentifyType(typeIdv.getIdentifyType());
			/*type1.setInputDate(typeIdv.getInputDate());*/type1.setLinkAddress(typeIdv.getLinkAddress());
			type1.setLowerViewFlag(typeIdv.getLowerViewFlag());type1.setMobile(typeIdv.getMobile());
			type1.setNetAddress(typeIdv.getNetAddress());type1.setNewCustomerCode(typeIdv.getNewCustomerCode());
			type1.setOccupationCode(typeIdv.getOccupationCode());type1.setOperatorCode(typeIdv.getOperatorCode());
			type1.setPager(typeIdv.getPager());type1.setPassword(typeIdv.getPassword());
			type1.setPhoneNumber(typeIdv.getPhoneNumber());type1.setPostCode(typeIdv.getPostCode());
			type1.setSex(typeIdv.getSex());type1.setShortHandCode(typeIdv.getShortHandCode());
			type1.setTopLevelFlag(typeIdv.getTopLevelFlag());type1.setUnit(typeIdv.getUnit());
			type1.setUnitAddress(typeIdv.getUnitAddress());type1.setUpdateDate(typeIdv.getUpdateDate());
			type1.setUpdaterCode(typeIdv.getUpdaterCode());type1.setValidStatus(typeIdv.getValidStatus());
			type1.setRoomPostCode(typeIdv.getRoomPostCode());type1.setRoomAddress(typeIdv.getRoomAddress());
            type1.setVerifyNumber(typeIdv.getVerifyNumber());type1.setLinkerName(typeIdv.getLinkerName());
            type1.setLoanAccount(typeIdv.getLoanAccount());
            
            type1.setOccupationName(typeIdv.getOccupationName());
            type1.setRoomPAreaNumber(typeIdv.getRoomPAreaNumber());
            type1.setRoomPExtNumber(typeIdv.getRoomPExtNumber());
            type1.setNationalityAddress(typeIdv.getNationalityAddress());
			type1.setPhoneAreaNumber(typeIdv.getPhoneAreaNumber());
			type1.setPhoneExtNumber(typeIdv.getPhoneExtNumber());
			type1.setIdentifyStartDate(typeIdv.getIdentifyStartDate());
			type1.setIdentifyEndDate(typeIdv.getIdentifyEndDate());
			
            type1.setCreditNumber(typeIdv.getCreditNumber());type1.setCollateralNumber(typeIdv.getCollateralNumber());
            type1.setLoansBehalfNumber(typeIdv.getLoansBehalfNumber());type1.setLoansDepartment(typeIdv.getLoansDepartment());
            type1.setMobileTelephone(typeIdv.getMobileTelephone());
            list1.add(type1);
		}
		dictPage.setData(list1);
		try {
			dictPage.setData(list1);
			dictPage.setTotalRecordCount(new Long(1));
		System.out.println("-----------------個人保存方法結束-----------------");
			return dictPage;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	
	/**
	 * 保存新增的船舶信息
	 * @author fengyang
	 * @param
	 * @return DictPage
	 */
	public DictPage savePrpDitemShip(String systemCode, Map values)
			throws Exception {
		String comCode = (String) values.get("comCode");
		PrpDitemShip typeOne = (PrpDitemShip) values
				.get("prpDitemShip");
		cn.com.sinosoft.dms.model.PrpDitemShip type = new cn.com.sinosoft.dms.model.PrpDitemShip();

		if (typeOne != null) {
			type.setCertificateOwner(typeOne.getCertificateOwner());type.setCheckEndDate(typeOne.getCheckEndDate());
			type.setCheckStartDate(typeOne.getCheckStartDate());type.setLoadTon(typeOne.getLoadTon());
			type.setMakeEndDate(typeOne.getMakeEndDate());type.setMakeStartDate(typeOne.getMakeStartDate());
			type.setManageStartDate(typeOne.getManageStartDate());type.setManageType(typeOne.getManageType());
			type.setMakeYearMonth(typeOne.getMakeYearMonth());
			type.setShipTypeCodeCName(typeOne.getShipTypeCodeCName());type.setUseNatureCodeCName(typeOne.getUseNatureCodeCName());
			type.setUseNatureCodeEName(typeOne.getUseNatureCodeEName());
			//type.setComCode(typeOne.getComCode());
			type.setOldShipEName(typeOne.getOldShipEName());type.setShipCode(typeOne.getShipCode());
			type.setOldShipName(typeOne.getOldShipName());type.setRegistrySite(typeOne.getRegistrySite());
			type.setRemark(typeOne.getRemark());type.setSeatCount(typeOne.getSeatCount());
			type.setShipCName(typeOne.getShipCName());type.setShipEName(typeOne.getShipEName());
			type.setShipFlag(typeOne.getShipFlag());type.setShipNo(typeOne.getShipNo());
			type.setShipOperator(typeOne.getShipOperator());type.setShipOwner(typeOne.getShipOwner());
			type.setShipPort(typeOne.getShipPort());type.setShipStruct(typeOne.getShipStruct());
			type.setShipTypeCode(typeOne.getShipTypeCode());type.setStepHull(typeOne.getStepHull());
			type.setTonCount(typeOne.getTonCount());type.setUseNatureCode(typeOne.getUseNatureCode());
		}
		super.save(type);
		DictPage dictPage = new DictPage();
		System.out.println(dictPage);
		
			
		List<cn.com.sinosoft.dms.model.PrpDitemShip> list = new ArrayList<cn.com.sinosoft.dms.model.PrpDitemShip>();
		list.add(type);
		List<com.sinosoft.dmsdriver.model.PrpDitemShip> list1 = new ArrayList<com.sinosoft.dmsdriver.model.PrpDitemShip>();
		for (cn.com.sinosoft.dms.model.PrpDitemShip typeIdv : list) {
			com.sinosoft.dmsdriver.model.PrpDitemShip type1 = new com.sinosoft.dmsdriver.model.PrpDitemShip();
			type1.setCertificateOwner(typeIdv.getCertificateOwner());type1.setCheckEndDate(typeIdv.getCheckEndDate());
			type1.setCheckStartDate(typeIdv.getCheckStartDate());type1.setLoadTon(typeIdv.getLoadTon());
			type1.setMakeEndDate(typeIdv.getMakeEndDate());type1.setMakeStartDate(typeIdv.getMakeStartDate());
			type1.setManageStartDate(typeIdv.getManageStartDate());type1.setManageType(typeIdv.getManageType());
			type1.setMakeYearMonth(typeIdv.getMakeYearMonth());
			type1.setShipTypeCodeCName(typeIdv.getShipTypeCodeCName());type1.setUseNatureCodeCName(typeIdv.getUseNatureCodeCName());
			type1.setUseNatureCodeEName(typeIdv.getUseNatureCodeEName());
			//type1.setComCode(typeIdv.getComCode());
			type1.setOldShipEName(typeIdv.getOldShipEName());type1.setShipCode(typeIdv.getShipCode());
			type1.setOldShipName(typeIdv.getOldShipName());type1.setRegistrySite(typeIdv.getRegistrySite());
			type1.setRemark(typeIdv.getRemark());type1.setSeatCount(typeIdv.getSeatCount());
			type1.setShipCName(typeIdv.getShipCName());type1.setShipEName(typeIdv.getShipEName());
			type1.setShipFlag(typeIdv.getShipFlag());type1.setShipNo(typeIdv.getShipNo());
			type1.setShipOperator(typeIdv.getShipOperator());type1.setShipOwner(typeIdv.getShipOwner());
			type1.setShipPort(typeIdv.getShipPort());type1.setShipStruct(typeIdv.getShipStruct());
			type1.setShipTypeCode(typeIdv.getShipTypeCode());type1.setStepHull(typeIdv.getStepHull());
			type1.setTonCount(typeIdv.getTonCount());type1.setUseNatureCode(typeIdv.getUseNatureCode());
			list1.add(type1);
		}
		dictPage.setData(list1);
		try {
			dictPage.setData(list1);
			dictPage.setTotalRecordCount(new Long(1));
			return dictPage;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * 保存新增的飞机信息
	 * @author fengyang
	 * @param
	 * @return DictPage
	 */
	public DictPage savePrpDplane(String systemCode, Map values)
			throws Exception {
		String comCode = (String) values.get("comCode");
		com.sinosoft.dmsdriver.model.PrpDplane typeOne = (com.sinosoft.dmsdriver.model.PrpDplane) values
				.get("prpDplane");
		cn.com.sinosoft.dms.model.PrpDplane type = new cn.com.sinosoft.dms.model.PrpDplane();

		if (typeOne != null) {
			type.setPlaneType(typeOne.getPlaneType());type.setRegistrationMarks(typeOne.getRegistrationMarks());
			type.setLabelNo(typeOne.getLabelNo());type.setLicenseNo(typeOne.getLicenseNo());
			type.setLicenseStartDate(typeOne.getLicenseStartDate());type.setLicenseEndDate(typeOne.getLicenseEndDate());
			type.setAirWorthinessNo(typeOne.getAirWorthinessNo());type.setAirWorthyStartDate(typeOne.getAirWorthyStartDate());
			type.setAirWorthyEndDate(typeOne.getAirWorthyEndDate());type.setBuildYear(typeOne.getBuildYear());
			type.setCrewSeatCapacity(typeOne.getCrewSeatCapacity());type.setPassengerSeatCapacity(typeOne.getPassengerSeatCapacity());
			type.setIdentificationMark(typeOne.getIdentificationMark());type.setEngineNo(typeOne.getEngineNo());
			type.setEngineType(typeOne.getEngineType());type.setRotorBladeType(typeOne.getRotorBladeType());
			type.setEquipmentItem(typeOne.getEquipmentItem());type.setTotalHoursMonth(typeOne.getTotalHoursMonth());
			type.setTotalHoursYear(typeOne.getTotalHoursYear());type.setTotalHoursNextYear(typeOne.getTotalHoursNextYear());
			type.setIsFlightNight(typeOne.getIsFlightNight());type.setFlightRouteSterritorial(typeOne.getFlightRouteSterritorial());
			type.setMaintainAdress(typeOne.getMaintainAdress());type.setKeepAdredd(typeOne.getKeepAdredd());
			type.setRotorsSetInmotion(typeOne.getRotorsSetInmotion());type.setNowPolicyCompany(typeOne.getNowPolicyCompany());
			type.setNowPolicyPeriod(typeOne.getNowPolicyPeriod());type.setLiabilityAgree(typeOne.getLiabilityAgree());
		}
		super.save(type);
		DictPage dictPage = new DictPage();
		System.out.println(dictPage);
		
			
		List<cn.com.sinosoft.dms.model.PrpDplane> list = new ArrayList<cn.com.sinosoft.dms.model.PrpDplane>();
		list.add(type);
		List<com.sinosoft.dmsdriver.model.PrpDplane> list1 = new ArrayList<com.sinosoft.dmsdriver.model.PrpDplane>();
		for (cn.com.sinosoft.dms.model.PrpDplane typeIdv : list) {
			com.sinosoft.dmsdriver.model.PrpDplane type1 = new com.sinosoft.dmsdriver.model.PrpDplane();
			type1.setPlaneType(typeIdv.getPlaneType());type1.setRegistrationMarks(typeIdv.getRegistrationMarks());
			type1.setLabelNo(typeIdv.getLabelNo());type1.setLicenseNo(typeIdv.getLicenseNo());
			type1.setLicenseStartDate(typeIdv.getLicenseStartDate());type1.setLicenseEndDate(typeIdv.getLicenseEndDate());
			type1.setAirWorthinessNo(typeIdv.getAirWorthinessNo());type1.setAirWorthyStartDate(typeIdv.getAirWorthyStartDate());
			type1.setAirWorthyEndDate(typeIdv.getAirWorthyEndDate());type1.setBuildYear(typeIdv.getBuildYear());
			type1.setCrewSeatCapacity(typeIdv.getCrewSeatCapacity());type1.setPassengerSeatCapacity(typeIdv.getPassengerSeatCapacity());
			type1.setIdentificationMark(typeIdv.getIdentificationMark());type1.setEngineNo(typeIdv.getEngineNo());
			type1.setEngineType(typeIdv.getEngineType());type1.setRotorBladeType(typeIdv.getRotorBladeType());
			type1.setEquipmentItem(typeIdv.getEquipmentItem());type1.setTotalHoursMonth(typeIdv.getTotalHoursMonth());
			type1.setTotalHoursYear(typeIdv.getTotalHoursYear());type1.setTotalHoursNextYear(typeIdv.getTotalHoursNextYear());
			type1.setIsFlightNight(typeIdv.getIsFlightNight());type1.setFlightRouteSterritorial(typeIdv.getFlightRouteSterritorial());
			type1.setMaintainAdress(typeIdv.getMaintainAdress());type1.setKeepAdredd(typeIdv.getKeepAdredd());
			type1.setRotorsSetInmotion(typeIdv.getRotorsSetInmotion());type1.setNowPolicyCompany(typeIdv.getNowPolicyCompany());
			type1.setNowPolicyPeriod(typeIdv.getNowPolicyPeriod());type1.setLiabilityAgree(typeIdv.getLiabilityAgree());
			list1.add(type1);
		}
		dictPage.setData(list1);
		try {
			dictPage.setData(list1);
			dictPage.setTotalRecordCount(new Long(1));
			return dictPage;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * @author wanglianzhou �ͻ����������Ϣ��ѯ
	 * @param
	 * @return DictPage
	 */
	public DictPage getPrpDcustomerIdv(String systemCode, Map values)
			throws Exception {
		
		int pageNo = (Integer) values.get("pageNO");
		int pageSize = (Integer) values.get("pageSize");
		String identifyType = (String) values.get("identifyType");
		String identifyNumber = (String) values.get("identifyNumber");
		String customerCName = (String) values.get("customerCName");
		String customerCode = (String) values.get("customerCode");
		/* mantis： OTH0069，處理人員：DP0706，需求單編號：OTH0069 START
		        新核心DMS服務異常問題
		 */
		//若customerCode為空，則identifyNumber不得為空
		if (customerCode == null && (identifyNumber == null || "".equals(identifyNumber))) {
			
			logger.error("系统" + systemCode +"调用接口"
					+ ServiceInfoConst.GETPRPDCUSTOMERIDV
					+ "(getPrpDcustomerIdv) 參數identifyNumber不得為空，請確認!");
			
			throw new Exception("系统" + systemCode +"调用接口"
					+ ServiceInfoConst.GETPRPDCUSTOMERIDV
					+ "(getPrpDcustomerIdv) 參數identifyNumber不得為空，請確認!");
		}
		
		//mantis： OTH0069，處理人員：DP0706，需求單編號：OTH0069 END
		DictPage dictPage = new DictPage();
		boolean hasFirstCon = false;
		StringBuffer condition = new StringBuffer(256);
		StringBuffer hql = new StringBuffer("from PrpDcustomerIdv a ");
		List<cn.com.sinosoft.dms.model.PrpDcustomerIdv> list = null;
		if (customerCName != null && !"".equals(customerCName)) {
			condition.append(hasFirstCon ? " and " : " where ");
			// condition.append(" a.id.kindCode = '").append(kindCode).append("'");
			condition.append(" a.customerCName = '").append(customerCName)
					.append("'");
			hasFirstCon = true;
		}
		if (identifyType != null && !"".equals(identifyType)) {
			condition.append(hasFirstCon ? " and " : " where ");
			condition.append(" a.identifyType = '").append(identifyType)
					.append("'");
			hasFirstCon = true;
		}
		if (identifyNumber != null && !"".equals(identifyNumber)) {
			condition.append(hasFirstCon ? " and " : " where ");
			condition.append(" a.identifyNumber = '")
					.append(identifyNumber).append("'");
			hasFirstCon = true;
		}
		if (customerCode != null && !"".equals(customerCode)) {
			condition.append(hasFirstCon ? " and " : " where ");
			condition.append(" a.customerCode = '").append(customerCode)
					.append("'");
			hasFirstCon = true;
		}
		hql.append(condition);
		if (pageNo == 0 || pageSize == 0) {
			int count = getCount("dms", "PrpDcustomerIdv", condition.toString());
			if (count > 1000) {
				logger.error("系统" + systemCode + "查询记录数过大，不予返回！");
				throw new Exception("系统" + systemCode + "调用接口"
						+ ServiceInfoConst.GETPRPDCUSTOMERIDV
						+ "查询记录数过大，请缩小查询范围");
			} else {
				list = super.findByHql(hql.toString());
			}
			List<com.sinosoft.dmsdriver.model.PrpDcustomerIdv> list1 = new ArrayList<com.sinosoft.dmsdriver.model.PrpDcustomerIdv>();
			for (cn.com.sinosoft.dms.model.PrpDcustomerIdv typeIdv : list) {
				com.sinosoft.dmsdriver.model.PrpDcustomerIdv type = new com.sinosoft.dmsdriver.model.PrpDcustomerIdv();
				PrpDcustomer prpDcustomer = super.get(PrpDcustomer.class, typeIdv.getCustomerCode());
				type.setCustomerType(prpDcustomer.getCustomerType());
				type.setAccount(typeIdv.getAccount());type.setAddressCName(typeIdv.getAddressCName());
				type.setAddressEName(typeIdv.getAddressEName());type.setAge(typeIdv.getAge());
				type.setArticleCode(typeIdv.getArticleCode());type.setBank(typeIdv.getBank());
				type.setBirthDate(typeIdv.getBirthDate());type.setBlackState(typeIdv.getBlackState());
				type.setComCode(typeIdv.getComCode());type.setCreditLevel(typeIdv.getCreditLevel());
				type.setCustomerCName(typeIdv.getCustomerCName());type.setCustomerCode(typeIdv.getCustomerCode());
				type.setCustomerEName(typeIdv.getCustomerEName());type.setCustomerFlag(typeIdv.getCustomerFlag());
				type.setCustomerKind(typeIdv.getCustomerKind());type.setDeathDate(typeIdv.getDeathDate());
				type.setEducationCode(typeIdv.getEducationCode());type.setEmail(typeIdv.getEmail());
				type.setFaxNumber(typeIdv.getFaxNumber());type.setFlag(typeIdv.getFlag());
				type.setHandlerCode(typeIdv.getHandlerCode());type.setHealth(typeIdv.getHealth());
				type.setIdentifyNumber(typeIdv.getIdentifyNumber());type.setIdentifyType(typeIdv.getIdentifyType());
				type.setInputDate(typeIdv.getInputDate());type.setLinkAddress(typeIdv.getLinkAddress());
				type.setLowerViewFlag(typeIdv.getLowerViewFlag());type.setMobile(typeIdv.getMobile());
				type.setNetAddress(typeIdv.getNetAddress());type.setNewCustomerCode(typeIdv.getNewCustomerCode());
				type.setOccupationCode(typeIdv.getOccupationCode());type.setOperatorCode(typeIdv.getOperatorCode());
				type.setPager(typeIdv.getPager());type.setPassword(typeIdv.getPassword());
				type.setPhoneNumber(typeIdv.getPhoneNumber());type.setPostCode(typeIdv.getPostCode());
				type.setSex(typeIdv.getSex());type.setShortHandCode(typeIdv.getShortHandCode());
				type.setTopLevelFlag(typeIdv.getTopLevelFlag());type.setUnit(typeIdv.getUnit());
				type.setUnitAddress(typeIdv.getUnitAddress());type.setUpdateDate(typeIdv.getUpdateDate());
				type.setUpdaterCode(typeIdv.getUpdaterCode());type.setValidStatus(typeIdv.getValidStatus());
				type.setRoomPostCode(typeIdv.getRoomPostCode());type.setRoomAddress(typeIdv.getRoomAddress());
	            type.setVerifyNumber(typeIdv.getVerifyNumber());type.setLinkerName(typeIdv.getLinkerName());
	            type.setLoanAccount(typeIdv.getLoanAccount());
	            
	            type.setOccupationName(typeIdv.getOccupationName());
				type.setRoomPAreaNumber(typeIdv.getRoomPAreaNumber());
				type.setRoomPExtNumber(typeIdv.getRoomPExtNumber());
				type.setNationalityAddress(typeIdv.getNationalityAddress());
				type.setPhoneAreaNumber(typeIdv.getPhoneAreaNumber());
				type.setPhoneExtNumber(typeIdv.getPhoneExtNumber());
				type.setIdentifyStartDate(typeIdv.getIdentifyStartDate());
				type.setIdentifyEndDate(typeIdv.getIdentifyEndDate());

	            type.setCreditNumber(typeIdv.getCreditNumber());type.setCollateralNumber(typeIdv.getCollateralNumber());
	            type.setLoansBehalfNumber(typeIdv.getLoansBehalfNumber());type.setLoansDepartment(typeIdv.getLoansDepartment());
				type.setMobileTelephone(typeIdv.getMobileTelephone());
	            list1.add(type);
			}
			dictPage.setData(list1);
			
			return dictPage;
		} else {
			Page page = super.findByHql(hql.toString(), pageNo, pageSize);
			// list = page.getResult();
			list = super.findByHql(hql.toString());
			List<com.sinosoft.dmsdriver.model.PrpDcustomerIdv> list1 = new ArrayList<com.sinosoft.dmsdriver.model.PrpDcustomerIdv>();
			for (cn.com.sinosoft.dms.model.PrpDcustomerIdv typeIdv : list) {
				com.sinosoft.dmsdriver.model.PrpDcustomerIdv type = new com.sinosoft.dmsdriver.model.PrpDcustomerIdv();
				PrpDcustomer prpDcustomer = super.get(PrpDcustomer.class, typeIdv.getCustomerCode());
				type.setCustomerType(prpDcustomer.getCustomerType());
				type.setAccount(typeIdv.getAccount());type.setAddressCName(typeIdv.getAddressCName());
				type.setAddressEName(typeIdv.getAddressEName());type.setAge(typeIdv.getAge());
				type.setArticleCode(typeIdv.getArticleCode());type.setBank(typeIdv.getBank());
				type.setBirthDate(typeIdv.getBirthDate());type.setBlackState(typeIdv.getBlackState());
				type.setComCode(typeIdv.getComCode());type.setCreditLevel(typeIdv.getCreditLevel());
				type.setCustomerCName(typeIdv.getCustomerCName());type.setCustomerCode(typeIdv.getCustomerCode());
				type.setCustomerEName(typeIdv.getCustomerEName());type.setCustomerFlag(typeIdv.getCustomerFlag());
				type.setCustomerKind(typeIdv.getCustomerKind());type.setDeathDate(typeIdv.getDeathDate());
				type.setEducationCode(typeIdv.getEducationCode());type.setEmail(typeIdv.getEmail());
				type.setFaxNumber(typeIdv.getFaxNumber());type.setFlag(typeIdv.getFlag());
				type.setHandlerCode(typeIdv.getHandlerCode());type.setHealth(typeIdv.getHealth());
				type.setIdentifyNumber(typeIdv.getIdentifyNumber());type.setIdentifyType(typeIdv.getIdentifyType());
				type.setInputDate(typeIdv.getInputDate());type.setLinkAddress(typeIdv.getLinkAddress());
				type.setLowerViewFlag(typeIdv.getLowerViewFlag());type.setMobile(typeIdv.getMobile());
				type.setNetAddress(typeIdv.getNetAddress());type.setNewCustomerCode(typeIdv.getNewCustomerCode());
				type.setOccupationCode(typeIdv.getOccupationCode());type.setOperatorCode(typeIdv.getOperatorCode());
				type.setPager(typeIdv.getPager());type.setPassword(typeIdv.getPassword());
				type.setPhoneNumber(typeIdv.getPhoneNumber());type.setPostCode(typeIdv.getPostCode());
				type.setSex(typeIdv.getSex());type.setShortHandCode(typeIdv.getShortHandCode());
				type.setTopLevelFlag(typeIdv.getTopLevelFlag());type.setUnit(typeIdv.getUnit());
				type.setUnitAddress(typeIdv.getUnitAddress());type.setUpdateDate(typeIdv.getUpdateDate());
				type.setUpdaterCode(typeIdv.getUpdaterCode());type.setValidStatus(typeIdv.getValidStatus());
				type.setRoomPostCode(typeIdv.getRoomPostCode());type.setRoomAddress(typeIdv.getRoomAddress());
                type.setVerifyNumber(typeIdv.getVerifyNumber());type.setLinkerName(typeIdv.getLinkerName());
                type.setLoanAccount(typeIdv.getLoanAccount());
                
                type.setOccupationName(typeIdv.getOccupationName());
				type.setRoomPAreaNumber(typeIdv.getRoomPAreaNumber());
				type.setRoomPExtNumber(typeIdv.getRoomPExtNumber());
				type.setNationalityAddress(typeIdv.getNationalityAddress());
				type.setPhoneAreaNumber(typeIdv.getPhoneAreaNumber());
				type.setPhoneExtNumber(typeIdv.getPhoneExtNumber());
				type.setIdentifyStartDate(typeIdv.getIdentifyStartDate());
				type.setIdentifyEndDate(typeIdv.getIdentifyEndDate());
                
				type.setCreditNumber(typeIdv.getCreditNumber());type.setCollateralNumber(typeIdv.getCollateralNumber());
                type.setLoansBehalfNumber(typeIdv.getLoansBehalfNumber());type.setLoansDepartment(typeIdv.getLoansDepartment());
                type.setMobileTelephone(typeIdv.getMobileTelephone());
                list1.add(type);
			}
			dictPage.setData(list1);
			dictPage.setPageNo(pageNo);
			dictPage.setPageSize(pageSize);
			dictPage.setPageCount(page.getTotalPageCount());
			dictPage.setTotalRecordCount(page.getTotalCount());
			return dictPage;
		}

	}
	/**
	 * 查询船舶信息
	 * @author fengyang
	 * @param
	 * @return DictPage
	 */
	public DictPage getPrDitemShip(String systemCode, Map values)
			throws Exception {
		
		int pageNo = (Integer) values.get("pageNO");
		int pageSize = (Integer) values.get("pageSize");
		String shipCode = (String) values.get("shipCode");
		String shipNo = (String) values.get("shipNo");
		String shipCName = (String) values.get("shipCName");
		String shipEName = (String) values.get("shipEName");
		DictPage dictPage = new DictPage();
		boolean hasFirstCon = false;
		StringBuffer condition = new StringBuffer(256);
		StringBuffer hql = new StringBuffer("from PrpDitemShip a ");
		List<cn.com.sinosoft.dms.model.PrpDitemShip> list = null;
		if (shipCode != null && !"".equals(shipCode)) {
			condition.append(hasFirstCon ? " and " : " where ");
			condition.append(" a.shipCode = '").append(shipCode)
					.append("'");
			hasFirstCon = true;
		}
		if (shipNo != null && !"".equals(shipNo)) {
			condition.append(hasFirstCon ? " and " : " where ");
			condition.append(" a.shipNo = '").append(shipNo)
					.append("'");
			hasFirstCon = true;	
		}
		if (shipCName != null && !"".equals(shipCName)) {
			condition.append(hasFirstCon ? " and " : " where ");
			condition.append(" a.shipCName like '%").append(shipCName)
					.append("%'");
			hasFirstCon = true;
		}
		if (shipEName != null && !"".equals(shipEName)) {
			condition.append(hasFirstCon ? " and " : " where ");
			condition.append(" a.shipEName like '%")
					.append(shipEName).append("%'");
			hasFirstCon = true;
		}
		hql.append(condition);
		if (pageNo == 0 || pageSize == 0) {
			int count = getCount("dms", "PrpDitemShip", condition.toString());
			if (count > 1000) {
				logger.error("ϵͳ" + systemCode + "��ѯ��¼���󣬲��践�أ�");
				throw new Exception("ϵͳ" + systemCode + "���ýӿ�"
						+ ServiceInfoConst.GETPRPDCUSTOMERIDV
						+ "��ѯ��¼��������С��ѯ��Χ");
			} else {
				list = super.findByHql(hql.toString());
			}
			List<com.sinosoft.dmsdriver.model.PrpDitemShip> list1 = new ArrayList<com.sinosoft.dmsdriver.model.PrpDitemShip>();
			for (cn.com.sinosoft.dms.model.PrpDitemShip typeIdv : list) {
				com.sinosoft.dmsdriver.model.PrpDitemShip type = new com.sinosoft.dmsdriver.model.PrpDitemShip();
				type.setCertificateOwner(typeIdv.getCertificateOwner());type.setCheckEndDate(typeIdv.getCheckEndDate());
				type.setCheckStartDate(typeIdv.getCheckStartDate());type.setLoadTon(typeIdv.getLoadTon());
				type.setMakeEndDate(typeIdv.getMakeEndDate());type.setMakeStartDate(typeIdv.getMakeStartDate());
				type.setManageStartDate(typeIdv.getManageStartDate());type.setManageType(typeIdv.getManageType());
				type.setMakeYearMonth(typeIdv.getMakeYearMonth());
				type.setShipTypeCodeCName(typeIdv.getShipTypeCodeCName());type.setUseNatureCodeCName(typeIdv.getUseNatureCodeCName());
				type.setUseNatureCodeEName(typeIdv.getUseNatureCodeEName());
				//type.setComCode(typeIdv.getComCode());
				type.setOldShipEName(typeIdv.getOldShipEName());type.setShipCode(typeIdv.getShipCode());
				type.setOldShipName(typeIdv.getOldShipName());type.setRegistrySite(typeIdv.getRegistrySite());
				type.setRemark(typeIdv.getRemark());type.setSeatCount(typeIdv.getSeatCount());
				type.setShipCName(typeIdv.getShipCName());type.setShipEName(typeIdv.getShipEName());
				type.setShipFlag(typeIdv.getShipFlag());type.setShipNo(typeIdv.getShipNo());
				type.setShipOperator(typeIdv.getShipOperator());type.setShipOwner(typeIdv.getShipOwner());
				type.setShipPort(typeIdv.getShipPort());type.setShipStruct(typeIdv.getShipStruct());
				type.setShipTypeCode(typeIdv.getShipTypeCode());type.setStepHull(typeIdv.getStepHull());
				type.setTonCount(typeIdv.getTonCount());type.setUseNatureCode(typeIdv.getUseNatureCode());
	           
				list1.add(type);
			}
			dictPage.setData(list1);
			
			return dictPage;
		} else {
			Page page = super.findByHql(hql.toString(), pageNo, pageSize);
			list = super.findByHql(hql.toString());
			List<com.sinosoft.dmsdriver.model.PrpDitemShip> list1 = new ArrayList<com.sinosoft.dmsdriver.model.PrpDitemShip>();
			for (cn.com.sinosoft.dms.model.PrpDitemShip typeIdv : list) {
				com.sinosoft.dmsdriver.model.PrpDitemShip type = new com.sinosoft.dmsdriver.model.PrpDitemShip();
				type.setCertificateOwner(typeIdv.getCertificateOwner());type.setCheckEndDate(typeIdv.getCheckEndDate());
				type.setCheckStartDate(typeIdv.getCheckStartDate());type.setLoadTon(typeIdv.getLoadTon());
				type.setMakeEndDate(typeIdv.getMakeEndDate());type.setMakeStartDate(typeIdv.getMakeStartDate());
				type.setManageStartDate(typeIdv.getManageStartDate());type.setManageType(typeIdv.getManageType());
				type.setMakeYearMonth(typeIdv.getMakeYearMonth());
				type.setShipTypeCodeCName(typeIdv.getShipTypeCodeCName());type.setUseNatureCodeCName(typeIdv.getUseNatureCodeCName());
				type.setUseNatureCodeEName(typeIdv.getUseNatureCodeEName());
				//type.setComCode(typeIdv.getComCode());
				type.setOldShipEName(typeIdv.getOldShipEName());type.setShipCode(typeIdv.getShipCode());
				type.setOldShipName(typeIdv.getOldShipName());type.setRegistrySite(typeIdv.getRegistrySite());
				type.setRemark(typeIdv.getRemark());type.setSeatCount(typeIdv.getSeatCount());
				type.setShipCName(typeIdv.getShipCName());type.setShipEName(typeIdv.getShipEName());
				type.setShipFlag(typeIdv.getShipFlag());type.setShipNo(typeIdv.getShipNo());
				type.setShipOperator(typeIdv.getShipOperator());type.setShipOwner(typeIdv.getShipOwner());
				type.setShipPort(typeIdv.getShipPort());type.setShipStruct(typeIdv.getShipStruct());
				type.setShipTypeCode(typeIdv.getShipTypeCode());type.setStepHull(typeIdv.getStepHull());
				type.setTonCount(typeIdv.getTonCount());type.setUseNatureCode(typeIdv.getUseNatureCode());
				list1.add(type);
			}
			dictPage.setData(list1);
			dictPage.setPageNo(pageNo);
			dictPage.setPageSize(pageSize);
			dictPage.setPageCount(page.getTotalPageCount());
			dictPage.setTotalRecordCount(page.getTotalCount());
			return dictPage;
		}

	}
	
	/**
	 * 查询飞机信息
	 * @author fengyang
	 * @param
	 * @return DictPage
	 */
	public DictPage getPrDplane(String systemCode, Map values)
			throws Exception {
		
		int pageNo = (Integer) values.get("pageNO");
		int pageSize = (Integer) values.get("pageSize");
		String planeType = (String) values.get("planeType");
		String registrationMarks = (String) values.get("registrationMarks");
		String labelNo = (String) values.get("labelNo");
		String licenseNo = (String) values.get("licenseNo");
		DictPage dictPage = new DictPage();
		boolean hasFirstCon = false;
		StringBuffer condition = new StringBuffer(256);
		StringBuffer hql = new StringBuffer("from PrpDplane a ");
		List<cn.com.sinosoft.dms.model.PrpDplane> list = null;
		if (planeType != null && !"".equals(planeType)) {
			condition.append(hasFirstCon ? " and " : " where ");
			condition.append(" a.planeType = '").append(planeType)
					.append("'");
			hasFirstCon = true;
		}
		if (registrationMarks != null && !"".equals(registrationMarks)) {
			condition.append(hasFirstCon ? " and " : " where ");
			condition.append(" a.registrationMarks = '").append(registrationMarks)
					.append("'");
			hasFirstCon = true;
		}
		if (labelNo != null && !"".equals(labelNo)) {
			condition.append(hasFirstCon ? " and " : " where ");
			condition.append(" a.labelNo = '").append(labelNo)
					.append("'");
			hasFirstCon = true;
		}
		if (licenseNo != null && !"".equals(licenseNo)) {
			condition.append(hasFirstCon ? " and " : " where ");
			condition.append(" a.licenseNo = '")
					.append(licenseNo).append("'");
			hasFirstCon = true;
		}
		hql.append(condition);
		if (pageNo == 0 || pageSize == 0) {
			int count = getCount("dms", "PrpDplane", condition.toString());
			if (count > 1000) {
				logger.error("ϵͳ" + systemCode + "��ѯ��¼���󣬲��践�أ�");
				throw new Exception("ϵͳ" + systemCode + "���ýӿ�"
						+ ServiceInfoConst.GETPRPDCUSTOMERIDV
						+ "��ѯ��¼��������С��ѯ��Χ");
			} else {
				list = super.findByHql(hql.toString());
			}
			List<com.sinosoft.dmsdriver.model.PrpDplane> list1 = new ArrayList<com.sinosoft.dmsdriver.model.PrpDplane>();
			for (cn.com.sinosoft.dms.model.PrpDplane typeIdv : list) {
				com.sinosoft.dmsdriver.model.PrpDplane type = new com.sinosoft.dmsdriver.model.PrpDplane();
				type.setPlaneType(typeIdv.getPlaneType());type.setRegistrationMarks(typeIdv.getRegistrationMarks());
				type.setLabelNo(typeIdv.getLabelNo());type.setLicenseNo(typeIdv.getLicenseNo());
				type.setLicenseStartDate(typeIdv.getLicenseStartDate());type.setLicenseEndDate(typeIdv.getLicenseEndDate());
				type.setAirWorthinessNo(typeIdv.getAirWorthinessNo());type.setAirWorthyStartDate(typeIdv.getAirWorthyStartDate());
				type.setAirWorthyEndDate(typeIdv.getAirWorthyEndDate());type.setBuildYear(typeIdv.getBuildYear());
				type.setCrewSeatCapacity(typeIdv.getCrewSeatCapacity());type.setPassengerSeatCapacity(typeIdv.getPassengerSeatCapacity());
				type.setIdentificationMark(typeIdv.getIdentificationMark());type.setEngineNo(typeIdv.getEngineNo());
				type.setEngineType(typeIdv.getEngineType());type.setRotorBladeType(typeIdv.getRotorBladeType());
				type.setEquipmentItem(typeIdv.getEquipmentItem());type.setTotalHoursMonth(typeIdv.getTotalHoursMonth());
				type.setTotalHoursYear(typeIdv.getTotalHoursYear());type.setTotalHoursNextYear(typeIdv.getTotalHoursNextYear());
				type.setIsFlightNight(typeIdv.getIsFlightNight());type.setFlightRouteSterritorial(typeIdv.getFlightRouteSterritorial());
				type.setMaintainAdress(typeIdv.getMaintainAdress());type.setKeepAdredd(typeIdv.getKeepAdredd());
				type.setRotorsSetInmotion(typeIdv.getRotorsSetInmotion());type.setNowPolicyCompany(typeIdv.getNowPolicyCompany());
				type.setNowPolicyPeriod(typeIdv.getNowPolicyPeriod());type.setLiabilityAgree(typeIdv.getLiabilityAgree());
				list1.add(type);
			}
			dictPage.setData(list1);
			
			return dictPage;
		} else {
			Page page = super.findByHql(hql.toString(), pageNo, pageSize);
			list = super.findByHql(hql.toString());
			List<com.sinosoft.dmsdriver.model.PrpDplane> list1 = new ArrayList<com.sinosoft.dmsdriver.model.PrpDplane>();
			for (cn.com.sinosoft.dms.model.PrpDplane typeIdv : list) {
				com.sinosoft.dmsdriver.model.PrpDplane type = new com.sinosoft.dmsdriver.model.PrpDplane();
				type.setPlaneType(typeIdv.getPlaneType());type.setRegistrationMarks(typeIdv.getRegistrationMarks());
				type.setLabelNo(typeIdv.getLabelNo());type.setLicenseNo(typeIdv.getLicenseNo());
				type.setLicenseStartDate(typeIdv.getLicenseStartDate());type.setLicenseEndDate(typeIdv.getLicenseEndDate());
				type.setAirWorthinessNo(typeIdv.getAirWorthinessNo());type.setAirWorthyStartDate(typeIdv.getAirWorthyStartDate());
				type.setAirWorthyEndDate(typeIdv.getAirWorthyEndDate());type.setBuildYear(typeIdv.getBuildYear());
				type.setCrewSeatCapacity(typeIdv.getCrewSeatCapacity());type.setPassengerSeatCapacity(typeIdv.getPassengerSeatCapacity());
				type.setIdentificationMark(typeIdv.getIdentificationMark());type.setEngineNo(typeIdv.getEngineNo());
				type.setEngineType(typeIdv.getEngineType());type.setRotorBladeType(typeIdv.getRotorBladeType());
				type.setEquipmentItem(typeIdv.getEquipmentItem());type.setTotalHoursMonth(typeIdv.getTotalHoursMonth());
				type.setTotalHoursYear(typeIdv.getTotalHoursYear());type.setTotalHoursNextYear(typeIdv.getTotalHoursNextYear());
				type.setIsFlightNight(typeIdv.getIsFlightNight());type.setFlightRouteSterritorial(typeIdv.getFlightRouteSterritorial());
				type.setMaintainAdress(typeIdv.getMaintainAdress());type.setKeepAdredd(typeIdv.getKeepAdredd());
				type.setRotorsSetInmotion(typeIdv.getRotorsSetInmotion());type.setNowPolicyCompany(typeIdv.getNowPolicyCompany());
				type.setNowPolicyPeriod(typeIdv.getNowPolicyPeriod());type.setLiabilityAgree(typeIdv.getLiabilityAgree());
				list1.add(type);
			}
			dictPage.setData(list1);
			dictPage.setPageNo(pageNo);
			dictPage.setPageSize(pageSize);
			dictPage.setPageCount(page.getTotalPageCount());
			dictPage.setTotalRecordCount(page.getTotalCount());
			return dictPage;
		}

	}
	/**
     * 
     * TODO 根据方案代码查询方案集合（可选）.
     * @see cn.com.sinosoft.dms.webservice.facade.DictionaryService#getSeriesListBySeriesNo(java.lang.String, java.util.Map)
     */
	public DictPage getListByPlanNo(String systemCode, Map values)
            throws Exception {
	    String rationCode = (String) values.get("rationCode");
	    String riskCode = (String) values.get("riskCode");
	    //add by yjm 20150710 start 多個rationCode
	    String rationCodes = rationCode;
	    if(rationCode==null){
	    	rationCode = "";
	    }
	    rationCodes = "('"+rationCode.replaceAll(",", "','")+"')";
	    //add by yjm 20150710 end 多個rationCode
	    DictPage dictPage = new DictPage();
	    /*  PrpDrationClauseKind typeOne = (PrpDrationClauseKind) values
                .get("prpDrationClauseKind");*/
        //cn.com.sinosoft.dms.model.PrpDrationClauseKind type = new cn.com.sinosoft.dms.model.PrpDrationClauseKind();
        List<PrpDrationClauseKind> prpDrationClauseKinds = null ;
        try{
            String hql = "from PrpDrationClauseKind  where id.rationCode in "+rationCodes+" and id.riskCode='"+riskCode+"' and validInd='1' order by id.kindCode";// 
            prpDrationClauseKinds = this.findByHql(hql);
        }catch (Exception e) {
            e.printStackTrace();
        }
        
        dictPage.setData(prpDrationClauseKinds);
       // PrpDcustomer prpDcustomer = new PrpDcustomer();
	    return dictPage;
	}
	
	/**
	 * 
	 * @see cn.com.sinosoft.dms.webservice.facade.DictionaryService#getRationPreiumListByCondition(java.lang.String, java.util.Map)
	 */
	public DictPage getRationPreiumListByCondition(String systemCode, Map values)
            throws Exception {
        String rationCode = (String) values.get("rationCode");//方案代码
        //add by yjm 20150710 start 多個rationCode
	    String rationCodes = rationCode;
	    if(rationCode==null){
	    	rationCode = "";
	    }
	    rationCodes = "('"+rationCode.replaceAll(",", "','")+"')";
	    //add by yjm 20150710 end 多個rationCode
        String riskCode = (String) values.get("riskCode");//险种
        String age = (String) values.get("age");//年龄
        String birthday = (String) values.get("birthday");//生日
        String dutyLevel = (String) values.get("dutyLevel");//职业等级
        String mainRelation = (String) values.get("mainRelation");
        String renewalFlag = (String) values.get("renewalFlag");//新/续保件
        String sex = (String) values.get("sex");//性别
        Date date = new Date();
        DictPage dictPage = new DictPage();
        if("01".equals(mainRelation)){
            mainRelation = "1";
        }else if("02".equals(mainRelation)){
            mainRelation = "2";
        }else if("03".equals(mainRelation)){
            mainRelation = "3";
        }else if("04".equals(mainRelation)){
            mainRelation = "4";
        }else if("05".equals(mainRelation)){
            mainRelation = "5";
        }else if("09".equals(mainRelation)){
            mainRelation = "9";
        }
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");       
        List<PrpDrationCondition> prpDrationConditions = null ;
        try{
            String hql = "from PrpDrationCondition  where id.rationCode in "+rationCodes+" and (gender='"+sex+"' or gender='3')" +
            		"  and  (insureType='"+renewalFlag+"' or insureType='3') and beginAges<="+
                    age+" and endAges>="+age+"and validDate<=to_date('"+df.format(date)+"','yyyy-MM-dd') and validInd='1'"; 
            if(dutyLevel!=null && !"".equals(dutyLevel)){
            	hql = hql + " and careerBegin<='"+dutyLevel+"' and careerEnd>='"+dutyLevel+"'" ;
            }
            if(mainRelation!=null && !"".equals(mainRelation)){
            	hql = hql + "and (identityType='"+mainRelation+"' or  identityType='9')";
            }
            
            prpDrationConditions = this.findByHql(hql);//and invalidDate>=to_date('"+df.format(date)+"','yyyy-MM-dd')
        }catch (Exception e) {
            e.printStackTrace();
        }
        
        dictPage.setData(prpDrationConditions);
       // PrpDcustomer prpDcustomer = new PrpDcustomer();
        return dictPage;
    }
	
	/**
	 * 
	 * TODO 根据方案代码查询方案集合（可选）.
	 * @see cn.com.sinosoft.dms.webservice.facade.DictionaryService#getSeriesListBySeriesNo(java.lang.String, java.util.Map)
	 */
	public DictPage getSeriesListBySeriesNo(String systemCode, Map values)
            throws Exception {
        String rationCode = (String) values.get("SeriesCode");
        DictPage dictPage = new DictPage();
      /*  PrpDrationClauseKind typeOne = (PrpDrationClauseKind) values
                .get("prpDrationClauseKind");*/
        //cn.com.sinosoft.dms.model.PrpDrationClauseKind type = new cn.com.sinosoft.dms.model.PrpDrationClauseKind();
        List<PrpDrationClauseKind> prpDrationClauseKinds = null ;
        try{
            String hql = "from PrpDsetRationrelation where setCode = '"+rationCode+"' ";
            prpDrationClauseKinds = this.findByHql(hql);
        }catch (Exception e) {
            e.printStackTrace();
        }
        
        dictPage.setData(prpDrationClauseKinds);
       // PrpDcustomer prpDcustomer = new PrpDcustomer();
        return dictPage;
    }
	
	/**
	 * 
	 * TODO 根据方案名称查询方案集合（可选）.
	 * @see cn.com.sinosoft.dms.webservice.facade.DictionaryService#getRationListByRationName(java.lang.String, java.util.Map)
	 */
	public DictPage getRationListByRationName(String systemCode, Map values)
            throws Exception {
        String rationName = (String) values.get("rationName");
        DictPage dictPage = new DictPage();
      /*  PrpDrationClauseKind typeOne = (PrpDrationClauseKind) values
                .get("prpDrationClauseKind");*/
        //cn.com.sinosoft.dms.model.PrpDrationClauseKind type = new cn.com.sinosoft.dms.model.PrpDrationClauseKind();
        List<PrpDration> prpDrations = null ;
        try{
            String hql = "from PrpDration where rationCName = '"+rationName+"'";
            prpDrations = this.findByHql(hql);
        }catch (Exception e) {
            e.printStackTrace();
        }
        
        dictPage.setData(prpDrations);
       // PrpDcustomer prpDcustomer = new PrpDcustomer();
        return dictPage;
    }
	
    @Override
    public DictPage getPrpDcustomerFXQ(String systemCode, Map values)
    		throws Exception {
    	int pageNo = (Integer) values.get("pageNO");
		int pageSize = (Integer) values.get("pageSize");
		String customerType = (String) values.get("customerType");
		String customerCode = (String) values.get("customerCode");
		DictPage dictPage = new DictPage();
		StringBuffer condition = new StringBuffer(256);
		condition.append("from PrpDcustomerFXQ a ");
		List<cn.com.sinosoft.dms.model.PrpDcustomerFXQ> list = null;
		condition.append("where a.customerType= '").append(customerType).append("'");
		condition.append("and a.customerCode = '").append(customerCode).append("'");
		list=super.findByHql(condition.toString());
		Page page = super.findByHql(condition.toString(), pageNo, pageSize);
		List<cn.com.sinosoft.dms.model.PrpDcustomerFXQ> list1=new ArrayList<cn.com.sinosoft.dms.model.PrpDcustomerFXQ>();
    	for(cn.com.sinosoft.dms.model.PrpDcustomerFXQ type1:list){
    		cn.com.sinosoft.dms.model.PrpDcustomerFXQ type2=new cn.com.sinosoft.dms.model.PrpDcustomerFXQ();
    		type2.setIdentifyName(type1.getIdentifyName());
    		type2.setIdentifyNumber(type1.getIdentifyNumber());
    		type2.setCustomerCode(type1.getCustomerCode());
    		type2.setIdentifyStartDate(type1.getIdentifyStartDate());
    		type2.setIdentifyEndDate(type1.getIdentifyEndDate());
    		type2.setIdentifyType(type1.getIdentifyType());
    		type2.setShareHolderName(type1.getShareHolderName());
    		type2.setShareHolderIdentifyType(type1.getShareHolderIdentifyType());
    		type2.setShareHolderIdentifyNumber(type1.getShareHolderIdentifyNumber());
    		type2.setShareHolderIdentifyStartDate(type1.getShareHolderIdentifyStartDate());
    		type2.setShareHolderIdentifyEndDate(type1.getShareHolderIdentifyEndDate());
    		type2.setLeaderName(type1.getLeaderName());
    		type2.setLeaderIdentifyType(type1.getLeaderIdentifyType());
    		type2.setLeaderIdentifyNumber(type1.getLeaderIdentifyNumber());
    		type2.setLeaderIdentifyStartDate(type1.getLeaderIdentifyStartDate());
    		type2.setLeaderIdentifyEndDate(type1.getLeaderIdentifyEndDate());
    		type2.setPrincipalName(type1.getPrincipalName());
    		type2.setPrincipalIdentifyType(type1.getPrincipalIdentifyType());
    		type2.setPrincipalIdentifyNumber(type1.getPrincipalIdentifyNumber());
    		type2.setPrincipalIdentifyStartDate(type1.getPrincipalIdentifyStartDate());
    		type2.setPrincipalIdentifyEndDate(type1.getPrincipalIdentifyEndDate());
    		type2.setTaxRegisterNumber(type1.getTaxRegisterNumber());
    		list1.add(type2);
    	}
    	dictPage.setData(list1);
		dictPage.setPageNo(pageNo);
		dictPage.setPageSize(pageSize);
		dictPage.setPageCount(page.getTotalPageCount());
		dictPage.setTotalRecordCount(page.getTotalCount());
		return dictPage;
    }
	/**
	 * 
	 * TODO 套装商品双击域查询（可选）.
	 * @see cn.com.sinosoft.dms.webservice.facade.DictionaryService#getSetCodeDb(java.lang.String, java.util.Map)
	 */
	public DictPage getSetCodeDb(String systemCode, Map values)
            throws Exception {
        String setCode = (String) values.get("setCode");
        String riskCode = (String) values.get("riskCode");
        String pageNo = (String) values.get("pageNo");
        String pageSize = (String) values.get("pageSize");
        DictPage dictPage = new DictPage();
      /*  PrpDrationClauseKind typeOne = (PrpDrationClauseKind) values
                .get("prpDrationClauseKind");*/
        //cn.com.sinosoft.dms.model.PrpDrationClauseKind type = new cn.com.sinosoft.dms.model.PrpDrationClauseKind();
        List<PrpDset> prpDsets = null ;
        String hql = null ;
        try{
            if(!"".equals(setCode))
                hql = "from PrpDset where setCode = '"+setCode+"' and riskCode='"+riskCode+"'";
            else
                hql = "from PrpDset where riskCode='"+riskCode+"'";
            prpDsets = this.findByHql(hql);
        }catch (Exception e) {
            e.printStackTrace();
        }
        
        dictPage.setData(prpDsets);
        if(prpDsets == null){
            dictPage.setTotalRecordCount(0L); 
        }else{
            dictPage.setTotalRecordCount((long)prpDsets.size()); 
        }      
        dictPage.setPageNo(Integer.parseInt(pageNo));
        dictPage.setPageSize(Integer.parseInt(pageSize));
        long totalCount = prpDsets.size();
        int pageSize1 = Integer.parseInt(pageSize);
        long pageCount = 0L;
        if(totalCount % (long)pageSize1 == 0L)
            pageCount = totalCount / (long)pageSize1;
        else
            pageCount =  totalCount / (long)pageSize1 + 1L;
        dictPage.setPageCount(pageCount);
       // PrpDcustomer prpDcustomer = new PrpDcustomer();
        return dictPage;
    }
	
	
	/**
	 * 
	 * @see cn.com.sinosoft.dms.webservice.facade.DictionaryService#getStartPlaceInfo(java.lang.String, java.util.Map)
	 */
	public DictPage getStartPlaceInfo(String systemCode, Map values)
            throws Exception {
        String startCode = (String) values.get("startCode");
        String riskCode = (String) values.get("rskCode");
        String pageNo = (String) values.get("pageNo");
        String pageSize = (String) values.get("pageSize");
        String flag=(String) values.get("flag");
        DictPage dictPage = new DictPage();
      /*  PrpDrationClauseKind typeOne = (PrpDrationClauseKind) values
                .get("prpDrationClauseKind");*/
        //cn.com.sinosoft.dms.model.PrpDrationClauseKind type = new cn.com.sinosoft.dms.model.PrpDrationClauseKind();
        List<PrpDstartPlace> prpDstartPlaces = null ;
        String hql = null ;
        int pageNo1 = Integer.parseInt(pageNo);
        int pageSize1 = Integer.parseInt(pageSize);
        Page page = null;
        try{
        	/*if("1".equals(flag)){
        		 if(!"".equals(startCode)){
        			 String[] startCodes = startCode.split("-");
        		     if(startCodes.length>1){
        		    	 hql = "from PrpDstartPlace where portName like '%"+startCodes[0]+"%' and countries like '%"+startCodes[1]+"%' order by codeCode asc";
        		     }else{
        		    	 hql = "from PrpDstartPlace where portName like '%"+startCodes[0]+"%' or countries like '%"+startCodes[0]+"%' order by codeCode asc";
        		     }
        		 }else{
                     hql = "from PrpDstartPlace order by codeCode asc";
        		 }
        	}else{
            if(!"".equals(startCode))
                hql = "from PrpDstartPlace where codeCode like '"+startCode+"%' order by codeCode asc";
            else
                hql = "from PrpDstartPlace order by codeCode asc";
        	}*/
        	 String[] startCodes = startCode.split("-");
		     if(startCodes.length>1){
		    	 hql = "from PrpDstartPlace where codeCode like '"+startCode+"%' or (portName like '%"+startCodes[0]+"%' and countries like '%"+startCodes[1]+"%') order by codeCode asc";
		     }else{
		    	 hql = "from PrpDstartPlace where codeCode like '"+startCode+"%' or portName like '%"+startCodes[0]+"%' or countries like '%"+startCodes[0]+"%' order by codeCode asc";
		     }
            prpDstartPlaces = this.findByHql(hql);
            page = super.findByHql(hql.toString(), pageNo1, pageSize1);
        }catch (Exception e) {
            e.printStackTrace(); 
        }
      
        /*dictPage.setData(page.getResult());
        dictPage.setTotalRecordCount((long)prpDstartPlaces.size());
        dictPage.setPageNo(Integer.parseInt(pageNo));
        dictPage.setPageSize(Integer.parseInt(pageSize));
        long totalCount = prpDstartPlaces.size();        
        long pageCount = 0L;
        if(totalCount % (long)pageSize1 == 0L)
            pageCount = totalCount / (long)pageSize1;
        else
            pageCount =  totalCount / (long)pageSize1 + 1L;
        dictPage.setPageCount(pageCount);*/
        dictPage.setData(page.getResult());
        dictPage.setTotalRecordCount(page.getTotalCount()); 
        dictPage.setPageNo(Integer.parseInt(pageNo));
        dictPage.setPageSize(Integer.parseInt(pageSize));
        dictPage.setPageCount(page.getTotalPageCount());
       // PrpDcustomer prpDcustomer = new PrpDcustomer();
        return dictPage;
    }
	
	/**
	 * 
	 * @see cn.com.sinosoft.dms.webservice.facade.DictionaryService#saveUserCodeMCInfo(java.lang.String, java.util.Map)
	 */
	public DictPage saveUserCodeMCInfo(String systemcode, Map<String, String> values)
            throws Exception {
        String userCode = (String) values.get("userCode");
        String customerCname = (String) values.get("customerCname");
        String customerEname = (String) values.get("customerEname");
        String lowerFee = (String) values.get("lowerFee");
        DictPage dictPage = new DictPage();
        String hql = null ;
        try{
            /*hql = "from PrpDcustomerUnit where unitCode='"+userCode+"' and customerEName='"+customerEname+"'";
            List<PrpDcustomerUnit> prpdsDcustomerUnits = this.findByHql(hql);
            PrpDcustomerUnit prpDcustomerUnit = prpdsDcustomerUnits.get(0);
            prpDcustomerUnit.setMinimumPreium(lowerFee);*/
            hql = "update PrpDcustomerUnit set minimumPreium='"+lowerFee+"' where unitCode='"+userCode+"' and customerCName='"+customerCname+"' and customerEName='"+customerEname+"'";
            this.getSession().createSQLQuery(hql).executeUpdate();
            dictPage.setPageNo(1);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return dictPage;
    }
	
	/**
	 * 
	 * @see cn.com.sinosoft.dms.webservice.facade.DictionaryService#getUserCodeMCInfo(java.lang.String, java.util.Map)
	 */
	public DictPage getUserCodeMCInfo(String systemCode, Map values)
            throws Exception {
        String userCode = (String) values.get("userCode");
        String pageNo = (String) values.get("pageNo");
        String pageSize = (String) values.get("pageSize");
        DictPage dictPage = new DictPage();
        String customerCname = (String) values.get("customerCname");
        String customerEname = (String) values.get("customerEname");
        String lowerFee = (String) values.get("lowerFee");
        List<cn.com.sinosoft.dms.model.PrpDcustomerUnit> prpDcustomerUnits = null ;
        int pageNo1 = Integer.parseInt(pageNo);
        int pageSize1 = Integer.parseInt(pageSize);
        String hql = "select unitCode,customerCName,customerEName,minimumPreium from PrpDcustomerUnit where customerCName is not null and customerEName is not null and unitCode is not null" ;
        Page page = null;
        try{
            if(userCode!=null && !"".equals(userCode)){
                hql = hql+" and unitCode like '"+userCode+"%'";
            }
            if(customerCname!=null && !"".equals(customerCname)){
                hql = hql+" and customerCName = '"+customerCname+"'";
            }
            if(customerEname!=null && !"".equals(customerEname)){
                hql = hql+" and customerEName = '"+customerEname+"'";
            }
            
            hql = hql + " group by unitCode,customerCName,customerEName,minimumPreium order by unitCode";
            //prpDcustomerUnits = this.findByHql(hql);
             /*QueryRule queryRule = QueryRule.getInstance();
             queryRule.addSql(hql.toString());*/
             prpDcustomerUnits = this.findByHql(hql);
             if(prpDcustomerUnits!=null && !prpDcustomerUnits.isEmpty()){
                 page = this.findByHql(hql, pageNo1, pageSize1); 
             }else{
            	 page = new Page();
             }
             //page = super.find(PrpDcustomerUnit.class,queryRule, pageNo1, pageSize1);
        }catch (Exception e) {
            e.printStackTrace();
        }
        
        dictPage.setData(page.getResult());
        dictPage.setTotalRecordCount((long)prpDcustomerUnits.size()); 
        dictPage.setPageNo(Integer.parseInt(pageNo));
        dictPage.setPageSize(Integer.parseInt(pageSize));
        long totalCount = prpDcustomerUnits.size();        
        long pageCount = 0L;
        if(totalCount % (long)pageSize1 == 0L)
            pageCount = totalCount / (long)pageSize1;
        else
            pageCount =  totalCount / (long)pageSize1 + 1L;
        dictPage.setPageCount(pageCount);
       // PrpDcustomer prpDcustomer = new PrpDcustomer();
        return dictPage;
    }
	/**
	 * 
	 * TODO 方案双击域查询（可选）.
	 * @see cn.com.sinosoft.dms.webservice.facade.DictionaryService#getRationRalationListBySeriesCode(java.lang.String, java.util.Map)
	 */
	public DictPage getRationRalationListBySeriesCode(String systemCode, Map values)
            throws Exception {	    
        String seriesCode = (String) values.get("seriesCode");
        String rationCode = (String) values.get("rationCode");
        String mainFalg = (String) values.get("mainFalg");
        String pageNo = (String) values.get("pageNo");
        String pageSize = (String) values.get("pageSize");
        DictPage dictPage = new DictPage();
      /*  PrpDrationClauseKind typeOne = (PrpDrationClauseKind) values
                .get("prpDrationClauseKind");*/
        //cn.com.sinosoft.dms.model.PrpDrationClauseKind type = new cn.com.sinosoft.dms.model.PrpDrationClauseKind();
        List<PrpDration> prpDrations = null ;
        String hql = null;
        try{
            if("".equals(rationCode) || rationCode == null){
               // hql = "from PrpDsetRationrelation where setCode = '"+seriesCode+"'";
                if(mainFalg.equals("1"))
                    hql = "from PrpDration where tcol1='0' and rationCode in " +
                    		"(select id.rationCode from PrpDsetRationrelation where setCode='"+seriesCode+"')";
                else
                    hql = "from PrpDration where tcol1='1' and rationCode in " +
                    		"(select id.rationCode from PrpDsetRationrelation where setCode='"+seriesCode+"')";
            }else{
                if(mainFalg.equals("1"))
                    hql = "from PrpDration where tcol1=0 and rationCode in " +
                    		"(select id.rationCode from PrpDsetRationrelation where setCode = '"+seriesCode+"' and rationCode = '"+rationCode+"')";
                else
                    hql = "from PrpDration where tcol1=1 and rationCode in " +
                            "(select id.rationCode from PrpDsetRationrelation where setCode = '"+seriesCode+"' and rationCode = '"+rationCode+"')";
            }           
            prpDrations = this.findByHql(hql);
        }catch (Exception e) {
            e.printStackTrace();
        }
        
        dictPage.setData(prpDrations);
        if(prpDrations == null){
            dictPage.setTotalRecordCount(0L);
        }else{
            dictPage.setTotalRecordCount((long)prpDrations.size());
        }       
        dictPage.setPageNo(Integer.parseInt(pageNo));
        dictPage.setPageSize(Integer.parseInt(pageSize));
        long totalCount = prpDrations.size();
        int pageSize1 = Integer.parseInt(pageSize);
        long pageCount = 0L;
        if(totalCount % (long)pageSize1 == 0L)
            pageCount = totalCount / (long)pageSize1;
        else
            pageCount =  totalCount / (long)pageSize1 + 1L;
        dictPage.setPageCount(pageCount);
       // PrpDcustomer prpDcustomer = new PrpDcustomer();
        return dictPage;
    }
	
	public DictPage getPrpDcustomerUnit(String systemCode, Map values)
			throws Exception {
		int pageNo = (Integer) values.get("pageNO");
		int pageSize = (Integer) values.get("pageSize");
		String organizeCode = (String) values.get("organizeCode");
		String customerCode = (String) values.get("customerCode");
		String customerCName = (String) values.get("customerCName");
		String unitCode = (String) values.get("unitCode");
		DictPage dictPage = new DictPage();
		boolean hasFirstCon = false;
		StringBuffer condition = new StringBuffer(256);
		StringBuffer hql = new StringBuffer("from PrpDcustomerUnit a ");
		List<cn.com.sinosoft.dms.model.PrpDcustomerUnit> list = null;
		if (organizeCode != null && !"".equals(organizeCode)) {
			condition.append(hasFirstCon ? " and " : " where ");
			// condition.append(" a.id.riskCode = '").append(riskCode).append("'");
			condition.append(" a.organizeCode = '").append(organizeCode)
					.append("'");
			hasFirstCon = true;
		}
		 if (customerCode != null && !"".equals(customerCode)) {
			 condition.append(hasFirstCon?" and ":" where ");
			 condition.append(" a.customerCode ='").append(customerCode).append("'");
			 hasFirstCon = true;
		}
		 if (customerCName != null && !"".equals(customerCName)) {
			 condition.append(hasFirstCon?" and ":" where ");
			 condition.append(" a.customerCName like'").append(customerCName).append("%'");
			 hasFirstCon = true;
		}
		 if (unitCode != null && !"".equals(unitCode)) {
			 condition.append(hasFirstCon?" and ":" where ");
			 condition.append(" a.unitCode like'").append(unitCode).append("%'");
			 hasFirstCon = true;
		}
		hql.append(condition);
		if (pageNo == 0 || pageSize == 0) {
			int count = getCount("dms", "PrpDcustomerUnit", condition.toString());
			if (count > 1000) {
				logger.error("系统" + systemCode + "查询记录数过大，不予返回！");
				throw new Exception("系统" + systemCode + "调用接口"
						+ ServiceInfoConst.GETPRPDCUSTOMERUNIT
						+ "查询记录数过大，请缩小查询范围");
			} else {
				list = super.findByHql(hql.toString());
			}
			
			List<com.sinosoft.dmsdriver.model.PrpDcustomerUnit> list1 = new ArrayList<com.sinosoft.dmsdriver.model.PrpDcustomerUnit>();
			for (cn.com.sinosoft.dms.model.PrpDcustomerUnit typeUnit : list) {
				com.sinosoft.dmsdriver.model.PrpDcustomerUnit type1 = new com.sinosoft.dmsdriver.model.PrpDcustomerUnit();
//				//added by wanglz 20130603 start 
//				PrpDcustomerFXQ typeFXQ = super.get(PrpDcustomerFXQ.class, typeUnit.getCustomerCode());
//				type1.setIdentifyNumber(typeFXQ.getIdentifyName());
//				type1.setSex(typeFXQ.getSex());
//				System.out.println("dictionaryServiceImpl.java=========3490hang====="+type1.getIdentifyNumber());
//				//added by wanglz 20130603 end
				type1.setAccount(typeUnit.getAccount());type1.setAddressCName(typeUnit.getAddressCName());
				type1.setAddressEName(typeUnit.getAddressEName());
				type1.setArticleCode(typeUnit.getArticleCode());
				type1.setBank(typeUnit.getBank());type1.setBlackState(typeUnit.getBlackState());
				type1.setBusinessRange(typeUnit.getBusinessRange());type1.setBusinessSort(typeUnit.getBusinessSort());
				type1.setBusinessSource(typeUnit.getBusinessSource());
				type1.setCareerRiskGrade(typeUnit.getCareerRiskGrade());type1.setComCode(typeUnit.getComCode());
				type1.setCreditLevel(typeUnit.getCreditLevel());type1.setCustomerCName(typeUnit.getCustomerCName());
				type1.setCustomerCode(typeUnit.getCustomerCode());type1.setCustomerEName(typeUnit.getCustomerEName());
				type1.setCustomerFlag(typeUnit.getCustomerFlag());
				type1.setCustomerFlag(typeUnit.getCustomerFlag());type1.setCustomerKind(typeUnit.getCustomerKind());
				PrpDcustomer prpDcustomer = super.get(PrpDcustomer.class, typeUnit.getCustomerCode());
				type1.setCustomerShortName(typeUnit.getCustomerShortName());type1.setCustomerType(prpDcustomer.getCustomerType());
				type1.setEmailAddress(typeUnit.getEmailAddress());type1.setEconomyCode(typeUnit.getEconomyCode());
				type1.setEmploySum(typeUnit.getEmploySum());type1.setFatherCode(typeUnit.getFatherCode());
				type1.setFaxNumber(typeUnit.getFaxNumber());type1.setFlag(typeUnit.getFlag());
				type1.setHandlerCode(typeUnit.getHandlerCode());type1.setIndustryCode(typeUnit.getIndustryCode());
				type1.setInputDate(typeUnit.getInputDate());type1.setLeaderName(typeUnit.getLeaderName());
				type1.setLinkerName(typeUnit.getLinkerName());type1.setLowerViewFlag(typeUnit.getLowerViewFlag());
				type1.setMeasureCode(typeUnit.getMeasureCode());type1.setMobile(typeUnit.getMobile());
				type1.setNetAddress(typeUnit.getNetAddress());type1.setNewCustomerCode(typeUnit.getNewCustomerCode());
				type1.setOperatorCode(typeUnit.getOperatorCode());type1.setOrganizeCode(typeUnit.getOrganizeCode());
				type1.setPassword(typeUnit.getPassword());type1.setPhoneNumber(typeUnit.getPhoneNumber());
				type1.setPossessNature(typeUnit.getPossessNature());type1.setPostAddress(typeUnit.getPostAddress());
				type1.setPostCode(typeUnit.getPostCode());type1.setRegionCode(typeUnit.getRegionCode());
				type1.setRegistFund(typeUnit.getRegistFund());type1.setRevenueCode(typeUnit.getRevenueCode());
				type1.setShareHolderFlag(typeUnit.getShareHolderFlag());type1.setShortHandCode(typeUnit.getShortHandCode());
				type1.setSponsorName(typeUnit.getSponsorName());type1.setTaxIdentifyCode(typeUnit.getTaxIdentifyCode());
				type1.setTopLevelFlag(typeUnit.getTopLevelFlag());type1.setUpdateDate(typeUnit.getUpdateDate());
				type1.setUpdaterCode(typeUnit.getUpdaterCode());type1.setValidStatus(type1.getValidStatus());
type1.setWordRiskRank(typeUnit.getWordRiskRank());type1.setVerifyNumber(typeUnit.getVerifyNumber());
	            type1.setLoanAccount(typeUnit.getLoanAccount());type1.setPrincipalName(typeUnit.getPrincipalName());
	            type1.setPrincipalIdentifyType(typeUnit.getPrincipalIdentifyType());
	            type1.setPrincipalIdentifyNumber(typeUnit.getPrincipalIdentifyNumber());
	            type1.setPrincipalIdentifyEndDate(typeUnit.getPrincipalIdentifyEndDate());
	            type1.setPrincipalIdentifyStartDate(typeUnit.getPrincipalIdentifyStartDate());
				type1.setWordRiskRank(typeUnit.getWordRiskRank());
type1.setNationalityAddress(typeUnit.getNationalityAddress());
				type1.setPhoneAreaNumber(typeUnit.getPhoneAreaNumber());
				type1.setPhoneExtNumber(typeUnit.getPhoneExtNumber());
				type1.setLocalNo(typeUnit.getLocalNo());
				type1.setLocalName(typeUnit.getLocalName());
				type1.setMinimumPreium(typeUnit.getMinimumPreium());
				type1.setUnitCode(typeUnit.getUnitCode());
				type1.setCreditNumber(typeUnit.getCreditNumber());type1.setCollateralNumber(typeUnit.getCollateralNumber());
	            type1.setLoansBehalfNumber(typeUnit.getLoansBehalfNumber());type1.setLoansDepartment(typeUnit.getLoansDepartment());
				type1.setMobileTelephone(typeUnit.getMobileTelephone());
				
	            list1.add(type1);
			}
			dictPage.setData(list1);
		} else {
			Page page = super.findByHql(hql.toString(), pageNo, pageSize);
			
			// list = page.getResult();
			list = super.findByHql(hql.toString());
			List<com.sinosoft.dmsdriver.model.PrpDcustomerUnit> list1 = new ArrayList<com.sinosoft.dmsdriver.model.PrpDcustomerUnit>();
			for (cn.com.sinosoft.dms.model.PrpDcustomerUnit typeUnit : list) {
				com.sinosoft.dmsdriver.model.PrpDcustomerUnit type1 = new com.sinosoft.dmsdriver.model.PrpDcustomerUnit();
//				//added by wanglz 20130603 start 
//				PrpDcustomerFXQ typeFXQ = super.get(PrpDcustomerFXQ.class, typeUnit.getCustomerCode());
//				type1.setIdentifyNumber(typeFXQ.getIdentifyName());
//				System.out.println("dictionaryServiceImpl.java=========3490hang====="+type1.getIdentifyNumber());
//				type1.setSex(typeFXQ.getSex());
//				//added by wanglz 20130603 end
				type1.setAccount(typeUnit.getAccount());type1.setAddressCName(typeUnit.getAddressCName());
				type1.setAddressEName(typeUnit.getAddressEName());
				type1.setArticleCode(typeUnit.getArticleCode());
				type1.setBank(typeUnit.getBank());type1.setBlackState(typeUnit.getBlackState());
				type1.setBusinessRange(typeUnit.getBusinessRange());type1.setBusinessSort(typeUnit.getBusinessSort());
				type1.setBusinessSource(typeUnit.getBusinessSource());
				type1.setCareerRiskGrade(typeUnit.getCareerRiskGrade());type1.setComCode(typeUnit.getComCode());
				type1.setCreditLevel(typeUnit.getCreditLevel());type1.setCustomerCName(typeUnit.getCustomerCName());
				type1.setCustomerCode(typeUnit.getCustomerCode());type1.setCustomerEName(typeUnit.getCustomerEName());
				type1.setCustomerFlag(typeUnit.getCustomerFlag());
				type1.setCustomerFlag(typeUnit.getCustomerFlag());type1.setCustomerKind(typeUnit.getCustomerKind());
				PrpDcustomer prpDcustomer = super.get(PrpDcustomer.class, typeUnit.getCustomerCode());
				type1.setCustomerShortName(typeUnit.getCustomerShortName());type1.setCustomerType(prpDcustomer.getCustomerType());
				type1.setEmailAddress(typeUnit.getEmailAddress());type1.setEconomyCode(typeUnit.getEconomyCode());
				type1.setEmploySum(typeUnit.getEmploySum());type1.setFatherCode(typeUnit.getFatherCode());
				type1.setFaxNumber(typeUnit.getFaxNumber());type1.setFlag(typeUnit.getFlag());
				type1.setHandlerCode(typeUnit.getHandlerCode());type1.setIndustryCode(typeUnit.getIndustryCode());
				type1.setInputDate(typeUnit.getInputDate());type1.setLeaderName(typeUnit.getLeaderName());
				type1.setLinkerName(typeUnit.getLinkerName());type1.setLowerViewFlag(typeUnit.getLowerViewFlag());
				type1.setMeasureCode(typeUnit.getMeasureCode());type1.setMobile(typeUnit.getMobile());
				type1.setNetAddress(typeUnit.getNetAddress());type1.setNewCustomerCode(typeUnit.getNewCustomerCode());
				type1.setOperatorCode(typeUnit.getOperatorCode());type1.setOrganizeCode(typeUnit.getOrganizeCode());
				type1.setPassword(typeUnit.getPassword());type1.setPhoneNumber(typeUnit.getPhoneNumber());
				type1.setPossessNature(typeUnit.getPossessNature());type1.setPostAddress(typeUnit.getPostAddress());
				type1.setPostCode(typeUnit.getPostCode());type1.setRegionCode(typeUnit.getRegionCode());
				type1.setRegistFund(typeUnit.getRegistFund());type1.setRevenueCode(typeUnit.getRevenueCode());
				type1.setShareHolderFlag(typeUnit.getShareHolderFlag());type1.setShortHandCode(typeUnit.getShortHandCode());
				type1.setSponsorName(typeUnit.getSponsorName());type1.setTaxIdentifyCode(typeUnit.getTaxIdentifyCode());
				type1.setTopLevelFlag(typeUnit.getTopLevelFlag());type1.setUpdateDate(typeUnit.getUpdateDate());
				type1.setUpdaterCode(typeUnit.getUpdaterCode());type1.setValidStatus(type1.getValidStatus());
type1.setWordRiskRank(typeUnit.getWordRiskRank());type1.setVerifyNumber(typeUnit.getVerifyNumber());
                type1.setLoanAccount(typeUnit.getLoanAccount());type1.setPrincipalName(typeUnit.getPrincipalName());
                type1.setPrincipalIdentifyType(typeUnit.getPrincipalIdentifyType());
                type1.setPrincipalIdentifyNumber(typeUnit.getPrincipalIdentifyNumber());
                type1.setPrincipalIdentifyEndDate(typeUnit.getPrincipalIdentifyEndDate());
                type1.setPrincipalIdentifyStartDate(typeUnit.getPrincipalIdentifyStartDate());
				type1.setNationalityAddress(typeUnit.getNationalityAddress());
				type1.setWordRiskRank(typeUnit.getWordRiskRank());
				type1.setPhoneAreaNumber(typeUnit.getPhoneAreaNumber());
				type1.setPhoneExtNumber(typeUnit.getPhoneExtNumber());
				type1.setLocalNo(typeUnit.getLocalNo());
				type1.setLocalName(typeUnit.getLocalName());
				type1.setMinimumPreium(typeUnit.getMinimumPreium());
				type1.setUnitCode(typeUnit.getUnitCode());
				type1.setCreditNumber(typeUnit.getCreditNumber());type1.setCollateralNumber(typeUnit.getCollateralNumber());
                type1.setLoansBehalfNumber(typeUnit.getLoansBehalfNumber());type1.setLoansDepartment(typeUnit.getLoansDepartment());
                type1.setMobileTelephone(typeUnit.getMobileTelephone());
                list1.add(type1);
			}
			dictPage.setData(list1);
			dictPage.setPageNo(pageNo);
			dictPage.setPageSize(pageSize);
			dictPage.setPageCount(page.getTotalPageCount());
			dictPage.setTotalRecordCount(page.getTotalCount());
		}
		return dictPage;
	}

	/**
	 * @author wanglianzhou 新增单位信息
	 * @param
	 * @return DictPage
	 */
	public DictPage savePrpDcustomerUnit(String systemCode, Map values)
			throws Exception {
		System.out.println("-------------進入單位保存方法！--------------- ");
		String comCode = (String) values.get("comCode");
		com.sinosoft.dmsdriver.model.PrpDcustomerUnit typeOne = (com.sinosoft.dmsdriver.model.PrpDcustomerUnit) values
				.get("prpDcustomerUnit");
		//暂时注掉----add  by pengxiaohui
		//PrpDcustomerFXQ typeTwo1 = (PrpDcustomerFXQ)values.get("prpDcustomerFXQ");
		cn.com.sinosoft.dms.model.PrpDcustomerUnit type = new cn.com.sinosoft.dms.model.PrpDcustomerUnit();
		//暂时注掉----add  by pengxiaohui
		//cn.com.sinosoft.dms.model.PrpDcustomerFXQ typeTwo = new cn.com.sinosoft.dms.model.PrpDcustomerFXQ();
		PrpDcustomer prpDcustomer = new PrpDcustomer();
		String organizeCode="";
		// add  by  pengxiaohui  ��ʱ������������Ϣ�?ֱ����unit��������ֶ�  2013-11-12
		
		//暂时注掉----add  by pengxiaohui
		
		/*if(typeTwo1.getCustomerType() != null && !"".equals(typeTwo1.getCustomerType())){
			typeTwo.setBusinessRange(typeTwo1.getBusinessRange());
			typeTwo.setBusinessSourceCode(typeTwo1.getBusinessSourceCode());
			typeTwo.setBusinessSourceName(typeTwo1.getBusinessSourceName());
			typeTwo.setCustomerType(typeTwo1.getCustomerType());
			if(null!=typeTwo1.getCustomerCode()&&!"".equals(typeTwo1.getCustomerCode())){
                typeTwo.setCustomerCode(typeTwo1.getCustomerCode());
            }
            String identifyNumber = typeTwo1.getShareHolderIdentifyNumber().substring(1,2);
            System.out.println("dictionaryServiceImpl.java -- -             ------------ ---3544 ==="+identifyNumber);
            if(identifyNumber == "1"){
                typeTwo.setSex("1");
            }
            if(identifyNumber == "2"){
                typeTwo.setSex("2");
            }
            typeTwo.setFlag(typeTwo1.getFlag());typeTwo.setFlag1(typeTwo1.getFlag1());
            typeTwo.setFlag2(typeTwo1.getFlag2());typeTwo.setIdentifyEndDate(typeTwo1.getIdentifyEndDate());
            typeTwo.setIdentifyName(typeTwo1.getIdentifyName());typeTwo.setIdentifyNumber(typeTwo1.getIdentifyNumber());
            typeTwo.setIdentifyStartDate(typeTwo1.getIdentifyStartDate());typeTwo.setLeaderIdentifyEndDate(typeTwo1.getLeaderIdentifyEndDate());
            typeTwo.setLeaderIdentifyName(typeTwo1.getLeaderIdentifyName());typeTwo.setLeaderIdentifyNumber(typeTwo1.getLeaderIdentifyNumber());
            typeTwo.setLeaderIdentifyStartDate(typeTwo1.getLeaderIdentifyStartDate());typeTwo.setLeaderIdentifyType(typeTwo1.getLeaderIdentifyType());
            typeTwo.setLeaderName(typeTwo1.getLeaderName());typeTwo.setOccupationCode(typeTwo1.getOccupationCode());
            typeTwo.setOccupationName(typeTwo1.getOccupationName());typeTwo.setPhoneNumber(typeTwo1.getPhoneNumber());
            typeTwo.setPrincipalIdentifyStartDate(typeTwo1.getPrincipalIdentifyStartDate());
            typeTwo.setPrincipalIdentifyEndDate(typeTwo1.getPrincipalIdentifyEndDate());
            typeTwo.setPrincipalIdentifyName(typeTwo1.getPrincipalIdentifyName());
            typeTwo.setPrincipalIdentifyNumber(typeTwo1.getPrincipalIdentifyNumber());
            typeTwo.setPrincipalIdentifyStartDate(typeTwo.getPrincipalIdentifyStartDate());
            typeTwo.setPrincipalIdentifyType(typeTwo1.getPrincipalIdentifyType());
            typeTwo.setPrincipalName(typeTwo1.getPrincipalName());
            typeTwo.setSex(typeTwo1.getSex());typeTwo.setShareHolderIdentifyEndDate(typeTwo1.getShareHolderIdentifyEndDate());
            typeTwo.setShareHolderIdentifyName(typeTwo1.getShareHolderIdentifyName());
            typeTwo.setShareHolderIdentifyNumber(typeTwo1.getShareHolderIdentifyNumber());
            typeTwo.setShareHolderIdentifyStartDate(typeTwo1.getShareHolderIdentifyStartDate());
            typeTwo.setShareHolderIdentifyType(typeTwo1.getShareHolderIdentifyType());
            typeTwo.setTaxRegisterNumber(typeTwo1.getTaxRegisterNumber());
            typeTwo.setShareHolderName(typeTwo1.getShareHolderName());
		}*/
		
		//-----end  by pengxiaohui 20140612
		if (typeOne != null) {
		    if(null!=typeOne.getCustomerCode()&&!"".equals(typeOne.getCustomerCode())){
                prpDcustomer = (PrpDcustomer) super.get(PrpDcustomer.class,
                        typeOne.getCustomerCode());
                //modify by liudezhen 20160216 start
                if(prpDcustomer == null){
                	prpDcustomer = new PrpDcustomer();
                }
                //modify by liudezhen 20160216 end
            }
			prpDcustomer.setAddressCName(typeOne.getAddressCName());
			prpDcustomer.setAddressEName(typeOne.getAddressEName());prpDcustomer.setArticleCode(typeOne.getArticleCode());
			prpDcustomer.setBlackState(typeOne.getBlackState());prpDcustomer.setCustomerCName(typeOne.getCustomerCName());
			prpDcustomer.setCustomerEName(typeOne.getCustomerEName());prpDcustomer.setCustomerFlag(typeOne.getCustomerFlag());
			prpDcustomer.setCustomerType("2");prpDcustomer.setInputDate(typeOne.getInputDate());
			prpDcustomer.setOperatorCode(typeOne.getOperatorCode());prpDcustomer.setShortHandCode(typeOne.getShortHandCode());
			prpDcustomer.setValidStatus(typeOne.getValidStatus());
			
			/*String str = "select prpDcustomerUnit_seq.nextval from dual ";
			 List templist1 = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(str).list();			
			 String billNo1 = null ;
	      	 if(templist1 != null && !templist1.isEmpty()){
	      		 billNo1 =((BigDecimal)templist1.get(0)).toString();
	      		 String math="0000000";
	      		 math=math+billNo1;
			if(typeOne.getOrganizeCode()==""){
				 organizeCode = "A" + math.substring(billNo1.length(),math.length());
				prpDcustomer.setOrganizeCode(organizeCode);
		      	 }
			else{
				prpDcustomer.setOrganizeCode(typeOne.getOrganizeCode());
			}
			}*/
			
			if(typeOne.getOrganizeCode()==""){

				String str = "select prpDcustomerUnit_seq.nextval from dual ";
				 List templist1 = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(str).list();			
				 String billNo1 = null ;
		      	 if(templist1 != null && !templist1.isEmpty()){
		      		 billNo1 =((BigDecimal)templist1.get(0)).toString();
		      		 String math="0000000";
		      		 math=math+billNo1;
				 organizeCode = "A" + math.substring(billNo1.length(),math.length());
				prpDcustomer.setOrganizeCode(organizeCode);
		      	 }
			}
			else{
				prpDcustomer.setOrganizeCode(typeOne.getOrganizeCode());
			}
			
			String customerCode=typeOne.getCustomerCode();
			if(customerCode==null||"".equals(customerCode)){
			String strSqlStatement = "select for_customercode_9.nextval from dual ";
			//List templist = this.getHibernateTemplate().getSessionFactory().openSession().createSQLQuery(strSqlStatement).list();
            List templist = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(strSqlStatement).list();			
			String billNo = null ;
	      	 if(templist != null && !templist.isEmpty()){
	      		 billNo =((BigDecimal)templist.get(0)).toString();
	      	 }
			String iCustomerType = typeOne.getCustomerKind();
			String strCustomerCode ;
			if(null!=typeOne.getCustomerCode()&&!"".equals(typeOne.getCustomerCode())){
                strCustomerCode=typeOne.getCustomerCode();
            }else{
                if (iCustomerType.equals("1")) {
                    strCustomerCode = "5" + comCode.trim().substring(0, 2)
                            + billNo;
                } else {
                    strCustomerCode = "0" + comCode.trim().substring(0, 2)
                            + billNo;
                }
            }   
			prpDcustomer.setCustomerCode(strCustomerCode); // added by wanglz
		    											// 20130424
			}else{
				prpDcustomer.setCustomerCode(typeOne.getCustomerCode());
			}
			// prpDcustomer.setCustomerType(typeOne.getCustomerKind());
			prpDcustomer.setCustomerType("2");
			prpDcustomer.setValidStatus("1");  //暂时写死  wlz
			type.setAccount(typeOne.getAccount());// 银行账号
			type.setAddressCName(typeOne.getAddressCName());// 地址中文名称
			type.setAddressEName(typeOne.getAddressEName());// 地址英文名称
			type.setArticleCode(typeOne.getArticleCode());
			type.setBank(typeOne.getBank());// 银行
			type.setBlackState(typeOne.getBlackState());// 黑名单标志
			type.setComCode(typeOne.getComCode());// 商业机构代码
			type.setCreditLevel(typeOne.getCreditLevel());type.setCustomerCName(typeOne.getCustomerCName());
			type.setCustomerCode(typeOne.getCustomerCode());type.setCustomerEName(typeOne.getCustomerEName());
			type.setCustomerFlag(typeOne.getCustomerFlag());type.setCustomerKind(typeOne.getCustomerKind());
			type.setFaxNumber(typeOne.getFaxNumber());type.setCustomerShortName(typeOne.getCustomerShortName());
			type.setFlag(typeOne.getFlag());type.setHandlerCode(typeOne.getHandlerCode());
			//mantis： XXXXX，處理人員：Sam，需求單編號：CAR0027，新增電子信箱，存檔時也要傳此參數
			type.setEmailAddress(typeOne.getEmailAddress());
			Date date=new Date();
			type.setInputDate(date);//修改時間
//			type.setInputDate(type.getInputDate());
			type.setMobile(typeOne.getMobile());type.setNetAddress(typeOne.getNetAddress());
			type.setNewCustomerCode(typeOne.getNewCustomerCode());type.setOperatorCode(typeOne.getOperatorCode());
			type.setPassword(typeOne.getPassword());type.setPhoneNumber(typeOne.getPhoneNumber());
			type.setLinkerName(typeOne.getLinkerName());
			type.setPostCode(typeOne.getPostCode());type.setShortHandCode(typeOne.getShortHandCode());
			type.setTopLevelFlag(typeOne.getTopLevelFlag());type.setUpdateDate(typeOne.getUpdateDate());
			type.setValidStatus(typeOne.getValidStatus());type.setNewCustomerCode(typeOne.getNewCustomerCode());
			if("".equals(typeOne.getOrganizeCode())||null==typeOne.getOrganizeCode()){
				type.setOrganizeCode(organizeCode);
			}else{
			type.setOrganizeCode(typeOne.getOrganizeCode());
			}
			type.setVerifyNumber(typeOne.getVerifyNumber());
			type.setLoanAccount(typeOne.getLoanAccount());type.setPrincipalName(typeOne.getPrincipalName());
			type.setPrincipalIdentifyType(typeOne.getPrincipalIdentifyType());
			type.setPrincipalIdentifyNumber(typeOne.getPrincipalIdentifyNumber());
			type.setPrincipalIdentifyEndDate(typeOne.getPrincipalIdentifyEndDate());
			type.setPrincipalIdentifyStartDate(typeOne.getPrincipalIdentifyStartDate());
			type.setCreditNumber(typeOne.getCreditNumber());
			type.setCollateralNumber(typeOne.getCollateralNumber());
			type.setLoansBehalfNumber(typeOne.getLoansBehalfNumber());
			type.setLoansDepartment(typeOne.getLoansDepartment());			
			type.setLinkerName(typeOne.getLinkerName());
			type.setBusinessRange(typeOne.getBusinessRange());
			type.setBusinessSource(typeOne.getBusinessSource());
			type.setPrincipalIdentifyNumber(typeOne.getPrincipalIdentifyNumber());
			type.setPrincipalIdentifyEndDate(typeOne.getPrincipalIdentifyEndDate());
			type.setPrincipalIdentifyStartDate(typeOne.getPrincipalIdentifyStartDate());
			type.setCreditNumber(typeOne.getCreditNumber());
			type.setCollateralNumber(typeOne.getCollateralNumber());
			type.setLoansBehalfNumber(typeOne.getLoansBehalfNumber());
			type.setLoansDepartment(typeOne.getLoansDepartment());
			
			//add by 添加字段值   通信地址 end
			type.setPostAddress(typeOne.getPostAddress());
			//add by ����ֶ�ֵ   ͨ�ŵ�ַ end
			type.setUnitCode(typeOne.getUnitCode());
			type.setNationalityAddress(typeOne.getNationalityAddress());
			type.setPhoneAreaNumber(typeOne.getPhoneAreaNumber());
			type.setPhoneExtNumber(typeOne.getPhoneExtNumber());
			type.setLocalNo(typeOne.getLocalNo());
			type.setLocalName(typeOne.getLocalName());
			type.setMobileTelephone(typeOne.getMobileTelephone());
			 //add by yjm MC最低保费维护  20141013 start
            type.setMinimumPreium(typeOne.getMinimumPreium());
            //add by yjm MC最低保费维护  20141013 end
			if (type.getValidStatus() == null) {
				type.setValidStatus("1");
			}
		}
		super.save(prpDcustomer);
		prpDcustomer = (PrpDcustomer) super.get(PrpDcustomer.class,
				prpDcustomer.getCustomerCode());
		type.setPrpDcustomer(prpDcustomer);
		type.setCustomerCode(prpDcustomer.getCustomerCode());
		// add  by  pengxiaohui  暂时注掉  20140612
		/*if(typeTwo.getCustomerType() != null && !"".equals(typeTwo.getCustomerType())){
			typeTwo.setCustomerCode(prpDcustomer.getCustomerCode());
			super.save(typeTwo);
		}*/
		if (type.getNewCustomerCode() == null) {
			type.setNewCustomerCode(prpDcustomer.getCustomerCode());
		}
		DictPage dictPage = new DictPage();

		List<cn.com.sinosoft.dms.model.PrpDcustomerUnit> list = new ArrayList<cn.com.sinosoft.dms.model.PrpDcustomerUnit>();
		try {
			super.save(type);
			list.add(type);
			List<com.sinosoft.dmsdriver.model.PrpDcustomerUnit> list1 = new ArrayList<com.sinosoft.dmsdriver.model.PrpDcustomerUnit>();
			for (cn.com.sinosoft.dms.model.PrpDcustomerUnit typeUnit : list) {
				com.sinosoft.dmsdriver.model.PrpDcustomerUnit type1 = new com.sinosoft.dmsdriver.model.PrpDcustomerUnit();
				type1.setAccount(typeUnit.getAccount());type1.setAddressCName(typeUnit.getAddressCName());
				type1.setAddressEName(typeUnit.getAddressEName());
				type1.setArticleCode(typeUnit.getArticleCode());
				type1.setBank(typeUnit.getBank());type1.setBlackState(typeUnit.getBlackState());
				type1.setBusinessRange(typeUnit.getBusinessRange());type1.setBusinessSort(typeUnit.getBusinessSort());
				type1.setBusinessSource(typeUnit.getBusinessSource());
				type1.setCareerRiskGrade(typeUnit.getCareerRiskGrade());type1.setComCode(typeUnit.getComCode());
				type1.setCreditLevel(typeUnit.getCreditLevel());type1.setCustomerCName(typeUnit.getCustomerCName());
				type1.setCustomerCode(typeUnit.getCustomerCode());type1.setCustomerEName(typeUnit.getCustomerEName());
				type1.setCustomerFlag(typeUnit.getCustomerFlag());
				type1.setCustomerFlag(typeUnit.getCustomerFlag());type1.setCustomerKind(typeUnit.getCustomerKind());
				PrpDcustomer prpDcustomer1 = super.get(PrpDcustomer.class, typeUnit.getCustomerCode());
				type1.setCustomerShortName(typeUnit.getCustomerShortName());type1.setCustomerType(prpDcustomer1.getCustomerType());
				type1.setEmailAddress(typeUnit.getEmailAddress());type1.setEconomyCode(typeUnit.getEconomyCode());
				type1.setEmploySum(typeUnit.getEmploySum());type1.setFatherCode(typeUnit.getFatherCode());
				type1.setFaxNumber(typeUnit.getFaxNumber());type1.setFlag(typeUnit.getFlag());
				type1.setHandlerCode(typeUnit.getHandlerCode());type1.setIndustryCode(typeUnit.getIndustryCode());
				type1.setInputDate(typeUnit.getInputDate());type1.setLeaderName(typeUnit.getLeaderName());
				type1.setLinkerName(typeUnit.getLinkerName());type1.setLowerViewFlag(typeUnit.getLowerViewFlag());
				type1.setMeasureCode(typeUnit.getMeasureCode());type1.setMobile(typeUnit.getMobile());
				type1.setNetAddress(typeUnit.getNetAddress());type1.setNewCustomerCode(typeUnit.getNewCustomerCode());
				type1.setOperatorCode(typeUnit.getOperatorCode());type1.setOrganizeCode(typeUnit.getOrganizeCode());
				type1.setPassword(typeUnit.getPassword());type1.setPhoneNumber(typeUnit.getPhoneNumber());
				type1.setLinkerName(typeUnit.getLinkerName());
				type1.setPossessNature(typeUnit.getPossessNature());type1.setPostAddress(typeUnit.getPostAddress());
				type1.setPostCode(typeUnit.getPostCode());type1.setRegionCode(typeUnit.getRegionCode());
				type1.setRegistFund(typeUnit.getRegistFund());type1.setRevenueCode(typeUnit.getRevenueCode());
				type1.setShareHolderFlag(typeUnit.getShareHolderFlag());type1.setShortHandCode(typeUnit.getShortHandCode());
				type1.setSponsorName(typeUnit.getSponsorName());type1.setTaxIdentifyCode(typeUnit.getTaxIdentifyCode());
				type1.setTopLevelFlag(typeUnit.getTopLevelFlag());type1.setUpdateDate(typeUnit.getUpdateDate());
				type1.setUpdaterCode(typeUnit.getUpdaterCode());type1.setValidStatus(type1.getValidStatus());
				type1.setWordRiskRank(typeUnit.getWordRiskRank());
				if("".equals(typeOne.getOrganizeCode())||null==typeOne.getOrganizeCode()){
					type1.setOrganizeCode(organizeCode);
				}else{
					type1.setOrganizeCode(typeUnit.getOrganizeCode());
				}
				//type1.setOrganizeCode(typeUnit.getOrganizeCode());
				type1.setVerifyNumber(typeUnit.getVerifyNumber());
	            type1.setLoanAccount(typeUnit.getLoanAccount());type1.setPrincipalName(typeUnit.getPrincipalName());
	            type1.setPrincipalIdentifyType(typeUnit.getPrincipalIdentifyType());
	            type1.setPrincipalIdentifyNumber(typeUnit.getPrincipalIdentifyNumber());
	            type1.setPrincipalIdentifyEndDate(typeUnit.getPrincipalIdentifyEndDate());
	            type1.setPrincipalIdentifyStartDate(typeUnit.getPrincipalIdentifyStartDate());
	            
	            type1.setNationalityAddress(typeUnit.getNationalityAddress());
				type1.setPhoneAreaNumber(typeUnit.getPhoneAreaNumber());
				type1.setPhoneExtNumber(typeUnit.getPhoneExtNumber());
				type1.setLocalNo(typeUnit.getLocalNo());
				type1.setLocalName(typeUnit.getLocalName());
				type1.setUnitCode(typeUnit.getUnitCode());
				type1.setCreditNumber(typeUnit.getCreditNumber());
				type1.setCollateralNumber(typeUnit.getCollateralNumber());
	            type1.setLoansBehalfNumber(typeUnit.getLoansBehalfNumber());
	            type1.setLoansDepartment(typeUnit.getLoansDepartment());
	            type1.setMobileTelephone(typeUnit.getMobileTelephone());
	            //add by yjm MC最低保费维护  20141013 start
	            type1.setMinimumPreium(typeUnit.getMinimumPreium());
	            //add by yjm MC最低保费维护  20141013 end
////				added by wanglianzhou 20130603 start
//				type1.setIdentifyNumber(typeTwo.getIdentifyNumber());
//				type1.setSex(typeTwo.getSex());
////				added by wanglianzhou 20130603 end
				list1.add(type1);
			}
			dictPage.setData(list1);
			dictPage.setTotalRecordCount(new Long(1));
			System.out.println("-------------單位保存方法結束--------------------");
			return dictPage;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	// modify add end by renshuo 2011-07-12 reason:增加条款责任互斥条件查询

	/**
	 * 查询原工具库prpDkind表数据
	 * 
	 * @param systemCode
	 * @param values
	 * @return
	 * @throws Exception
	 */
	public DictPage getPrpDkind(String systemCode, Map values) throws Exception {
		int pageNo = (Integer) values.get("pageNO");
		int pageSize = (Integer) values.get("pageSize");
		String riskCode = (String) values.get("riskCode");
		String kindCode = (String) values.get("kindCode");
		String ms_Flag = (String) values.get("flag");
		DictPage dictPage = new DictPage();
		boolean hasFirstCon = false;
		StringBuffer condition = new StringBuffer(256);
		StringBuffer hql = new StringBuffer("from PrpDkind a ");
		List list = null;
		if (riskCode != null && !"".equals(riskCode)) {
			condition.append(hasFirstCon ? " and " : " where ");
			condition.append(" a.id.riskCode = '").append(riskCode).append("'");
			hasFirstCon = true;
		}
		if (kindCode != null && !"".equals(kindCode)) {
			condition.append(hasFirstCon ? " and " : " where ");
			condition.append(" a.id.kindCode = '").append(kindCode).append("'");
			hasFirstCon = true;
		}
		condition.append(" and validStatus = 1");
		// added by wanglianzhou 20130317 begin 查询主险、附加险
		if (null != ms_Flag && !"".equals(ms_Flag)) { // wanglainzhou 修改
			if ("M".equals(ms_Flag)) {
				condition.append(" and substr(calculateFlag,3,1) = '1'");
			}
			if ("S".equals(ms_Flag)) {
				condition.append(" and substr(calculateFlag,3,1) = '2'");
			}
		}
		// added by wanglianzhou 20130317 end
		hql.append(condition);
		if (pageNo == 0 || pageSize == 0) {
			int count = getCount("dms", "PrpDkind", condition.toString());
			if (count > 1000) {
				logger.error("系统" + systemCode + "查询记录数过大，不予返回！");
				throw new Exception("系统" + systemCode + "调用接口"
						+ ServiceInfoConst.PRPDKIND + "查询记录数过大，请缩小查询范围");
			} else {
				list = super.findByHql(hql.toString());
			}
			dictPage.setData(list);
		} else {
			Page page = super.findByHql(hql.toString(), pageNo, pageSize);
			// list = page.getResult();
			list = super.findByHql(hql.toString());
			dictPage.setData(list);
			dictPage.setPageNo(pageNo);
			dictPage.setPageSize(pageSize);
			dictPage.setPageCount(page.getTotalPageCount());
			dictPage.setTotalRecordCount(page.getTotalCount());
		}
		return dictPage;
	}

	/**
	 * 查询所有有效记录
	 */
	public DictPage getReinsurer(String systemCode, Map values)
			throws Exception {
		int pageNo = (Integer) values.get("pageNo");
		int pageSize = (Integer) values.get("pageSize");
		String codeOrName = (String) values.get("codeOrName");
		DictPage dictPage = new DictPage();
		StringBuffer hql = new StringBuffer("from PrpDreinsurer a");
		StringBuffer condition = new StringBuffer(256);
		List list = new ArrayList();
		condition.append(" where (a.reinsCode like '" + codeOrName
				+ "%' or a.longName like '" + codeOrName
				+ "%') and a.validStatus = '1'");
		hql.append(condition);
		if (pageNo == 0 || pageSize == 0) {
			int count = getCount("dms", "PrpDreinsurer", condition.toString());
			if (count > 1000) {
				logger.error("系统" + systemCode + "查询记录数过大，不予返回！");
				throw new Exception("系统" + systemCode + "调用接口"
						+ ServiceInfoConst.GETREINSURER + "查询记录数过大，请缩小查询范围");
			} else {
				list = super.findByHql(hql.toString());
			}
			dictPage.setData(list);
		} else {
			Page page = super.findByHql(hql.toString(), pageNo, pageSize);
			list = page.getResult();
			dictPage.setData(list);
			dictPage.setPageNo(pageNo);
			dictPage.setPageSize(pageSize);
			dictPage.setPageCount(page.getTotalPageCount());
			dictPage.setTotalRecordCount(page.getTotalCount());
		}
		return dictPage;
	}

	/**
	 * 查询指定机构的有效数据，若该机构没有数据则逐级向上查询，查到则退出。
	 */
	public DictPage getCoins(String systemCode, Map values) throws Exception {
		int pageNo = (Integer) values.get("pageNo");
		int pageSize = (Integer) values.get("pageSize");
		String comCode = (String) values.get("makeCom");
		String codeOrName = (String) values.get("codeOrName");
		String upperComCode = getAllUpperComCode(comCode);
		String[] upperComs = upperComCode.split(",");
		List list = null;
		StringBuffer hql = new StringBuffer("from PrpDcoins a");
		StringBuffer condition = new StringBuffer(256);
		condition
				.append(" where a.id.comCode = ? and a.validStatus = '1' and (a.id.coinsComCode like '"
						+ codeOrName
						+ "%' or a.coinsComName like '"
						+ codeOrName + "%')");
		hql.append(condition);
		DictPage dictPage = new DictPage();
		if (pageNo == 0 || pageSize == 0) {
			for (int i = upperComs.length - 1; i >= 0; i--) {
				list = super.findByHql(hql.toString(), upperComs[i]);
				if (list.size() > 0) {
					break;
				}
			}
			dictPage.setData(list);
		} else {
			Page page = null;
			for (int i = upperComs.length - 1; i >= 0; i--) {
				page = super.findByHql(hql.toString(), pageNo, pageSize,
						upperComs[i]);
				list = page.getResult();
				if (list.size() > 0) {
					break;
				}
			}
			list = page.getResult();
			dictPage.setData(list);
			dictPage.setPageNo(pageNo);
			dictPage.setPageSize(pageSize);
			dictPage.setPageCount(page.getTotalPageCount());
			dictPage.setTotalRecordCount(page.getTotalCount());
		}
		return dictPage;
	}

	/**
	 * 限额/免额接口
	 * 输入条件：riskCode(险种名称),limitCode(限额/免赔类别代码),kindCode(条款信息),itemCode(标的项目代码)
	 * 其中riskCode不为空,其他三个字段都可以为空，返回list。 先查询责任级别数据,如果没有查到数据则查询条款级别
	 * 新添加extraLimitCode参数,用来查询原逻辑有效的数据 + 查询extraLimitCode不管是否有效的数据
	 * 
	 * @throws Exception
	 */
	public DictPage getPrpDriskLimit(String systemCode, Map values)
			throws Exception {
		int pageNo = (Integer) values.get("pageNo");
		int pageSize = (Integer) values.get("pageSize");
		String riskCode = (String) values.get("riskCode");
		String limitCode = (String) values.get("limitCode");
		String clauseCode = (String) values.get("clauseCode");
		String kindCode = (String) values.get("kindCode");
		String itemCode = (String) values.get("itemCode");
		String limitLevel = (String) values.get("limitLevel");
		String isRecorded = (String) values.get("isRecorded");
		String extraLimitCode = (String) values.get("extraLimitCode");
		String valueFlag = (String) values.get("valueFlag");
		List list = new ArrayList();
		StringBuffer hql = new StringBuffer("from PrpDriskLimit a ");
		StringBuffer con = new StringBuffer(256);
		con.append(" where a.id.riskCode ='").append(riskCode).append("'");
		if (limitCode != null && !"".equals(limitCode)) {
			con.append(" and (a.id.limitCode like '").append(limitCode)
					.append("%'");
			con.append(" or a.limitCName like '").append(limitCode)
					.append("%')");
		}
		if (clauseCode != null && !"".equals(clauseCode)) {
			if (kindCode != null && !"".equals(kindCode)) {
				con.append(" and a.id.clauseCode = '").append(clauseCode).append("'");
				if("Y".equals(valueFlag)){
				   con.append(" and a.kindCode ='").append(kindCode).append("'"); 
				}else{
				   con.append(" and (a.kindCode ='' or a.kindCode = null or a.kindCode ='").append(kindCode).append("')");
				}
			} else {
				con.append(" and a.id.clauseCode ='").append(clauseCode)
						.append("'");
			}
		}
		if (itemCode != null && !"".equals(itemCode)) {
			con.append(" and a.itemCode = '").append(itemCode).append("'");
		}
		if (limitLevel != null && !"".equals(limitLevel)) {
			if (!limitLevel.equals("0")) {
				con.append(" and a.limitLevel !='0'");
			} else {
				con.append(" and a.limitLevel ='0'");
			}
		}
		if (isRecorded != null && !"".equals(isRecorded)) {
			con.append(" and a.isRecorded ='").append(isRecorded).append("'");
		}
		con.append(" and limitFlag !='2'");
		con.append(" and (a.id.limitCode != '000048' or a.limitCName != '保险金额')");// 不查出限额/免赔额名称为'保险金额';限额/免赔额代码为'000048'的数据
		con.append(" and a.validInd = '1'");
		con.append(" and (sysdate between validdate and invaliddate or sysdate>= validdate and invaliddate is null )");
		if (extraLimitCode != null && !"".equals(extraLimitCode)) {
			String extraLimitCodes = extraLimitCode.replace(",", "','");
			con.append(" or (a.id.riskCode ='").append(riskCode).append("'");
			con.append(" and a.id.limitCode in('").append(extraLimitCodes)
					.append("')");
			if (clauseCode != null && !"".equals(clauseCode)) {
				if (kindCode != null && !"".equals(kindCode)) {
					con.append(" and a.id.clauseCode = '").append(clauseCode)
							.append("'");
					con.append(
							" and (a.kindCode ='' or a.kindCode = null or a.kindCode ='")
							.append(kindCode).append("')");
				} else {
					con.append(" and a.id.clauseCode ='").append(clauseCode)
							.append("'");
				}
			}
			if (itemCode != null && !"".equals(itemCode)) {
				con.append(" and a.itemCode = '").append(itemCode).append("'");
			}
			if (limitLevel != null && !"".equals(limitLevel)) {
				if (!limitLevel.equals("0")) {
					con.append(" and a.limitLevel !='0'");
				} else {
					con.append(" and a.limitLevel ='0'");
				}
			}
			if (isRecorded != null && !"".equals(isRecorded)) {
				con.append(" and a.isRecorded ='").append(isRecorded)
						.append("'");
			}
			con.append(" and limitFlag !='2'");
			con.append(" and (a.id.limitCode != '000048' or a.limitCName != '保险金额')");// 不查出限额/免赔额名称为'保险金额';限额/免赔额代码为'000048'的数据
			con.append(" and (sysdate between validdate and invaliddate or sysdate>= validdate and invaliddate is null ))");
		}
		con.append(" order by a.id.serialNo ");
		hql.append(con);
		DictPage dictPage = new DictPage();
		if (pageNo == 0 || pageSize == 0) {
			int count = getCount("dms", "PrpDriskLimit", con.toString());
			if (count > 2000) {
				logger.error("系统" + systemCode + "查询记录数过大，不予返回！");
				throw new Exception("系统" + systemCode + "调用接口"
						+ ServiceInfoConst.PRPDRISKLIMIT + "查询记录数过大，请缩小查询范围");
			} else {
				list = super.findByHql(hql.toString());
			}
			dictPage.setData(list);
		} else {
			Page page = super.findByHql(hql.toString(), pageNo, pageSize);
			list = page.getResult();
			dictPage.setData(list);
			dictPage.setPageNo(pageNo);
			dictPage.setPageSize(pageSize);
			dictPage.setPageCount(page.getTotalPageCount());
			dictPage.setTotalRecordCount(page.getTotalCount());
		}
		return dictPage;
	}

	/**
	 * 旧限额/免额接口
	 * 输入条件：riskCode(险种名称),limitCode(限额/免赔类别代码),kindCode(条款信息),itemCode(标的项目代码)
	 * 其中riskCode不为空，其他三个字段都可以为空，返回list。
	 * 
	 * @throws Exception
	 */
	public DictPage getPrpDlimit(String systemCode, Map values)
			throws Exception {
		int pageNo = (Integer) values.get("pageNo");
		int pageSize = (Integer) values.get("pageSize");
		String riskCode = (String) values.get("riskCode");
		String limitCode = (String) values.get("limitCode");
		String kindCode = (String) values.get("kindCode");
		String itemCode = (String) values.get("itemCode");
		List list = new ArrayList();
		StringBuffer hql = new StringBuffer("from PrpDlimit a ");
		StringBuffer con = new StringBuffer(256);
		con.append(" where a.id.riskCode='").append(riskCode).append("'");
		if (limitCode != null && !"".equals(limitCode)) {
			con.append(" and a.id.limitCode = '").append(limitCode).append("'");
		}
		if (kindCode != null && !"".equals(kindCode)) {
			con.append(" and a.kindCode = '").append(kindCode).append("'");
		}
		if (itemCode != null && !"".equals(itemCode)) {
			con.append(" and a.itemCode = '").append(itemCode).append("'");
		}
		con.append(" and a.validStatus = '1'");
		hql.append(con);
		DictPage dictPage = new DictPage();
		if (pageNo == 0 || pageSize == 0) {
			int count = getCount("dms", "PrpDlimit", con.toString());
			if (count > 1000) {
				logger.error("系统" + systemCode + "调用接口"
						+ ServiceInfoConst.PRPDLIMIT + "查询记录数过大，不予返回！");
				throw new Exception("系统" + systemCode + "调用接口"
						+ ServiceInfoConst.PRPDLIMIT + "查询记录数过大，请缩小查询范围");
			} else {
				list = super.findByHql(hql.toString());
			}
			dictPage.setData(list);
		} else {
			Page page = super.findByHql(hql.toString(), pageNo, pageSize);
			list = page.getResult();
			dictPage.setData(list);
			dictPage.setPageNo(pageNo);
			dictPage.setPageSize(pageSize);
			dictPage.setPageCount(page.getTotalPageCount());
			dictPage.setTotalRecordCount(page.getTotalCount());
		}
		return dictPage;
	}

	/**
	 * 国管局项目一级预算单位信息接口，接口支持代码和名称的模糊查询，并支持分页 输入条件：传入参数fieldValue
	 * 可能情况：代码或名称,无%或*（由数据字典处理）,空值（查询出所有记录）。 返回list。
	 */
	public DictPage getPrpDsettlementByr(String systemCode, Map values)
			throws Exception {
		int pageNo = (Integer) values.get("pageNo");
		int pageSize = (Integer) values.get("pageSize");
		String fieldValue = (String) values.get("fieldValue");
		DictPage dictPage = new DictPage();
		StringBuffer hql = new StringBuffer(256);
		StringBuffer con = new StringBuffer(256);
		List list = new ArrayList();
		hql.append("from PrpDsettlementByr a ");
		con.append(" where (a.buyerUnitCode like '" + fieldValue
				+ "%' or a.buyerUnitName like '" + fieldValue + "%')");
		con.append(" and validStatus = '1'");
		hql.append(con);
		if (pageNo == 0 || pageSize == 0) {
			int count = getCount("dms", "PrpDsettlementByr", con.toString());
			if (count > 1000) {
				logger.error("系统" + systemCode + "查询记录数过大，不予返回！");
				throw new Exception("系统" + systemCode + "调用接口"
						+ ServiceInfoConst.PRPDSETTLEMEMTBYR
						+ "查询记录数过大，请缩小查询范围");
			} else {
				list = super.findByHql(hql.toString());
			}
			dictPage.setData(list);
		} else {
			Page page = super.findByHql(hql.toString(), pageNo, pageSize);
			list = page.getResult();
			dictPage.setData(list);
			dictPage.setPageNo(pageNo);
			dictPage.setPageSize(pageSize);
			dictPage.setPageCount(page.getTotalPageCount());
			dictPage.setTotalRecordCount(page.getTotalCount());
		}
		return dictPage;

	}

	/**
	 * 国管局项目PICC联系人信息接口，接口支持代码和名称的模糊查询，并支持分页 输入条件：传入参数fieldValue
	 * 可能情况：代码或名称,无%或*（由数据字典处理）,空值（查询出所有记录）。 返回list。
	 * 
	 * @throws Exception
	 */
	public DictPage getPrpDsettlementLkr(String systemCode, Map values)
			throws Exception {
		int pageNo = (Integer) values.get("pageNo");
		int pageSize = (Integer) values.get("pageSize");
		String fieldValue = (String) values.get("fieldValue");
		DictPage dictPage = new DictPage();
		StringBuffer hql = new StringBuffer(256);
		StringBuffer con = new StringBuffer(256);
		List list = new ArrayList();
		hql.append("from PrpDsettlementLkr a where");
		con.append(" (a.linkerCode like '" + fieldValue
				+ "%' or a.linkerName like '" + fieldValue + "%')");
		con.append(" and validStatus = '1'");
		hql.append(con);
		if (pageNo == 0 || pageSize == 0) {
			int count = getCount("dms", "PrpDsettlementLkr", con.toString());
			if (count > 1000) {
				logger.error("系统" + systemCode + "查询记录数过大，不予返回！");
				throw new Exception("系统" + systemCode + "调用接口"
						+ ServiceInfoConst.PRPDSETTLEMENTLKR
						+ "查询记录数过大，请缩小查询范围");
			} else {
				list = super.findByHql(hql.toString());
			}
			dictPage.setData(list);
		} else {
			Page page = super.findByHql(hql.toString(), pageNo, pageSize);
			list = page.getResult();
			dictPage.setData(list);
			dictPage.setPageNo(pageNo);
			dictPage.setPageSize(pageSize);
			dictPage.setPageCount(page.getTotalPageCount());
			dictPage.setTotalRecordCount(page.getTotalCount());
		}
		return dictPage;
	}

	/**
	 * 根据主键makeCom进行主键模糊查询
	 */
	public DictPage getPrpDstatistics(String systemCode, String makeCom,
			int pageNo, int pageSize) {
		List list = new ArrayList();
		DictPage dictPage = new DictPage();
		StringBuffer hql = new StringBuffer("from PrpDstatistics a ");
		hql.append("where a.makeCom like '");
		hql.append(makeCom);
		hql.append("%'");
		if (pageNo == 0 || pageSize == 0) {
			list = super.findByHql(hql.toString());
			dictPage.setData(list);
		} else {
			Page page = super.findByHql(hql.toString(), pageNo, pageSize);
			dictPage.setData(page.getResult());
			dictPage.setPageNo(pageNo);
			dictPage.setPageSize(pageSize);
			dictPage.setPageCount(page.getTotalPageCount());
			dictPage.setTotalRecordCount(page.getTotalCount());
		}
		return dictPage;
	}

	/*
	 * 更新PrpDstatistics表 提供的数据如果存在则更新，不存在则保存
	 */
	public void updatePrpDstatistics(String systemCode,
			PrpDstatistics prpDstatistics) {
		Date current = Calendar.getInstance().getTime();
		// 查询库中是否有该条记录
		PrpDstatistics obj = (PrpDstatistics) super.getHibernateTemplate().get(
				PrpDstatistics.class, prpDstatistics.getMakeCom());
		// 如果没有该条记录则应该 更新 insertTime 和 updateTime 为当前日期
		if (obj == null) {
			prpDstatistics.setInsertTime(current);
			prpDstatistics.setUpdateTime(current);
			super.getHibernateTemplate().saveOrUpdate(prpDstatistics);
		}
		// 如果存在该条记录则只更新 updateTiem 字段
		else {
			obj.setKsdm(prpDstatistics.getKsdm());
			obj.setOpCode(prpDstatistics.getOpCode());
			obj.setStatisticsYM(prpDstatistics.getStatisticsYM());
			obj.setUpdateTime(current);
			obj.setFlag(prpDstatistics.getFlag());
			super.getHibernateTemplate().saveOrUpdate(obj);
		}
	}

	/**
	 * prpDcode表代码的新旧转换
	 * 
	 * @param systemCode
	 * @param codeType
	 * @param codeCode
	 * @param transType
	 *            1:new → old ，codecode为新代码 2:old → new ，codecode为老代码
	 * @return
	 */
	public List codeTransform(String systemCode, String codeType,
			String codeCode, String transType) {
		StringBuffer hql = new StringBuffer(256);
		if ("1".equals(transType)) {
			// hql.append("select a.oldCodeCode from PrpDnewCode a where a.id.codeType = ? and a.id.codeCode = ?");
			hql.append("select a.oldCodeType,a.oldCodeCode from PrpDnewCode a where a.id.codeType = ? and a.id.codeCode = ?");
		} else if ("2".equals(transType)) {
			// hql.append("select a.id.codeCode from PrpDnewCode a where a.id.codeType = ? and a.oldCodeCode = ?");
			hql.append("select a.newCodeType,a.newCodeCode from PrpDcode a where a.id.codeType = ? and a.id.codeCode = ?");
		}
		List list = super.findByHql(hql.toString(), codeType, codeCode);
		return list;
	}

	/**
	 * 险种代码新老转换
	 * 
	 * @param systemCode
	 * @param riskCode
	 *            非空
	 * @param clauseCode
	 *            当riskCode为DAA时，不能为空
	 * @param kindCode
	 *            当riskCode为DAA或DAB时，不能为空
	 * @param transtype
	 *            1: new → old 入参riskCode新代码，另外两个入参没用 2： old → new 入参皆为老代码，
	 * @return 1： 返回唯一的riskCode 2: 返回老产品对应的riskCode
	 */
	public List riskTransform(String systemCode, String riskCode,
			String clauseCode, String kindCode, String transType)
			throws Exception {
		StringBuffer hql = new StringBuffer(256);
		List list = null;
		if (riskCode == null) {
			throw new Exception("参数：险种代码不能为空");
		} else if ("DAA".equals(riskCode)) {
			if (clauseCode == null || kindCode == null) {
				throw new Exception("险种为DAA时条款类型和险别代码不能为空");
			}
		} else if ("DAB".equals(riskCode) && kindCode == null) {
			throw new Exception("险种为DAB时险别代码不能为空");
		}
		transType = (transType == null || "".equals(transType)) ? "1"
				: transType; // transType默认为1
		if ("1".equals(transType)) {
			hql.append("select a.oldRiskCode from PrpDrisk a where a.riskCode = ?");
			list = super.findByHql(hql.toString(), riskCode);
		} else { // 老代码转换新代码
			hql.append("select a.riskCode from PrpDrisk a where a.riskCode = ?");
			if ("DAA".equals(riskCode)) {
				if ("F47".equals(clauseCode)) {
					list = super.findByHql(hql.toString(), "DAT");
				} else if ("F48".equals(clauseCode)) {
					list = super.findByHql(hql.toString(), "DAZ");
				} else if ("BZ".equals(kindCode)) {
					list = super.findByHql(hql.toString(), "DZA");
				} else {
					list = super.findByHql(hql.toString(), "DAA");
				}
			} else if ("DAB".equals(riskCode)) {
				if ("BZ".equals(kindCode)) {
					list = super.findByHql(hql.toString(), "DZB");
				} else {
					list = super.findByHql(hql.toString(), "DAB");
				}
			} else {
				list = super
						.findByHql(
								"select a.riskCode from PrpDrisk a where a.oldRiskCode = ?",
								riskCode);
			}
		}
		return list;
	}

	/**
	 * 批量数据字典代码进行旧代码转换
	 * 
	 * @param systemCode
	 * @param codeVolist
	 *            要翻译的代码对象
	 * @param reverseType
	 *            1： newCodeType,newCodeCode → oldCodeType,oldCodeCode 2：
	 *            oldCodeType,oldCodeCode → newCodeType,newCodeCode
	 * @return
	 */
	public List reverseCodeTyeAndCode(String systemCode, List codeVoList,
			String reverseType) throws Exception {
		StringBuffer hql = new StringBuffer();
		Map<String, String> map = new HashMap<String, String>();
		if (reverseType.equals("1")) {
			hql.append("select a.id.codeType,a.id.codeCode,oldCodeType,oldCodeCode from PrpDnewCode a where ");
		} else {
			hql.append("select a.id.codeType,a.id.codeCode,newCodeType,newCodeCode from PrpDCode a where ");
		}
		String[] typeAndCode = null;
		String codeCode = null;
		for (int i = 0; i < codeVoList.size(); i++) {
			typeAndCode = (String[]) codeVoList.get(i);
			codeCode = map.get(typeAndCode[0]);
			if (codeCode == null) {
				map.put(typeAndCode[0], typeAndCode[1]);
			} else {
				codeCode = map.get(typeAndCode[0]) + "," + typeAndCode[1];
				map.put(typeAndCode[0], codeCode);
			}
			typeAndCode = null;
			codeCode = null;
		}
		Object[] codeTypes = (Object[]) (map.keySet().toArray());
		for (int j = 0; j < codeTypes.length; j++) {
			String codeCodes = (String) map.get(codeTypes[j]);
			if (j > 0) {
				hql.append(" or ");
			}
			codeCodes = codeCodes.replaceAll(",", "','");
			hql.append("a.id.codeType = '").append(codeTypes[j])
					.append("' and a.id.codeCode in('").append(codeCodes)
					.append("')");
		}
		List list = super.findByHql(hql.toString());
		return list;
	}

	/**
	 * 批量代码翻译
	 * 
	 * @param systemCode
	 * @param voList
	 *            要翻译的代码对象
	 * @param transType
	 *            1： code → name 2： name → code
	 * @return
	 */
	public List codeTranslate(String systemCode, List<TranslateVO> voList,
			String transType) {
		String cname = null;
		String ename = null;
		if ("1".equals(transType)) {
			for (TranslateVO vo : voList) {
				cname = codeService.translateCode(systemCode, vo.getCodeType(),
						vo.getCodeCode(), vo.getCodeFlag(), "C");
				ename = codeService.translateCode(systemCode, vo.getCodeType(),
						vo.getCodeCode(), vo.getCodeFlag(), "E");
				if (!cname.equals(vo.getCodeCode()))
					vo.setCodeCName(cname);
				if (!ename.equals(vo.getCodeCode()))
					vo.setCodeEName(ename);
			}
		} else {
			for (TranslateVO vo : voList) {
				vo.setCodeCode(codeService.translateNameToCode(systemCode,
						vo.getCodeType(), vo.getCodeCName(), vo.getCodeEName()));
			}
		}
		return voList;
	}

	/**
	 * @param systemCode
	 * @param currencyCode
	 * @param currencyName
	 * @param validStatus
	 *            1:查询有效数据（默认） 0：查询无效数据 9：查询所有数据
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public DictPage getPrpDcurrency(String systemCode, String currencyCode,
			String currencyName, String validStatus, int pageNo, int pageSize)
			throws Exception {
		DictPage dictPage = new DictPage();
		StringBuffer hql = new StringBuffer(256);
		StringBuffer con = new StringBuffer(256);
		List list = new ArrayList();
		boolean hasFirstCon = false;
		hql.append("from PrpDcurrency a ");
		if (currencyCode != null || currencyName != null || validStatus != null) {
			hql.append("where (");
		}
		if (currencyCode != null && !"".equals(currencyCode)
				&& !"null".equalsIgnoreCase(currencyCode)) {
			hasFirstCon = true;
			con.append(" a.currencyCode like '");
			con.append(currencyCode);
			con.append("%'");
		}
		if (currencyName != null && !"".equals(currencyName)
				&& !"null".equalsIgnoreCase(currencyName)) {
			if (hasFirstCon) {
				con.append(" or ");
			} else {
				hasFirstCon = true;
			}
			con.append(" a.currencyCName like '");
			con.append(currencyName);
			con.append("%' or a.currencyEName like '");
			con.append(currencyName);
			con.append("%'");
		}
		if (hasFirstCon) {
			con.append(")");
		} else {
			con.append("1=1)");
			hasFirstCon = true;
		}
		
		if (!"9".equals(validStatus)) { //
	            if (hasFirstCon) {
	                con.append(" and ");
	            }
	            if ("0".equals(validStatus)) {
	                con.append(" a.validStatus = 0");
	            } else {
	                con.append(" a.validStatus = 1");
	            }
	    }
			
		hql.append(con);
		if (pageNo == 0 || pageSize == 0) {
			int count = getCount("dms", "PrpDcurrency", con.toString());
			if (count > 1000) {
				logger.error("系统" + systemCode + "查询记录数过大，不予返回！");
				throw new Exception("系统" + systemCode + "调用接口"
						+ ServiceInfoConst.GETPRPDCURRENCY + "查询记录数过大，请缩小查询范围");
			} else {
				list = super.findByHql(hql.toString());
			}
			dictPage.setData(list);
		} else {
			Page page = super.findByHql(hql.toString(), pageNo, pageSize);
			list = page.getResult();
			dictPage.setData(list);
			dictPage.setPageNo(pageNo);
			dictPage.setPageSize(pageSize);
			dictPage.setPageCount(page.getTotalPageCount());
			dictPage.setTotalRecordCount(page.getTotalCount());
		}
		return dictPage;
	}

	/**
	 * 巨灾查询接口，接口支持代码和名称的模糊查询，并支持分页 输入条件：传入参数disasterCodeOrName
	 * 可能情况：代码或名称,无%或*（由数据字典处理）,空值（查询出所有记录）。 返回list。
	 * 
	 * @throws Exception
	 */
	public DictPage getPrpDdisaster(String systemCode,
			String disasterCodeOrName, String validStatus, Date damageDate,
			int pageNO, int pageSize) throws Exception {
		DictPage dictPage = new DictPage();
		StringBuffer hql = new StringBuffer(256);
		StringBuffer con = new StringBuffer(256);
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		List list = new ArrayList();
		hql.append("from PrpDdisaster a where");
		con.append(" (a.disasterCode like '" + disasterCodeOrName
				+ "%' or a.disasterName like '" + disasterCodeOrName + "%')");
		con.append(" and validStatus = '");
		con.append(validStatus);
		con.append("'");
		if (damageDate != null && !"".equals(damageDate)) {
			con.append(" and ( '" + sf.format(damageDate)
					+ "' between a.startDate and a.endDate )");
			con.append(" and sysdate <= a.closeDate ");
		}
		hql.append(con);
		if (pageNO == 0 || pageSize == 0) {
			int count = getCount("dms", "PrpDdisaster", con.toString());
			if (count > 1000) {
				logger.error("系统" + systemCode + "查询记录数过大，不予返回！");
				throw new Exception("系统" + systemCode + "调用接口"
						+ ServiceInfoConst.PRPDDISASTER + "查询记录数过大，请缩小查询范围");
			} else {
				list = super.findByHql(hql.toString());
			}
			dictPage.setData(list);
		} else {
			Page page = super.findByHql(hql.toString(), pageNO, pageSize);
			list = page.getResult();
			dictPage.setData(list);
			dictPage.setPageNo(pageNO);
			dictPage.setPageSize(pageSize);
			dictPage.setPageCount(page.getTotalPageCount());
			dictPage.setTotalRecordCount(page.getTotalCount());
		}
		return dictPage;
	}

	public DictPage getPrpDtype(String systemCode, String codeType,
			String codeTypeName, String validStatus, int pageNO, int pageSize)
			throws Exception {

		DictPage dictPage = new DictPage();
		StringBuffer hql = new StringBuffer(256);
		StringBuffer con = new StringBuffer(256);
		List list = new ArrayList();
		hql.append("from PrpDtype a where ");
		con.append("1=1");
		if (codeType != null && !"".equals(codeType)) {
			con.append(" and a.codeType like '");
			con.append(codeType);
			con.append("%'");
		}
		if (!"".equals(codeTypeName) && codeTypeName != null) {
			con.append(" and a.codeTypeDesc like '");
			con.append(codeTypeName);
			con.append("%'");
		}
		if ("0".equals(validStatus)) {
			con.append(" and a.validStatus = '0'");
		}
		if (!"0".equals(validStatus) && !"9".equals(validStatus)) {
			con.append(" and a.validStatus = '1'");
		}
		hql.append(con);
		if (pageNO == 0 || pageSize == 0) {
			int count = getCount("dms", "PrpDtype", con.toString());
			if (count > 1000) {
				logger.error("系统" + systemCode + "查询记录数过大，不予返回！");
				throw new Exception("系统" + systemCode + "调用接口"
						+ ServiceInfoConst.PRPDTYPE + "查询记录数过大，请缩小查询范围");
			} else {
				list = super.findByHql(hql.toString());
			}
			dictPage.setData(list);
		} else {
			Page page = super.findByHql(hql.toString(), pageNO, pageSize);
			list = page.getResult();
			dictPage.setData(list);
			dictPage.setPageNo(pageNO);
			dictPage.setPageSize(pageSize);
			dictPage.setPageCount(page.getTotalPageCount());
			dictPage.setTotalRecordCount(page.getTotalCount());
		}
		return dictPage;
	}

	public DictPage getClass(String systemCode, String classCode,
			String reverseType, String validStatus, int pageNo, int pageSize)
			throws Exception {

		DictPage dictPage = new DictPage();
		StringBuffer hql = new StringBuffer(256);
		StringBuffer con = new StringBuffer(256);
		List list = new ArrayList();
		hql.append("from PrpDclass a where ");
		con.append("1=1");
		if (!"2".equals(reverseType)) {
			if (!"".equals(classCode) && classCode != null) {
				con.append(" and classCode like '");
				con.append(classCode);
				con.append("%'");
			}
			if (!"0".equals(validStatus) && !"9".equals(validStatus)) {
				con.append(" and validInd = '1'");
			} else if ("0".equals(validStatus)) {
				con.append(" and validInd = '0'");
			}
		} else {
			if (!"".equals(classCode) && classCode != null) {
				con.append(" and oldClassCode like '");
				con.append(classCode);
				con.append("%'");
			}
			if ("0".equals(validStatus)) {
				con.append(" and validInd = '0'");
			}
			if (!"0".equals(validStatus) && !"9".equals(validStatus)) {
				con.append(" and validInd = '1'");
			}

		}
		hql.append(con);
		if (pageNo == 0 || pageSize == 0) {
			int count = getCount("dms", "PrpDclass", con.toString());
			if (count > 1000) {
				logger.error("系统" + systemCode + "查询记录数过大，不予返回！");
				throw new Exception("系统" + systemCode + "调用接口"
						+ ServiceInfoConst.PRPDCLASS + "查询记录数过大，请缩小查询范围");
			} else {
				hql.append(" order by classCode ");
				list = super.findByHql(hql.toString());
			}
			dictPage.setData(list);
		} else {
			hql.append(" order by classCode ");
			Page page = super.findByHql(hql.toString(), pageNo, pageSize);
			list = page.getResult();
			dictPage.setData(list);
			dictPage.setPageNo(pageNo);
			dictPage.setPageSize(pageSize);
			dictPage.setPageCount(page.getTotalPageCount());
			dictPage.setTotalRecordCount(page.getTotalCount());
		}
		return dictPage;
	}

	public RationObj getRationInfo(String systemCode,
			cn.com.sinosoft.dms.model.PrpDration prpDration) throws Exception {
		String rationCode = prpDration.getRationCode();
		RationObj rationObj = new RationObj();
		// 如果rationCode不等于空，则返回指定rationCode及其所有rationLimit,rationClause,rationClauseKind
		if (rationCode != null && !"".equals(rationCode)) {
			StringBuffer planHql = new StringBuffer(
					"from PrpDration where rationCode = '" + rationCode
							+ "' and validInd = '1'");
			StringBuffer planLimitHql = new StringBuffer(
					"from PrpDrationLimit where rationCode = '" + rationCode
							+ "' and validInd = '1'");
			StringBuffer planClauseKindHql = new StringBuffer(
					"from PrpDrationClauseKind where rationCode = '"
							+ rationCode + "' and validInd = '1'");
			StringBuffer translateHql = new StringBuffer(
					"select 'ClauseCode',b.id.clauseCode,b.clauseCName from PrpDriskClause b where b.id.clauseCode in (select a.id.clauseCode from PrpDrationClauseKind a where a.id.rationCode = '")
					.append(rationCode)
					.append("' and a.id.clauseCode is not null) group by b.id.clauseCode,b.clauseCName union select 'KincCode',c.id.kindCode,c.kindName from PrpDriskClauseKind c where c.kindCode in (select a.kindCode from PrpDrationClauseKind a where a.id.rationCode = '")
					.append(rationCode)
					.append("' and a.kindCode is not null) group by c.kindCode,c.kindName union select 'ItemCode',d.id.itemCode,d.itemCName from PrpDriskItem d where d.id.itemCode in (select a.itemCode from PrpDrationClauseKind a where a.id.rationCode = '")
					.append(rationCode)
					.append("' and a.itemCode is not null) group by d.id.itemcode,d.itemcname");

			List planList = super.findByHql(planHql.toString());

			List planLimitList = super.findByHql(planLimitHql.toString());
			List planClauseKindList = super.findByHql(planClauseKindHql
					.toString());
			List translateList = super.findByHql(translateHql.toString());
			List translateObjList = new ArrayList();
			TranslateObj obj = null;
			Object[] tempObj = null;
			for (int i = 0; i < translateList.size(); i++) {
				tempObj = (Object[]) translateList.get(i);
				obj = new TranslateObj();
				obj.setCodeType(tempObj[0].toString());
				obj.setCodecode(tempObj[1].toString());
				obj.setCodename(tempObj[2].toString());
				translateObjList.add(obj);
			}
			rationObj.setPrpDration(planList);
			rationObj.setPrpDrationLimit(planLimitList);
			rationObj.setPrpDrationClauseKind(planClauseKindList);
			rationObj.setTranslateObj(translateObjList);
		}
		// 如果planCode为空，则以prpDplan中其他所有字段为条件查询PrpDplan对象
		else {
			boolean hasFirstCondition = false;
			StringBuffer hql = new StringBuffer(256);
			hql.append("from PrpDration");

			if (prpDration.getRationCName() != null
					&& !"".equals(prpDration.getRationCName())) {
				hql.append(hasFirstCondition ? " and" : " where");
				hql.append(" rationCName like '" + prpDration.getRationCName()
						+ "%'");
				hasFirstCondition = true;
			}
			if (prpDration.getRationTName() != null
					&& !"".equals(prpDration.getRationTName())) {
				hql.append(hasFirstCondition ? " and" : " where");
				hql.append(" rationTName like '" + prpDration.getRationTName()
						+ "%'");
				hasFirstCondition = true;
			}
			if (prpDration.getRationSName() != null
					&& !"".equals(prpDration.getRationSName())) {
				hql.append(hasFirstCondition ? " and" : " where");
				hql.append(" rationSName like '" + prpDration.getRationSName()
						+ "%'");
				hasFirstCondition = true;
			}
			if (prpDration.getRationEName() != null
					&& !"".equals(prpDration.getRationEName())) {
				hql.append(hasFirstCondition ? " and" : " where");
				hql.append(" rationEName like '" + prpDration.getRationEName()
						+ "%'");
				hasFirstCondition = true;
			}
			if (prpDration.getIsFixedFlag() != null
					&& !"".equals(prpDration.getIsFixedFlag())) {
				hql.append(hasFirstCondition ? " and" : " where");
				hql.append(" isFixedFlag like '" + prpDration.getIsFixedFlag()
						+ "%'");
				hasFirstCondition = true;
			}
			if (prpDration.getFrameCode() != null
					&& !"".equals(prpDration.getFrameCode())) {
				hql.append(hasFirstCondition ? " and" : " where");
				hql.append(" frameCode like '" + prpDration.getFrameCode()
						+ "%'");
				hasFirstCondition = true;
			}
			// if (prpDration.getFrameCName() != null &&
			// !"".equals(prpDration.getFrameCName())) {
			// hql.append(hasFirstCondition ? " and" : " where");
			// hql.append(" frameCName like '" + prpDration.getFrameCName() +
			// "%'");
			// hasFirstCondition = true;
			// }

			/*if (prpDration.getPlanCode() != null
					&& !"".equals(prpDration.getPlanCode())) {
				hql.append(hasFirstCondition ? " and" : " where");
				hql.append(" planCode like '" + prpDration.getPlanCode() + "%'");
				hasFirstCondition = true;
			}*/
			// if (prpDration.getRiskCName() != null &&
			// !"".equals(prpDration.getRiskCName())) {
			// hql.append(hasFirstCondition ? " and" : " where");
			// hql.append(" riskCName like '" + prpDration.getRiskCName() +
			// "%'");
			// hasFirstCondition = true;
			// }
			if (prpDration.getPlanAttribute() != null
					&& !"".equals(prpDration.getPlanAttribute())) {
				hql.append(hasFirstCondition ? " and" : " where");
				hql.append(" planAttribute like '"
						+ prpDration.getPlanAttribute() + "%'");
				hasFirstCondition = true;
			}
			if (prpDration.getAreaLevel() != null
					&& !"".equals(prpDration.getAreaLevel())) {
				hql.append(hasFirstCondition ? " and" : " where");
				hql.append(" areaLevel like '" + prpDration.getAreaLevel()
						+ "%'");
				hasFirstCondition = true;
			}
			if (prpDration.getAreaCode() != null
					&& !"".equals(prpDration.getAreaCode())) {
				hql.append(hasFirstCondition ? " and" : " where");
				hql.append(" areaCode like '" + prpDration.getAreaCode() + "%'");
				hasFirstCondition = true;
			}
			if (prpDration.getAreaName() != null
					&& !"".equals(prpDration.getAreaName())) {
				hql.append(hasFirstCondition ? " and" : " where");
				hql.append(" areaName like '" + prpDration.getAreaName() + "%'");
				hasFirstCondition = true;
			}
			if (prpDration.getCreaterCode() != null
					&& !"".equals(prpDration.getCreaterCode())) {
				hql.append(hasFirstCondition ? " and" : " where");
				hql.append(" createrCode like '" + prpDration.getCreaterCode()
						+ "%'");
				hasFirstCondition = true;
			}
			if (prpDration.getCreateTime() != null
					&& !"".equals(prpDration.getCreateTime())) {
				hql.append(hasFirstCondition ? " and" : " where");
				hql.append(" createTime like '" + prpDration.getCreateTime()
						+ "%'");
				hasFirstCondition = true;
			}
			if (prpDration.getUpdaterCode() != null
					&& !"".equals(prpDration.getUpdaterCode())) {
				hql.append(hasFirstCondition ? " and" : " where");
				hql.append(" updaterCode like '" + prpDration.getUpdaterCode()
						+ "%'");
				hasFirstCondition = true;
			}
			if (prpDration.getUpdateTime() != null
					&& !"".equals(prpDration.getUpdateTime())) {
				hql.append(hasFirstCondition ? " and" : " where");
				hql.append(" updateTime like '" + prpDration.getUpdateTime()
						+ "%'");
				hasFirstCondition = true;
			}
			// if (prpDrationgetContentNumber() != null &&
			// !"".equals(prpDration.getContentNumber())) {
			// hql.append(hasFirstCondition ? " and" : " where");
			// hql.append(" contentNumber like '" + prpDplan.getContentNumber()
			// + "%'");
			// hasFirstCondition = true;
			// }
			if (prpDration.getValidDate() != null
					&& !"".equals(prpDration.getValidDate())) {
				hql.append(hasFirstCondition ? " and" : " where");
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				hql.append(" validDate = '"
						+ format.format(prpDration.getValidDate()) + "'");
				hasFirstCondition = true;
			}
			if (prpDration.getInvalidDate() != null
					&& !"".equals(prpDration.getInvalidDate())) {
				hql.append(hasFirstCondition ? " and" : " where");
				hql.append(" invalidDate like '" + prpDration.getInvalidDate()
						+ "%'");
				hasFirstCondition = true;
			}
			if (prpDration.getValidInd() != null
					&& !"".equals(prpDration.getValidInd())) {
				hql.append(hasFirstCondition ? " and" : " where");
				hql.append(" validInd = '" + prpDration.getValidInd() + "'");
				hasFirstCondition = true;
			}
			if (prpDration.getRemark() != null
					&& !"".equals(prpDration.getRemark())) {
				hql.append(hasFirstCondition ? " and" : " where");
				hql.append(" remark like '" + prpDration.getRemark() + "%'");
				hasFirstCondition = true;
			}
			if (prpDration.getFlag() != null
					&& !"".equals(prpDration.getFlag())) {
				hql.append(hasFirstCondition ? " and" : " where");
				hql.append(" flag like '" + prpDration.getFlag() + "%'");
				hasFirstCondition = true;
			}
			List planList = super.findByHql(hql.toString());
			rationObj.setPrpDration(planList);
		}
		// liufei begin
		RationObj rationObjD = new RationObj();
		BoCopyUtil.convert(rationObj, rationObjD, null, null, null);
		rationObj = rationObjD;
		// liufei end
		return rationObj;
	}

	/**
	 * 
	 * @desc 方案新方法，增加prpdrationPeriodrate 保费/费率区间表 prpdChannelinfo --个性信息表 等表信息
	 * @author wpf
	 * @date 2012-3-29
	 * @param prpDration
	 * @return
	 */
	/*
	 * @Override delete by sunJiuhua 20120718 原因：改为使用主子表关系的下面的方法 begin public
	 * RationObj getRationInfoNew(String systemCode, PrpDration prpDration,
	 * String agentCode, String comCode, String startDate, String startHour,
	 * String endDate, String endHour, String policyWayCode) throws Exception {
	 * String rationCode = prpDration.getRationCode(); RationObj rationObj = new
	 * RationObj();
	 * 
	 * //
	 * 如果rationCode不等于空，则返回指定rationCode及其所有rationLimit,rationClause,rationClauseKind
	 * if (rationCode != null && !"".equals(rationCode)) { StringBuffer planHql
	 * = new StringBuffer( "from PrpDration where rationCode = '" + rationCode +
	 * "' and validInd = '1'"); if
	 * (StringUtils.isNotBlank(prpDration.getTcol1())) {
	 * planHql.append(" and tcol1='" + prpDration.getTcol1() + "'"); } if
	 * (StringUtils.isNotBlank(prpDration.getBusinessFlag())) {
	 * planHql.append("and businessFlag like '%" + prpDration.getBusinessFlag()
	 * + "%'"); } StringBuffer planLimitHql = new StringBuffer(
	 * "from PrpDrationLimit where rationCode = '" + rationCode +
	 * "' and validInd = '1'"); StringBuffer planClauseKindHql = new
	 * StringBuffer( "from PrpDrationClauseKind where rationCode = '" +
	 * rationCode + "' and validInd = '1'"); StringBuffer translateHql = new
	 * StringBuffer(
	 * "select 'ClauseCode',b.id.clauseCode,b.clauseCName from PrpDriskClause b "
	 * +
	 * "where b.id.clauseCode in (select a.id.clauseCode from PrpDrationClauseKind a "
	 * + "where a.id.rationCode = '") .append(rationCode) .append(
	 * "' and a.id.clauseCode is not null) group by b.id.clauseCode,b.clauseCName "
	 * +
	 * "union select 'KincCode',c.id.kindCode,c.kindName from PrpDriskClauseKind c "
	 * + "where c.kindCode in (select a.kindCode from PrpDrationClauseKind a " +
	 * "where a.id.rationCode = '") .append(rationCode)
	 * .append("' and a.kindCode is not null) group by c.kindCode,c.kindName " +
	 * "union select 'ItemCode',d.id.itemCode,d.itemCName from PrpDriskItem d "
	 * +
	 * "where d.id.itemCode in (select a.itemCode from PrpDrationClauseKind a "
	 * + "where a.id.rationCode = '") .append(rationCode)
	 * .append("' and a.itemCode is not null) group by d.id.itemcode,d.itemcname"
	 * ); StringBuffer prpDrationEngageHql = new StringBuffer(
	 * "from PrpDRationEngage pre where pre.id.rationCode ='" + rationCode +
	 * "' ");
	 * 
	 * String[] comCodes = null; StringBuffer sqlTemp = new StringBuffer("");
	 * StringBuffer pccSqlTemp = new StringBuffer(""); if (comCode != null &&
	 * !"".equals(comCode)) { PrpDcompany prpDcompany =
	 * prpDcompanyService.getPrpDcompany1(comCode); comCodes =
	 * prpDcompany.getUpperPath().split(","); sqlTemp =
	 * sqlTemp.append(" and ("); pccSqlTemp = pccSqlTemp.append(" and(");
	 * for(int i=0; i<comCodes.length; i++) { if(i == comCodes.length - 1) {
	 * sqlTemp = sqlTemp.append("planc.areaCode like '%" + comCodes[i] +
	 * "%') "); pccSqlTemp = pccSqlTemp.append("pcc.comCode ='" + comCodes[i] +
	 * "') "); } else { sqlTemp = sqlTemp.append("planc.areaCode like '%" +
	 * comCodes[i] + "%' or "); pccSqlTemp = pccSqlTemp.append("pcc.comCode ='"
	 * + comCodes[i] + "' or "); } } } if
	 * (StringUtils.isNotBlank(prpDration.getBusinessFlag())) {
	 * sqlTemp.append(" and planc.subChannelCode ='" +
	 * prpDration.getBusinessFlag() + "' "); }
	 * 
	 * StringBuffer channelinfoHql = new StringBuffer(
	 * "from PrpdChannelInfo planc where planc.id.rationCode = '" + rationCode +
	 * "'" + sqlTemp); StringBuffer PrpDRationPeriodRateHql = new StringBuffer(
	 * "from PrpDRationPeriodRate prpr where prpr.id.rationCode = '" +
	 * rationCode + "'"); StringBuffer PrpdChannelCoinsHql = new StringBuffer(
	 * "from PrpdChannelCoins pcc where pcc.id.rationCode = '" + rationCode +
	 * "'" + pccSqlTemp); StringBuffer PrpDChannelRationEngageHql = new
	 * StringBuffer(
	 * "from PrpDChannelRationEngage pcre where pcre.id.rationCode = '" +
	 * rationCode + "'"); StringBuffer PrpDChannelRationClauseKindHql = new
	 * StringBuffer(
	 * "from PrpDChannelRationClauseKind pcrck where pcrck.id.rationCode = '" +
	 * rationCode + "' and validInd ='1' "); // StringBuffer
	 * PrpDChannelRationPeriodRateHql = new // StringBuffer("from
	 * PrpDChannelRationPeriodRate pcrpr where // pcrpr.id.rationCode =
	 * '"+rationCode+"'");
	 * 
	 * List planList = super.findByHql(planHql.toString());
	 * 
	 * List planLimitList = super.findByHql(planLimitHql.toString()); List
	 * planClauseKindList = super.findByHql(planClauseKindHql .toString());
	 * 
	 * List translateList = super.findByHql(translateHql.toString()); List
	 * preList = super.findByHql(prpDrationEngageHql.toString());
	 * 
	 * List channelinfoList = super.findByHql(channelinfoHql.toString());
	 * 
	 * List translateObjList = new ArrayList(); TranslateObj obj = null;
	 * Object[] tempObj = null; for (int i = 0; i < translateList.size(); i++) {
	 * tempObj = (Object[]) translateList.get(i); obj = new TranslateObj();
	 * obj.setCodeType(tempObj[0].toString());
	 * obj.setCodecode(tempObj[1].toString());
	 * obj.setCodename(tempObj[2].toString()); translateObjList.add(obj); } if
	 * (channelinfoList != null && channelinfoList.size() > 0) { PrpDration p =
	 * (PrpDration) planList.get(0); PrpDration temp = new PrpDration();
	 * BoCopyUtil.convert(p, temp, null, null, null); p = temp; }
	 * rationObj.setPrpDration(planList);
	 * rationObj.setPrpDrationLimit(planLimitList);
	 * rationObj.setPrpDrationClauseKind(planClauseKindList);
	 * rationObj.setPrpDrationEngage(preList);
	 * rationObj.setTranslateObj(translateObjList); //
	 * rationObj.setPrpDRationPeriodRate(prprlist);
	 * 
	 * if (channelinfoList != null && channelinfoList.size() > 0) {
	 * PrpdChannelInfo prpdChannelInfo = (PrpdChannelInfo) channelinfoList
	 * .get(0);
	 * 
	 * // 设置个性方案名称 ((PrpDration) planList.get(0)).setRationCName(prpdChannelInfo
	 * .getRationcName());
	 * 
	 * List pccList = prpdChannelInfo.getPrpdChannelCoins(); List pcreList =
	 * prpdChannelInfo.getPrpDChannelRationEngage(); List pcrckList =
	 * prpdChannelInfo .getPrpDChannelRationClauseKind(); List<PrpdChannelInfo>
	 * channelinfoTempList = new ArrayList<PrpdChannelInfo>( 0); for (int i = 0;
	 * i < channelinfoList.size(); i++) { // PrpdChannelInfo prpdChannelInfo =
	 * new PrpdChannelInfo(); PrpdChannelInfo info = (PrpdChannelInfo)
	 * channelinfoList .get(i); info.setPrpdChannelCoins(new ArrayList());
	 * info.setPrpDChannelRationClauseKind(new ArrayList());
	 * info.setPrpDChannelRationEngage(new ArrayList());
	 * channelinfoTempList.add(info); }
	 * rationObj.setPrpdChannelInfo(channelinfoTempList);
	 * 
	 * // 联共保信息表 有值 返回 if (pccList != null && pccList.size() > 0) {
	 * List<PrpdChannelCoins> pccTemList = new ArrayList<PrpdChannelCoins>( 0);
	 * for (int i = 0; i < pccList.size(); i++) { PrpdChannelCoins
	 * prpdChannelCoins = new PrpdChannelCoins(); PrpdChannelCoins p =
	 * (PrpdChannelCoins) pccList.get(i); BoCopyUtil.convert(p,
	 * prpdChannelCoins, null, null, null); pccTemList.add(prpdChannelCoins); }
	 * rationObj.setPrpdChannelCoins(pccTemList); }
	 * 
	 * // 个性信息特别约定表 有值 替换 prpdrationengage特别约定表 中数据 返回 if (pcreList != null &&
	 * pcreList.size() > 0) { List<PrpDRationEngage> preTemList = new
	 * ArrayList<PrpDRationEngage>( 0); for (int i = 0; i < pcreList.size();
	 * i++) { PrpDRationEngage prpDRationEngage = new PrpDRationEngage();
	 * PrpDChannelRationEngage prpDChannelRationEngage =
	 * (PrpDChannelRationEngage) pcreList .get(i);
	 * BoCopyUtil.convert(prpDChannelRationEngage, prpDRationEngage, null, null,
	 * null); PrpDRationEngageId prpDRationEngageId = prpDRationEngage .getId();
	 * prpDRationEngageId.setRiskCode(prpDChannelRationEngage .getRiskCode());
	 * prpDRationEngageId .setEngageCode(prpDChannelRationEngage
	 * .getEngageCode()); preTemList.add(prpDRationEngage); }
	 * 
	 * rationObj.setPrpDrationEngage(preTemList); } // 个性条款责任表 有值 替换
	 * prpdrationClausekind条款责任表 中数据 返回 if (pcrckList != null &&
	 * pcrckList.size() > 0) { List<PrpDrationClauseKind> list =
	 * this.jsbf(pcrckList, rationCode, comCodes, startDate, startHour, endDate,
	 * endHour, "1"); rationObj.setPrpDrationClauseKind(list); } } else { List
	 * pckList = this.jsbf(planClauseKindList, rationCode, comCodes, startDate,
	 * startHour, endDate, endHour, "2");
	 * rationObj.setPrpDrationClauseKind(pckList); }
	 * 
	 * //liufei begin RationObj rationObjD = new RationObj();
	 * BoCopyUtil.convert(rationObj, rationObjD, this, null, null); rationObj =
	 * rationObjD; //liufei end } return rationObj; }delete by sunJiuhua
	 * 20120718 原因：改为使用主子表关系的下面的方法 end
	 */

	/**
	 * 
	 * @desc 方案新方法，增加prpdrationPeriodrate 保费/费率区间表 prpdChannelinfo --个性信息表 等表信息
	 * @author sunJiuhua
	 * @date 2012-7-18
	 * @param prpDration
	 * @return
	 */
	@Override
	public RationObj getRationInfoNew(String systemCode, PrpDration prpDration,
			String agentCode, String comCode, String startDate,
			String startHour, String endDate, String endHour,
			String policyWayCode) throws Exception {
		String rationCode = prpDration.getRationCode();
		RationObj rationObj = new RationObj();

		// 如果rationCode不等于空，则返回指定rationCode及其所有rationLimit,rationClause,rationClauseKind
		if (rationCode != null && !"".equals(rationCode)) {
			StringBuffer planHql = new StringBuffer(
					"from PrpDration where rationCode = '" + rationCode
							+ "' and validInd = '1'");
			if (StringUtils.isNotBlank(prpDration.getTcol1())) {
				planHql.append(" and tcol1='" + prpDration.getTcol1() + "'");
			}
			if (StringUtils.isNotBlank(prpDration.getBusinessFlag())) {
				planHql.append("and businessFlag like '%"
						+ prpDration.getBusinessFlag() + "%'");
			}

			StringBuffer translateHql = new StringBuffer(
					"select 'ClauseCode',b.id.clauseCode,b.clauseCName from PrpDriskClause b "
							+ "where b.id.clauseCode in (select a.id.clauseCode from PrpDrationClauseKind a "
							+ "where a.id.rationCode = '")
					.append(rationCode)
					.append("' and a.id.clauseCode is not null) group by b.id.clauseCode,b.clauseCName "
							+ "union select 'KincCode',c.id.kindCode,c.kindName from PrpDriskClauseKind c "
							+ "where c.kindCode in (select a.kindCode from PrpDrationClauseKind a "
							+ "where a.id.rationCode = '")
					.append(rationCode)
					.append("' and a.kindCode is not null) group by c.kindCode,c.kindName "
							+ "union select 'ItemCode',d.id.itemCode,d.itemCName from PrpDriskItem d "
							+ "where d.id.itemCode in (select a.itemCode from PrpDrationClauseKind a "
							+ "where a.id.rationCode = '")
					.append(rationCode)
					.append("' and a.itemCode is not null) group by d.id.itemcode,d.itemcname");

			String[] comCodes = null;
			if (comCode != null && !"".equals(comCode)) {
				PrpDcompany prpDcompany = prpDcompanyService
						.getPrpDcompany1(comCode);
				comCodes = prpDcompany.getUpperPath().split(",");
			}

			List planList = super.findByHql(planHql.toString());
			if (planList == null || planList.size() == 0) {
				return null;
			}

			List translateList = super.findByHql(translateHql.toString());

			List translateObjList = new ArrayList();
			TranslateObj obj = null;
			Object[] tempObj = null;
			for (int i = 0; i < translateList.size(); i++) {
				tempObj = (Object[]) translateList.get(i);
				obj = new TranslateObj();
				obj.setCodeType(tempObj[0].toString());
				obj.setCodecode(tempObj[1].toString());
				obj.setCodename(tempObj[2].toString());
				translateObjList.add(obj);
			}
			PrpDration temp = (PrpDration) planList.get(0);
			PrpDration ration = new PrpDration();
			BoCopyUtil.convert(temp, ration, null, null, null);
			rationObj.getPrpDration().add(ration);
			rationObj.setPrpDrationLimit(ration.getPrpDrationLimits());
			rationObj
					.setPrpDrationClauseKind(ration.getPrpDrationClauseKinds());
			rationObj.setPrpDrationEngage(ration.getPrpDRationEngages());
			rationObj.setTranslateObj(translateObjList);

			boolean inArea = false;
			if (ration.getPrpdChannelInfos() != null
					&& ration.getPrpdChannelInfos().size() > 0) {
				for (int i = 0; i < ration.getPrpdChannelInfos().size(); i++) {
					PrpdChannelInfo prpdChannelInfo = ration
							.getPrpdChannelInfos().get(i);
					if (!prpdChannelInfo.getSubChannelCode().equals(
							prpDration.getBusinessFlag())) {
						continue;
					}
					for (int t = 0; t < comCodes.length; t++) {
						if (prpdChannelInfo.getAreaCode().indexOf(comCodes[t]) > -1) {
							inArea = true;
							break;
						}
					}
					if (!inArea) {
						continue;
					}
					// 设置个性方案名称
					ration.setRationCName(prpdChannelInfo.getRationcName());
					// 联共保信息表 有值 返回
					rationObj.setPrpdChannelCoins(prpdChannelInfo
							.getPrpdChannelCoins());
					// 个性信息特别约定表 有值 替换 prpdrationengage特别约定表 中数据 返回
					if (prpdChannelInfo.getPrpDChannelRationEngage() != null
							&& prpdChannelInfo.getPrpDChannelRationEngage()
									.size() > 0) {
						List<PrpDRationEngage> preTemList = new ArrayList<PrpDRationEngage>(
								0);
						for (int j = 0; j < prpdChannelInfo
								.getPrpDChannelRationEngage().size(); j++) {
							PrpDRationEngage prpDRationEngage = new PrpDRationEngage();
							PrpDChannelRationEngage prpDChannelRationEngage = prpdChannelInfo
									.getPrpDChannelRationEngage().get(j);
							BoCopyUtil.convert(prpDChannelRationEngage,
									prpDRationEngage, null, null, null);
							PrpDRationEngageId prpDRationEngageId = prpDRationEngage
									.getId();
							prpDRationEngageId
									.setRiskCode(prpDChannelRationEngage
											.getRiskCode());
							prpDRationEngageId
									.setEngageCode(prpDChannelRationEngage
											.getEngageCode());
							preTemList.add(prpDRationEngage);
						}

						rationObj.setPrpDrationEngage(preTemList);
					}
					// 个性条款责任表 有值 替换 prpdrationClausekind条款责任表 中数据 返回
					if (prpdChannelInfo.getPrpDChannelRationClauseKind() != null
							&& prpdChannelInfo.getPrpDChannelRationClauseKind()
									.size() > 0) {
						List<PrpDrationClauseKind> list = this.jsbf(
								prpdChannelInfo
										.getPrpDChannelRationClauseKind(),
								rationCode, comCodes, startDate, startHour,
								endDate, endHour, "1");
						rationObj.setPrpDrationClauseKind(list);
					}
					List<PrpdChannelInfo> channelinfoTempList = new ArrayList<PrpdChannelInfo>(
							0);
					prpdChannelInfo.setPrpdChannelCoins(new ArrayList());
					prpdChannelInfo
							.setPrpDChannelRationClauseKind(new ArrayList());
					prpdChannelInfo.setPrpDChannelRationEngage(new ArrayList());
					channelinfoTempList.add(prpdChannelInfo);
					rationObj.setPrpdChannelInfo(channelinfoTempList);
					break;
				}
			}
			if (!inArea) {
				List pckList = this.jsbf(ration.getPrpDrationClauseKinds(),
						rationCode, comCodes, startDate, startHour, endDate,
						endHour, "2");
				rationObj.setPrpDrationClauseKind(pckList);
			}
		}
		return rationObj;
	}

	/*
	 * bankCode 和 bankName 为与的关系，
	 */
	public DictPage getBank(String systemCode, String bankCode,
			String bankName, int pageNo, int pageSize) throws Exception {
		boolean hasFirstCon = false;
		DictPage dictPage = new DictPage();
		StringBuffer condition = new StringBuffer(128);
		StringBuffer hql = new StringBuffer(128);
		hql.append("from PrpDbank a ");
		List list = new ArrayList();
		if (bankCode != null && !"".equals(bankCode)) {
			condition.append(hasFirstCon ? " and " : " where ");
			hasFirstCon = true;
			condition.append(" a.bankCode like '").append(bankCode)
					.append("%'");
		}
		if (bankName != null && !"".equals(bankName)) {
			condition.append(hasFirstCon ? " and " : " where ");
			hasFirstCon = true;
			condition.append(" a.bankName like '").append(bankName)
					.append("%'");
		}

		condition.append(hasFirstCon ? " and " : " where ");
		hasFirstCon = true;
		condition.append(" a.validStatus = 1");

		hql.append(condition);
		if (pageNo == 0 || pageSize == 0) {
			int count = getCount(systemCode, "PrpDbank", condition.toString());
			if (count > 1000) {
				throw new Exception("系统" + systemCode + "调用接口"
						+ ServiceInfoConst.GETBANK + "查询记录数过大，请缩小查询范围");
			} else {
				list = super.findByHql(hql.toString());
			}
		} else {
			Page page = super.findByHql(hql.toString(), pageNo, pageSize);
			list = page.getResult();
			dictPage.setPageNo(pageNo);
			dictPage.setPageSize(pageSize);
			dictPage.setPageCount(page.getTotalPageCount());
			dictPage.setTotalRecordCount(page.getTotalCount());
		}
		dictPage.setData(list);
		return dictPage;
	}

	/** 2010-05-12 by wanghaibo */
	public DictPage getPrpDcrossOrg(String systemCode, String orgcod,
			String comp_cod, String org_lvl, int pageNO, int pageSize)
			throws Exception {
		DictPage dictPage = new DictPage();
		StringBuffer hql = new StringBuffer(256);
		StringBuffer con = new StringBuffer(256);
		List list = new ArrayList();
		hql.append("from PrpDcrossOrg a where ");
		con.append("1=1");
		if (orgcod != null && !"".equals(orgcod)) {
			con.append(" and a.id.orgCod = '");
			con.append(orgcod);
			con.append("'");
		}
		if (comp_cod != null && !"".equals(comp_cod)) {
			con.append(" and a.id.compCod = '");
			con.append(comp_cod);
			con.append("'");
		}
		if (org_lvl != null && !"".equals(org_lvl)) {
			con.append(" and a.orgLvl in (");
			con.append(org_lvl);
			con.append(")");
		}
		hql.append(con);
		if (pageNO == 0 || pageSize == 0) {
			int count = getCount("dms", "PrpDcrossOrg", con.toString());
			if (count > 1000) {
				logger.error("系统" + systemCode + "查询记录数过大，不予返回！");
				throw new Exception("系统" + systemCode + "调用接口"
						+ ServiceInfoConst.GETPRPDCROSSORG + "查询记录数过大，请缩小查询范围");
			} else {
				list = super.findByHql(hql.toString());
			}
			dictPage.setData(list);
		} else {
			Page page = super.findByHql(hql.toString(), pageNO, pageSize);
			list = page.getResult();
			dictPage.setData(list);
			dictPage.setPageNo(pageNO);
			dictPage.setPageSize(pageSize);
			dictPage.setPageCount(page.getTotalPageCount());
			dictPage.setTotalRecordCount(page.getTotalCount());
		}
		return dictPage;
	}

	/**
	 * 获取产品的标的信息
	 * 
	 * @param systemCode
	 * @param riskCode
	 *            产品代码
	 * @param upperItemCode
	 *            标的上级代码
	 * @param itemCode
	 *            标的代码
	 * @param clauseCode
	 *            条款代码
	 * @param extraItemCode
	 *            新添加extraItemCode参数,用来查询原逻辑有效的数据 + 查询extraItemCode不管是否有效的数据
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public DictPage getPrpDriskItem(String systemCode, String riskCode,
			String itemCode, String upperItemCode, String clauseCode,
			String extraItemCode, int pageNo, int pageSize) throws Exception {
		boolean hasFirstCon = false;
		DictPage dictPage = new DictPage();
		StringBuffer condition = new StringBuffer(256);
		StringBuffer hql = new StringBuffer(256);
		List list = new ArrayList();
		hql.append("from PrpDriskItem a");
		if (riskCode != null && !"".equals(riskCode)) {
			condition.append(hasFirstCon ? " and " : " where ");
			hasFirstCon = true;
			condition.append(" a.id.riskCode = '").append(riskCode).append("'");
		}
		if (itemCode != null && !"".equals(itemCode)) {
			condition.append(hasFirstCon ? " and " : " where ");
			hasFirstCon = true;
			condition.append(" (a.id.itemCode like '").append(itemCode)
					.append("%'");
			condition.append(" or a.itemCName like '").append(itemCode)
					.append("%')");
		} else {
			if ("PUB".equals(upperItemCode)) {

			} else if (upperItemCode == null || "".equals(upperItemCode)) {
				condition.append(hasFirstCon ? " and " : " where ");
				hasFirstCon = true;
				condition
						.append(" (a.upperItemCode is null or a.upperItemCode = '')");
			} else {
				condition.append(hasFirstCon ? " and " : " where ");
				hasFirstCon = true;
				condition.append(" a.upperItemCode = '").append(upperItemCode)
						.append("'");
			}
		}
		if (clauseCode != null && !"".equals(clauseCode)) {
			condition.append(hasFirstCon ? " and " : " where ");
			hasFirstCon = true;
			condition.append(" a.id.clauseCode = '").append(clauseCode)
					.append("'");
		}
		condition.append(" and a.validInd = 1 ");
		if (extraItemCode != null && !"".equals(extraItemCode)) {
			String extraItemCodes = extraItemCode.replace(",", "','");
			condition.append(" or ( a.id.itemCode in('").append(extraItemCodes)
					.append("')");
			if (riskCode != null && !"".equals(riskCode)) {
				condition.append(hasFirstCon ? " and " : " where ");
				hasFirstCon = true;
				condition.append(" a.id.riskCode = '").append(riskCode)
						.append("'");
			}
			if (itemCode != null && !"".equals(itemCode)) {
				condition.append(hasFirstCon ? " and " : " where ");
				hasFirstCon = true;
				condition.append(" (a.id.itemCode like '").append(itemCode)
						.append("%'");
				condition.append(" or a.itemCName like '").append(itemCode)
						.append("%')");
			} else {
				if (upperItemCode == null || "".equals(upperItemCode)) {
					condition.append(hasFirstCon ? " and " : " where ");
					hasFirstCon = true;
					condition
							.append(" (a.upperItemCode is null or a.upperItemCode = '')");
				} else {
					condition.append(hasFirstCon ? " and " : " where ");
					hasFirstCon = true;
					condition.append(" a.upperItemCode = '")
							.append(upperItemCode).append("'");
				}
			}
			if (clauseCode != null && !"".equals(clauseCode)) {
				condition.append(hasFirstCon ? " and " : " where ");
				hasFirstCon = true;
				condition.append(" a.id.clauseCode = '").append(clauseCode)
						.append("'");
			}
			condition.append(")");
		}
		hql.append(condition);
		if (pageNo == 0 || pageSize == 0) {
			list = super.findByHql(hql.toString());
		} else {
			Page page = super.findByHql(hql.toString(), pageNo, pageSize);
			list = page.getResult();
			dictPage.setData(list);
			dictPage.setPageNo(pageNo);
			dictPage.setPageSize(pageSize);
			dictPage.setPageCount(page.getTotalPageCount());
			dictPage.setTotalRecordCount(page.getTotalCount());
		}
		dictPage.setData(list);
		return dictPage;
	}

	public DictPage getPrpDports(String systemCode, String portCode,
			int pageNo, int pageSize) throws Exception {
		boolean hasFirstCon = false;
		DictPage dictPage = new DictPage();
		StringBuffer condition = new StringBuffer(32);
		StringBuffer hql = new StringBuffer(256);
		List list = new ArrayList();
		hql.append("from PrpDport a");
		if (portCode != null && !"".equals(portCode)) {
			condition.append(hasFirstCon ? " and " : " where ");
			hasFirstCon = true;
			condition.append(" a.id.portCode = '").append(portCode).append("'");
		}

		hql.append(condition);
		if (pageNo == 0 || pageSize == 0) {
			int count = getCount(systemCode, "PrpDport", condition.toString());
			if (count > 1000) {
				logger.error("系统" + systemCode + "调用接口"
						+ ServiceInfoConst.GETPRPDPORTS + "查询记录数过大，请缩小查询范围");
				throw new Exception("系统" + systemCode + "调用接口"
						+ ServiceInfoConst.GETPRPDPORTS + "查询记录数过大，请缩小查询范围");
			} else {
				list = super.findByHql(hql.toString());
			}
		} else {
			Page page = super.findByHql(hql.toString(), pageNo, pageSize);
			list = page.getResult();
			dictPage.setData(list);
			dictPage.setPageNo(pageNo);
			dictPage.setPageSize(pageSize);
			dictPage.setPageCount(page.getTotalPageCount());
			dictPage.setTotalRecordCount(page.getTotalCount());
		}
		dictPage.setData(list);
		return dictPage;
	}

	/** 2010-07-19 by wanghaibo */
	public DictPage getContractManage(String systemCode,
			String contractObjectCode, String validStatus, int pageNO,
			int pageSize) throws Exception {
		DictPage dictPage = new DictPage();
		StringBuffer hql = new StringBuffer(256);
		StringBuffer con = new StringBuffer(256);
		List list = new ArrayList();
		hql.append("from PrpDcontractManage a where ");
		con.append("1=1");
		if (contractObjectCode != null && !"".equals(contractObjectCode)) {
			con.append(" and a.id.contractObjectCode = '");
			con.append(contractObjectCode);
			con.append("'");
		}
		if (validStatus != null && !"".equals(validStatus)) {
			con.append(" and a.validStatus = '");
			con.append(validStatus);
			con.append("'");
		}
		hql.append(con);
		if (pageNO == 0 || pageSize == 0) {
			int count = getCount("dms", "PrpDcontractManage", con.toString());
			if (count > 1000) {
				logger.error("系统" + systemCode + "查询记录数过大，不予返回！");
				throw new Exception("系统" + systemCode + "调用接口"
						+ ServiceInfoConst.GETCONTRACTMANAGE
						+ "查询记录数过大，请缩小查询范围");
			} else {
				list = super.findByHql(hql.toString());
			}
			dictPage.setData(list);
		} else {
			Page page = super.findByHql(hql.toString(), pageNO, pageSize);
			list = page.getResult();
			dictPage.setData(list);
			dictPage.setPageNo(pageNO);
			dictPage.setPageSize(pageSize);
			dictPage.setPageCount(page.getTotalPageCount());
			dictPage.setTotalRecordCount(page.getTotalCount());
		}
		return dictPage;
	}

	/** 2010-07-21 by wanghaibo */
	public DictPage getPlan(String systemCode, String rationCode,
			String riskCode, String[] comCodes, String rationType, int pageNO,
			int pageSize) throws Exception {
		DictPage dictPage = new DictPage();
		StringBuffer hql = new StringBuffer(256);
		StringBuffer con = new StringBuffer(256);
		List list = new ArrayList();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		String[] rationTypes = StringUtils.split(rationType, ",");
		String rType = "";// 方案类型
		String businessFlag = "";// 出单方式
		rType = rationTypes[0];
		if (rationTypes.length == 2) {
			businessFlag = rationTypes[1];
		}
		hql.append("from PrpDration a where ");
		con.append("a.validInd = '1'");
		con.append(" and a.validDate <= to_date('"
				+ sf.format(new java.util.Date()) + "','YYYY-MM-DD')");
		if (rationCode != null && !"".equals(rationCode)) {
			con.append(" and a.id.rationCode = '");
			con.append(rationCode);
			con.append("'");
		}
		// modify by sunJiuhua 20120709 reason:PUB产品不再拼产品代码条件
		if (riskCode != null && !"".equals(riskCode) && !"PUB".equals(riskCode)) {
			con.append(" and a.prpDrisk.riskCode = '");
			con.append(riskCode);
			con.append("'");
		}
		System.out.println("ss");
		System.out.println("ss");
		if (comCodes != null && comCodes.length > 0 && comCodes[0]!=null) {
			int count = comCodes.length;
			con.append(" and (");
			for (int i = 0; i < count; i++) {
				con.append(" a.areaCode like '%");
				con.append(comCodes[i]);
				if (i == count - 1) {
					con.append("%'");
				} else {
					con.append("%' or ");
				}
			}
			con.append(" ) ");
		}
		if (StringUtils.isNotBlank(rType)) {
			con.append(" and a.rationType = '");
			con.append(rType);
			con.append("'");
		}
		if (StringUtils.isNotBlank(businessFlag)) {
			con.append(" and a.businessFlag like '");
			con.append(businessFlag);
			con.append("'");
		}
		hql.append(con);
		if (pageNO == 0 || pageSize == 0) {
			int count = getCount("dms", "PrpDration", con.toString());
			if (count > 1000) {
				logger.error("系统" + systemCode + "查询记录数过大，不予返回！");
				throw new Exception("系统" + systemCode + "调用接口"
						+ ServiceInfoConst.GETPLAN + "查询记录数过大，请缩小查询范围");
			} else {
				list = super.findByHql(hql.toString());
			}
			System.out.println("查询险别的sql=======》"+con);
			// liufei begin
			Iterator it = list.iterator();
			List<cn.com.sinosoft.dms.model.PrpDration> listD = new ArrayList<cn.com.sinosoft.dms.model.PrpDration>(
					0);
			while (it.hasNext()) {
				cn.com.sinosoft.dms.model.PrpDration prpdration = new cn.com.sinosoft.dms.model.PrpDration();
				BoCopyUtil.convert(
						(cn.com.sinosoft.dms.model.PrpDration) it.next(),
						prpdration, null, null, null);
				listD.add(prpdration);			
			}
			// liufei end
			dictPage.setData(listD);
		} else {
			Page page = super.findByHql(hql.toString(), pageNO, pageSize);
			list = page.getResult();
			// liufei begin
			Iterator it = list.iterator();
			List<cn.com.sinosoft.dms.model.PrpDration> listD = new ArrayList<cn.com.sinosoft.dms.model.PrpDration>(
					0);
			while (it.hasNext()) {
				cn.com.sinosoft.dms.model.PrpDration prpdration = new cn.com.sinosoft.dms.model.PrpDration();
				BoCopyUtil.convert(
						(cn.com.sinosoft.dms.model.PrpDration) it.next(),
						prpdration, null, null, null);
				listD.add(prpdration);
			}
			// liufei end
			dictPage.setData(listD);
			dictPage.setPageNo(pageNO);
			dictPage.setPageSize(pageSize);
			dictPage.setPageCount(page.getTotalPageCount());
			dictPage.setTotalRecordCount(page.getTotalCount());
		}
		return dictPage;
	}

	/** 2010-07-22 by wanghaibo */
	public DictPage getIdentity(String systemCode, String identifierCode,
			String identifierName, String portCode, String portName,
			String countryCode, String countryCName, String countryEName,
			String identifierType, int pageNO, int pageSize) throws Exception {
		DictPage dictPage = new DictPage();
		StringBuffer hql = new StringBuffer(256);
		StringBuffer con = new StringBuffer(256);
		List list = new ArrayList();
		hql.append("from PrpDidentifier a where ");
		con.append("1=1");
		if (identifierCode != null && !"".equals(identifierCode)) {
			con.append(" and a.id.identifierCode = '");
			con.append(identifierCode);
			con.append("'");
		}
		if (portCode != null && !"".equals(portCode)) {
			con.append(" and a.id.portCode = '");
			con.append(portCode);
			con.append("'");
		}
		if (identifierName != null && !"".equals(identifierName)) {
			con.append(" and a.identifierName = '");
			con.append(identifierName);
			con.append("'");
		}
		if (portName != null && !"".equals(portName)) {
			con.append(" and a.portName = '");
			con.append(portName);
			con.append("'");
		}
		if (countryCode != null && !"".equals(countryCode)) {
			con.append(" and a.countryCode = '");
			con.append(countryCode);
			con.append("'");
		}
		if (countryCName != null && !"".equals(countryCName)) {
			con.append(" and a.countryCName = '");
			con.append(countryCName);
			con.append("'");
		}
		if (countryEName != null && !"".equals(countryEName)) {
			con.append(" and a.countryEName = '");
			con.append(countryEName);
			con.append("'");
		}
		if (identifierType != null && !"".equals(identifierType)) {
			con.append(" and a.identifierType = '");
			con.append(identifierType);
			con.append("'");
		}
		hql.append(con);
		if (pageNO == 0 || pageSize == 0) {
			int count = getCount("dms", "PrpDidentifier", con.toString());
			if (count > 1000) {
				logger.error("系统" + systemCode + "查询记录数过大，不予返回！");
				throw new Exception("系统" + systemCode + "调用接口"
						+ ServiceInfoConst.GETIDENTITY + "查询记录数过大，请缩小查询范围");
			} else {
				list = super.findByHql(hql.toString());
			}
			dictPage.setData(list);
		} else {
			Page page = super.findByHql(hql.toString(), pageNO, pageSize);
			list = page.getResult();
			dictPage.setData(list);
			dictPage.setPageNo(pageNO);
			dictPage.setPageSize(pageSize);
			dictPage.setPageCount(page.getTotalPageCount());
			dictPage.setTotalRecordCount(page.getTotalCount());
		}
		return dictPage;
	}

	/** 2010-09-15 by wanghaibo */
	public DictPage getPrpDcurrencyAndExchRate(String systemCode,
			String currencyCode, String currencyName, String validStatus,
			int pageNO, int pageSize) throws Exception {
		DictPage dictPage = new DictPage();
		StringBuffer hql = new StringBuffer(256);
		StringBuffer con = new StringBuffer(256);
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		if (currencyCode != null && !"".equals(currencyCode)) {
			con.append("select a,b.exchRate from PrpDcurrency a,PrpDexch b where a.id.currencyCode = b.id.baseCurrency and a.currencyCode like '");
			con.append(currencyCode);
			con.append("%' and b.id.exchCurrency ='CNY'");
		}
		if (currencyName != null && !"".equals(currencyName)) {
			con.append("and (a.currencyCName like '").append(currencyName);
			con.append("%' or a.currencyEName like '").append(currencyName);
			con.append("%') ");
		}
		if (validStatus != null && !"".equals(validStatus)) {
			con.append(" and a.validStatus = '");
			con.append(validStatus);
			con.append("'");
			con.append(" and b.id.exchDate <= date('"
					+ sf.format(new java.util.Date()) + "')");
			con.append(" " + "order by exchdate desc");
		}
		hql.append(con);
		if (pageNO == 0 || pageSize == 0) {
			List list = super.findByHql(hql.toString());
			dictPage.setData(list);
		} else {
			Page page = super.findByHql(hql.toString(), pageNO, pageSize);
			dictPage.setData(page.getResult());
			dictPage.setPageCount(page.getTotalPageCount());
			dictPage.setPageNo(pageNO);
			dictPage.setPageSize(pageSize);
			dictPage.setTotalRecordCount(page.getTotalCount());
		}
		return dictPage;
	}

	/** 2010-09-15 by wanghaibo */
	public String getPlanWhetherHasFixed(String systemCode, String riskCode) {
		String result = null;
		StringBuffer con = new StringBuffer(256);
		StringBuffer hql = new StringBuffer(256);
		if (riskCode != null && !"".equals(riskCode)) {
			con.append("select count(*) from PrpDplan p where p.riskCode = '");
			con.append(riskCode);
			con.append("' and p.isFixedFlag = '1'");
		}
		hql.append(con);
		List list = super.findByHql(hql.toString());
		if (list.size() > 0) {
			result = "1";
		} else {
			result = "0";
		}
		return result;
	}

	/** 2010-12-16 by wanghaibo */
	public DictPage getSimpleTreaty(String systemCode, String classCode,
			String riskCode, String sectionNo, String startDate,
			String endDate, int pageNo, int pageSize) throws Exception {
		boolean hasFirstCon = false;
		DictPage dictPage = new DictPage();
		StringBuffer hql = new StringBuffer(256);
		StringBuffer con = new StringBuffer(256);
		hql.append("from PrpDSimpleTreaty p ");
		if (classCode != null && !"".equals(classCode)) {
			con.append(hasFirstCon ? " and " : " where ");
			hasFirstCon = true;
			con.append(" p.id.classCode = '").append(classCode).append("'");
		}
		if (riskCode != null && !"".equals(riskCode)) {
			con.append(hasFirstCon ? " and " : " where ");
			hasFirstCon = true;
			con.append(" p.id.riskCode = '").append(riskCode).append("'");
		}
		if (sectionNo != null && !"".equals(sectionNo)) {
			con.append(hasFirstCon ? " and " : " where ");
			hasFirstCon = true;
			con.append(" p.id.sectionNo = '").append(sectionNo).append("'");
		}
		if (startDate != null && !"".equals(startDate)) {
			con.append(hasFirstCon ? " and " : " where ");
			hasFirstCon = true;
			con.append(" p.startDate <= '").append(startDate).append("'");
		}
		if (endDate != null && !"".equals(endDate)) {
			con.append(hasFirstCon ? " and " : " where ");
			hasFirstCon = true;
			con.append(" p.endDate >= '").append(endDate).append("'");
		}
		con.append(hasFirstCon ? " and " : " where ");
		hasFirstCon = true;
		con.append(" p.id.othCondition = '0'");
		hql.append(con);
		if (pageNo == 0 || pageSize == 0) {
			List list = super.findByHql(hql.toString());
			dictPage.setData(list);
		} else {
			Page page = super.findByHql(hql.toString(), pageNo, pageSize);
			dictPage.setData(page.getResult());
			dictPage.setPageCount(page.getTotalPageCount());
			dictPage.setPageNo(pageNo);
			dictPage.setPageSize(pageSize);
			dictPage.setTotalRecordCount(page.getTotalCount());
		}
		return dictPage;
	}

	/** 2011-01-04 by wanghaibo */
	public DictPage getTradeCodes(String systemCode, String upperCode,
			String riskCode, int pageNo, int pageSize) throws Exception {
		DictPage dictPage = new DictPage();
		StringBuffer hql = new StringBuffer(256);
		StringBuffer con = new StringBuffer(256);
		con.append(
				"select a.id.codeCode,a.codeCName,a.upperCode,a.flag from PrpDnewCode a,PrpDnewCodeRisk b where b.id.riskCode in ('PUB','")
				.append(riskCode)
				.append("')")
				// .append(riskCode.substring(0,1)) //非车的险类代码不是险种代码的第一位
				.append(" and b.id.codeType = 'TradeCode'")
				.append(" and a.id.codeCode=b.id.codeCode and a.id.codeType=b.id.codeType")
				.append(" and a.validStatus = 1");
		if (upperCode != null && !"".equals(upperCode)) {
			con.append(" and upperCode = '").append(upperCode).append("'");
		} else {
			con.append(" and (upperCode is null or upperCode = '')");
		}
		hql.append(con);
		if (pageNo == 0 || pageSize == 0) {
			List list = super.findByHql(hql.toString());
			dictPage.setData(list);
		} else {
			Page page = super.findByHql(hql.toString(), pageNo, pageSize);
			dictPage.setData(page.getResult());
			dictPage.setPageCount(page.getTotalPageCount());
			dictPage.setPageNo(pageNo);
			dictPage.setPageSize(pageSize);
			dictPage.setTotalRecordCount(page.getTotalCount());
		}
		return dictPage;
	}

	/** 2011-01-05 by wanghaibo */
	public DictPage getShortRate(String systemCode, String riskCode,
			String clauseCode, String rateType, int newShortTerm,
			int oldShortTerm, int pageNo, int pageSize) throws Exception {
		StringBuffer hql = new StringBuffer(128);
		hql.append("from PrpDriskShortRate a");
		StringBuffer condition = new StringBuffer(256);
		StringBuffer con1 = new StringBuffer(128);
		StringBuffer con2 = new StringBuffer(128);
		boolean hasFirstCon = false;
		if (riskCode != null && !"".equals(riskCode)) {
			con1.append(hasFirstCon ? " and " : " where ");
			hasFirstCon = true;
			con1.append(" a.id.riskCode = '").append(riskCode).append("'");
		}
		if (clauseCode != null && !"".equals(clauseCode)) {
			con1.append(hasFirstCon ? " and " : " where ");
			hasFirstCon = true;
			con1.append(" a.id.clauseCode = '").append(clauseCode).append("'");
		}
		if (rateType != null && !"".equals(rateType)) {
			condition.append(hasFirstCon ? " and " : " where ");
			hasFirstCon = true;
			condition.append(" a.rateType = '").append(rateType).append("'");
		}

		condition.append(hasFirstCon ? " and " : " where ");
		hasFirstCon = true;
		condition.append(" a.lower < " + newShortTerm + " and a.upper >= "
				+ newShortTerm);

		condition.append(hasFirstCon ? " and " : " where ");
		hasFirstCon = true;
		condition.append(" a.actualLower < " + oldShortTerm
				+ " and a.actuaUpper >= " + oldShortTerm);

		con1.append(condition);
		con2.append(" where a.id.riskCode = 'PUB' and a.id.clauseCode = 'PUB'");
		con2.append(condition);

		List list = new ArrayList();
		DictPage dictPage = new DictPage();
		int count = getCount(systemCode, "PrpDriskShortRate", con1.toString());
		if (pageNo == 0 || pageSize == 0) {
			if (count > 1000) {
				throw new Exception("系统" + systemCode + "调用接口"
						+ ServiceInfoConst.NEWGETSHORTRATE + "查询记录数过大，请缩小查询范围");
			} else if (count > 0) {
				hql.append(con1.toString());
			} else {
				hql.append(con2.toString());
			}
			list = super.findByHql(hql.toString());
		} else {
			if (count > 0) {
				hql.append(con1.toString());
			} else {
				hql.append(con2.toString());
			}
			Page page = super.findByHql(hql.toString(), pageNo, pageSize);
			list = page.getResult();
			dictPage.setPageNo(pageNo);
			dictPage.setPageSize(pageSize);
			dictPage.setPageCount(page.getTotalPageCount());
			dictPage.setTotalRecordCount(page.getTotalCount());
		}
		dictPage.setData(list);
		return dictPage;
	}

	public DictPage getShortRate(String systemCode, String riskCode,
			String clauseCode, String rateType, int shortTerm, int pageNo,
			int pageSize) throws Exception {
		StringBuffer hql = new StringBuffer(128);
		hql.append("from PrpDriskShortRate a");
		StringBuffer condition = new StringBuffer(256);
		StringBuffer con1 = new StringBuffer(128);
		StringBuffer con2 = new StringBuffer(128);
		boolean hasFirstCon = false;
		if (riskCode != null && !"".equals(riskCode)) {
			con1.append(hasFirstCon ? " and " : " where ");
			hasFirstCon = true;
			con1.append(" a.id.riskCode = '").append(riskCode).append("'");
		}
		if (clauseCode != null && !"".equals(clauseCode)) {
			con1.append(hasFirstCon ? " and " : " where ");
			hasFirstCon = true;
			con1.append(" a.id.clauseCode = '").append(clauseCode).append("'");
		}
		if (rateType != null && !"".equals(rateType)) {
			condition.append(hasFirstCon ? " and " : " where ");
			hasFirstCon = true;
			condition.append(" a.rateType = '").append(rateType).append("'");
		}

		condition.append(hasFirstCon ? " and " : " where ");
		hasFirstCon = true;
		condition.append("a.lower < " + shortTerm + " and a.upper >= "
				+ shortTerm);

		con1.append(condition);
		con2.append(" where a.id.riskCode = 'PUB' and a.id.clauseCode = 'PUB'");
		con2.append(condition);

		List list = new ArrayList();
		DictPage dictPage = new DictPage();
		int count = getCount(systemCode, "PrpDriskShortRate", con1.toString());
		if (pageNo == 0 || pageSize == 0) {
			if (count > 1000) {
				throw new Exception("系统" + systemCode + "调用接口"
						+ ServiceInfoConst.GETSHORTRATE + "查询记录数过大，请缩小查询范围");
			} else if (count > 0) {
				hql.append(con1.toString());
			} else {
				hql.append(con2.toString());
			}
			list = super.findByHql(hql.toString());
		} else {
			if (count > 0) {
				hql.append(con1.toString());
			} else {
				hql.append(con2.toString());
			}
			Page page = super.findByHql(hql.toString(), pageNo, pageSize);
			list = page.getResult();
			dictPage.setPageNo(pageNo);
			dictPage.setPageSize(pageSize);
			dictPage.setPageCount(page.getTotalPageCount());
			dictPage.setTotalRecordCount(page.getTotalCount());
		}
		dictPage.setData(list);
		return dictPage;
	}

	/**
	 * @author rs
	 * @param systemCode
	 * @param riskCode
	 * @param areaCode
	 * @param clauseCode
	 * @param rateType
	 * @param shortTerm
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public DictPage getShortRateNew(String systemCode, String riskCode,
			String clauseCode, String rateType, Date startDate, int startHour,
			Date endDate, int endHour, int pageNo, int pageSize)
			throws Exception {
		System.out.println("dms接收getShortRateNew1:" + startDate);
		StringBuffer hql = new StringBuffer(128);
		hql.append("from PrpDriskShortRate a");
		StringBuffer condition = new StringBuffer(256);
		StringBuffer con1 = new StringBuffer(128);
		StringBuffer con2 = new StringBuffer(128);
		double shortTerm = 0;
		String shortRateFlagDay = "2";// 按日
		int shortTermDay = DateUtil.getDaysCount(startDate, startHour, endDate,
				endHour);
		String shortRateFlagMonth = "1";// 按月
		int shortTermMonth = DateUtil.getMonthsCount(startDate, startHour,
				endDate, endHour);
		System.out.println("dms接收getShortRateNew2:" + startDate);
		double shortTermTLGMonth = DateUtil.getTLGMonthsCount(startDate, startHour, endDate, endHour);
		
		String shortRateFlagHalfYear = "0";// 按半年
		int shortTermHalfYear = DateUtil.getMonthsCount(startDate, startHour,
				endDate, endHour);
		if ((shortTermHalfYear % 6) > 0) {
			shortTermHalfYear = shortTermHalfYear / 6 + 1;
		} else {
			shortTermHalfYear = shortTermHalfYear / 6;
		}
		String shortRateFlagYear = "0";// 按年
		int shortTermYear = DateUtil.getYearsCount(startDate, startHour,
				endDate, endHour);
		boolean hasFirstCon = false;
		if (riskCode != null && !"".equals(riskCode)) {
			con1.append(hasFirstCon ? " and " : " where ");
			hasFirstCon = true;
			con1.append(" a.id.riskCode = '").append(riskCode).append("'");
		}
		if (clauseCode != null && !"".equals(clauseCode)) {
			con1.append(hasFirstCon ? " and " : " where ");
			hasFirstCon = true;
			con1.append(" a.id.clauseCode = '").append(clauseCode).append("'");
		}
		if (rateType != null && !"".equals(rateType)) {
			condition.append(hasFirstCon ? " and " : " where ");
			hasFirstCon = true;
			condition.append(" a.rateType = '").append(rateType).append("'");
		}
		if (rateType == null || "".equals(rateType)) {
			condition.append(hasFirstCon ? " and " : " where ");
			hasFirstCon = true;
			condition.append(" ( ");
			condition.append(" (a.rateType = '").append(shortRateFlagDay)
					.append("'");
			condition.append(
					" and a.lower < " + shortTermDay + " and a.upper >= "
							+ shortTermDay).append(")");
			condition.append(" or (a.rateType = '").append(shortRateFlagMonth)
					.append("'");
			condition.append(
					" and a.lower < " + shortTermMonth + " and a.upper >= "
							+ shortTermMonth).append(")");
			condition.append(" or (a.rateType = '")
					.append(shortRateFlagHalfYear).append("'");
			condition.append(
					" and a.lower < " + shortTermHalfYear + " and a.upper >= "
							+ shortTermHalfYear).append(")");
			condition.append(" or (a.rateType = '").append(shortRateFlagYear)
					.append("'");
			condition.append(
					" and a.lower < " + shortTermYear + " and a.upper >= "
							+ shortTermYear).append(")");
			condition.append(" ) ");
		} else {
			if (shortRateFlagDay.equals(rateType)) {
				shortTerm = shortTermDay;
			} else if (shortRateFlagMonth.equals(rateType)) {
				shortTerm = shortTermTLGMonth;
			} else if (shortRateFlagHalfYear.equals(rateType)) {
				shortTerm = shortTermHalfYear;
			} else if (shortRateFlagYear.equals(rateType)) {
				shortTerm = shortTermYear;
			}
			condition.append(hasFirstCon ? " and " : " where ");
			hasFirstCon = true;
			condition.append("a.lower <= " + shortTerm + " and a.upper > "
					+ shortTerm);
		}
		condition.append(hasFirstCon ? " and " : " where ");
		condition.append(" a.validInd = '1' ");// modify update by renshuo
												// 2011-10-15
		con1.append(condition);
		con2.append(" where a.id.riskCode = 'PUB' and a.id.clauseCode = 'PUB'");
		con2.append(condition);

		List list = new ArrayList();
		DictPage dictPage = new DictPage();
		int count = getCount(systemCode, "PrpDriskShortRate", con1.toString());
		if (pageNo == 0 || pageSize == 0) {
			if (count > 1000) {
				throw new Exception("系统" + systemCode + "调用接口"
						+ ServiceInfoConst.GETSHORTRATE + "查询记录数过大，请缩小查询范围");
			} else if (count > 0) {
				hql.append(con1.toString());
			} else {
				hql.append(con2.toString());
			}
			list = super.findByHql(hql.toString());
		} else {
			if (count > 0) {
				hql.append(con1.toString());
			} else {
				hql.append(con2.toString());
			}
			Page page = super.findByHql(hql.toString(), pageNo, pageSize);
			list = page.getResult();
			dictPage.setPageNo(pageNo);
			dictPage.setPageSize(pageSize);
			dictPage.setPageCount(page.getTotalPageCount());
			dictPage.setTotalRecordCount(page.getTotalCount());
		}
		dictPage.setData(list);
		return dictPage;
	}
	
	/**
	 * @author liufei
	 * @param systemCode
	 * @param riskCode
	 * @param areaCode
	 * @param clauseCode
	 * @param rateType
	 * @param rationCode
	 * @param shortTerm
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public DictPage getRationShortRate(String systemCode, String riskCode,
			String areaCode, String clauseCode, String rateType,
			String rationCode, Date startDate, int startHour, Date endDate,
			int endHour, int pageNo, int pageSize) throws Exception {
		StringBuffer hql = new StringBuffer(128);
		hql.append("from PrpDrationShortrate a");
		StringBuffer condition = new StringBuffer(256);
		StringBuffer con1 = new StringBuffer(128);
		StringBuffer con2 = new StringBuffer(128);
		int shortTerm = 0;
		String shortRateFlagDay = "3";// 按日
		int shortTermDay = DateUtil.getDaysCount(startDate, startHour, endDate,
				endHour);
		String shortRateFlagMonth = "2";// 按月
		int shortTermMonth = DateUtil.getMonthsCount(startDate, startHour,
				endDate, endHour);
		String shortRateFlagHalfYear = "1";// 按半年
		int shortTermHalfYear = DateUtil.getMonthsCount(startDate, startHour,
				endDate, endHour);
		if ((shortTermHalfYear % 6) > 0) {
			shortTermHalfYear = shortTermHalfYear / 6 + 1;
		} else {
			shortTermHalfYear = shortTermHalfYear / 6;
		}
		String shortRateFlagYear = "0";// 按年
		int shortTermYear = DateUtil.getYearsCount(startDate, startHour,
				endDate, endHour);
		boolean hasFirstCon = false;
		if (riskCode != null && !"".equals(riskCode)) {
			con1.append(hasFirstCon ? " and " : " where ");
			hasFirstCon = true;
			con1.append(" a.id.riskCode = '").append(riskCode).append("'");
		}
		if (clauseCode != null && !"".equals(clauseCode)) {
			con1.append(hasFirstCon ? " and " : " where ");
			hasFirstCon = true;
			con1.append(" a.id.clauseCode = '").append(clauseCode).append("'");
		}
		if (rationCode != null && !"".equals(rationCode)) {
			condition.append(hasFirstCon ? " and " : " where ");
			hasFirstCon = true;
			condition.append(" a.id.rationCode = '").append(rationCode)
					.append("'");
		}
		if (areaCode != null && !"".equals(areaCode)) {
			condition.append(hasFirstCon ? " and " : " where ");
			hasFirstCon = true;
			condition.append(" a.id.areaCode = '").append(areaCode).append("'");
		}

		if (rateType != null && !"".equals(rateType)) {
			condition.append(hasFirstCon ? " and " : " where ");
			hasFirstCon = true;
			condition.append(" a.rateType = '").append(rateType).append("'");
		}
		if (rateType == null || "".equals(rateType)) {
			condition.append(hasFirstCon ? " and " : " where ");
			hasFirstCon = true;
			condition.append(" ( ");
			condition.append(" (a.rateType = '").append(shortRateFlagDay)
					.append("'");
			condition.append(
					" and a.lower < " + shortTermDay + " and a.upper >= "
							+ shortTermDay).append(")");
			condition.append(" or (a.rateType = '").append(shortRateFlagMonth)
					.append("'");
			condition.append(
					" and a.lower < " + shortTermMonth + " and a.upper >= "
							+ shortTermMonth).append(")");
			condition.append(" or (a.rateType = '")
					.append(shortRateFlagHalfYear).append("'");
			condition.append(
					" and a.lower < " + shortTermHalfYear + " and a.upper >= "
							+ shortTermHalfYear).append(")");
			condition.append(" or (a.rateType = '").append(shortRateFlagYear)
					.append("'");
			condition.append(
					" and a.lower < " + shortTermYear + " and a.upper >= "
							+ shortTermYear).append(")");
			condition.append(" ) ");
		} else {
			if (shortRateFlagDay.equals(rateType)) {
				shortTerm = shortTermDay;
			} else if (shortRateFlagMonth.equals(rateType)) {
				shortTerm = shortTermMonth;
			} else if (shortRateFlagHalfYear.equals(rateType)) {
				shortTerm = shortTermHalfYear;
			} else if (shortRateFlagYear.equals(rateType)) {
				shortTerm = shortTermYear;
			}
			condition.append(hasFirstCon ? " and " : " where ");
			hasFirstCon = true;
			condition.append("a.lower < " + shortTerm + " and a.upper >= "
					+ shortTerm);
		}
		condition.append(hasFirstCon ? " and " : " where ");
		condition.append(" a.validInd = '1' ");// modify update by renshuo
												// 2011-10-15
		con1.append(condition);
		con2.append(" where a.id.riskCode = 'PUB' and a.id.clauseCode = 'PUB'");
		con2.append(condition);

		List list = new ArrayList();
		DictPage dictPage = new DictPage();
		int count = getCount(systemCode, "PrpDrationShortrate", con1.toString());
		if (pageNo == 0 || pageSize == 0) {
			if (count > 1000) {
				throw new Exception("系统" + systemCode + "调用接口"
						+ ServiceInfoConst.GETSHORTRATERATION
						+ "查询记录数过大，请缩小查询范围");
			} else if (count > 0) {
				hql.append(con1.toString());
			} else {
				hql.append(con2.toString());
			}
			list = super.findByHql(hql.toString());
		} else {
			if (count > 0) {
				hql.append(con1.toString());
			} else {
				hql.append(con2.toString());
			}
			Page page = super.findByHql(hql.toString(), pageNo, pageSize);
			list = page.getResult();
			dictPage.setPageNo(pageNo);
			dictPage.setPageSize(pageSize);
			dictPage.setPageCount(page.getTotalPageCount());
			dictPage.setTotalRecordCount(page.getTotalCount());
		}
		dictPage.setData(list);
		return dictPage;
	}

	/**
	 * @param systemCode
	 *            系统代码
	 * @param comCodeOrName
	 *            机构代码或名称
	 * @param upperComcode
	 *            上级机构代码（精确匹配）
	 * @param flag
	 *            标志位（精确匹配）
	 * @param validStatus
	 *            有效状态 1:有效数据 0:无效数据 9:所有数据
	 * @return
	 */
	public DictPage getCompanys(String systemCode, String comCodeOrName,
			String upperComCode, String flag, String validStatus, int pageNo,
			int pageSize) throws Exception {
		boolean hasFirstCon = false;
		DictPage dictPage = new DictPage();
		StringBuffer condition = new StringBuffer(32);
		StringBuffer hql = new StringBuffer(256);
		List list = new ArrayList();
		hql.append("from PrpDcompany a");
		if (comCodeOrName != null && !"".equals(comCodeOrName)) {
			condition.append(hasFirstCon ? " and " : " where ");
			hasFirstCon = true;
			condition.append(" (a.comCode like '").append(comCodeOrName)
					.append("%'");
			condition.append(" or a.comCName like '").append(comCodeOrName)
					.append("%'");
			condition.append(" or a.comEName like '").append(comCodeOrName)
					.append("%') ");
		}

		if (upperComCode != null && !"".equals(upperComCode)) {
			condition.append(hasFirstCon ? " and " : " where ");
			hasFirstCon = true;
			condition.append(" a.upperComCode = '").append(upperComCode)
					.append("'");
		}

		if (flag != null && !"".equals(flag)) {
			condition.append(hasFirstCon ? " and " : " where ");
			hasFirstCon = true;
			condition.append(" a.flag like '").append(flag).append("')");
		}
		if (!"9".equals(validStatus)) {
			condition.append(hasFirstCon ? " and " : " where ");
			if ("0".equals(validStatus)) {
				condition.append(" a.validStatus = 0");
			} else {
				condition.append(" a.validStatus = 1");
			}
		}

		hql.append(condition);
		if (pageNo == 0 || pageSize == 0) {
			int count = getCount(systemCode, "PrpDcompany",
					condition.toString());
			if (count > 1000) {
				logger.error("系统" + systemCode + "调用接口"
						+ ServiceInfoConst.GETCOMPANYS + "查询记录数过大，请缩小查询范围");
				throw new Exception("系统" + systemCode + "调用接口"
						+ ServiceInfoConst.GETCOMPANYS + "查询记录数过大，请缩小查询范围");
			} else {
				list = super.findByHql(hql.toString());
			}
		} else {
			Page page = super.findByHql(hql.toString(), pageNo, pageSize);
			list = page.getResult();
			dictPage.setData(list);
			dictPage.setPageNo(pageNo);
			dictPage.setPageSize(pageSize);
			dictPage.setPageCount(page.getTotalPageCount());
			dictPage.setTotalRecordCount(page.getTotalCount());
		}
		dictPage.setData(list);
		return dictPage;
	}

	/*
	 * 2010-11-10 wulei 传入的机构如果查不到则上溯上级机构，直到总公司
	 */
	public DictPage getProjects(String systemCode, String projectCode,
			String comCode, int pageNo, int pageSize) throws Exception {
		DictPage dictPage = new DictPage();
		StringBuffer hql = new StringBuffer(256);
		StringBuffer con = new StringBuffer(256);
		List list = new ArrayList();
		boolean hasFirstCon = false;
		hql.append("from PrpDproject a ");
		if (projectCode != null && !"".equals(projectCode)) {
			con.append(hasFirstCon ? " and " : " where ");
			hasFirstCon = true;
			con.append("a.projectCode like '");
			con.append(projectCode);
			con.append("%'");
		}
		if (comCode != null && !"".equals(comCode)) {
			String upperComCode = getAllUpperCom(comCode); // 获得所有上级机构，包括当前机构
			if ("".equals(upperComCode)) {
				throw new Exception("不存在的机构或者机构的upperPath为空。");
			}
			con.append(hasFirstCon ? " and " : " where ");
			hasFirstCon = true;
			con.append("a.comCode in ('" + upperComCode + "')");
		}
		con.append(" and a.validInd = 1");

		hql.append(con);
		// if (pageNo == 0 || pageSize == 0) {
		list = super.findByHql(hql.toString());
		dictPage.setData(list);
		// } else {
		// Page page = super.findByHql(hql.toString(),pageNo,pageSize);
		// list = page.getResult();
		// dictPage.setData(list);
		// dictPage.setPageNo(pageNo);
		// dictPage.setPageSize(pageSize);
		// dictPage.setPageCount(page.getTotalPageCount());
		// dictPage.setTotalRecordCount(page.getTotalCount());
		// }
		return dictPage;
	}

	/**
	 * @param resourceCodeOrName
	 *            专管专营代码或名称
	 * @param projectCode
	 *            项目代码
	 * @param agentCode
	 *            渠道代码
	 * @param comCode
	 *            归属机构代码
	 * @return
	 * @throws Exception
	 */
	public DictPage getResource(String systemCode, String resourceCodeOrName,
			String projectCode, String agentCode, String comCode, int pageNo,
			int pageSize) throws Exception {
		DictPage dictPage = new DictPage();
		StringBuffer hql = new StringBuffer(256);
		StringBuffer con = new StringBuffer(256);
		List list = new ArrayList();
		boolean hasFirstCon = false;
		hql.append("from PrpDresource a ");
		if (resourceCodeOrName != null && !"".equals(resourceCodeOrName)) {
			con.append(hasFirstCon ? " and " : " where ");
			hasFirstCon = true;
			con.append("(a.resourceCode like '");
			con.append(resourceCodeOrName);
			con.append("%' or a.resourceName like '");
			con.append(resourceCodeOrName);
			con.append("%')");
		}
		if (projectCode != null && !"".equals(projectCode)) {
			con.append(hasFirstCon ? " and " : " where ");
			hasFirstCon = true;
			con.append("a.projectCode like '");
			con.append(projectCode);
			con.append("%'");
		}
		if (agentCode != null && !"".equals(agentCode)) {
			con.append(hasFirstCon ? " and " : " where ");
			hasFirstCon = true;
			con.append("a.agentCode like '");
			con.append(agentCode);
			con.append("%'");
		}
		if (comCode != null && !"".equals(comCode)) {
			con.append(hasFirstCon ? " and " : " where ");
			hasFirstCon = true;
			con.append("a.comCode = '");
			con.append(comCode);
			con.append("'");
		}
		con.append(hasFirstCon ? " and " : " where ");
		con.append(" a.validStatus = 1");
		hasFirstCon = true;
		hql.append(con);
		if (pageNo == 0 || pageSize == 0) {
			int count = getCount("dms", "PrpDresource", con.toString());
			if (count > 1000) {
				logger.error("系统" + systemCode + "查询记录数过大，不予返回！");
				throw new Exception("系统" + systemCode + "调用接口"
						+ ServiceInfoConst.GETRESOURCE + "查询记录数过大，请缩小查询范围");
			} else {
				list = super.findByHql(hql.toString());
			}
			dictPage.setData(list);
		} else {
			Page page = super.findByHql(hql.toString(), pageNo, pageSize);
			list = page.getResult();
			dictPage.setData(list);
			dictPage.setPageNo(pageNo);
			dictPage.setPageSize(pageSize);
			dictPage.setPageCount(page.getTotalPageCount());
			dictPage.setTotalRecordCount(page.getTotalCount());
		}
		return dictPage;
	}

	/**
	 * 支持limitflag = '2'的限额代码的翻译
	 */
	public String translateLimit(String systemCode, String riskCode,
			String limitCode) {
		String codeCName = "";
		StringBuffer hql = new StringBuffer();
		hql.append(
				" select a.limitCName from PrpDriskLimit a  where  a.id.riskCode ='")
				.append(riskCode).append("'");
		hql.append(" and a.id.limitCode ='").append(limitCode).append("'");
		List nameList = this.findByHql(hql.toString());
		if (nameList.size() > 0) {
			codeCName = nameList.get(0) + "";
			codeCName = codeCName.trim();
		}
		if ("".equals(codeCName)) {// 如果limitCName空则返回riskCode
			codeCName = riskCode;
		}
		return codeCName;
	}

	/**
	 * @param riskCode
	 *            系统代码
	 * @param riskCode
	 *            产品代码
	 * @param codeType
	 *            代码类型
	 * @param kindCode
	 *            责任代码 author wanghaibo 2011-03-11
	 */
	public DictPage getPrpDcodeKind(String systemCode, String riskCode,
			String codeType, String kindCode, int pageNo, int pageSize)
			throws Exception {
		DictPage dictPage = new DictPage();
		StringBuffer hql = new StringBuffer(256);
		StringBuffer con = new StringBuffer(256);
		List list = new ArrayList();
		boolean hasFirstCon = false;
		hql.append("from PrpDcodeKind a where ");
		con.append("1=1");
		if (riskCode != null && !"".equals(riskCode)) {
			con.append(" and a.id.riskCode = '");
			con.append(riskCode);
			con.append("'");
		}
		if (codeType != null && !"".equals(codeType)) {
			con.append(" and a.id.codeType = '");
			con.append(codeType);
			con.append("'");
		}
		if (kindCode != null && !"".equals(kindCode)) {
			con.append(" and a.id.kindCode = '");
			con.append(kindCode);
			con.append("'");
		}
		hql.append(con);
		if (pageNo == 0 || pageSize == 0) {
			int count = getCount("dms", "PrpDcodeKind", con.toString());
			if (count > 1000) {
				logger.error("系统" + systemCode + "查询记录数过大，不予返回！");
				throw new Exception("系统" + systemCode + "调用接口"
						+ ServiceInfoConst.GETPRPDCODEKIND + "查询记录数过大，请缩小查询范围");
			} else {
				list = super.findByHql(hql.toString());
			}
			dictPage.setData(list);
		} else {
			Page page = super.findByHql(hql.toString(), pageNo, pageSize);
			list = page.getResult();
			dictPage.setData(list);
			dictPage.setPageNo(pageNo);
			dictPage.setPageSize(pageSize);
			dictPage.setPageCount(page.getTotalPageCount());
			dictPage.setTotalRecordCount(page.getTotalCount());
		}
		return dictPage;
	}

	/**
	 * 工作日计算接口
	 * 
	 * @param systemCode
	 *            系统代码
	 * @param Date
	 *            日期代码
	 * @param n
	 *            前几个工作日
	 * @param flag
	 *            TRUE 或者FALSE 标志字段
	 * @return Date:
	 * @throws Exception
	 */
	public Date countWorkDay(String systemCode, Date date, int n, String flag)
			throws Exception {
		Calendar calendar = new GregorianCalendar(date.getYear() + 1900,
				date.getMonth(), date.getDate());
		int i = 0;
		if ("TRUE".equals(flag)) {// 工作日
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			while (i < n) {
				if (!isWorkDate(calendar.getTime())) {
					i--;
				}
				calendar.add(Calendar.DAY_OF_MONTH, -1);
				i++;
			}
			calendar.add(Calendar.DAY_OF_MONTH, 1);
		} else {// 自然日
			calendar.add(Calendar.DAY_OF_MONTH, -n);
		}
		return calendar.getTime();
	}

	private boolean isWorkDate(Date time) {

		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		StringBuffer con = new StringBuffer(256);
		boolean bool = true;
		if (time != null && !"".equals(time)) {
			con.append(" from UtiCalendar u where u.dateDay = '");
			con.append(sf.format(time));
			con.append("'");
		}
		List list = super.findByHql(con.toString());
		if (list.size() != 0) {
			UtiCalendar uti = (UtiCalendar) list.get(0);
			// "1":工作日,"2":休息日,"3":节假日
			if ("2".equals(uti.getDayType()) || "3".equals(uti.getDayType())) {
				bool = false;
			}
		} else {// UtiCalendar表中没有对应的日期配置，星期六、星期日默认为休息日
			Calendar calendar = new GregorianCalendar(time.getYear() + 1900,
					time.getMonth(), time.getDate());
			if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
					|| calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
				logger.debug("UtiCalendar表中没有对应的日期配置:" + sf.format(time)
						+ "默认为休息日");
				bool = false;
			}
		}
		return bool;
	}

	/**
	 * @param riskCode
	 *            系统代码
	 * @param riskCode
	 *            产品代码 author wanghaibo 2011-03-30
	 */
	public DictPage getItem(String systemCode, String riskCode, int pageNo,
			int pageSize) throws Exception {
		DictPage dictPage = new DictPage();
		StringBuffer hql = new StringBuffer(256);
		List<Object[]> list = new ArrayList();
		List listvo = new ArrayList();
		Long lo = null;
		int pgSize = 0;
		if (riskCode != null && !"".equals(riskCode)) {
			hql.append(" select distinct a.id.riskCode,a.id.itemCode,a.oldItemCode,a.itemCName ,a.itemEName,a.upperItemCode,a.level,a.areaLevel,to_char(a.areaCode),to_char(a.areaName),a.validDate,a.invalidDate,a.validInd,a.tcol1,a.tcol2,a.tcol3,to_char(a.remark),a.flag from PrpDriskItem a where a.id.riskCode ='");
			hql.append(riskCode);
			hql.append("'");
			hql.append("order by a.id.itemCode");
		}
		if (pageNo == 0 || pageSize == 0) {
			int count = getCount("dms", "PrpDriskItem", hql.toString());
			if (count > 1000) {
				logger.error("系统" + systemCode + "查询记录数过大，不予返回！");
				throw new Exception("系统" + systemCode + "调用接口"
						+ ServiceInfoConst.GETITEM + "查询记录数过大，请缩小查询范围");
			} else {
				list = super.findByHql(hql.toString());
				if (list.size() > 0) {
					for (Object[] obj : list) {
						String riskCode1 = (String) obj[0];
						String itemCode = (String) obj[1];
						String oldItemCode = (String) obj[2];
						String itemCName = (String) obj[3];
						String itemEName = (String) obj[4];
						String upperItemCode = (String) obj[5];
						String level = (String) obj[6];
						String areaLevel = (String) obj[7];
						String areaCode = (String) obj[8];
						String areaName = (String) obj[9];
						Date validDate = (Date) obj[10];
						Date invalidDate = (Date) obj[11];
						String validInd = (String) obj[12];
						String tcol1 = (String) obj[13];
						String tcol2 = (String) obj[14];
						String tcol3 = (String) obj[15];
						String remark = (String) obj[16];
						String flag = (String) obj[17];
						PrpDriskItem item = new PrpDriskItem();
						PrpDriskItemId itemId = new PrpDriskItemId();
						itemId.setRiskCode(riskCode1);
						itemId.setItemCode(itemCode);
						itemId.setClauseCode(null);
						item.setId(itemId);
						item.setOldItemCode(oldItemCode);
						item.setItemCName(itemCName);
						item.setItemEName(itemEName);
						item.setUpperItemCode(upperItemCode);
						item.setLevel(level);
						item.setAreaMappingCode(null);
						item.setAreaLevel(areaLevel);
						item.setAreaCode(areaCode);
						item.setAreaName(areaName);
						item.setValidDate(validDate);
						item.setInvalidDate(invalidDate);
						item.setValidInd(validInd);
						item.setTcol1(tcol1);
						item.setTcol1(tcol1);
						item.setTcol1(tcol1);
						item.setRemark(remark);
						item.setFlag(flag);
						listvo.add(item);
					}
				} else {
					listvo = list;
				}
			}
			dictPage.setData(listvo);
		} else {
			list = super.findByHql(hql.toString());
			List<Object[]> pageList = new ArrayList();
			if (list.size() < pageNo * pageSize) {
				pageList = list.subList((pageNo - 1) * pageSize, list.size());// 重新组织数据
			} else {
				pageList = list.subList((pageNo - 1) * pageSize, pageNo
						* pageSize);// 重新组织数据
			}
			if (pageList.size() > 0) {
				for (Object[] obj : pageList) {
					String riskCode1 = (String) obj[0];
					String itemCode = (String) obj[1];
					String oldItemCode = (String) obj[2];
					String itemCName = (String) obj[3];
					String itemEName = (String) obj[4];
					String upperItemCode = (String) obj[5];
					String level = (String) obj[6];
					String areaLevel = (String) obj[7];
					String areaCode = (String) obj[8];
					String areaName = (String) obj[9];
					Date validDate = (Date) obj[10];
					Date invalidDate = (Date) obj[11];
					String validInd = (String) obj[12];
					String tcol1 = (String) obj[13];
					String tcol2 = (String) obj[14];
					String tcol3 = (String) obj[15];
					String remark = (String) obj[16];
					String flag = (String) obj[17];
					PrpDriskItem item = new PrpDriskItem();
					PrpDriskItemId itemId = new PrpDriskItemId();
					itemId.setRiskCode(riskCode1);
					itemId.setItemCode(itemCode);
					itemId.setClauseCode(null);
					item.setId(itemId);
					item.setOldItemCode(oldItemCode);
					item.setItemCName(itemCName);
					item.setItemEName(itemEName);
					item.setUpperItemCode(upperItemCode);
					item.setLevel(level);
					item.setAreaMappingCode(null);
					item.setAreaLevel(areaLevel);
					item.setAreaCode(areaCode);
					item.setAreaName(areaName);
					item.setValidDate(validDate);
					item.setInvalidDate(invalidDate);
					item.setValidInd(validInd);
					item.setTcol1(tcol1);
					item.setTcol1(tcol1);
					item.setTcol1(tcol1);
					item.setRemark(remark);
					item.setFlag(flag);
					listvo.add(item);
				}
			} else {
				listvo = list;
			}
			if ((list.size()) <= pageSize) {
				pgSize = 1;
				lo = new Integer(pgSize).longValue();
			} else {
				if ((list.size()) % pageSize == 0) {
					pgSize = (list.size()) / pageSize;
					lo = new Integer(pgSize).longValue();
				} else {
					pgSize = (list.size()) / pageSize + 1;
					lo = new Integer(pgSize).longValue();
				}
			}
			Page pagereturn = new Page(pageNo, list.size(), pageSize, listvo);
			dictPage.setData(pagereturn.getResult());
			dictPage.setPageNo(pageNo);
			dictPage.setPageSize(pageSize);
			dictPage.setPageCount(lo);
			dictPage.setTotalRecordCount(new Integer(list.size()).longValue());
		}
		return dictPage;
	}

	/**
	 * @param systemcode
	 *            系统代码
	 * @param identifierCode
	 *            检验人代码 author wanghaibo 2011-04-08
	 */
	public DictPage getIdentityDesc(String systemCode, String identifierCode,
			int pageNo, int pageSize) throws Exception {
		DictPage dictPage = new DictPage();
		StringBuffer hql = new StringBuffer(256);
		StringBuffer con = new StringBuffer(256);
		List list = new ArrayList();
		hql.append(" from PrpDidentifierDesc a where ");
		con.append(" 1=1");
		if (identifierCode != null && !"".equals(identifierCode)) {
			con.append(" and a.id.identifierCode = '");
			con.append(identifierCode);
			con.append("' order by lineNo asc");
		}
		hql.append(con);
		if (pageNo == 0 || pageSize == 0) {
			list = super.findByHql(hql.toString());
			dictPage.setData(list);
		} else {
			Page page = super.findByHql(hql.toString(), pageNo, pageSize);
			list = page.getResult();
			dictPage.setData(list);
			dictPage.setPageNo(pageNo);
			dictPage.setPageSize(pageSize);
			dictPage.setPageCount(page.getTotalPageCount());
			dictPage.setTotalRecordCount(page.getTotalCount());
		}
		return dictPage;
	}

	/**
	 * @功能：查询社保地方政策资料
	 * @作者：chenyi
	 * @日期：2011-05-13
	 */
	public DictPage getInfomation(String systemCode, Map values)
			throws Exception {
		int pageNo = (Integer) values.get("pageNo");
		int pageSize = (Integer) values.get("pageSize");
		String proviceCode = (String) values.get("proviceCode");
		String cityCode = (String) values.get("cityCode");
		String countyCode = (String) values.get("countyCode");
		Date validDate = (Date) values.get("validDate");
		String fileCode = (String) values.get("fileCode");
		String fileName = (String) values.get("fileName");
		String regulationType = (String) values.get("regulationType");

		DictPage dictPage = new DictPage();
		StringBuffer hql = new StringBuffer("from PrpdRegulation a where 1=1");
		StringBuffer condition = new StringBuffer(256);
		List list = new ArrayList();

		if (proviceCode != null && !"".equals(proviceCode)) {
			condition.append(" and a.proviceCode ='").append(proviceCode)
					.append("'");
		}
		if (cityCode != null && !"".equals(cityCode)) {
			condition.append(" and a.cityCode ='").append(cityCode).append("'");
		}
		if (countyCode != null && !"".equals(countyCode)) {
			condition.append(" and a.countyCode ='").append(countyCode)
					.append("'");
		}
		if (fileCode != null && !"".equals(fileCode)) {
			condition.append(" and a.fileCode ='").append(fileCode).append("'");
		}
		if (fileName != null && !"".equals(fileName)) {
			condition.append(" and a.fileName ='").append(fileName).append("'");
		}
		if (regulationType != null && !"".equals(regulationType)) {
			condition.append(" and a.regulationType ='").append(regulationType)
					.append("'");
		}
		if (validDate != null) {
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			StringBuffer con = new StringBuffer();
			// MODIFY
			// BEGIN-ADD-duanfa-20110713-reason:validDate为Date类型×××××chenyi修改
			con.append(" and a.validDate = ");
			con.append(" to_date('");
			con.append(sf.format(validDate));
			con.append("','yyyy-mm-dd') ");
			// MODIFY
			// END-ADD-chenyi-20110713-reason:validDate为Date类型×××××chenyi修改
			// MODIFY BEGIN-DELETE-chenyi-20110721-reason：多拼了一个‘
			// con.append("'");
			// MODIFY BEGIN-DELETE-chenyi-20110721-reason：多拼了一个‘
			condition.append(con);
		}
		StringBuffer order = new StringBuffer(condition)
				.append(" order by a.validDate");
		// hql.append(condition);
		if (pageNo == 0 || pageSize == 0) {
			int count = getCount("dms", "PrpdRegulation",
					condition.substring(4));
			if (count > 1000) {
				logger.error("系统" + systemCode + "查询记录数过大，不予返回！");
				throw new Exception("系统" + systemCode + "调用接口"
						+ ServiceInfoConst.GETINFOMATION + "查询记录数过大，请缩小查询范围");
			} else {
				list = super.findByHql(hql.append(order).toString());
			}
			dictPage.setData(list);
		} else {
			Page page = super.findByHql(hql.append(order).toString(), pageNo,
					pageSize);
			list = page.getResult();

			dictPage.setData(list);
			dictPage.setPageNo(pageNo);
			dictPage.setPageSize(pageSize);
			dictPage.setPageCount(page.getTotalPageCount());
			dictPage.setTotalRecordCount(page.getTotalCount());
		}
		return dictPage;
	}

	/**
	 * 产品创新同步产品修订数据 guyanqing
	 * */
	public String synModiyRiskData(Object riskObj_dms) throws Exception {

		String isSuccess = "";
		RiskObj riskObj = (RiskObj) riskObj_dms;
		List prpDaccountInfoList = riskObj.getPrpDaccountInfo();// 核算信息表
		List prpDareaList = riskObj.getPrpDarea();// 区域表
		List prpDriskList = riskObj.getPrpDrisk();// 产品定义表
		List prpDriskClauseList = riskObj.getPrpDriskClause();// 产品条款定义表
		List prpDriskClauseKindList = riskObj.getPrpDriskClauseKind();// 产品条款责任表
		List prpDriskClauseKindRelationList = riskObj
				.getPrpDriskClauseKindRelation();// 产品条款/责任关系表
		List prpDriskEngageList = riskObj.getPrpDriskEngage();// 产品特别约定表
		List prpDriskItemList = riskObj.getPrpDriskItem();// 产品标的表
		List prpDriskLimitList = riskObj.getPrpDriskLimit();// 产品限额/免赔额表
		List prpDriskShortRateList = riskObj.getPrpDriskShortRate();// 产品短期费率表
		List newCodeRiskList = riskObj.getPrpDnewCodeRisk();//
		List prpDRCKRateLowerList = riskObj.getPrpDRCKRateLower();// 费率下限表
		List prpDclass=riskObj.getPrpDclass();
		// modify begin del by guyanqing 2011-10-19
		/*
		 * if(prpDriskList != null && !prpDriskList.isEmpty()){
		 * super.saveAll(prpDriskList); }
		 */
		// modify end del by guyanqing 2011-10-19
		if (prpDriskClauseList != null && !prpDriskClauseList.isEmpty()) {
			super.saveAll(prpDriskClauseList);
		}
		if (prpDriskClauseKindList != null && !prpDriskClauseKindList.isEmpty()) {
			super.saveAll(prpDriskClauseKindList);
		}
		if (prpDriskShortRateList != null && !prpDriskShortRateList.isEmpty()) {
			super.saveAll(prpDriskShortRateList);
		}
		if (prpDriskItemList != null && !prpDriskItemList.isEmpty()) {
			super.saveAll(prpDriskItemList);
		}
		if (prpDriskLimitList != null && !prpDriskLimitList.isEmpty()) {
			super.saveAll(prpDriskLimitList);
		}
		if (prpDriskEngageList != null && !prpDriskEngageList.isEmpty()) {
			super.saveAll(prpDriskEngageList);
		}
		if (prpDriskClauseKindRelationList != null
				&& !prpDriskClauseKindRelationList.isEmpty()) {
			super.saveAll(prpDriskClauseKindRelationList);
		}
		if (prpDaccountInfoList != null && !prpDaccountInfoList.isEmpty()) {
			super.saveAll(prpDaccountInfoList);
		}
		if (prpDareaList != null && !prpDareaList.isEmpty()) {
			super.saveAll(prpDareaList);
		}
		if (prpDRCKRateLowerList != null && !prpDRCKRateLowerList.isEmpty()) {
			super.saveAll(prpDRCKRateLowerList);
		}
		if (prpDclass != null && !prpDclass.isEmpty()) {
			super.saveAll(prpDclass);
		}
		/** 创新同步过来的数据默认保存一条产品模板的对应关系，目前模板中只存有固化公用的信息，供承保不经产品配置的展现与实现 */

		return isSuccess;
	}

	/**
	 * 产品创新同步产品废止 guyanqing 2011-09-28
	 * */
	public String synReviseRiskData(Object riskObj_dms) throws Exception {

		String isSuccess = "";
		try {
			String code = (String) riskObj_dms;
			Session session = null;
			session = this.getSession();
			String hql0 = "update PrpDrisk set validind = '0',flag = '0' where riskCode='"
					+ code + "' ";
			Query q0 = session.createSQLQuery(hql0);
			q0.executeUpdate();
			isSuccess = "1";
		} catch (Exception e) {
			isSuccess = "0";
			throw new Exception("产品废止同步数据时组织接口出错，请与管理员联系!");
		}
		return isSuccess;
	}

	/**
	 * 产品创新同步条款废止 guyanqing 2011-09-28
	 * */
	public String synReviseClauseData(Object riskObj_dms) throws Exception {
		String isSuccess = "";
		try {
			String code = (String) riskObj_dms;
			Session session = null;
			session = this.getSession();
			String hql0 = "update PrpDriskClause set validind = '0' where clauseCode='"
					+ code + "' ";
			Query q0 = session.createSQLQuery(hql0);
			q0.executeUpdate();
			String hql1 = "update PrpDriskClauseKind set validind = '0' where clauseCode='"
					+ code + "' ";
			Query q1 = session.createSQLQuery(hql1);
			q1.executeUpdate();
			String hql2 = "update PrpDriskClauseKindRelation set validind = '0' where relationCode like'"
					+ code + "%' ";
			Query q2 = session.createSQLQuery(hql2);
			q2.executeUpdate();
			String hqll2 = "update PrpDriskClauseKindRelation set validind = '0' where checkCode like'"
					+ code + "%' ";
			Query qq2 = session.createSQLQuery(hqll2);
			qq2.executeUpdate();
			String hql3 = "update PrpDriskEngage set validind = '0' where clauseCode='"
					+ code + "' ";
			Query q3 = session.createSQLQuery(hql3);
			q3.executeUpdate();
			String hql4 = "update PrpDriskItem set validind = '0' where clauseCode='"
					+ code + "' ";
			Query q4 = session.createSQLQuery(hql4);
			q4.executeUpdate();
			String hql5 = "update PrpDriskLimit set validind = '0' where clauseCode='"
					+ code + "' ";
			Query q5 = session.createSQLQuery(hql5);
			q5.executeUpdate();
			String hql6 = "update PrpDriskShortRate set validind = '0' where clauseCode='"
					+ code + "' ";
			Query q6 = session.createSQLQuery(hql6);
			q6.executeUpdate();
			isSuccess = "1";
		} catch (Exception e) {
			isSuccess = "0";
			throw new Exception("条款废止同步数据时组织接口出错，请与管理员联系!");
		}

		return isSuccess;
	}

	public DictPage getRationRate(String systemCode, String riskCode,
			String areaCode, String clauseCode, String kindCode,
			String rationCode, Date startDate, int startHour, Date endDate,
			int endHour, int pageNo, int pageSize) throws Exception {

		String modeType = "3";// add by guyanqing 默认按天，保留月日年的默认取值
		StringBuffer hql = new StringBuffer(128);
		hql.append("from PrpdRationRate a");
		StringBuffer condition = new StringBuffer(256);
		StringBuffer con1 = new StringBuffer(128);
		StringBuffer con2 = new StringBuffer(128);
		int shortTerm = 0;
		String shortRateFlagDay = "3";// 按日
		int shortTermDay = DateUtil.getDaysCount(startDate, startHour, endDate,
				endHour);
		String shortRateFlagMonth = "2";// 按月
		int shortTermMonth = DateUtil.getMonthsCount(startDate, startHour,
				endDate, endHour);
		String shortRateFlagHalfYear = "1";// 按半年
		int shortTermHalfYear = DateUtil.getMonthsCount(startDate, startHour,
				endDate, endHour);
		if ((shortTermHalfYear % 6) > 0) {
			shortTermHalfYear = shortTermHalfYear / 6 + 1;
		} else {
			shortTermHalfYear = shortTermHalfYear / 6;
		}
		String shortRateFlagYear = "0";// 按年
		int shortTermYear = DateUtil.getYearsCount(startDate, startHour,
				endDate, endHour);
		boolean hasFirstCon = false;
		if (riskCode != null && !"".equals(riskCode)) {
			con1.append(hasFirstCon ? " and " : " where ");
			hasFirstCon = true;
			con1.append(" a.id.riskCode = '").append(riskCode).append("'");
		}
		if (clauseCode != null && !"".equals(clauseCode)) {
			con1.append(hasFirstCon ? " and " : " where ");
			hasFirstCon = true;
			con1.append(" a.id.clauseCode = '").append(clauseCode).append("'");
		}
		if (clauseCode != null && !"".equals(kindCode)) {
			con1.append(hasFirstCon ? " and " : " where ");
			hasFirstCon = true;
			con1.append(" a.id.kindCode = '").append(kindCode).append("'");
		}
		if (rationCode != null && !"".equals(rationCode)) {
			condition.append(hasFirstCon ? " and " : " where ");
			hasFirstCon = true;
			condition.append(" a.id.rationCode = '").append(rationCode)
					.append("'");
		}
		/*
		 * if (areaCode != null && !"".equals(areaCode)) {
		 * condition.append(hasFirstCon ? " and " : " where "); hasFirstCon =
		 * true;
		 * condition.append(" a.id.areaCode = '").append(areaCode).append("'");
		 * }
		 */// 暂不对区域控制

		if (modeType != null && !"".equals(modeType)) {
			condition.append(hasFirstCon ? " and " : " where ");
			hasFirstCon = true;
			condition.append(" a.modeType = '").append(modeType).append("'");
		}
		if (modeType == null || "".equals(modeType)) {
			condition.append(hasFirstCon ? " and " : " where ");
			hasFirstCon = true;
			condition.append(" ( ");
			condition.append(" (a.modeType = '").append(shortRateFlagDay)
					.append("'");
			condition.append(
					" and a.insureDayLower < " + shortTermDay
							+ " and a.insureDayUpper >= " + shortTermDay)
					.append(")");
			condition.append(" or (a.modeType = '").append(shortRateFlagMonth)
					.append("'");
			condition.append(
					" and a.insureDayLower < " + shortTermMonth
							+ " and a.insureDayUpper >= " + shortTermMonth)
					.append(")");
			condition.append(" or (a.modeType = '")
					.append(shortRateFlagHalfYear).append("'");
			condition.append(
					" and a.insureDayLower < " + shortTermHalfYear
							+ " and a.insureDayUpper >= " + shortTermHalfYear)
					.append(")");
			condition.append(" or (a.modeType = '").append(shortRateFlagYear)
					.append("'");
			condition.append(
					" and a.insureDayLower < " + shortTermYear
							+ " and a.insureDayUpper >= " + shortTermYear)
					.append(")");
			condition.append(" ) ");
		} else {
			if (shortRateFlagDay.equals(modeType)) {
				shortTerm = shortTermDay;
			} else if (shortRateFlagMonth.equals(modeType)) {
				shortTerm = shortTermMonth;
			} else if (shortRateFlagHalfYear.equals(modeType)) {
				shortTerm = shortTermHalfYear;
			} else if (shortRateFlagYear.equals(modeType)) {
				shortTerm = shortTermYear;
			}
			condition.append(hasFirstCon ? " and " : " where ");
			hasFirstCon = true;
			condition.append("a.insureDayLower <= " + shortTerm
					+ " and a.insureDayUpper >= " + shortTerm);
		}
		condition.append(hasFirstCon ? " and " : " where ");
		condition.append(" a.validInd = '1' ");// modify update by renshuo
												// 2011-10-15
		con1.append(condition);
		con2.append(" where a.id.riskCode = 'PUB' and a.id.clauseCode = 'PUB'");
		con2.append(condition);

		List list = new ArrayList();
		DictPage dictPage = new DictPage();
		int count = getCount(systemCode, "PrpdRationRate", con1.toString());
		if (pageNo == 0 || pageSize == 0) {
			if (count > 1000) {
				throw new Exception("系统" + systemCode + "调用接口"
						+ ServiceInfoConst.GETSHORTRATERATION
						+ "查询记录数过大，请缩小查询范围");
			} else if (count > 0) {
				hql.append(con1.toString());
			} else {
				hql.append(con2.toString());
			}
			list = super.findByHql(hql.toString());
		} else {
			if (count > 0) {
				hql.append(con1.toString());
			} else {
				hql.append(con2.toString());
			}
			Page page = super.findByHql(hql.toString(), pageNo, pageSize);
			list = page.getResult();
			dictPage.setPageNo(pageNo);
			dictPage.setPageSize(pageSize);
			dictPage.setPageCount(page.getTotalPageCount());
			dictPage.setTotalRecordCount(page.getTotalCount());
		}
		dictPage.setData(list);
		return dictPage;
	}

	/**
	 * @desc 计算保费
	 * @author wpf
	 * @date 2012-4-11
	 * @param pcrckList
	 * @param rationCode
	 * @param startDate
	 * @param startHour
	 * @param endDate
	 * @param endHour
	 * @param flag
	 * @return
	 * @throws Exception
	 */
	public List<PrpDrationClauseKind> jsbf(List pcrckList, String rationCode,
			String[] comCodes, String startDate, String startHour,
			String endDate, String endHour, String flag) throws Exception {
		if (rationCode == null || "".equals(rationCode) || startDate == null
				|| "".equals(startDate) || startHour == null
				|| "".equals(startHour) || endDate == null
				|| "".equals(endDate) || endHour == null || "".equals(endHour)) {
			// add by fangchuanhui begin 20120614 reason:方案责任拷贝拷贝日补贴金额
			List<PrpDrationClauseKind> pdcks = new ArrayList<PrpDrationClauseKind>();
			List<PrpDChannelRationClauseKind> pcrcks = pcrckList;
			if (flag.equals("1")) {
				for (int i = 0; i < pcrckList.size(); i++) {
					PrpDrationClauseKind pdck = new PrpDrationClauseKind();
					BoCopyUtil
							.convert(pcrckList.get(i), pdck, null, null, null);
					if (pcrcks.get(i).getAllowance().equals("1")) {
						pdck.setAllowanceAmount(pcrcks.get(i)
								.getAllowanceAmount().toString());
						pdck.setAllowanceSumAmount(pcrcks.get(i)
								.getAllowanceSumAmount().toString());
					}
					pdcks.add(pdck);
				}
				return pdcks;
			} else {
				return pcrckList;
			}
		}
		List<PrpDrationClauseKind> pckList = new ArrayList<PrpDrationClauseKind>();
		String type = null;

		Date sD = DateUtil.strToDate(startDate);
		Date eD = DateUtil.strToDate(endDate);
		// 扣除年月后相差的天数
		int cd = DateUtil.getOtherDayCount(sD, Integer.parseInt(startHour), eD,
				Integer.parseInt(endHour));

		Calendar scl = Calendar.getInstance();
		scl.setTime(sD);
		Calendar ecl = Calendar.getInstance();
		ecl.setTime(eD);
		int sd = scl.get(Calendar.DAY_OF_MONTH);
		int ey = ecl.get(Calendar.YEAR);
		int eM = ecl.get(Calendar.MONTH) + 1;
		int ed = ecl.get(Calendar.DAY_OF_MONTH);
		// 去除最后一个月的天数
		if (sd > ed) {
			if (eM == 5 || eM == 7 || eM == 10 || eM == 12) {
				cd = cd % 30;
			} else if (eM == 1 || eM == 2 || eM == 4 || eM == 6 || eM == 8
					|| eM == 9 || eM == 11) {
				cd = cd % 31;
			} else if (eM == 3 && ey % 4 == 0 && ey % 10 != 0) {
				if (sd > 29) {
					cd = cd % 31;
				} else {
					cd = cd % 29;
				}
			} else if (eM == 3) {
				if (sd > 28) {
					cd = cd % 31;
				} else {
					cd = cd % 28;
				}
			}
		} else if (sd < ed) {
			if (eM == 1 || eM == 3 || eM == 5 || eM == 7 || eM == 8 || eM == 10
					|| eM == 12) {
				cd = cd % 31;
			} else if (eM == 4 || eM == 6 || eM == 9 || eM == 11) {
				cd = cd % 30;
			} else if (eM == 2 && ey % 4 == 0 && ey % 10 != 0) {
				cd = cd % 29;
			} else if (eM == 2) {
				cd = cd % 28;
			}
		}

		// 相差年数
		int y = DateUtil.getYearsCount(sD, Integer.parseInt(startHour), eD,
				Integer.parseInt(endHour));
		// 相差月数
		int m = DateUtil.getMonthsCount(sD, Integer.parseInt(startHour), eD,
				Integer.parseInt(endHour));
		// 相差总天数
		int day = DateUtil.getDaysCount(sD, Integer.parseInt(startHour), eD,
				Integer.parseInt(endHour));

		for (int i = 0; i < pcrckList.size(); i++) {
			PrpDrationClauseKind prpDrationClauseKind = new PrpDrationClauseKind();
			PrpDChannelRationClauseKind prpDChannelRationClauseKind = new PrpDChannelRationClauseKind();
			StringBuffer sql = new StringBuffer("");
			if ("1".equals(flag)) {// 个性费率/保费区间信息
				prpDChannelRationClauseKind = (PrpDChannelRationClauseKind) pcrckList
						.get(i);
				// add by wpf 2012-04-20 begin reason:澧炲姞琛ヨ创杩斿洖
				if ("1".equals(prpDChannelRationClauseKind.getAllowance())) {
					BigDecimal allowanceAmount = prpDChannelRationClauseKind
							.getAllowanceAmount();
					BigDecimal allowanceDays;
					if (prpDChannelRationClauseKind.getAllowanceDays() != null
							&& !"0".equals(prpDChannelRationClauseKind
									.getAllowanceDays())) {
						allowanceDays = new BigDecimal(
								prpDChannelRationClauseKind.getAllowanceDays());
					} else {
						allowanceDays = new BigDecimal(day);
					}
					prpDChannelRationClauseKind
							.setDefaultAmount(allowanceAmount
									.multiply(allowanceDays));
					prpDChannelRationClauseKind
							.setAllowanceSumAmount(allowanceAmount
									.multiply(allowanceDays));
					if (StringUtils.isNotBlank(prpDChannelRationClauseKind
							.getAllowanceAmountLower())
							&& StringUtils
									.isNotBlank(prpDChannelRationClauseKind
											.getAllowanceDaysUpper())) {
						prpDChannelRationClauseKind
								.setAmountLower(new BigDecimal(
										prpDChannelRationClauseKind
												.getAllowanceAmountLower()).multiply(new BigDecimal(
										prpDChannelRationClauseKind
												.getAllowanceDaysLower())));
						prpDChannelRationClauseKind
								.setAmountUpper(new BigDecimal(
										prpDChannelRationClauseKind
												.getAllowanceAmountUpper()).multiply(new BigDecimal(
										prpDChannelRationClauseKind
												.getAllowanceDaysUpper())));
					}
				}
				// add by wpf 2012-04-20 end reason:澧炲姞琛ヨ创杩斿洖
				StringBuffer PrpDChannelRationPeriodRateHql = new StringBuffer(
						"from PrpDChannelRationPeriodRate pcrpr where pcrpr.id.rationCode = '"
								+ rationCode + "'");
				PrpDChannelRationPeriodRateHql
						.append(" and pcrpr.id.channelInfoNo ='"
								+ prpDChannelRationClauseKind.getId()
										.getChannelInfoNo() + "'");
				PrpDChannelRationPeriodRateHql
						.append(" and pcrpr.id.channelRationClauseKindNo = '"
								+ prpDChannelRationClauseKind.getId()
										.getSerialNo() + "' ");
				if (comCodes != null && comCodes.length > 0) {
					PrpDChannelRationPeriodRateHql.append(" and(");
					for (int j = 0; j < comCodes.length; j++) {
						if (j == comCodes.length - 1) {
							PrpDChannelRationPeriodRateHql
									.append("pcrpr.channelCode like '%"
											+ comCodes[j] + "%') ");
						} else {
							PrpDChannelRationPeriodRateHql
									.append("pcrpr.channelCode like '%"
											+ comCodes[j] + "%' or ");
						}
					}
				}
				PrpDChannelRationPeriodRateHql
						.append(" order by pcrpr.rateType ");

				sql.append(PrpDChannelRationPeriodRateHql);
			} else {// 公用费率/保费区间信息
				prpDrationClauseKind = (PrpDrationClauseKind) pcrckList.get(i);
				// add by wpf 2012-04-20 begin reason:澧炲姞琛ヨ创杩斿洖
				if ("1".equals(prpDrationClauseKind.getAllowance())) {
					BigDecimal allowanceAmount = new BigDecimal(
							prpDrationClauseKind.getAllowanceAmount());
					BigDecimal allowanceDays;
					if (prpDrationClauseKind.getAllowanceDays() != null
							&& !"0".equals(prpDrationClauseKind
									.getAllowanceDays())) {
						allowanceDays = new BigDecimal(
								prpDrationClauseKind.getAllowanceDays());
					} else {
						allowanceDays = new BigDecimal(day);
					}
					prpDrationClauseKind.setDefaultAmount(allowanceAmount
							.multiply(allowanceDays));
					prpDrationClauseKind.setAllowanceSumAmount(allowanceAmount
							.multiply(allowanceDays).toString());
					if (StringUtils.isNotBlank(prpDrationClauseKind
							.getAllowanceAmountLower())
							&& StringUtils.isNotBlank(prpDrationClauseKind
									.getAllowanceDaysUpper())) {
						prpDrationClauseKind.setAmountLower(new BigDecimal(
								prpDrationClauseKind.getAllowanceAmountLower())
								.multiply(new BigDecimal(prpDrationClauseKind
										.getAllowanceDaysLower())));
						prpDrationClauseKind.setAmountUpper(new BigDecimal(
								prpDrationClauseKind.getAllowanceAmountUpper())
								.multiply(new BigDecimal(prpDrationClauseKind
										.getAllowanceDaysUpper())));
					}
				}
				// add by wpf 2012-04-20 end reason:澧炲姞琛ヨ创杩斿洖
				StringBuffer PrpDRationPeriodRateHql = new StringBuffer(
						" from PrpDRationPeriodRate prpr where prpr.id.rationCode ='"
								+ rationCode + "' ");
				PrpDRationPeriodRateHql.append(" and prpr.id.riskCode ='"
						+ prpDrationClauseKind.getId().getRiskCode() + "'");
				PrpDRationPeriodRateHql.append(" and prpr.id.kindCode ='"
						+ prpDrationClauseKind.getId().getKindCode() + "' ");
				PrpDRationPeriodRateHql.append(" and prpr.id.clauseCode='"
						+ prpDrationClauseKind.getId().getClauseCode() + "' ");
				if (comCodes != null && comCodes.length > 0) {
					PrpDRationPeriodRateHql.append(" and(");
					for (int j = 0; j < comCodes.length; j++) {
						if (j == comCodes.length - 1) {
							PrpDRationPeriodRateHql
									.append("prpr.channelCode like '%"
											+ comCodes[j] + "%') ");
						} else {
							PrpDRationPeriodRateHql
									.append("prpr.channelCode like '%"
											+ comCodes[j] + "%' or ");
						}
					}
				}
				PrpDRationPeriodRateHql.append(" order by prpr.rateType ");
				sql.append(PrpDRationPeriodRateHql);
			}
			Map mapLower = new HashMap();
			Map mapUpper = new HashMap();
			Map map = new HashMap();

			List pcrprList = super.findByHql(sql.toString());
			if (pcrprList == null || pcrprList.size() == 0) {
				if ("1".equals(flag)) {
					PrpDChannelRationClauseKind pk = (PrpDChannelRationClauseKind) pcrckList
							.get(i);
					PrpDrationClauseKind p = new PrpDrationClauseKind();
					BoCopyUtil.convert(pk, p, null, null, null);
					PrpDrationClauseKindId prpDrationClauseKindId = p.getId();
					prpDrationClauseKindId
							.setClauseCode(prpDChannelRationClauseKind
									.getClauseCode());
					prpDrationClauseKindId
							.setKindCode(prpDChannelRationClauseKind
									.getKindCode());
					prpDrationClauseKindId
							.setRiskCode(prpDChannelRationClauseKind
									.getRiskCode());
					pckList.add(p);
				} else {
					pckList.add((PrpDrationClauseKind) pcrckList.get(i));
				}
				continue;
			}
			// 单位费率/保费
			type = "1";

			Map<String, Object> prpDcrprMap = new HashMap<String, Object>(0);
			Map<String, Object> prpDrprMap = new HashMap<String, Object>(0);

			BigDecimal u = new BigDecimal("0");
			BigDecimal l = new BigDecimal("0");
			BigDecimal d = new BigDecimal("0");
			for (int j = 0; j < pcrprList.size(); j++) {
				if ("1".equals(flag)) {// 个性费率/保费区间信息
					PrpDChannelRationPeriodRate prpDChannelRationPeriodRate = (PrpDChannelRationPeriodRate) pcrprList
							.get(j);
					// 单位费率/保费：1-年费率/保费、2-半年费率/保费、3-季度费率/保费、4-月费率/保费、5-日费率/保费
					if ("1".equals(prpDChannelRationPeriodRate.getRateType())
							|| "2".equals(prpDChannelRationPeriodRate
									.getRateType())
							|| "3".equals(prpDChannelRationPeriodRate
									.getRateType())
							|| "4".equals(prpDChannelRationPeriodRate
									.getRateType())
							|| "5".equals(prpDChannelRationPeriodRate
									.getRateType())) {
						// 防止配置错误时重复计入相同单位费率/保费
						if (prpDcrprMap.containsKey(prpDChannelRationPeriodRate
								.getRateType())) {
							continue;
						} else {
							prpDcrprMap.put(
									prpDChannelRationPeriodRate.getRateType(),
									null);
						}

						mapUpper.put(prpDChannelRationPeriodRate.getRateType(),
								prpDChannelRationPeriodRate
										.getPremiumOrRateUpper());
						mapLower.put(prpDChannelRationPeriodRate.getRateType(),
								prpDChannelRationPeriodRate
										.getPremiumOrRateLower());
						map.put(prpDChannelRationPeriodRate.getRateType(),
								prpDChannelRationPeriodRate.getDefaultValue());
					} else {// 区间费率/保费：6-年费率/保费、7-半年费率/保费、8-季度费率/保费、9-月费率/保费、0-日费率/保费
						// 区间费率/保费
						type = "2";
						int mm = m;
						if ("6".equals(prpDChannelRationPeriodRate
								.getRateType())) {
							if (prpDChannelRationPeriodRate
									.getOriginalPerioLower().compareTo(
											new BigDecimal(mm).divide(
													new BigDecimal(12), 2,
													BigDecimal.ROUND_UP)) <= 0
									&& prpDChannelRationPeriodRate
											.getPriginalPerioUpper().compareTo(
													new BigDecimal(y)) >= 0) {
								mapUpper.put("1", prpDChannelRationPeriodRate
										.getPremiumOrRateUpper());
								mapLower.put("1", prpDChannelRationPeriodRate
										.getPremiumOrRateLower());
								map.put("1", prpDChannelRationPeriodRate
										.getDefaultValue());
								break;
							}
						}
						if ("7".equals(prpDChannelRationPeriodRate
								.getRateType())) {
							if (prpDChannelRationPeriodRate
									.getOriginalPerioLower().compareTo(
											new BigDecimal(mm).divide(
													new BigDecimal(6), 2,
													BigDecimal.ROUND_UP)) <= 0
									&& prpDChannelRationPeriodRate
											.getPriginalPerioUpper()
											.compareTo(
													new BigDecimal(mm)
															.divide(new BigDecimal(
																	6),
																	2,
																	BigDecimal.ROUND_UP)) >= 0) {
								mapUpper.put("2", prpDChannelRationPeriodRate
										.getPremiumOrRateUpper());
								mapLower.put("2", prpDChannelRationPeriodRate
										.getPremiumOrRateLower());
								map.put("2", prpDChannelRationPeriodRate
										.getDefaultValue());
								break;
							}
						}
						if ("8".equals(prpDChannelRationPeriodRate
								.getRateType())) {
							if (prpDChannelRationPeriodRate
									.getOriginalPerioLower().compareTo(
											new BigDecimal(mm).divide(
													new BigDecimal(3), 2,
													BigDecimal.ROUND_UP)) <= 0
									&& prpDChannelRationPeriodRate
											.getPriginalPerioUpper()
											.compareTo(
													new BigDecimal(mm)
															.divide(new BigDecimal(
																	3),
																	2,
																	BigDecimal.ROUND_UP)) >= 0) {
								mapUpper.put("3", prpDChannelRationPeriodRate
										.getPremiumOrRateUpper());
								mapLower.put("3", prpDChannelRationPeriodRate
										.getPremiumOrRateLower());
								map.put("3", prpDChannelRationPeriodRate
										.getDefaultValue());
								break;
							}
						}
						if ("9".equals(prpDChannelRationPeriodRate
								.getRateType())) {
							if (prpDChannelRationPeriodRate
									.getOriginalPerioLower().compareTo(
											new BigDecimal(mm)) <= 0
									&& prpDChannelRationPeriodRate
											.getPriginalPerioUpper().compareTo(
													new BigDecimal(mm)) >= 0) {
								mapUpper.put("4", prpDChannelRationPeriodRate
										.getPremiumOrRateUpper());
								mapLower.put("4", prpDChannelRationPeriodRate
										.getPremiumOrRateLower());
								map.put("4", prpDChannelRationPeriodRate
										.getDefaultValue());
								break;
							}
						}
						if ("0".equals(prpDChannelRationPeriodRate
								.getRateType())) {
							if (prpDChannelRationPeriodRate
									.getOriginalPerioLower().compareTo(
											new BigDecimal(day)) <= 0
									&& prpDChannelRationPeriodRate
											.getPriginalPerioUpper().compareTo(
													new BigDecimal(day)) >= 0) {
								mapUpper.put("5", prpDChannelRationPeriodRate
										.getPremiumOrRateUpper());
								mapLower.put("5", prpDChannelRationPeriodRate
										.getPremiumOrRateLower());
								map.put("5", prpDChannelRationPeriodRate
										.getDefaultValue());
								break;
							}
						}
					}
				} else {// 公共费率/保费区间
					PrpDRationPeriodRate prpDRationPeriodRate = (PrpDRationPeriodRate) pcrprList
							.get(j);
					// 单位费率/保费：1-年费率/保费、2-半年费率/保费、3-季度费率/保费、4-月费率/保费、5-日费率/保费
					if ("1".equals(prpDRationPeriodRate.getRateType())
							|| "2".equals(prpDRationPeriodRate.getRateType())
							|| "3".equals(prpDRationPeriodRate.getRateType())
							|| "4".equals(prpDRationPeriodRate.getRateType())
							|| "5".equals(prpDRationPeriodRate.getRateType())) {
						// 防止配置错误时重复计入相同单位费率/保费
						if (prpDrprMap.containsKey(prpDRationPeriodRate
								.getRateType())) {
							continue;
						} else {
							prpDrprMap.put(prpDRationPeriodRate.getRateType(),
									null);
						}

						mapUpper.put(prpDRationPeriodRate.getRateType(),
								prpDRationPeriodRate.getPremiumOrRateUpper());
						mapLower.put(prpDRationPeriodRate.getRateType(),
								prpDRationPeriodRate.getPremiumOrRateLower());
						map.put(prpDRationPeriodRate.getRateType(),
								prpDRationPeriodRate.getDefaultValue());
					} else {// 区间费率/保费：6-年费率/保费、7-半年费率/保费、8-季度费率/保费、9-月费率/保费、0-日费率/保费
						int mm = m;
						// 区间费率/保费
						type = "2";
						if ("6".equals(prpDRationPeriodRate.getRateType())) {
							if (prpDRationPeriodRate.getOriginalPerioLower()
									.compareTo(
											new BigDecimal(mm).divide(
													new BigDecimal(12), 2,
													BigDecimal.ROUND_UP)) <= 0
									&& prpDRationPeriodRate
											.getPriginalPerioUpper().compareTo(
													new BigDecimal(y)) >= 0) {
								mapUpper.put("1", prpDRationPeriodRate
										.getPremiumOrRateUpper());
								mapLower.put("1", prpDRationPeriodRate
										.getPremiumOrRateLower());
								map.put("1",
										prpDRationPeriodRate.getDefaultValue());
								break;
							}
						}
						if ("7".equals(prpDRationPeriodRate.getRateType())) {
							if (prpDRationPeriodRate.getOriginalPerioLower()
									.compareTo(
											new BigDecimal(mm).divide(
													new BigDecimal(6), 2,
													BigDecimal.ROUND_UP)) <= 0
									&& prpDRationPeriodRate
											.getPriginalPerioUpper()
											.compareTo(
													new BigDecimal(mm)
															.divide(new BigDecimal(
																	6),
																	2,
																	BigDecimal.ROUND_UP)) >= 0) {
								mapUpper.put("2", prpDRationPeriodRate
										.getPremiumOrRateUpper());
								mapLower.put("2", prpDRationPeriodRate
										.getPremiumOrRateLower());
								map.put("2",
										prpDRationPeriodRate.getDefaultValue());
								break;
							}
						}
						if ("8".equals(prpDRationPeriodRate.getRateType())) {
							if (prpDRationPeriodRate.getOriginalPerioLower()
									.compareTo(
											new BigDecimal(mm).divide(
													new BigDecimal(3), 2,
													BigDecimal.ROUND_UP)) <= 0
									&& prpDRationPeriodRate
											.getPriginalPerioUpper()
											.compareTo(
													new BigDecimal(mm)
															.divide(new BigDecimal(
																	3),
																	2,
																	BigDecimal.ROUND_UP)) >= 0) {
								mapUpper.put("3", prpDRationPeriodRate
										.getPremiumOrRateUpper());
								mapLower.put("3", prpDRationPeriodRate
										.getPremiumOrRateLower());
								map.put("3",
										prpDRationPeriodRate.getDefaultValue());
								break;
							}
						}
						if ("9".equals(prpDRationPeriodRate.getRateType())) {
							if (prpDRationPeriodRate.getOriginalPerioLower()
									.compareTo(new BigDecimal(mm)) <= 0
									&& prpDRationPeriodRate
											.getPriginalPerioUpper().compareTo(
													new BigDecimal(mm)) >= 0) {
								mapUpper.put("4", prpDRationPeriodRate
										.getPremiumOrRateUpper());
								mapLower.put("4", prpDRationPeriodRate
										.getPremiumOrRateLower());
								map.put("4",
										prpDRationPeriodRate.getDefaultValue());
								break;
							}
						}
						if ("0".equals(prpDRationPeriodRate.getRateType())) {
							if (prpDRationPeriodRate.getOriginalPerioLower()
									.compareTo(new BigDecimal(day)) <= 0
									&& prpDRationPeriodRate
											.getPriginalPerioUpper().compareTo(
													new BigDecimal(day)) >= 0) {
								mapUpper.put("5", prpDRationPeriodRate
										.getPremiumOrRateUpper());
								mapLower.put("5", prpDRationPeriodRate
										.getPremiumOrRateLower());
								map.put("5",
										prpDRationPeriodRate.getDefaultValue());
								break;
							}
						}
					}
				}
			}
			BigDecimal upper1 = (BigDecimal) mapUpper.get("1");
			BigDecimal lower1 = (BigDecimal) mapLower.get("1");
			BigDecimal defaultValue1 = (BigDecimal) map.get("1");
			BigDecimal upper2 = (BigDecimal) mapUpper.get("2");
			BigDecimal lower2 = (BigDecimal) mapLower.get("2");
			BigDecimal defaultValue2 = (BigDecimal) map.get("2");
			BigDecimal upper3 = (BigDecimal) mapUpper.get("3");
			BigDecimal lower3 = (BigDecimal) mapLower.get("3");
			BigDecimal defaultValue3 = (BigDecimal) map.get("3");
			BigDecimal upper4 = (BigDecimal) mapUpper.get("4");
			BigDecimal lower4 = (BigDecimal) mapLower.get("4");
			BigDecimal defaultValue4 = (BigDecimal) map.get("4");
			BigDecimal upper5 = (BigDecimal) mapUpper.get("5");
			BigDecimal lower5 = (BigDecimal) mapLower.get("5");
			BigDecimal defaultValue5 = (BigDecimal) map.get("5");
			if ("2".equals(type)) {
				if (upper1 != null) {
					u = u.add(upper1);
					l = l.add(lower1);
					d = d.add(defaultValue1);
				} else if (upper2 != null) {
					u = u.add(upper2);
					l = l.add(lower2);
					d = d.add(defaultValue2);
				} else if (upper3 != null) {
					u = u.add(upper3);
					l = l.add(lower3);
					d = d.add(defaultValue3);
				} else if (upper4 != null) {
					u = u.add(upper4);
					l = l.add(lower4);
					d = d.add(defaultValue4);
				} else if (upper5 != null) {
					u = u.add(upper5);
					l = l.add(lower5);
					d = d.add(defaultValue5);
				}
			} else {
				int mm = m;
				if (cd > 0)
					mm--;
				if (upper1 != null) {
					if (mm >= 12) {
						int x = (int) Math.floor(mm / 12);
						u = new BigDecimal(x).multiply(upper1);
						l = new BigDecimal(x).multiply(lower1);
						d = new BigDecimal(x).multiply(defaultValue1);
						mm = mm - 12 * x;
					}
				}
				if (upper2 != null) {
					if (mm >= 6) {
						int x = (int) Math.floor(mm / 6);
						u = u.add(upper2.multiply(new BigDecimal(x)));
						l = l.add(lower2.multiply(new BigDecimal(x)));
						d = d.add(defaultValue2.multiply(new BigDecimal(x)));
						mm = mm % 6;
					}
				}
				if (upper3 != null) {
					if (mm >= 3) {
						int x = (int) Math.floor(mm / 3);
						u = u.add(upper3.multiply(new BigDecimal(x)));
						l = l.add(lower3.multiply(new BigDecimal(x)));
						d = d.add(defaultValue3.multiply(new BigDecimal(x)));
						mm = mm % 3;
					}
				}
				if (upper4 != null) {
					if (mm >= 1) {
						int x = (int) Math.floor(mm / 1);
						u = u.add(upper4.multiply(new BigDecimal(x)));
						l = l.add(lower4.multiply(new BigDecimal(x)));
						d = d.add(defaultValue4.multiply(new BigDecimal(x)));
						mm = mm % 1;
					}
				}
				if (upper5 != null) {
					if (mm == 0) {
						u = u.add(upper5.multiply(new BigDecimal(cd)));
						l = l.add(lower5.multiply(new BigDecimal(cd)));
						d = d.add(defaultValue5.multiply(new BigDecimal(cd)));
					} else {
						u = u.add(upper5.multiply(new BigDecimal(day)));
						l = l.add(lower5.multiply(new BigDecimal(day)));
						d = d.add(defaultValue5.multiply(new BigDecimal(day)));
					}
				}

				if (upper5 == null && upper4 == null && mm > 0 && mm < 3
						&& upper3 != null) {
					u = u.add(upper3);
					l = l.add(lower3);
					d = d.add(defaultValue3);
				} else if (upper5 == null && upper4 == null && upper3 == null
						&& mm > 0 && mm < 6 && upper2 != null) {
					u = u.add(upper2);
					l = l.add(lower2);
					d = d.add(defaultValue2);
				} else if (upper5 == null && upper4 == null && upper3 == null
						&& upper2 == null && mm > 0 && mm < 12
						&& upper1 != null) {
					u = u.add(upper1);
					l = l.add(lower1);
					d = d.add(defaultValue1);
				}
			}

			if ("1".equals(flag)) {
				prpDChannelRationClauseKind.setValueLower(l);
				prpDChannelRationClauseKind.setValueUpper(u);
				prpDChannelRationClauseKind.setDefaultValue(d);
				PrpDrationClauseKind p = new PrpDrationClauseKind();
				BoCopyUtil.convert(prpDChannelRationClauseKind, p, null, null,
						null);
				PrpDrationClauseKindId prpDrationClauseKindId = p.getId();
				prpDrationClauseKindId
						.setClauseCode(prpDChannelRationClauseKind
								.getClauseCode());
				prpDrationClauseKindId.setKindCode(prpDChannelRationClauseKind
						.getKindCode());
				prpDrationClauseKindId.setRiskCode(prpDChannelRationClauseKind
						.getRiskCode());
				pckList.add(p);
			} else {
				prpDrationClauseKind.setValueLower(l);
				prpDrationClauseKind.setValueUpper(u);
				prpDrationClauseKind.setDefaultValue(d);
				pckList.add(prpDrationClauseKind);
			}
		}
		return pckList;
	}
@Override
	public String synClauseReportData(Object riskObj_dms) throws Exception {
		ClauseReportObj obj=(ClauseReportObj)riskObj_dms;
		List<PrpDclauseReport> reports= obj.getPrpdClauseReport();
		if(null!=reports&&!reports.isEmpty()){
			List<PrpDclauseReport> reportsOld=super.findByHql(" from PrpDclauseReport r where r.id.clauseCode=?", reports.get(0).getId().getClauseCode());
			getHibernateTemplate().deleteAll(reportsOld);
			for(PrpDclauseReport report:reports){
				if(report!=null)
					getHibernateTemplate().save(report);
			}
		}
		return "1";
	}
	//add by fengyang 20140402 reason：套装商品同步方法
		public String synProductSetData(String systemCode, Object data) {
			ProductSetObj productSetObj = (ProductSetObj) data;
			List productsetList = productSetObj.getPrpDset();
			for (Object o : productsetList) {
				PrpDset productSet = new PrpDset();
				cn.com.sinosoft.dms.model.PrpDset prpDset = (cn.com.sinosoft.dms.model.PrpDset) o;
				prpDset.setSetCode(prpDset.getSetCode());
			}
			List prpDsetRationrelationList = productSetObj.getPrpDsetRationrelation();
			List prpDsetRenewalList = productSetObj.getPrpDsetRenewal();
			List prpDsetChannelList = productSetObj.getPrpDsetChannel();
			if (productsetList != null && productsetList.size() > 0) {
				super.saveAll(productsetList);
			}
			if (prpDsetRationrelationList != null && prpDsetRationrelationList.size() > 0) {
				super.saveAll(prpDsetRationrelationList);
			}
			if (prpDsetRenewalList != null && prpDsetRenewalList.size() > 0) {
				super.saveAll(prpDsetRenewalList);
			}
			if (prpDsetChannelList != null && prpDsetChannelList.size() > 0) {
				super.saveAll(prpDsetChannelList);
			}
			return "1";
		}
	public static void main(String[] args) {
		Map<String, String> m=new HashMap<String, String>();
	//	int pageNo = (Integer) values.get("pageNO");
	//    int pageSize = (Integer) values.get("pageSize");
	//    String riskCode = (String) values.get("riskCode");
	//    String kindCode = (String) values.get("kindCode");
	//    String userNature = (String) values.get("userNature");
		m.put("pageNo", "1");
		m.put("pageSize", "10");
		m.put("kindCode", "1");
		m.put("userNature", "a");
		m.put("riskCode", "A01");
		DictionaryService s=new DictionaryServiceImpl();
		try {
			s.getPrpDkindReport("1", m);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//add by wangtao 20140628  增加查询商品文案的方法
	@Override
	public DictPage getReportNoByClauseCode(String systemcode,
			Map<String, String> values) throws Exception {
		String clauseCode=values.get("clauseCode");
		List<PrpDclauseReport> list = new ArrayList<PrpDclauseReport>();
		String hql="select a from PrpDclauseReport a where a.id.clauseCode='"+clauseCode+"' and a.tcol1  is  null ";
		list = super.findByHql(hql.toString());
		DictPage dictPage=new DictPage();
		dictPage.setData(list);
		return dictPage;
	}
	
	//add by yjm 20150331 特約及附加條款查詢
	@Override
	public DictPage getEngageMaintenance(String systemcode, Map<String, String> values) {
		int pageNo = Integer.parseInt(values.get("pageNo"));
		int pageSize = Integer.parseInt(values.get("pageSize"));
		String clauseCode=values.get("clauseCode");
		String engageCode=values.get("engageCode");
		String engageName=values.get("engageName");
		String riskCode=values.get("riskCode");
		String validInd=values.get("validInd");
		List list = new ArrayList();
		QueryRule queryRule = QueryRule.getInstance();
        if(clauseCode!=null&&!"".equals(clauseCode)){
        	queryRule.addLike("id.clauseCode", clauseCode);
        }
        if(engageCode!=null&&!"".equals(engageCode)){
        	queryRule.addLike("id.engageCode", engageCode);
        }
        if(engageName!=null&&!"".equals(engageName)){
        	queryRule.addLike("engageCName", engageName);
        }
        if(riskCode!=null&&!"".equals(riskCode)){
        	queryRule.addEqual("id.riskCode", riskCode);
        }
        if(validInd!=null&&!"".equals(validInd)){
        	queryRule.addEqual("validInd", validInd);
        }
        Page page = super.find(PrpDriskEngage.class, queryRule, pageNo, pageSize);
        List listPage = page.getResult();
		DictPage dictPage=new DictPage();
		dictPage.setPageNo(pageNo);
		dictPage.setPageSize(pageSize);
		dictPage.setPageCount(page.getTotalPageCount());
		dictPage.setTotalRecordCount(page.getTotalCount());
		dictPage.setData(listPage);
		return dictPage;
	}
	
	//add by yjm 20150331 特約及附加條款查詢
		@Override
		public DictPage getClauseMaintenance(String systemcode, Map<String, String> values) {
			int pageNo = Integer.parseInt(values.get("pageNo"));
			int pageSize = Integer.parseInt(values.get("pageSize"));
			String kindCode=values.get("kindCode");
			String kindName=values.get("kindName");
			String tcol1=values.get("tcol1");//條款屬性
			String riskCode=values.get("riskCode");
			String validInd=values.get("validInd");
			String clauseCode=values.get("clauseCode");//條款代碼
			String riskKCSerialNo=values.get("riskKCSerialNo");//險種條款序號
			List list = new ArrayList();
			QueryRule queryRule = QueryRule.getInstance();
			if(clauseCode!=null&&!"".equals(clauseCode)){
	        	queryRule.addLike("id.clauseCode", clauseCode);
	        }
	        if(kindCode!=null&&!"".equals(kindCode)){
	        	queryRule.addLike("kindCode", kindCode);
	        }
	        if(kindName!=null&&!"".equals(kindName)){
	        	queryRule.addLike("kindName", kindName);
	        }
	        if(tcol1!=null&&!"".equals(tcol1)){
	        	queryRule.addEqual("tcol1", tcol1);
	        }
	        if(riskCode!=null&&!"".equals(riskCode)){
	        	queryRule.addEqual("id.riskCode", riskCode);
	        }
	        if(validInd!=null&&!"".equals(validInd)){
	        	queryRule.addEqual("validInd", validInd);
	        }
	        if(riskKCSerialNo!=null&&!"".equals(riskKCSerialNo)){
	        	queryRule.addEqual("id.riskKCSerialNo", Integer.valueOf(riskKCSerialNo));
	        }
	        Page page = super.find(PrpDriskClauseKind.class, queryRule, pageNo, pageSize);
	        List listPage = page.getResult();
			DictPage dictPage=new DictPage();
			dictPage.setPageNo(pageNo);
			dictPage.setPageSize(pageSize);
			dictPage.setPageCount(page.getTotalPageCount());
			dictPage.setTotalRecordCount(page.getTotalCount());
			dictPage.setData(listPage);
			return dictPage;
		}
	
	//add by yjm 20150331 特約及附加條款保存
		@Override
		public DictPage saveClauseMaintenance(String systemCode, Map values) {
			List<com.sinosoft.dmsdriver.model.PrpDriskClauseKind> prpDriskClauseKindList = (List<com.sinosoft.dmsdriver.model.PrpDriskClauseKind>) values.get("prpDriskClauseKindList");
			String operationType = (String)values.get("operationType");
			List<PrpDriskClauseKind> prpDriskClauseKindListNew = new ArrayList<PrpDriskClauseKind>();
			for (int i = 0; i < prpDriskClauseKindList.size(); i++) {
				com.sinosoft.dmsdriver.model.PrpDriskClauseKind prpDriskClauseKind = prpDriskClauseKindList.get(i);
				PrpDriskClauseKind prpDriskClauseKindNew = null;
				try {
					if("insert".equals(operationType)){
						prpDriskClauseKindNew = new PrpDriskClauseKind();
						BoCopyUtil.convert(prpDriskClauseKind, prpDriskClauseKindNew, null, null, null);
						this.save(prpDriskClauseKindNew);
					}else{
						QueryRule queryRule = QueryRule.getInstance();
						queryRule.addEqual("id.clauseCode", prpDriskClauseKind.getId().getClauseCode());
						//queryRule.addEqual("id.engageCode", prpDriskClauseKind.getId().getEngageCode());
						queryRule.addEqual("id.riskCode", prpDriskClauseKind.getId().getRiskCode());
						List<PrpDriskClauseKind> list = super.find(PrpDriskClauseKind.class, queryRule);
						if(list!=null && list.size()>0){
							prpDriskClauseKindNew =list.get(0);	
							BoCopyUtil.convert(prpDriskClauseKind, prpDriskClauseKindNew, null, null, null);
							this.update(prpDriskClauseKindNew);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			
	        DictPage dictPage = new DictPage();
	 		try {
	 			List  list = new ArrayList();
	 			dictPage.setData(list);
	 			dictPage.setTotalRecordCount(new Long(1));
	 			return dictPage;
	 		} catch (Exception e) {
	 			e.printStackTrace();
	 			return null;
	 		}
		}
	
	//add by yjm 20150331 特約及附加條款保存
	@Override
	public DictPage saveEngageMaintenance(String systemCode, Map values) {
		List<com.sinosoft.dmsdriver.model.PrpDriskEngage> prpDriskEngageList = (List<com.sinosoft.dmsdriver.model.PrpDriskEngage>) values.get("prpDriskEngageList");
		String operationType = (String)values.get("operationType");
		List<PrpDriskEngage> prpDriskEngageListNew = new ArrayList<PrpDriskEngage>();
		for (int i = 0; i < prpDriskEngageList.size(); i++) {
			com.sinosoft.dmsdriver.model.PrpDriskEngage prpDriskEngage = prpDriskEngageList.get(i);
			PrpDriskEngage prpDriskEngageNew = null;
			try {
				if("insert".equals(operationType)){
					prpDriskEngageNew = new PrpDriskEngage();
					BoCopyUtil.convert(prpDriskEngage, prpDriskEngageNew, null, null, null);
					this.save(prpDriskEngageNew);
				}else{
					QueryRule queryRule = QueryRule.getInstance();
					queryRule.addEqual("id.clauseCode", prpDriskEngage.getId().getClauseCode());
					queryRule.addEqual("id.engageCode", prpDriskEngage.getId().getEngageCode());
					queryRule.addEqual("id.riskCode", prpDriskEngage.getId().getRiskCode());
					List<PrpDriskEngage> list = super.find(PrpDriskEngage.class, queryRule);
					if(list!=null && list.size()>0){
						prpDriskEngageNew =list.get(0);	
						BoCopyUtil.convert(prpDriskEngage, prpDriskEngageNew, null, null, null);
						this.update(prpDriskEngageNew);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
        DictPage dictPage = new DictPage();
 		try {
 			List  list = new ArrayList();
 			dictPage.setData(list);
 			dictPage.setTotalRecordCount(new Long(1));
 			return dictPage;
 		} catch (Exception e) {
 			e.printStackTrace();
 			return null;
 		}
	}
	
	@Override
	public DictPage getCopyNumber(String systemcode, Map<String, String> values) {
		// TODO Auto-generated method stub
		String clauseCode=values.get("clauseCode");
		 int pageNo = Integer.parseInt(values.get("pageNo"));
		 int pageSize = Integer.parseInt(values.get("pageSize"));
		/*String policyType=values.get("policyType");*/
		String printSign=values.get("printSign");//有效標誌
		String riskCode=values.get("riskCode");//險種
		String riskName=values.get("riskName");//險種名稱
		String kindCode=values.get("kindCode");//條款代碼（險種代碼）
		//String kindName=values.get("kindName");//條款名稱（險種名稱）

		List list = new ArrayList();
		QueryRule queryRule = QueryRule.getInstance();
        /*if(policyType!=null&&!"".equals(policyType)){
        	queryRule.addEqual("policyType", policyType);
        }*/
        if(printSign!=null&&!"".equals(printSign)){
        	queryRule.addEqual("printSign", printSign);
        }
        if(riskCode!=null&&!"".equals(riskCode)){
        	queryRule.addEqual("riskCode", riskCode);
        }
        if(riskName!=null&&!"".equals(riskName)){
        	queryRule.addEqual("riskName", riskName);
        }
        if(kindCode!=null&&!"".equals(kindCode)){
        	queryRule.addEqual("id.clauseCode", kindCode);
        }
        queryRule.addAscOrder("id.clauseCode");
        Page page = super.find(PrpDclauseReport.class, queryRule, pageNo, pageSize);
         List listPage = page.getResult();
		DictPage dictPage=new DictPage();
		dictPage.setPageNo(pageNo);
		dictPage.setPageSize(pageSize);
		dictPage.setPageCount(page.getTotalPageCount());
		dictPage.setTotalRecordCount(page.getTotalCount());
		dictPage.setData(listPage);
		return dictPage;
	}
	@Override
	public DictPage saveCopyNumber(String systemCode, Map values) {
		// TODO Auto-generated method stub
		List<com.sinosoft.dmsdriver.model.PrpDclauseReport> prpDclauseReportList = (List<com.sinosoft.dmsdriver.model.PrpDclauseReport>) values.get("prpDclauseReportList");
		List<PrpDclauseReport> prpDclauseReportListNew = new ArrayList<PrpDclauseReport>();
		String operationType = (String)values.get("operationType");
		for (int i = 0; i < prpDclauseReportList.size(); i++) {
			com.sinosoft.dmsdriver.model.PrpDclauseReport prpDclauseReport = prpDclauseReportList.get(i);
			PrpDclauseReport prpDclauseReportNew   =new PrpDclauseReport();
			PrpDclauseReportId 	prpDclauseReportNewId =  new PrpDclauseReportId();
			prpDclauseReportNewId.setClauseCode(prpDclauseReport.getId().getClauseCode());
			prpDclauseReportNewId.setReportNo(prpDclauseReport.getId().getReportNo());
			prpDclauseReportNewId.setVersionno(prpDclauseReport.getId().getVersionno());
			prpDclauseReportNew.setId(prpDclauseReportNewId);
			prpDclauseReportNew.setRiskCode(prpDclauseReport.getRiskCode());
			prpDclauseReportNew.setValidDate(prpDclauseReport.getValidDate());
			prpDclauseReportNew.setInvalidDate(prpDclauseReport.getInvalidDate());
			prpDclauseReportNew.setAuditFlag("9");
			prpDclauseReportNew.setValidInd(prpDclauseReport.getValidInd());
			prpDclauseReportNew.setRiskName(prpDclauseReport.getRiskName());
			prpDclauseReportNew.setPolicyType(prpDclauseReport.getPolicyType());
			prpDclauseReportNew.setPrintSign(prpDclauseReport.getPrintSign());
			prpDclauseReportNew.setTcol2(prpDclauseReport.getTcol2());
			prpDclauseReportListNew.add(prpDclauseReportNew);
		}
		 /*if(prpDclauseReportListNew.get(0).getTcol2()==null||
		    "".equals(prpDclauseReportListNew.get(0).getTcol2())){
         super.saveAll(prpDclauseReportListNew);
		 }else{
			 super.update(prpDclauseReportListNew.get(0));
		 }*/
		if("insert".equals(operationType)){
		    super.saveAll(prpDclauseReportListNew);
		}else{
			com.sinosoft.dmsdriver.model.PrpDclauseReport prpDclauseReportParam = (com.sinosoft.dmsdriver.model.PrpDclauseReport)values.get("prpDclauseReportParam");
			if(prpDclauseReportList!=null && prpDclauseReportList.size()>=1 && prpDclauseReportParam!=null){
				QueryRule queryRule = QueryRule.getInstance();
				queryRule.addEqual("id.clauseCode", prpDclauseReportParam.getId().getClauseCode());
				queryRule.addEqual("id.reportNo", prpDclauseReportParam.getId().getReportNo());
				List<PrpDclauseReport> list = super.find(PrpDclauseReport.class, queryRule);
				if(list!=null && list.size()>0){
					PrpDclauseReport prpDclauseReportNew  =list.get(0);
					String reportNoOld = prpDclauseReportNew.getId().getReportNo();
					String reportNoNew = prpDclauseReportList.get(0).getId().getReportNo();
					try {
						if(reportNoNew.equals(reportNoOld)){
							BoCopyUtil.convert(prpDclauseReportList.get(0), prpDclauseReportNew, null, null, null);
						    this.update(prpDclauseReportNew);
						}else{
							PrpDclauseReport prpDclauseReportNewTemp = new PrpDclauseReport();
							BoCopyUtil.convert(prpDclauseReportNew, prpDclauseReportNewTemp, null, null, null);
							BoCopyUtil.convert(prpDclauseReportList.get(0), prpDclauseReportNewTemp, null, null, null);
							prpDclauseReportNewTemp.getId().setReportNo(reportNoNew);
							this.delete(prpDclauseReportNew);
							super.save(prpDclauseReportNewTemp);
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
		}
        DictPage dictPage = new DictPage();
 		try {
 			List  list = new ArrayList();
 			dictPage.setData(list);
 			dictPage.setTotalRecordCount(new Long(1));
 			return dictPage;
 		} catch (Exception e) {
 			e.printStackTrace();
 			return null;
 		}
	}

	@Override
	public DictPage getCopyNumberClauseCode(String systemcode,
			Map<String, String> values) {
		// TODO Auto-generated method stub
		String clauseCode=values.get("clauseCode");
		String reportno=values.get("reportno");
		 int pageNo = Integer.parseInt(values.get("pageNo"));
		 int pageSize = Integer.parseInt(values.get("pageSize"));
		List list = new ArrayList();
		QueryRule queryRule = QueryRule.getInstance();
       if(clauseCode!=null&&!"".equals(clauseCode)){
       	queryRule.addEqual("id.clauseCode", clauseCode);
       }
       if(reportno!=null&&!"".equals(reportno)){
          	queryRule.addEqual("id.reportNo", reportno);
          }
       Page page = super.find(PrpDclauseReport.class, queryRule, pageNo, pageSize);
        List listPage = page.getResult();
		DictPage dictPage=new DictPage();
		dictPage.setPageNo(pageNo);
		dictPage.setPageSize(pageSize);
		dictPage.setPageCount(page.getTotalPageCount());
		dictPage.setTotalRecordCount(page.getTotalCount());
		dictPage.setData(listPage);
		return dictPage;
	}

	@Override
	public DictPage getOccupation(String systemcode, Map<String, String> values) {
		// TODO Auto-generated method stub
		String codeCode=values.get("codeCode");
		String reportno=values.get("reportno");
		 int pageNo = Integer.parseInt(values.get("pageNo"));
		 int pageSize = Integer.parseInt(values.get("pageSize"));
			List<PrpDnewCode> list = new ArrayList<PrpDnewCode>();
			QueryRule queryRule = QueryRule.getInstance();
		    if(codeCode!=null&&!"".equals(codeCode)){
		        queryRule.addEqual("id.codeCode",codeCode);
		     }
		       String sql ="";
		       sql="select a.id.codeType,a.id.codeCode,a.codeCName,a.validStatus,a.codeCdesc,a.flag  " +
		       		"  ,a.codeBigCode,a.codeBigName,a.codeMiddleCode,a.codeMiddleName,a.createUser,a.createDate,a.updateUser,a.updateDate   " +
		       		"from  PrpDnewCode a  where a.id.codeType in ('occupationCodeA','occupationCodeB','occupationCodeC')";
		       if(codeCode!=null&&!"".equals(codeCode)){
			        sql = sql +"and  id.codeCode='"+codeCode+"'";
			     }
		        queryRule.addSql(sql);
		        Page page = super.findByHql(sql,pageNo, pageSize);
		        List listPage = page.getResult();
		        
				DictPage dictPage=new DictPage();
				dictPage.setPageNo(pageNo);
				dictPage.setPageSize(pageSize);
				dictPage.setPageCount(page.getTotalPageCount());
				dictPage.setTotalRecordCount(page.getTotalCount());
				dictPage.setData(listPage);

				return dictPage;
	}
	
	@Override
	public DictPage getOccupationById(String systemcode, Map<String, String> values) {
		// TODO Auto-generated method stub
		String codeCode=values.get("codeCode");
		String codeType=values.get("codeType");
		int pageNo = Integer.parseInt(values.get("pageNo"));
		int pageSize = Integer.parseInt(values.get("pageSize"));
		List<PrpDnewCode> list = new ArrayList<PrpDnewCode>();
		QueryRule queryRule = QueryRule.getInstance();
	    String sql ="";
	    sql="select a.id.codeType,a.id.codeCode,a.codeCName,a.validStatus,a.codeCdesc,a.flag  " +
	       		"  ,a.codeBigCode,a.codeBigName,a.codeMiddleCode,a.codeMiddleName,a.createUser,a.createDate,a.updateUser,a.updateDate   " +
	       		"from  PrpDnewCode a  where 1=1";
	    if(codeType!=null&&!"".equals(codeType)){
		    sql = sql +"and  id.codeType='"+codeType+"'";
		}
	    if(codeCode!=null&&!"".equals(codeCode)){
		    sql = sql +"and  id.codeCode='"+codeCode+"'";
		}
	    queryRule.addSql(sql);
	    Page page = super.findByHql(sql,pageNo, pageSize);
	    List listPage = page.getResult();
	    
		DictPage dictPage=new DictPage();
		dictPage.setPageNo(pageNo);
		dictPage.setPageSize(pageSize);
		dictPage.setPageCount(page.getTotalPageCount());
		dictPage.setTotalRecordCount(page.getTotalCount());
		dictPage.setData(listPage);

		return dictPage;
	}
	
	@Override
	public DictPage saveOrUpdateOccupation(String systemCode, Map values) {
		// TODO Auto-generated method stub
		List<com.sinosoft.dmsdriver.model.PrpDnewCode> prpDnewCodes = (List<com.sinosoft.dmsdriver.model.PrpDnewCode>) values.get("prpDnewCodes");
		List<PrpDnewCode> prpDnewCodesNew = new ArrayList<PrpDnewCode>();
		List<PrpDnewCodeRisk>prpDnewCodeRisk = new ArrayList<PrpDnewCodeRisk>();
		for (int i = 0; i < prpDnewCodes.size(); i++) {
			com.sinosoft.dmsdriver.model.PrpDnewCode prpDnewCode = prpDnewCodes.get(i);

			QueryRule queryRule = QueryRule.getInstance();
			queryRule.addEqual("id.codeType", prpDnewCode.getId().getCodeType());
			queryRule.addEqual("id.codeCode", prpDnewCode.getId().getCodeCode());
        	 List<PrpDnewCode> list = super.find(PrpDnewCode.class, queryRule);
        	 if(list!=null&&list.size()>0){
        		 PrpDnewCode prpDnewCodeNew =list.get(0);
        		 prpDnewCodeNew.setCodeCName(prpDnewCode.getCodeCName());
     			prpDnewCodeNew.setValidDate(prpDnewCode.getValidDate());
     			prpDnewCodeNew.setValidStatus(prpDnewCode.getValidStatus());
     			prpDnewCodeNew.setCodeCdesc(prpDnewCode.getCodeCdesc());
     			prpDnewCodeNew.setFlag(prpDnewCode.getFlag());
     			prpDnewCodeNew.setNewCodeCode(prpDnewCode.getNewCodeCode());
     			prpDnewCodeNew.setCodeBigCode(prpDnewCode.getCodeBigCode());
     			prpDnewCodeNew.setCodeBigName(prpDnewCode.getCodeBigName());
     			prpDnewCodeNew.setCodeMiddleCode(prpDnewCode.getCodeMiddleCode());
     			prpDnewCodeNew.setCodeMiddleName(prpDnewCode.getCodeMiddleName());
     			prpDnewCodeNew.setCreateUser(prpDnewCode.getCreateUser());
     			prpDnewCodeNew.setCreateDate(prpDnewCode.getCreateDate());
     			prpDnewCodeNew.setUpdateUser(prpDnewCode.getUpdateUser());
     			prpDnewCodeNew.setUpdateDate(prpDnewCode.getUpdateDate());
     			 super.update(prpDnewCodeNew);
        	 }else{
     			PrpDnewCode prpDnewCodeNew =new  PrpDnewCode();
     	
     			PrpDnewCodeRisk  prpDnewCodeRiskNew = new PrpDnewCodeRisk();
     			PrpDnewCodeRiskId  prpDnewCodeRiskId =new PrpDnewCodeRiskId();
     			prpDnewCodeRiskId.setCodeCode(prpDnewCode.getId().getCodeCode());//新增记录，prpdnewcoderisk 表中增加codecode的值
     			prpDnewCodeRiskId.setCodeType(prpDnewCode.getId().getCodeType());//新增记录，prpdnewcoderisk 表中增加codetype的值
//     			prpDnewCodeRiskId.setCodeCode(prpDnewCodeRiskNew.getId().getCodeCode());//新增记录，prpdnewcoderisk 表中增加codecode的值
//     			prpDnewCodeRiskId.setCodeType(prpDnewCodeRiskNew.getId().getCodeType());//新增记录，prpdnewcoderisk 表中增加codetype的值
     			prpDnewCodeRiskId.setRiskCode("PUB");//新增记录，prpdnewcoderisk 表中增加riskcode的值
    			prpDnewCodeRiskNew.setId(prpDnewCodeRiskId);
    			prpDnewCodeRiskNew.setValidstatus("1");
    			
    			PrpDnewCodeId 	prpDnewCodeId =  new PrpDnewCodeId();
    			
    			prpDnewCodeId.setCodeCode(prpDnewCode.getId().getCodeCode());
    			prpDnewCodeId.setCodeType(prpDnewCode.getId().getCodeType());
    			
    			prpDnewCodeNew.setId(prpDnewCodeId);
    			prpDnewCodeNew.setCodeCName(prpDnewCode.getCodeCName());
    			prpDnewCodeNew.setValidDate(prpDnewCode.getValidDate());
    			prpDnewCodeNew.setValidStatus(prpDnewCode.getValidStatus());
    			prpDnewCodeNew.setCodeCdesc(prpDnewCode.getCodeCdesc());
    			prpDnewCodeNew.setFlag(prpDnewCode.getFlag());
    			prpDnewCodeNew.setNewCodeCode(prpDnewCode.getNewCodeCode());
    			prpDnewCodeNew.setCodeBigCode(prpDnewCode.getCodeBigCode());
     			prpDnewCodeNew.setCodeBigName(prpDnewCode.getCodeBigName());
     			prpDnewCodeNew.setCodeMiddleCode(prpDnewCode.getCodeMiddleCode());
     			prpDnewCodeNew.setCodeMiddleName(prpDnewCode.getCodeMiddleName());
     			prpDnewCodeNew.setCreateUser(prpDnewCode.getCreateUser());
     			prpDnewCodeNew.setCreateDate(prpDnewCode.getCreateDate());
     			prpDnewCodeNew.setUpdateUser(prpDnewCode.getUpdateUser());
     			prpDnewCodeNew.setUpdateDate(prpDnewCode.getUpdateDate());
     			
    			super.save(prpDnewCodeNew);
    			super.save(prpDnewCodeRiskNew);
        	 }
		}
         DictPage dictPage = new DictPage();
 		try {
 			List  list = new ArrayList();
 			dictPage.setData(list);
 			dictPage.setTotalRecordCount(new Long(1));
 			return dictPage;
 		} catch (Exception e) {
 			e.printStackTrace();
 			return null;
 		}
	}
	
	//add by yjm 伤害险险种详细信息查询（通报用） 20150729
	@Override
	public DictPage getClauseInfo(String systemcode, Map<String, String> values) {
		int pageNo = Integer.parseInt(values.get("pageNo"));
		int pageSize = Integer.parseInt(values.get("pageSize"));
		String clauseCode=values.get("clauseCode");
		QueryRule queryRule = QueryRule.getInstance();
		if(clauseCode!=null&&!"".equals(clauseCode)){
        	queryRule.addEqual("clauseCode", clauseCode);
        }
        Page page = super.find(PrpDclause.class, queryRule, pageNo, pageSize);
        List listPage = page.getResult();
		DictPage dictPage=new DictPage();
		dictPage.setPageNo(pageNo);
		dictPage.setPageSize(pageSize);
		dictPage.setPageCount(page.getTotalPageCount());
		dictPage.setTotalRecordCount(page.getTotalCount());
		dictPage.setData(listPage);
		return dictPage;
	}
	//add by lekaifeng 验证業務來源与套装商品是否匹配  20160224
		@Override
		public DictPage cleckSavePolicy(Map<String, String> values) {
			String setCode=values.get("setCode");
			String businessOriginCode=values.get("businessOriginCode");
			QueryRule queryRule = QueryRule.getInstance();
			if(setCode!=null&&!"".equals(setCode)){
	        	queryRule.addEqual("id.setCode", businessOriginCode);
	        }
			if(businessOriginCode!=null&&!"".equals(businessOriginCode)){
	        	queryRule.addEqual("businessOriginCode", setCode);
	        }
	        List page = super.find(PrpDsetChannel.class, queryRule);
			DictPage dictPage=new DictPage();
			dictPage.setTotalRecordCount((long) page.size());
			return dictPage;
		}
	/**
	 * 查詢導入的年份，在年份下拉選中有沒有配置
	 * @return
	 */
	public DictPage findYear(String systemCode,Map<String, String> values){
		String codeType = values.get("codeType");
		String codeCode = values.get("codeCode");
		String codeCName = values.get("codeCode");
		String validStatus = values.get("validStatus");
		String sql = "select a.id.codeCode,a.codeCName from PrpDnewCode a where 1=1";
		
		if(!("".equals(codeCode)||codeCode==null)){
			sql = sql + " and codeCode = '"+codeCode+"'";
		}
		if(!("".equals(codeCName)||codeCName==null)){
			sql = sql + " and codeCName = '"+codeCName+"'";
		}
		if(!("".equals(validStatus)||validStatus==null)){
			sql = sql + " and validStatus = '"+validStatus+"'";
		}
		sql = sql + " and codeType = '"+codeType+"'";
		
		List list = super.findByHql(sql);
		DictPage dictPage = new DictPage();
		dictPage.setData(list);
		return dictPage;
	}
	
	/**
	 * 導入數據中的年份再下拉選中沒配置，則需在下拉選中新增一條年份
	 */
	public void insertNewYear(String systemCode,Map<String, String> values){
		String codeType = values.get("codeType");
		String year = values.get("codeCode");
		String validStatus = values.get("validStatus");
		PrpDnewCode prpDnewCode = new PrpDnewCode();
		PrpDnewCodeId prpDnewCodeId = new PrpDnewCodeId();
		prpDnewCodeId.setCodeCode(year);
		prpDnewCodeId.setCodeType(codeType);
		prpDnewCode.setId(prpDnewCodeId);
		prpDnewCode.setCodeCName(year);
		prpDnewCode.setNewCodeCode(year);
		prpDnewCode.setValidStatus(validStatus);
		super.save(prpDnewCode);
	}
	
	/**
	 * 按條件查找港口代碼
	 * prpDstartPlace
	 * @return
	 */
	public DictPage findprpDstartPlaceByQuery(String systemCode,Map<String, String> values){
		int pageNo = Integer.parseInt(values.get("pageNo"));
		int pageSize = Integer.parseInt(values.get("pageSize"));
		String codeCode = values.get("codeCode");
		String portName = values.get("portName");
		String countries = values.get("countries");
		String sql = "";
		sql = sql + "select a.codeCode,a.portName,a.countries,a.validStatus from PrpDstartPlace a where 1=1 ";
		if(!("".equals(codeCode)||codeCode==null)){
			sql = sql + " and codeCode like '%"+codeCode+"%'";
		}
		if(!("".equals(portName)||portName==null)){
			sql = sql + " and portName like '%"+portName+"%'";
		}
		if(!("".equals(countries)||countries==null)){
			sql = sql + " and countries like '%"+countries+"%'";
		}
		Page page = super.findByHql(sql,pageNo, pageSize);
	    List listPage = page.getResult();
		DictPage dictPage = new DictPage();
		dictPage.setPageNo(pageNo);
		dictPage.setPageSize(pageSize);
		dictPage.setPageCount(page.getTotalPageCount());
		dictPage.setTotalRecordCount(page.getTotalCount());
		dictPage.setData(listPage);
		return dictPage;
	}
	
	/**
	 * 新增前校驗代碼是否重複
	 * @return
	 */
	public DictPage insertCheck(String systemCode,Map<String, String> values){
		String codeCode = values.get("codeCode");
		String sql = "";
		sql = sql + "select a.codeCode from PrpDstartPlace a where codeCode = '"+codeCode+"'";
		List list = super.findByHql(sql);
		DictPage dictPage = new DictPage();
		dictPage.setData(list);
		return dictPage;
	}
	
	/**
	 * 新增港口代碼數據
	 * add by liuyang 20160902
	 */
	public void insertPrpDstartPlace(String systemCode,Map<String, String> values){
		String codeCode = values.get("codeCode");
		String portName = values.get("portName");
		String countries = values.get("countries");
		String validStatus = values.get("validStatus");
		PrpDstartPlace prpDstartPlace = new PrpDstartPlace();
		prpDstartPlace.setCodeCode(codeCode);
		prpDstartPlace.setPortName(portName);
		prpDstartPlace.setCountries(countries);
		prpDstartPlace.setValidStatus(validStatus);
		super.save(prpDstartPlace);
	}
	
	/**
	 * 刪除一條港口代碼
	 * @param systemCode
	 * @param values
	 */
	public void deletePrpDstartPlace(String systemCode,Map<String, String> values){
		String codeCode = values.get("codeCode");
		String sql = "From PrpDstartPlace where codeCode = '"+codeCode+"'";
		List<PrpDstartPlace> list = super.findByHql(sql);
		if(list.size()>0){
			PrpDstartPlace prpDstartPlace = list.get(0);
			super.delete(prpDstartPlace);
		}
	}
	
	/**
	 * 查詢一條港口代碼數據
	 * @param systemCode
	 * @param values
	 * @return
	 */
	public DictPage searchStartPlace(String systemCode,Map<String, String> values){
		String codeCode = values.get("codeCode");
		String sql = "select a.codeCode,a.portName,a.countries,a.validStatus From PrpDstartPlace a where codeCode = '"+codeCode+"'";
		List list = super.findByHql(sql);
		DictPage dictPage = new DictPage();
		dictPage.setData(list);
		return dictPage;
	}
	
	/**
	 * 保存港口代碼修改數據
	 * @param systemCode
	 * @param values
	 */
	public void saveStartPlace(String systemCode,Map<String, String> values){
		String codeCode = values.get("codeCode");
		String portName = values.get("portName");
		String countries = values.get("countries");
		String validStatus = values.get("validStatus");
		PrpDstartPlace prpDstartPlace = new PrpDstartPlace();
		prpDstartPlace.setCodeCode(codeCode);
		prpDstartPlace.setPortName(portName);
		prpDstartPlace.setCountries(countries);
		prpDstartPlace.setValidStatus(validStatus);
		super.update(prpDstartPlace);
	}
	
	
	
}
