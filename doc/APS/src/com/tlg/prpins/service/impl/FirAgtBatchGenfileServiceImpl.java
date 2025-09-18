package com.tlg.prpins.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.FirAgtBatchGenfileDao;
import com.tlg.prpins.entity.FirAgtBatchGenfile;
import com.tlg.prpins.service.FirAgtBatchGenfileService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirAgtBatchGenfileServiceImpl implements FirAgtBatchGenfileService{
	/*mantis：FIR0266，處理人員：BJ085，需求單編號：FIR0266 板信資料交換處理作業 start */
	private FirAgtBatchGenfileDao firAgtBatchGenfileDao;

	@SuppressWarnings("rawtypes")
	@Override
	public int countFirAgtBatchGenfile(Map params) throws SystemException, Exception {
		return firAgtBatchGenfileDao.count(params);
	}

	@Override
	public Result findFirAgtBatchGenfileByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = firAgtBatchGenfileDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<FirAgtBatchGenfile> searchResult = firAgtBatchGenfileDao.findByPageInfo(pageInfo);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result findFirAgtBatchGenfileByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FirAgtBatchGenfile> searchResult = firAgtBatchGenfileDao.findByParams(params);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findFirAgtBatchGenfileByOid(BigDecimal oid) throws SystemException, Exception {

		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		FirAgtBatchGenfile persisted = firAgtBatchGenfileDao.findByOid(oid);
		if (null == persisted) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(persisted);
		}
		return result;
	}

	@Override
	public Result insertFirAgtBatchGenfile(FirAgtBatchGenfile firAgtBatchGenfile) throws SystemException, Exception {

		if (firAgtBatchGenfile == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}

		BigDecimal oid = firAgtBatchGenfileDao.insert(firAgtBatchGenfile);
		if(null == oid  || 0L ==oid.longValue()){
			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}
		firAgtBatchGenfile.setOid(oid);
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(firAgtBatchGenfile);
		return result;
	}

	public FirAgtBatchGenfileDao getFirAgtBatchGenfileDao() {
		return firAgtBatchGenfileDao;
	}

	public void setFirAgtBatchGenfileDao(FirAgtBatchGenfileDao firAgtBatchGenfileDao) {
		this.firAgtBatchGenfileDao = firAgtBatchGenfileDao;
	}
}
