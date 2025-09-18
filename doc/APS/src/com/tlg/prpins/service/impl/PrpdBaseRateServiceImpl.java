package com.tlg.prpins.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.PrpdBaseRateDao;
import com.tlg.prpins.entity.PrpdBaseRate;
import com.tlg.prpins.service.PrpdBaseRateService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class PrpdBaseRateServiceImpl implements PrpdBaseRateService{

	private PrpdBaseRateDao prpdBaseRateDao;

	@Override
	public int countPrpdBaseRate(Map params) throws SystemException, Exception {
		return prpdBaseRateDao.count(params);
	}

	@Override
	public Result findPrpdBaseRateByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = prpdBaseRateDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<PrpdBaseRate> searchResult = prpdBaseRateDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findPrpdBaseRateByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<PrpdBaseRate> searchResult = prpdBaseRateDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}


	public PrpdBaseRateDao getPrpdBaseRateDao() {
		return prpdBaseRateDao;
	}

	public void setPrpdBaseRateDao(PrpdBaseRateDao prpdBaseRateDao) {
		this.prpdBaseRateDao = prpdBaseRateDao;
	}

}
