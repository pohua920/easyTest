package com.tlg.prpins.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.FirRenewListDao;
import com.tlg.prpins.entity.FirRenewList;
import com.tlg.prpins.service.FirRenewListService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/*mantis：FIR0570，處理人員：BJ085，需求單編號：FIR0570 住火_APS每月應續件產生排程 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirRenewListServiceImpl implements FirRenewListService{
	private FirRenewListDao firRenewListDao;

	@Override
	public Result findFirRenewListByPageInfo(PageInfo pageInfo) throws SystemException, Exception {
		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = firRenewListDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();
		
		List<FirRenewList> searchResult = firRenewListDao.findByPageInfo(pageInfo);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result findFirRenewListByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FirRenewList> searchResult = firRenewListDao.findByParams(params);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result findFirRenewListByUk(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		FirRenewList firRenewList = firRenewListDao.findByUK(params);
		if (null == firRenewList) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(firRenewList);
		}
		return result;
	}
	
	@Override
	public Result updateFirRenewList(FirRenewList firRenewList) throws SystemException, Exception {

		if (firRenewList == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = firRenewListDao.update(firRenewList);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(firRenewList);
		return result;
	}
	
	@Override
	public Result insertFirRenewList(FirRenewList firRenewList) throws SystemException, Exception {
		if (firRenewList == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
	
		firRenewListDao.insert(firRenewList);
	
		if(firRenewListDao.isUnique(firRenewList)) {
			throw new SystemException(Constants.SAVE_DATA_FAIL);  
		}
		
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(firRenewList);
		return result;
	}

	// mantis：FIR0638，處理人員：DP0714，住火_APS到期續保要保書處理 -- start
	@SuppressWarnings("rawtypes")
	@Override
	public Result selectMainData(Map params) throws SystemException, Exception {
		Result result = new Result();
		List<Map<String, Object>> searchResult = firRenewListDao.selectMainData(params);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result selectInsuredData(Map params) throws SystemException, Exception {
		Result result = new Result();
		List<Map<String, Object>> searchResult = firRenewListDao.selectInsuredData(params);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result selectAddressData(Map params) throws SystemException, Exception {
		Result result = new Result();
		List<Map<String, Object>> searchResult = firRenewListDao.selectAddressData(params);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result selectPropData(Map params) throws SystemException, Exception {
		Result result = new Result();
		List<Map<String, Object>> searchResult = firRenewListDao.selectPropData(params);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result selectMortgageeData(Map params) throws SystemException, Exception {
		Result result = new Result();
		List<Map<String, Object>> searchResult = firRenewListDao.selectMortgageeData(params);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result selectItemkindData(Map params) throws SystemException, Exception {
		Result result = new Result();
		List<Map<String, Object>> searchResult = firRenewListDao.selectItemkindData(params);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	// mantis：FIR0638，處理人員：DP0714，住火_APS到期續保要保書處理 -- end

	public FirRenewListDao getFirRenewListDao() {
		return firRenewListDao;
	}

	public void setFirRenewListDao(FirRenewListDao firRenewListDao) {
		this.firRenewListDao = firRenewListDao;
	}
}
