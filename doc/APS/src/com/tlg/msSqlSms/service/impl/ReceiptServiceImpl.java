package com.tlg.msSqlSms.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.msSqlSms.dao.ReceiptDao;
import com.tlg.msSqlSms.entity.Receipt;
import com.tlg.msSqlSms.service.ReceiptService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

@Transactional(value = "msSqlSmsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class ReceiptServiceImpl implements ReceiptService{

	private ReceiptDao receiptDao;

	@Override
	public int countReceipt(Map params) throws SystemException, Exception {
		return receiptDao.count(params);
	}

	@Override
	public Result findReceiptByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = receiptDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<Receipt> searchResult = receiptDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findTopReceiptByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<Receipt> searchResult = receiptDao.findTopByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public Result findReceiptByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<Receipt> searchResult = receiptDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findReceiptByUK(String messagId, String destAddress, String seq) throws SystemException,Exception {

		if (StringUtil.isSpace(messagId)) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		if (StringUtil.isSpace(destAddress)) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		if (StringUtil.isSpace(seq)) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		Map<String, String> params = new HashMap<String, String>();
		params.put("messagId", messagId);
		params.put("destAddress", destAddress);
		params.put("seq", seq);
		Receipt persisted = receiptDao.findByUK(params);
		if (null == persisted) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(persisted);
		}
		return result;
	}

	@Override
	public Result updateReceipt(Receipt receipt) throws SystemException, Exception {

		if (receipt == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		if(receiptDao.isUnique(receipt)) {
			throw new SystemException(Constants.DATA_NOT_EXIST);  
		}
		
		boolean status = receiptDao.update(receipt);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(receipt);
		return result;
	}

	@Override
	public Result insertReceipt(Receipt receipt) throws SystemException, Exception {

		if (receipt == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
//		if(!receiptDao.isUnique(receipt)) {
//    		throw new SystemException("資料已存在資料庫中");    		
//    	}
		receiptDao.insert(receipt);
		
//		if(receiptDao.isUnique(receipt)) {
//			throw new SystemException(Constants.SAVE_DATA_FAIL);  
//		}
		
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(receipt);
		return result;
	}

	public ReceiptDao getReceiptDao() {
		return receiptDao;
	}

	public void setReceiptDao(ReceiptDao receiptDao) {
		this.receiptDao = receiptDao;
	}
	
	
}
