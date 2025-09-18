package com.tlg.prpins.service;

import java.util.Map;

import com.tlg.exception.SystemException;

public interface HasSpService {
	
	/** mantis：HAS0215，處理人員：CD094，需求單編號：HAS0215 TA雄獅回饋檔*/
	public int runSpHasAgtLionOP(Map<String,Object> params) throws SystemException, Exception;

	/** mantis：HAS0215，處理人員：CD094，需求單編號：HAS0215 TA雄獅回饋檔*/
	public int runSpHasAgtLionCH(Map<String,Object> params) throws SystemException, Exception;
	
	/** mantis：HAS0215，處理人員：CD094，需求單編號：HAS0215 TA雄獅回饋檔*/
	public int runSpHasAgtLionAC(Map<String,Object> params) throws SystemException, Exception;
		
	/** mantis：HAS0215，處理人員：CD094，需求單編號：HAS0215 TA雄獅回饋檔*/
	public int runSpHasAgtLionCM(Map<String,Object> params) throws SystemException, Exception;
	
	/** mantis：HAS0215，處理人員：CD094，需求單編號：HAS0215 TA雄獅回饋檔*/
	public int runSpHasAgtLionCL(Map<String,Object> params) throws SystemException, Exception;

	// mantis：OTH0161，處理人員：DP0714，錠嵂保經全險種回饋檔
	public int runSpHasAgtLawPol(Map<String, Object> params) throws SystemException, Exception;

	/** mantis：HAS0284，處理人員：DP0706，需求單編號：HAS0284_遠傳優化需求-比對同要保人但生日或姓名不同*/
	public int runSpHasBatchMatchFet(Map<String,Object> params) throws SystemException, Exception;
}
