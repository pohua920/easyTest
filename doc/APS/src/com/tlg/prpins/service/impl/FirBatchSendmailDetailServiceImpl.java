package com.tlg.prpins.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.vo.WriteForBatchSendmailVo;
import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.FirBatchSendmailDetailDao;
import com.tlg.prpins.entity.FirBatchSendmailDetail;
import com.tlg.prpins.service.FirBatchSendmailDetailService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class FirBatchSendmailDetailServiceImpl implements FirBatchSendmailDetailService{

	private FirBatchSendmailDetailDao firBatchSendmailDetailDao;
	
	@SuppressWarnings("rawtypes")
	@Override
	public int countFirBatchSendmailDetail(Map params) throws SystemException, Exception {
		return firBatchSendmailDetailDao.count(params);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Result findFirBatchSendmailDetailByPageInfo(PageInfo pageInfo) throws SystemException, Exception {
		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = firBatchSendmailDetailDao.count(pageInfo.getFilter());
		if (rowCount == 0) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<FirBatchSendmailDetail> searchResult = firBatchSendmailDetailDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result findFirBatchSendmailDetailByParams(Map params) throws SystemException, Exception {
		Result result = new Result();
		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}

		List<FirBatchSendmailDetail> searchResult = firBatchSendmailDetailDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	@Override
	public Result updateFirBatchSendmailDetail(FirBatchSendmailDetail firBatchSendmailDetail) throws SystemException, Exception {
		if (firBatchSendmailDetail == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = firBatchSendmailDetailDao.update(firBatchSendmailDetail);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(firBatchSendmailDetail);
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Result insertFirBatchSendmailDetail(FirBatchSendmailDetail firBatchSendmailDetail) throws SystemException, Exception {
		if (firBatchSendmailDetail == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
//		Map params = new HashMap();
////		params.put("batchNo",firAgtSalesMapping.getBatchNo());
//		params.put("batchNo","A");
//		int count = firAgtSalesMappingDao.count(params);
//		if(count > 0) {
//			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
//		}

//		BigDecimal oid = firBatchSendmailDetailDao.insert(firBatchSendmailDetail);
//		if(null == oid  || 0L ==oid.longValue()){
//			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
//		}else {
//			firBatchSendmailDetail.setOid(oid);
//		}
		firBatchSendmailDetailDao.insert(firBatchSendmailDetail);
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(firBatchSendmailDetail);
		return result;
	}

	@Override
	public Result removeFirBatchSendmailDetail(BigDecimal oid) throws SystemException, Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Result findFirBatchSendmailDetailByOid(BigDecimal oid) throws SystemException, Exception {
		Result result = new Result();
		if (null == oid) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		FirBatchSendmailDetail searchResult = firBatchSendmailDetailDao.findByOid(oid);
		if (null == searchResult) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}
	
	/*mantis：CAR0508，處理人員：BJ085，需求單編號：CAR0508 APS查詢電子保單發送結果(新增車險與旅平險)*/
	@Override
	public Result findRiskcodeForNoticeEmail() throws SystemException, Exception {
		Result result = new Result();
		List<WriteForBatchSendmailVo> searchResult = firBatchSendmailDetailDao.findRiskcodeForNoticeEmail();
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	public FirBatchSendmailDetailDao getFirBatchSendmailDetailDao() {
		return firBatchSendmailDetailDao;
	}

	public void setFirBatchSendmailDetailDao(FirBatchSendmailDetailDao firBatchSendmailDetailDao) {
		this.firBatchSendmailDetailDao = firBatchSendmailDetailDao;
	}

}
