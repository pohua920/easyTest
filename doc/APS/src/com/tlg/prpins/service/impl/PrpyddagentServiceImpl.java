package com.tlg.prpins.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.PrpyddagentDao;
import com.tlg.prpins.entity.Prpyddagent;
import com.tlg.prpins.service.PrpyddagentService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.Result;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class PrpyddagentServiceImpl implements PrpyddagentService{
	/*mantis：FIR0295，處理人員：BJ085，需求單編號：FIR0295 外銀板信續件移回新核心-維護作業*/
	private PrpyddagentDao prpyddagentDao;

	@SuppressWarnings("rawtypes")
	@Override
	public Result findPrpyddagentByParams(Map params) throws SystemException, Exception {
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<Prpyddagent> searchResult = prpyddagentDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	public PrpyddagentDao getPrpyddagentDao() {
		return prpyddagentDao;
	}

	public void setPrpyddagentDao(PrpyddagentDao prpyddagentDao) {
		this.prpyddagentDao = prpyddagentDao;
	}
}
