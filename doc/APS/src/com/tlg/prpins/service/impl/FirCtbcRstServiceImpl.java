package com.tlg.prpins.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.vo.Aps003DetailVo;
import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.FirCtbcRstDao;
import com.tlg.prpins.entity.FirCtbcRst;
import com.tlg.prpins.service.FirCtbcRstService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirCtbcRstServiceImpl implements FirCtbcRstService{

	private FirCtbcRstDao firCtbcRstDao;

	@SuppressWarnings("rawtypes")
	@Override
	public Result findFirCtbcRstByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FirCtbcRst> searchResult = firCtbcRstDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public Result updateFirCtbcRst(FirCtbcRst firCtbcRst) throws SystemException, Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Result insertFirCtbcRst(FirCtbcRst firCtbcRst) throws SystemException, Exception {
		if (firCtbcRst == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		Map params = new HashMap();
		params.put("batchNo",firCtbcRst.getBatchNo());
		params.put("oBatchNo",firCtbcRst.getoBatchNo());
		params.put("oBatchSeq",firCtbcRst.getoBatchSeq());
		params.put("fkOrderSeq",firCtbcRst.getFkOrderSeq());
		int count = firCtbcRstDao.count(params);
		if(count > 0) {
			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}

		BigDecimal oid = firCtbcRstDao.insert(firCtbcRst);
		if(null == oid  || 0L ==oid.longValue()){
			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}else {
			firCtbcRst.setOid(oid);
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(firCtbcRst);
		return result;
	}

	@Override
	public Result removeFirCtbcRst(BigDecimal oid) throws SystemException, Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Result findFirCtbcRstByPageInfo(PageInfo pageInfo) throws SystemException, Exception {
		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = firCtbcRstDao.count(pageInfo.getFilter());
		if (rowCount == 0) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<FirCtbcRst> searchResult = firCtbcRstDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	/*mantis：FIR0181， 中信新件流程-排程查詢作業(含產生查詢及回饋查詢) start*/
	@SuppressWarnings("unchecked")
	@Override
	public Result findForAps003Detail(PageInfo pageInfo) throws SystemException, Exception {
		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = firCtbcRstDao.countForAps003Detail(pageInfo.getFilter());
		if (rowCount == 0) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<Aps003DetailVo> searchResult = firCtbcRstDao.selectForAps003Detail(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int countForAps003Detail(Map params) throws SystemException, Exception {
		return firCtbcRstDao.countForAps003Detail(params);
	}
	/*mantis：FIR0181， 中信新件流程-排程查詢作業(含產生查詢及回饋查詢) end*/

	public FirCtbcRstDao getFirCtbcRstDao() {
		return firCtbcRstDao;
	}

	public void setFirCtbcRstDao(FirCtbcRstDao firCtbcRstDao) {
		this.firCtbcRstDao = firCtbcRstDao;
	}
}
