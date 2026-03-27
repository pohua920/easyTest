package cn.com.sinosoft.dms.service.facade;

import ins.framework.common.Page;

import java.util.List;

import cn.com.sinosoft.dms.model.PrpDcompanyTrace;

public interface PrpDcompanyTraceService {

	//获得所有申请
	public List<PrpDcompanyTrace> getPrpDcompanyTraceList();
	
	//获得所有申请，并带有查询条件
	public Page getPrpDcompanyTraceList(PrpDcompanyTrace prpDcompanyTrace, int pageNo,
			int pageSize);
	//添加新的申请
	public void insertPrpDcompanyTrace(PrpDcompanyTrace prpDcompanyTrace);
	
	//修改新的申请
	public void updatePrpDcompanyTrace(PrpDcompanyTrace prpDcompanyTrace);
	
	//根据编码查出实体(针对于维护机构申请页面）
	public PrpDcompanyTrace findByPrimaryKey(Integer serialNo );
		
	//查询未审核的申请
	public Page getPrpDcompanyTraceNotAuditList(PrpDcompanyTrace prpDcompanyTrace,int pageNo,int pageSize);
	
	//查找PrpDcompanyTrace表中最大ID(即序号）
	public Integer findByMaxId(String className,String key);
	
}
