package com.tlg.prpins.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.vo.EpolicyPrpdrationclausekindVo;
import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.PrpdrationclausekindDao;
import com.tlg.prpins.service.PrpdrationclausekindService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.Result;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class PrpdrationclausekindServiceImpl implements PrpdrationclausekindService{

	private PrpdrationclausekindDao prpdrationclausekindDao;

	@Override
	public Result selectForEpolicy(Map params) throws SystemException, Exception {
		Result result = new Result();
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		List<EpolicyPrpdrationclausekindVo> searchResult = prpdrationclausekindDao.selectForEpolicy(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	public PrpdrationclausekindDao getPrpdrationclausekindDao() {
		return prpdrationclausekindDao;
	}

	public void setPrpdrationclausekindDao(PrpdrationclausekindDao prpdrationclausekindDao) {
		this.prpdrationclausekindDao = prpdrationclausekindDao;
	}

}
