package com.sinosoft.claim.schema.service.spring;
/**
 * 特殊赔案申请原因信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLSpecialCaseReason;
import com.sinosoft.claim.schema.model.PrpLSpecialCaseReasonId;
import com.sinosoft.claim.schema.service.facade.PrpLSpecialCaseReasonService;
import com.sinosoft.one.bpm.aspect.ProcessTask;

public class PrpLSpecialCaseReasonServiceSpringImpl extends
GenericDaoHibernate<PrpLSpecialCaseReason, PrpLSpecialCaseReasonId> implements PrpLSpecialCaseReasonService{

	@Override
	public void save(PrpLSpecialCaseReason prpLSpecialCaseReason) throws Exception {
		logger.info("保存特殊赔案申请原因信息");
		super.save(prpLSpecialCaseReason);
	}
	/**
	 * 申请特殊赔案，，保存带jbpm工作流信息
	 * @param prpLSpecialCaseReason
	 * @throws Exception
	 */
	@ProcessTask(processId = "claim_05",userId="request_speci",businessBeanOffset=0)
	public void saveBpm(String businessNo,PrpLSpecialCaseReason prpLSpecialCaseReason) throws Exception {
		this.save(prpLSpecialCaseReason);
	}

	@Override
	public void save(List<PrpLSpecialCaseReason> list) throws Exception {
		logger.info("保存特殊赔案申请原因信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpLSpecialCaseReasonId prpLSpecialCaseReasonId) throws Exception {
		logger.info("删除特殊赔案申请原因信息编号为" + prpLSpecialCaseReasonId + "的特殊赔案申请原因信息");
		super.deleteByPK(PrpLSpecialCaseReason.class, prpLSpecialCaseReasonId);
	}

	@Override
	public PrpLSpecialCaseReason findPrpLSpecialCaseReason(PrpLSpecialCaseReasonId prpLSpecialCaseReasonId) throws Exception {
		logger.info("查询特殊赔案申请原因信息编号为" + prpLSpecialCaseReasonId + "的特殊赔案申请原因信息");
		return super.get(PrpLSpecialCaseReason.class, prpLSpecialCaseReasonId);
	}

	@Override
	public Page findPrpLSpecialCaseReason(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取特殊赔案申请原因信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLSpecialCaseReason> findPrpLSpecialCaseReason(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
	
	/**
	 * 根据特殊赔案申请原因编号查询出特殊赔案申请原因信息
	 * @param certiNo ：传入的特殊赔案申请原因编号
	 * @return 返回特殊赔案申请原因
	 */
	public PrpLSpecialCaseReason findPrpLSpecialCaseReason(String certiNo) throws Exception{
		PrpLSpecialCaseReason prpLSpecialCaseReason = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.claimNo", certiNo);
		List<PrpLSpecialCaseReason> resultList = super.find(queryRule);
		if(resultList!=null&&resultList.size()>0) {
			prpLSpecialCaseReason = resultList.get(0);
		}
		return prpLSpecialCaseReason;
	}
}
