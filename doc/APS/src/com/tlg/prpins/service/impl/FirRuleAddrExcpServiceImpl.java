package com.tlg.prpins.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.FirRuleAddrExcpDao;
import com.tlg.prpins.entity.FirRuleAddrExcp;
import com.tlg.prpins.service.FirRuleAddrExcpService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirRuleAddrExcpServiceImpl implements FirRuleAddrExcpService{
	/* mantis：FIR0238，處理人員：BJ085，需求單編號：FIR0238 稽核議題檢核-例外地址維護作業 start */

	private FirRuleAddrExcpDao firRuleAddrExcpDao;

	@Override
	public int countFirRuleAddrExcp(Map params) throws SystemException, Exception {
		return firRuleAddrExcpDao.count(params);
	}

	@Override
	public Result findFirRuleAddrExcpByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = firRuleAddrExcpDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<FirRuleAddrExcp> searchResult = firRuleAddrExcpDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findFirRuleAddrExcpByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FirRuleAddrExcp> searchResult = firRuleAddrExcpDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findFirRuleAddrExcpByOid(BigDecimal oid) throws SystemException,Exception {

		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		FirRuleAddrExcp persisted = firRuleAddrExcpDao.findByOid(oid);
		if (null == persisted) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(persisted);
		}
		return result;
	}

	@Override
	public Result updateFirRuleAddrExcp(FirRuleAddrExcp firRuleAddrExcp) throws SystemException, Exception {

		if (firRuleAddrExcp == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = firRuleAddrExcpDao.update(firRuleAddrExcp);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(firRuleAddrExcp);
		return result;
	}

	@Override
	public Result insertFirRuleAddrExcp(FirRuleAddrExcp firRuleAddrExcp) throws SystemException, Exception {

		if (firRuleAddrExcp == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		BigDecimal oid = firRuleAddrExcpDao.insert(firRuleAddrExcp);
		if(null == oid  || 0L ==oid.longValue()){
			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}
		firRuleAddrExcp.setOid(oid);
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(firRuleAddrExcp);
		return result;
	}

	@Override
	public Result removeFirRuleAddrExcp(BigDecimal oid) throws SystemException, Exception {

		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		FirRuleAddrExcp persisted = firRuleAddrExcpDao.findByOid(oid);
		if(null == persisted){//資料庫無資料
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = firRuleAddrExcpDao.remove(oid);
		if(!status){
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}

	public FirRuleAddrExcpDao getFirRuleAddrExcpDao() {
		return firRuleAddrExcpDao;
	}

	public void setFirRuleAddrExcpDao(FirRuleAddrExcpDao firRuleAddrExcpDao) {
		this.firRuleAddrExcpDao = firRuleAddrExcpDao;
	}
}
