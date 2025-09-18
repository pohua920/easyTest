package com.tlg.prpins.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.CarTradevanApplyLogDao;
import com.tlg.prpins.entity.CarTradevanApplyLog;
import com.tlg.prpins.service.CarTradevanApplyLogService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

@Transactional(value = "prpinsTransactionManager",propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class CarTradevanApplyLogServiceImpl implements CarTradevanApplyLogService{

	private CarTradevanApplyLogDao carTradevanApplyLogDao;

	@Override
	public int countCarTradevanApplyLog(Map params) throws SystemException, Exception {
		return carTradevanApplyLogDao.count(params);
	}

	@Override
	public Result findCarTradevanApplyLogByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = carTradevanApplyLogDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<CarTradevanApplyLog> searchResult = carTradevanApplyLogDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Result findCarTradevanApplyLogByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<CarTradevanApplyLog> searchResult = carTradevanApplyLogDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findCarTradevanApplyLogByOid(BigDecimal oid) throws SystemException,Exception {

		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		CarTradevanApplyLog persisted = carTradevanApplyLogDao.findByOid(oid);
		if (null == persisted) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(persisted);
		}
		return result;
	}

	@Override
	public Result updateCarTradevanApplyLog(CarTradevanApplyLog carTradevanApplyLog) throws SystemException, Exception {

		if (carTradevanApplyLog == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = carTradevanApplyLogDao.update(carTradevanApplyLog);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(carTradevanApplyLog);
		return result;
	}

	@Override
	public Result insertCarTradevanApplyLog(CarTradevanApplyLog carTradevanApplyLog) throws SystemException, Exception {

		if (carTradevanApplyLog == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		carTradevanApplyLog.setCreatetime(new Date());
		BigDecimal oid = carTradevanApplyLogDao.insert(carTradevanApplyLog);
		if(null == oid  || 0L ==oid.longValue()){
			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}
		
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(carTradevanApplyLog);
		return result;
	}

	@Override
	public Result removeCarTradevanApplyLog(BigDecimal oid) throws SystemException, Exception {

		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		CarTradevanApplyLog persisted = carTradevanApplyLogDao.findByOid(oid);
		if(null == persisted){//資料庫無資料
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = carTradevanApplyLogDao.remove(oid);
		if(!status){
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}


	public CarTradevanApplyLogDao getCarTradevanApplyLogDao() {
		return carTradevanApplyLogDao;
	}

	public void setCarTradevanApplyLogDao(CarTradevanApplyLogDao carTradevanApplyLogDao) {
		this.carTradevanApplyLogDao = carTradevanApplyLogDao;
	}
}
