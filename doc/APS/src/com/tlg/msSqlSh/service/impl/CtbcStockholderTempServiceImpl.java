package com.tlg.msSqlSh.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.msSqlSh.dao.CtbcStockholderTempDao;
import com.tlg.msSqlSh.entity.CtbcStockholderTemp;
import com.tlg.msSqlSh.service.CtbcStockholderTempService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/* mantis：OTH0101，處理人員：BJ085，需求單編號：OTH0101 取得金控利關人資料同步排程 */
@Transactional(value = "msSqlShTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class CtbcStockholderTempServiceImpl implements CtbcStockholderTempService{

	private CtbcStockholderTempDao ctbcStockholderTempDao;

	@Override
	public int countCtbcStockholderTemp(Map params) throws SystemException, Exception {
		return ctbcStockholderTempDao.count(params);
	}

	@Override
	public Result findCtbcStockholderTempByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = ctbcStockholderTempDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<CtbcStockholderTemp> searchResult = ctbcStockholderTempDao.findByPageInfo(pageInfo);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findCtbcStockholderTempByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<CtbcStockholderTemp> searchResult = ctbcStockholderTempDao.findByParams(params);
		if (null == searchResult || searchResult.isEmpty()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result insertCtbcStockholderTemp(CtbcStockholderTemp ctbcStockholderTemp) throws SystemException, Exception {

		if (ctbcStockholderTemp == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		ctbcStockholderTempDao.insert(ctbcStockholderTemp);
		
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(ctbcStockholderTemp);
		return result;
	}
	
	@Override
	public void truncate() throws Exception {
		ctbcStockholderTempDao.truncate();
	}

	public CtbcStockholderTempDao getCtbcStockholderTempDao() {
		return ctbcStockholderTempDao;
	}

	public void setCtbcStockholderTempDao(CtbcStockholderTempDao ctbcStockholderTempDao) {
		this.ctbcStockholderTempDao = ctbcStockholderTempDao;
	}
}
