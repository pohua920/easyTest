package com.tlg.prpins.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.FirCtbcRewFix180Dao;
import com.tlg.prpins.entity.FirCtbcRewFix180;
import com.tlg.prpins.service.FirCtbcRewFix180Service;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirCtbcRewFix180ServiceImpl implements FirCtbcRewFix180Service{

	private FirCtbcRewFix180Dao firCtbcRewFix180Dao;

	@SuppressWarnings("rawtypes")
	@Override
	public Result findFirCtbcRewFix180ByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FirCtbcRewFix180> searchResult = firCtbcRewFix180Dao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public Result updateFirCtbcRewFix180(FirCtbcRewFix180 firCtbcRewFix180) throws SystemException, Exception {
		if (firCtbcRewFix180 == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = firCtbcRewFix180Dao.update(firCtbcRewFix180);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(firCtbcRewFix180);
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Result insertFirCtbcRewFix180(FirCtbcRewFix180 firCtbcRewFix180) throws SystemException, Exception {
		if (firCtbcRewFix180 == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
//		Map params = new HashMap();
//		params.put("batchNo",firCtbcRewFix180.getBatchNo());
//		params.put("batchSeq",firCtbcRewFix180.getBatchSeq());
//		params.put("filename",firCtbcRewFix180.getFilename());
//		params.put("fkOrderSeq",firCtbcRewFix180.getFkOrderSeq());
//		int count = firCtbcRewFix180Dao.count(params);
//		if(count > 0) {
//			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
//		}

		firCtbcRewFix180Dao.insert(firCtbcRewFix180);
//		if(null == oid  || 0L ==oid.longValue()){
//			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
//		}else {
//			firCtbcRewFix180.setBatchOid(oid);
//		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(firCtbcRewFix180);
		return result;
	}

	@Override
	public Result removeFirCtbcRewFix180(BigDecimal oid) throws SystemException, Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Result findFirCtbcRewFix180ByPageInfo(PageInfo pageInfo) throws SystemException, Exception {
		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = firCtbcRewFix180Dao.count(pageInfo.getFilter());
		if (rowCount == 0) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<FirCtbcRewFix180> searchResult = firCtbcRewFix180Dao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	/*mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理 start*/
	@Override
	public Result findFirCtbcRewFix180ByPK(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		FirCtbcRewFix180 searchResult = firCtbcRewFix180Dao.findFirCtbcRewFix180ByPK(params);
		if (null == searchResult) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	/*mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理 end*/

	public FirCtbcRewFix180Dao getFirCtbcRewFix180Dao() {
		return firCtbcRewFix180Dao;
	}

	public void setFirCtbcRewFix180Dao(FirCtbcRewFix180Dao firCtbcRewFix180Dao) {
		this.firCtbcRewFix180Dao = firCtbcRewFix180Dao;
	}

	

}
