package com.tlg.prpins.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.PrpdcodeDao;
import com.tlg.prpins.entity.Prpdcode;
import com.tlg.prpins.service.PrpdcodeService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class PrpdcodeServiceImpl implements PrpdcodeService{

	private PrpdcodeDao prpdcodeDao;
	
	@SuppressWarnings("rawtypes")
	@Override
	public int countPrpdcode(Map params) throws SystemException, Exception {
		return prpdcodeDao.count(params);
	}
	
	@Override
	public Result findPrpdcodeByPageInfo(PageInfo pageInfo) throws SystemException, Exception {
		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = prpdcodeDao.count(pageInfo.getFilter());
		if (rowCount == 0) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<Prpdcode> searchResult = prpdcodeDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result findPrpdcodeByParams(Map params) throws SystemException, Exception {
		Result result = new Result();
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		List<Prpdcode> searchResult = prpdcodeDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
		

	public PrpdcodeDao getPrpdcodeDao() {
		return prpdcodeDao;
	}

	public void setPrpdcodeDao(PrpdcodeDao prpdcodeDao) {
		this.prpdcodeDao = prpdcodeDao;
	}

}
