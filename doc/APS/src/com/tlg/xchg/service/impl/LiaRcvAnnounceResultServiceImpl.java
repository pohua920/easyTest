package com.tlg.xchg.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.xchg.dao.LiaRcvAnnounceResultDao;
import com.tlg.xchg.entity.LiaRcvAnnounceResult;
import com.tlg.xchg.service.LiaRcvAnnounceResultService;

@Transactional(value="xchgTransactionManager", propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Exception.class)
public class LiaRcvAnnounceResultServiceImpl implements LiaRcvAnnounceResultService{

	private LiaRcvAnnounceResultDao liaRcvAnnounceResultDao;

	@Override
	public int countLiaRcvAnnounceResult(Map params) throws SystemException, Exception {
		return liaRcvAnnounceResultDao.count(params);
	}

	@Override
	public Result findLiaRcvAnnounceResultByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = liaRcvAnnounceResultDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<LiaRcvAnnounceResult> searchResult = liaRcvAnnounceResultDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findLiaRcvAnnounceResultByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<LiaRcvAnnounceResult> searchResult = liaRcvAnnounceResultDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findLiaRcvAnnounceResultByOid(BigDecimal oid) throws SystemException,Exception {

		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		LiaRcvAnnounceResult persisted = liaRcvAnnounceResultDao.findByOid(oid);
		if (null == persisted) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(persisted);
		}
		return result;
	}

	@Override
	public Result updateLiaRcvAnnounceResult(LiaRcvAnnounceResult liaRcvAnnounceResult) throws SystemException, Exception {

		if (liaRcvAnnounceResult == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = liaRcvAnnounceResultDao.update(liaRcvAnnounceResult);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(liaRcvAnnounceResult);
		return result;
	}

	@Override
	public Result insertLiaRcvAnnounceResult(LiaRcvAnnounceResult liaRcvAnnounceResult) throws SystemException, Exception {

		if (liaRcvAnnounceResult == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}

		BigDecimal oid = liaRcvAnnounceResultDao.insert(liaRcvAnnounceResult);
		if(null == oid  || 0L ==oid.longValue()){
			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(liaRcvAnnounceResult);
		return result;
	}

	@Override
	public Result removeLiaRcvAnnounceResult(BigDecimal oid) throws SystemException, Exception {

		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		LiaRcvAnnounceResult persisted = liaRcvAnnounceResultDao.findByOid(oid);
		if(null == persisted){//資料庫無資料
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = liaRcvAnnounceResultDao.remove(oid);
		if(!status){
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}


	public LiaRcvAnnounceResultDao getLiaRcvAnnounceResultDao() {
		return liaRcvAnnounceResultDao;
	}

	public void setLiaRcvAnnounceResultDao(LiaRcvAnnounceResultDao liaRcvAnnounceResultDao) {
		this.liaRcvAnnounceResultDao = liaRcvAnnounceResultDao;
	}
}
