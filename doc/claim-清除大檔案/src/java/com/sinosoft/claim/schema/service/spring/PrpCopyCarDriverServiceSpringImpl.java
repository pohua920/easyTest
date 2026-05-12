package com.sinosoft.claim.schema.service.spring;

/**
 * 车辆驾驶员关系接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;
import java.util.List;
import com.sinosoft.claim.schema.model.PrpCopyCarDriver;
import com.sinosoft.claim.schema.model.PrpCopyCarDriverId;
import com.sinosoft.claim.schema.service.facade.PrpCopyCarDriverService;

public class PrpCopyCarDriverServiceSpringImpl extends GenericDaoHibernate<PrpCopyCarDriver, PrpCopyCarDriverId> implements PrpCopyCarDriverService {

	public void save(PrpCopyCarDriver prpCopyCarDriver) throws Exception {
		logger.info("车辆驾驶员关系信息");
		super.save(prpCopyCarDriver);
	}

	public void save(List<PrpCopyCarDriver> list) throws Exception {
		logger.info("车辆驾驶员关系信息");
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

	public void delete(PrpCopyCarDriverId prpCopyCarDriverId) throws Exception {
		logger.info("删除车辆驾驶员关系编号为" + prpCopyCarDriverId + "的车辆驾驶员关系");
		super.deleteByPK(PrpCopyCarDriver.class, prpCopyCarDriverId);
	}

	public PrpCopyCarDriver findPrpCopyCarDriver(PrpCopyCarDriverId prpCopyCarDriverId) throws Exception {
		logger.info("查询车辆驾驶员关系编号为" + prpCopyCarDriverId + "的车辆驾驶员关系");
		return super.get(PrpCopyCarDriver.class, prpCopyCarDriverId);
	}

	public Page findPrpCopyCarDriver(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		logger.info("获取车辆驾驶员关系列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	public List<PrpCopyCarDriver> findPrpCopyCarDriver(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}
}
