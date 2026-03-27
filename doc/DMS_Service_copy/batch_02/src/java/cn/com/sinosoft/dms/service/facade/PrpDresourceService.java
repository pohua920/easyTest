package cn.com.sinosoft.dms.service.facade;

import ins.framework.common.Page;
import cn.com.sinosoft.dms.model.PrpDcompany;
import cn.com.sinosoft.dms.model.PrpDresource;

public interface PrpDresourceService {
	
	//查询所有数据，带有查询条件
	public Page getPrpDresourceList(PrpDresource prpDresource, String userCode,int pageNo, int pageSize)throws Exception;
	
	//根据专管专营代码查询
	public PrpDresource findByPrimaryKey(String resourceCode);
	
	public PrpDresource findByPrimaryKey1(String resourceCode);
	
	//修改
	public void updatePrpDresource(PrpDresource prpDresource,String userCode);
	
	//新增
	public void insertPrpDresource(PrpDresource prpDresource,String userCode);
	
	public void prpDresourceMessageProcess(PrpDresource prpDresource)throws Exception;
	
	//addPower用来限制查询结果（允许机构之内的数据）
	public  String addPower(String userCode) throws Exception;
}
