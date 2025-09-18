package com.tlg.sales.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.vo.SalesAgentDateAlertVo;
import com.tlg.exception.SystemException;
import com.tlg.sales.dao.PrpdagentIdvDao;
import com.tlg.sales.entity.CommdataCmemfil;
import com.tlg.sales.entity.Prpdagent;
import com.tlg.sales.entity.PrpdagentIdv;
import com.tlg.sales.service.PrpdagentIdvService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/** mantis：SALES0033 ，處理人員： DP0713 ，需求單編號：銷管系統-業務員換證日期更新排程及通知*/
@Transactional(value="salesTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class PrpdagentIdvServiceImpl implements PrpdagentIdvService{
	
	private PrpdagentIdvDao prpdagentIdvDao;

	public PrpdagentIdvDao getPrpdagentIdvDao() {
		return prpdagentIdvDao;
	}

	public void setPrpdagentIdvDao(PrpdagentIdvDao prpdagentIdvDao) {
		this.prpdagentIdvDao = prpdagentIdvDao;
	}

	@Override
	public Result findPrpdagentByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = prpdagentIdvDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<PrpdagentIdv> searchResult = prpdagentIdvDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findPrpdagentByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<PrpdagentIdv> searchResult = prpdagentIdvDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public Result findPrpdagentByUk(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		PrpdagentIdv prpdagent = prpdagentIdvDao.findByUK(params);
		if (null == prpdagent) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(prpdagent);
		}
		return result;
	}
	
	@Override
	public Result updatePrpdagent(PrpdagentIdv prpdagent) throws SystemException, Exception {
		if (prpdagent == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = prpdagentIdvDao.update(prpdagent);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(prpdagent);
		return result;
	}


}
