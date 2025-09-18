package com.tlg.prpins.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.AmlQueryObjMainDao;
import com.tlg.prpins.entity.AmlQueryObjMain;
import com.tlg.prpins.service.AmlQueryObjMainService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

@Transactional(value = "prpinsTransactionManager",propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class AmlQueryObjMainServiceImpl implements AmlQueryObjMainService{
	/* mantis：OTH0065，處理人員：BJ085，需求單編號：OTH0065 建置AML洗錢查詢畫面 start */

	private AmlQueryObjMainDao amlQueryObjMainDao;

	@Override
	public int countAmlQueryObjMain(Map params) throws SystemException, Exception {
		return amlQueryObjMainDao.count(params);
	}

	@Override
	public Result findAmlQueryObjMainByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = amlQueryObjMainDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<AmlQueryObjMain> searchResult = amlQueryObjMainDao.findByPageInfo(pageInfo);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findAmlQueryObjMainByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<AmlQueryObjMain> searchResult = amlQueryObjMainDao.findByParams(params);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findAmlQueryObjMainByOid(BigDecimal oid) throws SystemException, Exception {

		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		AmlQueryObjMain persisted = amlQueryObjMainDao.findByOid(oid);
		if (null == persisted) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(persisted);
		}
		return result;
	}

	@Override
	public Result insertAmlQueryObjMain(AmlQueryObjMain amlQueryObjMain) throws SystemException, Exception {

		if (amlQueryObjMain == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}

		amlQueryObjMain.setCreatetime(new Date());//加入建檔時間
		BigDecimal oid = amlQueryObjMainDao.insert(amlQueryObjMain);
		if(null == oid  || 0L ==oid.longValue()){
			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}
		amlQueryObjMain.setOid(oid);
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(amlQueryObjMain);
		return result;
	}
	
	@Override
	public Result findAmlQueryObjMainMaxOid(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = amlQueryObjMainDao.countMaxOid(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<AmlQueryObjMain> searchResult = amlQueryObjMainDao.findAmlQueryObjMainMaxOid(pageInfo);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	public AmlQueryObjMainDao getAmlQueryObjMainDao() {
		return amlQueryObjMainDao;
	}

	public void setAmlQueryObjMainDao(AmlQueryObjMainDao amlQueryObjMainDao) {
		this.amlQueryObjMainDao = amlQueryObjMainDao;
	}
}
