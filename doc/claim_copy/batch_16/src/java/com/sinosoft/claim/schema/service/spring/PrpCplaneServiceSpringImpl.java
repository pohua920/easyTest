package com.sinosoft.claim.schema.service.spring;

/**
 * 航空险接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;
import ins.framework.utils.DataUtils;

import java.util.List;

import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schema.model.PrpCplane;
import com.sinosoft.claim.schema.model.PrpCplaneId;
import com.sinosoft.claim.schema.service.facade.PrpCplaneService;

public class PrpCplaneServiceSpringImpl extends GenericDaoHibernate<PrpCplane, PrpCplaneId> implements PrpCplaneService {

	public void save(PrpCplane prpCplane) throws Exception {
		logger.info("航空险信息");
		super.save(prpCplane);
	}

	public void save(List<PrpCplane> list) throws Exception {
		logger.info("航空险信息");
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

	public void delete(PrpCplaneId prpCplaneId) throws Exception {
		logger.info("删除航空险编号为" + prpCplaneId + "的航空险");
		super.deleteByPK(PrpCplane.class, prpCplaneId);
	}

	public PrpCplane findPrpCplane(PrpCplaneId prpCplaneId) throws Exception {
		logger.info("查询航空险编号为" + prpCplaneId + "的航空险");
		return super.get(PrpCplane.class, prpCplaneId);
	}

	public Page findPrpCplane(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		logger.info("获取航空险列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	public List<PrpCplane> findPrpCplane(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}

	@Override
	public Page findByPage(String conditions, int pageNo, int pageSize) throws Exception {
		if (DataUtils.emptyToNull(conditions) == null) {
			conditions = " 1=1 ";
		}
		String sql = "select * from PrpCplane where " + conditions;
		return HibernateUtils.findPagebySql(super.getSession(), sql, pageNo, pageSize, PrpCplane.class);
	}
}
