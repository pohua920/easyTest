package com.tlg.prpins.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.HasAgtLionACDao;
import com.tlg.prpins.entity.HasAgtLionAC;
import com.tlg.prpins.service.HasAgtLionACService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/** mantis：HAS0215，處理人員：CD094，需求單編號：HAS0215 TA雄獅回饋檔*/
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class HasAgtLionACServiceImpl implements HasAgtLionACService{
	private HasAgtLionACDao hasAgtLionACDao;

	@Override
	public Result findHasAgtLionACByPageInfo(PageInfo pageInfo) throws SystemException, Exception {
		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = hasAgtLionACDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);	
		pageInfo.doPage();
		
		List<HasAgtLionAC> searchResult = hasAgtLionACDao.findByPageInfo(pageInfo);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result findHasAgtLionACByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<HasAgtLionAC> searchResult = hasAgtLionACDao.findByParams(params);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public Result insertHasAgtLionAC(HasAgtLionAC hasAgtLionAC) throws SystemException, Exception {
		if (hasAgtLionAC == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		hasAgtLionACDao.insert(hasAgtLionAC);
		
		if(hasAgtLionACDao.isUnique(hasAgtLionAC)) {
			throw new SystemException(Constants.SAVE_DATA_FAIL);  
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(hasAgtLionAC);
		return result;
	}

	@Override
	public Result updateHasAgtLionAC(HasAgtLionAC hasAgtLionAC) throws SystemException, Exception {
		if (hasAgtLionAC == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = hasAgtLionACDao.update(hasAgtLionAC);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(hasAgtLionAC);
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public int countHasAgtLionAC(Map params) throws SystemException, Exception {
		return hasAgtLionACDao.count(params);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result findHasAgtLionACByUK(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		if(!params.containsKey("oid")) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		HasAgtLionAC hasAgtLionAC = hasAgtLionACDao.findByUK(params);
		if (null == hasAgtLionAC) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(hasAgtLionAC);
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
		List<HasAgtLionAC> searchResult = hasAgtLionACDao.selectForGenFile(params);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	public HasAgtLionACDao getHasAgtLionACDao() {
		return hasAgtLionACDao;
	}

	public void setHasAgtLionACDao(HasAgtLionACDao hasAgtLionACDao) {
		this.hasAgtLionACDao = hasAgtLionACDao;
	}

	
}
