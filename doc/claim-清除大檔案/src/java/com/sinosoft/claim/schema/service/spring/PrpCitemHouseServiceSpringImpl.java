package com.sinosoft.claim.schema.service.spring;

/**
 * 房屋标的信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;
import java.util.List;
import com.sinosoft.claim.schema.model.PrpCitemHouse;
import com.sinosoft.claim.schema.model.PrpCitemHouseId;
import com.sinosoft.claim.schema.service.facade.PrpCitemHouseService;

public class PrpCitemHouseServiceSpringImpl extends GenericDaoHibernate<PrpCitemHouse, PrpCitemHouseId> implements PrpCitemHouseService {

	public void save(PrpCitemHouse prpCitemHouse) throws Exception {
		logger.info("房屋标的信息信息");
		super.save(prpCitemHouse);
	}

	public void save(List<PrpCitemHouse> list) throws Exception {
		logger.info("房屋标的信息");
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

	public void delete(PrpCitemHouseId prpCitemHouseId) throws Exception {
		logger.info("删除房屋标的信息编号为" + prpCitemHouseId + "的房屋标的信息");
		super.deleteByPK(PrpCitemHouse.class, prpCitemHouseId);
	}

	public PrpCitemHouse findPrpCitemHouse(PrpCitemHouseId prpCitemHouseId) throws Exception {
		logger.info("查询房屋标的信息编号为" + prpCitemHouseId + "的房屋标的信息");
		return super.get(PrpCitemHouse.class, prpCitemHouseId);
	}

	public Page findPrpCitemHouse(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		logger.info("获取房屋标的信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	public List<PrpCitemHouse> findPrpCitemHouse(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}
}
