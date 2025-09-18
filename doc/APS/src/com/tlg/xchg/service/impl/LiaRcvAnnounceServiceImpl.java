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
import com.tlg.xchg.dao.LiaRcvAnnounceDao;
import com.tlg.xchg.entity.LiaRcvAnnounce;
import com.tlg.xchg.service.LiaRcvAnnounceService;

@Transactional(value="xchgTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class LiaRcvAnnounceServiceImpl implements LiaRcvAnnounceService{

	private LiaRcvAnnounceDao liaRcvAnnounceDao;

	@Override
	public int countLiaRcvAnnounce(Map params) throws SystemException, Exception {
		return liaRcvAnnounceDao.count(params);
	}

	@Override
	public Result findLiaRcvAnnounceByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = liaRcvAnnounceDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<LiaRcvAnnounce> searchResult = liaRcvAnnounceDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findLiaRcvAnnounceByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<LiaRcvAnnounce> searchResult = liaRcvAnnounceDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findLiaRcvAnnounceByOid(BigDecimal oid) throws SystemException,Exception {

		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		LiaRcvAnnounce persisted = liaRcvAnnounceDao.findByOid(oid);
		if (null == persisted) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(persisted);
		}
		return result;
	}

	@Override
	public Result updateLiaRcvAnnounce(LiaRcvAnnounce liaRcvAnnounce) throws SystemException, Exception {

		if (liaRcvAnnounce == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = liaRcvAnnounceDao.update(liaRcvAnnounce);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(liaRcvAnnounce);
		return result;
	}

	@Override
	public Result insertLiaRcvAnnounce(LiaRcvAnnounce liaRcvAnnounce) throws SystemException, Exception {

		if (liaRcvAnnounce == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}

		BigDecimal oid = liaRcvAnnounceDao.insert(liaRcvAnnounce);
		if(null == oid  || 0L ==oid.longValue()){
			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(liaRcvAnnounce);
		return result;
	}

	@Override
	public Result removeLiaRcvAnnounce(BigDecimal oid) throws SystemException, Exception {

		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		LiaRcvAnnounce persisted = liaRcvAnnounceDao.findByOid(oid);
		if(null == persisted){//資料庫無資料
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = liaRcvAnnounceDao.remove(oid);
		if(!status){
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}
	
	@Override
	public Result findUnsendLiaRcvAnnounceData() throws SystemException, Exception {
		Result result = new Result();
		List<LiaRcvAnnounce> searchResult = liaRcvAnnounceDao.selectByDistinctIdno();
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}


	public LiaRcvAnnounceDao getLiaRcvAnnounceDao() {
		return liaRcvAnnounceDao;
	}

	public void setLiaRcvAnnounceDao(LiaRcvAnnounceDao liaRcvAnnounceDao) {
		this.liaRcvAnnounceDao = liaRcvAnnounceDao;
	}


}
