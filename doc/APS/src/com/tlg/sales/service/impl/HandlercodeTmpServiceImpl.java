package com.tlg.sales.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.sales.dao.HandlercodeTmpDao;
import com.tlg.sales.entity.HandlercodeTmp;
import com.tlg.sales.service.HandlercodeTmpService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.Result;

/** mantis：SALES0007，處理人員：BJ085，需求單編號：SALES0007 新增業務人員所屬服務人員維護*/
@Transactional(value="salesTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class HandlercodeTmpServiceImpl implements HandlercodeTmpService{
	
	private HandlercodeTmpDao handlercodeTmpDao;

	@Override
	public Result findHandlercodeTmpByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<HandlercodeTmp> searchResult = handlercodeTmpDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public Result findHandlercodeIsValidByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		Map searchResult = handlercodeTmpDao.findHandlercodeIsValidByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	public HandlercodeTmpDao getHandlercodeTmpDao() {
		return handlercodeTmpDao;
	}

	public void setHandlercodeTmpDao(HandlercodeTmpDao handlercodeTmpDao) {
		this.handlercodeTmpDao = handlercodeTmpDao;
	}

}
