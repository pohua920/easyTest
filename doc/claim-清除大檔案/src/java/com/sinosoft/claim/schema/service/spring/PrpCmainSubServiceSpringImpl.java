package com.sinosoft.claim.schema.service.spring;

/**
 * 保单隶属接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;
import java.util.List;
import com.sinosoft.claim.schema.model.PrpCmainSub;
import com.sinosoft.claim.schema.model.PrpCmainSubId;
import com.sinosoft.claim.schema.service.facade.PrpCmainSubService;

public class PrpCmainSubServiceSpringImpl extends GenericDaoHibernate<PrpCmainSub, PrpCmainSubId> implements PrpCmainSubService {

	public void save(PrpCmainSub prpCmainSub) throws Exception {
		logger.info("保单隶属信息");
		super.save(prpCmainSub);
	}

	public void save(List<PrpCmainSub> list) throws Exception {
		logger.info("保单隶属信息");
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

	public void delete(PrpCmainSubId prpCmainSubId) throws Exception {
		logger.info("删除保单隶属编号为" + prpCmainSubId + "的保单隶属");
		super.deleteByPK(PrpCmainSub.class, prpCmainSubId);
	}

	public PrpCmainSub findPrpCmainSub(PrpCmainSubId prpCmainSubId) throws Exception {
		logger.info("查询保单隶属编号为" + prpCmainSubId + "的保单隶属");
		return super.get(PrpCmainSub.class, prpCmainSubId);
	}

	public Page findPrpCmainSub(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		logger.info("获取保单隶属列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	public List<PrpCmainSub> findPrpCmainSub(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}
}
