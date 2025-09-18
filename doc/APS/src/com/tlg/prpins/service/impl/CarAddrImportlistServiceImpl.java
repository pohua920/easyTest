package com.tlg.prpins.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.CarAddrImportlistDao;
import com.tlg.prpins.entity.CarAddrImportlist;
import com.tlg.prpins.service.CarAddrImportlistService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/** mantis：CAR0504，處理人員：CC009，需求單編號：CAR0504 微型電動二輪車【已領牌&未領牌】資料交換作業 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class CarAddrImportlistServiceImpl implements CarAddrImportlistService{
	private static final Logger logger = Logger.getLogger(CarAddrImportlistServiceImpl.class);
	private CarAddrImportlistDao carAddrImportlistDao;
	
	@Override
	public Result findCarAddrImportlistByPageInfo(PageInfo pageInfo) throws SystemException, Exception {
		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = carAddrImportlistDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();
		
		List<CarAddrImportlist> searchResult = carAddrImportlistDao.findByPageInfo(pageInfo);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result removeCarAddrImportlist(CarAddrImportlist entity) throws SystemException, Exception {
		if (null == entity) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = carAddrImportlistDao.remove(entity);
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}
	
	@Override
	public Result insertAddrImportlist(CarAddrImportlist entity) throws SystemException, Exception {
		if (entity == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		carAddrImportlistDao.insert(entity);
		
		if(carAddrImportlistDao.isUnique(entity)) {
			throw new SystemException(Constants.SAVE_DATA_FAIL);  
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(entity);
		return result;
	}

	public CarAddrImportlistDao getCarAddrImportlistDao() {
		return carAddrImportlistDao;
	}

	public void setCarAddrImportlistDao(CarAddrImportlistDao carAddrImportlistDao) {
		this.carAddrImportlistDao = carAddrImportlistDao;
	}

}
