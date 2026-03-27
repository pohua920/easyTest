package com.sinosoft.claim.schema.service.spring;

/**
 * 单证分组信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schema.model.PrpGroup;
import com.sinosoft.claim.schema.model.PrpGroupId;
import com.sinosoft.claim.schema.service.facade.PrpGroupService;

public class PrpGroupServiceSpringImpl extends GenericDaoHibernate<PrpGroup, PrpGroupId> implements PrpGroupService {

	@Override
	public void save(PrpGroup prpGroup) throws Exception {
		logger.info("保存单证分组信息信息");
		super.save(prpGroup);

	}

	@Override
	public void save(List<PrpGroup> list) throws Exception {
		logger.info("保存单证分组信息");
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpGroupId prpGroupId) throws Exception {
		logger.info("删除单证分组信息编号为" + prpGroupId + "的单证分组信息");
		super.deleteByPK(PrpGroup.class, prpGroupId);
	}

	@Override
	public PrpGroup findPrpGroup(PrpGroupId prpGroupId) throws Exception {
		logger.info("查询单证分组信息编号为" + prpGroupId + "的单证分组信息");
		return super.get(PrpGroup.class, prpGroupId);
	}

	@Override
	public Page findPrpGroup(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		logger.info("获取单证分组信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpGroup> findPrpGroup(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}

	/**
	 * 获取单号编组
	 * @param subGroupNo 子编组
	 * @return strGroupNo 主编组
	 * @throws Exception
	 */
	public String getGroupNo(String subGroupNo) throws Exception {
		String strGroupNo = "";
		String statement = " Select UNIQUE GroupNo From PrpGroup Where subGroupNo = '"+subGroupNo+"'";
		List<?> result=HibernateUtils.findbySql(getSession(), statement);
		if (result.size()>0) {
			strGroupNo = (String) result.get(0);
		} else {
			strGroupNo = subGroupNo;
		}
		return strGroupNo;
	}
}
