package com.tlg.prpins.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.HasAgtLionCHDao;
import com.tlg.prpins.entity.HasAgtLionCH;
import com.tlg.prpins.service.HasAgtLionCHService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/** mantis：HAS0215，處理人員：CD094，需求單編號：HAS0215 TA雄獅回饋檔*/
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class HasAgtLionCHServiceImpl implements HasAgtLionCHService{
	private HasAgtLionCHDao hasAgtLionCHDao;

	@Override
	public Result findHasAgtLionCHByPageInfo(PageInfo pageInfo) throws SystemException, Exception {
		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = hasAgtLionCHDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();
		
		List<HasAgtLionCH> searchResult = hasAgtLionCHDao.findByPageInfo(pageInfo);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result findHasAgtLionCHByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<HasAgtLionCH> searchResult = hasAgtLionCHDao.findByParams(params);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public Result insertHasAgtLionCH(HasAgtLionCH hasAgtLionCH) throws SystemException, Exception {
		if (hasAgtLionCH == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		hasAgtLionCHDao.insert(hasAgtLionCH);
		
		if(hasAgtLionCHDao.isUnique(hasAgtLionCH)) {
			throw new SystemException(Constants.SAVE_DATA_FAIL);  
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(hasAgtLionCH);
		return result;
	}

	@Override
	public Result updateHasAgtLionCH(HasAgtLionCH hasAgtLionCH) throws SystemException, Exception {
		if (hasAgtLionCH == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = hasAgtLionCHDao.update(hasAgtLionCH);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(hasAgtLionCH);
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public int countHasAgtLionCH(Map params) throws SystemException, Exception {
		return hasAgtLionCHDao.count(params);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result findHasAgtLionCHByUK(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		if(!params.containsKey("oid")) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		HasAgtLionCH hasAgtLionCH = hasAgtLionCHDao.findByUK(params);
		if (null == hasAgtLionCH) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(hasAgtLionCH);
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
		List<HasAgtLionCH> searchResult = hasAgtLionCHDao.selectForGenFile(params);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	public HasAgtLionCHDao getHasAgtLionCHDao() {
		return hasAgtLionCHDao;
	}

	public void setHasAgtLionCHDao(HasAgtLionCHDao hasAgtLionCHDao) {
		this.hasAgtLionCHDao = hasAgtLionCHDao;
	}

	
}
