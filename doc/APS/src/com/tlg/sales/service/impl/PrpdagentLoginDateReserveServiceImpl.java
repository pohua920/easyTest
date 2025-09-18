package com.tlg.sales.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.vo.SalesAgentDateReserveVo;
import com.tlg.exception.SystemException;
import com.tlg.sales.dao.PrpdagentLoginDateReserveDao;
import com.tlg.sales.entity.PrpdagentLoginDateReserve;
import com.tlg.sales.service.PrpdagentLoginDateReserveService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/** mantis：SALES0033 ，處理人員： DP0713 ，需求單編號：銷管系統-業務員換證日期更新排程及通知*/
@Transactional(value="salesTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class PrpdagentLoginDateReserveServiceImpl implements PrpdagentLoginDateReserveService{
	
	private PrpdagentLoginDateReserveDao prpdagentLoginDateReserveDao;

	public PrpdagentLoginDateReserveDao getPrpdagentLoginDateReserveDao() {
		return prpdagentLoginDateReserveDao;
	}

	public void setPrpdagentLoginDateReserveDao(PrpdagentLoginDateReserveDao prpdagentLoginDateReserveDao) {
		this.prpdagentLoginDateReserveDao = prpdagentLoginDateReserveDao;
	}


	@Override
	public int countPrpdagentLoginDateReserve(Map params) throws SystemException, Exception {
		int rowCount = prpdagentLoginDateReserveDao.count(params);
		return rowCount;
	}

	@Override
	public Result findPrpdagentLoginDateReserveByPageInfo(PageInfo pageInfo) throws SystemException, Exception {
		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = prpdagentLoginDateReserveDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<PrpdagentLoginDateReserve> searchResult = prpdagentLoginDateReserveDao.findByPageInfo(pageInfo);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findPrpdagentLoginDateReserveByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<PrpdagentLoginDateReserve> searchResult = prpdagentLoginDateReserveDao.findByParams(params);

		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findPrpdagentLoginDateReserveByUk(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		PrpdagentLoginDateReserve searchResult = prpdagentLoginDateReserveDao.findByUK(params);
		if (null == searchResult) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result updatePrpdagentLoginDateReserve(PrpdagentLoginDateReserve prpdagentLoginDateReserve)
			throws SystemException, Exception {
		if (prpdagentLoginDateReserve == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = prpdagentLoginDateReserveDao.update(prpdagentLoginDateReserve);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(prpdagentLoginDateReserve);
		return result;
	}

	@Override
	public Result selectForAgentLoginDateReserve(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<SalesAgentDateReserveVo> searchResult = prpdagentLoginDateReserveDao.selectForAgentLoginDateReserve(params);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}


}
