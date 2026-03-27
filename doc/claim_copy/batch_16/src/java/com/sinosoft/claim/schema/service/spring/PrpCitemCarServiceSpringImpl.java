package com.sinosoft.claim.schema.service.spring;

/**
 * 机动车险标的接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;
import java.util.List;
import com.sinosoft.claim.schema.model.PrpCitemCar;
import com.sinosoft.claim.schema.model.PrpCitemCarId;
import com.sinosoft.claim.schema.service.facade.PrpCitemCarService;

public class PrpCitemCarServiceSpringImpl extends GenericDaoHibernate<PrpCitemCar, PrpCitemCarId> implements PrpCitemCarService {

	public void save(PrpCitemCar prpCitemCar) throws Exception {
		logger.info("机动车险标的信息");
		super.save(prpCitemCar);
	}

	public void save(List<PrpCitemCar> list) throws Exception {
		logger.info("机动车险标的信息");
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

	public void delete(PrpCitemCarId prpCitemCarId) throws Exception {
		logger.info("删除机动车险标的编号为" + prpCitemCarId + "的机动车险标的");
		super.deleteByPK(PrpCitemCar.class, prpCitemCarId);
	}

	public PrpCitemCar findPrpCitemCar(PrpCitemCarId prpCitemCarId) throws Exception {
		logger.info("查询机动车险标的编号为" + prpCitemCarId + "的机动车险标的");
		return super.get(PrpCitemCar.class, prpCitemCarId);
	}

	public Page findPrpCitemCar(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		logger.info("获取机动车险标的列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	public List<PrpCitemCar> findPrpCitemCar(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}

	@Override
	public PrpCitemCar findPrpCitemCar(String policyNo, Integer itemNo) {
		return super.get(PrpCitemCar.class, new PrpCitemCarId(policyNo,itemNo));
	}
}
