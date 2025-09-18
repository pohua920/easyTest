package com.tlg.prpins.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.vo.Aps042ImportVo;
import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.FirRepeatpolicyBatchDtlDao;
import com.tlg.prpins.entity.FirRepeatpolicyBatchDtl;
import com.tlg.prpins.service.FirRepeatpolicyBatchDtlService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.Result;

/* mantis：FIR0565，處理人員：CC009，需求單編號：FIR0565 住火_複保險通知轉檔作業 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirRepeatpolicyBatchDtlServiceImpl implements FirRepeatpolicyBatchDtlService{
	private FirRepeatpolicyBatchDtlDao firRepeatpolicyBatchDtlDao;
	
	
	@Override
	public Result findByParams(Map params) throws Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FirRepeatpolicyBatchDtl> searchResult = firRepeatpolicyBatchDtlDao.findByParams(params);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result removeFirRepeatpolicyBatchDtl(FirRepeatpolicyBatchDtl entity) throws SystemException,Exception {
		if (entity == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = firRepeatpolicyBatchDtlDao.remove(entity);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}
	
	@Override
	public Result insertFirRepeatpolicyBatchDtl(FirRepeatpolicyBatchDtl entity) throws SystemException,Exception {
		if (entity == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}

		firRepeatpolicyBatchDtlDao.insert(entity);
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(entity);
		return result;
	}
	
	@Override
	public Result selectForAps042Import(Map<String, String> params) throws SystemException,Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<Aps042ImportVo> searchResult = firRepeatpolicyBatchDtlDao.selectForAps042Import(params);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	public FirRepeatpolicyBatchDtlDao getFirRepeatpolicyBatchDtlDao() {
		return firRepeatpolicyBatchDtlDao;
	}

	public void setFirRepeatpolicyBatchDtlDao(FirRepeatpolicyBatchDtlDao firRepeatpolicyBatchDtlDao) {
		this.firRepeatpolicyBatchDtlDao = firRepeatpolicyBatchDtlDao;
	}
}
