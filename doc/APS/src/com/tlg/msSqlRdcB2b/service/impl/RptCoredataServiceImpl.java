package com.tlg.msSqlRdcB2b.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.msSqlRdcB2b.dao.RptCoredataDao;
import com.tlg.msSqlRdcB2b.entity.RptCoredata;
import com.tlg.msSqlRdcB2b.service.RptCoredataService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.Result;
//mantis：OTH0175，處理人員：DP0706，需求單編號：OTH0175_APS-收件收件報備系統 已出單資料回拋B2B
@Transactional(value = "msSqlRdmTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class RptCoredataServiceImpl implements RptCoredataService{
	
	private RptCoredataDao rptCoredataDao;
	
	@Override
	public Result insertRptCoredata(List<RptCoredata> rptCoredatas) throws SystemException, Exception {
		for(RptCoredata entity : rptCoredatas) {
			rptCoredataDao.insert(entity);
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(rptCoredatas);
		return result;
	}


	public RptCoredataDao getRptCoredataDao() {
		return rptCoredataDao;
	}

	public void setRptCoredataDao(RptCoredataDao rptCoredataDao) {
		this.rptCoredataDao = rptCoredataDao;
	}


}
