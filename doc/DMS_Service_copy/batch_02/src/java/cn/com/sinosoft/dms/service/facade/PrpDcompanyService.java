package cn.com.sinosoft.dms.service.facade;

import ins.framework.common.Page;

import java.util.List;
import java.util.Map;

import cn.com.sinosoft.dms.model.PrpDcompany;

public interface PrpDcompanyService {

	public List<PrpDcompany> getPrpDcompanyList();//通过返回的公司所有信息通过 upercode 生成树。

	public PrpDcompany getPrpDcompany(String comCode);
	
	public PrpDcompany getPrpDcompany1(String comCode);

	public void insertPrpDcompany(PrpDcompany prpDcompany,String userCode);

	public void updatePrpDcompany(PrpDcompany prpDcompany,String userCode);

	public void deletePrpDcompany(String comCode);

	public Page getPrpDcompanyList(PrpDcompany prpDcompany, int pageNo,
			int pageSize);
	
//	---------------------------------------------------------
	public void generatePrpDcompanyGrade(PrpDcompany prpDcompany);
	
	public void insertGrade(PrpDcompany prpDcompany, PrpDcompany newCompany);
	
	public List getSubCode(String upCode);
	
	public List getSubCode1(String upCode);
	
	public List getSubSystemListByParentId(String parentId,String userCode)throws Exception;
	
	public List getAllSubCompany(String comCode);
	
	public int getLv(PrpDcompany prpDcompany);
	
	public PrpDcompany getUpprpDcompany(PrpDcompany prpDcompany);

	public Map<String, String> upCodeMap(String comCode);
	
	public boolean isHadUser(String comCode);
	
	public void prpDcompanyMessageProcess(PrpDcompany prpDcompany)throws Exception;
	
	public  String addPower(String userCode) throws Exception;
}
