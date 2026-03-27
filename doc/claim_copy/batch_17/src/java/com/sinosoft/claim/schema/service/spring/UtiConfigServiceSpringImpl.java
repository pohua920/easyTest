package com.sinosoft.claim.schema.service.spring;

/**
 * UtiConfig接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import org.hibernate.Session;

import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schema.model.UtiConfig;
import com.sinosoft.claim.schema.service.facade.UtiConfigService;

public class UtiConfigServiceSpringImpl extends GenericDaoHibernate<UtiConfig, String> implements UtiConfigService {

	/**
	 * 保存UtiConfig信息
	 * @param utiConfig ：传入的UtiConfig
	 */
	@Override
	public void save(UtiConfig utiConfig) throws Exception {
		logger.info("保存UtiConfig信息");
		super.save(utiConfig);
	}

	/**
	 * @param utiConfig
	 * @throws Exception 保存或修改，
	 */
	public void saveOrUpdate(UtiConfig utiConfig) throws Exception {
		super.getSession().merge(utiConfig);
	}

	/**
	 * 删除UtiConfig信息
	 * @param configCode ：传入的UtiConfig编号
	 */
	@Override
	public void delete(String configCode) throws Exception {
		super.deleteByPK(UtiConfig.class, configCode);
		logger.info("删除编号为" + configCode + "的UtiConfig信息");
	}

	/**
	 * 保存UtiConfig信息
	 * @param list:保存UtiConfig信息
	 */
	@Override
	public void save(List<UtiConfig> list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

	/**
	 * @description: UtiConfig修改
	 * @param UtiConfig utiConfig
	 * @throws Exception
	 */
	@Override
	public void update(UtiConfig utiConfig) {
		logger.info("修改UtiConfig信息开始");
		super.update(utiConfig);
		logger.info("修改UtiConfig信息结束");
	}

	/**
	 * 根据UtiConfig编号查询出UtiConfig信息
	 * @param configCode ：传入的UtiConfig编号
	 * @return 返回UtiConfig
	 */
	@Override
	public UtiConfig findUtiConfig(String configCode) throws Exception {
		logger.info("查询编号为" + configCode + "的UtiConfig信息");
		return super.get(UtiConfig.class, configCode);
	}

	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的UtiConfig页面信息
	 */
	@Override
	public Page findUtiConfig(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		logger.info("获取UtiConfig列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的UtiConfig页面信息
	 */
	@Override
	public Page findUtiConfig(String conditions, int pageNo, int pageSize) throws Exception{
		logger.info("获取UtiConfig列表信息");
		String sql = "select * from UtiConfig where "+conditions;
		Page page = HibernateUtils.findPagebySql(this.getSession(), sql, pageNo, pageSize, UtiConfig.class);
		return page;
	}

	@Override
	public List<UtiConfig> findUtiConfig(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}

	/**
	 * @param conditions
	 * @return
	 * @throws Exception 根据sql语句条件查询
	 */
	public List<UtiConfig> findByConditions(String conditions) throws Exception {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(conditions);
		return super.find(queryRule);
	}

	/**
	 * @param configCode
	 * @return
	 */
	@Override
	public boolean isExist(String configCode) throws Exception {
		String hql = "from UtiConfig where configCode=?";
		long count = super.getCount(hql, configCode);
		if (count < 1) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 查询满足模糊查询条件的记录数
	 * @param conditions conditions
	 * @return 满足模糊查询条件的记录数
	 * @throws Exception
	 */
	@Override
	public int getCount(String conditions) throws Exception {
		int count = -1;
		StringBuffer buffer = new StringBuffer(100);
		buffer.append("SELECT count(*) FROM (SELECT * FROM UtiConfig WHERE ");
		buffer.append(conditions);
		buffer.append(")");
		Session session = super.getSession();
		count = (int) HibernateUtils.getCountbyCountSql(session, buffer.toString());
		return count;
	}
}