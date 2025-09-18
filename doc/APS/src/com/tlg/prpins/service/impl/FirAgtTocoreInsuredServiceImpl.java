package com.tlg.prpins.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.FirAgtTocoreInsuredDao;
import com.tlg.prpins.entity.FirAgtTocoreInsured;
import com.tlg.prpins.service.FirAgtTocoreInsuredService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirAgtTocoreInsuredServiceImpl implements FirAgtTocoreInsuredService{
	/* mantis：FIR0294，處理人員：BJ085，需求單編號：FIR0294 外銀板信續件移回新核心-資料接收排程 start */
	private FirAgtTocoreInsuredDao firAgtTocoreInsuredDao;

	@Override
	public Result findFirAgtTocoreInsuredByPageInfo(PageInfo pageInfo) throws SystemException, Exception {
		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = firAgtTocoreInsuredDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();
		
		List<FirAgtTocoreInsured> searchResult = firAgtTocoreInsuredDao.findByPageInfo(pageInfo);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result findFirAgtTocoreInsuredByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FirAgtTocoreInsured> searchResult = firAgtTocoreInsuredDao.findByParams(params);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public int countFirAgtTocoreInsured(Map params) throws SystemException, Exception {
		return firAgtTocoreInsuredDao.count(params);
	}

	@Override
	public Result insertFirAgtTocoreInsured(FirAgtTocoreInsured firAgtTocoreInsured) throws SystemException, Exception {

		if (firAgtTocoreInsured == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}

		BigDecimal oid = firAgtTocoreInsuredDao.insert(firAgtTocoreInsured);
		if(null == oid  || 0L ==oid.longValue()){
			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}else {
			firAgtTocoreInsured.setOid(oid);
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(firAgtTocoreInsured);
		return result;
	}
	
	@Override
	public Result updateFirAgtTocoreInsured(FirAgtTocoreInsured firAgtTocoreInsured) throws SystemException, Exception {

		if (firAgtTocoreInsured == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = firAgtTocoreInsuredDao.update(firAgtTocoreInsured);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(firAgtTocoreInsured);
		return result;
	}
	
	/** mantis：FIR0621，處理人員：BJ085，需求單編號：FIR0621 住火_臺銀續保作業_續件資料處理作業 */
	@Override
	public Result removeFirAgtTocoreInsured(BigDecimal oid) throws Exception {
		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		FirAgtTocoreInsured persisted = firAgtTocoreInsuredDao.findByOid(oid);
		if(null == persisted){//資料庫無資料
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = firAgtTocoreInsuredDao.remove(oid);
		if(!status){
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}

	public FirAgtTocoreInsuredDao getFirAgtTocoreInsuredDao() {
		return firAgtTocoreInsuredDao;
	}

	public void setFirAgtTocoreInsuredDao(FirAgtTocoreInsuredDao firAgtTocoreInsuredDao) {
		this.firAgtTocoreInsuredDao = firAgtTocoreInsuredDao;
	}

}
