package com.tlg.prpins.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.MetaAmlResultDetailDao;
import com.tlg.prpins.entity.MetaAmlResultDetail;
import com.tlg.prpins.service.MetaAmlResultDetailService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class MetaAmlResultDetailServiceImpl implements MetaAmlResultDetailService{
	/* mantis：OTH0065，處理人員：BJ085，需求單編號：OTH0065 建置AML洗錢查詢畫面 start */

	private MetaAmlResultDetailDao metaAmlResultDetailDao;

	@Override
	public int countMetaAmlResultDetail(Map params) throws SystemException, Exception {
		return metaAmlResultDetailDao.count(params);
	}

	@Override
	public Result findMetaAmlResultDetailByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = metaAmlResultDetailDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<MetaAmlResultDetail> searchResult = metaAmlResultDetailDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findMetaAmlResultDetailByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<MetaAmlResultDetail> searchResult = metaAmlResultDetailDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findMetaAmlResultDetailByOid(BigDecimal oid) throws SystemException,Exception {

		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		MetaAmlResultDetail persisted = metaAmlResultDetailDao.findByOid(oid);
		if (null == persisted) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(persisted);
		}
		return result;
	}

	@Override
	public Result updateMetaAmlResultDetail(MetaAmlResultDetail metaAmlResultDetail) throws SystemException, Exception {

		if (metaAmlResultDetail == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = metaAmlResultDetailDao.update(metaAmlResultDetail);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(metaAmlResultDetail);
		return result;
	}

	@Override
	public Result insertMetaAmlResultDetail(MetaAmlResultDetail metaAmlResultDetail) throws SystemException, Exception {

		if (metaAmlResultDetail == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}

		metaAmlResultDetail.setCreatetime(new Date());//加入建檔時間
		BigDecimal oid = metaAmlResultDetailDao.insert(metaAmlResultDetail);
		if(null == oid  || 0L ==oid.longValue()){
			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}
		metaAmlResultDetail.setOid(oid);
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(metaAmlResultDetail);
		return result;
	}

	@Override
	public Result removeMetaAmlResultDetail(BigDecimal oid) throws SystemException, Exception {

		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		MetaAmlResultDetail persisted = metaAmlResultDetailDao.findByOid(oid);
		if(null == persisted){//資料庫無資料
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = metaAmlResultDetailDao.remove(oid);
		if(!status){
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}


	public MetaAmlResultDetailDao getMetaAmlResultDetailDao() {
		return metaAmlResultDetailDao;
	}

	public void setMetaAmlResultDetailDao(MetaAmlResultDetailDao metaAmlResultDetailDao) {
		this.metaAmlResultDetailDao = metaAmlResultDetailDao;
	}
}
