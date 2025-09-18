package com.tlg.msSqlMob.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.msSqlMob.dao.MobileInsBatchInfoDao;
import com.tlg.msSqlMob.entity.MobileInsBatchInfo;
import com.tlg.msSqlMob.service.MobileInsBatchInfoService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

/** mantis：MOB0001，處理人員：CC009，需求單編號：MOB0001 遠傳手機保險投保&批改作業 */
@Transactional(value = "msSqlMobTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class MobileInsBatchInfoServiceImpl implements MobileInsBatchInfoService{

	private MobileInsBatchInfoDao mobileInsBatchInfoDao;

	@Override
	public int countMobileInsBatchInfo(Map params) throws SystemException, Exception {
		return mobileInsBatchInfoDao.count(params);
	}

	@Override
	public Result findMobileInsBatchInfoByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = mobileInsBatchInfoDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<MobileInsBatchInfo> searchResult = mobileInsBatchInfoDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findMobileInsBatchInfoByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<MobileInsBatchInfo> searchResult = mobileInsBatchInfoDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findMobileInsBatchInfoByUK(String oid) throws SystemException,Exception {

		if (StringUtil.isSpace(oid)) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		Map<String, String> params = new HashMap<String, String>();
		params.put("oid", oid);
		MobileInsBatchInfo persisted = mobileInsBatchInfoDao.findByUK(params);
		if (null == persisted) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(persisted);
		}
		return result;
	}

	@Override
	public Result updateMobileInsBatchInfo(MobileInsBatchInfo mobileInsBatchInfo) throws SystemException, Exception {

		if (mobileInsBatchInfo == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		if(mobileInsBatchInfoDao.isUnique(mobileInsBatchInfo)) {
			throw new SystemException(Constants.DATA_NOT_EXIST);  
		}
		
		boolean status = mobileInsBatchInfoDao.update(mobileInsBatchInfo);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(mobileInsBatchInfo);
		return result;
	}

	@Override
	public Result insertMobileInsBatchInfo(MobileInsBatchInfo mobileInsBatchInfo) throws SystemException, Exception {

		if (mobileInsBatchInfo == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		BigDecimal oid = mobileInsBatchInfoDao.insert(mobileInsBatchInfo);
		mobileInsBatchInfo.setOid(oid);
		
		if(mobileInsBatchInfoDao.isUnique(mobileInsBatchInfo)) {
			throw new SystemException(Constants.SAVE_DATA_FAIL);  
		}
		
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(mobileInsBatchInfo);
		return result;
	}

	@Override
	public Result removeMobileInsBatchInfo(String oid) throws SystemException, Exception {

		if (StringUtil.isSpace(oid)) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Map params = new HashMap();
		params.put("oid", oid);
		MobileInsBatchInfo persisted = mobileInsBatchInfoDao.findByUK(params);
		if(null == persisted){//資料庫無資料
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = mobileInsBatchInfoDao.remove(persisted);
		if(!status){
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}


	public MobileInsBatchInfoDao getMobileInsBatchInfoDao() {
		return mobileInsBatchInfoDao;
	}

	public void setMobileInsBatchInfoDao(MobileInsBatchInfoDao mobileInsBatchInfoDao) {
		this.mobileInsBatchInfoDao = mobileInsBatchInfoDao;
	}
}
