package cn.com.sinosoft.dms.service.facade;

import ins.framework.common.Page;

import java.util.Date;
import java.util.List;

import cn.com.sinosoft.dms.model.PrpDexch;
import cn.com.sinosoft.dms.model.PrpDexchId;

public interface PrpDexchService {

    public Page getPrpDexchList(PrpDexch prpDexch, int pageNo, int pageSize);

    public PrpDexch findByPrimaryKey(PrpDexchId prpDexch);

    public PrpDexch getLastPrpDexch(Date currDate, String baseCurrency, String exchCurrency);
    
    public PrpDexch getLastPrpDexchs(Date currDate, String baseCurrency, String exchCurrency);

    public void updatePrpDexch(PrpDexch prpDexch,String userCode);

    public void deletePrpDexch(PrpDexch prpDexch);

    public void insertPrpDexch(PrpDexch prpDexch,String userCode);

    public void deleteByPK(PrpDexchId prpDexch);

	public boolean isSameKey(PrpDexchId id);
	
	public void deleteAll(List list);
	public void prpDexchMessageProcess(PrpDexch prpDexch)throws Exception;
}
