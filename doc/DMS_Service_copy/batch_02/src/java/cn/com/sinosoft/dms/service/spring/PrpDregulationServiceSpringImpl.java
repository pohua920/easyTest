package cn.com.sinosoft.dms.service.spring;

import ins.framework.common.Page;
import ins.framework.dao.GenericDaoHibernate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.FlushMode;

import cn.com.sinosoft.dms.model.PrpdBasicMedical;
import cn.com.sinosoft.dms.model.PrpdInjuryDefine;
import cn.com.sinosoft.dms.model.PrpdInjuryDuty;
import cn.com.sinosoft.dms.model.PrpdInjuryRate;
import cn.com.sinosoft.dms.model.PrpdRegulation;
import cn.com.sinosoft.dms.model.PrpdbpmMain;
import cn.com.sinosoft.dms.service.facade.PrpDbpmMainService;
import cn.com.sinosoft.dms.service.facade.PrpDregulationService;
import cn.com.sinosoft.saa.util.HqlRulesUtil;

public class PrpDregulationServiceSpringImpl extends
		GenericDaoHibernate<PrpdRegulation, String> implements
		PrpDregulationService {
	private PrpDbpmMainService prpDbpmMainService;

	public Page PrpDregulationList(PrpdRegulation prpdRegulation, int pageNo,
			int pageSize) {
		StringBuffer hql = new StringBuffer();
		hql.append(" from PrpdRegulation prpdRegulation where 1=1 ");
		HqlRulesUtil hqlRules = new HqlRulesUtil();
		//modify by duanfa20110803 start 查询条件加上政策条例类型
		hqlRules.addLike("proviceCode", prpdRegulation.getProviceCode().trim());
		hqlRules.addLike("cityCode", prpdRegulation.getCityCode().trim());
		hqlRules.addLike("countyCode", prpdRegulation.getCountyCode().trim());
		hqlRules.addLike("fileCode", prpdRegulation.getFileCode().trim());
		hqlRules.addLike("fileName", prpdRegulation.getFileName().trim());
		hqlRules.addLike("validStatus", prpdRegulation.getValidStatus().trim());
		hqlRules.addEqual("validDate", prpdRegulation.getValidDate(), false);
		hqlRules.addEqual("regulationType", prpdRegulation.getRegulationType().trim());
		//modify by duanfa20110803 end
		if (hqlRules.getHql().trim() != null
				&& !hqlRules.getHql().trim().equals("")) {
			hql.append("and" + hqlRules.getHql());
		}
		logger.debug("HQL is :" + hql.toString());
		Page page = findByHql(hql.toString(), pageNo, pageSize);

		return page;
	}
	public Page checkPrpDregulationList(PrpdRegulation prpdRegulation, int pageNo,
			int pageSize) {
		StringBuffer hql = new StringBuffer();
		//add by duanfa20110902 无效的也可以查出来
		//hql.append(" from PrpdRegulation prpdRegulation where prpdRegulation.validStatus='1' and prpdRegulation.auditFlag='0' ");
		hql.append(" from PrpdRegulation prpdRegulation where prpdRegulation.auditFlag='0' ");
		HqlRulesUtil hqlRules = new HqlRulesUtil();
		hqlRules.addLike("proviceCode", prpdRegulation.getProviceCode());
		hqlRules.addLike("cityCode", prpdRegulation.getCityCode());
		hqlRules.addLike("countyCode", prpdRegulation.getCountyCode());
		hqlRules.addLike("fileCode", prpdRegulation.getFileCode());
		hqlRules.addLike("fileName", prpdRegulation.getFileName());
		//add by duanfa20110815 查询条件添加政策条例类型
		hqlRules.addLike("regulationType", prpdRegulation.getRegulationType());
		//add by duanfa20110902 查询条件添加有效状态
		hqlRules.addLike("validStatus", prpdRegulation.getValidStatus());
		
		hqlRules.addLike("fileName", prpdRegulation.getFileName());
		hqlRules.addEqual("validDate", prpdRegulation.getValidDate(), false);
		if (hqlRules.getHql().trim() != null
				&& !hqlRules.getHql().trim().equals("")) {
			hql.append("and" + hqlRules.getHql());
		}
		logger.debug("HQL is :" + hql.toString());
		Page page = findByHql(hql.toString(), pageNo, pageSize);
		
		return page;
	}

	public PrpdRegulation findByPrimaryKey(String prpdRegulationCode) {
		PrpdRegulation prpdRegulation = super.get(prpdRegulationCode);
		List<PrpdInjuryDefine> allPrpdInjuryDefines = prpdRegulation
				.getPrpdInjuryDefines();
		List<PrpdInjuryDefine> validPrpdInjuryDefines = new ArrayList<PrpdInjuryDefine>();
		for (PrpdInjuryDefine prpdInjuryDefine : allPrpdInjuryDefines) {
			if (!"0".equals(prpdInjuryDefine.getValidStatus())) {
				validPrpdInjuryDefines.add(prpdInjuryDefine);
			}
		}
		List<PrpdInjuryRate> allPrpdInjuryRates = prpdRegulation
				.getPrpdInjuryRates();
		List<PrpdInjuryRate> validPrpdInjuryRates = new ArrayList<PrpdInjuryRate>();
		for (PrpdInjuryRate prpdInjuryRate : allPrpdInjuryRates) {
			if (!"0".equals(prpdInjuryRate.getValidStatus())) {
				validPrpdInjuryRates.add(prpdInjuryRate);
			}
		}
		List<PrpdInjuryDuty> allPrpdInjuryDuties = prpdRegulation
				.getPrpdInjuryDuties();
		List<PrpdInjuryDuty> validPrpdInjuryDuties = new ArrayList<PrpdInjuryDuty>();
		for (PrpdInjuryDuty prpdInjuryDuty : allPrpdInjuryDuties) {
			if (!"0".equals(prpdInjuryDuty.getValidStatus())) {
				validPrpdInjuryDuties.add(prpdInjuryDuty);
			}
		}
		List<PrpdBasicMedical> allPrpdBasicMedicals = prpdRegulation
				.getPrpdBasicMedicals();
		List<PrpdBasicMedical> validPrpdBasicMedicals = new ArrayList<PrpdBasicMedical>();
		for (PrpdBasicMedical prpdBasicMedical : allPrpdBasicMedicals) {
			if (!"0".equals(prpdBasicMedical.getValidStatus())) {
				validPrpdBasicMedicals.add(prpdBasicMedical);
			}
		}
		prpdRegulation.setPrpdInjuryDefines(validPrpdInjuryDefines);
		prpdRegulation.setPrpdInjuryRates(validPrpdInjuryRates);
		prpdRegulation.setPrpdInjuryDuties(validPrpdInjuryDuties);
		prpdRegulation.setPrpdBasicMedicals(validPrpdBasicMedicals);
		return prpdRegulation;
	}

	public void insertPrpdRegulation(String usercode,
			PrpdRegulation prpdRegulation,
			List<PrpdInjuryDefine> prpdInjuryDefines,
			List<PrpdInjuryRate> prpdInjuryRates,
			List<PrpdInjuryDuty> prpdInjuryDuties,
			List<PrpdBasicMedical> prpdBasicMedicals) {
		prpdRegulation.setRegulationCode(prpdRegulation.getRegulationType()
				+ getDataCount("PrpdRegulation",0));
		int prpdInjuryDefinesIndex = 0;
		int prpdInjuryRatesIndex = 0;
		int prpdInjuryDutiesIndex = 0;
		int prpdBasicMedicalsIndex = 0;
		for (int i = 0; i < prpdInjuryDefines.size(); i++) {
			if ("".equals(prpdInjuryDefines.get(i).getInjuryDefineCode())
					|| prpdInjuryDefines.get(i).getInjuryDefineCode() == null) {
				prpdInjuryDefines.get(i).setInjuryDefineCode(
						"I" + getDataCount("PrpdInjuryDefine",prpdInjuryDefinesIndex++));
				prpdInjuryDefines.get(i).setPrpdRegulation(prpdRegulation);
				prpdInjuryDefines.get(i).setValidStatus("1");
				//delete by duanfa 20110728
				//super.save(prpdInjuryDefines.get(i));
			} else {
				prpdInjuryDefines.get(i).setPrpdRegulation(prpdRegulation);
				prpdInjuryDefines.get(i).setValidStatus("1");
				//delete by duanfa 20110728
				//super.update(prpdInjuryDefines.get(i));
			}
		}

		for (int i = 0; i < prpdInjuryRates.size(); i++) {
			if ("".equals(prpdInjuryRates.get(i).getInjuryRateCode())
					|| prpdInjuryRates.get(i).getInjuryRateCode() == null) {
				prpdInjuryRates.get(i).setInjuryRateCode(
						"I" + getDataCount("PrpdInjuryRate",prpdInjuryRatesIndex++));
				prpdInjuryRates.get(i).setPrpdRegulation(prpdRegulation);
				prpdInjuryRates.get(i).setValidStatus("1");
				//delete by duanfa 20110728
				//super.save(prpdInjuryRates.get(i));
			} else {
				prpdInjuryRates.get(i).setPrpdRegulation(prpdRegulation);
				prpdInjuryRates.get(i).setValidStatus("1");
				//delete by duanfa 20110728
				//super.update(prpdInjuryRates.get(i));
			}
		}
		for (int i = 0; i < prpdInjuryDuties.size(); i++) {
			if ("".equals(prpdInjuryDuties.get(i).getInjuryDutyCode())
					|| prpdInjuryDuties.get(i).getInjuryDutyCode() == null) {
				prpdInjuryDuties.get(i).setInjuryDutyCode(
						"I" + getDataCount("PrpdInjuryDuty",prpdInjuryDutiesIndex++));
				prpdInjuryDuties.get(i).setPrpdRegulation(prpdRegulation);
				prpdInjuryDuties.get(i).setValidStatus("1");
				//delete by duanfa 20110728
				//super.save(prpdInjuryDuties.get(i));
			} else {
				prpdInjuryDuties.get(i).setPrpdRegulation(prpdRegulation);
				prpdInjuryDuties.get(i).setValidStatus("1");
				//delete by duanfa 20110728
				//super.update(prpdInjuryDuties.get(i));
			}
		}
		for (int i = 0; i < prpdBasicMedicals.size(); i++) {
			if ("".equals(prpdBasicMedicals.get(i).getBasicMedicalCode())
					|| prpdBasicMedicals.get(i).getBasicMedicalCode() == null) {
				prpdBasicMedicals.get(i).setBasicMedicalCode(
						"B" + getDataCount("PrpdBasicMedical",prpdBasicMedicalsIndex++));
				prpdBasicMedicals.get(i).setPrpdRegulation(prpdRegulation);
				prpdBasicMedicals.get(i).setValidStatus("1");
				//delete by duanfa 20110728
				//super.save(prpdBasicMedicals.get(i));
			} else {
				prpdBasicMedicals.get(i).setPrpdRegulation(prpdRegulation);
				prpdBasicMedicals.get(i).setValidStatus("1");
				//delete by duanfa 20110728
//				super.save(prpdBasicMedicals.get(i));
			}
		}
       //add by duanfa 20110728 start
		super.saveAll(prpdInjuryDefines);
		super.saveAll(prpdInjuryRates);
		super.saveAll(prpdInjuryDuties);
		super.saveAll(prpdBasicMedicals);
		//add by duanfa 20110728 end
		
		prpdRegulation.setPrpdInjuryDefines(prpdInjuryDefines);
		prpdRegulation.setPrpdInjuryRates(prpdInjuryRates);
		prpdRegulation.setPrpdInjuryDuties(prpdInjuryDuties);
		prpdRegulation.setPrpdBasicMedicals(prpdBasicMedicals);
		prpdRegulation.setAuditFlag("0");
		super.save(prpdRegulation);
		PrpdbpmMain prpdbpmMain = new PrpdbpmMain();
		prpdbpmMain.setTaskId(prpdRegulation.getRegulationCode());
		prpdbpmMain.setBusinessName(prpdRegulation.getFileName());
		prpdbpmMain.setBusinessNo(prpdRegulation.getFileCode());
		prpdbpmMain.setState("0");
		prpdbpmMain.setBusinessType(prpdRegulation.getRegulationType());
		prpdbpmMain.setInDate(new Date());
		prpdbpmMain.setReferUser(usercode);
		prpdbpmMain.setBusinessState("1");
		super.save(prpdbpmMain);
	}

	public void updatePrpdRegulation(String usercode,
			PrpdRegulation prpdRegulation,
			List<PrpdInjuryDefine> prpdInjuryDefines,
			List<PrpdInjuryRate> prpdInjuryRates,
			List<PrpdInjuryDuty> prpdInjuryDuties,
			List<PrpdBasicMedical> prpdBasicMedicals) {
		int prpdInjuryDefinesIndex = 0;
		int prpdInjuryRatesIndex = 0;
		int prpdInjuryDutiesIndex = 0;
		int prpdBasicMedicalsIndex = 0;
		//第一步，先将页面删除的全部置为无效
		
		String exceptIds = "";
		String hql = "";
		//modify by duanfa 20110728 start 全部删除时不成功
		if(prpdInjuryDefines.size()>0){
			for(PrpdInjuryDefine injuryDefine: prpdInjuryDefines){
				exceptIds = exceptIds+"'"+injuryDefine.getInjuryDefineCode()+"',";
			}
			exceptIds = exceptIds.substring(0, exceptIds.length()-1);
			hql = "from PrpdInjuryDefine injuryDefine where injuryDefine.validStatus=1 and injuryDefine.prpdRegulation.regulationCode = '"+prpdRegulation.getRegulationCode()+"'  and injuryDefine.injuryDefineCode  not in ("+exceptIds+")";
		}else{
			hql = "from PrpdInjuryDefine injuryDefine where injuryDefine.validStatus=1 and injuryDefine.prpdRegulation.regulationCode = '"+prpdRegulation.getRegulationCode()+"' ";
		}
		List<PrpdInjuryDefine> prpdInjuryDefineOlds = super.findByHql(hql);
		for(PrpdInjuryDefine injuryDefine: prpdInjuryDefineOlds){
			injuryDefine.setValidStatus("0");
			super.update(injuryDefine);
		}
		if(prpdInjuryRates.size()>0){
			exceptIds = "";
			for(PrpdInjuryRate injuryRate: prpdInjuryRates){
				exceptIds = exceptIds+"'"+injuryRate.getInjuryRateCode()+"',";
			}
			exceptIds = exceptIds.substring(0, exceptIds.length()-1);
			hql = "from PrpdInjuryRate injuryRate where injuryRate.validStatus=1 and injuryRate.prpdRegulation.regulationCode = '"+prpdRegulation.getRegulationCode()+"'  and injuryRate.injuryRateCode  not in ("+exceptIds+")";
		}else{
			hql = "from PrpdInjuryRate injuryRate where injuryRate.validStatus=1 and injuryRate.prpdRegulation.regulationCode = '"+prpdRegulation.getRegulationCode()+"' ";
		}
		List<PrpdInjuryRate> prpdInjuryRateOlds = super.findByHql(hql);
		for(PrpdInjuryRate injuryRate: prpdInjuryRateOlds){
			injuryRate.setValidStatus("0");
			super.update(injuryRate);
		}
		if(prpdInjuryDuties.size()>0){
			exceptIds = "";
			for(PrpdInjuryDuty injuryDuty: prpdInjuryDuties){
				exceptIds = exceptIds+"'"+injuryDuty.getInjuryDutyCode()+"',";
			}
			exceptIds = exceptIds.substring(0, exceptIds.length()-1);
			hql = "from PrpdInjuryDuty injuryDuty where injuryDuty.validStatus=1 and injuryDuty.prpdRegulation.regulationCode = '"+prpdRegulation.getRegulationCode()+"' and injuryDuty.injuryDutyCode  not in ("+exceptIds+")";
		}else{
			hql = "from PrpdInjuryDuty injuryDuty where injuryDuty.validStatus=1 and injuryDuty.prpdRegulation.regulationCode = '"+prpdRegulation.getRegulationCode()+"' ";
		}
		List<PrpdInjuryDuty> prpdInjuryDutieOlds = super.findByHql(hql);
		for(PrpdInjuryDuty injuryDuty: prpdInjuryDutieOlds){
			injuryDuty.setValidStatus("0");
			super.update(injuryDuty);
		}
		if(prpdBasicMedicals.size()>0){
			exceptIds = "";
			for(PrpdBasicMedical basicMedical: prpdBasicMedicals){
				exceptIds = exceptIds+"'"+basicMedical.getBasicMedicalCode()+"',";
			}
			exceptIds = exceptIds.substring(0, exceptIds.length()-1);
			hql = "from PrpdBasicMedical basicMedical where basicMedical.validStatus=1 and basicMedical.prpdRegulation.regulationCode = '"+prpdRegulation.getRegulationCode()+"' and basicMedical.basicMedicalCode  not in ("+exceptIds+")";
		}else{
			hql = "from PrpdBasicMedical basicMedical where basicMedical.validStatus=1 and basicMedical.prpdRegulation.regulationCode = '"+prpdRegulation.getRegulationCode()+"' ";
		}
		List<PrpdBasicMedical> prpdBasicMedicalOlds = super.findByHql(hql);
		for(PrpdBasicMedical basicMedical: prpdBasicMedicalOlds){
			basicMedical.setValidStatus("0");
			super.update(basicMedical);
		}
		//modify by duanfa 20110728 end 全部删除时不成功
		for (int i = 0; i < prpdInjuryDefines.size(); i++) {
			if ("".equals(prpdInjuryDefines.get(i).getInjuryDefineCode())
					|| prpdInjuryDefines.get(i).getInjuryDefineCode() == null) {
				prpdInjuryDefines.get(i).setInjuryDefineCode(
						"I" + getDataCount("PrpdInjuryDefine",prpdInjuryDefinesIndex++));
				prpdInjuryDefines.get(i).setPrpdRegulation(prpdRegulation);
				prpdInjuryDefines.get(i).setValidStatus("1");
				//delete by duanfa 20110728
				//super.save(prpdInjuryDefines.get(i));
			} else {
				prpdInjuryDefines.get(i).setPrpdRegulation(prpdRegulation);
				prpdInjuryDefines.get(i).setValidStatus("1");
				//delete by duanfa 20110728
				//super.update(prpdInjuryDefines.get(i));
			}
		}

		for (int i = 0; i < prpdInjuryRates.size(); i++) {
			if ("".equals(prpdInjuryRates.get(i).getInjuryRateCode())
					|| prpdInjuryRates.get(i).getInjuryRateCode() == null) {
				prpdInjuryRates.get(i).setInjuryRateCode(
						"I" + getDataCount("PrpdInjuryRate",prpdInjuryRatesIndex++));
				prpdInjuryRates.get(i).setPrpdRegulation(prpdRegulation);
				prpdInjuryRates.get(i).setValidStatus("1");
				//delete by duanfa 20110728
				//super.save(prpdInjuryRates.get(i));
			} else {
				prpdInjuryRates.get(i).setPrpdRegulation(prpdRegulation);
				prpdInjuryRates.get(i).setValidStatus("1");
				//delete by duanfa 20110728
				//super.update(prpdInjuryRates.get(i));
			}
		}
		for (int i = 0; i < prpdInjuryDuties.size(); i++) {
			if ("".equals(prpdInjuryDuties.get(i).getInjuryDutyCode())
					|| prpdInjuryDuties.get(i).getInjuryDutyCode() == null) {
				prpdInjuryDuties.get(i).setInjuryDutyCode(
						"I" + getDataCount("PrpdInjuryDuty",prpdInjuryDutiesIndex++));
				prpdInjuryDuties.get(i).setPrpdRegulation(prpdRegulation);
				prpdInjuryDuties.get(i).setValidStatus("1");
				//delete by duanfa 20110728
				//super.save(prpdInjuryDuties.get(i));
			} else {
				prpdInjuryDuties.get(i).setPrpdRegulation(prpdRegulation);
				prpdInjuryDuties.get(i).setValidStatus("1");
				//delete by duanfa 20110728
				//super.update(prpdInjuryDuties.get(i));
			}
		}
		for (int i = 0; i < prpdBasicMedicals.size(); i++) {
			if ("".equals(prpdBasicMedicals.get(i).getBasicMedicalCode())
					|| prpdBasicMedicals.get(i).getBasicMedicalCode() == null) {
				prpdBasicMedicals.get(i).setBasicMedicalCode(
						"B" + getDataCount("PrpdBasicMedical",prpdBasicMedicalsIndex++));
				prpdBasicMedicals.get(i).setPrpdRegulation(prpdRegulation);
				prpdBasicMedicals.get(i).setValidStatus("1");
				//delete by duanfa 20110728
				//super.save(prpdBasicMedicals.get(i));
			} else {
				prpdBasicMedicals.get(i).setPrpdRegulation(prpdRegulation);
				prpdBasicMedicals.get(i).setValidStatus("1");
				//delete by duanfa 20110728
				//super.save(prpdBasicMedicals.get(i));
			}
		}
		 //add by duanfa 20110728 start
		super.saveAll(prpdInjuryDefines);
		super.saveAll(prpdInjuryRates);
		super.saveAll(prpdInjuryDuties);
		super.saveAll(prpdBasicMedicals);
		//add by duanfa 20110728 end
		prpdRegulation.setAuditFlag("0");
		super.update(prpdRegulation);
		PrpdbpmMain prpdbpmMain = prpDbpmMainService.findByPropertyName(
				"taskId", prpdRegulation.getRegulationCode());
		prpdbpmMain.setBusinessName(prpdRegulation.getFileName());
		prpdbpmMain.setBusinessNo(prpdRegulation.getFileCode());
		prpdbpmMain.setState("0");
		prpdbpmMain.setBusinessType(prpdRegulation.getRegulationType());
		prpdbpmMain.setInDate(new Date());
		prpdbpmMain.setReferUser(usercode);
		//mofify by duanfa20110815
		if(prpdbpmMain.getBusinessState().equals("4")){
			prpdbpmMain.setBusinessState("5");
		}else {
			prpdbpmMain.setBusinessState("3");
		}
		super.update(prpdbpmMain);
	}
	//modify by duanfa20110915
	public void changeRegulationStatus(String userCode,String regulationCode) {
		PrpdRegulation prpdRegulation = super.get(regulationCode);
		if ("0".equals(prpdRegulation.getValidStatus())) {
			prpdRegulation.setValidStatus("1");
		} else {
			prpdRegulation.setValidStatus("0");
		}
		prpdRegulation.setAuditFlag("0");
		this.update(prpdRegulation);
		PrpdbpmMain prpdbpmMain = prpDbpmMainService.findByPropertyName(
				"taskId", prpdRegulation.getRegulationCode());
		prpdbpmMain.setBusinessName(prpdRegulation.getFileName());
		prpdbpmMain.setBusinessNo(prpdRegulation.getFileCode());
		prpdbpmMain.setState("0");
		prpdbpmMain.setBusinessType(prpdRegulation.getRegulationType());
		prpdbpmMain.setInDate(new Date());
		prpdbpmMain.setReferUser(userCode);
		//mofify by duanfa20110815
		if(prpdbpmMain.getBusinessState().equals("4")){
			prpdbpmMain.setBusinessState("5");
		}else {
			prpdbpmMain.setBusinessState("3");
		}
		super.update(prpdbpmMain);

	}

	private String getDataCount(String ClassName,int i) {
		Long l = super.getCount("select count(o) from " + ClassName
				+ " o where 'a'=?", "a");
		l = l + 10000000+i;
		return (l + "").substring(1);
	}

	public PrpDbpmMainService getPrpDbpmMainService() {
		return prpDbpmMainService;
	}

	public void setPrpDbpmMainService(PrpDbpmMainService prpDbpmMainService) {
		this.prpDbpmMainService = prpDbpmMainService;
	}
//add by duanfa 2011-06-16
	@SuppressWarnings("unchecked")
	public void checkPassAll(String[] regulationCodes, String comments) {
		String codes = "";
		for (String regulationCode : regulationCodes) {
			codes = codes + "'";
			//modify by duanfa20110806 
			//codes = codes + regulationCode.trim();
			codes = codes + regulationCode.trim();
			codes = codes + "',";
		}
		codes = codes+"''";
		String hql = "select bpmMain from PrpdbpmMain bpmMain where bpmMain.taskId in ("+codes+")";
		List<PrpdbpmMain> prpdbpmMains = super.findByHql(hql);
		for(PrpdbpmMain prpdbpmMain:prpdbpmMains){
			prpdbpmMain.setComments(comments);
			prpdbpmMain.setBusinessState("9");
		}
		hql = "select regulation from PrpdRegulation regulation where regulation.regulationCode in ("+codes+")";
		List<PrpdRegulation> prpdRegulations = super.findByHql(hql);
		for(PrpdRegulation prpdRegulation:prpdRegulations){
			prpdRegulation.setAuditFlag("1");
		}
		this.getSession().setFlushMode(FlushMode.AUTO);
		super.saveAll(prpdRegulations);
		super.saveAll(prpdbpmMains);
		this.getSession().flush();
	}
	//add by duanfa 2011-06-16
	@SuppressWarnings("unchecked")
	public void checkRejectAll(String[] regulationCodes, String comments) {
		String codes = "";
		for (String regulationCode : regulationCodes) {
			codes = codes + "'";
			//modify by duanfa 20110806
//			codes = codes + regulationCode;
			codes = codes + regulationCode.trim();
			codes = codes + "',";
		}
		codes = codes+"''";
		String hql = "select bpmMain from PrpdbpmMain bpmMain where bpmMain.taskId in ("+codes+")";
		List<PrpdbpmMain> prpdbpmMains = super.findByHql(hql);
		for(PrpdbpmMain prpdbpmMain:prpdbpmMains){
			prpdbpmMain.setComments(comments);
			prpdbpmMain.setBusinessState("4");
		}
		hql = "select regulation from PrpdRegulation regulation where regulation.regulationCode in ("+codes+")";
		List<PrpdRegulation> prpdRegulations = super.findByHql(hql);
		for(PrpdRegulation prpdRegulation:prpdRegulations){
			prpdRegulation.setAuditFlag("2");
		}
		this.getSession().setFlushMode(FlushMode.AUTO);
		super.saveAll(prpdRegulations);
		super.saveAll(prpdbpmMains);
		this.getSession().flush();
	}
}
