package com.tlg.prpins.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.HasAgtLionOPDao;
import com.tlg.prpins.entity.HasAgtLionOP;
import com.tlg.prpins.service.HasAgtLionOPService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/** mantis：HAS0215，處理人員：CD094，需求單編號：HAS0215 TA雄獅回饋檔*/
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class HasAgtLionOPServiceImpl implements HasAgtLionOPService{
	private HasAgtLionOPDao hasAgtLionOPDao;

	@Override
	public Result findHasAgtLionOPByPageInfo(PageInfo pageInfo) throws SystemException, Exception {
		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = hasAgtLionOPDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();
		
		List<HasAgtLionOP> searchResult = hasAgtLionOPDao.findByPageInfo(pageInfo);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result findHasAgtLionOPByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<HasAgtLionOP> searchResult = hasAgtLionOPDao.findByParams(params);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public Result insertHasAgtLionOP(HasAgtLionOP hasAgtLionOP) throws SystemException, Exception {
		if (hasAgtLionOP == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		hasAgtLionOPDao.insert(hasAgtLionOP);
		
		if(hasAgtLionOPDao.isUnique(hasAgtLionOP)) {
			throw new SystemException(Constants.SAVE_DATA_FAIL);  
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(hasAgtLionOP);
		return result;
	}

	@Override
	public Result updateHasAgtLionOP(HasAgtLionOP hasAgtLionOP) throws SystemException, Exception {
		if (hasAgtLionOP == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = hasAgtLionOPDao.update(hasAgtLionOP);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(hasAgtLionOP);
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public int countHasAgtLionOP(Map params) throws SystemException, Exception {
		return hasAgtLionOPDao.count(params);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result findHasAgtLionOPByUK(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		if(!params.containsKey("oid")) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		HasAgtLionOP hasAgtLionOP = hasAgtLionOPDao.findByUK(params);
		if (null == hasAgtLionOP) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(hasAgtLionOP);
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
		List<HasAgtLionOP> searchResult = hasAgtLionOPDao.selectForGenFile(params);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	public HasAgtLionOPDao getHasAgtLionOPDao() {
		return hasAgtLionOPDao;
	}

	public void setHasAgtLionOPDao(HasAgtLionOPDao hasAgtLionOPDao) {
		this.hasAgtLionOPDao = hasAgtLionOPDao;
	}

	
}
