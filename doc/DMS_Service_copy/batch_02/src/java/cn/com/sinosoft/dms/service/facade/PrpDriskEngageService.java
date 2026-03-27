package cn.com.sinosoft.dms.service.facade;

import ins.framework.common.Page;

import java.util.List;

import cn.com.sinosoft.dms.model.PrpDcompanyCheck;
import cn.com.sinosoft.dms.model.PrpDcrossOrg;
import cn.com.sinosoft.dms.model.PrpDriskEngage;
import cn.com.sinosoft.dms.model.PrpDriskEngageId;

public interface PrpDriskEngageService {
	
	//查询所有数据，带有查询条件
	public Page getPrpDriskEngageList(PrpDriskEngage prpDriskEngage, String userCode,int pageNo, int pageSize)throws Exception;
	
	//根据产品代码查询
	public PrpDriskEngage findByPrimaryKey(PrpDriskEngageId prpDriskEngageId);
	
	public PrpDriskEngage findByPrimaryKey1(String riskCode);
	
	//修改
	public void updatePrpDriskEngage(PrpDriskEngage prpDriskEngage,String userCode);
	
	//新增
	public void insertPrpDriskEngage(PrpDriskEngage prpDriskEngage,String userCode);
	
	public void prpDriskEngageMessageProcess(PrpDriskEngage prpDriskEngage)throws Exception;
	
	//同步产品信息的清分功能
	public void synchroRiskDataMessageProcess(List accountInfoList, List areaList, List riskList,
			List riskClauseList,List riskClauseKindList, List riskClauseKindRelationList,List riskEngageList,
			List riskItemList,List riskLimitList,List riskShortRateList,List newCodeRiskList,
			List prpdrckratelowerList)throws Exception;
	
	//同步险种信息的清分功能
	public void synchroClassDataMessageProcess(List classList)throws Exception;
	
	//同步方案信息的清分功能
	public void synchroPlanDataMessageProcess(List prpDplanList,List prpDplanClauseKindList,List prpDplanLimitList)throws Exception;
	
	//addPower用来限制查询结果（允许机构之内的数据）
	public  String addPower(String userCode) throws Exception;
}
