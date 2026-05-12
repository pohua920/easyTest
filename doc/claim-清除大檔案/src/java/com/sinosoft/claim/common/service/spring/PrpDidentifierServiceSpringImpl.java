package com.sinosoft.claim.common.service.spring;

/**
 * 检验人接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.common.service.facade.PrpDidentifierService;
import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schema.model.PrpDidentifier;

public class PrpDidentifierServiceSpringImpl extends GenericDaoHibernate<PrpDidentifier, String> implements PrpDidentifierService {

	/**
	 * 保存检验人信息
	 * @param prpDidentifier  传入的检验人
	 */
	@Override
	public void save(PrpDidentifier prpDidentifier) throws Exception {
		logger.info("保存检验人信息");
		super.save(prpDidentifier);
	}

	/**
	 * 保存或修改
	 * @param prpDidentifier 传入的检验人
	 * @throws Exception
	 */
	public void saveOrUpdate(PrpDidentifier prpDidentifier) throws Exception {
		super.getSession().merge(prpDidentifier);
	}

	/**
	 * 保存检验人信息
	 * @param list:保存检验人信息
	 */
	@Override
	public void save(List<PrpDidentifier> list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

	/**
	 * @description: 检验人修改
	 * @param PrpDidentifier 检验人信息
	 * @throws Exception
	 */
	@Override
	public void update(PrpDidentifier prpDidentifier) {
		logger.info("修改检验人信息开始");
		super.update(prpDidentifier);
		logger.info("修改检验人信息结束");
	}

	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的检验人页面信息
	 */
	@Override
	public Page findPrpDidentifier(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		logger.info("获取检验人列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的检验人页面信息
	 */
	@Override
	public Page findPrpDidentifier(String conditions, int pageNo, int pageSize) throws Exception{
		logger.info("获取检验人列表信息");
		String sql = "select * from PrpDidentifier where "+conditions;
		Page page = HibernateUtils.findPagebySql(this.getSession(), sql, pageNo, pageSize, PrpDidentifier.class);
		return page;
	}

	/**
	 * 根据查询条件查询所有结果
	 * @param queryRule 查询条件
	 * @return 集合
	 * @throws Exception
	 */
	@Override
	public List<PrpDidentifier> findPrpDidentifier(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}

	/**
	 * 根据sql语句条件查询
	 * @param conditions 查询条件
	 * @return
	 * @throws Exception 
	 */
	public List<PrpDidentifier> findByConditions(String conditions) throws Exception {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(conditions);
		return super.find(queryRule);
	}

}