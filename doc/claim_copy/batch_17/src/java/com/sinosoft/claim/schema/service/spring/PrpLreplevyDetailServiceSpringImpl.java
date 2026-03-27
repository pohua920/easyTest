package com.sinosoft.claim.schema.service.spring;
/**
 * 权益转让及追偿信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLreplevyDetail;
import com.sinosoft.claim.schema.model.PrpLreplevyDetailId;
import com.sinosoft.claim.schema.service.facade.PrpLreplevyDetailService;

public class PrpLreplevyDetailServiceSpringImpl extends
GenericDaoHibernate<PrpLreplevyDetail, PrpLreplevyDetailId> implements PrpLreplevyDetailService{

	@Override
	public void save(PrpLreplevyDetail prpLreplevyDetail) throws Exception {
		logger.info("保存权益转让及追偿信息信息");
		super.save(prpLreplevyDetail);
		
	}

	@Override
	public void save(List<PrpLreplevyDetail> list) throws Exception {
		logger.info("保存权益转让及追偿信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpLreplevyDetailId prpLreplevyDetailId) throws Exception {
		logger.info("删除权益转让及追偿信息编号为" + prpLreplevyDetailId + "的权益转让及追偿信息");
		super.deleteByPK(PrpLreplevyDetail.class, prpLreplevyDetailId);
	}

	@Override
	public PrpLreplevyDetail findPrpLreplevyDetail(PrpLreplevyDetailId prpLreplevyDetailId) throws Exception {
		logger.info("查询权益转让及追偿信息编号为" + prpLreplevyDetailId + "的权益转让及追偿信息");
		return super.get(PrpLreplevyDetail.class, prpLreplevyDetailId);
	}

	@Override
	public Page findPrpLreplevyDetail(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取权益转让及追偿信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLreplevyDetail> findPrpLreplevyDetail(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
	
	/**
	 * 根据权益转让及追偿信息编号查询出权益转让及追偿信息
	 * @param certiNo ：传入的权益转让及追偿信息编号
	 * @return 返回权益转让及追偿信息
	 */
	public PrpLreplevyDetail findPrpLreplevyDetail(String certiNo) throws Exception{
		PrpLreplevyDetail prpLreplevyDetail = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.certiNo", certiNo);
		List<PrpLreplevyDetail> resultList = super.find(queryRule);
		if(resultList!=null&&resultList.size()>0) {
			prpLreplevyDetail = resultList.get(0);
		}
		return prpLreplevyDetail;
	}

}
