package com.tlg.prpins.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
// mantis：CAR0554，處理人員：DP0714，APS-TVBCM&TVMCQ作業通知信調整
import com.tlg.aps.vo.TiiTvbcmVo;
import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.TiiTvbcmDao;
import com.tlg.prpins.entity.TiiTvbcm;
import com.tlg.prpins.service.TiiTvbcmService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/*mantis：CAR0417，處理人員：BJ085，需求單編號：CAR0417 機車強制車險重新投保發對接功能 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class TiiTvbcmServiceImpl implements TiiTvbcmService{

	private TiiTvbcmDao tiiTvbcmDao;

	@Override
	public int countTiiTvbcm(Map params) throws SystemException, Exception {
		return tiiTvbcmDao.count(params);
	}

	@Override
	public Result findTiiTvbcmByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = tiiTvbcmDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<TiiTvbcm> searchResult = tiiTvbcmDao.findByPageInfo(pageInfo);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findTiiTvbcmByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<TiiTvbcm> searchResult = tiiTvbcmDao.findByParams(params);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findTiiTvbcmByOid(BigDecimal oid) throws SystemException, Exception {

		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		TiiTvbcm persisted = tiiTvbcmDao.findByOid(oid);
		if (null == persisted) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(persisted);
		}
		return result;
	}

	@Override
	public Result insertTiiTvbcm(TiiTvbcm tiiTvbcm) throws SystemException, Exception {

		if (tiiTvbcm == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}

		tiiTvbcmDao.insert(tiiTvbcm);
//		if(null == oid  || 0L ==oid.longValue()){
//			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
//		}
//		tiiTvbcm.setOid(oid);
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(tiiTvbcm);
		return result;
	}
	
	@Override
	public Result updateTiiTvbcm(TiiTvbcm tiiTvbcm) throws SystemException, Exception {
		if (tiiTvbcm == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = tiiTvbcmDao.update(tiiTvbcm);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(tiiTvbcm);
		return result;
	}
	
	@Override
	public int findMaxID() throws Exception {
		int count = tiiTvbcmDao.count(new HashMap<>());
		if(count == 0) {
			return count;
		}
		return tiiTvbcmDao.selectMaxID();
	}

	/**
	 * mantis：CAR0554，處理人員：DP0714，APS-TVBCM&TVMCQ作業通知信調整
	 */
	@Override
	public Result selectTiiTvbcm2() throws SystemException, Exception {
		Result result = new Result();
		List<TiiTvbcmVo> searchResult = tiiTvbcmDao.selectTiiTvbcm2();
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	public TiiTvbcmDao getTiiTvbcmDao() {
		return tiiTvbcmDao;
	}

	public void setTiiTvbcmDao(TiiTvbcmDao tiiTvbcmDao) {
		this.tiiTvbcmDao = tiiTvbcmDao;
	}
}
