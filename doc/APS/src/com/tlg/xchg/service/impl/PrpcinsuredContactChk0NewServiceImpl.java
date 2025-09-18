package com.tlg.xchg.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.xchg.dao.PrpcinsuredContactChk0NewDao;
import com.tlg.xchg.entity.PrpcinsuredContactChk0New;
import com.tlg.xchg.service.PrpcinsuredContactChk0NewService;

/**mantis：OTH0184，處理人員：DP0706，需求單編號：APS要被保人通訊地址比對作業增加資料來源(XCHG)*/
@Transactional(value = "xchgTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class PrpcinsuredContactChk0NewServiceImpl implements PrpcinsuredContactChk0NewService{
	private PrpcinsuredContactChk0NewDao prpcinsuredContactChk0NewDao;

	@Override
	public Result findPrpcinsuredContactChk0ByPageInfo(PageInfo pageInfo) throws SystemException, Exception {
		Result result = new Result();
		if (null == pageInfo) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		int rowCount = prpcinsuredContactChk0NewDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();
		
		List<PrpcinsuredContactChk0New> searchResult = prpcinsuredContactChk0NewDao.findByPageInfo(pageInfo);
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
		List<PrpcinsuredContactChk0New> searchResult = prpcinsuredContactChk0NewDao.findByParams(params);
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
		PrpcinsuredContactChk0New prpcinsuredContactChk0 = prpcinsuredContactChk0NewDao.findByUK(params);
		if (null == prpcinsuredContactChk0) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(prpcinsuredContactChk0);
		}
		return result;
	}

	@Override
	public Result updatePrpcinsuredContactChk0(PrpcinsuredContactChk0New prpcinsuredContactChk0) throws SystemException, Exception {

		if (prpcinsuredContactChk0 == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = prpcinsuredContactChk0NewDao.update(prpcinsuredContactChk0);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(prpcinsuredContactChk0);
		return result;
	}

	public PrpcinsuredContactChk0NewDao getPrpcinsuredContactChk0NewDao() {
		return prpcinsuredContactChk0NewDao;
	}

	public void setPrpcinsuredContactChk0NewDao(PrpcinsuredContactChk0NewDao prpcinsuredContactChk0NewDao) {
		this.prpcinsuredContactChk0NewDao = prpcinsuredContactChk0NewDao;
	}

	
}
