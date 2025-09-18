package com.tlg.xchg.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.vo.CwpAnnounceVo;
import com.tlg.exception.SystemException;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;
import com.tlg.xchg.dao.CwpUndwrtAnnounceDao;
import com.tlg.xchg.entity.CwpUndwrtAnnounce;
import com.tlg.xchg.service.CwpUndwrtAnnounceService;

@Transactional(value="xchgTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class CwpUndwrtAnnounceServiceImpl implements CwpUndwrtAnnounceService{
	/* mantis：OTH0093，處理人員：BJ085，需求單編號：OTH0093 傷害險通報查詢、重送介面 start */
	private CwpUndwrtAnnounceDao cwpUndwrtAnnounceDao;

	@Override
	public int countCwpUndwrtAnnounce(Map params) throws SystemException, Exception {
		return cwpUndwrtAnnounceDao.count(params);
	}

	@Override
	public Result findCwpUndwrtAnnounceByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = cwpUndwrtAnnounceDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<CwpUndwrtAnnounce> searchResult = cwpUndwrtAnnounceDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findCwpUndwrtAnnounceByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<CwpUndwrtAnnounce> searchResult = cwpUndwrtAnnounceDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public Result findCwpUndwrtAnnounceByUK(String checkno, String keyidno,
			String dataserno)throws SystemException, Exception{
		if(StringUtil.isSpace(checkno) || StringUtil.isSpace(keyidno) || StringUtil.isSpace(dataserno)){
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
    	}
		
		Result result = new Result();
		Map<String,String> map = new HashMap<String,String>();		
		map.put("checkno", checkno);
		map.put("keyidno", keyidno);
		map.put("dataserno", dataserno);
		CwpUndwrtAnnounce cwpUndwrtAnnounce = cwpUndwrtAnnounceDao.findByUK(map);
		if(null == cwpUndwrtAnnounce){
			//找不到資料
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		}else{
			result.setResObject(cwpUndwrtAnnounce);
		}
		return result;
	}

	@Override
	public Result findCwpUndwrtAnnounceByOid(BigDecimal oid) throws SystemException,Exception {

		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		CwpUndwrtAnnounce persisted = cwpUndwrtAnnounceDao.findByOid(oid);
		if (null == persisted) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(persisted);
		}
		return result;
	}

	@Override
	public Result updateCwpUndwrtAnnounce(CwpUndwrtAnnounce cwpUndwrtAnnounce) throws SystemException, Exception {

		if (cwpUndwrtAnnounce == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = cwpUndwrtAnnounceDao.update(cwpUndwrtAnnounce);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(cwpUndwrtAnnounce);
		return result;
	}

	@Override
	public Result insertCwpUndwrtAnnounce(CwpUndwrtAnnounce cwpUndwrtAnnounce) throws SystemException, Exception {

		if (cwpUndwrtAnnounce == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}

		BigDecimal oid = cwpUndwrtAnnounceDao.insert(cwpUndwrtAnnounce);
		if(null == oid  || 0L ==oid.longValue()){
			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(cwpUndwrtAnnounce);
		return result;
	}

	@Override
	public Result removeCwpUndwrtAnnounce(BigDecimal oid) throws SystemException, Exception {

		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		CwpUndwrtAnnounce persisted = cwpUndwrtAnnounceDao.findByOid(oid);
		if(null == persisted){//資料庫無資料
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = cwpUndwrtAnnounceDao.remove(oid);
		if(!status){
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}

	@Override
	public Result findDistinctCwpUndwrtAnnounceData(PageInfo pageInfo) throws SystemException, Exception {
		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = cwpUndwrtAnnounceDao.countDistinct(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<CwpAnnounceVo> searchResult = cwpUndwrtAnnounceDao.selectDistinctByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findDistinctCwpUndwrtAnnounceByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		CwpAnnounceVo searchResult = cwpUndwrtAnnounceDao.selectDistinctByParams(params);
		if (null == searchResult) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public Result findUnsendUndwrtData(Map params) throws SystemException,
			Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		if (params.size() == 0) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		Result result = new Result();
		List<Map> searchResult = cwpUndwrtAnnounceDao.selectUnsendUndwrtData(params);
		if (null == searchResult) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	public CwpUndwrtAnnounceDao getCwpUndwrtAnnounceDao() {
		return cwpUndwrtAnnounceDao;
	}

	public void setCwpUndwrtAnnounceDao(CwpUndwrtAnnounceDao cwpUndwrtAnnounceDao) {
		this.cwpUndwrtAnnounceDao = cwpUndwrtAnnounceDao;
	}
}
