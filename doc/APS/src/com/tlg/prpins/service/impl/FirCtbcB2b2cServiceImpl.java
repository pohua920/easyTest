package com.tlg.prpins.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.vo.Aps039DetailVo;
import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.FirCtbcB2b2cDao;
import com.tlg.prpins.entity.FirCtbcB2b2c;
import com.tlg.prpins.service.FirCtbcB2b2cService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/* mantis：FIR0497，處理人員：BJ085，需求單編號：FIR0497 中信保代網投_新件回饋檔產生排程規格_新增保經代網投 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirCtbcB2b2cServiceImpl implements FirCtbcB2b2cService{

	private FirCtbcB2b2cDao firCtbcB2b2cDao;
	
	@Override
	public Result findFirCtbcB2b2cByPageInfo(PageInfo pageInfo) throws SystemException, Exception {
		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = firCtbcB2b2cDao.count(pageInfo.getFilter());
		if (rowCount == 0) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<FirCtbcB2b2c> searchResult = firCtbcB2b2cDao.findByPageInfo(pageInfo);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public int countFirCtbcB2b2c(Map params) throws SystemException, Exception {
		return firCtbcB2b2cDao.count(params);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result findFirCtbcB2b2cByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FirCtbcB2b2c> searchResult = firCtbcB2b2cDao.findByParams(params);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result findFirCtbcB2b2cByUk(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		FirCtbcB2b2c firCtbcB2b2c = firCtbcB2b2cDao.findByUK(params);
		if (null == firCtbcB2b2c) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(firCtbcB2b2c);
		}
		return result;
	}

	/* mantis：FIR0499，處理人員：BJ085，需求單編號：FIR0499_住火-中信保代網投_進件查詢作業 */
	@Override
	public Result findAPS039DetailByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		Aps039DetailVo searchResult = firCtbcB2b2cDao.selectForAps039Detail(params);
		if (null == searchResult) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Result insertFirCtbcB2b2c(FirCtbcB2b2c firCtbcB2b2c) throws SystemException, Exception {
		if (firCtbcB2b2c == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		Map params = new HashMap();
		params.put("batchNo",firCtbcB2b2c.getBatchNo());
		params.put("batchSeq",firCtbcB2b2c.getBatchSeq());
		params.put("fkOrderSeq", firCtbcB2b2c.getFkOrderSeq());
		int count = firCtbcB2b2cDao.count(params);
		if(count > 0) {
			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}

		firCtbcB2b2cDao.insert(firCtbcB2b2c);
		
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(firCtbcB2b2c);
		return result;
	}

	@Override
	public Result updateFirCtbcB2b2c(FirCtbcB2b2c firCtbcB2b2c) throws SystemException, Exception {
		if (firCtbcB2b2c == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = firCtbcB2b2cDao.update(firCtbcB2b2c);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(firCtbcB2b2c);
		return result;
	}

	@Override
	public Result removeFirCtbcB2b2c(FirCtbcB2b2c firCtbcB2b2c) throws SystemException, Exception {
		if (firCtbcB2b2c == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = firCtbcB2b2cDao.remove(firCtbcB2b2c);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}

	public FirCtbcB2b2cDao getFirCtbcB2b2cDao() {
		return firCtbcB2b2cDao;
	}

	public void setFirCtbcB2b2cDao(FirCtbcB2b2cDao firCtbcB2b2cDao) {
		this.firCtbcB2b2cDao = firCtbcB2b2cDao;
	}

}
