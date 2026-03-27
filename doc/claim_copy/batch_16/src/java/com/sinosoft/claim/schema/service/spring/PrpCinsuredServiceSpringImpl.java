package com.sinosoft.claim.schema.service.spring;

/**
 * 保险关系人接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;
import ins.framework.utils.DataUtils;

import java.util.List;

import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schema.model.PrpCinsured;
import com.sinosoft.claim.schema.model.PrpCinsuredId;
import com.sinosoft.claim.schema.service.facade.PrpCinsuredService;

public class PrpCinsuredServiceSpringImpl extends GenericDaoHibernate<PrpCinsured, PrpCinsuredId> implements PrpCinsuredService {

	public void save(PrpCinsured prpCinsured) throws Exception {
		logger.info("保险关系人信息");
		super.save(prpCinsured);
	}

	public void save(List<PrpCinsured> list) throws Exception {
		logger.info("保险关系人信息");
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

	public void delete(PrpCinsuredId prpCinsuredId) throws Exception {
		logger.info("删除保险关系人编号为" + prpCinsuredId + "的保险关系人");
		super.deleteByPK(PrpCinsured.class, prpCinsuredId);
	}

	public PrpCinsured findPrpCinsured(PrpCinsuredId prpCinsuredId) throws Exception {
		logger.info("查询保险关系人编号为" + prpCinsuredId + "的保险关系人");
		return super.get(PrpCinsured.class, prpCinsuredId);
	}

	public Page findPrpCinsured(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		logger.info("获取保险关系人列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	public List<PrpCinsured> findPrpCinsured(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}

	@Override
	public Page findByPage(String conditions, int pageNo, int pageSize) throws Exception {
		if(DataUtils.emptyToNull(conditions)==null){
			conditions = " 1=1 ";
		}
		String sql = "select * from PrpCinsured where " + conditions;
		return HibernateUtils.findPagebySql(super.getSession(), sql, pageNo, pageSize,PrpCinsured.class);
	}
	/**
	 * 分页查询PrpCinsured
	 * @author 中科软
	 * @date Mar 26, 2013 11:47:54 AM
	 * @param conditions
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public List<PrpCinsured> findPrpCinsured(String conditions) throws Exception{
		if(DataUtils.emptyToNull(conditions)==null){
			conditions = " 1=1 ";
		}
//		String sql = "select * from PrpCinsured where " + conditions;
		QueryRule queryRule = QueryRule.getInstance().addSql(conditions);
		return this.find(queryRule);
	}
}
