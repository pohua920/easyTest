package com.tlg.prpins.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.vo.Aps002DetailVo;
import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.FirCtbcBatchDtlDao;
import com.tlg.prpins.entity.FirCtbcBatchDtl;
import com.tlg.prpins.entity.FirCtbcRst;
import com.tlg.prpins.service.FirCtbcBatchDtlService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirCtbcBatchDtlServiceImpl implements FirCtbcBatchDtlService{

	private FirCtbcBatchDtlDao firCtbcBatchDtlDao;
	
	@SuppressWarnings("rawtypes")
	@Override
	public int countFirCtbcBatchDtl(Map params) throws SystemException, Exception {
		return firCtbcBatchDtlDao.count(params);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result findFirCtbcBatchDtlByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FirCtbcBatchDtl> searchResult = firCtbcBatchDtlDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public Result updateFirCtbcBatchDtl(FirCtbcBatchDtl firCtbcBatchDtl) throws SystemException, Exception {
		if (firCtbcBatchDtl == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = firCtbcBatchDtlDao.update(firCtbcBatchDtl);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(firCtbcBatchDtl);
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Result insertFirCtbcBatchDtl(FirCtbcBatchDtl firCtbcBatchDtl) throws SystemException, Exception {
		if (firCtbcBatchDtl == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		Map params = new HashMap();
		params.put("batchNo",firCtbcBatchDtl.getBatchNo());
		params.put("batchSeq",firCtbcBatchDtl.getBatchSeq());
		params.put("fkOrderSeq",firCtbcBatchDtl.getFkOrderSeq());
		int count = firCtbcBatchDtlDao.count(params);
		if(count > 0) {
			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}

		firCtbcBatchDtlDao.insert(firCtbcBatchDtl);

		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(firCtbcBatchDtl);
		return result;
	}

	@Override
	public Result removeFirCtbcBatchDtl(BigDecimal oid) throws SystemException, Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Result findFirCtbcBatchDtlForFeedback(String batchNo) throws SystemException, Exception {
		Result result = new Result();
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("batchNo", batchNo);
		List<FirCtbcRst> searchResult = firCtbcBatchDtlDao.selectForFeedback(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public Result findFirCtbcBatchDtlByPageInfo(PageInfo pageInfo) throws SystemException, Exception {
		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = firCtbcBatchDtlDao.count(pageInfo.getFilter());
		if (rowCount == 0) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<FirCtbcBatchDtl> searchResult = firCtbcBatchDtlDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Result findForAps002Detail(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		Aps002DetailVo searchResult = firCtbcBatchDtlDao.selectForAps002Detail(params);
		if (null == searchResult) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public int countFirCtbcBatchDtlJoinStl(Map params) throws SystemException, Exception {
		return firCtbcBatchDtlDao.countJoinStl(params);
	}

	// mantis：FIR0708，處理人員：DP0714，住火_APS_中信新件轉入查詢作業增加多個單位邏輯(整段覆蓋) -- start
	@SuppressWarnings("unchecked")
	@Override
	public Result findFirCtbcBatchDtlByPageInfoJoinStl(PageInfo pageInfo) throws SystemException, Exception {
		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.putAll(pageInfo.getFilter());

		String coreComcode = (String) pageInfo.getFilter().get("coreComcode");
		if (!coreComcode.equals("")) {
			String[] array = coreComcode.split(",");
			List<String> coreComcodeList = new java.util.ArrayList<String>();
			for (int i=0; i<array.length; i++) {
				coreComcodeList.add(array[i]);
			}
			params.put("coreComcodeList", coreComcodeList);
		}

		int rowCount = firCtbcBatchDtlDao.countJoinStl(params);
		if (rowCount == 0) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<Aps002DetailVo> searchResult = firCtbcBatchDtlDao.selectJoinStl(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	// mantis：FIR0708，處理人員：DP0714，住火_APS_中信新件轉入查詢作業增加多個單位邏輯 -- end

	public FirCtbcBatchDtlDao getFirCtbcBatchDtlDao() {
		return firCtbcBatchDtlDao;
	}

	public void setFirCtbcBatchDtlDao(FirCtbcBatchDtlDao firCtbcBatchDtlDao) {
		this.firCtbcBatchDtlDao = firCtbcBatchDtlDao;
	}
}
