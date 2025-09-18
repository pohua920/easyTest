package com.tlg.prpins.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.RenewalnoticeDao;
import com.tlg.prpins.entity.Renewalnotice;
import com.tlg.prpins.service.RenewalnoticeService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/*mantis：CAR0417，處理人員：BJ085，需求單編號：CAR0417 機車強制車險重新投保發對接功能 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class RenewalnoticeServiceImpl implements RenewalnoticeService{

	private RenewalnoticeDao renewalnoticeDao;

	@Override
	public int countRenewalnotice(Map params) throws SystemException, Exception {
		return renewalnoticeDao.count(params);
	}

	@Override
	public Result findRenewalnoticeByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = renewalnoticeDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<Renewalnotice> searchResult = renewalnoticeDao.findByPageInfo(pageInfo);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findRenewalnoticeByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<Renewalnotice> searchResult = renewalnoticeDao.findByParams(params);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findRenewalnoticeByOid(BigDecimal oid) throws SystemException, Exception {

		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		Renewalnotice persisted = renewalnoticeDao.findByOid(oid);
		if (null == persisted) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(persisted);
		}
		return result;
	}

	@Override
	public Result insertRenewalnotice(Renewalnotice renewalnotice) throws SystemException, Exception {

		if (renewalnotice == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}

		renewalnoticeDao.insert(renewalnotice);
//		if(null == oid  || 0L ==oid.longValue()){
//			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
//		}
//		renewalnotice.setOid(oid);
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(renewalnotice);
		return result;
	}

	public RenewalnoticeDao getRenewalnoticeDao() {
		return renewalnoticeDao;
	}

	public void setRenewalnoticeDao(RenewalnoticeDao renewalnoticeDao) {
		this.renewalnoticeDao = renewalnoticeDao;
	}
}
