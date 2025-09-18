package com.tlg.msSqlMob.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.vo.mob.fetPolicy.TerminationNoticeVo;
import com.tlg.exception.SystemException;
import com.tlg.msSqlMob.dao.TerminationNoticeDao;
import com.tlg.msSqlMob.entity.TerminationNotice;
import com.tlg.msSqlMob.service.TerminationNoticeService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

/** mantis：MOB0024，處理人員：BJ016，需求單編號：MOB0024 產生終止通知書 */
@Transactional(value = "msSqlMobTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class TerminationNoticeServiceImpl implements TerminationNoticeService{

	private TerminationNoticeDao terminationNoticeDao;

	@SuppressWarnings("rawtypes")
	@Override
	public int countTerminationNotice(Map params) throws SystemException, Exception {
		return terminationNoticeDao.count(params);
	}

	@Override
	public Result findTerminationNoticeByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = terminationNoticeDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<TerminationNotice> searchResult = terminationNoticeDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result findTerminationNoticeByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<TerminationNotice> searchResult = terminationNoticeDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findTerminationNoticeByUK(String transactionId) throws SystemException,Exception {

		if (StringUtil.isSpace(transactionId)) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		Map<String, String> params = new HashMap<String, String>();
		params.put("transactionId", transactionId);
		TerminationNotice persisted = terminationNoticeDao.findByUK(params);
		if (null == persisted) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(persisted);
		}
		return result;
	}

	@Override
	public Result updateTerminationNotice(TerminationNotice terminationNotice) throws SystemException, Exception {

		if (terminationNotice == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		if(terminationNoticeDao.isUnique(terminationNotice)) {
			throw new SystemException(Constants.DATA_NOT_EXIST);  
		}
		
		boolean status = terminationNoticeDao.update(terminationNotice);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(terminationNotice);
		return result;
	}

	@Override
	public Result insertTerminationNotice(TerminationNotice terminationNotice) throws SystemException, Exception {

		if (terminationNotice == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		if(!terminationNoticeDao.isUnique(terminationNotice)) {
    		throw new SystemException("資料已存在資料庫中");    		
    	}
		terminationNoticeDao.insert(terminationNotice);
		
		if(terminationNoticeDao.isUnique(terminationNotice)) {
			throw new SystemException(Constants.SAVE_DATA_FAIL);  
		}
		
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(terminationNotice);
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Result removeTerminationNotice(String transactionId) throws SystemException, Exception {

		if (StringUtil.isSpace(transactionId)) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Map params = new HashMap();
		params.put("transactionId", transactionId);
		TerminationNotice persisted = terminationNoticeDao.findByUK(params);
		if(null == persisted){//資料庫無資料
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = terminationNoticeDao.remove(persisted);
		if(!status){
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}
	
	@Override
	public Result findTerminationNoticeForCancel(Map params) throws SystemException, Exception {
		Result result = new Result();
		List<TerminationNoticeVo> searchResult = terminationNoticeDao.selectForCancel(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findTerminationNoticeForUnpaid1(Map params) throws SystemException, Exception {
		Result result = new Result();
		List<TerminationNoticeVo> searchResult = terminationNoticeDao.selectForUnpaid1(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findTerminationNoticeForUnpaid2(Map params) throws SystemException, Exception {
		Result result = new Result();
		List<TerminationNoticeVo> searchResult = terminationNoticeDao.selectForUnpaid2(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	public TerminationNoticeDao getTerminationNoticeDao() {
		return terminationNoticeDao;
	}

	public void setTerminationNoticeDao(TerminationNoticeDao terminationNoticeDao) {
		this.terminationNoticeDao = terminationNoticeDao;
	}

}
