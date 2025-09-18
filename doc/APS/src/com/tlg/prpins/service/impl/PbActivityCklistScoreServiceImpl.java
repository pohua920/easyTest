package com.tlg.prpins.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.PbActivityCklistScoreDao;
import com.tlg.prpins.entity.PbActivityCklistScore;
import com.tlg.prpins.service.PbActivityCklistScoreService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class PbActivityCklistScoreServiceImpl implements PbActivityCklistScoreService{

	private PbActivityCklistScoreDao pbActivityCklistScoreDao;

	@Override
	public int countPbActivityCklistScore(Map params) throws SystemException, Exception {
		return pbActivityCklistScoreDao.count(params);
	}

	@Override
	public Result findPbActivityCklistScoreByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = pbActivityCklistScoreDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<PbActivityCklistScore> searchResult = pbActivityCklistScoreDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findPbActivityCklistScoreByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<PbActivityCklistScore> searchResult = pbActivityCklistScoreDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findPbActivityCklistScoreByOid(BigDecimal oid) throws SystemException,Exception {

		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		PbActivityCklistScore persisted = pbActivityCklistScoreDao.findByOid(oid);
		if (null == persisted) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(persisted);
		}
		return result;
	}

	@Override
	public Result updatePbActivityCklistScore(PbActivityCklistScore pbActivityCklistScore) throws SystemException, Exception {

		if (pbActivityCklistScore == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = pbActivityCklistScoreDao.update(pbActivityCklistScore);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(pbActivityCklistScore);
		return result;
	}

	@Override
	public Result insertPbActivityCklistScore(PbActivityCklistScore pbActivityCklistScore) throws SystemException, Exception {

		if (pbActivityCklistScore == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}

		BigDecimal oid = pbActivityCklistScoreDao.insert(pbActivityCklistScore);
		if(null == oid  || 0L ==oid.longValue()){
			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(pbActivityCklistScore);
		return result;
	}

	@Override
	public Result removePbActivityCklistScore(BigDecimal oid) throws SystemException, Exception {

		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		PbActivityCklistScore persisted = pbActivityCklistScoreDao.findByOid(oid);
		if(null == persisted){//資料庫無資料
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = pbActivityCklistScoreDao.remove(oid);
		if(!status){
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}


	public PbActivityCklistScoreDao getPbActivityCklistScoreDao() {
		return pbActivityCklistScoreDao;
	}

	public void setPbActivityCklistScoreDao(PbActivityCklistScoreDao pbActivityCklistScoreDao) {
		this.pbActivityCklistScoreDao = pbActivityCklistScoreDao;
	}
}
