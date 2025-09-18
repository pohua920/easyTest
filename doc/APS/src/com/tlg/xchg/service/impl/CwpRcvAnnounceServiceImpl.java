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
import com.tlg.xchg.dao.CwpRcvAnnounceDao;
import com.tlg.xchg.entity.CwpRcvAnnounce;
import com.tlg.xchg.service.CwpRcvAnnounceService;

@Transactional(value="xchgTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class CwpRcvAnnounceServiceImpl implements CwpRcvAnnounceService{
	/* mantis：OTH0093，處理人員：BJ085，需求單編號：OTH0093 傷害險通報查詢、重送介面 start */
	private CwpRcvAnnounceDao cwpRcvAnnounceDao;

	@Override
	public int countCwpRcvAnnounce(Map params) throws SystemException, Exception {
		return cwpRcvAnnounceDao.count(params);
	}

	@Override
	public Result findCwpRcvAnnounceByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = cwpRcvAnnounceDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<CwpRcvAnnounce> searchResult = cwpRcvAnnounceDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findCwpRcvAnnounceByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<CwpRcvAnnounce> searchResult = cwpRcvAnnounceDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findCwpRcvAnnounceByOid(BigDecimal oid) throws SystemException,Exception {

		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		CwpRcvAnnounce persisted = cwpRcvAnnounceDao.findByOid(oid);
		if (null == persisted) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(persisted);
		}
		return result;
	}
	
	@Override
	public Result findCwpRcvAnnounceByUK(String checkno, String keyidno,
			String dataserno)throws SystemException, Exception{
		if(StringUtil.isSpace(checkno) || StringUtil.isSpace(keyidno) || StringUtil.isSpace(dataserno)){
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
    	}
		
		Result result = new Result();
		Map<String,String> map = new HashMap<String,String>();		
		map.put("checkno", checkno);
		map.put("keyidno", keyidno);
		map.put("dataserno", dataserno);
		CwpRcvAnnounce cwpRcvAnnounce = cwpRcvAnnounceDao.findByUK(map);
		if(null == cwpRcvAnnounce){
			//找不到資料
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		}else{
			result.setResObject(cwpRcvAnnounce);
		}
		return result;
	}

	@Override
	public Result updateCwpRcvAnnounce(CwpRcvAnnounce cwpRcvAnnounce) throws SystemException, Exception {

		if (cwpRcvAnnounce == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = cwpRcvAnnounceDao.update(cwpRcvAnnounce);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(cwpRcvAnnounce);
		return result;
	}

	@Override
	public Result insertCwpRcvAnnounce(CwpRcvAnnounce cwpRcvAnnounce) throws SystemException, Exception {

		if (cwpRcvAnnounce == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}

		BigDecimal oid = cwpRcvAnnounceDao.insert(cwpRcvAnnounce);
		if(null == oid  || 0L ==oid.longValue()){
			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(cwpRcvAnnounce);
		return result;
	}

	@Override
	public Result removeCwpRcvAnnounce(BigDecimal oid) throws SystemException, Exception {

		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		CwpRcvAnnounce persisted = cwpRcvAnnounceDao.findByOid(oid);
		if(null == persisted){//資料庫無資料
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = cwpRcvAnnounceDao.remove(oid);
		if(!status){
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}

	@Override
	public Result findDistinctCwpRcvAnnounceData(PageInfo pageInfo) throws SystemException, Exception {
		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = cwpRcvAnnounceDao.countDistinct(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<CwpAnnounceVo> searchResult = cwpRcvAnnounceDao.selectDistinctByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findDistinctCwpRcvAnnounceByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		CwpAnnounceVo searchResult = cwpRcvAnnounceDao.selectDistinctByParams(params);
		if (null == searchResult) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findUnsendRcvData(Map params) throws SystemException,
			Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		if (params.size() == 0) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		Result result = new Result();
		List<Map> searchResult = cwpRcvAnnounceDao.selectUnsendRcvData(params);
		if (null == searchResult) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	
	public CwpRcvAnnounceDao getCwpRcvAnnounceDao() {
		return cwpRcvAnnounceDao;
	}

	public void setCwpRcvAnnounceDao(CwpRcvAnnounceDao cwpRcvAnnounceDao) {
		this.cwpRcvAnnounceDao = cwpRcvAnnounceDao;
	}
}
