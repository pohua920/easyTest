package com.tlg.prpins.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.PrpdnewcodeDao;
import com.tlg.prpins.entity.Prpdnewcode;
import com.tlg.prpins.service.PrpdnewcodeService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class PrpdnewcodeServiceImpl implements PrpdnewcodeService{

	private PrpdnewcodeDao prpdnewcodeDao;
	
	@SuppressWarnings("rawtypes")
	@Override
	public int countPrpdnewcode(Map params) throws SystemException, Exception {
		return prpdnewcodeDao.count(params);
	}
	
	@Override
	public Result findPrpdnewcodeByPageInfo(PageInfo pageInfo) throws SystemException, Exception {
		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = prpdnewcodeDao.count(pageInfo.getFilter());
		if (rowCount == 0) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<Prpdnewcode> searchResult = prpdnewcodeDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result findPrpdnewcodeByParams(Map params) throws SystemException, Exception {
		Result result = new Result();
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		List<Prpdnewcode> searchResult = prpdnewcodeDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
//	@Override
//	public Result updatePrpdnewcode(Prpdnewcode prpdnewcode) throws SystemException, Exception {
//		if (prpdnewcode == null) {
//			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
//		}
//		boolean status = prpdnewcodeDao.update(prpdnewcode);
//		if (!status) {
//			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
//		}
//		Result result = new Result();
//		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
//		result.setResObject(prpdnewcode);
//		return result;
//	}

//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	@Override
//	public Result insertPrpdnewcode(Prpdnewcode prpdnewcode) throws SystemException, Exception {
//		if (prpdnewcode == null) {
//			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
//		}
//		
//		Map params = new HashMap();
////		params.put("batchNo",prpdnewcode.getBatchNo());
//		params.put("batchNo","A");
//		int count = prpdnewcodeDao.count(params);
//		if(count > 0) {
//			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
//		}
//
//		BigDecimal oid = prpdnewcodeDao.insert(prpdnewcode);
//		if(null == oid  || 0L ==oid.longValue()){
//			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
//		}else {
//			prpdnewcode.setOid(oid);
//		}
//		Result result = new Result();
//		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
//		result.setResObject(prpdnewcode);
//		return result;
//	}

//	@Override
//	public Result removePrpdnewcode(BigDecimal oid) throws SystemException, Exception {
//		// TODO Auto-generated method stub
//		return null;
//	}
	

	public PrpdnewcodeDao getPrpdnewcodeDao() {
		return prpdnewcodeDao;
	}

	public void setPrpdnewcodeDao(PrpdnewcodeDao prpdnewcodeDao) {
		this.prpdnewcodeDao = prpdnewcodeDao;
	}

}
