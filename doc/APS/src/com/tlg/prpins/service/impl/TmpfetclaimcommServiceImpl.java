package com.tlg.prpins.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.exception.SystemException;
import com.tlg.prpins.dao.TmpfetclaimcommDao;
import com.tlg.prpins.entity.Tmpfetclaimcomm;
import com.tlg.prpins.service.TmpfetclaimcommService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/** mantis：MOB0018，處理人員：BJ085，需求單編號：MOB0018 理賠資料提交及列印作業 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class TmpfetclaimcommServiceImpl implements TmpfetclaimcommService{

	private TmpfetclaimcommDao tmpfetclaimcommDao;

	@Override
	public int countTmpfetclaimcomm(Map params) throws SystemException, Exception {
		return tmpfetclaimcommDao.count(params);
	}

	@Override
	public Result findTmpfetclaimcommByPageInfo(PageInfo pageInfo) throws SystemException, Exception {

		Result result = new Result();
		if (null == pageInfo) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		int rowCount = tmpfetclaimcommDao.count(pageInfo.getFilter());
		if (0 == rowCount) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
			return result;
		}
		pageInfo.setRowCount(rowCount);
		pageInfo.doPage();

		List<Tmpfetclaimcomm> searchResult = tmpfetclaimcommDao.findByPageInfo(pageInfo);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result findTmpfetclaimcommByParams(Map params) throws SystemException, Exception {

		if (null == params) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = new Result();
		List<Tmpfetclaimcomm> searchResult = tmpfetclaimcommDao.findByParams(params);
		if (null == searchResult || 0 == searchResult.size()) {
			result.setMessage(Message.getMessage(Constants.SEARCH_NO_DATA));
		} else {
			result.setResObject(searchResult);
		}
		return result;
	}

	@Override
	public Result updateTmpfetclaimcomm(Tmpfetclaimcomm tmpfetclaimcomm) throws SystemException, Exception {
		
		if (tmpfetclaimcomm == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		
		boolean status = tmpfetclaimcommDao.update(tmpfetclaimcomm);
		if (!status) {
			throw new SystemException(Message.getMessage(Constants.UPDATE_DATA_FAIL));
		}
		
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.UPDATE_DATA_SUCCESS));
		result.setResObject(tmpfetclaimcomm);
		return result;
	}

	@Override
	public Result insertTmpfetclaimcomm(Tmpfetclaimcomm tmpfetclaimcomm) throws SystemException, Exception {

		if (tmpfetclaimcomm == null) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}

		BigDecimal oid = tmpfetclaimcommDao.insert(tmpfetclaimcomm);
		if(null == oid  || 0L ==oid.longValue()){
			throw new SystemException(Message.getMessage(Constants.SAVE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
		result.setResObject(tmpfetclaimcomm);
		return result;
	}

	@Override
	public Result removeTmpfetclaimcomm(BigDecimal oid) throws SystemException, Exception {
		if (null == oid || 0L == oid.longValue()) {
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Tmpfetclaimcomm persisted = tmpfetclaimcommDao.findByOid(oid);
		if(null == persisted){//資料庫無資料
			throw new SystemException(Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		boolean status = tmpfetclaimcommDao.remove(oid);
		if(!status){
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}

	/** mantis：MOB0017，處理人員：BI086，需求單編號：MOB0017 將安達提供中介檔上傳資料庫 */
	@Override
	public Result removeTmpfetclaimcommAll() throws SystemException, Exception {
		boolean status = tmpfetclaimcommDao.removeAll();
		if(!status){
			throw new SystemException(Message.getMessage(Constants.DELETE_DATA_FAIL));
		}
		Result result = new Result();
		result.setMessage(Message.getMessage(Constants.DELETE_DATA_SUCCESS));
		result.setResObject(Boolean.TRUE);
		return result;
	}
	@Override
	public void batchInsertTmpfetclaimcomm(List<Tmpfetclaimcomm> listTmpfetclaimcomm) throws SystemException,
			Exception {
		this.tmpfetclaimcommDao.processInBatch(listTmpfetclaimcomm);
	}
	/** mantis：MOB0017，處理人員：BI086，需求單編號：MOB0017 將安達提供中介檔上傳資料庫 end */
	
	public TmpfetclaimcommDao getTmpfetclaimcommDao() {
		return tmpfetclaimcommDao;
	}

	public void setTmpfetclaimcommDao(TmpfetclaimcommDao tmpfetclaimcommDao) {
		this.tmpfetclaimcommDao = tmpfetclaimcommDao;
	}

}
