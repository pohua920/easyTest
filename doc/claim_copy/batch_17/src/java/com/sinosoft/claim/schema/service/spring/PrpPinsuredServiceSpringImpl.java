package com.sinosoft.claim.schema.service.spring;

/**
 * 保险关系人接口实现类
 * @author 张兴伟
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;
import ins.framework.utils.DataUtils;

import java.util.ArrayList;
import java.util.List;

import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schema.model.PrpPinsured;
import com.sinosoft.claim.schema.model.PrpPinsuredId;
import com.sinosoft.claim.schema.service.facade.PrpPinsuredService;

public class PrpPinsuredServiceSpringImpl extends GenericDaoHibernate<PrpPinsured, PrpPinsuredId> implements PrpPinsuredService {

	public void save(PrpPinsured prpPinsured) throws Exception {
		logger.info("保险关系人信息");
		super.save(prpPinsured);
	}

	public void save(List<PrpPinsured> list) throws Exception {
		logger.info("保险关系人信息");
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

	public void delete(PrpPinsuredId prpPinsuredId) throws Exception {
		logger.info("删除保险关系人编号为" + prpPinsuredId + "的保险关系人");
		super.deleteByPK(PrpPinsured.class, prpPinsuredId);
	}

	public PrpPinsured findPrpPinsured(PrpPinsuredId prpPinsuredId) throws Exception {
		logger.info("查询保险关系人编号为" + prpPinsuredId + "的保险关系人");
		return super.get(PrpPinsured.class, prpPinsuredId);
	}

	public Page findPrpPinsured(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		logger.info("获取保险关系人列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	public List<PrpPinsured> findPrpPinsured(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}

	@Override
	public Page findByPage(String conditions, int pageNo, int pageSize) throws Exception {
		if (DataUtils.emptyToNull(conditions) == null) {
			conditions = " 1=1 ";
		}
		String sql = "select * from PrpPinsured where " + conditions;
		return HibernateUtils.findPagebySql(super.getSession(), sql, pageNo, pageSize, PrpPinsured.class);
	}
}
