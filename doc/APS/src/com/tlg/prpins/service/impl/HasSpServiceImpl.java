package com.tlg.prpins.service.impl;

import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.HasSpDao;
import com.tlg.prpins.service.HasSpService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
/** mantis：HAS0215，處理人員：CD094，需求單編號：HAS0215 TA雄獅回饋檔*/
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class HasSpServiceImpl implements HasSpService{

	private HasSpDao hasSpDao;


	@Override
	public int runSpHasAgtLionOP(Map<String, Object> params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.PARAMETER_ERROR));
		}
		
		hasSpDao.runSpHasAgtLionOP(params);
		
		int returnValue = 0;
		if(params.containsKey("outResult")) {
			returnValue = (Integer)params.get("outResult");
		}
		
		return returnValue;
	}


	@Override
	public int runSpHasAgtLionCH(Map<String, Object> params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.PARAMETER_ERROR));
		}
		
		hasSpDao.runSpHasAgtLionCH(params);
		
		int returnValue = 0;
		if(params.containsKey("outResult")) {
			returnValue = (Integer)params.get("outResult");
		}
		
		return returnValue;
	}

	@Override
	public int runSpHasAgtLionAC(Map<String, Object> params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.PARAMETER_ERROR));
		}
		
		hasSpDao.runSpHasAgtLionAC(params);
		
		int returnValue = 0;
		if(params.containsKey("outResult")) {
			returnValue = (Integer)params.get("outResult");
		}
		
		return returnValue;
	}


	@Override
	public int runSpHasAgtLionCM(Map<String, Object> params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.PARAMETER_ERROR));
		}
		
		hasSpDao.runSpHasAgtLionCM(params);
		
		int returnValue = 0;
		if(params.containsKey("outResult")) {
			returnValue = (Integer)params.get("outResult");
		}
		
		return returnValue;
	}

	@Override
	public int runSpHasAgtLionCL(Map<String, Object> params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.PARAMETER_ERROR));
		}
		
		hasSpDao.runSpHasAgtLionCL(params);
		
		int returnValue = 0;
		if(params.containsKey("outResult")) {
			returnValue = (Integer)params.get("outResult");
		}
		
		return returnValue;
	}

	// mantis：OTH0161，處理人員：DP0714，錠嵂保經全險種回饋檔 -- start
	@Override
	public int runSpHasAgtLawPol(Map<String, Object> params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.PARAMETER_ERROR));
		}
		
		hasSpDao.runSpHasAgtLawPol(params);
		
		int returnValue = 0;
		if(params.containsKey("outResult")) {
			returnValue = (Integer)params.get("outResult");
		}
		
		return returnValue;
	}
	// mantis：OTH0161，處理人員：DP0714，錠嵂保經全險種回饋檔 -- end
	
	/**
	 * mantis：HAS0284，處理人員：DP0706，需求單編號：HAS0284_遠傳優化需求-比對同要保人但生日或姓名不同
	 */
	@Override
	public int runSpHasBatchMatchFet(Map<String, Object> params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.PARAMETER_ERROR));
		}
		
		hasSpDao.runSpHasBatchMatchFet(params);
		
		int returnValue = 0;
		if(params.containsKey("outResult")) {
			returnValue = (Integer)params.get("outResult");
		}
		
		return returnValue;
	}

	public HasSpDao getHasSpDao() {
		return hasSpDao;
	}


	public void setHasSpDao(HasSpDao hasSpDao) {
		this.hasSpDao = hasSpDao;
	}

	
	

}
