package com.sinosoft.claim.schema.service.spring;
/**
 * 涉诉赔案律师信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.Prpllawyer;
import com.sinosoft.claim.schema.model.PrpllawyerId;
import com.sinosoft.claim.schema.service.facade.PrpllawyerService;

public class PrpllawyerServiceSpringImpl extends
GenericDaoHibernate<Prpllawyer, PrpllawyerId> implements PrpllawyerService{

	@Override
	public void save(Prpllawyer prpllawyer) throws Exception {
		logger.info("保存涉诉赔案律师信息信息");
		super.save(prpllawyer);
		
	}

	@Override
	public void save(List<Prpllawyer> list) throws Exception {
		logger.info("保存涉诉赔案律师信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpllawyerId prpllawyerId) throws Exception {
		logger.info("删除涉诉赔案律师信息编号为" + prpllawyerId + "的涉诉赔案律师信息");
		super.deleteByPK(Prpllawyer.class, prpllawyerId);
	}

	@Override
	public Prpllawyer findPrpllawyer(PrpllawyerId prpllawyerId) throws Exception {
		logger.info("查询涉诉赔案律师信息编号为" + prpllawyerId + "的涉诉赔案律师信息");
		return super.get(Prpllawyer.class, prpllawyerId);
	}

	@Override
	public Page findPrpllawyer(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取涉诉赔案律师信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<Prpllawyer> findPrpllawyer(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
	
	/**
	 * 根据涉诉赔案律师信息编号查询出涉诉赔案律师信息
	 * @param certiNo ：传入的涉诉赔案律师信息编号
	 * @return 返回涉诉赔案律师信息
	 */
	public Prpllawyer findPrpllawyer(String certiNo) throws Exception{
		Prpllawyer prpllawyer = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.certiNo", certiNo);
		List<Prpllawyer> resultList = super.find(queryRule);
		if(resultList!=null&&resultList.size()>0) {
			prpllawyer = resultList.get(0);
		}
		return prpllawyer;
	}

}
