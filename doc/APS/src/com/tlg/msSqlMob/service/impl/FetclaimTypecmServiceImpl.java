package com.tlg.msSqlMob.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.msSqlMob.dao.FetclaimTypecmDao;
import com.tlg.msSqlMob.entity.FetclaimTypecm;
import com.tlg.msSqlMob.service.FetclaimTypecmService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

/** mantis：MOB0020，處理人員：BI086，需求單編號：MOB0020 理賠盜打詐騙API及XML作業  */
@Transactional(value = "msSqlMobTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FetclaimTypecmServiceImpl implements FetclaimTypecmService{

	private FetclaimTypecmDao fetclaimTypecmDao;

	@Override
	public int countFetclaimTypecm(Map params) throws SystemException, Exception {
		return fetclaimTypecmDao.count(params);
	}

	@Override
	public Result findFetclaimTypecmByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = fetclaimTypecmDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<FetclaimTypecm> searchResult = fetclaimTypecmDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findFetclaimTypecmByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FetclaimTypecm> searchResult = fetclaimTypecmDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findFetclaimTypecmByUK(String mtnNo) throws SystemException,Exception {

		if (StringUtil.isSpace(mtnNo)) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		Result result = new Result();
		Map<String, String> params = new HashMap<String, String>();
		params.put("mtnNo", mtnNo);
		FetclaimTypecm persisted = fetclaimTypecmDao.findByUK(params);
		if (null == persisted) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(persisted);
		}
		return result;
	}

	@Override
	public Result updateFetclaimTypecm(FetclaimTypecm fetclaimTypecm) throws SystemException, Exception {

		if (fetclaimTypecm == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		if(fetclaimTypecmDao.isUnique(fetclaimTypecm)) {
			throw new SystemException(Constants.DATA_NOT_EXIST);  
		}
		
		boolean status = fetclaimTypecmDao.update(fetclaimTypecm);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(fetclaimTypecm);
		return result;
	}

	@Override
	public Result insertFetclaimTypecm(FetclaimTypecm fetclaimTypecm) throws SystemException, Exception {

		if (fetclaimTypecm == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		if(!fetclaimTypecmDao.isUnique(fetclaimTypecm)) {
    		throw new SystemException("資料已存在資料庫中");    		
    	}
		fetclaimTypecm.setCreatdate(new Date());
		BigDecimal oid = fetclaimTypecmDao.insert(fetclaimTypecm);
		fetclaimTypecm.setOid(oid);
		if(fetclaimTypecmDao.isUnique(fetclaimTypecm)) {
			throw new SystemException(Constants.SAVE_DATA_FAIL);  
		}
		
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(fetclaimTypecm);
		return result;
	}

	@Override
	public Result removeFetclaimTypecm(String mtnNo, String datatype) throws SystemException, Exception {

		if (StringUtil.isSpace(mtnNo)) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		if (StringUtil.isSpace(datatype)) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Map<String, String> params = new HashMap<String, String>();
		params.put("mtnNo", mtnNo);
		params.put("datatype", datatype);
		FetclaimTypecm persisted = fetclaimTypecmDao.findByUK(params);
		if(null == persisted){//資料庫無資料
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = fetclaimTypecmDao.remove(persisted);
		if(!status){
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}
	
	@Override
	public Result findFetclaimTypecmByOid(BigDecimal oid) throws SystemException,Exception {

		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		FetclaimTypecm persisted = fetclaimTypecmDao.findByOid(oid);
		if (null == persisted) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(persisted);
		}
		return result;
	}


	public FetclaimTypecmDao getFetclaimTypecmDao() {
		return fetclaimTypecmDao;
	}

	public void setFetclaimTypecmDao(FetclaimTypecmDao fetclaimTypecmDao) {
		this.fetclaimTypecmDao = fetclaimTypecmDao;
	}
}
