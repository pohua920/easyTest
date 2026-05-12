package com.sinosoft.claim.schema.service.spring;

/**
 * 优惠信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;
import java.util.List;
import com.sinosoft.claim.schema.model.PrpCprofit;
import com.sinosoft.claim.schema.model.PrpCprofitId;
import com.sinosoft.claim.schema.service.facade.PrpCprofitService;

public class PrpCprofitServiceSpringImpl extends GenericDaoHibernate<PrpCprofit, PrpCprofitId> implements PrpCprofitService {

	/**
	 * 保存优惠信息
	 * @param prpLcheck ：传入的优惠信息
	 */
	public void save(PrpCprofit prpCprofit) throws Exception {
		logger.info("优惠信息信息");
		super.save(prpCprofit);
	}

	/**
	 * 优惠信息
	 * @param list :传入的优惠信息集合
	 * @throws Exception
	 */
	public void save(List<PrpCprofit> list) throws Exception {
		logger.info("优惠信息");
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

	/**
	 * 删除优惠信息
	 * @param prpCprofitId ：传入的优惠信息编号
	 */
	public void delete(PrpCprofitId prpCprofitId) throws Exception {
		logger.info("删除优惠信息编号为" + prpCprofitId + "的优惠信息");
		super.deleteByPK(PrpCprofit.class, prpCprofitId);
	}

	/**
	 * 根据优惠信息编号查询出优惠信息
	 * @param prpCprofitId ：传入的优惠信息编号
	 * @return 返回优惠信息
	 */
	public PrpCprofit findPrpCprofit(PrpCprofitId prpCprofitId) throws Exception {
		logger.info("查询优惠信息编号为" + prpCprofitId + "的优惠信息");
		return super.get(PrpCprofit.class, prpCprofitId);
	}

	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的优惠信息页面信息
	 */
	public Page findPrpCprofit(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		logger.info("获取优惠信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	/**
	 * 根据查询对象获取 优惠信息 的列表
	 * @param queryRule 查询对象
	 * @return 包含的 优惠信息 的列表
	 */
	public List<PrpCprofit> findPrpCprofit(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}
}
