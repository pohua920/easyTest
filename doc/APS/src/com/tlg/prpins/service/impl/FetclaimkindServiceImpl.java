package com.tlg.prpins.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.FetclaimkindDao;
import com.tlg.prpins.entity.Fetclaimkind;
import com.tlg.prpins.service.FetclaimkindService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/** mantis：MOB0018，處理人員：BJ085，需求單編號：MOB0018 理賠資料提交及列印作業 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FetclaimkindServiceImpl implements FetclaimkindService{

	private FetclaimkindDao fetclaimkindDao;

	@Override
	public int countFetclaimkind(Map params) throws SystemException, Exception {
		return fetclaimkindDao.count(params);
	}

	@Override
	public Result findFetclaimkindByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = fetclaimkindDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<Fetclaimkind> searchResult = fetclaimkindDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findFetclaimkindByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<Fetclaimkind> searchResult = fetclaimkindDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result updateFetclaimkind(Fetclaimkind fetclaimkind) throws SystemException, Exception {
		
		if (fetclaimkind == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		boolean status = fetclaimkindDao.update(fetclaimkind);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(fetclaimkind);
		return result;
	}

	@Override
	public Result insertFetclaimkind(Fetclaimkind fetclaimkind) throws SystemException, Exception {

		if (fetclaimkind == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}

		BigDecimal oid = fetclaimkindDao.insert(fetclaimkind);
		if(null == oid  || 0L ==oid.longValue()){
			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(fetclaimkind);
		return result;
	}

	@Override
	public Result removeFetclaimkind(BigDecimal oid) throws SystemException, Exception {
		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Fetclaimkind persisted = fetclaimkindDao.findByOid(oid);
		if(null == persisted){//資料庫無資料
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = fetclaimkindDao.remove(oid);
		if(!status){
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}


	public FetclaimkindDao getFetclaimkindDao() {
		return fetclaimkindDao;
	}

	public void setFetclaimkindDao(FetclaimkindDao fetclaimkindDao) {
		this.fetclaimkindDao = fetclaimkindDao;
	}
}
