package com.tlg.prpins.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.FirAgtYcb02Dao;
import com.tlg.prpins.entity.FirAgtYcb02;
import com.tlg.prpins.service.FirAgtYcb02Service;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.Result;

/**
 * mantis：FIR0680，處理人員：DP0714，住火_元大回饋檔產生排程規格
 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirAgtYcb02ServiceImpl implements FirAgtYcb02Service{

	private FirAgtYcb02Dao firAgtYcb02Dao;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Result selectForGenFile(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FirAgtYcb02> searchResult = firAgtYcb02Dao.selectForGenFile(params);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	public FirAgtYcb02Dao getFirAgtYcb02Dao() {
		return firAgtYcb02Dao;
	}

	public void setFirAgtYcb02Dao(FirAgtYcb02Dao firAgtYcb02Dao) {
		this.firAgtYcb02Dao = firAgtYcb02Dao;
	}
}
