package cn.com.sinosoft.dms.service.facade;

import cn.com.sinosoft.dms.model.PrpDstatistics;

public interface PrpDstatisticsService {
    public PrpDstatistics findByPrimaryKey(String licenceNo);
    
    public void updatePrpDstatistics(PrpDstatistics PrpDstatistics);
    
	public void insertPrpDstatistics(PrpDstatistics PrpDstatistics);
	
}
