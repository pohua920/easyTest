package cn.com.sinosoft.dms.service.spring;

import ins.framework.dao.GenericDaoHibernate;
import cn.com.sinosoft.dms.model.PrpDstatistics;
import cn.com.sinosoft.dms.service.facade.PrpDstatisticsService;

public class PrpDstatisticsServiceSpringImpl extends
		GenericDaoHibernate<PrpDstatistics, String> implements
		PrpDstatisticsService {

	public PrpDstatistics findByPrimaryKey(String makeCom) {
		PrpDstatistics prpDstatistics = super.get(makeCom);
		return prpDstatistics;
	}

	public void insertPrpDstatistics(PrpDstatistics prpDstatistics) {
		super.save(prpDstatistics);
	}

	public void updatePrpDstatistics(PrpDstatistics prpDstatistics) {
		super.update(prpDstatistics);
	}

}
