package com.tlg.db2.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.db2.dao.As400FilCustDao;
import com.tlg.db2.entity.As400FilToRptCoredata;
import com.tlg.db2.service.As400FilCustService;
import com.tlg.exception.SystemException;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.Result;

/** mantis：OTH0175，處理人員：DP0706，需求單編號：OTH0175_APS-收件收件報備系統 已出單資料回拋B2B*/
@Transactional(value = "db2TransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class As400FilCustServiceImpl implements As400FilCustService {

	private static final Logger logger = Logger.getLogger(As400FilCustServiceImpl.class);
	
	private As400FilCustDao as400FilCustDao;
	
	@Override
	public Result findAs400FilByCustQueryStr(Map<String, Object> qryMap) throws SystemException, Exception {
		Result result = new Result();
		List<As400FilToRptCoredata> as400FilToRptCoredatas = as400FilCustDao.selectAs400FilByCustQueryStr(qryMap);
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(as400FilToRptCoredatas);
		return result;
	}

	public As400FilCustDao getAs400FilCustDao() {
		return as400FilCustDao;
	}

	public void setAs400FilCustDao(As400FilCustDao as400FilCustDao) {
		this.as400FilCustDao = as400FilCustDao;
	}
	
	
}
