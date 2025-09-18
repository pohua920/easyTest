package com.tlg.prpins.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.HasAgtBatchDtlDao;
import com.tlg.prpins.entity.HasAgtBatchDtl;
import com.tlg.prpins.service.HasAgtBatchDtlService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.Result;
/** mantis：HAS0215，處理人員：CD094，需求單編號：HAS0215 TA雄獅回饋檔*/
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class HasAgtBatchDtlServiceImpl implements HasAgtBatchDtlService{
	
	private HasAgtBatchDtlDao hasAgtBatchDtlDao;
	

	@SuppressWarnings("rawtypes")
	@Override
	public Result findHasAgtBatchDtlByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<HasAgtBatchDtl> searchResult = hasAgtBatchDtlDao.findByParams(params);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}


	@Override
	public Result updateHasAgtBatchDtlByParams(Map<String,Object> params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Integer count = hasAgtBatchDtlDao.updateByParams(params);
		Result result = new Result();
		if (null == count || 0 == count) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		}else {
			result.setResObject(count);
		}
		return result;
	}
	
	
	@Override
	public Result insertHasAgtBatchDtl(HasAgtBatchDtl hasAgtBatchDtl) throws SystemException, Exception {
		if (hasAgtBatchDtl == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
	
		hasAgtBatchDtlDao.insert(hasAgtBatchDtl);
	
		if(hasAgtBatchDtlDao.isUnique(hasAgtBatchDtl)) {
			throw new SystemException(Constants.SAVE_DATA_FAIL);  
		}
		
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(hasAgtBatchDtl);
		return result;
	}
	
	@Override
	public Result updateHasAgtBatchDtl(HasAgtBatchDtl hasAgtBatchDtl) throws SystemException, Exception {
		if (hasAgtBatchDtl == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = hasAgtBatchDtlDao.update(hasAgtBatchDtl);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(hasAgtBatchDtl);
		return result;
	}
	
	@Override
	public Result findHasAgtBatchDtlByUK(Map params) throws SystemException, Exception {
		Result result = new Result();
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		HasAgtBatchDtl searchResult = hasAgtBatchDtlDao.findByUK(params);
		if (null == searchResult) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}


	public HasAgtBatchDtlDao getHasAgtBatchDtlDao() {
		return hasAgtBatchDtlDao;
	}


	public void setHasAgtBatchDtlDao(HasAgtBatchDtlDao hasAgtBatchDtlDao) {
		this.hasAgtBatchDtlDao = hasAgtBatchDtlDao;
	}

	
}
