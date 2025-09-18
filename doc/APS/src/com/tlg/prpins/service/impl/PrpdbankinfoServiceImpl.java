package com.tlg.prpins.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.vo.BankInfoVo;
import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.PrpdbankinfoDao;
import com.tlg.prpins.entity.Prpdbankinfo;
import com.tlg.prpins.service.PrpdbankinfoService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class PrpdbankinfoServiceImpl implements PrpdbankinfoService{

	private PrpdbankinfoDao prpdbankinfoDao;

	@Override
	public int countPrpdbankinfo(Map params) throws SystemException, Exception {
		return prpdbankinfoDao.count(params);
	}

	@Override
	public Result findPrpdbankinfoByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = prpdbankinfoDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<Prpdbankinfo> searchResult = prpdbankinfoDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findPrpdbankinfoByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<Prpdbankinfo> searchResult = prpdbankinfoDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findPrpdbankinfoByParamsForWs(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<BankInfoVo> searchResult = prpdbankinfoDao.findByParamsForWs(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	public PrpdbankinfoDao getPrpdbankinfoDao() {
		return prpdbankinfoDao;
	}

	public void setPrpdbankinfoDao(PrpdbankinfoDao prpdbankinfoDao) {
		this.prpdbankinfoDao = prpdbankinfoDao;
	}


}
