package com.tlg.sales.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.vo.Aps028ResultVo;
import com.tlg.exception.SystemException;
import com.tlg.sales.dao.PrpdagentsubDao;
import com.tlg.sales.entity.Prpdagentsub;
import com.tlg.sales.service.PrpdagentsubService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/** mantis：SALES0008，處理人員：BJ085，需求單編號：SALES0008 保經代分支機構維護程式需求 */
@Transactional(value="salesTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class PrpdagentsubServiceImpl implements PrpdagentsubService{
	
	private PrpdagentsubDao prpdagentsubDao;

	@Override
	public Result findPrpdagentsubByPageInfo(PageInfo pageInfo) throws Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = prpdagentsubDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<Prpdagentsub> searchResult = prpdagentsubDao.findByPageInfo(pageInfo);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findPrpdagentsubByParams(Map params) throws Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<Prpdagentsub> searchResult = prpdagentsubDao.findByParams(params);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public Result findPrpdagentsubByUk(Map params) throws Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		Prpdagentsub prpdagentsub = prpdagentsubDao.findByUK(params);
		if (null == prpdagentsub) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(prpdagentsub);
		}
		return result;
	}
	
	@Override
	public Result updatePrpdagentsub(Prpdagentsub prpdagentsub) throws Exception {
		if (prpdagentsub == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = prpdagentsubDao.update(prpdagentsub);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(prpdagentsub);
		return result;
	}
	
	@Override
	public Result insertPrpdagentsub(Prpdagentsub prpdagentsub) throws Exception {

		if (prpdagentsub == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}

		if(!prpdagentsubDao.isUnique(prpdagentsub)) {
			throw new SystemException("資料已存在資料庫中");
		}
		prpdagentsubDao.insert(prpdagentsub);
		
		if(prpdagentsubDao.isUnique(prpdagentsub)) {
			throw new SystemException(Constants.SAVE_DATA_FAIL);
		}
		
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(prpdagentsub);
		return result;
	}
	
	@Override
	public boolean removePrpdagentsub(Map<String,String> params) throws Exception {
		if(params.get("agentcode") == null || params.get("agentsubcode") == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		Prpdagentsub entity = prpdagentsubDao.findByUK(params);
		if(entity == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean result = prpdagentsubDao.remove(entity);
		
		return result;
	}
	
	@Override
	public int countMaxAgentsubcode() throws Exception {
		return prpdagentsubDao.countMaxAgentsubcode();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Result findPrpdagentsubForAps028ByPageInfo(PageInfo pageInfo) throws Exception {
		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = prpdagentsubDao.countForAps028(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<Aps028ResultVo> searchResult = prpdagentsubDao.selectForAps028(pageInfo);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	public PrpdagentsubDao getPrpdagentsubDao() {
		return prpdagentsubDao;
	}

	public void setPrpdagentsubDao(PrpdagentsubDao prpdagentsubDao) {
		this.prpdagentsubDao = prpdagentsubDao;
	}

}
