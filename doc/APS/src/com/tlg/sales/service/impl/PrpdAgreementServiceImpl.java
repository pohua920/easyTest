package com.tlg.sales.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.sales.dao.PrpdAgreementDao;
import com.tlg.sales.entity.PrpdAgreement;
import com.tlg.sales.service.PrpdAgreementService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

@Transactional(value="salesTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class PrpdAgreementServiceImpl implements PrpdAgreementService{
	/* mantis：FIR0294，處理人員：BJ085，需求單編號：FIR0294 外銀板信續件移回新核心-資料接收排程 start */
	
	private PrpdAgreementDao prpdAgreementDao;

	@Override
	public int countPrpdAgreement(Map params) throws SystemException, Exception {
		return prpdAgreementDao.count(params);
	}

	@Override
	public Result findPrpdAgreementByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = prpdAgreementDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<PrpdAgreement> searchResult = prpdAgreementDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findPrpdAgreementByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<PrpdAgreement> searchResult = prpdAgreementDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	//mantis：FIR0454，處理人員：BJ085，需求單編號：FIR0454 住火-APS富邦續件資料接收排程
	public Result findPrpdAgreementJoinDetail(Map params) throws SystemException, Exception {
		Result result = new Result();
		//mantis：FIR0454，處理人員：BJ085，需求單編號：FIR0454 住火-APS富邦續件資料接收排程
		List<PrpdAgreement> searchResult = prpdAgreementDao.findPrpdAgreementJoinDetail(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	public PrpdAgreementDao getPrpdAgreementDao() {
		return prpdAgreementDao;
	}

	public void setPrpdAgreementDao(PrpdAgreementDao prpdAgreementDao) {
		this.prpdAgreementDao = prpdAgreementDao;
	}
}
