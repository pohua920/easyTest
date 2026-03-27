package cn.com.sinosoft.dms.service.facade;

import java.util.List;
import java.util.Map;

import ins.framework.common.Page;
import cn.com.sinosoft.dms.model.PrpDnewCode;
import cn.com.sinosoft.dms.model.PrpDnewCodeCom;
import cn.com.sinosoft.dms.model.PrpDnewCodeId;

public interface PrpDcodeService {
	public Page getPrpDcodeList(PrpDnewCode prpDcode, int pageNo, int pageSize);

    public PrpDnewCode findByPrimaryKey(PrpDnewCodeId prpDcodeId);

    public PrpDnewCode findByPrimaryKey1(PrpDnewCodeId prpDcodeId);
    
    public void updatePrpDcode(PrpDnewCode prpDcode,String userCode);
    
    public void deletePrpDcode(PrpDnewCode prpDcode) throws Exception;

	public void insertPrpDcode(PrpDnewCode prpDcode,String userCode);
	
	public void deleteByPK(PrpDnewCodeId prpDcodeId);

	public PrpDnewCode getUpcode(String uplevel, String codeType);
	
//	public String getCodeLevel(String systemCode, String codeType, String codeCode);
	public void insertPrpDcode(PrpDnewCode prpDcode,String upcode,String userCode) throws Exception;
	
	public void updatePrpDcode(PrpDnewCode prpDcode,String upcode,String userCode) throws Exception;

	public List<PrpDnewCode> findByHql(String string, String codeType);

	public Map<String, String> upCodeMap(String codeType);

	public String getuplevel(PrpDnewCodeId prpDcodeId) throws Exception;
	
	public List<PrpDnewCode> getSubCode(String codeType,String codeCode);

	public List<PrpDnewCode> codeList(String codeType);//获得当前代码类型的所有代码信息列表。
	
	public void deleteAll(List list) throws Exception;
	
	//添加省颁代码并清分到分公司 by wanghaibo
	public void insertPrpDnewCodeCom(PrpDnewCodeCom prpDnewCodeCom, String userCode);
	
	public void prpdCodeMessageProcess(PrpDnewCode prpDcode)throws Exception;
	//清分prpdnewcodecom 到 分公司prpdnewcode表中
	public void prpDnewCodeComMessageProcess(PrpDnewCodeCom prpDnewCodeCom)throws Exception;
	
}
