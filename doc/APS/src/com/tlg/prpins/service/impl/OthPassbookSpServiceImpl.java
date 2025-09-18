package com.tlg.prpins.service.impl;

import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.OthPassbookSpDao;
import com.tlg.prpins.service.OthPassbookSpService;
import com.tlg.util.Constants;
import com.tlg.util.Message;

/** mantis：OTH0131，處理人員：BJ085，需求單編號：OTH0131 保發中心-保單存摺各險寫入中介Table作業 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class OthPassbookSpServiceImpl implements OthPassbookSpService{

	private OthPassbookSpDao othPassbookSpDao;

	@Override
	public int runSpOthPassbookMarP(Map<String, Object> params) throws Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.PARAMETER_ERROR));
		}
		
		othPassbookSpDao.runSpOthPassbookMarP(params);
		
		int outResult = 1;
		if(params.containsKey("outResult")) {
			outResult = (Integer)params.get("outResult");
		}
		return outResult;
	}

	@Override
	public int runSpOthPassbookMarE(Map<String, Object> params) throws Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.PARAMETER_ERROR));
		}
		
		othPassbookSpDao.runSpOthPassbookMarE(params);
		
		int outResult = 1;
		if(params.containsKey("outResult")) {
			outResult = (Integer)params.get("outResult");
		}
		
		return outResult;
	}

	@Override
	public int runSpOthPassbookFirP(Map<String, Object> params) throws Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.PARAMETER_ERROR));
		}
		
		othPassbookSpDao.runSpOthPassbookFirP(params);
		
		int outResult = 0;
		if(params.containsKey("outResult")) {
			outResult = (Integer)params.get("outResult");
		}
		return outResult;
	}

	@Override
	public int runSpOthPassbookFirE(Map<String, Object> params) throws Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.PARAMETER_ERROR));
		}
		
		othPassbookSpDao.runSpOthPassbookFirE(params);
		
		int outResult = 1;
		if(params.containsKey("outResult")) {
			outResult = (Integer)params.get("outResult");
		}
		return outResult;
	}

	@Override
	public int runSpOthPassbookCalP(Map<String, Object> params) throws Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.PARAMETER_ERROR));
		}
		
		othPassbookSpDao.runSpOthPassbookCalP(params);
		
		int outResult = 0;
		if(params.containsKey("outResult")) {
			outResult = (Integer)params.get("outResult");
		}
		return outResult;
	}

	@Override
	public int runSpOthPassbookCalE(Map<String, Object> params) throws Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.PARAMETER_ERROR));
		}
		
		othPassbookSpDao.runSpOthPassbookCalE(params);
		
		int outResult = 0;
		if(params.containsKey("outResult")) {
			outResult = (Integer)params.get("outResult");
		}
		return outResult;
	}

	@Override
	public int runSpOthPassbookCarP(Map<String, Object> params) throws Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.PARAMETER_ERROR));
		}
		
		othPassbookSpDao.runSpOthPassbookCarP(params);
		
		int outResult = 0;
		if(params.containsKey("outResult")) {
			outResult = (Integer)params.get("outResult");
		}
		return outResult;
	}

	@Override
	public int runSpOthPassbookCarE(Map<String, Object> params) throws Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.PARAMETER_ERROR));
		}
		
		othPassbookSpDao.runSpOthPassbookCarE(params);
		
		int outResult = 0;
		if(params.containsKey("outResult")) {
			outResult = (Integer)params.get("outResult");
		}
		return outResult;
	}

	@Override
	public int runSpOthPassbookLopP(Map<String, Object> params) throws Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.PARAMETER_ERROR));
		}
		
		othPassbookSpDao.runSpOthPassbookLopP(params);
		
		int outResult = 0;
		if(params.containsKey("outResult")) {
			outResult = (Integer)params.get("outResult");
		}
		return outResult;
	}

	@Override
	public int runSpOthPassbookLopE(Map<String, Object> params) throws Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.PARAMETER_ERROR));
		}
		
		othPassbookSpDao.runSpOthPassbookLopE(params);
		
		int outResult = 0;
		if(params.containsKey("outResult")) {
			outResult = (Integer)params.get("outResult");
		}
		return outResult;
	}
	
	public OthPassbookSpDao getOthPassbookSpDao() {
		return othPassbookSpDao;
	}

	public void setOthPassbookSpDao(OthPassbookSpDao othPassbookSpDao) {
		this.othPassbookSpDao = othPassbookSpDao;
	}
}
