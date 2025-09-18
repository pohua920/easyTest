package com.tlg.xchg.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;
import com.tlg.xchg.dao.CwpIpb905iDao;
import com.tlg.xchg.entity.CwpIpb905i;
import com.tlg.xchg.service.CwpIpb905iService;

@Transactional(value = "xchgTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class CwpIpb905iServiceImpl implements CwpIpb905iService{

	private CwpIpb905iDao cwpIpb905iDao;

	@Override
	public int countCwpIpb905i(Map params) throws SystemException, Exception {
		return cwpIpb905iDao.count(params);
	}

	@Override
	public Result findCwpIpb905iByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = cwpIpb905iDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<CwpIpb905i> searchResult = cwpIpb905iDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findCwpIpb905iByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<CwpIpb905i> searchResult = cwpIpb905iDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findCwpIpb905iByOid(BigDecimal oid) throws SystemException,Exception {

		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		CwpIpb905i persisted = cwpIpb905iDao.findByOid(oid);
		if (null == persisted) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(persisted);
		}
		return result;
	}
	
	@Override
	public Result findCwpIpb905iByUK(String cmptype, String cmpno, String idno, String birdate, 
			String insno, String insclass, String inskind, String insitem)throws SystemException, Exception{
		if (StringUtil.isSpace(cmptype)) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		if (StringUtil.isSpace(cmpno)) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		if (StringUtil.isSpace(idno)) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		if (StringUtil.isSpace(birdate)) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		if (StringUtil.isSpace(insno)) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		if (StringUtil.isSpace(insclass)) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		if (StringUtil.isSpace(inskind)) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		if (StringUtil.isSpace(insitem)) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		Result result = new Result();
		Map<String,String> map = new HashMap<String,String>();		
		map.put("cmptype", cmptype);
		map.put("cmpno", cmpno);
		map.put("idno", idno);
		map.put("birdate", birdate);
		map.put("insno", insno);
		map.put("insclass", insclass);
		map.put("inskind", inskind);
		map.put("insitem", insitem);
		CwpIpb905i cwpIpb905i = cwpIpb905iDao.findByUK(map);
		if(null == cwpIpb905i){
			//找不到資料
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		}else{
			result.setResObject(cwpIpb905i);
		}
		return result;
	}

	@Override
	public Result updateCwpIpb905i(CwpIpb905i cwpIpb905i) throws SystemException, Exception {

		if (cwpIpb905i == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = cwpIpb905iDao.update(cwpIpb905i);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(cwpIpb905i);
		return result;
	}

	@Override
	public Result insertCwpIpb905i(CwpIpb905i cwpIpb905i) throws SystemException, Exception {

		if (cwpIpb905i == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		cwpIpb905i.setDcreate(new Date());
		BigDecimal oid = cwpIpb905iDao.insert(cwpIpb905i);
		if(null == oid  || 0L ==oid.longValue()){
			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(cwpIpb905i);
		return result;
	}

	@Override
	public Result removeCwpIpb905i(BigDecimal oid) throws SystemException, Exception {

		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		CwpIpb905i persisted = cwpIpb905iDao.findByOid(oid);
		if(null == persisted){//資料庫無資料
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = cwpIpb905iDao.remove(oid);
		if(!status){
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}


	public CwpIpb905iDao getCwpIpb905iDao() {
		return cwpIpb905iDao;
	}

	public void setCwpIpb905iDao(CwpIpb905iDao cwpIpb905iDao) {
		this.cwpIpb905iDao = cwpIpb905iDao;
	}

}
