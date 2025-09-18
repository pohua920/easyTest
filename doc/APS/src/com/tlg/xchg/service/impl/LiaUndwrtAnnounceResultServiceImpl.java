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
import com.tlg.xchg.dao.LiaUndwrtAnnounceResultDao;
import com.tlg.xchg.entity.LiaUndwrtAnnounceResult;
import com.tlg.xchg.service.LiaUndwrtAnnounceResultService;

@Transactional(value="xchgTransactionManager", propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Exception.class)
public class LiaUndwrtAnnounceResultServiceImpl implements LiaUndwrtAnnounceResultService{

	private LiaUndwrtAnnounceResultDao liaUndwrtAnnounceResultDao;

	@Override
	public int countLiaUndwrtAnnounceResult(Map params) throws SystemException, Exception {
		return liaUndwrtAnnounceResultDao.count(params);
	}

	@Override
	public Result findLiaUndwrtAnnounceResultByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = liaUndwrtAnnounceResultDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<LiaUndwrtAnnounceResult> searchResult = liaUndwrtAnnounceResultDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findLiaUndwrtAnnounceResultByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<LiaUndwrtAnnounceResult> searchResult = liaUndwrtAnnounceResultDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findLiaUndwrtAnnounceResultByOid(BigDecimal oid) throws SystemException,Exception {

		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		LiaUndwrtAnnounceResult persisted = liaUndwrtAnnounceResultDao.findByOid(oid);
		if (null == persisted) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(persisted);
		}
		return result;
	}

	@Override
	public Result updateLiaUndwrtAnnounceResult(LiaUndwrtAnnounceResult liaUndwrtAnnounceResult) throws SystemException, Exception {

		if (liaUndwrtAnnounceResult == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = liaUndwrtAnnounceResultDao.update(liaUndwrtAnnounceResult);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(liaUndwrtAnnounceResult);
		return result;
	}

	@Override
	public Result insertLiaUndwrtAnnounceResult(LiaUndwrtAnnounceResult liaUndwrtAnnounceResult) throws SystemException, Exception {

		if (liaUndwrtAnnounceResult == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}

		BigDecimal oid = liaUndwrtAnnounceResultDao.insert(liaUndwrtAnnounceResult);
		if(null == oid  || 0L ==oid.longValue()){
			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(liaUndwrtAnnounceResult);
		return result;
	}

	@Override
	public Result removeLiaUndwrtAnnounceResult(BigDecimal oid) throws SystemException, Exception {

		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		LiaUndwrtAnnounceResult persisted = liaUndwrtAnnounceResultDao.findByOid(oid);
		if(null == persisted){//資料庫無資料
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = liaUndwrtAnnounceResultDao.remove(oid);
		if(!status){
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}


	public LiaUndwrtAnnounceResultDao getLiaUndwrtAnnounceResultDao() {
		return liaUndwrtAnnounceResultDao;
	}

	public void setLiaUndwrtAnnounceResultDao(LiaUndwrtAnnounceResultDao liaUndwrtAnnounceResultDao) {
		this.liaUndwrtAnnounceResultDao = liaUndwrtAnnounceResultDao;
	}
}
