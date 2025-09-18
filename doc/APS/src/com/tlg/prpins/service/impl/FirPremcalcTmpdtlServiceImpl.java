package com.tlg.prpins.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.FirPremcalcTmpdtlDao;
import com.tlg.prpins.entity.FirPremcalcTmpdtl;
import com.tlg.prpins.service.FirPremcalcTmpdtlService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirPremcalcTmpdtlServiceImpl implements FirPremcalcTmpdtlService{

	private FirPremcalcTmpdtlDao firPremcalcTmpdtlDao;

	@Override
	public int countFirPremcalcTmpdtl(Map params) throws SystemException, Exception {
		return firPremcalcTmpdtlDao.count(params);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int countDiscountNum(BigDecimal oidFirPremcalcTmp, String calcDate, String joinPremRate)
			throws SystemException, Exception {
		if(oidFirPremcalcTmp == null){
			throw new SystemException("無法取得承保多種附加險優待率"); 
		}
		if(StringUtil.isSpace(calcDate)){
			throw new SystemException("無法取得承保多種附加險優待率"); 
		}
		Map params = new HashMap();
		params.put("oidFirPremcalcTmp", oidFirPremcalcTmp);
		params.put("calcDate", calcDate);
		if("Y".equals(StringUtil.nullToSpace(joinPremRate))){
			params.put("joinPremRate", "Y");
		}
		return firPremcalcTmpdtlDao.getDiscountNum(params);
	}

	@Override
	public Result findFirPremcalcTmpdtlByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = firPremcalcTmpdtlDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<FirPremcalcTmpdtl> searchResult = firPremcalcTmpdtlDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Result findFirPremcalcTmpdtlByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FirPremcalcTmpdtl> searchResult = firPremcalcTmpdtlDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findFirPremcalcTmpdtlByOid(BigDecimal oid) throws SystemException,Exception {

		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		FirPremcalcTmpdtl persisted = firPremcalcTmpdtlDao.findByOid(oid);
		if (null == persisted) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(persisted);
		}
		return result;
	}

	@Override
	public Result updateFirPremcalcTmpdtl(FirPremcalcTmpdtl firPremcalcTmpdtl) throws SystemException, Exception {

		if (firPremcalcTmpdtl == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = firPremcalcTmpdtlDao.update(firPremcalcTmpdtl);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(firPremcalcTmpdtl);
		return result;
	}

	@Override
	public Result insertFirPremcalcTmpdtl(FirPremcalcTmpdtl firPremcalcTmpdtl) throws SystemException, Exception {

		if (firPremcalcTmpdtl == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}

		BigDecimal oid = firPremcalcTmpdtlDao.insert(firPremcalcTmpdtl);
		if(null == oid  || 0L ==oid.longValue()){
			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}
		firPremcalcTmpdtl.setOid(oid);
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(firPremcalcTmpdtl);
		return result;
	}

	@Override
	public Result removeFirPremcalcTmpdtl(BigDecimal oid) throws SystemException, Exception {

		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		FirPremcalcTmpdtl persisted = firPremcalcTmpdtlDao.findByOid(oid);
		if(null == persisted){//資料庫無資料
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = firPremcalcTmpdtlDao.remove(oid);
		if(!status){
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}


	public FirPremcalcTmpdtlDao getFirPremcalcTmpdtlDao() {
		return firPremcalcTmpdtlDao;
	}

	public void setFirPremcalcTmpdtlDao(FirPremcalcTmpdtlDao firPremcalcTmpdtlDao) {
		this.firPremcalcTmpdtlDao = firPremcalcTmpdtlDao;
	}

}
