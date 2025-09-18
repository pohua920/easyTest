package com.tlg.msSqlMob.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.msSqlMob.dao.ProposalInsufficientSmsDao;
import com.tlg.msSqlMob.entity.ProposalInsufficientSms;
import com.tlg.msSqlMob.service.ProposalInsufficientSmsService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

@Transactional(value = "msSqlMobTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class ProposalInsufficientSmsServiceImpl implements ProposalInsufficientSmsService{

	private ProposalInsufficientSmsDao proposalInsufficientSmsDao;

	@Override
	public int countProposalInsufficientSms(Map params) throws SystemException, Exception {
		return proposalInsufficientSmsDao.count(params);
	}

	@Override
	public Result findProposalInsufficientSmsByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = proposalInsufficientSmsDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<ProposalInsufficientSms> searchResult = proposalInsufficientSmsDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findProposalInsufficientSmsByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<ProposalInsufficientSms> searchResult = proposalInsufficientSmsDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findProposalInsufficientSmsByUK(String transactionId) throws SystemException,Exception {

		if (StringUtil.isSpace(transactionId)) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		Map<String, String> params = new HashMap<String, String>();
		params.put("transactionId", transactionId);
		ProposalInsufficientSms persisted = proposalInsufficientSmsDao.findByUK(params);
		if (null == persisted) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(persisted);
		}
		return result;
	}

	@Override
	public Result updateProposalInsufficientSms(ProposalInsufficientSms proposalInsufficientSms) throws SystemException, Exception {

		if (proposalInsufficientSms == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		if(proposalInsufficientSmsDao.isUnique(proposalInsufficientSms)) {
			throw new SystemException(Constants.DATA_NOT_EXIST);  
		}
		
		boolean status = proposalInsufficientSmsDao.update(proposalInsufficientSms);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(proposalInsufficientSms);
		return result;
	}

	@Override
	public Result insertProposalInsufficientSms(ProposalInsufficientSms proposalInsufficientSms) throws SystemException, Exception {

		if (proposalInsufficientSms == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		if(!proposalInsufficientSmsDao.isUnique(proposalInsufficientSms)) {
    		throw new SystemException("資料已存在資料庫中");    		
    	}
		proposalInsufficientSmsDao.insert(proposalInsufficientSms);
		
		if(proposalInsufficientSmsDao.isUnique(proposalInsufficientSms)) {
			throw new SystemException(Constants.SAVE_DATA_FAIL);  
		}
		
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(proposalInsufficientSms);
		return result;
	}

	@Override
	public Result removeProposalInsufficientSms(String transactionId) throws SystemException, Exception {

		if (StringUtil.isSpace(transactionId)) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Map params = new HashMap();
		params.put("transactionId", transactionId);
		ProposalInsufficientSms persisted = proposalInsufficientSmsDao.findByUK(params);
		if(null == persisted){//資料庫無資料
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = proposalInsufficientSmsDao.remove(persisted);
		if(!status){
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}

	public ProposalInsufficientSmsDao getProposalInsufficientSmsDao() {
		return proposalInsufficientSmsDao;
	}

	public void setProposalInsufficientSmsDao(ProposalInsufficientSmsDao proposalInsufficientSmsDao) {
		this.proposalInsufficientSmsDao = proposalInsufficientSmsDao;
	}
}
