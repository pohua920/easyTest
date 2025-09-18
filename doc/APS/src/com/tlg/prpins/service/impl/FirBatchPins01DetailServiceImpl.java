package com.tlg.prpins.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.FirBatchPins01DetailDao;
import com.tlg.prpins.entity.FirBatchPins01Detail;
import com.tlg.prpins.service.FirBatchPins01DetailService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/* mantis：FIR0436，處理人員：BJ085，需求單編號：FIR0436 住火-APS保單通訊資料產生及下載作業批次作業 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirBatchPins01DetailServiceImpl implements FirBatchPins01DetailService{

	private FirBatchPins01DetailDao firBatchPins01DetailDao;
	
	@SuppressWarnings("rawtypes")
	@Override
	public int countFirBatchPins01Detail(Map params) throws SystemException, Exception {
		return firBatchPins01DetailDao.selectSumRiskcodecount(params);
	}
	
	@Override
	public Result findFirBatchPins01DetailByPageInfo(PageInfo pageInfo) throws SystemException, Exception {
		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = firBatchPins01DetailDao.count(pageInfo.getFilter());
		if (rowCount == 0) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<FirBatchPins01Detail> searchResult = firBatchPins01DetailDao.findByPageInfo(pageInfo);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result findFirBatchPins01DetailByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<FirBatchPins01Detail> searchResult = firBatchPins01DetailDao.findByParams(params);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public Result updateFirBatchPins01Detail(FirBatchPins01Detail firBatchPins01Detail) throws SystemException, Exception {
		if (firBatchPins01Detail == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = firBatchPins01DetailDao.update(firBatchPins01Detail);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(firBatchPins01Detail);
		return result;
	}

	@Override
	public Result insertFirBatchPins01Detail(FirBatchPins01Detail firBatchPins01Detail) throws SystemException, Exception {
		if (firBatchPins01Detail == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}

		firBatchPins01DetailDao.insert(firBatchPins01Detail);
		
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(firBatchPins01Detail);
		return result;
	}

	@Override
	public Result findFirBatchPins01DetailByUK(Map params) throws SystemException, Exception {
		Result result = new Result();
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		FirBatchPins01Detail searchResult = firBatchPins01DetailDao.findByUK(params);
		if (null == searchResult) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	public int countSumRiskcodecount(Map params) throws SystemException, Exception {
		return firBatchPins01DetailDao.selectSumRiskcodecount(params);
	}

	public FirBatchPins01DetailDao getFirBatchPins01DetailDao() {
		return firBatchPins01DetailDao;
	}

	public void setFirBatchPins01DetailDao(FirBatchPins01DetailDao firBatchPins01DetailDao) {
		this.firBatchPins01DetailDao = firBatchPins01DetailDao;
	}

}
