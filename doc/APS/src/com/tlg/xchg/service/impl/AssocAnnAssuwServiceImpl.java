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
import com.tlg.xchg.dao.AssocAnnAssuwDao;
import com.tlg.xchg.entity.AssocAnnAssuw;
import com.tlg.xchg.service.AssocAnnAssuwService;

@Transactional(value="xchgTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class AssocAnnAssuwServiceImpl implements AssocAnnAssuwService{

	private AssocAnnAssuwDao assocAnnAssuwDao;

	@Override
	public int countAssocAnnAssuw(Map params) throws SystemException, Exception {
		return assocAnnAssuwDao.count(params);
	}

	@Override
	public Result findAssocAnnAssuwByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = assocAnnAssuwDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<AssocAnnAssuw> searchResult = assocAnnAssuwDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findAssocAnnAssuwByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<AssocAnnAssuw> searchResult = assocAnnAssuwDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findAssocAnnAssuwByOid(BigDecimal oid) throws SystemException,Exception {

		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		AssocAnnAssuw persisted = assocAnnAssuwDao.findByOid(oid);
		if (null == persisted) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(persisted);
		}
		return result;
	}

	@Override
	public Result updateAssocAnnAssuw(AssocAnnAssuw assocAnnAssuw) throws SystemException, Exception {

		if (assocAnnAssuw == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = assocAnnAssuwDao.update(assocAnnAssuw);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(assocAnnAssuw);
		return result;
	}

	@Override
	public Result insertAssocAnnAssuw(AssocAnnAssuw assocAnnAssuw) throws SystemException, Exception {

		if (assocAnnAssuw == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}

		BigDecimal oid = assocAnnAssuwDao.insert(assocAnnAssuw);
		if(null == oid  || 0L ==oid.longValue()){
			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(assocAnnAssuw);
		return result;
	}

	@Override
	public Result removeAssocAnnAssuw(BigDecimal oid) throws SystemException, Exception {

		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		AssocAnnAssuw persisted = assocAnnAssuwDao.findByOid(oid);
		if(null == persisted){//資料庫無資料
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = assocAnnAssuwDao.remove(oid);
		if(!status){
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}
	
	@Override
	public Result findUnsendAssocAnnAssuwData() throws SystemException, Exception {
		Result result = new Result();
		List<AssocAnnAssuw> searchResult = assocAnnAssuwDao.selectByDistinctIdno();
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}


	public AssocAnnAssuwDao getAssocAnnAssuwDao() {
		return assocAnnAssuwDao;
	}

	public void setAssocAnnAssuwDao(AssocAnnAssuwDao assocAnnAssuwDao) {
		this.assocAnnAssuwDao = assocAnnAssuwDao;
	}
}
