package com.tlg.prpins.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.FirCtbcDeptinfoDao;
import com.tlg.prpins.entity.FirCtbcDeptinfo;
import com.tlg.prpins.service.FirCtbcDeptinfoService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirCtbcDeptinfoServiceImpl implements FirCtbcDeptinfoService{

	private FirCtbcDeptinfoDao firCtbcDeptinfoDao;

	@SuppressWarnings("rawtypes")
	@Override
	public Result findFirCtbcDeptinfoByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FirCtbcDeptinfo> searchResult = firCtbcDeptinfoDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	@Override
	public Result insertFirCtbcDeptinfo(FirCtbcDeptinfo firCtbcDeptinfo) throws SystemException, Exception {
		if (firCtbcDeptinfo == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
//		Map params = new HashMap();
//		params.put("batchNo",FirRptCtbcCtf.getBatchNo());
//		params.put("oBatchNo",FirRptCtbcCtf.getoBatchNo());
//		params.put("oBatchSeq",FirRptCtbcCtf.getoBatchSeq());
//		params.put("fkOrderSeq",FirRptCtbcCtf.getFkOrderSeq());
//		int count = firRptCtbcCtfDao.count(params);
//		if(count > 0) {
//			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
//		}

		BigDecimal oid = firCtbcDeptinfoDao.insert(firCtbcDeptinfo);
		if(null == oid  || 0L ==oid.longValue()){
			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}else {
			firCtbcDeptinfo.setOid(oid);
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(firCtbcDeptinfo);
		return result;
	}

	@Override
	public Result removeFirCtbcDeptinfo(BigDecimal oid) throws SystemException, Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/* mantis：FIR0182，處理人員：BJ085，需求單編號：FIR0182 中信新件服務人員維護作業 start */
	@Override
	public Result updateFirCtbcDeptinfo(FirCtbcDeptinfo firCtbcDeptinfo) throws SystemException, Exception {
		if (firCtbcDeptinfo == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = firCtbcDeptinfoDao.update(firCtbcDeptinfo);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(firCtbcDeptinfo);
		return result;
	}
	
	@Override
	public Result findFirCtbcDeptinfoByPageInfo(PageInfo pageInfo) throws SystemException, Exception {
		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = firCtbcDeptinfoDao.count(pageInfo.getFilter());
		if (rowCount == 0) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<FirCtbcDeptinfo> searchResult = firCtbcDeptinfoDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findFirCtbcDeptinfoByOid(BigDecimal oid) throws SystemException,Exception {

		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		FirCtbcDeptinfo persisted = firCtbcDeptinfoDao.findByOid(oid);
		if (null == persisted) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(persisted);
		}
		return result;
	}
	/* mantis：FIR0182，處理人員：BJ085，需求單編號：FIR0182 中信新件服務人員維護作業 end */

	public FirCtbcDeptinfoDao getFirCtbcDeptinfoDao() {
		return firCtbcDeptinfoDao;
	}

	public void setFirCtbcDeptinfoDao(FirCtbcDeptinfoDao firCtbcDeptinfoDao) {
		this.firCtbcDeptinfoDao = firCtbcDeptinfoDao;
	}

}
