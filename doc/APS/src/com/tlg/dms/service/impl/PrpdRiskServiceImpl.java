package com.tlg.dms.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.dms.dao.PrpdRiskDao;
import com.tlg.dms.entity.PrpdRisk;
import com.tlg.dms.service.PrpdRiskService;
import com.tlg.exception.SystemException;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class PrpdRiskServiceImpl implements PrpdRiskService{
	/* mantis：OTH0087，處理人員：BJ085，需求單編號：OTH0087 AML手動登錄 start */
	private PrpdRiskDao prpdRiskDao;

	@Override
	public int countPrpdRisk(Map params) throws SystemException, Exception {
		return prpdRiskDao.count(params);
	}

	@Override
	public Result findPrpdRiskByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = prpdRiskDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<PrpdRisk> searchResult = prpdRiskDao.findByPageInfo(pageInfo);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findPrpdRiskByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<PrpdRisk> searchResult = prpdRiskDao.findByParams(params);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	public PrpdRiskDao getPrpdRiskDao() {
		return prpdRiskDao;
	}

	public void setPrpdRiskDao(PrpdRiskDao prpdRiskDao) {
		this.prpdRiskDao = prpdRiskDao;
	}

}
