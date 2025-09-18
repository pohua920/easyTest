package com.tlg.prpins.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.FirAgtrnTmpBopDao;
import com.tlg.prpins.entity.FirAgtrnTmpBop;
import com.tlg.prpins.service.FirAgtrnTmpBopService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirAgtrnTmpBopServiceImpl implements FirAgtrnTmpBopService{
	/* mantis：FIR0294，處理人員：BJ085，需求單編號：FIR0294 外銀板信續件移回新核心-資料接收排程 start */
	private FirAgtrnTmpBopDao firAgtrnTmpBopDao;

	@Override
	public Result findFirAgtrnTmpBopByPageInfo(PageInfo pageInfo) throws SystemException, Exception {
		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = firAgtrnTmpBopDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();
		
		List<FirAgtrnTmpBop> searchResult = firAgtrnTmpBopDao.findByPageInfo(pageInfo);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result findFirAgtrnTmpBopByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FirAgtrnTmpBop> searchResult = firAgtrnTmpBopDao.findByParams(params);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public int countFirAgtrnTmpBop(Map params) throws SystemException, Exception {
		return firAgtrnTmpBopDao.count(params);
	}

	@Override
	public Result insertFirAgtrnTmpBop(FirAgtrnTmpBop firAgtrnTmpBop) throws SystemException, Exception {

		if (firAgtrnTmpBop == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}

		BigDecimal oid = firAgtrnTmpBopDao.insert(firAgtrnTmpBop);
		if(null == oid  || 0L ==oid.longValue()){
			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}else {
			firAgtrnTmpBop.setOid(oid);
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(firAgtrnTmpBop);
		return result;
	}
	
	@Override
	public Result updateFirAgtrnTmpBop(FirAgtrnTmpBop firAgtrnTmpBop) throws SystemException, Exception {

		if (firAgtrnTmpBop == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = firAgtrnTmpBopDao.update(firAgtrnTmpBop);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(firAgtrnTmpBop);
		return result;
	}

	public FirAgtrnTmpBopDao getFirAgtrnTmpBopDao() {
		return firAgtrnTmpBopDao;
	}

	public void setFirAgtrnTmpBopDao(FirAgtrnTmpBopDao firAgtrnTmpBopDao) {
		this.firAgtrnTmpBopDao = firAgtrnTmpBopDao;
	}
}
