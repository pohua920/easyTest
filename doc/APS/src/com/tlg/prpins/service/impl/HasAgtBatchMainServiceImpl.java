package com.tlg.prpins.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.HasAgtBatchMainDao;
import com.tlg.prpins.entity.HasAgtBatchMain;
import com.tlg.prpins.service.HasAgtBatchMainService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
/** mantis：HAS0215，處理人員：CD094，需求單編號：HAS0215 TA雄獅回饋檔*/
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class HasAgtBatchMainServiceImpl implements HasAgtBatchMainService{

	private HasAgtBatchMainDao hasAgtBatchMainDao;

	@Override
	public Result findHasAgtBatchMainByPageInfo(PageInfo pageInfo) throws SystemException, Exception {
		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = hasAgtBatchMainDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();
		
		List<HasAgtBatchMain> searchResult = hasAgtBatchMainDao.findByPageInfo(pageInfo);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result findHasAgtBatchMainByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<HasAgtBatchMain> searchResult = hasAgtBatchMainDao.findByParams(params);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result findHasAgtBatchMainByUk(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		HasAgtBatchMain hasAgtBatchMain = hasAgtBatchMainDao.findByUK(params);
		if (null == hasAgtBatchMain) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(hasAgtBatchMain);
		}
		return result;
	}
	
	@Override
	public Result updateHasAgtBatchMain(HasAgtBatchMain hasAgtBatchMain) throws SystemException, Exception {

		if (hasAgtBatchMain == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = hasAgtBatchMainDao.update(hasAgtBatchMain);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(hasAgtBatchMain);
		return result;
	}
	

	@Override
	public Result insertHasAgtBatchMain(HasAgtBatchMain hasAgtBatchMain) throws SystemException, Exception {
		if (hasAgtBatchMain == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
	
		hasAgtBatchMainDao.insert(hasAgtBatchMain);
	
		if(hasAgtBatchMainDao.isUnique(hasAgtBatchMain)) {
			throw new SystemException(Constants.SAVE_DATA_FAIL);  
		}
		
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(hasAgtBatchMain);
		return result;
	}
	
	@Override
	public  int countByParams(Map params) throws SystemException, Exception {
		return hasAgtBatchMainDao.count(params);
	}

	public HasAgtBatchMainDao getHasAgtBatchMainDao() {
		return hasAgtBatchMainDao;
	}

	public void setHasAgtBatchMainDao(HasAgtBatchMainDao hasAgtBatchMainDao) {
		this.hasAgtBatchMainDao = hasAgtBatchMainDao;
	}

	
}
