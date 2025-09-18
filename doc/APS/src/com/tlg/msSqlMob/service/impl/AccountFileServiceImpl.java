package com.tlg.msSqlMob.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.msSqlMob.dao.AccountFileDao;
import com.tlg.msSqlMob.entity.AccountFile;
import com.tlg.msSqlMob.service.AccountFileService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

/** mantis：MOB0023，處理人員：BJ016，需求單編號：MOB0023 將安達提供的財務用銷帳回饋檔記錄到ACCOUNT_FILE資料表 */
@Transactional(value = "msSqlMobTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class AccountFileServiceImpl implements AccountFileService{

	private AccountFileDao accountFileDao;

	@SuppressWarnings("rawtypes")
	@Override
	public int countAccountFile(Map params) throws SystemException, Exception {
		return accountFileDao.count(params);
	}

	@Override
	public Result findAccountFileByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = accountFileDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<AccountFile> searchResult = accountFileDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result findAccountFileByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<AccountFile> searchResult = accountFileDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findAccountFileByUK(String transactionId) throws SystemException,Exception {

		if (StringUtil.isSpace(transactionId)) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		Map<String, String> params = new HashMap<String, String>();
		params.put("transactionId", transactionId);
		AccountFile persisted = accountFileDao.findByUK(params);
		if (null == persisted) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(persisted);
		}
		return result;
	}

	@Override
	public Result updateAccountFile(AccountFile accountFile) throws SystemException, Exception {

		if (accountFile == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		if(accountFileDao.isUnique(accountFile)) {
			throw new SystemException(Constants.DATA_NOT_EXIST);  
		}
		
		boolean status = accountFileDao.update(accountFile);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(accountFile);
		return result;
	}

	@Override
	public Result insertAccountFile(AccountFile accountFile) throws SystemException, Exception {

		if (accountFile == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		if(!accountFileDao.isUnique(accountFile)) {
    		throw new SystemException("資料已存在資料庫中");    		
    	}
		accountFileDao.insert(accountFile);
		
		if(accountFileDao.isUnique(accountFile)) {
			throw new SystemException(Constants.SAVE_DATA_FAIL);  
		}
		
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(accountFile);
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Result removeAccountFile(String transactionId) throws SystemException, Exception {

		if (StringUtil.isSpace(transactionId)) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Map params = new HashMap();
		params.put("transactionId", transactionId);
		AccountFile persisted = accountFileDao.findByUK(params);
		if(null == persisted){//資料庫無資料
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = accountFileDao.remove(persisted);
		if(!status){
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}


	public AccountFileDao getAccountFileDao() {
		return accountFileDao;
	}

	public void setAccountFileDao(AccountFileDao accountFileDao) {
		this.accountFileDao = accountFileDao;
	}
}
