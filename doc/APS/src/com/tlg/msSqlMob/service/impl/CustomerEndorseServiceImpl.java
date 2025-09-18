package com.tlg.msSqlMob.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.msSqlMob.dao.CustomerEndorseDao;
import com.tlg.msSqlMob.entity.CustomerEndorse;
import com.tlg.msSqlMob.service.CustomerEndorseService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

@Transactional(value = "msSqlMobTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class CustomerEndorseServiceImpl implements CustomerEndorseService{

	private CustomerEndorseDao customerEndorseDao;

	@Override
	public int countCustomerEndorse(Map params) throws SystemException, Exception {
		return customerEndorseDao.count(params);
	}

	@Override
	public Result findCustomerEndorseByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = customerEndorseDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<CustomerEndorse> searchResult = customerEndorseDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findCustomerEndorseByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<CustomerEndorse> searchResult = customerEndorseDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findCustomerEndorseByUK(String transactionId) throws SystemException,Exception {

		if (StringUtil.isSpace(transactionId)) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		Map<String, String> params = new HashMap<String, String>();
		params.put("transactionId", transactionId);
		CustomerEndorse persisted = customerEndorseDao.findByUK(params);
		if (null == persisted) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(persisted);
		}
		return result;
	}

	@Override
	public Result updateCustomerEndorse(CustomerEndorse customerEndorse) throws SystemException, Exception {

		if (customerEndorse == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		if(customerEndorseDao.isUnique(customerEndorse)) {
			throw new SystemException(Constants.DATA_NOT_EXIST);  
		}
		
		boolean status = customerEndorseDao.update(customerEndorse);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(customerEndorse);
		return result;
	}

	@Override
	public Result insertCustomerEndorse(CustomerEndorse customerEndorse) throws SystemException, Exception {

		if (customerEndorse == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		if(!customerEndorseDao.isUnique(customerEndorse)) {
    		throw new SystemException("資料已存在資料庫中");    		
    	}
		customerEndorseDao.insert(customerEndorse);
		
		if(customerEndorseDao.isUnique(customerEndorse)) {
			throw new SystemException(Constants.SAVE_DATA_FAIL);  
		}
		
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(customerEndorse);
		return result;
	}

	@Override
	public Result removeCustomerEndorse(String transactionId) throws SystemException, Exception {

		if (StringUtil.isSpace(transactionId)) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Map params = new HashMap();
		params.put("transactionId", transactionId);
		CustomerEndorse persisted = customerEndorseDao.findByUK(params);
		if(null == persisted){//資料庫無資料
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = customerEndorseDao.remove(persisted);
		if(!status){
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}

	public CustomerEndorseDao getCustomerEndorseDao() {
		return customerEndorseDao;
	}

	public void setCustomerEndorseDao(CustomerEndorseDao customerEndorseDao) {
		this.customerEndorseDao = customerEndorseDao;
	}
	/** mantis：MOB0026，處理人員：CE035，需求單編號：MOB0026 優化手機險對帳流程   ac檔補批單號 START*/
	@Override
	public Result selectCeToCorrectEndorseNo(Map params) throws Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<CustomerEndorse> searchResult = customerEndorseDao.selectCeToCorrectEndorseNo(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	/** mantis：MOB0026，處理人員：CE035，需求單編號：MOB0026 優化手機險對帳流程   ac檔補批單號 END*/
}
