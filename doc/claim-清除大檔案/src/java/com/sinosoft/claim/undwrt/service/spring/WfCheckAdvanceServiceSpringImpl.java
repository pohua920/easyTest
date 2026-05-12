/*
 * @(#)BLWfCheckAdvanceFacade.java	Feb 21, 2013
 *
 * @Company < Technology Development Company LTD..>
 */
package com.sinosoft.claim.undwrt.service.spring;

import ins.framework.dao.GenericDaoHibernate;

import java.util.ArrayList;
import java.util.List;

import com.sinosoft.claim.common.ConstantCodes;
import com.sinosoft.claim.common.service.facade.PolicyService;
import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schema.model.PrpLcompensate;
import com.sinosoft.claim.schema.model.PrpLprepay;
import com.sinosoft.claim.schema.service.facade.PrpLcompensateService;
import com.sinosoft.claim.schema.service.facade.PrpLprepayService;
import com.sinosoft.claim.schema.service.facade.WfLogService;
import com.sinosoft.claim.undwrt.service.facade.SwfPathForAdvanceService;
import com.sinosoft.claim.undwrt.service.facade.WfCheckAdvanceService;
import com.sinosoft.claim.undwrt.vo.UndwrtRelationCondition;
import com.sinosoft.claim.undwrt.vo.UndwrtRuleCondition;
import com.sinosoft.one.rule.service.facade.DroolsRuleService;
import com.sinosoft.sysframework.exceptionlog.UserException;

/**
 * @Project <CL-Allocation tool>
 * @version <1.0>
 * @author 中科软
 * @description
 */
@SuppressWarnings("unchecked")
public class WfCheckAdvanceServiceSpringImpl extends GenericDaoHibernate implements WfCheckAdvanceService {

	private SwfPathForAdvanceService swfPathForAdvanceService;
	private DroolsRuleService droolsRuleService;
	private PrpLprepayService prpLprepayService;
	private PrpLcompensateService prpLcompensateService;
	private PolicyService policyService;
	private WfLogService wfLogService;

	/***
	 * 核赔提交审核通过处理
	 * @param ModelNo 核赔工作流模板号码 
	 * @param StartNodeNo 起始节点
	 * @param BusinessType 业务类型
	 * @param BusinessNo 业务号码
	 * @param DefaultFlag 是否结束节点（1：结束节点）
	 * @param userCode 提交人员代码
	 * @return 
	 */
	public boolean checkAdvanceCondition(int ModelNo, int StartNodeNo, String BusinessType, String BusinessNo, String DefaultFlag, String userCode) throws Exception {
		boolean hasPath = true;
		try {
			UndwrtRuleCondition condition = this.getUndwrtRuleCondition(ModelNo, StartNodeNo, BusinessType, BusinessNo, userCode);
			droolsRuleService.executeRules("undwrtRuleFlow", "undwrtChangeSet.xml", condition);
			if (!condition.getRulesCheckFlag()) {
				hasPath = false;
//					throw new UserException(-1, -1, "核賠權限效驗失敗","您沒有配置該險別的權限，請通知管理員配置。");
			} else if (!condition.getResult()) {
				hasPath =  false;
//				throw new UserException(-1, -1, "核賠權限效驗失敗", condition.getStrResultMessage());
			}
		} catch (UserException e) {
			e.printStackTrace();
			throw new UserException(-1, -1, "核賠權限效驗失敗", e.getErrorMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new UserException(-1, -1, "核賠權限效驗失敗", e.getMessage());
		}
//		} else {
//			hasPath = swfPathForAdvanceService.getAdvancePathes(ModelNo, StartNodeNo, BusinessType, BusinessNo, DefaultFlag, userCode);
//		}
		return hasPath;
	}

	/***
	 * 
	 * @Description: 根据当前业务数据组织标准的核赔规则判断条件（用于规则引擎校验）
	 * @param modelNo 核赔工作流模板号码
	 * @param startNodeNo 提交节点号
	 * @param businessType 业务类型
	 * @param businessNo 业务号码
	 * @param userCode 人员代码
	 * @return
	 * @throws Exception
	 */
	public UndwrtRuleCondition getUndwrtRuleCondition(int modelNo, int startNodeNo, String businessType, String businessNo, String userCode) throws Exception {
		String riskCode = "";
		String comCode = "";
		double sumPaid = 0D;
		int realPayFlag = 1;
		double sumSumPaid = 0D;
		UndwrtRuleCondition condition = new UndwrtRuleCondition();
		// 预陪业务
		if ("Y".equals(businessType)) {
			PrpLprepay prpLprepay = prpLprepayService.findPrpLprepay(businessNo);
			if (prpLprepay == null) {
				throw new UserException(-1, -1, "核賠權限效驗失敗","預陪沒有查詢到相關的業務數據，請重新選擇");
			}
			riskCode = prpLprepay.getRiskCode();
			//comCode = prpLprepay.getMakeCom();
			sumPaid = new Double(prpLprepay.getSumPrePaid());
			// 折算为人民币的金额进行权限控制
			if (prpLprepay.getExchangeRate() != 0 && prpLprepay.getExchangeRate() != 1) {
				sumPaid = sumPaid * prpLprepay.getExchangeRate();
			}
			realPayFlag = this.checkPay(prpLprepay.getPolicyNo());
			
			String sql = "select sum(SumPrePaid) from prpLprepay where claimNo='"+prpLprepay.getClaimNo()+"'";
			List<?> result= HibernateUtils.findbySql(getSession(), sql);
			sumSumPaid = Double.parseDouble(result.get(0).toString()); 
		} else if ("C".equals(businessType)) {
			// 实赔业务
			PrpLcompensate prpLcompensate = prpLcompensateService.findPrpLcompensate(businessNo);
			if (prpLcompensate == null) {
				throw new UserException(-1, -1, "核賠權限效驗失敗", "实赔沒有查詢到相關的業務數據，請重新選擇");
			}
			riskCode = prpLcompensate.getRiskCode();
			//comCode = prpLcompensate.getMakeCom();
			sumPaid = new Double(prpLcompensate.getSumThisPaid());
			// 折算为人民币的金额进行权限控制
			if (prpLcompensate.getExchangeRate() != 0 && prpLcompensate.getExchangeRate() != 1) {
				sumPaid = sumPaid * prpLcompensate.getExchangeRate();
			}
			realPayFlag = this.checkPay(prpLcompensate.getPolicyNo());
			
//			String sql = "select sum(SumPaid) from prpLcompensate where compensateNo like 'C"+prpLcompensate.getClaimNo()+"%'";
//			包含独立处理费用
			String sql = "select sum(SumPaid)+sum(independentCosts) from prpLcompensate where ( compensateNo like 'C"+prpLcompensate.getClaimNo()+"%' or compensateNo like 'D"+prpLcompensate.getClaimNo()+"%')";
			List<?> result= HibernateUtils.findbySql(getSession(), sql);
			sumSumPaid = Double.parseDouble(result.get(0).toString()); 
			this.findRelationSumPaid(prpLcompensate, condition);
			sql = "select simpleFlag from prpLclaim where claimNo = '"+prpLcompensate.getClaimNo()+"'";
			result= HibernateUtils.findbySql(getSession(), sql);
			if(result.size()>0){
				condition.setSimpelFlag(String.valueOf(result.get(0)));
			}
			condition.setSimpleSumPaid(sumSumPaid);
		}
		String wfLogSql = "select comcode from wflog where businessNo='" + businessNo + "' and logno = 1 ";
		List<?> wfLogResult= HibernateUtils.findbySql(getSession(), wfLogSql);
		comCode =  wfLogResult.get(0).toString();
		condition.setUserCode(userCode);
		condition.setLeave(String.valueOf(startNodeNo));
		condition.setSumPaid(sumPaid);
		condition.setComCode(comCode);
		condition.setRiskCode(riskCode);
		condition.setUwType(businessType);
		condition.setRealPayFlag(realPayFlag);
		condition.setSumSumPaid(sumSumPaid+condition.getSumSumPaid());
		return condition;
	}
	/**
	 * 查询关联险别
	 * @param prpLcompensate
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public UndwrtRuleCondition findRelationSumPaid(PrpLcompensate prpLcompensate,UndwrtRuleCondition condition)throws Exception{
		if("E".equals(ConstantCodes.carClassMap.get(prpLcompensate.getRiskCode()))){
			List<UndwrtRelationCondition> list = new ArrayList<UndwrtRelationCondition>();
			//查询HP险种
			String relationKind = "HP";
			Object[] objs = this.findRiskRelationKind(prpLcompensate.getClaimNo(),relationKind);
			UndwrtRelationCondition undwrtRelationCondition = null;
			if((Boolean) objs[0]){
				undwrtRelationCondition = new UndwrtRelationCondition();
				undwrtRelationCondition.setKindCode(relationKind);
				undwrtRelationCondition.setSumSumPaid((Double) objs[1]);
				condition.setSumSumPaid(condition.getSumSumPaid()-undwrtRelationCondition.getSumSumPaid());
				list.add(undwrtRelationCondition);
			}
			//查询HG险种
			relationKind = "HG";
			objs = this.findRiskRelationKind(prpLcompensate.getClaimNo(),relationKind);
			if((Boolean) objs[0]){
				undwrtRelationCondition = new UndwrtRelationCondition();
				undwrtRelationCondition.setKindCode(relationKind);
				undwrtRelationCondition.setSumSumPaid((Double) objs[1]);
				condition.setSumSumPaid(condition.getSumSumPaid()-undwrtRelationCondition.getSumSumPaid());
				list.add(undwrtRelationCondition);
			}
			if(list.size()>0){
				condition.setUndwrtRelationList(list);
			}
		}
		return condition;
	}
	
	/**
	 * 判断是否有拆分险种，和拆分险种的金额
	 * @param claimNo
	 * @param riskCode
	 * @return
	 * @throws Exception
	 */
	public Object[] findRiskRelationKind(String claimNo,String riskCode)throws Exception{
		String sql = "select sum(sumRealPay*exchRate) from prpLloss where compensateNo like 'C"+claimNo+"%' and kindcode in (select codeCode from prpDcode where codeType='RiskRelationKind' and upperCode = '"+riskCode+"' and validstatus='1')";
		List list = HibernateUtils.findbySql(super.getSession(), sql);
		double sumPaid = 0D;
		boolean flag = false;
		if(list.size()>0&&list.get(0)!=null){
			flag = true;
			sumPaid += new Double(list.get(0).toString());
		}
		sql = "select sum(sumRealPay*exchRate) from prpLpersonloss where compensateNo like 'C"+claimNo+"%' and kindcode in (select codeCode from prpDcode where codeType='RiskRelationKind' and upperCode = '"+riskCode+"' and validstatus='1')";
		list = HibernateUtils.findbySql(super.getSession(), sql);
		if(list.size()>0&&list.get(0)!=null){
			flag = true;
			sumPaid += new Double(list.get(0).toString());
		}
		sql = "select sum(chargeAmount*exchRate) from prpLcharge where compensateNo like 'C"+claimNo+"%' and kindcode in (select codeCode from prpDcode where codeType='RiskRelationKind' and upperCode = '"+riskCode+"' and validstatus='1')";
		list = HibernateUtils.findbySql(super.getSession(), sql);
		if(list.size()>0&&list.get(0)!=null){
			flag = true;
			sumPaid += new Double(list.get(0).toString());
		}
		Object[] obj = {flag,sumPaid};
		return obj;
	}
	/**
	 * 检查缴费标志 返回值 int -1为未缴费，0为未缴全，1为缴全
	 * @param httpServletRequest 返回给页面的request
	 * @param policyNo 立案号
	 * @throws Exception
	 */
	public int checkPay(String policyNo) throws Exception {
		String conditions = " policyno = '" + policyNo + "'";
		int intReturn = policyService.checkPay(conditions);
		return intReturn;
	}
	
	public SwfPathForAdvanceService getSwfPathForAdvanceService() {
		return swfPathForAdvanceService;
	}

	public void setSwfPathForAdvanceService(SwfPathForAdvanceService swfPathForAdvanceService) {
		this.swfPathForAdvanceService = swfPathForAdvanceService;
	}

	public DroolsRuleService getDroolsRuleService() {
		return droolsRuleService;
	}

	public void setDroolsRuleService(DroolsRuleService droolsRuleService) {
		this.droolsRuleService = droolsRuleService;
	}

	public PrpLprepayService getPrpLprepayService() {
		return prpLprepayService;
	}

	public void setPrpLprepayService(PrpLprepayService prpLprepayService) {
		this.prpLprepayService = prpLprepayService;
	}

	public PrpLcompensateService getPrpLcompensateService() {
		return prpLcompensateService;
	}

	public void setPrpLcompensateService(PrpLcompensateService prpLcompensateService) {
		this.prpLcompensateService = prpLcompensateService;
	}

	public PolicyService getPolicyService() {
		return policyService;
	}

	public void setPolicyService(PolicyService policyService) {
		this.policyService = policyService;
	}

	public WfLogService getWfLogService() {
		return wfLogService;
	}

	public void setWfLogService(WfLogService wfLogService) {
		this.wfLogService = wfLogService;
	}

}
