package com.tlg.prpins.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.vo.Ajax005PrpduserVo;
import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.PrpduserDao;
import com.tlg.prpins.entity.Prpduser;
import com.tlg.prpins.service.PrpduserService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.Result;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class PrpduserServiceImpl implements PrpduserService{

	private PrpduserDao prpduserDao;

	@SuppressWarnings("rawtypes")
	@Override
	public Result findPrpduserByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<Prpduser> searchResult = prpduserDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Result selectForAjax005(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<Ajax005PrpduserVo> searchResult = prpduserDao.selectForAjax005(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	public PrpduserDao getPrpduserDao() {
		return prpduserDao;
	}

	public void setPrpduserDao(PrpduserDao prpduserDao) {
		this.prpduserDao = prpduserDao;
	}

}
