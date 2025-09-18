package com.tlg.prpins.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

// mantis：CAR0554，處理人員：DP0714，APS-TVBCM&TVMCQ作業通知信調整
import com.tlg.aps.vo.TiiTvmcqVo;
import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.TiiTvmcqDao;
import com.tlg.prpins.entity.TiiTvmcq;
import com.tlg.prpins.service.TiiTvmcqService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/*mantis：CAR0427，處理人員：BJ085，需求單編號：CAR0427 機車強制車險重新投保發對接功能-第四次明信片 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class TiiTvmcqServiceImpl implements TiiTvmcqService{

	private TiiTvmcqDao tiiTvmcqDao;

	@Override
	public int countTiiTvmcq(Map params) throws SystemException, Exception {
		return tiiTvmcqDao.count(params);
	}

	@Override
	public Result findTiiTvmcqByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = tiiTvmcqDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<TiiTvmcq> searchResult = tiiTvmcqDao.findByPageInfo(pageInfo);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findTiiTvmcqByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<TiiTvmcq> searchResult = tiiTvmcqDao.findByParams(params);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findTiiTvmcqByOid(BigDecimal oid) throws SystemException, Exception {

		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		TiiTvmcq persisted = tiiTvmcqDao.findByOid(oid);
		if (null == persisted) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(persisted);
		}
		return result;
	}

	@Override
	public Result insertTiiTvmcq(TiiTvmcq tiiTvmcq) throws SystemException, Exception {

		if (tiiTvmcq == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}

		tiiTvmcqDao.insert(tiiTvmcq);
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(tiiTvmcq);
		return result;
	}
	
	@Override
	public Result updateTiiTvmcq(TiiTvmcq tiiTvmcq) throws SystemException, Exception {
		if (tiiTvmcq == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = tiiTvmcqDao.update(tiiTvmcq);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(tiiTvmcq);
		return result;
	}
	
	@Override
	public int findMaxID() throws Exception {
		int count = tiiTvmcqDao.count(new HashMap<>());
		if(count == 0) {
			return count;
		}
		return tiiTvmcqDao.selectMaxID();
	}

	/**
	 * mantis：CAR0554，處理人員：DP0714，APS-TVBCM&TVMCQ作業通知信調整
	 */
	@Override
	public Result selectTiiTvmcq2() throws SystemException, Exception {
		Result result = new Result();
		List<TiiTvmcqVo> searchResult = tiiTvmcqDao.selectTiiTvmcq2();
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	public TiiTvmcqDao getTiiTvmcqDao() {
		return tiiTvmcqDao;
	}

	public void setTiiTvmcqDao(TiiTvmcqDao tiiTvmcqDao) {
		this.tiiTvmcqDao = tiiTvmcqDao;
	}
}
