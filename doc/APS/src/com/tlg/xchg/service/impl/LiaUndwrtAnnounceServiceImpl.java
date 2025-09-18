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
import com.tlg.xchg.dao.LiaUndwrtAnnounceDao;
import com.tlg.xchg.entity.LiaUndwrtAnnounce;
import com.tlg.xchg.service.LiaUndwrtAnnounceService;


@Transactional(value="xchgTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class LiaUndwrtAnnounceServiceImpl implements LiaUndwrtAnnounceService{

	private LiaUndwrtAnnounceDao liaUndwrtAnnounceDao;

	@Override
	public int countLiaUndwrtAnnounce(Map params) throws SystemException, Exception {
		return liaUndwrtAnnounceDao.count(params);
	}

	@Override
	public Result findLiaUndwrtAnnounceByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = liaUndwrtAnnounceDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<LiaUndwrtAnnounce> searchResult = liaUndwrtAnnounceDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findLiaUndwrtAnnounceByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<LiaUndwrtAnnounce> searchResult = liaUndwrtAnnounceDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findLiaUndwrtAnnounceByOid(BigDecimal oid) throws SystemException,Exception {

		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		LiaUndwrtAnnounce persisted = liaUndwrtAnnounceDao.findByOid(oid);
		if (null == persisted) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(persisted);
		}
		return result;
	}

	@Override
	public Result updateLiaUndwrtAnnounce(LiaUndwrtAnnounce liaUndwrtAnnounce) throws SystemException, Exception {

		if (liaUndwrtAnnounce == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = liaUndwrtAnnounceDao.update(liaUndwrtAnnounce);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(liaUndwrtAnnounce);
		return result;
	}

	@Override
	public Result insertLiaUndwrtAnnounce(LiaUndwrtAnnounce liaUndwrtAnnounce) throws SystemException, Exception {

		if (liaUndwrtAnnounce == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}

		BigDecimal oid = liaUndwrtAnnounceDao.insert(liaUndwrtAnnounce);
		if(null == oid  || 0L ==oid.longValue()){
			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(liaUndwrtAnnounce);
		return result;
	}

	@Override
	public Result removeLiaUndwrtAnnounce(BigDecimal oid) throws SystemException, Exception {

		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		LiaUndwrtAnnounce persisted = liaUndwrtAnnounceDao.findByOid(oid);
		if(null == persisted){//資料庫無資料
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = liaUndwrtAnnounceDao.remove(oid);
		if(!status){
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}
	
	@Override
	public Result findUnsendLiaUndwrtAnnounceData() throws SystemException, Exception {
		Result result = new Result();
		List<LiaUndwrtAnnounce> searchResult = liaUndwrtAnnounceDao.selectByDistinctIdno();
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}


	public LiaUndwrtAnnounceDao getLiaUndwrtAnnounceDao() {
		return liaUndwrtAnnounceDao;
	}

	public void setLiaUndwrtAnnounceDao(LiaUndwrtAnnounceDao liaUndwrtAnnounceDao) {
		this.liaUndwrtAnnounceDao = liaUndwrtAnnounceDao;
	}
}
