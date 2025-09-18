package com.tlg.prpins.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.HasAgtLionCMDao;
import com.tlg.prpins.entity.HasAgtLionCM;
import com.tlg.prpins.service.HasAgtLionCMService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/** mantis：HAS0215，處理人員：CD094，需求單編號：HAS0215 TA雄獅回饋檔*/
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class HasAgtLionCMServiceImpl implements HasAgtLionCMService{
	private HasAgtLionCMDao hasAgtLionCMDao;

	@Override
	public Result findHasAgtLionCMByPageInfo(PageInfo pageInfo) throws SystemException, Exception {
		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = hasAgtLionCMDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();
		
		List<HasAgtLionCM> searchResult = hasAgtLionCMDao.findByPageInfo(pageInfo);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result findHasAgtLionCMByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<HasAgtLionCM> searchResult = hasAgtLionCMDao.findByParams(params);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public Result insertHasAgtLionCM(HasAgtLionCM hasAgtLionCM) throws SystemException, Exception {
		if (hasAgtLionCM == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		hasAgtLionCMDao.insert(hasAgtLionCM);
		
		if(hasAgtLionCMDao.isUnique(hasAgtLionCM)) {
			throw new SystemException(Constants.SAVE_DATA_FAIL);  
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(hasAgtLionCM);
		return result;
	}

	@Override
	public Result updateHasAgtLionCM(HasAgtLionCM hasAgtLionCM) throws SystemException, Exception {
		if (hasAgtLionCM == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = hasAgtLionCMDao.update(hasAgtLionCM);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(hasAgtLionCM);
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public int countHasAgtLionCM(Map params) throws SystemException, Exception {
		return hasAgtLionCMDao.count(params);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result findHasAgtLionCMByUK(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		if(!params.containsKey("oid")) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		HasAgtLionCM hasAgtLionCM = hasAgtLionCMDao.findByUK(params);
		if (null == hasAgtLionCM) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(hasAgtLionCM);
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
		List<HasAgtLionCM> searchResult = hasAgtLionCMDao.selectForGenFile(params);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	public HasAgtLionCMDao getHasAgtLionCMDao() {
		return hasAgtLionCMDao;
	}

	public void setHasAgtLionCMDao(HasAgtLionCMDao hasAgtLionCMDao) {
		this.hasAgtLionCMDao = hasAgtLionCMDao;
	}

	
}
