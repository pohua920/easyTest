package com.tlg.prpins.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.HasAgtLawPolDetailDao;
import com.tlg.prpins.entity.HasAgtLawPolDetail;
import com.tlg.prpins.service.HasAgtLawPolDetailService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class HasAgtLawPolDetailServiceImpl implements HasAgtLawPolDetailService{
	private HasAgtLawPolDetailDao hasAgtLawPolDetailDao;

	@Override
	public Result findHasAgtLawPolDetailByPageInfo(PageInfo pageInfo) throws SystemException, Exception {
		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = hasAgtLawPolDetailDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);	
		pageInfo.doPage();
		
		List<HasAgtLawPolDetail> searchResult = hasAgtLawPolDetailDao.findByPageInfo(pageInfo);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result findHasAgtLawPolDetailByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<HasAgtLawPolDetail> searchResult = hasAgtLawPolDetailDao.findByParams(params);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public Result insertHasAgtLawPolDetail(HasAgtLawPolDetail hasAgtLawPolDetail) throws SystemException, Exception {
		if (hasAgtLawPolDetail == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		hasAgtLawPolDetailDao.insert(hasAgtLawPolDetail);
		
		if(hasAgtLawPolDetailDao.isUnique(hasAgtLawPolDetail)) {
			throw new SystemException(Constants.SAVE_DATA_FAIL);  
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(hasAgtLawPolDetail);
		return result;
	}

	@Override
	public Result updateHasAgtLawPolDetail(HasAgtLawPolDetail hasAgtLawPolDetail) throws SystemException, Exception {
		if (hasAgtLawPolDetail == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = hasAgtLawPolDetailDao.update(hasAgtLawPolDetail);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(hasAgtLawPolDetail);
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public int countHasAgtLawPolDetail(Map params) throws SystemException, Exception {
		return hasAgtLawPolDetailDao.count(params);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result findHasAgtLawPolDetailByUK(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		if(!params.containsKey("oid")) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		HasAgtLawPolDetail hasAgtLawPolDetail = hasAgtLawPolDetailDao.findByUK(params);
		if (null == hasAgtLawPolDetail) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(hasAgtLawPolDetail);
		}
		return result;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Result selectForGenFile(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<HasAgtLawPolDetail> searchResult = hasAgtLawPolDetailDao.selectForGenFile(params);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	public HasAgtLawPolDetailDao getHasAgtLawPolDetailDao() {
		return hasAgtLawPolDetailDao;
	}

	public void setHasAgtLawPolDetailDao(HasAgtLawPolDetailDao hasAgtLawPolDetailDao) {
		this.hasAgtLawPolDetailDao = hasAgtLawPolDetailDao;
	}
}
