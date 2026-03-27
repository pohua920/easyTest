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
import com.sinosoft.claim.schema.model.PrpCopyInsured;
import com.sinosoft.claim.schema.model.PrpCopyInsuredId;
import com.sinosoft.claim.schema.service.facade.PrpCopyInsuredService;

public class PrpCopyInsuredServiceSpringImpl extends GenericDaoHibernate<PrpCopyInsured, PrpCopyInsuredId> implements PrpCopyInsuredService {

	public void save(PrpCopyInsured prpCopyInsured) throws Exception {
		logger.info("保险关系人信息");
		super.save(prpCopyInsured);
	}

	public void save(List<PrpCopyInsured> list) throws Exception {
		logger.info("保险关系人信息");
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

	public void delete(PrpCopyInsuredId prpCopyInsuredId) throws Exception {
		logger.info("删除保险关系人编号为" + prpCopyInsuredId + "的保险关系人");
		super.deleteByPK(PrpCopyInsured.class, prpCopyInsuredId);
	}

	public PrpCopyInsured findPrpCopyInsured(PrpCopyInsuredId prpCopyInsuredId) throws Exception {
		logger.info("查询保险关系人编号为" + prpCopyInsuredId + "的保险关系人");
		return super.get(PrpCopyInsured.class, prpCopyInsuredId);
	}

	public Page findPrpCopyInsured(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		logger.info("获取保险关系人列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	public List<PrpCopyInsured> findPrpCopyInsured(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}

	@Override
	public Page findByPage(String conditions, int pageNo, int pageSize) throws Exception {
		if(DataUtils.emptyToNull(conditions)==null){
			conditions = " 1=1 ";
		}
		String sql = "select * from PrpCopyInsured where " + conditions;
		return HibernateUtils.findPagebySql(super.getSession(), sql, pageNo, pageSize,PrpCopyInsured.class);
	}
}
