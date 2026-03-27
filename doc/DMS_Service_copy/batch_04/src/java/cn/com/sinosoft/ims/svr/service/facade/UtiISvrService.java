package cn.com.sinosoft.ims.svr.service.facade;

import ins.framework.common.Page;

import java.util.List;
import java.util.Map;

import cn.com.sinosoft.ims.svr.model.UtiISvr;

public interface UtiISvrService {
	public Page getSvrList(UtiISvr svr,String userCode, int pageNo, int pageSize);

	public UtiISvr getSvrByCode(String svrcode);
	
	public UtiISvr findSvrByCode(String svrCode);

	public String insertSvrMethod(String userCode, String systemCode,
			UtiISvr utiIsvr);

	public void modifySvrMethod(String userCode, UtiISvr utiIsvr);

	public void changeValids(UtiISvr utiISvr);

	public void changeStatus(String[] svrcodes, String id, UtiISvr utiISvr);

	public String getUserNameByCode(String userCode);

	public String getCompanyNameByCode(String companyCode);

	public Map getCompanyCodeMap();

	public String getSystemCodeByName(String systemName);

	public List getCompanyCodeLists();

	public Map getSvrList(String userCode);
	
	public Map getSvrListMap(String userCode);
	
	public Map getCompanyListMap();

	public UtiISvr getUtiISvrByCode(String userBelComCode);
	
	public List<String> getUtiISvr(String userCode);
	
	public String getSvrCodes(String userCode);
//	public Map getUtilityDBCodeMap();
}
