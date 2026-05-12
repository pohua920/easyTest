package com.sinosoft.claim.schema.service.spring;

/**
 * 优惠折扣明细接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpCprofitDetail;
import com.sinosoft.claim.schema.model.PrpCprofitDetailId;
import com.sinosoft.claim.schema.service.facade.PrpCprofitDetailService;

public class PrpCprofitDetailServiceSpringImpl extends GenericDaoHibernate<PrpCprofitDetail, PrpCprofitDetailId> implements PrpCprofitDetailService {

	/**
	 * 保存优惠折扣明细信息
	 * @param prpCprofitDetail ：传入的优惠折扣明细
	 */
	public void save(PrpCprofitDetail prpCprofitDetail) throws Exception {
		logger.info("优惠折扣明细信息");
		super.save(prpCprofitDetail);
	}

	/**
	 * 优惠折扣明细信息
	 * @param list :传入的优惠折扣明细信息集合
	 * @throws Exceptionuan
	 */
	public void save(List<PrpCprofitDetail> list) throws Exception {
		logger.info("优惠折扣明细信息");
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

	/**
	 * 删除优惠折扣明细信息
	 * @param prpCprofitDetailId ：传入的优惠折扣明细编号
	 */
	public void delete(PrpCprofitDetailId prpCprofitDetailId) throws Exception {
		logger.info("删除优惠折扣明细编号为" + prpCprofitDetailId + "的优惠折扣明细");
		super.deleteByPK(PrpCprofitDetail.class, prpCprofitDetailId);
	}

	public PrpCprofitDetail findPrpCprofitDetail(PrpCprofitDetailId prpCprofitDetailId) throws Exception {
		logger.info("查询优惠折扣明细编号为" + prpCprofitDetailId + "的优惠折扣明细");
		return super.get(PrpCprofitDetail.class, prpCprofitDetailId);
	}

	public Page findPrpCprofitDetail(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		logger.info("获取优惠折扣明细列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	public List<PrpCprofitDetail> findPrpCprofitDetail(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}
}
