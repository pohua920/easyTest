package com.sinosoft.claim.test;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.drools.KnowledgeBase;
import org.drools.runtime.StatefulKnowledgeSession;

import com.sinosoft.claim.audit.vo.ReplevyRuleCondition;
import com.sinosoft.claim.common.service.facade.BillService;
import com.sinosoft.claim.common.service.facade.CodeService;
import com.sinosoft.claim.common.service.facade.PolicyService;
import com.sinosoft.claim.common.service.facade.PrpDcarModelService;
import com.sinosoft.claim.common.service.facade.PrpDclauseKindService;
import com.sinosoft.claim.common.service.facade.PrpDcompanyService;
import com.sinosoft.claim.common.service.facade.PrpDuserService;
import com.sinosoft.claim.schema.model.PrpClimit;
import com.sinosoft.claim.schema.model.PrpDcarModel;
import com.sinosoft.claim.schema.model.PrpDclauseKind;
import com.sinosoft.claim.schema.model.PrpDcode;
import com.sinosoft.claim.schema.model.PrpLdisabilityLimit;
import com.sinosoft.claim.schema.model.PrpLhospital;
import com.sinosoft.claim.schema.model.PrpLpersonLoss;
import com.sinosoft.claim.schema.model.SwfLogId;
import com.sinosoft.claim.schema.service.facade.PrpCinsuredService;
import com.sinosoft.claim.schema.service.facade.PrpClimitService;
import com.sinosoft.claim.schema.service.facade.PrpDcodeService;
import com.sinosoft.claim.schema.service.facade.PrpLdisabilityLimitService;
import com.sinosoft.claim.schema.service.facade.PrpLexternalAgencyService;
import com.sinosoft.claim.schema.service.facade.PrpLhospitalService;
import com.sinosoft.claim.schema.service.facade.PrpLpayObjectService;
import com.sinosoft.claim.schema.service.facade.PrpLpersonLossService;
import com.sinosoft.claim.schema.service.facade.SwfLogService;
import com.sinosoft.claim.ui.control.action.UICodeAction;
import com.sinosoft.claim.undwrt.vo.UndwrtRuleCondition;
import com.sinosoft.one.bpm.aspect.TaskAspect;
import com.sinosoft.one.rule.util.KnowledgeAgentManager;

@SuppressWarnings( { "unchecked", "serial" })
public class Test extends BaseSpringContextTests {
	
	
	public String[] getExtSpringConfigs() {
		return new String[]{
				"test/applicationContext-service-test.xml"};
	}
	public static void main(String[] args) {
		Test test = new Test();
		UndwrtRuleCondition condition = new UndwrtRuleCondition();
		condition.setUserCode("BB079");
		condition.setRiskCode("A01");
		condition.setLeave("6");
		//"undwrtRuleFlow"
		test.executeRules(null, "undwrtChangeSet.xml", condition);
		System.out.println(condition.getResult());
	}
	/**
	 * 转换字符串类型到clazz的property类型的值.
	 * 
	 * @param value 待转换的字符串
	 * @param clazz 提供类型信息的Class
	 * @param propertyName 提供类型信息的Class的属性.
	 */
	public  Object convertValue(Object value, Class<?> toType) {
		try {
			DateConverter dc = new DateConverter();
			dc.setUseLocaleFormat(true);
			dc.setPatterns(new String[] { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss" });
			ConvertUtils.register(dc, Date.class);
			return ConvertUtils.convert(value, toType);
		} catch (Exception e) {
//			throw convertToUncheckedException(e);
			e.printStackTrace();
		}
		Test s = findUnique("","");
		System.out.println(s);
		return null;
	}
	/**
	 * 按HQL查询唯一对象.
	 * 
	 * @param values 数量可变的参数,按顺序绑定.
	 */
	public <X> X findUnique(final String hql, final Object... values) {
		return (X) null;
	}
	
	public Map<String,Set<String>> findClass()throws Exception{
		return null;
	}
	private KnowledgeBase kBase = null;
	private StatefulKnowledgeSession ksession = null;
	private KnowledgeAgentManager kAgentManager = null;

	public void executeRules(String ruleFlowName, String changeSetFilePath, Object... facts) {
		 this.kAgentManager = KnowledgeAgentManager.getInstance();
		this.kBase = this.kAgentManager.getKnowledgeAgent(changeSetFilePath).getKnowledgeBase();
		this.ksession = this.kBase.newStatefulKnowledgeSession();

		for (Object fact : facts) {
			this.ksession.insert(fact);
		}
		if (ruleFlowName != null) {
			this.ksession.startProcess(ruleFlowName);
		}
		this.ksession.fireAllRules();
		this.ksession.dispose();
	}
	
//1.将要测试的bean加入test/applicationContext-service-test.xml，並在本类注入
//2.test()函数中填入测试数据，並调用测试方法，鼠标右键选择 run as jUnit test

	public void test() throws Exception{
		try {
			QueryRule queryRule = QueryRule.getInstance();
			queryRule.addEqual("id.compensateNo", "C50501201100000100014702");
			List<PrpLpersonLoss> prpLpersonLossList = prpLpersonLossService.findPrpLpersonLoss(queryRule);		
			System.out.println(prpLpersonLossList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void print(Object obj) throws Exception{
			
	}

	private PrpDcarModelService prpDcarModelService;
	private PolicyService policyService;
	private SwfLogService swfLogService;
	private CodeService codeService;
	private PrpLdisabilityLimitService prpLdisabilityLimitService;
	private PrpLhospitalService prpLhospitalService;
	private BillService billService;
	private PrpLpersonLossService prpLpersonLossService;
	public PrpDcarModelService getPrpDcarModelService() {
		return prpDcarModelService;
	}

	public void setPrpDcarModelService(PrpDcarModelService prpDcarModelService) {
		this.prpDcarModelService = prpDcarModelService;
	}

	public PrpLhospitalService getPrpLhospitalService() {
		return prpLhospitalService;
	}

	public void setPrpLhospitalService(PrpLhospitalService prpLhospitalService) {
		this.prpLhospitalService = prpLhospitalService;
	}

	public PolicyService getPolicyService() {
		return policyService;
	}

	public void setPolicyService(PolicyService policyService) {
		this.policyService = policyService;
	}

	public SwfLogService getSwfLogService() {
		return swfLogService;
	}

	public void setSwfLogService(SwfLogService swfLogService) {
		this.swfLogService = swfLogService;
	}

	public CodeService getCodeService() {
		return codeService;
	}

	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}

	public PrpLdisabilityLimitService getPrpLdisabilityLimitService() {
		return prpLdisabilityLimitService;
	}

	public void setPrpLdisabilityLimitService(PrpLdisabilityLimitService prpLdisabilityLimitService) {
		this.prpLdisabilityLimitService = prpLdisabilityLimitService;
	}

	public BillService getBillService() {
		return billService;
	}

	public void setBillService(BillService billService) {
		this.billService = billService;
	}

	public PrpLpersonLossService getPrpLpersonLossService() {
		return prpLpersonLossService;
	}

	public void setPrpLpersonLossService(PrpLpersonLossService prpLpersonLossService) {
		this.prpLpersonLossService = prpLpersonLossService;
	}
}
