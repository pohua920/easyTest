package com.tlg.prpins.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.PrpdcarmodelextDao;
import com.tlg.prpins.entity.Prpdcarmodelext;
import com.tlg.prpins.service.PrpdcarmodelextService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
/**mantis：CAR0563，處理人員：CD078，需求單編號：CAR0563 廠牌車型代號外部資料單筆維護查詢作業 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class PrpdcarmodelextServiceImpl implements PrpdcarmodelextService {
	private PrpdcarmodelextDao prpdcarmodelextDao;

	public Result findprpdcarmodelextByPageInfo(PageInfo pageInfo) throws SystemException, Exception {
		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = prpdcarmodelextDao.count(pageInfo.getFilter());
		
		if (rowCount == 0) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();
		
		List<Prpdcarmodelext> searchResult = prpdcarmodelextDao.findByPageInfo(pageInfo);
		if (null == searchResult || searchResult.isEmpty()){
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Result findPrpdcarmodelextByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<Prpdcarmodelext> searchResult = prpdcarmodelextDao.findByParams(params);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public Result findPrpdcarmodelextByOid(BigDecimal oid) throws SystemException,Exception {

		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		Prpdcarmodelext persisted = prpdcarmodelextDao.findByOid(oid);
		if (null == persisted) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(persisted);
		}
		return result;
	}
	
	public Result insertPrpdcarmodelext(Prpdcarmodelext prpdcarmodelext) throws SystemException, Exception {
		if (null == prpdcarmodelext){
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		BigDecimal oid = prpdcarmodelextDao.insert(prpdcarmodelext);
		if (null == oid || 0L == oid.longValue()){
			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}else {
			prpdcarmodelext.setOid(oid);
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(prpdcarmodelext);
		return result;
	}

	public Result updateFirCtbcDeptinfo(Prpdcarmodelext prpdcarmodelext) throws SystemException, Exception {
		if (prpdcarmodelext == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = prpdcarmodelextDao.update(prpdcarmodelext);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(prpdcarmodelext);
		return result;
	}
	
	public PrpdcarmodelextDao getPrpdcarmodelextDao() {
		return prpdcarmodelextDao;
	}

	public void setPrpdcarmodelextDao(PrpdcarmodelextDao prpdcarmodelextDao) {
		this.prpdcarmodelextDao = prpdcarmodelextDao;
	}

}
