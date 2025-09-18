package com.tlg.prpins.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.vo.FirHandler1codeVo;
import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.FirBatchInfoDao;
import com.tlg.prpins.entity.FirBatchInfo;
import com.tlg.prpins.service.FirBatchInfoService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.Result;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirBatchInfoServiceImpl implements FirBatchInfoService{

	private FirBatchInfoDao firBatchInfoDao;

	@SuppressWarnings("rawtypes")
	@Override
	public Result findFirBatchInfoByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FirBatchInfo> searchResult = firBatchInfoDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Result findFirBatchInfoByUK(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		if(!params.containsKey("prgId")) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		FirBatchInfo firBatchInfo = firBatchInfoDao.findByUK(params);
		if (null == firBatchInfo) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(firBatchInfo);
		}
		return result;
	}
	
	/* mantis：FIR0454，處理人員：BJ085，需求單編號：FIR0454 住火-APS富邦續件資料接收排程 start*/
	@Override
	public Result findHandler1code() throws Exception {
		
		Result result = new Result();
		
		FirHandler1codeVo handler1codeVo = firBatchInfoDao.selectHandler1code();
		if (null == handler1codeVo) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(handler1codeVo);
		}
		return result;
	}
	/* mantis：FIR0454，處理人員：BJ085，需求單編號：FIR0454 住火-APS富邦續件資料接收排程 end*/

	public FirBatchInfoDao getFirBatchInfoDao() {
		return firBatchInfoDao;
	}

	public void setFirBatchInfoDao(FirBatchInfoDao firBatchInfoDao) {
		this.firBatchInfoDao = firBatchInfoDao;
	}

}
