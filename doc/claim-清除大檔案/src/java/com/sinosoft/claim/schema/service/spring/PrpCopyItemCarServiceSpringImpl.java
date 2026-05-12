package com.sinosoft.claim.schema.service.spring;

/**
 * 机动车险标的接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;
import java.util.List;
import com.sinosoft.claim.schema.model.PrpCopyItemCar;
import com.sinosoft.claim.schema.model.PrpCopyItemCarId;
import com.sinosoft.claim.schema.service.facade.PrpCopyItemCarService;

public class PrpCopyItemCarServiceSpringImpl extends GenericDaoHibernate<PrpCopyItemCar, PrpCopyItemCarId> implements PrpCopyItemCarService {

	public void save(PrpCopyItemCar prpCopyItemCar) throws Exception {
		logger.info("机动车险标的信息");
		super.save(prpCopyItemCar);
	}

	public void save(List<PrpCopyItemCar> list) throws Exception {
		logger.info("机动车险标的信息");
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

	public void delete(PrpCopyItemCarId prpCopyItemCarId) throws Exception {
		logger.info("删除机动车险标的编号为" + prpCopyItemCarId + "的机动车险标的");
		super.deleteByPK(PrpCopyItemCar.class, prpCopyItemCarId);
	}

	public PrpCopyItemCar findPrpCopyItemCar(PrpCopyItemCarId prpCopyItemCarId) throws Exception {
		logger.info("查询机动车险标的编号为" + prpCopyItemCarId + "的机动车险标的");
		return super.get(PrpCopyItemCar.class, prpCopyItemCarId);
	}

	public Page findPrpCopyItemCar(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		logger.info("获取机动车险标的列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	public List<PrpCopyItemCar> findPrpCopyItemCar(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}
}
