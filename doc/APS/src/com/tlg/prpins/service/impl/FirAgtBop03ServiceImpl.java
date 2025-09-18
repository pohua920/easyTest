package com.tlg.prpins.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.FirAgtBop03Dao;
import com.tlg.prpins.entity.FirAgtBop03;
import com.tlg.prpins.service.FirAgtBop03Service;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/* mantis：FIR0494，處理人員：CC009，需求單編號：FIR0494_板信回饋檔產生排程規格_新版 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirAgtBop03ServiceImpl implements FirAgtBop03Service{
	private FirAgtBop03Dao firAgtBop03Dao;

	@Override
	public Result findFirAgtBop03ByPageInfo(PageInfo pageInfo) throws SystemException, Exception {
		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = firAgtBop03Dao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();
		
		List<FirAgtBop03> searchResult = firAgtBop03Dao.findByPageInfo(pageInfo);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result findFirAgtBop03ByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FirAgtBop03> searchResult = firAgtBop03Dao.findByParams(params);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public Result insertFirAgtBop03(FirAgtBop03 firAgtBop03) throws SystemException, Exception {
		if (firAgtBop03 == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		firAgtBop03Dao.insert(firAgtBop03);
		
		if(firAgtBop03Dao.isUnique(firAgtBop03)) {
			throw new SystemException(Constants.SAVE_DATA_FAIL);  
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(firAgtBop03);
		return result;
	}

	@Override
	public Result updateFirAgtBop03(FirAgtBop03 firAgtBop03) throws SystemException, Exception {
		if (firAgtBop03 == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = firAgtBop03Dao.update(firAgtBop03);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(firAgtBop03);
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public int countFirAgtBop03(Map params) throws SystemException, Exception {
		return firAgtBop03Dao.count(params);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result findFirAgtBop03ByUK(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		if(!params.containsKey("oid")) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		FirAgtBop03 firAgtBop03 = firAgtBop03Dao.findByUK(params);
		if (null == firAgtBop03) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(firAgtBop03);
		}
		return result;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Result selectForGenFile(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FirAgtBop03> searchResult = firAgtBop03Dao.selectForGenFile(params);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	public FirAgtBop03Dao getFirAgtBop03Dao() {
		return firAgtBop03Dao;
	}

	public void setFirAgtBop03Dao(FirAgtBop03Dao firAgtBop03Dao) {
		this.firAgtBop03Dao = firAgtBop03Dao;
	}
}
