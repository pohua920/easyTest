package com.tlg.prpins.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.FirRenewPhoneDao;
import com.tlg.prpins.entity.FirRenewPhone;
import com.tlg.prpins.service.FirRenewPhoneService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/** mantis：FIR0571，處理人員：BJ085，需求單編號：FIR0571 住火_APS到期通知處理作業-新增印刷廠產檔作業規格 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirRenewPhoneServiceImpl implements FirRenewPhoneService{
	private FirRenewPhoneDao firRenewPhoneDao;

	@Override
	public Result findFirRenewPhoneByPageInfo(PageInfo pageInfo) throws SystemException, Exception {
		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = firRenewPhoneDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();
		
		List<FirRenewPhone> searchResult = firRenewPhoneDao.findByPageInfo(pageInfo);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result findFirRenewPhoneByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FirRenewPhone> searchResult = firRenewPhoneDao.findByParams(params);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result findFirRenewPhoneByUk(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		FirRenewPhone firRenewPhone = firRenewPhoneDao.findByUK(params);
		if (null == firRenewPhone) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(firRenewPhone);
		}
		return result;
	}
	
	@Override
	public Result updateFirRenewPhone(FirRenewPhone firRenewPhone) throws SystemException, Exception {

		if (firRenewPhone == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = firRenewPhoneDao.update(firRenewPhone);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(firRenewPhone);
		return result;
	}
	
	@Override
	public Result insertFirRenewPhone(FirRenewPhone firRenewPhone) throws SystemException, Exception {
		if (firRenewPhone == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
	
		firRenewPhoneDao.insert(firRenewPhone);
	
		if(firRenewPhoneDao.isUnique(firRenewPhone)) {
			throw new SystemException(Constants.SAVE_DATA_FAIL);  
		}
		
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(firRenewPhone);
		return result;
	}
	
	@Override
	public Result FindPhoneByHandler1code(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		FirRenewPhone firRenewPhone = firRenewPhoneDao.selectPhoneByHandler1code(params);
		if (null == firRenewPhone) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(firRenewPhone);
		}
		return result;
	}

	public FirRenewPhoneDao getFirRenewPhoneDao() {
		return firRenewPhoneDao;
	}

	public void setFirRenewPhoneDao(FirRenewPhoneDao firRenewPhoneDao) {
		this.firRenewPhoneDao = firRenewPhoneDao;
	}
}
