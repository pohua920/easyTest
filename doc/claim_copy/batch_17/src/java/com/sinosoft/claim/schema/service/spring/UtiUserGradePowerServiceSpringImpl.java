package com.sinosoft.claim.schema.service.spring;
/**
 * 用户岗位接口实现类
 * @author 中科软
 *
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.UtiUserGradePower;
import com.sinosoft.claim.schema.model.UtiUserGradePowerId;
import com.sinosoft.claim.schema.service.facade.UtiUserGradePowerService;

public class UtiUserGradePowerServiceSpringImpl extends GenericDaoHibernate<UtiUserGradePower, UtiUserGradePowerId> implements UtiUserGradePowerService {
	public void delete(UtiUserGradePowerId utiUserGradePowerId) throws Exception {
		super.deleteByPK(utiUserGradePowerId);
		logger.info("删除用户岗位定义表编号为" + utiUserGradePowerId + "的用户岗位定义表信息");
	}

	public UtiUserGradePower findUtiUserGradePower(UtiUserGradePowerId utiUserGradePowerId) throws Exception {
		logger.info("查询用户岗位定义表编号为" + utiUserGradePowerId + "的用户岗位定义表信息");
		return super.get(UtiUserGradePower.class,utiUserGradePowerId);
	}

	public Page findUtiUserGradePower(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		logger.info("获取用户岗位定义表列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	public List<UtiUserGradePower> findUtiUserGradePower(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}

	public void save(UtiUserGradePower utiUserGradePower) throws Exception {
		logger.info("保存用户岗位定义表信息");
		super.save(utiUserGradePower);
	}

	public void save(List<UtiUserGradePower> list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}
}
