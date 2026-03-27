package cn.com.sinosoft.dms.service.facade;

import java.util.List;

import ins.framework.common.Page;
import cn.com.sinosoft.dms.model.PrpDtreatyReten;
import cn.com.sinosoft.dms.model.PrpDtreatyRetenId;

public interface PrpDTreatyRetenService {

	public Page PrpDTreatyRetenList(PrpDtreatyReten prpDtreatyReten,int pageNo, int pageSize);

	public void insertPrpDTreatyReten(PrpDtreatyReten prpDtreatyReten,String userCode);

	public PrpDtreatyReten findByPrimaryKey(PrpDtreatyRetenId prpDtreatyRetenId);

	public void updatePrpDTreatyReten(PrpDtreatyReten prpDtreatyReten,String userCode);

	public List<PrpDtreatyRetenId> riskCodeList(String codeType);

	public void prpDTreatyRetenMessageProcess(PrpDtreatyReten prpDTreatyReten) throws Exception;


}
