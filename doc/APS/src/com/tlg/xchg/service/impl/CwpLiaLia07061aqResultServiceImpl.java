package com.tlg.xchg.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;
import com.tlg.xchg.dao.CwpLiaLia07061aqResultDao;
import com.tlg.xchg.entity.CwpLiaLia07061aqResult;
import com.tlg.xchg.service.CwpLiaLia07061aqResultService;

@Transactional(value = "xchgTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class CwpLiaLia07061aqResultServiceImpl implements CwpLiaLia07061aqResultService{

	private CwpLiaLia07061aqResultDao cwpLiaLia07061aqResultDao;

	@Override
	public int countCwpLiaLia07061aqResult(Map params) throws SystemException, Exception {
		return cwpLiaLia07061aqResultDao.count(params);
	}

	@Override
	public Result findCwpLiaLia07061aqResultByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = cwpLiaLia07061aqResultDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<CwpLiaLia07061aqResult> searchResult = cwpLiaLia07061aqResultDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findCwpLiaLia07061aqResultByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<CwpLiaLia07061aqResult> searchResult = cwpLiaLia07061aqResultDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findCwpLiaLia07061aqResultByOid(BigDecimal oid) throws SystemException,Exception {

		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		CwpLiaLia07061aqResult persisted = cwpLiaLia07061aqResultDao.findByOid(oid);
		if (null == persisted) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(persisted);
		}
		return result;
	}
	
	@Override
	public Result findCwpLiaLia07061aqResultByUK(String checkno, String dataserno)throws SystemException, Exception{
		if(StringUtil.isSpace(checkno) || StringUtil.isSpace(dataserno)){
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
    	}
		
		Result result = new Result();
		Map<String,String> map = new HashMap<String,String>();		
		map.put("checkno", checkno);
		map.put("dataserno", dataserno);
		CwpLiaLia07061aqResult cwpLiaLia07061aqResult = cwpLiaLia07061aqResultDao.findByUK(map);
		if(null == cwpLiaLia07061aqResult){
			//找不到資料
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		}else{
			result.setResObject(cwpLiaLia07061aqResult);
		}
		return result;
	}

	@Override
	public Result updateCwpLiaLia07061aqResult(CwpLiaLia07061aqResult cwpLiaLia07061aqResult) throws SystemException, Exception {

		if (cwpLiaLia07061aqResult == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = cwpLiaLia07061aqResultDao.update(cwpLiaLia07061aqResult);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(cwpLiaLia07061aqResult);
		return result;
	}

	@Override
	public Result insertCwpLiaLia07061aqResult(CwpLiaLia07061aqResult cwpLiaLia07061aqResult) throws SystemException, Exception {

		if (cwpLiaLia07061aqResult == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		cwpLiaLia07061aqResult.setDcreate(new Date());
		BigDecimal oid = cwpLiaLia07061aqResultDao.insert(cwpLiaLia07061aqResult);
		if(null == oid  || 0L ==oid.longValue()){
			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(cwpLiaLia07061aqResult);
		return result;
	}

	@Override
	public void batchInsertCwpLiaLia07061aqResult(List<CwpLiaLia07061aqResult> list) throws SystemException,Exception {
		this.cwpLiaLia07061aqResultDao.processInBatch(list);
	}
	
	@Override
	public Result removeCwpLiaLia07061aqResult(BigDecimal oid) throws SystemException, Exception {

		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		CwpLiaLia07061aqResult persisted = cwpLiaLia07061aqResultDao.findByOid(oid);
		if(null == persisted){//資料庫無資料
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = cwpLiaLia07061aqResultDao.remove(oid);
		if(!status){
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}


	public CwpLiaLia07061aqResultDao getCwpLiaLia07061aqResultDao() {
		return cwpLiaLia07061aqResultDao;
	}

	public void setCwpLiaLia07061aqResultDao(CwpLiaLia07061aqResultDao cwpLiaLia07061aqResultDao) {
		this.cwpLiaLia07061aqResultDao = cwpLiaLia07061aqResultDao;
	}

}
