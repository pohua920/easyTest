package com.tlg.prpins.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.PrpLuserDao;
import com.tlg.prpins.entity.PrpLuser;
import com.tlg.prpins.service.PrpLuserService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.Result;

/**
 * mantis：CLM0168，處理人員：BI086，需求單編號：CLM0168  區塊鏈查詢、新增及更新攤賠案件排程
 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class PrpLuserServiceImpl implements PrpLuserService{

	private PrpLuserDao prpLuserDao;
	


	@SuppressWarnings("rawtypes")
	@Override
	public Result findPrpLuserByParams(Map params) throws SystemException, Exception {
		Result result = new Result();
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		List<PrpLuser> searchResult = prpLuserDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
		

	public PrpLuserDao getPrpLuserDao() {
		return prpLuserDao;
	}

	public void setPrpLuserDao(PrpLuserDao prpLuserDao) {
		this.prpLuserDao = prpLuserDao;
	}

}
