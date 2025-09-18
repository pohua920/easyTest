package com.tlg.prpins.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.AmlQueryObjDetailDao;
import com.tlg.prpins.entity.AmlQueryObjDetail;
import com.tlg.prpins.service.AmlQueryObjDetailService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class AmlQueryObjDetailServiceImpl implements AmlQueryObjDetailService{
	/* mantis：OTH0065，處理人員：BJ085，需求單編號：OTH0065 建置AML洗錢查詢畫面 start */

	private AmlQueryObjDetailDao amlQueryObjDetailDao;

	@Override
	public int countAmlQueryObjDetail(Map params) throws SystemException, Exception {
		return amlQueryObjDetailDao.count(params);
	}

	@Override
	public Result findAmlQueryObjDetailByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = amlQueryObjDetailDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<AmlQueryObjDetail> searchResult = amlQueryObjDetailDao.findByPageInfo(pageInfo);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findAmlQueryObjDetailByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<AmlQueryObjDetail> searchResult = amlQueryObjDetailDao.findByParams(params);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findAmlQueryObjDetailByOid(BigDecimal oid) throws SystemException, Exception {

		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		AmlQueryObjDetail persisted = amlQueryObjDetailDao.findByOid(oid);
		if (null == persisted) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(persisted);
		}
		return result;
	}

	@Override
	public Result insertAmlQueryObjDetail(AmlQueryObjDetail amlQueryObjDetail) throws SystemException, Exception {

		if (amlQueryObjDetail == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}

		amlQueryObjDetail.setCreatetime(new Date());//加入建檔時間
		BigDecimal oid = amlQueryObjDetailDao.insert(amlQueryObjDetail);
		if(null == oid  || 0L ==oid.longValue()){
			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}
		amlQueryObjDetail.setOid(oid);
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(amlQueryObjDetail);
		return result;
	}

	public AmlQueryObjDetailDao getAmlQueryObjDetailDao() {
		return amlQueryObjDetailDao;
	}

	public void setAmlQueryObjDetailDao(AmlQueryObjDetailDao amlQueryObjDetailDao) {
		this.amlQueryObjDetailDao = amlQueryObjDetailDao;
	}
}
