package com.sinosoft.claim.schema.service.spring;
/**
 * 保单的共保人子信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpCcoinsDetail;
import com.sinosoft.claim.schema.model.PrpCcoinsDetailId;
import com.sinosoft.claim.schema.service.facade.PrpCcoinsDetailService;

public class PrpCcoinsDetailServiceSpringImpl extends
GenericDaoHibernate<PrpCcoinsDetail, PrpCcoinsDetailId> implements PrpCcoinsDetailService{

	@Override
	public void save(PrpCcoinsDetail PrpCcoinsDetail) throws Exception {
		logger.info("保存保单的共保人子信息信息");
		super.save(PrpCcoinsDetail);
		
	}

	@Override
	public void save(List<PrpCcoinsDetail> list) throws Exception {
		logger.info("保存保单的共保人子信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpCcoinsDetailId PrpCcoinsDetailId) throws Exception {
		logger.info("删除保单的共保人子信息编号为" + PrpCcoinsDetailId + "的保单的共保人子信息");
		super.deleteByPK(PrpCcoinsDetail.class, PrpCcoinsDetailId);
	}

	@Override
	public PrpCcoinsDetail findPrpCcoinsDetail(PrpCcoinsDetailId PrpCcoinsDetailId) throws Exception {
		logger.info("查询保单的共保人子信息编号为" + PrpCcoinsDetailId + "的保单的共保人子信息");
		return super.get(PrpCcoinsDetail.class, PrpCcoinsDetailId);
	}

	@Override
	public Page findPrpCcoinsDetail(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取保单的共保人子信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpCcoinsDetail> findPrpCcoinsDetail(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
	
	/**
	 * 根据人伤跟踪编号查询出保单的共保人子信息
	 * @param certiNo ：传入的人伤跟踪编号
	 * @return 返回人伤跟踪
	 */
	public PrpCcoinsDetail findPrpCcoinsDetail(String certiNo) throws Exception{
		PrpCcoinsDetail PrpCcoinsDetail = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.certiNo", certiNo);
		List<PrpCcoinsDetail> resultList = super.find(queryRule);
		if(resultList!=null&&resultList.size()>0) {
			PrpCcoinsDetail = resultList.get(0);
		}
		return PrpCcoinsDetail;
	}

}
