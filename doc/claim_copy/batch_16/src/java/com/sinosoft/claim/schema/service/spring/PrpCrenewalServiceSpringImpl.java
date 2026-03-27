package com.sinosoft.claim.schema.service.spring;
/**
 * 续保信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpCrenewal;
import com.sinosoft.claim.schema.service.facade.PrpCrenewalService;

public class PrpCrenewalServiceSpringImpl extends
GenericDaoHibernate<PrpCrenewal, String> implements PrpCrenewalService{

	/**
	 * 续保信息
	 * @param PrpCrenewal ：传入的续保信息
	 */
	@Override
	public void save(PrpCrenewal PrpCrenewal) throws Exception {
		logger.info("保存续保信息信息");
		super.save(PrpCrenewal);
		
	}

	/**
	 * 保存续保信息
	 * @param list  :传入的续保信息集合
	 * @throws Exception
	 */
	@Override
	public void save(List<PrpCrenewal> list) throws Exception {
		logger.info("保存续保信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	/**
	 * 删除续保信息信息
	 * @param policyNo ：传入的续保信息编号
	 */
	@Override
	public void delete(String claimNo) throws Exception {
		logger.info("删除续保信息编号为" + claimNo + "的续保信息");
		super.deleteByPK(PrpCrenewal.class, claimNo);
	}

	/**
	 * 根据续保信息编号查询出续保信息信息
	 * @param policyNo ：传入的续保信息编号
	 * @return 返回续保信息
	 */
	@Override
	public PrpCrenewal findPrpCrenewal(String claimNo) throws Exception {
		logger.info("查询续保信息编号为" + claimNo + "的续保信息");
		return super.get(PrpCrenewal.class,claimNo);
	}
    
	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的续保信息页面信息
	 */
	@Override
	public Page findPrpCrenewal(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取续保信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
		
	}

	/**
	 * 根据查询对象获取 续保信息 的列表
	 * @param queryRule 查询对象
	 * @return 包含的续保信息  的列表
	 */
	@Override
	public List<PrpCrenewal> findPrpCrenewal(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}

}
