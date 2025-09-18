package com.tlg.prpins.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.MetaAmlResultMainDao;
import com.tlg.prpins.entity.MetaAmlResultMain;
import com.tlg.prpins.service.MetaAmlResultMainService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class MetaAmlResultMainServiceImpl implements MetaAmlResultMainService{
	/* mantis：OTH0065，處理人員：BJ085，需求單編號：OTH0065 建置AML洗錢查詢畫面 start */

	private MetaAmlResultMainDao metaAmlResultMainDao;

	@Override
	public int countMetaAmlResultMain(Map params) throws SystemException, Exception {
		return metaAmlResultMainDao.count(params);
	}

	@Override
	public Result findMetaAmlResultMainByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = metaAmlResultMainDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<MetaAmlResultMain> searchResult = metaAmlResultMainDao.findByPageInfo(pageInfo);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findMetaAmlResultMainByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<MetaAmlResultMain> searchResult = metaAmlResultMainDao.findByParams(params);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findMetaAmlResultMainByOid(BigDecimal oid) throws SystemException,Exception {

		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		MetaAmlResultMain persisted = metaAmlResultMainDao.findByOid(oid);
		if (null == persisted) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(persisted);
		}
		return result;
	}

	@Override
	public Result updateMetaAmlResultMain(MetaAmlResultMain metaAmlResultMain) throws Exception {

		if (metaAmlResultMain == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = metaAmlResultMainDao.update(metaAmlResultMain);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(metaAmlResultMain);
		return result;
	}

	@Override
	public Result insertMetaAmlResultMain(MetaAmlResultMain metaAmlResultMain) throws Exception {

		if (metaAmlResultMain == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}

		metaAmlResultMain.setCreatetime(new Date());//加入建檔時間
		BigDecimal oid = metaAmlResultMainDao.insert(metaAmlResultMain);
		if(null == oid  || 0L ==oid.longValue()){
			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}
		metaAmlResultMain.setOid(oid);
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(metaAmlResultMain);
		return result;
	}

	@Override
	public Result removeMetaAmlResultMain(BigDecimal oid) throws Exception {

		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		MetaAmlResultMain persisted = metaAmlResultMainDao.findByOid(oid);
		if(null == persisted){//資料庫無資料
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = metaAmlResultMainDao.remove(oid);
		if(!status){
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}


	public MetaAmlResultMainDao getMetaAmlResultMainDao() {
		return metaAmlResultMainDao;
	}

	public void setMetaAmlResultMainDao(MetaAmlResultMainDao metaAmlResultMainDao) {
		this.metaAmlResultMainDao = metaAmlResultMainDao;
	}
}
