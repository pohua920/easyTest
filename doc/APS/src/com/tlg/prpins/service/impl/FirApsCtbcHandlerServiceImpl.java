package com.tlg.prpins.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.FirApsCtbcHandlerDao;
import com.tlg.prpins.entity.FirApsCtbcHandler;
import com.tlg.prpins.service.FirApsCtbcHandlerService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.Result;

/* mantis：FIR0181， 中信新件流程-排程查詢作業(含產生查詢及回饋查詢)
 * 調整角色權限控管功能，後USER決定取消此功能*/
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirApsCtbcHandlerServiceImpl implements FirApsCtbcHandlerService{

	private FirApsCtbcHandlerDao firApsCtbcHandlerDao;

	@SuppressWarnings("rawtypes")
	@Override
	public Result findFirApsCtbcHandlerByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FirApsCtbcHandler> searchResult = firApsCtbcHandlerDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public String findCoreComcodeByUpperComcode(String upperComcode) throws SystemException, Exception {
		String coreComcodes = firApsCtbcHandlerDao.selectByUpperComcode(upperComcode);
		return coreComcodes;
	}

	public FirApsCtbcHandlerDao getFirApsCtbcHandlerDao() {
		return firApsCtbcHandlerDao;
	}

	public void setFirApsCtbcHandlerDao(FirApsCtbcHandlerDao firApsCtbcHandlerDao) {
		this.firApsCtbcHandlerDao = firApsCtbcHandlerDao;
	}
}
