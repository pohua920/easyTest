package com.tlg.prpins.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.vo.Aps041MainVo;
import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.FirAgtUb01Dao;
import com.tlg.prpins.entity.FirAgtUb01;
import com.tlg.prpins.service.FirAgtUb01Service;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/* mantis：FIR0547，處理人員：CC009，需求單編號：FIR0547 聯邦新件資料交換查詢作業 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirAgtUb01ServiceImpl implements FirAgtUb01Service{
	private FirAgtUb01Dao firAgtUb01Dao;

	@SuppressWarnings("unchecked")
	@Override
	public Result findAPS041Main2ByPageInfo(PageInfo pageInfo) throws SystemException, Exception {
		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = firAgtUb01Dao.countForAps041(pageInfo.getFilter());
		if (rowCount == 0) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<Aps041MainVo> searchResult = firAgtUb01Dao.selectForAps041(pageInfo);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	/*mantis：FIR0545，處理人員：BJ085，需求單編號：FIR0545 火險_聯邦新件_APS要保資料接收排程 start*/
	@Override
	public Result findFirAgtUb01ByPageInfo(PageInfo pageInfo) throws SystemException, Exception {
		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = firAgtUb01Dao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();
		
		List<FirAgtUb01> searchResult = firAgtUb01Dao.findByPageInfo(pageInfo);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result findFirAgtUb01ByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FirAgtUb01> searchResult = firAgtUb01Dao.findByParams(params);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public Result insertFirAgtUb01(FirAgtUb01 firAgtUb01) throws SystemException, Exception {
		if (firAgtUb01 == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		BigDecimal oid = firAgtUb01Dao.insert(firAgtUb01);
		
		if(null == oid  || 0L ==oid.longValue()){
			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}else {
			firAgtUb01.setOid(oid);
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(firAgtUb01);
		return result;
	}

	@Override
	public Result updateFirAgtUb01(FirAgtUb01 firAgtUb01) throws SystemException, Exception {
		if (firAgtUb01 == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = firAgtUb01Dao.update(firAgtUb01);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(firAgtUb01);
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public int countFirAgtUb01(Map params) throws SystemException, Exception {
		return firAgtUb01Dao.count(params);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result findFirAgtUb01ByUK(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		if(!params.containsKey("oid")) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		FirAgtUb01 firAgtUb01 = firAgtUb01Dao.findByUK(params);
		if (null == firAgtUb01) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(firAgtUb01);
		}
		return result;
	}
	/*mantis：FIR0545，處理人員：BJ085，需求單編號：FIR0545 火險_聯邦新件_APS要保資料接收排程 end*/

	public FirAgtUb01Dao getFirAgtUb01Dao() {
		return firAgtUb01Dao;
	}

	public void setFirAgtUb01Dao(FirAgtUb01Dao firAgtUb01Dao) {
		this.firAgtUb01Dao = firAgtUb01Dao;
	}
	
}
