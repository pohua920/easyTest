package com.tlg.prpins.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.FirCtbcRewNoshowwordDao;
import com.tlg.prpins.entity.FirCtbcRewNoshowword;
import com.tlg.prpins.service.FirCtbcRewNoshowwordService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirCtbcRewNoshowwordServiceImpl implements FirCtbcRewNoshowwordService{

	private FirCtbcRewNoshowwordDao firCtbcRewNoshowwordDao;

	@SuppressWarnings("rawtypes")
	@Override
	public Result findFirCtbcRewNoshowwordByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FirCtbcRewNoshowword> searchResult = firCtbcRewNoshowwordDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public Result updateFirCtbcRewNoshowword(FirCtbcRewNoshowword firCtbcRewNoshowword) throws SystemException, Exception {
		if (firCtbcRewNoshowword == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = firCtbcRewNoshowwordDao.update(firCtbcRewNoshowword);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(firCtbcRewNoshowword);
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Result insertFirCtbcRewNoshowword(FirCtbcRewNoshowword firCtbcRewNoshowword) throws SystemException, Exception {
		if (firCtbcRewNoshowword == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
//		Map params = new HashMap();
//		params.put("batchNo",firCtbcRewNoshowword.getBatchNo());
//		params.put("batchSeq",firCtbcRewNoshowword.getBatchSeq());
//		params.put("filename",firCtbcRewNoshowword.getFilename());
//		params.put("fkOrderSeq",firCtbcRewNoshowword.getFkOrderSeq());
//		int count = firCtbcRewNoshowwordDao.count(params);
//		if(count > 0) {
//			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
//		}

		firCtbcRewNoshowwordDao.insert(firCtbcRewNoshowword);
//		if(null == oid  || 0L ==oid.longValue()){
//			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
//		}else {
//			firCtbcRewNoshowword.setOid(oid);
//		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(firCtbcRewNoshowword);
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public int countByParams(Map params) throws SystemException, Exception {
		return firCtbcRewNoshowwordDao.count(params);
	}

	@Override
	public Result removeFirCtbcRewNoshowword(BigDecimal oid) throws SystemException, Exception {

		if(oid == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		FirCtbcRewNoshowword entity = firCtbcRewNoshowwordDao.findByOid(oid);
		if(entity == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		firCtbcRewNoshowwordDao.remove(entity);
		
		return null;
	}
	
	@Override
	public Result findFirCtbcRewNoshowwordByPageInfo(PageInfo pageInfo) throws SystemException, Exception {
		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = firCtbcRewNoshowwordDao.count(pageInfo.getFilter());
		if (rowCount == 0) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<FirCtbcRewNoshowword> searchResult = firCtbcRewNoshowwordDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	/*mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理 start*/
	@Override
	public Result findFirCtbcRewNoshowwordByUK(Map params) throws SystemException, Exception {
		Result result = new Result();
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		FirCtbcRewNoshowword searchResult = firCtbcRewNoshowwordDao.findByUK(params);
		if (null == searchResult) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	/*mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理 end*/

	public FirCtbcRewNoshowwordDao getFirCtbcRewNoshowwordDao() {
		return firCtbcRewNoshowwordDao;
	}

	public void setFirCtbcRewNoshowwordDao(FirCtbcRewNoshowwordDao firCtbcRewNoshowwordDao) {
		this.firCtbcRewNoshowwordDao = firCtbcRewNoshowwordDao;
	}

}
