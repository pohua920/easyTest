package cn.com.sinosoft.dms.service.facade;

import java.util.List;

import ins.framework.common.Page;
import cn.com.sinosoft.dms.model.PrpdBasicMedical;
import cn.com.sinosoft.dms.model.PrpdInjuryDefine;
import cn.com.sinosoft.dms.model.PrpdInjuryDuty;
import cn.com.sinosoft.dms.model.PrpdInjuryRate;
import cn.com.sinosoft.dms.model.PrpdRegulation;

public interface PrpDregulationService {

	public Page PrpDregulationList(PrpdRegulation prpdRegulation,int pageNo, int pageSize);
	
	public Page checkPrpDregulationList(PrpdRegulation prpdRegulation,int pageNo, int pageSize);

	public void insertPrpdRegulation(String usercode,PrpdRegulation prpdRegulation,List<PrpdInjuryDefine> prpdInjuryDefines,List<PrpdInjuryRate> prpdInjuryRates,List<PrpdInjuryDuty> prpdInjuryDuties,List<PrpdBasicMedical> prpdBasicMedicals);

	public PrpdRegulation findByPrimaryKey(String prpdRegulationCode);
	
	public void updatePrpdRegulation(String usercode,PrpdRegulation prpdRegulation,List<PrpdInjuryDefine> prpdInjuryDefines,List<PrpdInjuryRate> prpdInjuryRates,List<PrpdInjuryDuty> prpdInjuryDuties,List<PrpdBasicMedical> prpdBasicMedicals);
	//modify by duanfa20110915
	public void changeRegulationStatus(String userCode,String regulationCode);

	public void checkPassAll(String[] regulationCodes,String comments);
	public void checkRejectAll(String[] regulationCodes,String comments);
}
