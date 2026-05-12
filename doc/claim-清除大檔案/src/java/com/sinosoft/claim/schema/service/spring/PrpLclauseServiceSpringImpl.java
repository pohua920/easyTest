package com.sinosoft.claim.schema.service.spring;

/**
 * 条款接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import org.hibernate.Session;

import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schema.model.PrpLclause;
import com.sinosoft.claim.schema.service.facade.PrpLclauseService;

public class PrpLclauseServiceSpringImpl extends GenericDaoHibernate<PrpLclause, String> implements PrpLclauseService {

	/**
	 * 保存条款信息
	 * @param prpLclause ：传入的条款
	 */
	@Override
	public void save(PrpLclause prpLclause) throws Exception {
		logger.info("保存条款信息");
		super.save(prpLclause);
	}

	/**
	 * @param prpLclause
	 * @throws Exception 保存或修改，
	 */
	public void saveOrUpdate(PrpLclause prpLclause) throws Exception {
		super.getSession().merge(prpLclause);
	}

	/**
	 * 删除条款信息
	 * @param clauseCode ：传入的条款编号
	 */
	@Override
	public void delete(String clauseCode) throws Exception {
		super.deleteByPK(PrpLclause.class, clauseCode);
		logger.info("删除条款编号为" + clauseCode + "的条款信息");
	}

	/**
	 * 保存条款信息
	 * @param list:保存条款信息
	 */
	@Override
	public void save(List<PrpLclause> list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

	/**
	 * @description: 条款修改
	 * @param PrpLclause prpLclause
	 * @throws Exception
	 */
	@Override
	public void update(PrpLclause prpLclause) {
		logger.info("修改条款信息开始");
		super.update(prpLclause);
		logger.info("修改条款信息结束");
	}

	/**
	 * 根据条款编号查询出条款信息
	 * @param clauseCode ：传入的条款编号
	 * @return 返回条款
	 */
	@Override
	public PrpLclause findPrpLclause(String clauseCode) throws Exception {
		logger.info("查询条款编号为" + clauseCode + "的条款信息");
		return super.get(PrpLclause.class, clauseCode);
	}

	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的条款页面信息
	 */
	@Override
	public Page findPrpLclause(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		logger.info("获取条款列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	/**
	 * 根据查询对象获取Page对象的列表
	 * @param queryRule 查询对象
	 * @param pageNo 页面编号
	 * @param pageSize 页面大小
	 * @return 包含的条款页面信息
	 */
	@Override
	public Page findPrpLclause(String conditions, int pageNo, int pageSize) throws Exception {
		logger.info("获取条款列表信息");
		String sql = "select * from PrpLclause where " + conditions;
		Page page = HibernateUtils.findPagebySql(this.getSession(), sql, pageNo, pageSize, PrpLclause.class);
		return page;
	}

	@Override
	public List<PrpLclause> findPrpLclause(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}

	/**
	 * @param conditions
	 * @return
	 * @throws Exception 根据sql语句条件查询
	 *             conditions如果conditions後面有有别名，会出错，prpLclause.
	 *             clauseCode='';在权限中会存在，和hibernate取的别名不一致，出错
	 */
	@SuppressWarnings("unchecked")
	public List<PrpLclause> findByConditions(String conditions) throws Exception {
		String sql = "select * from prpLclause where " + conditions;
		return (List<PrpLclause>) HibernateUtils.findbySql(super.getSession(), sql, PrpLclause.class);
	}

	/**
	 * @param conditions
	 * @return
	 * @throws Exception 根据sql语句条件查询
	 *             conditions如果conditions後面有有别名，会出错，prpLclause.
	 *             clauseCode='';在权限中会存在，和hibernate取的别名不一致，出错
	 */
	public Page findByConditions(String conditions, int pageNo, int pageSize) throws Exception {
		String sql = "select * from prpLclause where " + conditions;
		return HibernateUtils.findPagebySql(super.getSession(), sql, pageNo, pageSize, PrpLclause.class);
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
		buffer.append("SELECT count(*) FROM (SELECT * FROM PrpLclause WHERE ");
		buffer.append(conditions);
		buffer.append(")");
		Session session = super.getSession();
		count = (int) HibernateUtils.getCountbyCountSql(session, buffer.toString());
		return count;
	}

}