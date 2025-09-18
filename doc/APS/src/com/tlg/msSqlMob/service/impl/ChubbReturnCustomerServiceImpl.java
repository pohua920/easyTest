package com.tlg.msSqlMob.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.msSqlMob.dao.ChubbReturnCustomerDao;
import com.tlg.msSqlMob.entity.ChubbReturnCustomer;
import com.tlg.msSqlMob.service.ChubbReturnCustomerService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

/** mantis：MOB0010，處理人員：BJ085，需求單編號：MOB0010 安達回傳保單及批單處理結果狀態更新 */
@Transactional(value = "msSqlMobTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class ChubbReturnCustomerServiceImpl implements ChubbReturnCustomerService{

	private ChubbReturnCustomerDao chubbReturnCustomerDao;

	@Override
	public int countChubbReturnCustomer(Map params) throws SystemException, Exception {
		return chubbReturnCustomerDao.count(params);
	}

	@Override
	public Result findChubbReturnCustomerByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = chubbReturnCustomerDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<ChubbReturnCustomer> searchResult = chubbReturnCustomerDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findChubbReturnCustomerByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<ChubbReturnCustomer> searchResult = chubbReturnCustomerDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result updateChubbReturnCustomer(ChubbReturnCustomer chubbReturnCustomer) throws SystemException, Exception {

		if (chubbReturnCustomer == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		if(chubbReturnCustomerDao.isUnique(chubbReturnCustomer)) {
			throw new SystemException(Constants.DATA_NOT_EXIST);  
		}
		chubbReturnCustomer.setModifiedBy("system");
		chubbReturnCustomer.setModifiedTime(new Date());
		boolean status = chubbReturnCustomerDao.update(chubbReturnCustomer);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(chubbReturnCustomer);
		return result;
	}

	@Override
	public Result insertChubbReturnCustomer(ChubbReturnCustomer chubbReturnCustomer) throws SystemException, Exception {

		if (chubbReturnCustomer == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		if(!chubbReturnCustomerDao.isUnique(chubbReturnCustomer)) {
    		throw new SystemException("資料已存在資料庫中");    		
    	}
		chubbReturnCustomerDao.insert(chubbReturnCustomer);
		
		if(chubbReturnCustomerDao.isUnique(chubbReturnCustomer)) {
			throw new SystemException(Constants.SAVE_DATA_FAIL);  
		}
		
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(chubbReturnCustomer);
		return result;
	}

	@Override
	public Result removeChubbReturnCustomer(String oid) throws SystemException, Exception {

		if (StringUtil.isSpace(oid)) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Map params = new HashMap();
		params.put("oid", oid);
		ChubbReturnCustomer persisted = chubbReturnCustomerDao.findByUK(params);
		if(null == persisted){//資料庫無資料
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = chubbReturnCustomerDao.remove(persisted);
		if(!status){
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}


	public ChubbReturnCustomerDao getChubbReturnCustomerDao() {
		return chubbReturnCustomerDao;
	}

	public void setChubbReturnCustomerDao(ChubbReturnCustomerDao chubbReturnCustomerDao) {
		this.chubbReturnCustomerDao = chubbReturnCustomerDao;
	}
}
