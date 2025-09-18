package com.tlg.prpins.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.PrpcinsuredContactChk0Dao;
import com.tlg.prpins.entity.PrpcinsuredContactChk0;
import com.tlg.prpins.service.PrpcinsuredContactChk0Service;
import com.tlg.sales.entity.PrpdCompany;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/**mantis：OTH0106，處理人員：BJ085，需求單編號：OTH0106 要被保人通訊資料比對確認作業*/
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class PrpcinsuredContactChk0ServiceImpl implements PrpcinsuredContactChk0Service{
	private PrpcinsuredContactChk0Dao prpcinsuredContactChk0Dao;

	@Override
	public Result findPrpcinsuredContactChk0ByPageInfo(PageInfo pageInfo) throws SystemException, Exception {
		Result result = new Result();
		if (null == pageInfo) {
			//mantis：OTH0184，處理人員：DP0706，需求單編號：APS要被保人通訊地址比對作業增加資料來源(XCHG)START
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
			//mantis：OTH0184，處理人員：DP0706，需求單編號：APS要被保人通訊地址比對作業增加資料來源(XCHG)END
		}
		int rowCount = prpcinsuredContactChk0Dao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();
		
		List<PrpcinsuredContactChk0> searchResult = prpcinsuredContactChk0Dao.findByPageInfo(pageInfo);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result findPrpcinsuredContactChk0ByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<PrpcinsuredContactChk0> searchResult = prpcinsuredContactChk0Dao.findByParams(params);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Result findPrpcinsuredContactChk0ByUk(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		PrpcinsuredContactChk0 prpcinsuredContactChk0 = prpcinsuredContactChk0Dao.findByUK(params);
		if (null == prpcinsuredContactChk0) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(prpcinsuredContactChk0);
		}
		return result;
	}

	@Override
	public Result updatePrpcinsuredContactChk0(PrpcinsuredContactChk0 prpcinsuredContactChk0) throws SystemException, Exception {

		if (prpcinsuredContactChk0 == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = prpcinsuredContactChk0Dao.update(prpcinsuredContactChk0);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(prpcinsuredContactChk0);
		return result;
	}
	
	/**
	 * mantis：OTH0184，處理人員：DP0706，需求單編號：APS要被保人通訊地址比對作業增加資料來源(XCHG)
	 */
	@Override
	public Result findPrpdCompany(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<PrpdCompany> searchResult = prpcinsuredContactChk0Dao.findPrpdCompany(params);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	public PrpcinsuredContactChk0Dao getPrpcinsuredContactChk0Dao() {
		return prpcinsuredContactChk0Dao;
	}

	public void setPrpcinsuredContactChk0Dao(PrpcinsuredContactChk0Dao prpcinsuredContactChk0Dao) {
		this.prpcinsuredContactChk0Dao = prpcinsuredContactChk0Dao;
	}
	
}
