package com.sinosoft.claim.schema.service.spring;

/**
 * 车辆驾驶员关系接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;
import java.util.List;
import com.sinosoft.claim.schema.model.PrpCcarDriver;
import com.sinosoft.claim.schema.model.PrpCcarDriverId;
import com.sinosoft.claim.schema.service.facade.PrpCcarDriverService;

public class PrpCcarDriverServiceSpringImpl extends GenericDaoHibernate<PrpCcarDriver, PrpCcarDriverId> implements PrpCcarDriverService {

	public void save(PrpCcarDriver prpCcarDriver) throws Exception {
		logger.info("车辆驾驶员关系信息");
		super.save(prpCcarDriver);
	}

	public void save(List<PrpCcarDriver> list) throws Exception {
		logger.info("车辆驾驶员关系信息");
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

	public void delete(PrpCcarDriverId prpCcarDriverId) throws Exception {
		logger.info("删除车辆驾驶员关系编号为" + prpCcarDriverId + "的车辆驾驶员关系");
		super.deleteByPK(PrpCcarDriver.class, prpCcarDriverId);
	}

	public PrpCcarDriver findPrpCcarDriver(PrpCcarDriverId prpCcarDriverId) throws Exception {
		logger.info("查询车辆驾驶员关系编号为" + prpCcarDriverId + "的车辆驾驶员关系");
		return super.get(PrpCcarDriver.class, prpCcarDriverId);
	}

	public Page findPrpCcarDriver(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		logger.info("获取车辆驾驶员关系列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	public List<PrpCcarDriver> findPrpCcarDriver(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}
}
