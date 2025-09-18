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
import com.tlg.xchg.dao.AssocRcvAncmtDao;
import com.tlg.xchg.entity.AssocRcvAncmt;
import com.tlg.xchg.service.AssocRcvAncmtService;

@Transactional(value="xchgTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class AssocRcvAncmtServiceImpl implements AssocRcvAncmtService{

	private AssocRcvAncmtDao assocRcvAncmtDao;

	@Override
	public int countAssocRcvAncmt(Map params) throws SystemException, Exception {
		return assocRcvAncmtDao.count(params);
	}

	@Override
	public Result findAssocRcvAncmtByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = assocRcvAncmtDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<AssocRcvAncmt> searchResult = assocRcvAncmtDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findAssocRcvAncmtByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<AssocRcvAncmt> searchResult = assocRcvAncmtDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findAssocRcvAncmtByOid(BigDecimal oid) throws SystemException,Exception {

		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		AssocRcvAncmt persisted = assocRcvAncmtDao.findByOid(oid);
		if (null == persisted) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(persisted);
		}
		return result;
	}

	@Override
	public Result updateAssocRcvAncmt(AssocRcvAncmt assocRcvAncmt) throws SystemException, Exception {

		if (assocRcvAncmt == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = assocRcvAncmtDao.update(assocRcvAncmt);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(assocRcvAncmt);
		return result;
	}

	@Override
	public Result insertAssocRcvAncmt(AssocRcvAncmt assocRcvAncmt) throws SystemException, Exception {

		if (assocRcvAncmt == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}

		BigDecimal oid = assocRcvAncmtDao.insert(assocRcvAncmt);
		if(null == oid  || 0L ==oid.longValue()){
			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(assocRcvAncmt);
		return result;
	}

	@Override
	public Result removeAssocRcvAncmt(BigDecimal oid) throws SystemException, Exception {

		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		AssocRcvAncmt persisted = assocRcvAncmtDao.findByOid(oid);
		if(null == persisted){//資料庫無資料
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = assocRcvAncmtDao.remove(oid);
		if(!status){
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}
	
	@Override
	public Result findUnsendAssocRcvAncmtData() throws SystemException, Exception {
		Result result = new Result();
		List<AssocRcvAncmt> searchResult = assocRcvAncmtDao.selectByDistinctIdno();
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}


	public AssocRcvAncmtDao getAssocRcvAncmtDao() {
		return assocRcvAncmtDao;
	}

	public void setAssocRcvAncmtDao(AssocRcvAncmtDao assocRcvAncmtDao) {
		this.assocRcvAncmtDao = assocRcvAncmtDao;
	}


}
