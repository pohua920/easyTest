package com.tlg.prpins.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.PrpcpaymentDao;
import com.tlg.prpins.entity.Prpcpayment;
import com.tlg.prpins.service.PrpcpaymentService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.Result;

/*mantis：FIR0570，處理人員：BJ085，需求單編號：FIR0570 住火_APS每月應續件產生排程 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class PrpcpaymentServiceImpl implements PrpcpaymentService{

	private PrpcpaymentDao prpcpaymentDao;

	@SuppressWarnings("rawtypes")
	@Override
	public Result findPrpcpaymentByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<Prpcpayment> searchResult = prpcpaymentDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	public PrpcpaymentDao getPrpcpaymentDao() {
		return prpcpaymentDao;
	}

	public void setPrpcpaymentDao(PrpcpaymentDao prpcpaymentDao) {
		this.prpcpaymentDao = prpcpaymentDao;
	}

}
