package com.tlg.prpins.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.vo.FirPahsinRenewalVo;
import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.PrpcinsuredDao;
import com.tlg.prpins.entity.Prpcinsured;
import com.tlg.prpins.service.PrpcinsuredService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.Result;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class PrpcinsuredServiceImpl implements PrpcinsuredService{

	private PrpcinsuredDao prpcinsuredDao;

	@SuppressWarnings("rawtypes")
	@Override
	public Result findPrpcinsuredByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<Prpcinsured> searchResult = prpcinsuredDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	/*mantis：FIR0589，處理人員：BJ085，需求單編號：FIR0589 住火_APS板信續保作業_第二年優化_資料接收排程 start*/
	@Override
	public Result findForPanhsinCoreInsured(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FirPahsinRenewalVo> searchResult = prpcinsuredDao.findForPanhsinCoreInsured(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	/*mantis：FIR0589，處理人員：BJ085，需求單編號：FIR0589 住火_APS板信續保作業_第二年優化_資料接收排程 end*/

	public PrpcinsuredDao getPrpcinsuredDao() {
		return prpcinsuredDao;
	}

	public void setPrpcinsuredDao(PrpcinsuredDao prpcinsuredDao) {
		this.prpcinsuredDao = prpcinsuredDao;
	}

}
