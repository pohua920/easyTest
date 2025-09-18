package com.tlg.msSqlMob.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.msSqlMob.dao.AcFileDao;
import com.tlg.msSqlMob.entity.AcFile;
import com.tlg.msSqlMob.service.AcFileService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

/** mantis：MOB0008，處理人員：BJ016，需求單編號：MOB0008 將安達提供的銷帳回饋檔記錄到AC_FILE資料表 */
@Transactional(value = "msSqlMobTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class AcFileServiceImpl implements AcFileService{

	private AcFileDao acFileDao;

	@Override
	public int countAcFile(Map params) throws SystemException, Exception {
		return acFileDao.count(params);
	}

	@Override
	public Result findAcFileByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = acFileDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<AcFile> searchResult = acFileDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findAcFileByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<AcFile> searchResult = acFileDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findAcFileByUK(String transactionId) throws SystemException,Exception {

		if (StringUtil.isSpace(transactionId)) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		Map<String, String> params = new HashMap<String, String>();
		params.put("transactionId", transactionId);
		AcFile persisted = acFileDao.findByUK(params);
		if (null == persisted) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(persisted);
		}
		return result;
	}

	@Override
	public Result updateAcFile(AcFile acFile) throws SystemException, Exception {

		if (acFile == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		if(acFileDao.isUnique(acFile)) {
			throw new SystemException(Constants.DATA_NOT_EXIST);  
		}
		
		boolean status = acFileDao.update(acFile);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(acFile);
		return result;
	}

	@Override
	public Result insertAcFile(AcFile acFile) throws SystemException, Exception {

		if (acFile == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		if(!acFileDao.isUnique(acFile)) {
    		throw new SystemException("資料已存在資料庫中");    		
    	}
		acFileDao.insert(acFile);
		
		if(acFileDao.isUnique(acFile)) {
			throw new SystemException(Constants.SAVE_DATA_FAIL);  
		}
		
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(acFile);
		return result;
	}

	@Override
	public Result removeAcFile(String transactionId) throws SystemException, Exception {

		if (StringUtil.isSpace(transactionId)) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Map params = new HashMap();
		params.put("transactionId", transactionId);
		AcFile persisted = acFileDao.findByUK(params);
		if(null == persisted){//資料庫無資料
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = acFileDao.remove(persisted);
		if(!status){
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}


	public AcFileDao getAcFileDao() {
		return acFileDao;
	}

	public void setAcFileDao(AcFileDao acFileDao) {
		this.acFileDao = acFileDao;
	}
}
