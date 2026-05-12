package com.sinosoft.claim.schema.service.spring;
/**
 * 船舶险船员信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpCshipDriver;
import com.sinosoft.claim.schema.model.PrpCshipDriverId;
import com.sinosoft.claim.schema.service.facade.PrpCshipDriverService;

public class PrpCshipDriverServiceSpringImpl extends
GenericDaoHibernate<PrpCshipDriver, PrpCshipDriverId> implements PrpCshipDriverService{

	/**
	 * 保存船舶险船员信息信息
	 * @param PrpCshipDriver ：传入的船舶险船员信息
	 */
	@Override
	public void save(PrpCshipDriver PrpCshipDriver) throws Exception {
		logger.info("保存船舶险船员信息信息");
		super.save(PrpCshipDriver);
		
	}

	/**
	 * 船舶险船员信息信息
	 * @param list  :传入的船舶险船员信息信息集合
	 * @throws Exception
	 */
	@Override
	public void save(List<PrpCshipDriver> list) throws Exception {
		logger.info("保存船舶险船员信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	/**
	 * 删除船舶险船员信息信息
	 * @param PrpCshipDriverId ：传入的船舶险船员信息编号
	 */
	@Override
	public void delete(PrpCshipDriverId PrpCshipDriverId) throws Exception {
		logger.info("删除船舶险船员信息编号为" + PrpCshipDriverId + "的船舶险船员信息");
		super.deleteByPK(PrpCshipDriver.class, PrpCshipDriverId);
	}

	/**
	 * 根据船舶险船员信息编号查询出船舶险船员信息信息
	 * @param PrpCshipDriverId ：传入的船舶险船员信息编号
	 * @return 返回船舶险船员信息
	 */
	@Override
	public PrpCshipDriver findPrpCshipDriver(PrpCshipDriverId PrpCshipDriverId) throws Exception {
		logger.info("查询船舶险船员信息编号为" + PrpCshipDriverId + "的船舶险船员信息");
		return super.get(PrpCshipDriver.class, PrpCshipDriverId);
	}

	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的船舶险船员信息页面信息
	 */
	@Override
	public Page findPrpCshipDriver(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取船舶险船员信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	/**
	 * 根据查询对象获取  船舶险船员的列表
	 * @param queryRule 查询对象
	 * @return 包含的  船舶险船员的列表
	 */
	@Override
	public List<PrpCshipDriver> findPrpCshipDriver(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
	
	/**
	 * 根据人伤跟踪编号查询出船舶险船员信息
	 * @param certiNo ：传入的人伤跟踪编号
	 * @return 返回人伤跟踪
	 */
	public PrpCshipDriver findPrpCshipDriver(String certiNo) throws Exception{
		PrpCshipDriver PrpCshipDriver = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.certiNo", certiNo);
		List<PrpCshipDriver> resultList = super.find(queryRule);
		if(resultList!=null&&resultList.size()>0) {
			PrpCshipDriver = resultList.get(0);
		}
		return PrpCshipDriver;
	}

}
