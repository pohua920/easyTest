package com.tlg.prpins.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.FirCtbcBatchMainDao;
import com.tlg.prpins.entity.FirCtbcBatchMain;
import com.tlg.prpins.service.FirCtbcBatchMainService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirCtbcBatchMainServiceImpl implements FirCtbcBatchMainService{

	private FirCtbcBatchMainDao firCtbcBatchMainDao;
	
	@SuppressWarnings("rawtypes")
	@Override
	public int countFirCtbcBatchMain(Map params) throws SystemException, Exception {
		return firCtbcBatchMainDao.count(params);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result findFirCtbcBatchMainByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FirCtbcBatchMain> searchResult = firCtbcBatchMainDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public Result updateFirCtbcBatchMain(FirCtbcBatchMain firCtbcBatchMain) throws SystemException, Exception {
		if (firCtbcBatchMain == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = firCtbcBatchMainDao.update(firCtbcBatchMain);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(firCtbcBatchMain);
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Result insertFirCtbcBatchMain(FirCtbcBatchMain firCtbcBatchMain) throws SystemException, Exception {
		if (firCtbcBatchMain == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		Map params = new HashMap();
		params.put("batchNo",firCtbcBatchMain.getBatchNo());
		params.put("batchSeq",firCtbcBatchMain.getBatchSeq());
		params.put("filenameZip",firCtbcBatchMain.getFilenameZip());
		int count = firCtbcBatchMainDao.count(params);
		if(count > 0) {
			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}

		firCtbcBatchMainDao.insert(firCtbcBatchMain);
//		if(null == oid  || 0L ==oid.longValue()){
//			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
//		}else {
//			firCtbcBatchMain.setOid(oid);
//		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(firCtbcBatchMain);
		return result;
	}

	@Override
	public Result removeFirCtbcBatchMain(BigDecimal oid) throws SystemException, Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Result findFirCtbcBatchMainByPageInfo(PageInfo pageInfo) throws SystemException, Exception {
		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = firCtbcBatchMainDao.count(pageInfo.getFilter());
		if (rowCount == 0) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<FirCtbcBatchMain> searchResult = firCtbcBatchMainDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	public FirCtbcBatchMainDao getFirCtbcBatchMainDao() {
		return firCtbcBatchMainDao;
	}

	public void setFirCtbcBatchMainDao(FirCtbcBatchMainDao firCtbcBatchMainDao) {
		this.firCtbcBatchMainDao = firCtbcBatchMainDao;
	}

}
