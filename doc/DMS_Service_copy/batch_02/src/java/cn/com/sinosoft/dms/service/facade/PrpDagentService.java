package cn.com.sinosoft.dms.service.facade;

import ins.framework.common.Page;

import java.util.List;

import cn.com.sinosoft.dms.model.PrpDagent;
import cn.com.sinosoft.dms.model.PrpDagentAll;
import cn.com.sinosoft.dms.model.PrpDagentExt;
import cn.com.sinosoft.dms.model.PrpDcontractManage;

public interface PrpDagentService {
	public Page getPrpDagentList(PrpDagent prpDagent, String userCode,int pageNo, int pageSize)throws Exception;
	
	public Page getPrpDagentAllList(PrpDagent prpDagent, String userCode,int pageNo, int pageSize)throws Exception;
	
    public PrpDagent findByPrimaryKey(String agentCode);
    
    public PrpDagentAll findByPrimaryKey2(String agentCode);
    
    public PrpDagent findByPrimaryKey1(String agentCode);

    public void updatePrpDagent(PrpDagent PrpDagent,String operUserCode);
    
    public void deletePrpDagent(PrpDagent PrpDagent);

	public void insertPrpDagent(PrpDagent PrpDagent,String operUserCode);
	
	public void deleteByPK(String PK);
	
	public void deleteAll(List list);
	
	public void prpDagentMessageProcess(PrpDagent prpDagent)throws Exception;
	//�����PrpDAgentAll��ֵ��ֹ�˾PrpDAgent by wanghaibo 2010-07-12
	public void prpDagentAllMessageProcess(PrpDagentAll prpDagentAll,List prpdAgentExtList,List prpdContractManageList)throws Exception;
	
	//addPower用来限制查询总公司结果（允许机构之内的数据）
	public  String addPowerAll(String userCode) throws Exception;
	
	//addPower用来限制查询分公司结果（允许机构之内的数据）
	public  String addPower(String userCode) throws Exception;
	
	public void qingFenSynPrpDAgentData(PrpDagentAll prpDagent,List prpdAgentExtList,List prpdContractManageList)throws Exception;
}
