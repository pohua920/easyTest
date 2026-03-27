package com.sinosoft.claim.schema.service.spring;
/**
 * 承保险种定额份数信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpCration;
import com.sinosoft.claim.schema.model.PrpCrationId;
import com.sinosoft.claim.schema.service.facade.PrpCrationService;

public class PrpCrationServiceSpringImpl extends
GenericDaoHibernate<PrpCration, PrpCrationId> implements PrpCrationService{

	/**
	 * 保存承保险种定额份数信息
	 * @param PrpCration ：传入的承保险种定额份数
	 */
	@Override
	public void save(PrpCration PrpCration) throws Exception {
		logger.info("保存承保险种定额份数信息信息");
		super.save(PrpCration);
		
	}

	/**
	 * 承保险种定额份数信息
	 * @param list  :传入的承保险种定额份数信息集合
	 * @throws Exception
	 */
	@Override
	public void save(List<PrpCration> list) throws Exception {
		logger.info("保存承保险种定额份数信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	/**
	 * 删除承保险种定额份数信息
	 * @param PrpCrationId ：传入的承保险种定额份数编号
	 */
	@Override
	public void delete(PrpCrationId PrpCrationId) throws Exception {
		logger.info("删除承保险种定额份数信息编号为" + PrpCrationId + "的承保险种定额份数信息");
		super.deleteByPK(PrpCration.class, PrpCrationId);
	}

	/**
	 * 根据承保险种定额份数编号查询出承保险种定额份数信息
	 * @param PrpCrationId ：传入的承保险种定额份数编号
	 * @return 返回承保险种定额份数
	 */
	@Override
	public PrpCration findPrpCration(PrpCrationId PrpCrationId) throws Exception {
		logger.info("查询承保险种定额份数信息编号为" + PrpCrationId + "的承保险种定额份数信息");
		return super.get(PrpCration.class, PrpCrationId);
	}

	/**
	 * 根据查询对象获取承保险种定额份数  的列表
	 * @param queryRule 查询对象
	 * @return 包含的承保险种定额份数  的列表
	 */
	@Override
	public Page findPrpCration(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取承保险种定额份数信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	/**
	 * 根据查询对象获取承保险种定额份数  的列表
	 * @param queryRule 查询对象
	 * @return 包含的承保险种定额份数  的列表
	 */
	@Override
	public List<PrpCration> findPrpCration(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
	
	/**
	 * 根据承保险种定额份数编号查询出承保险种定额份数信息
	 * @param certiNo ：传入的承保险种定额份数编号
	 * @return 返回承保险种定额份数
	 */
	public PrpCration findPrpCration(String certiNo) throws Exception{
		PrpCration PrpCration = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.certiNo", certiNo);
		List<PrpCration> resultList = super.find(queryRule);
		if(resultList!=null&&resultList.size()>0) {
			PrpCration = resultList.get(0);
		}
		return PrpCration;
	}

}
