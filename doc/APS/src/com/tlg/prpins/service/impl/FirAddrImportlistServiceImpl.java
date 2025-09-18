package com.tlg.prpins.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.FirAddrImportlistDao;
import com.tlg.prpins.entity.FirAddrImportlist;
import com.tlg.prpins.service.FirAddrImportlistService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirAddrImportlistServiceImpl implements FirAddrImportlistService{
	/* mantis：FIR0183，處理人員：BJ085，需求單編號：FIR0183 火險地址資料匯入 start */

	private FirAddrImportlistDao firAddrImportlistDao;

	@Override
	public Result findFirAddrImportlistByPageInfo(PageInfo pageInfo)throws SystemException, Exception {
		
		if (null==pageInfo ) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		Result result = new Result();
		
		int rowCount = firAddrImportlistDao.count(pageInfo.getFilter());
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();
		
		List<FirAddrImportlist> searchResult = firAddrImportlistDao.findByPageInfo(pageInfo);
		
		if(null == searchResult || 0 == searchResult.size()){
			//找不到資料	;
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		}else{
			result.setResObject(searchResult);
		}
		
		return result;
	}
	
	@Override
	public Result findFirAddrImportlistByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FirAddrImportlist> searchResult = firAddrImportlistDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public Result insertFirAddrImportlist(FirAddrImportlist firAddrImportlist) throws SystemException, Exception {

		if (firAddrImportlist == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}

		BigDecimal oid = firAddrImportlistDao.insert(firAddrImportlist);
		if(null == oid  || 0L ==oid.longValue()){
			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(firAddrImportlist);
		return result;
	}
	
	@Override
	public Result updateFirAddrImportlist(FirAddrImportlist firAddrImportlist) throws SystemException, Exception{
		if (firAddrImportlist == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = firAddrImportlistDao.update(firAddrImportlist);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(firAddrImportlist);
		return result;
	}
	
	
	public FirAddrImportlistDao getFirAddrImportlistDao() {
		return firAddrImportlistDao;
	}

	public void setFirAddrImportlistDao(FirAddrImportlistDao firAddrImportlistDao) {
		this.firAddrImportlistDao = firAddrImportlistDao;
	}


}
