package cn.com.sinosoft.dms.service.facade;

import ins.framework.common.Page;
import cn.com.sinosoft.dms.model.PrpDproject;

public interface PrpDprojectService {
	
	//查询所有数据，带有查询条件
	public Page getPrpDprojectList(PrpDproject prpDproject, String userCode,int pageNo, int pageSize) throws Exception;
	
	//根据项目代码查询
	public PrpDproject findByPrimaryKey(String projectCode);
	
	public PrpDproject findByPrimaryKey1(String projectCode);
	
	//修改
	public void updatePrpDproject(PrpDproject prpDproject,String userCode);
	
	//新增
	public void insertPrpDproject(PrpDproject prpDproject,String userCode);
	
	public void prpDprojectMessageProcess(PrpDproject prpDproject)throws Exception;
	
	//addPower用来限制查询结果（允许机构之内的数据）
	public  String addPower(String userCode) throws Exception;
	
}
